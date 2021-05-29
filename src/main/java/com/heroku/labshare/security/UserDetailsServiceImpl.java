package com.heroku.labshare.security;

import com.heroku.labshare.model.User;
import com.heroku.labshare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) {
        User user = userRepository
                .findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(s));
        return new SecurityUser(user);
    }
}
