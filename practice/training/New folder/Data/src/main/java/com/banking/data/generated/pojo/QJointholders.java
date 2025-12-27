package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QJointholders is a Querydsl query type for QJointholders
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QJointholders extends com.mysema.query.sql.RelationalPathBase<QJointholders> {

    private static final long serialVersionUID = -78707438;

    public static final QJointholders jointholders = new QJointholders("jointholders");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> addrType = createNumber("addr_type", Integer.class);

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public QJointholders(String variable) {
        super(QJointholders.class, forVariable(variable), "null", "jointholders");
    }

    @SuppressWarnings("all")
    public QJointholders(Path<? extends QJointholders> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "jointholders");
    }

    public QJointholders(PathMetadata<?> metadata) {
        super(QJointholders.class, metadata, "null", "jointholders");
    }

}

