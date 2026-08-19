package com.rm.ums.url.services;

import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.model.response.VisitUrlResponse;
import com.rm.ums.url.repositories.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class VisitUrlService {

    private final UrlRepository urlRepository;

    @Transactional
    public VisitUrlResponse visitUrl(String slug) {
        return urlRepository.findOriginalUrlBy(slug)
                .map(this::visit)
                .orElseGet(VisitUrlResponse::withUnknownSlugStatus);
    }

    private VisitUrlResponse visit(UrlEntity url) {
        if (url.isInActive()) {
            return VisitUrlResponse.withInactiveSlugStatus();
        }
        urlRepository.incrementViewCount(url.getId());
        return VisitUrlResponse.withValidSlugStatus(url);
    }


}
