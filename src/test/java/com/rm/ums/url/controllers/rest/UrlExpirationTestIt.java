package com.rm.ums.url.controllers.rest;

import com.rm.ums.common.AbstractIT;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static com.rm.ums.url.enums.VisitUrlStatusEnum.URL_EXPIRED;
import static org.assertj.core.api.Assertions.assertThat;

@Sql(
        scripts = "/sql/test-data/insert-urls.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class UrlExpirationTestIt extends AbstractIT {

    public static final String ENDPOINT_VISIT_URL = "/urls/visit-url/{slug}";

    @Test
    void should_show_error_message_when_url_expired() {
        String response =
                umsRequestWithoutHeader()
                        .pathParam("slug", "oa")
                        .when()
                        .get(ENDPOINT_VISIT_URL)
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();
        assertThat(response).contains(URL_EXPIRED.message());
    }

}
