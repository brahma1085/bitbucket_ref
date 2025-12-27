package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCid is a Querydsl query type for QCid
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCid extends com.mysema.query.sql.RelationalPathBase<QCid> {

    private static final long serialVersionUID = -501981047;

    public static final QCid cid1 = new QCid("cid");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acTy = createString("ac_ty");

    public final NumberPath<Integer> addrKey = createNumber("addr_key", Integer.class);

    public final StringPath backup = createString("backup");

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTime = createString("de_time");

    public final StringPath deTml = createString("de_tml");

    public final NumberPath<Integer> deUser = createNumber("de_user", Integer.class);

    public final NumberPath<Integer> nameKey = createNumber("name_key", Integer.class);

    public final StringPath purpose = createString("purpose");

    public final NumberPath<Integer> refAcNo = createNumber("ref_ac_no", Integer.class);

    public final StringPath refAcTy = createString("ref_ac_ty");

    public QCid(String variable) {
        super(QCid.class, forVariable(variable), "null", "cid");
    }

    @SuppressWarnings("all")
    public QCid(Path<? extends QCid> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "cid");
    }

    public QCid(PathMetadata<?> metadata) {
        super(QCid.class, metadata, "null", "cid");
    }

}

