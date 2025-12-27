package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QReason is a Querydsl query type for QReason
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QReason extends com.mysema.query.sql.RelationalPathBase<QReason> {

    private static final long serialVersionUID = 984521369;

    public static final QReason reason = new QReason("reason");

    public final NumberPath<Integer> ctrlNo = createNumber("ctrl_no", Integer.class);

    public final StringPath deDtTime = createString("de_dt_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> reasonCd = createNumber("reason_cd", Integer.class);

    public final StringPath veDtTime = createString("ve_dt_time");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QReason(String variable) {
        super(QReason.class, forVariable(variable), "null", "reason");
    }

    @SuppressWarnings("all")
    public QReason(Path<? extends QReason> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "reason");
    }

    public QReason(PathMetadata<?> metadata) {
        super(QReason.class, metadata, "null", "reason");
    }

}

