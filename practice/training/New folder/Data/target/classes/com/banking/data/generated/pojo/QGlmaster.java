package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QGlmaster is a Querydsl query type for QGlmaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QGlmaster extends com.mysema.query.sql.RelationalPathBase<QGlmaster> {

    private static final long serialVersionUID = 1485799068;

    public static final QGlmaster glmaster = new QGlmaster("glmaster");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final DatePath<java.sql.Date> fromDate = createDate("from_date", java.sql.Date.class);

    public final NumberPath<Integer> glCode = createNumber("gl_code", Integer.class);

    public final StringPath glName = createString("gl_name");

    public final StringPath glStatus = createString("gl_status");

    public final StringPath glType = createString("gl_type");

    public final StringPath normalCd = createString("normal_cd");

    public final StringPath schType = createString("sch_type");

    public final DatePath<java.sql.Date> toDate = createDate("to_date", java.sql.Date.class);

    public QGlmaster(String variable) {
        super(QGlmaster.class, forVariable(variable), "null", "glmaster");
    }

    @SuppressWarnings("all")
    public QGlmaster(Path<? extends QGlmaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "glmaster");
    }

    public QGlmaster(PathMetadata<?> metadata) {
        super(QGlmaster.class, metadata, "null", "glmaster");
    }

}

