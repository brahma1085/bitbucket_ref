package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QBorrowermaster is a Querydsl query type for QBorrowermaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QBorrowermaster extends com.mysema.query.sql.RelationalPathBase<QBorrowermaster> {

    private static final long serialVersionUID = 366348697;

    public static final QBorrowermaster borrowermaster = new QBorrowermaster("borrowermaster");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath brCode = createString("br_code");

    public final NumberPath<Integer> lnAcNo = createNumber("ln_ac_no", Integer.class);

    public final StringPath lnAcType = createString("ln_ac_type");

    public final StringPath type = createString("type");

    public QBorrowermaster(String variable) {
        super(QBorrowermaster.class, forVariable(variable), "null", "borrowermaster");
    }

    @SuppressWarnings("all")
    public QBorrowermaster(Path<? extends QBorrowermaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "borrowermaster");
    }

    public QBorrowermaster(PathMetadata<?> metadata) {
        super(QBorrowermaster.class, metadata, "null", "borrowermaster");
    }

}

