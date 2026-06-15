package com.rm.ums.assertions;

import com.rm.ums.TestFileUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class JsonAssertions {
    private JsonAssertions() {
    }

    public static void assertJsonEquals(
            String expectedResponseFile,
            String actualJson
    ) throws Exception {

        String expectedJson =
                TestFileUtils.readFile(expectedResponseFile);

        JSONAssert.assertEquals(
                expectedJson,
                actualJson,
                JSONCompareMode.STRICT
        );
    }
}
