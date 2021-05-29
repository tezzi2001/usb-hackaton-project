package com.heroku.labshare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroku.labshare.dto.UserJson;
import com.heroku.labshare.json.wrapper.UserJsonWrapper;
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
    private final ObjectMapper mapper;

    @PostMapping("/register")
    @SneakyThrows
    public void register(@RequestBody String json) {
        UserJsonWrapper userJsonWrapper = mapper.readValue(json, UserJsonWrapper.class);
        authService.saveDto(userJsonWrapper.getUserJson());
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("authorization") String authorization) {
        String token = authorization.replace(TOKEN_PREFIX, "");
        authService.logout(token);
    }
}
