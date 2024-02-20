package com.example.todolist.member.application;

import com.example.todolist.common.jwt.JwtTokenProvider;
import com.example.todolist.common.exception.CustomException;
import com.example.todolist.common.exception.ExceptionStatus;
import com.example.todolist.member.application.request.MemberJoinRequest;
import com.example.todolist.member.application.request.MemberLoginRequest;
import com.example.todolist.member.application.response.MemberJoinResponse;
import com.example.todolist.member.application.response.MemberLoginResponse;
import com.example.todolist.member.domain.dto.MemberDto;
import com.example.todolist.member.domain.entity.Member;
import com.example.todolist.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class LoginService {

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public MemberLoginResponse login(MemberLoginRequest request) {

        Member member = memberRepository.findByUserId(request.userId())
                .filter(i -> i.getPw().equals(request.pw()))
                .orElseThrow(() -> new CustomException(ExceptionStatus.LOGIN_INFO_DO_NOT_MATCH));
        String jwt = jwtTokenProvider.issue(MemberDto.from(member));

        return new MemberLoginResponse(member.getUserId(), member.getRoles(), jwt);
    }

    public MemberJoinResponse join(MemberJoinRequest request) {
        Member member = new Member(request.name(), request.email(), request.userId(), request.pw(), request.role());
        try {
            memberRepository.save(member);
            memberRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ExceptionStatus.USERNAME_IS_EXIST);
        }
        return MemberJoinResponse.from(member);
    }
}

