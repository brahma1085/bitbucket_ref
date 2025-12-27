package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTaskresult is a Querydsl query type for QTaskresult
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTaskresult extends com.mysema.query.sql.RelationalPathBase<QTaskresult> {

    private static final long serialVersionUID = -364529413;

    public static final QTaskresult taskresult = new QTaskresult("taskresult");

    public final DateTimePath<java.sql.Timestamp> endDateTime = createDateTime("EndDateTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> laneID = createNumber("LaneID", Integer.class);

    public final NumberPath<Integer> percentageFailCount = createNumber("PercentageFailCount", Integer.class);

    public final NumberPath<Integer> percentagePassCount = createNumber("PercentagePassCount", Integer.class);

    public final StringPath reportFilePath = createString("ReportFilePath");

    public final BooleanPath result = createBoolean("Result");

    public final StringPath runResultID = createString("RunResultID");

    public final DateTimePath<java.sql.Timestamp> startDateTime = createDateTime("StartDateTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> taskID = createNumber("TaskID", Integer.class);

    public final NumberPath<Integer> taskResultID = createNumber("TaskResultID", Integer.class);

    public final NumberPath<Integer> testRunID = createNumber("TestRunID", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QTaskresult> primary = createPrimaryKey(taskResultID);

    public final com.mysema.query.sql.ForeignKey<QSchedulerlanedetails> taskResultSchedulerLaneDetailsFK = createForeignKey(laneID, "ScheduleLaneID");

    public final com.mysema.query.sql.ForeignKey<QTask> tABLE33TaskFK = createForeignKey(taskID, "TaskID");

    public final com.mysema.query.sql.ForeignKey<QSchedulerrundetails> taskResultLaneResultsFKv1 = createForeignKey(testRunID, "TestRunID");

    public QTaskresult(String variable) {
        super(QTaskresult.class, forVariable(variable), "null", "taskresult");
    }

    @SuppressWarnings("all")
    public QTaskresult(Path<? extends QTaskresult> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "taskresult");
    }

    public QTaskresult(PathMetadata<?> metadata) {
        super(QTaskresult.class, metadata, "null", "taskresult");
    }

}

