package com.scb.login.actions;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import masterObject.administrator.UserActivityMasterObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.CommonDelegate;
import com.scb.login.forms.LoginFB;

import exceptions.LoginException;
public class LoginAction extends Action {
	String uname,upwd;
	String resname,respwd;
	Connection con;
	ResultSet res_login, res_access;
	String uid;
	ActionForward af=null;
	Vector v=new Vector();
	int[] accessarray=null;
	int[] ar;
	int x,c=2;
	UserActivityMasterObject user_activ=null;
	private CommonDelegate commonDelegate;
	private AdministratorDelegate administratorDelegate;
	HttpSession session=null;
	String bankName="",location="",banknamelocation="";
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		LoginFB fb=(LoginFB)form;
		try{
		 try{
			 session=request.getSession(true);
			 synchronized (request){
				 commonDelegate=CommonDelegate.getCommonDelegate();
				 administratorDelegate=new AdministratorDelegate();
				 banknamelocation=commonDelegate.getHeadings();
				 StringTokenizer st=new StringTokenizer(banknamelocation,"$$$$$$");
				 session.setAttribute("BankName",st.nextToken());
				 session.setAttribute("BankLocation",st.nextToken());
				 session.setMaxInactiveInterval(18000);
				 session.setAttribute("UserName",fb.getUserName().toUpperCase());
				 session.setAttribute("UserTml",fb.getUserTml().toUpperCase());
				 //session.setAttribute("branch", fb.getBranch().toUpperCase());
			 }
			 request.setAttribute("UserTml",fb.getUserTml().toUpperCase());
			 commonDelegate=CommonDelegate.getCommonDelegate();
			 banknamelocation=commonDelegate.getHeadings();
			 new timethread();
			 StringTokenizer st=new StringTokenizer(banknamelocation,"$$$$$$");
			 session.getServletContext().setAttribute("BankName",st.nextToken());
			 session.getServletContext().setAttribute("BankLocation",st.nextToken());
			// session.getServletContext().setAttribute("branches", administratorDelegate.getBranchMaster());
		}catch(NamingException e1){
        	 session.invalidate();
        	 request.setAttribute("errmsg","Server is not started yet !\n Please start the server");
		}catch(RemoteException e2){
        	 session.invalidate();
        	 request.setAttribute("errmsg","Unable to connect to the server");
		}catch(CreateException e3){
        	 session.invalidate();
        	 e3.printStackTrace();
		}
        String ip_address=null;
		try{
				 String login=null;
				 ip_address = "192.168.1.2";
				 /*if(!fb.getBranch().equals("SELECT")){
					 StringTokenizer st1=new StringTokenizer(fb.getUserName(),"_");
					 String uid=st1.nextToken();
					 String br_cd=st1.nextToken();
					 if(!br_cd.trim().equalsIgnoreCase(fb.getBranch()) && (fb.getAlert()==null||fb.getAlert().trim().length()==0)){
						 fb.setAlert("You are getting Login into Another Branch");
						 return mapping.findForward("no");
					 }*/
				 Vector user_roles = commonDelegate.checkLogin(fb.getUserName().trim().toUpperCase(),fb.getUserPwd().trim().toUpperCase(), commonDelegate.getSysDate(), fb.getUserTml().trim().toUpperCase(), ip_address);
				 synchronized (request){
	  				session.setAttribute("usraccessvector",commonDelegate.getUserAccessRights(fb.getUserName().trim()));
				 }
				 	if(commonDelegate.checkForHoliday(CommonDelegate.getSysDate())){     
				 		login = "H";
				 		request.setAttribute("errmsg","You Logged in a Holiday.....!");
				 		return mapping.findForward("no");
				 	}else if(user_roles!=null && commonDelegate.checkUserLogin(fb.getUserTml().trim().toUpperCase(),fb.getUserName().trim().toUpperCase())){
						request.setAttribute("errmsg", "User is already Logged in....Please verify the Login details");
						return mapping.findForward("no");
					}else{
						login = commonDelegate.checkLogin(fb.getUserName().trim().toUpperCase(),fb.getUserTml().trim().toUpperCase(),CommonDelegate.getSysDate(),CommonDelegate.getSysTime());
					}
				 if(login.equalsIgnoreCase("H")){
					 user_activ=new UserActivityMasterObject();
					 user_activ.setUser_id(fb.getUserName().trim());
					 user_activ.setTml_no(fb.getUserTml().trim());
					 user_activ.setBranch(fb.getBranch().trim());
					 user_activ.setIp_address(ip_address);
					 user_activ.setPage_visit("Login");
					 user_activ.setAc_no(0);
					 user_activ.setCid(0);
					 user_activ.setActivity_date(CommonDelegate.getSysDate());
					 user_activ.setActivity_time(CommonDelegate.getSysTime());
					 user_activ.setActivity("Logged in ! Holiday ");
					 request.setAttribute("errmsg","You Logged in a Holiday.....!");
					 return mapping.findForward("no");
				 }else if(login.equalsIgnoreCase("W")){
					 user_activ=new UserActivityMasterObject();
             	     user_activ.setUser_id(fb.getUserName().trim());
	            	 user_activ.setTml_no(fb.getUserTml().trim());
	            	// user_activ.setBranch(fb.getBranch().trim());
	            	 user_activ.setIp_address(ip_address);
	            	 user_activ.setPage_visit("Login");
	            	 user_activ.setAc_no(0);
	            	 user_activ.setCid(0);
	            	 user_activ.setActivity_date(CommonDelegate.getSysDate());
	            	 user_activ.setActivity_time(CommonDelegate.getSysTime());
	            	 user_activ.setActivity("Logged in ! Working day");
					 uname=fb.getUserName();
					 upwd=fb.getUserPwd();
					 return mapping.findForward("yes");
				 }else{  
					 user_activ=new UserActivityMasterObject(); 
					 user_activ.setUser_id(fb.getUserName().trim());
					 user_activ.setTml_no(fb.getUserTml().trim());
					// user_activ.setBranch(fb.getBranch().trim());
					 user_activ.setIp_address(ip_address);
					 user_activ.setPage_visit("Login");
					 user_activ.setAc_type("");
					 user_activ.setAc_no(0);
					 user_activ.setCid(0);
					 user_activ.setActivity_date(CommonDelegate.getSysDate());
					 user_activ.setActivity_time(CommonDelegate.getSysTime());
					 user_activ.setActivity(login);
					 request.setAttribute("errmsg",login.toString());
					 return mapping.findForward("no");
				 }
				/* }else{
					request.setAttribute("errmsg", "Select the Branch");
					return mapping.findForward("no");
				 }*/
			}catch(LoginException le){  
				user_activ=new UserActivityMasterObject();
				user_activ.setUser_id(fb.getUserName().trim());
				user_activ.setTml_no(fb.getUserTml().trim());
				//user_activ.setBranch(fb.getBranch().trim());
				user_activ.setIp_address(ip_address);
				user_activ.setPage_visit("Login Page");
				user_activ.setActivity(le.toString());
				user_activ.setAc_no(0);
				user_activ.setCid(0);
				user_activ.setActivity_date(CommonDelegate.getSysDate());
				user_activ.setActivity_time(CommonDelegate.getSysTime());
				try{
					int result=0;
					result=commonDelegate.storeUserActivityObj(user_activ);
					if(result!=1)
						request.setAttribute("errmsg","Problem in storing the UserActivity Object");
				}catch(RemoteException re){
					re.printStackTrace();
				}
				request.setAttribute("errmsg",le.toString());
			}catch(Exception ex){
				ex.printStackTrace();
			}
			HttpSession session=request.getSession(true);
			session.setAttribute("UserActivityObj", user_activ);
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("no");
	}
	public double getBankRunBal(){
		double bank_runbal = 0.0;
		try{
			bank_runbal=commonDelegate.getAllCashTmlRunningBal(CommonDelegate.getSysDate());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return bank_runbal;
	}
	class timethread extends Thread{
		timethread(){
			start();
		}

		public void run(){
			for(;;){
				try{
					session.getServletContext().setAttribute("BankBalance", getBankRunBal());
				}catch(Exception e){
					e.printStackTrace();
				}
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
}