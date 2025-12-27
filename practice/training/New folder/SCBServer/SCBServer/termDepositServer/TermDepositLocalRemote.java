package termDepositServer;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalObject;

import masterObject.termDeposit.DepositMasterObject;
import exceptions.DateFormatException;
import exceptions.RecordNotInsertedException;
import exceptions.RecordsNotFoundException;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface TermDepositLocalRemote extends EJBLocalObject{

	public double[] getDepositInterestRate(String ac_type,int dp_type,int cat_type,String date ,int days,double amount);
	public int closeFDAccount(DepositMasterObject depositMasterObject) throws CreateException,DateFormatException;
	//public float InsertTermDepTran(String ac_type,int ac_no,float amt,String date,String de_user,String de_tml,String de_date,String ve_user,String ve_tml,String ve_date)throws RecordsNotFoundException,IllegalAccessException;
	public float InsertTermDepTran(String ac_type,int ac_no,float amt,String date,String cd,int ref_no,String de_user,String de_tml,String de_date,String ve_user,String ve_tml,String ve_date)throws RecordsNotFoundException,IllegalAccessException;
	
}
