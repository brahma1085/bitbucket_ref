package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QQtrdefinition is a Querydsl query type for QQtrdefinition
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QQtrdefinition extends com.mysema.query.sql.RelationalPathBase<QQtrdefinition> {

    private static final long serialVersionUID = 307723085;

    public static final QQtrdefinition qtrdefinition = new QQtrdefinition("qtrdefinition");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath hyrDefn = createString("hyr_defn");

    public final NumberPath<Integer> month = createNumber("month", Integer.class);

    public final StringPath qtrDefn = createString("qtr_defn");

    public final StringPath yrDefn = createString("yr_defn");

    public QQtrdefinition(String variable) {
        super(QQtrdefinition.class, forVariable(variable), "null", "qtrdefinition");
    }

    @SuppressWarnings("all")
    public QQtrdefinition(Path<? extends QQtrdefinition> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "qtrdefinition");
    }

    public QQtrdefinition(PathMetadata<?> metadata) {
        super(QQtrdefinition.class, metadata, "null", "qtrdefinition");
    }

}

