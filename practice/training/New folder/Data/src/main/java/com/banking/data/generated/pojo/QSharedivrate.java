package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSharedivrate is a Querydsl query type for QSharedivrate
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSharedivrate extends com.mysema.query.sql.RelationalPathBase<QSharedivrate> {

    private static final long serialVersionUID = -375472953;

    public static final QSharedivrate sharedivrate = new QSharedivrate("sharedivrate");

    public final StringPath calDone = createString("cal_done");

    public final StringPath calOpt = createString("cal_opt");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> divRate = createNumber("div_rate", Double.class);

    public final NumberPath<Double> drfAmt = createNumber("drf_amt", Double.class);

    public final StringPath frDate = createString("fr_date");

    public final StringPath toDate = createString("to_date");

    public QSharedivrate(String variable) {
        super(QSharedivrate.class, forVariable(variable), "null", "sharedivrate");
    }

    @SuppressWarnings("all")
    public QSharedivrate(Path<? extends QSharedivrate> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "sharedivrate");
    }

    public QSharedivrate(PathMetadata<?> metadata) {
        super(QSharedivrate.class, metadata, "null", "sharedivrate");
    }

}

