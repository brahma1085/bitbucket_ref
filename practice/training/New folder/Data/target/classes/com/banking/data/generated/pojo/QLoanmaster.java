package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLoanmaster is a Querydsl query type for QLoanmaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLoanmaster extends com.mysema.query.sql.RelationalPathBase<QLoanmaster> {

    private static final long serialVersionUID = -1161342681;

    public static final QLoanmaster loanmaster = new QLoanmaster("loanmaster");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath addrType = createString("addr_type");

    public final StringPath appnDate = createString("appn_date");

    public final NumberPath<Integer> appnSrl = createNumber("appn_srl", Integer.class);

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final StringPath closeDate = createString("close_date");

    public final StringPath convDate = createString("conv_date");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath defaultInd = createString("default_ind");

    public final NumberPath<Integer> dirCode = createNumber("dir_code", Integer.class);

    public final NumberPath<Double> disbLeft = createNumber("disb_left", Double.class);

    public final NumberPath<Integer> holdayMth = createNumber("holday_mth", Integer.class);

    public final NumberPath<Double> instAmt = createNumber("inst_amt", Double.class);

    public final NumberPath<Double> intRate = createNumber("int_rate", Double.class);

    public final NumberPath<Integer> intRateType = createNumber("int_rate_type", Integer.class);

    public final NumberPath<Integer> intType = createNumber("int_type", Integer.class);

    public final StringPath intUptoDate = createString("int_upto_date");

    public final StringPath ldgprtDate = createString("ldgprt_date");

    public final StringPath loanSanc = createString("loan_sanc");

    public final NumberPath<Integer> lstTrSeq = createNumber("lst_tr_seq", Integer.class);

    public final StringPath lstTrnDate = createString("lst_trn_date");

    public final NumberPath<Integer> noCoborrowers = createNumber("no_coborrowers", Integer.class);

    public final NumberPath<Integer> noInst = createNumber("no_inst", Integer.class);

    public final NumberPath<Integer> noSurities = createNumber("no_surities", Integer.class);

    public final NumberPath<Integer> nomRegNo = createNumber("nom_reg_no", Integer.class);

    public final StringPath npaDate = createString("npa_date");

    public final StringPath npaStg = createString("npa_stg");

    public final NumberPath<Integer> payAcNo = createNumber("pay_ac_no", Integer.class);

    public final StringPath payAcType = createString("pay_ac_type");

    public final StringPath payMode = createString("pay_mode");

    public final NumberPath<Integer> ppsCode = createNumber("pps_code", Integer.class);

    public final StringPath psectCd = createString("psect_cd");

    public final StringPath rel = createString("rel");

    public final StringPath remdDate = createString("remd_date");

    public final NumberPath<Integer> remdNo = createNumber("remd_no", Integer.class);

    public final NumberPath<Double> reqAmt = createNumber("req_amt", Double.class);

    public final NumberPath<Double> sancAmt = createNumber("sanc_amt", Double.class);

    public final StringPath sancDate = createString("sanc_date");

    public final StringPath sancVer = createString("sanc_ver");

    public final StringPath sexCd = createString("sex_cd");

    public final NumberPath<Integer> shNo = createNumber("sh_no", Integer.class);

    public final StringPath shType = createString("sh_type");

    public final NumberPath<Integer> tdAcNo = createNumber("td_ac_no", Integer.class);

    public final StringPath tdAcType = createString("td_ac_type");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public final StringPath wkSect = createString("wk_sect");

    public final com.mysema.query.sql.PrimaryKey<QLoanmaster> primary = createPrimaryKey(acNo, acType);

    public QLoanmaster(String variable) {
        super(QLoanmaster.class, forVariable(variable), "null", "loanmaster");
    }

    @SuppressWarnings("all")
    public QLoanmaster(Path<? extends QLoanmaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "loanmaster");
    }

    public QLoanmaster(PathMetadata<?> metadata) {
        super(QLoanmaster.class, metadata, "null", "loanmaster");
    }

}

