package com.example.todolist.document.domain.port.repository;

import com.example.todolist.document.domain.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface DocumentRepository extends JpaRepository<Document, Integer>, DocumentRepositoryCustom {

    @Query("SELECT d FROM Document d WHERE d.member.userId = :userId AND d.title = :title")
    List<Document> findByTitleAndMemberUserId(@Param("title") String title, @Param("userId") String userId);

}
