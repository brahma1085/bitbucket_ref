package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTestplan is a Querydsl query type for QTestplan
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTestplan extends com.mysema.query.sql.RelationalPathBase<QTestplan> {

    private static final long serialVersionUID = -192434441;

    public static final QTestplan testplan = new QTestplan("testplan");

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final StringPath planName = createString("PlanName");

    public final NumberPath<Integer> postConditionGroupID = createNumber("PostConditionGroupID", Integer.class);

    public final NumberPath<Integer> preConditionGroupID = createNumber("PreConditionGroupID", Integer.class);

    public final NumberPath<Integer> testPlanID = createNumber("TestPlanID", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QTestplan> primary = createPrimaryKey(testPlanID);

    public final com.mysema.query.sql.ForeignKey<QConditiongroup> testPlanConditionGroupFK = createForeignKey(preConditionGroupID, "ConditionGroupID");

    public final com.mysema.query.sql.ForeignKey<QApplication> appIDApplication11 = createForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QConditiongroup> testPlanConditionGroupFKv2 = createForeignKey(postConditionGroupID, "ConditionGroupID");

    public final com.mysema.query.sql.ForeignKey<QPlansuitemapping> _tABLE43TestPlanFK = createInvForeignKey(testPlanID, "TestPlanID");

    public final com.mysema.query.sql.ForeignKey<QTestplanresult> _testPlanResultTaskResultFKv1 = createInvForeignKey(testPlanID, "TestPlanID");

    public final com.mysema.query.sql.ForeignKey<QTestsuiteresult> _testSuiteResultTestPlanFK = createInvForeignKey(testPlanID, "TestPlanID");

    public final com.mysema.query.sql.ForeignKey<QSchedulerrundetails> _schedulerRunDetailsTestPlanFK = createInvForeignKey(testPlanID, "TestPlanID");

    public final com.mysema.query.sql.ForeignKey<QScheduler> _schedulerTestPlanFK = createInvForeignKey(testPlanID, "TestPlanID");

    public QTestplan(String variable) {
        super(QTestplan.class, forVariable(variable), "null", "testplan");
    }

    @SuppressWarnings("all")
    public QTestplan(Path<? extends QTestplan> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "testplan");
    }

    public QTestplan(PathMetadata<?> metadata) {
        super(QTestplan.class, metadata, "null", "testplan");
    }

}

