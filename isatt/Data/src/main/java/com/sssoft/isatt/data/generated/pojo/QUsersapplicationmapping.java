package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QUsersapplicationmapping is a Querydsl query type for QUsersapplicationmapping
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QUsersapplicationmapping extends com.mysema.query.sql.RelationalPathBase<QUsersapplicationmapping> {

    private static final long serialVersionUID = 1785994698;

    public static final QUsersapplicationmapping usersapplicationmapping = new QUsersapplicationmapping("usersapplicationmapping");

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final StringPath authority = createString("AUTHORITY");

    public final NumberPath<Integer> userId = createNumber("USER_ID", Integer.class);

    public final NumberPath<Integer> usersApplicationMappingID = createNumber("UsersApplicationMappingID", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QUsersapplicationmapping> primary = createPrimaryKey(usersApplicationMappingID);

    public final com.mysema.query.sql.ForeignKey<QApplication> usersApplicationMappingApplicationFK = createForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QUsers> usersApplicationMappingUsersFK = createForeignKey(userId, "USER_ID");

    public QUsersapplicationmapping(String variable) {
        super(QUsersapplicationmapping.class, forVariable(variable), "null", "usersapplicationmapping");
    }

    @SuppressWarnings("all")
    public QUsersapplicationmapping(Path<? extends QUsersapplicationmapping> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "usersapplicationmapping");
    }

    public QUsersapplicationmapping(PathMetadata<?> metadata) {
        super(QUsersapplicationmapping.class, metadata, "null", "usersapplicationmapping");
    }

}

