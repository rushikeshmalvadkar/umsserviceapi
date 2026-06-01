package com.rm.ums.url.controllers.rest;

import com.rm.ums.assertions.SlugAssertions;
import com.rm.ums.common.AbstractIT;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql(
        scripts = {
                "/sql/cleanup/cleanup-urls.sql",
                "/sql/test-data/insert-urls.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class CheckSlugIT extends AbstractIT {

    private static final String ENDPOINT_CHECK_SLUG = "/urls/check-slug";


    @Test
    void should_return_slug_exists_when_slug_already_present() {

        Response response =
                umsRequest()
                        .queryParam("slug", "yt")
                        .when()
                        .get(ENDPOINT_CHECK_SLUG);

        response.then()
                .statusCode(200);

        SlugAssertions.assertSlugExists(response);
    }

    @Test
    void should_return_slug_not_exists_when_slug_is_available() {

        Response response =
                umsRequest()
                        .queryParam("slug", "NewSlug")
                        .when()
                        .get(ENDPOINT_CHECK_SLUG);

        response.then()
                .statusCode(200);

        SlugAssertions.assertSlugDoesNotExist(response);
    }


}
