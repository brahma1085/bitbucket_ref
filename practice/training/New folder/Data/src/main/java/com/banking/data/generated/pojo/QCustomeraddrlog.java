package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCustomeraddrlog is a Querydsl query type for QCustomeraddrlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCustomeraddrlog extends com.mysema.query.sql.RelationalPathBase<QCustomeraddrlog> {

    private static final long serialVersionUID = 1182844768;

    public static final QCustomeraddrlog customeraddrlog = new QCustomeraddrlog("customeraddrlog");

    public final NumberPath<Integer> addrType = createNumber("addr_type", Integer.class);

    public final StringPath address = createString("address");

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final StringPath city = createString("city");

    public final StringPath country = createString("country");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath email = createString("email");

    public final NumberPath<Integer> fax = createNumber("fax", Integer.class);

    public final NumberPath<Integer> faxstd = createNumber("faxstd", Integer.class);

    public final NumberPath<Integer> mobile = createNumber("mobile", Integer.class);

    public final NumberPath<Integer> phno = createNumber("phno", Integer.class);

    public final NumberPath<Integer> phstd = createNumber("phstd", Integer.class);

    public final NumberPath<Integer> pin = createNumber("pin", Integer.class);

    public final StringPath state = createString("state");

    public final StringPath udate = createString("udate");

    public QCustomeraddrlog(String variable) {
        super(QCustomeraddrlog.class, forVariable(variable), "null", "customeraddrlog");
    }

    @SuppressWarnings("all")
    public QCustomeraddrlog(Path<? extends QCustomeraddrlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "customeraddrlog");
    }

    public QCustomeraddrlog(PathMetadata<?> metadata) {
        super(QCustomeraddrlog.class, metadata, "null", "customeraddrlog");
    }

}

