package com.rm.ums.url.services;

import com.rm.ums.auth.repositories.UrlRepository;
import com.rm.ums.common.generator.SlugGenerator;
import com.rm.ums.common.model.response.CustomResponse;
import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.common.repositories.UrlStatusRepository;
import com.rm.ums.common.repositories.UserRepository;
import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.mapper.UrlMapper;
import com.rm.ums.url.model.request.CreateUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.rm.ums.common.enums.UmsResponseMessageEnum.CREATED_SUCCESSFULLY;
import static com.rm.ums.url.enums.UrlStatusEnum.ACTIVE;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreateUrlService {

    private final UrlMapper urlMapper;
    private final SlugGenerator slugGenerator;
    private final UrlRepository urlRepository;
    private final UserRepository userRepository;
    private final UrlStatusRepository urlStatusRepository;


    @Transactional
    public CustomResponse create(LoggedInUser loggedInUser, CreateUrlRequest createUrlRequest) {
        UrlEntity urlEntity = prepareUrlEntity(createUrlRequest, loggedInUser);
        UrlEntity savedUrl = urlRepository.save(urlEntity);
        return CustomResponse.created(savedUrl.getId(), CREATED_SUCCESSFULLY);

    }

    private UrlEntity prepareUrlEntity(CreateUrlRequest createUrlRequest, LoggedInUser loggedInUser) {
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl(createUrlRequest.originalUrl());
        urlEntity.setTitle(createUrlRequest.title());
        urlEntity.setCreatedBy(userRepository.getReferenceById(loggedInUser.userId()));
        urlEntity.setUrlStatus(urlStatusRepository.getReferenceById(ACTIVE.id()));
        urlEntity.setSlug(prepareSlug(createUrlRequest));
        return urlEntity;

    }

    private String prepareSlug(CreateUrlRequest createUrlRequest) {
        if (isNull(createUrlRequest.slug())) {
            return slugGenerator.generate();
        }
        return createUrlRequest.slug();
    }
}
