package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPrecedingfornight is a Querydsl query type for QPrecedingfornight
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPrecedingfornight extends com.mysema.query.sql.RelationalPathBase<QPrecedingfornight> {

    private static final long serialVersionUID = -1358438595;

    public static final QPrecedingfornight precedingfornight = new QPrecedingfornight("precedingfornight");

    public final StringPath dayOfWeek = createString("day_of_week");

    public final DateTimePath<java.sql.Timestamp> deDtTime = createDateTime("de_dt_time", java.sql.Timestamp.class);

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final DatePath<java.sql.Date> markingDt = createDate("marking_dt", java.sql.Date.class);

    public final NumberPath<Double> nDTLamount = createNumber("NDTLamount", Double.class);

    public final NumberPath<Integer> recurringDays = createNumber("recurring_days", Integer.class);

    public QPrecedingfornight(String variable) {
        super(QPrecedingfornight.class, forVariable(variable), "null", "precedingfornight");
    }

    @SuppressWarnings("all")
    public QPrecedingfornight(Path<? extends QPrecedingfornight> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "precedingfornight");
    }

    public QPrecedingfornight(PathMetadata<?> metadata) {
        super(QPrecedingfornight.class, metadata, "null", "precedingfornight");
    }

}

