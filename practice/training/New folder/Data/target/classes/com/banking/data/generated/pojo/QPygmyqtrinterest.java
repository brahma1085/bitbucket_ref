package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPygmyqtrinterest is a Querydsl query type for QPygmyqtrinterest
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPygmyqtrinterest extends com.mysema.query.sql.RelationalPathBase<QPygmyqtrinterest> {

    private static final long serialVersionUID = -1528497916;

    public static final QPygmyqtrinterest pygmyqtrinterest = new QPygmyqtrinterest("pygmyqtrinterest");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> intAmt = createNumber("int_amt", Double.class);

    public final StringPath intDate = createString("int_date");

    public QPygmyqtrinterest(String variable) {
        super(QPygmyqtrinterest.class, forVariable(variable), "null", "pygmyqtrinterest");
    }

    @SuppressWarnings("all")
    public QPygmyqtrinterest(Path<? extends QPygmyqtrinterest> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "pygmyqtrinterest");
    }

    public QPygmyqtrinterest(PathMetadata<?> metadata) {
        super(QPygmyqtrinterest.class, metadata, "null", "pygmyqtrinterest");
    }

}

