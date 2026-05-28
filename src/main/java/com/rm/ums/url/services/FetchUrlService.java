package com.rm.ums.url.services;

import com.rm.ums.auth.repositories.UrlRepository;
import com.rm.ums.common.enums.UmsResponseMessageEnum;
import com.rm.ums.common.helpers.HeaderHelper;
import com.rm.ums.common.model.response.CustomResponse;
import com.rm.ums.common.model.response.HeaderResponse;
import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.mapper.UrlMapper;
import com.rm.ums.url.model.request.FetchUrlsRequest;
import com.rm.ums.url.model.response.FetchUrlResponse;
import com.rm.ums.url.model.response.FetchUrlResponseData;
import com.rm.ums.url.specification.UrlSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.rm.ums.common.enums.MenuEnum.MY_SHORT_URLS;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FetchUrlService {

    private final HeaderHelper headerHelper;
    private final UrlRepository urlRepository;
    private final UrlMapper urlMapper;

    public CustomResponse fetch(LoggedInUser loggedInUser, FetchUrlsRequest fetchUrlsRequest) {
        List<HeaderResponse> headers = headerHelper.findHeaders(loggedInUser, MY_SHORT_URLS.id());
        List<FetchUrlResponseData> fetchUrlResponseDataList = prepareFetchUrlResponseData(loggedInUser, fetchUrlsRequest);
        return CustomResponse.success(FetchUrlResponse.from(headers, fetchUrlResponseDataList), UmsResponseMessageEnum.FETCHED_SUCCESSFULLY);
    }

    private List<FetchUrlResponseData> prepareFetchUrlResponseData(LoggedInUser loggedInUser, FetchUrlsRequest fetchUrlsRequest) {
        var urlEntitySpecification = UrlSpecification.fetchUrls(fetchUrlsRequest, loggedInUser);
        List<UrlEntity> urls = urlRepository.findAll(urlEntitySpecification);
        return urlMapper.toFetchUrlResponseData(urls);
    }
}
