package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QNomineemaster is a Querydsl query type for QNomineemaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QNomineemaster extends com.mysema.query.sql.RelationalPathBase<QNomineemaster> {

    private static final long serialVersionUID = -166342402;

    public static final QNomineemaster nomineemaster = new QNomineemaster("nomineemaster");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath address = createString("address");

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final StringPath courtOrderDate = createString("court_order_date");

    public final NumberPath<Integer> courtOrderNo = createNumber("court_order_no", Integer.class);

    public final StringPath dob = createString("dob");

    public final StringPath frDate = createString("fr_date");

    public final StringPath guardAddress = createString("guard_address");

    public final StringPath guardName = createString("guard_name");

    public final StringPath guardRel = createString("guard_rel");

    public final NumberPath<Integer> guardSex = createNumber("guard_sex", Integer.class);

    public final StringPath guardType = createString("guard_type");

    public final StringPath name = createString("name");

    public final NumberPath<Float> percentage = createNumber("percentage", Float.class);

    public final NumberPath<Integer> regNo = createNumber("reg_no", Integer.class);

    public final StringPath relation = createString("relation");

    public final NumberPath<Integer> sex = createNumber("sex", Integer.class);

    public final StringPath toDate = createString("to_date");

    public QNomineemaster(String variable) {
        super(QNomineemaster.class, forVariable(variable), "null", "nomineemaster");
    }

    @SuppressWarnings("all")
    public QNomineemaster(Path<? extends QNomineemaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "nomineemaster");
    }

    public QNomineemaster(PathMetadata<?> metadata) {
        super(QNomineemaster.class, metadata, "null", "nomineemaster");
    }

}

