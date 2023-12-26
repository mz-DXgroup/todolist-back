package com.example.todolist.document.web;

import com.example.todolist.document.application.DocumentService;
import com.example.todolist.document.application.dto.DocumentRequest;
import com.example.todolist.document.application.dto.DocumentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequestMapping("/api")
@RestController
public class DocumentController {

    private  final DocumentService documentService ;

    public DocumentController(DocumentService documentService) {this.documentService = documentService;}


    @PostMapping("/document")
    public ResponseEntity<Void> createDocument (DocumentRequest documentRequest){
        Integer id = documentService.createDocument(documentRequest);
        return ResponseEntity.created(URI.create("api/document"+ id )).build();
    }

    @GetMapping("/documents")
    public ResponseEntity<List<DocumentResponse>> getDocuments(){
            return ResponseEntity.ok(documentService.getDocument());
    }
}
