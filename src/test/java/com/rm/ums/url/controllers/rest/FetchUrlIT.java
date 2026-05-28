package com.rm.ums.url.controllers.rest;

import com.rm.ums.common.AbstractIT;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql(
        scripts = "/sql/insert/insert-urls.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class FetchUrlIT extends AbstractIT {

    @Test
    void should_return_fetch_urls_response() {



    }
}
