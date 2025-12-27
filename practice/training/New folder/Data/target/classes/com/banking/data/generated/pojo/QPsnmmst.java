package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPsnmmst is a Querydsl query type for QPsnmmst
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPsnmmst extends com.mysema.query.sql.RelationalPathBase<QPsnmmst> {

    private static final long serialVersionUID = -906982505;

    public static final QPsnmmst psnmmst = new QPsnmmst("psnmmst");

    public final StringPath age = createString("age");

    public final StringPath caste = createString("caste");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTime = createString("de_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath dofBirth = createString("dof_birth");

    public final StringPath fhName = createString("fh_name");

    public final StringPath name = createString("name");

    public final StringPath nameKey = createString("name_key");

    public final StringPath occupation = createString("occupation");

    public final StringPath telNos = createString("tel_nos");

    public QPsnmmst(String variable) {
        super(QPsnmmst.class, forVariable(variable), "null", "psnmmst");
    }

    @SuppressWarnings("all")
    public QPsnmmst(Path<? extends QPsnmmst> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "psnmmst");
    }

    public QPsnmmst(PathMetadata<?> metadata) {
        super(QPsnmmst.class, metadata, "null", "psnmmst");
    }

}

