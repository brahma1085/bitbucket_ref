package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDiscountcharges is a Querydsl query type for QDiscountcharges
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDiscountcharges extends com.mysema.query.sql.RelationalPathBase<QDiscountcharges> {

    private static final long serialVersionUID = -1609168087;

    public static final QDiscountcharges discountcharges = new QDiscountcharges("discountcharges");

    public final StringPath acType = createString("ac_type");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> frAmt = createNumber("fr_amt", Double.class);

    public final NumberPath<Double> intAmt = createNumber("int_amt", Double.class);

    public final NumberPath<Double> intRate = createNumber("int_rate", Double.class);

    public final StringPath intType = createString("int_type");

    public final NumberPath<Double> toAmt = createNumber("to_amt", Double.class);

    public QDiscountcharges(String variable) {
        super(QDiscountcharges.class, forVariable(variable), "null", "discountcharges");
    }

    @SuppressWarnings("all")
    public QDiscountcharges(Path<? extends QDiscountcharges> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "discountcharges");
    }

    public QDiscountcharges(PathMetadata<?> metadata) {
        super(QDiscountcharges.class, metadata, "null", "discountcharges");
    }

}

