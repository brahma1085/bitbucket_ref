package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QOdccmasterlog is a Querydsl query type for QOdccmasterlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QOdccmasterlog extends com.mysema.query.sql.RelationalPathBase<QOdccmasterlog> {

    private static final long serialVersionUID = -1035538856;

    public static final QOdccmasterlog odccmasterlog = new QOdccmasterlog("odccmasterlog");

    public final StringPath acClosedate = createString("ac_closedate");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acOpendate = createString("ac_opendate");

    public final StringPath acStatus = createString("ac_status");

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> addrType = createNumber("addr_type", Integer.class);

    public final StringPath appnDate = createString("appn_date");

    public final NumberPath<Integer> appnSrl = createNumber("appn_srl", Integer.class);

    public final StringPath chBkIssue = createString("ch_bk_issue");

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final StringPath convDt = createString("conv_dt");

    public final NumberPath<Double> creditlimit = createNumber("creditlimit", Double.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath defaultInd = createString("default_ind");

    public final NumberPath<Integer> dirCode = createNumber("dir_code", Integer.class);

    public final StringPath freezeInd = createString("freeze_ind");

    public final NumberPath<Double> intRate = createNumber("int_rate", Double.class);

    public final NumberPath<Integer> intRateType = createNumber("int_rate_type", Integer.class);

    public final StringPath intUptodt = createString("int_uptodt");

    public final NumberPath<Integer> lastLine = createNumber("last_line", Integer.class);

    public final StringPath lastTrDate = createString("last_tr_date");

    public final NumberPath<Integer> lastTrSeq = createNumber("last_tr_seq", Integer.class);

    public final NumberPath<Integer> ledgerSeq = createNumber("ledger_seq", Integer.class);

    public final StringPath limitUpto = createString("limit_upto");

    public final StringPath loanSanc = createString("loan_sanc");

    public final NumberPath<Integer> noCoborrowers = createNumber("no_coborrowers", Integer.class);

    public final NumberPath<Integer> noSurities = createNumber("no_surities", Integer.class);

    public final NumberPath<Integer> nomNo = createNumber("nom_no", Integer.class);

    public final StringPath npaDt = createString("npa_dt");

    public final StringPath npaStg = createString("npa_stg");

    public final NumberPath<Integer> period = createNumber("period", Integer.class);

    public final NumberPath<Integer> ppsCode = createNumber("pps_code", Integer.class);

    public final NumberPath<Integer> psBkSeq = createNumber("ps_bk_seq", Integer.class);

    public final NumberPath<Integer> psectCd = createNumber("psect_cd", Integer.class);

    public final StringPath rel = createString("rel");

    public final StringPath remdDt = createString("remd_dt");

    public final NumberPath<Integer> remdNo = createNumber("remd_no", Integer.class);

    public final NumberPath<Double> reqAmt = createNumber("req_amt", Double.class);

    public final NumberPath<Double> sancAmt = createNumber("sanc_amt", Double.class);

    public final StringPath sancDt = createString("sanc_dt");

    public final StringPath sancVer = createString("sanc_ver");

    public final StringPath sexCd = createString("sex_cd");

    public final NumberPath<Integer> shNo = createNumber("sh_no", Integer.class);

    public final StringPath shType = createString("sh_type");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public final NumberPath<Integer> wkSect = createNumber("wk_sect", Integer.class);

    public QOdccmasterlog(String variable) {
        super(QOdccmasterlog.class, forVariable(variable), "null", "odccmasterlog");
    }

    @SuppressWarnings("all")
    public QOdccmasterlog(Path<? extends QOdccmasterlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "odccmasterlog");
    }

    public QOdccmasterlog(PathMetadata<?> metadata) {
        super(QOdccmasterlog.class, metadata, "null", "odccmasterlog");
    }

}

