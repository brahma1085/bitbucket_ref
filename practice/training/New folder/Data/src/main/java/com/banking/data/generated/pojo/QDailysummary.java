package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDailysummary is a Querydsl query type for QDailysummary
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDailysummary extends com.mysema.query.sql.RelationalPathBase<QDailysummary> {

    private static final long serialVersionUID = -676061182;

    public static final QDailysummary dailysummary = new QDailysummary("dailysummary");

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

    public QDailysummary(String variable) {
        super(QDailysummary.class, forVariable(variable), "null", "dailysummary");
    }

    @SuppressWarnings("all")
    public QDailysummary(Path<? extends QDailysummary> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "dailysummary");
    }

    public QDailysummary(PathMetadata<?> metadata) {
        super(QDailysummary.class, metadata, "null", "dailysummary");
    }

}

