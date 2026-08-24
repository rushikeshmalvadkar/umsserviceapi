package com.rm.ums.url.counter;

import com.rm.ums.url.repositories.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UrlViewCounter {

    private final UrlRepository urlRepo;

    @Transactional
    public void incrementViewCount(Long urlId) {
        urlRepo.incrementViewCount(urlId);
    }
}
