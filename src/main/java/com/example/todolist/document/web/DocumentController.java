package com.example.todolist.document.web;

import com.example.todolist.document.application.DocumentService;
import com.example.todolist.document.application.dto.response.DocumentDetailResponse;
import com.example.todolist.document.application.dto.request.DocumentRequest;
import com.example.todolist.document.application.dto.response.DocumentResponse;
import com.example.todolist.document.application.dto.request.DocumentUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/api")
@RestController
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/documents")
    public ResponseEntity<Void> createDocument(@RequestBody DocumentRequest documentRequest) {
        Integer id = documentService.createDocument(documentRequest);
        return ResponseEntity.created(URI.create("/documents/" + id)).build();
    }

    @GetMapping("/documents")
    public ResponseEntity<Page<DocumentResponse>> getDocuments(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(documentService.getDocuments(pageable));
    }

    @GetMapping("/documents/{documentId}")
    public ResponseEntity<DocumentDetailResponse> getDocument(@PathVariable Integer documentId) {
        return ResponseEntity.ok(documentService.getDocument(documentId));
    }

    @PutMapping("/documents/{documentId}")
    public ResponseEntity<Void> updateDocument(@PathVariable Integer documentId, DocumentUpdateRequest documentUpdateRequest) {
        documentService.updateDocument(documentId, documentUpdateRequest);
        return ResponseEntity.ok().build();
    }

}
