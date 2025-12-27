package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSharetypehistory is a Querydsl query type for QSharetypehistory
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSharetypehistory extends com.mysema.query.sql.RelationalPathBase<QSharetypehistory> {

    private static final long serialVersionUID = -2110824208;

    public static final QSharetypehistory sharetypehistory = new QSharetypehistory("sharetypehistory");

    public final StringPath acType = createString("ac_type");

    public final StringPath catname = createString("catname");

    public final NumberPath<Integer> maxshare = createNumber("maxshare", Integer.class);

    public final NumberPath<Integer> memCat = createNumber("mem_cat", Integer.class);

    public final NumberPath<Integer> minshare = createNumber("minshare", Integer.class);

    public final StringPath shCap = createString("sh_cap");

    public final NumberPath<Double> shareval = createNumber("shareval", Double.class);

    public final StringPath susShCap = createString("sus_sh_cap");

    public final StringPath votePow = createString("vote_pow");

    public QSharetypehistory(String variable) {
        super(QSharetypehistory.class, forVariable(variable), "null", "sharetypehistory");
    }

    @SuppressWarnings("all")
    public QSharetypehistory(Path<? extends QSharetypehistory> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "sharetypehistory");
    }

    public QSharetypehistory(PathMetadata<?> metadata) {
        super(QSharetypehistory.class, metadata, "null", "sharetypehistory");
    }

}

