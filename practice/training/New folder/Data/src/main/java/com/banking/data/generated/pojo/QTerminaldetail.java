package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTerminaldetail is a Querydsl query type for QTerminaldetail
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTerminaldetail extends com.mysema.query.sql.RelationalPathBase<QTerminaldetail> {

    private static final long serialVersionUID = -508903134;

    public static final QTerminaldetail terminaldetail = new QTerminaldetail("terminaldetail");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath ipAddress = createString("ip_address");

    public final NumberPath<Double> maxTrnAmt = createNumber("max_trn_amt", Double.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath tmlCode = createString("tml_code");

    public final StringPath tmlDesc = createString("tml_desc");

    public final StringPath tmlType = createString("tml_type");

    public QTerminaldetail(String variable) {
        super(QTerminaldetail.class, forVariable(variable), "null", "terminaldetail");
    }

    @SuppressWarnings("all")
    public QTerminaldetail(Path<? extends QTerminaldetail> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "terminaldetail");
    }

    public QTerminaldetail(PathMetadata<?> metadata) {
        super(QTerminaldetail.class, metadata, "null", "terminaldetail");
    }

}

