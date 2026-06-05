package com.rm.ums.url.services;

import com.rm.ums.common.helpers.HeaderHelper;
import com.rm.ums.common.model.response.CustomResponse;
import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.url.model.request.FetchUrlsRequest;
import com.rm.ums.url.model.response.FetchUrlsResponse;
import com.rm.ums.url.preparer.FetchUrlsDataPreparer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.rm.ums.common.enums.MenuEnum.MY_SHORT_URLS;
import static com.rm.ums.common.enums.UmsResponseMessageEnum.FETCHED_SUCCESSFULLY;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FetchUrlsService {

    private final HeaderHelper headerHelper;
    private final FetchUrlsDataPreparer fetchUrlsDataPreparer;

    public CustomResponse fetchUrls(FetchUrlsRequest fetchUrlsRequest, LoggedInUser loggedInUser) {
        FetchUrlsResponse fetchUrlsResponse = new FetchUrlsResponse();
        fetchUrlsResponse.setHeaders(headerHelper.findHeaders(loggedInUser, MY_SHORT_URLS.id()));
        fetchUrlsResponse.setData(fetchUrlsDataPreparer.prepare(fetchUrlsRequest, loggedInUser));
        System.out.println(fetchUrlsResponse);
        return CustomResponse.success(fetchUrlsResponse, FETCHED_SUCCESSFULLY);
    }
}
