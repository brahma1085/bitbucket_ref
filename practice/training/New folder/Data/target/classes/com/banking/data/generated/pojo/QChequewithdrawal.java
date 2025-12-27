package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QChequewithdrawal is a Querydsl query type for QChequewithdrawal
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QChequewithdrawal extends com.mysema.query.sql.RelationalPathBase<QChequewithdrawal> {

    private static final long serialVersionUID = -623223893;

    public static final QChequewithdrawal chequewithdrawal = new QChequewithdrawal("chequewithdrawal");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> bookNo = createNumber("book_no", Integer.class);

    public final StringPath cashPd = createString("cash_pd");

    public final StringPath chqDt = createString("chq_dt");

    public final NumberPath<Integer> chqNo = createNumber("chq_no", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath payeeNm = createString("payee_nm");

    public final StringPath tmlNo = createString("tml_no");

    public final NumberPath<Integer> tokenNo = createNumber("token_no", Integer.class);

    public final NumberPath<Double> trnAmt = createNumber("trn_amt", Double.class);

    public final StringPath trnDate = createString("trn_date");

    public final StringPath trnMode = createString("trn_mode");

    public final StringPath trnType = createString("trn_type");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QChequewithdrawal(String variable) {
        super(QChequewithdrawal.class, forVariable(variable), "null", "chequewithdrawal");
    }

    @SuppressWarnings("all")
    public QChequewithdrawal(Path<? extends QChequewithdrawal> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "chequewithdrawal");
    }

    public QChequewithdrawal(PathMetadata<?> metadata) {
        super(QChequewithdrawal.class, metadata, "null", "chequewithdrawal");
    }

}

