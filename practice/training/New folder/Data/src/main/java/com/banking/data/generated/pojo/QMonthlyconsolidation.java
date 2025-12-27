package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QMonthlyconsolidation is a Querydsl query type for QMonthlyconsolidation
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QMonthlyconsolidation extends com.mysema.query.sql.RelationalPathBase<QMonthlyconsolidation> {

    private static final long serialVersionUID = 523045588;

    public static final QMonthlyconsolidation monthlyconsolidation = new QMonthlyconsolidation("monthlyconsolidation");

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

    public final StringPath recordType = createString("record_type");

    public final NumberPath<Double> trfCr = createNumber("trf_cr", Double.class);

    public final NumberPath<Double> trfDr = createNumber("trf_dr", Double.class);

    public final DateTimePath<java.sql.Timestamp> veDateTime = createDateTime("ve_date_time", java.sql.Timestamp.class);

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public final NumberPath<Integer> yrMth = createNumber("yr_mth", Integer.class);

    public QMonthlyconsolidation(String variable) {
        super(QMonthlyconsolidation.class, forVariable(variable), "null", "monthlyconsolidation");
    }

    @SuppressWarnings("all")
    public QMonthlyconsolidation(Path<? extends QMonthlyconsolidation> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "monthlyconsolidation");
    }

    public QMonthlyconsolidation(PathMetadata<?> metadata) {
        super(QMonthlyconsolidation.class, metadata, "null", "monthlyconsolidation");
    }

}

