package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDailyData is a Querydsl query type for QDailyData
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDailyData extends com.mysema.query.sql.RelationalPathBase<QDailyData> {

    private static final long serialVersionUID = -984536850;

    public static final QDailyData dailyData = new QDailyData("daily_data");

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final NumberPath<Double> dayBalance = createNumber("day_balance", Double.class);

    public final NumberPath<Integer> glCode = createNumber("gl_code", Integer.class);

    public final StringPath glType = createString("gl_type");

    public final StringPath normalCd = createString("normal_cd");

    public QDailyData(String variable) {
        super(QDailyData.class, forVariable(variable), "null", "daily_data");
    }

    @SuppressWarnings("all")
    public QDailyData(Path<? extends QDailyData> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "daily_data");
    }

    public QDailyData(PathMetadata<?> metadata) {
        super(QDailyData.class, metadata, "null", "daily_data");
    }

}

