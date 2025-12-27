package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLockertransaction is a Querydsl query type for QLockertransaction
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLockertransaction extends com.mysema.query.sql.RelationalPathBase<QLockertransaction> {

    private static final long serialVersionUID = 1090658641;

    public static final QLockertransaction lockertransaction = new QLockertransaction("lockertransaction");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath cdInd = createString("cd_ind");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> lockerNo = createNumber("locker_no", Integer.class);

    public final StringPath opDate = createString("op_date");

    public final StringPath operBy = createString("oper_by");

    public final NumberPath<Double> rentAmt = createNumber("rent_amt", Double.class);

    public final StringPath rentBy = createString("rent_by");

    public final StringPath rentUpto = createString("rent_upto");

    public final StringPath timeIn = createString("time_in");

    public final StringPath timeOut = createString("time_out");

    public final NumberPath<Integer> trfAcno = createNumber("trf_acno", Integer.class);

    public final StringPath trfActy = createString("trf_acty");

    public final NumberPath<Integer> trnSeq = createNumber("trn_seq", Integer.class);

    public final StringPath trnType = createString("trn_type");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QLockertransaction(String variable) {
        super(QLockertransaction.class, forVariable(variable), "null", "lockertransaction");
    }

    @SuppressWarnings("all")
    public QLockertransaction(Path<? extends QLockertransaction> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "lockertransaction");
    }

    public QLockertransaction(PathMetadata<?> metadata) {
        super(QLockertransaction.class, metadata, "null", "lockertransaction");
    }

}

