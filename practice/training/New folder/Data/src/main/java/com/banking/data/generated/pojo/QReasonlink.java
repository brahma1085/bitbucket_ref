package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QReasonlink is a Querydsl query type for QReasonlink
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QReasonlink extends com.mysema.query.sql.RelationalPathBase<QReasonlink> {

    private static final long serialVersionUID = -1234151917;

    public static final QReasonlink reasonlink = new QReasonlink("reasonlink");

    public final StringPath deDtTime = createString("de_dt_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> linkCode = createNumber("link_code", Integer.class);

    public final StringPath linkDesc = createString("link_desc");

    public final NumberPath<Integer> rCode = createNumber("r_code", Integer.class);

    public QReasonlink(String variable) {
        super(QReasonlink.class, forVariable(variable), "null", "reasonlink");
    }

    @SuppressWarnings("all")
    public QReasonlink(Path<? extends QReasonlink> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "reasonlink");
    }

    public QReasonlink(PathMetadata<?> metadata) {
        super(QReasonlink.class, metadata, "null", "reasonlink");
    }

}

