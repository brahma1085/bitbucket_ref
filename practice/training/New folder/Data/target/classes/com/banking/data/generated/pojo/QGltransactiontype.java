package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QGltransactiontype is a Querydsl query type for QGltransactiontype
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QGltransactiontype extends com.mysema.query.sql.RelationalPathBase<QGltransactiontype> {

    private static final long serialVersionUID = 643207198;

    public static final QGltransactiontype gltransactiontype = new QGltransactiontype("gltransactiontype");

    public final NumberPath<Integer> acType = createNumber("ac_type", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> glCode = createNumber("gl_code", Integer.class);

    public final StringPath glType = createString("gl_type");

    public final StringPath trnDesc = createString("trn_desc");

    public final StringPath trnType = createString("trn_type");

    public QGltransactiontype(String variable) {
        super(QGltransactiontype.class, forVariable(variable), "null", "gltransactiontype");
    }

    @SuppressWarnings("all")
    public QGltransactiontype(Path<? extends QGltransactiontype> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "gltransactiontype");
    }

    public QGltransactiontype(PathMetadata<?> metadata) {
        super(QGltransactiontype.class, metadata, "null", "gltransactiontype");
    }

}

