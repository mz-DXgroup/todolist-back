package com.example.todolist.document.domain.port.repository;

import com.example.todolist.document.domain.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocumentRepositoryCustom {
    Page<Document> findAllBy(Pageable pageable);
}
