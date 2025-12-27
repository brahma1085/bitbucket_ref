package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QVehiclemaster is a Querydsl query type for QVehiclemaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QVehiclemaster extends com.mysema.query.sql.RelationalPathBase<QVehiclemaster> {

    private static final long serialVersionUID = -1392671079;

    public static final QVehiclemaster vehiclemaster = new QVehiclemaster("vehiclemaster");

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

    public QVehiclemaster(String variable) {
        super(QVehiclemaster.class, forVariable(variable), "null", "vehiclemaster");
    }

    @SuppressWarnings("all")
    public QVehiclemaster(Path<? extends QVehiclemaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "vehiclemaster");
    }

    public QVehiclemaster(PathMetadata<?> metadata) {
        super(QVehiclemaster.class, metadata, "null", "vehiclemaster");
    }

}

