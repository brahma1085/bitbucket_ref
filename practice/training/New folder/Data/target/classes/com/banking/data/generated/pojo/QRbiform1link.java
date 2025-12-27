package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRbiform1link is a Querydsl query type for QRbiform1link
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QRbiform1link extends com.mysema.query.sql.RelationalPathBase<QRbiform1link> {

    private static final long serialVersionUID = 168733155;

    public static final QRbiform1link rbiform1link = new QRbiform1link("rbiform1link");

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

    public QRbiform1link(String variable) {
        super(QRbiform1link.class, forVariable(variable), "null", "rbiform1link");
    }

    @SuppressWarnings("all")
    public QRbiform1link(Path<? extends QRbiform1link> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "rbiform1link");
    }

    public QRbiform1link(PathMetadata<?> metadata) {
        super(QRbiform1link.class, metadata, "null", "rbiform1link");
    }

}

