package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QActions is a Querydsl query type for QActions
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QActions extends com.mysema.query.sql.RelationalPathBase<QActions> {

    private static final long serialVersionUID = 140842116;

    public static final QActions actions = new QActions("actions");

    public final NumberPath<Integer> actionID = createNumber("ActionID", Integer.class);

    public final StringPath actionName = createString("ActionName");

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QActions> primary = createPrimaryKey(actionID);

    public final com.mysema.query.sql.ForeignKey<QObjecttype> _objectTypeAPPLICATIONFKv1 = createInvForeignKey(actionID, "ActionId");

    public final com.mysema.query.sql.ForeignKey<QTeststep> _testStepActionsFK = createInvForeignKey(actionID, "ActionID");

    public QActions(String variable) {
        super(QActions.class, forVariable(variable), "null", "actions");
    }

    @SuppressWarnings("all")
    public QActions(Path<? extends QActions> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "actions");
    }

    public QActions(PathMetadata<?> metadata) {
        super(QActions.class, metadata, "null", "actions");
    }

}

