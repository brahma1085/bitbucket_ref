package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPayordercommission is a Querydsl query type for QPayordercommission
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPayordercommission extends com.mysema.query.sql.RelationalPathBase<QPayordercommission> {

    private static final long serialVersionUID = -562768410;

    public static final QPayordercommission payordercommission = new QPayordercommission("payordercommission");

    public final NumberPath<Double> commRate = createNumber("comm_rate", Double.class);

    public final StringPath commType = createString("comm_type");

    public final NumberPath<Integer> custSubType = createNumber("cust_sub_type", Integer.class);

    public final NumberPath<Integer> custType = createNumber("cust_type", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> extraRate = createNumber("extra_rate", Double.class);

    public final NumberPath<Double> frAmt = createNumber("fr_amt", Double.class);

    public final StringPath frDate = createString("fr_date");

    public final NumberPath<Double> minCommAmt = createNumber("min_comm_amt", Double.class);

    public final StringPath poType = createString("po_type");

    public final NumberPath<Double> toAmt = createNumber("to_amt", Double.class);

    public final StringPath toDate = createString("to_date");

    public QPayordercommission(String variable) {
        super(QPayordercommission.class, forVariable(variable), "null", "payordercommission");
    }

    @SuppressWarnings("all")
    public QPayordercommission(Path<? extends QPayordercommission> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "payordercommission");
    }

    public QPayordercommission(PathMetadata<?> metadata) {
        super(QPayordercommission.class, metadata, "null", "payordercommission");
    }

}

