package SRC.COM.SUNRISE.UTILITY.VALUEOBJECT;
//package src.com.sunrise.utility.valueobject;




import java.io.Serializable;

public class ModuleObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	private String lstdate;
	String moddesc,modabbre;
	double min_bal,min_amt,max_amt,min_amt_cheque,max_amt_cheque,min_amt_slip,max_amt_slip;
	int min_prd,no_trns,std_inst,insp_period,lst_voucscroll,pass_bk_lines,lst_acc_no,top_margin,lst_trf_scrollno,lst_voucher_no,rct_no,intto_day;
	String modcode,prop,lmt_hdg;
	
	
	
	// Added by Shiva
	String ln_module_code;

	
	private double penalty_rate;
	private int renewal_days;
	private int renewal_count;//ship......10/05/2006
	private double td_interest_rate;
	private int chq_validity_period;
    private float lnk_shares;

	public String getModuleDesc(){ return moddesc;}
	public void setModuleDesc(String modd){this.moddesc=modd;}
	
	
	public String getProperties(){ return prop;}
	public void setProperties(String modd){this.prop=modd;}


	public String getModuleAbbrv(){ return modabbre;}
	public void setModuleAbbrv(String moda){this.modabbre=moda;}

	public String getModuleCode(){ return modcode;}
	public void setModuleCode(String modc){this.modcode=modc;}

	public double getMinBal(){ return min_bal;}
	public void setMinBal(double modd){this.min_bal=modd;}
	
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

	public void setStdInst(int std_inst){ this.std_inst=std_inst;}
	public int getStdInst(){return std_inst;}
	
	/** Added by Deepa */
	public void setInspectionPeriod(int insp_period){ this.insp_period=insp_period;}
	public int getInspectionPeriod(){return insp_period;}
	
	public void setLastVoucherScrollno(int lst_voucscroll){ this.lst_voucscroll=lst_voucscroll;}
	public int getLastVoucherScrollno(){return lst_voucscroll;}
	
	/** */
	
	//Added by Shiva
	
	public void setLoanModuleCode(String code)
	{
		ln_module_code=code;
	}
	
	public String getLoanModuleCode()
	{
		return ln_module_code;
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
	public int getMaxRenewalCount(){ return renewal_count;}
	public void setMaxRenewalCount(int renewal_count){this.renewal_count=renewal_count;}
	/////////////
	
//	Added by Karthi-->14/07/2006
	
	public void setLastAccNo(int acc_no){this.lst_acc_no=acc_no;}
	public int getLastAccNo(){return lst_acc_no;}
	
	public void setTopmargin(int top_margin){this.top_margin=top_margin;}
	public int getTopmargin(){return top_margin;}
	
	public void setLastVoucherNo(int vouch_no){this.lst_voucher_no=vouch_no;}
	public int getLastVoucherNo(){return lst_voucher_no;}
	
	public void setLastTRFScrollno(int scroll_no){this.lst_trf_scrollno=scroll_no;}
	public int getLastTRFScrollno(){return lst_trf_scrollno;}
	
	public void setRCTNo(int rct_no){this.rct_no=rct_no;}
	public int getRCTNo(){return rct_no;}
	
	public int getIntto_day() {
		return intto_day;
	}
	public void setIntto_day(int intto_day) {
		this.intto_day = intto_day;
	}
}

