package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRoleaccess is a Querydsl query type for QRoleaccess
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QRoleaccess extends com.mysema.query.sql.RelationalPathBase<QRoleaccess> {

    private static final long serialVersionUID = -89171153;

    public static final QRoleaccess roleaccess = new QRoleaccess("roleaccess");

    public final NumberPath<Integer> access = createNumber("access", Integer.class);

    public final StringPath role = createString("role");

    public final StringPath screenId = createString("screen_id");

    public QRoleaccess(String variable) {
        super(QRoleaccess.class, forVariable(variable), "null", "roleaccess");
    }

    @SuppressWarnings("all")
    public QRoleaccess(Path<? extends QRoleaccess> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "roleaccess");
    }

    public QRoleaccess(PathMetadata<?> metadata) {
        super(QRoleaccess.class, metadata, "null", "roleaccess");
    }

}

