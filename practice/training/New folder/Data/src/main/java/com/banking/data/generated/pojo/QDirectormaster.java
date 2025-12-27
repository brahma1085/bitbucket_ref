package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDirectormaster is a Querydsl query type for QDirectormaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDirectormaster extends com.mysema.query.sql.RelationalPathBase<QDirectormaster> {

    private static final long serialVersionUID = -1854961597;

    public static final QDirectormaster directormaster = new QDirectormaster("directormaster");

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final NumberPath<Integer> directorCode = createNumber("director_code", Integer.class);

    public final StringPath fromDate = createString("from_date");

    public final NumberPath<Integer> srNo = createNumber("sr_no", Integer.class);

    public final StringPath toDate = createString("to_date");

    public final com.mysema.query.sql.PrimaryKey<QDirectormaster> primary = createPrimaryKey(srNo);

    public QDirectormaster(String variable) {
        super(QDirectormaster.class, forVariable(variable), "null", "directormaster");
    }

    @SuppressWarnings("all")
    public QDirectormaster(Path<? extends QDirectormaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "directormaster");
    }

    public QDirectormaster(PathMetadata<?> metadata) {
        super(QDirectormaster.class, metadata, "null", "directormaster");
    }

}

