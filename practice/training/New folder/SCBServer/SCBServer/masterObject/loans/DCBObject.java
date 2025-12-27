/*  
 * Created on Dec 20, 2004
 * 
 */
package masterObject.loans;

import java.io.Serializable;

/**
 * @author user 
 */
public class DCBObject implements Serializable {
	String ln_ac_ty,sanc_dt,de_tml,de_user,de_dt_time;
	int ln_ac_no,pps_code,dft_mths,stage_cd;
	double sanc_amt,loanBalance,principalArr,int_arr,pint_arr,ochg_arr;
	double adv_paid,pr_dmd,int_dmd,ochg_dmd,pr_coll,int_coll,pint_coll;
	double ochg_coll,adv_coll,cur_disb;
	String month;
	
	public void setMonth(String month){this.month=month;}
	public String getMonth(){return month;}
	
	public void setAccType(String acty){ln_ac_ty=acty;}
	public String getAccType(){return ln_ac_ty;}
	
	public void setAccNo(int acty){ln_ac_no=acty;}
	public int getAccNo(){return ln_ac_no;}
	
	public void setPurposeCode(int acty){pps_code=acty;}
	public int getPurposeCode(){return pps_code;}

	public void setSanctionDate(String acty){sanc_dt=acty;}
	public String getSanctionDate(){return sanc_dt;}

	public void setSanctionedAmount(double amt){sanc_amt=amt;}
	public double getSanctionedAmount(){return sanc_amt;}
	
	public void setLoanBalance(double amt){loanBalance=amt;}
	public double getLoanBalance(){return loanBalance;}
	
	public void setPrincipalArr(double amt){principalArr=amt;}
	public double getPrincipalArr(){return principalArr;}
	
	public void setIntrArr(double amt){int_arr=amt;}
	public double getIntrArr(){return int_arr;}

	public void setPenalIntArr(double amt){pint_arr=amt;}
	public double getPenalIntArr(){return pint_arr;}
	
	public void setOtherArr(double amt){ochg_arr=amt;}
	public double getOtherArr(){return ochg_arr;}
	
	public void setAdvPaid(double amt){adv_paid=amt;}
	public double getAdvPaid(){return adv_paid;}
	
	public void setPrincipalDemand(double amt){pr_dmd=amt;}
	public double getPrincipalDemand(){return pr_dmd;}
	
	public void setIntrDemand(double amt){int_dmd=amt;}
	public double getIntrDemand(){return int_dmd;}
	
	public void setOtherDemand(double amt){ochg_dmd=amt;}
	public double getOtherDemand(){return ochg_dmd;}
	
	public void setPrincipalCollected(double amt){pr_coll=amt;}
	public double getPrincipalCollected(){return pr_coll;}
	
	public void setIntrCollected(double amt){int_coll=amt;}
	public double getIntrCollected(){return int_coll;}
	
	public void setPenalCollected(double amt){pint_coll=amt;}
	public double getPenalCollected(){return pint_coll;}
	
	public void setOtherCollected(double amt){ochg_coll=amt;}
	public double getOtherCollected(){return ochg_coll;}
	
	public void setAdvCollected(double amt){adv_coll=amt;}
	public double getAdvCollected(){return adv_coll;}
	
	public void setCurrentDisb(double amt){cur_disb=amt;}
	public double getCurrentDisb(){return cur_disb;}
	
	public void setDefaultedMonths(int amt){dft_mths=amt;}
	public int getDefaultedMonths(){return dft_mths;}
	
	public void setStageCode(int amt){stage_cd=amt;}
	public int getStageCode(){return stage_cd;}	
}