package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QHead is a Querydsl query type for QHead
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QHead extends com.mysema.query.sql.RelationalPathBase<QHead> {

    private static final long serialVersionUID = 1618601845;

    public static final QHead head = new QHead("head");

    public final StringPath bankAbbr = createString("bank_abbr");

    public final NumberPath<Integer> bankcode = createNumber("bankcode", Integer.class);

    public final StringPath bankname = createString("bankname");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath location = createString("location");

    public QHead(String variable) {
        super(QHead.class, forVariable(variable), "null", "head");
    }

    @SuppressWarnings("all")
    public QHead(Path<? extends QHead> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "head");
    }

    public QHead(PathMetadata<?> metadata) {
        super(QHead.class, metadata, "null", "head");
    }

}

