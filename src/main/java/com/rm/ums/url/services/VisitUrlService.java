package com.rm.ums.url.services;

import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.enums.UrlExpirationStatusEnum;
import com.rm.ums.url.model.events.UrlVisitedEvent;
import com.rm.ums.url.model.response.VisitUrlResponse;
import com.rm.ums.url.repositories.UrlRepository;
import com.rm.ums.url.validater.UrlExpirationTimeValidator;
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
    private final UrlExpirationTimeValidator urlExpirationTimeValidator;

    public VisitUrlResponse visitUrl(String slug) {
        return urlRepo.findOriginalUrlBy(slug)
                .map(this::processVisitUrl)
                .orElseGet(VisitUrlResponse::withUnknownSlugStatus);
    }

    private VisitUrlResponse processVisitUrl(UrlEntity url) {
        if (url.isInActive()) {
            return VisitUrlResponse.withInactiveSlugStatus();
        }
        return switch (identifyAvailabilityOf(url)) {
            case NOT_AVAILABLE_YET -> VisitUrlResponse.withUrlNotAvailableYet();
            case EXPIRED -> VisitUrlResponse.withExpiredUrl();
            case AVAILABLE -> {
                publishVisitUrlEvent(url);
                yield VisitUrlResponse.withValidSlugStatus(url);
            }
        };
    }

    private UrlExpirationStatusEnum identifyAvailabilityOf(UrlEntity url) {
        return urlExpirationTimeValidator.canAccessStatus(url);
    }

    private void publishVisitUrlEvent(UrlEntity url) {
        eventPublisher.publishEvent(new UrlVisitedEvent(url.getId()));
    }
}
