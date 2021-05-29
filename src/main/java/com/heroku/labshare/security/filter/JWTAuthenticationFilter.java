package com.heroku.labshare.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroku.labshare.dto.UserJson;
import com.heroku.labshare.json.wrapper.TokenWrapper;
import com.heroku.labshare.json.wrapper.UserJsonWrapper;
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

import static com.heroku.labshare.constant.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper mapper;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.mapper = mapper;
        setFilterProcessesUrl(SIGN_IN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        try {
            UserJsonWrapper userJsonWrapper = mapper.readValue(req.getInputStream(), UserJsonWrapper.class);
            UserJson user = userJsonWrapper.getUserJson();

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
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


        res.getWriter().write(mapper.writeValueAsString(new TokenWrapper(token)));
        res.getWriter().flush();
    }
}
