package masterObject.backOffice;
import java.io.Serializable;
import masterObject.general.UserVerifier;
 
public class ChequeBookObject implements Serializable
{
	//public UserVerifier obj_userverifer=new UserVerifier();
	
	private UserVerifier obj_userverifer;
 	private String string_account_type,string_account_name,string_requested_date; 
 	private  int int_account_no,int_chequebook_no,int_firstcheque_no,int_lastcheque_no;
 	
 	//new name
 	private String accountType,accountName,requestedDate;
 	private int accountNo,chequeBookNo,firstChequeNo,lastChequeNo;
 	
 	public String getAccountType(){return accountType;}
	public void setAccountType(String acc_type){this.accountType=acc_type;}

	public String getAccountName(){return accountName;}
	public void setAccountName(String string_account_name){this.accountName=string_account_name;}

	public String getRequestedDate(){return requestedDate;}
	public void setRequestedDate(String string_requested_date){this.requestedDate=string_requested_date;}

	public int getAccountNo(){return accountNo;}
	public void setAccountNo(int int_account_no){this.accountNo=int_account_no;}
	
	public int getChequeBookNo(){return chequeBookNo;}
	public void setChequeBookNo(int int_chequebook_no){this.chequeBookNo=int_chequebook_no;}
	
	public int getFirstChequeNo(){return firstChequeNo;}
	public void setFirstChequeNo(int int_firstcheque_no){this.firstChequeNo=int_firstcheque_no;}

	public int getLastChequeNo(){return lastChequeNo;}
	public void setLastChequeNo(int int_lastcheque_no){this.lastChequeNo=int_lastcheque_no;}
	
	public UserVerifier getObj_userverifer() {return obj_userverifer;}
	public void setObj_userverifer( UserVerifier obj_userverifer) {	this.obj_userverifer = obj_userverifer;	}

		
}