package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTokennumbers is a Querydsl query type for QTokennumbers
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTokennumbers extends com.mysema.query.sql.RelationalPathBase<QTokennumbers> {

    private static final long serialVersionUID = -757920058;

    public static final QTokennumbers tokennumbers = new QTokennumbers("tokennumbers");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final DatePath<java.sql.Date> fromDate = createDate("from_date", java.sql.Date.class);

    public final DatePath<java.sql.Date> toDate = createDate("to_date", java.sql.Date.class);

    public final NumberPath<Integer> tokenNo = createNumber("token_no", Integer.class);

    public QTokennumbers(String variable) {
        super(QTokennumbers.class, forVariable(variable), "null", "tokennumbers");
    }

    @SuppressWarnings("all")
    public QTokennumbers(Path<? extends QTokennumbers> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "tokennumbers");
    }

    public QTokennumbers(PathMetadata<?> metadata) {
        super(QTokennumbers.class, metadata, "null", "tokennumbers");
    }

}

