package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPsbnmst is a Querydsl query type for QPsbnmst
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPsbnmst extends com.mysema.query.sql.RelationalPathBase<QPsbnmst> {

    private static final long serialVersionUID = -918034966;

    public static final QPsbnmst psbnmst = new QPsbnmst("psbnmst");

    public final StringPath bnAcNo = createString("bn_ac_no");

    public final StringPath bnAcTy = createString("bn_ac_ty");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTime = createString("de_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTime = createString("ve_time");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QPsbnmst(String variable) {
        super(QPsbnmst.class, forVariable(variable), "null", "psbnmst");
    }

    @SuppressWarnings("all")
    public QPsbnmst(Path<? extends QPsbnmst> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "psbnmst");
    }

    public QPsbnmst(PathMetadata<?> metadata) {
        super(QPsbnmst.class, metadata, "null", "psbnmst");
    }

}

