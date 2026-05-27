package com.rm.ums.url.controllers.rest;

import com.rm.ums.TestFileUtils;
import com.rm.ums.common.AbstractIT;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.Matchers.equalTo;


public class CreateUrlIT extends AbstractIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String ENDPOINT_CREATE_URL = "/urls/create-url";
    private static final String CREATE_URL_RESPONSE_JSON =
            "responses/create-url-response.json";

    @BeforeEach
    void cleanDb() {
        jdbcTemplate.execute("TRUNCATE TABLE urls");
    }

    @Test
    public void should_return_create_url_response() throws Exception {

        String requestPayload = """
                {
                    "originalUrl": "https://example.com",
                    "title": "Test URL",
                    "slug":"test"
                }
                """;
        String expectedResponse =
                TestFileUtils.readFile(CREATE_URL_RESPONSE_JSON);

        Response response =
                umsRequest()
                        .header(REQUEST_HEADER_ROLE_ID_KEY, CUSTOMER_ROLE_ID)
                        .contentType(ContentType.JSON)
                        .body(requestPayload)
                        .when()
                        .post(ENDPOINT_CREATE_URL);

        JSONAssert.assertEquals(
                expectedResponse,
                response.asString(),
                true
        );
    }

    @Test
    public void should_return_create_url_response_title_not_in_input() throws Exception {

        String requestPayload = """
                {
                    "originalUrl": "https://example.com",
                    "slug":"test"
                }
                """;
        String expectedResponse =
                TestFileUtils.readFile(CREATE_URL_RESPONSE_JSON);

        Response response =
                umsRequest()
                        .header(REQUEST_HEADER_ROLE_ID_KEY, CUSTOMER_ROLE_ID)
                        .contentType(ContentType.JSON)
                        .body(requestPayload)
                        .when()
                        .post(ENDPOINT_CREATE_URL);

        response.then()
                .body("message",equalTo("title is required"));
    }

    @Test
    public void should_return_create_url_response_original_url_not_in_input() throws Exception {

        String requestPayload = """
                {
                    "title": "Test URL",
                    "slug":"test"
                }
                """;
        String expectedResponse =
                TestFileUtils.readFile(CREATE_URL_RESPONSE_JSON);

        Response response =
                umsRequest()
                        .header(REQUEST_HEADER_ROLE_ID_KEY, CUSTOMER_ROLE_ID)
                        .contentType(ContentType.JSON)
                        .body(requestPayload)
                        .when()
                        .post(ENDPOINT_CREATE_URL);

        response.then()
                .body("message",equalTo("original url is required"));
    }
}
