package com.example.todolist.document.application;

import com.example.todolist.document.application.dto.DocumentDetailResponse;
import com.example.todolist.document.application.dto.DocumentRequest;
import com.example.todolist.document.application.dto.DocumentResponse;
import com.example.todolist.document.application.dto.DocumentUpdateRequest;
import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.document.domain.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Integer createDocument(DocumentRequest documentRequest) {
        Document document = documentRequest.toEntity();
        documentRepository.save(document);
        return document.getId();
    }

    @Transactional(readOnly = true)
    public List<DocumentResponse> getDocuments() {
        return documentRepository.findAll().stream().map(DocumentResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public DocumentDetailResponse getDocument(Integer id) {
        return DocumentDetailResponse.from(documentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + "찾을수 없습니다.")));
    }

    public void updateDocument(Integer documentId, DocumentUpdateRequest request) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new IllegalArgumentException("없음"));
        document.update(request.period(), request.title(), request.description(), request.dayStatus());
    }
}
