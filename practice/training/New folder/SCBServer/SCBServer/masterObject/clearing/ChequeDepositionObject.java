package masterObject.clearing;

import java.util.Vector;

public class ChequeDepositionObject implements java.io.Serializable
{

	String string_mult_cr,string_company_name,string_chqddpo,string_qdp_date,string_cr_ac_type;
	String string_dr_ac_type,string_branch_name,string_discount_ind,string_despatch_ind,post_ind;
	String string_de_tml,string_de_user,string_de_dt_time,string_de_date,string_ve_tml,string_ve_user,string_ve_datetime,string_ve_date,clgtype;
	String string_bounce_ind,string_exp_clgdt ,string_bank_code,doc_bs , cd_indicator;
	double double_po_comm,double_discount_amt,double_trn_amt,double_Actdiscount_amt,double_fine_amt,reasonFine;
	double disc_chg;
	boolean balance_ind;
	int int_cr_ac_no,int_dr_ac_no,int_exp_clgno;
	int int_qdp_no,int_send_to;
	int int_doc_source,int_doc_destin,int_ack_no;
	long long_ctrl_no,long_prev_ctrl_no;
	Vector vector_rea_arr;
	String string_payee_name,string_bank_name, sysdate;
    private String string_return;
    String loan_ac_type,clgDate;
    int loan_ac_no;

    int ackNo,creditACNo,qdpNo;
    String bankCode,branchName,creditACType,chqDDPO,qdpDate;
    double tranAmt,pOCommission,discChg;
    
    
    //i defined nee vars
    long controlNo; 
    String payeeName,debitACType, bankName,companyName;
    int sendTo ,debitACNo;
   
    
    
	public long getControlNo(){return controlNo;}
	public void setControlNo(long long_ctrl_no){this.controlNo=long_ctrl_no;}
	

	public String getLoanAcc()
	{
	    return loan_ac_type;
	}
	public void setLoanAcc(String loan_ac_type)
	{
	    this.loan_ac_type=loan_ac_type;
	}
  
	

	public int getLoanAcc_No()
	{
	    return loan_ac_no;
	}
	public void setLoanAcc_No(int loan_ac_no)
	{
	    this.loan_ac_no=loan_ac_no;
	}

	
	public double getDiscChg(){return discChg;}
public void setDiscChg(double disc_chg){this.discChg=disc_chg;}

public boolean getBalanceInd(){return balance_ind;}
public void setBalanceInd( boolean balance_ind){this.balance_ind=balance_ind;}


	public String getReturn(){return string_return;}
	public void setReturn(String string_return){this.string_return=string_return;}

	public String getMultiCredit(){return string_mult_cr;}
	public void setMultiCredit(String string_mult_cr){this.string_mult_cr=string_mult_cr;}
		
	public String getCompanyName(){return companyName;}
	public void setCompanyName(String string_company_name){this.companyName=string_company_name;}

	
	
	public int getDocSource(){return int_doc_source;}
	public void setDocSource(int int_doc_source){this.int_doc_source=int_doc_source;}

	public int getDocDestination(){return int_doc_destin;}
	public void setDocDestination(int int_doc_destin){this.int_doc_destin=int_doc_destin;}

	public int getSendTo(){return sendTo;}
	public void setSendTo(int int_send_to){this.sendTo=int_send_to;}

	public String getExpectedClgDate(){return string_exp_clgdt;}
	public void setExpectedClgDate(String string_exp_clgdt){this.string_exp_clgdt=string_exp_clgdt;}

	public int getExpClgNo(){return int_exp_clgno;}
	public void setExpClgNo(int int_exp_clgno){this.int_exp_clgno=int_exp_clgno;}

	public String getChqDDPO(){return chqDDPO;}
	public void setChqDDPO(String string_chqddpo){this.chqDDPO=string_chqddpo;}

	public int getQdpNo(){return qdpNo;}
	public void setQdpNo(int int_qdp_no){this.qdpNo=int_qdp_no;}

	public String getQdpDate(){return qdpDate;}
	public void setQdpDate(String string_qdp_date){this.qdpDate=string_qdp_date;}

	public String getCreditACType(){return creditACType;}
	public void setCreditACType(String string_cr_ac_type){this.creditACType=string_cr_ac_type;}

	public int getCreditACNo(){return creditACNo;}
	public void setCreditACNo(int int_cr_ac_no){this.creditACNo=int_cr_ac_no;}

	public double getPOCommission(){return pOCommission;}
	public void setPOCommission(double double_po_comm){this.pOCommission=double_po_comm;}

	public String getDebitACType(){return debitACType;}
	public void setDebitACType(String string_dr_ac_type){this.debitACType=string_dr_ac_type;}

	public int getDebitACNo(){return debitACNo;}
	public void setDebitACNo(int int_dr_ac_no){this.debitACNo=int_dr_ac_no;}

	public String getBankCode(){return bankCode;}
	public void setBankCode(String int_bank_code){this.bankCode=int_bank_code;}

	public String getBranchName(){return branchName;}
	public void setBranchName(String string_branch_name){this.branchName=string_branch_name;}

	public String getDiscInd(){return string_discount_ind;}
	public void setDiscInd(String string_discount_ind){this.string_discount_ind=string_discount_ind;}

	public String getBounceInd(){return string_bounce_ind;}
	public void setBounceInd(String string_bounce_ind){this.string_bounce_ind=string_bounce_ind;}

	public double getTranAmt(){return tranAmt;}
	public void setTranAmt(double double_trn_amt){this.tranAmt=double_trn_amt;}

	public double getDiscAmt(){return double_discount_amt;}
	public void setDiscAmt(double double_discount_amt){this.double_discount_amt=double_discount_amt;}

	public double getFineAmt(){return double_fine_amt;}
	public void setFineAmt(double double_fine_amt){this.double_fine_amt=double_fine_amt;}

	public double getActDiscAmt(){return double_Actdiscount_amt;}
	public void setActDiscAmt(double double_Actdiscount_amt){this.double_Actdiscount_amt=double_Actdiscount_amt;}

	
	public String getDeTml(){return string_de_tml;}
	public void setDeTml(String string_de_tml){this.string_de_tml=string_de_tml;}

	public String getDeUser(){return string_de_user;}
	public void setDeUser(String string_de_user){this.string_de_user=string_de_user;}

	public String getDeTime(){return string_de_dt_time;}
	public void setDeTime(String string_de_dt_time){this.string_de_dt_time=string_de_dt_time;}

	public String getVeTml(){return string_ve_tml;}
	public void setVeTml(String string_ve_tml){this.string_ve_tml=string_ve_tml;}

	public String getVeUser(){return string_ve_user;}
	public void setVeUser(String string_ve_user){this.string_ve_user=string_ve_user;}

	public String getDespInd(){return string_despatch_ind;}
	public void setDespInd(String string_despatch_ind){this.string_despatch_ind=string_despatch_ind;}
	
	//--------------------newly added---------------------------------
	
	public int getAckNo(){return ackNo;}
	public void setAckNo(int int_ack_no){this.ackNo=int_ack_no;}
	
	
	public String getClgType(){return clgtype;}
	public void setClgType(String clgtype){this.clgtype=clgtype;}

	public String getPayeeName(){return payeeName;}
	public void setPayeeName(String string_payee_name){this.payeeName=string_payee_name;}

	public String getBankName(){return bankName;}
	public void setBankName(String string_bank_name){this.bankName=string_bank_name;}
	
	public Vector getReasonArray(){return vector_rea_arr;}
	public void setReasonArray(Vector vector_rea_arr){this.vector_rea_arr=vector_rea_arr;}
	public String getVe_datetime() {
		return string_ve_datetime;
	}
	public void setVe_datetime(String string_ve_datetime) {
		this.string_ve_datetime = string_ve_datetime;
	}
	public String getVe_date() {
		return string_ve_date;
	}
	public void setVe_date(String string_ve_date) {
		this.string_ve_date = string_ve_date;
	}
	public String getDe_date() {
		return string_de_date;
	}
	public void setDe_date(String string_de_date) {
		this.string_de_date = string_de_date;
	}
	public String getDoc_bs() {
		return doc_bs;
	}
	public void setDoc_bs(String doc_bs) {
		this.doc_bs = doc_bs;
	}
	public String getCd_indicator() {
		return cd_indicator;
	}
	public void setCd_indicator(String cd_indicator) {
		this.cd_indicator = cd_indicator;
	}
	public long getprev_ctrl_no() {
		return long_prev_ctrl_no;
	}
	public void setprev_ctrl_no(long long_prev_ctrl_no) {
		this.long_prev_ctrl_no = long_prev_ctrl_no;
	}
	public String getSysdate() {
		return sysdate;
	}
	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}
	public String getPost_ind() {
		return post_ind;
	}
	public void setPost_ind(String post_ind) {
		this.post_ind = post_ind;
	}
	public double getReasonFine() {
		
		return reasonFine;
	}
	public void setReasonFine(double reasonFine) {
		
		this.reasonFine = reasonFine;
	}
	public String getClgDate() {
		return clgDate;
	}
	public void setClgDate(String clgDate) {
		this.clgDate = clgDate;
	}    
}