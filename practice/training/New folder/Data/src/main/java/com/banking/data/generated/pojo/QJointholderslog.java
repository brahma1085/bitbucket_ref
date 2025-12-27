package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QJointholderslog is a Querydsl query type for QJointholderslog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QJointholderslog extends com.mysema.query.sql.RelationalPathBase<QJointholderslog> {

    private static final long serialVersionUID = 278965490;

    public static final QJointholderslog jointholderslog = new QJointholderslog("jointholderslog");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> addrType = createNumber("addr_type", Integer.class);

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public QJointholderslog(String variable) {
        super(QJointholderslog.class, forVariable(variable), "null", "jointholderslog");
    }

    @SuppressWarnings("all")
    public QJointholderslog(Path<? extends QJointholderslog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "jointholderslog");
    }

    public QJointholderslog(PathMetadata<?> metadata) {
        super(QJointholderslog.class, metadata, "null", "jointholderslog");
    }

}

