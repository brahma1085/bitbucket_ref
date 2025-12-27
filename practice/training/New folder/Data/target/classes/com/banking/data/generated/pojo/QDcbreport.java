package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDcbreport is a Querydsl query type for QDcbreport
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDcbreport extends com.mysema.query.sql.RelationalPathBase<QDcbreport> {

    private static final long serialVersionUID = 738154050;

    public static final QDcbreport dcbreport = new QDcbreport("dcbreport");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Double> advColl = createNumber("adv_coll", Double.class);

    public final NumberPath<Double> advPaid = createNumber("adv_paid", Double.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> intArr = createNumber("int_arr", Double.class);

    public final NumberPath<Double> intColl = createNumber("int_coll", Double.class);

    public final NumberPath<Double> intDmd = createNumber("int_dmd", Double.class);

    public final NumberPath<Double> lnBal = createNumber("ln_bal", Double.class);

    public final StringPath month = createString("month");

    public final NumberPath<Double> ochgArr = createNumber("ochg_arr", Double.class);

    public final NumberPath<Double> ochgColl = createNumber("ochg_coll", Double.class);

    public final NumberPath<Double> ochgDmd = createNumber("ochg_dmd", Double.class);

    public final NumberPath<Double> pintArr = createNumber("pint_arr", Double.class);

    public final NumberPath<Double> pintColl = createNumber("pint_coll", Double.class);

    public final NumberPath<Double> prArr = createNumber("pr_arr", Double.class);

    public final NumberPath<Double> prColl = createNumber("pr_coll", Double.class);

    public final NumberPath<Double> prDmd = createNumber("pr_dmd", Double.class);

    public QDcbreport(String variable) {
        super(QDcbreport.class, forVariable(variable), "null", "dcbreport");
    }

    @SuppressWarnings("all")
    public QDcbreport(Path<? extends QDcbreport> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "dcbreport");
    }

    public QDcbreport(PathMetadata<?> metadata) {
        super(QDcbreport.class, metadata, "null", "dcbreport");
    }

}

