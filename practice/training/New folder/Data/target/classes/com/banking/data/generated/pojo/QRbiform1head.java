package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRbiform1head is a Querydsl query type for QRbiform1head
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QRbiform1head extends com.mysema.query.sql.RelationalPathBase<QRbiform1head> {

    private static final long serialVersionUID = 168609737;

    public static final QRbiform1head rbiform1head = new QRbiform1head("rbiform1head");

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final DateTimePath<java.sql.Timestamp> deDtTime = createDateTime("de_dt_time", java.sql.Timestamp.class);

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> form1link = createNumber("form1link", Integer.class);

    public final DatePath<java.sql.Date> fromdate = createDate("fromdate", java.sql.Date.class);

    public final StringPath name = createString("name");

    public final DatePath<java.sql.Date> todate = createDate("todate", java.sql.Date.class);

    public QRbiform1head(String variable) {
        super(QRbiform1head.class, forVariable(variable), "null", "rbiform1head");
    }

    @SuppressWarnings("all")
    public QRbiform1head(Path<? extends QRbiform1head> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "rbiform1head");
    }

    public QRbiform1head(PathMetadata<?> metadata) {
        super(QRbiform1head.class, metadata, "null", "rbiform1head");
    }

}

