package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRbitran1 is a Querydsl query type for QRbitran1
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QRbitran1 extends com.mysema.query.sql.RelationalPathBase<QRbitran1> {

    private static final long serialVersionUID = -1200243486;

    public static final QRbitran1 rbitran1 = new QRbitran1("rbitran1");

    public final NumberPath<Double> balance = createNumber("balance", Double.class);

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final NumberPath<Integer> glCode = createNumber("gl_code", Integer.class);

    public final StringPath glType = createString("gl_type");

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final StringPath trnSrc = createString("trn_src");

    public QRbitran1(String variable) {
        super(QRbitran1.class, forVariable(variable), "null", "rbitran1");
    }

    @SuppressWarnings("all")
    public QRbitran1(Path<? extends QRbitran1> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "rbitran1");
    }

    public QRbitran1(PathMetadata<?> metadata) {
        super(QRbitran1.class, metadata, "null", "rbitran1");
    }

}

