package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLoanhistory is a Querydsl query type for QLoanhistory
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLoanhistory extends com.mysema.query.sql.RelationalPathBase<QLoanhistory> {

    private static final long serialVersionUID = -1555392913;

    public static final QLoanhistory loanhistory = new QLoanhistory("loanhistory");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath actionParticulars = createString("action_particulars");

    public final NumberPath<Double> currentinstalment = createNumber("currentinstalment", Double.class);

    public final StringPath deDatetime = createString("de_datetime");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> defMnths = createNumber("def_mnths", Integer.class);

    public final NumberPath<Double> instalmentAmt = createNumber("instalment_amt", Double.class);

    public final NumberPath<Double> intOverdue = createNumber("int_overdue", Double.class);

    public final StringPath intUpto = createString("int_upto");

    public final NumberPath<Double> loanBal = createNumber("loan_bal", Double.class);

    public final StringPath lsttrnDate = createString("lsttrn_date");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> noOfInstalment = createNumber("no_of_instalment", Integer.class);

    public final NumberPath<Double> othCharges = createNumber("oth_charges", Double.class);

    public final NumberPath<Double> penalInt = createNumber("penal_int", Double.class);

    public final NumberPath<Double> prnOverdue = createNumber("prn_overdue", Double.class);

    public final NumberPath<Double> processingCharge = createNumber("processing_charge", Double.class);

    public final StringPath prtDate = createString("prt_date");

    public final StringPath prtInd = createString("prt_ind");

    public final NumberPath<Integer> refNo = createNumber("ref_no", Integer.class);

    public final NumberPath<Double> sancAmt = createNumber("sanc_amt", Double.class);

    public final StringPath sancDt = createString("sanc_dt");

    public final NumberPath<Integer> shNo = createNumber("sh_no", Integer.class);

    public final StringPath shTy = createString("sh_ty");

    public final NumberPath<Double> shareAmt = createNumber("share_amt", Double.class);

    public final NumberPath<Integer> stageNo = createNumber("stage_no", Integer.class);

    public QLoanhistory(String variable) {
        super(QLoanhistory.class, forVariable(variable), "null", "loanhistory");
    }

    @SuppressWarnings("all")
    public QLoanhistory(Path<? extends QLoanhistory> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "loanhistory");
    }

    public QLoanhistory(PathMetadata<?> metadata) {
        super(QLoanhistory.class, metadata, "null", "loanhistory");
    }

}

