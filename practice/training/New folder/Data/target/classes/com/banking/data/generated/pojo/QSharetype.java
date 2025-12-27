package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSharetype is a Querydsl query type for QSharetype
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSharetype extends com.mysema.query.sql.RelationalPathBase<QSharetype> {

    private static final long serialVersionUID = -1367117020;

    public static final QSharetype sharetype = new QSharetype("sharetype");

    public final StringPath acType = createString("ac_type");

    public final StringPath catname = createString("catname");

    public final NumberPath<Integer> maxshare = createNumber("maxshare", Integer.class);

    public final NumberPath<Integer> memCat = createNumber("mem_cat", Integer.class);

    public final NumberPath<Integer> minshare = createNumber("minshare", Integer.class);

    public final NumberPath<Double> shareval = createNumber("shareval", Double.class);

    public final StringPath votePow = createString("vote_pow");

    public final com.mysema.query.sql.PrimaryKey<QSharetype> primary = createPrimaryKey(acType, memCat);

    public QSharetype(String variable) {
        super(QSharetype.class, forVariable(variable), "null", "sharetype");
    }

    @SuppressWarnings("all")
    public QSharetype(Path<? extends QSharetype> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "sharetype");
    }

    public QSharetype(PathMetadata<?> metadata) {
        super(QSharetype.class, metadata, "null", "sharetype");
    }

}

