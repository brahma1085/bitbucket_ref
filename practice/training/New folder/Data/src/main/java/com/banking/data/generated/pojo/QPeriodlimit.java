package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPeriodlimit is a Querydsl query type for QPeriodlimit
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPeriodlimit extends com.mysema.query.sql.RelationalPathBase<QPeriodlimit> {

    private static final long serialVersionUID = -1501549723;

    public static final QPeriodlimit periodlimit = new QPeriodlimit("periodlimit");

    public final StringPath backup = createString("backup");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTime = createString("de_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> frLmt = createNumber("fr_lmt", Integer.class);

    public final StringPath lmtHdg = createString("lmt_hdg");

    public final NumberPath<Integer> modTy = createNumber("mod_ty", Integer.class);

    public final NumberPath<Integer> srlNo = createNumber("srl_no", Integer.class);

    public final NumberPath<Integer> toLmt = createNumber("to_lmt", Integer.class);

    public QPeriodlimit(String variable) {
        super(QPeriodlimit.class, forVariable(variable), "null", "periodlimit");
    }

    @SuppressWarnings("all")
    public QPeriodlimit(Path<? extends QPeriodlimit> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "periodlimit");
    }

    public QPeriodlimit(PathMetadata<?> metadata) {
        super(QPeriodlimit.class, metadata, "null", "periodlimit");
    }

}

