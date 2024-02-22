package com.example.todolist.document.domain.port.repository;

import com.example.todolist.document.domain.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    @Modifying
    @Query("""
            DELETE FROM Todo t
            WHERE t.document.id = :documentId
            """)
    void deleteAll(@Param("documentId")Integer documentId);
}
