package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QScreen is a Querydsl query type for QScreen
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QScreen extends com.mysema.query.sql.RelationalPathBase<QScreen> {

    private static final long serialVersionUID = 1212540933;

    public static final QScreen screen = new QScreen("screen");

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final NumberPath<Integer> featureID = createNumber("FeatureID", Integer.class);

    public final NumberPath<Integer> screenID = createNumber("ScreenID", Integer.class);

    public final StringPath screenName = createString("ScreenName");

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QScreen> primary = createPrimaryKey(screenID);

    public final com.mysema.query.sql.ForeignKey<QApplication> screenAPPLICATIONFK = createForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QAppfeature> screenAppFeatureFK = createForeignKey(featureID, "FeatureID");

    public final com.mysema.query.sql.ForeignKey<QObjectgroup> _objectGroupScreenFK = createInvForeignKey(screenID, "ScreenID");

    public QScreen(String variable) {
        super(QScreen.class, forVariable(variable), "null", "screen");
    }

    @SuppressWarnings("all")
    public QScreen(Path<? extends QScreen> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "screen");
    }

    public QScreen(PathMetadata<?> metadata) {
        super(QScreen.class, metadata, "null", "screen");
    }

}

