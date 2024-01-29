package com.example.todolist.document.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocument is a Querydsl query type for Document
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDocument extends EntityPathBase<Document> {

    private static final long serialVersionUID = 820412598L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocument document = new QDocument("document");

    public final com.example.todolist.common.domain.entity.QAuditingEntity _super = new com.example.todolist.common.domain.entity.QAuditingEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final EnumPath<com.example.todolist.document.domain.status.DayStatus> dayStatus = createEnum("dayStatus", com.example.todolist.document.domain.status.DayStatus.class);

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final com.example.todolist.member.domain.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final StringPath name = _super.name;

    public final QPeriod period;

    public final StringPath title = createString("title");

    public final ListPath<Todo, QTodo> todos = this.<Todo, QTodo>createList("todos", Todo.class, QTodo.class, PathInits.DIRECT2);

    public QDocument(String variable) {
        this(Document.class, forVariable(variable), INITS);
    }

    public QDocument(Path<? extends Document> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDocument(PathMetadata metadata, PathInits inits) {
        this(Document.class, metadata, inits);
    }

    public QDocument(Class<? extends Document> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.example.todolist.member.domain.entity.QMember(forProperty("member")) : null;
        this.period = inits.isInitialized("period") ? new QPeriod(forProperty("period")) : null;
    }

}

