package com.rm.ums.url.controllers.rest;

import com.rm.ums.common.entities.constants.HeaderConstant;
import com.rm.ums.common.model.response.CustomResponse;
import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.url.model.request.CreateUrlRequest;
import com.rm.ums.url.model.request.FetchUrlsRequest;
import com.rm.ums.url.services.CheckSlugService;
import com.rm.ums.url.services.CreateUrlOnLoadService;
import com.rm.ums.url.services.CreateUrlService;
import com.rm.ums.url.services.FetchUrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ums/urls")
@RequiredArgsConstructor
public class UrlController {

    private static final String ENDPOINT_CREATE_URL_ON_LOAD = "/create-url-on-load";
    public static final String ENDPOINT_CREATE_URL = "/create-url";
    public static final String ENDPOINT_FETCH_URLS = "/fetch-urls";
    private static final String ENDPOINT_CHECK_SLUG = "/check-slug";
    private final CreateUrlOnLoadService createUrlOnLoadService;
    private final CreateUrlService createUrlService;
    private final FetchUrlService fetchUrlService;
    private final CheckSlugService checkSlugService;



    @GetMapping(ENDPOINT_CREATE_URL_ON_LOAD)
    public ResponseEntity<CustomResponse> onLoadCreateUrl(
            @RequestHeader(HeaderConstant.USER_ID) Long userId,
            @RequestHeader(HeaderConstant.ROLE_ID) Long roleId,
            @RequestHeader(HeaderConstant.DEVICE) String device
    ) {
        LoggedInUser loggedInUser = LoggedInUser.of(userId, roleId, device);
        return ResponseEntity.ok(createUrlOnLoadService.onLoad(loggedInUser));
    }

    @PostMapping(ENDPOINT_CREATE_URL)
    public ResponseEntity<CustomResponse> createUrl(
            @RequestHeader(HeaderConstant.USER_ID) Long userId,
            @RequestHeader(HeaderConstant.ROLE_ID) Long roleId,
            @RequestHeader(HeaderConstant.DEVICE) String device,
            @Valid @RequestBody CreateUrlRequest createUrlRequest
    ) {
        LoggedInUser loggedInUser = LoggedInUser.of(userId, roleId, device);
        return ResponseEntity.ok(createUrlService.create(loggedInUser, createUrlRequest));

    }

    @PostMapping(ENDPOINT_FETCH_URLS)
    public ResponseEntity<CustomResponse> fetchUrls(
            @RequestHeader(HeaderConstant.USER_ID) Long userId,
            @RequestHeader(HeaderConstant.ROLE_ID) Long roleId,
            @RequestHeader(HeaderConstant.DEVICE) String device,
            @RequestBody FetchUrlsRequest fetchUrlsRequest
            ) {
        LoggedInUser loggedInUser = LoggedInUser.of(userId, roleId, device);
        return ResponseEntity.ok(fetchUrlService.fetch(loggedInUser, fetchUrlsRequest));

    }

    @GetMapping(ENDPOINT_CHECK_SLUG)
    public ResponseEntity<CustomResponse> checkSlug(@RequestParam("slug") String slug) {
        return ResponseEntity.ok(checkSlugService.check(slug));
    }


}
