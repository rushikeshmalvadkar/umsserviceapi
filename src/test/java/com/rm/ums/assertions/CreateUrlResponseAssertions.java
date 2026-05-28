package com.rm.ums.assertions;

import io.restassured.response.Response;

import static java.time.Instant.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class CreateUrlResponseAssertions {

    private CreateUrlResponseAssertions() {
    }

    public static void assertCreateUrlResponseWithCustomSlug(
            Response response,
            String expectedTitle,
            String expectedOriginalUrl,
            String expectedSlug,
            Long expectedCreatedByUserId
    ) {

        assertCommonCreateUrlResponse(
                response,
                expectedTitle,
                expectedOriginalUrl,
                expectedCreatedByUserId
        );

        String actualSlug =
                response.jsonPath().getString("data.slug");

        assertThat(actualSlug)
                .isNotBlank();

        assertThat(actualSlug)
                .isEqualTo(expectedSlug);
    }

    public static void assertCreateUrlResponseWithGeneratedSlug(
            Response response,
            String expectedTitle,
            String expectedOriginalUrl,
            Long expectedCreatedByUserId
    ) {

        assertCommonCreateUrlResponse(
                response,
                expectedTitle,
                expectedOriginalUrl,
                expectedCreatedByUserId
        );

        String generatedSlug =
                response.jsonPath().getString("data.slug");

        assertThat(generatedSlug)
                .isNotBlank();

    }

    private static void assertCommonCreateUrlResponse(
            Response response,
            String expectedTitle,
            String expectedOriginalUrl,
            Long expectedCreatedByUserId
    ) {

        assertThat(response.jsonPath().getInt("code"))
                .isEqualTo(201);

        assertThat(response.jsonPath().getString("status"))
                .isEqualTo("CREATED");

        assertThat(response.jsonPath().getString("message"))
                .isEqualTo("Created successfully");

        assertThat(response.jsonPath().getLong("data.id"))
                .isPositive();

        assertThat(response.jsonPath().getString("data.title"))
                .isEqualTo(expectedTitle);

        assertThat(response.jsonPath().getString("data.originalUrl"))
                .isEqualTo(expectedOriginalUrl);

        assertThat(response.jsonPath().getLong("data.urlStatusId"))
                .isEqualTo(1L);

        assertThat(response.jsonPath().getLong("data.createdByUserId"))
                .isEqualTo(expectedCreatedByUserId);

        String createdOn =
                response.jsonPath().getString("data.createdOn");

        assertThat(createdOn)
                .isNotBlank();

        assertThatCode(() -> parse(createdOn))
                .doesNotThrowAnyException();
    }
}
