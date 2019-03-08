package com.mj.qModels;

import com.mj.model.Subscribe;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QSubscribe is a Querydsl query type for Subscribe
 */
//@Generated("com.querydsl.codegen.EntitySerializer")
public class QSubscribe extends EntityPathBase<Subscribe> {

    private static final long serialVersionUID = 1521796122L;

    public static final QSubscribe subscribe = new QSubscribe("subscribe");

    public final StringPath avatarUrl = createString("avatarUrl");

    public final StringPath gender = createString("gender");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath nickName = createString("nickName");

    public QSubscribe(String variable) {
        super(Subscribe.class, forVariable(variable));
    }

    public QSubscribe(Path<? extends Subscribe> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubscribe(PathMetadata metadata) {
        super(Subscribe.class, metadata);
    }

}

