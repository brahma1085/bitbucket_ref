package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLoansplinterest is a Querydsl query type for QLoansplinterest
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLoansplinterest extends com.mysema.query.sql.RelationalPathBase<QLoansplinterest> {

    private static final long serialVersionUID = -1827368396;

    public static final QLoansplinterest loansplinterest = new QLoansplinterest("loansplinterest");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath fromDate = createString("from_date");

    public final NumberPath<Double> splIntRate = createNumber("spl_int_rate", Double.class);

    public final StringPath toDate = createString("to_date");

    public QLoansplinterest(String variable) {
        super(QLoansplinterest.class, forVariable(variable), "null", "loansplinterest");
    }

    @SuppressWarnings("all")
    public QLoansplinterest(Path<? extends QLoansplinterest> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "loansplinterest");
    }

    public QLoansplinterest(PathMetadata<?> metadata) {
        super(QLoansplinterest.class, metadata, "null", "loansplinterest");
    }

}

