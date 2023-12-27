package com.example.todolist.common.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingEntity {

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(updatable = false)
    @CreatedBy
    private String name;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
