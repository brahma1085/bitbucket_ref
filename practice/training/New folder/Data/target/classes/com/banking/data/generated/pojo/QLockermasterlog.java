package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLockermasterlog is a Querydsl query type for QLockermasterlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLockermasterlog extends com.mysema.query.sql.RelationalPathBase<QLockermasterlog> {

    private static final long serialVersionUID = 2060522709;

    public static final QLockermasterlog lockermasterlog = new QLockermasterlog("lockermasterlog");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> addrType = createNumber("addr_type", Integer.class);

    public final StringPath allotDt = createString("allot_dt");

    public final StringPath autoExtn = createString("auto_extn");

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final StringPath closeDt = createString("close_dt");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath freezeInd = createString("freeze_ind");

    public final NumberPath<Integer> intrAcNo = createNumber("intr_ac_no", Integer.class);

    public final StringPath intrAcType = createString("intr_ac_type");

    public final NumberPath<Integer> lockerNo = createNumber("locker_no", Integer.class);

    public final StringPath lockerPw = createString("locker_pw");

    public final StringPath lockerTy = createString("locker_ty");

    public final NumberPath<Integer> lstTrSeq = createNumber("lst_tr_seq", Integer.class);

    public final StringPath lstTrndt = createString("lst_trndt");

    public final StringPath matDate = createString("mat_date");

    public final NumberPath<Integer> noJtHldr = createNumber("no_jt_hldr", Integer.class);

    public final NumberPath<Integer> noSecurities = createNumber("no_securities", Integer.class);

    public final NumberPath<Integer> nomNo = createNumber("nom_no", Integer.class);

    public final StringPath opInst = createString("op_inst");

    public final NumberPath<Integer> reqMths = createNumber("req_mths", Integer.class);

    public final NumberPath<Integer> requiredDays = createNumber("required_days", Integer.class);

    public final NumberPath<Integer> shNo = createNumber("sh_no", Integer.class);

    public final StringPath shType = createString("sh_type");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public final com.mysema.query.sql.PrimaryKey<QLockermasterlog> primary = createPrimaryKey(acNo, acType);

    public QLockermasterlog(String variable) {
        super(QLockermasterlog.class, forVariable(variable), "null", "lockermasterlog");
    }

    @SuppressWarnings("all")
    public QLockermasterlog(Path<? extends QLockermasterlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "lockermasterlog");
    }

    public QLockermasterlog(PathMetadata<?> metadata) {
        super(QLockermasterlog.class, metadata, "null", "lockermasterlog");
    }

}

