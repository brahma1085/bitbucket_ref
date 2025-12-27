package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCitymaster is a Querydsl query type for QCitymaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCitymaster extends com.mysema.query.sql.RelationalPathBase<QCitymaster> {

    private static final long serialVersionUID = -1506632350;

    public static final QCitymaster citymaster = new QCitymaster("citymaster");

    public final StringPath cityCode = createString("city_code");

    public final StringPath cityName = createString("city_name");

    public QCitymaster(String variable) {
        super(QCitymaster.class, forVariable(variable), "null", "citymaster");
    }

    @SuppressWarnings("all")
    public QCitymaster(Path<? extends QCitymaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "citymaster");
    }

    public QCitymaster(PathMetadata<?> metadata) {
        super(QCitymaster.class, metadata, "null", "citymaster");
    }

}

