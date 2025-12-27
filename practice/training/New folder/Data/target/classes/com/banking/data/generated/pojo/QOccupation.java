package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QOccupation is a Querydsl query type for QOccupation
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QOccupation extends com.mysema.query.sql.RelationalPathBase<QOccupation> {

    private static final long serialVersionUID = -342147296;

    public static final QOccupation occupation1 = new QOccupation("occupation");

    public final StringPath occupation = createString("occupation");

    public QOccupation(String variable) {
        super(QOccupation.class, forVariable(variable), "null", "occupation");
    }

    @SuppressWarnings("all")
    public QOccupation(Path<? extends QOccupation> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "occupation");
    }

    public QOccupation(PathMetadata<?> metadata) {
        super(QOccupation.class, metadata, "null", "occupation");
    }

}

