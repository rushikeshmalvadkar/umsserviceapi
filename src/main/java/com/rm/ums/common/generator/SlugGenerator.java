package com.rm.ums.common.generator;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SlugGenerator {

    public String generate() {
        return UUID.randomUUID().toString();
    }

}
