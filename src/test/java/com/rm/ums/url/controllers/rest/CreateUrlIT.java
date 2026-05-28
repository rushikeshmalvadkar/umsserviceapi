package com.rm.ums.url.controllers.rest;

import com.rm.ums.TestFileUtils;
import com.rm.ums.assertions.CreateUrlResponseAssertions;
import com.rm.ums.common.AbstractIT;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.equalTo;

@Sql(
        scripts = "/sql/cleanup/cleanup-urls.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class CreateUrlIT extends AbstractIT {

    private static final String ENDPOINT_CREATE_URL = "/urls/create-url";
    private static final String CREATE_URL_WITH_CUSTOM_SLUG_REQUEST_JSON_FILE_PATH = "requests/create-url-with-custom-slug-request.json";
    private static final String CREATE_URL_WITHOUT_SLUG_REQUEST_JSON_FILE_PATH = "requests/create-url-without-slug-request.json";

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
        CreateUrlResponseAssertions.assertCreateUrlResponseWithCustomSlug(response, expectedTitle, expectedOriginalUrl, expectedSlug, expectedCreatedByUserId);


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
        CreateUrlResponseAssertions.assertCreateUrlResponseWithGeneratedSlug(response, expectedTitle, expectedOriginalUrl, expectedCreatedByUserId);


    }


    @Test
    public void should_return_create_url_response_title_not_in_input() throws Exception {

        String requestPayload = """
                {
                    "originalUrl": "https://example.com",
                    "slug":"test"
                }
                """;
        String expectedResponse = "hi";


        Response response =
                umsRequest()
                        .header(REQUEST_HEADER_ROLE_ID_KEY, CUSTOMER_ROLE_ID)
                        .contentType(ContentType.JSON)
                        .body(requestPayload)
                        .when()
                        .post(ENDPOINT_CREATE_URL);

        response.then()
                .body("message", equalTo("title is required"));
    }


    @Test
    public void should_return_create_url_response_original_url_not_in_input() throws Exception {

        String requestPayload = """
                {
                    "title": "Test URL",
                    "slug":"test"
                }
                """;
        String expectedResponse = "hi";


        Response response =
                umsRequest()
                        .header(REQUEST_HEADER_ROLE_ID_KEY, CUSTOMER_ROLE_ID)
                        .contentType(ContentType.JSON)
                        .body(requestPayload)
                        .when()
                        .post(ENDPOINT_CREATE_URL);

        response.then()
                .body("message", equalTo("original url is required"));
    }
}
