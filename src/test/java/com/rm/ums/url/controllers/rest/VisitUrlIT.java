package com.rm.ums.url.controllers.rest;

import com.rm.ums.assertions.VisitUrlAssertions;
import com.rm.ums.common.AbstractIT;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static com.rm.ums.url.enums.VisitUrlStatusEnum.INACTIVE_SLUG;
import static com.rm.ums.url.enums.VisitUrlStatusEnum.UNKNOWN_SLUG;
import static org.assertj.core.api.Assertions.assertThat;

@Sql(
        scripts = "/sql/test-data/insert-urls.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class VisitUrlIT  extends AbstractIT {

    public static final String ENDPOINT_VISIT_URL = "/urls/visit-url/{slug}";

    @Test
    void should_redirect_to_original_url_when_slug_exists() {

        Response response = umsRequestWithoutHeader()
                .pathParam("slug","yt")
                .when()
                .redirects().follow(false)
                .get(ENDPOINT_VISIT_URL);
        String exceptedUrl = "https://www.youtube.com";
        VisitUrlAssertions.assertVisitUrlFound(response,exceptedUrl);
    }

    @Test
    void should_show_error_message_when_slug_does_not_exist() {
        String response =
                umsRequestWithoutHeader()
                        .pathParam("slug", "unknown-slug")
                        .when()
                        .get(ENDPOINT_VISIT_URL)
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();
        assertThat(response).contains(UNKNOWN_SLUG.message());
    }

    @Test
    void should_show_error_message_when_slug_is_inactive() {
        String response =
                umsRequestWithoutHeader()
                        .pathParam("slug", "oa")
                        .when()
                        .get(ENDPOINT_VISIT_URL)
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();
        assertThat(response).contains(INACTIVE_SLUG.message());
    }

//    @Test
//    void should_show_error_message_when_slug_is_inactive() {
//        String response =
//                umsRequestWithoutHeader()
//                        .pathParam("slug", "oa")
//                        .when()
//                        .get(ENDPOINT_VISIT_URL)
//                        .then()
//                        .statusCode(200)
//                        .extract()
//                        .asString();
//        assertThat(response).contains(INACTIVE_SLUG.message());
//    }
}
