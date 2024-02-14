package com.example.todolist.member.application.response;

import com.example.todolist.member.domain.entity.Member;

public record MemberLoginResponse(
        String userId,
        String pw
) {
    public static MemberLoginResponse from(Member member) {
        return new MemberLoginResponse(
                member.getUserId(),
                member.getPw()
        );
    }
}
