package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDailystatus is a Querydsl query type for QDailystatus
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDailystatus extends com.mysema.query.sql.RelationalPathBase<QDailystatus> {

    private static final long serialVersionUID = 115465238;

    public static final QDailystatus dailystatus = new QDailystatus("dailystatus");

    public final StringPath cashClose = createString("cash_close");

    public final StringPath dayClose = createString("day_close");

    public final DateTimePath<java.sql.Timestamp> deDateTime = createDateTime("de_date_time", java.sql.Timestamp.class);

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath postInd = createString("post_ind");

    public final DatePath<java.sql.Date> trnDt = createDate("trn_dt", java.sql.Date.class);

    public QDailystatus(String variable) {
        super(QDailystatus.class, forVariable(variable), "null", "dailystatus");
    }

    @SuppressWarnings("all")
    public QDailystatus(Path<? extends QDailystatus> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "dailystatus");
    }

    public QDailystatus(PathMetadata<?> metadata) {
        super(QDailystatus.class, metadata, "null", "dailystatus");
    }

}

