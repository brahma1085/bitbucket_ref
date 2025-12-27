package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QFormsdetail is a Querydsl query type for QFormsdetail
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QFormsdetail extends com.mysema.query.sql.RelationalPathBase<QFormsdetail> {

    private static final long serialVersionUID = -751867381;

    public static final QFormsdetail formsdetail = new QFormsdetail("formsdetail");

    public final StringPath formcode = createString("formcode");

    public final StringPath formname = createString("formname");

    public final StringPath pageId = createString("pageId");

    public QFormsdetail(String variable) {
        super(QFormsdetail.class, forVariable(variable), "null", "formsdetail");
    }

    @SuppressWarnings("all")
    public QFormsdetail(Path<? extends QFormsdetail> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "formsdetail");
    }

    public QFormsdetail(PathMetadata<?> metadata) {
        super(QFormsdetail.class, metadata, "null", "formsdetail");
    }

}

