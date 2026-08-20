package com.rm.ums.url.services;

import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.model.eventRequst.UrlVisitedEventRequest;
import com.rm.ums.url.model.response.VisitUrlResponse;
import com.rm.ums.url.repositories.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VisitUrlService {

    private final UrlRepository urlRepository;
    private final UrlViewCounter urlViewCounter;
    private final ApplicationEventPublisher eventPublisher;

    public VisitUrlResponse visitUrl(String slug) {
        return urlRepository.findOriginalUrlBy(slug)
                .map(this::toResponseAndIncreaseViewCount)
                .orElseGet(VisitUrlResponse::withUnknownSlugStatus);
    }

    private VisitUrlResponse toResponseAndIncreaseViewCount(UrlEntity url) {
        if (url.isInActive()) {
            return VisitUrlResponse.withInactiveSlugStatus();
        }

         eventPublisher.publishEvent(new UrlVisitedEventRequest(url.getId()));
        return VisitUrlResponse.withValidSlugStatus(url);
    }
}
