package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QVehiclemasterlog is a Querydsl query type for QVehiclemasterlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QVehiclemasterlog extends com.mysema.query.sql.RelationalPathBase<QVehiclemasterlog> {

    private static final long serialVersionUID = 320072203;

    public static final QVehiclemasterlog vehiclemasterlog = new QVehiclemasterlog("vehiclemasterlog");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath addrdealer = createString("addrdealer");

    public final StringPath addrpark = createString("addrpark");

    public final StringPath area = createString("area");

    public final NumberPath<Double> cost = createNumber("cost", Double.class);

    public final StringPath licno = createString("licno");

    public final StringPath make = createString("make");

    public final StringPath other = createString("other");

    public final StringPath permitno = createString("permitno");

    public final StringPath pvalidity = createString("pvalidity");

    public final StringPath type = createString("type");

    public final StringPath validity = createString("validity");

    public final StringPath vehfor = createString("vehfor");

    public QVehiclemasterlog(String variable) {
        super(QVehiclemasterlog.class, forVariable(variable), "null", "vehiclemasterlog");
    }

    @SuppressWarnings("all")
    public QVehiclemasterlog(Path<? extends QVehiclemasterlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "vehiclemasterlog");
    }

    public QVehiclemasterlog(PathMetadata<?> metadata) {
        super(QVehiclemasterlog.class, metadata, "null", "vehiclemasterlog");
    }

}

