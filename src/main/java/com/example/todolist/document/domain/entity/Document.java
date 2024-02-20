package com.example.todolist.document.domain.entity;

import com.example.todolist.common.domain.entity.AuditingEntity;
import com.example.todolist.common.exception.CustomException;
import com.example.todolist.common.exception.ExceptionStatus;
import com.example.todolist.document.domain.status.DayStatus;
import com.example.todolist.document.domain.status.convertor.DayStatusConvertor;
import com.example.todolist.member.domain.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String title;

    @NotNull
    private String description;

    @Convert(converter = DayStatusConvertor.class)
    private DayStatus dayStatus;

    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document")
    private List<Todo> todos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Document(Period period, String title, String description, Member member, DayStatus dayStatus) {
        this.period = period;
        this.title = titleLimitLen(title);
        this.description = descLimitLen(description);
        this.member = member;
        this.dayStatus = dayStatus;
    }

    private Document(Integer documentId) {
        this.id = documentId;
    }

    public void update(Period period, String title, String description, DayStatus dayStatus) {
        this.period = period;
        this.title = titleLimitLen(title);
        this.description = descLimitLen(description);
        this.dayStatus = dayStatus;
    }

    public static Document fromId(Integer documentId) {
        return new Document(documentId);
    }

    static final Integer TITLE_MAX_LENGTH = 50;
    static final Integer DESC_MAX_LENGTH = 200;

    public static String titleLimitLen(String title) {
        if (title == null || title.length() > TITLE_MAX_LENGTH) {
            throw new CustomException(ExceptionStatus.TITLE_LENGTH_OVER);
        }
        return title;
    }

    public static String descLimitLen(String description) {
        if (description == null || description.length() > DESC_MAX_LENGTH) {
            throw new CustomException(ExceptionStatus.DESC_LENGTH_OVER);
        }
        return description;
    }

}
