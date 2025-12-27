package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QQtrdefinitionlog is a Querydsl query type for QQtrdefinitionlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QQtrdefinitionlog extends com.mysema.query.sql.RelationalPathBase<QQtrdefinitionlog> {

    private static final long serialVersionUID = 1918322903;

    public static final QQtrdefinitionlog qtrdefinitionlog = new QQtrdefinitionlog("qtrdefinitionlog");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath hyrDefn = createString("hyr_defn");

    public final NumberPath<Integer> month = createNumber("month", Integer.class);

    public final StringPath qtrDefn = createString("qtr_defn");

    public final StringPath yrDefn = createString("yr_defn");

    public QQtrdefinitionlog(String variable) {
        super(QQtrdefinitionlog.class, forVariable(variable), "null", "qtrdefinitionlog");
    }

    @SuppressWarnings("all")
    public QQtrdefinitionlog(Path<? extends QQtrdefinitionlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "qtrdefinitionlog");
    }

    public QQtrdefinitionlog(PathMetadata<?> metadata) {
        super(QQtrdefinitionlog.class, metadata, "null", "qtrdefinitionlog");
    }

}

