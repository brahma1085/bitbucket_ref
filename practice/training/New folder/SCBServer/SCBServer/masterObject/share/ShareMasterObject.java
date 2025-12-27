package masterObject.share;

import masterObject.general.AccountObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.UserVerifier;

import java.io.Serializable;

public class ShareMasterObject implements Serializable
{
    int shareNumber,tranNumber,tempShareNumber,customerId,share_type,recievedAccno,nomineeno,paybcode,paymentAccno,introducerAccno,branchCode,mailingAddress,memberCategory;
    double amount,shareVal,divAmount,drfAmount,shareBalance,numberofShares,misc_amount,totalAmount,displayShareBalance,amount_paid,acc_balance;
    String intrd_acctype,payMode,paymentAcctype,issueDate,loanAvailed,shareStatus,colour;
    String divUptoDate,closeDate,sharestmt,rel_director,rel_desc;
    String verified,name,recmode,recievedAcctype,ldgprntdt;
    String glcode,suspglcode,shtrntype,shacctype,branchName;
    String de_tml,de_user,de_date,ve_user,ve_tml,ve_date,trnamt;
    int swithdrawaltype,numberCert,trn_seq;
    int with_shares,shnum;
    String id,trnMode;
    //Added By Karthi
    int scrollno,old_scrollno;
    String cash_name,cash_acctype,share_stat,transferDate;
    double cash_amount;
    String lnk_shares,moduleabbr,ac_no,disb_left,pr_bal,operation,nom_name;
    AccountObject acobj;
    
    
    
    public UserVerifier uv=new UserVerifier();
    
    NomineeObject nom[]=null;
    SignatureInstructionObject siginsobject[]=null;
    
    public String getShareTrnType(){return shtrntype;}
    public void setShareTrnType(String str){this.shtrntype=str;}
    
    public int getTranNumber(){return tranNumber;}
    public void setTranNumber(int tran_number){this.tranNumber = tran_number;}
    
    public String getBranchName(){return branchName;}
    public void setBranchName(String str){this.branchName=str;}
    
    
    public String getGLCode(){return glcode;}
    public void setGLCode(String str){this.glcode=str;}
    
    public String getSuspGLCode(){return suspglcode;}
    public void setSuspGLCode(String str){this.suspglcode=str;}
    
    public String getShareAccType(){return shacctype;}
    public void setShareAccType(String  str){this.shacctype=str;}
    
    public int getShareNumber(){return shareNumber;}
    public void setShareNumber(int share_number){this.shareNumber = share_number;}
    
    public int getMemberCategory(){return memberCategory;}
    public void setMemberCategory(int memcat){this.memberCategory = memcat;}
    
    public int getMailingAddress(){return mailingAddress;}
    public void setMailingAddress(int add_type){this.mailingAddress=add_type;}
    
    public int getTempShareNumber(){return tempShareNumber;}
    public void setTempShareNumber(int share_number){this.tempShareNumber = share_number;}
    
    public String getShareStatus(){return shareStatus;}
    public void setShareStatus(String shr_status){this.shareStatus = shr_status;}
    
    
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    
    public int getBranchCode(){return branchCode;}
    public void setBranchCode(int bcode){this.branchCode=bcode;}
    
    public int  getCustomerId(){return customerId;}
    public void setCustomerId(int id){customerId=id;}
    
    public int getShareType(){return share_type;}
    public void setShareType(int share_type){this.share_type =share_type;}
    
    public String getIssueDate(){return issueDate;}
    public void setIssueDate(String st){this.issueDate=st;}
    
    public double getNumberofShares(){	return numberofShares;}
    public void setNumberofShares(double num_shares){this.numberofShares=num_shares;}
    
    public double getShareVal(){return shareVal;}
    public void setShareVal(double shareval){this.shareVal=shareval;}
    
    public int getIntroducerAccno(){return introducerAccno;}
    public void setIntroducerAccno(int introducer_accno){this.introducerAccno=introducer_accno;}
    
    public String getIntroducerAcctype(){return intrd_acctype;}
    public void setIntroducerAcctype(String intrd_acctype){this.intrd_acctype=intrd_acctype;}
    
    public int getNomineeno(){return nomineeno;}
    public void setNomineeno(int nom_reg_no){this.nomineeno=nom_reg_no;}
    
    
    public String getDivUptoDate(){return divUptoDate;}
    public void setDivUptoDate(String div_upto_dt){this.divUptoDate = div_upto_dt;}
    
    public String getLoanAvailed(){return loanAvailed;}
    public void setLoanAvailed(String loan_availed){this.loanAvailed = loan_availed;}
    
    public String getCloseDate(){return closeDate;}
    public void setCloseDate(String closedate){this.closeDate = closedate;}
    
    
    public String getRecievedMode(){return recmode;}
    public void setRecievedMode(String pay_mode){this.recmode=pay_mode;}
    
    public int getRecievedAccno(){return recievedAccno;}
    public void setRecievedAccno(int accno){recievedAccno=accno;}
    
    public String getRecievedAcctype(){return recievedAcctype;}
    public void setRecievedAcctype(String actype){recievedAcctype=actype;}
    
    public String getLdgPrntDate(){return ldgprntdt;}
    public void setLdgPrntDate(String ldgprntdt){this.ldgprntdt = ldgprntdt;}
    
    
    public double getAmount(){return amount;}
    public void setAmount(double amount){this.amount=amount;}
    
    public double getMiscAmount(){return misc_amount;}
    public void setMiscAmount(double mis_amt){this.misc_amount=mis_amt;}
     
     public double getTotalAmount(){return totalAmount;}
     public void setTotalAmount(double total_amount){this.totalAmount=total_amount;}
    
    public String getVerified(){return verified;}
    public void setVerified(String verified){this.verified=verified;}
    
    public String getRelationtoDirector(){return rel_director;}
    public void setRelationtoDirector(String rel){rel_director=rel;}
    
    public String getRelationDesc(){return rel_desc;}
    public void setRelationDesc(String desc){rel_desc=desc;}
    
    public double getShareBalance(){return shareBalance;}
    public void setShareBalance(double no){shareBalance=no;}
    
    public String getPayMode(){return payMode;}
    public void setPayMode(String paymode){this.payMode=paymode;}
    
    public int getPaymentAccno(){return paymentAccno;}
    public void setPaymentAccno(int accno){this.paymentAccno=accno;}
    
    public String getPaymentAcctype(){return paymentAcctype;}
    public void setPaymentAcctype(String acctype){this.paymentAcctype=acctype;}
    
    public double getDivAmount(){return divAmount;}
    public void  setDivAmount(double divamt){this.divAmount=divamt;}
    
    public void setDrfAmount(double drfamt){this.drfAmount=drfamt;}
    public double getDrfAmount(){return drfAmount;}
    
    public void setNomineeDetails(NomineeObject nom[]){this.nom=nom;}
    public NomineeObject[] getNomineeDetails(){return nom;}
    
    public void setWithdrawalType(int swithdrawaltype){this.swithdrawaltype=swithdrawaltype;}//Karthi
    public int getWithdrawalType(){return swithdrawaltype;}
    
    public void setDisplayShareBalance(double shrbal) {this.displayShareBalance=shrbal;}
    public double getDisplayShareBalance(){return displayShareBalance;}
    
    //Added By Karthi-->23/12/2005
    
    public void setScrollno(int scrno){this.scrollno=scrno;}
    public int getScrollno(){return scrollno;}
    
    public void setCashAcctype(String cashaccttype){this.cash_acctype=cashaccttype;}
    public String getCashAcctype(){return cash_acctype;}
    
    public void setCashAccountName(String cashname){this.cash_name=cashname;}
    public String getCashAccountName(){return cash_name;}
    
    public void setCashAmount(double cashamt){this.cash_amount=cashamt;}
    public double getCashAmount(){return cash_amount;} 
    
    public void setOldCashReceivedAccno(int oldscrollno){this.old_scrollno=oldscrollno;}//24/12/2005
    public int getOldCashReceivedAccno(){return old_scrollno;}
    
    
    //Added by Karthi==>18/04/2006
    public void setTrnSeq(int trnseq){this.trn_seq=trnseq;}
    public int getTrnseq(){return trn_seq;}
    
    public void setNumberCert(int nocert){this.numberCert=nocert;}
    public int getNumberCert(){return numberCert;}
    
    public void setTransferDate(String trfdate){this.transferDate=trfdate;}
    public String getTransferDate(){return transferDate;}
    
    public void setShrState(String shrstate){this.share_stat=shrstate;}
    public String getShrState(){return share_stat;}
    
    public void setSignatureDetails(SignatureInstructionObject sig[]){this.siginsobject=sig;}//Karthi -->12/07/2006
    public SignatureInstructionObject[] getSignatureDetails(){return siginsobject;}
	public String getDe_tml() {
		return de_tml;
	}
	public void setDe_tml(String de_tml) {
		this.de_tml = de_tml;
	}
	public String getDe_user() {
		return de_user;
	}
	public void setDe_user(String de_user) {
		this.de_user = de_user;
	}
	public String getDe_date() {
		return de_date;
	}
	public void setDe_date(String de_date) {
		this.de_date = de_date;
	}
	public String getVe_user() {
		return ve_user;
	}
	public void setVe_user(String ve_user) {
		this.ve_user = ve_user;
	}
	public String getVe_tml() {
		return ve_tml;
	}
	public void setVe_tml(String ve_tml) {
		this.ve_tml = ve_tml;
	}
	public String getVe_date() {
		return ve_date;
	}
	public void setVe_date(String ve_date) {
		this.ve_date = ve_date;
	}
	public int getWith_shares() {
		return with_shares;
	}
	public void setWith_shares(int with_shares) {
		this.with_shares = with_shares;
	}
	public double getAmount_paid() {
		return amount_paid;
	}
	public void setAmount_paid(double amount_paid) {
		this.amount_paid = amount_paid;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public double getAcc_balance() {
		return acc_balance;
	}
	public void setAcc_balance(double acc_balance) {
		this.acc_balance = acc_balance;
	}
	public String getLnk_shares() {
		return lnk_shares;
	}
	public void setLnk_shares(String lnk_shares) {
		this.lnk_shares = lnk_shares;
	}
	public String getModuleabbr() {
		return moduleabbr;
	}
	public void setModuleabbr(String moduleabbr) {
		this.moduleabbr = moduleabbr;
	}
	public String getAc_no() {
		return ac_no;
	}
	public void setAc_no(String ac_no) {
		this.ac_no = ac_no;
	}
	public String getDisb_left() {
		return disb_left;
	}
	public void setDisb_left(String disb_left) {
		this.disb_left = disb_left;
	}
	public String getPr_bal() {
		return pr_bal;
	}
	public void setPr_bal(String pr_bal) {
		this.pr_bal = pr_bal;
	}
	public AccountObject getAcobj() {
		return acobj;
	}
	public void setAcobj(AccountObject acobj) {
		this.acobj = acobj;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getTrnamt() {
		return trnamt;
	}
	public void setTrnamt(String trnamt) {
		this.trnamt = trnamt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNom_name() {
		return nom_name;
	}
	public void setNom_name(String nom_name) {
		this.nom_name = nom_name;
	}
	public int getShnum() {
		return shnum;
	}
	public void setShnum(int shnum) {
		this.shnum = shnum;
	}
	public String getTrnMode() {
		return trnMode;
	}
	public void setTrnMode(String trnMode) {
		this.trnMode = trnMode;
	}

}