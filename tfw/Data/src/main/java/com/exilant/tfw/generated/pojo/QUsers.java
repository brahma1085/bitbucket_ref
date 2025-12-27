package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QUsers is a Querydsl query type for QUsers
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QUsers extends com.mysema.query.sql.RelationalPathBase<QUsers> {

    private static final long serialVersionUID = 1011257167;

    public static final QUsers users = new QUsers("users");

    public final StringPath emailId = createString("EMAIL_ID");

    public final BooleanPath enabled = createBoolean("ENABLED");

    public final StringPath password = createString("PASSWORD");

    public final NumberPath<Integer> passwordCount = createNumber("PASSWORD_COUNT", Integer.class);

    public final NumberPath<Integer> userId = createNumber("USER_ID", Integer.class);

    public final StringPath username = createString("USERNAME");

    public final com.mysema.query.sql.PrimaryKey<QUsers> primary = createPrimaryKey(userId);

    public final com.mysema.query.sql.ForeignKey<QUserRoles> _userRolesUsersFK = createInvForeignKey(userId, "USER_ID");

    public final com.mysema.query.sql.ForeignKey<QUsersapplicationmapping> _usersApplicationMappingUsersFK = createInvForeignKey(userId, "USER_ID");

    public QUsers(String variable) {
        super(QUsers.class, forVariable(variable), "null", "users");
    }

    @SuppressWarnings("all")
    public QUsers(Path<? extends QUsers> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "users");
    }

    public QUsers(PathMetadata<?> metadata) {
        super(QUsers.class, metadata, "null", "users");
    }

}

