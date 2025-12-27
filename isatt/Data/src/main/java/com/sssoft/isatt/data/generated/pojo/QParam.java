package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QParam is a Querydsl query type for QParam
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QParam extends com.mysema.query.sql.RelationalPathBase<QParam> {

    private static final long serialVersionUID = -1866208815;

    public static final QParam param = new QParam("param");

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final NumberPath<Integer> objectID = createNumber("ObjectID", Integer.class);

    public final NumberPath<Integer> paramGroupID = createNumber("ParamGroupID", Integer.class);

    public final NumberPath<Integer> paramID = createNumber("ParamID", Integer.class);

    public final StringPath paramName = createString("ParamName");

    public final NumberPath<Integer> sortOrder = createNumber("SortOrder", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QParam> primary = createPrimaryKey(paramID);

    public final com.mysema.query.sql.ForeignKey<QParamgroup> parameterParamGroupFK = createForeignKey(paramGroupID, "ParamGroupID");

    public final com.mysema.query.sql.ForeignKey<QObjects> parameterObjectsFK = createForeignKey(objectID, "ObjectID");

    public QParam(String variable) {
        super(QParam.class, forVariable(variable), "null", "param");
    }

    @SuppressWarnings("all")
    public QParam(Path<? extends QParam> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "param");
    }

    public QParam(PathMetadata<?> metadata) {
        super(QParam.class, metadata, "null", "param");
    }

}

