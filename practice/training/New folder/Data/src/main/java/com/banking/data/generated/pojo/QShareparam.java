package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QShareparam is a Querydsl query type for QShareparam
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QShareparam extends com.mysema.query.sql.RelationalPathBase<QShareparam> {

    private static final long serialVersionUID = 564638179;

    public static final QShareparam shareparam = new QShareparam("shareparam");

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> memCat = createNumber("mem_cat", Integer.class);

    public final NumberPath<Double> prmAmt = createNumber("prm_amt", Double.class);

    public final NumberPath<Integer> prmCode = createNumber("prm_code", Integer.class);

    public final StringPath prmDesc = createString("prm_desc");

    public final StringPath prmFreq = createString("prm_freq");

    public final StringPath prmGlCode = createString("prm_gl_code");

    public final StringPath prmGlType = createString("prm_gl_type");

    public final StringPath prmTy = createString("prm_ty");

    public final com.mysema.query.sql.PrimaryKey<QShareparam> primary = createPrimaryKey(acType, memCat, prmCode);

    public QShareparam(String variable) {
        super(QShareparam.class, forVariable(variable), "null", "shareparam");
    }

    @SuppressWarnings("all")
    public QShareparam(Path<? extends QShareparam> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "shareparam");
    }

    public QShareparam(PathMetadata<?> metadata) {
        super(QShareparam.class, metadata, "null", "shareparam");
    }

}

