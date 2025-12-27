package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPayorder is a Querydsl query type for QPayorder
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPayorder extends com.mysema.query.sql.RelationalPathBase<QPayorder> {

    private static final long serialVersionUID = -824949061;

    public static final QPayorder payorder = new QPayorder("payorder");

    public final StringPath cdInd = createString("cd_ind");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath payeeNm = createString("payee_nm");

    public final StringPath payordDt = createString("payord_dt");

    public final NumberPath<Integer> payordNo = createNumber("payord_no", Integer.class);

    public final StringPath poCancel = createString("po_cancel");

    public final NumberPath<Integer> poChqNo = createNumber("po_chq_no", Integer.class);

    public final StringPath poCshDt = createString("po_csh_dt");

    public final NumberPath<Integer> poCshInd = createNumber("po_csh_ind", Integer.class);

    public final NumberPath<Integer> poEncshRefNo = createNumber("po_encsh_ref_no", Integer.class);

    public final NumberPath<Integer> poPrtInd = createNumber("po_prt_ind", Integer.class);

    public final StringPath poStop = createString("po_stop");

    public final NumberPath<Double> trnAmt = createNumber("trn_amt", Double.class);

    public final StringPath validUpto = createString("valid_upto");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public final com.mysema.query.sql.PrimaryKey<QPayorder> primary = createPrimaryKey(payordNo);

    public QPayorder(String variable) {
        super(QPayorder.class, forVariable(variable), "null", "payorder");
    }

    @SuppressWarnings("all")
    public QPayorder(Path<? extends QPayorder> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "payorder");
    }

    public QPayorder(PathMetadata<?> metadata) {
        super(QPayorder.class, metadata, "null", "payorder");
    }

}

