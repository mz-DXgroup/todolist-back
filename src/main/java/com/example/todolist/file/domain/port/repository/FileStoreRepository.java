package com.example.todolist.file.domain.port.repository;

import com.example.todolist.file.domain.entity.FileStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileStoreRepository extends JpaRepository<FileStore,Long> {


    @Modifying
    @Query("""
        DELETE FROM FileStore m WHERE m.todo.document.id= :documentId
            """)
    void deleteMapping(@Param("documentId")Integer documentId);
}
