package com.rm.ums.url.services;

import com.rm.ums.common.model.response.CustomResponse;
import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.common.repositories.UserRepository;
import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.generator.SlugGenerator;
import com.rm.ums.url.mapper.UrlMapper;
import com.rm.ums.url.model.request.CreateUrlRequest;
import com.rm.ums.url.repositories.UrlRepository;
import com.rm.ums.url.validater.UrlExpirationTimeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.rm.ums.common.enums.UmsResponseMessageEnum.CREATED_SUCCESSFULLY;
import static com.rm.ums.url.enums.UrlStatusEnum.ACTIVE;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CreateUrlService {

    private final UrlMapper urlMapper;
    private final SlugGenerator slugGenerator;
    private final UrlRepository urlRepository;
    private final UserRepository userRepository;
    private final UrlExpirationTimeValidator urlExpirationTimeValidator;

    @Transactional
    public CustomResponse create(LoggedInUser loggedInUser, CreateUrlRequest createUrlRequest) {
        UrlEntity savedUrlEntity = saveUrl(createUrlRequest, loggedInUser);
        return CustomResponse.created(urlMapper.toCreateUrlResponse(savedUrlEntity), CREATED_SUCCESSFULLY);

    }

    private UrlEntity saveUrl(CreateUrlRequest createUrlRequest, LoggedInUser loggedInUser) {
        validateExpiryDates(createUrlRequest);
        return savedUrl(createUrlRequest, loggedInUser);
    }

    private UrlEntity savedUrl(CreateUrlRequest createUrlRequest, LoggedInUser loggedInUser) {
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl(createUrlRequest.originalUrl());
        urlEntity.setTitle(createUrlRequest.title());
        urlEntity.setCreatedBy(userRepository.getReferenceById(loggedInUser.userId()));
        urlEntity.setUrlStatusId(ACTIVE.id());
        urlEntity.setSlug(prepareSlug(createUrlRequest));
        processUrlExpirationIfUserHasGiven(createUrlRequest, urlEntity);
        UrlEntity savedUrlEntity = urlRepository.save(urlEntity);
        log.debug("Created new url with id :: {} and slug :: {}", savedUrlEntity.getId(), savedUrlEntity.getSlug());
        return savedUrlEntity;
    }

    private void validateExpiryDates(CreateUrlRequest createUrlRequest) {
        urlExpirationTimeValidator.validate(createUrlRequest.startAt(), createUrlRequest.expireAt());
    }

    private static void processUrlExpirationIfUserHasGiven(CreateUrlRequest createUrlRequest, UrlEntity urlEntity) {
        if (createUrlRequest.userIsCreatingExpiryBasedUrl()) {
            urlEntity.setExpiryDates(createUrlRequest.startAt(), createUrlRequest.expireAt());
        }
    }

    private String prepareSlug(CreateUrlRequest createUrlRequest) {
        if (createUrlRequest.hasSlug()) {
            log.debug("User has given slug so using that one");
            return createUrlRequest.slug();
        }
        log.debug("User has not given slug so generating new one");
        return slugGenerator.generate();

    }
}
