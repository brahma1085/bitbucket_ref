package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QBranchDetails is a Querydsl query type for QBranchDetails
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QBranchDetails extends com.mysema.query.sql.RelationalPathBase<QBranchDetails> {

    private static final long serialVersionUID = -1541470133;

    public static final QBranchDetails branchDetails = new QBranchDetails("branch_details");

    public final StringPath branchAddr = createString("Branch_Addr");

    public final StringPath branchCode = createString("Branch_Code");

    public final StringPath branchName = createString("Branch_Name");

    public QBranchDetails(String variable) {
        super(QBranchDetails.class, forVariable(variable), "null", "branch_details");
    }

    @SuppressWarnings("all")
    public QBranchDetails(Path<? extends QBranchDetails> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "branch_details");
    }

    public QBranchDetails(PathMetadata<?> metadata) {
        super(QBranchDetails.class, metadata, "null", "branch_details");
    }

}

