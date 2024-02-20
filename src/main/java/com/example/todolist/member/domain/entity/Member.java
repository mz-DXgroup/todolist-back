package com.example.todolist.member.domain.entity;

import com.example.todolist.common.domain.entity.AuditingEntity;
import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.member.domain.dto.MemberDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Member extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    @Column(unique = true)
    private String userId;

    private String pw;

    private String roles;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "member")
    private List<Document> documents = new ArrayList<>();

    private Member(Integer memberId) {
        this.id = memberId;
    }

    public Member(String userId, String pw) {
        this.userId = userId;
        this.pw = pw;
    }

    public static Member fromId(Integer memberId) {
        return new Member(memberId);
    }

    public void create(String name, String email, String userId, String pw, String roles, List<Document> documents) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.pw = pw;
        this.roles = roles;
        this.documents = documents;
    }

    public Member(String name, String email, String userId, String pw, String role) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.pw = pw;
        this.roles = role;

    }

    public MemberDto memberDto() {
        return new MemberDto(userId, name, email, roles, false);
    }
}
