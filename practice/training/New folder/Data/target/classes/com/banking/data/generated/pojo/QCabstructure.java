package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCabstructure is a Querydsl query type for QCabstructure
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCabstructure extends com.mysema.query.sql.RelationalPathBase<QCabstructure> {

    private static final long serialVersionUID = -217456956;

    public static final QCabstructure cabstructure = new QCabstructure("cabstructure");

    public final NumberPath<Integer> cabNo = createNumber("cab_no", Integer.class);

    public final NumberPath<Integer> noOfCols = createNumber("no_of_cols", Integer.class);

    public final NumberPath<Integer> rowNo = createNumber("row_no", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QCabstructure> primary = createPrimaryKey(cabNo, rowNo);

    public QCabstructure(String variable) {
        super(QCabstructure.class, forVariable(variable), "null", "cabstructure");
    }

    @SuppressWarnings("all")
    public QCabstructure(Path<? extends QCabstructure> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "cabstructure");
    }

    public QCabstructure(PathMetadata<?> metadata) {
        super(QCabstructure.class, metadata, "null", "cabstructure");
    }

}

