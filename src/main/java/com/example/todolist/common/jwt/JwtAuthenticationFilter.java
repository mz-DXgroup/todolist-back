package com.example.todolist.common.jwt;

import com.example.todolist.common.config.SecurityConfig;
import com.example.todolist.member.domain.dto.MemberDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final List<AntPathRequestMatcher> apr;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.apr = new ArrayList<>();
        for(String url: SecurityConfig.ALLOWS_URLS) {
            apr.add(new AntPathRequestMatcher(url));
        }
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        log.info("allowed url - {}", apr.toString());
        if(apr.stream().noneMatch(a -> {
            log.trace("{} - {} : {}", a.getPattern(), request.getRequestURI(), a.matches(request));
            return a.matches(request);
        })) {
            String jws = getJwtFromRequest(request);

            log.info(":::::::::::::::::::; jws - {}", jws);

            MemberDto result = jwtTokenProvider.validate(jws);
            log.info(":::::::::::::::::::; result - {}", result);
            if(result.isValid()) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(result, null, null);
                log.info(":::::::::::::::::::; authentication - {}", authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
