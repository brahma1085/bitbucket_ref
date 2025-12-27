package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAppfunctionality is a Querydsl query type for QAppfunctionality
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAppfunctionality extends com.mysema.query.sql.RelationalPathBase<QAppfunctionality> {

    private static final long serialVersionUID = 2075396998;

    public static final QAppfunctionality appfunctionality = new QAppfunctionality("appfunctionality");

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final NumberPath<Integer> functionalID = createNumber("FunctionalID", Integer.class);

    public final StringPath functionalName = createString("FunctionalName");

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QAppfunctionality> primary = createPrimaryKey(functionalID);

    public final com.mysema.query.sql.ForeignKey<QApplication> functionalAPPLICATIONFK = createForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QTestcase> _testCaseAppFunctionalityFK = createInvForeignKey(functionalID, "FunctionalID");

    public final com.mysema.query.sql.ForeignKey<QAppfeature> _appFeatureScreenFKv1 = createInvForeignKey(functionalID, "FunctionalID");

    public QAppfunctionality(String variable) {
        super(QAppfunctionality.class, forVariable(variable), "null", "appfunctionality");
    }

    @SuppressWarnings("all")
    public QAppfunctionality(Path<? extends QAppfunctionality> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "appfunctionality");
    }

    public QAppfunctionality(PathMetadata<?> metadata) {
        super(QAppfunctionality.class, metadata, "null", "appfunctionality");
    }

}

