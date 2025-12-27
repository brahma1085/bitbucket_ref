package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QNpaclassification is a Querydsl query type for QNpaclassification
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QNpaclassification extends com.mysema.query.sql.RelationalPathBase<QNpaclassification> {

    private static final long serialVersionUID = -1193685744;

    public static final QNpaclassification npaclassification = new QNpaclassification("npaclassification");

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

    public QNpaclassification(String variable) {
        super(QNpaclassification.class, forVariable(variable), "null", "npaclassification");
    }

    @SuppressWarnings("all")
    public QNpaclassification(Path<? extends QNpaclassification> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "npaclassification");
    }

    public QNpaclassification(PathMetadata<?> metadata) {
        super(QNpaclassification.class, metadata, "null", "npaclassification");
    }

}

