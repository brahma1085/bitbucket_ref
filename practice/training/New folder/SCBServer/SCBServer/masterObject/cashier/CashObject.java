package masterObject.cashier;
import masterObject.general.UserVerifier;

import java.io.Serializable;

public class CashObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....02/12/2006
    
    private int int_scroll_no,int_token_no,int_trn_seq,int_vch_no;
    private String string_acc_no,string_acc_type,string_acc_name,string_trn_type,string_cd_ind;
    private String string_vch_type,string_locker_type,string_usertml,string_gl_ref_code,string_oth_tml,string_tml_no;
    private double double_amount,comm_amt,double_run_bal;
    private boolean curr_denom = false;
    
    //ship.....22/05/2006
    private double double_dob,double_dcb,double_dr,double_dp;
    ////////
    
    //ship.....28/10/2005
    private int string_share_cat;
    //
    
    //ship.....02/12/2005
    private int string_chq_no;
    //
    
    //ship.....18/11/2005
    private String string_cust_ac_type,string_cust_ac_no,string_cust_code,string_po_favour_name;
    //
    
    private int int_r1000,int_r500,int_r100,int_r50,int_r20,int_r10,int_r5,int_r2,int_r1;
    private int int_p1000,int_p500,int_p100,int_p50,int_p20,int_p10,int_p5,int_p2,int_p1;
    private double double_rcoins,double_pcoins;
    private String string_trn_date,string_attached,string_trn_time;
    private boolean insufficient_flag=false;
    
	public boolean isInsufficientFlag(){return insufficient_flag;}
	public void setInsufficientFlag(boolean flag){this.insufficient_flag=flag;}

	public String getOthtml(){return string_oth_tml;}
	public void setOthtml(String string_oth_tml){this.string_oth_tml=string_oth_tml;}
	
	public String getTerminalNo(){return string_tml_no;}
	public void setTerminalNo(String string_tml_no){this.string_tml_no=string_tml_no;}	
	
	public double getRunbal(){return double_run_bal;}
	public void setRunbal(double double_run_bal){this.double_run_bal=double_run_bal;}
	
	public UserVerifier uv=new UserVerifier();
	private int int_custid;

	public int getR1000(){return int_r1000;}
	public void setR1000(int int_r1000){this.int_r1000=int_r1000;}

	public int getR500(){return int_r500;}
	public void setR500(int int_r500){this.int_r500=int_r500;}

	public int getR100(){return int_r100;}
	public void setR100(int int_r100){this.int_r100=int_r100;}

	public int getR50(){return int_r50;}
	public void setR50(int int_r50){this.int_r50=int_r50;}

	public int getR20(){return int_r20;}
	public void setR20(int int_r20){this.int_r20=int_r20;}

	public int getR10(){return int_r10;}
	public void setR10(int int_r10){this.int_r10=int_r10;}

	public int getR5(){return int_r5;}
	public void setR5(int int_r5){this.int_r5=int_r5;}

	public int getR2(){return int_r2;}
	public void setR2(int int_r2){this.int_r2=int_r2;}

	public int getR1(){return int_r1;}
	public void setR1(int int_r1){this.int_r1=int_r1;}

	public double getRcoins(){return double_rcoins;}
	public void setRcoins(double double_rcoins){this.double_rcoins=double_rcoins;}

	public int getP1000(){return int_p1000;}
	public void setP1000(int int_p1000){this.int_p1000=int_p1000;}

	public int getP500(){return int_p500;}
	public void setP500(int int_p500){this.int_p500=int_p500;}

	public int getP100(){return int_p100;}
	public void setP100(int int_p100){this.int_p100=int_p100;}

	public int getP50(){return int_p50;}
	public void setP50(int int_p50){this.int_p50=int_p50;}

	public int getP20(){return int_p20;}
	public void setP20(int int_p20){this.int_p20=int_p20;}

	public int getP10(){return int_p10;}
	public void setP10(int int_p10){this.int_p10=int_p10;}

	public int getP5(){return int_p5;}
	public void setP5(int int_p5){this.int_p5=int_p5;}

	public int getP2(){return int_p2;}
	public void setP2(int int_p2){this.int_p2=int_p2;}

	public int getP1(){return int_p1;}
	public void setP1(int int_p1){this.int_p1=int_p1;}

	public double getPcoins(){return double_pcoins;}
	public void setPcoins(double double_pcoins){this.double_pcoins=double_pcoins;}

	public int getScrollno(){ return int_scroll_no;}
	public void setScrollno(int int_scroll_no){this.int_scroll_no=int_scroll_no;}

	public String getAccno(){ return string_acc_no;}
	public void setAccno(String string_acc_no){this.string_acc_no=string_acc_no;}

	public int getTokenno(){ return int_token_no;}
	public void setTokenno(int int_token_no){this.int_token_no=int_token_no;}

	public int getTrnseq(){ return int_trn_seq;}
	public void setTrnseq(int int_trn_seq){this.int_trn_seq=int_trn_seq;}

	public String getTrndate(){ return string_trn_date;}
	public void setTrndate(String string_acc_type){this.string_trn_date=string_acc_type;}
	
	//ship.....21/06/2006
	public String getTrntime(){ return string_trn_time;}
	public void setTrntime(String string_trn_time){this.string_trn_time=string_trn_time;}
	/////////////

	public String getAcctype(){ return string_acc_type;}
	public void setAcctype(String string_acc_type){this.string_acc_type=string_acc_type;}

	public String getAccname(){ return string_acc_name;}
	public void setAccname(String string_acc_name){this.string_acc_name=string_acc_name;}

	public String getTrntype(){ return string_trn_type;}
	public void setTrntype(String string_trn_type){this.string_trn_type=string_trn_type;}

	public String getCdind(){ return string_cd_ind;}
	public void setCdind(String string_cd_ind){this.string_cd_ind=string_cd_ind;}

	public String getVchtype(){ return string_vch_type;}
	public void setVchtype(String string_vch_type){this.string_vch_type=string_vch_type;}

	public int getVchno(){ return int_vch_no;}
	public void setVchno(int int_vch_no){this.int_vch_no=int_vch_no;}

	public String getLockertype(){ return string_locker_type;}
	public void setLockertype(String string_locker_type){this.string_locker_type=string_locker_type;}
	
	//ship.....28/10/2005
	public int getSharecat(){ return string_share_cat;}
	public void setSharecat(int share_cat){this.string_share_cat=share_cat;}
	//
	
	//ship.....02/12/2005
	public int getChqno(){ return string_chq_no;}
	public void setChqno(int chq_no){this.string_chq_no=chq_no;}
	//

	//ship.....18/11/2005
	public String getCustAcctype(){ return string_cust_ac_type;}
	public void setCustAcctype(String string_cust_ac_type){this.string_cust_ac_type=string_cust_ac_type;}
	
	public String getCustAccno(){ return string_cust_ac_no;}
	public void setCustAccno(String string_cust_ac_no){this.string_cust_ac_no=string_cust_ac_no;}
	
	public String getCustCode(){ return string_cust_code;}
	public void setCustCode(String string_cust_code){this.string_cust_code=string_cust_code;}
	
	public String getPOName(){ return string_po_favour_name;}
	public void setPOName(String string_po_favour_name){this.string_po_favour_name=string_po_favour_name;}
	//
	
	public double getAmount(){ return double_amount;}
	public void setAmount(double double_amount){this.double_amount=double_amount;}

	public double getCommamt(){ return comm_amt;}
	public void setCommamt(double comm_amt){this.comm_amt=comm_amt;}

	public String getGLRefCode(){return string_gl_ref_code;}
	public void setGLRefCode(String code){this.string_gl_ref_code=code;}
	
	public String getAttached(){return string_attached;}
	public void setAttached(String string_attached){this.string_attached=string_attached;}

	public String getUserTml(){return string_usertml;}
	public void setUserTml(String string_usertml){this.string_usertml=string_usertml;}
	
	public void setCustomerId(int int_custid) {this.int_custid=int_custid;}
	public int getCustomerId(){return int_custid;}
		
	//ship......22/05/2006
	public double getDayOpeningBalance(){return double_dob;}
	public void setDayOpeningBalance(double double_dob){this.double_dob=double_dob;}
	
	public double getDayClosingBalance(){return double_dcb;}
	public void setDayClosingBalance(double double_dcb){this.double_dcb=double_dcb;}
	
	public double getDayReceipts(){return double_dr;}
	public void setDayReceipts(double double_dr){this.double_dr=double_dr;}
	
	public double getDayPayments(){return double_dp;}
	public void setDayPayments(double double_dp){this.double_dp=double_dp;}
	/////////////
	
	//ship......14/09/2006
	public boolean getCurrDenom(){return curr_denom;}
	public void setCurrDenom(boolean denom){this.curr_denom=denom;}
	///////////
}
