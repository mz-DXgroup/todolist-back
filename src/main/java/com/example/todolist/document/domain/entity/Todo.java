package com.example.todolist.document.domain.entity;

import com.example.todolist.common.domain.entity.AuditingEntity;
import com.example.todolist.document.domain.dto.TodoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@Entity
public class Todo extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String todo;

    private String description;

    private Period period;

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    public Todo(String todo, String description, Period period, boolean isActive, Document document) {
        this.todo = todo;
        this.description = description;
        this.period = period;
        this.isActive = isActive;
        this.document = document;
    }

    public void update(TodoDto dto) {
        this.todo = dto.todo();
        this.description = dto.description();
        this.period = dto.period();
        this.isActive = dto.isActive();
        this.document = Document.fromId(dto.documentId());
    }

}
