package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCastes is a Querydsl query type for QCastes
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCastes extends com.mysema.query.sql.RelationalPathBase<QCastes> {

    private static final long serialVersionUID = 551926914;

    public static final QCastes castes = new QCastes("castes");

    public final StringPath scst = createString("scst");

    public QCastes(String variable) {
        super(QCastes.class, forVariable(variable), "null", "castes");
    }

    @SuppressWarnings("all")
    public QCastes(Path<? extends QCastes> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "castes");
    }

    public QCastes(PathMetadata<?> metadata) {
        super(QCastes.class, metadata, "null", "castes");
    }

}

