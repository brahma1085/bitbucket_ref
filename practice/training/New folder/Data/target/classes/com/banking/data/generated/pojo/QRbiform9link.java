package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRbiform9link is a Querydsl query type for QRbiform9link
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QRbiform9link extends com.mysema.query.sql.RelationalPathBase<QRbiform9link> {

    private static final long serialVersionUID = 176121323;

    public static final QRbiform9link rbiform9link = new QRbiform9link("rbiform9link");

    public final StringPath cdInd = createString("cd_ind");

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final DateTimePath<java.sql.Timestamp> deDtTime = createDateTime("de_dt_time", java.sql.Timestamp.class);

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final DatePath<java.sql.Date> fromdate = createDate("fromdate", java.sql.Date.class);

    public final NumberPath<Integer> glCode = createNumber("gl_code", Integer.class);

    public final StringPath glType = createString("gl_type");

    public final NumberPath<Integer> mulBy = createNumber("mul_by", Integer.class);

    public final NumberPath<Double> percentage = createNumber("percentage", Double.class);

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final DatePath<java.sql.Date> todate = createDate("todate", java.sql.Date.class);

    public final StringPath trnSrc = createString("trn_src");

    public QRbiform9link(String variable) {
        super(QRbiform9link.class, forVariable(variable), "null", "rbiform9link");
    }

    @SuppressWarnings("all")
    public QRbiform9link(Path<? extends QRbiform9link> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "rbiform9link");
    }

    public QRbiform9link(PathMetadata<?> metadata) {
        super(QRbiform9link.class, metadata, "null", "rbiform9link");
    }

}

