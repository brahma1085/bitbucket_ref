package com.scb.gl.actions;

import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import masterObject.general.DoubleFormat;
import masterObject.general.ModuleObject;
import masterObject.generalLedger.DayBookObject;
import masterObject.generalLedger.DayBookObjectNew;
import masterObject.generalLedger.GLObject;
import masterObject.generalLedger.GLReportObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.common.help.ResultHelp;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.GLDelegate;
import com.scb.gl.forms.DayBook;
import com.scb.gl.forms.GLSchedule;
import com.scb.gl.forms.MonthlyProfitLoss;
import com.scb.gl.forms.MonthlyRecieptsPayments;
import com.scb.gl.forms.RBICRRSLRSubSchedule;
import com.scb.gl.forms.RBIForm1Reports;
import com.scb.props.MenuNameReader;

import exceptions.DateFormatException;
import general.Validations;

public class GLActionTwo extends Action
{
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
	public ActionForward execute(ActionMapping map,ActionForm form,HttpServletRequest req,HttpServletResponse res)throws Exception
	{
		String[][] strMthStatus=null;

		String strType=null,strCode=null;
		System.out.println("The main path is"+map.getPath());
		System.out.println("Check for class file---------------------123456789-------------");
		System.out.println("__________Amzad____Amzad_________");
		           /** **************GL Daily Posting Start********************* */
		
		session=req.getSession();
		gluser=(String)session.getAttribute("UserName");
		gltml=(String)session.getAttribute("UserTml") ;
		/*  *************Gl RBI CRR SLR sub schedule  end********************** */
		/* **********************GL RBI Form 1 Reports start************ */
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
    	
    	 if(map.getPath().trim().equalsIgnoreCase("/GL/GLDayBook")){
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
		    					else
		    						//req.setAttribute("msg","Posting has Done, You can proceed.. ");
		    					    daybook.setDate(daybook.getDate());
		    					    
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
			    			  if(newDbook!=null){
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
			    			  req.setAttribute("DayBookNewObject", newDbook);
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
			    							String filename=req.getParameter("fname");
			    			  				try{
			    			  				HSSFWorkbook wb= new HSSFWorkbook();
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
			    				            cell.setCellValue("");
			    				            if(daybook.getUserChoice().equalsIgnoreCase("DayBook")){
			    	                       	HSSFRow row1 = sheet.createRow((short)3);
			    			  			   	HSSFCell cell1=row1.createCell((short)5);
			    			  				cell1.setCellStyle(cellStyle);
			    				            cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
			    				            cell1.setCellValue("Daybook For  "+daybook.getDate());
			    			  				}else{
			    			  					HSSFRow row1 = sheet.createRow((short)3);
				    			  			   	HSSFCell cell1=row1.createCell((short)5);
				    			  				cell1.setCellStyle(cellStyle);
				    				            cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
				    				            cell1.setCellValue("Trial Balance For  "+daybook.getDate());
			    			  				}
			    	                        HSSFRow row2 = sheet.createRow((short)5);
			    			  			    HSSFCell cell2=row2.createCell((short)4);
			    			  			    cell2.setCellStyle(cellStyle);
			    			                cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
			    			                cell2.setCellValue("General Ledger");
			    			                HSSFCell cell3=row2.createCell((short)7);
			    			  			    cell3.setCellStyle(cellStyle);
			    			                cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
			    			                cell3.setCellValue("Debit Amount");
			    			                HSSFCell cellc=row2.createCell((short)10);
			    			  			    cellc.setCellStyle(cellStyle);
			    			                cellc.setCellType(HSSFCell.CELL_TYPE_STRING);
			    			                cellc.setCellValue("Credit Amount");
			    			                HSSFCell celld=row2.createCell((short)14);
			    			  			    celld.setCellStyle(cellStyle);
			    			                celld.setCellType(HSSFCell.CELL_TYPE_STRING);
			    			                celld.setCellValue("Net");
			    			                if(daybook.getUserChoice().equalsIgnoreCase("TrailBalance")){
			    			                	HSSFCell celle=row2.createCell((short)15);
				    			  			    celle.setCellStyle(cellStyle);
				    			                celle.setCellType(HSSFCell.CELL_TYPE_STRING);
				    			                celle.setCellValue("Closing Balances");
			    			                }
			    			                HSSFRow row3 = sheet.createRow((short)6);
			    				  			HSSFCell cell4=row3.createCell((short)3);
			    				  			cell4.setCellStyle(cellStyle);
			    				            cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
			    				            cell4.setCellValue("Type"); 
			    				            HSSFCell cell5=row3.createCell((short)4);
			    				  			cell5.setCellStyle(cellStyle);
			    				            cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
			    				            cell5.setCellValue("Code");
			    				            HSSFCell cell6=row3.createCell((short)5);
			    				  			cell6.setCellStyle(cellStyle);
			    				            cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
			    				            cell6.setCellValue("Name");
			    				            HSSFCell cell7=row3.createCell((short)6);
			    				  			cell7.setCellStyle(cellStyle);
			    				            cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
			    				            cell7.setCellValue("Cash");
			    				            HSSFCell cell8=row3.createCell((short)7);
			    				  			cell8.setCellStyle(cellStyle);
			    				            cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
			    				            cell8.setCellValue("Clearing"); 
			    				            
			    				            
			    				  			HSSFCell cell9=row3.createCell((short)8);
			    				  			cell9.setCellStyle(cellStyle);
			    				            cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
			    				            cell9.setCellValue("Transfer"); 
			    				            HSSFCell cell10=row3.createCell((short)9);
			    				  			cell10.setCellStyle(cellStyle);
			    				            cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
			    				            cell10.setCellValue("Total");
			    				            HSSFCell cell11=row3.createCell((short)10);
			    				  			cell11.setCellStyle(cellStyle);
			    				            cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
			    				            cell11.setCellValue("Cash");
			    				            HSSFCell cell12=row3.createCell((short)11);
			    				  			cell12.setCellStyle(cellStyle);
			    				            cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
			    				            cell12.setCellValue("Clearing");
			    				            HSSFCell cell13=row3.createCell((short)12);
			    				  			cell13.setCellStyle(cellStyle);
			    				            cell13.setCellType(HSSFCell.CELL_TYPE_STRING);
			    				            cell13.setCellValue("Transfer"); 
			    				            HSSFCell cell14=row3.createCell((short)13);
			    				  			cell14.setCellStyle(cellStyle);
			    				            cell14.setCellType(HSSFCell.CELL_TYPE_STRING);
			    				            cell14.setCellValue("Total"); 
			    				            HSSFCell cell15=row3.createCell((short)14);
			    				  			cell15.setCellStyle(cellStyle);
			    				            cell15.setCellType(HSSFCell.CELL_TYPE_STRING);
			    				            cell15.setCellValue("Transaction"); 
			    				            if(daybook.getUserChoice().equalsIgnoreCase("TrailBalance")){
			    				            	HSSFCell cell16=row3.createCell((short)15);
				    				  			cell16.setCellStyle(cellStyle);
				    				            cell16.setCellType(HSSFCell.CELL_TYPE_STRING);
				    				            cell16.setCellValue("Debit"); 
				    				            HSSFCell cell17=row3.createCell((short)16);
				    				  			cell17.setCellStyle(cellStyle);
				    				            cell17.setCellType(HSSFCell.CELL_TYPE_STRING);
				    				            cell17.setCellValue("Credit"); 
			    				            }
			    				            int count=1;
			    				            int r=7;		  
			    				  			if(data!=null && data.length!=0){
			    				  				for(int i=0;i<data.length;i++){
			    				  					
			    				  					HSSFRow rowcount = sheet.createRow((short)r++);
			    				  				   if(data[i][0]!=null)
			    				  				      rowcount.createCell((short)3).setCellValue(data[i][0].toString());
			    				  				   else
			    				  					 rowcount.createCell((short)3).setCellValue("");
			    				  				   if(data[i][1]!=null)
			    				  				      rowcount.createCell((short)4).setCellValue(data[i][1].toString());
			    				  				   else
			    				  					 rowcount.createCell((short)4).setCellValue("");
			    				  				   if(data[i][2]!=null)
			    				  				     rowcount.createCell((short)5).setCellValue(data[i][2].toString());
			    				  				   else
			    				  					 rowcount.createCell((short)5).setCellValue("");
			    				  				   if(data[i][3]!=null)
			    				  				     rowcount.createCell((short)6).setCellValue(data[i][3].toString());
			    				  				   else
			    				  					 rowcount.createCell((short)6).setCellValue(""); 
			    				  				   if(data[i][4]!=null)
			    				  				     rowcount.createCell((short)7).setCellValue(data[i][4].toString());
			    				  				   else
			    				  					 rowcount.createCell((short)7).setCellValue("");
			    				  				   if(data[i][5]!=null)
			    				  				     rowcount.createCell((short)8).setCellValue(data[i][5].toString());
			    				  				   else
			    				  					 rowcount.createCell((short)8).setCellValue(""); 
			    				  				   if(data[i][6]!=null)
			    				  				     rowcount.createCell((short)9).setCellValue(data[i][6].toString());
			    				  				   else
			    				  					 rowcount.createCell((short)9).setCellValue(""); 
			    				  				   if(data[i][7]!=null)
			    				  				     rowcount.createCell((short)10).setCellValue(data[i][7].toString());
			    				  				   else
			    				  					 rowcount.createCell((short)10).setCellValue("");
			    				  				   if(data[i][8]!=null)
			    				  				     rowcount.createCell((short)11).setCellValue(data[i][8].toString());
			    				  				   else
			    				  					 rowcount.createCell((short)11).setCellValue("");
			    				  				   if(data[i][9]!=null)
			    				  				     rowcount.createCell((short)12).setCellValue(data[i][9].toString());
			    				  				   else
			    				  					 rowcount.createCell((short)12).setCellValue("");
			    				  				   if(data[i][10]!=null)
			    				  				     rowcount.createCell((short)13).setCellValue(data[i][10].toString());
			    				  				   else
			    				  					 rowcount.createCell((short)13).setCellValue("");
			    				  				   if(data[i][11]!=null)
			    				  				     rowcount.createCell((short)14).setCellValue(data[i][11].toString());
			    				  				   else
			    				  					 rowcount.createCell((short)14).setCellValue("");
			    				  				if(daybook.getUserChoice().equalsIgnoreCase("TrailBalance")){
			    				  					if(data[i][12]!=null)
			    				  					  rowcount.createCell((short)15).setCellValue(data[i][12].toString());
			    				  					else
			    				  						rowcount.createCell((short)15).setCellValue("");
			    				  					if(data[i][13]!=null)
				    				  				    rowcount.createCell((short)16).setCellValue(data[i][13].toString());
			    				  					else
			    				  						rowcount.createCell((short)16).setCellValue("");
			    				  				}
			    				  				
			    				  				
			    				  				
			    				  				}
			    				  				for(int l=0;l<data1.length-1;l++){
			    				  					HSSFRow rowcount = sheet.createRow((short)r++);
			    				  					rowcount.createCell((short)3).setCellValue(data1[l][0].toString());
			    					  				   rowcount.createCell((short)4).setCellValue(data1[l][1].toString());
			    					  				   rowcount.createCell((short)5).setCellValue(data1[l][2].toString());
			    					  				   rowcount.createCell((short)6).setCellValue(data1[l][3].toString());
			    					  				   rowcount.createCell((short)7).setCellValue(data1[l][4].toString());
			    					  				   rowcount.createCell((short)8).setCellValue(data1[l][5].toString());
			    					  				   rowcount.createCell((short)9).setCellValue(data1[l][6].toString());
			    					  				 rowcount.createCell((short)10).setCellValue(data1[l][7].toString());
			    					  				rowcount.createCell((short)11).setCellValue(data1[l][8].toString());
			    					  				rowcount.createCell((short)12).setCellValue(data1[l][9].toString());
			    					  				rowcount.createCell((short)13).setCellValue(data1[l][10].toString());
			    					  				rowcount.createCell((short)14).setCellValue(data1[l][11].toString());
			    					  				if(daybook.getUserChoice().equalsIgnoreCase("TrailBalance")){
			    					  					rowcount.createCell((short)15).setCellValue(data1[l][12].toString());
				    					  				rowcount.createCell((short)16).setCellValue(data1[l][13].toString());
			    					  				}
			    				  				}
			    				  				
			    				  				}
			    				  			FileOutputStream fileOut = new FileOutputStream("c:\\Reports\\daybook\\"+filename+".xls");
			    				  			wb.write(fileOut);
			    				  			fileOut.close();   
			    				  			req.setAttribute("msg","Successfully saved to file...! \n file path is c:\\Reports\\daybook\\"+filename+".xls");
			    				  			     }catch ( Exception ex ){  
			    				  			    	 ex.printStackTrace();
			    				  			     }     
			    				  				
			    							
			    							
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
		    				 }
		    				 
		    				 if(daybook.getFlag().equalsIgnoreCase("save"))
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
	                    	        out.print("DayBook Details: ");
	                    	        out.print("\n");
	                    	        out.print("\n");
	                       		   /*HSSFCell cell = row.createCell((short)0);
	                       		   cell.setCellValue(1);*/
	                    	        out.print("Type"); out.print(",");
	                           		out.print("Code"); out.print(",");
	                           		out.print("Name"); out.print(",");
	                           		out.print("Cash"); out.print(",");
	                           		out.print("Clearing"); out.print(",");
	                           		out.print("Transfer"); out.print(",");
	                           		out.print("Total"); out.print(",");
	                           		out.print("Cash"); out.print(",");
	                           		out.print("Clearing"); out.print(",");
	                           		out.print("Transfer"); out.print(",");
	                           		out.print("Total"); out.print(",");
	                           		out.print("Transaction"); out.print(",");
	                           		out.print("Debit"); out.print(",");
	                           		out.print("Credit"); out.print("\n");
	                    	        
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
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBIForm1Reports"))
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
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLRBICRRSLRSubSchedule"))
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
		
/*  *************GL Schedule Start********************* */
		
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLScheduleMenu"))
		{  String[][] codetype=null;
	      try{
	    	  GLSchedule schedule=(GLSchedule)form;
	    	  schedule.setValidations("");
	    	  
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+schedule);
	    	  System.out.println("the page id is "+schedule.getPageId());
	    	  req.setAttribute("pagenum", schedule.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(schedule.getPageId()))
	    	  {   
	    		  	path=MenuNameReader.getScreenProperties(schedule.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  schedule.setFromDate(gldelegate.getSysDate());
	    		  schedule.setToDate(gldelegate.getSysDate());
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  req.setAttribute("comboStatusType","true");
	    		  req.setAttribute("comboStatusCode","true");
	    		  codetype=gldelegate.getCodeTypes(schedule.getFromDate(), schedule.getToDate());
	    		  req.setAttribute("moduleobj",gldelegate.getMainMods());
	    		  req.setAttribute("codetype",gldelegate.getCodeTypes(schedule.getFromDate(), schedule.getToDate()));
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
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLSchedule")){
			try{
				GLSchedule schedule=(GLSchedule)form;
				schedule.setValidations("");
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+schedule);
		    	  System.out.println("the page id is "+schedule.getPageId());
		    	  req.setAttribute("pagenum", schedule.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(schedule.getPageId()))
		    	  {  String[][] codetype=null;
		    	  codetype=gldelegate.getCodeTypes(schedule.getFromDate(), schedule.getToDate());
		    		  path=MenuNameReader.getScreenProperties(schedule.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  
		    		  System.out.println("The button valuee is"+schedule.getForward());
		    		  	
		    		  //focus lost of from GlType start
		    		  if(schedule.getFlag().equalsIgnoreCase("from_gltype")){
                        req.setAttribute("comboStatusType","false");

		    		  }
		    		  
		    		  
		    		  
		    		  //focus lost of from GlType end
		    		  
		    		  
		    		  //focus lost of to glno start
		    		  if(schedule.getFlag().equalsIgnoreCase("to_glcode")){
		    			  
		                  if(schedule.getCodes().equalsIgnoreCase("ALL_Codes")){
		                	  strType="";
		                	  strCode="";
		                	  req.setAttribute("StringCode", strType);
		                	  req.setAttribute("StringCode", strCode); 
		                    
		                  }   
		                  else
		                  {
		                     
		                      for(int i=0;i<gldelegate.getCodeTypes(schedule.getFromDate(), schedule.getToDate()).length;i++)
		                      {
		                          if(schedule.getCodes().equals(codetype[i][1]))
		                          {
		                        	  req.setAttribute("StringType", codetype[i][2]);
				                	   
		                              
		                          }
		                      }
		                      //////////
		                      for(int i=0;i<gldelegate.getCodeTypes(schedule.getFromDate(), schedule.getToDate()).length;i++)
		                      {
		                          if(schedule.getToCodes().equals(codetype[i][1]))
		                          {
		                        	  
				                	  req.setAttribute("StringCode", codetype[i][2]); 
		                              
		                          }
		                      }
		                      
		                      
		                      
		                  }
		                  path=MenuNameReader.getScreenProperties(schedule.getPageId());
			    		  gldelegate=new GLDelegate();
			    		  setGLOpeningInitParams(req, path, gldelegate);
			    		  schedule.setFromDate(schedule.getFromDate());
			    		  schedule.setToDate(schedule.getToDate());
			    		  schedule.setTypes(schedule.getTypes());
			    		  schedule.setCodes(schedule.getCodes());
			    		  schedule.setToTypes(schedule.getToTypes());
			    		  schedule.setToCodes(schedule.getToCodes());
			    		  
			    		  System.out.println("the path from MenuNameReader is "+path);
			    		  req.setAttribute("pageId",path);
			    		  req.setAttribute("comboStatusType","true");
			    		  req.setAttribute("comboStatusCode","true");
			    		  codetype=gldelegate.getCodeTypes(schedule.getFromDate(), schedule.getToDate());
			    		  req.setAttribute("moduleobj",gldelegate.getMainMods());
			    		  req.setAttribute("codetype",gldelegate.getCodeTypes(schedule.getFromDate(), schedule.getToDate()));
			    		 
		                  return map.findForward(ResultHelp.getSuccess());
		    		  }
		              
		    		  
		    		  
		    		     		  
		    		  
		    		  
		    		  
		    		  
		    		  //focus lost of to glno end
//clear fun start
if(schedule.getFlag().equalsIgnoreCase("clear")){
	  
    
    
        
        
   
    path=MenuNameReader.getScreenProperties(schedule.getPageId());
	  gldelegate=new GLDelegate();
	  setGLOpeningInitParams(req, path, gldelegate);
	  schedule.setFromDate(gldelegate.getSysDate());
	  schedule.setToDate(schedule.getFromDate());
	 
	  schedule.setTypes("All Types");
	  schedule.setCodes("All Codes");
	  schedule.setToTypes("All Types");
	  schedule.setToCodes("All Codes");
	  
	  System.out.println("the path from MenuNameReader is "+path);
	  req.setAttribute("pageId",path);
	  req.setAttribute("comboStatusType","true");
	  req.setAttribute("comboStatusCode","true");
	  codetype=gldelegate.getCodeTypes(schedule.getFromDate(), schedule.getToDate());
	  req.setAttribute("moduleobj",gldelegate.getMainMods());
	  req.setAttribute("codetype",gldelegate.getCodeTypes(schedule.getFromDate(), schedule.getToDate()));
	 
    return map.findForward(ResultHelp.getSuccess());
}
//code for viewing records(view button) start
if(schedule.getFlag().equalsIgnoreCase("view")){
	System.out.println("View button clicked");
	masterObject.generalLedger.GLReportObject glReportObj[]=null;
	ModuleObject[] modObj=gldelegate.getMainMods();
	//view code starts
	String type_itemvalue,code_itemcount,code_to_itemcount;
    String string_qry=" ";
    int from_glno=0,to_glno=0;
    String from_gltype="",to_gltype="";
   
    
    //code!=all and type !=all
    if(!(schedule.getCodes().toString() .equalsIgnoreCase("ALL Codes")) && (!(schedule.getTypes().toString() .toString() .equalsIgnoreCase("ALL Types"))))
    {
        from_glno=Integer.parseInt(schedule.getCodes().toString());
        to_glno=Integer.parseInt(schedule.getToCodes().toString());
        if(modObj!=null){
        	for(int i=0;i<modObj.length;i++){
        		if(schedule.getTypes().equalsIgnoreCase(modObj[i].getModuleAbbrv())){
        			from_gltype=modObj[i].getModuleCode();
        		}
        		if(schedule.getToTypes().equalsIgnoreCase(modObj[i].getModuleAbbrv())){
        			to_gltype=modObj[i].getModuleCode();
        		}
        	}
        	
        }
        
     }
    //show records start
    System.out.println("From date="+schedule.getFromDate());
    System.out.println("From date="+schedule.getToDate());
    System.out.println("From date= from_gltype"+from_gltype);
    System.out.println("From date="+schedule.getCodes());
    System.out.println("From date= to_gltype"+to_gltype);
    System.out.println("To Codes="+schedule.getToCodes());
    glReportObj=gldelegate.glScheduleDetails(schedule.getFromDate(), schedule.getToDate(), from_gltype, from_glno,to_gltype,to_glno);
    System.out.println("glReportObj length Amzad1=" +glReportObj.length );
    System.out.println("GlReport obj created");
    if(glReportObj.length>0)
    {   try{
        System.out.println("glReportObj length Amzad=" +glReportObj.length );
        //showreport starts
        Object data[][] = new Object[glReportObj.length][12];
        //Object data1[][] = new Object[17][12];
        int temp_glcode=0;
        String temp_gltype="";
        String trn_date=null;//,ac_type=null;
        double closing_bal=0.0,opening_bal=0.0;
        double cr_total=0.0,dr_total=0.0,total=0.0,net_trans=0.0;
        double cash_dr_total=0.0,cash_cr_total=0.0,cg_cr_total=0.0,cg_dr_total=0.0,trf_dr_total=0.0,trf_cr_total=0.0;
        double cash_cr=0.0,cash_dr=0.0,cg_cr=0.0,cg_dr=0.0,trf_dr=0.0,trf_cr=0.0;
        double close_bal=0.0,open_bal=0.0;
        double grand_open_bal = 0.0,grand_credit_csh = 0.0,grand_credit_clg = 0.0,grand_credit_trf = 0.0,grand_credit = 0.0,grand_debit_csh = 0.0,grand_debit_clg = 0.0,grand_debit_trf = 0.0,grand_debit = 0.0,grand_close_bal = 0.0;
        
        int i=0;
        
        i=0;
        
        if(glReportObj !=null)
        {
            System.out.println("Length1="+glReportObj.length);
            
            
            for(i=0;i<glReportObj.length;i++)
            {
                System.out.println("i = "+i);
                System.out.println("ref_ac_type="+glReportObj[i].getAcType());
                System.out.println("gl code="+glReportObj[i].getGLCode());
                System.out.println("ac_no="+glReportObj[i].getAcNo());
                System.out.println("trn_date="+glReportObj[i].getDate());
                
                if(i==0)
                {
                    data[i][0]=" ";    
                    data[i][1]=" "+String.valueOf(glReportObj[i].getGLAbbr());
                    data[i][2]=" "+String.valueOf(glReportObj[i].getGLCode());
                    temp_glcode=glReportObj[i].getGLCode();
                    temp_gltype=glReportObj[i].getGLType();
                    
                    trn_date=glReportObj[i].getDate();
                    close_bal=glReportObj[i].getClosingBalance();
                    open_bal=glReportObj[i].getOpeningBalance();
                                        
                    
                    opening_bal = glReportObj[i].getOpeningBalance();
                    
                                        
                    data[i][3]=" "+String.valueOf(glReportObj[i].getGLName());
                    data[i][4]="  ";data[i][5]="  ";data[i][6]=" ";data[i][7]=" ";data[i][8]=" ";
                    data[i][9]=" ";data[i][10]=" ";data[i][11]=" ";
                    
                }
                
                if((!(temp_gltype.equals(glReportObj[i].getGLType()))) || (temp_glcode!=glReportObj[i].getGLCode()) || (!(trn_date.equals(glReportObj[i].getDate()))))
                {
                    data[i][0]=" ";data[i][1]=" ";data[i][2]=" ";data[i][3]="Total";
                    data[i][4]=DoubleFormat.doubleToString(cash_dr,2);
                    data[i][5]=DoubleFormat.doubleToString(cash_cr,2);
                    data[i][6]=DoubleFormat.doubleToString(cg_dr,2);
                    data[i][7]=DoubleFormat.doubleToString(cg_cr,2);
                    data[i][8]=DoubleFormat.doubleToString(trf_dr,2);
                    data[i][9]=DoubleFormat.doubleToString(trf_cr,2);
                    data[i][10]=" ";data[i][11]=" ";
                    
                    cash_cr_total +=cash_cr;
                    cash_dr_total +=cash_dr;
                    cg_cr_total +=cg_cr;
                    cg_dr_total +=cg_dr;
                    trf_cr_total +=trf_cr;
                    trf_dr_total +=trf_dr;
                    i++;
                    data[i][1]=" ";data[i][2]=" ";data[i][3]=" ";data[i][4]=" ";data[i][5]=" ";data[i][6]=" ";
                    data[i][7]=" ";data[i][8]=" ";data[i][9]=" ";data[i][10]=" ";data[i][11]=" ";
                    i++;
                    
                    
                    cr_total=cash_cr+cg_cr+trf_cr;
                    dr_total=cash_dr+cg_dr+trf_dr;
                    
                    
                    closing_bal = open_bal;
                    
                    data[i][0]=" ";data[i][1]=" ";data[i][2]="O/B";data[i][3]="Total";
                    
                    
                    if(open_bal<0)
                    	data[i][4]=DoubleFormat.doubleToString(open_bal,2)+"Dr";
                    else
                    	data[i][4]=DoubleFormat.doubleToString(open_bal,2)+"Cr";
                    data[i][5]=" ";data[i][6]=" ";data[i][7]=" ";data[i][8]=" ";data[i][9]="  ";data[i][10]=" ";
                   i++;
                    
                    grand_open_bal += open_bal;
                    
                    
                    
    
                    data[i][0]=" ";data[i][1]=" ";data[i][2]="Credit";data[i][3]="Total";
                    data[i][4]=DoubleFormat.doubleToString(cr_total,2);
                    data[i][5]=" ";data[i][6]=" ";data[i][7]=" ";data[i][8]=" ";data[i][9]="  ";data[i][10]=" ";
                    i++;
                    
                    data[i][0]=" ";data[i][1]=" ";data[i][2]="Debit";data[i][3]="Total";
                    data[i][4]=DoubleFormat.doubleToString(dr_total,2);
                    data[i][5]=" ";data[i][6]=" ";data[i][7]=" ";data[i][8]=" ";data[i][9]="  ";data[i][10]=" ";
                    i++;
                    
                    closing_bal = open_bal+cr_total-dr_total;
                    
                    
                   
                    data[i][0]=" ";data[i][1]=" ";data[i][2]="C/B";data[i][3]="Total";
                    if(closing_bal<0)
                    	data[i][4]=DoubleFormat.doubleToString(closing_bal,2)+"Dr";
                    else
                    	data[i][4]=DoubleFormat.doubleToString(closing_bal,2)+"Cr";
                    	
                    
                   
                    data[i][5]=" ";data[i][6]=" ";data[i][7]=" ";data[i][8]=" ";data[i][9]="  ";data[i][10]=" ";
                    i++;
                    
                    grand_close_bal += closing_bal;
                    
                    data[i][1]=" ";data[i][2]=" ";data[i][3]=" ";data[i][4]=" ";data[i][5]=" ";data[i][6]=" ";
                    data[i][7]=" ";data[i][8]=" ";data[i][9]=" ";data[i][10]=" ";data[i][11]=" ";
                    i++;
                    data[i][1]=" ";data[i][2]=" ";data[i][3]=" ";data[i][4]=" ";data[i][5]=" ";data[i][6]=" ";
                    data[i][7]=" ";data[i][8]=" ";data[i][9]=" ";data[i][10]=" ";data[i][11]=" ";
                    i++;
                    
                    open_bal = closing_bal;
                    
    
                    if((!(temp_gltype.equals(glReportObj[i].getGLType()))) || temp_glcode!=glReportObj[i].getGLCode())
                    {
                        System.out.println("temp gl="+temp_glcode);
                        System.out.println("temp type="+temp_gltype);
                        System.out.println("gl_code="+glReportObj[i].getGLCode());
                        System.out.println("gl type="+glReportObj[i].getGLType());
                        
    
                        
                        cash_cr_total =0.0;cash_dr_total=0.0;trf_cr_total=0.0;trf_dr_total=0.0;cg_dr_total=0.0;cg_cr_total=0.0;
                        cr_total=0.0;dr_total=0.0;
                        close_bal=glReportObj[i].getClosingBalance();
                        open_bal=glReportObj[i].getOpeningBalance();
                        
                       
                        opening_bal = glReportObj[i].getOpeningBalance();
                        
                        
                        data[i][0]=" ";
                        data[i][1]=glReportObj[i].getGLAbbr();
                        data[i][2]=String.valueOf(glReportObj[i].getGLCode());
                        data[i][3]=glReportObj[i].getGLName();
                        data[i][4]=" ";
                        data[i][5]=" ";data[i][6]=" ";data[i][7]=" ";data[i][8]=" ";data[i][9]="  ";data[i][10]=" ";
                        i++;
                        
                        data[i][1]=" ";data[i][2]=" ";data[i][3]=" ";data[i][4]=" ";data[i][5]=" ";data[i][6]=" ";
                        data[i][7]=" ";data[i][8]=" ";data[i][9]=" ";data[i][10]=" ";data[i][11]=" ";
                        i++;
                        
                        temp_glcode=glReportObj[i].getGLCode();
                        temp_gltype=glReportObj[i].getGLType();
                    }
                    
                    cash_cr=0.0;cash_dr=0.0;trf_cr=0.0;trf_dr=0.0;cg_dr=0.0;cg_cr=0.0;
                    //ac_type=GLObject[i].getAcType();
                    trn_date=glReportObj[i].getDate();
                }
                
                data[i][5]=" ";data[i][6]=" ";data[i][7]=" ";data[i][8]=" ";data[i][9]=" ";data[i][4]=" ";
                data[i][0]=glReportObj[i].getDate();
                data[i][1]=glReportObj[i].getAcAbbr();
                data[i][2]=glReportObj[i].getAcNo();
                data[i][3]=glReportObj[i].getName();
                data[i][10]=glReportObj[i].getTrnDesc();
                data[i][11]=glReportObj[i].getTrnseq();
                
                if(glReportObj[i].getTrnMode().equals("C") && glReportObj[i].getCdInd().equals("D")){
                    data[i][4]=DoubleFormat.doubleToString(glReportObj[i].getTrnAmt(),2);
                    cash_dr+=glReportObj[i].getTrnAmt();
                    
                    grand_debit_csh += glReportObj[i].getTrnAmt(); 
                }
                if(glReportObj[i].getTrnMode().equals("C") && glReportObj[i].getCdInd().equals("C")){
                    data[i][5]=DoubleFormat.doubleToString(glReportObj[i].getTrnAmt(),2);
                    cash_cr+=glReportObj[i].getTrnAmt();
                    
                    grand_credit_csh += glReportObj[i].getTrnAmt(); 
                }
                if(glReportObj[i].getTrnMode().equals("G") && glReportObj[i].getCdInd().equals("D")){
                    data[i][6]=DoubleFormat.doubleToString(glReportObj[i].getTrnAmt(),2);
                    cg_dr+=glReportObj[i].getTrnAmt();
                    
                    grand_debit_clg += glReportObj[i].getTrnAmt(); 
                }
                if(glReportObj[i].getTrnMode().equals("G") && glReportObj[i].getCdInd().equals("C")){
                    data[i][7]=DoubleFormat.doubleToString(glReportObj[i].getTrnAmt(),2);
                    cg_cr+=glReportObj[i].getTrnAmt();
                    
                    grand_credit_clg += glReportObj[i].getTrnAmt(); 
                }
                if(glReportObj[i].getTrnMode().equals("T") && glReportObj[i].getCdInd().equals("D")){
                    data[i][8]=DoubleFormat.doubleToString(glReportObj[i].getTrnAmt(),2);
                    trf_dr+=glReportObj[i].getTrnAmt();
                    
                    grand_debit_trf += glReportObj[i].getTrnAmt(); 
                }
                if(glReportObj[i].getTrnMode().equals("T") && glReportObj[i].getCdInd().equals("C")){
                    data[i][9]=DoubleFormat.doubleToString(glReportObj[i].getTrnAmt(),2);
                    trf_cr+=glReportObj[i].getTrnAmt();
                    
                    grand_credit_trf += glReportObj[i].getTrnAmt(); 
                }
                
                //i++; commented by amzad on 13.07.2009               
            }
            //code changed by amzad on 10.02.2009 starts

            /*data1[i][0]=" ";data1[i][1]=" ";data1[i][2]=" ";data1[i][3]="Total";
            data1[i][4]=DoubleFormat.doubleToString(cash_dr,2);
            data1[i][5]=DoubleFormat.doubleToString(cash_cr,2);
            data1[i][6]=DoubleFormat.doubleToString(cg_dr,2);
            data1[i][7]=DoubleFormat.doubleToString(cg_cr,2);
            data1[i][8]=DoubleFormat.doubleToString(trf_dr,2);
            data1[i][9]=DoubleFormat.doubleToString(trf_cr,2);
            data1[i][10]=" ";data1[i][11]=" ";
            
            cash_cr_total +=cash_cr;
            cash_dr_total +=cash_dr;
            cg_cr_total +=cg_cr;
            cg_dr_total +=cg_dr;
            trf_cr_total +=trf_cr;
            trf_dr_total +=trf_dr;
            
            System.out.println("----- TOTAL -----"+trf_dr_total);
            
            i++;
            data1[i][1]=" ";data1[i][2]=" ";data1[i][3]=" ";data1[i][4]=" ";data1[i][5]=" ";data1[i][6]=" ";
            data1[i][7]=" ";data1[i][8]=" ";data1[i][9]=" ";data1[i][10]=" ";data1[i][11]=" ";
            i++;
            
            
            cr_total=cash_cr+cg_cr+trf_cr;
            dr_total=cash_dr+cg_dr+trf_dr;
            
            data1[i][0]=" ";data1[i][1]=" ";data1[i][2]="O/B";data1[i][3]="Total";
            data1[i][4]=DoubleFormat.doubleToString(open_bal,2);
            data1[i][5]=" ";data1[i][6]=" ";data1[i][7]=" ";data1[i][8]=" ";data1[i][9]="  ";data1[i][10]=" ";data1[i][11]=" ";
            i++;
            
            grand_open_bal += open_bal;

            data1[i][0]=" ";data1[i][1]=" ";data1[i][2]="Credit";data1[i][3]="Total";
            data1[i][4]=DoubleFormat.doubleToString(cr_total,2);
            data1[i][5]=" ";data1[i][6]=" ";data1[i][7]=" ";data1[i][8]=" ";data1[i][9]="  ";data1[i][10]=" ";
            i++;
            
            data1[i][0]=" ";data1[i][1]=" ";data1[i][2]="Debit";data1[i][3]="Total";
            data1[i][4]=DoubleFormat.doubleToString(dr_total,2);
            data1[i][5]=" ";data1[i][6]=" ";data1[i][7]=" ";data1[i][8]=" ";data1[i][9]="  ";data1[i][10]=" ";
            i++;
            
            closing_bal = open_bal+cr_total-dr_total;
            
            data1[i][0]=" ";data1[i][1]=" ";data1[i][2]="C/B";data1[i][3]="Total";
            data1[i][4]=DoubleFormat.doubleToString(closing_bal,2);
            data1[i][5]=" ";data1[i][6]=" ";data1[i][7]=" ";data1[i][8]=" ";data1[i][9]="  ";data1[i][10]=" ";
            i++;
            
            grand_close_bal += closing_bal;
            
            data1[i][1]=" ";data1[i][2]=" ";data1[i][3]=" ";data1[i][4]=" ";data1[i][5]=" ";data1[i][6]=" ";
            data1[i][7]=" ";data1[i][8]=" ";data1[i][9]=" ";data1[i][10]=" ";data1[i][11]=" ";
            i++;
            data1[i][1]=" ";data1[i][2]=" ";data1[i][3]=" ";data1[i][4]=" ";data1[i][5]=" ";data1[i][6]=" ";
            data1[i][7]=" ";data1[i][8]=" ";data1[i][9]=" ";data1[i][10]=" ";data1[i][11]=" ";
            i++;
            
            
                       

            data1[i][0]=" ";data1[i][1]=" ";data1[i][2]=" ";data1[i][3]="Grand Total";
            data1[i][4]=DoubleFormat.doubleToString(grand_debit_csh,2);
            data1[i][5]=DoubleFormat.doubleToString(grand_credit_csh,2);
            data1[i][6]=DoubleFormat.doubleToString(grand_debit_clg,2);
            data1[i][7]=DoubleFormat.doubleToString(grand_credit_clg,2);
            data1[i][8]=DoubleFormat.doubleToString(grand_debit_trf,2);
            data1[i][9]=DoubleFormat.doubleToString(grand_credit_trf,2);
            data1[i][10]=" ";data1[i][11]=" ";
         i++;
         data1[i][1]=" ";data1[i][2]=" ";data1[i][3]=" ";data1[i][4]=" ";data1[i][5]=" ";data1[i][6]=" ";
         data1[i][7]=" ";data1[i][8]=" ";data1[i][9]=" ";data1[i][10]=" ";data1[i][11]=" ";
            i++;
            data1[i][1]=" ";data1[i][2]=" ";data1[i][3]=" ";data1[i][4]=" ";data1[i][5]=" ";data1[i][6]=" ";
            data1[i][7]=" ";data1[i][8]=" ";data1[i][9]=" ";data1[i][10]=" ";data1[i][11]=" ";
            i++;
            
            
            grand_credit = grand_credit_csh+grand_credit_clg+grand_credit_trf;
            grand_debit = grand_debit_csh+grand_debit_clg+grand_debit_trf;
            
            data1[i][0]=" ";data1[i][1]=" ";data1[i][2]="O/B";data1[i][3]="Total";
            data1[i][4]=DoubleFormat.doubleToString(grand_open_bal,2);
            data1[i][5]=" ";data1[i][6]=" ";data1[i][7]=" ";data1[i][8]=" ";data1[i][9]="  ";data1[i][10]=" ";
            i++;

            data1[i][0]=" ";data1[i][1]=" ";data1[i][2]="Credit";data1[i][3]="Total";
            data1[i][4]=DoubleFormat.doubleToString(grand_credit,2);
            data1[i][5]=" ";data1[i][6]=" ";data1[i][7]=" ";data1[i][8]=" ";data1[i][9]="  ";data1[i][10]=" ";
            i++;
            
            data1[i][0]=" ";data1[i][1]=" ";data1[i][2]="Debit";data1[i][3]="Total";
            data1[i][4]=DoubleFormat.doubleToString(grand_debit,2);
            data1[i][5]=" ";data1[i][6]=" ";data1[i][7]=" ";data1[i][8]=" ";data1[i][9]="  ";data1[i][10]=" ";
            i++;
            
            data1[i][0]=" ";data1[i][1]=" ";data1[i][2]="C/B";data1[i][3]="Total";
            data1[i][4]=DoubleFormat.doubleToString(grand_close_bal,2);
            data1[i][5]=" ";data1[i][6]=" ";data1[i][7]=" ";data1[i][8]=" ";data1[i][9]="  ";data1[i][10]=" ";
            i++;
            
            data1[i][1]=" ";data1[i][2]=" ";data1[i][3]=" ";data1[i][4]=" ";data1[i][5]=" ";data1[i][6]=" ";
            data1[i][7]=" ";data1[i][8]=" ";data1[i][9]=" ";data1[i][10]=" ";data1[i][11]=" ";
            i++;
            data1[i][1]=" ";data1[i][2]=" ";data1[i][3]=" ";data1[i][4]=" ";data1[i][5]=" ";data1[i][6]=" ";
            data1[i][7]=" ";data1[i][8]=" ";data1[i][9]=" ";data1[i][10]=" ";data1[i][11]=" ";
            i++;
*/        }

            //changes made by amzad on 10.02.2009 ends
        
        //showreport ends
        
        req.setAttribute("GlscheduleReportObj", data);
        //req.setAttribute("GlscheduleReportObj1", data1);
    }catch(Exception e){
    	e.printStackTrace();
    }
    }
    else
    {
    	req.setAttribute("msg","Records NOt Found");
    }
    
    
    //show records end
    
   
	
	
	//view code ends
    //added by amzad on 24.01.2009 starts
    if(schedule.getCodes().equalsIgnoreCase("ALL_Codes")){
  	  strType="";
  	  strCode="";
  	  req.setAttribute("StringCode", strType);
  	  req.setAttribute("StringCode", strCode); 
      
    }   
    else
    {
       
        for(int i=0;i<gldelegate.getCodeTypes(schedule.getFromDate(), schedule.getToDate()).length;i++)
        {
            if(schedule.getCodes().equals(codetype[i][1]))
            {
          	  req.setAttribute("StringType", codetype[i][2]);
          	   
                
            }
        }
        //////////
        for(int i=0;i<gldelegate.getCodeTypes(schedule.getFromDate(), schedule.getToDate()).length;i++)
        {
            if(schedule.getToCodes().equals(codetype[i][1]))
            {
          	  
          	  req.setAttribute("StringCode", codetype[i][2]); 
                
            }
        }
        
        
        
    }
    path=MenuNameReader.getScreenProperties(schedule.getPageId());
	  gldelegate=new GLDelegate();
	  setGLOpeningInitParams(req, path, gldelegate);
	  schedule.setFromDate(schedule.getFromDate());
	  schedule.setToDate(schedule.getToDate());
	  schedule.setTypes(schedule.getTypes());
	  schedule.setCodes(schedule.getCodes());
	  schedule.setToTypes(schedule.getToTypes());
	  schedule.setToCodes(schedule.getToCodes());
	  
	  System.out.println("the path from MenuNameReader is "+path);
	  req.setAttribute("pageId",path);
	  req.setAttribute("comboStatusType","true");
	  req.setAttribute("comboStatusCode","true");
	  codetype=gldelegate.getCodeTypes(schedule.getFromDate(), schedule.getToDate());
	  req.setAttribute("moduleobj",gldelegate.getMainMods());
	  req.setAttribute("codetype",gldelegate.getCodeTypes(schedule.getFromDate(), schedule.getToDate()));
	
    
    //added by amzad on 24.01.2009 ends
    
    
    
	   
     req.setAttribute("GLReportObj", glReportObj);
	 
    return map.findForward(ResultHelp.getSuccess());
}


if(schedule.getFlag().equalsIgnoreCase("save"))
{
	masterObject.generalLedger.GLReportObject glReportObj[]=null;
	ModuleObject[] modObj=gldelegate.getMainMods();
	//view code starts
	String type_itemvalue,code_itemcount,code_to_itemcount;
    String string_qry=" ";
    int from_glno=0,to_glno=0;
    String from_gltype="",to_gltype="";
    Object data[][] = new Object[glReportObj.length][12];
	System.out.println("Inside save ");
	 //TO save to an excel Sheet
	 res.setContentType("application/.csv");
       res.setHeader("Content-disposition", "attachment;filename=output.csv");
       
       java.io.PrintWriter out = res.getWriter();
       out.print("\n");
       out.print("\n");
       out.print("\n");
       out.print(",");out.print(",");out.print(",");
       out.print("GLSchedule Details: ");
       out.print("\n");
       out.print("\n");
		   /*HSSFCell cell = row.createCell((short)0);
		   cell.setCellValue(1);*/
       	out.print("Date"); out.print(",");
  		out.print("Acc Type"); out.print(",");
  		out.print("Narration"); out.print(",");
  		out.print("Cash Dr"); out.print(",");
  		out.print("Cash Cr"); out.print(",");
  		out.print("Clearing Dr"); out.print(",");
  		out.print("Clearing Cr"); out.print(",");
  		out.print("Trf Dr"); out.print(",");
  		out.print("Trf Cr"); out.print(",");
  		out.print("Tr Type"); out.print(",");
  		out.print("Seq No"); out.print("\n");
  		 if(!(schedule.getCodes().toString() .equalsIgnoreCase("ALL Codes")) && (!(schedule.getTypes().toString() .toString() .equalsIgnoreCase("ALL Types"))))
  	    {
  	        from_glno=Integer.parseInt(schedule.getCodes().toString());
  	        to_glno=Integer.parseInt(schedule.getToCodes().toString());
  	        if(modObj!=null){
  	        	for(int i=0;i<modObj.length;i++){
  	        		if(schedule.getTypes().equalsIgnoreCase(modObj[i].getModuleAbbrv())){
  	        			from_gltype=modObj[i].getModuleCode();
  	        		}
  	        		if(schedule.getToTypes().equalsIgnoreCase(modObj[i].getModuleAbbrv())){
  	        			to_gltype=modObj[i].getModuleCode();
  	        		}
  	        	}
  	        	
  	        }
  	        
  	     }
  	    //show records start
  	    System.out.println("From date="+schedule.getFromDate());
  	    System.out.println("From date="+schedule.getToDate());
  	    System.out.println("From date= from_gltype"+from_gltype);
  	    System.out.println("From date="+schedule.getCodes());
  	    System.out.println("From date= to_gltype"+to_gltype);
  	    System.out.println("To Codes="+schedule.getToCodes());
  	    glReportObj=gldelegate.glScheduleDetails(schedule.getFromDate(), schedule.getToDate(), from_gltype, from_glno,to_gltype,to_glno);
  	    System.out.println("glReportObj length Amzad1=" +glReportObj.length );
  	    System.out.println("GlReport obj created");
  	    if(glReportObj.length>0)
  	    {   try{
  	        System.out.println("glReportObj length Amzad=" +glReportObj.length );
  	        //showreport starts
  	       // Object data[][] = new Object[glReportObj.length][12];
  	        //Object data1[][] = new Object[17][12];
  	        int temp_glcode=0;
  	        String temp_gltype="";
  	        String trn_date=null;//,ac_type=null;
  	        double closing_bal=0.0,opening_bal=0.0;
  	        double cr_total=0.0,dr_total=0.0,total=0.0,net_trans=0.0;
  	        double cash_dr_total=0.0,cash_cr_total=0.0,cg_cr_total=0.0,cg_dr_total=0.0,trf_dr_total=0.0,trf_cr_total=0.0;
  	        double cash_cr=0.0,cash_dr=0.0,cg_cr=0.0,cg_dr=0.0,trf_dr=0.0,trf_cr=0.0;
  	        double close_bal=0.0,open_bal=0.0;
  	        double grand_open_bal = 0.0,grand_credit_csh = 0.0,grand_credit_clg = 0.0,grand_credit_trf = 0.0,grand_credit = 0.0,grand_debit_csh = 0.0,grand_debit_clg = 0.0,grand_debit_trf = 0.0,grand_debit = 0.0,grand_close_bal = 0.0;
  	        
  	        int i=0;
  	        
  	        i=0;
  	        
  	        if(glReportObj !=null)
  	        {
  	            System.out.println("Length1="+glReportObj.length);
  	            
  	            
  	            for(i=0;i<glReportObj.length;i++)
  	            {
  	                System.out.println("i = "+i);
  	                System.out.println("ref_ac_type="+glReportObj[i].getAcType());
  	                System.out.println("gl code="+glReportObj[i].getGLCode());
  	                System.out.println("ac_no="+glReportObj[i].getAcNo());
  	                System.out.println("trn_date="+glReportObj[i].getDate());
  	                
  	                if(i==0)
  	                {
  	                    data[i][0]=" ";    
  	                    data[i][1]=" "+String.valueOf(glReportObj[i].getGLAbbr());
  	                    data[i][2]=" "+String.valueOf(glReportObj[i].getGLCode());
  	                    temp_glcode=glReportObj[i].getGLCode();
  	                    temp_gltype=glReportObj[i].getGLType();
  	                    
  	                    trn_date=glReportObj[i].getDate();
  	                    close_bal=glReportObj[i].getClosingBalance();
  	                    open_bal=glReportObj[i].getOpeningBalance();
  	                                        
  	                    
  	                    opening_bal = glReportObj[i].getOpeningBalance();
  	                    
  	                                        
  	                    data[i][3]=" "+String.valueOf(glReportObj[i].getGLName());
  	                    data[i][4]="  ";data[i][5]="  ";data[i][6]=" ";data[i][7]=" ";data[i][8]=" ";
  	                    data[i][9]=" ";data[i][10]=" ";data[i][11]=" ";
  	                    
  	                }
  	                
  	                if((!(temp_gltype.equals(glReportObj[i].getGLType()))) || (temp_glcode!=glReportObj[i].getGLCode()) || (!(trn_date.equals(glReportObj[i].getDate()))))
  	                {
  	                    data[i][0]=" ";data[i][1]=" ";data[i][2]=" ";data[i][3]="Total";
  	                    data[i][4]=DoubleFormat.doubleToString(cash_dr,2);
  	                    data[i][5]=DoubleFormat.doubleToString(cash_cr,2);
  	                    data[i][6]=DoubleFormat.doubleToString(cg_dr,2);
  	                    data[i][7]=DoubleFormat.doubleToString(cg_cr,2);
  	                    data[i][8]=DoubleFormat.doubleToString(trf_dr,2);
  	                    data[i][9]=DoubleFormat.doubleToString(trf_cr,2);
  	                    data[i][10]=" ";data[i][11]=" ";
  	                    
  	                    cash_cr_total +=cash_cr;
  	                    cash_dr_total +=cash_dr;
  	                    cg_cr_total +=cg_cr;
  	                    cg_dr_total +=cg_dr;
  	                    trf_cr_total +=trf_cr;
  	                    trf_dr_total +=trf_dr;
  	                    i++;
  	                    data[i][1]=" ";data[i][2]=" ";data[i][3]=" ";data[i][4]=" ";data[i][5]=" ";data[i][6]=" ";
  	                    data[i][7]=" ";data[i][8]=" ";data[i][9]=" ";data[i][10]=" ";data[i][11]=" ";
  	                    i++;
  	                    
  	                    
  	                    cr_total=cash_cr+cg_cr+trf_cr;
  	                    dr_total=cash_dr+cg_dr+trf_dr;
  	                    
  	                    
  	                    closing_bal = open_bal;
  	                    
  	                    data[i][0]=" ";data[i][1]=" ";data[i][2]="O/B";data[i][3]="Total";
  	                    
  	                    
  	                    if(open_bal<0)
  	                    	data[i][4]=DoubleFormat.doubleToString(open_bal,2)+"Dr";
  	                    else
  	                    	data[i][4]=DoubleFormat.doubleToString(open_bal,2)+"Cr";
  	                    data[i][5]=" ";data[i][6]=" ";data[i][7]=" ";data[i][8]=" ";data[i][9]="  ";data[i][10]=" ";
  	                   i++;
  	                    
  	                    grand_open_bal += open_bal;
  	                    
  	                    
  	                    
  	    
  	                    data[i][0]=" ";data[i][1]=" ";data[i][2]="Credit";data[i][3]="Total";
  	                    data[i][4]=DoubleFormat.doubleToString(cr_total,2);
  	                    data[i][5]=" ";data[i][6]=" ";data[i][7]=" ";data[i][8]=" ";data[i][9]="  ";data[i][10]=" ";
  	                    i++;
  	                    
  	                    data[i][0]=" ";data[i][1]=" ";data[i][2]="Debit";data[i][3]="Total";
  	                    data[i][4]=DoubleFormat.doubleToString(dr_total,2);
  	                    data[i][5]=" ";data[i][6]=" ";data[i][7]=" ";data[i][8]=" ";data[i][9]="  ";data[i][10]=" ";
  	                    i++;
  	                    
  	                    closing_bal = open_bal+cr_total-dr_total;
  	                    
  	                    
  	                   
  	                    data[i][0]=" ";data[i][1]=" ";data[i][2]="C/B";data[i][3]="Total";
  	                    if(closing_bal<0)
  	                    	data[i][4]=DoubleFormat.doubleToString(closing_bal,2)+"Dr";
  	                    else
  	                    	data[i][4]=DoubleFormat.doubleToString(closing_bal,2)+"Cr";
  	                    	
  	                    
  	                   
  	                    data[i][5]=" ";data[i][6]=" ";data[i][7]=" ";data[i][8]=" ";data[i][9]="  ";data[i][10]=" ";
  	                    i++;
  	                    
  	                    grand_close_bal += closing_bal;
  	                    
  	                    data[i][1]=" ";data[i][2]=" ";data[i][3]=" ";data[i][4]=" ";data[i][5]=" ";data[i][6]=" ";
  	                    data[i][7]=" ";data[i][8]=" ";data[i][9]=" ";data[i][10]=" ";data[i][11]=" ";
  	                    i++;
  	                    data[i][1]=" ";data[i][2]=" ";data[i][3]=" ";data[i][4]=" ";data[i][5]=" ";data[i][6]=" ";
  	                    data[i][7]=" ";data[i][8]=" ";data[i][9]=" ";data[i][10]=" ";data[i][11]=" ";
  	                    i++;
  	                    
  	                    open_bal = closing_bal;
  	                    
  	    
  	                    if((!(temp_gltype.equals(glReportObj[i].getGLType()))) || temp_glcode!=glReportObj[i].getGLCode())
  	                    {
  	                        System.out.println("temp gl="+temp_glcode);
  	                        System.out.println("temp type="+temp_gltype);
  	                        System.out.println("gl_code="+glReportObj[i].getGLCode());
  	                        System.out.println("gl type="+glReportObj[i].getGLType());
  	                        
  	    
  	                        
  	                        cash_cr_total =0.0;cash_dr_total=0.0;trf_cr_total=0.0;trf_dr_total=0.0;cg_dr_total=0.0;cg_cr_total=0.0;
  	                        cr_total=0.0;dr_total=0.0;
  	                        close_bal=glReportObj[i].getClosingBalance();
  	                        open_bal=glReportObj[i].getOpeningBalance();
  	                        
  	                       
  	                        opening_bal = glReportObj[i].getOpeningBalance();
  	                        
  	                        
  	                        data[i][0]=" ";
  	                        data[i][1]=glReportObj[i].getGLAbbr();
  	                        data[i][2]=String.valueOf(glReportObj[i].getGLCode());
  	                        data[i][3]=glReportObj[i].getGLName();
  	                        data[i][4]=" ";
  	                        data[i][5]=" ";data[i][6]=" ";data[i][7]=" ";data[i][8]=" ";data[i][9]="  ";data[i][10]=" ";
  	                        i++;
  	                        
  	                        data[i][1]=" ";data[i][2]=" ";data[i][3]=" ";data[i][4]=" ";data[i][5]=" ";data[i][6]=" ";
  	                        data[i][7]=" ";data[i][8]=" ";data[i][9]=" ";data[i][10]=" ";data[i][11]=" ";
  	                        i++;
  	                        
  	                        temp_glcode=glReportObj[i].getGLCode();
  	                        temp_gltype=glReportObj[i].getGLType();
  	                    }
  	                    
  	                    cash_cr=0.0;cash_dr=0.0;trf_cr=0.0;trf_dr=0.0;cg_dr=0.0;cg_cr=0.0;
  	                    //ac_type=GLObject[i].getAcType();
  	                    trn_date=glReportObj[i].getDate();
  	                }
  	                
  	                data[i][5]=" ";data[i][6]=" ";data[i][7]=" ";data[i][8]=" ";data[i][9]=" ";data[i][4]=" ";
  	                data[i][0]=glReportObj[i].getDate();
  	                data[i][1]=glReportObj[i].getAcAbbr();
  	                data[i][2]=glReportObj[i].getAcNo();
  	                data[i][3]=glReportObj[i].getName();
  	                data[i][10]=glReportObj[i].getTrnDesc();
  	                data[i][11]=glReportObj[i].getTrnseq();
  	                
  	                if(glReportObj[i].getTrnMode().equals("C") && glReportObj[i].getCdInd().equals("D")){
  	                    data[i][4]=DoubleFormat.doubleToString(glReportObj[i].getTrnAmt(),2);
  	                    cash_dr+=glReportObj[i].getTrnAmt();
  	                    
  	                    grand_debit_csh += glReportObj[i].getTrnAmt(); 
  	                }
  	                if(glReportObj[i].getTrnMode().equals("C") && glReportObj[i].getCdInd().equals("C")){
  	                    data[i][5]=DoubleFormat.doubleToString(glReportObj[i].getTrnAmt(),2);
  	                    cash_cr+=glReportObj[i].getTrnAmt();
  	                    
  	                    grand_credit_csh += glReportObj[i].getTrnAmt(); 
  	                }
  	                if(glReportObj[i].getTrnMode().equals("G") && glReportObj[i].getCdInd().equals("D")){
  	                    data[i][6]=DoubleFormat.doubleToString(glReportObj[i].getTrnAmt(),2);
  	                    cg_dr+=glReportObj[i].getTrnAmt();
  	                    
  	                    grand_debit_clg += glReportObj[i].getTrnAmt(); 
  	                }
  	                if(glReportObj[i].getTrnMode().equals("G") && glReportObj[i].getCdInd().equals("C")){
  	                    data[i][7]=DoubleFormat.doubleToString(glReportObj[i].getTrnAmt(),2);
  	                    cg_cr+=glReportObj[i].getTrnAmt();
  	                    
  	                    grand_credit_clg += glReportObj[i].getTrnAmt(); 
  	                }
  	                if(glReportObj[i].getTrnMode().equals("T") && glReportObj[i].getCdInd().equals("D")){
  	                    data[i][8]=DoubleFormat.doubleToString(glReportObj[i].getTrnAmt(),2);
  	                    trf_dr+=glReportObj[i].getTrnAmt();
  	                    
  	                    grand_debit_trf += glReportObj[i].getTrnAmt(); 
  	                }
  	                if(glReportObj[i].getTrnMode().equals("T") && glReportObj[i].getCdInd().equals("C")){
  	                    data[i][9]=DoubleFormat.doubleToString(glReportObj[i].getTrnAmt(),2);
  	                    trf_cr+=glReportObj[i].getTrnAmt();
  	                    
  	                    grand_credit_trf += glReportObj[i].getTrnAmt(); 
  	                }
  	                
  	                //i++; commented by amzad on 13.07.2009               
  	            }
  	        }
  	  }catch(Exception e){
      	e.printStackTrace();
      }
      }
      
      for(int k=0;k<data.length;k++){
			  for(int j=0;j<12;j++){
 			out.print(data[k][j]);
 			out.print(",");
			  }
			  out.println();
 			}
      req.setAttribute("msg","Saved to excel file in C:");
	    return null;
}






//code for viewing records(view button ) end



//clear fun end
		    		  
		    		  
		    		/* if(daily_post.getForward().equalsIgnoreCase("submit")){
		    			  System.out.println("The date is "+daily_post.getDate());
		    			  String result=gldelegate.dailyposting(daily_post.getDate());
		    			  System.out.println("The rersult is------->"+result);
		    			  if(result.equals("1")){req.setAttribute("result","Posting Successful");}
		    			  else{req.setAttribute("result","Posting Failed");}
		    		  }*/
		    		  //commoncode start
		    		  path=MenuNameReader.getScreenProperties(schedule.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  schedule.setFromDate(schedule.getFromDate());
		    		  schedule.setToDate(schedule.getFromDate());
		    		  
		    		  
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  req.setAttribute("comboStatusType","true");
		    		  req.setAttribute("comboStatusCode","true");
		    		  codetype=gldelegate.getCodeTypes(schedule.getFromDate(), schedule.getToDate());
		    		  req.setAttribute("moduleobj",gldelegate.getMainMods());
		    		  req.setAttribute("codetype",gldelegate.getCodeTypes(schedule.getFromDate(), schedule.getToDate()));
		    		 
		    		  
		    		  
		    		  
		    		  
		    		  
		    		  //commoncode end
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
		
/*  *************Gl Monthly Reciepts & Payments Start********************** */
		
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyRecieptsPaymentsMenu"))
		{
	      try{
	    	  MonthlyRecieptsPayments monthly_reciepts_payments=(MonthlyRecieptsPayments)form;
	    	  req.setAttribute("msg","");
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+monthly_reciepts_payments);
	    	  System.out.println("the page id is "+monthly_reciepts_payments.getPageId());
	    	  req.setAttribute("pagenum", monthly_reciepts_payments.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(monthly_reciepts_payments.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(monthly_reciepts_payments.getPageId());
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
}else if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyRecieptsPayments")){
			
			try{
				MonthlyRecieptsPayments monthly_reciepts_payments=(MonthlyRecieptsPayments)form;
				req.setAttribute("msg","");
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+monthly_reciepts_payments);
		    	  System.out.println("the page id is "+monthly_reciepts_payments.getPageId());
		    	  req.setAttribute("pagenum", monthly_reciepts_payments.getPageId());
		    	  path=MenuNameReader.getScreenProperties(monthly_reciepts_payments.getPageId());
	    		  gldelegate=new GLDelegate();
	    		  setGLOpeningInitParams(req, path, gldelegate);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(monthly_reciepts_payments.getPageId()))
		    	  {
		    		 
		    		  
		    		  System.out.println("The button is"+monthly_reciepts_payments.getForward());
		    		  //code for view starts
		    		  
		    		  if(monthly_reciepts_payments.getFlag().equalsIgnoreCase("view") || monthly_reciepts_payments.getFlag().equalsIgnoreCase("file")){
		    			 
		    			  GLObject[] array_globject=null;
		    			  Object[][] data=null;
		    			  Object[][] data1=null;
		    			  String gl_type=null;
		    			    String gl_code=null,f_date,t_date,l_date="";
		    			    double opening=0.0,closing=0.0;
		    			    double credit_sum=0.0,debit_sum=0.0;
		    			  String detail[]=null;
		    			  String month=null;
		    			  String month1=null;
		    			  try{
		    				  
			    			  if(Integer.parseInt(monthly_reciepts_payments.getFromMonth())<9){
			    				  month="0"+monthly_reciepts_payments.getFromMonth();
			    			  }
			    			  else{
			    				  month=monthly_reciepts_payments.getFromMonth();
			    			  }
			    			  if(Integer.parseInt(monthly_reciepts_payments.getToMonth())<9){
			    				  month1="0"+monthly_reciepts_payments.getToMonth();
			    			  }
			    			  else{
			    				  month1=monthly_reciepts_payments.getToMonth();
			    			  }
		    				  int from_date = Integer.parseInt(monthly_reciepts_payments.getFromYear().toString().trim()+month.toString().trim());
		    					
		    					
		    					int to_date = Integer.parseInt(monthly_reciepts_payments.getToYear().toString().trim()+month1.toString().trim());
		    					
		    					array_globject=gldelegate.getRecieptAndPayment(from_date,to_date);
		    					
		    					
		    			
		    					
		    					System.out.println("Length"+array_globject.length);
		    					
		    					
		    					data=new Object[array_globject.length][6];
		    					data1=new Object[3][6];
		    					
		    					
		    					
		    					f_date="01"+"/"+monthly_reciepts_payments.getFromMonth().toString().trim()+"/"+monthly_reciepts_payments.getFromYear().toString().trim();
		    					t_date="01"+"/"+monthly_reciepts_payments.getToMonth().toString().trim()+"/"+monthly_reciepts_payments.getToYear().toString().trim();
		    					l_date=Validations.lastDayOfMonth(t_date);
		    					System.out.println("Before calling the method in action getCashGlTypeCode");
		    				    detail=gldelegate.getCashGlTypeCode();
		    				    System.out.println("After calling the method in action getCashGlTypeCode");
		    		            gl_type=detail[0];
		    		            gl_code=detail[1];
		    		            System.out.println("beforeCalling the method opening balance in action");
		    		            opening=gldelegate.openingBalance(gl_type,Integer.parseInt(gl_code),f_date);
		    		            System.out.println("After comingbac of the method and befor calling the mehtod closingbalance");
		    		            closing=gldelegate.closingBalance(gl_type,Integer.parseInt(gl_code),l_date);
		    		            System.out.println("After successfully coming back of the method closing balance");
		    		       
		    				
		    		            data1[0][0]="";
		    		            data1[0][1]=gl_type;
		    		            data1[0][2]=gl_code;
		    		            data1[0][3]="Cash on hand";
		    		            data1[0][4]=DoubleFormat.doubleToString(Math.abs(opening),2);
		    		            data1[0][5]="";
		    		            
		    			        
		    				for(int row=0;row<array_globject.length;row++)
		    				{
		    		   			data[row][0]=String.valueOf(row+1);
		    		        	data[row][1]=array_globject[row].getAcType();
		    		        	data[row][2]=String.valueOf(array_globject[row].getCode());
		    		        	data[row][3]=array_globject[row].getGlName();
		    		      		data[row][4]=DoubleFormat.doubleToString(array_globject[row].getCredit_sum(),2);
		    		      		data[row][5]=DoubleFormat.doubleToString(array_globject[row].getDebit_sum(),2);
		    		      		
		    		      		credit_sum += array_globject[row].getCredit_sum();
		    		      		debit_sum += array_globject[row].getDebit_sum();
		    		    			
		    			  		  			  		
		    			  		
		    			  		
		    			  		if(row==array_globject.length-1)
		    			  		{
		    		     			break ;
		    		     		}
		    		        }
		    				
		    				data1[1][0]="";
		    		    	data1[1][1]="";
		    		    	data1[1][2]="";
		    		    	data1[1][3]="";
		    		    	data1[1][4]="";
		    		  		data1[1][5]=DoubleFormat.doubleToString(Math.abs(closing),2);
		    		  		
		    		  		data1[2][0]="";
		    		    	data1[2][1]="";
		    		    	data1[2][2]="";
		    		    	data1[2][3]="Total";
		    		  		data1[2][4]=DoubleFormat.doubleToString(credit_sum,2);
		    		  		data1[2][5]=DoubleFormat.doubleToString(debit_sum,2);
		    		        
		    		}
		    		        		
		    		catch (Exception e) {e.printStackTrace();}
		    			
                       req.setAttribute("rpdataObj", data);
                       req.setAttribute("rpdataObj1", data1);
                       if(monthly_reciepts_payments.getFlag().equalsIgnoreCase("file")){
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
           	            cell1.setCellValue("Monthly Reciepts And Payments For "+Validations.lastDayOfMonth(l_date));
                           HSSFRow row2 = sheet.createRow((short)5);
             			    HSSFCell cell2=row2.createCell((short)3);
             			    cell2.setCellStyle(cellStyle);
                           cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
                           cell2.setCellValue("Serial No");
                           HSSFCell cell3=row2.createCell((short)4);
             			    cell3.setCellStyle(cellStyle);
                           cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
                           cell3.setCellValue("Accont Details");
                           HSSFCell cell9=row2.createCell((short)7);
             			    cell9.setCellStyle(cellStyle);
                           cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
                           cell9.setCellValue("Reciepts");
                           HSSFCell cell10=row2.createCell((short)8);
             			    cell10.setCellStyle(cellStyle);
                           cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
                           cell10.setCellValue("Payments");
                           HSSFRow row3 = sheet.createRow((short)6);
                           HSSFCell cell15=row3.createCell((short)3);
              	  			cell15.setCellStyle(cellStyle);
              	            cell15.setCellType(HSSFCell.CELL_TYPE_STRING);
              	            cell15.setCellValue("Serial No");
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
           	                 
           	            
           	  			
           	            HSSFCell cell11=row3.createCell((short)7);
           	  			cell11.setCellStyle(cellStyle);
           	            cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
           	            cell11.setCellValue("Reciepts");
           	            HSSFCell cell12=row3.createCell((short)8);
           	  			cell12.setCellStyle(cellStyle);
           	            cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
           	            cell12.setCellValue("Payments");
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
           	  				   
           	  				 }
           	  				for(int l=0;l<data1.length;l++){
           	  					HSSFRow rowcount = sheet.createRow((short)r++);
           	  					rowcount.createCell((short)3).setCellValue(data1[l][0].toString());
           		  				   rowcount.createCell((short)4).setCellValue(data1[l][1].toString());
           		  				   rowcount.createCell((short)5).setCellValue(data1[l][2].toString());
           		  				   rowcount.createCell((short)6).setCellValue(data1[l][3].toString());
           		  				   rowcount.createCell((short)7).setCellValue(data1[l][4].toString());
           		  				   rowcount.createCell((short)8).setCellValue(data1[l][5].toString());
           		  				   
           	  				}
           	  				
           	  				
           	  				}
           	  			FileOutputStream fileOut = new FileOutputStream("c:\\Reports\\RP\\"+filename+".xls");
           	  			wb.write(fileOut);
           	  			fileOut.close();   
           	  			req.setAttribute("msg","Successfully saved to file....!\n file path is c:\\Reports\\RP\\"+filename+".xls");
           	  			     }catch ( Exception ex ){       
           	  			     }     
  
		    			  
                         
		    		  }
		    		  //code for view ends
                     
		    		  path=MenuNameReader.getScreenProperties(monthly_reciepts_payments.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  return map.findForward(ResultHelp.getSuccess());
		    	  }
		    		  if(monthly_reciepts_payments.getFlag().equalsIgnoreCase("save"))
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
                 	        out.print("Reciepts And Payments Details: ");
                 	        out.print("\n");
                 	        out.print("\n");
                    		   /*HSSFCell cell = row.createCell((short)0);
                    		   cell.setCellValue(1);*/
                 	      
                 	       out.print("Sr No"); out.print(",");
                 	       out.print(",");out.print("Account Details");
                 	       out.print(",");out.print(",");
                 	       out.print("Receipts");out.print(",");
                 	       out.print("Payments");out.print(",");
                 	       out.println();
                 	        out.print("Sr No"); out.print(",");
                    		out.print("GL Type"); out.print(",");
                    		out.print("GL Code"); out.print(",");
                    		out.print("Name"); out.print(",");
                    		out.print("Reciepts"); out.print(",");
                    		out.print("Payments"); out.print("\n");
                    		
                    		GLObject[] array_globject=null;
  		    			  Object[][] data=null;
  		    			  Object[][] data1=null;
  		    			  String gl_type=null;
  		    			    String gl_code=null,f_date,t_date,l_date="";
  		    			    double opening=0.0,closing=0.0;
  		    			    double credit_sum=0.0,debit_sum=0.0;
  		    			  String detail[]=null;
  		    			  String month=null;
  		    			  String month1=null;
  		    			  try{
  		    				  
  			    			  if(Integer.parseInt(monthly_reciepts_payments.getFromMonth())<9){
  			    				  month="0"+monthly_reciepts_payments.getFromMonth();
  			    			  }
  			    			  else{
  			    				  month=monthly_reciepts_payments.getFromMonth();
  			    			  }
  			    			  if(Integer.parseInt(monthly_reciepts_payments.getToMonth())<9){
  			    				  month1="0"+monthly_reciepts_payments.getToMonth();
  			    			  }
  			    			  else{
  			    				  month1=monthly_reciepts_payments.getToMonth();
  			    			  }
  		    				  int from_date = Integer.parseInt(monthly_reciepts_payments.getFromYear().toString().trim()+month.toString().trim());
  		    					
  		    					
  		    					int to_date = Integer.parseInt(monthly_reciepts_payments.getToYear().toString().trim()+month1.toString().trim());
  		    					
  		    					array_globject=gldelegate.getRecieptAndPayment(from_date,to_date);
  		    					
  		    					
  		    			
  		    					
  		    					System.out.println("Length"+array_globject.length);
  		    					
  		    					
  		    					data=new Object[array_globject.length][6];
  		    					data1=new Object[3][6];
  		    					
  		    					
  		    					
  		    					f_date="01"+"/"+monthly_reciepts_payments.getFromMonth().toString().trim()+"/"+monthly_reciepts_payments.getFromYear().toString().trim();
  		    					t_date="01"+"/"+monthly_reciepts_payments.getToMonth().toString().trim()+"/"+monthly_reciepts_payments.getToYear().toString().trim();
  		    					l_date=Validations.lastDayOfMonth(t_date);
  		    					System.out.println("Before calling the method in action getCashGlTypeCode");
  		    				    detail=gldelegate.getCashGlTypeCode();
  		    				    System.out.println("After calling the method in action getCashGlTypeCode");
  		    		            gl_type=detail[0];
  		    		            gl_code=detail[1];
  		    		            System.out.println("beforeCalling the method opening balance in action");
  		    		            opening=gldelegate.openingBalance(gl_type,Integer.parseInt(gl_code),f_date);
  		    		            System.out.println("After comingbac of the method and befor calling the mehtod closingbalance");
  		    		            closing=gldelegate.closingBalance(gl_type,Integer.parseInt(gl_code),l_date);
  		    		            System.out.println("After successfully coming back of the method closing balance");
  		    		       
  		    				
  		    		            data1[0][0]="";
  		    		            data1[0][1]=gl_type;
  		    		            data1[0][2]=gl_code;
  		    		            data1[0][3]="Cash on hand";
  		    		            data1[0][4]=DoubleFormat.doubleToString(Math.abs(opening),2);
  		    		            data1[0][5]="";
  		    		            
  		    			        
  		    				for(int row=0;row<array_globject.length;row++)
  		    				{
  		    		   			data[row][0]=String.valueOf(row+1);
  		    		        	data[row][1]=array_globject[row].getAcType();
  		    		        	data[row][2]=String.valueOf(array_globject[row].getCode());
  		    		        	data[row][3]=array_globject[row].getGlName();
  		    		      		data[row][4]=DoubleFormat.doubleToString(array_globject[row].getCredit_sum(),2);
  		    		      		data[row][5]=DoubleFormat.doubleToString(array_globject[row].getDebit_sum(),2);
  		    		      		
  		    		      		credit_sum += array_globject[row].getCredit_sum();
  		    		      		debit_sum += array_globject[row].getDebit_sum();
  		    		    			
  		    			  		  			  		
  		    			  		
  		    			  		
  		    			  		if(row==array_globject.length-1)
  		    			  		{
  		    		     			break ;
  		    		     		}
  		    		        }
  		    				
  		    				data1[1][0]="";
  		    		    	data1[1][1]="";
  		    		    	data1[1][2]="";
  		    		    	data1[1][3]="";
  		    		    	data1[1][4]="";
  		    		  		data1[1][5]=DoubleFormat.doubleToString(Math.abs(closing),2);
  		    		  		
  		    		  		data1[2][0]="";
  		    		    	data1[2][1]="";
  		    		    	data1[2][2]="";
  		    		    	data1[2][3]="Total";
  		    		  		data1[2][4]=DoubleFormat.doubleToString(credit_sum,2);
  		    		  		data1[2][5]=DoubleFormat.doubleToString(debit_sum,2);
  		    		        
  		    		}
  		    		        		
  		    		catch (Exception e) {e.printStackTrace();}
  		    		for(int k=0;k<data.length;k++){
             			for(int l=0;l<6;l++){  
               			out.print(data[k][l]);
               			out.print(",");
             			}
             			out.println();
  		    		}
  		    		for(int k=0;k<data1.length;k++){
             			for(int l=0;l<6;l++){  
               			out.print(data1[k][l]);
               			out.print(",");
             			}
             			out.println();
  		    		}
  		    		req.setAttribute("msg","Saved to excel file in C:");
        		    return null;
                      }
		    		
		    	  else
		    	  {
		    		  path=MenuNameReader.getScreenProperties("0000");
		    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
		    		  return map.findForward(ResultHelp.getError());
		    	  }
		      }
			}
		      catch(Exception e)
		      {
		    	  path=MenuNameReader.getScreenProperties("0000");
		    	  setErrorPageElements(""+e, req, path);
		    	  return map.findForward(ResultHelp.getError());
		      }
		}
		
	
	
		
		
		
		
		/*  *************Gl Monthly Reciepts & Payments  end********************** */
		
		/*  *************Gl Monthly Profit & Loss Start********************** */
		if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyProfitLossMenu"))
		{
	      try{
	    	  MonthlyProfitLoss monthly_profit_loss=(MonthlyProfitLoss)form;
	    	  req.setAttribute("msg","");
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+monthly_profit_loss);
	    	  System.out.println("the page id is "+monthly_profit_loss.getPageId());
	    	  req.setAttribute("pagenum", monthly_profit_loss.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(monthly_profit_loss.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(monthly_profit_loss.getPageId());
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
		
		else if(map.getPath().trim().equalsIgnoreCase("/GL/GLMonthlyProfitLoss")){
			try{
				MonthlyProfitLoss monthly_profit_loss=(MonthlyProfitLoss)form;
				req.setAttribute("msg","");
		    	  System.out.println("******************************=="+form);
		    	  System.out.println("the page path is "+map.getPath().trim());
		    	  System.out.println("******************************=="+monthly_profit_loss);
		    	  System.out.println("the page id is "+monthly_profit_loss.getPageId());
		    	  req.setAttribute("pagenum", monthly_profit_loss.getPageId());
		    	  
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(monthly_profit_loss.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(monthly_profit_loss.getPageId());
		    		  gldelegate=new GLDelegate();
		    		  String report_data[][]=null;
		    		  setGLOpeningInitParams(req, path, gldelegate);
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  System.out.println("the vlue of flag in the action_______________"+monthly_profit_loss.getFlag());
		    		  System.out.println("The button valuee is"+monthly_profit_loss.getForward());
		    		  //code for view starts
		    		  if(monthly_profit_loss.getFlag().equalsIgnoreCase("view") || monthly_profit_loss.getFlag().equalsIgnoreCase("file")){
		    			  System.out.println("the vlue of flag in the action_______________"+monthly_profit_loss.getFlag());
		    			  String month=null;
		    			  String month1=null;
		    			  String date1="",date2="";
		    			  if(Integer.parseInt(monthly_profit_loss.getComparedMonth())<9){
		    				  month="0"+monthly_profit_loss.getComparedMonth();
		    			  }
		    			  else{
		    				  month=monthly_profit_loss.getComparedMonth();
		    			  }
		    			  if(Integer.parseInt(monthly_profit_loss.getReqMonth())<9){
		    				  month1="0"+monthly_profit_loss.getReqMonth();
		    			  }
		    			  else{
		    				  month1=monthly_profit_loss.getReqMonth();
		    			  }
		    			  GLReportObject[] expenses_data=null;
		    			  GLReportObject[] income_data=null;
		    			 // String report_data[][]=null;
		    			  System.out.println("Month in the Monthly PF is______"+month.toString());
		    			  String fromdate="01"+"/"+month.toString()+"/"+monthly_profit_loss.getComparedYear().toString();
			  				String todate="01"+"/"+month1.toString()+"/"+monthly_profit_loss.getReqYear().toString();
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
		  					req.setAttribute("PrevMthDate",date1);
		  					req.setAttribute("PresentMthDate",date2);
		  					System.out.println(" the date 1 is"+date1);
		  					System.out.println(" the date 2 is"+date2);
		  					
		  					expenses_data = gldelegate.getBalanceTwodates1(date1,date2,4);
		  					income_data = gldelegate.getBalanceTwodates2(date1,date2,3);

		  					
		  					
		  					int liability_data_length =0,assets_data_length=0;
		  					int sub_headings_liability=0,sub_headings_assets=0;;
		  					double sum1=0.00,sum2=0.00,sum3=0.00,sum4=0.00;
		  					
		  					
		  					if(expenses_data !=null){
		  						for(int i=0;i<expenses_data.length;i++){
		  							if(i!=0 && !(String.valueOf(expenses_data[i].getGLCode()).equals(" ")) && String.valueOf(expenses_data[i].getGLCode()).trim().substring(3,6).equalsIgnoreCase("000"))
		  								sub_headings_liability++;
		  						}
		  						liability_data_length = expenses_data.length;
		  					}
		  					
		  					if(income_data !=null){
		  						for(int i=0;i<income_data.length;i++){
		  							if(i!=0 && !(String.valueOf(income_data[i].getGLCode()).equals(" ")) && String.valueOf(income_data[i].getGLCode()).trim().substring(3,6).equalsIgnoreCase("000"))
		  								sub_headings_assets++;
		  						}
		  						assets_data_length = income_data.length;
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
		  							if(k!=0 && !(String.valueOf(expenses_data[k].getGLCode()).equals(" ")) && String.valueOf(expenses_data[k].getGLCode()).trim().substring(3,6).equalsIgnoreCase("000"))
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
		  							if(expenses_data[k].getNormalCD().equalsIgnoreCase("D")&& expenses_data[k].getFirstDateBalance()!=0.0)
		  								report_data[i][0]=DoubleFormat.doubleToString(Math.abs(expenses_data[k].getFirstDateBalance()),2);
		  							else
		  								report_data[i][0]=DoubleFormat.doubleToString(expenses_data[k].getFirstDateBalance(),2);
		  							
		  							report_data[i][1]=expenses_data[k].getGLAbbr();
		  							report_data[i][2]=String.valueOf(expenses_data[k].getGLCode());
		  							report_data[i][3]=expenses_data[k].getGLName();
		  							if(expenses_data[k].getNormalCD().equalsIgnoreCase("D")&& expenses_data[k].getFirstDateBalance()!=0.0)
		  								report_data[i][4]=DoubleFormat.doubleToString(Math.abs(expenses_data[k].getSecondDateBalance()),2);
		  							else
		  								report_data[i][4]=DoubleFormat.doubleToString(expenses_data[k].getSecondDateBalance(),2);
		  							
		  							if(expenses_data[k].getFirstDateBalance()!=0.0 && String.valueOf(expenses_data[k].getFirstDateBalance()).trim().length()>0 && expenses_data[k].getNormalCD().equalsIgnoreCase("D"))
		  							    sum_closing_first += Double.parseDouble(String.valueOf(Math.abs(expenses_data[k].getFirstDateBalance())));
		  							else
		  							    sum_closing_first += Double.parseDouble(String.valueOf(expenses_data[k].getFirstDateBalance()));
		  							
		  							if(expenses_data[k].getSecondDateBalance()!=0.0 && String.valueOf(expenses_data[k].getSecondDateBalance()).trim().length()>0 && expenses_data[k].getNormalCD().equalsIgnoreCase("D"))
		  								sum_closing_second += Double.parseDouble(String.valueOf(Math.abs(expenses_data[k].getSecondDateBalance())));
		  							else
		  							    sum_closing_second += Double.parseDouble(String.valueOf(expenses_data[k].getSecondDateBalance()));
		  						}
		  					}
		  					
		  					// to display the total for the last record
		  					
		  					report_data[i-7][0]=DoubleFormat.doubleToString(sum_closing_first,2);
		  					report_data[i-7][3]="Total";
		  					report_data[i-7][4]=DoubleFormat.doubleToString(sum_closing_second,2);
		  					sum1 +=sum_closing_first;
		  					sum2 +=sum_closing_second; 
		  					
		  					report_data[i-5][0]=DoubleFormat.doubleToString(sum1,2);
		  					report_data[i-5][3]="Total Expenses";
		  					report_data[i-5][4]=DoubleFormat.doubleToString(sum2,2);
		  					
		  					
		  					// Adding the assets data to the report data
		  					sum_closing_first=0;
		  					sum_closing_second=0;
		  					for(i=0,k=0;i<report_data.length;i++,k++){
		  						if(k<assets_data_length)
		  						{
		  							if(k!=0 && String.valueOf(income_data[k].getGLCode())!=null && String.valueOf(income_data[k].getGLCode()).trim().substring(3,6).equalsIgnoreCase("000")){
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
		  							if(income_data[k].getNormalCD().equalsIgnoreCase("D")&& income_data[k].getFirstDateBalance()!=0.0)
		  							 report_data[i][5]=DoubleFormat.doubleToString(income_data[k].getFirstDateBalance()*(-1),2);
		  							else
		  							report_data[i][5]=DoubleFormat.doubleToString(income_data[k].getFirstDateBalance(),2);
		  							
		  							report_data[i][6]=income_data[k].getGLAbbr();
		  							report_data[i][7]=String.valueOf(income_data[k].getGLCode());
		  							report_data[i][8]=income_data[k].getGLName();
		  							if(income_data[k].getNormalCD().equalsIgnoreCase("D")&& income_data[k].getSecondDateBalance()!=0.0)
		  							  report_data[i][9]=DoubleFormat.doubleToString(income_data[k].getSecondDateBalance()*(-1),2);
		  							else
		  							    report_data[i][9]=DoubleFormat.doubleToString(income_data[k].getSecondDateBalance(),2);
		  							
		  							if(income_data[k].getFirstDateBalance()!=0.0 && String.valueOf(income_data[k].getFirstDateBalance()).trim().length()>0 && income_data[k].getNormalCD().equalsIgnoreCase("D"))
		  							    sum_closing_first += Double.parseDouble(String.valueOf(income_data[k].getFirstDateBalance()*(-1)));
		  							else
		  							    sum_closing_first += Double.parseDouble(String.valueOf(income_data[k].getFirstDateBalance()));
		  							
		  							if(income_data[k].getSecondDateBalance()!=0.0 && String.valueOf(income_data[k].getSecondDateBalance()).trim().length()>0 && income_data[k].getNormalCD().equalsIgnoreCase("D"))
		  								sum_closing_second += Double.parseDouble(String.valueOf(income_data[k].getSecondDateBalance()*(-1)));
		  							else
		  							    sum_closing_second += Double.parseDouble(String.valueOf(income_data[k].getSecondDateBalance()));
		  						}
		  					}
		  					
		  					// to display the total for the last record
		  					report_data[i-7][5]=DoubleFormat.doubleToString(sum_closing_first,2);
		  					report_data[i-7][8]="Total";
		  					report_data[i-7][9]=DoubleFormat.doubleToString(sum_closing_second,2);
		  					sum3 +=sum_closing_first;
		  					sum4 +=sum_closing_second; 
		  					report_data[i-5][5]=DoubleFormat.doubleToString(sum3,2);
		  					report_data[i-5][8]="Total Income";
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
		  					sum3=0;
		  					sum4=0;
		  					}catch(Exception re){re.printStackTrace();}

		  					
		  					
		  					//show report ends
		  					
		  				}
		  				else{
		  					req.setAttribute("msg","Sorry Posting Not Done!..");
		  					
		  				}
		  			}catch(Exception ex){ex.printStackTrace();}
		  			req.setAttribute("MonthlyPLReportData", report_data);
		  			if(monthly_profit_loss.getFlag().equalsIgnoreCase("file")){
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
			            cell1.setCellValue("Monthly Profit And Loss For "+date2);
                        HSSFRow row2 = sheet.createRow((short)5);
		  			    HSSFCell cell2=row2.createCell((short)5);
		  			    cell2.setCellStyle(cellStyle);
		                cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
		                cell2.setCellValue("Expenses");
		                HSSFCell cell3=row2.createCell((short)10);
		  			    cell3.setCellStyle(cellStyle);
		                cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
		                cell3.setCellValue("Income");
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
			  			FileOutputStream fileOut = new FileOutputStream("c:\\Reports\\PL\\"+filename+".xls");
			  			wb.write(fileOut);
			  			fileOut.close();   
			  			req.setAttribute("msg","Successfully saved to file...! \n file path is c:\\Reports\\PL\\"+filename+".xls");
			  			     }catch ( Exception ex ){       
			  			     }     
			  			}

		  			return map.findForward(ResultHelp.getSuccess());
			    		  }
			    		
		    		  
		    		  	  //code for view ends
		    		  if(monthly_profit_loss.getFlag().equalsIgnoreCase("save"))
		    		  {
		    			  String month=null;
		    			  String month1=null;
		    			  String date1="",date2="";
		    			  if(Integer.parseInt(monthly_profit_loss.getComparedMonth())<9){
		    				  month="0"+monthly_profit_loss.getComparedMonth();
		    			  }
		    			  else{
		    				  month=monthly_profit_loss.getComparedMonth();
		    			  }
		    			  if(Integer.parseInt(monthly_profit_loss.getReqMonth())<9){
		    				  month1="0"+monthly_profit_loss.getReqMonth();
		    			  }
		    			  else{
		    				  month1=monthly_profit_loss.getReqMonth();
		    			  }
		    			  GLReportObject[] expenses_data=null;
		    			  GLReportObject[] income_data=null;
		    			 // String report_data[][]=null;
		    			  System.out.println("Month in the Monthly PF is______"+month.toString());
		    			  String fromdate="01"+"/"+month.toString()+"/"+monthly_profit_loss.getComparedYear().toString();
			  				String todate="01"+"/"+month1.toString()+"/"+monthly_profit_loss.getReqYear().toString();
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
		  					req.setAttribute("PrevMthDate",date1);
		  					req.setAttribute("PresentMthDate",date2);
		  					System.out.println(" the date 1 is"+date1);
		  					System.out.println(" the date 2 is"+date2);
		  					
		  					expenses_data = gldelegate.getBalanceTwodates1(date1,date2,4);
		  					income_data = gldelegate.getBalanceTwodates2(date1,date2,3);
		  					int liability_data_length =0,assets_data_length=0;
		  					int sub_headings_liability=0,sub_headings_assets=0;;
		  					double sum1=0.00,sum2=0.00,sum3=0.00,sum4=0.00;
		  					
		  					
		  					if(expenses_data !=null){
		  						for(int i=0;i<expenses_data.length;i++){
		  							if(i!=0 && !(String.valueOf(expenses_data[i].getGLCode()).equals(" ")) && String.valueOf(expenses_data[i].getGLCode()).trim().substring(3,6).equalsIgnoreCase("000"))
		  								sub_headings_liability++;
		  						}
		  						liability_data_length = expenses_data.length;
		  					}
		  					
		  					if(income_data !=null){
		  						for(int i=0;i<income_data.length;i++){
		  							if(i!=0 && !(String.valueOf(income_data[i].getGLCode()).equals(" ")) && String.valueOf(income_data[i].getGLCode()).trim().substring(3,6).equalsIgnoreCase("000"))
		  								sub_headings_assets++;
		  						}
		  						assets_data_length = income_data.length;
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
		  							if(k!=0 && !(String.valueOf(expenses_data[k].getGLCode()).equals(" ")) && String.valueOf(expenses_data[k].getGLCode()).trim().substring(3,6).equalsIgnoreCase("000"))
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
		  							if(expenses_data[k].getNormalCD().equalsIgnoreCase("D")&& expenses_data[k].getFirstDateBalance()!=0.0)
		  								report_data[i][0]=DoubleFormat.doubleToString(Math.abs(expenses_data[k].getFirstDateBalance()),2);
		  							else
		  								report_data[i][0]=DoubleFormat.doubleToString(expenses_data[k].getFirstDateBalance(),2);
		  							
		  							report_data[i][1]=expenses_data[k].getGLAbbr();
		  							report_data[i][2]=String.valueOf(expenses_data[k].getGLCode());
		  							report_data[i][3]=expenses_data[k].getGLName();
		  							if(expenses_data[k].getNormalCD().equalsIgnoreCase("D")&& expenses_data[k].getFirstDateBalance()!=0.0)
		  								report_data[i][4]=DoubleFormat.doubleToString(Math.abs(expenses_data[k].getSecondDateBalance()),2);
		  							else
		  								report_data[i][4]=DoubleFormat.doubleToString(expenses_data[k].getSecondDateBalance(),2);
		  							
		  							if(expenses_data[k].getFirstDateBalance()!=0.0 && String.valueOf(expenses_data[k].getFirstDateBalance()).trim().length()>0 && expenses_data[k].getNormalCD().equalsIgnoreCase("D"))
		  							    sum_closing_first += Double.parseDouble(String.valueOf(Math.abs(expenses_data[k].getFirstDateBalance())));
		  							else
		  							    sum_closing_first += Double.parseDouble(String.valueOf(expenses_data[k].getFirstDateBalance()));
		  							
		  							if(expenses_data[k].getSecondDateBalance()!=0.0 && String.valueOf(expenses_data[k].getSecondDateBalance()).trim().length()>0 && expenses_data[k].getNormalCD().equalsIgnoreCase("D"))
		  								sum_closing_second += Double.parseDouble(String.valueOf(Math.abs(expenses_data[k].getSecondDateBalance())));
		  							else
		  							    sum_closing_second += Double.parseDouble(String.valueOf(expenses_data[k].getSecondDateBalance()));
		  						}
		  					}
		  					
		  					// to display the total for the last record
		  					
		  					report_data[i-7][0]=DoubleFormat.doubleToString(sum_closing_first,2);
		  					report_data[i-7][3]="Total";
		  					report_data[i-7][4]=DoubleFormat.doubleToString(sum_closing_second,2);
		  					sum1 +=sum_closing_first;
		  					sum2 +=sum_closing_second; 
		  					
		  					report_data[i-5][0]=DoubleFormat.doubleToString(sum1,2);
		  					report_data[i-5][3]="Total Expenses";
		  					report_data[i-5][4]=DoubleFormat.doubleToString(sum2,2);
		  					
		  					
		  					// Adding the assets data to the report data
		  					sum_closing_first=0;
		  					sum_closing_second=0;
		  					for(i=0,k=0;i<report_data.length;i++,k++){
		  						if(k<assets_data_length)
		  						{
		  							if(k!=0 && String.valueOf(income_data[k].getGLCode())!=null && String.valueOf(income_data[k].getGLCode()).trim().substring(3,6).equalsIgnoreCase("000")){
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
		  							if(income_data[k].getNormalCD().equalsIgnoreCase("D")&& income_data[k].getFirstDateBalance()!=0.0)
		  							 report_data[i][5]=DoubleFormat.doubleToString(income_data[k].getFirstDateBalance()*(-1),2);
		  							else
		  							report_data[i][5]=DoubleFormat.doubleToString(income_data[k].getFirstDateBalance(),2);
		  							
		  							report_data[i][6]=income_data[k].getGLAbbr();
		  							report_data[i][7]=String.valueOf(income_data[k].getGLCode());
		  							report_data[i][8]=income_data[k].getGLName();
		  							if(income_data[k].getNormalCD().equalsIgnoreCase("D")&& income_data[k].getSecondDateBalance()!=0.0)
		  							  report_data[i][9]=DoubleFormat.doubleToString(income_data[k].getSecondDateBalance()*(-1),2);
		  							else
		  							    report_data[i][9]=DoubleFormat.doubleToString(income_data[k].getSecondDateBalance(),2);
		  							
		  							if(income_data[k].getFirstDateBalance()!=0.0 && String.valueOf(income_data[k].getFirstDateBalance()).trim().length()>0 && income_data[k].getNormalCD().equalsIgnoreCase("D"))
		  							    sum_closing_first += Double.parseDouble(String.valueOf(income_data[k].getFirstDateBalance()*(-1)));
		  							else
		  							    sum_closing_first += Double.parseDouble(String.valueOf(income_data[k].getFirstDateBalance()));
		  							
		  							if(income_data[k].getSecondDateBalance()!=0.0 && String.valueOf(income_data[k].getSecondDateBalance()).trim().length()>0 && income_data[k].getNormalCD().equalsIgnoreCase("D"))
		  								sum_closing_second += Double.parseDouble(String.valueOf(income_data[k].getSecondDateBalance()*(-1)));
		  							else
		  							    sum_closing_second += Double.parseDouble(String.valueOf(income_data[k].getSecondDateBalance()));
		  						}
		  					}
		  					
		  					// to display the total for the last record
		  					report_data[i-7][5]=DoubleFormat.doubleToString(sum_closing_first,2);
		  					report_data[i-7][8]="Total";
		  					report_data[i-7][9]=DoubleFormat.doubleToString(sum_closing_second,2);
		  					sum3 +=sum_closing_first;
		  					sum4 +=sum_closing_second; 
		  					report_data[i-5][5]=DoubleFormat.doubleToString(sum3,2);
		  					report_data[i-5][8]="Total Income";
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
		  					sum3=0;
		  					sum4=0;
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
                 	        out.print("Profit and Loss Details: ");
                 	        out.print("\n");
                 	        out.print("\n");
                    		   /*HSSFCell cell = row.createCell((short)0);
                    		   cell.setCellValue(1);*/
                 	       out.print(",");out.print(",");
                 	       out.print("Expenses"); out.print(",");
                 	       out.print(",");out.print(",");
                 	      out.print(",");
                 	       out.print(",");out.print("Income");
                 	       out.print(",");out.print(",");
                 	       out.println();
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
                    		 
   		  			
   		  		 
   			for(int k=0;k<report_data.length;k++){
  			  for(int j=0;j<10;j++){
    			out.print(report_data[k][j]);
    			out.print(",");
  			  }
  			  out.println();
   			}
   		  	req.setAttribute("msg","Saved to excel file in C:");
		    return null;	
		    		  }
		    		  
		    		  
		    		
		    		  
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
		
	
		
		
		
		
		
		
		/*  *************Gl Monthly Profit & Loss end********************** */
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
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

	
	

