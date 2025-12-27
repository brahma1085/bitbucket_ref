package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRunner is a Querydsl query type for QRunner
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QRunner extends com.mysema.query.sql.RelationalPathBase<QRunner> {

    private static final long serialVersionUID = -1942276500;

    public static final QRunner runner = new QRunner("runner");

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final NumberPath<Integer> runnerID = createNumber("RunnerID", Integer.class);

    public final StringPath runnerName = createString("RunnerName");

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QRunner> primary = createPrimaryKey(runnerID);

    public final com.mysema.query.sql.ForeignKey<QTestcase> _testCaseRunnerFK = createInvForeignKey(runnerID, "RunnerID");

    public final com.mysema.query.sql.ForeignKey<QTeststep> _testStepRunnerFKv1 = createInvForeignKey(runnerID, "RunnerID");

    public QRunner(String variable) {
        super(QRunner.class, forVariable(variable), "null", "runner");
    }

    @SuppressWarnings("all")
    public QRunner(Path<? extends QRunner> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "runner");
    }

    public QRunner(PathMetadata<?> metadata) {
        super(QRunner.class, metadata, "null", "runner");
    }

}

