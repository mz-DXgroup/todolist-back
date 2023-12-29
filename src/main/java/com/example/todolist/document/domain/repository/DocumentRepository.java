package com.example.todolist.document.domain.repository;

import com.example.todolist.document.domain.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Integer> ,DocumentRepositoryCustom {
}
