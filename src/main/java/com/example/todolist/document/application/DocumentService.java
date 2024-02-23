package com.example.todolist.document.application;

import com.example.todolist.common.exception.CustomException;
import com.example.todolist.common.exception.ExceptionStatus;
import com.example.todolist.document.application.dto.response.DocumentDetailResponse;
import com.example.todolist.document.application.dto.request.DocumentRequest;
import com.example.todolist.document.application.dto.response.DocumentResponse;
import com.example.todolist.document.application.dto.request.DocumentUpdateRequest;
import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.document.domain.port.repository.DocumentRepository;
import com.example.todolist.member.domain.entity.Member;
import com.example.todolist.member.domain.repository.MemberRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final MemberRepository memberRepository;

    public DocumentService(DocumentRepository documentRepository, MemberRepository memberRepository) {
        this.documentRepository = documentRepository;
        this.memberRepository = memberRepository;
    }

    public Integer createDocument(DocumentRequest documentRequest) {
        String userId = documentRequest.userId();
        String title = documentRequest.title();

        Member member = memberRepository.findByUserId(userId)
                .orElseGet(() -> Member.fromId(userId));

        List<Document> existingDocuments = documentRepository.findByTitleAndMemberUserId(title, userId);

        if (!existingDocuments.isEmpty()) {
            throw new CustomException(ExceptionStatus.TITLE_NAME_DUPLICATE);
        }

        if (!memberRepository.existsByUserId(userId)) {
            memberRepository.save(member);
        }

        Document document = documentRequest.toEntity(member);
        documentRepository.save(document);
        return document.getId();
    }



    @Transactional(readOnly = true)
    public Page<DocumentResponse> getDocuments(String userId, Pageable pageable) {
        return documentRepository.findAllBy(userId, pageable).map(DocumentResponse::from);
    }

    @Transactional(readOnly = true)
    public DocumentDetailResponse getDocument(Integer id) {
        Document document = documentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + "찾을수 없습니다."));
        boolean isTodoEmpty = false;

        if (!document.getTodos().isEmpty()) {
            isTodoEmpty = true;
        }

        return DocumentDetailResponse.from(document, isTodoEmpty);

    }

    public void updateDocument(Integer documentId, DocumentUpdateRequest request) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("없음"));
        document.update(request.period(), request.title(), request.description(), request.dayStatus());
    }

    public void deleteDocument(Integer documentId) {
        documentRepository.deleteById(documentId);
    }

    public void deleteDocumentAll() {
        documentRepository.deleteAll();
    }
}
