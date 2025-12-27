package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRbiform9transaction is a Querydsl query type for QRbiform9transaction
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QRbiform9transaction extends com.mysema.query.sql.RelationalPathBase<QRbiform9transaction> {

    private static final long serialVersionUID = -2021016627;

    public static final QRbiform9transaction rbiform9transaction = new QRbiform9transaction("rbiform9transaction");

    public final NumberPath<Double> closingBal = createNumber("closing_bal", Double.class);

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final DateTimePath<java.sql.Timestamp> deDtTime = createDateTime("de_dt_time", java.sql.Timestamp.class);

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> glCode = createNumber("gl_code", Integer.class);

    public final StringPath glType = createString("gl_type");

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final DatePath<java.sql.Date> trnDate = createDate("trn_date", java.sql.Date.class);

    public final StringPath trnSrc = createString("trn_src");

    public QRbiform9transaction(String variable) {
        super(QRbiform9transaction.class, forVariable(variable), "null", "rbiform9transaction");
    }

    @SuppressWarnings("all")
    public QRbiform9transaction(Path<? extends QRbiform9transaction> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "rbiform9transaction");
    }

    public QRbiform9transaction(PathMetadata<?> metadata) {
        super(QRbiform9transaction.class, metadata, "null", "rbiform9transaction");
    }

}

