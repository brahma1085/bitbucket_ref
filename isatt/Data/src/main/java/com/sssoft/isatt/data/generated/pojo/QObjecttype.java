package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QObjecttype is a Querydsl query type for QObjecttype
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QObjecttype extends com.mysema.query.sql.RelationalPathBase<QObjecttype> {

    private static final long serialVersionUID = -1728195531;

    public static final QObjecttype objecttype = new QObjecttype("objecttype");

    public final NumberPath<Integer> actionId = createNumber("ActionId", Integer.class);

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final NumberPath<Integer> objectTypeID = createNumber("ObjectTypeID", Integer.class);

    public final StringPath objectTypeName = createString("ObjectTypeName");

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QObjecttype> primary = createPrimaryKey(objectTypeID);

    public final com.mysema.query.sql.ForeignKey<QActions> objectTypeAPPLICATIONFKv1 = createForeignKey(actionId, "ActionID");

    public final com.mysema.query.sql.ForeignKey<QObjects> _objectsObjectTypeFK = createInvForeignKey(objectTypeID, "ObjectTypeID");

    public QObjecttype(String variable) {
        super(QObjecttype.class, forVariable(variable), "null", "objecttype");
    }

    @SuppressWarnings("all")
    public QObjecttype(Path<? extends QObjecttype> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "objecttype");
    }

    public QObjecttype(PathMetadata<?> metadata) {
        super(QObjecttype.class, metadata, "null", "objecttype");
    }

}

