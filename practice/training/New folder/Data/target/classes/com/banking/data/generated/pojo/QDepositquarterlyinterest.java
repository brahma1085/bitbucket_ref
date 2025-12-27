package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDepositquarterlyinterest is a Querydsl query type for QDepositquarterlyinterest
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDepositquarterlyinterest extends com.mysema.query.sql.RelationalPathBase<QDepositquarterlyinterest> {

    private static final long serialVersionUID = 732416410;

    public static final QDepositquarterlyinterest depositquarterlyinterest = new QDepositquarterlyinterest("depositquarterlyinterest");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath accdupto = createString("accdupto");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> depamt = createNumber("depamt", Double.class);

    public final NumberPath<Double> intamt = createNumber("intamt", Double.class);

    public final NumberPath<Double> intrate = createNumber("intrate", Double.class);

    public final NumberPath<Double> lstBalance = createNumber("lst_balance", Double.class);

    public final NumberPath<Double> matamt = createNumber("matamt", Double.class);

    public final StringPath name = createString("name");

    public final StringPath trnDate = createString("trn_date");

    public QDepositquarterlyinterest(String variable) {
        super(QDepositquarterlyinterest.class, forVariable(variable), "null", "depositquarterlyinterest");
    }

    @SuppressWarnings("all")
    public QDepositquarterlyinterest(Path<? extends QDepositquarterlyinterest> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "depositquarterlyinterest");
    }

    public QDepositquarterlyinterest(PathMetadata<?> metadata) {
        super(QDepositquarterlyinterest.class, metadata, "null", "depositquarterlyinterest");
    }

}

