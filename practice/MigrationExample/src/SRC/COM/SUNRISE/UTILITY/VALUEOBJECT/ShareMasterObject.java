package SRC.COM.SUNRISE.UTILITY.VALUEOBJECT;




import java.io.Serializable;
import java.util.Hashtable;

public class ShareMasterObject implements Serializable
{
    int share_number,tran_number,tshare_number,cid,share_type,recaccno,nom_reg_no,paybcode,payaccno,intrd_accno,bcode,add_type,memcat;
    double amount,share_val,divamt,drfamt,sh_bal,num_shares,misc_amount,total_amount,Share_bal,share_val_changed,share_val_previous,amount_paid,balance;
    String intrd_acctype,paymode,payacctype,issuedate,loan_availed,shr_status,trn_date;
    String div_upto_dt,closedate,sharestmt,rel_director,rel_desc;
    String verified,name,recmode,recactype,ldgprntdt;
    String glcode,suspglcode,shtrntype,shacctype,bname;
    int swithdrawaltype,no_cert,trn_seq;
    int branchcode;
    double totamt;
    String de_datetime,de_tml,de_user;
    int scrollno,old_scrollno;
    String cash_name,cash_acctype,share_stat,trf_dt;
    double cash_amount;
    Hashtable certificate;

    NomineeObject nominee ;
  

    
    public String getShareTrnType(){return shtrntype;}
    public void setShareTrnType(String str){this.shtrntype=str;}
    
    public int getTranNumber(){return tran_number;}
    public void setTranNumber(int tran_number){this.tran_number = tran_number;}
    
    public String getBranchName(){return bname;}
    public void setBranchName(String str){this.bname=str;}
    
    
    public String getGLCode(){return glcode;}
    public void setGLCode(String str){this.glcode=str;}
    
    public String getSuspGLCode(){return suspglcode;}
    public void setSuspGLCode(String str){this.suspglcode=str;}
    
    public String getShareAccType(){return shacctype;}
    public void setShareAccType(String  str){this.shacctype=str;}
    
    public int getShareNumber(){return share_number;}
    public void setShareNumber(int share_number){this.share_number = share_number;}
    
    public int getMemberCategory(){return memcat;}
    public void setMemberCategory(int memcat){this.memcat = memcat;}
    
    public int getMailingAddress(){return add_type;}
    public void setMailingAddress(int add_type){this.add_type=add_type;}
    
    public int getTempShareNumber(){return tshare_number;}
    public void setTempShareNumber(int share_number){this.tshare_number = share_number;}
    
    public String getShareStatus(){return shr_status;}
    public void setShareStatus(String shr_status){this.shr_status = shr_status;}
    
    
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    
    public int getBranchCode(){return bcode;}
    public void setBranchCode(int bcode){this.bcode=bcode;}
    
    public int  getCustomerId(){return cid;}
    public void setCustomerId(int id){cid=id;}
    
    public int getShareType(){return share_type;}
    public void setShareType(int share_type){this.share_type =share_type;}
    
    public String getIssueDate(){return issuedate;}
    public void setIssueDate(String st){this.issuedate=st;}
    
    public double getNumberofShares(){	return num_shares;}
    public void setNumberofShares(double num_shares){this.num_shares=num_shares;}
    
    public double getShareVal(){return share_val;}
    public void setShareVal(double shareval){this.share_val=shareval;}
    
    public int getIntroducerAccno(){return intrd_accno;}
    public void setIntroducerAccno(int introducer_accno){this.intrd_accno=introducer_accno;}
    
    public String getIntroducerAcctype(){return intrd_acctype;}
    public void setIntroducerAcctype(String intrd_acctype){this.intrd_acctype=intrd_acctype;}
    
    public int getNomineeno(){return nom_reg_no;}
    public void setNomineeno(int nom_reg_no){this.nom_reg_no=nom_reg_no;}
    
    
    public String getDivUptoDate(){return div_upto_dt;}
    public void setDivUptoDate(String div_upto_dt){this.div_upto_dt = div_upto_dt;}
    
    public String getLoanAvailed(){return loan_availed;}
    public void setLoanAvailed(String loan_availed){this.loan_availed = loan_availed;}
    
    public String getCloseDate(){return closedate;}
    public void setCloseDate(String closedate){this.closedate = closedate;}
    
    
    public String getRecievedMode(){return recmode;}
    public void setRecievedMode(String pay_mode){this.recmode=pay_mode;}
    
    public int getRecievedAccno(){return recaccno;}
    public void setRecievedAccno(int accno){recaccno=accno;}
    
    public String getRecievedAcctype(){return recactype;}
    public void setRecievedAcctype(String actype){recactype=actype;}
    
    public String getLdgPrntDate(){return ldgprntdt;}
    public void setLdgPrntDate(String ldgprntdt){this.ldgprntdt = ldgprntdt;}
    
    
    public double getAmount(){return amount;}
    public void setAmount(double amount){this.amount=amount;}
    
    public double getMiscAmount(){return misc_amount;}
    public void setMiscAmount(double mis_amt){this.misc_amount=mis_amt;}
     
     /*public double getTotalAmount(){return total_amount;}
     public void setTotalAmount(double misc_amt){this.total_amount=total_amount;}*/
    
    public String getVerified(){return verified;}
    public void setVerified(String verified){this.verified=verified;}
    
    public String getRelationtoDirector(){return rel_director;}
    public void setRelationtoDirector(String rel){rel_director=rel;}
    
    public String getRelationDesc(){return rel_desc;}
    public void setRelationDesc(String desc){rel_desc=desc;}
    
    public double getShareBalance(){return sh_bal;}
    public void setShareBalance(double no){sh_bal=no;}
    
    public String getPayMode(){return paymode;}
    public void setPayMode(String paymode){this.paymode=paymode;}
    
    public int getPaymentAccno(){return payaccno;}
    public void setPaymentAccno(int accno){this.payaccno=accno;}
    
    public String getPaymentAcctype(){return payacctype;}
    public void setPaymentAcctype(String acctype){this.payacctype=acctype;}
    
    public double getDivAmount(){return divamt;}
    public void  setDivAmount(double divamt){this.divamt=divamt;}
    
    public void setDrfAmount(double drfamt){this.drfamt=drfamt;}
    public double getDrfAmount(){return drfamt;}
    
    
    public void setWithdrawalType(int swithdrawaltype){this.swithdrawaltype=swithdrawaltype;}//Karthi
    public int getWithdrawalType(){return swithdrawaltype;}
    
    public void setDisplayShareBalance(double shrbal) {this.Share_bal=shrbal;}
    public double getDisplayShareBalance(){return Share_bal;}
    
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
    
    public void setNumberCert(int nocert){this.no_cert=nocert;}
    public int getNumberCert(){return no_cert;}
    
    public void setTransferDate(String trfdate){this.trf_dt=trfdate;}
    public String getTransferDate(){return trf_dt;}
    
    public void setShrState(String shrstate){this.share_stat=shrstate;}
    public String getShrState(){return share_stat;}
    
    public double getShare_val_changed() {
		return share_val_changed;
	}
	public void setShare_val_changed(double share_val_changed) {
		this.share_val_changed = share_val_changed;
	}
	public double getShare_val_previous() {
		return share_val_previous;
	}
	public void setShare_val_previous(double share_val_previous) {
		this.share_val_previous = share_val_previous;
	}
	public double getAmount_paid() {
		return amount_paid;
	}
	public void setAmount_paid(double amount_paid) {
		this.amount_paid = amount_paid;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double bal) {
		this.balance = bal;
	}
	public double getTotamt() {
		return totamt;
	}
	public void setTotamt(double totamt) {
		this.totamt = totamt;
	}
	public NomineeObject getNominee() {
		return nominee;
	}
	public void setNominee(NomineeObject nominee) {
		this.nominee = nominee;
	}
	public String getDe_datetime() {
		return de_datetime;
	}
	public void setDe_datetime(String de_datetime) {
		this.de_datetime = de_datetime;
	}
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
	public Hashtable getCertificate() {
		return certificate;
	}
	public void setCertificate(Hashtable certificate) {
		this.certificate = certificate;
	}
	public String getTrn_date() {
		return trn_date;
	}
	public void setTrn_date(String trn_date) {
		this.trn_date = trn_date;
	}
	
	

}