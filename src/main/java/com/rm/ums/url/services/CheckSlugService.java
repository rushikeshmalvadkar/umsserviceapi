package com.rm.ums.url.services;

import com.rm.ums.auth.repositories.UrlRepository;
import com.rm.ums.common.model.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.rm.ums.common.enums.UmsResponseMessageEnum.CHECKED_SUCCESSFULLY;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckSlugService {

    private final UrlRepository urlRepository;

    public CustomResponse check(String slug) {
        return CustomResponse.success(
                Map.of("slugAlreadyExists", urlRepository.existsBySlug(slug.trim())),
                CHECKED_SUCCESSFULLY
        );
    }
}
