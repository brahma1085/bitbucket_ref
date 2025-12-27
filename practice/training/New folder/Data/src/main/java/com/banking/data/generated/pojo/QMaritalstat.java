package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QMaritalstat is a Querydsl query type for QMaritalstat
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QMaritalstat extends com.mysema.query.sql.RelationalPathBase<QMaritalstat> {

    private static final long serialVersionUID = 437359123;

    public static final QMaritalstat maritalstat = new QMaritalstat("maritalstat");

    public final StringPath marital = createString("marital");

    public QMaritalstat(String variable) {
        super(QMaritalstat.class, forVariable(variable), "null", "maritalstat");
    }

    @SuppressWarnings("all")
    public QMaritalstat(Path<? extends QMaritalstat> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "maritalstat");
    }

    public QMaritalstat(PathMetadata<?> metadata) {
        super(QMaritalstat.class, metadata, "null", "maritalstat");
    }

}

