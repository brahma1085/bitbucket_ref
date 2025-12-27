package masterObject.backOffice;
import java.io.Serializable;

import masterObject.general.GLMasterObject;
import masterObject.general.UserVerifier;
 
public class VoucherDataObject implements Serializable
{
	GLMasterObject glm =new GLMasterObject();
	public UserVerifier obj_userverifier=new UserVerifier();
	private String string_voucher_type,string_voucher_mode,string_gl_type,string_module_account_type,transactionDate,string_cheque_date,cdIndicator,narration,string_transaction_type,name,glname;
	private String modAbbr;
	private  int voucherNo,glCode,int_cheque_no,moduleAccountNo;
	private  double transactionAmount;
	
 	
 	public String getVoucherType()
 	{
 		return string_voucher_type;
 		}
	public void setVoucherType(String string_voucher_type){this.string_voucher_type=string_voucher_type;}

	public int getVoucherNo(){return voucherNo;}
	public void setVoucherNo(int int_voucher_no){this.voucherNo=int_voucher_no;}
		
	public String getVoucherMode(){return string_voucher_mode;}
	public void setVoucherMode(String string_voucher_mode){this.string_voucher_mode=string_voucher_mode;}
	
	public String getTransactionDate(){return transactionDate;}
	public void setTransactionDate(String transaction_date){this.transactionDate=transaction_date;}
		
	public String getTransactionType(){return string_transaction_type;}
    public void setTransactionType(String string_transaction_type){this.string_transaction_type=string_transaction_type;}
    
    public String getModuleAccountType(){return string_module_account_type;}
	public void setModuleAccountType(String string_module_account_type){this.string_module_account_type=string_module_account_type;}
	
	public String getGlType(){return string_gl_type;}
	public void setGlType(String string_gl_type){this.string_gl_type=string_gl_type;}

	public int getGlCode(){return glCode;}
	public void setGlCode(int int_gl_code){this.glCode=int_gl_code;}
	
	public double getTransactionAmount(){return transactionAmount;}
	public void setTransactionAmount(double double_transaction_amount){this.transactionAmount=double_transaction_amount;}
	
	public int getChequeNo(){return int_cheque_no;}
	public void setChequeNo(int int_cheque_no){this.int_cheque_no=int_cheque_no;}
	
	public String getChequeDate(){return string_cheque_date;}
	public void setChequeDate(String string_cheque_date){this.string_cheque_date=string_cheque_date;}
	
	public String getCdIndicator(){return cdIndicator;}
	public void setCdIndicator(String string_cd_indicator){this.cdIndicator=string_cd_indicator;}
	
	public int getModuleAccountNo(){return moduleAccountNo;}
	public void setModuleAccountNo(int int_module_account_no){this.moduleAccountNo=int_module_account_no;}
	
	public String getNarration(){return narration;}
	public void setNarration(String string_narration){this.narration=string_narration;}

	public String getName(){return name;}
	public void setName(String name){this.name=name;}
	
	public UserVerifier getObj_userverifier() {
		return obj_userverifier;
	}
	public void setObj_userverifier(UserVerifier obj_userverifier) {
		this.obj_userverifier = obj_userverifier;
	}
	public GLMasterObject getGlm() {
		return glm;
	}
	public void setGlm(GLMasterObject glm) {
		this.glm = glm;
	}
	public String getGlname() {
		return glname;
	}
	public void setGlname(String glname) {
		this.glname = glname;
	}
	public String getModAbbr() {
		return modAbbr;
	}
	public void setModAbbr(String modAbbr) {
		this.modAbbr = modAbbr;
	}
	
	

	
	
	}