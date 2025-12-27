package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QModules is a Querydsl query type for QModules
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QModules extends com.mysema.query.sql.RelationalPathBase<QModules> {

    private static final long serialVersionUID = 601958866;

    public static final QModules modules = new QModules("modules");

    public final NumberPath<Integer> bottomMargin = createNumber("bottom_margin", Integer.class);

    public final NumberPath<Integer> chqValidityPeriod = createNumber("chq_validity_period", Integer.class);

    public final NumberPath<Double> commRateForAmt = createNumber("comm_rate_for_amt", Double.class);

    public final StringPath deDtTime = createString("de_dt_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> inspectionPeriod = createNumber("inspection_period", Integer.class);

    public final NumberPath<Double> intRate = createNumber("int_rate", Double.class);

    public final NumberPath<Integer> intfromDay = createNumber("intfrom_day", Integer.class);

    public final NumberPath<Integer> inttoDay = createNumber("intto_day", Integer.class);

    public final NumberPath<Integer> lastTrfScrollNo = createNumber("last_trf_scroll_no", Integer.class);

    public final NumberPath<Integer> linesPerPage = createNumber("lines_per_page", Integer.class);

    public final NumberPath<Integer> lnModulecode = createNumber("ln_modulecode", Integer.class);

    public final NumberPath<Double> lnkShares = createNumber("lnk_shares", Double.class);

    public final NumberPath<Integer> lstAccNo = createNumber("lst_acc_no", Integer.class);

    public final StringPath lstIntdt = createString("lst_intdt");

    public final NumberPath<Integer> lstRctNo = createNumber("lst_rct_no", Integer.class);

    public final NumberPath<Integer> lstVoucherNo = createNumber("lst_voucher_no", Integer.class);

    public final NumberPath<Integer> lstVoucherScrollNo = createNumber("lst_voucher_scroll_no", Integer.class);

    public final NumberPath<Double> maxAmt = createNumber("max_amt", Double.class);

    public final NumberPath<Double> maxAmtCheque = createNumber("max_amt_cheque", Double.class);

    public final NumberPath<Double> maxAmtClg = createNumber("max_amt_clg", Double.class);

    public final NumberPath<Double> maxCommRate = createNumber("max_comm_rate", Double.class);

    public final NumberPath<Integer> maxRenewalCount = createNumber("max_renewal_count", Integer.class);

    public final NumberPath<Short> maxRenewalDays = createNumber("max_renewal_days", Short.class);

    public final NumberPath<Double> minAmtCheque = createNumber("min_amt_cheque", Double.class);

    public final NumberPath<Double> minAmtClg = createNumber("min_amt_clg", Double.class);

    public final NumberPath<Double> minBal = createNumber("min_bal", Double.class);

    public final NumberPath<Integer> minPeriod = createNumber("min_period", Integer.class);

    public final StringPath moduleabbr = createString("moduleabbr");

    public final NumberPath<Integer> modulecode = createNumber("modulecode", Integer.class);

    public final StringPath moduledesc = createString("moduledesc");

    public final StringPath otherProp = createString("other_prop");

    public final NumberPath<Integer> passBkLines = createNumber("pass_bk_lines", Integer.class);

    public final NumberPath<Double> penaltyRate = createNumber("penalty_rate", Double.class);

    public final NumberPath<Double> renewalIntRate = createNumber("renewal_int_rate", Double.class);

    public final NumberPath<Integer> stdInst = createNumber("std_inst", Integer.class);

    public final NumberPath<Integer> topMargin = createNumber("top_margin", Integer.class);

    public final NumberPath<Integer> trnsPerMnth = createNumber("trns_per_mnth", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QModules> primary = createPrimaryKey(modulecode);

    public QModules(String variable) {
        super(QModules.class, forVariable(variable), "null", "modules");
    }

    @SuppressWarnings("all")
    public QModules(Path<? extends QModules> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "modules");
    }

    public QModules(PathMetadata<?> metadata) {
        super(QModules.class, metadata, "null", "modules");
    }

}

