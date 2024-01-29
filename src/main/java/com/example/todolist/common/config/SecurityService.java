package com.example.todolist.common.config;

import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.member.application.MemberService;
import com.example.todolist.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String insertUserId) throws UsernameNotFoundException {
        Member member = memberService.findOne(insertUserId);
        log.info("findOne : {} ", member);
        return User.builder()
                .username(member.getUserId())
                .password(member.getPw())
                .roles(member.getRoles())
                .build();
    }

    public Member createUser(String userId, String pw, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.create("1", "Test@mz.co.krq", "aa", "123", "USER", List.of(new Document()));
        return member;
    }
}