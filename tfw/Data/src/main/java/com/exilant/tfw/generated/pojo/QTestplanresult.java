package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTestplanresult is a Querydsl query type for QTestplanresult
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTestplanresult extends com.mysema.query.sql.RelationalPathBase<QTestplanresult> {

    private static final long serialVersionUID = -748927695;

    public static final QTestplanresult testplanresult = new QTestplanresult("testplanresult");

    public final DateTimePath<java.sql.Timestamp> endDateTime = createDateTime("EndDateTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> percentageFailCount = createNumber("PercentageFailCount", Integer.class);

    public final NumberPath<Integer> percentagePassCount = createNumber("PercentagePassCount", Integer.class);

    public final BooleanPath result = createBoolean("Result");

    public final DateTimePath<java.sql.Timestamp> startDateTime = createDateTime("StartDateTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> taskID = createNumber("TaskID", Integer.class);

    public final NumberPath<Integer> testPlanID = createNumber("TestPlanID", Integer.class);

    public final NumberPath<Integer> testPlanResultID = createNumber("TestPlanResultID", Integer.class);

    public final StringPath testPlanRunName = createString("TestPlanRunName");

    public final NumberPath<Integer> testRunID = createNumber("TestRunID", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QTestplanresult> primary = createPrimaryKey(testPlanResultID);

    public final com.mysema.query.sql.ForeignKey<QTask> testPlanResultTaskFK = createForeignKey(taskID, "TaskID");

    public final com.mysema.query.sql.ForeignKey<QTestplan> testPlanResultTaskResultFKv1 = createForeignKey(testPlanID, "TestPlanID");

    public final com.mysema.query.sql.ForeignKey<QSchedulerrundetails> testPlanResultSchedulerRunDetailsFK = createForeignKey(testRunID, "TestRunID");

    public QTestplanresult(String variable) {
        super(QTestplanresult.class, forVariable(variable), "null", "testplanresult");
    }

    @SuppressWarnings("all")
    public QTestplanresult(Path<? extends QTestplanresult> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "testplanresult");
    }

    public QTestplanresult(PathMetadata<?> metadata) {
        super(QTestplanresult.class, metadata, "null", "testplanresult");
    }

}

