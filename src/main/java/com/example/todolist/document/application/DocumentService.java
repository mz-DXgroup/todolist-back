package com.example.todolist.document.application;

import com.example.todolist.document.application.dto.DocumentRequest;
import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.document.domain.repository.DocumentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
}
