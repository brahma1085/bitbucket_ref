package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QYearlystatus is a Querydsl query type for QYearlystatus
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QYearlystatus extends com.mysema.query.sql.RelationalPathBase<QYearlystatus> {

    private static final long serialVersionUID = -1743622255;

    public static final QYearlystatus yearlystatus = new QYearlystatus("yearlystatus");

    public final NumberPath<Integer> brCode = createNumber("br_code", Integer.class);

    public final DateTimePath<java.sql.Timestamp> deDateTime = createDateTime("de_date_time", java.sql.Timestamp.class);

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath postInd = createString("post_ind");

    public final StringPath yearClose = createString("year_close");

    public final NumberPath<Integer> yrMth = createNumber("yr_mth", Integer.class);

    public QYearlystatus(String variable) {
        super(QYearlystatus.class, forVariable(variable), "null", "yearlystatus");
    }

    @SuppressWarnings("all")
    public QYearlystatus(Path<? extends QYearlystatus> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "yearlystatus");
    }

    public QYearlystatus(PathMetadata<?> metadata) {
        super(QYearlystatus.class, metadata, "null", "yearlystatus");
    }

}

