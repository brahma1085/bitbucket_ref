package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLockercatrate is a Querydsl query type for QLockercatrate
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLockercatrate extends com.mysema.query.sql.RelationalPathBase<QLockercatrate> {

    private static final long serialVersionUID = -1015058007;

    public static final QLockercatrate lockercatrate = new QLockercatrate("lockercatrate");

    public final NumberPath<Integer> category = createNumber("category", Integer.class);

    public final StringPath dateFr = createString("date_fr");

    public final StringPath dateTo = createString("date_to");

    public final NumberPath<Integer> daysFr = createNumber("days_fr", Integer.class);

    public final NumberPath<Integer> daysTo = createNumber("days_to", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> extraLockerRate = createNumber("extra_locker_rate", Double.class);

    public final StringPath lockerType = createString("locker_type");

    public QLockercatrate(String variable) {
        super(QLockercatrate.class, forVariable(variable), "null", "lockercatrate");
    }

    @SuppressWarnings("all")
    public QLockercatrate(Path<? extends QLockercatrate> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "lockercatrate");
    }

    public QLockercatrate(PathMetadata<?> metadata) {
        super(QLockercatrate.class, metadata, "null", "lockercatrate");
    }

}

