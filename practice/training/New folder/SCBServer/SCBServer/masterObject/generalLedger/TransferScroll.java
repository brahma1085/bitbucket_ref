/*
 * Created on May 16, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package masterObject.generalLedger;
import java.io.Serializable;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransferScroll implements Serializable
{

    int ref_no,frmacc_no,ac_no;
    String ac_abbr,name,ac_type,trn_date,de_tml,de_user,ve_user,moduleabbr,ve_tml,frmname;
    double trn_amt;
    

    public void setRefNo(int ref_no){this.ref_no=ref_no;}
    public int getRefNo(){return this.ref_no ;}
       
	public void setFromModuleabbr(String ac_abbr){this.ac_abbr=ac_abbr;}
	public String getFromModuleabbr(){return this.ac_abbr;}
	
	public void setFromAccNo(int frmacc_no){this.frmacc_no=frmacc_no;}
	public int getFromAccNo(){return this.frmacc_no;}
	
	public void setFromName(String frmname){this.frmname=frmname;}
	public String  getFromName(){return this.frmname;}
	
	public void setToModuleabbr(String moduleabbr){this.moduleabbr=moduleabbr;}
	public String getToModuleabbr(){return this.moduleabbr;}
	
	public void setToAccType(String ac_type){this.ac_type=ac_type;}
	public String getToAccType(){return this.ac_type;}
	
	public void setToName(String name){this.name=name;}
	public String  getToName(){return this.name;}
	
	public void setToAccNo(int ac_no){this.ac_no=ac_no;}
	public int getToAccNo(){return this.ac_no;}
	
	public void setTranAmt(double trn_amt){this.trn_amt=trn_amt;}
	public double getTranAmt(){return this.trn_amt;}
	
	public void setTranDate(String trn_date){this.trn_date=trn_date;}
	public String getTranDate(){return this.trn_date;}
	
	public void  setDe_tml(String de_tml){this.de_tml=de_tml;}
	public String getDe_tml(){return this.de_tml;}
	
	public void setDe_user(String de_user){this.de_user=de_user;}
	public String getDe_user(){return this.de_user;}
	
	public void setVe_tml(String ve_tml){this.ve_tml=ve_tml;}
	public String getVe_tml(){return this.ve_tml;}
	
	public void setVe_user(String ve_user){this.ve_user=ve_user;}
	public String getVe_user(){return this.ve_user;}
	
	public void setToAccTypeAbbr(String moduleabbr){this.moduleabbr=moduleabbr;}
	public String getToAccTypeAbbr(){return this.moduleabbr;}

}
