package com.rm.ums.url.controllers.rest;

import com.rm.ums.common.entities.constants.HeaderConstant;
import com.rm.ums.common.model.response.CustomResponse;
import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.url.model.request.CreateUrlRequest;
import com.rm.ums.url.services.CreateUrlOnLoadService;
import com.rm.ums.url.services.CreateUrlService;
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
    private final CreateUrlOnLoadService createUrlOnLoadService;
    private final CreateUrlService createUrlService;

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

}
