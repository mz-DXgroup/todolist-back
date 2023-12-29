package com.example.todolist.document.domain.repository.repositoryImpl;

import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.document.domain.repository.DocumentRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.todolist.document.domain.entity.QDocument.document;

public class DocumentRepositoryImpl implements DocumentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public DocumentRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }


    @Override
    public Page<Document> findAllBy(Pageable pageable) {

        List<Document> documents = queryFactory
                .select(Projections.constructor(Document.class,
                        document.id.as("id"),
                        document.title.as("title"),
                        document.period.as("period"),
                        document.description.as("description"),
                        document.dayStatus.as("dayStatus"))
                )
                .from(document)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long count = queryFactory.selectFrom(document).fetchCount();
        return new PageImpl<>(documents, pageable, count);
    }
}
