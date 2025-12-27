package masterObject.cashier;
import java.io.Serializable;
import masterObject.general.UserVerifier;

public class VoucherDataObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....02/12/2006
    
	public UserVerifier obj_userverifier=new UserVerifier();

	private String string_voucher_type,string_voucher_mode,string_gl_type,string_module_account_type,transaction_date,string_narration;
	private String string_cheque_date,string_cd_indicator,string_ve_user,string_de_user,string_de_dt_time,string_ve_dt_time;
	private int int_voucher_no,int_gl_code,int_cheque_no,int_module_account_no;
	private double double_transaction_amount;
 	
 	public String getVoucherType(){return string_voucher_type;}
	public void setVoucherType(String string_voucher_type){this.string_voucher_type=string_voucher_type;}

	public int getVoucherNo(){return int_voucher_no;}
	public void setVoucherNo(int int_voucher_no){this.int_voucher_no=int_voucher_no;}
   		
	public String getVoucherMode(){return string_voucher_mode;}
	public void setVoucherMode(String string_voucher_mode){this.string_voucher_mode=string_voucher_mode;}
	
	public String getTransactionDate(){return transaction_date;}
	public void setTransactionDate(String transaction_date){this.transaction_date=transaction_date;}
		
	public String getModuleAccountType(){return string_module_account_type;}
	public void setModuleAccountType(String string_module_account_type){this.string_module_account_type=string_module_account_type;}
	
	public String getGlType(){return string_gl_type;}
	public void setGlType(String string_gl_type){this.string_gl_type=string_gl_type;}

	public int getGlCode(){return int_gl_code;}
	public void setGlCode(int int_gl_code){this.int_gl_code=int_gl_code;}
	
	public double getTransactionAmount(){return double_transaction_amount;}
	public void setTransactionAmount(double double_transaction_amount){this.double_transaction_amount=double_transaction_amount;}
	
	public int getChequeNo(){return int_cheque_no;}
	public void setChequeNo(int int_cheque_no){this.int_cheque_no=int_cheque_no;}
	
	public String getChequeDate(){return string_cheque_date;}
	public void setChequeDate(String string_cheque_date){this.string_cheque_date=string_cheque_date;}
	
	public String getCdIndicator(){return string_cd_indicator;}
	public void setCdIndicator(String string_cd_indicator){this.string_cd_indicator=string_cd_indicator;}
	
	public int getModuleAccountNo(){return int_module_account_no;}
	public void setModuleAccountNo(int int_module_account_no){this.int_module_account_no=int_module_account_no;}
	
	public String getVerifiedBy(){return string_ve_user;}
	public void setVerifiedBy(String string_ve_user){this.string_ve_user=string_ve_user;}
	
	public String getDataEntryUser(){return string_de_user;}
	public void setDataEntryUser(String string_de_user){this.string_de_user=string_de_user;}
	
	public String getDataEntryUserDateTime(){return string_de_dt_time;}
	public void setDataEntryUserDateTime(String string_de_dt_time){this.string_de_dt_time=string_de_dt_time;}
	
	public String getVerifiedUserDateTime(){return string_ve_dt_time;}
	public void setVerifiedUserDateTime(String string_ve_dt_time){this.string_ve_dt_time=string_ve_dt_time;}
	
	public String getNarration(){return string_narration;}
	public void setNarration(String string_narration){this.string_narration=string_narration;}

}