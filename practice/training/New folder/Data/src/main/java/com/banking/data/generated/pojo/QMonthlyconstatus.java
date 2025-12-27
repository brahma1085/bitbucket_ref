package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QMonthlyconstatus is a Querydsl query type for QMonthlyconstatus
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QMonthlyconstatus extends com.mysema.query.sql.RelationalPathBase<QMonthlyconstatus> {

    private static final long serialVersionUID = 1221212316;

    public static final QMonthlyconstatus monthlyconstatus = new QMonthlyconstatus("monthlyconstatus");

    public final NumberPath<Integer> brCode = createNumber("br_code", Integer.class);

    public final DateTimePath<java.sql.Timestamp> deDateTime = createDateTime("de_date_time", java.sql.Timestamp.class);

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath postInd = createString("post_ind");

    public final NumberPath<Integer> yrMth = createNumber("yr_mth", Integer.class);

    public QMonthlyconstatus(String variable) {
        super(QMonthlyconstatus.class, forVariable(variable), "null", "monthlyconstatus");
    }

    @SuppressWarnings("all")
    public QMonthlyconstatus(Path<? extends QMonthlyconstatus> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "monthlyconstatus");
    }

    public QMonthlyconstatus(PathMetadata<?> metadata) {
        super(QMonthlyconstatus.class, metadata, "null", "monthlyconstatus");
    }

}

