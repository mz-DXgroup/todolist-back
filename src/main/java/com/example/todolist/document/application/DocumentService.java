package com.example.todolist.document.application;

import com.example.todolist.document.application.dto.DocumentRequest;
import com.example.todolist.document.application.dto.DocumentResponse;
import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.document.domain.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class DocumentService {

    private  final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {this.documentRepository = documentRepository;}


    public Integer createDocument(DocumentRequest documentRequest){
        Document document = documentRequest.toEntity();
        documentRepository.save(document);
        return document.getId();
    }

    @Transactional(readOnly = true)
    public List<DocumentResponse> getDocument(){
        return documentRepository.findAll().stream().map(DocumentResponse::from).toList();
    }
}
