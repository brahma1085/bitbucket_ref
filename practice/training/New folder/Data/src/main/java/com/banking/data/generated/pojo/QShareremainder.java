package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QShareremainder is a Querydsl query type for QShareremainder
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QShareremainder extends com.mysema.query.sql.RelationalPathBase<QShareremainder> {

    private static final long serialVersionUID = -2140170117;

    public static final QShareremainder shareremainder = new QShareremainder("shareremainder");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath emailFlag = createString("email_flag");

    public final StringPath smsFlag = createString("sms_flag");

    public final StringPath trnDate = createString("trn_date");

    public QShareremainder(String variable) {
        super(QShareremainder.class, forVariable(variable), "null", "shareremainder");
    }

    @SuppressWarnings("all")
    public QShareremainder(Path<? extends QShareremainder> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "shareremainder");
    }

    public QShareremainder(PathMetadata<?> metadata) {
        super(QShareremainder.class, metadata, "null", "shareremainder");
    }

}

