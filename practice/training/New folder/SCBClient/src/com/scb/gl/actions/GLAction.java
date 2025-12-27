package com.scb.gl.actions;
/**
 * author: Amzad
 * Date: 06/01/2009
 */
import exceptions.DateFormatException;
import exceptions.RecordsNotFoundException;
import general.Validations;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import masterObject.general.DoubleFormat;
import masterObject.general.ModuleObject;
import masterObject.generalLedger.DayBookObject;
import masterObject.generalLedger.DayBookObjectNew;
import masterObject.generalLedger.Form1Object;
import masterObject.generalLedger.GLObject;
import masterObject.generalLedger.GLReportObject;
import masterObject.generalLedger.TransferScroll;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.common.help.ResultHelp;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.FrontCounterDelegate;
import com.scb.designPatterns.GLDelegate;
import com.scb.fc.forms.TrayListForm;
import com.scb.gl.forms.DailyPosting;
import com.scb.gl.forms.DayBook;
import com.scb.gl.forms.DayClose;
import com.scb.gl.forms.Form1Posting;
import com.scb.gl.forms.GLAdmin;
import com.scb.gl.forms.GLSchedule;
import com.scb.gl.forms.MonthClose;
import com.scb.gl.forms.MonthlyBalanceSheet;
import com.scb.gl.forms.MonthlyGlSchedule;
import com.scb.gl.forms.MonthlyPosting;
import com.scb.gl.forms.MonthlyProfitLoss;
import com.scb.gl.forms.MonthlyRecieptsPayments;
import com.scb.gl.forms.MonthlyTrailBalance;
import com.scb.gl.forms.RBICRRSLRReports;
import com.scb.gl.forms.RBICRRSLRSubSchedule;
import com.scb.gl.forms.RBIForm1LinkUpdation;
import com.scb.gl.forms.RBIForm1Master;
import com.scb.gl.forms.RBIForm1Reports;
import com.scb.gl.forms.RBIForm9InputUpdation;
import com.scb.gl.forms.RBIForm9Posting;
import com.scb.gl.forms.RBIForm9Reports;
import com.scb.gl.forms.RBIMarkingDateEntry;
import com.scb.gl.forms.TrfScrollPrinting;
import com.scb.gl.forms.VoucherSlipPrinting;
import com.scb.gl.forms.YearClose;
import com.scb.props.MenuNameReader;

public class GLAction extends org.apache.struts.action.Action{
	
	private String path;
	private GLDelegate gldelegate;
	HttpSession session;
	String gluser,gltml;
	String lastfriday1=null;
	DayBookObject array_consolDayBookObjectforbr[]=null;
	DayBookObject array_consolDayBookObject[]=null;
	DayBookObject array_openclosebal[]=null;
	String branchdet[][]=null;
	AdministratorDelegate admDelegate;
	Map user_role;
	public ActionForward execute(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res)throws Exception{
		String[][] strMthStatus=null;

		String strType=null,strCode=null;
		System.out.println("The main path is"+map.getPath());
		System.out.println("Check for class file---------------------123456789-------------");
		System.out.println("__________Amzad____Amzad_________");
		           /** **************GL Daily Posting Start********************* */
		
		session=req.getSession();
		gluser=(String)session.getAttribute("UserName");
		gltml=(String)session.getAttribute("UserTml") ;
		try{
    		synchronized(req) {
				
			
    		if(gluser!=null){
    			
    			admDelegate=new AdministratorDelegate();
    			user_role=admDelegate.getUserLoginAccessRights(gluser,"GL");
    			if(user_role!=null){
    			req.setAttribute("user_role",user_role);
    			if(req.getParameter("pageId")!=null){
    				System.out.println("req.getParameter--in FC ACtion===>"+req.getParameter("pageId"));
    				req.setAttribute("accesspageId",req.getParameter("pageId").trim());
    			}
				
    			}
    			else
    			{
    				//to error page for display
    				path=MenuNameReader.getScreenProperties("0000");
    		        
    		           setErrorPageElements("Sorry, You do not have access to this page",req,path);
    		           return map.findForward(ResultHelp.getError());
    			}
    		}
    		  		}
    	}
    	catch(Exception ex){ex.printStackTrace();}
		
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLDailyPostingMenu"))
		{
	      try{
	    	  DailyPosting daily_post=(DailyPosting)form;
	    	  daily_post.setValidations("");
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+daily_post);
	    	  System.out.println("the page id is "+daily_post.getPageId());
	    	  req.setAttribute("pagenum", daily_post.getPageId());
	    	      	  
	    	  if(MenuNameReader.containsKeyScreen(daily_post.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(daily_post.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path); 
	    		  daily_post.setDate(GLDelegate.getSysDate());
	    		  req.setAttribute("NoPosting", "NoPosting");
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
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLDailyPosting")){
			try{
		    	  DailyPosting daily_post=(DailyPosting)form;
		    	  daily_post.setValidations("");
		       	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+daily_post);
		    	  System.out.println("the page id is "+daily_post.getPageId());
		    	  req.setAttribute("pagenum", daily_post.getPageId());
		    	  			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(daily_post.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(daily_post.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+daily_post.getForward());
		    		  String dlystatus[][]=null;
		    		 
		    			 /*
							 * if(Validations.dayCompare(daily_post.getDate(),GLDelegate.getSysDate())<0){
							 * daily_post.setValidations("Date Should be less
							 * than or equal to Currnet Date"); }
							 */
		    			 if(daily_post.getDate()!=null){
		    			   dlystatus=gldelegate.checkDailyStatus(daily_post.getDate());	 
		    			   if(dlystatus[0][0].equals("MIN")){
		    				   req.setAttribute("msg","Date should be greater than "+dlystatus[0][1]+" you cannot continue");
		    			   }
		    			   else if(dlystatus[0][0].equals("0")){
		    				   req.setAttribute("msg","Its Holiday");
		    			   }
		    			   else if(dlystatus[0][0].equals("CF")){
		    				   req.setAttribute("msg","Cash not closed You cannot do Posting");
		    			   }
		    			   else if(dlystatus[0][0].equals("PF")){
		    				   req.setAttribute("msg","You didnt do Posting  for  date "+dlystatus[0][1]+" you cannot continue");
		    			   }
		    			   else if(dlystatus[0][0].equals("DT")){
		    				   req.setAttribute("msg","You cannot do Posting  Day Closed");
		    			   }
		    			   else if(dlystatus[0][0].equals("PT")){
		    				   req.setAttribute("msg","Posting Already Done. You can do Reposting...!");
		    				   req.setAttribute("RePosting", "RePosting");
		    			   }
		    			   else{
		    				   daily_post.setValidations("");
		    				   req.setAttribute("NoPosting", "Posting");
		    				   req.setAttribute("RePosting", "NoRePosting");
		    			   }
		    			   //req.setAttribute("NoPosting", "NoPosting");
		    			 }
		    				 
	
		    			 
		    			  
		    		
		    		  
		    		  
		    		  if(daily_post.getFlag().equalsIgnoreCase("submit")){
		    			  
		    			  
		    			  String result=gldelegate.dailyposting(daily_post.getDate(),gluser,gltml,(String)session.getAttribute("BankLocation"));
		    			  daily_post.setDate(daily_post.getDate());
		    			  
		    			  if(result.equalsIgnoreCase("1"))
		    				  req.setAttribute("msg","Posting Done....!");
		    			  else
		    				  req.setAttribute("msg","Error During Posting....!");  
		    			  req.setAttribute("NoPosting", "NoPosting");
		    		  }
		    		  
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
		
		
		/** **************GL Daily Posting End********************* */
		/** **************GL Day Close Start********************* */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLDayCloseMenu"))
		{
	      try{
	    	  DayClose day_close=(DayClose)form;
	    	  day_close.setValidations("");
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+day_close);
	    	  System.out.println("the page id is "+day_close.getPageId());
	    	  req.setAttribute("pagenum", day_close.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(day_close.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(day_close.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  day_close.setDate(gldelegate.getSysDate());
	    		  req.setAttribute("Close", "NoClose");
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLDayClose")){
			try{
				DayClose day_close=(DayClose)form;
				day_close.setValidations("");
		       	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+day_close);
		    	  System.out.println("the page id is "+day_close.getPageId());
		    	  req.setAttribute("pagenum", day_close.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(day_close.getPageId()))
		    	  {
		    		  
		    		  
		    		  
		    		  String dlystatus[][]=null;
		    		  if(day_close.getDate()!=null){
		    			  System.out.println("=====Date is not null=====1");
		    			 /*
							 * if(Validations.dayCompare(daily_post.getDate(),GLDelegate.getSysDate())<0){
							 * daily_post.setValidations("Date Should be less
							 * than or equal to Currnet Date"); }
							 */
		    			 if(day_close.getDate()!=null){
		    				 System.out.println("=====Date is not null=====2");
		    			   dlystatus=gldelegate.checkDailyStatus(day_close.getDate());	
		    			   System.out.println("=========dlystatus value is "+dlystatus[0][0]);
		    			   if(dlystatus[0][0].equals("MIN")){
		    				   req.setAttribute("msg","Date should be greater than "+dlystatus[0][1]+" you cannot continue");
		    				   req.setAttribute("Close", "NoClose");
		    			   }
		    			   else if(dlystatus[0][0].equals("0")){
		    				   req.setAttribute("msg","Its Holiday");
		    				   req.setAttribute("Close", "NoClose");
		    			   }
		    			   else if(dlystatus[0][0].equals("CF")){
		    				   req.setAttribute("msg","Cash not closed You cannot do Posting");
		    				   req.setAttribute("Close", "NoClose");
		    			   }
		    			   else if(dlystatus[0][0].equals("PF")){
		    				   req.setAttribute("msg","You didnt do Posting  for  date "+dlystatus[0][1]+" you cannot continue");
		    				   req.setAttribute("Close", "NoClose");
		    			   }
		    			   else if(dlystatus[0][0].equals("DT")){
		    				   req.setAttribute("msg","You cannot do Posting  Day Closed");
		    				   req.setAttribute("Close", "NoClose");
		    			   }
		    			   else{
		    				   day_close.setValidations("");
		    				   req.setAttribute("Close", "Close");
		    			   }
		    			 }
		    				 
	
		    			 else{
		    				 day_close.setValidations("");
		    				 
		    			 }
		    			  
		    		  }
		    		  
		    		  if(day_close.getFlag()!=null){
		    		if(day_close.getFlag().equalsIgnoreCase("submit")){
		    			  try{
		    			  
		    			  int result=gldelegate.dodayClose(day_close.getDate(),gluser,gltml);
		    			 
		    			  if(result==-1)
		    				  req.setAttribute("msg","Day Closed!");
		  		        else if(result==-4)
		  		        	req.setAttribute("msg","Day already Closed!");
		  		           
		  		        else if(result==0)
		  		        	req.setAttribute("msg","Error!");
		  		            
		    			  req.setAttribute("Close", "NoClose");
		  		    }catch(RemoteException re){
		  		    	System.out.println("Exception Cought here");
		  		    	re.printStackTrace();}
		  		    

		    		  }
		    		  }
		    		  
		    		  path=MenuNameReader.getScreenProperties(day_close.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		/** **************GL Day Close End********************* */
		/** **************GL Monthly Posting start********************* */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyPostingMenu"))
		{
	      try{
	    	  MonthlyPosting monthly_post=(MonthlyPosting)form;
	    	  monthly_post.setValidations("");
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+monthly_post);
	    	  System.out.println("the page id is "+monthly_post.getPageId());
	    	  req.setAttribute("pagenum", monthly_post.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(monthly_post.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(monthly_post.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  monthly_post.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
	    		  req.setAttribute("MonthPosting", "NoPosting");
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyPosting")){
			try{
				MonthlyPosting monthly_post=(MonthlyPosting)form;
				monthly_post.setValidations("");
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+monthly_post);
		    	  System.out.println("the page id is "+monthly_post.getPageId());
		    	  req.setAttribute("pagenum", monthly_post.getPageId());
		    	  if(MenuNameReader.containsKeyScreen(monthly_post.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(monthly_post.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  String dlystatus[][]=null;
		    		  if(monthly_post.getFlag().equalsIgnoreCase("frm_date")){
		    		  if(monthly_post.getYear()!=null && monthly_post.getMonth()!=null ){
		    			  String month=null;
		  		    	int year=0;
		  		    	String month_check[][]=null,lastdate=null;;
		  		    	int ret=10;
		  		         	month=monthly_post.getMonth().trim();
		  		         	
		  		         	if(Validations.checkInteger(monthly_post.getMonth().trim())==false)
		  		         		req.setAttribute("msg","Please enter number's in Month");
		  		         		
		  		         	if(Validations.checkInteger(monthly_post.getYear().trim())==false)
		  		         		req.setAttribute("msg","Please enter number's in year");
		  		         		
		  		         	if(Integer.parseInt(monthly_post.getMonth().trim())>12)
		  		         		req.setAttribute("msg","Please enter correct month");
		  		         		
		  		         	
		  		         	if(monthly_post.getMonth().trim().length()==1)
		  		         		month="0"+monthly_post.getMonth().trim();
		  		         	if(monthly_post.getMonth().trim().length()==3)
		  		         		req.setAttribute("msg","Please enter correct Month");
		  		         		
		  		         	if(monthly_post.getYear().trim().length()<4 || monthly_post.getYear().trim().length()>4)
		  		         		req.setAttribute("msg","Please enter correct Year");
		  		         		
		  		         	if(month.trim().length()==2 && monthly_post.getYear().trim().length()==4){
		  		         		lastdate=Validations.lastDayOfMonth("01"+"/"+month+"/"+monthly_post.getYear().trim());
		  		         		if(Validations.dayCompare(lastdate,GLDelegate.getSysDate())>0){
		  		         			String firstdate="01"+"/"+month+"/"+monthly_post.getYear().trim();
		  		         			
		  		         			String[][] strChDailyStatus=gldelegate.checkDailyStatus(firstdate,lastdate);
		  		         			if(strChDailyStatus[0][0].equals("MIN")){
		  		         				req.setAttribute("msg","Date should be greater than or equal to "+strChDailyStatus[0][1]+" you cannot continue");
		  		         				
		  		         			}
		  		      			else if(strChDailyStatus[0][0].equals("0")){
		  		      			req.setAttribute("msg","No Entiries in DailyStatus");
		  		      		    
		  		      			}
		  		      			else if(strChDailyStatus.equals("CF")){
		  		      			req.setAttribute("msg","Cash not closed You cannot do Posting");
		  	     	      		
		  		      			}
		  		      			else if(strChDailyStatus.equals("PF") || strChDailyStatus.equals("CT")){
		  		      			req.setAttribute("msg","You didnt do Posting  for  date "+strChDailyStatus[0][1]+" you cannot continue");
		  		      		    
		  		      			}
		  		      			else if(strChDailyStatus[0][0].equals("DT") || strChDailyStatus.equals("PT")){
		  		      			
		  		      					strMthStatus=gldelegate.checkMonthlyStatus(firstdate,lastdate);
		  		      				}
		  		         			if(strMthStatus[0][0].equals("MIN"))
		  		         				req.setAttribute("msg","Date Should be greater than "+strMthStatus[0][1]+" !..You cannot continue ");
		  		         				
		  		  					
		  		  				else if(strMthStatus[0][0].equals("OP"))
		  		  				req.setAttribute("msg","You didn't Updated Opening Balance you cannot continue");
		  		  					
		  		  				else if(strMthStatus[0][0].equals("PF"))
		  		  				req.setAttribute("msg","You didn't do posting for Month"+strMthStatus[0][1]+" !..You cannot continue");
		  		  					
		  		  				else if(strMthStatus[0][0].equals("MT"))
		  		  				req.setAttribute("msg","Month Closed you cannot continue");
		  		  					
		  		  				else if(strMthStatus[0][0].equals("NO"))
		  		  				req.setAttribute("msg","You have Entries only till "+strMthStatus[0][1]+" you cannot continue");
		  		  					
		  		  				else if(strMthStatus[0][0].equals("PT") || strMthStatus[0][0].equals("0")){
		  		  				req.setAttribute("msg","MonthlyPosting already done");
		  		  			
		  		  					//ret=JOptionPane.showConfirmDialog(null,"MonthlyPosting already done do you want to continue?");
		  		  					//if(ret==JOptionPane.YES_OPTION)
		  		  						//checkmthconsolidation(firstdate,lastdate);
		  		  				}
		  		  				else if(strMthStatus[0][0].equals("OK")){
		  		  				req.setAttribute("MonthPosting", "Posting");
		  		  					//Allow for Posting
		  		  				 
		  		  				/*try{
		  				            int b=gl_remote.doMonthlyPosting(Integer.parseInt(txt_year.getText()),Integer.parseInt(txt_month.getText()),MainScreen.head.br_location,MainScreen.head.utml,MainScreen.head.uname,Validations.convertYMD(MainScreen.head.getSysDate())+" "+MainScreen.head.getSysTime(),"T");
		  				            
		  				            if(b==1)
		  				                JOptionPane.showMessageDialog(this,"Posting Done!");
		  				            else
		  				                JOptionPane.showMessageDialog(this,"Posting Not Done!");
		  				            
		  				        }catch(RemoteException re){re.printStackTrace();}
		  				        catch(NumberFormatException ne){ne.printStackTrace();}
		  				        
		  		  					*/
		  		  				}
		  		  					//checkmthconsolidation(firstdate,lastdate);
		  		  				/*else*/ else if(strMthStatus[0][0].equals("1"))
		  		  				req.setAttribute("msg","No Entries in Monthly Status");
		  		  				
		  		  			}
		  		         			
		  		         			
		  		         			
		  		         			
		  		         			
		  		      		
		  		         		}
		  		         		else{
		  		         			req.setAttribute("msg","Month should be less than present Month"); 
		  		         			
		  		         		}
		  		         			
		  		         	}
		    	  }
		    		  if(monthly_post.getFlag().equalsIgnoreCase("submit")){
		    			  
		    			  String month=null;
		    			  
		    			  if(Integer.parseInt(monthly_post.getMonth())<9){
		    				  month="0"+monthly_post.getMonth();
		    			  }
		    			  else{
		    				  month=monthly_post.getMonth();
		    			  }
		    			  try{
		  		            int b=gldelegate.doMonthPosting(monthly_post.getYear(),month,gluser,gltml,(String)session.getAttribute("BankLocation"));
		  		            
		  		            if(b==1)
		  		            	req.setAttribute("msg","Posting Done!");
		  		                
		  		            else
		  		            	req.setAttribute("msg","Posting Not Done!");
		  		               
		  		            
		  		            monthly_post.setYear(monthly_post.getYear());
		  		          monthly_post.setMonth(monthly_post.getMonth());
		  		        req.setAttribute("MonthPosting", "NoPosting");
		  		            
		  		        }catch(RemoteException re){re.printStackTrace();}
		  		        catch(NumberFormatException ne){ne.printStackTrace();} 
		  		      path=MenuNameReader.getScreenProperties("12003");
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  return map.findForward(ResultHelp.getSuccess());
		    	    }
		    		  path=MenuNameReader.getScreenProperties("12003");
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		/** **************GL Monthly Posting End********************* */
		/** **************GL Month Close Start********************* */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyCloseMenu"))
		{
	      try{
	    	  MonthClose month_close=(MonthClose)form;
	    	  month_close.setValidations("");
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+month_close);
	    	  System.out.println("the page id is "+month_close.getPageId());
	    	  req.setAttribute("pagenum", month_close.getPageId());
	    	 if(MenuNameReader.containsKeyScreen(month_close.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(month_close.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  req.setAttribute("MthClose", "NoClose");
	    		  month_close.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyClose")){
			try{
				MonthClose month_close=(MonthClose)form;
				month_close.setValidations("");
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+month_close);
		    	  System.out.println("the page id is "+month_close.getPageId());
		    	  req.setAttribute("pagenum", month_close.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(month_close.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(month_close.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  //amzad  from_date
		    		  if(month_close.getFlag().equalsIgnoreCase("from_date")){
		    		  if(month_close.getYear().length()>0)
		              {
		              	//but_submit.setEnabled(false);
		              	//enableSubmitButton(false);
		              	String month=null;
		                     try{
		                    	month=month_close.getMonth().trim();
		    		         	if(Validations.checkInteger(month_close.getMonth().trim())==false)
		    		         		req.setAttribute("msg","Please enter number's in Month");
		    		         		
		    		         	if(Validations.checkInteger(month_close.getYear().trim())==false)
		    		         		req.setAttribute("msg","Please enter number's in year");
		    		         		
		    		         	if(Integer.parseInt(month_close.getMonth().trim())>12)
		    		         		req.setAttribute("msg","Please enter correct month");
		    		         		
		    		         	
		    		         	if(month_close.getMonth().trim().length()==1)
		    		         		month="0"+month_close.getMonth().trim();
		    		         	if(month_close.getMonth().trim().length()==3)
		    		         		req.setAttribute("msg","Please enter correct Month");
		    		         		
		    		         	if(month_close.getYear().trim().length()<4 || month_close.getYear().trim().length()>4)
		    		         		req.setAttribute("msg","Please enter correct Year");
		    		         		
		    		         	if(month.trim().length()==2 && month_close.getYear().trim().length()==4){
		    		         		String lastdate=Validations.lastDayOfMonth("01"+"/"+month+"/"+month_close.getYear().trim());
		    		         		if(Validations.dayCompare(lastdate,GLDelegate.getSysDate())>0){
		    		         			String firstdate="01"+"/"+month+"/"+month_close.getYear().trim();
		    		         			String[][] ret=checkMonthClose(convertToYYYYMM(firstdate));
		    		         			System.out.println("Monthcloe status value is======"+ret[0][0]);
		    		         			//checkmth result start
		    		         			if(ret[0][0].equals("-1"))
		    		         				req.setAttribute("msg","Sorry you didn't do month close for "+ret[0][1]+" you cannot continue");
		    		            			
		    		            		else if(ret[0][0].equals("1")){
		    		            			req.setAttribute("MthClose", "Close");
		    		            			//submit copy start
		    		            			/*int b=-1;
		    		  		              
		    		  		              if(month_close.getYear().trim() !=null && month_close.getYear().trim() !=null && month_close.getYear().trim().length()==4 && (month_close.getMonth().trim().length()==1 || month_close.getMonth().trim().length()==2) )	
		    		  		              	b=gldelegate.closeMonth(Integer.parseInt(month_close.getYear().trim() ),Integer.parseInt(month_close.getMonth().trim()));
		    		  		              
		    		  		              if(b==1)
		    		  		              {
		    		  		                  
		    		  		                  month_close.setValidations("Month Closed!");
		    		  		                  
		    		  		              }else
		    		  		            	  month_close.setValidations("Error!");
		    		  		                 
		    		            			*/
		    		            			//submit copy end
		    		            			
		    		            			
		    		            			
		    		            			
		    		            			//but_submit.setEnabled(true);
		    		            			
		    		            		}
		    		            		else if(ret[0][0].equals("0"))
		    		            			req.setAttribute("msg","Sorry you Posting not done you cannot continue");
		    		            			
		    		            		else if(ret[0][0].equals("-2"))
		    		            			req.setAttribute("msg","No Entries in Monthly Status");
		    		            			
		    		            	}
		    		         			//checkmth result end
		    		         		}
		    		         		else 
		    		         			month_close.setValidations("Month should be less than present Month");
		    		         			
		    		         	
		                     
		                     }catch(Exception e){
		                    	 e.printStackTrace();
		                     }
		    	  
		    	  //submit code start
		    	  
		            
		           
		    	  
	    			  
	    			
	  		             
	  		            
	  		        
	  		        
	    			  
	    			  
	    			  
	    			  
	    			  
	    			  
	    		  }
		    	  }	
		    		  if(month_close.getFlag().equalsIgnoreCase("submit")){
			    		  
			    		  int b=-1;
                          String month=null;
		    			  
		    			  if(Integer.parseInt(month_close.getMonth())<9){
		    				  month="0"+month_close.getMonth();
		    			  }
		    			  else{
		    				  month=month_close.getMonth();
		    			  }
			              if(month_close.getYear().trim() !=null && month_close.getYear().trim() !=null && month_close.getYear().trim().length()==4 && (month_close.getMonth().trim().length()==1 || month_close.getMonth().trim().length()==2) )	
			              	b=gldelegate.closeMonth(Integer.parseInt(month_close.getYear().trim() ),Integer.parseInt(month.trim()));
			              
			              if(b==1)
			              {
			                  
			            	  req.setAttribute("msg","Month Closed!");
			                  
			              }else
			            	  req.setAttribute("msg","Error!");
			                 
			              
			             req.setAttribute("MthClose", "NoClose");
			              
			          }
		    		  //getSuccess
		    		  return map.findForward(ResultHelp.getSuccess());
		              }
		                     
	    		  
		    	  
		    	  //submit code end
			}catch(Exception e){
				e.printStackTrace();
			}
		    	  
			 
		            }
		    		  
		    		  
		    		  //end amzad
		    		  
		    		  
		    		  
		    		  
		    		  
		    		  	/** **************GL Month Close End********************* */
		
		/** **************GL Year Close Start********************* */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLYearCloseMenu"))
		{
	      try{
	    	  YearClose year_close=(YearClose)form;
	    	  req.setAttribute("msg","");
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+year_close);
	    	  System.out.println("the page id is "+year_close.getPageId());
	    	  req.setAttribute("pagenum", year_close.getPageId());
	    	  year_close.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(year_close.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(year_close.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLYearClose")){
			try{
				YearClose year_close=(YearClose)form;
				req.setAttribute("msg","");
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+year_close);
		    	  System.out.println("the page id is "+year_close.getPageId());
		    	  req.setAttribute("pagenum", year_close.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(year_close.getPageId()))
		    	  {
		    		  String yearmonth=year_close.getYear().concat(year_close.getMonth());
		    		  System.out.println("value of YearMonth in Action Class is==="+yearmonth);
		    		  path=MenuNameReader.getScreenProperties(year_close.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  year_close.setYear(year_close.getYear());
		    		  year_close.setMonth(year_close.getMonth());
		    		  System.out.println("The button valuee is"+year_close.getForward());
		    		  //focus lost start
		    		  if(year_close.getFlag().equalsIgnoreCase("from_date")){
		    		  try{  String month=null;
		                	month=year_close.getMonth().trim();
		                	if(month.length()==1)
		                	     month="0"+month;
		                	else
		                		month=month;
				         	if(Validations.checkInteger(year_close.getMonth().trim())==false)
				         		req.setAttribute("msg","Please enter number's in Month");
				         		
				         	if(Validations.checkInteger(year_close.getYear().trim())==false)
				         		req.setAttribute("msg","Please enter number's in year");
				         		
				         	if(Integer.parseInt(year_close.getMonth().trim())>12)
				         		req.setAttribute("msg","Please enter correct month");
				         		
				         	
				         	if(year_close.getMonth().trim().length()==1)
				         		month="0"+year_close.getMonth().trim();
				         	if(year_close.getMonth().trim().length()==3)
				         		req.setAttribute("msg","Please enter correct Month");
				         		
				         	if(year_close.getYear().trim().length()<4 || year_close.getYear().trim().length()>4)
				         		req.setAttribute("msg","Please enter correct Year");
				         		
				         	if(month.trim().length()==2 && year_close.getYear().trim().length()==4){
				         		String lastdate=Validations.lastDayOfMonth("01"+"/"+month+"/"+year_close.getYear().trim());
				         		if(Validations.dayCompare(lastdate,gldelegate.getSysDate())>0){
				         			String firstdate="01"+"/"+month+"/"+year_close.getYear().trim();
				         			//code of checkYearClose start
				         			
				                	try
				        			{        		
				                		String qtr_mth=null;
				                		String  ret[][]=null;
				                		qtr_mth=gldelegate.yearClose();
				                		System.out.println("qtr_mth="+qtr_mth);
				                		if(qtr_mth.equals("0"))
				                			req.setAttribute("msg","Month doesn't match with the yearly Defintaion");
				                			
				                		else if(Integer.parseInt(qtr_mth)!= Integer.parseInt(year_close.getMonth().trim()))
				                			req.setAttribute("msg","Month doesn't match with the yearly Defintaion");
				                			
				                		else if(Integer.parseInt(qtr_mth)== Integer.parseInt(year_close.getMonth().trim())){
				                			ret=gldelegate.yearStatus(qtr_mth);
				                			
				                			System.out.println("ret="+ret[0][0]);
				                			if(ret[0][0].equals("YT"))
				                				req.setAttribute("msg","Year already Closed you cannot do Posting");
				                				
				                			else if(ret[0][0].equals("PF"))
				                				req.setAttribute("msg","You didn't do posting for the year"+ret[0][1]+" you cannot continue");
				                			
				                				
				                			else if(ret[0][0].equals("PT")){
				                				req.setAttribute("msg","Positng already done ");//do you to overwrite? 
				                				/*int r=JOptionPane.showConfirmDialog(null,"Positng already done do you to overwrite? ");
				                				if(r==JOptionPane.YES_OPTION){
				                					string_from_date=ret[0][1];
				                    				file_logger.info("ret [0][1]="+ret[0][1]);
				                    				//but_submit.setEnabled(true);
				                    				enableSubmitButton(true);
				                    				but_submit.setFocusable(true);
				                    				//but_post.setEnabled(true);
				                    				enablePostButton(true);
				                				}
				                				if(r==JOptionPane.NO_OPTION){
				                					but_close.setFocusable(true);
				                					//but_close.setEnabled(true);
				                					enableCloseButton(true);
				                				}*/
				                			}
				                			else if(ret[0][0].equals("CN"))
				                				req.setAttribute("msg","Sorry you dont have entries in Monthly Status");
				                				
				                			else if(ret[0][0].equals("-2") ||ret[0][0].equals("NO") )
				                				req.setAttribute("msg","Sorry Monthly Status not updated for"+ret[0][1]+" you cannot continue ");
				                				
				                			else if(ret[0][0].equals("-1"))
				                				req.setAttribute("msg","Sorry you dont have entries in Monthly Status");
				                				
				                			else if(ret[0][0].equals("YN"))
				                				req.setAttribute("msg","Yearly Status not updated for date  "+ret[0][1]+" you cannot continue");
				                				
				                			else if(ret[0][0].equals("YN1")|| ret[0][0].equals("MN1"))
				                				req.setAttribute("msg","posting date should be less than or equal "+ret[0][1]+" you cannot continue ");
				                				
				                			else if(ret[0][0].equals("OK")){
				                				String string_from_date=ret[0][1];
				                				/*file_logger.info("ret [0][1]="+ret[0][1]);
				                				//but_submit.setEnabled(true);
				                				enableSubmitButton(true);
				                				but_submit.setFocusable(true);
				                				//but_post.setEnabled(true);
				                				enablePostButton(true);*/
				                			}
				                		}
				                	}
				                	catch(IllegalStateException ise)
				        			{
				                		ise.printStackTrace();
				        			}
				                	catch(RemoteException re)
				        			{
				                		re.printStackTrace();
				        			}
				                	catch(Exception e){e.printStackTrace();}
				         			
				         			
				         			
				         			
				         			
				         			//code of checkYearClose end
				         			
				         			
				         		}
				         		else 
				         			req.setAttribute("msg","Month should be less than present Month");
				         			
				         	}
		                 }catch(NumberFormatException ne){ne.printStackTrace();}
		                 catch(Exception ae){ae.printStackTrace();}
		    		  }
		    		  
		    		  
		    		  //focus lost end
		    		  //code of submit start
		    		  	if(year_close.getFlag().equalsIgnoreCase("submit")){
		    		  		String month=null;
			    			  
			    			  if(Integer.parseInt(year_close.getMonth())<9){
			    				  month="0"+year_close.getMonth();
			    			  }
			    			  else{
			    				  month=year_close.getMonth();
			    			  }
		    		  		double b=0.0;
		    		  		String profit="";
		    	              try{
		    	              if(year_close.getYear().trim() !=null && year_close.getYear().trim() !=null && year_close.getYear().trim().length()==4 && (year_close.getMonth().trim().length()==1 || year_close.getMonth().trim().length()==2) )	
		    	              	b=gldelegate.getPlProfit(year_close.getYear(), month);
		    	              	profit=String.valueOf(b);
		    	              	req.setAttribute("profit",profit);
		    	              }catch(NumberFormatException ne){ne.printStackTrace();}   
		    	              catch(RemoteException re){re.printStackTrace();}	
		    	              year_close.setYear(year_close.getYear());
		    		  		
		    			  
		    		  }
		    		  	//code of submit end
		    		  
		    		  	 //code of post start
		    		  	if(year_close.getFlag().equalsIgnoreCase("post")){
		    		  		String month=null;
			    			  
			    			  if(Integer.parseInt(year_close.getMonth())<9){
			    				  month="0"+year_close.getMonth();
			    			  }
			    			  else{
			    				  month=year_close.getMonth();
			    			  }
		    		  		try{
		    		          	int ret=gldelegate.PlPosting(year_close.getYear().trim(),month,gluser,gltml,(String)session.getAttribute("BankLocation"));
		    		          	
		    		          	if(ret!=1)
		    		          		req.setAttribute("msg","Error in Posting");
		    		          		
		    					else if(ret==1){
		    						req.setAttribute("msg","Posting Done");
		    						
		    						year_close.setYear(year_close.getYear());
		    						
		    					}
		    		          	}catch(Exception ae){ae.printStackTrace();}  
		    		  		
		    		  		
		    		  		
		    		  		
		    		  		
		    		  }
		    		  	//code of post end
		    		  
		    		  	 //code of close start
		    		  	if(year_close.getFlag().equalsIgnoreCase("close")){
		    		  		String month=null;
			    			  
			    			  if(Integer.parseInt(year_close.getMonth())<9){
			    				  month="0"+year_close.getMonth();
			    			  }
			    			  else{
			    				  month=year_close.getMonth();
			    			  }
		    		  		try{
		    	        		int ret=gldelegate.closeYear(year_close.getYear(), month,(String)session.getAttribute("BankLocation"));
		    	        		if(ret==1)
		    	        			req.setAttribute("msg","Year Closed");
		    	        			
		    	        		else
		    	        			req.setAttribute("msg","Error");
		    	        			
		    	        	}catch(Exception ae){ae.printStackTrace();}
		    		  		
		    	        	year_close.setYear(year_close.getYear());
		    		  		
		    		  		
		    		  		
		    		  		
		    		  }
		    		  	//code of close end
		    		  	path=MenuNameReader.getScreenProperties(year_close.getPageId());
			    		  gldelegate=new GLDelegate();
			    		  setGLOpeningInitParams(req, path, gldelegate);
			    		  System.out.println("the path from MenuNameReader is "+path);
			    		  req.setAttribute("pageId",path);
			    		  year_close.setYear(year_close.getYear());
			    		  year_close.setMonth(year_close.getMonth());
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
		
		
	
		
		
		
		
		
		
		
		
		/** **************GL Year Close End********************* */
		/** **************GL YearClosingCF Start********************* */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLYearClosingCFMenu"))
		{
	      try{
	    	  YearClose year_close=(YearClose)form;
	    	  req.setAttribute("msg","");
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+year_close);
	    	  System.out.println("the page id is "+year_close.getPageId());
	    	  req.setAttribute("pagenum", year_close.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(year_close.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(year_close.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLYearClosingCF")){
			try{
				YearClose year_close=(YearClose)form;
				req.setAttribute("msg","");
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+year_close);
		    	  System.out.println("the page id is "+year_close.getPageId());
		    	  req.setAttribute("pagenum", year_close.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(year_close.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(year_close.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+year_close.getForward());
		    		  
		    		  	/*if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }*/
		    		  
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
		
		
	
		
		
		
		
		
		
		
		
		
		
		/** **************GL YearClosingCF End********************* */
		/** **************GL BranchPLPosting Start********************* */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLBranchPLPostingMenu"))
		{
	      try{
	    	  YearClose year_close=(YearClose)form;
	    	  req.setAttribute("msg","");
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+year_close);
	    	  System.out.println("the page id is "+year_close.getPageId());
	    	  req.setAttribute("pagenum", year_close.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(year_close.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(year_close.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLBranchPLPosting")){
			try{
				YearClose year_close=(YearClose)form;
				req.setAttribute("msg","");
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+year_close);
		    	  System.out.println("the page id is "+year_close.getPageId());
		    	  req.setAttribute("pagenum", year_close.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(year_close.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(year_close.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+year_close.getForward());
		    		  
		    		  	/*if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }*/
		    		  
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
		
		
	
		
		
		
	
		
		
		
		
		
		/** **************GL BranchPLPosting End********************* */
		/** **************GL DayBook Start********************* */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLDayBookMenu"))
		{
			   
	      try{
	    	  DayBook daybook=(DayBook)form;
	    	  req.setAttribute("msg","");
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+daybook);
	    	  System.out.println("the page id is "+daybook.getPageId());
	    	  req.setAttribute("pagenum", daybook.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(daybook.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(daybook.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  daybook.setDate(gldelegate.getSysDate());
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLDayBook")){
			try{
				DayBook daybook=(DayBook)form;
				req.setAttribute("msg","");
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+daybook);
		    	  System.out.println("the page id is "+daybook.getPageId());
		    	  req.setAttribute("pagenum", daybook.getPageId());
		    	  
			      			 
		    	  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(daybook.getPageId()))
		    	  {   DayBookObject array_daybook[]=null;
		    		  path=MenuNameReader.getScreenProperties(daybook.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+daybook.getForward());
		    		  //focuslost start
		    		  if(daybook.getFlag().equalsIgnoreCase("fromdate")){
		    		  if(daybook.getDate()!=null){
		    				try{ int ret=0;
		    					Validations.checkDate(daybook.getDate().trim());
		    					ret=Validations.dayCompare(daybook.getDate().trim(),gldelegate.getSysDate());
		    					
		    					if(ret<0)
		    						req.setAttribute("msg","Date Should be less than or equal to current Date");
		    					else{
		    					ret=gldelegate.checkDlyStatusReport(daybook.getDate());
		    					if(ret==1) {
		    						req.setAttribute("msg","Posting not Done ");
		    						
		    					}
		    					else{
		    						//req.setAttribute("msg","Posting has Done, You can proceed.. ");
		    					    daybook.setDate(daybook.getDate());
		    					req.setAttribute("Youcandownload", "Download");
		    					req.setAttribute("showdwn", "true");
		    					}
		    					    
		    					}
		    					
		    					
		    				}catch(DateFormatException e){
		    					e.printStackTrace();
		    				}catch(RemoteException re){
		    					re.printStackTrace();
		    				}
		    	  }
		    			
		    		  }		  
		    		  
		    		  
		    		  
		    		  //focuslost end
		    				//for viewing records start
		    				 if(daybook.getFlag().equalsIgnoreCase("view") || daybook.getFlag().equalsIgnoreCase("file")){
		    				try{ 
		    					double dr_closetotal1=0.0;
		    					double close_bal=0.0;
		    					double cashdr_total=0.0,cgdr_total=0.0,trfdr_total=0.0,dr_total1=0.0,cashcr_total=0.0,cashcr_total1=0.0,cgcr_total=0.0,trfcr_total=0.0,cr_total1=0.0,closebal=0.0,dr_closetotal=0.0,cr_closetotal=0.0,cashdr_total1=0.0,dr_total=0.0,cr_total=0.0,cr_closetotal1=0.0;
			    			  System.out.println("The date is "+daybook.getDate());
			    			  DayBookObjectNew newDbook[]=null;
			    			  System.out.println("=====converted date========="+convertToYYYYMM(daybook.getDate()));
			    			  array_daybook=gldelegate.getRecords(daybook.getDate());
			    			  //code changed by amzad on 04.07.2009
			    			  newDbook=new DayBookObjectNew[array_daybook.length];
			    			  int ak=0;
			    			  /*if(newDbook!=null){
			    				  for(int i=1;i<array_daybook.length;i++)
	    							{
			    					  if(array_daybook[i].getdr_total()==0.0 && array_daybook[i].getcr_total()==0.0 && daybook.getUserChoice().equalsIgnoreCase("daybook")){
	    									
	    									continue;
	    										 	
	    								}
	    								else
	    								{
	    									if(array_daybook[i].getgl_code()==array_daybook[0].getcash_glcode()){
	    										System.out.println("======inside second if cond..........");
	    										continue;
	    									}
	    									newDbook[ak]=new DayBookObjectNew();
	    									newDbook[ak].setGlAbbr(array_daybook[i].getgl_abbr());
	    									newDbook[ak].setGlCode(array_daybook[i].getgl_code());
	    									newDbook[ak].setGlName(array_daybook[i].getgl_name());
	    									System.out.println("Value of the Debit Transfer_________"+DoubleFormat.doubleToString(array_daybook[i].getdr_trans(),2));
	    									if(array_daybook[i].getdr_cash()==0.0)
	    										newDbook[ak].setDrCash(" ");
		    								else
	    									    newDbook[ak].setDrCash(" "+DoubleFormat.doubleToString(array_daybook[i].getdr_cash(),2));
	    									if(array_daybook[i].getdr_clear()==0.0)
	    										newDbook[ak].setDrClear(" ");
		    								else
	    									    newDbook[ak].setDrClear(" "+DoubleFormat.doubleToString(array_daybook[i].getdr_clear(),2));
	    									if(array_daybook[i].getdr_trans()==0.0)
	    										newDbook[ak].setDrTrans(" ");
		    								else
	    									    newDbook[ak].setDrTrans(" "+DoubleFormat.doubleToString(array_daybook[i].getdr_trans(),2));
	    									System.out.println("value of getDrTrans in the aciton class__________"+newDbook[ak].getDrTrans());
	    									if(array_daybook[i].getdr_total()==0.0)
	    										newDbook[ak].setDrTotal(" ");
		    								else
	    									    newDbook[ak].setDrTotal(" "+DoubleFormat.doubleToString(array_daybook[i].getdr_total(),2));
	    									if(array_daybook[i].getcr_cash()==0.0)
	    										newDbook[ak].setCrCash(" ");
		    								else
	    									    newDbook[ak].setCrCash(" "+DoubleFormat.doubleToString(array_daybook[i].getcr_cash(),2));
	    									if(array_daybook[i].getcr_clear()==0.0)
	    										newDbook[ak].setCrClear(" ");
		    								else
	    									    newDbook[ak].setCrClear(" "+DoubleFormat.doubleToString(array_daybook[i].getcr_clear(),2));
	    									if(array_daybook[i].getcr_trans()==0.0)
	    										newDbook[ak].setCrTrans(" ");
		    								else
	    									    newDbook[ak].setCrTrans(" "+DoubleFormat.doubleToString(array_daybook[i].getcr_trans(),2));
	    									if(array_daybook[i].getcr_total()==0.0)
	    										newDbook[ak].setCrTotal(" ");
		    								else
	    									    newDbook[ak].setCrTotal(" "+DoubleFormat.doubleToString(array_daybook[i].getcr_total(),2));
	    									if(array_daybook[i].get_net()==0.0)
	    										newDbook[ak].setNetTrans(" ");
		    								else
	    									    newDbook[ak].setNetTrans(" "+DoubleFormat.doubleToString(Math.abs(array_daybook[i].get_net()),2));
	    									close_bal=array_daybook[i].getclose_bal();
		    								
		    								if(close_bal<0.0)
		    								{
		    									dr_closetotal=dr_closetotal+close_bal;
		    									//dr_closetotal1=dr_closetotal+array_DayBookObject[0].getcash_closebal();
		    									
		    								}
		    								else
		    								{
		    									cr_closetotal=cr_closetotal+close_bal;
		    								    
		    								}
		    								if(daybook.getUserChoice().equalsIgnoreCase("trailbalance"))
		    								{
		    								if(close_bal<0.0)
		    								{
		    									newDbook[ak].setDrClBal(Math.abs(array_daybook[i].getclose_bal()));
		    									newDbook[ak].setCrClBal(0.0);
		    								}
		    								else
		    								{
		    									newDbook[ak].setDrClBal(0.0);
		    									newDbook[ak].setCrClBal(array_daybook[i].getclose_bal());
		    								}
		    								}
		    								
                                            ak++;
	    								}
			    					  
	    							}
			    			  }else{
			    				  req.setAttribute("msg","Records Not Found");
			    			  }
			    			  req.setAttribute("DayBookNewObject", newDbook);*///for NewDaybook
			    			  //code changed by amzad on 04.07.2009
			    			  System.out.println("lenght========== of arrybookobj"+array_daybook.length);
			    			  if(array_daybook.length>0){
			    				//code for showing the records starts
			    				  req.setAttribute("DisplayDaybookObject", array_daybook);
			    				  Object[][] data=null;
			    				  Object[][] data1=null;
			    				  
			    					if(daybook.getUserChoice().equalsIgnoreCase("trailbalance"))
			    					{
			    					data=new Object[array_daybook.length][14];
			    					
			    					}
			    					else
			    					{
			    					data=new Object[array_daybook.length][12];
			    					
			    					}
			    						
			    			     int j;
			    					String str_temp=new String();
			    					
			    					try
			    					{
			    						if(array_daybook!=null)
			    						{ 
			    							
			    							
			    							if(daybook.getUserChoice().equalsIgnoreCase("trailbalance"))
			    								{
			    								
			    								for(j=0;j<13;j++){}
			    								}
			    							if(daybook.getUserChoice().equalsIgnoreCase("daybook"))
			    							{
			    								for(j=0;j<11;j++){}	
			    							}
			    							
			    							data[0][0]=" ";
			    							data[0][1]="Cash";
			    							data[0][2]="Opening Balance";
			    							data[0][3]="  ";
			    							data[0][4]="  ";
			    							data[0][5]="  ";
			    							data[0][6]="  "+DoubleFormat.doubleToString(array_daybook[0].getcash_openbal(),2);
			    							data[0][7]="  ";
			    							data[0][8]="  ";
			    							data[0][9]="  ";
			    							data[0][10]="  "+DoubleFormat.doubleToString(array_daybook[0].getcash_openbal(),2);
			    							data[0][11]="  ";
			    							if(daybook.getUserChoice().equalsIgnoreCase("trailbalance"))
			    							{
			    							data[0][12]="  ";
			    							data[0][13]="  ";
			    							}
			    							
			    							
			    							
			    							for(int i=1;i<array_daybook.length;i++)
			    							{	
			    								
			    								if(array_daybook[i].getdr_total()==0.0 && array_daybook[i].getcr_total()==0.0 && daybook.getUserChoice().equalsIgnoreCase("daybook")){
			    									
			    									continue;
			    										 	
			    								}
			    								else
			    								{
			    									if(array_daybook[i].getgl_code()==array_daybook[0].getcash_glcode()){
			    										System.out.println("======inside second if cond..........");
			    										continue;
			    									}
			    									/*if(array_daybook[i].getdr_total()==0.0 && array_daybook[i].getcr_total()==0.0 && daybook.getUserChoice().equalsIgnoreCase("daybook")){
			    										System.out.println("======inside new if cond..........");
			    										continue;
			    									}*/
			    								data[i][0]="  "+String.valueOf(array_daybook[i].getgl_abbr());
			    								data[i][1]="  "+String.valueOf(array_daybook[i].getgl_code());
			    								data[i][2]="  "+array_daybook[i].getgl_name();
			    								if(array_daybook[i].getdr_cash()==0.0)
			    									data[i][3]=" ";
			    								else
			    									data[i][3]=" "+DoubleFormat.doubleToString(array_daybook[i].getdr_cash(),2);
			    								
			    								if(array_daybook[i].getdr_clear()==0.0)
			    									data[i][4]=" ";
			    								else
			    									data[i][4]=" "+DoubleFormat.doubleToString(array_daybook[i].getdr_clear(),2);
			    								
			    								if(array_daybook[i].getdr_trans()==0.0)
			    									data[i][5]=" ";
			    								else
			    									data[i][5]=" "+DoubleFormat.doubleToString(array_daybook[i].getdr_trans(),2);
			    								
			    								if(array_daybook[i].getdr_total()==0.0)
			    									data[i][6]=" ";
			    								else
			    									data[i][6]=" "+DoubleFormat.doubleToString(array_daybook[i].getdr_total(),2);
			    								
			    								if(array_daybook[i].getcr_cash()==0.0)
			    									data[i][7]=" ";
			    								else
			    									data[i][7]="  "+DoubleFormat.doubleToString(array_daybook[i].getcr_cash(),2);
			    								
			    								if(array_daybook[i].getcr_clear()==0.0)
			    									data[i][8]=" ";
			    								else
			    									data[i][8]=" "+DoubleFormat.doubleToString(array_daybook[i].getcr_clear(),2);
			    								
			    								if(array_daybook[i].getcr_trans()==0.0)
			    									data[i][9]=" ";
			    								else
			    									data[i][9]="  "+DoubleFormat.doubleToString(array_daybook[i].getcr_trans(),2);
			    								
			    								if(array_daybook[i].getcr_total()==0.0)
			    									data[i][10]=" ";
			    								else
			    									data[i][10]="  "+DoubleFormat.doubleToString(array_daybook[i].getcr_total(),2);
			    								
			    								if(array_daybook[i].get_net()==0.0)
			    									data[i][11]=" ";
			    								else{
			    									double netvalue=array_daybook[i].get_net();
			    								
			    			                    if(netvalue<0.0)
			    			                    	str_temp="Dr";
			    			                    else
			    			                    	str_temp="Cr";
			    			                    
			    								data[i][11]="  "+DoubleFormat.doubleToString(Math.abs(array_daybook[i].get_net()),2)+str_temp;
			    								}

			    								close_bal=array_daybook[i].getclose_bal();
			    								
			    								if(close_bal<0.0)
			    								{
			    									dr_closetotal=dr_closetotal+close_bal;
			    									//dr_closetotal1=dr_closetotal+array_DayBookObject[0].getcash_closebal();
			    									
			    								}
			    								else
			    								{
			    									cr_closetotal=cr_closetotal+close_bal;
			    								    
			    								}
			    								if(daybook.getUserChoice().equalsIgnoreCase("trailbalance"))
			    								{
			    								if(close_bal<0.0)
			    								{
			    								data[i][12]=" "+DoubleFormat.doubleToString(Math.abs(array_daybook[i].getclose_bal()),2);
			    								data[i][13]=" ";
			    								}
			    								else
			    								{
			    								data[i][12]="  ";
			    								data[i][13]="  "+DoubleFormat.doubleToString(array_daybook[i].getclose_bal(),2);
			    								}
			    								}
			    								//dtm.addRow(data);
			    								}
			    								cashdr_total=cashdr_total+array_daybook[i].getdr_cash();
			    								cgdr_total=cgdr_total+array_daybook[i].getdr_clear();
			    								trfdr_total=trfdr_total+array_daybook[i].getdr_trans();
			    								dr_total1=dr_total1+array_daybook[i].getdr_total();
			    								cashcr_total=cashcr_total+array_daybook[i].getcr_cash();
			    								cgcr_total=cgcr_total+array_daybook[i].getcr_clear();
			    								trfcr_total=trfcr_total+array_daybook[i].getcr_trans();
			    								cr_total1=cr_total1+array_daybook[i].getcr_total();
			    								//closebal=array_DayBookObject[i].getclose_bal();
			    								
			    							 } //end of for
			    							cashdr_total1=cashdr_total+array_daybook[0].getcash_closebal();
			    							dr_total=dr_total1+array_daybook[0].getcash_closebal();
			    							cr_total=cr_total1+array_daybook[0].getcash_openbal();
			    							cashcr_total1=cashcr_total+array_daybook[0].getcash_openbal();
			    							dr_closetotal1=dr_closetotal-array_daybook[0].getcash_closebal();
			    							
			    							
			    							if(daybook.getUserChoice().equalsIgnoreCase("trailbalance"))
					    					{
					    					data1=new Object[3][14];
					    					
					    					}
					    					else
					    					{
					    					data1=new Object[3][12];
					    					
					    					}
					    						
			    							data1[0][0]=" ";
			    							data1[0][1]="Cash";
			    							data1[0][2]="Closing Balance";
			    							data1[0][3]="";
			    							data1[0][4]="  ";
			    							data1[0][5]="  ";
			    							data1[0][6]="  "+DoubleFormat.doubleToString(array_daybook[0].getcash_closebal(),2);
			    							data1[0][7]="  ";
			    							data1[0][8]="  ";
			    							data1[0][9]="  ";
			    							data1[0][10]="  "+DoubleFormat.doubleToString(array_daybook[0].getcash_closebal(),2);
			    							data1[0][11]="  ";
			    							if(daybook.getUserChoice().equalsIgnoreCase("trailbalance"))
			    							{
			    								data1[0][12]="  "+DoubleFormat.doubleToString(array_daybook[0].getcash_closebal(),2);
			    								data1[0][13]="  ";
			    								
			    				 			}
			    							
			    							
			    							data1[1][0]=" ";
			    							data1[1][1]=" ";
			    							data1[1][2]="Totals";
			    							data1[1][3]="  "+DoubleFormat.doubleToString(cashdr_total1,2);
			    							data1[1][4]="  "+DoubleFormat.doubleToString(cgdr_total,2);
			    							data1[1][5]="  "+DoubleFormat.doubleToString(trfdr_total,2);
			    							data1[1][6]="  "+DoubleFormat.doubleToString(dr_total,2);
			    							data1[1][7]="  "+DoubleFormat.doubleToString(cashcr_total1,2);
			    							data1[1][8]="  "+DoubleFormat.doubleToString(cgcr_total,2);
			    							data1[1][9]="  "+DoubleFormat.doubleToString(trfcr_total,2);
			    							data1[1][10]="  "+DoubleFormat.doubleToString(cr_total,2);
			    							data1[1][11]="  ";
			    							if(daybook.getUserChoice().equalsIgnoreCase("trailbalance"))
			    							{
			    								data1[1][12]="  "+DoubleFormat.doubleToString(Math.abs(dr_closetotal1),2);
			    								data1[1][13]=" "+DoubleFormat.doubleToString(cr_closetotal,2);
			    							}
			    							//code added on 07.02.2009 starts
			    							data1[2][0]=" ";
			    							data1[2][1]="Cash";
			    							data1[2][2]="Opening Balance";
			    							data1[2][3]="  ";
			    							data1[2][4]="  ";
			    							data1[2][5]="  ";
			    							data1[2][6]="  "+DoubleFormat.doubleToString(array_daybook[0].getcash_openbal(),2);
			    							data1[2][7]="  ";
			    							data1[2][8]="  ";
			    							data1[2][9]="  ";
			    							data1[2][10]="  "+DoubleFormat.doubleToString(array_daybook[0].getcash_openbal(),2);
			    							data1[2][11]="  ";
			    							if(daybook.getUserChoice().equalsIgnoreCase("trailbalance"))
			    							{
			    							data1[2][12]="  ";
			    							data1[2][13]="  ";
			    							}
			    							//code added on 07.02.2009 ends
			    							
			    							
			    							
			    							
			    					    } //end of if
			    						
			    						if(!(DoubleFormat.doubleToString(dr_total,2).equals(DoubleFormat.doubleToString(cr_total,2))))
			    						{
			    							req.setAttribute("msg","DayBook does not tally");
			    						}
			    						if(daybook.getUserChoice().equalsIgnoreCase("trailbalance"))
			    						{
			    						if(!(DoubleFormat.doubleToString(Math.abs(dr_closetotal1),2).equals(DoubleFormat.doubleToString(cr_closetotal,2))))
			    						{
			    							req.setAttribute("msg","Trial Balance does not tally");
			    						}
			    						}
			    						req.setAttribute("daybookDataObj", data);
			    						req.setAttribute("daybookDataObj1", data1);
			    						if(daybook.getFlag().equalsIgnoreCase("file")){
			    							//String filename=req.getParameter("fname");
			    			  				try{
			    			  				res.setContentType("application/.csv");
			    			           	    res.setHeader("Content-disposition", "attachment;filename=output.csv");
			    			           	    PrintWriter out=res.getWriter();
			    			  				/*HSSFWorkbook wb= new HSSFWorkbook();
			    			  				HSSFSheet sheet = wb.createSheet("daybook");
			    			  				sheet.setColumnWidth((short)0, (short)1000);
			    			  				sheet.setColumnWidth((short)1, (short)3000);
			    			  				sheet.setColumnWidth((short)2, (short)5000);
			    			  				sheet.setColumnWidth((short)3, (short)5000);
			    			  				sheet.setColumnWidth((short)4, (short)5000);
			    			  				sheet.setColumnWidth((short)5, (short)5000);
			    			  				sheet.setColumnWidth((short)6, (short)5000);
			    			  				sheet.setColumnWidth((short)7, (short)5000);
			    			  				sheet.setColumnWidth((short)8, (short)5000);
			    			  				sheet.setColumnWidth((short)9, (short)5000);
			    			  				sheet.setColumnWidth((short)10, (short)5000);
			    			  				sheet.setColumnWidth((short)11, (short)5000);
			    			  				sheet.setColumnWidth((short)12, (short)5000);
			    			  				sheet.setColumnWidth((short)13, (short)5000);
			    			  				sheet.setColumnWidth((short)14, (short)5000);
			    			  				HSSFFont font = wb.createFont();
			    			  	            font.setColor(HSSFFont.COLOR_RED);
			    			  	            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			    			  	            
			    			  	          HSSFCellStyle cellStyle= wb.createCellStyle();
			    			              cellStyle.setFont(font);
			    			  				HSSFRow row = sheet.createRow((short)2);
			    				  			HSSFCell cell=row.createCell((short)5);   
			    				  			cell.setCellStyle(cellStyle);
			    				            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			    				            cell.setCellValue("");*/
			    				            if(daybook.getUserChoice().equalsIgnoreCase("DayBook")){
			    				            	out.print("\n");
			    			        	        out.print("\n");
			    			        	        out.print("\n");
			    			        	        out.print(",");
			    				                out.print("Daybook For  "+daybook.getDate());
			    			  				}else{
			    			  					out.print("\n");
			    			        	        out.print("\n");
			    			        	        out.print("\n");
			    			        	        out.print(",");
			    			        	        out.print("Trial Balance For  "+daybook.getDate());
			    			  				}
			    				            out.print("\n");
			    				            out.print(",");out.print("General Ledger");out.print(",");
			    				            out.print(",");
			    				            out.print("Debit Amount");out.print(",");out.print(",");
			    				            out.print(",");out.print(",");
			    				            out.print("Credit Amount");out.print(",");out.print(",");
			    				            out.print(",");out.print(",");
			    				            out.print("Net");out.print(",");
			    			                if(daybook.getUserChoice().equalsIgnoreCase("TrailBalance")){
			    			                	out.print("Closing Balances");
			    			                }
			    			                out.print("\n");
			    			                out.print("Type");out.print(",");
			    			                out.print("Code");out.print(",");
			    			                out.print("Name");out.print(",");
			    			                out.print("Cash");out.print(",");
			    			                out.print("Clearing"); out.print(",");
			    			                out.print("Transfer"); out.print(",");
			    			                out.print("Total");out.print(",");
			    			                out.print("Cash");out.print(",");
			    			                out.print("Clearing");out.print(",");
			    			                out.print("Transfer"); out.print(",");
			    			                out.print("Total"); out.print(",");
			    			                out.print("Transaction"); out.print(",");
			    				            if(daybook.getUserChoice().equalsIgnoreCase("TrailBalance")){
			    				            	out.print("Debit"); out.print(",");
			    				            	out.print("Credit"); out.print(",");
			    				            }
			    				            out.print("\n");
			    				            int count=1;
			    				            int r=7;		  
			    				            int temp;
			    				            int cou=0;
			    				  			if(data!=null && data.length!=0){
			    				  				for(int i=0;i<data.length;i++){
			    				  					if(daybook.getUserChoice().equalsIgnoreCase("DayBook")){
			    				  						temp=i;
			    				  						for(int t=0;t<12;t++){
			    				  							if(data[temp][t]==null)
			    				  								cou=cou+1;
			    				  						}
			    				  					}
			    				  					if(cou!=0 && cou==12)
			    				  						continue;
			    				  				   if(data[i][0]!=null){
			    				  					 out.print(data[i][0].toString());out.print(",");
			    				  				   }else{
			    				  					 out.print("");out.print(",");}
			    				  				   if(data[i][1]!=null){
			    				  					 out.print(data[i][1].toString());out.print(",");
			    				  				   }else{
			    				  					 out.print("");out.print(",");}
			    				  				   if(data[i][2]!=null){
			    				  					 out.print(data[i][2].toString());out.print(",");}
			    				  				   else{
			    				  					 out.print("");out.print(",");}
			    				  				   if(data[i][3]!=null){
			    				  					 out.print(data[i][3].toString());out.print(",");}
			    				  				   else{
			    				  					 out.print("");out.print(",");} 
			    				  				   if(data[i][4]!=null){
			    				  					 out.print(data[i][4].toString());out.print(",");}
			    				  				   else{
			    				  					 out.print("");out.print(",");}
			    				  				   if(data[i][5]!=null){
			    				  					 out.print(data[i][5].toString());out.print(",");}
			    				  				   else{
			    				  					 out.print("");out.print(",");} 
			    				  				   if(data[i][6]!=null){
			    				  					 out.print(data[i][6].toString());out.print(",");}
			    				  				   else{
			    				  					 out.print("");out.print(",");} 
			    				  				   if(data[i][7]!=null){
			    				  					 out.print(data[i][7].toString());out.print(",");}
			    				  				   else{
			    				  					 out.print("");out.print(",");}
			    				  				   if(data[i][8]!=null){
			    				  					 out.print(data[i][8].toString());out.print(",");}
			    				  				   else{
			    				  					 out.print("");out.print(",");}
			    				  				   if(data[i][9]!=null){
			    				  					 out.print(data[i][9].toString());out.print(",");}
			    				  				   else{
			    				  					 out.print("");out.print(",");}
			    				  				   if(data[i][10]!=null){
			    				  					 out.print(data[i][10].toString());out.print(",");}
			    				  				   else{
			    				  					 out.print("");out.print(",");}
			    				  				   if(data[i][11]!=null){
			    				  					 out.print(data[i][11].toString());out.print(",");}
			    				  				   else{
			    				  					 out.print("");out.print(",");}
			    				  				if(daybook.getUserChoice().equalsIgnoreCase("TrailBalance")){
			    				  					if(data[i][12]!=null){
			    				  						out.print(data[i][12].toString());out.print(",");}
			    				  					else{
			    				  						out.print("");out.print(",");}
			    				  					if(data[i][13]!=null){
			    				  						out.print(data[i][13].toString());out.print(",");}
			    				  					else{
			    				  						out.print("");out.print(",");}
			    				  				}
			    				  				
			    				  				
			    				  				out.print("\n");
			    				  				}
			    				  				for(int l=0;l<data1.length-1;l++){
			    				  					//HSSFRow rowcount = sheet.createRow((short)r++);
			    				  					out.print(data1[l][0].toString());out.print(",");
			    				  					out.print(data1[l][1].toString());out.print(",");
			    				  					out.print(data1[l][2].toString());out.print(",");
			    				  					out.print(data1[l][3].toString());out.print(",");
			    				  					out.print(data1[l][4].toString());out.print(",");
			    				  					out.print(data1[l][5].toString());out.print(",");
			    				  					out.print(data1[l][6].toString());out.print(",");
			    				  					out.print(data1[l][7].toString());out.print(",");
			    				  					out.print(data1[l][8].toString());out.print(",");
			    				  					out.print(data1[l][9].toString());out.print(",");
			    				  					out.print(data1[l][10].toString());out.print(",");
			    				  					out.print(data1[l][11].toString());out.print(",");
			    					  				if(daybook.getUserChoice().equalsIgnoreCase("TrailBalance")){
			    					  					out.print(data1[l][12].toString());out.print(",");
			    					  					out.print(data1[l][13].toString());out.print(",");
			    					  				}
			    				  				}
			    				  				
			    				  				}
			    				  			/*FileOutputStream fileOut = new FileOutputStream("c:\\Reports\\daybook\\"+filename+".xls");
			    				  			wb.write(fileOut);
			    				  			fileOut.close();   
			    				  			req.setAttribute("msg","Successfully saved to file...! \n file path is c:\\Reports\\daybook\\"+filename+".xls");*/
			    				  			     }catch ( Exception ex ){  
			    				  			    	 ex.printStackTrace();
			    				  			     }     
			    				  				
			    							
			    				  			   return null;
			    						}
			    						
			    					}
			    					catch(Exception ex)
			    					{
			    						ex.printStackTrace();
			    					}

			    				  
			    				  //code for showing the recoreds ends
			    			  }
			    			  else
			    				  req.setAttribute("msg","No records found");
		    				 }catch(Exception e){
		    					 e.printStackTrace();
		    				 }
		    				 
		    				 
		    				 req.setAttribute("trialdaybook", daybook.getUserChoice());
	    					 daybook.setDate(daybook.getDate());
	    					 daybook.setUserChoice(daybook.getUserChoice());
	    					 path=MenuNameReader.getScreenProperties(daybook.getPageId());
	   		    		  gldelegate=new GLDelegate();
	   		    		  setGLOpeningInitParams(req, path, gldelegate);
	   		    		  System.out.println("the path from MenuNameReader is "+path);
	   		    		  req.setAttribute("pageId",path);
	   		    		req.setAttribute("Youcandownload", "Download");
    					req.setAttribute("showdwn", "false");
    					
			    		  }
			    		  
		    				
		    				
		    				
		    				//for viewing records end
		    				 if(daybook.getFlag().equalsIgnoreCase("from_radio")){
		    					 req.setAttribute("trialdaybook", daybook.getUserChoice());
		    					 daybook.setDate(daybook.getDate());
		    					 daybook.setUserChoice(daybook.getUserChoice());
		    					 path=MenuNameReader.getScreenProperties(daybook.getPageId());
		   		    		  gldelegate=new GLDelegate();
		   		    		  setGLOpeningInitParams(req, path, gldelegate);
		   		    		  System.out.println("the path from MenuNameReader is "+path);
		   		    		  req.setAttribute("pageId",path);
		   		    		req.setAttribute("Youcandownload", "Download");
	    					req.setAttribute("showdwn", "true");
		    				 }
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
		
	
		
		
		
		
		
		
		/*  * **************GL DayBook End********************* */
		
		
		
	
		
	
		
		
		
		
		
		
	
		
		
		
		
		
		/*  *************Gl Schedule End********************** */
		/*  *************Gl TrfScroll Printing Start********************** */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLTrfScrollPrintingMenu"))
		{
	      try{
	    	  TrfScrollPrinting scroll_printing=(TrfScrollPrinting)form;
	    	  req.setAttribute("msg","");
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+scroll_printing);
	    	  System.out.println("the page id is "+scroll_printing.getPageId());
	    	  req.setAttribute("pagenum", scroll_printing.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(scroll_printing.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(scroll_printing.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  scroll_printing.setFromDate(gldelegate.getSysDate());
	    		  scroll_printing.setToDate(gldelegate.getSysDate());
	    		  req.setAttribute("trfModuleObj",gldelegate.getMainModules());
	    		  req.setAttribute("trfModuleObj1",gldelegate.getMainModules1());
	    		  
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLTrfScrollPrinting")){
			try{
				TrfScrollPrinting scroll_printing=(TrfScrollPrinting)form;
				req.setAttribute("msg","");
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+scroll_printing);
		    	  System.out.println("the page id is "+scroll_printing.getPageId());
		    	  req.setAttribute("pagenum", scroll_printing.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(scroll_printing.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(scroll_printing.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+scroll_printing.getForward());
		    		  //focus lost of acctype start
		    		  if(scroll_printing.getFlag().equalsIgnoreCase("from_acctype")){
		                     
		                     ModuleObject lnmod[]=null,ldmod[]=null;
		                     //lnmod=gldelegate.getTrfMainModules();
			    			 //ldmod=gldelegate.getMainModules();
			                 if(scroll_printing.getTypes().equalsIgnoreCase("Loans")){
			                	 System.out.println("===========Loans++++++++++++");
			                	 req.setAttribute("trfModuleObj",gldelegate.getMainModules1());
			                 }else
			                 {
			                	 System.out.println("===========Loans On Deposit++++++++++++");
			                	 req.setAttribute("trfModuleObj",gldelegate.getTrfMainModules());
			                 }
			                  path=MenuNameReader.getScreenProperties(scroll_printing.getPageId());
				    		  gldelegate=new GLDelegate();
				    		  setGLOpeningInitParams(req, path, gldelegate);
				    		  scroll_printing.setFromDate(scroll_printing.getFromDate());
				    		  scroll_printing.setToDate(scroll_printing.getToDate());
				    		  scroll_printing.setTypes(scroll_printing.getTypes());
				    		  
				    		 
				    		  System.out.println("the path from MenuNameReader is "+path);
				    		  req.setAttribute("pageId",path);
				    		  req.setAttribute("comboStatusType","true");
				    		  req.setAttribute("comboStatusCode","true");
				    		  
				    		  req.setAttribute("trfModuleObj1",gldelegate.getMainModules1());
				    		  
				    		  
			                  return map.findForward(ResultHelp.getSuccess());
			    		  }
			        
		    		  
		    		  
		    		  //focus lost of acctype end
		    		  
		    		  //after focus lost of glcode start
		    		  if(scroll_printing.getFlag().equalsIgnoreCase("from_glcode")){
	                     String trfStringCode="";
	                     ModuleObject mod1[]=null,mod2[]=null;
	                     mod1=gldelegate.getMainModules1();
		    			 mod2=gldelegate.getTrfMainModules();
		                  if(scroll_printing.getCodes().equalsIgnoreCase("ALLCodes")){
		                	 
		                	  trfStringCode="";
		                	  
		                	  req.setAttribute("StringCode", trfStringCode); 
		                    
		                  }   
		                  else if(scroll_printing.getTypes().equalsIgnoreCase("Loans_on_deposit"))
		                  {
		                     
		                      for(int i=0;i<mod2.length;i++)
		                      {
		                          if(scroll_printing.getCodes().equals(mod2[i].getModuleAbbrv()))
		                          {
		                        	  System.out.println("value of mod obj"+mod1[i]);
		                        	  req.setAttribute("TrfStringCode", mod2[i].getModuleDesc());
				                	   
		                              
		                          }
		                      }
		                                         
		                      
		                  }else if(scroll_printing.getTypes().equalsIgnoreCase("Loans"))
		                  {
		                     
		                      for(int i=0;i<mod1.length;i++)
		                      {
		                          if(scroll_printing.getCodes().equals(mod1[i].getModuleAbbrv()))
		                          {
		                        	  System.out.println("value of mod obj"+mod1[i]);
		                        	  req.setAttribute("TrfStringCode", mod1[i].getModuleDesc());
				                	   
		                              
		                          }
		                      }
		                                         
		                      
		                  }
		                  path=MenuNameReader.getScreenProperties(scroll_printing.getPageId());
			    		  gldelegate=new GLDelegate();
			    		  setGLOpeningInitParams(req, path, gldelegate);
			    		  scroll_printing.setFromDate(scroll_printing.getFromDate());
			    		  scroll_printing.setToDate(scroll_printing.getToDate());
			    		  scroll_printing.setTypes(scroll_printing.getTypes());
			    		  scroll_printing.setCodes(scroll_printing.getCodes());
			    		 
			    		  System.out.println("the path from MenuNameReader is "+path);
			    		  req.setAttribute("pageId",path);
			    		  req.setAttribute("comboStatusType","true");
			    		  req.setAttribute("comboStatusCode","true");
			    		  req.setAttribute("trfModuleObj",gldelegate.getMainModules());
			    		  req.setAttribute("trfModuleObj1",gldelegate.getMainModules1());
			    		  
			    		  
		                  return map.findForward(ResultHelp.getSuccess());
		    		  }
		        
		    		  
		    		  //after focus lost of glcode end
//on click of clear button start
if(scroll_printing.getFlag().equalsIgnoreCase("clear")){
       
      
       
        path=MenuNameReader.getScreenProperties(scroll_printing.getPageId());
		  gldelegate=new GLDelegate();
		  setGLOpeningInitParams(req, path, gldelegate);
		  scroll_printing.setFromDate(gldelegate.getSysDate());
		  scroll_printing.setToDate(gldelegate.getSysDate());
		  scroll_printing.setTypes("All Types");
		  scroll_printing.setCodes("All Codes");
		 
		  System.out.println("the path from MenuNameReader is "+path);
		  req.setAttribute("pageId",path);
		  req.setAttribute("comboStatusType","true");
		  req.setAttribute("comboStatusCode","true");
		  
		  req.setAttribute("trfModuleObj1",gldelegate.getMainModules1());
		  
		  
        return map.findForward(ResultHelp.getSuccess());
	  }
//on click of clear button end
//on click of view button start
	if(scroll_printing.getFlag().equalsIgnoreCase("view")){
    TransferScroll trf_scroll[]=null;
    String string_qry=" ";
    ModuleObject modLn[]=null,modLd[]=null;
    modLn=gldelegate.getMainModules1();
    modLd=gldelegate.getTrfMainModules();
    String newStrCode="",actype="";
    //setting the actype starts
   if(scroll_printing.getTypes().equalsIgnoreCase("Loans")){
    String typename=scroll_printing.getCodes();
    if(typename!=null){
    	 for(int i=0;i<modLn.length;i++)
         {
             if(scroll_printing.getCodes().equals(modLn[i].getModuleAbbrv()))
             {
           	  System.out.println("value of mod obj"+modLn[i]);
           	  actype=modLn[i].getModuleCode();
           	  req.setAttribute("newstringcode", modLn[i].getModuleDesc());
                 
             }
         }
       
    }
    /*else{
        actype="1010%";
        req.setAttribute("newstringcode", "");
    }*/
   }
    
   
   if(scroll_printing.getTypes().equalsIgnoreCase("Loans_on_deposit")){
	    String typename=scroll_printing.getCodes();
	    if(typename!=null){
	    	 for(int i=0;i<modLd.length;i++)
	         {
	             if(scroll_printing.getCodes().equals(modLd[i].getModuleAbbrv()))
	             {
	           	  System.out.println("value of mod obj"+modLd[i]);
	           	  actype=modLd[i].getModuleCode();
	           	  req.setAttribute("newstringcode", modLd[i].getModuleDesc());
	                 
	             }
	         }
	       
	    }
	    /*else{
	        actype="1008%";
	        req.setAttribute("newstringcode", "");
	    }*/
	   }
    //setting the actype ends
    
    //view code start
   //resetting the value in the combo box of codes starts
   if(scroll_printing.getTypes().equalsIgnoreCase("Loans")){
  	 System.out.println("===========Loans++++++++++++");
  	 req.setAttribute("trfModuleObj",gldelegate.getMainModules1());
   }else
   {
  	 System.out.println("===========Loans On Deposit++++++++++++");
  	 req.setAttribute("trfModuleObj",gldelegate.getTrfMainModules());
   }
   //resetting the value in the combo box of codes ends
	try
	{
    System.out.println("from="+scroll_printing.getFromDate());
    System.out.println("from="+scroll_printing.getToDate());
    System.out.println("from="+scroll_printing.getCodes());
    System.out.println("==========================actype="+actype);
    trf_scroll=gldelegate.transferScrollPrint(scroll_printing.getFromDate().toString(),scroll_printing.getToDate().toString(),actype);
    System.out.println("TransferScroll Object++++++++++++Action+++++++++"+trf_scroll.length);
	}catch(Exception ae){ae.printStackTrace();}
	//showing the report using the transferscroll obj starts
	if(trf_scroll.length>0)
	{   
		int arrlength=(trf_scroll.length);
		System.out.println("before for loop aarray lenght=========="+arrlength);
		Object data[][] = new Object[arrlength][12];
	String trn_date;
	trn_date=new String(" ");
	int i=0;
	double total=0.0;
	if(trf_scroll!=null){
		for(i=0;i<trf_scroll.length;i++){
	        /*if(!(trn_date.equals(trf_scroll[i].getTranDate())))
	        {
	            data[i][0]=" ";data[i][1]=" ";data[i][2]=" ";data[i][3]=" ";data[i][4]=" ";data[i][5]=" ";
	            data[i][6]="Total";
	            data[i][7]="  "+String.valueOf(total);
	            data[i][8]=" ";data[i][9]=" ";data[i][10]=" ";data[i][11]=" ";
	            trn_date=trf_scroll[i].getTranDate();
	            total=0.0;
	            i++;
	            data[i][0]=" ";data[i][1]=" ";data[i][2]=" ";data[i][3]=" ";data[i][4]=" ";data[i][5]=" ";
	            data[i][6]=" ";data[i][7]=" ";data[i][8]=" ";data[i][9]=" ";data[i][10]=" ";data[i][11]=" ";
	            i++;
	            data[i][4]="Transfer ";data[i][5]="Scroll";data[i][6]="Printing";data[i][7]="for";
	            data[i][8]=" "+trf_scroll[i].getTranDate();
	            data[i][9]=" ";data[i][10]=" ";data[i][11]=" ";
	            i++;
	        }*/

	        data[i][0]=String.valueOf(trf_scroll[i].getRefNo() );
		    data[i][1]=String.valueOf(trf_scroll[i].getFromModuleabbr());
		    data[i][2]=String.valueOf(trf_scroll[i].getFromAccNo() );
		    data[i][3]=String.valueOf(trf_scroll[i].getFromName() );
		    data[i][4]=String.valueOf(trf_scroll[i].getToModuleabbr());
		    data[i][5]=String.valueOf(trf_scroll[i].getToAccNo() );
		    data[i][6]=String.valueOf(trf_scroll[i].getToName() );
		    data[i][7]=String.valueOf(trf_scroll[i].getTranAmt() );
		    data[i][8]=String.valueOf(trf_scroll[i].getDe_tml() );
		    data[i][9]=String.valueOf(trf_scroll[i].getDe_user() );
		    data[i][10]=String.valueOf(trf_scroll[i].getVe_tml());
		    data[i][11]=String.valueOf(trf_scroll[i].getVe_user() );
		    trn_date=String.valueOf(trf_scroll[i].getTranDate());
		    total=total+trf_scroll[i].getTranAmt();
		    
	    }
		/*int j=trf_scroll.length;
		i=j+1;
		System.out.println("out of for loop++++++++++++++"+i);
		
		data[i][0]=" ";data[i][1]=" ";data[i][2]=" ";data[i][3]=" ";data[i][4]=" ";data[i][5]=" ";
        data[i][6]="Total";
        data[i][7]="  "+String.valueOf(total);
        data[i][8]=" ";data[i][9]=" ";data[i][10]=" ";data[i][11]=" ";
        i++;
        data[i][0]=" ";data[i][1]=" ";data[i][2]=" ";data[i][3]=" ";data[i][4]=" ";data[i][5]=" ";
        data[i][6]=" ";data[i][7]=" ";data[i][8]=" ";data[i][9]=" ";data[i][10]=" ";data[i][11]=" ";
        */
		req.setAttribute("TrfScrollPrintingObj",data);
		req.setAttribute("grandtotal", String.valueOf(total));
	}
		
	}
	else
	{
		req.setAttribute("msg","No Records Found");
		
	}
	
	//showing the report using the transferscroll obj ends
	//view code end
    
    path=MenuNameReader.getScreenProperties(scroll_printing.getPageId());
	  gldelegate=new GLDelegate();
	  setGLOpeningInitParams(req, path, gldelegate);
	  scroll_printing.setFromDate(scroll_printing.getFromDate());
	  scroll_printing.setToDate(scroll_printing.getToDate());
	  scroll_printing.setTypes(scroll_printing.getTypes());
	  scroll_printing.setCodes(scroll_printing.getCodes());
	 
	 
	  System.out.println("the path from MenuNameReader is "+path);
	  req.setAttribute("pageId",path);
	  req.setAttribute("comboStatusType","true");
	  req.setAttribute("comboStatusCode","true");
	  
	  req.setAttribute("trfModuleObj1",gldelegate.getMainModules1());
	  
	  
    return map.findForward(ResultHelp.getSuccess());
  }
	
	if(scroll_printing.getFlag().equalsIgnoreCase("save")){
		TransferScroll trf_scroll[]=null;
	    String string_qry=" ";
	    ModuleObject modLn[]=null,modLd[]=null;
	    modLn=gldelegate.getMainModules1();
	    modLd=gldelegate.getTrfMainModules();
	    String newStrCode="",actype="";
		if(scroll_printing.getTypes().equalsIgnoreCase("Loans")){
		    String typename=scroll_printing.getCodes();
		    if(typename!=null){
		    	 for(int i=0;i<modLn.length;i++)
		         {
		             if(scroll_printing.getCodes().equals(modLn[i].getModuleAbbrv()))
		             {
		           	  System.out.println("value of mod obj"+modLn[i]);
		           	  actype=modLn[i].getModuleCode();
		           	  req.setAttribute("newstringcode", modLn[i].getModuleDesc());
		                 
		             }
		         }
		       
		    }
		    /*else{
		        actype="1010%";
		        req.setAttribute("newstringcode", "");
		    }*/
		   }
		if(scroll_printing.getTypes().equalsIgnoreCase("Loans_on_deposit")){
		    String typename=scroll_printing.getCodes();
		    if(typename!=null){
		    	 for(int i=0;i<modLd.length;i++)
		         {
		             if(scroll_printing.getCodes().equals(modLd[i].getModuleAbbrv()))
		             {
		           	  System.out.println("value of mod obj"+modLd[i]);
		           	  actype=modLd[i].getModuleCode();
		           	  req.setAttribute("newstringcode", modLd[i].getModuleDesc());
		                 
		             }
		         }
		       
		    }
		    /*else{
		        actype="1008%";
		        req.setAttribute("newstringcode", "");
		    }*/
		   }
		try
		{
	    System.out.println("from="+scroll_printing.getFromDate());
	    System.out.println("from="+scroll_printing.getToDate());
	    System.out.println("from="+scroll_printing.getCodes());
	    System.out.println("==========================actype="+actype);
	    trf_scroll=gldelegate.transferScrollPrint(scroll_printing.getFromDate().toString(),scroll_printing.getToDate().toString(),actype);
	    System.out.println("TransferScroll Object++++++++++++Action+++++++++"+trf_scroll.length);
		}catch(Exception ae){ae.printStackTrace();}
		
		 System.out.println("Inside save ");
		 //TO save to an excel Sheet
		 res.setContentType("application/.csv");
	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
	        
	        java.io.PrintWriter out = res.getWriter();
	        out.print("\n");
	        out.print("\n");
	        out.print("\n");
	        out.print(",");out.print(",");out.print(",");
	        out.print("Transfer Scroll Printing Details: ");
	        out.print("\n");
	        out.print("\n");
   		   /*HSSFCell cell = row.createCell((short)0);
   		   cell.setCellValue(1);*/
	       
   		out.print("Scroll No"); out.print(",");
   		out.print("Dr AcType"); out.print(",");
   		out.print("Dr AcNo"); out.print(",");
   		out.print("Name"); out.print(",");
   		out.print("Cr AcType"); out.print(",");
   		out.print("Cr AcNo"); out.print(",");
   		out.print("Name"); out.print(",");
   		out.print("Amount"); out.print(",");
   		out.print("Tml"); out.print(",");
   		out.print("User"); out.print(",");
   		out.print("Tml"); out.print(",");
   		
   		out.print("User"); out.print("\n");
   		
   		for(int k=0;k<trf_scroll.length;k++){
 			  
   			out.print(trf_scroll[k].getRefNo());
   			out.print(",");
   			out.print(trf_scroll[k].getFromModuleabbr());
   			out.print(",");
   			out.print(trf_scroll[k].getFromAccNo());
   			out.print(",");
   			out.print(trf_scroll[k].getFromName());
   			out.print(",");
   			out.print(trf_scroll[k].getToAccTypeAbbr());
   			out.print(",");
   			out.print(trf_scroll[k].getToAccNo());
   			out.print(",");
   			out.print(trf_scroll[k].getToName());
   			out.print(",");
   			out.print(trf_scroll[k].getTranAmt());
   			out.print(",");
   			out.print(trf_scroll[k].getDe_tml());
   			out.print(",");
   			out.print(trf_scroll[k].getDe_user());
   			out.print(",");
   			out.print(trf_scroll[k].getVe_tml());
   			out.print(",");
   			out.print(trf_scroll[k].getVe_user());
   			out.print(",");
   			out.print("\n");
   		}
   	 req.setAttribute("msg","Saved to excel file in C:");
	    return null;
		
		
	}


//on click of view button end



		    		  	    		  
		    		/* if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }*/
		    		  
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
		
	
		
	
		
		
		
		
		
		
		
		/*  *************Gl TrfScroll Printing End********************** */
		/*  *************Gl VoucherSlip Printing Start********************** */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLVoucherSlipPrintingMenu"))
		{
	      try{
	    	  VoucherSlipPrinting voucher_slip=(VoucherSlipPrinting)form;
	    	  req.setAttribute("msg","");
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+voucher_slip);
	    	  System.out.println("the page id is "+voucher_slip.getPageId());
	    	  req.setAttribute("pagenum", voucher_slip.getPageId());
	    	  
	    	  
	    	  if(MenuNameReader.containsKeyScreen(voucher_slip.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(voucher_slip.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  voucher_slip.setSlipDate(gldelegate.getSysDate());
	    		  req.setAttribute("moduleobjvoucher",gldelegate.getMainMods());
	    		  req.setAttribute("codetypevoucher",gldelegate.getCodeTypesForVoucher(voucher_slip.getSlipDate()));
	    		  
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLVoucherSlipPrinting")){
			try{
				GLObject[] array_globject;
				VoucherSlipPrinting voucher_slip=(VoucherSlipPrinting)form;
				req.setAttribute("msg","");
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+voucher_slip);
		    	  System.out.println("the page id is "+voucher_slip.getPageId());
		    	  req.setAttribute("pagenum", voucher_slip.getPageId());
		    	  
		    	  String glType=null;
		    	  String[][] codetypes=null;
		    	  ModuleObject[] mainMods=null;
		    	  String[][] voucherSlipDate=null;
		    	  String glAbbr=null;
		    	  String glDesc=null;
		    	  String rsw=null;
		    	   NumFormat conv = new NumFormat();
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(voucher_slip.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(voucher_slip.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  codetypes=gldelegate.getCodeTypesForVoucher(voucher_slip.getSlipDate());
		    		  mainMods=gldelegate.getMainMods();
		    		  req.setAttribute("moduleobjvoucher",mainMods);
		    		  req.setAttribute("codetypevoucher",codetypes);
		    		  for(int i=0;i<mainMods.length;i++){
		    			  if(mainMods[i].getModuleAbbrv().equalsIgnoreCase(voucher_slip.getGlTypes())){
		    				  glType=mainMods[i].getModuleCode();
		    			  }
		    			  
		    		  }
		    		  
		    		  
		    		  System.out.println("The button valuee is"+voucher_slip.getForward());
		    		  //code for view starts here
		    		  if(voucher_slip.getFlag().equalsIgnoreCase("view")){
		    			  try{
		    					if(voucher_slip.getRadioType().equalsIgnoreCase("credit")){
		    						array_globject=gldelegate.getVoucherSlip(Validations.convertYMD(voucher_slip.getSlipDate()),glType,Integer.parseInt(voucher_slip.getFromGlNo()),Integer.parseInt(voucher_slip.getToGlNo()),1);
		    					}
		    					else{
		    						array_globject=gldelegate.getVoucherSlip(Validations.convertYMD(voucher_slip.getSlipDate()),glType,Integer.parseInt(voucher_slip.getFromGlNo()),Integer.parseInt(voucher_slip.getToGlNo()),0);
		    					}
		    			if(array_globject==null){	
		    				
		    					req.setAttribute("NoVoucher", "NoVoucher");
		    				
		    			//System.out.println("Len***"+array_globject.length);
		    			}else{
		    				System.out.println("Len***"+array_globject.length);
		    				voucherSlipDate=new String[array_globject.length][9];
		    				for(int i=0;i<array_globject.length;i++){
		    					voucherSlipDate[i][0]=array_globject[i].getBank_name();
		    					voucherSlipDate[i][1]=array_globject[i].getAddr();
		    					if(voucher_slip.getRadioType().equalsIgnoreCase("credit")){
		    						voucherSlipDate[i][2]="Credit Vouching Slip";
		    					}else{
		    						voucherSlipDate[i][2]="Debit Vouching Slip";
		    					}
		    					voucherSlipDate[i][3]=String.valueOf(array_globject[i].getCode());
		    					voucherSlipDate[i][4]=array_globject[i].getTrn_date();
		    					glAbbr=String.valueOf(array_globject[i].getCode());
		    					for(int k=0;k<codetypes.length;k++){
		    					if(codetypes[k][1].equalsIgnoreCase(glAbbr)){
		    						glDesc=String.valueOf(codetypes[k][2]);
		    					}
		    					}
		    					
		    					voucherSlipDate[i][5]=glDesc;
		    					if(array_globject[i].getTrnType().equals("C")){
		    						voucherSlipDate[i][6]="Cash";
		    						
		    					}
		    					else if(array_globject[i].getTrnType().equals("G")){
		    						voucherSlipDate[i][6]="Clearing";
		    						
		    					}
		    					else if(array_globject[i].getTrnType().equals("T")){
		    						voucherSlipDate[i][6]="Transfer";
		    						
		    					}
		    					//Amzad starts
		    					if(voucher_slip.getRadioType().equalsIgnoreCase("credit") & array_globject[i].getTrnType().equals("C")){
		    						
		    						
		    						
		    						voucherSlipDate[i][7]=String.valueOf(array_globject[i].getCash_cr());
		    						
		    						
		    						rsw=conv.Convert(array_globject[i].getCash_cr());
		    						voucherSlipDate[i][8]=String.valueOf(rsw);
		    						
		    					}
		    					else if(voucher_slip.getRadioType().equalsIgnoreCase("credit") & array_globject[i].getTrnType().equals("G")){
		    						
		    						voucherSlipDate[i][7]=String.valueOf(array_globject[i].getCg_cr());
		    						
		    						
		    						
		    						rsw=conv.Convert(array_globject[i].getCg_cr());
		    						voucherSlipDate[i][8]=String.valueOf(rsw);
		    					}
		    					else if(voucher_slip.getRadioType().equalsIgnoreCase("credit") & array_globject[i].getTrnType().equals("T")){
		    						
		    						voucherSlipDate[i][7]=String.valueOf(array_globject[i].getTrf_cr());
		    						
		    						
		    						rsw=conv.Convert(array_globject[i].getTrf_cr());
		    						voucherSlipDate[i][8]=String.valueOf(rsw);
		    					}
		    					else if(voucher_slip.getRadioType().equalsIgnoreCase("debit") & array_globject[i].getTrnType().equals("C")){
		    						
		    						voucherSlipDate[i][7]=String.valueOf(array_globject[i].getCash_dr());
		    						
		    						rsw=conv.Convert(array_globject[i].getCash_dr());
		    						voucherSlipDate[i][8]=String.valueOf(rsw);
		    					}
		    					else if(voucher_slip.getRadioType().equalsIgnoreCase("debit") & array_globject[i].getTrnType().equals("G")){
		    						
		    						voucherSlipDate[i][7]=String.valueOf(array_globject[i].getCg_dr());
		    						
		    						rsw=conv.Convert(array_globject[i].getCg_dr());
		    						voucherSlipDate[i][8]=String.valueOf(rsw);
		    					}
		    					else if(voucher_slip.getRadioType().equalsIgnoreCase("debit") & array_globject[i].getTrnType().equals("T")){
		    						
		    						voucherSlipDate[i][7]=String.valueOf(array_globject[i].getTrf_dr());
		    						
		    						rsw=conv.Convert(array_globject[i].getTrf_dr());
		    						voucherSlipDate[i][8]=String.valueOf(rsw);
		    					}
		    					//Amzad ends
		    					req.setAttribute("ShowVoucher", "ShowVoucher");
		    					req.setAttribute("VouchingSlipPrinting", voucherSlipDate);
		    				}
		    				System.out.println("GL Abbrevation is"+glDesc);
		    			}
		    			
		    					
		    					//showForm();
		    					
		    					
		    					
		    				}catch(RecordsNotFoundException e){e.printStackTrace();
		    				req.setAttribute("msg","Records Not Found");
		    				}
		    				catch(Exception e){e.printStackTrace();}
		    			 
		    		  }
		    		  //code for view ends here
		    		  
		    		  
		    		  
		    		/* if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }*/
		    		  
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
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		/*  *************Gl VoucherSlip Printing end********************** */
		/*  *************Gl Monthly Gl Schedule Start********************** */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyGlScheduleMenu"))
		{   String yr,mnth,yearmnth;
	      try{
	    	  MonthlyGlSchedule monthly_schedule=(MonthlyGlSchedule)form;
	    	  req.setAttribute("msg","");
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+monthly_schedule);
	    	  System.out.println("the page id is "+monthly_schedule.getPageId());
	    	  req.setAttribute("pagenum", monthly_schedule.getPageId());
	    	  monthly_schedule.setYear("2009");
		      monthly_schedule.setMonth("01");
	    	  yr=String.valueOf(monthly_schedule.getYear());
	    	  mnth=String.valueOf(monthly_schedule.getMonth());
	    	  yearmnth=yr.concat(mnth);
	    	  System.out.println("Year Month="+yearmnth);
	    	  //monthly_schedule.setYear(String.valueOf(gldelegate.getCurrYear()));
	    	 // monthly_schedule.setMonth(String.valueOf(gldelegate.getCurrMonth()));
	    	 // System.out.println("Monty Gl No"+gldelegate.getGlDetForMonth((monthly_schedule.getYear().trim()+monthly_schedule.getMonth().trim())));
	    	  //System.out.println("Montyly gl no------------->>>>>>>>>>>"+gldelegate.getGlDetForMonth(Integer.parseInt(yearmnth)));
	    	  System.out.println("Year Month=1947==============================="+Integer.parseInt(yearmnth));
	    	 
	    	  //req.setAttribute("MonthlyGlNo",gldelegate.getCodeTypes("16/01/1980", "16/01/2009"));
	    	  
	    	  if(MenuNameReader.containsKeyScreen(monthly_schedule.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(monthly_schedule.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  req.setAttribute("moduleobj",gldelegate.getMainMods());
	    		  monthly_schedule.setYear("2009");
			      monthly_schedule.setMonth("01");
		    	  yr=String.valueOf(monthly_schedule.getYear());
		    	  mnth=String.valueOf(monthly_schedule.getMonth());
		    	  yearmnth=yr.concat(mnth);
	    		  String[][] arr=gldelegate.getGlDetForMonth(yearmnth);
		    	  System.out.println("*****************************1949");
		    	  if(arr!=null){
		    		  System.out.println("sending to delegate");
		    	  req.setAttribute("MonthlyGlNo", gldelegate.getGlDetForMonth(yearmnth));
		    	  System.out.println("receive from delegate");
		    	  }
			       
	    		  
	    		  //req.setAttribute("codetype",gldelegate.getCodeTypes(schedule.getFromDate(), schedule.getToDate()));
	    		 
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyGlSchedule")){
			try{
				MonthlyGlSchedule monthly_schedule=(MonthlyGlSchedule)form;
				req.setAttribute("msg","");
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+monthly_schedule);
		    	  System.out.println("the page id is "+monthly_schedule.getPageId());
		    	  req.setAttribute("pagenum", monthly_schedule.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(monthly_schedule.getPageId()))
		    	  {
		    		  System.out.println("====else if if==========of mglsch");
		    		  path=MenuNameReader.getScreenProperties(monthly_schedule.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+monthly_schedule.getForward());
		    		  
		    		  //code for onblur of toglno and clear start
if(monthly_schedule.getFlag().equalsIgnoreCase("to_glno")){

	                      String[][] codetype=null;
	                      codetype=gldelegate.getGlDetForMonth("200901");
		                  if(monthly_schedule.getCodes().equalsIgnoreCase("ALL_Codes")){
		                	  strType="";
		                	  strCode="";
		                	  req.setAttribute("StringCode", strType);
		                	  req.setAttribute("StringCode", strCode); 
		                    
		                  }   
		                  else
		                  {
		                     
		                      for(int i=0;i<gldelegate.getCodeTypes("01/01/1996", "01/01/2000").length;i++)
		                      {
		                          if(monthly_schedule.getCodes().equals(codetype[i][1]))
		                          {
		                        	  req.setAttribute("StringType", codetype[i][2]);
				                	   
		                              
		                          }
		                      }
		                      
		                      for(int i=0;i<gldelegate.getCodeTypes("01/01/1996", "01/01/2000").length;i++)
		                      {
		                          if(monthly_schedule.getToCodes().equals(codetype[i][1]))
		                          {
		                        	  
				                	  req.setAttribute("StringCode", codetype[i][2]); 
		                              
		                          }
		                      }
		                      
		                      
		                      
		                  }
		                  path=MenuNameReader.getScreenProperties(monthly_schedule.getPageId());
			    		  gldelegate=new GLDelegate();
			    		  setGLOpeningInitParams(req, path, gldelegate);
			    		  monthly_schedule.setYear(monthly_schedule.getYear());
			    		  monthly_schedule.setMonth(monthly_schedule.getMonth());
			    		  monthly_schedule.setTypes(monthly_schedule.getTypes());
			    		  monthly_schedule.setCodes(monthly_schedule.getCodes());
			    		  monthly_schedule.setToTypes(monthly_schedule.getToTypes());
			    		  monthly_schedule.setToCodes(monthly_schedule.getToCodes());
			    		  
			    		  System.out.println("the path from MenuNameReader is "+path);
			    		  req.setAttribute("pageId",path);
			    		  req.setAttribute("comboStatusType","true");
			    		  req.setAttribute("comboStatusCode","true");
			    		  
			    		  req.setAttribute("moduleobj",gldelegate.getMainMods());
			    		  String yearmnth="200901";
			    		  req.setAttribute("MonthlyGlNo", gldelegate.getGlDetForMonth(yearmnth));
			    		 
		                  return map.findForward(ResultHelp.getSuccess());
		    		  }
		              
		    		  
		    		  
		    		     		  
		    		  
		    		  
		    		  
		    		  
		    		  //focus lost of to glno end
//clear fun start
if(monthly_schedule.getFlag().equalsIgnoreCase("clear")){
	  
    
    
        
        
   
    path=MenuNameReader.getScreenProperties(monthly_schedule.getPageId());
	  gldelegate=new GLDelegate();
	  setGLOpeningInitParams(req, path, gldelegate);
	  monthly_schedule.setYear("2009");
	  monthly_schedule.setMonth("01");
	 
	  monthly_schedule.setTypes("All Types");
	  monthly_schedule.setCodes("All Codes");
	  monthly_schedule.setToTypes("All Types");
	  monthly_schedule.setToCodes("All Codes");
	  
	  System.out.println("the path from MenuNameReader is "+path);
	  req.setAttribute("pageId",path);
	  req.setAttribute("comboStatusType","true");
	  req.setAttribute("comboStatusCode","true");
	  
	 
	  req.setAttribute("moduleobj",gldelegate.getMainMods());
	  String yearmnth="200901";
	  req.setAttribute("MonthlyGlNo", gldelegate.getGlDetForMonth("200901"));
	 
	 
    return map.findForward(ResultHelp.getSuccess());
}

if(monthly_schedule.getFlag().equalsIgnoreCase("view") || monthly_schedule.getFlag().equalsIgnoreCase("file")){
	//code for view starts
	
	GLReportObject mthObject[]=null;
	ModuleObject[] modObj=gldelegate.getMainMods();
	
	String type_itemvalue,code_itemcount,code_to_itemcount;
    String string_qry=" ";
    int from_glno=0,to_glno=0;
    String from_gltype="",to_gltype="";
   
	if(!(monthly_schedule.getCodes().toString() .equalsIgnoreCase("ALL Codes")) && (!(monthly_schedule.getTypes().toString() .toString() .equalsIgnoreCase("ALL Types"))))
    {
        from_glno=Integer.parseInt(monthly_schedule.getCodes().toString());
        to_glno=Integer.parseInt(monthly_schedule.getToCodes().toString());
        if(modObj!=null){
        	for(int i=0;i<modObj.length;i++){
        		if(monthly_schedule.getTypes().equalsIgnoreCase(modObj[i].getModuleAbbrv())){
        			from_gltype=modObj[i].getModuleCode();
        		}
        		if(monthly_schedule.getToTypes().equalsIgnoreCase(modObj[i].getModuleAbbrv())){
        			to_gltype=modObj[i].getModuleCode();
        		}
        	}
        	
        }
        
     }
	int ret=-10;
	try{
		String month=null;
		  
		  if(Integer.parseInt(monthly_schedule.getMonth())<9){
			  month="0"+monthly_schedule.getMonth();
		  }
		  else{
			  month=monthly_schedule.getMonth();
		  }
		String fromdate="01"+"/"+month.toString()+"/"+monthly_schedule.getYear().toString();
		ret=gldelegate.checkMonthlyStatusReport(fromdate,Validations.lastDayOfMonth(fromdate));
		if(ret==0){
			string_qry=" ";
			//show report starts
			try
			{
			
			 mthObject=gldelegate.glMthScheduleDetails(monthly_schedule.getMonth().toString(),monthly_schedule.getYear().toString(),from_glno,from_gltype,to_glno,to_gltype,string_qry);    
			}catch(Exception ae){ae.printStackTrace();}

		if(mthObject.length>0)
		{
			System.out.println("Length.........."+mthObject.length);
			//show report of mthObject starts
			String[][] report=null;
			Object data1[][]=null;
			
			int temp_glcode=0,temp_glcode1=0;
			String temp_gltype="",temp_date="";
			
			double dr_total=0.0,cr_total=0.0,total=0.0;
			int j,i=0,k=0,headings=1;
			double amt=0.0,closing_bal=0.0,opening_bal=0.0;;
			String str_temp=new String();
			if(mthObject !=null){
				for( i=0;i<mthObject.length;i++){
				    if(i==0)
				        temp_glcode1=mthObject[i].getGLCode();
					if(i!=0 && mthObject[i].getGLCode()!=temp_glcode1)
					{
					 headings++;
					 temp_glcode1=mthObject[i].getGLCode();
					}
				}
				
			}
			Object data[][] = new Object[mthObject.length][7];
			i=0;
			report=new String [mthObject.length +(headings*3)][14];
			
			if(mthObject !=null)
				{
				System.out.println("Length1="+mthObject.length);
				
				for(i=0;i<mthObject.length;i++)
				{
				if(i==0)
				{
					//System.out.println("when i value is zero_____________"+i);
					/*System.out.println("value of i is ==="+i);
					System.out.println("value of String.valueOf(mthObject[i].getGLAbbr() is ==="+String.valueOf(mthObject[i].getGLAbbr()));
					System.out.println("value of String.valueOf(mthObject[i].getGLCode() is ==="+String.valueOf(mthObject[i].getGLCode()));
					System.out.println("value of String.valueOf(mthObject[i].getGLName() is ==="+String.valueOf(mthObject[i].getGLName()));
					System.out.println("value of String.valueOf(openbal[i].getGLName() is ==="+DoubleFormat.doubleToString(Math.abs(mthObject[i].getOpeningBalance()),2));*/
					
				data[i][0]=" "+String.valueOf(mthObject[i].getGLAbbr());
				data[i][1]=" "+String.valueOf(mthObject[i].getGLCode());
				temp_glcode=mthObject[i].getGLCode();
				temp_gltype=mthObject[i].getGLType();
				data[i][2]=" "+String.valueOf(mthObject[i].getGLName());
				data[i][3]="  ";
				data[i][4]="  ";
				data[i][5]="O /B";
				total=mthObject[i].getOpeningBalance();
				closing_bal=mthObject[i].getClosingBalance();
				System.out.println("open_bal"+mthObject[i].getOpeningBalance());
				System.out.println(" opne balance *************"+mthObject[0].getOpeningBalance());
				if(amt<0.0)
				data[i][6]="  "+DoubleFormat.doubleToString(Math.abs(mthObject[i].getOpeningBalance()),2)+"Dr";
				else
				data[i][6]="  "+DoubleFormat.doubleToString(mthObject[i].getOpeningBalance(),2)+"Cr";
				
				i++;
				report[k][0]=data[0].toString() ;report[k][1]=data[1].toString();report[k][2]=data[2].toString();report[k][3]=data[3].toString();
				report[k][4]=data[4].toString();report[k][5]=data[5].toString();report[k][6]=data[6].toString();
				k++;
				}
				if((temp_glcode==mthObject[i].getGLCode()) && (temp_gltype.equals(mthObject[i].getGLType())))
		        {
		            //System.out.println("inside if condition of the loop____________"+i);  
		            data[i][0]=" ";data[i][1]=" ";data[i][2]=" "+String.valueOf(mthObject[i].getGLName());
		            
		            data[i][3]=mthObject[i].getDate();
		            
		            if(mthObject[i].getBalance()<0.0)
		    		{
		    		
		    		dr_total=dr_total+mthObject[i].getBalance();
		    		data[i][4]=" "+DoubleFormat.doubleToString(Math.abs(mthObject[i].getBalance()),2);
		    		data[i][5]=" ";
		    		}
		    		else
		    		{
		    		data[i][4]=" ";
		    		
		    		cr_total+=mthObject[i].getBalance();
		    		data[i][5]=" "+DoubleFormat.doubleToString(mthObject[i].getBalance(),2);
		    		}
		    		
		            if(mthObject[i].getClosingBalance()<0.0)
		            data[i][6]=" "+DoubleFormat.doubleToString(Math.abs(mthObject[i].getClosingBalance()),2)+"Dr";
		            else
		            data[i][6]=" "+DoubleFormat.doubleToString(mthObject[i].getClosingBalance(),2)+"Cr";
		            
		    		
		    		report[k][0]=String .valueOf(mthObject[i].getGLCode());
		            report[k][1]=mthObject[i].getGLType();
		            report[k][2]=mthObject[i].getDate();
		            report[k][3]=data[4].toString() ;
		            report[k][4]=data[5].toString();
		            report[k][5]=data[6].toString() ;
		            k++;
		    		
		    	}
		        else
		        {
		        // System.out.println("inside else of the loop___________"+i);   
		            temp_glcode=mthObject[i].getGLCode();
		            temp_gltype=mthObject[i].getGLType() ;
		            temp_date=mthObject[i].getDate();
		           
		            data[i][0]=" ";data[i][1]=" ";data[i][2]=" ";
		            data[i][3]="Sub Total";
		            data[i][4]=" "+DoubleFormat.doubleToString(Math.abs(dr_total),2);
		            data[i][5]=" "+DoubleFormat.doubleToString(cr_total,2);
		            data[i][6]=" ";
		            //i++;
		            
		            
		            report[k][0]=String.valueOf(mthObject[i].getGLCode());
		            report[k][1]=mthObject[i].getGLType();
		            report[k][2]=mthObject[i].getDate();
		            report[k][3]=data[4].toString();
		            report[k][4]=data[5].toString();
		            report[k][5]=data[6].toString();
		            k++;
		            
		            
		            dr_total=0.0;
		            cr_total=0.0;
		            total=0.0;
		            
		            data[i][0]=" "+String.valueOf(mthObject[i].getGLAbbr());//gltype
		    		data[i][1]=" "+String.valueOf(mthObject[i].getGLCode());
		    		data[i][2]=" "+String.valueOf(mthObject[i].getGLName());
		    		data[i][3]="  ";
		    		data[i][4]="  ";
		    		data[i][5]="O/B ";
		    		opening_bal=mthObject[i].getOpeningBalance();
		    		System.out.println("open="+mthObject[i].getOpeningBalance());
		    		total=opening_bal;
		    		if(opening_bal<0.0)
		    			data[i][6]="  "+DoubleFormat.doubleToString(Math.abs(opening_bal),2)+"Dr";
		    			else
		    			data[i][6]="  "+DoubleFormat.doubleToString(opening_bal,2)+"Cr";
		    			
		           //dtm.addRow(data);
		    		i++;
		            report[k][0]=String.valueOf(mthObject[i].getGLCode());
		            report[k][1]=mthObject[i].getGLType();
		            report[k][2]=mthObject[i].getDate();
		            report[k][3]="";
		            report[k][4]="";
		            report[k][5]=data[6].toString();
		            k++;
		            
		           data[i][0]=" ";data[i][1]=" ";data[i][2]=" ";
		           data[i][3]=" "+String.valueOf(mthObject[i].getDate());
		            if(mthObject[i].getBalance()<0.0)
		    		{
		    		total=total+Math.abs(mthObject[i].getBalance());
		    		dr_total=dr_total+mthObject[i].getBalance();
		    		data[i][4]=" "+DoubleFormat.doubleToString(Math.abs(mthObject[i].getBalance()),2);
		    		data[i][5]=" ";
		    		}
		    		else
		    		{
		    		data[i][4]=" ";
		    		total=total+mthObject[i].getBalance();
		    		cr_total=cr_total+mthObject[i].getBalance();
		    		data[i][5]=" "+DoubleFormat.doubleToString(mthObject[i].getBalance(),2);
		    		}
		    		
		    		if(mthObject[i].getClosingBalance()<0.0)
			        data[i][6]=" "+DoubleFormat.doubleToString(Math.abs(mthObject[i].getClosingBalance()),2)+"Dr";
			        else
			        data[i][6]=" "+DoubleFormat.doubleToString(mthObject[i].getClosingBalance(),2)+"Cr";
		    		
			            
		    		//dtm.addRow(data);
		    		
		    		report[k][0]=String.valueOf(mthObject[i].getGLCode());
		            report[k][1]=mthObject[i].getGLType();
		            report[k][2]=mthObject[i].getDate();
		            report[k][3]=data[4].toString();
		            report[k][4]=data[5].toString();
		            report[k][5]=data[6].toString();
		            k++;
		        }
				}
				data1=new Object[1][7];
				data1[0][0]=" ";data1[0][1]=" ";data1[0][2]="Closing Bal";data1[0][3]="Sub Total";
				data1[0][4]=" "+DoubleFormat.doubleToString(Math.abs(dr_total),2);
	            data1[0][5]=" "+DoubleFormat.doubleToString(cr_total,2);
	            data1[0][6]=" ";
	           
	            //dtm.addRow(data);
	            
	            report[k][0]=String.valueOf(mthObject[i-1].getGLCode());
	            report[k][1]=mthObject[i-1].getGLType();
	            report[k][2]=mthObject[i-1].getDate();
	            report[k][3]=" ";
	            report[k][4]=" ";
	            report[k][5]=data[6].toString();
	            k++;
	            
	           
				}
			/*for(int a=0;a<data.length;a++){
				System.out.println("--->"+data[a][3]);
			}*/
			req.setAttribute("monthlyglschObj", data);
			req.setAttribute("monthlyglschObj1", data1);
			/*for(int a=0;i<report.length;a++){
				for(int b=0;b<14;b++){
					System.out.println("--------"+report[a][b]);
				}
				System.out.println("______________________");
				
			}*/
			req.setAttribute("monthlyglschReport", report);
			
			
			//show report of mthObject ends
			if(monthly_schedule.getFlag().equalsIgnoreCase("file")){
				String filename=req.getParameter("fname");
  				try{
  				HSSFWorkbook wb= new HSSFWorkbook();
  				HSSFSheet sheet = wb.createSheet("GLSchedule");
  				sheet.setColumnWidth((short)0, (short)1000);
  				sheet.setColumnWidth((short)1, (short)1000);
  				sheet.setColumnWidth((short)2, (short)1000);
  				sheet.setColumnWidth((short)3, (short)5000);
  				sheet.setColumnWidth((short)4, (short)5000);
  				sheet.setColumnWidth((short)5, (short)5000);
  				sheet.setColumnWidth((short)6, (short)5000);
  				sheet.setColumnWidth((short)7, (short)5000);
  				sheet.setColumnWidth((short)8, (short)5000);
  				sheet.setColumnWidth((short)9, (short)5000);
  				
  				HSSFFont font = wb.createFont();
  	            font.setColor(HSSFFont.COLOR_RED);
  	            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
  	            
  	          HSSFCellStyle cellStyle= wb.createCellStyle();
              cellStyle.setFont(font);
  				HSSFRow row = sheet.createRow((short)2);
	  			HSSFCell cell=row.createCell((short)5);   
	  			cell.setCellStyle(cellStyle);
	            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell.setCellValue("");
               	HSSFRow row1 = sheet.createRow((short)3);
  			   	HSSFCell cell1=row1.createCell((short)5);
  				cell1.setCellStyle(cellStyle);
	            cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell1.setCellValue("Monthly GLSchedule For "+Validations.lastDayOfMonth(fromdate));
                HSSFRow row2 = sheet.createRow((short)5);
  			    HSSFCell cell2=row2.createCell((short)4);
  			    cell2.setCellStyle(cellStyle);
                cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell2.setCellValue("Account Details");
                HSSFCell cell3=row2.createCell((short)6);
  			    cell3.setCellStyle(cellStyle);
                cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell3.setCellValue("Transaction");
                HSSFCell cell9=row2.createCell((short)7);
  			    cell9.setCellStyle(cellStyle);
                cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell9.setCellValue("Amount");
                HSSFCell cell10=row2.createCell((short)9);
  			    cell10.setCellStyle(cellStyle);
                cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell10.setCellValue("Balance");
                HSSFRow row3 = sheet.createRow((short)6);
	  			HSSFCell cell5=row3.createCell((short)3);
	  			cell5.setCellStyle(cellStyle);
	            cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell5.setCellValue("GLType");
	            HSSFCell cell6=row3.createCell((short)4);
	  			cell6.setCellStyle(cellStyle);
	            cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell6.setCellValue("GLCode");
	            HSSFCell cell7=row3.createCell((short)5);
	  			cell7.setCellStyle(cellStyle);
	            cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell7.setCellValue("GLName");
	                 
	            
	  			HSSFCell cell15=row3.createCell((short)6);
	  			cell15.setCellStyle(cellStyle);
	            cell15.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell15.setCellValue("Date");
	            HSSFCell cell11=row3.createCell((short)7);
	  			cell11.setCellStyle(cellStyle);
	            cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell11.setCellValue("Debit");
	            HSSFCell cell12=row3.createCell((short)8);
	  			cell12.setCellStyle(cellStyle);
	            cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell12.setCellValue("Credit");
	            HSSFCell cell13=row3.createCell((short)9);
	  			cell13.setCellStyle(cellStyle);
	            cell13.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell13.setCellValue("Balance"); 
	            int r=7;		  
	  			if(data!=null && data.length!=0){
	  				for(int m=0;m<data.length;m++){
	  					HSSFRow rowcount = sheet.createRow((short)r++);
	  				   
	  				   rowcount.createCell((short)3).setCellValue(data[m][0].toString());
	  				   rowcount.createCell((short)4).setCellValue(data[m][1].toString());
	  				   rowcount.createCell((short)5).setCellValue(data[m][2].toString());
	  				   rowcount.createCell((short)6).setCellValue(data[m][3].toString());
	  				   rowcount.createCell((short)7).setCellValue(data[m][4].toString());
	  				   rowcount.createCell((short)8).setCellValue(data[m][5].toString());
	  				   rowcount.createCell((short)9).setCellValue(data[m][6].toString());
	  				 }
	  				for(int l=0;l<data1.length;l++){
	  					HSSFRow rowcount = sheet.createRow((short)r++);
	  					rowcount.createCell((short)3).setCellValue(data1[l][0].toString());
		  				   rowcount.createCell((short)4).setCellValue(data1[l][1].toString());
		  				   rowcount.createCell((short)5).setCellValue(data1[l][2].toString());
		  				   rowcount.createCell((short)6).setCellValue(data1[l][3].toString());
		  				   rowcount.createCell((short)7).setCellValue(data1[l][4].toString());
		  				   rowcount.createCell((short)8).setCellValue(data1[l][5].toString());
		  				   rowcount.createCell((short)9).setCellValue(data1[l][6].toString());
	  				}
	  				
	  				
	  				}
	  			FileOutputStream fileOut = new FileOutputStream("c:\\Reports\\GLSchedule\\"+filename+".xls");
	  			wb.write(fileOut);
	  			fileOut.close();   
	  			req.setAttribute("msg","Successfully saved to file....!\n file path is c:\\Reports\\GLSchedule\\"+filename+".xls");
	  			     }catch ( Exception ex ){       
	  			     }     
	  		
				
			}
			
			
		}
		else
		{
			req.setAttribute("msg","No Records Found");
		
		}

			
			//show report ends
		}
		else{
			req.setAttribute("msg","Sorry Posting Not Done!..");
			
		}
	}catch(Exception ex){ex.printStackTrace();}

	
	
	
	//code for view ends
	
	
	
	
	
	String[][] codetype=null;
    codetype=gldelegate.getGlDetForMonth("200901");
    if(monthly_schedule.getCodes().equalsIgnoreCase("ALL_Codes")){
  	  strType="";
  	  strCode="";
  	  req.setAttribute("StringCode", strType);
  	  req.setAttribute("StringCode", strCode); 
      
    }   
    else
    {
       
        for(int i=0;i<gldelegate.getCodeTypes("01/01/1996", "01/01/2000").length;i++)
        {
            if(monthly_schedule.getCodes().equals(codetype[i][1]))
            {
          	  req.setAttribute("StringType", codetype[i][2]);
          	   
                
            }
        }
        
        for(int i=0;i<gldelegate.getCodeTypes("01/01/1996", "01/01/2000").length;i++)
        {
            if(monthly_schedule.getToCodes().equals(codetype[i][1]))
            {
          	  
          	  req.setAttribute("StringCode", codetype[i][2]); 
                
            }
        }
        
        
        
    }
    path=MenuNameReader.getScreenProperties(monthly_schedule.getPageId());
	  gldelegate=new GLDelegate();
	  setGLOpeningInitParams(req, path, gldelegate);
	  monthly_schedule.setYear(monthly_schedule.getYear());
	  monthly_schedule.setMonth(monthly_schedule.getMonth());
	  monthly_schedule.setTypes(monthly_schedule.getTypes());
	  monthly_schedule.setCodes(monthly_schedule.getCodes());
	  monthly_schedule.setToTypes(monthly_schedule.getToTypes());
	  monthly_schedule.setToCodes(monthly_schedule.getToCodes());
	  
	  System.out.println("the path from MenuNameReader is "+path);
	  req.setAttribute("pageId",path);
	  req.setAttribute("comboStatusType","true");
	  req.setAttribute("comboStatusCode","true");
	  
	  req.setAttribute("moduleobj",gldelegate.getMainMods());
	  String yearmnth="200901";
	  req.setAttribute("MonthlyGlNo", gldelegate.getGlDetForMonth(yearmnth));
	 
    return map.findForward(ResultHelp.getSuccess());
	
}
		if(monthly_schedule.getFlag().equalsIgnoreCase("save"))
		{
			 System.out.println("Inside save ");
    		 //TO save to an excel Sheet
    		 res.setContentType("application/.csv");
    	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
    	        
    	        java.io.PrintWriter out = res.getWriter();
    	        out.print("\n");
    	        out.print("\n");
    	        out.print("\n");
    	        out.print(",");out.print(",");out.print(",");
    	        out.print("Monthly GL Schedule Details: ");
    	        out.print("\n");
    	        out.print("\n");
       		   /*HSSFCell cell = row.createCell((short)0);
       		   cell.setCellValue(1);*/

    	        out.print(",");
    	        out.print("Account Details"); out.print(",");
    	        out.print(",");
    	        out.print("Transaction"); out.print(",");
    	        out.print("Amount"); out.print(",");
    	        out.print("Balance"); out.print(",");
    	        out.println();
       		out.print("GL Type"); out.print(",");
       		out.print("GL Code"); out.print(",");
       		out.print("GL Name"); out.print(",");
       		out.print("Date"); out.print(",");
       		out.print("Debit"); out.print(",");
       		out.print("Credit"); out.print(",");
       		out.print("Balance"); out.print("\n");	
       		
       		GLReportObject mthObject[]=null;
       		ModuleObject[] modObj=gldelegate.getMainMods();
       		String[][] report=null;
				Object data1[][]=null;
				Object data[][]=null;
       		String type_itemvalue,code_itemcount,code_to_itemcount;
       	    String string_qry=" ";
       	    int from_glno=0,to_glno=0;
       	    String from_gltype="",to_gltype="";
       	   
       		if(!(monthly_schedule.getCodes().toString() .equalsIgnoreCase("ALL Codes")) && (!(monthly_schedule.getTypes().toString() .toString() .equalsIgnoreCase("ALL Types"))))
       	    {
       	        from_glno=Integer.parseInt(monthly_schedule.getCodes().toString());
       	        to_glno=Integer.parseInt(monthly_schedule.getToCodes().toString());
       	        if(modObj!=null){
       	        	for(int i=0;i<modObj.length;i++){
       	        		if(monthly_schedule.getTypes().equalsIgnoreCase(modObj[i].getModuleAbbrv())){
       	        			from_gltype=modObj[i].getModuleCode();
       	        		}
       	        		if(monthly_schedule.getToTypes().equalsIgnoreCase(modObj[i].getModuleAbbrv())){
       	        			to_gltype=modObj[i].getModuleCode();
       	        		}
       	        	}
       	        	
       	        }
       	        
       	     }
       		int ret=-10;
       		try{
       			String month=null;
       			  
       			  if(Integer.parseInt(monthly_schedule.getMonth())<9){
       				  month="0"+monthly_schedule.getMonth();
       			  }
       			  else{
       				  month=monthly_schedule.getMonth();
       			  }
       			String fromdate="01"+"/"+month.toString()+"/"+monthly_schedule.getYear().toString();
       			ret=gldelegate.checkMonthlyStatusReport(fromdate,Validations.lastDayOfMonth(fromdate));
       			if(ret==0){
       				string_qry=" ";
       				//show report starts
       				try
       				{
       				
       				 mthObject=gldelegate.glMthScheduleDetails(monthly_schedule.getMonth().toString(),monthly_schedule.getYear().toString(),from_glno,from_gltype,to_glno,to_gltype,string_qry);    
       				}catch(Exception ae){ae.printStackTrace();}

       			if(mthObject.length>0)
       			{
       				System.out.println("Length.........."+mthObject.length);
       				//show report of mthObject starts
       				
       				int temp_glcode=0,temp_glcode1=0;
       				String temp_gltype="",temp_date="";
       				
       				double dr_total=0.0,cr_total=0.0,total=0.0;
       				int j,i=0,k=0,headings=1;
       				double amt=0.0,closing_bal=0.0,opening_bal=0.0;;
       				String str_temp=new String();
       				if(mthObject !=null){
       					for( i=0;i<mthObject.length;i++){
       					    if(i==0)
       					        temp_glcode1=mthObject[i].getGLCode();
       						if(i!=0 && mthObject[i].getGLCode()!=temp_glcode1)
       						{
       						 headings++;
       						 temp_glcode1=mthObject[i].getGLCode();
       						}
       					}
       					
       				}
       				data= new Object[mthObject.length][7];
       				i=0;
       				report=new String [mthObject.length +(headings*3)][14];
       				
       				if(mthObject !=null)
       					{
       					System.out.println("Length1="+mthObject.length);
       					
       					for(i=0;i<mthObject.length;i++)
       					{
       					if(i==0)
       					{
       						//System.out.println("when i value is zero_____________"+i);
       						/*System.out.println("value of i is ==="+i);
       						System.out.println("value of String.valueOf(mthObject[i].getGLAbbr() is ==="+String.valueOf(mthObject[i].getGLAbbr()));
       						System.out.println("value of String.valueOf(mthObject[i].getGLCode() is ==="+String.valueOf(mthObject[i].getGLCode()));
       						System.out.println("value of String.valueOf(mthObject[i].getGLName() is ==="+String.valueOf(mthObject[i].getGLName()));
       						System.out.println("value of String.valueOf(openbal[i].getGLName() is ==="+DoubleFormat.doubleToString(Math.abs(mthObject[i].getOpeningBalance()),2));*/
       						
       					data[i][0]=" "+String.valueOf(mthObject[i].getGLAbbr());
       					data[i][1]=" "+String.valueOf(mthObject[i].getGLCode());
       					temp_glcode=mthObject[i].getGLCode();
       					temp_gltype=mthObject[i].getGLType();
       					data[i][2]=" "+String.valueOf(mthObject[i].getGLName());
       					data[i][3]="  ";
       					data[i][4]="  ";
       					data[i][5]="O /B";
       					total=mthObject[i].getOpeningBalance();
       					closing_bal=mthObject[i].getClosingBalance();
       					System.out.println("open_bal"+mthObject[i].getOpeningBalance());
       					System.out.println(" opne balance *************"+mthObject[0].getOpeningBalance());
       					if(amt<0.0)
       					data[i][6]="  "+DoubleFormat.doubleToString(Math.abs(mthObject[i].getOpeningBalance()),2)+"Dr";
       					else
       					data[i][6]="  "+DoubleFormat.doubleToString(mthObject[i].getOpeningBalance(),2)+"Cr";
       					
       					i++;
       					report[k][0]=data[0].toString() ;report[k][1]=data[1].toString();report[k][2]=data[2].toString();report[k][3]=data[3].toString();
       					report[k][4]=data[4].toString();report[k][5]=data[5].toString();report[k][6]=data[6].toString();
       					k++;
       					}
       					if((temp_glcode==mthObject[i].getGLCode()) && (temp_gltype.equals(mthObject[i].getGLType())))
       			        {
       			            //System.out.println("inside if condition of the loop____________"+i);  
       			            data[i][0]=" ";data[i][1]=" ";data[i][2]=" "+String.valueOf(mthObject[i].getGLName());
       			            
       			            data[i][3]=mthObject[i].getDate();
       			            
       			            if(mthObject[i].getBalance()<0.0)
       			    		{
       			    		
       			    		dr_total=dr_total+mthObject[i].getBalance();
       			    		data[i][4]=" "+DoubleFormat.doubleToString(Math.abs(mthObject[i].getBalance()),2);
       			    		data[i][5]=" ";
       			    		}
       			    		else
       			    		{
       			    		data[i][4]=" ";
       			    		
       			    		cr_total+=mthObject[i].getBalance();
       			    		data[i][5]=" "+DoubleFormat.doubleToString(mthObject[i].getBalance(),2);
       			    		}
       			    		
       			            if(mthObject[i].getClosingBalance()<0.0)
       			            data[i][6]=" "+DoubleFormat.doubleToString(Math.abs(mthObject[i].getClosingBalance()),2)+"Dr";
       			            else
       			            data[i][6]=" "+DoubleFormat.doubleToString(mthObject[i].getClosingBalance(),2)+"Cr";
       			            
       			    		
       			    		report[k][0]=String .valueOf(mthObject[i].getGLCode());
       			            report[k][1]=mthObject[i].getGLType();
       			            report[k][2]=mthObject[i].getDate();
       			            report[k][3]=data[4].toString() ;
       			            report[k][4]=data[5].toString();
       			            report[k][5]=data[6].toString() ;
       			            k++;
       			    		
       			    	}
       			        else
       			        {
       			        // System.out.println("inside else of the loop___________"+i);   
       			            temp_glcode=mthObject[i].getGLCode();
       			            temp_gltype=mthObject[i].getGLType() ;
       			            temp_date=mthObject[i].getDate();
       			           
       			            data[i][0]=" ";data[i][1]=" ";data[i][2]=" ";
       			            data[i][3]="Sub Total";
       			            data[i][4]=" "+DoubleFormat.doubleToString(Math.abs(dr_total),2);
       			            data[i][5]=" "+DoubleFormat.doubleToString(cr_total,2);
       			            data[i][6]=" ";
       			            //i++;
       			            
       			            
       			            report[k][0]=String.valueOf(mthObject[i].getGLCode());
       			            report[k][1]=mthObject[i].getGLType();
       			            report[k][2]=mthObject[i].getDate();
       			            report[k][3]=data[4].toString();
       			            report[k][4]=data[5].toString();
       			            report[k][5]=data[6].toString();
       			            k++;
       			            
       			            
       			            dr_total=0.0;
       			            cr_total=0.0;
       			            total=0.0;
       			            
       			            data[i][0]=" "+String.valueOf(mthObject[i].getGLAbbr());//gltype
       			    		data[i][1]=" "+String.valueOf(mthObject[i].getGLCode());
       			    		data[i][2]=" "+String.valueOf(mthObject[i].getGLName());
       			    		data[i][3]="  ";
       			    		data[i][4]="  ";
       			    		data[i][5]="O/B ";
       			    		opening_bal=mthObject[i].getOpeningBalance();
       			    		System.out.println("open="+mthObject[i].getOpeningBalance());
       			    		total=opening_bal;
       			    		if(opening_bal<0.0)
       			    			data[i][6]="  "+DoubleFormat.doubleToString(Math.abs(opening_bal),2)+"Dr";
       			    			else
       			    			data[i][6]="  "+DoubleFormat.doubleToString(opening_bal,2)+"Cr";
       			    			
       			           //dtm.addRow(data);
       			    		i++;
       			            report[k][0]=String.valueOf(mthObject[i].getGLCode());
       			            report[k][1]=mthObject[i].getGLType();
       			            report[k][2]=mthObject[i].getDate();
       			            report[k][3]="";
       			            report[k][4]="";
       			            report[k][5]=data[6].toString();
       			            k++;
       			            
       			           data[i][0]=" ";data[i][1]=" ";data[i][2]=" ";
       			           data[i][3]=" "+String.valueOf(mthObject[i].getDate());
       			            if(mthObject[i].getBalance()<0.0)
       			    		{
       			    		total=total+Math.abs(mthObject[i].getBalance());
       			    		dr_total=dr_total+mthObject[i].getBalance();
       			    		data[i][4]=" "+DoubleFormat.doubleToString(Math.abs(mthObject[i].getBalance()),2);
       			    		data[i][5]=" ";
       			    		}
       			    		else
       			    		{
       			    		data[i][4]=" ";
       			    		total=total+mthObject[i].getBalance();
       			    		cr_total=cr_total+mthObject[i].getBalance();
       			    		data[i][5]=" "+DoubleFormat.doubleToString(mthObject[i].getBalance(),2);
       			    		}
       			    		
       			    		if(mthObject[i].getClosingBalance()<0.0)
       				        data[i][6]=" "+DoubleFormat.doubleToString(Math.abs(mthObject[i].getClosingBalance()),2)+"Dr";
       				        else
       				        data[i][6]=" "+DoubleFormat.doubleToString(mthObject[i].getClosingBalance(),2)+"Cr";
       			    		
       				            
       			    		//dtm.addRow(data);
       			    		
       			    		report[k][0]=String.valueOf(mthObject[i].getGLCode());
       			            report[k][1]=mthObject[i].getGLType();
       			            report[k][2]=mthObject[i].getDate();
       			            report[k][3]=data[4].toString();
       			            report[k][4]=data[5].toString();
       			            report[k][5]=data[6].toString();
       			            k++;
       			        }
       					}
       					data1=new Object[1][7];
       					data1[0][0]=" ";data1[0][1]=" ";data1[0][2]="Closing Bal";data1[0][3]="Sub Total";
       					data1[0][4]=" "+DoubleFormat.doubleToString(Math.abs(dr_total),2);
       		            data1[0][5]=" "+DoubleFormat.doubleToString(cr_total,2);
       		            data1[0][6]=" ";
       		           
       		            //dtm.addRow(data);
       		            
       		            report[k][0]=String.valueOf(mthObject[i-1].getGLCode());
       		            report[k][1]=mthObject[i-1].getGLType();
       		            report[k][2]=mthObject[i-1].getDate();
       		            report[k][3]=" ";
       		            report[k][4]=" ";
       		            report[k][5]=data[6].toString();
       		            k++;
       		            
       		           
       					}
       				/*for(int a=0;a<data.length;a++){
       					System.out.println("--->"+data[a][3]);
       				}*/
       				req.setAttribute("monthlyglschObj", data);
       				req.setAttribute("monthlyglschObj1", data1);
       				/*for(int a=0;i<report.length;a++){
       					for(int b=0;b<14;b++){
       						System.out.println("--------"+report[a][b]);
       					}
       					System.out.println("______________________");
       					
       				}*/
       				req.setAttribute("monthlyglschReport", report);
       			}
       			else{
       				
       			}
       			}
       		}catch(Exception e){
       				e.printStackTrace();
       			}
       		/*for(int i=0;i<1;i++){
       			for(int j=0;j<7;j++){
       			out.print(data1[i][j]);out.print(",");
       			}
       			out.println();
       		}
       		*/
       		for(int i=0;i<data.length;i++){
       			for(int j=0;j<7;j++){
       			out.print(data[i][j]);out.print(",");
       			}
       			out.println();
       		}
       		
       		for(int i=0;i<data1.length;i++){
       			for(int j=0;j<7;j++){
       			out.print(data1[i][j]);out.print(",");
       			}
       			out.println();
       		}
       	return null;	
		}
		    		  
		    		  
		    		  //code for onblur of toglno and clear end
		    		  	    		  
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
		
	
	
		
		
		
		
		
		
		/*  *************Gl Monthly Gl Schedule end********************** */
		/*  *************Gl Monthly Balance Sheet Start********************** */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyBalanceSheetMenu"))
		{
	      try{
	    	  MonthlyBalanceSheet balancesheet=(MonthlyBalanceSheet)form;
	    	  req.setAttribute("msg","");
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+balancesheet);
	    	  System.out.println("the page id is "+balancesheet.getPageId());
	    	  req.setAttribute("pagenum", balancesheet.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(balancesheet.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(balancesheet.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyBalanceSheet")){
			try{
				MonthlyBalanceSheet balancesheet=(MonthlyBalanceSheet)form;
				req.setAttribute("msg","");      	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+balancesheet);
		    	  System.out.println("the page id is "+balancesheet.getPageId());
		    	  req.setAttribute("pagenum", balancesheet.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(balancesheet.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(balancesheet.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  String report_data[][]=null;
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+balancesheet.getForward());
		    		  	//code for view starts
		    		  if(balancesheet.getFlag().equalsIgnoreCase("view") || balancesheet.getFlag().equalsIgnoreCase("file")){
		    			  String month=null;
		    			  String month1=null;
		    			  if(Integer.parseInt(balancesheet.getComparedMonth())<9){
		    				  month="0"+balancesheet.getComparedMonth();
		    			  }
		    			  else{
		    				  month=balancesheet.getComparedMonth();
		    			  }
		    			  if(Integer.parseInt(balancesheet.getReqMonth())<9){
		    				  month1="0"+balancesheet.getReqMonth();
		    			  }
		    			  else{
		    				  month1=balancesheet.getReqMonth();
		    			  }
		    			  GLReportObject[] liability_data=null;
		    			  GLReportObject[] assets_data=null;
		    			  
		    			  String date1="",date2="";
		    			  String fromdate="01"+"/"+month.toString()+"/"+balancesheet.getComparedYear().toString();
			  				String todate="01"+"/"+month1.toString()+"/"+balancesheet.getReqYear().toString();
		    		  int ret=-10;
		  			try{
		  				
		  				ret=gldelegate.checkMonthStatusForReport(fromdate,todate);
		  				if(ret==0){
		  					//show report starts
		  					try{
		  					 date1 = fromdate;
		  					 date2 = todate;
		  					
		  					date1=Validations.lastDayOfMonth(date1);
		  					date2=Validations.lastDayOfMonth(date2);
		  					System.out.println("date1"+date1);
		  					System.out.println("date2"+date2);
		  					req.setAttribute("PrevMthDate", date1);
		  					req.setAttribute("PresentMthDate", date2);
		  					
		  					
		  					System.out.println(" the date 1 is"+date1);
		  					System.out.println(" the date 2 is"+date2);
		  					
		  					liability_data = gldelegate.getBalanceTwodates1(date1,date2,1);
		  					assets_data = gldelegate.getBalanceTwodates2(date1,date2,2);
		  					
		  					int liability_data_length =0,assets_data_length=0;
		  					int sub_headings_liability=0,sub_headings_assets=0;;
		  					double sum1=0.00,sum2=0.00,sum3=0.00,sum4=0.00;
		  					
		  					
		  					if(liability_data !=null){
		  						for(int i=0;i<liability_data.length;i++){
		  							if(i!=0 && !(String.valueOf(liability_data[i].getGLCode()).equals(" ")) && String.valueOf(liability_data[i].getGLCode()).trim().substring(3,6).equalsIgnoreCase("000"))
		  								sub_headings_liability++;
		  						}
		  						liability_data_length = liability_data.length;
		  					}
		  					
		  					if(assets_data !=null){
		  						for(int i=0;i<assets_data.length;i++){
		  							if(i!=0 && !(String.valueOf(assets_data[i].getGLCode()).equals(" ")) && String.valueOf(assets_data[i].getGLCode()).trim().substring(3,6).equalsIgnoreCase("000"))
		  								sub_headings_assets++;
		  						}
		  						assets_data_length = assets_data.length;
		  					}
		  					
		  					if(liability_data_length > assets_data_length)
		  						report_data = new String[3+liability_data_length+(sub_headings_liability*2)+5][10];
		  					else
		  						report_data = new String[3+assets_data_length+(sub_headings_assets*2)+5][10];
		  					
		  					// Frist adding liability data to the report data with the sub total for sub heading
		  					double sum_closing_first=0,sum_closing_second=0;
		  					int i,k;
		  					for(i=0,k=0;i<report_data.length;i++,k++){
		  						if(k<liability_data_length){
		  							if(k!=0 && !(String.valueOf(liability_data[k].getGLCode()).equals(" ")) && String.valueOf(liability_data[k].getGLCode()).trim().substring(3,6).equalsIgnoreCase("000"))
		  							{
		  								report_data[i][0]=DoubleFormat.doubleToString(sum_closing_first,2);
		  								report_data[i][3]="Total";
		  								report_data[i][4]=DoubleFormat.doubleToString(sum_closing_second,2);
		  								sum1 +=sum_closing_first;
		  								sum2 +=sum_closing_second; 
		  								
		  								i++;
		  								i++; // to insert an empty line after total and next sub heading
		  								sum_closing_first=0;
		  								sum_closing_second=0;
		  							}
		  							if(liability_data[k].getNormalCD().equalsIgnoreCase("D")&& liability_data[k].getSecondDateBalance()!=0.0)
		  								report_data[i][0]=DoubleFormat.doubleToString(Math.abs(liability_data[k].getFirstDateBalance()),2);
		  							else
		  								report_data[i][0]=DoubleFormat.doubleToString(liability_data[k].getFirstDateBalance(),2);
		  							report_data[i][1]=liability_data[k].getGLAbbr();
		  							report_data[i][2]=String.valueOf(liability_data[k].getGLCode());
		  							report_data[i][3]=liability_data[k].getGLName();
		  							if(liability_data[k].getNormalCD().equalsIgnoreCase("D")&& liability_data[k].getSecondDateBalance()!=0.0)
		  								report_data[i][4]=DoubleFormat.doubleToString(Math.abs(liability_data[k].getSecondDateBalance()),2);
		  							else
		  								report_data[i][4]=DoubleFormat.doubleToString(liability_data[k].getSecondDateBalance(),2);
		  							
		  							
		  							if(liability_data[k].getFirstDateBalance()!=0.0 && String.valueOf(liability_data[k].getFirstDateBalance()).trim().length()>0 && liability_data[k].getNormalCD().equalsIgnoreCase("D"))
		  							    sum_closing_first += Double.parseDouble(String.valueOf(Math.abs(liability_data[k].getFirstDateBalance())));
		  							else
		  							    sum_closing_first += Double.parseDouble(String.valueOf(liability_data[k].getFirstDateBalance()));
		  							
		  							if(liability_data[k].getSecondDateBalance()!=0.0 && String.valueOf(liability_data[k].getSecondDateBalance()).trim().length()>0 && liability_data[k].getNormalCD().equalsIgnoreCase("D"))
		  								sum_closing_second += Double.parseDouble(String.valueOf(Math.abs(liability_data[k].getSecondDateBalance())));
		  							else
		  							    sum_closing_second += Double.parseDouble(String.valueOf(liability_data[k].getSecondDateBalance()));	}
		  					}
		  					
		  					// to display the total for the last record
		  					report_data[i-7][0]=DoubleFormat.doubleToString(sum_closing_first,2);
		  					report_data[i-7][3]="Total";
		  					report_data[i-7][4]=DoubleFormat.doubleToString(sum_closing_second,2);
		  					sum1 +=sum_closing_first;
		  					sum2 +=sum_closing_second; 
		  					
		  					report_data[i-5][0]=DoubleFormat.doubleToString(sum1,2);
		  					report_data[i-5][3]="Total Laibility";
		  					report_data[i-5][4]=DoubleFormat.doubleToString(sum2,2);
		  					
		  					
		  					
		  					// Adding the assets data to the report data
		  					sum_closing_first=0;
		  					sum_closing_second=0;
		  					for(i=0,k=0;i<report_data.length;i++,k++){
		  						if(k<assets_data_length)
		  						{
		  							if(k!=0 && String.valueOf(assets_data[k].getGLCode())!=null && String.valueOf(assets_data[k].getGLCode()).trim().substring(3,6).equalsIgnoreCase("000")){
		  								report_data[i][5]=DoubleFormat.doubleToString(sum_closing_first,2);
		  								report_data[i][8]="Total";
		  								report_data[i][9]=DoubleFormat.doubleToString(sum_closing_second,2);
		  								sum3 +=sum_closing_first;
		  								sum4 +=sum_closing_second; 
		  							
		  								i++;
		  								i++; // to insert an empty line after total and next sub heading
		  								sum_closing_first=0;
		  								sum_closing_second=0;
		  							}
		  							if(assets_data[k].getNormalCD().equalsIgnoreCase("D")&& assets_data[k].getFirstDateBalance()!=0.0)
		  							 report_data[i][5]=DoubleFormat.doubleToString(assets_data[k].getFirstDateBalance()*(-1),2);
		  							else
		  							report_data[i][5]=DoubleFormat.doubleToString(assets_data[k].getFirstDateBalance(),2);
		  							
		  							report_data[i][6]=assets_data[k].getGLAbbr();
		  							report_data[i][7]=String.valueOf(assets_data[k].getGLCode());
		  							report_data[i][8]=assets_data[k].getGLName();
		  							if(assets_data[k].getNormalCD().equalsIgnoreCase("D")&& assets_data[k].getSecondDateBalance()!=0.0)
		  							  report_data[i][9]=DoubleFormat.doubleToString(assets_data[k].getSecondDateBalance()*(-1),2);
		  							else
		  							    report_data[i][9]=DoubleFormat.doubleToString(assets_data[k].getSecondDateBalance(),2);
		  							
		  							if(assets_data[k].getFirstDateBalance()!=0.0 && String.valueOf(assets_data[k].getFirstDateBalance()).trim().length()>0 && assets_data[k].getNormalCD().equalsIgnoreCase("D"))
		  							    sum_closing_first += Double.parseDouble(String.valueOf(assets_data[k].getFirstDateBalance()*(-1)));
		  							else
		  							    sum_closing_first += Double.parseDouble(String.valueOf(assets_data[k].getFirstDateBalance()));
		  							
		  							if(assets_data[k].getSecondDateBalance()!=0.0 && String.valueOf(assets_data[k].getSecondDateBalance()).trim().length()>0 && assets_data[k].getNormalCD().equalsIgnoreCase("D"))
		  								sum_closing_second += Double.parseDouble(String.valueOf(assets_data[k].getSecondDateBalance()*(-1)));
		  							else
		  							    sum_closing_second += Double.parseDouble(String.valueOf(assets_data[k].getSecondDateBalance()));
		  						}
		  					}
		  					
		  					// to display the total for the last record
		  					report_data[i-7][5]=DoubleFormat.doubleToString(sum_closing_first,2);
		  					report_data[i-7][8]="Total";
		  					report_data[i-7][9]=DoubleFormat.doubleToString(sum_closing_second,2);
		  					sum3 +=sum_closing_first;
		  					sum4 +=sum_closing_second; 
		  					report_data[i-5][5]=DoubleFormat.doubleToString(sum3,2);
		  					report_data[i-5][8]="Total Asset";
		  					report_data[i-5][9]=DoubleFormat.doubleToString(sum4,2);
		  					
		  					double grand_sum1=sum3-sum1;
		  					double grand_sum2=sum4-sum2;
		  					report_data[i-3][0]=DoubleFormat.doubleToString(grand_sum1,2);
		  					report_data[i-3][3]="Profit/Loss";
		  					report_data[i-3][4]=DoubleFormat.doubleToString(grand_sum2,2);

		  					report_data[i-1][0]=DoubleFormat.doubleToString(grand_sum1+sum1,2);
		  					report_data[i-1][3]="Grand Total";
		  					report_data[i-1][4]=DoubleFormat.doubleToString(grand_sum2+sum2,2);
		  					
		  					report_data[i-1][5]=DoubleFormat.doubleToString(sum3,2);
		  					report_data[i-1][8]="Grand Total";
		  					report_data[i-1][9]=DoubleFormat.doubleToString(sum4,2);

		  					
		  					
		  					
		  					
		  					
		  					 
		  				}catch(Exception re){re.printStackTrace();}

		  					
		  				//show report ends
		  					
		  					
		  				}
		  				else{
		  					req.setAttribute("msg","Sorry Posting Not Done!..");
		  					
		  				}
		  			}catch(Exception ex){ex.printStackTrace();}
		  			req.setAttribute("MonthlyBalSheetReportData", report_data);
		  			if(balancesheet.getFlag().equalsIgnoreCase("file")){
		  				String filename=req.getParameter("fname");
		  				try{
		  				HSSFWorkbook wb= new HSSFWorkbook();
		  				HSSFSheet sheet = wb.createSheet("PLReport");
		  				sheet.setColumnWidth((short)0, (short)1000);
		  				sheet.setColumnWidth((short)1, (short)1000);
		  				sheet.setColumnWidth((short)2, (short)1000);
		  				sheet.setColumnWidth((short)3, (short)5000);
		  				sheet.setColumnWidth((short)4, (short)5000);
		  				sheet.setColumnWidth((short)5, (short)5000);
		  				sheet.setColumnWidth((short)6, (short)5000);
		  				sheet.setColumnWidth((short)7, (short)5000);
		  				sheet.setColumnWidth((short)8, (short)5000);
		  				sheet.setColumnWidth((short)9, (short)5000);
		  				sheet.setColumnWidth((short)10, (short)5000);
		  				sheet.setColumnWidth((short)11, (short)5000);
		  				sheet.setColumnWidth((short)12, (short)5000);
		  				sheet.setColumnWidth((short)13, (short)5000);
		  				HSSFFont font = wb.createFont();
		  	            font.setColor(HSSFFont.COLOR_RED);
		  	            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		  	            
		  	          HSSFCellStyle cellStyle= wb.createCellStyle();
		              cellStyle.setFont(font);
		  				HSSFRow row = sheet.createRow((short)2);
			  			HSSFCell cell=row.createCell((short)5);   
			  			cell.setCellStyle(cellStyle);
			            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell.setCellValue("");
                       	HSSFRow row1 = sheet.createRow((short)3);
		  			   	HSSFCell cell1=row1.createCell((short)5);
		  				cell1.setCellStyle(cellStyle);
			            cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell1.setCellValue("Balance Sheet For "+date2);
                        HSSFRow row2 = sheet.createRow((short)5);
		  			    HSSFCell cell2=row2.createCell((short)5);
		  			    cell2.setCellStyle(cellStyle);
		                cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
		                cell2.setCellValue("Liabilities");
		                HSSFCell cell3=row2.createCell((short)10);
		  			    cell3.setCellStyle(cellStyle);
		                cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
		                cell3.setCellValue("Assets");
		                HSSFRow row3 = sheet.createRow((short)6);
			  			HSSFCell cell4=row3.createCell((short)3);
			  			cell4.setCellStyle(cellStyle);
			            cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell4.setCellValue("As On"+date1); 
			            HSSFCell cell5=row3.createCell((short)4);
			  			cell5.setCellStyle(cellStyle);
			            cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell5.setCellValue("GLType");
			            HSSFCell cell6=row3.createCell((short)5);
			  			cell6.setCellStyle(cellStyle);
			            cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell6.setCellValue("GLCode");
			            HSSFCell cell7=row3.createCell((short)6);
			  			cell7.setCellStyle(cellStyle);
			            cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell7.setCellValue("GLName");
			            HSSFCell cell8=row3.createCell((short)7);
			  			cell8.setCellStyle(cellStyle);
			            cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell8.setCellValue("As On"+date2); 
			            
			            
			  			HSSFCell cell9=row3.createCell((short)8);
			  			cell9.setCellStyle(cellStyle);
			            cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell9.setCellValue("As On"+date1); 
			            HSSFCell cell10=row3.createCell((short)9);
			  			cell10.setCellStyle(cellStyle);
			            cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell10.setCellValue("GLType");
			            HSSFCell cell11=row3.createCell((short)10);
			  			cell11.setCellStyle(cellStyle);
			            cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell11.setCellValue("GLCode");
			            HSSFCell cell12=row3.createCell((short)11);
			  			cell12.setCellStyle(cellStyle);
			            cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell12.setCellValue("GLName");
			            HSSFCell cell13=row3.createCell((short)12);
			  			cell13.setCellStyle(cellStyle);
			            cell13.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell13.setCellValue("As On"+date2); 
			            int r=7;		  
			  			if(report_data!=null && report_data.length!=0){
			  				for(int i=0;i<report_data.length;i++){
			  					HSSFRow rowcount = sheet.createRow((short)r++);
			  				   
			  				   rowcount.createCell((short)3).setCellValue(report_data[i][0]);
			  				   rowcount.createCell((short)4).setCellValue(report_data[i][1]);
			  				   rowcount.createCell((short)5).setCellValue(report_data[i][2]);
			  				   rowcount.createCell((short)6).setCellValue(report_data[i][3]);
			  				   rowcount.createCell((short)7).setCellValue(report_data[i][4]);
			  				   rowcount.createCell((short)8).setCellValue(report_data[i][5]);
			  				   rowcount.createCell((short)9).setCellValue(report_data[i][6]);
			  				 rowcount.createCell((short)10).setCellValue(report_data[i][7]);
			  				rowcount.createCell((short)11).setCellValue(report_data[i][8]);
			  				rowcount.createCell((short)12).setCellValue(report_data[i][9]);
			  				   
			  				}
			  				
			  				
			  				}
			  			FileOutputStream fileOut = new FileOutputStream("c:\\Reports\\Balancesheet\\"+filename+".xls");
			  			wb.write(fileOut);
			  			fileOut.close();   
			  			req.setAttribute("msg","Successfully saved to file...!\n file path is c:\\Reports\\Balancesheet\\"+filename+".xls");
			  			     }catch ( Exception ex ){       
			  			     }     
			  			
		  			}
		  			return map.findForward(ResultHelp.getSuccess());
		    		  }
		    		  if(balancesheet.getFlag().equalsIgnoreCase("save"))
		    		  {
		    			  String month=null;
		    			  String month1=null;
		    			  if(Integer.parseInt(balancesheet.getComparedMonth())<9){
		    				  month="0"+balancesheet.getComparedMonth();
		    			  }
		    			  else{
		    				  month=balancesheet.getComparedMonth();
		    			  }
		    			  if(Integer.parseInt(balancesheet.getReqMonth())<9){
		    				  month1="0"+balancesheet.getReqMonth();
		    			  }
		    			  else{
		    				  month1=balancesheet.getReqMonth();
		    			  }
		    			  GLReportObject[] liability_data=null;
		    			  GLReportObject[] assets_data=null;
		    			  
		    			  String date1="",date2="";
		    			  String fromdate="01"+"/"+month.toString()+"/"+balancesheet.getComparedYear().toString();
			  				String todate="01"+"/"+month1.toString()+"/"+balancesheet.getReqYear().toString();
		    		  int ret=-10;
		  			try{
		  				
		  				ret=gldelegate.checkMonthStatusForReport(fromdate,todate);
		  				if(ret==0){
		  					//show report starts
		  					try{
		  					 date1 = fromdate;
		  					 date2 = todate;
		  					
		  					date1=Validations.lastDayOfMonth(date1);
		  					date2=Validations.lastDayOfMonth(date2);
		  					System.out.println("date1"+date1);
		  					System.out.println("date2"+date2);
		  					req.setAttribute("PrevMthDate", date1);
		  					req.setAttribute("PresentMthDate", date2);
		  					
		  					
		  					System.out.println(" the date 1 is"+date1);
		  					System.out.println(" the date 2 is"+date2);
		  					
		  					liability_data = gldelegate.getBalanceTwodates1(date1,date2,1);
		  					assets_data = gldelegate.getBalanceTwodates2(date1,date2,2);
		  					
		  					int liability_data_length =0,assets_data_length=0;
		  					int sub_headings_liability=0,sub_headings_assets=0;;
		  					double sum1=0.00,sum2=0.00,sum3=0.00,sum4=0.00;
		  					
		  					
		  					if(liability_data !=null){
		  						for(int i=0;i<liability_data.length;i++){
		  							if(i!=0 && !(String.valueOf(liability_data[i].getGLCode()).equals(" ")) && String.valueOf(liability_data[i].getGLCode()).trim().substring(3,6).equalsIgnoreCase("000"))
		  								sub_headings_liability++;
		  						}
		  						liability_data_length = liability_data.length;
		  					}
		  					
		  					if(assets_data !=null){
		  						for(int i=0;i<assets_data.length;i++){
		  							if(i!=0 && !(String.valueOf(assets_data[i].getGLCode()).equals(" ")) && String.valueOf(assets_data[i].getGLCode()).trim().substring(3,6).equalsIgnoreCase("000"))
		  								sub_headings_assets++;
		  						}
		  						assets_data_length = assets_data.length;
		  					}
		  					
		  					if(liability_data_length > assets_data_length)
		  						report_data = new String[3+liability_data_length+(sub_headings_liability*2)+5][10];
		  					else
		  						report_data = new String[3+assets_data_length+(sub_headings_assets*2)+5][10];
		  					
		  					// Frist adding liability data to the report data with the sub total for sub heading
		  					double sum_closing_first=0,sum_closing_second=0;
		  					int i,k;
		  					for(i=0,k=0;i<report_data.length;i++,k++){
		  						if(k<liability_data_length){
		  							if(k!=0 && !(String.valueOf(liability_data[k].getGLCode()).equals(" ")) && String.valueOf(liability_data[k].getGLCode()).trim().substring(3,6).equalsIgnoreCase("000"))
		  							{
		  								report_data[i][0]=DoubleFormat.doubleToString(sum_closing_first,2);
		  								report_data[i][3]="Total";
		  								report_data[i][4]=DoubleFormat.doubleToString(sum_closing_second,2);
		  								sum1 +=sum_closing_first;
		  								sum2 +=sum_closing_second; 
		  								
		  								i++;
		  								i++; // to insert an empty line after total and next sub heading
		  								sum_closing_first=0;
		  								sum_closing_second=0;
		  							}
		  							if(liability_data[k].getNormalCD().equalsIgnoreCase("D")&& liability_data[k].getSecondDateBalance()!=0.0)
		  								report_data[i][0]=DoubleFormat.doubleToString(Math.abs(liability_data[k].getFirstDateBalance()),2);
		  							else
		  								report_data[i][0]=DoubleFormat.doubleToString(liability_data[k].getFirstDateBalance(),2);
		  							report_data[i][1]=liability_data[k].getGLAbbr();
		  							report_data[i][2]=String.valueOf(liability_data[k].getGLCode());
		  							report_data[i][3]=liability_data[k].getGLName();
		  							if(liability_data[k].getNormalCD().equalsIgnoreCase("D")&& liability_data[k].getSecondDateBalance()!=0.0)
		  								report_data[i][4]=DoubleFormat.doubleToString(Math.abs(liability_data[k].getSecondDateBalance()),2);
		  							else
		  								report_data[i][4]=DoubleFormat.doubleToString(liability_data[k].getSecondDateBalance(),2);
		  							
		  							
		  							if(liability_data[k].getFirstDateBalance()!=0.0 && String.valueOf(liability_data[k].getFirstDateBalance()).trim().length()>0 && liability_data[k].getNormalCD().equalsIgnoreCase("D"))
		  							    sum_closing_first += Double.parseDouble(String.valueOf(Math.abs(liability_data[k].getFirstDateBalance())));
		  							else
		  							    sum_closing_first += Double.parseDouble(String.valueOf(liability_data[k].getFirstDateBalance()));
		  							
		  							if(liability_data[k].getSecondDateBalance()!=0.0 && String.valueOf(liability_data[k].getSecondDateBalance()).trim().length()>0 && liability_data[k].getNormalCD().equalsIgnoreCase("D"))
		  								sum_closing_second += Double.parseDouble(String.valueOf(Math.abs(liability_data[k].getSecondDateBalance())));
		  							else
		  							    sum_closing_second += Double.parseDouble(String.valueOf(liability_data[k].getSecondDateBalance()));	}
		  					}
		  					
		  					// to display the total for the last record
		  					report_data[i-7][0]=DoubleFormat.doubleToString(sum_closing_first,2);
		  					report_data[i-7][3]="Total";
		  					report_data[i-7][4]=DoubleFormat.doubleToString(sum_closing_second,2);
		  					sum1 +=sum_closing_first;
		  					sum2 +=sum_closing_second; 
		  					
		  					report_data[i-5][0]=DoubleFormat.doubleToString(sum1,2);
		  					report_data[i-5][3]="Total Laibility";
		  					report_data[i-5][4]=DoubleFormat.doubleToString(sum2,2);
		  					
		  					
		  					
		  					// Adding the assets data to the report data
		  					sum_closing_first=0;
		  					sum_closing_second=0;
		  					for(i=0,k=0;i<report_data.length;i++,k++){
		  						if(k<assets_data_length)
		  						{
		  							if(k!=0 && String.valueOf(assets_data[k].getGLCode())!=null && String.valueOf(assets_data[k].getGLCode()).trim().substring(3,6).equalsIgnoreCase("000")){
		  								report_data[i][5]=DoubleFormat.doubleToString(sum_closing_first,2);
		  								report_data[i][8]="Total";
		  								report_data[i][9]=DoubleFormat.doubleToString(sum_closing_second,2);
		  								sum3 +=sum_closing_first;
		  								sum4 +=sum_closing_second; 
		  							
		  								i++;
		  								i++; // to insert an empty line after total and next sub heading
		  								sum_closing_first=0;
		  								sum_closing_second=0;
		  							}
		  							if(assets_data[k].getNormalCD().equalsIgnoreCase("D")&& assets_data[k].getFirstDateBalance()!=0.0)
		  							 report_data[i][5]=DoubleFormat.doubleToString(assets_data[k].getFirstDateBalance()*(-1),2);
		  							else
		  							report_data[i][5]=DoubleFormat.doubleToString(assets_data[k].getFirstDateBalance(),2);
		  							
		  							report_data[i][6]=assets_data[k].getGLAbbr();
		  							report_data[i][7]=String.valueOf(assets_data[k].getGLCode());
		  							report_data[i][8]=assets_data[k].getGLName();
		  							if(assets_data[k].getNormalCD().equalsIgnoreCase("D")&& assets_data[k].getSecondDateBalance()!=0.0)
		  							  report_data[i][9]=DoubleFormat.doubleToString(assets_data[k].getSecondDateBalance()*(-1),2);
		  							else
		  							    report_data[i][9]=DoubleFormat.doubleToString(assets_data[k].getSecondDateBalance(),2);
		  							
		  							if(assets_data[k].getFirstDateBalance()!=0.0 && String.valueOf(assets_data[k].getFirstDateBalance()).trim().length()>0 && assets_data[k].getNormalCD().equalsIgnoreCase("D"))
		  							    sum_closing_first += Double.parseDouble(String.valueOf(assets_data[k].getFirstDateBalance()*(-1)));
		  							else
		  							    sum_closing_first += Double.parseDouble(String.valueOf(assets_data[k].getFirstDateBalance()));
		  							
		  							if(assets_data[k].getSecondDateBalance()!=0.0 && String.valueOf(assets_data[k].getSecondDateBalance()).trim().length()>0 && assets_data[k].getNormalCD().equalsIgnoreCase("D"))
		  								sum_closing_second += Double.parseDouble(String.valueOf(assets_data[k].getSecondDateBalance()*(-1)));
		  							else
		  							    sum_closing_second += Double.parseDouble(String.valueOf(assets_data[k].getSecondDateBalance()));
		  						}
		  					}
		  					
		  					// to display the total for the last record
		  					report_data[i-7][5]=DoubleFormat.doubleToString(sum_closing_first,2);
		  					report_data[i-7][8]="Total";
		  					report_data[i-7][9]=DoubleFormat.doubleToString(sum_closing_second,2);
		  					sum3 +=sum_closing_first;
		  					sum4 +=sum_closing_second; 
		  					report_data[i-5][5]=DoubleFormat.doubleToString(sum3,2);
		  					report_data[i-5][8]="Total Asset";
		  					report_data[i-5][9]=DoubleFormat.doubleToString(sum4,2);
		  					
		  					double grand_sum1=sum3-sum1;
		  					double grand_sum2=sum4-sum2;
		  					report_data[i-3][0]=DoubleFormat.doubleToString(grand_sum1,2);
		  					report_data[i-3][3]="Profit/Loss";
		  					report_data[i-3][4]=DoubleFormat.doubleToString(grand_sum2,2);

		  					report_data[i-1][0]=DoubleFormat.doubleToString(grand_sum1+sum1,2);
		  					report_data[i-1][3]="Grand Total";
		  					report_data[i-1][4]=DoubleFormat.doubleToString(grand_sum2+sum2,2);
		  					
		  					report_data[i-1][5]=DoubleFormat.doubleToString(sum3,2);
		  					report_data[i-1][8]="Grand Total";
		  					report_data[i-1][9]=DoubleFormat.doubleToString(sum4,2);

		  					
		  					
		  					
		  					
		  					
		  					 
		  				}catch(Exception re){re.printStackTrace();}

		  					
		  				//show report ends
		  					
		  					
		  				}
		  				else{
		  					req.setAttribute("msg","Sorry Posting Not Done!..");
		  					
		  				}
		  			}catch(Exception ex){ex.printStackTrace();}
		    			  System.out.println("Inside save ");
                 		 //TO save to an excel Sheet
                 		 res.setContentType("application/.csv");
                 	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
                 	        
                 	        java.io.PrintWriter out = res.getWriter();
                 	        out.print("\n");
                 	        out.print("\n");
                 	        out.print("\n");
                 	        out.print(",");out.print(",");out.print(",");
                 	        out.print("Balance Details: ");
                 	        out.print("\n");
                 	        out.print("\n");
                    		   /*HSSFCell cell = row.createCell((short)0);
                    		   cell.setCellValue(1);*/
                 	       out.print(","); out.print(",");
                 	       out.print("Liabilities"); out.print(",");
                 	       out.print(",");
                 	       out.print(",");out.print(",");
                 	       out.print(",");
                 	       out.print("Assets");out.print(",");
                 	       out.print(",");out.println();
                 	        out.print("As On"); out.print(",");
                    		out.print("GL Type"); out.print(",");
                    		out.print("GL Code"); out.print(",");
                    		out.print("GL Name"); out.print(",");
                    		out.print("As On"); out.print(",");
                    		out.print("As On"); out.print(",");    
                    		out.print("GL Type"); out.print(",");
                    		out.print("GL Code"); out.print(",");
                    		out.print("GL Name"); out.print(",");
                    		out.print("As On"); out.print("\n");
                    		
		  			for(int r=0;r<report_data.length;r++){
		  				for(int k=0;k<10;k++){
		  					out.print(report_data[r][k]);//.replaceAll(",", " ")
                 			out.print(",");
		  				}
		  				out.println();
		  			}
                    		
		  			return null;  
		    		  }
		    		  
		    		  
		    		  
		    		  //code for view ends
		    		
		    		  
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
		
	
	
		
		
		
		
		
		/*  *************Gl Monthly Balance Sheet end********************** */

		
		
		
/*  *************Gl Monthly Trial Balance Start********************** */
		
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyTrialBalanceMenu"))
		{
	      try{
	    	  MonthlyTrailBalance trailbalance=(MonthlyTrailBalance)form;
	    	  req.setAttribute("msg","");
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+trailbalance);
	    	  System.out.println("the page id is "+trailbalance.getPageId());
	    	  req.setAttribute("pagenum", trailbalance.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(trailbalance.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(trailbalance.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyTrialBalance")){
			try{
				MonthlyTrailBalance trailbalance=(MonthlyTrailBalance)form;
				req.setAttribute("msg","");
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+trailbalance);
		    	  System.out.println("the page id is "+trailbalance.getPageId());
		    	  req.setAttribute("pagenum", trailbalance.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(trailbalance.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(trailbalance.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+trailbalance.getForward());
		    		  //code for view starts
		    		  if(trailbalance.getFlag().equalsIgnoreCase("view") || trailbalance.getFlag().equalsIgnoreCase("file")){
		    			  GLReportObject[] array_obj_trial=null;
		    			  Object[][] data=null;
		    			  Object[][] data1=null;
		    			  double credit_sum=0.0,debit_sum=0.0;
		    			  String month=null;
		    			  
		    			  if(Integer.parseInt(trailbalance.getMonth())<9){
		    				  month="0"+trailbalance.getMonth();
		    			  }
		    			  else{
		    				  month=trailbalance.getMonth();
		    			  }
		    			  String fromdate="01"+"/"+month.toString()+"/"+trailbalance.getYear().toString();
			  				
		    		  int ret=-10;
		  			try{
		  				
		  				ret=gldelegate.checkMonthStatusForReport(fromdate,fromdate);
		  				if(ret==0){
		    			  //show report starts
		  					try{
		  						int date = Integer.parseInt(trailbalance.getYear().toString().trim()+trailbalance.getMonth().toString().trim());
		  						System.out.println("value of a is"+date);
		  						String t_date="01"+"/"+trailbalance.getMonth().toString().trim()+"/"+trailbalance.getYear().toString().trim();
		  						String l_date=Validations.lastDayOfMonth(t_date);
		  						array_obj_trial = gldelegate.getBalanceTwodates1(l_date,l_date,0);
		  						
		  						
		  					
		  					
		  					
		  					
		  					data=new Object[array_obj_trial.length][5];
		  					Object insert_line[] = new Object[6];
		  					
		  					for(int i=0;i<insert_line.length;i++)
		  						insert_line[i]=" ";
		  					
		  					for(int row=0;row<array_obj_trial.length;row++)
		  					{
		  			   			data[row][3]="";
		  						data[row][4]="";
		  						
		  			        	data[row][0]=array_obj_trial[row].getGLType();
		  			        	data[row][1]=String.valueOf(array_obj_trial[row].getGLCode());
		  			        	data[row][2]=array_obj_trial[row].getGLName();
		  			        	
		  			        	if(array_obj_trial[row].getNormalCD().equalsIgnoreCase("D")){
		  			        		data[row][3]=DoubleFormat.doubleToString(Math.abs(array_obj_trial[row].getSecondDateBalance()),2);
		  			        	    debit_sum+=(Math.abs(array_obj_trial[row].getSecondDateBalance()));
		  			        	}
		  			        	else{
		  			        		data[row][4]=DoubleFormat.doubleToString(array_obj_trial[row].getSecondDateBalance(),2);
		  			        		credit_sum+=(array_obj_trial[row].getSecondDateBalance());
		  			        	}
		  			    		
		  				  		
		  			        }  
		  					data1=new Object[1][5];
		  						data1[0][0]="";
		  						data1[0][1]="";
		  						data1[0][2]="Total :";
		  						data1[0][3]=DoubleFormat.doubleToString(debit_sum,2);
		  						data1[0][4]=DoubleFormat.doubleToString(credit_sum,2);
		  			        
		  						
		  			            
		  			            
		  			}
		  			catch (RemoteException e) {e.printStackTrace();}
		  					
		  					
		  					
		  					//show report ends
							}
								else{
									req.setAttribute("msg","Sorry Posting Not Done!..");
									
								}
							}catch(Exception ex){ex.printStackTrace();}
						
				  req.setAttribute("trialbaldata", data);
				  req.setAttribute("trialbaldata1", data1);
				  if(trailbalance.getFlag().equalsIgnoreCase("file")){
					  System.out.println("sstart of file code__________");
						String filename=req.getParameter("fname");
		  				try{
		  				HSSFWorkbook wb= new HSSFWorkbook();
		  				HSSFSheet sheet = wb.createSheet("GL");
		  				sheet.setColumnWidth((short)0, (short)1000);
		  				sheet.setColumnWidth((short)1, (short)1000);
		  				sheet.setColumnWidth((short)2, (short)1000);
		  				sheet.setColumnWidth((short)3, (short)5000);
		  				sheet.setColumnWidth((short)4, (short)5000);
		  				sheet.setColumnWidth((short)5, (short)5000);
		  				sheet.setColumnWidth((short)6, (short)5000);
		  				sheet.setColumnWidth((short)7, (short)5000);
		  				  				
		  				HSSFFont font = wb.createFont();
		  	            font.setColor(HSSFFont.COLOR_RED);
		  	            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		  	            
		  	          HSSFCellStyle cellStyle= wb.createCellStyle();
		              cellStyle.setFont(font);
		  				HSSFRow row = sheet.createRow((short)2);
			  			HSSFCell cell=row.createCell((short)5);   
			  			cell.setCellStyle(cellStyle);
			            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell.setCellValue("");
		               	HSSFRow row1 = sheet.createRow((short)3);
		  			   	HSSFCell cell1=row1.createCell((short)5);
		  				cell1.setCellStyle(cellStyle);
			            cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell1.setCellValue("Monthly Trialbalance For "+Validations.lastDayOfMonth(fromdate));
		                HSSFRow row2 = sheet.createRow((short)5);
		  			    HSSFCell cell2=row2.createCell((short)4);
		  			    cell2.setCellStyle(cellStyle);
		                cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
		                cell2.setCellValue("Account Details");
		                
		                HSSFCell cell9=row2.createCell((short)7);
		  			    cell9.setCellStyle(cellStyle);
		                cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
		                cell9.setCellValue("Amount");
		                
		                HSSFRow row3 = sheet.createRow((short)6);
			  			HSSFCell cell5=row3.createCell((short)3);
			  			cell5.setCellStyle(cellStyle);
			            cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell5.setCellValue("GLType");
			            HSSFCell cell6=row3.createCell((short)4);
			  			cell6.setCellStyle(cellStyle);
			            cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell6.setCellValue("GLCode");
			            HSSFCell cell7=row3.createCell((short)5);
			  			cell7.setCellStyle(cellStyle);
			            cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell7.setCellValue("GLName");
			                 
			            
			  			
			            HSSFCell cell11=row3.createCell((short)6);
			  			cell11.setCellStyle(cellStyle);
			            cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell11.setCellValue("Debit");
			            HSSFCell cell12=row3.createCell((short)7);
			  			cell12.setCellStyle(cellStyle);
			            cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
			            cell12.setCellValue("Credit");
			            
			            int r=7;		  
			  			if(data!=null && data.length!=0){
			  				System.out.println("value of data object is not null____________"+data.length);
			  				for(int m=0;m<data.length;m++){
			  					
			  					HSSFRow rowcount = sheet.createRow((short)r++);
			  				   
			  				   rowcount.createCell((short)3).setCellValue(data[m][0].toString());
			  				   rowcount.createCell((short)4).setCellValue(data[m][1].toString());
			  				   rowcount.createCell((short)5).setCellValue(data[m][2].toString());
			  				   rowcount.createCell((short)6).setCellValue(data[m][3].toString());
			  				   rowcount.createCell((short)7).setCellValue(data[m][4].toString());
			  				   
			  				 }
			  				
			  				for(int l=0;l<data1.length;l++){
			  					HSSFRow rowcount = sheet.createRow((short)r++);
			  					rowcount.createCell((short)3).setCellValue(data1[l][0].toString());
				  				   rowcount.createCell((short)4).setCellValue(data1[l][1].toString());
				  				   rowcount.createCell((short)5).setCellValue(data1[l][2].toString());
				  				   rowcount.createCell((short)6).setCellValue(data1[l][3].toString());
				  				   rowcount.createCell((short)7).setCellValue(data1[l][4].toString());
				  				   
			  				}
			  				
			  				
			  				}
			  			
			  			FileOutputStream fileOut = new FileOutputStream("c:\\Reports\\Trialbalance\\"+filename+".xls");
			  			wb.write(fileOut);
			  			
			  			fileOut.close();   
			  			req.setAttribute("msg","Successfully saved to file....!\n file path is  c:\\Reports\\Trialbalance\\"+filename+".xls");
			  			     }catch ( Exception ex ){   
			  			    	 ex.printStackTrace();
			  			     }     
			  		
						
					}
				
		    		  }
				  //code for view ends
		    		  if(trailbalance.getFlag().equalsIgnoreCase("save"))
		    		  {
		    			  System.out.println("Inside save ");
                 		 //TO save to an excel Sheet
                 		 res.setContentType("application/.csv");
                 	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
                 	        
                 	        java.io.PrintWriter out = res.getWriter();
                 	        out.print("\n");
                 	        out.print("\n");
                 	        out.print("\n");
                 	        out.print(",");out.print(",");out.print(",");
                 	        out.print("Trial Balance Details: ");
                 	        out.print("\n");
                 	        out.print("\n");
                    		   /*HSSFCell cell = row.createCell((short)0);
                    		   cell.setCellValue(1);*/
                 	        out.print(",");
                 	        out.print("Account Details"); out.print(",");
                 	        out.print(","); out.print("Amount");
                 	        out.println();
                    		out.print("GL Type"); out.print(",");
                    		out.print("GL Code"); out.print(",");
                    		out.print("Name"); out.print(",");
                    		out.print("Debit"); out.print(",");
                    		out.print("Credit"); out.print("\n");
                    		  GLReportObject[] array_obj_trial=null;
    		    			  Object[][] data=null;
    		    			  Object[][] data1=null;
    		    			  double credit_sum=0.0,debit_sum=0.0;
    		    			  String month=null;
    		    			  
    		    			  if(Integer.parseInt(trailbalance.getMonth())<9){
    		    				  month="0"+trailbalance.getMonth();
    		    			  }
    		    			  else{
    		    				  month=trailbalance.getMonth();
    		    			  }
    		    			  String fromdate="01"+"/"+month.toString()+"/"+trailbalance.getYear().toString();
    			  				
    		    		  int ret=-10;
    		  			try{
    		  				
    		  				ret=gldelegate.checkMonthStatusForReport(fromdate,fromdate);
    		  				if(ret==0){
    		    			  //show report starts
    		  					try{
    		  						int date = Integer.parseInt(trailbalance.getYear().toString().trim()+trailbalance.getMonth().toString().trim());
    		  						System.out.println("value of a is"+date);
    		  						String t_date="01"+"/"+trailbalance.getMonth().toString().trim()+"/"+trailbalance.getYear().toString().trim();
    		  						String l_date=Validations.lastDayOfMonth(t_date);
    		  						array_obj_trial = gldelegate.getBalanceTwodates1(l_date,l_date,0);
    		  						
    		  						
    		  					
    		  					
    		  					
    		  					
    		  					data=new Object[array_obj_trial.length][5];
    		  					Object insert_line[] = new Object[6];
    		  					
    		  					for(int i=0;i<insert_line.length;i++)
    		  						insert_line[i]=" ";
    		  					
    		  					for(int row=0;row<array_obj_trial.length;row++)
    		  					{
    		  			   			data[row][3]="";
    		  						data[row][4]="";
    		  						
    		  			        	data[row][0]=array_obj_trial[row].getGLType();
    		  			        	data[row][1]=String.valueOf(array_obj_trial[row].getGLCode());
    		  			        	data[row][2]=array_obj_trial[row].getGLName();
    		  			        	
    		  			        	if(array_obj_trial[row].getNormalCD().equalsIgnoreCase("D")){
    		  			        		data[row][3]=DoubleFormat.doubleToString(Math.abs(array_obj_trial[row].getSecondDateBalance()),2);
    		  			        	    debit_sum+=(Math.abs(array_obj_trial[row].getSecondDateBalance()));
    		  			        	}
    		  			        	else{
    		  			        		data[row][4]=DoubleFormat.doubleToString(array_obj_trial[row].getSecondDateBalance(),2);
    		  			        		credit_sum+=(array_obj_trial[row].getSecondDateBalance());
    		  			        	}
    		  			    		
    		  				  		
    		  			        }  
    		  					data1=new Object[1][5];
    		  						data1[0][0]="";
    		  						data1[0][1]="";
    		  						data1[0][2]="Total :";
    		  						data1[0][3]=DoubleFormat.doubleToString(debit_sum,2);
    		  						data1[0][4]=DoubleFormat.doubleToString(credit_sum,2);
    		  			        
    		  						
    		  			            
    		  			            
    		  			}
    		  			catch (RemoteException e) {e.printStackTrace();}
    		  					
    		  					
    		  					
    		  					//show report ends
    							}
    								else{
    									req.setAttribute("msg","Sorry Posting Not Done!..");
    									
    								}
    							}catch(Exception ex){ex.printStackTrace();}
    							/*for(int r=0;r<1;r++){
    								for(int l=0;l<5;l++){
                            			out.print(data1[r][l]);
                            			out.print(",");
                          			  }
                            			out.println();
    							}*/
                    		 for(int k=0;k<data.length;k++){
                      			  for(int l=0;l<5;l++){
                      				 out.print(data[k][l]);
                      				 out.print(",");
                      			  }
                        			out.println();
                    		 }
                    		 
                    		 for(int b=0;b<data1.length;b++){
                    			 for(int l=0;l<5;l++){
                         			out.print(data1[b][l]);
                         			out.print(",");
                       			  }
                         			out.println();
                    		 }
                    		 req.setAttribute("msg","Saved to excel file in C:");
                 		    return null;
		    		  }
		    		  	    		  
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
		
	
		
		
		
		
		
		/*  *************Gl Monthly Trial Balance end********************** */

		
		/*  *************Gl Consolidated Daybook  start********************** */
			/*  *************Gl Consolidated Daybook  end********************** */
		
		/*  *************Gl Consolidated VoucherSlip  start********************** */
		/*if(map.getPath().trim().equalsIgnoreCase("/GL/GLConsolidatedVoucherSlipMenu"))
		{
	      try{
	    	  ConsolidatedDayBook consolidated_daybook=(ConsolidatedDayBook)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+consolidated_daybook);
	    	  System.out.println("the page id is "+consolidated_daybook.getPageId());
	    	  req.setAttribute("pagenum", consolidated_daybook.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(consolidated_daybook.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(consolidated_daybook.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLConsolidatedVoucherSlip")){
			try{
				ConsolidatedDayBook consolidated_daybook=(ConsolidatedDayBook)form;
		       	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+consolidated_daybook);
		    	  System.out.println("the page id is "+consolidated_daybook.getPageId());
		    	  req.setAttribute("pagenum", consolidated_daybook.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(consolidated_daybook.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(consolidated_daybook.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+consolidated_daybook.getForward());
		    		  	    		  
		    		 if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }
		    		  
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
		
	*/
	
		//commented by amzad on 10.07.2009
		
		
		
		
		
		
		
		/*  *************Gl Consolidated VoucherSlip  end********************** */
		/*  *************Gl Monthly Consolidated Gl Schedule  start********************** 
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyConsolidatedGlScheduleMenu"))
		{
	      try{
	    	  MonthlyConsolidatedGlSchedule mon_con_glschedule=(MonthlyConsolidatedGlSchedule)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+mon_con_glschedule);
	    	  System.out.println("the page id is "+mon_con_glschedule.getPageId());
	    	  req.setAttribute("pagenum", mon_con_glschedule.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(mon_con_glschedule.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(mon_con_glschedule.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  req.setAttribute("moduleobj",gldelegate.getMainMods());
	    		  mon_con_glschedule.setYear("2009");
	    		  mon_con_glschedule.setMonth("01");
		    	  String yr=String.valueOf(mon_con_glschedule.getYear());
		    	  String mnth=String.valueOf(mon_con_glschedule.getMonth());
		    	  String yearmnth=yr.concat(mnth);
	    		  String[][] arr=gldelegate.getGlDetForMonth(yearmnth);
		    	  System.out.println("*****************************1949");
		    	  if(arr!=null){
		    		  System.out.println("sending to delegate");
		    	  req.setAttribute("MonthlyGlNo", gldelegate.getGlDetForMonth(yearmnth));
		    	  System.out.println("receive from delegate");
		    	  }
			       
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyConsolidatedGlSchedule")){
			try{
				 MonthlyConsolidatedGlSchedule mon_con_glschedule=(MonthlyConsolidatedGlSchedule)form;
		       	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+mon_con_glschedule);
		    	  System.out.println("the page id is "+mon_con_glschedule.getPageId());
		    	  req.setAttribute("pagenum", mon_con_glschedule.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(mon_con_glschedule.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(mon_con_glschedule.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+mon_con_glschedule.getForward());
		    		  
		    		//code for onblur of toglno and clear start
		    		  if(mon_con_glschedule.getFlag().equalsIgnoreCase("to_glno")){

		    		  	                      String[][] codetype=null;
		    		  	                      codetype=gldelegate.getGlDetForMonth("200901");
		    		  		                  if(mon_con_glschedule.getCodes().equalsIgnoreCase("ALL_Codes")){
		    		  		                	  strType="";
		    		  		                	  strCode="";
		    		  		                	  req.setAttribute("StringCode", strType);
		    		  		                	  req.setAttribute("StringCode", strCode); 
		    		  		                    
		    		  		                  }   
		    		  		                  else
		    		  		                  {
		    		  		                     
		    		  		                      for(int i=0;i<gldelegate.getCodeTypes("01/01/1996", "01/01/2000").length;i++)
		    		  		                      {
		    		  		                          if(mon_con_glschedule.getCodes().equals(codetype[i][1]))
		    		  		                          {
		    		  		                        	  req.setAttribute("StringType", codetype[i][2]);
		    		  				                	   
		    		  		                              
		    		  		                          }
		    		  		                      }
		    		  		                      //////////
		    		  		                      for(int i=0;i<gldelegate.getCodeTypes("01/01/1996", "01/01/2000").length;i++)
		    		  		                      {
		    		  		                          if(mon_con_glschedule.getToCodes().equals(codetype[i][1]))
		    		  		                          {
		    		  		                        	  
		    		  				                	  req.setAttribute("StringCode", codetype[i][2]); 
		    		  		                              
		    		  		                          }
		    		  		                      }
		    		  		                      
		    		  		                      
		    		  		                      
		    		  		                  }
		    		  		                  path=MenuNameReader.getScreenProperties(mon_con_glschedule.getPageId());
		    		  			    		  gldelegate=new GLDelegate();
		    		  			    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  			    		mon_con_glschedule.setYear(mon_con_glschedule.getYear());
		    		  			    		mon_con_glschedule.setMonth(mon_con_glschedule.getMonth());
		    		  			    		mon_con_glschedule.setTypes(mon_con_glschedule.getTypes());
		    		  			    		mon_con_glschedule.setCodes(mon_con_glschedule.getCodes());
		    		  			    		mon_con_glschedule.setToTypes(mon_con_glschedule.getToTypes());
		    		  			    		mon_con_glschedule.setToCodes(mon_con_glschedule.getToCodes());
		    		  			    		  
		    		  			    		  System.out.println("the path from MenuNameReader is "+path);
		    		  			    		  req.setAttribute("pageId",path);
		    		  			    		  req.setAttribute("comboStatusType","true");
		    		  			    		  req.setAttribute("comboStatusCode","true");
		    		  			    		  
		    		  			    		  req.setAttribute("moduleobj",gldelegate.getMainMods());
		    		  			    		  String yearmnth="200901";
		    		  			    		  req.setAttribute("MonthlyGlNo", gldelegate.getGlDetForMonth(yearmnth));
		    		  			    		 
		    		  		                  return map.findForward(ResultHelp.getSuccess());
		    		  		    		  }
		    		  		              
		    		  		    		  
		    		  		    		  
		    		  		    		     		  
		    		  		    		  
		    		  		    		  
		    		  		    		  
		    		  		    		  
		    		  		    		  //focus lost of to glno end
		    		  //clear fun start
		    		  if(mon_con_glschedule.getFlag().equalsIgnoreCase("clear")){
		    		  	  
		    		      
		    		      
		    		          
		    		          
		    		     
		    		      path=MenuNameReader.getScreenProperties(mon_con_glschedule.getPageId());
		    		  	  gldelegate=new GLDelegate();
		    		  	  setGLOpeningInitParams(req, path, gldelegate);
		    		  	mon_con_glschedule.setYear("2009");
		    		  	mon_con_glschedule.setMonth("01");
		    		  	 
		    		  	mon_con_glschedule.setTypes("All Types");
		    		  	mon_con_glschedule.setCodes("All Codes");
		    		  	mon_con_glschedule.setToTypes("All Types");
		    		  	mon_con_glschedule.setToCodes("All Codes");
		    		  	  
		    		  	  System.out.println("the path from MenuNameReader is "+path);
		    		  	  req.setAttribute("pageId",path);
		    		  	  req.setAttribute("comboStatusType","true");
		    		  	  req.setAttribute("comboStatusCode","true");
		    		  	  
		    		  	 
		    		  	  req.setAttribute("moduleobj",gldelegate.getMainMods());
		    		  	  String yearmnth="200901";
		    		  	  req.setAttribute("MonthlyGlNo", gldelegate.getGlDetForMonth("200901"));
		    		  	 
		    		  	 
		    		      return map.findForward(ResultHelp.getSuccess());
		    		  }
		    		  		    		  
		    		  		    		  
		    		  		    		  
		    		  		    		  //code for onblur of toglno and clear end

		    		  //code for view button start
		    		
		    		  if(mon_con_glschedule.getFlag().equalsIgnoreCase("view")){
		    		  	  //code for view starts
		    			  
		    			  
		    			  //code for view ends
		    		      
		    		      
		    		          
		    		          
		    		     
		    		      path=MenuNameReader.getScreenProperties(mon_con_glschedule.getPageId());
		    		  	  gldelegate=new GLDelegate();
		    		  	  setGLOpeningInitParams(req, path, gldelegate);
		    		  	mon_con_glschedule.setYear(mon_con_glschedule.getYear());
  			    		mon_con_glschedule.setMonth(mon_con_glschedule.getMonth());
  			    		mon_con_glschedule.setTypes(mon_con_glschedule.getTypes());
  			    		mon_con_glschedule.setCodes(mon_con_glschedule.getCodes());
  			    		mon_con_glschedule.setToTypes(mon_con_glschedule.getToTypes());
  			    		mon_con_glschedule.setToCodes(mon_con_glschedule.getToCodes());
  			    		  
  			    		  System.out.println("the path from MenuNameReader is "+path);
  			    		  req.setAttribute("pageId",path);
  			    		  req.setAttribute("comboStatusType","true");
  			    		  req.setAttribute("comboStatusCode","true");
  			    		  
  			    		  req.setAttribute("moduleobj",gldelegate.getMainMods());
  			    		  String yearmnth="200901";
  			    		  req.setAttribute("MonthlyGlNo", gldelegate.getGlDetForMonth(yearmnth));
  			    		 
  		                  return map.findForward(ResultHelp.getSuccess());
		    		  	  
		    		  	  
		    		  	  
		    		  	  
		    		  	
		    		  }
		    		  
		    		  //code for view button end
		    		  
		    		  
		    		  
		    		  	    		  
		    		 if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }
		    		  
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
		
	
	
		
		
		
		
		
		
		
		
		  *************Gl Monthly Consolidated GlSchedule  end********************** 
		  *************Gl Monthly Consolidated TrialBalance  start********************** 
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyConsolidatedTrialBalanceMenu"))
		{
	      try{
	    	  //MonthlyConsolidatedGlSchedule mon_con_trialbalance=(MonthlyConsolidatedGlSchedule)form;
	    	  MonthlyConsolidatedTrailBalance mon_con_trialbalance=(MonthlyConsolidatedTrailBalance)form;
	       	  
	    	  //System.out.println("******************************==form"+new MonthlyConsolidatedTrailBalance());
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************==typecasted form"+mon_con_trialbalance);
	    	  System.out.println("the page id is "+mon_con_trialbalance.getPageId());
	    	  req.setAttribute("pagenum", mon_con_trialbalance.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(mon_con_trialbalance.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(mon_con_trialbalance.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyConsolidatedTrialBalance")){
			try{
				MonthlyConsolidatedTrailBalance mon_con_trialbalance=(MonthlyConsolidatedTrailBalance)form;
		       	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+mon_con_trialbalance);
		    	  System.out.println("the page id is "+mon_con_trialbalance.getPageId());
		    	  req.setAttribute("pagenum", mon_con_trialbalance.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(mon_con_trialbalance.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(mon_con_trialbalance.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+mon_con_trialbalance.getForward());
		    		  	    		  
		    		 if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }
		    		  
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
		
	
	
		
		
		
		
		
		
		
		
		  *************Gl Monthly Consolidated TrialBalance  end********************** 
	
		  *************Gl Monthly Consolidated RP start********************** 
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyConsolidatedRPMenu"))
		{
	      try{
	    	  MonthlyConsolidatedRP mon_con_rp=(MonthlyConsolidatedRP)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+mon_con_rp);
	    	  System.out.println("the page id is "+mon_con_rp.getPageId());
	    	  req.setAttribute("pagenum", mon_con_rp.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(mon_con_rp.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(mon_con_rp.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyConsolidatedRP")){
			try{
				MonthlyConsolidatedRP mon_con_rp=(MonthlyConsolidatedRP)form;
		       	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+mon_con_rp);
		    	  System.out.println("the page id is "+mon_con_rp.getPageId());
		    	  req.setAttribute("pagenum", mon_con_rp.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(mon_con_rp.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(mon_con_rp.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+mon_con_rp.getForward());
		    		  	    		  
		    		 if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }
		    		  
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
		
	
	
		
		
		
		
		
		
		
		
		  *************Gl Monthly Consolidated RP  end********************** 
		  *************Gl Monthly Consolidated PL start********************** 
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyConsolidatedPLMenu"))
		{
	      try{
	    	  MonthlyConsolidatedPL mon_con_pl=(MonthlyConsolidatedPL)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+mon_con_pl);
	    	  System.out.println("the page id is "+mon_con_pl.getPageId());
	    	  req.setAttribute("pagenum", mon_con_pl.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(mon_con_pl.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(mon_con_pl.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyConsolidatedPL")){
			try{
				MonthlyConsolidatedPL mon_con_pl=(MonthlyConsolidatedPL)form;
		       	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+mon_con_pl);
		    	  System.out.println("the page id is "+mon_con_pl.getPageId());
		    	  req.setAttribute("pagenum", mon_con_pl.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(mon_con_pl.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(mon_con_pl.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+mon_con_pl.getForward());
		    		  	    		  
		    		 if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }
		    		  
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
		
	
	
		
		
		
		
		
		
		
		
		  *************Gl Monthly Consolidated PL  end********************** 
		  *************Gl Monthly Consolidated BalanceSheet start********************** 
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyConsolidatedBalanceSheetMenu"))
		{
	      try{
	    	  MonthlyConsolidatedBalanceSheet mon_con_balsheet=(MonthlyConsolidatedBalanceSheet)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+mon_con_balsheet);
	    	  System.out.println("the page id is "+mon_con_balsheet.getPageId());
	    	  req.setAttribute("pagenum", mon_con_balsheet.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(mon_con_balsheet.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(mon_con_balsheet.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyConsolidatedBalanceSheet")){
			try{
				MonthlyConsolidatedBalanceSheet mon_con_balsheet=(MonthlyConsolidatedBalanceSheet)form;
		       	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+mon_con_balsheet);
		    	  System.out.println("the page id is "+mon_con_balsheet.getPageId());
		    	  req.setAttribute("pagenum", mon_con_balsheet.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(mon_con_balsheet.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(mon_con_balsheet.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+mon_con_balsheet.getForward());
		    		  	    		  
		    		 if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }
		    		  
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
		
	
	
		
		
		
		
		
		
		
		
		  *************Gl Monthly Consolidated BalanceSheet  end********************** 
*/		/*  *************Gl Admin start********************** */
				/*  *************Gl Admin  end********************** */
		/*  *************Gl GLAdmin start********************** */
		if(map.getPath().trim().equalsIgnoreCase("/GL/AdminMenu"))
		{
	      try{
	    	  System.out.println("At 6292=============>");
	    	  GLAdmin admin=(GLAdmin)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+admin);
	    	  System.out.println("the page id is "+admin.getPageId());
	    	  req.setAttribute("pagenum", admin.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(admin.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(admin.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/Admin")){
			
					
			try{
				GLAdmin admin=(GLAdmin)form;
		       	req.setAttribute("msg","");  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+admin);
		    	  System.out.println("the page id is "+admin.getPageId());
		    	  req.setAttribute("pagenum", admin.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(admin.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(admin.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+admin.getForward());
		    		  if(admin.getFlag().equalsIgnoreCase("update")){
		    			  System.out.println("inside the update block");
		    			  GLObject globj=null;
		    			  String[] dataToSet=null;
		    			  String[] radioValues=null;
		    			  String tableName=admin.getTable().toString();
		    			  globj=gldelegate.getTableInfo(tableName);
		    			  String[] tableColNames=globj.getTableColumnNames();
		    			  
		    			  int columnCount=tableColNames.length;
		    			  
		    			  
		    				  
		    				  
		    				  
	    			  
		    			  if(admin.getTable().equalsIgnoreCase("DailyStatus")){
		    				  dataToSet=new String[7];
		    				  radioValues=req.getParameterValues("selectedRadio");
		    				  System.out.println("You selected the radio button==="+radioValues[0]);
		    				  String dataCol1[]=req.getParameterValues("col0");
		    				  String dataCol2[]=req.getParameterValues("col1");
		    				  String dataCol3[]=req.getParameterValues("col2");
		    				  String dataCol4[]=req.getParameterValues("col3");
		    				  String dataCol5[]=req.getParameterValues("col4");
		    				  String dataCol6[]=req.getParameterValues("col5");
		    				  String dataCol7[]=req.getParameterValues("col6");
		    				  dataToSet[0]=dataCol1[Integer.parseInt(radioValues[0])];
		    				  dataToSet[1]=dataCol2[Integer.parseInt(radioValues[0])];
		    				  dataToSet[2]=dataCol3[Integer.parseInt(radioValues[0])];
		    				  dataToSet[3]=dataCol4[Integer.parseInt(radioValues[0])];
		    				  dataToSet[4]=dataCol5[Integer.parseInt(radioValues[0])];
		    				  dataToSet[5]=dataCol6[Integer.parseInt(radioValues[0])];
		    				  dataToSet[6]=dataCol7[Integer.parseInt(radioValues[0])];
		    				  admin.setTrnDate(dataToSet[0]);
		    				  admin.setPostInd(dataToSet[1]);
		    				  admin.setCashClose(dataToSet[2]);
		    				  admin.setDayClose(dataToSet[3]);
		    				  admin.setDeTml(dataToSet[4]);
		    				  admin.setDeUser(dataToSet[5]);
		    				  admin.setDedtTime(dataToSet[6]);
		    				  req.setAttribute("dataToSet", dataToSet);
		    				  req.setAttribute("newTableCols", tableColNames);
		    				  req.setAttribute("newTableName", tableName);
		    				  
		    				  
		    			  }else if(admin.getTable().equalsIgnoreCase("MonthlyStatus")){
		    				  dataToSet=new String[6];
		    				  radioValues=req.getParameterValues("selectedRadio");
		    				  System.out.println("You selected the radio button==="+radioValues[0]);
		    				  String dataCol1[]=req.getParameterValues("col0");
		    				  String dataCol2[]=req.getParameterValues("col1");
		    				  String dataCol3[]=req.getParameterValues("col2");
		    				  String dataCol4[]=req.getParameterValues("col3");
		    				  String dataCol5[]=req.getParameterValues("col4");
		    				  String dataCol6[]=req.getParameterValues("col5");
		    				  
		    				  dataToSet[0]=dataCol1[Integer.parseInt(radioValues[0])];
		    				  dataToSet[1]=dataCol2[Integer.parseInt(radioValues[0])];
		    				  dataToSet[2]=dataCol3[Integer.parseInt(radioValues[0])];
		    				  dataToSet[3]=dataCol4[Integer.parseInt(radioValues[0])];
		    				  dataToSet[4]=dataCol5[Integer.parseInt(radioValues[0])];
		    				  dataToSet[5]=dataCol6[Integer.parseInt(radioValues[0])];
		    				  
		    				  admin.setYrMth(dataToSet[0]);
		    				  admin.setMthPostInd(dataToSet[1]);
		    				  admin.setMthClose(dataToSet[2]);
		    				  
		    				  admin.setMthDeTml(dataToSet[3]);
		    				  admin.setMthDeUser(dataToSet[4]);
		    				  admin.setMthDedtTime(dataToSet[5]);
		    				  req.setAttribute("dataToSet", dataToSet);
		    				  req.setAttribute("newTableCols", tableColNames);
		    				  req.setAttribute("newTableName", tableName);
		    				    
		    				  
		    				  
		    			  }else if(admin.getTable().equalsIgnoreCase("YearlyStatus")){
		    				  dataToSet=new String[7];
		    				  radioValues=req.getParameterValues("selectedRadio");
		    				  System.out.println("You selected the radio button==="+radioValues[0]);
		    				  String dataCol1[]=req.getParameterValues("col0");
		    				  String dataCol2[]=req.getParameterValues("col1");
		    				  String dataCol3[]=req.getParameterValues("col2");
		    				  String dataCol4[]=req.getParameterValues("col3");
		    				  String dataCol5[]=req.getParameterValues("col4");
		    				  String dataCol6[]=req.getParameterValues("col5");
		    				  String dataCol7[]=req.getParameterValues("col6");
		    				  dataToSet[0]=dataCol1[Integer.parseInt(radioValues[0])];
		    				  dataToSet[1]=dataCol2[Integer.parseInt(radioValues[0])];
		    				  dataToSet[2]=dataCol3[Integer.parseInt(radioValues[0])];
		    				  dataToSet[3]=dataCol4[Integer.parseInt(radioValues[0])];
		    				  dataToSet[4]=dataCol5[Integer.parseInt(radioValues[0])];
		    				  dataToSet[5]=dataCol6[Integer.parseInt(radioValues[0])];
		    				  dataToSet[6]=dataCol7[Integer.parseInt(radioValues[0])];
		    				  admin.setYearMth(dataToSet[0]);
		    				  admin.setBrCode(dataToSet[1]);
		    				  admin.setYrPostInd(dataToSet[2]);
		    				  admin.setYrClose(dataToSet[3]);
		    				  admin.setYrDeTml(dataToSet[4]);
		    				  admin.setYrDeUser(dataToSet[5]);
		    				  admin.setYrDedtTime(dataToSet[6]);
		    				  req.setAttribute("dataToSet", dataToSet);
		    				  req.setAttribute("newTableCols", tableColNames);
		    				  req.setAttribute("newTableName", tableName);
		    				  	     
		    			  }
		    			  
		    		  }
		    		  if(admin.getFlag().equalsIgnoreCase("submit")){
		    			  String deDtTime=GLDelegate.getSysDate()+" "+GLDelegate.getSysTime();
		    			  System.out.println("Inside the action the value of deDtTime is_______"+deDtTime);
		    			  String tablename=admin.getTable().toString().trim();
		    			  GLObject[] gl_update=null;
		    			  if(admin.getTable().equalsIgnoreCase("DailyStatus")){
		    			  gl_update=new GLObject[1];
		    			  gl_update[0]=new GLObject();
		    			  gl_update[0].setDate(Validations.convertYMD(admin.getTrnDate().trim()));
		    			  gl_update[0].setPostInd(admin.getPostInd().trim());
		    			  gl_update[0].setCashClose(admin.getCashClose().trim());
		    			  gl_update[0].setDayClose(admin.getDayClose().trim());
		    			  gl_update[0].setDeTml(admin.getDeTml().trim());
		    			  gl_update[0].setDeUser(admin.getDeUser().trim());
		    			  
		    			  gl_update[0].setDeDate(GLDelegate.getSysDate()+""+GLDelegate.getSysTime());
		    			  gl_update[0].setBr_name((String)session.getAttribute("BankLocation"));
		    			  
		    			  }else if(admin.getTable().equalsIgnoreCase("MonthlyStatus")){
		    				  gl_update=new GLObject[1];
		    				  gl_update[0]=new GLObject();
		    				  gl_update[0].setDate(admin.getYrMth().trim());
		    				  gl_update[0].setPostInd(admin.getMthPostInd().trim());
		    				  gl_update[0].setMonthClose(admin.getMthClose().trim());
		    				  gl_update[0].setBr_name((String)session.getAttribute("BankLocation"));
		    				  gl_update[0].setDeTml(admin.getMthDeTml());
		    				  gl_update[0].setDeUser(admin.getMthDeUser());
		    				  gl_update[0].setDeDate(GLDelegate.getSysDate()+""+GLDelegate.getSysTime());
		    			  }else if(admin.getTable().equalsIgnoreCase("YearlyStatus")){
		    				  gl_update=new GLObject[1];
		    				  gl_update[0]=new GLObject();
		    				  gl_update[0].setBr_name("Head Office");
		    				  gl_update[0].setDate(admin.getYearMth());
		    				  gl_update[0].setBr_code(Integer.parseInt(admin.getBrCode().trim()));
		    				  gl_update[0].setPostInd(admin.getYrPostInd().trim());
		    				  gl_update[0].setYearClose(admin.getYrClose().trim());
		    				  gl_update[0].setDeTml(admin.getYrDeTml().trim());
		    				  gl_update[0].setDeUser(admin.getYrDeUser());
		    				  gl_update[0].setDeDate(GLDelegate.getSysDate()+""+GLDelegate.getSysTime());
		    				  
		    				  
		    			  }
		    			  if(gl_update!=null){
								int result=gldelegate.updateTable(tablename.toString().trim(),gl_update);
								if(result==-1)
									req.setAttribute("msg","Not Updated To date falls between other date");
								else if(result==-2)
									req.setAttribute("msg","GL Code Already Used you cannot go for updation");
								else if(result==1)
									req.setAttribute("msg","Records Updated !");
								else 
									req.setAttribute("msg","Records not Updated !");
								
								}
		    			  else{
		    				  
		    				  req.setAttribute("msg","Records not Updated !");
		    			  }
		    			  
		    			  
		    		  }
		    		  
		    		  if(admin.getFlag().equalsIgnoreCase("from_combo")){
		    			  if(admin.getTable().equalsIgnoreCase("GLMaster")){
		    				  admin.setDate(gldelegate.getSysDate());
		    				  req.setAttribute("ShowDate", "ShowDate");
		    			  }
		    			  if(admin.getTable().equalsIgnoreCase("GLKeyParam")){
		    				  req.setAttribute("ShowRadio", "ShowRadio");
		    			  }
		    			  if(admin.getTable().equalsIgnoreCase("Modules")){
		    				  req.setAttribute("ShowRadio", "ShowRadio");
		    			  }
		    			  
		    		  }
		    		  
		    		  //code for view button starts
		    		  if(admin.getFlag().equalsIgnoreCase("view")){
		    			  System.out.println("start of view ++++++++++++++++++++++++++++");
		    			  boolean userchoice=false;
		    			  
		    			  GLObject globj=null;
		    			  GLObject[] gl_object=null;
		    			  Object object[][]=null;
		    			  //Object objrow[][]=null;
		    			  String date=null;
		    			  String tableName=admin.getTable().toString();
		    			  globj=gldelegate.getTableInfo(tableName);
		    			  String[] tableColNames=globj.getTableColumnNames();
		    			  for(int i=0;i<tableColNames.length;i++){
		    					 System.out.println("====Array items===="+tableColNames[i]);
		    			  }
		    			  System.out.println("tablecolumns================"+tableColNames.length);
		    			  
		    			  
		    			  //code for viewing records starts
		    			  try{
		    					if(admin.getTable().equalsIgnoreCase("GLKeyParam") || admin.getTable().equalsIgnoreCase("Modules") ){
		    						if(admin.getUserChoice().equalsIgnoreCase("Gl")){
		  		    				  userchoice=true;
		  		    			  }
		    						System.out.println("Keyparam================");
		    						gl_object=gldelegate.getTableFromDB(tableName,gldelegate.getSysDate(),userchoice);
		    					System.out.println("GLObject[] lenght----2================"+gl_object.length);
		    					}else if(admin.getTable().equalsIgnoreCase("GLMaster") && admin.getDate().length()>0){
		    						date=admin.getDate().trim();
		    						gl_object=gldelegate.getTableFromDB(tableName,date,false);
		    					}else if(admin.getTable().equalsIgnoreCase("GLMaster") && admin.getDate().length()==0){
		    						date=admin.getDate().trim();
		    						gl_object=gldelegate.getTableFromDB(tableName,null,false);
		    					}
		    					
		    					else	
		    						gl_object=gldelegate.getTableFromDB(tableName,gldelegate.getSysDate(),false);
		    					//storing the obj data into the array starts
		    					if(admin.getTable().equalsIgnoreCase("GLPost")){
		    						System.out.println("object array lenght========="+gl_object.length);
		    						System.out.println("object array lenght========="+tableColNames.length);
		    						object=new Object[gl_object.length][tableColNames.length];
		    						

		    						for(int i=0;i<gl_object.length;i++){
		    							
		    								
		    								
		    								object[i][0]="  "+gl_object[i].getAcType();
		    								
		    								object[i][1]="  "+gl_object[i].getGlType();
		    								
		    								object[i][2]="  "+gl_object[i].getGlCode();
		    								
		    								
		    								
		    								object[i][3]="  "+gl_object[i].getTrnType();
		    								
		    								object[i][4]="  "+gl_object[i].getCrDr();
		    								
		    								object[i][5]="  "+String.valueOf(gl_object[i].getMultBy());
		    								
		    								object[i][6]="  "+gl_object[i].getDeTml();
		    								
		    								object[i][7]="  "+gl_object[i].getDeUser();
		    								
		    								
		    								object[i][8]="  "+gl_object[i].getDeDate();
		    								
		    						
		    						}
		    						System.out.println("Object lenght in the action+++++++++++"+object.length);
		    						req.setAttribute("objectArrayData", object);
		    					}
		    					
		    					else if(admin.getTable().equalsIgnoreCase("GLMaster")){//GLmaser
		    						
		    						
		    						
		    						object=new Object[gl_object.length][tableColNames.length];
		    						

		    						
		    						for(int i=0;i<gl_object.length;i++){
		    							//object[i][0]=new Boolean(false);
		    							object[i][0]="  "+gl_object[i].getGlType();
		    							object[i][1]="  "+gl_object[i].getGlCode();
		    							object[i][2]="  "+gl_object[i].getGlName();
		    							object[i][3]="  "+gl_object[i].getShType();
		    							object[i][4]="  "+gl_object[i].getGlStatus();
		    							object[i][5]="  "+gl_object[i].getNormalCD();
		    							object[i][6]="  "+gl_object[i].getFrom_dt();
		    							object[i][7]="  "+gl_object[i].getTo_dt();
		    							object[i][8]="  "+gl_object[i].getDeTml();
		    							object[i][9]="  "+gl_object[i].getDeUser();
		    							object[i][10]="  "+gl_object[i].getDeDate();
		    							
		    						}
		    						req.setAttribute("ShowDate", "ShowDate");
		    						admin.setDate(admin.getDate());
		    						req.setAttribute("objectArrayData", object);
		    					}
		    					else if(admin.getTable().equalsIgnoreCase("GLTransactionType")){//GLtransaction Type
		    						
		    						
		    						
		    						
		    						object=new Object[gl_object.length][tableColNames.length];
		    						

		    						for(int i=0;i<gl_object.length;i++){
		    							//object[i][0]=new Boolean(false);
		    							object[i][0]="  "+gl_object[i].getAcType();
		    							object[i][1]="  "+gl_object[i].getGlType();
		    							object[i][2]="  "+gl_object[i].getGlCode();
		    							
		    							object[i][3]="  "+gl_object[i].getTrnType();
		    							object[i][4]="  "+gl_object[i].getTrnDesc();
		    							object[i][5]="  "+gl_object[i].getDeTml();
		    							object[i][6]="  "+gl_object[i].getDeUser();
		    							object[i][7]="  "+gl_object[i].getDeDate();
		    							
		    							
		    						}
		    						req.setAttribute("objectArrayData", object);
		    					}
		    					else if(admin.getTable().equalsIgnoreCase("GLKeyParam")){//GLKeyParam
		    						
		    						 object=new Object[gl_object.length][tableColNames.length];
		    						

		    						for(int i=0;i<gl_object.length;i++){
		    							//object[i][0]=new Boolean(false);
		    							//object[i][1]="  "+gl_object[i].getModAbbr();
		    							object[i][0]="  "+gl_object[i].getAcType();
		    							object[i][1]="  "+gl_object[i].getCode();
		    							object[i][2]="  "+gl_object[i].getKeyDesc();
		    							object[i][3]="  "+gl_object[i].getGlType();
		    							object[i][4]="  "+gl_object[i].getGlCode();
		    							//object[i][7]="  "+gl_object[i].getGlName();
		    							object[i][5]="  "+gl_object[i].getDeTml();
		    							object[i][6]="  "+gl_object[i].getDeUser();
		    							object[i][7]="  "+gl_object[i].getDeDate();
		    							
		    						}
		    						req.setAttribute("objectArrayData", object);
		    						req.setAttribute("ShowRadio", "ShowRadio");
		    						admin.setUserChoice(admin.getUserChoice());
		    					}
		    					else if(admin.getTable().equalsIgnoreCase("Modules")){//Modules
		    						
		    						
		    						object=new Object[gl_object.length][tableColNames.length];
		    						

		    						for(int i=0;i<gl_object.length;i++){
		    							//object[i][0]=new Boolean(false);
		    							object[i][0]="  "+gl_object[i].getModCode();
		    							object[i][1]="  "+gl_object[i].getModDesc();
		    							object[i][2]="  "+gl_object[i].getModAbbr();
		    							object[i][3]="  "+gl_object[i].getLstAccNO();
		    							object[i][4]="  "+gl_object[i].getMaxRen();
		    							object[i][5]="  "+gl_object[i].getMinBal();
		    							object[i][6]="  "+gl_object[i].getMinPeriod();
		    							object[i][7]="  "+gl_object[i].getOtherProp();
		    							object[i][8]="  "+gl_object[i].getLnkShares();
		    							object[i][9]="  "+gl_object[i].getMaxAmt();
		    							object[i][10]="  "+gl_object[i].getFromDay();
		    							object[i][11]="  "+gl_object[i].getToDay();
		    							object[i][12]="  "+gl_object[i].getTransPerMth();
		    							object[i][13]="  "+gl_object[i].getLstDate();
		    							object[i][14]="  "+gl_object[i].getIntRate();
		    							object[i][15]="  "+gl_object[i].getStdInst();
		    							object[i][16]="  "+gl_object[i].getDeTml();
		    							object[i][17]="  "+gl_object[i].getDeUser();
		    							object[i][18]="  "+gl_object[i].getDeDate();
		    							object[i][19]="  "+gl_object[i].getMaxRenDays();
		    							object[i][20]="  "+gl_object[i].getRenIntRate();
		    							object[i][21]="  "+gl_object[i].getPenRate();
		    							object[i][22]="  "+gl_object[i].getPassBkLines();
		    							object[i][23]="  "+gl_object[i].getCommRate();
		    							object[i][24]="  "+gl_object[i].getMaxCommRate();
		    							object[i][25]="  "+gl_object[i].getVchScrlNo();
		    							object[i][26]="  "+gl_object[i].getChqValidPrd();
		    							object[i][27]="  "+gl_object[i].getMinAmtChq();
		    							object[i][28]="  "+gl_object[i].getMinAmtClg();
		    							object[i][29]="  "+gl_object[i].getMaxAmtChq();
		    							object[i][30]="  "+gl_object[i].getMaxAmtClg();
		    							object[i][31]="  "+gl_object[i].getTopMargin();
		    							object[i][32]="  "+gl_object[i].getLinesPerPage();
		    							object[i][33]="  "+gl_object[i].getBottomMargin();
		    							object[i][34]="  "+gl_object[i].getLstTrfScrlNo();
		    							object[i][35]="  "+gl_object[i].getLstRectNo();
		    							object[i][36]="  "+gl_object[i].getLstVchNo();
		    							object[i][37]="  "+gl_object[i].getInsPrd();
		    							object[i][38]="  "+gl_object[i].getLnCode();
		    					
		    							
		    						}
		    						req.setAttribute("objectArrayData", object);
		    						req.setAttribute("ShowRadio", "ShowRadio");
		    						admin.setUserChoice(admin.getUserChoice());
		    					}
		    					else if(admin.getTable().equalsIgnoreCase("DailyStatus")){//Daily 
		    						
		    						
		    						 object=new Object[gl_object.length][tableColNames.length+1];
		    						

		    						for(int i=0;i<gl_object.length;i++){
		    							//object[i][0]=new Boolean(false);
		    							object[i][0]="  "+gl_object[i].getDate();
		    							object[i][1]="  "+gl_object[i].getPostInd();
		    							object[i][2]="  "+gl_object[i].getCashClose();
		    							object[i][3]="  "+gl_object[i].getDayClose();
		    							object[i][4]="  "+gl_object[i].getDeTml();
		    							object[i][5]="  "+gl_object[i].getDeUser();
		    							object[i][6]="  "+gl_object[i].getDeDate();
		    							
		    						}
		    						req.setAttribute("objectArrayData", object);
		    					}
		    					else if(admin.getTable().equalsIgnoreCase("MonthlyStatus")){//Monthly
		    						
		    						
		    						object=new Object[gl_object.length][tableColNames.length];
		    						

		    						for(int i=0;i<gl_object.length;i++){
		    							//object[i][0]=new Boolean(false);
		    							object[i][0]="  "+gl_object[i].getDate();
		    							object[i][1]="  "+gl_object[i].getPostInd();
		    							object[i][2]="  "+gl_object[i].getMonthClose();
		    							object[i][3]="  "+gl_object[i].getDeTml();
		    							object[i][4]="  "+gl_object[i].getDeUser();
		    							object[i][5]="  "+gl_object[i].getDeDate();
		    						
		    						}
		    						req.setAttribute("objectArrayData", object);
		    					}
		    					else if(admin.getTable().equalsIgnoreCase("DailyConStatus") || admin.getTable().equalsIgnoreCase("MonthlyConStatus")){//Consolidation 
		    						
		    						
		    						object=new Object[gl_object.length][tableColNames.length];
		    						

		    						for(int i=0;i<gl_object.length;i++){
		    							//object[i][0]=new Boolean(false);
		    							object[i][0]="  "+gl_object[i].getDate();
		    							object[i][1]="  "+gl_object[i].getPostInd();
		    							object[i][2]="  "+gl_object[i].getBr_code();
		    							object[i][3]="  "+gl_object[i].getDeTml();
		    							object[i][4]="  "+gl_object[i].getDeUser();
		    							object[i][5]="  "+gl_object[i].getDeDate();
		    							
		    						}
		    						req.setAttribute("objectArrayData", object);
		    					}
		    					else if(admin.getTable().equalsIgnoreCase("YearlyStatus")){//yearly
		    						
		    						
		    						 object=new Object[gl_object.length][tableColNames.length];
		    						

		    						for(int i=0;i<gl_object.length;i++){
		    							//object[i][0]=new Boolean(false);
		    							object[i][0]="  "+gl_object[i].getDate();
		    							object[i][1]="  "+gl_object[i].getBr_code();
		    							object[i][2]="  "+gl_object[i].getPostInd();
		    							object[i][3]="  "+gl_object[i].getYearClose();
		    							object[i][4]="  "+gl_object[i].getDeTml();
		    							object[i][5]="  "+gl_object[i].getDeUser();
		    							object[i][6]="  "+gl_object[i].getDeDate();
		    						
		    						}
		    						req.setAttribute("objectArrayData", object);
		    					}
		    			  
	
		    					
		  //storing the obj data into the array ends
		    					
		    				}catch(Exception e){
		    					e.printStackTrace();
		    				}
		    			  
		    			  //code for viewing records ends
		    			  
		    			  
		    			  
		    			  
		    			  path=MenuNameReader.getScreenProperties(admin.getPageId());
			    		  gldelegate=new GLDelegate();
			    		  setGLOpeningInitParams(req, path, gldelegate);
			    		  
			    		  
			    		  System.out.println("the path from MenuNameReader is "+path);
			    		  req.setAttribute("pageId",path);
			    		  admin.setTable(admin.getTable());	    		  
			    		  req.setAttribute("tableinfo", tableColNames);
		    			  req.setAttribute("nameOfTable", tableName);
		    			  req.setAttribute("tabledatatodisplay", gl_object);
		    			  
		    			  
		    			 
		                  return map.findForward(ResultHelp.getSuccess());
		    			 }
		    		  
		    		  
		    		  //code for view button ends
		    		  
		    		  //code for adding new row to a table starts
		    		  
  		    		  if(admin.getFlag().equalsIgnoreCase("addRow")){
  		    			GLObject globj=null;
		    			  
		    			 
		    			  
		    			  String tableName=admin.getTable().toString();
		    			  globj=gldelegate.getTableInfo(tableName);
		    			  String[] tableColNames=globj.getTableColumnNames();
		    			  
		    			  
		    			  
		    			  path=MenuNameReader.getScreenProperties(admin.getPageId());
			    		  gldelegate=new GLDelegate();
			    		  setGLOpeningInitParams(req, path, gldelegate);
			    		  
			    		  
			    		  System.out.println("the path from MenuNameReader is "+path);
			    		  req.setAttribute("pageId",path);
			    		  admin.setTable(admin.getTable());	    		  
			    		  req.setAttribute("tablecols", tableColNames);
		    			  req.setAttribute("nameOfTable", admin.getTable());
		    			 
		    			  
		    			 
		                  return map.findForward(ResultHelp.getSuccess());
		    			 
		    		  }
		    		  //code for adding new row to a table ends
  		    		  
  		    		  //code for inserting the records into the database starts
  		    		if(admin.getFlag().equalsIgnoreCase("insert")){
  		    			gldelegate=new GLDelegate();
  		    			GLObject[] globj=new GLObject[1];
  		    			int ret;
  		    			String tableName=admin.getTable().toString();
  		    			String[] tabData=req.getParameterValues("txtFld");
  		    			System.out.println("=======Parameter values===length==="+tabData.length);
  		    			for(int i=0;i<tabData.length;i++){
  		    				System.out.println("values====="+tabData[i]);
  		    			}
  		    			if(admin.getTable().equalsIgnoreCase("GLKeyParam")){
  		    				
  		    				globj[0]=new GLObject();
  		    				
  		    				globj[0].setAcType(tabData[0].toString());
  		    				globj[0].setCode(Integer.parseInt(tabData[1].toString()));
  		    				globj[0].setKeyDesc(tabData[2].toString());
  		    				globj[0].setGlType(tabData[3].toString());
  		    				globj[0].setGlCode(tabData[4].toString());
  		    				globj[0].setDeTml(tabData[5].toString());
  		    				globj[0].setDeUser(tabData[6].toString());
  		    				globj[0].setDeDate(tabData[7].toString());
  		    				ret=gldelegate.insertRecord(tableName,globj);
  		    				
  		    				if(ret==1)
				    			req.setAttribute("msg","Record Inserted");
				    		else
				    			req.setAttribute("msg","Record Not Inserted");
  		    				
  		    			}else if(admin.getTable().equalsIgnoreCase("GLMaster")){
  		    				
  		    				globj[0]=new GLObject();
  		    				
  		    				globj[0].setGlType(tabData[0].toString());
  		    				globj[0].setGlCode(tabData[1].toString());
  		    				globj[0].setGlName(tabData[2].toString());
  		    				globj[0].setShType(tabData[3].toString());
  		    				globj[0].setGlStatus(tabData[4].toString());
  		    				globj[0].setNormalCD(tabData[5].toString());
  		    				globj[0].setFrom_dt(tabData[6].toString());
  		    				globj[0].setTo_dt(tabData[7].toString());
  		    				globj[0].setDeTml(tabData[8].toString());
  		    				globj[0].setDeUser(tabData[9].toString());
  		    				globj[0].setDeDate(tabData[10].toString());
  		    				ret=gldelegate.insertRecord(tableName,globj);
  		    				
  		    				if(ret==2)
				    			req.setAttribute("msg","Record Inserted");
				    		else
				    			req.setAttribute("msg","Record Not Inserted");
  		    				
  		    			}if(admin.getTable().equalsIgnoreCase("Modules")){
  		    				
  		    				globj[0]=new GLObject();
  		    				
  		    				globj[0].setModCode(tabData[0].toString());
  		    				globj[0].setModDesc(tabData[1].toString());
  		    				globj[0].setModAbbr(tabData[2].toString());
  		    				globj[0].setDeTml(tabData[16].toString());
  		    				globj[0].setDeUser(tabData[17].toString());
  		    				globj[0].setDeDate(tabData[18].toString());
  		    				ret=gldelegate.insertRecord(tableName,globj);
  		    				
  		    				if(ret==1)
				    			req.setAttribute("msg","Record Inserted");
				    		else
				    			req.setAttribute("msg","Record Not Inserted");
  		    				
  		    			}
  		    			
  		    			
  		    			 path=MenuNameReader.getScreenProperties(admin.getPageId());
			    		  gldelegate=new GLDelegate();
			    		  setGLOpeningInitParams(req, path, gldelegate);
			    		  
			    		  
			    		  System.out.println("the path from MenuNameReader is "+path);
			    		  req.setAttribute("pageId",path);
			    		  admin.setTable(admin.getTable());	    		  
			    		  
		    			  		 
		    			  
		    			 
		                  return map.findForward(ResultHelp.getSuccess());
		    			 
  		    		}
  		    		  
  		    		  
  		    		  //code for inserting the records into the database ends
  		    		
  		    		//code for clear starts
  		    		if(admin.getFlag().equalsIgnoreCase("clear")){
  		    			path=MenuNameReader.getScreenProperties(admin.getPageId());
			    		  gldelegate=new GLDelegate();
			    		  setGLOpeningInitParams(req, path, gldelegate);
			    		  
			    		  
			    		  System.out.println("the path from MenuNameReader is "+path);
			    		  req.setAttribute("pageId",path);
			    		  admin.setTable(admin.getTable());	    		  
			    		  
		    			  		 
		    			  
		    			 
		                  return map.findForward(ResultHelp.getSuccess());
		    			 
  		    		}
  		    		
  		    		//code for clear ends
		    		  	    		  
		    		/* if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }*/
		    		  
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
		/* **************code for GLAdmin ends here********************* */
	/* **********************GL RBI Form9 Reports start************ 
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm9ReportMenu"))
		{
	      try{
	    	  RBIForm9Reports form9_reports=(RBIForm9Reports)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+form9_reports);
	    	  System.out.println("the page id is "+form9_reports.getPageId());
	    	  req.setAttribute("pagenum", form9_reports.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(form9_reports.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(form9_reports.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm9Report")){
			try{
				RBIForm9Reports form9_reports=(RBIForm9Reports)form;
		       	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+form9_reports);
		    	  System.out.println("the page id is "+form9_reports.getPageId());
		    	  req.setAttribute("pagenum", form9_reports.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(form9_reports.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(form9_reports.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+form9_reports.getForward());
		    		  //code for view starts
		    		  if(form9_reports.getFlag().equalsIgnoreCase("view")){
		    			  System.out.println("start of view================");
		    			String[][] table_data=null;
		    			try{
		    				table_data=gldelegate.getRbiForm9Report(form9_reports.getYear().toString().trim(),form9_reports.getMonth().toString().trim());
		    				System.out.println("table data length================"+table_data.length);
		    			}catch(Exception e){
		    				e.printStackTrace();
		    			}
		    			req.setAttribute("rbiForm9ReportData", table_data);
		    		  }
		    		  //code for view ends
		    		  	    		  
		    		 if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }
		    		  
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
		
	
	
		
		
		
		
		
		
		
		
		  *************Gl RBI Form9 Reports  end********************** 
		 **********************GL RBI Form9 Input Updation start************ 
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm9InputUpdationMenu"))
		{
	      try{
	    	  RBIForm9InputUpdation form9_input_updation=(RBIForm9InputUpdation)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+form9_input_updation);
	    	  System.out.println("the page id is "+form9_input_updation.getPageId());
	    	  req.setAttribute("pagenum", form9_input_updation.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(form9_input_updation.getPageId()))
	    	  {  
	    		  path=MenuNameReader.getScreenProperties(form9_input_updation.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  //req.setAttribute("form9inputcodes", gldelegate.getRbiForm9InputCodes());
	    		  //req.setAttribute("form9inputcodesinsert", "insert");
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm9InputUpdation")){
			try{
				RBIForm9InputUpdation form9_input_updation=(RBIForm9InputUpdation)form;
		       	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+form9_input_updation);
		    	  System.out.println("the page id is "+form9_input_updation.getPageId());
		    	  req.setAttribute("pagenum", form9_input_updation.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(form9_input_updation.getPageId()))
		    	  {
		    		 
		    		  
		    		  System.out.println("The button valuee is"+form9_input_updation.getForward());
		    		  
		    		  if(form9_input_updation.getFlag().equalsIgnoreCase("insert")){
		    			  System.out.println("inside insert if condition");
		    			  String[][] codename=null;
		    			  codename=gldelegate.getRbiForm9InputCodes();
		    			  req.setAttribute("form9inputcodes", codename);
		    			  req.setAttribute("form9inputcodesinsert", "insert");
		    			  System.out.println("******************************=="+form);
				    	  System.out.println("the page path is "+map.getPath().trim());
				    	  System.out.println("******************************=="+form9_input_updation);
				    	  System.out.println("the page id is "+form9_input_updation.getPageId());
				    	  req.setAttribute("pagenum", form9_input_updation.getPageId());
				    	  
					      			 
						  
		    		  }
		    		  if(form9_input_updation.getFlag().equalsIgnoreCase("submit")){
		    			  System.out.println("inside submit ==============="); 
		    			  
		    		  }
		    		  if(form9_input_updation.getFlag().equalsIgnoreCase("code")){
		    			  String codeName=form9_input_updation.getCode();
		    			  String[][] codename=null;
		    			  codename=gldelegate.getRbiForm9InputCodes();
		    			  for(int i=0;i<codename.length;i++){
		    				  if(codename[i][0].equalsIgnoreCase(codeName)){
		    					  form9_input_updation.setDescription1(codename[i][1].toString());  
		    				  }
		    			  }
		    			  req.setAttribute("form9inputcodes", codename);
		    			  req.setAttribute("form9inputcodesinsert", "insert");
		    			  form9_input_updation.setDescription(form9_input_updation.getCode());
		    			  
		    		  }
		    		  
		    		 if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }
		    		  path=MenuNameReader.getScreenProperties(form9_input_updation.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
	
	
		
		
		
		
		
		
		
		
		  *************Gl RBI Form9 INput UPdation  end********************** 
	*/
		
		/* ********************RBI Form1 Posting starts****************** */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLForm1PostingMenu"))
		{
	      try{
	    	  Form1Posting form1_post=(Form1Posting)form;
	    	  req.setAttribute("msg","");
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+form1_post);
	    	  System.out.println("the page id is "+form1_post.getPageId());
	    	  req.setAttribute("pagenum", form1_post.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(form1_post.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(form1_post.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  form1_post.setFromDate(gldelegate.getSysDate());
	    		  form1_post.setToDate(gldelegate.getSysDate());
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLForm1Posting")){
			try{
				Form1Posting form1_post=(Form1Posting)form;
				//monthly_post.setValidations("");
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+form1_post);
		    	  System.out.println("the page id is "+form1_post.getPageId());
		    	  req.setAttribute("pagenum", form1_post.getPageId());
		    	  if(MenuNameReader.containsKeyScreen(form1_post.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(form1_post.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  String string_date_from=null;
		    		  String string_date_to=null;
		    		  String dlystatus[][]=null;
		    		  Form1Object form1_object=new Form1Object();
		    		  if(form1_post.getToDate()!=null){
		    			  //req.setAttribute("Form1Posting", "NoPosting");
		    				string_date_from=form1_post.getFromDate().trim();
		    				string_date_to=form1_post.getToDate().trim();
		    				int int_return_value=0;
		    				String result_value[]=null;
		    			
		    				try{
		    					System.out.println("chechk date="+string_date_from);
		    					string_date_from = Validations.checkDate(string_date_from);
		    				}catch(Exception exception_date_from){
		    					System.out.println("asd="+string_date_from);
		    					exception_date_from.printStackTrace();
		    					string_date_from="E";
		    					}
		    				try{
		    					string_date_to = Validations.checkDate(string_date_to);
		    				}catch(Exception exception_date_to){
		    					string_date_to="E";
		    				}
		    			
		    				
		    					form1_object.setFromDate(Validations.convertYMD(string_date_from));
		    					form1_object.setToDate(Validations.convertYMD(string_date_to));
		    					form1_object.setDeUser(gluser);
		    					form1_object.setDeTml(gltml);
		    					form1_object.setDeDate(Validations.convertYMD(gldelegate.getSysDate())+gldelegate.getSysTime());
		    					try{
		    						result_value=gldelegate.checkRBITransaction(form1_object);
		    						 System.out.println("result_value="+result_value[0]);
		    					if(result_value[0].equals("0")){
		    						req.setAttribute("msg","You Cannot do processing marking date table not updted");
		    					}
		    					else if(result_value[0].equals("2")){
		    						int recurring_days=Integer.parseInt(result_value[2]);
		    						if(result_value[1].equals("2"))
		    							req.setAttribute("msg","You Cannot do Processing Marking date table doesnot have two entries");
		    						else{
		    							if((Validations.dayCompare(result_value[1],string_date_from)>recurring_days) || (Validations.dayCompare(result_value[1],string_date_from)<=0))
		    								req.setAttribute("msg","Processing should start between  "+Validations.addDays(result_value[1],1)+" and "+Validations.addDays(result_value[1],recurring_days)+" ");
		    							else{
		    								req.setAttribute("Form1Posting", "Posting");
		    							}
		    						}
		    					}						
		    					else if(result_value[0].equals("1")){
		    						if(result_value[1].equals("1")){
		    							System.out.println("only one day");
		    							req.setAttribute("Form1Posting", "Posting");
		    						}
		    						else if(Validations.dayCompare(result_value[1],string_date_from)<=0){
		    							req.setAttribute("msg","Posting already Done. Do You want to overwrite the Posted Entries  \n Entries already Exists till  "+result_value[1]+" and Entries will be deleted from RBITransaction  Do you want to Continue?");
		    							req.setAttribute("Form1Posting", "Posting");
		    						
		    					}
		    					}
		    						
		    					else
		    						req.setAttribute("msg","You Cannot Do Processing Entries exists only till "+result_value[1]);
		    					
		    				}catch(Exception E){E.printStackTrace();}
		    				
		    			}
		    		  
		    		  if(form1_post.getFlag().equalsIgnoreCase("submit")){
		    			  int re=0;
		    			  try{
		    			  re=gldelegate.Form1Posting(form1_object);
		    			  if(re!=1)
		    				  req.setAttribute("msg","Error in Posting");
		    				else
		    					req.setAttribute("msg","Posting Done");
		    			  }catch(Exception e){
		    				  e.printStackTrace();
		    			  }
		    		  }
		    	  }else
		    	  {
		    		  path=MenuNameReader.getScreenProperties("0000");
		    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
		    		  return map.findForward(ResultHelp.getError());
		    	  }
			
		
		    		  path=MenuNameReader.getScreenProperties(form1_post.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  return map.findForward(ResultHelp.getSuccess());
		    	  
		                  
			   
		    		  
}
		      catch(Exception e)
		      {
		    	  path=MenuNameReader.getScreenProperties("0000");
		    	  setErrorPageElements(""+e, req, path);
		    	  return map.findForward(ResultHelp.getError());
		      }
		}


		/* ********************RBI Form1 Posting ends*********************** */

		/* **********************GL RBI Form9 Reports start************ */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm9ReportMenu"))
		{
	      try{
	    	  RBIForm9Reports form9_reports=(RBIForm9Reports)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+form9_reports);
	    	  System.out.println("the page id is "+form9_reports.getPageId());
	    	  req.setAttribute("pagenum", form9_reports.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(form9_reports.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(form9_reports.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm9Report")){
			
			try{
				
				RBIForm9Reports form9_reports=(RBIForm9Reports)form;
		       	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+form9_reports);
		    	  System.out.println("the page id is "+form9_reports.getPageId());
		    	  req.setAttribute("pagenum", form9_reports.getPageId());
		    	  
		    	  if(MenuNameReader.containsKeyScreen(form9_reports.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(form9_reports.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  int main_int=-1,sub_int=-1,tempcode=0,length=0,length1=0,main_int1=-1,sub_int1=-1;
		    		  
		    		  System.out.println("The button valuee is"+form9_reports.getForward());
		    		  //code for view starts
		    		  if(form9_reports.getFlag().equalsIgnoreCase("view"))
		    		  {
		    			  System.out.println("start of view================");
		    			  String[][] table_data=null;
		    			try
		    			{
		    				table_data=gldelegate.getRbiForm9Report(form9_reports.getYear().toString().trim(),form9_reports.getMonth().toString().trim());
		    				System.out.println("table data length================"+table_data.length);
		    				
		    				/*Object data[] = new Object[3];
		    				Object data1[] = new Object[3];
		    				String data_lak[] = new String[table_data.length];
		    				for(int j=0;j<3;j++)
		    					data[j]="";
		    				for(int j=0;j<3;j++)
		    					data1[j]="";
		    				if(table_data!=null){
		    					int i=0;
		    					for(i=0;i<table_data.length-1;i++)
		    					{
		    						if(!((table_data[i][0].endsWith("00")) || (table_data[i][0].endsWith("0000")) || (table_data[i][0].endsWith("000000"))))
		    							data_lak[i] = "1 ";
		    							//dtm.addRow(table_data[i]);
		    					
		    						if((table_data[i][0].endsWith("00")) && !(table_data[i][0].endsWith("0000")) &&  !(table_data[i][0].endsWith("000000"))){
		    							if(sub_int!=-1 && i!=0){
		    								//dtm.addRow(table_data[sub_int]);
		    								data_lak[i] = "2 ";
		    								sub_int=-1;
		    								//dtm.addRow(data);
		    							}
		    							if(!(table_data[i][0].startsWith(table_data[i+1][0].substring(0,6)))){
		    								//dtm.addRow(table_data[i]);
		    								data_lak[i] = "3 ";
		    							}
		    							else{
		    								data1[0]=table_data[i][0];data1[1]=table_data[i][1];data1[2]=" ";
		    								data_lak[i] = "Total " ;
		    								//dtm.addRow(data1);
		    								//table_data[i][0]="sub int"+table_data[i][0];
		    								//table_data[i][1]="Total ";
		    								sub_int=i;
		    							}
		    						}
		    						
		    						if(table_data[i][0].endsWith("0000") && !(table_data[i][0].endsWith("000000"))){
		    							if(sub_int!=-1){
		    								data_lak[i]="Total04";
		    							//	dtm.addRow(table_data[sub_int]);
		    								sub_int=-1;
		    							}
		    							if(main_int!=-1 && i!=0){
		    								data_lak[i]="Total03";
		    							//	dtm.addRow(table_data[main_int]);
		    							//	dtm.addRow(data);
		    								main_int=-1;
		    							}
		    							if(!(table_data[i][0].startsWith(table_data[i+1][0].substring(0,3)))){
		    								data_lak[i]="Total02";
		    							//		dtm.addRow(table_data[i]);
		    							//		dtm.addRow(data);
		    							}
		    							else{
		    								data1[0]=table_data[i][0];data1[1]=table_data[i][1];data1[2]=" ";
		    								data_lak[i]="Total01";
		    							//	dtm.addRow(data1);
		    								//table_data[i][0]="main_int"+table_data[i][0];
		    							//	table_data[i][1]="Total ";
		    								main_int=i;
		    							}
		    						}
		    						if(table_data[i][0].endsWith("000000")){
		    							if(sub_int!=-1){
		    								data_lak[i]="Total10";
		    							//	dtm.addRow(table_data[sub_int]);
		    								sub_int=-1;
		    							}
		    							if(sub_int1!=-1){
		    								data_lak[i]="Total11";
		    							//	dtm.addRow(table_data[sub_int1]);
		    								sub_int1=-1;
		    							}
		    							if(main_int!=-1 && i!=0){
		    							//	dtm.addRow(table_data[main_int]);
		    							//	dtm.addRow(data);
		    								main_int=-1;
		    							}
		    							if(main_int1!=-1){
		    								data_lak[i]=" ";
		    							//	dtm.addRow(data);
		    							//	dtm.addRow(table_data[main_int1]);
		    							//	dtm.addRow(data);
		    								main_int1=-1;
		    							}
		    							if(!(table_data[i][0].startsWith(table_data[i+1][0].substring(0,1)))){
		    								data_lak[i]=" ";
		    							//		dtm.addRow(table_data[i]);
		    							//		dtm.addRow(data);
		    							}
		    							else{
		    								data1[0]=table_data[i][0];data1[1]=table_data[i][1];data1[2]=" ";
		    								data_lak[i]="Total"+i+"";
		    							//	dtm.addRow(data1);
		    								//table_data[i][0]="main_int1"+table_data[i][0];
		    							//	table_data[i][1]="Total ";
		    								main_int1=i;
		    							}
		    						}
		    					}
		    					req.setAttribute("Lakhs", data_lak);
		    				}
		    			*/
		    				
		    				req.setAttribute("rbiForm9ReportData", table_data);
		    			}
		    			catch(Exception e)
		    			{
		    				e.printStackTrace();
		    			}
		    			
		    		  }
		    		  //code for view ends
		    		  	    		  
		    		/* if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }*/
		    		  
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
		
	
		
		/*  *************Gl RBI Form9 Reports  end********************** */
		/* **********************GL RBI Form9 Input Updation start************ */
		
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm9InputUpdationMenu"))
		{
	      try
	      {
	    	  RBIForm9InputUpdation form9_input_updation=(RBIForm9InputUpdation)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+form9_input_updation);
	    	  System.out.println("the page id is "+form9_input_updation.getPageId());
	    	  req.setAttribute("pagenum", form9_input_updation.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(form9_input_updation.getPageId()))
	    	  {  
	    		  path=MenuNameReader.getScreenProperties(form9_input_updation.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  req.setAttribute("form9inputcodes", gldelegate.getRbiForm9InputCodes());
	    		  req.setAttribute("form9inputcodesinsert", "insert");
	    		  req.setAttribute("form9updatecodes",gldelegate.getForm9LinkCodes(gldelegate.getSysDate()));
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm9InputUpdation")){
			
			try
			{
				RBIForm9InputUpdation form9_input_updation=(RBIForm9InputUpdation)form;
		       	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+form9_input_updation);
		    	  System.out.println("the page id is "+form9_input_updation.getPageId());
		    	  req.setAttribute("pagenum", form9_input_updation.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(form9_input_updation.getPageId()))
		    	  {
		    		 
		    		  
		    		  System.out.println("The button valuee is"+form9_input_updation.getForward());
		    		  
		    		  if(form9_input_updation.getFlag().equalsIgnoreCase("insert"))
		    		  {
		    			  System.out.println("inside insert if condition");
		    			  String[][] codename=null;
		    			  codename=gldelegate.getRbiForm9InputCodes();
		    			  req.setAttribute("form9inputcodes", codename);
		    			  req.setAttribute("form9inputcodesinsert", "insert");
		    			  System.out.println("******************************=="+form);
				    	  System.out.println("the page path is "+map.getPath().trim());
				    	  System.out.println("******************************=="+form9_input_updation);
				    	  System.out.println("the page id is "+form9_input_updation.getPageId());
				    	  req.setAttribute("pagenum", form9_input_updation.getPageId());
				    	  
					      			 
						  
		    		  }
		    		  if(form9_input_updation.getFlag().equalsIgnoreCase("submit"))
		    		  {
		    			  System.out.println("inside submit ==============="); 
		    			  
		    			  String[] code = req.getParameterValues("description");
		    			  String[] description = req.getParameterValues("description1");
		    			  
		    			  String str;
		    				int j=1,ret=0;
		    				String codearray[][]=new String [code.length][2];
		    				
		    				for(int i=0;i<code.length;i++){
		    					
		    					str=code[i].toString();
		    					System.out.println("str="+str+"sdnfskd");
		    					if(str==null || str.trim().equals("")){
		    						req.setAttribute("msg","Please update all the entries");
		    						j=0;
		    					}
		    				}
		    				if(j==1){
		    					System.out.println("codelen="+code.length);
		    					
		    					for(int i=0;i<code.length;i++){
		    						codearray[i][0]=code[i].toString().trim();
		    						codearray[i][1]=gldelegate.getSysDate();
		    						System.out.println("i="+i);
		    					}
		    					try
		    					{
		    						//int int_result=JOptionPane.showConfirmDialog(this,"Data Ok....");
		    						//if(int_result==JOptionPane.YES_OPTION)
		    							
		    							ret=gldelegate.updateFormLinkdetails(codearray,1);
		    						
		    						if(ret==1)
		    						{
		    							req.setAttribute("msg","Sucessfully Updated");
		    						}
		    					}
		    					catch(Exception ae)
		    					{
		    						req.setAttribute("msg","Not updated");
		    						ae.printStackTrace();
		    					}
		    				}
		    			  
		    			  
		    		  }
		    		  if(form9_input_updation.getFlag().equalsIgnoreCase("code")){
		    			  String codeName=form9_input_updation.getCode();
		    			  String[][] codename=null;
		    			  codename=gldelegate.getRbiForm9InputCodes();
		    			  for(int i=0;i<codename.length;i++){
		    				  if(codename[i][0].equalsIgnoreCase(codeName)){
		    					  form9_input_updation.setDescription1(codename[i][1].toString());  
		    				  }
		    			  }
		    			  req.setAttribute("form9inputcodes", codename);
		    			  req.setAttribute("form9inputcodesinsert", "insert");
		    			  form9_input_updation.setDescription(form9_input_updation.getCode());
		    			  
		    		  }
		    		  
		    		/* if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }*/
		    		  path=MenuNameReader.getScreenProperties(form9_input_updation.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  req.setAttribute("form9updatecodes",gldelegate.getForm9LinkCodes(gldelegate.getSysDate()));
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
		
	
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm9PostingMenu"))
		{
			try{
				RBIForm9Posting form9Posting = (RBIForm9Posting)form;
				
				if(MenuNameReader.containsKeyScreen(form9Posting.getPageId()))
				{
					path = MenuNameReader.getScreenProperties(form9Posting.getPageId());
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
			catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm9Posting"))
		{
			try{
				RBIForm9Posting form9Posting = (RBIForm9Posting)form;
				String linkdetails[][]=null;
				if(MenuNameReader.containsKeyScreen(form9Posting.getPageId()))
				{
					gldelegate=new GLDelegate();
					String firstdate=null,lastdate=null,date=null,lastfriday=null;
			 		String ret[][]=null;
			 		String amount[][]=null;
			 		int month,index=0,diff=0,days=0;
			 		StringTokenizer st=null;
			 		GregorianCalendar  gcal;
			 		gcal=new GregorianCalendar();
					gcal.set(Integer.parseInt(form9Posting.getYear().toString().trim()),Integer.parseInt(form9Posting.getMonth().toString().trim())-1,1);
					firstdate="01"+"/"+form9Posting.getMonth().toString().trim()+"/"+form9Posting.getYear().toString().trim();
					lastdate=Validations.lastDayOfMonth(firstdate);
					try 
					{
						
						if(form9Posting.getFlag().equalsIgnoreCase("monthdet"))
						{
						st= new StringTokenizer(Validations.checkDate(lastdate),"/");
						
						int dd=Integer.parseInt(st.nextToken());
						int mm=Integer.parseInt(st.nextToken());
						int yy=Integer.parseInt(st.nextToken());
						gcal.set(yy,mm-1,dd);
						System.out.println(gcal);
						index=gcal.get(Calendar.DAY_OF_WEEK);
						System.out.println("index="+index);
						if(index==7)
							days=-1;
						if(index==6)
							days=0;
						if(index==5)
							days=-6;
						if(index==4)
							days=-5;
						if(index==3)
							days=-4;
						if(index==2)
							days=-3;
						if(index==1)
							days=-2;
						lastfriday=Validations.addDays(lastdate,days);
						lastfriday1=lastfriday;
						System.out.println("last friday="+lastfriday);
						System.out.println("last date="+lastdate);
						diff=Validations.dayCompare(lastfriday,gldelegate.getSysDate());
						System.out.println("diff="+diff);
						if(diff<0)
							req.setAttribute("msg","You cannot do the processing till :"+lastfriday);
						else
						{
							try
							{
								linkdetails = null;
								linkdetails = gldelegate.getForm9LinkCodes(lastfriday);
								
								if(linkdetails!=null)
								{
									req.setAttribute("Form9LinkCodes",linkdetails);
								
									/*for(int i=0;i<linkdetails.length;i++){
									dtm.addRow(linkdetails[i]);
									}*/
									amount=new String[linkdetails.length][3];
									//checkForm9Posting(lastfriday);
									int ret1=gldelegate.checkForm9Transaction(lastfriday);
							 		if(ret1==0)
							 		{
							 			//button_process.setEnabled(true);
							 			//enableSubmitButton(true);
							 			//button_process.requestFocus();
							 		}
							 		if(ret1==1)
							 		{
							 			req.setAttribute("msg","Posing done do you want to overwrite?");
							 			/*if(ret==JOptionPane.YES_OPTION){
							 				//button_process.setEnabled(true);
							 				//enableSubmitButton(true);
								 			//button_process.requestFocus();
							 			}*/
							 		}
								}
								if(linkdetails==null)
								{
									req.setAttribute("msg","Link Details Not Updated You Cannot Do Posting");
								}
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
					  }
					  else if(form9Posting.getFlag().equalsIgnoreCase("process"))
					  {
						String[] amountval = req.getParameterValues("amount");
						String[] glcode = req.getParameterValues("glCode");  
						
						System.out.println("====>"+amountval[0]);
						System.out.println("---amt len--"+amountval.length);
						
						if(amountval.toString()!=null)
						{
							if(amountval.length==glcode.length)
							{
								amount=new String[glcode.length][3];
								
								for(int i=0;i<amountval.length;i++)
								{
									amount[i][0]=glcode[i];
									amount[i][1]=amountval[i];
								}
								if(gldelegate.Form9Posting(lastfriday1,gluser,gltml,Validations.convertYMD(gldelegate.getSysDate())+" "+gldelegate.getSysTime(),amount)==false)
								{
									req.setAttribute("msg","Error in Posting");
								}
								else
								{
									req.setAttribute("msg","Posting Done");
								}
							}
							else
							{
								req.setAttribute("msg","Please Enter All Fields");
							}
						}
						else
						{
							req.setAttribute("msg","Please Enter All The Fields");
						}
						
						
					  }
						
						
						
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					
					
					path = MenuNameReader.getScreenProperties(form9Posting.getPageId());
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
			catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		/*  *************Gl RBI Form9 INput UPdation  end********************** */
	
		
		
		/* **********************GL RBI CRR SLR REPORT start************ */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBICRRSLRReportMenu"))
		{
	      try{
	    	  RBICRRSLRReports crrslr_reports=(RBICRRSLRReports)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+crrslr_reports);
	    	  System.out.println("the page id is "+crrslr_reports.getPageId());
	    	  req.setAttribute("pagenum", crrslr_reports.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(crrslr_reports.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(crrslr_reports.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBICRRSLRReport")){
			try{
				RBICRRSLRReports crrslr_reports=(RBICRRSLRReports)form;
		       	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+crrslr_reports);
		    	  System.out.println("the page id is "+crrslr_reports.getPageId());
		    	  req.setAttribute("pagenum", crrslr_reports.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(crrslr_reports.getPageId()))
		    	  {
		    		  
		    		  
		    		  
		    		  System.out.println("The button valuee is"+crrslr_reports.getForward());
		    		  
		    		  if(crrslr_reports.getFlag().equalsIgnoreCase("view")){
		    			  System.out.println("inside view of the action=====");
		    			  String yr=crrslr_reports.getYear();
		    			  System.out.println("year of the action====="+yr);
		    			  String mth=crrslr_reports.getMonth();
		    			  System.out.println("month of the action====="+mth);
		    			  String yearmth=mth.toString()+"/"+yr.toString();
		    			  req.setAttribute("yearmonth", yearmth);
		    			  req.setAttribute("year", (String)crrslr_reports.getYear());
		    			  req.setAttribute("month",(String)crrslr_reports.getMonth());
		    			  req.setAttribute("CrrSlrtabledata", gldelegate.getRbiCrrSlrReport(yr, mth, 0));
		    			  
		    		  }
		    		  	    		  
		    		/* if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }*/
		    		  path=MenuNameReader.getScreenProperties(crrslr_reports.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		/*  *************Gl RBI CRR SLR REPORT  end********************** */
		/* **********************GL RBI CRR SLR Sub Schedule start************ */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBICRRSLRSubScheduleMenu"))
		{
	      try{
	    	  RBICRRSLRSubSchedule crrslr_subschedule=(RBICRRSLRSubSchedule)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+crrslr_subschedule);
	    	  System.out.println("the page id is "+crrslr_subschedule.getPageId());
	    	  req.setAttribute("pagenum", crrslr_subschedule.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(crrslr_subschedule.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(crrslr_subschedule.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBICRRSLRSubSchedule")){
			try{
				RBICRRSLRSubSchedule crrslr_subschedule=(RBICRRSLRSubSchedule)form;
		       	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+crrslr_subschedule);
		    	  System.out.println("the page id is "+crrslr_subschedule.getPageId());
		    	  req.setAttribute("pagenum", crrslr_subschedule.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(crrslr_subschedule.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(crrslr_subschedule.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+crrslr_subschedule.getForward());
		    		  
		    		  //focus lost of month start
		    		  if(crrslr_subschedule.getFlag().equalsIgnoreCase("from_month")){
		                     
		                     String codeName[][]=null;
		                    codeName=gldelegate.getCodeSubSchedules(crrslr_subschedule.getYear(), crrslr_subschedule.getMonth());
		                    
		                    
		                    
			                  path=MenuNameReader.getScreenProperties(crrslr_subschedule.getPageId());
				    		  gldelegate=new GLDelegate();
				    		  setGLOpeningInitParams(req, path, gldelegate);
				    		  crrslr_subschedule.setYear(crrslr_subschedule.getYear());
				    		  crrslr_subschedule.setMonth(crrslr_subschedule.getMonth());
				    		  req.setAttribute("codename", codeName);
				    		  
				    		 
				    		  System.out.println("the path from MenuNameReader is "+path);
				    		  req.setAttribute("pageId",path);
				    		  
				    		  
				    		  
				    		  
				    		  
			                  return map.findForward(ResultHelp.getSuccess());
			    		  }
			        
		    		  
		    		  
		    		  //focus lost of month end
		    		  if(crrslr_subschedule.getFlag().equalsIgnoreCase("view")){
		    			  System.out.println("inside view of the action=====");
		    			  String yr=crrslr_subschedule.getYear();
		    			  System.out.println("year of the action====="+yr);
		    			  String mth=crrslr_subschedule.getMonth();
		    			  System.out.println("month of the action====="+mth);
		    			  String yearmth=mth.toString()+"/"+yr.toString();
		    			  req.setAttribute("yearmonth", yearmth);
		    			  req.setAttribute("year", (String)crrslr_subschedule.getYear());
		    			  req.setAttribute("month",(String)crrslr_subschedule.getMonth());
		    			  req.setAttribute("CrrSlrtabledata", gldelegate.getRbiCrrSlrReport(yr, mth, Integer.parseInt(crrslr_subschedule.getCode())));
		    			  
		    		  }
		    		  
		    		  
		    		  
		    		  	    		  
		    		/* if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }*/
		    		  
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
		/*  *************Gl RBI CRR SLR sub schedule  end********************** */
		/* **********************GL RBI Form 1 Reports start************ */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm1ReportsMenu"))
		{
	      try{
	    	  RBIForm1Reports form1_reports=(RBIForm1Reports)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+form1_reports);
	    	  System.out.println("the page id is "+form1_reports.getPageId());
	    	  req.setAttribute("pagenum", form1_reports.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(form1_reports.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(form1_reports.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm1Reports")){
			try{
				RBIForm1Reports form1_reports=(RBIForm1Reports)form;
		       	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+form1_reports);
		    	  System.out.println("the page id is "+form1_reports.getPageId());
		    	  req.setAttribute("pagenum", form1_reports.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(form1_reports.getPageId()))
		    	  {
		    		  String[] Form1A={"Code", "Name", "First Date", "Second Date", "Third Date"};
	    			  String[] Form1BC={"Serial No", "date", "NDTL", "Required", "Actually", "Defict", "Surplus", "CRR Interest"}; 
		    		  System.out.println("The button valuee is"+form1_reports.getForward());
		    		  
		    		  
		    		  if(form1_reports.getFlag().equalsIgnoreCase("Reports")){
		    			  
		    			  
		    			  String reportName=form1_reports.getReportName();
		    			  if(reportName.equalsIgnoreCase("Form1A")){
		    				  req.setAttribute("reportTable", Form1A);
		    			  }else{
		    				  req.setAttribute("reportTable", Form1BC);
		    			  }
		    			  
		    		  }
		    		  
		    		  if(form1_reports.getFlag().equalsIgnoreCase("view")){
		    			//code for view starts
		    			  String reportName=form1_reports.getReportName();
		    			  String table_data[][]=null;
		    			  String fridays[]=null;
		    			  try{
		    					if(reportName.equalsIgnoreCase("Form1A"))
		    						table_data=gldelegate.getRbiForm1AReport(form1_reports.getYear().toString().trim(),form1_reports.getMonth().toString().trim());
		    					else if (reportName.equalsIgnoreCase("Form1B"))
		    						table_data=gldelegate.getRbiForm1BCReport(form1_reports.getYear().toString().trim(),form1_reports.getMonth().toString().trim(),1);
		    					else if (reportName.equalsIgnoreCase("Form1C"))
		    						table_data=gldelegate.getRbiForm1BCReport(form1_reports.getYear().toString().trim(),form1_reports.getMonth().toString().trim(),2);
		    					//System.out.println("RBI Form1Reports tabledata lenght==========="+table_data.length);
		    					if(table_data==null)
		    						form1_reports.setValidations("Records Not Found");
		    					else{
		    						if(reportName.equalsIgnoreCase("Form1A")){
		    							Object[] obj=null;
		    						    fridays=gldelegate.getRbiMarkingFridays(form1_reports.getYear().toString().trim(),form1_reports.getMonth().toString().trim());
		    							int i=0;
		    							if(fridays!=null){
		    									if(fridays.length==1){
		    										//dtm.setDataVector(null,new Object[]{"Code","Name",fridays[i]," ","  "});
		    									}else if(fridays.length==2){
		    										//dtm.setDataVector(null,new Object[]{"Code","Name",fridays[i],fridays[i+1]});
		    									}else if(fridays.length==3){
		    											//dtm.setDataVector(null,new Object[]{"Code","Name",fridays[i],fridays[i+1],fridays[i+2]});
		    									}
		    								}
		    							}
		    					}
		    					/*System.out.println("RBI Form1 Reports Starts________________");
		    					for(int i=0;i<table_data.length;i++){
		    						for(int j=0;j<7;j++){
		    							System.out.print(""+table_data[i][j]+"  ");
		    						}
		    						System.out.println();
		    					}
		    					System.out.println("RBI Form1 Reports ends________________");*/
		    					reportName=form1_reports.getReportName();
				    			  if(reportName.equalsIgnoreCase("Form1A")){
				    				  req.setAttribute("reportTable", Form1A);
				    			  }else{
				    				  req.setAttribute("reportTable", Form1BC);
				    			  }
		    					req.setAttribute("RBIForm1ReportsTable", table_data);
		    					req.setAttribute("RBIForm1Fridays", fridays);
		    					req.setAttribute("RBIForm1ABC",form1_reports.getReportName());
		    			  }catch(Exception e){
		    				  e.printStackTrace();
		    			  }
		    			  
		    			  //code for view ends
		    			  
		    		  }
		    		  	    		  
		    		/* if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }*/
		    		  path=MenuNameReader.getScreenProperties(form1_reports.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  form1_reports.setReportName(form1_reports.getReportName());
		    		  
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
		/*  *************Gl RBI Form 1 Reports  end********************** */
		/* **********************GL RBI Form 1 LInk Updation start************ */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm1LinkUpdationMenu"))
		{
	      try{
	    	  RBIForm1LinkUpdation form1_linkupdation=(RBIForm1LinkUpdation)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+form1_linkupdation);
	    	  System.out.println("the page id is "+form1_linkupdation.getPageId());
	    	  req.setAttribute("pagenum", form1_linkupdation.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(form1_linkupdation.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(form1_linkupdation.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  req.setAttribute("Form1LinkCode",gldelegate.getCodeDet());
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm1LinkUpdation")){
			try{
				RBIForm1LinkUpdation form1_linkupdation=(RBIForm1LinkUpdation)form;
		       	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+form1_linkupdation);
		    	  System.out.println("the page id is "+form1_linkupdation.getPageId());
		    	  req.setAttribute("pagenum", form1_linkupdation.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(form1_linkupdation.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(form1_linkupdation.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+form1_linkupdation.getForward());
		    		  //to get the description start
		    		  if(form1_linkupdation.getFlag().equalsIgnoreCase("from_code")){
		    			  String[][] codearr=gldelegate.getCodeDet();
		    			  String recCode=form1_linkupdation.getNdtlCode().toString();
		    			  if(form1_linkupdation.getNdtlCode()!=null){
		    				  for(int i=0;i<codearr.length;i++){
		    					  if(codearr[i][0].equalsIgnoreCase(recCode))
		    					  {
		    						  req.setAttribute("DescriptionNdtl", codearr[i][1]);
		    					  }
		    					  
		    					  if(codearr[i][0].equalsIgnoreCase(form1_linkupdation.getCrrReqCode().toString()))
		    					  {
		    						  req.setAttribute("DescriptionCrrReq", codearr[i][1]);
		    					  }
		    					  
		    					  if(codearr[i][0].equalsIgnoreCase(form1_linkupdation.getCrrActCode().toString()))
		    					  {
		    						  req.setAttribute("DescriptionCrrAct", codearr[i][1]);
		    					  }
		    					  
		    					  if(codearr[i][0].equalsIgnoreCase(form1_linkupdation.getSlrReqCode().toString()))
		    					  {
		    						  req.setAttribute("DescriptionSlrReq", codearr[i][1]);
		    					  }
		    					  
		    					  if(codearr[i][0].equalsIgnoreCase(form1_linkupdation.getSlrMaintainedCode().toString()))
		    					  {
		    						  req.setAttribute("DescriptionSlrMaintain", codearr[i][1]);
		    					  }
		    					  
		    					  }
		    				  
		    				  
		    				  
		    				  
		    				  
		    			  }
		    			  
		    			  
		                      path=MenuNameReader.getScreenProperties(form1_linkupdation.getPageId());
				    		  gldelegate=new GLDelegate();
				    		  setGLOpeningInitParams(req, path, gldelegate);
				    		  req.setAttribute("Form1LinkCode",gldelegate.getCodeDet());
				    		  form1_linkupdation.setNdtlCode(form1_linkupdation.getNdtlCode());
				    		  form1_linkupdation.setCrrReqCode(form1_linkupdation.getCrrReqCode());
				    		  form1_linkupdation.setCrrActCode(form1_linkupdation.getCrrActCode());
				    		  form1_linkupdation.setSlrReqCode(form1_linkupdation.getSlrReqCode());
				    		  form1_linkupdation.setSlrMaintainedCode(form1_linkupdation.getSlrMaintainedCode());
				    		  req.setAttribute("Form1LinkCode",gldelegate.getCodeDet());
				    		  System.out.println("the path from MenuNameReader is "+path);
				    		  req.setAttribute("pageId",path);
				    		  
			                  return map.findForward(ResultHelp.getSuccess());
			    		  }
			        
			    	
		    		  
		    		  
		    		  //to get the description end
		    		  
		    		  
		    		  
		    		  
		    		  	    		  
		    		/* if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }*/
		    		  
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
		
	
	
		
		
		
		
		
		
		
		
		/*  *************Gl RBI Form 1 LInk Updation  end********************** */
	/* ******************GL RbI form1 Master start ******************* */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm1MasterMenu"))
		{
	      try{
	    	  RBIForm1Master form1_master=(RBIForm1Master)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+form1_master);
	    	  System.out.println("the page id is "+form1_master.getPageId());
	    	  req.setAttribute("pagenum", form1_master.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(form1_master.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(form1_master.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  req.setAttribute("Form1LinkCode",gldelegate.getCodeDet());
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm1Master")){
			try{
				RBIForm1Master form1_master=(RBIForm1Master)form;
		       	  
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+form1_master);
		    	  System.out.println("the page id is "+form1_master.getPageId());
		    	  req.setAttribute("pagenum", form1_master.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(form1_master.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(form1_master.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+form1_master.getForward());
		    		//to get the table columns start
		    		  if(form1_master.getFlag().equalsIgnoreCase("from_tablename")){
		    			  String[] String19Master={"Code","Name","From Date", "To Date", "De User", "De Tml", "De Date", "Update"};
		    			  String[] String19Link={"Code","Name", "Transaction Src", "GL Type", "GL Code", "GL Name", "From Date", "To Date", "Percentage", "CD Ind", "Mul By", "Sequence", "De User", "De Tml", "De Date", "Update"}; 
		    			  if(form1_master.getTableName().equalsIgnoreCase("RBIForm1Head") || form1_master.getTableName().equalsIgnoreCase("Form9Master")){
		    				  req.setAttribute("form19master", String19Master);
		    			  }
		    			  if(form1_master.getTableName().equalsIgnoreCase("RBIForm1Link") || form1_master.getTableName().equalsIgnoreCase("Form9Link")){
		    				  req.setAttribute("form19master", String19Link);
		    			  }
		    			  
		                      path=MenuNameReader.getScreenProperties(form1_master.getPageId());
				    		  gldelegate=new GLDelegate();
				    		  setGLOpeningInitParams(req, path, gldelegate);
				    		  
				    		  form1_master.setTableName(form1_master.getTableName());
				    		  
				    		  System.out.println("the path from MenuNameReader is "+path);
				    		  req.setAttribute("pageId",path);
				    		  
			                  return map.findForward(ResultHelp.getSuccess());
			    		  }
			          
		    		  
		    		  //to get the table columns end
		    		  //code for insert starts
		    		  if(form1_master.getFlag().equalsIgnoreCase("insert")){
		    			  String[] String19Master={"Code","Name","From Date", "To Date", "De User", "De Tml", "De Date", "Update"};
		    			  String[] String19Link={"Code","Name", "Transaction Src", "GL Type", "GL Code", "GL Name", "From Date", "To Date", "Percentage", "CD Ind", "Mul By", "Sequence", "De User", "De Tml", "De Date", "Update"}; 
		    			  if(form1_master.getTableName().equalsIgnoreCase("RBIForm1Head") || form1_master.getTableName().equalsIgnoreCase("Form9Master")){
		    				  req.setAttribute("form19masterInsert", String19Master);
		    			  }
		    			  if(form1_master.getTableName().equalsIgnoreCase("RBIForm1Link") || form1_master.getTableName().equalsIgnoreCase("Form9Link")){
		    				  req.setAttribute("form19masterInsert", String19Link);
		    			  }
		    			  
		                      path=MenuNameReader.getScreenProperties(form1_master.getPageId());
				    		  gldelegate=new GLDelegate();
				    		  setGLOpeningInitParams(req, path, gldelegate);
				    		  
				    		  form1_master.setTableName(form1_master.getTableName());
				    		  
				    		  System.out.println("the path from MenuNameReader is "+path);
				    		  req.setAttribute("pageId",path);
				    		  
			                  return map.findForward(ResultHelp.getSuccess());
			    		
		    		  }
		    		  
		    		  //code for insert ends
		    		  
		    		//code for refesh button start
		    		  if(form1_master.getFlag().equalsIgnoreCase("refresh")){
		    			  int tableindex=0;
		    			   			  String[][] tableData=null;
		    			  String[] String19Master={"Code","Name","From Date", "To Date", "De User", "De Tml", "De Date", "Update"};
		    			  String[] String19Link={"Code","Name", "Transaction Src", "GL Type", "GL Code", "GL Name", "From Date", "To Date", "Percentage", "CD Ind", "Mul By", "Sequence", "De User", "De Tml", "De Date", "Update"}; 
		    			  if(form1_master.getTableName().equalsIgnoreCase("RBIForm1Head") || form1_master.getTableName().equalsIgnoreCase("Form9Master")){
		    				  req.setAttribute("form19master", String19Master);
		    				  System.out.println("AAAAAAAAAAAArray length"+String19Master.length);
		    				 
		    			  }
		    			  if(form1_master.getTableName().equalsIgnoreCase("RBIForm1Link") || form1_master.getTableName().equalsIgnoreCase("Form9Link")){
		    				  req.setAttribute("form19master", String19Link);
		    				  System.out.println("AAAAAAAAAAAArray length"+String19Link.length);
		    				 
		    			  }
		    			  //refresh code start
		    			  if(form1_master.getTableName().equalsIgnoreCase("RBIForm1Head")){
		    				  tableindex=0;
		    				  tableData=gldelegate.form1Tabledata(tableindex);
				    		  System.out.println("Table length============="+tableData.length);
				    		  req.setAttribute("displaytabledata", tableData);
		    			  }
		    			  if(form1_master.getTableName().equalsIgnoreCase("RBIForm1Link")){
		    				  tableindex=1;
		    				  tableData=gldelegate.form1LinkTabledata(tableindex);
				    		  System.out.println("Table length============="+tableData.length);
				    		  req.setAttribute("displaytabledata", tableData);
		    				  
		    			  }
		    			  if(form1_master.getTableName().equalsIgnoreCase("Form9Master")){
		    				  tableindex=2;
		    				  tableData=gldelegate.form1Tabledata(tableindex);
				    		  System.out.println("Table length============="+tableData.length);
				    		  req.setAttribute("displaytabledata", tableData);
		    			  }
		    			  if(form1_master.getTableName().equalsIgnoreCase("Form9Link")){
		    				  tableindex=3;
		    				  tableData=gldelegate.form1LinkTabledata(tableindex);
				    		  System.out.println("Table length============="+tableData.length);
				    		  req.setAttribute("displaytabledata", tableData);
		    			  }
		    			  
			    		  
		    			  
		    			  //refresh code end
		    			 
		                      path=MenuNameReader.getScreenProperties(form1_master.getPageId());
				    		  
				    		  
				    		  setGLOpeningInitParams(req, path, gldelegate);
				    		  gldelegate=new GLDelegate();
				    		  form1_master.setTableName(form1_master.getTableName());
				    		  
				    		  System.out.println("the path from MenuNameReader is "+path);
				    		  req.setAttribute("pageId",path);
				    		  
			                  return map.findForward(ResultHelp.getSuccess());
			    		  }
			          
		    		  
		    		  //code for refresh button end
		    		  //code for exit starts
	    			  if(form1_master.getFlag().equalsIgnoreCase("exit")){
	    				    
	    			  }
	    			  
	    			  //code for exit ends
	    			  //code for insert starts
	    			  if(form1_master.getFlag().equalsIgnoreCase("submit")){
	    				  gldelegate=new GLDelegate();
	    				  int int_result_value=0,num=0;
	    				  Form1Object form1_object=null;
	  		    			
	  		    			int ret;
	  		    			String tableName=form1_master.getTableName().toString();
	  		    			String[] tabData=req.getParameterValues("txtFld");
	  		    			System.out.println("=======Parameter values===length==="+tabData.length);
	  		    			System.out.println("=======Tablename======"+tableName);
	  		    			for(int i=0;i<tabData.length;i++){
	  		    				System.out.println("values====="+tabData[i]);
	  		    			}
	  		    			if(form1_master.getTableName().equalsIgnoreCase("RBIForm1Head") ){
	  		    				form1_object=new Form1Object();
	  		    				form1_object.setCode(Integer.parseInt(tabData[0].toString()));
	  		    				form1_object.setName(tabData[1].toString());
	  		    				form1_object.setFromDate(tabData[2].toString());
	  		    				form1_object.setToDate(tabData[3].toString());
	  		    				form1_object.setDeTml(tabData[4].toString());
	  		    				form1_object.setDeUser(tabData[5].toString());
	  		    				form1_object.setDeDate(tabData[6].toString());
	  		    				num=1;
	  		    				try{
		  						    int_result_value = gldelegate.rbiForm1InsertRow(form1_object,num);
		  						    System.out.println("int _result_value="+int_result_value);
		  						    
		  						}catch(Exception exception_insert){System.out.println(" "+exception_insert);}
		  						
		  						
		  						if(int_result_value==0)
		  							req.setAttribute("msg","From Date should be greater than previous Date"+int_result_value);
		  					    if(int_result_value==1)
		  					    	req.setAttribute("msg","Cannot insert a row U can go Updation"+int_result_value);
		  					    if(int_result_value==2){
		  					    	req.setAttribute("msg","Succefully Inserted"+int_result_value);
		  					    }
		  					    else
		  					    	req.setAttribute("msg","No Insertion"+int_result_value);

	  		    				
			    			  }
	  		    			if(form1_master.getTableName().equalsIgnoreCase("Form9Master")){
	  		    				form1_object=new Form1Object(); 
	  		    				form1_object.setCode(Integer.parseInt(tabData[0].toString()));
	  		    				form1_object.setName(tabData[1].toString());
	  		    				form1_object.setFromDate(tabData[2].toString());
	  		    				form1_object.setToDate(tabData[3].toString());
	  		    				form1_object.setDeTml(tabData[4].toString());
	  		    				form1_object.setDeUser(tabData[5].toString());
	  		    				form1_object.setDeDate(tabData[6].toString());
	  		    				num=2;
	  		    				try{
		  						    int_result_value = gldelegate.rbiForm1InsertRow(form1_object,num);
		  						    System.out.println("int _result_value="+int_result_value);
		  						    
		  						}catch(Exception exception_insert){System.out.println(" "+exception_insert);}
		  						
		  						
		  						if(int_result_value==0)
		  							req.setAttribute("msg","From Date should be greater than previous Date"+int_result_value);
		  					    if(int_result_value==1)
		  					    	req.setAttribute("msg","Cannot insert a row U can go Updation"+int_result_value);
		  					    if(int_result_value==2){
		  					    	req.setAttribute("msg","Succefully Inserted"+int_result_value);
		  					    }
		  					    else
		  					    	req.setAttribute("msg","No Insertion"+int_result_value);

			    			  }
	  		    			if(form1_master.getTableName().equalsIgnoreCase("RBIForm1Link")){
	  		    				form1_object=new Form1Object(); 
	  		    				form1_object.setCode(Integer.parseInt(tabData[0].toString()));
	  		    				form1_object.setTrnSrc(tabData[2].toString());
	  		    				form1_object.setGLType(tabData[3].toString());
	  		    				form1_object.setGLCode(Integer.parseInt(tabData[4].toString()));
	  		    				
	  		    				form1_object.setFromDate(tabData[6].toString());
	  		    				form1_object.setToDate(tabData[7].toString());
	  		    				form1_object.setPercent(Double.parseDouble(tabData[8].toString()));
	  		    				form1_object.setCdInd(tabData[9].toString());
	  		    				form1_object.setMulBy(Integer.parseInt(tabData[10].toString()));
	  		    				form1_object.setSequence(Integer.parseInt(tabData[11].toString()));
	  		    				form1_object.setDeTml(tabData[12].toString());
	  		    				form1_object.setDeUser(tabData[13].toString());
	  		    				form1_object.setDeDate(tabData[14].toString());
	  		    				num=1;
	  		    				try{
		  						    int_result_value = gldelegate.rbiForm1LinkInsertRow(form1_object,num);
		  						    System.out.println("int _result_value="+int_result_value);
		  						    
		  						}catch(Exception exception_insert){System.out.println(" "+exception_insert);}
		  						
		  						
		  						if(int_result_value==0)
		  							req.setAttribute("msg","From Date should be greater than previous Date"+int_result_value);
		  					    if(int_result_value==1)
		  					    	req.setAttribute("msg","Cannot insert a row U can go Updation"+int_result_value);
		  					    if(int_result_value==2){
		  					    	req.setAttribute("msg","Succefully Inserted"+int_result_value);
		  					    }
		  					    else
		  					    	req.setAttribute("msg","No Insertion"+int_result_value);

			    			  }
	  		    			
	  		    			if(form1_master.getTableName().equalsIgnoreCase("Form9Link")){
	  		    				form1_object=new Form1Object(); 
	  		    				form1_object.setCode(Integer.parseInt(tabData[0].toString()));
	  		    				form1_object.setTrnSrc(tabData[2].toString());
	  		    				form1_object.setGLType(tabData[3].toString());
	  		    				form1_object.setGLCode(Integer.parseInt(tabData[4].toString()));
	  		    				
	  		    				form1_object.setFromDate(tabData[6].toString());
	  		    				form1_object.setToDate(tabData[7].toString());
	  		    				form1_object.setPercent(Double.parseDouble(tabData[8].toString()));
	  		    				form1_object.setCdInd(tabData[9].toString());
	  		    				form1_object.setMulBy(Integer.parseInt(tabData[10].toString()));
	  		    				form1_object.setSequence(Integer.parseInt(tabData[11].toString()));
	  		    				form1_object.setDeTml(tabData[12].toString());
	  		    				form1_object.setDeUser(tabData[13].toString());
	  		    				form1_object.setDeDate(tabData[14].toString());
	  		    				num=2;
	  		    				try{
		  						    int_result_value = gldelegate.rbiForm1LinkInsertRow(form1_object,num);
		  						    System.out.println("int _result_value="+int_result_value);
		  						    
		  						}catch(Exception exception_insert){System.out.println(" "+exception_insert);}
		  						
		  						
		  						if(int_result_value==0)
		  							req.setAttribute("msg","From Date should be greater than previous Date"+int_result_value);
		  					    if(int_result_value==1)
		  					    	req.setAttribute("msg","Cannot insert a row U can go Updation"+int_result_value);
		  					    if(int_result_value==2){
		  					    	req.setAttribute("msg","Succefully Inserted"+int_result_value);
		  					    }
		  					    else
		  					    	req.setAttribute("msg","No Insertion"+int_result_value);

			    			  }

	    				    
	    			  

	  		    			
	    				    
	    			  }
	    			  
	    			  
	    			  //code for insert ends
		    		 
		    		  
		    		  	    		  
		    		/* if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }*/
		    		  
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
	
		
		
		
		/* *******************GL Rbi form1 Master end **************** */
		/* ******************GL RbI Marking DAte Entry start ******************* */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIMarkingDateEntryMenu"))
		{
	      try{
	    	  RBIMarkingDateEntry markingdate_entry=(RBIMarkingDateEntry)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+markingdate_entry);
	    	  System.out.println("the page id is "+markingdate_entry.getPageId());
	    	  req.setAttribute("pagenum", markingdate_entry.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(markingdate_entry.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(markingdate_entry.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  req.setAttribute("Form1LinkCode",gldelegate.getCodeDet());
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIMarkingDateEntry")){
			try{
				RBIMarkingDateEntry markingdate_entry=(RBIMarkingDateEntry)form;
				req.setAttribute("msg","");
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+markingdate_entry);
		    	  System.out.println("the page id is "+markingdate_entry.getPageId());
		    	  req.setAttribute("pagenum", markingdate_entry.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(markingdate_entry.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(markingdate_entry.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+markingdate_entry.getForward());
		    		
		    		//code for refesh button start
		    		  if(markingdate_entry.getFlag().equalsIgnoreCase("refresh")){
		    			  try{
		    			  req.setAttribute("MarkingDateEntry", gldelegate.getMarkingDtDetails());
		    			  if(gldelegate.getMarkingDtDetails()==null){
		    				  req.setAttribute("Nothing", "Records Not Found");
		    			  }
		    			  if(gldelegate.getMarkingDtDetails().length==0){
		    				  
		    				  req.setAttribute("msg","Records not Found");  
		    			  }
		    			  }catch(Exception e){
		    				  e.printStackTrace();
		    			  }
		    			  
		    			 }
		    			  
			    		  
		    			  
		    			 
		    		  
		    		  //code for refresh button end
		    		  //code for insert a row starts
		    		  if(markingdate_entry.getFlag().equalsIgnoreCase("insert")){
		    			  req.setAttribute("MarkingDateEntry", gldelegate.getMarkingDtDetails());
		    			  req.setAttribute("addnewrow", "insert");
		    			  
		    			  
		    			 }
		    		  
		    		  //code for insert a row ends
		    		  //code for submit starts
		    		  if(markingdate_entry.getFlag().equalsIgnoreCase("submit")){
		    			  gldelegate=new GLDelegate();
	    				  int int_result_value=0,num=0;
	    				  Form1Object form1_object=null;
	  		    			
	  		    			int ret;
	  		    			
	  		    			String[] tabData=req.getParameterValues("txtFld");
	  		    			System.out.println("=======Parameter values===length==="+tabData.length);
	  		    			
	  		    			for(int i=0;i<tabData.length;i++){
	  		    				System.out.println("values====="+tabData[i]);
	  		    			}
	  		    			
	  		    				form1_object=new Form1Object();
	  		    				form1_object.setDayOfWeek(tabData[0].toString());
	  		    				form1_object.setDate(Validations.convertYMD(tabData[1].toString()));
	  		    				form1_object.setRecurringDays(Integer.parseInt(tabData[2].toString()));
	  		    				form1_object.setNDTLAmount(Double.parseDouble(tabData[3].toString()));
	  		    				form1_object.setDeTml(tabData[4].toString());
	  		    				form1_object.setDeUser(tabData[5].toString());
	  		    				form1_object.setDeDate(tabData[6].toString());
	  		    				
	  		    				try{
		  						    int_result_value = gldelegate.rbiMarkingDateInsertRow(form1_object);
		  						    System.out.println("int _result_value="+int_result_value);
		  						    
		  						}catch(Exception exception_insert){System.out.println(" "+exception_insert);}
		  						if(int_result_value==1)
		  							req.setAttribute("msg","Sucessfully Inserted"+int_result_value);
								else
									req.setAttribute("msg","No Insertion"+int_result_value);	
								
							
		  						
                        
		    		  }
		    		  
		    		  
		    		  //code for submit ends
		    		  if(markingdate_entry.getFlag().equalsIgnoreCase("exit")){
		    		  
		    		  }
		    		  	    		  
		    		/* if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }*/
		    		  
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
	
		
		/* ***********************Tray List******************** */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLTrayListMenu"))
		{
	      try{
	    	  TrayListForm tl=(TrayListForm)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+tl);
	    	  System.out.println("the page id is "+tl.getPageId());
	    	  req.setAttribute("pagenum", tl.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(tl.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(tl.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  req.setAttribute("Form1LinkCode",gldelegate.getCodeDet());
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
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLTyayList")){
			try{
		    	  TrayListForm tl=(TrayListForm)form;
		       	 // FrontCounterDelegate fcDelegate=new FrontCounterDelegate();
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+tl);
		    	  System.out.println("the page id is "+tl.getPageId());
		    	  req.setAttribute("pagenum", tl.getPageId());
		    	  
			       
		    	  
		    	  if(MenuNameReader.containsKeyScreen(tl.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(tl.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  FrontCounterDelegate fcDelegate=new FrontCounterDelegate();
		    		  fcDelegate.getPendingTrayList(gldelegate.getSysDate());
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  req.setAttribute("Form1LinkCode",gldelegate.getCodeDet());
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
	/* *************************End  of TrayList ***************** */

		
		
		
		/* *******************GL Rbi Marking Date Entry end **************** */
		
		
		
		
		
		
		return map.findForward(ResultHelp.getSuccess());
	}

	
	
	
	 private void setErrorPageElements(String exception,HttpServletRequest req,String path)
	  {
		  req.setAttribute("exception",exception);
		  req.setAttribute("pageId",path);

	  }

	private void setGLOpeningInitParams(HttpServletRequest req,String path,GLDelegate gldelegate)throws Exception
	 {
		try{
			req.setAttribute("pageId",path);
			System.out.println("inside the getsysdate method___________");
			req.setAttribute("date", gldelegate.getSysDate());
			req.setAttribute("daybookdate", gldelegate.getSysDate());
			Calendar cl=Calendar.getInstance();
			req.setAttribute("year", cl.get(Calendar.YEAR));
		}
		catch(Exception e){
			throw e;
		}
	 }
	
	/*void checkmthconsolidation(String fromdate,String todate){
		
		String mthstatus[][]=null;
		int ret=0;
		try{
		mthstatus=gldelegate.checkMonthConsolidation(fromdate,todate,"Head Office");
		
		if(mthstatus[0][0].equals("1") || mthstatus[0][0].equals("-2") || mthstatus[0][0].equals("PF") || mthstatus[0][0].equals("OK") || mthstatus[0][0].equals("NO") || mthstatus[0][0].equals("CN")||  mthstatus[0][0].equals("OP")){
			//but_post.setEnabled(true);
			enableSubmitButton(true);
			but_post.requestFocus();
		}
		else if(mthstatus[0][0].equals("0") || mthstatus[0][0].equals("PT") ){
			ret=JOptionPane.showConfirmDialog(null,"Monthly Consolidation Posting has done do you want to overwrite?");
			if(ret==JOptionPane.YES_OPTION)
				//but_post.setEnabled(true);
				enableSubmitButton(true);
				but_post.requestFocus();
		}
		else
			JOptionPane.showMessageDialog(null,"Error in  Monthly Conslidation");
		}catch(Exception e){e.printStackTrace();}
	}
	
	*/
	String[][] checkMonthClose(String year_mth){
    	String  ret[][]=null;
    	try{
    		ret=gldelegate.checkMonthClose(year_mth);
    		
    	}catch(Exception e){e.printStackTrace();}
    	return ret;
    }
	
	
	String convertToYYYYMM(String date)
	 {
	 	String day=null,month=null,year=null;
	 	if(date!=null){
		StringTokenizer st= new StringTokenizer(date,"/");
			day=st.nextToken();
			month=st.nextToken();
			year=st.nextToken();
			return (year+month);
		}
		else
				return "";
	}
	String convertToYYYYMMDD(String date)
	 {
	 	String day=null,month=null,year=null;
	 	if(date!=null){
		StringTokenizer st= new StringTokenizer(date,"/");
			day=st.nextToken();
			month=st.nextToken();
			year=st.nextToken();
			return (year+month+day);
		}
		else
				return "";
	}	 

void checkYearClose(){
	
	
	
	
	
	
}
}
