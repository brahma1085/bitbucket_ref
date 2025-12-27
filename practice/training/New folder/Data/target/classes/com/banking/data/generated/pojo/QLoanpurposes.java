package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLoanpurposes is a Querydsl query type for QLoanpurposes
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLoanpurposes extends com.mysema.query.sql.RelationalPathBase<QLoanpurposes> {

    private static final long serialVersionUID = 2112827546;

    public static final QLoanpurposes loanpurposes = new QLoanpurposes("loanpurposes");

    public final StringPath deDatetime = createString("de_datetime");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath ppsDesc = createString("pps_desc");

    public final NumberPath<Integer> ppsNo = createNumber("pps_no", Integer.class);

    public QLoanpurposes(String variable) {
        super(QLoanpurposes.class, forVariable(variable), "null", "loanpurposes");
    }

    @SuppressWarnings("all")
    public QLoanpurposes(Path<? extends QLoanpurposes> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "loanpurposes");
    }

    public QLoanpurposes(PathMetadata<?> metadata) {
        super(QLoanpurposes.class, metadata, "null", "loanpurposes");
    }

}

