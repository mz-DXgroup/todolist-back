package com.example.todolist.member.application;

import com.example.todolist.member.application.request.MemberJoinRequest;
import com.example.todolist.member.application.request.MemberLoginRequest;
import com.example.todolist.member.application.response.MemberJoinResponse;
import com.example.todolist.member.application.response.MemberLoginResponse;
import com.example.todolist.member.domain.entity.Member;
import com.example.todolist.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class LoginService {

    private final MemberRepository memberRepository;



    public MemberLoginResponse login(MemberLoginRequest request) {

        Member member = memberRepository.findByUserId(request.userId())
                .filter(i -> i.getPw().equals(request.pw()))
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다"));
        return new MemberLoginResponse(member.getUserId(), member.getPw());
    }

    public MemberJoinResponse join(MemberJoinRequest request) {
        Member member = new Member(request.name(), request.email(), request.userId(), request.pw(), request.role());
        memberRepository.save(member);
        try {
            memberRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("이미 사용중인 아이디 입니다.");
        }
        return MemberJoinResponse.from(member);
    }
}

