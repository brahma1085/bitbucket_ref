package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QOdcctransaction is a Querydsl query type for QOdcctransaction
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QOdcctransaction extends com.mysema.query.sql.RelationalPathBase<QOdcctransaction> {

    private static final long serialVersionUID = -2107136492;

    public static final QOdcctransaction odcctransaction = new QOdcctransaction("odcctransaction");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath cdInd = createString("cd_ind");

    public final StringPath chqDdDate = createString("chq_dd_date");

    public final NumberPath<Integer> chqDdNo = createNumber("chq_dd_no", Integer.class);

    public final NumberPath<Double> clBal = createNumber("cl_bal", Double.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath intUptodt = createString("int_uptodt");

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

    public QOdcctransaction(String variable) {
        super(QOdcctransaction.class, forVariable(variable), "null", "odcctransaction");
    }

    @SuppressWarnings("all")
    public QOdcctransaction(Path<? extends QOdcctransaction> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "odcctransaction");
    }

    public QOdcctransaction(PathMetadata<?> metadata) {
        super(QOdcctransaction.class, metadata, "null", "odcctransaction");
    }

}

