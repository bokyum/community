package com.community.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKeyword is a Querydsl query type for Keyword
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QKeyword extends EntityPathBase<Keyword> {

    private static final long serialVersionUID = 287434513L;

    public static final QKeyword keyword = new QKeyword("keyword");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Tag, QTag> tag = this.<Tag, QTag>createList("tag", Tag.class, QTag.class, PathInits.DIRECT2);

    public final StringPath word = createString("word");

    public QKeyword(String variable) {
        super(Keyword.class, forVariable(variable));
    }

    public QKeyword(Path<? extends Keyword> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKeyword(PathMetadata metadata) {
        super(Keyword.class, metadata);
    }

}

