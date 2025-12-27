package masterObject.backOffice;
 
import masterObject.general.UserVerifier;

public class PygmyObject implements java.io.Serializable
{
    public UserVerifier obj_userverifier=new UserVerifier();
    
    private  int accountNo,int_ref_no;
    private   String string_pygmy_type,string_name,string_transaction_mode,string_cd_ind,narration;
    private  double transactionAmount;

    public int getAccountNo(){return accountNo;}
	public void setAccountNo(int int_account_no){this.accountNo=int_account_no;}

	public int getReferenceNo(){return int_ref_no;}
	public void setReferenceNo(int int_ref_no){this.int_ref_no=int_ref_no;}
	
	public String getCdIndicator(){return string_cd_ind;}
	public void setCdIndicator(String string_cd_ind){this.string_cd_ind=string_cd_ind;}

	public String getName(){return string_name;}
	public void setName(String string_name){this.string_name=string_name;}
	
	public String getPygmyType(){return string_pygmy_type;}
	public void setPygmyType(String string_pygmy_type){this.string_pygmy_type=string_pygmy_type;}
	
	public String getTransactionMode(){return string_transaction_mode;}
	public void setTransactionMode(String string_transaction_mode){this.string_transaction_mode=string_transaction_mode;}
	
	public String getNarration(){return narration;}
	public void setNarration(String string_narration){this.narration=string_narration;}
	
	public double getTransactionAmount(){return transactionAmount;}
	public void setTransactionAmount(double double_amount){this.transactionAmount=double_amount;}
	
	public UserVerifier getObj_userverifier() {
		return obj_userverifier;
	}
	public void setObj_userverifier(UserVerifier obj_userverifier) {
		this.obj_userverifier = obj_userverifier;
	}

}