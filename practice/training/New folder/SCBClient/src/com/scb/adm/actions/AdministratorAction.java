package com.scb.adm.actions;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import masterObject.administrator.AdministratorObject;
import masterObject.administrator.TerminalObject;
import masterObject.administrator.UserActivityMasterObject;
import masterObject.customer.CustomerMasterObject;
import masterObject.general.BranchObject;
import masterObject.general.ModuleAdminObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.log4j.Logger;

import com.scb.adm.forms.AccessRightsformodulesForm;
import com.scb.adm.forms.AccessToTmlFormBean;
import com.scb.adm.forms.AdmCAModuleForm;
import com.scb.adm.forms.AdmCCModuleForm;
import com.scb.adm.forms.AdmClearingModuleForm;
import com.scb.adm.forms.AdmFDModuleForm;
import com.scb.adm.forms.AdmODModuleForm;
import com.scb.adm.forms.AdmPOModuleForm;
import com.scb.adm.forms.AdmPygmyModuleForm;
import com.scb.adm.forms.AdmRDModuleForm;
import com.scb.adm.forms.AdmSBModuleForm;
import com.scb.adm.forms.AdmShareModuleForm;
import com.scb.adm.forms.ChangePasswordForm;
import com.scb.adm.forms.ChangePwdFormBean;
import com.scb.adm.forms.CreateUserForm;
import com.scb.adm.forms.CreateUsersFormBean;
import com.scb.adm.forms.FormsDetailForm;
import com.scb.adm.forms.HolidayMasterFormBean;
import com.scb.adm.forms.IpConfFormBean;
import com.scb.adm.forms.LockersTmlDetFB;
import com.scb.adm.forms.NewAccessRightsForm;
import com.scb.adm.forms.NewSecurityandAdminForm;
import com.scb.adm.forms.TerminalsDetailsFB;
import com.scb.adm.forms.UserActivityFormBean;
import com.scb.adm.forms.UserRoleAssignmentForm;
import com.scb.adm.forms.ViewUsersFormBean;
import com.scb.common.help.CommonPanelHeading;
import com.scb.common.help.ResultHelp;
import com.scb.common.log4j.LogDetails;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.props.MenuNameReader;

import exceptions.RecordNotInsertedException;
import general.Validations;

public class AdministratorAction extends Action {
private AdministratorDelegate admDelegate;
private String path;
HttpSession session;
String user,tml;
ModuleAdminObject admmoduleObject;
TerminalObject[] tml_obj; 
AdministratorObject[] adminobject,admObject;
Object arrTml[]=null;
Map user_role;

final Logger logger=LogDetails.getInstance().getLoggerObject("AdministratorAction");

public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res){
	session=req.getSession();
	user=(String)session.getAttribute("UserName");
	tml=(String)session.getAttribute("UserTml") ;
   	try{
   		synchronized(req) {
    		if(user!=null){
    			admDelegate=new AdministratorDelegate();
    			user_role=admDelegate.getUserLoginAccessRights(user,"AD");
    			if(user_role!=null){
	    			req.setAttribute("user_role",user_role);
	    			if(req.getParameter("pageId")!=null){
	    				req.setAttribute("accesspageId",req.getParameter("pageId").trim());
	    			}
    			}else{
    				path=MenuNameReader.getScreenProperties("0000");
   		           	setErrorPageElements("Sorry, You do not have access to this page",req,path);
   		           	return map.findForward(ResultHelp.getError());
	    		}
	    	}
	    }
	   }catch(Exception ex){
		   ex.printStackTrace();
	   }
	   
    if(map.getPath().trim().equalsIgnoreCase("/Administrator/CreateUserMenu")){
         try{
        	 CreateUserForm cuForm=(CreateUserForm)form;
         	 req.setAttribute("pageNum",cuForm.getPageId().trim());
         	 logger.info("This is from Administrator==>"+map.getPath());
         	 if(MenuNameReader.containsKeyScreen(cuForm.getPageId())){
                path=MenuNameReader.getScreenProperties(cuForm.getPageId());
                req.setAttribute("pageId",path);
                return map.findForward(ResultHelp.getSuccess());
         	 }else{
         		 path=MenuNameReader.getScreenProperties("0000");
                 setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                 return map.findForward(ResultHelp.getError());
         	 }
        }catch(Exception e){
          path=MenuNameReader.getScreenProperties("0000");
          setErrorPageElements(""+e,req,path);
          return map.findForward(ResultHelp.getError());
        }
    }else if(map.getPath().trim().equalsIgnoreCase("/Administrator/CreateUser")){
    	CreateUserForm cuForm=new CreateUserForm();
    	String path=null;
    	try{
    		req.setAttribute("pageNum",cuForm.getPageId().trim());
    		if(MenuNameReader.containsKeyScreen(cuForm.getPageId())){
    			path=MenuNameReader.getScreenProperties(cuForm.getPageId());
    			req.setAttribute("pageId",path);
    			return map.findForward(ResultHelp.getSuccess());
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return map.findForward(ResultHelp.getSuccess());
    }
    
    if(map.getPath().trim().equalsIgnoreCase("/Administrator/ChangePasswordMenu")){
        try{
        	ChangePasswordForm cpwForm=(ChangePasswordForm)form;
        	logger.info("This is from Front Counter"+map.getPath());
        	if(MenuNameReader.containsKeyScreen(cpwForm.getPageId())){
               path=MenuNameReader.getScreenProperties(cpwForm.getPageId());
               req.setAttribute("pageId",path);
               req.setAttribute("pageNum",cpwForm.getPageId().trim());
               return map.findForward(ResultHelp.getSuccess());
        	}else{
        		path=MenuNameReader.getScreenProperties("0000");
                setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                return map.findForward(ResultHelp.getError());
        	}
       }catch(Exception e){
         path=MenuNameReader.getScreenProperties("0000");
         setErrorPageElements(""+e,req,path);
         return map.findForward(ResultHelp.getError());
       }
   }else if(map.getPath().trim().equalsIgnoreCase("/Administrator/ChangePassword")){
	   ChangePasswordForm acForm=new ChangePasswordForm();
	   String path=null;
	   try{
		   req.setAttribute("pageNum",acForm.getPageId().trim());
		   if(MenuNameReader.containsKeyScreen(acForm.getPageId())){
			   path=MenuNameReader.getScreenProperties(acForm.getPageId());
			   req.setAttribute("pageId",path);
			   return map.findForward(ResultHelp.getSuccess());
		   }
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  return map.findForward(ResultHelp.getSuccess());
   }

   if(map.getPath().trim().equalsIgnoreCase("/Administrator/ViewUsersMenu")){
	  try{
		  ViewUsersFormBean viewusers=(ViewUsersFormBean)form;
		  req.setAttribute("pagenum", viewusers.getPageId());
		  if(MenuNameReader.containsKeyScreen(viewusers.getPageId())){
			  path=MenuNameReader.getScreenProperties(viewusers.getPageId());
	    	  admDelegate=new AdministratorDelegate();
	    	  req.setAttribute("pageId",path);
	    	  viewusers.setFrmDate(AdministratorDelegate.getSysDate());
	    	  viewusers.setToDate(AdministratorDelegate.getSysDate());
	    	  return map.findForward(ResultHelp.getSuccess());
		  }else{
			  path=MenuNameReader.getScreenProperties("0000");
			  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
			  return map.findForward(ResultHelp.getError());
		  }
	     }catch(Exception e){
	    	  path=MenuNameReader.getScreenProperties("0000");
	    	  setErrorPageElements(""+e, req, path);
	    	  return map.findForward(ResultHelp.getError());
	     }
	}else if(map.getPath().trim().equalsIgnoreCase("/Administrator/ViewUsers")){
		try{
			ViewUsersFormBean viewusers=(ViewUsersFormBean)form;
			if(MenuNameReader.containsKeyScreen(viewusers.getPageId())){
				int type=0;
				AdministratorObject[] array_obj_user=null;
				Object data[][]=null;
				path=MenuNameReader.getScreenProperties(viewusers.getPageId());
				admDelegate=new AdministratorDelegate();
				req.setAttribute("pageId",path);
				if(viewusers.getFlag().equalsIgnoreCase("from_radio")){
					String usrch=viewusers.getUserChoice().toString();
					if(usrch.equalsIgnoreCase("All")){
						req.setAttribute("UserChoice", usrch);
						type=2;
					}else{
						req.setAttribute("UserChoice", usrch);
						type=1;
					}
				}
				if(viewusers.getFlag().equalsIgnoreCase("view")){
					try{
						String usrch=viewusers.getUserChoice().toString();
						if(usrch.equalsIgnoreCase("All")){
							req.setAttribute("UserChoice", usrch);
							type=2;
						}else{
							req.setAttribute("UserChoice", usrch);
							type=1;
						}
						array_obj_user=admDelegate.getViewUser(viewusers.getUid(),type,viewusers.getFrmDate(),viewusers.getToDate());
    					if(array_obj_user!=null){
		    				data=new Object[array_obj_user.length][12];
		    				for(int i=0;i<array_obj_user.length;i++){
		    					data[i][0]=array_obj_user[i].getUid();
		    					data[i][1]=String.valueOf(array_obj_user[i].getCust_id());
		    					data[i][2]=array_obj_user[i].getShortName();
		    					data[i][3]=String.valueOf(array_obj_user[i].getPass_expiry_period());
		    					data[i][4]=array_obj_user[i].getPass_expiry_date();
		    					data[i][5]=array_obj_user[i].getThump_ipm();
		    					data[i][6]=array_obj_user[i].getAcc_operation_fromDate();
		    					data[i][7]=array_obj_user[i].getAcc_oper_toDate();
		    					data[i][8]=String.valueOf(array_obj_user[i].getDisable());
		    					data[i][9]= array_obj_user[i].getDe_tml();
		    					data[i][10]=array_obj_user[i].getDe_user();
		    					data[i][11]=array_obj_user[i].getDe_date();
		    				}
		    				req.setAttribute("ArrayObject", data);
    					}else{
    						viewusers.setValidations("Warning:\nNO RECORDS MATCHED !\nPlease enter the Date..");
    					}
					}catch(Exception m){
						m.printStackTrace();
					} 
				}
				req.setAttribute("pagenum", viewusers.getPageId());
				return map.findForward(ResultHelp.getSuccess());
			}else{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
				return map.findForward(ResultHelp.getError());
			}
		}catch(Exception e){
		    path=MenuNameReader.getScreenProperties("0000");
		    setErrorPageElements(""+e, req, path);
		    return map.findForward(ResultHelp.getError());
		}
	}
   if(map.getPath().trim().equalsIgnoreCase("/Administrator/IpConfMenu")){
	   try{
		   IpConfFormBean ipconf=(IpConfFormBean)form;
		   req.setAttribute("pagenum", ipconf.getPageId());
		   if(MenuNameReader.containsKeyScreen(ipconf.getPageId())){
			   path=MenuNameReader.getScreenProperties(ipconf.getPageId());
			   admDelegate=new AdministratorDelegate();
			   TerminalObject[] tml_obj=null; 
			   Object arrTml[]=null;
			   tml_obj=admDelegate.getTmlDetails();
			   arrTml=new Object[tml_obj.length];
			   for(int i=0;i<tml_obj.length;i++){
				   arrTml[i]=tml_obj[i].getTmlName();
			   }
			   req.setAttribute("TmlObj",arrTml);
			   ArrayList arr=admDelegate.getDistinctTmlAddr();
			   req.setAttribute("IpAddrArrayList", arr);
			   req.setAttribute("pageId",path);
			   return map.findForward(ResultHelp.getSuccess());
		   }else{
			   path=MenuNameReader.getScreenProperties("0000");
			   setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
			   return map.findForward(ResultHelp.getError());
		   }
	      }catch(Exception e){
	    	  path=MenuNameReader.getScreenProperties("0000");
	    	  setErrorPageElements(""+e, req, path);
	    	  return map.findForward(ResultHelp.getError());
	      }
	}else if(map.getPath().trim().equalsIgnoreCase("/Administrator/IpConf")){
		try{
			IpConfFormBean ipconf=(IpConfFormBean)form;
			if(MenuNameReader.containsKeyScreen(ipconf.getPageId())){
				int type=0;
				AdministratorObject[] array_obj_user=null;
				Object data[][]=null;
				path=MenuNameReader.getScreenProperties(ipconf.getPageId());
				admDelegate=new AdministratorDelegate();
				req.setAttribute("pageId",path);
				ArrayList arrlist=admDelegate.getDistinctTmlAddr();
				req.setAttribute("arrlist",arrlist);
				if(ipconf.getFlag().equalsIgnoreCase("insert")){
					TerminalObject[] tml_obj=null; 
		    		Object arrTml[]=null;
		    		tml_obj=admDelegate.getTmlDetails();
		    		arrTml=new Object[tml_obj.length];
		    		for(int i=0;i<tml_obj.length;i++){
		    			arrTml[i]=tml_obj[i].getTmlName();
		    		}
		    		req.setAttribute("TmlObj",arrTml);
		    		ArrayList arr=admDelegate.getDistinctTmlAddr();
		    		req.setAttribute("IpAddrArrayList", arr);
		    		req.setAttribute("InsertRow","Insert");
		    		ipconf.setValidations("You can add A new row ...!");
				}
				if(ipconf.getFlag().equalsIgnoreCase("submit")){
					TerminalObject[] tml_obj=null; 
					Object arrTml[]=null;
					String[] tml_data=null;
					AdministratorObject obj_user=null;
					String[] arrSubmit=req.getParameterValues("ipAddr");
					tml_obj=admDelegate.getTmlDetails();
					arrTml=new Object[tml_obj.length];
					for(int i=0;i<tml_obj.length;i++){
						arrTml[i]=tml_obj[i].getTmlName();
					}
					req.setAttribute("TmlObj",arrTml);
					ArrayList arr=admDelegate.getDistinctTmlAddr();
					req.setAttribute("IpAddrArrayList", arr);
					String ipaddr=arrSubmit[0].toString();
					ArrayList arr1=admDelegate.getTerminalIPAddr(ipconf.getTmlNo());
					int insert_flag=0;
					for(int j=0;j<arr1.size();j++){
						if(ipaddr.equalsIgnoreCase(arr1.get(j).toString().trim())){
							ipconf.setValidations("Duplicate Entry..! \n Sorry Cannot be inserted");
							insert_flag=1;
						}
					}
					try{
						if(insert_flag==0){
							obj_user=new AdministratorObject();
							obj_user.setTerminal_no(ipconf.getTmlNo());
							obj_user.setDe_date(admDelegate.getSysDate());
							obj_user.setDe_tml(tml);
							obj_user.setDe_user(user);
							tml_data=new String[1];
							tml_data[0]=ipaddr;
							admDelegate.insertIntoTerminalIPAddr(obj_user,tml_data);
							ipconf.setValidations("The data is sucessfully submited");
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
				if(ipconf.getFlag().equalsIgnoreCase("from_tmlno")){
					TerminalObject[] tml_obj=null; 
					Object arrTml[]=null;
					tml_obj=admDelegate.getTmlDetails();
					arrTml=new Object[tml_obj.length];
					for(int i=0;i<tml_obj.length;i++){
						arrTml[i]=tml_obj[i].getTmlName();
					}
					req.setAttribute("TmlObj",arrTml);
					String str=ipconf.getTmlNo();
					try{
						if(str.equalsIgnoreCase("All")){
							ArrayList arr=admDelegate.getDistinctTmlAddr();
							req.setAttribute("IpAddrArrayList", arr);
						}else{
							ArrayList arr1=admDelegate.getTerminalIPAddr(str);
							req.setAttribute("IpAddrArrayList", arr1);
						}
					}catch(Exception er){
						er.printStackTrace();	
					}
				}
				if(ipconf.getFlag().equalsIgnoreCase("delete")){
					TerminalObject[] tml_obj=null; 
					int table_delete=0;
					int count=0;
					String[] ckBox=null;
					String[] ckBoxOn=null;
					Object arrTml[]=null;
					String[] tml_data=null;
					tml_obj=admDelegate.getTmlDetails();
					arrTml=new Object[tml_obj.length];
					for(int i=0;i<tml_obj.length;i++){
						arrTml[i]=tml_obj[i].getTmlName();
					}
					req.setAttribute("TmlObj",arrTml);
					ArrayList arr=admDelegate.getDistinctTmlAddr();
					req.setAttribute("IpAddrArrayList", arr);
					ckBox=(String[])req.getParameterValues("ckBox");
					tml_data=new String[ckBox.length];
					String[] ipaddr=(String[])req.getParameterValues("ipAddr");
					for(int i=0;i<ckBox.length;i++){
						int x=Integer.parseInt(ckBox[i].trim());
						tml_data[count++]=ipaddr[x].trim();
					}
					try{
						table_delete=admDelegate.deleteTerminalIpAddr(tml_data);
						if( table_delete>0)
							ipconf.setValidations("The data is deleted successfully");
						else
							ipconf.setValidations("Unable to delete");
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
				req.setAttribute("pagenum", ipconf.getPageId());
				return map.findForward(ResultHelp.getSuccess());
			}else{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
				return map.findForward(ResultHelp.getError());
			}
		}catch(Exception e){
		   path=MenuNameReader.getScreenProperties("0000");
		   setErrorPageElements(""+e, req, path);
		   return map.findForward(ResultHelp.getError());
		}
	}

   if(map.getPath().trim().equalsIgnoreCase("/Administrator/AccessToTerminalsMenu")){
	   try{
		   AccessToTmlFormBean tml_access=(AccessToTmlFormBean)form;
		   req.setAttribute("pagenum", tml_access.getPageId());
		   if(MenuNameReader.containsKeyScreen(tml_access.getPageId())){
			   path=MenuNameReader.getScreenProperties(tml_access.getPageId());
			   admDelegate=new AdministratorDelegate();
			   req.setAttribute("pageId",path);
			   TerminalObject[] tml_obj=null;
			   String[] user_ids=null;
			   String[] tml_ids=null;
			   try{
				   user_ids = admDelegate.getUsers();
				   tml_obj = admDelegate.getTmlDetails();
			   }catch(Exception exe){
				   exe.printStackTrace();
			   }
			   req.setAttribute("UserIds", user_ids);
			   String[][] userTmls=new String[tml_obj.length][2];
			   for(int i=0;i<tml_obj.length;i++){
				   userTmls[i][0]=tml_obj[i].getTmlName();
				   userTmls[i][1]=tml_obj[i].getTmlDesc();
			   }	
			   req.setAttribute("UserTmlObj", userTmls);
			   return map.findForward(ResultHelp.getSuccess());
		   }else{
			   path=MenuNameReader.getScreenProperties("0000");
			   setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
			   return map.findForward(ResultHelp.getError());
		   }
	   }catch(Exception e){
		   path=MenuNameReader.getScreenProperties("0000");
		   setErrorPageElements(""+e, req, path);
		   return map.findForward(ResultHelp.getError());
	   }
   }else if(map.getPath().trim().equalsIgnoreCase("/Administrator/AccessToTerminals")){
	   try{
		   AccessToTmlFormBean tml_access=(AccessToTmlFormBean)form;
		   if(MenuNameReader.containsKeyScreen(tml_access.getPageId())){
			   String[] newstr=null;
			   path=MenuNameReader.getScreenProperties(tml_access.getPageId());
			   admDelegate=new AdministratorDelegate();
			   req.setAttribute("pageId",path);
			   if(tml_access.getFlag()!=null){
			   if(tml_access.getFlag().equalsIgnoreCase("submit")){
				   String[] cbx=(String[])req.getParameterValues("chbox");
				   String[] tmlname=(String[])req.getParameterValues("tmlname");
				   if(cbx!=null){
					   newstr=new String[cbx.length];
					   for(int c=0;c<cbx.length;c++){
						   int x=Integer.parseInt(cbx[c]);
						   newstr[c]=tmlname[x];
					   }
					   admDelegate.submitTerminalAccess(tml_access.getUid(),newstr);
					   tml_access.setValidations("Values Successfully submitted");
				   }else{
					   tml_access.setValidations("Select the values to be submitted");
				   }
			   }
			   if(tml_access.getFlag().equalsIgnoreCase("from_uid")){
				   String[] tml_ids=null;
				   String[] user_ids=null;
				   TerminalObject[] tml_obj=null;
				   try{
					   user_ids = admDelegate.getUsers();
					   tml_obj = admDelegate.getTmlDetails();
				   }catch(Exception exe){
					   exe.printStackTrace();
				   }
				   req.setAttribute("UserIds", user_ids);
				   String[][] userTmls=new String[tml_obj.length][2];
				   int c=0;
				   for(int i=0;i<tml_obj.length;i++){
					   userTmls[i][0]=tml_obj[i].getTmlName();
					   userTmls[i][1]=tml_obj[i].getTmlDesc();
				   }
				   try{	
					   tml_ids = admDelegate.getUsersTmls(tml_access.getUid().toString().trim());
					   req.setAttribute("tml_ids",tml_ids);
				   }catch(Exception exe){
					   exe.printStackTrace();
				   }
				   req.setAttribute("HasAccessTo",tml_ids);
				   for(int z=0;z<tml_ids.length;z++)
					   req.setAttribute("UserTmlObj", userTmls);
				   req.setAttribute("uid",tml_access.getUid().toString().trim());
			   }
		   }
			   req.setAttribute("pagenum", tml_access.getPageId());
			   TerminalObject[] tml_obj=null;
			   String[] user_ids=null;
			   String[] tml_ids=null;
			   try{
				   user_ids = admDelegate.getUsers();
				   tml_obj = admDelegate.getTmlDetails();
			   }catch(Exception exe){
				   exe.printStackTrace();
			   }
			   req.setAttribute("UserIds", user_ids);
			   String[][] userTmls=new String[tml_obj.length][2];
			   for(int i=0;i<tml_obj.length;i++){
				   userTmls[i][0]=tml_obj[i].getTmlName();
				   userTmls[i][1]=tml_obj[i].getTmlDesc();
			   }
			   req.setAttribute("UserTmlObj", userTmls);
			   return map.findForward(ResultHelp.getSuccess());
		   }else{
			   path=MenuNameReader.getScreenProperties("0000");
			   setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
			   return map.findForward(ResultHelp.getError());
		   }
	   }catch(Exception e){
		   path=MenuNameReader.getScreenProperties("0000");
		   setErrorPageElements(""+e, req, path);
		   e.printStackTrace();
		   return map.findForward(ResultHelp.getError());
	   }
   }
    	/* *********************Administrator AccessToTerminals Action Ends Here********* */
    /* *************************Administrator UserActivity Action Starts here*********** */
    	if(map.getPath().trim().equalsIgnoreCase("/Administrator/UserActivityMenu"))
		{
	      try{
	    	  UserActivityFormBean user_activity=(UserActivityFormBean)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+user_activity);
	    	  System.out.println("the page id is "+user_activity.getPageId());
	    	  req.setAttribute("pagenum", user_activity.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(user_activity.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(user_activity.getPageId());
	    		  admDelegate=new AdministratorDelegate();
	    		  
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  
	    		  return map.findForward(ResultHelp.getSuccess());
	    	  }
	    	  else
	    	  {
	    		  path=MenuNameReader.getScreenProperties("0000");
	    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
	    		  return map.findForward(ResultHelp.getError());
	    	  }
	      }
	      catch(Exception e)
	      {
	    	  path=MenuNameReader.getScreenProperties("0000");
	    	  setErrorPageElements(""+e, req, path);
	    	  return map.findForward(ResultHelp.getError());
	      }
		}
		
		else if(map.getPath().trim().equalsIgnoreCase("/Administrator/UserActivity")){
			try{
				UserActivityFormBean user_activity=(UserActivityFormBean)form;
				String fdate,tdate,usrid,ipadd,tNo;
				int queryNum=0;
				String date="0",ip="0",uid="0",tml="0";
				UserActivityMasterObject user[]=null;
		    	 
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(user_activity.getPageId()))
		    	  {
		    		  
		    		  path=MenuNameReader.getScreenProperties(user_activity.getPageId());
		    		  admDelegate=new AdministratorDelegate();
		    		  
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		 
		    		  if(user_activity.getFlag().equalsIgnoreCase("UserChoice")){
		    			  try{
		    			  if(user_activity.getCkDate().equalsIgnoreCase("Date")){
		    			  user_activity.setFrmDate(admDelegate.getSysDate());
			    		  user_activity.setToDate(admDelegate.getSysDate());
			    		  req.setAttribute("ShowDates", "ShowDate");
		    			  }
		    			  if(user_activity.getIpAddr().equalsIgnoreCase("IpAddress")){
			    		  ArrayList al=admDelegate.getDistinctTmlAddr();
			    		  req.setAttribute("IpAddress", al);
		    			  }
		    			  if(user_activity.getFlag().equalsIgnoreCase("UserChoice")){
			    		  String[] users=admDelegate.getUsers();
			    		  req.setAttribute("UserDetails",users);
		    			  }
		    			  if(user_activity.getTml().equalsIgnoreCase("tml")){
			    		  AdministratorObject[] admObj=admDelegate.getTmlCodes();
			    		  String[] tmlNo=new String[admObj.length];
			    		  for(int i=0;i<admObj.length;i++){
			    			  tmlNo[i]=admObj[i].getTmlNo();
			    		  }
			    		  req.setAttribute("TerminalNoArray", tmlNo); 
		    			  }
		    			  }catch(Exception e){
		    				  e.printStackTrace();
		    			  }
		    		  }
		    		 
		    		  if(user_activity.getFlag().equalsIgnoreCase("search")){
		    			  System.out.println("==========inside search of Action==============");
		    			  try{
		    				  if(user_activity.getCkDate()!=null){
			    			  if(user_activity.getCkDate().equalsIgnoreCase("Date")){
			    			  user_activity.setFrmDate(admDelegate.getSysDate());
				    		  user_activity.setToDate(admDelegate.getSysDate());
				    		  req.setAttribute("ShowDates", "ShowDate");
			    			  }
		    				  }
		    				  if(user_activity.getIpAddr()!=null){
			    			  if(user_activity.getIpAddr().equalsIgnoreCase("IpAddress")){
				    		  ArrayList al=admDelegate.getDistinctTmlAddr();
				    		  req.setAttribute("IpAddress", al);
			    			  }
		    				  }
		    				  if(user_activity.getUid()!=null){
			    			  if(user_activity.getUid().equalsIgnoreCase("userid")){
				    		  String[] users=admDelegate.getUsers();
				    		  req.setAttribute("UserDetails",users);
			    			  }
		    				  }
		    				  if(user_activity.getTml()!=null){
			    			  if(user_activity.getTml().equalsIgnoreCase("tml")){
				    		  AdministratorObject[] admObj=admDelegate.getTmlCodes();
				    		  String[] tmlNo=new String[admObj.length];
				    		  for(int i=0;i<admObj.length;i++){
				    			  tmlNo[i]=admObj[i].getTmlNo();
				    		  }
				    		  req.setAttribute("TerminalNoArray", tmlNo); 
			    			  }
		    				  }
			    			  }catch(Exception e){
			    				  e.printStackTrace();
			    			  }
		    				
		    				if(user_activity.getFrmDate().toString().trim()!=null)
		    				    fdate=user_activity.getFrmDate().toString().trim();
		    		        else
		    		        	fdate="";
		    		        if(user_activity.getToDate().toString().trim()!=null)
		    				   tdate=user_activity.getToDate().toString().trim();
		    		        else
		    		        	tdate="";
		    		        if(user_activity.getUidValue().toString().trim()!=null)
		    				    usrid=user_activity.getUidValue().toString().trim();
		    		        else
		    		        	usrid="";
		    		        if(user_activity.getIpAddrValue().toString().trim()!=null)
		    				    ipadd=user_activity.getIpAddrValue().toString().trim();
		    		        else
		    		        	ipadd="";
		    		        if(user_activity.getTmlNo().toString().trim()!=null)
		    				    tNo=user_activity.getTmlNo().toString().trim();
		    		        else
		    		        	tNo="";
		    				
		    				try{
		    					if(user_activity.getCkDate().equalsIgnoreCase("Date")){
		    						date="1";
		    					}
		    					if(user_activity.getIpAddr().equalsIgnoreCase("IpAddress")){
		    						ip="1";
		    					}
		    					if(user_activity.getUid().equalsIgnoreCase("userid")){
		    						uid="1";
		    					}
		    					if(user_activity.getTml().equalsIgnoreCase("tml")){
		    						tml="1";
		    					}
		    					
		    					
		    					user=admDelegate.getUserActivity(""+tNo, ""+usrid, fdate, tdate, ""+ipadd,(date+ip+uid+tml));
		    					
		    				}catch(Exception ee){
		    			 ee.printStackTrace();
		    			  
		    		  }
		    				Object data[][]=new Object[user.length][10];
		    				if(user!=null){
		    				for(int i=0;i<user.length;i++){
		    					data[i][0]=user[i].getUser_id();
		    					data[i][1]=user[i].getTml_no();
		    					data[i][2]=user[i].getIp_address();
		    					data[i][3]=user[i].getPage_visit();
		    					data[i][4]=user[i].getActivity();
		    					data[i][5]=user[i].getAc_type();
		    					data[i][6]=user[i].getAc_no();
		    					data[i][7]=user[i].getCid();
		    					data[i][8]=user[i].getActivity_date();
		    					data[i][9]=user[i].getActivity_time();
		    					
		    				}}
		    				else
		    					user_activity.setValidations("Records Not Found");
		    				//System.out.println("UserActivity oBject in Action length============"+data.length);
		    				req.setAttribute("UserActivityObj", data);
		    		  }
		    		  if(user_activity.getFlag().equalsIgnoreCase("clear")){
		    			  
		    		  }
		    			 
		    		 System.out.println("******************************=="+form);
			    	  System.out.println("the page path is "+map.getPath().trim());
			    	  System.out.println("******************************=="+user_activity);
			    	  System.out.println("the page id is "+user_activity.getPageId());
			    	  req.setAttribute("pagenum", user_activity.getPageId());
			    	  
		    		return map.findForward(ResultHelp.getSuccess());
		    	  }
		    	  else
		    	  {
		    		  path=MenuNameReader.getScreenProperties("0000");
		    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
		    		  return map.findForward(ResultHelp.getError());
		    	  }
		      }
		      catch(Exception e)
		      {
		    	  path=MenuNameReader.getScreenProperties("0000");
		    	  setErrorPageElements(""+e, req, path);
		    	  return map.findForward(ResultHelp.getError());
		      }
		}

    /* ************************Administrator UserActivity Action ends here************** */
    /* ************************Administrator HolidayMaster Action starts here********* */
    	if(map.getPath().trim().equalsIgnoreCase("/Administrator/HolidayMasterMenu"))
		{
	      try{
	    	  HolidayMasterFormBean holiday_master=(HolidayMasterFormBean)form;
	    	  Object data[][]=null;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+holiday_master);
	    	  System.out.println("the page id is "+holiday_master.getPageId());
	    	  req.setAttribute("pagenum", holiday_master.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(holiday_master.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(holiday_master.getPageId());
	    		  admDelegate=new AdministratorDelegate();
	    		  
	    		  
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  AdministratorObject array_holiday[]=null;
	    		  try {
	    				array_holiday=admDelegate.showholidays();
	    				if(array_holiday.length>0)
	    				{
	    					data = new Object[array_holiday.length][7];
	    					
	    					
	    					try{
	    						
	    						for(int i=0;i<array_holiday.length;i++)
	    							{

	    								
	    								data[i][0]="  "+array_holiday[i].getdate();
	    								
	    								data[i][1]="  "+array_holiday[i].getday();
	    								data[i][2]="  "+array_holiday[i].getreason();
	    								data[i][3]="  "+array_holiday[i].getbr_name();
	    								data[i][4]="  "+array_holiday[i].getde_tml();
	    								data[i][5]="  "+array_holiday[i].getdeuser();
	    								data[i][6]="  "+array_holiday[i].getdate1();
	    								
	    							}
	    						
	    					}
	    					catch(Exception e)
	    					{
	    						e.printStackTrace();
	    					}
	
	    					
	    				}
	    				else
	    				{
	    					
	    					holiday_master.setValidations("No Records Found");
	    				}
	    				
	    				
	    				
	    			} catch (RemoteException e1) {
	    				
	    				e1.printStackTrace();
	    			}
	    			req.setAttribute("HolidayObj", data);
	    			//code for path----->20/10/2011
	    			path=MenuNameReader.getScreenProperties("10007"); 
	    		  
	    		  return map.findForward(ResultHelp.getSuccess());
	    	  }
	    	  else
	    	  {
	    		  path=MenuNameReader.getScreenProperties("0000");
	    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
	    		  return map.findForward(ResultHelp.getError());
	    	  }
	      }
	      catch(Exception e)
	      {
	    	  path=MenuNameReader.getScreenProperties("0000");
	    	  setErrorPageElements(""+e, req, path);
	    	  return map.findForward(ResultHelp.getError());
	      }
		}
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/Administrator/HolidayMaster")){
			try{
				HolidayMasterFormBean holiday_master=(HolidayMasterFormBean)form;
				
		    	 
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(holiday_master.getPageId()))
		    	  {
		    		  
		    		  path=MenuNameReader.getScreenProperties(holiday_master.getPageId());
		    		  admDelegate=new AdministratorDelegate();
		    		  
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  //code for view starts here
		    		  if(holiday_master.getFlag().equalsIgnoreCase("view")){
		    		  Object[][] data=null;
		    		  AdministratorObject array_holiday[]=null;
		    		  try {
		    				array_holiday=admDelegate.showholidays();
		    				if(array_holiday.length>0)
		    				{
		    					data = new Object[array_holiday.length][7];
		    					
		    					
		    					try{
		    						
		    						for(int i=0;i<array_holiday.length;i++)
		    							{

		    								
		    								data[i][0]="  "+array_holiday[i].getdate();
		    								
		    								data[i][1]="  "+array_holiday[i].getday();
		    								data[i][2]="  "+array_holiday[i].getreason();
		    								data[i][3]="  "+array_holiday[i].getbr_name();
		    								data[i][4]="  "+array_holiday[i].getde_tml();
		    								data[i][5]="  "+array_holiday[i].getdeuser();
		    								data[i][6]="  "+array_holiday[i].getdate1();
		    								
		    							}
		    						
		    					}
		    					catch(Exception e)
		    					{
		    						e.printStackTrace();
		    					}
		
		    					
		    				}
		    				else
		    				{
		    					
		    					holiday_master.setValidations("No Records Found");
		    				}
		    				
		    				
		    				
		    			} catch (RemoteException e1) {
		    				
		    				e1.printStackTrace();
		    			}
		    			req.setAttribute("HolidayObj", data);
		    		  }
		    		  

		    		  //code for view ends here
		    		  if(holiday_master.getFlag().equalsIgnoreCase("clear")){
		    			  
		    		  }
                      if(holiday_master.getFlag().equalsIgnoreCase("delete")){
                      String result=null;
		              int count=0;
		              String[] day=null;
		              String[] ckBox=null;
		              ckBox=(String[])req.getParameterValues("ckBox");
		              day=new String[ckBox.length];
		    			 String[] dayValues=(String[])req.getParameterValues("txt1");
		    			 for(int i=0;i<ckBox.length;i++){
		    				 int x=Integer.parseInt(ckBox[i].trim());
		    				 System.out.println("ckBoxOn values are========"+x);
		    				 System.out.println("SELECTED IP ADDRESSES ARE======="+dayValues[x]);
		    				 day[count++]=dayValues[x].trim();
		    				 
		    			 }
		    			 for(int i=0;i<day.length;i++)
		    				 System.out.println("day VAlues are===="+day[i]);
		    			 try{
		    					
	    					   
		    				 result=admDelegate.deleteHoliday(day);
		    				 System.out.println("result values is"+result);
	    						if(result.equalsIgnoreCase("success"))
	    							holiday_master.setValidations("The data is deleted successfully");
	    						else
	    							holiday_master.setValidations("Unable to delete");
	    					}
	    			 
	    	         
	    			     
	    				catch(Exception ex){ex.printStackTrace();}
	    			
		    			 AdministratorObject array_holiday[]=null;
		    			  Object[][] data=null;
			    		  try {
			    				array_holiday=admDelegate.showholidays();
			    				if(array_holiday.length>0)
			    				{
			    					data = new Object[array_holiday.length][7];
			    					
			    					
			    					try{
			    						
			    						for(int i=0;i<array_holiday.length;i++)
			    							{

			    								
			    								data[i][0]="  "+array_holiday[i].getdate();
			    								
			    								data[i][1]="  "+array_holiday[i].getday();
			    								data[i][2]="  "+array_holiday[i].getreason();
			    								data[i][3]="  "+array_holiday[i].getbr_name();
			    								data[i][4]="  "+array_holiday[i].getde_tml();
			    								data[i][5]="  "+array_holiday[i].getdeuser();
			    								data[i][6]="  "+array_holiday[i].getdate1();
			    								
			    							}
			    						
			    					}
			    					catch(Exception e)
			    					{
			    						e.printStackTrace();
			    					}
			
			    					
			    				}
			    				else
			    				{
			    					
			    					holiday_master.setValidations("No Records Found");
			    				}
			    				
			    				
			    				
			    			} catch (RemoteException e1) {
			    				
			    				e1.printStackTrace();
			    			}
			    			req.setAttribute("HolidayObj", data);

		    		  }
		    		  if(holiday_master.getFlag().equalsIgnoreCase("submit")){
		    			  //code for submit starts here
		    			    String day[]=(String[])req.getParameterValues("txt2");
		    				String date[]=(String[])req.getParameterValues("txt1");
		    				String reason[]=(String[])req.getParameterValues("txt3");
		    				String br_name[]=(String[])req.getParameterValues("txt4");
		    				String tml[]=(String[])req.getParameterValues("txt5");
		    				String sysdate[]=(String[])req.getParameterValues("txt7");
		    				String uid[]=(String[])req.getParameterValues("txt6");
		    				try	
		    				{
		    					admDelegate.insertHoliday(day, date, reason,br_name,tml,uid,sysdate);  //call to the insertholiday method in the adminBean 
		    					holiday_master.setValidations("The data is sucessfully submitted");
		    				}
		    				catch(Exception ex)
		    				{
		    					ex.printStackTrace();
		    					
		    				}
		    			  //code for submit ends here
		    			  AdministratorObject array_holiday[]=null;
		    			  Object[][] data=null;
			    		  try {
			    				array_holiday=admDelegate.showholidays();
			    				if(array_holiday.length>0)
			    				{
			    					data = new Object[array_holiday.length][7];
			    					
			    					
			    					try{
			    						
			    						for(int i=0;i<array_holiday.length;i++)
			    							{

			    								
			    								data[i][0]="  "+array_holiday[i].getdate();
			    								
			    								data[i][1]="  "+array_holiday[i].getday();
			    								data[i][2]="  "+array_holiday[i].getreason();
			    								data[i][3]="  "+array_holiday[i].getbr_name();
			    								data[i][4]="  "+array_holiday[i].getde_tml();
			    								data[i][5]="  "+array_holiday[i].getdeuser();
			    								data[i][6]="  "+array_holiday[i].getdate1();
			    								
			    							}
			    						
			    					}
			    					catch(Exception e)
			    					{
			    						e.printStackTrace();
			    					}
			
			    					
			    				}
			    				else
			    				{
			    					
			    					holiday_master.setValidations("No Records Found");
			    				}
			    				
			    				
			    				
			    			} catch (RemoteException e1) {
			    				
			    				e1.printStackTrace();
			    			}
			    			req.setAttribute("HolidayObj", data);

		    			  
		    		  }
		    		  if(holiday_master.getFlag().equalsIgnoreCase("addrow")){
		    			  /*holiday_master.setTxt5(tml);
		    			  holiday_master.setTxt6(user);
		    			  holiday_master.setTxt7(admDelegate.getSysDate()+" "+admDelegate.getSysTime());
		    			  */AdministratorObject array_holiday[]=null;
		    			  Object[][] data=null;
			    		  try {
			    				array_holiday=admDelegate.showholidays();
			    				if(array_holiday.length>0)
			    				{
			    					data = new Object[array_holiday.length][7];
			    					
			    					
			    					try{
			    						
			    						for(int i=0;i<array_holiday.length;i++)
			    							{

			    								
			    								data[i][0]="  "+array_holiday[i].getdate();
			    								
			    								data[i][1]="  "+array_holiday[i].getday();
			    								data[i][2]="  "+array_holiday[i].getreason();
			    								data[i][3]="  "+array_holiday[i].getbr_name();
			    								data[i][4]="  "+array_holiday[i].getde_tml();
			    								data[i][5]="  "+array_holiday[i].getdeuser();
			    								data[i][6]="  "+array_holiday[i].getdate1();
			    								
			    							}
			    						
			    					}
			    					catch(Exception e)
			    					{
			    						e.printStackTrace();
			    					}
			
			    					
			    				}
			    				else
			    				{
			    					
			    					holiday_master.setValidations("No Records Found");
			    				}
			    				
			    				
			    				
			    			} catch (RemoteException e1) {
			    				
			    				e1.printStackTrace();
			    			}
			    			req.setAttribute("HolidayObj", data);
			    		  

		    			  req.setAttribute("AddRow", "addRow");
		    			 
		    		  }
		    		   
		    		 System.out.println("******************************=="+form);
			    	  System.out.println("the page path is "+map.getPath().trim());
			    	  System.out.println("******************************=="+holiday_master);
			    	  System.out.println("the page id is "+holiday_master.getPageId());
			    	  req.setAttribute("pagenum", holiday_master.getPageId());
			    	  
		    		return map.findForward(ResultHelp.getSuccess());
		    	  }
		    	  else
		    	  {
		    		  path=MenuNameReader.getScreenProperties("0000");
		    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
		    		  return map.findForward(ResultHelp.getError());
		    	  }
		      }
		      catch(Exception e)
		      {
		    	  path=MenuNameReader.getScreenProperties("0000");
		    	  setErrorPageElements(""+e, req, path);
		    	  return map.findForward(ResultHelp.getError());
		      }
		}
	
    	
    /* ************************Administrator HolidayMaster Action ends here********* */
    /* ************************Administrator CreateUsers Action Starts here************** */
    	if(map.getPath().trim().equalsIgnoreCase("/Administrator/CreateUsersMenu")){
	      try{
	    	  CreateUsersFormBean createusers=(CreateUsersFormBean)form;
	    	  req.setAttribute("pagenum", createusers.getPageId());
	    	  if(MenuNameReader.containsKeyScreen(createusers.getPageId())){
	    		  path=MenuNameReader.getScreenProperties(createusers.getPageId());
	    		  admDelegate=new AdministratorDelegate();
	    		  req.setAttribute("Branch",admDelegate.getBranchMaster());
	    		  req.setAttribute("pageId",path);
	    		  createusers.setAcFrmDate(AdministratorDelegate.getSysDate());
	    		  createusers.setAcToDate(AdministratorDelegate.getSysDate());
	    		  return map.findForward(ResultHelp.getSuccess());
	    	  }
	    	  else
	    	  {
	    		  path=MenuNameReader.getScreenProperties("0000");
	    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
	    		  return map.findForward(ResultHelp.getError());
	    	  }
	      }
	      catch(Exception e)
	      {
	    	  path=MenuNameReader.getScreenProperties("0000");
	    	  setErrorPageElements(""+e, req, path);
	    	  return map.findForward(ResultHelp.getError());
	      }
		}
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/Administrator/CreateUsers")){
			try{
				CreateUsersFormBean createusers=(CreateUsersFormBean)form;
				if(MenuNameReader.containsKeyScreen(createusers.getPageId())){
					int type=0;
					AdministratorObject[] array_obj_user=null;
		    		Object data[][]=null;
		    		AdministratorObject adminObj=null; 
		    		AdministratorObject adminObject1=new AdministratorObject();
		    		CustomerMasterObject customermasterobject=null;
		    		path=MenuNameReader.getScreenProperties(createusers.getPageId());
		    		admDelegate=new AdministratorDelegate();
		    		req.setAttribute("pageId",path);
		    		if(createusers.getFlag()!=null){
		    		if(createusers.getFlag().equalsIgnoreCase("from_cid")){
		    			int cust_id=0;
	    		  		if(createusers.getCid()!=null && createusers.getCid().trim().length()!=0)
	    		  			cust_id=Integer.parseInt(createusers.getCid());
		    			try{
		    				if(admDelegate.getVerifiedCustomer(Integer.parseInt(createusers.getCid().trim()))){
		    					try{
		    						if(cust_id!=0){
		    							customermasterobject=admDelegate.getCustomer(cust_id);
		    							req.setAttribute("personalInfo", customermasterobject);
		    					    }
		    					   }catch(Exception exception){
		    						   exception.printStackTrace();
		    					   }
		    					}else{
		    						createusers.setValidations("Customer ID is not Verified");
		    					}
		    				}catch(Exception erx){
		    					createusers.setValidations("Customer ID is not Found");
		    				}
		    		  String perPath=MenuNameReader.getScreenProperties("0001");
		    		  req.setAttribute("flag",perPath);
		    		  req.setAttribute("panelName", CommonPanelHeading.getPersonal());
		    	  }
		    	  if(createusers.getFlag().equalsIgnoreCase("from_uid")){
		    		  try{
		    			  adminObj=(AdministratorObject)admDelegate.retrieveCustDetailNew(createusers.getUid().trim());
		    			  if(adminObj!=null){
		    				  createusers.setCid(adminObj.getCust_id()+"");
		    				  int  cust_id=adminObj.getCust_id();
		    				  try{
		    					  if(admDelegate.getVerifiedCustomer(adminObj.getCust_id())){
		    						  try{
		    							  if(cust_id!=0){
		    								  customermasterobject=admDelegate.getCustomer(cust_id);
		    								  req.setAttribute("personalInfo", customermasterobject);
		    							  }
		    						  }catch(Exception exception){
		    							  exception.printStackTrace();
		    						  }
		    					  } else {
		    						  createusers.setValidations("Customer ID is not Verified");
		    					  }
		    				  }catch(Exception erx){
		    					  createusers.setValidations("Customer ID is not Found");
		    				  }
		    				  createusers.setName(adminObj.getShortName());
		    				  createusers.setPwd(adminObj.getPassword());
		    				  createusers.setRePwd(adminObj.getPassword());
		    				  createusers.setPwdExpPeriod(adminObj.getPass_expiry_period()+"");
		    				  createusers.setPwdExpDate(adminObj.getPass_expiry_date());
		    				  createusers.setAcFrmDate(adminObj.getAcc_operation_fromDate());
		    				  createusers.setAcToDate(adminObj.getAcc_oper_toDate());
		    				  if(adminObject1.getDisable()){
		    					  createusers.setUserChoice("Disable");
		    				  }else{
		    					  createusers.setUserChoice("Enable");
		    				  }
		    			  }
		    			 }catch(Exception e){
		    				 e.printStackTrace();
		    			 }
		    			 String perPath=MenuNameReader.getScreenProperties("0001");
		    			 req.setAttribute("flag",perPath);
		    			 req.setAttribute("panelName", CommonPanelHeading.getPersonal());  
		    		  }	
		    		  if(createusers.getFlag().equalsIgnoreCase("update")){
		    			 try{
		    				 adminObj=(AdministratorObject)admDelegate.retrieveCustDetailNew(createusers.getUid().trim());
		    				 if(adminObj!=null){
		    					int cust=0;
				    		  	if(createusers.getCid()!=null && createusers.getCid().trim().length()!=0)
				    		  		cust=Integer.parseInt(createusers.getCid());
		    					String short_names=createusers.getName();
		    					String userID=createusers.getUid();
		    					String password=new String(createusers.getPwd());
		    					String re_pass=new String(createusers.getRePwd());
		    					int pass_expiry_per=0;
			    		  		if(createusers.getPwdExpPeriod()!=null && createusers.getPwdExpPeriod().trim().length()!=0)
			    		  			pass_expiry_per=Integer.parseInt(createusers.getPwdExpPeriod());
		    					String pass_expiry_date=createusers.getPwdExpDate();
		    					String acc_oper_fromDate=createusers.getAcFrmDate();
		    					String acc_oper_toDate=createusers.getAcToDate();
		    					adminObject1.setCust_id(cust);
		    					adminObject1.setShortName(short_names);
		    					adminObject1.setUid(userID);
		    					adminObject1.setPassword(password);
		    					adminObject1.setRe_type_pass(re_pass);
		    					adminObject1.setPass_expiry_period(pass_expiry_per);
		    					adminObject1.setPass_expiry_date(pass_expiry_date);
		    					adminObject1.setAcc_operation_fromDate(acc_oper_fromDate);
		    					adminObject1.setAcc_oper_toDate(acc_oper_toDate);
		    					adminObject1.setDe_date(AdministratorDelegate.getSysDate());
		    					adminObject1.setDe_tml(tml);
		    					adminObject1.setDe_user(user);
		    					if(createusers.getUserChoice().equalsIgnoreCase("Disable")){
		    						adminObject1.setDisable(false);
		    					}else{
		    						adminObject1.setDisable(true);
		    					}
		    					try{
		    						admDelegate.updateCreate(adminObject1,adminObj);
		    						createusers.setValidations("Please notedown the updated UserID:"+userID);
		    					}catch(RecordNotInsertedException ee){
		    					    createusers.setValidations("User information is not updated");
		    					}
		    				}	      					    
		    			 }catch(Exception ex){
		    				 createusers.setValidations("UserID information is not updated");
		    				 ex.printStackTrace();
		    			 }
		    		  	}
		    		  	if(createusers.getFlag().equalsIgnoreCase("delete")){
		    		  		int i=0;
		    		  		try{
		    		  			i=admDelegate.deleteCreateUser(Integer.parseInt(createusers.getUid()));
		    		  			if(i > 0) {
		    		  				createusers.setValidations("UserID Successfully deleted");
		    				    }
		    		  			if(i==0){
		    		  				createusers.setValidations("cant find that record");
		    		  			}
		    		  		}catch(Exception ee){
		    		  			createusers.setValidations("UserID information is not deleted");
		    		  		}
		    		  	}
		    		  	if(createusers.getFlag().equalsIgnoreCase("submit")){
		    		  		AdministratorObject adminObject=new AdministratorObject();
		    		  		int cust=0;
		    		  		if(createusers.getCid()!=null && createusers.getCid().trim().length()!=0)
		    		  			cust=Integer.parseInt(createusers.getCid());
		    		  		String short_names=createusers.getName();
		    		  		String userID=createusers.getUid();
		    		  		String password=new String(createusers.getPwd());
		    		  		String re_pass=new String(createusers.getRePwd());
		    		  		int pass_expiry_per=0;
		    		  		if(createusers.getPwdExpPeriod()!=null && createusers.getPwdExpPeriod().trim().length()!=0)
		    		  			pass_expiry_per=Integer.parseInt(createusers.getPwdExpPeriod());
		    		  		String pass_expiry_date=createusers.getPwdExpDate();
		    		  		String acc_oper_fromDate=createusers.getAcFrmDate();
		    		  		String acc_oper_toDate=createusers.getAcToDate();
		    		  		adminObject.setCust_id(cust);
		    		  		adminObject.setShortName(short_names);
		    		  		adminObject.setUid(userID);
		    		  		adminObject.setPassword(password);
		    		  		adminObject.setRe_type_pass(re_pass);
		    		  		adminObject.setPass_expiry_period(pass_expiry_per);
		    		  		adminObject.setPass_expiry_date(pass_expiry_date);
		    		  		adminObject.setAcc_operation_fromDate(acc_oper_fromDate);
		    		  		adminObject.setAcc_oper_toDate(acc_oper_toDate);
		    		  		adminObject.setDe_date(AdministratorDelegate.getSysDate());
		    		  		adminObject.setDe_tml(tml);
		    		  		adminObject.setDe_user(user);
		    		  		if(createusers.getUserChoice().equalsIgnoreCase("Disable")){
		    		  			adminObject.setDisable(false);
		    		  		}else{
		    		  			adminObject.setDisable(true);
		    		  		}
		    		  		try{
		    		  			int i=admDelegate.creatingUser(adminObject,0);
		    		  			if(i>0){
		    		  				createusers.setValidations("Please notedown the UserID :"+userID);
		    		  			}else{
		    		  				createusers.setValidations("Enter the details for Creation!!");	
		    		  			}
		    		  		}catch(RecordNotInsertedException rec){
		    		  			createusers.setValidations("UserID is not Created");
		    		  		}catch(Exception ee){
		    		  			ee.printStackTrace();
		    			    }
		    		  	}
		    		  	if(createusers.getFlag().equalsIgnoreCase("from_pwd")){
		    		  		String str=new String(createusers.getPwd());
		    				String str1=new String(createusers.getRePwd());
		    				if(!(str.equals(str1))){
		    					createusers.setValidations("password is incorrect re type");              
		    				}
		    				int cust_id=0;
		    		  		if(createusers.getCid()!=null && createusers.getCid().trim().length()!=0)
		    		  			cust_id=Integer.parseInt(createusers.getCid());
		    				try{
		    					if(admDelegate.getVerifiedCustomer(Integer.parseInt(createusers.getCid().trim()))){
		    						try{
		    							if(cust_id!=0){
		    					        	customermasterobject=admDelegate.getCustomer(cust_id);
		    					        	req.setAttribute("personalInfo", customermasterobject);
		    					        }
		    					    }catch(Exception exception){
		    					    	exception.printStackTrace();
		    					    }
		    					} else {
		    						createusers.setValidations("Customer ID is not Verified");
		    					}
		    				}catch(Exception erx){
		    					createusers.setValidations("Customer ID is not Found");
		    				}
		    				String perPath=MenuNameReader.getScreenProperties("0001");
		    				req.setAttribute("flag",perPath);
		    				req.setAttribute("panelName", CommonPanelHeading.getPersonal());
		    			 }
		    		  	if(createusers.getFlag().equalsIgnoreCase("Expiry_period")){
		    			  int i=(int)Integer.parseInt(createusers.getPwdExpPeriod());
		    			  try{
		    				  createusers.setPwdExpDate(Validations.addNoOfMonths(admDelegate.getSysDate(),i));
		    			  }catch(Exception exe){
		    				  exe.printStackTrace();
		    			  }
		    			  int cust_id=0;
		    		  		if(createusers.getCid()!=null && createusers.getCid().trim().length()!=0)
		    		  			cust_id=Integer.parseInt(createusers.getCid());
		    			  try{
		    				  if(admDelegate.getVerifiedCustomer(Integer.parseInt(createusers.getCid().trim()))){
		    					  try{
		    						  if(cust_id!=0){
		    					        	customermasterobject=admDelegate.getCustomer(cust_id);
		    					        	req.setAttribute("personalInfo", customermasterobject);
		    					        }
		    					    }catch(Exception exception){
		    					    	exception.printStackTrace();
		    					    }
		    					}else{
		    						createusers.setValidations("Customer ID is not Verified");
		    					}
		    				}catch(Exception erx){
		    					createusers.setValidations("Customer ID is not Found");
		    				}
		    				String perPath=MenuNameReader.getScreenProperties("0001");
		    				req.setAttribute("flag",perPath);
		    				req.setAttribute("panelName", CommonPanelHeading.getPersonal());
		    		  	}
		    		}
		    		  	req.setAttribute("pagenum", createusers.getPageId());
		    		  	return map.findForward(ResultHelp.getSuccess());
		    	  	}else{
		    		  path=MenuNameReader.getScreenProperties("0000");
		    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
		    		  return map.findForward(ResultHelp.getError());
		    	  	}
		      	}catch(Exception e){
		    	  path=MenuNameReader.getScreenProperties("0000");
		    	  e.printStackTrace();
		    	  setErrorPageElements(""+e, req, path);
		    	  return map.findForward(ResultHelp.getError());
		      	}
			}
    /* ************************Administrator CreateUsers Action ends here**************  */
    /* ************************Administrator ChangePwd Action start here**************** */
    	if(map.getPath().trim().equalsIgnoreCase("/Administrator/ChangePwdMenu"))
		{
	      try{
	    	  ChangePwdFormBean change_pwd=(ChangePwdFormBean)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+change_pwd);
	    	  System.out.println("the page id is "+change_pwd.getPageId());
	    	  req.setAttribute("pagenum", change_pwd.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(change_pwd.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(change_pwd.getPageId());
	    		  admDelegate=new AdministratorDelegate();
	    		 
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  
	    		 
	    		  return map.findForward(ResultHelp.getSuccess());
	    	  }
	    	  else
	    	  {
	    		  path=MenuNameReader.getScreenProperties("0000");
	    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
	    		  return map.findForward(ResultHelp.getError());
	    	  }
	      }
	      catch(Exception e)
	      {
	    	  path=MenuNameReader.getScreenProperties("0000");
	    	  setErrorPageElements(""+e, req, path);
	    	  return map.findForward(ResultHelp.getError());
	      }
		}
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/Administrator/ChangePwd")){
			try{
				ChangePwdFormBean change_pwd=(ChangePwdFormBean)form;
				String user_id=change_pwd.getName();
				String[] str=null;
				
				try
				{
					str=admDelegate.getValidUser(user_id);
				}
				catch(Exception es)
				{
					es.printStackTrace();
				}
				
		    	 
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(change_pwd.getPageId()))
		    	  {
		    		  
		    		  path=MenuNameReader.getScreenProperties(change_pwd.getPageId());
		    		  admDelegate=new AdministratorDelegate();
		    		  
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  if(change_pwd.getFlag().equalsIgnoreCase("username")){
		    			  try
		    				{
		    					
		    					
		    					if(str!=null)
		    					{
		    						System.out.println(""+str[0]+"   "+ str[1]);	
		    						if(user_id.equals(str[0])){
		    							//pwd_old_password.requestFocus();
		    						}
		    							
		    					}
		    					else
		    					{	//txt_name.requestFocus();
		    						change_pwd.setValidations("not a valid user");
		    						
		    					}
		    				}
		    				catch(Exception ee)
		    				{
		    					ee.printStackTrace();
		    				}
		    		  }
		    		  if(change_pwd.getFlag().equalsIgnoreCase("oldpwd")){
		    			  String pass=new String(change_pwd.getOldPwd());
		    				System.out.println(pass);
		    				System.out.println(str[1]);
		    				if(pass.equals(str[1]))
		    				{
		    					
		    					//pwd_new_password.requestFocus();
		    				}
		    				else
		    				{	
		    					//pwd_old_password.setText("");
		    					//pwd_old_password.requestFocus();
		    					change_pwd.setValidations("Warning:\nPassword is incorrect!!");
		    					
		    				}  
		    			  
		    		  }
		    		  if(change_pwd.getFlag().equalsIgnoreCase("from_pwd")){
		    			  String newpass=new String(change_pwd.getNewPwd());
		    				String confirm=new String (change_pwd.getConfirmPwd());
		    				if(newpass.equals(confirm))
		    				{
		    					//button_submit.requestFocus();
		    				}
		    				else
		    				{
		    					/*pwd_confirm_password.setText("");
		    					pwd_new_password.setText("");
		    					pwd_new_password.requestFocus();*/
		    					change_pwd.setValidations("Warning:\nConfirm password is incorrect!!");
		    					
		    				}
		    		  }
		    		  if(change_pwd.getFlag().equalsIgnoreCase("submit")){
		    			  String a=new String(change_pwd.getNewPwd());
		    				String b=new String(change_pwd.getConfirmPwd());
		    				System.out.println("1......."+a);
		    				System.out.println("2........"+b);
		    				
		    				if(change_pwd.getName().trim().equals("") || new String(change_pwd.getOldPwd()).trim().equals("") || new String(change_pwd.getNewPwd()).trim().equals("") || new String(change_pwd.getConfirmPwd()).trim().equals("") )
		    				{
		    						if(change_pwd.getName().trim().equals("") && new String(change_pwd.getOldPwd()).trim().equals("") && new String(change_pwd.getNewPwd()).trim().equals("") && new String(change_pwd.getConfirmPwd()).trim().equals("") )
		    						{
		    							change_pwd.setValidations("Please fill in details for Updation");
		    						}
		    						
		    						else if(change_pwd.getName().trim().equals(""))
		    						{
		    							change_pwd.setValidations("Please enter User Name");
		    						}
		    						
		    						else if(new String(change_pwd.getOldPwd()).trim().equals("") )
		    						{
		    							change_pwd.setValidations("Please enter Old Password");
		    						}
		    						
		    						else if(new String(change_pwd.getNewPwd()).trim().equals(""))
		    						{
		    							change_pwd.setValidations("Please enter New Password");
		    						}
		    						
		    						else if(new String(change_pwd.getConfirmPwd()).trim().equals(""))
		    						{
		    							change_pwd.setValidations("Please re-type the password to confirm ");
		    						}
		    						
		    					}	
		    				
		    				else
		    				{
		    					if(!(a.equals(b)) )
		    						{
		    							System.out.println("3......."+a);
		    							System.out.println("4........"+b);
		    							change_pwd.setValidations("Please retype the password correctly");
		    						}
		    					else
		    					{
		    						
		    	       	        	
		    	      	               	try
		    	      	               	{
		    	      	                   	
		    	      	              	 	
		    	      	        				int remark=admDelegate.createUserPassword(change_pwd.getName(), new String(change_pwd.getOldPwd()), new String(change_pwd.getNewPwd()) ) ;
		    	      	        				if(remark==1)
		    	      	        					{
		    	      	        					change_pwd.setValidations("Updation Over");
		    	      	        						
		    	      	        					}
		    	      	        					
		    	      	        				else
		    	      	        					change_pwd.setValidations("Unable to Update" +"\n"+ "User Name / Old Password may be wrong !");
		    	      	        			
		    	      	        			  
		    	      	        		}catch(Exception e1){e1.printStackTrace();}
		    						 
		    					}	
		    				}
		    		  }
		    		  System.out.println("******************************=="+form);
			    	  System.out.println("the page path is "+map.getPath().trim());
			    	  System.out.println("******************************=="+change_pwd);
			    	  System.out.println("the page id is "+change_pwd.getPageId());
			    	  req.setAttribute("pagenum", change_pwd.getPageId());
			    	  
		    		return map.findForward(ResultHelp.getSuccess());
		    	  }
		    	  else
		    	  {
		    		  path=MenuNameReader.getScreenProperties("0000");
		    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
		    		  return map.findForward(ResultHelp.getError());
		    	  }
		      }
		      catch(Exception e)
		      {
		    	  path=MenuNameReader.getScreenProperties("0000");
		    	  setErrorPageElements(""+e, req, path);
		    	  return map.findForward(ResultHelp.getError());
		      }
		}

    /* *************************Administrator ChangePwd Action Ends here**************** */
    /* *************************Administrator Lockers Tml Types Action Starts Here******** */
    	if(map.getPath().trim().equalsIgnoreCase("/Administrator/LockersTmlDetailsMenu"))
		{
	      try{
	    	  LockersTmlDetFB lockers=(LockersTmlDetFB)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+lockers);
	    	  System.out.println("the page id is "+lockers.getPageId());
	    	  req.setAttribute("pagenum", lockers.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(lockers.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(lockers.getPageId());
	    		  admDelegate=new AdministratorDelegate();
	    		 
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  
	    		  return map.findForward(ResultHelp.getSuccess());
	    	  }
	    	  else
	    	  {
	    		  path=MenuNameReader.getScreenProperties("0000");
	    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
	    		  return map.findForward(ResultHelp.getError());
	    	  }
	      }
	      catch(Exception e)
	      {
	    	  path=MenuNameReader.getScreenProperties("0000");
	    	  setErrorPageElements(""+e, req, path);
	    	  return map.findForward(ResultHelp.getError());
	      }
		}
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/Administrator/LockersTmlDetails")){
			try{
				LockersTmlDetFB lockers=(LockersTmlDetFB)form;
		       	  
		    	 
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(lockers.getPageId()))
		    	  {
		    		  int type=0;
		    		  AdministratorObject[] array_obj_user;
		    		  Object data[][]=null;
		    		  path=MenuNameReader.getScreenProperties(lockers.getPageId());
		    		  admDelegate=new AdministratorDelegate();
		    		  
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  if(lockers.getFlag().equalsIgnoreCase("readAll")){
		    			  
		    			 }
		    		  System.out.println("******************************=="+form);
			    	  System.out.println("the page path is "+map.getPath().trim());
			    	  System.out.println("******************************=="+lockers);
			    	  System.out.println("the page id is "+lockers.getPageId());
			    	  req.setAttribute("pagenum", lockers.getPageId());
			    	  
		    		return map.findForward(ResultHelp.getSuccess());
		    	  }
		    	  else
		    	  {
		    		  path=MenuNameReader.getScreenProperties("0000");
		    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
		    		  return map.findForward(ResultHelp.getError());
		    	  }
		      }
		      catch(Exception e)
		      {
		    	  path=MenuNameReader.getScreenProperties("0000");
		    	  setErrorPageElements(""+e, req, path);
		    	  return map.findForward(ResultHelp.getError());
		      }
		}
	
    /* ************************Administrator LockersTml Types Action Ends Here******** */
    /* ************************Administrator Terminals Details Action Starts Here******** */
    	if(map.getPath().trim().equalsIgnoreCase("/Administrator/TerminalsDetailsMenu"))
		{
	      try{
	    	  TerminalsDetailsFB tml_details=(TerminalsDetailsFB)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+tml_details);
	    	  System.out.println("the page id is "+tml_details.getPageId());
	    	  req.setAttribute("pagenum", tml_details.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(tml_details.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(tml_details.getPageId());
	    		  admDelegate=new AdministratorDelegate();
	    		  TerminalObject[] tmlObj=null;
	    		  Object arr[]=null;
	    		  try{
	    		  tmlObj=admDelegate.getTmlDetails();
	    		  System.out.println("Inside Action TmlObj size is"+tmlObj.length);
	    		  }catch(Exception e){
	    			  e.printStackTrace();
	    		  }
	    		  arr=new Object[tmlObj.length];
				  for(int i=0;i<tmlObj.length;i++){
					  arr[i]=tmlObj[i].getTmlName();
				  }
				  for(int j=0;j<arr.length;j++)
					  System.out.println("arrrrrrrrr values are "+arr[j]);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  req.setAttribute("TmlDetails", arr);
	    		  return map.findForward(ResultHelp.getSuccess());
	    	  }
	    	  else
	    	  {
	    		  path=MenuNameReader.getScreenProperties("0000");
	    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
	    		  return map.findForward(ResultHelp.getError());
	    	  }
	      }
	      catch(Exception e)
	      {
	    	  path=MenuNameReader.getScreenProperties("0000");
	    	  setErrorPageElements(""+e, req, path);
	    	  return map.findForward(ResultHelp.getError());
	      }
		}
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/Administrator/TerminalsDetails")){
			try{
				TerminalsDetailsFB tml_details=(TerminalsDetailsFB)form;
		       	  
		    	 
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(tml_details.getPageId()))
		    	  {
		    		  
		    		  path=MenuNameReader.getScreenProperties(tml_details.getPageId());
		    		  admDelegate=new AdministratorDelegate();
		    		  TerminalObject[] tmlObj=null;
		    		  Object arr[]=null;
		    		  tmlObj=admDelegate.getTmlDetails();
		    		  System.out.println("tmlobj obj obj length issssssssssssss"+tmlObj.length);
		    		  arr=new Object[tmlObj.length];
    				  for(int i=0;i<tmlObj.length;i++){
    					  arr[i]=tmlObj[i].getTmlName();
    				  }
    				  for(int j=0;j<arr.length;j++)
    					  System.out.println("arrrrrrrrrrrrrrr vlues"+arr[j]);
    				  req.setAttribute("TmlDetails", arr);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  if(tml_details.getFlag().equalsIgnoreCase("from_tmls")){
		    			  if(tml_details.getTmls().equalsIgnoreCase("NewTerminal")){
		    				  tml_details.setTmlName("");
    						  tml_details.setTmlDesc("");
    						  tml_details.setTmlIpAddr("");
    						  tml_details.setTranAmount("");
		    			  }else{
		    				  for(int i=0;i<tmlObj.length;i++){
		    					  if(tmlObj[i].getTmlName().equalsIgnoreCase(tml_details.getTmls())){
		    						  tml_details.setTmlName(tmlObj[i].getTmlName());
		    						  tml_details.setTmlDesc(tmlObj[i].getTmlDesc());
		    						  tml_details.setTmlIpAddr(tmlObj[i].getIPAddress());
		    						  tml_details.setTranAmount(String.valueOf(tmlObj[i].getMaxAmount()));
		    						  
		    						  if(tmlObj[i].getCashier() != null) {	
		    							  if((tmlObj[i].getCashier().equalsIgnoreCase("M"))) {
		    							  tml_details.setTmlType("MainCashier");
		    								}
		    							
		    								if((tmlObj[i].getCashier().equalsIgnoreCase("C"))) {
		    									tml_details.setTmlType("Cashier");
		    								}
		    							} else {
		    								tml_details.setTmlType("NonCashier");
		    							}
		    							
		    							if(tmlObj[i].getStatus() == 1) {
		    								tml_details.setStatus("Status");
		    							} 
		    							
		    							
		    							
		    					  }
		    				  }
		    				  req.setAttribute("Status", "Show");  
		    			  }
		    			  
		    			 }
		    		  //code for submit and update starts here
		    		  
		    		  String type = " ";
		    		  if(tml_details.getFlag().equalsIgnoreCase("submit") || tml_details.getFlag().equalsIgnoreCase("update")){
		  			if(tml_details.getFlag().equalsIgnoreCase("submit")) {
		  				type = "Submit";
		  			}
		  			if(tml_details.getFlag().equalsIgnoreCase("Update")){
		  				type = "Update";
		  			}
		  			
		  			
		  			
		  				try{
		  					TerminalObject tml_object = new TerminalObject();
		  					if(tml_details.getTmlName()!=null){
		  					tml_object.setTmlName(tml_details.getTmlName().trim());
		  					}
		  					if(tml_details.getTmlDesc()!=null){
		  					tml_object.setTmlDesc(tml_details.getTmlDesc().trim());
		  					}
		  					if(tml_details.getTmlIpAddr()!=null){
		  					tml_object.setIPAddress(tml_details.getTmlIpAddr().trim());
		  					if(tml_details.getTmlType()!=null){
		  					if(tml_details.getTmlType().equalsIgnoreCase("Cashier")) {
		  						tml_object.setCashier("C");
		  					} else if(tml_details.getTmlType().equalsIgnoreCase("MainCashier")) {
		  						tml_object.setCashier("M");
		  					} else {
		  						tml_object.setCashier(null);
		  					}
		  					}
		  					if(tml_details.getStatus()!=null){
		  					if(tml_details.getStatus().equalsIgnoreCase("Status")) {
		  						tml_object.setStatus(1);
		  					} else {
		  						tml_object.setStatus(0);
		  					}
		  					}
		  					tml_object.setMaxAmount(Double.parseDouble(tml_details.getTranAmount().trim()));
		  					tml_object.uv.setUserTml(tml);//hard coded
		  					tml_object.uv.setUserId(user);//HARD CODED
		  					tml_object.uv.setUserDate(admDelegate.getSysDate());
		  					admDelegate.setTerminalDetail(tml_object);
		  					//admDelegate.insertAccessValues(tml_create.getHashValues(),txt_tml.getText().trim());
		  					
		  					tml_details.setValidations("The data is sucessfully " + type + "ed");
		  					
		  				}
		  				}catch(Exception exe ){
		  					exe.printStackTrace();
		  					tml_details.setValidations("The data is not sucessfully " + type + "ed");
		  				}
		  				
		  			
		    	  }
		    		  //code for submit and update ends here
		    		  //code for delete Starts here
		    		  if(tml_details.getFlag().equalsIgnoreCase("delete")){
		    			
		    				try {
		    					boolean value = admDelegate.deleteTerminal(tml_details.getTmlName().trim());
		    					if(value) {
		    						tml_details.setValidations("The terminal is sucessfully deleted");
		    					}
		    				} catch (Exception exe) {
		    					exe.printStackTrace();
		    					tml_details.setValidations("The terminal is not sucessfully deleted");
		    				}
		    				
		    			
		    		  }
		    		  //code for delete ends here
		    		  System.out.println("******************************=="+form);
			    	  System.out.println("the page path is "+map.getPath().trim());
			    	  System.out.println("******************************=="+tml_details);
			    	  System.out.println("the page id is "+tml_details.getPageId());
			    	  req.setAttribute("pagenum", tml_details.getPageId());
			    	  
		    		return map.findForward(ResultHelp.getSuccess());
		    	  }
		    	  else
		    	  {
		    		  path=MenuNameReader.getScreenProperties("0000");
		    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
		    		  return map.findForward(ResultHelp.getError());
		    	  }
		      }
		      catch(Exception e)
		      {
		    	  path=MenuNameReader.getScreenProperties("0000");
		    	  setErrorPageElements(""+e, req, path);
		    	  return map.findForward(ResultHelp.getError());
		      }
		}
    	//=======================GL & Module Admin Starts Here===============	
        if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmShareModuleMenu")){
            try{AdmShareModuleForm admSh=(AdmShareModuleForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
               req.setAttribute("pageNum",admSh.getPageId().trim());
               System.out.println("PageId is"+admSh.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admSh.getPageId())){
                   path=MenuNameReader.getScreenProperties(admSh.getPageId());
                   
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
       
   else if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmShareModule")){
	   AdmShareModuleForm admshare=(AdmShareModuleForm)form;
       String path;
       System.out.println("Inside /Administrator/AdmShareModule");
       System.out.println("admshare=="+admshare);
       System.out.println("admshare=="+admshare.getPageId());
       System.out.println("page ID23423423 ");
       try{
           req.setAttribute("pageNum",admshare.getPageId().trim());
           if(MenuNameReader.containsKeyScreen(admshare.getPageId())){
               path=MenuNameReader.getScreenProperties(admshare.getPageId());
               System.out.println(path);
               System.out.println("got the object");
               //admshare.getNewactype()!=null&&
               //if(!admshare.getNewactype().equals("newAccountType")){
               admDelegate=new AdministratorDelegate();
               admmoduleObject=admDelegate.getGLData("1001001");
               System.out.println("got the object");
               if(admmoduleObject!=null){
            	   req.setAttribute("admObject",admmoduleObject);
            	   System.out.println("admmoduleObject is not null");
            	   String[] str=admmoduleObject.getModulesValues();
            	   for(int k=0;k<str.length;k++)
            	   System.out.println("Account Name"+str[k]);
            	   if(admshare.getSetv().equals("submit")){
            		   str[0]=admshare.getAcname();
            		   str[1]=admshare.getSname();
            		   str[2]=admshare.getLastacno();
            		   str[3]=admshare.getShDistno();
            		   str[4]=admshare.getMinbal();
            		   str[5]=admshare.getDivcdate();
            		   str[14]=admshare.getLshcertno();
            		   str[24]=admshare.getLtempshno();
            		   str[30]=admshare.getDivrefno();
            		   str[33]=admshare.getTrnno();
            		   str[34]=admshare.getDivcashno();
            		   str[35]=admshare.getLdivwarno();
            		   str[36]=admshare.getDvchno();
                	   admmoduleObject.setModuleCode("1001001");
                	   admmoduleObject.setModulesValues(str);
                	   String result=admDelegate.storeGlModAdmin(admmoduleObject);
                	  req.setAttribute("closingmsg",result);
                	  admshare.setSetv("");
                	   }
                	   else{
            	   admshare.setAcname(str[0]);
            	   admshare.setSname(str[1]);
            	   admshare.setLastacno(str[2]);
            	   admshare.setShDistno(str[3]);
            	   admshare.setMinbal(str[4]);
            	   admshare.setDivcdate(str[5]);
            	   admshare.setLshcertno(str[14]);
            	   admshare.setLtempshno(str[24]);
            	   admshare.setDivrefno(str[30]);
            	   admshare.setTrnno(str[33]);
            	   admshare.setDivcashno(str[34]);
            	   admshare.setLdivwarno(str[35]);
            	   admshare.setDvchno(str[36]);
               }
               }
               
               //}
               req.setAttribute("pageId",path);
               
               
   			
   			
   			return map.findForward(ResultHelp.getSuccess());
           }}
       catch(Exception e){System.out.println("Exception at 332"+e);}
       return map.findForward(ResultHelp.getSuccess());
   }
    	//GL & Admin =========================>Savings Bank
        
        if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmSBModuleMenu")){
            try{AdmSBModuleForm admSb=(AdmSBModuleForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
               req.setAttribute("pageNum",admSb.getPageId().trim());
               System.out.println("PageId is"+admSb.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admSb.getPageId())){
                   path=MenuNameReader.getScreenProperties(admSb.getPageId());
                   
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
       
   else if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmSBModule")){
	   AdmSBModuleForm admSb=(AdmSBModuleForm)form;
       String path;
       System.out.println("Inside /Administrator/AdmSBModule");
       System.out.println("admSb=="+admSb);
       System.out.println("admSb=="+admSb.getPageId());
       System.out.println("page ID23423423 ");
       try{
           req.setAttribute("pageNum",admSb.getPageId().trim());
           if(MenuNameReader.containsKeyScreen(admSb.getPageId())){
               path=MenuNameReader.getScreenProperties(admSb.getPageId());
               System.out.println(path);
               System.out.println("got the object");
               //admshare.getNewactype()!=null&&
               //if(!admshare.getNewactype().equals("newAccountType")){
               admDelegate=new AdministratorDelegate();
               admmoduleObject=admDelegate.getGLData("1002001");
               System.out.println("got the object");
               if(admmoduleObject!=null){
            	   System.out.println("admmoduleObject is not null");
            	   String[] str=admmoduleObject.getModulesValues();
            	   System.out.println("str.length -------->"+str.length);
            	   
            	   for(int k=0;k<str.length;k++)
            	   System.out.println("Account Name"+str[k]);
            	   
            	   
            	   if(admSb.getSetv().equals("submit")){
            	   str[0]=admSb.getAcname();
            	   str[1]=admSb.getSname();
            	   str[2]=admSb.getLastacno();
            	   str[4]=admSb.getMinbal();
            	   str[9]=admSb.getIfdate();
            	   str[10]=admSb.getItdate();
            	   str[11]=admSb.getTrnprmonth();
            	   str[12]=admSb.getLintpaydate();
            	   str[18]=admSb.getMaxjoints();
            	   str[21]=admSb.getLinpassbook();
            	   str[24]=admSb.getInopDays();
            	   str[25]=admSb.getChqvalid();
            	   str[26]=admSb.getMinbalchqm();
            	   str[27]=admSb.getMinbalclr();
            	   admmoduleObject.setModuleCode("1002001");
            	   admmoduleObject.setModulesValues(str);
            	   String result=admDelegate.storeGlModAdmin(admmoduleObject);
            	  req.setAttribute("closingmsg",result);
            	  admSb.setSetv("");
            	   }
            	   else{
            	   admSb.setAcname(str[0]);
            	   admSb.setSname(str[1]);
            	   admSb.setLastacno(str[2]);
            	   admSb.setMinbal(str[4]);
            	   admSb.setIfdate(str[9]);
            	   admSb.setItdate(str[10]);
            	   admSb.setTrnprmonth(str[11]);
            	   admSb.setLintpaydate(str[12]);
            	   admSb.setMaxjoints(str[18]);
            	   admSb.setLinpassbook(str[21]);
            	   admSb.setInopDays(str[24]);
            	   admSb.setChqvalid(str[25]);
            	   admSb.setMinbalchqm(str[26]);
            	   admSb.setMinbalclr(str[27]);
            	   
            	   }
               }
               
               //}
               req.setAttribute("pageId",path);
               
               
   			
   			
   			return map.findForward(ResultHelp.getSuccess());
           }}
       catch(Exception e){System.out.println("Exception at 332"+e);}
       return map.findForward(ResultHelp.getSuccess());
   }
    	//Gl & Module Admin ==========================>Fixed Deposit
        
        
        
        if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmFDModuleMenu")){
            try{AdmFDModuleForm admFd=(AdmFDModuleForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
               req.setAttribute("pageNum",admFd.getPageId().trim());
               System.out.println("PageId is"+admFd.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admFd.getPageId())){
                   path=MenuNameReader.getScreenProperties(admFd.getPageId());
                   
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
       
   else if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmFDModule")){
	   AdmFDModuleForm admFd=(AdmFDModuleForm)form;
       String path;
       System.out.println("Inside /Administrator/AdmFDModule");
       System.out.println("admFd=="+admFd);
       System.out.println("admFd=="+admFd.getPageId());
       System.out.println("page ID23423423 ");
       try{
           req.setAttribute("pageNum",admFd.getPageId().trim());
           if(MenuNameReader.containsKeyScreen(admFd.getPageId())){
               path=MenuNameReader.getScreenProperties(admFd.getPageId());
               System.out.println(path);
               System.out.println("got the object");
               //admshare.getNewactype()!=null&&
               //if(!admshare.getNewactype().equals("newAccountType")){
               admDelegate=new AdministratorDelegate();
               admmoduleObject=admDelegate.getGLData("1003001");
               System.out.println("got the object");
               if(admmoduleObject!=null){
            	   System.out.println("admmoduleObject is not null");
            	   String[] str=admmoduleObject.getModulesValues();
            	   for(int k=0;k<str.length;k++)
            	   System.out.println("Account Name"+str[k]);
            	   
            	   if(admFd.getSetv().equals("submit")){
            		   str[0]=admFd.getAcname();
            		   str[1]=admFd.getSname();
            		   str[2]=admFd.getLastacno();
            		   str[3]=admFd.getMaxrenewal();
            		   str[4]=admFd.getMindepamount();
            		   str[5]=admFd.getMinperiod();
            		   str[7]=admFd.getMaxloanP();
            		   str[8]=admFd.getMaxdepamount();
            		   str[10]=admFd.getMaxperiod();
            		   str[18]=admFd.getMaxjoints();
            		   str[20]=admFd.getPrematrate();
            		   str[34]=admFd.getLastreceipt();
            		   str[35]=admFd.getIntcalvchno();
                	   admmoduleObject.setModuleCode("1003001");
                	   admmoduleObject.setModulesValues(str);
                	   String result=admDelegate.storeGlModAdmin(admmoduleObject);
                	  req.setAttribute("closingmsg",result);
                	  admFd.setSetv("");
                	   }
                	   else{
            	   admFd.setAcname(str[0]);
            	   admFd.setSname(str[1]);
            	   admFd.setLastacno(str[2]);
            	   admFd.setMaxrenewal(str[3]);
            	   admFd.setMindepamount(str[4]);
            	   admFd.setMinperiod(str[5]);
            	   admFd.setMaxloanP(str[7]);
            	   admFd.setMaxdepamount(str[8]);
            	   admFd.setMaxperiod(str[10]);
            	   admFd.setMaxjoints(str[18]);
            	   admFd.setPrematrate(str[20]);
            	   admFd.setLastreceipt(str[34]);
            	   admFd.setIntcalvchno(str[35]);
               }
               }
               
               //}
               req.setAttribute("pageId",path);
               
               
   			
   			
   			return map.findForward(ResultHelp.getSuccess());
           }}
       catch(Exception e){System.out.println("Exception at 332"+e);}
       return map.findForward(ResultHelp.getSuccess());
   }
        //GL & Module Admin==========================>Recurring Deposit
        
        
        if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmRDModuleMenu")){
            try{AdmRDModuleForm admRd=(AdmRDModuleForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
               req.setAttribute("pageNum",admRd.getPageId().trim());
               System.out.println("PageId is"+admRd.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admRd.getPageId())){
                   path=MenuNameReader.getScreenProperties(admRd.getPageId());
                   
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
       
   else if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmRDModule")){
	   AdmRDModuleForm admRd=(AdmRDModuleForm)form;
       String path;
       System.out.println("Inside /Administrator/AdmRDModule");
       System.out.println("admRd=="+admRd);
       System.out.println("admRd=="+admRd.getPageId());
       System.out.println("page ID23423423 ");
       try{
           req.setAttribute("pageNum",admRd.getPageId().trim());
           if(MenuNameReader.containsKeyScreen(admRd.getPageId())){
               path=MenuNameReader.getScreenProperties(admRd.getPageId());
               System.out.println(path);
               System.out.println("got the object");
               //admshare.getNewactype()!=null&&
               //if(!admshare.getNewactype().equals("newAccountType")){
               admDelegate=new AdministratorDelegate();
               admmoduleObject=admDelegate.getGLData("1004001");
               System.out.println("got the object");
               if(admmoduleObject!=null){
            	   System.out.println("admmoduleObject is not null");
            	   String[] str=admmoduleObject.getModulesValues();
            	   for(int k=0;k<str.length;k++)
            	   System.out.println("Account Name"+str[k]);
            	   
            	   if(admRd.getSetv().equals("submit")){
            		   str[0]=admRd.getAcname();
            		   str[1]=admRd.getSname();
            		   str[2]=admRd.getLastacno();
            		   str[18]=admRd.getMaxrenewal();
            		   str[4]=admRd.getMindepamount();
            		   str[5]=admRd.getMinperiod();
            		   str[7]=admRd.getMaxloanP();
            		   str[8]=admRd.getMaxdepamount();
            		   str[10]=admRd.getMaxperiod();
            		   str[9]=admRd.getMaxjoints();
            		   str[20]=admRd.getPrematrate();
            		   str[34]=admRd.getLastreceipt();
                	   admmoduleObject.setModuleCode("1004001");
                	   admmoduleObject.setModulesValues(str);
                	   String result=admDelegate.storeGlModAdmin(admmoduleObject);
                	  req.setAttribute("closingmsg",result);
                	  admRd.setSetv("");
                	   }
                	   else{
            	   
            	   admRd.setAcname(str[0]);
            	   admRd.setSname(str[1]);
            	   admRd.setLastacno(str[2]);
            	   admRd.setMaxrenewal(str[18]);
            	   admRd.setMindepamount(str[4]);
            	   admRd.setMinperiod(str[5]);
            	   admRd.setMaxloanP(str[7]);
            	   admRd.setMaxdepamount(str[8]);
            	   admRd.setMaxperiod(str[10]);
            	   admRd.setMaxjoints(str[9]);
            	   admRd.setPrematrate(str[20]);
            	   admRd.setLastreceipt(str[34]);
            	   
               }
               }
               
               //}
               req.setAttribute("pageId",path);
               
               
   			
   			
   			return map.findForward(ResultHelp.getSuccess());
           }}
       catch(Exception e){System.out.println("Exception at 332"+e);}
       return map.findForward(ResultHelp.getSuccess());
   }
  //GL & Module Admin==========================>PYGMY Deposit
        
        
        if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmPygmyModuleMenu")){
            try{AdmPygmyModuleForm admPd=(AdmPygmyModuleForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
               req.setAttribute("pageNum",admPd.getPageId().trim());
               System.out.println("PageId is"+admPd.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admPd.getPageId())){
                   path=MenuNameReader.getScreenProperties(admPd.getPageId());
                   
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
       
   else if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmPygmyModule")){
	   AdmPygmyModuleForm admPd=(AdmPygmyModuleForm)form;
       String path;
       System.out.println("Inside /Administrator/AdmPygmyModule");
       System.out.println("admPd=="+admPd);
       System.out.println("admPd=="+admPd.getPageId());
       System.out.println("page ID23423423 ");
       try{
           req.setAttribute("pageNum",admPd.getPageId().trim());
           if(MenuNameReader.containsKeyScreen(admPd.getPageId())){
               path=MenuNameReader.getScreenProperties(admPd.getPageId());
               System.out.println(path);
               System.out.println("got the object");
               //admshare.getNewactype()!=null&&
               //if(!admshare.getNewactype().equals("newAccountType")){
               admDelegate=new AdministratorDelegate();
               admmoduleObject=admDelegate.getGLData("1006001");
               System.out.println("got the object");
               if(admmoduleObject!=null){
            	   System.out.println("admmoduleObject is not null");
            	   String[] str=admmoduleObject.getModulesValues();
            	   for(int k=0;k<str.length;k++)
            	   System.out.println("Account Name"+str[k]);
            	   
            	   
            	   if(admPd.getSetv().equals("submit")){
            		   str[0]=admPd.getAcname();
            		   str[1]=admPd.getSname();
            		   str[2]=admPd.getLastacno();
                	   admPd.setMinbal(str[4]);
                	   str[6]=admPd.getPartialwithdraw();
                	   str[7]=admPd.getMaxloanP();
                	   str[8]=admPd.getPartialwithdrawP();
                	   str[5]=admPd.getMinperiod();
                	   str[14]=admPd.getPrematrate();
                	   
                	   str[24]=admPd.getMaxjoints();
                	   admmoduleObject.setModuleCode("1006001");
                	   admmoduleObject.setModulesValues(str);
                	   String result=admDelegate.storeGlModAdmin(admmoduleObject);
                	  req.setAttribute("closingmsg",result);
                	  admPd.setSetv("");
                	   }
                	   else{
            	   admPd.setAcname(str[0]);
            	   admPd.setSname(str[1]);
            	   admPd.setLastacno(str[2]);
            	   admPd.setMinbal(str[4]);
            	   admPd.setPartialwithdraw(str[6]);
            	   admPd.setMaxloanP(str[7]);
            	   admPd.setPartialwithdrawP(str[8]);
            	   admPd.setMinperiod(str[5]);
            	   admPd.setPrematrate(str[14]);
            	   
            	   admPd.setMaxjoints(str[24]);
            	   
               }
               }
               
               //}
               req.setAttribute("pageId",path);
               
               
   			
   			
   			return map.findForward(ResultHelp.getSuccess());
           }}
       catch(Exception e){System.out.println("Exception at 332"+e);}
       return map.findForward(ResultHelp.getSuccess());
   }
        
//GL & Admin =========================>CURRENT ACCOUNT
        
        if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmCAModuleMenu")){
            try{AdmCAModuleForm admCa=(AdmCAModuleForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
               req.setAttribute("pageNum",admCa.getPageId().trim());
               System.out.println("PageId is"+admCa.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admCa.getPageId())){
                   path=MenuNameReader.getScreenProperties(admCa.getPageId());
                   
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
       
   else if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmCAModule")){
	   AdmCAModuleForm admCa=(AdmCAModuleForm)form;
       String path;
       System.out.println("Inside /Administrator/AdmCAModule");
       System.out.println("admCa=="+admCa);
       System.out.println("admCa=="+admCa.getPageId());
       System.out.println("page ID23423423 ");
       try{
           req.setAttribute("pageNum",admCa.getPageId().trim());
           if(MenuNameReader.containsKeyScreen(admCa.getPageId())){
               path=MenuNameReader.getScreenProperties(admCa.getPageId());
               System.out.println(path);
               System.out.println("got the object");
               //admshare.getNewactype()!=null&&
               //if(!admshare.getNewactype().equals("newAccountType")){
               admDelegate=new AdministratorDelegate();
               admmoduleObject=admDelegate.getGLData("1007001");
               System.out.println("got the object");
               if(admmoduleObject!=null){
            	   System.out.println("admmoduleObject is not null");
            	   String[] str=admmoduleObject.getModulesValues();
            	   for(int k=0;k<str.length;k++)
            	   System.out.println("Account Name"+str[k]);
            	   
            	   if(admCa.getSetv().equals("submit")){
            		   str[0]=admCa.getAcname();
            		   str[1]=admCa.getSname();
            		   str[2]=admCa.getLastacno();
            		   str[4]=admCa.getMinbal();
            		   str[9]=admCa.getIfdate();
            		   str[10]=admCa.getItdate();
            		   str[11]=admCa.getTrnprmonth();
            		   str[12]=admCa.getLintpaydate();
            		   str[18]=admCa.getMaxjoints();
            		   str[21]=admCa.getLinpassbook();
            		   str[24]=admCa.getInopDays();
            		   str[25]=admCa.getChqvalid();
            		   str[26]=admCa.getMinbalchqm();
            		   str[27]=admCa.getMinbalclr();
                	   admmoduleObject.setModuleCode("1007001");
                	   admmoduleObject.setModulesValues(str);
                	   String result=admDelegate.storeGlModAdmin(admmoduleObject);
                	  req.setAttribute("closingmsg",result);
                	  admCa.setSetv("");
                	   }
                	   else{
            	   admCa.setAcname(str[0]);
            	   admCa.setSname(str[1]);
            	   admCa.setLastacno(str[2]);
            	   admCa.setMinbal(str[4]);
            	   admCa.setIfdate(str[9]);
            	   admCa.setItdate(str[10]);
            	   admCa.setTrnprmonth(str[11]);
            	   admCa.setLintpaydate(str[12]);
            	   admCa.setMaxjoints(str[18]);
            	   admCa.setLinpassbook(str[21]);
            	   admCa.setInopDays(str[24]);
            	   admCa.setChqvalid(str[25]);
            	   admCa.setMinbalchqm(str[26]);
            	   admCa.setMinbalclr(str[27]);
            	   
               }
               }
               
               //}
               req.setAttribute("pageId",path);
               
               
   			
   			
   			return map.findForward(ResultHelp.getSuccess());
           }}
       catch(Exception e){System.out.println("Exception at 332"+e);}
       return map.findForward(ResultHelp.getSuccess());
   }

//GL & Admin =========================>CLEARING===========>
        
        if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmClearingModuleMenu")){
            try{
            	AdmClearingModuleForm admClr=(AdmClearingModuleForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
               req.setAttribute("pageNum",admClr.getPageId().trim());
               System.out.println("PageId is"+admClr.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admClr.getPageId())){
                   path=MenuNameReader.getScreenProperties(admClr.getPageId());
                   
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2797"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
       
   else if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmClearingModule")){
	   AdmClearingModuleForm admClr=(AdmClearingModuleForm)form;
       String path;
       System.out.println("Inside /Administrator/AdmClearingModule");
       System.out.println("admClr=="+admClr);
       System.out.println("admClr=="+admClr.getPageId());
       System.out.println("page ID23423423 ");
       try{
           req.setAttribute("pageNum",admClr.getPageId().trim());
           if(MenuNameReader.containsKeyScreen(admClr.getPageId())){
               path=MenuNameReader.getScreenProperties(admClr.getPageId());
               System.out.println(path);
               System.out.println("got the object");
               //admshare.getNewactype()!=null&&
               //if(!admshare.getNewactype().equals("newAccountType")){
               admDelegate=new AdministratorDelegate();
               admmoduleObject=admDelegate.getGLData("1011001");
               System.out.println("got the object");
               if(admmoduleObject!=null){
            	   System.out.println("admmoduleObject is not null");
            	   String[] str=admmoduleObject.getModulesValues();
            	   for(int k=0;k<str.length;k++)
            	   System.out.println("Account Name"+str[k]);
            	   
            	   
            	   if(admClr.getSetv().equals("submit")){
            		   str[0]=admClr.getAcname();
            		   str[1]=admClr.getSname();
            		   str[2]=admClr.getLastcontrolno();
            		   str[25]=admClr.getChqvalidperiod();
            		   str[24]=admClr.getLastackno();
            		   str[30]=admClr.getMaxclearing();
                	   admmoduleObject.setModuleCode("1011001");
                	   admmoduleObject.setModulesValues(str);
                	   String result=admDelegate.storeGlModAdmin(admmoduleObject);
                	  req.setAttribute("closingmsg",result);
                	  admClr.setSetv("");
                	   }
                	   else{ 
            	   admClr.setAcname(str[0]);
            	   admClr.setSname(str[1]);
            	   admClr.setLastcontrolno(str[2]);
            	   admClr.setChqvalidperiod(str[25]);
            	   admClr.setLastackno(str[24]);
            	   admClr.setMaxclearing(str[30]);
            	   
               }
               }
               
               //}
               req.setAttribute("pageId",path);
               
               
   			
   			
   			return map.findForward(ResultHelp.getSuccess());
           }}
       catch(Exception e){System.out.println("Exception at 332"+e);}
       return map.findForward(ResultHelp.getSuccess());
   }

//GL & Admin =========================>OVERDRAW ACCOUNT
        
        if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmODModuleMenu")){
            try{AdmODModuleForm admOd=(AdmODModuleForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
               req.setAttribute("pageNum",admOd.getPageId().trim());
               System.out.println("PageId is"+admOd.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admOd.getPageId())){
                   path=MenuNameReader.getScreenProperties(admOd.getPageId());
                   
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
       
   else if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmODModule")){
	   AdmODModuleForm admOd=(AdmODModuleForm)form;
       String path;
       System.out.println("Inside /Administrator/AdmODModule");
       System.out.println("admOd=="+admOd);
       System.out.println("admOd=="+admOd.getPageId());
       System.out.println("page ID23423423 ");
       try{
           req.setAttribute("pageNum",admOd.getPageId().trim());
           if(MenuNameReader.containsKeyScreen(admOd.getPageId())){
               path=MenuNameReader.getScreenProperties(admOd.getPageId());
               System.out.println(path);
               System.out.println("got the object");
               //admshare.getNewactype()!=null&&
               //if(!admshare.getNewactype().equals("newAccountType")){
               admDelegate=new AdministratorDelegate();
               admmoduleObject=admDelegate.getGLData("1015001");
               System.out.println("got the object");
               if(admmoduleObject!=null){
            	   System.out.println("admmoduleObject is not null");
            	   String[] str=admmoduleObject.getModulesValues();
            	   for(int k=0;k<str.length;k++)
            	   System.out.println("Account Name"+str[k]);
            	   
            	   if(admOd.getSetv().equals("submit")){
            		   str[0]=admOd.getAcname();
            		   str[1]=admOd.getSname();
            		   str[2]=admOd.getLastacno();
            		   str[4]=admOd.getMinbal();
            		   str[8]=admOd.getMaxsanction();
            		   str[21]=admOd.getLinpassbook();
            		   str[24]=admOd.getInopDays();
            		   str[25]=admOd.getChqvalid();
            		   str[26]=admOd.getMinbalchqm();
            		   str[27]=admOd.getMinbalclr();
                	   admmoduleObject.setModuleCode("1015001");
                	   admmoduleObject.setModulesValues(str);
                	   String result=admDelegate.storeGlModAdmin(admmoduleObject);
                	  req.setAttribute("closingmsg",result);
                	  admOd.setSetv("");
                	   }
                	   else{ 
            	   admOd.setAcname(str[0]);
            	   admOd.setSname(str[1]);
            	   admOd.setLastacno(str[2]);
            	   admOd.setMinbal(str[4]);
            	   
            	   admOd.setMaxsanction(str[8]);
            	   admOd.setLinpassbook(str[21]);
            	   admOd.setInopDays(str[24]);
            	   admOd.setChqvalid(str[25]);
            	   admOd.setMinbalchqm(str[26]);
            	   admOd.setMinbalclr(str[27]);
            	   
               }
               }
               
               //}
               req.setAttribute("pageId",path);
               
               
   			
   			
   			return map.findForward(ResultHelp.getSuccess());
           }}
       catch(Exception e){System.out.println("Exception at 332"+e);}
       return map.findForward(ResultHelp.getSuccess());
   }
        
//GL & Admin =========================>PAY ORDER
        
        if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmPOModuleMenu")){
            try{AdmPOModuleForm admPo=(AdmPOModuleForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
               req.setAttribute("pageNum",admPo.getPageId().trim());
               System.out.println("PageId is"+admPo.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admPo.getPageId())){
                   path=MenuNameReader.getScreenProperties(admPo.getPageId());
                   
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
       
   else if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmPOModule")){
	   AdmPOModuleForm admPo=(AdmPOModuleForm)form;
       String path;
       System.out.println("Inside /Administrator/AdmPOModule");
       System.out.println("admPo=="+admPo);
       System.out.println("admPo=="+admPo.getPageId());
       System.out.println("page ID23423423 ");
       try{
           req.setAttribute("pageNum",admPo.getPageId().trim());
           if(MenuNameReader.containsKeyScreen(admPo.getPageId())){
               path=MenuNameReader.getScreenProperties(admPo.getPageId());
               System.out.println(path);
               System.out.println("got the object");
               //admshare.getNewactype()!=null&&
               //if(!admshare.getNewactype().equals("newAccountType")){
               admDelegate=new AdministratorDelegate();
               admmoduleObject=admDelegate.getGLData("1016001");
               System.out.println("got the object");
               if(admmoduleObject!=null){
            	   System.out.println("admmoduleObject is not null");
            	   String[] str=admmoduleObject.getModulesValues();
            	   for(int k=0;k<str.length;k++)
            	   System.out.println("Account Name"+str[k]);
            	   
            	   if(admPo.getSetv().equals("submit")){
            		   admPo.setAcname(str[0]);
                	   admPo.setSname(str[1]);
                	   admPo.setLastacno(str[2]);
                	   admPo.setMinbal(str[4]);
                	   
                	   admPo.setMaxamount(str[8]);
                	  
                	   admPo.setChqvalid(str[25]);
                	   admPo.setLastpodeno(str[33]);
                	   admmoduleObject.setModuleCode("1016001");
                	   admmoduleObject.setModulesValues(str);
                	   String result=admDelegate.storeGlModAdmin(admmoduleObject);
                	  req.setAttribute("closingmsg",result);
                	  admPo.setSetv("");
                	   }
                	   else{ 
            	   
            	   admPo.setAcname(str[0]);
            	   admPo.setSname(str[1]);
            	   admPo.setLastacno(str[2]);
            	   admPo.setMinbal(str[4]);
            	   
            	   admPo.setMaxamount(str[8]);
            	  
            	   admPo.setChqvalid(str[25]);
            	   admPo.setLastpodeno(str[33]);
            	   
               }
               }
               
               //}
               req.setAttribute("pageId",path);
               
               
   			
   			
   			return map.findForward(ResultHelp.getSuccess());
           }}
       catch(Exception e){System.out.println("Exception at 332"+e);}
       return map.findForward(ResultHelp.getSuccess());
   }
//GL & Admin =========================>CASH CREDIT
        
        if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmCCModuleMenu")){
            try{AdmCCModuleForm admCc=(AdmCCModuleForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
               req.setAttribute("pageNum",admCc.getPageId().trim());
               System.out.println("PageId is"+admCc.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admCc.getPageId())){
                   path=MenuNameReader.getScreenProperties(admCc.getPageId());
                   
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
       
   else if(map.getPath().trim().equalsIgnoreCase("/Administrator/AdmCCModule")){
	   AdmCCModuleForm admCc=(AdmCCModuleForm)form;
       String path;
       System.out.println("Inside /Administrator/AdmPOModule");
       System.out.println("admCc=="+admCc);
       System.out.println("admCc=="+admCc.getPageId());
       System.out.println("page ID23423423 ");
       try{
           req.setAttribute("pageNum",admCc.getPageId().trim());
           if(MenuNameReader.containsKeyScreen(admCc.getPageId())){
               path=MenuNameReader.getScreenProperties(admCc.getPageId());
               System.out.println(path);
               System.out.println("got the object");
               //admshare.getNewactype()!=null&&
               //if(!admshare.getNewactype().equals("newAccountType")){
               admDelegate=new AdministratorDelegate();
               admmoduleObject=admDelegate.getGLData("1014001");
               System.out.println("got the object");
               if(admmoduleObject!=null){
            	   System.out.println("admmoduleObject is not null");
            	   String[] str=admmoduleObject.getModulesValues();
            	   for(int k=0;k<str.length;k++)
            	   System.out.println("Account Name"+str[k]);
            	   admCc.setAcname(str[0]);
            	   admCc.setSname(str[1]);
            	   admCc.setLastacno(str[2]);
            	   admCc.setMinbal(str[4]);
            	   admCc.setSharelp(str[7]);
            	   admCc.setMaxsanction(str[8]);
            	   admCc.setLinpassbook(str[21]);
            	   admCc.setInopDays(str[24]);
            	   admCc.setChqvalid(str[25]);
            	   admCc.setMinbalchqm(str[26]);
            	   admCc.setMinbalclr(str[27]);
            	   admCc.setStockinsp(str[36]);
            	   
            	   
               }
               
               //}
               req.setAttribute("pageId",path);
               
               
   			
   			
   			return map.findForward(ResultHelp.getSuccess());
           }}
       catch(Exception e){System.out.println("Exception at 332"+e);}
       return map.findForward(ResultHelp.getSuccess());
   }
        
        
        
        
        
    /* ************************Administrator Terminals Details Action Ends Here******* */
        //for Giving Access rights
        if(map.getPath().trim().equalsIgnoreCase("/Administrator/NewAccessRightsMenu")){
            try{
            	NewAccessRightsForm admCc=(NewAccessRightsForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
            	admDelegate=new AdministratorDelegate();
               req.setAttribute("pageNum",admCc.getPageId().trim());
               System.out.println("PageId is"+admCc.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admCc.getPageId())){
            	   
            	   System.out.println("at 3383 in Administrator Action");
                   path=MenuNameReader.getScreenProperties(admCc.getPageId());
                   
                   System.out.println("at 3386 in Administrator Action"+path);
                   
                   AdministratorObject[] admObj=admDelegate.getTmlCodes();
		    		  String[] tmlNo=new String[admObj.length];
		    		  for(int i=0;i<admObj.length;i++){
		    			  tmlNo[i]=admObj[i].getTmlNo();
		    		  }
		    		  req.setAttribute("TerminalNoArray", tmlNo);
     			String[] users=admDelegate.getUsers();
     			System.out.println("user[0]=====>"+users[0]);
	    		  req.setAttribute("UserDetails",users);
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("10023");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        
        else if(map.getPath().trim().equalsIgnoreCase("/Administrator/NewAccessRights")){
            try{
            	NewAccessRightsForm admCc=(NewAccessRightsForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
            	admDelegate=new AdministratorDelegate();
               req.setAttribute("pageNum",admCc.getPageId().trim());
               System.out.println("PageId is"+admCc.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admCc.getPageId())){
                
            	 if(admCc.getSub()!=null&&admCc.getSub().trim().equalsIgnoreCase("submit")){
            		 
            		 String[] str=new String[14];
            		if(admCc.getChkbox1()!=null&&admCc.getChkbox1().equals("1")){
            			str[0]=new String();
            			str[0]="1";
            		}
            		else{
            			str[0]=new String();
            			str[0]="0";
            		}
            		if(admCc.getChkbox2()!=null&&admCc.getChkbox2().equals("1")){
            			str[1]=new String();
            			str[1]="1";
            		}
            		else{
            			str[1]=new String();
            			str[1]="0";
            		}
            		if(admCc.getChkbox3()!=null&&admCc.getChkbox3().equals("1")){
            			str[2]=new String();
            			str[2]="1";
            		}
            		else{
            			str[2]=new String();
            			str[2]="0";
            		}
            		if(admCc.getChkbox4()!=null&&admCc.getChkbox4().equals("1")){
            			str[3]=new String();
            			str[3]="1";
            		}
            		else{
            			str[3]=new String();
            			str[3]="0";
            		}
            		if(admCc.getChkbox5()!=null&&admCc.getChkbox5().equals("1")){
            			str[4]=new String();
            			str[4]="1";
            		}
            		else{
            			str[4]=new String();
            			str[4]="0";
            		}
            		if(admCc.getChkbox6()!=null&&admCc.getChkbox6().equals("1")){
            			str[5]=new String();
            			str[5]="1";
            		}
            		else{
            			str[5]=new String();
            			str[5]="0";
            		}
            		if(admCc.getChkbox7()!=null&&admCc.getChkbox7().equals("1")){
            			str[6]=new String();
            			str[6]="1";
            		}
            		else{
            			str[6]=new String();
            			str[6]="0";
            		}
            		if(admCc.getChkbox8()!=null&&admCc.getChkbox8().equals("1")){
            			str[7]=new String();
            			str[7]="1";
            		}
            		else{
            			str[7]=new String();
            			str[7]="0";
            		}
            		if(admCc.getChkbox9()!=null&&admCc.getChkbox9().equals("1")){
            			str[8]=new String();
            			str[8]="1";
            		}
            		else{
            			str[8]=new String();
            			str[8]="0";
            		}
            		if(admCc.getChkbox10()!=null&&admCc.getChkbox10().equals("1")){
            			str[9]=new String();
            			str[9]="1";
            		}
            		else{
            			str[9]=new String();
            			str[9]="0";
            		}
            		if(admCc.getChkbox11()!=null&&admCc.getChkbox11().equals("1")){
            			str[10]=new String();
            			str[10]="1";
            		}
            		else{
            			str[10]=new String();
            			str[10]="0";
            		}
            		if(admCc.getChkbox12()!=null&&admCc.getChkbox12().equals("1")){
            			str[11]=new String();
            			str[11]="1";
            		}
            		else{
            			str[11]=new String();
            			str[11]="0";
            		}
            		if(admCc.getChkbox13()!=null&&admCc.getChkbox13().equals("1")){
            			str[12]=new String();
            			str[12]="1";
            		}
            		else{
            			str[12]=new String();
            			str[12]="0";
            		}
            		if(admCc.getChkbox14()!=null&&admCc.getChkbox14().equals("1")){
            			str[13]=new String();
            			str[13]="1";
            		}
            		else{
            			str[13]=new String();
            			str[13]="0";
            		}
            		for(int t=0;t<str.length;t++){
            			
            			System.out.println("----str--------->"+str[t]);
            		}
            		if(admCc.getUpdt()!=null&&admCc.getUpdt().equals("update")){
            		String result=admDelegate.setAccessRight(admCc.getUidValue().trim(),admCc.getTmlNo().trim(),str,1);
            		req.setAttribute("msg",result);
            		}
            		else{
            			String result=admDelegate.setAccessRight(admCc.getUidValue().trim(),admCc.getTmlNo().trim(),str,0);
                		req.setAttribute("msg",result);
                		
                		admDelegate.submituserAccess(admCc.getUidValue(),new String[]{"CA99"},user, tml);
            		}
            		admCc.setUpdt("");
            		admCc.setSub("");
            	 }
            	   
            	   
            	   
            	   
            	   
            	   System.out.println("At 3536------------------");
            	 String[] access=admDelegate.getAccessRight(admCc.getUidValue().trim(),admCc.getTmlNo().trim()); 
            	   if(access!=null){
            	 admCc.setChkbox1(access[0].trim());
            	 admCc.setChkbox2(access[1].trim());
            	 admCc.setChkbox3(access[2].trim());
            	 admCc.setChkbox4(access[3].trim());
            	 admCc.setChkbox5(access[4].trim());
            	 admCc.setChkbox6(access[5].trim());
            	 admCc.setChkbox7(access[6].trim());
            	 admCc.setChkbox8(access[7].trim());
            	 admCc.setChkbox9(access[8].trim());
            	 admCc.setChkbox10(access[9].trim());
            	 admCc.setChkbox11(access[10].trim());
            	 admCc.setChkbox12(access[11].trim());
            	 admCc.setChkbox13(access[12].trim());
            	 admCc.setChkbox14(access[13].trim());
            	 
            	   }
            	   else{
            		   System.out.println("At 3585------------inside else------");
            		   req.setAttribute("msg","Access Details not Available");
            	   }
            	   
            	   
            	   path=MenuNameReader.getScreenProperties(admCc.getPageId());
                   
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   TerminalObject[] tml_obj; 
      			  Object arrTml[]=null;
      			AdministratorObject[] admObj=admDelegate.getTmlCodes();
	    		  String[] tmlNo=new String[admObj.length];
	    		  for(int i=0;i<admObj.length;i++){
	    			  tmlNo[i]=admObj[i].getTmlNo();
	    		  }
	    		  req.setAttribute("TerminalNoArray", tmlNo);
      			String[] users=admDelegate.getUsers();
 	    		  req.setAttribute("UserDetails",users);
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        if(map.getPath().trim().equalsIgnoreCase("/Administrator/AccessRightsforModulesMenu")){
            try{
            	AccessRightsformodulesForm admCc=(AccessRightsformodulesForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
            	admDelegate=new AdministratorDelegate();
               req.setAttribute("pageNum",admCc.getPageId().trim());
               System.out.println("PageId is"+admCc.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admCc.getPageId())){
            	   
            	   System.out.println("at 3383 in Administrator Action");
                   path=MenuNameReader.getScreenProperties(admCc.getPageId());
                   
                   System.out.println("at 3386 in Administrator Action"+path);
                   
                   AdministratorObject[] admObj=admDelegate.getTmlCodes();
		    		  String[] tmlNo=new String[admObj.length];
		    		  for(int i=0;i<admObj.length;i++){
		    			  tmlNo[i]=admObj[i].getTmlNo();
		    		  }
		    		  req.setAttribute("TerminalNoArray", tmlNo);
     			String[] users=admDelegate.getUsers();
     			System.out.println("user[0]=====>"+users[0]);
	    		  req.setAttribute("UserDetails",users);
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("10023");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        
        else if(map.getPath().trim().equalsIgnoreCase("/Administrator/AccessRightsforModules")){
            try{
            	AccessRightsformodulesForm admCc=(AccessRightsformodulesForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
            	admDelegate=new AdministratorDelegate();
               req.setAttribute("pageNum",admCc.getPageId().trim());
               System.out.println("PageId is"+admCc.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admCc.getPageId())){
                
        if(admCc.getMods()!=null&&admCc.getMods().equals("TD")){
        	
        //	path=MenuNameReader.getScreenProperties("30100");
        	//req.setAttribute("pageId",path);
        	System.out.println("---3688----------path---->"+MenuNameReader.getScreenProperties("30100"));
        	req.setAttribute("flag",MenuNameReader.getScreenProperties("30100"));
           
        	
        	
        }
        else if(admCc.getMods()!=null&&admCc.getMods().equals("customer")){
        	
            //	path=MenuNameReader.getScreenProperties("30100");
            	//req.setAttribute("pageId",path);
            	System.out.println("---3698----------path---->"+MenuNameReader.getScreenProperties("130404"));
            	req.setAttribute("flag",MenuNameReader.getScreenProperties("130404"));
               
            	
            	
            }
            	   
            	   
            	   
            	   
            	   System.out.println("At 3536------------------");
            	
            	   
            	   
            	   path=MenuNameReader.getScreenProperties(admCc.getPageId());
                   
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   TerminalObject[] tml_obj; 
      			  Object arrTml[]=null;
      			AdministratorObject[] admObj=admDelegate.getTmlCodes();
	    		  String[] tmlNo=new String[admObj.length];
	    		  for(int i=0;i<admObj.length;i++){
	    			  tmlNo[i]=admObj[i].getTmlNo();
	    		  }
	    		  req.setAttribute("TerminalNoArray", tmlNo);
      			String[] users=admDelegate.getUsers();
 	    		  req.setAttribute("UserDetails",users);
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        //New Securityand Admin
        if(map.getPath().trim().equalsIgnoreCase("/Administrator/NewSecurityandAdminMenu")){
            try{
            	NewSecurityandAdminForm admCc=(NewSecurityandAdminForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
            	admDelegate=new AdministratorDelegate();
               req.setAttribute("pageNum",admCc.getPageId().trim());
               System.out.println("PageId is"+admCc.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admCc.getPageId())){
            	   
            	   System.out.println("at 3383 in Administrator Action");
                   path=MenuNameReader.getScreenProperties(admCc.getPageId());
                   
                   System.out.println("at 3386 in Administrator Action"+path);
                   
                   AdministratorObject[] admObj=admDelegate.getTmlCodes();
		    		  String[] tmlNo=new String[admObj.length];
		    		  for(int i=0;i<admObj.length;i++){
		    			  tmlNo[i]=admObj[i].getTmlNo();
		    		  }
		    		  req.setAttribute("TerminalNoArray", tmlNo);
     			String[] users=admDelegate.getUsers();
     			System.out.println("user[0]=====>"+users[0]);
	    		  req.setAttribute("UserDetails",users);
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   req.setAttribute("roleType",admDelegate.getRoleDefinition());
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("10023");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        
        else if(map.getPath().trim().equalsIgnoreCase("/Administrator/NewSecurityandAdmin")){
            try{
            	String a="0",b="0",c="0",d="0";
            	NewSecurityandAdminForm admCc=(NewSecurityandAdminForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
            	admDelegate=new AdministratorDelegate();
               req.setAttribute("pageNum",admCc.getPageId().trim());
               System.out.println("PageId is"+admCc.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admCc.getPageId()))
               {
              
            	   //submitting details
            	   if(admCc.getSub()!=null && admCc.getSub().trim().equals("submit")){
            		   Object[][] securityObject=admDelegate.getFormwiseAccessRights(admCc.getRoletype(),admCc.getMods());
            		   if(securityObject!=null){
                   	System.out.println("inside Submit---->3821");
                   	String[] selected1=(String[])req.getParameterValues("cbx1");
                   	String[] selected2=(String[])req.getParameterValues("cbx2");
                   	String[] selected3=(String[])req.getParameterValues("cbx3");
                   	String[] selected4=(String[])req.getParameterValues("cbx4");
                   	
                   /*	System.out.println("Selected1=======>"+selected1.length);
                   	System.out.println("Selected2=======>"+selected2.length);
                   	System.out.println("Selected3=======>"+selected3.length);
                   	System.out.println("Selected4=======>"+selected4.length);*/
                   	
                  	if(selected1!=null&&selected1.length>0||selected2!=null&&selected2.length>0||selected3!=null&&selected3.length>0||selected4!=null&&selected4.length>0){
                   	//code to b added herre	
                   		System.out.println("selected.length>0---->3829");
                   		adminobject=new AdministratorObject[securityObject.length];
                   		for(int k=0;k<securityObject.length;k++){
                   			adminobject[k]=new AdministratorObject();
                   		
                   		if(selected1!=null){
                   			for(int e=0;e<selected1.length;e++){
                   				if(selected1[e].equals(String.valueOf(k))){
                   					System.out.println("inside selected1 ==>1"+k);
                   					a="1";
                   					break;
                   				}
                   				else{
                   					
                   					a="0";
                   				}
                   			}
                   			
                   		}
                   		else{a="0";}
                   		if(selected2!=null){
                   			for(int f=0;f<selected2.length;f++){
                   				if(selected2[f].equals(String.valueOf(k))){
                   					System.out.println("inside selected2 ==>1"+k);
                   					
                   					b="1";
                   					break;
                   				}
                   				else{
                   					
                   					b="0";
                   				}
                   			}
                   			
                   		}
                   		else{b="0";}
                   		if(selected3!=null){
                   			for(int g=0;g<selected3.length;g++){
                   				if(selected3[g].equals(String.valueOf(k))){
                   					c="1";
                   					System.out.println("inside selected3 ==>1"+k);
                   					break;
                   				}
                   				else{
                   					
                   					c="0";
                   				}
                   			}
                   			
                   		}
                   		else{c="0";}
                   		if(selected4!=null){
                   			for(int h=0;h<selected4.length;h++){
                   				if(selected4[h].equals(String.valueOf(k))){
                   					d="1";
                   					System.out.println("inside selected4 ==>1"+k);
                   					break;
                   				}
                   				else{
                   					
                   					d="0";
                   				}
                   			}
                   			
                   		}
                   		else{d="0";}
                   		
                   		
                   		String access=a+b+c+d;
                   		System.out.println("access======>>>"+access);
                   	adminobject[k].setRolecode(admCc.getRoletype());
                   	if(k<9){
                   		int l=k+1;
                   	adminobject[k].setFormcode(admCc.getMods()+"00"+l);
                   	}
                   	else{
                   		int l=k+1;
                   		adminobject[k].setFormcode(admCc.getMods()+"0"+l);
                   	}
                   	adminobject[k].setAccess(access.trim());
                   	adminobject[k].setDe_user(user);
                   	adminobject[k].setDe_date(admDelegate.getSysDate()+""+admDelegate.getSysTime());
                   		
                   		
                   
                   		
                   }
                   		//here code to Submit
                   		String results=admDelegate.setFormwiseRoleAccess(adminobject,admCc.getRoletype(),admCc.getMods());
                   		if(results!=null){
                   			req.setAttribute("msg",results);
                   		}
                   		else{
                   			req.setAttribute("msg","Unable to insert the values");
                   		}
                   		
                  	}
            	   
                   	}//end of if 
            		   admCc.setSub("");
                   	}
            	          	   
            	   //to fetch details
            	   admObject=admDelegate.getFormsDetail(admCc.getMods());	   
            	   req.setAttribute("formsdetail",admObject);
        Object[][] securityObjects=admDelegate.getFormwiseAccessRights(admCc.getRoletype(),admCc.getMods());
        
        if(securityObjects!=null)
        {
        	req.setAttribute("securityObject",securityObjects);
        }
        else
        {
        	req.setAttribute("msg","Forms Details Not Defined");
        }
            	   System.out.println("At 3536------------------");
            	   path=MenuNameReader.getScreenProperties(admCc.getPageId());
                   
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   TerminalObject[] tml_obj; 
      			  Object arrTml[]=null;
      			AdministratorObject[] admObj=admDelegate.getTmlCodes();
	    		  String[] tmlNo=new String[admObj.length];
	    		  for(int i=0;i<admObj.length;i++){
	    			  tmlNo[i]=admObj[i].getTmlNo();
	    		  }
	    		  req.setAttribute("TerminalNoArray", tmlNo);
      			String[] users=admDelegate.getUsers();
 	    		  req.setAttribute("UserDetails",users);
 	    		 req.setAttribute("roleType",admDelegate.getRoleDefinition());
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements("Unable to process the request",req,path);
             e.printStackTrace();
             return map.findForward(ResultHelp.getError());
           }
           
       }
        //for UserRoleAssignment
        if(map.getPath().trim().equalsIgnoreCase("/Administrator/UserRoleAssignmentMenu")){
            try{
            	UserRoleAssignmentForm admCc=(UserRoleAssignmentForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
            	admDelegate=new AdministratorDelegate();
               req.setAttribute("pageNum",admCc.getPageId().trim());
               System.out.println("PageId is"+admCc.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admCc.getPageId())){
            	   
            	   System.out.println("at 3874 in Administrator Action");
                   path=MenuNameReader.getScreenProperties(admCc.getPageId());
                   
                   System.out.println("at 3876 in Administrator Action"+path);
                   
                   AdministratorObject[] admObj=admDelegate.getTmlCodes();
		    		  String[] tmlNo=new String[admObj.length];
		    		  for(int i=0;i<admObj.length;i++){
		    			  tmlNo[i]=admObj[i].getTmlNo();
		    		  }
		    		  req.setAttribute("TerminalNoArray", tmlNo);
     			String[] users=admDelegate.getUsers();
     			System.out.println("user[0]=====>"+users[0]);
	    		  req.setAttribute("UserDetails",users);
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   req.setAttribute("roleType",admDelegate.getRoleDefinition());
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("10023");
             System.out.println("At 69 here is exception"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        
        else if(map.getPath().trim().equalsIgnoreCase("/Administrator/UserRoleAssignment")){
            try{
            	UserRoleAssignmentForm admCc=(UserRoleAssignmentForm)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
            	admDelegate=new AdministratorDelegate();
               req.setAttribute("pageNum",admCc.getPageId().trim());
               System.out.println("PageId is"+admCc.getPageId().trim());
               logger.info("This is from Administrator"+map.getPath());
               if(MenuNameReader.containsKeyScreen(admCc.getPageId())){
            if(admCc.getSub()!=null && admCc.getSub().trim().equals("submit")){
            	System.out.println("inside Submit---->3923");
            	String[] selected=(String[])req.getParameterValues("cbox");
            	if(selected!=null&&selected.length>0){
            	//code to b added herre	
            		System.out.println("selected.length>0---->3927"+selected.length);
            		adminobject=new AdministratorObject[selected.length];
            		for(int c=0;c<selected.length;c++){
            			adminobject[c]=new AdministratorObject();
            			adminobject[c].setRolecode(selected[c]);
            			
            		}
            		String results=admDelegate.setUserRoleAssignment(admCc.getUidValue(),adminobject);
            		if(results!=null){
            			req.setAttribute("msg",results);
            		}
            		else{
            			req.setAttribute("msg","Unable to insert the values");
            		}
            	}
            	else{
            		req.setAttribute("msg","Please select roles to be assigned");
            	}
            	admCc.setSub("");	
            }
       
            	   
            	   
            	   
          //to fetch details  	   
          AdministratorObject[] userRoleAdminobj=admDelegate.getUserRoleAssignment(admCc.getUidValue());
        if(userRoleAdminobj!=null){
        	req.setAttribute("userRoleAdminobj",userRoleAdminobj);
        }
        else{
        req.setAttribute("msg","Role not defined for the selected user");
        }
            	   
            	   
            	   
            	   System.out.println("At 3536------------------");
            	
            	   
            	   
            	   path=MenuNameReader.getScreenProperties(admCc.getPageId());
                   
                   //admDelegate=new AdministratorDelegate();
                   System.out.println("At 2274"+path);
                  // setSBOpeningInitParams(req,path,admDelegate);
                   TerminalObject[] tml_obj; 
      			  Object arrTml[]=null;
      			AdministratorObject[] admObj=admDelegate.getTmlCodes();
	    		  String[] tmlNo=new String[admObj.length];
	    		  for(int i=0;i<admObj.length;i++){
	    			  tmlNo[i]=admObj[i].getTmlNo();
	    		  }
	    		  req.setAttribute("TerminalNoArray", tmlNo);
      			String[] users=admDelegate.getUsers();
 	    		  req.setAttribute("UserDetails",users);
 	    		 req.setAttribute("roleType",admDelegate.getRoleDefinition());
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 3991"+e);
             setErrorPageElements(""+e,req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if (map.getPath().trim().equalsIgnoreCase("/Administrator/FormsDetailMenu")) {
	 		FormsDetailForm mform=(FormsDetailForm)form;
			System.out.println("hi i am here" + map.getPath());

			try {

				req.setAttribute("pageNum", mform.getPageId().trim());
				req.setAttribute("Index", new String("0"));
				System.out.println("===" + mform.getPageId());
				//String value=mform.getValue();
				//req.setAttribute("VALUE",value);

				System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(mform.getPageId()));
				if (MenuNameReader.containsKeyScreen(mform.getPageId())) {
					path = MenuNameReader.getScreenProperties(mform.getPageId());
					System.out.println("insideExecutre path" + path);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getError());
				}
			} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getError());
			}

		}
	 	else if (map.getPath().trim().equalsIgnoreCase("/Administrator/FormsDetail")) 
	 	{
	 		FormsDetailForm mform=(FormsDetailForm)form;
	 		AdministratorObject[] formsdetailsbean=null;
	 		System.out.println("#############=" + mform.getPageId().trim());
			//System.out.println("from top ***********" + mform.getIntagno());
			try {
				req.setAttribute("pageNum", mform.getPageId().trim());
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre"+ MenuNameReader.containsKeyScreen(mform.getPageId()));
				if (MenuNameReader.containsKeyScreen(mform.getPageId())) {
					path = MenuNameReader.getScreenProperties(mform.getPageId());
					System.out.println(path);
					
					//System.out.println("--------681--------"+mform.getForward());
					//String value=mform.getValue();
					//req.setAttribute("VALUE",value);
					String[] id=req.getParameterValues("id");
					System.out.println("id=======>>>"+id);
					if(mform.getForward().equalsIgnoreCase("view"))
					{   List drList = new ArrayList();
						String frmcode=mform.getModulecode();
						System.out.println("the form code is----->"+frmcode);
						formsdetailsbean=admDelegate.getFormsDetail(frmcode);
						
						if(formsdetailsbean!=null){
						for(int i=0;i<formsdetailsbean.length;i++)
						{
							System.out.println("the result---->"+formsdetailsbean[i].getFormcode());
							
						}
						
						
						req.setAttribute("FormsDetail", formsdetailsbean);
					     
						
						}
					}
					
					else if(mform.getForward().equalsIgnoreCase("AddRow"))
					{
					req.setAttribute("addit","addit");
					}
					formsdetailsbean=admDelegate.getFormsDetail(mform.getModulecode());
							
					req.setAttribute("FormsDetail", formsdetailsbean);
					req.setAttribute("pageId",path);
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}

			return map.findForward(ResultHelp.getSuccess());
	 		
	 	
	 	}

else{
    path=MenuNameReader.getScreenProperties("0000");
    setErrorPageElements("Please chaack ! There is no matching path",req,path);
    return map.findForward(ResultHelp.getError());
}

        
    	
    	
  
   
}

    
    private void setErrorPageElements(String exception,HttpServletRequest req,String path){
        req.setAttribute("exception",exception);
        req.setAttribute("pageId",path);

    }
   /* private void CrearteUserInitParams(HttpServletRequest req,String path,FrontCounterDelegate fcDelegate)throws Exception{
        try{
     	   System.out.println("at 410----------------->");
           req.setAttribute("pageId",path);
           req.setAttribute("AcType",fcDelegate.getComboElements(0));
           req.setAttribute("IntroAcType",fcDelegate.getComboElements(1));
           req.setAttribute("Details",fcDelegate.getDetailsComboElements());
        }catch(Exception e){
            throw e;
        }
    }*/
}
