package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSecuritydetails is a Querydsl query type for QSecuritydetails
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSecuritydetails extends com.mysema.query.sql.RelationalPathBase<QSecuritydetails> {

    private static final long serialVersionUID = -70231475;

    public static final QSecuritydetails securitydetails = new QSecuritydetails("securitydetails");

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final DatePath<java.sql.Date> fromdate = createDate("fromdate", java.sql.Date.class);

    public final StringPath modulecode = createString("modulecode");

    public final NumberPath<Double> percLnAvailed = createNumber("perc_ln_availed", Double.class);

    public final DatePath<java.sql.Date> todate = createDate("todate", java.sql.Date.class);

    public final StringPath typeOfSecurity = createString("type_of_security");

    public QSecuritydetails(String variable) {
        super(QSecuritydetails.class, forVariable(variable), "null", "securitydetails");
    }

    @SuppressWarnings("all")
    public QSecuritydetails(Path<? extends QSecuritydetails> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "securitydetails");
    }

    public QSecuritydetails(PathMetadata<?> metadata) {
        super(QSecuritydetails.class, metadata, "null", "securitydetails");
    }

}

