package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLoanrecoverydetail is a Querydsl query type for QLoanrecoverydetail
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLoanrecoverydetail extends com.mysema.query.sql.RelationalPathBase<QLoanrecoverydetail> {

    private static final long serialVersionUID = -1509202453;

    public static final QLoanrecoverydetail loanrecoverydetail = new QLoanrecoverydetail("loanrecoverydetail");

    public final StringPath acNo = createString("ac_no");

    public final StringPath acType = createString("ac_type");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> intAmt = createNumber("int_amt", Double.class);

    public final NumberPath<Double> loanBalance = createNumber("loan_balance", Double.class);

    public final NumberPath<Double> otherCharges = createNumber("other_charges", Double.class);

    public final NumberPath<Double> penalAmt = createNumber("penal_amt", Double.class);

    public final StringPath processingDate = createString("processing_date");

    public QLoanrecoverydetail(String variable) {
        super(QLoanrecoverydetail.class, forVariable(variable), "null", "loanrecoverydetail");
    }

    @SuppressWarnings("all")
    public QLoanrecoverydetail(Path<? extends QLoanrecoverydetail> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "loanrecoverydetail");
    }

    public QLoanrecoverydetail(PathMetadata<?> metadata) {
        super(QLoanrecoverydetail.class, metadata, "null", "loanrecoverydetail");
    }

}

