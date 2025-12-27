package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPsacinst is a Querydsl query type for QPsacinst
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPsacinst extends com.mysema.query.sql.RelationalPathBase<QPsacinst> {

    private static final long serialVersionUID = 1566775360;

    public static final QPsacinst psacinst = new QPsacinst("psacinst");

    public final StringPath acNo = createString("ac_no");

    public final StringPath acTy = createString("ac_ty");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTime = createString("de_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath instrns = createString("instrns");

    public QPsacinst(String variable) {
        super(QPsacinst.class, forVariable(variable), "null", "psacinst");
    }

    @SuppressWarnings("all")
    public QPsacinst(Path<? extends QPsacinst> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "psacinst");
    }

    public QPsacinst(PathMetadata<?> metadata) {
        super(QPsacinst.class, metadata, "null", "psacinst");
    }

}

