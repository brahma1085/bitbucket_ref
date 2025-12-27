package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLoandocs is a Querydsl query type for QLoandocs
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLoandocs extends com.mysema.query.sql.RelationalPathBase<QLoandocs> {

    private static final long serialVersionUID = -354535872;

    public static final QLoandocs loandocs = new QLoandocs("loandocs");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> docCode = createNumber("doc_code", Integer.class);

    public final StringPath othDtls = createString("oth_dtls");

    public final StringPath pledgedDate = createString("pledged_date");

    public final StringPath returnDate = createString("return_date");

    public final StringPath trnKey = createString("trn_key");

    public QLoandocs(String variable) {
        super(QLoandocs.class, forVariable(variable), "null", "loandocs");
    }

    @SuppressWarnings("all")
    public QLoandocs(Path<? extends QLoandocs> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "loandocs");
    }

    public QLoandocs(PathMetadata<?> metadata) {
        super(QLoandocs.class, metadata, "null", "loandocs");
    }

}

