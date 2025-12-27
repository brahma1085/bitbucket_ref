package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDeposittransaction is a Querydsl query type for QDeposittransaction
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDeposittransaction extends com.mysema.query.sql.RelationalPathBase<QDeposittransaction> {

    private static final long serialVersionUID = -284017067;

    public static final QDeposittransaction deposittransaction = new QDeposittransaction("deposittransaction");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath cdInd = createString("cd_ind");

    public final NumberPath<Double> cumInt = createNumber("cum_int", Double.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> depAmt = createNumber("dep_amt", Double.class);

    public final NumberPath<Double> depPaid = createNumber("dep_paid", Double.class);

    public final NumberPath<Double> intAmt = createNumber("int_amt", Double.class);

    public final StringPath intDate = createString("int_date");

    public final NumberPath<Double> intPaid = createNumber("int_paid", Double.class);

    public final StringPath paidDate = createString("paid_date");

    public final NumberPath<Double> rdBal = createNumber("rd_bal", Double.class);

    public final NumberPath<Integer> refNo = createNumber("ref_no", Integer.class);

    public final StringPath trnDate = createString("trn_date");

    public final StringPath trnMode = createString("trn_mode");

    public final StringPath trnNarr = createString("trn_narr");

    public final NumberPath<Integer> trnSeq = createNumber("trn_seq", Integer.class);

    public final StringPath trnSource = createString("trn_source");

    public final StringPath trnType = createString("trn_type");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QDeposittransaction(String variable) {
        super(QDeposittransaction.class, forVariable(variable), "null", "deposittransaction");
    }

    @SuppressWarnings("all")
    public QDeposittransaction(Path<? extends QDeposittransaction> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "deposittransaction");
    }

    public QDeposittransaction(PathMetadata<?> metadata) {
        super(QDeposittransaction.class, metadata, "null", "deposittransaction");
    }

}

