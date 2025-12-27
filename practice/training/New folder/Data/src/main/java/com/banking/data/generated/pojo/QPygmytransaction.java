package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPygmytransaction is a Querydsl query type for QPygmytransaction
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPygmytransaction extends com.mysema.query.sql.RelationalPathBase<QPygmytransaction> {

    private static final long serialVersionUID = -1848472311;

    public static final QPygmytransaction pygmytransaction = new QPygmytransaction("pygmytransaction");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Double> amtPaid = createNumber("amt_paid", Double.class);

    public final StringPath cdInd = createString("cd_ind");

    public final NumberPath<Double> clBal = createNumber("cl_bal", Double.class);

    public final StringPath collDate = createString("coll_date");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath intFrom = createString("int_from");

    public final NumberPath<Double> intPaid = createNumber("int_paid", Double.class);

    public final NumberPath<Double> prnPaid = createNumber("prn_paid", Double.class);

    public final NumberPath<Integer> refInd = createNumber("ref_ind", Integer.class);

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

    public QPygmytransaction(String variable) {
        super(QPygmytransaction.class, forVariable(variable), "null", "pygmytransaction");
    }

    @SuppressWarnings("all")
    public QPygmytransaction(Path<? extends QPygmytransaction> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "pygmytransaction");
    }

    public QPygmytransaction(PathMetadata<?> metadata) {
        super(QPygmytransaction.class, metadata, "null", "pygmytransaction");
    }

}

