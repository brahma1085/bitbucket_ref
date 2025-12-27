package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCertificatemaster is a Querydsl query type for QCertificatemaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCertificatemaster extends com.mysema.query.sql.RelationalPathBase<QCertificatemaster> {

    private static final long serialVersionUID = 379748164;

    public static final QCertificatemaster certificatemaster = new QCertificatemaster("certificatemaster");

    public final StringPath allotmentdt = createString("allotmentdt");

    public final StringPath refunddt = createString("refunddt");

    public final NumberPath<Integer> shAcNo = createNumber("sh_ac_no", Integer.class);

    public final StringPath shAcType = createString("sh_ac_type");

    public final NumberPath<Integer> shDistNo = createNumber("sh_dist_no", Integer.class);

    public QCertificatemaster(String variable) {
        super(QCertificatemaster.class, forVariable(variable), "null", "certificatemaster");
    }

    @SuppressWarnings("all")
    public QCertificatemaster(Path<? extends QCertificatemaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "certificatemaster");
    }

    public QCertificatemaster(PathMetadata<?> metadata) {
        super(QCertificatemaster.class, metadata, "null", "certificatemaster");
    }

}

