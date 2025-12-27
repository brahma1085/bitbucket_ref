package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDepositinterestrate is a Querydsl query type for QDepositinterestrate
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDepositinterestrate extends com.mysema.query.sql.RelationalPathBase<QDepositinterestrate> {

    private static final long serialVersionUID = -1122676109;

    public static final QDepositinterestrate depositinterestrate = new QDepositinterestrate("depositinterestrate");

    public final StringPath acType = createString("ac_type");

    public final StringPath dateFr = createString("date_fr");

    public final StringPath dateTo = createString("date_to");

    public final NumberPath<Integer> daysFr = createNumber("days_fr", Integer.class);

    public final NumberPath<Integer> daysTo = createNumber("days_to", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> intRate = createNumber("int_rate", Double.class);

    public QDepositinterestrate(String variable) {
        super(QDepositinterestrate.class, forVariable(variable), "null", "depositinterestrate");
    }

    @SuppressWarnings("all")
    public QDepositinterestrate(Path<? extends QDepositinterestrate> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "depositinterestrate");
    }

    public QDepositinterestrate(PathMetadata<?> metadata) {
        super(QDepositinterestrate.class, metadata, "null", "depositinterestrate");
    }

}

