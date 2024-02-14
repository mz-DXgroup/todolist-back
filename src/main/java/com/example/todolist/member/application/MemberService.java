package com.example.todolist.member.application;

import com.example.todolist.member.domain.entity.Member;
import com.example.todolist.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findOne(String userId) {
        return memberRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }
}