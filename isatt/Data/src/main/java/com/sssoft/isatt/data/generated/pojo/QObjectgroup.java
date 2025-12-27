package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QObjectgroup is a Querydsl query type for QObjectgroup
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QObjectgroup extends com.mysema.query.sql.RelationalPathBase<QObjectgroup> {

    private static final long serialVersionUID = -2046668572;

    public static final QObjectgroup objectgroup = new QObjectgroup("objectgroup");

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final NumberPath<Integer> objectGroupID = createNumber("ObjectGroupID", Integer.class);

    public final StringPath objectGroupName = createString("ObjectGroupName");

    public final NumberPath<Integer> screenID = createNumber("ScreenID", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QObjectgroup> primary = createPrimaryKey(objectGroupID);

    public final com.mysema.query.sql.ForeignKey<QApplication> objectGroupAPPLICATIONFK = createForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QScreen> objectGroupScreenFK = createForeignKey(screenID, "ScreenID");

    public final com.mysema.query.sql.ForeignKey<QObjects> _objectsObjectGroupFK = createInvForeignKey(objectGroupID, "ObjectGroupID");

    public final com.mysema.query.sql.ForeignKey<QParamgroup> _paramGroupObjectGroupFK = createInvForeignKey(objectGroupID, "ObjectGroupID");

    public QObjectgroup(String variable) {
        super(QObjectgroup.class, forVariable(variable), "null", "objectgroup");
    }

    @SuppressWarnings("all")
    public QObjectgroup(Path<? extends QObjectgroup> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "objectgroup");
    }

    public QObjectgroup(PathMetadata<?> metadata) {
        super(QObjectgroup.class, metadata, "null", "objectgroup");
    }

}

