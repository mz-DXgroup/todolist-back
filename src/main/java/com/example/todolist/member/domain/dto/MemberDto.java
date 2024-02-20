package com.example.todolist.member.domain.dto;

import com.example.todolist.member.domain.entity.Member;

public record MemberDto(
        String userId,
        String name,
        String email,
        String roles,
        boolean isValid
) {

    public static MemberDto from(Member member) {
        return new MemberDto(
                member.getUserId(),
                member.getName(),
                member.getEmail(),
                member.getRoles(),
                false
        );
    }

    public static MemberDto update(MemberDto member, boolean isValid) {
        return new MemberDto(
                member.userId(),
                member.name(),
                member.email(),
                member.roles(),
                isValid
        );
    }
}
