package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLaneresults is a Querydsl query type for QLaneresults
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLaneresults extends com.mysema.query.sql.RelationalPathBase<QLaneresults> {

    private static final long serialVersionUID = 1655609489;

    public static final QLaneresults laneresults = new QLaneresults("laneresults");

    public final NumberPath<Integer> agentID = createNumber("AgentID", Integer.class);

    public final StringPath buildVersion = createString("BuildVersion");

    public final DateTimePath<java.sql.Timestamp> endDateTime = createDateTime("EndDateTime", java.sql.Timestamp.class);

    public final StringPath failureDetails = createString("FailureDetails");

    public final NumberPath<Integer> laneResultsID = createNumber("LaneResultsID", Integer.class);

    public final StringPath os = createString("OS");

    public final NumberPath<Integer> percentageFailCount = createNumber("PercentageFailCount", Integer.class);

    public final NumberPath<Integer> percentagePassCount = createNumber("PercentagePassCount", Integer.class);

    public final BooleanPath result = createBoolean("Result");

    public final NumberPath<Integer> scheduleLaneID = createNumber("ScheduleLaneID", Integer.class);

    public final DateTimePath<java.sql.Timestamp> startDateTime = createDateTime("StartDateTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> testRunID = createNumber("TestRunID", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QLaneresults> primary = createPrimaryKey(laneResultsID);

    public final com.mysema.query.sql.ForeignKey<QAgentdetails> tFWLaneResultsAgentDetailsFK = createForeignKey(agentID, "AgentID");

    public final com.mysema.query.sql.ForeignKey<QSchedulerrundetails> laneResultsSchedulerRunDetailsFK = createForeignKey(testRunID, "TestRunID");

    public final com.mysema.query.sql.ForeignKey<QSchedulerlanedetails> tFWLaneResultsLaneFK = createForeignKey(scheduleLaneID, "ScheduleLaneID");

    public QLaneresults(String variable) {
        super(QLaneresults.class, forVariable(variable), "null", "laneresults");
    }

    @SuppressWarnings("all")
    public QLaneresults(Path<? extends QLaneresults> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "laneresults");
    }

    public QLaneresults(PathMetadata<?> metadata) {
        super(QLaneresults.class, metadata, "null", "laneresults");
    }

}

