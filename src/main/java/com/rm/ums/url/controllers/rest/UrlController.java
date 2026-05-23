package com.rm.ums.url.controllers.rest;

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

    private final UrlService urlService;

    @GetMapping("/create-url-on-load")
    public ResponseEntity<CustomResponse> onLoadCreateUrl(
            @RequestHeader("userId") Long userId,
            @RequestHeader("roleId") Long roleId,
            @RequestHeader("device") String device
    ) {
        LoggedInUser loggedInUser = new LoggedInUser(userId, roleId, device);
        return ResponseEntity.ok(urlService.onLoadCreateUrl(loggedInUser));
    }

}
