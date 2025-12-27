package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QMonthlystatus is a Querydsl query type for QMonthlystatus
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QMonthlystatus extends com.mysema.query.sql.RelationalPathBase<QMonthlystatus> {

    private static final long serialVersionUID = -1502986166;

    public static final QMonthlystatus monthlystatus = new QMonthlystatus("monthlystatus");

    public final DateTimePath<java.sql.Timestamp> deDateTime = createDateTime("de_date_time", java.sql.Timestamp.class);

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath monthClose = createString("month_close");

    public final StringPath postInd = createString("post_ind");

    public final NumberPath<Integer> yrMth = createNumber("yr_mth", Integer.class);

    public QMonthlystatus(String variable) {
        super(QMonthlystatus.class, forVariable(variable), "null", "monthlystatus");
    }

    @SuppressWarnings("all")
    public QMonthlystatus(Path<? extends QMonthlystatus> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "monthlystatus");
    }

    public QMonthlystatus(PathMetadata<?> metadata) {
        super(QMonthlystatus.class, metadata, "null", "monthlystatus");
    }

}

