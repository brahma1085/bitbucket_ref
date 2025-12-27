package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QConditiongroup is a Querydsl query type for QConditiongroup
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QConditiongroup extends com.mysema.query.sql.RelationalPathBase<QConditiongroup> {

    private static final long serialVersionUID = -1074274944;

    public static final QConditiongroup conditiongroup = new QConditiongroup("conditiongroup");

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final NumberPath<Integer> conditionGroupID = createNumber("ConditionGroupID", Integer.class);

    public final StringPath conditionGroupName = createString("ConditionGroupName");

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QConditiongroup> primary = createPrimaryKey(conditionGroupID);

    public final com.mysema.query.sql.ForeignKey<QApplication> conditionGroupAPPLICATIONFK = createForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QTestplan> _testPlanConditionGroupFK = createInvForeignKey(conditionGroupID, "PreConditionGroupID");

    public final com.mysema.query.sql.ForeignKey<QConditions> _conditionConditionGroupFK = createInvForeignKey(conditionGroupID, "ConditionGroupID");

    public final com.mysema.query.sql.ForeignKey<QTestplan> _testPlanConditionGroupFKv2 = createInvForeignKey(conditionGroupID, "PostConditionGroupID");

    public final com.mysema.query.sql.ForeignKey<QTestcase> _testCaseConditionGroupFK = createInvForeignKey(conditionGroupID, "ConditionGroupID");

    public final com.mysema.query.sql.ForeignKey<QTestconditiondata> _testCondDataConditionGroupFK = createInvForeignKey(conditionGroupID, "ConditionGroupID");

    public QConditiongroup(String variable) {
        super(QConditiongroup.class, forVariable(variable), "null", "conditiongroup");
    }

    @SuppressWarnings("all")
    public QConditiongroup(Path<? extends QConditiongroup> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "conditiongroup");
    }

    public QConditiongroup(PathMetadata<?> metadata) {
        super(QConditiongroup.class, metadata, "null", "conditiongroup");
    }

}

