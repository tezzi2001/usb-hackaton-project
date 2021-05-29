package com.heroku.labshare.controller;

import com.heroku.labshare.dto.UserJson;
import com.heroku.labshare.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import static com.heroku.labshare.constant.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @SneakyThrows
    public void register(@RequestBody UserJson userJson) {
        authService.saveDto(userJson);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("authorization") String authorization) {
        String token = authorization.replace(TOKEN_PREFIX, "");
        authService.logout(token);
    }
}
