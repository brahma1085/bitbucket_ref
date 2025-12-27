package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSchedulerrundetails is a Querydsl query type for QSchedulerrundetails
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSchedulerrundetails extends com.mysema.query.sql.RelationalPathBase<QSchedulerrundetails> {

    private static final long serialVersionUID = -1505468071;

    public static final QSchedulerrundetails schedulerrundetails = new QSchedulerrundetails("schedulerrundetails");

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final DateTimePath<java.sql.Timestamp> endDateTime = createDateTime("EndDateTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> percentageFailCount = createNumber("PercentageFailCount", Integer.class);

    public final NumberPath<Integer> percentagePassCount = createNumber("PercentagePassCount", Integer.class);

    public final BooleanPath result = createBoolean("Result");

    public final DatePath<java.sql.Date> runTime = createDate("RunTime", java.sql.Date.class);

    public final NumberPath<Integer> scheduleID = createNumber("ScheduleID", Integer.class);

    public final DateTimePath<java.sql.Timestamp> startDateTime = createDateTime("StartDateTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> testDataID = createNumber("TestDataID", Integer.class);

    public final NumberPath<Integer> testPlanID = createNumber("TestPlanID", Integer.class);

    public final NumberPath<Integer> testRunID = createNumber("TestRunID", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QSchedulerrundetails> primary = createPrimaryKey(testRunID);

    public final com.mysema.query.sql.ForeignKey<QTestdata> schedulerRunDetailsTestDataFK = createForeignKey(testDataID, "TestDataID");

    public final com.mysema.query.sql.ForeignKey<QScheduler> schedulerRunDetailsSchedulerFK = createForeignKey(scheduleID, "ScheduleID");

    public final com.mysema.query.sql.ForeignKey<QTestplan> schedulerRunDetailsTestPlanFK = createForeignKey(testPlanID, "TestPlanID");

    public final com.mysema.query.sql.ForeignKey<QApplication> schedulerRunDetailsApplicationFK = createForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QTeststepresult> _testStepResultSchedulerRunDetailsFK = createInvForeignKey(testRunID, "TestRunID");

    public final com.mysema.query.sql.ForeignKey<QLaneresults> _laneResultsSchedulerRunDetailsFK = createInvForeignKey(testRunID, "TestRunID");

    public final com.mysema.query.sql.ForeignKey<QTestsuiteresult> _testSuiteResultSchedulerRunDetailsFK = createInvForeignKey(testRunID, "TestRunID");

    public final com.mysema.query.sql.ForeignKey<QTestscenarioresult> _testScenarioResultSchedulerRunDetailsFK = createInvForeignKey(testRunID, "TestRunID");

    public final com.mysema.query.sql.ForeignKey<QTestplanresult> _testPlanResultSchedulerRunDetailsFK = createInvForeignKey(testRunID, "TestRunID");

    public final com.mysema.query.sql.ForeignKey<QTestcaseresult> _testCaseResultSchedulerRunDetailsFK = createInvForeignKey(testRunID, "TestRunID");

    public final com.mysema.query.sql.ForeignKey<QTaskresult> _taskResultLaneResultsFKv1 = createInvForeignKey(testRunID, "TestRunID");

    public QSchedulerrundetails(String variable) {
        super(QSchedulerrundetails.class, forVariable(variable), "null", "schedulerrundetails");
    }

    @SuppressWarnings("all")
    public QSchedulerrundetails(Path<? extends QSchedulerrundetails> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "schedulerrundetails");
    }

    public QSchedulerrundetails(PathMetadata<?> metadata) {
        super(QSchedulerrundetails.class, metadata, "null", "schedulerrundetails");
    }

}

