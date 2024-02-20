package com.example.todolist.member.application.response;

import com.example.todolist.member.domain.entity.Member;

public record MemberLoginResponse(
        String userId,
        String roles,
        String jwt

) {

}
