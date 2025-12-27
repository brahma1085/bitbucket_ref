package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QBatchProcessDetails is a Querydsl query type for QBatchProcessDetails
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QBatchProcessDetails extends com.mysema.query.sql.RelationalPathBase<QBatchProcessDetails> {

    private static final long serialVersionUID = 154959992;

    public static final QBatchProcessDetails batchProcessDetails = new QBatchProcessDetails("batch_process_details");

    public final DateTimePath<java.sql.Timestamp> processEndDate = createDateTime("process_end_date", java.sql.Timestamp.class);

    public final StringPath processId = createString("process_id");

    public final StringPath processName = createString("process_name");

    public final DateTimePath<java.sql.Timestamp> processStartDate = createDateTime("process_start_date", java.sql.Timestamp.class);

    public final StringPath processStatus = createString("process_status");

    public final StringPath userId = createString("user_id");

    public QBatchProcessDetails(String variable) {
        super(QBatchProcessDetails.class, forVariable(variable), "null", "batch_process_details");
    }

    @SuppressWarnings("all")
    public QBatchProcessDetails(Path<? extends QBatchProcessDetails> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "batch_process_details");
    }

    public QBatchProcessDetails(PathMetadata<?> metadata) {
        super(QBatchProcessDetails.class, metadata, "null", "batch_process_details");
    }

}

