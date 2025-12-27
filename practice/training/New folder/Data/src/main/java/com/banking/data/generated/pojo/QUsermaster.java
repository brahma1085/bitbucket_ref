package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QUsermaster is a Querydsl query type for QUsermaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QUsermaster extends com.mysema.query.sql.RelationalPathBase<QUsermaster> {

    private static final long serialVersionUID = 361473986;

    public static final QUsermaster usermaster = new QUsermaster("usermaster");

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> disable = createNumber("disable", Integer.class);

    public final StringPath operativeFromDate = createString("operative_from_date");

    public final StringPath operativeToDate = createString("operative_to_date");

    public final StringPath password = createString("password");

    public final StringPath pwdExpiryDate = createString("pwd_expiry_date");

    public final NumberPath<Integer> pwdExpiryPeriod = createNumber("pwd_expiry_period", Integer.class);

    public final StringPath shortName = createString("short_name");

    public final StringPath thumpIpm = createString("thump_ipm");

    public final StringPath userId = createString("user_id");

    public QUsermaster(String variable) {
        super(QUsermaster.class, forVariable(variable), "null", "usermaster");
    }

    @SuppressWarnings("all")
    public QUsermaster(Path<? extends QUsermaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "usermaster");
    }

    public QUsermaster(PathMetadata<?> metadata) {
        super(QUsermaster.class, metadata, "null", "usermaster");
    }

}

