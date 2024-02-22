package com.example.todolist.common.jwt;

import com.example.todolist.member.domain.dto.MemberDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "bWVnYWR4LWRtcC1zZXJ2aWNlLWRldmVsb3Atc2VjdXJpdHkta2V5LXZhbHVlLTIzMDEzMQ==";

    private Key key;

    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


    public String issue(MemberDto member) {

        Date issuedAt = new Date();
        Date expiration = new  Date(issuedAt.getTime() + 3600000);

        return Jwts.builder()
                .setSubject(member.userId())
                .claim("user", member)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public MemberDto validate(String jws) {
        try {
            MemberDto memberDto = null;
            if (jws != null) {

                Map<String, Class> classMap = Collections.singletonMap("user", MemberDto.class);

                Claims claims = Jwts.parserBuilder().deserializeJsonWith(new JacksonDeserializer<>(classMap))
                        .setSigningKey(key).build().parseClaimsJws(jws).getBody();

                log.info("::::::::::::: claims - {}", claims);
                log.info("::::::::::::: user - {}", claims.get("user"));

                memberDto = MemberDto.update((MemberDto) claims.get("user"), true);


                // validateTokenIsNotForALoggedOut(jws);    // 로그아웃 시 필요
            }

            return memberDto;

        } catch (ExpiredJwtException e) {
            log.warn("# Token expired - {}", e.getMessage());
            throw new JwtException(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("# no token : cannot be empty - {}", e.getMessage());
            throw new JwtException(e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("# Wrong token - {}", e.getMessage());
            throw new JwtException(e.getMessage());
        } catch (Exception e) {
            log.warn("# JWT token Parse Exception - {}", e.getMessage());
            throw new JwtException(e.getMessage());
        }
    }

//    public Date getTokenExpiryFromJWT(String token) {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token).getBody();
//
//        return claims.getExpiration();
//    }
}
