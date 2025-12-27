package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPolink is a Querydsl query type for QPolink
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPolink extends com.mysema.query.sql.RelationalPathBase<QPolink> {

    private static final long serialVersionUID = 936816334;

    public static final QPolink polink = new QPolink("polink");

    public final StringPath payordDt = createString("payord_dt");

    public final NumberPath<Integer> payordNo = createNumber("payord_no", Integer.class);

    public final NumberPath<Integer> refSno = createNumber("ref_sno", Integer.class);

    public final StringPath refType = createString("ref_type");

    public QPolink(String variable) {
        super(QPolink.class, forVariable(variable), "null", "polink");
    }

    @SuppressWarnings("all")
    public QPolink(Path<? extends QPolink> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "polink");
    }

    public QPolink(PathMetadata<?> metadata) {
        super(QPolink.class, metadata, "null", "polink");
    }

}

