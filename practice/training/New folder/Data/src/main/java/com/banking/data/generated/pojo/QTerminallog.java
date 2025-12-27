package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTerminallog is a Querydsl query type for QTerminallog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTerminallog extends com.mysema.query.sql.RelationalPathBase<QTerminallog> {

    private static final long serialVersionUID = 1823020051;

    public static final QTerminallog terminallog = new QTerminallog("terminallog");

    public final StringPath deDatetime = createString("de_datetime");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath tmlCode = createString("tml_code");

    public final NumberPath<Integer> userNo = createNumber("user_no", Integer.class);

    public QTerminallog(String variable) {
        super(QTerminallog.class, forVariable(variable), "null", "terminallog");
    }

    @SuppressWarnings("all")
    public QTerminallog(Path<? extends QTerminallog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "terminallog");
    }

    public QTerminallog(PathMetadata<?> metadata) {
        super(QTerminallog.class, metadata, "null", "terminallog");
    }

}

