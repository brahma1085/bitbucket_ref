package com.scb.designPatterns;

import exceptions.DateFormatException;
import exceptions.RecordsNotFoundException;
import general.Validations;
import masterObject.generalLedger.Form1Object;
import masterObject.generalLedger.GLObject;
import masterObject.generalLedger.GLReportObject;
import masterObject.generalLedger.TransferScroll;
import generalLedgerServer.GLHome;
import generalLedgerServer.GLRemote;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.ejb.CreateException;

import masterObject.general.ModuleObject;
import masterObject.generalLedger.DayBookObject;

import shareServer.ShareHome;
import shareServer.ShareRemote;

import com.scb.designPatterns.exceptions.ServiceLocatorException;

import commonServer.CommonHome;
import commonServer.CommonRemote;
import masterObject.*;

/**
 * 
 * User: 
 * Date: Sep 29 
 * Time: 11:53:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class GLDelegate {
	private ServiceLocator servicelocator;
	private CommonHome comHome;
    private CommonRemote comRemote;
    private GLHome gl_home;
    private GLRemote gl_remote;
    DayBookObject array_DayBookObject[]=null;
    String str_qry="";
    
    
  public CommonRemote getComRemote() 
	
	{
        return this.comRemote;
    }
  
  public GLDelegate() throws RemoteException,CreateException,ServiceLocatorException
  {
	  try{
		  this.gl_home=(GLHome)ServiceLocator.getInstance().getRemoteHome("GLWEB", GLHome.class);
			this.gl_remote=gl_home.create();
	      		
			
			getComRemote();
	  }
	  
	  catch (RemoteException e) 
		{
          throw e;
      }
      catch (CreateException ex) {
          throw ex;
      }
      catch(ServiceLocatorException se){
          throw se;
      }
  }
  
  public GLDelegate(String id)throws RemoteException,CreateException,ServiceLocatorException 
	 {
		 // reconnect to the session bean for the given id
		 reconnect(id);
	 }
	
	 public void reconnect(String id) throws ServiceLocatorException 
	 {
		 try 
		 {
			 gl_remote = (GLRemote) ServiceLocator.getService(id);
		 } 
		 catch(ServiceLocatorException ex)
		 {
			 throw ex;
		 }
	 }

	 public String getID()throws RemoteException,CreateException,ServiceLocatorException 
	 {
	        try 
	        {
	            return ServiceLocator.getId(gl_remote);
	        } 
	        catch (ServiceLocatorException e) 
	        {
	            throw e;
	        }
	    }
	 
	private CommonRemote getCommonRemote()throws RemoteException,CreateException,ServiceLocatorException 
	{
     this.comHome=(CommonHome)ServiceLocator.getInstance().getRemoteHome("COMMONWEB",CommonHome.class);
     this.comRemote=comHome.create();
     return comRemote;
   }
	public static String getSysDate() 
	{
		System.out.println("_________inside the getsysdate in delegate_______");
		Calendar c = Calendar.getInstance();
		try {
			return (Validations.checkDate(c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR)));
		} catch (DateFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String getSysTime(){
		java.util.Date now=new java.util.Date();
		DateFormat fmt=DateFormat.getTimeInstance(DateFormat.MEDIUM,Locale.UK);
		return fmt.format(now);
	}

	
	public String dailyposting(String date,String gluser,String gltml,String bankname) throws RemoteException{
		String result;
		System.out.println("The date in daelegate"+date);
		result=gl_remote.dailyPosting(date, gluser, gltml,getSysDate(),bankname);
		System.out.println("The result after daily posting is "+result);
		return result;
		
	}
	
	public int dodayClose(String date,String gluser,String gltml) throws RemoteException{
		int result;
		System.out.println("The date in daelegate"+date);
		result=gl_remote.doDayClose(date,gluser,gluser);
		System.out.println("The result after daily posting is "+result);
		return result;
		
	}
	
	
	
	public String[][] checkDailyStatus(String date)throws RemoteException{
		String dlystatus[][]=null;
		dlystatus=gl_remote.checkDailyStatus(date,date);
		System.out.println("The dlystatus is"+dlystatus);
		return dlystatus;
		
	}
	
	public String[][] checkDailyStatus(String fromdate, String todate)throws RemoteException{
		String dlystatus[][]=null;
		int ret=0;
		try{
		dlystatus=gl_remote.checkDailyStatus(fromdate,todate);
		
	}catch(Exception e){e.printStackTrace();}
	return dlystatus;
}
	public String[][] checkMonthlyStatus(String fromdate, String todate)throws RemoteException{
		String mthstatus[][]=null;
		int ret=0;
		try{
			mthstatus=gl_remote.checkMonthlyStatus(fromdate,todate);
		
	}catch(Exception e){e.printStackTrace();}
	return mthstatus;
}
	
	public String[][] checkMonthConsolidation(String fromdate, String todate, String br_location)throws RemoteException{
		String mthstatus[][]=null;
		int ret=0;
		try{
		mthstatus=gl_remote.checkMthConStatus(fromdate,todate,"Head Office");
				
	}catch(Exception e){e.printStackTrace();}
	return mthstatus;
}
	
	public int doMonthPosting(String year, String month,String gluser,String gltml,String bankname)throws RemoteException
	{   int b=0;
		try{
			 b=gl_remote.doMonthlyPosting(Integer.parseInt(year),Integer.parseInt(month),bankname,gltml,gluser,this.getSysDate()+" "+this.getSysTime(),"T");
		}
		catch(Exception e){e.printStackTrace();}
		
	
	
	return b;
	}
	
	public String[][] checkMonthClose(String year_mth)throws RemoteException{
		String mthstatus[][]=null;
		int ret=0;
		try{
			mthstatus=gl_remote.checkForMonthClose(year_mth);
				
	}catch(Exception e){e.printStackTrace();}
	return mthstatus;
}
	public int closeMonth(int year, int month)throws RemoteException{
		int b=0;
		
		try{
			b=gl_remote.closeMonth(year ,month);
				
	}catch(Exception e){e.printStackTrace();}
	return b;
}
	
	//BranchPLPosting branch details code
	
	public String[][] getBranchDetails()throws RemoteException{
		String branchdet[][]=null;
		try
		{
	    	
	        branchdet=gl_remote.getNonComputersiedBranchs();
		}catch(Exception e){e.printStackTrace();}
	return branchdet;
}
	
	public String yearClose()throws RemoteException{
		String year_close=null;
		String ret[][]=null;
		try
		{
	    	
	        year_close=gl_remote.getYearMonth();
	        //ret=yearStatus(date);
		}catch(Exception e){e.printStackTrace();}
	return year_close;
}
	public String[][] yearStatus(String year_mth)throws RemoteException{
		String[][] year_status=null;
		try
		{
			year_status=gl_remote.checkYearStatus(year_mth,"Head Office");
	        
		}catch(Exception e){e.printStackTrace();}
	return year_status;
}
//plprofit
	public double getPlProfit(String year, String month)throws RemoteException{
		double p=0.0;
		try
		{
			p=gl_remote.getPLProfit(year.trim()+month,"Head Office",false);
	        
		}catch(Exception e){e.printStackTrace();}
	return p;
}
//plposting	
	public int PlPosting(String year, String month,String gluser,String gltml,String bankname)throws RemoteException{
		int p=0;
		try
		{
			
			p=gl_remote.PLPosting(year.trim()+month,year.trim()+month,bankname,gltml,gluser,this.getSysDate()+this.getSysTime(),false);
	        
		}catch(Exception e){e.printStackTrace();}
	return p;
}
//close year
	public int closeYear(String year, String month,String bankname)throws RemoteException{
		int p=0;
		try
		{
			p=gl_remote.closeYear(year.trim()+month,bankname);
			
	        
		}catch(Exception e){e.printStackTrace();}
	return p;
}
//daybook method(checkDlyStatusforReport)
	public int checkDlyStatusReport(String dt)throws RemoteException{
		int p=0;
		try
		{
			p=gl_remote.checkDlyStatusForReport(dt.trim(),dt.trim());
			
	        
		}catch(Exception e){e.printStackTrace();}
	return p;
}
	//viewing the records
	
	
	public DayBookObject[] getRecords(String dt)throws RemoteException{
		System.out.println("entering into getRecords");
		DayBookObject array_daybookobj[]=new DayBookObject[50];
		String qry_string=" ";
		try
		{   System.out.println("creating the daybookobj array");
			array_daybookobj=gl_remote.getDetails(dt,qry_string);
			System.out.println("created the daybookobj array");
			System.out.println("array is="+array_daybookobj.toString());
	        
		}catch(Exception e){e.printStackTrace();}
	return array_daybookobj;	
	}
	
	public ModuleObject[] getMainMods()throws RemoteException{
		ModuleObject mod[]=null;
		
	//moduleObj start
		try{
			comRemote=getCommonRemote();
	mod=comRemote.getMainModules(2,"1012000");       
		}
    catch(Exception e)
    {e.printStackTrace();}
    return mod;
}
    
	
	
	//moduleObj end
	public String[][] getCodeTypes(String fromdate, String todate)throws RemoteException{
		String codeType[][]=null;
		
	//moduleObj start
		try{
			codeType=gl_remote.getGLDetailsForTwoDates(fromdate.trim(),todate.trim());   
		}
    catch(Exception e)
    {e.printStackTrace();}
    return codeType;
}
	//voucherslipprinting
	public String[][] getCodeTypesForVoucher(String slipdate)throws RemoteException{
		String codeType[][]=null;
		
	//moduleObj start
		try{
			codeType=gl_remote.getGLDetails(slipdate.trim());   
		}
    catch(Exception e)
    {e.printStackTrace();}
    return codeType;
}
	
	public GLReportObject[] glScheduleDetails(String fromdate, String todate, String from_gltype, int from_glno, String to_gltype, int to_glno)throws RemoteException{
		GLReportObject[] GLObject=null;
		
		
		try{
		GLObject=gl_remote.getGLSchduleDetailsForTwoDates(fromdate.trim(),todate.trim(),from_gltype,from_glno,to_gltype,to_glno);
		
	}catch(Exception e){
		e.printStackTrace();
	}
	return GLObject;
	}
	
	public ModuleObject[] getMainModules()throws RemoteException{
		ModuleObject mod1[]=null;
		
	//moduleObj start
		try{
			comRemote=getCommonRemote();
	mod1=comRemote.getMainModules(2,"1008000");       
		}
    catch(Exception e)
    {e.printStackTrace();}
    return mod1;
}
	
	public ModuleObject[] getMainModules1()throws RemoteException{
		ModuleObject mod1[]=null;
		
	//moduleObj start
		try{
			comRemote=getCommonRemote();
	mod1=comRemote.getMainModules(2,"1010000");       
		}
    catch(Exception e)
    {
    	e.printStackTrace();
    }
    return mod1;
}
	//TrfScroll Loans on deposit combo values
	public ModuleObject[] getTrfMainModules()throws RemoteException{
		ModuleObject mod1[]=null;
		
	//moduleObj start
		try{
			comRemote=getCommonRemote();
	mod1=comRemote.getMainModules(2,"1008000");       
		}
    catch(Exception e)
    {e.printStackTrace();}
    return mod1;
}
	//view records of TrfScrollPrinting
	public TransferScroll[] transferScrollPrint(String fromdate, String todate, String actype){
		TransferScroll trfScroll[]=null;
		System.out.println("====delegate===fromdate==="+fromdate);
		System.out.println("====delegate===todate==="+todate);
		System.out.println("====delegate===actype==="+actype);
		try{
			trfScroll=gl_remote.transferScrollPrint(fromdate, todate, actype, " ");
			System.out.println("TransferScroll Object++++++++++++Delegate+++++++++"+trfScroll.length);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return trfScroll;
	}
	
	//monthly glschedule
	public String[][] getGlDetForMonth(String yearmonth)throws RemoteException{
		System.out.println("At 388 in delegate begins");
		String codeType[][]=null;
		
	//moduleObj start
		try{
			System.out.println("At 393 in delegate");
			codeType=gl_remote.getGLDetailsForMonth(Integer.parseInt(yearmonth));   
			System.out.println("At 393 in delegategl_remote.getGLDetailsForMonth(yearmonth)   "+gl_remote.getGLDetailsForMonth(Integer.parseInt(yearmonth)));
		}
    catch(Exception e)
    {e.printStackTrace();}
    return codeType;
}	
	/*public int getCurrYear()throws RemoteException{
		java.util.Calendar c=java.util.Calendar.getInstance();
		System.out.println("Year============="+c.get(Calendar.YEAR));
		return c.get(Calendar.YEAR);
	}
	public int getCurrMonth()throws RemoteException{
		java.util.Calendar c=java.util.Calendar.getInstance();
		System.out.println("Year============="+c.get(Calendar.MONTH));
		return c.get(Calendar.MONTH);
	}*/
	//crr slr subschedule combo for code
	public String[][] getCodeSubSchedules(String year, String month)throws RemoteException{
		System.out.println("At 388 in delegate begins");
		String codename[][]=null;
		
	//moduleObj start
		try{
			codename=gl_remote.getSubSchedules(year.toString().trim(),month.toString().trim(),0);   
			
		}
    catch(Exception e)
    {e.printStackTrace();}
    return codename;
}	
	
	//form1 link updation code details
	public String[][] getCodeDet()throws RemoteException{
		
		String codename[][]=null;
		
	//moduleObj start
		try{
			codename=gl_remote.getCodeDetails(this.getSysDate(),0);   
			
		}
    catch(Exception e)
    {e.printStackTrace();}
    return codename;
}	
	
	//form1master
public String[][] form1Tabledata(int num)throws RemoteException{
		
		String tabledata[][]=null;
		
	//moduleObj start
		try{
			tabledata=gl_remote.getForm1Details(num);   
			
		}
    catch(Exception e)
    {e.printStackTrace();}
    return tabledata;
}

//form1Master Link

public String[][] form1LinkTabledata(int num)throws RemoteException{
	
	String linkTabledata[][]=null;
	
//moduleObj start
	try{
		linkTabledata=gl_remote.getForm1LinkDetails(this.getSysDate(),num);   
		
	}
catch(Exception e)
{e.printStackTrace();}
return linkTabledata;
}
//Marking Date Entry
public String[][] getMarkingDtDetails()throws RemoteException{
	
	String markingDtDetail[][]=null;
	
//moduleObj start
	try{
		markingDtDetail=gl_remote.getMarkingDateDetails();   
		
	}
catch(Exception e)
{e.printStackTrace();}
return markingDtDetail;
}
//getting table information(admin)
public GLObject getTableInfo(String tablename)throws RemoteException{
	System.out.println("===entering into the getTableinfo method===========");
	GLObject gl_table=null;
	
//moduleObj start
	try{
		System.out.println("===inside try of  the getTableinfo method===========");
		gl_table=gl_remote.getTableInformation(tablename);   
		System.out.println("===After executing the bean method of getTableinfo method===========");
	}
catch(Exception e)
{e.printStackTrace();}
return gl_table;
}
//getting table data from db(admin)
public GLObject[] getTableFromDB(String tablename,String date, boolean selected)throws RemoteException{
	
	GLObject gl_object[]=null;
	
//moduleObj start
	try{
		
		gl_object=gl_remote.getTable(tablename,date,selected);   
		System.out.println("===After executing the bean method of getTableinfo method===========");
	}
catch(Exception e)
{e.printStackTrace();}
return gl_object;
}

//consolidated day book
public String[][] getBrDetails()throws RemoteException{
	String branchdet[][]=null;
	try
	{
    	
        branchdet=gl_remote.getBranchDetails();
	}catch(Exception e){e.printStackTrace();}
return branchdet;
}
//daybook array object

public DayBookObject[] getBrConDayBook(String date,int flag)throws RemoteException{
	DayBookObject brConDayBook[]=null;
	try
	{
    	
		brConDayBook=gl_remote.getBranchConsolidatedDayBook(date.trim()," ",flag);
	}catch(Exception e){e.printStackTrace();}
return brConDayBook;
}

public DayBookObject[] getConDayBookOpenCloseBal(String date)throws RemoteException{
	DayBookObject brConDayBook[]=null;
	try
	{
    	
		brConDayBook=gl_remote.ConsolDayBookOpeningClosingBal(date.trim());
	}catch(Exception e){e.printStackTrace();}
return brConDayBook;
}

public int insertRecord(String tablename, GLObject[] globj)throws RemoteException{
	int ret=0;
	try
	{
    	
		ret=gl_remote.insertRecords(tablename, globj);
	}catch(Exception e){e.printStackTrace();}
return ret;
}
public int checkMonthStatusForReport(String fromdt, String todt)throws RemoteException{
	int ret=0;
	try
	{
    	
		ret=gl_remote.checkMthStatusForReport(fromdt, todt);
	}catch(Exception e){e.printStackTrace();}
return ret;
}
public GLReportObject[] getBalanceTwodates1(String fromdate, String todate, int no)throws RemoteException{
	GLReportObject[] GLObject=null;
	
	
	try{
	GLObject=gl_remote.getBalanceTwoDates(fromdate.trim(),todate.trim(),no, " ");
	
}catch(Exception e){
	e.printStackTrace();
}
return GLObject;
}
public GLReportObject[] getBalanceTwodates2(String fromdate, String todate, int no)throws RemoteException{
	GLReportObject[] GLObject=null;
	
	
	try{
	GLObject=gl_remote.getBalanceTwoDates(fromdate.trim(),todate.trim(),no, " ");
	
}catch(Exception e){
	e.printStackTrace();
}
return GLObject;
}

public GLObject[] getRecieptAndPayment(int fromdate, int todate)throws RemoteException{
	GLObject[] GLObj=null;
	String qry="";
	System.out.println("fromdate inthe delegate is"+fromdate);
	System.out.println("todate inthe delegate is"+todate);
	try{
		System.out.println("Inside Delegate method of rp");
	GLObj=gl_remote.getReceiptAndPayment(fromdate,todate,qry);
	System.out.println("Successfully returning back from delegate rp");
}catch(Exception e){
	e.printStackTrace();
}
return GLObj;
}
public String[] getCashGlTypeCode()throws RemoteException{
	String[] glTypeCode=null;
	
	
	try{
		System.out.println("inside delegate of the method getGlCashTypeCode==");
	glTypeCode=gl_remote.getCashGLTypeCode();
	
}catch(Exception e){
	e.printStackTrace();
}
return glTypeCode;
}
public double openingBalance(String gltype, int glcode, String date)throws RemoteException{
	double bal=0.0;
	
	
	try{
		System.out.println("inside the delegate method of openingbalance");
	bal=gl_remote.OpeningBalance(gltype, glcode, date);
	
}catch(Exception e){
	e.printStackTrace();
}
return bal;
}
public double closingBalance(String gltype, int glcode, String date)throws RemoteException{
	double bal=0.0;
	
	
	try{
		System.out.println("inside the delegate method of closingbalance");
	bal=gl_remote.ClosingBalance(gltype, glcode, date);
	
}catch(Exception e){
	e.printStackTrace();
}
return bal;
}
public int checkMonthlyStatusReport(String dt, String dt1)throws RemoteException{
	int p=0;
	try
	{
		p=gl_remote.checkDlyStatusForReport(dt.trim(),dt1.trim());
		
        
	}catch(Exception e){e.printStackTrace();}
return p;
}
public GLReportObject[] glMthScheduleDetails(String month, String year, int from_glcode, String from_gltype, int to_glcode, String to_gltype, String str )throws RemoteException{
	GLReportObject[] GLObject=null;
	
	
	try{
	GLObject=gl_remote.getMonthlyDetailsForMonth(month.trim(),year.trim(),from_glcode,from_gltype,to_glcode,to_gltype,str);
	
}catch(Exception e){
	e.printStackTrace();
}
return GLObject;
}
public int rbiForm1InsertRow(Form1Object form1_object,int num)throws RemoteException{
	int ret=0;
	try
	{
    	
		ret=gl_remote.form1InsertRow(form1_object, num);
	}catch(Exception e){e.printStackTrace();}
return ret;
}
public int rbiForm1LinkInsertRow(Form1Object form1_object,int num)throws RemoteException{
	int ret=0;
	try
	{
     	
		ret=gl_remote.form1LinkInsertRow(form1_object, num);
	}catch(Exception e){e.printStackTrace();}
return ret;
}
public int rbiMarkingDateInsertRow(Form1Object form1_object)throws RemoteException{
	int ret=0;
	try
	{
     	
		ret=gl_remote.MarkingDateInsertRow(form1_object);
	}catch(Exception e){e.printStackTrace();}
return ret;
}
public String[][] getRbiForm9Report(String year, String month)throws RemoteException{
	String[][] tabledata=null;
	try
	{
     	System.out.println("about to fetch data from db============================");
		tabledata=gl_remote.getForm9Report(year,month,0," ");
	}catch(Exception e){e.printStackTrace();}
return tabledata;
}
public String[][] getRbiForm9InputCodes()throws RemoteException{
	String[][] tabledata=null;
	try
	{
     	
		tabledata=gl_remote.getForm9InputCodes(GLDelegate.getSysDate());
	}catch(Exception e){e.printStackTrace();}
return tabledata;
}
public String[][] getRbiCrrSlrReport(String year, String month, int code)throws RemoteException{
	String[][] tabledata=null;
	try
	{
     	System.out.println("=======INside delegate===========");
		tabledata=gl_remote.getCRSLRReport(year,month,code);
	}catch(Exception e){e.printStackTrace();}
return tabledata;
}
public String[][] getRbiForm1AReport(String year, String month)throws RemoteException{
	String[][] tabledata=null;
	try
	{
		System.out.println("year"+year+"month"+month);
		
     	System.out.println("about to fetch data from db in Form1A============================");
		tabledata=gl_remote.getForm1AReport(year,month);
		System.out.println("tabledata is "+tabledata);
	}catch(Exception e){e.printStackTrace();}
return tabledata;
}
public String[][] getRbiForm1BCReport(String year, String month,int num)throws RemoteException{
	String[][] tabledata=null;
	try
	{System.out.println("year"+year+"month"+month+"num"+num);
	
     	System.out.println("about to fetch data from db in Form1BC============================");
     	System.out.println("Hi ia m here again");
		tabledata=gl_remote.getForm1BCReport(year,month,num);
		System.out.println("tabledata is "+tabledata);
		return tabledata;
	}catch(Exception e){e.printStackTrace();}
return tabledata;
}
public String[] getRbiMarkingFridays(String year, String month)throws RemoteException{
	String[] fridays=null;
	try
	{System.out.println("year"+year+"month"+month);
     	System.out.println("about to fetch data from db============================");
     	fridays=gl_remote.getMarkingFridays(year,month);
	}catch(Exception e){e.printStackTrace();}
return fridays;
}

public GLObject[] getVoucherSlip(String slip_date,String gl_type,int gl_no1,int gl_no2,int type)throws RemoteException,RecordsNotFoundException{
	System.out.println("GLType is"+gl_type);
	System.out.println("GLNO1 is"+gl_no1);
	System.out.println("GLNO2 is"+gl_no2);
	GLObject[] globj=null;
	try
	{
     	
		globj=gl_remote.getVouchingSlip(slip_date, gl_type, gl_no1, gl_no2, type);
	}catch(Exception e){e.printStackTrace();}
return globj;
}
public String[] checkRBITransaction(Form1Object form1_object)
{
	String[] result_val=null;
	try{
		result_val=gl_remote.checkRBITransaction(form1_object);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result_val;
}

public boolean Form9Posting(String lastfriday,String de_user,String de_tml,String datetime,String amount[][])throws RemoteException 
{
	boolean i= gl_remote.Form9Posting(lastfriday,de_user,de_tml,datetime,amount);
	return i;
}
public int  checkForm9Transaction(String lastfriday)throws RemoteException 
{
	int i=gl_remote.checkForm9Transaction(lastfriday);
	
	return i;
}

public String[][] getForm9LinkCodes(String date)throws RemoteException{
	
	System.out.println("length in delegate-->");
	
	String[][] code = gl_remote.getForm9LinkCodes(date);
	
	
	
	return code;
}
public int Form1Posting(Form1Object form1_object){
	int result=0;
	try{
	result=gl_remote.Form1Posting(form1_object);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
public int updateFormLinkdetails(String link[][],int num)throws RemoteException{
	
	int i = gl_remote.updateFormLinkdetails(link,num);
	
	return i;
	
}
public int updateTable(String tablename, GLObject[] gl_obj)throws RemoteException{
	int result=0;
	System.out.println("inside the delegate of updateTAble"+tablename);
	System.out.println("inside the delegate of Obj Length=======>"+gl_obj.length);
	System.out.println("inside the delegate of DtTime=======>"+gl_obj[0].getDeDate());
	System.out.println("inside the delegate of Date=======>"+gl_obj[0].getDate());
	System.out.println("inside the delegate of PostINd=======>"+gl_obj[0].getPostInd());
	System.out.println("inside the delegate of DAyClose=======>"+gl_obj[0].getDayClose());
	System.out.println("inside the delegate of Cash close=======>"+gl_obj[0].getCashClose());
	System.out.println("inside the delegate of updateTAble=======>"+gl_obj[0].getDeUser());
	System.out.println("inside the delegate of updateTAble=======>"+gl_obj[0].getDeTml());
	try
	{   
     	
     	result=gl_remote.updateTable(tablename, gl_obj);
	}catch(Exception e){e.printStackTrace();}
return result;
}


}
