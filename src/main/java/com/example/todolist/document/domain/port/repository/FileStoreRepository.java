package com.example.todolist.document.domain.port.repository;

import com.example.todolist.document.domain.entity.FileStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileStoreRepository extends JpaRepository<FileStore,Long> {


}
