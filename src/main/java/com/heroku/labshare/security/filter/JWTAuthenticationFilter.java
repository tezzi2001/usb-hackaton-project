package com.heroku.labshare.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroku.labshare.json.UserJson;
import com.heroku.labshare.json.wrapper.TokenWithUserWrapper;
import com.heroku.labshare.model.Role;
import com.heroku.labshare.model.User;
import com.heroku.labshare.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static com.heroku.labshare.constant.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper mapper;
    private final UserRepository userRepository;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper mapper, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.mapper = mapper;
        this.userRepository = userRepository;
        setFilterProcessesUrl(SIGN_IN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        try {
            UserJson user = mapper.readValue(req.getInputStream(), UserJson.class);

            String username = userRepository.findByEmail(user.getEmail()).get().getUsername(); //TODO: fix
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            user.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new AuthenticationException("", e) {
            };
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        String token =
                JWT.create()
                .withSubject(username)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC512(SECRET.getBytes()));


        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.get();
        TokenWithUserWrapper tokenWithUserWrapper = TokenWithUserWrapper.builder()
                .token(token)
                .email(user.getEmail())
                .username(user.getUsername())
                .faculty(user.getFaculty())
                .specialty(user.getSpecialty())
                .userId(user.getId())
                .isApproved(Role.APPROVED_USER == user.getRole())
                .build();
        res.getWriter().write(mapper.writeValueAsString(tokenWithUserWrapper));
        res.getWriter().flush();
    }
}
