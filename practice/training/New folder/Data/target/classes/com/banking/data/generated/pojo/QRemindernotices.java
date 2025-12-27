package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRemindernotices is a Querydsl query type for QRemindernotices
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QRemindernotices extends com.mysema.query.sql.RelationalPathBase<QRemindernotices> {

    private static final long serialVersionUID = 413468468;

    public static final QRemindernotices remindernotices = new QRemindernotices("remindernotices");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final DatePath<java.sql.Date> date = createDate("date", java.sql.Date.class);

    public final NumberPath<Integer> noticeNo = createNumber("notice_no", Integer.class);

    public final StringPath noticeType = createString("notice_type");

    public QRemindernotices(String variable) {
        super(QRemindernotices.class, forVariable(variable), "null", "remindernotices");
    }

    @SuppressWarnings("all")
    public QRemindernotices(Path<? extends QRemindernotices> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "remindernotices");
    }

    public QRemindernotices(PathMetadata<?> metadata) {
        super(QRemindernotices.class, metadata, "null", "remindernotices");
    }

}

