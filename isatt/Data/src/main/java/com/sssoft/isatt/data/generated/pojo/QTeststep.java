package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTeststep is a Querydsl query type for QTeststep
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTeststep extends com.mysema.query.sql.RelationalPathBase<QTeststep> {

    private static final long serialVersionUID = -192337254;

    public static final QTeststep teststep = new QTeststep("teststep");

    public final NumberPath<Integer> actionID = createNumber("ActionID", Integer.class);

    public final StringPath active = createString("Active");

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final StringPath expectedResult = createString("ExpectedResult");

    public final NumberPath<Integer> inputParamGroupID = createNumber("InputParamGroupID", Integer.class);

    public final NumberPath<Integer> outputParamGroupID = createNumber("OutputParamGroupID", Integer.class);

    public final NumberPath<Integer> postConditionGroupID = createNumber("PostConditionGroupID", Integer.class);

    public final NumberPath<Integer> preConditionGroupID = createNumber("PreConditionGroupID", Integer.class);

    public final NumberPath<Integer> runnerID = createNumber("RunnerID", Integer.class);

    public final NumberPath<Integer> sortOrder = createNumber("SortOrder", Integer.class);

    public final StringPath stepName = createString("StepName");

    public final NumberPath<Integer> testStepID = createNumber("TestStepID", Integer.class);

    public final StringPath testStepType = createString("TestStepType");

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QTeststep> primary = createPrimaryKey(testStepID);

    public final com.mysema.query.sql.ForeignKey<QActions> testStepActionsFK = createForeignKey(actionID, "ActionID");

    public final com.mysema.query.sql.ForeignKey<QRunner> testStepRunnerFKv1 = createForeignKey(runnerID, "RunnerID");

    public final com.mysema.query.sql.ForeignKey<QParamgroup> testStepParamGroup = createForeignKey(inputParamGroupID, "ParamGroupID");

    public final com.mysema.query.sql.ForeignKey<QCasestepmapping> _caseStepMappingTestStep = createInvForeignKey(testStepID, "TestStepID");

    public final com.mysema.query.sql.ForeignKey<QTeststepresult> _testStepResultTestCaseResultFKv1 = createInvForeignKey(testStepID, "TestStepID");

    public QTeststep(String variable) {
        super(QTeststep.class, forVariable(variable), "null", "teststep");
    }

    @SuppressWarnings("all")
    public QTeststep(Path<? extends QTeststep> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "teststep");
    }

    public QTeststep(PathMetadata<?> metadata) {
        super(QTeststep.class, metadata, "null", "teststep");
    }

}

