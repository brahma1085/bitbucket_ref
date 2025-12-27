package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDepositcategoryrate is a Querydsl query type for QDepositcategoryrate
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDepositcategoryrate extends com.mysema.query.sql.RelationalPathBase<QDepositcategoryrate> {

    private static final long serialVersionUID = -1741595065;

    public static final QDepositcategoryrate depositcategoryrate = new QDepositcategoryrate("depositcategoryrate");

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

    public final NumberPath<Double> extraLnintRate = createNumber("extra_lnint_rate", Double.class);

    public QDepositcategoryrate(String variable) {
        super(QDepositcategoryrate.class, forVariable(variable), "null", "depositcategoryrate");
    }

    @SuppressWarnings("all")
    public QDepositcategoryrate(Path<? extends QDepositcategoryrate> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "depositcategoryrate");
    }

    public QDepositcategoryrate(PathMetadata<?> metadata) {
        super(QDepositcategoryrate.class, metadata, "null", "depositcategoryrate");
    }

}

