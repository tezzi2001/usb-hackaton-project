package com.heroku.labshare.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroku.labshare.repository.UserRepository;
import com.heroku.labshare.security.filter.JWTAuthenticationFilter;
import com.heroku.labshare.security.filter.JWTAuthorizationFilter;
import com.heroku.labshare.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.heroku.labshare.constant.SecurityConstants.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper mapper;
    private final UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and().authorizeRequests()
            .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
            .antMatchers(HttpMethod.POST, SIGN_OUT_URL).permitAll()
            .antMatchers(HttpMethod.GET, FETCH_USER_URL).permitAll()
            .antMatchers(HttpMethod.POST, SAVE_TASK_URL).permitAll()
            .antMatchers(HttpMethod.GET, FACULTY_URL).permitAll()
            .antMatchers(HttpMethod.GET, SPECIALTY_URL).permitAll()
            .antMatchers(HttpMethod.GET, SUBJECT_URL).permitAll()
            .antMatchers(HttpMethod.GET, DOWNLOAD_LINK_URL).permitAll()
            .antMatchers(HttpMethod.POST, SEARCH_URL).permitAll()
            .antMatchers(HttpMethod.POST, LIKE_URL).permitAll()
            .antMatchers(HttpMethod.GET, FETCH_TASK_URL).permitAll()
            .antMatchers(HttpMethod.GET, ADVANCED_SEARCH_URL).permitAll()
            .antMatchers(HttpMethod.POST, CRAWLER_URL).permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(new JWTAuthenticationFilter(authenticationManager(), mapper, userRepository))
            .addFilter(new JWTAuthorizationFilter(authenticationManager(), authService, userRepository))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
