package masterObject.backOffice;
import java.io.Serializable;

 
public class OpenedClosedInoperatedAccountObject implements Serializable
{
	private  String accountType,accountName,accountAddress,string_cheque_book_required,openDate,string_account_status,closeDate,string_frz_ind,modAbrr; 
	private  int accountNo,accountCategory,int_cust_type;
 	
 	public String getAccountType(){return accountType;}
	public void setAccountType(String string_account_type){this.accountType=string_account_type;}

	public int getAccountNo(){return accountNo;}
	public void setAccountNo(int int_account_no){this.accountNo=int_account_no;}
	
	public String getAccountName(){return accountName;}
	public void setAccountName(String string_account_name){this.accountName=string_account_name;}

	public String getAccountAddress(){return accountAddress;}
	public void setAccountAddress(String string_account_address){this.accountAddress=string_account_address;}

	public String getChequeBookRequired(){return string_cheque_book_required;}
	public void setChequeBookRequired(String string_cheque_book_required){this.string_cheque_book_required=string_cheque_book_required;}
	
	public String getOpenDate(){return openDate;}
	public void setOpenDate(String string_account_open_date){this.openDate=string_account_open_date;}
	
	public String getCloseDate(){return closeDate;}
	public void setCloseDate(String string_account_close_date){this.closeDate=string_account_close_date;}
	
	public String getAccountStatus(){return string_account_status;}
	public void setAccountStatus(String string_account_status){this.string_account_status=string_account_status;}
	
	public String getFreezeInd(){return string_frz_ind;}
	public void setFreezeInd(String string_frz_ind){this.string_frz_ind=string_frz_ind;}
	
	public int getAccountCategory(){return accountCategory;}
	public void setAccountCategory(int int_account_category) {this.accountCategory=int_account_category;}
	
	public int getCustType(){return int_cust_type;}
	public void setcustType(int int_cust_type) {this.int_cust_type=int_cust_type;}
	
	public String getModAbrr() {return modAbrr;	}
	public void setModAbrr(String modAbrr) { this.modAbrr = modAbrr;}
	
}