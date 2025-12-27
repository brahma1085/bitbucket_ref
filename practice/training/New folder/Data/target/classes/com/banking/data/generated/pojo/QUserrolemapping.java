package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QUserrolemapping is a Querydsl query type for QUserrolemapping
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QUserrolemapping extends com.mysema.query.sql.RelationalPathBase<QUserrolemapping> {

    private static final long serialVersionUID = -1633016264;

    public static final QUserrolemapping userrolemapping = new QUserrolemapping("userrolemapping");

    public final StringPath roleCode = createString("role_code");

    public final StringPath userid = createString("userid");

    public QUserrolemapping(String variable) {
        super(QUserrolemapping.class, forVariable(variable), "null", "userrolemapping");
    }

    @SuppressWarnings("all")
    public QUserrolemapping(Path<? extends QUserrolemapping> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "userrolemapping");
    }

    public QUserrolemapping(PathMetadata<?> metadata) {
        super(QUserrolemapping.class, metadata, "null", "userrolemapping");
    }

}

