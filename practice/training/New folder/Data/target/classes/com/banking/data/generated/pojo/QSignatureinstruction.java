package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSignatureinstruction is a Querydsl query type for QSignatureinstruction
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSignatureinstruction extends com.mysema.query.sql.RelationalPathBase<QSignatureinstruction> {

    private static final long serialVersionUID = 462454891;

    public static final QSignatureinstruction signatureinstruction = new QSignatureinstruction("signatureinstruction");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final StringPath desg = createString("desg");

    public final NumberPath<Integer> maxlmt = createNumber("maxlmt", Integer.class);

    public final NumberPath<Integer> minlmt = createNumber("minlmt", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath typeofopr = createString("typeofopr");

    public QSignatureinstruction(String variable) {
        super(QSignatureinstruction.class, forVariable(variable), "null", "signatureinstruction");
    }

    @SuppressWarnings("all")
    public QSignatureinstruction(Path<? extends QSignatureinstruction> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "signatureinstruction");
    }

    public QSignatureinstruction(PathMetadata<?> metadata) {
        super(QSignatureinstruction.class, metadata, "null", "signatureinstruction");
    }

}

