package com.example.todolist.member.domain.repository;

import com.example.todolist.common.exception.CustomException;
import com.example.todolist.common.exception.ExceptionStatus;
import com.example.todolist.member.MemberFixture;
import com.example.todolist.member.application.request.MemberJoinRequest;
import com.example.todolist.member.application.request.MemberLoginRequest;
import com.example.todolist.member.domain.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Repository - 회원가입&로그인")
//@TestPropertySource(locations = "classpath:application-test.yaml") // 기존 DB가 아닌 특정 테스트 DB 를 사용하는 경우
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@DataJpaTest
@DataJpaTest
class MemberRepositoryTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MemberRepositoryTest.class);

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setup() {
        // 테스트 디비에 회원 조회를 위한 데이터 삽입
        Member member = MemberFixture.member;
        testEntityManager.merge(member);

//        Member member1 = new Member("test", "password");
//        testEntityManager.persist(member);

        /*
            persist() | merge()
            persist() : 새로운 엔티티를 영속성 컨텍스트에 추가하기 위해 사용
                        (아직 데이터베이스에 저장되지 않은 새로운 인스턴스에 사용되어야 함, 인스턴스가 detached 상태이면 X)
            merge() : detached 상태의 엔티티를 받아 해당 엔티티의 복사본을 생성하고, 복사본을 다시 영속 상태로 만듦

            => public static final Member member = new Member("test", "password");
               이와 같이 상수로 엔티티 인스턴스를 선언하고 persist()로 저장하려고 하면 에러가 남
               이유 : member 인스턴스가 이미 초기화되어 있어 JPA 는 이 인스턴스를 'detached' 상태로 간주하기 때문
                     > 에러 없이 엔티티를 저장하려면 엔티티 인스턴스를 새로운 member 인스턴스를 생성해야 함. (new Member())

               merge()의 경우 member 상수를 merge()에 전달할 때 복사본이 연속 상태로 만들어지기 때문에 에러가 발생하지 않음
         */

        log.info("::::::::::::::::: MemberRepository @BeforeEach :::::::::::::::");
    }

    @DisplayName("회원 등록 테스트")
    @Transactional
    @Test
    void joinTest() {
        // given
        MemberJoinRequest request = MemberFixture.nonExistJoinMember;
        Member member = new Member(request.name(), request.email(), request.userId(), request.pw(), request.role());

        // when
        memberRepository.save(member);

        // then
        Member saveMember = memberRepository.findByUserId(request.userId()).orElseThrow();
        Assertions.assertEquals(member, saveMember);
    }

    @DisplayName("회원 등록 시 동일한 아이디가 있는 경우 예외 발생")
    @Transactional
    @Test
    void joinExceptionTest() {
        // given
        MemberJoinRequest request = MemberFixture.existJoinMember;
        Member member = new Member(request.name(), request.email(), request.userId(), request.pw(), request.role());

        // when - then
        assertThatThrownBy(() -> memberRepository.save(member))
        .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("로그인 하는 회원 조회 테스트")
    @Transactional
    @Test
    void loginTest() {
        // given
        MemberLoginRequest request = MemberFixture.existLoginMember;

        // when
        Member member = memberRepository.findByUserId(request.userId()).orElse(null);

        // then
        Assertions.assertNotNull(member, ExceptionStatus.LOGIN_INFO_DO_NOT_MATCH.getMessage());
        Assertions.assertEquals(member.getUserId(), request.userId());
        Assertions.assertEquals(member.getPw(), request.pw());
    }
}