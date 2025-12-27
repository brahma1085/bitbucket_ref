package loansOnDepositServer;

import java.rmi.RemoteException;
import java.sql.SQLException;

import masterObject.loansOnDeposit.LoanTransactionObject;
import exceptions.RecordNotUpdatedException;

public interface LoansOnDepositLocal extends javax.ejb.EJBLocalObject
{
	public double getCurrentIntRate(String actype,int acno) throws Exception;
//	 Code added by sanjeet..
	public double getMaxPayableAmtonCurrentDay(String actype,int acno,String date) throws SQLException;
	public Object recoverLDAccount(LoanTransactionObject loan_object) throws RecordNotUpdatedException;
}
