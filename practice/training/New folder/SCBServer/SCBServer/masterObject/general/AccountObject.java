package masterObject.general;
import java.io.Serializable;


public class AccountObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	String acc_name,acc_type,sign,verify,status;
	double amount,shadow_balance,int_amount;	
	int category,sub_category;	
	int cid,no_of_jt_hldr;
	private String post_ind;
	SignatureInstructionObject[] siob=null;
	private String freeze_ind;
	private String default_ind;
	double creditlimit;
	String limit_upto;
	int accno,ac_category,nom_regno,inst_no,close_ind;
	String trndate,intro_name,ac_opendate,ch_bk_issue,pbl_date,ac_closedate;
	private int scroll_no;
	public  String branch_name;
	private String trn_date,qdp_date;
	
	private boolean is_other_branch,is_director;
	
	private int dep_yrs = 0,dep_mths = 0,dep_days = 0;//ship......22/05/2006

	public int getAccno(){ return accno;}
	public void setAccno(int accno){this.accno=accno;}

	public int getCid(){return cid;}
	public void setCid(int cid){this.cid=cid;}
	
	public String getAcctype(){ return acc_type;}
	public void setAcctype(String acc_type){this.acc_type=acc_type;}

	public String getAccname(){ return acc_name;}
	public void setAccname(String acc_name){this.acc_name=acc_name;}
		
	public double getAmount(){ return amount;}
	public void setAmount(double amount){this.amount=amount;}

	public double getShadowBalance(){ return shadow_balance;}
	public void setShadowBalance(double amount){this.shadow_balance=amount;}

	public String getVerified(){ return verify;}
	public void setVerified(String verify){this.verify=verify;}

	public String getAccStatus(){return status;}
	public void setAccStatus(String status){this.status=status;}
	
	public String getPostInd(){return post_ind;}
	public void setPostInd(String post_ind){this.post_ind=post_ind;}
	
	public SignatureInstructionObject[] getSignatureInstruction(){return siob;}
	public void setSignatureInstruction(SignatureInstructionObject[] siob){this.siob=siob;}
	
	public int getCategory(){ return category;}
	public void setCategory(int no){this.category=no;}
	
	public int getSubCategory(){ return sub_category;}
	public void setSubCategory(int no){this.sub_category=no;}

	public String getFreezeInd(){return freeze_ind;}
	public void setFreezeInd(String freeze_ind){this.freeze_ind=freeze_ind;}
	
	public String getDefaultInd(){return default_ind;}
	public void setDefaultInd(String default_ind){this.default_ind=default_ind;}
	
	public double getCreditLimit(){ return creditlimit;}
	public void setCreditLimit(double amount){this.creditlimit=amount;}

	public String getLimitUpto(){return limit_upto;}
	public void setLimitUpto(String status){this.limit_upto=status;}

	public int getScrollno(){ return scroll_no;}
	public void setScrollno(int acc_no){this.scroll_no=acc_no;}
	
	public String getBranchname(){ return branch_name;}
	public void setBranchname(String b_name ){this.branch_name=b_name;}	

	public String getLastTrnDate(){ return trn_date;}
	public void setLastTrnDate(String trndate){this.trn_date=trndate;}
	
	public String getIntroName(){return intro_name;}
	public void setIntroName(String intro_name){this.intro_name=intro_name;}
	
	public String getAcOpenDate(){return ac_opendate;}	
	public void setAcOpenDate(String ac_opendate){this.ac_opendate=ac_opendate;}	
	
	
	
	public int getAcCategory(){ return ac_category;}
	public void setAcCategory(int ac_category){this.ac_category=ac_category;}
	
	public int getNomRegno(){ return nom_regno;}
	public void setNomRegno(int nom_regno){this.nom_regno=nom_regno;}
	
	public String getChBkIssue(){ return ch_bk_issue;}
	public void setChBkIssue(String ch_bk_issue){this.ch_bk_issue=ch_bk_issue;}
	
	public void setIntPblDate(String pbl_date){this.pbl_date=pbl_date; }
	public String getIntPblDate(){return pbl_date; }/**
	 * @param string
	 */
	//ship......added to get & set interest amount.....26/12/2005
	public double getIntAmount(){ return int_amount;}
	public void setIntAmount(double int_amount){this.int_amount=int_amount;}
	//


	public int getNo_of_jt_hldr() {
		return no_of_jt_hldr;
	}
	public void setNo_of_jt_hldr(int no_of_jt_hldr) {
		this.no_of_jt_hldr = no_of_jt_hldr;
	}
	
	//ship.....22/05/2006......for Deposit Accounts
	public int getDepositdays(){ return dep_days;}
	public void setDepositdays(int dep_days){this.dep_days=dep_days;}
	
	public int getDepositmths(){ return dep_mths;}
	public void setDepositmths(int dep_mths){this.dep_mths=dep_mths;}
	
	public int getDeposityrs(){ return dep_yrs;}
	public void setDeposityrs(int dep_yrs){this.dep_yrs=dep_yrs;}
	///////////
	//added by sanjeet
	public int getInst_no() {
		return inst_no;
	}
	public void setInst_no(int inst_no) {
		this.inst_no = inst_no;
	}
	
	public int getClose_ind() {
		return close_ind;
	}
	public void setClose_ind(int close_ind) {
		this.close_ind = close_ind;
	}
	public String getQdp_date() {
		return qdp_date;
	}
	public void setQdp_date(String qdp_date) {
		this.qdp_date = qdp_date;
	}
	
	public void setIsOtherBranch(boolean is_other_branch) {
		this.is_other_branch = is_other_branch;
	}
	
	public boolean isOtherBranch() {
		return is_other_branch;
	}
	
	public void setIsDirector(boolean is_director) {
		this.is_director = is_director;
	}
	
	public boolean isDirector() {
		return is_director;
	}
}





