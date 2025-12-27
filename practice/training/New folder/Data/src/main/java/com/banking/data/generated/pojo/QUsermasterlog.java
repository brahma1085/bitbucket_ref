package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QUsermasterlog is a Querydsl query type for QUsermasterlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QUsermasterlog extends com.mysema.query.sql.RelationalPathBase<QUsermasterlog> {

    private static final long serialVersionUID = 1188613186;

    public static final QUsermasterlog usermasterlog = new QUsermasterlog("usermasterlog");

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

    public QUsermasterlog(String variable) {
        super(QUsermasterlog.class, forVariable(variable), "null", "usermasterlog");
    }

    @SuppressWarnings("all")
    public QUsermasterlog(Path<? extends QUsermasterlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "usermasterlog");
    }

    public QUsermasterlog(PathMetadata<?> metadata) {
        super(QUsermasterlog.class, metadata, "null", "usermasterlog");
    }

}

