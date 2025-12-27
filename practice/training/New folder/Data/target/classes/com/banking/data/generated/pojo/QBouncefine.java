package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QBouncefine is a Querydsl query type for QBouncefine
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QBouncefine extends com.mysema.query.sql.RelationalPathBase<QBouncefine> {

    private static final long serialVersionUID = 1017265687;

    public static final QBouncefine bouncefine = new QBouncefine("bouncefine");

    public final StringPath accType = createString("acc_type");

    public final NumberPath<Integer> bCode = createNumber("b_code", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Double> emailChg = createNumber("email_chg", Double.class);

    public final NumberPath<Double> fine = createNumber("fine", Double.class);

    public final NumberPath<Double> mailChg = createNumber("mail_chg", Double.class);

    public final NumberPath<Double> returnFine = createNumber("return_fine", Double.class);

    public final NumberPath<Double> smsChg = createNumber("sms_chg", Double.class);

    public QBouncefine(String variable) {
        super(QBouncefine.class, forVariable(variable), "null", "bouncefine");
    }

    @SuppressWarnings("all")
    public QBouncefine(Path<? extends QBouncefine> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "bouncefine");
    }

    public QBouncefine(PathMetadata<?> metadata) {
        super(QBouncefine.class, metadata, "null", "bouncefine");
    }

}

