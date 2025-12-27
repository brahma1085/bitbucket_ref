package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLockermaster is a Querydsl query type for QLockermaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLockermaster extends com.mysema.query.sql.RelationalPathBase<QLockermaster> {

    private static final long serialVersionUID = -1963237361;

    public static final QLockermaster lockermaster = new QLockermaster("lockermaster");

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

    public final com.mysema.query.sql.PrimaryKey<QLockermaster> primary = createPrimaryKey(acNo, acType);

    public QLockermaster(String variable) {
        super(QLockermaster.class, forVariable(variable), "null", "lockermaster");
    }

    @SuppressWarnings("all")
    public QLockermaster(Path<? extends QLockermaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "lockermaster");
    }

    public QLockermaster(PathMetadata<?> metadata) {
        super(QLockermaster.class, metadata, "null", "lockermaster");
    }

}

