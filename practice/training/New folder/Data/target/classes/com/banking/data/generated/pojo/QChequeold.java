package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QChequeold is a Querydsl query type for QChequeold
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QChequeold extends com.mysema.query.sql.RelationalPathBase<QChequeold> {

    private static final long serialVersionUID = 1023088625;

    public static final QChequeold chequeold = new QChequeold("chequeold");

    public final NumberPath<Integer> ackNo = createNumber("ack_no", Integer.class);

    public final StringPath bankCd = createString("bank_cd");

    public final StringPath brName = createString("br_name");

    public final StringPath chqddpo = createString("chqddpo");

    public final StringPath clgDate = createString("clg_date");

    public final NumberPath<Integer> clgNo = createNumber("clg_no", Integer.class);

    public final StringPath clgType = createString("clg_type");

    public final StringPath compName = createString("comp_name");

    public final NumberPath<Integer> crAcNo = createNumber("cr_ac_no", Integer.class);

    public final StringPath crAcType = createString("cr_ac_type");

    public final NumberPath<Integer> ctrlNo = createNumber("ctrl_no", Integer.class);

    public final StringPath deDtTime = createString("de_dt_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath despInd = createString("desp_ind");

    public final NumberPath<Double> discAmt = createNumber("disc_amt", Double.class);

    public final NumberPath<Double> discChg = createNumber("disc_chg", Double.class);

    public final StringPath discInd = createString("disc_ind");

    public final StringPath docBs = createString("doc_bs");

    public final NumberPath<Integer> docDest = createNumber("doc_dest", Integer.class);

    public final NumberPath<Integer> docSou = createNumber("doc_sou", Integer.class);

    public final NumberPath<Double> docTot = createNumber("doc_tot", Double.class);

    public final NumberPath<Integer> drAcNo = createNumber("dr_ac_no", Integer.class);

    public final StringPath drAcType = createString("dr_ac_type");

    public final StringPath expClgdt = createString("exp_clgdt");

    public final NumberPath<Integer> expClgno = createNumber("exp_clgno", Integer.class);

    public final NumberPath<Double> fineAmt = createNumber("fine_amt", Double.class);

    public final StringPath letSent = createString("let_sent");

    public final NumberPath<Integer> loanAcNo = createNumber("loan_ac_no", Integer.class);

    public final StringPath loanAcType = createString("loan_ac_type");

    public final NumberPath<Double> mchgAmt = createNumber("mchg_amt", Double.class);

    public final StringPath multCr = createString("mult_cr");

    public final NumberPath<Integer> noDocs = createNumber("no_docs", Integer.class);

    public final StringPath payeeName = createString("payee_name");

    public final NumberPath<Double> poComm = createNumber("po_comm", Double.class);

    public final StringPath postInd = createString("post_ind");

    public final NumberPath<Integer> prevCtrlNo = createNumber("prev_ctrl_no", Integer.class);

    public final StringPath qdpDt = createString("qdp_dt");

    public final NumberPath<Integer> qdpNo = createNumber("qdp_no", Integer.class);

    public final StringPath retNorm = createString("ret_norm");

    public final NumberPath<Integer> sendTo = createNumber("send_to", Integer.class);

    public final StringPath toBounce = createString("to_bounce");

    public final StringPath trfType = createString("trf_type");

    public final NumberPath<Double> trnAmt = createNumber("trn_amt", Double.class);

    public final StringPath trnType = createString("trn_type");

    public final StringPath veDtTime = createString("ve_dt_time");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QChequeold(String variable) {
        super(QChequeold.class, forVariable(variable), "null", "chequeold");
    }

    @SuppressWarnings("all")
    public QChequeold(Path<? extends QChequeold> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "chequeold");
    }

    public QChequeold(PathMetadata<?> metadata) {
        super(QChequeold.class, metadata, "null", "chequeold");
    }

}

