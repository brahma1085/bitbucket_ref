package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QParamgroup is a Querydsl query type for QParamgroup
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QParamgroup extends com.mysema.query.sql.RelationalPathBase<QParamgroup> {

    private static final long serialVersionUID = 1959495502;

    public static final QParamgroup paramgroup = new QParamgroup("paramgroup");

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final NumberPath<Integer> objectGroupID = createNumber("ObjectGroupID", Integer.class);

    public final NumberPath<Integer> paramGroupID = createNumber("ParamGroupID", Integer.class);

    public final StringPath paramGroupName = createString("ParamGroupName");

    public final StringPath tag = createString("Tag");

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QParamgroup> primary = createPrimaryKey(paramGroupID);

    public final com.mysema.query.sql.ForeignKey<QObjectgroup> paramGroupObjectGroupFK = createForeignKey(objectGroupID, "ObjectGroupID");

    public final com.mysema.query.sql.ForeignKey<QApplication> paramGroupAPPLICATIONFK = createForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QParam> _parameterParamGroupFK = createInvForeignKey(paramGroupID, "ParamGroupID");

    public final com.mysema.query.sql.ForeignKey<QTeststep> _testStepParamGroup = createInvForeignKey(paramGroupID, "InputParamGroupID");

    public QParamgroup(String variable) {
        super(QParamgroup.class, forVariable(variable), "null", "paramgroup");
    }

    @SuppressWarnings("all")
    public QParamgroup(Path<? extends QParamgroup> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "paramgroup");
    }

    public QParamgroup(PathMetadata<?> metadata) {
        super(QParamgroup.class, metadata, "null", "paramgroup");
    }

}

