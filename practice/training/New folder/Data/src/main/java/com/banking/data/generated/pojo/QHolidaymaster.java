package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QHolidaymaster is a Querydsl query type for QHolidaymaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QHolidaymaster extends com.mysema.query.sql.RelationalPathBase<QHolidaymaster> {

    private static final long serialVersionUID = -2115334459;

    public static final QHolidaymaster holidaymaster = new QHolidaymaster("holidaymaster");

    public final NumberPath<Integer> brCode = createNumber("br_code", Integer.class);

    public final StringPath date = createString("date");

    public final StringPath day = createString("day");

    public final StringPath deDtTime = createString("de_dt_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath reason = createString("reason");

    public QHolidaymaster(String variable) {
        super(QHolidaymaster.class, forVariable(variable), "null", "holidaymaster");
    }

    @SuppressWarnings("all")
    public QHolidaymaster(Path<? extends QHolidaymaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "holidaymaster");
    }

    public QHolidaymaster(PathMetadata<?> metadata) {
        super(QHolidaymaster.class, metadata, "null", "holidaymaster");
    }

}

