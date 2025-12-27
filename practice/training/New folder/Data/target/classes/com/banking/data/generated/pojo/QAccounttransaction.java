package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAccounttransaction is a Querydsl query type for QAccounttransaction
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAccounttransaction extends com.mysema.query.sql.RelationalPathBase<QAccounttransaction> {

    private static final long serialVersionUID = -135574682;

    public static final QAccounttransaction accounttransaction = new QAccounttransaction("accounttransaction");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath cdInd = createString("cd_ind");

    public final StringPath chqDdDate = createString("chq_dd_date");

    public final NumberPath<Integer> chqDdNo = createNumber("chq_dd_no", Integer.class);

    public final NumberPath<Double> clBal = createNumber("cl_bal", Double.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> ldgPage = createNumber("ldg_page", Integer.class);

    public final StringPath payeeNm = createString("payee_nm");

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

    public QAccounttransaction(String variable) {
        super(QAccounttransaction.class, forVariable(variable), "null", "accounttransaction");
    }

    @SuppressWarnings("all")
    public QAccounttransaction(Path<? extends QAccounttransaction> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "accounttransaction");
    }

    public QAccounttransaction(PathMetadata<?> metadata) {
        super(QAccounttransaction.class, metadata, "null", "accounttransaction");
    }

}

