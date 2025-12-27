package com.scb.cm.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import masterObject.customer.CustomerMasterObject;
import masterObject.general.AccSubCategoryObject;
import masterObject.general.Address;
import masterObject.general.AddressTypesObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import com.scb.cm.forms.AddressForm;
import com.scb.cm.forms.CustomerViewLogForm;
import com.scb.cm.forms.Customerform;
import com.scb.cm.forms.Customizationform;
import com.scb.cm.forms.MenuForm;
import com.scb.cm.forms.QueryOnCustomerForm;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.CustomerDelegate;
import com.scb.designPatterns.FrontCounterDelegate;
import com.scb.designPatterns.exceptions.ApplicationException;
import com.scb.designPatterns.exceptions.SystemException;
import com.scb.fc.actions.ResultHelp;
import com.scb.props.MenuNameReader;
   

/**
 * Created swetha 
 * User: user
 * Date: Nov 7, 2007
 * Time: 10:15:16 PM
 * To change this template use File | Settings | File Templates.
 */

public class CustomerAction extends Action{	
	AccSubCategoryObject accsubcategoryobject[]=null;
	String string_table_column[]=null,string_column_names[]=null;
	Map map=null;
	HttpSession session=null,httpsession=null;
	AdministratorDelegate admDelegate;
	String user,tml;
	Map user_role;

    public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)throws Exception
    {
    		
    		String value;
    		String path;
    		String done="Done";
    		String addr_type_obj[];
    		String j;
    		Address addr=null;
      
    		HashMap hash_addr=new HashMap();
    		AddressTypesObject addr_object;
    		Address address_object=new Address();
    		session=req.getSession();
    		CustomerMasterObject mastobj,customer_mast_obj;
    		CustomerMasterObject customermasterobject_current=new CustomerMasterObject();
    		CustomerMasterObject customermasterobject_old=new CustomerMasterObject();
     
    		System.out.println("<====*****SUMANTHHHHHHHHH KETHEEEEEE****PageName*********======>"+map.getPath());
     
     
    		httpsession=req.getSession();
    		/*user=(String)session.getServletContext().getAttribute("UserName");
    		tml=(String)session.getServletContext().getAttribute("UserTml");*/
    		    	/*user=(String)getServlet().getServletContext().getAttribute("UserName");
    		    	tml=(String)getServlet().getServletContext().getAttribute("UserTml");*/
    		    	user=(String)httpsession.getAttribute("UserName");
    		    	tml=(String)httpsession.getAttribute("UserTml") ;
    		    	   
    		    	try{
    		    		synchronized(req) {
    						
    					
    		    		if(user!=null){
    		    			
    		    			admDelegate=new AdministratorDelegate();
    		    			user_role=admDelegate.getUserLoginAccessRights(user,"CU");
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
    		    		        
    		    		           setErrorPageElements("Sorry, You do not have access to this page contact Administrator",req,path);
    		    		           return map.findForward(ResultHelp.getError());
    		    			}
    		    		}
    		    		}
    		    	}
    		    	catch(Exception ex){ex.printStackTrace();}

         
     
    		

    		if(map.getPath().equalsIgnoreCase("/QueryOnCustomerMenuLink"))
    		{ 
    			DynaActionForm query=(DynaActionForm)form;
    			
    			if(MenuNameReader.containsKeyScreen((String)query.get("pageId")))
    			{
    				CustomerDelegate delegate=new CustomerDelegate();
    				
    				path=MenuNameReader.getScreenProperties((String)query.get("pageId"));
    				req.setAttribute("pageId",path);
    				
    				req.setAttribute("flag",null);
    				return map.findForward(ResultHelp.getSuccess()); 
    			} 
    		}
    		
    		
    		else if(map.getPath().equalsIgnoreCase("/CustomerViewLogMenuLink"))
    		{
    			DynaActionForm meform=(DynaActionForm)form;
    			if(MenuNameReader.containsKeyScreen((String)meform.get("pageId")))
    			{
    				CustomerDelegate delegate=new CustomerDelegate();
    				path=MenuNameReader.getScreenProperties((String)meform.get("pageId"));
    				req.setAttribute("pageId",path);
    				req.setAttribute("cid",0);
    				return map.findForward(ResultHelp.getSuccess()); 
    			}
    	   	
    		}else if(map.getPath().equals("/MenuLink"))
    		{
    			CustomerInformationForm menuform=(CustomerInformationForm)form;
    			
    			if(MenuNameReader.containsKeyScreen(menuform.getPageId()))
    			{
    				path=MenuNameReader.getScreenProperties(menuform.getPageId());
    				value=menuform.getValue();
    				System.out.println("tyhe customer id is----->"+menuform.getCustid());
    				System.out.println("th evalue---->"+value);
    				System.out.println("cid_first in menulik---kkkkk-> "+menuform.getTotalclear());
    				System.out.println("Pararararraar> "+req.getParameter("totalclear"));
    				/*if(menuform.getCid_first()!=null || req.getParameter("cid_first")!=null)
    				{
    				System.out.println("cid_first in menulik----> "+menuform.getCid_first());
    				System.out.println("cid_first in menulik req.para----> "+req.getParameter("cid_first"));
    				 if(req.getParameter("cid_first").equalsIgnoreCase("firsttime"))
    				 {
    				   System.out.println("i am inside lol");
    				 }
    				}*/
    				System.out.println("the value of CID is------> "+menuform.getCid());
    				if(menuform.getCustid()==0 && Integer.parseInt(value)==1)
        			{
        				/*System.out.println("i am in menulik value iis one sumanth");
        				clear(req,form);
        				menuform.setCid_first("somevalue");
        				req.setAttribute("Detail",null);
        				System.out.println("the value is----> "+value);
        				req.setAttribute("submit",value);
        				//session.invalidate();
        				session.setAttribute("wanttocleart", "firsttime");
        				//req.setAttribute("", "");
        				 * 
        				 * 
                        */        				 
    					
    					System.out.println("first time dude means first time");
    					menuform.setName("");
    					menuform.setCid(0);
    					menuform.setCustid(0);
    					//menuform.setName(null);
    				  	menuform.setMidname("");
    				  	//menuform.setMidname(null);
    				  	menuform.setLastname("");
    				  	menuform.setLastname(null);
    				  	menuform.setFathername("");
    				  	menuform.setFathername(null);
    				  	menuform.setMothername("");
    				  	menuform.setMothername(null);
    				  	menuform.setSpousename("");
    				  	menuform.setSpousename(null);
    				  	menuform.setNation("");
    				  	menuform.setNation(null);
    				  	menuform.setReligion("");
    				  	menuform.setDob("");
    				  	menuform.setCaste("");
    				  	menuform.setIntro("");
    				  	menuform.setPan("");
    				  	menuform.setAddressarea("");
    				  	menuform.setAddressarea2("");
    				  	//menuform.setAddrestype("");
    				  	//menuform.setAddrproof("");
    				  	//menuform.setCountry("");
    				  	//menuform.setTxt_gurtype("");
    				  	menuform.setTxt_guardate("");
    				  	menuform.setTxt_guarcourtno("");
    				  	menuform.setTxt_guaraddress("");
    				    	menuform.setTxt_guarname("");
    				  	 menuform.setCity("");
    				   	 menuform.setPin("");
    				  	 menuform.setMobile("");
    				  	 menuform.setPhnum1("");
    				  	 menuform.setMailid("");
    				  	 menuform.setFaxno("");
    				  	req.setAttribute("Detail",null);
    				  	clear(req,form);
    					
        			}else if(menuform.getCustid()==0 && Integer.parseInt(value)==2)
        			{
        				/*req.setAttribute("Detail",null);
        				System.out.println("the value is----> "+value);
        				req.setAttribute("submit",value);
        				//session.invalidate();
        				session.setAttribute("wanttocleart", "firsttime");*/
        				//req.setAttribute("wanttocleart", "firsttime");
        				System.out.println("Second time dude means first time");
    					menuform.setName("");
    					//menuform.setName(null);
    				  	menuform.setMidname("");
    				  	//menuform.setMidname(null);
    				  	menuform.setLastname("");
    				  	menuform.setLastname(null);
    				  	menuform.setFathername("");
    				  	menuform.setFathername(null);
    				  	menuform.setMothername("");
    				  	menuform.setMothername(null);
    				  	menuform.setSpousename("");
    				  	menuform.setSpousename(null);
    				  	menuform.setNation("");
    				  	menuform.setNation(null);
    				  	menuform.setReligion("");
    				  	menuform.setDob("");
    				  	menuform.setCaste("");
    				  	menuform.setIntro("");
    				  	menuform.setPan("");
    				  	menuform.setAddressarea("");
    				  	menuform.setAddressarea2("");
    				  	//menuform.setAddrestype("");
    				  	//menuform.setAddrproof("");
    				  	//menuform.setCountry("");
    				  	//menuform.setTxt_gurtype("");
    				  	menuform.setTxt_guardate("");
    				  	menuform.setTxt_guarcourtno("");
    				  	menuform.setTxt_guaraddress("");
    				    	menuform.setTxt_guarname("");
    				  	 menuform.setCity("");
    				   	 menuform.setPin("");
    				  	 menuform.setMobile("");
    				  	 menuform.setPhnum1("");
    				  	 menuform.setMailid("");
    				  	 menuform.setFaxno("");
    				  	req.setAttribute("Detail",null);
    				  	clear(req,form);
        			}else if(menuform.getCid()==0){
        				System.out.println("I am in side CID 0======>");
        				menuform.setName("");
    					menuform.setCid(0);
    					menuform.setCustid(0);
    					//menuform.setName(null);
    				  	menuform.setMidname("");
    				  	//menuform.setMidname(null);
    				  	menuform.setLastname("");
    				  	menuform.setLastname(null);
    				  	menuform.setFathername("");
    				  	menuform.setFathername(null);
    				  	menuform.setMothername("");
    				  	menuform.setMothername(null);
    				  	menuform.setSpousename("");
    				  	menuform.setSpousename(null);
    				  	menuform.setNation("");
    				  	menuform.setNation(null);
    				  	menuform.setReligion("");
    				  	menuform.setDob("");
    				  	menuform.setCaste("");
    				  	menuform.setIntro("");
    				  	menuform.setPan("");
    				  	menuform.setAddressarea("");
    				  	menuform.setAddressarea2("");
    				  	//menuform.setAddrestype("");
    				  	//menuform.setAddrproof("");
    				  	//menuform.setCountry("");
    				  	//menuform.setTxt_gurtype("");
    				  	menuform.setTxt_guardate("");
    				  	menuform.setTxt_guarcourtno("");
    				  	menuform.setTxt_guaraddress("");
    				    	menuform.setTxt_guarname("");
    				  	 menuform.setCity("");
    				   	 menuform.setPin("");
    				  	 menuform.setMobile("");
    				  	 menuform.setPhnum1("");
    				  	 menuform.setMailid("");
    				  	 menuform.setFaxno("");
    				  	req.setAttribute("Detail",null);
    				  	clear(req,form);
        			}
    				CustomerDelegate deligate=new CustomerDelegate();
    				menuform.setCid(0);
    				menuform.setCustid(0);
    				Customertype(req,path,deligate);
    				req.setAttribute("Detail",null);
    				req.setAttribute("submit",value);
    				menuform.setCustid(0);
    				return map.findForward(ResultHelp.getSuccess());
          
    			}
    		}  
/********************** code used for writing the customization from menu.jsp**************************/    
    		if(map.getPath().equalsIgnoreCase("/CustomizationMenuLink"))
    		{
    			DynaActionForm dyna=(DynaActionForm)form;
    			CustomerDelegate  custdeligate=new CustomerDelegate();
    			if(MenuNameReader.containsKeyScreen((String)dyna.get("pageId")))
    			{
    				path=MenuNameReader.getScreenProperties((String)dyna.get("pageId"));
    				System.out.println("path in customer action form costomization========" +path);
    				Customization(req,path,custdeligate);
    				return map.findForward(ResultHelp.getSuccess());  
    			}
    		}  
    
  /*=====================================method used for customization form===============================*/
      
    		if(map.getPath().equalsIgnoreCase("/Customer/Customization"))
    		{
    			System.out.println("*******Inside /Customer/Customization****");
    			
    			Customizationform customizationform=(Customizationform)form;
    			System.out.println("forward==="+customizationform.getForward());
    			CustomerDelegate  custdeligate=new CustomerDelegate();
    			String button_values=customizationform.getForward();  
    			System.out.println("Button values in action class=="+button_values);
    			String str_custparam=null;
    			String custmerparam[]=custdeligate.getComboitems(12);
    			System.out.println("dhh"+custmerparam[0]);
    			string_table_column=custdeligate.Customizationparam("CustomerParameters");
    			System.out.println("dhhtrutru"+string_table_column);
    			string_column_names=new String[string_table_column.length];
    			System.out.println("customerAction"+string_column_names);
    			
    			for(int i=0;i<string_table_column.length;i++)
    		     {
    		         StringTokenizer tokenizer_table_column=new StringTokenizer(string_table_column[i],"$$$");
    		         if(tokenizer_table_column.hasMoreElements())
    		            tokenizer_table_column.nextToken();
    		         if(tokenizer_table_column.hasMoreElements())
    		            string_column_names[i]=tokenizer_table_column.nextToken();   
    		     }   
    			
    			String combo_value=customizationform.getParam();
    			String tab_name=combo_value.substring(1);
    			String tab_index=combo_value.substring(0,1); 
    			System.out.println("tab index==="+tab_index);
    			System.out.println("combo selected items=============>"+customizationform.getParam());
    			System.out.println("item selected value==="+customizationform.getCountry());
    			//combo_listoftables.getSelectedItem().toString(),string_column_names[combo_listoftables.getSelectedIndex()],list_data.getSelectedValue().toString()
    			if((button_values!=null)&&(button_values.equalsIgnoreCase("clear"))) 
    			{
    				if(button_values.equalsIgnoreCase("clear"))
    				{   System.out.println("inside clear"); 
    				    customizationform=null;
    				}
    			} else

    			if(MenuNameReader.containsKeyScreen(customizationform.getPageId()))
    			{
    				
    			
    				path=MenuNameReader.getScreenProperties(customizationform.getPageId());        
    				System.out.println("path in customer action form costomization========" +path);
    				Customization(req,path,custdeligate);
    				Customization(req,path,custdeligate);
    				String tab_value=customizationform.getParam();
    				
    			    String paramvalue=tab_value.substring(1); 
    				System.out.println("tab name=====>===>"+paramvalue);
    				
    				if(paramvalue.equalsIgnoreCase("Country"))
    				{
    					req.setAttribute("Country",custdeligate.Customizationparam("Country"));
    					
    					if(custdeligate.checkAvailability(tab_name,string_column_names[Integer.valueOf(tab_index)],customizationform.getCountry())==false)
    					{
    						req.setAttribute("check_ava", "val_false");
    					}
    					else
    					{
    						req.setAttribute("check_ava", "val_true");
    					} 
    	    			
    	    			
    					if(button_values!=null){ 
    						
    	    				if(customizationform.getForward().equalsIgnoreCase("submit"))
    	    				{
    	    				Insert_customization_values(customizationform,custdeligate);	
    	    				customizationform.setRefreshpage("refresh");
    	    				}
    	    				else if(customizationform.getForward().equalsIgnoreCase("Delete"))
							{
    	    					String param_value=customizationform.getCountry();
    	    					int country_status=deletefun("Country", customizationform,"country",param_value, custdeligate);
    	    					customizationform.setRefreshpage("refresh");
    	    					System.out.println("country_status====="+country_status);
							}
    	    				
    	    			}
    					customizationform.setAddp("");
    					return map.findForward(ResultHelp.getSuccess());
    				}  
    				else if(paramvalue.equalsIgnoreCase("Salutation"))
    				{
    					if(custdeligate.checkAvailability(tab_name,string_column_names[Integer.valueOf(tab_index)],customizationform.getSalutation())==false)
    					{
    						req.setAttribute("check_ava", "val_false");
    					}
    					else
    					{
    						req.setAttribute("check_ava", "val_true");
    					}
    					req.setAttribute("Salutation",custdeligate.Customizationparam("Salutation"));
    					
    					if(button_values!=null){ 
    	    				if(customizationform.getForward().equalsIgnoreCase("submit"))
    	    				{
    	    				Insert_customization_values(customizationform,custdeligate);	
    	    				customizationform.setRefreshpage("refresh");
    	    				}
    	    				else if(customizationform.getForward().equalsIgnoreCase("Delete"))
							{
    	    					String param_value=customizationform.getSalutation();
    	    					int salute_status=deletefun("Salutation", customizationform, "salute",param_value,custdeligate);
    	    					customizationform.setRefreshpage("refresh");
    	    					System.out.println("salaried_status====="+salute_status);
							}
    	    				
    	    			} 
    					customizationform.setAddp("");
    					return map.findForward(ResultHelp.getSuccess());
    				}
    				else if(paramvalue.equalsIgnoreCase("1AccountSubCategory"))
    				{
    					if(customizationform.getAccountsubcatdesc()!=null){
    					if(custdeligate.checkAvailability(tab_name,string_column_names[Integer.valueOf(tab_index)],customizationform.getAccountsubcatdesc())==false)
    					{
    						req.setAttribute("check_ava", "val_false");
    					}
    					else
    					{
    						req.setAttribute("check_ava", "val_true");
    					}
    					}
    					req.setAttribute("AccountSubCategory",custdeligate.Customizationparam("AccountSubCategory"));
    					
    					if(button_values!=null){ 
    	    				if(customizationform.getForward().equalsIgnoreCase("submit"))
    	    				{
    	    				Insert_customization_values(customizationform,custdeligate);	
    	    				customizationform.setRefreshpage("refresh");
    	    				}
    	    				else if(customizationform.getForward().equalsIgnoreCase("Delete"))
							{
    	    					String param_value=customizationform.getAccountsubcatdesc();
    	    					int cate_status=deletefun("AccountSubCategory", customizationform, "subcatdesc",param_value,custdeligate);
    	    					customizationform.setRefreshpage("refresh");
    	    					System.out.println("cate_status===="+cate_status);
							}
    	    				
    	    			} 
    					customizationform.setAddp("");
    					return map.findForward(ResultHelp.getSuccess());
    				}
                                        
    				else if(paramvalue.equalsIgnoreCase("State"))
    				{    
    					HashMap country_code=null;
    					country_code=new HashMap();
    					String country_value=customizationform.getCountryvalues();
    					System.out.println("country value in state==="+country_value);
    					req.setAttribute("StateValue","staenotnull");
    					req.setAttribute("CountryValue",custdeligate.Customizationparam("Country"));
    					
    					boolean check_ava=custdeligate.checkAvailability(tab_name,string_column_names[Integer.valueOf(tab_index)],customizationform.getStatecountry());
    					if(custdeligate.checkAvailability(tab_name,string_column_names[Integer.valueOf(tab_index)],customizationform.getCountry())==false)
    					{
    						req.setAttribute("check_ava", "val_false");
    					}
    					else
    					{
    						req.setAttribute("check_ava", "val_true");
    					}
    					
    					
    					
    					
    					if(country_value!=null)
    					{
    						req.setAttribute("StateCountry",custdeligate.Customizationparam("Country$$$"+country_value.toString()));
    				
    						if(button_values!=null)
    						{ 
    							if(customizationform.getForward().equalsIgnoreCase("submit"))
    							{
    								String country[]=custdeligate.Customizationparam("Country");
    								for(int i=0;i<country.length;i++)
    								{
    									
    	    						 	StringTokenizer sttoken=new StringTokenizer(country[i],"$$$");
    	    	                        String country_token=sttoken.nextToken();
    	    	                        String code=sttoken.nextToken();			
    	    	                        country_code.put(country_token,code);	  
    								}
    								String strr=country_code.get(customizationform.getCountryvalues())+",'"+customizationform.getAddp()+"'";
    								String salutation=customizationform.getParam(); 
    								String sal_val=salutation.substring(1);
    								int state=custdeligate.StoreCustomParams(sal_val,strr);
    								customizationform.setRefreshpage("refresh");
    								System.out.println("state value after inserting======"+state);
    	  							}
    								if(customizationform.getForward().equalsIgnoreCase("Delete"))
    								{ 
    									String param_value=customizationform.getStatecountry();
    									int state_status=deletefun("State",customizationform,"state",param_value, custdeligate);
    									customizationform.setRefreshpage("refresh");
    									System.out.println("state_status===="+state_status);
    								
    								}
    							 
    							 
    						}
    					return map.findForward(ResultHelp.getSuccess());
    					}
    					
    					customizationform.setAddp(""); 
    					return map.findForward(ResultHelp.getSuccess());	
    				}
    				else if(paramvalue.equalsIgnoreCase("NameProof"))
    				{
    					req.setAttribute("NameProof",custdeligate.Customizationparam("NameProof"));
    					
    					
    					if(custdeligate.checkAvailability(tab_name,string_column_names[Integer.valueOf(tab_index)],customizationform.getNameproof())==false)
    					{
    						req.setAttribute("check_ava", "val_false");
    					}
    					else
    					{
    						req.setAttribute("check_ava", "val_true");
    					}
    					
    					
    					
    					
    					if(button_values!=null)
    					{ 
    	    				if(customizationform.getForward().equalsIgnoreCase("submit"))
    	    				{
    	    					Insert_customization_values(customizationform,custdeligate);	
    	    					customizationform.setRefreshpage("refresh");
    	    				}
    	    				else if(customizationform.getForward().equalsIgnoreCase("Delete"))
							{  
    	    					String param_value=customizationform.getNameproof();	
    	    					int nameproof_status=deletefun("NameProof", customizationform,"nameproof", param_value, custdeligate);	
    	    					customizationform.setRefreshpage("refresh");
    	    					System.out.println("Nameproof_delete_status===="+nameproof_status);
							}   
    					}
    					customizationform.setAddp("");
    					return map.findForward(ResultHelp.getSuccess());
    				}  
    				else if(paramvalue.equalsIgnoreCase("AddrTypes"))
    				{
    					Address_type(req,custdeligate);
    					if(customizationform.getAddresstype()!=null){
    					if(custdeligate.checkAvailability(tab_name,string_column_names[Integer.valueOf(tab_index)],customizationform.getAddresstype())==false)
    					{
    						req.setAttribute("check_ava", "val_false");
    					}
    					else
    					{
    						req.setAttribute("check_ava", "val_true");
    					}
    					}
    					
    					
    					
    					if(button_values!=null)
    					{ 
    	    				if(customizationform.getForward().equalsIgnoreCase("submit"))
    	    				{
    	    				Insert_customization_values(customizationform,custdeligate);
    	    				customizationform.setRefreshpage("refresh");
    	    				} 
    	    				else if(customizationform.getForward().equalsIgnoreCase("Delete")) 
    	    				{   
    	    					String param_value=customizationform.getAddresstype();
    	    					int addrtype_status=deletefun("AddrTypes", customizationform,"addr_type", param_value, custdeligate);
    	    					customizationform.setRefreshpage("refresh");
    	    					System.out.println("after calling custdeligate for removing data======="+addrtype_status);
    	    				
    	    				}
    	    				
    	    			} 
    					customizationform.setAddp("");
    					return map.findForward(ResultHelp.getSuccess());
    				}
    				else if(paramvalue.equalsIgnoreCase("AddressProof"))
    				{
    					req.setAttribute("AddressProof",custdeligate.Customizationparam("AddressProof"));
    					if(custdeligate.checkAvailability(tab_name,string_column_names[Integer.valueOf(tab_index)],customizationform.getAddrproof())==false)
    					{
    						req.setAttribute("check_ava", "val_false");
    					}
    					else
    					{
    						req.setAttribute("check_ava", "val_true");
    					}
    					String param_value=customizationform.getAddrproof();
    					if(button_values!=null)
    					{ 
    	    				if(customizationform.getForward().equalsIgnoreCase("submit"))
    	    				{
    	    				Insert_customization_values(customizationform,custdeligate);	
    	    				customizationform.setRefreshpage("refresh");
    	    				}
    	    				else if(customizationform.getForward().equalsIgnoreCase("Delete"))
							{  
 							int addr_status=deletefun("AddressProof",customizationform,"addrproof",param_value,custdeligate);
 							customizationform.setRefreshpage("refresh");
 							System.out.println("addr_status======="+addr_status); 
							}  
    					} 
    					customizationform.setAddp("");
    					return map.findForward(ResultHelp.getSuccess());
    				}
    				else if(paramvalue.equalsIgnoreCase("MaritalStat"))
    				{
    					req.setAttribute("MaritalStat",custdeligate.Customizationparam("MaritalStat"));
    					
    					if(custdeligate.checkAvailability(tab_name,string_column_names[Integer.valueOf(tab_index)],customizationform.getMaritialstatus())==false)
    					{
    						req.setAttribute("check_ava", "val_false");
    					}
    					else
    					{
    						req.setAttribute("check_ava", "val_true");
    					}
    					
    					if(button_values!=null){ 
    	    				if(customizationform.getForward().equalsIgnoreCase("submit"))
    	    				{
    	    				Insert_customization_values(customizationform,custdeligate);	
    	    				customizationform.setRefreshpage("refresh");
    	    				}
    	    				else if(customizationform.getForward().equalsIgnoreCase("Delete"))
							{  
    	    					String param_value=customizationform.getMaritialstatus();
    	    					int MaritalStat_delete_status=deletefun("MaritalStat", customizationform,"marital", param_value, custdeligate);		
    	    					customizationform.setRefreshpage("refresh");
    	    					System.out.println("MaritalStat_delete_status===="+MaritalStat_delete_status);
							}   
    	    			}
    					customizationform.setAddp("");
    					return map.findForward(ResultHelp.getSuccess());
    				} 
    				else if(paramvalue.equalsIgnoreCase("Occupation"))
    				{
    					req.setAttribute("Occupation",custdeligate.Customizationparam("Occupation"));
    					
    					if(custdeligate.checkAvailability(tab_name,string_column_names[Integer.valueOf(tab_index)],customizationform.getOccupation())==false)
    					{
    						req.setAttribute("check_ava", "val_false");
    					}
    					else
    					{
    						req.setAttribute("check_ava", "val_true");
    					}
    					
    					
    					if(button_values!=null){ 
    	    				if(customizationform.getForward().equalsIgnoreCase("submit"))
    	    				{
    	    				Insert_customization_values(customizationform,custdeligate);	
    	    				customizationform.setRefreshpage("refresh");
    	    				} 
    	    				else if(customizationform.getForward().equalsIgnoreCase("Delete"))
							{  
    	    					String  param_value=customizationform.getOccupation();
    	    					int occ_status=deletefun("Occupation", customizationform,"occupation", param_value, custdeligate);
    	    					customizationform.setRefreshpage("refresh");
    	    					System.out.println("occ_status====="+occ_status); 
							}
    	    				
    	    			}
    					customizationform.setAddp("");
    					return map.findForward(ResultHelp.getSuccess());
    				}
    				else if(paramvalue.equalsIgnoreCase("CustomerType"))
    				{
    					req.setAttribute("CustType",custdeligate.Customizationparam("CustomerType"));
    					if(customizationform.getCustomerType()!=null){
    						if(custdeligate.checkAvailability(tab_name,string_column_names[Integer.valueOf(tab_index)],customizationform.getCustomerType())==false)
    					{
    						req.setAttribute("check_ava", "val_false");
    					}
    					else
    					{
    						req.setAttribute("check_ava", "val_true");
    					}
    					}
    					
    					if(button_values!=null){ 
    	    				if(customizationform.getForward().equalsIgnoreCase("submit"))
    	    				{
    	    				Insert_customization_values(customizationform,custdeligate);	
    	    				customizationform.setRefreshpage("refresh");
    	    				} 
    	    				else if(customizationform.getForward().equalsIgnoreCase("Delete"))
							{  
    	    					String  param_value=customizationform.getCustomerType();
    	    					int CustomerType_status=deletefun("CustomerType", customizationform,"custype", param_value, custdeligate);
    	    					customizationform.setRefreshpage("refresh");
    	    					System.out.println("CustomerType_status====="+CustomerType_status); 
							}
    	    				
    	    			}
    					customizationform.setAddp("");
    					return map.findForward(ResultHelp.getSuccess());
    				}
    				else if(paramvalue.equalsIgnoreCase("0Salaried"))
    				{
    					System.out.println("am in salaried");
    					req.setAttribute("Salaried",custdeligate.Customizationparam("Salaried"));
    					
    					if(custdeligate.checkAvailability(tab_name,string_column_names[Integer.valueOf(tab_index)],customizationform.getSalaried())==false)
    					{
    						System.out.println("am in salaried"+tab_name);
    						System.out.println("am in salaried"+string_column_names[Integer.valueOf(tab_index)]);
    						System.out.println("am in salaried"+customizationform.getSalaried());
    						
    						req.setAttribute("check_ava", "val_false");
    					}
    					else
    					{
    						req.setAttribute("check_ava", "val_true");
    					}
    					
    					
    					if(button_values!=null){ 
    	    				if(customizationform.getForward().equalsIgnoreCase("submit"))
    	    				{
    	    				Insert_customization_values(customizationform,custdeligate);	
    	    				customizationform.setRefreshpage("refresh");
    	    				} 
    	    				else if(customizationform.getForward().equalsIgnoreCase("Delete"))
							{  
    	    					String  param_value=customizationform.getSalaried();
    	    					int Salaried_status=deletefun("Salaried", customizationform, " work", param_value, custdeligate);
    	    					customizationform.setRefreshpage("refresh");
    	    					System.out.println("Salaried_status====="+Salaried_status); 
							}
    	    				
    	    			}
    					customizationform.setAddp("");
    					return map.findForward(ResultHelp.getSuccess());
    				}
    				
    				else if(paramvalue.equalsIgnoreCase("Castes"))
    				{
    					req.setAttribute("Castes",custdeligate.Customizationparam("Castes"));
    					
    					if(custdeligate.checkAvailability(tab_name,string_column_names[Integer.valueOf(tab_index)],customizationform.getCaste())==false)
    					{
    						req.setAttribute("check_ava", "val_false");
    					}
    					else
    					{
    						req.setAttribute("check_ava", "val_true");
    					}
    					
    					
    					if(button_values!=null){ 
    	    				if(customizationform.getForward().equalsIgnoreCase("submit"))
    	    				{
    	    				Insert_customization_values(customizationform,custdeligate);	
    	    				customizationform.setRefreshpage("refresh");
    	    				} 
    	    				else if(customizationform.getForward().equalsIgnoreCase("Delete"))
							{ 
    	    					String param_value=customizationform.getCaste();
    	    					int cate_status=deletefun("Castes", customizationform, "scst", param_value, custdeligate);
    	    					customizationform.setRefreshpage("refresh");
    	    					System.out.println("cate_status===="+cate_status); 
							}
    	    			}
    					customizationform.setAddp("");
    					return map.findForward(ResultHelp.getSuccess());
                    }  
    			/*	else if(paramvalue.equalsIgnoreCase("AccountSubCategory"))
    				{   
    					System.out.println("Hi am in AccountSubCategory");
    					req.setAttribute("AccountSubCategory",custdeligate.Customizationparam("AccountSubCategory"));
    					
    					if(custdeligate.checkAvailability(tab_name,string_column_names[Integer.valueOf(tab_index)],customizationform.getAccountcat())==false)
    					{
    						System.out.println("Hi am in AccountSubCategory"+customizationform.getAccountcat());
    						System.out.println("Hi am in AccountSubCategory"+tab_name);
    						System.out.println("Hi am in AccountSubCategory"+string_column_names[Integer.valueOf(tab_index)]);
    						req.setAttribute("check_ava", "val_false");
    					}
    					else
    					{
    						req.setAttribute("check_ava", "val_true");
    					}
    					if(button_values!=null){ 
    	    				if(customizationform.getForward().equalsIgnoreCase("submit"))
    	    				{
    	    				Insert_customization_values(customizationform,custdeligate);	
    	    				customizationform.setRefreshpage("refresh");
    	    				} 
    	    				else if(customizationform.getForward().equalsIgnoreCase("Delete"))
							{ 
    	    					String param_value=customizationform.getAccountcat();
    	    					int account_sub_cat=deletefun("AccountSubCategory", customizationform," subcatdesc", param_value, custdeligate);
    	    					customizationform.setRefreshpage("refresh");
    	    					String param_value=customizationform.getCaste();
    	    					int cate_status=deletefun("Castes", customizationform, "scst", param_value, custdeligate);
    	    					customizationform.setRefreshpage("refresh");
    	    					System.out.println("cate_status===="+cate_status); 
							}
    	    			}
    					customizationform.setAddp("");
    					return map.findForward(ResultHelp.getSuccess());
    				}*/
    					
    			/*		System.out.println("sub tabel values====>"+customizationform.getAccountsubcat());
                        req.setAttribute("AccountSubCategory",custdeligate.Customizationparam("CustomerType"));
                        String custtype[]=custdeligate.Customizationparam("CustomerType");
                        System.out.println("customer type===>"+custtype);
                        if(custtype!=null){
                        	String tab_acc2=combo_value.substring(2); 
                        	System.out.println("inside====>===>"+tab_acc2);
                        String listitem[]=custdeligate.Customizationparam(tab_acc2+"$$$"+customizationform.getAccountsubcat());
                        if(listitem!=null){
                        for(int k=0;k<listitem.length;k++)  
                        req.setAttribute("AccountSubCategory1",listitem);
                        } 
                        }
                        if(button_values!=null){ 
    	    				if(customizationform.getForward().equalsIgnoreCase("submit"))
    	    				{
    	    					System.out.println("customizationform.getAccountsubcat()===="+customizationform.getAccountsubcat());
    	    					String subcat=customizationform.getAccountsubcat()+","+customizationform.getDesc()+","+customizationform.getCode();
    	    					int code=custdeligate.StoreCustomParams(tab_name,subcat);
    	    					customizationform.setRefreshpage("refresh");
    	    					System.out.println("code====="+code);  
    	    				} 
    	    				else if(customizationform.getForward().equalsIgnoreCase("Delete"))
							{
    	    					String param_value=customizationform.getAccountcat();
    	    					int account_sub_cat=deletefun("AccountSubCategory", customizationform," subcatdesc", param_value, custdeligate);
    	    					customizationform.setRefreshpage("refresh");
							}
    	    				
    	    			}    
                        customizationform.setAddp("");
                        return map.findForward(ResultHelp.getSuccess());
    				}*/ 
    		/*		else if(paramvalue.equalsIgnoreCase("Salaried"))
    				{
                        req.setAttribute("Salaried",custdeligate.Customizationparam("Salaried"));
                        
                        if(custdeligate.checkAvailability(tab_name,string_column_names[Integer.valueOf(tab_index)],customizationform.getSalaried())==false)
    					{
    						req.setAttribute("check_ava", "val_false");
    					}
    					else
    					{
    						req.setAttribute("check_ava", "val_true");
    					}
                        
                        
                        if(button_values!=null){ 
    	    				if(customizationform.getForward().equalsIgnoreCase("submit"))
    	    				{
    	    				Insert_customization_values(customizationform,custdeligate);	
    	    				customizationform.setRefreshpage("refresh");
    	    				} 
    	    				else if(customizationform.getForward().equalsIgnoreCase("Delete"))
							{
    	    					String param_value=customizationform.getSalaried();
    	    					int salaried_status=deletefun("Salaried", customizationform, "suboccupation", param_value, custdeligate);
    	    					customizationform.setRefreshpage("refresh");
    	    					System.out.println("salaried_status====="+salaried_status); 
							}
    	    				
    	    			}  
                        customizationform.setAddp("");
                        return map.findForward(ResultHelp.getSuccess());
                    }*/
    				
    					return map.findForward(ResultHelp.getSuccess());  
    			}
    			
    		}
    		
    		 
 
    		if(map.getPath().equalsIgnoreCase("/custlink"))
    		{  
    			Customerform custform=(Customerform)form;
    			//CustomerInformationForm custinfo=custform.getCustinfo();
    			AddressTypesObject obj[];
    			java.util.HashMap address; 
    			CustomerDelegate deligate=new CustomerDelegate();
    			
    			req.setAttribute("submit",custform.getValue()); 
    			custform.setCid_verified("null");
    			custform.setCid_notver("null");
    			req.setAttribute("clear","1");
    			custform.setClear_value("1");
    			if(custform.getCustid()!=0)
    			{   
    				req.setAttribute("clear","0");
    				custform.setClear_value("0");
    				System.out.println("inside Not Found Customer !=0"); 
    				int customerid=custform.getCustid();
    				try{
    				customermasterobject_current=deligate.getCustomerdetails(customerid);
    				
    				}catch (Exception e) { 
						e.printStackTrace();
						path=MenuNameReader.getScreenProperties("0000");
						setErrorPageElements(""+e, req, path);
						return map.findForward(ResultHelp.getError());
					}
			    	 
				 
    			}
    			
    			
    			if(Integer.parseInt(custform.getValue())==3){
    				req.setAttribute("button_values","update");
    			}else if(Integer.parseInt(custform.getValue())==2)
    				{
    				req.setAttribute("button_values","verify");
    				} 
    			 else if(Integer.parseInt(custform.getValue())==1)
    	 			{
        			req.setAttribute("button_values","submit");
        			} 
    			int cid=custform.getCustid();
    			System.out.println("customer type in action class"+custform.getCusttype());
    			String custtype=custform.getCusttype();
    			req.setAttribute("CustomerPageID",custtype); 
    			System.out.println("hi my cid=="+cid); 
    			
    			//if(custform.getCid_first()!=null && custform.getCid_first().equals("first")&& cid==0)
    			
    			if(req.getParameter("cid_first").equalsIgnoreCase("firsttime") && cid==0)
    			{
    				System.out.println("I am indise set fiest sumanth");
    				//custform.setCustid(0);
    				//custform.setClear_value("1");
    				req.setAttribute("clear","1");
        			custform.setClear_value("1");
        			custform.setCid_first("");
    			   
    			}
    			customermasterobject_current=deligate.UnverifiedCustomer(cid);
    			/*System.out.println("customermasterobject_current===" +customermasterobject_current.uv.getVerTml());*/ 
    			String diff_value=custform.getValue();
    			System.out.println("page diff values======" +diff_value); 
    			req.setAttribute("clear","1");
    			custform.setClear_value("1");
    			if(cid!=0)
    			{   
    				req.setAttribute("clear","0");
    				custform.setClear_value("0");
    			//	System.out.println(" ver user====> "+customermasterobject_current.getVe_user());
    				
    				if(customermasterobject_current.getVe_user()==null){
    					System.out.println("inside veuser is null");
    				req.setAttribute("Detail",deligate.getCustomerdetails(cid));
    				}else{
    					System.out.println("inside veuser is NOT null");
    					custform.setTesting("CID Already verified");
    				}
    				customer_mast_obj=deligate.getCustomerdetails(cid);
    				if(customer_mast_obj.getBinaryImage()!=null)
    				{
    					System.out.println("***********Testing 123******************");
    					
    					/*byte imagedata[]=customer_mast_obj.getBinaryImage();
    					res.setContentType("image/jpeg");
    					OutputStream o=res.getOutputStream();
    					o.write(imagedata);
    					o.flush();
    					o.close();*/
    					byte imagedata[]=customer_mast_obj.getBinaryImage();
    					String newFloderpath="C://Princess";
    					File f=new File(newFloderpath); 
    					f.mkdir(); 
    					System.out.println("path u ve specified------>"+f.getAbsolutePath());
    					if(f.exists())
    					{
    						File file1=new File(f.getAbsolutePath()+"\\Image");
    						file1.createNewFile();
    						FileOutputStream outfile=new FileOutputStream(file1+".jpg");
    						outfile.write(imagedata);
    						outfile.close();
    					}
    					
    				}
   				if(customer_mast_obj!=null)
    				{	
    					String Introducer_Name=deligate.CustomerName(customer_mast_obj.getIntroducerId());
    					System.out.println("introducresname is------>"+Introducer_Name);
    					//req.setAttribute("Introducer_Name",Introducer_Name);
    					//changed by sumanth on 08/11/2009 sunday
    					req.setAttribute("Cust_Name",Introducer_Name);
    					if(customer_mast_obj.getDOB()!=null && !customer_mast_obj.getDOB().equals("00/00/0000") && customer_mast_obj.getDOB().length()>0)
    					{
    						System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    						StringTokenizer d=new StringTokenizer(customer_mast_obj.getDOB(),"/");
            				d.nextToken(); d.nextToken();
            				int yy=Integer.parseInt(d.nextToken());
                        	
            				Calendar cal=Calendar.getInstance(); 
            				req.setAttribute("AGE_ATT",String.valueOf(cal.get(Calendar.YEAR)-yy));
    					}
    					accsubcategoryobject=deligate.getAccSubCategories(Integer.valueOf(custform.getCusttype()));
    					for(int i=0; i< accsubcategoryobject.length ;i++)
    					{	
    						if(customer_mast_obj.getSubCategory()== accsubcategoryobject[i].getSubCategoryCode())
    						{
    							req.setAttribute("SubCatCode",String.valueOf(accsubcategoryobject[i].getSubCategoryCode()));
    							req.setAttribute("SubCatDesc",accsubcategoryobject[i].getSubCategoryDesc());
    						}
    					}	
    				} 
    				System.out.println("<====address in customer deligate link=====>"+deligate.getAddress(cid));
    				if(customermasterobject_current.uv.getVerTml()==null){
    				req.setAttribute("Address",deligate.getAddress(cid)); 
    				}else{
    					System.out.println("inside veuser is NOT null");
    					custform.setTesting("CID Already verified");
    				}
    				req.setAttribute("AddressFlag",1);
    				req.setAttribute("AddressType",deligate.CustomerAddress());
    				System.out.println("after checking cid!=0");
    				 
    				System.out.println("diff_value------------->"+diff_value);
    				System.out.println("================>====>===>"+customermasterobject_current.uv.getVerTml()); 
    				
    				if((customermasterobject_current.uv.getVerTml()!=null) && (diff_value.equalsIgnoreCase("2") || diff_value.equalsIgnoreCase("1") ))
    				{
    					System.out.println("***********^^^^^^Testing 123^^^^^^^^^**************");
    					//custform.setCid_verified("CustIdVerified");
    					req.setAttribute("clear","1");
    					custform.setClear_value("1");
    					//custform.setCustinfo(null);
    					/*System.out.println("inside is in custlink");
    					req.setAttribute("notverifycust",user); 
    					int num=0;
    					req.setAttribute("Verify_customer",0);
    					req.setAttribute("pageId","/CustomerWebPages/Sucess.jsp");
    					req.setAttribute("custid_from_bean",cid);
    					req.setAttribute("Update_sucessfully",num);
    					req.setAttribute("notverifycust","10");
    					return map.findForward(ResultHelp.getSuccess());*/
        	        	
    				}
    				//for checking whether the customer id is verified or not before doing validation//
    				
    				if((customermasterobject_current.uv.getVerTml()==null)&& (diff_value.equalsIgnoreCase("3")||diff_value.equalsIgnoreCase("1")  ))
    				{
    					custform.setCid_notver("CIDNOTVER");
    					req.setAttribute("clear","1"); 
    					custform.setClear_value("1");
    					/*System.out.println("inside is in custlink");
    					req.setAttribute("notverifycust","update");
    					int num=0;
    					req.setAttribute("Verify_customer",0);
    					req.setAttribute("pageId","/CustomerWebPages/Sucess.jsp");
    					req.setAttribute("custid_from_bean",cid);
    					req.setAttribute("Update_sucessfully",num);
    					return map.findForward(ResultHelp.getSuccess());
        	        	*/
    				}
 
    			}   
    			 
    			
    			if(MenuNameReader.containsKeyScreen(custform.getPageId()))
    			{
    				
    				AddressTypesObject[] address2=deligate.CustomerAddress();
    				Map addr_list=new HashMap();
    				if(address2!=null)
    				{
    					for(int i=0;i<address2.length;i++)
    					{
    						Map map_addr=new LinkedHashMap();
    	     				map_addr.put(""+address2[i].getAddressCode(),"");
    	     				addr_list.put(""+address2[i].getAddressCode(),map_addr);
    					} 
    				}
    				req.setAttribute("AddrType",address2);
     				session=req.getSession(true);
     				req.setAttribute("addr2", "");
     				session.setAttribute("addressList", addr_list);
    				path=MenuNameReader.getScreenProperties(custform.getPageId());
    				Customertype(req,path,deligate);
    				ComboValues(req,path,deligate);
    				Initial_Accsubcat(req, deligate,Integer.valueOf(custform.getCusttype()));
    				req.setAttribute("AddressType",deligate.CustomerAddress());
    				//swethaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    				///req.setAttribute("pageName",MenuNameReader.getScreenProperties(custform.getCusttype().trim()));
    				req.setAttribute("pageName",MenuNameReader.getScreenProperties(custform.getCusttype().trim()));
    				req.setAttribute("customerform",custform);
    				return map.findForward(ResultHelp.getSuccess());
    			}
     
    			/*if((cid!=0)||(custform.getCusttype().equalsIgnoreCase("Individual")))
        		{
        			System.out.println("inside if condition in customer action");
        	     
        	 		mastobj=deligate.getCustomerdetails(custform.getCustid());
        			System.out.println("in customer action========master obj========" +mastobj);
        			req.setAttribute("CustomerDetail",mastobj);
            		return map.findForward(ResultHelp.getSuccess());
        		}*/
    			custform.setCustid(0);
    			custform.setCusttype("Select");
     		}  

/*==========================================code for customer detail form=============================*/
    		else 
    			if(map.getPath().equalsIgnoreCase("/custdetails"))
    			{ 
    				int Customer_id=0;	   
		            
    				CustomerInformationForm custdetform=(CustomerInformationForm)form;
    				CustomerDelegate custdeligate=new CustomerDelegate();
    				Customerform customerform=custdetform.getCustform(); 
    				
    				custdetform.setVer_cid(null);
                    System.out.println("custdetform.getPassportdetail()=====" +custdetform.getPassportdetail());
    				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+custdetform.getPassportdetail());
    				String address=custdetform.getAddrestype();
    				String button_values=custdetform.getButton();    
    				System.out.println("button_values========"+button_values);
    				String checkbox=custdetform.getPassportdetail(); 
    				System.out.println("check box===========>"+checkbox);
    				
    				/*if((customerform.getCustid()==0)&&((checkbox=="on")))
    				{
    				System.out.println("inside if condtition button values"); 	
    				req.setAttribute("AddressType",custdeligate.CustomerAddress());
    				req.setAttribute("submit",customerform.getValue());		
  					req.setAttribute("checkbox",checkbox);
  					if(MenuNameReader.containsKeyScreen(customerform.getPageId()))
					{
						path=MenuNameReader.getScreenProperties(customerform.getPageId());
						Customertype(req,path,custdeligate);
						ComboValues(req,path,custdeligate);
						req.setAttribute("pageName",MenuNameReader.getScreenProperties(customerform.getCusttype().trim()));
					}
					
   					}
    				*/
    				AddressTypesObject[] address2=custdeligate.CustomerAddress();
    				req.setAttribute("AddrType",address2);
    				Map cat_list=(HashMap)session.getAttribute("addressList");
    				String method=req.getParameter("method");
    				System.out.println("method=="+method);
    				if(method!=null){
    					if(method.equalsIgnoreCase("Save")){
    						System.out.println("addr code=="+custdetform.getAddrestype());
    						Map m=(HashMap)cat_list.get(custdetform.getAddrestype());
    						System.out.println(m);
    						if(m!=null){
    							m.remove(custdetform.getAddrestype());
    							m.put(custdetform.getAddrestype(), custdetform.getAddressarea());
    							System.out.println(m);
    							cat_list.remove(custdetform.getAddrestype());
    							cat_list.put(custdetform.getAddrestype(),m);
    							req.setAttribute("addr2", custdetform.getAddressarea());
    							
    						}else{
    							System.out.println("m in else =="+m);
    						}
    						
    					}else{
    						System.out.println("hi i am submit");
    						setAddress((HashMap)session.getAttribute("addressList"), custdetform);
    					}
    					
    						
    				}else{
    					String addres="";
    					if(custdetform.getAddrestype()!=null){
    						System.out.println("Addr type>> "+custdetform.getAddrestype());
    						Map m=(HashMap)cat_list.get(custdetform.getAddrestype());
							System.out.println(m);
							if(!m.get(custdetform.getAddrestype().toString()).equals("")){
								addres=""+m.get(custdetform.getAddrestype());
								System.out.println("addres<>>+"+addres);
								System.out.println(m);
							}
    					}
    					req.setAttribute("addr2", addres);
    				}
    				session.setAttribute("addressList",cat_list); 
/******************************code for writing fetching cid from the new customer form**********************/
	   
    				if(customerform.getCustid()!=0) 
    				{
    					int cid=0;	   
    					req.setAttribute("customerform",customerform); 
    					req.setAttribute("submit",customerform.getValue());
	   					cid=customerform.getCustid();
    					System.out.println("cid=="+cid);
    					req.setAttribute("Detail",custdeligate.getCustomerdetails(cid));
    					req.setAttribute("Address",custdeligate.getAddress(cid));
    					req.setAttribute("AddressFlag",1);
    					
    				   
    					if(button_values!=null)
    					{ 
    						
    						if(button_values.equalsIgnoreCase("verify"))
    						{   
    							int num=0;
    							System.out.println("custdetform.getVerify()======"+custdetform.getButtonvalues());
    							System.out.println("customer form cid====="+customerform.getCustid()); 
    							int verify_cst=custdeligate.VerifyCustomer(customerform.getCustid(),user,tml);
    							int cid1=customerform.getCustid();
    							req.setAttribute("custid_from_bean",cid1);
    							req.setAttribute("Verify_customer",verify_cst);
    							req.setAttribute("Update_sucessfully",num);
    							
    							System.out.println("verify customer====== " +verify_cst);
    							req.setAttribute("pageId","/Sucess.jsp");
    							return map.findForward(ResultHelp.getSuccess());
    						}
    					}
        	     
    					if(button_values.equalsIgnoreCase("update"))
    						{
    							System.out.println("**********inside update*********"); 
    							String false_value_inupdate="1";
    							req.setAttribute("update_flag_value",false_value_inupdate);
    							req.setAttribute("button_values","update_submit");
    							cid=customerform.getCustid();
    							System.out.println("cid=="+cid);
    							req.setAttribute("Detail",custdeligate.getCustomerdetails(cid));
    							req.setAttribute("Address",custdeligate.getAddress(cid));
    				 			req.setAttribute("AddressType",custdeligate.CustomerAddress());
    							if(MenuNameReader.containsKeyScreen(customerform.getPageId()))
    							{
    								path=MenuNameReader.getScreenProperties(customerform.getPageId());
    								Customertype(req,path,custdeligate);
    								ComboValues(req,path,custdeligate);
    								Initial_Accsubcat(req, custdeligate,Integer.valueOf(custdetform.getCustform().getCusttype()));
    								//swethaeeeeeeeeeeeeeeeeee
    								//req.setAttribute("pageName",MenuNameReader.getScreenProperties(customerform.getCusttype().trim()));
    								req.setAttribute("pageName",MenuNameReader.getScreenProperties(customerform.getCusttype().trim()));
    							}
    							return map.findForward(ResultHelp.getSuccess());
    						}
    					

   //code for update submit
    					/****************** code for updating the data into database*****************/
     		           
    					else  if(button_values!=null)
    					{
    						if(button_values.equalsIgnoreCase("update_submit"))
    						{   
    							System.out.println("******Inside UpdateSubmit******");
    							customermasterobject_current=custdeligate.getCustomerdetails(customerform.getCustid());
    							customermasterobject_old.copy(customermasterobject_old,customermasterobject_current);
    	    	   				customermasterobject_current.setOperation(0);
    	           				boolean flag=customermasterobject_current.equals(customermasterobject_old);
    							
    	           				System.out.println("customermasterobject_current.getName()=========="+customermasterobject_current.getName());
    							System.out.println("flag value inside update=======" +flag);
    							
    							if(flag==true)
    							{
    								customermasterobject_current.setOperation(2);		
    							}
    							
    							customermasterobject_current.addressIsEqual(customermasterobject_old.getAddress());
    							System.out.println("adddddresss is equallllllll=="+customermasterobject_current.addressIsEqual(customermasterobject_old.getAddress()));
    							int bit=0;
    							Set new_address=customermasterobject_current.getAddress().keySet();
    							Iterator itter_new_address=new_address.iterator();
    							aa:while(itter_new_address.hasNext()){
    								Integer i=(Integer)itter_new_address.next();
    								Address master_new=(Address)customermasterobject_current.getAddress().get(i);
    								if(master_new.isChanged()==true)
    								{
    									System.out.println("address changed==============="+master_new.isChanged());
                                        bit=3;
                                        break aa;
    								}
    							}
    							if(customermasterobject_current.getOperation()==2 && bit==3)
                                    customermasterobject_current.setOperation(4);
                                if(customermasterobject_current.getOperation()==0 && bit==3)
                                    customermasterobject_current.setOperation(3);
                                System.out.println("Get operation"+customermasterobject_current.getOperation());
    						
                               if(customermasterobject_current.getOperation()==0)
                               {
                            	   System.out.println("sucessfully updated");    
                               }else
                               {
                            	  
                                   
                            	   System.out.println("get operation before calling store==="+customermasterobject_current.getOperation());
                            	   int i= Inserting_values(customermasterobject_current,custdetform,customerform,customerform.getCustid(),custdeligate,(HashMap)session.getAttribute("addressList"));
                            	   System.out.println("after sucessfully updated customer id====" +i);
                            	  
                            	   req.setAttribute("Update_sucessfully",i); 
                            	   req.setAttribute("custid_from_bean",0);
              	     			   req.setAttribute("Verify_customer",0);
              	     			   req.setAttribute("Customer_not_found",null);
              	     			   req.setAttribute("pageId","/Sucess.jsp");
              	     			   return map.findForward(ResultHelp.getSuccess());
                            	   
                            	   
                               } 
                                
    						}
    					}
    					if(MenuNameReader.containsKeyScreen(customerform.getPageId()))
    					{
    						path=MenuNameReader.getScreenProperties(customerform.getPageId());
    						Customertype(req,path,custdeligate);
    						ComboValues(req,path,custdeligate);
    						Initial_Accsubcat(req, custdeligate,Integer.valueOf(custdetform.getCustform().getCusttype()));
    						req.setAttribute("pageName",MenuNameReader.getScreenProperties(customerform.getCusttype().trim()));
    					}
    					
    					
    					if(address!=null)
    					{   
    						mastobj=custdeligate.getCustomerdetails(custdetform.getCustform().getCustid());
    						hash_addr=mastobj.getAddress();
    						addr=(Address)hash_addr.get(new Integer(Integer.parseInt(address.trim())));
    						req.setAttribute("AddressFlag",Integer.parseInt(custdetform.getAddrestype().trim()));
    						int cid1=0;	   
        					req.setAttribute("customerform",customerform); 
        					req.setAttribute("submit",customerform.getValue());
    	   					cid1=customerform.getCustid();
        					System.out.println("cid=="+cid1);
        					req.setAttribute("Detail",custdeligate.getCustomerdetails(cid1));
        					req.setAttribute("Address",custdeligate.getAddress(cid1));
        					req.setAttribute("AddressType",custdeligate.CustomerAddress());
        					
    						if(MenuNameReader.containsKeyScreen(customerform.getPageId()))
        					{
        						path=MenuNameReader.getScreenProperties(customerform.getPageId());
        						Customertype(req,path,custdeligate);
        						ComboValues(req,path,custdeligate);
        						Initial_Accsubcat(req, custdeligate,Integer.valueOf(custdetform.getCustform().getCusttype()));
        						req.setAttribute("pageName",MenuNameReader.getScreenProperties(customerform.getCusttype().trim()));
        					}
    						
    						return map.findForward(ResultHelp.getSuccess());
    						
    					} 
    					
    					
    					
	   
    					
    				} 
	    
/*************************code for inserting values into customer table in the database*********************/
    				if(custdetform.getCustform().getCustid()==0)
    				{
    					if(custdetform.getIntro()!=null)
    					{
    						req.setAttribute("Introducer","introid2");
    						String customer_name=custdeligate.CustomerName(custdetform.getIntro());
    						System.out.println("introducer name in customer action=====" +customer_name);
    						req.setAttribute("Cust_Name",customer_name);
    						System.out.println("Before the method--------------------1-");
    						String age_val=Calcul_Age(req, custdetform,custdeligate);
    						System.out.println("After the method------------------1--");
    						req.setAttribute("AgeAttribute",age_val);
    						
    						req.setAttribute("customerform",customerform); 
    						req.setAttribute("submit",customerform.getValue());
    						req.setAttribute("AddressFlag",1);
    						req.setAttribute("AddressType",custdeligate.CustomerAddress());
    						if(MenuNameReader.containsKeyScreen(customerform.getPageId()))
    						{
    							path=MenuNameReader.getScreenProperties(customerform.getPageId());
    							Customertype(req,path,custdeligate);
    							ComboValues(req,path,custdeligate);
    							Initial_Accsubcat(req, custdeligate,Integer.valueOf(customerform.getCusttype()));
    							req.setAttribute("pageName",MenuNameReader.getScreenProperties(customerform.getCusttype().trim()));
							}

    				if(button_values!=null)
    				{
    					
    					if(button_values.equalsIgnoreCase("submit"))
    					{ 
    						
    						customermasterobject_current.setOperation(1); 
    						int i=Inserting_values(customermasterobject_current, custdetform, customerform, Customer_id, custdeligate,(HashMap)session.getAttribute("addressList"));
    						System.out.println("Customer-ID==========="+i);
            				customermasterobject_old.copy(customermasterobject_old,customermasterobject_current);
            				customermasterobject_current=custdeligate.getCustomerdetails(i);
            				req.setAttribute("custName",customermasterobject_current.getName());
            				req.setAttribute("custDOB",customermasterobject_current.getDOB());
            				//req.setAttribute("custAddress",customermasterobject_current.getAddress());
            				
       	     				req.setAttribute("custid_from_bean",i);
       	     				req.setAttribute("Verify_customer",4); 
       	     				req.setAttribute("Update_sucessfully",0);
       	     				req.setAttribute("pageId","/Sucess.jsp");
       	     				return map.findForward(ResultHelp.getSuccess());
            				
    					}
    				}
    				return map.findForward(ResultHelp.getSuccess());
    					}	
    				}
    	
    				if(MenuNameReader.containsKeyScreen(custdetform.getPageId()))
    				{
    					int num=0;
    					path=MenuNameReader.getScreenProperties(custdetform.getPageId());
    					String value_str="1";
    					ComboValues(req,path,custdeligate);
    					Initial_Accsubcat(req, custdeligate,Integer.valueOf(custdetform.getCustform().getCusttype()));
    					/*req.setAttribute("AddressFlag",Integer.parseInt(custdetform.getAddrestype().trim()));*/
    					req.setAttribute("submit",value_str);
    					req.setAttribute("AddressType",custdeligate.CustomerAddress());
    					req.setAttribute("pageId","/Sucess.jsp");
    					req.setAttribute("custid_from_bean",Customer_id);
    					req.setAttribute("Verify_customer",0);
    					req.setAttribute("Update_sucessfully",num);
    					return map.findForward(ResultHelp.getSuccess());
    				}
    			} 
    			else if(map.getPath().equalsIgnoreCase("/custinst"))
    			{
    				CustomerInstituteForm customerinstitute=(CustomerInstituteForm)form;
    				Customerform customer_form=customerinstitute.getCustomer();
    				CustomerDelegate custdeligate=new CustomerDelegate(); 
    				System.out.println("******inside Customer Institute function********"+customerinstitute.getInstname());
    				//System.out.println("forward==="+customerinstitute.getForward());
    				
    				String Institute_name=customerinstitute.getInstname();
    				System.out.println("Institute_name===>"+Institute_name);
    				req.setAttribute("customerform",customer_form);
    				System.out.println("--------->"+customerinstitute.getButton());
    				String button_value=customerinstitute.getButton();
    			    System.out.println("submit========"+button_value); 
    				System.out.println("The  verificatiomn CIDis----->"+customer_form.getCustid());
    				req.setAttribute("Introducer","introid"); 
    				if(customer_form.getCustid()==0)
    				{
    					if(customerinstitute.getIntroid()!=null)
    					{
    						req.setAttribute("Introducer","introid2");
    						String customer_name=custdeligate.CustomerName(customerinstitute.getIntroid());
    						System.out.println("introducer name in customer action==in custinst===" +customer_name);
    						req.setAttribute("Cust_Name",customer_name);
    						req.setAttribute("customerform",customer_form); 
    						req.setAttribute("submit",customer_form.getValue());
    						req.setAttribute("AddressFlag",1);
    						req.setAttribute("AddressType",custdeligate.CustomerAddress());
    						if(MenuNameReader.containsKeyScreen(customer_form.getPageId()))
    						{
    							path=MenuNameReader.getScreenProperties(customer_form.getPageId());
    							Customertype(req,path,custdeligate);
    							ComboValues(req,path,custdeligate);
    							Initial_Accsubcat(req, custdeligate,Integer.valueOf(customerinstitute.getCustomer().getCusttype()));
    							req.setAttribute("pageName",MenuNameReader.getScreenProperties(customer_form.getCusttype().trim()));
							}
    						if(button_value!=null)
    						{
    							if(button_value.equalsIgnoreCase("submit"))
    							{
    								customermasterobject_current.setOperation(1); 
    								System.out.println("inst name in submit===>"+Institute_name);
    								System.out.println("inst name===> "+customerinstitute.getInstname());
    								System.out.println("inst name===> "+customer_form.getCusttype());
    								customerinstitute.setInstname(Institute_name);
    								System.out.println("ids--->"+customerinstitute.getIntro());
    								System.out.println("ids--->"+customerinstitute.getIntroid());
    								System.out.println("ids--->"+customerinstitute.getIntroid2());
    								int Customer_id=Institute_values_Inserting(customermasterobject_current, customerinstitute, customer_form, 0, custdeligate,(HashMap)session.getAttribute("addressList"));
    								
    								System.out.println("Customer-ID==========="+Customer_id);
    								req.setAttribute("pageId","/Sucess.jsp"); 
    								req.setAttribute("custid_from_bean",Customer_id);
    								req.setAttribute("Verify_customer",4);
    								req.setAttribute("Update_sucessfully",0);
    								customermasterobject_current=custdeligate.getCustomerdetails(Customer_id);
    	            				req.setAttribute("custName",customermasterobject_current.getName());
    	            				req.setAttribute("custDOB",customermasterobject_current.getDOB());
    								req.setAttribute("pageId","/Sucess.jsp");
    								
    								
    								return map.findForward(ResultHelp.getSuccess());
    							}
    						} 
    						return map.findForward(ResultHelp.getSuccess());
    					}
    				}
    				/******************************code for writing fetching cid from the new customer form**********************/
    				   
    				if(customer_form.getCustid()!=0)
    				{
    					System.out.println("This is cid greater than Zero"+customer_form.getCustid());
    					
    					
    					int cid=0;	   
    					req.setAttribute("customerform",customer_form);   
    					req.setAttribute("submit",customer_form.getValue());
	  
    					cid=customer_form.getCustid();
    					CustomerMasterObject mastobjtt=new CustomerMasterObject();
    					mastobjtt=custdeligate.getCustomerdetails(cid);
    					System.out.println("cid=="+cid);
    					System.out.println("mastobjtt.uv.getVerId()"+mastobjtt.uv.getVerId());
    					if(mastobjtt.uv.getVerId()==null)
    					{
    						    					req.setAttribute("Detail",mastobjtt);
    					}
    					else
    					{
    							System.out.println("Hello there block====");
    							customer_form.setTesting("Customer ID Already Verified");
    							customerinstitute.setTesting("Customer ID Already Verified");
    					}
    						
    					HashMap addressmap=custdeligate.getAddress(cid);
    					if(addressmap!=null)
    					{
    					req.setAttribute("Address",addressmap);
    					}
    					String customer_name=custdeligate.CustomerName(mastobjtt.getIntroducerId());
						System.out.println("introducer name in customer action===1390==" +customer_name);
						req.setAttribute("Cust_Name",customer_name);
    					req.setAttribute("AddressFlag",3);
    					req.setAttribute("AddressType",custdeligate.CustomerAddress());
    					System.out.println("button value in  institute---->"+button_value);
    					if(button_value!=null)
    					{ 
    							
    						
    						CustomerMasterObject custmast=custdeligate.getCustomerdetails(cid);
    					 
    						System.out.println("verify date----->"+custmast.uv.getVerId());
    						System.out.println("verify date----->"+custmast.uv.getVerTml());
    						
    						if(custmast.uv.getVerId()==null){
    						if(button_value.equalsIgnoreCase("verify"))
    						{   
    							int num=0;
    							System.out.println("custdetform.getVerify()======"+customerinstitute.getButtonvalues());
    							System.out.println("customer form cid====="+customer_form.getCustid()); 
    							int verify_cst=custdeligate.VerifyCustomer(customer_form.getCustid(),user,tml);
    							int cid1=customer_form.getCustid();
    							req.setAttribute("custid_from_bean",cid1);
    							req.setAttribute("Verify_customer",verify_cst);
    							req.setAttribute("Update_sucessfully",num);
    							
    							System.out.println("verify customer====== " +verify_cst);
    							req.setAttribute("pageId","/Sucess.jsp");
    							return map.findForward(ResultHelp.getSuccess());
    						 }
    						}
    						else{
    							System.out.println("i am in else block====");
    							customerinstitute.setTesting("Customer ID Already Verified");
    						}
    					}
        	     
    					   if(button_value.equalsIgnoreCase("update"))
    						{
    							System.out.println("**********inside update*********"); 
    							String false_value_inupdate="1";
    							req.setAttribute("update_flag_value",false_value_inupdate);
    							req.setAttribute("button_values","update_submit");
    							cid=customer_form.getCustid();
    							System.out.println("cid=="+cid);
    							req.setAttribute("Detail",custdeligate.getCustomerdetails(cid));
    							req.setAttribute("Address",custdeligate.getAddress(cid));
    				 			req.setAttribute("AddressFlag",1);
    							req.setAttribute("AddressType",custdeligate.CustomerAddress());
    							if(MenuNameReader.containsKeyScreen(customer_form.getPageId()))
    							{  
    								path=MenuNameReader.getScreenProperties(customer_form.getPageId());
    								Customertype(req,path,custdeligate);
    								ComboValues(req,path,custdeligate);
    								Initial_Accsubcat(req, custdeligate,Integer.valueOf(customerinstitute.getCustomer().getCusttype()));
    								req.setAttribute("pageName",MenuNameReader.getScreenProperties(customer_form.getCusttype().trim()));
    								
    							}
    							return map.findForward(ResultHelp.getSuccess());
    						}
    					

   //code for update submit
    					/****************** code for updating the data into database*****************/
     		           
    					else  if(button_value!=null)
    					{
    						if(button_value.equalsIgnoreCase("update_submit"))
    						{   
    							System.out.println("******Inside UpdateSubmit******");
    							customermasterobject_current=custdeligate.getCustomerdetails(customer_form.getCustid());
    							customermasterobject_old.copy(customermasterobject_old,customermasterobject_current);
    	    	   				customermasterobject_current.setOperation(0);
    	           				boolean flag=customermasterobject_current.equals(customermasterobject_old);
    							
    	           				System.out.println("customermasterobject_current.getName()=========="+customermasterobject_current.getName());
    							System.out.println("flag value inside update=======" +flag);
    							
    							if(flag==true)
    							{
    								customermasterobject_current.setOperation(2);		
    							}
    							
    							customermasterobject_current.addressIsEqual(customermasterobject_old.getAddress());
    							System.out.println("adddddresss is equallllllll=="+customermasterobject_current.addressIsEqual(customermasterobject_old.getAddress()));
    							int bit=0;
    							Set new_address=customermasterobject_current.getAddress().keySet();
    							Iterator itter_new_address=new_address.iterator();
    							aa:while(itter_new_address.hasNext()){
    								Integer i=(Integer)itter_new_address.next();
    								Address master_new=(Address)customermasterobject_current.getAddress().get(i);
    								if(master_new.isChanged()==true)
    								{
    									System.out.println("address changed==============="+master_new.isChanged());
                                        bit=3;
                                        break aa;
    								}
    							}
    							if(customermasterobject_current.getOperation()==2 && bit==3)
                                    customermasterobject_current.setOperation(4);
                                if(customermasterobject_current.getOperation()==0 && bit==3)
                                    customermasterobject_current.setOperation(3);
                                System.out.println("Get operation"+customermasterobject_current.getOperation());
    						
                               if(customermasterobject_current.getOperation()==0)
                               {
                            	   System.out.println("sucessfully updated");    
                               }else
                               {
                            	  
                                   
                            	   System.out.println("get operation before calling store==="+customermasterobject_current.getOperation());
                            	   int i=Institute_values_Inserting(customermasterobject_current, customerinstitute, customer_form,customer_form.getCustid(),custdeligate,null); 
                            	   System.out.println("after sucessfully updated customer id====" +i);
                            	   req.setAttribute("Update_sucessfully",i); 
                            	   req.setAttribute("custid_from_bean",0);
              	     			   req.setAttribute("Verify_customer",0);
              	     			   req.setAttribute("Customer_not_found",null);
              	     			   req.setAttribute("pageId","/Sucess.jsp");
              	     			   return map.findForward(ResultHelp.getSuccess());
                            	   
                            	   
                               } 
                                
    						}
    					}
    					 
    					
    					
    					
    					if(MenuNameReader.containsKeyScreen(customer_form.getPageId()))
    					{
    						path=MenuNameReader.getScreenProperties(customer_form.getPageId());
    						Customertype(req,path,custdeligate);
    						ComboValues(req,path,custdeligate);
    						Initial_Accsubcat(req, custdeligate,Integer.valueOf(customerinstitute.getCustomer().getCusttype()));
    						req.setAttribute("pageName",MenuNameReader.getScreenProperties(customer_form.getCusttype().trim()));
    					}
    					
    					String address=customerinstitute.getAddrestype();
    					if(address!=null)
    					{   
    						System.out.println("i am inside address----->");
    						mastobj=custdeligate.getCustomerdetails(customer_form.getCustid());
    						hash_addr=mastobj.getAddress();
    						addr=(Address)hash_addr.get(new Integer(Integer.parseInt(address.trim())));
    						req.setAttribute("AddressFlag",Integer.parseInt(customerinstitute.getAddrestype().trim()));
    						int cid1=0;	   
        					req.setAttribute("customerform",customer_form); 
        					req.setAttribute("submit",customer_form.getValue());
    	   					cid1=customer_form.getCustid();
        					System.out.println("cid=="+cid1);
        					CustomerMasterObject mastterobjec=new CustomerMasterObject();
        					mastterobjec=custdeligate.getCustomerdetails(cid1);
        					if(mastterobjec.uv.getVerId()==null)
        					{
        					req.setAttribute("Detail",mastterobjec);
        					}
        					else
        					{
        						System.out.println("In Address else block");
    							customer_form.setTesting("Customer ID Already Verified");
    							customerinstitute.setTesting("Customer ID Already Verified");
        					}
        					req.setAttribute("Address",custdeligate.getAddress(cid1));
        					req.setAttribute("AddressType",custdeligate.CustomerAddress());
        					
    						if(MenuNameReader.containsKeyScreen(customer_form.getPageId()))
        					{
        						path=MenuNameReader.getScreenProperties(customer_form.getPageId());
        						Customertype(req,path,custdeligate);
        						ComboValues(req,path,custdeligate);
        						Initial_Accsubcat(req, custdeligate,Integer.valueOf(customerinstitute.getCustomer().getCusttype()));
        						req.setAttribute("pageName",MenuNameReader.getScreenProperties(customer_form.getCusttype().trim()));
        					}
    						
    						return map.findForward(ResultHelp.getSuccess());
    						
    					}
	   
    					
	 
    			}
      
    			}		
       
    			else if(map.getPath().equalsIgnoreCase("/Customer/CustomerViewLog"))
    			{
    			try{	
    				CustomerViewLogForm viewform=(CustomerViewLogForm)form;
    				if(MenuNameReader.containsKeyScreen(viewform.getPageId()))
    				{ 
    					CustomerDelegate delegate=new CustomerDelegate(); 
    					path=MenuNameReader.getScreenProperties(viewform.getPageId());
    					System.out.println("path==================" +path);
    					int cid=viewform.getCustid();
    					//delegate.CustomerViewLog("CustomerMasterLog");
    					//System.out.println("view log from customer action============" +delegate.CustomerViewLog("CustomerMasterLog")); 
    					//req.setAttribute("viewlogcoloum",delegate.CustomerViewLog("CustomerMasterLog"));
    					CustomerLog(req,path,delegate,cid);
    					req.setAttribute("cid",cid);
    					req.setAttribute("pageId",path);
    					
    					return map.findForward(ResultHelp.getSuccess());
    				}
    			}catch(Exception e)
    			{
    				e.printStackTrace();
    				path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements(""+e, req, path);
					return map.findForward(ResultHelp.getError());
    			}	
    			}
    			else if(map.getPath().trim().equalsIgnoreCase("/customer/queryoncustomer"))
    			{   
    				int Customer_id1[]=null;
    				Vector vector=new Vector();
    				System.out.println("***** Inside /customer/queryoncustomer***** ");
    				QueryOnCustomerForm query=(QueryOnCustomerForm)form;  
    				CustomerDelegate delegate=new CustomerDelegate();
    				
    				String name=query.getName(); 
    				String comboname=query.getComboname();
    				System.out.println("name====="+comboname);
    				String[] trial=delegate.getcustomerIds(query.getComboname());
    				Customer_id1=new int[trial.length];
    				int custid_customer=-1;
    				for(int i=0;i<trial.length;i++) 
    				{   
    					StringTokenizer token_cust=new StringTokenizer(trial[i],"$$");
    					vector.add(token_cust.nextToken());
    					Customer_id1[i] = Integer.parseInt(token_cust.nextToken());
    					int index=i;
    					
    					 custid_customer=Customer_id1[index];
    					if(custid_customer!=-1)
    					  req.setAttribute("flag", "/Personnel.jsp");
    					  req.setAttribute("personalInfo",delegate.getCustomerdetails(custid_customer));
    					
    					
    				}
    				if(name!=null){
    				path=MenuNameReader.getScreenProperties(query.getPageId());
    				String name_cust[]=delegate.getcustomerIds(name);
    				
    				req.setAttribute("panelName","Personal");
    				
    				req.setAttribute("QueryOnName",name_cust); 
    				
    				req.setAttribute("pageId",path);
    				
    				return map.findForward(ResultHelp.getSuccess());
    				}
    				if(MenuNameReader.containsKeyScreen(query.getPageId()));
    				{
    					 
    					path=MenuNameReader.getScreenProperties(query.getPageId());
    					req.setAttribute("pageId",path);
    					return map.findForward(ResultHelp.getSuccess());
    				}
    			}
    		
    			if(map.getPath().equalsIgnoreCase("/AddressMenu"))
    			{
    				System.out.println("*******Address Menu**********");
    				CustomerInformationForm inform=(CustomerInformationForm)form;
    				CustomerDelegate custdeligate=new CustomerDelegate();
    				req.setAttribute("pageId","Address.jsp");
    				AddressTypesObject[] address2=custdeligate.CustomerAddress();
    				Map addr_list=new HashMap();
    				if(address2!=null)
    				{
    					for(int i=0;i<address2.length;i++)
    					{
    						Map map_addr=new LinkedHashMap();
    	     				map_addr.put(""+address2[i].getAddressCode(),"");
    	     				addr_list.put(""+address2[i].getAddressCode(),map_addr);
    					}
    				}
    				req.setAttribute("AddrType",address2);
     				session=req.getSession(true);
     				req.setAttribute("addr2", "");
     				session.setAttribute("addressList", addr_list);
    			}
    			if(map.getPath().equalsIgnoreCase("/Address"))
    			{
    				CustomerDelegate custdeligate=new CustomerDelegate();
    				System.out.println("*******Address**********");
    				CustomerInformationForm inform=(CustomerInformationForm)form;
    				
    				req.setAttribute("pageId","Address.jsp");
    				
    				//AddressTypesObject[] address2=custdeligate.CustomerAddress();
    				
    				//req.setAttribute("AddrType",address2);
    				
    				Map cat_list=(HashMap)session.getAttribute("addressList");
    				String method=req.getParameter("method");
    				System.out.println("method=="+method);
    				if(method!=null){
    					if(method.equalsIgnoreCase("Save")){
    						System.out.println("addr code=="+inform.getCombo_addresstype());
    						Map m=(HashMap)cat_list.get(inform.getCombo_addresstype());
    						System.out.println(m);
    						if(m!=null){
    							m.remove(inform.getCombo_addresstype());
    							m.put(inform.getCombo_addresstype(), inform.getAddressarea2());
    							System.out.println(m);
    							cat_list.remove(inform.getCombo_addresstype());
    							cat_list.put(inform.getCombo_addresstype(),m);
    							req.setAttribute("addr2", inform.getAddressarea2());
    							
    						}else{
    							System.out.println("m in else =="+m);
    						}
    						
    					}else{
    						System.out.println("hi i am submit");
    						setAddress((HashMap)session.getAttribute("addressList"), inform);
    					}
    					
    						
    				}else{
    					String addres="";
    					if(inform.getCombo_addresstype()!=null){
    						Map m=(HashMap)cat_list.get(inform.getCombo_addresstype());
							System.out.println(m);
							if(!m.get(inform.getCombo_addresstype().toString()).equals("")){
								addres=""+m.get(inform.getCombo_addresstype());
								System.out.println("addres<>>+"+addres);
								System.out.println(m);
							}
    					}
    					req.setAttribute("addr2", addres);
    				}
    				
    				session.setAttribute("addressList",cat_list);  				
    		}
    			
    		/*if(map.getPath().equalsIgnoreCase("/PhotoMenuLink"))
    		{
    			
    			CustomerDelegate custdeligate=new CustomerDelegate();
    			CustomerInformationForm query=(CustomerInformationForm)form;
    			req.setAttribute("salutation",custdeligate.getComboitems(5));
    			 path="/CustomerPanel.jsp";
    			 req.setAttribute("submit","1");
    			Customertype(req,path,custdeligate);
				ComboValues(req,path,custdeligate);
				//Initial_Accsubcat(req, custdeligate,Integer.valueOf(query.getCusttype()));
				req.setAttribute("AddressType",custdeligate.CustomerAddress());
				//req.setAttribute("pageName",MenuNameReader.getScreenProperties(query.getCusttype().trim()));
				req.setAttribute("customerform",query);
					req.setAttribute("pageId","/CustomerPanel.jsp");
					return map.findForward(ResultHelp.getSuccess());
				
    		}*/
    		else if(map.getPath().equalsIgnoreCase("/CustomerPanel"))
    		{ 
    		
    		int Customer_id=0;	   
			CustomerInformationForm custdetform=(CustomerInformationForm)form;
			
			System.out.println("how many times will  this display---->"+custdetform.getForward());
			if(custdetform.getForward().equals("clearitem"))
			{	custdetform.setAddressarea("");
				custdetform.setAddressarea2("");
				custdetform.setCity("");
				custdetform.setPin("");
				custdetform.setPhnum1("");
				custdetform.setForward("");
			}
			
			
			if(custdetform.getForward().equals("clearall"))
			{
			    System.out.println("i am in ClearAll");
			    CustomerMasterObject custdetformone=new CustomerMasterObject();
			    
			    custdetform.setName(null);
				custdetform.setMidname(null);
				custdetform.setLastname(null);
				custdetform.setFathername(null);
				custdetform.setMothername(null);
				custdetform.setSpousename(null);
				custdetform.setNation(null);
				custdetform.setReligion("");
				custdetform.setDob("");
				custdetform.setCaste("");
				custdetform.setIntro("");
				custdetform.setPan("");
				custdetform.setAddressarea("");
				custdetform.setAddressarea2("");
			  	//custdetformone.setAddrestype("");
			  	//custdetformone.setAddrproof("");
			  	//custdetformone.setCountry("");
			  	//custdetformone.setTxt_gurtype("");
				custdetform.setTxt_guardate("");
				custdetform.setTxt_guarcourtno("");
				custdetform.setTxt_guaraddress("");
				custdetform.setTxt_guarname("");
				custdetform.setCity("");
				custdetform.setPin("");
				custdetform.setMobile("");
				custdetform.setPhnum1("");
				custdetform.setMailid("");
				custdetform.setFaxno("");
				custdetform.setCid_fi("");
				custdetform.setName(""); 
				custdetform.setMidname("");
				custdetform.setLastname("");
			    custdetform.setSalutation("");
				//custdetform.setCategories("");
			    
			    custdetform.setPvt_sector("");		
				custdetform.setFathername(""); 
				custdetform.setMothername("");
				custdetform.setMarital("");
				custdetform.setSpousename("");
	            custdetform.setNation("");
				custdetform.setReligion("");
				custdetform.setSex("");
				custdetform.setCaste("");
				custdetform.setScst("");
			    custdetform.setDob("");
				custdetform.setIntro("");
				custdetform.setPassportno("");
				custdetform.setIssuedate("");
				custdetform.setExpirydate("");
				custdetform.setOccupation("");
				custdetform.setAddrproof("");
				custdetform.setNameproof("");
				custdetform.setPan("");
				custdetform.setTxt_gursex("");
				custdetform.setTxt_guaraddress("");
				custdetform.setTxt_guarcourtno("");
				custdetform.setTxt_gurtype("");
				
				req.setAttribute("pageId",MenuNameReader.getScreenProperties("1001"));
				return map.findForward(ResultHelp.getSuccess());
			}
			
			/*if(custdetform.getCid_first().equalsIgnoreCase("firsttime"))
			{
				System.out.println("hhhhheeeeeeeeeeeeeho");
				clear(req);
				custdetform.setCid_first("hooooooo");
			}*/
			
			
			System.out.println("********Sumanth****/CustomerPanel**************");
			if(custdetform.getCid()==0)
			{
				System.out.println("this will print when cid is zero");
				
            /*req.setAttribute("Detail",null);
			System.out.println("hi SUMANTH i am inside the new customer hello ");	
			custdetform.setCid_first("");
			custdetform.setCid_fi("");*/
			}
			CustomerDelegate custdeligate=new CustomerDelegate();
			Customerform customerform=custdetform.getCustform(); 
			custdetform.setVer_cid(null);
            System.out.println("custdetform.getPassportdetail()=====" +custdetform.getPassportdetail());
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+custdetform.getPassportdetail());
			String address=custdetform.getAddrestype();
			String button_values=custdetform.getButton();    
			System.out.println("button_values========"+button_values);
			String checkbox=custdetform.getPassportdetail(); 
			System.out.println("check box===========>"+checkbox);

			FormFile file=custdetform.getTheFile();
			if(file!=null){
			String content=file.getContentType();
			String filename=file.getFileName();
			int filesize=file.getFileSize();          
			byte[] filedata=file.getFileData();
			req.setAttribute("Image",filename);
			}
			
			/*if((customerform.getCustid()==0)&&((checkbox=="on")))
			{
			System.out.println("inside if condtition button values"); 	
			req.setAttribute("AddressType",custdeligate.CustomerAddress());
			req.setAttribute("submit",customerform.getValue());		
				req.setAttribute("checkbox",checkbox);
				if(MenuNameReader.containsKeyScreen(customerform.getPageId()))
			{
				path=MenuNameReader.getScreenProperties(customerform.getPageId());
				Customertype(req,path,custdeligate);
				ComboValues(req,path,custdeligate);
				req.setAttribute("pageName",MenuNameReader.getScreenProperties(customerform.getCusttype().trim()));
			}
			
				}
			*/
			AddressTypesObject[] address2=custdeligate.CustomerAddress();
			req.setAttribute("AddrType",address2);
			Map cat_list=(HashMap)session.getAttribute("addressList");
			String method=req.getParameter("method");
			System.out.println("method=="+method);
			if(method!=null){
				if(method.equalsIgnoreCase("Save"))
				{
					System.out.println("addr code=="+custdetform.getAddrestype());
					Map m=(HashMap)cat_list.get(custdetform.getAddrestype());
					System.out.println(m);
					if(m!=null)
					{
						m.remove(custdetform.getAddrestype());
						List int_list1=new ArrayList();
						int_list1.add(custdetform.getAddressarea());
						int_list1.add(custdetform.getCity());
						int_list1.add(custdetform.getCountry());
						int_list1.add(custdetform.getState());
						int_list1.add(custdetform.getPin());
						int_list1.add(custdetform.getPhnum1());
						System.out.println("the list is ---->"+int_list1);
						m.put(custdetform.getAddrestype(),int_list1);
						System.out.println(m);
						cat_list.remove(custdetform.getAddrestype());
						cat_list.put(custdetform.getAddrestype(),m);
						req.setAttribute("addr2", custdetform.getAddressarea());
						
					}else{
						System.out.println("m in else =="+m);
					}
					
				}else{
					System.out.println("hi i am submit");
					setAddress((HashMap)session.getAttribute("addressList"), custdetform);
				}
				
					
			}else{
				String addres="";
				if(custdetform.getAddrestype()!=null){
					System.out.println("Addr type>> "+custdetform.getAddrestype());
					Map m=(HashMap)cat_list.get(custdetform.getAddrestype());
					System.out.println(m);
					if(!m.get(custdetform.getAddrestype().toString()).equals("")){
						addres=""+m.get(custdetform.getAddrestype());
						System.out.println("addres<>>+"+addres);
						System.out.println(m);
					}
				}
				req.setAttribute("addr2", addres);
			}
			session.setAttribute("addressList",cat_list); 
/******************************code for writing fetching cid from the new customer form**********************/
			System.out.println("cid in Customer panel========>"+customerform.getCustid());
			System.out.println("================customer form in cp----->"+customerform);
			if(customerform.getCustid()!=0)  
			{
				int cid=0;	   
				//req.setAttribute("customerform",customerform); 
				req.setAttribute("submit",customerform.getValue());
					cid=customerform.getCustid();
				System.out.println("cid=="+cid);
				req.setAttribute("Detail",custdeligate.getCustomerdetails(cid));
				req.setAttribute("Address",custdeligate.getAddress(cid));
				System.out.println("Customer Address in Customer Panel-----====---->"+custdeligate.getAddress(cid));
				req.setAttribute("AddressFlag",1); 
				
			   
				if(button_values!=null)
				{ 
					
					if(button_values.equalsIgnoreCase("verify"))
					{   
						int num=0;
						System.out.println("custdetform.getVerify()======"+custdetform.getButtonvalues());
						System.out.println("customer form cid====="+customerform.getCustid()); 
						int verify_cst=custdeligate.VerifyCustomer(customerform.getCustid(),user,tml);
						int cid1=customerform.getCustid();
						req.setAttribute("custid_from_bean",cid1);
						req.setAttribute("Verify_customer",verify_cst);
						req.setAttribute("Update_sucessfully",num);
						
						System.out.println("verify customer====== " +verify_cst);
						req.setAttribute("pageId","/Sucess.jsp");
						return map.findForward(ResultHelp.getSuccess());
					}
				}
	     
				if(button_values.equalsIgnoreCase("update"))
					{
						System.out.println("**********inside update*********"); 
						String false_value_inupdate="1";
						req.setAttribute("update_flag_value",false_value_inupdate);
						req.setAttribute("button_values","update_submit");
						cid=customerform.getCustid();
						System.out.println("cid=="+cid);
						req.setAttribute("Detail",custdeligate.getCustomerdetails(cid));
						req.setAttribute("Address",custdeligate.getAddress(cid));
			 			req.setAttribute("AddressType",custdeligate.CustomerAddress());
						if(MenuNameReader.containsKeyScreen(customerform.getPageId()))
						{
							path=MenuNameReader.getScreenProperties(customerform.getPageId());
							Customertype(req,path,custdeligate);
							ComboValues(req,path,custdeligate);
							Initial_Accsubcat(req, custdeligate,Integer.valueOf(custdetform.getCustform().getCusttype()));
							//swethaeeeeeeeeeeeeeeeeee
							//req.setAttribute("pageName",MenuNameReader.getScreenProperties(customerform.getCusttype().trim()));
							req.setAttribute("pageName",MenuNameReader.getScreenProperties(customerform.getCusttype().trim()));
						}
						return map.findForward(ResultHelp.getSuccess());
					}
				

//code for update submit
				/****************** code for updating the data into database*****************/
		           
				else  if(button_values!=null)
				{
					if(button_values.equalsIgnoreCase("update_submit"))
					{   
						System.out.println("******Inside UpdateSubmit******");
						customermasterobject_current=custdeligate.getCustomerdetails(customerform.getCustid());
						customermasterobject_old.copy(customermasterobject_old,customermasterobject_current);
    	   				customermasterobject_current.setOperation(0);
           				boolean flag=customermasterobject_current.equals(customermasterobject_old);
						
           				System.out.println("customermasterobject_current.getName()=========="+customermasterobject_current.getName());
						System.out.println("flag value inside update=======" +flag);
						
						if(flag==true)
						{
							customermasterobject_current.setOperation(2);		
						}
						
						customermasterobject_current.addressIsEqual(customermasterobject_old.getAddress());
						System.out.println("adddddresss is equallllllll=="+customermasterobject_current.addressIsEqual(customermasterobject_old.getAddress()));
						int bit=0;
						Set new_address=customermasterobject_current.getAddress().keySet();
						Iterator itter_new_address=new_address.iterator();
						aa:while(itter_new_address.hasNext()){
							Integer i=(Integer)itter_new_address.next();
							Address master_new=(Address)customermasterobject_current.getAddress().get(i);
							if(master_new.isChanged()==true)
							{
								System.out.println("address changed==============="+master_new.isChanged());
                                bit=3;
                                break aa;
							}
						}
						if(customermasterobject_current.getOperation()==2 && bit==3)
                            customermasterobject_current.setOperation(4);
                        if(customermasterobject_current.getOperation()==0 && bit==3)
                            customermasterobject_current.setOperation(3);
                        System.out.println("Get operation"+customermasterobject_current.getOperation());
					
                       if(customermasterobject_current.getOperation()==0)
                       {
                    	   System.out.println("sucessfully updated");    
                       }
                       else
                       {
                    	  
                           
                    	   System.out.println("get operation before calling store==="+customermasterobject_current.getOperation());
                    	   int i= Inserting_values(customermasterobject_current,custdetform,customerform,customerform.getCustid(),custdeligate,(HashMap)session.getAttribute("addressList"));
                    	   System.out.println("after sucessfully updated customer id====" +i);
                    	  
                    	   req.setAttribute("Update_sucessfully",i); 
                    	   req.setAttribute("custid_from_bean",0);
      	     			   req.setAttribute("Verify_customer",0);
      	     			   req.setAttribute("Customer_not_found",null);
      	     			   req.setAttribute("pageId","/Sucess.jsp");
      	     			   return map.findForward(ResultHelp.getSuccess());
                    	   
                    	   
                       } 
                        
					}
				}
				if(MenuNameReader.containsKeyScreen(customerform.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(customerform.getPageId());
					Customertype(req,path,custdeligate);
					ComboValues(req,path,custdeligate);
					Initial_Accsubcat(req, custdeligate,Integer.valueOf(custdetform.getCustform().getCusttype()));
					req.setAttribute("pageName",MenuNameReader.getScreenProperties(customerform.getCusttype().trim()));
				}
				
				
				if(address!=null)
				{   
					mastobj=custdeligate.getCustomerdetails(custdetform.getCustform().getCustid());
					hash_addr=mastobj.getAddress();
					addr=(Address)hash_addr.get(new Integer(Integer.parseInt(address.trim())));
					req.setAttribute("AddressFlag",Integer.parseInt(custdetform.getAddrestype().trim()));
					int cid1=0;	   
					req.setAttribute("customerform",customerform); 
					req.setAttribute("submit",customerform.getValue());
   					cid1=customerform.getCustid();
					System.out.println("cid=="+cid1);
					req.setAttribute("Detail",custdeligate.getCustomerdetails(cid1));
					req.setAttribute("Address",custdeligate.getAddress(cid1));
					req.setAttribute("AddressType",custdeligate.CustomerAddress());
					
					if(MenuNameReader.containsKeyScreen(customerform.getPageId()))
					{
						path=MenuNameReader.getScreenProperties(customerform.getPageId());
						Customertype(req,path,custdeligate);
						ComboValues(req,path,custdeligate);
						Initial_Accsubcat(req, custdeligate,Integer.valueOf(custdetform.getCustform().getCusttype()));
						req.setAttribute("pageName",MenuNameReader.getScreenProperties(customerform.getCusttype().trim()));
					}
					
					return map.findForward(ResultHelp.getSuccess());
					
				} 
				
				
				

				
			} 

/*************************code for inserting values into customer table in the database*********************/
			if(custdetform.getCustform().getCustid()==0)
			{
				
				/*if(req.getParameter("cid_first").equalsIgnoreCase("firsttime"))
				{
					clear(req);
				}*/
				System.out.println("i am inside this introducer---->"+custdetform.getIntro());
				System.out.println("custdetform.getCustform().getCustid()--->"+custdetform.getCustform().getCustid());
				if(custdetform.getIntro()!=null)
				{
					req.setAttribute("Introducer","introid2");
					String customer_name=custdeligate.CustomerName(custdetform.getIntro());
					System.out.println("introducer name in customer action=====" +customer_name);
					req.setAttribute("Cust_Name",customer_name);
					System.out.println("Before the method--------------------2-");
					String age_val=Calcul_Age(req, custdetform,custdeligate);
					System.out.println("After the method--------------------2-");
					req.setAttribute("AgeAttribute",age_val);
					
				//	req.setAttribute("customerform",customerform); 
					req.setAttribute("submit",customerform.getValue());
					req.setAttribute("AddrssFlag",1);
					req.setAttribute("AddressType",custdeligate.CustomerAddress());
					if(MenuNameReader.containsKeyScreen(customerform.getPageId()))
					{
						path=MenuNameReader.getScreenProperties(customerform.getPageId());
						Customertype(req,path,custdeligate);
						ComboValues(req,path,custdeligate);
						Initial_Accsubcat(req, custdeligate,Integer.valueOf(customerform.getCusttype()));
						req.setAttribute("pageName",MenuNameReader.getScreenProperties(customerform.getCusttype().trim()));
					}

			if(button_values!=null)
			{
				
				if(button_values.equalsIgnoreCase("submit"))
				{ 
					System.out.println("i am in submit of customer Panel");
					customermasterobject_current.setOperation(1); 
					int i=Inserting_values(customermasterobject_current, custdetform, customerform, Customer_id, custdeligate,(HashMap)session.getAttribute("addressList"));
					System.out.println("Customer-ID==========="+i);
    				customermasterobject_old.copy(customermasterobject_old,customermasterobject_current);
    				customermasterobject_current=custdeligate.getCustomerdetails(i);
    				req.setAttribute("custName",customermasterobject_current.getName());
    				req.setAttribute("custDOB",customermasterobject_current.getDOB());
    				//req.setAttribute("custAddress",customermasterobject_current.getAddress());
    				
	     				req.setAttribute("custid_from_bean",i);
	     				req.setAttribute("Verify_customer",4); 
	     				req.setAttribute("Update_sucessfully",0);
	     				req.setAttribute("pageId","/Sucess.jsp");
	     				
	     				return map.findForward(ResultHelp.getSuccess());
    				
				}
			}
			return map.findForward(ResultHelp.getSuccess());
				}	
			}

			if(MenuNameReader.containsKeyScreen(custdetform.getPageId()))
			{
				int num=0;
				path=MenuNameReader.getScreenProperties(custdetform.getPageId());
				String value_str="1";
				ComboValues(req,path,custdeligate);
				Initial_Accsubcat(req, custdeligate,Integer.valueOf(custdetform.getCustform().getCusttype()));
				/*req.setAttribute("AddressFlag",Integer.parseInt(custdetform.getAddrestype().trim()));*/
				req.setAttribute("submit",value_str);
				req.setAttribute("AddressType",custdeligate.CustomerAddress());
				req.setAttribute("pageId","/Sucess.jsp");
				req.setAttribute("custid_from_bean",Customer_id);
				req.setAttribute("Verify_customer",0);
				req.setAttribute("Update_sucessfully",num);
				return map.findForward(ResultHelp.getSuccess());
			}	
			/*CustomerInformationForm query=(CustomerInformationForm)form;
			CustomerDelegate custdeligate=new CustomerDelegate();
			path="/Photo.jsp";
			req.setAttribute("submit","1");
			Customertype(req,path,custdeligate);
			ComboValues(req,path,custdeligate);
			//Initial_Accsubcat(req, custdeligate,Integer.valueOf(custform.getCusttype()));
			req.setAttribute("AddressType",custdeligate.CustomerAddress());
			//req.setAttribute("pageName",MenuNameReader.getScreenProperties(custform.getCusttype().trim()));
			//req.setAttribute("customerform",custform);
			if(query.getCid()!=0)
			{
			req.setAttribute("Detail",custdeligate.getCustomerdetails(query.getCid()));
			}
			req.setAttribute("salutation",custdeligate.getComboitems(5));
			System.out.println("First Name---->"+query.getName());
			System.out.println("Address----->"+query.getAddressarea2());
			System.out.println("Middle Name---->"+query.getMidname());
			System.out.println("Last Name------>"+query.getLastname());
			FormFile file=query.getTheFile();
			String content=file.getContentType();
			String filename=file.getFileName();
			int filesize=file.getFileSize();          
			byte[] filedata=file.getFileData();
			System.out.println("file name" +filename);
			System.out.println("file size" +filesize);
			System.out.println("file data" +filedata);
			if(MenuNameReader.containsKeyScreen(query.getPageId()))
			{
				CustomerDelegate delegate=new CustomerDelegate();
				path=MenuNameReader.getScreenProperties(query.getPageId());
				req.setAttribute("pageId",path);
				
				return map.findForward(ResultHelp.getSuccess()); 
			}*/
    		
    		
    		}
    			
    		if(map.getPath().equalsIgnoreCase("/AddressAmmendmentsMenu"))
    		{ 
    			System.out.println("i am in AddressAmmendmentsMenu");
    			AddressForm afm=(AddressForm) form;
    			
    			
    			if(MenuNameReader.containsKeyScreen(afm.getPageId()))
    			{
    				CustomerDelegate delegate=new CustomerDelegate();
    				path=MenuNameReader.getScreenProperties(afm.getPageId());
    				req.setAttribute("pageId",path);
    				req.setAttribute("HideAddresstype",null);
    				
    					afm.setCid(0);
    					afm.setUdate("");
    					afm.setResidential("");
    					afm.setCommunication("");
    					afm.setOffice("");
    					afm.setCity("");
    					afm.setState("");
    					afm.setPin(0);
    					afm.setCountry("");
    					afm.setPhno(0);
    					afm.setPhstd(0);
    					afm.setFax(0);
    					afm.setFaxstd(0);
    					afm.setMobile("");
    					afm.setEmail("");
    				      					
    					
    					afm.setComcity("");
    					afm.setComcountry("");
    					afm.setComphno(0);
    					afm.setCompin(0);
    					afm.setComstate("");
    					
    					afm.setOffcity("");
    					afm.setOffcountry("");
    					afm.setOffphno(0);
    					afm.setOffpin(0);
    					afm.setOffstate("");
    					
    					
    					
    				return map.findForward(ResultHelp.getSuccess()); 
    			} 
    		}
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    		 else if(map.getPath().equalsIgnoreCase("/adderss"))
    		{
    			Connection con=null;
    			System.out.println("M in Action Class");
    			CustomerDelegate  custdeligate=new CustomerDelegate();
    			int CustType=0;
    				AddressForm sbForm=(AddressForm)form;
    				int cid2=sbForm.getCid();
    				
    				
    				
    				
    				
    				System.out.println("CID Value"+cid2);
    			if(sbForm.getHid().equals("Hidden"))
    				{
    					try
    					{
    				//to fetch values and set 
    				Class.forName("com.mysql.jdbc.Driver");
    				//con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost/bkcbhoaug","bkcb","bkcb");
    				con=(Connection) DriverManager.getConnection("jdbc:mysql://192.168.1.2/RAMNAGAR22022010","root","");
    				Statement st=(Statement) con.createStatement();
    				ResultSet rs2=(ResultSet) st.executeQuery("select * from CustomerMaster where cid='"+cid2+"'");
    				while(rs2.next())
    				{
    				     CustType=rs2.getInt(3);
    				     System.out.println("the value of CustType is----->"+CustType);
    				     
    				}
    				if(CustType==1)
    				{
    					
    					req.setAttribute("HideAddresstype", "rescom");
    				}else{
    					req.setAttribute("HideAddresstype", null);
    				}
    				ResultSet rs=(ResultSet) st.executeQuery("select * from CustomerAddr where cid='"+cid2+"'");
    					 //ResultSet rs1=(ResultSet) st.executeQuery("select * from CustomerAddr where cid='"+cid2+"' and addr_type='1'");
    					// ResultSet rs2=(ResultSet) st.executeQuery("select * from CustomerAddr where cid='"+cid2+"' and addr_type='2'");
    					 //ResultSet rs3=(ResultSet) st.executeQuery("select * from CustomerAddr where cid='"+cid2+"' and addr_type='3'");
    					 while(rs.next())
    					 {
    						 
    						 System.out.println("value of Address type"+rs.getInt(2));
    						 System.out.println("value of Address ColName type"+rs.getInt("addr_type"));
    						 if(rs.getInt("addr_type")==1)
    						 {
    							 sbForm.setResidential(rs.getString(4));
    							 System.out.println("M inside if statement");
    							 sbForm.setCity(rs.getString(5));
    							 sbForm.setState(rs.getString(6));
    							 sbForm.setPin(rs.getInt(7));
    							 sbForm.setCountry(rs.getString(8));
    							 sbForm.setPhno(rs.getInt(9));
    							 
    							 //System.out.println("M after Query");

    						 }
    						 if(rs.getInt("addr_type")==2)
    						 {
    							 
    							 sbForm.setCommunication(rs.getString(4));
    							  System.out.println("m in 2nd if block ");
    							  sbForm.setComcity(rs.getString(5));
     							 sbForm.setComstate(rs.getString(6));
     							 sbForm.setCompin(rs.getInt(7));
     							 sbForm.setComcountry(rs.getString(8));
     							 sbForm.setComphno(rs.getInt(9));
    							// System.out.println("M in 2nd if n after Query");
    						 }
    						 
    					 
    						 
    						  if(rs.getInt("addr_type")==3)
    						 {
    							 sbForm.setOffice(rs.getString(4));
    							 System.out.println("m in 3nd if block");
    							 sbForm.setOffcity(rs.getString(5));
    							 sbForm.setOffstate(rs.getString(6));
    							 sbForm.setOffpin(rs.getInt(7));
    							 sbForm.setOffcountry(rs.getString(8));
    							 sbForm.setOffphno(rs.getInt(9));
    							 
    						 }
    						
    					 
    					
    						 sbForm.setUdate(rs.getString(3));;
    						 sbForm.setFax(rs.getInt(11));
    						 sbForm.setPhstd(rs.getInt(10));
    						 sbForm.setPhno(rs.getInt(9));
    						 sbForm.setFaxstd(rs.getInt(12));
    						 sbForm.setMobile(rs.getString(13));
    						 if(rs.getString(14)!=null){
    						 sbForm.setEmail(rs.getString(14));
    						 }else{
    							 sbForm.setEmail("");
    						 }
    						// request.setAttribute("SBform", sbForm);

    				}
    				}
    				catch(Exception e)
    				{
    					req.setAttribute("msg","Customer Not Found");
    					System.out.println("Exception"+e);
    				}
    				finally
    				{
    					try{
    					con.close();
    					}
    					catch (Exception e) {
							System.out.println("Exception in CustomerAction");
						}
    				}
    				sbForm.setHid("");
    				}
    			
    			System.out.println("get parameter====> ");
    			System.out.println("i am here before submit "+sbForm.getSub());
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			if(sbForm.getSub().equalsIgnoreCase("Submit"))
    			{
    				System.out.println("M in submit block");
    				try
    				{

    			Class.forName("com.mysql.jdbc.Driver");
    				//con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost/bkcbhoaug","bkcb","bkcb");
    				con=(Connection) DriverManager.getConnection("jdbc:mysql://192.168.1.2/RAMNAGAR22022010","root","");
    			Statement st=(Statement) con.createStatement();
    			
    			st.executeUpdate("delete from CustomerAddr where cid='"+cid2+"'");
    			//String s1=sbForm.getResidential();
    			if(sbForm.getResidential()!=null)
    			{
    				String queryStr="insert into CustomerAddr values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    				PreparedStatement pst=(PreparedStatement) con.prepareStatement(queryStr);
    				System.out.println("M Inside PrepareStatement");
    				pst.setInt(1, sbForm.getCid());
    				pst.setInt(2,1);
    				pst.setString(3,sbForm.getUdate());
    				pst.setString(4,sbForm.getResidential());
    				pst.setString(5,sbForm.getCity() );
    				pst.setString(6, sbForm.getState());
    				pst.setInt(7, sbForm.getPin());
    				pst.setString(8, sbForm.getCountry());
    				pst.setInt(9, sbForm.getPhno());
    				pst.setInt(10, sbForm.getPhstd());
    				pst.setInt(11, sbForm.getFax());
    				pst.setInt(12, sbForm.getFaxstd());
    				pst.setString(13, sbForm.getMobile());
    				pst.setString(14, sbForm.getEmail());
    				pst.setString(15,"1044");
    				pst.setString(16,"CA99");
    				pst.setString(17,custdeligate.getSysDate());
    				int re=pst.executeUpdate();
    				if(re==1){
    					req.setAttribute("msg","Address Successfully Updated");
    					//updating master log
    					String queryStr1="insert into CustomerAddrLog values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        				PreparedStatement pstm=(PreparedStatement) con.prepareStatement(queryStr1);
        				System.out.println("M Inside PrepareStatement of Log");
        				pstm.setInt(1, sbForm.getCid());
        				pstm.setInt(2,1);
        				pstm.setString(3,sbForm.getUdate());
        				pstm.setString(4,sbForm.getResidential());
        				pstm.setString(5,sbForm.getCity() );
        				pstm.setString(6, sbForm.getState());
        				pstm.setInt(7, sbForm.getPin());
        				pstm.setString(8, sbForm.getCountry());
        				pstm.setInt(9, sbForm.getPhno());
        				pstm.setInt(10, sbForm.getPhstd());
        				pstm.setInt(11, sbForm.getFax());
        				pstm.setInt(12, sbForm.getFaxstd());
        				pstm.setString(13, sbForm.getMobile());
        				pstm.setString(14, sbForm.getEmail());
        				pstm.setString(15, "1044");
        				pstm.setString(16,"CA99");
        				pstm.setString(17,custdeligate.getSysDate());
        				int c=pstm.executeUpdate();
        				System.out.println("After storing in Log====>"+c);
    				}
    				else{
    					req.setAttribute("msg","Address cannot be updated");
    					
    				}
    				System.out.println("M after PrepareStatement===>>>"+re);
    			}
    			
    			if(sbForm.getCommunication()!=null)
    			{
    				String queryStr="insert into CustomerAddr values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    				PreparedStatement pst=(PreparedStatement) con.prepareStatement(queryStr);
    				System.out.println("M Inside 2nd PrepareStatement");
    				pst.setInt(1, sbForm.getCid());
    				pst.setInt(2,2);
    				pst.setString(3,sbForm.getUdate());
    				pst.setString(4,sbForm.getCommunication());
    				pst.setString(5,sbForm.getComcity() );
    				pst.setString(6, sbForm.getComstate());
    				pst.setInt(7, sbForm.getCompin());
    				pst.setString(8, sbForm.getComcountry());
    				pst.setInt(9, sbForm.getComphno());
    				pst.setInt(10, sbForm.getPhstd());
    				pst.setInt(11, sbForm.getFax());
    				pst.setInt(12, sbForm.getFaxstd());
    				pst.setString(13, sbForm.getMobile());
    				pst.setString(14, sbForm.getEmail());
    				pst.setString(15, "1044");
    				pst.setString(16, "CA99");
    				pst.setString(17,custdeligate.getSysDate());
    				int re=pst.executeUpdate();
    				if(re==1){
    					req.setAttribute("msg","Address Successfully Updated");
    					//updating master log
    					String queryStr2="insert into CustomerAddrLog values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        				PreparedStatement pstm=(PreparedStatement) con.prepareStatement(queryStr2);
        				System.out.println("M Inside PrepareStatement of Log");
        				pstm.setInt(1, sbForm.getCid());
        				pstm.setInt(2,2);
        				pstm.setString(3,sbForm.getUdate());
        				pstm.setString(4,sbForm.getCommunication());
        				pstm.setString(5,sbForm.getComcity() );
        				pstm.setString(6, sbForm.getComstate());
        				pstm.setInt(7, sbForm.getCompin());
        				pstm.setString(8, sbForm.getComcountry());
        				pstm.setInt(9, sbForm.getComphno());
        				pstm.setInt(10, sbForm.getPhstd());
        				pstm.setInt(11, sbForm.getFax());
        				pstm.setInt(12, sbForm.getFaxstd());
        				pstm.setString(13, sbForm.getMobile());
        				pstm.setString(14, sbForm.getEmail());
        				pstm.setString(15, "1044");
        				pstm.setString(16, "CA99");
        				pstm.setString(17,custdeligate.getSysDate());
        				int c=pstm.executeUpdate();
        				System.out.println("After storing in Log====>"+c);
    				}
    				else{
    					req.setAttribute("msg","Address cannot be updated");
    					
    				}
    				System.out.println("M after 2nd PrepareStatement--->>"+re);
    			}
    			
    			if(sbForm.getOffice()!=null)
    			{
    				String queryStr="insert into CustomerAddr values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    				PreparedStatement pst=(PreparedStatement) con.prepareStatement(queryStr);
    				System.out.println("M Inside 3nd PrepareStatement");
    				pst.setInt(1, sbForm.getCid());
    				pst.setInt(2,3);
    				pst.setString(3,sbForm.getUdate());
    				pst.setString(4,sbForm.getOffice());
    				pst.setString(5,sbForm.getOffcity() );
    				pst.setString(6, sbForm.getOffstate());
    				pst.setInt(7, sbForm.getOffpin());
    				pst.setString(8, sbForm.getOffcountry());
    				pst.setInt(9, sbForm.getOffphno());
    				pst.setInt(10, sbForm.getPhstd());
    				pst.setInt(11, sbForm.getFax());
    				pst.setInt(12, sbForm.getFaxstd());
    				pst.setString(13, sbForm.getMobile());
    				pst.setString(14, sbForm.getEmail());
    				pst.setString(15, "1044");
    				pst.setString(16, "CA99");
    				pst.setString(17,custdeligate.getSysDate());
    				int re=pst.executeUpdate();
    				if(re==1){
    					req.setAttribute("msg","Address Successfully Updated");
    					//updating master log
    					String queryStr3="insert into CustomerAddrLog values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        				PreparedStatement pstm=(PreparedStatement) con.prepareStatement(queryStr3);
        				System.out.println("M Inside PrepareStatement of Log");
        				pstm.setInt(1, sbForm.getCid());
        				pstm.setInt(2,3);
        				pstm.setString(3,sbForm.getUdate());
        				pstm.setString(4,sbForm.getOffice());
        				pstm.setString(5,sbForm.getOffcity() );
        				pstm.setString(6, sbForm.getOffstate());
        				pstm.setInt(7, sbForm.getOffpin());
        				pstm.setString(8, sbForm.getOffcountry());
        				pstm.setInt(9, sbForm.getOffphno());
        				pstm.setInt(10, sbForm.getPhstd());
        				pstm.setInt(11, sbForm.getFax());
        				pstm.setInt(12, sbForm.getFaxstd());
        				pstm.setString(13, sbForm.getMobile());
        				pstm.setString(14, sbForm.getEmail());
        				pstm.setString(15, "1044");
        				pstm.setString(16, "CA99");
        				pstm.setString(17,custdeligate.getSysDate());
        				int c=pstm.executeUpdate();
        				System.out.println("After storing in Log====>"+c);
    				}
    				else{
    					req.setAttribute("msg","Address cannot be updated");
    					
    				}
    				System.out.println("M after 3nd PrepareStatement-->>"+re);
    				
    			}
    			
    			
    			
    				
    				}
    				catch(Exception e)
    				{
    					System.out.println("Exception"+e);
    				}
    				req.setAttribute("pageId",MenuNameReader.getScreenProperties(sbForm.getPageId()));
        			return map.findForward(ResultHelp.getSuccess());
    				
    			}
    			
    			
    			System.out.println("The CID is----->"+sbForm.getCid());
    			if(sbForm.getCid()==0)
				{
					sbForm.setCid(0);
					sbForm.setUdate("");
					sbForm.setResidential("");
					sbForm.setCommunication("");
					sbForm.setOffice("");
					sbForm.setCity("");
					sbForm.setState("");
					sbForm.setPin(0);
					sbForm.setCountry("");
					sbForm.setPhno(0);
					sbForm.setPhstd(0);
					sbForm.setFax(0);
					sbForm.setFaxstd(0);
					sbForm.setMobile("");
					sbForm.setEmail("");
					sbForm.setComcity("");
					sbForm.setComcountry("");
					sbForm.setComphno(0);
					sbForm.setCompin(0);
					sbForm.setComstate("");
					
					sbForm.setOffcity("");
					sbForm.setOffcountry("");
					sbForm.setOffphno(0);
					sbForm.setOffpin(0);
					sbForm.setOffstate("");
				}
    			
    			
    			req.setAttribute("pageId",MenuNameReader.getScreenProperties(sbForm.getPageId()));
    			return map.findForward(ResultHelp.getSuccess());
    			
    			
    			
    		}    		
    		
    		
    	return map.findForward(ResultHelp.getSuccess());
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
     	}
    
  	public void clear(HttpServletRequest req1,ActionForm form)
	{
  	System.out.println("I am in Clear method of customer action ");
  	CustomerInformationForm custdetformone=(CustomerInformationForm) form ;
  	custdetformone.setName("");
  	custdetformone.setMidname("");
  	custdetformone.setLastname("");
  	custdetformone.setFathername("");
  	custdetformone.setMothername("");
  	custdetformone.setSpousename("");
  	custdetformone.setNation("");
  	custdetformone.setReligion("");
  	custdetformone.setDob("");
  	custdetformone.setCaste("");
  	custdetformone.setIntro("");
  	custdetformone.setPan("");
  	custdetformone.setAddressarea("");
  	custdetformone.setAddressarea2("");
  	//custdetformone.setAddrestype("");
  	//custdetformone.setAddrproof("");
  	//custdetformone.setCountry("");
  	//custdetformone.setTxt_gurtype("");
  	custdetformone.setTxt_guardate("");
  	custdetformone.setTxt_guarcourtno("");
  	custdetformone.setTxt_guaraddress("");
    	custdetformone.setTxt_guarname("");
  	 custdetformone.setCity("");
   	 custdetformone.setPin("");
  	 custdetformone.setMobile("");
  	 custdetformone.setPhnum1("");
  	 custdetformone.setMailid("");
  	 custdetformone.setFaxno("");
  	// req1.setAttribute("pleaseclear",10525525); 		   
	 }
  	
  	
  	public void makeitnull()
  	{
  		System.out.println("i am in makeitclear===========");
  		MenuForm menuformone=new MenuForm();
  		menuformone.setCid_first("helloclear");
  	}
    
    
    
    
    
    	
     	/*private Map getAddressMap(){
     		if(map==null)
     			map=new LinkedHashMap();
     		return map;
     	}*/
 /*===============method to fetch the detail of customer from customer deligate=============*/
     
      
     	private void Customertype(HttpServletRequest req,String path,CustomerDelegate custdeligate)throws Exception
     	{
     		try{
     			req.setAttribute("pageId",path);
     			req.setAttribute("Customertype",custdeligate.getComboitems(1));
     			String cust[]=custdeligate.getComboitems(1);
     		}catch(Exception e)
     		{
     			throw e;
     		}
     	}
     	private void ComboValues(HttpServletRequest req,String path,CustomerDelegate custdeligate)throws Exception
     	{
     		try{
     			req.setAttribute("salutation",custdeligate.getComboitems(5));
     			String country[]=custdeligate.getComboitems(10);
     			String[] country_values=new String[country.length];
     			for(int i=0;i<country.length;i++)
     			{
     				StringTokenizer str_country=new StringTokenizer(country[i],"$$$");
     				country_values[i]=str_country.nextToken();
     				req.setAttribute("Country",country_values);
     			}
     			req.setAttribute("AddressProof",custdeligate.getComboitems(7));
     			req.setAttribute("NameProof",custdeligate.getComboitems(8));
     			req.setAttribute("MaritalStatus",custdeligate.getComboitems(6));
     			req.setAttribute("sub_occ",custdeligate.getComboitems(4));
     			req.setAttribute("occupation",custdeligate.getComboitems(3));
     			req.setAttribute("states",custdeligate.getComboitems(11));
     			String states[]=custdeligate.getComboitems(11);
     			req.setAttribute("statevalues",states);  
     			req.setAttribute("caste",custdeligate.getComboitems(9));
     		}catch(Exception e)
     		{
     			throw e;
     		} 
 
     	}
     	private void Initial_Accsubcat(HttpServletRequest req,CustomerDelegate deligate,int num)throws RemoteException
     	{
     		req.setAttribute("AccountSubCat",deligate.getAccSubCategories(num));
     		
     	}
     	private void Customization(HttpServletRequest req,String path,CustomerDelegate custdeligate)throws Exception
     	{
     		try{ 
     			req.setAttribute("pageId",path);
     			req.setAttribute("Customization",custdeligate.Customizationparam("CustomerParameters"));
     			System.out.println("customization value in customer action============="+custdeligate.Customizationparam("CustomerParameters")); 
     		}catch(Exception e)
     		{
     			throw e;	
     		}
	  
     	}
      
     	private void CustomerLog(HttpServletRequest req,String path,CustomerDelegate custdeligate,int cid)throws RemoteException
     	{
     			req.setAttribute("viewlogcoloum",custdeligate.CustomerViewLog("CustomerMasterLog"));
     			req.setAttribute("Viewlogrow",custdeligate.Customerviewlogcoloum("CustomerMasterLog","cid",cid));
     			req.setAttribute("addresslogcoloum",custdeligate.CustomerViewLog("CustomerAddrLog"));
     			//System.out.println("address log" +custdeligate.CustomerViewLog("CustomerAddrLog"));
     			req.setAttribute("addresslogrow",custdeligate.Customerviewlogcoloum("CustomerAddrLog","cid",cid));
     			//System.out.println("addresslog row" +custdeligate.Customerviewlogcoloum("CustomerMasterLog","cid",cid));
     			     		
     	}
     	private void Address_type(HttpServletRequest req,CustomerDelegate custdeligate)throws ApplicationException,SystemException,RemoteException
     	{
     		AddressTypesObject[] address=custdeligate.CustomerAddress();
     		String str_addr[]=new String[address.length];
     		for(int i=0;i<address.length;i++)
     		{
     			str_addr[i]=address[i].getAddressDesc();
     			req.setAttribute("AddressType",str_addr); 
	 
     		}
     	}
     	private void setAddress(HashMap map,CustomerInformationForm custdetform){
     		
     		HashMap addr_map=new HashMap();
     		Set keyset=map.keySet();
     		Address addr;
     		Iterator iter=keyset.iterator();
     		String key;
     		HashMap submap;
     		while(iter.hasNext()){
     			key=iter.next().toString();
     			
     			System.out.println("-->"+map.get(key));
     			submap=(HashMap)map.get(key);
     			if(!submap.get(key).toString().equals("")){
     				addr=new Address();
     				addr.setFlag((Integer.parseInt(key))); 
				
     				addr.setAddress(submap.get(key).toString());
     				addr.setCity("Bangalore");
     				addr.setCountry("India");
     				addr.setState("Karnataka");
     				addr.setPin("560004");
     				addr.setMobile("9999999999");
     				addr.setPhStd("080");
     				if(addr_map.get(key)==null)
     					addr_map.put(key,addr);
     				}
     		}
     		if(addr_map!=null){
     			keyset=addr_map.keySet();
         		iter=keyset.iterator();
         		
         		
         		while(iter.hasNext()){
         			key=iter.next().toString();
         			addr=(Address)addr_map.get(key);
         			System.out.println("---------------------------");
         			System.out.println();
         			System.out.println(addr.getFlag());
         			System.out.println(addr.getAddress());
         			System.out.println(addr.getCity());
         			System.out.println(addr.getState());
         			System.out.println(addr.getCountry());
         			System.out.println();
         			System.out.println("---------------------------");
         			
         		}
         		
     		}
     	}
     	private int Inserting_values(CustomerMasterObject customermasterobject_current,CustomerInformationForm custdetform,Customerform customerform,int Customer_id,CustomerDelegate custdeligate,HashMap map)throws RemoteException,IOException
     	{
 		
     		String addr_type_obj[];
     		Address address_object=new Address(); 
    		String j;
    		Address addr=null;
    		HashMap hash_addr=new HashMap();
             System.out.println("RELIGION BEFORE SUBMITING=======> "+custdetform.getReligion());
			System.out.println("the sex of customrer---->"+custdetform.getSex());
            System.out.println("the custtype=====> "+custdetform.getCusttype());
			customermasterobject_current.setBranchCode("Head Office");
			System.out.println("customermasterobject_current.setBranchCode=================="+customermasterobject_current.getBranchCode());
			if(customerform.getCusttype().equalsIgnoreCase("1002"))
			{
				customermasterobject_current.setCategory(0);
			}	
			customermasterobject_current.setFirstName(custdetform.getName()); 
			customermasterobject_current.setMiddleName(custdetform.getMidname());
			customermasterobject_current.setLastName(custdetform.getLastname());
			customermasterobject_current.setSalute(custdetform.getSalutation());
			customermasterobject_current.setSubCategory(custdetform.getCategories());
			customermasterobject_current.setSubOccupation(custdetform.getPvt_sector());		
			customermasterobject_current.setFatherName(custdetform.getFathername()); 
			customermasterobject_current.setMotherName(custdetform.getMothername());
			customermasterobject_current.setMaritalStatus(custdetform.getMarital());
			customermasterobject_current.setSpouseName(custdetform.getSpousename());
			customermasterobject_current.setNationality(custdetform.getNation());
			customermasterobject_current.setReligion(custdetform.getReligion());
			customermasterobject_current.setSex(custdetform.getSex());
			customermasterobject_current.setCaste(custdetform.getCaste());
			customermasterobject_current.setScSt(custdetform.getScst());
			customermasterobject_current.setDOB(custdetform.getDob());
			customermasterobject_current.setIntroducerId(custdetform.getIntro());
			customermasterobject_current.setPassPortNumber(custdetform.getPassportno());
			customermasterobject_current.setPPIssueDate(custdetform.getIssuedate());
			customermasterobject_current.setPPExpiryDate(custdetform.getExpirydate());
			customermasterobject_current.setOccupation(custdetform.getOccupation());
			customermasterobject_current.setAddressProof(custdetform.getAddrproof());
			customermasterobject_current.setNameProof(custdetform.getNameproof());
			customermasterobject_current.setPanno(custdetform.getPan());
			customermasterobject_current.setGuardName(custdetform.getTxt_gursex());
			customermasterobject_current.setGuardAddress(custdetform.getTxt_guaraddress());
			customermasterobject_current.setGuardRelationship(custdetform.getTxt_guarcourtno());
			customermasterobject_current.setGuardType(custdetform.getTxt_gurtype());
			
			FormFile file=custdetform.getTheFile();
			String content=file.getContentType();
			String filename=file.getFileName();
			int filesize=file.getFileSize();          
			byte[] filedata=file.getFileData();
			customermasterobject_current.setPhoto(filename);
			customermasterobject_current.setBinaryImage(filedata);
			System.out.println("Input stream----->=====>---->"+file.getInputStream());
			System.out.println("filesize----------->"+filesize); 
			System.out.println("filesize----------->"+file);
			System.out.println("content======>"+content); 
			
			FormFile signature=custdetform.getSecfile();
			String sigName=signature.getFileName();
			int signaturesize=signature.getFileSize();
			byte[] sigdata=signature.getFileData();
			
			customermasterobject_current.setSign(sigName);
			customermasterobject_current.setBinarySignImage(sigdata);
			
			System.out.println("file name" +filename);
			System.out.println("file size" +filesize);
			System.out.println("file data" +filedata);
			customermasterobject_current.uv.setUserId(user);
			customermasterobject_current.uv.setUserTml(tml);
			String[] address_addtype={custdetform.getAddrestype()};
			addr_type_obj=address_addtype;
			System.out.println("map-->"+map);
			if(map!=null){
				Set keyset=map.keySet();
				Iterator iter=keyset.iterator();
				String key;
				HashMap submap;
				while(iter.hasNext()){
					key=iter.next().toString();
					System.out.println("-->"+map.get(key));
					submap=(HashMap)map.get(key);
					if(!submap.get(key).toString().equals(""))
					{
						addr=new Address();
						addr.setFlag((Integer.parseInt(key))); 
						//addr.setAddress(submap.get(key).toString());
						System.out.println("submap value===>"+submap.get(key));
						
						StringTokenizer st=new StringTokenizer(submap.get(key).toString(),",");
						String address=st.nextToken();
						String city=st.nextToken();
						String country=st.nextToken();
						String state=st.nextToken();
						String pinco=st.nextToken();
						String landphno=st.nextToken();
						System.out.println("Addresss---->"+address);
                        System.out.println("city=====>"+city);
                        System.out.println("country=====>"+country);
                        System.out.println("state=====>"+state);
                        System.out.println("pinco=====>"+pinco);
                        System.out.println("landphno=====>"+landphno);
                                              
                        String realaddress=address.substring(1);
                                               
                        System.out.println("realaddress====>"+realaddress);
                        addr.setAddress(realaddress);
						addr.setCity(city);
						addr.setCountry(country);
						addr.setState(state);
						addr.setPin(pinco);
						if(custdetform.getPin().trim()==null||custdetform.getPin().trim().length()==0)
						{
							addr.setPin("0");
						}else{
						addr.setPin(pinco);
						}
						if(custdetform.getMobile().trim()==null||custdetform.getMobile().trim().length()==0)
						{
							addr.setMobile("0");						
						}else{
						addr.setMobile(custdetform.getMobile());
					    }
					    if(custdetform.getPhnum1().trim()==null||custdetform.getPhnum1().trim().length()==0){
					    	addr.setPhStd("0");
					    	addr.setPhno("0");
					    }else{
						addr.setPhStd(custdetform.getPhnum1());//PARUL//
						addr.setPhno(custdetform.getPhnum1());
						}
					    if(custdetform.getFaxno().trim()==null||custdetform.getFaxno().trim().length()==0)
					    {
					    	addr.setFax("0");
					    }else{
						addr.setFax(custdetform.getFaxno());
					    }
						addr.setChanged(true);
						if(custdetform.getMailid().trim()==null||custdetform.getMailid().trim().length()==0){
							addr.setEmail(" ");
						}else
						{
							
						addr.setEmail(custdetform.getMailid());
						}
						addr.setFaxStd("0");
						if(hash_addr.get(key)==null){
							System.out.println("key=="+key);
							System.out.println("addr=="+addr);
							
							hash_addr.put(key,addr);
						}
     				}
				}
			}
			
			/*for(int i=0;i<addr_type_obj.length;i++)
			{
				swe
				j=addr_type_obj[i];
				addr=new Address();
				addr.setFlag((Integer.parseInt(key))); 
				
 				addr.setAddress(submap.get(key).toString());
				//addr.setFlag((Integer.parseInt(j))); 
				//addr.setAddress(custdetform.getAddressarea());
				addr.setCity(custdetform.getCity());
				addr.setCountry(custdetform.getCountry());
				addr.setState(custdetform.getState());
				addr.setPin(custdetform.getPin());
				addr.setMobile(custdetform.getMobile());
				addr.setPhStd(custdetform.getPhnum1());
				addr.setFax(custdetform.getFaxno());
				addr.setEmail(custdetform.getMailid());

				if(hash_addr.get(j)==null)
					hash_addr.put(j,addr);
			}
*/
			
			customermasterobject_current.setAddress(hash_addr);
			Customer_id=custdeligate.storeCustomerDetail(customermasterobject_current);
			//seting every thing to null as the page was retaing in values
			custdetform.setName(""); 
			custdetform.setMidname("");
			custdetform.setLastname("");
		    custdetform.setSalutation("");
			//custdetform.setCategories("");
		    custdetform.setPvt_sector("");		
			custdetform.setFathername(""); 
			custdetform.setMothername("");
			custdetform.setMarital("");
			custdetform.setSpousename("");
            custdetform.setNation("");
			custdetform.setReligion("");
			custdetform.setSex("");
			custdetform.setCaste("");
			custdetform.setScst("");
		    custdetform.setDob("");
			custdetform.setIntro("");
			custdetform.setPassportno("");
			custdetform.setIssuedate("");
			custdetform.setExpirydate("");
			custdetform.setOccupation("");
			custdetform.setAddrproof("");
			custdetform.setNameproof("");
			custdetform.setPan("");
			custdetform.setTxt_gursex("");
			custdetform.setTxt_guaraddress("");
			custdetform.setTxt_guarcourtno("");
			custdetform.setTxt_gurtype("");
			custdetform.setCity("");
			custdetform.setPin("");
			custdetform.setPhnum1("");
			custdetform.setMailid("");
			custdetform.setMobile("");
			custdetform.setFaxno("");
			custdetform.setReligion("");
			System.out.println("Customer-ID==========="+Customer_id);
			return Customer_id;
     	}
     	
     	private void Insert_customization_values(Customizationform customizationform,CustomerDelegate custdeligate)throws RemoteException
     	{
     		String salutation=customizationform.getParam(); 
     		String sal2=salutation.substring(1);
			System.out.println("table name salutation value in customer action==="+sal2);
			String arr=customizationform.getAddp();
			String arr1="'"+arr+"'";
			System.out.println("text filed value in customer action==="+arr+""+arr1);
			
			int salu=custdeligate.StoreCustomParams(sal2,arr1);
			System.out.println("After inserting into database==="+salu);
     	}
     	private int deletefun(String tablename,Customizationform customizationform,String coloumnane,String param_value,CustomerDelegate custdeligate)throws RemoteException
     	{
     		int delete_status=0;
    		delete_status= custdeligate.removeData(tablename,coloumnane,param_value);
			return delete_status;
     	}
     	
     	private int Institute_values_Inserting(CustomerMasterObject customermasterobject_current,CustomerInstituteForm customerinstitute,Customerform customer_form,int Customer_id,CustomerDelegate custdeligate,HashMap map)throws RemoteException
     	{
     		System.out.println("i am inside insterting instute values");
     		String addr_type_obj[];
     		Address address_object=new Address();
    		String j;
    		Address addr=null;
    		HashMap hash_addr=new HashMap();
     		customermasterobject_current.setBranchCode("Head Office");
     		System.out.println("customerinstitute.getInstname()=====>"+customerinstitute.getInstname());
    		System.out.println("customermasterobject_current.setBranchCode=================="+customermasterobject_current.getBranchCode());
		    System.out.println("customer_form.getCusttype()===>"+customer_form.getCusttype());
    		/*if(customer_form.getCusttype().equalsIgnoreCase("1003"))
		    {
			System.out.println("inside if condition in 1003"); 
			customermasterobject_current.setCategory(1);
		    }*/
		    if(customer_form.getCusttype().equalsIgnoreCase("1"))
		    {
			System.out.println("inside if condition in 1003"); 
			customermasterobject_current.setCategory(1);
		    }
		    System.out.println("intro id is--->"+customerinstitute.getIntroid());
		
		    System.out.println("The Valsdfdfue of master obj is---->"+customermasterobject_current.getCategory());
		
		   // accsubcategoryobject=custdeligate.getAccSubCategories(num);
		    
			customermasterobject_current.setFirstName(customerinstitute.getInstname());
			customermasterobject_current.setOccupation(customerinstitute.getDesignation());
			customermasterobject_current.setIntroducerId(customerinstitute.getIntroid());
			customermasterobject_current.setMiddleName(customerinstitute.getContactpersonname());
			customermasterobject_current.setSalute(customerinstitute.getSalutation());
			customermasterobject_current.setPanno(customerinstitute.getPan());
			customermasterobject_current.setAddressProof(customerinstitute.getAddrproof());
			customermasterobject_current.setNameProof(customerinstitute.getNameproof());
			customermasterobject_current.setPanno(customerinstitute.getPan());
			customermasterobject_current.uv.setUserId(user);
			customermasterobject_current.uv.setUserTml(tml);
			String[] address_addtype={customerinstitute.getAddrestype()};
			addr_type_obj=address_addtype;

			if(map!=null){
				Set keyset=map.keySet();
				Iterator iter=keyset.iterator();
				String key;
				HashMap submap;
				while(iter.hasNext()){
					key=iter.next().toString();
					System.out.println("-->"+map.get(key));
					submap=(HashMap)map.get(key);
					if(!submap.get(key).toString().equals("")){
						addr=new Address();
						addr.setFlag((Integer.parseInt(key))); 
						addr.setAddress(submap.get(key).toString());
						addr.setCity(customerinstitute.getCity());
						addr.setCountry(customerinstitute.getCountry());
						addr.setState(customerinstitute.getState());
						addr.setPin(customerinstitute.getPin());
						addr.setMobile(customerinstitute.getMobile());
						addr.setPhStd(customerinstitute.getPhnum1());
						addr.setFax(customerinstitute.getFaxno());
						addr.setEmail(customerinstitute.getMailid());
						if(hash_addr.get(key)==null)
							hash_addr.put(key,addr);
     				}
				}
			}
				
			
			for(int i=0;i<addr_type_obj.length;i++)
			{
				j=addr_type_obj[i];
				addr=new Address();
				addr.setFlag((Integer.parseInt(j))); 
				addr.setAddress(customerinstitute.getAddressarea());
				addr.setCity(customerinstitute.getCity());
				addr.setCountry(customerinstitute.getCountry());
				addr.setState(customerinstitute.getState());
				addr.setPin(customerinstitute.getPin());
				addr.setMobile(customerinstitute.getMobile());
				addr.setPhStd(customerinstitute.getPhnum1());
				addr.setFax(customerinstitute.getFaxno());
				addr.setEmail(customerinstitute.getMailid());

			if(hash_addr.get(j)==null)
				hash_addr.put(j,addr);
		}

		addr.setChanged(true);
		customermasterobject_current.setAddress(hash_addr);
		int Id_Customer=custdeligate.storeCustomerDetail(customermasterobject_current);
		System.out.println("Customer-ID==========="+Id_Customer);
		customerinstitute.setAddressarea("");
		customerinstitute.setCity("");
		customerinstitute.setMobile("");
		customerinstitute.setIntroid("");
		customerinstitute.setDesignation("");
		customerinstitute.setMailid("");
		customerinstitute.setInstname("");
		customerinstitute.setFaxno("");
		customerinstitute.setPhnum("");
		customerinstitute.setPan("");
		customerinstitute.setPin("");
		customerinstitute.setIntroid2("");
		customerinstitute.setPhnum1("");
		customerinstitute.setAddrestype("Select");
		customerinstitute.setContactpersonname("");
		return Id_Customer;
     	}
     	
     	private void setErrorPageElements(String exception,HttpServletRequest req,String path)
     	{
            req.setAttribute("exception",exception);
            req.setAttribute("pageId",path);
        }
     	private String Calcul_Age(HttpServletRequest req,CustomerInformationForm custinfo_form,CustomerDelegate deligate)
     	{
     		String age_val=null;
     		System.out.println("The date of birth is ------------------------------------->"+custinfo_form.getDob());
     		if(custinfo_form.getDob().length()>0 && !(custinfo_form.getDob().equals("00/00/0000")))
     		{	
     			StringTokenizer str_token=new StringTokenizer(custinfo_form.getDob(),"/");
     			int int_day=Integer.valueOf(str_token.nextToken());
     			int int_month=Integer.valueOf(str_token.nextToken());
     			int int_year=Integer.valueOf(str_token.nextToken());
     			
     			StringTokenizer str_token2=new StringTokenizer(deligate.getSysDate(),"/");
     			int cal_day=Integer.valueOf(str_token2.nextToken());
     			int cal_month=Integer.valueOf(str_token2.nextToken());
     			int cal_year=Integer.valueOf(str_token2.nextToken());
     			
     			if((cal_year-int_year)==18)
     			{
     				if(cal_month==int_month)
                        if(cal_day<int_day)
                        {
                        	System.out.println("hiiiiiii  2      iiiiiiiiiiii");
                        	req.setAttribute("GuardianDetail","GuardianDetail");
                           // txt_gname.requestFocus();
                           // panel_guard.setVisible(true);
                           // txt_gname.requestFocus();
                        }
                    if(cal_month<int_month)
                    {
                    	System.out.println("hiiiiiiiiiiiiiiiiiii 1");
                    	req.setAttribute("GuardianDetail","GuardianDetail");
                        //txt_gname.requestFocus();
                        //panel_guard.setVisible(true);
                        
                    }else 	if((cal_year-int_year)==60)
         			{
         				if(cal_month==int_month)
                            if(cal_day<int_day)
                            {
                            	System.out.println("gjhsdgahd");
                            	//req.setAttribute("GuardianDetail","GuardianDetail");
                               // txt_gname.requestFocus();
                               // panel_guard.setVisible(true);
                               // txt_gname.requestFocus();
                            }
                        if(cal_month<int_month)
                        {
                        	System.out.println("55555555");
                        //	req.setAttribute("GuardianDetail","GuardianDetail");
                            //txt_gname.requestFocus();
                            //panel_guard.setVisible(true);
                        }
         			}
         			/*else
         			{
         				System.out.println("hiiiiiiiiiiiiiiiiiii 1 from else"); 
         				req.setAttribute("GuardianDetail",null);
         			}*/
         			if(custinfo_form.getForward().equalsIgnoreCase("Senior Citizen")==true){
         				System.out.println("777777777");
         			 if((cal_year-int_year)<18 && (cal_year-int_year)<60)
                    {
         				//req.setAttribute("GuardianDetail","GuardianDetail");
         				 req.setAttribute("msg", "Please enter the valid DOB!");
         				System.out.println("hiiiiiiiiiiiiiiiiiii");
                    }
         			
         			 }
         			
         			/*else if((cal_year-int_year)>18)
                    {
         				System.out.println("hiiiiiiiiiiiiiiiiiii from else"); 
         				req.setAttribute("GuardianDetail",null);
                    } */
         			
         			 if(custinfo_form.getForward().equalsIgnoreCase("Senior Citizen")){
         			 if((cal_year-int_year)>=60 && (cal_year-int_year)>18)
                    {
         				System.out.println("2222222222"); 
         				//req.setAttribute("GuardianDetail",null);
                    } else{
                    	req.setAttribute("msg","Please enter the Correct DOB!");
                    }
         			
         				 System.out.println("hi am checking for senior citizen");
         			 }
         			 
         			
                    
     				
     			}
     			else
     			{
     				System.out.println("hiiiiiiiiiiiiiiiiiii 1 from else"); 
     				req.setAttribute("GuardianDetail",null);
     			}
     			 if((cal_year-int_year)<18)
                {
     				req.setAttribute("GuardianDetail","GuardianDetail");
     				System.out.println("hiiiiiiiiiiiiiiiiiii");
                }
     			
     			else if((cal_year-int_year)>18)
                {
     				System.out.println("hiiiiiiiiiiiiiiiiiii from else"); 
     				req.setAttribute("GuardianDetail",null);
                } 
     			 
     			 
     			Calendar cal=Calendar.getInstance(); 
     			age_val=String.valueOf(cal.get(Calendar.YEAR)-int_year);
     			
     			
     		}	
     		
     		return age_val;
     	}
     	
     	
       	
     	
}

