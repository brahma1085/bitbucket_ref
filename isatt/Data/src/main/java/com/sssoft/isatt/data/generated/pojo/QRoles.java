package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRoles is a Querydsl query type for QRoles
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QRoles extends com.mysema.query.sql.RelationalPathBase<QRoles> {

    private static final long serialVersionUID = -1863950335;

    public static final QRoles roles = new QRoles("roles");

    public final StringPath authority = createString("Authority");

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final NumberPath<Integer> roleID = createNumber("RoleID", Integer.class);

    public final StringPath rolesDescription = createString("RolesDescription");

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QRoles> primary = createPrimaryKey(roleID);

    public QRoles(String variable) {
        super(QRoles.class, forVariable(variable), "null", "roles");
    }

    @SuppressWarnings("all")
    public QRoles(Path<? extends QRoles> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "roles");
    }

    public QRoles(PathMetadata<?> metadata) {
        super(QRoles.class, metadata, "null", "roles");
    }

}

