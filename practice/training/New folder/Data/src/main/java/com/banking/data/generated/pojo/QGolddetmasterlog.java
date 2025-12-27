package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QGolddetmasterlog is a Querydsl query type for QGolddetmasterlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QGolddetmasterlog extends com.mysema.query.sql.RelationalPathBase<QGolddetmasterlog> {

    private static final long serialVersionUID = -2101315228;

    public static final QGolddetmasterlog golddetmasterlog = new QGolddetmasterlog("golddetmasterlog");

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

    public QGolddetmasterlog(String variable) {
        super(QGolddetmasterlog.class, forVariable(variable), "null", "golddetmasterlog");
    }

    @SuppressWarnings("all")
    public QGolddetmasterlog(Path<? extends QGolddetmasterlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "golddetmasterlog");
    }

    public QGolddetmasterlog(PathMetadata<?> metadata) {
        super(QGolddetmasterlog.class, metadata, "null", "golddetmasterlog");
    }

}

