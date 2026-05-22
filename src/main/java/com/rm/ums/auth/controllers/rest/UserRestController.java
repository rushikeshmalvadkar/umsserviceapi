package com.rm.ums.auth.controllers.rest;

import com.rm.ums.auth.services.UserService;
import com.rm.ums.common.model.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/sign-in")
    public ResponseEntity<CustomResponse> signIn() {
        return ResponseEntity.ok(userService.signIn());
    }
}
