package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QProductslog is a Querydsl query type for QProductslog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QProductslog extends com.mysema.query.sql.RelationalPathBase<QProductslog> {

    private static final long serialVersionUID = -2043835349;

    public static final QProductslog productslog = new QProductslog("productslog");

    public final StringPath dpdlDate = createString("dpdl_date");

    public final StringPath prodDate = createString("prod_date");

    public final StringPath rinveProdDate = createString("rinve_prod_date");

    public QProductslog(String variable) {
        super(QProductslog.class, forVariable(variable), "null", "productslog");
    }

    @SuppressWarnings("all")
    public QProductslog(Path<? extends QProductslog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "productslog");
    }

    public QProductslog(PathMetadata<?> metadata) {
        super(QProductslog.class, metadata, "null", "productslog");
    }

}

