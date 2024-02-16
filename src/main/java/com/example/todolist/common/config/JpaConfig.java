package com.example.todolist.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Configuration(proxyBeanMethods = false)
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var userDetails = authentication.getPrincipal() instanceof UserDetails ?
                    (UserDetails) authentication.getPrincipal() : null;
            var username = userDetails != null ? userDetails.getUsername() : DEFAULT_CREATED_BY;
            return Optional.ofNullable(username);
        };
    }

}