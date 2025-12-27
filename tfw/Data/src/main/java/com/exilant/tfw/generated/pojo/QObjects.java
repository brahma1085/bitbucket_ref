package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QObjects is a Querydsl query type for QObjects
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QObjects extends com.mysema.query.sql.RelationalPathBase<QObjects> {

    private static final long serialVersionUID = -357003109;

    public static final QObjects objects = new QObjects("objects");

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final StringPath identifier = createString("Identifier");

    public final NumberPath<Integer> identifierTypeID = createNumber("IdentifierTypeID", Integer.class);

    public final NumberPath<Integer> objectGroupID = createNumber("ObjectGroupID", Integer.class);

    public final NumberPath<Integer> objectID = createNumber("ObjectID", Integer.class);

    public final StringPath objectName = createString("ObjectName");

    public final NumberPath<Integer> objectTypeID = createNumber("ObjectTypeID", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QObjects> primary = createPrimaryKey(objectID);

    public final com.mysema.query.sql.ForeignKey<QObjectgroup> objectsObjectGroupFK = createForeignKey(objectGroupID, "ObjectGroupID");

    public final com.mysema.query.sql.ForeignKey<QIdentifiertype> objectsIdentifierTypeFK = createForeignKey(identifierTypeID, "IdentifierTypeID");

    public final com.mysema.query.sql.ForeignKey<QApplication> objectsApplicationFK = createForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QObjecttype> objectsObjectTypeFK = createForeignKey(objectTypeID, "ObjectTypeID");

    public final com.mysema.query.sql.ForeignKey<QParam> _parameterObjectsFK = createInvForeignKey(objectID, "ObjectID");

    public QObjects(String variable) {
        super(QObjects.class, forVariable(variable), "null", "objects");
    }

    @SuppressWarnings("all")
    public QObjects(Path<? extends QObjects> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "objects");
    }

    public QObjects(PathMetadata<?> metadata) {
        super(QObjects.class, metadata, "null", "objects");
    }

}

