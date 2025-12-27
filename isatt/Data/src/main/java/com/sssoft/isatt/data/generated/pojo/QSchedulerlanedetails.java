package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSchedulerlanedetails is a Querydsl query type for QSchedulerlanedetails
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSchedulerlanedetails extends com.mysema.query.sql.RelationalPathBase<QSchedulerlanedetails> {

    private static final long serialVersionUID = -1526879881;

    public static final QSchedulerlanedetails schedulerlanedetails = new QSchedulerlanedetails("schedulerlanedetails");

    public final NumberPath<Integer> agentID = createNumber("AgentID", Integer.class);

    public final NumberPath<Integer> clones = createNumber("Clones", Integer.class);

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final NumberPath<Integer> duration = createNumber("Duration", Integer.class);

    public final NumberPath<Integer> iterations = createNumber("Iterations", Integer.class);

    public final StringPath laneType = createString("LaneType");

    public final StringPath laneUserName = createString("LaneUserName");

    public final NumberPath<Integer> rampUpDelay = createNumber("RampUpDelay", Integer.class);

    public final StringPath runnerType = createString("RunnerType");

    public final NumberPath<Integer> scheduleID = createNumber("ScheduleID", Integer.class);

    public final NumberPath<Integer> scheduleLaneID = createNumber("ScheduleLaneID", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QSchedulerlanedetails> primary = createPrimaryKey(scheduleLaneID);

    public final com.mysema.query.sql.ForeignKey<QScheduler> schedulerLaneDetailsSchedulerFK = createForeignKey(scheduleID, "ScheduleID");

    public final com.mysema.query.sql.ForeignKey<QAgentdetails> schedulerLaneDetailsAgentDetailsFK = createForeignKey(agentID, "AgentID");

    public final com.mysema.query.sql.ForeignKey<QTaskresult> _taskResultSchedulerLaneDetailsFK = createInvForeignKey(scheduleLaneID, "LaneID");

    public final com.mysema.query.sql.ForeignKey<QTask> _tABLE32LaneFK = createInvForeignKey(scheduleLaneID, "LaneID");

    public final com.mysema.query.sql.ForeignKey<QLaneresults> _tFWLaneResultsLaneFK = createInvForeignKey(scheduleLaneID, "ScheduleLaneID");

    public QSchedulerlanedetails(String variable) {
        super(QSchedulerlanedetails.class, forVariable(variable), "null", "schedulerlanedetails");
    }

    @SuppressWarnings("all")
    public QSchedulerlanedetails(Path<? extends QSchedulerlanedetails> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "schedulerlanedetails");
    }

    public QSchedulerlanedetails(PathMetadata<?> metadata) {
        super(QSchedulerlanedetails.class, metadata, "null", "schedulerlanedetails");
    }

}

