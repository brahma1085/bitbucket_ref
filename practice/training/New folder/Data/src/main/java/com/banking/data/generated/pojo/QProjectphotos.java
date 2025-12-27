package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QProjectphotos is a Querydsl query type for QProjectphotos
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QProjectphotos extends com.mysema.query.sql.RelationalPathBase<QProjectphotos> {

    private static final long serialVersionUID = 555104997;

    public static final QProjectphotos projectphotos = new QProjectphotos("projectphotos");

    public final SimplePath<byte[]> photo = createSimple("photo", byte[].class);

    public final StringPath photoName = createString("photo_name");

    public QProjectphotos(String variable) {
        super(QProjectphotos.class, forVariable(variable), "null", "projectphotos");
    }

    @SuppressWarnings("all")
    public QProjectphotos(Path<? extends QProjectphotos> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "projectphotos");
    }

    public QProjectphotos(PathMetadata<?> metadata) {
        super(QProjectphotos.class, metadata, "null", "projectphotos");
    }

}

