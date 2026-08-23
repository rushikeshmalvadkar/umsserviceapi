package com.rm.ums.url.services;

import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.model.events.UrlVisitedEvent;
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

    private final UrlRepository urlRepo;
    private final ApplicationEventPublisher eventPublisher;

    public VisitUrlResponse visitUrl(String slug) {
        return urlRepo.findOriginalUrlBy(slug)
                .map(this::processVisitUrl)
                .orElseGet(VisitUrlResponse::withUnknownSlugStatus);
    }

    private VisitUrlResponse processVisitUrl(UrlEntity url) {
        if (url.isInActive()) {
            return VisitUrlResponse.withInactiveSlugStatus();
        }
        publishVisitUrlEvent(url);
        return VisitUrlResponse.withValidSlugStatus(url);
    }

    private void publishVisitUrlEvent(UrlEntity url) {
        eventPublisher.publishEvent(new UrlVisitedEvent(url.getId()));
    }
}
