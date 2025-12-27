package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTempOdccInt is a Querydsl query type for QTempOdccInt
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTempOdccInt extends com.mysema.query.sql.RelationalPathBase<QTempOdccInt> {

    private static final long serialVersionUID = 1318816017;

    public static final QTempOdccInt tempOdccInt = new QTempOdccInt("temp_odcc_int");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final NumberPath<Integer> acType = createNumber("ac_type", Integer.class);

    public final NumberPath<Double> bal = createNumber("bal", Double.class);

    public final NumberPath<Integer> days = createNumber("days", Integer.class);

    public final StringPath fromDate = createString("from_date");

    public final NumberPath<Double> interest = createNumber("interest", Double.class);

    public final StringPath toDate = createString("to_date");

    public final NumberPath<Integer> trnSeq = createNumber("trn_seq", Integer.class);

    public QTempOdccInt(String variable) {
        super(QTempOdccInt.class, forVariable(variable), "null", "temp_odcc_int");
    }

    @SuppressWarnings("all")
    public QTempOdccInt(Path<? extends QTempOdccInt> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "temp_odcc_int");
    }

    public QTempOdccInt(PathMetadata<?> metadata) {
        super(QTempOdccInt.class, metadata, "null", "temp_odcc_int");
    }

}

