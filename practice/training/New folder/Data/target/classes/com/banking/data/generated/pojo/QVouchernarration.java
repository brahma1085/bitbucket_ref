package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QVouchernarration is a Querydsl query type for QVouchernarration
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QVouchernarration extends com.mysema.query.sql.RelationalPathBase<QVouchernarration> {

    private static final long serialVersionUID = 1317420745;

    public static final QVouchernarration vouchernarration = new QVouchernarration("vouchernarration");

    public final StringPath narration = createString("narration");

    public final StringPath trnDt = createString("trn_dt");

    public final NumberPath<Integer> vchNo = createNumber("vch_no", Integer.class);

    public final StringPath vchTy = createString("vch_ty");

    public QVouchernarration(String variable) {
        super(QVouchernarration.class, forVariable(variable), "null", "vouchernarration");
    }

    @SuppressWarnings("all")
    public QVouchernarration(Path<? extends QVouchernarration> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "vouchernarration");
    }

    public QVouchernarration(PathMetadata<?> metadata) {
        super(QVouchernarration.class, metadata, "null", "vouchernarration");
    }

}

