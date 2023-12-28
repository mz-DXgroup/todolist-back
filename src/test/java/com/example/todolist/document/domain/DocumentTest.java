package com.example.todolist.document.domain;

import com.example.todolist.document.application.DocumentService;
import com.example.todolist.document.application.dto.DocumentRequest;
import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.repository.DocumentRepository;
import com.example.todolist.document.domain.status.DayStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("DocumentTest")
@SpringBootTest
class DocumentTest {

    @Autowired
     DocumentRepository documentRepository;

    @Autowired
    DocumentService documentService;

    public  static  final  Period 시간 = new Period(LocalDateTime.now(),LocalDateTime.now().plusHours(1)) ;
    public  static  final  DocumentRequest 도큐멘트_요청 = new DocumentRequest(시간,"1월 10일 할 일","할거 없음 ㅋ", 1, DayStatus.VERYGOOD);

    @DisplayName("문서 생성이 잘 된다")
    @Transactional
    @Test
    void  createDocumentTest(){
        Document document = 도큐멘트_요청.toEntity();
        Document savedDocument=  documentRepository.save(document);
        Document result= documentRepository.findById(savedDocument.getId()).orElseThrow(()->new IllegalArgumentException("null 임"));

        assertEquals(savedDocument,result);
    }
/*
    @DisplayName("문서 리스트가 조회가 잘 된다 ")
    @Transactional
    @Test
    void getDocumentsTest(){

         List<DocumentResponse> dbDocumentResponses= documentRepository.findAll().stream().map(DocumentResponse::from).toList();
        List<DocumentResponse>  documentResponses=documentService.getDocuments();

        Assertions.assertEquals(dbDocumentResponses,documentResponses);
    }
*/
}