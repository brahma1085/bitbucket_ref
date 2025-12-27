package alm;

import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.ejb.EJBObject;
import javax.ejb.RemoveException;

public interface ALMRemote extends EJBObject{

	
	public String[] getMonthlySummary(String as_on_date,String gl_code)throws SQLException,RemoteException;
	
}
