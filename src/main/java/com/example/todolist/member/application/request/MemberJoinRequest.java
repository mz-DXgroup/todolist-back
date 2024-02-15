package com.example.todolist.member.application.request;

import com.example.todolist.member.domain.entity.Member;

public record MemberJoinRequest(
        String name,
        String email,
        String userId,
        String pw,
        String role
) {
    public Member toEntity(){
        return new Member(name,email,userId,pw,role);

    }
}
