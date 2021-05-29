package com.heroku.labshare.service;

import com.auth0.jwt.JWT;
import com.heroku.labshare.dto.UserJson;
import com.heroku.labshare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserJson getUserInfo(String token) {
        String username = JWT.decode(token).getSubject();
        return new UserJson(userRepository.findByUsername(username).orElseThrow());
    }
}
