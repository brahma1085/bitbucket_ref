package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLoaninterestquaterlydates is a Querydsl query type for QLoaninterestquaterlydates
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLoaninterestquaterlydates extends com.mysema.query.sql.RelationalPathBase<QLoaninterestquaterlydates> {

    private static final long serialVersionUID = 92619781;

    public static final QLoaninterestquaterlydates loaninterestquaterlydates = new QLoaninterestquaterlydates("loaninterestquaterlydates");

    public final BooleanPath flag = createBoolean("flag");

    public final StringPath quaterlyDate = createString("quaterly_date");

    public QLoaninterestquaterlydates(String variable) {
        super(QLoaninterestquaterlydates.class, forVariable(variable), "null", "loaninterestquaterlydates");
    }

    @SuppressWarnings("all")
    public QLoaninterestquaterlydates(Path<? extends QLoaninterestquaterlydates> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "loaninterestquaterlydates");
    }

    public QLoaninterestquaterlydates(PathMetadata<?> metadata) {
        super(QLoaninterestquaterlydates.class, metadata, "null", "loaninterestquaterlydates");
    }

}

