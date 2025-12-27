package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAgentchange is a Querydsl query type for QAgentchange
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAgentchange extends com.mysema.query.sql.RelationalPathBase<QAgentchange> {

    private static final long serialVersionUID = 1546068928;

    public static final QAgentchange agentchange = new QAgentchange("agentchange");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> frmAgtNo = createNumber("frm_agt_no", Integer.class);

    public final StringPath frmAgtType = createString("frm_agt_type");

    public final StringPath pdModulecode = createString("pd_modulecode");

    public final NumberPath<Integer> refInd = createNumber("ref_ind", Integer.class);

    public final NumberPath<Integer> toAgtNo = createNumber("to_agt_no", Integer.class);

    public final StringPath toAgtType = createString("to_agt_type");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QAgentchange(String variable) {
        super(QAgentchange.class, forVariable(variable), "null", "agentchange");
    }

    @SuppressWarnings("all")
    public QAgentchange(Path<? extends QAgentchange> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "agentchange");
    }

    public QAgentchange(PathMetadata<?> metadata) {
        super(QAgentchange.class, metadata, "null", "agentchange");
    }

}

