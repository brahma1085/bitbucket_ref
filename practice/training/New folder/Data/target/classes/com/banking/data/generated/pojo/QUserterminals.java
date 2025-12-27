package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QUserterminals is a Querydsl query type for QUserterminals
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QUserterminals extends com.mysema.query.sql.RelationalPathBase<QUserterminals> {

    private static final long serialVersionUID = -1281702889;

    public static final QUserterminals userterminals = new QUserterminals("userterminals");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath tmlCode = createString("tml_code");

    public final StringPath userId = createString("user_id");

    public QUserterminals(String variable) {
        super(QUserterminals.class, forVariable(variable), "null", "userterminals");
    }

    @SuppressWarnings("all")
    public QUserterminals(Path<? extends QUserterminals> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "userterminals");
    }

    public QUserterminals(PathMetadata<?> metadata) {
        super(QUserterminals.class, metadata, "null", "userterminals");
    }

}

