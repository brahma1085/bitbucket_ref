package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAccountsubcategory is a Querydsl query type for QAccountsubcategory
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAccountsubcategory extends com.mysema.query.sql.RelationalPathBase<QAccountsubcategory> {

    private static final long serialVersionUID = -976440378;

    public static final QAccountsubcategory accountsubcategory = new QAccountsubcategory("accountsubcategory");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final NumberPath<Integer> catcode = createNumber("catcode", Integer.class);

    public final NumberPath<Integer> subcatcode = createNumber("subcatcode", Integer.class);

    public final StringPath subcatdesc = createString("subcatdesc");

    public QAccountsubcategory(String variable) {
        super(QAccountsubcategory.class, forVariable(variable), "null", "accountsubcategory");
    }

    @SuppressWarnings("all")
    public QAccountsubcategory(Path<? extends QAccountsubcategory> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "accountsubcategory");
    }

    public QAccountsubcategory(PathMetadata<?> metadata) {
        super(QAccountsubcategory.class, metadata, "null", "accountsubcategory");
    }

}

