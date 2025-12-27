package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTestparamdata is a Querydsl query type for QTestparamdata
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTestparamdata extends com.mysema.query.sql.RelationalPathBase<QTestparamdata> {

    private static final long serialVersionUID = 503350188;

    public static final QTestparamdata testparamdata = new QTestparamdata("testparamdata");

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final NumberPath<Integer> paramGroupID = createNumber("ParamGroupID", Integer.class);

    public final NumberPath<Integer> paramID = createNumber("ParamID", Integer.class);

    public final StringPath paramValue = createString("ParamValue");

    public final NumberPath<Integer> testDataID = createNumber("TestDataID", Integer.class);

    public final NumberPath<Integer> testParamDataID = createNumber("TestParamDataID", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final StringPath valueBig = createString("ValueBig");

    public final com.mysema.query.sql.PrimaryKey<QTestparamdata> primary = createPrimaryKey(testParamDataID);

    public final com.mysema.query.sql.ForeignKey<QTestdata> testParamDataParamGroupFKv1 = createForeignKey(testDataID, "TestDataID");

    public QTestparamdata(String variable) {
        super(QTestparamdata.class, forVariable(variable), "null", "testparamdata");
    }

    @SuppressWarnings("all")
    public QTestparamdata(Path<? extends QTestparamdata> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "testparamdata");
    }

    public QTestparamdata(PathMetadata<?> metadata) {
        super(QTestparamdata.class, metadata, "null", "testparamdata");
    }

}

