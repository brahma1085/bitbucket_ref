package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLoanperiodrate is a Querydsl query type for QLoanperiodrate
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLoanperiodrate extends com.mysema.query.sql.RelationalPathBase<QLoanperiodrate> {

    private static final long serialVersionUID = 126170726;

    public static final QLoanperiodrate loanperiodrate = new QLoanperiodrate("loanperiodrate");

    public final StringPath dateFr = createString("date_fr");

    public final StringPath dateTo = createString("date_to");

    public final StringPath deDtTime = createString("de_dt_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> frMon = createNumber("fr_mon", Integer.class);

    public final StringPath lnType = createString("ln_type");

    public final NumberPath<Double> rate = createNumber("rate", Double.class);

    public final NumberPath<Integer> toMon = createNumber("to_mon", Integer.class);

    public QLoanperiodrate(String variable) {
        super(QLoanperiodrate.class, forVariable(variable), "null", "loanperiodrate");
    }

    @SuppressWarnings("all")
    public QLoanperiodrate(Path<? extends QLoanperiodrate> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "loanperiodrate");
    }

    public QLoanperiodrate(PathMetadata<?> metadata) {
        super(QLoanperiodrate.class, metadata, "null", "loanperiodrate");
    }

}

