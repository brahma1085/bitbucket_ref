package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QGlkeyparam is a Querydsl query type for QGlkeyparam
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QGlkeyparam extends com.mysema.query.sql.RelationalPathBase<QGlkeyparam> {

    private static final long serialVersionUID = -2138846392;

    public static final QGlkeyparam glkeyparam = new QGlkeyparam("glkeyparam");

    public final NumberPath<Integer> acType = createNumber("ac_type", Integer.class);

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> glCode = createNumber("gl_code", Integer.class);

    public final StringPath glType = createString("gl_type");

    public final StringPath keyDesc = createString("key_desc");

    public QGlkeyparam(String variable) {
        super(QGlkeyparam.class, forVariable(variable), "null", "glkeyparam");
    }

    @SuppressWarnings("all")
    public QGlkeyparam(Path<? extends QGlkeyparam> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "glkeyparam");
    }

    public QGlkeyparam(PathMetadata<?> metadata) {
        super(QGlkeyparam.class, metadata, "null", "glkeyparam");
    }

}

