package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTeststepresult is a Querydsl query type for QTeststepresult
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTeststepresult extends com.mysema.query.sql.RelationalPathBase<QTeststepresult> {

    private static final long serialVersionUID = 172955447;

    public static final QTeststepresult teststepresult = new QTeststepresult("teststepresult");

    public final StringPath comment = createString("Comment");

    public final NumberPath<Integer> duration = createNumber("Duration", Integer.class);

    public final DateTimePath<java.sql.Timestamp> endDateTime = createDateTime("EndDateTime", java.sql.Timestamp.class);

    public final StringPath exception = createString("EXCEPTION");

    public final StringPath request = createString("Request");

    public final StringPath response = createString("Response");

    public final NumberPath<Integer> result = createNumber("Result", Integer.class);

    public final DateTimePath<java.sql.Timestamp> startDateTime = createDateTime("StartDateTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> testCaseID = createNumber("TestCaseID", Integer.class);

    public final NumberPath<Integer> testRunID = createNumber("TestRunID", Integer.class);

    public final NumberPath<Integer> testStepID = createNumber("TestStepID", Integer.class);

    public final NumberPath<Integer> testStepResultID = createNumber("TestStepResultID", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QTeststepresult> primary = createPrimaryKey(testStepResultID);

    public final com.mysema.query.sql.ForeignKey<QSchedulerrundetails> testStepResultSchedulerRunDetailsFK = createForeignKey(testRunID, "TestRunID");

    public final com.mysema.query.sql.ForeignKey<QTeststep> testStepResultTestCaseResultFKv1 = createForeignKey(testStepID, "TestStepID");

    public final com.mysema.query.sql.ForeignKey<QTestcase> testStepResultTestCaseFK = createForeignKey(testCaseID, "TestCaseID");

    public QTeststepresult(String variable) {
        super(QTeststepresult.class, forVariable(variable), "null", "teststepresult");
    }

    @SuppressWarnings("all")
    public QTeststepresult(Path<? extends QTeststepresult> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "teststepresult");
    }

    public QTeststepresult(PathMetadata<?> metadata) {
        super(QTeststepresult.class, metadata, "null", "teststepresult");
    }

}

