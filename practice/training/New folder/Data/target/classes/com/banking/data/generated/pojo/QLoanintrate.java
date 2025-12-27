package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLoanintrate is a Querydsl query type for QLoanintrate
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLoanintrate extends com.mysema.query.sql.RelationalPathBase<QLoanintrate> {

    private static final long serialVersionUID = -523892950;

    public static final QLoanintrate loanintrate = new QLoanintrate("loanintrate");

    public final StringPath dateFr = createString("date_fr");

    public final StringPath dateTo = createString("date_to");

    public final StringPath deDtTime = createString("de_dt_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath lnType = createString("ln_type");

    public final NumberPath<Double> maxBal = createNumber("max_bal", Double.class);

    public final NumberPath<Double> minBal = createNumber("min_bal", Double.class);

    public final NumberPath<Integer> ppsCode = createNumber("pps_code", Integer.class);

    public final NumberPath<Double> rate = createNumber("rate", Double.class);

    public QLoanintrate(String variable) {
        super(QLoanintrate.class, forVariable(variable), "null", "loanintrate");
    }

    @SuppressWarnings("all")
    public QLoanintrate(Path<? extends QLoanintrate> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "loanintrate");
    }

    public QLoanintrate(PathMetadata<?> metadata) {
        super(QLoanintrate.class, metadata, "null", "loanintrate");
    }

}

