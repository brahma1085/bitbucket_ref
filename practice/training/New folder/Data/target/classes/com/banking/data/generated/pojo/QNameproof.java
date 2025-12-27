package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QNameproof is a Querydsl query type for QNameproof
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QNameproof extends com.mysema.query.sql.RelationalPathBase<QNameproof> {

    private static final long serialVersionUID = 1463470372;

    public static final QNameproof nameproof1 = new QNameproof("nameproof");

    public final StringPath nameproof = createString("nameproof");

    public QNameproof(String variable) {
        super(QNameproof.class, forVariable(variable), "null", "nameproof");
    }

    @SuppressWarnings("all")
    public QNameproof(Path<? extends QNameproof> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "nameproof");
    }

    public QNameproof(PathMetadata<?> metadata) {
        super(QNameproof.class, metadata, "null", "nameproof");
    }

}

