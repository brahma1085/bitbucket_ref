package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRelativemasterlog is a Querydsl query type for QRelativemasterlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QRelativemasterlog extends com.mysema.query.sql.RelationalPathBase<QRelativemasterlog> {

    private static final long serialVersionUID = -282980127;

    public static final QRelativemasterlog relativemasterlog = new QRelativemasterlog("relativemasterlog");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath age = createString("age");

    public final StringPath marital = createString("marital");

    public final StringPath name = createString("name");

    public final StringPath relation = createString("relation");

    public final StringPath reltype = createString("reltype");

    public final StringPath sex = createString("sex");

    public final StringPath status = createString("status");

    public QRelativemasterlog(String variable) {
        super(QRelativemasterlog.class, forVariable(variable), "null", "relativemasterlog");
    }

    @SuppressWarnings("all")
    public QRelativemasterlog(Path<? extends QRelativemasterlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "relativemasterlog");
    }

    public QRelativemasterlog(PathMetadata<?> metadata) {
        super(QRelativemasterlog.class, metadata, "null", "relativemasterlog");
    }

}

