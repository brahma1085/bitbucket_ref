package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAccountcode is a Querydsl query type for QAccountcode
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAccountcode extends com.mysema.query.sql.RelationalPathBase<QAccountcode> {

    private static final long serialVersionUID = 313474661;

    public static final QAccountcode accountcode = new QAccountcode("accountcode");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath moduleabbr = createString("moduleabbr");

    public final StringPath modulecode = createString("modulecode");

    public QAccountcode(String variable) {
        super(QAccountcode.class, forVariable(variable), "null", "accountcode");
    }

    @SuppressWarnings("all")
    public QAccountcode(Path<? extends QAccountcode> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "accountcode");
    }

    public QAccountcode(PathMetadata<?> metadata) {
        super(QAccountcode.class, metadata, "null", "accountcode");
    }

}

