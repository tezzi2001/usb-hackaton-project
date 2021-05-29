package com.heroku.labshare.service;

import com.auth0.jwt.JWT;
import com.heroku.labshare.exception.EntityNotFoundException;
import com.heroku.labshare.json.UserJson;
import com.heroku.labshare.model.Role;
import com.heroku.labshare.model.User;
import com.heroku.labshare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserJson getUserInfo(String token) {
        String username = JWT.decode(token).getSubject();
        return new UserJson(userRepository
                .findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(User.class))
        );
    }

    public boolean isUserApprovedById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, id));
        return user.getRole() == Role.APPROVED_USER;
    }
}
