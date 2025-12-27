package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QChequedetails is a Querydsl query type for QChequedetails
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QChequedetails extends com.mysema.query.sql.RelationalPathBase<QChequedetails> {

    private static final long serialVersionUID = 1209296652;

    public static final QChequedetails chequedetails = new QChequedetails("chequedetails");

    public final NumberPath<Integer> crAcNo = createNumber("cr_ac_no", Integer.class);

    public final StringPath crAcType = createString("cr_ac_type");

    public final NumberPath<Double> crAmt = createNumber("cr_amt", Double.class);

    public final NumberPath<Integer> ctrlNo = createNumber("ctrl_no", Integer.class);

    public final StringPath date = createString("date");

    public final StringPath deDtTime = createString("de_dt_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> discAmt = createNumber("disc_amt", Double.class);

    public final StringPath discInd = createString("disc_ind");

    public final NumberPath<Integer> loanAcNo = createNumber("loan_ac_no", Integer.class);

    public final StringPath loanAcType = createString("loan_ac_type");

    public final StringPath postInd = createString("post_ind");

    public final StringPath veDtTime = createString("ve_dt_time");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QChequedetails(String variable) {
        super(QChequedetails.class, forVariable(variable), "null", "chequedetails");
    }

    @SuppressWarnings("all")
    public QChequedetails(Path<? extends QChequedetails> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "chequedetails");
    }

    public QChequedetails(PathMetadata<?> metadata) {
        super(QChequedetails.class, metadata, "null", "chequedetails");
    }

}

