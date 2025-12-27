package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPsshmst is a Querydsl query type for QPsshmst
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPsshmst extends com.mysema.query.sql.RelationalPathBase<QPsshmst> {

    private static final long serialVersionUID = -902513855;

    public static final QPsshmst psshmst = new QPsshmst("psshmst");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTime = createString("de_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath intrAcNo = createString("intr_ac_no");

    public final StringPath intrAcTy = createString("intr_ac_ty");

    public final StringPath lnBrCd = createString("ln_br_cd");

    public final StringPath scSt = createString("sc_st");

    public final StringPath sexCd = createString("sex_cd");

    public final StringPath shLfNo = createString("sh_lf_no");

    public final StringPath shareStat = createString("share_stat");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTime = createString("ve_time");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QPsshmst(String variable) {
        super(QPsshmst.class, forVariable(variable), "null", "psshmst");
    }

    @SuppressWarnings("all")
    public QPsshmst(Path<? extends QPsshmst> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "psshmst");
    }

    public QPsshmst(PathMetadata<?> metadata) {
        super(QPsshmst.class, metadata, "null", "psshmst");
    }

}

