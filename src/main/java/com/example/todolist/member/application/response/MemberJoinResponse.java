package com.example.todolist.member.application.response;

import com.example.todolist.member.domain.entity.Member;

public record MemberJoinResponse(
        String name,
        String email,
        String userId,
        String pw,
        String role
) {
    public static MemberJoinResponse from(Member member) {
        return new MemberJoinResponse(
                member.getName(),
                member.getEmail(),
                member.getUserId(),
                member.getPw(),
                member.getRoles()
        );
    }
}
