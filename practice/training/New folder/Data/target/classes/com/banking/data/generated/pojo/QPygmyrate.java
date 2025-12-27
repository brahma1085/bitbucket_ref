package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPygmyrate is a Querydsl query type for QPygmyrate
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPygmyrate extends com.mysema.query.sql.RelationalPathBase<QPygmyrate> {

    private static final long serialVersionUID = 228580629;

    public static final QPygmyrate pygmyrate = new QPygmyrate("pygmyrate");

    public final StringPath acType = createString("ac_type");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath frDate = createString("fr_date");

    public final NumberPath<Double> intRate = createNumber("int_rate", Double.class);

    public final NumberPath<Integer> prdFr = createNumber("prd_fr", Integer.class);

    public final NumberPath<Integer> prdTo = createNumber("prd_to", Integer.class);

    public final StringPath toDate = createString("to_date");

    public QPygmyrate(String variable) {
        super(QPygmyrate.class, forVariable(variable), "null", "pygmyrate");
    }

    @SuppressWarnings("all")
    public QPygmyrate(Path<? extends QPygmyrate> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "pygmyrate");
    }

    public QPygmyrate(PathMetadata<?> metadata) {
        super(QPygmyrate.class, metadata, "null", "pygmyrate");
    }

}

