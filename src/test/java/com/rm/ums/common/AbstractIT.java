package com.rm.ums.common;

import com.rm.ums.TestcontainersConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("it")
@Import(TestcontainersConfiguration.class)
public class AbstractIT {


    protected static final String REQUEST_HEADER_ROLE_ID_KEY = "roleid";
    protected static final String REQUEST_HEADER_USER_ID_KEY = "userid";
    protected static final String REQUEST_HEADER_DEVICE_KEY = "device";

    protected static final String TEST_CUSTOMER_ROLE_USER_ID = "1";
    protected static final String CUSTOMER_ROLE_ID = "3";
    protected static final String DEVICE_WEB = "web";
    protected static final String API_BASE_PATH = "/api/ums";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {

        RestAssured.port = port;
        RestAssured.basePath = API_BASE_PATH;
    }

    protected RequestSpecification umsRequest() {
        return RestAssured
                .given()
                .header(REQUEST_HEADER_USER_ID_KEY, TEST_CUSTOMER_ROLE_USER_ID)
                .header(REQUEST_HEADER_DEVICE_KEY, DEVICE_WEB)
                .header(REQUEST_HEADER_USER_ID_KEY, TEST_CUSTOMER_ROLE_USER_ID)
                .contentType(ContentType.JSON);
    }

    protected RequestSpecification umsRequestWithoutHeader() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON);
    }

}
