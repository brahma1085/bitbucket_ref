package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLockerremainder is a Querydsl query type for QLockerremainder
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLockerremainder extends com.mysema.query.sql.RelationalPathBase<QLockerremainder> {

    private static final long serialVersionUID = -1575949992;

    public static final QLockerremainder lockerremainder = new QLockerremainder("lockerremainder");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath emailFlag = createString("email_flag");

    public final StringPath smsFlag = createString("sms_flag");

    public final StringPath trnDate = createString("trn_date");

    public QLockerremainder(String variable) {
        super(QLockerremainder.class, forVariable(variable), "null", "lockerremainder");
    }

    @SuppressWarnings("all")
    public QLockerremainder(Path<? extends QLockerremainder> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "lockerremainder");
    }

    public QLockerremainder(PathMetadata<?> metadata) {
        super(QLockerremainder.class, metadata, "null", "lockerremainder");
    }

}

