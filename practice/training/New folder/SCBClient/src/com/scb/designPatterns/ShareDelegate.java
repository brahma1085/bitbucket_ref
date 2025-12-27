package com.scb.designPatterns;

import java.lang.reflect.UndeclaredThrowableException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.CreateException;

import masterObject.customer.CustomerMasterObject;
import masterObject.general.AccountObject;
import masterObject.general.BranchObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.share.DirectorMasterObject;
import masterObject.share.DividendObject;
import masterObject.share.DividendRateObject;
import masterObject.share.ShareCategoryObject;
import masterObject.share.ShareMasterObject;
import masterObject.share.ShareParamObject;
import masterObject.share.ShareReportObject;

import org.apache.log4j.Logger;

import shareServer.ShareHome;
import shareServer.ShareRemote;

import com.scb.common.log4j.LogDetails;
import com.scb.designPatterns.exceptions.ServiceLocatorException;
import commonServer.CommonHome;
import commonServer.CommonRemote;

import customerServer.CustomerHome;
import customerServer.CustomerRemote;
import exceptions.AccountNotFoundException;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import exceptions.RecordsNotFoundException;
import general.Validations;

public class ShareDelegate{
	private ShareHome sh_home;
	private ShareRemote sh_remote;
	private CommonHome comHome;
    private CommonRemote comRemote;
    private ModuleObject[] comboElements=null;
    private ShareCategoryObject sharecatobj[];
    private BranchObject[] branch_obj;
    private CustomerHome cust_home;
    private CustomerRemote cust_remote;
    public String [] sh_type;
    final Logger logger=LogDetails.getInstance().getLoggerObject("ShareDelegate");
    
	public CommonRemote getComRemote(){
        return this.comRemote;
    }
	
	public ShareDelegate() throws RemoteException,CreateException,ServiceLocatorException{
		try{
			this.sh_home=(ShareHome)ServiceLocator.getInstance().getRemoteHome("SHAREWEB", ShareHome.class);
			this.sh_remote=sh_home.create();
			getCommonRemote();
			this.cust_home=(CustomerHome)ServiceLocator.getInstance().getRemoteHome("CUSTOMERWEB", CustomerHome.class);
			this.cust_remote=cust_home.create();
		}catch (RemoteException e){
            throw e;
        }catch (CreateException ex) {
            throw ex;
        }catch(ServiceLocatorException se){
            throw se;
        }
	}
	
	public ShareDelegate(String id)throws RemoteException,CreateException,ServiceLocatorException{
		 reconnect(id);// reconnect to the session bean for the given id
	}
	
	public void reconnect(String id) throws ServiceLocatorException{
		 try{
			 sh_remote = (ShareRemote) ServiceLocator.getService(id);
		 }catch(ServiceLocatorException ex){
			 throw ex;
		 }
	}
	
	public String getID()throws RemoteException,CreateException,ServiceLocatorException{
	     try{
	    	 return ServiceLocator.getId(sh_remote);
	        }catch (ServiceLocatorException e){
	            throw e;
	        }
	}
	
	private CommonRemote getCommonRemote()throws RemoteException,CreateException,ServiceLocatorException{
        this.comHome=(CommonHome)ServiceLocator.getInstance().getRemoteHome("COMMONWEB",CommonHome.class);
        this.comRemote=comHome.create();
        return comRemote;
    }
	
	public ModuleObject[] getmodcode() throws RemoteException{//to fetch the module code
		comboElements=comRemote.getMainModules(2,"1001000");			
		return comboElements;
	}
	
	public Hashtable getShareType(){
		 Hashtable hash_type=new Hashtable();
		 hash_type.put("P", "Direct");
		 hash_type.put("T", "Suspence");
		 Enumeration en=hash_type.keys();
		 while(en.hasMoreElements()){
			 Object obj=en.nextElement();
			 logger.info("The keys are"+obj);
			 System.out.println("The values are"+hash_type.get(obj));
		 }
		 return hash_type;
	}
	
	public ShareCategoryObject[] getShareCategory()throws RemoteException{
		sharecatobj=sh_remote.getShareCategories(0,"1001001");
		for(int i=0;i<sharecatobj.length;i++){
			sharecatobj[i].getCatName();
		}
		return sharecatobj;
	}

	public ShareCategoryObject[] getShareCategory(int no, String ac_type)throws RemoteException{
		ShareCategoryObject[] sharecatobj=null;
		try{
			sharecatobj=sh_remote.getShareCategories(no,ac_type);
		}catch(Exception e){
			e.printStackTrace();
		}
		for(int i=0;i<sharecatobj.length;i++){
			sharecatobj[i].getCatName();
		}
		return sharecatobj;
	}
	
	public BranchObject[] getBranchcode()throws RemoteException{
		branch_obj=comRemote.getBranchDetails(-2);
		for(int i=0;i<branch_obj.length;i++){
			branch_obj[i].getBranchName();
		}
		return branch_obj;
	}
	
	public BranchObject[] getBranchcode1()throws RemoteException{
		branch_obj=comRemote.getBranchDetails(-2);
		for(int i=0;i<branch_obj.length;i++){
			branch_obj[i].getBranchName();
		}
		return branch_obj;
	} 
	
	public Hashtable getDivpaymode(){
		Hashtable hash_div=new Hashtable();
		hash_div.put("C","Cash");
		hash_div.put("T","Transfer");
		hash_div.put("W","Warrant");
		Enumeration en_div=hash_div.keys();
		while(en_div.hasMoreElements()){
			Object obj;
			obj=en_div.nextElement();
		}
		return hash_div;
	}

	public ModuleObject[] getAcTypes()throws RemoteException{
		comboElements=comRemote.getMainModules(2,"1002000,1007000,1014000,1015000,1018000");
		for(int i=0;i<comboElements.length;i++){
			comboElements[i].getModuleAbbrv();
		}
		return comboElements;
	}
	
	public Hashtable getpaymode(){
		Hashtable hash_div=new Hashtable();
		hash_div.put("C","Cash");
		hash_div.put("T","Transfer");
		hash_div.put("B","Branch");
		Enumeration en_div=hash_div.keys();
		while(en_div.hasMoreElements()){
			Object obj=en_div.nextElement();
			System.out.println("the keys are"+obj);
			System.out.println("the modes are"+hash_div.get(obj));
		}
		return hash_div;
	}
	
	public CustomerMasterObject getCustomer(int cid)throws RemoteException,CustomerNotFoundException{
		CustomerMasterObject cmobj=this.cust_remote.getCustomer(cid);
		if(cmobj!=null){
			String addr=sh_remote.getNomineeAddress(cid);
			cmobj.setAddressProof(addr);
		}
		return cmobj;
	}

	public String getSharecalc(double num_sh){
		double sh_calc=num_sh*100.00;
		return(String.valueOf(sh_calc));
	}

	public AccountObject getIntrodet(String actype,int acno)throws RemoteException{
		return comRemote.getAccount(null, actype, acno, getSysDate());
	}
	
	public String[] getdetails(){
		String[] details=new String[3];
		details[0]="Personal Details";
		details[1]="Intorducer Details";
		details[2]="Signature Details";
		return details;
	}

	public ShareMasterObject getShareMaster(String ac_type,int ac_no)throws RemoteException,AccountNotFoundException,CreateException{
		ShareMasterObject shmobj=null;
		try{
			shmobj=sh_remote.getShare(ac_type, ac_no);
			if(shmobj!=null){
				String rec_mode=shmobj.getRecievedMode();
				if(rec_mode.equalsIgnoreCase("T")){
					String ac_rcvd_type=shmobj.getRecievedAcctype();	
					int rec_ac_no=shmobj.getRecievedAccno();
					AccountObject ac=comRemote.getAccount(rec_mode, ac_rcvd_type, rec_ac_no, getSysDate());
					shmobj.setAcc_balance(ac.getAmount());
					shmobj.setAcobj(ac);
					ac=shmobj.getAcobj();
				}else if(rec_mode.equalsIgnoreCase("C")){
					int rec_ac_no=shmobj.getScrollno();
					String ac_rcvd_type=shmobj.getRecievedAcctype();
					AccountObject ac=comRemote.getAccount(rec_mode, ac_rcvd_type, rec_ac_no, getSysDate());
					shmobj.setAcc_balance(ac.getAmount());
					shmobj.setAcobj(ac);
					ac=shmobj.getAcobj();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return shmobj;
	}

	public  AccountObject getAccountBalance(String ac_type,int ac_no,String date) throws RemoteException{
		return comRemote.getAccount(null, ac_type, ac_no, getSysDate());
	}
	
	public ShareParamObject[] getshparam(int cat_no, String ac_type) throws RemoteException{
		ShareParamObject shparam[]=null;
		try{
			shparam=sh_remote.getShCategoryParameters(cat_no,ac_type);
		}catch(Exception e){
			e.printStackTrace();
		}
		return shparam;
	}
	
	public double getBalance(String trn_mode,String mod_code,int ac_no,double share_amt) throws RemoteException{
		double min_bal=0.0;
		double bal=0.0;
		AccountObject acobj=comRemote.getAccount(trn_mode,mod_code,ac_no,getSysDate());
		min_bal=sh_remote.getMinBalance(mod_code);
		if(acobj.getAmount()-share_amt>min_bal){
			bal=acobj.getAmount();
		}
		return bal;
	}
	
	public double getBalance(String mod_code)throws RemoteException{
		return sh_remote.getMinBalance(mod_code);
	}
	
	public double calculateShareAmt(ShareParamObject[] shparam,double num_of_shares,double sh_amt,int sh_cat,int a)throws RemoteException{
		double cal_amt=0.0;
		if(a==0){
			double shareamt=num_of_shares*sh_amt;
			double sharefee=0.0;
			shparam=sh_remote.getShCategoryParameters(sh_cat,"1001001");
			for(int i=0;i<shparam.length;i++){
				sharefee=shparam[0].getPrm_amt()*num_of_shares;
			}
			double other_prm_amt=sh_remote.getPrmAmt(sh_cat);
			double adm_fee=shparam[0].getPrm_amt();
			cal_amt=shareamt+sharefee+other_prm_amt+adm_fee;
		}
		if(a==1){
			double shareamt=num_of_shares*sh_amt;
			double sharefee=0.0;
			double addshfee=0.0;
			shparam=sh_remote.getShCategoryParameters(sh_cat,"1001001");
			for(int i=0;i<shparam.length;i++){
				sharefee=shparam[0].getPrm_amt()*num_of_shares;
				addshfee=shparam[3].getPrm_amt();
			}
			cal_amt=shareamt+sharefee+addshfee;
		}
		return cal_amt;
	}
	
	public  String[] getPaymentDetails(double num_of_shares,double sh_amt,double amt_recvd) throws RemoteException
	{
		ShareParamObject shparam[];
		String values[]=new String[6];
		shparam=sh_remote.getShCategoryParameters(1,"1001001");
		double sh_fee=shparam[0].getPrm_amt()*num_of_shares;
		double adm_fee=shparam[1].getPrm_amt();
		double sale=shparam[2].getPrm_amt();
		double totshval=num_of_shares*100;
		double bal=amt_recvd-totshval;
		System.out.println("The balance is "+bal);
		values[0]=String.valueOf(sh_fee);
		values[1]=String.valueOf(adm_fee);
		values[2]=String.valueOf(sale);
		values[3]=String.valueOf(0.0);
			
		/*if(bal>=sh_fee){
			values[0]=String.valueOf(sh_fee);
			System.out.println("Value[0]"+values[0]);
			bal-=sh_fee;
			System.out.println("The balance after sh_fee is"+bal);
		}
		else{
			values[0]=String.valueOf(bal);
			values[1]="0.00";
			values[2]="0.00";
			values[3]="0.00";
			bal-=bal;
		}

		if(bal>=adm_fee){
			values[1]=String.valueOf(adm_fee);
			System.out.println("Value[1]"+values[1]);
			bal-=adm_fee;
			System.out.println("The balance after admiss is"+bal);
		}
		else{
			values[1]=String.valueOf(bal);
			values[2]="0.00";
			values[3]="0.00";
			bal-=bal;
		}

		if(bal>=sale){
			values[2]=String.valueOf(sale);
			System.out.println("Value[2]"+values[2]);
			bal-=sale;
			System.out.println("The balance after sale of forms is"+bal);
		}
		else{
			values[2]=String.valueOf(bal);
			values[3]="0.00";
			bal-=bal;
			System.out.println("The balance after sale of forms in else is"+bal);
		}

		if(bal>=0){
			values[3]=String.valueOf(bal);
			System.out.println("The misc value is "+values[3]);
			System.out.println("Value[3]"+values[3]);
			
		}
		else{
			values[3]=String.valueOf(0.0);
			System.out.println("Value[3] in else"+values[3]);
		}*/
		values[4]=String.valueOf(amt_recvd);
		values[5]=String.valueOf(totshval);
		return values;
	}
	
	public AccountObject paycash(String mode,String ac_type,int scroll_no)throws RemoteException{
		return comRemote.getAccount(mode, ac_type, scroll_no, ShareDelegate.getSysDate());
	}
	
	public AccountObject[] getAccounts(String ac_type)throws RemoteException{
		return comRemote.getAccounts(ac_type,0,getSysDate());
	}
	
	public String[] get_addPaymentDetails(double num_of_shares,double sh_amt,double amt_recvd) throws RemoteException{
		String []values=new String[5];
		ShareParamObject shparam[]=sh_remote.getShCategoryParameters(1,"1001001");
		double sh_fee=shparam[0].getPrm_amt()*num_of_shares;
		double addfee=shparam[3].getPrm_amt();
		double totshval=num_of_shares*sh_amt;
		double bal=amt_recvd-totshval;
		if(bal>=sh_fee){
			values[0]=String.valueOf(sh_fee);
			bal-=sh_fee;
		}else{
			values[0]=String.valueOf(bal);
			values[1]="0.00";
			values[2]="0.00";
			bal-=bal;
		}
		if(bal>=addfee){
			values[1]=String.valueOf(addfee);
			bal-=addfee;
		}else{
			values[1]=String.valueOf(bal);
			values[2]="0.00";
			bal-=bal;
		}
		if(bal>=0){
			values[2]=String.valueOf(bal);
		}else{
			values[2]="0.00";
		}
		values[3]=String.valueOf(totshval);
		values[4]=String.valueOf(amt_recvd);
		return values;
	}
	
	public int store_addshare(ShareMasterObject shobj)throws RemoteException{
		return sh_remote.storeShare(shobj, 1, 0, 0);
	}
	
	public int storeShParam(Object[][] obj)throws RemoteException{
		return sh_remote.storeShareParam(obj);
	}
	
	public ShareMasterObject getShare(int trn_no)throws RemoteException{
		return sh_remote.getShareTransaction(trn_no, 0, ShareDelegate.getSysDate());
	}
	
	public int setDivrate(DividendRateObject[] divobj,int type,String shuser,String shtml)throws RemoteException{//for dividend cal
		return sh_remote.calculateDividend(divobj,shuser,shtml, ShareDelegate.getSysDate(), type);
	}
	
	public DividendRateObject[] getDivRate(String frm_dt,String to_dt) throws RemoteException{
		return sh_remote.getDivRate(frm_dt, to_dt);  
	}
	
	public ShareReportObject[] getPassbook(int type,String a,String b,int acnum,String actype)throws RemoteException{//for Pass Book
		ShareReportObject arr[]=sh_remote.shareReport(type, null, null, acnum, actype);
		if(arr!=null){
			if(arr[0].getAccStatus().equalsIgnoreCase("O")){
				arr[0].setAccStatus("OPENED");
			}else{
				arr[0].setAccStatus("CLOSED");
			}
			Object[] ob=new Object[2];
			for(int i=0; i<arr.length;i++){
				ob[0]=new Object();
				ob[1]=new Object();
			}
		}
		return arr;
	}
	
	public ShareReportObject[] shareopenclosereport(String frm_dt,String to_dt,int option) throws RemoteException,UndeclaredThrowableException{//for report
		ShareReportObject arr[]=null;
		if(option==1){
			arr=sh_remote.shareOpenClosed(6,frm_dt, to_dt, null);
		}else if(option==2){
			arr=sh_remote.shareOpenClosed(7,frm_dt, to_dt, null);
		}else if(option ==3){
			arr=sh_remote.shareTempPermanent(8,frm_dt, to_dt, null);
		}else if(option==4){
			arr=sh_remote.shareTempPermanent(9,frm_dt, to_dt, null);
		}else if(option==5){
			arr=sh_remote.shareRegistryReport(frm_dt, to_dt, null);
		}else if(option==6){
			arr=sh_remote.shareAllotmentWithdrawal(frm_dt, to_dt, null);
		}else if(option==7){
			arr=sh_remote.branchwiseSummary(null);
		}
		return arr;
	}
	
	public ShareReportObject[] getShareLedger(int type, String a,String b,int ac_no,String ac_type)throws RemoteException{
		ShareReportObject[] sh_report=null;
		try{
			sh_report=sh_remote.shareReport(type, null, null, ac_no, ac_type);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(sh_report!=null){
			if(sh_report[0].getAccStatus().equalsIgnoreCase("O")){
				sh_report[0].setAccStatus("OPENED");
			}else{
				sh_report[0].setAccStatus("CLOSED");
			}
			if(sh_report[0].getPayMode().equalsIgnoreCase("C")){
				sh_report[0].setPayMode("Cash");
			}else if(sh_report[0].getPayMode().equalsIgnoreCase("T")){
				sh_report[0].setPayMode("Transfer");
			}
			if(sh_report[0].getLoanAvailed().equalsIgnoreCase("F")){
				sh_report[0].setLoanAvailed("Not Availed");
			}else{
				sh_report[0].setLoanAvailed("Availed");
			}
		}
		return sh_report;
	}
  
	public static String getSysDate(){
		Calendar c = Calendar.getInstance();
		try{
			return (Validations.checkDate(c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR)));
		}catch (DateFormatException e){
			e.printStackTrace();
		}
		return null;
	}

	public int storeShare(ShareMasterObject sh)throws RemoteException{
		return sh_remote.storeShare(sh, 0, 0, 0);
	}

	public int verifyshare(ShareMasterObject sh,ShareParamObject[] shparam)throws RemoteException{
		return sh_remote.verifyShare(sh, 0, 0, shparam, getSysDate());
	}

	public int verifyAddshare(ShareMasterObject sh,ShareParamObject[] shparam,int trn_no)throws RemoteException{
		return sh_remote.verifyShare(sh, trn_no, 1, shparam, getSysDate());
	}

	public SignatureInstructionObject[] getSignature(int accno,String type)throws Exception{
		SignatureInstructionObject siObj[]=null;
		try{
			siObj=getComRemote().getSignatureDetails(accno,type);
		}catch(Exception e){
			throw e;
		}  
		return siObj;
	}

	public int checkShareNumber(int ac_no,int type) throws RemoteException{
		return sh_remote.checkShareNumber(type);
	}

	public int check_for_share_holder(int cid) throws RemoteException{
		return sh_remote.checkCustomer(cid);
	}
	
	public Hashtable WithdrawalTypes(){// for Share Withdrawal
		Hashtable hash_with=new Hashtable();
		hash_with.put("C", "Account Closure");
		hash_with.put("W", "Partial Withdrawal");
		Enumeration en_with=hash_with.keys();
		while(en_with.hasMoreElements()){
			Object ob;
			ob=en_with.nextElement();
		}
		return hash_with;
	}
	
	public String[] getwithdetails(){
		String[] details=new String[3];
		details[0]="Select";
		details[1]="PersonalDetails";
		details[2]="LoanDetails";
		return details;
	}
	
	public ShareMasterObject[] getLoanDetails(int ac_no,String ac_type)throws RemoteException{
		return sh_remote.getLoanDetails(ac_no, ac_type);
	}

	public String[] getWithdrawamount(String ac_type,int sh_no,int sh_cat)throws RemoteException{
		String [] withdraw=new String[2];
		ShareMasterObject shobj=sh_remote.getShare(ac_type, sh_no);
		shobj.getNumberofShares();
		ShareCategoryObject[] sh_cat1=sh_remote.getShareCategories(sh_cat,ac_type);
		sh_cat1[0].getMinShare();
		double minshares=shobj.getNumberofShares()-sh_cat1[0].getMinShare();
		withdraw[0]=String.valueOf(minshares);//set the values
		withdraw[1]=String.valueOf(minshares*shobj.getShareVal());//set the values
		return withdraw;
	}
	
	public String closure_amt(double no_of_sh,double sh_val){
		return String.valueOf(no_of_sh * sh_val);
	}

	public String checkloans(String ac_type,int ac_no)throws RemoteException{
		return sh_remote.checkLoans(ac_type, ac_no);
	}
	
	public int with_storeShare(ShareMasterObject sh)throws RemoteException{
		return sh_remote.storeShare(sh, 2, 0, 0);
	}

	public ShareMasterObject getShareTransaction(int trn_no)throws RemoteException{
		ShareMasterObject shobj=sh_remote.getShareTransaction(trn_no, 1, getSysDate());
		if(shobj!=null){
			shobj.setTotalAmount(shobj.getShareBalance()*shobj.getShareVal());
		}
		return shobj;
	}
	
	public int verify_withdraw(ShareMasterObject sh,int trn_no)throws RemoteException{
		return sh_remote.verifyShare(sh, trn_no, 2, null, getSysDate());
	}

	public ShareReportObject[] voterlist() throws RemoteException{
		return sh_remote.voterList(null);
	}
	
	public int storeDividendData(String shr_type,int shno,float damount,String date,String paymode,int accno,String acctype,String uid,String utml)throws RemoteException{
		return sh_remote.storeDividendData(shr_type, shno, damount, date, paymode, accno, acctype, uid, utml);
	}
	
	public ShareMasterObject getCustDetails(int ac_no)throws RemoteException{
		return sh_remote.getDetails(ac_no);
	}
	
	public String getAcname(int ac_no,String ac_type)throws RemoteException{
		return sh_remote.getAcDetails(ac_no,ac_type);
	}
	
	public ShareMasterObject[] converttemp_perm()throws RemoteException{
		return sh_remote.getShare("", "", 1);
	}
	
	public ShareMasterObject[] converttemp_perm_date(String frm_date,String to_dt,int type)throws RemoteException{
		return sh_remote.getShare(frm_date, to_dt, type);
	}
	
	public DividendObject[] unclaimeddiv_list(int brcode,String date,int frm_ac,int to_ac) throws RemoteException,DateFormatException{
		DividendObject[] div_obj=null;
		double total=0.0;
		try{
			div_obj=sh_remote.RetrieveUnPaid(brcode, date, frm_ac, to_ac,"");
		}catch(Exception e){
			e.printStackTrace();
		}
		if(div_obj!=null){
			for(int i=0;i<div_obj.length;i++){
				total=total+div_obj[i].getDivAmount();
			}
		}
		return div_obj;
	}
	
	public String[] getDivregtypes(){
		String[] div_reg=new String[2];
		div_reg[0]="Combined";
		div_reg[1]="Branch Wise";
		return div_reg;
	}

	public String[] getDivregoptions(){
		String[] div_types=new String[2];
		div_types[0]="All";
		div_types[1]="Individual";
		return div_types;
	}

	public ShareMasterObject[] dividendregistry(String date,int brno,int type,String string_query) throws RemoteException,DateFormatException,RecordsNotFoundException{
		return sh_remote.RetrieveRegister(date, brno, type, string_query);
	}

	public int[] getTempNos() throws RemoteException{
		return sh_remote.getTempShareNos();
	}

	public void shareregularization(ShareMasterObject[] shobj)throws RemoteException{
		sh_remote.shareRegularization(shobj);
	}
	 
	public void masterupdation(ShareMasterObject shobj,String ac_type,int ac_no) throws RemoteException,RecordsNotFoundException{
		sh_remote.updateShareMaster(shobj, ac_type, ac_no);
	}
	
	public ShareMasterObject[] sharemasterlog(String ac_type,int ac_no)throws RemoteException,RecordsNotFoundException{
		return sh_remote.viewlog(ac_type, ac_no);
	}
	
	public DividendObject[] retrivepayment(String from_dt,String to_dt,int accno,String ac_type,int type) throws RemoteException{
		return sh_remote.RetrievePayment(Validations.convertYMD(from_dt), Validations.convertYMD(to_dt), accno, ac_type, type);
	}

	public ShareReportObject[] certificate(int frm_ac_no,int to_ac_no,String actype,int memcat)throws RemoteException,RecordsNotFoundException{
		ShareReportObject[] shrep=null;
		try{
			shrep=sh_remote.printCertificate(frm_ac_no, to_ac_no, actype, memcat);
			if(shrep[0].getMem_cat().equalsIgnoreCase("1")){
				shrep[0].setMem_cat("Regular");
			}
			if(shrep[0].getMem_cat().equalsIgnoreCase("2")){
				shrep[0].setMem_cat("Associate");
			}
			if(shrep[0].getMem_cat().equalsIgnoreCase("3")){
				shrep[0].setMem_cat("Nominal");
			}
			if(shrep[0].getMem_cat().equalsIgnoreCase("4")){
				shrep[0].setMem_cat("Society");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return shrep;
	}
	
	public DividendObject[] dividend_notice(String ac_type,String date) throws RemoteException{
		DividendObject divobj[]=null;
		StringTokenizer token=new StringTokenizer(date,"/");
		String day=token.nextToken();
		String month=token.nextToken();
		String year=token.nextToken();
		date=year+"/"+month+"/"+day;
		divobj=sh_remote.RetrieveUnPaidNotice(ac_type, date);
		return divobj;
	}

	public String[] getTemplates() throws RemoteException{
		String[] template=sh_remote.getTemplate(1,"1001001");
		String temp[]=null;
		StringTokenizer st=null;
		if(template!=null){
			temp=new String[template.length];
			for(int i=0;i<template.length;i++){
				st=new StringTokenizer(template[i],"%%%%%");
				if(st.hasMoreTokens()){
					st.nextToken();
					temp[i]=new String("Template "+st.nextToken());
				}
         	}
		}
		return temp;
	}

	public String getTemplatesDetails(int template_no) throws RemoteException{
		return sh_remote.getTemplatedata(template_no);
	}

	public ShareCategoryObject[] storeSharetypes()throws RemoteException{
		return sh_remote.getShareTypes();
	}

	public ShareParamObject[] storeShareParams()throws RemoteException{
		return sh_remote.storeShareParams();
	}

	public String[] getTables(){
		String[] tables=new String[3];
		tables[0]=new String("ShareDivRate");
		tables[1]=new String("DirectorMaster");
		tables[2]=new String("DirectorRelation");
		return tables;
	}

	public DividendRateObject[]  getDivrates()throws RemoteException, RecordsNotFoundException{
		return sh_remote.getDivRateDetails(0);
	}

	public DirectorMasterObject[] getDirectorMaster() throws RemoteException,RecordsNotFoundException{
		return sh_remote.getDirectorMasterDetails(1);
	}
	
	public DirectorMasterObject[] getDirectorRelations()throws RemoteException,RecordsNotFoundException{
		return sh_remote.getDirectorRelationDetails(2);
	}

	public ModuleObject change_of_module(String mod_code)throws RemoteException{
		return sh_remote.changeofmodules(mod_code);
	}

	public DividendObject[] retrivedivpayment(String fr_dt,String to_dt,int ac_no,String ac_type) throws RemoteException,RecordsNotFoundException{
		return sh_remote.Retrieve_cash_table(fr_dt, to_dt, ac_no, ac_type);
	}

	public int delete_share(int ac_no, String actype)throws RemoteException{
		return sh_remote.deleteShare(ac_no, actype);
	}

	public int store_admin(Vector vec) throws RemoteException{
		int r=0;
		try{
			r=sh_remote.storeShareTypes(vec);
		}catch(Exception e){
			e.printStackTrace();
		}
		return r;
	}

	public void confirmPermShare(ShareMasterObject[] shobj,String date,int type) throws RemoteException{
		sh_remote.confirmShare(shobj,date,type);
	}

	public void shareDispatch(int ac_no)throws RemoteException{
		sh_remote.saveTempNo(ac_no);
	}

	public void storeDivRate(DividendRateObject[] dro, int type,String shuser,String shtml)throws RemoteException {
		sh_remote.storeShareDivRate(dro, type,shuser,shtml,getSysDate());
	}

	public void insertdeletemodule(ModuleObject[] mod_obj)throws RemoteException{
		sh_remote.insertdeleteModules(mod_obj,0);
	}

	public int cash(DividendObject[] divobj,String utml,String uid,String udate,String date)throws RemoteException,RecordsNotFoundException{
		return sh_remote.cash(divobj, utml, uid, udate, date);
	}

	public void main_transfer(String share_type,String utml,String uid,String udate,String date)throws RemoteException,RecordsNotFoundException{
		sh_remote.main_transfer(share_type, utml, uid, udate, date);
	}

	public void Nominee(NomineeObject[] nom) throws RemoteException{
		sh_remote.SetNomineeDetails(nom);
	}

	public int getnomineePercentage(int acno,String actype)throws RemoteException {
		return sh_remote.getNomineePercent(acno, actype);
	}

	public boolean updateTemplate(String actype, int no, String content,String shuser,String shtml) throws RemoteException{
	    return sh_remote.storeTemplate(content, actype, 1,shuser,shtml,no+1 );
	}
	
	public ModuleObject[] getAllAc_types(){
		ModuleObject[] mod_obj=null;
		try{
			mod_obj=comRemote.getMainModules(2,"1001000,1002000,1003000,1004000,1005000,1006000,1007000,1009000,1014000,1015000,1010000");
			return mod_obj;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mod_obj;
	}

	public String[] checkShareTransaction(String ac_type, int ac_no) throws RemoteException,RecordsNotFoundException{
		String[] s=new String[2]; 
		s=sh_remote.checkShareTransaction(ac_type,ac_no);
		return s;
	}

	public int transfer(DividendObject[] divobj,String utml,String uid,String udate,int type,String date)throws RemoteException,RecordsNotFoundException{
		return sh_remote.transfer(divobj, utml, uid, udate,type, date);
	}

	public int verifyDividend(DividendObject[] divobj,int vchno,String uid,String utml,String udate,String date)throws RemoteException,RecordsNotFoundException{
		return sh_remote.verifyDividend(divobj,vchno, utml, uid, udate, date);
	}

	public int payOrder(DividendObject[] divobj,String utml,String uid,String udate,int type,String date)throws RemoteException,RecordsNotFoundException{
		return sh_remote.transfer(divobj, utml, uid, udate,type, date);
	}

	public DividendObject[] getDividend(int vouch_no,String acct_type) throws RemoteException,RecordsNotFoundException{
		DividendObject[] div_obj=null;
		try{
			div_obj=sh_remote.getDividend(vouch_no, acct_type);
		}catch(Exception e){
			e.printStackTrace();
		}
		return div_obj;
	}

	public String getSysDateTime(){
		Calendar c=Calendar.getInstance();
		String str=String.valueOf(c.get(Calendar.SECOND));
		if(str.length()==1)
			str="0"+str;
		String str1=String.valueOf(c.get(Calendar.MINUTE));
		if(str1.length()==1)
			str1="0"+str1;
		String str2=String.valueOf(c.get(Calendar.HOUR));
		if(str2.length()==1)
			str2="0"+str2;
		try{
			return(Validations.checkDate(c.get(Calendar.DATE)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR))+" "+str2+":"+str1+":"+str+"  ");
		}catch (DateFormatException e){
			e.printStackTrace();
		}
		return null;
	}
}