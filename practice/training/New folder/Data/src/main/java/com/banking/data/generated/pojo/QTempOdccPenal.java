package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTempOdccPenal is a Querydsl query type for QTempOdccPenal
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTempOdccPenal extends com.mysema.query.sql.RelationalPathBase<QTempOdccPenal> {

    private static final long serialVersionUID = 373033894;

    public static final QTempOdccPenal tempOdccPenal = new QTempOdccPenal("temp_odcc_penal");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final NumberPath<Integer> acType = createNumber("ac_type", Integer.class);

    public final NumberPath<Double> bal = createNumber("bal", Double.class);

    public final StringPath fromDate = createString("from_date");

    public final NumberPath<Double> interest = createNumber("interest", Double.class);

    public final NumberPath<Integer> period = createNumber("period", Integer.class);

    public final NumberPath<Double> prevPenal = createNumber("prev_penal", Double.class);

    public final StringPath toDate = createString("to_date");

    public final NumberPath<Integer> trnSeq = createNumber("trn_seq", Integer.class);

    public QTempOdccPenal(String variable) {
        super(QTempOdccPenal.class, forVariable(variable), "null", "temp_odcc_penal");
    }

    @SuppressWarnings("all")
    public QTempOdccPenal(Path<? extends QTempOdccPenal> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "temp_odcc_penal");
    }

    public QTempOdccPenal(PathMetadata<?> metadata) {
        super(QTempOdccPenal.class, metadata, "null", "temp_odcc_penal");
    }

}

