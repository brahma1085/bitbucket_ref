package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QNpaclassification90 is a Querydsl query type for QNpaclassification90
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QNpaclassification90 extends com.mysema.query.sql.RelationalPathBase<QNpaclassification90> {

    private static final long serialVersionUID = -375730137;

    public static final QNpaclassification90 npaclassification90 = new QNpaclassification90("npaclassification_90");

    public final NumberPath<Integer> assetCode = createNumber("asset_code", Integer.class);

    public final StringPath assetDesc = createString("asset_desc");

    public final NumberPath<Integer> daysLimitFr = createNumber("days_limit_fr", Integer.class);

    public final NumberPath<Integer> daysLimitTo = createNumber("days_limit_to", Integer.class);

    public final DatePath<java.sql.Date> fromDate = createDate("from_date", java.sql.Date.class);

    public final NumberPath<Integer> loanType = createNumber("loan_type", Integer.class);

    public final NumberPath<Integer> mthsLimitFr = createNumber("mths_limit_fr", Integer.class);

    public final NumberPath<Integer> mthsLimitTo = createNumber("mths_limit_to", Integer.class);

    public final NumberPath<Double> provPerc = createNumber("prov_perc", Double.class);

    public final DatePath<java.sql.Date> toDate = createDate("to_date", java.sql.Date.class);

    public QNpaclassification90(String variable) {
        super(QNpaclassification90.class, forVariable(variable), "null", "npaclassification_90");
    }

    @SuppressWarnings("all")
    public QNpaclassification90(Path<? extends QNpaclassification90> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "npaclassification_90");
    }

    public QNpaclassification90(PathMetadata<?> metadata) {
        super(QNpaclassification90.class, metadata, "null", "npaclassification_90");
    }

}

