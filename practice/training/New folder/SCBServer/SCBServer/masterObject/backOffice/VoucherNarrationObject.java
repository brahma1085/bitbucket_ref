package masterObject.backOffice;
import java.io.Serializable;
import masterObject.general.UserVerifier;

public class VoucherNarrationObject implements Serializable
{

	private  String string_voucher_type,string_transaction_date,string_narration;
	private  int int_voucher_no;
 	
	public UserVerifier obj_userverifier=new UserVerifier();

	public String getVoucherType(){return string_voucher_type;}
	public void setVoucherType(String string_voucher_type){this.string_voucher_type=string_voucher_type;}

	public int getVoucherNo(){return int_voucher_no;}
	public void setVoucherNo(int int_voucher_no){this.int_voucher_no=int_voucher_no;}
	
	public String getTransactionDate(){return string_transaction_date;}
	public void setTransactionDate(String string_transaction_date){this.string_transaction_date=string_transaction_date;}

	public String getNarration(){return string_narration;}
	public void setNarration(String string_narration){this.string_narration=string_narration;}

	
}