package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QApprisersmaster is a Querydsl query type for QApprisersmaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QApprisersmaster extends com.mysema.query.sql.RelationalPathBase<QApprisersmaster> {

    private static final long serialVersionUID = 1380396440;

    public static final QApprisersmaster apprisersmaster = new QApprisersmaster("apprisersmaster");

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final StringPath name = createString("name");

    public QApprisersmaster(String variable) {
        super(QApprisersmaster.class, forVariable(variable), "null", "apprisersmaster");
    }

    @SuppressWarnings("all")
    public QApprisersmaster(Path<? extends QApprisersmaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "apprisersmaster");
    }

    public QApprisersmaster(PathMetadata<?> metadata) {
        super(QApprisersmaster.class, metadata, "null", "apprisersmaster");
    }

}

