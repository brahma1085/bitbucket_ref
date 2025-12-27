package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QIntpay is a Querydsl query type for QIntpay
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QIntpay extends com.mysema.query.sql.RelationalPathBase<QIntpay> {

    private static final long serialVersionUID = 735733422;

    public static final QIntpay intpay = new QIntpay("intpay");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> intRate = createNumber("int_rate", Double.class);

    public final StringPath postInd = createString("post_ind");

    public final StringPath postend = createString("postend");

    public final StringPath poststrt = createString("poststrt");

    public final NumberPath<Double> prod1 = createNumber("prod_1", Double.class);

    public final NumberPath<Double> prod2 = createNumber("prod_2", Double.class);

    public final NumberPath<Double> prod3 = createNumber("prod_3", Double.class);

    public final NumberPath<Double> prod4 = createNumber("prod_4", Double.class);

    public final NumberPath<Double> prod5 = createNumber("prod_5", Double.class);

    public final NumberPath<Double> prod6 = createNumber("prod_6", Double.class);

    public final NumberPath<Double> trnAmt = createNumber("trn_amt", Double.class);

    public final StringPath trnDate = createString("trn_date");

    public QIntpay(String variable) {
        super(QIntpay.class, forVariable(variable), "null", "intpay");
    }

    @SuppressWarnings("all")
    public QIntpay(Path<? extends QIntpay> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "intpay");
    }

    public QIntpay(PathMetadata<?> metadata) {
        super(QIntpay.class, metadata, "null", "intpay");
    }

}

