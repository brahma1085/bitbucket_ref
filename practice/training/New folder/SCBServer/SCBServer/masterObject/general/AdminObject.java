/*
 * Created on Sep 13, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package masterObject.general;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AdminObject implements java.io.Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	private String ac_type,key_desc,gl_type,tran_type,tran_desc,mod_desc,mod_abbr,min_period,pygmy_rate,penalty_rate;
	private String comm_rate,part_withdraw,max_amount,min_amount,reciept_credit,reciept_debit,payment_credit,payment_debit,interest_credit,interest_debit,secdep_rate;
	private int code,gl_code;
	
   public void setAcType(String ac_type){this.ac_type=ac_type;}	
   public String getAcType(){return ac_type;}
   
   public void setCode(int code){this.code=code;}
   public int getCode(){return code;}
   
   public void setKeyDesc(String key_desc){this.key_desc=key_desc;}
   public String getKeyDesc(){return key_desc;}
   
   public void setGl_code(int gl_code){this.gl_code=gl_code;}
   public int getGl_code(){return gl_code;}
   
   public void setGl_type(String gl_type){this.gl_type=gl_type;}
   public String getGl_type(){return gl_type;}
   
   public void setTran_type(String tran_type){this.tran_type=tran_type;}
   public String getTran_type(){return tran_type;}
   
   public void setTran_desc(String tran_desc){this.tran_desc=tran_desc;}
   public String getTran_desc(){return tran_desc;}
   
   public void setModuleDesc(String mod_desc){this.mod_desc=mod_desc;}
   public String getModuleDesc(){return mod_desc;}
   
   public void setModuleAbbr(String mod_abbr){this.mod_abbr=mod_abbr;}
   public String getModuleAbbr(){return mod_abbr;}
   
   public void setMinPeriod(String min_period){this.min_period=min_period;}
   public String getMinPerid(){return min_period;}
   
   public void setPygmyRate(String pygmy_rate){this.pygmy_rate=pygmy_rate;}
   public String getPygmyRate(){return pygmy_rate;}
   
   public void setPenaltyRate(String penalty_rate){this.penalty_rate=penalty_rate;}
   public String getPenaltyRate(){return penalty_rate;}
   
   public void setCommissionRate(String comm_rate){this.comm_rate=comm_rate;}
   public String getCommissionRate(){return comm_rate;}
   
   public void setPartWithdraw(String part_withdraw){this.part_withdraw=part_withdraw;}
   public String getPartWithdraw(){return part_withdraw;}
   
   public void setMaxAmount(String max_amount){this.max_amount=max_amount;}
   public String getMaxAmount(){return max_amount;}
   
   public void setMinAmount(String min_amount){this.min_amount=min_amount;}
   public String getMinAmount(){return min_amount;}
   
   public void setReciept_credit(String reciept_credit){this.reciept_credit=reciept_credit;}
   public String getReciept_credit(){return reciept_credit;}
   
   public void setReciept_debit(String reciept_debit){this.reciept_debit=reciept_debit;}
   public String getReciept_debit(){return reciept_debit;}
   
   public void setPayment_credit(String payment_credit){this.payment_credit=payment_credit;}
   public String getPayment_credit(){return payment_credit;}
   
   public void setPayment_debit(String payment_debit){this.payment_debit=payment_debit;}
   public String getPayment_debit(){return payment_debit;}
   
   public void setInterest_credit(String interest_credit){this.interest_credit=interest_credit;}
   public String getInterest_credit(){return interest_credit;}
   
   public void setInterest_debit(String interest_debit){this.interest_debit=interest_debit;}
   public String getInterest_debit(){return interest_debit;}
   
   public void setSecurityDepRate(String secdep_rate){this.secdep_rate=secdep_rate;}
   public String getSecurityDepRate(){return secdep_rate;}
}
