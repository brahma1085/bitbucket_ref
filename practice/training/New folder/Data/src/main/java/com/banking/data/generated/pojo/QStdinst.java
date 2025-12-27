package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QStdinst is a Querydsl query type for QStdinst
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QStdinst extends com.mysema.query.sql.RelationalPathBase<QStdinst> {

    private static final long serialVersionUID = 1774804276;

    public static final QStdinst stdinst = new QStdinst("stdinst");

    public final StringPath altDeDtTime = createString("alt_de_dt_time");

    public final StringPath altDeTml = createString("alt_de_tml");

    public final StringPath altDeUser = createString("alt_de_user");

    public final StringPath altVeDtTime = createString("alt_ve_dt_time");

    public final StringPath altVeTml = createString("alt_ve_tml");

    public final StringPath altVeUser = createString("alt_ve_user");

    public final NumberPath<Double> amount = createNumber("amount", Double.class);

    public final NumberPath<Double> amtAdj = createNumber("amt_adj", Double.class);

    public final StringPath deDtTime = createString("de_dt_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath delInd = createString("del_ind");

    public final StringPath dueDt = createString("due_dt");

    public final NumberPath<Integer> execTime = createNumber("exec_time", Integer.class);

    public final NumberPath<Integer> expiryDays = createNumber("expiry_days", Integer.class);

    public final NumberPath<Integer> frAcNo = createNumber("fr_ac_no", Integer.class);

    public final StringPath frAcTy = createString("fr_ac_ty");

    public final StringPath lastDate = createString("last_date");

    public final NumberPath<Integer> lnOpt = createNumber("ln_opt", Integer.class);

    public final NumberPath<Integer> noofTime = createNumber("noof_time", Integer.class);

    public final NumberPath<Integer> prdDays = createNumber("prd_days", Integer.class);

    public final NumberPath<Integer> prdMths = createNumber("prd_mths", Integer.class);

    public final NumberPath<Integer> priNo = createNumber("pri_no", Integer.class);

    public final NumberPath<Integer> siNo = createNumber("si_no", Integer.class);

    public final NumberPath<Integer> toAcNo = createNumber("to_ac_no", Integer.class);

    public final StringPath toAcTy = createString("to_ac_ty");

    public final StringPath veDtTime = createString("ve_dt_time");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public final com.mysema.query.sql.PrimaryKey<QStdinst> primary = createPrimaryKey(siNo);

    public QStdinst(String variable) {
        super(QStdinst.class, forVariable(variable), "null", "stdinst");
    }

    @SuppressWarnings("all")
    public QStdinst(Path<? extends QStdinst> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "stdinst");
    }

    public QStdinst(PathMetadata<?> metadata) {
        super(QStdinst.class, metadata, "null", "stdinst");
    }

}

