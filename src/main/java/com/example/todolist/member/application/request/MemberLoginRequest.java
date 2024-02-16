package com.example.todolist.member.application.request;

import com.example.todolist.member.domain.entity.Member;

public record MemberLoginRequest(
        String userId,
        String email,
        String role,
        String pw
) {
    public Member toEntity(Member member) {
      return    new Member(userId, pw);
    }
}
