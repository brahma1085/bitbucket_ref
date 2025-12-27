/*
 * Created on Mar 15, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package clearingServer;

import java.io.File;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import javax.ejb.EJBObject;

import masterObject.lockers.RentTransObject;
import masterObject.clearing.AckObject;
import masterObject.clearing.BankMaster;
import masterObject.clearing.BounceFineObject;
import masterObject.clearing.ChequeAckObject;
import masterObject.clearing.ChequeDepositionObject;
import masterObject.clearing.ClearingObject;
import masterObject.clearing.CompanyMaster;
import masterObject.clearing.Reason;
import masterObject.clearing.ReasonLink;
import masterObject.clearing.ReasonMaster;
import masterObject.clearing.DiscountCharges;
import commonServer.GLTransObject;

import exceptions.InsufficientBalanceException;
import exceptions.RecordNotInsertedException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;
import exceptions.RecordsNotInsertedException;
import masterObject.frontCounter.AccountMasterObject;
import masterObject.general.BranchObject;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
	public interface ClearingRemote extends EJBObject {
		
	    public String[][] getClgAdminTable(String table_name)throws RecordsNotFoundException,RemoteException;
		
	    public void setClgAdminTable(int table_name,String table_data[][])throws RemoteException,RecordNotInsertedException;
		
	    public double CalcPoCommission(String d_ac_type,int d_ac_no,String cr_ac_type,double amount,String de_user,String de_tml,String de_date)throws RecordsNotFoundException,RemoteException;
		
	    public double getLoanDetails(double amt,String acc_type,int acc_no,String user_id,String user_tml,String date)throws RecordsNotFoundException,RemoteException;
		
	    public BankMaster[] getBankDetails(int bcode,int city_code,int bank_code)throws RemoteException,RecordsNotFoundException;
		
	    public BranchObject[] getBranchDetails(int bcode)throws RemoteException,RecordsNotFoundException;
		
	    public double DiscountChargeCalculation(String actype,double amount)throws RemoteException;
		
	    public long storeChequeData(ChequeDepositionObject cd)throws RemoteException;
		
	    public int storeChequeData(CompanyMaster cm[],int ctrlno)throws RemoteException;
		
	    public int deleteChequeData(int ctrlno)throws RemoteException;
		
	    public int lateReturnCheques(long ctrlno,String clgdate)throws RemoteException;
		
	    public double storeMailChg(Vector value,String acc_type)throws RemoteException;
		
	    public String getGL(String acc_tpe,int gl_code)throws RemoteException;
		
		public ChequeDepositionObject[] retrieve_multi_Cheque(int ctrl_no)throws RemoteException,RecordsNotFoundException;
		
		public Object getGenParam(String column)throws RemoteException;
		
		public boolean upDateGenParam(int value)throws RecordNotUpdatedException, RemoteException;
		
		public ChequeDepositionObject[] retrieveChequeDetails(int ctrlno, int type)throws RemoteException,RecordsNotFoundException;
		
		public ChequeDepositionObject[] getUnidentifiedCheques(int dest)throws RemoteException,RecordsNotFoundException;
		
		//public int identifyCheques(long[] ctrlno,String clgdate,int clgno,int sendto)throws RemoteException;
		
		public int identifyCheques(long[] ctrlno,String clgdate,int clgno,int sendto,int delete)throws RemoteException;
		
		public int chequeDiscount(ChequeDepositionObject[] cd,int i, String date )throws RemoteException,RecordNotInsertedException;
		
		public ClearingObject[] getInwardStatement(String clgdate,int clgno,int source,String query) throws RemoteException,RecordsNotFoundException;
		
		public ChequeAckObject[] getBouncedCheques(String fdate,String tdate,String query)throws RemoteException,RecordsNotFoundException;
	    
		public DiscountCharges[] retrieveDiscountCharges()throws RemoteException,RecordsNotFoundException;
	
		public ChequeDepositionObject[] getIdentifiedCheques(int opt,int bank_cd,String clg_date ,int send_to,int clg_no )throws RemoteException,RecordsNotFoundException;
		
		public int ackEntry(int source,String clg_type,double amount,String tml,String user,int ackno)throws RemoteException;
		
		public Object getMailChg(String column) throws RemoteException,RecordsNotFoundException;
		
		public int verifyAck(int ackno,int docsou,double amount,String tml,String user,String ve_date)throws RemoteException;
		
		public AckObject getAckDetails(int ackno,int ctrl)throws RemoteException,RecordsNotFoundException;
		
		public AckObject[] getAckReconcillation(boolean verified)throws RemoteException,RecordsNotFoundException;
		
		public ChequeAckObject[] getChequeDetails(int ackno)throws RemoteException,RecordsNotFoundException;
		
		public int deleteControl(int ctrlno)throws RemoteException;
		
		public long storeChequeData(ChequeAckObject cd)throws RemoteException;
		
		public double reconcile(int ackno)throws RecordsNotFoundException,RemoteException;
		
		public ChequeDepositionObject[] getChargeableCheques()throws RemoteException,RecordsNotFoundException;
		
		public void mailingCharge(ChequeDepositionObject[] cd)throws RemoteException;
		
		public int dispatchCheques(long[] ctrlno,String tml,String user,String date,String date_on)throws RemoteException,RecordNotUpdatedException,InsufficientBalanceException;
		
		public int onVerification(long ctrlno,String tml,String user,String ve_date)throws RemoteException,RecordNotUpdatedException;
		
		public void onPost(long ctrlno,String tml,String user)throws RemoteException,RecordNotUpdatedException;
		
		public ClearingObject[] getOutwardStatement(String date,int clgno,String query)throws RemoteException,RecordsNotFoundException;
		
		public ClearingObject[] getOutwardSummary(String date,int n,int clgno,String query)throws RemoteException,RecordsNotFoundException;
		
		public void insertTable(String clg_date,int clg_no,ClearingObject[]array_clearingobject)throws RemoteException ,RecordNotInsertedException;
		
		public ClearingObject[] getOutwardSlips(String date,int n,String query)throws RemoteException,RecordsNotFoundException;
		
		public CompanyMaster[] retrieveCompanyDetails(int str)throws RemoteException,RecordsNotFoundException;
		
		public CompanyMaster[] retrieveChqDtls(int ctrlno,int n)throws RemoteException,RecordsNotFoundException;
		
		public ChequeDepositionObject[] retrieveChequeDetails(String actype,int acno)throws RemoteException,RecordsNotFoundException;
		
		public ClearingObject[] getDetails(int ctrlno_from,int ctrlno_to,String str)throws RemoteException,RecordsNotFoundException;
		
		public int loadFile(File fc)throws RemoteException;
		
		public File StoreFile()throws RemoteException;
		
		public boolean storeTemplate(String text,String acty,int stage,String user,String tml,int temp_no) throws RemoteException,RecordNotInsertedException;
		
		public String[] getTemplate(int stage,String acty) throws RemoteException,RecordsNotFoundException;
		
		public ClearingObject[] getBouncedCheque()throws RemoteException,RecordsNotFoundException,RecordsNotFoundException;
		
		public Object[][] getBouncedChequeReport(String acty,String ac_no) throws RemoteException,RecordsNotFoundException;
		
		public void updateBounceChequeSummary(ClearingObject[] cl)throws RemoteException,RecordNotInsertedException;
		
		public ChequeDepositionObject[] getIdentifiedCheques(String clgdate,int clgno,int sendto)throws RemoteException,RecordsNotFoundException;
		

		//=======================Shettys method=====================
		
		public AccountMasterObject getAccountInfoInd(String acctype ,int accno) throws RemoteException,RecordsNotFoundException;
		
		public Reason[] getReasonDetails(String acc_type,int value) throws RemoteException,RecordsNotFoundException;
		
		public int addAccountTransactionDebitEntry(ChequeDepositionObject cd)throws RemoteException;
		
		public long storeInwardBouncedData(ChequeDepositionObject cd)throws RemoteException;
		
		public ChequeDepositionObject[] getBouncedDetails(String clgdate,String vetml,String veuser,String date ,int type)throws RemoteException,RecordsNotFoundException;
		
		public int storeGLTransaction(GLTransObject trnobj) throws RemoteException;
		
		public int[] verifyInwardData(long ctrlno,String vetml,String veuser,String vedate,String account_type,int account_no,int opt,String sysdate) throws RemoteException;
		
		public int deleteInwardData(long ctrlno) throws RemoteException;
		
		public long storeInwardData(ChequeDepositionObject cd)throws RemoteException;
		
		public ChequeDepositionObject getInwardData(long ctrlno,int type)throws RemoteException,RecordsNotFoundException;
		
		public ChequeDepositionObject getmulti_crInwardData(long ctrlno,String ac_type,int ac_no)throws RemoteException,RecordsNotFoundException;
		
		public AckObject getAcknowledgeAmount(int ackno, String clgtype)throws RemoteException,RecordsNotFoundException;
		
		public CompanyMaster[] retrieveSelectiveMultiAccDetails(long ctrlno)throws RemoteException,RecordsNotFoundException;
		
		public ClearingObject getSelectiveDetails(long ctrlno)throws RemoteException,RecordsNotFoundException;
		
		public int storeSelectivePosting(long ctrlno,String vuser,String vtml,String ve_date,String date ,CompanyMaster[] cm) throws RemoteException;
		
		//public Vector clearingPosting(String clgdate,String clgnoarr[],String bankcdarr[],String vtml,String vuser) throws RemoteException,RecordNotInsertedException;
		
		public Vector[] clearingPosting(String clgdate,Vector clgno,Vector bankcd,Vector ctrl_no,String vtml,String vuser,String ve_date , String date) throws RemoteException,RecordNotInsertedException;
		
		public ClearingObject[] getOutwardSummary(String date,int n ,int clg_no, int send_to,String str)throws RemoteException,RecordsNotFoundException;
		
		public int storeRentTran(RentTransObject rto) throws RemoteException;
		
		public ClearingObject[] getBankName(String date,int arr[])throws RemoteException,RecordsNotFoundException;
		
		public ClearingObject[] StoreToFile(String date,String clg_no)throws RemoteException,RecordsNotFoundException;
		
		public ReasonMaster[] getReasons() throws RemoteException,RecordsNotFoundException;
		
		public ReasonLink[] getLinkReasons() throws RemoteException,RecordsNotFoundException;
		
		public BounceFineObject[] retrieveBounceFine()throws RemoteException,RecordsNotFoundException;
		
		public int clearingAdminUpdation(Object value[],int type)throws RemoteException,RecordsNotInsertedException;
		
		public int ClearingAdminInsertion(Vector value[],int type)throws RemoteException,RecordNotInsertedException;
		
		public int clearingAdminDeletion(Vector value,int type)throws RemoteException,RecordNotInsertedException;
		
		public void getFromFile(String file_name)throws RemoteException,RecordNotInsertedException;
		
		public void checkReconcilation(String ackno)throws RemoteException,RecordsNotFoundException;
		
		public String getToday() throws RemoteException;
		
		 public ClearingObject[] getBounceCheque(ClearingObject clear)throws RemoteException;
		 
		 public ChequeDepositionObject getLateReturnDetail(long ctrl)throws RemoteException, RecordsNotFoundException;
		 
		 public Hashtable getColbalanePostInd(String acc_type,int acc_no,int prev_ctrl,String date)throws RemoteException,RecordsNotFoundException;
		 
		 public int outwardReturnBounce(long ctrl_no,int type,String ve_tml,String ve_user,String ve_date)throws RemoteException,RecordsNotFoundException;
		 
		 public Hashtable getCityBankBranchDetail(int bcode, String city_cd,String bank_cd ,String branch_cd) throws RemoteException,RecordsNotFoundException;

		 public String[] getDistinctTablesValues(int table_name)throws RecordsNotFoundException,RemoteException;
		
		 public AckObject getBundleAckDetails(int ackno,int ctrl)throws RecordsNotFoundException ,RemoteException;
		 
		 public int deleteAck(int ackno)throws RecordsNotFoundException,RemoteException;
		 
		 public ChequeDepositionObject[] getHelpData(int i)throws RecordsNotFoundException,RemoteException;
		 
		 public ChequeAckObject[] ackChequeDetails(int ackno)throws RecordsNotFoundException,RemoteException;
		 
		 public int createPayOrder( ChequeDepositionObject co)throws RemoteException; 
		 
		 public int setChequeNo(ChequeDepositionObject cd)throws RecordNotInsertedException,RemoteException;
		 
		 public int[] getClearingNo(String date , int send_to,int type)throws RecordsNotFoundException,RemoteException;
		 
		 public long verifyDebitECS(ChequeDepositionObject cd)  throws RecordNotInsertedException,RemoteException;
		 
		 public long verifyCreditECS(ChequeDepositionObject cd)  throws RecordNotInsertedException,RemoteException;
		 
		 public AckObject[] getAckDetail(int type , String code ) throws RecordsNotFoundException,RemoteException;
		 
		 public boolean updateOutStationBounceCheque( ChequeDepositionObject chqobj ) throws RemoteException;
		 
		 public boolean verifyOutStationCheque( ChequeDepositionObject cd ) throws RemoteException;
		 
		 public  ChequeDepositionObject[] getOutstationCheque(String date)throws RecordsNotFoundException,RemoteException;
		 
		 public void setOutstationLetter( Vector< Integer > ctrl_no) throws RecordNotInsertedException, RemoteException;
		 
		 public ChequeDepositionObject[] getOutwardDespCheque(String act_type, int ac_no) throws RecordsNotFoundException,RemoteException ;
		
		 public BankMaster[] getBankDetails(int bcode,String date, int clg_bank,int clg_no) throws RecordsNotFoundException,RemoteException;
		 
		 public int chequeWithdrawal(int ctrlno ,String tml,String usr,String date) throws RecordsNotInsertedException,RemoteException;
		 
		 public int chequeDisBean(ChequeDepositionObject[] cd,int i, String date )throws RecordNotInsertedException,RemoteException;
		 
		}
