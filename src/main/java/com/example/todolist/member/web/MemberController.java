package com.example.todolist.member.web;

import com.example.todolist.member.application.response.MemberResponse;
import com.example.todolist.member.domain.dto.MemberDto;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequestMapping("/api")
@RestController
public class MemberController {

    @GetMapping("/members")
    public ResponseEntity<MemberDto> getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDto principal = (MemberDto) authentication.getPrincipal();

        return ResponseEntity.ok(principal);
    }
}
