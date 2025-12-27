package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QGenparam is a Querydsl query type for QGenparam
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QGenparam extends com.mysema.query.sql.RelationalPathBase<QGenparam> {

    private static final long serialVersionUID = -389815726;

    public static final QGenparam genparam = new QGenparam("genparam");

    public final NumberPath<Integer> lstCustomerId = createNumber("lst_customer_id", Integer.class);

    public QGenparam(String variable) {
        super(QGenparam.class, forVariable(variable), "null", "genparam");
    }

    @SuppressWarnings("all")
    public QGenparam(Path<? extends QGenparam> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "genparam");
    }

    public QGenparam(PathMetadata<?> metadata) {
        super(QGenparam.class, metadata, "null", "genparam");
    }

}

