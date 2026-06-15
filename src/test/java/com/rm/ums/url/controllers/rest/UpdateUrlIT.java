package com.rm.ums.url.controllers.rest;

import com.rm.ums.TestFileUtils;
import com.rm.ums.assertions.JsonAssertions;
import com.rm.ums.common.AbstractIT;
import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.repositories.UrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@Sql(
        scripts = {
                "/sql/cleanup/cleanup-urls.sql",
                "/sql/test-data/update-url.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class UpdateUrlIT extends AbstractIT {
    private static final String ENDPOINT_UPDATE_URL = "/urls/update-url";

    private static final String UPDATE_TITLE_REQUEST_JSON =
            "requests/update-url/update-title-request.json";

    private static final String UPDATE_SLUG_REQUEST_JSON =
            "requests/update-url/update-slug-request.json";

    private static final String UPDATE_SUCCESS_RESPONSE_JSON =
            "responses/update-url/update-success-response.json";

    private static final String UPDATE_PERMISSION_DENIED_RESPONSE_JSON =
            "responses/update-url/update-permission-denied-response.json";

    @Autowired
    private UrlRepository urlRepo;

    @Test
    void should_update_title_when_column_is_editable() throws Exception {

        String request =
                TestFileUtils.readFile(
                        UPDATE_TITLE_REQUEST_JSON
                );

        String actualResponse =
                umsRequest()
                        .header(REQUEST_HEADER_ROLE_ID_KEY, CUSTOMER_ROLE_ID)
                        .body(request)
                        .when()
                        .patch(ENDPOINT_UPDATE_URL)
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        JsonAssertions.assertJsonEquals(
                UPDATE_SUCCESS_RESPONSE_JSON,
                actualResponse
        );

        UrlEntity url =
                urlRepo.findByIdOrThrow(1L);

        assertThat(url.getTitle())
                .isEqualTo("Database Exploration Checklist Updated");
    }
    @Test
    void should_return_permission_denied_when_column_is_not_editable() throws Exception {

        String request =
                TestFileUtils.readFile(
                        UPDATE_SLUG_REQUEST_JSON
                );

        String actualResponse =
                umsRequest()
                        .header(REQUEST_HEADER_ROLE_ID_KEY, CUSTOMER_ROLE_ID)
                        .body(request)
                        .when()
                        .patch(ENDPOINT_UPDATE_URL)
                        .then()
                        .statusCode(403)
                        .extract()
                        .asString();

        JsonAssertions.assertJsonEquals(
                UPDATE_PERMISSION_DENIED_RESPONSE_JSON,
                actualResponse
        );

        UrlEntity url =
                urlRepo.findByIdOrThrow(1L);

        assertThat(url.getSlug())
                .isEqualTo("DbExploration");
    }

}
