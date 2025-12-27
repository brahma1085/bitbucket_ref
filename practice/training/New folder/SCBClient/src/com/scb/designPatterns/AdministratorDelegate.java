package com.scb.designPatterns;

import administratorServer.AdministratorHome;
import administratorServer.AdministratorRemote;


import com.scb.designPatterns.exceptions.ServiceLocatorException;

import javax.ejb.CreateException;
import javax.swing.JOptionPane;

import java.net.ConnectException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeSet;
import java.util.Vector;

import commonServer.CommonHome;
import commonServer.CommonRemote;
import masterObject.general.BranchObject;
import masterObject.general.ModuleAdminObject;
import masterObject.general.ModuleObject;
import masterObject.general.AccountObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.frontCounter.AccountMasterObject;
import masterObject.administrator.AdministratorObject;
import masterObject.administrator.TerminalObject;
import masterObject.administrator.UserActivityMasterObject;
import masterObject.customer.CustomerMasterObject;
import exceptions.AccountNotFoundException;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import exceptions.RecordsNotFoundException;
import exceptions.RecordsNotInsertedException;
import customerServer.CustomerHome;
import customerServer.CustomerRemote;
import general.Validations;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Mohsin
 * Date: Dec 20, 2008
 * Time: 11:52:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdministratorDelegate {
    private ServiceLocator serviceLoactor;
    private AdministratorHome admHome;
    private AdministratorRemote admRemote;
    private CommonHome comHome;
    private ModuleAdminObject admin_object;
    private AccountMasterObject accountmasterobject;
    private CustomerHome custHome;
    private CustomerRemote custRemote;
    private Map user_role;
    Object object_tabledata[][]=null,securityObject[][];
    ModuleAdminObject omdAdminObject;
    private AdministratorObject[] adminObject;
    int i;
    String results;
    public CommonRemote getComRemote() {
        return this.comRemote;
    }

    private CommonRemote comRemote;
    private ModuleObject[] comboElements=null;
    
    public AdministratorDelegate()throws RemoteException,CreateException,ServiceLocatorException  {
        try{
           this.admHome =(AdministratorHome)ServiceLocator.getInstance().getRemoteHome("ADMINISTRATORWEB",AdministratorHome.class);
           this.admRemote =admHome.create();

           getCommonRemote();
           this.custHome=(CustomerHome)ServiceLocator.getInstance().getRemoteHome("CUSTOMERWEB",CustomerHome.class);
           this.custRemote=custHome.create();

        }
        catch (RemoteException e) {
            throw e;
        }
        catch (CreateException ex) {
            throw ex;
        }
        catch(ServiceLocatorException se){
            throw se;
        }

    }

    public AdministratorDelegate(String id)throws RemoteException,CreateException,ServiceLocatorException {
        // reconnect to the session bean for the given id
        reconnect(id);
    }

    public String getID()throws RemoteException,CreateException,ServiceLocatorException {
        try {
            return ServiceLocator.getId(admRemote);
        } catch (ServiceLocatorException e) {
            throw e;
        }
    }

    public void reconnect(String id) throws ServiceLocatorException {
        try {
            admRemote = (AdministratorRemote) ServiceLocator.getService(id);
        } catch(ServiceLocatorException ex){
            throw ex;
        }
    }
    private CommonRemote getCommonRemote()throws RemoteException,CreateException,ServiceLocatorException {
        this.comHome=(CommonHome)ServiceLocator.getInstance().getRemoteHome("COMMONWEB",CommonHome.class);
        this.comRemote=comHome.create();
        return comRemote;
    }
    public ModuleObject[] getComboElements(int type)throws RemoteException{
        /*
         type 0 for SB and CA account type
         type 1 for introducer account type
         */
        if(type==0){
            comboElements=getComRemote().getMainModules(2,"1002000,1007000,1017000,1018000");
        }
        else if(type==1){
            comboElements= getComRemote().getMainModules(2,"1002000,1007000,1001000,1017000,1018000");
        }
        return comboElements;
    }
   
   public int createUsers(int cust,String short_names,String userID,String password,String re_pass,int pass_expiry_per,String pass_expiry_date,String acc_oper_fromDate,String acc_oper_toDate){
	   AdministratorObject adminObject=new AdministratorObject();
	   adminObject.setCust_id(cust);
		adminObject.setShortName(short_names);
		adminObject.setUid(userID);
		adminObject.setPassword(password);
		adminObject.setRe_type_pass(re_pass);
		adminObject.setPass_expiry_period(pass_expiry_per);
		adminObject.setPass_expiry_date(pass_expiry_date);
		adminObject.setAcc_operation_fromDate(acc_oper_fromDate);
		adminObject.setAcc_oper_toDate(acc_oper_toDate);
		adminObject.setDe_date(getSysDate());
		adminObject.setDe_tml("ln01");
		adminObject.setDe_user("vinay");
		adminObject.setDisable(false);
		/*adminObject.setDe_date(MainScreen.head.getSysDateTime());
		adminObject.setDe_tml(MainScreen.head.getTml());
		adminObject.setDe_user(MainScreen.head.getUid());*/
	   
		
		try {
			i = admRemote.creatingUsers(adminObject,0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
		
		return i;
   }
    public static String getSysDate() {
        Calendar c = Calendar.getInstance();
        try {
            return (Validations.checkDate(c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR)));
        } catch (DateFormatException e) {
                e.printStackTrace();
        }
        return null;
    }

    public static String getSysTime() {
            return (new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds());
    }
    
   //Methods for ViewLog.jsp Tables 
   //code added by Amzad for viewusers form
    public AdministratorObject[] getViewUser(String cid, int type, String fdate, String tdate)throws RemoteException{
    	System.out.println("Type in delegateis===="+type);
    	AdministratorObject mod1[]=null;
		
	
		try{
			
	mod1=admRemote.getViewUser(cid, type, fdate, tdate);       
		}
    catch(Exception e)
    {e.printStackTrace();}
    return mod1;
}
    public TerminalObject[] getTmlDetails()throws RemoteException{
    	
    	TerminalObject tmlObj[]=null;
		
	
		try{
			System.out.println("Inside Delegate class before calling the bean method");		
	tmlObj=admRemote.getTerminalDetails();       
	System.out.println("Inside Delegate class After calling the bean method");
		}
    catch(Exception e)
    {e.printStackTrace();}
    return tmlObj;
}
public ArrayList getDistinctTmlAddr()throws RemoteException{
    	
	ArrayList al=null;
		
	
		try{
			
	 al=admRemote.getDestinctTerminalAddr();       
		}
    catch(Exception e)
    {e.printStackTrace();}
    return al;
}
public ArrayList getTerminalIPAddr(String str)throws RemoteException{
	
	ArrayList al=null;
		
	
		try{
			
	 al=admRemote.getTerminalIPAddr(str);       
		}
    catch(Exception e)
    {e.printStackTrace();}
    return al;
}
public void insertIntoTerminalIPAddr(AdministratorObject admin_obj, String[] str)throws RemoteException{
		try{
			
	 admRemote.insertIntoTerminalIPAddr(admin_obj, str);       
		}
    catch(Exception e)
    {e.printStackTrace();}
    
}
public int deleteTerminalIpAddr(String[] str)throws RemoteException{
	int table_delete=0;
	try{
		
 table_delete=admRemote.deleteTerminalIpAddr(str);       
	}
catch(Exception e)
{e.printStackTrace();}
return table_delete;

}
public String[] getUsers()throws RemoteException{
	String[] users=null;
	try{
		
		users=admRemote.getUsers();       
	}
catch(Exception e)
{e.printStackTrace();}
return users;

}
public String[] getUsersTmls(String uid)throws RemoteException{
	String[] user_tml=null;
	try{
		
		user_tml=admRemote.getUserTerminals(uid);       
	}
catch(Exception e)
{e.printStackTrace();}
return user_tml;

}
public AdministratorObject[] getTmlCodes()throws RemoteException{
	
	AdministratorObject tmlObj[]=null;
	

	try{
		
tmlObj=admRemote.getTmlCodes();       
	}
catch(Exception e)
{e.printStackTrace();}
return tmlObj;
}
public UserActivityMasterObject[] getUserActivity(String tml_no,String uid,String from_date, String to_date,String ip_address,String queryNum)throws RemoteException{
	
	UserActivityMasterObject user[]=null;
	

	try{
		comRemote=this.getCommonRemote();
		user=comRemote.getUserActivity(tml_no,uid,from_date,to_date,ip_address,queryNum);
		System.out.println("Inside the delegate user length========="+user.length);
	}
catch(Exception e)
{e.printStackTrace();}
return user;
}
public AdministratorObject[] showholidays()throws RemoteException{
	
	AdministratorObject holiday[]=null;
	

	try{
		
		holiday=admRemote.showholidays();
		System.out.println("Inside the delegate user length========="+holiday.length);
	}
catch(Exception e)
{e.printStackTrace();}
return holiday;
}
public void insertHoliday(String day[],String date[],String reason[],String br_name[],String tml[],String uid[],String sysdate[]){
	try{
		admRemote.insertHoliday(day,date,reason,br_name,tml,uid,sysdate);
	}catch(Exception e){
		e.printStackTrace();
	}
}
public String deleteHoliday(String day[]){
	String res=null;
	for(int i=0;i<day.length;i++)		
	    System.out.println("in delegate day values are==="+day[i]);
	try{
		res=admRemote.deleteHoliday(day);
	}catch(Exception e){
		e.printStackTrace();
	}
	return res;
}
public boolean getVerifiedCustomer(int cid){
	boolean verify=false;
	
	try{
		comRemote=this.getCommonRemote();
		verify=comRemote.getVerifiedCustomer(cid);
	}catch(Exception e){
		e.printStackTrace();
	}
	return verify;
}

public CustomerMasterObject getCustomer(int cid){
	CustomerMasterObject custmasterObj=null;
	
	try{
		
		custmasterObj=custRemote.getCustomer(cid);
	}catch(Exception e){
		e.printStackTrace();
	}
	return custmasterObj;
}
public AdministratorObject retrieveCustDetailNew(String uid){
	System.out.println("in delegate uid is"+uid);
	AdministratorObject masterObj=null;
	
	try{
		
		masterObj=admRemote.retrieveCustDetailNew(uid);
	}catch(Exception e){
		e.printStackTrace();
	}
	return masterObj;
}
public int creatingUser(AdministratorObject admObj, int k){
	int res=0;
	
	try{
		
		res=admRemote.creatingUsers(admObj,k);
	}catch(Exception e){
		e.printStackTrace();
	}
	return res;
}

public void updateCreate(AdministratorObject adObje,AdministratorObject admObj){
	
	
	try{
		
		admRemote.updateCreate(adObje,admObj);
	}catch(Exception e){
		e.printStackTrace();
	}
	
}
public int deleteCreateUser(int cid){
	int res=0;
	
	try{
		
		res=admRemote.deleteCreateUser(String.valueOf(cid).trim());
	}catch(Exception e){
		e.printStackTrace();
	}
	return res;
}
public String[] getValidUser(String uid){
	String[] str=null;
	
	try{
		
		str=admRemote.getValidUser(uid);
	}catch(Exception e){
		e.printStackTrace();
	}
	return str;
}

public int createUserPassword(String name, String oldPwd, String newPwd){
	int res=0;
	
	try{
		
		res=admRemote.createUserPassword(name,oldPwd,newPwd);
	}catch(Exception e){
		e.printStackTrace();
	}
	return res;
}
public void setTerminalDetail(TerminalObject tmlobj){
	try{
		admRemote.setTerminalDetail(tmlobj);
	}
	catch(Exception e){
		e.printStackTrace();
	}
}
public int insertAccessValues(Hashtable hash,String tml){
	int res=0;
	try{
		res=admRemote.insertAccessValues(hash,tml);
	}
	catch(Exception e){
		e.printStackTrace();
	}
	return res;
}

public boolean deleteTerminal(String tml){
	boolean res=false;
	try{
		res=admRemote.deleteTerminal(tml);
	}
	catch(Exception e){
		e.printStackTrace();
	}
	return res;
}

//GL & Module Admin Starts HERE================================

public ModuleAdminObject getGLData(String modulecode){
	try{
		omdAdminObject=admRemote.getGLData(modulecode);
	}
	catch(Exception ex){ex.printStackTrace();}
	
	return omdAdminObject;
	
}

//to Strore GL& Module Admin Data
public String storeGlModAdmin(ModuleAdminObject admin_object)
{
try{
	int k = admRemote.UpdateGLModuleAdmin(admin_object);
	if(k != -1) {
		return "The values are successfully submitted";
	}
}
catch(Exception ex){ex.printStackTrace();}
return "Could Not Be Updated";
}


//Terminal Access
public void submitTerminalAccess(String selected,String[] tmlids){
	
	try{
		admRemote.setUserTerminals(selected.toString(),tmlids,"vinay","ca99",getSysDate());	
	}
	catch(Exception ex){ex.printStackTrace();}
}

public void submituserAccess(String selected,String[] tmlids,String user,String tml){
	
	try{
		admRemote.setUserTerminals(selected.toString(),tmlids,user,tml,getSysDate());	
	}
	catch(Exception ex){ex.printStackTrace();}
}
public void doLogout(String tml,String uid){
	try{
		admRemote.doLogout(tml,uid);
	}catch(Exception e){
		e.printStackTrace();
	}
}

public String [] getAccessRight(String user,String tml){
	
	try{
		return admRemote.getAccessRight(user,tml);
	}
	catch(Exception ex){ex.printStackTrace();}
return null;	
}

public String setAccessRight(String user,String tml,String[] arr,int ind) {
	try{
		return admRemote.setAccessRight(user,tml,arr,ind);
		
	}
	catch(Exception ex){ex.printStackTrace();}
	return null;
}

public  Object[][] getFormwiseAccessRights(String rolecode,String formcode){
	try{
		securityObject=admRemote.getFormwiseAccessRights(rolecode,formcode);
	}
	catch(Exception ex){ex.printStackTrace();}
	
	return securityObject;
}

public AdministratorObject[] getRoleDefinition(){
	try{
		adminObject=admRemote.getRoleDefinition();
	}
	catch(Exception ex){ex.printStackTrace();}
	return adminObject;
}

public AdministratorObject[] getUserRoleAssignment(String userid){
	try{
		adminObject=admRemote.getUserRoleAssignment(userid);
	}
	catch(Exception ex){ex.printStackTrace();}
	return adminObject;
}

public String setUserRoleAssignment(String userid,AdministratorObject[] admObj){
	try{
		System.out.println("At 544 in admindelegate---->>");
		results=admRemote.setUserRoleAssignment(userid,admObj);
	}
	catch(Exception ex){ex.printStackTrace();}
	return results;
}

public String setFormwiseRoleAccess(AdministratorObject[] admObj,String rolecode,String formcode){
	try{
		System.out.println("At 553 in admindelegate---->>");
		results=admRemote.setFormwiseRoleAccess(admObj,rolecode,formcode);
	}
	catch(Exception ex){ex.printStackTrace();}
	return results;
}

//to fetch role-access for all modules

public Map getUserLoginAccessRights(String user,String formcode){
	try{
		System.out.println("At 553 in admindelegate---->>");
		user_role=admRemote.getUserLoginAccessRights(user,formcode);
	}
	catch(Exception ex){ex.printStackTrace();}
	return user_role;
}


public AdministratorObject[] getFormsDetail(String formcode){
	try{
		adminObject=admRemote.getFormsDetail(formcode);
	}
	catch(Exception ex){ex.printStackTrace();}
	return adminObject;
}

public BranchObject[] getBranchMaster(){
	BranchObject[] branch=null;
		try{
			branch=admRemote.getBranchMaster();
				if(branch!=null){
					return branch; 
				}
		}catch (Exception e) {
	
	}
	return branch;
}


}
