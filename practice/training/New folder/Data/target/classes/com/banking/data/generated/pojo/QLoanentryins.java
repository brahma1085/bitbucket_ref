package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLoanentryins is a Querydsl query type for QLoanentryins
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLoanentryins extends com.mysema.query.sql.RelationalPathBase<QLoanentryins> {

    private static final long serialVersionUID = -1736381503;

    public static final QLoanentryins loanentryins = new QLoanentryins("loanentryins");

    public final StringPath application = createString("Application");

    public final StringPath coborrowers = createString("Coborrowers");

    public final StringPath employment = createString("Employment");

    public final StringPath gold = createString("Gold");

    public final StringPath loanShareDet = createString("LoanShareDet");

    public final StringPath moduleabbr = createString("moduleabbr");

    public final StringPath modulecode = createString("modulecode");

    public final StringPath od = createString("Od");

    public final StringPath personal = createString("Personal");

    public final StringPath property = createString("Property");

    public final StringPath relative = createString("Relative");

    public final StringPath signIns = createString("SignIns");

    public final StringPath surities = createString("Surities");

    public final StringPath vehicle = createString("Vehicle");

    public final com.mysema.query.sql.PrimaryKey<QLoanentryins> primary = createPrimaryKey(modulecode);

    public QLoanentryins(String variable) {
        super(QLoanentryins.class, forVariable(variable), "null", "loanentryins");
    }

    @SuppressWarnings("all")
    public QLoanentryins(Path<? extends QLoanentryins> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "loanentryins");
    }

    public QLoanentryins(PathMetadata<?> metadata) {
        super(QLoanentryins.class, metadata, "null", "loanentryins");
    }

}

