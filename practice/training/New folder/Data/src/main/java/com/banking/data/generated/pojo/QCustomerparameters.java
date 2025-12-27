package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCustomerparameters is a Querydsl query type for QCustomerparameters
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCustomerparameters extends com.mysema.query.sql.RelationalPathBase<QCustomerparameters> {

    private static final long serialVersionUID = 1138210077;

    public static final QCustomerparameters customerparameters = new QCustomerparameters("customerparameters");

    public final StringPath colname = createString("colname");

    public final StringPath tablename = createString("tablename");

    public QCustomerparameters(String variable) {
        super(QCustomerparameters.class, forVariable(variable), "null", "customerparameters");
    }

    @SuppressWarnings("all")
    public QCustomerparameters(Path<? extends QCustomerparameters> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "customerparameters");
    }

    public QCustomerparameters(PathMetadata<?> metadata) {
        super(QCustomerparameters.class, metadata, "null", "customerparameters");
    }

}

