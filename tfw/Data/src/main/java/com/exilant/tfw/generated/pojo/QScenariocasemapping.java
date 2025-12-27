package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QScenariocasemapping is a Querydsl query type for QScenariocasemapping
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QScenariocasemapping extends com.mysema.query.sql.RelationalPathBase<QScenariocasemapping> {

    private static final long serialVersionUID = -2080632043;

    public static final QScenariocasemapping scenariocasemapping = new QScenariocasemapping("scenariocasemapping");

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final NumberPath<Integer> scenarioCaseMappingID = createNumber("ScenarioCaseMappingID", Integer.class);

    public final NumberPath<Integer> testCaseID = createNumber("TestCaseID", Integer.class);

    public final NumberPath<Integer> testScenarioID = createNumber("TestScenarioID", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QScenariocasemapping> primary = createPrimaryKey(scenarioCaseMappingID, testCaseID, testScenarioID);

    public final com.mysema.query.sql.ForeignKey<QTestscenario> scenarioCaseMappingTestScenarioFK = createForeignKey(testScenarioID, "TestScenarioID");

    public final com.mysema.query.sql.ForeignKey<QTestcase> scenarioCaseMappingTestCaseFK = createForeignKey(testCaseID, "TestCaseID");

    public QScenariocasemapping(String variable) {
        super(QScenariocasemapping.class, forVariable(variable), "null", "scenariocasemapping");
    }

    @SuppressWarnings("all")
    public QScenariocasemapping(Path<? extends QScenariocasemapping> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "scenariocasemapping");
    }

    public QScenariocasemapping(PathMetadata<?> metadata) {
        super(QScenariocasemapping.class, metadata, "null", "scenariocasemapping");
    }

}

