package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QStockinspectiondetails is a Querydsl query type for QStockinspectiondetails
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QStockinspectiondetails extends com.mysema.query.sql.RelationalPathBase<QStockinspectiondetails> {

    private static final long serialVersionUID = 327725133;

    public static final QStockinspectiondetails stockinspectiondetails = new QStockinspectiondetails("stockinspectiondetails");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Double> creditLimit = createNumber("credit_limit", Double.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath inspDate = createString("insp_date");

    public final StringPath nextInspDate = createString("next_insp_date");

    public final NumberPath<Double> stockValue = createNumber("stock_value", Double.class);

    public QStockinspectiondetails(String variable) {
        super(QStockinspectiondetails.class, forVariable(variable), "null", "stockinspectiondetails");
    }

    @SuppressWarnings("all")
    public QStockinspectiondetails(Path<? extends QStockinspectiondetails> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "stockinspectiondetails");
    }

    public QStockinspectiondetails(PathMetadata<?> metadata) {
        super(QStockinspectiondetails.class, metadata, "null", "stockinspectiondetails");
    }

}

