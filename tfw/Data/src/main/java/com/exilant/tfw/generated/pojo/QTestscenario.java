package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTestscenario is a Querydsl query type for QTestscenario
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTestscenario extends com.mysema.query.sql.RelationalPathBase<QTestscenario> {

    private static final long serialVersionUID = -545302789;

    public static final QTestscenario testscenario = new QTestscenario("testscenario");

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final NumberPath<Integer> sortOrder = createNumber("SortOrder", Integer.class);

    public final NumberPath<Integer> testScenarioID = createNumber("TestScenarioID", Integer.class);

    public final StringPath testScenarioName = createString("TestScenarioName");

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QTestscenario> primary = createPrimaryKey(testScenarioID);

    public final com.mysema.query.sql.ForeignKey<QApplication> testScenarioApplicationFK = createForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QSuitescenariomapping> _suiteScenarioMappingTestPlanFKv1 = createInvForeignKey(testScenarioID, "TestScenarioID");

    public final com.mysema.query.sql.ForeignKey<QScenariocasemapping> _scenarioCaseMappingTestScenarioFK = createInvForeignKey(testScenarioID, "TestScenarioID");

    public final com.mysema.query.sql.ForeignKey<QTestscenarioresult> _testScenarioResultTestScenarioFK = createInvForeignKey(testScenarioID, "TestScenarioID");

    public final com.mysema.query.sql.ForeignKey<QTestcaseresult> _testCaseResultTestScenarioFK = createInvForeignKey(testScenarioID, "TestScenarioID");

    public QTestscenario(String variable) {
        super(QTestscenario.class, forVariable(variable), "null", "testscenario");
    }

    @SuppressWarnings("all")
    public QTestscenario(Path<? extends QTestscenario> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "testscenario");
    }

    public QTestscenario(PathMetadata<?> metadata) {
        super(QTestscenario.class, metadata, "null", "testscenario");
    }

}

