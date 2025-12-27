package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDepositquantumrate is a Querydsl query type for QDepositquantumrate
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDepositquantumrate extends com.mysema.query.sql.RelationalPathBase<QDepositquantumrate> {

    private static final long serialVersionUID = 1691583986;

    public static final QDepositquantumrate depositquantumrate = new QDepositquantumrate("depositquantumrate");

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> category = createNumber("category", Integer.class);

    public final StringPath dateFr = createString("date_fr");

    public final StringPath dateTo = createString("date_to");

    public final NumberPath<Integer> daysFr = createNumber("days_fr", Integer.class);

    public final NumberPath<Integer> daysTo = createNumber("days_to", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> extraIntRate = createNumber("extra_int_rate", Double.class);

    public final NumberPath<Double> maxAmt = createNumber("max_amt", Double.class);

    public final NumberPath<Double> minAmt = createNumber("min_amt", Double.class);

    public QDepositquantumrate(String variable) {
        super(QDepositquantumrate.class, forVariable(variable), "null", "depositquantumrate");
    }

    @SuppressWarnings("all")
    public QDepositquantumrate(Path<? extends QDepositquantumrate> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "depositquantumrate");
    }

    public QDepositquantumrate(PathMetadata<?> metadata) {
        super(QDepositquantumrate.class, metadata, "null", "depositquantumrate");
    }

}

