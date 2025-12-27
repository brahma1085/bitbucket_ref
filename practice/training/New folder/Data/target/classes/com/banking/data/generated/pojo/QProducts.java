package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QProducts is a Querydsl query type for QProducts
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QProducts extends com.mysema.query.sql.RelationalPathBase<QProducts> {

    private static final long serialVersionUID = 1081350265;

    public static final QProducts products = new QProducts("products");

    public final StringPath dpdlDate = createString("dpdl_date");

    public final StringPath prodDate = createString("prod_date");

    public final StringPath rinveProdDate = createString("rinve_prod_date");

    public QProducts(String variable) {
        super(QProducts.class, forVariable(variable), "null", "products");
    }

    @SuppressWarnings("all")
    public QProducts(Path<? extends QProducts> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "products");
    }

    public QProducts(PathMetadata<?> metadata) {
        super(QProducts.class, metadata, "null", "products");
    }

}

