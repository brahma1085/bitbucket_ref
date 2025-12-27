package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QNpatable is a Querydsl query type for QNpatable
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QNpatable extends com.mysema.query.sql.RelationalPathBase<QNpatable> {

    private static final long serialVersionUID = -270939292;

    public static final QNpatable npatable = new QNpatable("npatable");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> assetCode = createNumber("asset_code", Integer.class);

    public final NumberPath<Integer> days = createNumber("days", Integer.class);

    public final NumberPath<Double> intOdueAmt = createNumber("int_odue_amt", Double.class);

    public final NumberPath<Integer> intOduePrd = createNumber("int_odue_prd", Integer.class);

    public final StringPath intUptoDate = createString("int_upto_date");

    public final StringPath lastTrnDate = createString("last_trn_date");

    public final NumberPath<Double> loanBalance = createNumber("loan_balance", Double.class);

    public final StringPath npaProDate = createString("npa_pro_date");

    public final StringPath npaTowards = createString("npa_towards");

    public final StringPath prOdueFrom = createString("pr_odue_from");

    public final NumberPath<Double> priOdueAmt = createNumber("pri_odue_amt", Double.class);

    public final NumberPath<Integer> priOduePrd = createNumber("pri_odue_prd", Integer.class);

    public final NumberPath<Double> provAmt = createNumber("prov_amt", Double.class);

    public QNpatable(String variable) {
        super(QNpatable.class, forVariable(variable), "null", "npatable");
    }

    @SuppressWarnings("all")
    public QNpatable(Path<? extends QNpatable> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "npatable");
    }

    public QNpatable(PathMetadata<?> metadata) {
        super(QNpatable.class, metadata, "null", "npatable");
    }

}

