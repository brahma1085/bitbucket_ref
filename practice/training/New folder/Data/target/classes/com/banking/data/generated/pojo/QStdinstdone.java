package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QStdinstdone is a Querydsl query type for QStdinstdone
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QStdinstdone extends com.mysema.query.sql.RelationalPathBase<QStdinstdone> {

    private static final long serialVersionUID = 2128529078;

    public static final QStdinstdone stdinstdone = new QStdinstdone("stdinstdone");

    public final StringPath completeInd = createString("complete_ind");

    public final StringPath deDtTime = createString("de_dt_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath dueDat = createString("due_dat");

    public final NumberPath<Double> intAmt = createNumber("int_amt", Double.class);

    public final StringPath intDate = createString("int_date");

    public final NumberPath<Double> otherAmt = createNumber("other_amt", Double.class);

    public final NumberPath<Double> penalIntAmt = createNumber("penal_int_amt", Double.class);

    public final NumberPath<Double> prinAmt = createNumber("prin_amt", Double.class);

    public final NumberPath<Integer> siNo = createNumber("si_no", Integer.class);

    public final NumberPath<Double> trnAmt = createNumber("trn_amt", Double.class);

    public final StringPath trnDate = createString("trn_date");

    public QStdinstdone(String variable) {
        super(QStdinstdone.class, forVariable(variable), "null", "stdinstdone");
    }

    @SuppressWarnings("all")
    public QStdinstdone(Path<? extends QStdinstdone> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "stdinstdone");
    }

    public QStdinstdone(PathMetadata<?> metadata) {
        super(QStdinstdone.class, metadata, "null", "stdinstdone");
    }

}

