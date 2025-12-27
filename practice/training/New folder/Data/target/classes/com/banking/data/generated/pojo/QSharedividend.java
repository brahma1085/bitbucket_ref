package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSharedividend is a Querydsl query type for QSharedividend
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSharedividend extends com.mysema.query.sql.RelationalPathBase<QSharedividend> {

    private static final long serialVersionUID = 1237003993;

    public static final QSharedividend sharedividend = new QSharedividend("sharedividend");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath cdInd = createString("cd_ind");

    public final StringPath cvDt = createString("cv_dt");

    public final NumberPath<Integer> cvNo = createNumber("cv_no", Integer.class);

    public final StringPath cvPaid = createString("cv_paid");

    public final StringPath cvTy = createString("cv_ty");

    public final NumberPath<Float> ddnAmt = createNumber("ddn_amt", Float.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> divAcBr = createNumber("div_ac_br", Integer.class);

    public final NumberPath<Integer> divAcNo = createNumber("div_ac_no", Integer.class);

    public final StringPath divAcTy = createString("div_ac_ty");

    public final NumberPath<Float> divAmt = createNumber("div_amt", Float.class);

    public final StringPath divDt = createString("div_dt");

    public final StringPath payMode = createString("pay_mode");

    public final StringPath paydivDt = createString("paydiv_dt");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public final NumberPath<Integer> voucherNo = createNumber("voucher_no", Integer.class);

    public final NumberPath<Float> wrtAmt = createNumber("wrt_amt", Float.class);

    public final StringPath wrtCshd = createString("wrt_cshd");

    public final StringPath wrtDt = createString("wrt_dt");

    public final NumberPath<Integer> wrtNo = createNumber("wrt_no", Integer.class);

    public final StringPath wrtPrtd = createString("wrt_prtd");

    public QSharedividend(String variable) {
        super(QSharedividend.class, forVariable(variable), "null", "sharedividend");
    }

    @SuppressWarnings("all")
    public QSharedividend(Path<? extends QSharedividend> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "sharedividend");
    }

    public QSharedividend(PathMetadata<?> metadata) {
        super(QSharedividend.class, metadata, "null", "sharedividend");
    }

}

