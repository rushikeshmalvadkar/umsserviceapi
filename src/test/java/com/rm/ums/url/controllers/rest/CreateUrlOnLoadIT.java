package com.rm.ums.url.controllers.rest;


import com.rm.ums.TestFileUtils;
import com.rm.ums.common.AbstractIT;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class CreateUrlOnLoadIT extends AbstractIT {

    public static final String ENDPOINT_CREATE_URL_ON_LOAD = "/urls/create-url-on-load";

    @Test
    void should_return_create_url_on_load_response() throws Exception {

        String expectedResponse =
                TestFileUtils.readFile(
                        "responses/create-url-on-load-response.json");

        Response response =
                umsRequest()
                        .header("roleid", CUSTOMER_ROLE_ID)
                        .when()
                        .get(ENDPOINT_CREATE_URL_ON_LOAD);

        response.then()
                .statusCode(200);

        JSONAssert.assertEquals(
                expectedResponse,
                response.asPrettyString(),
                true
        );
    }
}
