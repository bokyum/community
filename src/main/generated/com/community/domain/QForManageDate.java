package com.community.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QForManageDate is a Querydsl query type for ForManageDate
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QForManageDate extends BeanPath<ForManageDate> {

    private static final long serialVersionUID = 280220548L;

    public static final QForManageDate forManageDate = new QForManageDate("forManageDate");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = createDateTime("lastModifiedDate", java.time.LocalDateTime.class);

    public QForManageDate(String variable) {
        super(ForManageDate.class, forVariable(variable));
    }

    public QForManageDate(Path<? extends ForManageDate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QForManageDate(PathMetadata metadata) {
        super(ForManageDate.class, metadata);
    }

}

