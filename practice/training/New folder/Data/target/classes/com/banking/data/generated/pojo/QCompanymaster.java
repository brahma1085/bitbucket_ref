package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCompanymaster is a Querydsl query type for QCompanymaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCompanymaster extends com.mysema.query.sql.RelationalPathBase<QCompanymaster> {

    private static final long serialVersionUID = -1511771350;

    public static final QCompanymaster companymaster = new QCompanymaster("companymaster");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> compCode = createNumber("comp_code", Integer.class);

    public final StringPath compName = createString("comp_name");

    public final StringPath deDtTime = createString("de_dt_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath veDtTime = createString("ve_dt_time");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QCompanymaster(String variable) {
        super(QCompanymaster.class, forVariable(variable), "null", "companymaster");
    }

    @SuppressWarnings("all")
    public QCompanymaster(Path<? extends QCompanymaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "companymaster");
    }

    public QCompanymaster(PathMetadata<?> metadata) {
        super(QCompanymaster.class, metadata, "null", "companymaster");
    }

}

