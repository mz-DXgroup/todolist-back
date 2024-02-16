package com.example.todolist.common.config;

import com.example.todolist.common.login.CustomAccessDeniedHandler;
import com.example.todolist.common.login.CustomAuthenticationEntryPoint;
import com.example.todolist.common.login.CustomUserDetailsService;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final String[] allowedUrls = {"/", "/swagger-ui/**", "/api/**", "/h2-console", "/**"};

    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.addAllowedMethod("POST");
            config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:8080"));
            config.setAllowCredentials(true);

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
            return config;
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable
                )
                .headers((headerConfig) -> headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable
                        )
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/api/members**").permitAll()
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated()

                ).formLogin(login -> login.defaultSuccessUrl("/swagger-ui/index.html", true)
                        .permitAll()
                )
                .logout(withDefaults());

        return http.build();
    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))    // H2 콘솔 사용을 위한 설정
//                .authorizeHttpRequests(requests ->
//                        requests.requestMatchers(allowedUrls).permitAll()    // requestMatchers의 인자로 전달된 url은 모두에게 허용
//                                .requestMatchers(PathRequest.toH2Console()).permitAll()    // H2 콘솔 접속은 모두에게 허용
//                                .anyRequest().authenticated()    // 그 외의 모든 요청은 인증 필요
//                )
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )    // 세션을 사용하지 않으므로 STATELESS 설정
//                .build();
//    }
}
