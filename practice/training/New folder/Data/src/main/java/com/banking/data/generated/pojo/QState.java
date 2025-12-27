package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QState is a Querydsl query type for QState
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QState extends com.mysema.query.sql.RelationalPathBase<QState> {

    private static final long serialVersionUID = -1352344164;

    public static final QState state1 = new QState("state");

    public final StringPath code = createString("code");

    public final StringPath state = createString("state");

    public QState(String variable) {
        super(QState.class, forVariable(variable), "null", "state");
    }

    @SuppressWarnings("all")
    public QState(Path<? extends QState> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "state");
    }

    public QState(PathMetadata<?> metadata) {
        super(QState.class, metadata, "null", "state");
    }

}

