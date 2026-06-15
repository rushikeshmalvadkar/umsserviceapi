package com.rm.ums.assertions;

import com.rm.ums.url.entities.UrlEntity;

import static org.assertj.core.api.Assertions.assertThat;

public final class UrlAssertions {

    public static void assertTitleUpdated(
            UrlEntity url,
            String expectedTitle
    ) {
        assertThat(url.getTitle())
                .isEqualTo(expectedTitle);
    }

    public static void assertSlugNotUpdated(
            UrlEntity url,
            String expectedSlug
    ) {
        assertThat(url.getSlug())
                .isEqualTo(expectedSlug);
    }
}
