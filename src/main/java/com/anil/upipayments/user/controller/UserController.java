package com.anil.upipayments.user.controller;

import com.anil.upipayments.user.dto.UserProfileResponse;
import com.anil.upipayments.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/me")
    public UserProfileResponse getMyProfile(Authentication authentication) {

        String mobile = authentication.getName(); // from JWT
        return userService.getProfile(mobile);
    }
}
