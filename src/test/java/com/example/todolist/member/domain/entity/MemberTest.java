package com.example.todolist.member.domain.entity;

import com.example.todolist.common.exception.CustomException;
import com.example.todolist.common.exception.ExceptionStatus;
import com.example.todolist.member.MemberFixture;
import com.example.todolist.member.application.request.MemberJoinRequest;
import com.example.todolist.member.application.request.MemberLoginRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

@DisplayName("도메인 - 회원가입&로그인 테스트")
@Import(ExceptionStatus.class)
class MemberTest {
    @DisplayName("회원가입 시 중복 아이디이면 예외 발생")
    @Test
    void duplicateId() throws JsonProcessingException {

        MemberJoinRequest request = MemberFixture.existJoinMember;
        Member member = MemberFixture.member;

        Assertions.assertThatThrownBy(() -> {
                    if (member.getPw().equals(request.pw())) {
                        throw new CustomException(ExceptionStatus.USERNAME_IS_EXIST);
                    }
                })
                .isInstanceOf(CustomException.class);
    }

    @DisplayName("로그인 시 아이디 또는 비밀번호가 db에 저장된 비밀번호가 다른 경우 예외 발생")
    @Test
    void doesNotMatchUserInfo() throws JsonProcessingException {
        MemberLoginRequest request = MemberFixture.nonExistLoginMember;
        Member member = MemberFixture.member;

        Assertions.assertThatThrownBy(() -> {
            if (!member.getUserId().equals(request.userId()) || !member.getPw().equals(request.pw())) {
                throw new CustomException(ExceptionStatus.LOGIN_INFO_DO_NOT_MATCH);
            }
        })
        .isInstanceOf(CustomException.class);
    }
}