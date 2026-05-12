package com.rm.ums;

import org.springframework.boot.SpringApplication;

public class TestUmsserviceapiApplication {

    public static void main(String[] args) {
        SpringApplication.from(UmsserviceapiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
