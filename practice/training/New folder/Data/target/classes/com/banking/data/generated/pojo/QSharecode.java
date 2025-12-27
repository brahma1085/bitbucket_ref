package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSharecode is a Querydsl query type for QSharecode
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSharecode extends com.mysema.query.sql.RelationalPathBase<QSharecode> {

    private static final long serialVersionUID = -1367633449;

    public static final QSharecode sharecode = new QSharecode("sharecode");

    public final NumberPath<Integer> certPrtd = createNumber("cert_prtd", Integer.class);

    public final StringPath shCertDt = createString("sh_cert_dt");

    public final NumberPath<Integer> shCertNo = createNumber("sh_cert_no", Integer.class);

    public final NumberPath<Integer> shDistFr = createNumber("sh_dist_fr", Integer.class);

    public final NumberPath<Integer> shDistTo = createNumber("sh_dist_to", Integer.class);

    public final StringPath shInd = createString("sh_ind");

    public final NumberPath<Integer> shLfNo = createNumber("sh_lf_no", Integer.class);

    public final StringPath shareStat = createString("share_stat");

    public QSharecode(String variable) {
        super(QSharecode.class, forVariable(variable), "null", "sharecode");
    }

    @SuppressWarnings("all")
    public QSharecode(Path<? extends QSharecode> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "sharecode");
    }

    public QSharecode(PathMetadata<?> metadata) {
        super(QSharecode.class, metadata, "null", "sharecode");
    }

}

