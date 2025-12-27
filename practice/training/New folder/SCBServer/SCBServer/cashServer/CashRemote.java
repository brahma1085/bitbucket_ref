/*
 * Created on Mar 14, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cashServer;

import java.rmi.RemoteException;
import java.sql.SQLException;

//import lockers.LockerMasterObject;

import masterObject.cashier.CashObject;
import masterObject.cashier.CurrencyStockObject;
import masterObject.cashier.TerminalObject;
import masterObject.cashier.VoucherDataObject;
import exceptions.DateFormatException;
import exceptions.InsufficientAmountException;
import exceptions.RecordNotInsertedException;
import exceptions.RecordsNotFoundException;
import exceptions.ScrollNotFoundException;
import exceptions.TerminalNotFoundException;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface CashRemote extends javax.ejb.EJBObject
{
    public int storeVoucher(CashObject co) throws RemoteException,SQLException;
    
    //ship.....09/11/2005
    public int storeToken(CashObject co) throws RemoteException,SQLException;
    //
    
    public int storeDayCash(CashObject co) throws RemoteException,SQLException,InsufficientAmountException,TerminalNotFoundException;
    
    //ship...PO....12/11/2005
    public int verifyDayCashPO(CashObject co) throws RemoteException,SQLException, DateFormatException;
    //
    
    public boolean verifyDayCash(CashObject co) throws RemoteException,SQLException,DateFormatException;
    
    public int closeTerminal(String subtml,String maintml,CashObject co) throws RemoteException,SQLException;
    
    //ship......17/10/2005
    public int updateTerminal(CashObject co) throws RemoteException,SQLException;
    
    public int checkTerminal(String tmlno) throws RemoteException,SQLException;
    //
    //ship......20/10/2005
    public int updateCash(CashObject co) throws RemoteException,SQLException;
    
    public int checkCash(String tmlno) throws RemoteException,SQLException;
    
    public int closeCash(String maintml,String date) throws RemoteException,SQLException;
	//
    
    public void updateCurrency(CashObject co) throws RemoteException,SQLException;
    
    //ship
    public int AcceptMoney(CashObject co,String utml,String scroll) throws RemoteException,SQLException;
    //
    
    public boolean deleteData(int scroll,int type,String date,String time) throws RemoteException,SQLException,ScrollNotFoundException;
    
    //ship.....commented......16/01/2006
    //public int setCurrencyStockObject(CurrencyStockObject cso) throws RemoteException,SQLException;
    //
    
    public void storeVoucherData(VoucherDataObject fd) throws RemoteException,SQLException;
    
    public CashObject getData(int sc_no,int type,String date) throws RemoteException,SQLException,ScrollNotFoundException;
    
    //ship....to get unverified scroll nos
    public CashObject[] forVerify(int type,String date) throws RemoteException,SQLException,ScrollNotFoundException;
    //
    
    //public int updateVoucherData(int int_scroll_no,int vchno,String[] gltype,int[] glcode,double[] amount,String date) throws RemoteException,SQLException;
    
    public void deleteVoucherData(int int_vchno,String string_vch_type,String string_tml,String date,String time) throws RemoteException,SQLException;
    
    //ship
    public void deleteVoucherDataTable(int int_vchno,String string_vch_type,String string_tml,String date) throws RemoteException,SQLException;
    //
    
    public boolean verifyVoucherReceipts(CashObject co) throws RemoteException,SQLException;
       
    public double getCashTmlRunningBalance(String utml,String date) throws RemoteException,SQLException;
    
    //ship......06/11/2006
    public double getAllCashTmlRunningBalance(String date) throws RemoteException,SQLException;
    //////////
        
    //ship......19/06/2006
    //public void setRunningBalance(String rb,String utml) throws RemoteException,SQLException;
    //
    
    public TerminalObject[] getTerminalObject() throws RemoteException,SQLException;
    
    public CashObject[] getDayCashData(String curdate,String tmlno,int flag,String query) throws RemoteException ,SQLException,RecordsNotFoundException ;
    
    public CurrencyStockObject getCurrencyStockObject(String tml,String date,int flag) throws RemoteException,SQLException;
    
    public VoucherDataObject getVoucherData(int vch_no,String vch_type,String date) throws RemoteException,SQLException,RecordsNotFoundException;
    
    public String getGLName(String s1) throws RemoteException,SQLException;
    
    public String getGlName(int gl_code) throws RemoteException,SQLException;
    
    public int[] getGLCodes(String date,int type) throws RemoteException,SQLException;
    
    public VoucherDataObject[] getArrayVoucherData(int int_vch_no,String vch_type,String date) throws RemoteException,SQLException,RecordsNotFoundException;
    
    //public String[] getGLTypes() throws RemoteException,SQLException;
    
   // public int storeGLTransaction(GLTransObject trnobj) throws RemoteException,SQLException;
    
    public VoucherDataObject getPaymentData(int int_vch_no,String vch_type,String date)throws SQLException,RecordsNotFoundException,RemoteException;
    
    public CashObject getPaymentDetails(int vch_no,String date) throws SQLException,RecordsNotFoundException,RemoteException;
    
    
    //for collecting Locker Rent
    public double getLockerRent(String string_locker_ac_type,int int_locker_ac_no,String date) throws SQLException,RecordsNotFoundException,RemoteException;
  
    public String getLockerType(String string_locker_ac_type,int int_locker_ac_no) throws SQLException,RecordsNotFoundException,RemoteException;
    
    public boolean rentCollectByCash(String string_ac_type,int int_ac_no,double double_rent,String date) throws RemoteException;
    
    //ship.....24/10/2005
    public String getAcctypename(String acctypecode) throws RemoteException,SQLException;
    //
    
    //ship.....21/11/2005.....PO
    public String getCustSubCat(String cust_acctype,String cust_accno) throws RemoteException,SQLException;
    //
    
    //  ship......10/01/2006.....to get gl_code and gl_type for Misc. Rec
    public int[] getGLParam() throws RemoteException,SQLException;
    //
    
    //ship.....25/01/2006
    public boolean checkPAG(int sc_no,String date) throws RemoteException,SQLException;
    //
    
    //ship.....22/05/2006
    public CashObject getDayCashSummary(String curdate,String tmlno) throws RemoteException,SQLException;
    /////////
    
    //ship......23/05/2006
    public boolean checkClosingBalance(String main_tml_no,String date) throws RemoteException,SQLException;
    /////////
    
    //ship......25/05/2006
    public int checkReceiptPending(String sub_tml_no,String date) throws RemoteException,SQLException;
    ///////////
    
    //ship......01/06/2006
    public int checkPaymentPending(String sub_tml_no,String date) throws RemoteException,SQLException;
    ///////////
    
    //ship.....13/06/2006
    public double checkLNAmount(String ln_ac_type,int ln_ac_no,String date,String de_user,String de_tml) throws RemoteException,SQLException;
    //////////////
    
    //ship......23/06/2006
    public int checkTmlOpenClose(String tmlno,String date) throws SQLException,RemoteException;
    ////////////
    
    //ship......05/07/2006
    public int rebalancingScroll(String date,String tml) throws SQLException,RemoteException;
    ///////////
    
    //ship.....06/07/2006
    public String checkDailyStatus(String date,int type) throws SQLException,RemoteException;
    ///////////
    
    //ship......03/01/2007
    public int isScrollVerified(int scroll_no,String trndate) throws SQLException,RemoteException;
    //////////
    public String[][] getGlCodesNames() throws SQLException,RemoteException;
    
    public VoucherDataObject[] voucherDetails(int vch_no,String vch_type,String date)throws SQLException ,RemoteException,RecordsNotFoundException;
    /*//Added By Shreya
    public String[] getGlNameCode() throws SQLException,RemoteException;
    //Ended
*/}
