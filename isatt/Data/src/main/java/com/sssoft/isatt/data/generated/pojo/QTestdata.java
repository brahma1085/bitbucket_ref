package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTestdata is a Querydsl query type for QTestdata
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTestdata extends com.mysema.query.sql.RelationalPathBase<QTestdata> {

    private static final long serialVersionUID = -192801928;

    public static final QTestdata testdata = new QTestdata("testdata");

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath status = createString("Status");

    public final StringPath testDataDescription = createString("TestDataDescription");

    public final NumberPath<Integer> testDataID = createNumber("TestDataID", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QTestdata> primary = createPrimaryKey(testDataID);

    public final com.mysema.query.sql.ForeignKey<QApplication> testDataApplicationFK = createForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QScheduler> _schedulerTestDataFK = createInvForeignKey(testDataID, "TestDataID");

    public final com.mysema.query.sql.ForeignKey<QTestconditiondata> _testCondDataTestDataFK = createInvForeignKey(testDataID, "TestDataID");

    public final com.mysema.query.sql.ForeignKey<QSchedulerrundetails> _schedulerRunDetailsTestDataFK = createInvForeignKey(testDataID, "TestDataID");

    public final com.mysema.query.sql.ForeignKey<QTestparamdata> _testParamDataParamGroupFKv1 = createInvForeignKey(testDataID, "TestDataID");

    public QTestdata(String variable) {
        super(QTestdata.class, forVariable(variable), "null", "testdata");
    }

    @SuppressWarnings("all")
    public QTestdata(Path<? extends QTestdata> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "testdata");
    }

    public QTestdata(PathMetadata<?> metadata) {
        super(QTestdata.class, metadata, "null", "testdata");
    }

}

