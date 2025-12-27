package pygmyServer;

import java.rmi.RemoteException;
import java.security.SignatureException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.ejb.EJBObject;

import masterObject.backOffice.PygmyObject;

import masterObject.pygmyDeposit.AgentMasterObject;
import exceptions.AccountNotFoundException;
import masterObject.pygmyDeposit.PygmyMasterObject;
import masterObject.pygmyDeposit.PygmyRateObject;
import masterObject.pygmyDeposit.PygmyTransactionObject;
import masterObject.pygmyDeposit.QuarterInterestObject;
import exceptions.AccountClosedException;
import exceptions.DateFormatException;
import exceptions.FreezedAccountException;
import exceptions.InOperativeAccountException;
import exceptions.JointAccountNotFound;
import exceptions.NomineeNotCreatedException;
import exceptions.NotValidJointHolder;
import exceptions.RecordNotInsertedException;
import exceptions.RecordsNotFoundException;
import masterObject.general.AdminObject;
import masterObject.general.ModuleAdminObject;
import masterObject.general.ModuleObject;
import masterObject.generalLedger.GLObject;

public interface PygmyRemote extends EJBObject
{
	//public PygmyMasterObject getRemittanceDataToValidate(String agt_no,int agt_no,String coll_date)throws RemoteException;
    public PygmyMasterObject[] getUnVerifiedPDClosureAcc(String curdate)throws RemoteException;
    
	public int getRefInd(String agt_type,int agt_no,String coll_dt,int scrl_no)throws RemoteException;
	
	public boolean checkPygTran(String ac_type,int ac_no,int id)throws RemoteException;
	
	public int getMaxPeriodDRemittance(int ac_type) throws RemoteException;
	
	public int getPartWithdUpdateRefInd(String ac_type,int ac_no)throws RemoteException;
	
	public int getMaturedPeriodPD()throws RemoteException;
		
	public AgentMasterObject getPAGJointAccountCids(String jt_acc_ty,int jt_acc_no)throws RemoteException;
	
	public int getUpdateRemittanceRefInd(String ag_ty,int ag_no,String coll_date)throws RemoteException;
	
	public int getUpdatePDRefInd(String ac_ty,int ac_no,int id)throws RemoteException;
	
	public PygmyMasterObject[] getUnVerifiedPDNo()throws RemoteException;
	
	public int getPAGRefIndUpdate(String ac_type,int ac_no,int id)throws RemoteException;
	
	public AgentMasterObject[] getUnVerifiedAgtNo()throws RemoteException,RecordsNotFoundException;
	
    public int agentUpdate(String agttype,int agentno,String curdate,int type)throws RemoteException;
	
	//public double calculateInterest()throws RemoteException;

	public double calculateInterest(String ac_type,int ac_no,String curdate)throws RemoteException;
	
    public String checkJointAccount(String actype,int personalacno,int jointacno,int personalcid)throws RemoteException,JointAccountNotFound,NotValidJointHolder;

	public int closure(double interest,double fine,double loan_balance,double loan_interest,String pay_mode,String pay_ac_type,int pay_ac_no,String curdate,String curdatetime,int flag,String ac_type,int ac_no,String de_tml,String de_user) throws RemoteException;
    
    public int deleteAgent(String agenttype,int agentno)throws RemoteException;
 
    public AgentMasterObject[] getAgentNos(String agttype,int close_open_ind)throws RemoteException; //To get The Agent Numbers
	
    public AgentMasterObject getAgentDetails(String agttype,int agtno,String date)throws RemoteException;

	public PygmyTransactionObject[] getAgentRemittance(String agttype,int agtno,String colldt,boolean flag)throws RemoteException;
    
	public PygmyTransactionObject[] getAgentInformation(String agttype,int agtno)throws RemoteException,RecordsNotFoundException;
	
	
	public AgentMasterObject[] getAgentReport()throws RemoteException;

	public int getAgentUpdate(AgentMasterObject am,int verify,String curdatetime)throws RemoteException;

	public PygmyMasterObject[] getAgtClose(String agttype,int agentno)throws RemoteException;

	public PygmyMasterObject[] getAccountsUnderAgent(String agttype,int num)throws RemoteException;

	public PygmyTransactionObject[] getAgtWiseRemittanceReport(int agentno,String agent_type,String date)throws RemoteException;

    public double getClosingBalance(String ac_type,int ac_no)throws RemoteException;
	
	public PygmyTransactionObject[] getClosureDetails(String ac_type,int ac_no)throws RemoteException;
	
	public PygmyMasterObject[] getOpenClosedReport(String from,String to,int type) throws RemoteException;

	public PygmyMasterObject getPygmyDetails(String pgtype,int pgno)throws RemoteException,AccountNotFoundException;
	
	public PygmyMasterObject getPygmyDetails()throws RemoteException,AccountNotFoundException;

	public PygmyMasterObject[] getPygmyLedgerReport(int type,String from,String to,int staccno,int endno) throws RemoteException;

	public PygmyTransactionObject[] getPygmyLedgerTransaction(int type,String from,String to,int staccno,int endno)throws RemoteException;

	public PygmyTransactionObject[] getPygmyTransaction(String accType,int acno,String fdate,String tdate)throws RemoteException;

	public PygmyMasterObject[] getUnverifiedPigmyDetails()throws RemoteException;
	
	public int partialWithdraw(double amount,double penalty,String pay_mode,String pay_ac_type,int pay_ac_no,String curdate,String curdatetime)throws RemoteException;

	public int pigmyCalendarQuerterInterestCalc(String uid,String tml,String curdate,String curdatetime,String br_location)throws RemoteException;

	public PygmyTransactionObject[] printAcknowledgementSlips(String agenttype,int agentno,String frm_dt,String to_dt)throws RemoteException;

	public int pygmyAccountDeletion(String actype,int acno)throws RemoteException;

	public int pygmyWithdrwalDeletion(String actype, int accno,int id) throws RemoteException,SQLException;
	
	public int pygmyAccountOpen(PygmyMasterObject pygmymasterobject,String detml,String deuser,String curdatetime,int int_verify) throws SignatureException, RemoteException;

	public QuarterInterestObject[] retrieveInterestRegister(String fdate,String tdate,String query)throws RemoteException;

	public PygmyTransactionObject[] retrieveMonthlyRemit(String type,int no,String fromdate,String todate)throws RemoteException;

	public QuarterInterestObject[] retrieveRegister(String fdate,String tdate,int type)throws RemoteException;

	public int setAgtToCustomers(int[] accno,String agttype1,int num1,String agttype2,int num2)throws RemoteException;

	public int storeDailyRemittance(String agttype,int agtno,Object[][] obj,String colldt,int scrlno,int oldscrl,String detml,String deuser,String curdate,String curdatetime,byte indicator)throws RemoteException;
	
	public int verifyClosure(double loan_bal,double loan_interest,String curdate,String curdatetime,String ac_type,int ac_no,String g_tml,String g_user)throws RemoteException,DateFormatException, AccountClosedException, InOperativeAccountException, FreezedAccountException;

	//public int verifyRemittance(String agttype,int agtno,String colldate,int scrlno,String vetml,String veuser) throws RemoteException,DateFormatException, AccountClosedException, InOperativeAccountException, FreezedAccountException;
	public int verifyRemittance(String agenttype,int agentno,String collection_date,int scrollno,String vetml,String veuser,String curdate,String curdatetime) throws RemoteException,DateFormatException, AccountClosedException, InOperativeAccountException, FreezedAccountException;
	public HashMap getScrollDetails(String ac_type, int agentno,String cur_date,int value) throws RemoteException,RecordsNotFoundException;
    public PygmyRateObject getInterestDetails(int no_of_months,String curdate) throws RemoteException,SQLException;
    
    //Ris
    public int createAgent(String agttype,String agtdesc,String min_period,String detml,String deuser,int opt)throws RemoteException;
    public int createPygmy(ModuleObject mo,String detml,String deuser,int type)throws RemoteException;
    public PygmyRateObject[] getPygmyRate()throws RemoteException;
    public int modifyPygmyRate(String actype,int prdfrm,int prdto,double intrate,int option)throws RemoteException,SQLException;
    
    public int deleteRemittanceTransaction(Object[][] obj,int scroll_no,String collection_dt,String remittance_dt)throws RemoteException,SQLException;
  
    public double getPenaltyAmount(double withAmnt)throws RemoteException,SQLException;
    
    public int updateAgentMaster(AgentMasterObject amo)throws RemoteException;
    
    public int updatePygmyMaster(PygmyMasterObject amo)throws RemoteException;     
            
    public PygmyRateObject[] commissionRatesChange()throws RemoteException;
    
    public int insertCommissionRt(PygmyRateObject comm_object,String tml,String uid,String curdate,String curdatetime)throws RemoteException;
    
    public int PygmyTransactedAccOnDt(String date)throws RemoteException;
    
    public int insertCommissionRow(PygmyRateObject comm_object,String tml,String uid)throws RemoteException;
    
    public int CommissionRtUpdate(PygmyRateObject comm_object,String curdate,int value)throws RemoteException;
    
    public void checkAccountValidation(String actype,int acno,String veuser,String vetml) throws RemoteException,AccountNotFoundException;
    
    public int insert_updatePygmyRate(PygmyRateObject po[],String tml,String user,String datetime,int update_delete_indicator)throws RemoteException,SQLException;
    
    public GLObject[] copyGLdata(String from_ac_type,String to_ac_type,String tml,String user,String date_time)throws RemoteException,SQLException;
    
    public GLObject[] copyGLPost(String from_actype,String to_actype,String tml,String user,String datetime)throws RemoteException,SQLException;
    
    public int checkCid(String cid)throws RemoteException;
    
    public GLObject[] getGLKeyParam(ModuleObject[] mo,int value)throws RemoteException;
    
    public int checkAgentUsage(String ac_type)throws RemoteException;
    
    public GLObject[] getGLPost(ModuleObject[] mo)throws RemoteException;
    
    public GLObject[] getGltranType(ModuleObject[] mo)throws RemoteException;
    
    public ModuleAdminObject getCommissionRate(String agent_type,String date)throws RemoteException;
    
    public int checkJointAccno(int cid,String ac_type,String acno)throws RemoteException;
    
    public AgentMasterObject[] getAgentCloseToVerify(String agttype)throws RemoteException;
    
    public int storeAgentChange(PygmyMasterObject[] pyg_obj,String frm_agtty,int frm_agtno,String to_agtty,int to_agtno,String de_tml,String de_user,String date,int indicator)throws SQLException,RemoteException;
    
    public PygmyMasterObject[] getUnverifiedAgtChange(String frm_agttype,int frm_agtno,String to_agttype,int to_agtno)throws RemoteException;
    
    public int VerifyAgentChange(PygmyMasterObject[] pyg_obj,String frm_agtty,int frm_agtno,String to_agtty,int to_agtno,String ve_tml,String ve_user,String date) throws RemoteException;
    
    public void UpdateRefIndAgtChange(String frm_agtty,int frm_agtno,String to_agtty,int to_agtno)throws RemoteException;
    
    public AgentMasterObject getAgtDetailsForMasterUpdation(String agttype,int agtno,String date)throws RemoteException;
    
    public PygmyTransactionObject[] getAgentRemittanceForUpdate(String agenttype,int agentno,int scroll_no,String collection_date,String remit_date)throws RemoteException;
    
    public String getAgentName(String actype,int acno)throws RemoteException;
    
    public PygmyMasterObject[] getPygmyLedgerReportNew(String from_date,String to_date,int start_accno)throws RemoteException;
    
    public PygmyTransactionObject[] getPygmyLedgerTransactionNew(String from_date,String to_date,int start_accno)throws RemoteException;
    
   
    
   // public AgentMasterObject getAgentNumber(String agtype,int agno)throws RemoteException;
}