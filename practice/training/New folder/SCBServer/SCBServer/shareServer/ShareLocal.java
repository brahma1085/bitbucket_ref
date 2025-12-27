
package shareServer;

import javax.ejb.EJBLocalObject;

import exceptions.RecordsNotFoundException;

import masterObject.share.ShareMasterObject;
import masterObject.share.ShareParamObject;



/**
 *   Created Karthi on Feb 23, 2006
 *
 */
public interface ShareLocal extends EJBLocalObject
{
	public int storeShare(ShareMasterObject ms,int type,int old_scroll,int new_scroll);
	
	public int verifyShare(ShareMasterObject sm,int trnno,int type,ShareParamObject shparam[],String date);
	
	public ShareMasterObject getShare(String actype,int shareno);
	
	public float InsertintoShareTran(String ac_type,int ac_no,float amnt,String type,int trn_no,String date,String de_user,String de_tml,String de_date)throws RecordsNotFoundException,IllegalAccessException;
	
}
