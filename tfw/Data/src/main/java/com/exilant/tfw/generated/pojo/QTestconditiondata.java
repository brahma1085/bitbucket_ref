package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTestconditiondata is a Querydsl query type for QTestconditiondata
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTestconditiondata extends com.mysema.query.sql.RelationalPathBase<QTestconditiondata> {

    private static final long serialVersionUID = 40427194;

    public static final QTestconditiondata testconditiondata = new QTestconditiondata("testconditiondata");

    public final NumberPath<Integer> conditionGroupID = createNumber("ConditionGroupID", Integer.class);

    public final NumberPath<Integer> conditionID = createNumber("ConditionID", Integer.class);

    public final StringPath conditionValue = createString("ConditionValue");

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final NumberPath<Integer> testConditionDataID = createNumber("TestConditionDataID", Integer.class);

    public final NumberPath<Integer> testDataID = createNumber("TestDataID", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QTestconditiondata> primary = createPrimaryKey(testConditionDataID);

    public final com.mysema.query.sql.ForeignKey<QConditions> testCondDataConditionsFK = createForeignKey(conditionID, "ConditionID");

    public final com.mysema.query.sql.ForeignKey<QTestdata> testCondDataTestDataFK = createForeignKey(testDataID, "TestDataID");

    public final com.mysema.query.sql.ForeignKey<QConditiongroup> testCondDataConditionGroupFK = createForeignKey(conditionGroupID, "ConditionGroupID");

    public QTestconditiondata(String variable) {
        super(QTestconditiondata.class, forVariable(variable), "null", "testconditiondata");
    }

    @SuppressWarnings("all")
    public QTestconditiondata(Path<? extends QTestconditiondata> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "testconditiondata");
    }

    public QTestconditiondata(PathMetadata<?> metadata) {
        super(QTestconditiondata.class, metadata, "null", "testconditiondata");
    }

}

