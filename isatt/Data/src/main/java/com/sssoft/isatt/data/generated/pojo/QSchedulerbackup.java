package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSchedulerbackup is a Querydsl query type for QSchedulerbackup
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSchedulerbackup extends com.mysema.query.sql.RelationalPathBase<QSchedulerbackup> {

    private static final long serialVersionUID = 987990657;

    public static final QSchedulerbackup schedulerbackup = new QSchedulerbackup("schedulerbackup");

    public final NumberPath<Integer> agentID = createNumber("AgentID", Integer.class);

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final NumberPath<Integer> failureCount = createNumber("FailureCount", Integer.class);

    public final StringPath frequency = createString("Frequency");

    public final StringPath multiLanes = createString("MultiLanes");

    public final StringPath notifications = createString("Notifications");

    public final NumberPath<Integer> schedulerbackupID = createNumber("SchedulerbackupID", Integer.class);

    public final StringPath schedulerName = createString("SchedulerName");

    public final DateTimePath<java.sql.Timestamp> scheduleTime = createDateTime("ScheduleTime", java.sql.Timestamp.class);

    public final StringPath status = createString("Status");

    public final NumberPath<Integer> testDataID = createNumber("TestDataID", Integer.class);

    public final NumberPath<Integer> testPlanID = createNumber("TestPlanID", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QSchedulerbackup> primary = createPrimaryKey(schedulerbackupID);

    public QSchedulerbackup(String variable) {
        super(QSchedulerbackup.class, forVariable(variable), "null", "schedulerbackup");
    }

    @SuppressWarnings("all")
    public QSchedulerbackup(Path<? extends QSchedulerbackup> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "schedulerbackup");
    }

    public QSchedulerbackup(PathMetadata<?> metadata) {
        super(QSchedulerbackup.class, metadata, "null", "schedulerbackup");
    }

}

