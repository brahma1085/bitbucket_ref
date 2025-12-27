package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QGltranold is a Querydsl query type for QGltranold
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QGltranold extends com.mysema.query.sql.RelationalPathBase<QGltranold> {

    private static final long serialVersionUID = 1202592290;

    public static final QGltranold gltranold = new QGltranold("gltranold");

    public final StringPath cdInd = createString("cd_ind");

    public final NumberPath<Integer> glCode = createNumber("gl_code", Integer.class);

    public final StringPath glType = createString("gl_type");

    public final NumberPath<Integer> refAcNo = createNumber("ref_ac_no", Integer.class);

    public final StringPath refAcType = createString("ref_ac_type");

    public final NumberPath<Integer> refNo = createNumber("ref_no", Integer.class);

    public final NumberPath<Integer> refTrSeq = createNumber("ref_tr_seq", Integer.class);

    public final StringPath refTrType = createString("ref_tr_type");

    public final NumberPath<Double> trnAmt = createNumber("trn_amt", Double.class);

    public final StringPath trnDate = createString("trn_date");

    public final StringPath trnMode = createString("trn_mode");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QGltranold(String variable) {
        super(QGltranold.class, forVariable(variable), "null", "gltranold");
    }

    @SuppressWarnings("all")
    public QGltranold(Path<? extends QGltranold> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "gltranold");
    }

    public QGltranold(PathMetadata<?> metadata) {
        super(QGltranold.class, metadata, "null", "gltranold");
    }

}

