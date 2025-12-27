package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLoantransaction is a Querydsl query type for QLoantransaction
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLoantransaction extends com.mysema.query.sql.RelationalPathBase<QLoantransaction> {

    private static final long serialVersionUID = -1594286279;

    public static final QLoantransaction loantransaction = new QLoantransaction("loantransaction");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath cdInd = createString("cd_ind");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> extraInt = createNumber("extra_int", Double.class);

    public final NumberPath<Double> intAmt = createNumber("int_amt", Double.class);

    public final StringPath intDate = createString("int_date");

    public final NumberPath<Double> otherAmt = createNumber("other_amt", Double.class);

    public final NumberPath<Double> penalAmt = createNumber("penal_amt", Double.class);

    public final NumberPath<Double> prAmt = createNumber("pr_amt", Double.class);

    public final NumberPath<Double> prBal = createNumber("pr_bal", Double.class);

    public final StringPath rcyDate = createString("rcy_date");

    public final NumberPath<Integer> refNo = createNumber("ref_no", Integer.class);

    public final NumberPath<Double> trnAmt = createNumber("trn_amt", Double.class);

    public final StringPath trnDate = createString("trn_date");

    public final StringPath trnMode = createString("trn_mode");

    public final StringPath trnNarr = createString("trn_narr");

    public final NumberPath<Integer> trnSeq = createNumber("trn_seq", Integer.class);

    public final StringPath trnSource = createString("trn_source");

    public final StringPath trnType = createString("trn_type");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QLoantransaction(String variable) {
        super(QLoantransaction.class, forVariable(variable), "null", "loantransaction");
    }

    @SuppressWarnings("all")
    public QLoantransaction(Path<? extends QLoantransaction> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "loantransaction");
    }

    public QLoantransaction(PathMetadata<?> metadata) {
        super(QLoantransaction.class, metadata, "null", "loantransaction");
    }

}

