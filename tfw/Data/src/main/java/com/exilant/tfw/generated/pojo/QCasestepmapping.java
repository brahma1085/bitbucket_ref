package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCasestepmapping is a Querydsl query type for QCasestepmapping
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCasestepmapping extends com.mysema.query.sql.RelationalPathBase<QCasestepmapping> {

    private static final long serialVersionUID = 403247065;

    public static final QCasestepmapping casestepmapping = new QCasestepmapping("casestepmapping");

    public final NumberPath<Integer> caseStepMappingID = createNumber("CaseStepMappingID", Integer.class);

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final NumberPath<Integer> testCaseID = createNumber("TestCaseID", Integer.class);

    public final NumberPath<Integer> testStepID = createNumber("TestStepID", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QCasestepmapping> primary = createPrimaryKey(caseStepMappingID, testCaseID, testStepID);

    public final com.mysema.query.sql.ForeignKey<QTeststep> caseStepMappingTestStep = createForeignKey(testStepID, "TestStepID");

    public final com.mysema.query.sql.ForeignKey<QTestcase> caseStepMappingTestCaseFK = createForeignKey(testCaseID, "TestCaseID");

    public QCasestepmapping(String variable) {
        super(QCasestepmapping.class, forVariable(variable), "null", "casestepmapping");
    }

    @SuppressWarnings("all")
    public QCasestepmapping(Path<? extends QCasestepmapping> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "casestepmapping");
    }

    public QCasestepmapping(PathMetadata<?> metadata) {
        super(QCasestepmapping.class, metadata, "null", "casestepmapping");
    }

}

