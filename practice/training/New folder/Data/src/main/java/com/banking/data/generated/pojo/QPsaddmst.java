package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPsaddmst is a Querydsl query type for QPsaddmst
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPsaddmst extends com.mysema.query.sql.RelationalPathBase<QPsaddmst> {

    private static final long serialVersionUID = 1567548965;

    public static final QPsaddmst psaddmst = new QPsaddmst("psaddmst");

    public final StringPath addr1 = createString("addr_1");

    public final StringPath addr2 = createString("addr_2");

    public final StringPath addr3 = createString("addr_3");

    public final StringPath addr4 = createString("addr_4");

    public final StringPath addr5 = createString("addr_5");

    public final NumberPath<Integer> addrKey = createNumber("addr_key", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTime = createString("de_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public QPsaddmst(String variable) {
        super(QPsaddmst.class, forVariable(variable), "null", "psaddmst");
    }

    @SuppressWarnings("all")
    public QPsaddmst(Path<? extends QPsaddmst> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "psaddmst");
    }

    public QPsaddmst(PathMetadata<?> metadata) {
        super(QPsaddmst.class, metadata, "null", "psaddmst");
    }

}

