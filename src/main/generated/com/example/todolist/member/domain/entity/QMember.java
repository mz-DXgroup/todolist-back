package com.example.todolist.member.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1340397206L;

    public static final QMember member = new QMember("member1");

    public final com.example.todolist.common.domain.entity.QAuditingEntity _super = new com.example.todolist.common.domain.entity.QAuditingEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<com.example.todolist.document.domain.entity.Document, com.example.todolist.document.domain.entity.QDocument> documents = this.<com.example.todolist.document.domain.entity.Document, com.example.todolist.document.domain.entity.QDocument>createList("documents", com.example.todolist.document.domain.entity.Document.class, com.example.todolist.document.domain.entity.QDocument.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final StringPath pw = createString("pw");

    public final StringPath roles = createString("roles");

    public final StringPath userId = createString("userId");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

