package masterObject.backOffice;
import java.io.Serializable;
import masterObject.general.UserVerifier;




public class ChequeNoObject implements Serializable
{
	
	public UserVerifier obj_userverifer=new UserVerifier();

	private  String string_account_type,string_account_name,string_next_cheque_date,string_post_dated,string_cheque_delete,string_stop_payment; 
	private  int int_account_no,int_cheque_no;
	
	//new names
	private String accountType,accountName,nextChequeDate,modAbbrv;
	private int accountNo,chequeNo;
 	
 	public String getAccountType(){return accountType;}
	public void setAccountType(String string_account_type){this.accountType=string_account_type;}

	public int getAccountNo(){return accountNo;}
	public void setAccountNo(int int_account_no){this.accountNo=int_account_no;}
	
	public String getAccountName(){return accountName;}
	public void setAccountName(String string_account_name){this.accountName=string_account_name;}

	public int getChequeNo(){return chequeNo;}
	public void setChequeNo(int int_cheque_no){this.chequeNo=int_cheque_no;}
	
	public String getNextChequeDate(){return nextChequeDate;}
	public void setNextChequeDate(String string_next_cheque_date){this.nextChequeDate=string_next_cheque_date;}

	public String getPostDated(){return string_post_dated;}
	public void setPostDated(String string_post_dated){this.string_post_dated=string_post_dated;}
	
	public String getChequeDeleted(){return string_cheque_delete;}
	public void setChequeDeleted(String string_cheque_delete){this.string_cheque_delete=string_cheque_delete;}
	
	public String getStopPayment(){return string_stop_payment;}
	public void setStopPayment(String string_stop_payment){this.string_stop_payment=string_stop_payment;}
	
	public String getModAbbrv() {return modAbbrv;}
	public void setModAbbrv(String modAbbrv) {this.modAbbrv = modAbbrv;}
		
}