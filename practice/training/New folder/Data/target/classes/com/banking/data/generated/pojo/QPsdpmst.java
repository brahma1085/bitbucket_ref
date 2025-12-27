package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPsdpmst is a Querydsl query type for QPsdpmst
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPsdpmst extends com.mysema.query.sql.RelationalPathBase<QPsdpmst> {

    private static final long serialVersionUID = -916128342;

    public static final QPsdpmst psdpmst = new QPsdpmst("psdpmst");

    public final StringPath acCatgry = createString("ac_catgry");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTime = createString("de_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath dpAcNo = createString("dp_ac_no");

    public final StringPath dpType = createString("dp_type");

    public final StringPath intrAcNo = createString("intr_ac_no");

    public final StringPath intrAcTy = createString("intr_ac_ty");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTime = createString("ve_time");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QPsdpmst(String variable) {
        super(QPsdpmst.class, forVariable(variable), "null", "psdpmst");
    }

    @SuppressWarnings("all")
    public QPsdpmst(Path<? extends QPsdpmst> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "psdpmst");
    }

    public QPsdpmst(PathMetadata<?> metadata) {
        super(QPsdpmst.class, metadata, "null", "psdpmst");
    }

}

