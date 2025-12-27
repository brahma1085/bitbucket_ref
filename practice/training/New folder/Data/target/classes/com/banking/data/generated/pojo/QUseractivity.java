package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QUseractivity is a Querydsl query type for QUseractivity
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QUseractivity extends com.mysema.query.sql.RelationalPathBase<QUseractivity> {

    private static final long serialVersionUID = 1839241327;

    public static final QUseractivity useractivity = new QUseractivity("useractivity");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath activity = createString("activity");

    public final StringPath activityDate = createString("activity_date");

    public final StringPath activityTime = createString("activity_time");

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final StringPath ipAddress = createString("ip_address");

    public final StringPath pageVisit = createString("page_visit");

    public final StringPath tmlNo = createString("tml_no");

    public final StringPath userId = createString("user_id");

    public QUseractivity(String variable) {
        super(QUseractivity.class, forVariable(variable), "null", "useractivity");
    }

    @SuppressWarnings("all")
    public QUseractivity(Path<? extends QUseractivity> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "useractivity");
    }

    public QUseractivity(PathMetadata<?> metadata) {
        super(QUseractivity.class, metadata, "null", "useractivity");
    }

}

