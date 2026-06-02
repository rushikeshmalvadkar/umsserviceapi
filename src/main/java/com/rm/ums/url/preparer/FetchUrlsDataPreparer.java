package com.rm.ums.url.preparer;

import com.rm.ums.auth.repositories.UrlRepository;
import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.mapper.UrlMapper;
import com.rm.ums.url.model.request.FetchUrlsRequest;
import com.rm.ums.url.model.response.FetchUrlsDataResponse;
import com.rm.ums.url.specification.UrlSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FetchUrlsDataPreparer {
    private final UrlRepository urlRepo;
    private final UrlMapper urlMapper;

    public List<FetchUrlsDataResponse> prepare(FetchUrlsRequest fetchUrlsRequest, LoggedInUser loggedInUser) {
        List<UrlEntity> urlEntities = urlRepo.findAll(UrlSpecification.byFilter(fetchUrlsRequest, loggedInUser));
        log.debug("Total URLs :: {}", urlEntities.size());
        return urlMapper.toFetchUrlDataResponseList(urlEntities);
    }
}
