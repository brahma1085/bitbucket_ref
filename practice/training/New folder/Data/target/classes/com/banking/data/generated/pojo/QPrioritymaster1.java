package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPrioritymaster1 is a Querydsl query type for QPrioritymaster1
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPrioritymaster1 extends com.mysema.query.sql.RelationalPathBase<QPrioritymaster1> {

    private static final long serialVersionUID = -1532440778;

    public static final QPrioritymaster1 prioritymaster1 = new QPrioritymaster1("prioritymaster1");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> prCode = createNumber("pr_code", Integer.class);

    public final StringPath prDesc = createString("pr_desc");

    public QPrioritymaster1(String variable) {
        super(QPrioritymaster1.class, forVariable(variable), "null", "prioritymaster1");
    }

    @SuppressWarnings("all")
    public QPrioritymaster1(Path<? extends QPrioritymaster1> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "prioritymaster1");
    }

    public QPrioritymaster1(PathMetadata<?> metadata) {
        super(QPrioritymaster1.class, metadata, "null", "prioritymaster1");
    }

}

