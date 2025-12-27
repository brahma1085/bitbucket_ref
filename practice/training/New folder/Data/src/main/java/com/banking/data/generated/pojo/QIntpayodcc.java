package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QIntpayodcc is a Querydsl query type for QIntpayodcc
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QIntpayodcc extends com.mysema.query.sql.RelationalPathBase<QIntpayodcc> {

    private static final long serialVersionUID = 1442797731;

    public static final QIntpayodcc intpayodcc = new QIntpayodcc("intpayodcc");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Double> calculatedBal = createNumber("calculated_bal", Double.class);

    public final BooleanPath exceeds = createBoolean("exceeds");

    public final NumberPath<Double> intAmt = createNumber("int_amt", Double.class);

    public final StringPath name = createString("name");

    public final NumberPath<Double> penalInt = createNumber("penal_int", Double.class);

    public final BooleanPath posting = createBoolean("posting");

    public final NumberPath<Double> prevBal = createNumber("prev_bal", Double.class);

    public final NumberPath<Double> sancAmt = createNumber("sanc_amt", Double.class);

    public final StringPath trnDate = createString("trn_date");

    public QIntpayodcc(String variable) {
        super(QIntpayodcc.class, forVariable(variable), "null", "intpayodcc");
    }

    @SuppressWarnings("all")
    public QIntpayodcc(Path<? extends QIntpayodcc> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "intpayodcc");
    }

    public QIntpayodcc(PathMetadata<?> metadata) {
        super(QIntpayodcc.class, metadata, "null", "intpayodcc");
    }

}

