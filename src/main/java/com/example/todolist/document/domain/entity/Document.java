package com.example.todolist.document.domain.entity;

import com.example.todolist.common.member.domain.entity.Member;
import com.example.todolist.document.domain.dto.Period;
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
public class Document {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Period period;

    private String title;

    private String description;

    @Setter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "document")
    private List<Todo> todos= new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member =null;


    public  Document(Period period, String title, String description, Member member){
                this.period= period;
                this.title=title;
                this.description=description;
                this.member= member;
    }
    private Document(Integer documentId){
            this.id=documentId;
    }

    public void update(Period period,  String title, String description){
        this.period=period;
        this.title=title;
        this.description=description;
    }

    public static Document fromId(Integer documentId){
        return new Document(documentId);
    }
}
