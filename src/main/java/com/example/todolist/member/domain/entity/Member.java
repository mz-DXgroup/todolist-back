package com.example.todolist.member.domain.entity;

import com.example.todolist.common.domain.entity.AuditingEntity;
import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.member.domain.dto.MemberDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Member extends AuditingEntity implements UserDetails {

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

    public void add(Document document) {
        this.documents.add(document);
        document.setMember(this);
    }


    private Member(String userId) {
        this.userId = userId;
    }

    public Member(String userId, String pw) {
        this.userId = userId;
        this.pw = pw;
    }

    public static Member fromId(String userId) {
        return new Member(userId);
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
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.pw = passwordEncoder.encode(pw);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return pw;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
