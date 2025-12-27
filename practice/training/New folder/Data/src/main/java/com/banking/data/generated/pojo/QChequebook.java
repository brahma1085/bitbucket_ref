package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QChequebook is a Querydsl query type for QChequebook
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QChequebook extends com.mysema.query.sql.RelationalPathBase<QChequebook> {

    private static final long serialVersionUID = 1650592351;

    public static final QChequebook chequebook = new QChequebook("chequebook");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> bookNo = createNumber("book_no", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> fChqPrev = createNumber("f_chq_prev", Integer.class);

    public final NumberPath<Integer> fstChqNo = createNumber("fst_chq_no", Integer.class);

    public final NumberPath<Integer> lstChqNo = createNumber("lst_chq_no", Integer.class);

    public final NumberPath<Integer> noBounce = createNumber("no_bounce", Integer.class);

    public final NumberPath<Integer> noLeaf = createNumber("no_leaf", Integer.class);

    public final StringPath requestDt = createString("request_dt");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QChequebook(String variable) {
        super(QChequebook.class, forVariable(variable), "null", "chequebook");
    }

    @SuppressWarnings("all")
    public QChequebook(Path<? extends QChequebook> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "chequebook");
    }

    public QChequebook(PathMetadata<?> metadata) {
        super(QChequebook.class, metadata, "null", "chequebook");
    }

}

