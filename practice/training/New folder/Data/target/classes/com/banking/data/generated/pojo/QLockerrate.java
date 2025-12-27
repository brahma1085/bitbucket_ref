package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLockerrate is a Querydsl query type for QLockerrate
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLockerrate extends com.mysema.query.sql.RelationalPathBase<QLockerrate> {

    private static final long serialVersionUID = -551614003;

    public static final QLockerrate lockerrate = new QLockerrate("lockerrate");

    public final StringPath dateFr = createString("date_fr");

    public final StringPath dateTo = createString("date_to");

    public final NumberPath<Integer> daysFr = createNumber("days_fr", Integer.class);

    public final NumberPath<Integer> daysTo = createNumber("days_to", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> lockerRate = createNumber("locker_rate", Double.class);

    public final StringPath lockerType = createString("locker_type");

    public final NumberPath<Double> securityDeposit = createNumber("security_deposit", Double.class);

    public QLockerrate(String variable) {
        super(QLockerrate.class, forVariable(variable), "null", "lockerrate");
    }

    @SuppressWarnings("all")
    public QLockerrate(Path<? extends QLockerrate> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "lockerrate");
    }

    public QLockerrate(PathMetadata<?> metadata) {
        super(QLockerrate.class, metadata, "null", "lockerrate");
    }

}

