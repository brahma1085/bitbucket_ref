package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSavingscatrate is a Querydsl query type for QSavingscatrate
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSavingscatrate extends com.mysema.query.sql.RelationalPathBase<QSavingscatrate> {

    private static final long serialVersionUID = 905303602;

    public static final QSavingscatrate savingscatrate = new QSavingscatrate("savingscatrate");

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> category = createNumber("category", Integer.class);

    public final StringPath dateFr = createString("date_fr");

    public final StringPath dateTo = createString("date_to");

    public final NumberPath<Integer> daysFr = createNumber("days_fr", Integer.class);

    public final NumberPath<Integer> daysTo = createNumber("days_to", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> extraIntRt = createNumber("extra_int_rt", Double.class);

    public QSavingscatrate(String variable) {
        super(QSavingscatrate.class, forVariable(variable), "null", "savingscatrate");
    }

    @SuppressWarnings("all")
    public QSavingscatrate(Path<? extends QSavingscatrate> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "savingscatrate");
    }

    public QSavingscatrate(PathMetadata<?> metadata) {
        super(QSavingscatrate.class, metadata, "null", "savingscatrate");
    }

}

