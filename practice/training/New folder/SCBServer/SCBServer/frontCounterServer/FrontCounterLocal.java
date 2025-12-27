/*
 * Created on Mar 8, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package frontCounterServer;

import java.rmi.RemoteException;

import exceptions.AccountNotFoundException;
import exceptions.RecordsNotFoundException;
import masterObject.frontCounter.AccountInfoObject;
import masterObject.frontCounter.PayOrderObject;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface FrontCounterLocal extends javax.ejb.EJBLocalObject
{
	public String verifyChequeBookNo(String actype,int bookno);
	
	public int storePOGL(PayOrderObject po);

	public String ODCCInterestCalc(String ac_type,int acno,double minimumbal,String Uid,String Utml,String Udate,int calc_ind);
	
	public AccountInfoObject getAccountInfo(String acctype,int accno) throws AccountNotFoundException;
		
	public double getCommission(String actype,int custtype,double amt,String date) ;

	
	/*	public int storeAccountMaster(AccountMasterObject am,int updated) ;

	public Vector CheckChqNo(int chq_no,int ac_no,String ac_type) ;

	public int storeCashWithdraw(ChequeObject ch) ;

	public ChequeObject getCashWithdraw(int tokenno,int type) ;
	
	public int updateAccountMaster(AccountMasterObject am) ;

	public int verifySB(AccountTransObject at);

	public int[] getCid(int accno,String type) ;

	public AccountMasterObject getAccountMaster(int accno,String type) ;

	public ChequeObject[] showChequeNos(String acctype,int accno);
	
	public boolean checkToken(int tokenno) ;

	public AccountObject[] getChequeDet(int chqno) ;

	public Vector getChequeNoDet(String acctype,int accno,int chqno) ;

	public int updateChequeDet(ChequeObject ch) ;

	public int storePayOrder(PayOrderObject po) ;

	public PayOrderObject getPayOrder(int srno) ;

	public AccCategoryObject[] getCustomerPayTypes() ;

	public double getCommission(String actype,int custtype,double amt) ;

	public int  verifyPOEntry(PayOrderObject po) ;

	public int verifyCheque(int acno,String type,int bno,int from,int to,String vid,String tml);


	public boolean accountClose(String actype,int acno) ;

	public PayOrderObject getPayOrderInstrn(int chqno) ;

	public int storePOLink(PayOrderObject[] po,int updated,int delete) ;

	public PayOrderObject[] getPODetails(int po_no) ;

	public int verifyPayOrder(PayOrderObject po) ;




	public PayOrderObject[] getPOMake(int pono) ;

	public int  getLastChequeNo(int j,String acctype,int accno) ;

	public int storeChqDetails(ChequeObject ch);

	public ChequeObject getChqDetails(String actype,int acno);



	public int setPayOrderInstrn(int type,String no,int chqno,String date) ;

	public CustomerMasterObject[] retrieveinfo(String cum_name,String cum_acctype) ;

	public AccountTransObject[] retrievePassBook(int type,String actype,int acno,String from,String to) ;

	public PayOrderObject[] RetrievePOMadeInfo(String date,int no,String query);

	public PayOrderObject[] RetrieveCashUncash(String fromdate,String todate,int num,String query);

   	public PayOrderObject RetrievePrintInfo(String date,int pono,int flag) ;

   	public PayOrderObject[] RetrievePrintInfo(int flag) ; 

	public int getMaxRows(String modulecode);

	public AccountTransObject[] PrintBook(String acty,int acno,String frdate,String todate,int ps_bk_seq,int a) ;
	
	public String verifyChequeNo(String actype,int ch_bk_no,int leaf);
	
	public boolean checkPayOrderChequeNo(int chqno) ;
	
	public int verifyChequeWithDrawal(AccountTransObject at);
	
	public AccountInfoObject getAccountInfo(String acctype,int accno) ;
	
	public boolean checkChequeWithdrawal(String actype,int acno) ;

	
//-----------Interest

	public String getLastIntDate(int code,int type);
	
	public int savingsInterestCalculation(String type,int acno,int modcode,double minbal,String todate,String Uid,String Utml,int no);
	
	public IntPayObject[] getIntPay();
	
	public boolean InterestPosting(String UTml,String Uid,String modname,int modcode);
	
//--------------ODCC	
	
	public int storeODCCMaster(ODCCMasterObject ln,int type) ;

	public ODCCMasterObject getODCCMaster(int ln_acno,String ln_acty) ;

	public int verifyODCCMaster(AccountTransObject at);
	
	public int verifyODCCSanction(ODCCMasterObject lnobj) ;
	
	public int sanctionODCC(ODCCMasterObject lnobj,boolean priority,boolean weaker,String sysdate);
	
	public double getODCCIntRate(String ln_type,String fdate,int category,int period,double amt) ;
	
	public int ODCCInterestCalc(String ac_type,int acno,double minimumbal,String Uid,String Utml,int calc_ind);
	
	public DepositMasterObject getDepositMaster(String actype,int no) ;
	
	public int confirmCustomerId(String cid) ;
//---------Admin
	public int insertIntoTable(String table_name,int no_of_columns,Vector vec) ;
	
	public int deleteFromTable(String table_name,String conditions) ;
	
	public int updateTable(String table_name,String update,String condition) ;*/

}
