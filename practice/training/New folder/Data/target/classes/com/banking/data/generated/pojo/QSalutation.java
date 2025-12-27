package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSalutation is a Querydsl query type for QSalutation
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSalutation extends com.mysema.query.sql.RelationalPathBase<QSalutation> {

    private static final long serialVersionUID = 1106648045;

    public static final QSalutation salutation = new QSalutation("salutation");

    public final StringPath salute = createString("salute");

    public QSalutation(String variable) {
        super(QSalutation.class, forVariable(variable), "null", "salutation");
    }

    @SuppressWarnings("all")
    public QSalutation(Path<? extends QSalutation> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "salutation");
    }

    public QSalutation(PathMetadata<?> metadata) {
        super(QSalutation.class, metadata, "null", "salutation");
    }

}

