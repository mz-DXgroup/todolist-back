package com.example.todolist.document.domain;

import com.example.todolist.common.exception.CustomException;
import com.example.todolist.common.exception.ExceptionStatus;
import com.example.todolist.document.application.DocumentService;
import com.example.todolist.document.application.dto.request.DocumentRequest;
import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.port.repository.DocumentRepository;
import com.example.todolist.document.domain.status.DayStatus;
import com.example.todolist.member.domain.entity.Member;
import com.example.todolist.member.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("DocumentTest")
@SpringBootTest
class DocumentTest {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    MemberRepository memberRepository;

    @MockBean
    DocumentService documentService;

    public static final  Period period = new Period(LocalDate.now(), LocalDate.now().plusDays(1)) ;
    public static final  DocumentRequest documentRequest = new DocumentRequest(period,"1월 10일 할 일","할거 없음 ㅋ", "yellow", DayStatus.VERYGOOD);

    @DisplayName("문서 생성이 잘 된다")
    @Transactional
    @Test
    void createDocumentTest() {
        Member member = memberRepository.findByUserId(documentRequest.userId()).orElseThrow();

        List<Document> existingDocuments = documentRepository.findByTitleAndMemberUserId(documentRequest.title(), documentRequest.userId());

        if (!existingDocuments.isEmpty()) {
            throw new CustomException(ExceptionStatus.TITLE_NAME_DUPLICATE);
        }

        Document document = documentRequest.toEntity(member);
        Document savedDocument=  documentRepository.save(document);

        Document result= documentRepository.findById(savedDocument.getId()).orElseThrow(() -> new IllegalArgumentException("null 임"));

        assertEquals(savedDocument,result);
    }

//    @DisplayName("문서 리스트가 조회가 잘 된다")
//    @Transactional
//    @Test
//    void getDocumentsTest(){
//
//         List<DocumentResponse> dbDocumentResponses= documentRepository.findAll().stream().map(DocumentResponse::from).toList();
//        List<DocumentResponse>  documentResponses=documentService.getDocuments();
//
//        Assertions.assertEquals(dbDocumentResponses,documentResponses);
//    }

}