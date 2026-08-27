package com.rm.ums.url.model.response;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class CreateUrlResponse {
    private Long id;
    private String title;
    private String originalUrl;
    private String slug;
    private Long urlStatusId;
    private Long createdByUserId;
    private Instant createdOn;
    private LocalDate startAt;
    private LocalDate expireAt;
}
