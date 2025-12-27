package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAcknowledge is a Querydsl query type for QAcknowledge
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAcknowledge extends com.mysema.query.sql.RelationalPathBase<QAcknowledge> {

    private static final long serialVersionUID = 511166311;

    public static final QAcknowledge acknowledge = new QAcknowledge("acknowledge");

    public final StringPath ackDate = createString("ack_date");

    public final NumberPath<Integer> ackNo = createNumber("ack_no", Integer.class);

    public final StringPath clgType = createString("clg_type");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> docSou = createNumber("doc_sou", Integer.class);

    public final StringPath reconciled = createString("reconciled");

    public final NumberPath<Double> totAmt = createNumber("tot_amt", Double.class);

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public final com.mysema.query.sql.PrimaryKey<QAcknowledge> primary = createPrimaryKey(ackNo);

    public QAcknowledge(String variable) {
        super(QAcknowledge.class, forVariable(variable), "null", "acknowledge");
    }

    @SuppressWarnings("all")
    public QAcknowledge(Path<? extends QAcknowledge> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "acknowledge");
    }

    public QAcknowledge(PathMetadata<?> metadata) {
        super(QAcknowledge.class, metadata, "null", "acknowledge");
    }

}

