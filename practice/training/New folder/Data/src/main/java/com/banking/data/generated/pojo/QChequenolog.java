package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QChequenolog is a Querydsl query type for QChequenolog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QChequenolog extends com.mysema.query.sql.RelationalPathBase<QChequenolog> {

    private static final long serialVersionUID = -360165075;

    public static final QChequenolog chequenolog = new QChequenolog("chequenolog");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath altDeDate = createString("alt_de_date");

    public final StringPath altDeUser = createString("alt_de_user");

    public final NumberPath<Integer> bookNo = createNumber("book_no", Integer.class);

    public final NumberPath<Double> chqAmt = createNumber("chq_amt", Double.class);

    public final StringPath chqCancel = createString("chq_cancel");

    public final StringPath chqDel = createString("chq_del");

    public final StringPath chqIssDt = createString("chq_iss_dt");

    public final NumberPath<Integer> chqNo = createNumber("chq_no", Integer.class);

    public final StringPath chqPayee = createString("chq_payee");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath expDate = createString("exp_date");

    public final StringPath nextChqdt = createString("next_chqdt");

    public final StringPath postDated = createString("post_dated");

    public final StringPath stopPymnt = createString("stop_pymnt");

    public final StringPath stopUser = createString("stop_user");

    public final NumberPath<Integer> tranKey = createNumber("tran_key", Integer.class);

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QChequenolog(String variable) {
        super(QChequenolog.class, forVariable(variable), "null", "chequenolog");
    }

    @SuppressWarnings("all")
    public QChequenolog(Path<? extends QChequenolog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "chequenolog");
    }

    public QChequenolog(PathMetadata<?> metadata) {
        super(QChequenolog.class, metadata, "null", "chequenolog");
    }

}

