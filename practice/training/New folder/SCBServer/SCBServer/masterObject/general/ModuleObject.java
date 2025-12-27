package masterObject.general;
import java.io.Serializable;

public class ModuleObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	private String lstdate;
	String moduleDesc,moduleAbbrv;
	double minBal,min_amt,max_amt,min_amt_cheque,max_amt_cheque,min_amt_slip,max_amt_slip;
	int min_prd,no_trns,stdInst,inspectionPeriod,lastVoucherScrollno,pass_bk_lines,lastAccNo,topmargin,lastTRFScrollno,lastVoucherNo,rCTNo;
	String moduleCode,prop,lmt_hdg;
	
	int intto_day;
	
	
	
	// Added by Shiva
	String loanModuleCode;

	UserVerifier uv = new UserVerifier();
	private double penalty_rate;
	private int renewal_days;
	private int maxRenewalCount;//ship......10/05/2006
	private double td_interest_rate;
	private int chq_validity_period;
    private float lnk_shares;

	public String getModuleDesc(){ return moduleDesc;}
	public void setModuleDesc(String modd){this.moduleDesc=modd;}
	
	
	public String getProperties(){ return prop;}
	public void setProperties(String modd){this.prop=modd;}


	public String getModuleAbbrv(){ return moduleAbbrv;}
	public void setModuleAbbrv(String moda){this.moduleAbbrv=moda;}

	public String getModuleCode(){ return moduleCode;}
	public void setModuleCode(String modc){this.moduleCode=modc;}

	public double getMinBal(){ return minBal;}
	public void setMinBal(double modd){this.minBal=modd;}
	
	public int getMinPeriod(){ return min_prd;}
	public void setMinPeriod(int modd){this.min_prd=modd;}
	
	public double getMinAmount(){ return min_amt;}
	public void setMinAmount(double amount){this.min_amt=amount;}
	
	public double getMaxAmount(){ return max_amt;}
	public void setMaxAmount(double amount){this.max_amt=amount;}
	
	public int getNoOfTrnsPerMonth(){ return no_trns;}
	public void setNoOfTrnsPerMonth(int amount){this.no_trns=amount;}
	
	public String getLastInterestDate() {return lstdate;}
	public void setLastInterestDate(String date) {lstdate=date;}
	
	public double getPenaltyRate(){ return penalty_rate;}
	public void setPenaltyRate(double penalty_rate){this.penalty_rate=penalty_rate;}
	
	public int getPassBkLines(){ return pass_bk_lines;}
	public void setPassBkLines(int pass_bk_lines){this.pass_bk_lines=pass_bk_lines;}
	
	public double getTDInterestRate(){ return td_interest_rate;}
	public void setTDInterestRate(double td_interest_rate){this.td_interest_rate=td_interest_rate;}
	
	public int getMaxRenewalDays(){ return renewal_days;}
	public void setMaxRenewalDays(int renewal_days){this.renewal_days=renewal_days;}
	
	public double getChequeMinAmount(){ return min_amt_cheque;}
	public void setChequeMinAmount(double amount){this.min_amt_cheque=amount;}

	public double getSlipMinAmount(){ return min_amt_slip;}
	public void setSlipMinAmount(double amount){this.min_amt_slip=amount;}
	
	public double getChequeMaxAmount(){ return max_amt_cheque;}
	public void setChequeMaxAmount(double amount){this.max_amt_cheque=amount;}

	public double getSlipMaxAmount(){ return max_amt_slip;}
	public void setSlipMaxAmount(double amount){this.max_amt_slip=amount;}

	public void setChequeValidityPeriod(int double1) {this.chq_validity_period=double1;}
	public int getChequeValidityPeriod(){ return chq_validity_period;}
	
	public void setLinkShares(float lnk_shares) {this.lnk_shares=lnk_shares;}
	public float getLinkShares() {return this.lnk_shares;}

	public void setStdInst(int std_inst){ this.stdInst=std_inst;}
	public int getStdInst(){return stdInst;}
	
	/** Added by Deepa */
	public void setInspectionPeriod(int insp_period){ this.inspectionPeriod=insp_period;}
	public int getInspectionPeriod(){return inspectionPeriod;}
	
	public void setLastVoucherScrollno(int lst_voucscroll){ this.lastVoucherScrollno=lst_voucscroll;}
	public int getLastVoucherScrollno(){return lastVoucherScrollno;}
	
	/** */
	
	//Added by Shiva
	
	public void setLoanModuleCode(String code)
	{
		loanModuleCode=code;
	}
	
	public String getLoanModuleCode()
	{
		return loanModuleCode;
	}
	
	// Added by Sanjeet
	
	public String getlmt_hdg()
	{
		return lmt_hdg;
		
	}
	
	public void setlmt_hdg(String limit)
	{
		this.lmt_hdg=limit;
		
	}
	
	//ship.....10/05/2006
	public int getMaxRenewalCount(){ return maxRenewalCount;}
	public void setMaxRenewalCount(int renewal_count){this.maxRenewalCount=renewal_count;}
	/////////////
	
//	Added by Karthi-->14/07/2006
	
	public void setLastAccNo(int acc_no){this.lastAccNo=acc_no;}
	public int getLastAccNo(){return lastAccNo;}
	
	public void setTopmargin(int top_margin){this.topmargin=top_margin;}
	public int getTopmargin(){return topmargin;}
	
	public void setLastVoucherNo(int vouch_no){this.lastVoucherNo=vouch_no;}
	public int getLastVoucherNo(){return lastVoucherNo;}
	
	public void setLastTRFScrollno(int scroll_no){this.lastTRFScrollno=scroll_no;}
	public int getLastTRFScrollno(){return lastTRFScrollno;}
	
	public void setRCTNo(int rct_no){this.rCTNo=rct_no;}
	public int getRCTNo(){return rCTNo;}
	public int getIntto_day() {
		return intto_day;
	}
	public void setIntto_day(int intto_day) {
		this.intto_day = intto_day;
	}
	
}
