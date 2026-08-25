package com.rm.ums.url.controllers.rest;

import com.rm.ums.TestFileUtils;
import com.rm.ums.assertions.CreateUrlResponseAssertions;
import com.rm.ums.common.AbstractIT;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;

@Sql(
        scripts = "/sql/cleanup/cleanup-urls.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class CreateUrlIT extends AbstractIT {

    private static final String ENDPOINT_CREATE_URL = "/urls/create-url";
    private static final String CREATE_URL_WITH_CUSTOM_SLUG_REQUEST_JSON_FILE_PATH = "requests/create-url-with-custom-slug-request.json";
    private static final String CREATE_URL_WITHOUT_SLUG_REQUEST_JSON_FILE_PATH = "requests/create-url-without-slug-request.json";
    private static final String CREATE_URL_WITH_EXPIRATION_TIME = "requests/create-url-with-expiration_time-request.json";
    private static final String CREATE_URL_WITH_EXPIRATION_TIME_IF_START_TIME_AFTER_EXPIRE_TIME = "requests/create-url-with-expiration_time-request.json";

    @Test
    public void should_return_create_url_response_when_user_has_given_custom_slug() throws Exception {

        String request =
                TestFileUtils.readFile(
                        CREATE_URL_WITH_CUSTOM_SLUG_REQUEST_JSON_FILE_PATH);

        Response response =
                umsRequest()
                        .header(REQUEST_HEADER_ROLE_ID_KEY, CUSTOMER_ROLE_ID)
                        .contentType(ContentType.JSON)
                        .body(request)
                        .when()
                        .post(ENDPOINT_CREATE_URL);

        response.then()
                .statusCode(200);

        long expectedCreatedByUserId = 1L;
        String expectedSlug = "DbExploration";
        String expectedOriginalUrl = "https://medium.com/omarelgabrys-blog/database-introduction-part-1-4844fada1fb0";
        String expectedTitle = "Database Exploration Checklist";
        Instant startAt=null;
        Instant expireAt = null;
        CreateUrlResponseAssertions.assertCreateUrlResponseWithCustomSlug(response, expectedTitle, expectedOriginalUrl, expectedSlug, expectedCreatedByUserId,startAt,expireAt);


    }

    @Test
    public void should_return_create_url_response_when_user_does_not_given_custom_slug() throws Exception {

        String request =
                TestFileUtils.readFile(
                        CREATE_URL_WITHOUT_SLUG_REQUEST_JSON_FILE_PATH);

        Response response =
                umsRequest()
                        .header(REQUEST_HEADER_ROLE_ID_KEY, CUSTOMER_ROLE_ID)
                        .contentType(ContentType.JSON)
                        .body(request)
                        .when()
                        .post(ENDPOINT_CREATE_URL);

        response.then()
                .statusCode(200);

        long expectedCreatedByUserId = 1L;
        String expectedOriginalUrl = "https://medium.com/omarelgabrys-blog/database-introduction-part-1-4844fada1fb0";
        String expectedTitle = "Database Exploration Checklist";
        Instant startAt = null;
        Instant expireAt=null;
        CreateUrlResponseAssertions.assertCreateUrlResponseWithGeneratedSlug(response, expectedTitle, expectedOriginalUrl, expectedCreatedByUserId,startAt,expireAt);


    }

    @Test
    public void should_return_start_time_expired_time_in_url_response_when_user_give_the_expiration_time_during_create_url() throws Exception {

        String request =
                TestFileUtils.readFile(
                        CREATE_URL_WITH_EXPIRATION_TIME);

        Response response =
                umsRequest()
                        .header(REQUEST_HEADER_ROLE_ID_KEY, CUSTOMER_ROLE_ID)
                        .contentType(ContentType.JSON)
                        .body(request)
                        .when()
                        .post(ENDPOINT_CREATE_URL);

        response.then()
                .statusCode(200);

        long expectedCreatedByUserId = 1L;
        String expectedOriginalUrl = "https://medium.com/omarelgabrys-blog/database-introduction-part-1-4844fada1fb0";
        String expectedTitle = "Database Exploration Checklist";
        Instant startAt=Instant.parse("2026-08-25T04:25:00Z");
        Instant expireAt=Instant.parse("2026-08-26T00:00:00Z");
        CreateUrlResponseAssertions.assertCreateUrlResponseWithGeneratedSlug(response, expectedTitle, expectedOriginalUrl, expectedCreatedByUserId,startAt,expireAt);


    }

    @Test
    public void should_return_error_message_start_time_should_not_be_after_expire_time_when_user_give_the_expiration_time_during_create_url_if_start_time_after_expire_time() throws Exception {

        String request =
                TestFileUtils.readFile(
                        CREATE_URL_WITH_EXPIRATION_TIME_IF_START_TIME_AFTER_EXPIRE_TIME);

        Response response =
                umsRequest()
                        .header(REQUEST_HEADER_ROLE_ID_KEY, CUSTOMER_ROLE_ID)
                        .contentType(ContentType.JSON)
                        .body(request)
                        .when()
                        .post(ENDPOINT_CREATE_URL);

        response.then()
                .statusCode(400);

    }
}
