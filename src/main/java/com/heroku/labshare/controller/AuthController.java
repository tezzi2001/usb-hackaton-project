package com.heroku.labshare.controller;

import com.heroku.labshare.json.UserJson;
import com.heroku.labshare.service.AuthService;
import com.heroku.labshare.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.heroku.labshare.constant.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody UserJson userJson) {
        authService.saveDto(userJson);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("authorization") String authorization) {
        String token = authorization.replace(TOKEN_PREFIX, "");
        authService.logout(token);
    }

    @GetMapping("/fetchUser")
    public UserJson fetchUser(@RequestHeader("authorization") String authorization) {
        String token = authorization.replace(TOKEN_PREFIX, "");
        return userService.getUserInfo(token);
    }
}
