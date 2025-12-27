package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTestscenarioresult is a Querydsl query type for QTestscenarioresult
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTestscenarioresult extends com.mysema.query.sql.RelationalPathBase<QTestscenarioresult> {

    private static final long serialVersionUID = -1652717061;

    public static final QTestscenarioresult testscenarioresult = new QTestscenarioresult("testscenarioresult");

    public final DateTimePath<java.sql.Timestamp> endDateTime = createDateTime("EndDateTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> percentageFailCount = createNumber("PercentageFailCount", Integer.class);

    public final NumberPath<Integer> percentagePassCount = createNumber("PercentagePassCount", Integer.class);

    public final NumberPath<Integer> percentageSkipCount = createNumber("PercentageSkipCount", Integer.class);

    public final BooleanPath result = createBoolean("Result");

    public final DateTimePath<java.sql.Timestamp> startDateTime = createDateTime("StartDateTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> testRunID = createNumber("TestRunID", Integer.class);

    public final NumberPath<Integer> testScenarioID = createNumber("TestScenarioID", Integer.class);

    public final NumberPath<Integer> testScenarioResultID = createNumber("TestScenarioResultID", Integer.class);

    public final NumberPath<Integer> testSuiteID = createNumber("TestSuiteID", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QTestscenarioresult> primary = createPrimaryKey(testScenarioResultID);

    public final com.mysema.query.sql.ForeignKey<QTestsuite> testScenarioResultTestSuiteFK = createForeignKey(testSuiteID, "TestSuiteID");

    public final com.mysema.query.sql.ForeignKey<QSchedulerrundetails> testScenarioResultSchedulerRunDetailsFK = createForeignKey(testRunID, "TestRunID");

    public final com.mysema.query.sql.ForeignKey<QTestscenario> testScenarioResultTestScenarioFK = createForeignKey(testScenarioID, "TestScenarioID");

    public QTestscenarioresult(String variable) {
        super(QTestscenarioresult.class, forVariable(variable), "null", "testscenarioresult");
    }

    @SuppressWarnings("all")
    public QTestscenarioresult(Path<? extends QTestscenarioresult> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "testscenarioresult");
    }

    public QTestscenarioresult(PathMetadata<?> metadata) {
        super(QTestscenarioresult.class, metadata, "null", "testscenarioresult");
    }

}

