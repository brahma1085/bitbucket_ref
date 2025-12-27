package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDirectorrelation is a Querydsl query type for QDirectorrelation
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDirectorrelation extends com.mysema.query.sql.RelationalPathBase<QDirectorrelation> {

    private static final long serialVersionUID = -1045011555;

    public static final QDirectorrelation directorrelation = new QDirectorrelation("directorrelation");

    public final NumberPath<Integer> relCode = createNumber("rel_code", Integer.class);

    public final StringPath relationType = createString("relation_type");

    public QDirectorrelation(String variable) {
        super(QDirectorrelation.class, forVariable(variable), "null", "directorrelation");
    }

    @SuppressWarnings("all")
    public QDirectorrelation(Path<? extends QDirectorrelation> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "directorrelation");
    }

    public QDirectorrelation(PathMetadata<?> metadata) {
        super(QDirectorrelation.class, metadata, "null", "directorrelation");
    }

}

