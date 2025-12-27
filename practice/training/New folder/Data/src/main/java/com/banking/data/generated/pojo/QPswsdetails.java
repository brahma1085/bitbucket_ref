package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPswsdetails is a Querydsl query type for QPswsdetails
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QPswsdetails extends com.mysema.query.sql.RelationalPathBase<QPswsdetails> {

    private static final long serialVersionUID = -1293705714;

    public static final QPswsdetails pswsdetails = new QPswsdetails("pswsdetails");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Double> advancedAmt = createNumber("advanced_amt", Double.class);

    public final NumberPath<Double> amtOverdue = createNumber("amt_overdue", Double.class);

    public final NumberPath<Double> balOutstding = createNumber("bal_outstding", Double.class);

    public final StringPath category = createString("category");

    public final StringPath deDatetime = createString("de_datetime");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> prCode = createNumber("pr_code", Integer.class);

    public final StringPath processDt = createString("process_dt");

    public final NumberPath<Double> sancAmt = createNumber("sanc_amt", Double.class);

    public final StringPath sexCd = createString("sex_cd");

    public final StringPath wkSect = createString("wk_sect");

    public QPswsdetails(String variable) {
        super(QPswsdetails.class, forVariable(variable), "null", "pswsdetails");
    }

    @SuppressWarnings("all")
    public QPswsdetails(Path<? extends QPswsdetails> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "pswsdetails");
    }

    public QPswsdetails(PathMetadata<?> metadata) {
        super(QPswsdetails.class, metadata, "null", "pswsdetails");
    }

}

