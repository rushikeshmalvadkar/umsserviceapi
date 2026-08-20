package com.rm.ums.url.controllers.rest;

import com.rm.ums.assertions.VisitUrlAssertions;
import com.rm.ums.common.AbstractIT;
import com.rm.ums.common.exceptions.UmsException;
import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.repositories.UrlRepository;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.awaitility.Awaitility.await;

@Sql(scripts = "/sql/test-data/insert-urls.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class VisitUrlViewCountIt extends AbstractIT {

    @Autowired
    private UrlRepository urlRepo;

    public static final String ENDPOINT_VISIT_URL = "/urls/visit-url/{slug}";
    private static final int NUMBER_OF_REQUEST = 100;
    private static final int NUMBER_OF_THREAD = 20;

    @Test
    @DisplayName(value = "when single user visit url at time")
    void should_increase_view_count_when_user_visit_url() {

        Response response = umsRequestWithoutHeader().pathParam("slug", "yt").when().redirects().follow(false).get(ENDPOINT_VISIT_URL);
        String exceptedUrl = "https://www.youtube.com";
        VisitUrlAssertions.assertVisitUrlFound(response, exceptedUrl);
        Long urlViewCounts = urlRepo.findOriginalUrlBy("yt").map(UrlEntity::getViewCount).orElseThrow(() -> new UmsException("Url Not Found", HttpStatus.NOT_FOUND));
        Assertions.assertThat(urlViewCounts).isEqualTo(1L);
    }


    @Test
    @DisplayName("when multiple users visit url concurrently, view count should increase correctly")
    void should_increase_view_count_when_multiple_users_visit_url_concurrently() {

        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);
        CountDownLatch startLatch = new CountDownLatch(1);

        try {
            List<CompletableFuture<Response>> futures = IntStream.range(0, NUMBER_OF_REQUEST).mapToObj(i -> CompletableFuture.supplyAsync(() -> {
                try {
                    // Wait until all tasks are ready
                    startLatch.await();

                    // Send request
                    return umsRequestWithoutHeader().pathParam("slug", "yt").when().redirects().follow(false).get(ENDPOINT_VISIT_URL);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }, executor)).toList();

            // Release all waiting tasks
            startLatch.countDown();

            // Wait for all requests to complete
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            // Verify every request was successful
            futures.forEach(future -> Assertions.assertThat(future.join().getStatusCode()).isEqualTo(HttpStatus.FOUND.value()));

        } finally {
            executor.shutdown();
        }

        // @Async updates may still be running after HTTP requests complete.
        await().atMost(Duration.ofSeconds(10)).pollInterval(Duration.ofMillis(100)).untilAsserted(() -> {

            long actualViewCount = urlRepo.findOriginalUrlBy("yt").map(UrlEntity::getViewCount).orElseThrow(() -> new UmsException("Url Not Found", HttpStatus.NOT_FOUND));

            Assertions.assertThat(actualViewCount).isEqualTo(NUMBER_OF_REQUEST);
        });
    }
}
