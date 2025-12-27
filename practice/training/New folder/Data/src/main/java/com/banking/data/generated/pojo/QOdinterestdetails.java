package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QOdinterestdetails is a Querydsl query type for QOdinterestdetails
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QOdinterestdetails extends com.mysema.query.sql.RelationalPathBase<QOdinterestdetails> {

    private static final long serialVersionUID = 284202478;

    public static final QOdinterestdetails odinterestdetails = new QOdinterestdetails("odinterestdetails");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Double> depAmt = createNumber("dep_amt", Double.class);

    public final NumberPath<Double> eligibleAmt = createNumber("eligible_amt", Double.class);

    public final StringPath fromDate = createString("from_date");

    public final NumberPath<Double> intAmt = createNumber("int_amt", Double.class);

    public final NumberPath<Integer> intType = createNumber("int_type", Integer.class);

    public final NumberPath<Double> lnIntRate = createNumber("ln_int_rate", Double.class);

    public final NumberPath<Integer> secAcNo = createNumber("sec_ac_no", Integer.class);

    public final StringPath secAcType = createString("sec_ac_type");

    public final StringPath toDate = createString("to_date");

    public QOdinterestdetails(String variable) {
        super(QOdinterestdetails.class, forVariable(variable), "null", "odinterestdetails");
    }

    @SuppressWarnings("all")
    public QOdinterestdetails(Path<? extends QOdinterestdetails> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "odinterestdetails");
    }

    public QOdinterestdetails(PathMetadata<?> metadata) {
        super(QOdinterestdetails.class, metadata, "null", "odinterestdetails");
    }

}

