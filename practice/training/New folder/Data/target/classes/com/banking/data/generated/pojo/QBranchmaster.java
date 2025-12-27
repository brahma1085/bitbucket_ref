package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QBranchmaster is a Querydsl query type for QBranchmaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QBranchmaster extends com.mysema.query.sql.RelationalPathBase<QBranchmaster> {

    private static final long serialVersionUID = -265108711;

    public static final QBranchmaster branchmaster = new QBranchmaster("branchmaster");

    public final StringPath address = createString("address");

    public final NumberPath<Integer> brAcNo = createNumber("br_ac_no", Integer.class);

    public final StringPath brAcType = createString("br_ac_type");

    public final NumberPath<Integer> brCode = createNumber("br_code", Integer.class);

    public final StringPath brComp = createString("br_comp");

    public final StringPath brName = createString("br_name");

    public final StringPath brShnm = createString("br_shnm");

    public final StringPath brType = createString("br_type");

    public final StringPath computerised = createString("computerised");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> glCode = createNumber("gl_code", Integer.class);

    public final StringPath glType = createString("gl_type");

    public QBranchmaster(String variable) {
        super(QBranchmaster.class, forVariable(variable), "null", "branchmaster");
    }

    @SuppressWarnings("all")
    public QBranchmaster(Path<? extends QBranchmaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "branchmaster");
    }

    public QBranchmaster(PathMetadata<?> metadata) {
        super(QBranchmaster.class, metadata, "null", "branchmaster");
    }

}

