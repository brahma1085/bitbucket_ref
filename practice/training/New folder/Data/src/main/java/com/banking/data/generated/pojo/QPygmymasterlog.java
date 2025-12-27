package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPygmymasterlog is a Querydsl query type for QPygmymasterlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPygmymasterlog extends com.mysema.query.sql.RelationalPathBase<QPygmymasterlog> {

    private static final long serialVersionUID = -440856947;

    public static final QPygmymasterlog pygmymasterlog = new QPygmymasterlog("pygmymasterlog");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> addrType = createNumber("addr_type", Integer.class);

    public final NumberPath<Integer> agtNo = createNumber("agt_no", Integer.class);

    public final StringPath agtType = createString("agt_type");

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final StringPath closeDate = createString("close_date");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath ldgrprtdt = createString("ldgrprtdt");

    public final NumberPath<Integer> lnAcNo = createNumber("ln_ac_no", Integer.class);

    public final StringPath lnAcType = createString("ln_ac_type");

    public final StringPath lnAvld = createString("ln_avld");

    public final StringPath lstIntDt = createString("lst_int_dt");

    public final NumberPath<Integer> lstTrnSeq = createNumber("lst_trn_seq", Integer.class);

    public final StringPath lstWdlDt = createString("lst_wdl_dt");

    public final NumberPath<Integer> lstWdlNo = createNumber("lst_wdl_no", Integer.class);

    public final NumberPath<Integer> noJtHldr = createNumber("no_jt_hldr", Integer.class);

    public final NumberPath<Integer> nomNo = createNumber("nom_no", Integer.class);

    public final StringPath openDate = createString("open_date");

    public final NumberPath<Integer> payAcNo = createNumber("pay_ac_no", Integer.class);

    public final StringPath payAcType = createString("pay_ac_type");

    public final StringPath payMode = createString("pay_mode");

    public final NumberPath<Integer> refInd = createNumber("ref_ind", Integer.class);

    public final StringPath status = createString("status");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public final NumberPath<Double> wdlAmt = createNumber("wdl_amt", Double.class);

    public QPygmymasterlog(String variable) {
        super(QPygmymasterlog.class, forVariable(variable), "null", "pygmymasterlog");
    }

    @SuppressWarnings("all")
    public QPygmymasterlog(Path<? extends QPygmymasterlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "pygmymasterlog");
    }

    public QPygmymasterlog(PathMetadata<?> metadata) {
        super(QPygmymasterlog.class, metadata, "null", "pygmymasterlog");
    }

}

