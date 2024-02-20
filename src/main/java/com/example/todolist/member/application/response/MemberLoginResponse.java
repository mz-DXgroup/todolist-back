package com.example.todolist.member.application.response;

import com.example.todolist.member.domain.entity.Member;

public record MemberLoginResponse(
        String userId,
        String roles,
        String jwt

) {
    public static MemberLoginResponse from(Member member) {
        return new MemberLoginResponse(
                member.getUserId(),
                null,
                member.getPw()
        );
    }

    public MemberLoginResponse loginResultInfo(String userId, String roles, String jwt) {
        return new MemberLoginResponse(
                userId,
                roles,
                jwt
        );
    }
}
