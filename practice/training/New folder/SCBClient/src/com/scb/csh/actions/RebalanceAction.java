package com.scb.csh.actions;

import exceptions.RecordsNotFoundException;
import general.Validations;

import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import masterObject.cashier.CashObject;
import masterObject.cashier.CurrencyStockObject;
import masterObject.cashier.TerminalObject;
import masterObject.general.AccSubCategoryObject;
import masterObject.general.ModuleObject;
import masterObject.share.ShareCategoryObject;
import masterObject.share.ShareMasterObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.common.help.ResultHelp;
import com.scb.common.log4j.LogDetails;
import com.scb.csh.forms.ConsolidateScrollform;
import com.scb.csh.forms.RebalanceForm;
import com.scb.csh.forms.Recieiptsform;
import com.scb.designPatterns.CashDelegate;
import com.scb.props.MenuNameReader;

public class RebalanceAction extends Action{
	private String path;
	int share_cat = 0;
	int  accnoexist =0;
	int po_descrptn = 0;
	 masterObject.general.AccountObject accountobject=null;
	 AccSubCategoryObject accsubcategoryobject[]=null,accsubcategoryobject1[]=null;;
	 
	final Logger logger=LogDetails.getInstance().getLoggerObject("CashDelegate");	
public ActionForward execute(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse res)throws Exception

{

	if(map.getPath().equalsIgnoreCase("/Rebalance"))
	{
		
		RebalanceForm rbform =(RebalanceForm)form;
		System.out.println("**********inside Rebalancing***********");
		System.out.println("current date in Rebalnce page"+rbform.getDate());
		request.setAttribute("pageId", MenuNameReader.getScreenProperties("2010"));
		System.out.println(request.getAttribute("pageId"));
		CashDelegate csdelegate = new CashDelegate();
		int value=csdelegate.rebalancingScroll(rbform.getDate(),"CA99");
		System.out.println("value from deligate in action class===========>"+value); 
		if(value == 1)
			request.setAttribute("value","SUCCESSFUL");
		else if(value == 0)
			request.setAttribute("value", "NO ENTRY IN CURRENCY_STOCK");
		else if(value == 2)
			request.setAttribute("value", "No Transaction");
		
		return map.findForward(ResultHelp.getSuccess());
	}
	

	else if(map.getPath().equalsIgnoreCase("/RebalancingMenu"))
		{
     try{
    	 System.out.println("*********RebalancingMenu*************");
    	 
    	 RebalanceForm rbform =(RebalanceForm)form;     
    	 CashDelegate rbdelegate = new CashDelegate();
    	 System.out.println("pageid==================>"+rbform.getPageId()); 
    	 System.out.println("menu==========="+MenuNameReader.containsKeyScreen(rbform.getPageId()));
         
         String date1 = CashDelegate.getSysDate(); 
         request.setAttribute("date",date1) ;
         System.out.println("present Date"+date1);
         if(MenuNameReader.containsKeyScreen(rbform.getPageId()))
			{
				path=MenuNameReader.getScreenProperties(rbform.getPageId());
				System.out.println("path in action class=======>"+path); 
				request.setAttribute("pageId",path);
				setRebalanceInitParams(request,path,rbdelegate);
				
				return map.findForward(ResultHelp.getSuccess());
   
			}
         else
         {
        	 return map.findForward(ResultHelp.getError());
         }
       }catch (Exception e) 
       {		
    	       e.printStackTrace(); 
        	   return map.findForward(ResultHelp.getSuccess());
       }
          
        
	}
	
	else if(map.getPath().equalsIgnoreCase("/CurrencyStockMenu"))
	{
		try{
	    	 System.out.println("*********CurrencystockMenu*************");
	    	 
	    	 RebalanceForm rbform =(RebalanceForm)form;
	    	 CashDelegate curdelegate =new CashDelegate();
	    	 
	    	System.out.println("pageid==================>"+rbform.getPageId()); 
	        
	    	String date = CashDelegate.getSysDate();
	    	double tml_runable =curdelegate.getCashTmlRunningBalance("CA99",date); 
			request.setAttribute("tml_runable",tml_runable);
			System.out.println("terminal balance=========>"+tml_runable);
			
			CashDelegate curobjectDelegate=new CashDelegate();
			CurrencyStockObject currencystockobject = curobjectDelegate.getCurrencyStockObject("CA99",date, 1);
			
			request.setAttribute("curobject1", currencystockobject.gets1000());
			System.out.println("curobject1"+currencystockobject.gets1000());
			request.setAttribute("curobject2", currencystockobject.gets500());
			request.setAttribute("curobject3", currencystockobject.gets100());
			request.setAttribute("curobject4", currencystockobject.gets50());
			request.setAttribute("curobject5", currencystockobject.gets20());
			request.setAttribute("curobject6", currencystockobject.gets10());
			request.setAttribute("curobject7", currencystockobject.gets5());
			request.setAttribute("curobject8", currencystockobject.gets2());
			request.setAttribute("curobject9", currencystockobject.gets1());
			request.setAttribute("curobject10", currencystockobject.getscoins());
	        
	         if(MenuNameReader.containsKeyScreen(rbform.getPageId()))
				{
	        	 
					path=MenuNameReader.getScreenProperties(rbform.getPageId());
					System.out.println("path in action class=======>"+path); 
					request.setAttribute("pageId",path);
					setRebalanceInitParams(request,path,curdelegate);
					
					return map.findForward(ResultHelp.getSuccess());
	   
				}
	         else
	         {
	        	 return map.findForward(ResultHelp.getError());
	         }
	       }catch (Exception e) 
	       {		
	    	       e.printStackTrace(); 
	        	   return map.findForward(ResultHelp.getSuccess());
	       }
	          
		
	}
	else if(map.getPath().equalsIgnoreCase("/Currency"))
	{
		RebalanceForm rbform =(RebalanceForm)form;
		CashDelegate curfDelegate=new CashDelegate();
       String date = CashDelegate.getSysDate();
		double tml_runable =curfDelegate.getCashTmlRunningBalance("CA99",date); 
		request.setAttribute("tml_runable",tml_runable);
		System.out.println("***************Currency Stock******************");
		String button_value = rbform.getBut_value();
		System.out.println("button_value==============>"+button_value);
		CashDelegate csdelegate = new CashDelegate();
		String date1 = csdelegate.getSysDate();
		request.setAttribute("pageId", MenuNameReader.getScreenProperties("2010"));
		int value=csdelegate.checkTmlOpenClose("CA99",date1);
		System.out.println("terminal open close"+value);	
		if(value==1)
		setRebalanceInitParams(request,path,curfDelegate); 
		if(button_value.equalsIgnoreCase("UPDATE"))
		{   
			System.out.println("**********inside update*********"); 
			request.setAttribute("button_value", "UPDATE");
			
		}
		if(button_value.equalsIgnoreCase("CLEAR")){
			System.out.println("**********inside clear*********");
			request.setAttribute("button_value", "CLEAR");
		}
	
		else if(value==0)
			request.setAttribute("value","Error : Please check DB entries");
		else if(value==2)
			request.setAttribute("value","U can't update the currency Stock as Terminal is Closed");
		else if(value==-1)
			request.setAttribute("value","Terminal doesn't exists");
		CashDelegate curobjectDelegate=new CashDelegate();
		String date2 = curobjectDelegate.getSysDate();
		CurrencyStockObject currencystockobject = curobjectDelegate.getCurrencyStockObject("CA99",date2, 1);
		
		request.setAttribute("curobject1", currencystockobject.gets1000());
		System.out.println("curobject1"+currencystockobject.gets1000());
		request.setAttribute("curobject2", currencystockobject.gets500());
		request.setAttribute("curobject3", currencystockobject.gets100());
		request.setAttribute("curobject4", currencystockobject.gets50());
		request.setAttribute("curobject5", currencystockobject.gets20());
		request.setAttribute("curobject6", currencystockobject.gets10());
		request.setAttribute("curobject7", currencystockobject.gets5());
		request.setAttribute("curobject8", currencystockobject.gets2());
		request.setAttribute("curobject9", currencystockobject.gets1());
		request.setAttribute("curobject10", currencystockobject.getscoins());
		
		
		
	}
	else if(map.getPath().equalsIgnoreCase("/ConsolidatedMenu")){
		try{
	    	 System.out.println("*********ConsolidatedMenu*************");
	    	 CashDelegate srolldelegate = new CashDelegate();
	    	 ConsolidateScrollform srollform =(ConsolidateScrollform)form; 
	    	 
	    	 System.out.println("pageid=========>"+srollform.getPageId()); 
	    	 try{
	    	 TerminalObject[] terminalobject_view=srolldelegate.getTerminalObject();
	    	 request.setAttribute("combo_terminal", terminalobject_view);
	    	 }catch (Exception e) {
				e.printStackTrace();
			}
	    	 String date1 = srolldelegate.getSysDate(); 
	         request.setAttribute("date",date1) ;
	             	  
	    	 	    	   	 
	    	 if(MenuNameReader.containsKeyScreen(srollform.getPageId()))
			{
        	 
				path=MenuNameReader.getScreenProperties(srollform.getPageId());
				System.out.println("path in action class=======>"+path); 
				request.setAttribute("pageId",path);
				setScrollInitParams(request,path,srolldelegate);
				
				return map.findForward(ResultHelp.getSuccess());
   
			}
         else
         {
        	 return map.findForward(ResultHelp.getError());
         }
       }catch (Exception e) 
       {		
    	       e.printStackTrace(); 
        	   return map.findForward(ResultHelp.getSuccess());
       }
	}
	
	else if(map.getPath().equalsIgnoreCase("/Scroll")){
		CashDelegate scrolldelegate = new CashDelegate();
		
		ConsolidateScrollform scrollform =(ConsolidateScrollform)form; 
		try{
		TerminalObject[] terminalobject_view=scrolldelegate.getTerminalObject();
		
		request.setAttribute("combo_terminal", terminalobject_view);
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("**********inside Scroll Report***********");
		
		String terminalCode =scrollform.getTerminal();
		
		String string_from_date=scrollform.getDate();
		
		String string_present_date = scrolldelegate.getSysDate();
		
		CurrencyStockObject cso = null;
		
		System.out.println("String string_from_date"+string_from_date);
		
		System.out.println("string_present_date"+string_present_date);
		int value=Validations.dayCompare(string_from_date,string_present_date);
		System.out.println("daycompare"+value);
		
		String terminalobj =scrollform.getReport();
		request.setAttribute("report", terminalobj);
		System.out.println("report object****************"+terminalobj);
		setScrollInitParams(request, path, scrolldelegate);
		if(value==-1){
			request.setAttribute("value","The date entered is not Valid");
			request.setAttribute("pageId", MenuNameReader.getScreenProperties("2010"));
		}
		
		else if(value!=-1){
				
				setScrollInitParams(request,path,scrolldelegate); 
				
				String getSelectedTerminal = scrollform.getTerminal();	
				System.out.println("selected Terminal+++++++"+getSelectedTerminal);
				
				int flag=0;
				String query="";
				System.out.println("button value++++++++++++++++++++++++"+scrollform.getBut_value());
				//**********************select Terminal******************
				CashObject[] cashobject_withdenom;
				CashDelegate getDayDelegate =new CashDelegate();
				cashobject_withdenom = getDayDelegate.getDayCashData(string_from_date,getSelectedTerminal , flag, query);
				request.setAttribute("cashobject_withdenom",cashobject_withdenom );
				
				CashObject cashobject =null;
				CashDelegate getsummuryDelegate = new CashDelegate();
				cashobject =getsummuryDelegate.getDayCashSummary(string_from_date,scrollform.getTerminal());
				request.setAttribute("CashObject",cashobject); 
				
				//**********************all Terminal******************
				CashObject[] cashobject_withdenomall;
				CashDelegate AllDelegate =new CashDelegate();
				cashobject_withdenomall=AllDelegate.getDayCashData(string_from_date,null,flag,query); 
				
				
				CashObject cashobjectAll =null;
				CashDelegate getAllsumDelegate = new CashDelegate();
				cashobjectAll =getAllsumDelegate.getDayCashSummary(string_from_date,null);
				 
				
				String button_value = scrollform.getValue();
				System.out.println("<====><=========>"+button_value); 
				
						if(button_value.equalsIgnoreCase("true")){
							if(scrollform.getReport().equalsIgnoreCase("Selected Terminals")){
							setScrollInitParams(request, path, scrolldelegate);
							request.setAttribute("denomination","denomination");
							request.setAttribute("cashobject_withdenom",cashobject_withdenom);
							request.setAttribute("CashObject",cashobject);
						}else if(scrollform.getReport().equalsIgnoreCase("All Terminals")){
							request.setAttribute("denomination","denomination");
							request.setAttribute("cashobject_withdenom", cashobject_withdenomall);
							request.setAttribute("CashObject", cashobjectAll);
							
							}
				}
						else 
							if(button_value.equalsIgnoreCase("false")){
							if(scrollform.getReport().equalsIgnoreCase("Selected Terminals")){
								request.setAttribute("denomination","woutdenomination");
								request.setAttribute("cashobject_withdenom", cashobject_withdenomall);
								request.setAttribute("CashObject", cashobjectAll);
							
							}
					
							else 
								if(scrollform.getReport().equalsIgnoreCase("All Terminals")){
									request.setAttribute("denomination","woutdenomination");
									request.setAttribute("cashobject_withdenom",cashobject_withdenom);
									request.setAttribute("CashObject",cashobject);
									
						
								}
					}
				/*if(button_value.equalsIgnoreCase("VIEW"))
				{   
					
					System.out.println("**********inside VIEW*********"); 
					if(terminalobj.equalsIgnoreCase("Selected Terminals")){//select terminal with denomination
					System.out.println("select terminal***************");
					
					request.setAttribute("cashobject_withdenom",cashobject_withdenom);
					request.setAttribute("button_value", "VIEW");
					}
				else if(terminalobj.equalsIgnoreCase("All Terminals")){//all terminal with denomination
					System.out.println("all terminal%%%%%%%%%%%%%");
					
					request.setAttribute("cashobject_withdenom",cashobject_withdenom);
					System.out.println("all terminal===================>"+request.getAttribute("cashobject_withdenom"));	
					request.setAttribute("button_value", "VIEW");
				}
					if(cashobject_withdenom!=null && cashobject_withdenom.length==0)
						request.setAttribute("cashobject_withdenom","The date entered is not Valid");
	                
	                else
	               
	                    if(terminalobj.equalsIgnoreCase("All Terminals")){//all tml
	                    	cashobject= getsummuryDelegate.getDayCashSummary(string_from_date,null);
	                    	System.out.println("cashobject for all terminal"+cashobject);
	                    	request.setAttribute("CashObject",cashobject);
	                    	request.setAttribute("button_value", "VIEW");
	                    }
	                
	                    	
	                else if(terminalobj.equalsIgnoreCase("Selected Terminals"))//selected tml
	                    {
	                       
	                    	cashobject =getsummuryDelegate.getDayCashSummary(string_from_date,scrollform.getTerminal());
	                        cso = getsummuryDelegate.getCurrencyStockObject(scrollform.getTerminal(),scrolldelegate.getSysDate(),0);
	                        System.out.println("cashobject for all terminal"+cashobject);
	                        request.setAttribute("CashObject", cashobject);
	                        request.setAttribute("button_value", "VIEW");
	                    }
	                */
				 
				//}
				
			
				
				
			}
		


		return map.findForward(ResultHelp.getSuccess());
	}
	else if(map.getPath().equalsIgnoreCase("/ReceiptMenu")){
		  try{
		    	 System.out.println("*********ReceiptMenu*************");
		    	 
		    	 CashDelegate rcdelegate =new CashDelegate();     
		    	 Recieiptsform rcform=(Recieiptsform)form;
		    	 System.out.println("pageid==================>"+rcform.getPageId()); 
		    	 System.out.println("menu==========="+MenuNameReader.containsKeyScreen(rcform.getPageId()));
		    	 //rcform.setMiniamt("null");
		    	 rcform.setMiniamt(0.0);
		    	 rcform.setAccexist("null");
		    	 ModuleObject[] module_obj_array = null;
		 		module_obj_array  = rcdelegate.getMainModules(2, "1001000,1002000,1003000,1004000,1005000,1007000,1008000,1009000,1010000,1013000,1014000,1015000,1016000,1017000,1018000");
		 		request.setAttribute("MainModules", module_obj_array);
		        System.out.println("main modules"+module_obj_array); 
		        ModuleObject[] module_obj_array1 = null;
		        module_obj_array1 = rcdelegate.getMainModules(2,"1001000,1002000,1003000,1004000,1005000,1006000,1007000,1008000,1009000,1010000,1013000,1014000,1015000,1017000,1018000");
		        request.setAttribute("MainModules1", module_obj_array1);
		        System.out.println("main modules 111"+module_obj_array1);
		       
		       
		       /*String string_code = null;
		        
		       String module =module_obj_array[Integer.valueOf(rcform.getAccounttype())].getModuleAbbrv();
		       if(module!=null)
		       	
		       {
		       
		    	for(int i=0;i<module_obj_array.length;i++)
		    	  {
		    		   String mod=module_obj_array[i].getModuleAbbrv();
		       	
		    	if(mod.equalsIgnoreCase(module))
		       {
		       	String string_desc=module_obj_array[i].getModuleDesc();
		       	request.setAttribute("module",string_desc);
		       	 string_code =String.valueOf(module_obj_array[i].getModuleCode());
		       	System.out.println("modulecode..........."+string_code);
		       }
		       }
		       } */
		        /* 
		        System.out.println("module----code"+string_code);
		        
		        String custtype= rcform.getCusttype();
		        request.setAttribute("custtype", custtype);
		        
		        if(custtype.equalsIgnoreCase("Customer")){ 
		        CashDelegate pomDelegate = new CashDelegate();//PODescription
		        AccSubCategoryObject[] acc =null;
		        acc =pomDelegate.getAccSubCategories(0);
		        request.setAttribute("podescription", acc);
		        
		        CashDelegate pom1Delegate = new CashDelegate();
		        AccSubCategoryObject[] acc1 =null;
		        acc1=pom1Delegate.getAccSubCategories(1);
		        request.setAttribute("podescription1", acc1);
		  }
		        else if(custtype.equalsIgnoreCase("Non Customer")){
      		
      		CashDelegate po3Delegate = new CashDelegate();//poDescription1
              AccSubCategoryObject[] acc2 =null;
              acc2=po3Delegate.getAccSubCategories(2);
              request.setAttribute("podescription2", acc2);	
      	}
		        
		       */
		        CashDelegate lkDelegate = new CashDelegate();
		        String[] locktype = lkDelegate.getLockersDesc();
		        request.setAttribute("locktype", locktype);
		      /* ShareCategoryObject[] sharecategoryobject=null; //share
		        sharecategoryobject = rcdelegate.getShareCategories(0,string_code);
		        request.setAttribute("sharecategory", sharecategoryobject);	*/
		        if(MenuNameReader.containsKeyScreen(rcform.getPageId()))
					{
						path=MenuNameReader.getScreenProperties(rcform.getPageId());
						System.out.println("path in action class=======>"+path); 
						request.setAttribute("pageId",path);
						setReceiptparamform(request,path,rcdelegate);
						
						return map.findForward(ResultHelp.getSuccess());
		   
					}
		         else
		         {
		        	 return map.findForward(ResultHelp.getError());
		         }
		       }catch (Exception e) 
		       {		
		    	       e.printStackTrace(); 
		        	   return map.findForward(ResultHelp.getSuccess());
		       }	
	}
	else if(map.getPath().equalsIgnoreCase("/Receipt"))
	{
		System.out.println("*******************Receipt********************");
		
		CashDelegate recDelegate = new CashDelegate();
		Recieiptsform recform =(Recieiptsform)form;
		//recform.setMiniamt("null");
		recform.setMiniamt(0);
		recform.setAccexist("null");
		ModuleObject[] module_obj_array = null;//general
		
		module_obj_array  = recDelegate.getMainModules(2, "1001000,1002000,1003000,1004000,1005000,1007000,1008000,1009000,1010000,1013000,1014000,1015000,1016000,1017000,1018000");
		request.setAttribute("MainModules", module_obj_array);
		System.out.println("module_object_array"+module_obj_array);//main module general
		
		ModuleObject[] module_obj_array1 = null;//PO
        module_obj_array1 = recDelegate.getMainModules(2,"1001000,1002000,1003000,1004000,1005000,1006000,1007000,1008000,1009000,1010000,1013000,1014000,1015000,1017000,1018000");
        request.setAttribute("MainModules1", module_obj_array1);//po-main module
       
        //***************module code*******************
        
        
        String string_code = null;
        
       String module =module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleAbbrv();
      
       System.out.println("general combobox"+module);
       request.setAttribute("getAbbrv", module);
       if(module!=null)
       	
       {
       
    	for(int i=0;i<module_obj_array.length;i++)
    	  {
    		   String mod=module_obj_array[i].getModuleAbbrv();
       	
    	if(mod.equalsIgnoreCase(module))
       {
       	String string_desc=module_obj_array[i].getModuleDesc();
       	request.setAttribute("module",string_desc);
       	 string_code =String.valueOf(module_obj_array[i].getModuleCode());
       	System.out.println("modulecode..........."+string_code);
       }
       }
       } 
        	 
       
        //*********************po - module code**************
        
        
       
        String pomodule =module_obj_array1[Integer.valueOf(recform.getCustacctype())].getModuleAbbrv();//PO
        
        System.out.println("module abbrivation$$$$$$$$$$=============>"+pomodule);
        String postring_code =null;
        if(pomodule!=null)
        	
        {
        	
      
        for(int i=0;i<module_obj_array1.length;i++)
        {
        	String pomod=module_obj_array1[i].getModuleAbbrv();
        	
        if(pomod.equalsIgnoreCase(pomodule))
        {
        	String postring_abbrv=module_obj_array1[i].getModuleDesc();
        	
        	request.setAttribute("module1",postring_abbrv);
        	 postring_code =String.valueOf(module_obj_array1[i].getModuleCode());
        	System.out.println("modulecode..........."+postring_code);
        }
        }
        } 
        //*******************customer type***************
        String custtype= recform.getCusttype();
        if(custtype!=null)
        {
        request.setAttribute("custtype", custtype);
        System.out.println("cust type..........."+custtype);
        }
        try{ 
        if(custtype.equalsIgnoreCase("Customer"))
        {
        CashDelegate poDelegate = new CashDelegate();//PODescription-customer
        AccSubCategoryObject[] acc =null;
        acc =poDelegate.getAccSubCategories(0);
    
        
        CashDelegate po1Delegate = new CashDelegate();
        AccSubCategoryObject[] acc1 =null;
        acc1=po1Delegate.getAccSubCategories(1);
          if(acc!=null && acc1!=null )
          {
			AccSubCategoryObject[] accsubcategoryobject=new AccSubCategoryObject[acc.length+acc1.length];
			for(int i=0;i<acc.length;i++){
            accsubcategoryobject[i]=acc[i];
			}
			int j=0;
        	for(int i=acc.length;i<(acc1.length+acc.length);i++){
            accsubcategoryobject[i]=acc1[j++];
            request.setAttribute("podescription",accsubcategoryobject);
            
        	}
          }
     
        	} 
        else if(custtype.equalsIgnoreCase("Non Customer"))
        {
        		
        		CashDelegate po3Delegate = new CashDelegate();//poDescription1-noncustomer
                AccSubCategoryObject[] acc2 =null;
                acc2=po3Delegate.getAccSubCategories(2);
                request.setAttribute("podescription2", acc2);	
        	}
       }catch(RemoteException e){
    	   e.printStackTrace();
       }
       	catch (SQLException se) {
       		se.printStackTrace();
			
		}
        CashDelegate lkDelegate = new CashDelegate();//Locker type
        String[] Lockdesc = lkDelegate.getLockersDesc();
        request.setAttribute("locktype", Lockdesc);
        
        
        ShareCategoryObject[] sharecategoryobject; //share category
        sharecategoryobject = recDelegate.getShareCategories(0,string_code);
        request.setAttribute("sharecategory", sharecategoryobject);
        
        
        setReceiptparamform(request,path,recDelegate);
        //start
    	
        int txtaccno = recform.getAccno();
        System.out.println("account no====="+txtaccno);
        //start
		 
        	if(recform.getAccno()==0){
        			request.setAttribute("newacc","New Account");
        	}
        	else
        	if(recform.getAccno()!=0)
        	{
        		System.out.println("lockercode(((((("+string_code);
        		if(string_code.startsWith("1009"))//LK
        		{
		        try 
                	{
                     	String string_locker_type=recDelegate.getLockerType(module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode(),recform.getAccno());
                     	System.out.println("locker type from delegate"+string_locker_type);
                     	String[]lock_types = recDelegate.getLockersTypes(); 
                    
                     for(int i=0;i<lock_types.length;i++){
                    	 System.out.println("locker types before for loop==="+lock_types[i]);
                     }
                     if(string_locker_type==null)
                     {
                    	 accnoexist = 1;
                     	 System.out.println("Invalid Account No");
                     	
                     }
                     else
                     {
                         for(int i=0;i<lock_types.length;i++)
                         {
                             if(lock_types[i].equals(string_locker_type)){
                                 
                            	String locdesc = Lockdesc[i];
                                request.setAttribute("lockerdescpn", locdesc); 
                                request.setAttribute("lockerindex",String.valueOf(i));
                             }
                             
                         }
                     }
                     
                     String date = recDelegate.getSysDate();
                     double lk_rent = 0.0;  
                     double double_locker_rent=recDelegate.getLockerRent(module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode(),recform.getAccno(),date);
                     System.out.println("...total rent in client...."+double_locker_rent);
                       
                     if(double_locker_rent==0.0)
                     {
                    	 accnoexist = 1;
                    	 System.out.println("Locker Rent not defined");
                     }
                     else
                     {
                         //txt_amount.setText(String.valueOf(double_locker_rent));
                         lk_rent = double_locker_rent;
                         System.out.println("locker rent for particular type"+lk_rent);
                     }       
                } 
			    catch (NumberFormatException e1) 
				{
                     e1.printStackTrace();
                }
		        catch (RemoteException e1) 
				{
                     e1.printStackTrace();
                }
		        catch (SQLException e1) 
				{
                     e1.printStackTrace();
                }
            }
		
		else if(string_code.startsWith("1001"))//SH
        {
	        try 
            {
	        	 
	        		recform.setAccexist("null");
	        	 System.out.println("share type..."+string_code);
	        	 System.out.println("share no..."+recform.getAccno());
	        	 ShareMasterObject sharemasterobject;
                 sharemasterobject=recDelegate.getShare(string_code,recform.getAccno());
                
                 if(sharemasterobject!=null)
                     share_cat = sharemasterobject.getShareType();   
                 
                 if(share_cat==0)
                 { 
                 	 accnoexist = 1;
                 	 
                     request.setAttribute("share_cat","Invalid account no");
                     request.setAttribute("pageId", MenuNameReader.getScreenProperties("2010"));
                 }
                 else
                 {
                     for(int i=0;i<sharecategoryobject.length;i++)
					 {
						if(sharecategoryobject[i].getShCat()==share_cat){
						    
							String sharecategory =sharecategoryobject[i].getCatName();
							System.out.println("[==============>"+sharecategory);
							//recform.setSharecat(sharecategoryobject[i].getCatName());
							request.setAttribute("sharecategoryname",sharecategory);
							request.setAttribute("shareindex",String.valueOf(i));
							
					 }
					 }
                     
                    
                 }
            } 
		    catch (NumberFormatException e1) 
			{
                 e1.printStackTrace();
            }
	        catch (RemoteException e1) 
			{
                 e1.printStackTrace();
            }
        }
		//for general account number
		String date = recDelegate.getSysDate();
	    System.out.println("accnoexist == "+accnoexist);
	    if(accnoexist==0)
        {
	    	
	    	try
			{	
        		
        		accountobject=recDelegate.getAccount(null,string_code,recform.getAccno(),date);
				
				if(accountobject!=null)
				{
					 recform.setAccexist("null");
					if(accountobject.getAccStatus().equals("C"))
					{
                        recform.setAccountobject("accountobject");
                        System.out.println("Given account is Closed");
					}
					else if(accountobject.getAccStatus().equals("I"))
					{
						recform.setAccountobject("accountobject1");
                        System.out.println("InOperative Account");
					}
					else if(accountobject.getVerified()==null)
					{
                        //combo_account_type.requestFocus();
                        System.out.println("Given account is not yet Verified");
					}
					else if((accountobject.getDefaultInd()!=null)?accountobject.getDefaultInd().equals("T"):false)
					{  
                        //combo_account_type.requestFocus();
                        System.out.println("Default account");
					}
					else if((accountobject.getFreezeInd()!=null)?accountobject.getFreezeInd().equals("T"):false)
					{
                        //combo_account_type.requestFocus();
                        System.out.println("Freezed account");
					}
					else
					{
						
						System.out.println("name of the account holder"+accountobject.getAccname());
						recform.setAccname(accountobject.getAccname());
						//txt_name.select(0,0);//ship......11/06/2006
						//txt_name.setToolTipText(accountobject.getAccname().toUpperCase());
								
						//txt_name.setEditable(false);
						
						//ship.....24/08/2006
						System.out.println("Customer ID = "+accountobject.getCid());
						//obj_personal.showCustomer(accountobject.getCid());
						///////////
						//txt_amount.requestFocus();
						
						//tabbedPane.setEnabled(true);//ship.....24/08/2006
					}
				}
				else
				{
				   
				    recform.setAccexist("accexist");
					System.out.println("Given account number not found.....1");
					
				    }
                  	
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
        }//end of accnoexist==0
	       else
           {
               accnoexist = 0;
           }
           
		
		
		System.out.println("hi hello");
       
        
	}//end of txt_account_no
	 
     double amount = recform.getAmount();
     System.out.println("amount from JSP"+amount);
	 if(recform.getAmount()!=0)
		{
         
         System.out.println("focus lost from txt amount");
		   		System.out.println("code inside amount########"+string_code);
					if(string_code.startsWith("1001"))//SH
					{
						int minshares = 0;
						double amt = 0.00,minamt = 0.00,shareamt = 0.00;
						
						try
						{
							System.out.println("share category"+recform.getSharecat());
							minshares = sharecategoryobject[Integer.valueOf(recform.getSharecat())].getMinShare();
							
							shareamt = sharecategoryobject[Integer.valueOf(recform.getSharecat())].getShareVal();
							minamt = minshares * shareamt;
							System.out.println("minimum amount   ===="+minamt);
							amt = (amount);
								
							if(amt>=minamt)
							{
								//recform.setMiniamt("null");
								recform.setMiniamt(0);
									for(int i=0;i<sharecategoryobject.length;i++)
									{
										if(sharecategoryobject[i].getCatName().equals(recform.getSharecat().toString()))
										share_cat=sharecategoryobject[i].getShCat();
										
										
									}
								
								
								}
							
							else
							{
								//txt_amount.setText("0.00");
								//txt_amount.requestFocus();
								
								//recform.setMiniamt("MinimumAmount");
								recform.setMiniamt(0);
								System.out.println("Min Share Amount for Category < "+recform.getSharecat()+" >\n should be Rs. "+minamt+"");
							}
						}
						catch(Exception e1)
						{
							e1.printStackTrace();
						}
					
					}
				
	
	 else if(string_code.startsWith("1016"))//PO
		{
		    double comm_amt = 0.00;
		    
		    System.out.println("PayOrder ...ac_no==0....");
		    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1111===========>"+recform.getCustacno());
		   
		  //po-amount*********************comm_amt,tot_amt
		   //if((recform.getPoamount())!=null)
		    if((recform.getPoamount())!=0)
		   {
		    //double amt=Double.parseDouble(recform.getPoamount());
		    	double amt=recform.getPoamount();
			System.out.println("amount from jsp page"+amt);
			try
			{				
				System.out.println("customer Account type####"+recform.getCustacctype());
				if(amt>module_obj_array[Integer.valueOf(recform.getCustacctype())].getMaxAmount())
				{
                // txt_amount.requestFocus();  
					System.out.println("PayOrder Amount cannot be more than "+module_obj_array1[Integer.valueOf(recform.getCustacctype())].getMaxAmount());
					//recform.setPoamount("0.00");
					
					
				}
				else if(amt<module_obj_array[Integer.valueOf(recform.getCustacctype())].getMinBal())
				{
                 //txt_amount.requestFocus();
					System.out.println("PayOrder Amount cannot be less than "+module_obj_array[Integer.valueOf(recform.getCustacctype())].getMinBal());
					//recform.setPoamount("0.00");
				}
				else
				{
				    
				    if(recform.getCusttype().equalsIgnoreCase("Customer"))
				    {
				    	try
				        {
				            System.out.println("inside customer");
				        	accsubcategoryobject1=recDelegate.getAccSubCategories(0);
				            
				            System.out.println("PO111111");
				            
				            for(int i=0;i<accsubcategoryobject1.length;i++)
				            {
				                if((recform.getDescription()).equals(accsubcategoryobject1[i].getSubCategoryDesc()))
				                    po_descrptn = accsubcategoryobject1[i].getSubCategoryCode();
				            }
				            System.out.println("PO22222");    
				            AccSubCategoryObject[] acc1=recDelegate.getAccSubCategories(1);
				            for(int i=0;i<acc1.length;i++)
				            {
				                if((recform.getDescription()).equals(acc1[i].getSubCategoryDesc()))
				                    po_descrptn = acc1[i].getSubCategoryCode();
				            }
				        }
				        catch(Exception e1)
						{
							System.out.println("hi.....");
							System.out.println("PO Exception :" +e1);//Joption
						}
				        
				        System.out.println("po_desc Customer.....= "+po_descrptn);
				        
				        try
				        {
				            String date=recDelegate.getSysDate();
				        	//comm_amt = recDelegate.getCommission(string_code,po_descrptn,Double.parseDouble(recform.getPoamount()),date);
				            comm_amt = recDelegate.getCommission(string_code,po_descrptn,recform.getPoamount(),date);
				        }
				        catch(Exception e1)
						{
							System.out.println("hello.....");
							System.out.println("PO Exception11 :" +e1);//JOptionPane
						}
				    }
				    else if(recform.getCusttype().equalsIgnoreCase("Non Customer"))
				    {
				        try
			            {
				           
				           System.out.println("inside noncustomer");
				            accsubcategoryobject1 =recDelegate.getAccSubCategories(2);
				            
				            System.out.println("PO111111");
				            
				            for(int i=0;i<accsubcategoryobject1.length;i++)
				            {
				                if((recform.getDescription()).equals(accsubcategoryobject1[i].getSubCategoryDesc()))
				                    po_descrptn = accsubcategoryobject1[i].getSubCategoryCode();
				            }
				            ///////////////
			            }
			            catch(Exception exception)
					    {
					        exception.printStackTrace();
					    }
			            
			            System.out.println("po_desc Non-Customer.....= "+po_descrptn);
				        
				        try
				        {
				            String date = recDelegate.getSysDate();
//				        	comm_amt = recDelegate.getCommission(string_code,po_descrptn,Double.parseDouble(recform.getPoamount()),date);
				            comm_amt = recDelegate.getCommission(string_code,po_descrptn,recform.getPoamount(),date);
				            System.out.println("comm_amt......= "+comm_amt);
						    //recform.setCommAcc(String.valueOf(comm_amt));
				            recform.setCommAcc(comm_amt);
						    //recform.setTotal(String.valueOf(Double.parseDouble(recform.getPoamount())+comm_amt));
				            recform.setTotal(String.valueOf(recform.getPoamount()+comm_amt));
				        }
				        catch(Exception e1)
						{
							System.out.println("hello.hi....");
							System.out.println("PO Exception22 :" +e1);
						}
				    }
				    
				
				    
				}
			}
			catch(Exception exception_pay_amount)
			{
				exception_pay_amount.printStackTrace();
			}
		}
		   
		
		}//end of po
	 else if(string_code.startsWith("1009"))//LK
		{
		    double lock_amt = 0.00;
		    lock_amt = recform.getAmount();
		    System.out.println("inside gen rec LK lock_amt = "+lock_amt);
		    String[]lock_types = recDelegate.getLockersTypes();
		    String lk_ty =null;
		    for(int i=0;i<Lockdesc.length;i++)
		    {
		        if((recform.getLockertype()).equals(Lockdesc[i]))
		        	lk_ty =lock_types[i];
		    }
		    
		    System.out.println("inside gen rec LK lock_type = "+lk_ty);
		    
		    try
			{
		        String date =recDelegate.getSysDate();
		    	double rent = recDelegate.getRent(lk_ty,365,101,date);
             
             System.out.println("lk rent to be collected == "+rent);
             
		        if(lock_amt<rent)
		        {
		            System.out.println("U r Paying less rent...rent to be paid is Rs. "+rent+"");
		            //txt_amount.requestFocus();
		        }
		        
			}
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null,"Retrieve Exception :" +e1);
			}
		}
		else
		{
			System.out.println("not a share or LK or PO ...ac_no==0....");
			
			
			double amt = 0.00,minamt = 0.00;
         
         try
         {
             minamt = module_obj_array[Integer.valueOf(recform.getAccounttype())].getMinBal();
             
             amt = (amount);
             
             if(amt>=minamt)
             {
                System.out.println("currency denomination");
             }
             else
             {
                 //txt_amount.setText("0.00");
                 //txt_amount.requestFocus();
                 JOptionPane.showMessageDialog(null,"Min Amount should be Rs. "+minamt+"");
             }
         }
         catch(Exception e1)
         {
             JOptionPane.showMessageDialog(null,"Retrieve Exception :" +e1);
         }
         /////////
		}
	}
	
//Existing Account
	else if(recform.getAmount()>0 && (recform.getAccno()!=0))
	{
		if(accnoexist==0)
		{
			if(string_code.startsWith("1004"))
			{
				System.out.println("u r inside RD ");
				double amt=recform.getAmount();
				
				String Ldate=accountobject.getLastTrnDate();
				double days=accountobject.getDepositdays();//for deposit days;
				double depamt=accountobject.getAmount();
				double rdbal=accountobject.getShadowBalance();//rd balance
				double int_amt = accountobject.getIntAmount();//interest
				String date =recDelegate.getSysDate();
				System.out.println("Last Trn Date ="+ date);
				System.out.println("Amount : "+amt);
				System.out.println("DepAmt : "+depamt);
				System.out.println("days   : "+days);
				System.out.println("rdBal  : "+rdbal);
				System.out.println("rd interest  : "+int_amt);
				System.out.println("Check: "+((depamt*days)+int_amt-rdbal));
				
				if(amt<depamt)
				{
					System.out.println("Your installment amount is "+depamt+"\n you are paying less than that");
					//txt_amount.setText("");
					//txt_amount.requestFocus();
				}
				else if(amt>((depamt*days)+int_amt-rdbal))
				{
					System.out.println("Paid amt is more than amount to pay");
					//txt_amount.setText("");
					//txt_amount.requestFocus();
				}
				else if(amt%depamt!=0)
				{
					JOptionPane.showMessageDialog(null,"Amount is not multiple of installment amount");
					//txt_amount.setText("");
					//txt_amount.requestFocus();
				}
				else
				{
					System.out.println("...RD...");
					
					
				}		
			}
			else if(string_code.startsWith("1008"))
			{
				double amt=recform.getAmount();
				double max_amt = 0.0;
				
				System.out.println("LD amt = "+amt);
				
				//ship.....19/06/2006
				try
				{
					String date = recDelegate.getSysDate();
					max_amt = recDelegate.getMaxPayableAmtonCurrentDay(module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode(),recform.getAccno(),date);
				    System.out.println("ld max amt = "+max_amt);
				}
				catch(Exception ex)
				{
				    ex.printStackTrace();
				}
				
				if(amt > max_amt)
				{
					System.out.println("Paid amt is more than amount to pay. Amt to pay-->"+max_amt);
					//txt_amount.requestFocus();
				}
				else if(amt==0)
				{
					System.out.println("Enter Valid Amount");
					//txt_amount.requestFocus();
				}
			}
			
			else if(string_code.startsWith("1010"))
			{
				double amt = recform.getAmount();
				double max_amt = 0.0;
				System.out.println("LN amt = "+amt);
				
				
				try
				{
				    String date=recDelegate.getSysDate();
					max_amt = recDelegate.checkLNAmount(module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode(),recform.getAccno(),date,"ship","CA99");
				    
				    if(amt > max_amt)
					{
				    	System.out.println("Paid amt is more than amount to pay. Amt to pay-->"+max_amt);
						//txt_amount.requestFocus();
					}
					else if(amt==0)
					{
						System.out.println("Enter Valid Amount");
						//txt_amount.requestFocus();
					}
					else
					{
					    
					    
					    //cb_currDenom.requestFocus();
					    
					}
				}
				catch(Exception ex)
				{
				    ex.printStackTrace();
				}
				///////////////
			}
			//
		
		
	}//end of amount


	 //if((recform.getCustacno())!=null)
		if((recform.getCustacno())!=0)
		{
     		
		 	
         	System.out.println("account no in PO"+recform.getAccno());
		    System.out.println("focus lost from PO accno");
		    System.out.println("code of the module@@@@@@@@@@@@@@@"+postring_code);
		    
				    if(postring_code.startsWith("1009"))//LK
	                {
				        try 
	                    {
	                         //String string_locker_type=recDelegate.getLockerType((recform.getCustacctype()),Integer.parseInt(recform.getCustacno()));
				        	String string_locker_type=recDelegate.getLockerType((recform.getCustacctype()),recform.getCustacno());
	                            
	                         if(string_locker_type==null)
	                         { 
	                         	 accnoexist = 1;
	                         	
	                             System.out.println("Invalid account no");
	                         }
	                    } 
					    catch (NumberFormatException e1) 
						{
	                         e1.printStackTrace();
	                    }
				        catch (RemoteException e1) 
						{
	                         e1.printStackTrace();
	                    }
				        catch (SQLException e1) 
						{
	                         e1.printStackTrace();
	                    }
				        catch (RecordsNotFoundException e1) 
						{
	                         e1.printStackTrace();
	                    }
	                }
				    
				    else if(postring_code.startsWith("1001"))//SH
	                {
				        try 
	                    {
	                         //String string_share_cat=String.valueOf(recDelegate.getShare(postring_code,Integer.parseInt(recform.getCustacno())));
				        	String string_share_cat=String.valueOf(recDelegate.getShare(postring_code,recform.getCustacno()));
	                         
	                         if(string_share_cat.equals("null"))
	                         { 
	                         	 accnoexist = 1;
	                         	 
	                             System.out.println("Invalid account no");
	                         }
	                    } 
					    catch (NumberFormatException e1) 
						{
	                         e1.printStackTrace();
	                    }
				        catch (RemoteException e1) 
						{
	                         e1.printStackTrace();
	                    }
	                }
				    
	                if(accnoexist==0)
	                {
	                	try
						{
							String date = recDelegate.getSysDate();
	                		//accountobject=recDelegate.getAccount(null,postring_code,Integer.parseInt(recform.getCustacno()),date);
							accountobject=recDelegate.getAccount(null,postring_code,recform.getCustacno(),date);
							System.out.println("accountobject%%%%%%%%%"+accountobject+"\n"+"name&&&&&&"+accountobject.getAccname());
							
							if(accountobject!=null)
							{
								if(accountobject.getAccStatus().equals("C"))
								{
								    //po_ac_type.requestFocus();
		                            System.out.println("Given account is Closed");
								}
								else if(accountobject.getAccStatus().equals("I"))
								{
								    //po_ac_type.requestFocus();
									System.out.println("InOperative Account");
								}
								else if(accountobject.getVerified()==null)
								{
								    //po_ac_type.requestFocus();
									System.out.println("Given account is not yet Verified");
								}
								else if((accountobject.getDefaultInd()!=null)?accountobject.getDefaultInd().equals("T"):false)
								{
								   // po_ac_type.requestFocus();
									System.out.println("Default account");
								}
								else if((accountobject.getFreezeInd()!=null)?accountobject.getFreezeInd().equals("T"):false)
								{
								   // po_ac_type.requestFocus();
									System.out.println("Freezed account");
								}
								else if(accountobject.getAccname()!=null)
								{ 
									System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!========>"+accountobject.getAccname());
									recform.setPurchase(accountobject.getAccname());
									//pur_name_txt.setToolTipText(accountobject.getAccname().toUpperCase());
								recform.setCustacctype(accountobject.getAcctype());		
									//pur_name_txt.setEditable(false);
									
									//ship.....29/09/2006
									System.out.println("Customer ID = "+accountobject.getCid());
									//obj_personal.showCustomer(accountobject.getCid());
									///////////
									
									//ship......06/10/2006
									String cust_subcat = "";
									
							        try
							        {
							            //cust_subcat = recDelegate.getCustSubCat(postring_code,recform.getCustacno());
							        	cust_subcat = recDelegate.getCustSubCat(postring_code,String.valueOf(recform.getCustacno()));
							            recform.setDescription(cust_subcat);
							        }
							        catch(Exception exception)
								    {
								        exception.printStackTrace();
								    }
							        System.out.println("cust_subcat "+cust_subcat);
							      
							       // po_desc_combo.setEnabled(false);
							        ///////////
							        
									//po_favour_txt.requestFocus();
								}
							}
							else
							{
								accnoexist = 1;
								//po_ac_no.requestFocus();
								System.out.println("Given account number not found");
							}	
						}
		                catch(NullPointerException ex)
						{
		                   
		                	System.out.println("Given account number not found");
		                }
						catch(Exception exception)
						{
							exception.printStackTrace();
						}
	                }
			
		}
	}

		

	 setReceiptparamform(request, path, recDelegate);
	
		
	}
	return map.findForward(ResultHelp.getSuccess());
}

private void setRebalanceInitParams(HttpServletRequest req,String path, CashDelegate rbDelegate)throws Exception

{
       try{
    	  
          req.setAttribute("pageId",path);
          
         
       }catch (Exception e) {
	throw e;
	}
}

private void setScrollInitParams(HttpServletRequest req,String path, CashDelegate scrollDelegate)throws Exception

{
       try{
    	  
          req.setAttribute("pageId",path);
          
         
       }catch (Exception e) {
	throw e;
	}
}

private void setReceiptparamform(HttpServletRequest req,String path, CashDelegate rcDelegate)throws Exception

{
       try{
    	  
          req.setAttribute("pageId",path);
          
         
       }catch (Exception e) {
	throw e;
	}
}
private void setErrorPageElements(String exception,HttpServletRequest req,String path)
{
    req.setAttribute("exception",exception);
    req.setAttribute("pageId",path);

} 

}