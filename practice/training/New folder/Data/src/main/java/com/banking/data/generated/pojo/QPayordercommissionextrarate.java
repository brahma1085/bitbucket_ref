package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPayordercommissionextrarate is a Querydsl query type for QPayordercommissionextrarate
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPayordercommissionextrarate extends com.mysema.query.sql.RelationalPathBase<QPayordercommissionextrarate> {

    private static final long serialVersionUID = -1441086006;

    public static final QPayordercommissionextrarate payordercommissionextrarate = new QPayordercommissionextrarate("payordercommissionextrarate");

    public final NumberPath<Integer> custSubType = createNumber("cust_sub_type", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> extraRate = createNumber("extra_rate", Double.class);

    public final NumberPath<Double> frAmt = createNumber("fr_amt", Double.class);

    public final StringPath frDate = createString("fr_date");

    public final StringPath poType = createString("po_type");

    public final NumberPath<Double> toAmt = createNumber("to_amt", Double.class);

    public final StringPath toDate = createString("to_date");

    public QPayordercommissionextrarate(String variable) {
        super(QPayordercommissionextrarate.class, forVariable(variable), "null", "payordercommissionextrarate");
    }

    @SuppressWarnings("all")
    public QPayordercommissionextrarate(Path<? extends QPayordercommissionextrarate> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "payordercommissionextrarate");
    }

    public QPayordercommissionextrarate(PathMetadata<?> metadata) {
        super(QPayordercommissionextrarate.class, metadata, "null", "payordercommissionextrarate");
    }

}

