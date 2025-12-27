package com.scb.designPatterns;

import java.net.ConnectException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import loanServer.LoanHome;
import loanServer.LoanRemote;
import masterObject.administrator.UserActivityMasterObject;
import masterObject.frontCounter.AccountMasterObject;
import masterObject.general.ModuleObject;
import administratorServer.AdministratorHome;
import administratorServer.AdministratorRemote;
import cashServer.CashHome;
import cashServer.CashRemote;

import com.scb.designPatterns.exceptions.ServiceLocatorException;
import commonServer.CommonHome;
import commonServer.CommonRemote;

import customerServer.CustomerHome;
import customerServer.CustomerRemote;
import exceptions.DateFormatException;
import exceptions.LoginException;
import general.Validations;

public class CommonDelegate {
	  private ServiceLocator serviceLoactor;
	    private AdministratorHome adminHome;
	    private AdministratorRemote adminRemote;
	    private CommonHome comHome;
	    private AccountMasterObject accountmasterobject;
	    private CustomerHome custHome;
	    private CustomerRemote custRemote;
	    private LoanRemote loanremote=null;
	    private LoanHome loanHome=null;
	    private CashRemote cashremote=null;
	    private CashHome cashHome=null;

	    private static CommonDelegate cDelegate=null;
	    public CommonRemote getComRemote() {
	        return this.comRemote;
	    }

	    private CommonRemote comRemote;
	    private ModuleObject[] comboElements=null;
	    
	 public CommonDelegate()throws RemoteException,CreateException,ServiceLocatorException  {
	        try{
	           this.adminHome =(AdministratorHome)ServiceLocator.getInstance().getRemoteHome("ADMINISTRATORWEB",AdministratorHome.class);
	           this.adminRemote = adminHome.create();

	           this.comHome=(CommonHome)ServiceLocator.getInstance().getRemoteHome("COMMONWEB",CommonHome.class);
	           this.comRemote=comHome.create();
	         
	           if(loanremote==null){
	        	   this.loanHome=(LoanHome)ServiceLocator.getInstance().getRemoteHome("LOANSWEB",LoanHome.class);
	        	   this.loanremote=loanHome.create();
	           }
	           if(cashremote==null){
	        	   this.cashHome=(CashHome)ServiceLocator.getInstance().getRemoteHome("CASHWEB",CashHome.class);
	        	   this.cashremote=cashHome.create();
	           }

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
	 public Vector checkLogin(String user_id, String password, String login_date, String tml_id, String ip_address) throws LoginException,RemoteException{
		 Vector vLogin=adminRemote.checkLogin(user_id, password, login_date, tml_id, ip_address);
		 return vLogin;
		 
	 }
	 //06-11-2011
	 public boolean checkUserLogin(String tml_id,String user_id) throws RemoteException{
		 boolean flag=false;
		 flag=adminRemote.checkUserLogin(tml_id, user_id);
		 return flag;
	 }
	 
	 //28-11-2011
	 
	 public Vector getUserAccessRights(String user_name) throws RemoteException{
		 Vector vLogin=adminRemote.usrAccessRights(user_name);
		 return vLogin;
	 }
	 
	public String checkLogin(String uid,String tml,String date,String time)throws RemoteException{
		String login=comRemote.checkLogin(uid,tml,date,time);
		return login;
	}
	 public static CommonDelegate getCommonDelegate()throws RemoteException,CreateException,ServiceLocatorException  {
		 if(cDelegate==null)
			 cDelegate=new CommonDelegate();
		 return cDelegate;
	 }
	 public boolean checkForHoliday(String date)throws RemoteException{
		 boolean login=comRemote.checkForHoliday(date);
		 return login;
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
	  //code added by amzad
	  public int storeUserActivityObj(UserActivityMasterObject user_activ)throws RemoteException{
			int res=0;
			res=comRemote.storeUserActivity(user_activ);
			 return res;
		 }
     
	  public String getHeadings() throws RemoteException,NamingException{
		  String str="";
		  try{
			  str=comRemote.getHeading();
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		 return str; 
	  }
	  public  double getAllCashTmlRunningBal(String date) throws SQLException{
		  double bal=0.0;
		  
		  try{
			  bal=cashremote.getAllCashTmlRunningBalance(date);
			  
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  return bal;
	  }

	  public Connection getcon()throws RemoteException,ConnectException{
	  	Connection con=null;
	  	try{
	  		con=adminRemote.getConn();
	  		
	  	}
	  	catch(ConnectException ex){ex.printStackTrace();}
	  	return con;
	  }
	 
}
