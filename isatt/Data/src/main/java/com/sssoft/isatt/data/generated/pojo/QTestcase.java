package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTestcase is a Querydsl query type for QTestcase
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTestcase extends com.mysema.query.sql.RelationalPathBase<QTestcase> {

    private static final long serialVersionUID = -192831746;

    public static final QTestcase testcase = new QTestcase("testcase");

    public final StringPath active = createString("Active");

    public final StringPath caseName = createString("CaseName");

    public final StringPath classificationTag = createString("ClassificationTag");

    public final NumberPath<Integer> conditionGroupID = createNumber("ConditionGroupID", Integer.class);

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final NumberPath<Integer> featureID = createNumber("FeatureID", Integer.class);

    public final NumberPath<Integer> functionalID = createNumber("FunctionalID", Integer.class);

    public final StringPath positive = createString("Positive");

    public final NumberPath<Integer> runnerID = createNumber("RunnerID", Integer.class);

    public final NumberPath<Integer> sortOrder = createNumber("SortOrder", Integer.class);

    public final NumberPath<Integer> testCaseID = createNumber("TestCaseID", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QTestcase> primary = createPrimaryKey(testCaseID);

    public final com.mysema.query.sql.ForeignKey<QAppfunctionality> testCaseAppFunctionalityFK = createForeignKey(functionalID, "FunctionalID");

    public final com.mysema.query.sql.ForeignKey<QRunner> testCaseRunnerFK = createForeignKey(runnerID, "RunnerID");

    public final com.mysema.query.sql.ForeignKey<QConditiongroup> testCaseConditionGroupFK = createForeignKey(conditionGroupID, "ConditionGroupID");

    public final com.mysema.query.sql.ForeignKey<QAppfeature> testCaseAppFeatureFK = createForeignKey(featureID, "FeatureID");

    public final com.mysema.query.sql.ForeignKey<QCasestepmapping> _caseStepMappingTestCaseFK = createInvForeignKey(testCaseID, "TestCaseID");

    public final com.mysema.query.sql.ForeignKey<QTeststepresult> _testStepResultTestCaseFK = createInvForeignKey(testCaseID, "TestCaseID");

    public final com.mysema.query.sql.ForeignKey<QScenariocasemapping> _scenarioCaseMappingTestCaseFK = createInvForeignKey(testCaseID, "TestCaseID");

    public final com.mysema.query.sql.ForeignKey<QTestcaseresult> _testCaseResultTestCaseFK = createInvForeignKey(testCaseID, "TestCaseID");

    public QTestcase(String variable) {
        super(QTestcase.class, forVariable(variable), "null", "testcase");
    }

    @SuppressWarnings("all")
    public QTestcase(Path<? extends QTestcase> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "testcase");
    }

    public QTestcase(PathMetadata<?> metadata) {
        super(QTestcase.class, metadata, "null", "testcase");
    }

}

