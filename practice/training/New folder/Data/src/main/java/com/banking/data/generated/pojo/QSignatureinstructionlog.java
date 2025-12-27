package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSignatureinstructionlog is a Querydsl query type for QSignatureinstructionlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSignatureinstructionlog extends com.mysema.query.sql.RelationalPathBase<QSignatureinstructionlog> {

    private static final long serialVersionUID = -1261320455;

    public static final QSignatureinstructionlog signatureinstructionlog = new QSignatureinstructionlog("signatureinstructionlog");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final StringPath desg = createString("desg");

    public final NumberPath<Integer> maxlmt = createNumber("maxlmt", Integer.class);

    public final NumberPath<Integer> minlmt = createNumber("minlmt", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath typeofopr = createString("typeofopr");

    public QSignatureinstructionlog(String variable) {
        super(QSignatureinstructionlog.class, forVariable(variable), "null", "signatureinstructionlog");
    }

    @SuppressWarnings("all")
    public QSignatureinstructionlog(Path<? extends QSignatureinstructionlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "signatureinstructionlog");
    }

    public QSignatureinstructionlog(PathMetadata<?> metadata) {
        super(QSignatureinstructionlog.class, metadata, "null", "signatureinstructionlog");
    }

}

