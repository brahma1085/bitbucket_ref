package termDepositServer;

import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.ejb.EJBLocalObject;


import masterObject.general.ModuleObject;
import exceptions.AccountNotFoundException;
import exceptions.CustomerNotFoundException;
import exceptions.NomineeNotCreatedException;
import exceptions.RecordsNotFoundException;
import exceptions.ScrollNotFoundException;
import exceptions.SignatureNotInsertedException;


/**
 *   Created by Sanjeet on Apr 17, 2006
 *
 */
public interface TermDepositLocal extends EJBLocalObject
{
	
	//public float InsertTermDepTran(String ac_type,int ac_no,float amt,String date,String de_user,String de_tml,String de_date,String ve_user,String ve_tml,String ve_date);
	public float InsertTermDepTran(String ac_type,int ac_no,float amt,String date,String cd,int ref_no,String de_user,String de_tml,String de_date,String ve_user,String ve_tml,String ve_date);
}
