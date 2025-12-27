package com.scb.designPatterns;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Calendar;

import javax.ejb.CreateException;
import javax.swing.JOptionPane;

import termDepositServer.TermDepositHome;
import termDepositServer.TermDepositRemote;

import lockerServer.LockersHome;
import lockerServer.LockersRemote;
import masterObject.customer.CustomerMasterObject;
import masterObject.general.AccountObject;
import masterObject.general.Address;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.lockers.LockerDetailsObject;
import masterObject.lockers.LockerMasterObject;
import masterObject.lockers.LockerTransObject;
import masterObject.termDeposit.DepositMasterObject;

import com.scb.designPatterns.exceptions.ServiceLocatorException;
import commonServer.CommonHome;
import commonServer.CommonRemote;

import customerServer.CustomerHome;
import customerServer.CustomerRemote;
import exceptions.AccountNotFoundException;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import exceptions.RecordNotInsertedException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;
import general.Validations;

public class LockerDelegate 
{
    private LockersHome    lkHome;
    private LockersRemote  lkRemote;
    private CommonHome     cmnhome;
    private CommonRemote   cmnremote;
    private CustomerHome   custHome; 
    private CustomerRemote custRemote;
    private TermDepositHome depositHome;
    private TermDepositRemote depositRemote;
    CustomerMasterObject cmobject;   
    ModuleObject array_moduleobject_lockers[]=null;
    
    public LockerDelegate() throws RemoteException, CreateException, ServiceLocatorException {
            try {
           this.cmnhome =(CommonHome)ServiceLocator.getInstance().getRemoteHome("COMMONWEB",CommonHome.class);
           this.cmnremote= cmnhome.create();
           
           this.custHome =(CustomerHome)ServiceLocator.getInstance().getRemoteHome("CUSTOMERWEB",CustomerHome.class);
           this.custRemote= custHome.create();
           
           this.depositHome=(TermDepositHome)ServiceLocator.getInstance().getRemoteHome("TERMDEPOSITWEB", TermDepositHome.class);
           this.depositRemote=depositHome.create();
           
           this.lkHome =(LockersHome)ServiceLocator.getInstance().getRemoteHome("LOCKERSWEB",LockersHome.class);
           this.lkRemote= lkHome.create();
           System.out.println(lkRemote);     
            }
           catch(RemoteException e){
                throw e;
            }
           catch(CreateException e){
               throw e;
            }
           catch(ServiceLocatorException e){
               throw e;
            }


    }
public LockerDetailsObject getLockerDetails(String lkType){
        LockerDetailsObject lkdobj=null;
        LockerDetailsObject[] lkObj=null;
        try{

        int lkNum=3;    
        lkdobj =new LockerDetailsObject();
        System.out.println("i am here="+lkRemote);
        lkObj=lkRemote.getLockers(lkNum);
        System.out.println(lkObj.length);    
        lkdobj=lkRemote.getLockerDetails(lkType);
       }
        catch(Exception ex){
          ex.printStackTrace();  
        }
        return lkdobj;
 }
    public ModuleObject[] getMainModules(String abbr){

        ModuleObject[]  modobj=null;
        try{
        modobj = cmnremote.getMainModules(2,abbr);
       }catch(Exception e){ e.printStackTrace();}

     return modobj;
    }

    public LockerMasterObject getLockerMaster(int acnum,int type) throws RemoteException{
         LockerMasterObject lockermasterobject=null;
         lockermasterobject=lkRemote.getLockerMaster("1009001",acnum,type,ClearingDelegate.getSysDate());
         return lockermasterobject;
    }
    
    public AccountObject getAccount(String trn_mode,String acctype,int accno,String date) throws RemoteException
    {
    	AccountObject accountObject=cmnremote.getAccount(trn_mode, acctype, accno, date);
    	return accountObject;
    }
public String[] getLockersTypes()  throws Exception{
	   String[] str_locker_types=null;
	   str_locker_types=lkRemote.getLockersTypes();
	   return str_locker_types;
}

public CustomerMasterObject getCustomer(int cid)throws RemoteException,CustomerNotFoundException {
	
	
	System.out.println("Converted cid"+cid);
	System.out.println(cmobject);
	
	cmobject=custRemote.getCustomer(cid);
	
	System.out.println("********"+cmobject);
	
	return cmobject;
}
  



public AccountObject getShareAccount(int cid) throws RemoteException ,RecordNotUpdatedException{
	
	System.out.println("My cid in delegate"+cid);
	
	AccountObject accountobject=lkRemote.getShareAccount(cid);
	
	
	
	return accountobject;
	
	
}
public String  getFutureMonthDate(String allotDate,String months) throws RemoteException{
	System.out.println("alllll"+allotDate+"int value"+months);
	String expiryMonths=cmnremote.getFutureMonthDate(allotDate,Integer.parseInt(months));
	System.out.println("from delegate gotta val "+expiryMonths);
	return expiryMonths;
}

public String[]  getDaysRent(String allotdate,String days,String months,String expirydate,String lkacType)throws RemoteException{
	
	String[] rentExpirydate={"rent","calcExpirydate"};
	
	int total=Integer.parseInt(days)+Integer.parseInt(months)*30;
	
	System.out.println("TOOOOOt"+total);
	
	 array_moduleobject_lockers=cmnremote.getMainModules(2,"1009000");
	 System.out.println("i guess my val is"+array_moduleobject_lockers.length);
	 for(int i=0;i<array_moduleobject_lockers.length;i++)
     {
         if(array_moduleobject_lockers[i].getModuleAbbrv().equals("LK"))
         {
        	 
             int lk_min_period = array_moduleobject_lockers[i].getMinPeriod();
             //ship.......1/4/2006
             int lk_max_period = array_moduleobject_lockers[i].getMaxRenewalDays();
             
             if((total >= lk_min_period) && (total <= lk_max_period))
             {
                 int int_total_days_month=Validations.dayCompare(allotdate,expirydate);
                 System.out.println("int_total_days_month = "+int_total_days_month);
                 System.out.println("**LOCKERTYPE**"+lkacType);
                 
                 double double_rent=lkRemote.getRent(lkacType,Integer.parseInt(days)+int_total_days_month,1,getSysDate());
                 
                 
                 System.out.println("total = "+total);
                 System.out.println("int_total_days_month = "+int_total_days_month);
                 System.out.println("double_rent = "+double_rent);
                 
                 if(double_rent!=0)
                 {
                     System.out.println("inside double_rent != "+double_rent);
                     //'m convertng double into string
                      
                     rentExpirydate[0]=String.valueOf(double_rent);
                     //rentExpirydate[1]=cmnremote.getFutureDayDate(allotdate,int_total_days_month+Integer.parseInt(days));
                     rentExpirydate[1]=cmnremote.getFutureDayDate(allotdate,total);
                     
                     return rentExpirydate;
                 }
                 
             }
             
         }
     }
	return null;
	
	
	
}
public static String getSysDate() {
        Calendar c = Calendar.getInstance();
        try {
        return (Validations.checkDate(c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR)));
        } 
        catch (DateFormatException e) {
                e.printStackTrace();
        }
        return null;
   
   
}
public Address getAddress(int cid,int addr_type)throws RemoteException 
{
	Address address=cmnremote.getAddress(cid,addr_type);
	return address;
}
public int deleteLockerTypeParameter(String combo_types) throws RemoteException{
	
	int delete=lkRemote.deleteLockerTypeParameter(combo_types);
	
	System.out.println("I'm..?..from Delegate my del val is"+delete);
	
	return delete;
}
public boolean addLockerTypeParameter(LockerDetailsObject lkdobj) throws RemoteException{
	System.out.println("while adding 'm in delegate");
	boolean flag;
	flag=lkRemote.addLockerTypeParameter(lkdobj);
	if(flag==true)
	{
		System.out.println("successfully inserted");
	}
	else{
		System.out.println("false");
	}
	
	return flag;
}
public LockerDetailsObject[] getLockerDetails()throws RemoteException
{
	LockerDetailsObject[] loc=lkRemote.getLockerDetails();
	return loc;
}
public NomineeObject[] getNominee(int i)throws RemoteException
{
	NomineeObject[] nomineeObjects=cmnremote.getNominee(i);
	return nomineeObjects;
}
public SignatureInstructionObject[] getSignatureDetails(int accnum,String acctype)throws RemoteException
{
		SignatureInstructionObject[] sObject=cmnremote.getSignatureDetails(accnum, acctype);
	return sObject;
}
public SignatureInstructionObject[] getSignatureDetails(int cid) throws RemoteException
{
	SignatureInstructionObject[] sObject=cmnremote.getSignatureDetails(cid);
	return sObject;
}
public String[] getDetailsElements(){
	
    String[] combonames={"Personal","Introducer","Nominee","JointHolders","SignatureIns","Deposit","Receipt","Locker"};
    return combonames;
}
public boolean updatePostInd(String brname,String date)throws RemoteException
{
	boolean i=cmnremote.updatePostInd(brname,date);
	return i;
}
public AccountObject getIntroducerAcntDetails(String intacType, int intacNum)throws RemoteException {
	
	AccountObject accountobject= cmnremote.getAccount(null,intacType,intacNum,getSysDate());
	return accountobject;
}
public int surrender(String txt_acType, String txt_rentUpto,LockerTransObject lockertransobject,int type)throws RemoteException, DateFormatException {
	
	array_moduleobject_lockers=cmnremote.getMainModules(2,"1009000");
	System.out.println("Jeez#####");
	int grace_period = 0;
	    
    for(int k=0;k<array_moduleobject_lockers.length;k++)
    {
        if(txt_acType.equals(array_moduleobject_lockers[k].getModuleAbbrv()))
        {
            grace_period = array_moduleobject_lockers[k].getMaxRenewalCount();
            System.out.println("Jeez#####"+grace_period);
            break;
        }
    }System.out.println("Jeez#####"+txt_rentUpto);
    String date = Validations.addDays(txt_rentUpto,grace_period);
    System.out.println("Jeez#####4");
    System.out.println("lbl_rent_upto = "+txt_rentUpto);
    System.out.println("grace period = "+grace_period);
    System.out.println("date = "+date);
    
	//if(Validations.checkDateValid(lbl_rent_upto.getText(),MainScreen.head.getSysDate())==-1)
    if(Validations.checkDateValid(date,getSysDate())==-1)
		return 3;
    else
	{
		    
		    if(lkRemote.storeLockerTransaction(lockertransobject,type))
		    {
		    	return 1;
		     }
		    else
		    	return 2;
	
	}
}


//for Locker Extension


public String[] getExtendRent(String days,String months,String lockernum,String lockerType,String presentDate) throws RemoteException,SQLException {

	array_moduleobject_lockers=cmnremote.getMainModules(2,"1009000");
	//LockerMasterObject lockermasterobject=lkRemote.getLockerMaster("1009000",Integer.parseInt(lockernum),3,getSysDate());
	String[] rentFutureDate={"rent","calcExpirydate"};  
	
	int total=Integer.parseInt(days)+Integer.parseInt(months)*30;
        
        for(int i=0;i<array_moduleobject_lockers.length;i++)
        {
            if(array_moduleobject_lockers[i].getModuleAbbrv().equals("LK"))
            {
                int lk_min_period = array_moduleobject_lockers[i].getMinPeriod();
                int lk_max_period = array_moduleobject_lockers[i].getMaxRenewalDays();
                
                if((total >= lk_min_period) && (total <= lk_max_period))
                {   System.out.println("TOOOTOOO"+total+"MINNMINN"+lk_min_period+"MAXXXMAXX"+lk_max_period);
                    String mt_dt = cmnremote.getFutureDayDate(presentDate,total);
                    System.out.println("future date = "+mt_dt);
                    
                    //ship......04/04/2006
                    if(Validations.checkDateValid(mt_dt,getSysDate())==1)
                    {
                        System.out.println("%%%%here is your maturity date"+mt_dt);
                        
                        //Double double_rent=lkRemote.getRent(new StringTokenizer(lockermasterobject.getLockerType()).nextToken(),total,1,getSysDate());
                        double double_rent=lkRemote.getRent(lockerType,total,1,getSysDate());
                        System.out.println("total = "+total);
                        System.out.println("double_rent = "+double_rent);
                        
                        if(double_rent!=0)
                        {
                            System.out.println("inside double_rent != 0");
                            
                           //here u got ur rent n dnt kow d followin line
                            //obj_locker_issue.txt_period_rent.setText(String.valueOf(double_rent));
                            System.out.println("Whew!!!!Got it"+double_rent+"Maturity date is"+mt_dt);
                            rentFutureDate[0]=String.valueOf(double_rent);
                            rentFutureDate[1]=mt_dt;
                            return rentFutureDate;
                            //lbl_rent_amt.setText(String.valueOf(double_rent));
                            
                            /*if(lockermasterobject.getTransAcNo()!=0)
                            {
                                obj_receiptdetails.setPaymentType(lockermasterobject.getRentBy());
                                obj_receiptdetails.setPaymentDet(lockermasterobject.getTransAcType(),lockermasterobject.getTransAcNo());
                            }*/
                            /////////////
                        }
                        
                    }
                    /*else
                    {
                        txt_extend_mths.setText("");
                        txt_extend_days.setText("");
                        txt_extend_mths.requestFocus();
                        JOptionPane.showMessageDialog(null,"Maturity date should be more than or equal to todays date");
                    }*/
                }
                /*else
                {     
                    txt_extend_mths.setEnabled(true);
                    txt_extend_days.setEnabled(true);
                    txt_extend_mths.requestFocus();
                    JOptionPane.showMessageDialog(null,"Locker Period should be b/n "+lk_min_period+" and "+lk_max_period+" days");
                    txt_extend_mths.setText("");
                    txt_extend_days.setText("");
                }*/


            }
            
        }
		return null;
        
    
}

public LockerMasterObject[] getAutoExtnLockers(String date) throws RemoteException,RecordsNotFoundException {
	LockerMasterObject array_lockermasterobject[];
	
	array_lockermasterobject = lkRemote.getAutoExtnLockers(date);
	
	return array_lockermasterobject;
}

public DepositMasterObject getDepositMaster(String actype,int acnum)throws RemoteException,RecordsNotFoundException,AccountNotFoundException,NullPointerException {
	
	 
	DepositMasterObject depositMasterObject=depositRemote.getDepositMaster(actype,acnum);
	return depositMasterObject;
}
public void fetchAccountDetails(String acnt, String lkType) {
	int req_mths = 0,req_days = 0;	
	int acntNum=Integer.parseInt(acnt);
	ModuleObject array_moduleobject_transfers[]=null;
	SignatureInstructionObject array_siginsobject[] = null;
	
	try
    {System.out.println("FRM &&&& DELEGATE");
		LockerMasterObject lockermasterobject = lkRemote.getLockerMaster(lkType,Integer.parseInt(acnt),5,getSysDate());
		array_moduleobject_transfers = cmnremote.getMainModules(2,"1002000,1007000,1014000,1015000");
		
        if(lockermasterobject!=null)
        {
            
            
            
            int locker_max_renewal_period = 0;
            
            for(int m=0;m<array_moduleobject_lockers.length;m++)
            {
                if(array_moduleobject_lockers[m].getModuleCode().equals(lkType))
                    locker_max_renewal_period = array_moduleobject_lockers[m].getMaxRenewalCount();
            }
            
            if(Validations.dayCompare(lockermasterobject.getMatDate(),getSysDate())>locker_max_renewal_period)
            {
            	System.out.println("-------LOcker Expired******");
            }
            else
            {
                if(lockermasterobject.getFreezeInd().equals("F"))
                {
                    for(int i=0;i<array_moduleobject_lockers.length;i++)
                    {
                        if(array_moduleobject_lockers[i].getModuleCode().equals(lockermasterobject.getLockerAcType()))
                        {   System.out.println("m here in 394 line");
                           // lbl_lk_actype.setText(array_moduleobject_lockers[i].getModuleAbbrv());
                            break;
                        }
                    }
                    
                   // lbl_lk_acno.setText(String.valueOf(lockermasterobject.getLockerAcNo()));
                    
                    if(lockermasterobject.getLockerType().equals("L"))
                    	System.out.println("Corresponding accnt type is Large");
                        //lbl_lk_type.setText("LARGE");
                    if(lockermasterobject.getLockerType().equals("M"))
                        //lbl_lk_type.setText("MEDIUM");
                    	System.out.println("Corresponding accnt type is medium");
                    if(lockermasterobject.getLockerType().equals("S"))
                        //lbl_lk_type.setText("SMALL");
                    	System.out.println("Corresponding accnt type is small");
               
                    
                    
                   // lbl_lk_no.setText(String.valueOf(lockermasterobject.getLockerNo()));
                    
                    for(int i=0;i<array_moduleobject_transfers.length;i++)
                    {
                        if(array_moduleobject_transfers[i].getModuleCode().equals(lockermasterobject.getTransAcType()))
                        {	System.out.println("m here in 419 line");
                           // lbl_trf_actype.setText(array_moduleobject_transfers[i].getModuleAbbrv());
                            break;
                        }
                    }
                    
                   // lbl_trf_acno.setText(String.valueOf(lockermasterobject.getTransAcNo()));
                   
                   /* obj_personal_locker.setCid(String.valueOf(lockermasterobject.getCid()));
                    obj_personal_locker.showCustomer(lockermasterobject.getCid());
                    */
                    if(lockermasterobject.getSigObj()!=null)
                    {
                        /*obj_signaturedetails_locker.addCoBorrower(lockermasterobject.getSigObj(),0);
                        obj_signaturedetails_locker.setEnabled(false);*/
                    }
                    else
                    {
                        System.out.println("Error in getting SignatureDetails of Locker account");
                        //JOptionPane.showMessageDialog(null,"Error in getting SignatureDetails of Locker account");
                        //clearForm();
                    }
                    
                    try
                    {
                        array_siginsobject = cmnremote.getSignatureDetails(lockermasterobject.getTransAcNo(),lockermasterobject.getTransAcType());
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                                        
                    if(array_siginsobject!=null)
                    {
                       /* obj_signaturedetails_transfer.addCoBorrower(array_siginsobject,0);
                        obj_signaturedetails_transfer.setEnabled(false);
                  */  }
                    else
                    {
                    	System.out.println("Error in getting SignatureDetails of Transfer account");
                   
                      //  JOptionPane.showMessageDialog(null,"Error in getting SignatureDetails of Transfer account");
                        //clearForm();
                    }
                    
                    req_mths = lockermasterobject.getReqdMonths();
                    req_days = lockermasterobject.getRequiredDays();
                    
                    System.out.println("req_mths = "+req_mths);
                    System.out.println("req_days = "+req_days);
                }
                else if(lockermasterobject.getFreezeInd().equals("T"))
                {
                    /*if(button_view.isEnabled())
                        button_view.requestFocus();
                    else
                        button_clear.requestFocus();*/
                    
                   
                    JOptionPane.showMessageDialog(null,"Locker Ac No is Freezed");
                    //clearForm(); 
                }
            }
        }
        else
        {    System.out.println("Error : No Records Found");
            //table.setRowSelectionInterval(n+1,n+1);
            // JOptionPane.showMessageDialog(null,"Error : No Records Found");
            //clearForm();
            /////////
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
}
public boolean storeLockerTransaction(LockerTransObject lockerTransObject, int i) throws RemoteException {
	
	boolean boolean_stored=lkRemote.storeLockerTransaction(lockerTransObject,i);
	
	return boolean_stored;
}

public LockerDetailsObject[] getCabinStructure(String lktype) throws RemoteException {
	 LockerDetailsObject array_lockerparamobject_cabins[]=lkRemote.getCabinStructure(lktype);
	return array_lockerparamobject_cabins;
}
public  int storeLockers(LockerDetailsObject lockedetailsobject_cabstructure[],LockerDetailsObject lockerDetailsObject_lockers[])throws RemoteException,RecordNotInsertedException 
{
	int	int_cabin_no=lkRemote.storeLockers(lockedetailsobject_cabstructure,lockerDetailsObject_lockers);
	return int_cabin_no;
}
public boolean deleteLockerTransaction(LockerTransObject lockertransobject,	int i) throws RemoteException,RecordNotUpdatedException {
	System.out.println("int type is@@@@@@@@@"+i);
	boolean deleted = lkRemote.deleteLockerTransaction(lockertransobject,i);
	System.out.println("------------"+deleted);
	return deleted;
}
public LockerMasterObject[] getLockerReport(String fromDate,String toDate,int flag) throws RemoteException {
	
	LockerMasterObject[] array_lockermasterobject=lkRemote.getLockerReport(null,toDate,flag,null);
	
	return array_lockermasterobject;
}
public LockerTransObject[] getLockerTransaction(String acntNum,String modCode,String fromDate, String toDate, int flag, String string_qry) throws RemoteException {
	
	 LockerTransObject array_lockertransobject[]=array_lockertransobject=lkRemote.getLockerTransaction(acntNum,modCode,fromDate,toDate,flag,string_qry);	
	return array_lockertransobject;
}
public LockerMasterObject[] getLockerReport(String fromDate, String toDate,
		int flag, String string_qry) throws RemoteException {
	
	LockerMasterObject array_lockermasterobject[] = lkRemote.getLockerReport(fromDate,toDate,flag,string_qry);
	
		
		return array_lockermasterobject;
}
public LockerMasterObject[] getRentDueReport(String toDate, int flag,String string_qry) throws RemoteException{

	LockerMasterObject[] array_lockermasterobject=lkRemote.getRentDueReport(toDate,flag,string_qry);
	return array_lockermasterobject;
	
}
public int[] getDistinctCabs(String lkTypes) throws RemoteException {
	
	int arr[]= lkRemote.getDistinctCabs(lkTypes);
	for(int i=0;i<arr.length;i++){
		System.out.println("***********&&&&&&&&&************"+arr[i]);
	}
	return arr;
}


public LockerDetailsObject[] getLockerCabStructure(int cabNum) throws RemoteException{
	
	LockerDetailsObject[] locDetailsObjects = lkRemote.getLockerCabStructure(cabNum);
	
	System.out.println("-----I gotta value- frm cab struc---------"+locDetailsObjects.length);
	
	for(int i=0;i<locDetailsObjects.length;i++){
		
	System.out.println("----"+locDetailsObjects[i].getCabNo()+"----"+locDetailsObjects[i].getCols()+"----"+locDetailsObjects[i].getRowNum());

	}
	return locDetailsObjects;
}

public LockerDetailsObject[] getLockers(int cabNum) throws RemoteException {
	
	LockerDetailsObject[] lockerDetailsObjects = lkRemote.getLockers(cabNum);	
	
	return lockerDetailsObjects;
}

public LockerDetailsObject[] getLockerTypes() throws RemoteException {
	
	LockerDetailsObject[] lockerDetailsObjects= lkRemote.getLockerTypes();
	
	return lockerDetailsObjects;
}
public boolean updateLocker(LockerMasterObject lockermasterobject, int i) throws RemoteException {
	
	boolean flag=lkRemote.updateLocker(lockermasterobject, i);
	
	System.out.println("----______----"+flag);
	
	return flag;
}
public int checkDailyStatus(String sysDate, int i) throws RemoteException{
	 int proceed = cmnremote.checkDailyStatus(sysDate,i);
	 return proceed;
}
public ModuleObject[] getMainModules(int i) throws RemoteException 
{
	ModuleObject[] array_moduleobject_lockers=null;
	if(i==1)
	{
		array_moduleobject_lockers= cmnremote.getMainModules(2,"1009000");
	}
	else if(i==2)
	{
		array_moduleobject_lockers = cmnremote.getMainModules(2, "1003000,1004000,1005000" );
	}
	
	return array_moduleobject_lockers;
}

public LockerDetailsObject[] getLockerRateParameter() throws RemoteException{
	
	LockerDetailsObject[] lockerRateParameterObjects=lkRemote.getLockerRateParameter();
	
	return lockerRateParameterObjects;
}

public int storeLockerMaster(LockerMasterObject lockermasterobject, int int_verify) throws RemoteException {

	System.out.println(lockermasterobject.getRentBy()+"?loclockermasterobject.get--?"+lockermasterobject.getTransAcNo());
	
	int int_locker_no=lkRemote.storeLockerMaster(lockermasterobject, int_verify);
	
	return int_locker_no;
}

//method for Rate definition
public void addLockerRateParameter(LockerDetailsObject[] array_lockerDetailsObject, int i) throws RemoteException,RecordNotUpdatedException{
	
	lkRemote.addLockerRateParameter(array_lockerDetailsObject,i);
	System.out.println("---^^----2");
	
}
public Object[][] getHelpWindowDetails() throws RemoteException {
	String[] colnames=new String[3];
	colnames[1]="ac_type";
	colnames[2]="ac_no";
	Object obj[][]=cmnremote.getHelpDetails("LockerTransaction", colnames, "ac_type='1009001' and rent_by is null and rent_amt is null and cd_ind is null and time_in is null and time_out is null and op_date='"+getSysDate()+"' and trn_seq>1 and de_user is not null and de_tml is not null and de_date is not null and ve_user is null and ve_tml is null and ve_date is null order by ac_no");
	if(obj!=null)
	{
	System.out.println("------------>"+obj.length);
	}
	return obj;
}
public void deleteLockerAccount(LockerMasterObject lockermasterobject, int i) throws RemoteException, RecordNotUpdatedException{
	lkRemote.deleteLockerAccount(lockermasterobject, i);
	
}
public String getFutureDayDate(String matDate, int lk_min_period) throws RemoteException {
	
	System.out.println("1b4 goin chkin values"+matDate+"--"+lk_min_period);
	String mt_dt = cmnremote.getFutureDayDate(matDate,lk_min_period);
	
	System.out.println("in delegate mat amnt==> "+mt_dt);
	
	return mt_dt;
}
public double getRent(String string, int total_days, int cat, String sysDate) throws RemoteException {
	
	System.out.println("2b4 goin chkin values"+string+"--"+total_days+"--"+sysDate);
	
	double locker_rent=lkRemote.getRent(string,total_days,cat,sysDate);
	
	System.out.println("in delegate locker rent amnt==> "+locker_rent);
	return locker_rent;
}
public AccountObject getAccount(String transferAcntType,int tranferAcntNum, String sysDate) throws RemoteException {
	
AccountObject accountObject=cmnremote.getAccount(null, transferAcntType, tranferAcntNum, sysDate);
	
	return accountObject;
}
public int getNoOfMonths(String matDate, String rentUpto) throws RemoteException {
	 
	int total_no_months=cmnremote.getNoOfMonths(matDate,rentUpto);
	 
	return total_no_months;
}
public String getFutureMonthDate(String matDate, int total_no_months)throws RemoteException {

	   String exp_date_month=cmnremote.getFutureMonthDate(matDate,total_no_months);
	
	return exp_date_month;
}
public int getDaysFromTwoDate(String exp_date_month, String rentUpto) throws RemoteException{
	
	int total_no_days=cmnremote.getDaysFromTwoDate(exp_date_month,rentUpto);
	
	return total_no_days;
}
public boolean updateLockerMaster(LockerMasterObject lockermasterobject) throws RemoteException {
	
	return (lkRemote.updateLockerMaster(lockermasterobject));
	
	 
}



}
   