package com.rm.ums.url.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class FetchUrlResponseData {

    private Long id;
    private String title;
    private String originalUrl;
    private String slug;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime createdDate;

}
