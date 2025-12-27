package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTerminalipaddr is a Querydsl query type for QTerminalipaddr
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTerminalipaddr extends com.mysema.query.sql.RelationalPathBase<QTerminalipaddr> {

    private static final long serialVersionUID = -356161943;

    public static final QTerminalipaddr terminalipaddr = new QTerminalipaddr("terminalipaddr");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath ipAddress = createString("ip_address");

    public final StringPath tmlNo = createString("tml_no");

    public QTerminalipaddr(String variable) {
        super(QTerminalipaddr.class, forVariable(variable), "null", "terminalipaddr");
    }

    @SuppressWarnings("all")
    public QTerminalipaddr(Path<? extends QTerminalipaddr> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "terminalipaddr");
    }

    public QTerminalipaddr(PathMetadata<?> metadata) {
        super(QTerminalipaddr.class, metadata, "null", "terminalipaddr");
    }

}

