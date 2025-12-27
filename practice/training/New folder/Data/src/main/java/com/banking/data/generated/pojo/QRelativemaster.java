package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRelativemaster is a Querydsl query type for QRelativemaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QRelativemaster extends com.mysema.query.sql.RelationalPathBase<QRelativemaster> {

    private static final long serialVersionUID = -1322336381;

    public static final QRelativemaster relativemaster = new QRelativemaster("relativemaster");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath age = createString("age");

    public final StringPath marital = createString("marital");

    public final StringPath name = createString("name");

    public final StringPath relation = createString("relation");

    public final StringPath reltype = createString("reltype");

    public final StringPath sex = createString("sex");

    public final StringPath status = createString("status");

    public QRelativemaster(String variable) {
        super(QRelativemaster.class, forVariable(variable), "null", "relativemaster");
    }

    @SuppressWarnings("all")
    public QRelativemaster(Path<? extends QRelativemaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "relativemaster");
    }

    public QRelativemaster(PathMetadata<?> metadata) {
        super(QRelativemaster.class, metadata, "null", "relativemaster");
    }

}

