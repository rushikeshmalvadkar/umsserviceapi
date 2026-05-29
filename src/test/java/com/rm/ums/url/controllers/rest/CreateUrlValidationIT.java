package com.rm.ums.url.controllers.rest;


import com.rm.ums.TestFileUtils;
import com.rm.ums.common.AbstractIT;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CreateUrlValidationIT extends AbstractIT {

    private static final String ENDPOINT_CREATE_URL = "/urls/create-url";
    private static final String CREATE_URL_WITH_VALIDATION_FAILED_REQUEST_JSON_FILE_PATH = "requests/create-url-with-validation-failed-request.json";

    @Test
    void should_return_create_url_validation_failed_response_when_user_has_given_invalid_data() throws Exception {

        String request = TestFileUtils.readFile(
                CREATE_URL_WITH_VALIDATION_FAILED_REQUEST_JSON_FILE_PATH
        );

        Response response =
                umsRequest()
                        .header(REQUEST_HEADER_ROLE_ID_KEY, CUSTOMER_ROLE_ID)
                        .body(request)
                        .when()
                        .post(ENDPOINT_CREATE_URL);

        response.then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        assertThat(response.jsonPath().getInt("code"))
                .isEqualTo(400);

        assertThat(response.jsonPath().getString("status"))
                .isEqualTo("BAD_REQUEST");

        assertThat(response.jsonPath().getBoolean("success"))
                .isFalse();

        assertThat(response.jsonPath().getList("errors"))
                .containsExactlyInAnyOrder(
                        "title is required",
                        "originalUrl is required"
                );

        assertThat(response.jsonPath().getObject("data", Object.class))
                .isNull();

        assertThat(response.jsonPath().getString("message"))
                .isNull();
    }
}
