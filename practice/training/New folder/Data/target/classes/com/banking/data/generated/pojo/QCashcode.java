package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCashcode is a Querydsl query type for QCashcode
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCashcode extends com.mysema.query.sql.RelationalPathBase<QCashcode> {

    private static final long serialVersionUID = 2109644469;

    public static final QCashcode cashcode = new QCashcode("cashcode");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath mainAbbr = createString("main_abbr");

    public final StringPath moduleAbbr = createString("module_abbr");

    public final StringPath moduleCode = createString("module_code");

    public QCashcode(String variable) {
        super(QCashcode.class, forVariable(variable), "null", "cashcode");
    }

    @SuppressWarnings("all")
    public QCashcode(Path<? extends QCashcode> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "cashcode");
    }

    public QCashcode(PathMetadata<?> metadata) {
        super(QCashcode.class, metadata, "null", "cashcode");
    }

}

