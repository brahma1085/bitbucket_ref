package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QGolddetmaster is a Querydsl query type for QGolddetmaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QGolddetmaster extends com.mysema.query.sql.RelationalPathBase<QGolddetmaster> {

    private static final long serialVersionUID = 1694359008;

    public static final QGolddetmaster golddetmaster = new QGolddetmaster("golddetmaster");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> appcode = createNumber("appcode", Integer.class);

    public final StringPath date = createString("date");

    public final StringPath descr = createString("descr");

    public final NumberPath<Double> grwgt = createNumber("grwgt", Double.class);

    public final NumberPath<Double> netwgt = createNumber("netwgt", Double.class);

    public final NumberPath<Double> rate = createNumber("rate", Double.class);

    public final NumberPath<Integer> srlNo = createNumber("srl_no", Integer.class);

    public final StringPath time = createString("time");

    public QGolddetmaster(String variable) {
        super(QGolddetmaster.class, forVariable(variable), "null", "golddetmaster");
    }

    @SuppressWarnings("all")
    public QGolddetmaster(Path<? extends QGolddetmaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "golddetmaster");
    }

    public QGolddetmaster(PathMetadata<?> metadata) {
        super(QGolddetmaster.class, metadata, "null", "golddetmaster");
    }

}

