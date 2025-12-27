/*
 * Created on Apr 20, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pygmyServer;

import java.sql.SQLException;

import javax.ejb.EJBLocalObject;


/**
 * @author Riswan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface PygmyLocalRemote extends EJBLocalObject{

	public int insertintoPygmyTran(String ac_ty,int ac_no,String trn_date,double trn_amt,String trn_type,String cd_ind,String de_tml,String de_user,String de_date,String ve_user,String ve_tml,String ve_date);
	public double calculateInterest(String ac_type,int ac_no,String curdate);
	public double getClosingBalance(String ac_type,int ac_no);
}
