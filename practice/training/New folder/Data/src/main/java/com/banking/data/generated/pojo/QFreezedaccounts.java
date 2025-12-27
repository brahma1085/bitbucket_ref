package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QFreezedaccounts is a Querydsl query type for QFreezedaccounts
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QFreezedaccounts extends com.mysema.query.sql.RelationalPathBase<QFreezedaccounts> {

    private static final long serialVersionUID = -504733986;

    public static final QFreezedaccounts freezedaccounts = new QFreezedaccounts("freezedaccounts");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final DatePath<java.sql.Date> fromDate = createDate("from_date", java.sql.Date.class);

    public final StringPath frzInoperInd = createString("frz_inoper_ind");

    public final StringPath reason = createString("reason");

    public final DatePath<java.sql.Date> toDate = createDate("to_date", java.sql.Date.class);

    public QFreezedaccounts(String variable) {
        super(QFreezedaccounts.class, forVariable(variable), "null", "freezedaccounts");
    }

    @SuppressWarnings("all")
    public QFreezedaccounts(Path<? extends QFreezedaccounts> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "freezedaccounts");
    }

    public QFreezedaccounts(PathMetadata<?> metadata) {
        super(QFreezedaccounts.class, metadata, "null", "freezedaccounts");
    }

}

