package com.example.todolist.document.domain.repository.repositoryImpl;

import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.document.domain.repository.DocumentRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.todolist.document.domain.entity.QDocument.document;
@Repository
public class DocumentRepositoryImpl implements DocumentRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public DocumentRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Document> findAllBy(Pageable pageable) {

        List<Document> documents = queryFactory
                .selectFrom(document)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long count = queryFactory.select(document.count()).from(document).fetchOne();
        return new PageImpl<>(documents, pageable, count);
    }
}
