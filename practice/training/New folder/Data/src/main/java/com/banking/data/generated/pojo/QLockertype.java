package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLockertype is a Querydsl query type for QLockertype
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLockertype extends com.mysema.query.sql.RelationalPathBase<QLockertype> {

    private static final long serialVersionUID = -551531481;

    public static final QLockertype lockertype = new QLockertype("lockertype");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath depth = createString("depth");

    public final StringPath descrptn = createString("descrptn");

    public final StringPath height = createString("height");

    public final StringPath length = createString("length");

    public final StringPath lockerType = createString("locker_type");

    public final com.mysema.query.sql.PrimaryKey<QLockertype> primary = createPrimaryKey(lockerType);

    public QLockertype(String variable) {
        super(QLockertype.class, forVariable(variable), "null", "lockertype");
    }

    @SuppressWarnings("all")
    public QLockertype(Path<? extends QLockertype> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "lockertype");
    }

    public QLockertype(PathMetadata<?> metadata) {
        super(QLockertype.class, metadata, "null", "lockertype");
    }

}

