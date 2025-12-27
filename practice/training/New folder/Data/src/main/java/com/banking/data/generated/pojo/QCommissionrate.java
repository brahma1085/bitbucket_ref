package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCommissionrate is a Querydsl query type for QCommissionrate
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCommissionrate extends com.mysema.query.sql.RelationalPathBase<QCommissionrate> {

    private static final long serialVersionUID = -1269678176;

    public static final QCommissionrate commissionrate = new QCommissionrate("commissionrate");

    public final StringPath agtType = createString("agt_type");

    public final NumberPath<Double> commRate = createNumber("comm_rate", Double.class);

    public final StringPath dateFr = createString("date_fr");

    public final StringPath dateTo = createString("date_to");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> maxAmt = createNumber("max_amt", Double.class);

    public final NumberPath<Double> minAmt = createNumber("min_amt", Double.class);

    public final NumberPath<Double> secDepRate = createNumber("sec_dep_rate", Double.class);

    public QCommissionrate(String variable) {
        super(QCommissionrate.class, forVariable(variable), "null", "commissionrate");
    }

    @SuppressWarnings("all")
    public QCommissionrate(Path<? extends QCommissionrate> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "commissionrate");
    }

    public QCommissionrate(PathMetadata<?> metadata) {
        super(QCommissionrate.class, metadata, "null", "commissionrate");
    }

}

