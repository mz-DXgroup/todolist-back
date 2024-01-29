package com.example.todolist.member.application.response;

import java.security.Principal;

public record MemberResponse(
        String name
) {
    public static MemberResponse from(Principal principal) {
        return new MemberResponse(
                principal.getName()
        );
    }
}
