package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QScheduler is a Querydsl query type for QScheduler
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QScheduler extends com.mysema.query.sql.RelationalPathBase<QScheduler> {

    private static final long serialVersionUID = 1846265474;

    public static final QScheduler scheduler = new QScheduler("scheduler");

    public final NumberPath<Integer> agentID = createNumber("AgentID", Integer.class);

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final NumberPath<Integer> failureCount = createNumber("FailureCount", Integer.class);

    public final StringPath frequency = createString("Frequency");

    public final StringPath multiLanes = createString("MultiLanes");

    public final StringPath notifications = createString("Notifications");

    public final NumberPath<Integer> scheduleID = createNumber("ScheduleID", Integer.class);

    public final StringPath schedulerName = createString("SchedulerName");

    public final DateTimePath<java.sql.Timestamp> scheduleTime = createDateTime("ScheduleTime", java.sql.Timestamp.class);

    public final StringPath status = createString("Status");

    public final NumberPath<Integer> testDataID = createNumber("TestDataID", Integer.class);

    public final NumberPath<Integer> testPlanID = createNumber("TestPlanID", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QScheduler> primary = createPrimaryKey(scheduleID);

    public final com.mysema.query.sql.ForeignKey<QTestdata> schedulerTestDataFK = createForeignKey(testDataID, "TestDataID");

    public final com.mysema.query.sql.ForeignKey<QAgentdetails> schedulerAgentDetailsFK = createForeignKey(agentID, "AgentID");

    public final com.mysema.query.sql.ForeignKey<QApplication> schedulerApplicationFK = createForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QTestplan> schedulerTestPlanFK = createForeignKey(testPlanID, "TestPlanID");

    public final com.mysema.query.sql.ForeignKey<QSchedulerlanedetails> _schedulerLaneDetailsSchedulerFK = createInvForeignKey(scheduleID, "ScheduleID");

    public final com.mysema.query.sql.ForeignKey<QSchedulerrundetails> _schedulerRunDetailsSchedulerFK = createInvForeignKey(scheduleID, "ScheduleID");

    public QScheduler(String variable) {
        super(QScheduler.class, forVariable(variable), "null", "scheduler");
    }

    @SuppressWarnings("all")
    public QScheduler(Path<? extends QScheduler> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "scheduler");
    }

    public QScheduler(PathMetadata<?> metadata) {
        super(QScheduler.class, metadata, "null", "scheduler");
    }

}

