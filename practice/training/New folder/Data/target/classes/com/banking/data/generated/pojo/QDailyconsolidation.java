package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDailyconsolidation is a Querydsl query type for QDailyconsolidation
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDailyconsolidation extends com.mysema.query.sql.RelationalPathBase<QDailyconsolidation> {

    private static final long serialVersionUID = -1200565880;

    public static final QDailyconsolidation dailyconsolidation = new QDailyconsolidation("dailyconsolidation");

    public final NumberPath<Integer> brCode = createNumber("br_code", Integer.class);

    public final NumberPath<Double> cashCr = createNumber("cash_cr", Double.class);

    public final NumberPath<Double> cashDr = createNumber("cash_dr", Double.class);

    public final NumberPath<Double> cgCr = createNumber("cg_cr", Double.class);

    public final NumberPath<Double> cgDr = createNumber("cg_dr", Double.class);

    public final DateTimePath<java.sql.Timestamp> deDateTime = createDateTime("de_date_time", java.sql.Timestamp.class);

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> glCode = createNumber("gl_code", Integer.class);

    public final StringPath glType = createString("gl_type");

    public final NumberPath<Double> trfCr = createNumber("trf_cr", Double.class);

    public final NumberPath<Double> trfDr = createNumber("trf_dr", Double.class);

    public final DatePath<java.sql.Date> trnDt = createDate("trn_dt", java.sql.Date.class);

    public final DateTimePath<java.sql.Timestamp> veDateTime = createDateTime("ve_date_time", java.sql.Timestamp.class);

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QDailyconsolidation(String variable) {
        super(QDailyconsolidation.class, forVariable(variable), "null", "dailyconsolidation");
    }

    @SuppressWarnings("all")
    public QDailyconsolidation(Path<? extends QDailyconsolidation> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "dailyconsolidation");
    }

    public QDailyconsolidation(PathMetadata<?> metadata) {
        super(QDailyconsolidation.class, metadata, "null", "dailyconsolidation");
    }

}

