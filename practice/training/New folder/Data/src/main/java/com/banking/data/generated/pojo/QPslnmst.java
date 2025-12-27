package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPslnmst is a Querydsl query type for QPslnmst
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPslnmst extends com.mysema.query.sql.RelationalPathBase<QPslnmst> {

    private static final long serialVersionUID = -908799756;

    public static final QPslnmst pslnmst = new QPslnmst("pslnmst");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTime = createString("de_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath lnAcNo = createString("ln_ac_no");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTime = createString("ve_time");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QPslnmst(String variable) {
        super(QPslnmst.class, forVariable(variable), "null", "pslnmst");
    }

    @SuppressWarnings("all")
    public QPslnmst(Path<? extends QPslnmst> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "pslnmst");
    }

    public QPslnmst(PathMetadata<?> metadata) {
        super(QPslnmst.class, metadata, "null", "pslnmst");
    }

}

