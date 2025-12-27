package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QNarration is a Querydsl query type for QNarration
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QNarration extends com.mysema.query.sql.RelationalPathBase<QNarration> {

    private static final long serialVersionUID = 1964401453;

    public static final QNarration narration = new QNarration("narration");

    public final StringPath narr = createString("narr");

    public final NumberPath<Integer> seqNo = createNumber("seq_no", Integer.class);

    public final StringPath trnDt = createString("trn_dt");

    public final NumberPath<Integer> vchNo = createNumber("vch_no", Integer.class);

    public final StringPath vchTy = createString("vch_ty");

    public QNarration(String variable) {
        super(QNarration.class, forVariable(variable), "null", "narration");
    }

    @SuppressWarnings("all")
    public QNarration(Path<? extends QNarration> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "narration");
    }

    public QNarration(PathMetadata<?> metadata) {
        super(QNarration.class, metadata, "null", "narration");
    }

}

