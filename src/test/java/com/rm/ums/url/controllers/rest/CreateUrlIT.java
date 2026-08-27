package com.rm.ums.url.controllers.rest;

import com.rm.ums.TestFileUtils;
import com.rm.ums.assertions.CreateUrlResponseAssertions;
import com.rm.ums.common.AbstractIT;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(
        scripts = "/sql/cleanup/cleanup-urls.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class CreateUrlIT extends AbstractIT {

    private static final String ENDPOINT_CREATE_URL = "/urls/create-url";
    private static final String CREATE_URL_WITH_CUSTOM_SLUG_REQUEST_JSON_FILE_PATH = "requests/create-url-with-custom-slug-request.json";
    private static final String CREATE_URL_WITHOUT_SLUG_REQUEST_JSON_FILE_PATH = "requests/create-url-without-slug-request.json";
    private static final String CREATE_URL_WITH_EXPIRATION_TIME = "requests/create-url-with-expiration-time-request.json";
    private static final String CREATE_URL_WITH_EXPIRATION_TIME_IF_START_TIME_AFTER_EXPIRE_TIME = "requests/create-url-with-expiration-time-start-date-after-expiry-date-request.json";
    private static final String CREATE_URL_WITH_EXPIRATION_TIME_IF_EXPIRE_TIME_BEFORE_START_TIME = "requests/create-url-with-expiration-time-start-date-after-expiry-date-request.json";

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
        String startAt=null;
        String expireAt = null;
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
        String startAt = null;
        String expireAt=null;
        CreateUrlResponseAssertions.assertCreateUrlResponseWithGeneratedSlug(response, expectedTitle, expectedOriginalUrl, expectedCreatedByUserId,startAt,expireAt);


    }

    @Test
    public void should_return_url_response_when_user_give_the_expiration_time_during_create_url() throws Exception {

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
        String startAt="2026-08-25";
        String expireAt="2026-08-26";
        CreateUrlResponseAssertions.assertCreateUrlResponseWithGeneratedSlug(response, expectedTitle, expectedOriginalUrl, expectedCreatedByUserId,startAt,expireAt);
    }

    @Test
    public void should_return_error_message_if_start_date_after_expire_date_when_user_give_the_expiration_time_during_create_url_if_start_time_after_expire_time() throws Exception {

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

                assertThat(response.jsonPath().getString("message")).isEqualTo("Start date should not be after expiration date");


    }

    @Test
    public void should_return_error_message_if_expire_time_before_start_time_when_user_give_the_expiration_time_during_create_url_if_start_time_after_expire_time() throws Exception {

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

        assertThat(response.jsonPath().getString("message")).isEqualTo("Start date should not be after expiration date");


    }
}
