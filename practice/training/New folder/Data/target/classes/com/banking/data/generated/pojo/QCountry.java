package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCountry is a Querydsl query type for QCountry
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCountry extends com.mysema.query.sql.RelationalPathBase<QCountry> {

    private static final long serialVersionUID = 332356065;

    public static final QCountry country1 = new QCountry("country");

    public final StringPath code = createString("code");

    public final StringPath country = createString("country");

    public QCountry(String variable) {
        super(QCountry.class, forVariable(variable), "null", "country");
    }

    @SuppressWarnings("all")
    public QCountry(Path<? extends QCountry> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "country");
    }

    public QCountry(PathMetadata<?> metadata) {
        super(QCountry.class, metadata, "null", "country");
    }

}

