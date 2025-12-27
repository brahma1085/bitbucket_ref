package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTestcaseresult is a Querydsl query type for QTestcaseresult
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTestcaseresult extends com.mysema.query.sql.RelationalPathBase<QTestcaseresult> {

    private static final long serialVersionUID = 756002971;

    public static final QTestcaseresult testcaseresult = new QTestcaseresult("testcaseresult");

    public final StringPath comment = createString("Comment");

    public final DateTimePath<java.sql.Timestamp> endDateTime = createDateTime("EndDateTime", java.sql.Timestamp.class);

    public final StringPath exception = createString("EXCEPTION");

    public final NumberPath<Integer> percentageFailCount = createNumber("PercentageFailCount", Integer.class);

    public final NumberPath<Integer> percentagePassCount = createNumber("PercentagePassCount", Integer.class);

    public final NumberPath<Integer> percentageSkipCount = createNumber("PercentageSkipCount", Integer.class);

    public final StringPath request = createString("Request");

    public final StringPath response = createString("Response");

    public final NumberPath<Integer> result = createNumber("Result", Integer.class);

    public final DateTimePath<java.sql.Timestamp> startDateTime = createDateTime("StartDateTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> testCaseID = createNumber("TestCaseID", Integer.class);

    public final NumberPath<Integer> testCaseResultID = createNumber("TestCaseResultID", Integer.class);

    public final NumberPath<Integer> testRunID = createNumber("TestRunID", Integer.class);

    public final NumberPath<Integer> testScenarioID = createNumber("TestScenarioID", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QTestcaseresult> primary = createPrimaryKey(testCaseResultID);

    public final com.mysema.query.sql.ForeignKey<QSchedulerrundetails> testCaseResultSchedulerRunDetailsFK = createForeignKey(testRunID, "TestRunID");

    public final com.mysema.query.sql.ForeignKey<QTestscenario> testCaseResultTestScenarioFK = createForeignKey(testScenarioID, "TestScenarioID");

    public final com.mysema.query.sql.ForeignKey<QTestcase> testCaseResultTestCaseFK = createForeignKey(testCaseID, "TestCaseID");

    public QTestcaseresult(String variable) {
        super(QTestcaseresult.class, forVariable(variable), "null", "testcaseresult");
    }

    @SuppressWarnings("all")
    public QTestcaseresult(Path<? extends QTestcaseresult> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "testcaseresult");
    }

    public QTestcaseresult(PathMetadata<?> metadata) {
        super(QTestcaseresult.class, metadata, "null", "testcaseresult");
    }

}

