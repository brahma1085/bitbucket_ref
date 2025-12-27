package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTask is a Querydsl query type for QTask
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTask extends com.mysema.query.sql.RelationalPathBase<QTask> {

    private static final long serialVersionUID = -198628415;

    public static final QTask task = new QTask("task");

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath dataSet = createString("DataSet");

    public final NumberPath<Integer> laneID = createNumber("LaneID", Integer.class);

    public final NumberPath<Integer> repeatNo = createNumber("RepeatNo", Integer.class);

    public final StringPath tagsToRun = createString("TagsToRun");

    public final NumberPath<Integer> taskID = createNumber("TaskID", Integer.class);

    public final StringPath taskName = createString("TaskName");

    public final StringPath testPlanXlsPath = createString("TestPlanXlsPath");

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QTask> primary = createPrimaryKey(taskID);

    public final com.mysema.query.sql.ForeignKey<QSchedulerlanedetails> tABLE32LaneFK = createForeignKey(laneID, "ScheduleLaneID");

    public final com.mysema.query.sql.ForeignKey<QTaskresult> _tABLE33TaskFK = createInvForeignKey(taskID, "TaskID");

    public QTask(String variable) {
        super(QTask.class, forVariable(variable), "null", "task");
    }

    @SuppressWarnings("all")
    public QTask(Path<? extends QTask> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "task");
    }

    public QTask(PathMetadata<?> metadata) {
        super(QTask.class, metadata, "null", "task");
    }

}

