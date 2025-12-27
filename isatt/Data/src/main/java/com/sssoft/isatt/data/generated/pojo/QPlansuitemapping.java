package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPlansuitemapping is a Querydsl query type for QPlansuitemapping
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPlansuitemapping extends com.mysema.query.sql.RelationalPathBase<QPlansuitemapping> {

    private static final long serialVersionUID = 567741627;

    public static final QPlansuitemapping plansuitemapping = new QPlansuitemapping("plansuitemapping");

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final NumberPath<Integer> planSuiteMappingID = createNumber("PlanSuiteMappingID", Integer.class);

    public final NumberPath<Integer> testPlanID = createNumber("TestPlanID", Integer.class);

    public final NumberPath<Integer> testSuiteID = createNumber("TestSuiteID", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QPlansuitemapping> primary = createPrimaryKey(planSuiteMappingID, testPlanID, testSuiteID);

    public final com.mysema.query.sql.ForeignKey<QTestplan> tABLE43TestPlanFK = createForeignKey(testPlanID, "TestPlanID");

    public final com.mysema.query.sql.ForeignKey<QTestsuite> tABLE43TestSuiteFK = createForeignKey(testSuiteID, "TestSuiteID");

    public QPlansuitemapping(String variable) {
        super(QPlansuitemapping.class, forVariable(variable), "null", "plansuitemapping");
    }

    @SuppressWarnings("all")
    public QPlansuitemapping(Path<? extends QPlansuitemapping> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "plansuitemapping");
    }

    public QPlansuitemapping(PathMetadata<?> metadata) {
        super(QPlansuitemapping.class, metadata, "null", "plansuitemapping");
    }

}

