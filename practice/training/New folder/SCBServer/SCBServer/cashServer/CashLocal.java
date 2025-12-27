/*
 * Created on Mar 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cashServer;

import java.sql.SQLException;
import javax.ejb.EJBLocalObject;
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
public interface CashLocal extends EJBLocalObject
{
    public int storeVoucher(CashObject co) throws SQLException;
    
    //ship.....09/11/2005
    public int storeToken(CashObject co) throws SQLException;
    //
    
    public int storeDayCash(CashObject co) throws SQLException,InsufficientAmountException,TerminalNotFoundException;
    
    //ship...PO....12/11/2005
    public int verifyDayCashPO(CashObject co) throws SQLException,DateFormatException;
    //
    
    public boolean verifyDayCash(CashObject co) throws SQLException,DateFormatException;
    
    public int closeTerminal(String subtml,String maintml,CashObject co) throws SQLException;
    
    //ship......17/10/2005
    public int updateTerminal(CashObject co) throws SQLException;
    
    public int checkTerminal(String tmlno) throws SQLException;
    //
    //ship......20/10/2005
    public int updateCash(CashObject co) throws SQLException;
    
    public int checkCash(String tmlno) throws SQLException;
    
    public int closeCash(String maintml,String date) throws SQLException;
	//
    
    public void updateCurrency(CashObject co) throws SQLException;
    
    //ship
    public int AcceptMoney(CashObject co,String utml,String scroll) throws SQLException;
    //
    
    public boolean deleteData(int scroll,int type,String date,String time) throws SQLException,ScrollNotFoundException;
    
    //ship.....commented......16/01/2006
    //public int setCurrencyStockObject(CurrencyStockObject cso) throws SQLException;
    //
    
    public void storeVoucherData(VoucherDataObject fd) throws SQLException,RecordNotInsertedException;
    
    public CashObject getData(int sc_no,int type,String date) throws SQLException,ScrollNotFoundException;
    
    //ship....to get unverified scroll nos
    public CashObject[] forVerify(int type,String date) throws SQLException,ScrollNotFoundException;
    //
    
    //public int updateVoucherData(int int_scroll_no,int vchno,String[] gltype,int[] glcode,double[] amount,String date) throws SQLException;
    
    public void deleteVoucherData(int int_vchno,String string_vch_type,String string_tml,String date,String time) throws SQLException;
    
    //ship
    public void deleteVoucherDataTable(int int_vchno,String string_vch_type,String string_tml,String date) throws SQLException;
    //
    
    public boolean verifyVoucherReceipts(CashObject co) throws SQLException;
       
    public double getCashTmlRunningBalance(String utml,String date) throws SQLException;
    
    //ship......06/11/2006
    public double getAllCashTmlRunningBalance(String date) throws SQLException;
    ////////////
        
    //ship......19/06/2006
    //public void setRunningBalance(String rb,String utml) throws SQLException;
    //
    
    public TerminalObject[] getTerminalObject() throws SQLException;
    
    public CashObject[] getDayCashData(String curdate,String tmlno,int flag,String query) throws SQLException,RecordsNotFoundException ;
    
    public CurrencyStockObject getCurrencyStockObject(String tml,String date,int flag) throws SQLException;
    
    public VoucherDataObject getVoucherData(int vch_no,String vch_type,String date) throws SQLException,RecordsNotFoundException;
    
    public String getGLName(String s1) throws SQLException;
    
    public String getGlName(int gl_code) throws SQLException;
    
    public int[] getGLCodes(String date,int type) throws SQLException;
    
    public VoucherDataObject[] getArrayVoucherData(int int_vch_no,String vch_type,String date) throws SQLException,RecordsNotFoundException;
    
    //public String[] getGLTypes() throws SQLException;
    
   // public int storeGLTransaction(GLTransObject trnobj) throws SQLException;
    
    public VoucherDataObject getPaymentData(int int_vch_no,String vch_type,String date) throws SQLException,RecordsNotFoundException;
    
    public CashObject getPaymentDetails(int vch_no,String date) throws SQLException,RecordsNotFoundException;
    
    //for collecting Locker Rent
    public double getLockerRent(String string_locker_ac_type,int int_locker_ac_no,String date) throws SQLException,RecordsNotFoundException;
  
    public String getLockerType(String string_locker_ac_type,int int_locker_ac_no) throws SQLException,RecordsNotFoundException;
    
    public boolean rentCollectByCash(String string_ac_type,int int_ac_no,double double_rent,String date);
    
    //ship.....24/10/2005
    public String getAcctypename(String acctypecode) throws SQLException;
    //
    
    //ship.....21/11/2005.....PO
    public String getCustSubCat(String cust_acctype,String cust_accno) throws SQLException;
    //
    
    //  ship......10/01/2006.....to get gl_code and gl_type for Misc. Rec
    public int[] getGLParam() throws SQLException;
    //
    
    //ship.....25/01/2006
    public boolean checkPAG(int sc_no,String date) throws SQLException;
    //
    
    //ship.....22/05/2006
    public CashObject getDayCashSummary(String curdate,String tmlno) throws SQLException;
    /////////
    
    //ship......23/05/2006
    public boolean checkClosingBalance(String main_tml_no,String date) throws SQLException;
    /////////
    
    //ship......25/05/2006
    public int checkReceiptPending(String sub_tml_no,String date) throws SQLException;
    ///////////
    
    //ship......01/06/2006
    public int checkPaymentPending(String sub_tml_no,String date) throws SQLException;
    ///////////
    
    //ship.....13/06/2006
    public double checkLNAmount(String ln_ac_type,int ln_ac_no,String date,String de_user,String de_tml) throws SQLException;
    //////////////
    
    //ship......23/06/2006
    public int checkTmlOpenClose(String tmlno,String date) throws SQLException;
    ////////////
    
    //ship......05/07/2006
    public int rebalancingScroll(String date,String tml) throws SQLException;
    ///////////
    
    //ship.....06/07/2006
    public String checkDailyStatus(String date,int type) throws SQLException;
    ///////////
    
    //ship.....03/01/2007
    public int isScrollVerified(int scroll_no,String trndate) throws SQLException;
    //////////
}
