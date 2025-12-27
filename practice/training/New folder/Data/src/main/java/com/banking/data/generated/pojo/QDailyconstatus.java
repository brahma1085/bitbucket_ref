package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDailyconstatus is a Querydsl query type for QDailyconstatus
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDailyconstatus extends com.mysema.query.sql.RelationalPathBase<QDailyconstatus> {

    private static final long serialVersionUID = 1204123984;

    public static final QDailyconstatus dailyconstatus = new QDailyconstatus("dailyconstatus");

    public final NumberPath<Integer> brCode = createNumber("br_code", Integer.class);

    public final DateTimePath<java.sql.Timestamp> deDateTime = createDateTime("de_date_time", java.sql.Timestamp.class);

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath postInd = createString("post_ind");

    public final DatePath<java.sql.Date> trnDt = createDate("trn_dt", java.sql.Date.class);

    public QDailyconstatus(String variable) {
        super(QDailyconstatus.class, forVariable(variable), "null", "dailyconstatus");
    }

    @SuppressWarnings("all")
    public QDailyconstatus(Path<? extends QDailyconstatus> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "dailyconstatus");
    }

    public QDailyconstatus(PathMetadata<?> metadata) {
        super(QDailyconstatus.class, metadata, "null", "dailyconstatus");
    }

}

