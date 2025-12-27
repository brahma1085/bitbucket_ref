package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDaycash is a Querydsl query type for QDaycash
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDaycash extends com.mysema.query.sql.RelationalPathBase<QDaycash> {

    private static final long serialVersionUID = 822399770;

    public static final QDaycash daycash = new QDaycash("daycash");

    public final StringPath acNo = createString("ac_no");

    public final StringPath acType = createString("ac_type");

    public final StringPath attached = createString("attached");

    public final StringPath cdInd = createString("cd_ind");

    public final NumberPath<Integer> chqNo = createNumber("chq_no", Integer.class);

    public final NumberPath<Double> commAmt = createNumber("comm_amt", Double.class);

    public final NumberPath<Double> cshAmt = createNumber("csh_amt", Double.class);

    public final StringPath custAcNo = createString("cust_ac_no");

    public final StringPath custAcType = createString("cust_ac_type");

    public final StringPath custCode = createString("cust_code");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath lockerType = createString("locker_type");

    public final StringPath name = createString("name");

    public final StringPath othTml = createString("oth_tml");

    public final NumberPath<Integer> p1 = createNumber("p1", Integer.class);

    public final NumberPath<Integer> p10 = createNumber("p10", Integer.class);

    public final NumberPath<Integer> p100 = createNumber("p100", Integer.class);

    public final NumberPath<Integer> p1000 = createNumber("p1000", Integer.class);

    public final NumberPath<Integer> p2 = createNumber("p2", Integer.class);

    public final NumberPath<Integer> p20 = createNumber("p20", Integer.class);

    public final NumberPath<Integer> p5 = createNumber("p5", Integer.class);

    public final NumberPath<Integer> p50 = createNumber("p50", Integer.class);

    public final NumberPath<Integer> p500 = createNumber("p500", Integer.class);

    public final NumberPath<Double> pcoins = createNumber("pcoins", Double.class);

    public final StringPath poFavourName = createString("po_favour_name");

    public final NumberPath<Integer> r1 = createNumber("r1", Integer.class);

    public final NumberPath<Integer> r10 = createNumber("r10", Integer.class);

    public final NumberPath<Integer> r100 = createNumber("r100", Integer.class);

    public final NumberPath<Integer> r1000 = createNumber("r1000", Integer.class);

    public final NumberPath<Integer> r2 = createNumber("r2", Integer.class);

    public final NumberPath<Integer> r20 = createNumber("r20", Integer.class);

    public final NumberPath<Integer> r5 = createNumber("r5", Integer.class);

    public final NumberPath<Integer> r50 = createNumber("r50", Integer.class);

    public final NumberPath<Integer> r500 = createNumber("r500", Integer.class);

    public final NumberPath<Double> rcoins = createNumber("rcoins", Double.class);

    public final NumberPath<Double> runBal = createNumber("run_bal", Double.class);

    public final NumberPath<Integer> scrollNo = createNumber("scroll_no", Integer.class);

    public final StringPath shareCategory = createString("share_category");

    public final StringPath tmlNo = createString("tml_no");

    public final NumberPath<Integer> tokenNo = createNumber("token_no", Integer.class);

    public final StringPath trnDate = createString("trn_date");

    public final NumberPath<Integer> trnSeq = createNumber("trn_seq", Integer.class);

    public final StringPath trnType = createString("trn_type");

    public final NumberPath<Integer> vchNo = createNumber("vch_no", Integer.class);

    public final StringPath vchType = createString("vch_type");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QDaycash(String variable) {
        super(QDaycash.class, forVariable(variable), "null", "daycash");
    }

    @SuppressWarnings("all")
    public QDaycash(Path<? extends QDaycash> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "daycash");
    }

    public QDaycash(PathMetadata<?> metadata) {
        super(QDaycash.class, metadata, "null", "daycash");
    }

}

