package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSalaried is a Querydsl query type for QSalaried
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSalaried extends com.mysema.query.sql.RelationalPathBase<QSalaried> {

    private static final long serialVersionUID = -276588818;

    public static final QSalaried salaried = new QSalaried("salaried");

    public final StringPath work = createString("work");

    public QSalaried(String variable) {
        super(QSalaried.class, forVariable(variable), "null", "salaried");
    }

    @SuppressWarnings("all")
    public QSalaried(Path<? extends QSalaried> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "salaried");
    }

    public QSalaried(PathMetadata<?> metadata) {
        super(QSalaried.class, metadata, "null", "salaried");
    }

}

