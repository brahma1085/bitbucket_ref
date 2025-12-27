package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDocparam is a Querydsl query type for QDocparam
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDocparam extends com.mysema.query.sql.RelationalPathBase<QDocparam> {

    private static final long serialVersionUID = -1353097878;

    public static final QDocparam docparam = new QDocparam("docparam");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath docDesc = createString("doc_desc");

    public final NumberPath<Integer> docNo = createNumber("doc_no", Integer.class);

    public final StringPath lnCat = createString("ln_cat");

    public QDocparam(String variable) {
        super(QDocparam.class, forVariable(variable), "null", "docparam");
    }

    @SuppressWarnings("all")
    public QDocparam(Path<? extends QDocparam> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "docparam");
    }

    public QDocparam(PathMetadata<?> metadata) {
        super(QDocparam.class, metadata, "null", "docparam");
    }

}

