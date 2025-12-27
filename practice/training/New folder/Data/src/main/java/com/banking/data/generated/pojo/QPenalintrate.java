package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPenalintrate is a Querydsl query type for QPenalintrate
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPenalintrate extends com.mysema.query.sql.RelationalPathBase<QPenalintrate> {

    private static final long serialVersionUID = -1960934816;

    public static final QPenalintrate penalintrate = new QPenalintrate("penalintrate");

    public final NumberPath<Integer> category = createNumber("category", Integer.class);

    public final StringPath dateFr = createString("date_fr");

    public final StringPath dateTo = createString("date_to");

    public final StringPath deDtTime = createString("de_dt_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath lnType = createString("ln_type");

    public final NumberPath<Double> penalrate = createNumber("penalrate", Double.class);

    public QPenalintrate(String variable) {
        super(QPenalintrate.class, forVariable(variable), "null", "penalintrate");
    }

    @SuppressWarnings("all")
    public QPenalintrate(Path<? extends QPenalintrate> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "penalintrate");
    }

    public QPenalintrate(PathMetadata<?> metadata) {
        super(QPenalintrate.class, metadata, "null", "penalintrate");
    }

}

