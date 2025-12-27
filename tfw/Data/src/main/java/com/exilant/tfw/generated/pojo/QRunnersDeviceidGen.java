package com.exilant.tfw.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRunnersDeviceidGen is a Querydsl query type for QRunnersDeviceidGen
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QRunnersDeviceidGen extends com.mysema.query.sql.RelationalPathBase<QRunnersDeviceidGen> {

    private static final long serialVersionUID = 802794101;

    public static final QRunnersDeviceidGen runnersDeviceidGen = new QRunnersDeviceidGen("runners_deviceid_gen");

    public final DatePath<java.sql.Date> dateAdded = createDate("DateAdded", java.sql.Date.class);

    public final NumberPath<Integer> endRange = createNumber("EndRange", Integer.class);

    public final StringPath extraID = createString("ExtraID");

    public final StringPath info = createString("Info");

    public final NumberPath<Integer> startRange = createNumber("StartRange", Integer.class);

    public final NumberPath<Integer> tableID = createNumber("TableID", Integer.class);

    public final StringPath type = createString("Type");

    public final com.mysema.query.sql.PrimaryKey<QRunnersDeviceidGen> primary = createPrimaryKey(tableID);

    public QRunnersDeviceidGen(String variable) {
        super(QRunnersDeviceidGen.class, forVariable(variable), "null", "runners_deviceid_gen");
    }

    @SuppressWarnings("all")
    public QRunnersDeviceidGen(Path<? extends QRunnersDeviceidGen> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "runners_deviceid_gen");
    }

    public QRunnersDeviceidGen(PathMetadata<?> metadata) {
        super(QRunnersDeviceidGen.class, metadata, "null", "runners_deviceid_gen");
    }

}

