package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QStdinstprm is a Querydsl query type for QStdinstprm
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QStdinstprm extends com.mysema.query.sql.RelationalPathBase<QStdinstprm> {

    private static final long serialVersionUID = 2146883831;

    public static final QStdinstprm stdinstprm = new QStdinstprm("stdinstprm");

    public final StringPath deDtTime = createString("de_dt_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath frType = createString("fr_type");

    public final NumberPath<Integer> priorityNo = createNumber("priority_no", Integer.class);

    public final StringPath toType = createString("to_type");

    public QStdinstprm(String variable) {
        super(QStdinstprm.class, forVariable(variable), "null", "stdinstprm");
    }

    @SuppressWarnings("all")
    public QStdinstprm(Path<? extends QStdinstprm> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "stdinstprm");
    }

    public QStdinstprm(PathMetadata<?> metadata) {
        super(QStdinstprm.class, metadata, "null", "stdinstprm");
    }

}

