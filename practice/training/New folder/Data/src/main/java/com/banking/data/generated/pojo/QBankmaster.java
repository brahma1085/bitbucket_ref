package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QBankmaster is a Querydsl query type for QBankmaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QBankmaster extends com.mysema.query.sql.RelationalPathBase<QBankmaster> {

    private static final long serialVersionUID = -1142462093;

    public static final QBankmaster bankmaster = new QBankmaster("bankmaster");

    public final StringPath bankAbbr = createString("bank_abbr");

    public final StringPath bankCode = createString("bank_code");

    public final StringPath bankName = createString("bank_name");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public QBankmaster(String variable) {
        super(QBankmaster.class, forVariable(variable), "null", "bankmaster");
    }

    @SuppressWarnings("all")
    public QBankmaster(Path<? extends QBankmaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "bankmaster");
    }

    public QBankmaster(PathMetadata<?> metadata) {
        super(QBankmaster.class, metadata, "null", "bankmaster");
    }

}

