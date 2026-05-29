package com.rm.ums.url.model.response;

import com.rm.ums.common.model.response.HeaderResponse;
import lombok.Data;

import java.util.List;

@Data
public class FetchUrlResponse {
    private   List<HeaderResponse> headers;
    private  List<FetchUrlResponseData> data;
}
