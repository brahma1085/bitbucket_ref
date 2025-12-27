package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAddressproof is a Querydsl query type for QAddressproof
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAddressproof extends com.mysema.query.sql.RelationalPathBase<QAddressproof> {

    private static final long serialVersionUID = 312752741;

    public static final QAddressproof addressproof = new QAddressproof("addressproof");

    public final StringPath addrproof = createString("addrproof");

    public QAddressproof(String variable) {
        super(QAddressproof.class, forVariable(variable), "null", "addressproof");
    }

    @SuppressWarnings("all")
    public QAddressproof(Path<? extends QAddressproof> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "addressproof");
    }

    public QAddressproof(PathMetadata<?> metadata) {
        super(QAddressproof.class, metadata, "null", "addressproof");
    }

}

