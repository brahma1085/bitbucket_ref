package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QBorrowermasterlog is a Querydsl query type for QBorrowermasterlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QBorrowermasterlog extends com.mysema.query.sql.RelationalPathBase<QBorrowermasterlog> {

    private static final long serialVersionUID = 382240523;

    public static final QBorrowermasterlog borrowermasterlog = new QBorrowermasterlog("borrowermasterlog");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath brCode = createString("br_code");

    public final NumberPath<Integer> lnAcNo = createNumber("ln_ac_no", Integer.class);

    public final StringPath lnAcType = createString("ln_ac_type");

    public final StringPath type = createString("type");

    public QBorrowermasterlog(String variable) {
        super(QBorrowermasterlog.class, forVariable(variable), "null", "borrowermasterlog");
    }

    @SuppressWarnings("all")
    public QBorrowermasterlog(Path<? extends QBorrowermasterlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "borrowermasterlog");
    }

    public QBorrowermasterlog(PathMetadata<?> metadata) {
        super(QBorrowermasterlog.class, metadata, "null", "borrowermasterlog");
    }

}

