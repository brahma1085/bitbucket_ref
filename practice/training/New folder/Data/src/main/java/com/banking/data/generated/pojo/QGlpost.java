package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QGlpost is a Querydsl query type for QGlpost
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QGlpost extends com.mysema.query.sql.RelationalPathBase<QGlpost> {

    private static final long serialVersionUID = 676508506;

    public static final QGlpost glpost = new QGlpost("glpost");

    public final StringPath acType = createString("ac_type");

    public final StringPath crDr = createString("cr_dr");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> glCode = createNumber("gl_code", Integer.class);

    public final StringPath glType = createString("gl_type");

    public final NumberPath<Integer> multBy = createNumber("mult_by", Integer.class);

    public final StringPath trnType = createString("trn_type");

    public QGlpost(String variable) {
        super(QGlpost.class, forVariable(variable), "null", "glpost");
    }

    @SuppressWarnings("all")
    public QGlpost(Path<? extends QGlpost> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "glpost");
    }

    public QGlpost(PathMetadata<?> metadata) {
        super(QGlpost.class, metadata, "null", "glpost");
    }

}

