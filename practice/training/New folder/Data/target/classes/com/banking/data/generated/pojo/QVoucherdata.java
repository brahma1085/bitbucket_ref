package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QVoucherdata is a Querydsl query type for QVoucherdata
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QVoucherdata extends com.mysema.query.sql.RelationalPathBase<QVoucherdata> {

    private static final long serialVersionUID = -1946395613;

    public static final QVoucherdata voucherdata = new QVoucherdata("voucherdata");

    public final StringPath cashPdrd = createString("cash_pdrd");

    public final StringPath cdInd = createString("cd_ind");

    public final StringPath chqDt = createString("chq_dt");

    public final NumberPath<Integer> chqNo = createNumber("chq_no", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> glSlCode = createNumber("gl_sl_code", Integer.class);

    public final StringPath glSlType = createString("gl_sl_type");

    public final NumberPath<Integer> modAcNo = createNumber("mod_ac_no", Integer.class);

    public final StringPath modAcType = createString("mod_ac_type");

    public final StringPath narration = createString("narration");

    public final NumberPath<Double> trnAmt = createNumber("trn_amt", Double.class);

    public final StringPath trnDate = createString("trn_date");

    public final StringPath trnType = createString("trn_type");

    public final StringPath vchMode = createString("vch_mode");

    public final NumberPath<Integer> vchNo = createNumber("vch_no", Integer.class);

    public final StringPath vchType = createString("vch_type");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QVoucherdata(String variable) {
        super(QVoucherdata.class, forVariable(variable), "null", "voucherdata");
    }

    @SuppressWarnings("all")
    public QVoucherdata(Path<? extends QVoucherdata> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "voucherdata");
    }

    public QVoucherdata(PathMetadata<?> metadata) {
        super(QVoucherdata.class, metadata, "null", "voucherdata");
    }

}

