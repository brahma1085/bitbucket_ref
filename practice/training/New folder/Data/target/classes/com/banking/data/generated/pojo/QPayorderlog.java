package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPayorderlog is a Querydsl query type for QPayorderlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPayorderlog extends com.mysema.query.sql.RelationalPathBase<QPayorderlog> {

    private static final long serialVersionUID = -254501207;

    public static final QPayorderlog payorderlog = new QPayorderlog("payorderlog");

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

    public final com.mysema.query.sql.PrimaryKey<QPayorderlog> primary = createPrimaryKey(payordNo);

    public QPayorderlog(String variable) {
        super(QPayorderlog.class, forVariable(variable), "null", "payorderlog");
    }

    @SuppressWarnings("all")
    public QPayorderlog(Path<? extends QPayorderlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "payorderlog");
    }

    public QPayorderlog(PathMetadata<?> metadata) {
        super(QPayorderlog.class, metadata, "null", "payorderlog");
    }

}

