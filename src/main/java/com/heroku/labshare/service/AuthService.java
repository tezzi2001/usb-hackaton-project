package com.heroku.labshare.service;

import com.auth0.jwt.JWT;
import com.heroku.labshare.json.UserJson;
import com.heroku.labshare.model.JwtBlacklist;
import com.heroku.labshare.model.Role;
import com.heroku.labshare.model.User;
import com.heroku.labshare.repository.JwtBlacklistRepository;
import com.heroku.labshare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static com.heroku.labshare.util.DateUtils.dateToLocalDate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtBlacklistRepository jwtBlacklistRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void saveDto(UserJson userJson) {
        Optional<User> optionalUser = userRepository.findByUsername(userJson.getUsername());

        if (!optionalUser.isPresent()) {
            String encodedPassword = passwordEncoder.encode(userJson.getPassword());

            // TODO: refactor
            User user = User.builder()
                    .username(userJson.getUsername())
                    .password(encodedPassword)
                    .email(userJson.getEmail())
                    .faculty(userJson.getFaculty())
                    .specialty(userJson.getSpecialty())
                    .role(Role.NON_APPROVED_USER)
                    .build();
            userRepository.save(user);
        }
    }

    public void logout(String token) {
        String signature = JWT.decode(token).getSignature();
        Date expiresAt = JWT.decode(token).getExpiresAt();

        JwtBlacklist jwtBlacklist = JwtBlacklist.builder()
                .signature(signature)
                .expiresAt(dateToLocalDate(expiresAt))
                .build();
        jwtBlacklistRepository.save(jwtBlacklist);
    }

    public boolean canAuthorize(String token) {
        String signature = JWT.decode(token).getSignature();
        return jwtBlacklistRepository.existsBySignature(signature);
    }
}
