/*
 * Created on Feb 23, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package clearingServer;

import java.io.File;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import javax.ejb.EJBLocalObject;

import masterObject.lockers.RentTransObject;
import masterObject.clearing.AckObject;
import masterObject.clearing.BankMaster;
import masterObject.clearing.BounceFineObject;
import masterObject.clearing.ChequeAckObject;
import masterObject.clearing.ChequeDepositionObject;
import masterObject.clearing.ClearingObject;
import masterObject.clearing.CompanyMaster;
import masterObject.clearing.DiscountCharges;
import masterObject.clearing.Reason;
import masterObject.clearing.ReasonLink;
import masterObject.clearing.ReasonMaster;

import commonServer.GLTransObject;

import exceptions.InsufficientBalanceException;
import exceptions.RecordNotInsertedException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;
import exceptions.RecordsNotInsertedException;
import masterObject.frontCounter.AccountMasterObject;
import masterObject.general.BranchObject;





/**
 * @author admin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ClearingLocal  extends EJBLocalObject{
	
	public String[][] getClgAdminTable(String table_name)throws RecordsNotFoundException;
	
	public void setClgAdminTable(int table_name,String table_data[][])throws RecordsNotInsertedException;
	
	public BankMaster[] getBankDetails(int bcode,int city_code,int bank_code)throws RecordsNotFoundException;
	
	public BranchObject[] getBranchDetails(int bcode)throws RecordsNotFoundException;
	
	public double DiscountChargeCalculation(String actype,double amount) ;
	
	public long storeChequeData(ChequeDepositionObject cd) ;
	
	public int storeChequeData(CompanyMaster cm[],int ctrlno) ;
	
	public int deleteChequeData(int ctrlno) ;
	
	public int lateReturnCheques(long ctrlno,String clgdate) ;
	
	public double storeMailChg(Vector value,String acc_type) ;
	
	public double CalcPoCommission(String d_ac_type,int d_ac_no,String cr_ac_type,double amount,String de_user,String de_tml,String de_date)throws RecordsNotFoundException;

	public ChequeDepositionObject[] retrieveChequeDetails(int ctrlno, int type)throws RecordsNotFoundException;
	
	public ChequeDepositionObject[] getUnidentifiedCheques(int dest)throws RecordsNotFoundException;
	//public int identifyCheques(long[] ctrlno,String clgdate,int clgno,int sendto)throws ;
	
	public int identifyCheques(long[] ctrlno,String clgdate,int clgno,int sendto,int delete) ;
	
	public int chequeDiscount( ChequeDepositionObject[] cd,int i,String date)throws RecordNotInsertedException;
	
	public ClearingObject[] getInwardStatement(String clgdate,int clgno,int source,String query) throws RecordsNotFoundException;
	
	public ChequeAckObject[] getBouncedCheques(String fdate,String tdate,String query)throws RecordsNotFoundException;
	
	public DiscountCharges[] retrieveDiscountCharges()throws RecordsNotFoundException;
	//========
	
	public ChequeDepositionObject[] getIdentifiedCheques(int opt,int bank_code,String clg_date ,int send_to,int clg_no)throws RecordsNotFoundException;
	
	public int ackEntry(int source,String str,double amount,String tml,String user,int ackno) ;
	
	public Object getMailChg(String column) throws RecordsNotFoundException;
	
	public int verifyAck(int ackno,int docsou,double amount,String tml,String user,String ve_date);
	
	public AckObject getAckDetails(int ackno,int ctrl)throws RecordsNotFoundException;
	
	public AckObject[] getAckReconcillation(boolean verified)throws RecordsNotFoundException;
	
	public ChequeAckObject[] getChequeDetails(int ackno)throws RecordsNotFoundException;
	
	public int deleteControl(int ctrlno) ;
	
	public long storeChequeData(ChequeAckObject cd) ;
	
	public double reconcile(int ackno)throws RecordsNotFoundException;
	
	public ChequeDepositionObject[] getChargeableCheques()throws RecordsNotFoundException;
	
	public void mailingCharge(ChequeDepositionObject[] cd) ;
	
	public int dispatchCheques(long[] ctrlno,String tml,String user,String date,String date_on)throws RecordNotUpdatedException,InsufficientBalanceException;
	
	public int onVerification(long ctrlno,String tml,String user,String ve_date)throws RecordNotUpdatedException;
	
	public void onPost(long ctrlno,String tml,String user)throws RecordNotUpdatedException;
	
	public ClearingObject[] getOutwardStatement(String date,int clgno,String query)throws RecordsNotFoundException;
	
	public ClearingObject[] getOutwardSummary(String date,int n,int clgno,String query)throws RecordsNotFoundException;
	
	public void insertTable(String clg_date,int clg_no,ClearingObject[]array_clearingobject)throws  RecordNotInsertedException;
	
	public ClearingObject[] getOutwardSlips(String date,int n,String query)throws RecordsNotFoundException;
	
	public CompanyMaster[] retrieveCompanyDetails(int str)throws RecordsNotFoundException;
	
	public CompanyMaster[] retrieveChqDtls(int ctrlno,int n)throws RecordsNotFoundException;
	
	public ChequeDepositionObject[] retrieveChequeDetails(String actype,int acno)throws RecordsNotFoundException;
	
	public ClearingObject[] getDetails(int ctrlno_from,int ctrlno_to,String str)throws RecordsNotFoundException;
	
	public int loadFile(File fc) ;
	
	public File StoreFile() ;
	
	public boolean storeTemplate(String text,String acty,int stage,String user,String tml,int temp_no) throws RecordNotInsertedException;
	
	public String[] getTemplate(int stage,String acty) throws RecordsNotFoundException;
	
	public ClearingObject[] getBouncedCheque()throws RecordsNotFoundException,RecordsNotFoundException;
	
	public Object[][] getBouncedChequeReport(String acty,String ac_no) throws RecordsNotFoundException;
	
	public void updateBounceChequeSummary(ClearingObject[] cl)throws RecordNotInsertedException;
	
	public ChequeDepositionObject[] getIdentifiedCheques(String clgdate,int clgno,int sendto)throws RecordsNotFoundException;

	//=======================Shettys method=====================
	public AccountMasterObject getAccountInfoInd(String acctype ,int accno) throws RecordsNotFoundException;
	
	public Reason[] getReasonDetails(String acc_type,int value) throws RecordsNotFoundException;
	
	public int addAccountTransactionDebitEntry(ChequeDepositionObject cd) ;
	
	public long storeInwardBouncedData(ChequeDepositionObject cd) ;
	
	public ChequeDepositionObject[] getBouncedDetails(String clgdate,String vetml,String veuser,String date ,int type)throws RecordsNotFoundException;
	
	public int storeGLTransaction(GLTransObject trnobj) ;
	
	public int[] verifyInwardData(long ctrlno,String vetml,String veuser,String vedate,String accounttype,int accountno,int opt,String sysdate)  ;
	
	public int deleteInwardData(long ctrlno)  ;
	
	public long storeInwardData(ChequeDepositionObject cd) ;
	
	public ChequeDepositionObject getInwardData(long ctrlno,int type)throws RecordsNotFoundException;
	
	public AckObject getAcknowledgeAmount(int ackno,String clgtype)throws RecordsNotFoundException;
	
	public CompanyMaster[] retrieveSelectiveMultiAccDetails(long ctrlno)throws RecordsNotFoundException;
	
	public ClearingObject getSelectiveDetails(long ctrlno)throws RecordsNotFoundException;
	
	public int storeSelectivePosting(long ctrlno,String vuser,String vtml,String ve_date,String date ,CompanyMaster[] cm) ;
	
	//public Vector clearingPosting(String clgdate,String clgnoarr[],String bankcdarr[],String vtml,String vuser) throws ,RecordNotInsertedException;
	public Vector[] clearingPosting(String clgdate,Vector clgno,Vector bankcd,Vector ctrl_no,String vtml,String vuser,String ve_date, String date ) throws RecordNotInsertedException;
	
	public Object getGenParam(String column);
	
	public boolean upDateGenParam(int value)throws RecordNotUpdatedException;
	
	public ClearingObject[] getOutwardSummary(String date,int n ,int clg_no, int send_to,String str)throws RecordsNotFoundException;
	
	public int storeRentTran(RentTransObject rto)  ;
	
	public ClearingObject[] getBankName(String date,int arr[])throws RecordsNotFoundException;
	
	public ClearingObject[] StoreToFile(String date,String clg_no)throws RecordsNotFoundException;
	
	public ReasonMaster[] getReasons() throws RecordsNotFoundException;
	
	public ReasonLink[] getLinkReasons() throws RecordsNotFoundException;
	
	public BounceFineObject[] retrieveBounceFine()throws RecordsNotFoundException;
	
	public int clearingAdminUpdation(Object value[],int type)throws RecordsNotInsertedException;
	
	public int ClearingAdminInsertion(Vector value[],int type)throws RecordNotInsertedException;
	
	public int clearingAdminDeletion(Vector value,int type)throws RecordNotInsertedException;
	
	public String getToday() ;
	
	public void checkReconcilation(String ackno)throws RecordsNotFoundException;
//	public ChequeDepositionObject bounce	
	public ClearingObject[] getBounceCheque(ClearingObject clear);
	
	public ChequeDepositionObject getLateReturnDetail(long ctrl)throws  RecordsNotFoundException;
	
	public Hashtable getColbalanePostInd(String acc_type,int acc_no,int prev_ctrl,String date)throws RecordsNotFoundException;
	
	public int outwardReturnBounce(long ctrl_no,int type,String ve_tml,String ve_user,String ve_date)throws RecordsNotFoundException;

	public Hashtable getCityBankBranchDetail(int bcode, String city_cd,String bank_cd ,String branch_cd) throws RecordsNotFoundException;

	public String[] getDistinctTablesValues(int table_name)throws RecordsNotFoundException;
	
	public AckObject getBundleAckDetails(int ackno,int ctrl)throws RecordsNotFoundException;
	
	public int deleteAck(int ackno)throws RecordsNotFoundException;
	
	public ChequeDepositionObject[] getHelpData(int i)throws RecordsNotFoundException;
	
	public ChequeAckObject[] ackChequeDetails(int ackno)throws RecordsNotFoundException;
	
	public int createPayOrder( ChequeDepositionObject co);
	
	public int setChequeNo(ChequeDepositionObject cd)throws RecordNotInsertedException;
}
