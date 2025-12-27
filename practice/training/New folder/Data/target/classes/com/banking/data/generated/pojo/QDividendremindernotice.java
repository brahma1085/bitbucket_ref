package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDividendremindernotice is a Querydsl query type for QDividendremindernotice
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDividendremindernotice extends com.mysema.query.sql.RelationalPathBase<QDividendremindernotice> {

    private static final long serialVersionUID = 1177336270;

    public static final QDividendremindernotice dividendremindernotice = new QDividendremindernotice("dividendremindernotice");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> divAmt = createNumber("div_amt", Double.class);

    public final StringPath divDt = createString("div_dt");

    public final NumberPath<Integer> noticeNo = createNumber("notice_no", Integer.class);

    public final NumberPath<Integer> templNo = createNumber("templ_no", Integer.class);

    public QDividendremindernotice(String variable) {
        super(QDividendremindernotice.class, forVariable(variable), "null", "dividendremindernotice");
    }

    @SuppressWarnings("all")
    public QDividendremindernotice(Path<? extends QDividendremindernotice> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "dividendremindernotice");
    }

    public QDividendremindernotice(PathMetadata<?> metadata) {
        super(QDividendremindernotice.class, metadata, "null", "dividendremindernotice");
    }

}

