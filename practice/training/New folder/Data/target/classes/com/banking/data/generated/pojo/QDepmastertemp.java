package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDepmastertemp is a Querydsl query type for QDepmastertemp
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDepmastertemp extends com.mysema.query.sql.RelationalPathBase<QDepmastertemp> {

    private static final long serialVersionUID = 460867728;

    public static final QDepmastertemp depmastertemp = new QDepmastertemp("depmastertemp");

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

    public QDepmastertemp(String variable) {
        super(QDepmastertemp.class, forVariable(variable), "null", "depmastertemp");
    }

    @SuppressWarnings("all")
    public QDepmastertemp(Path<? extends QDepmastertemp> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "depmastertemp");
    }

    public QDepmastertemp(PathMetadata<?> metadata) {
        super(QDepmastertemp.class, metadata, "null", "depmastertemp");
    }

}

