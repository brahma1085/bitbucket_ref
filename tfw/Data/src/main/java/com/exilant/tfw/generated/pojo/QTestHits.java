package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTestHits is a Querydsl query type for QTestHits
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTestHits extends com.mysema.query.sql.RelationalPathBase<QTestHits> {

    private static final long serialVersionUID = 580166731;

    public static final QTestHits testHits = new QTestHits("test_hits");

    public final StringPath deviceId = createString("DeviceId");

    public final DatePath<java.sql.Date> logTime = createDate("LogTime", java.sql.Date.class);

    public QTestHits(String variable) {
        super(QTestHits.class, forVariable(variable), "null", "test_hits");
    }

    @SuppressWarnings("all")
    public QTestHits(Path<? extends QTestHits> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "test_hits");
    }

    public QTestHits(PathMetadata<?> metadata) {
        super(QTestHits.class, metadata, "null", "test_hits");
    }

}

