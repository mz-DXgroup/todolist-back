package com.example.todolist.document.domain.entity;

import com.example.todolist.common.domain.entity.AuditingEntity;
import com.example.todolist.document.domain.dto.TodoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


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

    private boolean isChecked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "todo")
    private List<FileStore> fileStores = new ArrayList<>();

    public Todo(String todo, String description, Period period, boolean isChecked, Document document) {
        this.todo = todo;
        this.description = description;
        this.period = period;
        this.isChecked = isChecked;
        this.document = document;
    }

    public void update(TodoDto dto) {
        this.document = Document.fromId(dto.documentId());
        this.todo = dto.todo();
        this.description = dto.description();
        this.period = dto.period();
        this.isChecked = dto.isActive();
    }
//    private void periodValidate(Period period, Document document) {
//        if (document.getPeriod().startDate().isBefore(period.startDate())) {
//            throw new IllegalArgumentException("기간을 확인해 주세요");
//        }
//        if (document.getPeriod().endDate().isBefore(period.endDate())) {
//            throw new IllegalArgumentException("기간을 확인해 주세요");
//        }
//    }

}
