package com.example.todolist.document.application;


import com.example.todolist.document.application.dto.request.DocumentRequest;
import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.port.repository.DocumentRepository;
import com.example.todolist.document.domain.status.DayStatus;
import com.example.todolist.member.domain.entity.Member;
import com.example.todolist.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("서비스 - Document 테스트")
@SpringBootTest
class DocumentServiceTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void createDocument() {

        // given
        DocumentRequest request = new DocumentRequest(new Period(LocalDate.now(), LocalDate.now()),
                "제목1", "설명1", "test", DayStatus.VERYGOOD);

        // when
        Member member = memberRepository.findByUserId(request.userId()).orElseThrow();  // 없는 경우 NoSuchElementException 발생 (오류처리 필요)
        // Member member1 = memberRepository.findByUserId(request.userId()).orElseThrow(() -> new RuntimeException("없음")); // Custom Exception 처리도 가능

        // then (member > id:3 userId:test)
        assertEquals(request.userId(), member.getUserId());
        assertEquals(3, member.getId());
    }
}