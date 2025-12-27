package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QGltransaction is a Querydsl query type for QGltransaction
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QGltransaction extends com.mysema.query.sql.RelationalPathBase<QGltransaction> {

    private static final long serialVersionUID = -236186908;

    public static final QGltransaction gltransaction = new QGltransaction("gltransaction");

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

    public QGltransaction(String variable) {
        super(QGltransaction.class, forVariable(variable), "null", "gltransaction");
    }

    @SuppressWarnings("all")
    public QGltransaction(Path<? extends QGltransaction> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "gltransaction");
    }

    public QGltransaction(PathMetadata<?> metadata) {
        super(QGltransaction.class, metadata, "null", "gltransaction");
    }

}

