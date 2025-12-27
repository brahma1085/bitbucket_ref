package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QMonthlysummary is a Querydsl query type for QMonthlysummary
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QMonthlysummary extends com.mysema.query.sql.RelationalPathBase<QMonthlysummary> {

    private static final long serialVersionUID = 691552846;

    public static final QMonthlysummary monthlysummary = new QMonthlysummary("monthlysummary");

    public final NumberPath<Double> cashCr = createNumber("cash_cr", Double.class);

    public final NumberPath<Double> cashDr = createNumber("cash_dr", Double.class);

    public final NumberPath<Double> cgCr = createNumber("cg_cr", Double.class);

    public final NumberPath<Double> cgDr = createNumber("cg_dr", Double.class);

    public final DateTimePath<java.sql.Timestamp> deDateTime = createDateTime("de_date_time", java.sql.Timestamp.class);

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> glCode = createNumber("gl_code", Integer.class);

    public final StringPath glType = createString("gl_type");

    public final StringPath recordType = createString("record_type");

    public final NumberPath<Double> trfCr = createNumber("trf_cr", Double.class);

    public final NumberPath<Double> trfDr = createNumber("trf_dr", Double.class);

    public final DateTimePath<java.sql.Timestamp> veDateTime = createDateTime("ve_date_time", java.sql.Timestamp.class);

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public final NumberPath<Integer> yrMth = createNumber("yr_mth", Integer.class);

    public QMonthlysummary(String variable) {
        super(QMonthlysummary.class, forVariable(variable), "null", "monthlysummary");
    }

    @SuppressWarnings("all")
    public QMonthlysummary(Path<? extends QMonthlysummary> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "monthlysummary");
    }

    public QMonthlysummary(PathMetadata<?> metadata) {
        super(QMonthlysummary.class, metadata, "null", "monthlysummary");
    }

}

