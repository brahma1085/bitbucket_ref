package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAddrtypes is a Querydsl query type for QAddrtypes
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAddrtypes extends com.mysema.query.sql.RelationalPathBase<QAddrtypes> {

    private static final long serialVersionUID = 1102522035;

    public static final QAddrtypes addrtypes = new QAddrtypes("addrtypes");

    public final StringPath addrType = createString("addr_type");

    public final NumberPath<Integer> number = createNumber("number", Integer.class);

    public QAddrtypes(String variable) {
        super(QAddrtypes.class, forVariable(variable), "null", "addrtypes");
    }

    @SuppressWarnings("all")
    public QAddrtypes(Path<? extends QAddrtypes> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "addrtypes");
    }

    public QAddrtypes(PathMetadata<?> metadata) {
        super(QAddrtypes.class, metadata, "null", "addrtypes");
    }

}

