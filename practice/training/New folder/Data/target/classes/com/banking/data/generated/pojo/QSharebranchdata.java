package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSharebranchdata is a Querydsl query type for QSharebranchdata
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSharebranchdata extends com.mysema.query.sql.RelationalPathBase<QSharebranchdata> {

    private static final long serialVersionUID = -1762180938;

    public static final QSharebranchdata sharebranchdata = new QSharebranchdata("sharebranchdata");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath madePermanent = createString("made_permanent");

    public final NumberPath<Integer> tempNo = createNumber("temp_no", Integer.class);

    public QSharebranchdata(String variable) {
        super(QSharebranchdata.class, forVariable(variable), "null", "sharebranchdata");
    }

    @SuppressWarnings("all")
    public QSharebranchdata(Path<? extends QSharebranchdata> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "sharebranchdata");
    }

    public QSharebranchdata(PathMetadata<?> metadata) {
        super(QSharebranchdata.class, metadata, "null", "sharebranchdata");
    }

}

