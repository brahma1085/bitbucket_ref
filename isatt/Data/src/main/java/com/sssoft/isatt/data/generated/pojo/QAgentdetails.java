package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAgentdetails is a Querydsl query type for QAgentdetails
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAgentdetails extends com.mysema.query.sql.RelationalPathBase<QAgentdetails> {

    private static final long serialVersionUID = -1136842279;

    public static final QAgentdetails agentdetails = new QAgentdetails("agentdetails");

    public final NumberPath<Integer> agentID = createNumber("AgentID", Integer.class);

    public final StringPath agentName = createString("AgentName");

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath ip = createString("IP");

    public final StringPath machineDetails = createString("MachineDetails");

    public final NumberPath<Integer> port = createNumber("Port", Integer.class);

    public final StringPath protocol = createString("Protocol");

    public final StringPath status = createString("Status");

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QAgentdetails> primary = createPrimaryKey(agentID);

    public final com.mysema.query.sql.ForeignKey<QSchedulerlanedetails> _schedulerLaneDetailsAgentDetailsFK = createInvForeignKey(agentID, "AgentID");

    public final com.mysema.query.sql.ForeignKey<QScheduler> _schedulerAgentDetailsFK = createInvForeignKey(agentID, "AgentID");

    public final com.mysema.query.sql.ForeignKey<QLaneresults> _tFWLaneResultsAgentDetailsFK = createInvForeignKey(agentID, "AgentID");

    public QAgentdetails(String variable) {
        super(QAgentdetails.class, forVariable(variable), "null", "agentdetails");
    }

    @SuppressWarnings("all")
    public QAgentdetails(Path<? extends QAgentdetails> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "agentdetails");
    }

    public QAgentdetails(PathMetadata<?> metadata) {
        super(QAgentdetails.class, metadata, "null", "agentdetails");
    }

}

