package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPeriodlimitlog is a Querydsl query type for QPeriodlimitlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPeriodlimitlog extends com.mysema.query.sql.RelationalPathBase<QPeriodlimitlog> {

    private static final long serialVersionUID = -583302721;

    public static final QPeriodlimitlog periodlimitlog = new QPeriodlimitlog("periodlimitlog");

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

    public QPeriodlimitlog(String variable) {
        super(QPeriodlimitlog.class, forVariable(variable), "null", "periodlimitlog");
    }

    @SuppressWarnings("all")
    public QPeriodlimitlog(Path<? extends QPeriodlimitlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "periodlimitlog");
    }

    public QPeriodlimitlog(PathMetadata<?> metadata) {
        super(QPeriodlimitlog.class, metadata, "null", "periodlimitlog");
    }

}

