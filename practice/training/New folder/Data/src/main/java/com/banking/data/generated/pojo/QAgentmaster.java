package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAgentmaster is a Querydsl query type for QAgentmaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAgentmaster extends com.mysema.query.sql.RelationalPathBase<QAgentmaster> {

    private static final long serialVersionUID = 1826437746;

    public static final QAgentmaster agentmaster = new QAgentmaster("agentmaster");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath apptDate = createString("appt_date");

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final StringPath closeDate = createString("close_date");

    public final NumberPath<Integer> closeInd = createNumber("close_ind", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> intrAcNo = createNumber("intr_ac_no", Integer.class);

    public final StringPath intrAcType = createString("intr_ac_type");

    public final NumberPath<Integer> jtAcNo = createNumber("jt_ac_no", Integer.class);

    public final StringPath jtAcType = createString("jt_ac_type");

    public final NumberPath<Integer> noJtHldr = createNumber("no_jt_hldr", Integer.class);

    public final NumberPath<Integer> pAcNo = createNumber("p_ac_no", Integer.class);

    public final StringPath pAcType = createString("p_ac_type");

    public final NumberPath<Integer> refInd = createNumber("ref_ind", Integer.class);

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public final com.mysema.query.sql.PrimaryKey<QAgentmaster> primary = createPrimaryKey(acNo, acType);

    public QAgentmaster(String variable) {
        super(QAgentmaster.class, forVariable(variable), "null", "agentmaster");
    }

    @SuppressWarnings("all")
    public QAgentmaster(Path<? extends QAgentmaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "agentmaster");
    }

    public QAgentmaster(PathMetadata<?> metadata) {
        super(QAgentmaster.class, metadata, "null", "agentmaster");
    }

}

