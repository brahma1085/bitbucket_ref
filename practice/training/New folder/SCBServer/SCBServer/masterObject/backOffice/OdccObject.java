package masterObject.backOffice;
import java.io.Serializable;

 
public class OdccObject implements Serializable
{
	private  String accountType,accountOpenDate,string_last_transaction_date,accountName; 
	private  int accountNo;
	private  double creditLimit,accountBalance;
 	
 	public String getAccountType(){return accountType;}
	public void setAccountType(String string_account_type){this.accountType=string_account_type;}

	public String getAccountName(){return accountName;}
	public void setAccountName(String string_account_name){this.accountName=string_account_name;}

	public int getAccountNo(){return accountNo;}
	public void setAccountNo(int string_account_no){this.accountNo=string_account_no;}
	
	public String getAccountOpenDate(){return accountOpenDate;}
	public void setAccountOpenDate(String string_account_open_date){this.accountOpenDate=string_account_open_date;}
	
	public String getLastTransactionDate(){return string_last_transaction_date;}
	public void setLastTransactionDate(String string_last_transaction_date){this.string_last_transaction_date=string_last_transaction_date;}
	
	public double getCreditLimit(){return creditLimit;}
	public void setCreditLimit(double double_creditlimit){this.creditLimit=double_creditlimit;}
	
	public double getAccountBalance(){return accountBalance;}
	public void setAccountBalance(double double_account_balance){this.accountBalance=double_account_balance;}
	

}