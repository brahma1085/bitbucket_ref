package shareServer;

import exceptions.DateFormatException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;
import exceptions.ScrollNotFoundException;
import masterObject.general.AccountObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;
import javax.ejb.EJBObject;


import masterObject.share.DirectorMasterObject;
import masterObject.share.DividendObject;
import masterObject.share.DividendRateObject;
import masterObject.share.ShareCategoryObject;
import masterObject.share.ShareParamObject;
import masterObject.share.ShareReportObject;
import masterObject.share.ShareMasterObject;


public interface ShareRemote extends EJBObject {
	
	public ShareReportObject[] printCertificate(int fromacno,int toacno,String actype,int memcat) throws RemoteException,RecordsNotFoundException;

	public ShareReportObject[] branchwiseSummary(String str) throws RemoteException;

	//public ShareReportObject[] shareRegistryReport(String date2,String str) throws RemoteException;
	public ShareReportObject[] shareRegistryReport(String date1,String date2,String str) throws RemoteException;

	public ShareReportObject[] shareAllotmentWithdrawal(String date1,String date2,String str) throws RemoteException;

	public ShareReportObject[] voterList(String str) throws RemoteException;

	public ShareReportObject[] shareTempPermanent(int j,String date1,String date2,String str) throws RemoteException;

	public ShareReportObject[] shareOpenClosed(int j,String date1,String date2,String str) throws RemoteException;
	
	public ShareMasterObject[] getLoanDetails(int shno,String shtype)throws RemoteException;
	
	
	//public int storeShare(ShareMasterObject ms,int type) throws RemoteException;
	
	public int storeShare(ShareMasterObject ms,int type,int old_scroll,int new_scroll) throws RemoteException;//Swaran
	
	public int closeShare(ShareMasterObject cms) throws RemoteException; //Swaran
	
	public String checkLoans(String acctype,int acno) throws RemoteException;//Swaran
	
	public int[] getTempShareNos() throws RemoteException;//Swaran
	
	public int saveTempNo(int ac_number) throws RemoteException;//Swaran
	
	public int shareRegularization(ShareMasterObject[] sm) throws RemoteException;//Swaran
	
	public ShareMasterObject getShare(String actype,int shareno) throws RemoteException;
	
	public int confirmShare(ShareMasterObject sm[],String apdate,int type) throws RemoteException;
	//public int confirmShare(ShareMasterObject sm[],String apdate,int type,String date) throws RemoteException;//Amzad
	
	public int deleteShare(int no,String shtype) throws RemoteException;//Swaran
	
	public int deleteShareTran(int no,int type) throws RemoteException;//Swaran
	
	public ShareMasterObject[] getShare(String from,String to,int type) throws RemoteException;
	
	//public int checkShareTransaction(String actype,int acno) throws RemoteException;//Swaran
	
	public String[] checkShareTransaction(String actype,int acno) throws RemoteException;//Karthi -->07/06/2006
	
	public ShareParamObject[] getShCategoryParameters(int cat_no,String actype) throws RemoteException;
	
	public ShareCategoryObject[] getShareCategories(int no,String actype) throws RemoteException;
	
	public int verifyShare(ShareMasterObject sm,int trnno,int type,ShareParamObject shparam[],String date)throws RemoteException;

	public ShareMasterObject getShareTransaction(int trnno,int type,String date) throws RemoteException;
	
	public AccountObject[] getCheques(String acctype,int accno)throws RemoteException;
	
	//public String getDirectorName(int id,String date) throws RemoteException,RecordsNotFoundException;
	public DirectorMasterObject[] getDirectorName(String date) throws RemoteException,RecordsNotFoundException;//karthi-->24/01/2006
	
	public double getMinBalance(String module_code) throws RemoteException;
	
	public int storeShareTypes(Vector vec) throws RemoteException;
	
	//public int storeShareParam(Vector vec) throws RemoteException;
	public int storeShareParam(Object[][] obj) throws RemoteException;//Karthi==>05/04/2006
	
	public ShareReportObject[] shareReport(int j,String date1,String date2,int x,String str) throws RemoteException;
	
	//public int storeDividendData(int mno,float damount,String date,String paymode)throws RemoteException;
	public int storeDividendData(String shr_type,int shno,float damount,String date,String paymode,int accno,String acctype,String uid,String utml)throws RemoteException;//Karthi

	public DividendObject[] RetrieveUnPaid(int branchcode,String date,int startno,int endno,String string_query) throws RemoteException,DateFormatException;

	public DividendObject[] RetrievePayment(String from,String to,int accno,String acctype,int type) throws RemoteException;
	
	//public DividendObject[] RetrieveUnPaidNotice(String date) throws RemoteException;
	public DividendObject[] RetrieveUnPaidNotice(String acc_type,String date) throws RemoteException;
	
	public String[] getTemplate(int stage,String code) throws RemoteException;
	
	public boolean storeTemplate(String text,String acty,int stage,String user,String tml,int temp_no) throws RemoteException;
	
	public int getDeleteTemplate(String ac_type,int int_template_no) throws RemoteException;
	
	public ShareMasterObject[] RetrieveRegister(String date,int brno,int type,String string_query) throws RemoteException,DateFormatException,RecordsNotFoundException;

	public int calculateDividend(DividendRateObject[] div,String detml,String deuser,String dedate,int type)throws RemoteException;


	public DividendObject[] Retrieve_cash_table(String from,String to,int accno,String acctype) throws RemoteException,RecordsNotFoundException;
	
	public int main_transfer(String share_type,String utml,String uid,String udate,String date)throws RemoteException,RecordsNotFoundException;

	public DividendObject[] main_warrant(String share_type,String utml,String uid)throws RemoteException,RecordsNotFoundException;
	
	//public int transfer(DividendObject[] update_div_transfer,String utml,String uid)throws RemoteException,RecordsNotFoundException;
	public int transfer(DividendObject[] update_div_transfer,String utml,String uid,String udate,int type,String date)throws RemoteException,RecordsNotFoundException;//karthi

	//public DividendObject[] print_warrant(DividendObject[] update_div_warrant,String utml,String uid)throws RemoteException,RecordsNotFoundException;
	public DividendObject[] print_warrant(DividendObject[] update_div_warrant,String utml,String uid,int type)throws RemoteException,RecordsNotFoundException;//karthi
	
	public DividendRateObject retrieve(int acno) throws RemoteException;

	//public int cash(int shno) throws RemoteException;
	
	public int cash(DividendObject[] update_div_share,String utml,String uid,String udate,String date)throws RemoteException,RecordsNotFoundException;

	public int getYearEnd()throws RemoteException;

	public int setBalance(String acctype,int accno,double amount,int type) throws RemoteException;

	public DividendRateObject[] getDivRate(String fdate,String tdate)throws RemoteException;
	//newly added --tossy
	//public ShareReportObject[] printOneCertificate(int fromacno,int cert_no,String date,String actype,int memcat) throws RemoteException,RecordsNotFoundException, RecordNotUpdatedException, DateFormatException;
	//removed date as it is not used in the function at al,removed cert_no(taking from genParam,added toacno(2nd field)-----Swaran
	public ShareReportObject[] printOneCertificate(int fromacno,int toacno,String actype,int memcat) throws RemoteException,RecordsNotFoundException, RecordNotUpdatedException, DateFormatException;
	
	public int[] getDirectorRelation() throws RemoteException;//Swaran
	
	public int getRelationCode(String name) throws RemoteException;//Swaran
	
	public String getRelationDesc(int code) throws RemoteException;//Swaran
	
	public ShareMasterObject[] displayScrollNo(String ac_type,String date) throws RemoteException,SQLException,ScrollNotFoundException;//Karthi
	
	public DividendRateObject[] getDivRateDetails(int type)throws RemoteException,RecordsNotFoundException;//Karthi-->16/01/2006
	
	public DirectorMasterObject[] getDirectorMasterDetails(int type)throws RemoteException,RecordsNotFoundException;//Karthi-->16/01/2006
	
	public DirectorMasterObject[] getDirectorRelationDetails(int type)throws RemoteException,RecordsNotFoundException;//Karthi-->16/01/2006
	
	public int deleteShareDivRate(String[] fd,String[] td,double[] dr,int type) throws RemoteException;//Karthi-->17/01/2006
	
	public int deleteDirMasterRelation(int[] did,int[] dcid,int[] rcod,String[] rtyp,int type) throws RemoteException;//Karthi-->17/01/2006
	
	public int storeShareDivRate(DividendRateObject[] dro,int type,String uid,String tml,String udate) throws RemoteException;//Karthi-->18/01/2006
	
	public int storeDirMasterRelation(DirectorMasterObject[] dmo,int type) throws RemoteException;//Karthi-->18/01/2006
	
	public int checkCustomer(int cid)throws RemoteException;//Karthi-->24/01/2006
	
	public  DividendObject[] getDividend(int vouch_no,String acct_type)throws RemoteException,RecordsNotFoundException;//Karthi-->03/03/2006
	
	public int verifyDividend(DividendObject[] divobj,int vouch_no,String uid,String utml,String udate,String date)throws RemoteException;//Karthi-->04/03/2006
	
	public int payOrder(DividendObject[] update_div_share,String utml,String uid,String udate,int type,String date)throws RemoteException,RecordsNotFoundException;//karthi-->06/03/2006
	
	public int deleteDividend(int vouch_no,String acc_type,int acc_no)throws RemoteException;//Karthi-->06/03/2006
	
	public int RetriveReferenceNo(String modulecode)throws RemoteException;//Karthi-->22/03/2006
	
	public int updateShareMaster(ShareMasterObject smobj,String ac_type,int ac_no)throws RecordsNotFoundException,RemoteException;//Karthi-->19/04/2006
	
	public int updateNomineeMaster(NomineeObject[] nom_obj_old,NomineeObject[] nom_obj_new,String date)throws RemoteException;//Karthi-->21/04/2006 
	
	public int updateSignatureInstruction(SignatureInstructionObject[] sig_inst,String acc_type,int acc_no)throws RemoteException;//Karthi-->26/04/2006
	
	public ShareMasterObject[] displayControllNo(String ac_type,int ac_no,String date) throws RemoteException,SQLException,ScrollNotFoundException;//Karthi-->22/05/2006
	
	public int checkLoanDetails(String ac_type,int ac_no)throws RemoteException;//Karthi-->26/06/2006
	
	public ShareMasterObject[] displayTransactionNo(String date) throws RemoteException,ScrollNotFoundException;//Karthi-->01/07/2006
	
	public ShareMasterObject[] displayUnVerifiedShrNos(String ac_type,String date)throws RemoteException,ScrollNotFoundException;//Karthi-->10/07/2006
	
	public int insertdeleteModules(ModuleObject[] mobj,int type)throws RemoteException;//Karthi-->17/07/2006
	
	public int storeToRemainder(DividendObject[] dvobj)throws RemoteException;//Karthi-->29/09/2006
	
	public double getPrmAmt(int category)throws RemoteException;//suraj 21/12/2007
	
	public int checkShareNumber(int type)throws RemoteException;//suraj 09/01/2008
	
	public ShareMasterObject getDetails(int ac_no) throws RemoteException;//suraj 21/03/2008
	
	public String getAcDetails(int ac_no,String ac_type)throws RemoteException;//suraj 21/03/2008
	
	public ShareMasterObject[] viewlog(String ac_type,int ac_no)throws RemoteException;//suraj 11/04/2008

	public ShareCategoryObject[] getShareTypes()throws RemoteException;//suraj 29/04/2008

	public ModuleObject changeofmodules(String mod_code)throws RemoteException; //suraj 03/05/2008
	
	public ShareParamObject[] storeShareParams() throws RemoteException;//suraj added on 05/08/2008
	
	public int SetNomineeDetails(NomineeObject[] nom)throws RemoteException; //suraj added on 29/8/2008
	
	 public String getNomineeAddress(int cid)throws RemoteException; //suraj added on 18/09/2008
	 
	 public int getNomineePercent(int accno,String ac_type)throws RemoteException; //suraj added on 19/09/2008
	 
	 public String getTemplatedata(int template_no)throws RemoteException; //suraj added on 06/10/2008
	
}
