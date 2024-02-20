package com.example.todolist.file.domain.port.repository;

import com.example.todolist.file.domain.entity.FileStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileStoreRepository extends JpaRepository<FileStore,Long> {


}
