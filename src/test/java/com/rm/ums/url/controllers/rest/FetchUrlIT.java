package com.rm.ums.url.controllers.rest;

import com.rm.ums.TestFileUtils;
import com.rm.ums.common.AbstractIT;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.equalTo;

@Sql(
        scripts = "/sql/test-data/insert-urls.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class FetchUrlIT extends AbstractIT {

    private static final String ENDPOINT_FETCH_URLS = "/urls/fetch-urls";
    private static final String FETCH_ALL_URLS_RESPONSE_JSON_FILE_PATH = "responses/fetch-all-urls-response.json";
    private static final String FETCH_ACTIVE_URLS_RESPONSE_JSON_FILE_PATH = "responses/fetch-active-urls-response.json";


    @Test
    void should_return_all_fetch_urls_response() throws Exception {

        String request = "{}";

        String expectedResponse =
                TestFileUtils.readFile(
                        FETCH_ALL_URLS_RESPONSE_JSON_FILE_PATH);


        Response response =
                umsRequest()
                        .header(REQUEST_HEADER_ROLE_ID_KEY, CUSTOMER_ROLE_ID)
                        .contentType(ContentType.JSON)
                        .body(request)
                        .when()
                        .post(ENDPOINT_FETCH_URLS);

        response.then()
                .statusCode(200);
        System.out.println(response.body().print());

        JSONAssert.assertEquals(
                expectedResponse,
                response.asPrettyString(),
                true
        );

    }

    @Test
    void should_return_active_fetch_urls_response() throws Exception {

        String request = """
                {
                    "urlStatusId": 1
                
                }
                """;

        String expectedResponse =
                TestFileUtils.readFile(
                        FETCH_ACTIVE_URLS_RESPONSE_JSON_FILE_PATH);


        Response response =
                umsRequest()
                        .header(REQUEST_HEADER_ROLE_ID_KEY, CUSTOMER_ROLE_ID)
                        .contentType(ContentType.JSON)
                        .body(request)
                        .when()
                        .post(ENDPOINT_FETCH_URLS);

        response.then()
                .statusCode(200)
                .body("data.data.size()", equalTo(3));

        JSONAssert.assertEquals(
                expectedResponse,
                response.asPrettyString(),
                true
        );

    }
}
