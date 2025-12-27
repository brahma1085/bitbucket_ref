
 /**
  * Created on Mar 15, 2000
  *
  * TODO To change the template for this generated file go to
  * Window - Preferences - Java - Code Style - Code Templateso
  */
 package clearingServer;
     
 import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import loanServer.LoanLocal;
import loanServer.LoanLocalHome;
import loansOnDepositServer.LoansOnDepositHome;
import loansOnDepositServer.LoansOnDepositRemote;
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
import masterObject.frontCounter.AccountMasterObject;
import masterObject.frontCounter.AccountTransObject;
import masterObject.frontCounter.PayOrderObject;
import masterObject.general.AccSubCategoryObject;
import masterObject.general.AccountObject;
import masterObject.general.BranchObject;
import masterObject.general.DoubleFormat;
import masterObject.loans.LoanTransactionObject;
import masterObject.lockers.RentTransObject;
import masterObject.share.ShareMasterObject;
import masterObject.termDeposit.DepositMasterObject;
import shareServer.ShareLocal;
import shareServer.ShareLocalHome;
import termDepositServer.TermDepositLocalHome;
import termDepositServer.TermDepositLocalRemote;
import cashServer.CashLocal;
import cashServer.CashLocalHome;

import commonServer.CommonLocal;
import commonServer.CommonLocalHome;
import commonServer.GLTransObject;

import exceptions.DateFormatException;
import exceptions.InsufficientBalanceException;
import exceptions.RecordNotInsertedException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;
import exceptions.RecordsNotInsertedException;
import frontCounterServer.FrontCounterLocal;
import frontCounterServer.FrontCounterLocalHome;
import general.Validations;
 /**      
  * @author user
  *
  * TODO To change the template for this generated type comment go to
  * Window - Preferences - Java - Code Style - Code Templates
  */
 public class ClearingBean implements SessionBean {

	 	private DataSource ds=null;
	 	private InitialContext ctx=null;
	 	
	 	private FrontCounterLocal front=null;
	 	private FrontCounterLocalHome frontHome;
	 	
	 	private CommonLocal comm=null;
	 	private CommonLocalHome commonHome;
	 	
	 	
	 	
	 	private LoanLocal loanremote=null;
	 	private LoanLocalHome loanHome;
	 	
	 	private CashLocalHome cashhome;
	 	private CashLocal cash=null;
	 	
	 	
	 	
	 	private TermDepositLocalHome termhome;
	 	private TermDepositLocalRemote term=null;
	 	
	 	private LoansOnDepositHome loanondop ;
	 	private LoansOnDepositRemote loanonde =null;
	 	private LoanTransactionObject loansOnDeposit;
	 	private ShareLocalHome sharehome;
	 	private ShareLocal share=null;
	 	
	 	
	 	SessionContext sessionContext;
	 	
	 	//static CommonImpl comm;
	 	//CommonBean comm;
	 	
	 	public void ejbCreate(){
	 		try {
	 			ctx = new InitialContext();
	 			commonHome = (CommonLocalHome) ctx.lookup("COMMONLOCALWEB");
	 			ds = (DataSource)ctx.lookup("java:MySqlDS");
	 			
	 			sharehome = (ShareLocalHome) ctx.lookup("SHARELOCALWEB");
	 			loanHome = (LoanLocalHome) ctx.lookup("LOANSLOCALWEB");
	 			termhome =(TermDepositLocalHome)ctx.lookup("TERMDEPOSITLOCALWEB");
	 			cashhome =(CashLocalHome)ctx.lookup("CASHLOCALWEB");
	 			frontHome =(FrontCounterLocalHome)ctx.lookup("FRONTCOUNTERLOCALWEB");
	 			loanondop = (LoansOnDepositHome)ctx.lookup("LOANSONDEPOSITWEB");
	 			
	 			
	 		} catch (NamingException e) {
	 			e.printStackTrace();
	 		} 
	 	}
	 	
	 	
	 	
	 	
	 	public void setSessionContext(SessionContext arg0) throws EJBException,RemoteException {
	 		sessionContext=arg0;
	 	}
	 	public void ejbRemove() throws EJBException, RemoteException {}
	 	public void ejbActivate() throws EJBException, RemoteException {
	 		
	 		try 
	 		{
	 			ctx = new InitialContext();
	 			commonHome = (CommonLocalHome) ctx.lookup("COMMONLOCALWEB");
	 			ds = (DataSource)ctx.lookup("java:MySqlDS");
	 			loanHome = (LoanLocalHome) ctx.lookup("LOANLOCALWEB");
	 			
	 			sharehome = (ShareLocalHome) ctx.lookup("SHARELOCALWEB");
	 			cashhome=(CashLocalHome)ctx.lookup("CASHLOCALWEB");
	 			
	 			
	 		} 
	 		catch (NamingException e) 
	 		{
	 			e.printStackTrace();
	 		} 
	 	}
	 	public void ejbPassivate() throws EJBException, RemoteException {
	 		System.out.println("passivating");
	 	}
	 		
	 	public String getToday()
	 	{
	 	    String date = null;
	 		GregorianCalendar cal=new GregorianCalendar();
	 		try{
	             date = Validations.checkDate(cal.get(Calendar.DATE)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR));
	             System.out.println("date"+date);
	         } catch (DateFormatException e) {
	            e.printStackTrace();
	         }
	         return date;
	 	}
	 	
	 	private Connection getConnection() throws SQLException{
	 		Connection conn = ds.getConnection("root","");
	 		if(conn == null)
	 			throw new SQLException();
	 		return conn;
	 	}
	 	
	 	
	 	 public String[][] getClgAdminTable(String table_name) throws RecordsNotFoundException
		 {
		 	String table_data[][]=null;
		 	Connection conn=null;
			ResultSet rs=null;
			ResultSetMetaData rs_meta_data=null;
			Statement stmt=null;
			try{
				conn=getConnection();
				stmt=conn.createStatement();
				
				System.out.println(" clg admin table "+table_name);
				if(table_name!=null){
					rs = stmt.executeQuery("select * from "+table_name);
					
					//rs.last();
					if(rs!=null)
						rs_meta_data = rs.getMetaData();
					else 
						throw new RecordsNotFoundException();
					if(rs!=null && rs_meta_data!=null && rs.last())
						table_data = new String[rs.getRow()][rs_meta_data.getColumnCount()];
					
					rs.beforeFirst();
					
					int j=0;
					if(rs.next()){
						for(int i=0;i<table_data.length;i++){
							
								for(j=0;j<table_data[i].length;j++){
									table_data[i][j] = rs.getString(j+1);
									//System.out.println(table_data[i][j]);
								}
							rs.next();
							
						}
					}
				}
			}
			catch(Exception e){
				sessionContext.setRollbackOnly();
				e.printStackTrace();
			}
			finally{
				try{                
					conn.close();
				}catch(Exception exception){exception.printStackTrace();}
			}
		 	return table_data;
		 }
		
	 	
	 	/*public void insertUpdateAdmin(String table_name,String arr_table[][])
	 	{
	 				
	 	}*/
	 	
	 	
	 	
	 	public double CalcPoCommission(String d_ac_type,int d_ac_no,String cr_ac_type ,double amount,String de_user,String de_tml,String de_date)
	 	{
	 		double comm_amt=0.00;
	 		 
	 		try
			{ 	
	 			
	 		 	String custtype=null;
				int po_desc=0;
				try{
				cash=cashhome.create();
				front=frontHome.create();
				comm=commonHome.create();
			
				}
				catch(CreateException cr)
				{
					cr.printStackTrace();
				}
				System.out.println("<----- calling cash ----->");
				
				custtype=cash.getCustSubCat(d_ac_type,String.valueOf(d_ac_no));
				
				AccSubCategoryObject [] cat_arr=comm.getAccSubCategories(0);
				if(cat_arr !=null)
				{
					for(int i=0;i<cat_arr.length;i++)
					{
						if(custtype.equalsIgnoreCase(cat_arr[i].getSubCategoryDesc()))
						{	
							po_desc=cat_arr[i].getSubCategoryCode();
							break;
						}
					}
				}
				else 
					throw (new SQLException());
				
				AccSubCategoryObject [] cat_arr1=comm.getAccSubCategories(1);
				if(cat_arr !=null)
				{
					for(int i=0;i<cat_arr1.length;i++)
					{
						if(custtype.equalsIgnoreCase(cat_arr[i].getSubCategoryDesc()))
						{	
							po_desc=cat_arr[i].getSubCategoryCode();
							break;
						}
					}
				}
				else
					throw (new SQLException());
				
				System.out.println("po_desc"+"  "+po_desc);
				
				 comm_amt=front.getCommission(cr_ac_type,po_desc,amount,de_date);
				
						}catch(SQLException e)
			{
				e.printStackTrace();
			}
			return comm_amt; 
		 	   
	 	}
	 	
	 	public double getLoanDetails(double amt,String acc_type,int acc_no,String user_id,String user_tml,String date)
	 	{
	 	    Connection conn=null;
	 	    double total_amt=0.00d;
	 	    try
	 	    {
	 	    loanremote=loanHome.create();
	 	    comm=commonHome.create();
	 	    }
	 	    catch(CreateException e)
	 	    {
	 	        e.printStackTrace();
	 	    }
	 	    try
	 	    {
	 	        ResultSet rs=null;
	 	        
	 	        conn=getConnection();
	 	       Statement  stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 	      String sys_date=date;
	 	    System.out.println(sys_date);
	 	       rs=stmt.executeQuery("select int_amt,penal_amt,other_charges,loan_balance from LoanRecoveryDetail where ac_type='"+acc_type+"' and ac_no="+acc_no+" and processing_date='"+sys_date+"' ");
	 	       if(rs.next())
	 	       /*String date=rs.getString("processing_date");
	 	       String sys_date=comm.getSysDate();
	 	       System.out.println(date);
	 	       
	 	      */
	 	      // if(date.equals(sys_date))
	 	       {
	 	           
	 	           System.out.println("first case");
	 	           total_amt= rs.getDouble("int_amt")+rs.getDouble("penal_amt")+rs.getDouble("other_charges")+rs.getDouble("loan_balance");
	 	          
	 	       }
	 	       else
	 	       {
	 	           
	 	           System.out.println("2nd case");
	 	           ResultSet res=stmt.executeQuery("select sub_category from LoanMaster lm,CustomerMaster cm where ac_type='"+acc_type+"' and ac_no="+acc_no+" and lm.cid=cm.cid");
	 	           if(res.next())
	 	           {
	 	           int sub_category=res.getInt("sub_category");
	 	           
	 	           loanremote.postRecoveryDetails(acc_type,acc_no,sys_date,sub_category,1, user_id, user_tml);
	 	           ResultSet  rs1=stmt.executeQuery("select int_amt,penal_amt,other_charges,loan_balance from LoanRecoveryDetail where ac_type='"+acc_type+"' and ac_no="+acc_no+" and processing_date='"+sys_date+"'");
	 		       
	 		       if(rs1.next())
	 		       {
	 		           total_amt= rs1.getDouble("int_amt")+rs1.getDouble("penal_amt")+rs1.getDouble("other_charges")+rs1.getDouble("loan_balance");
	 		           
	 		       }
	 	           }
	 	       }
	 	 	  	        
	 	 	  	        
	 	    }
	 	    catch(SQLException e)
	 	    {
	 	        e.printStackTrace();
	 	    }
	 	    finally
	 	    {
	 	        try
	 	        {
	 	        conn.close();
	 	    }
	 	        catch(SQLException ae)
	 	        {
	 	            ae.printStackTrace();
	 	        }
	 	   
	 	}
	 	return total_amt;
	 	}
	 	
	 	public void getFromFile(String file_name)
	 	{
	 	    Connection conn=null;
	 	   
	 	    try
	 	    {
	 	        conn=getConnection();
	 	 	  	        Statement  stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 	 	  	        System.out.println(file_name);
	 	      int i= stmt.executeUpdate("LOAD DATA LOCAL INFILE '"+file_name+"' INTO TABLE Dispatched_Cheque FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\r\n' "); 
	 	          if(i==0){
	 	        	  sessionContext.setRollbackOnly();
	 	              throw new RecordNotInsertedException();
	 	          }
	 	          else
	 	              System.out.println("Successfully Upload");
	 	    }
	 	    catch(Exception ae)
	 	    {
	 	        ae.printStackTrace();
	 	    }
	 	}
	 	
	 	public int storeGLTransaction(GLTransObject trnobj)
	 	{
	 		Connection conn=null;
	 		try{
	 			
	 			conn = getConnection();
	 			PreparedStatement pstmt = conn.prepareStatement("insert into GLTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	 			pstmt.setString(1,trnobj.getTrnDate());
	 			pstmt.setString(2,trnobj.getGLType());
	 			pstmt.setString(3,trnobj.getGLCode());
	 			pstmt.setString(4,trnobj.getTrnMode());
	 			pstmt.setDouble(5,trnobj.getAmount());
	 			pstmt.setString(6,trnobj.getCdind());
	 			pstmt.setString(7,trnobj.getAccType());
	 			pstmt.setString(8,trnobj.getAccNo());
	 			pstmt.setInt(9,trnobj.getTrnSeq());
	 			pstmt.setString(10,trnobj.getTrnType());
	 			pstmt.setInt(11,trnobj.getRefNo());
	 			pstmt.setString(12,trnobj.getVtml());
	 			pstmt.setString(13,trnobj.getVid());
	 			pstmt.setString(14,trnobj.getVDate());
	 			int a=pstmt.executeUpdate();
	 			if(a==0){
	 				sessionContext.setRollbackOnly();
	 				throw new SQLException();
	 			}
	 			return a;
	 			
	 		}catch(SQLException e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}	
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e1) {
	 				e1.printStackTrace();
	 			}
	 		}
	 		return 0;
	 	}
	 	
	 	public Object getMailChg(String column)
	 	{
	 		Object obj= null;
	 		Connection conn=null;
	 		ResultSet rs=null;
	 		try
	 		{
	 		conn= getConnection();
	 		Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 		rs=stmt.executeQuery("select "+column+" from Mailing_Charges");
	 		while(rs.next())
	 		{
	 		
	 		obj=rs.getObject(1);
	 		}
	 		
	 		}
	 		catch(Exception e)
	 		{
	 			e.printStackTrace();
	 		}
	 		finally{
	 			try{		    	
	 				conn.close();
	 			}catch(Exception exception){exception.printStackTrace();}
	 		}
	 	return obj;
	 	}
	 	
	 	
	 	public BounceFineObject[] retrieveBounceFine() throws RecordsNotFoundException
	 	{
	 		ResultSet rs=null;
	 		BounceFineObject[] bf=null;
	 		Connection conn=null;
	 		try
	 		{
	 			conn = getConnection();
	 			Statement  stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			//rs=stmt.executeQuery("Select * from Bounce_Fine");
	 			rs=stmt.executeQuery("Select bf.*,m.moduleabbr from BounceFine bf,Modules m where acc_type=modulecode");
	 			rs.last();
	 			if(rs.getRow()>0)
	 			{
	 				int i=0;
	 				bf = new BounceFineObject[rs.getRow()];
	 				rs.beforeFirst();
	 				while(rs.next())
	 				{
	 					bf[i]=new BounceFineObject();
	 					bf[i].setBounceCode(rs.getInt("b_code"));
	 					bf[i].setAccountType(rs.getString("m.moduleabbr"));
	 					bf[i].setFine(rs.getDouble("fine"));
	 					bf[i].setReturnFine(rs.getDouble("return_fine"));
	 					if(rs.getString("mail_chg") != null)
	 						bf[i].setMailingChg(rs.getDouble("mail_chg"));
	 					if(rs.getString("sms_chg") != null)	
	 						bf[i].setSmsChg(rs.getDouble("sms_chg"));
	 					if(rs.getString("email_chg") != null)	
	 						bf[i].setEmailChg(rs.getDouble("email_chg"));
	 					bf[i].setDe_tml(rs.getString("de_tml"));
	 					bf[i].setDe_user(rs.getString("de_user"));
	 					bf[i].setDe_date(rs.getString("de_date"));//    
	 					
	 					i++;	
	 				}
	 			}
	 			else
	 				throw new RecordsNotFoundException();
	 		}catch(SQLException m){
	 			m.printStackTrace();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return bf;
	 	} 
	 
	 	 public void setClgAdminTable(int table_name,String table_data[][]) throws RecordNotInsertedException
		 {
	 	 	Connection conn=null;
	 		ResultSet rs=null;
	 		ResultSet rs1 = null;
			PreparedStatement pstmt=null;
			Statement stmt=null;
			Statement stmt1= null;
			Statement stmt2= null;
			int row_del = 0;
			try{
				
				conn=getConnection();
				stmt=conn.createStatement();
				stmt1=conn.createStatement();
				stmt2=conn.createStatement();
				if(table_name == 2)
				 row_del = stmt.executeUpdate("delete from BankMaster");
				else if(table_name == 1)
					row_del=stmt.executeUpdate("delete from BranchMaster where br_code> 1000  ");
				else if(table_name == 3)
					row_del=stmt.executeUpdate("delete from BranchMaster where br_code< 1000  ");
				else if(table_name == 5)
					row_del=stmt.executeUpdate("delete from ReasonMaster ");
				else if(table_name == 4)
					row_del=stmt.executeUpdate("delete from CompanyMaster ");
				else if(table_name == 6)
					row_del=stmt.executeUpdate("delete from ReasonLink ");
				else if(table_name == 7)
					row_del=stmt.executeUpdate("delete from BounceFine ");
				else if(table_name == 8)
					row_del=stmt.executeUpdate("delete from DiscountCharges ");
				
				if(row_del >0)
				{
					if(table_name == 2)
					{				
						pstmt=conn.prepareStatement("insert into BankMaster values(?,?,?,?,?,?)");
						
						System.out.println(" first array length " + table_data.length);
						System.out.println(" second array length " + table_data[0].length);
						for(int i=0;i<table_data.length;i++)
						{
							int k =1;
							for(int j=0;j<table_data[i].length;j++)
							{
							
								
								if(j==0) 		
									pstmt.setInt(k,Integer.parseInt(table_data[i][j]));
								else
									pstmt.setString(k,table_data[i][j]);	
								
								k++;
								
							}
							int coun  =pstmt.executeUpdate();
					}
						
					}
					else if(table_name == 3){
						
						
						for(int i=0;i<table_data.length;i++){
							System.out.println(table_data[i][0]+"br_code values"+ table_data[i][6]+"end");
							rs1 = stmt2.executeQuery("select modulecode from Modules where moduleabbr = '"+table_data[i][4] +"' and modulecode not like '____000'");
							if(rs1.next()){
								
								int value = stmt1.executeUpdate("insert into BranchMaster(br_code,br_name,br_shnm,address,br_ac_type,br_ac_no,computerised,br_type,de_tml,de_user,de_date) values("+ table_data[i][0]+",'"+ table_data[i][1]+"','"+ table_data[i][2]+"','"+ table_data[i][3]+"','"+ rs1.getString("modulecode") +"',"+ table_data[i][5]+",'"+table_data[i][6]+"','"+ table_data[i][7]+"','"+ table_data[i][8]+"','"+ table_data[i][9]+"','"+ table_data[i][10]+"')");
								System.out.println(" the updated value is " + value);
							}
							else{
								sessionContext.setRollbackOnly();
								throw new RecordNotInsertedException();
							}
						}
					}
					
					else if(table_name == 1){
						
						//pstmt=conn.prepareStatement("insert into BranchMaster1 values(?,?,?,?,?,?,?,?,?,?,?)");
						
						for(int i=0;i<table_data.length;i++){
							System.out.println(table_data[i][0]+"br_code values");
						
							rs1 = stmt2.executeQuery("select modulecode from Modules where moduleabbr = '"+table_data[i][4] +"' and modulecode not like '____000'");
							if(rs1.next()){
							 stmt1.executeUpdate("insert into BranchMaster(br_code,br_name,br_shnm,address,br_ac_type,br_ac_no,br_type,de_tml,de_user,de_date) values("+ table_data[i][0]+",'"+ table_data[i][1]+"','"+ table_data[i][2]+"','"+ table_data[i][3]+"','"+ rs1.getString("modulecode")+ "',"+ table_data[i][5]+",'"+ table_data[i][6]+"','"+ table_data[i][7]+"','"+ table_data[i][8]+"','"+ table_data[i][9]+"')");
							}
							else{
								sessionContext.setRollbackOnly();
								throw new RecordNotInsertedException();
							}
						}
					}
					else if(table_name == 4){
						
						
						for(int i=0;i<table_data.length;i++){
							System.out.println(table_data[i][0]+"--"+table_data[i][1]+"--"+ table_data[i][2]+"--"+table_data[i][3]+"----"+table_data[i][4]+"----"+table_data[i][5]+"----"+table_data[i][6]+"----"+table_data[i][7]+"----"+table_data[i][8]+"end");
							rs1 = stmt2.executeQuery("select modulecode from Modules where moduleabbr = '"+table_data[i][2] +"' and modulecode not like '____000'");
							if(rs1.next()){
								
								int value = stmt1.executeUpdate("insert into CompanyMaster values("+ table_data[i][0]+",'"+ table_data[i][1]+"','"+ rs1.getString("modulecode")+"',"+ table_data[i][3]+",'"+ table_data[i][4] +"','"+ table_data[i][5]+"','"+table_data[i][6]+"','"+ table_data[i][7]+"','"+ table_data[i][8]+"','"+ table_data[i][9]+"')");
								System.out.println(" the updated value is " + value);
							}
							else{
								sessionContext.setRollbackOnly();
								throw new RecordNotInsertedException();
							}
						}
					}
					
					else if (table_name == 5){
						
						for(int i=0;i<table_data.length;i++){
							System.out.println(table_data[i][0]+"---"+table_data[i][1]+"---"+table_data[i][2]+"---"+table_data[i][3]+"---"+table_data[i][3]);
							stmt1.executeUpdate("insert into ReasonMaster values("+ table_data[i][0]+",'"+table_data[i][1]+"','"+table_data[i][2]+"','"+table_data[i][3]+"','"+table_data[i][4]+"')");
						}
						
					}
					else if (table_name == 6){
						
						for(int i=0;i<table_data.length;i++){
							System.out.println(table_data[i][0]+"---"+table_data[i][1]+"---"+table_data[i][2]+"---"+table_data[i][3]+"---"+table_data[i][3]);
							stmt1.executeUpdate("insert into ReasonLink values("+ table_data[i][0]+",'"+table_data[i][1]+"',"+table_data[i][2]+",'"+table_data[i][3]+"','"+table_data[i][4]+"','"+table_data[i][5]+"')");
						}
						
					}
					else if(table_name == 7){
						
						
						for(int i=0;i<table_data.length;i++){
							System.out.println(table_data[i][0]+"br_code values"+ table_data[i][6]+"end");
							rs1 = stmt2.executeQuery("select modulecode from Modules where moduleabbr = '"+table_data[i][1] +"' and modulecode not like '____000'");
							if(rs1.next()){
								
								int value = stmt1.executeUpdate("insert into BounceFine values("+ table_data[i][0]+",'"+ rs1.getString("modulecode")+"',"+ table_data[i][2]+","+ table_data[i][3]+","+ table_data[i][4] +","+ table_data[i][5]+","+table_data[i][6]+",'"+ table_data[i][7]+"','"+ table_data[i][8]+"','"+ table_data[i][9]+"')");
								System.out.println(" the updated value is " + value);
							}
							else{
								sessionContext.setRollbackOnly();
								throw new RecordNotInsertedException();
							}
						}
					}
					else if(table_name == 8){
						
						
						for(int i=0;i<table_data.length;i++){
							System.out.println(table_data[i][0]+"--"+table_data[i][1]+"--"+ table_data[i][2]+"--"+table_data[i][3]+"----"+table_data[i][4]+"----"+table_data[i][5]+"----"+table_data[i][6]+"----"+table_data[i][7]+"----"+table_data[i][8]+"end");
							rs1 = stmt2.executeQuery("select modulecode from Modules where moduleabbr = '"+table_data[i][8] +"' and modulecode not like '____000'");
							if(rs1.next()){
								
								int value = stmt1.executeUpdate("insert into DiscountCharges values("+ table_data[i][0]+","+ table_data[i][1]+",'"+ table_data[i][2]+"',"+ table_data[i][3]+","+ table_data[i][4] +",'"+ table_data[i][5]+"','"+table_data[i][6]+"','"+ table_data[i][7]+"','"+ rs1.getString("modulecode")+"')");
								System.out.println(" the updated value is " + value);
							}
							else{
								sessionContext.setRollbackOnly();
								throw new RecordNotInsertedException();
							}
						}
					}
					
					System.out.println("finished server 1");
				}
				else{
					sessionContext.setRollbackOnly();
					throw new RecordNotInsertedException();
				}
				if(rs==null && table_name == 2)
					sessionContext.setRollbackOnly();
				
			}catch(RecordNotInsertedException rec){
				rec.printStackTrace();
				sessionContext.setRollbackOnly();
				throw rec;
			}
			catch(SQLException sq)
			{
			sq.printStackTrace();
			sessionContext.setRollbackOnly();
			}
		
		
		 	finally{
				try{                
					conn.close();
				}catch(SQLException exception){exception.printStackTrace();}
			}
		 }

	 	public DiscountCharges[] retrieveDiscountCharges() throws RecordsNotFoundException
	 	{
	 		ResultSet rs=null;
	 		DiscountCharges[] dc=null;
	 		Connection conn=null;
	 		try
	 		{
	 			conn = getConnection();
	 			Statement  stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			//rs=stmt.executeQuery("Select * from Bounce_Fine");
	 			rs=stmt.executeQuery("Select ds.* , moduleabbr from DiscountCharges ds, Modules where  ds.ac_type = modulecode ");
	 			rs.last();
	 			if(rs.getRow()>0)
	 			{
	 				int i=0;
	 				dc = new DiscountCharges[rs.getRow()];
	 				rs.beforeFirst();
	 				while(rs.next())
	 				{
	 					dc[i]=new DiscountCharges();
	 					
	 					dc[i].setFromAmt(rs.getDouble("fr_amt"));
	 					dc[i].setToAmt(rs.getDouble("to_amt"));
	 					dc[i].setInt_type(rs.getString("int_type"));
	 					dc[i].setFineAmt(rs.getDouble("int_amt"));
	 					dc[i].setInt_rate(rs.getDouble("int_rate"));
	 					dc[i].setDe_tml(rs.getString("de_tml"));
	 					dc[i].setDe_user(rs.getString("de_user"));
	 					dc[i].setDe_date(rs.getString("de_date"));
	 					dc[i].setAc_type(rs.getString("moduleabbr"));    

	 					i++;	
	 				}
	 			}
	 			else
	 				throw new RecordsNotFoundException();
	 		}catch(SQLException m){
	 			m.printStackTrace();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return dc;
	 	}
	 	
	 	
	 	public void insertTable(String clg_date,int clg_no,ClearingObject[] array_clearingobject)throws RecordNotInsertedException
	 	{
	 		Connection conn=null;
	 		
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			for(int i=0;i<array_clearingobject.length;i++)
	 			{
	 				String clg_dt=clg_date;
	 				int cl_no=clg_no;
	 				int bank_code=array_clearingobject[i].getBankCode();
	 				String bank_name=array_clearingobject[i].getBranchName();
	 				int ack_no=array_clearingobject[i].getCreditACNo();
	 				double amt=array_clearingobject[i].getTranAmt();
	 			stmt.executeUpdate("insert into Dispatched_Cheque values('"+clg_dt+"',"+cl_no+","+bank_code+",'"+bank_name+"',"+ack_no+","+amt+")");
	 			}
	 		conn.close();
	 		
	 		}
	 		catch(SQLException sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		catch (Exception e) {
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 	}
	 	
	 	public BankMaster[] getBankDetails(int bcode,int city_cd,int bank_cd) throws RecordsNotFoundException
	 	{
	 		ResultSet rs=null,result=null;
	 		BankMaster[] bm=null;
	 		String br_type=null;
	 		Connection conn=null;
	 		System.out.println("Inside bank details");
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 		 if(bcode==-1)
	 				rs=stmt.executeQuery("select bank_code,bank_name,bank_abbr from BankMaster order by bank_code");
	 			else if(bcode==-2)
	 				//rs=stmt.executeQuery("select br_code,br_name,bankcode,br_type from BranchMaster,Head where br_code = bankcode order by br_code");
	 				rs=stmt.executeQuery("select br_code,br_name,bankcode,BranchMaster.br_type from BranchMaster,Head where br_code<1000 order by br_code");
	 			else if(bcode==-3) 
	 				rs=stmt.executeQuery("select * from CityMaster order by city_name");
	 			else if(bcode==-4) 
	 				rs=stmt.executeQuery("select branch_code,branch_name from BranchCodes where city_code="+city_cd+" and bank_code="+bank_cd);
	 			else if(bcode==-5) 
	 				rs=stmt.executeQuery("select bankcode,bank_abbr from Head");
	 			else if(bcode== -6) 
	 				rs=stmt.executeQuery("select * from CityMaster where city_code="+city_cd );
	 			else if(bcode== -7) {
	 				System.out.println(" entered into the credit account number.........6");
	 				rs=stmt.executeQuery("select gl_code , gl_name from GLMaster where gl_code not like '%0' and gl_code not in (select gl_code  from GLKeyParam where ac_type='1019001')" );
	 			}
	 			else if(bcode== -8) {

	 				rs=stmt.executeQuery("select  distinct(bm.bank_code),bm.bank_name,bm.bank_abbr,bm.de_tml,bm.de_user,bm.de_date from Cheque , BankMaster bm where clg_date is not null and desp_ind = 'T' and post_ind = 'F' and clg_type = 'O' and doc_bs = 'S' and ret_norm = 'N' and bm.bank_code = substring(bank_cd,4,3)" );
	 			}
	 			else
	 				rs=stmt.executeQuery("select bank_code,bank_name from BankMaster where bank_code="+bcode);

	 			rs.last();
	 			if(rs.getRow()==0)
	 			{
	 				
	 				bm=null;
	 				return bm;
	 			}
	 				bm=new BankMaster[rs.getRow()];
	 				rs.beforeFirst();
	 				int i=0;
	 				while(rs.next())
	 				{

	 					bm[i]=new BankMaster();
	 					bm[i].setBankCode(rs.getInt(1));
	 					bm[i].setBankName(rs.getString(2));
	 					if(bcode==-2)
	 					{
	 						bm[i].setDeTml(String.valueOf(rs.getInt(3)));
	 						bm[i].setDeUser(rs.getString(4));
	 					}
	 					if ( bcode == -1){
	 						
	 						bm[i].setabbrevation(rs.getString("bank_abbr"));
	 						
	 					}
	 					
	 					i++;
	 				}
	 		}catch(Exception e){
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e1) {
	 				e1.printStackTrace();
	 			}
	 		}
	 		return bm;
	 	}
	 	
	 	
	 	public BankMaster[] getBankDetails(int bcode,String date, int clg_bank,int clg_no) throws RecordsNotFoundException
	 	{
	 		ResultSet rs=null,result=null;
	 		BankMaster[] bm=null;
	 		String br_type=null;
	 		Connection conn=null;
	 		System.out.println("Inside bank details");
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 			
	 			rs = stmt.executeQuery("select distinct(bm.bank_code),bm.bank_name,bm.bank_abbr,bm.de_tml,bm.de_user,bm.de_date from Cheque , BankMaster bm where clg_date is not null and desp_ind = 'T' and post_ind = 'F' and clg_type = 'O' and doc_bs= 'S' and ret_norm = 'N' and bm.bank_code = substring(bank_cd,4,3) and concat(right(exp_clgdt,4),'-',mid(exp_clgdt,locate('/',exp_clgdt)+1,(locate('/',exp_clgdt,4)-locate('/',exp_clgdt)-1)),'-',left(exp_clgdt,locate('/',exp_clgdt)-1)) = '"+Validations.convertYMD(date)+"' and clg_no = "+ clg_no+" and send_to = "+clg_bank);

	 			
	 				
	 			rs.last();
	 			if(rs.getRow()==0)
	 			{
	 				
	 				bm=null;
	 				throw new RecordsNotFoundException();
	 			}
	 			
	 			
	 				bm=new BankMaster[rs.getRow()];
	 				rs.beforeFirst();
	 				int i=0;
	 				while(rs.next())
	 				{

	 					bm[i]=new BankMaster();
	 					bm[i].setBankCode(rs.getInt(1));
	 					bm[i].setBankName(rs.getString(2));
	 					
	 					
	 					i++;
	 				}
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e1) {
	 				e1.printStackTrace();
	 			}
	 		}
	 		return bm;
	 	}

	 /*	public BranchObject[] getBranchDetails(int bcode)throws RemoteException
	 	{
	 		ResultSet rs=null;
	 		BranchObject[] bm=null;
	 		try
	 		{
	 			if(bcode==-1)
	 				rs=stmt.executeQuery("select * from BranchMaster order by br_code");
	 			else
	 				rs=stmt.executeQuery("select * from BranchMaster where br_code="+bcode);

	 			rs.last();
	 			System.out.println(rs.getRow());
	 			if(rs.getRow()==0)
	 			{
	 				bm=new BranchObject[1];
	 				bm[0]=new BranchObject();
	 				bm[0].setBranchCode(-1);
	 				return bm;
	 			}
	 			bm=new BranchObject[rs.getRow()];
	 			rs.beforeFirst();
	 			int i=0;
	 			while(rs.next())
	 			{
	 				System.out.println(i);

	 				bm[i]=new BranchObject();
	 				bm[i].setBranchCode(rs.getInt("br_code"));
	 				bm[i].setBranchName(rs.getString("br_name"));
	 				bm[i].setShortName(rs.getString("br_shnm"));
	 				bm[i].setBranchACType(rs.getString("br_ac_type"));
	 				bm[i].setBranchACNo(rs.getInt("br_ac_no"));
	 				i++;
	 			}
	 			System.out.println("afdds");
	 			return bm;

	 		}catch(Exception e){bm[0].setBranchCode(-1);System.out.println(e);}
	 		return bm;
	 	}*/
	 	
	 	public BranchObject[] getBranchDetails(int bcode) throws RecordsNotFoundException
	 	{
	 		ResultSet rs=null,result=null;
	 		BranchObject[] bm=null;
	 		Connection conn = null;
	 		String br_type=null;
	 		try
	 		{
	 			
	 			
	 			conn = getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 		
	 			if(bcode==0)
	 			{
	 				System.out.println("hello");
//	 				result=st.executeQuery("select br.br_type from BranchMaster br ,Head he  where br.br_code=he.bankcode");
	 				if(result.next())
	 				{
	 					
	 					br_type=result.getString("br_type");
	 					System.out.println("br_type"+br_type);
	 					if(br_type.equalsIgnoreCase("HO"))
	 						rs=stmt.executeQuery("select br.*,br_code,br_name,he.bankcode,br.br_type from BranchMaster br,Head he where br.br_type is  not null  order by br_code");
	 					else
	 						rs=stmt.executeQuery("select br.*,br.br_code,br_name,br.br_type from BranchMaster br,Head he where he.bankcode=br.br_code union select br.*,br.br_code,br_name,br.br_type from BranchMaster br,Head he where br.br_type='HO'");
	 				
					
	 				}
	 			}
	 			 else if(bcode==-1)// -1 for all
	 			{
	 				rs=stmt.executeQuery("select bm.* from BranchMaster bm,Head where br_code!= bankcode and br_code not between 1000 and 1109 order by br_code");
	 				//rs=stmt.executeQuery("select br_type,bankcode from BranchMaster,Head where br_code=bankcode");
	 				//rs.next();
	 				
	 				/*if(rs.getString(1).equals("HO"))
	 					rs=stmt.executeQuery("select bm.*,bankcode from BranchMaster bm,Head where br_code not between 1000 and 1109 and br_type!='"+rs.getString(1)+"' order by br_code");
	 				else
	 					rs=stmt.executeQuery("select bm.*,bankcode from BranchMaster bm,Head where br_code not between 1000 and 1109 and br_type ='HO' order by br_code");
	 				*/	
	 			}
	 			else if(bcode==-2)//   
	 			{
	 				rs=stmt.executeQuery("Select * from BranchMaster where br_shnm!='AB'");
	 				
	 			}
	 			else if( bcode==-3 )// 
	 			{
	 				rs=stmt.executeQuery(" Select * from BranchMaster ");
	 				
	 			}
	 			
	 			else if(bcode==1)
	 			{
	 				rs=stmt.executeQuery("select bm.*,location,bankname,moduleabbr from Head,BranchMaster bm,Modules where bankcode=br_code and br_ac_type=modulecode");
	 			}
	 			else if(bcode==10)
	 			{
	 				System.out.println("inside the dcode 10");
	 				rs=stmt.executeQuery("select bm.*, moduleabbr from BranchMaster bm,Head,Modules where br_code != bankcode  and br_code  between 1000 and 10000 and modulecode = bm.br_ac_type");
	 			}
	 			else
	 				rs=stmt.executeQuery("select bm.* from BranchMaster bm,Head where br_code != bankcode and br_code="+bcode+" and br_code not between 1000 and 1109");

	 			
	 			rs.last();
	 			System.out.println(  "out is " + rs.getRow());
	 			if(rs.getRow()==0)
	 			{
	 				
	 				bm = null;
	 				return bm;
	 				
	 			}
	 			else 
	 			{
	 	 				
	 				bm=new BranchObject[rs.getRow()];
	 			
	 			System.out.println("111111111");

	 			rs.beforeFirst();
	 			int i=0;
	 			while(rs.next())
	 			{
	 				bm[i]=new BranchObject();
	 				bm[i].setBranchCode(rs.getInt("br_code"));
	 				bm[i].setBranchName(rs.getString("br_name"));
	 				bm[i].setBranchACType(rs.getString("br_ac_type"));
	 				bm[i].setBranchType(rs.getString("br_type"));
	 				
	 				bm[i].setBranchACNo(rs.getInt("br_ac_no"));
	 				bm[i].setBr_abbrv(rs.getString("br_shnm"));
	 				
	 				if(bcode == 10)
	 				{
	 	 				
	 					bm[i].setBr_abbrv(rs.getString("moduleabbr"));
	 					bm[i].setBranchACType(rs.getString("moduleabbr"));
	 					System.out.println(bm[i].getBranchACType()+ "-----------------------");
	 					
	 					
	 					
	 				}
	 				
	 				 if(bcode!=1)
	 				{
	 					bm[i].setShortName(rs.getString("br_shnm"));
	 					bm[i].setGlType(rs.getString("gl_type"));
	 				}
	 				else
	 				{
	 					bm[i].setShortName(rs.getString("location"));
	 					bm[i].setGlType(rs.getString("bankname"));
	 				}
	 				
	 				bm[i].setGlCode(rs.getInt("gl_code"));
	 				bm[i].setBranchAddress(rs.getString("address"));
	 				bm[i].setDe_date(rs.getString("de_date"));      
	 				bm[i].setDe_tml(rs.getString("de_tml"));
	 				bm[i].setDe_user(rs.getString("de_user"));
	 				
	 				System.out.println (bm[i].getBranchACNo());
	 				System.out.println (bm[i].getGlCode());
	 				i++;
	 			}
	 			//return bm;
	 		}
	 		}
	 		catch(SQLException e)
	 		{
	 			//bm[0].setBranchCode(-1);
	 			e.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally
	 		{
	 			try 
	 			{
	 				conn.close();
	 			}
	 			catch (SQLException e1)
	 			{
	 				e1.printStackTrace();
	 			}
	 		}
	 		return bm;
	 	}

	 	
	 	public double DiscountChargeCalculation(String actype,double amount)
	 	{
	 		ResultSet rs=null;
	 		Connection conn=null;
	 		//double discntFine=0;
	 		try
	 		{	System.out.println("indide try");
	 			conn = getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			rs=stmt.executeQuery("select * from DiscountCharges where "+amount+" between fr_amt and to_amt and ac_type='"+actype+"'");
	 			System.out.println("inside discount calculationn");
	 			if(rs.next()){
	 				if(rs.getString("int_type").equals("T"))
	 				{
	 					return ((amount*rs.getFloat("int_rate"))/100);
	 				}
	 				System.out.println("int amt"+rs.getFloat("int_amt"));
	 				return rs.getFloat("int_amt");
	 				
	 			}	
	 		}catch(SQLException x){
	 			x.printStackTrace();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return 0.0;
	 	}

	 	public long storeChequeData(ChequeDepositionObject cd)
	 	{
	 		Connection conn = null;
	 		try
	 		{
	 			int trn_seq = 0, cat_type =0;
	 			ResultSet rs=null;
	 			long ctrlno=0;
	 			conn = getConnection();
	 			comm = commonHome.create();
	 			PreparedStatement pstmt=null;
	 			Statement stmt_bounce = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stupdate=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmt_prev = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 			if(cd.getControlNo()==0)
	 			{
	 				//ctrlno=Integer.parseInt(comm.getGenParam("lst_ctrl_no").toString());
	 				rs=stmt.executeQuery("select modulecode,lst_acc_no from Modules where modulecode='1011001'");
	 				rs.next();
	 				ctrlno=rs.getInt(2)+1;
	 				rs.updateLong("lst_acc_no",ctrlno);
	 				rs.updateRow();
	 				rs.close();
	 				
	 				
	 			
	 				if(cd.getDocSource() == cd.getDocDestination())
	 				    pstmt=conn.prepareStatement("insert into Cheque values(null,null,null,?,'G',?,?,?,?,'S',1,null,?,?,?,?,?,'N',?,null,?,?,?,?,?,?,?,?,?,?,'F','F',?,?,?,null,null,'F',0.00,?,?,?,?,?,?,null,null,null)");
	 				else
	 					pstmt=conn.prepareStatement("insert into Cheque values(null,null,null,?,'G',?,?,?,?,'S',1,0.0,?,?,?,?,?,'N',?,null,?,?,?,?,?,?,?,?,?,?,'F','F',?,?,?,null,null,'F',0.00,?,?,?,?,?,?,null,null,null)");
	 				//else if(cd.getExpectedClgDate().equals("T"))	
	 					
	 				System.out.println (13);
	 				
	 				pstmt.setLong(1,ctrlno);
	 				if(cd.getDocSource() == cd.getDocDestination()){
	 					pstmt.setString(2,"T");
	 					pstmt.setInt(3,0);

	 					
	 				}
	 				
	 				else {
	 				
	 					pstmt.setString(2,cd.getClgType());
	 					pstmt.setInt(3,cd.getAckNo());
	 				}

	 	 				pstmt.setInt(4,cd.getDocSource());
	 	 				pstmt.setInt(5,cd.getDocDestination());
	 	 				pstmt.setString(6,cd.getMultiCredit());
	 	 				pstmt.setString(7,cd.getCompanyName());
	 	 				pstmt.setString(8,cd.getChqDDPO());
	 	 				pstmt.setInt(9,cd.getQdpNo());
	 	 				pstmt.setString(10,cd.getQdpDate());

	 	 				pstmt.setLong(11,cd.getprev_ctrl_no());
	 	 				
	 	 				pstmt.setString(12,cd.getCreditACType());
	 	 				pstmt.setInt(13,cd.getCreditACNo());
	 	 								
	 	 				pstmt.setDouble(14,cd.getPOCommission());
	 	 				pstmt.setString(15,cd.getDebitACType());
	 	 				pstmt.setInt(16,cd.getDebitACNo());
	 	 				pstmt.setString(17,cd.getPayeeName());

	 	 				pstmt.setString(18,cd.getBankCode());
	 	 				pstmt.setString(19,cd.getBranchName());
	 	 				pstmt.setDouble(20,cd.getTranAmt());

	 	 				pstmt.setString(21,cd.getBounceInd());
	 	 				pstmt.setString(22,cd.getDiscInd());
	 	 				pstmt.setDouble(23,cd.getDiscAmt());
	 	 				pstmt.setDouble(24,cd.getDiscChg());

	 	 				pstmt.setDouble(25,cd.getFineAmt());
	 	 				
	 	 				pstmt.setString(26,cd.getLoanAcc());
	 	 				pstmt.setInt(27,cd.getLoanAcc_No());
	 	 				pstmt.setString(28,cd.getDeTml());
	 	 				pstmt.setString(29,cd.getDeUser());
	 	 				pstmt.setString(30,cd.getDeTime());

	 				
	 				
	 				 				
	 				if(pstmt.executeUpdate()==0){
	 					sessionContext.setRollbackOnly();
	 					throw new SQLException();
	 				}
	 								
	 				if( cd.getBounceInd() != null && cd.getBounceInd().equals("T"))
	 				{
	 					Vector reason_arr = cd.getReasonArray();
	 					
	 					for(int i=0;i<reason_arr.size();i++){
	 						System.out.println("vector array ....................."+reason_arr.get(i));
	 						pstmt.addBatch("insert into Reason values("+ctrlno+","+reason_arr.get(i)+",'"+cd.getDeTml()+"','"+cd.getDeUser()+"','"+cd.getDeTime()+"',null,null,null)");
	 					}
	 					pstmt.executeBatch();
	 				}
	 				
	 				if ( cd.getprev_ctrl_no()!= 0 ){
	 					
	 					int h = stmt_prev.executeUpdate(" update Cheque set prev_ctrl_no = "+ ctrlno +" where ctrl_no ="+ cd.getprev_ctrl_no());
	 					
	 					if ( h < 0){
	 						 return -1;
	 					}
	 				}
	 				
	 				
	 			}
	 			else
	 			{
	 				if(cd.getVeTml() == null) 
	 				{
	 					String brname=cd.getBranchName();
	 					System.out.println("branch name i n bean==>"+cd.getBranchName());
	 					ctrlno = stmt.executeUpdate("update Cheque set bank_cd='"+cd.getBankCode()+"',br_name='"+cd.getBranchName()+"',trn_amt="+cd.getTranAmt()+",to_bounce='"+cd.getBounceInd()+"',mult_cr='"+cd.getMultiCredit()+"',comp_name='"+cd.getCompanyName()+"',cr_ac_type='"+cd.getCreditACType()+"',cr_ac_no="+cd.getCreditACNo()+",chqddpo='"+cd.getChqDDPO()+"',payee_name='"+cd.getPayeeName()+"',qdp_no="+cd.getQdpNo()+",fine_amt='"+cd.getFineAmt()+"',qdp_dt='"+cd.getQdpDate()+"',po_comm="+cd.getPOCommission()+",disc_ind='"+cd.getDiscInd()+"',disc_amt="+cd.getDiscAmt()+",de_tml='"+cd.getDeTml()+"',de_user='"+cd.getDeUser()+"',de_dt_time='"+cd.getDeTime()+"',desp_ind='F',post_ind='F' where ctrl_no="+cd.getControlNo());
	 				
	 					if( cd.getBounceInd() != null && cd.getBounceInd().equals("T"))
	 	 				{                                       
	 	 					
	 	 					
	 	 					stmt.executeUpdate(" delete from Reason where ctrl_no = " + cd.getControlNo());
	 	 					
	 	 					for(int i=0;i<cd.getReasonArray().size();i++){
	 	 						
	 	 						stmt_bounce.addBatch("insert into Reason values("+cd.getControlNo()+","+cd.getReasonArray().get(i)+",'"+cd.getDeTml()+"','"+cd.getDeUser()+"','"+cd.getDeTime()+"',null,null,null)");
	 	 					}
	 	 					int[] count = stmt_bounce.executeBatch();
	 	 					
	 	 					if ( count.length == 0 ){ 
	 	 						
	 	 						sessionContext.setRollbackOnly(); 
	 	 						ctrlno = -1;
	 	 					}
	 	 					
	 	 				}
	 			
	 				}
	 				else
	 				{
	 				    System.out.println("ctrl no == "+cd.getControlNo());				    
	 				    
	 				    rs=stmt.executeQuery("SELECT Cheque.*,BranchMaster.br_name from Cheque,BranchMaster,Head WHERE Cheque.ctrl_no="+cd.getControlNo()+" AND BranchMaster.br_code=Head.bankcode and ve_tml is null");
	 					if (rs.next())
	 					{
	 						if(cd.getBounceInd().equals("F"))
	 						{
	 							System.out.println("Bank code"+cd.getBankCode());
	 							System.out.println("Branch name"+cd.getBranchName());
	 							System.out.println("br name"+rs.getString("BranchMaster.br_name"));

	 							//st.addBatch("update ChequeNo set chq_del='T' where ac_type='"+cd.getDebitACType()+"' and ac_no="+cd.getDebitACNo()+" and chq_no="+cd.getQdpNo());
	 							//st.executeBatch();
	 							System.out.println("-------------------------<>---------");
	 							System.out.println("account type "+ cd.getDebitACType()+" account no "+cd.getDebitACNo()+" and chq_no="+cd.getQdpNo() );
	 						 int rets =st.executeUpdate("update ChequeNo set chq_del='T' where ac_type='"+cd.getDebitACType()+"' and ac_no="+cd.getDebitACNo()+" and chq_no="+cd.getQdpNo());
	 						 System.out.println("Cheque has been updated value**************"+ rets);
	 						}
	 	
	 						else if(cd.getBankCode().equalsIgnoreCase("000000001"))// && cd.getBranchName().equalsIgnoreCase(rs.getString("br_name")))
	 						{
	 							 String reas="";
	 							 try
	 							 {
	 								 System.out.println("inside the searching method");
	 								 Vector reason_arr = cd.getReasonArray();
	 								 
	 								 for(int i=0;i<reason_arr.size();i++)
	 								 {
	 									 
	 									 if(i==(reason_arr.size()-1))	
	 										 reas = reas+reason_arr.get(i);
	 									 else
	 										 reas = reas+reason_arr.get(i)+",";
	 								 }
	 								 rs=stmt.executeQuery(" select max(fine) from BounceFine where acc_type='"+cd.getDebitACType()+"' and b_code in ("+reas+")");
	 								 rs.next();
	 								 double fine = rs.getDouble(1);
	 								System.out.println("inside the searching method........................1");
	 								 ResultSet reason =  st.executeQuery(" select acc_type, fine, reason, re.reason_cd from BounceFine, ReasonMaster , Reason re, Cheque ch where code = b_code and acc_type = '" + cd.getDebitACType()+"' and code = re.reason_cd  and   ch.ctrl_no = re.ctrl_no and re.ctrl_no =" + cd.getControlNo() );
	 								 int no_rows = 0;
	 								 double fine_amount = 0.0;
	 								 
	 								 while(reason.next()){
	 									 
	 									 fine_amount += reason.getDouble("fine");
	 									 
	 								 }
	 								 System.out.println("Fine Amount "+ fine_amount);
	 								 if(fine_amount > 0.0){
	 									 {
	 										System.out.println("inside the searching method........................1");
	 										 AccountTransObject am = new AccountTransObject();
	 										 
	 										 am.setAccType(cd.getDebitACType());
	 										 am.setAccNo(cd.getDebitACNo());
	 	 								
	 										 am.setTransType("P");
	 										 am.setTransAmount(fine_amount);
	 										 am.setTransMode("T");
	 										 am.setTransSource(cd.getVeTml());
	 										 //am.setTransDate(getToday());
	 										 
	 										am.setTransDate(cd.getDe_date());
	 										 
	 										 am.setCdInd("D");
	 										 am.setChqDDNo(cd.getQdpNo());
	 										 am.setChqDDDate(cd.getQdpDate());
	 										 am.setTransNarr("Ctrl No "+cd.getControlNo());
	 										 am.setRef_No(Integer.parseInt(String.valueOf(cd.getControlNo())));
	 										 am.setPayeeName(cd.getPayeeName());
	 										 am.setCloseBal(fine_amount);
	 										 am.setLedgerPage(0);
	 										 am.uv.setUserTml(cd.getDeTml());
	 										 am.uv.setUserId(cd.getDeUser());
	 										 am.uv.setUserDate(cd.getVe_date());
	 										 am.uv.setVerTml(cd.getVeTml());
	 										 am.uv.setVerId(cd.getVeUser());
	 										 am.uv.setVerDate(cd.getVe_datetime());
	 	 					
	 										 comm.storeAccountTransaction(am);
	 										 
	 										 ResultSet gl = st.executeQuery("select * from GLKeyParam where ac_type='1011001' and code=7");
	 										 if(gl.next())
	 										 {
	 											 System.out.println (1111);
	 											 
	 											 GLTransObject trnobj=new GLTransObject();
	 											 //trnobj.setTrnDate(getToday());
	 											trnobj.setTrnDate(cd.getDe_date());
	 											 
	 											 System.out.println (22);
	 											 trnobj.setGLType(gl.getString("gl_type"));
	 											 trnobj.setGLCode(gl.getString("gl_code"));
	 											 System.out.println (33);
	 											 trnobj.setTrnMode("G");
	 											 trnobj.setAmount(fine_amount);
	 											 System.out.println (44);
	 											 trnobj.setCdind("C");
	 											 trnobj.setAccType(cd.getDebitACType());
	 											 trnobj.setAccNo(Integer.toString(cd.getDebitACNo()));
	 											 trnobj.setTrnType("");
	 											 trnobj.setRefNo(0);
	 											 trnobj.setTrnSeq(0);
	 											 trnobj.setVtml(cd.getVeTml());
	 											 trnobj.setVid(cd.getVeUser());
	 											 trnobj.setVDate(cd.getVe_datetime());
	 											 comm.storeGLTransaction(trnobj);
	 											 System.out.println (66);
	 										 }	
	 	 	 									
	 	 	 								}
	 	 	 							}
	 	 	 							}catch(SQLException sq){
	 	 	 								sq.printStackTrace();
	 	 	 								System.out.println("in else   "+sq);
	 	 								}
	 	 	 							
	 	 	 							int rets =st.executeUpdate("update ChequeNo set chq_del='T' where ac_type='"+cd.getDebitACType()+"' and ac_no="+cd.getDebitACNo()+" and chq_no="+cd.getQdpNo());
	 	 						}
	 	 					
	 						 
	 					}
	 					

	 				}
	 				}
	 			
	 			return ctrlno;
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		 } catch (CreateException e) {
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return -1;
	 	}
	 	
	 	public boolean updateOutStationBounceCheque( ChequeDepositionObject chqobj )
	 	{
	 		
	 		Connection conn = null;
	 		int n = 0;
	 		try
	 		{
	 			conn = getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);	
	 			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			{
	 				
	 				
	 				n =  stmt.executeUpdate(" update Cheque set to_bounce = 'T', fine_amt = " + chqobj.getFineAmt() + " where ctrl_no =" + chqobj.getControlNo() );
	 				
	 				if ( n > 0){ 
	 					{
	 		 				Vector reason_arr = chqobj.getReasonArray();
	 		 				n = stmt.executeUpdate(" delete from Reason where ctrl_no ="+  chqobj.getControlNo() );
	 		 				
	 		 				for(int i=0;i<reason_arr.size();i++){
	 		 					
	 		 					
	 		 					
	 		 					System.out.println("value "+reason_arr.get(i));
	 		 					stat.addBatch("insert into Reason values("+ chqobj.getControlNo() +","+reason_arr.get(i)+",'"+ chqobj.getDeTml()+"','"+ chqobj.getDeUser()+"','"+ chqobj.getDe_date()+"',null,null,null)");
	 		 					
	 		 				}
	 		 				stat.executeBatch();
	 		 			}
	 				} else 
	 					return false; 
	 			}
	 			System.out.println("insertion done");
	 		} catch(SQLException sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		} catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		
	 		return true;
	 		
	 	}

	 	public int storeChequeData(CompanyMaster cm[],int ctrlno)
	 	{
	 		Connection conn = null;
	 		int n=0;
	 		try
	 		{
	 			conn = getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			if(cm[0].getCompanyName().equals("0"))
	 				stmt.executeUpdate("delete from ChequeDetails where ctrl_no="+ctrlno);
	 			System.out.println("deletion done");
	 			for(int i=0;i<cm.length;i++)
	 			{
	 			    System.out.println("amt isuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu"+cm[i].getVeUser());
	 			    System.out.println("loan ac type.........."+cm[i].getLoanAccType());
	 			    System.out.println("loan ac No.........."+cm[i].getLoanAccNo());
	 				if(cm[i].getCompanyName().equals("0"))
	 				{
	 					n=n+stmt.executeUpdate("insert into ChequeDetails (ctrl_no,date,cr_ac_type,cr_ac_no,cr_amt,post_ind,disc_ind,de_tml,de_user,de_dt_time,loan_ac_type,loan_ac_no) values("+ctrlno+",'"+ cm[i].getDe_date()+ "','"+cm[i].getAccType()+"',"+cm[i].getAccNo()+","+Double.parseDouble(cm[i].getVeUser())+",'F','F','"+cm[i].getDeTml()+"','"+cm[i].getDeUser()+"','"+ cm[i].getDate()+ "','"+cm[i].getLoanAccType()+"',"+cm[i].getLoanAccNo()+")");
	 					if(n==0) 
	 					{
	 						sessionContext.setRollbackOnly();
	 						throw new SQLException();
	 					}   
	 					
	 				}
	 				else if(cm[i].getCompanyName().equals("1"))
	 				{
	 					n=n+stmt.executeUpdate("update ChequeDetails cd set cd.ve_tml='"+cm[i].getVeTml()+"',cd.ve_user='"+cm[i].getVeUser()+"',cd.ve_dt_time = date_format(sysdate(),'%d/%m/%Y %r') where cd.ctrl_no="+ctrlno+" ");
	 					if(n==0) 
	 					{
	 						sessionContext.setRollbackOnly();
	 						throw new SQLException();
	 					}   
	 					
	 				}
	 			}
	 			System.out.println("insertion done");
	 		} catch(SQLException sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		} catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return n;
	 	}
	 	
	 	public ChequeDepositionObject[] getChargeableCheques()throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		ChequeDepositionObject[] cd=null;
	 		try{
	 			conn = getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			ResultSet rs=stmt.executeQuery("select * from Cheque where clg_type='I' and ret_norm='R' and to_bounce='T' and mult_cr='F' and mchg_amt=0.00 and qdp_no is not null order by ctrl_no desc;");
	 			
	 			rs.last();
	 			if(rs.getRow()>0){
	 				cd=new ChequeDepositionObject[rs.getRow()];
	 				rs.beforeFirst();
	 				int i=0;
	 				while(rs.next())
	 				{
	 					cd[i]=new ChequeDepositionObject();
	 					cd[i].setControlNo(rs.getLong("ctrl_no"));
	 					cd[i].setChqDDPO(rs.getString("chqddpo"));
	 					cd[i].setQdpNo(rs.getInt("qdp_no"));
	 					cd[i].setTranAmt(rs.getDouble("trn_amt"));
	 					/*if(rs.getString("clg_type").equalsIgnoreCase("O"))
	 					{
	 						cd[i].setCreditACType(rs.getString("cr_ac_type"));
	 						cd[i].setCreditACNo(rs.getInt("cr_ac_no"));
	 					}
	 					else
	 					{
	 						if(rs.getString("ret_norm").equalsIgnoreCase("N"))
	 						{
	 					*/		cd[i].setCreditACType(rs.getString("dr_ac_type"));
	 							cd[i].setCreditACNo(rs.getInt("dr_ac_no"));
	 					/*	/}
	 						else
	 						{
	 							cd[i].setCreditACType(rs.getString("cr_ac_type"));
	 							cd[i].setCreditACNo(rs.getInt("cr_ac_no"));
	 						}	
	 					} 
	 					*/
	 					cd[i].setBounceInd(rs.getString("let_sent"));//for Letter Sent
	 					i++;
	 				}
	 			}
	 			else{
	 				cd=null;
	 				return cd;
	 			}	
	 		}catch(SQLException e){
	 			e.printStackTrace();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e1) {
	 				e1.printStackTrace();
	 			}
	 		}
	 		return cd;
	 	}
	 	
	 	public void mailingCharge(ChequeDepositionObject[] cd)
	 	{
	 		Connection conn=null;
	 		Vector vector_reason=new Vector(0,1);
	 		int value=0;
	 		int cat_type=0,trn_seq=0;
	 		
	 		
	 		try {
	 			conn=getConnection();
	 			Statement stmt= conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stupdate= conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			AccountTransObject am;
	 	    	int accode;
	 	    	for (int k = 0; k<cd.length; k++)
	 	    	{
	 	    		
	 	    		// added Balakrishna
	 	    		//accode = Integer.parseInt(cd[k].getCreditACType().substring(0,4));
	 	    		
	 	    		accode = Integer.parseInt(cd[k].getCreditACType().substring(0,4));
	 	    		System.out.println("accode == "+accode);
	 	    		ResultSet rs=stmt.executeQuery("select reason_cd from Reason where ctrl_no="+cd[k].getControlNo());
	 	    		while(rs.next())
	 	    		{
	 	    			vector_reason.add(String.valueOf(rs.getInt(1)));
	 	    			
	 	    		}
	 	    		for(int j=0;j<vector_reason.size();j++)
	 	    		{
	 	    			System.out.println("vetor size++++==="+vector_reason.size());
	 	    			System.out.println("values reason"+vector_reason.get(j));
	 	    		}
	 	    		double mail_chg =storeMailChg(vector_reason,cd[k].getCreditACType());
	 	    		System.out.println("charges are"+mail_chg);
	 	    		System.out.println("ctrl_no"+cd[k].getControlNo());
	 	    		switch(accode){
	 	    			case 1002://for SB Accounts
	 						{
	 	    				 am = new AccountTransObject();
	 						
	 						System.out.println("ac type == "+cd[k].getCreditACType());
	 						System.out.println("ac No == "+cd[k].getCreditACNo());
	 						am.setAccType(cd[k].getCreditACType());
	 						am.setAccNo(cd[k].getCreditACNo());
	 						am.setTransType("P");
	 						am.setTransAmount(mail_chg);//mailing charge amount has to be retrieved from database..
	 						am.setTransMode("G");
	 						am.setTransDate(getToday());
	 						
	 						am.setTransSource(cd[k].getDeUser());
	 						am.setCdInd("D");
	 						am.setChqDDNo(cd[k].getQdpNo());
	 						am.setChqDDDate(cd[k].getQdpDate());
	 						am.setTransNarr("Clg Ctrl No "+cd[k].getControlNo());
	 						am.setRef_No(0);
	 						am.uv.setUserTml(cd[k].getDeTml());
	 						am.uv.setUserId(cd[k].getDeUser());
	 						am.uv.setVerTml(cd[k].getVeTml());
	 						am.uv.setVerId(cd[k].getVeUser());
	 	
	 						// call common impl method
	 						//int success = comm.storeAccountTransaction(am);
	 						System.out.println("R u there ?");
	 						comm = commonHome.create();
	 						comm.storeAccountTransaction(am);
	 						System.out.println("Yes I am Here");
	 						}
	 						
	 						
	 						//GL Code...
	 						{
	 							
	 						ResultSet	ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 							ret.next();
	 							trn_seq=ret.getInt(2);
	 						int cid	=ret.getInt("cid");
	 						System.out.println("cid is"+ cid);
	 							ResultSet rr= stupdate.executeQuery("select custtype from CustomerMaster where cid="+ cid );
	 							rr.next();
	 							System.out.println("custtype is"+ rr.getInt("custtype"));	
	 					int	 custtype=rr.getInt("custtype");
	 					
	 							if(custtype==0)
	 								cat_type=1;
	 							else
	 								cat_type=2;
	 							
	 							
	 						
	 						ResultSet	ret1 = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 							if(ret1.next())
	 							{
	 								GLTransObject trnobj=new GLTransObject();
	 								trnobj.setTrnDate(getToday());
	 								trnobj.setGLType(ret1.getString(3));
	 								trnobj.setGLCode(ret1.getString(1));
	 								trnobj.setTrnMode("G");
	 								trnobj.setAmount(mail_chg*ret1.getInt(2));
	 								trnobj.setCdind("D");
	 								trnobj.setAccType(cd[k].getCreditACType());
	 								trnobj.setAccNo(String.valueOf(cd[k].getCreditACNo()));
	 								trnobj.setTrnType("P");
	 								trnobj.setRefNo(0);
	 								trnobj.setTrnSeq(trn_seq);
	 								trnobj.setVtml(cd[k].getVeTml());
	 								trnobj.setVid(cd[k].getVeUser());System.out.println(34);
	 	
	 								comm.storeGLTransaction(trnobj);System.out.println(35);
	 							}
	 					ResultSet	ret2 = stupdate.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =7 and gk.ac_type = '1011001'");

	 					if(ret2.next())
	 							{
	 								GLTransObject trnobj=new GLTransObject();
	 								trnobj.setTrnDate(getToday());
	 								trnobj.setGLType(ret2.getString(2));
	 								trnobj.setGLCode(ret2.getString(1));
	 								trnobj.setTrnMode("G");
	 								trnobj.setAmount(mail_chg);
	 								trnobj.setCdind("C");
	 								trnobj.setAccType(cd[k].getCreditACType());
	 								trnobj.setAccNo(String.valueOf(cd[k].getCreditACNo()));
	 								trnobj.setTrnType("");
	 								trnobj.setRefNo(0);
	 								trnobj.setTrnSeq(0);
	 								trnobj.setVtml(cd[k].getVeTml());
	 								trnobj.setVid(cd[k].getVeUser());System.out.println(34);

	 								comm.storeGLTransaction(trnobj);System.out.println(35);
	 							}

	 						}
	 	    				break;
	 	    			case 1003:
	 	    				break;
	 	    			case 1004:
	 	    				break;
	 	    			case 1005:
	 	    				break;
	 	    			case 1007:
	 	    			{
	 	    				am = new AccountTransObject();
	 							
	 							System.out.println("ac type == "+cd[k].getCreditACType());
	 							System.out.println("ac No == "+cd[k].getCreditACNo());
	 							am.setAccType(cd[k].getCreditACType());
	 							am.setAccNo(cd[k].getCreditACNo());
	 							am.setTransType("P");
	 							am.setTransAmount(mail_chg);//mailing charge amount has to be retrieved from database..
	 							am.setTransMode("G");
	 							am.setTransSource(cd[k].getDeUser());
	 							am.setCdInd("D");
	 							am.setChqDDNo(cd[k].getQdpNo());
	 							am.setChqDDDate(cd[k].getQdpDate());
	 							am.setTransNarr("Clg Ctrl No "+cd[k].getControlNo());
	 							am.setRef_No(0);
	 							am.uv.setUserTml(cd[k].getDeTml());
	 							am.uv.setUserId(cd[k].getDeUser());
	 							am.uv.setVerTml(cd[k].getVeTml());
	 							am.uv.setVerId(cd[k].getVeUser());
	 							am.setTransDate(getToday());
	 							// call common impl method
	 							//int success = comm.storeAccountTransaction(am);
	 							System.out.println("R u there ?");
	 							comm = commonHome.create();
	 							comm.storeAccountTransaction(am);
	 							System.out.println("Yes I am Here");
	 							}
	 							
	 							
	 							//GL Code...
	 							{
	 								
	 							ResultSet	ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 								ret.next();
	 								trn_seq=ret.getInt(2);
	 							int cid	=ret.getInt("cid");
	 							System.out.println("cid is"+ cid);
	 								ResultSet rr= stupdate.executeQuery("select custtype from CustomerMaster where cid="+ cid );
	 								rr.next();
	 								System.out.println("custtype is"+ rr.getInt("custtype"));	
	 						int	 custtype=rr.getInt("custtype");
	 						
	 								if(custtype==0)
	 									cat_type=1;
	 								else
	 									cat_type=2;
	 								
	 								
	 							
	 							ResultSet	ret1 = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 								if(ret1.next())
	 								{
	 									GLTransObject trnobj=new GLTransObject();
	 									trnobj.setTrnDate(getToday());
	 									trnobj.setGLType(ret1.getString(3));
	 									trnobj.setGLCode(ret1.getString(1));
	 									trnobj.setTrnMode("G");
	 									trnobj.setAmount(mail_chg*ret1.getInt(2));
	 									trnobj.setCdind("D");
	 									trnobj.setAccType(cd[k].getCreditACType());
	 									trnobj.setAccNo(String.valueOf(cd[k].getCreditACNo()));
	 									trnobj.setTrnType("P");
	 									trnobj.setRefNo(0);
	 									trnobj.setTrnSeq(trn_seq);
	 									trnobj.setVtml(cd[k].getVeTml());
	 									trnobj.setVid(cd[k].getVeUser());System.out.println(34);
	 		
	 									comm.storeGLTransaction(trnobj);System.out.println(35);
	 								}
	 						ResultSet	ret2 = stupdate.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =7 and gk.ac_type = '1011001'");

	 						if(ret2.next())
	 								{
	 									GLTransObject trnobj=new GLTransObject();
	 									trnobj.setTrnDate(getToday());
	 									trnobj.setGLType(ret2.getString(2));
	 									trnobj.setGLCode(ret2.getString(1));
	 									trnobj.setTrnMode("G");
	 									trnobj.setAmount(mail_chg);
	 									trnobj.setCdind("C");
	 									trnobj.setAccType(cd[k].getCreditACType());
	 									trnobj.setAccNo(String.valueOf(cd[k].getCreditACNo()));
	 									trnobj.setTrnType("");
	 									trnobj.setRefNo(0);
	 									trnobj.setTrnSeq(0);
	 									trnobj.setVtml(cd[k].getVeTml());
	 									trnobj.setVid(cd[k].getVeUser());System.out.println(34);

	 									comm.storeGLTransaction(trnobj);System.out.println(35);
	 								}

	 							}
	     				break;
	 	    			case 1008:
	 	    				break;
	 	    			case 1009:
	 	    				break;
	 	    			case 1010:
	 	    				break;
	 	    		}
	 	    		value = stmt.executeUpdate("update Cheque set mchg_amt="+mail_chg+" where ctrl_no="+cd[k].getControlNo());
	 	    	}
	 	    		
	 	    }catch(Exception ex){
	 	        ex.printStackTrace();
	 	       sessionContext.setRollbackOnly();
	 	    }
	 	    
	 	    finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e1) {
	 				e1.printStackTrace();
	 			}
	 		}
	 	    
	 	}    
	 	
	 	public ClearingObject[] getInwardStatement(String clgdate,int clgno,int source,String query) throws RecordsNotFoundException
	 	{
	 		ClearingObject[] cd=null;
	 		Connection conn=null;
	 		try
	 		{
	 			ResultSet rs=null;
	 			conn = getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 			rs = stmt.executeQuery("select ch.*,bm1.br_name,bm2.br_name,bk.bank_name from Cheque ch,BranchMaster bm1,BranchMaster bm2,BankMaster bk where ch.ve_tml is not null and ch.clg_date='"+clgdate+"' and ch.clg_no="+clgno+" and ch.doc_sou="+source+" and bm1.br_code=ch.doc_sou and bm2.br_code=ch.doc_dest and bk.bank_code=substring(ch.bank_cd,4,3)");
	 			
	 			System.out.println("query == "+query);
	 			if(rs.next())
	 			{
	 			if(query ==null){
	 			    if(source%1111==0 && rs.getString("doc_bs").equalsIgnoreCase("S"))
	 					rs = stmt.executeQuery("select ch.*,bm1.br_name,bm2.br_name,bk.bank_name,cm.fname,cm.mname,cm.lname from Cheque ch,BranchMaster bm1,BranchMaster bm2,BankMaster bk,CustomerMaster cm,AccountMaster am where ch.clg_type='I' and ch.ve_tml is not null and ch.clg_date='"+clgdate+"' and ch.clg_no="+clgno+" and ch.doc_sou="+source+" and bm1.br_code=ch.doc_sou and bm2.br_code=ch.doc_dest and bk.bank_code=substring(ch.bank_cd,4,3) and am.ac_type=ch.dr_ac_type and am.ac_no=ch.dr_ac_no and cm.cid=am.cid");
	 				else
	 					rs = stmt.executeQuery("select ch.*,bm1.br_name,bm2.br_name,bk.bank_name from Cheque ch,BranchMaster bm1,BranchMaster bm2,BankMaster bk where ch.ve_tml is not null and ch.clg_date='"+clgdate+"' and ch.clg_no="+clgno+" and ch.doc_sou="+source+" and bm1.br_code=ch.doc_sou and bm2.br_code=ch.doc_dest and bk.bank_code=substring(ch.bank_cd,4,3)");
	 			}
	 			else{
	 			    if(source%1111==0 && rs.getString("doc_bs").equalsIgnoreCase("S"))
	 					rs = stmt.executeQuery("select ch.*,bm1.br_name,bm2.br_name,bk.bank_name,cm.fname,cm.mname,cm.lname from Cheque ch,BranchMaster bm1,BranchMaster bm2,BankMaster bk,CustomerMaster cm,AccountMaster am where ch.clg_type='I' and ch.ve_tml is not null and ch.clg_date='"+clgdate+"' and ch.clg_no="+clgno+" and ch.doc_sou="+source+" and bm1.br_code=ch.doc_sou and bm2.br_code=ch.doc_dest and bk.bank_code=substring(ch.bank_cd,4,3) and am.ac_type=ch.dr_ac_type and am.ac_no=ch.dr_ac_no and cm.cid=am.cid and ("+query+")");
	 				else
	 					rs = stmt.executeQuery("select ch.*,bm1.br_name,bm2.br_name,bk.bank_name from Cheque ch,BranchMaster bm1,BranchMaster bm2,BankMaster bk where ch.ve_tml is not null and ch.clg_date='"+clgdate+"' and ch.clg_no="+clgno+" and ch.doc_sou="+source+" and bm1.br_code=ch.doc_sou and bm2.br_code=ch.doc_dest and bk.bank_code=substring(ch.bank_cd,4,3) and ("+query+")");
	 			}
	 		
	 			rs.last();
	 			if(rs.getRow()>0){
	 				cd = new ClearingObject[rs.getRow()];
	 				rs.beforeFirst();
	 				int i=0;
	 				while (rs.next()) {
	 					
	 					cd[i] = new ClearingObject();
	 					cd[i].setCtrlNo(rs.getLong("ch.ctrl_no"));
	 					cd[i].setClgType(rs.getString("ch.clg_type"));
	 					cd[i].setDocBs(rs.getString("ch.doc_bs"));
	 					cd[i].setAckNo(rs.getInt("ch.ack_no"));
	 					cd[i].setNoOfDocs(rs.getInt("ch.no_docs"));
	 					cd[i].setVeTml(rs.getString("ch.ret_norm"));
	 					if(rs.getString("ch.doc_bs").equalsIgnoreCase("S"))
	 					{
	 						cd[i].setTranAmt(rs.getDouble("ch.trn_amt"));
	 						cd[i].setCompanyName(rs.getString("cm.fname")+" "+rs.getString("cm.mname")+" "+rs.getString("cm.lname"));
	 					}
	 					else
	 					{
	 						cd[i].setTranAmt(rs.getDouble("ch.doc_tot"));
	 						cd[i].setCompanyName(" ");	
	 					}
	 					
	 					cd[i].setSourceName(rs.getString("bm1.br_name"));
	 					cd[i].setDestName(rs.getString("bm2.br_name"));
	 					cd[i].setChqDDPO(rs.getString("ch.chqddpo"));
	 					cd[i].setQdpNo(rs.getInt("ch.qdp_no"));
	 					cd[i].setQdpDate(rs.getString("ch.qdp_dt"));
	 					
	 					cd[i].setDebitACType(rs.getString("ch.dr_ac_type"));
	 					cd[i].setDebitACNo(rs.getInt("ch.dr_ac_no"));
	 					cd[i].setPayeeName(rs.getString("ch.payee_name"));
	 					cd[i].setBankName(rs.getString("bk.bank_name"));
	 					cd[i].setBranchName(rs.getString("ch.br_name"));
	 					cd[i].setPrevCtrlNo(rs.getInt("ch.prev_ctrl_no"));
	 					cd[i].setDeUser(rs.getString("ch.de_user")+" "+rs.getString("ch.de_tml"));
	 					cd[i].setVeUser(rs.getString("ch.ve_user")+" "+rs.getString("ch.ve_tml"));
	 					i++;
	 				}
	 			}
	 		}
	 			else{
	 				cd=null;
	 				return cd;
	 			}
	 		}catch(SQLException e){
	 			e.printStackTrace();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e1) {
	 				e1.printStackTrace();
	 			}
	 		}
	 		return cd;
	 	}
	 	
	 	public ChequeAckObject[] getBouncedCheques(String fdate,String tdate,String query)throws RecordsNotFoundException
	 	{
	 		ChequeAckObject co[]=null;
	 		Connection conn=null;
	 		
	 		try
	 		{
	 		    fdate=Validations.convertYMD(fdate);
	 		    tdate=Validations.convertYMD(tdate);
	 		    
	 			conn = getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			ResultSet rs=null;
	 			if(query==null)
	 			    rs=stmt.executeQuery("select c.ret_norm,c.ctrl_no,chqddpo,qdp_no,qdp_dt,trn_amt,brm1.br_name,brm2.br_name, cr_ac_type,cr_ac_no,dr_ac_type,dr_ac_no,c.bank_cd,bm.bank_name,c.br_name,prev_ctrl_no,rm.reason from Cheque c,BankMaster bm,BranchMaster brm1,BranchMaster brm2,ReasonMaster rm,Reason r where c.to_bounce='T' and concat(right(clg_date,4),'-',mid(clg_date,locate('/',clg_date)+1,(locate('/',clg_date,4)-locate('/',clg_date)-1)),'-',left(clg_date,locate('/',clg_date)-1)) between  '"+fdate+"'and '"+tdate+"' and bm.bank_code=substring(bank_cd,4,3) and r.ctrl_no=c.ctrl_no and rm.code=r.reason_cd and brm1.br_code=c.doc_sou and brm2.br_code=c.doc_dest order by c.ctrl_no,r.reason_cd");
	 			else{
	 			    query="c."+query;
	 			    rs=stmt.executeQuery("select c.ret_norm,c.ctrl_no,chqddpo,qdp_no,qdp_dt,trn_amt,brm1.br_name,brm2.br_name, cr_ac_type,cr_ac_no,dr_ac_type,dr_ac_no,bm.bank_name,c.br_name,prev_ctrl_no,rm.reason from Cheque c,BankMaster bm,BranchMaster brm1,BranchMaster brm2,ReasonMaster rm,Reason r where c.to_bounce='T' and concat(right(clg_date,4),'-',mid(clg_date,locate('/',clg_date)+1,(locate('/',clg_date,4)-locate('/',clg_date)-1)),'-',left(clg_date,locate('/',clg_date)-1)) between  '"+fdate+"'and '"+tdate+"' and bm.bank_code=substring(bank_cd,4,3) and r.ctrl_no=c.ctrl_no and rm.code=r.reason_cd and brm1.br_code=c.doc_sou and brm2.br_code=c.doc_dest and ("+query+") order by c.ctrl_no,r.reason_cd");
	 			}    
	 			rs.last();
	 			if(rs.getRow()>0){
	 			    
	 			    rs.beforeFirst();
	 			    int ctrlno = 0;
	 			    int i=0;
	 			    
	 			    while(rs.next()){
	 			        if(i>0 && ctrlno==rs.getInt("c.ctrl_no"))
	 			            continue;
	 			        ctrlno=rs.getInt("c.ctrl_no");
	 			        i++;
	 			    }
	 				co=new ChequeAckObject[i];
	 				i=0;
	 				rs.beforeFirst();
	 				while(rs.next()){
	 					
	 					if(i>0 && co[i-1].getControlNum() == rs.getInt("c.ctrl_no"))
	 					{
	 						System.out.println("inside continue"+i);
	 						co[i-1].setDeUser(co[i-1].getDeUser()+","+rs.getString("rm.reason"));			//for Reason
	 						continue;
	 					}
	 					co[i]=new ChequeAckObject();
	 					System.out.println (2);
	 					co[i].setControlNum((rs.getInt("c.ctrl_no")));         //for control no
	 					System.out.println (3);	
	 					System.out.println("chqddpo"+rs.getString("chqddpo"));
	 					co[i].setTranTyp(rs.getString("chqddpo"));			//for instrument type
	 					co[i].setAckNum(String.valueOf(rs.getInt("qdp_no")));				//for Instrument no
	 					co[i].setDocBss(rs.getString("qdp_dt"));				//for Instrument Date
	 					co[i].setDocTotall(rs.getDouble("trn_amt"));			//for Instrument Amount
	 					
	 					co[i].setSrcName(rs.getString("brm1.br_name"));	//for Source
	 					co[i].setDestinName(rs.getString("brm2.br_name"));	//for Destination
	 					/*if(rs.getString("c.ret_norm").equals("R"))
	 					{
	 						co[i].setCreditACType(rs.getString("cr_ac_type"));  //for Credit A/C
	 						co[i].setCreditACNo(rs.getInt("cr_ac_no"));
	 					}
	 					else*/
	 					{
	 						co[i].setCreditACType(rs.getString("dr_ac_type"));  //for Debit A/C
	 						co[i].setCreditACNo(rs.getInt("dr_ac_no"));
	 					}	
	 					//co[i].											//for Debit A/C
	 					co[i].setBnkName(rs.getString("bm.bank_name"));	//for Bank
	 					//co[i].setBankCode(rs.getString("c.bank_cd"));
	 					co[i].setDeTml(rs.getString("c.br_name"));			//for Branch
	 					
	 					if(rs.getString("prev_ctrl_no") == null ){
	 						co[i].setBankCode("0");
	 					}else
	 						co[i].setBankCode(rs.getString("prev_ctrl_no"));		//for Previous Control Number
	 					
	 					co[i].setDeUser(rs.getString("rm.reason"));			//for Reason
	 					i++;
	 				}
	 			}
	 			else
	 				throw new RecordsNotFoundException();
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return co;
	 	}
	 	
	 	public int addAccountTransactionDebitEntry(ChequeDepositionObject cd) throws RecordNotInsertedException
	 	{
	 		Connection conn=null;
	 		
	 		try{
	 			int trn_seq=0,cat_type=0,custtype=0;
	 			ResultSet ret=null,rr=null;
	 			conn = getConnection();
	 			comm = commonHome.create();
	 			
	 			Statement stupdate = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stupdate_ch = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stup = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 		// Debit entry allowing -ve balance..
	 			AccountTransObject am = new AccountTransObject();
	 			am.setAccType(cd.getDebitACType());
	 			am.setAccNo(cd.getDebitACNo());
	 			am.setTransType("P");
	 			am.setTransAmount(cd.getTranAmt());
	 			am.setTransMode("G");
	 			am.setTransSource(cd.getVeTml());
	 			am.setCdInd("D");
	 			am.setTransDate( cd.getSysdate() );
	 			am.setChqDDNo(cd.getQdpNo());
	 			am.setChqDDDate(cd.getQdpDate());
	 			am.setTransNarr("Clg Ctrl No "+cd.getControlNo());
	 			am.setRef_No(cd.getAckNo());
	 			am.setPayeeName(cd.getPayeeName());
	 			am.setCloseBal(cd.getTranAmt());//set -value
	 			am.setLedgerPage(0);
	 			am.uv.setUserTml(cd.getVeTml());
	 			am.uv.setUserId(cd.getVeUser());
	 			am.uv.setUserDate(cd.getDeTime());
	 			am.uv.setVerTml(cd.getVeTml());
	 			am.uv.setVerId(cd.getVeUser());
	 			am.uv.setVerDate(cd.getDeTime());
	 			// call common impl method
	 			comm.storeAccountTransaction(am);
	 			// Gl code goes here .....
	 			

					if(cd.getDebitACType().startsWith("1002") || cd.getDebitACType().startsWith("1007"))
						ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
					else if(cd.getDebitACType().startsWith("1014") || cd.getDebitACType().startsWith("1015"))
						ret = stupdate.executeQuery("select odt.ac_type,od.last_tr_seq,cid from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
					
				if(	ret.next())
				{
					if(cd.getDebitACType().startsWith("1002")||cd.getDebitACType().startsWith("1007")||cd.getDebitACType().startsWith("1014")||cd.getDebitACType().startsWith("1015"))
					{
					trn_seq=ret.getInt(2);
					
				int cid	=ret.getInt("cid");
				System.out.println("cid is"+ cid);
					 rr= st.executeQuery("select custtype from CustomerMaster where cid="+ cid );
					if(rr.next())
					{
					System.out.println("custtype is"+ rr.getInt("custtype"));	
				 custtype=rr.getInt("custtype");
					if(custtype==0)
						cat_type=1;
					else
						cat_type=2;
					}
					
				
	 			
	 			ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+am.getAccType()+" and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 			if(ret.next())
	 			{
	 				GLTransObject trnobj=new GLTransObject();
	 				trnobj.setTrnDate(cd.getSysdate());
	 				trnobj.setGLType(ret.getString(3));
	 				trnobj.setGLCode(ret.getString(1));
	 				trnobj.setTrnMode("G");
	 				trnobj.setAmount(cd.getTranAmt()*ret.getInt(2));
	 				trnobj.setCdind("D");
	 				trnobj.setAccType(am.getAccType());
	 				trnobj.setAccNo(String.valueOf(am.getAccNo()));
	 				trnobj.setTrnType("P");
	 				trnobj.setRefNo(trn_seq);
	 				trnobj.setTrnSeq(trn_seq);
	 				trnobj.setVtml(cd.getVeTml());
	 				trnobj.setVid(cd.getVeUser());System.out.println(34);
	 				trnobj.setVDate(cd.getDeTime());
	 				
	 				comm.storeGLTransaction(trnobj);System.out.println(35);
	 			}
	 			// credit gl to clg_INApexBank
	 			ret = stupdate.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =1 and gk.ac_type = '1011001'");
	 			if(ret.next())
	 			{
	 				GLTransObject trnobj=new GLTransObject();
	 				trnobj.setTrnDate( cd.getSysdate() );
	 				trnobj.setGLType(ret.getString(2));
	 				trnobj.setGLCode(ret.getString(1));
	 				trnobj.setTrnMode("G");
	 				trnobj.setAmount(cd.getTranAmt());
	 				trnobj.setCdind("C");
	 				trnobj.setAccType(am.getAccType());
	 				trnobj.setAccNo(String.valueOf(am.getAccNo()));
	 				trnobj.setTrnType("P");
	 				trnobj.setRefNo(cd.getAckNo());
	 				trnobj.setTrnSeq(0);
	 				trnobj.setVtml(cd.getVeTml());
	 				trnobj.setVid(cd.getVeUser());System.out.println(34);
	 				trnobj.setVDate(cd.getDeTime());
	 				

	 				comm.storeGLTransaction(trnobj);System.out.println(35);
	 			}
				}
	 			// update cheque table..
	 			System.out.println("oooooooo"+cd.getControlNo());
	 			stup.executeUpdate("update Cheque set post_ind='T' where ctrl_no="+cd.getControlNo());
	 			
	 			stupdate.executeUpdate("update Cheque set  ve_tml='"+cd.getVeTml()+"',ve_user='"+cd.getVeUser()+"' where ctrl_no="+cd.getControlNo());
	 			// update chequeNo table..for blocking the chq
	 			stupdate_ch.executeUpdate("update ChequeNo set chq_amt="+cd.getTranAmt()+",chq_del='T' where ac_type='"+cd.getDebitACType()+"' and ac_no="+cd.getDebitACNo()+" and chq_no="+cd.getQdpNo());
	 
	 		}}catch(SQLException debitentry){
	 			debitentry.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}catch (CreateException e) {
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 			
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			
	 			}
	 		}
	 		return -1;
	 	}
	 	
	 	public long storeInwardBouncedData(ChequeDepositionObject cd)throws RecordNotInsertedException
	 	{
	 		Connection conn=null;
	 		ResultSet rs=null;
	 		int trn_seq=0,cat_type=0;
	 		long ctrlno=0;
	 		ResultSet ret,result=null;
	 		try
	 		{
	 			conn = getConnection();
	 			comm=commonHome.create();
	 			Statement stupdate = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stup= conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 			/*ctrlno=Long.parseLong(comm.getGenParam("lst_ctrl_no").toString());
	 			stupdate.executeUpdate("update GenParam set lst_ctrl_no="+(++ctrlno));
	 			
	 */
	 			rs=stupdate.executeQuery("select modulecode,lst_acc_no from Modules where modulecode='1011001'");
	 			rs.next();
	 			ctrlno=rs.getInt(2)+1;
	 			rs.updateLong("lst_acc_no",ctrlno);
	 			rs.updateRow();
	 			rs.close();
	 		
	 		
	 			
	 			
	 			ret = stupdate.executeQuery("select bankcode from Head");
	 			ret.next();
	 			
	 			

	 			PreparedStatement pstmt=conn.prepareStatement("insert into Cheque values(null,null,null,?,'G','O',null,?,?,'S',1,0,'F',null,?,?,?,'R',?,null,?,?,null,null,null,?,?,?,?,'F','F','F','F',null,null,null,null,'F',0.00,?,null,null,?,?,'"+ cd.getDeTime()+"',?,?,'"+ cd.getDeTime()+"')");
	 			pstmt.setLong(1,ctrlno);
	 			pstmt.setInt(2,ret.getInt(1));
	 			pstmt.setInt(3,cd.getSendTo());
	 			pstmt.setString(4,cd.getChqDDPO());
	 			
	 			pstmt.setInt(5,cd.getQdpNo());
	 			pstmt.setString(6,cd.getQdpDate());
	 			pstmt.setLong(7,cd.getControlNo());
	 			
	 			pstmt.setString(8,cd.getDebitACType());
	 			pstmt.setInt(9,cd.getDebitACNo());
	 			
	 			pstmt.setString(10,cd.getPayeeName());
	 			pstmt.setString(11,String.valueOf(cd.getBankCode()));
	 			pstmt.setString(12,cd.getBranchName());
	 			pstmt.setDouble(13,cd.getTranAmt());
	 			pstmt.setDouble(14,cd.getFineAmt());
	 			pstmt.setString(15,cd.getDeTml());
	 			pstmt.setString(16,cd.getDeUser());
	 			pstmt.setString(17,cd.getDeTml());
	 			pstmt.setString(18,cd.getDeUser());

	 			pstmt.executeUpdate();

	 			if(cd.getChqDDPO().equals("C") && cd.getFineAmt()>0.00){
	 				
	 				System.out.println(" inside the fine amount ");
	 				// Acc tran with fine....
	 				AccountTransObject am = new AccountTransObject();
	 				am.setAccType(cd.getDebitACType());
	 				am.setAccNo(cd.getDebitACNo());
	 				am.setTransType("P");
	 				am.setTransAmount(cd.getFineAmt());
	 				am.setTransDate(cd.getDeTime());
	 				am.setTransMode("G");
	 				am.setTransSource(cd.getDeTml());
	 				am.setCdInd("D");
	 				am.setChqDDNo(cd.getQdpNo());
	 				am.setChqDDDate(cd.getQdpDate());
	 				am.setTransNarr("Bounced Fine Ctrl No "+cd.getControlNo());
	 				am.setRef_No(0);
	 				am.setPayeeName(cd.getPayeeName());
	 				am.setCloseBal(cd.getFineAmt());//set -value
	 				am.setLedgerPage(0);
	 				am.uv.setUserTml(cd.getDeTml());
	 				am.uv.setUserId(cd.getDeUser());
	 				am.uv.setUserDate(cd.getDeTime());
	 				am.uv.setVerDate(cd.getDeTime());
	 				
	 				
	 				am.uv.setVerTml(cd.getDeTml());
	 				am.uv.setVerId(cd.getDeUser());
	 				
	 				// call common impl method
	 				comm.storeAccountTransaction(am);
	 				// Gl code goes here .....
	 				if(cd.getDebitACType().startsWith("1002")|| cd.getDebitACType().startsWith("1007"))
	 				ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 				else if(cd.getDebitACType().startsWith("1014") || cd.getDebitACType().startsWith("1015"))
	 					ret = stupdate.executeQuery("select odt.ac_type,odt.trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");	
	 				ret.next();
	 				trn_seq=ret.getInt(2);
	 				if(ret.getInt(1)==0)
	 					cat_type=1;
	 				else
	 					cat_type=2;
	 				ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+am.getAccType()+" and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 				if(ret.next())
	 				{
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate( cd.getSysdate() );
	 					trnobj.setGLType(ret.getString(3));
	 					trnobj.setGLCode(ret.getString(1));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(cd.getFineAmt()*ret.getInt(2));
	 					trnobj.setCdind("D");
	 					trnobj.setAccType(am.getAccType());
	 					trnobj.setAccNo(String.valueOf(am.getAccNo()));
	 					trnobj.setTrnType("P");
	 					trnobj.setRefNo(0);
	 					trnobj.setTrnSeq(trn_seq);
	 					trnobj.setVtml(cd.getDeTml());
	 					trnobj.setVid(cd.getDeUser());
	 					trnobj.setVDate(cd.getDeTime());
	 					
	 					System.out.println(1);

	 					comm.storeGLTransaction(trnobj);System.out.println(2);
	 				}
	 				// Profit Gl .....
	 				ret = stupdate.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =7 and gk.ac_type = '1011001'");
	 				if(ret.next())
	 				{
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate( cd.getSysdate() );
	 					trnobj.setGLType(ret.getString(2));
	 					trnobj.setGLCode(ret.getString(1));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(cd.getFineAmt());
	 					trnobj.setCdind("C");
	 					trnobj.setAccType(cd.getDebitACType());
	 					trnobj.setAccNo(String.valueOf(cd.getDebitACNo()));
	 					trnobj.setTrnType("");
	 					trnobj.setRefNo(0);
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(cd.getDeTml());
	 					trnobj.setVid(cd.getDeUser());System.out.println(3);
	 					trnobj.setVDate(cd.getDeTime());
	 					comm.storeGLTransaction(trnobj);System.out.println(4);
	 				}
	 			}
	 			else{
	 				// po/warant/dd fine goes here ...
	 			}

	 			// update cheque table..
	 			stupdate.executeUpdate("update Cheque set post_ind='T' where ctrl_no="+cd.getControlNo());

	 			// debit gl to I/W RTN CHQ GL
	 			ret = stupdate.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =12 and gk.ac_type = '1011001'");
	 			if(ret.next())
	 			{
	 				GLTransObject trnobj=new GLTransObject();
	 				trnobj.setTrnDate( cd.getSysdate() );
	 				trnobj.setGLType(ret.getString(2));
	 				trnobj.setGLCode(ret.getString(1));
	 				trnobj.setTrnMode("G");
	 				trnobj.setAmount(cd.getTranAmt());
	 				trnobj.setCdind("D");
	 				trnobj.setAccType(cd.getDebitACType());
	 				trnobj.setAccNo(String.valueOf(cd.getDebitACNo()));
	 				trnobj.setTrnType("");
	 				trnobj.setRefNo(0);
	 				trnobj.setTrnSeq(0);
	 				trnobj.setVtml(cd.getDeTml());
	 				trnobj.setVid(cd.getDeUser());System.out.println(5);
	 				trnobj.setVDate(cd.getDeTime());
	 				comm.storeGLTransaction(trnobj);
	 				System.out.println(6);
	 			}

	 			// credit gl to clg_INApexBank
	 			ret = stupdate.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =1 and gk.ac_type = '1011001'");
	 			if(ret.next())
	 			{
	 				GLTransObject trnobj=new GLTransObject();
	 				trnobj.setTrnDate(  cd.getSysdate() );
	 				trnobj.setGLType(ret.getString(2));
	 				trnobj.setGLCode(ret.getString(1));
	 				trnobj.setTrnMode("G");
	 				trnobj.setAmount(cd.getTranAmt());
	 				trnobj.setCdind("C");
	 				trnobj.setAccType(cd.getDebitACType());
	 				trnobj.setAccNo(String.valueOf(cd.getDebitACNo()));
	 				trnobj.setTrnType("");
	 				trnobj.setRefNo(0);
	 				trnobj.setTrnSeq(0);
	 				trnobj.setVtml(cd.getDeTml());
	 				trnobj.setVid(cd.getDeUser());System.out.println(7);
	 				trnobj.setVDate(cd.getDeTime());

	 				comm.storeGLTransaction(trnobj);
	 				
	 				System.out.println(8);
	 			}
	 			return ctrlno;
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		} catch (CreateException e) {
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		catch(InsufficientBalanceException e)
	 		{
	 			sessionContext.setRollbackOnly();
	 		    throw new InsufficientBalanceException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return -1;
	 	}
	 	
	 	public int setChequeNo(ChequeDepositionObject cd)throws RecordNotInsertedException
	 	{
	 	
	 		Connection conn=null;
	 		ResultSet rs=null;
	 		int f = -1;
	 		
	 		ResultSet ret,result=null;
	 		try{
	 			conn = getConnection();
	 			Statement stupdate = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stupdate1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			 f = stupdate.executeUpdate("update ChequeNo set chq_del = 'F' , chq_payee = null , chq_amt = 0.0 where ac_type ='"+ cd.getDebitACType()+ "' and ac_no = "+ cd.getDebitACNo()+" and chq_no = "+ cd.getQdpNo() );
	 			 
	 			 if(f == 0){
	 				 sessionContext.setRollbackOnly();
	 				 throw new RecordNotInsertedException();
	 			 }
	 			 f = stupdate1.executeUpdate("update Cheque set ret_norm = 'N' where  ctrl_no = " + cd.getControlNo());
	 			 
	 		}catch(RecordNotInsertedException res){
	 			
	 			sessionContext.setRollbackOnly();
	 			throw res;
	 		}catch(SQLException sq){
	 			
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}finally{
	 			
	 			try{
	 				conn.close();
	 			}catch(SQLException ss){
	 				ss.printStackTrace();
	 			}
	 		}
	 		return f;
	 	}
	 	
	 	
	 	public ChequeDepositionObject[] getBouncedDetails(String clgdate,String vetml,String veuser,String date ,int type)throws RecordsNotFoundException
	 	{
	 		ChequeDepositionObject[] co =null;
	 		int i=0,trn_seq=0,cat_type=0;
	 		Connection conn=null;
	 	
	 		System.out.println("insside clearing bean");
	 		

	 		try
	 		{
	 			ResultSet rs=null,res=null,ret=null,result=null;
	 			conn = getConnection();
	 			comm=commonHome.create();
	 			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement st1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stupdate = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 			System.out.println("inside bean....");
	 			
	 			rs = stat.executeQuery("select ch.*,brm.br_name,bm.bank_name from Cheque ch,BranchMaster brm,BankMaster bm where concat(right(clg_date,4),'-',mid(clg_date,locate('/',clg_date)+1,(locate('/',clg_date,4)-locate('/',clg_date)-1)),'-',left(clg_date,locate('/',clg_date)-1)) <= '"+Validations.convertYMD(clgdate)+"' and clg_type='I' and to_bounce='T' and ret_norm='R' and post_ind='F' and  ch.doc_sou=brm.br_code and substring(ch.bank_cd,4,3) = bm.bank_code and prev_ctrl_no=0 and ch.chqddpo is not null and ch.ve_tml is not null");
	 			rs.last();
	 				
	 			if(rs.getRow() > 0){
	 				
	 				System.out.println("lines are"+ rs.getRow());
	 				co = new ChequeDepositionObject[rs.getRow()];
	 				rs.beforeFirst();
	 				while(rs.next()){
	 					co[i] = new ChequeDepositionObject();
	 					co[i].setControlNo(rs.getLong("ctrl_no"));
	 					
	 					System.out.println("no is" +rs.getString("ctrl_no"));
	 					
	 					co[i].setAckNo(rs.getInt("ack_no"));
	 					co[i].setSendTo(rs.getInt("doc_sou"));
	 					co[i].setCompanyName(rs.getString("brm.br_name"));
	 					co[i].setCreditACType(rs.getString("ret_norm"));
	 					co[i].setCreditACNo(rs.getInt("prev_ctrl_no"));
	 					co[i].setPayeeName(rs.getString("payee_name"));
	 					co[i].setBankCode(rs.getString("bank_cd"));
	 					co[i].setBankName(rs.getString("bm.bank_name"));
	 					co[i].setBranchName(rs.getString("br_name"));
	 					
	 					System.out.println("type is" +rs.getString("chqddpo"));
	 					
	 					co[i].setChqDDPO(rs.getString("chqddpo"));
	 					co[i].setDebitACType(rs.getString("dr_ac_type"));
	 					co[i].setDebitACNo(rs.getInt("dr_ac_no"));
	 					co[i].setTranAmt(rs.getDouble("trn_amt"));
	 					co[i].setQdpNo(rs.getInt("qdp_no"));
	 					co[i].setQdpDate(rs.getString("qdp_dt"));
	 					co[i].setDeTime(rs.getString("ve_dt_time"));

	 					co[i].setBounceInd(rs.getString("to_bounce"));
	 					co[i].setFineAmt(rs.getDouble("fine_amt"));//fine column

	 					co[i].setVeTml("F");//dummy value

	 					System.out.println("bounce "+i);


	 					Vector rea_arr = new Vector(0,1);
	 					res = stupdate.executeQuery(" select reason_cd,r_code from Reason left join ReasonLink on reason_cd=r_code where ctrl_no="+co[i].getControlNo());
	 					while(res.next()){
	 						
	 						rea_arr.add(new Integer(res.getInt(1)));
	 					}
	 					co[i].setReasonArray(rea_arr);

	 					// if insuffient fund only exsit
	 					// check for current balance in account transaction
	 					// iff pass the ctrl no
	 					res.last();
	 					
	 					System.out.println("row count "+res.getRow());
	 					
	 					if( res.getRow()==1 ) {
	 						
	 						res.beforeFirst();
	 						res.next();
	 					
	 						if(res.getInt(2)==1 && co[i].getChqDDPO().equals("C")){
	 							ResultSet res1=null;
	 							// check chq amount with acc tran...
	 							//res = stupdate.executeQuery("select cl_bal,clg_chq_min_bal from AccountTransaction ,GenParam where ac_type='"+co[i].getDebitACType()+"' and ac_no="+co[i].getDebitACNo()+" and ve_tml is not null order by trn_seq desc limit 1");

	 							if(co[i].getDebitACType().startsWith("1002")||co[i].getDebitACType().startsWith("1007")){

	 								res1 = stupdate.executeQuery("select cl_bal from AccountTransaction,Modules where ac_type='"+co[i].getDebitACType()+"' and ac_no="+co[i].getDebitACNo()+" and modulecode=1011001 and ve_tml is not null order by trn_seq desc limit 1");
	 							}
	 							else if(co[i].getDebitACType().startsWith("1014")||co[i].getDebitACType().startsWith("1015")){

	 								res1 = stupdate.executeQuery("select cl_bal from ODCCTransaction,Modules where ac_type='"+co[i].getDebitACType()+"' and ac_no="+co[i].getDebitACNo()+" and modulecode=1011001 and ve_tml is not null order by trn_seq desc limit 1");
	 							}
	 							res1.next();
	 							System.out.println("res1.getDouble(1)"+res1.getDouble(1));
	 							System.out.println("co[i].getTranAmt()"+co[i].getTranAmt());

	 							if( (res1.getDouble(1) >= co[i].getTranAmt() && type==2)){
	 								//Debit entry..
	 								AccountTransObject am = new AccountTransObject();
	 								am.setAccType(co[i].getDebitACType());
	 								am.setAccNo(co[i].getDebitACNo());
	 								am.setTransType("P");
	 								am.setTransAmount(co[i].getTranAmt());
	 								am.setTransMode("G");
	 								am.setTransDate(co[i].getDeTime());
	 								am.setTransSource(vetml);
	 								am.setCdInd("D");
	 								am.setChqDDNo(co[i].getQdpNo());
	 								am.setChqDDDate(co[i].getQdpDate());
	 								am.setTransNarr("Clg Ctrl No "+co[i].getControlNo());
	 								am.setRef_No(0);
	 								am.setPayeeName(co[i].getPayeeName());
	 								am.setCloseBal(co[i].getTranAmt());//set -value
	 								am.setLedgerPage(0);
	 								am.uv.setUserTml(vetml);
	 								am.uv.setUserId(veuser);
	 								am.uv.setUserDate(co[i].getDeTime());
	 								am.uv.setVerTml(vetml);
	 								am.uv.setVerDate(co[i].getDeTime());
	 								am.uv.setVerId(veuser);

	 								// call common impl method
	 								comm.storeAccountTransaction(am);
	 								System.out.println("its here");
	 								// update cheque table..
	 								/*stupdate.executeUpdate("update Cheque set post_ind='T' where ctrl_no="+co[i].getControlNo());
	 								// update chequeNo table..for blocking the chq
	 								stupdate.executeUpdate("update ChequeNo set chq_amt="+co[i].getTranAmt()+",chq_del='T' where ac_type='"+co[i].getDebitACType()+"' and ac_no="+co[i].getDebitACNo());
	 								 */// Gl code goes here .....
	 								if(co[i].getDebitACType().startsWith("1002")||co[i].getDebitACType().startsWith("1007"))
	 									ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");

	 								else if(co[i].getDebitACType().startsWith("1014")||co[i].getDebitACType().startsWith("1015"))
	 									ret = stupdate.executeQuery("select odt.ac_type,odt.trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");

	 								ret.next();
	 								trn_seq=ret.getInt(2);
	 								if(ret.getInt(1)==0)
	 									cat_type=1;
	 								else
	 									cat_type=2;
	 								ResultSet ret1 = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+am.getAccType()+" and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 								if(ret1.next())
	 								{
	 									GLTransObject trnobj=new GLTransObject();
	 									trnobj.setTrnDate(date);
	 									trnobj.setGLType(ret1.getString(3));
	 									trnobj.setGLCode(ret1.getString(1));
	 									trnobj.setTrnMode("G");
	 									trnobj.setAmount(am.getTransAmount()*ret1.getInt(2));
	 									trnobj.setCdind("D");
	 									trnobj.setAccType(am.getAccType());
	 									trnobj.setAccNo(String.valueOf(am.getAccNo()));
	 									trnobj.setTrnType("P");
	 									trnobj.setRefNo(0);
	 									trnobj.setTrnSeq(trn_seq);
	 									trnobj.setVtml(vetml);
	 									trnobj.setVid(veuser);
	 									System.out.println(34);

	 									comm.storeGLTransaction(trnobj);
	 									System.out.println(35);
	 								}

	 								// credit gl to clg_INApexBank
	 								result= st1.executeQuery("select * from BranchMaster where br_code="+co[i].getSendTo());
	 								result.next();
	 								String acc_type=result.getString("br_ac_type");

	 								int acc_no=result.getInt("br_ac_no");

	 								ResultSet	ret2 = stupdate.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =1 and gk.ac_type = '1011001'");
	 								if(ret2.next())
	 								{
	 									GLTransObject trnobj=new GLTransObject();
	 									trnobj.setTrnDate( date );
	 									trnobj.setGLType(ret2.getString(2));
	 									trnobj.setGLCode(ret2.getString(1));
	 									trnobj.setTrnMode("G");
	 									trnobj.setAmount(am.getTransAmount());
	 									trnobj.setCdind("C");
	 									trnobj.setAccType(acc_type);
	 									trnobj.setAccNo(String.valueOf(acc_no));
	 									trnobj.setTrnType("");
	 									trnobj.setRefNo(0);
	 									trnobj.setTrnSeq(0);
	 									trnobj.setVtml(vetml);
	 									trnobj.setVid(veuser);
	 									System.out.println(34);

	 									comm.storeGLTransaction(trnobj);System.out.println(35);
	 								}

	 							}
	 							co[i].setBalanceInd(true);//ctrlno passed  
	 						}


	 					}
	 						else
	 								co[i].setBalanceInd(false);// to show msg -ve balance allowed
	 						
	 					
	 					
	 					// update chequeNo table..for blocking the chq
	 					stupdate.executeUpdate("update ChequeNo set chq_amt="+co[i].getTranAmt()+",chq_del='T' where ac_type='"+co[i].getDebitACType()+"' and ac_no="+co[i].getDebitACNo()+ " and chq_no = "+ co[i].getQdpNo());
	 					
	 					i++;
	 				}
	 			
	 			}
	 			else{
	 				
	 				
	 				System.out.println("Rcord not found exception in clg bean....1");
	 				co=null;
	 				return co;
	 				
	 				}
	 		}
	 		catch(SQLException getinward){
	 			getinward.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		catch(Exception e)
	 		{
	 			e.printStackTrace();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return co;
	 	}
	 	
	 	
	 	public  ChequeDepositionObject[] getOutstationCheque(String date)throws RecordsNotFoundException {
	 		
	 		
	 		ChequeDepositionObject[] chqdep_obj = null;
	 		
	 		Connection conn = null;
	 		
	 		try{
	 			
	 			conn =getConnection();
	 			Statement stmt = conn.createStatement();
	 			ResultSet rs = stmt.executeQuery(" select  * from Cheque where clg_type = 'S' and ve_tml is not null  and desp_ind = 'F' and concat(right(substring( ve_dt_time,1,11 ),4),'-',mid(substring( ve_dt_time,1,11 ),locate('/',substring( ve_dt_time,1,11 ))+1,(locate('/',substring( ve_dt_time,1,11 ),4)-locate('/',substring( ve_dt_time,1,11 ))-1)),'-',left(substring( ve_dt_time,1,11 ),locate('/',substring( ve_dt_time,1,11 ))-1)) <= '"+Validations.convertYMD(date)+"' ");
	 	
	 			if (rs.next()){
	 				
	 				rs.last();
	 				chqdep_obj = new ChequeDepositionObject[rs.getRow()];
	 				
	 				rs.beforeFirst();
	 				int i = 0;
	 				while (rs.next()){
	 					
	 					chqdep_obj[i] = new ChequeDepositionObject();
	 					chqdep_obj[i].setControlNo(rs.getLong("ctrl_no"));
	 					
	 					System.out.println("no is" +rs.getString("ctrl_no"));

	 					chqdep_obj[i].setSendTo(rs.getInt("doc_sou"));
	 					chqdep_obj[i].setDocDestination(rs.getInt("doc_dest"));
	 					chqdep_obj[i].setCreditACType(rs.getString("cr_ac_type"));
	 					chqdep_obj[i].setCreditACNo(rs.getInt("cr_ac_no"));
	 					chqdep_obj[i].setPayeeName(rs.getString("payee_name"));
	 					chqdep_obj[i].setBankCode(rs.getString("bank_cd"));
	 					
	 					chqdep_obj[i].setChqDDPO(rs.getString("chqddpo"));
	 					chqdep_obj[i].setTranAmt(rs.getDouble("trn_amt"));
	 					chqdep_obj[i].setQdpNo(rs.getInt("qdp_no"));
	 					chqdep_obj[i].setQdpDate(rs.getString("qdp_dt"));

	 					i++;
	 				}
	 				
	 			} else 
	 				throw new RecordsNotFoundException();
	 			
	 		} catch ( SQLException sql ) {
	 			
	 			sessionContext.setRollbackOnly();
	 			throw new RecordsNotFoundException();
	 			
	 		} finally{
	 			
	 			try{
	 				
	 				conn.close();

	 			} catch( SQLException sqlex){
	 				
	 				sqlex.printStackTrace();
	 			}
	 			
	 		}
	 		
	 		
	 		
	 		return chqdep_obj;
	 	}
	 	
	 	 public void setOutstationLetter( Vector<Integer> ctrl_no ) throws RecordNotInsertedException {
	 		 
	 		 
	 		 
	 		 Connection conn = null;
	 		 
	 		 try {
	 			 
	 			 conn = getConnection();
	 			 Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			 
	 			 System.out.println("inside the outstation cheque");
	 			 for( int i = 0; i< ctrl_no.size(); i++){
	 				 
	 				 System.out.println( ctrl_no.get(i).intValue());
	 				 stmt.addBatch(" update Cheque set desp_ind = 'T' where ctrl_no = " + ctrl_no.get(i).intValue());
	 				 
	 			 }
	 			 
	 			  int d[]= stmt.executeBatch();
	 			  
	 		 } catch ( SQLException sql){
	 			 
	 			 sql.printStackTrace();
	 			 sessionContext.setRollbackOnly();
	 			 throw new RecordNotInsertedException();
	 		 } finally{
	 			 
	 			 try{
	 				 
	 				conn.close(); 
	 			 } catch ( SQLException ss){
	 				 
	 				 
	 			 }
	 			 
	 		 }
	 		 
	 		
	 	 }
	 	
	 	
	 	public int[] verifyInwardData(long ctrlno,String vetml,String veuser,String ve_date,String account_type,int account_no,int opt,String sysdate) throws RecordNotInsertedException
	 	{
	 		
	 		int value=0,cat_type=0,trn_seq=0,custtype=0,mem_cat=0,fd_ac_no=0;
	 		int[] value_arr=null;
	 		double trn_amt=0.0;
	 		long ref_no=0;
	 		
	 		Connection conn=null;
	 		ResultSet res=null,ret=null,rr=null;
	 		ShareMasterObject sh=null;
	 		DepositMasterObject dp=null;
	 		try{
	 			conn = getConnection();
	 			comm=commonHome.create();
	 			share=sharehome.create();
	 			term= termhome.create();
	 			
	 			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stupdate = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			PreparedStatement ps1=null;
	 			
	 			if(account_type != null && account_no >0)
	 			    res=stat.executeQuery("select ch.*,cd.* from Cheque ch,ChequeDetails cd where ch.ctrl_no="+ctrlno+" and ch.prev_ctrl_no=cd.ctrl_no and cd.cr_ac_type='"+account_type+"'  and cd.cr_ac_no="+account_no+" and ve_tml is null ");
	 			else
	 				res = stat.executeQuery("select ch.* from Cheque ch where ctrl_no="+ctrlno+" and ve_tml is null ");
	 			
	 			
	 			res.next();
	 			double amt=0.00;
	 			String d_acc_type=null;
	 			int d_acc_no=0;
	 			int bank_cd= res.getInt("ch.bank_cd");
	 			int doc_sou = res.getInt("ch.doc_sou");
	 			long prev_ctrl_no=res.getInt("ch.prev_ctrl_no");
	 			String de_tml=res.getString("ch.de_tml");
	 			String de_user=res.getString("ch.de_user");
	 			String de_date=res.getString("ch.de_dt_time");
	 			String type= res.getString("ch.clg_type");
	 			String chq_dd_po=res.getString("ch.chqddpo");
	 			int chq_no=res.getInt("ch.qdp_no");
	 			String chq_dt=res.getString("ch.qdp_dt");
	 			int ack_no=res.getInt("ch.ack_no");
	 			String acc_type=res.getString("ch.cr_ac_type");
	 			int acc_no=res.getInt("ch.cr_ac_no");
	 			
	 		if(account_type != null && account_no >0)
	 		{
	 			amt=res.getDouble("cd.cr_amt");
	 			d_acc_type=res.getString("cd.cr_ac_type");
	 			d_acc_no=res.getInt("cd.cr_ac_no");
	 		}
	 		else
	 		{
	 		    amt=res.getDouble("ch.trn_amt");
	 			d_acc_type=res.getString("ch.dr_ac_type");
	 			d_acc_no=res.getInt("ch.dr_ac_no"); 
	 		}
	 		double fine_amt=res.getDouble("fine_amt");
	 		String payee_name=res.getString("ch.payee_name");
	 		String ret_norm=res.getString("ch.ret_norm");
	 		String post_ind=res.getString("ch.post_ind");
	 		String prev_post_ind=null;
	 		
	 		if ( prev_ctrl_no > 0)
	 		{
	 		    ResultSet rr1 = stat.executeQuery("select post_ind from Cheque where ctrl_no="+prev_ctrl_no);
	 			rr1.next();
	 			
	 			prev_post_ind = rr1.getString("post_ind");
	 		 
	 			if( prev_ctrl_no > 0 )//For post The Cheque First in case of outward return
	 			{
	 				ret = stupdate.executeQuery("select * from Cheque where ctrl_no="+prev_ctrl_no);
	 				ret.next();
	 			
	 				if(ret.getString("post_ind").equals("F"))
	 				{
	 							
	 					ret = stupdate.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =3 and gk.ac_type = '1011001'");
	 					if(ret.next())
	 					{
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(sysdate);
	 						trnobj.setGLType(ret.getString(2));
	 						trnobj.setGLCode(ret.getString(1));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(amt);
	 						trnobj.setCdind("D");
	 						trnobj.setAccType(d_acc_type);
	 						trnobj.setAccNo(String.valueOf(d_acc_no));
	 						trnobj.setTrnType("");
	 						trnobj.setRefNo(0);
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(vetml);
	 						trnobj.setVid(veuser);
	 						trnobj.setVDate(ve_date);
	 					
	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);System.out.println(35);
	 					}
	 				
	 				AccountTransObject am=null;
	 				
	 				if(d_acc_type.startsWith("1002")||d_acc_type.startsWith("1007")||d_acc_type.startsWith("1014")||d_acc_type.startsWith("1015"))
	 				{
	 				
	 					am = new AccountTransObject();
	 					am.setAccType(d_acc_type);
	 					am.setAccNo(d_acc_no);
	 					am.setTransType("P");
	 					am.setTransAmount(amt);
	 					am.setTransMode("G");
	 					am.setTransSource(de_tml);
	 					am.setCdInd("C");
	 					am.setChqDDNo(chq_no);
	 					am.setChqDDDate(chq_dt);
	 					am.setTransNarr("Clg Ctrl No "+ctrlno);
	 					am.setRef_No(0);
	 					am.setTransDate(sysdate);
	 					am.setPayeeName(payee_name);
	 					am.setCloseBal(amt);
	 					am.setLedgerPage(0);
	 					am.uv.setUserTml(de_tml);
	 					am.uv.setUserId(de_user);
	 					am.uv.setUserDate(de_date);
	 				
	 					am.uv.setVerTml(vetml);
	 					am.uv.setVerId(veuser);
	 					am.uv.setVerDate(ve_date);

	 					value=comm.storeAccountTransaction(am);
	 			
	 				} else if(d_acc_type.startsWith("1001")|| d_acc_type.startsWith("1004"))
	 				{
	 						int ros= st.executeUpdate("update Cheque set post_ind='T' where ctrl_no="+prev_ctrl_no);
	 						System.out.println("posted"+ros);
	 				}
	 				
	 				
	 				if(d_acc_type.startsWith("1002") || d_acc_type.startsWith("1007"))
	 					ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 				
	 				else if(d_acc_type.startsWith("1014") || d_acc_type.startsWith("1015"))
	 					ret = stupdate.executeQuery("select odt.ac_type,cid from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
	 				
	 				else if(d_acc_type.startsWith("1001"))
	 					ret = stupdate.executeQuery("select odt.ac_type,mem_cat from ShareMaster od,ShareTransaction odt where od.ac_no="+d_acc_no+" and od.ac_type='"+d_acc_type+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
	 					
	 				ret.next();
	 				if(d_acc_type.startsWith("1002")||d_acc_type.startsWith("1007")||d_acc_type.startsWith("1014")||d_acc_type.startsWith("1015"))
	 				{
	 					trn_seq=ret.getInt(2);
	 				
	 					int cid	=ret.getInt("cid");
	 					System.out.println("cid is"+ cid);
	 					rr= stupdate.executeQuery("select custtype from CustomerMaster where cid="+ cid );
	 					rr.next();
	 					System.out.println("custtype is"+ rr.getInt("custtype"));	
	 					custtype=rr.getInt("custtype");
	 					
	 					if(custtype==0)
	 						cat_type=1;
	 					else
	 						cat_type=2;
	 				}
	 				else if(d_acc_type.startsWith("1001"))
	 				{
	 					 mem_cat=ret.getInt("mem_cat");
	 					
	 				}
	 				
	 				if(d_acc_type.startsWith("1001"))
	 					ret = stupdate.executeQuery("select gk.prm_gl_code,mult_by,gk.prm_gl_type from ShareParam gk,GLPost gp where gk.ac_type='"+d_acc_type+"' and gk.mem_cat="+mem_cat+" and prm_ty='D'and trn_type='A' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.prm_gl_code");
	 				
	 				else if(d_acc_type.startsWith("1002")||d_acc_type.startsWith("1007")||d_acc_type.startsWith("1014")||d_acc_type.startsWith("1015"))
	 				ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 				
	 				if(ret.next())
	 				{
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(sysdate);
	 					trnobj.setGLType(ret.getString(3));
	 					trnobj.setGLCode(ret.getString(1));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(amt*ret.getInt(2));
	 					trnobj.setCdind("C");
	 					trnobj.setAccType(d_acc_type);
	 					trnobj.setAccNo(String.valueOf(d_acc_no));
	 					
	 					if(d_acc_type.startsWith("1001"))
	 						trnobj.setTrnType("A");
	 					else if(d_acc_type.startsWith("1002")||d_acc_type.startsWith("1007")||d_acc_type.startsWith("1014")||d_acc_type.startsWith("1015"))
	 					
	 					trnobj.setTrnType("P");
	 					trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 					trnobj.setTrnSeq(trn_seq);
	 					trnobj.setVtml(vetml);
	 					trnobj.setVid(veuser);
	 					trnobj.setVDate(ve_date);
	 					System.out.println(34);

	 					comm.storeGLTransaction(trnobj);System.out.println(35);
	 				}		
	 			
	 				if(d_acc_type.startsWith("1012"))
	 				{
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(sysdate);
	 					trnobj.setGLType(d_acc_type);
	 					trnobj.setGLCode(String.valueOf(d_acc_no));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(amt);
	 					trnobj.setCdind("C");
	 					trnobj.setAccType("");
	 					trnobj.setAccNo("");
	 					trnobj.setTrnType("");
	 					trnobj.setRefNo(0);
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(vetml);
	 					trnobj.setVid(veuser);
	 					trnobj.setVDate(ve_date);
	 					
	 					System.out.println(34);

	 					comm.storeGLTransaction(trnobj);System.out.println(35);
	 					
	 				}
	 				}
	 		
	 				System.out.println("chq_dd_po"+chq_dd_po);
	 				System.out.println("acc_no"+d_acc_no);
	 				System.out.println("acc_type"+d_acc_type.substring(0,4));
	 				System.out.println("fine_amt"+fine_amt);
	 		
	 			}
	 		 
	 		}
	 		
	 		
	 		
	 		 if(chq_dd_po.equalsIgnoreCase("P")&& opt==2 )//payorder
	 		 {

				System.out.println("its payorder");
				// Gl goes here..
				ret = stupdate.executeQuery("select gl_code,gl_type,ac_type from GLKeyParam where ac_type='1016001' and code=3");
				if(ret.next())
				{
					GLTransObject trnobj=new GLTransObject();
					trnobj.setTrnDate(sysdate);
					trnobj.setGLType(ret.getString(2));
					trnobj.setGLCode(ret.getString(1));
					trnobj.setTrnMode("G");
					trnobj.setAmount(amt);
					trnobj.setCdind("D");
					trnobj.setAccType(ret.getString(3));
					trnobj.setAccNo(String.valueOf(chq_no));
					trnobj.setTrnType("");
					trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
					trnobj.setTrnSeq(0);
					trnobj.setVtml(vetml);
					trnobj.setVid(veuser);
					trnobj.setVDate(ve_date);
					System.out.println(34);

					comm.storeGLTransaction(trnobj);System.out.println(35);
					
					value = stupdate.executeUpdate("update PayOrder set po_csh_ind=1,po_csh_dt='"+ve_date+"' where po_chq_no="+chq_no);
				}
			}//end po

	 		
	 			//if(res.getString(1).equals("F"))// if instrument not bounced
	 		if( (type.equals("I")&& post_ind.equals("F"))|| (type.equals("I") && prev_ctrl_no >0 && (doc_sou %1111)==0))//for chq
	 		{
	 		
	 			System.out.println("opt"+opt);
	 		    if(opt==0)///if   allowing -ve Balance
	 		    {
	 		
	 			 				System.out.println("Debitting parties acc");	
	 			 				AccountTransObject am=null;
	 								
	 					
	 					if(d_acc_type.startsWith("1001") && prev_post_ind.equals("F") )
	 					{
	 					
	 						System.out.println("not posted");
	 						ResultSet res1=stat.executeQuery("select lst_trn_seq from ShareMaster where ac_type='"+d_acc_type+"' and ac_no="+d_acc_no+"");
	 						res1.next();
	 						trn_seq = res1.getInt("lst_trn_seq")+1;
	 						int trn_seq1=res1.getInt("lst_trn_seq");

	 						ResultSet rs=stat.executeQuery("select share_bal from ShareTransaction where ac_type='"+d_acc_type+"'  and ac_no="+d_acc_no+" and trn_seq="+trn_seq1+"");
	 						rs.next();
	 				
	 						double share_bal=rs.getDouble("share_bal");
	 						
	 						stat.executeUpdate("update ShareMaster set lst_trn_seq="+trn_seq+" where ac_type='"+d_acc_type+"' and ac_no="+d_acc_no+"" );
	 					 
	 					 
	 						ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+ctrlno+",?,"+trn_seq+",date_format(sysdate(),'%d/%m/%Y'),'W',?,"+ctrlno+",?,?,?,'D',?,?,?,null,null,null,null,'T','F',?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),?)");
	 						ps1.setString(1,d_acc_type);
	 						ps1.setInt(2,d_acc_no);
	 						ps1.setDouble(3,amt);
	 						ps1.setString(4,"Ctrl No "+ctrlno);
	 						ps1.setString(5,"G");
	 						ps1.setString(6,vetml);
	 						ps1.setString(7,"D");
	 						ps1.setString(8,"P");
	 						ps1.setDouble(9,share_bal-amt);
	 						
	 						ps1.setString(10,de_user);
	 						ps1.setString(11,de_tml);
	 						ps1.setString(12,veuser);
	 						ps1.setString(13,vetml);
	 						ps1.executeUpdate();
	 
	 					}
	 					else if(d_acc_type.startsWith("1001") && prev_post_ind.equals("T"))	
	 					{
	 				
	 							System.out.println("posted");
	 							sh= new ShareMasterObject();
	 							
	 							ResultSet rs=stat.executeQuery("select mem_cat from ShareMaster where ac_no="+d_acc_no+" and ac_type='"+d_acc_type+"'");
	 							rs.next();
	 							int me_cat=rs.getInt("mem_cat");
	 						

	 							ResultSet r1= st.executeQuery("select trn_seq ,trn_amt,trn_date from ShareTransaction where trn_narr='Ctrl No "+prev_ctrl_no+"' ");
	 							r1.next();
	 							
	 							trn_amt= r1.getDouble("trn_amt");
	 							int tr_seq=r1.getInt("trn_seq");
	 							
	 							System.out.println("out is"+r1.getString("trn_amt"));
	 							sh.setAmount(trn_amt);
	 							sh.setShareNumber(d_acc_no);
	 							sh.setShareAccType(d_acc_type);
	 							sh.setRecievedMode("Clg");
	 							sh.setMemberCategory(me_cat);
	 							sh.uv.setUserId(de_user);
	 							sh.uv.setUserTml(de_tml);
	 							sh.uv.setUserDate(ve_date);
	 							sh.uv.setVerId(veuser);
	 							sh.uv.setVerTml(vetml);
	 							sh.uv.setVerDate(ve_date);
	 							sh.setRecievedAccno(Integer.parseInt(String.valueOf(ctrlno)));
	 							
	 							
	 							
	 							
	 							ResultSet r= st.executeQuery("select sm.sh_ind,st.trn_no from ShareMaster sm,ShareTransaction st where sm.ac_type='"+d_acc_type+"' and sm.ac_no= "+d_acc_no+" and sm.ac_no=st.ac_no and sm.ac_type=st.ac_type and sm.lst_trn_seq=st.trn_seq");
	 							r.next();
	 							sh.setShareStatus(r.getString("sm.sh_ind"));
	 							sh.setTranNumber(r.getInt("st.trn_no"));
	 							
	 							System.out.println("ok");
	 							share.storeShare(sh,2,0,0);
	 							
	 							ResultSet r2=st.executeQuery("select gl_code,gl_type,trn_amt from GLTransaction where ref_tr_type='A' and ref_tr_seq="+tr_seq+" and trn_amt != "+trn_amt+"");
	 							while(r2.next())
	 							{
	 								
	 								GLTransObject trnobj=new GLTransObject();
	 								trnobj.setTrnDate(sysdate);
	 								trnobj.setGLType(r2.getString(2));
	 								trnobj.setGLCode(r2.getString(1));
	 								trnobj.setTrnMode("G");
	 								trnobj.setAmount(r2.getDouble(3));
	 								trnobj.setCdind("D");
	 								trnobj.setAccType(d_acc_type);
	 								trnobj.setAccNo(String.valueOf(d_acc_no));
	 								trnobj.setTrnType("W");
	 								
	 								
	 								trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 								trnobj.setTrnSeq(tr_seq);
	 								trnobj.setVtml(vetml);
	 								trnobj.setVid(veuser);
	 								trnobj.setVDate(ve_date);
	 								System.out.println(34);

	 								comm.storeGLTransaction(trnobj);System.out.println(35);
	 							
	 								
	 							}
	 					}			
	 						
	 					if(d_acc_type.startsWith("1003")&& prev_post_ind.equals("T")|| d_acc_type.startsWith("1005")&& prev_post_ind.equals("T"))
	 					{
	 					    int val=0;
	 					    dp= new DepositMasterObject();
	 					    dp.setAccType(d_acc_type);
	 					    dp.setAccNo(d_acc_no);
	 					    dp.setAutoRenwlNo(3);
	 					    dp.setTransferType("Clg");
	 					    dp.userverifier.setVerId(veuser);
	 					    dp.userverifier.setVerTml(vetml);
	 					    dp.setCumInterest(0.00);
	 					    dp.setCloseInd(1);
	 					    dp.setClosedt(ve_date);	
	 					    dp.setLastTrnSeq(ctrlno);
	 					    
	 					    try
	 					    {
	 					    	
	 					    	value= term.closeFDAccount(dp);
	 					    
	 					    }catch(Exception e)
	 					    {
	 					        e.printStackTrace();
	 					    }
	 					    
	 					   
	 					}
	 					
	 					if(d_acc_type.startsWith("1012"))
	 					{

	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(sysdate);
	 						trnobj.setGLType(d_acc_type);
	 						trnobj.setGLCode(String.valueOf(d_acc_no));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(amt);
	 						trnobj.setCdind("D");
	 						trnobj.setAccType("");
	 						trnobj.setAccNo(String.valueOf(""));
	 						
	 						trnobj.setTrnType("P");
	 						trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 						trnobj.setTrnSeq(trn_seq);
	 						trnobj.setVtml(vetml);
	 						trnobj.setVid(veuser);
	 						trnobj.setVDate(ve_date);
	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);System.out.println(35);
	 				
	 					}
	 						
	 						
	 					 if(d_acc_type.startsWith("1002") || d_acc_type.startsWith("1007")|| d_acc_type.startsWith("1014") || d_acc_type.startsWith("1015"))
	 					{
	 						 	// debit entry..
	 						 
	 						 am = new AccountTransObject();
	 						 am.setAccType(d_acc_type);
	 						 am.setAccNo(d_acc_no);
	 						 am.setTransType("P");
	 						 am.setTransAmount(amt);
	 						 am.setTransMode("G");
	 						 am.setTransSource(de_tml);
	 						 am.setTransDate(sysdate);
	 						 am.setCdInd("D");
	 						 am.setChqDDNo(chq_no);
	 						 am.setChqDDDate(chq_dt);
	 						 am.setTransNarr("Clg Ctrl No "+ctrlno);
	 						 am.setRef_No(0);
	 						 am.setPayeeName(payee_name);
	 						 am.setCloseBal(amt);
	 						 am.setLedgerPage(0);
	 						 am.uv.setUserTml(de_tml);
	 						 am.uv.setUserId(de_user);
	 						 am.uv.setUserDate(de_date);
	 						 am.uv.setVerTml(vetml);
	 						 am.uv.setVerId(veuser);
	 						 am.uv.setVerDate(ve_date);

	 						 value=comm.storeAccountTransaction(am);
	 					}
	 					
	 					 if(d_acc_type.startsWith("1004") )
	 					{
	 					    ResultSet rs_close= st.executeQuery("select ref_no from DepositTransaction where ac_no="+d_acc_no+" and ac_type='"+d_acc_type+"'and trn_seq=1");
	 					    rs_close.next();
	 					     ref_no=rs_close.getLong("ref_no");
	 					    
	 					    
	 					   {
	 						   ret=stupdate.executeQuery("select dt.ac_type,trn_seq,rd_bal,cum_int from DepositMaster dm,DepositTransaction dt where dt.ac_type='"+d_acc_type+"' and dt.ac_no="+d_acc_no+" and dm.ac_no=dt.ac_no and dm.ac_type=dt.ac_type order by trn_seq desc limit 1");
	 						   ret.next();
	 						   trn_seq=ret.getInt(2)+1;
	 						
	 						// credit entry
	 						//stupdate.executeUpdate("insert into DepositTransaction values('"++"',"+res.getInt(7)+","+(trn_seq+1)+",'"+sysdate+"','D',"+res.getDouble(3)+",null,null,null,"+(ret.getDouble("rd_bal")+res.getDouble(3))+",null,null,"+ctrlno+",'"+res.getString(8)+"','G','"+vtml+"','C',"+ret.getDouble("cum_int")+",'"+vtml+"','"+vuser+"',date_format(sysdate(),'%d/%m/%Y %r'),'"+vtml+"','"+vuser+"',date_format(sysdate(),'%d/%m/%Y %r'))");
	 						
	 						   ps1=conn.prepareStatement("insert into DepositTransaction values(?,?,?,?,'D',?,null,null,null,?,null,null,?,?,?,?,'D',?,?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'))");
	 						   ps1.setString(1,d_acc_type);
	 						   ps1.setInt(2,d_acc_no);
	 						   ps1.setInt(3,trn_seq);//trnseq
	 						   ps1.setString(4,ve_date);
	 						   ps1.setDouble(5,amt);
	 						
	 						   if(ref_no!=prev_ctrl_no)
	 							   ps1.setDouble(6,ret.getDouble("rd_bal")-amt);
	 						   else
	 							   ps1.setDouble(6,amt);    
	 						   
	 						   ps1.setInt(7,0);
	 						   ps1.setString(8,"Ctrl No "+ctrlno);
	 						   ps1.setString(9,"G");
	 						   ps1.setString(10,vetml);
	 						   ps1.setDouble(11,ret.getDouble(4));
	 						   ps1.setString(12,de_tml);
	 						   ps1.setString(13,de_user);
	 						   ps1.setString(14,vetml);
	 						   ps1.setString(15,veuser);
	 						   
	 						   ps1.executeUpdate();
	 						
	 					    
	 					   }
	 					}
	 					
	 					// GL goes here..

	 					 if(d_acc_type.startsWith("1002") || d_acc_type.startsWith("1007"))
	 						ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 					else if(d_acc_type.startsWith("1014") || d_acc_type.startsWith("1015"))
	 						ret = stupdate.executeQuery("select odt.ac_type,cid from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
	 					else if(d_acc_type.startsWith("1001"))
	 						ret = stupdate.executeQuery("select odt.ac_type,mem_cat from ShareMaster od,ShareTransaction odt where od.ac_no="+d_acc_no+" and od.ac_type='"+d_acc_type+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
	 					else if((d_acc_type.startsWith("1003")&& prev_post_ind.equals("T")) ||(d_acc_type.startsWith("1005")&& prev_post_ind.equals("T")))
	 					    ret=stupdate.executeQuery("select att.ac_type,am.last_tr_seq,cid from DepositMaster am,DepositTransaction att where am.ac_type='"+d_acc_type+"' and am.ac_no="+d_acc_no+" and am.ac_type=att.ac_type and am.ac_no=att.ac_no order by trn_seq desc limit 1");
	 					else if((d_acc_type.startsWith("1004")) )
	 					    ret=stupdate.executeQuery("select att.ac_type,am.last_tr_seq,cid from DepositMaster am,DepositTransaction att where am.ac_type='"+d_acc_type+"' and am.ac_no="+d_acc_no+" and am.ac_type=att.ac_type and am.ac_no=att.ac_no order by trn_seq desc limit 1");
	 					if(! d_acc_type.startsWith("1012") )	
	 					ret.next();
	 					
	 					
	 					if(d_acc_type.startsWith("1002")||d_acc_type.startsWith("1007")||d_acc_type.startsWith("1014")||d_acc_type.startsWith("1015")||(d_acc_type.startsWith("1003")&& prev_post_ind.equals("T"))||(d_acc_type.startsWith("1005")&& prev_post_ind.equals("T"))||d_acc_type.startsWith("1004"))
	 					{
	 						trn_seq=ret.getInt(2);
	 					
	 						int cid	=ret.getInt("cid");
	 						System.out.println("cid is"+ cid);
	 						
	 						rr= stupdate.executeQuery("select custtype from CustomerMaster where cid="+ cid );
	 						rr.next();
	 						
	 						System.out.println("custtype is"+ rr.getInt("custtype"));	
	 						custtype=rr.getInt("custtype");

	 						if(custtype==0)
	 							cat_type=1;
	 						else
	 							cat_type=2;
	 					}
	 					else if(d_acc_type.startsWith("1001"))
	 					{
	 						 mem_cat=ret.getInt("mem_cat");
	 						
	 					}
	 					
	 					if(d_acc_type.startsWith("1001"))
	 						ret = stupdate.executeQuery("select gk.prm_gl_code,mult_by,gk.prm_gl_type from ShareParam gk,GLPost gp where gk.ac_type='"+d_acc_type+"' and gk.mem_cat="+mem_cat+" and prm_ty='D'and trn_type='W' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.prm_gl_code");
	 					
	 					else if(d_acc_type.startsWith("1003") || d_acc_type.startsWith("1004")||d_acc_type.startsWith("1005"))
	 					    ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+d_acc_type+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 					else if(d_acc_type.startsWith("1002")||d_acc_type.startsWith("1007")||d_acc_type.startsWith("1014")||d_acc_type.startsWith("1015"))
	 					ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 					
	 					if( ret.next() )
	 					{
	 						if(! d_acc_type.startsWith("1012") )	
	 						{	
	 							System.out.println("Debit GL"); 						
	 						
	 							GLTransObject trnobj=new GLTransObject();
	 							trnobj.setTrnDate(sysdate);
	 							trnobj.setGLType(ret.getString(3));
	 							trnobj.setGLCode(ret.getString(1));
	 							trnobj.setTrnMode("G");
	 						
	 							if(d_acc_type.startsWith("1001")&& prev_post_ind.equals("T"))
	 								trnobj.setAmount(trn_amt*ret.getInt(2));
	 							else if(d_acc_type.startsWith("1002")||d_acc_type.startsWith("1007")||d_acc_type.startsWith("1014")||d_acc_type.startsWith("1015")||d_acc_type.startsWith("1004")||(d_acc_type.startsWith("1003")&& prev_post_ind.equals("T"))||(d_acc_type.startsWith("1005")&& prev_post_ind.equals("T")))
	 								trnobj.setAmount(amt*ret.getInt(2));
	 							
	 							trnobj.setCdind("D");
	 							trnobj.setAccType(d_acc_type);
	 							trnobj.setAccNo(String.valueOf(d_acc_no));
	 							
	 							if(d_acc_type.startsWith("1001"))
	 								trnobj.setTrnType("W");
	 							else if(d_acc_type.startsWith("1002")||d_acc_type.startsWith("1007")||d_acc_type.startsWith("1014")||d_acc_type.startsWith("1015")||(d_acc_type.startsWith("1003")&& prev_post_ind.equals("T"))||(d_acc_type.startsWith("1005")&& prev_post_ind.equals("T")))
	 								trnobj.setTrnType("P");
	 							
	 							trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 							trnobj.setTrnSeq(trn_seq);
	 							trnobj.setVtml(vetml);
	 							trnobj.setVid(veuser);
	 							trnobj.setVDate(ve_date);

	 							comm.storeGLTransaction(trnobj);System.out.println(35);
	 						}
	 					}
	 				
	 					int num=0;
	 					
	 					if ( ref_no == prev_ctrl_no )
	 						num=st.executeUpdate("update DepositMaster set close_ind=1,close_date='"+sysdate+"' where ac_type='"+d_acc_type+"' and ac_no="+d_acc_no);
	 					//update chq details in chequeNo table
	 					System.out.println("three output"+d_acc_type+" "+d_acc_no+" "+chq_no);
	 					
	 					payee_name = payee_name.replace('\'',' ');
	 					value = stupdate.executeUpdate("update ChequeNo set chq_payee='"+payee_name+"',chq_amt="+amt+",chq_del='T' where ac_type='"+d_acc_type+"' and ac_no="+d_acc_no+" and chq_no="+chq_no+"");

	 		    }
	 		    
	 		    
	 		    else if(opt==1)
	 		    {	// not allowing -ve Balance
	 		        
	 		        System.out.println("Not Allowing -ve Balance");
	 		        String ac_type=null;
	 				int ac_no=0;
	 			
	 				ResultSet rs= stmt1.executeQuery(" select * from BranchMaster where br_code="+doc_sou);
	 				if(rs.next())
	 				{
	 					acc_type=rs.getString("br_ac_type");
	 					acc_no=rs.getInt("br_ac_no");
	 				}
	 				
	 				ret = stupdate.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =12 and gk.ac_type = '1011001'");
	 				
	 				if(ret.next())
	 				{
	 				
	 					System.out.println("its inside gl");
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(sysdate);
	 					trnobj.setGLType(ret.getString(2));
	 					trnobj.setGLCode(ret.getString(1));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(amt);
	 					trnobj.setCdind("D");
	 					trnobj.setAccType(acc_type);
	 					System.out.println("acc_type"+acc_type);
	 					trnobj.setAccNo(String.valueOf(acc_no));
	 					System.out.println("acc_no"+acc_no);
	 					trnobj.setTrnType("");
	 					trnobj.setRefNo(0);
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(vetml);
	 					trnobj.setVid(veuser);
	 					trnobj.setVDate(ve_date);
	 					System.out.println(34);
	 					
	 					comm.storeGLTransaction(trnobj);System.out.println(35);
	 				}
	 			}
	 		}
	 				//end chq
	 				
	 				 				
	 		else if(res.getString(15).equals("W")&& res.getString(30).equals("F"))
	 	 	{
	 	 							// for warrant
	 	 	}
	 		else if(res.getString(15).equals("D") && res.getString(30).equals("F"))
	 		{
	 			// for DD
	 		}
	 				
	 		if(type.equals("I")&& post_ind.equals("F")||( type.equals("I") && prev_ctrl_no >0 && (doc_sou %1111)==0))
	 		{
	 					
	 			System.out.println("Inside crediting apex bank");
	 			// credit gl to clg_INApexBank
	 			String ac_type=null;
	 			int ac_no=0;
	 			System.out.println("Opt in cr ap bk"+opt);
	 			
	 			if(opt!=3)
	 			{
	 				System.out.println("crediting apex bank");
	 				ResultSet rs= stmt1.executeQuery(" select * from BranchMaster where br_code="+doc_sou);
	 				
	 				if(rs.next())
	 				{
	 					 acc_type=rs.getString("br_ac_type");
	 					 acc_no=rs.getInt("br_ac_no");
	 				}
	 				ret = stupdate.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =1 and gk.ac_type = '1011001'");
	 				if(ret.next())
	 				{
	 					
	 					System.out.println("its inside gl");
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(sysdate);
	 					trnobj.setGLType(ret.getString(2));
	 					trnobj.setGLCode(ret.getString(1));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(amt);
	 					trnobj.setCdind("C");
	 					trnobj.setAccType(acc_type);
	 					System.out.println("acc_type"+acc_type);
	 					trnobj.setAccNo(String.valueOf(acc_no));
	 					System.out.println("acc_no"+acc_no);
	 					trnobj.setTrnType("");
	 					trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(vetml);
	 					trnobj.setVid(veuser);
	 					trnobj.setVDate(ve_date);
	 					System.out.println(34);

	 					comm.storeGLTransaction(trnobj);System.out.println(35);
	 				}
	 				
	 			}
	 		}
	 				
	 		else if(bank_cd ==555 )
	 		{
	 			AccountTransObject am = new AccountTransObject();
	 			{
	 						
	 				am.setAccType(d_acc_type);
	 				System.out.println("act acc type"+d_acc_type);
	 				am.setAccNo(d_acc_no);
	 				System.out.println("act acc no"+d_acc_no);
	 				am.setTransType("P");
	 				am.setTransAmount(amt);
	 				am.setTransMode("G");
	 				am.setTransDate(sysdate);
	 				am.setTransSource(de_tml);
	 				am.setCdInd("D");
	 				am.setChqDDNo(chq_no);
	 				am.setChqDDDate(chq_dt);
	 				am.setTransNarr("Ack No "+ack_no);
	 				
	 				//am.setRef_No("");
	 				am.setPayeeName("");
	 				am.setCloseBal(amt);
	 				am.setLedgerPage(0);
	 				am.uv.setUserTml(de_tml);
	 				am.uv.setUserId(de_user);
	 				am.uv.setUserDate(de_date);
	 				am.uv.setVerTml(vetml);
	 				am.uv.setVerId(veuser);
	 				am.uv.setVerDate(ve_date);
	 	
	 				comm.storeAccountTransaction(am);
	 			}
	 					
	 					ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 					ret.next();
	 					trn_seq=ret.getInt(2);
	 			
	 					int cid	=ret.getInt("cid");
	 					System.out.println("cid is"+ cid);
	 					 rr= stupdate.executeQuery("select custtype from CustomerMaster where cid="+ cid );
	 					rr.next();
	 					System.out.println("custtype is"+ rr.getInt("custtype"));	
	 					custtype=rr.getInt("custtype");
	 					
	 					if(custtype==0)
	 						cat_type=1;
	 					else
	 						cat_type=2;
	 					ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+am.getAccType()+" and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 					
	 					if(ret.next())
	 					{
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(sysdate);
	 						trnobj.setGLType(ret.getString(3));
	 						trnobj.setGLCode(ret.getString(1));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(amt*ret.getInt(2));
	 						trnobj.setCdind("D");
	 						trnobj.setAccType(d_acc_type);
	 						trnobj.setAccNo(String.valueOf(d_acc_no));
	 						trnobj.setTrnType("P");
	 						trnobj.setRefNo(0);
	 						trnobj.setTrnSeq(trn_seq);
	 						trnobj.setVtml(vetml);
	 						trnobj.setVid(veuser);
	 						trnobj.setVDate(ve_date);
	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);System.out.println(35);
	 					}

	 					//credit to Clg_InBr gl
	 					System.out.println ("doc source == "+doc_sou);
	 					
	 						ResultSet ret1 = st.executeQuery("select gl_code,gl_type from GLKeyParam  where code = 2 and ac_type = '1011001'");
	 					if(ret1.next())
	 					{
	 						System.out.println (100);
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(sysdate);
	 						System.out.println (200);
	 						trnobj.setGLType(ret1.getString(2));
	 						trnobj.setGLCode(ret1.getString(1));
	 						System.out.println (300);
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(amt);
	 						trnobj.setCdind("C");
	 						trnobj.setAccType(d_acc_type);
	 						trnobj.setAccNo(String.valueOf(d_acc_no));
	 						trnobj.setTrnType("");
	 						trnobj.setRefNo(0);
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(vetml);
	 						trnobj.setVid(veuser);
	 						trnobj.setVDate(ve_date);
	 						
	 						System.out.println(34);
	 		
	 						comm.storeGLTransaction(trnobj);
	 						System.out.println(35);
	 					}
	 					
	 					//Dr. CLRG_OWBR
	 					ret = st.executeQuery("select ac_type,gl_type,gl_code from GLKeyParam  where ac_type='1011001' and code =11");
	 					if(ret.next())
	 					{
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(sysdate);
	 						trnobj.setGLType(ret.getString("gl_type"));
	 						trnobj.setGLCode(ret.getString("gl_code"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(amt);
	 						trnobj.setCdind("D");
	 						trnobj.setAccType(d_acc_type);
	     					trnobj.setAccNo(String.valueOf(d_acc_no));
	 						trnobj.setTrnType("");
	 						trnobj.setRefNo(0);
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(vetml);
	 						trnobj.setVid(veuser);
	 						trnobj.setVDate(ve_date);
	 						System.out.println(34);
	 	
	 						comm.storeGLTransaction(trnobj);
	 						System.out.println(35);
	 					}
	 				//	credit Branch C/A
	 					{
	 						
	 						
	 						
//	 						Credit branch C/A 
	 	 					{	
	 	 					am.setAccType( acc_type);
	 	 					am.setAccNo( acc_no);
	 	 					am.setTransType("P");
	 	 					am.setTransAmount(amt);
	 	 					System.out.println("act amt"+amt);
	 	 					am.setTransMode("G");
	 	 					am.setTransDate(sysdate);
	 	 					am.setTransSource(de_tml);
	 	 					am.setCdInd("C");
	 	 					am.setChqDDNo(chq_no);
	 	 					am.setChqDDDate(chq_dt);
	 	 					am.setTransNarr("Ack No "+ack_no);
	 	 					//am.setRef_No("");
	 	 					am.setPayeeName(payee_name);
	 	 					am.setCloseBal(amt);
	 	 					am.setLedgerPage(0);
	 	 					am.uv.setUserTml(de_tml);
	 	 					am.uv.setUserId(de_user);
	 	 					am.uv.setUserDate(de_date);
	 	 					am.uv.setVerTml(vetml);
	 	 					am.uv.setVerId(veuser);
	 	 					am.uv.setVerDate(ve_date);
	 	 	
	 	 					comm.storeAccountTransaction(am);
	 	 					}
	 	 	
	 					ResultSet rs= stmt1.executeQuery(" select * from BranchMaster where br_code="+doc_sou);
	 	 				if(rs.next())
	 	 				{
	 	 					 acc_type=rs.getString("br_ac_type");
	 	 				
	 	 				 acc_no=rs.getInt("br_ac_no");
	 	 				}
	 					
	 					ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+acc_no+" and am.ac_type='"+acc_type+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 					if(ret.next())
	 					{
	 					trn_seq=ret.getInt(2);
	 					cid	=ret.getInt("cid");
	 				System.out.println("cid is"+ cid);
	 					}
	 				 rr= stupdate.executeQuery("select custtype from CustomerMaster where cid="+ cid );
	 				if(	rr.next())
	 				{
	 					System.out.println("custtype is"+ rr.getInt("custtype"));	
	 				 custtype=rr.getInt("custtype");
	 					if(custtype==0)
	 						cat_type=1;
	 					else
	 						cat_type=2;
	 				}
	 					
	 				
	 					ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+am.getAccType()+" and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 					if(ret.next())
	 			 					{
	 			 						System.out.println (14);
	 			 						GLTransObject trnobj=new GLTransObject();
	 			 						trnobj.setTrnDate(sysdate);
	 			 						trnobj.setGLType(ret.getString("gk.gl_type"));
	 			 						System.out.println (15);
	 			 						trnobj.setGLCode(ret.getString("gk.gl_code"));
	 			 						trnobj.setTrnMode("G");
	 			 						trnobj.setAmount(amt);
	 			 						System.out.println (16);
	 			 						trnobj.setCdind("C");
	 			 						trnobj.setAccType(acc_type);
	 			 						trnobj.setAccNo(String.valueOf(acc_no));
	 			 						System.out.println (18);
	 			 						trnobj.setTrnType("P");
	 			 						//trnobj.setRefNo("");
	 			 						trnobj.setTrnSeq(trn_seq);
	 			 						trnobj.setVtml(vetml);
	 			 						trnobj.setVid(veuser);
	 			 						
	 			 						trnobj.setVDate(ve_date);
	 			 						System.out.println(19);
	 			 	
	 			 						comm.storeGLTransaction(trnobj);
	 			 						System.out.println(20);
	 			 					}
	 			 	
	 				
	 					}
	 		}
	 					//if already posted...o/w			
	 			// if(res.getString(10).equals("R") && res.getString("clg_type").equals("O"))// if instrument bounced and if it is outward che..
	 				// retrieve old ctrl no to chek posted or not
	 				
	 		if( prev_ctrl_no>0 )
	 		{
	 					
	 			System.out.println("debiting fine amt");
	 			
	 			if(  (d_acc_no != 0 )&& (Integer.parseInt(d_acc_type.substring(0,4))==1002 || Integer.parseInt(d_acc_type.substring(0,4))==1007 || Integer.parseInt(d_acc_type.substring(0,4))==1015 || Integer.parseInt(d_acc_type.substring(0,4))==1014 || Integer.parseInt(d_acc_type.substring(0,4))==1017 ) && (fine_amt) >0.00 ){//if chq
	 					//debit entry
	 			
	 				AccountTransObject am =null;		
	 							
	 				if(d_acc_type.startsWith("1002")||d_acc_type.startsWith("1007")||d_acc_type.startsWith("1014")||d_acc_type.startsWith("1015"))
	 				{
	 					am = new AccountTransObject();
	 					am.setAccType(d_acc_type);
	 					am.setAccNo(d_acc_no);
	 					am.setTransType("P");
	 					am.setTransAmount(fine_amt);
	 					am.setTransMode("G");
	 					am.setTransSource(vetml);
	 					am.setCdInd("D");
	 					am.setTransDate(sysdate);
	 					am.setChqDDNo(chq_no);
	 					am.setChqDDDate(chq_dt);
	 					am.setTransNarr("Clg Ctrl No "+ctrlno);
	 					am.setRef_No(0);
	 					am.setPayeeName(payee_name);
	 					am.setCloseBal(amt);
	 					am.setLedgerPage(0);
	 					am.uv.setUserTml(de_tml);
	 					am.uv.setUserId(de_user);
	 					am.uv.setUserDate(de_date);
	 					am.uv.setVerTml(vetml);
	 					am.uv.setVerId(veuser);
	 					am.uv.setVerDate(ve_date);
	 					
	 					value=comm.storeAccountTransaction(am);
	 				
	 				}

	 						// GL goes here..for debit entry fine amt
	 				if(d_acc_type.startsWith("1002") || d_acc_type.startsWith("1007"))
	 					ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 				else if(d_acc_type.startsWith("1014") || d_acc_type.startsWith("1015"))
	 					ret = stupdate.executeQuery("select odt.ac_type,cid from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
	 				
	 				ret.next();
	 				
	 				if(d_acc_type.startsWith("1002")||d_acc_type.startsWith("1007")||d_acc_type.startsWith("1014")||d_acc_type.startsWith("1015"))
	 				{
	 					trn_seq=ret.getInt(2);
	 					
	 					int cid	=ret.getInt("cid");
	 					System.out.println("cid is"+ cid);
	 					rr= stupdate.executeQuery("select custtype from CustomerMaster where cid="+ cid );
	 					rr.next();
	 					
	 					System.out.println("custtype is"+ rr.getInt("custtype"));	
	 					custtype=rr.getInt("custtype");
	 					
	 					if(custtype==0)
	 						cat_type=1;
	 					else
	 						cat_type=2;
	 				}
	 				
	 				if(d_acc_type.startsWith("1002")||d_acc_type.startsWith("1007")||d_acc_type.startsWith("1014")||d_acc_type.startsWith("1015"))
	 					
	 					ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+am.getAccType()+" and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 				if(ret.next())
	 				{
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(sysdate);
	 					trnobj.setGLType(ret.getString(3));
	 					trnobj.setGLCode(ret.getString(1));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(fine_amt*ret.getInt(2));
	 					trnobj.setCdind("D");
	 					trnobj.setAccType(d_acc_type);
	 					trnobj.setAccNo(String.valueOf(d_acc_no));
	 				
	 					if(d_acc_type.startsWith("1002")||d_acc_type.startsWith("1007")||d_acc_type.startsWith("1014")||d_acc_type.startsWith("1015"))
	 						trnobj.setTrnType("P");
	 							
	 					trnobj.setRefNo(0);
	 					trnobj.setTrnSeq(trn_seq);
	 					trnobj.setVtml(vetml);
	 					trnobj.setVid(veuser);
	 					trnobj.setVDate(ve_date);
	 					System.out.println(34);
	 					
	 					comm.storeGLTransaction(trnobj);System.out.println(35);
	 				}
	 				
	 						// profit gl fine amt..
	 				if(! d_acc_type.equals("1001001") )
	 				{
	 					ret = stupdate.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =7 and gk.ac_type = '1011001'");
	 					if(ret.next())
	 					{
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(sysdate);
	 						trnobj.setGLType(ret.getString(2));
	 						trnobj.setGLCode(ret.getString(1));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(fine_amt);
	 						trnobj.setCdind("C");
	 						trnobj.setAccType(d_acc_type);
	 						trnobj.setAccNo(String.valueOf(d_acc_no));
	 						trnobj.setTrnType("");
	 						trnobj.setRefNo(0);
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(vetml);
	 						trnobj.setVid(veuser);
	 						trnobj.setVDate(ve_date);
	 						System.out.println(34);
	 						
	 						comm.storeGLTransaction(trnobj);System.out.println(35);
	 					}
	 				}
	 			}
	 		}						// update cheque postind true
	 		
	 		if( opt !=3) 
	 		{
	 			stat.addBatch("update Cheque set post_ind='T' where ctrl_no in("+ctrlno+","+prev_ctrl_no+" )");//I/w ctrl no
	 			
	 			// end if bounced and o/w cheque
	 			
	 			stat.addBatch("update Reason set ve_tml='"+vetml+"',ve_user='"+veuser+"',ve_dt_time = '"+ve_date+"' where ctrl_no="+ctrlno+" and ve_tml is null");
	 			stat.addBatch("update Cheque set ve_tml='"+vetml+"',ve_user='"+veuser+"',ve_dt_time = '"+ve_date+"',clg_date='"+ve_date+"' where ctrl_no="+ctrlno+" and ve_tml is null");
	 		 			
	 		}
	 		else
	 		{
	 				
	 			stat.addBatch("update Reason set ve_tml='"+vetml+"',ve_user='"+veuser+"',ve_dt_time = '"+ve_date+"' where ctrl_no="+ctrlno+" and ve_tml is null");
	 			stat.addBatch("update Cheque set ve_tml='"+vetml+"',ve_user='"+veuser+"',ve_dt_time ='"+ve_date+"',clg_date='"+ve_date+"' where ctrl_no="+ctrlno+" and ve_tml is null");
	 		}
	 		
	 		value_arr=stat.executeBatch();
	 		System.out.println("updated");
	 		
	 		if((opt !=0 && opt !=3) && chq_dd_po.equals("C"))///////////////for not allowing -ve Balance n present cheque again for identification
	 		{
	 			int   updated=stupdate.executeUpdate("update Cheque set clg_type='O',clg_date=null,send_to=null where ctrl_no="+ctrlno);
	 		 
	 			if(updated>0)
	 				System.out.println("updated "+updated);
	 			else{
	 				sessionContext.setRollbackOnly();
	 				throw new SQLException();
	 			}
	 		}
	 		
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		} catch (CreateException e) {
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return value_arr;
	 	}
	 	
	 	public int deleteInwardData(long ctrlno) throws RecordNotInsertedException
	 	{
	 		int value=0;
	 		Connection conn=null;
	 		try{
	 			conn = getConnection();	
	 			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			//value = stat.executeUpdate("delete from cheque c,reason r using cheque c,reason r where c.ctrl_no="+ctrlno+" and c.ctrl_no=r.ctrl_no");
	 			value = stat.executeUpdate("delete from Reason where ctrl_no="+ctrlno);
	 			value = stat.executeUpdate("delete from Cheque where ctrl_no="+ctrlno);
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return value;
	 	}
	 	
	 	
	 	public double storeMailChg(Vector value,String acc_type)
	 	{
	 		Connection conn=null;
	 		ResultSet rs=null;
	 		Vector vector_mail_chg=new Vector(0,1);
	 		double mail_chg=0.00;
	 		BounceFineObject bf=null;
	 		System.out.println("inside store mailing charges");
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 		
	 		for(int i=0;i<value.size();i++)
	 		{
	 			rs=stat.executeQuery("select mail_chg from BounceFine,ReasonMaster left join ReasonLink on code=r_code where code="+value.get(i)+" and code=b_code and acc_type='"+acc_type+"' order by code ,link_code");
	 		
	 			while(rs.next())
	 			{
	 				vector_mail_chg.add(String.valueOf(rs.getDouble("mail_chg")));
	 			}
	 			System.out.println("values are------"+value.get(i));
	 			System.out.println("acc_type===="+acc_type);
	 		
	 		}	
	 		
	 		
	 		if(vector_mail_chg.size()==1)
	 		{
	 			System.out.println("vector_mail_chg.get(0) = "+vector_mail_chg.get(0));
	 			vector_mail_chg.set(0,vector_mail_chg.get(0));
	 		}
	 		else
	 		{
	 				for(int i=1;i<vector_mail_chg.size();i++)
	 					if(Double.parseDouble(vector_mail_chg.get(i).toString()) > Double.parseDouble(vector_mail_chg.get(0).toString()))
	 					{
	 						vector_mail_chg.set(0,vector_mail_chg.get(i));
	 						System.out.println("max value "+vector_mail_chg.get(0).toString());
	 					}	
	 		
	 		}
	 		

	 		
	 		
	 		}catch(SQLException e)
	 		{
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 			
	 			
	 	}
	 		return Double.parseDouble(vector_mail_chg.get(0).toString());
	 	}
	 	public long storeInwardData(ChequeDepositionObject cd)throws RecordNotInsertedException
	 	{
	 		Connection conn=null;
	 		long ctrlno=-1;
	 		ResultSet res;
	 		try
	 		{
	 			conn = getConnection();
	 			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

	 			if(cd.getControlNo()==0)
	 			{
	 				//ctrlno=Long.parseLong(comm.getGenParam("lst_ctrl_no").toString());
	 				//stat.executeUpdate("update GenParam set lst_ctrl_no="+(++ctrlno));
	 				res=stat.executeQuery("select modulecode,lst_acc_no from Modules where modulecode='1011001'");
	 				res.next();
	 				ctrlno=res.getInt(2)+1;
	 				res.updateLong("lst_acc_no",ctrlno);
	 				res.updateRow();
	 				res.close();
	 			}
	 			
	 			
	 			
	 			
	 			else{
	 				deleteInwardData(cd.getControlNo());//calling delete method
	 				ctrlno = cd.getControlNo();
	 			}
	 			res = stat.executeQuery("select bankcode from Head");
	 			res.next();
	 			PreparedStatement pstmt=conn.prepareStatement("insert into Cheque values('"+ cd.getVe_date()+"',1,1,?,'G',?,?,?,?,'S',1,0,?,null,?,?,?,?,?,null,?,?,null,?,?,?,?,?,?,?,'F','F','F',?,?,null,null,'F',0.00,?,?,?,?,?,date_format(sysdate(),'%d/%m/%Y %r'),null,null,null)");
	 			pstmt.setLong(1,ctrlno);
	 			pstmt.setString(2,cd.getClgType());
	 			pstmt.setInt(3,cd.getAckNo());
	 			
	 			if ( cd.getClgType().equalsIgnoreCase("I")){
	 				pstmt.setInt(4,cd.getSendTo());
	 				pstmt.setInt(5,res.getInt(1));
	 				
	 				pstmt.setString(10,cd.getCreditACType());
	 	 			
	 	 			System.out.println("cd.getCreditACType()"+cd.getCreditACType());
	 	 			
	 	 			pstmt.setInt(11,cd.getCreditACNo());
	 	 			
	 	 			pstmt.setString(12,null);
	 	 			
	 	 			System.out.println("acc_type"+cd.getDebitACType());
	 	 			pstmt.setInt(13,0);
	 	 			
	 			
	 			} else {
	 				
	 				pstmt.setInt(4,cd.getDocSource());
	 				pstmt.setInt(5,cd.getDocDestination());
	 			
	 				pstmt.setString(10,"N");
	 	 			
	 	 			
	 	 			
	 	 			pstmt.setInt(11,0);
	 	 			
	 	 			pstmt.setString(12,cd.getCreditACType());
	 	 			
	 	 			
	 	 			pstmt.setInt(13,cd.getCreditACNo());
	 				
	 			}
	 				
	 			
	 			pstmt.setString(6,cd.getMultiCredit());
	 			pstmt.setString(7,cd.getChqDDPO());
	 			
	 			pstmt.setInt(8,cd.getQdpNo());
	 			pstmt.setString(9,cd.getQdpDate());
	 			
	 			
	 			
	 			pstmt.setString(14,cd.getDebitACType());
	 			
	 			System.out.println("acc_type"+cd.getDebitACType());
	 			pstmt.setInt(15,cd.getDebitACNo());
	 			
	 			System.out.println("acc_no"+cd.getDebitACNo());
	 			pstmt.setString(16,cd.getPayeeName());
	 		
	 			pstmt.setString(17,String.valueOf(cd.getBankCode()));
	 			pstmt.setString(18,cd.getBranchName());
	 			pstmt.setDouble(19,cd.getTranAmt());
	 			pstmt.setString(20,cd.getBounceInd());
	 			pstmt.setDouble(21,cd.getDiscAmt());
	 			pstmt.setDouble(22,cd.getDiscChg());
	 			pstmt.setDouble(23,cd.getFineAmt());
	 			pstmt.setString(24,cd.getLoanAcc());
	 			pstmt.setInt(25,cd.getLoanAcc_No());
	 			pstmt.setString(26,cd.getDeTml());
	 			pstmt.setString(27,cd.getDeUser());
	 			pstmt.executeUpdate();
	 			//add batch
	 			System.out.println("bounce val===>"+cd.getBounceInd());
	 			if(cd.getBounceInd().equals("T")){
	 				Vector reason_arr = cd.getReasonArray();
	 				for(int i=0;i<reason_arr.size();i++){
	 					System.out.println("value "+reason_arr.get(i));
	 					stat.addBatch("insert into Reason values("+ctrlno+","+reason_arr.get(i)+",'"+cd.getDeTml()+"','"+cd.getDeUser()+"','"+ cd.getVe_date()+"',null,null,null)");
	 				}
	 				stat.executeBatch();
	 			}
	 			//return ctrlno;
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return ctrlno;
	 	}
	 	
	 	public ChequeDepositionObject getInwardData(long ctrlno,int type)throws RecordsNotFoundException
	 	{
	 	    ChequeDepositionObject co =null;
	 		Connection conn=null;
	 		try
	 		{
	 			conn = getConnection();
	 			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 			Statement stat1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			ResultSet rs = null;
	 			
	 			if(type==1)//for i/w ctrl
	 				rs = stat.executeQuery("select ch.*,brm.br_name,bm.bank_name,a.reconciled,a.ve_tml from Cheque ch,BranchMaster brm,BankMaster bm,Acknowledge a where ch.ctrl_no="+ctrlno+" and ch.clg_type='I' and ch.doc_sou=brm.br_code and substring(ch.bank_cd, 4,3)=bm.bank_code and a.ack_no=ch.ack_no and ch.ve_tml is null");
	 			else if (type == 12) // for verified i/w clearing 
	 				rs = stat.executeQuery("select ch.*,brm.br_name,bm.bank_name,a.reconciled ,a.ve_tml from Cheque ch,BranchMaster brm,BankMaster bm,Acknowledge a where ch.ctrl_no="+ctrlno+" and ch.clg_type='I' and ch.doc_sou=brm.br_code and substring(ch.bank_cd, 4,3)=bm.bank_code and a.ack_no=ch.ack_no and ch.ve_tml is null");
	 			else if (type == 2) // for ECS
	 				rs = stat.executeQuery("select ch.*,brm.br_name,bm.bank_name , a.reconciled ,a.ve_tml from Cheque ch, Acknowledge a, BranchMaster brm,BankMaster bm where ch.ctrl_no = "+ctrlno+"  and ch.clg_type in ( 'C','D') and ch.doc_sou=brm.br_code and substring(ch.bank_cd,4,3) = bm.bank_code  and a.ack_no = ch.ack_no ");
	 			else
	 				rs = stat.executeQuery("select ch.*,brm.br_name,bm.bank_name from Cheque ch,BranchMaster brm,BankMaster bm where ch.ctrl_no="+ctrlno+" and clg_type ='O' and ch.doc_sou=brm.br_code and substring(ch.bank_cd,4,3)=bm.bank_code");
	 			
	 			rs.last();
	 			
	 			if(rs.getRow() > 0){
	 				co = new ChequeDepositionObject();
	 				
	 				System.out.println("enter");
	 				rs.beforeFirst();
	 				rs.next();
	 				co.setAckNo(rs.getInt("ack_no"));
	 				co.setSendTo(rs.getInt("doc_sou"));
	 				co.setClgType(rs.getString("clg_type"));
	 				co.setCompanyName(rs.getString("brm.br_name"));
	 				co.setCreditACType(rs.getString("ret_norm"));
	 				co.setCreditACNo(rs.getInt("prev_ctrl_no"));
	 				if ( type == 12) {
	 					System.out.println("12 is calling ");
	 					co.setprev_ctrl_no( rs.getInt("prev_ctrl_no") );
	 				}
	 				
	 				co.setPayeeName(rs.getString("payee_name"));
	 				co.setBankCode(rs.getString("bank_cd"));
	 				co.setFineAmt(rs.getDouble("fine_amt"));
	 				co.setBankName(rs.getString("bm.bank_name"));
	 				co.setBranchName(rs.getString("br_name"));
	 				co.setDespInd(rs.getString("desp_ind"));
	 				System.out.println("mult cr"+rs.getString("mult_cr"));
	 				co.setMultiCredit(rs.getString("mult_cr"));
	 				co.setChqDDPO(rs.getString("chqddpo"));
	 				if(type == 1 || type == 12 )
	 				{
	 					co.setDebitACType(rs.getString("dr_ac_type"));
	 					co.setDebitACNo(rs.getInt("dr_ac_no"));
	 					co.setVeUser(rs.getString("reconciled"));//ack reconciled
	 					co.setDeTml(rs.getString("a.ve_tml"));
	 				} else if ( type==2){
	 					
	 					co.setVeUser(rs.getString("reconciled"));//ack reconciled
	 					co.setDeTml(rs.getString("a.ve_tml"));
	 					
	 					if (rs.getString("cr_ac_type")!= null ) 
	 						co.setCreditACType(rs.getString("cr_ac_type"));
	 					else 
	 						co.setCreditACType("0");
	 					
	 					if (rs.getString("cr_ac_no")!= null ) 
	 						co.setCreditACNo(rs.getInt("cr_ac_no"));
	 					else 
	 						co.setCreditACNo(0);
	 					
	 					if (rs.getString("dr_ac_type")!= null ) 
	 						co.setDebitACType(rs.getString("dr_ac_type"));
	 					else 
	 						co.setDebitACType("0");
	 					
	 					if (rs.getString("dr_ac_type")!= null ) 
	 						co.setDebitACNo(rs.getInt("dr_ac_no"));
	 					else 
	 						co.setDebitACNo(0);
	 					
	 					ResultSet rs_st = stat1.executeQuery("select tot_amt, sum(doc_tot + trn_amt)as trn_amt from Cheque ch ,Acknowledge ak where ch.ack_no = ak.ack_no and ch.ack_no= "+ rs.getInt("ack_no")+" group by ch.ack_no"); 
	 					
	 					double amt = 0;
	 					if( rs_st.next())
	 						amt = rs_st.getDouble("tot_amt") - rs_st.getDouble("trn_amt") + rs.getDouble("trn_amt");
	 				
	 					co.setActDiscAmt(amt);
	 				}
	 				else{
	 					co.setDebitACType(rs.getString("cr_ac_type"));
	 					co.setDebitACNo(rs.getInt("cr_ac_no"));
	 				}
	 				co.setTranAmt(rs.getDouble("trn_amt"));
	 				co.setQdpNo(rs.getInt("qdp_no"));
	 				co.setQdpDate(rs.getString("qdp_dt"));
	 				co.setBounceInd(rs.getString("to_bounce"));
	 				co.setVeTml(rs.getString("ve_tml"));
	 				co.setReturn(rs.getString("ret_norm"));
	 				System.out.println("exit");
	 				if(co.getBounceInd()!=null && co.getBounceInd().equals("T"))
	 				{
	 				
	 					Vector rea_arr = new Vector(0,1);
	 					rs = stat.executeQuery("select * from Reason where ctrl_no="+ctrlno);
	 					while(rs.next())
	 					{
	 						rea_arr.add(new Integer(rs.getInt(2)));
	 					}
	 					co.setReasonArray(rea_arr);
	 					//co.setFineAmt(rs.getDouble("fine_amt"));
	 				}
	 			}
	 			else{
	 				co=null;
	 				return co;
	 			}
	 		}catch(SQLException getinward){
	 			getinward.printStackTrace();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return co;
	 	}
	 	public ChequeDepositionObject getmulti_crInwardData(long ctrlno,String ac_type,int ac_no )throws RecordsNotFoundException
	 	{
	 	    ChequeDepositionObject co =null;
	 		Connection conn=null;
	 		try
	 		{
	 			conn = getConnection();
	 			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			ResultSet rs = null;
	 			
	 			
	 				rs = stat.executeQuery("select ch.*,cd.*,brm.br_name,bm.bank_name from Cheque ch,BranchMaster brm,BankMaster bm,ChequeDetails cd where ch.ctrl_no="+ctrlno+" and cd.ctrl_no= ch.ctrl_no and clg_type='O' and ch.doc_sou=brm.br_code and ch.bank_cd=bm.bank_code and cd.cr_ac_type='"+ac_type+"' && cd.cr_ac_no="+ac_no+" ");
	 if(	rs.next())
	 	
	 	{
	 		
	 				co = new ChequeDepositionObject();
	 				
	 				
	 				co.setAckNo(rs.getInt("ack_no"));
	 				co.setSendTo(rs.getInt("doc_sou"));
	 				
	 				co.setCompanyName(rs.getString("brm.br_name"));
	 				co.setCreditACType(rs.getString("ret_norm"));
	 				co.setCreditACNo(rs.getInt("prev_ctrl_no"));
	 				
	 				co.setPayeeName(rs.getString("payee_name"));
	 				co.setBankCode(rs.getString("bank_cd"));
	 				co.setBankName(rs.getString("bm.bank_name"));
	 				co.setBranchName(rs.getString("br_name"));
	 				co.setDespInd(rs.getString("desp_ind"));
	 				
	 				//co.setMultiCredit(rs.getString("chqddpo"));
	 					co.setChqDDPO(rs.getString("chqddpo"));
	 				
	 					co.setDebitACType(rs.getString("cd.cr_ac_type"));
	 					co.setDebitACNo(rs.getInt("cd.cr_ac_no"));
	 				
	 				co.setTranAmt(rs.getDouble("cd.cr_amt"));
	 				co.setQdpNo(rs.getInt("qdp_no"));
	 				co.setQdpDate(rs.getString("qdp_dt"));
	 				co.setBounceInd(rs.getString("to_bounce"));
	 				co.setVeTml(rs.getString("ve_tml"));
	 				co.setReturn(rs.getString("ret_norm"));

	 			}
	 	
	 			
	 		else if(co==null)
	 			{
	 				throw new RecordsNotFoundException();
	 			}
	 		}catch(SQLException getinward){
	 			getinward.printStackTrace();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return co;
	 	}
	 	
	 		
	 	public CompanyMaster[] retrieveSelectiveMultiAccDetails(long ctrlno)throws RecordsNotFoundException
	 	{
	 		ResultSet rs=null;
	 		CompanyMaster[] cm=null;
	 		Connection conn=null;
	 		try
	 		{
	 			conn = getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			rs=stmt.executeQuery("select moduleabbr,cr_ac_no,cr_amt,cr_ac_type from ChequeDetails,Modules where ctrl_no="+ctrlno+" and post_ind = 'F' and cr_ac_type=modulecode");
	 			rs.last();
	 			if(rs.getRow()>0){
	 				
	 				cm=new CompanyMaster[rs.getRow()];
	 				rs.beforeFirst();
	 				int i=0;
	 				while(rs.next())
	 				{
	 					cm[i]=new CompanyMaster();
	 					cm[i].setAccType(rs.getString(1));
	 					cm[i].setAccNo(rs.getInt(2));
	 					cm[i].setDeTml(String.valueOf(rs.getDouble(3)));//for amount
	 					cm[i].setDeUser(rs.getString(4));//for module code
	 					i++;
	 				}
	 			}
	 			else{
	 				cm=null;
	 				throw new RecordsNotFoundException();
	 			}
	 			
	 		}catch(SQLException s){
	 			s.printStackTrace();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cm;
	 	}	
	 	
	 	public ClearingObject getSelectiveDetails(long ctrlno)throws RecordsNotFoundException
	 	{
	 		ClearingObject cl=null;
	 		Connection conn =null;
	 		try{
	 			conn = getConnection(); 
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			ResultSet rs=stmt.executeQuery("select c.*,br.br_name,brm.br_name,bm.bank_name from Cheque c,BankMaster bm,BranchMaster br,BranchMaster brm where ctrl_no="+ctrlno+" and br.br_code=c.send_to and doc_sou = brm.br_code and substring(c.bank_cd,4,3)=bm.bank_code");
	 			rs.last();
	 			if(rs.getRow()>0){
	 				
	 				rs.beforeFirst();
	 				if(rs.next()){
	 					
	 					cl = new ClearingObject();
	 					cl.setClgDate(rs.getString("clg_date"));
	 					cl.setClgNo(rs.getInt("clg_no"));
	 					cl.setSendTo(rs.getInt("send_to"));
	 					cl.setCtrlNo(rs.getLong("ctrl_no"));
	 					cl.setTranType(rs.getString("trn_type"));
	 					cl.setClgType(rs.getString("clg_type"));
	 					cl.setAckNo(rs.getInt("ack_no"));
	 					cl.setDocSource(rs.getInt("doc_sou"));
	 					cl.setDocDestination(rs.getInt("doc_dest"));
	 					cl.setDocBs(rs.getString("doc_bs"));
	 					cl.setNoOfDocs(rs.getInt("no_docs"));
	 					cl.setDocTotal(rs.getDouble("doc_tot"));
	 					cl.setMultiCredit(rs.getString("mult_cr"));
	 					cl.setCompanyName(rs.getString("comp_name"));
	 					cl.setChqDDPO(rs.getString("chqddpo"));
	 					cl.setQdpNo(rs.getInt("qdp_no"));
	 					cl.setQdpDate(rs.getString("qdp_dt"));
	 					cl.setRetNormally(rs.getString("ret_norm"));
	 					cl.setPrevCtrlNo(rs.getInt("prev_ctrl_no"));
	 					//cl.setTranMode(rs.getString("trn_mode"));
	 					cl.setTrfType(rs.getString("trn_type"));
	 					cl.setCreditACType(rs.getString("cr_ac_type"));
	 					cl.setCreditACNo(rs.getInt("cr_ac_no"));
	 					cl.setPOCommission(rs.getDouble("po_comm"));
	 					cl.setDebitACType(rs.getString("dr_ac_type"));
	 					cl.setDebitACNo(rs.getInt("dr_ac_no"));
	 					cl.setPayeeName(rs.getString("payee_name"));
	 					cl.setBankCode(rs.getInt("bank_cd"));
	 					cl.setBranchName(rs.getString("br_name"));
	 					cl.setTranAmt(rs.getDouble("trn_amt"));
	 					cl.setToBounce(rs.getString("to_bounce"));
	 					cl.setDespInd(rs.getString("desp_ind"));
	 					cl.setPostInd(rs.getString("post_ind"));
	 					cl.setDiscInd(rs.getString("disc_ind"));
	 					cl.setDiscAmt(rs.getDouble("disc_amt"));
	 					cl.setExpectedClgDate(rs.getString("exp_clgdt"));
	 					cl.setExpClgNo(rs.getInt("exp_clgno"));
	 					cl.setLetterSent(rs.getString("let_sent"));
	 					cl.setMChangeAmt(rs.getDouble("mchg_amt"));
	 					cl.setFineAmt(rs.getDouble("fine_amt"));
	 					
	 					cl.setDeTml(rs.getString("de_tml"));
	 					cl.setDeUser(rs.getString("de_user"));
	 					cl.setDeTime(rs.getString("de_dt_time"));
	 					cl.setVeTml(rs.getString("ve_tml"));
	 					cl.setVeUser(rs.getString("ve_user"));
	 					cl.setVeTime(rs.getString("ve_dt_time"));
	 					
	 					/*cl.setSourceName(rs.getString(48));
	 					cl.setDestName(rs.getString(49));
	 					cl.setBankName(rs.getString(50));*/
	 					
	 					cl.setSourceName(rs.getString("br.br_name"));
	 					cl.setDestName(rs.getString("brm.br_name"));
	 					cl.setBankName(rs.getString("bm.bank_name"));
	 				}
	 			}
	 			else{
	 				cl=null;
	 				return cl;
	 			}
	 			
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cl;
	 	}
	 	
	 	
	 	public ClearingObject[] getBankName(String date,int arr[])throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		ResultSet rs=null;
	 		ClearingObject[] cd=null;
	 		String str="";
	 		int i=0;
	 		
	 		try{

	 			for(int k=0;k<arr.length;k++)
	 				if((arr.length-1)==k)
	 					str  = str+String.valueOf(arr[k]);
	 				else
	 					str = str+String.valueOf(arr[k])+",";
	 			
	 			conn = getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 			//rs = stmt.executeQuery("select bank_cd,bank_name,ctrl_no,cr_ac_no,trn_amt from Cheque,BankMaster where desp_ind='T' and concat(right(exp_clgdt,4),'-',mid(exp_clgdt,locate('/',exp_clgdt)+1,(locate('/',exp_clgdt,4)-locate('/',exp_clgdt)-1)),'-',left(exp_clgdt,locate('/',exp_clgdt)-1))='"+Validations.convertYMD(date)+"' and ret_norm='N' and clg_no in ("+str+") and bank_cd = bank_code and post_ind='F' and trn_type='G' and clg_type='O' and doc_bs='S' group by bank_cd order by bank_code");
	 			rs = stmt.executeQuery("select bank_cd,bank_name,ctrl_no,cr_ac_no,trn_amt,moduleabbr,cr_ac_no from Cheque,BankMaster,Modules where desp_ind='T' and concat(right(exp_clgdt,4),'-',mid(exp_clgdt,locate('/',exp_clgdt)+1,(locate('/',exp_clgdt,4)-locate('/',exp_clgdt)-1)),'-',left(exp_clgdt,locate('/',exp_clgdt)-1))='"+Validations.convertYMD(date)+"' and ret_norm='N' and clg_no in ("+str+") and substring(bank_cd ,4,3) = bank_code and post_ind='F' and trn_type='G' and clg_type='O' and doc_bs='S' and cr_ac_type=modulecode order by bank_code,ctrl_no");	
	 			rs.last();
	 			if(rs.getRow()>0){
	 				
	 				cd=new ClearingObject[rs.getRow()];
	 				rs.beforeFirst();
	 				while(rs.next())
	 				{
	 					cd[i] = new ClearingObject();
	 					cd[i].setBankName(rs.getString("bank_cd"));
	 					cd[i].setBranchName(rs.getString(2));
	 					cd[i].setControlNo(rs.getInt(3));
	 					cd[i].setCreditACNo(rs.getInt(4));
	 					cd[i].setTranAmt(rs.getDouble(5));
	 					cd[i].setCreditACType(rs.getString(6));
	 					

	 					i++;
	 				}
	 			}
	 			else{
	 				cd=null;
	 				throw new RecordsNotFoundException();
	 			}
	 			
	 		}catch(Exception bankname){
	 			bankname.printStackTrace();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cd;
	 	}
	 	
	 	public ClearingObject[] StoreToFile(String date,String clg_no)throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		ResultSet rs=null;
	 		ClearingObject[] cd=null;
	 		//String str="";
	 		int i=0;
	 		
	 		try{

	 			conn = getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 			rs = stmt.executeQuery("select bank_cd,bank_name,cr_ac_no,trn_amt from Cheque,BankMaster where desp_ind='T' and concat(right(exp_clgdt,4),'-',mid(exp_clgdt,locate('/',exp_clgdt)+1,(locate('/',exp_clgdt,4)-locate('/',exp_clgdt)-1)),'-',left(exp_clgdt,locate('/',exp_clgdt)-1))='"+Validations.convertYMD(date)+"' and clg_no='"+clg_no+"' and bank_cd = bank_code  group by bank_cd order by bank_code");
	 			rs.last();
	 			if(rs.getRow()>0){
	 				System.out.println("reocrds found");
	 				cd=new ClearingObject[rs.getRow()];
	 				rs.beforeFirst();
	 				while(rs.next())
	 				{
	 					cd[i] = new ClearingObject();
	 					cd[i].setBankCode(rs.getInt(1));
	 					cd[i].setBranchName(rs.getString(2));
	 					cd[i].setCreditACNo(rs.getInt(3));
	 					cd[i].setTranAmt(rs.getDouble(4));
	 					i++;
	 				}
	 			}
	 			else{
	 				cd=null;
	 				throw new RecordsNotFoundException();
	 			}
	 	
	 		} catch(RecordsNotFoundException re){
	 			sessionContext.setRollbackOnly();
	 			throw re;
	 		}
	 		catch(Exception bankname){
	 			bankname.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cd;
	 	}
	 	
	 	public int storeSelectivePosting(long ctrlno,String vuser,String vtml, String ve_date, String date,CompanyMaster[] cm) throws RecordNotInsertedException
	 	{
	 		int value=0,cat_type=0,trn_seq=0;
	 		Connection conn=null;
	 		ResultSet res,ret,rr;
	 		int cid=0,custtype=0;
	 		
	 		PreparedStatement ps1;
	 		LoanTransactionObject lob=null;
	 		
	 		
	 		try{
	 			
	 			conn = getConnection();
	 			comm=commonHome.create();
	 			String chq_date=null;
	 			int chq_no=0;
	 			String payee_name=null;
	 			double trn_amt=0.00,disc_amt=0.00;
	 			String de_tml=null,de_user=null,de_date=null,disc_ind=null;
	 			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stupdate = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			res = stat.executeQuery("select mult_cr,comp_name,trn_amt,chqddpo,qdp_no,cr_ac_type,cr_ac_no,payee_name,qdp_dt,disc_ind,disc_amt,de_user,de_tml,de_dt_time from Cheque where ctrl_no="+ctrlno);
	 			if(res.next())
	 			{
	 			chq_no=res.getInt("qdp_no");
	 			chq_date=res.getString("qdp_dt");
	 			de_tml=res.getString("de_tml");
	 			de_user=res.getString("de_user");
	 			de_date=res.getString("de_dt_time");
	 			disc_amt=res.getDouble("disc_amt");
	 			disc_ind=res.getString("disc_ind");
	 			trn_amt=res.getDouble("trn_amt");
	 			payee_name=res.getString("payee_name");
	 			
	 			if(res.getString(1).equals("T")){ // if mult cr
	 				String mcr_ac_type=null;
	 				int mcr_ac_no=0;
	 				double mcr_amount=0;
	 				payee_name=res.getString("comp_name");

	 				for(int i=0;i<cm.length;i++){
	 					if(cm[i].getAccNo() != 0){
	 						int ac_no_case = Integer.parseInt(cm[i].getAccType().substring(0,4));
	 						System.out.println(ac_no_case+"case");
	 						mcr_ac_type=cm[i].getAccType();
	 						mcr_ac_no=cm[i].getAccNo();
	 						mcr_amount=Double.parseDouble(cm[i].getDeTml());

	 						switch(ac_no_case){
	 							case 1002:
	 							case 1014:
	 							case 1015:	
	 							case 1007:
	 							case 1018:	
	 										//delete old row for shadow balance..
	 										stupdate.executeUpdate("delete from AccountTransaction where ac_no="+mcr_ac_no+" and ac_type='"+mcr_ac_type+"' and trn_narr='"+ctrlno+"'");
	 										// credit entry...
	 										AccountTransObject am = new AccountTransObject();
	 										am.setAccType(mcr_ac_type);
	 										am.setAccNo(mcr_ac_no);
	 										am.setTransType("R");
	 										am.setTransAmount(mcr_amount);
	 										am.setTransMode("G");
	 										am.setTransSource(de_tml);
	 										am.setCdInd("C");
	 										am.setTransDate(date);
	 										am.setChqDDNo(res.getInt(5));
	 										am.setChqDDDate(res.getString(9));
	 										am.setTransNarr("Ctrl No "+ctrlno);
	 										am.setRef_No(0);
	 										am.setPayeeName(res.getString(8));
	 										am.setCloseBal(mcr_amount);
	 										am.setLedgerPage(0);
	 										am.uv.setUserTml(de_tml);
											am.uv.setUserId(de_user);
											am.uv.setUserDate(de_date);
											am.uv.setVerTml(vtml);
											am.uv.setVerId(vuser);
											am.uv.setVerDate(ve_date);

	 										value=comm.storeAccountTransaction(am);

	 										ResultSet rs_ret = null;
	 										// GL goes here..for credit entry
	 										if(mcr_ac_type.startsWith("1014") || mcr_ac_type.startsWith("1015"))
	 											rs_ret = stupdate.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
	 										else
	 											rs_ret = stupdate.executeQuery("select am.ac_type,trn_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 										
	 										if(rs_ret.next())
	 											{
	 											
	 											trn_seq=rs_ret.getInt(2);
	 																					
	 										if(rs_ret.getInt(1)==0)
	 											cat_type=1;
	 										else
	 											cat_type=2;
	 											}
	 										
	 										ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 										if(ret.next())
	 										{
	 											GLTransObject trnobj=new GLTransObject();
	 											trnobj.setTrnDate(date);
	 											trnobj.setGLType(ret.getString(3));
	 											trnobj.setGLCode(ret.getString(1));
	 											trnobj.setTrnMode("G");
	 											trnobj.setAmount(mcr_amount*ret.getInt(2));
	 											trnobj.setCdind("C");
	 											trnobj.setAccType(mcr_ac_type);
	 											trnobj.setAccNo(String.valueOf(mcr_ac_no));
	 											trnobj.setTrnType("R");
	 											trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 											trnobj.setTrnSeq(trn_seq);
	 											trnobj.setVtml(vtml);
	 											trnobj.setVid(vuser);
	 											trnobj.setVDate(ve_date);
	 											System.out.println(34);

	 											comm.storeGLTransaction(trnobj);System.out.println(35);
	 										}
	 										
	 										
	 										/////////// for Loan
	 										System.out.println("Control no"+ctrlno);
	 									
	 										ResultSet resc=stupdate.executeQuery("select cr_ac_type,cr_ac_no,loan_ac_type, loan_ac_no from ChequeDetails where ctrl_no="+ctrlno+" and cr_ac_no="+mcr_ac_no+"");
	 										resc.next();
	 										String loan_ac_type=resc.getString("loan_ac_type");
	 										int loan_ac_no=resc.getInt("loan_ac_no");
	 										System.out.println("loan_ac_type*******"+loan_ac_type+""+"Loan_ac_no*******"+loan_ac_no);
	 										
	 										
	 										if(resc.getString("loan_ac_type")!=null && resc.getInt("loan_ac_no")>0)
	 										{
	 										    
	 										    System.out.println("Its inside loan");
	 										  AccountTransObject am1 = new AccountTransObject();
	 											am1.setAccType(mcr_ac_type);
	 										am1.setAccNo(mcr_ac_no);
	 										am1.setTransType("R");
	 										am.setTransDate(date);
	 										    am1.setTransAmount(mcr_amount);
	 										am1.setTransMode("G");
	 										am1.setTransSource(de_tml);
	 										am1.setCdInd("D");
	 										am1.setChqDDNo(chq_no);
	 										am1.setChqDDDate(chq_date);
	 										am1.setTransNarr("Ctrl No "+ctrlno);
	 										am1.setRef_No(0);
	 										am1.setPayeeName(payee_name);
	 										am1.setCloseBal(mcr_amount);
	 										am1.setLedgerPage(0);
	 										am.uv.setUserTml(de_tml);
											am.uv.setUserId(de_user);
											am.uv.setUserDate(de_date);
											am.uv.setVerTml(vtml);
											am.uv.setVerId(vuser);
											am.uv.setVerDate(ve_date);


	 										comm.storeAccountTransaction(am1);
	 										
	 										
	 										ResultSet ret1 = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+mcr_ac_type+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 										if(ret1.next())
	 										{
	 											GLTransObject trnobj=new GLTransObject();
	 											trnobj.setTrnDate(date);
	 											trnobj.setGLType(ret1.getString(3));
	 											trnobj.setGLCode(ret1.getString(1));
	 											trnobj.setTrnMode("G");
	 											trnobj.setAmount(mcr_amount*ret1.getInt(2));
	 											trnobj.setCdind("D");
	 											trnobj.setAccType(mcr_ac_type);
	 											trnobj.setAccNo(String.valueOf(mcr_ac_no));
	 											trnobj.setTrnType("P");
	 											trnobj.setRefNo(0);
	 											trnobj.setTrnSeq(trn_seq);
	 											trnobj.setVtml(vtml);
	 											trnobj.setVid(vuser);
	 											trnobj.setVDate(ve_date);
	 											System.out.println(34);

	 											comm.storeGLTransaction(trnobj);System.out.println(35);
	 										}
	 										
	 										lob= new LoanTransactionObject();							    
	 									    lob.setAccType(loan_ac_type);
	 									    lob.setAccNo(loan_ac_no);
	 									    lob.setTransactionDate(date);
	 									    lob.setTranType("R");
	 									    lob.setTransactionAmount(mcr_amount);
	 									    lob.setTranMode("G");
	 									    lob.setTranSou(vtml);
	 									    lob.setReferenceNo(Integer.parseInt(String.valueOf(ctrlno)));
	 									    lob.setTranNarration("Clg");
	 									    lob.setCdind("C");
	 									    lob.uv.setUserTml(de_tml);
	 									    lob.uv.setUserId(de_user);
	 									   lob.uv.setUserDate(de_date);
	 									   lob.uv.setVerTml(vtml);
	 									   lob.uv.setVerId(vuser) ;
	 									  lob.uv.setVerDate(ve_date);
	 									  Integer trn_seq_object =null;
	 									  
	 						                try
	 										{
	 						                    loanremote=loanHome.create();
	 						                	trn_seq_object= (Integer) loanremote.recoverLoanAccount(lob);
	 										}
	 						                catch(RecordNotUpdatedException rec_update)
	 										{
	 						                	sessionContext.setRollbackOnly();
	 						                	rec_update.printStackTrace();
	 						                	throw new SQLException();
	 										}
	 						                if(trn_seq_object!=null)
	 						                	trn_seq = trn_seq_object.intValue();
	 										
	 										

	 										}									

	 										
	 										
	 										
	 										break;
	 							case 1004:// dep trn with RD
	 										ret=stupdate.executeQuery("select cm.custtype,cm.cid,trn_seq,rd_bal,cum_int from DepositMaster dm,DepositTransaction dt ,CustomerMaster cm where dt.ac_type='"+mcr_ac_type+"'  and dt.ac_no="+mcr_ac_no+" and dm.ac_no=dt.ac_no and dm.ac_type=dt.ac_type and cm.cid=dm.cid order by trn_seq desc limit 1");
	 										if(ret.next())
	 										{
	 										trn_seq=ret.getInt(2)+1;
	 										if(ret.getInt(1)==0)
	 											cat_type=1;
	 										else
	 											cat_type=2;
	 										// credit entry
	 										//stupdate.executeUpdate("insert into DepositTransaction values('"++"',"+mcr_ac_no+","+(trn_seq+1)+",'"+date+"','D',"+mcr_amount+",null,null,null,"+(ret.getDouble("rd_bal")+mcr_amount)+",null,null,"+ctrlno+",'"+res.getString(8)+"','G','"+vtml+"','C',"+ret.getDouble("cum_int")+",'"+vtml+"','"+vuser+"',date_format(sysdate(),'%d/%m/%Y %r'),'"+vtml+"','"+vuser+"',date_format(sysdate(),'%d/%m/%Y %r'))");
	 										ps1=conn.prepareStatement("insert into DepositTransaction values(?,?,?,?,'D',?,null,null,null,?,null,null,?,?,?,?,'C',?,?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'))");
	 										ps1.setString(1,mcr_ac_type);
	 										ps1.setInt(2,mcr_ac_no);
	 										ps1.setInt(3,trn_seq);//trnseq
	 										ps1.setString(4,date);
	 										ps1.setDouble(5,mcr_amount);
	 										ps1.setDouble(6,ret.getDouble("rd_bal")+mcr_amount);
	 										ps1.setInt(7,0);
	 										ps1.setString(8,"Ctrl No "+ctrlno);
	 										ps1.setString(9,"G");
	 										ps1.setString(10,vtml);
	 										ps1.setDouble(11,ret.getDouble(4));
	 										ps1.setString(12,de_tml);
	 										ps1.setString(13,de_user);
	 										ps1.setString(14,vtml);
	 										ps1.setString(15,vuser);
	 										ps1.executeUpdate();
	 										}
	 										else 
	 										{
	 										    System.out.println(" New Account");
	 										}
	 										// gl
	 										ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+mcr_ac_type+"' and trn_type='D' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 										if(ret.next())
	 										{
	 											GLTransObject trnobj=new GLTransObject();
	 											trnobj.setTrnDate(date);
	 											trnobj.setGLType(ret.getString(3));
	 											trnobj.setGLCode(ret.getString(1));
	 											trnobj.setTrnMode("G");
	 											trnobj.setAmount(mcr_amount*ret.getInt(2));
	 											trnobj.setCdind("C");
	 											trnobj.setAccType(mcr_ac_type);
	 											trnobj.setAccNo(String.valueOf(mcr_ac_no));
	 											trnobj.setTrnType("D");
	 											trnobj.setRefNo(0);
	 											trnobj.setTrnSeq(trn_seq);
	 											trnobj.setVtml(vtml);
	 											trnobj.setVid(vuser);
	 											trnobj.setVDate(ve_date);
	 											System.out.println(34);

	 											comm.storeGLTransaction(trnobj);System.out.println(35);
	 										}
	 										else
	 										{
	 										    System.out.println("New Account");
	 										}
	 										
	 										
	 										break;
	 							/*case 1001:// share trn
	 										// creditentry
	 										//ret=stupdate.executeQuery("select trn_no from ShareTransaction where sh_ty='"+mcr_ac_type+"' and sh_no="+mcr_ac_no+" order by trn_seq desc limit 1");
	 										ret=stupdate.executeQuery("select trn_no from ShareTransaction order by trn_seq desc limit 1");
	 										ret.next();
	 										int trn_no = ret.getInt(1);
	 										ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+ret.getInt(1)+",?,date_format(sysdate(),'%d/%m/%Y'),'A',?,?,?,?,?,'C',?,?,?,?,?,null,null,'F','F',?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'))");
	 										ps1.setString(1,mcr_ac_type);
	 										ps1.setInt(2,mcr_ac_no);
	 										ps1.setDouble(3,mcr_amount);
	 										ps1.setInt(4,0);
	 										ps1.setString(5,"Ctrl No "+ctrlno);
	 										ps1.setString(6,"G");
	 										ps1.setString(7,vtml);
	 										ps1.setString(8,"D");
	 										ps1.setString(9,"P");
	 										ps1.setInt(10,0);
	 										ps1.setInt(11,0);
	 										ps1.setInt(12,0);
	 										ps1.setString(13,vtml);
	 										ps1.setString(14,vuser);
	 										ps1.setString(15,vtml);
	 										ps1.setString(16,vuser);
	 										ps1.executeUpdate();
	 										// gl
	 										ret = stupdate.executeQuery("select sh_cap from ShareType,ShareMaster where sh_ty='"+mcr_ac_type+"' and sh_no="+mcr_ac_no+" and sh_type=sh_ty and mem_cat=shcat");
	 										ret.next();
	 										int gl_code = ret.getInt(1);
	 										ret = stupdate.executeQuery("select mult_by from GLPost where gl_code = "+gl_code+" and ac_type = "+mcr_ac_type+" and trn_type='A' and cr_dr='C'");
	 										if(ret.next())
	 										{
	 											GLTransObject trnobj=new GLTransObject();
	 											trnobj.setTrnDate(date);
	 											trnobj.setGLType("GL");
	 											trnobj.setGLCode(String.valueOf(gl_code));
	 											trnobj.setTrnMode("G");
	 											trnobj.setAmount(mcr_amount*ret.getInt(1));
	 											trnobj.setCdind("C");
	 											trnobj.setAccType(mcr_ac_type);
	 											trnobj.setAccNo(String.valueOf(mcr_ac_no));
	 											trnobj.setTrnType("A");
	 											trnobj.setRefNo(0);
	 											trnobj.setTrnSeq(trn_no);
	 											trnobj.setVtml(vtml);
	 											trnobj.setVid(vuser);System.out.println(34);

	 											storeGLTransaction(trnobj);System.out.println(35);
	 										}
	 										break;
	 */							case 1008:// LD..ln trn
	 										break;
	 							case 1009:// locker trn
	 										/*RentTransObject rto=new RentTransObject();
	 										rto.setLockerAcType(mcr_ac_type);
	 										rto.setLockerAcNo(mcr_ac_no);
	 										rto.setTrnSource(vtml);
	 										rto.setTrnMode("G");
	 										rto.setTrfAcNo(0);
	 										rto.setTrfAcType("ctrl no "+ctrlno);
	 										rto.setTrfVoucherNo(0);
	 										rto.setRentAmt(mcr_amount);
	 										rto.uv.setUserId(vtml);
	 										rto.uv.setUserTml(vuser);
	 										storeRentTran(rto);
	 										// gl loker
	 										ret=stupdate.executeQuery("select gl_code,gl_type from glkeyparam where ac_type=1009001 and code=1");
	 										if(ret.next())
	 										{
	 											GLTransObject trn_obj=new GLTransObject();
	 											trn_obj.setTrnDate(date);
	 											trn_obj.setGLType(ret.getString(2));
	 											trn_obj.setGLCode(ret.getString(1));
	 											trn_obj.setTrnMode("G");
	 											trn_obj.setAmount(mcr_amount);
	 											trn_obj.setCdind("C");
	 											trn_obj.setAccType(mcr_ac_type);
	 											trn_obj.setAccNo(String.valueOf(mcr_ac_no));
	 											trn_obj.setTrnSeq(0);
	 											trn_obj.setTrnType("");
	 											trn_obj.setRefNo(0);
	 											trn_obj.setVtml(vtml);
	 											trn_obj.setVid(vuser);

	 											storeGLTransaction(trn_obj);
	 										}*/
	 										break;
	 							case 1010:// Loan ..ln trn
	 										break;

	 						}// end of switch

	 					}

	 					// clg out gl for debit entry...
	 					ret = stupdate.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =3 and gk.ac_type = '1011001'");
	 					if(ret.next())
	 					{
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(date);
	 						trnobj.setGLType(ret.getString(2));
	 						trnobj.setGLCode(ret.getString(1));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(mcr_amount);
	 						trnobj.setCdind("D");
	 						trnobj.setAccType(mcr_ac_type);
	 						trnobj.setAccNo(String.valueOf(mcr_ac_no));
	 						trnobj.setTrnType("");
	 						trnobj.setRefNo(0);
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(vtml);
	 						trnobj.setVid(vuser);
	 						trnobj.setVDate(ve_date);
	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);System.out.println(35);
	 					}


	 					//update chq details in cheque table
	 					value = stupdate.executeUpdate("update ChequeDetails set post_ind='T' where cr_ac_type='"+mcr_ac_type+"' and cr_ac_no="+mcr_ac_no+" and ctrl_no="+ctrlno);
	 				}
	 				//making postind true in cheque table iff cheque detail table all true
	 				res = stat.executeQuery("select * from ChequeDetails where post_ind='F' and ctrl_no="+ctrlno);
	 				res.last();
	 				if(res.getRow() <=0)
	 					value = stupdate.executeUpdate("update Cheque set post_ind='T' where ctrl_no="+ctrlno);
	 			}
	 			else{
	 						// for indvidual posting....
	 				if(res.getInt(7) != 0){
	 					int ac_no_case = Integer.parseInt(res.getString(6).substring(0,4));
	 					int ac_no= res.getInt(7);
	 					String ac_type= res.getString(6);
	 					payee_name=res.getString("payee_name");
	 				double amt=res.getDouble(3);
	 					System.out.println(ac_no_case+"case");
	 					switch(ac_no_case){
	 						case 1002:
	 						case 1014:
	 						case 1015:	
	 						case 1007:
	 						case 1018:	
	 						    		//delete old row for shadow balance..
	 									stupdate.executeUpdate("delete from AccountTransaction where ac_no="+res.getInt(7)+" and ac_type='"+res.getString(6)+"' and trn_narr='"+ctrlno+"'");

	 											 					

	 									
	 									// credit entry...
	 									AccountTransObject am = new AccountTransObject();
	 									am.setAccType(ac_type);
	 									am.setAccNo(ac_no);
	 									am.setTransType("R");
	 									if(disc_ind.equalsIgnoreCase("T"))
	 									    am.setTransAmount(trn_amt-disc_amt);
	 									else
	 									    am.setTransAmount(trn_amt);
	 									am.setTransMode("G");
	 									am.setTransDate(date);
	 									am.setTransSource(de_tml);
	 									am.setCdInd("C");
	 									am.setChqDDNo(chq_no);
	 									am.setChqDDDate(chq_date);
	 									am.setTransNarr("Ctrl No "+ctrlno);
	 									am.setRef_No(0);
	 									am.setPayeeName(payee_name);
	 									am.setCloseBal(trn_amt);
	 									am.setLedgerPage(0);
	 									am.uv.setUserTml(de_tml);
										am.uv.setUserId(de_user);
										am.uv.setUserDate(de_date);
										am.uv.setVerTml(vtml);
										am.uv.setVerId(vuser);
										am.uv.setVerDate(ve_date);

	 									value=comm.storeAccountTransaction(am);

	 									// GL goes here..for credit entry
	 									ResultSet rs_ret = null;
	 									// GL goes here..for credit entry
	 									if(res.getString(6).startsWith("1014") || res.getString(6).startsWith("1015"))
	 										rs_ret = stupdate.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
	 									else if(res.getString(6).startsWith("1002") || res.getString(6).startsWith("1007"))
	 										rs_ret = stupdate.executeQuery("select am.ac_type,trn_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 									
	 									if(rs_ret.next())
	 										{
	 										
	 										trn_seq=rs_ret.getInt(2);
	 																				
	 									if(rs_ret.getInt(1)==0)
	 										cat_type=1;
	 									else
	 										cat_type=2;
	 										}
	 									
	 									
	 									
	 										
	 										/*ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+res.getInt(7)+" and am.ac_type='"+res.getString(6)+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
							 					if(ret.next())
							 					{
							 					trn_seq=ret.getInt(2);
							 					cid	=ret.getInt("cid");
							 				System.out.println("cid is"+ cid);
							 					}
							 					 rr= stupdate.executeQuery("select custtype from CustomerMaster where cid="+ cid );
							 				if(	rr.next())
							 				{
							 					System.out.println("custtype is"+ rr.getInt("custtype"));	
							 				 custtype=rr.getInt("custtype");
							 					if(custtype==0)
							 						cat_type=1;
							 					else
							 						cat_type=2;
							 				}*/

	 										
	 										
	 									
	 								
	 										ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 										if(ret.next())
	 										{
	 											GLTransObject trnobj=new GLTransObject();
	 											trnobj.setTrnDate(date);
	 											trnobj.setGLType(ret.getString(3));
	 											trnobj.setGLCode(ret.getString(1));
	 											trnobj.setTrnMode("G");
	 											trnobj.setAmount(res.getDouble(3)*ret.getInt(2));
	 											trnobj.setCdind("C");
	 											trnobj.setAccType(ac_type);
	 											trnobj.setAccNo(String.valueOf(ac_no));
	 											trnobj.setTrnType("R");
	 											trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 											trnobj.setTrnSeq(trn_seq);
	 											trnobj.setVtml(vtml);
	 											trnobj.setVid(vuser);
	 											trnobj.setVDate(ve_date);
	 											System.out.println(34);

	 											comm.storeGLTransaction(trnobj);System.out.println(35);
	 										}
	 									
	 									
	 									/////////// for Loan
	 									System.out.println("Control no"+ctrlno);
	 									
	 									int mult_by= ret.getInt("mult_by");
	 								
	 									ResultSet resc=stupdate.executeQuery("select cr_ac_type,cr_ac_no,loan_ac_type, loan_ac_no from Cheque where ctrl_no="+ctrlno+" and cr_ac_no="+res.getString(7)+"");
	 									resc.next();
	 									String loan_ac_type=resc.getString("loan_ac_type");
	 									int loan_ac_no=resc.getInt("loan_ac_no");
	 									System.out.println("loan_ac_type*******"+loan_ac_type+""+"Loan_ac_no*******"+loan_ac_no);
	 									
	 									
	 									if(resc.getString("loan_ac_type")!=null && resc.getInt("loan_ac_no")>0)
	 									{
	 									    
	 									    System.out.println("Its inside loan");
	 									  AccountTransObject am1 = new AccountTransObject();
	 										am1.setAccType(ac_type);
	 									am1.setAccNo(ac_no);
	 									am1.setTransType("R");
	 									
	 									    am1.setTransAmount(amt*mult_by);
	 									am1.setTransMode("G");
	 									am1.setTransSource(de_tml);
	 									am1.setCdInd("D");
	 									am.setTransDate(date);
	 									am1.setChqDDNo(chq_no);
	 									am1.setChqDDDate(chq_date);
	 									am1.setTransNarr("Ctrl No "+ctrlno);
	 									am1.setRef_No(0);
	 									am1.setPayeeName(payee_name);
	 									am1.setCloseBal(amt*mult_by);
	 									am1.setLedgerPage(0);
	 									am.uv.setUserTml(de_tml);
										am.uv.setUserId(de_user);
										am.uv.setUserDate(de_date);
										am.uv.setVerTml(vtml);
										am.uv.setVerId(vuser);
										am.uv.setVerDate(ve_date);


	 									comm.storeAccountTransaction(am1);
	 									
	 									
	 									ResultSet ret1 = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+res.getString(6)+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 									if(ret1.next())
	 									{
	 										GLTransObject trnobj=new GLTransObject();
	 										trnobj.setTrnDate(date);
	 										trnobj.setGLType(ret1.getString(3));
	 										trnobj.setGLCode(ret1.getString(1));
	 										trnobj.setTrnMode("G");
	 										trnobj.setAmount(amt * mult_by);
	 										trnobj.setCdind("D");
	 										trnobj.setAccType(ac_type);
	 										trnobj.setAccNo(String.valueOf(ac_no));
	 										trnobj.setTrnType("P");
	 										trnobj.setRefNo(0);
	 										trnobj.setTrnSeq(trn_seq);
	 										trnobj.setVtml(vtml);
	 										trnobj.setVid(vuser);
	 										
	 										trnobj.setVDate(ve_date);
	 										System.out.println(34);

	 										comm.storeGLTransaction(trnobj);System.out.println(35);
	 									}
	 									
	 									lob= new LoanTransactionObject();							    
	 								    lob.setAccType(loan_ac_type);
	 								    lob.setAccNo(loan_ac_no);
	 								    lob.setTransactionDate(date);
	 								    lob.setTranType("R");
	 								    lob.setTransactionAmount(amt * mult_by);
	 								    lob.setTranMode("G");
	 								    lob.setTranSou(de_tml);
	 								    lob.setReferenceNo(Integer.parseInt(String.valueOf(ctrlno)));
	 								    lob.setTranNarration("Clg");
	 								    lob.setCdind("C");
	 								    lob.uv.setUserTml(de_tml);
	 								    lob.uv.setUserId(de_user);
	 								   lob.uv.setUserDate(de_date);
	 								   lob.uv.setVerTml(vtml);
	 								   lob.uv.setVerId(vuser) ;
	 								  lob.uv.setVerDate(ve_date);
	 								  Integer trn_seq_object =null;
	 								  
	 					                try
	 									{
	 					                    loanremote=loanHome.create();
	 					                	trn_seq_object= (Integer) loanremote.recoverLoanAccount(lob);
	 									}
	 					                catch(RecordNotUpdatedException rec_update)
	 									{
	 					                	sessionContext.setRollbackOnly();
	 					                	rec_update.printStackTrace();
	 					                	throw new SQLException();
	 									}
	 					                if(trn_seq_object!=null)
	 					                	trn_seq = trn_seq_object.intValue();
	 									
	 									

	 									}									

	 									
	 									
	 									
	 									break;
	 						case 1004:// dep trn with RD
	 						    ret=stupdate.executeQuery("select cm.custtype,cm.cid,trn_seq,rd_bal,cum_int from DepositMaster dm,DepositTransaction dt ,CustomerMaster cm where dt.ac_type='"+res.getString(6)+"'  and dt.ac_no="+res.getInt(7)+" and dm.ac_no=dt.ac_no and dm.ac_type=dt.ac_type and cm.cid=dm.cid order by trn_seq desc limit 1");
	 							if(ret.next())
	 							{
	 							trn_seq=ret.getInt(2)+1;
	 							if(ret.getInt(1)==0)
	 								cat_type=1;
	 							else
	 								cat_type=2;
	 							
	 									// credit entry
	 									//stupdate.executeUpdate("insert into DepositTransaction values('"++"',"+res.getInt(7)+","+(trn_seq+1)+",'"+date+"','D',"+res.getDouble(3)+",null,null,null,"+(ret.getDouble("rd_bal")+res.getDouble(3))+",null,null,"+ctrlno+",'"+res.getString(8)+"','G','"+vtml+"','C',"+ret.getDouble("cum_int")+",'"+vtml+"','"+vuser+"',date_format(sysdate(),'%d/%m/%Y %r'),'"+vtml+"','"+vuser+"',date_format(sysdate(),'%d/%m/%Y %r'))");
	 									ps1=conn.prepareStatement("insert into DepositTransaction values(?,?,?,?,'D',?,null,null,null,?,null,null,?,?,?,?,'C',?,?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'))");
	 									ps1.setString(1,res.getString(6));
	 									ps1.setInt(2,res.getInt(7));
	 									ps1.setInt(3,trn_seq);//trnseq
	 									ps1.setString(4,date);
	 									ps1.setDouble(5,res.getDouble(3));
	 									ps1.setDouble(6,ret.getDouble("rd_bal")+res.getDouble(3));
	 									ps1.setInt(7,0);
	 									ps1.setString(8,"Ctrl No "+ctrlno);
	 									ps1.setString(9,"G");
	 									ps1.setString(10,vtml);
	 									ps1.setDouble(11,ret.getDouble(4));
	 									ps1.setString(12,de_tml);
	 									ps1.setString(13,de_user);
	 									ps1.setString(14,vtml);
	 									ps1.setString(15,vuser);
	 									ps1.executeUpdate();
	 									}
	 									
	 									else
	 									{
	 									    System.out.println("New Account");
	 									}
	 									// gl
	 									ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+res.getString(6)+"' and trn_type='D' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 									if(ret.next())
	 									{
	 										GLTransObject trnobj=new GLTransObject();
	 										trnobj.setTrnDate(date);
	 										trnobj.setGLType(ret.getString(3));
	 										trnobj.setGLCode(ret.getString(1));
	 										trnobj.setTrnMode("G");
	 										trnobj.setAmount(res.getDouble(3)*ret.getInt(2));
	 										trnobj.setCdind("C");
	 										trnobj.setAccType(res.getString(6));
	 										trnobj.setAccNo(res.getString(7));
	 										trnobj.setTrnType("D");
	 										trnobj.setRefNo(0);
	 										trnobj.setTrnSeq(trn_seq);
	 										trnobj.setVtml(vtml);
	 										trnobj.setVid(vuser);
	 										trnobj.setVDate(ve_date);
	 										System.out.println(34);

	 										comm.storeGLTransaction(trnobj);System.out.println(35);
	 									}
	 									
	 									else
	 									{
	 									    System.out.println("New Account");
	 									}
	 									break;
	 						case 1001:// share trn
	 									// creditentry
	 									//ret=stupdate.executeQuery("select trn_no from ShareTransaction where sh_ty='"+res.getString(6)+"' and sh_no="+res.getInt(7)+" order by trn_seq desc limit 1");
	 /*									ret=stupdate.executeQuery("select trn_no from ShareTransaction order by trn_seq desc limit 1");
	 									ret.next();
	 									int trn_no = ret.getInt(1);
	 									ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+ret.getInt(1)+",?,date_format(sysdate(),'%d/%m/%Y'),'A',?,?,?,?,?,'C',?,?,?,?,?,null,null,'F','F',?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'))");
	 									ps1.setString(1,res.getString(6));
	 									ps1.setInt(2,res.getInt(7));
	 									ps1.setDouble(3,res.getDouble(3));
	 									ps1.setInt(4,0);
	 									ps1.setString(5,"Ctrl No "+ctrlno);
	 									ps1.setString(6,"G");
	 									ps1.setString(7,vtml);
	 									ps1.setString(8,"D");
	 									ps1.setString(9,"P");
	 									ps1.setInt(10,0);
	 									ps1.setInt(11,0);
	 									ps1.setInt(12,0);
	 									ps1.setString(13,vtml);
	 									ps1.setString(14,vuser);
	 									ps1.setString(15,vtml);
	 									ps1.setString(16,vuser);
	 									ps1.executeUpdate();
	 									// gl
	 									ret = stupdate.executeQuery("select sh_cap from ShareType,ShareMaster where sh_ty='"+res.getString(6)+"' and sh_no="+res.getInt(7)+" and sh_type=sh_ty and mem_cat=shcat");
	 									ret.next();
	 									int gl_code = ret.getInt(1);
	 									ret = stupdate.executeQuery("select mult_by from GLPost where gl_code = "+gl_code+" and ac_type = "+res.getString(6)+" and trn_type='A' and cr_dr='C'");
	 									if(ret.next())
	 									{
	 										GLTransObject trnobj=new GLTransObject();
	 										trnobj.setTrnDate(date);
	 										trnobj.setGLType("GL");
	 										trnobj.setGLCode(String.valueOf(gl_code));
	 										trnobj.setTrnMode("G");
	 										trnobj.setAmount(res.getDouble(3)*ret.getInt(1));
	 										trnobj.setCdind("C");
	 										trnobj.setAccType(res.getString(6));
	 										trnobj.setAccNo(res.getString(7));
	 										trnobj.setTrnType("A");
	 										trnobj.setRefNo(0);
	 										trnobj.setTrnSeq(trn_no);
	 										trnobj.setVtml(vtml);
	 										trnobj.setVid(vuser);System.out.println(34);

	 										storeGLTransaction(trnobj);System.out.println(35);
	 									}*/
	 									break;
	 									
	 									
	 									
	 						case 1008:// LD..ln trn
	 									break;
	 						case 1009:// locker trn
	 									/*RentTransObject rto=new RentTransObject();
	 									rto.setLockerAcType(res.getString(6));
	 									rto.setLockerAcNo(res.getInt(7));
	 									rto.setTrnSource(vtml);
	 									rto.setTrnMode("G");
	 									rto.setTrfAcNo(0);
	 									rto.setTrfAcType("ctrl no "+ctrlno);
	 									rto.setTrfVoucherNo(0);
	 									rto.setRentAmt(res.getDouble(3));
	 									rto.uv.setUserId(vtml);
	 									rto.uv.setUserTml(vuser);
	 									storeRentTran(rto);
	 									// gl loker
	 									ret=stupdate.executeQuery("select gl_code,gl_type from glkeyparam where ac_type=1009001 and code=1");
	 									if(ret.next())
	 									{
	 										GLTransObject trn_obj=new GLTransObject();
	 										trn_obj.setTrnDate(date);
	 										trn_obj.setGLType(ret.getString(2));
	 										trn_obj.setGLCode(ret.getString(1));
	 										trn_obj.setTrnMode("G");
	 										trn_obj.setAmount(res.getDouble(3));
	 										trn_obj.setCdind("C");
	 										trn_obj.setAccType(res.getString(6));
	 										trn_obj.setAccNo(res.getString(7));
	 										trn_obj.setTrnSeq(0);
	 										trn_obj.setTrnType("");
	 										trn_obj.setRefNo(0);
	 										trn_obj.setVtml(vtml);
	 										trn_obj.setVid(vuser);

	 										storeGLTransaction(trn_obj);
	 									}*/
	 									break;
	 						case 1010:// Loan ..ln trn
	 									break;
	 						case 1012:// GL only..gl trn only..
	 									GLTransObject trnobj=new GLTransObject();
	 									trnobj.setTrnDate(date);
	 									trnobj.setGLType("GL");
	 									trnobj.setGLCode(res.getString(7));
	 									trnobj.setTrnMode("G");
	 									trnobj.setAmount(res.getDouble(3));
	 									trnobj.setCdind("C");
	 									trnobj.setAccType("");
	 									trnobj.setAccNo("");
	 									trnobj.setTrnType("");
	 									trnobj.setRefNo(0);
	 									trnobj.setTrnSeq(0);
	 									trnobj.setVtml(vtml);
	 									trnobj.setVid(vuser);
	 									trnobj.setVDate(ve_date);
	 									System.out.println(34);

	 									comm.storeGLTransaction(trnobj);System.out.println(35);

	 									break;

	 					}// end of switch

	 					// clg out gl for debit entry...
	 					
	 					ret = stupdate.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =3 and gk.ac_type = '1011001'");
	 					if(ret.next())
	 					{
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(date);
	 						trnobj.setGLType(ret.getString(2));
	 						trnobj.setGLCode(ret.getString(1));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(res.getDouble(3));
	 						trnobj.setCdind("D");
	 						trnobj.setAccType(res.getString(6));
	 						trnobj.setAccNo(res.getString(7));
	 						trnobj.setTrnType("");
	 						trnobj.setRefNo(0);
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(vtml);
	 						trnobj.setVid(vuser);
	 						trnobj.setVDate(ve_date);
	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);System.out.println(35);
	 					}
	 					
	 					//update chq details in cheque table
	 					
	 				}
	 				value = stupdate.executeUpdate("update Cheque set post_ind='T' where ctrl_no="+ctrlno);
	 			}
	 			}
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		} catch (CreateException e) {
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return value;
	 	}
	 	
	 	
	 	
	 	
	 	
	 	
	 	public ChequeDepositionObject[] retrieve_multi_Cheque(int ctrl_no)
	 	{
	 	    Connection conn=null;	
	 	    ChequeDepositionObject[] cd=null;
	 	    try{
	 	        ResultSet rs=null;
	 	        conn=getConnection();
	 	       
	 	        
	 	        Statement stupdate = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 	        rs=stupdate.executeQuery("select cr_ac_type,cr_ac_no,cr_amt from ChequeDetails where ctrl_no="+ctrl_no+"");
	 	       if(rs.next())
	 	           rs.last();
	 	       int count= rs.getRow();
	 	       System.out.println("count is"+count);
	 	       rs.beforeFirst();
	 	       
	 	       cd=new ChequeDepositionObject[count];
	 	       int j=0;
	 	       while(rs.next())
	 	       {
	 	       
	 	           cd[j]=new ChequeDepositionObject();
	 	           System.out.println("rs.getString"+rs.getString("cr_ac_type"));
	 	           cd[j].setCreditACType(rs.getString("cr_ac_type"));
	 	           
	 	           cd[j].setCreditACNo(rs.getInt("cr_ac_no"));
	 	           cd[j].setTranAmt(rs.getDouble("cr_amt"));
	 	       
	 	           j++;
	 	    }
	 	       
	 	    }
	 	    catch(SQLException e)
	 	    {
	 	        e.printStackTrace();
	 	    }
	 	    
	 	    finally
	 	    {
	 	        try{
	 	        conn.close();
	 	        }
	 	        catch(SQLException e)
	 	        {
	 	            e.printStackTrace();
	 	        }
	 	    }
	 	    return cd;
	 	}
	 	
	 	public Vector[] clearingPosting(String clgdate,Vector clgnoarr,Vector bankcdarr,Vector ctrl_no_arr,String vtml,String vuser,String ve_date, String date) throws RecordNotInsertedException
	 	{
	 		Connection conn=null;	
	 		int updated=0;
	 		int trn_seq=0,cat_type=0;
	 		Vector result[]=null;
	 		try
	 		{
	 		    
	 		    LoanTransactionObject lob=null;
	 		  // LoanTransactionObject deplon = null;
	 		    masterObject.loansOnDeposit.LoanTransactionObject deplon=null;
	 		   loanonde = loanondop.create();
	 			PreparedStatement ps1=null;
	 			conn=getConnection();
	 			
	 			comm=commonHome.create();
	 			term= termhome.create();
	 			
	 			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stupdate = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmcr = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stm_pocomm = conn.createStatement( );
	 			
	 			
	 			ResultSet res=null,ret=null,rsmcr=null,rmcr=null;
	 			int ctrlno;

	 			String clgno="",bankcd="",ctrl="";
	 			double po_comm = 0.0;
	 			int prev_ctrl_no = 0 ;
	 			String ac_type=null;
	 			int ac_no;
	 			double chq_amt=0.00d;
	 			String chq_date=null;
	 			int chq_no=0;
	 			String payee_name=null;
	 			String disc_ind=null;
	 			String de_tml=null;
	 			String de_user=null;
	 			String de_date=null;
	 			double disc_amt=0.00d;
	 			
	 			int k=0,t=0;
	 			
	 			for(;k<clgnoarr.size()-1;k++)
	 				clgno = clgno+clgnoarr.elementAt(k)+",";
	 			clgno  = clgno+clgnoarr.elementAt(k);	
	 			
	 			System.out.println(clgno+ " clg no *************************");
	 			for(;t<ctrl_no_arr.size()-1;t++)
	 				ctrl = ctrl+ctrl_no_arr.elementAt(t)+",";
	 			ctrl  = ctrl+ctrl_no_arr.elementAt(t);
	 			System.out.println(ctrl+ " ctrl no *************************");
	 			
	 			
	 			res = stat.executeQuery(" select ch.cr_ac_type,ch.cr_ac_no,ch.trn_amt,ch.de_tml,ch.de_user,ch.de_dt_time,ch.qdp_no,ch.qdp_dt,ch.payee_name,ch.ctrl_no,moduleabbr,ch.mult_cr,ch.disc_ind,ch.disc_amt,ch.po_comm,ch.prev_ctrl_no from Cheque ch,Modules,ChequeDetails cd where clg_no in ("+clgno+") and ch.ctrl_no in("+ctrl+") and concat(right(exp_clgdt,4),'-',mid(exp_clgdt,locate('/',exp_clgdt)+1,(locate('/',exp_clgdt,4)-locate('/',exp_clgdt)-1)),'-',left(exp_clgdt,locate('/',exp_clgdt)-1))='"+Validations.convertYMD(clgdate)+"' and desp_ind='T' and doc_bs='S' and ch.post_ind='F' and clg_type='O' and trn_type='G' and ret_norm='N' and modulecode=ifnull(ch.cr_ac_type,cd.cr_ac_type) group by ch.ctrl_no");
	 			res.last();
	 			int lines=res.getRow();
	 			res.beforeFirst();
	 			
	 			while(res.next()){
	 				
	 				
	 			    ac_type=res.getString("ch.cr_ac_type");
	 			    ac_no=res.getInt("ch.cr_ac_no");
	 			    payee_name= res.getString("ch.payee_name");
	 			    chq_date= res.getString("ch.qdp_dt");  
	 			    chq_no=res.getInt("ch.qdp_no");
	 			    chq_amt=res.getDouble("ch.trn_amt");
	 			    disc_ind=res.getString("ch.disc_ind");
	 			    disc_amt=res.getDouble("ch.disc_amt");
	 			    de_tml=res.getString("ch.de_tml");
	 			    de_user=res.getString("ch.de_user");
	 			    de_date=res.getString("ch.de_dt_time");
	 			    
	 			   ctrlno=res.getInt("ctrl_no");
	 			   po_comm = res.getDouble("ch.po_comm"); 
	 			   prev_ctrl_no = res.getInt("ch.prev_ctrl_no"); 
	 			   
	 				
	 			   if(res.getString("mult_cr").equals("T"))         // if mult cr 
	 			   {                                          
	 					
	 					String mcr_ac_type=null;
	 					int mcr_ac_no=0;
	 					double mcr_amount=0;
	 					

	 					rsmcr = stmcr.executeQuery("select cr_ac_type,cr_ac_no,cr_amt,moduleabbr from ChequeDetails,Modules where ctrl_no="+ctrlno+" and post_ind='F' and cr_ac_type=modulecode");
	 					rsmcr.last();
	 					int length=rsmcr.getRow();
	 					int j=0;
	 					rsmcr.beforeFirst();
	 						while(rsmcr.next()){
	 							System.out.println("yashawa...........................5 ");
	 							int ac_no_case = Integer.parseInt(rsmcr.getString(1).substring(0,4));
	 							mcr_ac_type=rsmcr.getString(1);
	 							mcr_ac_no=rsmcr.getInt(2);
	 							mcr_amount=rsmcr.getDouble(3);

	 							switch(ac_no_case){
	 								case 1002:
	 								case 1014:
	 								case 1015:	
	 								case 1007:
	 								case 1018:	
	 											System.out.println("yashawa...........................6 ");
	 											//delete old row for shadow balance..
	 											if(mcr_ac_type.startsWith("1014") || mcr_ac_type.startsWith("1015"))
	 												stupdate.executeUpdate("delete from ODCCTransaction where ac_no="+mcr_ac_no+" and ac_type='"+mcr_ac_type+"' and trn_narr='"+ctrlno+"'");
	 											else
	 												stupdate.executeUpdate("delete from AccountTransaction where ac_no="+mcr_ac_no+" and ac_type='"+mcr_ac_type+"' and trn_narr='"+ctrlno+"'");
	 											// credit entry...
	 											AccountTransObject am = new AccountTransObject();
	 											am.setAccType(mcr_ac_type);
	 											am.setAccNo(mcr_ac_no);
	 											am.setTransType("R");
	 											am.setTransAmount(mcr_amount);
	 											am.setTransMode("G");
	 											am.setTransSource(de_tml);
	 											am.setCdInd("C");
	 											am.setChqDDNo(res.getInt(7));
	 											am.setChqDDDate(res.getString(8));
	 											am.setTransNarr("Ctrl No "+ctrlno);
	 											am.setRef_No(Integer.parseInt(String.valueOf(ctrlno)));
	 											am.setPayeeName(res.getString(9));
	 											am.setCloseBal(mcr_amount);
	 											am.setTransDate(date);
	 											am.setLedgerPage(0);
	 											am.uv.setUserTml(de_tml);
	 											am.uv.setUserId(de_user);
	 											am.uv.setUserDate(de_date);
	 											am.uv.setVerTml(vtml);
	 											am.uv.setVerId(vuser);
	 											am.uv.setVerDate(ve_date);

	 											comm.storeAccountTransaction(am);
	 											System.out.println("yashawa...........................7 ");
	 											ResultSet rs_ret = null;
	 											// GL goes here..for credit entry
	 											if(mcr_ac_type.startsWith("1014") || mcr_ac_type.startsWith("1015"))
	 												rs_ret = stupdate.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
	 											else
	 												rs_ret = stupdate.executeQuery("select am.ac_type,trn_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 											
	 											if(rs_ret.next())
	 											{
	 												
	 												trn_seq=rs_ret.getInt(2);
	 																						
	 												if(rs_ret.getInt(1)==0)
	 													cat_type=1;
	 												else
	 													cat_type=2;
	 											}
	 											
	 											ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 											if(ret.next())
	 											{
	 												GLTransObject trnobj=new GLTransObject();
	 												trnobj.setTrnDate(date);
	 												trnobj.setGLType(ret.getString(3));
	 												trnobj.setGLCode(ret.getString(1));
	 												trnobj.setTrnMode("G");
	 												trnobj.setAmount(mcr_amount*ret.getInt(2));
	 												trnobj.setCdind("C");
	 												trnobj.setAccType(mcr_ac_type);
	 												trnobj.setAccNo(String.valueOf(mcr_ac_no));
	 												trnobj.setTrnType("R");
	 												trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 												trnobj.setTrnSeq(trn_seq);
	 												trnobj.setVtml(vtml);
	 												trnobj.setVid(vuser);
	 												trnobj.setVDate(ve_date);
	 												//System.out.println(34);
	 												
	 												comm.storeGLTransaction(trnobj);
	 											}
	 											
	 											
	 									/////////// for Loan
	 											System.out.println("Control no"+ctrlno);
	 										
	 											ResultSet resc=stupdate.executeQuery("select cr_ac_type,cr_ac_no,loan_ac_type, loan_ac_no from ChequeDetails where ctrl_no="+ctrlno+" and cr_ac_no="+mcr_ac_no+"");
	 											resc.next();
	 											String loan_ac_type=resc.getString("loan_ac_type");
	 											int loan_ac_no=resc.getInt("loan_ac_no");
	 											System.out.println("loan_ac_type*******"+loan_ac_type+""+"Loan_ac_no*******"+loan_ac_no);
	 											
	 											
	 											if(resc.getString("loan_ac_type")!=null && resc.getInt("loan_ac_no")>0)
	 											{
	 											    
	 												System.out.println("Its inside loan");
	 											    AccountTransObject am1 = new AccountTransObject();
	 											    am1.setAccType(mcr_ac_type);
	 											    am1.setAccNo(mcr_ac_no);
	 											    am1.setTransType("R");
	 											
	 											    am1.setTransAmount(mcr_amount);
	 											    am1.setTransMode("G");
	 											    am1.setTransSource(de_tml);
	 											    am1.setCdInd("D");
	 											    am1.setChqDDNo(chq_no);
	 											    am1.setChqDDDate(chq_date);
	 											    am1.setTransNarr("Ctrl No "+ctrlno);
	 											    am1.setRef_No(0);
	 											    am.setTransDate(date);
	 											    am1.setPayeeName(payee_name);
	 											    am1.setCloseBal(mcr_amount);
	 											    am1.setLedgerPage(0);
	 											    am.uv.setUserTml(de_tml);
	 											    am.uv.setUserId(de_user);
	 											    am.uv.setUserDate(de_date);
	 											    am.uv.setVerTml(vtml);
	 											    am.uv.setVerId(vuser);
	 											    am.uv.setVerDate(ve_date);
	 											    comm.storeAccountTransaction(am1);
	 											
	 											
	 											    ResultSet ret1 = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+mcr_ac_type+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 											    if(ret1.next())
	 											    {
	 											    	GLTransObject trnobj=new GLTransObject();
	 											    	trnobj.setTrnDate(date);
	 											    	trnobj.setGLType(ret1.getString(3));
	 											    	trnobj.setGLCode(ret1.getString(1));
	 											    	trnobj.setTrnMode("G");
	 											    	trnobj.setAmount(mcr_amount*ret1.getInt(2));
	 											    	trnobj.setCdind("D");
	 											    	trnobj.setAccType(mcr_ac_type);
	 											    	trnobj.setAccNo(String.valueOf(mcr_ac_no));
	 											    	trnobj.setTrnType("P");
	 											    	trnobj.setRefNo(0);
	 											    	trnobj.setTrnSeq(trn_seq);
	 											    	trnobj.setVtml(vtml);
	 											    	trnobj.setVid(vuser);
	 											    	trnobj.setVDate(ve_date);
	 											    	System.out.println("chek 111111111111 ");

	 											    	comm.storeGLTransaction(trnobj);
	 											    	System.out.println("chek 222222222 ");
	 											    }
	 											
	 											    lob= new LoanTransactionObject();							    
	 											    lob.setAccType(loan_ac_type);
	 											    lob.setAccNo(loan_ac_no);
	 											    lob.setTransactionDate(date);
	 											    lob.setTranType("R");
	 											    lob.setTransactionAmount(mcr_amount);
	 											    lob.setTranMode("G");
	 											    lob.setTranSou(de_tml);
	 											    lob.setReferenceNo(Integer.parseInt(String.valueOf(ctrlno)));
	 											    lob.setTranNarration("Clg");
	 											    lob.setCdind("C");
	 											    lob.uv.setUserTml(de_tml);
	 											    lob.uv.setUserId(de_user);
	 											    lob.uv.setUserDate(de_date);
	 											    lob.uv.setVerTml(vtml);
	 											    lob.uv.setVerId(vuser) ;
	 											    lob.uv.setVerDate(ve_date);
	 											    Integer trn_seq_object =null;
	 										  
	 											    try
	 											    {
	 											    	loanremote=loanHome.create();
	 											    	trn_seq_object= (Integer) loanremote.recoverLoanAccount(lob);
	 											    }
	 											    catch(RecordNotUpdatedException rec_update)
	 											    {
	 											    	sessionContext.setRollbackOnly();
	 											    	rec_update.printStackTrace();
	 											    	throw new SQLException();
	 											    }
	 											    if(trn_seq_object!=null)
	 											    	trn_seq = trn_seq_object.intValue();
	 											    
	 											

	 											}									
	 											break;

	 								case 1004:         // dep trn with RD
	 											ret=stupdate.executeQuery("select cm.custtype,cm.cid,trn_seq,rd_bal,cum_int from DepositMaster dm,DepositTransaction dt ,CustomerMaster cm where dt.ac_type='"+mcr_ac_type+"'  and dt.ac_no="+mcr_ac_no+" and dm.ac_no=dt.ac_no and dm.ac_type=dt.ac_type and cm.cid=dm.cid order by trn_seq desc limit 1");
	 											if(ret.next())
	 											{
	 												trn_seq=ret.getInt(2)+1;
	 												if(ret.getInt(1)==0)
	 													cat_type=1;
	 												else
	 													cat_type=2;
	 												
	 											// credit entry
	 												ps1=conn.prepareStatement("insert into DepositTransaction values(?,?,?,?,'D',?,null,null,null,?,null,null,?,?,?,?,'C',?,?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'))");
	 												ps1.setString(1,mcr_ac_type);
	 												ps1.setInt(2,mcr_ac_no);
	 												ps1.setInt(3,trn_seq);//trnseq
	 												ps1.setString(4,date);
	 												ps1.setDouble(5,mcr_amount);
	 												ps1.setDouble(6,ret.getDouble("rd_bal")+mcr_amount);
	 												ps1.setInt(7,ctrlno);
	 												ps1.setString(8,"Ctrl No "+ctrlno);
	 												ps1.setString(9,"G");
	 												ps1.setString(10,de_tml);
	 												ps1.setDouble(11,ret.getDouble(4));
	 												ps1.setString(12,de_tml);
	 												ps1.setString(13,de_user);
	 												ps1.setString(14,vtml);
	 												ps1.setString(15,vuser);
	 											
	 												ps1.executeUpdate();
	 											}
	 											// gl
	 											ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+mcr_ac_type+"' and trn_type='D' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 											if(ret.next())
	 											{
	 												GLTransObject trnobj=new GLTransObject();
	 												trnobj.setTrnDate(date);
	 												trnobj.setGLType(ret.getString(3));
	 												trnobj.setGLCode(ret.getString(1));
	 												trnobj.setTrnMode("G");
	 												trnobj.setAmount(mcr_amount*ret.getInt(2));
	 												trnobj.setCdind("C");
	 												trnobj.setAccType(mcr_ac_type);
	 												trnobj.setAccNo(String.valueOf(mcr_ac_no));
	 												trnobj.setTrnType("D");
	 												trnobj.setRefNo(0);
	 												trnobj.setTrnSeq(trn_seq);
	 												trnobj.setVtml(vtml);
	 												trnobj.setVid(vuser);
	 												trnobj.setVDate(ve_date);
	 												trnobj.setVDate(ve_date);
	 												System.out.println("chek 33333333333333 ");

	 												comm.storeGLTransaction(trnobj);
	 												System.out.println("chek 44444444444444 ");
	 											}
	 											break;
	 								case 1001:// share trn
	 											// creditentry
	 											//ret=stupdate.executeQuery("select trn_no from ShareTransaction where sh_ty='"+mcr_ac_type+"' and sh_no="+mcr_ac_no+" order by trn_seq desc limit 1");
	 											/*ret=stupdate.executeQuery("select trn_no from ShareTransaction order by trn_seq desc limit 1");
	 											ret.next();
	 											int trn_no = ret.getInt(1);
	 											ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+ret.getInt(1)+",?,date_format(sysdate(),'%d/%m/%Y'),'A',?,?,?,?,?,'C',?,?,?,?,?,null,null,'F','F',?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'))");
	 											ps1.setString(1,mcr_ac_type);
	 											ps1.setInt(2,mcr_ac_no);
	 											ps1.setDouble(3,mcr_amount);
	 											ps1.setInt(4,0);
	 											ps1.setString(5,"Ctrl No "+ctrlno);
	 											ps1.setString(6,"G");
	 											ps1.setString(7,vtml);
	 											ps1.setString(8,"D");
	 											ps1.setString(9,"P");
	 											ps1.setInt(10,0);
	 											ps1.setInt(11,0);
	 											ps1.setInt(12,0);
	 											ps1.setString(13,vtml);
	 											ps1.setString(14,vuser);
	 											ps1.setString(15,vtml);
	 											ps1.setString(16,vuser);
	 											ps1.executeUpdate();
	 											// gl
	 											//ret = stupdate.executeQuery("select sh_cap from ShareType,ShareMaster where sh_ty='"+mcr_ac_type+"' and sh_no="+mcr_ac_no+" and sh_type=sh_ty and mem_cat=shcat");
	 											ret = stupdate.executeQuery("select st.sh_cap from ShareType st,ShareMaster sm where sm.ac_type='"+mcr_ac_type+"' and sm.ac_no="+mcr_ac_no+" and st.ac_type=sm.ac_type and st.mem_cat=sm.mem_cat");
	 											ret.next();
	 											int gl_code = ret.getInt(1);
	 											ret = stupdate.executeQuery("select mult_by from GLPost where gl_code = "+gl_code+" and ac_type = "+mcr_ac_type+" and trn_type='A' and cr_dr='C'");
	 											if(ret.next())
	 											{
	 												GLTransObject trnobj=new GLTransObject();
	 												trnobj.setTrnDate(date);
	 												trnobj.setGLType("GL");
	 												trnobj.setGLCode(String.valueOf(gl_code));
	 												trnobj.setTrnMode("G");
	 												trnobj.setAmount(mcr_amount*ret.getInt(1));
	 												trnobj.setCdind("C");
	 												trnobj.setAccType(mcr_ac_type);
	 												trnobj.setAccNo(String.valueOf(mcr_ac_no));
	 												trnobj.setTrnType("A");
	 												trnobj.setRefNo(0);
	 												trnobj.setTrnSeq(trn_no);
	 												trnobj.setVtml(vtml);
	 												trnobj.setVid(vuser);System.out.println(34);

	 												storeGLTransaction(trnobj);System.out.println(35);
	 											}
	 */											break;
	 								case 1008:// LD..ln trn
	 										
	 											deplon =new masterObject.loansOnDeposit.LoanTransactionObject();
	 											
	 											deplon.setAccType(ac_type);
	 											
	 											deplon.setAccNo(ac_no);

	 											deplon.setTransactionDate(date);
	 											
	 											deplon.setTranMode("G");
	 											
	 											deplon.setTransactionAmount(mcr_amount);
	 											
	 											deplon.setTranSou(de_tml);
	 											deplon.setReferenceNo(Integer.parseInt(String.valueOf(ctrlno)));
	 											deplon.setTranNarration("Clg");
	 											deplon.setCdind("C");
	 											deplon.uv.setUserTml(de_tml);
	 											deplon.uv.setUserId(de_user);
	 											deplon.uv.setUserDate(de_date);
	 											deplon.uv.setVerTml(vtml);
	 											deplon.uv.setVerId(vuser) ;
	 											deplon.uv.setVerDate(ve_date);
	 											Integer trn_seq_object =null;
									  
	 											try
	 											{
	 												trn_seq_object = (Integer)loanonde.recoverLDAccount(deplon);
	 											}
	 											catch(RecordNotUpdatedException rec_update)
	 											{
	 												sessionContext.setRollbackOnly();
	 												rec_update.printStackTrace();
	 												throw new SQLException();
	 											}
	 											break;
	 								case 1009:// locker trn
	 /*											RentTransObject rto=new RentTransObject();
	 											rto.setLockerAcType(mcr_ac_type);
	 											rto.setLockerAcNo(mcr_ac_no);
	 											rto.setTrnSource(vtml);
	 											rto.setTrnMode("G");
	 											rto.setTrfAcNo(0);
	 											rto.setTrfAcType("ctrl no "+ctrlno);
	 											rto.setTrfVoucherNo(0);
	 											rto.setRentAmt(mcr_amount);
	 											rto.uv.setUserId(vtml);
	 											rto.uv.setUserTml(vuser);
	 											storeRentTran(rto);
	 											// gl loker
	 											ret=stupdate.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type=1009001 and code=1");
	 											if(ret.next())
	 											{
	 												GLTransObject trn_obj=new GLTransObject();
	 												trn_obj.setTrnDate(date);
	 												trn_obj.setGLType(ret.getString(2));
	 												trn_obj.setGLCode(ret.getString(1));
	 												trn_obj.setTrnMode("G");
	 												trn_obj.setAmount(mcr_amount);
	 												trn_obj.setCdind("C");
	 												trn_obj.setAccType(mcr_ac_type);
	 												trn_obj.setAccNo(String.valueOf(mcr_ac_no));
	 												trn_obj.setTrnSeq(0);
	 												trn_obj.setTrnType("");
	 												trn_obj.setRefNo(0);
	 												trn_obj.setVtml(vtml);
	 												trn_obj.setVid(vuser);

	 												storeGLTransaction(trn_obj);
	 											}*/
	 											break;
	 								case 1010:// Loan ..ln trn
	 											break;
	 								
	 										
	 											
	 											

	 							}// end of switch

	 							// clg out gl for debit entry...
	 							ret = stupdate.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =3 and gk.ac_type = '1011001'");
	 							if(ret.next())
	 							{
	 								GLTransObject trnobj=new GLTransObject();
	 								trnobj.setTrnDate(date);
	 								trnobj.setGLType(ret.getString(2));
	 								trnobj.setGLCode(ret.getString(1));
	 								trnobj.setTrnMode("G");
	 								trnobj.setAmount(mcr_amount);
	 								trnobj.setCdind("D");
	 								trnobj.setAccType(mcr_ac_type);
	 								trnobj.setAccNo(String.valueOf(mcr_ac_no));
	 								trnobj.setTrnType("");
	 								trnobj.setRefNo(0);
	 								trnobj.setTrnSeq(0);
	 								trnobj.setVtml(vtml);
	 								trnobj.setVid(vuser);
	 								trnobj.setVDate(ve_date);
	 								System.out.println("chek 55555555555555 ");

	 								comm.storeGLTransaction(trnobj);
	 								System.out.println("chek 66666666666 ");
	 							}

	 							//update chq details in cheque table
	 							
	 							
	 							// storing for display purpose...
	 							
	 							stupdate.executeUpdate("update ChequeDetails set post_ind='T' where cr_ac_type='"+mcr_ac_type+"' and cr_ac_no="+mcr_ac_no+" and ctrl_no="+ctrlno);
	 						}
	 						//making postind true in cheque table iff cheque detail table all true
	 						ResultSet rmc = stmcr.executeQuery("select * from ChequeDetails where post_ind='F' and ctrl_no="+ctrlno);
	 						rmc.last();
	 						System.out.println ("rmc.getRow()  "+rmc.getRow());
	 						
	 				}//end of if for multi cr
	 				
	 			   else {                    // for indvidual posting....
	 						 
	 					 		boolean discountCheque=false;
	 					 		
	 					 		int ac_no_case = Integer.parseInt(res.getString(1).substring(0,4));
	 					 		System.out.println(" ++++++++++++++++++++++++++++++++ " + ac_no_case);
	 					 		ctrlno=res.getInt("ctrl_no");
	 					 		if(res.getInt(2) != 0 && ac_no_case !=1001){
	 						
	 						System.out.println(ac_no_case+"case");
	 						
	 						switch(ac_no_case){
	 							case 1002: 
	 							case 1014:
	 							case 1015:	
	 							case 1007:
	 							case 1018:	{     
	 										System.out.println("out is 2nd 7");
	 										//delete old row for shadow balance..
	 										stupdate.executeUpdate("delete from AccountTransaction where ac_no="+res.getInt(2)+" and ac_type='"+res.getString(1)+"' and trn_narr='"+ctrlno+"'");
	 										// credit entry...
	 										AccountTransObject am = new AccountTransObject();
	 										am.setAccType(res.getString(1));
	 										am.setAccNo(res.getInt(2));
	 										am.setTransType("R");
	 										if(res.getString("ch.disc_ind").equalsIgnoreCase("T")){
	 											discountCheque=true;
	 											am.setTransAmount(res.getDouble(3));//-res.getDouble("ch.disc_amt"));
	 										}
	 										else
	 										    am.setTransAmount(res.getDouble(3));
	 										
	 										am.setTransMode("G");
	 										am.setTransSource(de_tml);
	 										am.setCdInd("C");
	 										am.setTransDate(date);
	 										am.setChqDDNo(res.getInt(7));
	 										am.setChqDDDate(res.getString(8));
	 										am.setTransNarr("Ctrl No "+ctrlno);
	 										am.setRef_No(Integer.parseInt(String.valueOf(ctrlno)));
	 										am.setPayeeName(res.getString(9));
	 										am.setCloseBal(res.getDouble(3));
	 										am.setLedgerPage(0);
	 										am.uv.setUserTml(de_tml);
											am.uv.setUserId(de_user);
											am.uv.setUserDate(de_date);
											am.uv.setVerTml(vtml);
											am.uv.setVerId(vuser);
											am.uv.setVerDate(ve_date);

	 										comm.storeAccountTransaction(am);
	 										
	 										ResultSet rs_ret = null;
	 										// GL goes here..for credit entry
	 										System.out.println(" +++++++++++++++ ---------" + res.getString(1));
	 										if(res.getString(1).startsWith("1014") || res.getString(1).startsWith("1015")){
	 											System.out.println("inside the adcc GLTrn");
	 											rs_ret = stupdate.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
	 										}
	 										else
	 											rs_ret = stupdate.executeQuery("select am.ac_type,trn_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 										
	 										if(rs_ret.next())
	 										{
	 											
	 											trn_seq=rs_ret.getInt(2);
	 																					
	 										if(rs_ret.getInt(1)==0)
	 											cat_type=1;
	 										else
	 											cat_type=2;
	 										}
	 													
	 											ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 											if(ret.next())
	 											{
	 												GLTransObject trnobj=new GLTransObject();
	 												trnobj.setTrnDate(date);
	 												trnobj.setGLType(ret.getString(3));
	 												trnobj.setGLCode(ret.getString(1));
	 												trnobj.setTrnMode("G");
	 												trnobj.setAmount((res.getDouble(3))*ret.getInt(2));//-res.getDouble("ch.disc_amt")
	 												trnobj.setCdind("C");
	 												trnobj.setAccType(res.getString(1));
	 												trnobj.setAccNo(res.getString(2));
	 												trnobj.setTrnType("R");
	 												trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 												trnobj.setTrnSeq(trn_seq);
	 												trnobj.setVtml(vtml);
	 												trnobj.setVid(vuser);
	 												trnobj.setVDate(ve_date);
	 												System.out.println("chek 7777777777777 ");

	 												comm.storeGLTransaction(trnobj);
	 												System.out.println("chek 88888888888888 ");
	 											}
	 											if(discountCheque){
	 										
	 												ret = stupdate.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =5 and gk.ac_type = '1011001'");
	 												if(ret.next()){
	 													GLTransObject trnobj=new GLTransObject();
	 													trnobj.setTrnDate(date);
	 													trnobj.setGLType(ret.getString("gl_type"));
	 							 						trnobj.setGLCode(ret.getString("gl_code"));
	 							 						trnobj.setTrnMode("G");
	 							 						trnobj.setAmount(res.getDouble("ch.disc_amt"));
	 							 						trnobj.setCdind("C");
	 							 				
	 							 						trnobj.setAccType(res.getString("cr_ac_type"));
	 							 						trnobj.setAccNo(res.getString("cr_ac_no"));
	 							 				
	 							 						trnobj.setTrnType("R");
	 							 						trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 							 						trnobj.setTrnSeq(trn_seq);
	 							 						trnobj.setVtml(vtml);
	 							 						trnobj.setVDate(ve_date);
	 							 						trnobj.setVid(vuser);
	 							 						System.out.println("chek 999999999999999 ");

	 							 					comm.storeGLTransaction(trnobj);System.out.println(35);
	 							 					System.out.println("888888888888888888>>>->>-.._");
	 							 				}
	 												// account debit entry goes here  
	 												
	 												AccountTransObject am1 = new AccountTransObject();
	 		 										am.setAccType(res.getString(1));
	 		 										am.setAccNo(res.getInt(2));
	 		 										am.setTransType("P");
	 		 										am.setTransAmount(res.getDouble("ch.disc_amt"));
	 		 										am.setTransMode("G");
	 		 										am.setTransSource(de_tml);
	 		 										am.setCdInd("D");
	 		 										am.setTransDate(date);
	 		 										am.setChqDDNo(res.getInt(7));
	 		 										am.setChqDDDate(res.getString(8));
	 		 										am.setTransNarr("Ctrl No "+ctrlno+"Cheque Discount");
	 		 										am.setRef_No(0);
	 		 										am.setPayeeName(res.getString(9));
	 		 										am.setCloseBal(res.getDouble(3));
	 		 										am.setLedgerPage(0);
	 		 										am.uv.setUserTml(de_tml);
	 												am.uv.setUserId(de_user);
	 												am.uv.setUserDate(de_date);
	 												am.uv.setVerTml(vtml);
	 												am.uv.setVerId(vuser);
	 												am.uv.setVerDate(ve_date);

	 		 										comm.storeAccountTransaction(am);
	 		 										
	 												
	 		 										ResultSet rs_ret1 = null;
	 		 										// GL goes here..for credit entry
	 		 										if(res.getString(2).startsWith("1014") || res.getString(2).startsWith("1015"))
	 		 											rs_ret1 = stupdate.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
	 		 										else
	 		 											rs_ret1 = stupdate.executeQuery("select am.ac_type,trn_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 		 										
	 		 										if(rs_ret1.next())
	 		 											{
	 		 											
	 		 											 trn_seq=rs_ret1.getInt(2);
	 		 																					
	 		 										if(rs_ret1.getInt(1)==0)
	 		 											cat_type=1;
	 		 										else
	 		 											cat_type=2;
	 		 											}
	 		 													
	 		 											ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 		 											if(ret.next())
	 		 											{
	 		 												GLTransObject trnobj=new GLTransObject();
	 		 												trnobj.setTrnDate(date);
	 		 												trnobj.setGLType(ret.getString(3));
	 		 												trnobj.setGLCode(ret.getString(1));
	 		 												trnobj.setTrnMode("G");
	 		 												trnobj.setAmount(res.getDouble("ch.disc_amt")*ret.getInt(2));//-res.getDouble("ch.disc_amt")
	 		 												trnobj.setCdind("D");
	 		 												trnobj.setAccType(res.getString(1));
	 		 												trnobj.setAccNo(res.getString(2));
	 		 												trnobj.setTrnType("P");
	 		 												trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 		 												trnobj.setTrnSeq(trn_seq);
	 		 												trnobj.setVtml(vtml);
	 		 												trnobj.setVid(vuser);
	 		 												trnobj.setVDate(ve_date);
	 		 												System.out.println("chek 1111 44444444444 ");

	 		 												comm.storeGLTransaction(trnobj);
	 		 												System.out.println("chek 11111 555555555 ");
	 		 											}
	 												
	 												
	 												
	 										}
	 										
	 											ResultSet resc=stupdate.executeQuery("select cr_ac_type,cr_ac_no,loan_ac_type, loan_ac_no from Cheque where ctrl_no="+ctrlno+" and post_ind='F'");
	 											if(resc.next())
	 											{
	 												String loan_ac_type=resc.getString("loan_ac_type");
	 												int loan_ac_no=resc.getInt("loan_ac_no");

	 												if(resc.getString("loan_ac_type")!=null && resc.getInt("loan_ac_no")>0)
	 												{
	 													AccountTransObject am1 = new AccountTransObject();
	 													am1.setAccType(resc.getString(1));
	 													am1.setAccNo(resc.getInt(2));
	 													am1.setTransType("R");
	 													if(disc_ind.equalsIgnoreCase("T"))
	 														am1.setTransAmount(chq_amt-disc_amt);
	 													else
	 														am1.setTransAmount(chq_amt);

	 													am1.setTransMode("G");
	 													am1.setTransSource(de_tml);
	 													am1.setCdInd("D");
	 													am1.setChqDDNo(chq_no);
	 													am1.setChqDDDate(chq_date);
	 													am1.setTransNarr("Ctrl No "+ctrlno);
	 													am1.setRef_No(0);
	 													am1.setPayeeName(payee_name);
	 													am1.setCloseBal(chq_amt);
	 													am1.setLedgerPage(0);
	 													am1.uv.setUserTml(de_tml);
	 													am1.uv.setUserId(de_user);
	 													am1.uv.setUserDate(de_date);
	 													am1.uv.setVerTml(vtml);
	 													System.out.println("--val of dedate-->"+de_date);
																
	 													am1.setTransDate(de_date.substring(0,10));
	 													am1.uv.setVerId(vuser);
	 													am1.uv.setVerDate(ve_date);

	 													comm.storeAccountTransaction(am1);


	 													ResultSet ret1 = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+ac_type+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 													if(ret1.next())
	 													{
	 														GLTransObject trnobj=new GLTransObject();
	 														trnobj.setTrnDate(date);
	 														trnobj.setGLType(ret1.getString(3));
	 														trnobj.setGLCode(ret1.getString(1));
	 														trnobj.setTrnMode("G");
	 														trnobj.setAmount(chq_amt*ret1.getInt(2));
	 														trnobj.setCdind("D");
	 														trnobj.setAccType(ac_type);
	 														trnobj.setAccNo(String.valueOf(ac_no));
	 														trnobj.setTrnType("P");
	 														trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 														trnobj.setTrnSeq(trn_seq);
	 														trnobj.setVtml(vtml);
	 														trnobj.setVid(vuser);
	 														trnobj.setVDate(ve_date);
	 														System.out.println("chek 11111 666666666 ");

	 														comm.storeGLTransaction(trnobj);
	 														System.out.println("chek 11111 7777777777 ");
	 													}

	 													lob= new LoanTransactionObject();							    
	 													lob.setAccType(loan_ac_type);
	 													lob.setAccNo(loan_ac_no);
	 													lob.setTransactionDate(date);
	 													lob.setTranType("R");
	 													lob.setTransactionAmount(chq_amt);
	 													lob.setTranMode("G");
	 													lob.setTranSou(de_tml);
	 													lob.setReferenceNo(ctrlno);
	 													lob.setTranNarration("Clg");
	 													lob.setCdind("C");
	 													lob.uv.setUserTml(de_tml);
	 													lob.uv.setUserId(de_user);
	 													lob.uv.setUserDate(de_date);
	 													lob.uv.setVerTml(vtml);
	 													lob.uv.setVerId(vuser) ;
	 													lob.uv.setVerDate(ve_date);

	 													Integer trn_seq_object =null;

	 													try
	 													{
	 														loanremote=loanHome.create();
	 														trn_seq_object= (Integer) loanremote.recoverLoanAccount(lob);
	 													}
	 													catch(RecordNotUpdatedException rec_update)
	 													{
	 														sessionContext.setRollbackOnly();
	 														rec_update.printStackTrace();
	 														throw new SQLException();
	 													}
	 													if(trn_seq_object!=null)
	 														trn_seq = trn_seq_object.intValue();



	 												}									
	 											}
	 										break;
	 							}
	 							case 1004:// dep trn with RD
	 										if(ac_no>0)
	 										{
	 											ret=stupdate.executeQuery("select cm.custtype,cm.cid,trn_seq,rd_bal,cum_int from DepositMaster dm,DepositTransaction dt ,CustomerMaster cm where dt.ac_type='"+ac_type+"'  and dt.ac_no="+ac_no+" and dm.ac_no=dt.ac_no and dm.ac_type=dt.ac_type and cm.cid=dm.cid order by trn_seq desc limit 1");
	 											if(ret.next())
	 											{
	 												trn_seq=ret.getInt(2)+1;
	 												if(ret.getInt(1)==0)
	 													cat_type=1;
	 												else
	 													cat_type=2;
	 												
	 										// credit entry
	 												ps1=conn.prepareStatement("insert into DepositTransaction values(?,?,?,?,'D',?,null,null,null,?,null,null,?,?,?,?,'C',?,?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'))");
	 												ps1.setString(1,res.getString(1));
	 												ps1.setInt(2,res.getInt(2));
	 												ps1.setInt(3,trn_seq);//trnseq
	 												ps1.setString(4,date);
	 												ps1.setDouble(5,res.getDouble(3));
	 												ps1.setDouble(6,ret.getDouble("rd_bal")+res.getDouble(3));
	 												ps1.setInt(7,ctrlno);
	 												ps1.setString(8,"Ctrl No "+ctrlno);
	 												ps1.setString(9,"G");
	 												ps1.setString(10,vtml);
	 												ps1.setDouble(11,ret.getDouble(4));
	 												ps1.setString(12,de_tml);
	 												ps1.setString(13,de_user);
	 												ps1.setString(14,vtml);
	 												ps1.setString(15,vuser);
	 												ps1.executeUpdate();
	 										// 	gl
	 											}
	 											ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+res.getString(1)+"' and trn_type='D' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 											if(ret.next())
	 											{
	 												GLTransObject trnobj=new GLTransObject();
	 												trnobj.setTrnDate(date);
	 												trnobj.setGLType(ret.getString(3));
	 												trnobj.setGLCode(ret.getString(1));
	 												trnobj.setTrnMode("G");
	 												trnobj.setAmount(res.getDouble(3)*ret.getInt(2));
	 												trnobj.setCdind("C");
	 												trnobj.setAccType(res.getString(1));
	 												trnobj.setAccNo(res.getString(2));
	 												trnobj.setTrnType("D");
	 												trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 												trnobj.setTrnSeq(trn_seq);
	 												trnobj.setVtml(vtml);
	 												trnobj.setVid(vuser);
	 												trnobj.setVDate(ve_date);
	 												System.out.println("chek 11111 999999999 ");
	 												
	 												comm.storeGLTransaction(trnobj);
	 												System.out.println("chek 2222 6666666666 ");
	 											}
	 										}
	 										
	 										
	 										break;
	 										
	 										
	 										
	 										
	 										
	 										
	 							case 1001:  // share trn
	 										// creditentry
	 										//ret=stupdate.executeQuery("select trn_no from ShareTransaction where sh_ty='"+res.getString(6)+"' and sh_no="+res.getInt(7)+" order by trn_seq desc limit 1");
	 										//ret=stupdate.executeQuery("select trn_no from ShareTransaction order by trn_seq desc limit 1");
	 								
	 							    		/*ret=stupdate.executeQuery("select trn_no from ShareTransaction order by trn_no desc limit 1");
	 										ret.next();
	 										int trn_no = ret.getInt(1);
	 										ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+ret.getInt(1)+",?,date_format(sysdate(),'%d/%m/%Y'),'A',?,?,?,?,?,'C',?,?,?,?,?,null,null,'F','F',?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'))");
	 										ps1.setString(1,res.getString(1));
	 										ps1.setInt(2,res.getInt(2));
	 										ps1.setDouble(3,res.getDouble(3));
	 										ps1.setInt(4,0);
	 										ps1.setString(5,"Ctrl No "+ctrlno);
	 										ps1.setString(6,"G");
	 										ps1.setString(7,vtml);
	 										ps1.setString(8,"D");
	 										ps1.setString(9,"P");
	 										ps1.setInt(10,0);
	 										ps1.setInt(11,0);
	 										ps1.setInt(12,0);
	 										ps1.setString(13,vtml);
	 										ps1.setString(14,vuser);
	 										ps1.setString(15,vtml);
	 										ps1.setString(16,vuser);
	 										ps1.executeUpdate();
	 										// gl
	 										ret = stupdate.executeQuery("select sh_cap from ShareType,ShareMaster where ShareMaster.ac_type='"+res.getString(1)+"' and ac_no="+res.getInt(2)+" and ShareMaster.ac_type=ShareType.ac_type and ShareMaster.mem_cat=ShareType.mem_cat");
	 										ret.next();
	 										int gl_code = ret.getInt(1);
	 										ret = stupdate.executeQuery("select mult_by from GLPost where gl_code = "+gl_code+" and ac_type = "+res.getString(1)+" and trn_type='A' and cr_dr='C'");
	 										if(ret.next())
	 										{
	 											GLTransObject trnobj=new GLTransObject();
	 											trnobj.setTrnDate(date);
	 											trnobj.setGLType("GL");
	 											trnobj.setGLCode(String.valueOf(gl_code));
	 											trnobj.setTrnMode("G");
	 											trnobj.setAmount(res.getDouble(3)*ret.getInt(1));
	 											trnobj.setCdind("C");
	 											trnobj.setAccType(res.getString(1));
	 											trnobj.setAccNo(res.getString(2));
	 											trnobj.setTrnType("A");
	 											trnobj.setRefNo(0);
	 											trnobj.setTrnSeq(trn_no);
	 											trnobj.setVtml(vtml);
	 											trnobj.setVid(vuser);System.out.println(34);

	 											storeGLTransaction(trnobj);System.out.println(35);
	 										}
	 */										break;
	 							case 1008:// LD..ln trn
	 								
	 									deplon =new masterObject.loansOnDeposit.LoanTransactionObject();
										
	 									deplon.setAccType(ac_type);
										
	 									deplon.setAccNo(ac_no);

	 									deplon.setTransactionDate(date);
										
										deplon.setTranMode("G");
											
										deplon.setTransactionAmount(chq_amt);
										deplon.setTranType("T");
										deplon.setTranSou(de_tml);
										deplon.setReferenceNo(Integer.parseInt(String.valueOf(ctrlno)));
										deplon.setTranNarration("Clg");
										deplon.setCdind("C");
										deplon.uv.setUserTml(de_tml);
										deplon.uv.setUserId(de_user);
										deplon.uv.setUserDate(de_date);
										deplon.uv.setVerTml(vtml);
										deplon.uv.setVerId(vuser) ;
										deplon.uv.setVerDate(ve_date);
										Integer trn_seq_object =null;
										//Object obj = null;
										try
										{	
											
											trn_seq_object =(Integer) loanonde.recoverLDAccount(deplon);
											
												
											
										}
										catch(RecordNotUpdatedException rec_update)
										{
											sessionContext.setRollbackOnly();
											rec_update.printStackTrace();
											throw new SQLException();
										}
										break;
	 								
	 							case 1009:// locker trn
	 										/*RentTransObject rto=new RentTransObject();
	 										rto.setLockerAcType(res.getString(1));
	 										rto.setLockerAcNo(res.getInt(2));
	 										rto.setTrnSource(vtml);
	 										rto.setTrnMode("G");
	 										rto.setTrfAcNo(0);
	 										rto.setTrfAcType("ctrl no "+ctrlno);
	 										rto.setTrfVoucherNo(0);
	 										rto.setRentAmt(res.getDouble(3));
	 										rto.uv.setUserId(vtml);
	 										rto.uv.setUserTml(vuser);
	 										storeRentTran(rto);
	 										// gl loker
	 										ret=stupdate.executeQuery("select gl_code,gl_type from glkeyparam where ac_type=1009001 and code=1");
	 										if(ret.next())
	 										{
	 											GLTransObject trn_obj=new GLTransObject();
	 											trn_obj.setTrnDate(date);
	 											trn_obj.setGLType(ret.getString(2));
	 											trn_obj.setGLCode(ret.getString(1));
	 											trn_obj.setTrnMode("G");
	 											trn_obj.setAmount(res.getDouble(3));
	 											trn_obj.setCdind("C");
	 											trn_obj.setAccType(res.getString(1));
	 											trn_obj.setAccNo(res.getString(2));
	 											trn_obj.setTrnSeq(0);
	 											trn_obj.setTrnType("");
	 											trn_obj.setRefNo(0);
	 											trn_obj.setVtml(vtml);
	 											trn_obj.setVid(vuser);

	 											storeGLTransaction(trn_obj);
	 										}
	 */										break;
	 							case 1010:// Loan ..ln trn
	 							    
	 							   /* lob.setAccType(res.getString(1));
	 							    lob.setAccNo(res.getInt(2));
	 							    lob.setTransactionDate(comm.getSysDate());
	 							    lob.setTranType("R");
	 							    lob.setTransactionAmount(res.getDouble(3));
	 							    lob.setTranMode("G");
	 							    lob.setTranSou(vtml);
	 							    lob.setReferenceNo(Integer.parseInt(String.valueOf(ctrlno)));
	 							    lob.setTranNarration("Clg");
	 							    lob.setCdind("C");
	 							    lob.uv.setUserTml(vtml);
	 							    lob.uv.setUserId(vuser);
	 							   lob.uv.setUserDate(comm.getDateTime());
	 							   lob.uv.setVerTml(vtml);
	 							   lob.uv.setVerId(vuser) ;
	 							  lob.uv.setVerDate(comm.getDateTime());
	 							  Integer trn_seq_object =null;
	 							  
	 				                try
	 								{
	 				                	trn_seq_object= (Integer) loanremote.recoverLoanAccount(lob);
	 								}
	 				                catch(RecordNotUpdatedException rec_update)
	 								{
	 				                	rec_update.printStackTrace();
	 				                	throw new SQLException();
	 								}
	 				                if(trn_seq_object!=null)
	 				                	trn_seq = trn_seq_object.intValue();
	 				            */
	 										break;
	 							case 1012:// GL only..gl trn only..
	 										GLTransObject trnobj=new GLTransObject();
	 										trnobj.setTrnDate(date);
	 										trnobj.setGLType(res.getString(1));
	 										trnobj.setGLCode(res.getString(2));
	 										trnobj.setTrnMode("G");
	 										trnobj.setAmount(res.getDouble(3));
	 										trnobj.setCdind("C");
	 										trnobj.setAccType("0");
	 										trnobj.setAccNo("0");
	 										trnobj.setTrnType("");
	 										trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 										trnobj.setTrnSeq(0);
	 										trnobj.setVtml(vtml);
	 										trnobj.setVid(vuser);
	 										trnobj.setVDate(ve_date);
	 										System.out.println("chek 2222 555555555 ");

	 										comm.storeGLTransaction(trnobj);
	 										System.out.println("chek 2222 4444444 ");

	 										break;

	 						}// end of switch
	 						

	 						
	 						
	 						System.out.println("start here");

	 						// clg out gl for debit entry...
	 						ret = stupdate.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =3 and gk.ac_type = '1011001'");
	 						if(ret.next())
	 						{
	 							GLTransObject trnobj=new GLTransObject();
	 							trnobj.setTrnDate(date);
	 							trnobj.setGLType(ret.getString(2));
	 							trnobj.setGLCode(ret.getString(1));
	 							trnobj.setTrnMode("G");
	 							trnobj.setAmount(res.getDouble(3));
	 							trnobj.setCdind("D");
	 							trnobj.setAccType(res.getString(1));
	 							trnobj.setAccNo(res.getString(2));
	 							trnobj.setTrnType("");
	 							trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 							trnobj.setTrnSeq(0);
	 							trnobj.setVtml(vtml);
	 							trnobj.setVid(vuser);
	 							trnobj.setVDate(ve_date);
	 							System.out.println("chek 33333 5555555555 ");

	 							comm.storeGLTransaction(trnobj);
	 							System.out.println("chek 33333 66666666 ");
	 						}

	 						
	 						
	 					}
	 					
	 					System.out.println("query run");
	 	 				
	 	 			
	 	 			
	 	 			
	 					
	 					}//end of ind	
	 			   System.out.println("<--------******AM i here*****------>");
	 				stupdate.executeUpdate("update Cheque set post_ind='T' where ctrl_no ="+ctrlno+" and post_ind='F'");
	 					updated++;
	 					
	 					
	 					if ( po_comm > 0.0 && prev_ctrl_no > 0){
	 				
	 						ResultSet res_po = stm_pocomm.executeQuery(" select * from Cheque where ctrl_no = " +prev_ctrl_no + " and clg_type = 'S'"  );
	 						
	 						if ( res_po.next() ){
	 							
	 							
									AccountTransObject am = new AccountTransObject();
									
										am.setAccType(ac_type);
										am.setAccNo(ac_no);
							
										am.setTransType("P");
										am.setTransAmount(po_comm);
										am.setTransMode("G");
										am.setTransSource( de_tml );
										am.setTransDate(date);
				
										am.setCdInd("D");
										am.setChqDDNo( chq_no );
										am.setChqDDDate( chq_date );
										am.setTransNarr("Ctrl No "+ctrlno + " due to OutStation Cheque");
										am.setRef_No(Integer.parseInt(String.valueOf(ctrlno)));
										am.setPayeeName( payee_name );
										am.setCloseBal(po_comm);
										am.setLedgerPage(0);
										am.uv.setUserTml( de_tml );
										am.uv.setUserId( de_user );
										am.uv.setUserDate(de_date);
										am.uv.setVerTml( de_tml );
										am.uv.setVerId( de_user );
										am.uv.setVerDate( de_date );
				
				
										comm.storeAccountTransaction(am);

							// GL goes here..for credit entry
							
										ResultSet ret_po = null;
		 								if( ac_type.startsWith("1002")|| ac_type.startsWith("1007"))
		 										ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
		 								else
		 										ret = stupdate.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
										
										ret.next();
							
										trn_seq=ret.getInt(2);
										if(ret.getInt(1)==0)
											cat_type=1;
										else
											cat_type=2;
										ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
										if(ret.next())
										{
											GLTransObject trnobj=new GLTransObject();
											trnobj.setTrnDate(date);
											trnobj.setGLType(ret.getString(3));
											trnobj.setGLCode(ret.getString(1));
											trnobj.setTrnMode("T");
											trnobj.setAmount( po_comm *ret.getInt(2));
											trnobj.setCdind("D");
											trnobj.setAccType( ac_type );
											trnobj.setAccNo( Integer.toString(ac_no));
											trnobj.setTrnType("P");
											trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
											trnobj.setTrnSeq(trn_seq);
											trnobj.setVtml(de_tml);
								
					
											trnobj.setVDate( de_date);
											trnobj.setVid( de_user );
											System.out.println(34);
								
											System.out.println("before 4 store");
											comm.storeGLTransaction(trnobj);
											System.out.println(35);
										}

										ResultSet gl = stm_pocomm.executeQuery("select * from GLKeyParam where ac_type='1011001' and code=7");
										if(gl.next())
										{
											System.out.println (1111);
											
											GLTransObject trnobj=new GLTransObject();
											trnobj.setTrnDate(date);
											System.out.println (22);
											trnobj.setGLType(gl.getString("gl_type"));
											trnobj.setGLCode(gl.getString("gl_code"));
											System.out.println (33);
											trnobj.setTrnMode("G");
											trnobj.setAmount( po_comm );
											System.out.println (44);
											trnobj.setCdind("C");
											trnobj.setAccType( ac_type);
											trnobj.setAccNo(Integer.toString(ac_no));
											trnobj.setTrnType(" ");
											trnobj.setRefNo( ctrlno );
											trnobj.setTrnSeq(0);
											trnobj.setVtml(de_tml);
											trnobj.setVid(de_user);
											trnobj.setVDate(de_date);
											comm.storeGLTransaction(trnobj);
											System.out.println (66);
										}
	 							
	 						}
	 					}
	 			
	 			}
	 			
	 			
	 			System.out.println("updated rows"+updated);
	 			System.out.println("ctrl"+ctrl);
	 			System.out.println("size"+ctrl_no_arr.size());
	 		
	 			if(updated != ctrl_no_arr.size()){
	 				sessionContext.setRollbackOnly();
	 			    throw new SQLException();
	 			}
	 			
	 			int count=0;
	 			String arr_ctrl[] = null;
	 			String mult_ctrl = "";
	 			res=stat.executeQuery("select ctrl_no from Cheque where mult_cr='T' and ctrl_no in("+ctrl+")");
	 			if(res.next())
	 			{
	 			    res.last();
	 			    count=res.getRow();
	 			    res.beforeFirst();
	 			    int l=0;
	 			    arr_ctrl=new String[count];
	 			    while(res.next())
	 			    {
	 			       if(l<count)
	 			       {
	 			           arr_ctrl[l]=res.getString("ctrl_no");
	 			           System.out.println("ou"+arr_ctrl[l]);
	 			           ctrl=ctrl.replaceAll(arr_ctrl[l],"null");
	 			       }
	 			     
	 			        l++;
	 			    }
	 			    int p=0;
	 				for(;p<arr_ctrl.length-1;p++)
	 				mult_ctrl=mult_ctrl+arr_ctrl[p]+",";
	 				System.out.println("mult_cr"+mult_ctrl);
	 				mult_ctrl=mult_ctrl+arr_ctrl[p];
	 				System.out.println("mult_cr"+mult_ctrl);	    
	 			}
	 			else
	 			
	 			   System.out.println("No  Multi_ Cr Cheques Found");
	 			
	 			
	 			System.out.println("updated ctrl"+""+ctrl);
	 			System.out.println("mult_ctrl"+mult_ctrl);
	 			
	 			
	 		
	 			res= stat.executeQuery("select md.moduleabbr,ch.cr_ac_no,ch.trn_amt,ch.cr_ac_type from Cheque ch,Modules md where ch.ctrl_no in("+ctrl+") and ch.cr_ac_type=md.modulecode union all	select md.moduleabbr,cd.cr_ac_no,cd.cr_amt,cd.cr_ac_type from ChequeDetails cd,Modules md where cd.ctrl_no in('"+mult_ctrl+"')and cd.cr_ac_type=md.modulecode");		
	 			if(res.next())
	 			{
	 			    res.last();
	 			    int count1=res.getRow();
	 			    int q=0;
	 			    res.beforeFirst();
	 			    result= new Vector[count1];
	 			    while(res.next())
	 			    {
	 			        if(q<count1)
	 			        {
	 			         result[q]=new Vector();
	 			        result[q].add(0,res.getString("moduleabbr"));
	 			        result[q].add(1,res.getString("cr_ac_no"));
	 			        result[q].add(2,res.getString("trn_amt"));
	 			        
	 			        
	 			        }
	 			        q++;
	 			        
	 			    }
	 			    
	 			   
	 			    
	 			}
	 			else{
	 				sessionContext.setRollbackOnly();
	 			    throw new SQLException();
	 			}
	 			
	 			 for(int g=0;g<result.length;g++)
	 				{
	 				    System.out.println("ist is"+result[g].get(0));
	 				    System.out.println("2nd is"+result[g].get(1));
	 				    System.out.println("3rd is"+result[g].get(2));
	 				}
	 			
	 			}
	 		
	 		catch(SQLException posting){
	 			posting.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			
	 			throw new RecordNotInsertedException();
	 		} catch (CreateException e) {
	             e.printStackTrace();
	             sessionContext.setRollbackOnly();
	         }catch(Exception e){
	        	 e.printStackTrace();
	        	 sessionContext.setRollbackOnly();
	         }
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 	return result;
	 	}
	 	
	 	
	 	public int storeRentTran(RentTransObject rto) throws RecordNotInsertedException
	 	{
	 		Connection conn=null;
	 		int a=0;
	 		try
	 		{
	 			conn=getConnection();
	 			PreparedStatement pstmt=conn.prepareStatement("insert into RentTran values(?,?,date_format(sysdate(),'%d/%m/%Y'),?,?,?,?,?,?,?,?,date_format(sysdate(),'%d/%m/%Y %r'))");
	 			pstmt.setString(1,rto.getLockerAcType());
	 			pstmt.setInt(2,rto.getLockerAcNo());
	 			pstmt.setString(3,rto.getTrnSource());
	 			pstmt.setString(4,rto.getTrnMode());
	 			pstmt.setString(5,rto.getTrfAcType());
	 			pstmt.setInt(6,rto.getTrfAcNo());
	 			pstmt.setInt(7,rto.getTrfVoucherNo());
	 			pstmt.setDouble(8,rto.getRentAmt());
	 			pstmt.setString(9,rto.uv.getUserId());
	 			pstmt.setString(10,rto.uv.getUserTml());
	 			a=pstmt.executeUpdate();
	 		}
	 		catch(Exception ex){
	 			ex.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}
	 		finally{
	 			try{
	 				conn.close();
	 			}catch (SQLException e){
	 				e.printStackTrace();
	 			}
	 		}
	 		return a;
	 	}

	 	// getSummary and retrive posting details...
	 	
	 	public ClearingObject[] getOutwardSummary(String date,int n ,int clg_no, int send_to,String str)throws RecordsNotFoundException
	 	{
	 		ResultSet rs=null;
	 		ClearingObject[] cd=null;
	 		Connection conn=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			if(n==2 || n==3){//linz
	 				rs=stmt.executeQuery("select bank_cd,bank_name,doc_bs,no_docs,doc_tot,trn_amt,ret_norm from Cheque,BankMaster where desp_ind='T' and post_ind='F' and clg_date='"+date+"' and bank_cd=bank_code");
	 				rs.last();
	 				if(rs.getRow()>0)
	 				{
	 					cd=new ClearingObject[rs.getRow()];
	 					rs.beforeFirst();
	 					int i=0,number=0;
	 					int count=0,cnt=0,tno;
	 					double amt=0,amount=0,sum=0,tamt=0;
	 					System.out.println(2);
	 					while(rs.next())
	 					{
	 						System.out.println(3);
	 						if(number!=rs.getInt("bank_cd"))
	 						{
	 							count=0;
	 							cnt=0;
	 							amt=0;
	 							amount=0;
	 							sum=0;
	 							i++;
	 							number=rs.getInt("bank_cd");
	 							cd[i]=new ClearingObject();
	 							System.out.println(4);
	 						}
	 						
	 						if(number==rs.getInt("bank_cd"))
	 						{
	 							System.out.println(5);
	 							cd[i].setBankCode(rs.getInt("bank_cd"));
	 							System.out.println("cd[i].getBankCode()=="+cd[i].getBankCode());
	 							cd[i].setBranchName(rs.getString("bank_name"));
	 							cd[i].setDocBs(rs.getString("doc_bs"));
	 							
	 							if(rs.getString("doc_bs").equals("B"))
	 							{
	 								System.out.println(6);
	 								cd[i].setNoOfDocs(rs.getInt("no_docs"));
	 								cd[i].setDiscAmt(rs.getDouble("doc_tot"));
	 								sum=sum+cd[i].getDiscAmt();
	 							}
	 							
	 							else if(rs.getString("doc_bs").equals("S"))
	 							{
	 								System.out.println(66);
	 								amt=amt+rs.getDouble("trn_amt");
	 								count++;
	 								cd[i].setControlNo(count);
	 								cd[i].setTranAmt(amt);
	 							}
	 							
	 							else if(rs.getString("ret_norm").equals("R"))
	 							{
	 								System.out.println(666);
	 								amount=amount+rs.getDouble("doc_tot");
	 								cnt++;
	 								cd[i].setPOCommission(amount);
	 								cd[i].setQdpNo(cnt);
	 							}
	 							
	 							System.out.println(7);
	 							tamt=amount+amt+sum;
	 							tno=count+cnt+cd[i].getNoOfDocs();
	 							cd[i].setFineAmt(tamt);
	 							cd[i].setAckNo(tno);
	 							System.out.println(8);
	 						}
	 						System.out.println("i== "+i);
	 					}
	 				}
	 				else{
	 					cd=null;
	 					return cd;
	 				}	
	 			}
	 			//posting perpose...
	 			else if(n==1 || n==4)
	 			{
	 				int i=0;
	 				//rs=stmt.executeQuery("select clg_no,ctrl_no,clg_date,send_to,bm.br_name from Cheque,BranchMaster bm where desp_ind='T' and concat(right(exp_clgdt,4),'-',mid(exp_clgdt,locate('/',exp_clgdt)+1,(locate('/',exp_clgdt,4)-locate('/',exp_clgdt)-1)),'-',left(exp_clgdt,locate('/',exp_clgdt)-1))<='"+Validations.convertYMD(date)+"' and ret_norm ='N' and send_to=br_code and post_ind='F' and trn_type='G' and clg_type='O' and doc_bs='S' group by clg_no order by clg_no");
	 				if( n==1 ) {
	 				
	 					if ( str == null)
	 						rs=stmt.executeQuery("select ctrl_no,clg_no,clg_date,send_to,bm.br_name,trn_amt,mult_cr,cr_ac_type,cr_ac_no ,doc_dest,doc_sou,bank_cd from Cheque,BranchMaster bm where desp_ind='T' and concat(right(exp_clgdt,4),'-',mid(exp_clgdt,locate('/',exp_clgdt)+1,(locate('/',exp_clgdt,4)-locate('/',exp_clgdt)-1)),'-',left(exp_clgdt,locate('/',exp_clgdt)-1))='"+Validations.convertYMD(date)+"' and ret_norm ='N' and send_to=br_code and post_ind='F' and trn_type='G' and clg_type='O' and doc_bs='S' and clg_no = "+ clg_no+" and send_to = " +send_to);
	 					else {
	 						System.out.println(str);
	 						rs=stmt.executeQuery("select ctrl_no,clg_no,clg_date,send_to,bm.br_name,trn_amt,mult_cr,cr_ac_type,cr_ac_no ,doc_dest,doc_sou,bank_cd from Cheque,BranchMaster bm where desp_ind='T' and concat(right(exp_clgdt,4),'-',mid(exp_clgdt,locate('/',exp_clgdt)+1,(locate('/',exp_clgdt,4)-locate('/',exp_clgdt)-1)),'-',left(exp_clgdt,locate('/',exp_clgdt)-1))='"+Validations.convertYMD(date)+"' and ret_norm ='N' and send_to=br_code and post_ind='F' and trn_type='G' and clg_type='O' and doc_bs='S' and clg_no = "+ clg_no+" and send_to = "+ send_to+" and substring(bank_cd,4,3) in ( "+str+")");
	 					}	
	 				}
	 				else 
	 					rs=stmt.executeQuery("select  ctrl_no,clg_no,clg_date,send_to,bm.br_name,trn_amt,mult_cr,cr_ac_type,cr_ac_no ,doc_dest,doc_sou ,bank_cd from Cheque,BranchMaster bm where desp_ind='T' and concat(right(exp_clgdt,4),'-',mid(exp_clgdt,locate('/',exp_clgdt)+1,(locate('/',exp_clgdt,4)-locate('/',exp_clgdt)-1)),'-',left(exp_clgdt,locate('/',exp_clgdt)-1))<='"+Validations.convertYMD(date)+"' and ret_norm ='N' and send_to=br_code and post_ind='F' and trn_type='G' and clg_type='O' and doc_bs='S' group by clg_date");
	 				
	 				Statement stmt_multi = conn.createStatement();
	 				rs.last();
	 				if(rs.getRow()>0){
	 					cd=new ClearingObject[rs.getRow()];
	 					rs.beforeFirst();
	 					while(rs.next())
	 					{
	 						cd[i] = new ClearingObject();
	 						cd[i].setControlNo(rs.getInt(1));
	 						cd[i].setClgNo(rs.getInt(2));
	 						cd[i].setClgDate(rs.getString(3));
	 						cd[i].setBankCode(rs.getInt(4));
	 						cd[i].setBranchName(rs.getString(5));
	 						cd[i].setTranAmt(rs.getDouble(6));
	 						cd[i].setCreditACType(rs.getString("cr_ac_type"));
	 						cd[i].setCreditACNo(rs.getInt("cr_ac_no"));
	 						cd[i].setDocSource(rs.getInt("doc_sou"));
	 						cd[i].setDocDestination(rs.getInt("doc_dest"));
	 						cd[i].setMultiCredit("T");
	 						cd[i].setBankName(rs.getString("bank_cd"));
	 		 				
	 		 				if( rs.getString("mult_cr") != null && rs.getString("mult_cr").equalsIgnoreCase("T")){
	 		 				
	 		 					System.out.println("working 1..................");
	 		 					ResultSet mu = stmt_multi.executeQuery("select * from ChequeDetails where ve_tml is not null and ctrl_no="+rs.getInt("ctrl_no"));
	 		 					
	 		 					if(!mu.next())
	 		 						cd[i].setMultiCredit("F");
	 		 					
	 		 						
	 		 				}
	 						i++;
	 					}
	 				}
	 				else{
	 					cd=null;
	 					return cd;
	 				}	
	 			}
	 		}catch(SQLException sq){
	 			sq.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cd;
	 	}

	 	public AckObject getAcknowledgeAmount(int ackno,String clg)throws RecordsNotFoundException
	 	{
	 		AckObject ackob = null;
	 		Connection conn=null;
	 		try{
	 			
	 			conn=getConnection();
	 			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			ResultSet rs = stat.executeQuery(" select reconciled,ve_tml,tot_amt,doc_sou,br_name from Acknowledge,BranchMaster where ack_no="+ackno+" and ve_tml is null and br_code = doc_sou and clg_type = '"+clg+"'");
	 			rs.last();
	 			if(rs.getRow()>0){
	 				rs.beforeFirst();
	 				rs.next();
	 				ackob = new AckObject();
	 				ackob.setReconciled(rs.getString(1));//already reconciled
	 				ackob.setAckDate(rs.getString(2));//not yet verified
	 				ackob.setAckEntered(rs.getDouble(3));//ack full amount
	 				ackob.setBankCode(rs.getString(4));
	 				ackob.setBankName(rs.getString(5));

	 				rs = stat.executeQuery(" select sum(doc_tot+trn_amt) from Cheque where ack_no ="+ackno+" and ve_tml is null");
	 				rs.next();
	 				ackob.setTotal(rs.getDouble(1));//ack used amount
	 			}
	 			else{
	 				ackob=null;
	 				return ackob;
	 			}
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return ackob;
	 	}
	 		
	 	public int deleteChequeData(int ctrlno)
	 	{
	 		int a=-1;
	 		Connection conn=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt2 = conn.createStatement();
	 			Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			ResultSet rs = stmt1.executeQuery(" select * from Cheque where ctrl_no = "+ ctrlno ); 
	 			ResultSet rs1 = null;
	 			int c = 0;
	 			if (rs.next()){
	 				
	 				if (  rs.getInt("prev_ctrl_no") != 0 )
	 					 c  = stmt1.executeUpdate(" update Cheque set prev_ctrl_no = 0 where ctrl_no = "+ rs.getInt("prev_ctrl_no") +" and clg_type = 'S';  "); 
	 				
	 			}
	 			
	 			a = stmt.executeUpdate("delete from ChequeDetails where ctrl_no="+ctrlno+" and ve_tml is null");
	 			a = stmt.executeUpdate("delete from Cheque where ctrl_no="+ctrlno+" and ve_tml is null");

	 			
	 		
	 		}catch(SQLException m){
	 			m.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return a;
	 	}

	 	public ChequeDepositionObject[] retrieveChequeDetails(int ctrlno, int type)throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		ResultSet rs=null;
	 		ResultSet rs1=null;
	 		String to_bounce=null;
	 		
	 		ChequeDepositionObject[] cd=null;
	 		try
	 		{
	 			System.out.println("1................................."+ ctrlno);
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 			if(type == 0)
	 			{
	 			 
	 				if(ctrlno==-1)// for outward non verified chqs
	 					rs=stmt.executeQuery("select ret_norm,doc_dest,clg_type,dr_ac_type,dr_ac_no,ctrl_no,bank_cd,br_name,trn_amt,to_bounce,mult_cr,comp_name,cr_ac_type,cr_ac_no,payee_name,chqddpo,qdp_no,qdp_dt,po_comm,disc_ind,desp_ind,disc_amt,loan_ac_type,loan_ac_no,ve_tml,bm.bank_name from Cheque,BankMaster bm where ve_tml is null and clg_type='O' and bank_cd=bm.bank_code");
	 				else if(ctrlno==-2)// For inward non verified chqs
	 					rs=stmt.executeQuery("select ret_norm,doc_dest,clg_type,dr_ac_type,dr_ac_no,ctrl_no,bank_cd,br_name,trn_amt,to_bounce,mult_cr,comp_name,cr_ac_type,cr_ac_no,payee_name,chqddpo,qdp_no,qdp_dt,po_comm,disc_ind,desp_ind,disc_amt,loan_ac_type,loan_ac_no,ve_tml,bm.bank_name from Cheque,BankMaster bm where ve_tml is null and clg_type='I' and bank_cd=bm.bank_code");
	 				else if(ctrlno==-11)// For inward non verified chqs
	 					rs=stmt.executeQuery("select ctrl_no , qdp_no , qdp_dt from Cheque where ve_tml is null and clg_type='O'");
	 				else
	 					rs=stmt.executeQuery("select ret_norm ,prev_ctrl_no,doc_dest,clg_type,dr_ac_type,fine_amt,dr_ac_no,bank_cd,br_name,trn_amt,to_bounce,mult_cr,comp_name,cr_ac_type,cr_ac_no,chqddpo,payee_name,qdp_no,qdp_dt,po_comm,disc_ind,desp_ind,disc_amt,loan_ac_type,loan_ac_no,ve_tml from Cheque where ctrl_no="+ctrlno+" and ve_tml is  null");
	 			
	 			} else if (type == 1) {
	 				
	 				rs=stmt.executeQuery("select ret_norm ,prev_ctrl_no,doc_dest,fine_amt,clg_type,dr_ac_type,dr_ac_no,bank_cd,br_name,trn_amt,doc_tot,to_bounce,mult_cr,comp_name,cr_ac_type,cr_ac_no,chqddpo,payee_name,qdp_no,qdp_dt,po_comm,disc_ind,desp_ind,post_ind,disc_amt,loan_ac_type,loan_ac_no,ve_tml,doc_bs from Cheque where ctrl_no = "+ctrlno+" and ve_tml is  not null and clg_type ='S' ");
	 				
	 			}
	 			
	 			else {
	 				
	 				rs=stmt.executeQuery("select ret_norm ,prev_ctrl_no,doc_dest,clg_type,dr_ac_type,dr_ac_no,bank_cd,br_name,trn_amt,to_bounce,mult_cr,comp_name,cr_ac_type,cr_ac_no,chqddpo,payee_name,qdp_no,qdp_dt,po_comm,disc_ind,desp_ind,disc_amt,loan_ac_type,loan_ac_no,ve_tml from Cheque where ctrl_no="+ctrlno+" and ve_tml is  not null");
	 			}
	 			
	 			if (ctrlno==-11){
	 				
	 				rs.last();
	 	 			if(rs.getRow()==0)
	 	 			{
	 	 				cd=null;
	 	 				return cd;
	 	 			}
	 	 			
	 	 			cd=new ChequeDepositionObject[rs.getRow()];
	 	 				rs.beforeFirst();
	 	 			
	 	 				int i=0;
	 	 				while(rs.next())
	 	 				{
	 	 					cd[i]=new ChequeDepositionObject();
	 	 					
	 	 					cd[i].setControlNo(rs.getInt("ctrl_no"));
	 	 					cd[i].setQdpDate(rs.getString("qdp_dt"));
	 	 					cd[i].setQdpNo(rs.getInt("qdp_no"));
	 	 					
	 	 					i++;
	 	 				}
	 				
	 			} else {
	 			
	 						rs.last();
	 						if(rs.getRow()==0)
	 						{
	 							cd=null;
	 							return cd;
	 						}
	 				
	 						cd=new ChequeDepositionObject[rs.getRow()];
	 						rs.beforeFirst();
	 						int i=0;
	 						while(rs.next())
	 						{
	 							System.out.println("2.........................");
	 							cd[i]=new ChequeDepositionObject();

	 							if(ctrlno==-1)
	 								cd[i].setControlNo(rs.getInt("ctrl_no"));
	 					
	 							cd[i].setDebitACType(rs.getString("dr_ac_type"));
	 							System.out.println("debit acc no is "+cd[i].getDebitACType());
	 					
	 							cd[i].setDebitACNo(rs.getInt("dr_ac_no"));
	 							System.out.println("debit acc no is "+cd[i].getDebitACNo());
	 					
	 							cd[i].setDocDestination(rs.getInt("doc_dest"));
	 							cd[i].setBankCode(rs.getString("bank_cd"));
	 							cd[i].setBranchName(rs.getString("br_name"));
	 							cd[i].setTranAmt(rs.getDouble("trn_amt"));
	 							to_bounce=rs.getString("to_bounce");
	 							
	 							if( to_bounce !=null && to_bounce.equalsIgnoreCase("T"))
	 							{
	 								cd[i].setBounceInd(to_bounce);
	 								System.out.println(11);
	 								Vector rea_arr = new Vector(0,1);
	 								Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 								rs1 = stat.executeQuery("select * from Reason where ctrl_no="+ctrlno);
	 								cd[i].setReasonFine(rs.getDouble("fine_amt"));
	 								while(rs1.next()){
	 									rea_arr.add(new Integer(rs1.getInt(2)));
	 								}
	 								cd[i].setReasonArray(rea_arr);
	 							}else 
	 								cd[i].setBounceInd("F");
	 							
	 							cd[i].setMultiCredit(rs.getString("mult_cr"));
	 							cd[i].setCompanyName(rs.getString("comp_name"));
	 							cd[i].setCreditACType(rs.getString("cr_ac_type"));
	 							cd[i].setCreditACNo(rs.getInt("cr_ac_no"));
	 							cd[i].setChqDDPO(rs.getString("chqddpo"));
	 							cd[i].setQdpNo(rs.getInt("qdp_no"));
	 							cd[i].setQdpDate(rs.getString("qdp_dt"));
	 							cd[i].setPOCommission(rs.getDouble("po_comm"));
	 							cd[i].setDiscInd(rs.getString("disc_ind"));
	 							cd[i].setDespInd(rs.getString("desp_ind"));
	 								
	 							if ( type == 1) {
	 								
	 								cd[i].setPost_ind(rs.getString("post_ind"));
	 								cd[i].setDoc_bs(rs.getString("doc_bs"));
	 								cd[i].setFineAmt(rs.getDouble("doc_tot"));
	 								cd[i].setReasonFine(rs.getDouble("fine_amt"));
	 							}

	 							cd[i].setDiscAmt(rs.getDouble("disc_amt"));
	 							cd[i].setVeTml(rs.getString("ve_tml"));
	 							
	 							//cd[i].setDeTime(rs.getString("bm.bank_name"));
	 							cd[i].setDeUser(rs.getString("ret_norm"));
	 							cd[i].setLoanAcc(rs.getString("loan_ac_type"));
	 							cd[i].setLoanAcc_No(rs.getInt("loan_ac_no"));
	 							cd[i].setPayeeName(rs.getString("payee_name"));
	 							cd[i].setClgType(rs.getString("clg_type"));
	 							cd[i].setprev_ctrl_no(rs.getLong("prev_ctrl_no"));
	 					
	 							i++;
	 						}
	 			}
	 			/*}
	 			else{
	 				cd=null;
	 				
	 			}*/
	 		}catch(SQLException sq){
	 			//cd[0].setBankCode(-1);
	 			sq.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cd;
	 	}

	 	public int chequeWithdrawal(int ctrlno, String tml,String usr,String date) throws RecordsNotInsertedException
	    {
	 		
	 		Connection conn=null;
	 		
	 		int i = 0;
	 		try {
	 			
	 			conn = getConnection();
	 			Statement stmt = conn.createStatement();
	 			Statement st = conn.createStatement();
	 			comm=commonHome.create();
	 			
	 			ResultSet rs = stmt.executeQuery("select * from Cheque where ctrl_no = "+ ctrlno);
	 			
	 			if ( rs.next()) {
	 				
	 				
	 				if ( rs.getString("doc_bs").equalsIgnoreCase("S") ) {
	 					
	 					if ( rs.getString("desp_ind").equalsIgnoreCase("F") )
	 					{
	 						System.out.println("inside the despatch false");
	 						
	 						ResultSet ret = st.executeQuery("select gl_code,gl_type from GLKeyParam  where (code = 9) and ac_type = '1011001'");
	 		 				
	 		 				
	 						if(ret.next())
	 						{
	 							GLTransObject trnobj=new GLTransObject();
	 							trnobj.setTrnDate(date);
	 							trnobj.setGLType(ret.getString(2));
	 							trnobj.setGLCode(ret.getString(1));
	 							trnobj.setTrnMode("G");
	 							trnobj.setAmount(rs.getDouble("trn_amt"));
	 							trnobj.setCdind("D");
	 							trnobj.setAccType(rs.getString("cr_ac_type"));
	 							trnobj.setAccNo(rs.getString("cr_ac_no"));
	 							trnobj.setTrnType("");
	 							trnobj.setRefNo(ctrlno);
	 							trnobj.setTrnSeq(0);
	 							trnobj.setVtml( tml );
	 							trnobj.setVid( usr );
	 							trnobj.setVDate( date );
	 							System.out.println(34);
	 							
	 							System.out.println("before 1 store");
	 							comm.storeGLTransaction(trnobj);
	 							System.out.println(35);
	 						}
	 						
	 						ResultSet ret1 = st.executeQuery("select * from GLKeyParam where ac_type='1011001' and code=10");
	 	 					if(ret1.next())
	 	 					{
	 	 						GLTransObject trnobj=new GLTransObject();
	 	 						trnobj.setTrnDate(date);
	 	 						trnobj.setGLType(ret1.getString(5));
	 	 						trnobj.setGLCode(ret1.getString(4));
	 	 						trnobj.setTrnMode("G");
	 	 						trnobj.setAmount(rs.getDouble("trn_amt"));
	 	 						trnobj.setCdind("C");
	 	 						trnobj.setAccType(rs.getString("cr_ac_type"));
	 	 						trnobj.setAccNo(rs.getString("cr_ac_no"));
	 	 						trnobj.setTrnType("");
	 	 						trnobj.setRefNo(ctrlno);
	 	 						trnobj.setTrnSeq(0);
	 	 						trnobj.setVtml(tml);
	 	 						trnobj.setVid(usr);
	 	 						trnobj.setVDate(date);
	 	 						System.out.println("before 2 store");
	 	 						comm.storeGLTransaction(trnobj);
	 	 						System.out.println (66);
	 	 					}
	 						
	 					} else if ( rs.getString("desp_ind").equalsIgnoreCase("T")) {
	 						
	 						System.out.println("inside the despatch True");
	 						
	 						ResultSet ret = st.executeQuery(" select gl_code,gl_type from GLKeyParam  where (code =3 ) and ac_type = '1011001'");
	 	 					
	 						if(ret.next())
	 	 					{
	 	 						GLTransObject trnobj=new GLTransObject();
	 	 						trnobj.setTrnDate(date);
	 	 						trnobj.setGLType(ret.getString(2));
	 	 						trnobj.setGLCode(ret.getString(1));
	 	 						trnobj.setTrnMode("G");
	 	 						trnobj.setAmount(rs.getDouble("trn_amt"));
	 	 						trnobj.setCdind("D");
	 	 						trnobj.setAccType(rs.getString("cr_ac_type"));
	 	 						trnobj.setAccNo(rs.getString("cr_ac_no"));
	 	 						trnobj.setTrnType("");
	 	 						trnobj.setRefNo(rs.getInt(4));
	 	 						trnobj.setTrnSeq(0);
	 	 						trnobj.setVtml(tml);
	 	 						trnobj.setVid(usr);
	 	 						trnobj.setVDate(date);
	 	 						System.out.println(34);
	 	 		
	 	 						comm.storeGLTransaction(trnobj);
	 	 						System.out.println(35);
	 	 					}
	 						
	 						
	 						ResultSet ret_ban = st.executeQuery("select br_ac_type,br_ac_no from BranchMaster where br_code ="+rs.getInt("send_to"));
	 						
	 						if ( ret_ban.next() ) {
	 							
	 							System.out.println("111111111111111");
	 		 					GLTransObject trnobj=new GLTransObject();
	 		 					trnobj.setTrnDate(date);
	 		 					trnobj.setGLType(ret_ban.getString("br_ac_type"));
			 					trnobj.setGLCode(ret_ban.getString("br_ac_no"));	 			 					
	 		 					trnobj.setTrnMode("G");
	 		 					trnobj.setAmount(rs.getDouble("trn_amt"));
	 		 					trnobj.setCdind("C ");
	 		 					trnobj.setAccType( rs.getString("cr_ac_type") );
	 		 					trnobj.setAccNo(rs.getString("cr_ac_no"));
	 		 					trnobj.setTrnType("P");
	 		 					
	 		 					trnobj.setRefNo(ctrlno);
	 		 					trnobj.setTrnSeq(0);
	 		 					trnobj.setVtml(tml);
	 		 					trnobj.setVid(usr);
	 		 					trnobj.setVDate(date);
	 		 					
	 		 					System.out.println(34);

	 		 					comm.storeGLTransaction(trnobj);System.out.println(35);
	 		 					
	 		 					System.out.println("22222222222222222");
	 						}
	 						
	 					}
	 					
	 					
	 				} else if ( rs.getString("doc_bs").equalsIgnoreCase("B") ) {
	 					
	 					ResultSet ret = st.executeQuery("select ac_type,gl_type,gl_code from GLKeyParam where ac_type=1011001 and code =11");
	 					
	 					if(ret.next())
	 					{
	 						System.out.println("0655506506555");
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate( date );
	 						trnobj.setGLType(ret.getString("gl_type"));
	 						trnobj.setGLCode(ret.getString("gl_code"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("doc_tot"));
	 						trnobj.setCdind("D");
	 						trnobj.setAccType(rs.getString("cr_ac_type"));
	 						trnobj.setAccNo(rs.getString("cr_ac_no"));
	 						
	 						trnobj.setTrnType("");
	 						trnobj.setRefNo( ctrlno);
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(tml);
	 						trnobj.setVid(usr);
	 						trnobj.setVDate(date);
	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);System.out.println(35);
	 						System.out.println("6666666666666666666");
	 					}
	 					
	 					
	 					ResultSet ret2 = st.executeQuery("select ac_type,gl_type,gl_code from GLKeyParam where ac_type=1011001 and code =2");
	 					
	 					if(ret2.next())
	 					{
	 					
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(date);
	 						trnobj.setGLType(ret2.getString("gl_type"));
	 						trnobj.setGLCode(ret2.getString("gl_code"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("doc_tot"));
	 						
	 						trnobj.setCdind("C");
	 						trnobj.setAccType(rs.getString("cr_ac_type"));
	 						trnobj.setAccNo(rs.getString("cr_ac_no"));
	 							
	 						
	 						trnobj.setTrnType("");
	 						trnobj.setRefNo(ctrlno);
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(tml);
	 						trnobj.setVDate(date);
	 						trnobj.setVid(usr);System.out.println(34);

	 						comm.storeGLTransaction(trnobj);System.out.println(35);
	 						System.out.println("888888888888888888");
	 					}
	 					
	 				} 
	 			
	 				 i = stmt.executeUpdate(" update Cheque set let_sent = 'T' ,post_ind = 'T' where ctrl_no = "+ ctrlno);
	 				
	 			}
	 			
	 		} catch (CreateException rec) {
	 			
	 			rec.printStackTrace();
	 		}
	 		catch ( SQLException eq) {
	 				
	 			eq.printStackTrace();
	 			
	 		}finally {
	 			
	 			try {
	 				
	 				conn.close();
	 			} catch ( SQLException dq) {
	 				
	 				
	 			}
	 		}
	 		
	 		return i;
	 	}
	 	
	 	
	 	public ChequeDepositionObject[] retrieveChequeDetails(String actype,int acno)throws RecordsNotFoundException
	 	{
	 		ResultSet rs=null;
	 		Connection conn=null;
	 		ChequeDepositionObject[] cd=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			//rs=stmt.executeQuery("select ctrl_no,bank_cd,br_name,trn_amt,to_bounce,mult_cr,comp_name,cr_ac_type,cr_ac_no,chqddpo,qdp_no,qdp_dt,po_comm,disc_ind,desp_ind,disc_amt,ve_tml,bm.bank_name from Cheque,BankMaster bm where cr_ac_type='"+actype+"' and cr_ac_no="+acno+" and post_ind='F' and bank_cd=bm.bank_code");
	 			rs=stmt.executeQuery("select ctrl_no,clg_type,bank_cd,br_name,trn_amt,to_bounce,mult_cr,comp_name,cr_ac_type,cr_ac_no,chqddpo,qdp_no,qdp_dt,po_comm,disc_ind,desp_ind,disc_amt,disc_chg,ve_tml,bm.bank_name from Cheque,BankMaster bm where cr_ac_type='"+actype+"' and cr_ac_no="+acno+" and post_ind='F' and clg_date is not null and substring(bank_cd,4,3) = bm.bank_code and br_name not in (select br_name from BranchMaster where br_code in (select bankcode from Head))");
	 			int j=0;
	 			while(rs.next())
	 			{
	 				if(rs.getString("disc_ind").equals("T"))
	 				{
	 					if(rs.getDouble("disc_amt")<rs.getDouble("trn_amt"))
	 						j++;
	 				}
	 				else
	 					j++;
	 			}
	 			if(j>0){
	 				cd=new ChequeDepositionObject[j];

	 				rs.beforeFirst();
	 				int i=0;
	 				boolean flag=false;
	 				while(rs.next())
	 				{
	 					if(rs.getString("disc_ind").equals("T"))
	 					{
	 						if(rs.getDouble("disc_amt")<rs.getDouble("trn_amt"))
	 							flag=true;
	 						else
	 							flag=false;
	 					}
	 					else
	 						flag=true;
	 					
	 					if(flag==true)
	 					{
	 						cd[i]=new ChequeDepositionObject();
	 						
	 						cd[i].setControlNo(rs.getInt("ctrl_no"));
	 						cd[i].setBankCode(rs.getString("bank_cd"));
	 						cd[i].setBranchName(rs.getString("br_name"));
	 						cd[i].setTranAmt(rs.getDouble("trn_amt"));
	 						cd[i].setBounceInd(rs.getString("to_bounce"));
	 						cd[i].setMultiCredit(rs.getString("mult_cr"));
	 						cd[i].setCompanyName(rs.getString("comp_name"));
	 						cd[i].setCreditACType(rs.getString("cr_ac_type"));
	 						cd[i].setCreditACNo(rs.getInt("cr_ac_no"));
	 						cd[i].setChqDDPO(rs.getString("chqddpo"));
	 						cd[i].setQdpNo(rs.getInt("qdp_no"));
	 						cd[i].setQdpDate(rs.getString("qdp_dt"));
	 						cd[i].setPOCommission(rs.getDouble("po_comm"));
	 						cd[i].setDiscInd(rs.getString("disc_ind"));
	 						cd[i].setDespInd(rs.getString("desp_ind"));
	 						cd[i].setClgType(rs.getString("clg_type"));
	 						cd[i].setDiscAmt(rs.getDouble("disc_amt"));
	 						cd[i].setDiscChg(rs.getDouble("disc_chg"));
	 						System.out.println("output is"+rs.getDouble("disc_chg"));
	 						
	 						cd[i].setVeTml(rs.getString("ve_tml"));
	 						cd[i].setDeTime(rs.getString("bm.bank_name"));
	 						i++;
	 					}
	 				}
	 			}
	 			else{
	 				cd=null;
	 				return cd;
	 			}
	 		}catch(SQLException sq){
	 			//cd[0].setBankCode(-1);
	 			sq.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cd;
	 	}
	 	
	 	public int chequeDiscount1(ChequeDepositionObject[] cd,int i)throws RecordNotInsertedException
	 	{
	 		ResultSet rs=null;
	 		Connection conn=null;
	 		int update=0;
	 		int trnSeq=0;
	 		int cat_type=0;
	 		double closeBal=0;
	 		double discntFine=0;
	 		

	 		try
	 		{
	 			comm = commonHome.create();
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			for(int j=0;j<cd.length;j++)
	 			{
	 				
	 				System.out.println("Weeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"+cd[j].getCreditACType()+cd[j].getCreditACNo());
	 				rs=stmt.executeQuery("select trn_seq,cl_bal from AccountTransaction where ac_type='"+cd[j].getCreditACType()+"' and ac_no="+cd[j].getCreditACNo()+" order by trn_seq desc limit 1");
	 				rs.next();
	 				trnSeq=rs.getInt(1)+1;
	 				if(i==0)
	 					closeBal = rs.getDouble(2) + cd[j].getDiscAmt();
	 				if(i==1)
	 					closeBal=rs.getDouble(2)+cd[j].getActDiscAmt();
	 				
	 				System.out.println(cd[j].getVe_datetime());
	 				System.out.println(cd[j].getVeUser());
	 				System.out.println(cd[j].getVeTml());
	 				
	 				if(i==0)
	 					update=stmt.executeUpdate("insert into AccountTransaction values('"+cd[j].getCreditACType()+"',"+cd[j].getCreditACNo()+",'"+cd[j].getVe_datetime()+"','R',"+trnSeq+","+cd[j].getDiscAmt()+",'G','"+cd[j].getVeTml()+"','C',"+cd[j].getQdpNo()+",'"+cd[j].getQdpDate()+"','Clg  "+cd[j].getControlNo()+" Discount Cheque',null,null,'"+closeBal+"',null,'"+cd[j].getVeTml()+"','"+cd[j].getVeUser()+"','"+cd[j].getVe_date()+"','"+cd[j].getVeTml()+"','"+cd[j].getVeUser()+"','"+ cd[j].getVe_datetime()+ "')");
	 				if(i==1)
	 					update=stmt.executeUpdate("insert into AccountTransaction values('"+cd[j].getCreditACType()+"',"+cd[j].getCreditACNo()+","+cd[j].getVe_datetime()+",'R',"+trnSeq+","+cd[j].getActDiscAmt()+",'G','"+cd[j].getVeTml()+"','C',"+cd[j].getQdpNo()+",'"+cd[j].getQdpDate()+"','Clg  "+cd[j].getControlNo()+"',null,null,'"+closeBal+"',null,'"+cd[j].getVeTml()+"','"+cd[j].getVeUser()+"','"+cd[j].getVe_datetime()+"','"+cd[j].getVeTml()+"','"+cd[j].getVeUser()+"','"+cd[j].getVe_datetime()+"')");
	 				// GL codes goes here for one Dedit entry
	 				
	 				rs=stmt.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =5 and gk.ac_type = '1011001'");
	 				if(rs.next()){
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(getToday());
	 					System.out.println(trnobj.getTrnDate());
	 					
	 					trnobj.setGLType(rs.getString("gl_type"));
	 					System.out.println(trnobj.getGLType());
	 					
	 					trnobj.setGLCode(rs.getString("gl_code"));
	 					System.out.println(trnobj.getGLCode());
	 					
	 					trnobj.setTrnMode("G");
	 					System.out.println(trnobj.getTrnMode());
	 					
	 					trnobj.setAmount(cd[j].getDiscAmt());
	 					System.out.println(trnobj.getAmount());
	 					trnobj.setCdind("D");
	 				
	 					trnobj.setAccType(cd[j].getCreditACType());
	 					System.out.println(trnobj.getAccType());
	 					
	 					trnobj.setAccNo(cd[j].getCreditACNo()+"");
	 					System.out.println(trnobj.getAccNo());
	 					
	 					trnobj.setTrnType("P");
	 					trnobj.setRefNo(trnSeq);
	 					System.out.println(trnobj.getRefNo());
	 					
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(cd[j].getVeTml());
	 					System.out.println(trnobj.getVtml());
	 					
	 					trnobj.setVDate(cd[j].getVe_datetime());
	 					System.out.println(trnobj.getVDate());
	 					
	 					trnobj.setVid(cd[j].getVeUser());
	 					System.out.println(trnobj.getVid());
	 					System.out.println(34);

	 					int h =comm.storeGLTransaction(trnobj);
	 					System.out.println("jkbfjkbdfjd");
	 					
	 					System.out.println(35);
	 					System.out.println("888888888888888888");
	 				}
	 				
	 				// GL codes goes here for one Credit entry
	 				if(cd[j].getCreditACType().startsWith("1014") ||cd[j].getCreditACType().startsWith("1015"))
							rs = stmt.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+cd[j].getCreditACNo()+" and od.ac_type='"+cd[j].getCreditACType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
						else
							rs = stmt.executeQuery("select am.ac_type,trn_seq from AccountMaster am,AccountTransaction att where am.ac_no="+cd[j].getCreditACNo()+" and am.ac_type='"+cd[j].getCreditACType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
						
						if(rs.next())
						{
							if(rs.getInt(1)==0)
								cat_type=1;
							else
								cat_type=2;
						}
						
	 				rs=stmt.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+cd[j].getCreditACType()+"' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 				if(rs.next()){
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(getToday());
	 					trnobj.setGLType(rs.getString("gl_type"));
	 					trnobj.setGLCode(rs.getString("gl_code"));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(cd[j].getDiscAmt());
	 					trnobj.setCdind("C");
	 				
	 					trnobj.setAccType(cd[j].getCreditACType());
	 					trnobj.setAccNo(cd[j].getCreditACNo()+"");
	 				
	 					trnobj.setTrnType("R");
	 					trnobj.setRefNo(trnSeq);
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(cd[j].getVeTml());
	 					trnobj.setVDate(cd[j].getVe_datetime());
	 					trnobj.setVid(cd[j].getVeUser());
	 					System.out.println(34);

	 					comm.storeGLTransaction(trnobj);
	 					System.out.println(35);
	 					System.out.println("99999999999999>-->above-->");
	 				}
	 				
						
	 				
	 				if(i==0)
	 					discntFine=DiscountChargeCalculation(cd[j].getCreditACType(),cd[j].getDiscAmt());
	 				else if(i==1)
	 					discntFine=DiscountChargeCalculation(cd[j].getCreditACType(),cd[j].getActDiscAmt());
	 				
	 				System.out.println("discntFine=="+discntFine);
	 				rs=stmt.executeQuery("select trn_seq,cl_bal from AccountTransaction where ac_type='"+cd[j].getCreditACType()+"' and ac_no="+cd[j].getCreditACNo()+" order by trn_seq desc limit 1");
	 				rs.next();
	 				trnSeq=rs.getInt(1)+1;
	 				closeBal=rs.getDouble(2)-discntFine;
	 				
	 				stmt.addBatch("insert into AccountTransaction values('"+cd[j].getCreditACType()+"',"+cd[j].getCreditACNo()+",'"+cd[j].getVe_date()+"','P',"+trnSeq+","+discntFine+",'G','"+cd[j].getVeTml()+"','D',"+cd[j].getQdpNo()+",'"+cd[j].getQdpDate()+"','Clg Disc Chrg "+cd[j].getControlNo()+"',null,null,"+closeBal+",null,'"+cd[j].getVeTml()+"','"+cd[j].getVeUser()+"','"+cd[j].getVe_datetime()+"','"+cd[j].getVeTml()+"','"+cd[j].getVeUser()+"','"+cd[j].getVe_datetime()+"')");
	 				//stmt.addBatch("update AccountMaster set last_tr_seq="+trnSeq+",last_tr_date=date_format(sysdate(),'%d/%m/%Y ')");
	 				stmt.addBatch("update AccountMaster set last_tr_seq="+trnSeq+",last_tr_date='"+cd[j].getVe_date()+"'");
	 				double total_dis_amt=cd[j].getDiscAmt()+cd[j].getActDiscAmt() ;
	 				System.out.println(total_dis_amt);
	 				stmt.addBatch("update Cheque set disc_ind = 'T',disc_amt="+total_dis_amt+",disc_chg="+cd[j].getDiscChg()+" where ctrl_no="+cd[j].getControlNo());
	 				stmt.executeBatch();
	 				
	 				rs=stmt.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =6 and gk.ac_type = '1011001'");
	 				
	 				if(rs.next()){
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(getToday());
	 					trnobj.setGLType(rs.getString("gl_type"));
	 					trnobj.setGLCode(rs.getString("gl_code"));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(discntFine);
	 					trnobj.setCdind("C");
	 				
	 					trnobj.setAccType(cd[j].getCreditACType());
	 					trnobj.setAccNo(cd[j].getCreditACNo()+"");
	 				
	 					trnobj.setTrnType("R");
	 					trnobj.setRefNo(trnSeq);
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(cd[j].getVeTml());
	 					trnobj.setVDate(cd[j].getVe_datetime());
	 					trnobj.setVid(cd[j].getVeUser());
	 					System.out.println(34);

	 					comm.storeGLTransaction(trnobj);System.out.println(35);
	 					System.out.println("888888888888888888");

	 				}
	 				
	 				if(cd[j].getCreditACType().startsWith("1014") ||cd[j].getCreditACType().startsWith("1015"))
						rs = stmt.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+cd[j].getCreditACNo()+" and od.ac_type='"+cd[j].getCreditACType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
					else
						rs = stmt.executeQuery("select am.ac_type,trn_seq from AccountMaster am,AccountTransaction att where am.ac_no="+cd[j].getCreditACNo()+" and am.ac_type='"+cd[j].getCreditACType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
					
					if(rs.next())
					{
						if(rs.getInt(1)==0)
							cat_type=1;
						else
							cat_type=2;
					}
					rs=stmt.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+cd[j].getCreditACType()+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 				if(rs.next()){
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(getToday());
	 					trnobj.setGLType(rs.getString("gl_type"));
	 					trnobj.setGLCode(rs.getString("gl_code"));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(discntFine);
	 					trnobj.setCdind("D");
	 				
	 					trnobj.setAccType(cd[j].getCreditACType());
	 					trnobj.setAccNo(cd[j].getCreditACNo()+"");
	 				
	 					trnobj.setTrnType("P");
	 					trnobj.setRefNo(trnSeq);
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(cd[j].getVeTml());
	 					trnobj.setVDate(cd[j].getVe_datetime());
	 					trnobj.setVid(cd[j].getVeUser());
	 					System.out.println(34);

	 					comm.storeGLTransaction(trnobj);
	 					System.out.println(35);
	 					System.out.println("99999999999999>-->--hiiii->");
	 				}
	 				

	 				
	 				
	 			}
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return update;
	 	}

	 	public int chequeDisBean(ChequeDepositionObject[] cd,int i, String date )throws RecordNotInsertedException
	 	{
	 	
	 		ResultSet rs=null;
	 		Connection conn=null;
	 		int update=0;
	 		int trnSeq=0;
	 		int cat_type=0;
	 		double closeBal=0;
	 		double discntFine=0;
	 		System.out.println("inside cheque discount");

	 		try
	 		{	System.out.println("inside if");
	 			comm = commonHome.create();
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 			for(int j=0;j<cd.length;j++)
	 			{
	 				if(i == 0)
	 				{
	 					
	 					AccountTransObject accoun = new AccountTransObject();
	 					//System.out.println("before setting ac type"+cd[j].getCreditACType());
	 					accoun.setAccType(cd[j].getCreditACType());
	 					accoun.setAccNo(cd[j].getCreditACNo());
	 					accoun.setTransType("R");
	 					accoun.setTransAmount(cd[j].getDiscAmt());
	 					accoun.setTransMode("G");
	 					accoun.setTransSource(cd[j].getVeTml());
	 					accoun.setTransDate(date);
							
	 					accoun.setCdInd("C");
	 					accoun.setChqDDNo(cd[j].getQdpNo());
	 					accoun.setChqDDDate(cd[j].getQdpDate());
	 					accoun.setTransNarr("Discount Amount, Ctrl No "+cd[j].getControlNo());
	 					accoun.setRef_No(Integer.parseInt(String.valueOf(cd[j].getControlNo())));
	 					accoun.setPayeeName(cd[j].getPayeeName());
	 					accoun.setLedgerPage(0);
	 					accoun.uv.setUserTml(cd[j].getVeTml());
	 					accoun.uv.setUserId(cd[j].getVeUser());
	 					accoun.uv.setUserDate(cd[j].getVe_date());
	 					accoun.uv.setVerTml(cd[j].getVeTml());
	 					accoun.uv.setVerId(cd[j].getVeUser());
	 					accoun.uv.setVerDate(cd[j].getVe_datetime());
	 					accoun.setTransDate(date);
							
	 					comm.storeAccountTransaction(accoun);
	 					
	 				}
	 				else if (i ==1)
	 				{
	 					
	 					AccountTransObject accoun = new AccountTransObject();
	 					accoun.setAccType(cd[j].getCreditACType());
	 					accoun.setAccNo(cd[j].getCreditACNo());
	 					accoun.setTransType("R");
	 					accoun.setTransAmount(cd[j].getActDiscAmt());
	 					accoun.setTransMode("G");
	 					accoun.setTransSource(cd[j].getVeTml());
	 					accoun.setTransDate(date);
							
	 					accoun.setCdInd("C");
	 					accoun.setChqDDNo(cd[j].getQdpNo());
	 					accoun.setChqDDDate(cd[j].getQdpDate());
	 					accoun.setTransNarr("Discount Amount, Ctrl No "+cd[j].getControlNo());
	 					accoun.setRef_No(Integer.parseInt(String.valueOf(cd[j].getControlNo())));
	 					accoun.setPayeeName(cd[j].getPayeeName());
	 					accoun.setLedgerPage(0);
	 					accoun.uv.setUserTml(cd[j].getVeTml());
	 					accoun.uv.setUserId(cd[j].getVeUser());
	 					accoun.uv.setUserDate(cd[j].getVe_date());
	 					accoun.uv.setVerTml(cd[j].getVeTml());
	 					accoun.uv.setVerId(cd[j].getVeUser());
	 					accoun.uv.setVerDate(cd[j].getVe_datetime());
	 					accoun.setTransDate(date);
							
	 					comm.storeAccountTransaction(accoun);
	 					
	 				}
	 				
	 				// GL codes goes here for one Credit entry
	 				if(cd[j].getCreditACType().startsWith("1014") ||cd[j].getCreditACType().startsWith("1015"))
							rs = stmt.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+cd[j].getCreditACNo()+" and od.ac_type='"+cd[j].getCreditACType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
						else
							rs = stmt.executeQuery("select am.ac_type,trn_seq from AccountMaster am,AccountTransaction att where am.ac_no="+cd[j].getCreditACNo()+" and am.ac_type='"+cd[j].getCreditACType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
						
						if(rs.next())
						{
							if(rs.getInt(1)==0)
								cat_type=1;
							else
								cat_type=2;
						}
						
	 				rs=stmt.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+cd[j].getCreditACType()+"' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 				if(rs.next()){
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(date);
	 					trnobj.setGLType(rs.getString("gl_type"));
	 					trnobj.setGLCode(rs.getString("gl_code"));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(cd[j].getDiscAmt());
	 					trnobj.setCdind("C");
	 				
	 					trnobj.setAccType(cd[j].getCreditACType());
	 					trnobj.setAccNo(cd[j].getCreditACNo()+"");
	 				
	 					trnobj.setTrnType("R");
	 					trnobj.setRefNo(trnSeq);
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(cd[j].getVeTml());
	 					trnobj.setVDate(cd[j].getVe_datetime());
	 					trnobj.setVid(cd[j].getVeUser());
	 					System.out.println(34);

	 					comm.storeGLTransaction(trnobj);
	 					System.out.println(35);
	 					System.out.println("99999999999999>--mmmmmeeeeeeeeeeeeeee--->");
	 				}
	 			
	 				rs=stmt.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =5 and gk.ac_type = '1011001'");
	 				if(rs.next()){
	 					GLTransObject trnobj=new GLTransObject();
	 					
	 					trnobj.setTrnDate(date);
	 					trnobj.setGLType(rs.getString("gl_type"));
	 					trnobj.setGLCode(rs.getString("gl_code"));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(cd[j].getDiscAmt());
	 					trnobj.setCdind("D");
	 					trnobj.setAccType(cd[j].getCreditACType());
	 					trnobj.setAccNo(cd[j].getCreditACNo()+"");
	 					trnobj.setTrnType("P");
	 					trnobj.setRefNo((int)cd[j].getControlNo());
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(cd[j].getVeTml());
	 					trnobj.setVDate(cd[j].getVe_datetime());
	 					trnobj.setVid(cd[j].getVeUser());
	 					System.out.println("before  gl tran noww");
	 					int h =comm.storeGLTransaction(trnobj);
	 					System.out.println("after gl tran NOWWW");
	 				}
	 				
	 				if(i==0)
	 					discntFine=DiscountChargeCalculation(cd[j].getCreditACType(),cd[j].getDiscAmt());
	 				else if(i==1)
	 					discntFine=DiscountChargeCalculation(cd[j].getCreditACType(),cd[j].getActDiscAmt());
	 				
	 				if(discntFine > 0.0)
	 				{
	 					{
	 						System.out.println("dict amttt");
	 					AccountTransObject accoun = new AccountTransObject();
	 					accoun.setAccType(cd[j].getCreditACType());
	 					accoun.setAccNo(cd[j].getCreditACNo());
	 					accoun.setTransType("T");
	 					accoun.setTransAmount(discntFine);
	 					accoun.setTransMode("G");
	 					accoun.setTransSource(cd[j].getVeTml());
	 					accoun.setTransDate(date);
	 					accoun.setCdInd("D");
	 					accoun.setChqDDNo(cd[j].getQdpNo());
	 					accoun.setChqDDDate(cd[j].getQdpDate());
	 					accoun.setTransNarr("Discount Charges, Ctrl No "+cd[j].getControlNo());
	 					accoun.setRef_No(Integer.parseInt(String.valueOf(cd[j].getControlNo())));
	 					accoun.setPayeeName(cd[j].getPayeeName());
	 					accoun.setLedgerPage(0);
	 					accoun.uv.setUserTml(cd[j].getVeTml());
	 					accoun.uv.setUserId(cd[j].getVeUser());
	 					accoun.uv.setUserDate(cd[j].getVe_date());
	 					accoun.uv.setVerTml(cd[j].getVeTml());
	 					accoun.uv.setVerId(cd[j].getVeUser());
	 					accoun.uv.setVerDate(cd[j].getVe_datetime());
	 					accoun.setTransDate(date);
							System.out.println("before acc Transaction");
	 					comm.storeAccountTransaction(accoun);
	 					System.out.println("after accc Transcation");
	 				}
	 				// fine amount debit entry 
	 				// GL codes goes here for one Debit entry , 
	 				if(cd[j].getCreditACType().startsWith("1014") ||cd[j].getCreditACType().startsWith("1015"))
						rs = stmt.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+cd[j].getCreditACNo()+" and od.ac_type='"+cd[j].getCreditACType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
					else
						rs = stmt.executeQuery("select am.ac_type,trn_seq from AccountMaster am,AccountTransaction att where am.ac_no="+cd[j].getCreditACNo()+" and am.ac_type='"+cd[j].getCreditACType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
					
					if(rs.next())
					{
						if(rs.getInt(1)==0)
							cat_type=1;
						else
							cat_type=2;
					}
					rs=stmt.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+cd[j].getCreditACType()+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 				if(rs.next()){
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(date);
	 					trnobj.setGLType(rs.getString("gl_type"));
	 					trnobj.setGLCode(rs.getString("gl_code"));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(discntFine);
	 					trnobj.setCdind("D");
	 				
	 					trnobj.setAccType(cd[j].getCreditACType());
	 					trnobj.setAccNo(cd[j].getCreditACNo()+"");
	 				
	 					trnobj.setTrnType("P");
	 					trnobj.setRefNo(trnSeq);
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(cd[j].getVeTml());
	 					trnobj.setVDate(cd[j].getVe_datetime());
	 					trnobj.setVid(cd[j].getVeUser());
	 					System.out.println(34);

	 					comm.storeGLTransaction(trnobj);System.out.println(35);
	 					System.out.println("99999999999999>--wwwwwwwwwwwwwwwwwwwee--->");
	 				}
	 				
	 				rs=stmt.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =6 and gk.ac_type = '1011001'");
	 				
	 				if(rs.next())
	 				{
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(date);
	 					trnobj.setGLType(rs.getString("gl_type"));
	 					trnobj.setGLCode(rs.getString("gl_code"));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(discntFine);
	 					trnobj.setCdind("C");
	 				
	 					trnobj.setAccType(cd[j].getCreditACType());
	 					trnobj.setAccNo(cd[j].getCreditACNo()+"");
	 				
	 					trnobj.setTrnType("R");
	 					trnobj.setRefNo(trnSeq);
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(cd[j].getVeTml());
	 					trnobj.setVDate(cd[j].getVe_datetime());
	 					trnobj.setVid(cd[j].getVeUser());
	 					System.out.println(34);

	 					comm.storeGLTransaction(trnobj);
	 					System.out.println(35);
	 					System.out.println("888888888888888888");

	 				}
	 			}
	 			
	 				double total_dis_amt=cd[j].getDiscAmt()+cd[j].getActDiscAmt();
	 				double disc_chg=cd[j].getDiscChg();
	 				System.out.println("disc chg============="+disc_chg);
	 				long ctrl_no=cd[j].getControlNo();
	 				System.out.println("control====="+ctrl_no);
	 				System.out.println("totallalallal====="+total_dis_amt);
	 				stmt.addBatch("update Cheque set disc_ind ='T',disc_amt="+total_dis_amt+",disc_chg="+disc_chg+" where ctrl_no="+ctrl_no+"");
	 				stmt.executeBatch();
	 				System.out.println("after update batch");
	 				
	 			}
	 			
	 		}
	 		catch(SQLException sql)
	 		{
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("Eception in Bean");
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 			
	 		return 1;
	 		
	 	}
	 	
	 	
	 	
	 	public int chequeDiscount(ChequeDepositionObject[] cd,int i, String date )throws RecordNotInsertedException
	 	{
	 		ResultSet rs=null;
	 		Connection conn=null;
	 		int update=0;
	 		int trnSeq=0;
	 		int cat_type=0;
	 		double closeBal=0;
	 		double discntFine=0;
	 		System.out.println("inside cheque discount");

	 		try
	 		{	System.out.println("inside if");
	 			comm = commonHome.create();
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			System.out.println("cd length in beannnnn====>"+cd.length);
	 			for(int j=0;j<cd.length;j++)
	 			{
	 				if(i == 0)
	 				{
	 					
	 					AccountTransObject accoun = new AccountTransObject();
	 					System.out.println("before setting ac type"+cd[j].getCreditACType());
	 					accoun.setAccType(cd[j].getCreditACType());
	 					accoun.setAccNo(cd[j].getCreditACNo());
	 					accoun.setTransType("R");
	 					accoun.setTransAmount(cd[j].getDiscAmt());
	 					accoun.setTransMode("G");
	 					accoun.setTransSource(cd[j].getVeTml());
	 					accoun.setTransDate(date);
							
	 					accoun.setCdInd("C");
	 					accoun.setChqDDNo(cd[j].getQdpNo());
	 					accoun.setChqDDDate(cd[j].getQdpDate());
	 					accoun.setTransNarr("Discount Amount, Ctrl No "+cd[j].getControlNo());
	 					accoun.setRef_No(Integer.parseInt(String.valueOf(cd[j].getControlNo())));
	 					accoun.setPayeeName(cd[j].getPayeeName());
	 					accoun.setLedgerPage(0);
	 					accoun.uv.setUserTml(cd[j].getVeTml());
	 					accoun.uv.setUserId(cd[j].getVeUser());
	 					accoun.uv.setUserDate(cd[j].getVe_date());
	 					accoun.uv.setVerTml(cd[j].getVeTml());
	 					accoun.uv.setVerId(cd[j].getVeUser());
	 					accoun.uv.setVerDate(cd[j].getVe_datetime());
	 					accoun.setTransDate(date);
							
	 					comm.storeAccountTransaction(accoun);
	 					
	 				}
	 				else if (i ==1)
	 				{
	 					
	 					AccountTransObject accoun = new AccountTransObject();
	 					accoun.setAccType(cd[j].getCreditACType());
	 					accoun.setAccNo(cd[j].getCreditACNo());
	 					accoun.setTransType("R");
	 					accoun.setTransAmount(cd[j].getActDiscAmt());
	 					accoun.setTransMode("G");
	 					accoun.setTransSource(cd[j].getVeTml());
	 					accoun.setTransDate(date);
							
	 					accoun.setCdInd("C");
	 					accoun.setChqDDNo(cd[j].getQdpNo());
	 					accoun.setChqDDDate(cd[j].getQdpDate());
	 					accoun.setTransNarr("Discount Amount, Ctrl No "+cd[j].getControlNo());
	 					accoun.setRef_No(Integer.parseInt(String.valueOf(cd[j].getControlNo())));
	 					accoun.setPayeeName(cd[j].getPayeeName());
	 					accoun.setLedgerPage(0);
	 					accoun.uv.setUserTml(cd[j].getVeTml());
	 					accoun.uv.setUserId(cd[j].getVeUser());
	 					accoun.uv.setUserDate(cd[j].getVe_date());
	 					accoun.uv.setVerTml(cd[j].getVeTml());
	 					accoun.uv.setVerId(cd[j].getVeUser());
	 					accoun.uv.setVerDate(cd[j].getVe_datetime());
	 					accoun.setTransDate(date);
							
	 					comm.storeAccountTransaction(accoun);
	 					
	 				}
	 				
	 				// GL codes goes here for one Credit entry
	 				if(cd[j].getCreditACType().startsWith("1014") ||cd[j].getCreditACType().startsWith("1015"))
							rs = stmt.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+cd[j].getCreditACNo()+" and od.ac_type='"+cd[j].getCreditACType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
						else
							rs = stmt.executeQuery("select am.ac_type,trn_seq from AccountMaster am,AccountTransaction att where am.ac_no="+cd[j].getCreditACNo()+" and am.ac_type='"+cd[j].getCreditACType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
						
						if(rs.next())
						{
							if(rs.getInt(1)==0)
								cat_type=1;
							else
								cat_type=2;
						}
						
	 				rs=stmt.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+cd[j].getCreditACType()+"' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 				if(rs.next()){
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(date);
	 					trnobj.setGLType(rs.getString("gl_type"));
	 					trnobj.setGLCode(rs.getString("gl_code"));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(cd[j].getDiscAmt());
	 					trnobj.setCdind("C");
	 				
	 					trnobj.setAccType(cd[j].getCreditACType());
	 					trnobj.setAccNo(cd[j].getCreditACNo()+"");
	 				
	 					trnobj.setTrnType("R");
	 					trnobj.setRefNo(trnSeq);
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(cd[j].getVeTml());
	 					trnobj.setVDate(cd[j].getVe_datetime());
	 					trnobj.setVid(cd[j].getVeUser());
	 					System.out.println(34);

	 					comm.storeGLTransaction(trnobj);
	 					System.out.println(35);
	 					System.out.println("99999999999999>--mmmmmeeeeeeeeeeeeeee--->");
	 				}
	 			
	 				rs=stmt.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =5 and gk.ac_type = '1011001'");
	 				if(rs.next()){
	 					GLTransObject trnobj=new GLTransObject();
	 					
	 					trnobj.setTrnDate(date);
	 					trnobj.setGLType(rs.getString("gl_type"));
	 					trnobj.setGLCode(rs.getString("gl_code"));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(cd[j].getDiscAmt());
	 					trnobj.setCdind("D");
	 					trnobj.setAccType(cd[j].getCreditACType());
	 					trnobj.setAccNo(cd[j].getCreditACNo()+"");
	 					trnobj.setTrnType("P");
	 					trnobj.setRefNo((int)cd[j].getControlNo());
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(cd[j].getVeTml());
	 					trnobj.setVDate(cd[j].getVe_datetime());
	 					trnobj.setVid(cd[j].getVeUser());
	 					System.out.println("before  gl tran noww");
	 					int h =comm.storeGLTransaction(trnobj);
	 					System.out.println("after gl tran NOWWW");
	 				}
	 				
	 				if(i==0)
	 					discntFine=DiscountChargeCalculation(cd[j].getCreditACType(),cd[j].getDiscAmt());
	 				else if(i==1)
	 					discntFine=DiscountChargeCalculation(cd[j].getCreditACType(),cd[j].getActDiscAmt());
	 				
	 				if(discntFine > 0.0)
	 				{
	 					{
	 						System.out.println("dict amttt");
	 					AccountTransObject accoun = new AccountTransObject();
	 					accoun.setAccType(cd[j].getCreditACType());
	 					accoun.setAccNo(cd[j].getCreditACNo());
	 					accoun.setTransType("T");
	 					accoun.setTransAmount(discntFine);
	 					accoun.setTransMode("G");
	 					accoun.setTransSource(cd[j].getVeTml());
	 					accoun.setTransDate(date);
	 					accoun.setCdInd("D");
	 					accoun.setChqDDNo(cd[j].getQdpNo());
	 					accoun.setChqDDDate(cd[j].getQdpDate());
	 					accoun.setTransNarr("Discount Charges, Ctrl No "+cd[j].getControlNo());
	 					accoun.setRef_No(Integer.parseInt(String.valueOf(cd[j].getControlNo())));
	 					accoun.setPayeeName(cd[j].getPayeeName());
	 					accoun.setLedgerPage(0);
	 					accoun.uv.setUserTml(cd[j].getVeTml());
	 					accoun.uv.setUserId(cd[j].getVeUser());
	 					accoun.uv.setUserDate(cd[j].getVe_date());
	 					accoun.uv.setVerTml(cd[j].getVeTml());
	 					accoun.uv.setVerId(cd[j].getVeUser());
	 					accoun.uv.setVerDate(cd[j].getVe_datetime());
	 					accoun.setTransDate(date);
							System.out.println("before acc Transaction");
	 					comm.storeAccountTransaction(accoun);
	 					System.out.println("after accc Transcation");
	 				}
	 				// fine amount debit entry 
	 				// GL codes goes here for one Debit entry , 
	 				if(cd[j].getCreditACType().startsWith("1014") ||cd[j].getCreditACType().startsWith("1015"))
						rs = stmt.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+cd[j].getCreditACNo()+" and od.ac_type='"+cd[j].getCreditACType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
					else
						rs = stmt.executeQuery("select am.ac_type,trn_seq from AccountMaster am,AccountTransaction att where am.ac_no="+cd[j].getCreditACNo()+" and am.ac_type='"+cd[j].getCreditACType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
					
					if(rs.next())
					{
						if(rs.getInt(1)==0)
							cat_type=1;
						else
							cat_type=2;
					}
					rs=stmt.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+cd[j].getCreditACType()+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 				if(rs.next()){
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(date);
	 					trnobj.setGLType(rs.getString("gl_type"));
	 					trnobj.setGLCode(rs.getString("gl_code"));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(discntFine);
	 					trnobj.setCdind("D");
	 				
	 					trnobj.setAccType(cd[j].getCreditACType());
	 					trnobj.setAccNo(cd[j].getCreditACNo()+"");
	 				
	 					trnobj.setTrnType("P");
	 					trnobj.setRefNo(trnSeq);
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(cd[j].getVeTml());
	 					trnobj.setVDate(cd[j].getVe_datetime());
	 					trnobj.setVid(cd[j].getVeUser());
	 					System.out.println(34);

	 					comm.storeGLTransaction(trnobj);System.out.println(35);
	 					System.out.println("99999999999999>--wwwwwwwwwwwwwwwwwwwee--->");
	 				}
	 				
	 				rs=stmt.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =6 and gk.ac_type = '1011001'");
	 				
	 				if(rs.next())
	 				{
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(date);
	 					trnobj.setGLType(rs.getString("gl_type"));
	 					trnobj.setGLCode(rs.getString("gl_code"));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(discntFine);
	 					trnobj.setCdind("C");
	 				
	 					trnobj.setAccType(cd[j].getCreditACType());
	 					trnobj.setAccNo(cd[j].getCreditACNo()+"");
	 				
	 					trnobj.setTrnType("R");
	 					trnobj.setRefNo(trnSeq);
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(cd[j].getVeTml());
	 					trnobj.setVDate(cd[j].getVe_datetime());
	 					trnobj.setVid(cd[j].getVeUser());
	 					System.out.println(34);

	 					comm.storeGLTransaction(trnobj);
	 					System.out.println(35);
	 					System.out.println("888888888888888888");

	 				}
	 			}
	 			
	 				double total_dis_amt=cd[j].getDiscAmt()+cd[j].getActDiscAmt() ;
	 				System.out.println(total_dis_amt);
	 				stmt.addBatch("update Cheque set disc_ind = 'T',disc_amt="+total_dis_amt+",disc_chg="+cd[j].getDiscChg()+" where ctrl_no="+cd[j].getControlNo());
	 				stmt.executeBatch();
	 				
	 			}
	 			
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}catch(Exception e){
	 			System.out.println("Eception in Bean");
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			//throw new Exception();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 			
	 		return 1;
	 	}
	 	
	 	
	 	
	 	public CompanyMaster[] retrieveCompanyDetails(int ctrlno)throws RecordsNotFoundException
	 	{
	 		ResultSet rs=null;
	 		Connection conn=null;
	 		CompanyMaster[] cm=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			if(ctrlno == -1)
	 				rs=stmt.executeQuery("select cm.*,moduleabbr from CompanyMaster cm,Modules where ac_type=modulecode group by comp_name");
	 		
	 			else if(ctrlno == -2)
	 				rs=stmt.executeQuery("select comp_code,comp_name,ac_type,ac_no,cm.de_tml,cm.de_user,cm.de_dt_time,cm.ve_tml , cm.ve_user,cm.ve_dt_time,moduleabbr from CompanyMaster cm ,Modules where ac_type=modulecode order by comp_name,ac_type,ac_no");
	 			else
	 				rs=stmt.executeQuery("select ch.ve_dt_time,cpm.comp_name,cpm.ac_type,cpm.ac_no,concat(ifnull(cm.fname,''),' ',ifnull(cm.mname,''),' ',ifnull(cm.lname,'')) as name,ch.trn_amt from CompanyMaster cpm,Cheque ch,AccountMaster am,CustomerMaster cm where ch.ctrl_no="+ctrlno+" and ch.comp_name is not null and ch.comp_name=cpm.comp_name and cpm.ac_type=am.ac_type and cpm.ac_no=am.ac_no and am.cid=cm.cid order by cpm.ac_no");
	 			System.out.println("3333");
	 			rs.last();
	 			System.out.println("rs.getRow()=="+rs.getRow());

	 			if(rs.getRow()>0)
	 			{
	 				cm=new CompanyMaster[rs.getRow()];
	 				rs.beforeFirst();
	 				int i=0;
	 				while(rs.next())
	 				{
	 					cm[i]=new CompanyMaster();
	 					if(ctrlno == -1 || ctrlno == -2)
	 					{
	 						cm[i].setCompanyCode(rs.getInt("comp_code"));
	 						cm[i].setAccType(rs.getString("moduleabbr"));
	 						cm[i].setCompanyName(rs.getString("comp_name"));
	 						cm[i].setDeTml(rs.getString("de_tml"));
	 						cm[i].setDeUser(rs.getString("de_user"));
	 						cm[i].setDeTime(rs.getString("de_dt_time"));
	 						cm[i].setVeTime(rs.getString("ve_tml"));
	 						cm[i].setVeTml(rs.getString("ve_dt_time"));
	 						cm[i].setVeUser(rs.getString("ve_user"));
	 					}
	 					else
	 					{
	 						cm[i].setDeUser(String.valueOf(rs.getDouble("trn_amt")));
	 						cm[i].setCompanyName(rs.getString("name"));
	 						cm[i].setAccType(rs.getString("ac_type"));
	 					}
	 					
	 					cm[i].setAccNo(rs.getInt("ac_no"));
	 					cm[i].setDeTml(rs.getString("de_tml"));
	 					if(ctrlno!=-1 && ctrlno!=-2)
	 						cm[i].setVeUser(rs.getString("ch.ve_dt_time"));

	 					System.out.println("i=="+i);
	 					i++;
	 				}
	 			}
	 			else{
	 				cm=null;
	 				throw new RecordsNotFoundException();
	 			}
	 		}catch(SQLException sq){
	 			sq.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cm;
	 	}

	 	public String getGL(String acc_type,int gl_code)throws RemoteException
	 	{
	 	    ResultSet rs= null;
	 	    Connection conn= null;
	 	    String  rt= null;
	 	    try{
	 	        
	 	    conn=getConnection();
	 	    Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 		//select * from GLMaster where gl_type = '1012001' and gl_code = 104004 limit 1 
//	 	    rs = stat.executeQuery("select mr.gl_code,mr.gl_name from GLTransaction tr, GLMaster mr where   mr.gl_code="+gl_code+"  and tr.gl_code="+gl_code+" limit 1");
	 	   rs = stat.executeQuery("select * from GLMaster where gl_type = '"+acc_type+"' and gl_code = "+gl_code +" limit 1");
	 		 if(rs.next())
	 		     rt=rs.getString("gl_name");
	 		 else
	 		    return null;
	 	    }
	 	    catch(SQLException s)
	 	    {
	 	        s.printStackTrace();
	 	    }
	 	    finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		} 
	 	    return rt;
	 	    
	 	}
	 	
	 	
	 	public CompanyMaster[] retrieveChqDtls(int ctrlno,int n)throws RecordsNotFoundException
	 	{
	 		ResultSet rs=null;
	 		ResultSet rs2= null;
	 		Connection conn=null;
	 		CompanyMaster[] cm=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 		
	 			if(n==1)
	 			{
	 				
	 				
	 				ResultSet res1 =stmt.executeQuery("select  * from ChequeDetails  where ctrl_no ="+ctrlno);
	 				
	 				if(res1.last())
	 				{
	 					int numof_row1 = res1.getRow();
	 					System.out.println("yaaa.............."+numof_row1);
	 					if(numof_row1>0)
	 						cm=new CompanyMaster[numof_row1];
	 					else 
	 					{
	 						cm=null;
	 					}	
	 					res1.beforeFirst();
	 	 		
	 					int i=0;
	 					double total_amt = 0.0;

	 					while(res1.next()){
	 	 					
	 	 					if(res1.getString("cr_ac_type").startsWith("1002") ||res1.getString("cr_ac_type").startsWith("1007"))
	 	 						 rs2 = stmt1.executeQuery("select concat(ifnull(cm.fname,''),' ',ifnull(cm.mname,''),' ',ifnull(cm.lname,''))as name from AccountMaster am,CustomerMaster cm where am.ac_type='"+res1.getString("cr_ac_type")+"' and am.ac_no = "+res1.getString("cr_ac_no")+" and am.cid = cm.cid");

	 	 			 			/* for OD/CC */
	 	 			 			 else if(res1.getString("cr_ac_type").startsWith("1014") ||res1.getString("cr_ac_type").startsWith("1015"))	
	 	 			 				 rs2 = stmt1.executeQuery("select concat(ifnull(cm.fname,''),' ',ifnull(cm.mname,''),' ',ifnull(cm.lname,''))as name from ODCCMaster am,CustomerMaster cm where am.ac_type='"+res1.getString("cr_ac_type")+"' and am.ac_no = "+res1.getString("cr_ac_no")+" and am.cid = cm.cid");
	 	 			 				 
	 	 			 		
	 	 			 			 /* for Share */
	 	 			 			 else if(res1.getString("cr_ac_type").startsWith("1001"))
	 	 			 				 rs2 = stmt1.executeQuery("select concat(ifnull(cm.fname,''),' ',ifnull(cm.mname,''),' ',ifnull(cm.lname,''))as name from ShareMaster am,CustomerMaster cm where am.ac_type='"+res1.getString("cr_ac_type")+"' and am.ac_no = "+res1.getString("cr_ac_no")+" and am.cid = cm.cid");
	 	 			 				 
	 	 			 			 /* for Deposits */
	 	 			 			 else if(res1.getString("cr_ac_type").startsWith("1003")|| res1.getString("cr_ac_type").startsWith("1004")|| res1.getString("cr_ac_type").startsWith("1005"))
	 	 			 				 rs2 = stmt1.executeQuery("select concat(ifnull(cm.fname,''),' ',ifnull(cm.mname,''),' ',ifnull(cm.lname,''))as name from DepositMaster am,CustomerMaster cm where am.ac_type='"+res1.getString("cr_ac_type")+"' and am.ac_no = "+res1.getString("cr_ac_no")+" and am.cid = cm.cid");
	 	 			 				 
	 	 					
	 	 					if(rs2.next())
	 	 					{
	 	 						cm[i]=new CompanyMaster();

	 	 						cm[i].setAccType(res1.getString("cr_ac_type"));
	 	 						cm[i].setAccNo(res1.getInt("cr_ac_no"));
	 	 						cm[i].setCompanyName(rs2.getString("name"));
	 	 						cm[i].setDeTml(res1.getString("cr_amt"));
	 	 						
	 	 						total_amt += Double.parseDouble(res1.getString("cr_amt"));
	 	 						
	 	 						cm[0].setDeUser(Double.toString(total_amt) );
	 	 						
	 	 						System.out.println("Tatal amount is------------------"+ total_amt);
	 	 						
	 	 						cm[i].setVeUser(res1.getString("ve_user"));
	 	 						
	 	 						if(res1.getString("loan_ac_type")!= null && res1.getInt("loan_ac_no")!=0)
	 	 						{
	 	 							cm[i].setLoanAccType(res1.getString("loan_ac_type"));
	 	 							cm[i].setLoanAccNo(res1.getInt("loan_ac_no"));
	 	 						    
	 	 						}
	 	 						i++;
	 	 					}
	 	 					
	 	 				}
	 				}
	 				else 
	 				{
	 				
	 				ResultSet rs1 = stmt.executeQuery("select ch.trn_amt, ch.ve_dt_time ,ch.ve_user,cm.* from Cheque ch, CompanyMaster cm where ch.ctrl_no = "+ ctrlno+" and cm.comp_name = ch.comp_name");
	 				
	 				rs1.last();
	 	 			int numof_row = rs1.getRow();
	 	 			System.out.println("yaaa.............."+numof_row);
	 	 			if(numof_row>0)
	 	 				cm=new CompanyMaster[numof_row];
	 	 			else 
	 	 				cm=null;
	 	 			
	 	 			rs1.beforeFirst();
	 	 		
	 	 		int i=0;
	 				
	 				while(rs1.next()){
	 					
	 					if(rs1.getString("ac_type").startsWith("1002") ||rs1.getString("ac_type").startsWith("1007"))
	 						 rs2 = stmt1.executeQuery("select concat(ifnull(cm.fname,''),' ',ifnull(cm.mname,''),' ',ifnull(cm.lname,''))as name from AccountMaster am,CustomerMaster cm where am.ac_type='"+rs1.getString("ac_type")+"' and am.ac_no = "+rs1.getString("ac_no")+" and am.cid = cm.cid");
	 						//rs=stmt.executeQuery("select ch.trn_amt,ch.ve_dt_time,ch.ve_user,cd.*,concat(ifnull(cm.fname,''),' ',ifnull(cm.mname,''),' ',ifnull(cm.lname,''))as name from Cheque ch,ChequeDetails cd,AccountMaster am,CustomerMaster cm where ch.ctrl_no="+ctrlno+" and cd.cr_ac_type='"+res.getString("cr_ac_type")+"' and cd.cr_ac_no="+res.getInt("cr_ac_no")+" and ch.mult_cr='T' and ch.ctrl_no=cd.ctrl_no and cd.cr_ac_type=am.ac_type and cd.cr_ac_no=am.ac_no and am.cid=cm.cid order by cd.cr_ac_type,cd.cr_ac_no");

	 			 			/* for OD/CC */
	 			 			 else if(rs1.getString("ac_type").startsWith("1014") ||rs1.getString("ac_type").startsWith("1015"))	
	 			 				 rs2 = stmt1.executeQuery("select concat(ifnull(cm.fname,''),' ',ifnull(cm.mname,''),' ',ifnull(cm.lname,''))as name from ODCCMaster am,CustomerMaster cm where am.ac_type='"+rs1.getString("ac_type")+"' and am.ac_no = "+rs1.getString("ac_no")+" and am.cid = cm.cid");
	 			 				 
	 			 				 //rs=stmt.executeQuery("select ch.trn_amt,ch.ve_dt_time,ch.ve_user,cd.*,concat(ifnull(cm.fname,''),' ',ifnull(cm.mname,''),' ',ifnull(cm.lname,''))as name from Cheque ch,ChequeDetails cd,ODCCMaster am,CustomerMaster cm where ch.ctrl_no="+ctrlno+" and ch.mult_cr='T' and ch.ctrl_no=cd.ctrl_no and cd.cr_ac_type=am.ac_type and cd.cr_ac_no=am.ac_no and cd.cr_ac_type='"+res.getString("cr_ac_type")+"' and cd.cr_ac_no="+res.getInt("cr_ac_no")+" and am.cid=cm.cid order by cd.cr_ac_type,cd.cr_ac_no");
	 			 		
	 			 			 /* for Share */
	 			 			 else if(rs1.getString("ac_type").startsWith("1001"))
	 			 				 rs2 = stmt1.executeQuery("select concat(ifnull(cm.fname,''),' ',ifnull(cm.mname,''),' ',ifnull(cm.lname,''))as name from ShareMaster am,CustomerMaster cm where am.ac_type='"+rs1.getString("ac_type")+"' and am.ac_no = "+rs1.getString("ac_no")+" and am.cid = cm.cid");
	 			 				 
	 			 				 //rs=stmt.executeQuery("select ch.trn_amt,ch.ve_dt_time,ch.ve_user,cd.*,concat(ifnull(cm.fname,''),' ',ifnull(cm.mname,''),' ',ifnull(cm.lname,''))as name from Cheque ch,ChequeDetails cd,ShareMaster am,CustomerMaster cm where ch.ctrl_no="+ctrlno+" and ch.mult_cr='T' and ch.ctrl_no=cd.ctrl_no and cd.cr_ac_type=am.ac_type and cd.cr_ac_no=am.ac_no and cd.cr_ac_type='"+res.getString("cr_ac_type")+"' and cd.cr_ac_no="+res.getInt("cr_ac_no")+" and am.cid=cm.cid order by cd.cr_ac_type,cd.cr_ac_no");
	 			 			 /* for Deposits */
	 			 			 else if(rs1.getString("ac_type").startsWith("1003")|| rs1.getString("ac_type").startsWith("1004")|| rs1.getString("ac_type").startsWith("1005"))
	 			 				 rs2 = stmt1.executeQuery("select concat(ifnull(cm.fname,''),' ',ifnull(cm.mname,''),' ',ifnull(cm.lname,''))as name from DepositMaster am,CustomerMaster cm where am.ac_type='"+rs1.getString("ac_type")+"' and am.ac_no = "+rs1.getString("ac_no")+" and am.cid = cm.cid");
	 			 				 
	 			 				 //rs=stmt.executeQuery("select ch.trn_amt,ch.ve_dt_time,ch.ve_user,cd.*,concat(ifnull(cm.fname,''),' ',ifnull(cm.mname,''),' ',ifnull(cm.lname,''))as name from Cheque ch,ChequeDetails cd,DepositMaster am,CustomerMaster cm where ch.ctrl_no="+ctrlno+" and ch.mult_cr='T' and ch.ctrl_no=cd.ctrl_no and cd.cr_ac_type=am.ac_type and cd.cr_ac_no=am.ac_no and cd.cr_ac_type='"+res.getString("cr_ac_type")+"' and cd.cr_ac_no="+res.getInt("cr_ac_no")+" and am.cid=cm.cid order by cd.cr_ac_type,cd.cr_ac_no");
	 					
	 					if(rs2.next())
	 					{
	 						cm[i]=new CompanyMaster();

	 						cm[i].setAccType(rs1.getString("ac_type"));
	 						cm[i].setAccNo(rs1.getInt("ac_no"));
	 						cm[i].setCompanyName(rs2.getString("name"));
	 						cm[i].setDeTml("0");
	 						cm[i].setDeUser(String.valueOf(rs1.getDouble("trn_amt")));
	 						
	 						
	 						cm[i].setVeUser(null);
	 						
	 						cm[i].setLoanAccType("0");
	 						cm[i].setLoanAccNo(0);
	 						    
	 						
	 						
	 					
	 						i++;
	 					}
	 					
	 				}
	 			}
	 				
	 			}
	 			
	 			
	 		}catch(SQLException s){
	 			s.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}catch(NullPointerException m){
	 			m.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cm;
	 	}

	 	/*public ChequeDepositionObject[] getUnidentifiedCheques()throws RemoteException
	 	{
	 		ChequeDepositionObject[] cd=null;
	 		try
	 		{
	 			ResultSet rs=stmt.executeQuery("select * from Cheque where clg_type='O' and clg_date is null and clg_no is null and send_to is null and ve_tml is not null order by ctrl_no");
	 			rs.last();
	 			cd=new ChequeDepositionObject[rs.getRow()];
	 			rs.beforeFirst();
	 			int i=0;
	 			while(rs.next())
	 			{
	 				cd[i]=new ChequeDepositionObject();
	 				cd[i].setControlNo(rs.getLong("ctrl_no"));
	 				cd[i].setQdpNo(rs.getInt("qdp_no"));
	 				cd[i].setQdpDate(rs.getString("qdp_dt"));
	 				cd[i].setCreditACType(rs.getString("cr_ac_type"));
	 				cd[i].setCreditACNo(rs.getInt("cr_ac_no"));
	 				cd[i].setTranAmt(rs.getDouble("trn_amt"));
	 				i++;
	 			}
	 			return cd;

	 		}catch(SQLException sql){System.out.println(sql);}
	 		return cd;
	 	}*/
	 	
	 	public ChequeDepositionObject[] getUnidentifiedCheques(int dest)throws RecordsNotFoundException
	 	{
	 		ChequeDepositionObject[] cd=null;
	 		Connection conn=null;
	 		ResultSet rs;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			if(dest%1111 == 0)
	 				rs = stmt.executeQuery("select * from Cheque where clg_type in ('O','C','D') and clg_date is null and send_to is null and ve_tml is not null and desp_ind = 'F' and post_ind = 'F' and doc_dest = "+ dest +" order by ctrl_no");
	 			else
	 				//rs=stmt.executeQuery("select Cheque.* from Cheque,Head where doc_dest = "+dest+" and doc_sou != bankcode and clg_date is null and clg_no is null and send_to is null and ve_tml is not null order by ctrl_no");
	 				rs=stmt.executeQuery("select Cheque.* from Cheque,Head where doc_dest = "+dest+" and doc_dest != bankcode and clg_date is null and clg_no is null and send_to is null and ve_tml is not null and desp_ind = 'F' and post_ind = 'F' order by ctrl_no");
	 			rs.last();
	 			if(rs.getRow()==0)
	 			{
	 				cd=null;
	 				return cd;
	 			}
	 			cd=new ChequeDepositionObject[rs.getRow()];
	 			rs.beforeFirst();
	 			int i=0;
	 			while(rs.next())
	 			{
	 				cd[i]=new ChequeDepositionObject();
	 				cd[i].setControlNo(rs.getLong("ctrl_no"));
	 				cd[i].setQdpNo(rs.getInt("qdp_no"));
	 				cd[i].setQdpDate(rs.getString("qdp_dt"));
	 				cd[i].setDeTml(rs.getString("doc_bs"));
	 				cd[i].setCreditACType(rs.getString("cr_ac_type"));
	 				cd[i].setCreditACNo(rs.getInt("cr_ac_no"));
	 				cd[i].setBankCode(rs.getString("bank_cd"));
	 				cd[i].setClgType(rs.getString("clg_type"));
	 				cd[i].setDocSource(rs.getInt("doc_sou"));
	 				cd[i].setDocDestination(rs.getInt("doc_dest"));
	 				
	 				System.out.println("server is"+rs.getInt("bank_cd"));
	 				
	 				if(rs.getString("doc_bs").equals("S"))
	 					cd[i].setTranAmt(rs.getDouble("trn_amt"));
	 				else	
	 					cd[i].setTranAmt(rs.getDouble("doc_tot"));
	 					
	 				cd[i].setDeUser(rs.getString("ret_norm"));
	 				cd[i].setMultiCredit(rs.getString("mult_cr"));
	 				i++;
	 			}
	 			//return cd;
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cd;
	 	}


	 	/*public int identifyCheques(long[] ctrlno,String clgdate,int clgno,int sendto)throws RemoteException
	 	{
	 		int identify=0;
	 		try
	 		{
	 			PreparedStatement pstmt=conn.prepareStatement("Update Cheque set clg_date='"+clgdate+"',clg_no="+clgno+",send_to="+sendto+" where ctrl_no=?");

	 			for(int i=0;i<ctrlno.length;i++)
	 			{
	 				pstmt.setLong(1,ctrlno[i]);
	 				identify+=(pstmt.executeUpdate());
	 			}
	 			return identify;

	 		}catch(Exception sql){System.out.println(sql);identify=-1;}
	 		return identify;
	 	}*/
	 	
	 	public int identifyCheques(long[] ctrlno,String clgdate,int clgno,int sendto,int delete)throws RecordNotInsertedException
	 	{
	 		int identify=0;
	 		Connection conn=null;
	 		try
	 		{
	 			conn=getConnection();
	 			if(delete ==1){
	 				PreparedStatement pstmt=conn.prepareStatement("Update Cheque set clg_date='"+clgdate+"',clg_no="+clgno+",send_to="+sendto+",doc_dest="+sendto+",exp_clgdt='"+clgdate+"',exp_clgno="+clgno+" where ctrl_no=?");
	 				for(int i=0;i<ctrlno.length;i++)
	 				{
	 					pstmt.setLong(1,ctrlno[i]);
	 					identify+=(pstmt.executeUpdate());
	 				}
	 			}
	 			else if(delete == -1){
	 				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 				for(int i=0;i<ctrlno.length;i++)
	 					stmt.addBatch("Update Cheque set clg_date=NULL,clg_no=NULL,send_to=NULL where ctrl_no="+ctrlno[i]);
	 				stmt.executeBatch();
	 				identify=1;
	 			}
	 			//return identify;
	 		}catch(Exception sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException(); 
	 		
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return identify;
	 	}
	 	
	 	public int ackEntry(int source,String str,double amount,String tml,String user,int ackno)throws RecordNotInsertedException
	 	{
	 		Connection conn=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			if(ackno == 0)
	 			{
	 				
	 				ResultSet rs=stmt.executeQuery("select lst_voucher_scroll_no+1 from Modules where modulecode=1011001");
	 				
	 				if(rs.next());
	 				ackno=rs.getInt(1);
	 				System.out.println("ack num=+++++++++++++=="+rs.getInt(1));
	 				stmt.executeUpdate("insert into Acknowledge values("+ackno+",'"+getToday()+"','"+source+"','"+str+"','"+amount+"','F','"+tml+"','"+user+"','"+getToday()+"',null,null,null)");
	 				stmt.executeUpdate("update Modules set lst_voucher_scroll_no=lst_voucher_scroll_no+1 where modulecode=1011001");
	 				System.out.println("aakk  num inside bean");
	 				return ackno;
	 				
	 			}
	 			stmt.executeUpdate("update Acknowledge set ack_date= '"+getToday()+"',doc_sou='"+source+"',clg_type = '"+ str+"',tot_amt='"+amount+"',de_tml='"+tml+"',de_user='"+user+"',de_date= '"+getToday()+"' where ack_no="+ackno);
	 			System.out.println("after return");
	 			return ackno;
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}catch(Exception e ){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return -1;
	 	}
	 	
	 	public int verifyAck(int ackno,int docsou,double amount,String tml,String user,String ve_date)throws RecordNotInsertedException
	 	{
	 		Connection conn=null;
	 		ResultSet ret =null;
	 		boolean boolean_flag=true;
	 		try
	 		{
	 			conn=getConnection();
	 			comm = commonHome.create();
	 			Statement stupdate = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			System.out.println("0000000000000000000");
	 	//		
	 			ResultSet rr=null,ret1=null;
	 			ResultSet rs1 = stmt1.executeQuery("select  ch.to_bounce,ch.doc_tot, ch.cr_ac_type,ch.cr_ac_no,ch.clg_type,ch.dr_ac_type,ch.dr_ac_no,ch.prev_ctrl_no,ch.de_tml,ch.de_user,ch.de_dt_time,ac.ack_no  from Cheque ch,Acknowledge  ac where ac.ve_user is  null and ac.ack_no=ch.ack_no and ch.ack_no="+ackno+" and ch.ve_tml is null ");
	 			
	 			int update =0;
	 			if(rs1.next())
	 			{
	 				String to_bounce=rs1.getString("to_bounce");
	 				String clg_type= rs1.getString("clg_type");
	 				String acc_type=null;
	 				int acc_no=0;
	 				int trn_seq=0,custtype=0,cid=0,cat_type=0;
	 				String de_date=rs1.getString("de_dt_time");
	 				String de_user=rs1.getString("de_user");
	 				String de_tml=rs1.getString("de_tml");


	 				if( docsou%1111 == 0 )
	 				{

	 					ResultSet result= stmt.executeQuery("select * from Cheque  where ack_no ="+ackno+" and ve_tml is null");

	 					if(result.next())
	 					{
	 						System.out.println(" sending Bank"+docsou);

	 						ret = stmt.executeQuery("select br_ac_type,br_ac_no from BranchMaster where br_code ="+docsou);

	 						if(ret.next())
	 						{
	 							acc_type=ret.getString("br_ac_type");
	 							acc_no=ret.getInt("br_ac_no");

	 						}
	 						if( !acc_type.startsWith("1012") )
	 						{
	 							boolean_flag=false;
	 							AccountTransObject am=new AccountTransObject();	
	 							am.setAccType(acc_type);
	 							System.out.println("acc type is"+ acc_type);
	 							am.setAccNo(acc_no);
	 							am.setTransType("R");
	 							am.setTransAmount(amount);
	 							am.setTransMode("G");
	 							am.setTransDate(getToday());
	 							am.setTransSource(de_tml);
	 							am.setCdInd("C");
	 							am.setChqDDNo(result.getInt("qdp_no"));
	 							am.setChqDDDate(result.getString("qdp_dt"));
	 							am.setTransNarr("Ack No "+ackno);
	 							//am.setRef_No("");
	 							am.setPayeeName("");
	 							am.setCloseBal(amount);
	 							am.setLedgerPage(0);
	 							am.uv.setUserTml(de_tml);
	 							am.uv.setUserDate(de_date);
	 							am.uv.setUserId(de_user);

	 							am.uv.setVerTml(tml);
	 							am.uv.setVerId(user);
	 							am.uv.setVerDate(ve_date);

	 							comm.storeAccountTransaction(am);
	 							rs1 = stmt1.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+acc_no+" and am.ac_type='"+acc_type+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 							if(rs1.next())
	 							{
	 								if(ret.next())
	 								{
	 									trn_seq=ret.getInt(2);
	 									cid	=ret.getInt("cid");
	 									System.out.println("cid is"+ cid);
	 								}
	 								rr= stupdate.executeQuery("select custtype from CustomerMaster where cid="+ cid );
	 								if(	rr.next())
	 								{
	 									System.out.println("custtype is"+ rr.getInt("custtype"));	
	 									custtype=rr.getInt("custtype");
	 									if(custtype==0)
	 										cat_type=1;
	 									else
	 										cat_type=2;
	 								}
	 							}

	 							ret1= stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+acc_type+" and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");


	 						}

	 						if(boolean_flag)
	 						{

	 							System.out.println("111111111111111");
	 							GLTransObject trnobj=new GLTransObject();
	 							trnobj.setTrnDate(getToday());

	 							trnobj.setGLType(acc_type);
	 							trnobj.setGLCode(String.valueOf(acc_no));


	 							trnobj.setTrnMode("G");
	 							trnobj.setAmount(amount);

	 							if (clg_type.equalsIgnoreCase("C"))
	 								trnobj.setCdind("D");
	 							else
	 								trnobj.setCdind("C");

	 							trnobj.setAccType(acc_type);
	 							trnobj.setAccNo(String.valueOf(acc_no));
	 							trnobj.setTrnType("R");
	 							trnobj.setRefNo(ackno);
	 							trnobj.setTrnSeq(trn_seq);
	 							trnobj.setVtml(tml);
	 							trnobj.setVid(user);
	 							trnobj.setVDate(ve_date);

	 							System.out.println(34);

	 							comm.storeGLTransaction(trnobj);System.out.println(35);

	 							System.out.println("22222222222222222");



	 						}

	 						else
	 						{
	 							if(ret1.next())
	 							{
	 								System.out.println("111111111111111");
	 								GLTransObject trnobj=new GLTransObject();
	 								trnobj.setTrnDate(getToday());

	 								trnobj.setGLType(ret1.getString("gl_type"));
	 								trnobj.setGLCode(ret1.getString("gl_code"));

	 								trnobj.setTrnMode("G");
	 								trnobj.setAmount(amount);
	 								trnobj.setCdind("C");
	 								trnobj.setAccType(acc_type);
	 								trnobj.setAccNo(String.valueOf(acc_no));
	 								trnobj.setTrnType("R");
	 								trnobj.setRefNo(ackno);
	 								trnobj.setTrnSeq(trn_seq);
	 								trnobj.setVtml(tml);
	 								trnobj.setVid(user);
	 								trnobj.setVDate(ve_date);

	 								System.out.println(34);

	 								comm.storeGLTransaction(trnobj);System.out.println(35);

	 								System.out.println("22222222222222222");

	 							}

	 						}
	 					} 				

	 					// IW Clg Apex Bank	
	 					if ( !clg_type.equalsIgnoreCase("C"))
	 					{
	 						ret1 = stmt1.executeQuery("select * from GLKeyParam where code=1 and  ac_type ='1011001' ");
	 						if(ret1.next())
	 						{
	 							System.out.println("33333333333");
	 							GLTransObject trnobj=new GLTransObject();
	 							trnobj.setTrnDate(getToday());
	 							trnobj.setGLType(ret1.getString("gl_type"));
	 							trnobj.setGLCode(ret1.getString("gl_code"));
	 							trnobj.setTrnMode("G");
	 							trnobj.setAmount(amount);
	 							trnobj.setCdind("D");
	 							trnobj.setAccType(acc_type);
	 							trnobj.setAccNo(String.valueOf(acc_no));
	 							trnobj.setTrnType("");
	 							trnobj.setRefNo(ackno);
	 							trnobj.setTrnSeq(0);
	 							trnobj.setVtml(tml);
	 							trnobj.setVDate(ve_date);
	 							trnobj.setVid(user);System.out.println(34);

	 							comm.storeGLTransaction(trnobj);System.out.println(35);
	 							System.out.println("444444444444444");

	 						}
	 					}else {   

	 						// for Credit ECS Debit Entry 

	 						ret1 = stmt1.executeQuery("select * from GLKeyParam where code=9 and  ac_type ='1011001' ");
	 						if(ret1.next())
	 						{
	 							System.out.println("33333333333");
	 							GLTransObject trnobj=new GLTransObject();
	 							trnobj.setTrnDate(getToday());
	 							trnobj.setGLType(ret1.getString("gl_type"));
	 							trnobj.setGLCode(ret1.getString("gl_code"));
	 							trnobj.setTrnMode("G");
	 							trnobj.setAmount(amount);
	 							trnobj.setCdind("C");
	 							trnobj.setAccType(acc_type);
	 							trnobj.setAccNo(String.valueOf(acc_no));
	 							trnobj.setTrnType("");
	 							trnobj.setRefNo(ackno);
	 							trnobj.setTrnSeq(0);
	 							trnobj.setVtml(tml);
	 							trnobj.setVDate(ve_date);
	 							trnobj.setVid(user);System.out.println(34);

	 							comm.storeGLTransaction(trnobj);System.out.println(35);
	 							System.out.println("444444444444444");

	 						}

	 					}

	 				}
	 				else 
	 				{
	 					//Cr. CLRG_OWBR
	 					ret = stmt.executeQuery("select ac_type,gl_type,gl_code from GLKeyParam where ac_type=1011001 and code =11");
	 					if(ret.next())
	 					{
	 						System.out.println("0655506506555");
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(getToday());
	 						trnobj.setGLType(ret.getString("gl_type"));
	 						trnobj.setGLCode(ret.getString("gl_code"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(amount);
	 						
	 						if (clg_type.equalsIgnoreCase("C"))
	 							trnobj.setCdind("D");
	 						else
	 							trnobj.setCdind("C");
	 						
	 						
	 						if(rs1.getInt("prev_ctrl_no")>0)
	 						{
	 							trnobj.setAccType(rs1.getString("dr_ac_type"));
	 							trnobj.setAccNo(rs1.getString("dr_ac_no"));
	 						}
	 						else
	 						{
	 							trnobj.setAccType(rs1.getString("cr_ac_type"));
	 							trnobj.setAccNo(rs1.getString("cr_ac_no"));
	 						}

	 						trnobj.setTrnType("");
	 						trnobj.setRefNo(ackno);
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(tml);
	 						trnobj.setVid(user);
	 						trnobj.setVDate(ve_date);
	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);System.out.println(35);
	 						System.out.println("6666666666666666666");
	 					}
	 					//Dr. CLRG_INB	

	 					ResultSet ret2 = stmt.executeQuery("select ac_type,gl_type,gl_code from GLKeyParam where ac_type=1011001 and code =2");
	 					if(ret2.next())
	 					{
	 						System.out.println("7777777777777777777");
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(getToday());
	 						trnobj.setGLType(ret2.getString("gl_type"));
	 						trnobj.setGLCode(ret2.getString("gl_code"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(amount);
	 						
	 						if (clg_type.equalsIgnoreCase("C"))
	 							trnobj.setCdind("C");
	 						else
	 							trnobj.setCdind("D");
	 						
	 						
	 						if(rs1.getInt("prev_ctrl_no")>0)
	 						{
	 							trnobj.setAccType(rs1.getString("dr_ac_type"));
	 							trnobj.setAccNo(rs1.getString("dr_ac_no"));
	 						}
	 						else
	 						{
	 							trnobj.setAccType(rs1.getString("cr_ac_type"));
	 							trnobj.setAccNo(rs1.getString("cr_ac_no"));
	 						}
	 						trnobj.setTrnType("");
	 						trnobj.setRefNo(ackno);
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(tml);
	 						trnobj.setVDate(ve_date);
	 						trnobj.setVid(user);System.out.println(34);

	 						comm.storeGLTransaction(trnobj);System.out.println(35);
	 						System.out.println("888888888888888888");
	 					}
	 				}
	 				//ResultSet rs2 = stmt.executeQuery("select ");

	 				update=stmt.executeUpdate("update Acknowledge set ve_tml='"+tml+"',ve_user='"+user+"',ve_date='"+ve_date+"' where ack_no="+ackno+" and ve_tml is null");

	 				System.out.println("99999999999999999");
	 				//return stmt.executeBatch()[1];
	 			}
	 			return update ;
	 		
	 			}
	 		
	 		catch(Exception sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		//return -1;
	 	}
	 	
	 	
	 	public long verifyDebitECS(ChequeDepositionObject cd)  throws RecordNotInsertedException{
	 		
	 		long ctrlno = 0;
	 		Connection conn = null;
	 		Statement stmt = null;
	 		Statement stmt1 = null;
	 		Statement stmt2 = null;
	 		Statement stupdate = null;
	 		ResultSet ret = null;
	 		PreparedStatement pstmt=null;
	 		int trn_seq = 0,cat_type= 0;
	 		
	 		
	 		try{
	 			
	 			comm=commonHome.create();
	 			conn = getConnection();
	 			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			stmt2 = conn.createStatement();
	 			stupdate = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			// Credit Entry for i/w clg apex bank
	 			
	 				ret = stmt.executeQuery("select * from GLKeyParam where code=1 and  ac_type ='1011001' ");
					
	 				if(ret.next())
					{
						System.out.println("33333333333");
						GLTransObject trnobj=new GLTransObject();
					
						trnobj.setTrnDate(getToday());
						trnobj.setGLType(ret.getString("gl_type"));
						trnobj.setGLCode(ret.getString("gl_code"));
						trnobj.setTrnMode("G");
						trnobj.setAmount(cd.getTranAmt());
						trnobj.setCdind("C");
						trnobj.setAccType(cd.getDebitACType());
						trnobj.setAccNo(Integer.toString(cd.getDebitACNo()));
						trnobj.setTrnType("R");
						trnobj.setRefNo(cd.getAckNo());
						trnobj.setTrnSeq(0);
						trnobj.setVtml(cd.getVeTml());
						trnobj.setVDate(cd.getVe_date());
						trnobj.setVid(cd.getVeUser());System.out.println(34);

						comm.storeGLTransaction(trnobj);
						System.out.println(35);
						System.out.println("444444444444444");

					}
	 			
				if ( cd.getBounceInd().equalsIgnoreCase("F") ){
					
					if ( cd.getDebitACType().startsWith("1002") || cd.getDebitACType().startsWith("1007") || cd.getDebitACType().startsWith("1014") || cd.getDebitACType().startsWith("1015") || cd.getDebitACType().startsWith("1018"))
					{
						
					     
							System.out.println("out is 2nd 7");
							//delete old row for shadow balance..

							// credit entry...
							AccountTransObject am = new AccountTransObject();
							am.setAccType(cd.getDebitACType());
							am.setAccNo(cd.getDebitACNo());
							am.setTransType("R");
							am.setTransAmount(cd.getTranAmt());
							am.setTransMode("G");
							am.setTransSource(cd.getVeTml());
							am.setCdInd("D");
							am.setTransDate(getToday());
							am.setChqDDNo(cd.getQdpNo());
							am.setChqDDDate(cd.getQdpDate());
							am.setTransNarr("Ctrl No "+cd.getControlNo());
							am.setRef_No(Integer.parseInt(String.valueOf(cd.getControlNo())));
							am.setPayeeName(cd.getPayeeName());
							am.setCloseBal(cd.getTranAmt());
							am.setLedgerPage(0);
							am.uv.setUserTml(cd.getVeTml());
							am.uv.setUserId(cd.getVeUser());
							am.uv.setUserDate(cd.getVe_date());
							am.uv.setVerTml(cd.getVeTml());
							am.uv.setVerId(cd.getVeUser());
							am.uv.setVerDate(cd.getVe_datetime());

							comm.storeAccountTransaction(am);
							
							ResultSet rs_ret = null;
							// GL goes here..for credit entry
							System.out.println(" +++++++++++++++ ---------" + cd.getDebitACType());
							if(cd.getDebitACType().startsWith("1014") || cd.getDebitACType().startsWith("1015")){
								System.out.println("inside the adcc GLTrn");
								rs_ret = stupdate.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
							}
							else
								rs_ret = stupdate.executeQuery("select am.ac_type,trn_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
							
							if(rs_ret.next())
							{
								
								trn_seq=rs_ret.getInt(2);
																		
							if(rs_ret.getInt(1)==0)
								cat_type=1;
							else
								cat_type=2;
							}
										
								ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
								if(ret.next())
								{
									GLTransObject trnobj=new GLTransObject();
									trnobj.setTrnDate(getToday());
									trnobj.setGLType(ret.getString(3));
									trnobj.setGLCode(ret.getString(1));
									trnobj.setTrnMode("G");
									trnobj.setAmount((cd.getTranAmt())*ret.getInt(2));//-res.getDouble("ch.disc_amt")
									trnobj.setCdind("D");
									trnobj.setAccType(cd.getDebitACType());
									trnobj.setAccNo(Integer.toString(cd.getDebitACNo()));
									trnobj.setTrnType("R");
									trnobj.setRefNo((int)(cd.getControlNo()));
									trnobj.setTrnSeq(trn_seq);
									trnobj.setVtml(cd.getVeTml());
									trnobj.setVid(cd.getVeUser());
									trnobj.setVDate(cd.getVe_datetime());
									System.out.println("chek 7777777777777 ");

									comm.storeGLTransaction(trnobj);
									System.out.println("chek 88888888888888 ");
								}
						
					}
					
				} else {
					
					// for bounced ECS
					ret = stmt.executeQuery("select * from GLKeyParam where code=12 and  ac_type ='1011001' ");
					if(ret.next())
					{
						System.out.println("33333333333");
						GLTransObject trnobj=new GLTransObject();
					
						trnobj.setTrnDate(getToday());
						trnobj.setGLType(ret.getString("gl_type"));
						trnobj.setGLCode(ret.getString("gl_code"));
						trnobj.setTrnMode("G");
						trnobj.setAmount(cd.getTranAmt());
						trnobj.setCdind("D");
						trnobj.setAccType(cd.getDebitACType());
						trnobj.setAccNo(Integer.toString(cd.getDebitACNo()));
						trnobj.setTrnType("R");
						trnobj.setRefNo(cd.getAckNo());
						trnobj.setTrnSeq(0);
						trnobj.setVtml(cd.getVeTml());
						trnobj.setVDate(cd.getVe_date());
						trnobj.setVid(cd.getVeUser());System.out.println(34);

						comm.storeGLTransaction(trnobj);
						System.out.println(35);
						System.out.println("444444444444444");
						
					}	
					
					
					
					
					
					if ( cd.getFineAmt() > 0){
					
						 AccountTransObject am = new AccountTransObject();
							 
							 am.setAccType(cd.getDebitACType());
							 am.setAccNo(cd.getDebitACNo());
						
							 am.setTransType("P");
							 am.setTransAmount( cd.getFineAmt() );
							 am.setTransMode("T");
							 am.setTransSource(cd.getVeTml());
							 am.setTransDate(getToday());
							 
							 am.setCdInd("D");
							 am.setChqDDNo(cd.getQdpNo());
							 am.setChqDDDate(cd.getQdpDate());
							 am.setTransNarr("Ctrl No "+cd.getControlNo());
							 am.setRef_No(Integer.parseInt(String.valueOf(cd.getControlNo())));
							 am.setPayeeName(cd.getPayeeName());
							 am.setCloseBal(cd.getFineAmt());
							 am.setLedgerPage(0);
							 am.uv.setUserTml(cd.getDeTml());
							 am.uv.setUserId(cd.getDeUser());
							 am.uv.setUserDate(cd.getVe_date());
							 am.uv.setVerTml(cd.getVeTml());
							 am.uv.setVerId(cd.getVeUser());
							 am.uv.setVerDate(cd.getVe_datetime());
			
							 comm.storeAccountTransaction(am);
							 
							 ResultSet gl = stmt2.executeQuery("select * from GLKeyParam where ac_type='1011001' and code=7");
							 if(gl.next())
							 {
								 System.out.println (1111);
								 
								 GLTransObject trnobj=new GLTransObject();
								 trnobj.setTrnDate(getToday());
								 System.out.println (22);
								 trnobj.setGLType(gl.getString("gl_type"));
								 trnobj.setGLCode(gl.getString("gl_code"));
								 System.out.println (33);
								 trnobj.setTrnMode("G");
								 trnobj.setAmount(cd.getFineAmt());
								 System.out.println (44);
								 trnobj.setCdind("C");
								 trnobj.setAccType(cd.getDebitACType());
								 trnobj.setAccNo(Integer.toString(cd.getDebitACNo()));
								 trnobj.setTrnType("");
								 trnobj.setRefNo(0);
								 trnobj.setTrnSeq(0);
								 trnobj.setVtml(cd.getVeTml());
								 trnobj.setVid(cd.getVeUser());
								 trnobj.setVDate(cd.getVe_datetime());
								 comm.storeGLTransaction(trnobj);
								 System.out.println (66);
							 }
						
						
					}
						
								
					
					// creating new Control No 
					{
						
		 				
		 				ResultSet rs=stmt.executeQuery("select modulecode,lst_acc_no from Modules where modulecode='1011001'");
		 				rs.next();
		 				ctrlno=rs.getInt(2)+1;
		 				rs.updateLong("lst_acc_no",ctrlno);
		 				rs.updateRow();
		 				rs.close();
		 				
		 				pstmt=conn.prepareStatement("insert into Cheque values(null,null,null,?,'G',?,?,?,?,'S',1,0.0,?,?,?,?,?,'R',?,null,?,?,?,?,?,?,?,?,?,?,'F','F',?,?,?,null,null,null,0.00,0.00,?,?,?,?,?,?,?,?)");
		 				
		 					
		 				System.out.println (13);
		 				
		 				pstmt.setLong(1,ctrlno);
		 				pstmt.setString(2,cd.getClgType());
		 				pstmt.setInt(3,cd.getAckNo());
		 				pstmt.setInt(4,cd.getDocDestination());
		 				pstmt.setInt(5,cd.getDocSource());
		 				pstmt.setString(6,cd.getMultiCredit());
		 				pstmt.setString(7,cd.getCompanyName());
		 				pstmt.setString(8,cd.getChqDDPO());
		 				pstmt.setInt(9,cd.getQdpNo());
		 				pstmt.setString(10,cd.getQdpDate());

		 				pstmt.setLong(11,cd.getControlNo());
		 				pstmt.setString(12,cd.getDebitACType());
		 				pstmt.setInt(13,cd.getDebitACNo());
		 								
		 				pstmt.setDouble(14,cd.getPOCommission());
		 				pstmt.setString(15,cd.getCreditACType());
		 				pstmt.setInt(16,cd.getCreditACNo());
		 				pstmt.setString(17,cd.getPayeeName());

		 				pstmt.setString(18,cd.getBankCode());
		 				pstmt.setString(19,cd.getBranchName());
		 				pstmt.setDouble(20,cd.getTranAmt());

		 				pstmt.setString(21,"F");
		 				pstmt.setString(22,cd.getDiscInd());
		 				pstmt.setDouble(23,cd.getDiscAmt());
		 				pstmt.setDouble(24,cd.getDiscChg());

		 				pstmt.setString(25,cd.getLoanAcc());
		 				pstmt.setInt(26,cd.getLoanAcc_No());
		 				pstmt.setString(27,cd.getDeTml());
		 				pstmt.setString(28,cd.getDeUser());
		 				pstmt.setString(29,cd.getDeTime());
		 				pstmt.setString(30,cd.getVeTml());
		 				pstmt.setString(31,cd.getVeUser());
		 				pstmt.setString(32,cd.getVe_datetime());
		 				
		 				
		 				if(pstmt.executeUpdate()==0){
		 					sessionContext.setRollbackOnly();
		 					throw new SQLException();
		 				}
		 								
		 				
					}
					
					
				}
	 			
	 			int r = stmt.executeUpdate("update Cheque set  clg_date = '"+ getToday()+ "',  clg_no = 1,  send_to = "+ cd.getDocSource()+" ,desp_ind = 'T', post_ind= 'T', ve_tml = '"+ cd.getVeTml()+"', ve_user = '"+ cd.getVeUser()+ "', ve_dt_time = '"+ cd.getVe_datetime()+"' where ctrl_no = " + cd.getControlNo());
				
	 			if ( r == 0){
	 				sessionContext.setRollbackOnly();
	 				throw new RecordNotInsertedException();
	 			}
	 		
	 		} catch ( CreateException cre ){
	 			
	 			cre.printStackTrace();
	 		}
	 		catch (SQLException sql){
	 			
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		} catch( RecordNotInsertedException re){
	 			
	 			re.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw re;
	 		}
	 		finally {
	 			
	 			try{
	 				
	 				conn.close();
	 			}catch ( SQLException sqlexc ){
	 				
	 			}
	 		}
	 		
	 		
	 		
	 		
	 		return ctrlno;
	 	}
	 	
	 	
	 	public long verifyCreditECS(ChequeDepositionObject cd)  throws RecordNotInsertedException{
	 		
	 		long ctrlno = 0;
	 		
	 		Connection conn = null;
	 		Statement stmt = null;
	 		Statement stmt1 = null;
	 		Statement stmt2 = null;
	 		Statement stupdate = null;
	 		ResultSet ret = null;
	 		PreparedStatement pstmt=null;
	 		int trn_seq = 0,cat_type= 0;
	 		
	 		
	 		try{
	 			
	 			comm=commonHome.create();
	 			conn = getConnection();
	 			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			stmt2 = conn.createStatement();
	 			stupdate = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			// debit Entry 
	 			
	 				ret = stmt.executeQuery("select * from GLKeyParam where code=9 and  ac_type ='1011001' ");
					if(ret.next())
					{
						System.out.println("33333333333");
						GLTransObject trnobj=new GLTransObject();
					
						trnobj.setTrnDate(getToday());
						trnobj.setGLType(ret.getString("gl_type"));
						trnobj.setGLCode(ret.getString("gl_code"));
						trnobj.setTrnMode("G");
						trnobj.setAmount(cd.getTranAmt());
						trnobj.setCdind("D");
						trnobj.setAccType(cd.getCreditACType());
						trnobj.setAccNo(Integer.toString(cd.getCreditACNo()));
						trnobj.setTrnType("R");
						trnobj.setRefNo(cd.getAckNo());
						trnobj.setTrnSeq(0);
						trnobj.setVtml(cd.getVeTml());
						trnobj.setVDate(cd.getVe_date());
						trnobj.setVid(cd.getVeUser());System.out.println(34);

						comm.storeGLTransaction(trnobj);
						System.out.println(35);
						System.out.println("444444444444444");

					}
	 			
				if ( cd.getBounceInd().equalsIgnoreCase("F") ){
					
					if ( cd.getCreditACType().startsWith("1002") || cd.getCreditACType().startsWith("1007") || cd.getCreditACType().startsWith("1014") || cd.getCreditACType().startsWith("1015") || cd.getCreditACType().startsWith("1018"))
					{
						
					     
							System.out.println("out is 2nd 7");
							//delete old row for shadow balance..

							// credit entry...
							AccountTransObject am = new AccountTransObject();
							am.setAccType(cd.getCreditACType());
							am.setAccNo(cd.getCreditACNo());
							am.setTransType("R");
							am.setTransAmount(cd.getTranAmt());
							am.setTransMode("G");
							am.setTransSource(cd.getVeTml());
							am.setCdInd("C");
							am.setTransDate(getToday());
							am.setChqDDNo(cd.getQdpNo());
							am.setChqDDDate(cd.getQdpDate());
							am.setTransNarr("Ctrl No "+cd.getControlNo());
							am.setRef_No(Integer.parseInt(String.valueOf(cd.getControlNo())));
							am.setPayeeName(cd.getPayeeName());
							am.setCloseBal(cd.getTranAmt());
							am.setLedgerPage(0);
							am.uv.setUserTml(cd.getVeTml());
							am.uv.setUserId(cd.getVeUser());
							am.uv.setUserDate(cd.getVe_date());
							am.uv.setVerTml(cd.getVeTml());
							am.uv.setVerId(cd.getVeUser());
							am.uv.setVerDate(cd.getVe_datetime());

							comm.storeAccountTransaction(am);
							
							ResultSet rs_ret = null;
							// GL goes here..for credit entry
							System.out.println(" +++++++++++++++ ---------" + cd.getCreditACType());
							if(cd.getCreditACType().startsWith("1014") || cd.getCreditACType().startsWith("1015")){
								System.out.println("inside the adcc GLTrn");
								rs_ret = stupdate.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
							}
							else
								rs_ret = stupdate.executeQuery("select am.ac_type,trn_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
							
							if(rs_ret.next())
							{
								
								trn_seq=rs_ret.getInt(2);
																		
							if(rs_ret.getInt(1)==0)
								cat_type=1;
							else
								cat_type=2;
							}
										
								ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
								if(ret.next())
								{
									GLTransObject trnobj=new GLTransObject();
									trnobj.setTrnDate(getToday());
									trnobj.setGLType(ret.getString(3));
									trnobj.setGLCode(ret.getString(1));
									trnobj.setTrnMode("G");
									trnobj.setAmount((cd.getTranAmt())*ret.getInt(2));//-res.getDouble("ch.disc_amt")
									trnobj.setCdind("C");
									trnobj.setAccType(cd.getCreditACType());
									trnobj.setAccNo(Integer.toString(cd.getCreditACNo()));
									trnobj.setTrnType("R");
									trnobj.setRefNo((int)(cd.getControlNo()));
									trnobj.setTrnSeq(trn_seq);
									trnobj.setVtml(cd.getVeTml());
									trnobj.setVid(cd.getVeUser());
									trnobj.setVDate(cd.getVe_datetime());
									System.out.println("chek 7777777777777 ");

									comm.storeGLTransaction(trnobj);
									System.out.println("chek 88888888888888 ");
								}
						
					}
					
				} else {
					
					ret = stmt.executeQuery("select * from GLKeyParam where code=11 and  ac_type ='1011001' ");
					if(ret.next())
					{
						System.out.println("33333333333");
						GLTransObject trnobj=new GLTransObject();
					
						trnobj.setTrnDate(getToday());
						trnobj.setGLType(ret.getString("gl_type"));
						trnobj.setGLCode(ret.getString("gl_code"));
						trnobj.setTrnMode("G");
						trnobj.setAmount(cd.getTranAmt());
						trnobj.setCdind("D");
						trnobj.setAccType(cd.getDebitACType());
						trnobj.setAccNo(Integer.toString(cd.getDebitACNo()));
						trnobj.setTrnType("R");
						trnobj.setRefNo(cd.getAckNo());
						trnobj.setTrnSeq(0);
						trnobj.setVtml(cd.getVeTml());
						trnobj.setVDate(cd.getVe_date());
						trnobj.setVid(cd.getVeUser());System.out.println(34);

						comm.storeGLTransaction(trnobj);
						System.out.println(35);
						System.out.println("444444444444444");
						
						
					}
					
					if ( cd.getFineAmt()> 0){
						
						 AccountTransObject am = new AccountTransObject();
						 
						 am.setAccType(cd.getCreditACType());
						 am.setAccNo(cd.getCreditACNo());
					
						 am.setTransType("P");
						 am.setTransAmount( cd.getFineAmt() );
						 am.setTransMode("T");
						 am.setTransSource(cd.getVeTml());
						 am.setTransDate(getToday());
						 
						 am.setCdInd("D");
						 am.setChqDDNo(cd.getQdpNo());
						 am.setChqDDDate(cd.getQdpDate());
						 am.setTransNarr("Ctrl No "+cd.getControlNo());
						 am.setRef_No(Integer.parseInt(String.valueOf(cd.getControlNo())));
						 am.setPayeeName(cd.getPayeeName());
						 am.setCloseBal(cd.getFineAmt());
						 am.setLedgerPage(0);
						 am.uv.setUserTml(cd.getDeTml());
						 am.uv.setUserId(cd.getDeUser());
						 am.uv.setUserDate(cd.getVe_date());
						 am.uv.setVerTml(cd.getVeTml());
						 am.uv.setVerId(cd.getVeUser());
						 am.uv.setVerDate(cd.getVe_datetime());
		
						 comm.storeAccountTransaction(am);
						 
						 ResultSet gl = stmt2.executeQuery("select * from GLKeyParam where ac_type='1011001' and code=7");
						 if(gl.next())
						 {
							 System.out.println (1111);
							 
							 GLTransObject trnobj=new GLTransObject();
							 trnobj.setTrnDate(getToday());
							 System.out.println (22);
							 trnobj.setGLType(gl.getString("gl_type"));
							 trnobj.setGLCode(gl.getString("gl_code"));
							 System.out.println (33);
							 trnobj.setTrnMode("G");
							 trnobj.setAmount(cd.getFineAmt());
							 System.out.println (44);
							 trnobj.setCdind("C");
							 trnobj.setAccType(cd.getDebitACType());
							 trnobj.setAccNo(Integer.toString(cd.getDebitACNo()));
							 trnobj.setTrnType("");
							 trnobj.setRefNo(0);
							 trnobj.setTrnSeq(0);
							 trnobj.setVtml(cd.getVeTml());
							 trnobj.setVid(cd.getVeUser());
							 trnobj.setVDate(cd.getVe_datetime());
							 comm.storeGLTransaction(trnobj);
							 System.out.println (66);
						 }
					}
				
					{
					
	 				
	 				ResultSet rs=stmt.executeQuery("select modulecode,lst_acc_no from Modules where modulecode='1011001'");
	 				rs.next();
	 				ctrlno=rs.getInt(2)+1;
	 				rs.updateLong("lst_acc_no",ctrlno);
	 				rs.updateRow();
	 				rs.close();
	 				
	 				pstmt=conn.prepareStatement("insert into Cheque values(null,null,null,?,'G',?,?,?,?,'S',1,0.0,?,?,?,?,?,'R',?,null,?,?,?,?,?,?,?,?,?,?,'F','F',?,?,?,null,null,'F',0.00,0.00,?,?,?,?,?,?,?,?)");
	 				
	 					
	 				System.out.println (13);
	 				
	 				pstmt.setLong(1,ctrlno);
	 				pstmt.setString(2,cd.getClgType());
	 				pstmt.setInt(3,cd.getAckNo());
	 				pstmt.setInt(4,cd.getDocDestination());
	 				pstmt.setInt(5,cd.getDocSource());
	 				pstmt.setString(6,cd.getMultiCredit());
	 				pstmt.setString(7,cd.getCompanyName());
	 				pstmt.setString(8,cd.getChqDDPO());
	 				pstmt.setInt(9,cd.getQdpNo());
	 				pstmt.setString(10,cd.getQdpDate());

	 				pstmt.setLong(11,cd.getControlNo());
	 				pstmt.setString(12,cd.getDebitACType());
	 				pstmt.setInt(13,cd.getDebitACNo());
	 								
	 				pstmt.setDouble(14,cd.getPOCommission());
	 				pstmt.setString(15,cd.getCreditACType());
	 				pstmt.setInt(16,cd.getCreditACNo());
	 				pstmt.setString(17,cd.getPayeeName());

	 				pstmt.setString(18,cd.getBankCode());
	 				pstmt.setString(19,cd.getBranchName());
	 				pstmt.setDouble(20,cd.getTranAmt());

	 				pstmt.setString(21,"F");
	 				pstmt.setString(22,cd.getDiscInd());
	 				pstmt.setDouble(23,cd.getDiscAmt());
	 				pstmt.setDouble(24,cd.getDiscChg());

	 				pstmt.setString(25,cd.getLoanAcc());
	 				pstmt.setInt(26,cd.getLoanAcc_No());
	 				pstmt.setString(27,cd.getDeTml());
	 				pstmt.setString(28,cd.getDeUser());
	 				pstmt.setString(29,cd.getDeTime());
	 				pstmt.setString(30,cd.getDeTime());
	 				pstmt.setString(31,cd.getDeTime());
	 				pstmt.setString(32,cd.getDeTime());
	 				
	 				
	 				if(pstmt.executeUpdate()==0){
	 					sessionContext.setRollbackOnly();
	 					throw new SQLException();
	 				}
	 				
				}
				}
				
				
				int r = stmt.executeUpdate("update Cheque set ve_tml = '"+ cd.getVeTml()+"', ve_user = '"+ cd.getVeUser()+ "', ve_dt_time = '"+ cd.getVe_datetime()+"' where ctrl_no = " + cd.getControlNo());
				
	 			if ( r == 0){
	 				sessionContext.setRollbackOnly();
	 				throw new RecordNotInsertedException();
	 			}
	 			
	 			
	 		
	 		} catch( RecordNotInsertedException re ){
	 			
	 			re.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw re;
	 		}
	 		catch ( CreateException cre ){
	 			
	 			cre.printStackTrace();
	 		}
	 		catch (SQLException sql){
	 			
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}
	 		finally {
	 			
	 			try{
	 				
	 				conn.close();
	 			}catch ( SQLException sqlexc ){
	 				
	 			}
	 		}
	 		
	 		

	 		
	 		
	 		
	 		return ctrlno;
	 	}
	 	
	 	
	 	public AckObject[] getAckReconcillation(boolean verified)throws RecordsNotFoundException
	 	{
	 		AckObject[] ack=null;
	 		Connection conn=null;
	 		try
	 		{
	 			ResultSet rs;
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 			if(!verified)
	 				rs=stmt.executeQuery("select ack.*,bm.br_name from Acknowledge ack,BranchMaster bm where reconciled='F' and bm.br_code=ack.doc_sou and ve_user is null order by ack_no");
	 			else
	 				rs=stmt.executeQuery("select ack.*,bm.br_name from Acknowledge ack,BranchMaster bm where reconciled='T' and bm.br_code=ack.doc_sou and ve_user is null order by ack_no");
	 			rs.last();
	 			if(rs.getRow()==0)
	 				ack=null;
	 			ack=new AckObject[rs.getRow()];
	 			rs.beforeFirst();
	 			int i=0;
	 			while(rs.next())
	 			{
	 				ack[i]=new AckObject();
	 				ack[i].setAckNo(rs.getInt("ack_no"));
	 				ack[i].setAckDate(rs.getString("ack_date"));
	 				ack[i].setDocSource(rs.getInt("doc_sou"));
	 				ack[i].branchobject.setBranchName(rs.getString("br_name"));
	 				ack[i].setTotal(rs.getDouble("tot_amt"));
	 				ack[i].setReconciled(rs.getString("reconciled"));

	 				i++;
	 			}
	 			//return ack;
	 		}catch(SQLException e){
	 			e.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try{
	 				conn.close();
	 			}catch(SQLException e1) {
	 				e1.printStackTrace();
	 			}
	 		}
	 		return ack;
	 	}
	 	
	 	public int deleteControl(int ctrlno)throws RecordNotInsertedException 
	 	{
	 		Connection conn=null;
	 		try{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			stmt.executeUpdate("delete from Cheque where ctrl_no="+ctrlno);
	 			return 1;
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e1) {
	 				e1.printStackTrace();
	 			}
	 		}
	 		//return -1;
	 	}
	 	public long storeChequeData(ChequeAckObject cd)throws RecordNotInsertedException
	 	{
	 		ResultSet rs,rs1=null;
	 		long ctrlno=-1;
	 		Connection conn=null;
	 		
	 		
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement st1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			/*rs1=st1.executeQuery("select reconciled from Acknowledge where ack_no="+ cd.getAckNo());
	 			rs1.next();
	 			*/if(cd.getControlNo()==0)
	 			{
	 				//ctrlno=Long.parseLong(comm.getGenParam("lst_ctrl_no").toString());
	 				rs=stmt.executeQuery("select modulecode,lst_acc_no from Modules where modulecode='1011001'");
	 				rs.next();
	 				ctrlno=rs.getInt(2)+1;
	 				rs.updateLong("lst_acc_no",ctrlno);
	 				rs.updateRow();
	 				rs.close();
	 				System.out.println("bank code..............."+ cd.getBankCode());
	 				stmt.executeUpdate("insert into Cheque(ctrl_no,bank_cd,trn_type,clg_type,ack_no,doc_sou,doc_dest,doc_bs,no_docs,doc_tot,cr_ac_type,cr_ac_no,trn_amt,de_tml,de_user,de_dt_time,desp_ind,post_ind,mult_cr,ret_norm,let_sent)values("+ctrlno+",'"+cd.getBankCode()+"','"+cd.getTranType()+"','"+cd.getClgType()+"',"+cd.getAckNo()+","+cd.getDocSource()+","+cd.getDocDestination()+",'"+cd.getDocBs()+"',"+cd.getNoOfDocs()+","+cd.getDocTotal()+",'"+cd.getCreditACType()+"',"+cd.getCreditACNo()+",0,'"+cd.getDeTml()+"','"+cd.getDeUser()+"','"+cd.getDeTime()+"','F','F','F','F','F')");
	 				//stmt.executeUpdate("update GenParam set lst_ctrl_no="+ctrlno);
	 				//return ctrlno;
	 				System.out.println("out is"+cd.getClgType() +" "+ cd.getTranType() );
	 			}
	 			else if(cd.getVeTml()==null && cd.getControlNo()>0)
	 			{
	 				System.out.println("inside the update function ");
	 				stmt.executeUpdate("update Cheque set bank_cd='"+cd.getBankCode()+"',clg_type='"+cd.getClgType()+"',ack_no="+cd.getAckNo()+",doc_sou="+cd.getDocSource()+",doc_dest="+cd.getDocDestination()+",doc_bs='"+cd.getDocBs()+"',no_docs="+cd.getNoOfDocs()+",doc_tot="+cd.getDocTotal()+",cr_ac_type='"+cd.getCreditACType()+"',cr_ac_no="+cd.getCreditACNo()+",desp_ind='F',post_ind='F' where ctrl_no="+cd.getControlNo()+" and ve_tml is null");
	 				ctrlno=cd.getControlNo();
	 			}
	 			
	 			else
	 			{											//ve_tml='"+cd.getDeTml()+"',ve_user='"+cd.getDeUser()+"',ve_dt_time='"+cd.getDeTime()+"',
	 				System.out.println("inside the Verification " +cd.getControlNo() );
	 				ctrlno=st.executeUpdate("update Cheque set ve_tml='"+cd.getVeTml()+"',ve_user='"+cd.getVeUser()+"',ve_dt_time = '"+cd.getDeTime()+"' where ctrl_no="+cd.getControlNo());
	 				if(ctrlno==0){
	 					sessionContext.setRollbackOnly();
	 					throw new SQLException();
	 				}
	 				}
	 			
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return ctrlno;
	 	}
	 	public double reconcile(int ackno)throws RecordsNotFoundException
	 	{
	 		double doctot = 0.0,acktot = 0.0;
	 		Connection conn=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			ResultSet rs=stmt.executeQuery("select sum(c.doc_tot+c.trn_amt),a.tot_amt from Cheque c,Acknowledge a where c.ack_no="+ackno+" and a.ack_no="+ackno+" group by c.ack_no");
	 			if(rs.next())
	 			{
	 				doctot=rs.getDouble(1);
	 				acktot=rs.getDouble(2);
	 				if(doctot==acktot)
	 				{
	 					stmt.executeUpdate("update Acknowledge set reconciled='T' where ack_no="+ackno);
	 					return 0.0;
	 				}
	 				else{
	 					
	 					return Double.parseDouble(DoubleFormat.toString((acktot-doctot)));
	 				}
	 			}
	 			else
	 			{
	 				
	 				throw new RecordsNotFoundException();
	 			}
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return Double.NaN;
	 	}
	 	
	 	public ChequeAckObject[] getChequeDetails(int ackno)throws RecordsNotFoundException
	 	{
	 		ChequeAckObject[] cao=null;
	 		ResultSet rs=null;
	 		ResultSet rs1=null;
	 		int len = 0;
	 		
	 		Connection conn=null;
	 		try{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			rs=stmt.executeQuery("select c.*,bm1.br_name,bm2.br_name,bk.bank_name from Cheque c,BankMaster as bk,BranchMaster as bm1,BranchMaster as bm2 where ack_no="+ackno+" and bm1.br_code=c.doc_sou and bm2.br_code=c.doc_dest and bk.bank_code=substring(c.bank_cd,4,3) ");
	 		// UNION  select c.*, bm1.br_name,bm2.br_name, hd.bank_abbr from Cheque as c, BranchMaster as bm1,BranchMaster as bm2 , Head as hd  where ack_no= "+ackno+" and bm1.br_code=c.doc_sou and bm2.br_code=c.doc_dest and hd.bankcode = substring(c.bank_cd,9,1)
	 			
	 			rs1 = stmt1.executeQuery("select c.*, bm1.br_name,bm2.br_name, hd.bank_abbr from Cheque as c, BranchMaster as bm1,BranchMaster as bm2 , Head as hd  where ack_no= "+ackno+" and bm1.br_code=c.doc_sou and bm2.br_code=c.doc_dest and hd.bankcode = substring(c.bank_cd,9,1)");
	 			rs.last();
	 			rs1.last();
	 			if(rs.getRow() > 0 || rs1.getRow() > 0){
	 				//throw new RecordsNotFoundException();
	 				
	 			int rs_len = 0;
	 			cao = new ChequeAckObject[rs.getRow()+ rs1.getRow()];
	 			System.out.println(cao.length+" -------------------");
	 			
	 			len = cao.length;
	 			
	 			System.out.println("inside UNION ---------------------");
	 			rs_len = rs.getRow();
	 			rs.beforeFirst();
	 			rs1.beforeFirst();
	 			int i=0;
	 			
	 			while(rs.next()){
	 				
	 				cao[i]=new ChequeAckObject();
	 				cao[i].setControlNo(rs.getInt("ctrl_no"));
	 				cao[i].setTranType(rs.getString("trn_type"));
	 				cao[i].setClgType(rs.getString("clg_type"));
	 				cao[i].setDocSource(rs.getInt("doc_sou"));
	 				cao[i].setDocDestination(rs.getInt("doc_dest"));
	 				cao[i].setDocBs(rs.getString("doc_bs"));
	 				cao[i].setNoOfDocs(rs.getInt("no_docs"));
	 				cao[i].setDocTotal((rs.getDouble("doc_tot")==0)?rs.getDouble("trn_amt"):rs.getDouble("doc_tot"));
	 				cao[i].setBankCode(rs.getString("bank_cd"));
	 				cao[i].setSourceName(rs.getString("bm1.br_name")); // bm1.br_name
	 				cao[i].setDestName(rs.getString("bm2.br_name"));
	 				cao[i].setBankName(rs.getString("bk.bank_name"));
	 				
	 				if(rs.getString("clg_type").equalsIgnoreCase("I"))
	 				{
	 					cao[i].setCreditACType(rs.getString("dr_ac_type"));
	 					cao[i].setCreditACNo(rs.getInt("dr_ac_no"));
	 			
	 				} else {
	 					
	 					cao[i].setCreditACType(rs.getString("cr_ac_type"));//    
	 					cao[i].setCreditACNo(rs.getInt("cr_ac_no"));
	 				}
	 					
	 				cao[i].setChequeNo(rs.getString("qdp_no"));
	 				
	 				
	 					i++;
	 			}
	 			
	 			
	 			while(rs1.next()){
		 				cao[i]=new ChequeAckObject();
		 				cao[i].setControlNo(rs1.getInt("ctrl_no"));
		 				System.out.println("==============="+rs1.getInt("ctrl_no"));
		 				cao[i].setTranType(rs1.getString("trn_type"));
		 				cao[i].setClgType(rs1.getString("clg_type"));
		 				cao[i].setDocSource(rs1.getInt("doc_sou"));
		 				cao[i].setDocDestination(rs1.getInt("doc_dest"));
		 				cao[i].setDocBs(rs1.getString("doc_bs"));
		 				cao[i].setNoOfDocs(rs1.getInt("no_docs"));
		 				cao[i].setDocTotal((rs1.getDouble("doc_tot")==0)?rs1.getDouble("trn_amt"):rs1.getDouble("doc_tot"));
		 				cao[i].setBankCode(rs1.getString("bank_cd"));
		 				cao[i].setSourceName(rs1.getString("bm1.br_name"));
		 				cao[i].setDestName(rs1.getString("bm2.br_name"));
		 				cao[i].setBankName(rs1.getString("hd.bank_abbr"));
		 				
		 				{
		 					
		 					cao[i].setCreditACType(rs1.getString("cr_ac_type"));//    
		 					cao[i].setCreditACNo(rs1.getInt("cr_ac_no"));
		 				}
		 				System.out.println("===============================99999999999999999999999===============");
		 				i++;
		 			}
	 			
	 			
	 			
	 			
	 			
	 			}else{
	                                  // select c.*, bm1.br_name,bm2.br_name, hd.bank_abbr from Cheque as c, BranchMaster as bm1,BranchMaster as bm2 , Head as hd  where ack_no= "+ackno+" and bm1.br_code=c.doc_sou and bm2.br_code=c.doc_dest and hd.bankcode = substring(c.bank_cd,9,1)  					
	 				rs=stmt.executeQuery("select c.*, bm1.br_name,bm2.br_name, hd.bank_abbr from Cheque c, BranchMaster bm1,BranchMaster bm2 , Head hd  where ack_no= "+ackno+" and bm1.br_code=c.doc_sou and bm2.br_code=c.doc_dest and hd.bankcode = substring(c.bank_cd,9,1)");
	 	 			System.out.println("===============================99999999999999999999999===============");
	 				rs.last();
	 	 			if(rs.getRow()==0)
	 	 				throw new RecordsNotFoundException();
	 	 			System.out.println("===============================99999999999999999999999===============");
	 	 			cao = new ChequeAckObject[rs.getRow()];
	 	 			
	 	 			rs.beforeFirst();
	 	 			int i=0;
	 	 			while(rs.next()){
	 	 				cao[i]=new ChequeAckObject();
	 	 				cao[i].setControlNo(rs.getInt("ctrl_no"));
	 	 				System.out.println("==============="+rs.getInt("ctrl_no"));
	 	 				cao[i].setTranType(rs.getString("trn_type"));
	 	 				cao[i].setClgType(rs.getString("clg_type"));
	 	 				cao[i].setDocSource(rs.getInt("doc_sou"));
	 	 				cao[i].setDocDestination(rs.getInt("doc_dest"));
	 	 				cao[i].setDocBs(rs.getString("doc_bs"));
	 	 				cao[i].setNoOfDocs(rs.getInt("no_docs"));
	 	 				cao[i].setDocTotal((rs.getDouble("doc_tot")==0)?rs.getDouble("trn_amt"):rs.getDouble("doc_tot"));
	 	 				cao[i].setBankCode(rs.getString("bank_cd"));
	 	 				cao[i].setSourceName(rs.getString("bm1.br_name"));
	 	 				cao[i].setDestName(rs.getString("bm2.br_name"));
	 	 				cao[i].setBankName(rs.getString("bk.bank_name"));
	 	 				System.out.println("===============================99999999999999999999999===============");
	 	 				i++;
	 	 			}
	 				
	 			}
	 			//return cao;
	 		}catch(SQLException e){
	 			e.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e1) {
	 				e1.printStackTrace();
	 			}
	 		}
	 		return cao;
	 	}
	 	
	 	public ChequeAckObject[] ackChequeDetails(int ackno)throws RecordsNotFoundException{
	 		
	 		
	 		ChequeAckObject[] cao=null;
	 		ResultSet rs=null;
	 		ResultSet rs1=null;
	 		
	 		
	 		Connection conn=null;
	 		
	 		try {
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			rs=stmt.executeQuery("select c.*,bm1.br_name,bm2.br_name,bk.bank_name from Cheque c,BankMaster bk,BranchMaster bm1,BranchMaster bm2 where ack_no="+ackno+" and bm1.br_code=c.doc_sou and bm2.br_code=c.doc_dest and bk.bank_code=substring(c.bank_cd,4,3)");
	 			//rs1=stmt1.executeQuery("select c.*, bm1.br_name,bm2.br_name, hd.bank_abbr from Cheque c, BranchMaster bm1,BranchMaster bm2 , Head hd  where ack_no= "+ackno+" and bm1.br_code=c.doc_sou and bm2.br_code=c.doc_dest and hd.bankcode = substring(c.bank_cd,9,1)");
	 			
	 			rs.last();
	 			//rs1.last();
	 			int i=0;
	 			//int row_count= rs.getRow() + rs1.getRow();
	 			int row_count= rs.getRow();
	 			cao = new ChequeAckObject[row_count];
	 			
	 			System.out.println(" no of rows -----------"+i);
	 			
	 			if(row_count == 0 )
	 				throw new RecordsNotFoundException();
	 			if(rs.getRow() > 0){
	 				//throw new RecordsNotFoundException();
	 			//cao = new ChequeAckObject[rs.getRow()];
	 			
	 			rs.beforeFirst();
	 			
	 			while(rs.next()){
	 				cao[i]=new ChequeAckObject();
	 				cao[i].setControlNo(rs.getInt("c.ctrl_no"));
	 				cao[i].setTranType(rs.getString("c.trn_type"));
	 				cao[i].setClgType(rs.getString("c.clg_type"));
	 				cao[i].setDocSource(rs.getInt("c.doc_sou"));
	 				cao[i].setDocDestination(rs.getInt("c.doc_dest"));
	 				cao[i].setDocBs(rs.getString("c.doc_bs"));
	 				cao[i].setNoOfDocs(rs.getInt("c.no_docs"));
	 				cao[i].setDocTotal((rs.getDouble("c.doc_tot")==0)?rs.getDouble("c.trn_amt"):rs.getDouble("c.doc_tot"));
	 				cao[i].setBankCode(rs.getString("c.bank_cd"));
	 				cao[i].setSourceName(rs.getString("bm1.br_name"));
	 				cao[i].setDestName(rs.getString("bm2.br_name"));
	 				cao[i].setBankName(rs.getString("bk.bank_name"));
	 				i++;
	 			}
	 			
	 		}
	 			
	 			/*if(rs1.getRow() > 0 ){
	 				
	 				rs1=stmt.executeQuery("select c.*, bm1.br_name,bm2.br_name, hd.bank_abbr from Cheque c, BranchMaster bm1,BranchMaster bm2 , Head hd  where ack_no= "+ackno+" and bm1.br_code=c.doc_sou and bm2.br_code=c.doc_dest and hd.bankcode = substring(c.bank_cd,9,1)");
	 	 			System.out.println("===============================99999999999999999999999===============");
	 				//rs.last();
	 	 			if(rs.getRow()==0)
	 	 				throw new RecordsNotFoundException();
	 	 			System.out.println("===============================99999999999999999999999===============");
	 	 			//cao = new ChequeAckObject[rs.getRow()];
	 	 			
	 	 			rs1.beforeFirst();
	 	 			
	 	 			while(rs1.next()){
	 	 				cao[i]=new ChequeAckObject();
	 	 				cao[i].setControlNo(rs1.getInt("c.ctrl_no"));
	 	 				System.out.println("==============="+rs1.getInt("c.ctrl_no"));
	 	 				cao[i].setTranType(rs1.getString("c.trn_type"));
	 	 				cao[i].setClgType(rs1.getString("c.clg_type"));
	 	 				cao[i].setDocSource(rs1.getInt("c.doc_sou"));
	 	 				cao[i].setDocDestination(rs1.getInt("c.doc_dest"));
	 	 				cao[i].setDocBs(rs1.getString("c.doc_bs"));
	 	 				cao[i].setNoOfDocs(rs1.getInt("c.no_docs"));
	 	 				cao[i].setDocTotal((rs1.getDouble("c.doc_tot")==0)?rs1.getDouble("c.trn_amt"):rs1.getDouble("c.doc_tot"));
	 	 				cao[i].setBankCode(rs1.getString("c.bank_cd"));
	 	 				cao[i].setSourceName(rs1.getString("bm1.br_name"));
	 	 				cao[i].setDestName(rs1.getString("bm2.br_name"));
	 	 				cao[i].setBankName(rs1.getString("hd.bank_abbr"));
	 	 				System.out.println("===============================99999999999999999999999===============");
	 	 				i++;
	 	 			}
	 				
	 			}*/
	 			//return cao;
	 		}catch(SQLException e){
	 			e.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e1) {
	 				e1.printStackTrace();
	 			}
	 		}
	 		return cao;	
	 		
	 		
	 		
	 		
	 		
	 	}
	 	
	 	
	 	public AckObject getAckDetails(int ackno,int ctrl)throws RecordsNotFoundException
	 	{
	 		AckObject ack=new AckObject();
	 		double ackrem=0;
	 		ResultSet rs,rs2;
	 		Connection conn=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			if(ctrl==1)
	 				rs=stmt.executeQuery("select a.*,bm.*,c.clg_type,c.no_docs,c.doc_tot,c.doc_dest,c.ack_no,bank_cd,bk.bank_name,bmm.br_name from Cheque c,BankMaster bk,Acknowledge a,BranchMaster bm,BranchMaster bmm where  c.ctrl_no="+ackno+" and  doc_bs='B' and a.ack_no=c.ack_no  and  bm.br_code=a.doc_sou and bk.bank_code=substring(c.bank_cd,4,3) and bmm.br_code=c.doc_dest and c.ve_tml is null");
	 			else if(ctrl==2)
	 				rs=stmt.executeQuery("select a.*,bm.*,c.clg_type,c.no_docs,c.doc_tot,c.doc_dest,c.ack_no,bank_cd,bk.bank_name,bmm.br_name from Cheque c,BankMaster bk,Acknowledge a,BranchMaster bm,BranchMaster bmm where  c.ctrl_no="+ackno+" and  doc_bs='B' and a.ack_no=c.ack_no  and  bm.br_code=a.doc_sou and bk.bank_code=substring(c.bank_cd,4,3) and bmm.br_code=c.doc_dest and c.ve_tml is null and a.reconciled = 'T'");
	 			else if(ctrl==3)
	 				rs=stmt.executeQuery("select a.*,bm.*,c.clg_type,c.no_docs,c.doc_tot,c.doc_dest,c.ack_no,bank_cd,hd.bank_abbr,bmm.br_name from Cheque c,Head hd,Acknowledge a,BranchMaster bm,BranchMaster bmm where  c.ctrl_no= "+ackno+" and  doc_bs='B' and a.ack_no=c.ack_no  and  bm.br_code=a.doc_sou and hd.bankcode=substring(c.bank_cd,9,1) and bmm.br_code=c.doc_dest and c.ve_tml is null and a.reconciled = 'T'");
	 			else
	 				rs=stmt.executeQuery("select ack.*,bm.* from Acknowledge ack,BranchMaster bm where ack.ack_no="+ackno+" and bm.br_code=ack.doc_sou");
	 			
	 			if(rs.next())
	 			{
	 			    if(ctrl==1)
	 			        ackno=rs.getInt("ack_no");
	 			    
	 			    rs2=st.executeQuery("select sum(doc_tot+trn_amt) from Cheque where ack_no="+ackno+" and ve_tml is null ");
	 				try
	 				{
	 					rs2.next();
	 					ackrem=rs2.getDouble(1);
	 					
	 				}catch(SQLException sql){
	 				    ackrem=0.0;
	 				    System.out.println("No Entries for Ack in Cheque");
	 				}
	 				
	 				ack.setAckNo(rs.getInt("ack_no"));
	 				ack.setAckDate(rs.getString("ack_date"));
	 				ack.setDocSource(rs.getInt("doc_sou"));
	 				ack.setClgType(rs.getString("clg_type"));
	 				ack.branchobject.setBranchName(rs.getString("bm.br_name"));
	 				ack.branchobject.setBranchACType(rs.getString("br_ac_type"));
	 				ack.branchobject.setBranchACNo(rs.getInt("br_ac_no"));
	 				ack.setTotal(rs.getDouble("tot_amt"));
	 				ack.setReconciled(rs.getString("reconciled"));
	 				ack.setAckEntered(ackrem);
	 				ack.setVeTml(rs.getString("ve_tml"));
	 				
	 				
	 							
	 				if(ctrl==1 || ctrl ==2)
	 				{
	 					ack.setClgType(rs.getString("clg_type"));
	 				    ack.branchobject.setBranchAddress(rs.getString("bmm.br_name"));
	 					ack.setNoDocs(rs.getInt("c.no_docs"));
	 					ack.setDocTotal(rs.getDouble("c.doc_tot"));
	 					ack.setBankCode(rs.getString("bank_cd"));
	 					ack.setBankName(rs.getString("bk.bank_name"));
	 					ack.setBranch(rs.getString("c.doc_dest"));
	 					ack.setDocSource(rs.getInt("doc_sou"));
	 					ack.branchobject.setBranchName(rs.getString("bm.br_name"));
	 					ack.branchobject.setBranchACType(rs.getString("br_ac_type"));
	 					//VeTml value
	 				}
	 				//return ack;
	 			} else 
	 				{
	 					ack=null;
	 					return ack;
	 				}
	 			/*else
	 			    rs.getInt("ack_no");*/
	 		}catch(SQLException e){
	 			//ack.setAckNo(-1);
	 			//ack.setReconciled("T");
	 			e.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e1) {
	 				e1.printStackTrace();
	 			}
	 		}
	 		return ack;
	 	}
	 	
	 	public AckObject getBundleAckDetails(int ackno,int ctrl)throws RecordsNotFoundException{
	 		
	 		AckObject ack=new AckObject();
	 		double ackrem=0;
	 		ResultSet rs = null,rs2 = null;
	 		Connection conn=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			 if(ctrl==2)
	 			 {
	 				 rs=stmt.executeQuery("select a.*,bm.*,c.clg_type,c.no_docs,c.doc_tot,c.doc_dest,c.ack_no,bank_cd,bk.bank_name,bmm.br_name from Cheque c,BankMaster bk,Acknowledge a,BranchMaster bm,BranchMaster bmm where  c.ctrl_no="+ackno+" and  doc_bs='B' and a.ack_no=c.ack_no  and  bm.br_code=a.doc_sou and bk.bank_code=substring(c.bank_cd,4,3) and bmm.br_code=c.doc_dest and c.ve_tml is null and a.reconciled = 'T' and a.ve_tml is not null");
	 			
	 			if(rs.next())
	 			{
	 				ackrem = rs.getDouble("a.ack_no");
	 				
	 			    rs2=st.executeQuery("select sum(doc_tot+trn_amt) from Cheque where ack_no="+ackno+" and ve_tml is null ");
	 				try
	 				{
	 					rs2.next();
	 					ackrem=rs2.getDouble(1);
	 					
	 				}catch(SQLException sql){
	 				    ackrem=0.0;
	 				    System.out.println("No Entries for Ack in Cheque");
	 				}
	 				
	 				ack.setAckNo(rs.getInt("ack_no"));
	 				ack.setAckDate(rs.getString("ack_date"));
	 				ack.setDocSource(rs.getInt("doc_sou"));
	 				ack.branchobject.setBranchName(rs.getString("bm.br_name"));
	 				ack.branchobject.setBranchACType(rs.getString("br_ac_type"));
	 				ack.branchobject.setBranchACNo(rs.getInt("br_ac_no"));
	 				ack.setTotal(rs.getDouble("tot_amt"));
	 				ack.setReconciled(rs.getString("reconciled"));
	 				ack.setAckEntered(ackrem);
	 				ack.setVeTml(rs.getString("ve_tml"));
	 				
	 				ack.setClgType(rs.getString("clg_type"));
	 				ack.branchobject.setBranchAddress(rs.getString("bmm.br_name"));
	 				ack.setNoDocs(rs.getInt("c.no_docs"));
	 				ack.setDocTotal(rs.getDouble("c.doc_tot"));
	 				ack.setBankCode(rs.getString("bank_cd"));
	 				ack.setBankName(rs.getString("bk.bank_name"));
	 				ack.setBranch(rs.getString("c.doc_dest"));
	 				ack.setDocSource(rs.getInt("doc_sou"));
	 				ack.branchobject.setBranchName(rs.getString("bm.br_name"));
	 				ack.branchobject.setBranchACType(rs.getString("br_ac_type"));
	 					//VeTml value
	 				
	 				//return ack;
	 			} else{
	 				
	 				rs=stmt.executeQuery("select a.*,bm.*,c.clg_type,c.no_docs,c.doc_tot,c.doc_dest,c.ack_no,bank_cd,hd.bank_abbr,bmm.br_name from Cheque c,Head hd,Acknowledge a,BranchMaster bm,BranchMaster bmm where  c.ctrl_no= "+ackno+" and  doc_bs='B' and a.ack_no=c.ack_no  and  bm.br_code=a.doc_sou and hd.bankcode=substring(c.bank_cd,9,1) and bmm.br_code=c.doc_dest and c.ve_tml is null and a.reconciled = 'T' and a.ve_tml is not null");
	 				
	 				if(rs.next()){
	 					
	 					ackrem = rs.getDouble("a.ack_no");
	 				
	 				rs2=st.executeQuery("select sum(doc_tot+trn_amt) from Cheque where ack_no="+ackno+" and ve_tml is null ");
	 				try
	 				{
	 					rs2.next();
	 					ackrem=rs2.getDouble(1);
	 					
	 				}catch(SQLException sql){
	 				    ackrem=0.0;
	 				    System.out.println("No Entries for Ack in Cheque");
	 				}
	 				
	 					ack.setAckNo(rs.getInt("ack_no"));
	 					ack.setAckDate(rs.getString("ack_date"));
	 					ack.setDocSource(rs.getInt("doc_sou"));
	 					ack.branchobject.setBranchName(rs.getString("bm.br_name"));
	 					ack.branchobject.setBranchACType(rs.getString("br_ac_type"));
	 					ack.branchobject.setBranchACNo(rs.getInt("br_ac_no"));
	 					ack.setTotal(rs.getDouble("tot_amt"));
	 					ack.setReconciled(rs.getString("reconciled"));
	 					ack.setAckEntered(ackrem);
	 					ack.setVeTml(rs.getString("ve_tml"));
	 				
	 					ack.setClgType(rs.getString("clg_type"));
	 					ack.branchobject.setBranchAddress(rs.getString("bmm.br_name"));
	 					ack.setNoDocs(rs.getInt("c.no_docs"));
	 					ack.setDocTotal(rs.getDouble("c.doc_tot"));
	 					ack.setBankCode(rs.getString("bank_cd"));
	 					ack.setBankName(rs.getString("hd.bank_abbr"));
	 					ack.setBranch(rs.getString("c.doc_dest"));
	 					ack.setDocSource(rs.getInt("doc_sou"));
	 					ack.branchobject.setBranchName(rs.getString("bm.br_name"));
	 					ack.branchobject.setBranchACType(rs.getString("br_ac_type"));
	 				}else{
	 					ack=null;
	 					return ack;
	 				}
	 			}
	 		}
	 			if(ctrl == 1){
	 				
	 				double acknow_no=0;
	 				
	 				rs=stmt.executeQuery(" select a.*,bm.*,c.clg_type,c.no_docs,c.doc_tot,c.doc_dest,c.ack_no,bank_cd,bk.bank_name,bmm.br_name from Cheque c,BankMaster bk,Acknowledge a,BranchMaster bm,BranchMaster bmm where  c.ctrl_no="+ackno+" and  doc_bs='B' and a.ack_no=c.ack_no  and  bm.br_code=a.doc_sou and bk.bank_code=substring(c.bank_cd,4,3) and bmm.br_code=c.doc_dest and c.ve_tml is null");

	 				
	 				
	 				if(rs.next())
	 	 			{
	 					acknow_no = rs.getInt("a.ack_no");
	 	 			    rs2=st.executeQuery("select sum(doc_tot+trn_amt) from Cheque where ack_no="+acknow_no+" and ve_tml is null ");
	 	 				try
	 	 				{
	 	 					rs2.next();
	 	 					ackrem=rs2.getDouble(1);
	 	 					System.out.println(ackrem+ " total amount  i ,.,,,,,, ");
	 	 					
	 	 				}catch(SQLException sql){
	 	 				    ackrem=0.0;
	 	 				    System.out.println("No Entries for Ack in Cheque");
	 	 				}
	 	 				
	 	 				ack.setAckNo(rs.getInt("ack_no"));
	 	 				ack.setAckDate(rs.getString("ack_date"));
	 	 				ack.setDocSource(rs.getInt("doc_sou"));
	 	 				ack.branchobject.setBranchName(rs.getString("bm.br_name"));
	 	 				ack.branchobject.setBranchACType(rs.getString("br_ac_type"));
	 	 				ack.branchobject.setBranchACNo(rs.getInt("br_ac_no"));
	 	 				ack.setTotal(rs.getDouble("tot_amt"));
	 	 				ack.setReconciled(rs.getString("reconciled"));
	 	 				ack.setAckEntered(ackrem);
	 	 				ack.setVeTml(rs.getString("ve_tml"));
	 	 				
	 	 				ack.setClgType(rs.getString("clg_type"));
	 	 				ack.branchobject.setBranchAddress(rs.getString("bmm.br_name"));
	 	 				ack.setNoDocs(rs.getInt("c.no_docs"));
	 	 				ack.setDocTotal(rs.getDouble("c.doc_tot"));
	 	 				ack.setBankCode(rs.getString("bank_cd"));
	 	 				ack.setBankName(rs.getString("bk.bank_name"));
	 	 				ack.setBranch(rs.getString("c.doc_dest"));
	 	 				ack.setDocSource(rs.getInt("doc_sou"));
	 	 				ack.branchobject.setBranchName(rs.getString("bm.br_name"));
	 	 				ack.branchobject.setBranchACType(rs.getString("br_ac_type"));
	 	 					//VeTml value
	 	 				
	 	 				//return ack;
	 	 			} else{
	 	 				
	 	 				rs=stmt.executeQuery(" select a.*,bm.*,c.clg_type,c.no_docs,c.doc_tot,c.doc_dest,c.ack_no,bank_cd,hd.bank_abbr,bmm.br_name from Cheque c,Acknowledge a,BranchMaster bm,BranchMaster bmm ,Head hd where  c.ctrl_no= "+ackno+" and  doc_bs='B' and a.ack_no=c.ack_no  and  bm.br_code=a.doc_sou and hd.bankcode=substring(c.bank_cd,9,1) and bmm.br_code=c.doc_dest and c.ve_tml is null");
	 	 				
	 	 				
	 	 				
	 	 				if(rs.next())
	 	 	 			{
	 	 					acknow_no = rs.getInt("ack_no");
	 	 	 			    rs2=st.executeQuery("select sum(doc_tot+trn_amt) from Cheque where ack_no="+acknow_no+" and ve_tml is null ");
	 	 	 				try
	 	 	 				{
	 	 	 					rs2.next();
	 	 	 					ackrem=rs2.getDouble(1);
	 	 	 					
	 	 	 				}catch(SQLException sql){
	 	 	 				    ackrem=0.0;
	 	 	 				    System.out.println("No Entries for Ack in Cheque");
	 	 	 				}
	 	 	 				
	 	 	 				ack.setAckNo(rs.getInt("ack_no"));
	 	 	 				ack.setAckDate(rs.getString("ack_date"));
	 	 	 				ack.setDocSource(rs.getInt("doc_sou"));
	 	 	 				ack.branchobject.setBranchName(rs.getString("bm.br_name"));
	 	 	 				ack.branchobject.setBranchACType(rs.getString("br_ac_type"));
	 	 	 				ack.branchobject.setBranchACNo(rs.getInt("br_ac_no"));
	 	 	 				ack.setTotal(rs.getDouble("tot_amt"));
	 	 	 				ack.setReconciled(rs.getString("reconciled"));
	 	 	 				ack.setAckEntered(ackrem);
	 	 	 				ack.setVeTml(rs.getString("ve_tml"));
	 	 	 				
	 	 	 				ack.setClgType(rs.getString("clg_type"));
	 	 	 				ack.branchobject.setBranchAddress(rs.getString("bmm.br_name"));
	 	 	 				ack.setNoDocs(rs.getInt("c.no_docs"));
	 	 	 				ack.setDocTotal(rs.getDouble("c.doc_tot"));
	 	 	 				ack.setBankCode(rs.getString("bank_cd"));
	 	 	 				ack.setBankName(rs.getString("bank_abbr"));
	 	 	 				ack.setBranch(rs.getString("c.doc_dest"));
	 	 	 				ack.setDocSource(rs.getInt("doc_sou"));
	 	 	 				ack.branchobject.setBranchName(rs.getString("bm.br_name"));
	 	 	 				ack.branchobject.setBranchACType(rs.getString("br_ac_type"));
	 	 	 					//VeTml value
	 	 	 				
	 	 	 				//return ack;
	 	 	 			}else{
	 	 					ack=null;
	 	 					return ack;
	 	 				}
	 	 				
	 	 				
	 	 			}
	 				
	 			}
	 			
	 			/*else
	 			    rs.getInt("ack_no");*/
	 		}catch(SQLException e){
	 			//ack.setAckNo(-1);
	 			//ack.setReconciled("T");
	 			//e.printStackTrace();
	 			
	 		}catch(Exception sql){
	 			
	 			throw new RecordsNotFoundException(); 
	 			
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e1) {
	 				e1.printStackTrace();
	 			}
	 		}
	 		return ack;
	 	}

	 	public int lateReturnCheques(long ctrlno,String clgdate)throws RecordNotInsertedException
	 	{
	 		int identify=0;
	 		Connection conn=null;
	 		try
	 		{
	 			conn=getConnection();
	 			PreparedStatement pstmt=conn.prepareStatement("Update Cheque set exp_clgdt='"+clgdate+"' where ctrl_no=?");
	 			pstmt.setLong(1,ctrlno);
	 			identify+=(pstmt.executeUpdate());
	 			//return identify;
	 			
	 		}catch(Exception sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return identify;
	 	}

	 	public ChequeDepositionObject[] getIdentifiedCheques(String clgdate,int clgno,int sendto)throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		ResultSet rs;
	 		ChequeDepositionObject[] cd=null;
	 		
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			rs=stmt.executeQuery("select * from Cheque where clg_date='"+clgdate+"' and clg_no="+clgno+" and send_to="+sendto+" and desp_ind not like 'T' order by ctrl_no");
	 			rs.last();
	 			if(rs.getRow()==0)
	 				{
	 					//throw new RecordsNotFoundException();
	 					
	 				cd=null;
	 				}
	 			
	 			else{
	 				cd=new ChequeDepositionObject[rs.getRow()];
	 			
	 			rs.beforeFirst();
	 			int i=0;
	 			while(rs.next())
	 			{
	 				
	 				cd[i]=new ChequeDepositionObject();
	 				cd[i].setControlNo(rs.getLong("ctrl_no"));
	 				cd[i].setExpectedClgDate(rs.getString((clgno == -1)?"clg_date":"qdp_dt"));
	 				cd[i].setExpClgNo(rs.getInt((clgno == -1)?"clg_no":"qdp_no"));
	 				cd[i].setSendTo(rs.getInt((clgno == -1)?"bank_cd":"cr_ac_no"));
	 				cd[i].setBranchName(rs.getString((clgno == -1)?"bank_name":"cr_ac_type"));
	 				if(clgno != -1)
	 					cd[i].setTranAmt( rs.getString("doc_bs").equalsIgnoreCase("B") ? rs.getDouble("doc_tot"):rs.getDouble("trn_amt") ) ;//rs.getDouble("trn_amt"));
	 				i++;
	 			}
	 			}  
	 			//return bcd;
	 		}catch(SQLException sql){
	 			//sql.printStackTrace();
	 			System.out.println("RecordsNotFoundException inside identifyCheque()......1");
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				System.out.println("RecordsNotFoundException inside identifyCheque()......2");
	 				e.printStackTrace();
	 			}
	 		}
	 		return cd;
	 	}


	 	public ChequeDepositionObject[] getIdentifiedCheques(int opt,int bank_cd,String clg_date , int send_to , int clg_no)throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		ChequeDepositionObject[] cd=null;
	 		try
	 		{
	 			ResultSet res=null;
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmt_multi = conn.createStatement();
	 			Statement stmt_bank=conn.createStatement();
	 			//ResultSet rs=stmt.executeQuery("select ctrl_no,clg_date,clg_no,bank_cd,bank_name from Cheque,BankMaster where clg_type='O' and desp_ind='F' and concat(right(clg_date,4),'-',mid(clg_date,locate('/',clg_date)+1,(locate('/',clg_date,4)-locate('/',clg_date)-1)),'-',left(clg_date,locate('/',clg_date)-1))<=date_format(sysdate(),'%Y-%m-%d') and bank_cd=bank_code order by bank_cd,concat(right(clg_date,4),'-',mid(clg_date,locate('/',clg_date)+1,(locate('/',clg_date,4)-locate('/',clg_date)-1)),'-',left(clg_date,locate('/',clg_date)-1)),clg_no");
	 			if(opt==1){   /// for all Bank
	 			ResultSet rs = stmt.executeQuery(" select ctrl_no,ack_no,clg_date,clg_no,clg_type,bank_cd,doc_bs,trn_amt,doc_tot,doc_dest,doc_sou,mult_cr from Cheque ch where desp_ind not like 'T'  and concat(right(clg_date,4),'-',mid(clg_date,locate('/',clg_date)+1,(locate('/',clg_date,4)-locate('/',clg_date)-1)),'-',left(clg_date,locate('/',clg_date)-1))<='"+Validations.convertYMD(clg_date)+"' and   (clg_type in ('O','D','C') || (clg_type='I' && doc_bs='B') || (clg_type='T' && doc_bs='B')) and  send_to = "+ send_to+ " and  clg_no = "+ clg_no +"   order by concat(right(substring(ve_dt_time,1,10),4),'-',mid(substring(ve_dt_time,1,10),locate('/',substring(ve_dt_time,1,10))+1,(locate('/',substring(ve_dt_time,1,10),4)-locate('/',substring(ve_dt_time,1,10))-1)),'-',left(substring(ve_dt_time,1,10),locate('/',substring(ve_dt_time,1,10))-1)) desc,ctrl_no ");
	 			
	 			rs.last();
	 			if(rs.getRow()==0)
	 			{
	 				cd=null;
	 			}
	 			
	 			cd=new ChequeDepositionObject[rs.getRow()];
	 			rs.beforeFirst();
	 			int i=0;
	 			while(rs.next())
	 			{
	 				int bank_code=rs.getInt("bank_cd");
					
	 				cd[i]=new ChequeDepositionObject();
	 				cd[i].setControlNo(rs.getInt("ctrl_no"));
	 				cd[i].setAckNo(rs.getInt("ack_no"));
	 				cd[i].setExpectedClgDate(rs.getString("clg_date"));
	 				cd[i].setExpClgNo(rs.getInt("clg_no"));
	 				cd[i].setSendTo(rs.getInt("bank_cd"));
	 				cd[i].setDoc_bs(rs.getString("doc_bs"));
	 				cd[i].setDocSource(rs.getInt("doc_sou"));
	 				cd[i].setBankCode(rs.getString("bank_cd"));
	 				cd[i].setClgType(rs.getString("clg_type"));
	 				
	 				if(rs.getString("doc_bs").equalsIgnoreCase("B"))
	 				    cd[i].setTranAmt(rs.getDouble("doc_tot"));
	 				else
	 				    cd[i].setTranAmt(rs.getDouble("trn_amt"));	
	 				
	 				cd[i].setDocDestination(rs.getInt("doc_dest"));
	 				
	 				//cd[i].setBranchName(rs.getString("bank_name")); 
	 				//cd[i].setMultiCredit(rs.getString("mult_cr"));
	 				
	 				
	 				i++;
	 				}
	 			}
	 			
	 			else if(opt==2)// for BankWise
	 			{
	 				//ResultSet rs=stmt.executeQuery("select c.bank_cd,b.bank_name,count(ctrl_no) as ch from Cheque c ,BankMaster b where desp_ind not like 'T' and concat(right(clg_date,4),'-',mid(clg_date,locate('/',clg_date)+1,(locate('/',clg_date,4)-locate('/',clg_date)-1)),'-',left(clg_date,locate('/',clg_date)-1))<= '"+Validations.convertYMD(clg_date)+"' and (clg_type='O' || (clg_type='I' && doc_bs='B') || (clg_type='T' && doc_bs='B')) and b.bank_code= substring(c.bank_cd,4,3)   group by c.bank_cd");
	 				
	 				ResultSet rs=stmt.executeQuery("select concat('000',b.bank_code,'000')as bank_cd , bank_name, count(*) as ch from Cheque, BankMaster b where  substring(bank_cd, 4,3) = b.bank_code and desp_ind not like 'T' and concat(right(clg_date,4),'-',mid(clg_date,locate('/',clg_date)+1,(locate('/',clg_date,4)-locate('/',clg_date)-1)),'-',left(clg_date,locate('/',clg_date)-1))<= '"+Validations.convertYMD(clg_date)+"' and (clg_type='O' || (clg_type='I' && doc_bs='B') || (clg_type='T' && doc_bs='B')) group by b.bank_code");
	 				
	 				rs.last();
	 				if(rs.getRow()==0)
	 					cd=null;
	 				
	 				cd=new ChequeDepositionObject[rs.getRow()];
	 				rs.beforeFirst();
	 				int i=0;
	 				while(rs.next())
	 				{
	 					cd[i]=new ChequeDepositionObject();
	 					cd[i].setBankCode(rs.getString("bank_cd"));
	 					cd[i].setBankName(rs.getString("bank_name"));
	 					cd[i].setAckNo(rs.getInt("ch"));
	 					i++;
	 				}
	 				
	 			}
	 			
	 			else if(opt==3)//For Branchwise
	 			{
	 				ResultSet rs=stmt.executeQuery("select c.send_to,b.br_code,b.br_name,count(ctrl_no) as ch from Cheque c ,BranchMaster b where desp_ind not like 'T' and concat(right(clg_date,4),'-',mid(clg_date,locate('/',clg_date)+1,(locate('/',clg_date,4)-locate('/',clg_date)-1)),'-',left(clg_date,locate('/',clg_date)-1))<='"+Validations.convertYMD(clg_date)+"' and (clg_type='O' || (clg_type='I' && doc_bs='B') || (clg_type='T' && doc_bs='B')) and b.br_code=c.send_to group by c.send_to");
	 				
	 					rs.last();
	 					if(rs.getRow()==0)
	 						cd=null;
	 					
	 					cd=new ChequeDepositionObject[rs.getRow()];
	 					rs.beforeFirst();
	 					int i=0;
	 					while(rs.next())
	 					{
	 						cd[i]=new ChequeDepositionObject();
	 						cd[i].setBankCode(rs.getString("c.send_to"));
	 						cd[i].setBranchName(rs.getString("b.br_name"));
	 						cd[i].setAckNo(rs.getInt("ch"));
	 						System.out.println("ch"+rs.getInt("ch"));
	 						i++;
	 					}
	 				
	 			}
	 			
	 			else if(opt==4)//For Desp Help
	 			{
	 				ResultSet rs=stmt.executeQuery("select clg_date, clg_no,send_to,count(ctrl_no)as docs,(if(trn_amt is null || trn_amt=0.00,sum(doc_tot),sum(trn_amt)))as amount from Cheque where clg_date is not null and desp_ind not like 'T'  and (clg_type='O' || (clg_type='I' && doc_bs='B') || (clg_type='T' && doc_bs='B'))  group by clg_no,clg_date order by  concat(right(clg_date,4),'-',mid(clg_date,locate('/',clg_date)+1,(locate('/',clg_date,4)-locate('/',clg_date)-1)),'-',left(clg_date,locate('/',clg_date)-1)) desc");
	 				if(rs.next())
	 				{
	 					rs.last();
	 					if(rs.getRow()==0)
	 						cd=null;
	 					
	 					cd=new ChequeDepositionObject[rs.getRow()];
	 					rs.beforeFirst();
	 					int i=0;
	 					while(rs.next())
	 					{
	 						cd[i]=new ChequeDepositionObject();
	 						cd[i].setDeTime(rs.getString("clg_date"));
	 						cd[i].setAckNo(rs.getInt("clg_no"));
	 						cd[i].setBankCode(rs.getString("send_to"));
	 						cd[i].setControlNo(rs.getInt("docs"));
	 						cd[i].setTranAmt(rs.getDouble("amount"));
	 						System.out.println("ch"+rs.getInt("docs"));
	 						i++;
	 					}
	 				}
	 				else
	 					cd=null;
	 			}else if(opt==5){
	 						System.out.println("in 1 the statement");
	 				ResultSet rs = stmt.executeQuery("select ctrl_no,ch.ack_no,clg_date,clg_no,bank_cd,doc_bs,sum(trn_amt) as trn_amt , sum(doc_tot) as doc_tot,doc_dest,mult_cr , ak.tot_amt from Cheque ch , Acknowledge ak where ch.ack_no =  ak.ack_no and desp_ind not like 'T'  and concat(right(clg_date,4),'-',mid(clg_date,locate('/',clg_date)+1,(locate('/',clg_date,4)-locate('/',clg_date)-1)),'-',left(clg_date,locate('/',clg_date)-1))<='"+Validations.convertYMD(clg_date)+"' and   (clg_type='O' || (clg_type='I' && doc_bs='B') || (clg_type='T' && doc_bs='B')) group by ch.ack_no order by doc_bs");
	 				System.out.println("in 2 the statement");
	 				
	 				ResultSet rs1 = stmt1.executeQuery("select ctrl_no,ack_no,clg_date,clg_no,bank_cd,doc_bs,trn_amt,doc_tot,doc_dest,mult_cr from Cheque ch where desp_ind not like 'T'  and concat(right(clg_date,4),'-',mid(clg_date,locate('/',clg_date)+1,(locate('/',clg_date,4)-locate('/',clg_date)-1)),'-',left(clg_date,locate('/',clg_date)-1))<='"+Validations.convertYMD(clg_date)+"' and   (clg_type='O' && doc_bs = 'S')   order by concat(right(substring(ve_dt_time,1,10),4),'-',mid(substring(ve_dt_time,1,10),locate('/',substring(ve_dt_time,1,10))+1,(locate('/',substring(ve_dt_time,1,10),4)-locate('/',substring(ve_dt_time,1,10))-1)),'-',left(substring(ve_dt_time,1,10),locate('/',substring(ve_dt_time,1,10))-1)) desc,ctrl_no");
	 				
	 				System.out.println("in 3 the statement");
	 	 			rs.last();
	 	 			rs1.last();
	 	 			if(rs.getRow()==0 && rs1.getRow() == 0)
	 	 				cd=null;
	 	 			
	 	 			cd=new ChequeDepositionObject[rs.getRow()+rs1.getRow()];
	 	 			System.out.println(cd.length+ " length of the cd object");
	 	 			rs.beforeFirst();
	 	 			rs1.beforeFirst();
	 	 			int i=0;
	 	 			while(rs.next())
		 	 			{
		 	 				
	 					
		 	 				cd[i]=new ChequeDepositionObject();
		 	 				
		 	 				cd[i].setControlNo(rs.getInt("ctrl_no"));
		 	 				cd[i].setAckNo(rs.getInt("ack_no"));
		 	 				cd[i].setExpectedClgDate(rs.getString("clg_date"));
		 	 				cd[i].setExpClgNo(rs.getInt("clg_no"));
		 	 				cd[i].setSendTo(rs.getInt("bank_cd"));
		 	 				cd[i].setDoc_bs(rs.getString("doc_bs"));
		 	 				
		 	 				cd[i].setTranAmt(rs.getDouble("doc_tot"));// used for the document Total 
		 	 				cd[i].setFineAmt(rs.getDouble("tot_amt"));// used for the Acknowledge Amount
		 	 					
	 	 				
		 	 				cd[i].setDocDestination(rs.getInt("doc_dest"));
	 	 				
		 	 				//cd[i].setBranchName(rs.getString("bank_name")); 
		 	 				//cd[i].setMultiCredit(rs.getString("mult_cr"));
	 	 				
	 	 				
	 	 				i++;
		 	 				
	 	 			}
	 	 			System.out.println("count of i"+ i);
	 	 			
	 	 			
	 	 			
	 	 			
	 	 			while(rs1.next())
		 	 			{
	 	 				
	 					
	 	 					cd[i]=new ChequeDepositionObject();
	 	 					cd[i].setControlNo(rs1.getInt("ctrl_no"));
	 	 					cd[i].setAckNo(rs1.getInt("ack_no"));
	 	 					cd[i].setExpectedClgDate(rs1.getString("clg_date"));
	 	 					cd[i].setExpClgNo(rs1.getInt("clg_no"));
	 	 					cd[i].setSendTo(rs1.getInt("bank_cd"));
	 	 					cd[i].setDoc_bs(rs1.getString("doc_bs"));
	 	 				
	 	 					if(rs1.getString("doc_bs").equalsIgnoreCase("B"))
	 	 						cd[i].setTranAmt(rs1.getDouble("doc_tot"));
	 	 					else
	 	 						cd[i].setTranAmt(rs1.getDouble("trn_amt"));	
	 	 				
	 	 					cd[i].setDocDestination(rs1.getInt("doc_dest"));
	 	 				
	 	 				
	 	 				
	 	 					i++;
	 	 					
	 	 			}
	 			}
	 			
	 			
	 			
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cd;
	 	}

	 	
	 	public void onPost(long ctrl_no,String tml,String user)
	 	{

	 		Connection conn=null;
	 		
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stat=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			PreparedStatement pstmt=null;
	 			
	 			try {
	 				comm = commonHome.create();
	 			} catch (CreateException e) {
	 				e.printStackTrace();
	 			}
	 			ResultSet rs=stmt.executeQuery("select * from Cheque where ctrl_no="+ctrl_no);
	 			System.out.println("out is"+ ctrl_no); 
	 			rs.next();
	 			
	 			String doc_sou = rs.getString("doc_sou");
	 			//if(rs.getString("clg_type").equals("O") && rs.getString("doc_bs").equals("S") && rs.getString("ret_norm").equals("N")) 
	 			{
	 				ResultSet rs1 = stat.executeQuery("select * from BranchMaster where br_code="+doc_sou);
	 				ResultSet ret = st.executeQuery("select gl_code,gl_type from GLKeyParam  where (code = 1) and ac_type = '1011001'");
	 				
	 				
	 				while(ret.next())
	 				{
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(getToday());
	 					trnobj.setGLType(ret.getString(2));
	 					trnobj.setGLCode(ret.getString(1));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(rs.getDouble("trn_amt"));
	 					trnobj.setCdind("C");
	 					trnobj.setAccType(rs1.getString("br_ac_type"));
	 					trnobj.setAccNo(rs1.getString("br_ac_no"));
	 					trnobj.setTrnType("");
	 					trnobj.setRefNo(rs.getInt(4));
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(tml);
	 					trnobj.setVid(user);
	 					trnobj.setVDate(getToday());
	 					System.out.println(34);
	 	
	 					comm.storeGLTransaction(trnobj);
	 					System.out.println(35);
	 				}
	 			}
	 			
	 			ResultSet ret = st.executeQuery("select * from GLKeyParam where ac_type='1011001' and code=12");
	 			if(ret.next())
	 			{
	 				System.out.println (1111);
	 				GLTransObject trnobj=new GLTransObject();
	 				//trnobj.setTrnDate(comm.getSysDate());
	 				System.out.println (22);
	 				trnobj.setGLType(ret.getString(5));
	 				trnobj.setGLCode(ret.getString(4));
	 				System.out.println (33);
	 				trnobj.setTrnMode("G");
	 				trnobj.setAmount(rs.getDouble("trn_amt"));
	 				System.out.println (44);
	 				trnobj.setCdind("D");
	 				trnobj.setAccType(rs.getString(21));
	 				trnobj.setAccNo(rs.getString(22));
	 				trnobj.setTrnType("");
	 				trnobj.setRefNo(rs.getInt(4));
	 				trnobj.setTrnSeq(0);
	 				trnobj.setVtml(tml);
	 				trnobj.setVid(user);
	 				
	 				comm.storeGLTransaction(trnobj);
	 				System.out.println (66);
	 			}
	 		}
	 	
	 		
	 			
	 			
	 			
	 	
	 	
	 	
	 		
	 				catch(SQLException sql){
	 					sql.printStackTrace();
	 					
	 					
	 				}catch(NullPointerException nl){
	 					
	 					sessionContext.setRollbackOnly();
	 					nl.printStackTrace();
	 					
	 				}
	 				finally{
	 					try {
	 						conn.close();
	 					} catch (SQLException e) {
	 						e.printStackTrace();
	 					}
	 				}
	 			}
	 	
	 	
	 	public boolean verifyOutStationCheque( ChequeDepositionObject cd) {
	 		
	 		boolean result = true;
	 		Connection conn = null ;
	 		try {
	 			
	 			conn = getConnection();	
	 			
	 			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stupdate=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					{
						System.out.println("inside GLTran");
						{
							
							ResultSet ret = st.executeQuery("select gl_code,gl_type from GLKeyParam  where (code = 9) and ac_type = '1011001'");
					
					
							while(ret.next())
							{
								GLTransObject trnobj=new GLTransObject();
								trnobj.setTrnDate(getToday());
								trnobj.setGLType(ret.getString(2));
								trnobj.setGLCode(ret.getString(1));
								trnobj.setTrnMode("G");
								trnobj.setAmount( cd.getTranAmt()  );
								trnobj.setCdind("D");
								trnobj.setAccType( cd.getCreditACType() );
								trnobj.setAccNo( Integer.toString(cd.getCreditACNo()) );
								trnobj.setTrnType("");
								trnobj.setRefNo( (int)cd.getControlNo());
								trnobj.setTrnSeq(0);
								trnobj.setVtml( cd.getVeTml());
								trnobj.setVid( cd.getVeUser() );
								trnobj.setVDate( cd.getVe_datetime() );
								System.out.println(34);
								
								System.out.println("before 1 store");
								comm.storeGLTransaction(trnobj);
								System.out.println(35);
							}
						}
				
						ResultSet ret = st.executeQuery("select * from GLKeyParam where ac_type='1011001' and code=10");
						if(ret.next())
						{
							GLTransObject trnobj=new GLTransObject();
							trnobj.setTrnDate(getToday());
							trnobj.setGLType(ret.getString(5));
							trnobj.setGLCode(ret.getString(4));
							trnobj.setTrnMode("G");
							trnobj.setAmount( cd.getTranAmt() );
							trnobj.setCdind("C");
							trnobj.setAccType( cd.getCreditACType());
							trnobj.setAccNo( Integer.toString(cd.getCreditACNo()) );
							trnobj.setTrnType("");
							trnobj.setRefNo( (int)cd.getControlNo() );
							trnobj.setTrnSeq(0);
							trnobj.setVtml(cd.getVeTml());
							trnobj.setVid(cd.getVeUser());
							trnobj.setVDate(cd.getVe_datetime());
							System.out.println("before 2 store");
							comm.storeGLTransaction(trnobj);
							System.out.println (66);
						}
						
						
						int ret_val = onVerification(  cd.getprev_ctrl_no(), cd.getVeTml(), cd.getVeUser(), cd.getVe_datetime() ) ;
						
						if ( ret_val <= 0 ){
							
							result = false;
							throw new SQLException();
							
						}
					}
	 			
	 			
	 			
	 		} catch(SQLException sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 					
	 		}catch(NullPointerException nl){
	 			
	 			sessionContext.setRollbackOnly();
	 			nl.printStackTrace();
	 			
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		
	 		
	 		
	 		
	 		return result;
	 	}
	 	
	 	public int onVerification(long ctrlno,String tml,String user,String ve_date)
	 	
	 	{
	 		Connection conn=null;
	 		int update=0;
	 		try
	 		{
	 			
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stat=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stupdate=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			PreparedStatement pstmt=null;
	 			
	 			try {
	 				comm = commonHome.create();
	 			} catch (CreateException e) {
	 				e.printStackTrace();
	 			}
	 		
	 			ResultSet rs=stmt.executeQuery("select * from Cheque where ctrl_no="+ctrlno+" and ve_tml is null");
	 			System.out.println("out is"+ ctrlno); 
	 			if(rs.next())
	 			{
	 				String de_date = rs.getString("de_dt_time");
	 				String de_user = rs.getString("de_user");
	 				String de_tml = rs.getString("de_tml");	
	 				String mult_cr = rs.getString("mult_cr");
	 				String comp_name = rs.getString("comp_name");
	 				String to_bounce = rs.getString("to_bounce");
	 				String credit_ac = rs.getString("cr_ac_type");
	 				int credit_no = rs.getInt("cr_ac_no");
	 				String clg_type = rs.getString("clg_type");
	 			
	 				String gl_type =null;
	 				//if(rs.getString("clg_type").equals("O") && rs.getString("doc_bs").equals("S") && rs.getString("ret_norm").equals("N")) 
	 			
	 				if (rs.getInt("doc_sou")!= rs.getInt("doc_dest"))
	 				{
	 					System.out.println("inside GLTran");
	 					{
	 						
	 						ResultSet ret = st.executeQuery("select gl_code,gl_type from GLKeyParam  where (code = 9) and ac_type = '1011001'");
	 				
	 				
	 						if(ret.next())
	 						{
	 							GLTransObject trnobj=new GLTransObject();
	 							trnobj.setTrnDate(getToday());
	 							trnobj.setGLType(ret.getString(2));
	 							trnobj.setGLCode(ret.getString(1));
	 							trnobj.setTrnMode("G");
	 							trnobj.setAmount(rs.getDouble("trn_amt"));
	 							trnobj.setCdind("C");
	 							trnobj.setAccType(rs.getString(21));
	 							trnobj.setAccNo(rs.getString(22));
	 							trnobj.setTrnType("");
	 							trnobj.setRefNo(rs.getInt(4));
	 							trnobj.setTrnSeq(0);
	 							trnobj.setVtml(tml);
	 							trnobj.setVid(user);
	 							trnobj.setVDate(ve_date);
	 							System.out.println(34);
	 							
	 							System.out.println("before 1 store");
	 							comm.storeGLTransaction(trnobj);
	 							System.out.println(35);
	 						}
	 					}
	 			
	 					ResultSet ret = st.executeQuery("select * from GLKeyParam where ac_type='1011001' and code=10");
	 					if(ret.next())
	 					{
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(getToday());
	 						trnobj.setGLType(ret.getString(5));
	 						trnobj.setGLCode(ret.getString(4));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("trn_amt"));
	 						trnobj.setCdind("D");
	 						trnobj.setAccType(rs.getString(21));
	 						trnobj.setAccNo(rs.getString(22));
	 						trnobj.setTrnType("");
	 						trnobj.setRefNo(rs.getInt(4));
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(tml);
	 						trnobj.setVid(user);
	 						trnobj.setVDate(ve_date);
	 						System.out.println("before 2 store");
	 						comm.storeGLTransaction(trnobj);
	 						System.out.println (66);
	 					}
	 				}
	 				else
	 				{
	 					String cr_ac_type = rs.getString("cr_ac_type");
	 					
	 					Statement po_stmt = conn.createStatement();
	 				
	 					if ((cr_ac_type.startsWith("1005") ||  cr_ac_type.startsWith("1003") ) )
	 					{
	 						try{
	 							
	 							Statement stmts = conn.createStatement();
	 							update = stmts.executeUpdate("update Cheque set  desp_ind='T',  post_ind='T',  ve_tml='"+ tml +"' , ve_user ='"+ user +"' ,  ve_dt_time = '"+ ve_date+"' where ctrl_no="+ctrlno );// 15/11/2011   before post_in='T'
	 						
	 						}catch (SQLException sql){
	 							
	 						}
	 					}
	 					else if (rs.getString("chqddpo").equalsIgnoreCase("C") && rs.getString("to_bounce").equalsIgnoreCase("F") )
	 					{	
	 						int trn_seq,cat_type;
	 						{
	 							AccountTransObject am = new AccountTransObject();
	 							am.setAccType(rs.getString("cr_ac_type"));
	 							am.setAccNo(rs.getInt("cr_ac_no"));
	 							am.setTransType("R");
	 							am.setTransAmount(rs.getDouble("trn_amt"));
	 							am.setTransMode("T");
	 							am.setTransSource(tml);
	 							am.setTransDate(getToday());
	 							
	 							am.setCdInd("C");
	 							am.setChqDDNo(rs.getInt("qdp_no"));
	 							am.setChqDDDate(rs.getString("qdp_dt"));
	 							am.setTransNarr("Ctrl No "+ctrlno);
	 							am.setRef_No(Integer.parseInt(String.valueOf(ctrlno)));
	 							am.setPayeeName(rs.getString("payee_name"));
	 							am.setCloseBal(rs.getDouble("trn_amt"));
	 							am.setLedgerPage(0);
	 							am.uv.setUserTml(tml);
	 							am.uv.setUserId(user);
	 							am.uv.setUserDate(de_date);
	 							am.uv.setVerTml(tml);
	 							am.uv.setVerId(user);
	 							am.uv.setVerDate(ve_date);
	 							am.setTransDate(getToday());
	 							
	 							comm.storeAccountTransaction(am);

	 							// GL goes here..for credit entry
	 							ResultSet	ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 							ret.next();
	 							trn_seq=ret.getInt(2);
	 							if(ret.getInt(1)==0)
	 								cat_type=1;
	 							else
	 								cat_type=2;
	 							ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 							if(ret.next())
	 							{
	 								GLTransObject trnobj=new GLTransObject();
	 								trnobj.setTrnDate(getToday());
	 								trnobj.setGLType(ret.getString(3));
	 								trnobj.setGLCode(ret.getString(1));
	 								trnobj.setTrnMode("T");
	 								trnobj.setAmount(rs.getDouble("trn_amt")*ret.getInt(2));
	 								trnobj.setCdind("C");
	 								trnobj.setAccType(rs.getString("cr_ac_type"));
	 								trnobj.setAccNo(String.valueOf(rs.getInt("cr_ac_no")));
	 								trnobj.setTrnType("R");
	 								trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 								trnobj.setTrnSeq(trn_seq);
	 								trnobj.setVtml(tml);
	 								trnobj.setVid(user);
	 				
	 								trnobj.setVDate(ve_date);
	 								System.out.println(34);
	 								
	 								System.out.println("before 3 store");
	 								//comm.storeGLTransaction(trnobj);System.out.println(35);
	 							}
	 						}
	 				
	 						{
	 							AccountTransObject am = new AccountTransObject();
	 							am.setAccType(rs.getString("dr_ac_type"));
	 							am.setAccNo(rs.getInt("dr_ac_no"));
	 							am.setTransType("P");
	 							am.setTransAmount(rs.getDouble("trn_amt"));
	 							am.setTransMode("T");
	 							am.setTransSource(tml);
	 							am.setTransDate(getToday());
	 				
	 							am.setCdInd("D");
	 							am.setChqDDNo(rs.getInt("qdp_no"));
	 							am.setChqDDDate(rs.getString("qdp_dt"));
	 							am.setTransNarr("Ctrl No "+ctrlno);
	 							am.setRef_No(Integer.parseInt(String.valueOf(ctrlno)));
	 							am.setPayeeName(rs.getString("payee_name"));
	 							am.setCloseBal(-rs.getDouble("trn_amt"));
	 							am.setLedgerPage(0);
	 							am.uv.setUserTml(tml);
	 							am.uv.setUserId(user);
	 							am.uv.setUserDate(de_date);
	 							am.uv.setVerTml(tml);
	 							am.uv.setVerId(user);
	 							am.uv.setVerDate(ve_date);
	 				
	 				
	 							comm.storeAccountTransaction(am);

	 							// GL goes here..for credit entry
	 							
	 							ResultSet	ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 							ret.next();
	 							
	 							trn_seq=ret.getInt(2);
	 							if(ret.getInt(1)==0)
	 								cat_type=1;
	 							else
	 								cat_type=2;
	 							ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 							if(ret.next())
	 							{
	 								GLTransObject trnobj=new GLTransObject();
	 								trnobj.setTrnDate(getToday());
	 								trnobj.setGLType(ret.getString(3));
	 								trnobj.setGLCode(ret.getString(1));
	 								trnobj.setTrnMode("T");
	 								trnobj.setAmount(rs.getDouble("trn_amt")*ret.getInt(2));
	 								trnobj.setCdind("D");
	 								trnobj.setAccType(rs.getString("dr_ac_type"));
	 								trnobj.setAccNo(String.valueOf(rs.getInt("dr_ac_no")));
	 								trnobj.setTrnType("P");
	 								trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 								trnobj.setTrnSeq(trn_seq);
	 								trnobj.setVtml(tml);
	 								
	 					
	 								trnobj.setVDate(ve_date);
	 								trnobj.setVid(user);System.out.println(34);
	 								
	 								System.out.println("before 4 store");
	 								//	comm.storeGLTransaction(trnobj);System.out.println(35);
	 							}

	 						}
	 				
	 						if(rs.getString("loan_ac_type")!=null && rs.getInt("loan_ac_no")>0)
	 						{
	 							String ac_ty ="";
	 							String ln_ty ="";
	 							String loan_no ="";
	 							int str = 0;
	 							double trn_amt = 0.0;
	 							//ResultSet resc=stupdate.executeQuery("select cr_ac_type,cr_ac_no,loan_ac_type, loan_ac_no ,moduleabbr from Cheque , Modules  where ctrl_no="+ctrlno+" and post_ind='F'and  modulecode = cr_ac_type");
	 							ResultSet resc=stupdate.executeQuery("select cr_ac_type,cr_ac_no,loan_ac_type, loan_ac_no ,m1.moduleabbr, m2.moduleabbr as abbrv from Cheque , Modules as m1 , Modules as m2 where ctrl_no = "+ctrlno+" and  m1.modulecode = cr_ac_type and m2.modulecode= loan_ac_type");
							 if(resc.next())
							 {
								 AccountTransObject am1 = new AccountTransObject();
								 ac_ty = resc.getString("moduleabbr");
								 str = resc.getInt(2);
								 ln_ty =resc.getString("abbrv");
								 loan_no =resc.getString("loan_ac_no");
								 
								 am1.setAccType(resc.getString(1));
								 am1.setAccNo(resc.getInt(2));
								 am1.setTransType("R");
								 
								 if(rs.getString("disc_ind").equalsIgnoreCase("T"))
									 trn_amt = rs.getDouble("trn_amt")- rs.getDouble("disc_amt");
										
								 else
									 trn_amt = rs.getDouble("trn_amt");
									
								 am1.setTransAmount(trn_amt);
									
								 am1.setTransMode("G");
								 am1.setTransSource(de_tml);
								 am1.setCdInd("D");
								 am1.setChqDDNo(rs.getInt("qdp_no"));
								 am1.setChqDDDate(rs.getString("qdp_dt"));
								 am1.setTransNarr("Ctrl No "+ctrlno );
								 am1.setRef_No(0);
								 am1.setPayeeName("Tran to "+ln_ty+" "+loan_no);
								 am1.setCloseBal(rs.getDouble("trn_amt"));
								 am1.setLedgerPage(0);
								 am1.uv.setUserTml(tml);
								 am1.uv.setUserId(user);
								 am1.uv.setUserDate(ve_date);
								 am1.uv.setVerTml(tml);
								 am1.setTransDate(getToday());
								 am1.uv.setVerId(user);
								 am1.uv.setVerDate(ve_date);
								 
								 comm.storeAccountTransaction(am1);
								
								 ResultSet	ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am1.getAccNo()+" and am.ac_type='"+am1.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
								 ret.next();
								 trn_seq=ret.getInt(2);
								 if(ret.getInt(1)==0)
									 cat_type=1;
								 else
									 cat_type=2;
								
								ResultSet ret1 = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am1.getAccType()+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
								if(ret1.next())
								{
									GLTransObject trnobj=new GLTransObject();
									trnobj.setTrnDate(getToday());
									trnobj.setGLType(ret1.getString(3));
									trnobj.setGLCode(ret1.getString(1));
									trnobj.setTrnMode("G");
									trnobj.setAmount(trn_amt*ret1.getInt(2));
									trnobj.setCdind("D");
									trnobj.setAccType(rs.getString("cr_ac_type"));
									trnobj.setAccNo(rs.getString("cr_ac_no"));
									trnobj.setTrnType("P");
									trnobj.setRefNo(0);
									trnobj.setTrnSeq(trn_seq);
									trnobj.setVtml(tml); //tml,String user,String ve_date
									trnobj.setVid(user);
									trnobj.setVDate(ve_date);
									System.out.println(34);

									comm.storeGLTransaction(trnobj);System.out.println(35);
								}
								
								LoanTransactionObject lob= new LoanTransactionObject();							    
							    lob.setAccType(rs.getString("loan_ac_type"));
							    lob.setAccNo(rs.getInt("loan_ac_no"));
							    lob.setTransactionDate(getToday());
							    lob.setTranType("R");
							    lob.setTransactionAmount(trn_amt);
							    lob.setTranMode("G");
							    lob.setTranSou(de_tml);
							    lob.setReferenceNo(Integer.parseInt(rs.getString("ctrl_no"))); 
							    lob.setTranNarration("Clg "+Integer.parseInt(rs.getString("ctrl_no"))+" "+"Trf "+ac_ty+" " + str );
							    lob.setCdind("C");
							    lob.uv.setUserTml(de_tml);
							    lob.uv.setUserId(de_user);
							    lob.uv.setUserDate(de_date);
							    lob.uv.setVerTml(tml);
							    lob.uv.setVerId(user) ;
							    lob.uv.setVerDate(ve_date);
							  
							  Integer trn_seq_object =null;
							  
				                try
								{//loanremote loanHome
				                    loanremote=loanHome.create();
				                
				                    trn_seq_object= (Integer) loanremote.recoverLoanAccount(lob);
				                    									 
				                    
								}catch (CreateException ece){
									
								}
				                catch(RecordNotUpdatedException rec_update)
								{
				                	sessionContext.setRollbackOnly();
				                	rec_update.printStackTrace();
				                	throw new SQLException();
								}
				                if(trn_seq_object!=null)
				                	update = trn_seq_object.intValue();
				                	
				                	//trn_seq = trn_seq_object.intValue();
								}

							 }
							 
								 				
	 				
	 					}//end Cheque
	 				
	 					else if(rs.getString("chqddpo").equalsIgnoreCase("P"))
	 					{
	 				
	 							//  for pay order
	 						
	 						
	 						if ( !rs.getString("cr_ac_type").startsWith("1012")) {	
	 						int trn_seq,cat_type;
	 						{
	 							AccountTransObject am = new AccountTransObject();
	 							am.setAccType(rs.getString("cr_ac_type"));
	 							am.setAccNo(rs.getInt("cr_ac_no"));
	 							am.setTransType("R");
	 							am.setTransAmount(rs.getDouble("trn_amt"));
	 							am.setTransMode("T");
	 							am.setTransSource(tml);
	 							am.setTransDate(getToday());
	 							
	 							am.setCdInd("C");
	 							am.setChqDDNo(rs.getInt("qdp_no"));
	 							am.setChqDDDate(rs.getString("qdp_dt"));
	 							am.setTransNarr("Ctrl No "+ctrlno);
	 							am.setRef_No(Integer.parseInt(String.valueOf(ctrlno)));
	 							am.setPayeeName(rs.getString("payee_name"));
	 							am.setCloseBal(rs.getDouble("trn_amt"));
	 							am.setLedgerPage(0);
	 							am.uv.setUserTml(tml);
	 							am.uv.setUserId(user);
	 							am.uv.setUserDate(de_date);
	 							am.uv.setVerTml(tml);
	 							am.uv.setVerId(user);
	 							am.uv.setVerDate(ve_date);
	 							am.setTransDate(getToday());
	 							
	 							comm.storeAccountTransaction(am);

	 							// GL goes here..for credit entry
	 							ResultSet	ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 							ret.next();
	 							trn_seq=ret.getInt(2);
	 							if(ret.getInt(1)==0)
	 								cat_type=1;
	 							else
	 								cat_type=2;
	 							ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 							if(ret.next())
	 							{
	 								GLTransObject trnobj=new GLTransObject();
	 								trnobj.setTrnDate(getToday());
	 								trnobj.setGLType(ret.getString(3));
	 								trnobj.setGLCode(ret.getString(1));
	 								trnobj.setTrnMode("T");
	 								trnobj.setAmount(rs.getDouble("trn_amt")*ret.getInt(2));
	 								trnobj.setCdind("C");
	 								trnobj.setAccType(rs.getString("cr_ac_type"));
	 								trnobj.setAccNo(String.valueOf(rs.getInt("cr_ac_no")));
	 								trnobj.setTrnType("R");
	 								trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 								trnobj.setTrnSeq(trn_seq);
	 								trnobj.setVtml(tml);
	 								trnobj.setVid(user);
	 				
	 								trnobj.setVDate(ve_date);
	 								System.out.println(34);
	 								
	 								System.out.println("before 3 store");
	 								//comm.storeGLTransaction(trnobj);System.out.println(35);
	 							}
	 						}
	 						
	 					} else  {
	 						
	 						
	 						GLTransObject trnobj=new GLTransObject();
		 						trnobj.setTrnDate(getToday());
		 						trnobj.setGLType( rs.getString("cr_ac_type"));
		 						trnobj.setGLCode( rs.getString("cr_ac_no"));
		 						trnobj.setTrnMode("T");
		 						trnobj.setAmount(rs.getDouble("trn_amt"));
		 						trnobj.setCdind("C");
		 						trnobj.setAccType(rs.getString(21));
		 						trnobj.setAccNo(rs.getString(22));
		 						trnobj.setTrnType("");
		 						trnobj.setRefNo(rs.getInt(4));
		 						trnobj.setTrnSeq(0);
		 						trnobj.setVtml(tml);
		 						trnobj.setVid(user);
		 						trnobj.setVDate(ve_date);
		 						System.out.println("before 2 store");
		 						comm.storeGLTransaction(trnobj);
		 						System.out.println (66);
	 						
	 					}
	 						
	 						ResultSet rs_po = po_stmt.executeQuery("select gl_code , gl_type from GLKeyParam where ac_type = '1016001' and code = 3");
	 						
	 						if(rs_po.next()){
	 							
	 							

	 	 						GLTransObject trnobj=new GLTransObject();
	 	 						trnobj.setTrnDate(getToday());
	 	 						trnobj.setGLType(rs_po.getString("gl_type"));
	 	 						trnobj.setGLCode(rs_po.getString("gl_code"));
	 	 						trnobj.setTrnMode("T");
	 	 						trnobj.setAmount(rs.getDouble("trn_amt"));
	 	 						trnobj.setCdind("D");
	 	 						trnobj.setAccType(rs.getString(21));
	 	 						trnobj.setAccNo(rs.getString(22));
	 	 						trnobj.setTrnType("");
	 	 						trnobj.setRefNo(rs.getInt(4));
	 	 						trnobj.setTrnSeq(0);
	 	 						trnobj.setVtml(tml);
	 	 						trnobj.setVid(user);
	 	 						trnobj.setVDate(ve_date);
	 	 						System.out.println("before 2 store");
	 	 						comm.storeGLTransaction(trnobj);
	 	 						System.out.println (66);
	 							
	 							
	 							
	 						}
	 						
	 						
	 						
	 						
	 				
	 				
	 				
	 					}
	 				}
	 				
	 				
	 				if(rs.getInt("doc_sou")!= rs.getInt("doc_dest"))	
	 						if(to_bounce.equalsIgnoreCase("T"))
	 						{
	 							
	 							// when it bounce again reverse entry into GL Code   106001 and 209001 
	 							
	 							ResultSet	rt = st.executeQuery(" select * from GLKeyParam where ac_type='1011001' and code=9");
	 							if(rt.next())
	 							{
	 								System.out.println (1111);
	 								GLTransObject trnobj=new GLTransObject();
	 								trnobj.setTrnDate(getToday());
	 								System.out.println (22);
	 								trnobj.setGLType(rt.getString(5));
	 								trnobj.setGLCode(rt.getString(4));
	 								System.out.println (33);
	 								trnobj.setTrnMode("G");
	 								trnobj.setAmount(rs.getDouble("trn_amt"));
	 								System.out.println (44);
	 								trnobj.setCdind("D");
	 								trnobj.setAccType(rs.getString(21));
	 								trnobj.setAccNo(rs.getString(22));
	 								trnobj.setTrnType("");
	 								trnobj.setRefNo(rs.getInt(4));
	 								trnobj.setTrnSeq(0);
	 								trnobj.setVtml(tml);
	 								trnobj.setVid(user);
	 								trnobj.setVDate(ve_date);
	 							
	 								comm.storeGLTransaction(trnobj);
	 								System.out.println (66);
	 							}
						
	 							ResultSet rt1 = st.executeQuery("select * from GLKeyParam where ac_type='1011001' and code=10");
	 							if(rt1.next())
	 							{
	 								System.out.println (1111);
	 								GLTransObject trnobj=new GLTransObject();
	 								trnobj.setTrnDate(getToday());
	 								System.out.println (22);
	 								trnobj.setGLType(rt1.getString(5));
	 								trnobj.setGLCode(rt1.getString(4));
	 								System.out.println (33);
	 								trnobj.setTrnMode("G");
	 								trnobj.setAmount(rs.getDouble("trn_amt"));
	 								System.out.println (44);
	 								trnobj.setCdind("C");
	 								trnobj.setAccType(rs.getString(21));
	 								trnobj.setAccNo(rs.getString(22));
	 								trnobj.setTrnType("");
	 								trnobj.setRefNo(rs.getInt(4));
	 								trnobj.setTrnSeq(0);
	 								trnobj.setVtml(tml);
	 								trnobj.setVid(user);
	 								trnobj.setVDate(ve_date);
	 								comm.storeGLTransaction(trnobj);
	 								System.out.println (66);
	 							}
	 			
	 							//if(rs.getString("cr_ac_type").toString().startsWith("1002")|| rs.getString("cr_ac_type").toString().startsWith("1007"))
	 							{	
	 							int trn_seq,cat_type;
	 							{
	 								AccountTransObject am = new AccountTransObject();
	 								am.setAccType(rs.getString("cr_ac_type"));
	 								am.setAccNo(rs.getInt("cr_ac_no"));
	 								am.setTransType("R");
	 								am.setTransAmount(rs.getDouble("trn_amt"));
	 								am.setTransMode("G");
	 								am.setTransSource(tml);
	 								am.setTransDate(getToday());
	 								
	 								am.setCdInd("C");
	 								am.setChqDDNo(rs.getInt("qdp_no"));
	 								am.setChqDDDate(rs.getString("qdp_dt"));
	 								am.setTransNarr("Ctrl No "+ctrlno);
	 								am.setRef_No(Integer.parseInt(String.valueOf(ctrlno)));
	 								am.setPayeeName(rs.getString("payee_name"));
	 								am.setCloseBal(rs.getDouble("trn_amt"));
	 								am.setLedgerPage(0);
	 								am.uv.setUserTml(tml);
	 								am.uv.setUserId(user);
	 								am.uv.setUserDate(de_date);
	 								am.uv.setVerTml(tml);
	 								am.uv.setVerId(user);
	 								am.uv.setVerDate(ve_date);
	 								am.setTransDate(getToday());
	 								
	 								comm.storeAccountTransaction(am);
	 								
	 								
	 								// GL goes here..for credit entry
	 								ResultSet ret = null;
	 								if(rs.getString("cr_ac_type").toString().startsWith("1002")|| rs.getString("cr_ac_type").toString().startsWith("1007"))
	 										ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 								else
	 										ret = stupdate.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
	 							
	 								ret.next();
	 								trn_seq=ret.getInt(2);
	 								if(ret.getInt(1)==0)
	 									cat_type=1;
	 								else
	 									cat_type=2;
	 								ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 								if(ret.next())
	 								{
	 									GLTransObject trnobj=new GLTransObject();
	 									trnobj.setTrnDate(getToday());
	 									trnobj.setGLType(ret.getString(3));
	 									trnobj.setGLCode(ret.getString(1));
	 									trnobj.setTrnMode("G");
	 									trnobj.setAmount(rs.getDouble("trn_amt")*ret.getInt(2));
	 									trnobj.setCdind("C");
	 									trnobj.setAccType(rs.getString("cr_ac_type"));
	 									trnobj.setAccNo(String.valueOf(rs.getInt("cr_ac_no")));
	 									trnobj.setTrnType("R");
	 									trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 									trnobj.setTrnSeq(trn_seq);
	 									trnobj.setVtml(tml);
	 									trnobj.setVid(user);
	 									
	 									trnobj.setVDate(ve_date);
	 									System.out.println(34);
	 								
	 									System.out.println("before 3 store");
	 								//	comm.storeGLTransaction(trnobj);System.out.println(35);
	 								}
	 							}
	 					
	 							{
	 								AccountTransObject am = new AccountTransObject();
	 							
	 								am.setAccType(rs.getString("cr_ac_type"));
	 								am.setAccNo(rs.getInt("cr_ac_no"));
	 							
	 								am.setTransType("P");
	 								am.setTransAmount(rs.getDouble("trn_amt"));
	 								am.setTransMode("G");
	 								am.setTransSource(tml);
	 								am.setTransDate(getToday());
	 								
	 								am.setCdInd("D");
	 								am.setChqDDNo(rs.getInt("qdp_no"));
	 								am.setChqDDDate(rs.getString("qdp_dt"));
	 								am.setTransNarr("Ctrl No "+ctrlno);
	 								am.setRef_No(Integer.parseInt(String.valueOf(ctrlno)));
	 								am.setPayeeName(rs.getString("payee_name"));
	 								am.setCloseBal(rs.getDouble("trn_amt"));
	 								am.setLedgerPage(0);
	 								am.uv.setUserTml(tml);
	 								am.uv.setUserId(user);
	 								am.uv.setUserDate(de_date);
	 								am.uv.setVerTml(tml);
	 								am.uv.setVerId(user);
	 								am.uv.setVerDate(ve_date);
	 								
	 				
	 								comm.storeAccountTransaction(am);

	 							// GL goes here..for credit entry
	 								
	 								ResultSet ret = null;
	 								if(rs.getString("cr_ac_type").toString().startsWith("1002")|| rs.getString("cr_ac_type").toString().startsWith("1007"))
	 										ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 								else
	 										ret = stupdate.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
	 							
	 								ret.next();
	 								
	 								trn_seq=ret.getInt(2);
	 								
	 								if(ret.getInt(1)==0)
	 									cat_type=1;
	 								else
	 									cat_type=2;
	 								ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 								if(ret.next())
	 								{
	 									GLTransObject trnobj=new GLTransObject();
	 									trnobj.setTrnDate(getToday());
	 									trnobj.setGLType(ret.getString(3));
	 									trnobj.setGLCode(ret.getString(1));
	 									trnobj.setTrnMode("G");
	 									trnobj.setAmount(rs.getDouble("trn_amt")*ret.getInt(2));
	 									trnobj.setCdind("D");
	 									trnobj.setAccType(rs.getString("dr_ac_type"));
	 									trnobj.setAccNo(String.valueOf(rs.getInt("dr_ac_no")));
	 									trnobj.setTrnType("P");
	 									trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 									trnobj.setTrnSeq(trn_seq);
	 									trnobj.setVtml(tml);
	 								
	 					
	 									trnobj.setVDate(ve_date);
	 									trnobj.setVid(user);System.out.println(34);
	 								
	 									System.out.println("before 4 store");
	 									//	comm.storeGLTransaction(trnobj);System.out.println(35);
	 								}
	 								
	 							}
	 					
	 							ResultSet reason =  st.executeQuery(" select acc_type, return_fine, reason, re.reason_cd from BounceFine, ReasonMaster , Reason re, Cheque ch where code = b_code and acc_type = '" + credit_ac+"' and code = re.reason_cd  and   ch.ctrl_no = re.ctrl_no and re.ctrl_no =" + ctrlno );
	 							int no_rows = 0;
	 							double fine_amount = 0.0;

	 							while(reason.next()){
	 						
	 								fine_amount = reason.getDouble("return_fine");
	 								
	 							}
	 					
	 							if(fine_amount > 0.0){
	 							{
	 									AccountTransObject am = new AccountTransObject();
								
	 									am.setAccType(rs.getString("cr_ac_type"));
	 									am.setAccNo(rs.getInt("cr_ac_no"));
								
	 									am.setTransType("P");
	 									am.setTransAmount(fine_amount);
	 									am.setTransMode("G");
	 									am.setTransSource(tml);
	 									am.setTransDate(getToday());
					
	 									am.setCdInd("D");
	 									am.setChqDDNo(rs.getInt("qdp_no"));
	 									am.setChqDDDate(rs.getString("qdp_dt"));
	 									am.setTransNarr("Ctrl No "+ctrlno + " due to Bounce");
	 									am.setRef_No(Integer.parseInt(String.valueOf(ctrlno)));
	 									am.setPayeeName(rs.getString("payee_name"));
	 									am.setCloseBal(fine_amount);
	 									am.setLedgerPage(0);
	 									am.uv.setUserTml(tml);
	 									am.uv.setUserId(user);
	 									am.uv.setUserDate(de_date);
	 									am.uv.setVerTml(tml);
	 									am.uv.setVerId(user);
	 									am.uv.setVerDate(ve_date);
					
					
	 									comm.storeAccountTransaction(am);

								// GL goes here..for credit entry
								
	 									ResultSet ret = null;
	 	 								if(rs.getString("cr_ac_type").toString().startsWith("1002")|| rs.getString("cr_ac_type").toString().startsWith("1007"))
	 	 										ret = stupdate.executeQuery("select att.ac_type,am.last_tr_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 	 								else
	 	 										ret = stupdate.executeQuery("select od.ac_type,trn_seq from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
	 									
	 									ret.next();
								
	 									trn_seq=ret.getInt(2);
	 									if(ret.getInt(1)==0)
	 										cat_type=1;
	 									else
	 										cat_type=2;
	 									ret = stupdate.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = '"+am.getAccType()+"' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 									if(ret.next())
	 									{
	 										GLTransObject trnobj=new GLTransObject();
	 										trnobj.setTrnDate(getToday());
	 										trnobj.setGLType(ret.getString(3));
	 										trnobj.setGLCode(ret.getString(1));
	 										trnobj.setTrnMode("T");
	 										trnobj.setAmount(rs.getDouble("trn_amt")*ret.getInt(2));
	 										trnobj.setCdind("D");
	 										trnobj.setAccType(rs.getString("dr_ac_type"));
	 										trnobj.setAccNo(String.valueOf(rs.getInt("dr_ac_no")));
	 										trnobj.setTrnType("P");
	 										trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno)));
	 										trnobj.setTrnSeq(trn_seq);
	 										trnobj.setVtml(tml);
									
						
	 										trnobj.setVDate(ve_date);
	 										trnobj.setVid(user);System.out.println(34);
									
	 										System.out.println("before 4 store");
	 										comm.storeGLTransaction(trnobj);System.out.println(35);
	 									}

	 									ResultSet gl = st.executeQuery("select * from GLKeyParam where ac_type='1011001' and code=7");
	 									if(gl.next())
	 									{
	 										System.out.println (1111);
	 										
	 										GLTransObject trnobj=new GLTransObject();
	 										trnobj.setTrnDate(getToday());
	 										System.out.println (22);
	 										trnobj.setGLType(gl.getString("gl_type"));
	 										trnobj.setGLCode(gl.getString("gl_code"));
	 										System.out.println (33);
	 										trnobj.setTrnMode("G");
	 										trnobj.setAmount(fine_amount);
	 										System.out.println (44);
	 										trnobj.setCdind("C");
	 										trnobj.setAccType(credit_ac);
	 										trnobj.setAccNo(Integer.toString(credit_no));
	 										trnobj.setTrnType("");
	 										trnobj.setRefNo(rs.getInt(4));
	 										trnobj.setTrnSeq(0);
	 										trnobj.setVtml(tml);
	 										trnobj.setVid(user);
	 										trnobj.setVDate(ve_date);
	 										comm.storeGLTransaction(trnobj);
	 										System.out.println (66);
	 									}	
	 									
	 								}
	 								
	 								//update = stat.executeUpdate("update Cheque set  desp_ind='T',  post_ind='T',  ve_tml='"+ tml +"' , ve_user ='"+ user +"' ,  ve_dt_time = '"+ ve_date+"' where ctrl_no="+ctrlno );
	 								//update=stat.executeUpdate("update Reason set ve_tml='"+tml+"',ve_user='"+user+"',ve_dt_time = '"+ve_date+"' where ctrl_no="+ctrlno+" and ve_tml is null");
	 							}
	 							stat.addBatch("update Cheque set  desp_ind='T',  post_ind='T',  ve_tml='"+ tml +"' , ve_user ='"+ user +"' ,  ve_dt_time = '"+ ve_date+"' where ctrl_no="+ctrlno);
									stat.addBatch("update Reason set ve_tml='"+tml+"',ve_user='"+user+"',ve_dt_time = '"+ve_date+"' where ctrl_no="+ctrlno+" and ve_tml is null");
									int[] updated = stat.executeBatch();
									
									update=stat.executeUpdate("update Cheque set ve_tml='"+tml+"' ,ve_user='"+user+"',ve_dt_time='"+ve_date+"' where ctrl_no="+ctrlno+" ");
						 			
					 				System.out.println("update"+" "+update);
	 						}
	 							
	 						}
	 				
	 				if ( clg_type.equalsIgnoreCase("S")){
	 					
	 					
	 					
	 				}
	 				update=stat.executeUpdate("update Cheque set ve_tml='"+tml+"' ,ve_user='"+user+"',ve_dt_time='"+ve_date+"' where ctrl_no="+ctrlno+" ");
		 			
	 				System.out.println("update"+" "+update);
	 			}
	 				
	 			}
	 		
	 		catch(SQLException sql){
	 			sql.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 					
	 		}catch(NullPointerException nl){
	 			
	 			sessionContext.setRollbackOnly();
	 			nl.printStackTrace();
	 			
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return update;
	 	}
	 	
	 	
	 	
	 	public void onReconcilation(long ctrlno,String tml,String user)
	 	
	 	{
	 		Connection conn=null;
	 		
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stat=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			PreparedStatement pstmt=null;
	 			
	 			try {
	 				comm = commonHome.create();
	 			} catch (CreateException e) {
	 				e.printStackTrace();
	 			}
	 		
	 			ResultSet rs=stmt.executeQuery("select * from Cheque where ctrl_no="+ctrlno);
	 			System.out.println("out is"+ ctrlno); 
	 			rs.next();
	 			
	 			
	 			//if(rs.getString("clg_type").equals("O") && rs.getString("doc_bs").equals("S") && rs.getString("ret_norm").equals("N")) 
	 			{
	 				
	 				ResultSet ret = st.executeQuery("select gl_code,gl_type from GLKeyParam  where (code = 9) and ac_type = '1011001'");
	 				while(ret.next())
	 				{
	 					GLTransObject trnobj=new GLTransObject();
	 					trnobj.setTrnDate(getToday());
	 					trnobj.setGLType(ret.getString(2));
	 					trnobj.setGLCode(ret.getString(1));
	 					trnobj.setTrnMode("G");
	 					trnobj.setAmount(rs.getDouble("trn_amt"));
	 					trnobj.setCdind("C");
	 					trnobj.setAccType("");
	 					trnobj.setAccNo("");
	 					trnobj.setTrnType("");
	 					trnobj.setRefNo(0);
	 					trnobj.setTrnSeq(0);
	 					trnobj.setVtml(tml);
	 					trnobj.setVid(user);
	 					System.out.println(34);
	 	
	 					comm.storeGLTransaction(trnobj);
	 					System.out.println(35);
	 				}
	 			}
	 			
	 			ResultSet ret = st.executeQuery("select * from GLKeyParam where ac_type='1011001' and code=10");
	 			if(ret.next())
	 			{
	 				System.out.println (1111);
	 				GLTransObject trnobj=new GLTransObject();
	 				//trnobj.setTrnDate(comm.getSysDate());
	 				System.out.println (22);
	 				trnobj.setGLType("GL");
	 				trnobj.setGLCode(ret.getString(4));
	 				System.out.println (33);
	 				trnobj.setTrnMode("G");
	 				trnobj.setAmount(rs.getDouble("trn_amt"));
	 				System.out.println (44);
	 				trnobj.setCdind("D");
	 				trnobj.setAccType("");
	 				System.out.println (55);
	 				trnobj.setAccNo("");
	 				trnobj.setTrnType("P");
	 				trnobj.setRefNo(0);
	 				trnobj.setTrnSeq(0);
	 				trnobj.setVtml(tml);
	 				trnobj.setVid(user);
	 				
	 				comm.storeGLTransaction(trnobj);
	 				System.out.println (66);
	 			}
	 		}
	 	
	 		
	 				catch(SQLException sql){
	 					sql.printStackTrace();
	 					sessionContext.setRollbackOnly();
	 					
	 				}catch(NullPointerException nl){
	 					
	 					sessionContext.setRollbackOnly();
	 					nl.printStackTrace();
	 					sessionContext.setRollbackOnly();
	 				}
	 				finally{
	 					try {
	 						conn.close();
	 					} catch (SQLException e) {
	 						e.printStackTrace();
	 					}
	 				}
	 			}
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	public int dispatchCheques(long[] ctrlno,String tml,String user,String date,String date_on)throws RecordNotUpdatedException
	 	{
	 		Connection conn=null;
	 		
	 		int dispatch=0;
	 		ResultSet result=null;
	 		String de_date=null,de_user=null,de_tml=null;
	 		String acc_type=null;
	 		int acc_no=0;
	 		ResultSet rs1=null,rr=null,ret1=null,rss=null,ret=null;
	 		boolean boolean_flag=true;
	 		
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stat=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stup=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmt3 =conn.createStatement();
	 			
	 			PreparedStatement pstmt=null;
	 			int cid=0,trn_seq=0,custtype=0,cat_type=0;
	 			
	 			System.out.println ("ctrlno.length =="+ctrlno.length);
	 			try {
	 				comm = commonHome.create();
	 			} catch (CreateException e) {
	 				e.printStackTrace();
	 			}
	 			for(int i=0;i<ctrlno.length;i++)
	 			{
	 				ResultSet rs=stmt.executeQuery("select * from Cheque where ctrl_no="+ctrlno[i]);
	 				if(rs.next())
	 				{
	 					de_tml=rs.getString("de_tml");
	 					de_user=rs.getString("de_user");
	 					de_date=rs.getString("de_dt_time");
	 			
	 					pstmt=conn.prepareStatement("Update Cheque set desp_ind='T' where ctrl_no="+ctrlno[i]);
	 					dispatch+=(pstmt.executeUpdate());
	 					System.out.println ("dispatch == "+dispatch+"  "+i);
	 				
	 				//the gl code comes next
	 				
	 				//if check deposited in head office(i.e bank code in head table) dispatching to apex bank
	 				
	 				if(((rs.getString("clg_type").equals("O")&& rs.getInt("prev_ctrl_no")==0)||rs.getString("clg_type").equals("T")) && rs.getString("doc_bs").equals("S") && rs.getString("ret_norm").equals("N")) 
	 				{
	 					//credit to Clg_Out gl
	 					
	 					 ret = st.executeQuery(" select gl_code,gl_type from GLKeyParam  where (code =3 ) and ac_type = '1011001'");
	 					while(ret.next())
	 					{
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(date_on);
	 						trnobj.setGLType(ret.getString(2));
	 						trnobj.setGLCode(ret.getString(1));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("trn_amt"));
	 						trnobj.setCdind("C");
	 						trnobj.setAccType(rs.getString(21));
	 						trnobj.setAccNo(rs.getString(22));
	 						trnobj.setTrnType("");
	 						trnobj.setRefNo(rs.getInt(4));
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(tml);
	 						trnobj.setVid(user);
	 						trnobj.setVDate(date);
	 						System.out.println(34);
	 		
	 						comm.storeGLTransaction(trnobj);
	 						System.out.println(35);
	 					}
	 					
	 					//debit to apex bank account i.e 9999 account
	 					{
	 						System.out.println(" sending Bank"+rs.getInt("send_to"));
	 						
	 						ret = stmt2.executeQuery("select br_ac_type,br_ac_no from BranchMaster where br_code ="+rs.getInt("send_to"));
	 		 				
	 		 				if(ret.next())
	 		 				{
	 		 					acc_type=ret.getString("br_ac_type");
	 		 					acc_no=ret.getInt("br_ac_no");
	 		 					
	 		 				}
	 						if( !(acc_type.startsWith("1012")) )
	 						{
	 							boolean_flag=false;
	 							AccountTransObject am=new AccountTransObject();	
	 						System.out.println("chatti");
	 						am.setAccType(acc_type);
	 						System.out.println("acc type is"+ acc_type);
	 						am.setAccNo(acc_no);
	 						am.setTransType("P");
	 						am.setTransAmount(rs.getDouble("trn_amt"));
	 						am.setTransMode("G");
	 						am.setTransDate(date_on);
	 						am.setTransSource(de_tml);
	 						am.setCdInd("D");
	 						am.setChqDDNo(rs.getInt("qdp_no"));
	 						am.setChqDDDate(rs.getString("qdp_dt"));
	 						am.setTransNarr("clg No "+rs.getInt("ctrl_no"));
	 						//am.setRef_No("");
	 						am.setPayeeName("");
	 						am.setCloseBal(rs.getDouble("trn_amt"));
	 						am.setLedgerPage(0);
	 						am.uv.setUserTml(de_tml);
	 						am.uv.setUserDate(de_date);
	 						am.uv.setUserId(de_user);
	 						
	 						am.uv.setVerTml(tml);
	 						am.uv.setVerId(user);
	 						am.uv.setVerDate(date);
	 		
	 						comm.storeAccountTransaction(am);
	 						rs1 = stup.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+acc_no+" and am.ac_type='"+acc_type+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
								if(rs1.next())
								{
									if(ret.next())
				 					{
				 					trn_seq=ret.getInt(2);
				 					cid	=ret.getInt("cid");
				 				System.out.println("cid is"+ cid);
				 					}
				 					 rr= st.executeQuery("select custtype from CustomerMaster where cid="+ cid );
				 				if(	rr.next())
				 				{
				 					System.out.println("custtype is"+ rr.getInt("custtype"));	
				 				 custtype=rr.getInt("custtype");
				 					if(custtype==0)
				 						cat_type=1;
				 					else
				 						cat_type=2;
				 				}
								}
							
								ret1= stup.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+acc_type+" and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				 			
	 						
	 						}
	 						if(boolean_flag)
			 				{
			 					
			 					System.out.println("111111111111111");
	 		 					GLTransObject trnobj=new GLTransObject();
	 		 					trnobj.setTrnDate(date_on);
	 		 					trnobj.setGLType(acc_type);
			 					trnobj.setGLCode(String.valueOf(acc_no));		 			 					
	 		 					trnobj.setTrnMode("G");
	 		 					trnobj.setAmount(rs.getDouble("trn_amt"));
	 		 					trnobj.setCdind("D");
	 		 					trnobj.setAccType(acc_type);
	 		 					trnobj.setAccNo(String.valueOf(acc_no));
	 		 					trnobj.setTrnType("P");
	 		 					System.out.println("ctrl_no"+ctrlno[i]);
	 		 					trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno[i])));
	 		 					trnobj.setTrnSeq(trn_seq);
	 		 					trnobj.setVtml(tml);
	 		 					trnobj.setVid(user);
	 		 					trnobj.setVDate(date);
	 		 					
	 		 					System.out.println(34);

	 		 					comm.storeGLTransaction(trnobj);System.out.println(35);
	 		 					
	 		 					System.out.println("22222222222222222");
	 		 					

			 					}
			 					else
			 					{			
			 						if(ret1.next())
			 						{
			 							System.out.println("111111111111111");
			 							GLTransObject trnobj=new GLTransObject();
			 							trnobj.setTrnDate(date_on); 		 									
			 							trnobj.setGLType(ret1.getString("gl_type"));
			 							trnobj.setGLCode(ret1.getString("gl_code")); 		 					
			 							trnobj.setTrnMode("G");
			 							trnobj.setAmount(rs.getDouble("trn_amt"));
			 							trnobj.setCdind("D");
			 							trnobj.setAccType(acc_type);
			 							trnobj.setAccNo(String.valueOf(acc_no));
			 							trnobj.setTrnType("P");
			 							System.out.println("ctrl_no"+ctrlno[i]);
			 							trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno[i])));
			 							trnobj.setTrnSeq(trn_seq);
			 							trnobj.setVtml(tml);
			 							trnobj.setVid(user);
			 							trnobj.setVDate(date);
	 		 					
			 							System.out.println(34);

			 							comm.storeGLTransaction(trnobj);System.out.println(35);
	 		 					
			 							System.out.println("22222222222222222");
	 		 					
			 						}
			 					}

	 					}
	 				
	 				ResultSet	rt = st.executeQuery("select * from GLKeyParam where ac_type='1011001' and code=9");
	 					if(rt.next())
	 					{
	 						System.out.println (1111);
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(date_on);
	 						System.out.println (22);
	 						trnobj.setGLType(rt.getString(5));
	 						trnobj.setGLCode(rt.getString(4));
	 						System.out.println (33);
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("trn_amt"));
	 						System.out.println (44);
	 						trnobj.setCdind("D");
	 						trnobj.setAccType(rs.getString(21));
	 						trnobj.setAccNo(rs.getString(22));
	 						trnobj.setTrnType("");
	 						trnobj.setRefNo(rs.getInt(4));
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(tml);
	 						trnobj.setVid(user);
	 						trnobj.setVDate(date);
	 						
	 						comm.storeGLTransaction(trnobj);
	 						System.out.println (66);
	 					}
	 					
	 					ret = st.executeQuery("select * from GLKeyParam where ac_type='1011001' and code=10");
	 					if(ret.next())
	 					{
	 						System.out.println (1111);
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(date_on);
	 						System.out.println (22);
	 						trnobj.setGLType(ret.getString(5));
	 						trnobj.setGLCode(ret.getString(4));
	 						System.out.println (33);
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount( rs.getDouble("trn_amt") );
	 						System.out.println (44);
	 						trnobj.setCdind("C");
	 						trnobj.setAccType(rs.getString(21));
	 						trnobj.setAccNo(rs.getString(22));
	 						trnobj.setTrnType("");
	 						trnobj.setRefNo(rs.getInt(4));
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(tml);
	 						trnobj.setVid(user);
	 						trnobj.setVDate(date);
	 						comm.storeGLTransaction(trnobj);
	 						System.out.println (66);
	 					}

	 				}
	 				//if check is bundled entry recieved from branches dispatching to apex bank
	 				else if(((rs.getString("clg_type").equals("O") ) && rs.getString("doc_bs").equals("B"))||(rs.getString("clg_type").equals("T") && rs.getString("doc_bs").equals("B")))
	 				{
	 					
	 					AccountTransObject am = new AccountTransObject();
	 					
	 				//	Credit branch C/A 
	 					
	 					
	 					ret = st.executeQuery("select br_ac_type,br_ac_no from BranchMaster where br_code ="+rs.getInt("doc_sou"));
			 				
			 				if(ret.next())
			 				{
			 					acc_type=ret.getString("br_ac_type");
			 					acc_no=ret.getInt("br_ac_no");
			 					
			 				}
							
							{
								 am=new AccountTransObject();	
							
							am.setAccType(acc_type);
							System.out.println("acc type is"+ acc_type);
							am.setAccNo(acc_no);
							am.setTransType("R");
							am.setTransAmount(rs.getDouble("doc_tot"));
							am.setTransMode("G");
							am.setTransDate(date_on);
							am.setTransSource(de_tml);
							am.setCdInd("C");
							am.setChqDDNo(rs.getInt("qdp_no"));
							am.setChqDDDate(rs.getString("qdp_dt"));
							am.setTransNarr("Ack No "+rs.getInt("ack_no"));
							//am.setRef_No("");
							am.setPayeeName(rs.getString("payee_name"));
							am.setCloseBal(rs.getDouble("doc_tot"));
							am.setLedgerPage(0);
							am.uv.setUserTml(de_tml);
							am.uv.setUserDate(de_date);
							am.uv.setUserId(de_user);
							
							am.uv.setVerTml(tml);
							am.uv.setVerId(user);
							am.uv.setVerDate(date);
			
							comm.storeAccountTransaction(am);
							}
							
							 					
						
							ret = stup.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+acc_no+" and am.ac_type='"+acc_type+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
							if(ret.next())
							{
								trn_seq=ret.getInt(2);
								cid	=ret.getInt("cid");
								System.out.println("cid is"+ cid);
							}
							rr= st.executeQuery("select custtype from CustomerMaster where cid="+ cid );
							if(	rr.next())
							{
								System.out.println("custtype is"+ rr.getInt("custtype"));	
								custtype=rr.getInt("custtype");
								if(custtype==0)
									cat_type=1;
								else
									cat_type=2;
							}
								
							
							ret1= stup.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+acc_type+" and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				 				
							if(ret1.next())
							{
								System.out.println("111111111111111");
								GLTransObject trnobj=new GLTransObject();
			 					trnobj.setTrnDate(date_on);
			 					trnobj.setGLType(ret1.getString("gl_type"));
			 					trnobj.setGLCode(ret1.getString("gl_code"));
			 					trnobj.setTrnMode("G");
			 					trnobj.setAmount(rs.getDouble("doc_tot"));
			 					trnobj.setCdind("C");
			 					trnobj.setAccType(acc_type);
			 					trnobj.setAccNo(String.valueOf(acc_no));
			 					trnobj.setTrnType("P");
			 					trnobj.setRefNo(rs.getInt("ack_no"));
			 					trnobj.setTrnSeq(trn_seq);
			 					trnobj.setVtml(tml);
			 					trnobj.setVid(user);
			 					trnobj.setVDate(date);
			 					
			 					System.out.println(34);

			 					comm.storeGLTransaction(trnobj);System.out.println(35);
			 					
			 					System.out.println("22222222222222222");
			 					
							}
			 					
							
//	 						Dr. CLRG_OWBR
							ret = st.executeQuery(" select ac_type,gl_type,gl_code from GLKeyParam  where ac_type='1011001' and code =11");
							if(ret.next())
							{
								GLTransObject trnobj=new GLTransObject();
								trnobj.setTrnDate(date_on);
								trnobj.setGLType(ret.getString("gl_type"));
								trnobj.setGLCode(ret.getString("gl_code"));
								trnobj.setTrnMode("G");
								trnobj.setAmount(rs.getDouble("doc_tot"));
								trnobj.setCdind("D");
								trnobj.setAccType(acc_type);
								trnobj.setAccNo(String.valueOf(acc_no));
								trnobj.setTrnType("");
								trnobj.setRefNo(0);
								trnobj.setTrnSeq(0);
								trnobj.setVtml(tml);
								trnobj.setVid(user);
								trnobj.setVDate(date);
								System.out.println(34);
	 	
								comm.storeGLTransaction(trnobj);
								System.out.println(35);
							}
	 					
	 					 					
							//credit to Clg_InBr gl
							System.out.println ("doc source == "+rs.getInt("doc_sou"));
							
							ret = st.executeQuery("select gl_code,gl_type from GLKeyParam  where code = 2 and ac_type = '1011001'");
							if(ret.next())
							{
								System.out.println (100);
								GLTransObject trnobj=new GLTransObject();
								trnobj.setTrnDate(date_on);
								System.out.println (200);
								trnobj.setGLType(ret.getString(2));
								trnobj.setGLCode(ret.getString(1));
								System.out.println (300);
								trnobj.setTrnMode("G");
								trnobj.setAmount(rs.getDouble("doc_tot"));
								trnobj.setCdind("C");
								trnobj.setAccType(rs.getString(21));
								trnobj.setAccNo(rs.getString(22));
								trnobj.setTrnType("");
								trnobj.setRefNo(0);
								trnobj.setTrnSeq(0);
								trnobj.setVtml(tml);
								trnobj.setVid(user);
								trnobj.setVDate(date);
								System.out.println(34);
	 		
								comm.storeGLTransaction(trnobj);
								System.out.println(35);
							}
	 					
	 					//debit to apex bank account i.e 9999 account
	 					

							ret = stmt2.executeQuery("select br_ac_type,br_ac_no from BranchMaster where br_code ="+rs.getInt("send_to"));
		
							if(ret.next())
							{
								acc_type=ret.getString("br_ac_type");
								acc_no=ret.getInt("br_ac_no");
			
							}
		
							if(!acc_type.startsWith("1012"))
							{
								boolean_flag=false;	 
								am=new AccountTransObject();	
								System.out.println("chatti");
								am.setAccType(acc_type);
								System.out.println("acc type is"+ acc_type);
								am.setAccNo(acc_no);
								am.setTransType("P");
								am.setTransAmount(rs.getDouble("doc_tot"));
								am.setTransMode("G");
								am.setTransDate(date_on);
								am.setTransSource(de_tml);
								am.setCdInd("D");
								am.setChqDDNo(rs.getInt("qdp_no"));
								am.setChqDDDate(rs.getString("qdp_dt"));
								am.setTransNarr("Ack No "+rs.getInt("ack_no"));
								
								//		am.setRef_No("");
								
								am.setPayeeName(rs.getString("payee_name"));
								am.setCloseBal(rs.getDouble("doc_tot"));
								am.setLedgerPage(0);
								am.uv.setUserTml(de_tml);
								am.uv.setUserDate(de_date);
								am.uv.setUserId(de_user);
								
								am.uv.setVerTml(tml);
								am.uv.setVerId(user);
								am.uv.setVerDate(date);
								
								comm.storeAccountTransaction(am);
								
								rs1 = stup.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+acc_no+" and am.ac_type='"+acc_type+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
								if(rs1.next())
								{
									if(ret.next())
									{
										trn_seq=ret.getInt(2);
										cid	=ret.getInt("cid");
										System.out.println("cid is"+ cid);
									}
				
									rr= st.executeQuery("select custtype from CustomerMaster where cid="+ cid );
									
									if(	rr.next())
									{
										System.out.println("custtype is"+ rr.getInt("custtype"));	
										custtype=rr.getInt("custtype");
										if(custtype==0)
											cat_type=1;
										else
											cat_type=2;
									}	
								}
								
								ret1= stup.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+acc_type+" and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
								
							}
							
							if(boolean_flag)
							{
								System.out.println("111111111111111");
								GLTransObject trnobj=new GLTransObject();
								trnobj.setTrnDate(date_on);
								trnobj.setGLType(acc_type);
								trnobj.setGLCode(String.valueOf(acc_no));
								trnobj.setTrnMode("G");
								trnobj.setAmount(rs.getDouble("doc_tot"));
								trnobj.setCdind("D");
								trnobj.setAccType(acc_type);
								trnobj.setAccNo(String.valueOf(acc_no));
								trnobj.setTrnType("P");
								trnobj.setRefNo(rs.getInt("ack_no"));
								trnobj.setTrnSeq(trn_seq);
								trnobj.setVtml(tml);
								trnobj.setVid(user);	
								trnobj.setVDate(date);
								
								System.out.println(34);
								
								comm.storeGLTransaction(trnobj);System.out.println(35);
								
								System.out.println("22222222222222222");
								
							}
							else
							{	
								if(ret1.next())
								{
									System.out.println("111111111111111");
									GLTransObject trnobj=new GLTransObject();
									trnobj.setTrnDate(date_on);
									
									trnobj.setGLType(ret1.getString("gl_type"));
									trnobj.setGLCode(ret1.getString("gl_code"));
									
									trnobj.setTrnMode("G");
									trnobj.setAmount(rs.getDouble("doc_tot"));
									trnobj.setCdind("D");
									trnobj.setAccType(acc_type);
									trnobj.setAccNo(String.valueOf(acc_no));
									trnobj.setTrnType("P");
									trnobj.setRefNo(rs.getInt("ack_no"));
									trnobj.setTrnSeq(trn_seq);
									trnobj.setVtml(tml);
									trnobj.setVid(user);
									trnobj.setVDate(date);	
			
									System.out.println(34);
			
									comm.storeGLTransaction(trnobj);System.out.println(35);
									
									System.out.println("22222222222222222");
									
								}
							}
	 				
	 				}
	 				
	 				
	 				else if(((rs.getString("clg_type").equals("I") || (rs.getString("clg_type").equals("D") && rs.getInt("doc_dest") %1111 != 0) ) && rs.getString("doc_bs").equals("B")))
	 				{
	 					
	 					ResultSet	rt = st.executeQuery("select * from GLKeyParam where ac_type='1011001' and code=1");
	 					if(rt.next())
	 					{
	 						System.out.println (14);
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(date_on);
	 						trnobj.setGLType(rt.getString("gl_type"));
	 						System.out.println ("15.....gl_type "+rt.getString("gl_type"));
	 						trnobj.setGLCode(rt.getString("gl_code"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("doc_tot"));
	 						System.out.println ("16...gl_code "+rt.getString("gl_code"));
	 						trnobj.setCdind("C");
	 						trnobj.setAccType(rs.getString("cr_ac_type"));
	 						trnobj.setAccNo(rs.getString("cr_ac_no"));
	 						System.out.println (18);
	 						trnobj.setTrnType("");
	 						//trnobj.setRefNo("");
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(tml);
	 						trnobj.setVid(user);
	 						trnobj.setVDate(date);
	 						System.out.println(19);
	 	
	 						comm.storeGLTransaction(trnobj);
	 						System.out.println(20);
	 	
	 					}

	 					ret = stat.executeQuery("select br_ac_type,br_ac_no from BranchMaster where br_code ="+rs.getInt("send_to"));
	 						
	 						if(ret.next())
	 						{
	 							acc_type=ret.getString("br_ac_type");
	 							acc_no=ret.getInt("br_ac_no");
	 							
	 						}
	 						
	 						
	 					{
	 					AccountTransObject	am=new AccountTransObject();	
	 					
	 					am.setAccType(acc_type);
	 					System.out.println("acc type is"+ acc_type);
	 					
	 					am.setAccNo(acc_no);
	 					System.out.println(am.getAccNo() + " account number  ");
	 					
	 					am.setTransType("P");
	 					System.out.println(am.getTransType());
	 					
	 					am.setTransAmount(rs.getDouble("doc_tot"));
	 					System.out.println(am.getTransAmount()+" tran amount");
	 					
	 					am.setTransMode("G");
	 					System.out.println(am.getTransMode()+" trans mode");
	 					
	 					am.setTransDate(date_on);
	 					am.setTransSource(de_tml);
	 					
	 					am.setCdInd("D");
	 					System.out.println(am.getCdInd()+" CD indicator");
	 					
	 					am.setChqDDNo(rs.getInt("qdp_no"));
	 					
	 					am.setChqDDDate(rs.getString("qdp_dt"));
	 					am.setTransNarr("Ack No "+rs.getInt("ack_no"));
//	 					am.setRef_No("");
	 					am.setPayeeName(rs.getString("payee_name"));
	 					am.setCloseBal(rs.getDouble("doc_tot"));
	 					System.out.println(am.getCloseBal()+" closing balance");
	 					am.setLedgerPage(0);
	 					am.uv.setUserTml(de_tml);
	 					am.uv.setUserDate(de_date);
	 					am.uv.setUserId(de_user);

	 					am.uv.setVerTml(tml);
	 					am.uv.setVerId(user);
	 					am.uv.setVerDate(date);

	 					comm.storeAccountTransaction(am);
	 					}
	 					
	 					
	 					rs1 = stup.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+acc_no+" and am.ac_type='"+acc_type+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 					if(rs1.next())
	 					{
	 					
	 						trn_seq=rs1.getInt(2);
	 						cid	=rs1.getInt("cid");
	 						System.out.println("cid is"+ cid);
	 					}
	 					rr= st.executeQuery("select custtype from CustomerMaster where cid="+ cid );
	 			
	 					if(	rr.next())
	 					{
	 						System.out.println("custtype is"+ rr.getInt("custtype"));	
	 					 custtype=rr.getInt("custtype");
	 						if(custtype==0)
	 							cat_type=1;
	 						else
	 							cat_type=2;
	 					}
	 					

	 					ret1= stup.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+acc_type+" and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");

	 					{
	 						if(ret1.next())
	 						{
	 							System.out.println("111111111111111");
	 							GLTransObject trnobj=new GLTransObject();
	 							trnobj.setTrnDate(date_on);
	 							
	 								trnobj.setGLType(ret1.getString("gl_type"));
	 								trnobj.setGLCode(ret1.getString("gl_code"));
	 								
	 							trnobj.setTrnMode("G");
	 							trnobj.setAmount(rs.getDouble("doc_tot"));
	 							trnobj.setCdind("D");
	 							trnobj.setAccType(acc_type);
	 							trnobj.setAccNo(String.valueOf(acc_no));
	 							trnobj.setTrnType("P");
	 							trnobj.setRefNo(rs.getInt("ack_no"));
	 							trnobj.setTrnSeq(trn_seq);
	 							trnobj.setVtml(tml);
	 							trnobj.setVid(user);
	 							trnobj.setVDate(date);
	 							
	 							System.out.println(34);

	 							comm.storeGLTransaction(trnobj);System.out.println(35);
	 							
	 							System.out.println("22222222222222222");
	 							
	 						}
	 					}
	 				}	
	 				
	 				
	 				else if((rs.getString("clg_type").equals("O") && rs.getInt("prev_ctrl_no")>0))
	 				{

	 					result=stup.executeQuery("select * from Cheque where ctrl_no="+rs.getInt("prev_ctrl_no")+" and clg_type not in('S')" );
	 					if(result.next())
	 					{
	 						ret = stat.executeQuery("select br_ac_type,br_ac_no from BranchMaster where br_code ="+rs.getInt("send_to"));

	 						if(ret.next())
	 						{
	 							acc_type=ret.getString("br_ac_type");
	 							acc_no=ret.getInt("br_ac_no");

	 						}


	 						ResultSet result1=st.executeQuery("select ac_type,gl_type,gl_code from GLKeyParam  where ac_type='1011001' and code =12");
	 						if(result1.next())
	 						{
	 							GLTransObject trnobj=new GLTransObject();
	 							trnobj.setTrnDate(date_on);
	 							trnobj.setGLType(result1.getString(2));
	 							System.out.println (15+ result.getDouble("trn_amt"));
	 							trnobj.setGLCode(result1.getString(3));
	 							trnobj.setTrnMode("G");
	 							trnobj.setAmount(result.getDouble("trn_amt"));
	 							System.out.println (16);
	 							trnobj.setCdind("C");
	 							trnobj.setAccType(acc_type);
	 							trnobj.setAccNo(String.valueOf(acc_no));
	 							System.out.println (18);
	 							trnobj.setTrnType("P");
	 							//trnobj.setRefNo("");
	 							trnobj.setTrnSeq(0);
	 							trnobj.setVtml(tml);
	 							trnobj.setVid(user);
	 							trnobj.setVDate(date);
	 							System.out.println(19);

	 							comm.storeGLTransaction(trnobj);
	 							System.out.println(20);

	 						}

	 						System.out.println(" sending Bank"+rs.getInt("send_to"));

	 						ret = stat.executeQuery("select br_ac_type,br_ac_no from BranchMaster where br_code ="+rs.getInt("send_to"));

	 						if(ret.next())
	 						{
	 							acc_type=ret.getString("br_ac_type");
	 							acc_no=ret.getInt("br_ac_no");

	 						}
	 						if( !acc_type.startsWith("1012") )
	 						{
	 							boolean_flag=false;
	 							AccountTransObject am=new AccountTransObject();	
	 							System.out.println("chatti");
	 							am.setAccType(acc_type);
	 							System.out.println("acc type is"+ acc_type);
	 							am.setAccNo(acc_no);
	 							am.setTransType("P");
	 							am.setTransAmount(rs.getDouble("trn_amt"));
	 							am.setTransMode("G");
	 							am.setTransDate(date_on);
	 							am.setTransSource(de_tml);
	 							am.setCdInd("D");
	 							am.setChqDDNo(rs.getInt("qdp_no"));
	 							am.setChqDDDate(rs.getString("qdp_dt"));
	 							am.setTransNarr("clg No "+rs.getInt("ctrl_no"));
	 							//am.setRef_No("");
	 							am.setPayeeName("");
	 							am.setCloseBal(rs.getDouble("trn_amt"));
	 							am.setLedgerPage(0);
	 							am.uv.setUserTml(de_tml);
	 							am.uv.setUserDate(de_date);
	 							am.uv.setUserId(de_user);

	 							am.uv.setVerTml(tml);
	 							am.uv.setVerId(user);
	 							am.uv.setVerDate(date);

	 							comm.storeAccountTransaction(am);
	 							rs1 = stup.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+acc_no+" and am.ac_type='"+acc_type+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 							if(rs1.next())
	 							{
	 								if(ret.next())
	 								{
	 									trn_seq=ret.getInt(2);
	 									cid	=ret.getInt("cid");
	 									System.out.println("cid is"+ cid);
	 								}
	 								rr= st.executeQuery("select custtype from CustomerMaster where cid="+ cid );
	 								if(	rr.next())
	 								{
	 									System.out.println("custtype is"+ rr.getInt("custtype"));	
	 									custtype=rr.getInt("custtype");
	 									if(custtype==0)
	 										cat_type=1;
	 									else
	 										cat_type=2;
	 								}
	 							}

	 							ret1= stup.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+acc_type+" and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");


	 						}

	 						if(boolean_flag)
	 						{
	 							System.out.println("111111111111111");
	 							GLTransObject trnobj=new GLTransObject();
	 							trnobj.setTrnDate(date_on);

	 							trnobj.setGLType(acc_type);
	 							trnobj.setGLCode(String.valueOf(acc_no));

	 							trnobj.setTrnMode("G");
	 							trnobj.setAmount(rs.getDouble("trn_amt"));
	 							trnobj.setCdind("D");
	 							trnobj.setAccType(acc_type);
	 							trnobj.setAccNo(String.valueOf(acc_no));
	 							trnobj.setTrnType("P");
	 							trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno[i])));
	 							trnobj.setTrnSeq(trn_seq);
	 							trnobj.setVtml(tml);
	 							trnobj.setVid(user);
	 							trnobj.setVDate(date);

	 							System.out.println(34);

	 							comm.storeGLTransaction(trnobj);System.out.println(35);

	 							System.out.println("22222222222222222");


	 						}

	 						else{	
	 							if(ret1.next())
	 							{
	 								System.out.println("111111111111111");
	 								GLTransObject trnobj=new GLTransObject();
	 								trnobj.setTrnDate(date_on);


	 								trnobj.setGLType(ret1.getString("gl_type"));
	 								trnobj.setGLCode(ret1.getString("gl_code"));

	 								trnobj.setTrnMode("G");
	 								trnobj.setAmount(rs.getDouble("trn_amt"));
	 								trnobj.setCdind("D");
	 								trnobj.setAccType(acc_type);
	 								trnobj.setAccNo(String.valueOf(acc_no));
	 								trnobj.setTrnType("P");
	 								trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno[i])));
	 								trnobj.setTrnSeq(trn_seq);
	 								trnobj.setVtml(tml);
	 								trnobj.setVid(user);
	 								trnobj.setVDate(date);

	 								System.out.println(34);

	 								comm.storeGLTransaction(trnobj);System.out.println(35);

	 								System.out.println("22222222222222222");

	 							}
	 						}





	 					}
	 					else {
	 					result=stup.executeQuery("select * from Cheque where ctrl_no="+rs.getInt("prev_ctrl_no")+ " and clg_type in ('S')" );
	 					if(result.next())
	 					{
	 	 					 ret = st.executeQuery(" select gl_code,gl_type from GLKeyParam  where (code =3 ) and ac_type = '1011001'");
	 	 					while(ret.next())
	 	 					{
	 	 						GLTransObject trnobj=new GLTransObject();
	 	 						trnobj.setTrnDate(date_on);
	 	 						trnobj.setGLType(ret.getString(2));
	 	 						trnobj.setGLCode(ret.getString(1));
	 	 						trnobj.setTrnMode("G");
	 	 						trnobj.setAmount(rs.getDouble("trn_amt"));
	 	 						trnobj.setCdind("C");
	 	 						trnobj.setAccType(rs.getString(21));
	 	 						trnobj.setAccNo(rs.getString(22));
	 	 						trnobj.setTrnType("");
	 	 						trnobj.setRefNo(rs.getInt(4));
	 	 						trnobj.setTrnSeq(0);
	 	 						trnobj.setVtml(tml);
	 	 						trnobj.setVid(user);
	 	 						trnobj.setVDate(date);
	 	 						System.out.println(34);
	 	 		
	 	 						comm.storeGLTransaction(trnobj);
	 	 						System.out.println(35);
	 	 					}
	 	 					
	 	 					//debit to apex bank account i.e 9999 account
	 	 					{
	 	 						System.out.println(" sending Bank"+rs.getInt("send_to"));
	 	 						
	 	 						ret = stmt2.executeQuery("select br_ac_type,br_ac_no from BranchMaster where br_code ="+rs.getInt("send_to"));
	 	 		 				
	 	 		 				if(ret.next())
	 	 		 				{
	 	 		 					acc_type=ret.getString("br_ac_type");
	 	 		 					acc_no=ret.getInt("br_ac_no");
	 	 		 					
	 	 		 				}
	 	 						if( !(acc_type.startsWith("1012")) )
	 	 						{
	 	 							boolean_flag=false;
	 	 							AccountTransObject am=new AccountTransObject();	
	 	 						System.out.println("chatti");
	 	 						am.setAccType(acc_type);
	 	 						System.out.println("acc type is"+ acc_type);
	 	 						am.setAccNo(acc_no);
	 	 						am.setTransType("P");
	 	 						am.setTransAmount(rs.getDouble("trn_amt"));
	 	 						am.setTransMode("G");
	 	 						am.setTransDate(date_on);
	 	 						am.setTransSource(de_tml);
	 	 						am.setCdInd("D");
	 	 						am.setChqDDNo(rs.getInt("qdp_no"));
	 	 						am.setChqDDDate(rs.getString("qdp_dt"));
	 	 						am.setTransNarr("clg No "+rs.getInt("ctrl_no"));
	 	 						//am.setRef_No("");
	 	 						am.setPayeeName("");
	 	 						am.setCloseBal(rs.getDouble("trn_amt"));
	 	 						am.setLedgerPage(0);
	 	 						am.uv.setUserTml(de_tml);
	 	 						am.uv.setUserDate(de_date);
	 	 						am.uv.setUserId(de_user);
	 	 						
	 	 						am.uv.setVerTml(tml);
	 	 						am.uv.setVerId(user);
	 	 						am.uv.setVerDate(date);
	 	 		
	 	 						comm.storeAccountTransaction(am);
	 	 						rs1 = stup.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+acc_no+" and am.ac_type='"+acc_type+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 								if(rs1.next())
	 								{
	 									if(ret.next())
	 				 					{
	 				 					trn_seq=ret.getInt(2);
	 				 					cid	=ret.getInt("cid");
	 				 				System.out.println("cid is"+ cid);
	 				 					}
	 				 					 rr= st.executeQuery("select custtype from CustomerMaster where cid="+ cid );
	 				 				if(	rr.next())
	 				 				{
	 				 					System.out.println("custtype is"+ rr.getInt("custtype"));	
	 				 				 custtype=rr.getInt("custtype");
	 				 					if(custtype==0)
	 				 						cat_type=1;
	 				 					else
	 				 						cat_type=2;
	 				 				}
	 								}
	 							
	 								ret1= stup.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+acc_type+" and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 				 			
	 	 						
	 	 						}
	 	 						if(boolean_flag)
	 			 				{
	 			 					
	 			 					System.out.println("111111111111111");
	 	 		 					GLTransObject trnobj=new GLTransObject();
	 	 		 					trnobj.setTrnDate(date_on);
	 	 		 					trnobj.setGLType(acc_type);
	 			 					trnobj.setGLCode(String.valueOf(acc_no));		 			 					
	 	 		 					trnobj.setTrnMode("G");
	 	 		 					trnobj.setAmount(rs.getDouble("trn_amt"));
	 	 		 					trnobj.setCdind("D");
	 	 		 					trnobj.setAccType(acc_type);
	 	 		 					trnobj.setAccNo(String.valueOf(acc_no));
	 	 		 					trnobj.setTrnType("P");
	 	 		 					System.out.println("ctrl_no"+ctrlno[i]);
	 	 		 					trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno[i])));
	 	 		 					trnobj.setTrnSeq(trn_seq);
	 	 		 					trnobj.setVtml(tml);
	 	 		 					trnobj.setVid(user);
	 	 		 					trnobj.setVDate(date);
	 	 		 					
	 	 		 					System.out.println(34);

	 	 		 					comm.storeGLTransaction(trnobj);System.out.println(35);
	 	 		 					
	 	 		 					System.out.println("22222222222222222");
	 	 		 					

	 			 					}
	 			 					else
	 			 					{			
	 			 						if(ret1.next())
	 			 						{
	 			 							System.out.println("111111111111111");
	 			 							GLTransObject trnobj=new GLTransObject();
	 			 							trnobj.setTrnDate(date_on); 		 									
	 			 							trnobj.setGLType(ret1.getString("gl_type"));
	 			 							trnobj.setGLCode(ret1.getString("gl_code")); 		 					
	 			 							trnobj.setTrnMode("G");
	 			 							trnobj.setAmount(rs.getDouble("trn_amt"));
	 			 							trnobj.setCdind("D");
	 			 							trnobj.setAccType(acc_type);
	 			 							trnobj.setAccNo(String.valueOf(acc_no));
	 			 							trnobj.setTrnType("P");
	 			 							System.out.println("ctrl_no"+ctrlno[i]);
	 			 							trnobj.setRefNo(Integer.parseInt(String.valueOf(ctrlno[i])));
	 			 							trnobj.setTrnSeq(trn_seq);
	 			 							trnobj.setVtml(tml);
	 			 							trnobj.setVid(user);
	 			 							trnobj.setVDate(date);
	 	 		 					
	 			 							System.out.println(34);

	 			 							comm.storeGLTransaction(trnobj);System.out.println(35);
	 	 		 					
	 			 							System.out.println("22222222222222222");
	 	 		 					
	 			 						}
	 			 					}

	 	 					}
	 	 				
	 	 				ResultSet	rt = st.executeQuery("select * from GLKeyParam where ac_type='1011001' and code=9");
	 	 					if(rt.next())
	 	 					{
	 	 						System.out.println (1111);
	 	 						GLTransObject trnobj=new GLTransObject();
	 	 						trnobj.setTrnDate(date_on);
	 	 						System.out.println (22);
	 	 						trnobj.setGLType(rt.getString(5));
	 	 						trnobj.setGLCode(rt.getString(4));
	 	 						System.out.println (33);
	 	 						trnobj.setTrnMode("G");
	 	 						trnobj.setAmount(rs.getDouble("trn_amt"));
	 	 						System.out.println (44);
	 	 						trnobj.setCdind("D");
	 	 						trnobj.setAccType(rs.getString(21));
	 	 						trnobj.setAccNo(rs.getString(22));
	 	 						trnobj.setTrnType("");
	 	 						trnobj.setRefNo(rs.getInt(4));
	 	 						trnobj.setTrnSeq(0);
	 	 						trnobj.setVtml(tml);
	 	 						trnobj.setVid(user);
	 	 						trnobj.setVDate(date);
	 	 						
	 	 						comm.storeGLTransaction(trnobj);
	 	 						System.out.println (66);
	 	 					}
	 	 					
	 	 					ret = st.executeQuery("select * from GLKeyParam where ac_type='1011001' and code=10");
	 	 					if(ret.next())
	 	 					{
	 	 						System.out.println (1111);
	 	 						GLTransObject trnobj=new GLTransObject();
	 	 						trnobj.setTrnDate(date_on);
	 	 						System.out.println (22);
	 	 						trnobj.setGLType(ret.getString(5));
	 	 						trnobj.setGLCode(ret.getString(4));
	 	 						System.out.println (33);
	 	 						trnobj.setTrnMode("G");
	 	 						trnobj.setAmount(rs.getDouble("trn_amt"));
	 	 						System.out.println (44);
	 	 						trnobj.setCdind("C");
	 	 						trnobj.setAccType(rs.getString(21));
	 	 						trnobj.setAccNo(rs.getString(22));
	 	 						trnobj.setTrnType("");
	 	 						trnobj.setRefNo(rs.getInt(4));
	 	 						trnobj.setTrnSeq(0);
	 	 						trnobj.setVtml(tml);
	 	 						trnobj.setVid(user);
	 	 						trnobj.setVDate(date);
	 	 						comm.storeGLTransaction(trnobj);
	 	 						System.out.println (66);
	 	 					}
	 								
	 						
	 					}
	 				}
	 					
	 					
	 				}
	 				else if((rs.getString("clg_type").equals("C") && rs.getString("doc_bs").equalsIgnoreCase("B") && rs.getInt("doc_dest") %1111 != 0))
	 				{
	 					
	 					ret = stmt3.executeQuery("select * from GLKeyParam where code= 9 and  ac_type ='1011001' ");
	 					if(ret.next())
	 					{
	 						System.out.println("33333333333");
	 						GLTransObject trnobj=new GLTransObject();

	 						trnobj.setTrnDate(date_on);
	 						trnobj.setGLType(ret.getString("gl_type"));
	 						trnobj.setGLCode(ret.getString("gl_code"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("doc_tot"));
	 						trnobj.setCdind("D");
	 						trnobj.setAccType(rs.getString("cr_ac_type"));
	 						trnobj.setAccNo(rs.getString("cr_ac_no"));
	 						trnobj.setTrnType("R");
	 						trnobj.setRefNo(rs.getInt("ack_no"));
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(rs.getString("ve_tml"));
	 						trnobj.setVDate(rs.getString("ve_user"));
	 						trnobj.setVid(rs.getString("ve_dt_time"));
	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);
	 						System.out.println(35);
	 						System.out.println("444444444444444");


	 						System.out.println("33333333333");



	 						




	 					}

	 					AccountTransObject am = new AccountTransObject();
	 					
	 	 				//	Credit branch C/A 
	 	 					
	 	 					
	 	 					ret = st.executeQuery("select br_ac_type,br_ac_no from BranchMaster where br_code ="+rs.getInt("doc_dest"));
	 			 				
	 			 				if(ret.next())
	 			 				{
	 			 					acc_type=ret.getString("br_ac_type");
	 			 					acc_no=ret.getInt("br_ac_no");
	 			 					
	 			 				}
	 							
	 							{
	 								 am=new AccountTransObject();	
	 							
	 							am.setAccType(acc_type);
	 							System.out.println("acc type is"+ acc_type);
	 							
	 							System.out.println("acc no is"+ acc_no);
	 							am.setAccNo(acc_no);
	 							am.setTransType("R");
	 							am.setTransAmount(rs.getDouble("doc_tot"));
	 							am.setTransMode("G");
	 							am.setTransDate(date_on);
	 							am.setTransSource(de_tml);
	 							am.setCdInd("C");
	 							am.setChqDDNo(rs.getInt("qdp_no"));
	 							am.setChqDDDate(rs.getString("qdp_dt"));
	 							am.setTransNarr("Ack No "+rs.getInt("ack_no"));
	 							//am.setRef_No("");
	 							am.setPayeeName(rs.getString("payee_name"));
	 							am.setCloseBal(rs.getDouble("doc_tot"));
	 							am.setLedgerPage(0);
	 							am.uv.setUserTml(de_tml);
	 							am.uv.setUserDate(de_date);
	 							am.uv.setUserId(de_user);
	 							
	 							am.uv.setVerTml(tml);
	 							am.uv.setVerId(user);
	 							am.uv.setVerDate(date);
	 			
	 							comm.storeAccountTransaction(am);
	 							}
	 							
	 							 					
	 						
	 							ret = stup.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+acc_no+" and am.ac_type='"+acc_type+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 							if(ret.next())
	 							{
	 								trn_seq=ret.getInt(2);
	 								cid	=ret.getInt("cid");
	 								System.out.println("cid is"+ cid);
	 							}
	 							rr= st.executeQuery("select custtype from CustomerMaster where cid="+ cid );
	 							if(	rr.next())
	 							{
	 								System.out.println("custtype is"+ rr.getInt("custtype"));	
	 								custtype=rr.getInt("custtype");
	 								if(custtype==0)
	 									cat_type=1;
	 								else
	 									cat_type=2;
	 							}
	 								
	 							
	 							ret1= stup.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+acc_type+" and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 				 				
	 							if(ret1.next())
	 							{
	 								System.out.println("111111111111111");
	 								GLTransObject trnobj=new GLTransObject();
	 			 					trnobj.setTrnDate(date_on);
	 			 					trnobj.setGLType(ret1.getString("gl_type"));
	 			 					trnobj.setGLCode(ret1.getString("gl_code"));
	 			 					trnobj.setTrnMode("G");
	 			 					trnobj.setAmount(rs.getDouble("doc_tot"));
	 			 					trnobj.setCdind("C");
	 			 					trnobj.setAccType(acc_type);
	 			 					trnobj.setAccNo(String.valueOf(acc_no));
	 			 					trnobj.setTrnType("P");
	 			 					trnobj.setRefNo(rs.getInt("ack_no"));
	 			 					trnobj.setTrnSeq(trn_seq);
	 			 					trnobj.setVtml(tml);
	 			 					trnobj.setVid(user);
	 			 					trnobj.setVDate(date);
	 			 					
	 			 					System.out.println(34);

	 			 					comm.storeGLTransaction(trnobj);System.out.println(35);
	 			 					
	 			 					System.out.println("22222222222222222");
	 			 					
	 							}
	 					
	 					
	 				}
	 				
	 				else if((rs.getString("clg_type").equals("C") && rs.getInt("prev_ctrl_no")>0) && rs.getString("doc_bs").equalsIgnoreCase("S"))
	 				{


	 					ret = stmt3.executeQuery("select * from GLKeyParam where code=11 and  ac_type ='1011001' ");
	 					if(ret.next())
	 					{
	 						System.out.println("33333333333");
	 						GLTransObject trnobj=new GLTransObject();

	 						trnobj.setTrnDate(date_on);
	 						trnobj.setGLType(ret.getString("gl_type"));
	 						trnobj.setGLCode(ret.getString("gl_code"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("trn_amt"));
	 						trnobj.setCdind("C");
	 						trnobj.setAccType(rs.getString("dr_ac_type"));
	 						trnobj.setAccNo(rs.getString("dr_ac_no"));
	 						trnobj.setTrnType("R");
	 						trnobj.setRefNo(rs.getInt("ack_no"));
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(rs.getString("ve_tml"));
	 						trnobj.setVDate(rs.getString("ve_user"));
	 						trnobj.setVid(rs.getString("ve_dt_time"));
	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);
	 						System.out.println(35);
	 						System.out.println("444444444444444");


	 						// one more Credit entry for bounce ECS 

	 						System.out.println("33333333333");



	 						comm.storeGLTransaction(trnobj);
	 						System.out.println(35);
	 						System.out.println("444444444444444");




	 					}


	 					ResultSet ret1_ss = stmt3.executeQuery("select br_ac_type,br_ac_no from BranchMaster where br_code ="+rs.getInt("doc_dest"));
	 					if(ret1_ss.next())
	 					{
	 						System.out.println("33333333333");
	 						GLTransObject trnobj=new GLTransObject();

	 						trnobj.setTrnDate(date_on);
	 						trnobj.setGLType(ret1_ss.getString("br_ac_type"));
	 						trnobj.setGLCode(ret1_ss.getString("br_ac_no"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("trn_amt"));
	 						trnobj.setCdind("C");
	 						trnobj.setAccType(rs.getString("cr_ac_type"));
	 						trnobj.setAccNo(rs.getString("cr_ac_no"));
	 						trnobj.setTrnType("R");
	 						trnobj.setRefNo(rs.getInt("ack_no"));
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(rs.getString("ve_tml"));
	 						trnobj.setVDate(rs.getString("ve_user"));
	 						trnobj.setVid(rs.getString("ve_dt_time"));
	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);
	 						System.out.println(35);
	 						System.out.println("444444444444444");

	 					}

	 				}
	 				
	 				
	 				else if((rs.getString("clg_type").equals("D") && rs.getInt("prev_ctrl_no")>0) && rs.getString("doc_bs").equalsIgnoreCase("S"))
	 				{

	 					ret = stmt3.executeQuery("select * from GLKeyParam where code=12 and  ac_type ='1011001' ");
	 					if(ret.next())
	 					{
	 						System.out.println("33333333333");
	 						GLTransObject trnobj=new GLTransObject();

	 						trnobj.setTrnDate(date_on);
	 						trnobj.setGLType(ret.getString("gl_type"));
	 						trnobj.setGLCode(ret.getString("gl_code"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(Double.parseDouble(rs.getString("trn_amt")));
	 						trnobj.setCdind("C");
	 						trnobj.setAccType(rs.getString("cr_ac_type"));// cd.getDebitACType()
	 						trnobj.setAccNo((rs.getString("cr_ac_no")));
	 						trnobj.setTrnType("R");
	 						trnobj.setRefNo(rs.getInt("ack_no"));
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(rs.getString("ve_tml"));
	 						trnobj.setVDate(rs.getString("ve_user"));
	 						trnobj.setVid(rs.getString("ve_dt_time"));
	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);
	 						System.out.println(35);
	 						System.out.println("444444444444444");


	 					}	

	 					ResultSet ret_su = stmt3.executeQuery("select br_ac_type,br_ac_no from BranchMaster where br_code ="+rs.getInt("doc_dest"));
	 					if(ret_su.next())
	 					{
	 						System.out.println("33333333333");
	 						GLTransObject trnobj=new GLTransObject();

	 						trnobj.setTrnDate(date_on);
	 						trnobj.setGLType(ret_su.getString("br_ac_type"));
	 						trnobj.setGLCode(ret_su.getString("br_ac_no"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(Double.parseDouble(rs.getString("trn_amt")));
	 						trnobj.setCdind("D");
	 						trnobj.setAccType(rs.getString("cr_ac_type"));
	 						trnobj.setAccNo(rs.getString("cr_ac_no"));
	 						trnobj.setTrnType("R");
	 						trnobj.setRefNo(rs.getInt("ack_no"));
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(rs.getString("ve_tml"));
	 						trnobj.setVDate(rs.getString("ve_user"));
	 						trnobj.setVid(rs.getString("ve_dt_time"));
	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);
	 						System.out.println(35);
	 						System.out.println("444444444444444");

	 					}


	 				}
	 				
	 				else if((rs.getString("clg_type").equals("C") && rs.getString("doc_bs").equalsIgnoreCase("B") && rs.getInt("doc_dest") %1111 == 0))
	 				{

	 					System.out.println("1---------------------------------------->");
	 					AccountTransObject am = new AccountTransObject();

	 					//	Credit branch C/A 


	 					ret = st.executeQuery("select br_ac_type,br_ac_no from BranchMaster where br_code ="+rs.getInt("doc_sou"));

	 					if(ret.next())
	 					{
	 						acc_type=ret.getString("br_ac_type");
	 						acc_no=ret.getInt("br_ac_no");

	 					}

	 					{
	 						am=new AccountTransObject();	

	 						am.setAccType(acc_type);
	 						System.out.println("acc type is"+ acc_type);

	 						System.out.println("acc no is"+ acc_no);
	 						am.setAccNo(acc_no);
	 						am.setTransType("R");
	 						am.setTransAmount(rs.getDouble("doc_tot"));
	 						am.setTransMode("G");
	 						am.setTransDate(date_on);
	 						am.setTransSource(de_tml);
	 						am.setCdInd("D");
	 						am.setChqDDNo(rs.getInt("qdp_no"));
	 						am.setChqDDDate(rs.getString("qdp_dt"));
	 						am.setTransNarr("Ack No "+rs.getInt("ack_no"));
	 						//am.setRef_No("");
	 						am.setPayeeName(rs.getString("payee_name"));
	 						am.setCloseBal(rs.getDouble("doc_tot"));
	 						am.setLedgerPage(0);
	 						am.uv.setUserTml(de_tml);
	 						am.uv.setUserDate(de_date);
	 						am.uv.setUserId(de_user);

	 						am.uv.setVerTml(tml);
	 						am.uv.setVerId(user);
	 						am.uv.setVerDate(date);

	 						comm.storeAccountTransaction(am);
	 						System.out.println("2---------------------------------------->");
	 					}



	 					ret = stup.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+acc_no+" and am.ac_type='"+acc_type+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 					if(ret.next())
	 					{
	 						trn_seq=ret.getInt(2);
	 						cid	=ret.getInt("cid");
	 						System.out.println("cid is"+ cid);
	 					}
	 					rr= st.executeQuery("select custtype from CustomerMaster where cid="+ cid );
	 					if(	rr.next())
	 					{
	 						System.out.println("custtype is"+ rr.getInt("custtype"));	
	 						custtype=rr.getInt("custtype");
	 						if(custtype==0)
	 							cat_type=1;
	 						else
	 							cat_type=2;
	 					}


	 					ret1= stup.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+acc_type+" and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");

	 					if(ret1.next())
	 					{
	 						System.out.println("111111111111111");
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(date_on);
	 						trnobj.setGLType(ret1.getString("gl_type"));
	 						trnobj.setGLCode(ret1.getString("gl_code"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("doc_tot"));
	 						trnobj.setCdind("D");
	 						trnobj.setAccType(acc_type);
	 						trnobj.setAccNo(String.valueOf(acc_no));
	 						trnobj.setTrnType("P");
	 						trnobj.setRefNo(rs.getInt("ack_no"));
	 						trnobj.setTrnSeq(trn_seq);
	 						trnobj.setVtml(tml);
	 						trnobj.setVid(user);
	 						trnobj.setVDate(date);

	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);System.out.println(35);
	 						System.out.println("3---------------------------------------->");

	 						System.out.println("22222222222222222");

	 					}

	 					
	 					ret = stmt3.executeQuery("select * from GLKeyParam where code=11 and  ac_type ='1011001' ");
	 					if(ret.next())
	 					{
	 						System.out.println("4---------------------------------------->");
	 						System.out.println("33333333333");
	 						GLTransObject trnobj=new GLTransObject();

	 						trnobj.setTrnDate(date_on);
	 						trnobj.setGLType(ret.getString("gl_type"));
	 						trnobj.setGLCode(ret.getString("gl_code"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("doc_tot"));
	 						trnobj.setCdind("C");
	 						trnobj.setAccType(rs.getString("dr_ac_type"));
	 						trnobj.setAccNo(rs.getString("dr_ac_no"));
	 						trnobj.setTrnType("R");
	 						trnobj.setRefNo(rs.getInt("ack_no"));
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(rs.getString("ve_tml"));
	 						trnobj.setVDate(rs.getString("ve_user"));
	 						trnobj.setVid(rs.getString("ve_dt_time"));
	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);
	 						System.out.println(35);
	 						System.out.println("444444444444444");


	 					 

	 					}
	 					
	 					
	 					
	 					ret = stmt3.executeQuery("select * from GLKeyParam where code= 2 and  ac_type ='1011001' ");
	 					if(ret.next())
	 					{
	 						System.out.println("33333333333");
	 						GLTransObject trnobj=new GLTransObject();

	 						trnobj.setTrnDate(date_on);
	 						trnobj.setGLType(ret.getString("gl_type"));
	 						trnobj.setGLCode(ret.getString("gl_code"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("doc_tot"));
	 						trnobj.setCdind("D");
	 						trnobj.setAccType(rs.getString("dr_ac_type"));
	 						trnobj.setAccNo(rs.getString("dr_ac_no"));
	 						trnobj.setTrnType("R");
	 						trnobj.setRefNo(rs.getInt("ack_no"));
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(rs.getString("ve_tml"));
	 						trnobj.setVDate(rs.getString("ve_dt_time"));
	 						trnobj.setVid(rs.getString("ve_user"));
	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);
	 						System.out.println(35);
	 						System.out.println("444444444444444");

	 						System.out.println("6---------------------------------------->");


	 					}
	 					
	 					
	 					ret = stmt3.executeQuery("select br_ac_type,br_ac_no from BranchMaster where br_code ="+rs.getInt("doc_dest"));

	 					if(ret.next())
	 					{

	 							System.out.println("111111111111111");
	 							
	 							GLTransObject trnobj=new GLTransObject();
	 							trnobj.setTrnDate(date_on);

	 	 						
	 							trnobj.setGLType(ret.getString("br_ac_type"));
	 	 						trnobj.setGLCode(ret.getString("br_ac_no"));
	 							

	 							trnobj.setTrnMode("G");
	 							trnobj.setAmount(rs.getDouble("doc_tot"));

	 							trnobj.setCdind("C");

	 							trnobj.setAccType(acc_type);
	 							trnobj.setAccNo(String.valueOf(acc_no));
	 							trnobj.setTrnType("R");
	 							trnobj.setRefNo(rs.getInt("ack_no"));
	 							trnobj.setTrnSeq(trn_seq);
	 							trnobj.setVtml(tml);
	 							trnobj.setVid(user);
	 							trnobj.setVDate(rs.getString("ve_dt_time"));

	 							System.out.println(34);

	 							comm.storeGLTransaction(trnobj);System.out.println(35);

	 							System.out.println("22222222222222222");
	 							System.out.println("7---------------------------------------->");


	 						}
	 					
	 					
	 					
	 					
	 					


	 				}
	 				
	 				
	 				else if((rs.getString("clg_type").equals("D") && rs.getString("doc_bs").equalsIgnoreCase("B") && rs.getInt("doc_dest") %1111 == 0))
	 				{


	 					AccountTransObject am = new AccountTransObject();

	 					//	Credit branch C/A 


	 					ret = st.executeQuery("select br_ac_type,br_ac_no from BranchMaster where br_code ="+rs.getInt("doc_sou"));

	 					if(ret.next())
	 					{
	 						acc_type=ret.getString("br_ac_type");
	 						acc_no=ret.getInt("br_ac_no");

	 					}

	 					{
	 						am=new AccountTransObject();	

	 						am.setAccType(acc_type);
	 						System.out.println("acc type is"+ acc_type);

	 						System.out.println("acc no is"+ acc_no);
	 						am.setAccNo(acc_no);
	 						am.setTransType("R");
	 						am.setTransAmount(rs.getDouble("doc_tot"));
	 						am.setTransMode("G");
	 						am.setTransDate(date_on);
	 						am.setTransSource(de_tml);
	 						am.setCdInd("C");
	 						am.setChqDDNo(rs.getInt("qdp_no"));
	 						am.setChqDDDate(rs.getString("qdp_dt"));
	 						am.setTransNarr("Ack No "+rs.getInt("ack_no"));
	 						//am.setRef_No("");
	 						am.setPayeeName(rs.getString("payee_name"));
	 						am.setCloseBal(rs.getDouble("doc_tot"));
	 						am.setLedgerPage(0);
	 						am.uv.setUserTml(de_tml);
	 						am.uv.setUserDate(de_date);
	 						am.uv.setUserId(de_user);

	 						am.uv.setVerTml(tml);
	 						am.uv.setVerId(user);
	 						am.uv.setVerDate(date);

	 						comm.storeAccountTransaction(am);
	 					}



	 					ret = stup.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+acc_no+" and am.ac_type='"+acc_type+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 					if(ret.next())
	 					{
	 						trn_seq=ret.getInt(2);
	 						cid	=ret.getInt("cid");
	 						System.out.println("cid is"+ cid);
	 					}
	 					rr= st.executeQuery("select custtype from CustomerMaster where cid="+ cid );
	 					if(	rr.next())
	 					{
	 						System.out.println("custtype is"+ rr.getInt("custtype"));	
	 						custtype=rr.getInt("custtype");
	 						if(custtype==0)
	 							cat_type=1;
	 						else
	 							cat_type=2;
	 					}


	 					ret1= stup.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+acc_type+" and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");

	 					if(ret1.next())
	 					{
	 						System.out.println("111111111111111");
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(date_on);
	 						trnobj.setGLType(ret1.getString("gl_type"));
	 						trnobj.setGLCode(ret1.getString("gl_code"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("doc_tot"));
	 						trnobj.setCdind("C");
	 						trnobj.setAccType(acc_type);
	 						trnobj.setAccNo(String.valueOf(acc_no));
	 						trnobj.setTrnType("P");
	 						trnobj.setRefNo(rs.getInt("ack_no"));
	 						trnobj.setTrnSeq(trn_seq);
	 						trnobj.setVtml(tml);
	 						trnobj.setVid(user);
	 						trnobj.setVDate(date);

	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);System.out.println(35);

	 						System.out.println("22222222222222222");

	 					}

	 					
	 					
	 					ret = stmt3.executeQuery("select * from GLKeyParam where code=11 and  ac_type ='1011001' ");
	 					if(ret.next())
	 					{
	 						System.out.println("33333333333");
	 						GLTransObject trnobj=new GLTransObject();

	 						trnobj.setTrnDate(date_on);
	 						trnobj.setGLType(ret.getString("gl_type"));
	 						trnobj.setGLCode(ret.getString("gl_code"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("doc_tot"));
	 						trnobj.setCdind("D");
	 						trnobj.setAccType(rs.getString("dr_ac_type"));
	 						trnobj.setAccNo(rs.getString("dr_ac_no"));
	 						trnobj.setTrnType("R");
	 						trnobj.setRefNo(rs.getInt("ack_no"));
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(rs.getString("ve_tml"));
	 						trnobj.setVDate(rs.getString("ve_user"));
	 						trnobj.setVid(rs.getString("ve_dt_time"));
	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);
	 						System.out.println(35);
	 						System.out.println("444444444444444");


	 					}
	 					
	 					ret = stmt3.executeQuery("select * from GLKeyParam where code= 2 and  ac_type ='1011001' ");
	 					if(ret.next())
	 					{
	 						System.out.println("33333333333");
	 						GLTransObject trnobj=new GLTransObject();

	 						trnobj.setTrnDate(date_on);
	 						trnobj.setGLType(ret.getString("gl_type"));
	 						trnobj.setGLCode(ret.getString("gl_code"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("doc_tot"));
	 						trnobj.setCdind("C");
	 						trnobj.setAccType(rs.getString("dr_ac_type"));
	 						trnobj.setAccNo(rs.getString("dr_ac_no"));
	 						trnobj.setTrnType("R");
	 						trnobj.setRefNo(rs.getInt("ack_no"));
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(rs.getString("ve_tml"));
	 						trnobj.setVDate(rs.getString("ve_dt_time"));
	 						trnobj.setVid(rs.getString("ve_user"));
	 						System.out.println(34);

	 						comm.storeGLTransaction(trnobj);
	 						System.out.println(35);
	 						System.out.println("444444444444444");



	 					}
	 					
	 					
	 					ret = stmt3.executeQuery("select br_ac_type,br_ac_no from BranchMaster where br_code ="+rs.getInt("doc_dest"));

	 					if(ret.next())
	 					{

	 							System.out.println("111111111111111");
	 							
	 							GLTransObject trnobj=new GLTransObject();
	 							trnobj.setTrnDate(date_on);

	 	 						
	 							trnobj.setGLType(ret.getString("br_ac_type"));
	 	 						trnobj.setGLCode(ret.getString("br_ac_no"));
	 							

	 							trnobj.setTrnMode("G");
	 							trnobj.setAmount(rs.getDouble("doc_tot"));
	 							
	 							trnobj.setCdind("D");

	 							trnobj.setAccType(acc_type);
	 							trnobj.setAccNo(String.valueOf(acc_no));
	 							trnobj.setTrnType("R");
	 							trnobj.setRefNo(rs.getInt("ack_no"));
	 							trnobj.setTrnSeq(trn_seq);
	 							trnobj.setVtml(tml);
	 							trnobj.setVid(user);
	 							trnobj.setVDate(rs.getString("ve_dt_time"));

	 							System.out.println(34);

	 							comm.storeGLTransaction(trnobj);System.out.println(35);

	 							System.out.println("22222222222222222");



	 						}

	 				}
	 				
	 				
	 				}		
	 					
	 					
	 					
	 								//if check is bundled entry recieved from apex bank(Inward) dispatching to branches
	 				/* else if(rs.getString("clg_type").equals("I") && rs.getString("doc_bs").equals("B"))
	 				{
	 					//credit to Clg_InAB gl
	 					ResultSet ret = st.executeQuery("select ac_type,gl_type,gl_code from GLKeyParam  where ac_type=1011001 and code=1");
	 					if(ret.next())
	 					{
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(comm.getSysDate());
	 						trnobj.setGLType(ret.getString("gl_type"));
	 						trnobj.setGLCode(ret.getString("gl_code"));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("doc_tot"));
	 						trnobj.setCdind("C");
	 						trnobj.setAccType(ret.getString("ac_type"));
	 						trnobj.setAccNo("");
	 						trnobj.setTrnType("R");
	 						trnobj.setRefNo(rs.getInt("ack_no"));
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(tml);
	 						trnobj.setVid(user);System.out.println(34);
	 	
	 						comm.storeGLTransaction(trnobj);System.out.println(35);
	 					}
	 					
	 					//debit to branch current account gl
	 					ResultSet rss = stat.executeQuery("select br_ac_type,br_ac_no,gl_type,gl_code from BranchMaster where br_code="+rs.getInt("doc_dest"));
	 					AccountTransObject am=null;
	 					if(rss.next())
	 					{
	 						am = new AccountTransObject();
	 						am.setAccType(rss.getString("br_ac_type"));
	 						am.setAccNo(rss.getInt("br_ac_no"));
	 						am.setTransType("P");
	 						am.setTransAmount(rs.getDouble("doc_tot"));
	 						am.setTransMode("G");
	 						
	 						am.setTransSource(tml);
	 						am.setCdInd("D");
	 						am.setChqDDNo(rs.getInt("qdp_no"));
	 						am.setChqDDDate(rs.getString("qdp_dt"));
	 						am.setTransNarr("clg No "+rs.getInt("ctrl_no"));
	 						//am.setRef_No("");
	 						am.setPayeeName("");
	 						am.setCloseBal(-rs.getDouble("doc_tot"));
	 						am.setLedgerPage(0);
	 						am.uv.setUserTml(tml);
	 						am.uv.setUserId(user);
	 						am.uv.setVerTml(tml);		
	 						am.uv.setVerId(user);
	 		
	 						comm.storeAccountTransaction(am);
	 					}	
	 					
	 					ret = st.executeQuery("select trn_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 					ret.next();
	 					System.out.println (11);
	 					int trn_seq=ret.getInt("trn_seq");
	 															
	 					ret = st.executeQuery("select gl_code,mult_by from GLPost gp where gl_code = "+rss.getInt("gl_code")+" and ac_type='"+am.getAccType()+"' and trn_type='P' and cr_dr='D'");
	 					System.out.println (13);
	 					if(ret.next())
	 					{
	 						System.out.println (14);
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(comm.getSysDate());
	 						trnobj.setGLType("GL");
	 						System.out.println (15);
	 						trnobj.setGLCode(ret.getString(1));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("doc_tot")*ret.getInt(2));
	 						System.out.println (16);
	 						trnobj.setCdind("D");
	 						trnobj.setAccType(rss.getString("br_ac_type"));
	 						trnobj.setAccNo(rss.getString("br_ac_no"));
	 						System.out.println (18);
	 						trnobj.setTrnType("P");
	 						//trnobj.setRefNo("");
	 						trnobj.setTrnSeq(trn_seq);
	 						trnobj.setVtml(tml);
	 						trnobj.setVid(user);
	 						System.out.println(19);
	 	
	 						comm.storeGLTransaction(trnobj);
	 						System.out.println(20);
	 					}
	 				}
	 				
	 				//if check is bounced recieved from apex bank(normal Inward cheque)
	 				/*
	 				 else if(rs.getString("clg_type").equals("O") && rs.getString("ret_norm").equals("R"))
	 				{
	 					//debit to apex bank account i.e 9999 account
	 					
	 					ResultSet rss = stat.executeQuery("select br_ac_type,br_ac_no,gl_type,gl_code from BranchMaster where br_code="+rs.getInt("send_to"));
	 					AccountTransObject am=null;
	 					
	 					if(rss.next())
	 					{
	 						am = new AccountTransObject();
	 						am.setAccType(rss.getString("br_ac_type"));
	 						am.setAccNo(rss.getInt("br_ac_no"));
	 						am.setTransType("P");
	 						//am.setTransAmount(rs.getDouble("doc_tot"));
	 						am.setTransAmount(rs.getDouble("trn_amt"));
	 						am.setTransMode("G");
	 						
	 						am.setTransSource(tml);
	 						am.setCdInd("D");
	 						am.setChqDDNo(rs.getInt("qdp_no"));
	 						am.setChqDDDate(rs.getString("qdp_dt"));
	 						am.setTransNarr("clg No "+rs.getInt("ctrl_no"));
	 						//am.setRef_No("");
	 						am.setPayeeName("");
	 						//am.setCloseBal(-rs.getDouble("doc_tot"));
	 						am.setCloseBal(-rs.getDouble("trn_amt"));
	 						am.setLedgerPage(0);
	 						am.uv.setUserTml(tml);
	 						am.uv.setUserId(user);
	 						am.uv.setVerTml(tml);
	 						am.uv.setVerId(user);
	 		
	 						comm.storeAccountTransaction(am);
	 					}
	 					// GL goes here..
	 					ResultSet ret = st.executeQuery("select trn_seq from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 					ret.next();
	 					System.out.println (11);
	 					int trn_seq=ret.getInt("trn_seq");
	 								
	 					
	 					ret = st.executeQuery("select gl_code,mult_by from GLPost gp where gl_code = "+rss.getInt("gl_code")+" and ac_type='"+am.getAccType()+"' and trn_type='P' and cr_dr='D'");
	 					System.out.println (13);
	 					if(ret.next())
	 					{
	 						System.out.println (14);
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(comm.getSysDate());
	 						trnobj.setGLType("GL");
	 						System.out.println (15);
	 						trnobj.setGLCode(ret.getString(1));
	 						trnobj.setTrnMode("G");
	 						//trnobj.setAmount(rs.getDouble("doc_tot")*ret.getInt(2));
	 						trnobj.setAmount(rs.getDouble("trn_amt")*ret.getInt(2));
	 						System.out.println (16);
	 						trnobj.setCdind("D");
	 						trnobj.setAccType(rss.getString("br_ac_type"));
	 						trnobj.setAccNo(rss.getString("br_ac_no"));
	 						System.out.println (18);
	 						trnobj.setTrnType("P");
	 						//trnobj.setRefNo("");
	 						trnobj.setTrnSeq(trn_seq);
	 						trnobj.setVtml(tml);
	 						trnobj.setVid(user);
	 						System.out.println(19);
	 	
	 						comm.storeGLTransaction(trnobj);
	 						System.out.println(20);
	 					}
	 					
	 					//credit gl to I/W RTN CHQ GL
	 					ret = st.executeQuery("select gl_code,gl_type from GLKeyParam where code =12 and ac_type = '1011001'");
	 					if(ret.next())
	 					{
	 						GLTransObject trnobj=new GLTransObject();
	 						trnobj.setTrnDate(date_on);
	 						trnobj.setGLType(ret.getString(2));
	 						trnobj.setGLCode(ret.getString(1));
	 						trnobj.setTrnMode("G");
	 						trnobj.setAmount(rs.getDouble("trn_amt"));
	 						//trnobj.setAmount(rs.getDouble("doc_tot"));
	 						trnobj.setCdind("C");
	 						trnobj.setAccType("");
	 						trnobj.setAccNo("");
	 						trnobj.setTrnType("");
	 						trnobj.setRefNo(0);
	 						trnobj.setTrnSeq(0);
	 						trnobj.setVtml(tml);
	 						trnobj.setVid(user);System.out.println(34);
	 		
	 						storeGLTransaction(trnobj);System.out.println(35);
	 					}
	 				}*/
	 	
	 			
	 			
	 			}	
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			dispatch=-1;
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotUpdatedException();
	 		}catch(NullPointerException nl){
	 			dispatch=-1;
	 			sessionContext.setRollbackOnly();
	 			nl.printStackTrace();
	 			throw new RecordNotUpdatedException();
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		//return -1;
	 		return dispatch;
	 	}

	 	public ClearingObject[] getOutwardStatement(String date,int clgno,String query)throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		
	 		ResultSet rs1=null;
	 		ClearingObject[] cd=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 			System.out.println("Working1.............................");
	 			if(query==null)
	 			    rs1=stmt.executeQuery("select mult_cr,ctrl_no,doc_bs,cr_ac_type from Cheque where desp_ind='T' and clg_date='"+date+"'and clg_type='O' and clg_no="+clgno);
	 			else
	 			    rs1=stmt.executeQuery("select mult_cr,ctrl_no,doc_bs,cr_ac_type from Cheque where desp_ind='T' and clg_date='"+date+"' and clg_type='O' and ("+query+") and clg_no="+clgno);
	 			
	 			rs1.last();
	 			System.out.println("Working2.............................");
	 			if(rs1.getRow()>0)
	 			{
	 				cd=new ClearingObject[rs1.getRow()];
	 			
	 			rs1.beforeFirst();
	 			int i=0;
	 			int ctrlno=0;
	 			while(rs1.next())
	 			{

	 				ResultSet rs=null;
	 				ctrlno=rs1.getInt("ctrl_no");
	 				String acc_type = rs1.getString("cr_ac_type");
	 				
	 				if(rs1.getString("mult_cr").equals("F") && rs1.getString("doc_bs").equals("S")){

	 					if( acc_type != null &&   (acc_type.startsWith("1002") || acc_type.startsWith("1007")) )
	 						rs=stat.executeQuery("select clg_date,clg_no,ctrl_no,trn_type,doc_bs,ack_no,no_docs,trn_amt,doc_tot,bm1.br_name,bm.br_name,chqddpo,qdp_no,qdp_dt,moduleabbr,cr_ac_type,cr_ac_no,payee_name,bank_name,ch.br_name,mult_cr,comp_name,disc_amt,po_comm,ch.de_user,ch.de_tml,ch.de_dt_time,ch.ve_user,ch.ve_tml,ch.ve_dt_time,fname,mname,lname from Cheque ch,AccountMaster am,CustomerMaster cm,BankMaster,BranchMaster bm1,BranchMaster bm,Modules where desp_ind='T' and clg_date='"+date+"'and clg_no="+clgno+" and ctrl_no="+ctrlno+" and cr_ac_type=am.ac_type and cr_ac_no=am.ac_no and am.cid=cm.cid and substring( bank_cd,4,3)=bank_code and doc_sou=bm1.br_code and send_to=bm.br_code and cr_ac_type=modulecode group by ctrl_no");
	 					
	 					else if ( acc_type != null && (acc_type.startsWith("1014")||acc_type.startsWith("1015")) ) 
	 						rs=stat.executeQuery("select clg_date,clg_no,ctrl_no,trn_type,doc_bs,ack_no,no_docs,trn_amt,doc_tot,bm1.br_name,bm.br_name,chqddpo,qdp_no,qdp_dt,moduleabbr,cr_ac_type,cr_ac_no,payee_name,bank_name,ch.br_name,mult_cr,comp_name,disc_amt,po_comm,ch.de_user,ch.de_tml,ch.de_dt_time,ch.ve_user,ch.ve_tml,ch.ve_dt_time,fname,mname,lname from Cheque ch,ODCCMaster am,CustomerMaster cm,BankMaster,BranchMaster bm1,BranchMaster bm,Modules where desp_ind='T' and clg_date='"+date+"'and clg_no="+clgno+" and ctrl_no="+ctrlno+" and cr_ac_type=am.ac_type and cr_ac_no=am.ac_no and am.cid=cm.cid and substring( bank_cd,4,3)=bank_code and doc_sou=bm1.br_code and send_to=bm.br_code and cr_ac_type=modulecode group by ctrl_no");
	 					
	 					else if (acc_type != null &&  acc_type.startsWith("1008") ) 
	 						rs=stat.executeQuery("select clg_date,clg_no,ctrl_no,trn_type,doc_bs,ack_no,no_docs,trn_amt,doc_tot,bm1.br_name,bm.br_name,chqddpo,qdp_no,qdp_dt,moduleabbr,cr_ac_type,cr_ac_no,payee_name,bank_name,ch.br_name,mult_cr,comp_name,disc_amt,po_comm,ch.de_user,ch.de_tml,ch.de_dt_time,ch.ve_user,ch.ve_tml,ch.ve_dt_time,fname,mname,lname from Cheque ch,LoanMaster am,CustomerMaster cm,BankMaster,BranchMaster bm1,BranchMaster bm,Modules where desp_ind='T' and clg_date='"+date+"'and clg_no="+clgno+" and ctrl_no="+ctrlno+" and cr_ac_type=am.ac_type and cr_ac_no=am.ac_no and am.cid=cm.cid and substring( bank_cd,4,3)=bank_code and doc_sou=bm1.br_code and send_to=bm.br_code and cr_ac_type=modulecode group by ctrl_no");
	 					
	 					System.out.println("Working3.............................");
	 				}
	 				else{
	 					//rs=stat.executeQuery("select clg_date,clg_no,ctrl_no,trn_type,doc_bs,ack_no,no_docs,trn_amt,doc_tot,bm1.br_name,bm.br_name,chqddpo,qdp_no,qdp_dt,cr_ac_type,cr_ac_no,payee_name,bank_name,ch.br_name,mult_cr,comp_name,disc_amt,po_comm,ch.de_user,ch.de_tml,ch.de_dt_time,ch.ve_user,ch.ve_tml,ch.ve_dt_time,fname,mname,lname from Cheque ch,AccountMaster am,customerMaster cm,BankMaster,branchmaster bm1,branchmaster bm where desp_ind='T' and clg_date='"+date+"'and clg_no="+clgno+" and ctrl_no="+ctrlno+" and bank_cd=bank_code and doc_sou=bm1.br_code and doc_dest=bm.br_code group by ctrl_no");
	 					rs=stat.executeQuery("select clg_date,clg_no,ctrl_no,trn_type,doc_bs,ack_no,no_docs,trn_amt,doc_tot,bm1.br_name,bm.br_name,chqddpo,qdp_no,qdp_dt,ifnull(cr_ac_type,'') as cr_ac_type,ifnull(cr_ac_no,'') as cr_ac_no,payee_name,bank_name,ch.br_name,mult_cr,comp_name,disc_amt,po_comm,ch.de_user,ch.de_tml,ch.de_dt_time,ch.ve_user,ch.ve_tml,ch.ve_dt_time from Cheque ch,BankMaster,BranchMaster bm1,BranchMaster bm where desp_ind='T' and clg_date='"+date+"'and clg_no="+clgno+" and ctrl_no="+ctrlno+" and substring( bank_cd,4,3)=bank_code and doc_sou=bm1.br_code and send_to=bm.br_code group by ctrl_no");
	 				}
	 				
	 				if(rs != null)
	 				{
	 					rs.last();
	 					if(rs.getRow() > 0 )
	 					{
	 						rs.beforeFirst();
	 						if(rs.next())
	 						{
	 							cd[i]=new ClearingObject();
	 						
	 							cd[i].setClgDate(rs.getString("clg_date"));
	 							cd[i].setClgNo(rs.getInt("clg_no"));
	 						
	 							cd[i].setControlNo(rs.getInt("ctrl_no"));
	 							System.out.println("Control no is "+rs.getInt("ctrl_no") );
	 						
	 							cd[i].setTranType(rs.getString("trn_type"));
	 							cd[i].setDocBs(rs.getString("doc_bs"));
	 							
	 							cd[i].setAckNo(rs.getInt("ack_no"));
	 							cd[i].setNoOfDocs(rs.getInt("no_docs"));
	 							cd[i].setTranAmt(rs.getDouble("trn_amt"));
	 							cd[i].setDocTotal(rs.getDouble("doc_tot"));
	 						
	 						cd[i].setBranchName(rs.getString("bm1.br_name"));
	 						cd[i].setRetNormally(rs.getString("bm.br_name"));
	 						cd[i].setChqDDPO(rs.getString("chqddpo"));
	 						
	 						cd[i].setQdpNo(rs.getInt("qdp_no"));
	 						cd[i].setQdpDate(rs.getString("qdp_dt"));
	 						cd[i].setCreditACType(rs.getString("cr_ac_type"));
	 						cd[i].setCreditACNo(rs.getInt("cr_ac_no"));
	 						String name="";
	 						if(rs1.getString("mult_cr").equals("F") && rs1.getString("doc_bs").equals("S"))
	 							name=rs.getString("fname")+" "+rs.getString("mname")+" "+rs.getString("fname");
	 						cd[i].setDespInd(name);
	 						cd[i].setPayeeName(rs.getString("payee_name"));
	 						
	 						cd[i].setTranMode(rs.getString("bank_name"));
	 						cd[i].setTrfType(rs.getString("ch.br_name"));
	 						cd[i].setMultiCredit(rs.getString("mult_cr"));
	 						
	 						cd[i].setCompanyName(rs.getString("comp_name"));
	 						cd[i].setDiscAmt(rs.getDouble("disc_amt"));
	 						cd[i].setPOCommission(rs.getDouble("po_comm"));
	 						
	 						cd[i].setDeUser(rs.getString("de_user"));
	 						cd[i].setDeTml(rs.getString("de_tml"));
	 						cd[i].setDeTime(rs.getString("de_dt_time"));
	 						
	 						cd[i].setVeUser(rs.getString("ve_user"));
	 						cd[i].setVeTml(rs.getString("ve_tml"));
	 						cd[i].setVeTime(rs.getString("ve_dt_time"));
	 						
	 						System.out.println("within end of if");
	 						System.out.println("Working4.............................");
	 					}
	 					i++;
	 				}
	 			}
	 			}
	 		}
	 		
	 		
	 			else
		 		{
		 			cd=null;
		 			return cd;
		 		}
	 		
	 		}
	 			catch(Exception sq){
	 			//cd[0]=new ClearingObject();
	 			//cd[0].setBankCode(-1);
	 			sq.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cd;
	 	}

	 	public ClearingObject[] getOutwardSummary(String date,int n,int clgno,String query)throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		ResultSet rs=null;
	 		ClearingObject[] cd=null;
	 		
	 		try
	 		{
	 			if(n==2)
	 			{
	 				conn=getConnection();
	 				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 				
	 				System.out.println("query == "+query);
	 				if(query==null)
	 				    rs=stmt.executeQuery("select bank_cd,bank_name,doc_bs,no_docs,doc_tot,trn_amt,ret_norm,bm.br_name,clg_date,clg_no from Cheque,BankMaster,BranchMaster bm where desp_ind='T' and clg_date='"+date+"' and  clg_no="+clgno+" and substring(bank_cd,4,3)=bank_code and send_to=br_code order by bank_cd ");
	 				else
	 				    rs=stmt.executeQuery("select bank_cd,bank_name,doc_bs,no_docs,doc_tot,trn_amt,ret_norm,bm.br_name,clg_date,clg_no from Cheque,BankMaster,BranchMaster bm where desp_ind='T' and clg_date='"+date+"' and  clg_no="+clgno+" and bank_cd=bank_code and send_to=br_code and ("+query+")order by bank_cd");
	 				
	 				int i=0,number=0;
	 				rs.last();
	 				if(rs.getRow()!=0)
	 				{
	 					rs.beforeFirst();
	 					while(rs.next())
	 					{
	 						if(number!=rs.getInt("bank_cd"))
	 						{
	 							i++;
	 							number=rs.getInt("bank_cd");
	 						}
	 					}
	 					cd=new ClearingObject[i];
	 					System.out.println("cd.length=="+cd.length);
	 					i=-1;
	 					number=0;
	 					rs.beforeFirst();
	 				}
	 				int count=0,cnt=0,tno;
	 				double amt=0,amount=0,sum=0,tamt=0;
	 				System.out.println(2);
	 				while(rs.next())
	 				{
	 					System.out.println(3);
	 					if(number!=rs.getInt("bank_cd"))
	 					{
	 						count=0;
	 						cnt=0;
	 						amt=0;
	 						amount=0;
	 						sum=0;
	 						i++;
	 						System.out.println("iiii==="+i);
	 						number=rs.getInt("bank_cd");
	 						cd[i]=new ClearingObject();
	 						System.out.println(4);
	 					}

	 					if(number==rs.getInt("bank_cd"))
	 					{

	 						System.out.println(5);
	 						cd[i].setBankCode(rs.getInt("bank_cd"));
	 						System.out.println("cd[i].getBankCode()=="+cd[i].getBankCode());
	 						cd[i].setBranchName(rs.getString("bank_name"));
	 						cd[i].setDocBs(rs.getString("doc_bs"));

	 						if(rs.getString("doc_bs").equals("B"))
	 						{
	 							System.out.println(6);
	 							cd[i].setNoOfDocs(rs.getInt("no_docs"));
	 							cd[i].setDiscAmt(rs.getDouble("doc_tot"));
	 							sum=sum+cd[i].getDiscAmt();
	 						}

	 						else if(rs.getString("doc_bs").equals("S"))
	 						{
	 							System.out.println(66);
	 							amt=amt+rs.getDouble("trn_amt");
	 							count++;
	 							cd[i].setControlNo(count);
	 							cd[i].setTranAmt(amt);
	 						}

	 						else if(rs.getString("ret_norm").equals("R"))
	 						{
	 							System.out.println(666);
	 							amount=amount+rs.getDouble("doc_tot");
	 							cnt++;
	 							cd[i].setPOCommission(amount);
	 							cd[i].setQdpNo(cnt);
	 						}

	 						System.out.println(7);
	 						tamt=amount+amt+sum;
	 						tno=count+cnt+cd[i].getNoOfDocs();
	 						cd[i].setFineAmt(tamt);
	 						cd[i].setAckNo(tno);
	 						cd[i].setRetNormally(rs.getString("bm.br_name"));
	 						cd[i].setClgDate(rs.getString("clg_date"));
	 						cd[i].setClgNo(rs.getInt("clg_no"));
	 						System.out.println(8);
	 					}
	 					System.out.println("i== "+i);
	 				}
	 			}
	 		}catch(SQLException sq){
	 			sq.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cd;
	 	}
	 	
	 	public ClearingObject[] getDetails(int ctrlno_from,int ctrlno_to,String str)throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		ClearingObject cl[] = null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			ResultSet rs=null;
	 			System.out.println("String == "+str);
	 			if(str == null)			
	 			    rs=stmt.executeQuery("select * from Cheque where ctrl_no between "+ctrlno_from+" and "+ctrlno_to);
	 			else
	 			    rs=stmt.executeQuery("select * from Cheque where ctrl_no between "+ctrlno_from+" and "+ctrlno_to+" or ("+str+")");
	 			rs.last();
	 			if(rs.getRow()>0)
	 			    cl= new ClearingObject[rs.getRow()];
	 			System.out.println("rows == "+rs.getRow());
	 			rs.beforeFirst();
	 			int i=0;
	 			while(rs.next()){
	 				cl[i] = new ClearingObject();
	 				cl[i].setClgDate(rs.getString("clg_date"));
	 				cl[i].setClgNo(rs.getInt("clg_no"));
	 				cl[i].setSendTo(rs.getInt("send_to"));
	 				cl[i].setControlNo(rs.getInt("ctrl_no"));
	 				cl[i].setTranType(rs.getString("trn_type"));
	 				cl[i].setClgType(rs.getString("clg_type"));
	 				cl[i].setAckNo(rs.getInt("ack_no"));
	 				cl[i].setDocSource(rs.getInt("doc_sou"));
	 				cl[i].setDocDestination(rs.getInt("doc_dest"));
	 				cl[i].setDocBs(rs.getString("doc_bs"));
	 				cl[i].setNoOfDocs(rs.getInt("no_docs"));
	 				cl[i].setDocTotal(rs.getDouble("doc_tot"));
	 				cl[i].setMultiCredit(rs.getString("mult_cr"));
	 				cl[i].setCompanyName(rs.getString("comp_name"));
	 				cl[i].setChqDDPO(rs.getString("chqddpo"));
	 				cl[i].setQdpNo(rs.getInt("qdp_no"));
	 				cl[i].setQdpDate(rs.getString("qdp_dt"));
	 				cl[i].setRetNormally(rs.getString("ret_norm"));
	 				cl[i].setPrevCtrlNo(rs.getInt("prev_ctrl_no"));
	 				//cl.setTranMode(rs.getString("trn_mode"));
	 				cl[i].setTrfType(rs.getString("trn_type"));
	 				cl[i].setCreditACType(rs.getString("cr_ac_type"));
	 				cl[i].setCreditACNo(rs.getInt("cr_ac_no"));
	 				cl[i].setPOCommission(rs.getDouble("po_comm"));
	 				cl[i].setDebitACType(rs.getString("dr_ac_type"));
	 				cl[i].setDebitACNo(rs.getInt("dr_ac_no"));
	 				cl[i].setPayeeName(rs.getString("payee_name"));
	 				cl[i].setBankCode(rs.getInt("bank_cd"));
	 				cl[i].setBranchName(rs.getString("br_name"));
	 				cl[i].setTranAmt(rs.getDouble("trn_amt"));
	 				cl[i].setToBounce(rs.getString("to_bounce"));
	 				cl[i].setDespInd(rs.getString("desp_ind"));
	 				cl[i].setPostInd(rs.getString("post_ind"));
	 				cl[i].setDiscInd(rs.getString("disc_ind"));
	 				cl[i].setDiscAmt(rs.getDouble("disc_amt"));
	 				cl[i].setExpectedClgDate(rs.getString("exp_clgdt"));
	 				cl[i].setExpClgNo(rs.getInt("exp_clgno"));
	 				cl[i].setLetterSent(rs.getString("let_sent"));
	 				cl[i].setMChangeAmt(rs.getDouble("mchg_amt"));
	 				cl[i].setFineAmt(rs.getDouble("fine_amt"));
	 				cl[i].setDeTml(rs.getString("de_tml"));
	 				cl[i].setDeUser(rs.getString("de_user"));
	 				cl[i].setDeTime(rs.getString("de_dt_time"));
	 				cl[i].setVeTml(rs.getString("ve_tml"));
	 				cl[i].setVeUser(rs.getString("ve_user"));
	 				cl[i].setVeTime(rs.getString("ve_dt_time"));
	 				i++;
	 			}
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cl;
	 	}

	 	public ClearingObject[] getOutwardSlips(String date,int n,String query)throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		ResultSet rs=null;
	 		ClearingObject[] cd=null;

	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

	 			if(query == null)
	 			    rs=stmt.executeQuery("select ctrl_no,cr_ac_type,cr_ac_no,bank_cd,bank_name,qdp_no,bm.br_name,bm1.br_name,doc_bs,no_docs,doc_tot,trn_amt,clg_date from Cheque,BankMaster,BranchMaster bm,BranchMaster bm1 where desp_ind='T' and clg_date='"+date+"' and clg_no="+n+" and substring(bank_cd,4,3)=bank_code  and doc_sou=bm.br_code and send_to=bm1.br_code order by bank_cd,ctrl_no ");
	 			else
	 			    rs=stmt.executeQuery("select ctrl_no,cr_ac_type,cr_ac_no,bank_cd,bank_name,qdp_no,bm.br_name,bm1.br_name,doc_bs,no_docs,doc_tot,trn_amt,clg_date from Cheque,BankMaster,BranchMaster bm,BranchMaster bm1 where desp_ind='T' and clg_date='"+date+"' and clg_no="+n+" and bank_cd=bank_code  and doc_sou=bm.br_code and send_to=bm1.br_code  and ("+query+")order by bank_cd,ctrl_no");
	 			rs.last();
	 			if(rs.getRow()==0)
	 			{
	 				cd=null;
	 				return cd;
	 			}
	 			else
	 			{
	 			cd=new ClearingObject[rs.getRow()];
	 			System.out.println("cd.length=="+cd.length);
	 			rs.beforeFirst();
	 			int i=0;
	 			while(rs.next())
	 			{
	 				System.out.println("i===="+i);
	 				cd[i]=new ClearingObject();
	 				cd[i].setControlNo(rs.getInt("ctrl_no"));
	 				cd[i].setBankCode(rs.getInt("bank_cd"));
	 				cd[i].setBranchName(rs.getString("bank_name"));
	 				cd[i].setRetNormally(rs.getString("bm1.br_name"));
	 				cd[i].setQdpNo(rs.getInt("qdp_no"));
	 				cd[i].setCompanyName(rs.getString("bm.br_name"));
	 				cd[i].setDocBs(rs.getString("doc_bs"));
	 				cd[i].setCreditACType(rs.getString("cr_ac_type"));
	 				cd[i].setCreditACNo(rs.getInt("cr_ac_no"));
	 				cd[i].setNoOfDocs(rs.getInt("no_docs"));
	 				cd[i].setDiscAmt(rs.getDouble("doc_tot"));
	 				cd[i].setTranAmt(rs.getDouble("trn_amt"));
	 				cd[i].setClgDate(rs.getString("clg_date"));
	 				cd[i].setClgNo(n);
	 				i++;
	 			}
	 			}
	 		}catch(SQLException sq){
	 			sq.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cd;
	 	}

	 	//=====================Shettys methods===================

	 	public AccountMasterObject getAccountInfoInd(String acctype ,int accno) throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		AccountMasterObject am=new AccountMasterObject();
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stat=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			ResultSet ret  =null;
	 			if(acctype.startsWith("1002")||acctype.startsWith("1007"))
	 				ret = stat.executeQuery("select * from AccountMaster where ac_no="+accno+" and ac_type='"+acctype+"' and ve_tml is not null");
	 			
	 			else if(acctype.startsWith("1014")||acctype.startsWith("1015"))
	 				ret = stat.executeQuery("select * from ODCCMaster where ac_no="+accno+" and ac_type='"+acctype+"' and ve_tml is not null");
	 			
	 			else if(acctype.startsWith("1001"))
	 				ret = stat.executeQuery("select * from ShareMaster where ac_no="+accno+" and ac_type='"+acctype+"' and ve_tml is not null");
	 			
	 			else if(acctype.startsWith("1004"))
	 				ret = stat.executeQuery("select * from DepositMaster where ac_no="+accno+" and ac_type='"+acctype+"' and ve_tml is not null");

	 			if(ret.next())
	 			{
	 				System.out.println("Acc no"+ret.getInt("ac_no"));

	 				am.setAccNo(ret.getInt("ac_no"));
	 				am.setCid(ret.getInt("cid"));
	 				am.setMailingAddress(ret.getInt("addr_type"));
	 				am.setAccType(ret.getString("ac_type"));
	 				if(acctype.startsWith("1001")||acctype.startsWith("1004"))
	 					{
	 					am.setAccStatus(" ");
	 					am.setFreezeInd(" ");
	 	
	 					
	 					}
	 				else	{				
	 				am.setAccStatus(ret.getString("ac_status"));
	 				//am.setDefaultInd(ret.getString("default_ind"));
	 				am.setFreezeInd(ret.getString("freeze_ind"));
	 				}
	 				if(acctype.startsWith("1002")||acctype.startsWith("1007")||acctype.startsWith("1004"))
	 				{
	 					am.setNoOfJointHolders(ret.getInt("no_jt_hldr"));//no_jt_hldr
	 					System.out.println(ret.getInt("no_jt_hldr")+"----------------------->>>>>");
	 					System.out.println(am.getNoOfJointHolders()+ "--------------------------->>>>>>>>");
	 				

	 				//start joint and sigins...
	 				
	 				String addrtype[]=new String[am.getNoOfJointHolders()+1];
	 				String cidtype[]=new String[am.getNoOfJointHolders()+1];
	 				String op[]=new String[am.getNoOfJointHolders()+1];
	 				
	 				ret = stat.executeQuery("select cid,typeofopr from SignatureInstruction where ac_type='"+acctype+"' and ac_no='"+accno+"' order by cid");
	 				int j=0;
	 				ret.last();
	 				//am.setCidLength(ret.getRow());
	 				ret.beforeFirst();
	 				
	 				while(ret.next())
	 				{
	 					
	 					op[j]=ret.getString(2);
	 					cidtype[j]=ret.getString(1);
	 					//am.setCid(j,cidtype[j]);
	 					j++;
	 				}
	 				
	 				if(cidtype.length>1)
	 				{
	 					System.out.println (1212);
	 					for(int l=0;l<cidtype.length;l++)
	 					{
	 						System.out.println (1213); 
	 						if(am.getCid()==Integer.parseInt(cidtype[l]))
	 						{
	 							
	 							String temp = cidtype[0];
	 							cidtype[0] = cidtype[l];
	 							cidtype[l] = temp;
	 							//am.setCid(0,cidtype[0]);
	 							//am.setCid(l,cidtype[l]);
	 							
	 							break;
	 						}
	 					}
	 				}
	 				am.setTypeofOp(op);
	 				j=0;
	 				//addrtype[j]=am.getMailingAddress();
	 				j++;
	 				if(am.getNoOfJointHolders()>0){
	 					ret = stat.executeQuery("select addr_type from JointHolders where ac_type='"+acctype+"' and ac_no="+accno+" order by cid");
	 					while(ret.next())
	 					{
	 						addrtype[j]=ret.getString(1);
	 						//cidtype[j]=rs3.getString(1);
	 						j++;
	 					}
	 					}
	 				//rs3.close();
	 				//am.setJointAddrType(addrtype);
	 				//end joint and sigins...

	 				ret = stat.executeQuery("select cl_bal from AccountTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is not null order by trn_seq desc limit 1");
	 				ret.next();
	 				am.setCloseBal(ret.getDouble(1));//sum close bal
	 				ret = stat.executeQuery("select sum(cl_bal) from AccountTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is null and cd_ind='C'");
	 				ret.next();
	 				am.setPreCloseBal(ret.getDouble(1));// sum credit entry
	 				ret = stat.executeQuery("select sum(cl_bal) from AccountTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is null and cd_ind='D'");
	 				ret.next();
	 				am.setTransAmount(ret.getDouble(1));//sum debit entry
	 			}
	 				else if(acctype.startsWith("1014")||acctype.startsWith("1015"))
	 				{
	 					ret = stat.executeQuery("select cl_bal from ODCCTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is not null order by trn_seq desc limit 1");
	 					ret.next();
	 					am.setCloseBal(ret.getDouble(1));//sum close bal
	 					ret = stat.executeQuery("select sum(cl_bal) from ODCCTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is null and cd_ind='C'");
	 					ret.next();
	 					am.setPreCloseBal(ret.getDouble(1));// sum credit entry
	 					ret = stat.executeQuery("select sum(cl_bal) from ODCCTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is null and cd_ind='D'");
	 					ret.next();
	 					am.setTransAmount(ret.getDouble(1));//sum debit entry
	 					
	 				}
	 				
	 				else if(acctype.startsWith("1001"))
	 				{
	 					ret = stat.executeQuery("select share_bal from ShareTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is not null order by trn_seq desc limit 1");
	 					ret.next();
	 					am.setCloseBal(ret.getDouble(1));//sum close bal
	 					ret = stat.executeQuery("select sum(share_bal) from ShareTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is null and cd_ind='C'");
	 					ret.next();
	 					am.setPreCloseBal(ret.getDouble(1));// sum credit entry
	 					ret = stat.executeQuery("select sum(share_bal) from ShareTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is null and cd_ind='D'");
	 					ret.next();
	 					am.setTransAmount(ret.getDouble(1));//sum debit entry
	 					
	 				}
	 				
	 				
	 			}
	 			}catch(SQLException info){
	 			//am.setAccNo(-1);
	 			info.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return am;
	 	}

	 	public int updateNegative(long ctrl_no,double amt,String vetml,String veuser) throws RecordNotUpdatedException
	 	{
	 	    Connection conn=null;
	 	    int updated=0;
	 	    try
	 	    {
	 	     conn=getConnection();
	 	     Statement stmt=conn.createStatement(ResultSet.CONCUR_UPDATABLE,ResultSet.TYPE_SCROLL_SENSITIVE);
	 	     
//	 	   credit gl to clg_INApexBank
	 			String acc_type=null;
	 			int acc_no=0;
	 			String doc_sou=null;
	 			ResultSet ret=null;
	 			ResultSet rt= stmt.executeQuery("select doc_sou from Cheque where ctrl_no="+ctrl_no);
	 			if(rt.next())
	 			{
	 		ResultSet rs= stmt.executeQuery(" select * from BranchMaster where br_code="+doc_sou);
	 		if(rs.next())
	 		{
	 			 acc_type=rs.getString("br_ac_type");
	 		
	 		 acc_no=rs.getInt("br_ac_no");
	 		}
	 		ret = stmt.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =1 and gk.ac_type = '1011001'");
	 		if(ret.next())
	 		{
	 			
	 			System.out.println("its inside gl");
	 			GLTransObject trnobj=new GLTransObject();
	 			trnobj.setTrnDate(getToday());
	 			trnobj.setGLType(ret.getString(2));
	 			trnobj.setGLCode(ret.getString(1));
	 			trnobj.setTrnMode("G");
	 			trnobj.setAmount(amt);
	 			trnobj.setCdind("C");
	 			trnobj.setAccType(acc_type);
	 			System.out.println("acc_type"+acc_type);
	 			trnobj.setAccNo(String.valueOf(acc_no));
	 			System.out.println("acc_no"+acc_no);
	 			trnobj.setTrnType("");
	 			trnobj.setRefNo(0);
	 			trnobj.setTrnSeq(0);
	 			trnobj.setVtml(vetml);
	 			trnobj.setVid(veuser);System.out.println(34);

	 			comm.storeGLTransaction(trnobj);System.out.println(35);
	 			
	 		}
	 		else{
	 			sessionContext.setRollbackOnly();
	 		    throw new SQLException();
	 		}
	 		
	 			
	 			ret = stmt.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =12 and gk.ac_type = '1011001'");
	 			if(ret.next())
	 			{
	 				
	 				System.out.println("its inside gl");
	 				GLTransObject trnobj=new GLTransObject();
	 				trnobj.setTrnDate(getToday());
	 				trnobj.setGLType(ret.getString(2));
	 				trnobj.setGLCode(ret.getString(1));
	 				trnobj.setTrnMode("G");
	 				trnobj.setAmount(amt);
	 				trnobj.setCdind("D");
	 				trnobj.setAccType(acc_type);
	 				System.out.println("acc_type"+acc_type);
	 				trnobj.setAccNo(String.valueOf(acc_no));
	 				System.out.println("acc_no"+acc_no);
	 				trnobj.setTrnType("");
	 				trnobj.setRefNo(0);
	 				trnobj.setTrnSeq(0);
	 				trnobj.setVtml(vetml);
	 				trnobj.setVid(veuser);System.out.println(34);

	 				comm.storeGLTransaction(trnobj);
	 				System.out.println(35);
	 		}
	 		else{
	 			sessionContext.setRollbackOnly();
	 		    throw new SQLException();
	 		}
	 		
	 			}
	 			else{
	 				sessionContext.setRollbackOnly();
	 			    throw new SQLException();
	 			}
	 	     
	 	     
	 	     
	 	     updated=stmt.executeUpdate("update Cheque set clg_type='O',clg_date=null,send_to=null where ctrl_no="+ctrl_no);
	 	     if(updated<=0){
	 	    	sessionContext.setRollbackOnly();
	 	    	 throw new SQLException();
	 	     }
	 	         
	 			}
	 	    
	 	    catch(SQLException e)
	 	    {
	 	        e.printStackTrace();
	 	       sessionContext.setRollbackOnly();
	 	    }catch(Exception e){
	 	    	e.printStackTrace();
	 	    	sessionContext.setRollbackOnly();
	 	    }
	 	    
	 	    finally
	 	    {
	 	        try
	 	        {
	 	        conn.close();
	 	        }catch(SQLException d)
	 	        {d.printStackTrace();  }
	 	    
	 	    }

	 	    return updated;
	 	}
	 	
	 	public ChequeDepositionObject[] getOutwardDespCheque(String act_type, int ac_no) throws RecordsNotFoundException
	 	{
	 		
	 		ChequeDepositionObject[] che_obj = null;
	 		
	 		Connection conn = null;
	 		Statement stmt = null;
	 		
	 		try{
	 			
	 			conn = getConnection();
	 			stmt = conn.createStatement();
	 			ResultSet rs = stmt.executeQuery( "select  ch.* , mo.moduleabbr  from Cheque ch , Modules mo where clg_type = 'O' and desp_ind = 'T' and post_ind = 'F'  and to_bounce = 'F' and cr_ac_type = '" + act_type+"' and cr_ac_no = "+ ac_no + " and mo.modulecode = cr_ac_type" );
	 			
	 			
	 			if ( rs.next()){
	 				
	 				rs.last();
	 				che_obj = new ChequeDepositionObject[rs.getRow()];
	 				
	 				rs.beforeFirst();
	 				int i = 0;
	 				while ( rs.next()){
	 					
	 					che_obj[i] = new ChequeDepositionObject();
	 					che_obj[i].setCreditACType(rs.getString("moduleabbr"));
	 					che_obj[i].setCreditACNo(rs.getInt("cr_ac_no"));
	 					che_obj[i].setTranAmt(rs.getDouble("trn_amt"));
	 					che_obj[i].setQdpDate(rs.getString("qdp_dt"));
	 					che_obj[i].setQdpNo(rs.getInt("qdp_no"));
	 					i++;
	 				}
	 				
	 				
	 				
	 			}
	 			
	 		} catch (SQLException e) {

	 			throw new RecordsNotFoundException(); 
	 			
	 		} finally{
	 			
	 			try{
	 			
	 				conn.close();
	 			
	 			} catch (SQLException e) {
	 				
	 				throw new RecordsNotFoundException();
	 			}
	 		}
	 		
	 		
	 		return che_obj;
	 	}
	 	
	 	
	 	public Reason[] getReasonDetails(String acc_type,int value) throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		Reason rea[] = null;
	 		ResultSet ret  =null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stat=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 			if(value==0){//inward bounced ..i.e retrieve fine amount
	 			
	 				ret = stat.executeQuery("select code,reason,link_code,return_fine from BounceFine,ReasonMaster left join ReasonLink on code=r_code where code=b_code and acc_type='"+acc_type+"' order by code ,link_code");
	 				
	 				if(!ret.next())
	 					ret = stat.executeQuery("select code,reason,link_code,return_fine from BounceFine,ReasonMaster left join ReasonLink on code=r_code where code=b_code group by code order by code ,link_code");
	 			}
	 			  
	 			else if(value==1){//outward returns...i.e retrieve return fine amount
	 				ret = stat.executeQuery("select code,reason,link_code,fine from BounceFine,ReasonMaster left join ReasonLink on code=r_code where code=b_code and acc_type='"+acc_type+"' order by code ,link_code");
	 			}
	 			//else if(value==2)
	 				//ret = stat.executeQuery("select code,reason,link_code,return_fine from reasonmaster,bounce_fine left join reasonlink on code=r_code where code=b_code and acc_type='"+acc_type+"' order by code ,link_code");
	 			ret.last();
	 			if(ret.getRow()==0)
	 				throw new RecordsNotFoundException();
	 			rea = new Reason[ret.getRow()];
	 			ret.beforeFirst();

	 			int i=0;
	 			while(ret.next())
	 			{
	 				rea[i] = new Reason();
	 				rea[i].setReasonCd(ret.getInt(1));
	 				rea[i].setReasonDesc(ret.getString(2));
	 				rea[i].setLinkReasonCd(ret.getInt(3));
	 				
	 				    rea[i].setBounceFine(ret.getDouble(4));
	 				i++;
	 			}
	 		}catch(SQLException reason){
	 			reason.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return rea;
	 	}
	 	
	 	
	 	public int loadFile(File fc)
	 	{
	 		Connection conn=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 						
	 			System.out.println(fc.getAbsolutePath());

	 			GregorianCalendar gc=new GregorianCalendar();
	 			String table="t"+gc.getTimeInMillis();

	 			FileInputStream fis=new FileInputStream(fc);
	 			File file=new File("c:\\"+table);
	 			FileOutputStream f=new FileOutputStream(file);
	 			System.out.println (file.getParent());
	 			int ii=0;
	 			while((ii=fis.read())!=-1)
	 				f.write(ii);
	 			f.close();


	 			System.out.println("File:"+table);

	 			st.executeUpdate("create table "+table+" type=memory select * from Cheque limit 0");
	 			st.executeUpdate("alter table "+table+" modify ctrl_no int");
	 			int n=st.executeUpdate("load data local infile 'c:/"+table+"' into table "+table+"(bank_cd,br_name,trn_type,clg_type,doc_sou,doc_dest,doc_bs,no_docs,trn_amt,to_bounce,mult_cr,comp_name,cr_ac_type,cr_ac_no,chqddpo,qdp_no,qdp_dt,po_comm,disc_ind,disc_amt)");
	 			file.delete();
	 			
	 			ResultSet rs=st.executeQuery("select lst_ctrl_no from genparam");
	 			rs.next();
	 			int jj=rs.getInt(1);
	 				
	 			for(int i=0;i<n;i++)
	 				st.addBatch("update "+table+" set ctrl_no="+(++jj)+" where ctrl_no is null limit 1");
	 			st.addBatch("update genparam set lst_ctrl_no="+jj);
	 			st.addBatch("insert into Cheque select * from "+table);
	 			st.addBatch("drop table "+table);
	 			st.executeBatch();
	 			
	 			return 0;
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e1) {
	 				e1.printStackTrace();
	 			}
	 			System.gc();
	 		}
	 		return -1;
	 	}
	 	
	 	public File StoreFile()
	 	{
	 		File file=null;
	 		try
	 		{
	 			//Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 						
	 			GregorianCalendar gc=new GregorianCalendar();
	 			String str=String.valueOf(gc.getTimeInMillis());
	 			
	 			System.out.println(file);
	 			//ResultSet rs = st.executeQuery("select bank_cd,br_name,trn_type,clg_type,doc_sou,doc_dest,doc_bs,no_docs,trn_amt,to_bounce,mult_cr,comp_name,cr_ac_type,cr_ac_no,chqddpo,qdp_no,qdp_dt,po_comm,disc_ind,disc_amt into outfile '"+file+"' from Cheque where clg_type='O' and clg_date='"+getToday()+"'and desp_ind='T'");
	 			file=new File("C:\\Program Files\\MySQL\\Data\\BKCB22JULY\\"+str);
	 					
	 			return file;
	 			
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{System.gc();}
	 	
	 		return file;
	 	}
	 	
	 	public boolean storeTemplate(String text,String acty,int stage,String user,String tml,int temp_no) throws RecordNotInsertedException
	 	{
	 		Connection conn=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			if(temp_no==0)
	 			{
	 				ResultSet rs=stmt.executeQuery("select max(temp_no) from Template where ac_type='"+acty+"'");
	 				PreparedStatement pstmt=conn.prepareStatement("insert into Template values(?,?,?,?,?,?,'"+ getToday()+ "')");
	 				if(rs.next())
	 				{
	 					pstmt.setString(1,acty);
	 					pstmt.setInt(2,rs.getInt(1)+1);
	 					pstmt.setInt(3,stage);
	 					pstmt.setString(4,text);
	 					pstmt.setString(5,user);
	 					pstmt.setString(6,tml);
	 					if(pstmt.executeUpdate()==1)
	 						return true;
	 					return false;
	 				}
	 			}
	 			else if(stmt.executeUpdate("update Template set text='"+text+"' where ac_type='"+acty+"' and temp_no="+temp_no+"")==1)
	 				return true;
	 		}catch(Exception ex){
	 			ex.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException(); 
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return false;
	 	}
	 	
	 	public String[] getTemplate(int stage,String acty) throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		String str[]=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 			ResultSet rs=stmt.executeQuery("select * from Template where ac_type='"+acty+"' and stage_no="+stage+"");
	 			if(rs.next())
	 			{
	 				rs.last();
	 				str=new String[rs.getRow()];
	 				rs.beforeFirst();
	 			}
	 			int i=0;
	 			while(rs.next())
	 			{
	 				str[i++]=rs.getString("text")+"%%%%%"+rs.getInt("temp_no");
	 			}
	 		}catch(SQLException ex){
	 			ex.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return str;
	 	}
	 	
	 	
	 	public void updateBounceChequeSummary(ClearingObject[] cl)throws RecordNotInsertedException
	 	{
	 		Connection conn=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 			for(int i=0;i<cl.length;i++)
	 			{
	 				if(cl[i].getClgNo()==0)
	 				{ 
	 					//if(cl[i].getRetNormally().equals("N"))
	 						stmt.addBatch("update Cheque set let_sent='T' where dr_ac_type='"+cl[i].getDebitACType()+"' and dr_ac_no="+cl[i].getDebitACNo());
	 					//else if(cl[i].getRetNormally().equals("R"))
	 					//	stmt.addBatch("update cheque set let_sent='T' where cr_ac_type='"+cl[i].getCreditACType()+"' and cr_ac_no="+cl[i].getCreditACNo());
	 				}
	 			}
	 			stmt.executeBatch();		
	 		}catch(Exception eeee){
	 			eeee.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 	}
	 		
	 	public ClearingObject[] getBouncedCheque()throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		ClearingObject cl[]=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			ResultSet rs=stmt.executeQuery("select * from Cheque where to_bounce='T' and let_sent='F'");
	 			//if(rs.next())
	 			//{
	 				rs.last();
	 				if(rs.getRow()==0)
	 					throw new RecordsNotFoundException();
	 				cl=new ClearingObject[rs.getRow()];
	 				rs.beforeFirst();
	 			//}
	 			
	 			int i=0;
	 			
	 			while(rs.next())
	 			{
	 				cl[i] = new ClearingObject();
	 				
	 				cl[i].setClgDate(rs.getString("clg_date"));
	 				cl[i].setSendTo(rs.getInt("send_to"));
	 				cl[i].setControlNo(rs.getInt("ctrl_no"));
	 								
	 				cl[i].setClgType(rs.getString("clg_type"));
	 				cl[i].setDocSource(rs.getInt("doc_sou"));
	 				cl[i].setDocDestination(rs.getInt("doc_dest"));
	 				
	 				cl[i].setAckNo(rs.getInt("ack_no"));
	 				cl[i].setDocBs(rs.getString("doc_bs"));
	 				cl[i].setNoOfDocs(rs.getInt("no_docs"));
	 				
	 				cl[i].setDocTotal(rs.getDouble("doc_tot"));
	 				cl[i].setMultiCredit(rs.getString("mult_cr"));
	 				cl[i].setCompanyName(rs.getString("comp_name"));
	 				
	 				cl[i].setChqDDPO(rs.getString("chqddpo"));
	 				cl[i].setQdpNo(rs.getInt("qdp_no"));
	 				cl[i].setQdpDate(rs.getString("qdp_dt"));
	 				
	 				cl[i].setRetNormally(rs.getString("ret_norm"));
	 				cl[i].setPrevCtrlNo(rs.getInt("prev_ctrl_no"));
	 				
	 				cl[i].setCreditACType(rs.getString("cr_ac_type"));
	 				cl[i].setCreditACNo(rs.getInt("cr_ac_no"));
	 				
	 				cl[i].setDebitACType(rs.getString("dr_ac_type"));
	 				cl[i].setDebitACNo(rs.getInt("dr_ac_no"));
	 				
	 				cl[i].setBankCode(rs.getInt("bank_cd"));
	 				cl[i].setBranchName(rs.getString("br_name"));
	 				cl[i].setTranAmt(rs.getDouble("trn_amt"));
	 				cl[i].setClgNo(1);
	 				i++;
	 			}
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cl;
	 	}
	 		
	 	public Object[][] getBouncedChequeReport(String acty,String ac_no) throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		ResultSet rs=null;
	 		ResultSetMetaData rsmd=null;
	 		Object ob[][]=null;
	 		int k=0;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			//rs=stmt.executeQuery("select b  ln_ac_ty,ln_ac_no,appn_srl,appn_date,req_amt,lnee_sh_no,lnee_sh_ty,ac_ty,ac_no,sanc_amt,sanc_dt,inst_amt,fname,mname,lname,address,city,state,pin,country,phno,remd_no from LoanMaster,CustomerMaster,CustomerAddr,ShareMaster where LoanMaster.ln_ac_ty='"+lnacty+"' and LoanMaster.ln_ac_no in ("+ac_no+") and ShareMaster.sh_no=LoanMaster.lnee_sh_no and ShareMaster.sh_type=LoanMaster.lnee_sh_ty and CustomerMaster.cid=ShareMaster.cid and CustomerAddr.cid=ShareMaster.cid and CustomerAddr.addtype=LoanMaster.addr_type and LoanMaster.ve_tml is not null and sanc_ver='Y'");
	 			//rs=stmt.executeQuery("select ch.*,fname,mname,lname,address,city,state,pin,country,phno from Cheque ch,AccountMaster am,CustomerMaster cm,CustomerAddr ca where to_bounce='T' and let_sent is null and dr_ac_type='"+acty+"' and dr_ac_no in ("+ac_no+") and dr_ac_type=am.ac_ty and dr_ac_no=am.ac_no and am.cid=cm.cid and cm.cid=ca.cid");
	 			System.out.println("account type == "+acty);
	 			System.out.println("account Number == "+ac_no);
	 			rs=stmt.executeQuery("select ch.*,fname,mname,lname,address,city,state,pin,country,phno from Cheque ch,AccountMaster am,CustomerMaster cm,CustomerAddr ca where to_bounce='T' and let_sent='F' and dr_ac_type in("+acty+") and dr_ac_no in ("+ac_no+") and dr_ac_type=am.ac_type and dr_ac_no=am.ac_no and am.cid=cm.cid and cm.cid=ca.cid group by ctrl_no");
	 			rsmd=rs.getMetaData();
	 			int a=rsmd.getColumnCount();
	 			//if(rs.next())
	 			//{
	 				rs.last();
	 				if(rs.getRow()==0)
	 					throw new RecordsNotFoundException();
	 				ob=new Object[rs.getRow()][a];	
	 				rs.beforeFirst();
	 			//}
	 			int i=0;
	 			int j=0;
	 			System.out.println("1........");
	 			
	 			while(rs.next())
	 			{
	 				System.out.println("3........");
	 				for(k=0;k<ob.length;k++)
	 				ob[i][k]=new Object();
	 				if(rs.getString("ret_norm").equals("N"))
	 				{
	 					ob[i][j++]=rs.getString("dr_ac_type");
	 					ob[i][j++]=String.valueOf(rs.getInt("dr_ac_no"));
	 				}
	 				else
	 				{
	 					ob[i][j++]=rs.getString("cr_ac_type");
	 					ob[i][j++]=String.valueOf(rs.getInt("cr_ac_no"));
	 				}	
	 				
	 				ob[i][j++]=String.valueOf(rs.getInt("send_to"));
	 				ob[i][j++]=String.valueOf(rs.getInt("doc_sou"));
	 				ob[i][j++]=String.valueOf(rs.getInt("doc_dest"));
	 				
	 				ob[i][j++]=rs.getString("chqddpo");
	 				ob[i][j++]=String.valueOf(rs.getInt("qdp_no"));
	 				ob[i][j++]=rs.getString("qdp_dt");
	 							
	 				ob[i][j++]=rs.getString("bank_cd");
	 				ob[i][j++]=rs.getString("br_name");
	 				
	 				ob[i][j++]=String.valueOf(rs.getDouble("trn_amt"));
	 				ob[i][j++]=String.valueOf(rs.getDouble("doc_tot"));
	 				
	 				ob[i][j++]=rs.getString("fname");
	 				ob[i][j++]=rs.getString("mname");
	 				ob[i][j++]=rs.getString("lname");
	 				ob[i][j++]=rs.getString("address");
	 				ob[i][j++]=rs.getString("city");
	 				ob[i][j++]=rs.getString("state");
	 				ob[i][j++]=String.valueOf(rs.getInt("pin"));
	 				ob[i][j++]=rs.getString("country");
	 				ob[i][j++]=String.valueOf(rs.getInt("phno"));
	 				ob[i][j++]=String.valueOf(rs.getDouble("fine_amt"));
	 				//ob[i][j++]=rs.getString("ret_norm");
	 				j=0;
	 				i++;	
	 			}
	 			//return ob;
	 		}catch(SQLException ex){
	 			ex.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		
	 		return ob;
	 	}
	 	
	 	public ReasonMaster[] getReasons() throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		ReasonMaster rm[]=null;
	 		try 
	 		{
	 			conn=getConnection();
	 	    	Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	 	    	ResultSet rs = stmt.executeQuery("select * from ReasonMaster order by code ");
	 	    	rs.last();
	 	    	System.out.println("rs.length == "+rs.getRow());
	 	    	if(rs.getRow()>0)
	 	    	{
	 	    	   	rm = new ReasonMaster[rs.getRow()];
	 		    	rs.beforeFirst();
	 		    	int i=0;
	 		    	while(rs.next())
	 		    	{
	 		    		rm[i] = new ReasonMaster();
	 		    		rm[i].setCode(rs.getInt("code"));
	 		    		rm[i].setReason(rs.getString("reason"));
	 		    		rm[i].setDe_tml(rs.getString("de_tml"));
	 		    		rm[i].setDe_user(rs.getString("de_user"));
	 		    		rm[i].setDe_date(rs.getString("de_date"));//    
	 		    		i++;
	 		    	}
	 			}
	 	    	else{
	 	    		rm=null;
	 	    		throw new RecordsNotFoundException();
	 	    	}
	 	    }catch (SQLException ex){
	 	    	ex.printStackTrace();
	 	    	throw new RecordsNotFoundException();
	 	    }
	 	    finally{
	 	    	try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 	    }
	 	    return rm;
	 	}
	 	
	 	public ReasonLink[] getLinkReasons() throws RecordsNotFoundException
	 	{
	 		Connection conn=null;
	 		ReasonLink rl[]=null;
	 		try 
	 		{
	 			conn = getConnection();
	 	    	Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	 	    	ResultSet rs = stmt.executeQuery("select * from ReasonLink order by link_code ");
	 	    	rs.last();
	 	    	System.out.println("rs.length == "+rs.getRow());
	 	    	if(rs.getRow()>0)
	 	    	{
	 	    	   	rl = new ReasonLink[rs.getRow()];
	 		    	rs.beforeFirst();
	 		    	int i=0;
	 		    	while(rs.next())
	 		    	{
	 		    		rl[i] = new ReasonLink();
	 		    		rl[i].setLinkCode(rs.getInt("link_code"));
	 		    		rl[i].setLinkDiscription(rs.getString("link_desc"));
	 		    		rl[i].setReasonCode(rs.getInt("r_code"));
	 		    		rl[i].setDe_tml(rs.getString("de_tml"));
	 		    		rl[i].setDe_user(rs.getString("de_user"));
	 		    		rl[i].setDe_date(rs.getString("de_dt_time"));//    
	 		    		i++;
	 		    	}
	 			}
	 	    	else{
	 	    		rl=null;
	 	    		throw new RecordsNotFoundException();
	 	    	}
	 	    }catch (SQLException ex){
	 	    	ex.printStackTrace();
	 	    	throw new RecordsNotFoundException();
	 	    }
	 	    finally{
	 	    	try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 	    }
	 	    return rl;
	 	}
	 	
	 	public Object getGenParam(String column)
	 	{
	 		Object obj=null;
	 		Connection conn=null;
	 		Statement stmt=null;
	 		ResultSet rs=null;
	 		try{
	 			conn=getConnection();	
	 			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
	 			
	 			rs=stmt.executeQuery("select "+column+" from Modules where modulecode=1011001");
	 			rs.next();
	 			obj=rs.getObject(1);
	 			rs.close();
	 			
	 		}catch(Exception exception){exception.printStackTrace();}
	 		finally{
	 			try{		    	
	 				conn.close();
	 			}catch(Exception exception){exception.printStackTrace();}
	 		}
	 		
	 		return obj;
	 	}
	 	
	 	public boolean upDateGenParam(int value)throws RecordNotUpdatedException
	 	{
	 		Connection conn=null;
	 		Statement stmt=null;
	 		try{
	 			conn=getConnection();	
	 			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
	 			
	 			if( stmt.executeUpdate("update Modules set top_margin = "+ value +" where modulecode  = '1011001'") == 1 )
	 				return true ;
	 			else {
	 				sessionContext.setRollbackOnly();
	 				throw new RecordNotUpdatedException();
	 			}
	 			
	 			
	 		}catch(SQLException exception){exception.printStackTrace();}
	 		finally{
	 			try{		    	
	 				conn.close();
	 			}catch(SQLException exception){exception.printStackTrace();}
	 		}
	 		
	 		return true;
	 	}
	 	
	 	
	 	public int clearingAdminUpdation(Object value[],int type)throws RecordsNotInsertedException,RecordNotInsertedException
	 	{
	 		Connection conn=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	 			ResultSet rs=null;
	 			if(type==1 ||type==3){
	 				
	 				int a  = 0;
	 				System.out.println(" the value is " + value[6].toString());
	 				rs=stmt.executeQuery("select modulecode from Modules where moduleabbr='"+value[6].toString()+"'");
	 				if(rs.next()){
	 					System.out.println("inside the if statement .,.,.,.,.,.,.,.,..,.,.,.,.");
	 					a = stmt.executeUpdate("update BranchMaster set br_code="+Integer.parseInt(value[1].toString())+",br_name='"+value[2].toString()+"',address='"+value[3].toString()+"',br_shnm='"+value[4].toString()+"',br_type='"+value[5].toString()+"',br_ac_type='"+rs.getString(1)+"',br_ac_no="+Integer.parseInt(value[7].toString())+ " where br_code="+Integer.parseInt(value[1].toString()));
	 				}
	 				else{
	 					sessionContext.setRollbackOnly();
	 					throw new RecordsNotInsertedException();
	 				}
	 				
	 				System.out.println("after throwing the Exception .,.,.,.,.,.,.,.,..,.,.,.,.");
	 				return a;
	 			}
	 			else if(type==2){
	 				int a=stmt.executeUpdate("update BankMaster set bank_code="+Integer.parseInt(value[1].toString())+",bank_name='"+value[2].toString()+"' where bank_code="+Integer.parseInt(value[1].toString()));
	 				return a;
	 			}
	 			else if(type==4){
	 				
	 				rs=stmt.executeQuery("select modulecode from Modules where moduleabbr='"+value[2].toString()+"'");
	 				rs.next();
	 				int a=stmt.executeUpdate("update CompanyMaster set comp_name='"+value[1].toString()+"',ac_type='"+rs.getString(1)+"',ac_no="+Integer.parseInt(value[3].toString())+" where comp_name='"+value[1].toString()+"'");
	 				return a;
	 			}
	 			else if(type==5){
	 				int a=stmt.executeUpdate("update ReasonMaster set code="+Integer.parseInt(value[1].toString())+",reason='"+value[2].toString()+"' where code="+Integer.parseInt(value[1].toString()));
	 				return a;
	 			}
	 			else if(type==6){
	 				int a=stmt.executeUpdate("update ReasonLink set link_code="+Integer.parseInt(value[1].toString())+",link_desc='"+value[2].toString()+"',r_code="+Integer.parseInt(value[3].toString())+" where link_code="+Integer.parseInt(value[1].toString()));
	 				return a;
	 			}
	 			else if(type==7){
	 				rs=stmt.executeQuery("select modulecode from Modules where moduleabbr='"+value[2].toString()+"'");
	 				rs.next();
	 				int a=stmt.executeUpdate("update BounceFine set b_code="+Integer.parseInt(value[1].toString())+",acc_type='"+rs.getString(1)+"',fine="+Double.parseDouble(value[3].toString())+",return_fine="+Double.parseDouble(value[4].toString())+",mail_chg="+Double.parseDouble(value[5].toString())+",sms_chg="+Double.parseDouble(value[6].toString())+",email_chg="+Double.parseDouble(value[7].toString())+" where b_code="+Integer.parseInt(value[1].toString()));
	 				return a;
	 			}
	 		}catch(SQLException z){
	 			System.out.println("inside the SQLException");
	 			z.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordsNotInsertedException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return -1;
	 	}
	 	
	 	public int ClearingAdminInsertion(Vector value[],int type)throws RecordNotInsertedException
	 	{
	 		System.out.println("Inside Clear Admin...");
	 		System.out.println("Data  is"+value[0].toString());	
	 		int insert=0;
	 		Connection conn=null;
	 		PreparedStatement pstmt=null;
	 		ResultSet rs=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	 			
	 			//Iterator iterator=value.iterator();
	 			//Vector data=null;
	 						
	 			if(type==1 ||type==3){
	 				
	 				pstmt= conn.prepareStatement("insert into BranchMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,'"+ getToday()+"')");
	 				for(int i=0;i<value.length;i++){
	 					
	 					//rs=stmt.executeQuery("select modulecode from Modules where moduleabbr='"+value[i].elementAt(5)+"'");
	 					//rs.next();
	 								
	 					pstmt.setInt(1,Integer.parseInt(value[i].elementAt(0).toString()));
	 					
	 					pstmt.setString(2,value[i].elementAt(1).toString());
	 					pstmt.setString(3,value[i].elementAt(2).toString());
	 					pstmt.setString(4,value[i].elementAt(3).toString());
	 					pstmt.setString(5,value[i].elementAt(4).toString());
	 					System.out.println("out 1 is"+value[i].elementAt(5).toString());
	 					pstmt.setInt(6,Integer.parseInt(value[i].elementAt(5).toString()));
	 				//	pstmt.setString(7,value[i].elementAt(4).toString());
	 					pstmt.setString(7,"T");
	 					pstmt.setString(8,value[i].elementAt(6).toString());
	 					pstmt.setInt(9,Integer.parseInt(value[i].elementAt(7).toString()));
	 					System.out.println("out 1 is"+value[i].elementAt(7).toString());
	 					
	 					pstmt.setString(10,value[i].elementAt(8).toString());
	 					
	 					
	 					pstmt.setString(11," ");
	 					pstmt.setString(12,value[i].elementAt(9).toString());
	 					pstmt.setString(13,value[i].elementAt(10).toString());
	 			
	 					insert=insert+pstmt.executeUpdate();
	 				}
	 			}

	 			else if(type==2){
	 				
	 				pstmt= conn.prepareStatement("insert into BankMaster values(?,?,?,?,?,'"+ getToday()+ "')");
	 				for(int i=0;i<value.length;i++){
	 					
	 					pstmt.setInt(1,Integer.parseInt(value[i].elementAt(0).toString().trim()));
	 					pstmt.setString(2,value[i].elementAt(1).toString().trim());
	 					pstmt.setString(3,value[i].elementAt(2).toString().trim());
	 					pstmt.setString(4,value[i].elementAt(3).toString().trim());
	 					pstmt.setString(5,value[i].elementAt(4).toString().trim());					
	 					insert=insert+pstmt.executeUpdate();
	 				}
	 			}
	 			
	 			else if(type==4){
	 				
	 				pstmt= conn.prepareStatement("insert into CompanyMaster values(?,?,?,?,?,?,'"+ getToday()+ "',?,?,'"+ getToday()+ "')");
	 				System.out.println(String.valueOf(value[0].elementAt(0)));
	 				for(int i=0;i<value.length;i++){
	 					
	 					rs=stmt.executeQuery("select modulecode from Modules where moduleabbr='"+value[i].elementAt(2)+"'");
	 					rs.next();
	 					System.out.println("ModuleCode is: "+rs.getString(1));
	 					System.out.println("out is"+String.valueOf(value[i].elementAt(1).toString()));
	 					
	 					pstmt.setInt(1,Integer.parseInt(value[i].elementAt(0).toString().trim()));
	 					pstmt.setString(2,value[i].elementAt(1).toString().trim());
	 					pstmt.setString(3,rs.getString(1));
	 					pstmt.setInt(4,Integer.parseInt(String.valueOf(value[i].elementAt(3))));
	 					pstmt.setString(5,value[i].elementAt(4).toString().trim());
	 					pstmt.setString(6,value[i].elementAt(5).toString().trim());
	 					pstmt.setString(7,value[i].elementAt(4).toString().trim());
	 					pstmt.setString(8,value[i].elementAt(5).toString().trim());
	 				
	 										
	 					insert=insert+pstmt.executeUpdate();
	 				}
	 			}
	 			
	 			else if(type==5){
	 				
	 				pstmt= conn.prepareStatement("insert into ReasonMaster values(?,?,?,?,'"+ getToday()+ "')");
	 				for(int i=0;i<value.length;i++)
	 				{
	 					
	 					pstmt.setInt(1,Integer.parseInt(value[i].elementAt(0).toString().trim()));
	 					pstmt.setString(2,value[i].elementAt(1).toString().trim());
	 					pstmt.setString(3,value[i].elementAt(2).toString().trim());
	 					pstmt.setString(4,value[i].elementAt(3).toString().trim());
	 										
	 					insert=insert+pstmt.executeUpdate();
	 				}
	 			}
	 			
	 			else if(type==7){
	 				
	 				pstmt= conn.prepareStatement("insert into BounceFine values(?,?,?,?,?,?,?,?,?,'"+ getToday()+ "')");
	 				for(int i=0;i<value.length;i++){
	 					
	 					rs=stmt.executeQuery("select modulecode from Modules where moduleabbr='"+value[i].elementAt(1)+"'");
	 					
	 					rs.next();
	 					System.out.println("fineeee");
	 					System.out.println("module code"+rs.getString(1));
	 					pstmt.setInt(1,Integer.parseInt(value[i].elementAt(0).toString().trim()));
	 					pstmt.setString(2,rs.getString(1));
	 					pstmt.setDouble(3,Double.parseDouble(value[i].elementAt(2).toString().trim()));
	 					pstmt.setDouble(4,Double.parseDouble(value[i].elementAt(3).toString().trim()));
	 					pstmt.setDouble(5,Double.parseDouble(value[i].elementAt(4).toString().trim()));
	 					pstmt.setDouble(6,Double.parseDouble(value[i].elementAt(5).toString().trim()));
	 					pstmt.setDouble(7,Double.parseDouble(value[i].elementAt(6).toString().trim()));
	 										
	 					System.out.println("734534705034578345fineeee");
	 					pstmt.setString(8,value[i].elementAt(7).toString().trim());
	 					pstmt.setString(9,value[i].elementAt(8).toString().trim());
	 					insert=insert+pstmt.executeUpdate();
	 				}
	 			}
	 		}catch(SQLException o){
	 			o.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		if(insert>0)
	 			return 1;
	 		return 0;
	 	}	
	 	
	 	public int clearingAdminDeletion(Vector value,int type)throws RecordNotInsertedException
	 	{
	 		PreparedStatement pstmt=null;
	 		Connection conn=null;
	 		try
	 		{
	 			conn=getConnection();
	 			if(type==1 ){
	 				
	 				pstmt = conn.prepareStatement("delete from BranchMaster where br_code=?");
	 				for(int i=0;i<value.size();i++){
	 					pstmt.setInt(1,Integer.parseInt(value.elementAt(i).toString().trim()));
	 					pstmt.addBatch();
	 				}	
	 			}
	 			
	 			else if( type==3){
	 				
	 				pstmt = conn.prepareStatement("delete from BranchMaster where br_code=?");
	 				int i =0;
	 				
	 				{
	 					System.out.println(Integer.parseInt(value.elementAt(i).toString().trim()) + " br code to delete ");
	 					pstmt.setInt(1,Integer.parseInt(value.elementAt(i).toString().trim()));
	 					pstmt.addBatch();
	 				}	
	 			}
	 			
	 			else if(type == 2){
	 				
	 				pstmt = conn.prepareStatement("delete from BankMaster where bank_code=?");
	 				for(int i=0;i<value.size();i++){
	 					pstmt.setInt(1,Integer.parseInt(value.elementAt(i).toString().trim()));
	 					pstmt.addBatch();
	 				}
	 			}			
	 			else if(type == 4){
	 				
	 				ResultSet rs=null;
	 				PreparedStatement pstat=null;
	 				pstat = conn.prepareStatement("select modulecode from Modules where moduleabbr=?");
	 				pstmt = conn.prepareStatement("delete from CompanyMaster where comp_name=? and ac_type=? and ac_no=?");
	 				
	 				for(int i=0;i<value.size();i=i+3){
	 					
	 					pstat.setString(1,value.elementAt(i+1).toString());
	 					rs = pstat.executeQuery();
	 					rs.next();
	 					
	 					pstmt.setString(1,value.elementAt(i).toString().trim());
	 					pstmt.setString(2,rs.getString(1));
	 					pstmt.setInt(3,Integer.parseInt(value.elementAt(i+2).toString().trim()));
	 					pstmt.addBatch();
	 				}
	 			}
	 			
	 			else if(type == 5){
	 				
	 				pstmt = conn.prepareStatement("delete from ReasonMaster where code=?");
	 				
	 				int i =0;
	 				
	 				{
	 					System.out.println(Integer.parseInt(value.elementAt(i).toString().trim()) + " br code to delete ");
	 					pstmt.setInt(1,Integer.parseInt(value.elementAt(i).toString().trim()));
	 					pstmt.addBatch();
	 				}	
	 				
	 				/*for(int i=0;i<value.size();i++){
	 					pstmt.setInt(1,Integer.parseInt(value.elementAt(i).toString().trim()));
	 					pstmt.addBatch();
	 				}*/
	 			}
	 			
	 			else if(type == 7){
	 				
	 				Statement st = conn.createStatement();
	 				
	 				ResultSet rss = st.executeQuery(" select modulecode from Modules where moduleabbr = '"+value.elementAt(1) +"'");
	 				
	 				if (rss.next()){
	 					pstmt = conn.prepareStatement("delete from BounceFine where b_code=? and acc_type= ?");

	 					int i =0;

	 					{
	 						System.out.println(Integer.parseInt(value.elementAt(i).toString().trim()) + " br code to delete ");
	 						pstmt.setInt(1,Integer.parseInt(value.elementAt(i).toString().trim()));
	 						pstmt.setString(2, rss.getString("modulecode"));
	 						pstmt.addBatch();
	 					}
	 				}
	 				/*for(int i=0;i<value.size();i++){
	 					pstmt.setInt(1,Integer.parseInt(value.elementAt(i).toString().trim()));
	 					pstmt.addBatch();
	 				}*/
	 			}
	 			else if(type == 8){
	 				
	 				Statement st = conn.createStatement();
	 				
	 				ResultSet rss = st.executeQuery(" select modulecode from Modules where moduleabbr = '"+value.elementAt(4) +"'");
	 				
	 				if (rss.next()){
	 					pstmt = conn.prepareStatement("delete from DiscountCharges where fr_amt = ? and  to_amt = ? and  ac_type= ?");

	 					int i =0;

	 					{
	 						System.out.println(Double.parseDouble(value.elementAt(i).toString().trim()) + " br code to delete ");
	 						pstmt.setDouble(1,Double.parseDouble(value.elementAt(0).toString().trim()));
	 						pstmt.setDouble(2,Double.parseDouble(value.elementAt(1).toString().trim()));
	 						pstmt.setString(3, rss.getString("modulecode"));
	 						pstmt.addBatch();
	 					}
	 				}
	 				/*for(int i=0;i<value.size();i++){
	 					pstmt.setInt(1,Integer.parseInt(value.elementAt(i).toString().trim()));
	 					pstmt.addBatch();
	 				}*/
	 			}
	 			int a=pstmt.executeBatch().length;
	 			if(a>0)
	 				return 1;
	 			return 0;
	 		}catch(SQLException b){
	 			b.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 			throw new RecordNotInsertedException();
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			sessionContext.setRollbackOnly();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return -1;
	 	}
	 	public void checkReconcilation(String ctrl)throws RecordsNotFoundException
		{
	 		
	 		PreparedStatement pstmt=null;
	 		Connection conn=null;
	 		ResultSet rs=null;
	 		String ackno;
	 		try{
	 			conn=getConnection();
	 			Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			Statement stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			rs=stmt1.executeQuery("select ack_no,ctrl_no from Cheque where ctrl_no="+ctrl);
	 			if(rs!=null && rs.next())
	 			{
	 				ackno=rs.getString(1);
	 			}
	 			else 
	 				throw new RecordsNotFoundException();
	 			int r=stmt2.executeUpdate("update Acknowledge set reconciled='F', ve_tml=NULL, ve_user=NULL, ve_date=NULL where ack_no="+ackno);
	 			if(r==0)
	 				throw new RecordsNotFoundException();
	 			
	 		}
	 		 catch(SQLException ee){
		    	ee.printStackTrace();
		    	sessionContext.setRollbackOnly();
			}catch(Exception e){
				e.printStackTrace();
				sessionContext.setRollbackOnly();
			}
	 		 finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 }
	 	
	 public ClearingObject[] getBounceCheque(ClearingObject clear)
	 {
	 		Statement pstmt=null;
	 		Statement stmt=null;
			Connection conn=null;
			ResultSet rs=null;
			ResultSet sub_rs=null;
			ClearingObject[] clearObj=null;
			int i=0;
			try{
				System.out.println("1,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
				 conn=getConnection();		
				 pstmt=conn.createStatement();
				 stmt=conn.createStatement();
				 rs=pstmt.executeQuery("select * from Cheque where to_bounce='"+clear.getToBounce()+"' and clg_date <='"+clear.getClgDate()+"' and doc_dest ="+clear.getDocSource()+" and clg_type='"+clear.getClgType()+"' and clg_no="+clear.getClgNo());
				 if(rs.next())
				 {
					 System.out.println("2,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
				 	rs.last();
				 	clearObj= new ClearingObject[rs.getRow()];
				 	rs.beforeFirst();
				 	System.out.println("3,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
				 	while(rs.next()){
				 		clearObj[i]=new ClearingObject();
				 		clearObj[i].setClgDate(rs.getString("clg_date"));
				 		clearObj[i].setControlNo(rs.getInt("ctrl_no"));
				 		clearObj[i].setSendTo(rs.getInt("send_to"));
				 		clearObj[i].setDocSource(rs.getInt("doc_sou"));
				 		clearObj[i].setDocDestination(rs.getInt("doc_dest"));
				 		clearObj[i].setAckNo(rs.getInt("ack_no"));
				 		clearObj[i].setNoOfDocs(rs.getInt("no_docs"));
				 		clearObj[i].setTranAmt(rs.getDouble("trn_amt"));
				 		clearObj[i].setMultiCredit(rs.getString("mult_cr"));
						clearObj[i].setCompanyName(rs.getString("comp_name"));
						clearObj[i].setChqDDPO(rs.getString("chqddpo"));
						clearObj[i].setQdpNo(rs.getInt("qdp_no"));
						clearObj[i].setQdpDate(rs.getString("qdp_dt"));
						clearObj[i].setRetNormally(rs.getString("ret_norm"));
						clearObj[i].setPrevCtrlNo(rs.getInt("prev_ctrl_no"));
						clearObj[i].setBankCode(Integer.parseInt(rs.getString("bank_cd").toString().substring(4, 6)));
						clearObj[i].setBankName(rs.getString("br_name"));
						clearObj[i].setCreditACNo(rs.getInt("cr_ac_no"));
						clearObj[i].setDebitACType(rs.getString("dr_ac_type"));
						clearObj[i].setDebitACNo(rs.getInt("dr_ac_no"));
						if(rs.getString("cr_ac_type").toString().startsWith("1002") ||rs.getString("cr_ac_type").toString().startsWith("1007")  )
						 sub_rs = stmt.executeQuery("select fname,moduleabbr, address  from AccountMaster as AM, CustomerAddr as CA ,Modules as MD, CustomerMaster as CM where AM.ac_type = '"+ rs.getString("cr_ac_type")+"' and AM.ac_no ='"+ rs.getInt("cr_ac_no")+"' and AM.cid = CA.cid and AM.addr_type = CA.addr_type and MD.modulecode = AM.ac_type and AM.cid = CM.cid");
						 if(sub_rs.next()){
							 clearObj[i].setClgType(sub_rs.getString("fname"));
							 clearObj[i].setDiscInd(sub_rs.getString("address"));
							 clearObj[i].setCreditACType(sub_rs.getString("moduleabbr"));
						 }else{
							 clearObj[i].setClgType("-----");
							 clearObj[i].setDiscInd("-----");
							 clearObj[i].setCreditACType("-----");
						 }
						i++;
				 }
				}
				 else
				 	return null;
			}
			 catch(Exception ee){
	    	ee.printStackTrace();
		}finally{
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		return clearObj;
	 }
	 	public  ChequeDepositionObject getLateReturnDetail(long ctrl)throws  RecordsNotFoundException
	 	{
	 		
	 		Connection conn=null;
	 		ChequeDepositionObject cd=null;
	 		ResultSet rs1=null;
	 		try
	 		{
	 			conn=getConnection();
	 			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			//ResultSet rs=stmt.executeQuery("select * from Cheque where to_bounce='T' and let_sent='F'");
	 			ResultSet rs=stmt.executeQuery("select * from Cheque where ctrl_no='"+ctrl+"' and desp_ind ='T' and post_ind='F'");
	 			//if(rs.next())
	 			//{
	 				
	 				System.out.println("after resultset");
	 				rs.last();
	 				if(rs.getRow()==0)
	 				{
	 					cd=null;
	 					System.out.println("after null");
	 				}
	 				else{
	 				
	 				cd=new ChequeDepositionObject();
	 				rs.beforeFirst();
	 			
	 				String to_bounce=null;
	 				while(rs.next())
	 				{
	 					
						cd.setControlNo(rs.getInt("ctrl_no"));
	 					cd.setDebitACType(rs.getString("dr_ac_type"));
	 					cd.setDebitACNo(rs.getInt("dr_ac_no"));
	 					System.out.println("debit acc no is "+cd.getDebitACNo());
	 					cd.setDocDestination(rs.getInt("doc_dest"));
	 					cd.setBankCode(rs.getString("bank_cd"));
	 					cd.setBranchName(rs.getString("br_name"));
	 					cd.setTranAmt(rs.getDouble("trn_amt"));
	 					to_bounce=rs.getString("to_bounce");
	 					
	 					if( to_bounce !=null && to_bounce.equalsIgnoreCase("T"))
	 					{
	 						cd.setBounceInd(to_bounce);
	 						System.out.println(11);
	 						Vector rea_arr = new Vector(0,1);
	 						Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 						rs1 = stat.executeQuery("select * from Reason where ctrl_no="+ctrl);
	 						while(rs1.next()){
	 							rea_arr.add(new Integer(rs1.getInt(2)));
	 						}
	 						cd.setReasonArray(rea_arr);
	 					}else 
	 						cd.setBounceInd("F");
	 					cd.setMultiCredit(rs.getString("mult_cr"));
	 					cd.setCompanyName(rs.getString("comp_name"));
	 					cd.setCreditACType(rs.getString("cr_ac_type"));
	 					cd.setCreditACNo(rs.getInt("cr_ac_no"));
	 					cd.setChqDDPO(rs.getString("chqddpo"));
	 					cd.setQdpNo(rs.getInt("qdp_no"));
	 					cd.setQdpDate(rs.getString("qdp_dt"));
	 					cd.setPOCommission(rs.getDouble("po_comm"));
	 					cd.setDiscInd(rs.getString("disc_ind"));
	 					cd.setDespInd(rs.getString("desp_ind"));
	 					cd.setDiscAmt(rs.getDouble("disc_amt"));
	 					cd.setVeTml(rs.getString("ve_tml"));
	 					//cd.setDeTime(rs.getString("bm.bank_name"));
	 					cd.setDeUser(rs.getString("ret_norm"));
	 					cd.setLoanAcc(rs.getString("loan_ac_type"));
	 					cd.setLoanAcc_No(rs.getInt("loan_ac_no"));
	 					cd.setPayeeName(rs.getString("payee_name"));
	 					cd.setClgType(rs.getString("clg_type"));
	 					cd.setClgDate(rs.getString("clg_date"));
	 					
	 				}
	 			}	
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 		return cd;
	 		
	 	}
	 	
	 	public Hashtable getColbalanePostInd(String acc_type,int acc_no,int prev_ctrl,String date)throws RecordsNotFoundException
	 	{
	 		//Vector str=new Vector();
	 		
	 		Hashtable str = new Hashtable();
	 		
	 		Connection conn=null;
	 		ResultSet rs1=null;
	 		try
	 		{
	 			comm=commonHome.create();
	 			conn=getConnection();
	 			Statement stmt=conn.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE );
	 			
	 			AccountObject acc_obj = comm.getAccount(null,acc_type,acc_no,date);
	 			
	 			//str.add(new Double(acc_obj.getAmount()));
	 			
	 			
	 			rs1=stmt.executeQuery("select post_ind from Cheque where ctrl_no="+prev_ctrl);
	 			
	 			if( rs1.next() )
	 				str.put ( new Double(acc_obj.getAmount()) , rs1.getString("post_ind"));
	 			else 
	 				throw new RecordsNotFoundException();
	 			
	 			
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		catch(CreateException cre){
	 			cre.printStackTrace();
	 		}
	 		finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 		}
	 	
	 		return str;
	 	}
	 
	 	public int outwardReturnBounce(long ctrl_no,int type,String ve_tml,String ve_user,String ve_date)throws RecordsNotFoundException
	 	{
	 		int result=0;
	 		Connection conn=null;
	 		ResultSet rs1=null;
	 		ResultSet rs2=null;
	 		ResultSet rs3=null;
	 		int trn_seq =0;
	 		int custtype=0,cat_type=0;
	 		long prev_ctrl_no = 0;
	 		try{
	 			conn=getConnection();
	 			Statement smt=conn.createStatement();
	 			Statement smt1=conn.createStatement();
	 			Statement stmt2=conn.createStatement();
	 			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 			
	 			rs1= smt.executeQuery("select ch.* from Cheque ch where ctrl_no="+ctrl_no+" and ve_tml is null ");
	 				
	 			if( ! rs1.next()){
	 				throw new RecordsNotFoundException();
	 			}
	 			
	 			prev_ctrl_no = rs1.getInt("ch.prev_ctrl_no");
	 			AccountObject acc_obj=comm.getAccount(null,rs1.getString("dr_ac_type"),rs1.getInt("dr_ac_no"),ve_date);
	 			
	 			if(type==1){
	 				
	 				AccountTransObject am = new AccountTransObject();
	 	 			am.setAccType(rs1.getString("dr_ac_type"));
	 	 			am.setAccNo(rs1.getInt("dr_ac_no"));//DebitACNo());
	 	 			am.setTransType("P");
	 	 			am.setTransAmount(rs1.getDouble("trn_amt"));//TranAmt());
	 	 			am.setTransMode("G");
	 	 			am.setTransSource(ve_tml);
	 	 			am.setCdInd("D");
	 	 			am.setTransDate(getToday());
	 	 			am.setChqDDNo(rs1.getInt("qdp_no"));//cd.getQdpNo());
	 	 			am.setChqDDDate(rs1.getString("qdp_dt"));//(cd.getQdpDate());
	 	 			am.setTransNarr("Clg Ctrl No "+rs1.getInt("ctrl_no"));
	 	 			am.setRef_No(rs1.getInt("ack_no"));//(cd.getAckNo());
	 	 			am.setPayeeName(rs1.getString("payee_name"));//(cd.getPayeeName());
	 	 			am.setCloseBal(acc_obj.getAmount());//set -value
	 	 			am.setLedgerPage(0);
	 	 			am.uv.setUserTml(ve_tml);//ve_date
	 	 			am.uv.setUserId(ve_user);
	 	 			am.uv.setUserDate(ve_date);
	 	 			am.uv.setVerTml(ve_tml);
	 	 			am.uv.setVerId(ve_user);
	 	 			am.uv.setVerDate(ve_date);
	 	 			// call common impl method
	 	 			comm.storeAccountTransaction(am);
	 	 			// Gl code goes here .....
	 	 			

	 					if(rs1.getString("dr_ac_type").startsWith("1002") || rs1.getString("dr_ac_type").startsWith("1007"))
	 						rs2 = stmt2.executeQuery("select att.ac_type,am.last_tr_seq,cid from AccountMaster am,AccountTransaction att where am.ac_no="+am.getAccNo()+" and am.ac_type='"+am.getAccType()+"' and am.ac_no=att.ac_no and am.ac_type=att.ac_type order by trn_seq desc limit 1");
	 					else if(rs1.getString("dr_ac_type").startsWith("1014") || rs1.getString("dr_ac_type").startsWith("1015"))
	 						rs2 = stmt2.executeQuery("select odt.ac_type,od.last_tr_seq,cid from ODCCMaster od,ODCCTransaction odt where od.ac_no="+am.getAccNo()+" and od.ac_type='"+am.getAccType()+"' and od.ac_no=odt.ac_no and od.ac_type=odt.ac_type order by trn_seq desc limit 1");
	 					
	 				if(	rs2.next())
	 				{
	 					 
	 					if(rs1.getString("dr_ac_type").startsWith("1002")||rs1.getString("dr_ac_type").startsWith("1007")||rs1.getString("dr_ac_type").startsWith("1014")||rs1.getString("dr_ac_type").startsWith("1015"))
	 					{
	 					trn_seq=rs2.getInt(2);
	 					
	 				int cid	=rs2.getInt("cid");
	 				System.out.println("cid is"+ cid);
	 					 rs3= smt1.executeQuery("select custtype from CustomerMaster where cid="+ cid );
	 					if(rs3.next())
	 					{
	 					System.out.println("custtype is"+ rs3.getInt("custtype"));	
	 				 custtype=rs3.getInt("custtype");
	 					if(custtype==0)
	 						cat_type=1;
	 					else
	 						cat_type=2;
	 					}
	 			
	 					rs3 = smt1.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+cat_type+" and gk.ac_type = "+am.getAccType()+" and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
	 	 			if(rs3.next())
	 	 			{
	 	 				GLTransObject trnobj=new GLTransObject();
	 	 				trnobj.setTrnDate(getToday());
	 	 				trnobj.setGLType(rs3.getString(3));
	 	 				trnobj.setGLCode(rs3.getString(1));
	 	 				trnobj.setTrnMode("G");
	 	 				trnobj.setAmount(rs1.getDouble("trn_amt")*rs3.getInt(2));
	 	 				trnobj.setCdind("D");
	 	 				trnobj.setAccType(am.getAccType());
	 	 				trnobj.setAccNo(String.valueOf(am.getAccNo()));
	 	 				trnobj.setTrnType("P");
	 	 				trnobj.setRefNo(trn_seq);
	 	 				trnobj.setTrnSeq(trn_seq);
	 	 				trnobj.setVtml(ve_tml);//ve_tmlve_userve_date
	 	 				trnobj.setVid(ve_user);System.out.println(34);
	 	 				trnobj.setVDate(ve_date);
	 	 				
	 	 				comm.storeGLTransaction(trnobj);System.out.println(35);
	 	 			}
	 			}
	 		}
	 				// GL code goes here for inward apex bank 213004 
	 				
	 				rs3 = smt1.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =1 and gk.ac_type = '1011001'");
	 	 			if(rs3.next())
	 	 			{
	 	 				GLTransObject trnobj=new GLTransObject();
	 	 				trnobj.setTrnDate(getToday());
	 	 				trnobj.setGLType(rs3.getString("gl_type"));
	 	 				trnobj.setGLCode(rs3.getString("gl_code"));
	 	 				trnobj.setTrnMode("G");
	 	 				trnobj.setAmount(rs1.getDouble("trn_amt"));
	 	 				trnobj.setCdind("C");
	 	 				trnobj.setAccType(am.getAccType());
	 	 				trnobj.setAccNo(String.valueOf(am.getAccNo()));
	 	 				trnobj.setTrnType("R");
	 	 				trnobj.setRefNo(trn_seq);
	 	 				trnobj.setTrnSeq(trn_seq);
	 	 				trnobj.setVtml(ve_tml);//ve_tmlve_userve_date
	 	 				trnobj.setVid(ve_user);System.out.println(34);
	 	 				trnobj.setVDate(ve_date);
	 	 				
	 	 				comm.storeGLTransaction(trnobj);System.out.println(35);
	 	 			}
	 	 			result = 1;	
	 			}
	 			
	 			if(type==2){
	 				
	 				rs3 = smt1.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =1 and gk.ac_type = '1011001'");
	 	 			if(rs3.next())
	 	 			{
	 	 				GLTransObject trnobj=new GLTransObject();
	 	 				trnobj.setTrnDate(getToday());
	 	 				trnobj.setGLType(rs3.getString("gl_type"));
	 	 				trnobj.setGLCode(rs3.getString("gl_code"));
	 	 				trnobj.setTrnMode("G");
	 	 				trnobj.setAmount(rs1.getDouble("trn_amt"));
	 	 				trnobj.setCdind("C");
	 	 				trnobj.setAccType(rs1.getString("dr_ac_type"));//rs1.getString("dr_ac_type")rs1.getInt("dr_ac_no")
	 	 				trnobj.setAccNo(""+rs1.getInt("dr_ac_no"));
	 	 				trnobj.setTrnType("R");
	 	 				trnobj.setRefNo(rs1.getInt("ctrl_no"));
	 	 				trnobj.setTrnSeq(trn_seq);
	 	 				trnobj.setVtml(ve_tml);//ve_tmlve_userve_date
	 	 				trnobj.setVid(ve_user);System.out.println(34);
	 	 				trnobj.setVDate(ve_date);
	 	 				
	 	 				comm.storeGLTransaction(trnobj);System.out.println(35);
	 	 			}
	 	 			rs3 = smt1.executeQuery("select gk.gl_code,gk.gl_type from GLKeyParam gk where code =12 and gk.ac_type = '1011001'");
	 	 			if(rs3.next())
	 	 			{
	 	 				GLTransObject trnobj=new GLTransObject();
	 	 				trnobj.setTrnDate(getToday());
	 	 				trnobj.setGLType(rs3.getString("gl_type"));
	 	 				trnobj.setGLCode(rs3.getString("gl_code"));
	 	 				trnobj.setTrnMode("G");
	 	 				trnobj.setAmount(rs1.getDouble("trn_amt"));
	 	 				trnobj.setCdind("D");
	 	 				trnobj.setAccType(rs1.getString("dr_ac_type"));//rs1.getString("dr_ac_type")rs1.getInt("dr_ac_no")
	 	 				trnobj.setAccNo(""+rs1.getInt("dr_ac_no"));
	 	 				trnobj.setTrnType("P");
	 	 				trnobj.setRefNo(rs1.getInt("ctrl_no"));
	 	 				trnobj.setTrnSeq(trn_seq);
	 	 				trnobj.setVtml(ve_tml);//ve_tmlve_userve_date
	 	 				trnobj.setVid(ve_user);System.out.println(34);
	 	 				trnobj.setVDate(ve_date);
	 	 				
	 	 				comm.storeGLTransaction(trnobj);System.out.println(35);
	 	 			}
	 	 			result = 1;
	 			}
	 			//String ve_tml,String ve_user,String ve_date
	 			stat.addBatch("update Cheque set post_ind='T' where ctrl_no in("+ctrl_no+","+prev_ctrl_no+" )");//I/w ctrl no
	 			
	 			stat.addBatch("update Reason set ve_tml='"+ve_tml+"',ve_user='"+ve_user+"',ve_dt_time = '"+ve_date+"' where ctrl_no="+ctrl_no+" and ve_tml is null");
	 			stat.addBatch("update Cheque set ve_tml='"+ve_tml+"',ve_user='"+ve_user+"',ve_dt_time = '"+ve_date+"',clg_date='"+ve_date+"' where ctrl_no="+ctrl_no+" and ve_tml is null");
	 			
	 			int[] updat =stat.executeBatch();
	 			
	 			}catch(RecordsNotFoundException rec) {
	 				sessionContext.setRollbackOnly();
	 				throw rec;
	 				
	 			
	 		} catch(SQLException sql){
	 			
	 			sessionContext.setRollbackOnly();
	 			
	 		}finally {
	 			try {
	 				conn.close();
	 			} catch (Exception exe) {
	 				exe.printStackTrace();
	 			}
	 		}
	 		
	 		
	 		return result;
	 	}
	 	public Hashtable getCityBankBranchDetail(int bcode, String city_cd,String bank_cd, String branch_cd) throws RecordsNotFoundException
	 	{
	 		Hashtable hash=null;
	 		Connection conn=null;
	 		Statement stmt=null;
	 		ResultSet rs=null;
	 		
	 		try{
	 			conn=getConnection();
	 			stmt=conn.createStatement();
	 			
	 				
	 			if(bcode == 2) 
	 				rs=stmt.executeQuery("select * from CityMaster");
	 			else if ( bcode == 3 || bcode == 7)
	 				rs=stmt.executeQuery("select * from BankMaster ");
	 			else if (bcode ==4)
	 			{ 
	 				rs=stmt.executeQuery("select concat(city_code,bank_code,branch_code) as branch_code,branch_name from BranchCodes");
	 			}
	 			else if ( bcode == 5)
	 				rs=stmt.executeQuery("select * from BranchMaster ");
	 			else if ( bcode == 6)
	 				rs=stmt.executeQuery("select * from Modules ");
	 			System.out.println("3.....................");
	 			rs.last();
	 			if(rs.getRow()==0)
	 			{
	 				hash=null;
	 				return hash;
	 			}
	 			else 
	 			{
	 					System.out.println("1.....................");
	 					hash=new Hashtable();
	 			}
	 			rs.beforeFirst();
	 			while(rs.next())
	 			{
	 				if(bcode ==2 )
	 					hash.put(rs.getString("city_code"), rs.getString("city_name"));
	 				else if (bcode == 3)
	 					hash.put(rs.getString("bank_code"), rs.getString("bank_name"));
	 				else if(bcode ==4)
	 				{
	 					hash.put(rs.getString("branch_code"), rs.getString("branch_name"));
	 				}
	 				else if(bcode == 5)
	 				{
	 					hash.put(rs.getString("br_code"), rs.getString("br_name"));
	 				}
	 				else if(bcode == 6)
	 				{
	 					hash.put(rs.getString("modulecode"), rs.getString("moduleabbr"));
	 				}
	 				else if(bcode == 7)
	 					hash.put(rs.getString("bank_code"), rs.getString("bank_name")+rs.getString("bank_abbr"));
	 				
	 			}
	 			
	 		}catch(Exception e){
	 			throw new RecordsNotFoundException();
	 		}finally{
	 			try {
	 				conn.close();
	 			} catch (SQLException e1) {
	 				e1.printStackTrace();
	 			}
	 		}
	 		
	 		return hash;
	 	}
	 
	 	public String[] getDistinctTablesValues(int table_name)throws RecordsNotFoundException{
	 	
	 		String[] distinct_val = null;
	 		Connection conn = null;
	 		Statement stmt = null;
	 		ResultSet rs = null;
	 		try{
	 			
	 			conn = getConnection();
	 			stmt = conn.createStatement();
	 			
	 			{
	 				 if(table_name == 1)
	 					 rs = stmt.executeQuery("select distinct send_to from Cheque where send_to is not null");
	 				 else if(table_name == 3)
	 					rs = stmt.executeQuery("select distinct doc_dest from Cheque where doc_dest is not null");
	 					 
	 				if(rs.next()){
	 					rs.last();
	 					distinct_val = new String[rs.getRow()];
	 					rs.beforeFirst();
	 				}
	 				int i = 0;
	 				while(rs.next()){
	 					
	 					if(table_name == 1)
	 						distinct_val[i] = rs.getString("send_to");
	 					else if(table_name == 3)
	 						distinct_val[i] = rs.getString("doc_dest");
	 					i++;
	 				}
	 				
	 			}
	 			
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 		}finally{
	 			
	 			try{
	 				conn.close();
	 			}catch(SQLException sql){
	 				sql.printStackTrace();
	 			}
	 		}
	 		
	 		return distinct_val;
	 	}
	 	
	 	public int deleteAck(int ackno)throws RecordsNotFoundException
	 	{
	 		
			
	 		Connection conn = null;
	 		Statement stmt = null;
	 		ResultSet rs = null;
	 		ResultSet rs1 = null;
	 		int i = -1;
	 		try{
	 			
	 			conn = getConnection();
	 			stmt = conn.createStatement();
	 			
	 			rs = stmt.executeQuery("select * from Acknowledge where ack_no = "+ ackno +" and de_tml is not null");
	 			if(rs.next())
	 			{	
	 				rs1 = stmt.executeQuery("select * from Cheque ch,Acknowledge ac where ch.ack_no = "+ ackno +"  and ac.ack_no = ch.ack_no and ac.ack_no is null");
	 				if(rs1.next()){
	 					
	 					return i;
	 					
	 				}else{
	 					
	 					i = stmt.executeUpdate("delete  from Acknowledge  where ack_no =" + ackno);
	 					
	 				}
	 				
	 				
	 			}else{
	 				
	 				i=0;
	 				return i;
	 			}
	 		
	 			
	 		}catch(SQLException sql){
	 			sql.printStackTrace();
	 		}finally{
	 			
	 			try{
	 				conn.close();
	 			}catch(SQLException sql){
	 				sql.printStackTrace();
	 			}
	 		}
	 		
	 		return i;
	 	}
	 	
	 	public ChequeDepositionObject[] getHelpData(int i)throws RecordsNotFoundException
	 	{
	 		ChequeDepositionObject[] chq = null;
	 		Connection conn = null;
	 		Statement stmt = null;
	 		ResultSet rs = null;
	 		
	 		try{
	 			
	 			conn = getConnection();
	 			
	 			stmt = conn.createStatement();
	 			
	 			if (i == 1) {     // Outward Cheque and InterClearing which r not verifeid 
	 				
	 				rs = stmt.executeQuery("select * from Cheque where clg_type in('O' , 'T') and doc_bs = 'S' and clg_date is null and ve_tml is null order by ctrl_no desc ");
	 				
	 			} else if (i == 2){   // Outward Multi Credit Cheque (Combined Cheque) which r not verifeid 
	 				
	 				rs = stmt.executeQuery("select * from Cheque where mult_cr = 'T' and clg_type = 'O' and doc_bs = 'S' and clg_date is null and ve_tml is null order by ctrl_no desc ");
	 			
	 			} else if (i == 3){    // Inward Cheque not yet verified  
	 				
	 				rs = stmt.executeQuery(" select ch.*,mo.moduleabbr  from Cheque ch, Modules mo where clg_type = 'I'  and doc_bs = 'S' and mo.modulecode = dr_ac_type and ve_tml is null order by ctrl_no asc ");
	 			
	 			} else if (i == 4){
	 				
	 				rs = stmt.executeQuery(" select * from Cheque where doc_bs = 'B' and clg_date is null and ve_tml is null order by ctrl_no desc ");
	 				
	 			} else if (i == 5){
	 				
	 				rs = stmt.executeQuery(" select ch.* from Cheque ch ,Acknowledge ack where ch.doc_bs = 'B' and ch.clg_date is null and ch.ve_tml is null and ch.ack_no = ack.ack_no and ack.reconciled = 'T' and  ack.ve_tml is not null ");
	 				
	 			} else if ( i == 6 ){
	 				
	 				rs = stmt.executeQuery(" select * from Cheque where  ( clg_type = 'C' ||  clg_type = 'D') and doc_bs = 'S'  and ve_tml is null order by ctrl_no asc ");
	 				
	 			} else if ( i == 7 ){
	 				
	 				rs = stmt.executeQuery(" select * from Cheque ch ,Acknowledge ak where  ( ch.clg_type = 'C' ||  ch.clg_type = 'D') and ch.doc_bs = 'S'  and ch.ve_tml is  null and ch.ack_no = ak.ack_no and ak.reconciled = 'T' and ak.ve_tml is not null order by ctrl_no asc ");
	 				
	 			}
	 			else if (i == 8) {     // Outstation Cheque which r not verifeid 
	 				
	 				rs = stmt.executeQuery(" select * from Cheque where clg_type in('S') and doc_bs = 'S' and clg_date is null and ve_tml is not null order by ctrl_no desc ");
	 				
	 			} 
	 			
	 			
	 			if(rs.next()){
	 				rs.last();
	 				chq = new ChequeDepositionObject[rs.getRow()];
	 				
	 				rs.beforeFirst();
	 				int j = 0;
	 				while(rs.next()){
	 					chq[j] = new ChequeDepositionObject();
	 					chq[j].setAckNo(rs.getInt("ack_no"));
	 					chq[j].setControlNo(rs.getLong("ctrl_no"));
	 					chq[j].setTranAmt(rs.getDouble("trn_amt"));
	 					if(i == 2){
	 					chq[j].setMultiCredit(rs.getString("mult_cr"));
	 					if(rs.getString("mult_cr").equalsIgnoreCase("T") && rs.getString("comp_name") != null)
	 						chq[j].setCompanyName(rs.getString("comp_name"));
	 					
	 					}
	 					chq[j].setQdpNo(rs.getInt("qdp_no"));
	 					chq[j].setDiscAmt(rs.getDouble("doc_tot"));
	 					chq[j].setDebitACNo(rs.getInt("dr_ac_no")); 
	 					chq[j].setDebitACType(rs.getString("dr_ac_type"));   
	 					chq[j].setDocSource(rs.getInt("doc_sou"));
	 					chq[j].setDocDestination(rs.getInt("doc_dest"));
	 					chq[j].setCreditACNo(rs.getInt("cr_ac_no"));
	 					chq[j].setCreditACType(rs.getString("cr_ac_type"));
	 					chq[j].setVe_date(rs.getString("de_dt_time"));
	 					
	 					if (i == 3 ){
	 						
	 						chq[j].setDebitACType(rs.getString("moduleabbr"));  
	 					}
	 					
	 					if ( i == 6 || i ==7 ){
	 						
	 						chq[j].setClgType(rs.getString("clg_type"));
	 						
	 						if ( rs.getString("clg_type").equalsIgnoreCase("C")){
	 						
	 							chq[j].setDebitACNo(rs.getInt("cr_ac_type")); //  
	 							chq[j].setDebitACType(rs.getString("cr_ac_no"));   
	 						
	 						} else if ( rs.getString("clg_type").equalsIgnoreCase("D")){
	 						
	 							chq[j].setDebitACNo(rs.getInt("dr_ac_no")); 
	 							chq[j].setDebitACType(rs.getString("dr_ac_type"));   
	 						
	 						} 
	 					}	
	 					
	 				j++;	
	 				}
	 				
	 				
	 				
	 				
	 			} else
	 				 throw new RecordsNotFoundException();
	 			
	 			
	 		}catch(SQLException exc){
	 			throw new RecordsNotFoundException();
	 			
	 		}finally{
	 			
	 			try{
	 				conn.close();
	 			}catch(SQLException sq){
	 				sq.printStackTrace();
	 			}
	 		}
	 		
	 		return chq;
	 		
	 	}
	 	
	 	
	 	public AckObject[] getAckDetail(int type , String code ) throws RecordsNotFoundException{
	 		
	 		AckObject ack[] = null;
	 		
	 		Connection conn = null;
	 		Statement stmt = null;
	 		ResultSet rs = null;
	 		
	 		try {
	 			
	 			conn = getConnection();
	 			
	 			stmt = conn.createStatement();
	 			
	 			
	 			
	 			rs = stmt.executeQuery(" select ack.*,bm.br_name from Acknowledge ack,BranchMaster bm where reconciled='F' and clg_type = '"+ code+"' and bm.br_code=ack.doc_sou and ve_user is null order by ack_no ");
	 				
	 	
	 			if(rs.next()){
	 				rs.last();
	 				ack = new AckObject[rs.getRow()];
	 				
	 				rs.beforeFirst();
	 				int j = 0;
	 				while(rs.next()){
	 					
	 					ack[j] = new AckObject();
	 					
	 					ack[j].setAckNo(rs.getInt("ack_no"));
	 					ack[j].setClg_type(rs.getString("clg_type"));
	 					ack[j].setDocSource(rs.getInt("doc_sou"));
	 					ack[j].setDocTotal(rs.getDouble("tot_amt"));
	 					
	 					j++;
	 					
	 				}
	 			} else 
	 				throw new RecordsNotFoundException();
	 			
	 		} catch (SQLException sql ){
	 			
	 			throw new RecordsNotFoundException();
	 		} finally{
	 			
	 			try{
	 				conn.close();
	 			} catch (SQLException e) {
	 				
	 				e.printStackTrace();
	 			}
	 		}
	 		
	 		return ack;
	 	}
	 	
	 	public int createPayOrder( ChequeDepositionObject co) {
	 		
	 	    int po_sr_no = 0;
	        Connection conn=null;
	        ResultSet rs_po = null;
	        System.out.println("inside PO...scroll no = "+co.getControlNo());
	        
	        System.out.println("1..........................................");
	        
	        try
			{
	        	comm = commonHome.create();
	        	conn=getConnection();
	            Statement stmt=conn.createStatement();
	            Statement stmt1=conn.createStatement();
	            Statement stmt2=conn.createStatement();
	            Statement stmt3=conn.createStatement();
	            Statement stmt4=conn.createStatement();
	            Statement stmt5=conn.createStatement();
	            Statement stmt6=conn.createStatement();
	            
	            rs_po = stmt.executeQuery("select * from Cheque where ctrl_no ="+co.getControlNo());
	            
	            if(rs_po.next())
	            {
	        
	            	System.out.println("2..........................................");
	            	//ship....01/02/2007
	                /*if(stmt.executeUpdate("update DayCash  set attached='T',ve_user='"+co.uv.getVerId()+"',ve_tml='"+co.uv.getVerTml()+"',ve_date='"+co.uv.getVerDate()+"' where scroll_no="+co.getScrollno()+" and trn_date='"+co.getTrndate()+"'")==0)
	                	throw new SQLException();*/
	                
	                PayOrderObject po=new PayOrderObject();
	                po.setPOType("X");
	                po.setCustType(Integer.parseInt(rs_po.getString("cr_ac_no")));
	                po.setPOPayee(rs_po.getString("payee_name"));  /// rs_po.getString("payee_name")
	                po.setPOAccType(rs_po.getString("dr_ac_type"));
	                po.setPOAccNo(Integer.parseInt(rs_po.getString("dr_ac_no")));
	                po.setPOFavour(rs_po.getString("payee_name"));
	                po.setPODate(rs_po.getString("de_dt_time"));
	                
	                po.setPOAmount(rs_po.getDouble("trn_amt"));
	                
	                po.setCommissionAmount(rs_po.getDouble("po_comm"));
	                
	                po.uv.setUserTml(rs_po.getString("de_tml"));
	                po.uv.setUserId(rs_po.getString("de_user"));
	                po.uv.setUserDate(co.getDe_date());
	                po.uv.setVerTml(co.getVeTml());
	                po.uv.setVerId(co.getVeUser());
	                po.uv.setVerDate(co.getVe_date());
	                
	                ResultSet rs_PO = null;
	                rs_PO=stmt1.executeQuery("select * from GLKeyParam where ac_type='" + rs_po.getString("dr_ac_type") + "' and code=1");
	                
	                if(rs_PO.next())
	                {
	                    po.setPOGlName(rs_PO.getString("key_desc"));
	                    po.setPOGlType(rs_PO.getString("gl_type"));
	                    po.setPOGlCode(rs_PO.getInt("gl_code"));
	                    
	                    System.out.println("before storePayOrder");
	                    po_sr_no = comm.storePayOrder(po);
	                    System.out.println("after storePayOrder");
	                    
	                    //ship.....01/02/2007
	                    if(stmt2.executeUpdate("update Cheque  set cr_ac_no="+po_sr_no+" where ctrl_no="+co.getControlNo()) == 0){
	                    	sessionContext.setRollbackOnly();
	                    	throw new SQLException();
	                    }
	                    ////////////
	                }
	                
	                ResultSet rs = null,rs1 = null,rs2 = null;
	                GLTransObject trnobj = new GLTransObject();
	                
	                System.out.println("---------------------->"+ rs_po.getString("dr_ac_type"));
	                
	                // code to be added here 
	                
	                AccountTransObject am = new AccountTransObject();
						am.setAccType(rs_po.getString("dr_ac_type"));
						am.setAccNo(rs_po.getInt("dr_ac_no"));
						am.setTransType("P");
						am.setTransAmount(rs_po.getDouble("trn_amt")+rs_po.getDouble("po_comm"));
						am.setTransMode("G");
						am.setTransSource(co.getDeTml());
						am.setTransDate(co.getDe_date());
						
						am.setCdInd("D");
						am.setChqDDNo(rs_po.getInt("qdp_no"));
						am.setChqDDDate(rs_po.getString("qdp_dt"));
						am.setTransNarr("Ctrl No "+rs_po.getString("ctrl_no"));
						am.setRef_No(Integer.parseInt(rs_po.getString("ctrl_no")));
						am.setPayeeName(rs_po.getString("payee_name"));
						am.setCloseBal(rs_po.getDouble("trn_amt"));
						am.setLedgerPage(0);
						am.uv.setUserTml(co.getDeTml());
						am.uv.setUserId(co.getDeUser());
						am.uv.setUserDate(co.getDe_date());
						am.uv.setVerTml(co.getVeTml());
						am.uv.setVerId(co.getVeUser());
						am.uv.setVerDate(co.getVe_date());
						am.setTransDate(co.getDe_date());
						
						comm.storeAccountTransaction(am);
	                
	                
	                rs = stmt3.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='"+ rs_po.getString("dr_ac_type") +"' and code=1");
	                
	                if(rs.next())
	                {
	                    trnobj.setGLCode(rs.getString("gl_code"));
	                    trnobj.setGLType(rs.getString("gl_type"));
	                }
	                
	                trnobj.setCdind("D");
	                trnobj.setTrnDate(rs_po.getString("de_dt_time"));
	                trnobj.setTrnMode("G");
	                trnobj.setAmount(rs_po.getDouble("trn_amt")+rs_po.getDouble("po_comm"));
	                
	                //trnobj.setCdind(co.getCdind());
	                //ship.....28/06/2006
	                
	                trnobj.setAccType(rs_po.getString("dr_ac_type"));
	                trnobj.setAccNo(rs_po.getString("dr_ac_no"));
	                trnobj.setTrnSeq(0);
	                trnobj.setTrnType("P");
	                ///////////
	                /*trnobj.setAccType("1019001");
	                trnobj.setAccNo(null);
	                trnobj.setTrnSeq(0);
	                trnobj.setTrnType("R");*/
	                trnobj.setRefNo(Integer.parseInt(rs_po.getString("ctrl_no")));
	                trnobj.setVtml(co.getVeTml());
	                trnobj.setVid(co.getVeUser());
	                trnobj.setVDate(co.getVe_datetime());
	                
	                comm.storeGLTransaction(trnobj);
	                
	                System.out.println("2....PO........");
	                System.out.println("cash bean verify PO acc_type = "+co.getDebitACType());
	 
	                po.setGLRefCode(Integer.parseInt(rs_po.getString("cr_ac_type")));
	                po.setGLSubCode(1);
	                po.setPOAmount(rs_po.getDouble("trn_amt"));
	                
	                rs1=stmt4.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type="+po.getGLRefCode()+" and code="+po.getGLSubCode()+"");
	                
	                if(rs1.next())
	                {
	                    trnobj.setGLCode(rs1.getString(1));
	                    trnobj.setGLType(rs1.getString(2));
	                }
	                
	                trnobj.setCdind("C");
	                trnobj.setTrnDate(co.getDe_date());
	                trnobj.setTrnMode("G");
	                trnobj.setAmount(po.getPOAmount());
	                trnobj.setAccType(rs_po.getString("cr_ac_type"));
	                trnobj.setAccNo(String.valueOf(po_sr_no));
	                trnobj.setTrnSeq(0);
	                trnobj.setTrnType("R");
	                trnobj.setRefNo(Integer.parseInt(rs_po.getString("ctrl_no")));
	                trnobj.setVtml(co.getVeTml());
	                trnobj.setVid(co.getVeUser());
	                trnobj.setVDate(co.getVe_datetime());
	                
	                comm.storeGLTransaction(trnobj);
	                
	                System.out.println("3....PO........");
	                
	                //Posting to Profit
	                po.setGLRefCode(Integer.parseInt(rs_po.getString("cr_ac_type")));
	                po.setGLSubCode(2);
	                po.setPOAmount(rs_po.getDouble("po_comm"));
	                
	                rs2=stmt5.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type="+po.getGLRefCode()+" and code="+po.getGLSubCode()+"");
	                
	                if(rs2.next())
	                {
	                    trnobj.setGLCode(rs2.getString(1));
	                    trnobj.setGLType(rs2.getString(2));
	                }
	                
	                trnobj.setCdind("C");
	                trnobj.setTrnDate(co.getDe_date());
	                trnobj.setTrnMode("G");
	                trnobj.setAmount(po.getPOAmount());
	                trnobj.setAccType(rs_po.getString("cr_ac_type"));
	                trnobj.setAccNo(String.valueOf(po_sr_no));
	                trnobj.setTrnSeq(0);
	                trnobj.setTrnType("R");
	                trnobj.setRefNo(Integer.parseInt(rs_po.getString("ctrl_no")));
	                trnobj.setVtml(co.getVeTml());
	                trnobj.setVid(co.getVeUser());
	                trnobj.setVDate(co.getVe_datetime());
	                
	                comm.storeGLTransaction(trnobj);
	                
	                System.out.println("3....PO........");
	                System.out.println("3..........................................");
	            }
	            
	            if(po_sr_no > 0 && co.getVeTml()!= null){
	            	
	            	int h = stmt6.executeUpdate("update Cheque  set ve_tml='"+ co.getVeTml()+"', ve_user='"+ co.getVeUser()+"', ve_dt_time ='"+ co.getVe_datetime()+"',desp_ind='T', post_ind ='T' where ctrl_no ="+co.getControlNo()); 
	            } 
	            
	            
			}
	        catch(SQLException e)
	        {
	        	sessionContext.setRollbackOnly();
	        	e.printStackTrace();
	            throw new RecordNotInsertedException();
	        }catch (Exception e) {
	        	sessionContext.setRollbackOnly();
	        	e.printStackTrace();
				
			}
	        finally
	        {
	            try{
	            	conn.close();
	            }catch (SQLException e) {
	            	e.printStackTrace();
					// TODO: handle exception
				}
	        	
	        }
	        return po_sr_no;
	 		
	 	}
	 	
	 	public int[] getClearingNo(String date , int send_to,int type)throws RecordsNotFoundException{
	 		
	 		int[] clg_no = null;
	 		Connection conn=null;
	 		ResultSet rs  = null;
	 		try{
	 			conn = getConnection();
	 			
	 			Statement stmt = conn.createStatement();
	 			if (type == 1) 
	 				rs = stmt.executeQuery(" select distinct clg_no from Cheque,BranchMaster bm where desp_ind='T' and concat(right(exp_clgdt,4),'-',mid(exp_clgdt,locate('/',exp_clgdt)+1,(locate('/',exp_clgdt,4)-locate('/',exp_clgdt)-1)),'-',left(exp_clgdt,locate('/',exp_clgdt)-1)) = '"+Validations.convertYMD(date)+"' and ret_norm ='N' and send_to=br_code and post_ind='F' and trn_type='G' and clg_type='O' and doc_bs='S' and send_to =" +send_to);
	 			
	 			else if (type == 2)
	 				rs = stmt.executeQuery("select distinct send_to from Cheque,BranchMaster bm where desp_ind='T' and concat(right(exp_clgdt,4),'-',mid(exp_clgdt,locate('/',exp_clgdt)+1,(locate('/',exp_clgdt,4)-locate('/',exp_clgdt)-1)),'-',left(exp_clgdt,locate('/',exp_clgdt)-1)) = '" + Validations.convertYMD(date)+ "' and ret_norm ='N' and send_to = br_code and post_ind='F' and trn_type='G' and clg_type='O' and doc_bs='S'");
	 			
	 			else if (type == 3)
	 				rs = stmt.executeQuery("select distinct send_to from Cheque,BranchMaster bm where desp_ind not like 'T' and concat(right(clg_date,4),'-',mid(clg_date,locate('/',clg_date)+1,(locate('/',clg_date,4)-locate('/',clg_date)-1)),'-',left(clg_date,locate('/',clg_date)-1)) <= '" + Validations.convertYMD(date)+ "'  and send_to = br_code and post_ind='F' and trn_type='G' and (clg_type='O' || clg_type='C' || clg_type='D' || (clg_type='I' && doc_bs='B') || (clg_type='T' && doc_bs='B')) ");
	 			
	 			else if ( type == 4 ) 
	 				rs = stmt.executeQuery("select distinct clg_no from Cheque,BranchMaster bm where desp_ind not like 'T' and concat(right(clg_date,4),'-',mid(clg_date,locate('/',clg_date)+1,(locate('/',clg_date,4)-locate('/',clg_date)-1)),'-',left(clg_date,locate('/',clg_date)-1)) <= '" + Validations.convertYMD(date)+ "'  and send_to = br_code and post_ind='F' and trn_type='G' and (clg_type='O' || clg_type='C' || clg_type='D' || (clg_type='I' && doc_bs='B') || (clg_type='T' && doc_bs='B'))  and send_to =" +send_to);
	 			
	 			
	 			if( rs.next()){
	 				rs.last();
	 				clg_no  = new int[rs.getRow()];
	 				rs.beforeFirst();
	 			} else {
	 				
	 				clg_no=null;
	 				return clg_no;
	 				
	 			}
	 			int i = 0 ;
	 			while(rs.next()){
	 				
	 				
	 				if ( type == 1 || type == 4 ){
	 					clg_no[i] = rs.getInt("clg_no");
	 					System.out.println(rs.getInt("clg_no") + "^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	 				}
	 				else if ( type == 2 || type == 3  ){
	 					
	 					clg_no[i] = rs.getInt("send_to");
	 					System.out.println(rs.getInt("send_to") + "^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	 				}
	 				i++;
	 			}
	 			
	 			
	 		}catch(SQLException rec){
	 			
	 			rec.printStackTrace();
	 			
	 			

	 		}catch(Exception sql){
	 			
	 			sql.printStackTrace();
	 			throw new RecordsNotFoundException();
	 		}
	 		finally{
	 			
	 			try{
	 				conn.close();
	 				
	 			}catch(SQLException sql){
	 				
	 				sql.printStackTrace();
	 				
	 			}
	 		}
	 		
	 		
	 		return clg_no;
	 		
	 	}
	 	
	 	
	 }

 