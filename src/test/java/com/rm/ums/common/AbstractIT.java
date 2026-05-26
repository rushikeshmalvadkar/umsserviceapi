package com.rm.ums.common;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("it")
public class AbstractIT {

    protected static final String CUSTOMER_ROLE_ID = "3";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {

        RestAssured.port = port;
        RestAssured.basePath = "/api/ums";
    }

    protected RequestSpecification umsRequest() {
        return RestAssured
                .given()
                .header("userid", "1")
                .header("device", "web")
                .contentType(ContentType.JSON);
    }

}
