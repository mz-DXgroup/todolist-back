package com.example.todolist.member.application;

import com.example.todolist.common.exception.CustomException;
import com.example.todolist.common.exception.ExceptionStatus;
import com.example.todolist.member.MemberFixture;
import com.example.todolist.member.application.request.MemberJoinRequest;
import com.example.todolist.member.application.request.MemberLoginRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("서비스 - 회원가입&로그인 테스트")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class LoginServiceTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LoginServiceTest.class);

    @Autowired
    private LoginService loginService;

    @DisplayName("회원가입 시 비밀번호 암호화, 로그인 시 암호화된 비밀번호 일치 여부 테스트")
    @Test
    void join() {

        // given
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        MemberJoinRequest request = MemberFixture.nonExistJoinMember;

        // when
        String encodePassword = passwordEncoder.encode(request.pw());
        boolean isMatch = passwordEncoder.matches(request.pw(), encodePassword);

        // then
        Assertions.assertThat(passwordEncoder).isNotNull();
        Assertions.assertThat(isMatch).isTrue();
    }

    @DisplayName("회원 등록 시 동일한 아이디가 있는 경우 예외 발생")
    @Transactional
    @Test
    void joinExceptionTest() {
        // given  (동일한 아이디가 이미 존재하는 상황을 명시적으로 만들어주면 더 좋음..??)
        MemberJoinRequest request = MemberFixture.existJoinMember;

        // when - then
        Assertions.assertThatThrownBy(() -> loginService.join(request))  // .hasMessage() : detailMessage 에 있는 내용을...
                .isInstanceOf(CustomException.class)
                // extracting() : (테스트) 대상 객체로부터 특정 값이나 상태를 추출하기 위해 사용
//                .extracting(e -> (CustomException) e)   // CustomException 으로 캐스팅
//                .extracting(CustomException::getExceptionStatus) // CustomException 내의 ExceptionStatus 추출
                .extracting(e -> ((CustomException) e).getExceptionStatus()) // 위 두줄 하나로 합친 것
                .isInstanceOf(ExceptionStatus.class)    // isInstanceOf() : 해당 인스턴스가 맞는 지 확인
                // satisfies() : AssertJ에서 특정 객체가 주어진 조건들을 만족하는지 검증함
                .satisfies(exceptionStatus -> {
                    Assertions.assertThat(exceptionStatus.getStatusCode()).isEqualTo(ExceptionStatus.USERNAME_IS_EXIST.getStatusCode());
                    Assertions.assertThat(exceptionStatus.getMessage()).isEqualTo(ExceptionStatus.USERNAME_IS_EXIST.getMessage());
                });

//        CustomException e = assertThrows(CustomException.class, () -> loginService.join(request));
//
//        log.info("::::::::::::::: error message :  {}", e.getExceptionStatus().getMessage());
//        assertThat(e.getExceptionStatus().getStatusCode()).isEqualTo(ExceptionStatus.USERNAME_IS_EXIST.getStatusCode());
//        assertThat(e.getExceptionStatus().getMessage()).isEqualTo(ExceptionStatus.USERNAME_IS_EXIST.getMessage());
    }

    @DisplayName("로그인 시 일치하는 아이디가 없는 경우 예외 발생")
    @Transactional
    @Test
    void loginExceptionTest() {
        // given
        MemberLoginRequest request = MemberFixture.nonExistLoginMember;

        // when - then
        Assertions.assertThatThrownBy(() -> loginService.login(request))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getExceptionStatus())
                .isInstanceOf(ExceptionStatus.class)
                .satisfies(exceptionStatus -> {
                    Assertions.assertThat(exceptionStatus.getStatusCode()).isEqualTo(ExceptionStatus.LOGIN_INFO_DO_NOT_MATCH.getStatusCode());
                    Assertions.assertThat(exceptionStatus.getMessage()).isEqualTo(ExceptionStatus.LOGIN_INFO_DO_NOT_MATCH.getMessage());
                });

//        CustomException e = assertThrows(CustomException.class, () -> loginService.login(request));
//        log.info("::::::::::::::::: error message : {}", e.getExceptionStatus().getMessage());
    }
}