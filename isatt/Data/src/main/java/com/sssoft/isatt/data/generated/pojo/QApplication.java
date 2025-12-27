package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QApplication is a Querydsl query type for QApplication
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QApplication extends com.mysema.query.sql.RelationalPathBase<QApplication> {

    private static final long serialVersionUID = 777442196;

    public static final QApplication application = new QApplication("application");

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final StringPath appName = createString("AppName");

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QApplication> primary = createPrimaryKey(appID);

    public final com.mysema.query.sql.ForeignKey<QObjectgroup> _objectGroupAPPLICATIONFK = createInvForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QTestscenario> _testScenarioApplicationFK = createInvForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QTestsuite> _testSuiteApplicationFKv1 = createInvForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QTestplan> _appIDApplication11 = createInvForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QReplacementStrings> _replacementStringsApplicationFK = createInvForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QTestsuite> _testSuiteAPPLICATIONFK = createInvForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QScreen> _screenAPPLICATIONFK = createInvForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QAppfunctionality> _functionalAPPLICATIONFK = createInvForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QConditiongroup> _conditionGroupAPPLICATIONFK = createInvForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QParamgroup> _paramGroupAPPLICATIONFK = createInvForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QUsersapplicationmapping> _usersApplicationMappingApplicationFK = createInvForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QIdentifiertype> _identifierTypeAPPLICATIONFK = createInvForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QObjects> _objectsApplicationFK = createInvForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QGenericdata> _genericDataApplicationFK = createInvForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QScheduler> _schedulerApplicationFK = createInvForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QTestdata> _testDataApplicationFK = createInvForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QSchedulerrundetails> _schedulerRunDetailsApplicationFK = createInvForeignKey(appID, "AppID");

    public QApplication(String variable) {
        super(QApplication.class, forVariable(variable), "null", "application");
    }

    @SuppressWarnings("all")
    public QApplication(Path<? extends QApplication> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "application");
    }

    public QApplication(PathMetadata<?> metadata) {
        super(QApplication.class, metadata, "null", "application");
    }

}

