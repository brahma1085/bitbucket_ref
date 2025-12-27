package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCurrencyStock is a Querydsl query type for QCurrencyStock
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCurrencyStock extends com.mysema.query.sql.RelationalPathBase<QCurrencyStock> {

    private static final long serialVersionUID = 2115393424;

    public static final QCurrencyStock currencyStock = new QCurrencyStock("currency_stock");

    public final StringPath curDate = createString("cur_date");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> netamt = createNumber("netamt", Double.class);

    public final StringPath recType = createString("rec_type");

    public final NumberPath<Integer> s1 = createNumber("s1", Integer.class);

    public final NumberPath<Integer> s10 = createNumber("s10", Integer.class);

    public final NumberPath<Integer> s100 = createNumber("s100", Integer.class);

    public final NumberPath<Integer> s1000 = createNumber("s1000", Integer.class);

    public final NumberPath<Integer> s2 = createNumber("s2", Integer.class);

    public final NumberPath<Integer> s20 = createNumber("s20", Integer.class);

    public final NumberPath<Integer> s5 = createNumber("s5", Integer.class);

    public final NumberPath<Integer> s50 = createNumber("s50", Integer.class);

    public final NumberPath<Integer> s500 = createNumber("s500", Integer.class);

    public final NumberPath<Double> scoins = createNumber("scoins", Double.class);

    public final StringPath tmlNo = createString("tml_no");

    public QCurrencyStock(String variable) {
        super(QCurrencyStock.class, forVariable(variable), "null", "currency_stock");
    }

    @SuppressWarnings("all")
    public QCurrencyStock(Path<? extends QCurrencyStock> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "currency_stock");
    }

    public QCurrencyStock(PathMetadata<?> metadata) {
        super(QCurrencyStock.class, metadata, "null", "currency_stock");
    }

}

