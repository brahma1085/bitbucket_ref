package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSharetransaction is a Querydsl query type for QSharetransaction
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSharetransaction extends com.mysema.query.sql.RelationalPathBase<QSharetransaction> {

    private static final long serialVersionUID = 40997812;

    public static final QSharetransaction sharetransaction = new QSharetransaction("sharetransaction");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath altInd = createString("alt_ind");

    public final StringPath cdInd = createString("cd_ind");

    public final StringPath certPrtd = createString("cert_prtd");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> distNoFrom = createNumber("dist_no_from", Integer.class);

    public final NumberPath<Integer> distNoTo = createNumber("dist_no_to", Integer.class);

    public final StringPath markdel = createString("markdel");

    public final NumberPath<Integer> refNo = createNumber("ref_no", Integer.class);

    public final StringPath shCertDt = createString("sh_cert_dt");

    public final NumberPath<Integer> shCertNo = createNumber("sh_cert_no", Integer.class);

    public final NumberPath<Double> shareBal = createNumber("share_bal", Double.class);

    public final StringPath suspInd = createString("susp_ind");

    public final NumberPath<Double> trnAmt = createNumber("trn_amt", Double.class);

    public final StringPath trnDate = createString("trn_date");

    public final StringPath trnMode = createString("trn_mode");

    public final StringPath trnNarr = createString("trn_narr");

    public final NumberPath<Integer> trnNo = createNumber("trn_no", Integer.class);

    public final NumberPath<Integer> trnSeq = createNumber("trn_seq", Integer.class);

    public final StringPath trnSource = createString("trn_source");

    public final StringPath trnType = createString("trn_type");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QSharetransaction(String variable) {
        super(QSharetransaction.class, forVariable(variable), "null", "sharetransaction");
    }

    @SuppressWarnings("all")
    public QSharetransaction(Path<? extends QSharetransaction> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "sharetransaction");
    }

    public QSharetransaction(PathMetadata<?> metadata) {
        super(QSharetransaction.class, metadata, "null", "sharetransaction");
    }

}

