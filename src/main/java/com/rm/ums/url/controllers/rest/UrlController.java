package com.rm.ums.url.controllers.rest;

import com.rm.ums.common.entities.constants.HeaderConstant;
import com.rm.ums.common.model.response.CustomResponse;
import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.url.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ums/urls")
@RequiredArgsConstructor
public class UrlController {

    private static final String ENDPOINT_CREATE_URL_ON_LOAD = "/create-url-on-load";
    private final UrlService urlService;

    @GetMapping(ENDPOINT_CREATE_URL_ON_LOAD)
    public ResponseEntity<CustomResponse> onLoadCreateUrl(
            @RequestHeader(HeaderConstant.USER_ID) Long userId,
            @RequestHeader(HeaderConstant.ROLE_ID) Long roleId,
            @RequestHeader(HeaderConstant.DEVICE) String device
    ) {
        LoggedInUser loggedInUser = LoggedInUser.of(userId, roleId, device);
        return ResponseEntity.ok(urlService.onLoadCreateUrl(loggedInUser));
    }

}
