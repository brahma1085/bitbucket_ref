package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QShareparamhistory is a Querydsl query type for QShareparamhistory
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QShareparamhistory extends com.mysema.query.sql.RelationalPathBase<QShareparamhistory> {

    private static final long serialVersionUID = 141155665;

    public static final QShareparamhistory shareparamhistory = new QShareparamhistory("shareparamhistory");

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> memCat = createNumber("mem_cat", Integer.class);

    public final NumberPath<Double> prmAmt = createNumber("prm_amt", Double.class);

    public final NumberPath<Integer> prmCode = createNumber("prm_code", Integer.class);

    public final StringPath prmDesc = createString("prm_desc");

    public final StringPath prmFreq = createString("prm_freq");

    public final StringPath prmGlKey = createString("prm_gl_key");

    public final StringPath prmTy = createString("prm_ty");

    public QShareparamhistory(String variable) {
        super(QShareparamhistory.class, forVariable(variable), "null", "shareparamhistory");
    }

    @SuppressWarnings("all")
    public QShareparamhistory(Path<? extends QShareparamhistory> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "shareparamhistory");
    }

    public QShareparamhistory(PathMetadata<?> metadata) {
        super(QShareparamhistory.class, metadata, "null", "shareparamhistory");
    }

}

