package com.rm.ums.url.services;

import com.rm.ums.auth.repositories.UrlRepository;
import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.model.response.UrlVisitResponse;
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

    public UrlVisitResponse visit(String slug) {
        return urlRepository.findOriginalUrlBySlug(slug).
                map(this::processVistaUrl)
                .orElseGet(UrlVisitResponse::notFound);

    }

    private UrlVisitResponse processVistaUrl(UrlEntity entity) {
        return UrlVisitResponse.success(entity.getOriginalUrl());
    }


}
