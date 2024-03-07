package com.example.todolist.common.config;

import com.example.todolist.common.jwt.JwtTokenProvider;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

// 테스트 시 JWT 인증과정을 무시하기 위한 테스트용 시큐리티 config
@TestConfiguration
public class TestSecurityConfig {

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) ->
                            authorizeRequests.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                            .anyRequest().permitAll());
        return http.build();
    }
}
