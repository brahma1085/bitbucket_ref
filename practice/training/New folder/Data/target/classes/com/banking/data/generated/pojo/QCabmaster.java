package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCabmaster is a Querydsl query type for QCabmaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCabmaster extends com.mysema.query.sql.RelationalPathBase<QCabmaster> {

    private static final long serialVersionUID = -1086758223;

    public static final QCabmaster cabmaster = new QCabmaster("cabmaster");

    public final NumberPath<Integer> cabNo = createNumber("cab_no", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath descrptn = createString("descrptn");

    public final StringPath masterKey = createString("master_key");

    public final NumberPath<Integer> noOfLkrs = createNumber("no_of_lkrs", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QCabmaster> primary = createPrimaryKey(cabNo);

    public QCabmaster(String variable) {
        super(QCabmaster.class, forVariable(variable), "null", "cabmaster");
    }

    @SuppressWarnings("all")
    public QCabmaster(Path<? extends QCabmaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "cabmaster");
    }

    public QCabmaster(PathMetadata<?> metadata) {
        super(QCabmaster.class, metadata, "null", "cabmaster");
    }

}

