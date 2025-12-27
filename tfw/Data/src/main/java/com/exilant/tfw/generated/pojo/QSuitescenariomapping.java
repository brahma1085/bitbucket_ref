package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSuitescenariomapping is a Querydsl query type for QSuitescenariomapping
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSuitescenariomapping extends com.mysema.query.sql.RelationalPathBase<QSuitescenariomapping> {

    private static final long serialVersionUID = -1913141985;

    public static final QSuitescenariomapping suitescenariomapping = new QSuitescenariomapping("suitescenariomapping");

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final NumberPath<Integer> suiteScenarioMappingID = createNumber("SuiteScenarioMappingID", Integer.class);

    public final NumberPath<Integer> testScenarioID = createNumber("TestScenarioID", Integer.class);

    public final NumberPath<Integer> testSuiteID = createNumber("TestSuiteID", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QSuitescenariomapping> primary = createPrimaryKey(suiteScenarioMappingID, testScenarioID, testSuiteID);

    public final com.mysema.query.sql.ForeignKey<QTestscenario> suiteScenarioMappingTestPlanFKv1 = createForeignKey(testScenarioID, "TestScenarioID");

    public final com.mysema.query.sql.ForeignKey<QTestsuite> suiteScenarioMappingTestSuiteFK = createForeignKey(testSuiteID, "TestSuiteID");

    public QSuitescenariomapping(String variable) {
        super(QSuitescenariomapping.class, forVariable(variable), "null", "suitescenariomapping");
    }

    @SuppressWarnings("all")
    public QSuitescenariomapping(Path<? extends QSuitescenariomapping> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "suitescenariomapping");
    }

    public QSuitescenariomapping(PathMetadata<?> metadata) {
        super(QSuitescenariomapping.class, metadata, "null", "suitescenariomapping");
    }

}

