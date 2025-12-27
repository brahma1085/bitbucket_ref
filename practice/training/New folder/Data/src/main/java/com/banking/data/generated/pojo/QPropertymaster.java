package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPropertymaster is a Querydsl query type for QPropertymaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPropertymaster extends com.mysema.query.sql.RelationalPathBase<QPropertymaster> {

    private static final long serialVersionUID = 317355980;

    public static final QPropertymaster propertymaster = new QPropertymaster("propertymaster");

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

    public QPropertymaster(String variable) {
        super(QPropertymaster.class, forVariable(variable), "null", "propertymaster");
    }

    @SuppressWarnings("all")
    public QPropertymaster(Path<? extends QPropertymaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "propertymaster");
    }

    public QPropertymaster(PathMetadata<?> metadata) {
        super(QPropertymaster.class, metadata, "null", "propertymaster");
    }

}

