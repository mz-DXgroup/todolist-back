package com.example.todolist.member.application;

import com.example.todolist.member.application.request.MemberLoginRequest;
import com.example.todolist.member.application.response.MemberLoginResponse;
import com.example.todolist.member.domain.entity.Member;
import com.example.todolist.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;


    public MemberLoginResponse logIn(MemberLoginRequest request) {

        Member member = memberRepository.findByUserId(request.userId())
                .filter(i -> i.getPw().equals(request.pw()))
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다"));
        return new MemberLoginResponse(member.getUserId(), member.getPw());
    }
}
