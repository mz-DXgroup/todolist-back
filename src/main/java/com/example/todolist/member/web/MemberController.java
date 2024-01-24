package com.example.todolist.member.web;

import com.example.todolist.member.application.MemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequestMapping("/api")
@RestController
public class MemberController {
    @GetMapping("/members")
    public ResponseEntity<MemberResponse> getMember(Principal principal) {
        return ResponseEntity.ok(MemberResponse.from(principal));
    }
}
