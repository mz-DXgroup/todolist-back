package com.example.todolist.document;

import com.example.todolist.document.application.dto.DocumentRequest;
import com.example.todolist.document.domain.dto.Period;
import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.document.domain.repository.DocumentRepository;
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

    public  static  final  Period 시간 = new Period(LocalDateTime.now(),LocalDateTime.now().plusHours(1)) ;
    public  static  final  DocumentRequest 도큐멘트_요청 = new DocumentRequest(시간,"1월 10일 할 일","할거 없음 ㅋ", 1);

    @DisplayName("문서 생성이 잘 된다")
    @Transactional
    @Test
    void  createDocumentTest(){
        Document document = 도큐멘트_요청.toEntity();
        Document SavedDocument=  documentRepository.save(document);
        Document result= documentRepository.findById(SavedDocument.getId()).orElseThrow(()->new IllegalArgumentException("null 임"));


        assertEquals(SavedDocument,result);
    }
}