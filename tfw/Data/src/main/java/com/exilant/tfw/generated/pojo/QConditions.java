package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QConditions is a Querydsl query type for QConditions
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QConditions extends com.mysema.query.sql.RelationalPathBase<QConditions> {

    private static final long serialVersionUID = 1155852753;

    public static final QConditions conditions = new QConditions("conditions");

    public final NumberPath<Integer> conditionGroupID = createNumber("ConditionGroupID", Integer.class);

    public final NumberPath<Integer> conditionID = createNumber("ConditionID", Integer.class);

    public final StringPath conditionName = createString("ConditionName");

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final StringPath expression = createString("Expression");

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QConditions> primary = createPrimaryKey(conditionID);

    public final com.mysema.query.sql.ForeignKey<QConditiongroup> conditionConditionGroupFK = createForeignKey(conditionGroupID, "ConditionGroupID");

    public final com.mysema.query.sql.ForeignKey<QTestconditiondata> _testCondDataConditionsFK = createInvForeignKey(conditionID, "ConditionID");

    public QConditions(String variable) {
        super(QConditions.class, forVariable(variable), "null", "conditions");
    }

    @SuppressWarnings("all")
    public QConditions(Path<? extends QConditions> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "conditions");
    }

    public QConditions(PathMetadata<?> metadata) {
        super(QConditions.class, metadata, "null", "conditions");
    }

}

