package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSharemasterlog is a Querydsl query type for QSharemasterlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSharemasterlog extends com.mysema.query.sql.RelationalPathBase<QSharemasterlog> {

    private static final long serialVersionUID = 1496302584;

    public static final QSharemasterlog sharemasterlog = new QSharemasterlog("sharemasterlog");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> addrType = createNumber("addr_type", Integer.class);

    public final NumberPath<Integer> brCode = createNumber("br_code", Integer.class);

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath divUptodt = createString("div_uptodt");

    public final NumberPath<Integer> intrAcNo = createNumber("intr_ac_no", Integer.class);

    public final StringPath intrAcType = createString("intr_ac_type");

    public final StringPath ldgprtdt = createString("ldgprtdt");

    public final StringPath lnAvailed = createString("ln_availed");

    public final NumberPath<Integer> lstTrnSeq = createNumber("lst_trn_seq", Integer.class);

    public final NumberPath<Integer> memCat = createNumber("mem_cat", Integer.class);

    public final StringPath memClDate = createString("mem_cl_date");

    public final StringPath memIssuedate = createString("mem_issuedate");

    public final NumberPath<Integer> noCert = createNumber("no_cert", Integer.class);

    public final NumberPath<Integer> nomNo = createNumber("nom_no", Integer.class);

    public final NumberPath<Integer> payAcNo = createNumber("pay_ac_no", Integer.class);

    public final StringPath payAcType = createString("pay_ac_type");

    public final StringPath payMode = createString("pay_mode");

    public final NumberPath<Integer> relCode = createNumber("rel_code", Integer.class);

    public final StringPath relDirector = createString("rel_director");

    public final StringPath shInd = createString("sh_ind");

    public final StringPath shareStat = createString("share_stat");

    public final NumberPath<Double> shareVal = createNumber("share_val", Double.class);

    public final NumberPath<Integer> tempNo = createNumber("temp_no", Integer.class);

    public final StringPath trfDate = createString("trf_date");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QSharemasterlog(String variable) {
        super(QSharemasterlog.class, forVariable(variable), "null", "sharemasterlog");
    }

    @SuppressWarnings("all")
    public QSharemasterlog(Path<? extends QSharemasterlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "sharemasterlog");
    }

    public QSharemasterlog(PathMetadata<?> metadata) {
        super(QSharemasterlog.class, metadata, "null", "sharemasterlog");
    }

}

