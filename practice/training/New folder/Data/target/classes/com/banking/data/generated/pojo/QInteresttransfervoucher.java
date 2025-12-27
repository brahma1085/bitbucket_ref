package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QInteresttransfervoucher is a Querydsl query type for QInteresttransfervoucher
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QInteresttransfervoucher extends com.mysema.query.sql.RelationalPathBase<QInteresttransfervoucher> {

    private static final long serialVersionUID = 1671173188;

    public static final QInteresttransfervoucher interesttransfervoucher = new QInteresttransfervoucher("interesttransfervoucher");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath ddPurInd = createString("dd_pur_ind");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> payAcNo = createNumber("pay_ac_no", Integer.class);

    public final StringPath payAcType = createString("pay_ac_type");

    public final StringPath payMode = createString("pay_mode");

    public final NumberPath<Double> vchAmount = createNumber("vch_amount", Double.class);

    public final StringPath vchDate = createString("vch_date");

    public final NumberPath<Integer> vchNo = createNumber("vch_no", Integer.class);

    public final StringPath vchPayDate = createString("vch_pay_date");

    public final StringPath vchPayInd = createString("vch_pay_ind");

    public final StringPath vchPrtInd = createString("vch_prt_ind");

    public QInteresttransfervoucher(String variable) {
        super(QInteresttransfervoucher.class, forVariable(variable), "null", "interesttransfervoucher");
    }

    @SuppressWarnings("all")
    public QInteresttransfervoucher(Path<? extends QInteresttransfervoucher> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "interesttransfervoucher");
    }

    public QInteresttransfervoucher(PathMetadata<?> metadata) {
        super(QInteresttransfervoucher.class, metadata, "null", "interesttransfervoucher");
    }

}

