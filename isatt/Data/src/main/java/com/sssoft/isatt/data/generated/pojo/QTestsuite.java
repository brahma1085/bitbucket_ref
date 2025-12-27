package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTestsuite is a Querydsl query type for QTestsuite
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTestsuite extends com.mysema.query.sql.RelationalPathBase<QTestsuite> {

    private static final long serialVersionUID = -1667453718;

    public static final QTestsuite testsuite = new QTestsuite("testsuite");

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final NumberPath<Integer> sortOrder = createNumber("SortOrder", Integer.class);

    public final StringPath suiteName = createString("SuiteName");

    public final NumberPath<Integer> testSuiteID = createNumber("TestSuiteID", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QTestsuite> primary = createPrimaryKey(testSuiteID);

    public final com.mysema.query.sql.ForeignKey<QApplication> testSuiteApplicationFKv1 = createForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QApplication> testSuiteAPPLICATIONFK = createForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QTestsuiteresult> _testSuiteResultTestPlanResultFKv1 = createInvForeignKey(testSuiteID, "TestSuiteID");

    public final com.mysema.query.sql.ForeignKey<QPlansuitemapping> _tABLE43TestSuiteFK = createInvForeignKey(testSuiteID, "TestSuiteID");

    public final com.mysema.query.sql.ForeignKey<QTestscenarioresult> _testScenarioResultTestSuiteFK = createInvForeignKey(testSuiteID, "TestSuiteID");

    public final com.mysema.query.sql.ForeignKey<QSuitescenariomapping> _suiteScenarioMappingTestSuiteFK = createInvForeignKey(testSuiteID, "TestSuiteID");

    public QTestsuite(String variable) {
        super(QTestsuite.class, forVariable(variable), "null", "testsuite");
    }

    @SuppressWarnings("all")
    public QTestsuite(Path<? extends QTestsuite> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "testsuite");
    }

    public QTestsuite(PathMetadata<?> metadata) {
        super(QTestsuite.class, metadata, "null", "testsuite");
    }

}

