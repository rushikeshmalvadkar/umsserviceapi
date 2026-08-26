package com.rm.ums.url.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;

@Data
public class FetchUrlsDataResponse {
    private Long id;
    private String title;
    private String originalUrl;
    private String slug;
    private Long urlStatusId;
    private String urlStatusName;
    private Long viewCount;
    private Instant createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d MMMM yyyy", timezone = "UTC")
    private Instant startAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d MMMM yyyy", timezone = "UTC")
    private Instant expireAt;
}
