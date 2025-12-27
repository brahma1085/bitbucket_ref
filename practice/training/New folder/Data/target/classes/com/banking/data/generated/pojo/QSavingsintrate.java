package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSavingsintrate is a Querydsl query type for QSavingsintrate
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSavingsintrate extends com.mysema.query.sql.RelationalPathBase<QSavingsintrate> {

    private static final long serialVersionUID = -1987429941;

    public static final QSavingsintrate savingsintrate = new QSavingsintrate("savingsintrate");

    public final StringPath acType = createString("ac_type");

    public final StringPath date = createString("date");

    public final DateTimePath<java.sql.Timestamp> deDate = createDateTime("de_date", java.sql.Timestamp.class);

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> intRate = createNumber("int_rate", Double.class);

    public QSavingsintrate(String variable) {
        super(QSavingsintrate.class, forVariable(variable), "null", "savingsintrate");
    }

    @SuppressWarnings("all")
    public QSavingsintrate(Path<? extends QSavingsintrate> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "savingsintrate");
    }

    public QSavingsintrate(PathMetadata<?> metadata) {
        super(QSavingsintrate.class, metadata, "null", "savingsintrate");
    }

}

