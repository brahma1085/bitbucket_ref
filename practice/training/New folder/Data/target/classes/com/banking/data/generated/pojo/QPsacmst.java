package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPsacmst is a Querydsl query type for QPsacmst
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPsacmst extends com.mysema.query.sql.RelationalPathBase<QPsacmst> {

    private static final long serialVersionUID = -919286188;

    public static final QPsacmst psacmst = new QPsacmst("psacmst");

    public final StringPath acCatgry = createString("ac_catgry");

    public final StringPath acNo = createString("ac_no");

    public final StringPath acTy = createString("ac_ty");

    public final StringPath acType = createString("ac_type");

    public final StringPath caType = createString("ca_type");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTime = createString("de_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath intrAcNo = createString("intr_ac_no");

    public final StringPath intrAcTy = createString("intr_ac_ty");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTime = createString("ve_time");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QPsacmst(String variable) {
        super(QPsacmst.class, forVariable(variable), "null", "psacmst");
    }

    @SuppressWarnings("all")
    public QPsacmst(Path<? extends QPsacmst> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "psacmst");
    }

    public QPsacmst(PathMetadata<?> metadata) {
        super(QPsacmst.class, metadata, "null", "psacmst");
    }

}

