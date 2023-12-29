package com.example.todolist.document.domain.entity;

import com.example.todolist.common.domain.entity.AuditingEntity;
import com.example.todolist.common.member.domain.entity.Member;
import com.example.todolist.document.domain.status.DayStatus;
import com.example.todolist.document.domain.status.convertor.DayStatusConvertor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
@Entity
public class Document extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Period period;

    private String title;

    private String description;

    @Convert(converter = DayStatusConvertor.class)
    private DayStatus dayStatus;

    @Setter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "document")
    private List<Todo> todos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    public Document(Period period, String title, String description, Member member,DayStatus dayStatus) {
        this.period = period;
        this.title = title;
        this.description = description;
        this.member = member;
        this.dayStatus = dayStatus;
    }

    private Document(Integer documentId) {
        this.id = documentId;
    }

    public void update(Period period, String title, String description,DayStatus dayStatus) {
        this.period = period;
        this.title = title;
        this.description = description;
        this.dayStatus=dayStatus;
    }

    public static Document fromId(Integer documentId) {
        return new Document(documentId);
    }
}
