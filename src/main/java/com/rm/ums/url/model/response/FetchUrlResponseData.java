package com.rm.ums.url.model.response;

import lombok.Data;

import java.time.Instant;

@Data
public class FetchUrlResponseData {

    private Long id;
    private String title;
    private String originalUrl;
    private String slug;
    private Instant createdOn;

}
