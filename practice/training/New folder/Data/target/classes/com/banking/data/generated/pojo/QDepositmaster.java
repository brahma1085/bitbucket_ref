package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDepositmaster is a Querydsl query type for QDepositmaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDepositmaster extends com.mysema.query.sql.RelationalPathBase<QDepositmaster> {

    private static final long serialVersionUID = 1858482315;

    public static final QDepositmaster depositmaster = new QDepositmaster("depositmaster");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> addType = createNumber("add_type", Integer.class);

    public final StringPath autoRenewal = createString("auto_renewal");

    public final NumberPath<Integer> autorenewalNo = createNumber("autorenewal_no", Integer.class);

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final StringPath closeDate = createString("close_date");

    public final NumberPath<Integer> closeInd = createNumber("close_ind", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> depAmt = createNumber("dep_amt", Double.class);

    public final StringPath depDate = createString("dep_date");

    public final NumberPath<Integer> depDays = createNumber("dep_days", Integer.class);

    public final NumberPath<Integer> depMths = createNumber("dep_mths", Integer.class);

    public final StringPath depRenewed = createString("dep_renewed");

    public final NumberPath<Integer> depYrs = createNumber("dep_yrs", Integer.class);

    public final NumberPath<Double> excAmt = createNumber("exc_amt", Double.class);

    public final NumberPath<Integer> extraRateType = createNumber("extra_rate_type", Integer.class);

    public final StringPath intFreq = createString("int_freq");

    public final StringPath intMode = createString("int_mode");

    public final StringPath intPaidDate = createString("int_paid_date");

    public final NumberPath<Double> intRate = createNumber("int_rate", Double.class);

    public final StringPath intUptoDate = createString("int_upto_date");

    public final NumberPath<Integer> intrAcNo = createNumber("intr_ac_no", Integer.class);

    public final StringPath intrAcType = createString("intr_ac_type");

    public final StringPath ldadjreq = createString("ldadjreq");

    public final StringPath ldgprtdt = createString("ldgprtdt");

    public final NumberPath<Integer> lnAcNo = createNumber("ln_ac_no", Integer.class);

    public final StringPath lnAcType = createString("ln_ac_type");

    public final StringPath lnAvailed = createString("ln_availed");

    public final NumberPath<Integer> lstPrSeq = createNumber("lst_pr_seq", Integer.class);

    public final NumberPath<Integer> lstTrnSeq = createNumber("lst_trn_seq", Integer.class);

    public final NumberPath<Double> matAmt = createNumber("mat_amt", Double.class);

    public final StringPath matDate = createString("mat_date");

    public final StringPath matPost = createString("mat_post");

    public final StringPath newRct = createString("new_rct");

    public final StringPath nextPayDate = createString("next_pay_date");

    public final NumberPath<Integer> noJtHldr = createNumber("no_jt_hldr", Integer.class);

    public final NumberPath<Integer> nomNo = createNumber("nom_no", Integer.class);

    public final StringPath postDate = createString("post_date");

    public final NumberPath<Integer> rctNo = createNumber("rct_no", Integer.class);

    public final StringPath rctPrtd = createString("rct_prtd");

    public final StringPath rctSign = createString("rct_sign");

    public final NumberPath<Integer> rcvdAcNo = createNumber("rcvd_ac_no", Integer.class);

    public final StringPath rcvdAcType = createString("rcvd_ac_type");

    public final StringPath rcvdBy = createString("rcvd_by");

    public final NumberPath<Integer> renAcNo = createNumber("ren_ac_no", Integer.class);

    public final StringPath renAcType = createString("ren_ac_type");

    public final NumberPath<Integer> trfAcNo = createNumber("trf_ac_no", Integer.class);

    public final StringPath trfAcType = createString("trf_ac_type");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public final com.mysema.query.sql.PrimaryKey<QDepositmaster> primary = createPrimaryKey(acNo, acType);

    public QDepositmaster(String variable) {
        super(QDepositmaster.class, forVariable(variable), "null", "depositmaster");
    }

    @SuppressWarnings("all")
    public QDepositmaster(Path<? extends QDepositmaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "depositmaster");
    }

    public QDepositmaster(PathMetadata<?> metadata) {
        super(QDepositmaster.class, metadata, "null", "depositmaster");
    }

}

