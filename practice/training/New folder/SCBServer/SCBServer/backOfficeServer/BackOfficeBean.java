package backOfficeServer;

import masterObject.backOffice.VoucherDataObject;
import exceptions.DateFormatException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;
import exceptions.ScrollNotFoundException;
import exceptions.VoucherdataObjectNotFoundException;
import masterObject.frontCounter.AccountTransObject;
import masterObject.general.AccountObject;
import masterObject.general.GLMasterObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.UserVerifier;
import masterObject.backOffice.UserObject;
import general.Validations;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

//import com.scb.common.log4j.LogDetails;

import client.HomeFactory;
/*import client.MainScreen;*/
import masterObject.termDeposit.DepositTransactionObject;
import masterObject.loans.LoanTransactionObject;
import loansOnDepositServer.LoansOnDepositHome;
import loansOnDepositServer.LoansOnDepositLocal;
import loansOnDepositServer.LoansOnDepositRemote;
import masterObject.backOffice.AdminObject;
import masterObject.backOffice.ChequeBookObject;
import masterObject.backOffice.ChequeNoObject;
import masterObject.backOffice.ClosingBalObject;
import masterObject.backOffice.OdccObject;
import masterObject.backOffice.OpenedClosedInoperatedAccountObject;
import masterObject.backOffice.PygmyObject;
import masterObject.backOffice.SIDoneObject;
import masterObject.backOffice.SIEntryObject;
import masterObject.backOffice.ShareObject;

import commonServer.CommonLocal;
import commonServer.CommonLocalHome;
import commonServer.GLTransObject;
import masterObject.customer.CustomerMasterObject;
import customerServer.CustomerRemote;

import masterObject.share.ShareMasterObject;
import shareServer.ShareLocal;
import shareServer.ShareLocalHome;

import masterObject.termDeposit.DepositMasterObject;
import termDepositServer.TermDepositLocalHome;
import termDepositServer.TermDepositLocalRemote;

import loanServer.LoanHome;
import loanServer.LoanLocalHome;
import loanServer.LoanRemote;
import loanServer.LoanLocal;

import masterObject.pygmyDeposit.PygmyMasterObject;
import pygmyServer.PygmyLocalHome;
import pygmyServer.PygmyRemote;
import pygmyServer.PygmyLocalRemote;
import pygmyServer.PygmyHome;

import cashServer.CashLocalHome;
import cashServer.CashLocal;


public class BackOfficeBean  implements SessionBean
{
	private static final long serialVersionUID = 1L;
	javax.sql.DataSource ds=null;
	SessionContext ctx=null;
	CommonLocalHome commonlocalhome;
	CommonLocal commonlocal;
	
	ShareLocalHome sharelocalhome;
	ShareLocal sharelocal;
	
	TermDepositLocalHome termdepositlocalhome;
	TermDepositLocalRemote termdepositlocalremote;
	
	LoanLocalHome loanlocalhome;
	LoanLocal loanlocal;
	
	LoansOnDepositHome loansondeposithome;
	LoansOnDepositRemote loansondepositremote;
	
	PygmyLocalHome pygmylocalhome;
	PygmyLocalRemote pygmylocalremote;
	
	LoanHome loanhome;
	LoanRemote loanremote;
	
	/*CashLocalHome cashlocalhome;
	CashLocal cashlocal;
	*/
	Context jndiContext;
	
	
	public BackOfficeBean()
	{
		try{	
			
			
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	public void ejbCreate()
	{
		try
        {
			jndiContext=new InitialContext();			
			ds=(javax.sql.DataSource)jndiContext.lookup("java:MySqlDS");
            
			commonlocalhome=(commonServer.CommonLocalHome)jndiContext.lookup("COMMONLOCALWEB");
			commonlocal=commonlocalhome.create();
			
			sharelocalhome=(shareServer.ShareLocalHome)jndiContext.lookup("SHARELOCALWEB");
			sharelocal=sharelocalhome.create();
			
			termdepositlocalhome=(termDepositServer.TermDepositLocalHome)jndiContext.lookup("TERMDEPOSITLOCALWEB");
			termdepositlocalremote=termdepositlocalhome.create();
			
			loanlocalhome=(loanServer.LoanLocalHome)jndiContext.lookup("LOANSLOCALWEB");
			loanlocal=loanlocalhome.create();
			
  
			loanhome =(loanServer.LoanHome)jndiContext.lookup("LOANSWEB");
			loanremote = loanhome.create();
			/*loanServer.LoanHome loanhome=(loanServer.LoanHome)HomeFactory.getFactory().lookUpHome("LOANS");
			LoanRemote loanremote = loanhome.create();		
			*/
			
			/*pygmylocalhome=(pygmyServer.PygmyLocalHome)ctx.lookup("PYGMYLOCAL");
			pygmylocalremote=pygmylocalhome.create();*/
			
			/*cashlocalhome=(cashServer.CashLocalHome)jndiContext.lookup("CASHLOCAL");
			cashlocal=cashlocalhome.create();*/
			
			loansondeposithome=(LoansOnDepositHome)jndiContext.lookup("LOANSONDEPOSITWEB");
			loansondepositremote=loansondeposithome.create();
		}
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
	}
	
	public void ejbRemove(){}
    
	public void ejbActivate()
	{ 
        Context jndiContext = null;
        
        try 
        {
            jndiContext = new InitialContext();
        } 
        catch (NamingException e) 
        {
            e.printStackTrace();
        }
        try 
        {
            commonlocalhome=(CommonLocalHome)jndiContext.lookup("COMMONLOCAL");
            sharelocalhome=(ShareLocalHome)jndiContext.lookup("SHARELOCAL");
            termdepositlocalhome=(TermDepositLocalHome)jndiContext.lookup("TERMDEPOSITLOCAL");
            loanlocalhome=(LoanLocalHome)jndiContext.lookup("LOANSLOCAL");
            pygmylocalhome=(PygmyLocalHome)jndiContext.lookup("PYGMYLOCAL");
            /*cashlocalhome=(CashLocalHome)jndiContext.lookup("CASHLOCAL");
  */          
        } catch (NamingException e1) {
            e1.printStackTrace();
        }
        try 
        {
            commonlocal = commonlocalhome.create();
            sharelocal = sharelocalhome.create();
            termdepositlocalremote = termdepositlocalhome.create();
            loanlocal = loanlocalhome.create();
            pygmylocalremote = pygmylocalhome.create();
            /*cashlocal = cashlocalhome.create();*/
            
        } catch (CreateException e2) {
            e2.printStackTrace();
        }
	}
	public void ejbPassivate()
	{
		try{
			ctx=null;			
			ds=null;
			
			commonlocalhome=null;
			commonlocal=null;
			
			sharelocalhome=null;
			sharelocal=null;
			
			termdepositlocalhome=null;
			termdepositlocalremote=null;
			
			loanlocalhome=null;
			loanlocal=null;
			
			pygmylocalhome=null;
			pygmylocalremote=null;
			
			/*cashlocalhome=null;
			cashlocal=null;*/
			
		}catch(Exception ex){ex.printStackTrace();}
	}
	public void setSessionContext(SessionContext arg0) throws EJBException,RemoteException {
		ctx=arg0;
		
	}
	
	//Standing Instruction
	
	private  int x = 0,int_sufficient_amt_flag = 0, dep_days;
	//0-transfer,1-from account problem,2-to account problem
	private  double double_dep_amt,double_rd_bal,double_cum_int,double_req_amt;
	private  double double_total;
	AdminObject send[] = null;
	private Statement s4;
	ClosingBalObject[] array_closingbalobject_receive,array_obj_gl_info=null;
	private int acc_no;
	private String acc_type;
	/*private static final String RefAcTy = null;*/
	
	//Riswan..
	
	public int  getCategoryCode(String category)throws SQLException
	{
		System.out.println("Inside getCode....");
		Connection conn=null;
		Statement stmt;
		ResultSet rs=null;
		Object obj[][]=null;
		int code=0;
		try{
			conn=getConnection();
			
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=stmt.executeQuery("select mem_cat from ShareType where catname='"+category+"' ");	
	        rs.next();
	        code=rs.getInt("mem_cat");
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}
		System.out.println("Code : "+code);
		return code;
		}
	public Object[][] getcategoryDetails()
	{
		Connection conn=null;
		Statement stmt;
		ResultSet rs=null;
		Object obj[][]=null;
		try{
			conn=getConnection();
			
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=stmt.executeQuery("select mem_cat,catname from ShareType where ac_type=1001001");
			
			ResultSetMetaData rsmd=rs.getMetaData();
			int col=rsmd.getColumnCount();
			rs.last();
			int row=rs.getRow();
			
			
			obj=new Object[row][col];
					
			rs.beforeFirst();
			
			while(rs.next())
			{
			  for(int i=0; i<row; i++)
			  {
			  	for(int j=0; j<col; j++)
			  	{	
			      obj[i][j]=new Integer(rs.getInt("mem_cat"));
			      //obj[i][j]=new String[rs.getString("catname")];
			      obj[i][j]=new String(rs.getString("catname"));
			   
			      
     			}
	
		}
	}
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e1){e1.printStackTrace();}
		}
		
		return obj;
	}
	
	
	
	//Standing Instruction
	/*
	 * Inserting a row of values in StdInstPrm(Standing Instruction Parameters) table.
	 */
	public void insertdata(int pri,String fr_acc,String to_acc,String tml,String user)	throws RemoteException, SQLException
	{
		System.out.println("-------0-------");
		Connection conn=null;
		try
		{
			conn=getConnection();
			
			System.out.println("-------1-------");
			//conn.setAutoCommit(false);
			PreparedStatement ps;
			ps=conn.prepareStatement("insert StdInstPrm values(?,?,?,?,?,date_format(sysdate(),'%d/%m/%Y %r'))");
			ps.setInt(1, pri);
			ps.setString(2, fr_acc);
			ps.setString(3, to_acc);
			ps.setString(4, tml);
			ps.setString(5, user);
			//ps.setString(6,date);
			ps.executeUpdate();
			ps.close();
			//System.out.println()
			//conn.commit();
			//conn.setAutoCommit(true);
			
			System.out.println("-------2------");
		} catch (Exception excep)
		{
			excep.printStackTrace();
			/*try
			 {
			 //		conn.rollback();
			  //	conn.setAutoCommit(true);
			   
			   }			
			   catch(SQLException se)
			   {
			   se.printStackTrace();
			   }*/
			System.out.println("Exception arised in Insert Details Method");
			
		}
		finally
		{
			conn.close();
		}
	}
	/*
	 * Getting all the rows as AdminObject arrayfrom StdInstPrm table.
	 */
	public AdminObject[] retrieveData() throws RemoteException, SQLException,RecordsNotFoundException
	{
		AdminObject[] obj = null;
		Connection conn=null;
		try
		{
			System.out.println("-------3-------");
			conn=getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs =stmt.executeQuery("select * from StdInstPrm order by priority_no");
			rs.last();
			if(rs.getRow()==0)
				throw new RecordsNotFoundException();
			obj = new AdminObject[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			while (rs.next())
			{
				obj[i] = new AdminObject();
				obj[i].setFromType(rs.getString(2));
				obj[i].setToType(rs.getString(3));
				obj[i].setPriorityNo(rs.getInt(1));
				i++;
			}
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		return obj;
	}
	
	/*
	 * Inserting a row of values to StdInst table using SIEntryObject.
	 */
	public int storeInfo(SIEntryObject obj1) throws RemoteException, SQLException
	{
		int result = 0;
		Connection conn=null;
		try
		{
			System.out.println("-------3-------");
			System.out.println("123456");
			conn=getConnection();
			System.out.println("checking si enteries");
			System.out.println("from acc type  "+obj1.getFromType());
			System.out.println("from acc type  "+obj1.getToType());
			String date=obj1.obj_userverifier.getUserDate();
			
			result = commonlocal.getModulesColumn("std_inst", "1019000");
			
			//ps1 =ServerImpl.conn.prepareStatement("insert StdInst(pri_no,fr_ac_ty,fr_ac_no,to_ac_ty,to_ac_no ,ln_opt,due_dt,prd_mths,prd_days,amount,last_date,noof_time ,expiry_days,de_tml,de_user,de_dt_time ,ve_tml,ve_user,ve_date,alt_de_tml ,alt_de_user,alt_de_dt_timedel_ind) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,date_format(sysdate(),'%d/%m/%Y 	%r'),?,?,null,?,?,null,'F')");
			PreparedStatement ps1 =conn.prepareStatement("insert StdInst(pri_no,fr_ac_ty,fr_ac_no,to_ac_ty,to_ac_no ,ln_opt,due_dt,prd_mths,prd_days,amount,last_date,noof_time ,expiry_days,de_tml,de_user,de_dt_time , si_no) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps1.setInt(1, obj1.getPriorityNo());
			
			ps1.setString(2, obj1.getFromType());
			ps1.setInt(3, obj1.getFromAccNo());
			ps1.setString(4, obj1.getToType());
			ps1.setInt(5, obj1.getToAccNo());
			ps1.setInt(6, obj1.getLoanOpt());
			ps1.setString(7, obj1.getDueDt());
			ps1.setInt(8, obj1.getPeriodMonths());
			ps1.setInt(9, obj1.getPeriodDays());
			ps1.setDouble(10, obj1.getAmount());
			ps1.setString(11, obj1.getLastExec());
			ps1.setInt(12, obj1.getNoExec());
			ps1.setInt(13, obj1.getExpiryDays());
			System.out.println(" Before UserTml----------------");
			ps1.setString(14, obj1.obj_userverifier.getUserTml());
			System.out.println("Server Terminal" + obj1.obj_userverifier.getUserTml());
			ps1.setString(15, obj1.obj_userverifier.getUserId());
			ps1.setString(16, date);
			ps1.setInt(17, result);
			System.out.println("Server User" + obj1.obj_userverifier.getUserId());
			
			ps1.executeUpdate();
			/*if (ps1.executeUpdate() != -1)
			{
				Statement stmt1 =conn.createStatement();
				ResultSet rs2 = stmt1.executeQuery("select * from StdInst");
				rs2.last();
				result = rs2.getInt(1);
				System.out.println("Standing instruction Number inside the getSINo function"+ result);
			}*/
			
			ps1.close();
			
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		System.out.println("Instructuion Number in Bean is ========"+result);
		return result;
		
	}
	
	/*
	 * Getting a set of rows as SIEntryObject array ,which matches with the given account type,account number
	 * from StdInst table .
	 * 
	 */
	public SIEntryObject[] getInfo(String acc_ty, int acc_no,String string_qry) throws SQLException
	{
		SIEntryObject[] entry_obj = null;
		Connection conn=null;
		try
		{
			System.out.println("Query **************"+string_qry);
			int i = 0;
			ResultSet rs2;
			conn=getConnection();
			Statement stmt1 =conn.createStatement();
			if(string_qry!=null)
				rs2 =stmt1.executeQuery("select * from StdInst where fr_ac_no ="+ acc_no+ " and fr_ac_ty='"+ acc_ty+ "' and del_ind='F' and ve_tml is not NULL and ("+string_qry+")");
			else
				rs2 =stmt1.executeQuery("select * from StdInst where fr_ac_no ="+ acc_no+ " and fr_ac_ty='"+ acc_ty+ "' and del_ind='F' and ve_tml is not NULL ");
			
			rs2.last();
			entry_obj = new SIEntryObject[rs2.getRow()];
			rs2.beforeFirst();
			while (rs2.next())
			{
				entry_obj[i] = new SIEntryObject();
				entry_obj[i].setInstNo(rs2.getInt(1));
				System.out.println("ServerInst No" + rs2.getInt(1));
				entry_obj[i].setPriorityNo(rs2.getInt(2));
				entry_obj[i].setDueDt(rs2.getString(7));
				entry_obj[i].setToType(rs2.getString(5));
				entry_obj[i].setToAccNo(rs2.getInt(6));
				entry_obj[i].setAmount(rs2.getFloat(11));
				entry_obj[i].setNoExec(rs2.getInt(14));
				entry_obj[i].setLastExec(rs2.getString(13));
				entry_obj[i].setDelDate(rs2.getString(24));
				entry_obj[i].setLoanOpt(rs2.getInt(10));
				i++;
			}
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		return entry_obj;
	}
	/*
	 * Getting  si_no at the last row (Standing Instruction Number) from StdInst Table.
	 */
	public int getSINo() throws SQLException
	{
		int si_no = 0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt1 = conn.createStatement();
			ResultSet rs2 = stmt1.executeQuery("select * from StdInst");
			rs2.last();
			si_no = rs2.getInt(1);
			System.out.println("Standing instruction Number inside the getSINo function" + si_no);
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		return si_no;
	}
	/*
	 * Getting active Instruction as SIEntryObject when the delete_verify_flag==0(active)
	 * or Getting deleted Instruction as SIEntryObject when the delete_verify_flag==1(deleted),
	 * but the instruction should not be deleted in the past ,so alt_de_tml(terminal through which the instruction is deleted) is checked for null.
	 
	 */
	public SIEntryObject getInfoThruSiNo(int si_no, int delete_verify_flag) throws SQLException
	{
		SIEntryObject entry_obj = null;
		Connection conn=null;
		try
		{
			conn=getConnection();
			System.out.println("*********************Inside getInfoThru SINO***********"+si_no+"************"+delete_verify_flag);
			entry_obj = new SIEntryObject();
			Statement stmt1 = conn.createStatement();
			ResultSet rs2 =stmt1.executeQuery("select * from StdInst where si_no="+ si_no+ " ");
			rs2.next();
			if (rs2.getRow() != 0)
			{
				System.out.println("del ind aiyoooooooooooooo  "+rs2.getString("del_ind"));
				if(delete_verify_flag==1)
				{
					System.out.println("del ind  "+rs2.getString("del_ind"));
					if(rs2.getString("del_ind").equals("T"))
					{
						entry_obj.setInstNo(-2);
						return entry_obj;
					}
				}
				else if(delete_verify_flag==2)
				{
					System.out.println("del verify  "+rs2.getString("alt_ve_tml"));
					if(rs2.getString("alt_ve_tml")!=null)
					{
						entry_obj.setInstNo(-3);
						return entry_obj;
					}
				}
				else if(delete_verify_flag==0)
				{
					//System.out.println("del verify  "+rs2.getString("alt_ve_tml"));
				//	if(rs2.getString("alt_ve_tml")!=null)
					//{
						entry_obj.setInstNo(rs2.getInt("si_no"));
						//return entry_obj;
					//}
				}
				
				entry_obj.setPriorityNo(rs2.getInt("pri_no"));
				entry_obj.setFromType(rs2.getString("fr_ac_ty"));
				entry_obj.setFromAccNo(rs2.getInt("fr_ac_no"));
				entry_obj.setToType(rs2.getString("to_ac_ty"));
				entry_obj.setToAccNo(rs2.getInt("to_ac_no"));
				entry_obj.setLoanOpt(rs2.getInt("ln_opt"));
				entry_obj.setDueDt(rs2.getString("due_dt"));
				entry_obj.setPeriodMonths(rs2.getInt("prd_mths"));
				entry_obj.setPeriodDays(rs2.getInt("prd_days"));
				entry_obj.setAmount(rs2.getFloat("amount"));
				entry_obj.setAmtAdj(rs2.getFloat("amt_adj"));
				entry_obj.setLastExec(rs2.getString("last_date"));
				entry_obj.setNoExec(rs2.getInt("noof_time"));
				entry_obj.setExpiryDays(rs2.getInt("expiry_days"));
				System.out.println("**********Ver Tml from StdInst	*********"+rs2.getString("ve_tml"));
				entry_obj.obj_userverifier.setVerTml(rs2.getString("ve_tml"));
				System.out.println("**********After setting verTml in uv object"+entry_obj.obj_userverifier.getVerTml());
				entry_obj.obj_userverifier.setVerId(rs2.getString("ve_user"));
				entry_obj.obj_userverifier.setVerDate(rs2.getString("ve_dt_time"));
				entry_obj.setAltDeTml(rs2.getString("alt_de_tml"));
				entry_obj.setAltVeTml(rs2.getString("alt_ve_tml"));
				System.out.println("The valu of Del_ind-----> "+rs2.getString("del_ind"));
				entry_obj.setDelInd(rs2.getString("del_ind"));
				
			} else if(rs2.getRow()==0){
				return null;
			}
				entry_obj.setInstNo(-1);
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		System.out.println("Entry obj in bean ----------"+entry_obj);
		return entry_obj;
	}
	
	/*
	 * Deleting a row which matches with the given si_no from StdInst table.
	 */
	public void delete(int si_no, String tml, String uid,String userdate,int verify_flag) throws SQLException
	{
		Connection conn=null;
		try
		{
			System.out.println("Inside Server SIDelete");
			System.out.println("SI NO==="+si_no);
			System.out.println("VERIFY FLAG==="+verify_flag);
			conn=getConnection();
			PreparedStatement ps1=null;
			if (verify_flag == 0)
				ps1 =conn.prepareStatement("update StdInst set del_ind='T',alt_de_tml='"+ tml+ "',alt_de_user='"+ uid+ "',alt_de_dt_time='"+userdate+"' where si_no=?");
			else
				ps1 =conn.prepareStatement("update StdInst set alt_ve_tml='"+ tml+ "',alt_ve_user='"+ uid+ "',alt_ve_dt_time='"+userdate+"' where si_no=? and del_ind='T'");
			
			ps1.setInt(1, si_no);
			ps1.executeUpdate();
			
			ps1.close();
			
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		finally
		{
			conn.close();
		}
	}
	
	/*
	 * Updating a row, which matches with the instruction number from the given SIEntryObject, using
	 * all other values from that SIEntryObject. 
	 */
	public int update(SIEntryObject si1) throws SQLException
	{
		int re = 0;
		Connection conn=null;
		try
		{
			System.out.println("Inside update");
			System.out.println("After getting uid");
			System.out.println("Befor Update");
			conn=getConnection();
			
			PreparedStatement ps1 =conn.prepareStatement("update StdInst set pri_no=?,fr_ac_ty=? ,fr_ac_no=?, to_ac_ty=? ,to_ac_no=? ,ln_opt=? ,due_dt=?,prd_mths=?,prd_days=?,amount=?,last_date=?,noof_time=? ,expiry_days=? where si_no=? ");
			System.out.println("Befor Update");
			ps1.setInt(1, si1.getPriorityNo());
			ps1.setString(2, si1.getFromType());
			ps1.setInt(3, si1.getFromAccNo());
			ps1.setString(4, si1.getToType());
			ps1.setInt(5, si1.getToAccNo());
			ps1.setInt(6, si1.getLoanOpt());
			ps1.setString(7, si1.getDueDt());
			ps1.setInt(8, si1.getPeriodMonths());
			ps1.setInt(9, si1.getPeriodDays());
			System.out.println("After period days settings");
			ps1.setDouble(10, si1.getAmount());
			ps1.setString(11, si1.getLastExec());
			ps1.setInt(12, si1.getNoExec());
			ps1.setInt(13, si1.getExpiryDays());
			ps1.setInt(14, si1.getInstNo());
			System.out.println("After setting Instruction No");
			re = ps1.executeUpdate();
			System.out.println("After execute update");
			
			ps1.close();
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		return re;
	}
	/*
	 * Getting a set of rows as SIEntryObject array from StdInst table ,
	 * the rows should have their del_ind='F' when the given choice is 1(active instn)
	 * otherwise the rows should have their del_ind='T' when the given choice is 0(deleted instn)
	 * where the due date of those rows must be in between the given from and to date.s
	 */
	public SIEntryObject[] getInstInfo(int choice,String from_date,String to_date,String string_qry) throws SQLException
	{
		SIEntryObject si_obj[] = null;
		
		System.out.println("Inside getInstInfo function");
		Connection conn=null;
		try
		{
			System.out.println("QUERY"+string_qry);
			
			int i = 0;
			conn=getConnection();
			
			Statement stmt1 = conn.createStatement();
			ResultSet rs2 = null;
			if(string_qry!=null && string_qry.trim().length()!=0)
			{
				System.out.println("****************Inside executing query for query option in report******************");
				if (choice == 0)
				{
					System.out.println("Inside executing query for deletyed instruction");
					rs2 =stmt1.executeQuery("select * from StdInst where  concat(right(SUBSTRING(alt_de_dt_time,1,10),4),'-',mid(SUBSTRING(alt_de_dt_time,1,10),locate('/',SUBSTRING(alt_de_dt_time,1,10))+1, (locate('/',SUBSTRING(alt_de_dt_time,1,10),4)-locate('/',SUBSTRING(alt_de_dt_time,1,10))-1)),'-',left(SUBSTRING(alt_de_dt_time,1,10),locate('/',SUBSTRING(alt_de_dt_time,1,10))-1))  between concat(right('"+ from_date+ "',4),'-',mid('"+ from_date+ "',locate('/','"+ from_date+ "')+1, (locate('/','"+ from_date+ "',4)-locate('/','"+ from_date+ "')-1)),'-',left('"+ from_date+ "',locate('/','"+ from_date+ "')-1)) and concat(right('"+ to_date+ "',4),'-',mid('"+ to_date+"',locate('/','"+ to_date+ "')+1, (locate('/','"+ to_date+ "',4)-locate('/','"+ to_date+ "')-1)),'-',left('"+ to_date+ "',locate('/','"+ to_date+ "')-1)) and del_ind='T' and ("+string_qry+")  order by si_no" );
					
				}
				//rs2 =stmt1.executeQuery("select * from StdInst where  concat(right(due_dt,4),'-',mid(due_dt,locate('/',due_dt)+1, (locate('/',due_dt,4)-locate('/',due_dt)-1)),'-',left(due_dt,locate('/',due_dt)-1))  between concat(right('"+ from_date+ "',4),'-',mid('"+ from_date+ "',locate('/','"+ from_date+ "')+1, (locate('/','"+ from_date+ "',4)-locate('/','"+ from_date+ "')-1)),'-',left('"+ from_date+ "',locate('/','"+ from_date+ "')-1)) and concat(right('"+ to_date+ "',4),'-',mid('"+ to_date+ "',locate('/','"+ to_date+ "')+1, (locate('/','"+ to_date+ "',4)-locate('/','"+ to_date+ "')-1)),'-',left('"+ to_date+ "',locate('/','"+ to_date+ "')-1)) and del_ind='T' order by si_no");	
				if (choice == 1&& !from_date.equals(""))
				{
					System.out.println(" inside choice 1  ");
					rs2 =stmt1.executeQuery("select * from StdInst where  concat(right(due_dt,4),'-',mid(due_dt,locate('/',due_dt)+1, (locate('/',due_dt,4)-locate('/',due_dt)-1)),'-',left(due_dt,locate('/',due_dt)-1))  between concat(right('"+ from_date+ "',4),'-',mid('"+ from_date+ "',locate('/','"+ from_date+ "')+1, (locate('/','"+ from_date+ "',4)-locate('/','"+ from_date+ "')-1)),'-',left('"+ from_date+ "',locate('/','"+ from_date+ "')-1)) and concat(right('"+ to_date+ "',4),'-',mid('"+ to_date+ "',locate('/','"+ to_date+ "')+1, (locate('/','"+ to_date+ "',4)-locate('/','"+ to_date+ "')-1)),'-',left('"+ to_date+ "',locate('/','"+ to_date+ "')-1)) and del_ind='F' and ("+string_qry+") order by si_no");
				}//
				if (from_date.equals("") && to_date.equals(""))
					{
					System.out.println("date from and to");
					rs2 =stmt1.executeQuery("select * from StdInst where del_ind='F' and si_no="+choice+ " and ("+string_qry+") ");
					}
				
			}
			else
			{
				if (choice == 0)
				{
					System.out.println("Inside executing query for deletyed instruction");
					rs2 =stmt1.executeQuery("select * from StdInst where  concat(right(SUBSTRING(alt_de_dt_time,1,10),4),'-',mid(SUBSTRING(alt_de_dt_time,1,10),locate('/',SUBSTRING(alt_de_dt_time,1,10))+1, (locate('/',SUBSTRING(alt_de_dt_time,1,10),4)-locate('/',SUBSTRING(alt_de_dt_time,1,10))-1)),'-',left(SUBSTRING(alt_de_dt_time,1,10),locate('/',SUBSTRING(alt_de_dt_time,1,10))-1))  between concat(right('"+ from_date+ "',4),'-',mid('"+ from_date+ "',locate('/','"+ from_date+ "')+1, (locate('/','"+ from_date+ "',4)-locate('/','"+ from_date+ "')-1)),'-',left('"+ from_date+ "',locate('/','"+ from_date+ "')-1)) and concat(right('"+ to_date+ "',4),'-',mid('"+ to_date+"',locate('/','"+ to_date+ "')+1, (locate('/','"+ to_date+ "',4)-locate('/','"+ to_date+ "')-1)),'-',left('"+ to_date+ "',locate('/','"+ to_date+ "')-1)) and del_ind='T' order by si_no");
					
				}
				//rs2 =stmt1.executeQuery("select * from StdInst where  concat(right(due_dt,4),'-',mid(due_dt,locate('/',due_dt)+1, (locate('/',due_dt,4)-locate('/',due_dt)-1)),'-',left(due_dt,locate('/',due_dt)-1))  between concat(right('"+ from_date+ "',4),'-',mid('"+ from_date+ "',locate('/','"+ from_date+ "')+1, (locate('/','"+ from_date+ "',4)-locate('/','"+ from_date+ "')-1)),'-',left('"+ from_date+ "',locate('/','"+ from_date+ "')-1)) and concat(right('"+ to_date+ "',4),'-',mid('"+ to_date+ "',locate('/','"+ to_date+ "')+1, (locate('/','"+ to_date+ "',4)-locate('/','"+ to_date+ "')-1)),'-',left('"+ to_date+ "',locate('/','"+ to_date+ "')-1)) and del_ind='T' order by si_no");	
				if (choice == 1 && !from_date.equals(""))
					{
					System.out.println("123456");
					rs2 =stmt1.executeQuery("select * from StdInst where  concat(right(due_dt,4),'-',mid(due_dt,locate('/',due_dt)+1, (locate('/',due_dt,4)-locate('/',due_dt)-1)),'-',left(due_dt,locate('/',due_dt)-1))  between concat(right('"+ from_date+ "',4),'-',mid('"+ from_date+ "',locate('/','"+ from_date+ "')+1, (locate('/','"+ from_date+ "',4)-locate('/','"+ from_date+ "')-1)),'-',left('"+ from_date+ "',locate('/','"+ from_date+ "')-1)) and concat(right('"+ to_date+ "',4),'-',mid('"+ to_date+ "',locate('/','"+ to_date+ "')+1, (locate('/','"+ to_date+ "',4)-locate('/','"+ to_date+ "')-1)),'-',left('"+ to_date+ "',locate('/','"+ to_date+ "')-1)) and del_ind='F' order by si_no");
					}
				//
				if (from_date.equals("") && to_date.equals(""))
				{
					System.out.println("###########################         1         ###################");	
					rs2 =stmt1.executeQuery("select * from StdInst where del_ind='F' and si_no="+choice+ "");
					System.out.println("###########################    2        ###################");
				}
			}
			System.out.println("outside choice ");
			
			if (rs2 != null )
			{
				rs2.last();
				si_obj = new SIEntryObject[rs2.getRow()];
				rs2.last();
				System.out.println("inside if  ");
				rs2.beforeFirst();
				while (rs2.next())
				{
					System.out.println("in while  ");
					si_obj[i] = new SIEntryObject();
					si_obj[i].setInstNo(rs2.getInt("si_no"));
					si_obj[i].setPriorityNo(rs2.getInt("pri_no"));
					si_obj[i].setFromType(rs2.getString("fr_ac_ty"));
					System.out.println("from type in form  "+rs2.getString("fr_ac_ty"));
					si_obj[i].setFromAccNo(rs2.getInt("fr_ac_no"));
					si_obj[i].setToType(rs2.getString("to_ac_ty"));
					System.out.println("to type in form  "+rs2.getString("to_ac_ty"));
					
					si_obj[i].setToAccNo(rs2.getInt("to_ac_no"));
					si_obj[i].setLoanOpt(rs2.getInt("ln_opt"));
					si_obj[i].setDueDt(rs2.getString("due_dt"));
					si_obj[i].setPeriodMonths(rs2.getInt("prd_mths"));
					si_obj[i].setPeriodDays(rs2.getInt("prd_days"));
					si_obj[i].setAmount(rs2.getFloat("amount"));
					si_obj[i].setAmtAdj(rs2.getFloat("amt_adj"));
					si_obj[i].setLastExec(rs2.getString("last_date"));
					si_obj[i].setNoExec(rs2.getInt("noof_time"));
					si_obj[i].setExecTime(rs2.getInt("exec_time"));
					si_obj[i].setExpiryDays(rs2.getInt("expiry_days"));
					//changed
					//si_obj[i].setName(rs2.getString("name"));
					//System.out.println("name"+rs2.getString("name"));
					System.out.println("setting values for object+i");
					i++;
				}
			} 
			
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		return si_obj;
	}
	/*
	 * Retrieving a set of rows as SIDoneObject array from StdInst table 
	 * where the due date of these rows should be in between the given from and to date.
	 */
	
	public SIEntryObject[] getInstInfoForDue(String from_date, String to_date,String string_qry) throws SQLException
	{
		SIEntryObject[] obj = null;
		Connection conn=null;
		try
		{
			conn=getConnection();
			
			ResultSet rs2;
			Statement stmt1 = conn.createStatement();
			System.out.println("From date from server" + from_date);
			System.out.println("To date from server" + to_date);
			if(string_qry!=null)
				rs2 =stmt1.executeQuery("select  fr_ac_ty, fr_ac_no,si_no,pri_no,amount,to_ac_ty,to_ac_no,ln_opt from StdInst where  concat(right(due_dt,4),'-',mid(due_dt,locate('/',due_dt)+1, (locate('/',due_dt,4)-locate('/',due_dt)-1)),'-',left(due_dt,locate('/',due_dt)-1))  between concat(right('"+ from_date+ "',4),'-',mid('"+ from_date+ "',locate('/','"+ from_date+ "')+1, (locate('/','"+ from_date+ "',4)-locate('/','"+ from_date+ "')-1)),'-',left('"+ from_date+ "',locate('/','"+ from_date+ "')-1)) and concat(right('"+ to_date+ "',4),'-',mid('"+ to_date+ "',locate('/','"+to_date+ "')+1, (locate('/','"+ to_date+ "',4)-locate('/','"+ to_date+ "')-1)),'-',left('"+ to_date+ "',locate('/','"+ to_date+ "')-1)) and del_ind='F' and ("+string_qry+")order by si_no;");
			else
				rs2 =stmt1.executeQuery("select  fr_ac_ty, fr_ac_no,si_no,pri_no,amount,to_ac_ty,to_ac_no,ln_opt from StdInst where  concat(right(due_dt,4),'-',mid(due_dt,locate('/',due_dt)+1, (locate('/',due_dt,4)-locate('/',due_dt)-1)),'-',left(due_dt,locate('/',due_dt)-1))  between concat(right('"+ from_date+ "',4),'-',mid('"+ from_date+ "',locate('/','"+ from_date+ "')+1, (locate('/','"+ from_date+ "',4)-locate('/','"+ from_date+ "')-1)),'-',left('"+ from_date+ "',locate('/','"+ from_date+ "')-1)) and concat(right('"+ to_date+ "',4),'-',mid('"+ to_date+ "',locate('/','"+to_date+ "')+1, (locate('/','"+ to_date+ "',4)-locate('/','"+ to_date+ "')-1)),'-',left('"+ to_date+ "',locate('/','"+ to_date+ "')-1)) and del_ind='F' order by si_no;");
			rs2.last();
			obj = new SIEntryObject[rs2.getRow()];
			System.out.println("Number of rows retrieved" + rs2.getRow());
			rs2.beforeFirst();
			int i = 0;
			while (rs2.next())
			{
				obj[i] = new SIEntryObject();
				obj[i].setFromType(rs2.getString(1));
				obj[i].setFromAccNo(rs2.getInt(2));
				obj[i].setInstNo(rs2.getInt(3));
				obj[i].setPriorityNo(rs2.getInt(4));
				obj[i].setAmount(rs2.getFloat(5));
				obj[i].setToType(rs2.getString(6));
				obj[i].setToAccNo(rs2.getInt(7));
				obj[i].setLoanOpt(rs2.getInt(8));
				i++;
			}
			
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		return obj;
	}
	/*
	 * Retrieving a set of rows as SIDoneObject array from StdInstDone table. 
	 * where the trn date of these rows should be in between the given from and to date.
	 */
	public SIDoneObject[] getInstInfoForDone(String from_date, String to_date,String string_qry) throws SQLException
	{
		System.out.println("Inside Done function in StdImpl"+from_date+"  "+to_date);
		SIDoneObject obj[] = null;
		Connection conn=null;
		try
		{
			int i = 0;
			conn=getConnection();
			Statement stmt1 = conn.createStatement();
			ResultSet rs2;
			if(string_qry!=null)
				rs2 =stmt1.executeQuery("select si_no,trn_amt,due_dat,prin_amt,int_amt,penal_int_amt,other_amt,int_date from StdInstDone where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))  between concat(right('"+ from_date+ "',4),'-',mid('"+ from_date+ "',locate('/','"+ from_date+ "')+1, (locate('/','"+ from_date+ "',4)-locate('/','"+ from_date+ "')-1)),'-',left('"+ from_date+ "',locate('/','"+ from_date+ "')-1)) and concat(right('"+ to_date+ "',4),'-',mid('"+ to_date+ "',locate('/','"+to_date+ "')+1, (locate('/','"+ to_date+ "',4)-locate('/','"+ to_date+ "')-1)),'-',left('"+ to_date+ "',locate('/','"+ to_date+ "')-1)) and ("+string_qry+") order by si_no");
			else
				rs2 =stmt1.executeQuery("select si_no,trn_amt,due_dat,prin_amt,int_amt,penal_int_amt,other_amt,int_date from StdInstDone where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))  between concat(right('"+ from_date+ "',4),'-',mid('"+ from_date+ "',locate('/','"+ from_date+ "')+1, (locate('/','"+ from_date+ "',4)-locate('/','"+ from_date+ "')-1)),'-',left('"+ from_date+ "',locate('/','"+ from_date+ "')-1)) and concat(right('"+ to_date+ "',4),'-',mid('"+ to_date+ "',locate('/','"+to_date+ "')+1, (locate('/','"+ to_date+ "',4)-locate('/','"+ to_date+ "')-1)),'-',left('"+ to_date+ "',locate('/','"+ to_date+ "')-1)) order by si_no");			
			rs2.last();
			if(rs2.getRow()!=0)
			{
			obj = new SIDoneObject[rs2.getRow()];
			System.out.println("After execution of retrieving records for done records:no of rex"+ rs2.getRow());
			rs2.beforeFirst();
			
				
			while (rs2.next())
			{
				obj[i] = new SIDoneObject();
				System.out.println("INSTRUCTION NUMBER" + rs2.getInt("si_no"));
				obj[i].setSiNo(rs2.getInt("si_no"));
				System.out.println("Number after setting" + obj[i].getSiNo());
				obj[i].setDueDt(rs2.getString("due_dat"));
				obj[i].setIntDt(rs2.getString("int_date"));
				obj[i].setTrnAmt(rs2.getDouble("trn_amt"));
				obj[i].setPrinAmt(rs2.getDouble("prin_amt"));
				obj[i].setIntAmt(rs2.getDouble("int_amt"));
				obj[i].setPenalAmt(rs2.getDouble("penal_int_amt"));
				obj[i].setOtherAmt(rs2.getDouble("other_amt"));
				i++;
			}
			}
			
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		System.out.println("Object in Beamn-------"+obj);
		return obj;
	}
	
	String getSysDate() 
    {
        Calendar c=Calendar.getInstance();
        try {
			return(Validations.checkDate(c.get(Calendar.DATE)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR)));
		} catch (DateFormatException e) {
			e.printStackTrace();
		}
		return null;
    }
	
	/*
	 * Crediting to a particular loan deposit account with the given amount
	 */
	////Neetha....24/May/06.....
	public void transferToLoanDeposit(String AccType,int AccNo,double amtpaid,int type,int si_no,String fr_type,String fr_ac_no,String user,String terminal,String date,String datetime)throws RemoteException
	{
	    System.out.println("AccType == "+AccType);
	    System.out.println("AccNo == "+AccNo);
	    System.out.println("amtpaid == "+amtpaid);
	    System.out.println("type == "+type);
	    System.out.println("si_no == "+si_no);
	    System.out.println("fr_type == "+fr_type);
	    System.out.println("fr_ac_no == "+fr_ac_no);
	    System.out.println("user == "+user+" terminal == "+terminal);
	    
	    Connection conn=null;
	    
		try
		{
			conn=getConnection();
            Statement stmt=conn.createStatement();
            Statement stmt1= conn.createStatement();
            
            int trn_seq=0;
            ResultSet rs=stmt1.executeQuery("select trn_seq,pr_bal,int_date,other_amt from LoanTransaction where ac_type='"+AccType+"' and ac_no="+AccNo+" order by trn_seq desc");
            rs.next();
            trn_seq=rs.getInt(1)+1;
            double prnbal=rs.getDouble("pr_bal");
            String intupto=rs.getString("int_date");
            //co.setAmount(co.getAmount()+rs.getDouble("other_amt"));
            long intamttopay=Math.round((prnbal*Validations.dayCompare(intupto,Validations.addDays(date,-1))*20)/(365*100));
            
            PreparedStatement pstmt1= conn.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt1.setString(1,AccType);//lnactype
            pstmt1.setInt(2,AccNo);//lnacno
            pstmt1.setInt(3,trn_seq);//lst trn seq		
            pstmt1.setString(4,date);//lst trn dt
            pstmt1.setString(5,"R");
            pstmt1.setDouble(6,amtpaid);
            pstmt1.setString(7,"T");
            pstmt1.setString(8,terminal);
            pstmt1.setInt(9,si_no);
            pstmt1.setString(10,"SI Excec");
            pstmt1.setString(11,null);
            pstmt1.setString(12,"C");
            pstmt1.setString(13,date);
            double intamt=0.0;
            double pramt=0.0,otheramt=0.0;
            
            if(amtpaid>=intamttopay)
            {
                pstmt1.setString(13,Validations.addDays(date,-1));
                intamt=intamttopay;
                pramt=amtpaid-intamt;		
                prnbal=prnbal-pramt;
            }
            else
            {
                double perday=(prnbal*20)/(365*100);
                int a=(int)Math.floor(amtpaid/perday);
                intamt=Math.round(perday*a);
                otheramt=amtpaid-intamt;
                pstmt1.setString(13,Validations.addDays(intupto,a));		
            }
            
            pstmt1.setDouble(14,pramt);					
            pstmt1.setDouble(15,intamt);
            pstmt1.setDouble(16,0);
            pstmt1.setDouble(17,otheramt);
            pstmt1.setDouble(18,0);
            pstmt1.setDouble(19,prnbal);	
            
            pstmt1.setString(20,terminal);//de tml
            pstmt1.setString(21,user);//de user			
            pstmt1.setString(22,datetime);//de date			
            pstmt1.setString(23,terminal);//ve tml
            pstmt1.setString(24,user);//ve user
            if(pstmt1.executeUpdate()==0)
                throw new SQLException();
            
            if(stmt.executeUpdate("update LoanMaster set lst_tr_seq="+trn_seq+",lst_trn_date='"+datetime+"' where ac_type='"+AccType+"' and ac_no="+AccNo+" ")==0)
                throw new SQLException();
            GLTransObject trnobj=new GLTransObject();
            ResultSet rs1 = stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1019001' and code=1");
            rs1.next();
            trnobj.setGLCode(rs1.getString("gl_code"));
            trnobj.setCdind("C");
            trnobj.setTrnDate(datetime);
            trnobj.setGLType(rs1.getString("gl_type"));
            trnobj.setTrnMode("T");
            trnobj.setAmount(amtpaid);
            trnobj.setCdind("C");
            trnobj.setAccType(AccType);
            trnobj.setAccNo(String.valueOf(AccNo));
            trnobj.setTrnSeq(trn_seq);
            trnobj.setTrnType("T");
            trnobj.setRefNo(si_no);
            trnobj.setVtml(terminal);
            trnobj.setVid(user);
            commonlocal.storeGLTransaction(trnobj);
        
			
		}catch(Exception exception){exception.printStackTrace();}
        
        finally
        {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	/*
	 * Executing a standing Instruction (row)from StdInst table which matches with the given si_no(parameter n)
	 * and inserting the details to StdInstDone table. 
	 */
	public String[] stdExec(int int_si_no,String string_user,String string_tml,String date,String datetime) throws SQLException
	{
		String[] array_string_details;
		array_string_details = new String[9];
		int int_from_mod_index = 0;
		double	double_amt_adj=0.0;	
		String string_changed_due_date;
		Connection conn=null;
		
		try
		{
			conn=getConnection();	
			System.out.println("Inside stdExec");
			SIEntryObject[] array_sientryobject = null;
			int j;
			double double_trn_amt, int_amt = 0;
			System.out.println("Inside Standard Execution");
			ModuleObject[] array_moduleobject =commonlocal.getMainModules(1, "");
			System.out.println("Before calling Information for StdInst ");
			array_sientryobject = getInstInfo(int_si_no,"","",null);
			System.out.println("After calling Information for stdinst ");
			String string_from_module, string_to_module;
			
			if(array_sientryobject != null) {
				if(array_sientryobject[0].getToType().startsWith("1010")){
					array_string_details = stdLoanExec(int_si_no,string_user,string_tml,date,datetime);
					if(array_string_details==null){
						array_string_details=new String[9];
						array_string_details[8]="Update Error";
					}
					return array_string_details;
				}
				
				if(array_sientryobject[0].getToType().startsWith("1004")){
					array_string_details = stdRDExec(int_si_no,string_user,string_tml,date,datetime);
					if(array_string_details==null){
						array_string_details=new String[9];
						array_string_details[8]="Update Error";
					}
					return array_string_details;
				}
				
				if(array_sientryobject[0].getToType().startsWith("1002") || array_sientryobject[0].getToType().startsWith("1007")){
					array_string_details = stdSBCAExec(int_si_no,string_user,string_tml,date,datetime);
					if(array_string_details==null){
						array_string_details=new String[9];
						array_string_details[8]="Update Error";
					}
					return array_string_details;
				}
			}
			
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		
		finally
		{
			conn.close();
		}
		return array_string_details;
	}
	
	/*
	 * Getting the number of active instruction from StdInst Table
	 */
	
	 public VoucherDataObject[] VoucherVerfifyPayment(int type,String date) throws RemoteException,SQLException,VoucherdataObjectNotFoundException
	 
	 {
        	Connection conn=null;
        	ResultSet rs=null;
        	PreparedStatement psmt=null;
        	int voucherdata;
        	int row=0;
        	VoucherDataObject[] voucherdataobject=null;
        	try
			{
        		conn=getConnection();
        		if(type==0){
        		psmt=conn.prepareStatement("select distinct vch_no,sum(trn_amt) as trn_amt,gl_sl_type,gl_sl_code from VoucherData where  trn_date='"+date+"' and vch_type='P' and ve_user is null group by vch_no") ;
        			rs=psmt.executeQuery();
        			rs.last();
        			row=rs.getRow();
        			if(row==0)
        				throw new VoucherdataObjectNotFoundException();
        			
        				voucherdataobject=new VoucherDataObject[rs.getRow()];
        				rs.beforeFirst();
                		int i = 0;
                		while(rs.next())
                		{
                			voucherdataobject[i]=new VoucherDataObject();
                			voucherdataobject[i].setVoucherNo(rs.getInt("vch_no"));
                			voucherdataobject[i].setTransactionAmount(rs.getDouble("trn_amt"));
                			voucherdataobject[i].setGlType(rs.getString("gl_sl_type"));
                			 voucherdataobject[i].setGlCode(rs.getInt("gl_sl_code"));
                			 
                			 i++;
                		}
                		System.out.println("the length of voucherdata is"+voucherdataobject.length);
        		}		
				
        		  /*if(type==1)
        		   {
            			psmt=conn.prepareStatement("select vch_no,sum(trn_amt) as trn_amt from VoucherData where trn_date='"+date+"' and ve_user is null group by vch_no order  by vch_no");
            			rs=psmt.executeQuery();
            			rs.last();
            			row = rs.getRow();
            			if(row==0)
            				throw new VoucherdataObjectNotFoundException();
            			
                		voucherdataobject=new VoucherDataObject[rs.getRow()];
        				rs.beforeFirst();
                		int i = 0;
                		while(rs.next()){
                			voucherdataobject[i]=new VoucherDataObject();
                			voucherdataobject[i].setVoucherNo(rs.getInt("vch_no"));
                			voucherdataobject[i].setTransactionAmount(rs.getDouble("trn_amt"));
                			i++;
                    	}*/
                  //}
                	
        		           		
        		
			}
	 
        		catch(SQLException ex)
			{
	           	ex.printStackTrace();
	           	throw ex;
			}
	        finally
			{
	           	conn.close();
			}
        		return voucherdataobject;
			}

	 public VoucherDataObject[] VoucherVerfifyTransfer(int type,String date) throws RemoteException,SQLException,VoucherdataObjectNotFoundException
        	
	 {
	 	Connection conn=null;
    	ResultSet rs=null;
    	PreparedStatement psmt=null;
    	int voucherdata;
    	int row=0;
    	VoucherDataObject[] voucherdataobject=null;
    	try
		{
    		conn=getConnection();
	 
	 	if(type==1)
		   {
 			psmt=conn.prepareStatement("select vch_no,sum(trn_amt) as trn_amt  from VoucherData where trn_date='"+date+"' and vch_type='T'and cd_ind = 'C' and ve_user is null group by vch_no order  by vch_no");
 			rs=psmt.executeQuery();
 			rs.last();
 			row = rs.getRow();
 			if(row==0)
 				throw new VoucherdataObjectNotFoundException();
 			
     		voucherdataobject=new VoucherDataObject[rs.getRow()];
				rs.beforeFirst();
     		int i = 0;
     		while(rs.next()){
     			voucherdataobject[i]=new VoucherDataObject();
     			voucherdataobject[i].setVoucherNo(rs.getInt("vch_no"));
     			voucherdataobject[i].setTransactionAmount(rs.getDouble("trn_amt"));
     			i++;
         	}
       } else if(type == 2) {
    	   psmt=conn.prepareStatement("select vch_no,trn_amt  from VoucherData where trn_date='"+date+"' and vch_type='P'and ve_user is null and cd_ind = 'C'  order  by vch_no;");
			rs=psmt.executeQuery();
			rs.last();
			row = rs.getRow();
			if(row==0)
				throw new VoucherdataObjectNotFoundException();
			
    		voucherdataobject=new VoucherDataObject[rs.getRow()];
				rs.beforeFirst();
    		int i = 0;
    		while(rs.next()){
    			voucherdataobject[i]=new VoucherDataObject();
    			voucherdataobject[i].setVoucherNo(rs.getInt("vch_no"));
    			voucherdataobject[i].setTransactionAmount(rs.getDouble("trn_amt"));
    			i++;
        	}
       }
	 
		}
    	catch(SQLException ex)
		{
           	ex.printStackTrace();
           	throw ex;
		}
        finally
		{
           	conn.close();
		}
    		return voucherdataobject;
		}
  
	public int getNoOfActiveInst() throws SQLException
	{
		int i = 0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt1 =conn.createStatement();
			ResultSet rs2 =stmt1.executeQuery("select count(*) as count from StdInst where del_ind='F' and ve_tml!='null'");
			rs2.next();
			i = rs2.getInt("count");
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		return i;
	}
	
	public int[] getInstnsForExec(String date) throws SQLException
	{
		int std_no[]=null;
		Connection conn=null;
		int index=0;
		try
		{
			conn=getConnection(); 
			Statement 	stmt =conn.createStatement();
			date=Validations.convertYMD(date);
			System.out.println("Date========="+date);
			System.out.println("Date from Server"+date);
			
			//ResultSet rs2=stmt1.executeQuery("select si_no as instn_no from StdInst where del_ind='F' and ve_tml is not NULL and alt_ve_tml is NULL and due_dt like '"+date+"' and noof_time!=exec_time");
			//ResultSet rs=stmt.executeQuery("select si_no as instn_no from StdInst where del_ind='F' and ve_tml is not NULL and concat(right(due_dt,4),'-',mid(due_dt,locate('/',due_dt)+1,(locate('/',due_dt,4)-locate('/',due_dt)-1)),'-',left(due_dt,locate('/',due_dt)-1))<='"+date+"' and noof_time>exec_time");
			stmt.addBatch("drop table if exists temp_std ");
			stmt.addBatch("create temporary table temp_std ( si_no int(20), due_date date,expiry_days int(5),trn_date date)");
			stmt.addBatch("insert into  temp_std select si_no, (concat(right(due_dt,4),'-',mid(due_dt,locate('/',due_dt)+1,(locate('/',due_dt,4)-locate('/',due_dt)-1)),'-',left(due_dt,locate('/',due_dt)-1))) as due_date,expiry_days,'"+date+"'  as trn_date from StdInst where del_ind='F' and ve_tml is not NULL and (concat(right(due_dt,4),'-',mid(due_dt,locate('/',due_dt)+1,(locate('/',due_dt,4)-locate('/',due_dt)-1)),'-',left(due_dt,locate('/',due_dt)-1)))<='"+date+"' and noof_time>exec_time and (concat(right(last_date,4),'-',mid(last_date,locate('/',last_date)+1,(locate('/',last_date,4)-locate('/',last_date)-1)),'-',left(last_date,locate('/',last_date)-1)))>='"+date+"' ");
			stmt.executeBatch();
			System.out.println("****************************************");
			ResultSet rs = stmt.executeQuery("select si_no  from temp_std where date_add(due_date, interval expiry_days day)>='"+date+"' or expiry_days=0 order by due_date");//Here changed by renuka

			if(rs.next()){
				rs.last();
				std_no=new int[rs.getRow()];
				rs.beforeFirst();
			}
			rs.beforeFirst();
			while(rs.next()){
				
				System.out.println(rs.getInt("si_no"));
				std_no[index]=rs.getInt("si_no");
				System.out.println("STDArray>> "+std_no[index]);
				index++;
				
			}
			System.out.println("****************************************");
		} catch (Exception exe){
			System.out.println(exe.getMessage());
			//exe.printStackTrace();
			}
		finally{ conn.close(); }
		System.out.println("the std_no-----------"+std_no);
		return std_no;
	}
	
	/*
	 * updating a row in StdInst table which matches with the given si_no using the other parameters
	 * for making the that standing instruction entry as verified . 
	 */
	
	public void verify(int si_no, String tml, String user, String date) throws RemoteException,SQLException
	{
		Connection conn=null;
		try
		{
			conn=getConnection();
			
			PreparedStatement ps2 =conn.prepareStatement("update StdInst set ve_tml=?,ve_user=?,ve_dt_time='"+date+"' where si_no=?");
			ps2.setString(1, tml);
			ps2.setString(2, user);
			ps2.setInt(3, si_no);
			ps2.executeUpdate();
			
			ps2.close();
			//conn.commit();
			//conn.setAutoCommit(true);
			
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		finally
		{
			conn.close();
		}
	}
	
	//Admin for standing Instruction
	
	/*
	 * Retrieving all the sub module codes from Modules table.
	 */
	public String[] getAcType() throws RemoteException,SQLException
	{
		String a[]=null;
		Connection conn=null;
		try
		{
			conn=getConnection();
			String qry = "select * from modules where modulecode NOT LIKE '%000'";
			Statement s1 = conn.createStatement();
			ResultSet rs = s1.executeQuery(qry);
			
			if (rs.next())//;
			{
				rs.last();
				a = new String[rs.getRow()];
				rs.beforeFirst();
			}
			int index = 0;
			while (rs.next())
			{
				a[index++] = rs.getString(3);
			}
			
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		
		finally
		{
			conn.close();
		}
		return a;
	}
	
	/*
	 *Inserting a new row to StdInstPrm table using AdminObject  
	 * 
	 */
	public int insertDetails(AdminObject obj) throws RemoteException ,SQLException
	{
		Connection conn=null;
		try
		{
			conn=getConnection();
			
			PreparedStatement ps =conn.prepareStatement("insert into StdInstPrm values(?,?,?,?,?,?)");
			ps.setInt(1, obj.getPriorityNo());
			ps.setString(2, obj.getFromType());
			ps.setString(3, obj.getToType());
			ps.setString(4, obj.obj_userverifier.getUserTml());
			ps.setString(5, obj.obj_userverifier.getUserId());
			ps.setString(6, obj.getDate());
			System.out.println("Inside Insert Details ");
			
			int res=ps.executeUpdate();
			
			ps.close();
			
			return (res);
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		
		finally
		{
			conn.close();
		}
		
		return 0;
	}
	
	/**
	 * Retrieving all the rows as AdminObject array from the StdInstPrm table 
	 */
	public AdminObject[] getDetails() throws RemoteException,SQLException
	{
		String qry ="Select priority_no,m1.moduleabbr,m1.moduledesc,m2.moduleabbr,m2.moduledesc from StdInstPrm,Modules m1,Modules m2 where StdInstPrm.fr_type=m1.modulecode and StdInstPrm.to_type=m2.modulecode ORDER BY priority_no";
		send = null;
		Connection conn=null;
		
		try
		{
			conn=getConnection();
			System.out.println("String returned by local common ejb"+commonlocal.getHeading());
			Statement s1 = conn.createStatement();
			ResultSet rs1 = s1.executeQuery(qry);
			
			if (rs1.next())
			{
				rs1.last();
				send = new AdminObject[rs1.getRow()];
				rs1.beforeFirst();
			}
			
			int i = 0;
			
			while (rs1.next())
			{
				send[i] = new AdminObject();
				send[i].setPriorityNo(rs1.getInt(1));
				send[i].setFromType(rs1.getString(2));
				send[i].setFrTypeDesc(rs1.getString(3));
				send[i].setToType(rs1.getString(4));
				send[i].setToTypeDesc(rs1.getString(5));
				
				i++;
			}
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		
		finally
		{
			conn.close();
		}
		return send;
	}
	
	/**
	 * Validating the entered record with the existing records in StdInstPrm table
	 * to avoid redundant records
	 */
	public int validate(int p_no, String fromtype, String totype) throws SQLException
	{
		System.out.println(fromtype + " " + totype);
		System.out.println("p no  "+p_no);
		String qry = "select * from StdInstPrm";
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement s2 = conn.createStatement();
			ResultSet rs2 = s2.executeQuery(qry);
			while (rs2.next())
			{
				System.out.println(
						"From type from stdinstprm table" + rs2.getString(2));
				System.out.println("From type from  parameters" + fromtype);
				if (fromtype.equals(rs2.getString(2)))
				{
					System.out.println("from type matched");
					System.out.println(
							"to type from stdinstprm table" + rs2.getString(3));
					System.out.println("to type from  parameters" + totype);
					if (totype.equals(rs2.getString(3)))
					{
						System.out.println("to type matched");
						return 0;
					}
				}
			}
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		return 1;
	}
	/**
	 * Deleting the record in StdInstPrm table which matches the parameters.
	 */
	
	public int delete(int[] xx, String[] yy, String[] zz) throws RemoteException,SQLException
	{
		Connection conn=null;
		try
		{
			conn=getConnection();
			System.out.println("Inside Delete no of rows" + xx.length);
			
			for (int i = 0; i < xx.length; i++)
			{
				PreparedStatement ps1 =conn.prepareStatement("delete from StdInstPrm where priority_no=? and fr_type=? and to_type=?");
				ps1.setInt(1, xx[i]);
				ps1.setString(2, yy[i]);
				ps1.setString(3, zz[i]);
				
				ps1.executeUpdate();
			}
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		return 1;
	}
	
	/**
	 * Updating the record in StdInstPrm table which matches with the given parameters 
	 */
	public void update(AdminObject aobj1, int prno, String a, String b) throws RemoteException,SQLException
	{
	    System.out.println("a == "+a+" b== "+b);
		Connection conn=null;
		try
		{
			conn=getConnection();
			//PreparedStatement ps2 =conn.prepareStatement("update StdInstPrm SET fr_type=?,to_type=? where priority_no=? and fr_type is null and to_type is null");
			//renuka changed
			PreparedStatement ps2 =conn.prepareStatement("update StdInstPrm SET fr_type=?,to_type=? where priority_no=?");
			ps2.setString(1, aobj1.getFromType());
			ps2.setString(2, aobj1.getToType());
			
			ps2.setInt(3, prno);
			//changes
			//	           		ps2.setString(4,a);
			//	           		ps2.setString(5,b);
			
			ps2.executeUpdate();
			ps2.close();
			
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		
		finally
		{
			conn.close();
		}
	}
	/**
	 *Getting the maximum priority number from StdInstPrm table.
	 */
	public int getMaxRowNo() throws RemoteException,SQLException
	{
		int max = 0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			String qry = "select MAX(priority_no) from StdInstPrm";
			Statement s3 = conn.createStatement();
			ResultSet rs3 = s3.executeQuery(qry);
			rs3.next();
			max = Integer.parseInt(rs3.getString(1));
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		
		finally
		{
			conn.close();
		}
		
		return max;
	}
	/**
	 * Setting null values for from type and to type fields in StdInstPrm table which matches with the given parameters.
	 */
	public void setNull(int prno_table, String a_table, String b_table) throws RemoteException,SQLException
	{
		Connection conn=null;
		try
		{
			conn=getConnection();
			
			PreparedStatement ps1 =conn.prepareStatement("update StdInstPrm set fr_type=null,to_type=null where priority_no=? and fr_type=? and to_type=?");
			ps1.setInt(1, prno_table);
			ps1.setString(2, a_table);
			ps1.setString(3, b_table);
			
			ps1.executeUpdate();
			
		} catch (Exception excep)
		{
			excep.printStackTrace();
		}
		
		finally
		{
			conn.close();
		}
	}
	
	public VoucherDataObject[] getFaDataDetails(int vch_no) throws RemoteException,SQLException
	{
		Connection conn =null;
		VoucherDataObject voucherdataobject[] =null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			//ResultSet rs=stmt.executeQuery("select * from VoucherData where vch_no="+vch_no+" && vch_type='P' and cd_ind='D';");
			ResultSet rs=stmt.executeQuery("select * from VoucherData where vch_no="+vch_no+" and  vch_type='P' ");
			rs.last();
			voucherdataobject= new VoucherDataObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			
			{
				voucherdataobject[i]=new VoucherDataObject();
				voucherdataobject[i].setVoucherType(rs.getString("vch_type"));
				voucherdataobject[i].setVoucherNo(rs.getInt("vch_no"));
				voucherdataobject[i].setGlType(rs.getString("gl_sl_type"));
				voucherdataobject[i].setGlCode(rs.getInt("gl_sl_code"));
                voucherdataobject[i].setNarration(rs.getString("narration"));
                voucherdataobject[i].setCdIndicator(rs.getString("cd_ind"));
				voucherdataobject[i].setTransactionAmount(rs.getFloat("trn_amt"));
				voucherdataobject[i].setTransactionDate(rs.getString("trn_date"));
				voucherdataobject[i].obj_userverifier.setUserId(rs.getString("de_user"));
				voucherdataobject[i].obj_userverifier.setUserTml(rs.getString("de_tml"));
				voucherdataobject[i].obj_userverifier.setVerId(rs.getString("ve_user"));
				voucherdataobject[i].obj_userverifier.setVerTml(rs.getString("ve_tml"));
				voucherdataobject[i].obj_userverifier.setUserDate(rs.getString("de_date"));
				voucherdataobject[i].obj_userverifier.setVerDate(rs.getString("ve_date"));
				
				i++;
			}
		}catch(Exception exception){exception.printStackTrace();}
		
		finally
		{
			conn.close();
		}
		return voucherdataobject;
		
	}
	
//////////////////method included by neetha on 6/3/06 for the verification form of transfer voucher
	
	public VoucherDataObject getFaDataTransferDetails(int vch_no) throws RemoteException,SQLException
	{
		System.out.println("loop of fadata transfer details");
		Connection conn =null;
		VoucherDataObject voucherdataobject =null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			ResultSet rs=stmt.executeQuery("select * from VoucherData where vch_no="+vch_no+" and vch_type='T' ");
			
			rs.last();
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			
			{
				voucherdataobject=new VoucherDataObject();
				voucherdataobject.setVoucherType(rs.getString("vch_type"));
				voucherdataobject.setVoucherNo(rs.getInt("vch_no"));
				voucherdataobject.setModuleAccountType(rs.getString("mod_ac_type"));
				voucherdataobject.setModuleAccountNo(rs.getInt("mod_ac_no"));
				
				voucherdataobject.setGlType(rs.getString("gl_sl_type"));
				voucherdataobject.setGlCode(rs.getInt("gl_sl_code"));
                voucherdataobject.setNarration(rs.getString("narration"));
                voucherdataobject.setTransactionType(rs.getString("trn_type"));
                voucherdataobject.setCdIndicator(rs.getString("cd_ind"));
				voucherdataobject.setTransactionAmount(rs.getFloat("trn_amt"));
				voucherdataobject.setTransactionDate(rs.getString("trn_date"));
				voucherdataobject.obj_userverifier.setUserId(rs.getString("de_user"));
				voucherdataobject.obj_userverifier.setUserTml(rs.getString("de_tml"));
				voucherdataobject.obj_userverifier.setUserDate(rs.getString("de_date"));
				
				voucherdataobject.obj_userverifier.setVerId(rs.getString("ve_user"));
				voucherdataobject.obj_userverifier.setVerTml(rs.getString("ve_tml"));
				voucherdataobject.obj_userverifier.setVerDate(rs.getString("ve_date"));
				
				i++;
			}
		}catch(Exception exception){exception.printStackTrace();}
		
		finally
		{
			conn.close();
		}
		return voucherdataobject;
		
	}
	
	
	
	public VoucherDataObject[] getFaData(String fdate,String todate,String vchtyp,String string_query) throws RemoteException,SQLException,RecordsNotFoundException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		VoucherDataObject fdo[]=null;
		UserVerifier obj_userverifer=null;
		String date=null;
		
		System.out.println("fdate in server "+fdate);
		System.out.println("todate in server "+todate);
		System.out.println("string_query "+string_query);
		
		try
		{
			conn=getConnection();
            
            //Misc Rec
            if(vchtyp.equals("R"))
            {
                System.out.println("inside R");
                if(string_query==null)
                {
                    System.out.println("inside string");
                    ps=conn.prepareStatement("select * from VoucherData where vch_type='"+vchtyp+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between ? and ? and ve_user is not null order by trn_date,vch_no,cd_ind asc");
                    System.out.println("inside after select   q");
                }
                else
                    ps=conn.prepareStatement("select * from VoucherData where vch_type='"+vchtyp+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between ? and ? and ("+string_query+") and ve_user is not null order by trn_date,vch_no,cd_ind asc");
                ps.setString(1,Validations.convertYMD(fdate));
                ps.setString(2,Validations.convertYMD(todate));
            }
            //Payment Vch
            else if(vchtyp.equals("P"))
			{
				if(string_query==null)
					ps=conn.prepareStatement("select * from VoucherData where vch_type='"+vchtyp+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between ? and ? and ve_user is  not null order by trn_date,vch_no ,cd_ind desc");
				else
					ps=conn.prepareStatement("select * from VoucherData where vch_type='"+vchtyp+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between ? and ? and ("+string_query+") and ve_user is not null order by trn_date,vch_no ,cd_ind desc");
                
				ps.setString(1,Validations.convertYMD(fdate));
				ps.setString(2,Validations.convertYMD(todate));
			}
            //Trf Vch
			else if(vchtyp.equals("T"))
			{
				if(string_query==null)
					ps=conn.prepareStatement("select * from VoucherData where vch_type='"+vchtyp+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between ? and ? and ve_user is  not null order by trn_date,vch_no,cd_ind asc");
				else
					ps=conn.prepareStatement("select * from VoucherData where vch_type='"+vchtyp+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between ? and ? and ("+string_query+") and ve_user is not null order by trn_date,vch_no,cd_ind asc");
				   
				ps.setString(1,Validations.convertYMD(fdate));
				ps.setString(2,Validations.convertYMD(todate));
			}
            //ship.....04/06/2007
            //Csh Payment Vch
            else if(vchtyp.equals("C"))
            {
                if(string_query==null)
                    ps=conn.prepareStatement("select * from DayCash where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between ? and ? and scroll_no>0 and ac_type is not null and ac_no is not null and vch_type is not null and vch_no is not null and ve_user is not null order by trn_date,ac_type,ac_no asc");
                else
                    ps=conn.prepareStatement("select * from DayCash where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between ? and ? and scroll_no>0 and ac_type is not null and ac_no is not null and vch_type is not null and vch_no is not null and ve_user is not null and ("+string_query+") order by trn_date,ac_type,ac_no asc");
                   
                ps.setString(1,Validations.convertYMD(fdate));
                ps.setString(2,Validations.convertYMD(todate));
            }
            /////////
            
			rs=ps.executeQuery();
			
            //ship.....04/06/2007
			/*rs.last();
			System.out.println("No. of records available is "+rs.getRow());
			if(rs.getRow()==0)
				throw new RecordsNotFoundException();
			System.out.println("There are no Records");
			
			fdo=new VoucherDataObject[rs.getRow()];
			rs.beforeFirst();*/
            
            if(rs.last())
            {
                if(rs.getRow()>0)
                {
                    fdo = new VoucherDataObject[rs.getRow()];
                    rs.beforeFirst();
                }
                else
                {
                    System.out.println("There are no Records");
                    return fdo;
                }
            }
            /////////
            
			int i=0;
            
			while(rs.next())
			{
                //ship......04/06/2007
                if(vchtyp.equals("C"))
                {
                    fdo[i]=new VoucherDataObject();
                    
                    fdo[i].setTransactionDate(rs.getString("trn_date"));
                    
                    fdo[i].setModuleAccountNo(rs.getInt("ac_no"));
                    fdo[i].setModuleAccountType(rs.getString("ac_type"));
                    
                    fdo[i].setVoucherType(rs.getString("vch_type"));
                    fdo[i].setVoucherNo(rs.getInt("vch_no"));
                    
                    //Deposit(FD) & Share...interest payment 'I'
                    if(rs.getString("vch_type").equals("I"))
                    {
                        fdo[i].setNarration("Interest Payment");
                    }
                    //Shares, Deposit & Pygmy....withdrawal 'W'
                    else if(rs.getString("vch_type").equals("W"))
                    {
                        fdo[i].setNarration("Cash Withdrawal");
                    }
                    //Shares, Deposit & Pygmy....Account Closure 'C'
                    else if(rs.getString("vch_type").equals("C"))
                    {
                        fdo[i].setNarration("Account Closure");
                    }
                    else
                    {
                        fdo[i].setNarration("Csh Pay Vch");
                    }
                    
                    fdo[i].setTransactionAmount(rs.getDouble("csh_amt"));
                    fdo[i].setCdIndicator(rs.getString("cd_ind"));
                    
                    fdo[i].obj_userverifier.setUserId(rs.getString("de_user"));
                    fdo[i].obj_userverifier.setUserTml(rs.getString("de_tml"));
                    fdo[i].obj_userverifier.setUserDate(rs.getString("de_date"));
                    
                    fdo[i].obj_userverifier.setVerId(rs.getString("ve_user"));
                    fdo[i].obj_userverifier.setVerTml(rs.getString("ve_tml"));
                    fdo[i].obj_userverifier.setVerDate(rs.getString("ve_date"));
                }
                ////////
                else
                {
                    fdo[i]=new VoucherDataObject();
                    
                    fdo[i].setTransactionDate(rs.getString("trn_date"));
                    fdo[i].setVoucherType(rs.getString("vch_type"));
                    fdo[i].setVoucherNo(rs.getInt("vch_no"));
                    fdo[i].setGlType(rs.getString("gl_sl_type"));
                    fdo[i].setGlCode(rs.getInt("gl_sl_code"));
                   
                    //added by renuka
                    String glname=getGlName(fdo[i].getGlCode(),fdate);
                    fdo[i].setGlname(glname);
                    
                    GLMasterObject  glm=getGLMasterDetails(fdo[i].getGlCode(),fdate);
                    fdo[i].setGlm(glm);
                   //added by renuka 
                    obj_userverifer = new UserVerifier();
                    
					obj_userverifer.setUserId(rs.getString("de_user"));
                    obj_userverifer.setVerId(rs.getString("ve_user"));
                    fdo[i].setObj_userverifier(obj_userverifer);
                    
                   // fdo[i].obj_userverifier.setUserId(rs.getString("de_user"));
                    fdo[i].obj_userverifier.setUserTml(rs.getString("de_tml"));
                    //fdo[i].obj_userverifier.setVerId(rs.getString("ve_user"));
                    fdo[i].obj_userverifier.setVerTml(rs.getString("ve_tml"));
                    
                    fdo[i].obj_userverifier.setUserDate(rs.getString("de_date"));
                    fdo[i].obj_userverifier.setVerDate(rs.getString("ve_date"));
                    
                    fdo[i].setNarration(rs.getString("narration"));
                    fdo[i].obj_userverifier.setVerTml(rs.getString("ve_user"));
                    
                    fdo[i].setTransactionAmount(rs.getDouble("trn_amt"));
                    fdo[i].setChequeNo(rs.getInt("chq_no"));
                    fdo[i].setChequeDate(rs.getString("chq_dt"));
                    fdo[i].setCdIndicator(rs.getString("cd_ind"));
                    fdo[i].setModuleAccountNo(rs.getInt("mod_ac_no"));
                    fdo[i].setModuleAccountType(rs.getString("mod_ac_type"));
                }
				
				i++;
			}
		}
		catch(Exception ex)
		{
            ex.printStackTrace();
        }
		finally
		{
			conn.close();
		}
        
		return fdo;
	}
	
	public  GLMasterObject  getGLMasterDetails(int glcd,String date) throws RemoteException,SQLException
	{
		System.out.println("inside glmasterobject");
		GLMasterObject glm =new GLMasterObject();
		Connection conn=null;
		try
		{
			conn=getConnection();	
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select * from GLMaster where gl_code="+glcd+" and ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))");
			rs.last();
			System.out.println("Number of rows in glmasterdetails"+rs.getRow());
			rs.beforeFirst();
			if(rs.next())
			{
				glm.setGLType(rs.getString("gl_type"));
				glm.setGLCode(rs.getString("gl_code"));
				glm.setGLName(rs.getString("gl_name"));
				glm.setSCHType(rs.getString("sch_type"));
				glm.setGLStatus(rs.getString("gl_status"));
				glm.setNormalCd(rs.getString("normal_cd"));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		return glm;
	}
	
	public int deleteVoucherDataVoucher(int vchno) throws RemoteException,SQLException
	{
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stat=conn.createStatement();
			//for(int i=0;i<gltype.length;i++)
			//{
				stat.executeUpdate("delete from VoucherData where vch_no="+vchno);
				
			//}
		}catch(Exception Exception){Exception.printStackTrace();}
		finally
		{
			conn.close(	);
		}
		return 1;
	}
	
	public int deleteTransferDataVoucher(int int_vchno,String vch_type,String date) throws RemoteException,SQLException
	{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		Connection conn=null;
		//int rs=0;
		try
		{
			conn=getConnection();
			Statement stat=conn.createStatement();
			System.out.println("Delete Vch-no--------"+int_vchno);
			System.out.println("Delete Vch-no--------"+vch_type);
			System.out.println("Delete Vch-no--------"+date);

			stat.executeUpdate("delete from VoucherData where vch_no="+int_vchno+" and vch_type='"+vch_type+"' and trn_date='"+date+"' ");
			//System.out.println("No of rows affected "+rs);
			
	  }
		catch(Exception r)
		{
			r.printStackTrace();
		}
		finally
		{
			conn.close(	);
		}
		return 1 ;
	}
	public int deleteVoucherData(int vchno,String[] gltype,int[] glcode,double[] amount) throws RemoteException, SQLException
	{
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stat=conn.createStatement();
			for(int i=0;i<gltype.length;i++)
			{
				stat.executeUpdate("delete from VoucherData where vch_no="+vchno+" and gl_sl_type='"+gltype[i]+"' and gl_sl_code="+glcode[i]+" and trn_amt="+amount[i]+" ");
			}
		}catch(Exception Exception){Exception.printStackTrace();}
		finally
		{
			conn.close(	);
		}
		return 1;
	}
	public int deleteVoucherDataPayment(int vch_no)throws RemoteException,SQLException
	{
		
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stat=conn.createStatement();
			
				stat.executeUpdate("delete from VoucherData where vch_no="+vch_no+" ");
			
		}catch(Exception Exception){Exception.printStackTrace();}
		finally
		{
			conn.close();
		}
		return 1;
	}
	
	public int deleteTransferVoucher(VoucherDataObject[] vobj) throws RemoteException,SQLException
	{
		System.out.println(" delete transfer voucher function ");
		
		Connection conn=null;
		try
		{
			
			conn=getConnection();
			System.out.println("-------111-------");
			PreparedStatement ps;
			
			for(int n=0; n<vobj.length; n++){
			ps=conn.prepareStatement("delete from VoucherData where vch_no=? and vch_type='T';");
			
			ps.setInt(1, vobj[n].getVoucherNo());
			
			ps.executeUpdate();
			}
			//ps.close();
			
			return 1;
			
		} catch (Exception excep)
		{
			excep.printStackTrace();
			
			System.out.println("Exception in Delete Details Method");
			
		}
		finally
		{
			conn.close();
		}
		return 0;
		
	}
	
		
	
	public UserObject getUserObject(String user) throws RemoteException,SQLException
	{
		UserObject uo=new UserObject();
		ResultSet rs=null;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery("select sname from Users where uid='"+user+"' ");
			if(rs.next())
			{
				uo.setSName(rs.getString("sname"));
				System.out.println("sname="+rs.getString("sname"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			conn.close();
		}
		return uo;
		
	}
	
	public AccountTransObject[] getAccountTransaction(int accno,String acctype) throws RemoteException,SQLException,RecordsNotFoundException
	{
		Connection conn=null;
		AccountTransObject array_accounttransobject[]=null;
				
		try
		{
			ResultSet res=null;
			conn=getConnection();
			Statement stmt=conn.createStatement();
			System.out.println("accno in server "+accno);
			System.out.println("Acctype="+acctype);
			
			if(acctype.startsWith("1002")||acctype.startsWith("1007"))
				res=stmt.executeQuery("select * from AccountTransaction where ac_type='"+acctype+"' and ac_no="+accno+" ");
			else if(acctype.startsWith("1014")||acctype.startsWith("1015"))
				res=stmt.executeQuery("select * from ODCCTransaction where ac_type='"+acctype+"' and ac_no="+accno+" ");
				
			res.last();
			System.out.println("No of rows in acctrans obj "+res.getRow());
			if(res.getRow()!=0)
			{				
				array_accounttransobject=new AccountTransObject[res.getRow()];
				res.beforeFirst();
				int p=0;
				while(res.next())
				{
					array_accounttransobject[p]=new AccountTransObject();
					array_accounttransobject[p].setTransDate(res.getString("trn_date"));
					array_accounttransobject[p].setChqDDNo(res.getInt("chq_dd_no"));
					array_accounttransobject[p].setPayeeName(res.getString("payee_nm"));
					array_accounttransobject[p].setTransNarr(res.getString("trn_narr"));
					array_accounttransobject[p].setCdInd(res.getString("cd_ind"));
					array_accounttransobject[p].setTransAmount(res.getDouble("trn_amt"));
					array_accounttransobject[p].setCloseBal(res.getDouble("cl_bal"));
					array_accounttransobject[p].uv.setUserId(res.getString("de_user"));
					array_accounttransobject[p].uv.setVerId(res.getString("ve_user"));
					System.out.println("\n");
					p++;
				}
			}
			else
				throw new RecordsNotFoundException();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		finally
		{
			conn.close();
		}
		
		return array_accounttransobject;
	}
	
	/*public AccountObject[] getAccount(String acctype,int fromacno,int toacno,String fromdate,String todate) throws RemoteException,RecordsNotFoundException
	{
		AccountObject ao[]=null;
		Connection conn=null;
		try
		{
			conn=getConnection();
			System.out.println("Acctype from server in getAcc"+acctype);
			System.out.println("Fromacno from server in getAcc"+fromacno);
			System.out.println("toacno from server in getAcc"+toacno);
			System.out.println("From date "+fromdate);
			ResultSet res=null;
			Statement stmt=conn.createStatement();
			System.out.println(".....0");
			if(fromdate==null && toacno!=0) //to retreive data given fromacno and toacno
			{
				System.out.println(".....1");
				//				res=stmt.executeQuery("select distinct concat(fname,' ',mname,' ',lname)as name,cl_bal,trn_date,ac_status,AccountMaster.ve_user,AccountMaster.ac_no,CustomerMaster.cid,intro_name,ac_opendate,sub_category,nom_no,ch_bk_issue,int_pbl_date from CustomerMaster,AccountMaster,AccountTransaction where CustomerMaster.cid=AccountMaster.cid and AccountMaster.ac_no=AccountTransaction.ac_no and AccountMaster.ac_ty=AccountTransaction.ac_ty and AccountMaster.ac_ty='"+acctype+"' and AccountMaster.ac_no between "+fromacno+" and "+toacno+" and ac_status!='C' order by AccountTransaction.ac_no asc,AccountTransaction.trn_seq  asc");
				//				res=stmt.executeQuery("select distinct concat(IFNULL(fname,''),' ',IFNULL(mname,''),' ',IFNULL(lname,'')) as name,cl_bal,trn_date,ac_status,AccountMaster.ve_user,AccountMaster.ac_no,CustomerMaster.cid,ac_opendate,sub_category,nom_no,ch_bk_issue,int_pbl_date from CustomerMaster,AccountMaster,AccountTransaction where CustomerMaster.cid=AccountMaster.cid and AccountMaster.ac_no=AccountTransaction.ac_no and AccountMaster.ac_type=AccountTransaction.ac_type and AccountMaster.ac_type='"+acctype+"' and AccountMaster.ac_no between "+fromacno+" and "+toacno+" and ac_status!='C' order by AccountTransaction.ac_no asc,AccountTransaction.trn_seq  asc");
				res=stmt.executeQuery("select distinct concat(IFNULL(fname,''),' ',IFNULL(mname,''),' ',IFNULL(lname,'')) as name,ac_status,AccountMaster.ve_user,AccountMaster.ac_no,CustomerMaster.cid,ac_opendate,sub_category,nom_no,ch_bk_issue,int_pbl_date from CustomerMaster,AccountMaster where CustomerMaster.cid=AccountMaster.cid and  AccountMaster.ac_type='"+acctype+"' and AccountMaster.ac_no between "+fromacno+" and "+toacno+" and ac_status!='C' ");
			}
			else if(toacno==0 && fromdate==null) //to retreive data given only account no.
			{
				System.out.println(".....2");
				res=stmt.executeQuery("select distinct concat(IFNULL(fname,''),' ',IFNULL(mname,''),' ',IFNULL(lname,''))as name,ac_status,AccountMaster.ve_user,AccountMaster.ac_no,CustomerMaster.cid,ac_opendate,sub_category,nom_no,ch_bk_issue,int_pbl_date from CustomerMaster,AccountMaster where CustomerMaster.cid=AccountMaster.cid and   AccountMaster.ac_type='"+acctype+"' and AccountMaster.ac_no = "+fromacno+" and ac_status!='C' ");
				//				res=stmt.executeQuery("select distinct concat(IFNULL(fname,''),' ',IFNULL(mname,''),' ',IFNULL(lname,''))as name,cl_bal,trn_date,ac_status,AccountMaster.ve_user,AccountMaster.ac_no,CustomerMaster.cid,ac_opendate,sub_category,nom_no,ch_bk_issue,int_pbl_date from CustomerMaster,AccountMaster,AccountTransaction where CustomerMaster.cid=AccountMaster.cid and AccountMaster.ac_no=AccountTransaction.ac_no and AccountMaster.ac_type=AccountTransaction.ac_type and AccountMaster.ac_type='"+acctype+"' and AccountMaster.ac_no = "+fromacno+" and ac_status!='C' order by AccountTransaction.ac_no asc,AccountTransaction.trn_seq  asc");
				//				res=stmt.executeQuery("select distinct concat(fname,' ',mname,' ',lname)as name,cl_bal,trn_date,ac_status,AccountMaster.ve_user,AccountMaster.ac_no,CustomerMaster.cid,intro_name,ac_opendate,sub_category,nom_no,ch_bk_issue,int_pbl_date from CustomerMaster,AccountMaster,AccountTransaction where CustomerMaster.cid=AccountMaster.cid and AccountMaster.ac_no=AccountTransaction.ac_no and AccountMaster.ac_ty=AccountTransaction.ac_ty and AccountMaster.ac_ty='"+acctype+"' and AccountMaster.ac_no = "+fromacno+" and ac_status!='C' order by AccountTransaction.ac_no asc,AccountTransaction.trn_seq  asc");
			}
			else if(fromacno==0) //to retreive data given fromdate and todate
			{
				System.out.println(".....3");
				String fdate=Validations.convertYMD(fromdate);
				String tdate=Validations.convertYMD(todate);
				//res=stmt.executeQuery("select distinct concat(fname,' ',mname,' ',lname)as name,cl_bal,trn_date,ac_status,AccountMaster.ve_user,AccountMaster.ac_no,CustomerMaster.cid,intro_name,ac_opendate,sub_category,nom_no,ch_bk_issue,int_pbl_date from CustomerMaster,AccountMaster,AccountTransaction where CustomerMaster.cid=AccountMaster.cid and AccountMaster.ac_no=AccountTransaction.ac_no and AccountMaster.ac_ty=AccountTransaction.ac_ty and AccountMaster.ac_ty='"+acctype+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fdate+"' and '"+tdate+"' and ac_status!='C' order by AccountTransaction.ac_no asc,AccountTransaction.trn_seq  asc");
				//				res=stmt.executeQuery("select distinct concat(IFNULL(fname,''),' ',IFNULL(mname,''),' ',IFNULL(lname,'')) as name,cl_bal,trn_date,ac_status,AccountMaster.ve_user,AccountMaster.ac_no,CustomerMaster.cid,ac_opendate,sub_category,nom_no,ch_bk_issue,int_pbl_date from CustomerMaster,AccountMaster,AccountTransaction where CustomerMaster.cid=AccountMaster.cid and AccountMaster.ac_no=AccountTransaction.ac_no and AccountMaster.ac_type=AccountTransaction.ac_type and AccountMaster.ac_type='"+acctype+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fdate+"' and '"+tdate+"' and ac_status!='C' order by AccountTransaction.ac_no asc,AccountTransaction.trn_seq  asc");
				res=stmt.executeQuery("select distinct concat(IFNULL(fname,''),' ',IFNULL(mname,''),' ',IFNULL(lname,'')) as name,ac_status,AccountMaster.ve_user,AccountMaster.ac_no,CustomerMaster.cid,ac_opendate,sub_category,nom_no,ch_bk_issue,int_pbl_date from CustomerMaster,AccountMaster where CustomerMaster.cid=AccountMaster.cid and AccountMaster.ac_type='"+acctype+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fdate+"' and '"+tdate+"' and ac_status!='C' ");
			}
			else
			{
				System.out.println(".....4");
				String fdate=Validations.convertYMD(fromdate);
				String tdate=Validations.convertYMD(todate);				
				//				res=stmt.executeQuery("select distinct concat(fname,' ',mname,' ',lname)as name,cl_bal,trn_date,ac_status,AccountMaster.ve_user,AccountMaster.ac_no,CustomerMaster.cid,intro_name,ac_opendate,sub_category,nom_no,ch_bk_issue,int_pbl_date from CustomerMaster,AccountMaster,AccountTransaction where CustomerMaster.cid=AccountMaster.cid and AccountMaster.ac_no=AccountTransaction.ac_no and AccountMaster.ac_ty=AccountTransaction.ac_ty and AccountMaster.ac_ty='"+acctype+"' and AccountMaster.ac_no between "+fromacno+" and "+toacno+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fdate+"' and '"+tdate+"' and ac_status!='C' order by AccountTransaction.trn_seq  asc");
				//				res=stmt.executeQuery("select distinct concat(fname,' ',mname,' ',lname)as name,cl_bal,trn_date,ac_status,AccountMaster.ve_user,AccountMaster.ac_no,CustomerMaster.cid,ac_opendate,sub_category,nom_no,ch_bk_issue,int_pbl_date from CustomerMaster,AccountMaster,AccountTransaction where CustomerMaster.cid=AccountMaster.cid and AccountMaster.ac_no=AccountTransaction.ac_no and AccountMaster.ac_type=AccountTransaction.ac_type and AccountMaster.ac_type='"+acctype+"' and AccountMaster.ac_no between "+fromacno+" and "+toacno+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fdate+"' and '"+tdate+"' and ac_status!='C' order by AccountTransaction.trn_seq  asc");
				res=stmt.executeQuery("select distinct concat(fname,' ',mname,' ',lname)as name,ac_status,AccountMaster.ve_user,AccountMaster.ac_no,CustomerMaster.cid,ac_opendate,sub_category,nom_no,ch_bk_issue,int_pbl_date from CustomerMaster,AccountMaster where CustomerMaster.cid=AccountMaster.cid and AccountMaster.ac_type='"+acctype+"' and AccountMaster.ac_no between "+fromacno+" and "+toacno+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fdate+"' and '"+tdate+"' and ac_status!='C' ");
			}
			//res=stmt.executeQuery("select * from accountmaster where ac_ty="+acctype+" and ac_no between "+fromacno+" and "+toacno+" ");
			System.out.println("\n");
			System.out.println("res: "+res);
			res.last();
			System.out.println("no of records from getaccount "+res.getRow());
			if(res.getRow()!=0)
				//if(res.next())
			{
				ao=new AccountObject[res.getRow()];
				res.beforeFirst();
				int p=0;
				while(res.next())
				{
					ao[p]=new AccountObject();
					ao[p].setAcctype(String.valueOf(acctype));
					ao[p].setAccname(res.getString(1));
					//					ao[p].setAmount(res.getDouble(2)); //cl_bal
					//					ao[p].setLastTrnDate(res.getString(3)); //trn_date
					ao[p].setAccStatus(res.getString(2));  //ac_status
					ao[p].setAccno(res.getInt(4));
					// ao[p].setIntroName(res.getString("intro_name"));
					ao[p].setCid(res.getInt("CustomerMaster.cid"));
					System.out.println("cid vavlue  "+res.getInt("cid"));
					ao[p].setAcOpenDate(res.getString("ac_opendate"));
					ao[p].setIntPblDate(res.getString("int_pbl_date"));
					ao[p].setAcCategory(res.getInt("sub_category"));
					ao[p].setNomRegno(res.getInt("nom_no"));
					ao[p].setChBkIssue(res.getString("ch_bk_issue"));
					p++;
				}
			}
			else
				throw new RecordsNotFoundException();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ao;
	}*/
	
	
	/** Added 25/08/2006*/
	public AccountObject[] getAccount(String acctype,int fromacno,int toacno,String fromdate,String todate) throws RemoteException,RecordsNotFoundException
	{
		AccountObject ao[]=null;
		Connection conn=null;
		String table_name=null;
		try
		{
			conn=getConnection();
			System.out.println("Acctype "+acctype);
			System.out.println("Fromacno"+fromacno);
			System.out.println("toacno "+toacno);
			System.out.println("From date "+fromdate);
			System.out.println("To date "+todate);
			
			ResultSet res=null;
			Statement stmt=conn.createStatement();
			System.out.println(".....0");
			if(acctype.startsWith("1002")||acctype.startsWith("1007"))
				table_name="AccountMaster";
			else
				table_name="ODCCMaster";
			
			if(fromdate==null && toacno!=0) //to retreive data given fromacno and toacno
			{
				System.out.println(".....1");				
				res=stmt.executeQuery("select distinct concat(IFNULL(fname,''),' ',IFNULL(mname,''),' ',IFNULL(lname,'')) as name,"+table_name+".*,CustomerMaster.cid,sub_category from CustomerMaster,"+table_name+" where CustomerMaster.cid="+table_name+".cid and  "+table_name+".ac_type='"+acctype+"' and "+table_name+".ac_no between "+fromacno+" and "+toacno+" ");
			}
			else if(toacno==0 && fromdate==null) //to retreive data given only account no.
			{
				System.out.println(".....2");
				System.out.println("table....."+table_name);
				System.out.println("Acctype...."+acctype);
				System.out.println("Accno....."+fromacno);
				res=stmt.executeQuery("select distinct concat(IFNULL(fname,''),' ',IFNULL(mname,''),' ',IFNULL(lname,''))as name,"+table_name+".*,CustomerMaster.cid,sub_category from CustomerMaster,"+table_name+" where CustomerMaster.cid="+table_name+".cid and "+table_name+".ac_type='"+acctype+"' and "+table_name+".ac_no ="+fromacno+" ");				
			}
			else if(fromacno==0) //to retreive data given fromdate and todate
			{
				System.out.println(".....3");
				String fdate=Validations.convertYMD(fromdate);
				String tdate=Validations.convertYMD(todate);				
				res=stmt.executeQuery("select distinct concat(IFNULL(fname,''),' ',IFNULL(mname,''),' ',IFNULL(lname,'')) as name,"+table_name+".*,CustomerMaster.cid,sub_category from CustomerMaster,"+table_name+" where CustomerMaster.cid="+table_name+".cid and "+table_name+".ac_type='"+acctype+"' and concat(right(last_tr_date,4),'-',mid(last_tr_date,locate('/',last_tr_date)+1,(locate('/',last_tr_date,4)-locate('/',last_tr_date)-1)),'-',left(last_tr_date,locate('/',last_tr_date)-1)) between '"+fdate+"' and '"+tdate+"' ");
			}
			else
			{
				System.out.println(".....4");
				String fdate=Validations.convertYMD(fromdate);
				String tdate=Validations.convertYMD(todate);				
				
				res=stmt.executeQuery("select distinct concat(fname,' ',mname,' ',lname)as name,"+table_name+".*,CustomerMaster.cid,sub_category from CustomerMaster,"+table_name+" where CustomerMaster.cid="+table_name+".cid and "+table_name+".ac_type='"+acctype+"' and "+table_name+".ac_no between "+fromacno+" and "+toacno+" and concat(right(last_tr_date,4),'-',mid(last_tr_date,locate('/',last_tr_date)+1,(locate('/',last_tr_date,4)-locate('/',last_tr_date)-1)),'-',left(last_tr_date,locate('/',last_tr_date)-1)) between '"+fdate+"' and '"+tdate+"'");
			}		
						
			res.last();
			System.out.println("no of records from getaccount "+res.getRow());
			if(res.getRow()!=0)			
			{
				ao=new AccountObject[res.getRow()];
				res.beforeFirst();
				int p=0;
				while(res.next())
				{
					ao[p]=new AccountObject();
					ao[p].setAcctype(String.valueOf(acctype));
					ao[p].setAccname(res.getString("name"));					
					ao[p].setAccStatus(res.getString("ac_status"));  //ac_status
					ao[p].setAccno(res.getInt("ac_no"));					
					ao[p].setCid(res.getInt("CustomerMaster.cid"));
					System.out.println("cid vavlue  "+res.getInt("cid"));
					ao[p].setAcOpenDate(res.getString("ac_opendate"));
					if(acctype.startsWith("1002")||acctype.startsWith("1007"))
						ao[p].setIntPblDate(res.getString("int_pbl_date"));
					else
						ao[p].setIntPblDate(res.getString("int_uptodt"));
					ao[p].setAcCategory(res.getInt("sub_category"));
					ao[p].setNomRegno(res.getInt("nom_no"));
					ao[p].setChBkIssue(res.getString("ch_bk_issue"));
					p++;
				}
			}
			else
				throw new RecordsNotFoundException();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ao;
	}
	
	public DepositTransactionObject[] getDepositReport(String fdate,String tdate,String modcode,int acno,String string_query) throws RemoteException,RecordsNotFoundException
	{
		DepositTransactionObject dto[]=null;
		Connection conn=null;
		try
		{
			conn=getConnection();
			ResultSet res=null,res1=null;
			Statement stmt=conn.createStatement();
			Statement stmt1=conn.createStatement();
			String fromdate=Validations.convertYMD(fdate);
			String todate=Validations.convertYMD(tdate);
			System.out.println("Account Number in Bean --------"+acno);
			System.out.println("string_query=="+string_query);
			if(acno==0)
			{
				if(string_query==null)
					res=stmt.executeQuery("select * from DepositTransaction where ac_type='"+modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' order by ac_no");
				else 
					res=stmt.executeQuery("select * from DepositTransaction where ac_type='"+modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and ("+string_query+") order by ac_no");
			}				
			else
			{
				if(string_query==null)
					res=stmt.executeQuery("select * from DepositTransaction where ac_type='"+modcode+"' and ac_no="+acno+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' order by ac_no");
				else 
					res=stmt.executeQuery("select * from DepositTransaction where ac_type='"+modcode+"' and ac_no="+acno+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and ("+string_query+") order by ac_no");
			}
			res.last();
			if(res.getRow()!=0)
			{
				dto=new DepositTransactionObject[res.getRow()];
				res.beforeFirst();
				int q=0;
				while(res.next())
				{
					dto[q]=new DepositTransactionObject();
					dto[q].setAccNo(res.getInt("ac_no"));
					res1=stmt1.executeQuery("select concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name from DepositMaster dm,CustomerMaster cm where dm.ac_type='"+modcode+"' and dm.ac_no="+res.getInt("ac_no")+" and dm.cid=cm.cid");
					if(res1.next()){
						dto[q].setName(res1.getString(1));
					}
					dto[q].setTranType(res.getString("trn_type"));
					dto[q].setTranMode(res.getString("trn_mode"));
					dto[q].setDepositPaid(res.getDouble("dep_paid"));
					dto[q].setDepositAmt(res.getDouble("dep_amt"));
					dto[q].setInterestPaid(res.getDouble("int_paid"));
					dto[q].setInterestAmt(res.getDouble("int_amt"));
					dto[q].setTranNarration(res.getString("trn_narr"));
					dto[q].obj_userverifier.setUserId(res.getString("de_tml"));
					dto[q].obj_userverifier.setVerId(res.getString("ve_tml"));
					q++;
				}
			}
			else
				throw new RecordsNotFoundException();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dto;
		
	}
	//puspa on 14/11/2006
	public masterObject.loansOnDeposit.LoanTransactionObject[] getLoanTransactionReport(String fdate,String tdate,String modcode,int acno,String string_query) throws RemoteException,RecordsNotFoundException
	{
		masterObject.loansOnDeposit.LoanTransactionObject lnobj[]=null;
		UserVerifier uv=null;
		Connection con =null;
		try
		{
			con=getConnection();
			ResultSet res=null;
			Statement stmt=con.createStatement();
			String fromdate=Validations.convertYMD(fdate);
			String todate=Validations.convertYMD(tdate);
			if(acno==0)
			{
				
					if(string_query==null)
						res=stmt.executeQuery("select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,lt.ac_no,lt.trn_amt,lt.trn_mode,lt.pr_amt,lt.int_amt,lt.trn_narr,lt.other_amt,lt.extra_int,lt.pr_bal,lt.de_tml,lt.de_user,lt.ve_tml,lt.ve_user from LoanTransaction lt,LoanMaster lm,CustomerMaster cm where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and lt.ac_type=lm.ac_type and lt.ac_type='"+modcode+"' and lt.ac_no=lm.ac_no and cm.cid=lm.cid order by lm.ac_type,lm.ac_no");
					else if (string_query!=null)
						res=stmt.executeQuery("select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,lt.ac_no,lt.trn_amt,lt.trn_mode,lt.pr_amt,lt.int_amt,lt.trn_narr,lt.other_amt,lt.extra_int,lt.pr_bal,lt.de_tml,lt.de_user,lt.ve_tml,lt.ve_user from LoanTransaction lt,LoanMaster lm,CustomerMaster cm where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and lt.ac_type=lm.ac_type and lt.ac_type='"+modcode+"' and lt.ac_no=lm.ac_no and cm.cid=lm.cid and ("+string_query+") order by lm.ac_type,lm.ac_no");
						
						
					System.out.println("res: "+res);
			}				
				else
				{
					if(string_query==null)
						res=stmt.executeQuery("select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,lt.ac_no,lt.trn_amt,lt.trn_mode,lt.pr_amt,lt.int_amt,lt.trn_narr,lt.other_amt,lt.extra_int,lt.pr_bal,lt.de_tml,lt.de_user,lt.ve_tml,lt.ve_user from LoanTransaction lt,LoanMaster lm,CustomerMaster cm where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and lt.ac_type=lm.ac_type and lt.ac_type='"+modcode+"' and lt.ac_no=lm.ac_no and lt.ac_no="+acno+" and cm.cid=lm.cid order by lm.ac_type,lm.ac_no");
					else if(string_query!=null)
						res=stmt.executeQuery("select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,lt.ac_no,lt.trn_amt,lt.trn_mode,lt.pr_amt,lt.int_amt,lt.trn_narr,lt.other_amt,lt.extra_int,lt.pr_bal,lt.de_tml,lt.de_user,lt.ve_tml,lt.ve_user from LoanTransaction lt,LoanMaster lm,CustomerMaster cm where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and lt.ac_type=lm.ac_type and lt.ac_type='"+modcode+"' and lt.ac_no=lm.ac_no  and dt.ac_no="+acno+" and cm.cid=lm.cid and ("+string_query+") order by lm.ac_type,lm.ac_no");
						
					System.out.println("res: "+res);
				}
			res.last();
			System.out.println("rows=="+res.getRow());
			if(res.getRow()!=0)
			{
				lnobj=new masterObject.loansOnDeposit.LoanTransactionObject[res.getRow()];
				res.beforeFirst();
				int q=0;
				while(res.next())
				{
					lnobj[q]=new masterObject.loansOnDeposit.LoanTransactionObject();
					lnobj[q].setAccNo(res.getInt("ac_no"));
					lnobj[q].setName(res.getString(1));
					lnobj[q].setTransactionAmount(res.getDouble("trn_amt"));
					lnobj[q].setPrincipalPaid(res.getDouble("pr_amt"));
					lnobj[q].setInterestPaid(res.getDouble("int_amt"));
					lnobj[q].setOtherAmount(res.getDouble("other_amt"));
					lnobj[q].setExtraIntAmount(res.getDouble("extra_int"));
					lnobj[q].setPrincipalBalance(res.getDouble("pr_bal"));
					lnobj[q].setTranMode(res.getString("trn_mode"));
					lnobj[q].setTranNarration(res.getString("trn_narr"));
					//renuka added
					uv=new UserVerifier();
                    uv.setUserId(res.getString("de_user")+"-"+res.getString("de_tml"));
					uv.setVerId(res.getString("ve_user")+"-"+ res.getString("ve_tml"));
					lnobj[q].setUv(uv);			
					
					q++;
				}
			}
			else
				throw new RecordsNotFoundException();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
			
				
			
		
	 return lnobj;	
	}
	
	/**  Deepa on 25/07/2006  */
	public AccountTransObject[] getSBCAReport(String modcode,String fdate,String tdate,int acno,String string_query) throws RemoteException,RecordsNotFoundException
	{
	    AccountTransObject ato[]=null;
		System.out.println(".modcode from server="+modcode);
		System.out.println("fdate..."+fdate);
		Connection conn=null;
		try
		{
		    conn=getConnection();
			ResultSet res=null;
			Statement stmt=conn.createStatement();
			String fromdate=Validations.convertYMD(fdate);
			System.out.println("fromdate="+fromdate);
			String todate=Validations.convertYMD(tdate);
			System.out.println("Todate="+todate);
			System.out.println("acno="+acno);
			System.out.println("string_query=="+string_query);			
			
			if(acno==0)
			{
				if(modcode.startsWith("1002")||modcode.startsWith("1007")||modcode.startsWith("1017")||modcode.startsWith("1018"))
				{
					if(string_query==null)
						res=stmt.executeQuery("select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,at.cd_ind,at.ref_no,at.ac_no,at.trn_amt,at.cl_bal,trn_mode,trn_narr,at.de_user,at.ve_user from AccountTransaction at,AccountMaster am,CustomerMaster cm where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"' and '"+todate+"' and at.ac_type=am.ac_type and at.ac_type='"+modcode+"' and at.ac_no=am.ac_no and cm.cid=am.cid ");
					else if (string_query!=null)
						res=stmt.executeQuery("select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,at.cd_ind,at.ref_no,at.ac_no,at.trn_amt,at.cl_bal,trn_mode,trn_narr,at.de_user,at.ve_user from AccountTransaction at,AccountMaster am,CustomerMaster cm where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"' and '"+todate+"' and at.ac_type=am.ac_type and at.ac_type='"+modcode+"' and at.ac_no=am.ac_no and cm.cid=am.cid and ("+string_query+")");
					System.out.println("res: "+res);
				}
				else if(modcode.startsWith("1014")||modcode.startsWith("1015"))
				{
					if(string_query==null)
						res=stmt.executeQuery("select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,at.cd_ind,at.ref_no,at.ac_no,at.trn_amt,at.cl_bal,trn_mode,trn_narr,at.de_user,at.ve_user from ODCCTransaction at,ODCCMaster am,CustomerMaster cm where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"' and '"+todate+"' and at.ac_type=am.ac_type and at.ac_type='"+modcode+"' and at.ac_no=am.ac_no and cm.cid=am.cid ");
					else if (string_query!=null)
						res=stmt.executeQuery("select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,at.cd_ind,at.ref_no,at.ac_no,at.trn_amt,at.cl_bal,trn_mode,trn_narr,at.de_user,at.ve_user from ODCTransaction at,ODCCMaster am,CustomerMaster cm where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"' and '"+todate+"' and at.ac_type=am.ac_type and at.ac_type='"+modcode+"' and at.ac_no=am.ac_no and cm.cid=am.cid and ("+string_query+")");
				}
			}				
			else
			{
				if(modcode.startsWith("1002")||modcode.startsWith("1007")||modcode.startsWith("1017")||modcode.startsWith("1018"))
				{
					if(string_query==null)
						res=stmt.executeQuery("select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,at.ref_no,at.cd_ind,at.ac_no,at.trn_amt,at.cl_bal,trn_mode,trn_narr,at.de_user,at.ve_user from AccountTransaction at,AccountMaster am,CustomerMaster cm where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"' and '"+todate+"' and at.ac_type=am.ac_type and at.ac_type='"+modcode+"' and at.ac_no="+acno+" and at.ac_no=am.ac_no and cm.cid=am.cid ");
					else if (string_query!=null)
						res=stmt.executeQuery("select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,at.ref_no,at.cd_ind,at.ac_no,at.trn_amt,at.cl_bal,trn_mode,trn_narr,at.de_user,at.ve_user from AccountTransaction at,AccountMaster am,CustomerMaster cm where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"' and '"+todate+"' and at.ac_type=am.ac_type and at.ac_type='"+modcode+"' and at.ac_no=am.ac_no and cm.cid=am.cid and ("+string_query+")");
					System.out.println("res: "+res);
				}
				else if(modcode.startsWith("1014")||modcode.startsWith("1015"))
				{
					if(string_query==null)
						res=stmt.executeQuery("select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,at.ref_no,at.cd_ind,at.ac_no,at.trn_amt,at.cl_bal,trn_mode,trn_narr,at.de_user,at.ve_user from ODCCTransaction at,ODCCMaster am,CustomerMaster cm where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"' and '"+todate+"' and at.ac_type=am.ac_type and at.ac_type='"+modcode+"' and at.ac_no="+acno+" and at.ac_no=am.ac_no and cm.cid=am.cid ");
					else if (string_query!=null)
						res=stmt.executeQuery("select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,at.ref_no,at.cd_ind,at.ac_no,at.trn_amt,at.cl_bal,trn_mode,trn_narr,at.de_user,at.ve_user from ODCCTransaction at,ODCCMaster am,CustomerMaster cm where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"' and '"+todate+"' and at.ac_type=am.ac_type and at.ac_type='"+modcode+"' and at.ac_no=am.ac_no and cm.cid=am.cid and ("+string_query+")");
				}
			}
			res.last();
			System.out.println("no of records "+res.getRow());
			if(res.getRow()!=0)
			{
				ato=new AccountTransObject[res.getRow()];
				res.beforeFirst();
				int q=0;
				while(res.next())
				{
				    ato[q]=new AccountTransObject();
					ato[q].setName(res.getString(1));
					ato[q].setAccNo(res.getInt(4));
					ato[q].setTransMode(res.getString("trn_mode"));
					ato[q].setCdInd(res.getString("cd_ind"));
					ato[q].setRef_No(res.getInt("at.ref_no"));
					ato[q].setTransAmount(res.getDouble("trn_amt"));
					ato[q].setTransNarr(res.getString("trn_narr"));
					ato[q].uv.setUserId(res.getString("de_user"));
					ato[q].uv.setVerId(res.getString("ve_user"));
					
					q++;
				}
			}
			/*else
				throw new RecordsNotFoundException();*/
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ato;
		
	}
	
	public String[] getShareCategory(String shtype) throws RemoteException
	{
		String cate[]=null;
		Connection  conn=null;
		try
		{
			conn=getConnection();
			System.out.println("shtype in getsharecatre"+shtype);
			ResultSet rs=null;
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery("select catname from ShareType where ac_type='"+shtype+"' order by mem_cat");
			rs.last();
			System.out.println("no of rows in sharecategory === "+rs.getRow());
			int i=0;
			if(rs.getRow()!=0)
			{
				cate=new String[rs.getRow()];
				rs.beforeFirst();
				while(rs.next())
				{
					cate[i]=rs.getString(1);
					i++;
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cate;
	}
	
	public ShareObject[] getShareTranSummary(String modecode,String fdate,String tdate,int cate,int shno,String string_query) throws RemoteException,RecordsNotFoundException
	{
		ShareObject[] so=null;
		System.out.println("modecode = "+modecode);
		System.out.println("fdate = "+fdate);
		System.out.println("todate = "+tdate);
		System.out.println("cate = "+cate);
		System.out.println("Share No: "+shno);
		System.out.println("Strin _query"+string_query);
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet res=null;
			String fromdate=Validations.convertYMD(fdate);
			String todate=Validations.convertYMD(tdate);
			if(shno==0) //for all shares
			{
				//removed one condn from this res=stmt.executeQuery("Select st.ac_no,concat(fname,' ',mname,' ',lname)as name,cd_ind,trn_mode,trn_amt,trn_type,susp_ind,trn_narr,st.de_user,st.ve_user,sname from ShareMaster as sm,ShareTransaction as st,CustomerMaster as cm,Users as u where sm.ac_type=st.ac_type and st.ac_type='"+modecode+"' and sm.ac_no=st.ac_no and mem_cat="+cate+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and  cm.cid=sm.cid and u.uid=st.de_user order by st.de_date");
				if(string_query==null)
				{System.out.println("1....");
					//res=stmt.executeQuery("Select st.ac_no,concat(fname,' ',mname,' ',lname)as name,cd_ind,trn_mode,trn_amt,trn_type,susp_ind,trn_narr,st.de_user,st.ve_user,sname from ShareMaster as sm,ShareTransaction as st,CustomerMaster as cm,Users as u where sm.ac_type=st.ac_type and st.ac_type='"+modecode+"' and sm.ac_no=st.ac_no and mem_cat="+cate+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and  cm.cid=sm.cid order by st.de_date");
					res=stmt.executeQuery("Select sm.mem_cl_date,st.trn_seq,st.ac_no,concat_ws('',fname,mname,lname)as name,cd_ind,trn_mode,trn_amt,trn_type,susp_ind,trn_narr,st.de_user,st.ve_user from ShareMaster as sm,ShareTransaction as st,CustomerMaster as cm,ShareType sht where sm.ac_type=st.ac_type and st.ac_type='"+modecode+"' and sm.ac_no=st.ac_no and sm.mem_cat="+cate+" and sm.mem_cat=sht.mem_cat and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and  cm.cid=sm.cid order by st.trn_seq");
				    //res=stmt.executeQuery("select st.ac_no,concat(fname,' ',mname,' ',lname)as name,cd_ind,trn_mode,trn_amt,trn_type,susp_ind,trn_narr,st.de_user,st.ve_user,sname from ShareMaster as sm,ShareTransaction as st,CustomerMaster as cm,Users as u where sm.ac_type=st.ac_type and st.ac_type='"+modecode+"' and sm.ac_no=st.ac_no and mem_cat="+cate+" and trn_date between '"+fromdate+"'and '"+todate+"' and  cm.cid=sm.cid order by st.de_date");
				}
				else if(string_query!=null)
					//res=stmt.executeQuery("Select st.ac_no,concat(fname,' ',mname,' ',lname)as name,cd_ind,trn_mode,trn_amt,trn_type,susp_ind,trn_narr,st.de_user,st.ve_user,sname from ShareMaster as sm,ShareTransaction as st,CustomerMaster as cm,Users as u where sm.ac_type=st.ac_type and st.ac_type='"+modecode+"' and sm.ac_no=st.ac_no and mem_cat="+cate+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and  cm.cid=sm.cid and ("+string_query+") order by st.de_date");
					res=stmt.executeQuery("Select sm.mem_cl_date,st.trn_seq,st.ac_no,concat_ws('',fname,mname,lname)as name,cd_ind,trn_mode,trn_amt,trn_type,susp_ind,trn_narr,st.de_user,st.ve_user from ShareMaster as sm,ShareTransaction as st,CustomerMaster as cm,ShareType sht where sm.ac_type=st.ac_type and st.ac_type='"+modecode+"' and sm.ac_no=st.ac_no and sm.mem_cat="+cate+" and sm.mem_cat=sht.mem_cat and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and  cm.cid=sm.cid and ("+string_query+") order by st.trn_seq");
			}
			else //for a particular share specified
			{
				//removed one condn from this res=stmt.executeQuery("Select st.ac_no,concat(fname,' ',mname,' ',lname)as name,cd_ind,trn_mode,trn_amt,trn_type,susp_ind,trn_narr,st.de_user,st.ve_user,sname from ShareMaster as sm,ShareTransaction as st,CustomerMaster as cm,Users as u where sm.ac_type=st.ac_type and st.ac_type='"+modecode+"' and sm.ac_no=st.ac_no and st.ac_no="+shno+" and mem_cat="+cate+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and cm.cid=sm.cid and u.de_user=st.de_user order by st.de_date");
				if(string_query==null)
					//res=stmt.executeQuery("Select st.ac_no,concat(fname,' ',mname,' ',lname)as name,cd_ind,trn_mode,trn_amt,trn_type,susp_ind,trn_narr,st.de_user,st.ve_user,sname from ShareMaster as sm,ShareTransaction as st,CustomerMaster as cm,Users as u where sm.ac_type=st.ac_type and st.ac_type='"+modecode+"' and sm.ac_no=st.ac_no and st.ac_no="+shno+" and mem_cat="+cate+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and cm.cid=sm.cid  order by st.de_date");
					res=stmt.executeQuery("Select sm.mem_cl_date,st.trn_seq,st.ac_no,concat_ws('',fname,mname,lname)as name,cd_ind,trn_mode,trn_amt,trn_type,susp_ind,trn_narr,st.de_user,st.ve_user from ShareMaster as sm,ShareTransaction as st,CustomerMaster as cm,ShareType sht where sm.ac_type=st.ac_type and st.ac_type='"+modecode+"' and sm.ac_no=st.ac_no and st.ac_no="+shno+" and sm.mem_cat="+cate+" and sm.mem_cat=sht.mem_cat and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and cm.cid=sm.cid  order by st.trn_seq");
				else if(string_query!=null)
					//res=stmt.executeQuery("Select st.ac_no,concat(fname,' ',mname,' ',lname)as name,cd_ind,trn_mode,trn_amt,trn_type,susp_ind,trn_narr,st.de_user,st.ve_user,sname from ShareMaster as sm,ShareTransaction as st,CustomerMaster as cm,Users as u where sm.ac_type=st.ac_type and st.ac_type='"+modecode+"' and sm.ac_no=st.ac_no and st.ac_no="+shno+" and mem_cat="+cate+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and cm.cid=sm.cid and ("+string_query+") order by st.de_date");
					res=stmt.executeQuery("Select sm.mem_cl_date,st.trn_seq,st.ac_no,concat_ws('',fname,mname,lname)as name,cd_ind,trn_mode,trn_amt,trn_type,susp_ind,trn_narr,st.de_user,st.ve_user from ShareMaster as sm,ShareTransaction as st,CustomerMaster as cm,ShareType sht where sm.ac_type=st.ac_type and st.ac_type='"+modecode+"' and sm.ac_no=st.ac_no and st.ac_no="+shno+" and sm.mem_cat="+cate+" and sm.mem_cat=sht.mem_cat and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and cm.cid=sm.cid and ("+string_query+") order by st.trn_seq");
			}
			res.last();
			System.out.println("...No of Rows... "+res.getRow());
			if(res.getRow()!=0)
			{
				so=new ShareObject[res.getRow()];
				res.beforeFirst();
				int p=0;
				while(res.next())
				{
					so[p]=new ShareObject();
					so[p].setTrnSeq(res.getInt("st.trn_seq"));
					so[p].setMemCloseDate(res.getString("sm.mem_cl_date"));
					so[p].setCdInd(res.getString("cd_ind"));
					so[p].setShNo(res.getInt("st.ac_no"));
					so[p].setName(res.getString("name"));
					so[p].setSuspInd(res.getString("susp_ind"));
					so[p].setTrnMode(res.getString("trn_mode"));
					so[p].setTrnNarr(res.getString("trn_narr"));
					so[p].setTrnAmt(res.getDouble("trn_amt"));
					so[p].setTrnTy(res.getString("trn_type"));
					so[p].setTrnNarr(res.getString("trn_narr"));
					so[p].setUid(res.getString("st.de_user"));
					so[p].setVid(res.getString("st.ve_user"));
					//so[p].setSname(res.getString("sname"));
					p++;
				}
			}
			else
				throw new RecordsNotFoundException();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return so;
	}
	
	public NomineeObject getNominee(int reg_no) throws RemoteException
	{
		NomineeObject nomineeobject = new NomineeObject();
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs1 = stmt.executeQuery("select * from NomineeMaster where reg_no='"+ reg_no + "'");
			if (rs1.next())
			{
				nomineeobject.setRegNo(rs1.getInt("reg_no"));
				nomineeobject.setCustomerId(rs1.getInt("cid"));
				nomineeobject.setSex(rs1.getInt("sex"));
				nomineeobject.setNomineeName(rs1.getString("name"));
				nomineeobject.setNomineeDOB(rs1.getString("dob"));
				nomineeobject.setNomineeAddress(rs1.getString("address"));
				nomineeobject.setNomineeRelation(rs1.getString("relation"));
				nomineeobject.setGuardianName(rs1.getString("guard_name"));
				nomineeobject.setGuardianAddress(rs1.getString("guard_address"));
				nomineeobject.setGuardianType(rs1.getString("guard_type"));
				nomineeobject.setGuardRelation(rs1.getString("guard_rel"));
				nomineeobject.setCourtOrderNo(rs1.getInt("court_order_no"));
				nomineeobject.setCourtOrderDate(rs1.getString("court_order_date"));
			}
			
			return nomineeobject;
		} catch (Exception ce)
		{
			ce.printStackTrace();
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nomineeobject;
	}
	
	public PygmyObject[] getPygmy(String fromdate,String todate,String modcode,int accno,String string_query) throws RemoteException,RecordsNotFoundException
	{
		PygmyObject pygmyobject[]=null;
		UserVerifier obj_userverifier =  null;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			fromdate=Validations.convertYMD(fromdate);
			todate=Validations.convertYMD(todate);
			System.out.println("fromdate=="+fromdate);
			System.out.println("todate=="+todate);
			System.out.println("modecode"+modcode);
			System.out.println("accno"+accno);
			System.out.println("string_query"+string_query);
			ResultSet rs=null;
			if(accno==0)
			{
				System.out.println("....1");
				if(string_query==null)
					rs=stmt.executeQuery("select concat(cm.fname,' ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name,pt.ac_type,pt.ac_no,trn_amt,cd_ind,trn_mode,trn_narr,ref_no,pt.de_user,pt.ve_user from PygmyTransaction pt,PygmyMaster pm,CustomerMaster cm where pt.ac_no=pm.ac_no and pt.ac_type=pm.ac_type and pt.ac_type='"+modcode+"' and pm.cid=cm.cid and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' ");
				else if(string_query!=null)
					rs=stmt.executeQuery("select concat(cm.fname,' ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name,pt.ac_type,pt.ac_no,trn_amt,cd_ind,trn_mode,trn_narr,ref_no,pt.de_user,pt.ve_user from PygmyTransaction pt,PygmyMaster pm,CustomerMaster cm where pt.ac_no=pm.ac_no and pt.ac_type=pm.ac_type and pt.ac_type='"+modcode+"' and pm.cid=cm.cid and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and ("+string_query+") ");
			}
			else
			{
				System.out.println("....2");
				if(string_query==null)
					rs=stmt.executeQuery("select concat(cm.fname,' ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name,pt.ac_type,pt.ac_no,trn_amt,cd_ind,trn_mode,trn_narr,ref_no,pt.de_user,pt.ve_user from PygmyTransaction pt,PygmyMaster pm,CustomerMaster cm where pt.ac_no=pm.ac_no and pt.ac_no="+accno+" and pt.ac_type=pm.ac_type and pt.ac_type='"+modcode+"' and pm.cid=cm.cid and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' ");
				else if(string_query!=null)
					rs=stmt.executeQuery("select concat(cm.fname,' ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name,pt.ac_type,pt.ac_no,trn_amt,cd_ind,trn_mode,trn_narr,ref_no,pt.de_user,pt.ve_user from PygmyTransaction pt,PygmyMaster pm,CustomerMaster cm where pt.ac_no=pm.ac_no and pt.ac_no="+accno+" and pt.ac_type=pm.ac_type and pt.ac_type='"+modcode+"' and pm.cid=cm.cid and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and ("+string_query+") ");
				
			}
			
			rs.last();
			System.out.println("no. of records ="+rs.getRow());
			if(rs.getRow()!=0)
			{
				pygmyobject=new PygmyObject[rs.getRow()];
				rs.beforeFirst();
				int p=0;
				while(rs.next())
				{
					pygmyobject[p]=new PygmyObject();
					pygmyobject[p].setName(rs.getString("name"));
					pygmyobject[p].setPygmyType(rs.getString("pt.ac_type"));
					pygmyobject[p].setTransactionAmount(rs.getDouble("trn_amt"));
					pygmyobject[p].setAccountNo(rs.getInt("pt.ac_no"));
					pygmyobject[p].setCdIndicator(rs.getString("cd_ind"));
					pygmyobject[p].setTransactionMode(rs.getString("trn_mode"));
					pygmyobject[p].setNarration(rs.getString("trn_narr"));
					pygmyobject[p].setReferenceNo(rs.getInt("ref_no"));
					
					
					//renuka added
					obj_userverifier = new UserVerifier();
					
					obj_userverifier.setUserId(rs.getString("pt.de_user"));
					obj_userverifier.setVerId(rs.getString("pt.ve_user"));
					pygmyobject[p].setObj_userverifier(obj_userverifier);
					p++;
				}
			}
			else
				throw new RecordsNotFoundException();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pygmyobject;
	}
	
	public LoanTransactionObject[] getLoanTranSummary(String fromdate,String todate,String modcode,int accno,String string_query) throws RemoteException,RecordsNotFoundException
	{
		LoanTransactionObject array_loantransactionobject[]=null;
		UserVerifier uv=null;
		Connection conn=null;
		try
		{
			conn=getConnection();
			ResultSet res=null;
			Statement stmt=conn.createStatement();
			fromdate=Validations.convertYMD(fromdate);
			todate=Validations.convertYMD(todate);
			System.out.println("fromdate=="+fromdate);
			System.out.println("todate=="+todate);
			System.out.println("modecode "+modcode);
			System.out.println("accno "+accno);
			System.out.println("string_query"+string_query);
			if(accno==0)
			{
				if(string_query==null)
					res=stmt.executeQuery("select *, concat(cm.fname,' ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name from LoanTransaction lt,LoanMaster lm,CustomerMaster cm where lt.ac_type=lm.ac_type and lt.ac_type='"+modcode+"' and lm.ac_no=lt.ac_no and lm.cid=cm.cid and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and trn_type not like '%S%' order by lm.ac_no   ");
				else if(string_query!=null)
					res=stmt.executeQuery("select *, concat(cm.fname,' ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name from LoanTransaction lt,LoanMaster lm,CustomerMaster cm where lt.ac_type=lm.ac_type and lt.ac_type='"+modcode+"' and lm.ac_no=lt.ac_no and lm.cid=cm.cid and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and ("+string_query+") and trn_type not like '%S%' order by lm.ac_no ");
			}
			else
			{
				if(string_query==null)
					res=stmt.executeQuery("select *, concat(cm.fname,' ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name from LoanTransaction lt,LoanMaster lm,CustomerMaster cm where lt.ac_type=lm.ac_type and lt.ac_type='"+modcode+"' and lm.ac_no=lt.ac_no and lt.ac_no="+accno+" and lm.cid=cm.cid and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and trn_type not like '%S%' order by lm.ac_no    ");
				else if(string_query!=null)
					res=stmt.executeQuery("select *, concat(cm.fname,' ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name from LoanTransaction lt,LoanMaster lm,CustomerMaster cm where lt.ac_type=lm.ac_type and lt.ac_type='"+modcode+"' and lm.ac_no=lt.ac_no and lt.ac_no="+accno+" and lm.cid=cm.cid and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and ("+string_query+") and trn_type not like '%S%' order by lm.ac_no   ");
				
			}
			res.last();
			System.out.println("no of records "+res.getRow());
			if(res.getRow()!=0)
			{
				array_loantransactionobject=new LoanTransactionObject[res.getRow()];
				res.beforeFirst();
				int p=0;
				while(res.next())
				{
					array_loantransactionobject[p]=new LoanTransactionObject();
					array_loantransactionobject[p].setName(res.getString("name"));
					array_loantransactionobject[p].setAccNo(res.getInt("lt.ac_no"));
					array_loantransactionobject[p].setAccType(res.getString("lt.ac_type"));
					array_loantransactionobject[p].setPrincipalPaid(res.getDouble("pr_amt"));
					array_loantransactionobject[p].setInterestPaid(res.getDouble("int_amt")+res.getDouble("extra_int"));
					array_loantransactionobject[p].setPenaltyAmount(res.getDouble("penal_amt"));
					array_loantransactionobject[p].setTransactionAmount(res.getDouble("trn_amt"));
					array_loantransactionobject[p].setTranType(res.getString("trn_type"));
					array_loantransactionobject[p].setTranMode(res.getString("trn_mode"));
					array_loantransactionobject[p].setOtherAmount(res.getDouble("other_amt"));
					array_loantransactionobject[p].setTranNarration(res.getString("trn_narr"));
					array_loantransactionobject[p].setReferenceNo(res.getInt("ref_no"));
					uv=new UserVerifier();
					uv.setUserId(res.getString("de_user"));
					uv.setVerId(res.getString("ve_user"));
					//array_loantransactionobject[p].setUv(uv);
					p++;
				}
			}
			else 
				throw  new RecordsNotFoundException();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try {
				conn.close();  
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return array_loantransactionobject;
	}
	public void updateVoucherDataPayment(int vch_no) throws RemoteException,SQLException
	{
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			stmt.executeUpdate("delete from VoucherData where vch_no="+vch_no+" ");
			
		}catch(Exception exception){exception.printStackTrace();}
		finally
		{
			conn.close();
		}
	}
	
	public void updateVoucherData(String string_vchtype,int int_vchno,String[] array_string_gltype,int[] array_int_glcode,double[] array_double_amount)throws RemoteException
	{
		Connection conn=null;
		System.out.println("inside update "+array_string_gltype);
		System.out.println("inside vchtype"+string_vchtype);
		System.out.println("inside vchno"+int_vchno);
		System.out.println("glcode == "+array_int_glcode);
		System.out.println("array_double_amount == "+array_double_amount);
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			stmt.executeUpdate("delete from VoucherData where vch_no="+int_vchno+" and vch_type='"+string_vchtype+"' ");
			
		}catch(Exception exception){exception.printStackTrace();}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

	
	public int updateTransferVoucher(VoucherDataObject[] array_VoucherDataObject) throws RemoteException
	{
		Connection conn=null;
		System.out.println("************");
		int result=0;
		
		try
		{
			conn=getConnection();
			System.out.println("connection obtained");
			Statement stmt=conn.createStatement();
			PreparedStatement ps=null,ps1=null;
			ResultSet rs=null;
			int r=0;
			
				rs =stmt.executeQuery("select de_user,de_tml,de_date from VoucherData where vch_type='"+array_VoucherDataObject[0].getVoucherType()+ "' and vch_no="+array_VoucherDataObject[0].getVoucherNo()+";");
				
				String de_user="",de_tml="",de_date="";
				
				if(rs.next())
				{
					de_user=rs.getString(1);
					de_tml=rs.getString(2);
					de_date=rs.getString(3);
				}
			
			
				ps=conn.prepareStatement("delete from VoucherData where vch_no=?;");
				
				ps.setInt(1, array_VoucherDataObject[0].getVoucherNo());
				ps.executeUpdate();
					
			for(int n=0; n<array_VoucherDataObject.length; n++)
			{
				if(array_VoucherDataObject[n].getVoucherNo()>0)
				{

					System.out.println("inside insert query");
					
					
					ps1=conn.prepareStatement("insert into VoucherData values(?,?,?,?,?,?,?,?,?,?,null,?,?,?,'F',?,?,?,null,null,null)");
					
					
					ps1.setString(1,array_VoucherDataObject[n].getVoucherType());
					ps1.setInt(2,array_VoucherDataObject[n].getVoucherNo());
					ps1.setString(3,null);
					ps1.setString(4,array_VoucherDataObject[n].getTransactionDate());
					ps1.setString(5, array_VoucherDataObject[n].getNarration());
					ps1.setString(6, array_VoucherDataObject[n].getModuleAccountType());
					ps1.setInt(7,array_VoucherDataObject[n].getModuleAccountNo());
					ps1.setString(8, array_VoucherDataObject[n].getTransactionType());
					ps1.setString(9, array_VoucherDataObject[n].getGlType());
					ps1.setInt(10,array_VoucherDataObject[n].getGlCode());
					
					ps1.setString(11,null);
					ps1.setDouble(12,array_VoucherDataObject[n].getTransactionAmount());
					ps1.setString(13,array_VoucherDataObject[n].getCdIndicator());
					
					ps1.setString(14,de_user);
					ps1.setString(15,de_tml);
					ps1.setString(16,de_date);
					
					
					
					
					if(ps1.executeUpdate()==0){
					    System.out.println("Unable to Updated trf vch "+r);
						throw new RecordNotUpdatedException();
					}
					else {
						   System.out.println(" Updated trf vch "+r);	
						r++;
					}
				}
			}
				
				System.out.println("r value: "+r);
				if(r>0)
					return 1;
				
			}catch (Exception excep)
			{
				excep.printStackTrace();
				ctx.setRollbackOnly();
				System.out.println("Exception in Update Details Method");
				
			}
			finally
			{
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return 0;
	}
			
	public VoucherDataObject[] VoucherData(String vch_type,int vch_no)throws RemoteException,SQLException
	{
		 VoucherDataObject voucherdataobject[]=null;
		 Connection conn=null;
		 ResultSet rs=null;
		 try
		 {
		 	conn=getConnection();
		 	Statement stmt=conn.createStatement();
		 	rs=stmt.executeQuery("select * from VoucherData where  vch_type='"+vch_type+"' and vch_no ="+vch_no+" and ve_user is  null " );
		 	rs.last();
		 	if(rs.getRow()==0)
                return voucherdataobject;
		 	 voucherdataobject= new VoucherDataObject[rs.getRow()];
		 	rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				    voucherdataobject[i]= new VoucherDataObject();
		 		    voucherdataobject[i].setVoucherType(rs.getString("vch_type"));
		 		    voucherdataobject[i].setVoucherNo(rs.getInt("vch_no"));
		 		    voucherdataobject[i].setGlType(rs.getString("gl_sl_type"));
					voucherdataobject[i].setGlCode(rs.getInt("gl_sl_code"));
					voucherdataobject[i].setTransactionAmount(rs.getDouble("trn_amt"));
	                voucherdataobject[i].setTransactionType(rs.getString("trn_type"));
	                voucherdataobject[i].setModuleAccountType(rs.getString("mod_ac_type"));
	                voucherdataobject[i].setModuleAccountNo(Integer.parseInt(rs.getString("mod_ac_no")));
	                voucherdataobject[i].setCdIndicator(rs.getString("cd_ind"));
					voucherdataobject[i].obj_userverifier.setUserId(rs.getString("de_user"));
					voucherdataobject[i].obj_userverifier.setUserTml(rs.getString("de_tml"));
					voucherdataobject[i].obj_userverifier.setVerId(rs.getString("ve_user"));
					voucherdataobject[i].obj_userverifier.setVerTml(rs.getString("ve_tml"));
					voucherdataobject[i].obj_userverifier.setUserDate(rs.getString("de_date"));
					voucherdataobject[i].obj_userverifier.setVerDate(rs.getString("ve_date"));
					voucherdataobject[i].setNarration(rs.getString("narration"));
					i++;
		 	    }
			
		 }
		       
		       
		       			       
		      catch(Exception e)
			{
		 		e.printStackTrace();
			}
		 	finally
			{
		 		try
				 {
		 			 conn.close();
				}
		 		catch(SQLException p)
				{
		 			p.printStackTrace();
				}
		 	}
		      return voucherdataobject;
	 }
	
	
	
	public VoucherDataObject[] getVoucherData(String vch_type,int int_vch_no,String trn_date) throws RemoteException
	{
		System.out.println(" vch ty "+vch_type+"vch no"+int_vch_no+"trn date"+trn_date);
        
        VoucherDataObject voucherdataobject[]=null;
		Connection conn=null;
		try
		{
			conn=getConnection();
			ResultSet rs=null;
			Statement stmt=conn.createStatement();
			
            rs=stmt.executeQuery("select * from VoucherData where vch_no="+int_vch_no+" and vch_type='"+vch_type+"' and (concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))='"+Validations.convertYMD(trn_date)+"') and ve_tml is null ");
			
			rs.last();
             
            if(rs.getRow()==0)
                return voucherdataobject;
            voucherdataobject=new VoucherDataObject[rs.getRow()];
			
            rs.beforeFirst();
			int i=0;
			
			while(rs.next())
			{
				voucherdataobject[i]=new VoucherDataObject();
				
				voucherdataobject[i].setVoucherType(rs.getString("vch_type"));
				voucherdataobject[i].setVoucherNo(rs.getInt("vch_no"));
                voucherdataobject[i].setTransactionDate(rs.getString("trn_date"));
                voucherdataobject[i].setModuleAccountType(rs.getString("mod_ac_type"));
                voucherdataobject[i].setModuleAccountNo(rs.getInt("mod_ac_no"));
                
                if(rs.getString("mod_ac_type") != null) {
                	voucherdataobject[i].setName(commonlocal.getName(rs.getString("mod_ac_type"),rs.getInt("mod_ac_no")));
                }
                
                voucherdataobject[i].setGlType(rs.getString("gl_sl_type"));
				voucherdataobject[i].setGlCode(rs.getInt("gl_sl_code"));
				voucherdataobject[i].setTransactionAmount(rs.getDouble("trn_amt"));
                voucherdataobject[i].setTransactionType(rs.getString("trn_type"));
                voucherdataobject[i].setCdIndicator(rs.getString("cd_ind"));
				voucherdataobject[i].setChequeDate(rs.getString("chq_dt"));
				voucherdataobject[i].setChequeNo(rs.getInt("chq_no"));
				voucherdataobject[i].obj_userverifier.setUserId(rs.getString("de_user"));
				voucherdataobject[i].obj_userverifier.setUserTml(rs.getString("de_tml"));
				voucherdataobject[i].obj_userverifier.setVerId(rs.getString("ve_user"));
				voucherdataobject[i].obj_userverifier.setVerTml(rs.getString("ve_tml"));
				voucherdataobject[i].obj_userverifier.setUserDate(rs.getString("de_date"));
				voucherdataobject[i].obj_userverifier.setVerDate(rs.getString("ve_date"));
				voucherdataobject[i].setNarration(rs.getString("narration"));
				
				i++;
			}
			
		}catch(Exception exception){exception.printStackTrace();}
		
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        System.out.println("...before return..."+voucherdataobject);
		return voucherdataobject;
	}
	
	/**
	 * 
	 * @param fd
	 * @param type
	 * @throws RemoteException
	 */
	public void storeVoucherData(VoucherDataObject[] fd) throws RemoteException,SQLException
	{
		PreparedStatement ps = null;
		Connection  conn=null;
		try
		{
			conn=getConnection();
			System.out.println("inside storeVoucherData");
			
			//ship......20/06/2006
			//deleteVoucherDataPayment();
			//ps=conn.prepareStatement("insert into VoucherData(vch_type,trn_date,gl_sl_type,gl_sl_code,chq_no,chq_dt,trn_amt,cd_ind,cash_pdrd,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,vch_no,narration)values(?,date_format(sysdate(),'%d/%m/%Y'),?,?,?,?,?,?,'F',?,?,date_format(sysdate(),'%d/%m/%Y %r'),?,?,?,?,?)");
			ps = conn.prepareStatement("insert into VoucherData(vch_type,trn_date,gl_sl_type,gl_sl_code,trn_amt,cd_ind,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,vch_no,narration)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
   		    System.out.println("****************************");
   		    for(int i=0;i<fd.length;i++)
   		    {
   		     System.out.println("the length of table is  "+fd.length);	
			ps.setString(1,fd[i].getVoucherType());
			ps.setString(2,fd[i].getTransactionDate());
			ps.setString(3,fd[i].getGlType());
			ps.setInt(4,fd[i].getGlCode());
			/*ps.setInt(4,fd.getChequeNo());
			ps.setString(5,fd.getChequeDate());*/
			
			/*ps.setInt(4,0);
			ps.setString(5,null);*/
			
			ps.setDouble(5,fd[i].getTransactionAmount());
			ps.setString(6,fd[i].getCdIndicator());
			//ps.setString(6,null);
			ps.setString(7,fd[i].obj_userverifier.getUserId());
			ps.setString(8,fd[i].obj_userverifier.getUserTml());
			ps.setString(9,fd[i].obj_userverifier.getUserDate());
			ps.setString(10,null);
			ps.setString(11,null);
			ps.setString(12,null);
			ps.setInt(13,fd[i].getVoucherNo());
			ps.setString(14,fd[i].getNarration());
			ps.executeUpdate();
			System.out.println("________________________________");
			System.out.println("fd.getVoucherType()  "+fd[i].getVoucherType());
			System.out.println("fd.getVoucherNo()    "+fd[i].getVoucherNo());
			System.out.println("fd.getGlCode()   "+fd[i].getGlCode());
		}
		}
		catch(Exception exception){exception.printStackTrace();}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//......12/12/2006
	public int deleteVoucherDataTable(int int_vchno,String string_vch_type,String date) throws RemoteException,SQLException
    {
        System.out.println("************inside deleteVoucherDataTable**************");
      
        Connection conn=null;
        try
        {
            conn=getConnection();
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                      
            stmt.executeUpdate("delete from VoucherData where vch_no="+int_vchno+" and vch_type='"+string_vch_type+"' and trn_date='"+date+"' ");   
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            conn.close();
        }
        return 1;
    }
	
	
	////////////
	public int getVchNoPayment(String date) throws RemoteException,SQLException
	{
		int pvchno=0;
		Connection conn=null;
		try
		{
			conn=getConnection();			
			Statement stmt=conn.createStatement();
			System.out.println("inside getVchNo");
			ResultSet rs2=stmt.executeQuery("select lst_voucher_no from Modules where modulecode='1019000'");
			if(rs2.next())
			    pvchno=rs2.getInt(1)+1;
			stmt.executeUpdate("update Modules set lst_voucher_no=lst_voucher_no+1 where modulecode='1019000'");
	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		return pvchno;
	}
	

	public int VchNo(String date) throws RemoteException
	{
		int pvchno=0;
		Connection conn=null;
		String cash = null;
		
		try
		{
			conn=getConnection();			
			Statement stmt=conn.createStatement();
			System.out.println("inside getVchNo");
			
			ResultSet rs1=stmt.executeQuery("select cash_close from DailyStatus where trn_dt='"+Validations.convertYMD(date)+"'");
			
			if(rs1.next())
			    cash = rs1.getString("cash_close");
			
			System.out.println("cash close ="+cash);
			
			if(cash.equalsIgnoreCase("F"))
			{
				System.out.println("print"+cash);
				ResultSet rs2=stmt.executeQuery("select lst_voucher_no from Modules where modulecode='1019000'");
				if(rs2.next())
					pvchno =rs2.getInt(1);
				System.out.println("the voucher no is"+pvchno);
				stmt.executeUpdate("update Modules set lst_voucher_no=lst_voucher_no where modulecode='1019000'");
			}
			else
			{
				System.out.println("getvalue"+cash);
				return pvchno;
			}	
        }
		catch(Exception exception){exception.printStackTrace();}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pvchno;
	
	}
	public int getVchNo(String date) throws RemoteException
	{
		int pvchno=0;
		Connection conn=null;
		String cash = null;
		
		try
		{
			conn=getConnection();			
			Statement stmt=conn.createStatement();
			System.out.println("inside getVchNo");
			
			ResultSet rs1=stmt.executeQuery("select cash_close from DailyStatus where trn_dt='"+Validations.convertYMD(date)+"'");
			
			if(rs1.next())
			    cash = rs1.getString("cash_close");
			
			System.out.println("cash close ="+cash);
			
			if(cash.equalsIgnoreCase("F"))
			{
				System.out.println("print"+cash);
				ResultSet rs2=stmt.executeQuery("select lst_voucher_no from Modules where modulecode='1019000'");
				if(rs2.next())
					pvchno =rs2.getInt(1)+1;
				System.out.println("the voucher no is"+pvchno);
				stmt.executeUpdate("update Modules set lst_voucher_no=lst_voucher_no+1 where modulecode='1019000'");
			}
			else
			{
				System.out.println("getvalue"+cash);
				return pvchno;
			}	
        }
		catch(Exception exception){exception.printStackTrace();}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pvchno;
	}
	
	public int[] getGlCodes(String date) throws RemoteException,SQLException
	{
		int glm[]=null;
		Connection conn=null;
		System.out.println("inside glcodes");
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select gl_code from GLMaster where ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))  order by gl_code");
			rs.last();
            if(rs.getRow()!=0)
                glm=new int[rs.getRow()];
            System.out.println("length"+rs.getRow());
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				glm[i]=rs.getInt(1);
				i++;
			}
		}catch(Exception exception){exception.printStackTrace();}
		
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {e.printStackTrace();}
		}
		return glm;
	}
	public int[] glcodes(String date)throws RemoteException,SQLException
	{
		Connection con=null;
		ResultSet rs=null;
		int glm[]=null;
		
		try
		{
		con=getConnection();
	    Statement stmt=con.createStatement();
		rs=stmt.executeQuery("select gl_code from GLMaster  where gl_code not like '%000' order by gl_code ");
		
	    rs.last();
        if(rs.getRow()!=0)
            glm=new int[rs.getRow()];
        System.out.println("length"+rs.getRow());
		rs.beforeFirst();
		int i=0;
		while(rs.next())
		{
			
			glm[i]=rs.getInt(1);
			i++;
			System.out.println("++++++++++++++++++++++++++++ ");
		}
	}catch(Exception exception){exception.printStackTrace();}
	
	finally
	{
		try {
			con.close();
		} catch (SQLException e) {e.printStackTrace();}
	}
	return glm;		
	}
	
	
	
	
	
	
	public String[] getCDIndicator() throws RemoteException,SQLException
	{
		System.out.println("Inside CDIndicator.....");
		String cdind[]=null;
		Connection conn=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select distinct cd_ind from GLTransaction;");
			rs.last();
			cdind=new String[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				cdind[i]=rs.getString(1);
				i++;
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally
		{
			conn.close();
		}
		return cdind;
	}
	
	
	public String[] getGlTypes(String date) throws RemoteException,SQLException
	{
		System.out.println("Inside GLTypes.....");
		String gltype[]=null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select distinct gl_type from GLMaster where ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))");
			rs.last();
			gltype=new String[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				gltype[i]=rs.getString(1);
				i++;
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally
		{
			conn.close();
		}
		return gltype;
	}
	
	public String getGlName(int gl_code,String date) throws RemoteException,SQLException
	{
		System.out.println("inside getGlname glcode"+gl_code);
        String glname=null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select gl_name from GLMaster where gl_code="+gl_code+" and ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))");
			while(rs.next())
			{
				glname=rs.getString(1);
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally
		{
			conn.close();
		}
        System.out.println("glname "+glname);
		return glname;
	}
	
	public boolean verifyFadata(VoucherDataObject co) throws RemoteException,SQLException
	{
		Connection conn=null;
		String cash = null;
		
		try
		{
			conn=getConnection();
			System.out.println("inside verifyfadata");
			Statement stmt=conn.createStatement();
			ResultSet rs_csh=null,rs_acc=null;
			String date=co.getTransactionDate();
			
			ResultSet rs1=stmt.executeQuery("select cash_close from DailyStatus where trn_dt='"+Validations.convertYMD(date)+"'");
			
			if(rs1.next())
			    cash=rs1.getString("cash_close");
			
			System.out.println("values"+cash);
			if(cash.equalsIgnoreCase("F"))
			{	
				stmt.executeUpdate("update VoucherData  set ve_user='"+co.obj_userverifier.getVerId()+"',ve_tml='"+co.obj_userverifier.getVerTml()+"',ve_date='"+co.obj_userverifier.getVerDate()+"' where vch_no="+co.getVoucherNo()+" and trn_date='"+co.getTransactionDate()+"'");
				System.out.println("...inside new code ....");
            
				VoucherDataObject[] array_voucherDataObject=null;
				array_voucherDataObject=getVoucherData(co.getVoucherType(),co.getVoucherNo(),co.getTransactionDate());
				System.out.println("transaction date"+co.getTransactionDate());
            if(array_voucherDataObject!=null){
                System.out.println("...voucher object is null");
                GLTransObject trnobj=new GLTransObject();
            
                //changed by neetha
            for(int i=0;i<array_voucherDataObject.length;i++)
            {
           
              System.out.println("..inside for loop..");
              /*GLTransObject trnobj=new GLTransObject();*/
              if(array_voucherDataObject[i].getCdIndicator().equals("C"))
              	
              { 
                   System.out.println("....2714....");
                  trnobj.setGLCode("201001");
                  trnobj.setGLType(array_voucherDataObject[i].getGlType());
                  trnobj.setCdind("C");
                  trnobj.setTrnDate(array_voucherDataObject[i].getTransactionDate());
                  trnobj.setTrnMode("C");
                  trnobj.setAmount(array_voucherDataObject[i].getTransactionAmount());
                  trnobj.setAccType("");
                  trnobj.setAccNo("");
                  trnobj.setTrnSeq(0);
                  trnobj.setTrnType(array_voucherDataObject[i].getVoucherType());
                  trnobj.setRefNo(array_voucherDataObject[i].getVoucherNo());
                  trnobj.setRefAccType("1012001");
                  
                  trnobj.setVtml(array_voucherDataObject[i].obj_userverifier.getVerTml());
                  trnobj.setVid(array_voucherDataObject[i].obj_userverifier.getVerId());
               
                  int a=commonlocal.storeGLTransaction(trnobj);
                  
                  System.out.println("after calling storeGLTransaction 2731    "+a);
               
              }            
              
              else if(array_voucherDataObject[i].getCdIndicator().equalsIgnoreCase("D"))
              {
              	
              trnobj.setGLCode(String.valueOf(array_voucherDataObject[i].getGlCode()));
              trnobj.setGLType(array_voucherDataObject[i].getGlType());
              trnobj.setCdind("D");
              trnobj.setTrnDate(array_voucherDataObject[i].getTransactionDate());
              trnobj.setTrnMode("C");
              trnobj.setAmount(array_voucherDataObject[i].getTransactionAmount());
              trnobj.setAccType("");
              trnobj.setAccNo("");
              trnobj.setTrnSeq(0);
              trnobj.setTrnType(array_voucherDataObject[i].getVoucherType());
              trnobj.setRefNo(array_voucherDataObject[i].getVoucherNo());
              trnobj.setRefAccType("1012001");
              trnobj.setVtml(array_voucherDataObject[i].obj_userverifier.getVerTml());
              trnobj.setVid(array_voucherDataObject[i].obj_userverifier.getVerId());
           
              if(commonlocal.storeGLTransaction(trnobj)==1)
              {                 
                  //debit to PayOrder GL if it is PO
                  System.out.println("..inside if ..");
                  System.out.println("voucher type"+array_voucherDataObject[i].getVoucherType());
                  System.out.println("voucher no"+array_voucherDataObject[i].getVoucherNo());
                  System.out.println("voucher cd ind"+array_voucherDataObject[i].getCdIndicator());
              }
              }
            
            }//end of for loop
            } 
            
		    try
		    {
			    System.out.println("Inside insert DayCashhhhhhhhhhhhh");
	            
	            //changed by Neetha
			       rs_csh= stmt.executeQuery("select * from VoucherData where vch_no="+ co.getVoucherNo()+"");
			       rs_csh.last();
			       //PreparedStatement psmt= connection.prepareStatement("insert into DayCash values ('"+getSysDate()+"',?,?,'P',?,'C',?,?,?,?,'F',?,?,?,?,?,?)");
			       PreparedStatement psmt= conn.prepareStatement("insert into DayCash (vch_type,vch_no,trn_date,name,csh_amt,cd_ind,trn_type,ve_user,ve_date,ve_tml,de_user,de_tml,de_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			       			       
			       psmt.setString(1,rs_csh.getString("vch_type"));
			       psmt.setInt(2,rs_csh.getInt("vch_no"));
			       psmt.setString(3,rs_csh.getString("trn_date"));
			       psmt.setString(4,rs_csh.getString("narration"));
			       psmt.setInt(5,rs_csh.getInt("trn_amt"));
			       psmt.setString(6,rs_csh.getString("cd_ind"));
			       psmt.setString(7,"P");
			       psmt.setString(8,rs_csh.getString("ve_user"));
			       psmt.setString(9,rs_csh.getString("ve_date"));
			       psmt.setString(10,rs_csh.getString("ve_tml"));
			       psmt.setString(11,rs_csh.getString("de_user"));
			       psmt.setString(12,rs_csh.getString("de_tml"));
			       psmt.setString(13,rs_csh.getString("de_date"));
			       
			       psmt.executeUpdate();
			       System.out.println("Finished insert DayDCashhhhhhhhhhhhh");
			    }
			    catch (SQLException daycashException){daycashException.printStackTrace();}
	        
			    //////changed by nita on 11/05/2006 for making an entry in GLTransaction
			    
			    try{
			    	System.out.println("insert GLTransactionnnnnnn");
			    	rs_acc=stmt.executeQuery("select * from VoucherData where vch_no="+co.getVoucherNo()+" and vch_type='P' and trn_date='"+co.getTransactionDate()+"' ");
					
					rs_acc.last();						
					rs_acc.beforeFirst();			
					while(rs_acc.next())
					{
						PreparedStatement ps=conn.prepareStatement("insert into GLTransaction (trn_date,gl_type,gl_code,trn_mode,trn_amt,cd_ind,ref_ac_type,ref_ac_no,ref_tr_seq,ref_tr_type,ref_no,ve_tml,ve_user,ve_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						
						ps.setString(1,rs_acc.getString("trn_date"));
					    ps.setString(2,rs_acc.getString("gl_sl_type"));
					    ps.setInt(3,rs_acc.getInt("gl_sl_code"));
					    ps.setString(4,"C");
					    ps.setFloat(5,rs_acc.getFloat("trn_amt"));
					    ps.setString(6,rs_acc.getString("cd_ind"));
					    ps.setString(7,"1019001");
					    ps.setInt(8,rs_acc.getInt("vch_no"));
					    ps.setInt(9,0);
					    ps.setString(10,"P");
					    ps.setInt(11,0);
					    ps.setString(12,rs_acc.getString("ve_tml"));
					    ps.setString(13,rs_acc.getString("ve_user"));
					    ps.setString(14,rs_acc.getString("ve_date"));
					    
					    ps.executeUpdate();
					    System.out.println("inserting into GLTransaction.....");
					    
					}
			    	
			    	
			    }catch (Exception excep){excep.printStackTrace();}
				
			    
			    
                  
		    return true;
			}else
			{
				System.out.println("getvalue"+cash);
				return false;
			}
            
		}
		catch(Exception exception){exception.printStackTrace();}
		finally
		{
			conn.close();
		}
		return true;
	}
	
	public boolean verifyFadataVoucher(VoucherDataObject co1) throws RemoteException,SQLException
	{
		
	   VoucherDataObject voucher[];
		Connection conn=null;
		try
		{
			conn=getConnection();
			System.out.println("-------11111-------");
			PreparedStatement ps=null,psmtd=null;
			ResultSet rs=null,rs1=null;
			Statement stmt=conn.createStatement();
			Statement stmtd=conn.createStatement();
			
			
			  int gl=0,cash=0;
			  System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			  System.out.println("the voucher no is " + co1.getVoucherNo());
			  System.out.println("the transaction date is"+co1.getTransactionDate());
			  
			  rs=stmt.executeQuery("select * from VoucherData where vch_no="+co1.getVoucherNo()+" and trn_date='"+co1.getTransactionDate()+"' and ve_tml is  null and vch_type = 'P'");
			  if(rs.last()){
			  	System.out.println("the length of rs is "+ rs.getRow());
			  	rs.beforeFirst();	
			  }else 
			  	   return false;
			  
			   System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			  						
				
				while(rs.next())
				{
				   System.out.println("inserting into GLTransaction.....");
				   ps=conn.prepareStatement("insert into GLTransaction (trn_date,gl_type,gl_code,trn_mode,trn_amt,cd_ind,ref_ac_type,ref_ac_no,ref_tr_seq,ref_tr_type,ref_no,ve_tml,ve_user,ve_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				  {
				  		ps.setString(1,rs.getString("trn_date"));
				  		ps.setString(2,rs.getString("gl_sl_type"));
				  		ps.setInt(3,rs.getInt("gl_sl_code"));
				  		ps.setString(4,"C");
				  		ps.setFloat(5,rs.getFloat("trn_amt"));
				  		ps.setString(6,rs.getString("cd_ind"));
				  		ps.setString(7,"1019001");
				  		ps.setInt(8,rs.getInt("vch_no"));
				  		ps.setInt(9,0);
				  		ps.setString(10,"P");
				  		ps.setInt(11,0);
				  		ps.setString(12,co1.obj_userverifier.getVerTml());
			    
				  		ps.setString(13,co1.obj_userverifier.getVerId());
			    
				  		ps.setString(14,co1.obj_userverifier.getVerDate());
			    
				  		ps.executeUpdate();
			   
			
		            }
		       }
		    
				System.out.println("###################################");
				rs1=stmtd.executeQuery("select * from VoucherData where vch_no="+co1.getVoucherNo()+" and trn_date='"+co1.getTransactionDate()+"' and cd_ind='C' and ve_tml is  null and vch_type='P'");
		        if(rs1.last()){
			    System.out.println("the length of rs is "+ rs1.getRow());
		  	    rs1.beforeFirst();	
		        }
		        else 
		  	     return false;
		        
		        System.out.println("****************************");
		        while(rs1.next())
		       {
		  	
		  	   System.out.println("inserting into DayCashTable.....");
			    psmtd= conn.prepareStatement("insert into DayCash (vch_type,vch_no,trn_date,name,csh_amt,cd_ind,trn_type,ve_user,ve_date,ve_tml,de_user,de_tml,de_date,attached) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			   System.out.println("??????????????????????????");
			   psmtd.setString(1,rs1.getString("vch_type"));
		       psmtd.setInt(2,rs1.getInt("vch_no"));
		       psmtd.setString(3,rs1.getString("trn_date"));
		       psmtd.setString(4,rs1.getString("narration"));
		       psmtd.setDouble(5,rs1.getDouble("trn_amt"));
		       psmtd.setString(6,rs1.getString("cd_ind"));
		       psmtd.setString(7,"P");
		       psmtd.setString(8,co1.obj_userverifier.getVerId());
		       psmtd.setString(9,co1.obj_userverifier.getVerDate());
		       psmtd.setString(10,co1.obj_userverifier.getVerTml());
		       psmtd.setString(11,rs1.getString("de_user"));
		       psmtd.setString(12,rs1.getString("de_tml"));
		       psmtd.setString(13,rs1.getString("de_date"));
               psmtd.setString(14,"F");
		       System.out.println("...............................");
		       psmtd.executeUpdate();
		       psmtd.clearBatch();
		       System.out.println("Finished insert DayDCashhhhhhhhhhhhh");
		   }
		        
		      System.out.println("-------------------R u Verifying Here--------------");  
		    int rsa = psmtd.executeUpdate("update VoucherData set  ve_user ='"+  co1.obj_userverifier.getVerId()+"' , ve_tml='"+  co1.obj_userverifier.getVerTml()+ "',  ve_date='"+ co1.obj_userverifier.getVerDate()+"' where  vch_no ='"+ co1.getVoucherNo()+"' and ve_user is  null ");    
		        
		        
		        
		        
		}
		 
			catch(Exception k)
			{
				k.printStackTrace();
				}
			 finally
			 {
				try{
					conn.close();
					}
				catch(Exception m){
					m.printStackTrace();
					}
			   }
			    return true;	
				
			         
			
			}
			
	

	
	
	
	public boolean verifyTransferFadata(VoucherDataObject co1) throws RemoteException,SQLException
	{
		Connection conn=null;
		try
		{
			conn=getConnection();
			System.out.println("-------11111-------");
			PreparedStatement ps,ps1;
			Statement stmt=conn.createStatement();
			int r=0;
			
			ps =conn.prepareStatement("update VoucherData set ve_tml=?, ve_user=?, ve_date=? where vch_type='T' and vch_no="+co1.getVoucherNo()+"");
			
			ps.setString(1, co1.obj_userverifier.getVerTml());
			ps.setString(2, co1.obj_userverifier.getVerId());
			ps.setString(3, co1.obj_userverifier.getVerDate());
			
			if(ps.executeUpdate()==0){
			    System.out.println("Unable to Updated trf vch "+r);
				throw new RecordNotUpdatedException();
			}
			else {
				   System.out.println(" Updated trf vch "+r);	
				r++;
			}
				
			ResultSet rs_acc=null;
            
			AccountTransObject accountobject_insert_acc =new AccountTransObject();
            
			rs_acc=stmt.executeQuery("select * from VoucherData where vch_type='T' and vch_no="+co1.getVoucherNo()+" and trn_date='"+co1.getTransactionDate()+"' and ve_tml is not null and ve_user is not null and ve_date is not null");
			
			rs_acc.last();						
			rs_acc.beforeFirst();		
            
			while(rs_acc.next())
			{
				System.out.println("Inside while loop");
				System.out.println("Ac_type----"+co1.getModuleAccountType());
				if(rs_acc.getString("mod_ac_type")!=null)
				{
					System.out.println("account type of entry");
				if(rs_acc.getString("mod_ac_type").startsWith("1002") || rs_acc.getString("mod_ac_type").startsWith("1007") || rs_acc.getString("mod_ac_type").startsWith("1014") || rs_acc.getString("mod_ac_type").startsWith("1015") || rs_acc.getString("mod_ac_type").startsWith("1018"))
				{
					System.out.println("Inside SB CA OD CC");
                    
					try
					{
        				accountobject_insert_acc.setAccType(rs_acc.getString("mod_ac_type"));
        				accountobject_insert_acc.setAccNo(rs_acc.getInt("mod_ac_no"));
        				accountobject_insert_acc.setTransDate(rs_acc.getString("trn_date"));
        				accountobject_insert_acc.setTransType(rs_acc.getString("trn_type"));
        				accountobject_insert_acc.setTransSeqNo(0);
        				accountobject_insert_acc.setTransAmount(rs_acc.getDouble("trn_amt"));
        				accountobject_insert_acc.setTransType(rs_acc.getString("trn_type"));
        				accountobject_insert_acc.setTransMode("T");
        				accountobject_insert_acc.setTransSource(rs_acc.getString("de_tml"));
        				accountobject_insert_acc.setCdInd(rs_acc.getString("cd_ind"));
        				accountobject_insert_acc.setChqDDNo(0);
        				accountobject_insert_acc.setChqDDDate(null);
        				accountobject_insert_acc.setTransNarr("Voucher  "+rs_acc.getInt("vch_no"));
        				accountobject_insert_acc.setRef_No(rs_acc.getInt("vch_no"));
        				accountobject_insert_acc.setPayeeName(null);
        				accountobject_insert_acc.setLedgerPage(0);
        				accountobject_insert_acc.uv.setUserTml(rs_acc.getString("de_tml"));
        				accountobject_insert_acc.uv.setUserId(rs_acc.getString("de_user"));
        				accountobject_insert_acc.uv.setUserDate(rs_acc.getString("de_date"));   
        				accountobject_insert_acc.uv.setVerTml(rs_acc.getString("ve_tml"));   
        				accountobject_insert_acc.uv.setVerId(rs_acc.getString("ve_user"));
        				accountobject_insert_acc.uv.setVerDate(rs_acc.getString("ve_date"));
        				
        				commonlocal=commonlocalhome.create();
                        
        				if (commonlocal.storeAccountTransaction(accountobject_insert_acc)== 0)
        					System.out.println("SB CA OD CC account transaction");
					}
                    catch(SQLException se) 
                    {
                        se.printStackTrace();
                    }
				}
			
			else if(rs_acc.getString("mod_ac_type").startsWith("1001"))
			{
				try
				{
				System.out.println("inside share transaction function ");
				ShareMasterObject sharemasterobject=null;
				sharemasterobject=new ShareMasterObject();
				
				String ac_type=rs_acc.getString("mod_ac_type");
				int ac_no=rs_acc.getInt("mod_ac_no");
				double amnt=rs_acc.getDouble("trn_amt");
				String indicator=rs_acc.getString("cd_ind");
				int trn_no=rs_acc.getInt("vch_no");
				String date=rs_acc.getString("trn_date");
				String deuser=rs_acc.getString("de_user");
				String detml=rs_acc.getString("de_tml");
				String dedate=rs_acc.getString("de_date");
				
				float store_share=sharelocal.InsertintoShareTran(ac_type,ac_no,Float.parseFloat(String.valueOf(amnt)),indicator,trn_no,date,deuser,detml,dedate);	
				ps =conn.prepareStatement("update ShareTransaction set ve_tml=?, ve_user=?, ve_date=?, trn_source=?, ref_no=?, trn_narr=? where ac_type='"+ac_type+"' and trn_no='"+co1.getVoucherNo()+"' and trn_date='"+co1.getTransactionDate()+"' ");
				
				ps.setString(1, co1.obj_userverifier.getVerTml());
				ps.setString(2, co1.obj_userverifier.getVerId());
				ps.setString(3, co1.obj_userverifier.getVerDate());
				ps.setString(4, co1.obj_userverifier.getVerTml());
				ps.setInt(5, co1.getVoucherNo());
				ps.setString(6, String.valueOf("Trf.Voucher "+co1.getVoucherNo()));
				if(ps.executeUpdate()==0)
				{
				    System.out.println("Unable to Updated trf vch");
					throw new RecordNotUpdatedException();
				}
				else 
				{
					   System.out.println(" Updated trf vch ");	
				}
				
				ps=conn.prepareStatement("insert into GLTransaction (trn_date,gl_type,gl_code,trn_mode,trn_amt,cd_ind,ref_ac_type,ref_ac_no,ref_tr_seq,ref_tr_type,ref_no,ve_tml,ve_user,ve_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
				ps.setString(1,rs_acc.getString("trn_date"));
			    ps.setString(2,"1012001");
			    ps.setInt(3,rs_acc.getInt("gl_sl_code"));
			    ps.setString(4,"T");
			    ps.setDouble(5,rs_acc.getDouble("trn_amt"));
			    ps.setString(6,rs_acc.getString("cd_ind"));
			    ps.setString(7,rs_acc.getString("mod_ac_type"));
			    ps.setInt(8,rs_acc.getInt("mod_ac_no"));
			    ps.setInt(9,0);
			    ps.setString(10,"T");
			    ps.setInt(11,rs_acc.getInt("vch_no"));
			    ps.setString(12,rs_acc.getString("ve_tml"));
			    ps.setString(13,rs_acc.getString("ve_user"));
			    ps.setString(14,rs_acc.getString("ve_date"));
			    
			    ps.executeUpdate();
			    System.out.println("inserting into GLTransaction.....");
			    
			}catch (SQLException se) {se.printStackTrace();}
			}
			
			else if(rs_acc.getString("mod_ac_type").startsWith("1003") || rs_acc.getString("mod_ac_type").startsWith("1005") || rs_acc.getString("mod_ac_type").startsWith("1004") )
			{
				try
				{
					System.out.println("inside termdeposit transaction function ");
					//DepositMasterObject depositmasterobject=null;
					//depositmasterobject=new DepositMasterObject(); 
					
					String ac_type=rs_acc.getString("mod_ac_type");
					int ac_no=rs_acc.getInt("mod_ac_no");
					double amnt=rs_acc.getDouble("trn_amt");
					String date=rs_acc.getString("trn_date");
					String deuser=rs_acc.getString("de_user");
					String detml=rs_acc.getString("de_tml");
					String dedate=rs_acc.getString("de_date");
					
					String veuser=rs_acc.getString("ve_user");
					String vetml=rs_acc.getString("ve_tml");
					String vedate=rs_acc.getString("ve_date");
					
					String cd_ind=rs_acc.getString("cd_ind"); 
					int ref_no=rs_acc.getInt("vch_no");
					
					float store_termdeposit=termdepositlocalremote.InsertTermDepTran(ac_type,ac_no,Float.parseFloat(String.valueOf(amnt)),date,cd_ind,ref_no,deuser,detml,dedate,veuser,vetml,vedate);
					ps=conn.prepareStatement("insert into GLTransaction (trn_date,gl_type,gl_code,trn_mode,trn_amt,cd_ind,ref_ac_type,ref_ac_no,ref_tr_seq,ref_tr_type,ref_no,ve_tml,ve_user,ve_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					
					ps.setString(1,rs_acc.getString("trn_date"));
					ps.setString(2,"1012001");
				    ps.setInt(3,rs_acc.getInt("gl_sl_code"));
				    ps.setString(4,"T");
				    ps.setDouble(5,rs_acc.getDouble("trn_amt"));
				    ps.setString(6,rs_acc.getString("cd_ind"));
				    ps.setString(7,rs_acc.getString("mod_ac_type"));
				    ps.setInt(8,rs_acc.getInt("mod_ac_no"));
				    ps.setInt(9,0);
				    ps.setString(10,"T");
				    ps.setInt(11,rs_acc.getInt("vch_no"));
				    ps.setString(12,rs_acc.getString("ve_tml"));
				    ps.setString(13,rs_acc.getString("ve_user"));
				    ps.setString(14,rs_acc.getString("ve_date"));
				    
				    ps.executeUpdate();
				    System.out.println("inserting into GLTransaction.....");
				   
				}catch (SQLException se) {se.printStackTrace();}
				}
			
			else if(rs_acc.getString("mod_ac_type").startsWith("1008") || rs_acc.getString("mod_ac_type").startsWith("1010"))
			{
				try
				{
				   
					LoanTransactionObject loan_tran_object = new LoanTransactionObject();
						
					loan_tran_object.setAccType(rs_acc.getString("mod_ac_type"));
					loan_tran_object.setAccNo(rs_acc.getInt("mod_ac_no"));
					loan_tran_object.setTransactionDate(rs_acc.getString("trn_date"));
					loan_tran_object.setTranType("R");
					loan_tran_object.setTransactionAmount(rs_acc.getDouble("trn_amt"));
					loan_tran_object.setTranMode("T");
					loan_tran_object.setTranSou(rs_acc.getString("de_tml"));
					loan_tran_object.setReferenceNo(rs_acc.getInt("vch_no"));
					loan_tran_object.setTranNarration("Trn Vouch "+rs_acc.getInt("vch_no"));
					loan_tran_object.setCdind("C");
					loan_tran_object.uv.setUserTml(rs_acc.getString("de_tml"));
					loan_tran_object.uv.setUserId(rs_acc.getString("de_user"));
					loan_tran_object.uv.setUserDate(rs_acc.getString("de_date"));   
					loan_tran_object.uv.setVerTml(rs_acc.getString("ve_tml"));   
					loan_tran_object.uv.setVerId(rs_acc.getString("ve_user"));
					loan_tran_object.uv.setVerDate(rs_acc.getString("ve_date"));
					
					boolean loan_entry=loanlocal.insertTransferVoucherEntry(loan_tran_object);
					
					if(loan_entry)
					{
						JOptionPane.showMessageDialog(null,"Sucessfully inserted");
						System.out.println("success");
					}
					
					else
					{
						JOptionPane.showMessageDialog(null,"Sucessfully not inserted");
						System.out.println("not successfully inserted");
						ctx.setRollbackOnly();
					}
						
						ps=conn.prepareStatement("insert into GLTransaction (trn_date,gl_type,gl_code,trn_mode,trn_amt,cd_ind,ref_ac_type,ref_ac_no,ref_tr_seq,ref_tr_type,ref_no,ve_tml,ve_user,ve_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						
						ps.setString(1,rs_acc.getString("trn_date"));
						ps.setString(2,"1012001");
					    ps.setInt(3,rs_acc.getInt("gl_sl_code"));
					    ps.setString(4,"T");
					    ps.setDouble(5,rs_acc.getDouble("trn_amt"));
					    ps.setString(6,rs_acc.getString("cd_ind"));
					    ps.setString(7,rs_acc.getString("mod_ac_type"));
					    ps.setInt(8,rs_acc.getInt("mod_ac_no"));
					    ps.setInt(9,0);
					    ps.setString(10,"T");
					    ps.setInt(11,rs_acc.getInt("vch_no"));
					    ps.setString(12,rs_acc.getString("ve_tml"));
					    ps.setString(13,rs_acc.getString("ve_user"));
					    ps.setString(14,rs_acc.getString("ve_date"));
					    
					    ps.executeUpdate();
					    System.out.println("inserting into GLTransaction.....");
					   		
				
				}
				catch (SQLException se) {se.printStackTrace();}
					
			}
			
			else if(rs_acc.getString("mod_ac_type").startsWith("1006") || rs_acc.getString("mod_ac_type").startsWith("1013") )
			{
				try
				{
				System.out.println("inside pygmy transaction function ");
			
				PygmyMasterObject pygmymasterobject=null;
				pygmymasterobject=new PygmyMasterObject(); 
				
				String ac_type=rs_acc.getString("mod_ac_type");
				int ac_no=rs_acc.getInt("mod_ac_no");
				String date=rs_acc.getString("trn_date");
				
				double amnt=rs_acc.getDouble("trn_amt");
				String trn_type=rs_acc.getString("trn_type");
				String indicator=rs_acc.getString("cd_ind");
				
				String detml=rs_acc.getString("de_tml");
				String deuser=rs_acc.getString("de_user");
				String dedate=rs_acc.getString("de_date");
				
				String veuser=rs_acc.getString("ve_user");
				String vetml=rs_acc.getString("ve_tml");
				String vedate=rs_acc.getString("ve_date");
				
				int store_pygmy=pygmylocalremote.insertintoPygmyTran(ac_type,ac_no,date,amnt,trn_type,indicator,detml,deuser,dedate,veuser,vetml,vedate);	
				ps=conn.prepareStatement("insert into GLTransaction (trn_date,gl_type,gl_code,trn_mode,trn_amt,cd_ind,ref_ac_type,ref_ac_no,ref_tr_seq,ref_tr_type,ref_no,ve_tml,ve_user,ve_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
				ps.setString(1,rs_acc.getString("trn_date"));
				ps.setString(2,"1012001");
			    ps.setInt(3,rs_acc.getInt("gl_sl_code"));
			    ps.setString(4,"T");
			    ps.setDouble(5,rs_acc.getDouble("trn_amt"));
			    ps.setString(6,rs_acc.getString("cd_ind"));
			    ps.setString(7,rs_acc.getString("mod_ac_type"));
			    ps.setInt(8,rs_acc.getInt("mod_ac_no"));
			    ps.setInt(9,0);
			    ps.setString(10,"T");
			    ps.setInt(11,rs_acc.getInt("vch_no"));
			    ps.setString(12,rs_acc.getString("ve_tml"));
			    ps.setString(13,rs_acc.getString("ve_user"));
			    ps.setString(14,rs_acc.getString("ve_date"));
			    
			    ps.executeUpdate();
			    System.out.println("inserting into GLTransaction.....");
			    
			}catch (SQLException se) {se.printStackTrace();}
			}
			
			}
			
			else 
			{
			
				System.out.println("only gl entry");
				ps=conn.prepareStatement("insert into GLTransaction (trn_date,gl_type,gl_code,trn_mode,trn_amt,cd_ind,ref_ac_type,ref_ac_no,ref_tr_seq,ref_tr_type,ref_no,ve_tml,ve_user,ve_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
				ps.setString(1,rs_acc.getString("trn_date"));
				ps.setString(2,"1012001");
			    ps.setInt(3,rs_acc.getInt("gl_sl_code"));
			    ps.setString(4,"T");
			    ps.setDouble(5,rs_acc.getDouble("trn_amt"));
			    ps.setString(6,rs_acc.getString("cd_ind"));
			    ps.setString(7,"1019001");
			    ps.setInt(8,rs_acc.getInt("vch_no"));
			    ps.setInt(9,0);
			    ps.setString(10,"T");
			    ps.setInt(11,rs_acc.getInt("vch_no"));
			    ps.setString(12,rs_acc.getString("ve_tml"));
			    ps.setString(13,rs_acc.getString("ve_user"));
			    ps.setString(14,rs_acc.getString("ve_date"));
			    
			    ps.executeUpdate();
			    System.out.println("inserting into GLTransaction.....");
				
			}
			
			}
			
			return true;
		
		}catch (Exception excep)
		{
			excep.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		return false;
	}
	
	
	public int[] getChequeNos(int acc_no,String acc_type) throws RemoteException,SQLException
	{
		int chqno[]=null;
		Connection conn=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			ResultSet rs=stmt.executeQuery("select 'chq_no' from ChequeNo where ac_no="+acc_no+" and ac_type="+acc_type+" ");
			rs.last();
			chqno=new int[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				chqno[i]=rs.getInt(1);
				i++;
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally
		{
			conn.close();
		}
		return chqno;
	}
	
	public String[] getChequeType() throws RemoteException,SQLException
	{
		String chqtype[]=null;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select distinct ac_ty from ChequeNo");
			rs.last();
			chqtype=new String[rs.getRow()];
			
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				chqtype[i]=rs.getString(1);
				i++;
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally
		{
			conn.close();
		}
		return chqtype;
	}
	
	/** Changed on 10/08/06 */
	public ChequeNoObject[] getUnpresentedCheque(int int_account_no,String string_account_type,String string_query) throws RemoteException,SQLException
	{
		ChequeNoObject chequenoobject[]=null;
		Connection conn=null;
		
		System.out.println("acc_type:"+string_account_type);
		System.out.println("string_query:"+string_query);
		System.out.println("acc no"+int_account_no);
				
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
						
            if(string_query==null || string_query.equals(" "))
                rs=stmt.executeQuery("select distinct am.ac_type,am.ac_no,cn.chq_no,cn.next_chqdt,cn.post_dated,cn.stop_pymnt,cn.chq_del,concat_ws('',cm.fname,cm.mname,cm.lname) as name from ChequeNo cn,CustomerMaster cm,AccountMaster am,ChequeBook cb where cm.cid =am.cid and am.ac_type='"+string_account_type+"' and am.ac_no="+int_account_no+" and am.ac_no=cn.ac_no and am.ac_type=cn.ac_type and am.ac_no=cn.ac_no and am.ac_type=cb.ac_type and am.ac_no=cb.ac_no and cn.chq_del!='T'");
             else
                //rs=stmt.executeQuery("select distinct am.ac_type,am.ac_no,cn.chq_no,cn.next_chqdt,cn.post_dated,cn.stop_pymnt,cn.chq_del,concat_ws('',cm.fname,cm.mname,cm.lname) as name from ChequeNo cn,CustomerMaster cm,AccountMaster am,ChequeBook cb where cm.cid =am.cid and am.ac_type='"+string_account_type+"' and am.ac_no="+int_account_no+" and am.ac_no=cn.ac_no and am.ac_type=cn.ac_type and am.ac_no=cn.ac_no and am.ac_type=cb.ac_type and am.ac_no=cb.ac_no and ("+string_query+") and cn.chq_del!='T' order by cn.ac_no");
                rs=stmt.executeQuery("select distinct am.ac_type,am.ac_no,cn.chq_no,cn.next_chqdt,cn.post_dated,cn.stop_pymnt,cn.chq_del,concat_ws('',cm.fname,cm.mname,cm.lname) as name from ChequeNo cn,CustomerMaster cm,AccountMaster am,ChequeBook cb where cm.cid =am.cid and am.ac_no=cn.ac_no and am.ac_type=cn.ac_type and am.ac_no=cn.ac_no and am.ac_type=cb.ac_type and am.ac_no=cb.ac_no and ("+string_query+") and cn.chq_del!='T' order by cn.ac_no");
            			
            /**
             * Before return check whether where the condition bears any rows or not.
             * otherwise it client side it consider null object also as a valid object.
             * So before return make it null and return.
             */
            rs.last();
			System.out.println("length in getChequeNo():"+rs.getRow());
            if(rs.getRow()==0)
                return chequenoobject; 
			chequenoobject=new ChequeNoObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				
				chequenoobject[i]=new ChequeNoObject();
				
				System.out.println("iniside 1");
				
				chequenoobject[i].setAccountType(rs.getString("ac_type"));
				chequenoobject[i].setAccountNo(rs.getInt("ac_no"));
				chequenoobject[i].setAccountName(rs.getString("name"));
				
				System.out.println("iniside 2");
				
				chequenoobject[i].setChequeNo(rs.getInt("chq_no"));
				chequenoobject[i].setNextChequeDate(rs.getString("next_chqdt"));
				chequenoobject[i].setPostDated(rs.getString("post_dated"));
				chequenoobject[i].setStopPayment(rs.getString("stop_pymnt"));
				chequenoobject[i].setChequeDeleted(rs.getString("chq_del"));
				
				System.out.println("inisde 3");
				
				i++;
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally
		{
			conn.close();
		}
		
		return chequenoobject;
	}
	
	public String getName(int acc_no,String acc_type) throws RemoteException
	{
		String accname=null;
		int cid=0;
		Connection conn=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select cid from AccountMaster where ac_no="+acc_no+" && ac_ty="+acc_type+" ");
			rs.last();
			rs.beforeFirst();
			while(rs.next())
			{
				cid=rs.getInt(1);
			}
			ResultSet rs1=stmt.executeQuery("select lname from CustomerMaster where cid="+cid+" ");
			
			while(rs1.next())
			{
				accname=rs1.getString(1);
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return accname;
	}
	
	public ChequeBookObject[] getChequeBook(int flag,String query,String fdate,String ldate) throws RemoteException,SQLException
	{
		UserVerifier obj_userverifer =  null;
		
		ChequeBookObject chequebookobject[]=null;
		Connection conn=null;
		System.out.println("flag:"+flag);
		System.out.println("query"+query);
		System.out.println("fdate"+fdate);
		System.out.println("ldate"+ldate);
		
		//rs=stmt.executeQuery("select request_dt,ac_type,ac_no,book_no,fst_chq_no,lst_chq_no,de_user,de_tml,ve_user,ve_tml from ChequeBook where concat(right(request_dt,4),'-',mid(request_dt,locate('/',request_dt)+1, (locate('/',request_dt,4)-locate('/',request_dt)-1)),'-',left(request_dt,locate('/',request_dt)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(ldate)+"' ");
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;        
            
            /*  if(flag==0)
				rs=stmt.executeQuery("select distinct cb.request_dt,cb.ac_type,cb.ac_no,cb.book_no,cb.fst_chq_no,cb.lst_chq_no,cb.de_user,cb.de_tml,cb.ve_user,cb.ve_tml,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from ChequeBook cb,CustomerMaster cm where cm.cid in(select distinct c.cid from AccountMaster c,ChequeBook cb where concat(right(request_dt,4),'-',mid(request_dt,locate('/',request_dt)+1,(locate('/',request_dt,4)-locate('/',request_dt)-1)),'-',left(request_dt,locate('/',request_dt)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(ldate)+"' and c.ac_no=cb.ac_no and c.ac_type=cb.ac_type) order by cb.book_no");
			else
				rs=stmt.executeQuery("select distinct cb.request_dt,cb.ac_type,cb.ac_no,cb.book_no,cb.fst_chq_no,cb.lst_chq_no,cb.de_user,cb.de_tml,cb.ve_user,cb.ve_tml,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from ChequeBook cb,CustomerMaster cm where cm.cid in(select distinct c.cid from AccountMaster c,ChequeBook cb where concat(right(request_dt,4),'-',mid(request_dt,locate('/',request_dt)+1,(locate('/',request_dt,4)-locate('/',request_dt)-1)),'-',left(request_dt,locate('/',request_dt)-1))  '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(ldate)+"' and c.ac_no=cb.ac_no and c.ac_type=cb.ac_type) and ("+query+") order by cb.book_no");
            */
			
			 /** Changed on 22/08/2005 */
            if(flag==0)
                rs=stmt.executeQuery("select cb.request_dt,cb.ac_type,cb.ac_no,cb.book_no,cb.fst_chq_no,cb.lst_chq_no,cb.de_user,cb.de_tml,cb.ve_user,cb.ve_tml,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from ChequeBook cb,CustomerMaster cm,AccountMaster am where cm.cid=am.cid and concat(right(cb.request_dt,4),'-',mid(cb.request_dt,locate('/',cb.request_dt)+1,(locate('/',cb.request_dt,4)-locate('/',cb.request_dt)-1)),'-',left(cb.request_dt,locate('/',cb.request_dt)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(ldate)+"' and am.ac_no=cb.ac_no and am.ac_type=cb.ac_type order by cb.book_no");
            else
                rs=stmt.executeQuery("select cb.request_dt,cb.ac_type,cb.ac_no,cb.book_no,cb.fst_chq_no,cb.lst_chq_no,cb.de_user,cb.de_tml,cb.ve_user,cb.ve_tml,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from ChequeBook cb,CustomerMaster cm,AccountMaster am where cm.cid=am.cid and concat(right(cb.request_dt,4),'-',mid(cb.request_dt,locate('/',cb.request_dt)+1,(locate('/',cb.request_dt,4)-locate('/',cb.request_dt)-1)),'-',left(cb.request_dt,locate('/',cb.request_dt)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(ldate)+"' and am.ac_no=cb.ac_no and am.ac_type=cb.ac_type and ("+query+") order by cb.book_no");
            
			//	rs=stmselet.executeQuery("select request_dt,ac_type,ac_no,book_no,fst_chq_no,lst_chq_no,de_user,de_tml,ve_user,ve_tml from ChequeBook where concat(right(request_dt,4),'-',mid(request_dt,locate('/',request_dt)+1, (locate('/',request_dt,4)-locate('/',request_dt)-1)),'-',left(request_dt,locate('/',request_dt)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(ldate)+"' and ("+query+") ");
			
			rs.last();
			System.out.println("length in server"+rs.getRow());
            if(rs.getRow()==0)
                return chequebookobject;            
			chequebookobject=new ChequeBookObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				chequebookobject[i]=new ChequeBookObject();
				chequebookobject[i].setRequestedDate(rs.getString("cb.request_dt"));
				chequebookobject[i].setAccountType(rs.getString("cb.ac_type"));
				chequebookobject[i].setAccountNo(rs.getInt("cb.ac_no"));
				chequebookobject[i].setAccountName(rs.getString("name"));
				chequebookobject[i].setChequeBookNo(rs.getInt("cb.book_no"));
				chequebookobject[i].setFirstChequeNo(rs.getInt("cb.fst_chq_no"));
				chequebookobject[i].setLastChequeNo(rs.getInt("cb.lst_chq_no"));
				
				//added by renuka
				
				obj_userverifer = new UserVerifier();
				obj_userverifer.setUserId(rs.getString("cb.de_user"));
				obj_userverifer.setUserTml(rs.getString("cb.de_tml"));
				obj_userverifer.setVerId(rs.getString("cb.ve_user"));
		        obj_userverifer.setVerTml(rs.getString("cb.ve_tml"));
		       
		        chequebookobject[i].setObj_userverifer(obj_userverifer);
				i++;
				
				
				
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally
		{
			conn.close();
		}
		return chequebookobject;
	}
	
	/** Added on 22/08/2006  Deepa*/
	public OpenedClosedInoperatedAccountObject[] getOpenedClosedInoperatedAccounts(int flag,String acctype,String fdate,String tdate,String list,String query) throws RemoteException,SQLException
	{
		OpenedClosedInoperatedAccountObject[] openclosedinoperatedaccountobject=null;
		Connection conn=null;		
		try	
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			
			System.out.println("Inside getOpened/Closed/Inoperative/Freezed Accounts Query=="+query);
			System.out.println("AC_type===="+acctype);
			
			int ac=0;
			if(list.equals("Opened"))
			{
				ac=Integer.parseInt(acctype.substring(0,4));
				switch(ac)
				{
					case 1017:
					case 1018:
					case 1002:
					case 1007:	if(flag==0)
			    					rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.ac_opendate,ac.ac_closedate,ac.ac_status,ac.freeze_ind,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from AccountMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid and concat(right(ac.ac_opendate,4),'-',mid(ac.ac_opendate,locate('/',ac.ac_opendate)+1,(locate('/',ac.ac_opendate,4)-locate('/',ac.ac_opendate)-1)),'-',left(ac.ac_opendate,locate('/',ac.ac_opendate)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid");
			    				else
			    					rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.ac_opendate,ac.ac_closedate,ac.ac_status,ac.freeze_ind,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from AccountMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid and concat(right(ac.ac_opendate,4),'-',mid(ac.ac_opendate,locate('/',ac.ac_opendate)+1,(locate('/',ac.ac_opendate,4)-locate('/',ac.ac_opendate)-1)),'-',left(ac.ac_opendate,locate('/',ac.ac_opendate)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and ("+query+")");
			    				break;
			    				
					case 1014:
					case 1015:	if(flag==0)
    								rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,ac.ac_closedate,cm.custtype,cm.sub_category,ac.ac_opendate,ac.ac_status,ac.freeze_ind,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from ODCCMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid and concat(right(ac.ac_opendate,4),'-',mid(ac.ac_opendate,locate('/',ac.ac_opendate)+1,(locate('/',ac.ac_opendate,4)-locate('/',ac.ac_opendate)-1)),'-',left(ac.ac_opendate,locate('/',ac.ac_opendate)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid");
    							else
    								rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,ac.ac_closedate,cm.custtype,cm.sub_category,ac.ac_opendate,ac.ac_status,ac.freeze_ind,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from ODCCMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid and concat(right(ac.ac_opendate,4),'-',mid(ac.ac_opendate,locate('/',ac.ac_opendate)+1,(locate('/',ac.ac_opendate,4)-locate('/',ac.ac_opendate)-1)),'-',left(ac.ac_opendate,locate('/',ac.ac_opendate)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and ("+query+")");
    							break;						
					
					case 1006:	if(flag==0)
    								rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.open_date,ac.close_date,ac.status,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from PygmyMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid and concat(right(ac.open_date,4),'-',mid(ac.open_date,locate('/',ac.open_date)+1,(locate('/',ac.open_date,4)-locate('/',ac.open_date)-1)),'-',left(ac.open_date,locate('/',ac.open_date)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid");
    							else
    								rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.open_date,ac.close_date,ac.status,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from PygmyMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid and concat(right(ac.open_date,4),'-',mid(ac.open_date,locate('/',ac.open_date)+1,(locate('/',ac.open_date,4)-locate('/',ac.open_date)-1)),'-',left(ac.open_date,locate('/',ac.open_date)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and ("+query+")");
    							break;
    							
    				case 1001: if(flag==0)
									rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.mem_issuedate,ac.mem_cl_date,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from ShareMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid and concat(right(ac.mem_issuedate,4),'-',mid(ac.mem_issuedate,locate('/',ac.mem_issuedate)+1,(locate('/',ac.mem_issuedate,4)-locate('/',ac.mem_issuedate)-1)),'-',left(ac.mem_issuedate,locate('/',ac.mem_issuedate)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid");
							   else
							   		rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.mem_issuedate,ac.mem_cl_date,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from ShareMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid and concat(right(ac.mem_issuedate,4),'-',mid(ac.mem_issuedate,locate('/',ac.mem_issuedate)+1,(locate('/',ac.mem_issuedate,4)-locate('/',ac.mem_issuedate)-1)),'-',left(ac.mem_issuedate,locate('/',ac.mem_issuedate)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and ("+query+")");
							   break;
							   
					case 1003:
					case 1004:
    				case 1005: if(flag==0)
									rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.dep_date,ac.close_date,ac.close_ind,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from DepositMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid and concat(right(ac.dep_date,4),'-',mid(ac.dep_date,locate('/',ac.dep_date)+1,(locate('/',ac.dep_date,4)-locate('/',ac.dep_date)-1)),'-',left(ac.dep_date,locate('/',ac.dep_date)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid");
    						   else
    								rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.dep_date,ac.close_date,ac.close_ind,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from DepositMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid and concat(right(ac.dep_date,4),'-',mid(ac.dep_date,locate('/',ac.dep_date)+1,(locate('/',ac.dep_date,4)-locate('/',ac.dep_date)-1)),'-',left(ac.dep_date,locate('/',ac.dep_date)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and ("+query+")");
    						   break;
    						   
    				case 1009: if(flag==0)
									rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.allot_dt,ac.close_dt,ac.freeze_ind,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from LockerMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid and concat(right(ac.allot_dt,4),'-',mid(ac.allot_dt,locate('/',ac.allot_dt)+1,(locate('/',ac.allot_dt,4)-locate('/',ac.allot_dt)-1)),'-',left(ac.allot_dt,locate('/',ac.allot_dt)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid");
    						   else
    						   		rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.allot_dt,ac.close_dt,ac.freeze_ind,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from LockerMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid and concat(right(ac.allot_dt,4),'-',mid(ac.allot_dt,locate('/',ac.allot_dt)+1,(locate('/',ac.allot_dt,4)-locate('/',ac.allot_dt)-1)),'-',left(ac.allot_dt,locate('/',ac.allot_dt)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and ("+query+")");
    						   break;
    						   
    				case 1008:
					case 1010:	if(flag==0)
    								rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,ac.close_date,cm.custtype,cm.sub_category,ac.appn_date,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from LoanMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid and concat(right(ac.appn_date,4),'-',mid(ac.appn_date,locate('/',ac.appn_date)+1,(locate('/',ac.appn_date,4)-locate('/',ac.appn_date)-1)),'-',left(ac.appn_date,locate('/',ac.appn_date)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid");
    							else
    								rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,ac.close_date,cm.custtype,cm.sub_category,ac.appn_date,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from LoanMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid and concat(right(ac.appn_date,4),'-',mid(ac.appn_date,locate('/',ac.appn_date)+1,(locate('/',ac.appn_date,4)-locate('/',ac.appn_date)-1)),'-',left(ac.appn_date,locate('/',ac.appn_date)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and ("+query+")");
    							break;
							   
							   
				}
			}			
			else if(list.equals("Closed"))
			{	
				ac=Integer.parseInt(acctype.substring(0,4));
				switch(ac)
				{
					case 1017:
					case 1018:
					case 1002:
					case 1007:	if(flag==0)
									rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,ac.ac_closedate,cm.custtype,cm.sub_category,ac.ac_opendate,ac.ac_status,ac.freeze_ind,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from AccountMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid= ac.cid and concat(right(ac.ac_closedate,4),'-',mid(ac.ac_closedate,locate('/',ac.ac_closedate)+1, (locate('/',ac.ac_closedate,4)-locate('/',ac.ac_closedate)-1)),'-',left(ac.ac_closedate,locate('/',ac.ac_closedate)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid");
	                			else
	                				rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,ac.ac_closedate,cm.custtype,cm.sub_category,ac.ac_opendate,ac.ac_status,ac.freeze_ind,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from AccountMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid= ac.cid and concat(right(ac.ac_closedate,4),'-',mid(ac.ac_closedate,locate('/',ac.ac_closedate)+1, (locate('/',ac.ac_closedate,4)-locate('/',ac.ac_closedate)-1)),'-',left(ac.ac_closedate,locate('/',ac.ac_closedate)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and ("+query+")");
			    				break;
					case 1014:
					case 1015:	if(flag==0)
									rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,ac.ac_closedate,cm.custtype,cm.sub_category,ac.ac_opendate,ac.freeze_ind,ac.ac_status,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from ODCCMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid= ac.cid and concat(right(ac.ac_closedate,4),'-',mid(ac.ac_closedate,locate('/',ac.ac_closedate)+1, (locate('/',ac.ac_closedate,4)-locate('/',ac.ac_closedate)-1)),'-',left(ac.ac_closedate,locate('/',ac.ac_closedate)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid");
        						else
        							rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,ac.ac_closedate,cm.custtype,cm.sub_category,ac.ac_opendate,ac.freeze_ind,ac.ac_status,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from ODCCMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid= ac.cid and concat(right(ac.ac_closedate,4),'-',mid(ac.ac_closedate,locate('/',ac.ac_closedate)+1, (locate('/',ac.ac_closedate,4)-locate('/',ac.ac_closedate)-1)),'-',left(ac.ac_closedate,locate('/',ac.ac_closedate)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and ("+query+")");
    							break;						
					
					case 1006:	if(flag==0)
    								rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.open_date,ac.close_date,ac.status,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from PygmyMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid= ac.cid and concat(right(ac.close_date,4),'-',mid(ac.close_date,locate('/',ac.close_date)+1, (locate('/',ac.close_date,4)-locate('/',ac.close_date)-1)),'-',left(ac.close_date,locate('/',ac.close_date)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid");
    							else
    								rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.open_date,ac.close_date,ac.status,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from PygmyMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid= ac.cid and concat(right(ac.close_date,4),'-',mid(ac.close_date,locate('/',ac.close_date)+1, (locate('/',ac.close_date,4)-locate('/',ac.close_date)-1)),'-',left(ac.close_date,locate('/',ac.close_date)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and ("+query+")");
    							break;
    			
					case 1001:	if(flag==0)
									rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.mem_issuedate,ac.mem_cl_date,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from ShareMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid= ac.cid and concat(right(ac.mem_cl_date,4),'-',mid(ac.mem_cl_date,locate('/',ac.mem_cl_date)+1, (locate('/',ac.mem_cl_date,4)-locate('/',ac.mem_cl_date)-1)),'-',left(ac.mem_cl_date,locate('/',ac.mem_cl_date)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid");
        						else
        							rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.mem_issuedate,ac.mem_cl_date,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from ShareMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid= ac.cid and concat(right(ac.mem_cl_date,4),'-',mid(ac.mem_cl_date,locate('/',ac.mem_cl_date)+1, (locate('/',ac.mem_cl_date,4)-locate('/',ac.mem_cl_date)-1)),'-',left(ac.mem_cl_date,locate('/',ac.mem_cl_date)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and ("+query+")");
    							break;
    							
					case 1003:
					case 1004:
    				case 1005: if(flag==0)
									rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.dep_date,ac.close_date,ac.close_ind,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from DepositMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid= ac.cid and concat(right(ac.close_date,4),'-',mid(ac.close_date,locate('/',ac.close_date)+1, (locate('/',ac.close_date,4)-locate('/',ac.close_date)-1)),'-',left(ac.close_date,locate('/',ac.close_date)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid");
    						   else
    								rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.dep_date,ac.close_date,ac.close_ind,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from DepositMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid= ac.cid and concat(right(ac.close_date,4),'-',mid(ac.close_date,locate('/',ac.close_date)+1, (locate('/',ac.close_date,4)-locate('/',ac.close_date)-1)),'-',left(ac.close_date,locate('/',ac.close_date)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and ("+query+")");
    						   break;
    						   
    				case 1009: if(flag==0)
									rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.allot_dt,ac.close_dt,ac.freeze_ind,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from LockerMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid= ac.cid and concat(right(ac.close_dt,4),'-',mid(ac.close_dt,locate('/',ac.close_dt)+1, (locate('/',ac.close_dt,4)-locate('/',ac.close_dt)-1)),'-',left(ac.close_dt,locate('/',ac.close_dt)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid");
    						   else
    						   		rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.allot_dt,ac.close_dt,ac.freeze_ind,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from LockerMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid= ac.cid and concat(right(ac.close_dt,4),'-',mid(ac.close_dt,locate('/',ac.close_dt)+1, (locate('/',ac.close_dt,4)-locate('/',ac.close_dt)-1)),'-',left(ac.close_dt,locate('/',ac.close_dt)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and ("+query+")");
    						   break;
    						   
    				case 1008:
					case 1010:	if(flag==0)
    								rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,ac.close_date,cm.custtype,cm.sub_category,ac.appn_date,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from LoanMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid= ac.cid and concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid");
    							else
    								rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,ac.close_date,cm.custtype,cm.sub_category,ac.appn_date,concat_ws(cm.fname,'',cm.mname,'',cm.lname) as name,ca.address from LoanMaster ac,CustomerMaster cm,CustomerAddr ca where cm.cid= ac.cid and concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and ("+query+")");
    							break;
				}                	
			}
			else if(list.equals("Freezed"))
			{					
				ac=Integer.parseInt(acctype.substring(0,4));
				switch(ac)
				{
					case 1014:
					case 1015:
					case 1017:
					case 1018:
					case 1002:
					case 1007:	if(flag==0)
									rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.ac_opendate,ac.ac_closedate,ac.freeze_ind,ac.ac_status,concat_ws('',cm.fname,cm.mname,cm.lname) as name,ca.address from AccountMaster ac,CustomerMaster cm,CustomerAddr ca,FreezedAccounts fa where freeze_ind='T' and fa.from_date  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"'and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and fa.ac_type=ac.ac_type and fa.ac_no=ac.ac_no and frz_inoper_ind='F'");
								else
									rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.ac_opendate,ac.ac_closedate,ac.freeze_ind,ac.ac_status,concat_ws('',cm.fname,cm.mname,cm.lname) as name,ca.address from AccountMaster ac,CustomerMaster cm,CustomerAddr ca,FreezedAccounts fa where freeze_ind='T' and fa.from_date  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"'and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and fa.ac_type=ac.ac_type and fa.ac_no=ac.ac_no and frz_inoper_ind='F' and ("+query+")");
			    				break;
			    				
					case 1009:	if(flag==0)
    								rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.allot_dt,ac.close_dt,ac.freeze_ind,concat_ws('',cm.fname,cm.mname,cm.lname) as name,ca.address from LockerMaster ac,CustomerMaster cm,CustomerAddr ca,FreezedAccounts fa where freeze_ind='T' and fa.from_date  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"'and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and fa.ac_type=ac.ac_type and fa.ac_no=ac.ac_no and frz_inoper_ind='F'");
    							else
    								rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.allot_dt,ac.close_dt,ac.freeze_ind,concat_ws('',cm.fname,cm.mname,cm.lname) as name,ca.address from LockerMaster ac,CustomerMaster cm,CustomerAddr ca,FreezedAccounts fa where freeze_ind='T' and fa.from_date  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"'and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and fa.ac_type=ac.ac_type and fa.ac_no=ac.ac_no and frz_inoper_ind='F' and ("+query+")");
    							break;	
    			   	
				}				
			}
			else if(list.equals("InOperated"))
			{					
				ac=Integer.parseInt(acctype.substring(0,4));
				switch(ac)
				{
					case 1014:
					case 1015:
					case 1017:
					case 1018:
					case 1002:
					case 1007:	if(flag==0)
									rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.ac_opendate,ac.ac_closedate,ac.freeze_ind,ac.ac_status,concat_ws('',cm.fname,cm.mname,cm.lname) as name,ca.address from AccountMaster ac,CustomerMaster cm,CustomerAddr ca,FreezedAccounts fa where ac_status='I' and fa.from_date  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and fa.ac_type=ac.ac_type and fa.ac_no=ac.ac_no and frz_inoper_ind='I'");
								else
									rs=stmt.executeQuery("select distinct ac.ac_no,ac.ac_type,cm.custtype,cm.sub_category,ac.ac_opendate,ac.ac_closedate,ac.freeze_ind,ac.ac_status,concat_ws('',cm.fname,cm.mname,cm.lname) as name,ca.address from AccountMaster ac,CustomerMaster cm,CustomerAddr ca,FreezedAccounts fa where ac_status='I' and fa.from_date  between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ac.ac_type='"+acctype+"' and cm.cid=ac.cid and cm.cid=ca.cid and fa.ac_type=ac.ac_type and fa.ac_no=ac.ac_no and frz_inoper_ind='I' and ("+query+")");
			    				break;
				}				
			}
			
			rs.last();
			System.out.println("Length in OpenClosedInoperated"+rs.getRow());			
            if(rs.getRow()>0)
                openclosedinoperatedaccountobject = new OpenedClosedInoperatedAccountObject[rs.getRow()];
            else
                return openclosedinoperatedaccountobject;            
            rs.beforeFirst();
						
            int i=0;			
			while(rs.next())
			{
				openclosedinoperatedaccountobject[i]=new OpenedClosedInoperatedAccountObject();
				openclosedinoperatedaccountobject[i].setAccountNo(rs.getInt("ac.ac_no"));
				openclosedinoperatedaccountobject[i].setAccountType(rs.getString("ac.ac_type"));
				openclosedinoperatedaccountobject[i].setAccountName(rs.getString("name"));
				openclosedinoperatedaccountobject[i].setAccountAddress(rs.getString("ca.address"));	
				openclosedinoperatedaccountobject[i].setcustType(rs.getInt("cm.custtype"));
				openclosedinoperatedaccountobject[i].setAccountCategory(rs.getInt("cm.sub_category"));
				openclosedinoperatedaccountobject[i].setFreezeInd("");
				if(acctype.startsWith("1002")|| acctype.startsWith("1007")||acctype.startsWith("1014")||acctype.startsWith("1015")||acctype.startsWith("1017")||acctype.startsWith("1018"))
				{
					openclosedinoperatedaccountobject[i].setFreezeInd(rs.getString("ac.freeze_ind"));
					openclosedinoperatedaccountobject[i].setCloseDate(rs.getString("ac.ac_closedate"));				
					openclosedinoperatedaccountobject[i].setOpenDate(rs.getString("ac.ac_opendate"));
					openclosedinoperatedaccountobject[i].setAccountStatus(rs.getString("ac.ac_status"));
				}
				else if(acctype.startsWith("1006"))
				{
					openclosedinoperatedaccountobject[i].setCloseDate("");				
					openclosedinoperatedaccountobject[i].setOpenDate(rs.getString("ac.open_date"));
					openclosedinoperatedaccountobject[i].setAccountStatus(rs.getString("ac.status"));
				}
				else if(acctype.startsWith("1001"))
				{
					openclosedinoperatedaccountobject[i].setCloseDate("");				
					openclosedinoperatedaccountobject[i].setOpenDate(rs.getString("ac.mem_issuedate"));
					if(rs.getString("ac.mem_cl_date")!=null)
					{
						openclosedinoperatedaccountobject[i].setCloseDate(rs.getString("ac.mem_cl_date"));	
						openclosedinoperatedaccountobject[i].setAccountStatus("C");
					}
					else
					{
						openclosedinoperatedaccountobject[i].setCloseDate("");	
						openclosedinoperatedaccountobject[i].setAccountStatus("O");
					}
				}
				else if(acctype.startsWith("1003")||acctype.startsWith("1004")||acctype.startsWith("1005"))
				{								
					openclosedinoperatedaccountobject[i].setOpenDate(rs.getString("ac.dep_date"));
					if(rs.getString("ac.close_date")!=null)
					{
						openclosedinoperatedaccountobject[i].setCloseDate(rs.getString("ac.close_date"));	
						openclosedinoperatedaccountobject[i].setAccountStatus("C");
					}
					else
					{
						openclosedinoperatedaccountobject[i].setCloseDate("");	
						openclosedinoperatedaccountobject[i].setAccountStatus("O");
					}
						
				}
				else if(acctype.startsWith("1009"))
				{									
					openclosedinoperatedaccountobject[i].setOpenDate(rs.getString("ac.allot_dt"));
					openclosedinoperatedaccountobject[i].setFreezeInd(rs.getString("ac.freeze_ind"));
					if(rs.getString("ac.close_dt")!=null)
					{
						openclosedinoperatedaccountobject[i].setCloseDate(rs.getString("ac.close_dt"));						
						openclosedinoperatedaccountobject[i].setAccountStatus("C");						
					}
					else
					{
						openclosedinoperatedaccountobject[i].setCloseDate("");	
						openclosedinoperatedaccountobject[i].setAccountStatus("O");
					}
				}
				else if(acctype.startsWith("1008")||acctype.startsWith("1010"))
				{								
					openclosedinoperatedaccountobject[i].setOpenDate(rs.getString("ac.appn_date"));
					if(rs.getString("ac.close_date")!=null)
					{
						openclosedinoperatedaccountobject[i].setCloseDate(rs.getString("ac.close_date"));	
						openclosedinoperatedaccountobject[i].setAccountStatus("C");
					}
					else
					{
						openclosedinoperatedaccountobject[i].setCloseDate("");	
						openclosedinoperatedaccountobject[i].setAccountStatus("O");
					}
						
				}
				
				i++;
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally
		{
			conn.close();
		}
		System.out.println("before return"+openclosedinoperatedaccountobject.length);
        return openclosedinoperatedaccountobject;
	}
	
	
	
	public OdccObject[] getOdccMaster(int flag,String date,int acctype,String query) throws RemoteException,SQLException
	{
	    System.out.println("flag == "+flag);
        System.out.println("date == "+date);
        System.out.println("acctype == "+acctype);
        System.out.println("query == "+query);
        
		OdccObject[] odcc=null;
        
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			/*if(flag==0)
				rs=stmt.executeQuery("select distinct od.ac_no,od.ac_type,ac.ac_opendate,od.creditlimit,ot.cl_bal,concat_ws('',fname,mname,lname) as name from ODCCTransaction ot,CustomerMaster cm,ODCCMaster od,AccountMaster ac where cm.cid in(select distinct odcc.cid from ODCCMaster odcc ,CustomerAddr cmm,CustomerMaster c where concat(right(odcc.last_tr_date,4),'-',mid(odcc.last_tr_date,locate('/',odcc.last_tr_date)+1,(locate('/',odcc.last_tr_date,4)-locate('/',odcc.last_tr_date)-1)),'-',left(odcc.last_tr_date,locate('/',odcc.last_tr_date)-1))='2005-09-15' and od.ac_type='"+acctype+"' and cmm.addr_type=odcc.addr_type and od.cid=cm.cid and ot.ac_type=od.ac_type and od.ac_no=ot.ac_no and od.ac_type=ot.ac_type and ac.ac_no=od.ac_no and ac.ac_type=od.ac_type) ");
			else
				rs=stmt.executeQuery("select distinct od.ac_no,od.ac_type,ac.ac_opendate,od.creditlimit,ot.cl_bal,concat(fname,'',mname,'',lname) as name from ODCCTransaction ot,CustomerMaster cm,ODCCMaster od,AccountMaster ac where cm.cid in(select distinct odcc.cid from ODCCMaster odcc ,CustomerAddr cmm,CustomerMaster c where concat(right(odcc.last_tr_date,4),'-',mid(odcc.last_tr_date,locate('/',odcc.last_tr_date)+1,(locate('/',odcc.last_tr_date,4)-locate('/',odcc.last_tr_date)-1)),'-',left(odcc.last_tr_date,locate('/',odcc.last_tr_date)-1))='2005-09-15' and od.ac_type='"+acctype+"' and cmm.addr_type=odcc.addr_type and od.cid=cm.cid and ot.ac_type=od.ac_type and od.ac_no=ot.ac_no and od.ac_type=ot.ac_type and ac.ac_no=od.ac_no and ac.ac_type=od.ac_type and ("+query+")) ");
			*/
            
            
            if(flag==0)
               // rs=stmt.executeQuery("select distinct od.ac_no,od.ac_type,ac.ac_opendate,od.creditlimit,ot.cl_bal,concat_ws('',fname,mname,lname) as name from ODCCTransaction ot,CustomerMaster cm,ODCCMaster od,AccountMaster ac,CustomerAddr cmm where concat(right(od.last_tr_date,4),'-',mid(od.last_tr_date,locate('/',od.last_tr_date)+1,(locate('/',od.last_tr_date,4)-locate('/',od.last_tr_date)-1)),'-',left(od.last_tr_date,locate('/',od.last_tr_date)-1))='"+Validations.convertYMD(date)+"' and od.ac_type='"+acctype+"' and cmm.addr_type=od.addr_type and od.cid=cm.cid and ot.ac_type=od.ac_type and od.ac_no=ot.ac_no and  cm.cid=od.cid and cm.cid=cmm.cid");
            rs=stmt.executeQuery("select distinct od.ac_no,od.ac_type,ac.ac_opendate,od.creditlimit,ot.cl_bal,concat_ws('',fname,mname,lname) as name from ODCCTransaction ot,CustomerMaster cm,ODCCMaster od,AccountMaster ac,CustomerAddr cmm where concat(right(od.last_tr_date,4),'-',mid(od.last_tr_date,locate('/',od.last_tr_date)+1,(locate('/',od.last_tr_date,4)-locate('/',od.last_tr_date)-1)),'-',left(od.last_tr_date,locate('/',od.last_tr_date)-1))<'"+Validations.convertYMD(date)+"' and od.ac_type='"+acctype+"' and cmm.addr_type=od.addr_type and od.cid=cm.cid and ot.ac_type=od.ac_type and od.ac_no=ot.ac_no and  cm.cid=od.cid and cm.cid=cmm.cid limit 20");    
            else
                rs=stmt.executeQuery("select distinct od.ac_no,od.ac_type,ac.ac_opendate,od.creditlimit,ot.cl_bal,concat_ws('',fname,mname,lname) as name from ODCCTransaction ot,CustomerMaster cm,ODCCMaster od,AccountMaster ac,CustomerAddr cmm where concat(right(od.last_tr_date,4),'-',mid(od.last_tr_date,locate('/',od.last_tr_date)+1,(locate('/',od.last_tr_date,4)-locate('/',od.last_tr_date)-1)),'-',left(od.last_tr_date,locate('/',od.last_tr_date)-1))<'"+Validations.convertYMD(date)+"' and od.ac_type='"+acctype+"' and cmm.addr_type=od.addr_type and od.cid=cm.cid and ot.ac_type=od.ac_type and od.ac_no=ot.ac_no and  cm.cid=od.cid and cm.cid=cmm.cid and ("+query+") limit 20");
            
            rs.last();
            System.out.println("length == "+rs.getRow());
            
            if(rs.getRow()>0)
			    odcc = new OdccObject[rs.getRow()];
            else
                return odcc;
            
            rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				odcc[i]=new OdccObject();
				odcc[i].setAccountNo(rs.getInt("od.ac_no"));
				odcc[i].setAccountType(rs.getString("od.ac_type"));
				odcc[i].setAccountOpenDate(rs.getString("ac.ac_opendate"));
				odcc[i].setCreditLimit(rs.getDouble("od.creditlimit"));
				odcc[i].setAccountBalance(rs.getDouble("ot.cl_bal"));
				odcc[i].setAccountName(rs.getString("name"));
				
				i++;
				
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally
		{
			conn.close();
		}
		System.out.println("length before return"+odcc.length);
        return odcc;
	}
	
	public double getAccountBalance(int acc_no) throws RemoteException,SQLException
	{
		double balance=0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select cl_bal from AccountTransaction where ac_no="+acc_no+"  ");				    
			
			while(rs.next())
				balance=rs.getDouble("cl_bal");
		}catch(Exception exception){exception.printStackTrace();}
		finally
		{
			conn.close();
		}
		return balance;
	}
	
	public double getCreditLimit(String acc_type,int acc_no) throws RemoteException,SQLException
	{
		double credit_limit=0;
		Connection  conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select creditlimit from ODCCMaster where ac_no="+acc_no+" and ac_type="+acc_type+" ");				    
			
			while(rs.next())
				credit_limit=rs.getDouble("creditlimit");
		}catch(Exception exception){exception.printStackTrace();}
		finally
		{
			conn.close();
		}
		
		return credit_limit;
	}
	
	//	Closing Balance R&P
	
	/**
	 * Generates report for SB Accounts between two dates passed
	 * from the client 
	 */
	//Changed by Deepa for CA/CABk/CABr/OD/CC on 05/04/2006
	public ClosingBalObject[] getReportSB(String string_modcode,String string_fromdate, String string_todate, int ac_catgry) throws RemoteException,SQLException
	{
		double double_open_bal=0,double_cl_bal=0,double_tran_amt=0;
		int int_count=0;
		double double_debit=0;
		double double_credit=0;
		double double_interest=0;
		int j=0;
		int int_no_records=0;
		int int_no_records_xtra=0;
		int int_first_record=0;
		String string_temp_date=null;
		
		ResultSet rs1=null,rs2=null;
		Statement s1=null;
		Connection conn=null;
		
		ClosingBalObject[] array_obj_customer=null;
		ClosingBalObject[] array_obj_customer_xtra=null;
		ClosingBalObject[] clos_new=null;
		System.out.println("From Date==="+string_fromdate);
		System.out.println("To Date===="+string_todate);
		String string_fdate=Validations.convertYMD(string_fromdate);
		String string_tdate=Validations.convertYMD(string_todate);
		System.out.println("Converted date="+string_fdate);		
		System.out.println("Convertede to date="+string_tdate);
		System.out.println("AC CATEGORY===="+ac_catgry);		
		String qry1=null;
		if(string_modcode.startsWith("1002")||string_modcode.startsWith("1007")||string_modcode.startsWith("1018")||string_modcode.startsWith("1017") )
		    qry1="select distinct am.ac_no,concat(IFNULL(fname,' '),'   ',IFNULL(mname,' '),'   ',IFNULL(lname,' ')) as name,trn_date,cd_ind,trn_amt,cl_bal,trn_type,trn_seq from AccountTransaction at,AccountMaster am,CustomerMaster cm where cm.custtype="+ac_catgry+" and am.ac_type='"+string_modcode+"'and am.ac_no=at.ac_no and  am.ac_type=at.ac_type and cm.cid=am.cid and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+string_fdate+"' and '"+string_tdate+"'  order by at.ac_no,at.ac_type,at.trn_seq,concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1) )";
			//qry1="select distinct at.ac_no,concat(IFNULL(fname,' '),'   ',IFNULL(mname,' '),'   ',IFNULL(lname,' ')) as name,trn_date,cd_ind,trn_amt,cl_bal,trn_type,trn_seq from AccountTransaction at,AccountMaster am,CustomerMaster cm where am.ac_type='"+string_modcode+"' and at.ac_no=am.ac_no and  at.ac_type=am.ac_type and cm.cid=am.cid order by ac_no,at.ac_type,concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1) )";
		else if(string_modcode.startsWith("1014")||string_modcode.startsWith("1015"))
		    qry1="select distinct am.ac_no,concat(IFNULL(fname,' '),'   ',IFNULL(mname,' '),'   ',IFNULL(lname,' ')) as name,trn_date,cd_ind,trn_amt,cl_bal,trn_type,trn_seq from ODCCTransaction at,ODCCMaster am,CustomerMaster cm where am.ac_type='"+string_modcode+"' and at.ac_no=am.ac_no and  at.ac_type=am.ac_type and cm.cid=am.cid and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+string_fdate+"' and '"+string_tdate+"'  order by at.ac_no,at.ac_type,at.trn_seq,concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1) )";
		
		//--------- between dates----------------------------------------------------------
		
		try
		{
			conn=getConnection();
			s1=conn.createStatement();

			rs1=s1.executeQuery(qry1);
						
			 while(rs1.next())
			 {
				 //System.out.println("11111111111111111111111111111111111111111111");
				 int_first_record=1;
				 
				 int temp1=rs1.getInt(1);
				 
				 if(rs1.next())
				 {
				 int temp2=rs1.getInt(1);
				 if(temp1!=temp2)
				 int_no_records=int_no_records+1;
				 }
				 rs1.previous();
			 }
			 int_no_records=int_no_records+int_first_record;
			 
			 array_obj_customer=new ClosingBalObject[int_no_records];
			 
			 rs1.beforeFirst();
			
			while(rs1.next())
			{
				//System.out.println("2222222222222222222222222222222222222222");
				int temp1=rs1.getInt(1);
				
				if(rs1.isLast())
				{
					array_obj_customer[j]=new ClosingBalObject();
					array_obj_customer[j].setAcNo(temp1);
				}
				
				if(rs1.next())
				{
					int temp2=rs1.getInt(1);
					if(temp1!=temp2)
					{
						array_obj_customer[j]=new ClosingBalObject();
						array_obj_customer[j].setAcNo(temp1);
						System.out.println("customer["+j+"]="+array_obj_customer[j].getAcNo());
						j++;
					}
				}
				rs1.previous();
			}
			
			rs1.beforeFirst();			
			j=0;
			
			while(rs1.next())
			{			
				if(array_obj_customer[j].getAcNo()==rs1.getInt(1))
				{	
					if(int_count==0)
					{						
						array_obj_customer[j].setName(rs1.getString(2));
						//System.out.println("DEEEEEEEEEEEEEEEEEEEPa"+array_obj_customer[j].getName());
						double_tran_amt=rs1.getDouble(5);
						double_cl_bal=rs1.getDouble(6);
						//System.out.println("TrnAmt="+double_tran_amt);
						//System.out.println("Cl Bal="+double_cl_bal);
						if(rs1.getString(4).equals("C"))
						{
							//System.out.println("Credittttttttt");
							if(double_tran_amt==double_cl_bal)
								double_open_bal=double_tran_amt;
							else
								double_open_bal=double_cl_bal-double_tran_amt;
							//System.out.println("Opening Balane="+double_open_bal);
						}
						if(rs1.getString(4).equals("D"))
						{
							//System.out.println("Debitttttttttt");
							if(double_tran_amt==double_cl_bal)
								double_open_bal=double_tran_amt;
							else
								double_open_bal=double_cl_bal+double_tran_amt;
							//System.out.println("Opening Balane="+double_open_bal);
						}
						
						array_obj_customer[j].setOpenBal(double_open_bal);
						array_obj_customer[j].setClBal(double_cl_bal);
						
						if(rs1.getString(4).equals("C"))
						{						    
							if(rs1.getString(7).equals("I")) //CH
							{								
								double_interest=double_interest+double_tran_amt;
								array_obj_customer[j].setInterest(double_interest);
							}
							else
							{
								double_credit=double_credit+double_tran_amt;
								array_obj_customer[j].setCreditAmt(double_credit);
							}
							
						}
						if(rs1.getString(4).equals("D"))
						{						  
						    if(rs1.getString(7).equals("I")) //CH
							{								
								double_interest=double_tran_amt-double_interest;
								array_obj_customer[j].setInterest(double_interest);								
							}
							else
							{
								double_debit=double_debit+double_tran_amt;
								array_obj_customer[j].setDebitAmt(double_debit);
							}
						}
						
						if(double_tran_amt==double_cl_bal)
						{
							double_credit=0;
							double_debit=0;
						}
						
						array_obj_customer[j].setOpenBal(double_open_bal);
						array_obj_customer[j].setCreditAmt(double_credit);
						array_obj_customer[j].setDebitAmt(double_debit);
						array_obj_customer[j].setClBal(double_cl_bal);
						array_obj_customer[j].setInterest(double_interest);
						/*System.out.println(" Next Opening Balane="+double_open_bal);
						System.out.println(" Next credit="+double_credit);
						System.out.println(" Next Debit="+double_debit);
						System.out.println(" Next Closing Bal="+double_cl_bal);*/
						int_count++;
					}
					
					else
					{						
						double_tran_amt=rs1.getDouble(5);
						/*System.out.println("TrnAmt="+double_tran_amt);
						System.out.println("Cl Bal="+double_cl_bal);*/
						if(rs1.getString(4).equals("C") )
						{
							if(rs1.getString(7).equals("I"))
							{								
								double_interest=double_interest+double_tran_amt;
								array_obj_customer[j].setInterest(double_interest);								
							}							
							else							
								double_credit=double_credit+double_tran_amt;								
						}
						
						if(rs1.getString(4).equals("D"))
						{
						    if(rs1.getString(7).equals("I"))
							{								
								double_interest=double_tran_amt-double_interest;
								array_obj_customer[j].setInterest(double_interest);								
							}							
							else													
								double_debit=double_debit+double_tran_amt;						    
						}
						
						/*if(double_tran_amt==double_cl_bal)
						{
							if(string_modcode.startsWith("1014")||string_modcode.startsWith("1015"))
								double_cl_bal=double_open_bal+double_credit-double_debit+double_interest;
							else
								double_cl_bal=double_tran_amt;
						}
						else*/
							double_cl_bal=double_open_bal+double_credit-double_debit+double_interest;
						
						array_obj_customer[j].setOpenBal(double_open_bal);
						array_obj_customer[j].setCreditAmt(double_credit);
						array_obj_customer[j].setDebitAmt(double_debit);
						array_obj_customer[j].setClBal(double_cl_bal);
						array_obj_customer[j].setInterest(double_interest);
						
						/*System.out.println(" Next credit="+double_credit);
						System.out.println(" Next Debit="+double_debit);
						System.out.println(" Next Closing Bal="+double_cl_bal);*/
						
					}
				}				
				else
				{
					if(rs1.getInt(1)!=array_obj_customer[j].getAcNo())
					{
						if(double_tran_amt==double_cl_bal)
							double_cl_bal=double_tran_amt;
						else
							double_cl_bal=double_open_bal+double_credit-double_debit+double_interest;
												
						array_obj_customer[j].setOpenBal(double_open_bal);
						array_obj_customer[j].setCreditAmt(double_credit);
						array_obj_customer[j].setDebitAmt(double_debit);
						array_obj_customer[j].setClBal(double_cl_bal);
						array_obj_customer[j].setInterest(double_interest);
					}
					
					rs1.previous();
					
					int_count=0;
					double_credit=0;
					double_debit=0;
					double_credit=0;
					double_interest=0;
					j++;
				}
			}
			
			j=0;			
			String qry_1=null;
			if(string_modcode.startsWith("1002")||string_modcode.startsWith("1007")||string_modcode.startsWith("1018")||string_modcode.startsWith("1017"))
			    qry_1="create temporary table temp select distinct at.ac_no from AccountTransaction at,CustomerMaster cm,AccountMaster am where cm.custtype="+ac_catgry+" and at.ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+string_fdate+"' and '"+string_tdate+"' and at.ac_no=am.ac_no and at.ac_type=am.ac_type and cm.cid=am.cid ";
			else
			    qry_1="create temporary table temp select distinct at.ac_no from ODCCTransaction at,CustomerMaster cm,ODCCMaster am where at.ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+string_fdate+"' and '"+string_tdate+"' and at.ac_no=am.ac_no and at.ac_type=am.ac_type and cm.cid=am.cid ";			
			Statement stmt_1=conn.createStatement();
			stmt_1.executeUpdate(qry_1);
			
			Statement stmt_2=conn.createStatement();
			String qry_2=null;
			if(string_modcode.startsWith("1002")||string_modcode.startsWith("1007")||string_modcode.startsWith("1018")||string_modcode.startsWith("1017"))
			    qry_2=" Select distinct  at1.ac_no from AccountTransaction at1,AccountMaster am,CustomerMaster cm where cm.custtype="+ac_catgry+" and (am.ac_status!='C' or am.ac_closedate is null) and am.ac_type='"+string_modcode+"' and at1.ac_type=am.ac_type and at1.ac_no=am.ac_no and am.cid=cm.cid and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <'"+string_fdate+"' and at1.ac_no not in (select ac_no from temp) order by at1.ac_no,at1.trn_seq ";
			else
			    qry_2="Select distinct  at1.ac_no from ODCCTransaction at1,ODCCMaster am where  (am.ac_status!='C' or am.ac_closedate is null) and am.ac_type='"+string_modcode+"' and at1.ac_type=am.ac_type and at1.ac_no=am.ac_no and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <'"+string_fdate+"' and at1.ac_no not in (select ac_no from temp) order by at1.ac_no,at1.trn_seq ";
			rs2=stmt_2.executeQuery(qry_2);
			
			Statement stmt_3=conn.createStatement();
			String qry_3="Drop table temp";
			stmt_3.executeUpdate(qry_3);
			
			int_first_record=0;
			
			while(rs2.next())
			{
				int_first_record=1;
				int temp1=rs2.getInt(1);
				
				if(rs2.next())
				{
					int temp2=rs2.getInt(1);
					if(temp1!=temp2)
						int_no_records_xtra=int_no_records_xtra+1;
				}
				rs2.previous();
			}
			int_no_records_xtra=int_no_records_xtra+int_first_record;
						
			array_obj_customer_xtra=new ClosingBalObject[int_no_records_xtra];			 
			rs2.beforeFirst();
			
			j=0;			
			while(rs2.next())
			{
				int temp1=rs2.getInt(1);
				
				if(rs2.isLast())
				{
					array_obj_customer_xtra[j]=new ClosingBalObject();
					array_obj_customer_xtra[j].setAcNo(temp1);
				}
				
				if(rs2.next())
				{
					int temp2=rs2.getInt(1);
			
					if(temp1!=temp2)
					{						
						array_obj_customer_xtra[j]=new ClosingBalObject();
						array_obj_customer_xtra[j].setAcNo(temp1);						
						j++;						
					}
				}
				rs2.previous();
			}
			
			for(int i=0;i<int_no_records_xtra;i++)
			{
				//array_obj_customer_xtra[i]=new ClosingBalObject();				
				array_obj_customer_xtra[i].setOpenBal(0);
				array_obj_customer_xtra[i].setClBal(0);
				array_obj_customer_xtra[i].setCreditAmt(0);
				array_obj_customer_xtra[i].setDebitAmt(0);
				array_obj_customer_xtra[i].setInterest(0);
				array_obj_customer_xtra[i].setName(null);
				
				
				Statement s3=conn.createStatement();
				String qry3=null;
				if(string_modcode.startsWith("1002")||string_modcode.startsWith("1007")||string_modcode.startsWith("1017")||string_modcode.startsWith("1018"))
				    qry3="Select max( concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) ) as trn_date from AccountTransaction where ac_no="+array_obj_customer_xtra[i].getAcNo()+" and ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) < '"+string_fdate+"'order by trn_seq   ";
				else
				    qry3="Select max( concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) ) as trn_date from ODCCTransaction where ac_no="+array_obj_customer_xtra[i].getAcNo()+" and ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) < '"+string_fdate+"' order by trn_seq  ";
				ResultSet rs3=s3.executeQuery(qry3);
				
				while(rs3.next())
					string_temp_date=rs3.getString("trn_date");
				
				s4=conn.createStatement();
				String qry4=null;
				if(string_modcode.startsWith("1002")||string_modcode.startsWith("1007")||string_modcode.startsWith("1017")||string_modcode.startsWith("1018"))
				    qry4="Select cl_bal from AccountTransaction where ac_no="+array_obj_customer_xtra[i].getAcNo()+" and ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) ='"+string_temp_date+"' order by trn_seq  ";
				else
				    qry4="Select cl_bal from ODCCTransaction where ac_no="+array_obj_customer_xtra[i].getAcNo()+" and  ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) ='"+string_temp_date+"' order by trn_seq  ";
				ResultSet rs4=s4.executeQuery(qry4);
				
				while(rs4.next())
				{
					array_obj_customer_xtra[i].setOpenBal(rs4.getDouble("cl_bal"));
					array_obj_customer_xtra[i].setClBal(rs4.getDouble("cl_bal"));					
				}
				
				Statement s5=conn.createStatement();
				String qry5=null;
				if(string_modcode.startsWith("1002")||string_modcode.startsWith("1007")||string_modcode.startsWith("1017")||string_modcode.startsWith("1018"))
				    qry5="Select concat(IFNULL(fname,' '),'   ',IFNULL(mname,' '),'   ',IFNULL(lname,' ')) as name from CustomerMaster cm,AccountMaster am where cm.custtype="+ac_catgry+" and am.ac_no="+array_obj_customer_xtra[i].getAcNo()+" and am.ac_type='"+string_modcode+"' and am.cid=cm.cid and am.ac_status!='C'";
				else
				    qry5="Select concat(IFNULL(fname,' '),'   ',IFNULL(mname,' '),'   ',IFNULL(lname,' ')) as name from CustomerMaster cm,ODCCMaster am where cm.custtype="+ac_catgry+" and am.ac_no="+array_obj_customer_xtra[i].getAcNo()+" and am.ac_type='"+string_modcode+"' and am.cid=cm.cid and am.ac_status!='C'";
				ResultSet rs5=s5.executeQuery(qry5);
				
				if(rs5.next())
					array_obj_customer_xtra[i].setName(rs5.getString("name"));					
				//System.out.println("NULLLLLLLLLLLLLLLLLLLLLLLLLLLLNAMEEEEEEEEEEEE=="+array_obj_customer_xtra[i].getName());				
			}	
			
			
			TreeMap map=new TreeMap();
			for(int i=0;i<array_obj_customer.length;i++)	
			{
				System.out.println("AccountNo=="+array_obj_customer[i].getAcNo());
				map.put(Integer.valueOf(String.valueOf(array_obj_customer[i].getAcNo())),array_obj_customer[i]);
			}						
			for(int i=0;i<array_obj_customer_xtra.length;i++)
			{
				System.out.println("account no="+array_obj_customer_xtra[i].getAcNo());
				map.put(Integer.valueOf(String.valueOf(array_obj_customer_xtra[i].getAcNo())),array_obj_customer_xtra[i]);
			}
			
			Set s=map.keySet();			
			Iterator it=s.iterator();
			clos_new=new ClosingBalObject[map.size()];
			int i=0;
			while(it.hasNext())
			{
				Object obj=it.next();				
				clos_new[i]=(ClosingBalObject)map.get(obj);				
				i++;
			}
			
			
		}
		catch(Exception e2){e2.printStackTrace();}
		finally
		{
			conn.close();
		}
		
		return clos_new;
	}
	public ClosingBalObject[] getReportSBNew(String string_modcode,String string_fromdate, String string_todate, int ac_catgry) throws RemoteException,SQLException
	{
		ResultSet rs1=null,rs2=null,rs_open=null,rs_cl=null,rs_rec=null,rs_pay=null;
		Statement s1=null,stmt_pay=null,stmt_rec=null,stmt_open=null,stmt_cl=null;
		Connection conn=null;
		String qry1=null;
		ClosingBalObject [] array_obj_customer=null;
		int ac_no=0;
		double open_bal=0,reciepts=0,payments=0,cl_bal=0;
		String tablename=null;
		
		
		try
		{
			conn=getConnection();
			s1=conn.createStatement();
			stmt_cl=conn.createStatement();
			stmt_open=conn.createStatement();
			stmt_rec=conn.createStatement();
			stmt_pay=conn.createStatement();
			
			
			if(string_modcode.startsWith("1002")||string_modcode.startsWith("1007")||string_modcode.startsWith("1017")||string_modcode.startsWith("1018"))
			{
				if(ac_catgry==-1)
					rs1=s1.executeQuery("select distinct ac_no,concat(IFNULL(fname,' '),'   ',IFNULL(mname,' '),'   ',IFNULL(lname,' ')) as name    from AccountMaster am,CustomerMaster cm  where am.cid=cm.cid and  ac_type='"+string_modcode+"' and concat(right(ac_opendate,4),'-',mid(ac_opendate,locate('/',ac_opendate)+1, (locate('/',ac_opendate,4)-locate('/',ac_opendate)-1)),'-',left(ac_opendate,locate('/',ac_opendate)-1))<='"+Validations.convertYMD(string_todate)+"' and ( (ac_closedate is null or ac_closedate ='') or ((concat(right(ac_closedate,4),'-',mid(ac_closedate,locate('/',ac_closedate)+1, (locate('/',ac_closedate,4)-locate('/',ac_closedate)-1)),'-',left(ac_closedate,locate('/',ac_closedate)-1))>='"+Validations.convertYMD(string_fromdate)+"')));");
				else
					rs1=s1.executeQuery("select distinct ac_no,concat(IFNULL(fname,' '),'   ',IFNULL(mname,' '),'   ',IFNULL(lname,' ')) as name    from AccountMaster am,CustomerMaster cm  where am.cid=cm.cid and cm.custtype="+ac_catgry+" and  ac_type='"+string_modcode+"' and concat(right(ac_opendate,4),'-',mid(ac_opendate,locate('/',ac_opendate)+1, (locate('/',ac_opendate,4)-locate('/',ac_opendate)-1)),'-',left(ac_opendate,locate('/',ac_opendate)-1))<='"+Validations.convertYMD(string_todate)+"' and ( (ac_closedate is null or ac_closedate ='') or ((concat(right(ac_closedate,4),'-',mid(ac_closedate,locate('/',ac_closedate)+1, (locate('/',ac_closedate,4)-locate('/',ac_closedate)-1)),'-',left(ac_closedate,locate('/',ac_closedate)-1))>='"+Validations.convertYMD(string_fromdate)+"')));");
				
				tablename="AccountTransaction";
			}
			if(string_modcode.startsWith("1015")||string_modcode.startsWith("1014"))
			{
				if(ac_catgry==-1)
					rs1=s1.executeQuery("select distinct ac_no,concat(IFNULL(fname,' '),'   ',IFNULL(mname,' '),'   ',IFNULL(lname,' ')) as name    from ODCCMaster am,CustomerMaster cm  where am.cid=cm.cid and  ac_type='"+string_modcode+"' and concat(right(ac_opendate,4),'-',mid(ac_opendate,locate('/',ac_opendate)+1, (locate('/',ac_opendate,4)-locate('/',ac_opendate)-1)),'-',left(ac_opendate,locate('/',ac_opendate)-1))<='"+Validations.convertYMD(string_todate)+"' and ( (ac_closedate is null or ac_closedate ='') or ((concat(right(ac_closedate,4),'-',mid(ac_closedate,locate('/',ac_closedate)+1, (locate('/',ac_closedate,4)-locate('/',ac_closedate)-1)),'-',left(ac_closedate,locate('/',ac_closedate)-1))>='"+Validations.convertYMD(string_fromdate)+"')));");
				else
					rs1=s1.executeQuery("select distinct ac_no,concat(IFNULL(fname,' '),'   ',IFNULL(mname,' '),'   ',IFNULL(lname,' ')) as name    from ODCCMaster am,CustomerMaster cm  where am.cid=cm.cid and cm.custtype="+ac_catgry+" and  ac_type='"+string_modcode+"' and concat(right(ac_opendate,4),'-',mid(ac_opendate,locate('/',ac_opendate)+1, (locate('/',ac_opendate,4)-locate('/',ac_opendate)-1)),'-',left(ac_opendate,locate('/',ac_opendate)-1))<='"+Validations.convertYMD(string_todate)+"' and ( (ac_closedate is null or ac_closedate ='') or ((concat(right(ac_closedate,4),'-',mid(ac_closedate,locate('/',ac_closedate)+1, (locate('/',ac_closedate,4)-locate('/',ac_closedate)-1)),'-',left(ac_closedate,locate('/',ac_closedate)-1))>='"+Validations.convertYMD(string_fromdate)+"')));");
				
				tablename="ODCCTransaction";
			}
			
			if(rs1.next())
			{
				rs1.last();
				array_obj_customer=new ClosingBalObject[rs1.getRow()];
				rs1.beforeFirst();
			}
			int i=0;
			while(rs1.next())
			{
				array_obj_customer[i]=new ClosingBalObject();
				ac_no=rs1.getInt("ac_no");
				//open bal;
				rs_open=stmt_open.executeQuery("select trn_seq,cl_bal from "+tablename+" where ac_no="+ac_no+" and ac_type='"+string_modcode+"' and  concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <'"+Validations.convertYMD(string_fromdate)+"' order by trn_seq desc limit 1");
				if(rs_open.next() && rs_open.getString("cl_bal")!=null)
					open_bal=rs_open.getDouble("cl_bal");
				else
					open_bal=0;
				//payments
				rs_pay=stmt_pay.executeQuery("select sum(trn_amt) from "+tablename+" where ac_no="+ac_no+" and ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"'  and cd_ind='D'");
				if(rs_pay.next() && rs_pay.getString(1)!=null)
					payments=rs_pay.getDouble(1);
				else
					payments=0;
				//reciepts
				rs_rec=stmt_rec.executeQuery("select sum(trn_amt) from "+tablename+" where ac_no="+ac_no+" and ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"'  and cd_ind='C'");
				if(rs_rec.next() && rs_rec.getString(1)!=null)
					reciepts=rs_rec.getDouble(1);
				else
					reciepts=0;
				
				//cl bal
				rs_cl=stmt_open.executeQuery("select trn_seq,cl_bal from "+tablename+" where ac_no="+ac_no+" and ac_type='"+string_modcode+"' and  concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <='"+Validations.convertYMD(string_todate)+"' order by trn_seq desc limit 1");
				if(rs_cl.next() && rs_cl.getString("cl_bal")!=null)
					cl_bal=rs_cl.getDouble("cl_bal");
				else
					cl_bal=0;
				
				
				array_obj_customer[i].setAcNo(ac_no);
				array_obj_customer[i].setName(rs1.getString("name"));
				array_obj_customer[i].setOpenBal(open_bal);
				array_obj_customer[i].setCreditAmt(reciepts);
				array_obj_customer[i].setDebitAmt(payments);
				array_obj_customer[i].setClBal(cl_bal);
				i++;
			}
		}catch(Exception e2){e2.printStackTrace();}
		finally
		{
			conn.close();
		}
		return array_obj_customer;
	}


	
	/**
	 * Generates report for Share Accounts between two dates passed
	 * from the client 
	 */
	public ClosingBalObject[] getReportSH(String string_modcode,String string_fromdate,String string_todate,String shr_cat) throws RemoteException,SQLException
	{
	    //Changed by Karthi==>04/05/2006
	    Connection conn=null;
		Statement stmt=null;
		ResultSet rs_perm=null,rs_temp=null;
		ClosingBalObject[] cb_obj_perm=null,cb_obj_temp=null;
		ClosingBalObject[] cb_obj_final=null;
		int no_of_perm=0,no_of_temp=0;
		int loop=2,round=2;
		try
		{
		    conn=getConnection();
		    stmt=conn.createStatement();
		    
		    for(int i=0;i<loop;i++)
		    {
		        if(i==0)
		        {
		            System.out.println("Loop== 0");
		            //rs_perm=stmt.executeQuery("Select st.ac_no,st.susp_ind,sm.mem_cat,sht.catname from ShareTransaction st,ShareMaster sm,ShareType sht where st.ac_type='"+string_modcode+"' and sm.sh_ind='P' and st.ac_type=sm.ac_type and st.ac_no=sm.ac_no and sm.sh_ind=st.susp_ind and sm.mem_cat=sht.mem_cat and st.ac_type=sht.ac_type and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"' order by mem_cat,st.ac_no");
		            if(shr_cat.equalsIgnoreCase("All"))
		                //rs_perm=stmt.executeQuery("Select distinct st.ac_no,st.susp_ind,sm.mem_cat,sht.catname from ShareTransaction st,ShareMaster sm,ShareType sht where st.ac_type='"+string_modcode+"' and sm.sh_ind='P' and sm.share_val!=0 and  sm.mem_cl_date is null and st.ac_type=sm.ac_type and st.ac_no=sm.ac_no and sm.sh_ind=st.susp_ind and sm.mem_cat=sht.mem_cat and st.ac_type=sht.ac_type order by mem_cat,st.ac_no");
		                rs_perm=stmt.executeQuery("Select distinct st.ac_no ,st.susp_ind,sm.mem_cat,sht.catname,sm.mem_cl_date from ShareTransaction st,ShareMaster sm,ShareType sht where st.ac_type='"+string_modcode+"'and sm.sh_ind='P' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1,(locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1))<='"+Validations.convertYMD(string_todate)+"' and sm.mem_cl_date is null and st.ac_type=sm.ac_type and st.ac_no=sm.ac_no and sm.sh_ind=st.susp_ind and sm.mem_cat=sht.mem_cat and st.ac_type=sht.ac_type union Select distinct st1.ac_no,st1.susp_ind,sm1.mem_cat,sht1.catname,sm1.mem_cl_date from ShareTransaction st1,ShareMaster sm1,ShareType sht1 where st1.ac_type='"+string_modcode+"' and sm1.sh_ind='P' and concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1,(locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and'"+Validations.convertYMD(string_todate)+"' and st1.ac_type=sm1.ac_type and st1.ac_no=sm1.ac_no and sm1.sh_ind=st1.susp_ind and sm1.mem_cat=sht1.mem_cat and st1.ac_type=sht1.ac_type union select distinct st2.ac_no,st2.susp_ind,sm2.mem_cat,sht2.catname,sm2.mem_cl_date from ShareTransaction st2,ShareMaster sm2,ShareType sht2 where st2.ac_type='"+string_modcode+"' and sm2.sh_ind='P'  and concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1,(locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1))>'"+Validations.convertYMD(string_todate)+"' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1,(locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1))<='"+Validations.convertYMD(string_todate)+"' and st2.ac_type=sm2.ac_type and st2.ac_no=sm2.ac_no and sm2.sh_ind=st2.susp_ind and sm2.mem_cat=sht2.mem_cat and st2.ac_type=sht2.ac_type order by mem_cat,ac_no");
		            else if(shr_cat.equalsIgnoreCase("Nominal"))
		                //rs_perm=stmt.executeQuery("Select distinct st.ac_no,st.susp_ind,sm.mem_cat,sht.catname from ShareTransaction st,ShareMaster sm,ShareType sht where st.ac_type='"+string_modcode+"' and sht.catname='"+shr_cat+"'and sm.sh_ind='P' and  sm.mem_cl_date is null and st.ac_type=sm.ac_type and st.ac_no=sm.ac_no and sm.sh_ind=st.susp_ind and sm.mem_cat=sht.mem_cat and st.ac_type=sht.ac_type order by st.ac_no");
		                rs_perm=stmt.executeQuery("Select distinct st.ac_no,st.susp_ind,sm.mem_cat,sht.catname,sm.mem_cl_date from ShareTransaction st,ShareMaster sm,ShareType sht where st.ac_type='"+string_modcode+"' and sht.catname='"+shr_cat+"'and sm.sh_ind='P' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1,(locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1))<='"+Validations.convertYMD(string_todate)+"' and sm.mem_cl_date is null and st.ac_type=sm.ac_type and st.ac_no=sm.ac_no and sm.sh_ind=st.susp_ind and sm.mem_cat=sht.mem_cat and st.ac_type=sht.ac_type union Select distinct st1.ac_no,st1.susp_ind,sm1.mem_cat,sht1.catname,sm1.mem_cl_date from ShareTransaction st1,ShareMaster sm1,ShareType sht1 where st1.ac_type='"+string_modcode+"' and sm1.sh_ind='P' and sht1.catname='"+shr_cat+"' and concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1,(locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and'"+Validations.convertYMD(string_todate)+"' and st1.ac_type=sm1.ac_type and st1.ac_no=sm1.ac_no and sm1.sh_ind=st1.susp_ind and sm1.mem_cat=sht1.mem_cat and st1.ac_type=sht1.ac_type union select distinct st2.ac_no,st2.susp_ind,sm2.mem_cat,sht2.catname,sm2.mem_cl_date from ShareTransaction st2,ShareMaster sm2,ShareType sht2 where st2.ac_type='"+string_modcode+"' and sm2.sh_ind='P' and sht2.catname='"+shr_cat+"' and concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1,(locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1))>'"+Validations.convertYMD(string_todate)+"' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1,(locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1))<='"+Validations.convertYMD(string_todate)+"' and st2.ac_type=sm2.ac_type and st2.ac_no=sm2.ac_no and sm2.sh_ind=st2.susp_ind and sm2.mem_cat=sht2.mem_cat and st2.ac_type=sht2.ac_type order by 1");
		            else
		            {
		                //rs_perm=stmt.executeQuery("Select distinct st.ac_no,st.susp_ind,sm.mem_cat,sht.catname from ShareTransaction st,ShareMaster sm,ShareType sht where st.ac_type='"+string_modcode+"' and sht.catname='"+shr_cat+"'and sm.share_val!=0 and sm.sh_ind='P' and  sm.mem_cl_date is null and st.ac_type=sm.ac_type and st.ac_no=sm.ac_no and sm.sh_ind=st.susp_ind and sm.mem_cat=sht.mem_cat and st.ac_type=sht.ac_type order by st.ac_no");
		            	System.out.println("Regular");
		            	rs_perm=stmt.executeQuery("Select distinct st.ac_no,st.susp_ind,sm.mem_cat,sht.catname,sm.mem_cl_date from ShareTransaction st,ShareMaster sm,ShareType sht where st.ac_type='"+string_modcode+"' and sht.catname='"+shr_cat+"'and sm.share_val!=0 and sm.sh_ind='P' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1,(locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1))<='"+Validations.convertYMD(string_todate)+"' and sm.mem_cl_date is null and st.ac_type=sm.ac_type and st.ac_no=sm.ac_no and sm.sh_ind=st.susp_ind and sm.mem_cat=sht.mem_cat and st.ac_type=sht.ac_type union Select distinct st1.ac_no,st1.susp_ind,sm1.mem_cat,sht1.catname,sm1.mem_cl_date from ShareTransaction st1,ShareMaster sm1,ShareType sht1 where st1.ac_type='"+string_modcode+"' and sm1.sh_ind='P' and sht1.catname='"+shr_cat+"' and concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1,(locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and'"+Validations.convertYMD(string_todate)+"' and st1.ac_type=sm1.ac_type and st1.ac_no=sm1.ac_no and sm1.sh_ind=st1.susp_ind and sm1.mem_cat=sht1.mem_cat and st1.ac_type=sht1.ac_type union select distinct st2.ac_no,st2.susp_ind,sm2.mem_cat,sht2.catname,sm2.mem_cl_date from ShareTransaction st2,ShareMaster sm2,ShareType sht2 where st2.ac_type='"+string_modcode+"' and sm2.sh_ind='P' and sht2.catname='"+shr_cat+"' and concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1,(locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1))>'"+Validations.convertYMD(string_todate)+"' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1,(locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1))<='"+Validations.convertYMD(string_todate)+"' and st2.ac_type=sm2.ac_type and st2.ac_no=sm2.ac_no and sm2.sh_ind=st2.susp_ind and sm2.mem_cat=sht2.mem_cat and st2.ac_type=sht2.ac_type order by 1");
		            	System.out.println("row count:"+rs_perm.getRow());
		            	System.out.println("modcode="+string_modcode);
		            	System.out.println("share_cat="+shr_cat);
		            	System.out.println("todate="+string_todate);
		            	System.out.println("modcode="+string_fromdate);
		            }
		            rs_perm.last();
		            if(rs_perm.getRow()>0)
		            {
		                System.out.println("rs_perm.getRow()==>"+rs_perm.getRow());
		                int[] len=new int[rs_perm.getRow()];
		                rs_perm.beforeFirst();
		                String[] indication=new String[len.length];
		                int number=1,k=0;
		                while(rs_perm.next())
		                {
		                    //len[k]=rs_perm.getInt("st.ac_no");
		                    len[k]=rs_perm.getInt("ac_no");
		                    //indication[k]=rs_perm.getString("sht.catname");
		                    indication[k]=rs_perm.getString("catname");
		                    k++;
		                }
		                cb_obj_perm=new ClosingBalObject[len.length];
		                for(int j=0;j<len.length;j++)
		                {
		                    if(j==0)
		                    {
		                        cb_obj_perm[j]=new ClosingBalObject();
		                        cb_obj_perm[j].setAcNo(len[j]);
		                        cb_obj_perm[j].setShareInd(indication[j]);
		                    }
		                    else if(j>0)
		                    {
		                        if(len[j-1]!=len[j])
		                        {
		                            cb_obj_perm[number]=new ClosingBalObject();
		                            cb_obj_perm[number].setAcNo(len[j]);
		                            cb_obj_perm[number].setShareInd(indication[j]);
		                            number++;
		                        }
		                    }
		                }
		                no_of_perm=number;
		                System.out.println("No.of Permanent Records : "+no_of_perm);
		            }
		        }
		        else
		        {
		            System.out.println("Loop== 1");
		            //rs_temp=stmt.executeQuery("Select st.ac_no,st.susp_ind,sm.mem_cat,sht.catname from ShareTransaction st,ShareMaster sm,ShareType sht where st.ac_type='"+string_modcode+"' and sm.sh_ind='T' and st.ac_type=sm.ac_type and st.ac_no=sm.temp_no and sm.sh_ind=st.susp_ind and sm.mem_cat=sht.mem_cat and st.ac_type=sht.ac_type and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"' order by mem_cat,st.ac_no");
		            if(shr_cat.equalsIgnoreCase("All"))
		                //rs_temp=stmt.executeQuery("Select distinct st.ac_no,st.susp_ind,sm.mem_cat,sht.catname from ShareTransaction st,ShareMaster sm,ShareType sht where st.ac_type='"+string_modcode+"' and sm.sh_ind='T' and  sm.mem_cl_date is null and st.ac_type=sm.ac_type and st.ac_no=sm.temp_no and sm.sh_ind=st.susp_ind and sm.mem_cat=sht.mem_cat and st.ac_type=sht.ac_type order by sm.mem_cat,st.ac_no");
		                rs_temp=stmt.executeQuery("Select distinct st.ac_no ,st.susp_ind,sm.mem_cat,sht.catname,sm.mem_cl_date from ShareTransaction st,ShareMaster sm,ShareType sht where st.ac_type='"+string_modcode+"'and sm.sh_ind='T' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1,(locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1))<='"+Validations.convertYMD(string_todate)+"' and sm.mem_cl_date is null and st.ac_type=sm.ac_type and st.ac_no=sm.ac_no and sm.sh_ind=st.susp_ind and sm.mem_cat=sht.mem_cat and st.ac_type=sht.ac_type union Select distinct st1.ac_no,st1.susp_ind,sm1.mem_cat,sht1.catname,sm1.mem_cl_date from ShareTransaction st1,ShareMaster sm1,ShareType sht1 where st1.ac_type='"+string_modcode+"' and sm1.sh_ind='T' and concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1,(locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and'"+Validations.convertYMD(string_todate)+"' and st1.ac_type=sm1.ac_type and st1.ac_no=sm1.ac_no and sm1.sh_ind=st1.susp_ind and sm1.mem_cat=sht1.mem_cat and st1.ac_type=sht1.ac_type union select distinct st2.ac_no,st2.susp_ind,sm2.mem_cat,sht2.catname,sm2.mem_cl_date from ShareTransaction st2,ShareMaster sm2,ShareType sht2 where st2.ac_type='"+string_modcode+"' and sm2.sh_ind='T'  and concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1,(locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1))>'"+Validations.convertYMD(string_todate)+"' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1,(locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1))<='"+Validations.convertYMD(string_todate)+"' and st2.ac_type=sm2.ac_type and st2.ac_no=sm2.ac_no and sm2.sh_ind=st2.susp_ind and sm2.mem_cat=sht2.mem_cat and st2.ac_type=sht2.ac_type order by mem_cat,ac_no");
		            else if(shr_cat.equalsIgnoreCase("Nominal"))
		                //rs_temp=stmt.executeQuery("Select distinct st.ac_no,st.susp_ind,sm.mem_cat,sht.catname from ShareTransaction st,ShareMaster sm,ShareType sht where st.ac_type='"+string_modcode+"' and sht.catname='"+shr_cat+"'and sm.sh_ind='T' and  sm.mem_cl_date is null and st.ac_type=sm.ac_type and st.ac_no=sm.temp_no and sm.sh_ind=st.susp_ind and sm.mem_cat=sht.mem_cat and st.ac_type=sht.ac_type order by st.ac_no");
		                rs_temp=stmt.executeQuery("Select distinct st.ac_no,st.susp_ind,sm.mem_cat,sht.catname,sm.mem_cl_date from ShareTransaction st,ShareMaster sm,ShareType sht where st.ac_type='"+string_modcode+"' and sht.catname='"+shr_cat+"'and sm.sh_ind='T' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1,(locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1))<='"+Validations.convertYMD(string_todate)+"' and sm.mem_cl_date is null and st.ac_type=sm.ac_type and st.ac_no=sm.ac_no and sm.sh_ind=st.susp_ind and sm.mem_cat=sht.mem_cat and st.ac_type=sht.ac_type union Select distinct st1.ac_no,st1.susp_ind,sm1.mem_cat,sht1.catname,sm1.mem_cl_date from ShareTransaction st1,ShareMaster sm1,ShareType sht1 where st1.ac_type='"+string_modcode+"' and sm1.sh_ind='T' and sht1.catname='"+shr_cat+"' and concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1,(locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and'"+Validations.convertYMD(string_todate)+"' and st1.ac_type=sm1.ac_type and st1.ac_no=sm1.ac_no and sm1.sh_ind=st1.susp_ind and sm1.mem_cat=sht1.mem_cat and st1.ac_type=sht1.ac_type union select distinct st2.ac_no,st2.susp_ind,sm2.mem_cat,sht2.catname,sm2.mem_cl_date from ShareTransaction st2,ShareMaster sm2,ShareType sht2 where st2.ac_type='"+string_modcode+"' and sm2.sh_ind='T' and sht2.catname='"+shr_cat+"' and concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1,(locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1))>'"+Validations.convertYMD(string_todate)+"' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1,(locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1))<='"+Validations.convertYMD(string_todate)+"' and st2.ac_type=sm2.ac_type and st2.ac_no=sm2.ac_no and sm2.sh_ind=st2.susp_ind and sm2.mem_cat=sht2.mem_cat and st2.ac_type=sht2.ac_type union select distinct st2.ac_no,st2.susp_ind,sm2.mem_cat,sht2.catname,sm2.mem_cl_date from ShareTransaction st2,ShareMaster sm2,ShareType sht2 where st2.ac_type='"+string_modcode+"' and sm2.sh_ind='T' and sht2.catname='"+shr_cat+"' and concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1,(locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1))>'"+Validations.convertYMD(string_todate)+"' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1,(locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1))<='"+Validations.convertYMD(string_todate)+"' and st2.ac_type=sm2.ac_type and st2.ac_no=sm2.ac_no and sm2.sh_ind=st2.susp_ind and sm2.mem_cat=sht2.mem_cat and st2.ac_type=sht2.ac_type order by 1");
		            else
		            	System.out.println("****inside rs_temp*****");
		                //rs_temp=stmt.executeQuery("Select distinct st.ac_no,st.susp_ind,sm.mem_cat,sht.catname from ShareTransaction st,ShareMaster sm,ShareType sht where st.ac_type='"+string_modcode+"' and sht.catname='"+shr_cat+"'and sm.share_val!=0 and sm.sh_ind='T' and  sm.mem_cl_date is null and st.ac_type=sm.ac_type and st.ac_no=sm.temp_no and sm.sh_ind=st.susp_ind and sm.mem_cat=sht.mem_cat and st.ac_type=sht.ac_type order by st.ac_no");
		                rs_temp=stmt.executeQuery("Select distinct st.ac_no,st.susp_ind,sm.mem_cat,sht.catname,sm.mem_cl_date from ShareTransaction st,ShareMaster sm,ShareType sht where st.ac_type='"+string_modcode+"' and sht.catname='"+shr_cat+"'and sm.share_val!=0 and sm.sh_ind='T' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1,(locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1))<='"+Validations.convertYMD(string_todate)+"' and sm.mem_cl_date is null and st.ac_type=sm.ac_type and st.ac_no=sm.ac_no and sm.sh_ind=st.susp_ind and sm.mem_cat=sht.mem_cat and st.ac_type=sht.ac_type union Select distinct st1.ac_no,st1.susp_ind,sm1.mem_cat,sht1.catname,sm1.mem_cl_date from ShareTransaction st1,ShareMaster sm1,ShareType sht1 where st1.ac_type='"+string_modcode+"' and sm1.sh_ind='T' and sht1.catname='"+shr_cat+"' and concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1,(locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and'"+Validations.convertYMD(string_todate)+"' and st1.ac_type=sm1.ac_type and st1.ac_no=sm1.ac_no and sm1.sh_ind=st1.susp_ind and sm1.mem_cat=sht1.mem_cat and st1.ac_type=sht1.ac_type union select distinct st2.ac_no,st2.susp_ind,sm2.mem_cat,sht2.catname,sm2.mem_cl_date from ShareTransaction st2,ShareMaster sm2,ShareType sht2 where st2.ac_type='"+string_modcode+"' and sm2.sh_ind='T' and sht2.catname='"+shr_cat+"' and concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1,(locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1))>'"+Validations.convertYMD(string_todate)+"' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1,(locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1))<='"+Validations.convertYMD(string_todate)+"' and st2.ac_type=sm2.ac_type and st2.ac_no=sm2.ac_no and sm2.sh_ind=st2.susp_ind and sm2.mem_cat=sht2.mem_cat and st2.ac_type=sht2.ac_type union select distinct st2.ac_no,st2.susp_ind,sm2.mem_cat,sht2.catname,sm2.mem_cl_date from ShareTransaction st2,ShareMaster sm2,ShareType sht2 where st2.ac_type='"+string_modcode+"' and sm2.sh_ind='T' and sht2.catname='"+shr_cat+"' and concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1,(locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1))>'"+Validations.convertYMD(string_todate)+"' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1,(locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1))<='"+Validations.convertYMD(string_todate)+"' and st2.ac_type=sm2.ac_type and st2.ac_no=sm2.ac_no and sm2.sh_ind=st2.susp_ind and sm2.mem_cat=sht2.mem_cat and st2.ac_type=sht2.ac_type union select distinct st2.ac_no,st2.susp_ind,sm2.mem_cat,sht2.catname,sm2.mem_cl_date from ShareTransaction st2,ShareMaster sm2,ShareType sht2 where st2.ac_type='"+string_modcode+"' and sm2.sh_ind='T' and sht2.catname='"+shr_cat+"' and concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1,(locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1))>'"+Validations.convertYMD(string_todate)+"' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1,(locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1))<='"+Validations.convertYMD(string_todate)+"' and st2.ac_type=sm2.ac_type and st2.ac_no=sm2.ac_no and sm2.sh_ind=st2.susp_ind and sm2.mem_cat=sht2.mem_cat and st2.ac_type=sht2.ac_type order by 1");
		            
		            rs_temp.last();
		            
		            if(rs_temp.getRow()>0)
		            {
		                System.out.println("rs_temp.getRow()==>"+rs_temp.getRow());
		                int[] len1=new int[rs_temp.getRow()];
		                String[] indication1=new String[len1.length];
		                rs_temp.beforeFirst();
		                int number1=1,k1=0;
		                while(rs_temp.next())
		                {
		                    len1[k1]=rs_temp.getInt(1);
		                    indication1[k1]=rs_temp.getString("catname");
		                    k1++;
		                }
		                cb_obj_temp=new ClosingBalObject[len1.length];
		                for(int j=0;j<len1.length;j++)
		                {
		                    if(j==0)
		                    {
		                        cb_obj_temp[j]=new ClosingBalObject();
		                        cb_obj_temp[j].setAcNo(len1[j]);
		                        cb_obj_temp[j].setShareInd(indication1[j]);
		                    }
		                    else if(j>0)
		                    {
		                        if(len1[j-1]!=len1[j])
		                        {
		                            cb_obj_temp[number1]=new ClosingBalObject();
		                            cb_obj_temp[number1].setAcNo(len1[j]);
		                            cb_obj_temp[number1].setShareInd(indication1[j]);
		                            number1++;
		                        }
		                    }
		                }
		                no_of_temp=number1;
		                System.out.println("No.of Temprory Records : "+no_of_temp);
		            }
		        }
		    }
		    int total=no_of_perm+no_of_temp;
		    System.out.println("Total No.of Records : "+total);
		    double allot=0.0,withdraw=0.0;
		    if(no_of_perm>0)
		    {
		        for(int i=0;i<no_of_perm;i++)
		        {
		            ResultSet rs_p_bal=null,rs_p_name=null,rs_allot_p=null,rs_withdra_p=null;
		            allot=0;withdraw=0;
		            //rs_p=stmt.executeQuery("select st.share_bal,concat_ws(' ',fname,mname,lname) as name,sm.cid  from ShareTransaction st,ShareMaster sm,CustomerMaster cm where st.ac_no="+cb_obj_perm[i].getAcNo()+" and st.ac_type='"+string_modcode+"' and sm.ac_no=st.ac_no and sm.ac_type=st.ac_type and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <'"+Validations.convertYMD(string_fromdate)+"' and cm.cid=sm.cid order by trn_seq desc limit 1");
		            rs_p_bal=stmt.executeQuery("select st.share_bal,st.trn_date,sm.share_val from ShareTransaction st,ShareMaster sm where st.ac_no="+cb_obj_perm[i].getAcNo()+" and st.ac_type='"+string_modcode+"' and sm.ac_no=st.ac_no and sm.ac_type=st.ac_type and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <'"+Validations.convertYMD(string_fromdate)+"' order by trn_seq desc limit 1");
		            rs_p_bal.last();
		            int c=rs_p_bal.getRow();
		            
		            rs_p_bal.beforeFirst();
		            rs_p_bal.next();
		            if(c>0)
		                cb_obj_perm[i].setOpenBal(rs_p_bal.getDouble("st.share_bal"));
		            else
		            {
		                rs_p_bal=stmt.executeQuery("select share_val from ShareMaster where ac_no="+cb_obj_perm[i].getAcNo()+" and ac_type='"+string_modcode+"' ");
		                rs_p_bal.next();
		                cb_obj_perm[i].setOpenBal(rs_p_bal.getDouble("share_val")); 
		            }
		            
		            
		            rs_p_name=stmt.executeQuery("select concat_ws(' ',fname,mname,lname) as name,sm.cid  from ShareMaster sm,CustomerMaster cm,ShareTransaction st where st.ac_no="+cb_obj_perm[i].getAcNo()+" and st.ac_type='"+string_modcode+"' and sm.ac_no=st.ac_no and sm.ac_type=st.ac_type and cm.cid=sm.cid limit 1");
		           
		            System.out.println("*******custname==="+rs_p_name.getRow()); 
		            rs_p_name.next();
		            cb_obj_perm[i].setName(rs_p_name.getString("name"));
		            
		            rs_allot_p=stmt.executeQuery("Select sum(trn_amt) as amt from ShareTransaction st where st.ac_no="+cb_obj_perm[i].getAcNo()+" and st.ac_type='"+string_modcode+"' and trn_type='A' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"'");
		            rs_allot_p.last();
		            int a=rs_allot_p.getRow();
		            rs_allot_p.beforeFirst();
		            rs_allot_p.next();
		            if(a>0 || String.valueOf(rs_allot_p.getDouble("amt"))!=null)
		                allot=rs_allot_p.getDouble("amt");
		            else
		                allot=0;
		            
		            rs_withdra_p=stmt.executeQuery("Select sum(trn_amt) as amt from ShareTransaction st where st.ac_no="+cb_obj_perm[i].getAcNo()+" and st.ac_type='"+string_modcode+"' and trn_type='W' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"'");
		            rs_withdra_p.last();
		            int b=rs_withdra_p.getRow();
		            if(b>0||String.valueOf(rs_withdra_p.getDouble("amt"))!=null)
		                withdraw=rs_withdra_p.getDouble("amt");
		            else
		                withdraw=0;
		            
		            cb_obj_perm[i].setCreditAmt(allot);
		            cb_obj_perm[i].setDebitAmt(withdraw);
		            cb_obj_perm[i].setClBal(cb_obj_perm[i].getOpenBal()+cb_obj_perm[i].getCreditAmt()-cb_obj_perm[i].getDebitAmt());
		            cb_obj_perm[i].setInterest(0);
		            
		        }
		    }
		    
		    if(no_of_temp>0)
		    {
		        for(int i=0;i<no_of_temp;i++)
		        {
		            ResultSet rs_t_bal=null,rs_t_name,rs_allot_t=null,rs_withdra_t=null;
		            allot=0;withdraw=0;
		            rs_t_bal=stmt.executeQuery("select st.share_bal,st.trn_date from ShareTransaction st,ShareMaster sm where st.ac_no="+cb_obj_temp[i].getAcNo()+" and st.ac_type='"+string_modcode+"'and sm.temp_no=st.ac_no and sm.ac_type=st.ac_type and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <'"+Validations.convertYMD(string_fromdate)+"' order by trn_seq desc limit 1");
		            rs_t_bal.last();
		            int c=rs_t_bal.getRow();
		            rs_t_bal.beforeFirst();
		            rs_t_bal.next();
		            if(c>0)
		                cb_obj_temp[i].setOpenBal(rs_t_bal.getDouble("st.share_bal"));
		            else
		                cb_obj_temp[i].setOpenBal(0);
		            
		            rs_t_name=stmt.executeQuery("select concat_ws(' ',fname,mname,lname) as name,sm.cid  from ShareMaster sm,CustomerMaster cm,ShareTransaction st where st.ac_no="+cb_obj_temp[i].getAcNo()+" and st.ac_type='"+string_modcode+"' and sm.ac_no=st.ac_no and sm.ac_type=st.ac_type and cm.cid=sm.cid limit 1");
		            rs_t_name.next();
		            cb_obj_temp[i].setName(rs_t_name.getString("name"));
		            
		            rs_allot_t=stmt.executeQuery("Select sum(trn_amt) as amt from ShareTransaction st where st.ac_no="+cb_obj_temp[i].getAcNo()+" and st.ac_type='"+string_modcode+"' and trn_type='A' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"'");
		            rs_allot_t.last();
		            int a=rs_allot_t.getRow();
		            rs_allot_t.beforeFirst();
		            rs_allot_t.next();
		            if(a>0 || String.valueOf(rs_allot_t.getDouble("amt"))!=null)
		                allot=rs_allot_t.getDouble("amt");
		            else
		                allot=0;
		            
		            rs_withdra_t=stmt.executeQuery("Select sum(trn_amt) as amt from ShareTransaction st where st.ac_no="+cb_obj_temp[i].getAcNo()+" and st.ac_type='"+string_modcode+"' and trn_type='W' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"'");
		            rs_withdra_t.last();
		            int b=rs_withdra_t.getRow();
		            if(b>0||String.valueOf(rs_withdra_t.getDouble("amt"))!=null)
		                withdraw=rs_withdra_t.getDouble("amt");
		            else
		                withdraw=0;
		            
		            cb_obj_temp[i].setCreditAmt(allot);
		            cb_obj_temp[i].setDebitAmt(withdraw);
		            cb_obj_temp[i].setClBal(0);
		            cb_obj_temp[i].setInterest(cb_obj_temp[i].getOpenBal()+cb_obj_temp[i].getCreditAmt()-cb_obj_temp[i].getDebitAmt());
		        }
		    }
		    
		    int l=0;
		    cb_obj_final=new ClosingBalObject[total];
		    if(no_of_perm>0)
		    {
		        for(int i=0;i<no_of_perm;i++)
		        {
		            cb_obj_final[i]=new ClosingBalObject();
		            cb_obj_final[i].setAcNo(cb_obj_perm[i].getAcNo());
		            cb_obj_final[i].setName(cb_obj_perm[i].getName());
		            cb_obj_final[i].setShareInd(cb_obj_perm[i].getShareInd());
		            cb_obj_final[i].setOpenBal(cb_obj_perm[i].getOpenBal());
		            cb_obj_final[i].setCreditAmt(cb_obj_perm[i].getCreditAmt());
		            cb_obj_final[i].setDebitAmt(cb_obj_perm[i].getDebitAmt());
		            cb_obj_final[i].setClBal(cb_obj_perm[i].getClBal());
		            cb_obj_final[i].setInterest(cb_obj_perm[i].getInterest());
		        }
		    }
		    else if(no_of_temp>0 && no_of_perm>0)
		    {
		        l=no_of_perm;
		        for(int i=0;i<no_of_perm;i++)
		        {
		            cb_obj_final[i]=new ClosingBalObject();
		            cb_obj_final[i].setAcNo(cb_obj_perm[i].getAcNo());
		            cb_obj_final[i].setName(cb_obj_perm[i].getName());
		            cb_obj_final[i].setShareInd(cb_obj_perm[i].getShareInd());
		            cb_obj_final[i].setOpenBal(cb_obj_perm[i].getOpenBal());
		            cb_obj_final[i].setCreditAmt(cb_obj_perm[i].getCreditAmt());
		            cb_obj_final[i].setDebitAmt(cb_obj_perm[i].getDebitAmt());
		            cb_obj_final[i].setClBal(cb_obj_perm[i].getClBal());
		            cb_obj_final[i].setInterest(cb_obj_perm[i].getInterest());
		        } 
		        for(int j=0;j<no_of_temp;j++)
		        {
		            cb_obj_final[l]=new ClosingBalObject();
		            cb_obj_final[l].setAcNo(cb_obj_temp[j].getAcNo());
		            cb_obj_final[l].setName(cb_obj_temp[j].getName());
		            cb_obj_final[l].setShareInd(cb_obj_temp[j].getShareInd());
		            cb_obj_final[l].setOpenBal(cb_obj_temp[j].getOpenBal());
		            cb_obj_final[l].setCreditAmt(cb_obj_temp[j].getCreditAmt());
		            cb_obj_final[l].setDebitAmt(cb_obj_temp[j].getDebitAmt());
		            cb_obj_final[l].setClBal(cb_obj_temp[j].getClBal());
		            cb_obj_final[l].setInterest(cb_obj_temp[j].getInterest());
		            l++;
		        }
		    }
		    else if(no_of_temp>0 && no_of_perm==0)
		    {
		        for(int j=0;j<no_of_temp;j++)
		        {
		            cb_obj_final[j]=new ClosingBalObject();
		            cb_obj_final[j].setAcNo(cb_obj_temp[j].getAcNo());
		            cb_obj_final[j].setName(cb_obj_temp[j].getName());
		            cb_obj_final[j].setShareInd(cb_obj_temp[j].getShareInd());
		            cb_obj_final[j].setOpenBal(cb_obj_temp[j].getOpenBal());
		            cb_obj_final[j].setCreditAmt(cb_obj_temp[j].getCreditAmt());
		            cb_obj_final[j].setDebitAmt(cb_obj_temp[j].getDebitAmt());
		            cb_obj_final[j].setClBal(cb_obj_temp[j].getClBal());
		            cb_obj_final[j].setInterest(cb_obj_temp[j].getInterest());
		        }
		    }
			    
		}catch(SQLException ex){ex.printStackTrace();}
		finally
		{
		    try{conn.close();}catch(SQLException ex){ex.printStackTrace();}
		}
		return cb_obj_final;
			
	}
	
	/**
	 * author shubha
	 * @param string_modcode-->pygmy deposit acc type
	 * @param string_fromdate
	 * @param string_todate
	 * @return-->array object 
	 * @throws RemoteException
	 * @throws SQLException
	 * gives closing balances report for pygmy
	 */
	public ClosingBalObject[] getReportPD(String string_modcode,String string_fromdate, String string_todate) throws RemoteException,SQLException
	   {
			Connection conn=null;
			ClosingBalObject[] closebal_object=null;
			Statement stmt=null;    
			Statement stmt_update=null;
			Statement stmt_close=null;
			ResultSet rs_close=null;
			int numrows=0,i=0;
	            
		  try{
				conn=getConnection();			 
	     	  	stmt=conn.createStatement();
	     	  	stmt_update=conn.createStatement();
	     	  	stmt_close=conn.createStatement();
	     	   {
	     	  	 stmt.addBatch("drop table if exists pd1");
	 			 stmt.addBatch("drop table if exists trnseq_prn_open");
	 			 stmt.addBatch("drop table if exists reciepts");
	 			 stmt.addBatch("drop table if exists amt_paid");
	 			 stmt.addBatch("drop table if exists accrued");
	 			 stmt.addBatch("drop table if exists paid");
	 			 stmt.addBatch("drop table if exists payments");
	 			 stmt.executeBatch();
	 			 
	 			 stmt_update.executeUpdate("create temporary table  pd1 (ac_type varchar(20),ac_no  int(10), cid int(10),name varchar(70),open_bal_prn double(10,2) default 0.00,reciept double(10,2) default 0.00,payment double(10,2) default 0.00,close_bal_prn double(10,2) default 0.00,open_bal_int double(10,2) default 0.00,accrued double(10,2) default 0.00,paid double(10,2) default 0.00,close_bal_int double(10,2) default 0.00)");
	 			 stmt_update.executeUpdate("insert into pd1(ac_type,ac_no,cid)  (select distinct ac_type,ac_no,cid from PygmyMaster where ac_type='"+string_modcode+"' and (concat(right(open_date,4),'-',mid(open_date,locate('/',open_date)+1,(locate('/',open_date,4)-locate('/',open_date)-1)),'-',left(open_date,locate('/',open_date)-1))<='"+Validations.convertYMD(string_todate)+"') and ( close_date is null or concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,(locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))>='"+Validations.convertYMD(string_fromdate)+"')order by ac_type,ac_no)");
	 			 stmt_update.executeUpdate("update pd1 p set name= (select concat(IFNULL(fname,' '),'  ',IFNULL(mname,'  '),'  ',IFNULL(lname,'  '),' ')  from CustomerMaster cm where  cm.cid=p.cid)");
	 			 stmt_update.executeUpdate("drop table if exists trnseq_prn_open");
	 			 stmt_update.executeUpdate("create temporary table trnseq_prn_open select pt.ac_type,pt.ac_no,max(trn_seq)trn_seq,max(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))) as trn_date from PygmyTransaction pt,pd1 pd1 where pt.ac_type=pd1.ac_type and pt.ac_no =pd1.ac_no  and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) < '"+Validations.convertYMD(string_fromdate)+"' group by pt.ac_type,pt.ac_no");
	 			 stmt_update.executeUpdate("update pd1 pd1 set open_bal_prn=(select cl_bal from PygmyTransaction pt,trnseq_prn_open tr  where pt.ac_no=pd1.ac_no and pt.ac_type=pd1.ac_type and tr.trn_seq=pt.trn_seq and tr.ac_no=pd1.ac_no and tr.ac_type=pd1.ac_type limit 1 )");
	 			 stmt_update.executeUpdate("create temporary table reciepts select pd1.ac_type,pd1.ac_no,sum(trn_amt) as amount from  PygmyTransaction pt ,pd1 pd1 where pt.ac_type=pd1.ac_type and pt.ac_no=pd1.ac_no and trn_type='R' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"' group by pt.ac_no,trn_type");
	 			 stmt_update.executeUpdate("create temporary table payments select pd1.ac_type,pd1.ac_no,sum(trn_amt)-sum(int_paid) as amount,trn_type from  PygmyTransaction pt ,pd1 pd1  where pt.ac_type=pd1.ac_type and pt.ac_no=pd1.ac_no and trn_type='P' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))  between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"' group by pt.ac_no,trn_type");
	 			 stmt_update.executeUpdate("update pd1 pd1 set reciept=(select amount from reciepts pt  where pt.ac_no=pd1.ac_no and pt.ac_type=pd1.ac_type limit 1 )");
	 			 stmt_update.executeUpdate("update pd1 pd1 set payment=(select amount from payments pt  where pt.ac_no=pd1.ac_no and pt.ac_type=pd1.ac_type limit 1 )");
	 			 stmt_update.executeUpdate("drop table if exists trnseq_prn_open");
	 			 stmt_update.executeUpdate("create temporary table trnseq_prn_open select pt.ac_type,pt.ac_no,max(trn_seq)trn_seq,max(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))) as trn_date from PygmyTransaction pt,pd1 pd1 where pt.ac_type=pd1.ac_type and pt.ac_no =pd1.ac_no  and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <= '"+Validations.convertYMD(string_todate)+"' group by pt.ac_type,pt.ac_no");
	 			 stmt_update.executeUpdate("update pd1 pd1 set close_bal_prn=(select cl_bal from PygmyTransaction pt,trnseq_prn_open tr  where pt.ac_no=pd1.ac_no and pt.ac_type=pd1.ac_type and tr.trn_seq=pt.trn_seq and tr.ac_no=pd1.ac_no and tr.ac_type=pd1.ac_type limit 1 )");
	 			 stmt_update.executeUpdate("drop table if exists trnseq_prn_open");
	 			 stmt_update.executeUpdate("create temporary table trnseq_prn_open select pt.ac_type,pt.ac_no,max(trn_seq) trn_seq,max(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))) as trn_date from PygmyTransaction pt,pd1 pd1 where trn_type='I' and cd_ind='C' and pt.ac_type=pd1.ac_type and pt.ac_no =pd1.ac_no  and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) < '"+Validations.convertYMD(string_fromdate)+"' group by pt.ac_type,pt.ac_no");
	 			 stmt_update.executeUpdate("create temporary table amt_paid select pt.ac_type,pt.ac_no,sum(int_paid) as amount from  PygmyTransaction pt ,trnseq_prn_open  tr where pt.ac_no=tr.ac_no and pt.ac_type=tr.ac_type  and trn_type='P' and cd_ind='D'  and concat(right(pt.trn_date,4),'-',mid(pt.trn_date,locate('/',pt.trn_date)+1,(locate('/',pt.trn_date,4)-locate('/',pt.trn_date)-1)),'-',left(pt.trn_date,locate('/',pt.trn_date)-1)) <'"+Validations.convertYMD(string_fromdate)+"' group by pt.ac_type,pt.ac_no");
	 			 stmt_update.executeQuery("select pd1.ac_no,sum(pt.trn_amt)-am.amount from PygmyTransaction pt,trnseq_prn_open tr  ,amt_paid am,pd1 pd1 where pt.ac_no=pd1.ac_no and pt.ac_type=pd1.ac_type and tr.trn_seq=pt.trn_seq and tr.ac_no=pd1.ac_no and tr.ac_type=pd1.ac_type and tr.ac_no=am.ac_no  and tr.ac_type=am.ac_type and pt.ac_no=am.ac_no and am.ac_type=pt.ac_type group by pd1.ac_no");
	 			 stmt_update.executeUpdate("update pd1 pd1 set open_bal_int=(select sum(trn_amt)-am.amount from PygmyTransaction pt,trnseq_prn_open tr,amt_paid am   where pt.ac_no=pd1.ac_no and pt.ac_type=pd1.ac_type and tr.trn_seq=pt.trn_seq and tr.ac_no=pd1.ac_no and tr.ac_type=pd1.ac_type  and tr.ac_no=am.ac_no  and tr.ac_type=am.ac_type and pt.ac_no=am.ac_no and am.ac_type=pt.ac_type group by pd1.ac_no limit 1 )");
	 			 stmt_update.executeUpdate("create temporary table accrued select pd1.ac_type,pd1.ac_no,sum(trn_amt)as accrued from PygmyTransaction pt,pd1 pd1 where pt.ac_type=pd1.ac_type and pt.ac_no=pd1.ac_no and trn_type='I' and cd_ind='C' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"' group by pt.ac_no,trn_type");
	 			 stmt_update.executeUpdate("create temporary table paid select pd1.ac_type,pd1.ac_no,sum(int_paid)as paid from PygmyTransaction pt,pd1 pd1 where pt.ac_type=pd1.ac_type and pt.ac_no=pd1.ac_no and trn_type='P' and cd_ind='D' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"' group by pt.ac_no,trn_type");
	 			 stmt_update.executeUpdate("update pd1 pd1 set accrued=(select accrued from accrued pt  where pt.ac_no=pd1.ac_no and pt.ac_type=pd1.ac_type limit 1 )");
	 			 stmt_update.executeUpdate("update pd1 pd1 set paid=(select paid from paid pt  where pt.ac_no=pd1.ac_no and pt.ac_type=pd1.ac_type limit 1 )");
	 			 stmt_update.executeUpdate("drop table if exists trnseq_prn_open");
	 			 stmt_update.executeUpdate("update pd1 set open_bal_prn=0.00 where open_bal_prn is null");
	 			 stmt_update.executeUpdate("update pd1 set reciept=0.00 where reciept is null");
	 			 stmt_update.executeUpdate("update pd1 set payment=0.00 where payment is null");
	 			 stmt_update.executeUpdate("update pd1 set close_bal_prn=0.00 where close_bal_prn is null");
	 			 stmt_update.executeUpdate("update pd1 set open_bal_int=0.00 where open_bal_int is null");
	 			 stmt_update.executeUpdate("update pd1 set accrued=0.00 where accrued is null");
	 			 stmt_update.executeUpdate("update pd1 set paid=0.00 where paid is null");
	 			 stmt_update.executeUpdate("update pd1 set close_bal_int=0.00 where close_bal_int is null");
	 			 stmt_update.executeUpdate("update pd1 set close_bal_int= open_bal_int+accrued-paid");
	 			 
	 			 rs_close=stmt_close.executeQuery("select * from pd1");
	 			 if(rs_close.next())
	 			 {
	 			     rs_close.last();
	 			     numrows=rs_close.getRow();
	 			     closebal_object=new ClosingBalObject[numrows];
	 			     rs_close.beforeFirst();
	 			 }
	 			 while(rs_close.next())
	 			 {
	 			     closebal_object[i]=new ClosingBalObject();
	 			     closebal_object[i].setPgAcNo(rs_close.getInt("ac_no"));
	 			     closebal_object[i].setName(rs_close.getString("name"));
	 			     closebal_object[i].setOpenBal(rs_close.getDouble("open_bal_prn"));
	 			     closebal_object[i].setCreditAmt(rs_close.getDouble("reciept"));
	 			     closebal_object[i].setDebitAmt(rs_close.getDouble("payment"));
	 			     closebal_object[i].setClBal(rs_close.getDouble("close_bal_prn"));
	 			     closebal_object[i].setIntOpenBal(rs_close.getDouble("open_bal_int"));
	 			     closebal_object[i].setIntAcd(rs_close.getDouble("accrued"));
	 			     closebal_object[i].setIntPaid(rs_close.getDouble("paid"));
	 			     closebal_object[i].setIntClBal(rs_close.getDouble("close_bal_int"));
	 			     i++;
	 			 }
	 			 			 
	     	  }
	     	  			   			      
		  	}catch(Exception e){e.printStackTrace();}
			finally{
				try{
					conn.close();
				}catch(SQLException e){e.printStackTrace();}
			}
			
			System.out.println("closebal obj lngth=="+closebal_object.length);
			return closebal_object;
		}

	/**
	 * author shubha
	 * @param string_modcode-->pygmy deposit account type
	 * @param string_fromdate
	 * @param string_todate
	 * @param agentno
	 * @return-->array object
	 * @throws RemoteException
	 * @throws SQLException
	 * returns closing balances report agentwise
	 */
	public ClosingBalObject[] getReportPDAgentwise(String string_modcode,String string_fromdate, String string_todate,String agentno) throws RemoteException,SQLException
	{
	    Connection conn=null;
	    ClosingBalObject[] closebal_object=null;
	    Statement stmt=null;    
		Statement stmt_update=null;
		Statement stmt_close=null;
		ResultSet rs_close=null;
		int numrows=0,i=0;
			
		System.out.println(" mod code="+string_modcode);
		System.out.println("from date="+string_fromdate);
		System.out.println(" to date="+string_todate);
		System.out.println("agetn no="+agentno);
		int agentno1=Integer.parseInt(agentno);
		System.out.println("agent1 no="+agentno1);
		
		try{
			conn=getConnection();
			stmt=conn.createStatement();
     	  	stmt_update=conn.createStatement();
     	  	stmt_close=conn.createStatement();
     	  	
     	  	{
     	  	 stmt.addBatch("drop table if exists pd1");
 			 stmt.addBatch("drop table if exists trnseq_prn_open");
 			 stmt.addBatch("drop table if exists reciepts");
 			 stmt.addBatch("drop table if exists amt_paid");
 			 stmt.addBatch("drop table if exists accrued");
 			 stmt.addBatch("drop table if exists paid");
 			 stmt.addBatch("drop table if exists payments");
 			 stmt.executeBatch();
     	  	    
 			 stmt_update.executeUpdate("create temporary table  pd1 (ac_type varchar(20),ac_no  int(10), cid int(10),name varchar(70),open_bal_prn double(10,2) default 0.00,reciept double(10,2) default 0.00,payment double(10,2) default 0.00,close_bal_prn double(10,2) default 0.00,open_bal_int double(10,2) default 0.00,accrued double(10,2) default 0.00,paid double(10,2) default 0.00,close_bal_int double(10,2) default 0.00)");
			 stmt_update.executeUpdate("insert into pd1(ac_type,ac_no,cid)  (select distinct pm.ac_type,pm.ac_no,pm.cid from PygmyMaster pm,AgentMaster am where pm.ac_type='"+string_modcode+"' and pm.agt_no=am.ac_no and pm.agt_no="+agentno+" and (concat(right(pm.open_date,4),'-',mid(pm.open_date,locate('/',pm.open_date)+1,(locate('/',pm.open_date,4)-locate('/',pm.open_date)-1)),'-',left(pm.open_date,locate('/',pm.open_date)-1))<='"+Validations.convertYMD(string_todate)+"') and (pm.close_date is null or concat(right(pm.close_date,4),'-',mid(pm.close_date,locate('/',pm.close_date)+1,(locate('/',pm.close_date,4)-locate('/',pm.close_date)-1)),'-',left(pm.close_date,locate('/',pm.close_date)-1))>='"+Validations.convertYMD(string_fromdate)+"')order by pm.ac_type,pm.ac_no)");
			 stmt_update.executeUpdate("update pd1 p set name= (select concat(IFNULL(fname,' '),'  ',IFNULL(mname,'  '),'  ',IFNULL(lname,'  '),' ')  from CustomerMaster cm where  cm.cid=p.cid)");
			 stmt_update.executeUpdate("drop table if exists trnseq_prn_open");
			 stmt_update.executeUpdate("create temporary table trnseq_prn_open select pt.ac_type,pt.ac_no,max(trn_seq)trn_seq,max(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))) as trn_date from PygmyTransaction pt,pd1 pd1 where pt.ac_type=pd1.ac_type and pt.ac_no =pd1.ac_no  and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) < '"+Validations.convertYMD(string_fromdate)+"' group by pt.ac_type,pt.ac_no");
			 stmt_update.executeUpdate("update pd1 pd1 set open_bal_prn=(select cl_bal from PygmyTransaction pt,trnseq_prn_open tr  where pt.ac_no=pd1.ac_no and pt.ac_type=pd1.ac_type and tr.trn_seq=pt.trn_seq and tr.ac_no=pd1.ac_no and tr.ac_type=pd1.ac_type limit 1 )");
			 stmt_update.executeUpdate("create temporary table reciepts select pd1.ac_type,pd1.ac_no,sum(trn_amt) as amount from  PygmyTransaction pt ,pd1 pd1 where pt.ac_type=pd1.ac_type and pt.ac_no=pd1.ac_no and trn_type='R' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"' group by pt.ac_no,trn_type");
			 stmt_update.executeUpdate("create temporary table payments select pd1.ac_type,pd1.ac_no,sum(trn_amt)-sum(int_paid) as amount,trn_type from  PygmyTransaction pt ,pd1 pd1  where pt.ac_type=pd1.ac_type and pt.ac_no=pd1.ac_no and trn_type='P' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))  between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"' group by pt.ac_no,trn_type");
			 stmt_update.executeUpdate("update pd1 pd1 set reciept=(select amount from reciepts pt  where pt.ac_no=pd1.ac_no and pt.ac_type=pd1.ac_type limit 1 )");
			 stmt_update.executeUpdate("update pd1 pd1 set payment=(select amount from payments pt  where pt.ac_no=pd1.ac_no and pt.ac_type=pd1.ac_type limit 1 )");
			 stmt_update.executeUpdate("drop table if exists trnseq_prn_open");
			 stmt_update.executeUpdate("create temporary table trnseq_prn_open select pt.ac_type,pt.ac_no,max(trn_seq)trn_seq,max(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))) as trn_date from PygmyTransaction pt,pd1 pd1 where pt.ac_type=pd1.ac_type and pt.ac_no =pd1.ac_no  and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <= '"+Validations.convertYMD(string_todate)+"' group by pt.ac_type,pt.ac_no");
			 stmt_update.executeUpdate("update pd1 pd1 set close_bal_prn=(select cl_bal from PygmyTransaction pt,trnseq_prn_open tr  where pt.ac_no=pd1.ac_no and pt.ac_type=pd1.ac_type and tr.trn_seq=pt.trn_seq and tr.ac_no=pd1.ac_no and tr.ac_type=pd1.ac_type limit 1 )");
			 stmt_update.executeUpdate("drop table if exists trnseq_prn_open");
			 stmt_update.executeUpdate("create temporary table trnseq_prn_open select pt.ac_type,pt.ac_no,max(trn_seq) trn_seq,max(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))) as trn_date from PygmyTransaction pt,pd1 pd1 where trn_type='I' and cd_ind='C' and pt.ac_type=pd1.ac_type and pt.ac_no =pd1.ac_no  and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) < '"+Validations.convertYMD(string_fromdate)+"' group by pt.ac_type,pt.ac_no");
			 stmt_update.executeUpdate("create temporary table amt_paid select pt.ac_type,pt.ac_no,sum(int_paid) as amount from  PygmyTransaction pt ,trnseq_prn_open  tr where pt.ac_no=tr.ac_no and pt.ac_type=tr.ac_type  and trn_type='P' and cd_ind='D'  and concat(right(pt.trn_date,4),'-',mid(pt.trn_date,locate('/',pt.trn_date)+1,(locate('/',pt.trn_date,4)-locate('/',pt.trn_date)-1)),'-',left(pt.trn_date,locate('/',pt.trn_date)-1)) <'"+Validations.convertYMD(string_fromdate)+"' group by pt.ac_type,pt.ac_no");
			 stmt_update.executeQuery("select pd1.ac_no,sum(pt.trn_amt)-am.amount from PygmyTransaction pt,trnseq_prn_open tr  ,amt_paid am,pd1 pd1 where pt.ac_no=pd1.ac_no and pt.ac_type=pd1.ac_type and tr.trn_seq=pt.trn_seq and tr.ac_no=pd1.ac_no and tr.ac_type=pd1.ac_type and tr.ac_no=am.ac_no  and tr.ac_type=am.ac_type and pt.ac_no=am.ac_no and am.ac_type=pt.ac_type group by pd1.ac_no");
			 stmt_update.executeUpdate("update pd1 pd1 set open_bal_int=(select sum(trn_amt)-am.amount from PygmyTransaction pt,trnseq_prn_open tr,amt_paid am   where pt.ac_no=pd1.ac_no and pt.ac_type=pd1.ac_type and tr.trn_seq=pt.trn_seq and tr.ac_no=pd1.ac_no and tr.ac_type=pd1.ac_type  and tr.ac_no=am.ac_no  and tr.ac_type=am.ac_type and pt.ac_no=am.ac_no and am.ac_type=pt.ac_type group by pd1.ac_no limit 1 )");
			 stmt_update.executeUpdate("create temporary table accrued select pd1.ac_type,pd1.ac_no,sum(trn_amt)as accrued from PygmyTransaction pt,pd1 pd1 where pt.ac_type=pd1.ac_type and pt.ac_no=pd1.ac_no and trn_type='I' and cd_ind='C' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"' group by pt.ac_no,trn_type");
			 stmt_update.executeUpdate("create temporary table paid select pd1.ac_type,pd1.ac_no,sum(int_paid)as paid from PygmyTransaction pt,pd1 pd1 where pt.ac_type=pd1.ac_type and pt.ac_no=pd1.ac_no and trn_type='P' and cd_ind='D' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"' group by pt.ac_no,trn_type");
			 stmt_update.executeUpdate("update pd1 pd1 set accrued=(select accrued from accrued pt  where pt.ac_no=pd1.ac_no and pt.ac_type=pd1.ac_type limit 1 )");
			 stmt_update.executeUpdate("update pd1 pd1 set paid=(select paid from paid pt  where pt.ac_no=pd1.ac_no and pt.ac_type=pd1.ac_type limit 1 )");
			 stmt_update.executeUpdate("drop table if exists trnseq_prn_open");
			 stmt_update.executeUpdate("update pd1 set open_bal_prn=0.00 where open_bal_prn is null");
			 stmt_update.executeUpdate("update pd1 set reciept=0.00 where reciept is null");
			 stmt_update.executeUpdate("update pd1 set payment=0.00 where payment is null");
			 stmt_update.executeUpdate("update pd1 set close_bal_prn=0.00 where close_bal_prn is null");
			 stmt_update.executeUpdate("update pd1 set open_bal_int=0.00 where open_bal_int is null");
			 stmt_update.executeUpdate("update pd1 set accrued=0.00 where accrued is null");
			 stmt_update.executeUpdate("update pd1 set paid=0.00 where paid is null");
			 stmt_update.executeUpdate("update pd1 set close_bal_int=0.00 where close_bal_int is null");
			 stmt_update.executeUpdate("update pd1 set close_bal_int= open_bal_int+accrued-paid");
					         
			 rs_close=stmt_close.executeQuery("select * from pd1");
 			 if(rs_close.next())
 			 {
 			     rs_close.last();
 			     numrows=rs_close.getRow();
 			     closebal_object=new ClosingBalObject[numrows];
 			     rs_close.beforeFirst();
 			 }
 			 while(rs_close.next())
 			 {
 			     closebal_object[i]=new ClosingBalObject();
 			     closebal_object[i].setPgAcNo(rs_close.getInt("ac_no"));
 			     closebal_object[i].setName(rs_close.getString("name"));
 			     closebal_object[i].setOpenBal(rs_close.getDouble("open_bal_prn"));
 			     closebal_object[i].setCreditAmt(rs_close.getDouble("reciept"));
 			     closebal_object[i].setDebitAmt(rs_close.getDouble("payment"));
 			     closebal_object[i].setClBal(rs_close.getDouble("close_bal_prn"));
 			     closebal_object[i].setIntOpenBal(rs_close.getDouble("open_bal_int"));
 			     closebal_object[i].setIntAcd(rs_close.getDouble("accrued"));
 			     closebal_object[i].setIntPaid(rs_close.getDouble("paid"));
 			     closebal_object[i].setIntClBal(rs_close.getDouble("close_bal_int"));
 			     i++;
 			 }
		  }		        
		      			  			
	  	}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(SQLException e){e.printStackTrace();}
		}
		System.out.println("closebal agtwise lngth==="+closebal_object.length);
		return closebal_object;
		
	}




	/**
	 * Generates report for Pigmy Accounts between two dates passed
	 * from the client 
	 */
	public ClosingBalObject[] getReportPD1(String string_modcode,String string_fromdate, String string_todate) throws RemoteException,SQLException
	{
		System.out.println("Mod Code : "+string_modcode);
		double double_open_bal=0;
		double double_cl_bal=0;
		double double_credit=0;
		double double_debit=0;
		
		//double double_int_accrued=0;
		//double double_int_paid=0;
		int int_no_records=0;
		int int_no_records_xtra=0;
		int int_first_record=1;
		int j=0;
		int int_count=0;
		double double_tran_amt=0;
		double double_interest=0;
		
		//double double_int_open_bal=0;
		double double_int_cl_bal=0;
		
		String string_this_date=null;
		String string_temp_date=null;
		
		String string_fdate=Validations.convertYMD(string_fromdate);
		String string_tdate=Validations.convertYMD(string_todate);
		ClosingBalObject[] array_obj_customer=null;
		ClosingBalObject[] array_obj_customer_xtra=null;
		ClosingBalObject[] clos_new=null;
		
		ResultSet rs1=null,rs2=null,rs3=null,rs4=null,rs4c=null,rs5=null,rs6=null,rs6a=null,rs7=null;
		Statement s1=null,s2=null,s3=null,s4=null,s4c=null,s6=null,s6a=null,s7=null;
		Connection conn=null;
		
		String qry="Select distinct pt.ac_no,agt_no,trn_amt,cd_ind,trn_type,cl_bal,int_paid from PygmyTransaction pt,PygmyMaster pm where  pt.ac_type='"+string_modcode+"' and pt.ac_type=pm.ac_type and pm.ac_no=pt.ac_no and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+string_fdate+"' and '"+string_tdate+"' order by pt.ac_no,pt.ac_type,concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))";
		
		//------START OF BETWEEN DATES---------------------------------------------------------------
		try
		{
			conn=getConnection();
			s1=conn.createStatement();
			rs1=s1.executeQuery(qry);
			
			while(rs1.next())
			{
				int_first_record=1;
				int int_temp1=rs1.getInt("pt.ac_no");
				
				if(rs1.next())
				{
					int int_temp2=rs1.getInt("pt.ac_no");
					if(int_temp1!=int_temp2)
						int_no_records=int_no_records+1;
				}
				rs1.previous();
			}
			
			int_no_records=int_no_records+int_first_record;
			System.out.println("No of records: "+int_no_records);
			
			array_obj_customer=new ClosingBalObject[int_no_records];
			
			rs1.beforeFirst();
			
			while(rs1.next())
			{
				int_first_record=2;
				int int_temp1=rs1.getInt("pt.ac_no");
				
				if(rs1.isLast())
				{
					array_obj_customer[j]=new ClosingBalObject();
					array_obj_customer[j].setPgAcNo(int_temp1);
					System.out.println("customer["+j+"]="+array_obj_customer[j].getPgAcNo());
				}
				
				if(rs1.next())
				{
					int int_temp2=rs1.getInt("pt.ac_no");
					if(int_temp1!=int_temp2)
					{
						array_obj_customer[j]=new ClosingBalObject();
						array_obj_customer[j].setPgAcNo(int_temp1);
						System.out.println("customer["+j+"]="+array_obj_customer[j].getPgAcNo());
						j++;
					}
				}
				rs1.previous();
			}
			
			rs1.beforeFirst();
			j=0;
		
			while(rs1.next())
			{
				
				if(array_obj_customer[j].getPgAcNo()==rs1.getInt("pt.ac_no"))
				{
					
					if(int_count==0)
					{
						
						double_tran_amt=rs1.getDouble("trn_amt");
						double_cl_bal=rs1.getDouble("cl_bal");
						
						if(rs1.getString("cd_ind").equals("C") && rs1.getString("trn_type").equals("R"))
						{
							if(double_tran_amt==double_cl_bal)
								double_open_bal=double_tran_amt;
							else
							{
								double_open_bal=double_cl_bal-double_tran_amt;
								double_credit=double_credit+double_tran_amt;
								array_obj_customer[j].setCreditAmt(double_credit);
							}
						}
						
						if(rs1.getString("cd_ind").equals("D") && rs1.getString("trn_type").equals("P"))
						{
							if(double_tran_amt==double_cl_bal)
								double_open_bal=double_tran_amt;
							else
							{
								double_open_bal=double_cl_bal+double_tran_amt;
								double_debit=double_debit+double_tran_amt;
								array_obj_customer[j].setDebitAmt(double_debit);
							}
						}
						
						if(rs1.getString("cd_ind").equals("C") && rs1.getString("trn_type").equals("I"))
						{
							double_interest=double_interest+double_tran_amt;
							array_obj_customer[j].setInterest(double_interest);
							
						}
						
						array_obj_customer[j].setOpenBal(double_open_bal);
						array_obj_customer[j].setClBal(double_cl_bal);
						array_obj_customer[j].setAgentNo(rs1.getInt("agt_no"));
						
						if(rs1.getString("cd_ind").equals("C") && rs1.getString("trn_type").equals("P"))
						{
							if(double_tran_amt==double_cl_bal)
								double_open_bal=double_tran_amt;
							else
								double_open_bal=double_cl_bal+double_tran_amt;
							
							
						}
						
						if(rs1.getString("cd_ind").equals("D") && rs1.getString("trn_type").equals("R"))
						{
							if(double_tran_amt==double_cl_bal)
								double_open_bal=double_tran_amt;
							else
								double_open_bal=double_cl_bal+double_tran_amt;
							
							
						}
						
						
						if(rs1.getString("cd_ind").equals("C"))
						{
							
							if(rs1.getString("trn_type").equals("I")) //CH
							{
								
								double_interest=double_interest+double_tran_amt;
								array_obj_customer[j].setInterest(double_interest);
								
							}
							else
							{
								double_credit=double_credit+double_tran_amt;
								array_obj_customer[j].setCreditAmt(double_credit);
							}
							
						}
						if(rs1.getString("cd_ind").equals("D"))
						{
							double_debit=double_debit+double_tran_amt;
							array_obj_customer[j].setDebitAmt(double_debit);
						}
						
						if(double_tran_amt==double_cl_bal)
						{
							double_credit=0;
							double_debit=0;
						}
						
						array_obj_customer[j].setOpenBal(double_open_bal);
						array_obj_customer[j].setCreditAmt(double_credit);
						array_obj_customer[j].setDebitAmt(double_debit);
						array_obj_customer[j].setClBal(double_cl_bal);
						array_obj_customer[j].setInterest(double_interest);
						
						int_count++;
					}
					
					else
					{
						double_tran_amt=rs1.getDouble("trn_amt");
						
						if(rs1.getString("cd_ind").equals("C") && rs1.getString("trn_type").equals("R"))
							double_credit=double_credit+double_tran_amt;
						
						
						if(rs1.getString("cd_ind").equals("D") && rs1.getString("trn_type").equals("P"))
							double_debit=double_debit+double_tran_amt;
						
						
						if(rs1.getString("cd_ind").equals("C") && rs1.getString("trn_type").equals("I"))
						{
							
							double_interest=double_interest+double_tran_amt;
							array_obj_customer[j].setInterest(double_interest);
							
						}
						
						
						if(double_tran_amt==double_cl_bal)
							double_cl_bal=double_tran_amt;
						else
							double_cl_bal=double_open_bal+double_credit-double_debit+double_interest;
						
						array_obj_customer[j].setOpenBal(double_open_bal);
						array_obj_customer[j].setCreditAmt(double_credit);
						array_obj_customer[j].setDebitAmt(double_debit);
						array_obj_customer[j].setClBal(double_cl_bal);
						array_obj_customer[j].setInterest(double_interest);
						
					}
				}
				
				else
				{
					
					if(rs1.getInt("pt.ac_no")!=array_obj_customer[j].getPgAcNo())
					{
						if(double_tran_amt==double_cl_bal)
							double_cl_bal=double_tran_amt;
						else
							double_cl_bal=double_open_bal+double_credit-double_debit+double_interest;
						
						
						
						array_obj_customer[j].setOpenBal(double_open_bal);
						array_obj_customer[j].setCreditAmt(double_credit);
						array_obj_customer[j].setDebitAmt(double_debit);
						array_obj_customer[j].setClBal(double_cl_bal);
						//customer[j].setInterest(double_interest);
						//customer[j].setIntPaid(int_paid);
					}
					
					
					rs1.previous();
					
					int_count=0;
					double_credit=0;
					double_debit=0;
					double_credit=0;
					double_interest=0;
					//double_int_paid=0;
					j++;
				}
			}
			
			for(int i=0;i<array_obj_customer.length;i++)
			{
				System.out.println("Just above try");
				
				
				String qry1="Select sum(int_amt) as int_amt from PygmyQtrInterest pi,PygmyMaster pm where pi.ac_no='"+array_obj_customer[i].getPgAcNo()+"' and pm.ac_type='"+string_modcode+"' and pm.status!='C' and pi.ac_type=pm.ac_type and pm.ac_no=pi.ac_no and concat(right(int_date,4),'-',mid(int_date,locate('/',int_date)+1,(locate('/',int_date,4)-locate('/',int_date)-1)),'-',left(int_date,locate('/',int_date)-1)) between '"+string_fdate+"' and '"+string_tdate+"' order by pi.ac_no,concat(right(int_date,4),'-',mid(int_date,locate('/',int_date)+1,(locate('/',int_date,4)-locate('/',int_date)-1)),'-',left(int_date,locate('/',int_date)-1))";
				String qry2="Select max(int_date) as int_date from PygmyQtrInterest where ac_no='"+array_obj_customer[i].getPgAcNo()+"' and concat(right(int_date,4),'-',mid(int_date,locate('/',int_date)+1,(locate('/',int_date,4)-locate('/',int_date)-1)),'-',left(int_date,locate('/',int_date)-1)) <'"+string_fdate+"' ";
				
				s2=conn.createStatement();
				rs2=s2.executeQuery(qry1);
				
				array_obj_customer[i].setIntAcd(0.0);
				
				while(rs2.next())
				{
					array_obj_customer[i].setIntAcd(rs2.getDouble("int_amt"));
				}
				
				
				s3=conn.createStatement();
				rs3=s3.executeQuery(qry2);
				
				array_obj_customer[i].setIntOpenBal(0.0);
				
				while(rs3.next())
				{
					string_this_date=rs3.getString("int_date");
					
				}
				
				
				String qry3="Select int_amt from PygmyQtrInterest where ac_no="+array_obj_customer[i].getPgAcNo()+" and int_date='"+string_this_date+"' ";
				s4=conn.createStatement();
				rs4=s4.executeQuery(qry3);
				
				while(rs4.next())
				{
					array_obj_customer[i].setIntOpenBal(rs4.getDouble("int_amt"));
				}
				
				double_int_cl_bal=array_obj_customer[i].getIntOpenBal()+array_obj_customer[i].getIntAcd()-array_obj_customer[i].getInterest();
				array_obj_customer[i].setIntClBal(double_int_cl_bal);
				
				String qry5="Select concat(IFNULL(fname,' '),'  ',IFNULL(mname,'  '),'  ',IFNULL(lname,'  '),'  ') as name from CustomerMaster cm,PygmyMaster pm where ac_no="+array_obj_customer[i].getPgAcNo()+" and cm.cid=pm.cid";
				s4c=conn.createStatement();
				rs4c=s4c.executeQuery(qry5);
				if(rs4c.next())
					array_obj_customer[i].setName(rs4c.getString("name"));
				else
					array_obj_customer[i].setName(null);
				
				//double_int_open_bal=0;
				//double_int_accrued=0;
				
			}
		
		
		//------START OF NOT IN BETWEEN DATES---------------------------------------------------------------
		
			
			 String qry_1="create temporary table temp select pt1.ac_no from PygmyTransaction pt1,PygmyMaster pm1 where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+string_fdate+"' and '"+string_tdate+"' and pt1.ac_no=pm1.ac_no and pt1.ac_type=pm1.ac_type";
			Statement stmt_1=conn.createStatement();
			stmt_1.executeUpdate(qry_1);
			
			Statement stmt_2=conn.createStatement();
			String qry_2="Select distinct  pt.ac_no from PygmyTransaction pt,PygmyMaster pm where pm.status!='C' and pm.ac_type='"+string_modcode+"' and pt.ac_type=pm.ac_type and pt.ac_no=pm.ac_no and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<'"+string_fdate+"' and pt.ac_no not in (select ac_no from temp) order by pt.ac_no";
			rs5=stmt_2.executeQuery(qry_2);
			
			Statement stmt_3=conn.createStatement();
			String qry_3="Drop table temp";
			stmt_3.executeUpdate(qry_3);
			
			j=0;
			int_no_records=0;
			int_first_record=0;
			
			while(rs5.next())
			{
				System.out.println("Just inside while1");
				int_first_record=1;
				
				int temp1=rs5.getInt(1);
				
				if(rs5.next())
				{
					int temp2=rs5.getInt(1);
					if(temp1!=temp2)
						int_no_records_xtra=int_no_records_xtra+1;
				}
				rs5.previous();
			}
			
			int_no_records_xtra=int_no_records_xtra+int_first_record;
			System.out.println("No of records: "+int_no_records_xtra);
			
			array_obj_customer_xtra=new ClosingBalObject[int_no_records_xtra];
			rs5.beforeFirst();
			
			while(rs5.next())
			{
				System.out.println("Just inside while2");
				int_first_record=2;
				int temp1=rs5.getInt(1);
				
				if(rs5.isLast())
				{
					array_obj_customer_xtra[j]=new ClosingBalObject();
					array_obj_customer_xtra[j].setPgAcNo(temp1);
					System.out.println("customer["+j+"]="+array_obj_customer_xtra[j].getPgAcNo());
				}
				
				if(rs5.next())
				{
					int temp2=rs5.getInt(1);
					if(temp1!=temp2)
					{
						array_obj_customer_xtra[j]=new ClosingBalObject();
						array_obj_customer_xtra[j].setPgAcNo(temp1);
						System.out.println("customer["+j+"]="+array_obj_customer_xtra[j].getPgAcNo());
						j++;
						
						System.out.println("1......."+j);
					}
				}
				rs5.previous();
			}
			
			rs5.beforeFirst();
			
			s6=conn.createStatement();
			s6a=conn.createStatement();
			s7=conn.createStatement();
			
			for(int i=0;i<array_obj_customer_xtra.length;i++)
			{
				double_credit=0;
				double_debit=0;
				double_interest=0;
				double_tran_amt=0;
				double_cl_bal=0;
				double_open_bal=0;
				rs6=s6.executeQuery("Select max(trn_date) as trn_date from PygmyTransaction where ac_no="+array_obj_customer_xtra[i].getPgAcNo()+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) < '"+string_fdate+"' ");
				while(rs6.next())
				{
					string_temp_date=rs6.getString("trn_date");
					System.out.println("Trn_Dt="+string_temp_date);
				}
				
				rs6a=s6a.executeQuery("Select cl_bal from PygmyTransaction where ac_no="+array_obj_customer_xtra[i].getPgAcNo()+" and trn_date='"+string_temp_date+"' ");
				while(rs6a.next())
				{
					array_obj_customer_xtra[i].setOpenBal(rs6a.getDouble("cl_bal"));
					array_obj_customer_xtra[i].setClBal(rs6a.getDouble("cl_bal"));
				}
				
				array_obj_customer_xtra[i].setInterest(0.0);
				array_obj_customer_xtra[i].setCreditAmt(0.0);
				array_obj_customer_xtra[i].setDebitAmt(0.0);
				
				rs7=s7.executeQuery("Select concat(IFNULL(fname,' '),'  ',IFNULL(mname,'  '),'  ',IFNULL(lname,'  '),'  ') as name,agt_no from Customermaster cm,PygmyMaster pm where ac_no="+array_obj_customer_xtra[i].getPgAcNo()+" and cm.cid=pm.cid");
				while(rs7.next())
				{
					array_obj_customer_xtra[i].setName(rs7.getString("name"));
					array_obj_customer_xtra[i].setAgentNo(rs7.getInt("agt_no"));
				}
				System.out.println("Customer["+i+"] PgNo= "+array_obj_customer_xtra[i].getPgAcNo());
				System.out.println("Customer["+i+"] PgAgentNo= "+array_obj_customer_xtra[i].getAgentNo());
				System.out.println("Customer["+i+"] Name= "+array_obj_customer_xtra[i].getName());
				System.out.println("Customer["+i+"] Open Bal= "+array_obj_customer_xtra[i].getOpenBal());
				System.out.println("Customer["+i+"] Credit  = "+array_obj_customer_xtra[i].getCreditAmt());
				System.out.println("Customer["+i+"] Debit ="+array_obj_customer_xtra[i].getDebitAmt());
				System.out.println("Customer["+i+"] Closing Bal= "+array_obj_customer_xtra[i].getClBal());
				System.out.println("Customer["+i+"] Interest ="+array_obj_customer_xtra[i].getInterest());
				
				System.out.println(" Obj 1---"+array_obj_customer.length);
				System.out.println(" Obj 2---"+array_obj_customer_xtra.length);
				
				TreeMap map=new TreeMap();
				for(int m=0;i<array_obj_customer.length;m++)
				{
				//	System.out.println(i+"--- "+array_obj_customer[i]);
					map.put(String.valueOf(array_obj_customer[m].getAcNo()),array_obj_customer[m]);
				}
				
				for(int n=0;i<array_obj_customer_xtra.length;n++)
				{
					System.out.println("account no="+array_obj_customer_xtra[n].getAcNo());
					map.put(String.valueOf(array_obj_customer_xtra[n].getAcNo()),array_obj_customer_xtra[n]);
				}
				
				Set s=map.keySet();
				Iterator it=s.iterator();
				clos_new=new ClosingBalObject[map.size()];
				int k=0;
				while(it.hasNext())
				{
					Object obj=it.next();
					System.out.println("key No= "+obj);
					clos_new[i]=(ClosingBalObject)map.get(obj);
					k++;
				}
				
				for(int m=0;m<clos_new.length;m++)
				{
					System.out.println("Ac No= "+clos_new[m].getAcNo());
				}
			}
			
		}catch(Exception e3){e3.printStackTrace();}
		
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return clos_new;
	}
	
	/**
	 * Generates report for Term Deposit Accounts between two dates passed
	 * from the client 
	 */  
	//	 Method added by Sanjeet on 05/05/2006
	public ClosingBalObject[] getReportTDSum(String string_modcode,String string_date) throws RemoteException,SQLException
	{
		double double_dep_amt=0,double_int_paid=0,double_dep_paid=0,rd_bal=0;
		double double_int_amt=0;
		int int_ac_type,int_ac_no;
		int j=0,i=0;
		int int_first_record=0;
		int int_no_records=0;
		
		ClosingBalObject[] array_obj_customer =null;
		
		Connection conn=null;
		Statement s1=null;
		ResultSet rs1=null;
		
		try
		{
			conn=getConnection();
			s1=conn.createStatement();
			//rs1=s1.executeQuery("select distinct ac_type,ac_no from DepositMaster where ac_type='"+string_modcode+"' and (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))>'"+Validations.convertYMD(string_date)+"' or close_date is null) and (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1, (locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1))<='"+Validations.convertYMD(string_date)+"') order by ac_no");
			s1.executeUpdate("drop table if exists  fd_values");
			s1.executeUpdate("create temporary table fd_values select distinct ac_type,ac_no from DepositMaster where ac_type='"+string_modcode+"' and (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))>'"+Validations.convertYMD(string_date)+"' or close_date is null) and (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1, (locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1))<='"+Validations.convertYMD(string_date)+"') order by ac_no");
			rs1=s1.executeQuery("select distinct td.ac_type,td.ac_no from fd_values fd , DepositTransaction as td where fd.ac_type =+ td.ac_type and fd.ac_no =+ td.ac_no and (concat(right(td.trn_date,4),'-',mid(td.trn_date,locate('/',td.trn_date)+1, (locate('/',td.trn_date,4)-locate('/',td.trn_date)-1)),'-',left(td.trn_date,locate('/',td.trn_date)-1)) <='"+Validations.convertYMD(string_date)+"' )");
			rs1.last(); 
			if(rs1.getRow()==0)
			    throw new RecordsNotFoundException();
			array_obj_customer=new ClosingBalObject[rs1.getRow()];
			rs1.beforeFirst();
			while( rs1.next() )
			{
				array_obj_customer[j]=new ClosingBalObject();				
				array_obj_customer[j].setAcNo( rs1.getInt("ac_no") );
				j++;
			}
			
			Statement s2=conn.createStatement();
			ResultSet rs2=null;
			for(i=0;i<array_obj_customer.length;i++)
			{
				double_dep_amt=0;	
				double_dep_paid=0;
				double_int_amt=0;	
				double_int_paid=0;
				
				
				rs2=s2.executeQuery("Select sum(dep_amt) as dep_amt from DepositTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and cd_ind in ('C','D') and trn_type='D' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(string_date)+"' ");
				if(rs2.next()){
					double_dep_amt=rs2.getDouble("dep_amt");
				}
				
				rs2=s2.executeQuery("Select sum(dep_paid) as dep_paid from DepositTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"'  and cd_ind in ('C','D') and trn_type='P'  and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(string_date)+"' ");
				if(rs2.next()){
					double_dep_paid=rs2.getDouble("dep_paid");
				}
				
				array_obj_customer[i].setOpenBal(double_dep_amt-double_dep_paid);
				
				array_obj_customer[i].setNetClBal(array_obj_customer[i].getOpenBal());
				
				rs2=s2.executeQuery("Select sum(int_amt) as int_amt from DepositTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and cd_ind in ('C','D') and trn_type='I' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(string_date)+"' ");
				if(rs2.next()){
					double_int_amt=rs2.getDouble("int_amt");
				}
				
				rs2=s2.executeQuery("Select sum(int_paid) as int_paid from DepositTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"'  and cd_ind in ('C','D') and trn_type='P'  and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(string_date)+"' ");
				if(rs2.next()){
					double_int_paid=rs2.getDouble("int_paid");
				}
				
				array_obj_customer[i].setIntOpenBal(double_int_amt-double_int_paid);
				
				if(string_modcode.startsWith("1003")){
					array_obj_customer[i].setClBal(array_obj_customer[i].getNetClBal());
				}
				else{
					array_obj_customer[i].setClBal(array_obj_customer[i].getNetClBal()+array_obj_customer[i].getIntOpenBal());
				}
				
			}
			
		}catch(Exception e2){e2.printStackTrace();}
		
		finally
		{
			conn.close();
		}
		
		return array_obj_customer;
	}
	
	/**
	 * Generates report for Loan Accounts between two dates passed
	 * from the client 
	 */  
	// Method added by Murugesh on 04/04/2006
	public ClosingBalObject[] getReportLn(String ac_type,String from_date, String to_date) 
	{
		Connection conn=null;
		Statement stmt=null,stmt_individual=null;
		ResultSet rs=null,rs_individual=null;
		ClosingBalObject close_object[]=null;
		try{
			
			conn=getConnection();
			stmt=conn.createStatement();
			stmt_individual=conn.createStatement();

					
			
			
			rs=stmt.executeQuery("select distinct ac_type,ac_no,concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name from LoanMaster as lm,CustomerMaster as cm  where ac_type ='"+ac_type+"' and concat(right(sanc_date,4),'-',mid(sanc_date,locate('/',sanc_date)+1, (locate('/',sanc_date,4)-locate('/',sanc_date)-1)),'-',left(sanc_date,locate('/',sanc_date)-1))<='"+Validations.convertYMD(to_date)+"' and (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))>='"+Validations.convertYMD(from_date)+"' or close_date is null) and lm.ve_tml is not null and loan_sanc like 'Y' and sanc_ver like 'Y' and lm.cid=cm.cid order by ac_type,ac_no");
			rs.last();
			if(rs.getRow()==0)
				return null;
			else
				close_object =  new ClosingBalObject[rs.getRow()];
			
			
			int row=0;
			rs.beforeFirst();
			while(rs.next()){
				close_object[row] = new ClosingBalObject();
				close_object[row].setAcNo(rs.getInt("ac_no"));
				close_object[row].setName(rs.getString("name"));
				close_object[row].setOpenBal(loanremote.getCurrentDayClosingBalance(rs.getString("ac_type"),rs.getInt("ac_no"),Validations.addDays(from_date,-1)));
				close_object[row].setClBal(loanremote.getCurrentDayClosingBalance(rs.getString("ac_type"),rs.getInt("ac_no"),to_date));
				close_object[row].setIntDate(loanremote.getIntUpToDateOnCurrentDate(rs.getString("ac_type"),rs.getInt("ac_no"),to_date));
				
				rs_individual = stmt_individual.executeQuery("select ac_type,ac_no,trn_type,sum(trn_amt) as trn_amt,sum(pr_amt) as pr_amt,sum(int_amt) as int_amt,sum(penal_amt) as penal_amt,sum(other_amt) as other_amt from LoanTransaction  where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type  not like '%S%' and ve_tml is not null and  concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(from_date)+"' and '"+Validations.convertYMD(to_date)+"'  group by ac_type,ac_no,trn_type");
				while(rs_individual.next()){
					if(rs_individual.getString("trn_type").equalsIgnoreCase("D"))
						close_object[row].setCreditAmt(rs_individual.getDouble("trn_amt"));
					
					if(rs_individual.getString("trn_type").equalsIgnoreCase("O"))
						close_object[row].setOtherAmt(rs_individual.getDouble("trn_amt"));
					
					if(rs_individual.getString("trn_type").equalsIgnoreCase("R")){
						close_object[row].setDebitAmt(rs_individual.getDouble("pr_amt"));
						close_object[row].setIntRcd(rs_individual.getDouble("int_amt"));
						close_object[row].setPIntRcd(rs_individual.getDouble("penal_amt"));
						close_object[row].setOtherRecoveryAmt(rs_individual.getDouble("other_amt"));
					}
				}
				row++;
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally	{
			try{
				conn.close();
			}catch(Exception exe){exe.printStackTrace();}
		}
		return close_object;
	}
	
	/**
	 * Generates report for Loans Aginst Deposit Accounts between two dates passed
	 * from the client 
	 */ 
	
	public ClosingBalObject[] getReportLnD(String string_modcode,String string_fromdate,String string_todate)
	{
		double double_dd=0,double_dr=0;
		
		int int_no_records=0,int_no_records_xtra=0,int_first_record=0,i=0;
		String string_temp_date=null,int_ac_type=null;
		ClosingBalObject[] array_obj_customer =null;
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null,rs1=null;
		try
		{
		conn=getConnection();	
		stmt=conn.createStatement();
		rs1=stmt.executeQuery("select distinct ac_type,ac_no from LoanMaster where ac_type ='"+string_modcode+"' and concat(right(sanc_date,4),'-',mid(sanc_date,locate('/',sanc_date)+1, (locate('/',sanc_date,4)-locate('/',sanc_date)-1)),'-',left(sanc_date,locate('/',sanc_date)-1))<='"+Validations.convertYMD(string_todate)+"' and (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))>='"+Validations.convertYMD(string_fromdate)+"' or close_date is null) and ve_tml is not null order by ac_type,ac_no");
		rs1.last();
		if(rs1.getRow()==0)
			return null;
		else
			array_obj_customer =  new ClosingBalObject[rs1.getRow()];
		
		rs1.beforeFirst();
		while(rs1.next()){
			array_obj_customer[i] = new ClosingBalObject();
			array_obj_customer[i].setAcNo(rs1.getInt("ac_no"));
			//array_obj_customer[i].setName(rs.getString("name"));
			int_ac_type=String.valueOf(rs1.getString("ac_type"));
			
			
			i++;
		}
		
			Statement s2=conn.createStatement();
						
			for(i=0;i<array_obj_customer.length;i++)
			{
				double_dd=0;
				double_dr=0;
				rs=s2.executeQuery("Select max(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) ) as trn_date from LoanTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ((cd_ind='C' and trn_type='R') || (cd_ind='D' and trn_type='D')) and ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <'"+Validations.convertYMD(string_fromdate)+"' ");
				if(rs.next()){
					string_temp_date=rs.getString("trn_date");
				}
				
				rs=s2.executeQuery("Select pr_bal from LoanTransaction where ac_type='"+int_ac_type+"' and ac_no="+array_obj_customer[i].getAcNo()+"  and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))='"+string_temp_date+"' ");
				if(rs.next()){
					array_obj_customer[i].setOpenBal(rs.getDouble("pr_bal"));
				}
				rs=s2.executeQuery("Select sum(trn_amt) as disp_amt from LoanTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and cd_ind='D' and trn_type='D' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"'  ");
				if(rs.next()){
					double_dd=rs.getDouble("disp_amt");
				}
				rs=s2.executeQuery("Select sum(pr_amt) as disp_amt from LoanTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and cd_ind='D' and trn_type='R' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"'  ");
				if(rs.next()){
					double_dr=rs.getDouble("disp_amt");
				}
				array_obj_customer[i].setCreditAmt(double_dd-double_dr);
				
				rs=s2.executeQuery("Select sum(pr_amt) as recy from LoanTransaction where ac_type='"+int_ac_type+"' and ac_no="+array_obj_customer[i].getAcNo()+" and cd_ind='C' and trn_type='R' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"'  ");
				if(rs.next()){
					array_obj_customer[i].setDebitAmt(rs.getDouble("recy"));
				}
				
				rs=s2.executeQuery("Select sum(int_amt) as int_recd from LoanTransaction where ac_type='"+int_ac_type+"' and  ac_no="+array_obj_customer[i].getAcNo()+" and cd_ind='C' and trn_type='R' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"' ");
				if(rs.next())
				{
					array_obj_customer[i].setIntRcd(rs.getDouble("int_recd"));
					
				}
				array_obj_customer[i].setClBal(array_obj_customer[i].getOpenBal()+array_obj_customer[i].getCreditAmt()-array_obj_customer[i].getDebitAmt());
				rs=s2.executeQuery("Select concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name from CustomerMaster cm,LoanMaster dm where ac_type='"+int_ac_type+"' and ac_no="+array_obj_customer[i].getAcNo()+" and cm.cid=dm.cid");
				if(rs.next()){
					array_obj_customer[i].setName(rs.getString("name"));
				}
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
			conn.close();
			}catch(Exception e1){e1.printStackTrace();}
		}
			return array_obj_customer;
	}
	
	//------Closing Balance Summary --------------------------------------------------------------
	
	/**
	 * Generates report for SB,CA,CABk,CABr,OD,CC Accounts for a particular date passed
	 * from the client 
	 */  
	//Changed by Deepa for CA/CABk/CABr/OD/CC
	public ClosingBalObject[] getReportSBSum(String string_modcode,String string_date) throws RemoteException,SQLException
	{
		ClosingBalObject[] array_obj_customer=null;
		String string_this_date=Validations.convertYMD(string_date);
		String string_temp_date=null;
		Connection conn=null;
		
		int i=0,j=0;
		try
		{
			conn=getConnection();
			String qry1="";
			if(string_modcode.startsWith("1002")||string_modcode.startsWith("1007")||string_modcode.startsWith("1017")||string_modcode.startsWith("1018"))
				qry1="Select distinct ac_no from AccountMaster where ac_type='"+string_modcode+"' and (concat(right(ac_opendate,4),'-',mid(ac_opendate,locate('/',ac_opendate)+1, (locate('/',ac_opendate,4)-locate('/',ac_opendate)-1)),'-',left(ac_opendate,locate('/',ac_opendate)-1)) <= '"+string_this_date+"' or concat(right(ac_closedate,4),'-',mid(ac_closedate,locate('/',ac_closedate)+1, (locate('/',ac_closedate,4)-locate('/',ac_closedate)-1)),'-',left(ac_closedate,locate('/',ac_closedate)-1)) <= '"+string_this_date+"') order by ac_no";
			else if(string_modcode.startsWith("1014")||string_modcode.startsWith("1015"))
			    qry1="Select distinct ac_no from ODCCMaster where ac_type='"+string_modcode+"' and (concat(right(ac_opendate,4),'-',mid(ac_opendate,locate('/',ac_opendate)+1, (locate('/',ac_opendate,4)-locate('/',ac_opendate)-1)),'-',left(ac_opendate,locate('/',ac_opendate)-1)) <= '"+string_this_date+"' or concat(right(ac_closedate,4),'-',mid(ac_closedate,locate('/',ac_closedate)+1, (locate('/',ac_closedate,4)-locate('/',ac_closedate)-1)),'-',left(ac_closedate,locate('/',ac_closedate)-1)) <= '"+string_this_date+"') order by ac_no";
			Statement s1=conn.createStatement();
			ResultSet rs1=s1.executeQuery(qry1);
			
			if(rs1.next())
			{
				rs1.last();
				array_obj_customer=new ClosingBalObject[rs1.getRow()];
				rs1.beforeFirst();
			}
			
			while(rs1.next())
			{
				int temp1=rs1.getInt(1);
				
				if(rs1.isLast())
				{
					array_obj_customer[j]=new ClosingBalObject();
					array_obj_customer[j].setAcNo(temp1);
				}
				if(rs1.next())
				{
					int temp2=rs1.getInt(1);
					if(temp1!=temp2)
					{
						array_obj_customer[j]=new ClosingBalObject();
						array_obj_customer[j].setAcNo(temp1);
						j++;
					}
				}
				rs1.previous();
			}
		}catch(Exception e1){e1.printStackTrace();}
		try		
		{
			if(array_obj_customer!=null)
			{
				Statement s2=conn.createStatement();
				Statement s3=conn.createStatement();
				
				for(i=0;i<array_obj_customer.length;i++)
				{
					String qry2="";
					if(string_modcode.startsWith("1002")||string_modcode.startsWith("1007")||string_modcode.startsWith("1017")||string_modcode.startsWith("1018"))
						qry2="Select max(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))) as date from AccountTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+string_this_date+"' ";
					else if(string_modcode.startsWith("1014")||string_modcode.startsWith("1015"))
					    qry2="Select max(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))) as date from ODCCTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+string_this_date+"' ";					    
					ResultSet rs2=s2.executeQuery(qry2);
					while(rs2.next())
					{
						string_temp_date=rs2.getString("date");
					}
					
					String qry3="";
					if(string_modcode.startsWith("1002")||string_modcode.startsWith("1007")||string_modcode.startsWith("1017")||string_modcode.startsWith("1018"))
					    qry3="Select cl_bal from AccountTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))='"+string_temp_date+"' ";
					else if(string_modcode.startsWith("1014")||string_modcode.startsWith("1015"))
					    qry3="Select cl_bal from ODCCTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))='"+string_temp_date+"' ";
					ResultSet rs3=s3.executeQuery(qry3);
					while(rs3.next())
					{
						array_obj_customer[i].setClBal(rs3.getDouble("cl_bal"));
						
					}
				}
			}
			
		}catch(Exception e2){e2.printStackTrace();}
		finally
		{
			conn.close();
		}
		
		return array_obj_customer;
	}
	/**
	 * Generates report for Pygmy Accounts for a particular date passed
	 * from the client 
	 */  
	public ClosingBalObject[] getReportPDSum(String string_modcode,String string_date) throws RemoteException,SQLException
	{
		ClosingBalObject[] array_obj_customer=null;
		String string_this_date=Validations.convertYMD(string_date);
		String string_temp_date=null;
		
		int i=0,j=0;
		System.out.println("Inside");
		Connection conn=null;
		
		try
		{
			conn=getConnection();
			//String qry1="Select distinct ac_no from PygmyMaster where ac_type='"+string_modcode+"' and (concat(right(open_date,4),'-',mid(open_date,locate('/',open_date)+1, (locate('/',open_date,4)-locate('/',open_date)-1)),'-',left(open_date,locate('/',open_date)-1)) <= '"+string_this_date+"' or concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) <= '"+string_this_date+"') order by ac_no ";
			String qry1="select distinct ac_no from PygmyMaster where status='O' and close_date is null and ac_no not in (select distinct ac_no from PygmyMaster where concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))<='"+string_this_date+"') union select distinct ac_no from PygmyMaster where status='0' and close_date is null and ac_no not in (select distinct ac_no from PygmyMaster where concat(right(open_date,4),'-',mid(open_date,locate('/',open_date)+1, (locate('/',open_date,4)-locate('/',open_date)-1)),'-',left(open_date,locate('/',open_date)-1))>'"+string_this_date+"') order by ac_no";
			Statement s1=conn.createStatement();
			ResultSet rs1=s1.executeQuery(qry1);
			
			if(rs1.next())
			{
				rs1.last();
				array_obj_customer=new ClosingBalObject[rs1.getRow()];
				rs1.beforeFirst();
			}
			
			while(rs1.next())
			{
				System.out.println("Just inside while2");
				int temp1=rs1.getInt(1);
				
				if(rs1.isLast())
				{
					array_obj_customer[j]=new ClosingBalObject();
					array_obj_customer[j].setAcNo(temp1);
				}
				
				if(rs1.next())
				{
					int temp2=rs1.getInt(1);
					if(temp1!=temp2)
					{
						array_obj_customer[j]=new ClosingBalObject();
						array_obj_customer[j].setAcNo(temp1);
						System.out.println("customer["+j+"]="+array_obj_customer[j].getAcNo());
						j++;
						
						System.out.println("1......."+j);
					}
				}
				rs1.previous();
			}
		}catch(Exception e1){e1.printStackTrace();}
		
		try
		{
			if(array_obj_customer!=null)
			{
				Statement s2=conn.createStatement();
				Statement s3=conn.createStatement();
				
				for(i=0;i<array_obj_customer.length;i++)
				{
					String qry2="Select max(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))) as date from PygmyTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+string_this_date+"' ";
					ResultSet rs2=s2.executeQuery(qry2);
					while(rs2.next())
					{
						string_temp_date=rs2.getString("date");
						
					}
					
					//String qry3="Select cl_bal from PygmyTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))='"+string_temp_date+"' ";
					String qry3="Select cl_bal from PygmyTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+string_temp_date+"' order by trn_seq desc limit 1";
					ResultSet rs3=s3.executeQuery(qry3);
					while(rs3.next())
					{
						array_obj_customer[i].setClBal(rs3.getDouble("cl_bal"));
						System.out.println("Clo Bal= "+array_obj_customer[j].getClBal());
						
					}
				}
			}
			
		}catch(Exception e2){e2.printStackTrace();}
		finally
		{
			conn.close();
		}
		System.out.println("PD NO OF ACCOUNTS==="+array_obj_customer.length);
		return array_obj_customer;
	}
	
	/**
	 * Generates report for Term Deposit Accounts for a particular date passed
	 * from the client 
	 */  
	public ClosingBalObject[] getReportTD(String string_modcode,String string_fromdate, String string_todate) throws RemoteException,SQLException
	{
		double double_dep_amt=0,double_int_paid=0,double_dep_paid=0,rd_bal=0;
		double double_int_amt=0;
		int int_ac_type,int_ac_no;
		int j=0,i=0;
		int int_first_record=0;
		int int_no_records=0;
		
		ClosingBalObject[] array_obj_customer =null;
		
		Connection conn=null;
		Statement s1=null;
		ResultSet rs1=null,rs=null;
		
		try
		{
			conn=getConnection();
			s1=conn.createStatement();
			//rs1=s1.executeQuery("select distinct ac_type,ac_no from DepositMaster where ac_type='"+string_modcode+"' and (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))>'"+Validations.convertYMD(string_fromdate)+"' or close_date is null) and (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1, (locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1))<='"+Validations.convertYMD(string_todate)+"') order by ac_no");
			s1.executeUpdate("drop table if exists  fd_values");
			s1.executeUpdate("create temporary table fd_values select distinct ac_type,ac_no from DepositMaster where ac_type='"+string_modcode+"' and (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))>='"+Validations.convertYMD(string_fromdate)+"' or close_date is null) and (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1, (locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1))<='"+Validations.convertYMD(string_todate)+"') order by ac_no limit 20");
			rs1=s1.executeQuery("select distinct td.ac_type,td.ac_no from fd_values fd , DepositTransaction as td where fd.ac_type =+ td.ac_type and fd.ac_no =+ td.ac_no and (concat(right(td.trn_date,4),'-',mid(td.trn_date,locate('/',td.trn_date)+1, (locate('/',td.trn_date,4)-locate('/',td.trn_date)-1)),'-',left(td.trn_date,locate('/',td.trn_date)-1)) <='"+Validations.convertYMD(string_todate)+"' ) limit 20");
			
			rs1.last(); 
			if(rs1.getRow()==0)
			    throw new RecordsNotFoundException();
			array_obj_customer=new ClosingBalObject[rs1.getRow()];
			rs1.beforeFirst();
			while( rs1.next() )
			{
				array_obj_customer[j]=new ClosingBalObject();				
				array_obj_customer[j].setAcNo( rs1.getInt("ac_no") );
				j++;
			}
			
			Statement s2=conn.createStatement();
			
			for(i=0;i<array_obj_customer.length;i++)
			{
				double_dep_amt=0;	
				double_dep_paid=0;
				double_int_amt=0;	
				double_int_paid=0;
				
				rs=s2.executeQuery("Select sum(dep_amt) as dep_amt from DepositTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and cd_ind in ('C','D') and trn_type='D' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<'"+Validations.convertYMD(string_fromdate)+"' ");
				if(rs.next()){
					double_dep_amt=rs.getDouble("dep_amt");
				}
				
				rs=s2.executeQuery("Select sum(dep_paid) as dep_paid from DepositTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"'  and cd_ind in ('C','D') and trn_type='P' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<'"+Validations.convertYMD(string_fromdate)+"' ");
				if(rs.next()){
					double_dep_paid=rs.getDouble("dep_paid");
				}
								
				array_obj_customer[i].setOpenBal(double_dep_amt-double_dep_paid);
				
				rs=s2.executeQuery("Select sum(dep_amt) as dep_amt from DepositTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"'  and cd_ind in ('C','D') and trn_type='D'  and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"' ");
				if(rs.next()){
					array_obj_customer[i].setCreditAmt(rs.getDouble("dep_amt"));
				}
				
				rs=s2.executeQuery("Select sum(dep_paid) as dep_paid from DepositTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"'  and cd_ind in ('C','D') and trn_type='P'  and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"' ");
				if(rs.next()){
					array_obj_customer[i].setDebitAmt(rs.getDouble("dep_paid"));
				}
				array_obj_customer[i].setClBal(array_obj_customer[i].getOpenBal()+array_obj_customer[i].getCreditAmt()-array_obj_customer[i].getDebitAmt());
				
				rs=s2.executeQuery("Select sum(int_amt) as int_amt from DepositTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and cd_ind in ('C','D') and trn_type='I' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<'"+Validations.convertYMD(string_fromdate)+"' ");
				if(rs.next()){
					double_int_amt=rs.getDouble("int_amt");
				}
				
				rs=s2.executeQuery("Select sum(int_paid) as int_paid from DepositTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"'  and cd_ind in ('C','D') and trn_type='P'  and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<'"+Validations.convertYMD(string_fromdate)+"' ");
				if(rs.next()){
					double_int_paid=rs.getDouble("int_paid");
				}
				array_obj_customer[i].setIntOpenBal(double_int_amt-double_int_paid);
				
				rs=s2.executeQuery("Select sum(int_amt) as int_amt from DepositTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"'  and cd_ind in ('C','D') and trn_type='I'  and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"' ");
				if(rs.next()){
					array_obj_customer[i].setIntAcd(rs.getDouble("int_amt"));
				}
				
				rs=s2.executeQuery("Select sum(int_paid) as int_paid from DepositTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"'  and cd_ind in ('C','D') and trn_type='P'  and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_fromdate)+"' and '"+Validations.convertYMD(string_todate)+"' ");
				if(rs.next()){
					array_obj_customer[i].setIntPaid(rs.getDouble("int_paid"));
				}
				array_obj_customer[i].setIntClBal(array_obj_customer[i].getIntOpenBal()+array_obj_customer[i].getIntAcd()-array_obj_customer[i].getIntPaid());
				array_obj_customer[i].setNetClBal(array_obj_customer[i].getClBal()+array_obj_customer[i].getIntClBal());
				
				rs=s2.executeQuery("Select concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name from CustomerMaster cm,DepositMaster dm where ac_no="+array_obj_customer[i].getAcNo()+" and cm.cid=dm.cid");
				if(rs.next()){
					array_obj_customer[i].setName(rs.getString("name"));
				}
			}
			
		}catch(Exception e2){e2.printStackTrace();}
		
		finally
		{
			conn.close();
		}
		
		return array_obj_customer;
	}
	
	/*public ClosingBalObject[] getReportFDSum(String string_modcode,String string_date) throws RemoteException,SQLException
	{
		ClosingBalObject[] array_obj_customer=null;
		array_obj_customer = getReportFD(string_modcode,string_date,string_date);
		String string_this_date=Validations.convertYMD(string_date);
		String string_temp_date=null;
		double rd_bal=0;
				
		int i=0,j=0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			String qry1="Select distinct ac_no from DepositMaster where ac_type='"+string_modcode+"' and concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1, (locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1)) <= '"+string_this_date+"' and (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) > '"+string_this_date+"' or close_date is null) order by ac_no";
			Statement s1=conn.createStatement();
			ResultSet rs1=s1.executeQuery(qry1);
			
			rs1.last(); 
			if(rs1.getRow()==0)
			    throw new RecordsNotFoundException();
			array_obj_customer=new ClosingBalObject[rs1.getRow()];
			rs1.beforeFirst();
			while( rs1.next() )
			{
				array_obj_customer[j]=new ClosingBalObject();				
				array_obj_customer[j].setAcNo( rs1.getInt("ac_no") );
				j++;
			}
			if(rs1.next())
			{
				rs1.last();
				array_obj_customer=new ClosingBalObject[rs1.getRow()];
				rs1.beforeFirst();
			}
			
			while(rs1.next())
			{
				int temp1=rs1.getInt(1);
				
				if(rs1.isLast())
				{
					array_obj_customer[j]=new ClosingBalObject();
					array_obj_customer[j].setAcNo(temp1);
					
				}
				
				if(rs1.next())
				{
					int temp2=rs1.getInt(1);
					if(temp1!=temp2)
					{
						array_obj_customer[j]=new ClosingBalObject();
						array_obj_customer[j].setAcNo(temp1);
						System.out.println("Ac No= "+array_obj_customer[i].getAcNo());
						j++;
					}
				}
				rs1.previous();
			}
		}catch(Exception e1){e1.printStackTrace();}
		
		try
		{
			if(array_obj_customer!=null)
			{
			
				Statement s2=conn.createStatement();
				Statement s3=conn.createStatement();
				
				for(i=0;i<array_obj_customer.length;i++)
				{
					String qry2="Select max(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))) as date from DepositTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+string_this_date+"' ";
					ResultSet rs2=s2.executeQuery(qry2);
					while(rs2.next())
					{
						string_temp_date=rs2.getString("date");
					}
					
					String qry3="Select dep_amt from DepositTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))='"+string_temp_date+"' ";
					ResultSet rs3=s3.executeQuery(qry3);
					while(rs3.next())
					{
						rd_bal=rs3.getDouble("dep_amt");
					}
					array_obj_customer[i].setClBal(rd_bal);
				}
			
			
		}catch(Exception e2){e2.printStackTrace();}
		finally
		{
			conn.close();
		}
		return array_obj_customer;
	}*/

	// Method changed by Murugesh on 06/09/2006
	public ClosingBalObject[] getReportLnSum(String ac_type,String date) throws RemoteException,SQLException
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		ClosingBalObject[] return_values=null;
		int count=0;
		try
		{
			loanServer.LoanHome loanhome=(loanServer.LoanHome)HomeFactory.getFactory().lookUpHome("LOANSWEB");
			LoanRemote loanremote = loanhome.create();	
			conn=getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select distinct ac_no from LoanMaster where ac_type='"+ac_type+"' and  concat(right(sanc_date,4),'-',mid(sanc_date,locate('/',sanc_date)+1, (locate('/',sanc_date,4)-locate('/',sanc_date)-1)),'-',left(sanc_date,locate('/',sanc_date)-1)) <= '"+Validations.convertYMD(date)+"' and loan_sanc='Y' and (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)))>'"+Validations.convertYMD(date)+"' or close_date is null)");
			if(rs.last())
				return_values = new ClosingBalObject[rs.getRow()];
			rs.beforeFirst();
			while(rs.next()){
				return_values[count] = new ClosingBalObject();
				return_values[count].setAc_type(ac_type);
				return_values[count].setAcNo(rs.getInt("ac_no"));
				return_values[count].setClBal(loanremote.getCurrentDayClosingBalance(ac_type,rs.getInt("ac_no"),date));
				count++;
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
			conn.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		return return_values;
	}
	
	/**
	 * Generates report for Loan Against Deposit Accounts for a particular date passed
	 * from the client 
	 */  
	public ClosingBalObject[] getReportLnDSum(String string_modcode,String string_date) throws RemoteException,SQLException
	{
		ClosingBalObject[] array_obj_customer=null;
		String string_this_date=Validations.convertYMD(string_date);
		String string_temp_date=null;
		Connection conn=null;
		int i=0,j=0;
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement();
			ResultSet rs1=s1.executeQuery("Select distinct ac_no from LoanMaster where ac_type='"+string_modcode+"' and concat(right(sanc_date,4),'-',mid(sanc_date,locate('/',sanc_date)+1, (locate('/',sanc_date,4)-locate('/',sanc_date)-1)),'-',left(sanc_date,locate('/',sanc_date)-1)) <= '"+string_this_date+"' and (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) > '"+string_this_date+"' or close_date is null) order by ac_no ");
			
			if(rs1.next())
			{
				rs1.last();
				array_obj_customer=new ClosingBalObject[rs1.getRow()];
				rs1.beforeFirst();
			}
			
			while(rs1.next())
			{
				int temp1=rs1.getInt(1);
				if(rs1.isLast())
				{
					array_obj_customer[j]=new ClosingBalObject();
					array_obj_customer[j].setAcNo(temp1);
				}
				
				if(rs1.next())
				{
					int temp2=rs1.getInt(1);
					if(temp1!=temp2)
					{
						array_obj_customer[j]=new ClosingBalObject();
						array_obj_customer[j].setAcNo(temp1);
						j++;
					}
				}
				rs1.previous();
			}
		}catch(Exception e1){e1.printStackTrace();}
		
		try
		{
			if(array_obj_customer!=null)
			{
				Statement s2=conn.createStatement();
				Statement s3=conn.createStatement();
				
				for(i=0;i<array_obj_customer.length;i++)
				{
					String qry2="Select max(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))) as date from LoanTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and ((cd_ind='C' and trn_type='R') || (cd_ind='D' and trn_type='D')) and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <='"+string_this_date+"' ";
					ResultSet rs2=s2.executeQuery(qry2);
					while(rs2.next())
					{
						string_temp_date=rs2.getString("date");
					}
					String qry3="Select pr_bal from LoanTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))='"+string_temp_date+"' ";
					ResultSet rs3=s3.executeQuery(qry3);
					while(rs3.next())
					{
						array_obj_customer[i].setClBal(rs3.getDouble("pr_bal"));
					}
				}
			}
			
		}catch(Exception e2){e2.printStackTrace();}
		finally
		{
			conn.close();
		}
		
		return array_obj_customer;
	}
	
	/**
	 * Generates report for Share Accounts for a particular date passed
	 * from the client 
	 */  
	public ClosingBalObject[] getReportSHSum(String string_modcode,String string_date) throws RemoteException,SQLException
	{
		ClosingBalObject[] array_obj_customer=null;
		String string_this_date=Validations.convertYMD(string_date);
		double double_ca=0,double_da=0,double_dw=0,double_cw=0;
		int i=0,j=0;
		Connection conn=null;
		
		try
		{
			conn=getConnection();
			String qry1="Select distinct ac_no from ShareMaster where  ac_type='"+string_modcode+"' and  (concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1, (locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1)) <= '"+string_this_date+"' or concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1, (locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1)) <= '"+string_this_date+"') order by ac_no";
			Statement s1=conn.createStatement();
			ResultSet rs1=s1.executeQuery(qry1);
			
			if(rs1.next())
			{
				rs1.last();
				array_obj_customer=new ClosingBalObject[rs1.getRow()];
				rs1.beforeFirst();
			}
			
			while(rs1.next())
			{
				int temp1=rs1.getInt(1);
				
				if(rs1.isLast())
				{
					array_obj_customer[j]=new ClosingBalObject();
					array_obj_customer[j].setAcNo(temp1);
					
				}
				
				if(rs1.next())
				{
					int temp2=rs1.getInt(1);
					if(temp1!=temp2)
					{
						array_obj_customer[j]=new ClosingBalObject();
						array_obj_customer[j].setAcNo(temp1);
						j++;
						
					}
				}
				rs1.previous();
			}
		}catch(Exception e1){e1.printStackTrace();}
		
		try
		{
			if(array_obj_customer!=null)
			{
				Statement s2=conn.createStatement();
				Statement s3=conn.createStatement();
				Statement s4=conn.createStatement();
				Statement s5=conn.createStatement();
				
				for(i=0;i<array_obj_customer.length;i++)
				{
					String qry2="Select sum(trn_amt) as ca from ShareTransaction where ac_no="+array_obj_customer[i].getAcNo()+"  and ac_type='"+string_modcode+"' and cd_ind='C' and trn_type='A' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <='"+string_this_date+"' ";
					ResultSet rs2=s2.executeQuery(qry2);
					while(rs2.next())
					{
						double_ca=rs2.getDouble("ca");
					}
					
					String qry3="Select sum(trn_amt) as da from ShareTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and cd_ind='D' and trn_type='A' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <='"+string_this_date+"' ";
					ResultSet rs3=s3.executeQuery(qry3);
					while(rs3.next())
					{
						double_da=rs3.getDouble("da");
						
					}
					
					String qry4="Select sum(trn_amt) as dw from ShareTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and cd_ind='D' and trn_type='W' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <='"+string_this_date+"' ";
					ResultSet rs4=s4.executeQuery(qry4);
					while(rs4.next())
					{
						double_dw=rs4.getDouble("dw");
						
					}
					
					String qry5="Select sum(trn_amt) as cw from ShareTransaction where ac_no="+array_obj_customer[i].getAcNo()+" and ac_type='"+string_modcode+"' and cd_ind='C' and trn_type='W' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <='"+string_this_date+"' ";
					ResultSet rs5=s5.executeQuery(qry5);
					while(rs5.next())
					{
						double_cw=rs5.getDouble("cw");
						
					}
					
					array_obj_customer[i].setClBal(double_ca-double_da-double_dw+double_cw);
				}
			}
		}catch(Exception e2){e2.printStackTrace();}
		finally
		{
			conn.close();
		}
		
		return array_obj_customer;
		
	}
	
	/**
	 * Generates report for particular GL Code between two dates passed
	 * from the client
	 */  	
	public ClosingBalObject[] getReqGLReport(String string_glcode,String string_fromdate,String string_todate) throws RemoteException,SQLException
	{
		System.out.println("GL CODE="+string_glcode);
		int j=0;	
		ClosingBalObject[] array_obj_customer=null;
		String string_fdate=Validations.convertYMD(string_fromdate);
		String string_tdate=Validations.convertYMD(string_todate);
		int int_gl_code=Integer.parseInt(string_glcode);
		
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement();
			Statement s22=conn.createStatement();
			Statement s23=conn.createStatement();
			
			String qry1="Select ref_ac_no,ref_ac_type,gl_code,trn_amt,cd_ind,trn_source,ref_tr_seq,trn_date from GLTransaction where gl_code="+int_gl_code+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+string_fdate+"' and  '"+string_tdate+"' order by ref_ac_no ";
			
			ResultSet rs1=s1.executeQuery(qry1);
			
			if(! rs1.next())
				return array_obj_customer;
			rs1.beforeFirst();
			
			if(rs1.next())
			{
				rs1.last();
				array_obj_customer=new ClosingBalObject[rs1.getRow()];
			}
			
			
			rs1.beforeFirst();
			
			while(rs1.next())
			{
				array_obj_customer[j]=new ClosingBalObject();
				
				array_obj_customer[j].setAcNo(rs1.getInt("ref_ac_no"));
				array_obj_customer[j].setRefAcTy(rs1.getString("ref_ac_type"));
				array_obj_customer[j].setGLCode(rs1.getInt("gl_code"));
				//array_obj_customer[j].setName(rs1.getString("name"));
				
				array_obj_customer[j].setCash_Cr(0);
				array_obj_customer[j].setCash_Dr(0);
				array_obj_customer[j].setCheque_Cr(0);
				array_obj_customer[j].setCheque_Dr(0);
				array_obj_customer[j].setTransfer_Cr(0);
				array_obj_customer[j].setTransfer_Dr(0);
				System.out.println("inside while");
				
				int acc_no=rs1.getInt("ref_ac_no");
				String acc_type=rs1.getString("ref_ac_type");
				/*AccountObject object =commonlocal.getAccountName((String)acc_type,Integer.parseInt(acc_no.toString()));*/
				String name =commonlocal.getAccountHolderName(acc_type,acc_no);
				System.out.println("otside the common bean"+name);
				array_obj_customer[j].setName(name);
				
				if(rs1.getString("trn_source").equals("C") && rs1.getString("cd_ind").equals("C") )
				{
					array_obj_customer[j].setCash_Cr(rs1.getDouble("trn_amt"));
				}
				
				if(rs1.getString("trn_source").equals("C") && rs1.getString("cd_ind").equals("D") )
				{
					array_obj_customer[j].setCash_Dr(rs1.getDouble("trn_amt"));
				}
				
				if(rs1.getString("trn_source").equals("G") && rs1.getString("cd_ind").equals("C") )
				{
					array_obj_customer[j].setCheque_Cr(rs1.getDouble("trn_amt"));
				}
				
				if(rs1.getString("trn_source").equals("G") && rs1.getString("cd_ind").equals("D") )
				{
					array_obj_customer[j].setCheque_Dr(rs1.getDouble("trn_amt"));
				}
				
				if(rs1.getString("trn_source").equals("T") && rs1.getString("cd_ind").equals("C") )
				{
					array_obj_customer[j].setTransfer_Cr(rs1.getDouble("trn_amt"));
				}
				
				if(rs1.getString("trn_source").equals("T") && rs1.getString("cd_ind").equals("D") )
				{
					array_obj_customer[j].setTransfer_Dr(rs1.getDouble("trn_amt"));
				}
				
				array_obj_customer[j].setTrnDate(rs1.getString("trn_date"));
				array_obj_customer[j].setTrnTy(rs1.getString("cd_ind"));
				array_obj_customer[j].setTrnSeq(rs1.getString("ref_tr_seq"));
				
				System.out.println("Trn_Amt="+rs1.getDouble("trn_amt"));
								
				j++;	
			

		}
			
			
			j=0;
			
			//try
			//{	
			Statement s2=conn.createStatement();
			Statement s3=conn.createStatement();
			
			String qry2="Select sum(cash_cr) as cash_cr, sum(clr_cr) as clr_cr, sum(trf_cr) as trf_cr,sum(cash_dr) as cash_dr, sum(clr_dr) as clr_dr, sum(trf_dr) as trf_dr from MonthSummary where gl_code="+int_gl_code+" and concat(right(yr_mth,4),'-',mid(yr_mth,locate('/',yr_mth)+1, (locate('/',yr_mth,4)-locate('/',yr_mth)-1)),'-',left(yr_mth,locate('/',yr_mth)-1)) <'"+string_fdate+"' ";
			ResultSet rs2=s2.executeQuery(qry2);
			if(rs2.next())
			{
				double x=rs2.getDouble("cash_cr")+rs2.getDouble("clr_cr")+rs2.getDouble("trf_cr")-(rs2.getDouble("cash_dr")+rs2.getDouble("clr_dr")+rs2.getDouble("trf_dr"));
				array_obj_customer[j].setOpenBal(x);
			}
			
			String qry3="Select sum(cash_cr) as cash_cr, sum(clr_cr) as clr_cr, sum(trf_cr) as trf_cr,sum(cash_dr) as cash_dr, sum(clr_dr) as clr_dr, sum(trf_dr) as trf_dr from MonthSummary where gl_code="+int_gl_code+" and concat(right(yr_mth,4),'-',mid(yr_mth,locate('/',yr_mth)+1, (locate('/',yr_mth,4)-locate('/',yr_mth)-1)),'-',left(yr_mth,locate('/',yr_mth)-1)) between '"+string_fdate+"' and '"+string_tdate+"' ";
			ResultSet rs3=s3.executeQuery(qry3);
			if(rs3.next())
			{
				double y=rs3.getDouble("cash_cr")+rs3.getDouble("clr_cr")+rs3.getDouble("trf_cr")-(rs3.getDouble("cash_dr")+rs3.getDouble("clr_dr")+rs3.getDouble("trf_dr"));
				array_obj_customer[j].setClBal(x-y);
			}
		}catch(Exception e2){e2.printStackTrace();}
		
		finally
		{
			conn.close();
		}
		return array_obj_customer;
	}
	//			--------GL Schedule Report-------------------------------------------------------------------
	
	/**
	 * Generates report for all GL Codes between two dates passed
	 * from the client 
	 */  
	public ClosingBalObject[] getGLReport(String string_fromdate,String string_todate) throws RemoteException,SQLException
	{
		int j=0;	
		ClosingBalObject[] array_obj_customer=null;
		String string_fdate=Validations.convertYMD(string_fromdate);
		String string_tdate=Validations.convertYMD(string_todate);
		Connection conn=null;
		
		try
		{
			System.out.println("..11111111111111111");
			conn= getConnection();
			System.out.println("..222");
			Statement s1=conn.createStatement();
			System.out.println("..222333333333333333");
			String qry1="Select ref_ac_no,ref_ac_type,gl_code,trn_amt,cd_ind,ref_tr_seq,trn_date,trn_mode from GLTransaction where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+string_fdate+"' and  '"+string_tdate+"' order by gl_code ";
			ResultSet rs1=s1.executeQuery(qry1);
			
			if(! rs1.next())
				return array_obj_customer;
			rs1.beforeFirst();
			
			if(rs1.next())
			{
				rs1.last();
				array_obj_customer=new ClosingBalObject[rs1.getRow()];
				System.out.println("NO RECORDS= "+array_obj_customer.length);
			}
			
			rs1.beforeFirst();
			
			while(rs1.next())
			{
				array_obj_customer[j]=new ClosingBalObject();
				
				array_obj_customer[j].setAcNo(rs1.getInt("ref_ac_no"));
				array_obj_customer[j].setRefAcTy(rs1.getString("ref_ac_type"));
				array_obj_customer[j].setGLCode(rs1.getInt("gl_code"));
				
				array_obj_customer[j].setCash_Cr(0);
				array_obj_customer[j].setCash_Dr(0);
				array_obj_customer[j].setCheque_Cr(0);
				array_obj_customer[j].setCheque_Dr(0);
				array_obj_customer[j].setTransfer_Cr(0);
				array_obj_customer[j].setTransfer_Dr(0);
				
				
				if(rs1.getString("trn_mode").equals("C") && rs1.getString("cd_ind").equals("C") )
				{
					array_obj_customer[j].setCash_Cr(rs1.getDouble("trn_amt"));
				}
				
				if(rs1.getString("trn_mode").equals("C") && rs1.getString("cd_ind").equals("D") )
				{
					array_obj_customer[j].setCash_Dr(rs1.getDouble("trn_amt"));
				}
				
				if(rs1.getString("trn_mode").equals("G") && rs1.getString("cd_ind").equals("C") )
				{
					array_obj_customer[j].setCheque_Cr(rs1.getDouble("trn_amt"));
				}
				
				if(rs1.getString("trn_mode").equals("G") && rs1.getString("cd_ind").equals("D") )
				{
					array_obj_customer[j].setCheque_Dr(rs1.getDouble("trn_amt"));
				}
				
				if(rs1.getString("trn_mode").equals("T") && rs1.getString("cd_ind").equals("C") )
				{
					array_obj_customer[j].setTransfer_Cr(rs1.getDouble("trn_amt"));
				}
				
				if(rs1.getString("trn_mode").equals("T") && rs1.getString("cd_ind").equals("D") )
				{
					array_obj_customer[j].setTransfer_Dr(rs1.getDouble("trn_amt"));
				}
				
				array_obj_customer[j].setTrnDate(rs1.getString("trn_date"));
				array_obj_customer[j].setTrnTy(rs1.getString("cd_ind"));
				array_obj_customer[j].setTrnSeq(rs1.getString("ref_tr_seq"));
				
				System.out.println("Trn_Amt="+rs1.getDouble("trn_amt"));
				
				array_obj_customer[j].setName(null);
				j++;
			}
			//	}catch(Exception e1){e1.printStackTrace();}
			j=0;
			//		try
			//	{	
			if(array_obj_customer!=null)
			{
				Statement s2=conn.createStatement();
				Statement s3=conn.createStatement();
				
				String qry2="Select sum(cash_cr) as cash_cr, sum(clr_cr) as clr_cr, sum(trf_cr) as trf_cr,sum(cash_dr) as cash_dr, sum(clr_dr) as clr_dr, sum(trf_dr) as trf_dr from MonthSummary where concat(right(yr_mth,4),'-',mid(yr_mth,locate('/',yr_mth)+1, (locate('/',yr_mth,4)-locate('/',yr_mth)-1)),'-',left(yr_mth,locate('/',yr_mth)-1)) <'"+string_fdate+"' ";
				ResultSet rs2=s2.executeQuery(qry2);
				
				if(rs2.next())
				{
					double x=rs2.getDouble("cash_cr")+rs2.getDouble("clr_cr")+rs2.getDouble("trf_cr")-(rs2.getDouble("cash_dr")+rs2.getDouble("clr_dr")+rs2.getDouble("trf_dr"));
					array_obj_customer[j].setOpenBal(x);
				}
				
				String qry3="Select sum(cash_cr) as cash_cr, sum(clr_cr) as clr_cr, sum(trf_cr) as trf_cr,sum(cash_dr) as cash_dr, sum(clr_dr) as clr_dr, sum(trf_dr) as trf_dr from MonthSummary where concat(right(yr_mth,4),'-',mid(yr_mth,locate('/',yr_mth)+1, (locate('/',yr_mth,4)-locate('/',yr_mth)-1)),'-',left(yr_mth,locate('/',yr_mth)-1)) between '"+string_fdate+"' and '"+string_tdate+"' ";
				ResultSet rs3=s3.executeQuery(qry3);
				
				if(rs3.next())
				{
					double y=rs3.getDouble("cash_cr")+rs3.getDouble("clr_cr")+rs3.getDouble("trf_cr")-(rs3.getDouble("cash_dr")+rs3.getDouble("clr_dr")+rs3.getDouble("trf_dr"));
					array_obj_customer[j].setClBal(x-y);
				}
			}
			
		}catch(Exception e2){e2.printStackTrace();}
		
		finally
		{
			try
			{
				System.out.println("closing1........");
				conn.close();
				System.out.println("closing1........");
			}catch(Exception ex){ex.printStackTrace();}
		}
		
		return array_obj_customer;
	}
	
	// General Activities
	public ClosingBalObject[] getInOperativeAccounts(String string_modcode,String string_date,int int_from_ac_no,int int_to_ac_no) throws RemoteException,SQLException
	{
		ClosingBalObject[] array_obj_customer=null;
		String string_this_date=Validations.convertYMD(string_date);
		String string_temp_date=null;
		int int_period=0;
		Connection conn=null;
		
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement();
			String qry1="Select inop_defn from GenParam ";
			ResultSet rs1=s1.executeQuery(qry1);
			if(rs1.next())
			{
				int_period=rs1.getInt("inop_defn");
			}
			System.out.println("InOp Def= "+ int_period);
			Statement s2=conn.createStatement();
			String qry2="select distinct at.ac_no from AccountTransaction at,AccountMaster am where at.ac_no between "+int_from_ac_no+" and "+int_to_ac_no+" and at.ac_type='"+string_modcode+"' and am.ac_status='O' and am.ac_no=at.ac_no and am.ac_type=at.ac_type and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <'"+string_this_date+"' and at.ac_no not in(select at1.ac_no from AccountTransaction at1,AccountMaster am1 where am1.ac_status='O' and at1.ac_type='"+string_modcode+"' and am1.ac_no=at1.ac_no and am1.ac_type=at1.ac_type and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between date_sub('"+string_this_date+"',interval "+int_period+" year) and '"+string_this_date+"' and at1.ac_no between "+int_from_ac_no+" and "+int_to_ac_no+")";
			ResultSet rs2=s2.executeQuery(qry2);
			rs2.last();
			array_obj_customer=new ClosingBalObject[rs2.getRow()];
			rs2.beforeFirst();
			int j=0;
			while(rs2.next())
			{
				array_obj_customer[j]=new ClosingBalObject();			
				array_obj_customer[j].setAcNo(rs2.getInt("at.ac_no"));
				System.out.println("Ac NO= "+ 	array_obj_customer[j].getAcNo());
				j++;
			}
		}catch(Exception e1){e1.printStackTrace();}
		
		try
		{
			for(int i=0;i<array_obj_customer.length;i++)
			{
				System.out.println("i= "+i);
				Statement s3=conn.createStatement();
				String qry3="Select max(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))) as trn_date from AccountTransaction where ac_type='"+string_modcode+"' and ac_no="+array_obj_customer[i].getAcNo()+"  ";
				ResultSet rs3=s3.executeQuery(qry3);
				while(rs3.next())
				{
					string_temp_date=rs3.getString("trn_date");
				}
				
				Statement s4=conn.createStatement();
				String qry4="Select cl_bal,trn_date from AccountTransaction where ac_type='"+string_modcode+"' and ac_no="+array_obj_customer[i].getAcNo()+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) = '"+string_temp_date+"'  ";
				ResultSet rs4=s4.executeQuery(qry4);
				while(rs4.next())
				{
					array_obj_customer[i].setClBal(rs4.getDouble("cl_bal"));
					array_obj_customer[i].setTrnDate(rs4.getString("trn_date"));
				}
				
				Statement s5=conn.createStatement();
				String qry5="Select concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name from CustomerMaster cm,AccountMaster am where ac_no="+array_obj_customer[i].getAcNo()+" and cm.cid=am.cid ";
				ResultSet rs5=s5.executeQuery(qry5);
				if(rs5.next())
				{
					array_obj_customer[i].setName(rs5.getString("name"));
				}
			}
			
		}catch(Exception e2){e2.printStackTrace();}
		finally
		{
			conn.close();
		}
		return array_obj_customer;	
	}
	
	public int makeInOperativeAccounts(String string_modcode,ClosingBalObject[] array_obj_ac_no) throws RemoteException,SQLException
	{
		int k=0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement();
			System.out.println("Length*******= "+array_obj_ac_no.length);
			for(int i=0;i<array_obj_ac_no.length;i++)
			{
				String qry1="Update AccountMaster set ac_status='I' where ac_no="+array_obj_ac_no[i].getAcNo()+" and ac_type='"+string_modcode+"' ";
				s1.executeUpdate(qry1);
			}
			
			k=1;
		}catch(Exception e){e.printStackTrace();}
		finally
		{
			conn.close();
		}
		
		if(k==1)
			return 1;
		return 0;
	}
	
	public ClosingBalObject[] getGLInfo(String date) throws SQLException
	{
		ClosingBalObject[] array_obj_gl_info=null;
		Connection conn=null;
		int j=0;
		
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("Select distinct gl_name,gl_code from GLMaster where ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"')) ");
			
			if(rs.next())
			{
				rs.last();
				array_obj_gl_info=new ClosingBalObject[rs.getRow()];
				rs.beforeFirst();
			}
			else return array_obj_gl_info;
			
			while(rs.next())
			{
				array_obj_gl_info[j]=new ClosingBalObject();
				array_obj_gl_info[j].setGLName(rs.getString("gl_name"));
				array_obj_gl_info[j].setGLCode(rs.getInt("gl_code"));
				j++;
			}
		}catch(Exception e3){e3.printStackTrace();}
		
		finally
		{
			conn.close();
		}
		return array_obj_gl_info;
	}
	
	public Connection getConnection() 
	{
		try{			
			return ds.getConnection("root","");
 		}catch(Exception e)	{e.printStackTrace();}
		
		return null;
	}

    public String getDateTime() {
        Calendar cal = Calendar.getInstance();
        String date = null;
        try {
            date = Validations.checkDate(cal.get(Calendar.DATE)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR));
        } catch (DateFormatException e) {
            e.printStackTrace();
        }
        int hour = cal.get(Calendar.HOUR);
        if(hour==0)
            hour=12;
        String time= Validations.checkTime(hour+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND))+" "+(cal.get(Calendar.AM_PM)==0?"AM":"PM");
        return date+" "+time;
    }
    public String getSysDateTime() 
    {
        return getDateTime();       
    }
    //Transfer Voucher methods
    public int[] getAccountGlcodes(String ac_type,String gl_type,int ac_no,String date) 
    {
        int[] array_glcodes=null;
        Connection conn=null;
        System.out.println("inside accountglcodes");
        System.out.println(" ac type is"+ac_type);
        ResultSet rs=null;
        ResultSet rs_share=null;
        int prm_code=0;
        int mem_cat=0;
            try
            {
                conn=getConnection();
                Statement stmt=conn.createStatement();
                //ResultSet rs=stmt.executeQuery("select distinct gl_code from GLPost where ac_type='"+ac_type+"' order by gl_code");
                //ResultSet rs=stmt.executeQuery("select distinct glm.gl_code from GLKeyParam as glk, GLMaster as glm where glk.ac_type='"+ac_type+"' and glk.gl_type='"+gl_type+"' and glk.gl_code=glm.gl_code;");
                if(ac_type.startsWith("1001"))
                {
                	rs_share=stmt.executeQuery("select distinct mem_cat,sh_ind from ShareMaster where ac_type='"+ac_type+"' and ac_no='"+ac_no+"' ");
                	if(rs_share.next())
                	{
                		prm_code=(rs_share.getString("sh_ind").equalsIgnoreCase("P")?5:6);
                		mem_cat=(rs_share.getInt("mem_cat"));
                	}
                	rs=stmt.executeQuery("select distinct prm_gl_code from ShareParam sp,GLKeyParam glk, GLMaster glm where glk.ac_type='"+ac_type+"' and sp.ac_type=glk.ac_type and glk.gl_type='"+gl_type+"' and glk.gl_code=glm.gl_code and mem_cat="+mem_cat+" and prm_code="+prm_code+" and ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))");
                }
                else
                	rs=stmt.executeQuery("select distinct glm.gl_code from GLKeyParam as glk, GLMaster as glm where glk.ac_type='"+ac_type+"' and glk.gl_type='"+gl_type+"' and glk.gl_code=glm.gl_code and ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))");
                
                rs.last();
                if(rs.getRow()!=0)
                    array_glcodes=new int[rs.getRow()];
                System.out.println("length"+rs.getRow());
                rs.beforeFirst();
                int i=0;
                while(rs.next())
                {
                    array_glcodes[i]=rs.getInt(1);
                    i++;
                }
            }catch(Exception exception){exception.printStackTrace();}
            
            finally
            {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("The array code in bean is --------"+array_glcodes);
        return array_glcodes;
    }
    
    public int[] getGLCode(String gl_type,String date) 
    {
    	int[] array_glcodes=null;
    	Connection conn=null;
    	System.out.println("inside get gl code method");
    	try
		{
    		conn=getConnection();
    		Statement stmt=conn.createStatement();
    		ResultSet rs=stmt.executeQuery("select distinct gl_code from GLMaster where gl_type='"+gl_type+"' and ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"')) ");
    		rs.last();
    		
    		 if(rs.getRow()!=0)
                array_glcodes=new int[rs.getRow()];
            System.out.println("length"+rs.getRow());
            rs.beforeFirst();
            int i=0;
            while(rs.next())
            {
                array_glcodes[i]=rs.getInt(1);
                i++;
            }
        }catch(Exception exception){exception.printStackTrace();}
        
        finally
        {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    return array_glcodes;
}
    
    
    public String[] getAccountTransferTypes(String ac_type,int glcode) 
    {
        String[] array_transfer_types=null;
        Connection conn=null;
        System.out.println("inside accounttransfer types");
            try
            {
                conn=getConnection();
                Statement stmt=conn.createStatement();
                ResultSet rs=stmt.executeQuery("select distinct trn_type from GLPost where ac_type='"+ac_type+"' and gl_code="+glcode+" ");
                rs.last();
                if(rs.getRow()!=0)
                    array_transfer_types=new String[rs.getRow()];
                System.out.println("length"+rs.getRow());
                rs.beforeFirst();
                int i=0;
                while(rs.next())
                {
                    array_transfer_types[i]=rs.getString("trn_type");
                    i++;
                }
            }catch(Exception exception){exception.printStackTrace();}
            
            finally
            {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        return array_transfer_types;
    }
    public String getCDIndicator(String ac_type,int int_glcode,String string_trn_type) 
    {
        String string_cd_indicator=null;
        Connection conn=null;
        System.out.println("inside getCDIndicator");
            try
            {
                conn=getConnection();
                Statement stmt=conn.createStatement();
                ResultSet rs=stmt.executeQuery("select cr_dr,mult_by from GLPost where ac_type='"+ac_type+"' and gl_code="+int_glcode+" and trn_type='"+string_trn_type+"' ");
                rs.last();
                if(rs.getRow()==1)
                    return string_cd_indicator=rs.getString("cr_dr");
                else if(rs.getRow()>1) {
                    int i=0;
                    rs.beforeFirst();
                    
                    while(rs.next())
                    {
                        if(rs.getInt("mult_by")==1)
                            return string_cd_indicator=rs.getString("cr_dr");
                        i++;
                    }
                }
            }catch(Exception exception){exception.printStackTrace();}
            
            finally
            {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        return string_cd_indicator;
    }
    
    
    public int storeTransferVoucherData(VoucherDataObject[] array_VoucherDataObject)  
    {
        PreparedStatement pstat=null;
        Connection conn=null;
        ResultSet rs2=null;
        int vch_no=0;
        
        System.out.println("Array object is-----------"+array_VoucherDataObject[0].getVoucherType());
        try
        {
            conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if(conn==null)
                throw new SQLException();
                        
                                    
         if(array_VoucherDataObject!=null)
         {
        	 String modules_col = "last_trf_scroll_no";
        	 System.out.println("Array Voucher Object --------"+array_VoucherDataObject[0].getVoucherType());
        	 if(array_VoucherDataObject[0].getVoucherType().equalsIgnoreCase("P")) {
        		 modules_col = "lst_voucher_no";
        	 }
        	 
        	 
             if(array_VoucherDataObject[0].getVoucherNo()==0) 
             {
                 rs2=stmt.executeQuery("select " + modules_col + " from Modules where modulecode='1019000'");
                 rs2.next();
                 System.out.println("vch no before update"+rs2.getInt(1));
                 vch_no=rs2.getInt(1)+1;
                 if(stmt.executeUpdate("update Modules set " + modules_col + " = '"+vch_no+"' where modulecode='1019000' ")==0)
                    throw new SQLException();
             }
             else
                 vch_no=array_VoucherDataObject[0].getVoucherNo();
           
           //pstat=conn.prepareStatement("insert into VoucherData(vch_type,trn_date,gl_sl_type,gl_sl_code,mod_ac_type,mod_ac_no,trn_type,trn_amt,cd_ind,cash_pdrd,de_user,de_tml,de_date,vch_no,narration)values(?,?,?,?,?,?,?,?,?,'F',?,?,date_format(sysdate(),'%d/%m/%Y %r'),?,?)");
             
             System.out.println("array length  "+array_VoucherDataObject.length);
            
            for(int i=0;i<array_VoucherDataObject.length;i++)
            {
            	System.out.println("inside for loop of store voucher data... i = "+i);
            	
            	pstat=conn.prepareStatement("insert into VoucherData(vch_type,trn_date,gl_sl_type,gl_sl_code,mod_ac_type,mod_ac_no,trn_type,trn_amt,cd_ind,cash_pdrd,de_user,de_tml,de_date,vch_no,narration) values(?,?,?,?,?,?,?,?,?,'F',?,?,?,?,?)");
            	//System.out.println("voucher type in array ----"+array_VoucherDataObject[i].getVoucherType());
                pstat.setString(1,array_VoucherDataObject[i].getVoucherType());
                pstat.setString(2,array_VoucherDataObject[i].getTransactionDate());
                pstat.setString(3,array_VoucherDataObject[i].getGlType());
                pstat.setInt(4,array_VoucherDataObject[i].getGlCode());
                System.out.println("Account type while inserting ======"+array_VoucherDataObject[i].getModuleAccountType());
                pstat.setString(5,array_VoucherDataObject[i].getModuleAccountType());
                pstat.setInt(6,array_VoucherDataObject[i].getModuleAccountNo());
                pstat.setString(7,array_VoucherDataObject[i].getTransactionType());
                pstat.setDouble(8,array_VoucherDataObject[i].getTransactionAmount());
                pstat.setString(9,array_VoucherDataObject[i].getCdIndicator());
                System.out.println("CD INDICATOR----"+array_VoucherDataObject[i].getCdIndicator());
                pstat.setString(10,array_VoucherDataObject[i].obj_userverifier.getUserId());
                pstat.setString(11,array_VoucherDataObject[i].obj_userverifier.getUserTml());
                pstat.setString(12,array_VoucherDataObject[i].obj_userverifier.getUserDate());
                pstat.setInt(13,vch_no);
                pstat.setString(14,array_VoucherDataObject[i].getNarration());
                pstat.executeUpdate();
            }
            //int[] length = pstat.executeBatch();
           // System.out.println("length is"+length.length); 
            System.out.println("arr length"+array_VoucherDataObject.length);
        }
        }catch(SQLException sqlexception){
            sqlexception.printStackTrace();
            ctx.setRollbackOnly();           
        }        
        finally{
            try{
                conn.close();
            }catch(SQLException e){e.printStackTrace();}
        }
        
        return vch_no; 
    }
    
//Added By Karthi-->10/08/2006
    
public SIEntryObject[] displayUnVerifiedSINo(int type) throws RemoteException,NullPointerException
{
	SIEntryObject si_obj[]=null;
	Statement stmt=null;
	ResultSet rs=null;
	Connection conn=null;
	int i=0;
	try
	{
		conn=getConnection();
		stmt=conn.createStatement();
		if(type==1)
			rs=stmt.executeQuery("select si_no,pri_no,due_dt from StdInst where ve_user is null and de_user is not null");
		else
			rs=stmt.executeQuery("select si_no,pri_no,due_dt from StdInst where alt_ve_user is null and de_user is not null and ve_user is not null");
		
		rs.last();
		if(rs.getRow()==0)
			throw new NullPointerException();
		else
		{
			si_obj=new SIEntryObject[rs.getRow()];
			rs.beforeFirst();
			while(rs.next())
			{
				si_obj[i]=new SIEntryObject();
				si_obj[i].setInstNo(rs.getInt("si_no"));
				si_obj[i].setPriorityNo(rs.getInt("pri_no")); 
				si_obj[i].setDueDt(rs.getString("due_dt"));
				i++;
			}
			
		}
	}catch(SQLException ex){ex.printStackTrace();}
	finally
	{
		try{conn.close();}catch(SQLException ex){ex.printStackTrace();}
	}
	
	return si_obj;
}

public String[] stdLoanExec(int std_no,String user,String tml,String date,String date_time) throws SQLException
{
	Statement stmt=null;
    Connection conn=null;
    ResultSet rs=null;
    String return_values[] = new String[9];
    String from_ac_type=null,to_ac_type=null,due_date=null,int_upto_date=null;
    String complete_ind=" ",expiry_date=null;
    int from_ac_no=0,to_ac_no=0,loan_option=0, sub_category=0,trn_seq=0,update_count=0,expiry_days=0;
    double std_exe_amount=0,adjusted_amount=0,transfer_amt_available=0,min_bal=0,othercharges_paid=0;
    double penal_interest=0,interest_amt=0,other_charges=0,principal_overdue=0,loan_balance=0;
    double transaction_amt=0,total_closable_amt=0,principal_paid=0,interest_paid=0,penalint_paid=0;
    boolean update_flag=false;
    Object trn_seq_object=null;
    AccountObject acc_object=null;
    LoanTransactionObject loan_object=null;
    AccountTransObject acc_trn=null;
    try
    {
        conn=getConnection();
        stmt = conn.createStatement();
        rs=stmt.executeQuery("select * from StdInst where si_no="+std_no+" ");
        if(rs.next()){
        	from_ac_type=rs.getString("fr_ac_ty");
        	from_ac_no=rs.getInt("fr_ac_no");
        	to_ac_type=rs.getString("to_ac_ty");
        	to_ac_no=rs.getInt("to_ac_no");
        	due_date=rs.getString("due_dt");
        	loan_option=rs.getInt("ln_opt");
        	std_exe_amount=rs.getDouble("amount");
        	adjusted_amount=rs.getDouble("amt_adj");
        	expiry_days=rs.getInt("expiry_days");
        	expiry_date=Validations.addDays(due_date,expiry_days);
        }
        if(Validations.dayCompare(date,expiry_date)==0 && expiry_days!=0){
        	update_count=updateDueDateForStdExec(std_no);
			if(update_count==0){
				return_values[8]="Update Error";
				ctx.setRollbackOnly();
			}
			else
				return_values[8]="SI Number Expired";
			return return_values;
        }
        if(from_ac_type!=null && to_ac_type!=null){
        	acc_object=commonlocal.getAccount(null,from_ac_type,from_ac_no,date);
        	if(acc_object==null){
        		return_values[8]="From Account Not Found";
        		return return_values;
        	}
        	else if(acc_object.getAccStatus()!=null && acc_object.getAccStatus().equalsIgnoreCase("C")){
        		return_values[8]="Frm Acc Closed";
        		return return_values;
        	}
        	if(acc_object.getFreezeInd().equalsIgnoreCase("T")){
        		return_values[8]="Frm Acc Freezed";
        		return return_values;
        	}
        	else{
        		rs=stmt.executeQuery("select min_bal from Modules where modulecode='"+from_ac_type+"'");
				if(rs.next())
					min_bal = rs.getDouble("min_bal");
				transfer_amt_available = (acc_object.getAmount()-min_bal); //  we are doing this in order to maintain a min balance in the account
				transfer_amt_available = Math.floor(transfer_amt_available);
				if(transfer_amt_available<=0){
					return_values[8]="No amount";
					return return_values;
				}
				
				rs=stmt.executeQuery("select lm.*,cm.sub_category from LoanMaster as lm, CustomerMaster as cm where lm.ac_type='"+to_ac_type+"' and lm.ac_no="+to_ac_no+" and lm.ve_tml is not null and lm.cid=cm.cid;");
				if(rs.next()){
					if(rs.getString("close_date")!=null){
						return_values[8]="To Acc Closed";
						return return_values;
					}
					sub_category=rs.getInt("sub_category");
				}
				else{
					return_values[8]="To Account Not Found";
            		return return_values;
				}
				
				rs=stmt.executeQuery("select * from LoanRecoveryDetail where concat(right(processing_date,4),'-',mid(processing_date,locate('/',processing_date)+1,(locate('/',processing_date,4)-locate('/',processing_date)-1)),'-',left(processing_date,locate('/',processing_date)-1)) = '"+Validations.convertYMD(date) +"' and ac_type='"+to_ac_type+"' and ac_no="+to_ac_no+" ");
				if(!rs.next()){
					loanlocal.postRecoveryDetails(to_ac_type,to_ac_no,date,sub_category,1,null,null); // We are calling this function because to update the LoanRecoveryDetail table with current calculated values of interest,penal_interest,other_charges,loan_balance for the current date
				}
				
				rs=stmt.executeQuery("select * from LoanRecoveryDetail where concat(right(processing_date,4),'-',mid(processing_date,locate('/',processing_date)+1,(locate('/',processing_date,4)-locate('/',processing_date)-1)),'-',left(processing_date,locate('/',processing_date)-1)) = '"+Validations.convertYMD(date) +"' and ac_type='"+to_ac_type+"' and ac_no="+to_ac_no+" ");
				if(rs.next()){
					interest_amt=rs.getDouble("int_amt");
					penal_interest=rs.getDouble("penal_amt");
					other_charges=rs.getDouble("other_charges");
					loan_balance=rs.getDouble("loan_balance");
					total_closable_amt=(interest_amt+penal_interest+other_charges+loan_balance);
				}
					
				loan_object=loanlocal.getPrincipalOutstandings(to_ac_type,to_ac_no,date);
				if(loan_object!=null){
					principal_overdue=loan_object.getPrincipalPayable()+loan_object.getPrincipalBalance();
				}
				
				if(loan_option==1){
					transaction_amt=(std_exe_amount-adjusted_amount); // towards total amt
				}
				else if(loan_option==2){
					transaction_amt=(std_exe_amount-adjusted_amount);// towards principal
					transaction_amt += (interest_amt+penal_interest+other_charges); // towards principal + (interest_amt+penal_interest+other_charges)
				}
				else if(loan_option==3){
					transaction_amt=(principal_overdue+interest_amt+penal_interest+other_charges);
					transaction_amt -= adjusted_amount;
				}
				
				if(transaction_amt<0) // because at any point trn_amt should not be negative
					transaction_amt=0;
				if(total_closable_amt<transaction_amt)
					transaction_amt=total_closable_amt;
				if(transfer_amt_available<transaction_amt)
					transaction_amt=transfer_amt_available;
				
				transaction_amt=Math.floor(transaction_amt);
				if(transaction_amt>0){
					loan_object=null;
					loan_object=new LoanTransactionObject();
											
					loan_object.setAccType(to_ac_type);
					loan_object.setAccNo(to_ac_no);
					loan_object.setTransactionDate(date);
					loan_object.setTranType("R");
					loan_object.setTransactionAmount(transaction_amt);
					loan_object.setTranMode("T");
					loan_object.setTranSou(tml);
					loan_object.setReferenceNo(0);
					loan_object.setTranNarration(from_ac_type+"  "+from_ac_no);
					loan_object.setCdind("C");
					loan_object.uv.setUserTml(tml);
					loan_object.uv.setUserId(user);
					loan_object.uv.setUserDate(date_time);
					loan_object.uv.setVerId(user);
					loan_object.uv.setVerTml(tml);
					loan_object.uv.setVerDate(date_time);
					
					acc_trn=new AccountTransObject();
					acc_trn.setAccType(from_ac_type);
					acc_trn.setAccNo(from_ac_no);
					acc_trn.setTransDate(date);
					acc_trn.setTransType("P");
					acc_trn.setTransAmount(transaction_amt);
					acc_trn.setTransMode("T");
					acc_trn.setCdInd("D");
					acc_trn.setTransNarr(to_ac_type+"  "+to_ac_no);
					acc_trn.setTransSource(tml);
					acc_trn.uv.setUserTml(tml);
					acc_trn.uv.setUserId(user);
					acc_trn.uv.setUserDate(date_time);
					acc_trn.uv.setVerTml(tml);
					acc_trn.uv.setVerId(user);
					acc_trn.uv.setVerDate(date_time);
					
					int a=commonlocal.storeAccountTransaction(acc_trn);
					if(a!=1){
						ctx.setRollbackOnly();
						return_values[8]="To Account update Error";
						return 	return_values;
					}
					
					try{
						trn_seq_object = loanlocal.recoverLoanAccount(loan_object);
					}catch(RecordNotUpdatedException recn){recn.printStackTrace();trn_seq_object=null;}
					
					if(trn_seq_object==null){
						ctx.setRollbackOnly();
						return_values[8]="From Account update Error";
						return 	return_values;
					}
					else{
						trn_seq = Integer.parseInt(trn_seq_object.toString());
						rs=stmt.executeQuery("select * from LoanTransaction where ac_type='"+to_ac_type+"' and ac_no="+to_ac_no+" and trn_seq="+trn_seq+" ");
						if(rs.next()){
							principal_paid=rs.getDouble("pr_amt");
							interest_paid=rs.getDouble("int_amt")+rs.getDouble("extra_int");
							penalint_paid=rs.getDouble("penal_amt");
							othercharges_paid=rs.getDouble("other_amt");
							int_upto_date=rs.getString("int_date");
						}
						
						if(loan_option==1){
							if((transaction_amt+adjusted_amount)<std_exe_amount){
								complete_ind="F";
								adjusted_amount += transaction_amt;
							}
							else
								complete_ind="T";
						}
						else if(loan_option==2){
							if((principal_paid+adjusted_amount)<std_exe_amount){
								complete_ind="F";
								adjusted_amount += transaction_amt;
							}
							else
								complete_ind="T";
						}
						else if(loan_option==3){
							if((transaction_amt+adjusted_amount)<(principal_overdue+interest_amt+penal_interest+other_charges)){
								complete_ind="F";
								adjusted_amount += transaction_amt;
							}
							else
								complete_ind="T";
						}
						stmt.executeUpdate("insert into StdInstDone values ("+std_no+",'"+date+"','"+due_date+"',"+transaction_amt+","+principal_paid+","+interest_paid+","+penalint_paid+","+othercharges_paid+",'"+int_upto_date+"','"+complete_ind+"','"+user+"','"+tml+"','"+date_time+"')");
						update_flag=true;
					}
				}
				
				if(complete_ind.equalsIgnoreCase("T") || (Validations.dayCompare(date,expiry_date)==0 && expiry_days!=0)){
					update_count=updateDueDateForStdExec(std_no);
					if(update_count==0){
						return_values[8]="Update Error";
						ctx.setRollbackOnly();
					}
					else
						return_values[8]="Executed";
				} else if(update_flag){
					if(stmt.executeUpdate("update StdInst set amt_adj="+adjusted_amount+" where si_no="+std_no+" ")>0){
						return_values[8]="Partially Executed";
					}
					else{
						return_values[8]="Update Error";
						ctx.setRollbackOnly();
					}
				}
				
				if(complete_ind.equalsIgnoreCase("T") && checkForNextExec(std_no, date) ) {
					stdLoanExec(std_no, user, tml, date, date_time);
				}
        	}
        }
        
    }catch(Exception exe){
    	exe.printStackTrace();
    	return_values[8]="Error";
    	ctx.setRollbackOnly();           
    }        
    finally{
        try{
            conn.close();
        }catch(SQLException e){e.printStackTrace();}
    }
    return return_values;   
}

public int updateDueDateForStdExec(int std_no)  
{
    Connection conn=null;
    int update_count=0;
    ResultSet rs=null;
    Statement stmt=null;
    int period_months=0,period_days=0;
    String due_date=null;
    try
    {
        conn=getConnection();
        stmt = conn.createStatement();
        rs=stmt.executeQuery("select due_dt,prd_mths,prd_days from StdInst where si_no="+std_no+" ");
        if(rs.next()){
        	due_date=rs.getString("due_dt");
        	period_months=rs.getInt("prd_mths");
        	period_days=rs.getInt("prd_days");
        	
        	due_date=Validations.addNoOfMonths(due_date,period_months);
        	due_date=Validations.addDays(due_date,period_days);
        	
        	update_count=stmt.executeUpdate("update StdInst set due_dt='"+due_date+"',amt_adj=0.00,exec_time=exec_time+1 where  si_no="+std_no+" ");
        }
        
    }catch(Exception exe){
    	exe.printStackTrace();
    	update_count=0;
    	ctx.setRollbackOnly();           
    }        
    finally{
        try{
            conn.close();
        }catch(SQLException e){e.printStackTrace();}
    }
    return update_count;
}

public boolean checkForNextExec(int std_no,String trn_date) {

    Connection conn=null;
    ResultSet rs=null;
    Statement stmt=null;
    String due_date=null, last_date = null;
    try
    {
        conn=getConnection();
        stmt = conn.createStatement();
        rs=stmt.executeQuery("select due_dt,last_date from StdInst where si_no="+std_no+" ");
        if(rs.next()){
        	due_date=rs.getString("due_dt");
        	last_date=rs.getString("last_date");
        	
        	if( Validations.dayCompare(due_date,last_date) > 0 && Validations.dayCompare(due_date,trn_date) >= 0) {
        		return true;
        	}
        } 
        
    }catch(Exception exe){
    	exe.printStackTrace();
    	return false;           
    }        
    finally{
        try{
            conn.close();
        }catch(SQLException e){e.printStackTrace();return false;}
    }
    return false;
}
 

public String[] stdRDExec(int std_no,String user,String tml,String date,String date_time) throws SQLException
{
	Statement stmt=null;
    Connection conn=null;
    ResultSet rs=null;
    String return_values[] = new String[9];
    String from_ac_type=null,to_ac_type=null,due_date=null,int_upto_date=null;
    String complete_ind=" ",expiry_date=null;
    int from_ac_no=0,to_ac_no=0,int_dp_type=0,category=0,trn_seq=0,update_count=0,expiry_days=0,int_return_value=0,total_inst=0,inst_done=0,inst_left=0;
    double std_exe_amount=0,transfer_amt_available=0,min_bal=0,transaction_amt=0,total_closable_amt=0,dep_amt=0;
    boolean update_flag=false;
    Object trn_seq_object=null;
    AccountObject acc_object=null,accountobject_to_acc=null;
    AccountTransObject ac_tran=null;
    
    CustomerMasterObject customermasterobject=null;
    try
    {
        conn=getConnection();
        stmt = conn.createStatement();
        rs=stmt.executeQuery("select * from StdInst where si_no="+std_no+" ");
        if(rs.next()){
        	from_ac_type=rs.getString("fr_ac_ty");
        	from_ac_no=rs.getInt("fr_ac_no");
        	to_ac_type=rs.getString("to_ac_ty");
        	to_ac_no=rs.getInt("to_ac_no");
        	due_date=rs.getString("due_dt");
        	std_exe_amount=rs.getDouble("amount");
        	expiry_days=rs.getInt("expiry_days");
        	expiry_date=Validations.addDays(due_date,expiry_days);
        }
        
        if(Validations.dayCompare(date,expiry_date)==0 && expiry_days!=0){
        	update_count=updateDueDateForStdExec(std_no);
			if(update_count==0){
				return_values[8]="Update Error";
				ctx.setRollbackOnly();
			}
			else
				return_values[8]="SI Number Expired";
			return return_values;
        }
        if(from_ac_type!=null && to_ac_type!=null && from_ac_no!=0 && to_ac_no!=0){
        	acc_object=commonlocal.getAccount(null,from_ac_type,from_ac_no,date);
        	if(acc_object==null){
        		return_values[8]="From Account Not Found";
        		return return_values;
        	}
        	else if(acc_object.getAccStatus()!=null && acc_object.getAccStatus().equalsIgnoreCase("C")){
        		return_values[8]="Frm Acc Closed";
        		return return_values;
        	}
        	else if(acc_object.getFreezeInd().equalsIgnoreCase("T")){
        		return_values[8]="Frm Acc Freezed";
        		return return_values;
        	}
        	else{
        		rs=stmt.executeQuery("select min_bal from Modules where modulecode='"+from_ac_type+"'");
				if(rs.next())
					min_bal = rs.getDouble("min_bal");
				transfer_amt_available = (acc_object.getAmount()-min_bal); //  we are doing this in order to maintain a min balance in the account
				transfer_amt_available = Math.floor(transfer_amt_available);
				if(transfer_amt_available<std_exe_amount){
					return_values[8]="No amount";
					return return_values;
				}
				accountobject_to_acc=commonlocal.getAccount(null,to_ac_type,to_ac_no,date);
				if(accountobject_to_acc!=null){
					total_inst=accountobject_to_acc.getDepositdays();//for deposit days;
					inst_done=accountobject_to_acc.getInst_no();
					inst_left=total_inst-inst_done;
					dep_amt=accountobject_to_acc.getAmount();
				
					if(accountobject_to_acc.getClose_ind() != 0 && accountobject_to_acc.getClose_ind() <6){
                	   	return_values[8]="To Account no. Closed";
						return return_values;
                	}
					else{
//						checking conditions for standing instructions to execute.
						 if((std_exe_amount < dep_amt) || (std_exe_amount%dep_amt!=0) || (std_exe_amount > (dep_amt*inst_left))){
							transaction_amt=0;
							complete_ind="F";
						}else if(std_exe_amount <= (dep_amt*inst_left)){
							transaction_amt=std_exe_amount;
							complete_ind="T";
						}
					}
				}
				else{
					return_values[8]="To Account Not Found";
            		return return_values;
				}
				
				rs=stmt.executeQuery("select lst_trn_seq,custtype from DepositMaster dm,CustomerMaster cm where ac_type='"+to_ac_type+"' and ac_no="+to_ac_no+" and dm.cid=cm.cid");
		        if(rs.next()){
		        	trn_seq=rs.getInt("lst_trn_seq")+1;
		        	category=rs.getInt("custtype");
		        	stmt.executeUpdate("update DepositMaster set lst_trn_seq=lst_trn_seq+1 where ac_type='"+to_ac_type+"' and ac_no="+to_ac_no+" ");
		        }
				
				if(transaction_amt>0){
					// Insert a row into Deposit Transaction.
					PreparedStatement ps_store_trn=conn.prepareStatement("insert into DepositTransaction values(?,?,?,?,'D',?,0,0,0,?,null,null,?,?,'T',?,'C',0,?,?,?,?,?,?)");
					ps_store_trn.setString(1,to_ac_type);
					ps_store_trn.setInt(2,to_ac_no);
					ps_store_trn.setInt(3,trn_seq);
					ps_store_trn.setString(4,date);
					ps_store_trn.setDouble(5,transaction_amt);
					ps_store_trn.setDouble(6,(accountobject_to_acc.getShadowBalance()+transaction_amt));
					ps_store_trn.setInt(7,0);
					ps_store_trn.setString(8,(from_ac_type+"  "+from_ac_no));
					ps_store_trn.setString(9,user);
					ps_store_trn.setString(10,tml);
					ps_store_trn.setString(11,user);
					ps_store_trn.setString(12,date_time);
					ps_store_trn.setString(13,tml);
					ps_store_trn.setString(14,user);
					ps_store_trn.setString(15,date_time);
					ps_store_trn.executeUpdate();
					
			// insert gl entry.
					
					if(category==0)
						int_dp_type=1;
					else if(category==1)
						int_dp_type=2;
					else int_dp_type=1;
					
					rs = stmt.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+int_dp_type+" and gk.ac_type = '"+to_ac_type+"' and trn_type='D' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs.next();
					GLTransObject trnobj=new GLTransObject();
					//code changed by sanjeet..
					trnobj.setTrnDate(date);
					//trnobj.setTrnDate(commonLocal.getSysDate());
					trnobj.setGLType(rs.getString("gk.gl_type"));
					trnobj.setGLCode(rs.getString("gk.gl_code"));
				    trnobj.setTrnMode("T");
					trnobj.setAmount(transaction_amt*rs.getInt("mult_by"));
					trnobj.setCdind("C");
					trnobj.setAccType(to_ac_type);
					trnobj.setAccNo(String.valueOf(to_ac_no));
					trnobj.setTrnType("D");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trn_seq);
					trnobj.setVtml(tml);
					trnobj.setVid(user);	
					trnobj.setVDate(date_time);
					commonlocal.storeGLTransaction(trnobj);	
					
			// Insert a row in account transaction.
					ac_tran = new AccountTransObject();
					ac_tran.setAccType(from_ac_type);
					ac_tran.setAccNo(from_ac_no);
					ac_tran.setTransDate(getSysDate());
					ac_tran.setTransType("P");
					ac_tran.setTransAmount(transaction_amt);
					ac_tran.setTransMode("T");
					ac_tran.setTransSource(tml);
					ac_tran.setCdInd("D");
					ac_tran.setChqDDNo(0);
					ac_tran.setChqDDDate("");
					ac_tran.setTransNarr(to_ac_type+" "+to_ac_no);
					ac_tran.setRef_No(0);
					ac_tran.setPayeeName(accountobject_to_acc.getAccname());
					ac_tran.setCloseBal(transaction_amt);
					ac_tran.setLedgerPage(0);
					ac_tran.setTransSource(tml);
					ac_tran.uv.setUserTml(tml);
					ac_tran.uv.setUserId(user);
					ac_tran.uv.setUserDate(date_time);
					ac_tran.uv.setVerTml(tml);
					ac_tran.uv.setVerId(user);
					ac_tran.uv.setVerDate(date_time);				
					int_return_value=commonlocal.storeAccountTransaction(ac_tran);
					if(int_return_value!=1){
						ctx.setRollbackOnly();
						return_values[8]="To Account update Error";
						return 	return_values;
					}
					// inserting a row in stdinstdone.
					stmt.executeUpdate("insert into StdInstDone values ("+std_no+",'"+date+"','"+due_date+"',"+transaction_amt+",'0.0','0.0','0.0',0.0,null,'"+complete_ind+"','"+tml+"','"+user+"','"+date_time+"')");
					update_flag=true;
				}
				
				if(complete_ind.equalsIgnoreCase("T") || (Validations.dayCompare(date,expiry_date)==0 && expiry_days!=0)){
					update_count=updateDueDateForStdExec(std_no);
					if(update_count==0){
						return_values[8]="Update Error";
						ctx.setRollbackOnly();
					}
					else
						return_values[8]="Executed";
				}
				
				if(complete_ind.equalsIgnoreCase("T") && checkForNextExec(std_no, date) ) {
					stdRDExec(std_no, user, tml, date, date_time);
				}
        	}
        }
        
    }catch(Exception exe){
    	exe.printStackTrace();
    	return_values[8]="Error";
    	ctx.setRollbackOnly();           
    }        
    finally{
        try{
            conn.close();
        }catch(SQLException e){e.printStackTrace();}
    }
    return return_values;   
}

public String[] stdSBCAExec(int std_no,String user,String tml,String date,String date_time) throws SQLException
{
	Statement stmt=null;
    Connection conn=null;
    ResultSet rs=null;
    String return_values[] = new String[9];
    String from_ac_type=null,to_ac_type=null,due_date=null,int_upto_date=null;
    String complete_ind=" ",expiry_date=null;
    int from_ac_no=0,to_ac_no=0,update_count=0,expiry_days=0;
    double std_exe_amount=0,adjusted_amount=0,transfer_amt_available=0,min_bal=0;
    double transaction_amt=0;
    boolean update_flag=false;
    Object trn_seq_object=null;
    AccountObject from_acc_object=null,to_acc_object=null;
    AccountTransObject acc_trn=null;
    try
    {
        conn=getConnection();
        stmt = conn.createStatement();
        rs=stmt.executeQuery("select * from StdInst where si_no="+std_no+" ");
        if(rs.next()){
        	from_ac_type=rs.getString("fr_ac_ty");
        	from_ac_no=rs.getInt("fr_ac_no");
        	to_ac_type=rs.getString("to_ac_ty");
        	to_ac_no=rs.getInt("to_ac_no");
        	due_date=rs.getString("due_dt");
        	std_exe_amount=rs.getDouble("amount");
        	adjusted_amount=rs.getDouble("amt_adj");
        	expiry_days=rs.getInt("expiry_days");
        	expiry_date=Validations.addDays(due_date,expiry_days);
        }
        if(Validations.dayCompare(date,expiry_date)==0 && expiry_days!=0){
        	update_count=updateDueDateForStdExec(std_no);
			if(update_count==0){
				return_values[8]="Update Error";
				ctx.setRollbackOnly();
			}
			else
				return_values[8]="SI Number Expired";
			return return_values;
        }
        if((from_ac_type!=null && to_ac_type!=null) && (from_ac_no!=0 && to_ac_no!=0)){
        	from_acc_object=commonlocal.getAccount(null,from_ac_type,from_ac_no,date);
        	if(from_acc_object==null){
        		return_values[8]="From Account Not Found";
        		return return_values;
        	}
        	else if(from_acc_object.getAccStatus()!=null && from_acc_object.getAccStatus().equalsIgnoreCase("C")){
        		return_values[8]="Frm Acc Closed";
        		return return_values;
        	}
        	if(from_acc_object.getFreezeInd().equalsIgnoreCase("T")){
        		return_values[8]="Frm Acc Freezed";
        		return return_values;
        	}
        	else{
        		rs=stmt.executeQuery("select min_bal from Modules where modulecode='"+from_ac_type+"'");
				if(rs.next())
					min_bal = rs.getDouble("min_bal");
				transfer_amt_available = (from_acc_object.getAmount()-min_bal); //  we are doing this in order to maintain a min balance in the account
				transfer_amt_available = Math.floor(transfer_amt_available);
				if(transfer_amt_available<=0){
					return_values[8]="No amount";
					return return_values;
				}
				
				to_acc_object=commonlocal.getAccount(null,to_ac_type,to_ac_no,date);
	        	if(to_acc_object==null){
	        		return_values[8]="To Account Not Found";
	        		return return_values;
	        	}
	        	else if(to_acc_object.getAccStatus()!=null && to_acc_object.getAccStatus().equalsIgnoreCase("C")){
	        		return_values[8]="To Acc Closed";
	        		return return_values;
	        	}
	        	if(to_acc_object.getFreezeInd().equalsIgnoreCase("T")){
	        		return_values[8]="To Acc Freezed";
	        		return return_values;
	        	}
				
	        	transaction_amt=std_exe_amount-adjusted_amount;
	        	if(transfer_amt_available<=transaction_amt)
	        		transaction_amt=transfer_amt_available;
	        	
				transaction_amt=Math.floor(transaction_amt);
				// make one credit entry in to a AccounTransaction table
				if(transaction_amt>0){
					acc_trn=new AccountTransObject();
					acc_trn.setAccType(to_ac_type);
					acc_trn.setAccNo(to_ac_no);
					acc_trn.setTransDate(date);
					acc_trn.setTransType("R");
					acc_trn.setTransAmount(transaction_amt);
					acc_trn.setTransMode("T");
					acc_trn.setCdInd("C");
					acc_trn.setTransNarr(from_ac_type+"  "+from_ac_no);
					acc_trn.setTransSource(tml);
					acc_trn.uv.setUserId(user);
					acc_trn.uv.setUserTml(tml);
					acc_trn.uv.setUserDate(date_time);
					acc_trn.uv.setVerTml(tml);
					acc_trn.uv.setVerId(user);
					acc_trn.uv.setVerDate(date_time);
					
					int a=commonlocal.storeAccountTransaction(acc_trn);
					if(a!=1){
						ctx.setRollbackOnly();
						return_values[8]="To Account update Error";
						return 	return_values;
					}
					
					acc_trn=new AccountTransObject();
					acc_trn.setAccType(from_ac_type);
					acc_trn.setAccNo(from_ac_no);
					acc_trn.setTransDate(date);
					acc_trn.setTransType("P");
					acc_trn.setTransAmount(transaction_amt);
					acc_trn.setTransMode("T");
					acc_trn.setCdInd("D");
					acc_trn.setTransNarr(to_ac_type+"  "+to_ac_no);
					acc_trn.setTransSource(tml);
					acc_trn.uv.setUserTml(tml);
					acc_trn.uv.setUserId(user);
					acc_trn.uv.setUserDate(date_time);
					acc_trn.uv.setVerTml(tml);
					acc_trn.uv.setVerId(user);
					acc_trn.uv.setVerDate(date_time);
					
					a=commonlocal.storeAccountTransaction(acc_trn);
					if(a!=1){
						ctx.setRollbackOnly();
						return_values[8]="From Account update Error";
						return 	return_values;
					}
					
					if((transaction_amt+adjusted_amount)<std_exe_amount){
						complete_ind="F";
						adjusted_amount += transaction_amt;
					}
					else
						complete_ind="T";
					stmt.executeUpdate("insert into StdInstDone values ("+std_no+",'"+date+"','"+due_date+"',"+transaction_amt+",0,0,0,0,null,'"+complete_ind+"','"+user+"','"+tml+"','"+date_time+"')");
					update_flag=true;
				}
				
				if(complete_ind.equalsIgnoreCase("T") || (Validations.dayCompare(date,expiry_date)==0 && expiry_days!=0)){
					update_count=updateDueDateForStdExec(std_no);
					if(update_count==0){
						return_values[8]="Update Error";
						ctx.setRollbackOnly();
					}
					else
						return_values[8]="Executed";
				}
				else if(update_flag){
					if(stmt.executeUpdate("update StdInst set amt_adj="+adjusted_amount+" where si_no="+std_no+" ")>0){
						return_values[8]="Partially Executed";
					}
					else{
						return_values[8]="Update Error";
						ctx.setRollbackOnly();
					}
				}
				
				if(complete_ind.equalsIgnoreCase("T") && checkForNextExec(std_no, date) ) {
					stdSBCAExec(std_no, user, tml, date, date_time);
				}
        	}
        }
        
    }catch(Exception exe){
    	exe.printStackTrace();
    	return_values[8]="Error";
    	ctx.setRollbackOnly();           
    }        
    finally{
        try{
            conn.close();
        }catch(SQLException e){e.printStackTrace();}
    }
    return return_values;   
}


public String[] stdLDExec(int std_no,String user,String tml,String date,String date_time) throws SQLException
{
	Statement stmt=null;
    Connection conn=null;
    ResultSet rs=null;
    String return_values[] = new String[9];
    String from_ac_type=null,to_ac_type=null,due_date=null,int_upto_date=null;
    String complete_ind=" ",expiry_date=null;
    int from_ac_no=0,to_ac_no=0,loan_option=0, sub_category=0,trn_seq=0,update_count=0,expiry_days=0;
    double std_exe_amount=0,adjusted_amount=0,transfer_amt_available=0,min_bal=0,othercharges_paid=0;
    double interest_amt=0,other_charges=0,principal_overdue=0,loan_balance=0;
    double transaction_amt=0,total_closable_amt=0,principal_paid=0,interest_paid=0,penalint_paid=0;
    boolean update_flag=false;
    Object trn_seq_object=null;
    AccountObject acc_object=null;
    masterObject.loansOnDeposit.LoanTransactionObject loan_object=null;
    AccountTransObject acc_trn=null;
    try
    {
        conn=getConnection();
        stmt = conn.createStatement();
        rs=stmt.executeQuery("select * from StdInst where si_no="+std_no+" ");
        if(rs.next()){
        	from_ac_type=rs.getString("fr_ac_ty");
        	from_ac_no=rs.getInt("fr_ac_no");
        	to_ac_type=rs.getString("to_ac_ty");
        	to_ac_no=rs.getInt("to_ac_no");
        	due_date=rs.getString("due_dt");
        	loan_option=rs.getInt("ln_opt");
        	std_exe_amount=rs.getDouble("amount");
        	adjusted_amount=rs.getDouble("amt_adj");
        	expiry_days=rs.getInt("expiry_days");
        	expiry_date=Validations.addDays(due_date,expiry_days);
        }
        if(Validations.dayCompare(date,expiry_date)==0 && expiry_days!=0){
        	update_count=updateDueDateForStdExec(std_no);
			if(update_count==0){
				return_values[8]="Update Error";
				ctx.setRollbackOnly();
			}
			else
				return_values[8]="SI Number Expired";
			return return_values;
        }
        if(from_ac_type!=null && to_ac_type!=null){
        	acc_object=commonlocal.getAccount(null,from_ac_type,from_ac_no,date);
        	if(acc_object==null){
        		return_values[8]="From Account Not Found";
        		return return_values;
        	}
        	else if(acc_object.getAccStatus()!=null && acc_object.getAccStatus().equalsIgnoreCase("C")){
        		return_values[8]="Frm Acc Closed";
        		return return_values;
        	}
        	if(acc_object.getFreezeInd().equalsIgnoreCase("T")){
        		return_values[8]="Frm Acc Freezed";
        		return return_values;
        	}
        	else{
        		rs=stmt.executeQuery("select min_bal from Modules where modulecode='"+from_ac_type+"'");
				if(rs.next())
					min_bal = rs.getDouble("min_bal");
				transfer_amt_available = (acc_object.getAmount()-min_bal); //  we are doing this in order to maintain a min balance in the account
				transfer_amt_available = Math.floor(transfer_amt_available);
				if(transfer_amt_available<=0){
					return_values[8]="No amount";
					return return_values;
				}
				
				rs=stmt.executeQuery("select lm.*,cm.sub_category from LoanMaster as lm, CustomerMaster as cm where lm.ac_type='"+to_ac_type+"' and lm.ac_no="+to_ac_no+" and lm.ve_tml is not null and lm.cid=cm.cid;");
				if(rs.next()){
					if(rs.getString("close_date")!=null){
						return_values[8]="To Acc Closed";
						return return_values;
					}
					sub_category=rs.getInt("sub_category");
				}
				else{
					return_values[8]="To Account Not Found";
            		return return_values;
				}
				
				
					interest_amt=loansondepositremote.getCurrentIntPay(to_ac_type,to_ac_no,date);
					other_charges=loansondepositremote.getOtherAmt(to_ac_type,to_ac_no,date);
					loan_balance=loansondepositremote.getCurrentPrBal(to_ac_type,to_ac_no,date);
					total_closable_amt=(interest_amt+other_charges+loan_balance);
					principal_overdue=loansondepositremote.getPrincipalOutstandings(to_ac_type,to_ac_no,date)+loan_balance;
					
				//loan_object=loansondepositremote.getPrincipalOutstandings(to_ac_type,to_ac_no,date);
				/*if(loan_object!=null){
					principal_overdue=loan_object.getPrincipalPayable()+loan_object.getPrincipalBalance();
				}*/
					
				if(loan_option==1){
					transaction_amt=(std_exe_amount-adjusted_amount); // towards total amt
				}
				else if(loan_option==2){
					transaction_amt=(std_exe_amount-adjusted_amount);// towards principal
					transaction_amt += (interest_amt+other_charges); // towards principal + (interest_amt+other_charges)
				}
				else if(loan_option==3){
					transaction_amt=(principal_overdue+interest_amt+other_charges);
					transaction_amt -= adjusted_amount;
				}
				
				if(transaction_amt<0) // because at any point trn_amt should not be negative
					transaction_amt=0;
				if(total_closable_amt<transaction_amt)
					transaction_amt=total_closable_amt;
				if(transfer_amt_available<transaction_amt)
					transaction_amt=transfer_amt_available;
				
				transaction_amt=Math.floor(transaction_amt);
				if(transaction_amt>0){
					loan_object=null;
					loan_object=new masterObject.loansOnDeposit.LoanTransactionObject();
											
					loan_object.setAccType(to_ac_type);
					loan_object.setAccNo(to_ac_no);
					loan_object.setTransactionDate(date);
					loan_object.setTranType("R");
					loan_object.setTransactionAmount(transaction_amt);
					loan_object.setTranMode("T");
					loan_object.setTranSou(tml);
					loan_object.setReferenceNo(0);
					loan_object.setTranNarration(from_ac_type+"  "+from_ac_no);
					loan_object.setCdind("C");
					loan_object.uv.setUserTml(tml);
					loan_object.uv.setUserId(user);
					loan_object.uv.setUserDate(date_time);
					loan_object.uv.setVerId(user);
					loan_object.uv.setVerTml(tml);
					loan_object.uv.setVerDate(date_time);
					
					acc_trn=new AccountTransObject();
					acc_trn.setAccType(from_ac_type);
					acc_trn.setAccNo(from_ac_no);
					acc_trn.setTransDate(date);
					acc_trn.setTransType("P");
					acc_trn.setTransAmount(transaction_amt);
					acc_trn.setTransMode("T");
					acc_trn.setCdInd("D");
					acc_trn.setTransNarr(to_ac_type+"  "+to_ac_no);
					acc_trn.setTransSource(tml);
					acc_trn.uv.setUserTml(tml);
					acc_trn.uv.setUserId(user);
					acc_trn.uv.setUserDate(date_time);
					acc_trn.uv.setVerTml(tml);
					acc_trn.uv.setVerId(user);
					acc_trn.uv.setVerDate(date_time);
					
					int a=commonlocal.storeAccountTransaction(acc_trn);
					if(a!=1){
						ctx.setRollbackOnly();
						return_values[8]="To Account update Error";
						return 	return_values;
					}
					
					try{
						trn_seq_object = loansondepositremote.recoverLDAccount(loan_object);
					}catch(RecordNotUpdatedException recn){recn.printStackTrace();trn_seq_object=null;}
					
					if(trn_seq_object==null){
						ctx.setRollbackOnly();
						return_values[8]="From Account update Error";
						return 	return_values;
					}
					else{
						trn_seq = Integer.parseInt(trn_seq_object.toString());
						rs=stmt.executeQuery("select * from LoanTransaction where ac_type='"+to_ac_type+"' and ac_no="+to_ac_no+" and trn_seq="+trn_seq+" ");
						if(rs.next()){
							principal_paid=rs.getDouble("pr_amt");
							interest_paid=rs.getDouble("int_amt")+rs.getDouble("extra_int");
							penalint_paid=rs.getDouble("penal_amt");
							othercharges_paid=rs.getDouble("other_amt");
							int_upto_date=rs.getString("int_date");
						}
						
						if(loan_option==1){
							if((transaction_amt+adjusted_amount)<std_exe_amount){
								complete_ind="F";
								adjusted_amount += transaction_amt;
							}
							else
								complete_ind="T";
						}
						else if(loan_option==2){
							if((principal_paid+adjusted_amount)<std_exe_amount){
								complete_ind="F";
								adjusted_amount += transaction_amt;
							}
							else
								complete_ind="T";
						}
						else if(loan_option==3){
							if((transaction_amt+adjusted_amount)<(principal_overdue+interest_amt+other_charges)){
								complete_ind="F";
								adjusted_amount += transaction_amt;
							}
							else
								complete_ind="T";
						}
						stmt.executeUpdate("insert into StdInstDone values ("+std_no+",'"+date+"','"+due_date+"',"+transaction_amt+","+principal_paid+","+interest_paid+","+penalint_paid+","+othercharges_paid+",'"+int_upto_date+"','"+complete_ind+"','"+user+"','"+tml+"','"+date_time+"')");
						update_flag=true;
					}
				}
				
				if(complete_ind.equalsIgnoreCase("T") || (Validations.dayCompare(date,expiry_date)==0 && expiry_days!=0)){
					update_count=updateDueDateForStdExec(std_no);
					if(update_count==0){
						return_values[8]="Update Error";
						ctx.setRollbackOnly();
					}
					else
						return_values[8]="Executed";
				}
				else if(update_flag){
					if(stmt.executeUpdate("update StdInst set amt_adj="+adjusted_amount+" where si_no="+std_no+" ")>0){
						return_values[8]="Partially Executed";
					}
					else{
						return_values[8]="Update Error";
						ctx.setRollbackOnly();
					}
				}
				
				if(complete_ind.equalsIgnoreCase("T") && checkForNextExec(std_no, date) ) {
					stdLDExec(std_no, user, tml, date, date_time);
				}
        	}
        }
        
    }catch(Exception exe){
    	exe.printStackTrace();
    	return_values[8]="Error";
    	ctx.setRollbackOnly();           
    }        
    finally{
        try{
            conn.close();
        }catch(SQLException e){e.printStackTrace();}
    }
    return return_values;   
}
public Hashtable getGLCodes(String date) {
	Statement stmt_actype=null,stmt_glcode;
    Connection conn=null;
    ResultSet rs_actype=null,rs_glcode;
    Hashtable hash=null;
    Vector ac_type=null;
	try {
		conn = getConnection();
		hash=new Hashtable();
		stmt_actype = conn.createStatement();
		stmt_glcode = conn.createStatement();
		
		/** Since for Share we maintain separate share params we need to 
		 * take the distinct ac_type from share master
		 **/
		rs_actype = stmt_actype.executeQuery("select distinct ac_type from ShareMaster"); 
		while(rs_actype.next()) {
			/**  Since all the share params are stored in ShareParam table instead of 
			 *   GLKeyParam we are taking it from ShareParam
			 **/
			rs_glcode = stmt_glcode.executeQuery("select distinct prm_gl_code from ShareParam  where ac_type='"+rs_actype.getString("ac_type")+"' order by prm_gl_code");
			ac_type = new Vector();
			while(rs_glcode.next()) {
				ac_type.add(rs_glcode.getString("prm_gl_code"));
			}
			/**
			 * The gl_codes associated with each ac_type is add to a vector and put
			 * in a hash table with the key as the ac_type so that it would be useful
			 * to display only the glcodes associated with the particular acytpe when
			 * that ac_type is selected. 
			 */
			hash.put(rs_actype.getString("ac_type"),ac_type);
		}
		
		/**
		 * Since rest of the actypes gl code is in Glkeyparam we are taking it from GLKeyParam
		 */
		rs_actype = stmt_actype.executeQuery("select distinct ac_type from GLKeyParam where ac_type not like '1001%'"); 
		while(rs_actype.next()) {
			/**  Since all the rest gl codes for other ac types  are stored in GLKeyParam 
			 * table we are taking it from GLKeyParam and all the active gl code are in GLMaster
			 * with corresponding date we are using the date to fecth the active glcodes on 
			 * the particular day
			 **/
			rs_glcode = stmt_glcode.executeQuery("select distinct gk.gl_code from GLKeyParam as gk,GLMaster as gm where gk.ac_type like '"+rs_actype.getString("ac_type")+"' and gk.gl_type = gm.gl_type and gk.gl_code=gm.gl_code and ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"')) order by gk.gl_code");
			ac_type = new Vector();
			while(rs_glcode.next()) {
				ac_type.add(rs_glcode.getString("gl_code"));
			}
			/**
			 * The gl_codes associated with each ac_type is add to a vector and put
			 * in a hash table with the key as the ac_type so that it would be useful
			 * to display only the glcodes associated with the particular acytpe when
			 * that ac_type is selected. 
			 */
			hash.put(rs_actype.getString("ac_type"),ac_type);
		}
			
		/**
		 * This one is to select all the active gl_codes on the given date and it should not 
		 * contain cash gl codes
		 */
		rs_glcode = stmt_glcode.executeQuery("select distinct gl_code from GLMaster as gm where ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))  and gl_code not like '%000' and gl_code not in(select  gl_code from GLKeyParam , Modules where modulecode like '1019%' and ac_type = modulecode) order by gl_code");
		ac_type = new Vector();
		while(rs_glcode.next()) {
			ac_type.add(rs_glcode.getString("gl_code"));
		}
		/**
		 * This on is added under the key ALL since when no ac type is selected we have to 
		 * dispaly these code 
		 */
		hash.put("ALL",ac_type);
		
	}catch(Exception exe) {
		exe.printStackTrace();
	} finally {
        try {
            conn.close();
        }catch(SQLException e){e.printStackTrace();}
    }
	return hash;
}

public Hashtable getGLCodesDesc(String date) {
	Statement stmt;
    Connection conn=null;
    ResultSet rs;
    Hashtable hash=null;
	try {
		conn = getConnection();
		hash=new Hashtable();
		stmt= conn.createStatement();
		
		/**
		 * Since share params sre stored in  shareparam table we are taking the 
		 * desc from share param table and adding it to the hash table with 
		 * gl code as key
		 */
		rs = stmt.executeQuery("select distinct prm_gl_code,prm_desc from ShareParam where prm_gl_code is not null and prm_desc is not null");
		while(rs.next()) {
			hash.put(rs.getString("prm_gl_code"),rs.getString("prm_desc"));
		}
		
		/**
		 * Since rest of the ac type gl codes are in gl master we are taking all the 
		 * active gl codes on the given date and adding the values to hash table with 
		 * gl codes as key
		 */
		rs = stmt.executeQuery("select distinct gl_code,gl_name from GLMaster where ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"')) and gl_code is not null and gl_name is not null");
		while(rs.next()) {
			hash.put(rs.getString("gl_code"),rs.getString("gl_name"));
		}
	}catch(Exception exe) {
		exe.printStackTrace();
	} finally {
        try {
            conn.close();
        }catch(SQLException e){e.printStackTrace();}
    }
	return hash;
}

public Hashtable getTransactionTypes() {
	Statement stmt_actype,stmt_trntype;
    Connection conn=null;
    ResultSet rs_actype,rs_trntype;
    Hashtable hash=null;
    Vector trn_type;
	try {
		conn = getConnection();
		hash=new Hashtable();
		stmt_actype= conn.createStatement();
		stmt_trntype= conn.createStatement();
		/**
		 * All the ac typoe  corrsponding trn type are stored in glpost table and so first
		 * we are taking all the distinc actype and for all the distinct acc tpes we are 
		 * adding to a vector and puting the vector along with its corresponding trn type
		 * to the hash table with ac type as key.
		 */
		rs_actype = stmt_actype.executeQuery("select distinct ac_type from GLPost");
		while(rs_actype.next()) {
			
			rs_trntype = stmt_trntype.executeQuery("select distinct trn_type from GLPost where ac_type like '"+rs_actype.getString("ac_type")+"'");
			trn_type = new Vector();
			while(rs_trntype.next()) {
				trn_type.add(rs_trntype.getString("trn_type"));
			}
			hash.put(rs_actype.getString("ac_type"),trn_type);
		}
		
	}catch(Exception exe) {
		exe.printStackTrace();
	} finally {
        try {
            conn.close();
        }catch(SQLException e){e.printStackTrace();}
    }
	return hash;
}


public String[] getCashGLCode() {
	Statement stmt;
    Connection conn=null;
    ResultSet rs;
    String[] cash_gl = new String[2];
    
	try {
		conn = getConnection();
		stmt= conn.createStatement();
		
		rs = stmt.executeQuery("select gl_type, gl_code from GLKeyParam , Modules where modulecode= '1019001' and ac_type = modulecode");
		int i = 0;
		if(rs.next()) {
			cash_gl[0] = rs.getString("gl_type");
			cash_gl[1] = rs.getString("gl_code");
		}
		
	}catch(Exception exe) {
		exe.printStackTrace();
	} finally {
        try {
            conn.close();
        }catch(SQLException e){e.printStackTrace();}
    }
	return cash_gl;
}

public String[][] getStdInstRecords(int array_int_si_no[])throws RemoteException{
	Connection conn=null;
	Statement stmt=null,stmt_to=null;
	ResultSet rs=null,rs_to=null;
	String data[][]=null;
	try{
		conn=getConnection();
		stmt=conn.createStatement();
		stmt_to=conn.createStatement();
		data=new String[array_int_si_no.length][9];
		
		int j=0;
		String to_accounts[][]=new String[array_int_si_no.length][2];
		String master_table=null;
		System.out.println("Array"+array_int_si_no);
		System.out.println("Array----10312---"+array_int_si_no.length);
		for(int i=0;i < array_int_si_no.length;i++){
			
			System.out.println(array_int_si_no[i] + "--------------------");
			//rs = stmt.executeQuery("select x.* from StdInst x where x.si_no in ("+array_int_si_no[i]+")");
			rs=stmt.executeQuery("select  x.si_no,x.fr_ac_ty,x.fr_ac_no,x.to_ac_ty,x.to_ac_no,x.amount,concat(IFNULL(cm.fname,' '),' ',IFNULL(cm.mname,' ' ),' ',IFNULL(cm.lname,' ')) as fromname from StdInst x,AccountMaster am,CustomerMaster cm where cm.cid=am.cid and  am.ac_no=x.fr_ac_no and am.ac_type=x.fr_ac_ty and x.si_no="+array_int_si_no[i]+"");
			//System.out.println("RS Valueeeeeeeeeeeeeeeee-----------"+rs);
			if(rs!=null){
			while(rs.next()){
				
				System.out.println(" in side while loop");
				data[i][0]=""+rs.getInt(1);
				data[i][1]=""+rs.getString(2);
				data[i][2]=""+rs.getInt(3);
				to_accounts[i][0]=""+rs.getString(4);
				System.out.println(to_accounts[i][0]);
				to_accounts[i][1]=""+rs.getInt(5);
				System.out.println(to_accounts[i][1]);
				data[i][3]=""+rs.getString("fromname");
				data[i][4]=""+rs.getString(4);
				data[i][5]=""+rs.getInt(5);;
				data[i][6]="";
				data[i][7]=""+rs.getFloat("amount");
				
						
			}
			System.out.println("Array----10337---");
			}
			rs.beforeFirst();
			if(rs.next()){
			if(to_accounts[i][0].startsWith("1004")){
				master_table="DepositMaster";
			}
			else if(to_accounts[i][0].startsWith("1007")||to_accounts[i][0].startsWith("1002")){
				master_table="AccountMaster";
			}
			else if(to_accounts[i][0].startsWith("1008")){
				master_table="LoanMaster";
			}
			else if(to_accounts[i][0].startsWith("1010")){
				master_table="LoanMaster";
			}
			System.out.println("master_table"+master_table);
			if(master_table!=null){
				rs_to=stmt_to.executeQuery("select concat(IFNULL(cm.fname,' '),' ',IFNULL(cm.mname,' ' ),' ',IFNULL(cm.lname,' ')) as toname from "+master_table+" dm,CustomerMaster cm  where cm.cid=dm.cid and  dm.ac_no="+ to_accounts[i][1]+" and dm.ac_type="+ to_accounts[i][0]+"");
				while(rs_to.next()){
					data[i][6]=rs_to.getString(1);
					System.out.println("data[i][6]"+data[i][6]);
				}
			}
		}
			
		}
			
	}catch(Exception e){
		//e.printStackTrace();
        System.out.println(e.getMessage());
	
	}finally{
		
		try{
			
			conn.close();
		}catch(SQLException sql){
			
		}
	}
	
	return data;
}



public AccountTransObject[] getSBCATranSummary(String modcode,String fromdate,String todate,int accno,String string_query) throws RemoteException,RecordsNotFoundException
{
	AccountTransObject accountTransObject[]=null;
	UserVerifier uv=null;
	Connection conn=null;
	try
	{
		conn=getConnection();
		ResultSet res=null;
		Statement stmt=conn.createStatement();
		System.out.println(fromdate+" DATE "+todate);
		fromdate=Validations.convertYMD(fromdate);
		todate=Validations.convertYMD(todate);
		System.out.println("fromdate=="+fromdate);
		System.out.println("todate=="+todate);
		System.out.println("modecode "+modcode);
		System.out.println("accno "+accno);
		System.out.println("string_query"+string_query);
		
		if(accno==0)
		{
			if(string_query==null)
				res=stmt.executeQuery("select at.*, concat(cm.fname,' ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name from AccountTransaction at,AccountMaster am,CustomerMaster cm where am.ac_type='"+modcode+"'  and am.ac_type = at.ac_type and  am.ac_no=at.ac_no and am.cid=cm.cid and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' order by at.ac_no;");
			else if(string_query!=null)
				res=stmt.executeQuery("select at.*, concat(cm.fname,' ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name from AccountTransaction at,AccountMaster am,CustomerMaster cm where am.ac_type='"+modcode+"'  and am.ac_type = at.ac_type and  am.ac_no=at.ac_no and am.cid=cm.cid and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and ("+string_query+") order by at.ac_no;");
		}
		//For Indivisual and Institute records
		else if(accno==10 || accno==11){
			if(accno==10){
				if(string_query==null)
					res=stmt.executeQuery("select at.*, concat(cm.fname,' ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name from AccountTransaction at,AccountMaster am,CustomerMaster cm where am.ac_type='"+modcode+"'  and am.ac_type = at.ac_type and  am.ac_no=at.ac_no and am.cid=cm.cid and cm.custtype=0 and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' order by at.ac_no");
				else if(string_query!=null)
					res=stmt.executeQuery("select at.*, concat(cm.fname,' ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name from AccountTransaction at,AccountMaster am,CustomerMaster cm where am.ac_type='"+modcode+"'  and am.ac_type = at.ac_type and  am.ac_no=at.ac_no and am.cid=cm.cid and cm.custtype=0 and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and ("+string_query+") order by at.ac_no");
			}
			else if(accno==11){
				if(string_query==null)
					res=stmt.executeQuery("select at.*, concat(cm.fname,' ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name from AccountTransaction at,AccountMaster am,CustomerMaster cm where am.ac_type='"+modcode+"'  and am.ac_type = at.ac_type and  am.ac_no=at.ac_no and am.cid=cm.cid and cm.custtype=1 and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' order by at.ac_no");
				else if(string_query!=null)
					res=stmt.executeQuery("select at.*, concat(cm.fname,' ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name from AccountTransaction at,AccountMaster am,CustomerMaster cm where am.ac_type='"+modcode+"'  and am.ac_type = at.ac_type and  am.ac_no=at.ac_no and am.cid=cm.cid and cm.custtype=1 and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' and ("+string_query+") order by at.ac_no");
			}
		}
		else
		{
			if(string_query==null)
				res=stmt.executeQuery("select at.*, concat(cm.fname,' ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name from AccountTransaction at,AccountMaster am,CustomerMaster cm where am.ac_type='"+modcode+"'  and am.ac_type = at.ac_type and  am.ac_no=at.ac_no and at.ac_no="+accno+" and am.cid=cm.cid and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"' order by at.ac_no");
			else if(string_query!=null)
				res=stmt.executeQuery("select at.*, concat(cm.fname,' ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name from AccountTransaction at,AccountMaster am,CustomerMaster cm where am.ac_type='"+modcode+"'  and am.ac_type = at.ac_type and  am.ac_no=at.ac_no and at.ac_no="+accno+" and am.cid=cm.cid and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"'and '"+todate+"'and ("+string_query+") order by at.ac_no");
		
		}
		res.last();
		System.out.println("no of records "+res.getRow());
		if(res.getRow()!=0)
		{
			accountTransObject=new AccountTransObject[res.getRow()];
			res.beforeFirst();
			int p=0;
			while(res.next())
			{
				accountTransObject[p]=new AccountTransObject();
				accountTransObject[p].setAccNo(res.getInt("ac_no"));
				accountTransObject[p].setCdInd(res.getString("cd_ind"));
				accountTransObject[p].setTransMode(res.getString("cd_ind"));
				accountTransObject[p].setTransAmount(res.getDouble("trn_amt"));
				accountTransObject[p].setTransNarr(res.getString("trn_narr"));
				accountTransObject[p].setRef_No(res.getInt("ref_no"));
				accountTransObject[p].setName(res.getString("name"));
				//renuka added
				uv=new UserVerifier();
				uv.setUserId(res.getString("de_user"));
				uv.setVerId(res.getString("ve_user"));
				accountTransObject[p].setUv(uv);
				p++;
			}
		}
		else 
			throw  new RecordsNotFoundException();
	}
	catch(RecordsNotFoundException ex)
	{
		throw ex;
	}
	
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	finally
	{
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return accountTransObject;
}
public String[][] getGlCodesNames() throws SQLException
{
    String[][] glname=null;
    Connection conn=null;
    int row=0;
    
    try
    {
        conn=getConnection();
        Statement stmt= conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rs=stmt.executeQuery("select gl_code,gl_name from GLMaster ");
        System.out.println("rs  in the cashbean is==="+rs);
        rs.last();
        int count=rs.getRow();
        System.out.println("rs.getRow() in the cashbean is==="+count);
        glname= new String[count][2];
        rs.beforeFirst();
        while(rs.next())
         
        {
        	glname[row][0]=rs.getString(1);
        	glname[row][1]=rs.getString(2);
        	row++;
        
        }
    }
    catch(SQLException exception)
    {
        exception.printStackTrace();
    }
    finally
    {
        conn.close();
    }
    System.out.println("return in bean--------"+glname);
    return glname;
}

public String getAccNo(String acc_type) throws SQLException,RemoteException
{
	 String ac_no=null;
	    Connection conn=null;
	    int row=0;
	    
	    try
	    {
	        conn=getConnection();
	        Statement stmt= conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	        ResultSet rs=stmt.executeQuery("select ac_no from AccountMaster where ac_status='o'&& ac_type="+acc_type+" ");
	        System.out.println("rs  in the cashbean is==="+rs);
	        rs.last();
	        int count=rs.getRow();
	        System.out.println("rs.getRow() in the cashbean is==="+count);
	        
	        rs.beforeFirst();
	        while(rs.next())
	         
	        {
	        	ac_no = rs.getString(1);
	        
	        }
	    }
	    catch(SQLException exception)
	    {
	        exception.printStackTrace();
	    }
	    finally
	    {
	        conn.close();
	    }
	    System.out.println("return in bean--------"+ac_no);
	    return ac_no;
}


public String Max_AccountNo(String ac_type)throws SQLException,RemoteException
{
        String ac_no=null;
	    Connection conn=null;
	    int row=0;
	    
	    try
	    {
	        conn=getConnection();
	        Statement stmt= conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	        ResultSet rs=stmt.executeQuery("select max(ac_no)from AccountMaster where ac_type='"+ac_type+"' ");
	        System.out.println("rs  in the cashbean is==="+rs);
	        rs.last();
	        int count=rs.getRow();
	        System.out.println("rs.getRow() in the cashbean is==="+count);
	        
	        rs.beforeFirst();
	        while(rs.next())
	         
	        {
	        	ac_no = rs.getString(1);
	        
	        }
	    }
	    catch(SQLException exception)
	    {
	        exception.printStackTrace();
	    }
	    finally
	    {
	        conn.close();
	    }
	    System.out.println("return in bean--------"+ac_no);
	    return ac_no;
}
public String[] getAccountGlcodes1(String ac_type,String gl_type,int ac_no,String date) 
{
    String[] array_glcodes=null;
    Connection conn=null;
    System.out.println("inside accountglcodes");
    System.out.println(" ac type is"+ac_type);
    System.out.println(" gl type is"+gl_type);
    System.out.println(" ac_no is"+ac_no);
    System.out.println(" date is"+date);
    
    ResultSet rs=null;
    ResultSet rs_share=null;
    int prm_code=0;
    int mem_cat=0;
        try
        {
            conn=getConnection();
            Statement stmt=conn.createStatement();
            //ResultSet rs=stmt.executeQuery("select distinct gl_code from GLPost where ac_type='"+ac_type+"' order by gl_code");
            //ResultSet rs=stmt.executeQuery("select distinct glm.gl_code from GLKeyParam as glk, GLMaster as glm where glk.ac_type='"+ac_type+"' and glk.gl_type='"+gl_type+"' and glk.gl_code=glm.gl_code;");
            if(ac_type.startsWith("1001"))
            {
            	rs_share=stmt.executeQuery("select distinct mem_cat,sh_ind from ShareMaster where ac_type='"+ac_type+"' and ac_no='"+ac_no+"' ");
            	if(rs_share.next())
            	{
            		prm_code=(rs_share.getString("sh_ind").equalsIgnoreCase("P")?5:6);
            		mem_cat=(rs_share.getInt("mem_cat"));
            	}
            	rs=stmt.executeQuery("select distinct prm_gl_code from ShareParam sp,GLKeyParam glk, GLMaster glm where glk.ac_type='"+ac_type+"' and sp.ac_type=glk.ac_type and glk.gl_type='"+gl_type+"' and glk.gl_code=glm.gl_code and mem_cat="+mem_cat+" and prm_code="+prm_code+" and ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))");
            }
            else
            	rs=stmt.executeQuery("select distinct glm.gl_code from GLKeyParam as glk, GLMaster as glm where glk.ac_type='"+ac_type+"' and glk.gl_type='"+gl_type+"' and glk.gl_code=glm.gl_code and ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))");
            
            rs.last();
            if(rs.getRow()!=0)
                array_glcodes=new String[rs.getRow()];
            System.out.println("length"+rs.getRow());
            rs.beforeFirst();
            int i=0;
            while(rs.next())
            {
                array_glcodes[i]=rs.getString(1);
                i++;
            }
        }catch(Exception exception){exception.printStackTrace();}
        
        finally
        {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for(int i=0;i<array_glcodes.length;i++)
        {
        System.out.println("Array codes in Bean--------"+array_glcodes[i]);
        }
    return array_glcodes;
}
}
    	
    	

    	
