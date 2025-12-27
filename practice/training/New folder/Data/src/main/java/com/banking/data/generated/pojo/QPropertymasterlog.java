package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPropertymasterlog is a Querydsl query type for QPropertymasterlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPropertymasterlog extends com.mysema.query.sql.RelationalPathBase<QPropertymasterlog> {

    private static final long serialVersionUID = 1129089016;

    public static final QPropertymasterlog propertymasterlog = new QPropertymasterlog("propertymasterlog");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath addr = createString("addr");

    public final StringPath eastBy = createString("east_by");

    public final StringPath eastWest = createString("east_west");

    public final NumberPath<Double> marketValue = createNumber("market_value", Double.class);

    public final StringPath nature = createString("nature");

    public final StringPath northBy = createString("north_by");

    public final StringPath northSouth = createString("north_south");

    public final StringPath propertyAqd = createString("property_aqd");

    public final StringPath propertyNo = createString("property_no");

    public final NumberPath<Double> rentMnth = createNumber("rent_mnth", Double.class);

    public final StringPath southBy = createString("south_by");

    public final StringPath tenantName = createString("tenant_name");

    public final StringPath type = createString("type");

    public final StringPath westBy = createString("west_by");

    public QPropertymasterlog(String variable) {
        super(QPropertymasterlog.class, forVariable(variable), "null", "propertymasterlog");
    }

    @SuppressWarnings("all")
    public QPropertymasterlog(Path<? extends QPropertymasterlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "propertymasterlog");
    }

    public QPropertymasterlog(PathMetadata<?> metadata) {
        super(QPropertymasterlog.class, metadata, "null", "propertymasterlog");
    }

}

