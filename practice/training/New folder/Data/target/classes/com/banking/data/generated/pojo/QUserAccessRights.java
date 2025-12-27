package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QUserAccessRights is a Querydsl query type for QUserAccessRights
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QUserAccessRights extends com.mysema.query.sql.RelationalPathBase<QUserAccessRights> {

    private static final long serialVersionUID = 539939291;

    public static final QUserAccessRights userAccessRights = new QUserAccessRights("user_access_rights");

    public final NumberPath<Integer> access = createNumber("access", Integer.class);

    public final NumberPath<Integer> mod1 = createNumber("mod1", Integer.class);

    public final NumberPath<Integer> mod10 = createNumber("mod10", Integer.class);

    public final NumberPath<Integer> mod11 = createNumber("mod11", Integer.class);

    public final NumberPath<Integer> mod12 = createNumber("mod12", Integer.class);

    public final NumberPath<Integer> mod13 = createNumber("mod13", Integer.class);

    public final NumberPath<Integer> mod14 = createNumber("mod14", Integer.class);

    public final NumberPath<Integer> mod2 = createNumber("mod2", Integer.class);

    public final NumberPath<Integer> mod3 = createNumber("mod3", Integer.class);

    public final NumberPath<Integer> mod4 = createNumber("mod4", Integer.class);

    public final NumberPath<Integer> mod5 = createNumber("mod5", Integer.class);

    public final NumberPath<Integer> mod6 = createNumber("mod6", Integer.class);

    public final NumberPath<Integer> mod7 = createNumber("mod7", Integer.class);

    public final NumberPath<Integer> mod8 = createNumber("mod8", Integer.class);

    public final NumberPath<Integer> mod9 = createNumber("mod9", Integer.class);

    public final StringPath tml = createString("tml");

    public final StringPath userid = createString("userid");

    public final com.mysema.query.sql.PrimaryKey<QUserAccessRights> primary = createPrimaryKey(userid);

    public QUserAccessRights(String variable) {
        super(QUserAccessRights.class, forVariable(variable), "null", "user_access_rights");
    }

    @SuppressWarnings("all")
    public QUserAccessRights(Path<? extends QUserAccessRights> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "user_access_rights");
    }

    public QUserAccessRights(PathMetadata<?> metadata) {
        super(QUserAccessRights.class, metadata, "null", "user_access_rights");
    }

}

