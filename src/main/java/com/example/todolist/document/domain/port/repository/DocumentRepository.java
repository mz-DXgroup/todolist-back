package com.example.todolist.document.domain.port.repository;

import com.example.todolist.document.domain.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DocumentRepository extends JpaRepository<Document,Integer> ,DocumentRepositoryCustom {
}
