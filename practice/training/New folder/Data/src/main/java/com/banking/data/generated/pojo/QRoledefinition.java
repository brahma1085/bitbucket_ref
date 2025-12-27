package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRoledefinition is a Querydsl query type for QRoledefinition
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QRoledefinition extends com.mysema.query.sql.RelationalPathBase<QRoledefinition> {

    private static final long serialVersionUID = 400752030;

    public static final QRoledefinition roledefinition = new QRoledefinition("roledefinition");

    public final StringPath roleCode = createString("role_code");

    public final StringPath roleDef = createString("role_def");

    public QRoledefinition(String variable) {
        super(QRoledefinition.class, forVariable(variable), "null", "roledefinition");
    }

    @SuppressWarnings("all")
    public QRoledefinition(Path<? extends QRoledefinition> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "roledefinition");
    }

    public QRoledefinition(PathMetadata<?> metadata) {
        super(QRoledefinition.class, metadata, "null", "roledefinition");
    }

}

