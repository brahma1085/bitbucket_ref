package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QStagemaster is a Querydsl query type for QStagemaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QStagemaster extends com.mysema.query.sql.RelationalPathBase<QStagemaster> {

    private static final long serialVersionUID = 183835563;

    public static final QStagemaster stagemaster = new QStagemaster("stagemaster");

    public final StringPath acType = createString("ac_type");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath descptn = createString("descptn");

    public final NumberPath<Integer> noOfAcs = createNumber("no_of_acs", Integer.class);

    public final NumberPath<Integer> prdFrom = createNumber("prd_from", Integer.class);

    public final NumberPath<Integer> prdTo = createNumber("prd_to", Integer.class);

    public final StringPath reversbl = createString("reversbl");

    public final StringPath splrt = createString("splrt");

    public final NumberPath<Integer> stageCd = createNumber("stage_cd", Integer.class);

    public final StringPath stageTy = createString("stage_ty");

    public final StringPath toHist = createString("to_hist");

    public final NumberPath<Double> totAmt = createNumber("tot_amt", Double.class);

    public QStagemaster(String variable) {
        super(QStagemaster.class, forVariable(variable), "null", "stagemaster");
    }

    @SuppressWarnings("all")
    public QStagemaster(Path<? extends QStagemaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "stagemaster");
    }

    public QStagemaster(PathMetadata<?> metadata) {
        super(QStagemaster.class, metadata, "null", "stagemaster");
    }

}

