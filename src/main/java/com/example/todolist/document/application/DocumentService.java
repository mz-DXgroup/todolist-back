package com.example.todolist.document.application;

import com.example.todolist.document.application.dto.response.DocumentDetailResponse;
import com.example.todolist.document.application.dto.request.DocumentRequest;
import com.example.todolist.document.application.dto.response.DocumentResponse;
import com.example.todolist.document.application.dto.request.DocumentUpdateRequest;
import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.document.domain.repository.DocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<DocumentResponse> getDocuments(Pageable pageable) {
        return documentRepository.findAllBy(pageable).map(DocumentResponse::from);
    }

    @Transactional(readOnly = true)
    public DocumentDetailResponse getDocument(Integer id) {
        return DocumentDetailResponse.from(
                documentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + "찾을수 없습니다."))
        );
    }

    public void updateDocument(Integer documentId, DocumentUpdateRequest request) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("없음"));
        document.update(request.period(), request.title(), request.description(), request.dayStatus());
    }

    public void deleteDocument(Integer documentId){
        documentRepository.deleteById(documentId);
    }
}
