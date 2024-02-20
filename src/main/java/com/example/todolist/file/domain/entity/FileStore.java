package com.example.todolist.file.domain.entity;

import com.example.todolist.common.domain.entity.AuditingEntity;
import com.example.todolist.document.domain.entity.Todo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity
public class FileStore extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origFilename;

    private String filename;

    private String filePath;

    private String contentType;

    private Integer todoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    private Todo todo;

    public FileStore(String origFilename, String filename, String contentType, String filePath, Integer todoId) {
        this.origFilename = origFilename;
        this.filename = filename;
        this.contentType = contentType;
        this.filePath = filePath;
        this.todoId = todoId;
    }
}
