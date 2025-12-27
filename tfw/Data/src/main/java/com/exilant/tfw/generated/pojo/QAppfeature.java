package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAppfeature is a Querydsl query type for QAppfeature
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAppfeature extends com.mysema.query.sql.RelationalPathBase<QAppfeature> {

    private static final long serialVersionUID = 575356110;

    public static final QAppfeature appfeature = new QAppfeature("appfeature");

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final NumberPath<Integer> featureID = createNumber("FeatureID", Integer.class);

    public final StringPath featureName = createString("FeatureName");

    public final NumberPath<Integer> functionalID = createNumber("FunctionalID", Integer.class);

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QAppfeature> primary = createPrimaryKey(featureID);

    public final com.mysema.query.sql.ForeignKey<QAppfunctionality> appFeatureScreenFKv1 = createForeignKey(functionalID, "FunctionalID");

    public final com.mysema.query.sql.ForeignKey<QScreen> _screenAppFeatureFK = createInvForeignKey(featureID, "FeatureID");

    public final com.mysema.query.sql.ForeignKey<QTestcase> _testCaseAppFeatureFK = createInvForeignKey(featureID, "FeatureID");

    public QAppfeature(String variable) {
        super(QAppfeature.class, forVariable(variable), "null", "appfeature");
    }

    @SuppressWarnings("all")
    public QAppfeature(Path<? extends QAppfeature> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "appfeature");
    }

    public QAppfeature(PathMetadata<?> metadata) {
        super(QAppfeature.class, metadata, "null", "appfeature");
    }

}

