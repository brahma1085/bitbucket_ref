package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLoancategoryrate is a Querydsl query type for QLoancategoryrate
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLoancategoryrate extends com.mysema.query.sql.RelationalPathBase<QLoancategoryrate> {

    private static final long serialVersionUID = 589732323;

    public static final QLoancategoryrate loancategoryrate = new QLoancategoryrate("loancategoryrate");

    public final NumberPath<Integer> category = createNumber("category", Integer.class);

    public final StringPath dateFr = createString("date_fr");

    public final StringPath dateTo = createString("date_to");

    public final StringPath deDtTime = createString("de_dt_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath lnType = createString("ln_type");

    public final NumberPath<Double> rate = createNumber("rate", Double.class);

    public QLoancategoryrate(String variable) {
        super(QLoancategoryrate.class, forVariable(variable), "null", "loancategoryrate");
    }

    @SuppressWarnings("all")
    public QLoancategoryrate(Path<? extends QLoancategoryrate> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "loancategoryrate");
    }

    public QLoancategoryrate(PathMetadata<?> metadata) {
        super(QLoancategoryrate.class, metadata, "null", "loancategoryrate");
    }

}

