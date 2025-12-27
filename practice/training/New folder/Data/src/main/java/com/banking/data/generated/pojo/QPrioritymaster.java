package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPrioritymaster is a Querydsl query type for QPrioritymaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPrioritymaster extends com.mysema.query.sql.RelationalPathBase<QPrioritymaster> {

    private static final long serialVersionUID = -2127643557;

    public static final QPrioritymaster prioritymaster = new QPrioritymaster("prioritymaster");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> prCode = createNumber("pr_code", Integer.class);

    public final StringPath prDesc = createString("pr_desc");

    public QPrioritymaster(String variable) {
        super(QPrioritymaster.class, forVariable(variable), "null", "prioritymaster");
    }

    @SuppressWarnings("all")
    public QPrioritymaster(Path<? extends QPrioritymaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "prioritymaster");
    }

    public QPrioritymaster(PathMetadata<?> metadata) {
        super(QPrioritymaster.class, metadata, "null", "prioritymaster");
    }

}

