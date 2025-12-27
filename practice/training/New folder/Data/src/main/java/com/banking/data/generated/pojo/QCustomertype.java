package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCustomertype is a Querydsl query type for QCustomertype
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCustomertype extends com.mysema.query.sql.RelationalPathBase<QCustomertype> {

    private static final long serialVersionUID = 934126765;

    public static final QCustomertype customertype = new QCustomertype("customertype");

    public final NumberPath<Integer> custcode = createNumber("custcode", Integer.class);

    public final StringPath custype = createString("custype");

    public QCustomertype(String variable) {
        super(QCustomertype.class, forVariable(variable), "null", "customertype");
    }

    @SuppressWarnings("all")
    public QCustomertype(Path<? extends QCustomertype> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "customertype");
    }

    public QCustomertype(PathMetadata<?> metadata) {
        super(QCustomertype.class, metadata, "null", "customertype");
    }

}

