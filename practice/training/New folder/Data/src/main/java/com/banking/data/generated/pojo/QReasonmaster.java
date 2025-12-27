package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QReasonmaster is a Querydsl query type for QReasonmaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QReasonmaster extends com.mysema.query.sql.RelationalPathBase<QReasonmaster> {

    private static final long serialVersionUID = -587616709;

    public static final QReasonmaster reasonmaster = new QReasonmaster("reasonmaster");

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath reason = createString("reason");

    public QReasonmaster(String variable) {
        super(QReasonmaster.class, forVariable(variable), "null", "reasonmaster");
    }

    @SuppressWarnings("all")
    public QReasonmaster(Path<? extends QReasonmaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "reasonmaster");
    }

    public QReasonmaster(PathMetadata<?> metadata) {
        super(QReasonmaster.class, metadata, "null", "reasonmaster");
    }

}

