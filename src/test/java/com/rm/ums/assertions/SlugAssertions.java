package com.rm.ums.assertions;

import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class SlugAssertions {

    public static void assertSlugExists(Response response) {

        assertCommonResponse(response);

        assertThat(
                response.jsonPath()
                        .getBoolean("data.slugAlreadyExists")
        ).isTrue();

    }

    public static void assertSlugDoesNotExist(Response response) {

        assertCommonResponse(response);

        assertThat(
                response.jsonPath()
                        .getBoolean("data.slugAlreadyExists")
        ).isFalse();
    }

    private static void assertCommonResponse(Response response) {

        assertThat(response.jsonPath().getInt("code"))
                .isEqualTo(200);

        assertThat(response.jsonPath().getString("status"))
                .isEqualTo("SUCCESS");

        assertThat(response.jsonPath().getBoolean("success"))
                .isTrue();

        assertThat(response.jsonPath().getString("message"))
                .isEqualTo("Checked successfully");
    }

}
