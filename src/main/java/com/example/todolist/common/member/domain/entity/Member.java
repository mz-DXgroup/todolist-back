package com.example.todolist.common.member.domain.entity;

import com.example.todolist.document.domain.entity.Document;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer id;

    private String name;

    private String email;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "member")
    private List<Document> documents = new ArrayList<>();

    private Member(Integer memberId){
        this.id=memberId;
    }
    public static Member fromId(Integer memberId){
        return  new Member(memberId);
    }
}
