package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRelations is a Querydsl query type for QRelations
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QRelations extends com.mysema.query.sql.RelationalPathBase<QRelations> {

    private static final long serialVersionUID = 206299522;

    public static final QRelations relations = new QRelations("relations");

    public final StringPath type = createString("type");

    public QRelations(String variable) {
        super(QRelations.class, forVariable(variable), "null", "relations");
    }

    @SuppressWarnings("all")
    public QRelations(Path<? extends QRelations> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "relations");
    }

    public QRelations(PathMetadata<?> metadata) {
        super(QRelations.class, metadata, "null", "relations");
    }

}

