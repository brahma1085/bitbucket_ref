package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPayordermake is a Querydsl query type for QPayordermake
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPayordermake extends com.mysema.query.sql.RelationalPathBase<QPayordermake> {

    private static final long serialVersionUID = 700413737;

    public static final QPayordermake payordermake = new QPayordermake("payordermake");

    public final NumberPath<Double> commAmt = createNumber("comm_amt", Double.class);

    public final StringPath custType = createString("cust_type");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> poAcno = createNumber("po_acno", Integer.class);

    public final StringPath poActy = createString("po_acty");

    public final NumberPath<Double> poAmt = createNumber("po_amt", Double.class);

    public final StringPath poDate = createString("po_date");

    public final StringPath poFavourName = createString("po_favour_name");

    public final NumberPath<Integer> poGlcd = createNumber("po_glcd", Integer.class);

    public final StringPath poGlname = createString("po_glname");

    public final StringPath poGltype = createString("po_gltype");

    public final StringPath poMade = createString("po_made");

    public final StringPath poPurchaserName = createString("po_purchaser_name");

    public final NumberPath<Integer> poSno = createNumber("po_sno", Integer.class);

    public final StringPath poType = createString("po_type");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QPayordermake(String variable) {
        super(QPayordermake.class, forVariable(variable), "null", "payordermake");
    }

    @SuppressWarnings("all")
    public QPayordermake(Path<? extends QPayordermake> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "payordermake");
    }

    public QPayordermake(PathMetadata<?> metadata) {
        super(QPayordermake.class, metadata, "null", "payordermake");
    }

}

