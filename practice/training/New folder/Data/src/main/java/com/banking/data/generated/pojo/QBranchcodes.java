package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QBranchcodes is a Querydsl query type for QBranchcodes
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QBranchcodes extends com.mysema.query.sql.RelationalPathBase<QBranchcodes> {

    private static final long serialVersionUID = -1541405553;

    public static final QBranchcodes branchcodes = new QBranchcodes("branchcodes");

    public final StringPath bankCode = createString("bank_code");

    public final StringPath branchCode = createString("branch_code");

    public final StringPath branchName = createString("branch_name");

    public final StringPath cityCode = createString("city_code");

    public QBranchcodes(String variable) {
        super(QBranchcodes.class, forVariable(variable), "null", "branchcodes");
    }

    @SuppressWarnings("all")
    public QBranchcodes(Path<? extends QBranchcodes> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "branchcodes");
    }

    public QBranchcodes(PathMetadata<?> metadata) {
        super(QBranchcodes.class, metadata, "null", "branchcodes");
    }

}

