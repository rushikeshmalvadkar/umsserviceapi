package com.rm.ums.url.model.response;

import com.rm.ums.common.model.response.HeaderResponse;
import lombok.Data;

import java.util.List;

@Data
public class FetchUrlResponse {
    private  final List<HeaderResponse> headers;
    private final List<FetchUrlResponseData> data;

    private FetchUrlResponse(List<HeaderResponse> headers, List<FetchUrlResponseData> data) {
        this.headers = headers;
        this.data = data;
    }

    public static FetchUrlResponse from(List<HeaderResponse> headers, List<FetchUrlResponseData> data) {
        return new FetchUrlResponse(headers, data);
    }
}
