package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QFormwiseaccess is a Querydsl query type for QFormwiseaccess
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QFormwiseaccess extends com.mysema.query.sql.RelationalPathBase<QFormwiseaccess> {

    private static final long serialVersionUID = 631055905;

    public static final QFormwiseaccess formwiseaccess = new QFormwiseaccess("formwiseaccess");

    public final StringPath access = createString("access");

    public final StringPath deDate = createString("de_date");

    public final StringPath deUser = createString("de_user");

    public final StringPath formcode = createString("formcode");

    public final StringPath roleCode = createString("role_code");

    public QFormwiseaccess(String variable) {
        super(QFormwiseaccess.class, forVariable(variable), "null", "formwiseaccess");
    }

    @SuppressWarnings("all")
    public QFormwiseaccess(Path<? extends QFormwiseaccess> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "formwiseaccess");
    }

    public QFormwiseaccess(PathMetadata<?> metadata) {
        super(QFormwiseaccess.class, metadata, "null", "formwiseaccess");
    }

}

