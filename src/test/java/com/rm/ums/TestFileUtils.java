package com.rm.ums;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TestFileUtils {

    private TestFileUtils() {
    }

    public static String readFile(String path) throws IOException {
        return new ClassPathResource(path)
                .getContentAsString(StandardCharsets.UTF_8);
    }

}
