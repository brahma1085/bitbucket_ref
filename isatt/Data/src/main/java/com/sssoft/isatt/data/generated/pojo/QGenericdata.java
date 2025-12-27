package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QGenericdata is a Querydsl query type for QGenericdata
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QGenericdata extends com.mysema.query.sql.RelationalPathBase<QGenericdata> {

    private static final long serialVersionUID = 323224645;

    public static final QGenericdata genericdata = new QGenericdata("genericdata");

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final NumberPath<Integer> id = createNumber("ID", Integer.class);

    public final StringPath keyName = createString("KeyName");

    public final StringPath value = createString("Value");

    public final com.mysema.query.sql.PrimaryKey<QGenericdata> primary = createPrimaryKey(id);

    public final com.mysema.query.sql.ForeignKey<QApplication> genericDataApplicationFK = createForeignKey(appID, "AppID");

    public QGenericdata(String variable) {
        super(QGenericdata.class, forVariable(variable), "null", "genericdata");
    }

    @SuppressWarnings("all")
    public QGenericdata(Path<? extends QGenericdata> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "genericdata");
    }

    public QGenericdata(PathMetadata<?> metadata) {
        super(QGenericdata.class, metadata, "null", "genericdata");
    }

}

