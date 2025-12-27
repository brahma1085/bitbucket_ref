package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QBatchExceptionLog is a Querydsl query type for QBatchExceptionLog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QBatchExceptionLog extends com.mysema.query.sql.RelationalPathBase<QBatchExceptionLog> {

    private static final long serialVersionUID = -2052830886;

    public static final QBatchExceptionLog batchExceptionLog = new QBatchExceptionLog("batch_exception_log");

    public final DateTimePath<java.sql.Timestamp> dateTime = createDateTime("date_time", java.sql.Timestamp.class);

    public final StringPath logMsg = createString("log_msg");

    public final StringPath processId = createString("process_id");

    public QBatchExceptionLog(String variable) {
        super(QBatchExceptionLog.class, forVariable(variable), "null", "batch_exception_log");
    }

    @SuppressWarnings("all")
    public QBatchExceptionLog(Path<? extends QBatchExceptionLog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "batch_exception_log");
    }

    public QBatchExceptionLog(PathMetadata<?> metadata) {
        super(QBatchExceptionLog.class, metadata, "null", "batch_exception_log");
    }

}

