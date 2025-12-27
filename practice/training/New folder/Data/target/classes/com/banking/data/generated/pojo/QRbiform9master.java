package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRbiform9master is a Querydsl query type for QRbiform9master
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QRbiform9master extends com.mysema.query.sql.RelationalPathBase<QRbiform9master> {

    private static final long serialVersionUID = 1770268691;

    public static final QRbiform9master rbiform9master = new QRbiform9master("rbiform9master");

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final DateTimePath<java.sql.Timestamp> deDtTime = createDateTime("de_dt_time", java.sql.Timestamp.class);

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> form9link = createNumber("form9link", Integer.class);

    public final DatePath<java.sql.Date> fromdate = createDate("fromdate", java.sql.Date.class);

    public final StringPath name = createString("name");

    public final DatePath<java.sql.Date> todate = createDate("todate", java.sql.Date.class);

    public QRbiform9master(String variable) {
        super(QRbiform9master.class, forVariable(variable), "null", "rbiform9master");
    }

    @SuppressWarnings("all")
    public QRbiform9master(Path<? extends QRbiform9master> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "rbiform9master");
    }

    public QRbiform9master(PathMetadata<?> metadata) {
        super(QRbiform9master.class, metadata, "null", "rbiform9master");
    }

}

