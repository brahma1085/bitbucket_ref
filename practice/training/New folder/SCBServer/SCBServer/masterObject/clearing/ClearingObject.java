package masterObject.clearing;

public class ClearingObject implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String string_clg_date,string_trn_type,string_clg_type,string_doc_bs,string_mult_cr;
	String string_comp_name,string_chqddpo,string_qdp_date,string_ret_norm,string_trn_mode;
	String string_trf_type,string_cr_ac_type,string_dr_ac_type,string_payee_name;
	String branchName,string_bounce,string_desp_ind,string_post_ind;
	String string_disc_ind,string_exp_clg_date,string_letter_sent,string_de_tml;
	String string_de_user,string_de_dt_time,string_ve_tml,string_ve_user,string_ve_dt_time;
	String string_source_name,string_destin_name,string_bank_name;
	
	int int_clg_no,send_to,int_ctrl_no,int_ack_no,int_doc_sourse;
	int int_no_of_docs,qdpNo,int_prev_ctrl_no,int_doc_destin;
	int int_cr_ac_no,int_dr_ac_no,int_exp_clgno,bankCode;
	long long_ctrl_no;
	
	double POCommission,tranAmt,discAmt,double_mail_chg_amt,fineAmt,double_doc_total;

	String clgDate,tranType,clgType,docBs,multiCredit,companyName,chqDDPO,sourceName,bankName,creditACType,qdpDate;
	int sendTo,clgNo,controlNo,ackNo,docSource,docDestination,noOfDocs,creditACNo;
	double docTotal;
	
	//16/11/2011
	String retNormally,despInd,payeeName,tranMode,trfType,deUser,deTml,deTime,veUser,veTml,veTime;
	
	
	public String getClgDate(){return clgDate;}
	public void setClgDate(String string_clg_date){this.clgDate=string_clg_date;}

	public int getClgNo(){return clgNo;}
	public void setClgNo(int int_clg_no){this.clgNo=int_clg_no;}

	public int getSendTo(){return sendTo;}
	public void setSendTo(int send_to){this.sendTo=send_to;}

	public int getControlNo(){return controlNo;}
	public void setControlNo(int int_ctrl_no){this.controlNo=int_ctrl_no;}
	
	public long getCtrlNo(){return long_ctrl_no;}
	public void setCtrlNo(long long_ctrl_no){this.long_ctrl_no=long_ctrl_no;}

	public String getTranType(){return tranType;}
	public void setTranType(String string_trn_type){this.tranType=string_trn_type;}

	public String getClgType(){return clgType;}
	public void setClgType(String string_clg_type){this.clgType=string_clg_type;}

	public int getAckNo(){return ackNo;}
	public void setAckNo(int int_ack_no){this.ackNo=int_ack_no;}

	public int getDocSource(){return docSource;}
	public void setDocSource(int int_doc_sourse){this.docSource=int_doc_sourse;}

	public int getDocDestination(){return docDestination;}
	public void setDocDestination(int int_doc_destin){this.docDestination=int_doc_destin;}

	public String getDocBs(){return docBs;}
	public void setDocBs(String string_doc_bs){this.docBs=string_doc_bs;}

	public int getNoOfDocs(){return noOfDocs;}
	public void setNoOfDocs(int int_no_of_docs){this.noOfDocs=int_no_of_docs;}

	public double getDocTotal(){return docTotal;}
	public void setDocTotal(double double_doc_total){this.docTotal=double_doc_total;}

	public String getMultiCredit(){return multiCredit;}
	public void setMultiCredit(String string_mult_cr){this.multiCredit=string_mult_cr;}

	public String getCompanyName(){return companyName;}
	public void setCompanyName(String string_comp_name){this.companyName=string_comp_name;}

	public String getChqDDPO(){return chqDDPO;}
	public void setChqDDPO(String string_chqddpo){this.chqDDPO=string_chqddpo;}

	public int getQdpNo(){return qdpNo;}
	public void setQdpNo(int int_qdp_no){this.qdpNo=int_qdp_no;}

	public String getQdpDate(){return qdpDate;}
	public void setQdpDate(String string_qdp_date){this.qdpDate=string_qdp_date;}

	public String getRetNormally(){return retNormally;}
	public void setRetNormally(String string_retNormally){this.retNormally=string_retNormally;}

	public int getPrevCtrlNo(){return int_prev_ctrl_no;}
	public void setPrevCtrlNo(int int_prev_ctrl_no){this.int_prev_ctrl_no=int_prev_ctrl_no;}

	public String getTranMode(){return tranMode;}
	public void setTranMode(String string_tranMode){this.tranMode=string_tranMode;}

	public String getTrfType(){return trfType;}
	public void setTrfType(String string_trfType){this.trfType=string_trfType;}

	public String getCreditACType(){return creditACType;}
	public void setCreditACType(String string_cr_ac_type){this.creditACType=string_cr_ac_type;}

	public int getCreditACNo(){return creditACNo;}
	public void setCreditACNo(int int_cr_ac_no){this.creditACNo=int_cr_ac_no;}

	public double getPOCommission(){return POCommission;}
	public void setPOCommission(double double_po_comm){this.POCommission=double_po_comm;}

	public String getDebitACType(){return string_dr_ac_type;}
	public void setDebitACType(String string_dr_ac_type){this.string_dr_ac_type=string_dr_ac_type;}

	public int getDebitACNo(){return int_dr_ac_no;}
	public void setDebitACNo(int int_dr_ac_no){this.int_dr_ac_no=int_dr_ac_no;}

	public String getPayeeName(){return payeeName;}
	public void setPayeeName(String string_payeeName){this.payeeName=string_payeeName;}

	public int getBankCode(){return bankCode;}
	public void setBankCode(int int_bank_code){this.bankCode=int_bank_code;}

	public String getBranchName(){return branchName;}
	public void setBranchName(String string_branch_name){this.branchName=string_branch_name;}

	public double getTranAmt(){return tranAmt;}
	public void setTranAmt(double double_trn_amt){this.tranAmt=double_trn_amt;}

	public String getToBounce(){return string_bounce;}
	public void setToBounce(String string_bounce){this.string_bounce=string_bounce;}

	public String getDespInd(){return despInd;}
	public void setDespInd(String string_despInd){this.despInd=string_despInd;}

	public String getPostInd(){return string_post_ind;}
	public void setPostInd(String string_post_ind){this.string_post_ind=string_post_ind;}

	public String getDiscInd(){return string_disc_ind;}
	public void setDiscInd(String string_disc_ind){this.string_disc_ind=string_disc_ind;}

	public double getDiscAmt(){return discAmt;}
	public void setDiscAmt(double double_disc_amt){this.discAmt=double_disc_amt;}

	public String getExpectedClgDate(){return string_exp_clg_date;}
	public void setExpectedClgDate(String string_exp_clg_date){this.string_exp_clg_date=string_exp_clg_date;}

	public int getExpClgNo(){return int_exp_clgno;}
	public void setExpClgNo(int int_exp_clgno){this.int_exp_clgno=int_exp_clgno;}

	public String getLetterSent(){return string_letter_sent;}
	public void setLetterSent(String string_letter_sent){this.string_letter_sent=string_letter_sent;}

	public double getMChangeAmt(){return double_mail_chg_amt;}
	public void setMChangeAmt(double double_mail_chg_amt){this.double_mail_chg_amt=double_mail_chg_amt;}

	public double getFineAmt(){return fineAmt;}
	public void setFineAmt(double double_fine_amt){this.fineAmt=double_fine_amt;}

	public String getDeTml(){return deTml;}
	public void setDeTml(String string_de_tml){this.deTml=string_de_tml;}

	public String getDeUser(){return deUser;}
	public void setDeUser(String string_de_user){this.deUser=string_de_user;}

	public String getDeTime(){return deTime;}
	public void setDeTime(String string_de_dt_time){this.deTime=string_de_dt_time;}

	public String getVeTml(){return veTml;}
	public void setVeTml(String string_ve_tml){this.veTml=string_ve_tml;}

	public String getVeUser(){return veUser;}
	public void setVeUser(String string_ve_user){this.veUser=string_ve_user;}

	public String getVeTime(){return veTime;}
	public void setVeTime(String string_ve_dt_time){this.veTime=string_ve_dt_time;}
	
	public String getSourceName(){return sourceName;}
	public void setSourceName(String string_source_name){this.sourceName=string_source_name;}
	
	public String getDestName(){return string_destin_name;}
	public void setDestName(String string_destin_name){this.string_destin_name=string_destin_name;}
	
	public String getBankName(){return bankName;}
	public void setBankName(String string_bank_name){this.bankName=string_bank_name;}
	
	
}