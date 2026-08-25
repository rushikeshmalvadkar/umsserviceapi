package com.rm.ums.url.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;

@Data
public class CreateUrlResponse {
    private Long id;
    private String title;
    private String originalUrl;
    private String slug;
    private Long urlStatusId;
    private Long createdByUserId;
    private Instant createdOn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d MMM yyyy", timezone = "UTC")
    private Instant startAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d MMM yyyy", timezone = "UTC")
    private Instant expireAt;
}
