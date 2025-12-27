package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTestsuiteresult is a Querydsl query type for QTestsuiteresult
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTestsuiteresult extends com.mysema.query.sql.RelationalPathBase<QTestsuiteresult> {

    private static final long serialVersionUID = -1976853113;

    public static final QTestsuiteresult testsuiteresult = new QTestsuiteresult("testsuiteresult");

    public final DateTimePath<java.sql.Timestamp> endDateTime = createDateTime("EndDateTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> percentageFailCount = createNumber("PercentageFailCount", Integer.class);

    public final NumberPath<Integer> percentagePassCount = createNumber("PercentagePassCount", Integer.class);

    public final BooleanPath result = createBoolean("Result");

    public final DateTimePath<java.sql.Timestamp> startDateTime = createDateTime("StartDateTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> testPlanID = createNumber("TestPlanID", Integer.class);

    public final NumberPath<Integer> testRunID = createNumber("TestRunID", Integer.class);

    public final NumberPath<Integer> testSuiteID = createNumber("TestSuiteID", Integer.class);

    public final NumberPath<Integer> testSuiteResultID = createNumber("TestSuiteResultID", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QTestsuiteresult> primary = createPrimaryKey(testSuiteResultID);

    public final com.mysema.query.sql.ForeignKey<QTestsuite> testSuiteResultTestPlanResultFKv1 = createForeignKey(testSuiteID, "TestSuiteID");

    public final com.mysema.query.sql.ForeignKey<QTestplan> testSuiteResultTestPlanFK = createForeignKey(testPlanID, "TestPlanID");

    public QTestsuiteresult(String variable) {
        super(QTestsuiteresult.class, forVariable(variable), "null", "testsuiteresult");
    }

    @SuppressWarnings("all")
    public QTestsuiteresult(Path<? extends QTestsuiteresult> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "testsuiteresult");
    }

    public QTestsuiteresult(PathMetadata<?> metadata) {
        super(QTestsuiteresult.class, metadata, "null", "testsuiteresult");
    }

}

