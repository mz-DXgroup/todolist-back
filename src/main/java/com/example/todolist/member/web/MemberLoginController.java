package com.example.todolist.member.web;

import com.example.todolist.member.application.LoginService;
import com.example.todolist.member.application.request.MemberJoinRequest;
import com.example.todolist.member.application.request.MemberLoginRequest;
import com.example.todolist.member.application.response.MemberLoginResponse;
import com.example.todolist.member.domain.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class MemberLoginController {

    private final LoginService service;

    public MemberLoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponse> login(@RequestBody MemberLoginRequest request) {
        MemberLoginResponse member = service.login(request);
        return ResponseEntity.ok().body(member);
    }

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody MemberJoinRequest request) {
        service.join(request);
        return ResponseEntity.ok().build();
    }
}
