package com.scb.designPatterns;



import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import javax.ejb.CreateException;

import masterObject.clearing.AckObject;
import masterObject.clearing.BounceFineObject;
import masterObject.clearing.ChequeAckObject;
import masterObject.clearing.ChequeDepositionObject;
import masterObject.clearing.ClearingObject;
import masterObject.clearing.CompanyMaster;
import masterObject.clearing.DiscountCharges;
import masterObject.clearing.Reason;
import masterObject.clearing.ReasonLink;
import masterObject.clearing.ReasonMaster;
import masterObject.customer.CustomerMasterObject;
import masterObject.frontCounter.AccountMasterObject;
import masterObject.frontCounter.PayOrderObject;
import masterObject.general.AccSubCategoryObject;
import masterObject.general.AccountObject;
import masterObject.general.BranchObject;
import masterObject.general.ModuleObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.share.ShareCategoryObject;
import shareServer.ShareHome;
import shareServer.ShareRemote;
import cashServer.CashHome;
import cashServer.CashLocalHome;
import cashServer.CashRemote;
import clearingServer.ClearingHome;
import clearingServer.ClearingRemote;

import com.scb.designPatterns.exceptions.ServiceLocatorException;
import commonServer.CommonHome;
import commonServer.CommonRemote;

import customerServer.CustomerHome;
import customerServer.CustomerRemote;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import exceptions.RecordNotInsertedException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;
import exceptions.RecordsNotInsertedException;
import frontCounterServer.FrontCounterHome;
import frontCounterServer.FrontCounterRemote;
import general.Validations;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Dec 1, 2007
 * Time: 11:52:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClearingDelegate {
	
	
	
	private static ClearingDelegate delegate;
	
	private ClearingRemote clearing_remote;
	
	
	private CommonRemote common_remote;
	
	private FrontCounterHome front_home;
	private FrontCounterRemote front_remote;
	
	private CustomerRemote cust_remote;
	
	private ShareRemote share_remote;
	
	private CashRemote cash_remote ; 
	private CashLocalHome cshlocal_home;
	
	Hashtable hash_city;
	Hashtable hash_bank;
	Hashtable hash_branch_name;
	
	
	public static ClearingDelegate getInstance(){
		
		if ( delegate == null) {
			
		 	return  delegate = new ClearingDelegate();
		}
		else 
			return delegate;
				
	}
	
	private ClearingDelegate(){
		
		try {
			
			ClearingHome clearing_home = (ClearingHome)ServiceLocator.getInstance().getRemoteHome( "CLEARINGWEB", ClearingHome.class);
			clearing_remote = clearing_home.create();
			
			CommonHome common_home = (CommonHome)ServiceLocator.getInstance().getRemoteHome("COMMONWEB", CommonHome.class);
			common_remote = common_home.create();
			
			
			
			front_home = (FrontCounterHome)ServiceLocator.getInstance().getRemoteHome("FRONTCOUNTERWEB", FrontCounterHome.class);
			front_remote = front_home.create();
			
			CustomerHome custHome =(CustomerHome)ServiceLocator.getInstance().getRemoteHome("CUSTOMERWEB",CustomerHome.class);
			cust_remote = custHome.create();
			
			ShareHome  share_home = (ShareHome)ServiceLocator.getInstance().getRemoteHome("SHAREWEB", ShareHome.class);  
			share_remote = share_home.create();
			
			CashHome cash_home = ( CashHome ) ServiceLocator.getInstance().getRemoteHome("CASHWEB", CashHome.class);
			cash_remote = cash_home.create();
			
			cshlocal_home =(CashLocalHome)ServiceLocator.getInstance().getLocalHome("CASHLOCALWEB",CashLocalHome.class);
			
			
		} catch ( ServiceLocatorException e) {
			
			e.printStackTrace();
		
		} catch ( CreateException cre   ) {
			
			cre.printStackTrace();
			
		} catch ( RemoteException rmi ){
			
			rmi.printStackTrace();
		}
		
	}
	
	public ModuleObject[] getMainModule( int modules ){
		
		ModuleObject[] module_obj_array = null;
		
		try{
			
			if (modules == 1) 
				module_obj_array = common_remote.getMainModules(2, "'1001000','1002000','1003000','1004000','1005000','1007000','1008000','1014000','1015000','1018000','1012000'" ); 
			else if ( modules == 2 )
				module_obj_array = common_remote.getMainModules(2, "'1002000','1007000','1014000','1015000'" );
			
			else if ( modules == 3 )
				module_obj_array = common_remote.getMainModules(2, "'1010000'" );
			else if ( modules == 4 )
				module_obj_array = common_remote.getMainModules(2, "'1001000','1002000','1003000','1004000','1005000','1007000','1008000','1014000','1015000','1016000','1018000','1012000'" );
			else if ( modules == 5 )
				module_obj_array = common_remote.getMainModules(2, "'1003000','1004000','1005000','1005001','1005002','1005003'" );
			
			
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return module_obj_array;
		
	}
	
	public Hashtable getCityBankBranchDetail(int bcode, String city_cd,String bank_cd, String branch_cd) throws RemoteException,RecordsNotFoundException
 	{
		hash_bank = clearing_remote.getCityBankBranchDetail(bcode, city_cd, bank_cd,branch_cd);
		
		return hash_bank;
	}
	
	
	

	public HashMap getCityBankBranchDetail(String code ) 
	{
		
			String citybankname = null;
			HashMap map_result = new HashMap();
		
		try {
			
			if(hash_city == null)
			{
				
				hash_city = clearing_remote.getCityBankBranchDetail(2,(code).substring(0,3), null, null );
			} 
			
			if ( hash_bank == null ){
				
				hash_bank = clearing_remote.getCityBankBranchDetail(3,(code).substring(3,6) , null, null );
			}
			
			
			if(hash_branch_name == null)
			{
				
				hash_branch_name = clearing_remote.getCityBankBranchDetail(4,(code).substring(6,9) , null, null );
			}
			System.out.println("val of hash tab"+  hash_city);
			
			
			if(hash_city != null)
			{
				if(hash_city.containsKey(((code)).substring(0, 3)))
				{
					System.out.println( hash_city );
					citybankname=(String)hash_city.get(((code)).substring(0,3));
					System.out.println( citybankname );
					map_result.put(((code)).substring(0,3),citybankname) ;
				}
			}
			
			System.out.println(((code)).substring(3,6)+ "  ------------" );
			
			if(hash_bank != null)
			{
				if(hash_bank .containsKey( ((code)).substring( 3, 6 )))
				{
					citybankname = citybankname +"  "+hash_bank.get( ((code)).substring( 3, 6 ));
					System.out.println(  citybankname + " inside the bank code ");
					map_result.put(( (code)).substring(3, 6), hash_bank.get(((code)).substring( 3, 6 ) ));
				}
			} 
			if(hash_branch_name != null)
			{
				if(hash_branch_name.containsKey(((code))))
				{
					citybankname = citybankname + "  " + hash_branch_name.get(((code)));
					System.out.println( citybankname + " inside the branch name hash ");
					map_result.put(((code)).substring(6, 9)+1 , hash_branch_name.get(((code)))) ;
				}
			}
			
			return map_result;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
		
	
	}
	public SignatureInstructionObject[] getSignatureDetails(int accno,String type)throws RecordsNotFoundException,RemoteException 
	{
		 SignatureInstructionObject[] sigObjects=common_remote.getSignatureDetails(accno,type);
		 return sigObjects;
	}
	public int clearingAdminUpdation(Object value[],int type)throws RecordsNotInsertedException,RecordNotInsertedException
	{
		int i=0;
		try
		{
		 i=clearing_remote.clearingAdminUpdation(value,type);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return i;
	}
	public String getGLName( String gl_type, int gl_code){
		
		String gl_name = null;
		
		try {
				
				gl_name = clearing_remote.getGL( gl_type, gl_code ) ;
				
		} catch ( Exception e ) {
			
			e.printStackTrace();
		}
		
		return gl_name;
	}
	
	public AccountObject getAccount ( String acc_tye, int annum, String date){
		
		AccountObject account_obj = null ;
		
		
		try {
			
			account_obj = common_remote.getAccount(null,acc_tye,annum,date);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return account_obj;
		
	}
	public int clearingAdminDeletion(Vector value,int type)throws RecordNotInsertedException,RemoteException
 	{
		int i=clearing_remote.clearingAdminDeletion(value,type);
		return i;
 	}
	
	public CustomerMasterObject getCustomerDetail( int cid ){
		
		CustomerMasterObject cust_obj = null;
		
		try{
			
			cust_obj = cust_remote.getCustomer( cid );
			
		} catch (Exception e) {

		
		}
			
		return cust_obj;
	}
	
	public Vector getChequeDetail( String ac_type, int ac_no, int chq_no  ){
		
		Vector chequeobject = null;
		
		try{
		
			chequeobject = front_remote.getChequeNoDet(ac_type,ac_no,chq_no);
		
		} catch (Exception e) {
				
			e.printStackTrace();
		}
		
	return 	chequeobject;
	}
	
	public ChequeAckObject[] getBouncedCheques(String fdate,String tdate,String query)throws RecordsNotFoundException
	{
		ChequeAckObject[] ackObjects=null;
		try{
		 ackObjects=clearing_remote.getBouncedCheques(fdate, tdate, null);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return ackObjects;
	}
	
	
	public Object  getGenParam(){
		
		Object obj = null;
		
		try{
			
			obj = clearing_remote.getGenParam("min_period");
			
		} catch (Exception e) {

		}
		
		return obj;
		
	} 
	
	
	public Reason[] getReasonDetails(String acc_type,int value){
		
		Reason[] reasons = null;
		
		try {
		
			reasons = clearing_remote.getReasonDetails("1002001",0 );
			
		} catch (Exception e) {

				
				e.printStackTrace();
				
		}
		 
		
		return reasons;
		
	}

	public double DiscountChargeCalculation( String ac_type, double amt  ){
		
		double discount_charge = 0.0;
		
		try
		{
			discount_charge = clearing_remote.DiscountChargeCalculation( ac_type , amt ) ;
			
		} catch ( Exception exc ){
			
		   	exc.printStackTrace();
			
		}
		
		
		return discount_charge;
		
	}
	
	public AckObject getAckDetails ( int ack_no ){
		
		AckObject ack_obj = null ;
		
		try{
			
		
			//ack_obj = clearing_remote.getAcknowledgeAmount( ack_no );
			
		} catch (Exception e) {
					
			e.printStackTrace();
		}
		
		return ack_obj;
	}
	public AccountMasterObject getAccountInfoInd(String acctype ,int accno) throws RecordsNotFoundException,RemoteException
 	{
		AccountMasterObject accountmasterobject_am = clearing_remote.getAccountInfoInd(acctype,accno);
		return accountmasterobject_am;
 	}
	public ChequeDepositionObject[] getControlNoDetails( int ctrl_no,int i )
	{
		ChequeDepositionObject[] chq_obj = null ;
		try
		{
			chq_obj = clearing_remote.retrieveChequeDetails( ctrl_no,i );
		}
		catch ( Exception ee )
		{
			
			
		}
		
		
		return chq_obj;
	}
	
	
	public masterObject.clearing.BankMaster[] getBankDetails(int bcode,int city_cd,int bank_cd)
	{
		
		masterObject.clearing.BankMaster[] bm = null;
		
		try {
			
			bm = clearing_remote.getBankDetails(bcode, city_cd, bank_cd );
			
		} catch ( Exception e ) {

		
		}
		
		return bm;
	}
	
	
	public int deleteInwardData(long ctrlno) throws RecordNotInsertedException,RemoteException
 	{
		
		int int_value = clearing_remote.deleteInwardData(ctrlno);
		return int_value;
 	}
	
	public ShareCategoryObject[]  getShareCategories ( int ids, String module_code ){
		
		ShareCategoryObject[] shareCategoryObjects = null; 
		
		
		try {
			
			shareCategoryObjects = share_remote.getShareCategories(ids, module_code );  
			
			
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
		
		return shareCategoryObjects;
	}
	
	public double getLoanDetails ( double amt , int ln_type,int ln_no, String date ){
		
		double val = 0.0;
		
		try{
				
			val = clearing_remote.getLoanDetails(  amt , Integer.toString(ln_type), ln_no, "","",date );
			
		 } catch (Exception e) {
			 
			 e.printStackTrace();
		 }
		
		
		return val ;
	}
	
	public Object getCommission ( String db_ac_type , int db_ac_no ,String cr_ac_type , Double amt, String date, int type ) {
		
		String cash_value = "";
		int po_desc = 0;
		double po_comm = 0.0;
		
		try {
			
			cash_value = cash_remote.getCustSubCat(db_ac_type, Integer.toString(db_ac_no) ) ;
			
			AccSubCategoryObject[] cat_arr = common_remote.getAccSubCategories(0);
			   
			if ( cat_arr != null   ) {
				
				for(int i=0;i<cat_arr.length;i++)
				{
					if(cash_value.equalsIgnoreCase(cat_arr[i].getSubCategoryDesc()))
					{	
						po_desc=cat_arr[i].getSubCategoryCode();
						break;
					}
				}
				
				
			} else 
				  return 0.0;
			
			if  (  type == 0 ) {
				
				return po_desc;
			}
			
			
			po_comm = front_remote.getCommission( cr_ac_type,po_desc ,amt, date  );
			
		} catch (Exception e) {
				
			e.printStackTrace();
			return 0.0;
		}
		
		
		return po_comm;
		
	}
	
	public int storeChequeData  ( ChequeDepositionObject chq  ) {
		
		long i = 0;
	 
			try{
				
				i = clearing_remote.storeChequeData(chq);
				
			} catch (Exception e) {

			}
			
		 return (int)i ;
	}
	
	public boolean updateOutStationBounceCheque( ChequeDepositionObject chqobj )
	{	boolean cond=true;
		try{
			 cond =clearing_remote.updateOutStationBounceCheque(chqobj);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return cond;
	}
	
	
	
	public int deleteChequeData ( int ctrl_no ) {
		
		
		int result = 0;
		
		try {
			
			result = clearing_remote.deleteChequeData( ctrl_no );
			
		} catch ( Exception e  ) {
			
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Object getGenParam ( String param  )
	{
		
		Object  val = null ;
		
		
		try{
			
			val = clearing_remote.getGenParam(param);
			
		} catch ( Exception e ) {

			e.printStackTrace();
			return null;
		} 
		
		return val;
	}
	
	
	public ChequeDepositionObject getInwardData ( long ctrlno , int type   ){
		
		ChequeDepositionObject chq = null;
		
		try {
			
			chq = clearing_remote.getInwardData(ctrlno, type);
			
			
		} catch ( Exception e ){
			
			e.printStackTrace();
			return null;
			
		} 
		
		
		return chq ;
	}
	
	public String getGL(String acc_type,int gl_code)throws RemoteException,RecordsNotFoundException
 	{
		String val = clearing_remote.getGL(acc_type,gl_code);
			return val;
 	}
	
	public PayOrderObject getPayOrderInstrn(int chqno)throws RemoteException,RecordsNotFoundException 
	{
		PayOrderObject payorderobject = front_remote.getPayOrderInstrn(chqno);
		return payorderobject;
	}
	public  long storeInwardData( ChequeDepositionObject cd ) {
		
		long res = 0;
		
		try {
			
			res = clearing_remote.storeInwardData( cd );
			
		} catch ( Exception e ) {

			
			e.printStackTrace();
		}
		
		return res;
	}
	
	
	public ChequeDepositionObject[] getUnidentifiedCheques(int dest)throws RemoteException,RecordsNotFoundException
	{
		
		ChequeDepositionObject[] cd= clearing_remote.getUnidentifiedCheques(dest);
		return cd;
	}
	
	
	public ChequeDepositionObject[]  getUnidentifiedCheque ( int clg_bank , int send_to  )  {
		
		ChequeDepositionObject[]  chq =  null ;
		
		try {
			
			BranchObject[] br_obj =  clearing_remote.getBranchDetails(clg_bank);
			if(br_obj!=null)
			{	
			
			if ( br_obj[0].getBranchCode() != -1 )
			{
				
				chq = clearing_remote . getUnidentifiedCheques( send_to );
				
				ModuleObject[] array_module = getMainModule(4);
				
				if ( chq != null ) {
					
					for ( int i =0; i< chq.length; i++ ) { 
						
						System.out.println(   chq[i].getQdpDate()  +  " q date and q number  " + chq[i].getQdpNo() );
						
						//if(chq[i].getDeUser().equals("N") && ( chq[i].getMultiCredit() != null && chq[i].getMultiCredit().equals("F")) && chq[i].getDeTml().equals("S"))
						{	
							
							//chq[i].setCreditACType( "SB22" );
							
							/*for ( int j = 0; j < array_module.length ;  j++  ){
								
								
							
								if ( array_module[j].getModuleCode().equalsIgnoreCase( chq[i].getCreditACType() ) )  {
								
									System.out.println(  chq[i].getCreditACType()  + " --------------------" );
									
									
									
									System.out.println(  chq[i].getCreditACType()  + " --------------------" );
									
									break;
								}
							}  */
						
						}
						
						
						if ( chq[i].getClgType() != null && chq[i].getClgType().equalsIgnoreCase("O"))
							chq[i].setClgType("O/W");
						else if ( chq[i].getClgType() != null && chq[i].getClgType().equalsIgnoreCase("C"))
							chq[i].setClgType("Cr ECS");
						else if ( chq[i].getClgType() != null && chq[i].getClgType().equalsIgnoreCase("D"))
							chq[i].setClgType("Dr ECS");
						else if ( chq[i].getClgType() != null && chq[i].getClgType().equalsIgnoreCase("T"))
							chq[i].setClgType("Trf");
						else if ( chq[i].getClgType() != null && chq[i].getClgType().equalsIgnoreCase("I"))
							chq[i].setClgType("I/W");
					}
					
					
				} 
				
			}
			}
			else
			{
				
			}
			             
		} catch ( Exception exe  )  {
			
			exe.printStackTrace();
			return null ;
		}
		
		
		
		return chq;
	}
	
	
	public int identifyCheques( long[] ctrl ,String date , int clg_no , int sent_to,int type ) {
		
		int res = 0;
		
		try {
			
			res = clearing_remote . identifyCheques( ctrl , date ,  clg_no ,  sent_to, type );
			
		} catch ( Exception exc  ) {
			
			exc.printStackTrace();
			return 0;
		}
		
		
		return res;
	}

	public BranchObject[] getBranchDetails(int i) throws RemoteException,RecordsNotFoundException {
		BranchObject[] array_branchobject=clearing_remote.getBranchDetails(i);
		return array_branchobject;
	}

	public int ackEntry(String source, String clgType, String amount,String tml, String uid, String i) throws RemoteException {
		
		int int_ackno=clearing_remote.ackEntry(Integer.parseInt(source),clgType,Double.parseDouble(amount),tml,uid,Integer.parseInt(i));
		
		return int_ackno;    
	}

	public AckObject getAckDetails(int ackNumber, int i) throws RemoteException,RecordsNotFoundException{
		
		AckObject ackobject = clearing_remote.getAckDetails(ackNumber,0);
		
		return ackobject;
	}
	
	public int ClearingAdminInsertion(Vector value[],int type)throws RecordNotInsertedException,RemoteException
 	{
		int i=clearing_remote.ClearingAdminInsertion(value,type);
		return i;
 	}	
	public int deleteAck(int acntNum) throws RemoteException,RecordsNotFoundException {
		int i = clearing_remote.deleteAck(acntNum);
		return i;
	}
	
	
	public ClearingObject[] getDetails(int controlFromDate, int controlToDate,String query) throws RemoteException,RecordsNotFoundException ,SQLException{
		
		ClearingObject clearingobject[]=clearing_remote.getDetails(controlFromDate,controlToDate,query);
		System.out.println(clearingobject);
		
		return clearingobject;
	}
	
	public DiscountCharges[] retrieveDiscountCharges() throws RecordsNotFoundException,RemoteException
	{
		
		DiscountCharges discountCharges[]=clearing_remote.retrieveDiscountCharges();
		return discountCharges;
		
	}
	public ClearingObject[] getOutwardSummary(String date,int n ,int clg_no, int send_to,String str)throws RecordsNotFoundException,RemoteException
 	{
 	
		ClearingObject[] array_clearingobject = clearing_remote.getOutwardSummary(date,n,clg_no,send_to,str);
 	
		return array_clearingobject;
 	}
	
	
	public static String getSysDate() {
        Calendar c = Calendar.getInstance();
        try {
            return (Validations.checkDate(c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR)));
        	} 
        catch (DateFormatException e) 
        {
                e.printStackTrace();
        }
        return null;
    }
	public static String getSysTime(){
		java.util.Date now=new java.util.Date();
		DateFormat fmt=DateFormat.getTimeInstance(DateFormat.MEDIUM,Locale.UK);
		return fmt.format(now);
	}
	public ClearingObject[] getInwardStatement(String clgdate,int clgno,int source,String query) throws RecordsNotFoundException,RemoteException
	{	
		System.out.println("inside delegate");
		 ClearingObject[] array_clearingobject;
		array_clearingobject = clearing_remote.getInwardStatement(clgdate,clgno,source,query);
		return array_clearingobject;
		
	}
	public ClearingObject[] getOutwardStatement(String date,int clgno,String query)throws RecordsNotFoundException,RemoteException
 	{
		ClearingObject[] array_clearingobject;
		array_clearingobject=clearing_remote.getOutwardStatement(date,clgno,query);
		return array_clearingobject;
 	
 	}
	public ClearingObject[] getOutwardSlips(String date,int n,String query)throws RecordsNotFoundException,RemoteException
 	{
		ClearingObject[] array_clearingobject;
		array_clearingobject=clearing_remote.getOutwardSlips(date,n,query);
		return array_clearingobject;
		
 	}
	public ClearingObject[] getOutwardSummary(String date,int n,int clgno,String query)throws RecordsNotFoundException,RemoteException
 	{
		ClearingObject[] array_clearingobject;
		array_clearingobject=clearing_remote.getOutwardSummary(date,n,clgno,query);
		return array_clearingobject;
		
 	}
	
	

	public ChequeDepositionObject[] getIdentifiedCheques(String clr_date,
			int clr_no, int sent_to) throws RemoteException,RecordsNotFoundException{
		
		ChequeDepositionObject[] cd=clearing_remote.getIdentifiedCheques(clr_date,clr_no,sent_to);
		
		System.out.println("1...........Records not found");
		return cd;
	}
	
	public ModuleObject[] getMainModules(int a,String str)throws RemoteException,SQLException 
	{
		ModuleObject array_moduleobject[];
		array_moduleobject = common_remote.getMainModules(a,str);
		return array_moduleobject;
	
	}
	public String[] getTemplate(int stage,String acty) throws RecordsNotFoundException,RemoteException,SQLException
 	{
		 String array_string_templates[]=null;
		array_string_templates=clearing_remote.getTemplate(stage,acty);
		return array_string_templates;
 	
 	}
	public ClearingObject[] getBounceCheque(ClearingObject clear)throws RecordsNotFoundException,RemoteException,SQLException
	 {
	 	
		ClearingObject[] array_clearingobject;
		array_clearingobject=clearing_remote.getBounceCheque(clear);
		return array_clearingobject;
 	
 	}

	public AckObject[] getAckReconcillation(boolean b)  throws RemoteException,RecordsNotFoundException{
		
		AckObject[] array_ackobject=clearing_remote.getAckReconcillation(b);
		
		return array_ackobject;
	}
	public  ChequeDepositionObject[] getOutstationCheque(String date)throws RecordsNotFoundException,RemoteException,SQLException 
	{
		ChequeDepositionObject[] chq_obj = clearing_remote.getOutstationCheque(date);
		return chq_obj;
	
	}

	public ChequeDepositionObject getLateReturnDetail(int controlNum)throws RemoteException ,RecordsNotFoundException {
	
		ChequeDepositionObject[] chequedeposaitionobject=new ChequeDepositionObject[1];
		chequedeposaitionobject[0]=clearing_remote.getLateReturnDetail(controlNum);
		
		return chequedeposaitionobject[0];
	}

	public int lateReturnCheques(long ctrlno, String clgdate) throws RemoteException {
		
		int res = clearing_remote.lateReturnCheques(ctrlno, clgdate);
		return res;
	}

	public ChequeDepositionObject[] getChargeableCheques() throws RemoteException,RecordsNotFoundException{
		ChequeDepositionObject[] array_chequedepositionobject=clearing_remote.getChargeableCheques();
		return array_chequedepositionobject;
	}
 		
//Added on oct 04 2008
	
	
	public double reconcile(int ackNum) throws RemoteException,RecordsNotFoundException{
		
		double double_diffamount=clearing_remote.reconcile(ackNum);
		
		return double_diffamount;
	}
	
	public ChequeAckObject[] getChequeDetail(int ackNmuber) throws RemoteException,RecordsNotFoundException{
		
		ChequeAckObject chequeAckObject[]=clearing_remote.getChequeDetails(ackNmuber);
		
		return chequeAckObject;
	}
	
	public AckObject getAcknowledgeAmount(int ackNumber, String c_d) throws RemoteException,RecordsNotFoundException {
		
		AckObject ackObject= clearing_remote.getAcknowledgeAmount(ackNumber,c_d);
		
		return ackObject;
	}
	
	public Hashtable getColbalanePostInd(String acc_type,int acc_no,int prev_ctrl,String date)throws RecordsNotFoundException,RemoteException
 	{
		Hashtable result=clearing_remote.getColbalanePostInd(acc_type,acc_no,prev_ctrl,date);
		return result; 
 	}	
	public Vector getChequeNoDet(String acctype,int accno,int chqno)throws RecordsNotFoundException,RemoteException 
	{
		Vector chequeobject = front_remote.getChequeNoDet(acctype,accno,chqno);
		return chequeobject;
	}
	
	public int[] verifyInwardData(long ctrlno,String vetml,String veuser,String ve_date,String account_type,int account_no,int opt,String sysdate) throws RecordNotInsertedException,RemoteException
 	{
		int[] int_value = clearing_remote.verifyInwardData(ctrlno,vetml,veuser,ve_date,account_type,account_no,opt,sysdate);
		return int_value;
 	}
	public int outwardReturnBounce(long ctrl_no,int type,String ve_tml,String ve_user,String ve_date)throws RecordsNotFoundException,RemoteException
 	{
		int int_value=clearing_remote.outwardReturnBounce(ctrl_no,type,ve_tml,ve_user,ve_date);
		return int_value;
 	}
	
public int storeSelectivePosting(long controlNum, String uid,String tml, String sysTime, String sysDate, CompanyMaster[] cm) throws RemoteException,RecordsNotFoundException{
		
		int value=clearing_remote.storeSelectivePosting(controlNum, uid, tml, sysTime, sysDate, cm);
		
		return value;
	}

public ClearingObject getSelectiveDetails(long controlNum) throws RecordsNotFoundException,RemoteException {
	
	ClearingObject clearingObject=clearing_remote.getSelectiveDetails(controlNum);
	
	return clearingObject;
}

public ChequeDepositionObject[] getBouncedDetails(String clr_date,String tml,String uid,  String sysDate, int type) throws RemoteException,RecordsNotFoundException {
	ChequeDepositionObject[] chequeDepositionObject=null;
	System.out.println("inside delegate.....");
	try{
	 chequeDepositionObject= clearing_remote.getBouncedDetails(clr_date, tml, uid, sysDate, type);
	
	}
	catch(RecordsNotFoundException re){
	System.out.println("^^^^^########$$$$$$$$$$@@@@@@@@@@@!!!!!!!!!!!!!");
	}
	return chequeDepositionObject;
}
public int chequeWithdrawal(int intValue, String string, String string2,String sysDate)throws RemoteException,RecordsNotInsertedException 
{
	int i = clearing_remote.chequeWithdrawal(intValue,string, string2,sysDate);
	
	return i;
}

public ChequeDepositionObject[] retrieveChequeDetails(String accType, int ac_num) throws RemoteException,RecordsNotFoundException{
	
	ChequeDepositionObject chequeDepositionObject[]=clearing_remote.retrieveChequeDetails(accType, ac_num);
	
	return chequeDepositionObject;
}

public ChequeDepositionObject[] retrieveChequeDetails(int accType, int type) throws RemoteException,RecordsNotFoundException{
	
	ChequeDepositionObject chequeDepositionObject[]=clearing_remote.retrieveChequeDetails(accType, type);
	
	return chequeDepositionObject;
}

public int[] getClearingNo(String clr_date, int bankNum, int type)throws RemoteException,RecordsNotFoundException {
	
	int arr[] =clearing_remote.getClearingNo(clr_date,bankNum , type);
	
	return arr;
}

public AckObject getBundleAckDetails(int ackno,int ctrl)throws RecordsNotFoundException,RemoteException
{
	AckObject ackobject=clearing_remote.getBundleAckDetails(ackno,ctrl);
	return ackobject;
}

public int deleteControl(int ctrlno)throws RecordNotInsertedException,RemoteException 
	{
	
		int int_delete=clearing_remote.deleteControl(ctrlno);
	
	return int_delete;
	
	}




public long storeChequeData(ChequeAckObject cd)throws RecordNotInsertedException,RemoteException
	{
	 	long  long_ctrlno=clearing_remote.storeChequeData(cd);
		
	 	return long_ctrlno;
	}

public Vector[] clearingPosting(String clgdate,Vector clgnoarr,Vector bankcdarr,Vector ctrl_no_arr,String vtml,String vuser,String ve_date, String date) throws RecordNotInsertedException,RemoteException
	{
	
	Vector[] vector_acc_type_no = clearing_remote.clearingPosting(clgdate,clgnoarr,bankcdarr,ctrl_no_arr,vtml,vuser,ve_date,date);
		return vector_acc_type_no;
	}
public ChequeDepositionObject[] getIdentifiedCheques(int i, int j,String sysDate, int clr_bank, int clr_num)throws RemoteException,RecordsNotFoundException {

	ChequeDepositionObject[]  array_chequedepositionobject=clearing_remote.getIdentifiedCheques(i,j,sysDate,clr_bank, clr_num);
	
	return array_chequedepositionobject;
}

public int dispatchCheques(long[] ctrl_no, String tml, String user,String sysTime, String sysDate) throws RecordNotUpdatedException,RemoteException {
	
	int i=clearing_remote.dispatchCheques(ctrl_no,tml,user,sysTime,sysDate);
	
	return i;
}

public int chequeDiscount(ChequeDepositionObject[] cd,int i, String date )throws RecordNotInsertedException, RemoteException
{
	
	int j=clearing_remote.chequeDiscount(cd, i, date);
	
	return j;
}
public CustomerMasterObject customerDetails(String cid)throws RecordsNotFoundException,RemoteException,CustomerNotFoundException
{
	CustomerMasterObject custMasterObj=cust_remote.getCustomer(Integer.parseInt(cid));
	return custMasterObj;
}
//end of add
public ChequeAckObject[] ackChequeDetails(int ackno)throws RecordsNotFoundException,RemoteException
{
	ChequeAckObject array_chequeackobject[] = clearing_remote.ackChequeDetails(ackno);
	return array_chequeackobject;
}
//Added by BK
public boolean updatePostInd(String br_location,String date)throws RecordsNotFoundException,RemoteException
{
	boolean glposting = common_remote.updatePostInd(br_location,date);
	return glposting;
}
public int verifyAck(int ackno,int docsou,double amount,String tml,String user,String ve_date)throws RecordNotInsertedException,RemoteException
	{
		int int_verified=clearing_remote.verifyAck(ackno,docsou,amount,tml,user,ve_date);
		return int_verified;
	}
	
public int checkDailyStatus(String date,int type)throws RecordsNotFoundException,RemoteException
{
	int  status =common_remote.checkDailyStatus(date,type);
	return status;
}
public int onVerification(long ctrlno,String tml,String user,String ve_date)throws RecordsNotFoundException,RemoteException,RecordNotUpdatedException
{
	int i= clearing_remote.onVerification(ctrlno,tml,user,ve_date);
	return i;
}

public int createPayOrder( ChequeDepositionObject co) throws RemoteException,RecordsNotFoundException
{
	int pay_order_no = clearing_remote.createPayOrder(co);
	return pay_order_no;
}
public CompanyMaster[] retrieveChqDtls(int ctrl_no,int type)throws RecordsNotFoundException,RemoteException,CustomerNotFoundException
{
	CompanyMaster[] coMasterObj=clearing_remote.retrieveChqDtls(ctrl_no,type);
	return coMasterObj;
}
public void mailingCharge(ChequeDepositionObject[] cd)throws RemoteException,RecordsNotFoundException
{
	clearing_remote.mailingCharge(cd);
	
}
public boolean verifyOutStationCheque( ChequeDepositionObject cd)throws RecordsNotFoundException,RemoteException 
{
	boolean boo=clearing_remote.verifyOutStationCheque(cd);
	return boo;
	
}
public long verifyDebitECS(ChequeDepositionObject cd)  throws RecordNotInsertedException,RemoteException
{
	long long_ctrl_no=clearing_remote.verifyDebitECS(cd);
	return long_ctrl_no;
}
public long verifyCreditECS(ChequeDepositionObject cd)  throws RecordNotInsertedException
{
	long long_ctrl_no=delegate.verifyCreditECS(cd);
	return long_ctrl_no;
}
public int storeChequeData(CompanyMaster cm[],int ctrlno)throws RemoteException
{
	int j=clearing_remote.storeChequeData(cm,ctrlno );
return j;
}
	
public ChequeDepositionObject[] getHelpData(int i)throws RemoteException,RecordsNotFoundException

{
	ChequeDepositionObject[] cd=clearing_remote.getHelpData(i);
	return cd;
}
public long storeInwardBouncedData(ChequeDepositionObject cd)throws RemoteException,RecordNotInsertedException
{
	
	long ctr_no=clearing_remote.storeInwardBouncedData(cd);
	
	return ctr_no;
}
public int setChequeNo(ChequeDepositionObject cd)throws RecordNotInsertedException,RemoteException
{
	
	int kk=clearing_remote.setChequeNo(cd);	
	return kk;
}

public String[][] getClgAdminTable(String table_name) throws RecordsNotFoundException,RemoteException
{
	String [][] str_arr=clearing_remote.getClgAdminTable(table_name);
	
	return str_arr;
}
public CompanyMaster[] retrieveCompanyDetails(int ctrlno)throws RecordsNotFoundException,RemoteException
{
	CompanyMaster array_companymaster[]=clearing_remote.retrieveCompanyDetails(ctrlno);
	return array_companymaster;
}
public ReasonMaster[] getReasons() throws RecordsNotFoundException,RemoteException
{
	ReasonMaster array_reasonmaster[]=clearing_remote.getReasons();

return array_reasonmaster;
}
public ReasonLink[] getLinkReasons() throws RecordsNotFoundException,RemoteException
{
	ReasonLink array_reasonlink[]=clearing_remote.getLinkReasons();
return array_reasonlink;
}
public BounceFineObject[] retrieveBounceFine() throws RecordsNotFoundException,RemoteException
{
	BounceFineObject[] array_bouncefineobject=clearing_remote.retrieveBounceFine();
return array_bouncefineobject;
}

public Object getMailChg(String column)throws RemoteException,RecordsNotFoundException
{
	Object obj =clearing_remote.getMailChg("mail_chg");
return obj;
}

public boolean UpdateGenParam(int i)throws RecordsNotInsertedException,RemoteException,RecordNotUpdatedException 
{
	boolean tr=clearing_remote.upDateGenParam(i);
	return tr; 
}
public int addAccountTransactionDebitEntry(ChequeDepositionObject cd) throws RemoteException, RecordNotInsertedException
{
	int j=clearing_remote.addAccountTransactionDebitEntry(cd);
return j;
}

}
