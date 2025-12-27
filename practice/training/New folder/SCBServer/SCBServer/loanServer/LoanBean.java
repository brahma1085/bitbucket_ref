package loanServer;

import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import exceptions.GLKeyCodeNotFound;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotDeletedException;
import exceptions.RecordsNotFoundException;
import exceptions.RecordsNotInsertedException;
import exceptions.RecordsNotVerifiedException;
import exceptions.SignatureNotInsertedException;
import frontCounterServer.FrontCounterLocal;
import frontCounterServer.FrontCounterLocalHome;
import general.Validations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;

import masterObject.frontCounter.AccountTransObject;
import masterObject.general.AccountObject;
import masterObject.general.DoubleFormat;
import masterObject.general.GoldObject;
import masterObject.general.IncomeObject;
import masterObject.general.PropertyObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.VehicleObject;
import masterObject.loans.DCBObject;
import masterObject.loans.DetailsObject;
import masterObject.loans.DocumentsObject;
import masterObject.loans.LedgerObject;
import masterObject.loans.LoanIntRateObject;
import masterObject.loans.LoanMasterObject;
import masterObject.loans.LoanReportObject;
import masterObject.loans.LoanStageObject;
import masterObject.loans.LoanTransactionObject;
import masterObject.loans.NPAObject;
import masterObject.loans.PSWSObject;
import masterObject.loans.PriorityMasterObject;
import masterObject.loans.SurityCoBorrowerObject;
import masterObject.loansOnDeposit.LoanPurposeObject;
import masterObject.pygmyDeposit.SimpleMasterObject;

import org.apache.log4j.Logger;

import commonServer.CommonLocal;
import commonServer.CommonLocalHome;
import commonServer.CommonRemote;
import commonServer.GLTransObject;

public class LoanBean implements SessionBean 
{
	javax.sql.DataSource ds=null; 
	
	commonServer.CommonHome home;  
	CommonRemote cremote=null;
	CommonLocal commonlocal=null; // Code added by Murugesh on 15/12/2005
	SessionContext sessionContext = null; 
	CommonLocalHome commonlocalhome=null;
	private static Logger file_logger = null;
	
	public LoanBean()
	{
		try{	
			//			Context ctx=getInitialContext();			
			//ds=(javax.sql.DataSource)ctx.lookup("java:MySqlDS");
			//Object obj=ctx.lookup("COMMON");
			//commonServer.CommonHome home=(commonServer.CommonHome)obj;
			
			commonlocalhome=(CommonLocalHome) getInitialContext().lookup("COMMONLOCALWEB");
			// Code altered by Murugesh on 15/12/2005
			//	CommonLocal commonlocal=commonlocalhome.create();
			commonlocal=commonlocalhome.create();
			//
			
		}catch(Exception ex){ex.printStackTrace();}
		
	}
	
	public void setSessionContext(SessionContext ctx) throws EJBException{
		sessionContext = ctx; 
	}
	public void ejbCreate()
	{
		try{	
			Context ctx=new InitialContext();			
			Object obj=ctx.lookup("COMMONWEB");
			ds=(javax.sql.DataSource)ctx.lookup("java:MySqlDS");
			home=(commonServer.CommonHome)obj;
			commonlocalhome=(CommonLocalHome) getInitialContext().lookup("COMMONLOCALWEB");
			// Code altered by Murugesh on 15/12/2005
			//	CommonLocal commonlocal=commonlocalhome.create();
			commonlocal=commonlocalhome.create();
			
		}catch(Exception ex){ex.printStackTrace();}
		
	}
	public void ejbRemove() throws EJBException{}
	public void ejbActivate() throws EJBException{}
	public void ejbPassivate() throws EJBException { System.out.println("LoansBean Passivating");}
	
	//Select the interest rate between the given date for the given account type,category,period,amt
	public double  getIntRate(String ln_type,String fdate,int category,int period,double amt,int ac_no) 
	{
		double rate=0;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			System.out.println("Date from client"+fdate);
			fdate=Validations.convertYMD(fdate);
			System.out.println("From date"+fdate);
			System.out.println("Category"+category);
			System.out.println("Period"+period);
			System.out.println("Amount"+amt);
			
			stmt=conn.createStatement();
			
			//select interest rate for given amt
			//			 Code added by Murugesh on 03/06/2006
			ResultSet rs_purpose=null;
			int purpose_code=0;
			rs_purpose=stmt.executeQuery("select pps_code  from LoanMaster where ac_type='"+ln_type+"' and ac_no="+ac_no+" ");
			if(rs_purpose.next())
				purpose_code=rs_purpose.getInt("pps_code");
			rs_purpose=stmt.executeQuery("select  * from LoanIntRate where ln_type='"+ln_type+"' and  "+amt+" between min_bal and max_bal and  pps_code="+purpose_code+" and '"+fdate+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
			if(!rs_purpose.next())
				purpose_code=0;
			// 
			rs=stmt.executeQuery("select rate from LoanIntRate where ln_type='"+ln_type+"' and  "+amt+" between min_bal and max_bal and  pps_code="+purpose_code+" and '"+fdate+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
			if(rs.next())
				rate=rate+rs.getDouble("rate");
			
			//Select interest rate for given period
			rs=stmt.executeQuery("select rate from LoanPeriodRate where ln_type='"+ln_type+"' and "+period+" between fr_mon and to_mon and '"+fdate+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
			if(rs.next())
				rate=rate+rs.getDouble("rate");
			
			//select interest rate for given category
			rs=stmt.executeQuery("select rate from LoanCategoryRate where ln_type="+ln_type+" and category="+category+" and '"+fdate+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
			if(rs.next())
				rate=rate+rs.getDouble("rate");
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return rate;
	}
	
	 public Object penalIntFix(LoanTransactionObject loan_trn) 
	 {
	 	Connection conn=null;
	 	Statement stmt=null,stmt_master=null;
	 	PreparedStatement pstmt=null;
 		ResultSet rs_master=null,rs=null;
 		
	 	try{
	 		cremote=home.create();
	 		System.out.println("Inside the Penal Interest Fix Method");
	 		conn=getConnection();
	 		stmt=conn.createStatement();
	 		stmt_master=conn.createStatement();
	 		
	 		
	 			
	 			
	 			pstmt = conn.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

				pstmt.setString(1,loan_trn.getAccType());
				pstmt.setInt(2,loan_trn.getAccNo());
				pstmt.setInt(3,1);
				pstmt.setString(4,"27/11/1982");
				pstmt.setString(5,"R");
				pstmt.setDouble(6,loan_trn.getTransactionAmount());
				pstmt.setString(7,"T");
				pstmt.setString(8,"LN01");
				pstmt.setInt(9,987123);
				pstmt.setString(10,"Penal Fix");
				pstmt.setString(11,null);
				//pstmt.setString(12,loan_trn.getCdind());
				pstmt.setString(12,"D");
				pstmt.setString(13,"27/11/1982");
				pstmt.setDouble(14,0);
				pstmt.setDouble(15,0);
				pstmt.setDouble(16,loan_trn.getPenaltyAmount());
				pstmt.setDouble(17,0);
				pstmt.setDouble(18,0);
				pstmt.setDouble(19,0);
				pstmt.setString(20,"Vinay");
				pstmt.setString(21,"Vinay");
				pstmt.setString(22,"27/11/1982");
				pstmt.setString(23,"Vinay");
				pstmt.setString(24,"Vinay");
				pstmt.setString(25,"27/11/1982");
				
				pstmt.executeUpdate();
				
				conn.createStatement().executeUpdate("delete from LoanRecoveryDetail where ac_type like '"+loan_trn.getAccType()+"' and ac_no = "+ loan_trn.getAccNo() +" ");
				
	 	} catch(Exception e){
	 		System.out.println("inside reverse &&&&&&&&&&&&&&... 7");
	 		e.printStackTrace();
	 		sessionContext.setRollbackOnly();
	 		return null;
	 	}
	 	finally{
	 		try{                
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	return "1";
	 }
	
	//Select the Penal interest rate between the given date for the given account type
	public LoanIntRateObject[] getPenalIntRate(String ln_type,String fdate,String tdate,int category) 
	{
		LoanIntRateObject d[]=null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			ResultSet rs=null;
			System.out.println("Ac type"+ln_type);
			System.out.println("Ac type"+fdate);
			System.out.println("Ac type"+tdate+"  "+category);
			
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select * from PenalIntRate where ln_type='"+ln_type+"' and  category="+category+" and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) ");
			
			rs.last();
			d=new LoanIntRateObject[rs.getRow()];
			rs.beforeFirst();			
			int i=0;
			
			while(rs.next())
			{
				d[i]=new LoanIntRateObject();
				d[i].setFromDate(rs.getString("date_fr"));
				d[i].setToDate(rs.getString("date_to"));
				d[i].setPenalRate(rs.getDouble("penalrate"));
				System.out.println("From date"+d[i].getFromDate());
				System.out.println("to date"+d[i].getToDate());
				System.out.println("reate"+d[i].getPenalRate());
				i++;			
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return d;
	}
	
	public Vector getIntRate(String ln_type,String fdate,String tdate,int category,int period,double amt,int ac_no) 
	{
		Vector vec=null;
		Connection conn=null;
		int purpose_code=0;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			ResultSet rs=null,rs_purpose=null;
			stmt=conn.createStatement();
			vec=new Vector(3);
			for(int j=0;j<3;j++)
			{
				if(j==0){	
					
					// Code added by Murugesh on 03/06/2006
					if(ln_type.startsWith("1010")) {
						rs_purpose=stmt.executeQuery("select pps_code  from LoanMaster where ac_type='"+ln_type+"' and ac_no="+ac_no+" ");
					} else {
						rs_purpose=stmt.executeQuery("select pps_code  from ODCCMaster where ac_type='"+ln_type+"' and ac_no="+ac_no+" ");
					}
					
					if(rs_purpose.next())
						purpose_code=rs_purpose.getInt("pps_code");
					rs_purpose=stmt.executeQuery("select  * from LoanIntRate where ln_type='"+ln_type+"' and  "+amt+" between min_bal and max_bal and  pps_code="+purpose_code+" and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
					if(!rs_purpose.next())
						purpose_code=0;
					// 
					rs=stmt.executeQuery("select  distinct date_fr,date_to,rate from LoanIntRate where ln_type='"+ln_type+"' and  "+amt+" between min_bal and max_bal and   pps_code="+purpose_code+" and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
				}
				else if(j==1)
					rs=stmt.executeQuery("select distinct date_fr,date_to,rate from LoanPeriodRate where ln_type='"+ln_type+"' and "+period+" between fr_mon and to_mon and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
				else if(j==2)
					rs=stmt.executeQuery("select distinct date_fr,date_to,rate from LoanCategoryRate where ln_type="+ln_type+" and category="+category+" and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
				
				rs.last();
				LoanIntRateObject d[]=new LoanIntRateObject[rs.getRow()];
				rs.beforeFirst();
				int i=0;
				while(rs.next())
				{
					d[i]=new LoanIntRateObject();
					d[i].setFromDate(rs.getString("date_fr"));
					d[i].setToDate(rs.getString("date_to"));
					d[i].setIntRate(rs.getDouble("rate"));
					i++;
				}
				vec.addElement(d);
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return vec;
	}
	
	
	public double getPenalIntRate(String ln_type,String fdate,int category) 
	{
		
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			ResultSet rs=null;
			rs=stmt.executeQuery("select * from PenalIntRate where ln_type='"+ln_type+"' and  concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(fdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and category="+category);
			if(rs.next())
				return(rs.getDouble("penalrate"));
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return 0;
	}
	
	
	/*
	 * Method returns Loantransactionobject for given acctype,acno which
	 * disbursement details are not yet verified 
	 */
	
	public LoanTransactionObject getUnVerifiedDisbursement(String ln_type,int acno) 
	{
		LoanTransactionObject lnobj=null;
		
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			System.out.println("Server"+ln_type+"  "+acno);
			stmt=conn.createStatement();
			
			// ResultSet rs=stmt.executeQuery("select * from LoanTransaction where ve_user is NULL and trn_type='D' and ac_type='"+ln_type+"' and ac_no="+acno); // Code changed by Murugesh 19/1/2006
			ResultSet rs=stmt.executeQuery("select * from LoanTransaction where ve_user is NULL and trn_type='D' and ac_type='"+ln_type+"' and ac_no="+acno+" order by trn_seq desc limit 1"); // Code added by Murugesh 19/1/2006
			if(rs.next())
			{
				lnobj=new LoanTransactionObject();
				
				lnobj.setAccType(rs.getString("ac_type"));
				lnobj.setAccNo(rs.getInt("ac_no"));
				lnobj.setTranSequence(rs.getInt("trn_seq"));
				lnobj.setTranType(rs.getString("trn_type"));
				lnobj.setTranNarration(rs.getString("trn_narr"));
				lnobj.setTranMode(rs.getString("trn_mode"));
				lnobj.setTranSou(rs.getString("trn_source"));
				lnobj.setCdind(rs.getString("cd_ind"));
				
				lnobj.setReferenceNo(rs.getInt("ref_no"));
				lnobj.setLoanBalance(rs.getDouble("pr_bal"));
				lnobj.setTransactionAmount(rs.getDouble("trn_amt"));
				lnobj.setOtherAmount(rs.getDouble("other_amt"));
				lnobj.setPenaltyAmount(rs.getDouble("penal_amt"));
				lnobj.setExtraIntAmount(rs.getDouble("extra_int"));
				
				lnobj.setIntUptoDate(rs.getString("int_date"));
				lnobj.setTransactionDate(rs.getString("trn_date"));
				lnobj.setRecoveryDate(rs.getString("rcy_date"));
				
				// Code added by Murugesh on 11/1/2006
				lnobj.uv.setUserTml(rs.getString("de_tml"));
				lnobj.uv.setUserId(rs.getString("de_user"));
				lnobj.uv.setUserDate(rs.getString("de_date"));
				//
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return lnobj;
	}
	
	/*
	 * Get loan details for given account type and account number
	 */
	
	public LoanReportObject getLoanDetails(String ln_type,int acno,String date) 
	{
		LoanReportObject lnobj=null;
		Connection conn=null;
		String share_type=null,ln_ac_type=null,sb_type=null;
		int sb_no=0,ln_ac_no=0,share_no=0, surity_cid=0,surity_add_type=0,cid=0;
		System.out.println("Loan Type:"+ln_type);
		System.out.println("Loan Ac No:"+acno);
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs=null;	
			stmt=conn.createStatement();
			
			rs=stmt.executeQuery("select lm.ac_type,lm.ac_no,lm.int_type,lm.int_rate_type,lm.pay_ac_type,lm.pay_ac_no,lm.sh_type,lm.sh_no,lm.no_inst,lm.sanc_amt,lm.int_rate,lm.inst_amt,lm.int_upto_date,lm.holday_mth,lp.pps_desc,concat_ws(' ',cm.fname,cm.mname,cm.lname) name,cm.sub_category,lm.nom_reg_no,cm.cid from LoanMaster lm,LoanPurposes lp,CustomerMaster  cm where  lm.ac_type='"+ln_type+"' and lm.ac_no="+acno+" and cm.cid=lm.cid and lm.pps_code=lp.pps_no"); // Code added by Murugesh on 20/1/2006
			
			if(rs.next())
			{
				lnobj=new LoanReportObject();
				cid=rs.getInt("cid"); // Code added by Murugesh on 20/1/2006
				lnobj.setAccType(rs.getString("ac_type"));
				lnobj.setAccNo(rs.getInt("ac_no"));
				lnobj.setNomRegNo(rs.getInt("nom_reg_no"));
				lnobj.setSurityDetails("Nominee Reg.No:"+rs.getInt("nom_reg_no"));
				
				ln_ac_type=rs.getString("ac_type");
				ln_ac_no=rs.getInt("ac_no");
				share_no=rs.getInt("sh_no");
				share_type=rs.getString("sh_type");
				sb_type=rs.getString("pay_ac_type");
				System.out.println("SBBBBBBBBBBB"+sb_type);
				sb_no=rs.getInt("pay_ac_no");
				System.out.println("SBNo"+"No"+sb_no);
				lnobj.setNoInstallments(rs.getInt("no_inst"));
				lnobj.setSanctionedAmount(rs.getDouble("sanc_amt"));
				
				if(rs.getInt("int_rate_type")==0)
					lnobj.setLoanIntRate(rs.getDouble("int_rate"));
				else
					lnobj.setLoanIntRate(getIntRate(lnobj.getAccType(),date,rs.getInt("sub_category"),lnobj.getNoInstallments(),lnobj.getSanctionedAmount(),lnobj.getAccNo()));
				
				lnobj.setInstallmentAmount(rs.getDouble("inst_amt"));
				lnobj.setLoanPurpose(rs.getString("pps_desc"));
				lnobj.setInterestType(rs.getInt("int_type"));
				lnobj.setIntUptoDate(rs.getString("int_upto_date"));
				lnobj.setHolidayPeriod(rs.getInt("holday_mth"));
				lnobj.setName(rs.getString("name"));
				
				
				//Select last disbursement date and disbursement amount
				rs=stmt.executeQuery("select trn_date,trn_amt,trn_type from LoanTransaction where ac_type="+ln_type+"  and ac_no="+acno+" and trn_type='D' and ve_tml is not null order by trn_seq desc");
				if(rs.next()){
					lnobj.setDisbursementDate(rs.getString("trn_date"));
					lnobj.setDisburseAmount(rs.getDouble("trn_amt"));
				}
				
				lnobj.setLoanBalance(getCurrentDayClosingBalance(ln_type,acno,date));
				
				rs=stmt.executeQuery("select moduleabbr from Modules where modulecode='"+share_type+"'"); // Code added by Murugesh on 08/06/2006
				if(rs.next())
					lnobj.setShareDet(rs.getString("moduleabbr")+" "+share_no);

				rs=stmt.executeQuery("select moduleabbr from Modules where moduleabbr='"+sb_type+"'"); // Code added by Murugesh on 08/06/2006
				if(rs.next()){
					lnobj.setSavingDet(rs.getString("moduleabbr")+"  "+sb_no);
					System.out.println("Savings"+rs.getString("moduleabbr")+"  "+sb_no);
				}
				rs=stmt.executeQuery("select ca.* from CustomerAddr as ca , ShareMaster as sm where sm.cid="+cid+" and ca.cid="+cid+" and ca.addr_type=sm.addr_type"); // Code added by Murugesh on 20/1/2006
				if(rs.next()){
					surity_cid=rs.getInt("cid");
					surity_add_type=rs.getInt("addr_type"); // Code added by Murugesh on 20/1/2006
				}

				rs=stmt.executeQuery("select concat_ws(' ',cm.fname,cm.mname,cm.lname) surity_name,ca.address,ca.city,ca.state,ca.pin,ca.country,ca.phno,ca.email from CustomerMaster cm,CustomerAddr ca where cm.cid="+surity_cid+" and ca.addr_type="+surity_add_type+" and ca.cid=cm.cid ");
				if(rs.next()){
					//lnobj.setSurityDetails(rs.getString("surity_name")+"\n"+rs.getString("address")+"\n"+rs.getString("city")+" "+rs.getString("state")+"\n"+rs.getInt("pin")+" "+rs.getString("country")); // Code changed by Murugesh on 20/1/2006
					lnobj.setPhoneNo(rs.getInt("phno"));
					lnobj.setEmail(rs.getString("email"));
				}
				
				// Code added by Murugesh on 20/1/2006
				String surity_details=" ";
				int i=1;
				rs=stmt.executeQuery("select sh.cid,sh.addr_type from ShareMaster as sh, BorrowerMaster as bm where bm.ln_ac_type='"+ln_type+"' and bm.ln_ac_no="+acno+" and bm.ac_type = sh.ac_type and bm.ac_no=sh.ac_no and bm.type='S'");
				ResultSet rs1;
				Statement stmt1=conn.createStatement();
				while(rs.next()){
					rs1=stmt1.executeQuery("select (cm.fname) surity_name,ca.address,ca.city,ca.state,ca.pin,ca.country,ca.phno,ca.email from CustomerMaster cm,CustomerAddr ca where cm.cid="+rs.getInt("cid")+" and ca.addr_type="+rs.getInt("addr_type")+" and ca.cid=cm.cid ");
					if(rs1.next()){
						surity_details=(surity_details+""+i+":"+rs1.getString("surity_name")+"\n"+rs1.getString("address")+"\n"+rs1.getString("city")+" "+rs1.getString("state")+"\n"+rs1.getInt("pin")+" "+rs1.getString("country")+"\n ---------------------\n");
						i++;
					}
				}
				lnobj.setSurityDetails(surity_details);
				//
			}
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return lnobj;
		
	}
	
	/*
	 * Select transaction records for given account type and account number
	 * String str will be either "Schedule"/"Recovery"
	 * "Schedule"--> will return only "S" records
	 * "Recovery"-->will return only "R" records
	 */
	public LoanTransactionObject[] getLoanTransaction(String ln_type,int acno,String str) 
	{
		LoanTransactionObject lnobj[]=null;
		String trn_narration=null;
		StringTokenizer trn_token=null;
		
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement stmt_narr=conn.createStatement();
			
			System.out.println("Server "+ln_type+"  "+acno+"  "+str);
			System.out.println("Loan Type:"+ln_type);
			System.out.println("Loan Ac No:"+acno);
			ResultSet rs=null,rs_narr=null;
			
			if(str.equals("Schedule"))		
				rs=stmt.executeQuery("select * from LoanTransaction where  trn_type='S' and ac_type='"+ln_type+"' and ac_no="+acno+" and ve_tml is not null order by concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)),trn_seq");
			else if(str.equals("Recovery"))		
				rs=stmt.executeQuery("select * from LoanTransaction where trn_type Not like '%S%' and ac_type='"+ln_type+"' and ac_no="+acno+" and ve_tml is not null order by concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)),trn_seq");
			
			System.out.println("+++++++++++++++++++++++++++ 1 +++++++++++++++++++++++++");
			rs.last();
			lnobj=new LoanTransactionObject[rs.getRow()];
			
			System.out.println("+++++++++++++++++++++++++++row "+rs.getRow()+" +++++++++++++++++++++++++");
			rs.beforeFirst();
			
			System.out.println("+++++++++++++++++++++++++++ 2 +++++++++++++++++++++++++");
			int i=0;
			while(rs.next())
			{
				lnobj[i]=new LoanTransactionObject();
				
				System.out.println("+++++++++++++++++++++++++++ 3 +++++++++++++++++++++++++");
				
				lnobj[i].setAccType(rs.getString("ac_type"));
				lnobj[i].setAccNo(rs.getInt("ac_no"));
				lnobj[i].setTranSequence(rs.getInt("trn_seq"));
				lnobj[i].setTranType(rs.getString("trn_type"));
				
				trn_narration=rs.getString("trn_narr");
				if(trn_narration!=null){
					try{
						trn_token=new StringTokenizer(trn_narration," ");
						if(trn_token.hasMoreTokens()){
							rs_narr=stmt_narr.executeQuery("select moduleabbr from Modules where modulecode='"+trn_token.nextToken()+"'");
							if(rs_narr.next())
								trn_narration=rs_narr.getString("moduleabbr")+" "+trn_token.nextToken();
						}
					}catch(Exception exe){exe.printStackTrace();}
				}
				
				lnobj[i].setTranNarration(trn_narration);
				lnobj[i].setTranMode(rs.getString("trn_mode"));
				lnobj[i].setTranSou(rs.getString("trn_source"));
				lnobj[i].setCdind(rs.getString("cd_ind"));
				
				lnobj[i].setReferenceNo(rs.getInt("ref_no"));
				lnobj[i].setPrincipalPaid(rs.getDouble("pr_amt"));
				lnobj[i].setInterestPaid(rs.getDouble("int_amt"));
				lnobj[i].setLoanBalance(rs.getDouble("pr_bal"));
				lnobj[i].setTransactionAmount(rs.getDouble("trn_amt"));
				lnobj[i].setOtherAmount(rs.getDouble("other_amt"));
				lnobj[i].setPenaltyAmount(rs.getDouble("penal_amt"));
				lnobj[i].setExtraIntAmount(rs.getDouble("extra_int"));
				
				lnobj[i].setIntUptoDate(rs.getString("int_date"));
				lnobj[i].setTransactionDate(rs.getString("trn_date"));
				lnobj[i].setRecoveryDate(rs.getString("rcy_date"));
				i++;
				
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return lnobj;
	}
	
	
	//Select the minimum share percentage should be maintained for this loan account type
	public double getSharePercentage(String ln_type) 
	{
		double d=0.0;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			ResultSet rs=stmt.executeQuery("select * from Modules  where modulecode='"+ln_type+"'");
			if(rs.next())
				d=rs.getDouble("lnk_shares");			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return d;
	}
	
	//Verify the Loan Disbursement  for given transaction number,account type,account number
	public int verifyLoanDisbursement(int trn,String ac_type,int ac_no,String uid,String tml,String sbtype,int sbno,double disbursement,LoanMasterObject loan_object,LoanTransactionObject loan_trn_object,String date_time) 
	{
		Connection conn=null;
		int voucher_number=0;
		int ps_no=0;
		System.out.println("type in bean" + sbtype);
		try{
			cremote=home.create();
			conn=getConnection();
			Statement stmt=conn.createStatement();
			stmt=conn.createStatement();
			int num_disb = getNumPreviousDisb(ac_type,ac_no,2); // pass 2 to get only the verified disbursements
			int d = stmt.executeUpdate("update LoanMaster set  lst_trn_date='"+loan_trn_object.getTransactionDate()+"',lst_tr_seq="+trn+",disb_left=disb_left-"+disbursement+" where ac_type='"+ac_type+"' and ac_no="+ac_no);
			int c = stmt.executeUpdate("update LoanTransaction set trn_type='S"+(num_disb)+"' , ve_user='"+uid+"',ve_tml='"+tml+"',ve_date='"+date_time+"'where trn_type='S' and ac_type="+ac_type+" and ac_no="+ac_no+" ");
			
			num_disb = getNumPreviousDisb(ac_type,ac_no,1); // pass 1 to get all the verified and un verified disbursements
			int b = stmt.executeUpdate("update LoanTransaction set trn_type='S' , ve_user='"+uid+"',ve_tml='"+tml+"',ve_date='"+date_time+"'where trn_type like 'S%' and ac_type="+ac_type+" and ac_no="+ac_no+" and ve_tml is null"); // Code added by Murugesh 0n 4/1/2005
			int a=stmt.executeUpdate("update LoanTransaction set ve_user='"+uid+"',ve_tml='"+tml+"',ve_date='"+date_time+"' where ac_type="+ac_type+" and ac_no="+ac_no+" and trn_seq="+trn+" and trn_type='D'");
			System.out.println("(((((((((((value of a)))))))))))" + a);
			if(a==1)
			{   System.out.println("*loan master object tran mode**************" + loan_object.getPayMode());
				if(loan_object.getPayMode().equals("C"))
				{	
					System.out.println("Am in the paymode");
					Statement st2=conn.createStatement();
					ResultSet rs2=st2.executeQuery("SELECT lst_voucher_no FROM Modules where modulecode=1019000");
					if(rs2.next())
						voucher_number=rs2.getInt("lst_voucher_no")+1;
					System.out.println("************voucher_number***********" + voucher_number);
					rs2.close();
					st2.executeUpdate( "update Modules set lst_voucher_no =lst_voucher_no+1 where modulecode=1019000");
					
					PreparedStatement pstmt1=conn.prepareStatement("insert into DayCash (scroll_no,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,attached,vch_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,trn_type,name,trn_seq) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");					
					System.out.println("Calling DAYCASH !");
					
					pstmt1.setInt(1,0);
					pstmt1.setString(2,loan_trn_object.getTransactionDate()); 
					pstmt1.setString(3,ac_type);
					pstmt1.setInt(4,ac_no);
					pstmt1.setDouble(5,disbursement);
					pstmt1.setString(6,"C");
					pstmt1.setString(7,"D");
					pstmt1.setString(8,"F");
					pstmt1.setInt(9,voucher_number);
					pstmt1.setString(10,loan_trn_object.uv.getUserId()); 
					pstmt1.setString(11,loan_trn_object.uv.getUserTml());
					pstmt1.setString(12,loan_trn_object.uv.getUserDate());
					pstmt1.setString(13,uid);
					pstmt1.setString(14,tml);
					pstmt1.setString(15,date_time);
					//pstmt1.setString(16,"P");
					pstmt1.setString(16,"D");
					pstmt1.setString(17,loan_object.getName()); // pending.....
					pstmt1.setInt(18,trn);
					
					pstmt1.executeUpdate();
					System.out.println("+++++++++++++++++++++++++++++++++ CCC +++++++++++++++++++++++++");
				}
				          
				if(loan_object.getPayMode().equals("T"))
				{
					System.out.println("+++++++++++++++++++++++++++++++++ TTT +++++++++++++++++++++++++");
					AccountTransObject accounttransobject=new AccountTransObject();
					accounttransobject.setAccType(sbtype);
					accounttransobject.setAccNo(sbno);				
					accounttransobject.setTransAmount(disbursement);
					accounttransobject.setTransDate(loan_trn_object.getTransactionDate());
					accounttransobject.setTransMode("T");
					accounttransobject.setTransType("R");
					accounttransobject.setCdInd("C");
					accounttransobject.setTransNarr(ac_type +" "+ac_no); // pending.....UserTml and UserID
					accounttransobject.uv.setUserTml(uid);
					accounttransobject.uv.setUserId(tml);
					accounttransobject.uv.setUserDate(date_time);
					accounttransobject.uv.setVerTml(uid);
					accounttransobject.uv.setVerId(tml);
					accounttransobject.uv.setVerDate(date_time);
					System.out.println("Before home creation");
					System.out.println("Before home creation");
					cremote.storeAccountTransaction(accounttransobject);
				}
				
				if(loan_object.getPayMode().equals("P"))
				{
					Statement stmt_ps_no=conn.createStatement();
					ResultSet rs_ps_no=stmt_ps_no.executeQuery("select last_trf_scroll_no from Modules where modulecode='1016001'");
					if(rs_ps_no!=null)
					{
						if(rs_ps_no.next())
						{
							ps_no=rs_ps_no.getInt("last_trf_scroll_no")+1;
							PreparedStatement ps_update_lst_no=conn.prepareStatement("update Modules set last_trf_scroll_no="+ps_no+" where modulecode='1016001'");
							ps_update_lst_no.executeUpdate();
							System.out.println("+++++++++++++++++++++++++++++++++1+++++++++++++++++++++++++");
							/*if(ps_update_lst_no.executeUpdate()==0)
							 throw new SQLException();*/
						}
					}
				}
				
				ResultSet rs1=stmt.executeQuery("select gk.gl_type,gp.gl_code,gp.mult_by from GLPost gp,GLKeyParam gk where gk.ac_type="+ac_type+" and gp.ac_type="+ac_type+" and gk.gl_code=gp.gl_code and trn_type='D' and cr_dr='D' and code=1");
				GLTransObject trnobj=new GLTransObject();
				if(rs1.next()){
					trnobj.setGLType(rs1.getString("gk.gl_type"));
					trnobj.setGLCode(rs1.getString("gp.gl_code"));
					trnobj.setAmount(disbursement*rs1.getInt("gp.mult_by"));
				}else {
					trnobj.setGLType(null);
					trnobj.setGLCode(null);
					trnobj.setAmount(disbursement);
				}
				
				{
					//debiting amt in loan gl
					trnobj.setTrnDate(loan_trn_object.getTransactionDate());
					trnobj.setTrnMode(loan_object.getPayMode()); 
					trnobj.setCdind("D");
					trnobj.setAccType(ac_type); 
					trnobj.setAccNo(String.valueOf(ac_no)); 
					trnobj.setTrnSeq(trn);
					trnobj.setTrnType("D");
					if(loan_object.getPayMode().equals("C"))
						trnobj.setRefNo(voucher_number);
					if(loan_object.getPayMode().equals("T"))
						trnobj.setRefNo(0);
					if(loan_object.getPayMode().equals("P"))
						trnobj.setRefNo(ps_no); // Pending....
					trnobj.setVtml(tml);
					trnobj.setVid(uid);
					trnobj.setVDate(date_time);
					cremote.storeGLTransaction(trnobj);
					System.out.println("After storing loan gl");
				}
				
				if(loan_object.getPayMode().equals("C"))
				{
					if(trnobj!=null)
						trnobj=null;
					ResultSet rs_credit=stmt.executeQuery("select gl_code,gl_type from GLKeyParam gk where ac_type='1019001'"); // Code added by Murugesh on 12/1/2005
					trnobj=new GLTransObject();
					if(rs_credit.next()){
						trnobj.setGLType(rs_credit.getString("gl_type"));
						trnobj.setGLCode(rs_credit.getString("gl_code"));
						trnobj.setAmount(disbursement);
					} else {
						trnobj.setGLType(null);
						trnobj.setGLCode(null);
						trnobj.setAmount(disbursement);
					}
					
					{
						trnobj.setTrnDate(loan_trn_object.getTransactionDate());
						trnobj.setTrnMode(loan_object.getPayMode());
						trnobj.setCdind("C");
						trnobj.setAccType(ac_type);
						trnobj.setAccNo(String.valueOf(ac_no));
						trnobj.setTrnSeq(trn);
						trnobj.setTrnType("R");
						trnobj.setRefNo(voucher_number); 
						trnobj.setVtml(tml);
						trnobj.setVid(uid);
						trnobj.setVDate(date_time);
						cremote.storeGLTransaction(trnobj);
					}
				}
				if(loan_object.getPayMode().equals("P"))
				{
					int custype=0;
					
					Statement stmt_cust_info=conn.createStatement();
					ResultSet rs_cust_info=stmt_cust_info.executeQuery("Select custtype from CustomerMaster cm,LoanMaster lm where lm.ac_type='"+loan_object.getAccType()+"' and lm.ac_no="+loan_object.getAccNo()+" and lm.cid=cm.cid");
					if(rs_cust_info!=null)
						if(rs_cust_info.next())
							custype= rs_cust_info.getInt("custtype");
						
					System.out.println("+++++++++++++++++++++++++++++++++ 2 +++++++++++++++++++++++++");	
					PreparedStatement ps_payorder=conn.prepareStatement("insert into PayOrderMake values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					
					ps_payorder.setString(1,"X");
					ps_payorder.setString(2,String.valueOf(custype));
					ps_payorder.setInt(3,ps_no);
					ps_payorder.setString(4,loan_trn_object.getTransactionDate());
					ps_payorder.setString(5,loan_object.getName()); // pending...
					ps_payorder.setString(6,loan_object.getAccType());
					ps_payorder.setInt(7,loan_object.getAccNo());
					ps_payorder.setString(8,null);
					
					Statement stmt_gl=conn.createStatement();
					ResultSet rs_gl=stmt_gl.executeQuery("select gk.gl_code,gk.gl_type,gm.gl_name from GLKeyParam gk,GLMaster gm where ac_type='"+loan_object.getAccType()+"' and code=1 and gk.gl_code=gm.gl_code and gk.gl_type=gm.gl_type");
					if(rs_gl!=null)
					{
						if(rs_gl.next())
						{
							ps_payorder.setString(9,rs_gl.getString("gk.gl_type"));
							ps_payorder.setString(10,rs_gl.getString("gk.gl_code"));
							ps_payorder.setString(11,rs_gl.getString("gm.gl_name"));
						}
					}else{
						ps_payorder.setString(9,null);
						ps_payorder.setString(10,null);
						ps_payorder.setString(11,null);
					}
					
					ps_payorder.setDouble(12,disbursement);
					ps_payorder.setString(13,"F");
					
					System.out.println("+++++++++++++++++++++++++++++++++ 3 +++++++++++++++++++++++++");
					
					/*Statement stmt_po_commission=conn.createStatement();
					 ResultSet rs_po_commission=stmt_po_commission.executeQuery("select commission_rate from PayOrderCommission where ac_type='Customer' ");
					 if(rs_po_commission!=null)
					 {
					 if(rs_po_commission.next())
					 ps_payorder.setDouble(14,rs_po_commission.getDouble("commission_rate"));
					 }else ps_payorder.setDouble(14,0);*/ // Code changed by Murugesh on 19/1/2006
					
					ps_payorder.setDouble(14,0); // Code added by Murugesh on 19/1/2006
					
					System.out.println("+++++++++++++++++++++++++++++++++ 4 +++++++++++++++++++++++++");
					ps_payorder.setString(15,loan_object.uv.getUserTml());
					ps_payorder.setString(16,loan_object.uv.getUserId());
					ps_payorder.setString(17,loan_object.uv.getUserDate());
					ps_payorder.setString(18,tml);
					ps_payorder.setString(19,uid);
					ps_payorder.setString(20,date_time);
					ps_payorder.executeUpdate();
					
					/*if(ps_payorder.execute())
					 {
					 if(ps_payorder.getUpdateCount()==0)
					 throw new SQLException();
					 }*/
					trnobj=null;
					trnobj=new GLTransObject();
					
					Statement stmt_gl_po=conn.createStatement();
					ResultSet rs_gl_po=stmt_gl_po.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1016001' and code=1");
					
					trnobj.setTrnDate(loan_trn_object.getTransactionDate());
					
					System.out.println("+++++++++++++++++++++++++++++++++ 5 +++++++++++++++++++++++++");
					
					if(rs_gl_po.next()){
						trnobj.setGLType(rs_gl_po.getString("gl_type"));
						trnobj.setGLCode(rs_gl_po.getString("gl_code"));
					} else {
						trnobj.setGLType(null);
						trnobj.setGLCode(null);
					}
					
					trnobj.setAmount(disbursement);
					trnobj.setCdind("C");
					trnobj.setTrnMode("T");
					trnobj.setAccType("1016001");  
					trnobj.setAccNo(String.valueOf(ps_no)); 
					trnobj.setTrnSeq(trn);
					trnobj.setTrnType("R");
					trnobj.setRefNo(ps_no);
					trnobj.setVid(uid);
					trnobj.setVtml(tml);
					trnobj.setVDate(date_time);
					
					cremote.storeGLTransaction(trnobj);
					System.out.println("+++++++++++++++++++++++++++++++++ 6 +++++++++++++++++++++++++");
				}
			}
			
			
			if(loan_object.getPayMode().equals("C") && voucher_number>0)
			{
				stmt.executeUpdate("update LoanTransaction set ref_no="+voucher_number+",trn_narr='Csh Vch "+voucher_number+"' where ac_type="+ac_type+" and ac_no="+ac_no+" and trn_seq="+trn+" and trn_type='D'");
				return(voucher_number);
			}
			
			if(loan_object.getPayMode().equals("P") && ps_no>0)
			{
				stmt.executeUpdate("update LoanTransaction set ref_no="+ps_no+",trn_narr='Pay No "+ps_no+"' where ac_type="+ac_type+" and ac_no="+ac_no+" and trn_seq="+trn+" and trn_type='D'");
				System.out.println("+++++++++++++++++++++++++++++++++ 7 +++++++++++++++++++++++++");
				return(ps_no);
			}
			
			return(a);
		}catch(Exception exception){exception.printStackTrace();sessionContext.setRollbackOnly();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return 0;
	}
	
	
	//Store loan master
	//public int storeLoanMaster(LoanMasterObject ln,int type,String clientdatetime) 
	
	public int storeLoanMaster(LoanMasterObject ln,int type,String clientdatetime) 
	{
		//file_logger.info("****************************************************************************************************");
		//file_logger.info("***********storemaster*********");
		System.out.println("***********storemaster*********");  
		
		Connection conn=null;
		boolean[] submited_details;
        int result=0;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			
			int no=0;
			//If account number is 0 means new account 
			
            if(ln.getAccNo()==0 && type==0)
            {
                //file_logger.info("inside getAccNo is 0"); 
                //Select last_acc_no from modules then generate account number for user
                ResultSet rs=stmt.executeQuery("select lst_acc_no from Modules where modulecode = '1010000'");
                if(rs.next())
                    no=rs.getInt(1);
                ln.setAccNo(no+1);
                //file_logger.info("+++++++++++check point for store update last ac number+++++++++++");
               
            }
			
			int res=0;
			
			//if type 0 means new account
			if(type==0)
			{
				//If account number is given means then updating before verification
				//then delete records insert again
				
				if(ln.getAccNo()>0)
				{
					stmt.executeUpdate("delete from LoanMaster where ac_no="+ln.getAccNo()+" and ac_type='"+ln.getModuleCode()+"'");
					stmt.executeUpdate("delete from BorrowerMaster where ac_no="+ln.getAccNo()+" and ac_type='"+ln.getModuleCode()+"'");
					stmt.executeUpdate("delete from RelativeMaster where ac_no="+ln.getAccNo()+" and ac_type='"+ln.getModuleCode()+"'");
					stmt.executeUpdate("delete from EmploymentMaster where ac_no="+ln.getAccNo()+" and ac_type='"+ln.getModuleCode()+"'");
					stmt.executeUpdate("delete from PropertyMaster where ac_no="+ln.getAccNo()+" and ac_type='"+ln.getModuleCode()+"'");
					stmt.executeUpdate("delete from LoanTransaction where ac_no="+ln.getAccNo()+" and ac_type='"+ln.getModuleCode()+"'");
					stmt.executeUpdate("delete from GoldDetMaster where ac_no="+ln.getAccNo()+" and ac_type='"+ln.getModuleCode()+"'");
					stmt.executeUpdate("delete from VehicleMaster where ac_no="+ln.getAccNo()+" and ac_type='"+ln.getModuleCode()+"'");
					// code added by Murugesh on 15/12/2005
					stmt.executeUpdate("delete from SignatureInstruction where ac_no="+ln.getAccNo()+" and ac_type='"+ln.getModuleCode()+"'");
					//
				}
				
				//Insert into loan master
				//file_logger.info("before insert into loanmaster"); 
				PreparedStatement pstmt=conn.prepareStatement("insert into LoanMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmt.setString(1,String.valueOf(ln.getModuleCode()));//lnactype
				pstmt.setInt(2,ln.getAccNo());//lnacno
				pstmt.setInt(3,ln.getNoOfCoBorrowers());//no jointholders			
				pstmt.setInt(4,ln.getNoOfSurities());
				pstmt.setInt(5,ln.getCustomerId());
				pstmt.setInt(6,ln.getMailingAddress());
				//file_logger.info("1............");
				pstmt.setInt(7,ln.getApplicationSrlNo());//ln appn srl number
				pstmt.setString(8,ln.getApplicationDate());//ln appn date
				pstmt.setDouble(9,ln.getRequiredAmount());//req amt
				pstmt.setInt(10,ln.getShareAccNo());//lnee lf no		
				pstmt.setString(11,ln.getShareAccType());//lnee lf ac type
				
				pstmt.setInt(12,ln.getPurposeCode());//purpose code
				pstmt.setString(13,null);//nom no
				pstmt.setString(14,ln.getDepositAccType());//td actype
				pstmt.setInt(15,ln.getDepositAccNo());//td acno		
				pstmt.setInt(16,ln.getInterestType());
				pstmt.setInt(17,ln.getInterestRateType());
				pstmt.setDouble(18,ln.getInterestRate());
				pstmt.setInt(19,ln.getPrior());//psect _cd
				pstmt.setString(20,"N");//weeker section
				pstmt.setString(21,String.valueOf(ln.getSexInd()));//sex
				pstmt.setString(22,ln.getRelative());//relation // Code added by Murugesh on 18/11/2005
				pstmt.setString(23,String.valueOf(ln.getDirectorCode()));//dir code	 // Code added by Murugesh on 18/11/2005
				pstmt.setString(24,null);//conv dt
				//file_logger.info("1............");
				pstmt.setString(25,null);//holdiday mth		
				pstmt.setString(26,ln.getSanctionDate());//sanc dt // Code added by Murugesh on 18/11/2005
				pstmt.setDouble(27,ln.getSanctionedAmount());//sanc amt
				pstmt.setString(28,"N");//loan sanctioned
				
				
				pstmt.setString(29,"N");// sanc verified		
				pstmt.setString(30,null);//no insta
				pstmt.setString(31,null);//insta amt
				
				pstmt.setString(32,null);//int uptodt
				pstmt.setString(33,null);//lst trn dt
				pstmt.setString(34,null);//lst trn seq		
				pstmt.setString(35,"N");//default ind
				pstmt.setString(36,null);//close dt
				
				pstmt.setString(37,ln.getPayMode());//paymode
				pstmt.setString(38,ln.getPaymentAcctype());//pay actype
				pstmt.setInt(39,ln.getPaymentAccno());//pay acno
				
				pstmt.setString(40,null);//remd no
				pstmt.setString(41,null);//remd dt
				pstmt.setString(42,null);//disab left
				pstmt.setString(43,null);//ldgprtdt
				pstmt.setString(44,null);//npa
				pstmt.setString(45,null);//npastg
				
				pstmt.setString(46,ln.uv.getUserTml());//de tml
				pstmt.setString(47,ln.uv.getUserId());//de user
				pstmt.setString(48,ln.uv.getUserDate());//de date time
				pstmt.setString(49,null);//ve tml
				pstmt.setString(50,null);//ve user
				pstmt.setString(51,null);//ve date time
				//file_logger.info("+++++++++++++++++++check point for store LoanMaster2++++++++++++++++++");
				res=pstmt.executeUpdate();
				System.out.println("the value os result is" + res );
				submited_details = ln.getSubmitedDetails(); // Code added by Murugesh on 19/12/2005
				System.out.println("The submitted details is----------->"+submited_details);
				//CoBorrowers and Surities Details			 
				
				if(res==1)
				{	
					if(ln.getNoOfCoBorrowers()>0 || ln.getNoOfSurities()>0)
					{
						//file_logger.info("the number of coborrowers "+ln.getNoOfCoBorrowers());
						//file_logger.info("the number of surities "+ln.getNoOfSurities());
						if(ln.getNoOfCoBorrowers()>0)
						{
							pstmt=conn.prepareStatement("insert into BorrowerMaster values(?,?,?,?,?,?)");
							//getCoBorrowers return vector which will contain coborrower details
							Vector  borrowers=null;
							borrowers=ln.getCoBorrowers();
							String borrower_detail=null;
							String sh_type=null;
							//-----03/10/2011-----
							SurityCoBorrowerObject borrowerObject=null;
							int sh_no=0;
							for(int i=0;i<borrowers.size();i++)
							{
								/*borrower_detail = borrowers.get(i).toString();
								sh_type = borrower_detail.substring(0,7);
								sh_no = Integer.parseInt(borrower_detail.substring(13,borrower_detail.length()));*/
								borrowerObject=(SurityCoBorrowerObject)borrowers.get(i);
								sh_type=borrowerObject.getAc_type();
								sh_no=Integer.parseInt(borrowerObject.getAc_no());
								pstmt.setString(1,String.valueOf(ln.getModuleCode()));
								pstmt.setInt(2,ln.getAccNo());
								/*pstmt.setString(3,"C");*/
								/*pstmt.setString(4,sh_type);
								pstmt.setInt(5,sh_no);*/
								pstmt.setString(3, borrowerObject.getType());
								pstmt.setString(4, sh_type);
								pstmt.setString(5, String.valueOf(sh_no));
								
								ResultSet rs1=stmt.executeQuery("select br_code from ShareMaster where ac_type='"+sh_type+"' and ac_no="+sh_no);
								if(rs1.next())
									pstmt.setString(6,String.valueOf(rs1.getInt(1)));
								else
									pstmt.setString(6,null);
								pstmt.addBatch();	
							}
							//file_logger.info("+++++++++++++++++++++check point for Borrwer 3+++++++++++++++++++++");
							pstmt.executeBatch();
						}
						if(ln.getNoOfSurities()>0)
						{
							pstmt=conn.prepareStatement("insert into BorrowerMaster values(?,?,?,?,?,?)");
							//getSurities return vector which will contain coborrower details
							Vector  surities=null;
							surities=ln.getSurities();
							String surities_detail=null;
							String sh_type=null;
							SurityCoBorrowerObject borrowerObject=null;
							int sh_no=0;
							for(int i=0;i<surities.size();i++)
							{
								/*surities_detail = surities.get(i).toString();
								sh_type = surities_detail.substring(0,7);
								sh_no = Integer.parseInt(surities_detail.substring(13,surities_detail.length()));*/
								pstmt.setString(1,String.valueOf(ln.getModuleCode()));
								pstmt.setInt(2,ln.getAccNo());
								/*pstmt.setString(3,"S");
								pstmt.setString(4,sh_type);
								pstmt.setInt(5,sh_no);*/
								borrowerObject=(SurityCoBorrowerObject)surities.get(i);
								sh_type=borrowerObject.getAc_type();
								sh_no=Integer.parseInt(borrowerObject.getAc_no());
								pstmt.setString(3, borrowerObject.getType());
								pstmt.setString(4, sh_type);
								pstmt.setString(5, String.valueOf(sh_no));
								
								ResultSet rs1=stmt.executeQuery("select br_code from ShareMaster where ac_type='"+sh_type+"' and ac_no="+sh_no);
								if(rs1.next())
									pstmt.setString(6,String.valueOf(rs1.getInt(1)));
								else
									pstmt.setString(6,null);
								pstmt.addBatch();	
							}
							pstmt.executeBatch();
						}
						//file_logger.info("+++++++++++++++++++++check point for Surities 3+++++++++++++++++++++");
						
					}
					//
					
					// Signature Details //Code added by Murugesh on 15/12/2005
                   try{
                	   if(ln.getSignatureDet()!=null && ln.getAccNo()!=0&&Integer.toString(ln.getAccNo())!=null)
                        commonlocal.storeSignatureDetails(ln.getSignatureDet(),ln.getAccNo());
                   }catch(Exception e){
                       throw new SignatureNotInsertedException();
                       } 
					//
					
					//Dependents Details	
					if(submited_details[1]) // Code added by Murugesh on 19/12/2005
					{
						System.out.println("sfd");
						System.out.println("Relative details ----------- >");
						System.out.println("The length is ---------->"+ln.getRelatives().length);
						Object relObj[][] = ln.getRelatives();
						System.out.println("0th row ------->"+relObj[0][0]);
						System.out.println("0th row ------->"+relObj[0][1]);
						if(ln.getDependents().length>0 || ln.getInterests().length>0 || ln.getRelatives().length>0)
						{
							System.out.println("inside the Main Loop----->");
							
							pstmt=conn.prepareStatement("insert into RelativeMaster values(?,?,?,?,?,?,?,?,?)");		
							if(ln.getRelatives().length>0)
							{
								System.out.println("Inside the Loop----->");
								//Get relative details and insert
								Object obj[][]=ln.getRelatives();
								
								System.out.println("Values are 1 name ----->"+obj[0][0]);
								System.out.println("Values are 2 age ----->"+obj[0][1]);
								System.out.println("Values are 3 Type----->"+obj[0][2]);
								System.out.println("Values are 4 Sex----->"+obj[0][3]);
								System.out.println("Values are 5 Martial----->"+obj[0][4]);
								System.out.println("Values are 6 Status ----->"+obj[0][5]);
								System.out.println("Length is ------->"+obj.length);
								for(int i=0;i<=0;i++)
								{
									System.out.println("The values is 1 name------------"+obj[i][0].toString());
									System.out.println("The values is 2 age------------"+obj[i][1].toString());
									System.out.println("The values is 3 Type------------"+obj[i][2]);
									System.out.println("The values is 4 Sex------------"+obj[i][3]);
									System.out.println("The values is 5 Martial------------"+obj[i][4]);
									System.out.println("The values is 5 Martial------------"+obj[i][5]);
									System.out.println("String.valueOf(ln.getModuleCode()"+String.valueOf(ln.getModuleCode()));
									System.out.println("Acc No is ---------->"+ln.getAccNo());
									pstmt.setString(1,String.valueOf(ln.getModuleCode()));
									pstmt.setInt(2,ln.getAccNo());
									pstmt.setString(3,"Relatives");
									pstmt.setString(4,obj[i][0].toString()); //name
									pstmt.setString(5,obj[i][1].toString()); //age
									pstmt.setString(6,obj[i][3].toString()); //sex
									pstmt.setString(7,obj[i][4].toString()); //maritial
									pstmt.setString(8,obj[i][5].toString()); //status
									pstmt.setString(9,obj[i][2].toString()); //type
									pstmt.addBatch();				
								}
							}
							if(ln.getDependents().length>0)
							{
								//Get dependents details and insert
								Object obj[][]=ln.getDependents();
								for(int i=0;i<=0;i++)
								{
									pstmt.setString(1,String.valueOf(ln.getModuleCode()));
									pstmt.setInt(2,ln.getAccNo());
									pstmt.setString(3,"Dependents");
									pstmt.setString(4,obj[i][0].toString());
									pstmt.setString(5,obj[i][1].toString());
									pstmt.setString(6,obj[i][2].toString());
									pstmt.setString(7,null);
									pstmt.setString(8,null);
									pstmt.setString(9,obj[i][3].toString());
									pstmt.addBatch();				
								}
							}
							if(ln.getInterests().length>0)
							{
								//Get the interests(who and all having interest in loanees property) details
								Object obj[][]=ln.getInterests();
								for(int i=0;i<=0;i++)
								{
									pstmt.setString(1,String.valueOf(ln.getModuleCode()));
									pstmt.setInt(2,ln.getAccNo());
									pstmt.setString(3,"Interests");
									pstmt.setString(4,obj[i][0].toString());
									pstmt.setString(5,obj[i][1].toString());
									pstmt.setString(6,obj[i][2].toString());
									pstmt.setString(7,obj[i][3].toString());
									pstmt.setString(8,obj[i][4].toString());
									pstmt.setString(9,null);
									pstmt.addBatch();				
								}
							}
							//file_logger.info("++++++++++++++++check point for RelativeMaster 4+++++++++++++++++");
							pstmt.executeBatch();
						}
					}
					
					//Income Details
					if(submited_details[2])
					{
						if(ln.getIncomeDetails().length>0)
						{
							pstmt=conn.prepareStatement("insert into EmploymentMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,null,null,null,?,?)");		
							IncomeObject in[]=ln.getIncomeDetails();
							for(int i=0;i<ln.getIncomeDetails().length;i++)	
							{
								if(in[i]!=null)	
								{
									pstmt.setInt(1,ln.getAccNo());				
									pstmt.setString(2,String.valueOf(ln.getModuleCode()));
									pstmt.setString(3,in[i].getTypeOfIncome());
									pstmt.setString(4,null);
									pstmt.setString(5,null);
									pstmt.setString(6,null);
									//	pstmt.setString(6,in[i].getAddress());
									pstmt.setString(7,in[i].getAddress());
									//pstmt.setInt(7,in[i].getPhNo());
									//file_logger.info("********************* the phone number is "+in[i].getStringPhNo());
									pstmt.setString(8,in[i].getStringPhNo());				
									pstmt.setString(9,null);				
									pstmt.setString(10,null);
									/*pstmt.setInt(11,in[i].getService());
									 pstmt.setString(12,null);			*/
									pstmt.setString(11,null);
									pstmt.setInt(12,in[i].getService());			
									pstmt.setString(13,null);				
									pstmt.setString(14,null);
									//pstmt.setDouble(15,in[i].getIncome());
									pstmt.setDouble(15,0);
									pstmt.setDouble(16,in[i].getIncome());					
									pstmt.setString(17,null);				
									pstmt.setString(18,null);				
									pstmt.setInt(19,0);
									pstmt.setString(20,null);
									pstmt.setString(21,null);
									pstmt.setString(22,null);
									if(i==0){	
										//pstmt.setString(4,in[i].getName());
										pstmt.setString(5,in[i].getName());
										pstmt.setDouble(17,in[i].getExpenditure());
										pstmt.setDouble(22,in[i].getNetIncome());
									}
									if(i==1)
									{					   
										//pstmt.setString(4,in[i].getName());
										pstmt.setString(6,in[i].getName());
										pstmt.setString(8, String.valueOf(in[i].getPhNo()));
										/*pstmt.setString(8,in[i].getEmpNo());			
										 pstmt.setString(9,in[i].getDesignation());				
										 pstmt.setString(10,in[i].getDept());*/
										pstmt.setString(9,in[i].getEmpNo());			
										pstmt.setString(10,in[i].getDesignation());				
										pstmt.setString(11,in[i].getDept());
										
										/*pstmt.setString(12,(in[i].isConfirmed()?"T":"F"));				
										 pstmt.setString(13,(in[i].isTransferable()?"T":"F"));				
										 pstmt.setString(14,(in[i].isCertificateEnclosed()?"T":"F"));*/
										
										pstmt.setString(13,(in[i].isConfirmed()?"T":"F"));				
										pstmt.setString(14,(in[i].isTransferable()?"T":"F"));				
										pstmt.setString(15,(in[i].isCertificateEnclosed()?"T":"F"));
										pstmt.setDouble(17,in[i].getExpenditure());
										// Code added by Murugesh on 20/12/2005
										pstmt.setDouble(22,in[i].getNetIncome());
										//
									}	
									if(i==2)
									{
										pstmt.setString(4,in[i].getName());
										pstmt.setString(5,in[i].getNature());
										pstmt.setString(8, String.valueOf(in[i].getPhNo()));
										//	pstmt.setDouble(16,in[i].getExpenditure());
										// Code added by Murugesh on 20/12/2005
										pstmt.setDouble(17,in[i].getExpenditure());
										pstmt.setDouble(21,in[i].getTurnOver());
										pstmt.setDouble(22,in[i].getSurplus());
										//
									}
									if(i==3)
									{
										pstmt.setString(6,in[i].getName());	
										pstmt.setString(8, String.valueOf(in[i].getPhNo()));
										//pstmt.setString(17,in[i].getBankName());				
										//pstmt.setString(18,in[i].getAccType());				
										//pstmt.setInt(19,Integer.parseInt(in[i].getAccNo()));						
										pstmt.setString(18,in[i].getBankName());				
										pstmt.setString(19,in[i].getAccType());				
										pstmt.setInt(20,Integer.parseInt(in[i].getAccNo()));
										// Code added by Murugesh on 20/12/2005
										pstmt.setDouble(22,in[i].getNetIncome());
										//
									}
									if(i==4)
									{
										pstmt.setDouble(17,in[i].getExpenditure());	
										// Code added by Murugesh on 20/12/2005
										pstmt.setDouble(22,in[i].getNetIncome());
										//
									}
									
									pstmt.addBatch();	
								}
							}	
							//file_logger.info("++++++++++++++++check point for EmployementMaster 4+++++++++++++++++");
							pstmt.executeBatch();
						}
					}
					//Property Details
					if(ln.getPropertyDetails()!=null)
					{
						PropertyObject po=ln.getPropertyDetails();
						pstmt=conn.prepareStatement("insert into PropertyMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");		
						Object data[][]=po.getTenantsDetail();
						for(int i=0;i<((data!=null)?data.length:1);i++)
						{
							pstmt.setInt(1,ln.getAccNo());				
							pstmt.setString(2,String.valueOf(ln.getModuleCode()));
							pstmt.setString(3,po.getPropertyNo());
							pstmt.setString(4,po.getAddress());
							pstmt.setString(5,po.getMeasurementEW());
							pstmt.setString(6,po.getMeasurementNS());
							pstmt.setString(7,po.getEastBy());
							pstmt.setString(8,po.getWestBy());
							pstmt.setString(9,po.getNorthBy());
							pstmt.setString(10,po.getSouthBy());
							pstmt.setDouble(11,po.getPropertyValue());
							pstmt.setString(12,po.getPropertyAqdBy());
							pstmt.setString(13,po.getPropertyNature());
							
							if(data!=null)
							{
								pstmt.setString(14,data[i][0].toString());
								pstmt.setDouble(15,Double.parseDouble(data[i][1].toString()));
								pstmt.setString(16,data[i][2].toString());
							}
							else
							{
								pstmt.setString(14,null);
								pstmt.setDouble(15,0);
								pstmt.setString(16,null);
							}
							pstmt.addBatch();
						}
						//file_logger.info("++++++++++++++++check point for PropertyMaster 4+++++++++++++++++");
						pstmt.executeBatch();
					}	
					
					//file_logger.info(" gold details check"+ln.getGoldDet());
					
					if(ln.getGoldDet()!=null)
					{
						//file_logger.info("inside if");
						GoldObject gobj=ln.getGoldDet();
						pstmt=conn.prepareStatement("insert into GoldDetMaster values(?,?,?,?,?,?,?,?,?,?)");		
						Object data[][]=gobj.getGoldDet();
						
						for(int i=0;i<((data!=null)?data.length:1);i++)
						{
							///file_logger.info("inside for");
							
							pstmt.setInt(1,ln.getAccNo());				
							pstmt.setString(2,String.valueOf(ln.getModuleCode()));
							pstmt.setInt(3,gobj.getApprisersCode());
							//file_logger.info("to table data");
							/*	if(data!=null)
							 {
							 pstmt.setString(4,data[i][1].toString());
							 pstmt.setInt(5,Integer.parseInt(data[i][2].toString()));
							 pstmt.setInt(6,Integer.parseInt(data[i][3].toString()));
							 pstmt.setDouble(7,Double.parseDouble(data[i][4].toString()));
							 pstmt.setInt(8,Integer.parseInt(data[i][5].toString()));
							 }*/
							// New Code Added by Murugesh 18/11/2005
							if(data!=null)
							{
								pstmt.setInt(4,Integer.parseInt(data[i][0].toString()));
								//file_logger.info("column 4"+data[i][0].toString());
								pstmt.setString(5,data[i][1].toString());
								//file_logger.info("column 5"+data[i][1].toString());
								pstmt.setDouble(6,Double.parseDouble(data[i][2].toString()));
								//file_logger.info("column 6"+data[i][2].toString());
								pstmt.setDouble(7,Double.parseDouble(data[i][3].toString()));
								//file_logger.info("column 7"+data[i][3].toString());
								pstmt.setDouble(8,Double.parseDouble(data[i][5].toString()));
								//file_logger.info("column 8"+data[i][5].toString());
							}
							else
							{
								pstmt.setInt(4,0);
								pstmt.setString(5,null);
								pstmt.setInt(6,0);
								pstmt.setInt(7,0);
								pstmt.setDouble(8,0.0);
							}
							pstmt.setString(9,gobj.getApprisalDate());
							pstmt.setString(10,gobj.getApprisalTime());
							
							pstmt.addBatch();
						}
						//file_logger.info("++++++++++++++++check point for GoldMaster 4+++++++++++++++++");
						int array_gold[]=pstmt.executeBatch();
						//file_logger.info("length if batch in gold"+array_gold.length);
						
						
					}
					if(ln.getVehicleDet()!=null)
					{	
						//file_logger.info("before insert into VehicleMaster"); 
						masterObject.general.VehicleObject vobj=ln.getVehicleDet();
						pstmt=conn.prepareStatement("insert into VehicleMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");		
						pstmt.setInt(2,ln.getAccNo());				
						pstmt.setString(1,String.valueOf(ln.getModuleCode()));
						pstmt.setString(3,vobj.getVehicleMake());
						pstmt.setDouble(4,vobj.getVehicleCost());
						pstmt.setString(5,vobj.getAddressDealer());
						pstmt.setString(6,vobj.getLicenceNo());
						pstmt.setString(7,vobj.getLicenceValidity());
						pstmt.setString(8,vobj.getVehicleType());
						pstmt.setString(9,vobj.getPermitNo());	
						pstmt.setString(10,vobj.getPermitValidity());
						pstmt.setString(11,vobj.getVehicleFor());
						pstmt.setString(12,vobj.getArea());
						pstmt.setString(13,vobj.getAddressParking());
						pstmt.setString(14,vobj.getOtherDet());
						//file_logger.info("++++++++++++++++check point for VehicleMaster 4+++++++++++++++++");
						pstmt.executeUpdate();
                     
						
					}
				}
             
                
                
			}//if result greater than 0
             stmt.executeUpdate("update Modules set lst_acc_no=lst_acc_no+1 where modulecode = '1010000'");
		}
        catch(SignatureNotInsertedException sign){
        	sessionContext.setRollbackOnly();
        	ln.setAccNo(-2);
        	sign.printStackTrace();
        	return -2;
        	}
        catch(Exception exception){
        	sessionContext.setRollbackOnly(); 
        	ln.setAccNo(0);
        	exception.printStackTrace();
        	return 0;
        	}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){
				exception.printStackTrace();
				}
		}
		
		
       
           return(ln.getAccNo());
       
	}
	
	public int getCheakLoanNum(int ln_acno)
	{
		System.out.println("Acc No in Bean is ===="+ln_acno);
		Connection conn=null;
		int i = -1;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select *from LoanMaster where ac_no="+ln_acno+"");
			if(rs.next())
			{
				System.out.println("Value of i in Bean"+i);
				return i;
			}
			else
			{
				i=2;
				System.out.println("Value of i in Bean"+i);
				return i;
				
			}
		}
		catch(SQLException sql)
		{
 			sql.printStackTrace();
 		}
		finally
		{
			try
			{
 				conn.close();
 			}
			catch(SQLException sql)
			{
 				sql.printStackTrace();
 			}
 		}
		
		System.out.println("End of BL");
		return i;
	}
	public LoanMasterObject getLoanModuleDesc(String accType)
	{
		System.out.println("Inside the Bean Class ====== 1");
		Connection conn=null;
		LoanMasterObject lnobj=new LoanMasterObject();
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select moduledesc from Modules where modulecode='"+accType+"'");
			System.out.println("the result set is ======"+rs.next());
			System.out.println("The module desc is ======"+rs.getString(1));
			if(rs!=null){
			lnobj.setLoanMod(rs.getString(1));
			}else{
				lnobj.setLoanMod("");
			}
				
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return lnobj;
	}
	public LoanMasterObject getLoanNoDetails(int accNo)
	{
		System.out.println("Inside the Bean Class ====== 1");
		Connection conn=null;
		LoanMasterObject lnobj=new LoanMasterObject();
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select *from LoanMaster where ac_no="+accNo);
			if(rs.next())
			{
				System.out.println("The Ac Type is --"+rs.getString("ac_type"));
				lnobj.setAccType(rs.getString("ac_type"));
				System.out.println("The sh type is -----"+rs.getString("sh_type"));
				lnobj.setShareAccType(rs.getString("sh_type"));
				System.out.println("The sh No is -----"+rs.getString("sh_no"));
				lnobj.setShareAccNo(rs.getInt("sh_no"));
				System.out.println("The purpose is -----"+rs.getInt("pps_code"));
				lnobj.setPurposeCode(rs.getInt("pps_code"));
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return lnobj;
			
		
	}
	
	//Get loan master details for given account type and account number
	public LoanMasterObject getLoanMaster(int ln_acno,String ln_acty) 
	{
		LoanMasterObject lnobj=null;
		Object data[][]=null;
		Connection conn=null;
		try{
		
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select lm.* from LoanMaster lm where lm.ac_no="+ln_acno+" and lm.ac_type='"+ln_acty+"'");
			
			//Get all loan master details
			//int i=0;
			if(rs.next())
			{
				lnobj=new LoanMasterObject();
				lnobj.setModuleCode(Integer.parseInt(rs.getString("ac_type")));
				lnobj.setAccType(rs.getString("ac_type"));
				lnobj.setAccNo(rs.getInt("ac_no"));
				lnobj.setCustomerId(rs.getInt("cid"));
				lnobj.setMailingAddress(rs.getInt("addr_type"));
				lnobj.setApplicationSrlNo(rs.getInt("appn_srl"));
				lnobj.setApplicationDate(rs.getString("appn_date"));
				
				lnobj.setInterestType(rs.getInt("int_type"));
				lnobj.setInterestRate(rs.getDouble("int_rate"));
				lnobj.setInterestRateType(rs.getInt("int_rate_type"));
				if(lnobj.getInterestRateType()==1)
					lnobj.setInterestRate(rs.getDouble("int_rate"));
				
				lnobj.setRequiredAmount(rs.getDouble("req_amt"));
				lnobj.setSanctionedAmount(rs.getDouble("sanc_amt"));
				lnobj.setPurposeCode(rs.getInt("pps_code"));
				//lnobj.setPayMode(rs.getString("pay_mode"));
				if(rs.getString("pay_mode").equals("T"))
					lnobj.setPayMode("Transfer");	
				else if(rs.getString("pay_mode").equals("P"))
					lnobj.setPayMode("PayOrder");
				else
					lnobj.setPayMode("Cash");
				lnobj.setPaymentAccno(rs.getInt("pay_ac_no"));
				lnobj.setPaymentAcctype(rs.getString("pay_ac_type"));
				//Vinay
				lnobj.setRemd_no(rs.getInt("remd_no"));
				lnobj.setRemd_date(rs.getString("remd_date"));
				lnobj.setLdgPrntDate(rs.getString("ldgprt_date"));
				lnobj.setLastTrndt(rs.getString("lst_trn_date"));
				lnobj.setLastTrnSeq(rs.getInt("lst_tr_seq"));
				
				lnobj.setClosedt(rs.getString("close_date"));
				
				lnobj.setInterestUpto(rs.getString("int_upto_date"));
				
				lnobj.setLoanSanctioned(rs.getString("loan_sanc").equals("N")?false:true);
				//file_logger.info("=========================Loan Sanctioned"+rs.getString("loan_sanc"));
				//file_logger.info("=========================Loan Sanctioned"+lnobj.isLoanSanctioned());
				lnobj.setSanctionedAmount(rs.getDouble("sanc_amt"));
				lnobj.setNoOfInstallments(rs.getInt("no_inst"));
				lnobj.setInstallmentAmt(rs.getDouble("inst_amt"));
				lnobj.setWeakerSection(rs.getString("wk_sect").equals("N")?false:true);
				lnobj.setPrioritySector(rs.getString("psect_cd").equals("N")?false:true);
				System.out.println("Priority**************"+rs.getString("psect_cd"));
				//lnobj.setPrior(rs.getString(("psect_cd")));
				lnobj.setSanctionVerified(rs.getString("sanc_ver").equals("N")?false:true);
				lnobj.setDisbursementLeft(rs.getDouble("disb_left"));
				lnobj.setRelative(rs.getString("rel"));
				
				lnobj.setShareAccType(rs.getString("sh_type"));
				lnobj.setShareAccNo(rs.getInt("sh_no"));
				
				if(rs.getString("holday_mth")!=null)
					lnobj.setHolidayPeriod(rs.getInt("holday_mth"));
				lnobj.setDirectorCode(rs.getInt("dir_code"));
				lnobj.setNoOfSurities(rs.getInt("no_surities"));
				lnobj.setNoOfCoBorrowers(rs.getInt("no_coborrowers"));
				
				lnobj.uv.setUserId(rs.getString("de_user"));
				lnobj.uv.setUserDate(rs.getString("de_date"));
				lnobj.uv.setUserTml(rs.getString("de_tml"));
				lnobj.uv.setVerId(rs.getString("ve_user"));
				lnobj.uv.setVerDate(rs.getString("ve_date"));
				lnobj.uv.setVerTml(rs.getString("ve_tml"));
				System.out.println("============"+rs.getString("ve_tml"));
				lnobj.setDepositAccType(rs.getString("td_ac_type"));
				lnobj.setDepositAccNo(rs.getInt("td_ac_no"));
				System.out.println("============");
				System.out.println("============"+rs.getString("sex_cd"));
				
				
				
				if(rs.getString("sex_cd").equals("M"))
					lnobj.setSexInd('M');	
				else
					lnobj.setSexInd('F');
				
				System.out.println("After getting application details");
				
				
				//Select all surities details
				ResultSet rs1=stmt.executeQuery("select * from BorrowerMaster where ln_ac_no="+ln_acno+" and ln_ac_type='"+ln_acty+"' and type='S'");
				
				Vector c=null;
				if(rs1.next())
				{
					rs1.last();
					c=new Vector(rs1.getRow());
					rs1.beforeFirst();
				}
				else
					c=null;			
				
				while(rs1.next())	
					c.add(rs1.getString("ac_type")+" "+rs1.getInt("ac_no"));			
				lnobj.setSurities(c);
				
				//Select all coborrower details
				System.out.println("Account number"+ln_acno+"  "+ln_acty);
				rs1=stmt.executeQuery("select * from BorrowerMaster where ln_ac_no="+ln_acno+" and ln_ac_type='"+ln_acty+"' and type='C'");
				if(rs1.next())
				{
					System.out.println("Bo........................");
					rs1.last();
					c=new Vector(rs1.getRow());
					rs1.beforeFirst();
				}
				else
					c=null;
				
				while(rs1.next())
				{
					System.out.println("Co borrowers details inside");
					c.add(rs1.getString("ac_type")+" "+rs1.getInt("ac_no"));
				}
				lnobj.setCoBorrowers(c);
				
				System.out.println("After getting surity/borrower details");
				
				
				//Select relative master details
				int j=0;
				rs1=stmt.executeQuery("select * from RelativeMaster where ac_no="+ln_acno+" and ac_type='"+ln_acty+"' and reltype='Relatives'");		
				if(rs1.next())
				{
					rs1.last();
					data=new Object[rs1.getRow()][6];				
					rs1.beforeFirst();
					j=0;
				}
				else
					data=null;
				
				while(rs1.next())
				{
					data[j][0]=rs1.getString("name");	
					data[j][1]=rs1.getString("age"); 	
					data[j][2]=rs1.getString("relation");
					if(rs1.getString("sex")=="M"){
						data[j][3]="Male";
					}else{
						data[j][3]="Female";
					}
					if(rs1.getString("marital")=="U"){
						data[j][4]="UnMarried";
					}else{
						data[j][4]="Married";
					}
					if(rs1.getString("status")=="L"){
						data[j][5]="Late";
					}else{
						data[j][5]="Alive";
					}
					
					/*data[j][3]=rs1.getString("sex");	
					data[j][4]=rs1.getString("marital");	
					data[j][5]=rs1.getString("status");	*/
					j++;
				}
				lnobj.setRelatives(data);
				System.out.println("After getting relative details");
				
				rs1=stmt.executeQuery("select * from RelativeMaster where ac_no="+ln_acno+" and ac_type='"+ln_acty+"' and reltype='Interests'");		
				if(rs1.next())
				{
					rs1.last();
					data=new Object[rs1.getRow()][5];				
					rs1.beforeFirst();
					j=0;
				}
				else
					data=null;	
				while(rs1.next())
				{
					data[j][0]=rs1.getString("name");	
					data[j][1]=rs1.getString("age");
					if(rs1.getString("sex")=="M"){
						data[j][2]="Male";
					}else{
						data[j][2]="Female";
					}
					if(rs1.getString("marital")=="U"){
						data[j][3]="UnMarried";
					}else{
						data[j][3]="Married";
					}
					if(rs1.getString("status")=="L"){
						data[j][4]="Late";
					}else{
						data[j][4]="Alive";
					}
				/*	data[j][2]=rs1.getString("sex");	
					data[j][3]=rs1.getString("marital");	
					data[j][4]=rs1.getString("status");	*/
					j++;
				}
				lnobj.setInterests(data);
				System.out.println("After getting relative details");
				
				rs1=stmt.executeQuery("select * from RelativeMaster where ac_no="+ln_acno+" and ac_type='"+ln_acty+"' and reltype='Dependents'");		
				if(rs1.next())
				{
					rs1.last();
					data=new Object[rs1.getRow()][4];				
					rs1.beforeFirst();
					j=0;
				}
				else
					data=null;
				while(rs1.next())
				{
					data[j][0]=rs1.getString("name");	
					data[j][1]=rs1.getString("age");	
					if(rs1.getString("sex")=="M"){
						data[j][2]="Male";
					}else{
						data[j][2]="Female";
					}
					//data[j][2]=rs1.getString("sex");	
					data[j][3]=rs1.getString("relation");	
					j++;
				}
				lnobj.setDependents(data);
				
				System.out.println("After getting relative  details");
				
				//Select all property details
				rs1=stmt.executeQuery("select * from PropertyMaster where ac_no="+ln_acno+" and ac_type='"+ln_acty+"'");
				rs1.last();
				j=rs1.getRow();
				rs1.beforeFirst();
				PropertyObject prop=null;
				if(rs1.next())
				{
					prop=new PropertyObject();	
					prop.setPropertyNo(rs1.getString("property_no"));	
					prop.setAddress(rs1.getString("addr"));	
					prop.setMeasurementEW(rs1.getString("east_west"));	
					prop.setMeasurementNS(rs1.getString("north_south"));	
					prop.setEastBy(rs1.getString("east_by"));	
					prop.setWestBy(rs1.getString("west_by"));	
					prop.setNorthBy(rs1.getString("north_by"));	
					prop.setSouthBy(rs1.getString("south_by"));	
					prop.setPropertyValue(rs1.getDouble("market_value"));	
					prop.setPropertyAqdBy(rs1.getString("property_aqd"));	
					prop.setPropertyNature(rs1.getString("nature"));	
					if(rs1.getString("tenant_name")!=null)	
					{
						System.out.println("3.....");
						data=new Object[j][3];
						for(int k=0;k<j;k++)
						{
							data[k][0]=rs1.getString("tenant_name");	
							data[k][1]=rs1.getString("rent_mnth");	
							data[k][2]=rs1.getString("type");	
						}
						prop.setTenantsDetail(data);	
					}
					else
						prop.setTenantsDetail(null);	
					System.out.println("4.....");
					
				}
				else
					prop=null;
				
				lnobj.setPropertyDetails(prop);
				
				System.out.println("After getting property details");
				
				
				//Select all employement details
				rs1=stmt.executeQuery("select * from EmploymentMaster where ac_no="+ln_acno+" and ac_type='"+ln_acty+"'");
				IncomeObject in[]=null;
				if(rs1.next())
				{
					rs1.last();
					in=new IncomeObject[rs1.getRow()];
					rs1.beforeFirst();
					j=0;
				}
				else 
					in=null;
				
				while(rs1.next())
				{
					in[j]=new IncomeObject();
					in[j].setTypeOfIncome(rs1.getString("emp_type"));				
					in[j].setAddress(rs1.getString("addr"));				
					in[j].setPhNo(rs1.getString("ph_no"));					
					in[j].setService(rs1.getInt("serv_length"));				
					in[j].setIncome(rs1.getDouble("amt_mnth"));				
					if(rs1.getString("emp_type").equals("Self")){	
						in[j].setName(rs1.getString("nature_of_emp"));
						in[j].setExpenditure(rs1.getDouble("tax_exp_mnth"));	
						in[j].setNetIncome(rs1.getDouble("net_month_income"));
					}
					else if(rs1.getString("emp_type").equals("Service"))		
					{
						in[j].setName(rs1.getString("employer_name"));	
						in[j].setPhNo(rs1.getInt("ph_no"));
						in[j].setEmpNo(rs1.getString("emp_no"));			
						in[j].setDesignation(rs1.getString("designation"));				
						in[j].setDept(rs1.getString("department"));			
						in[j].setConfirmed(rs1.getString("emp_confirmed").equals("T")?true:false);				
						in[j].setTransferable(rs1.getString("serv_trans").equals("T")?true:false);				
						in[j].setCertificateEnclosed(rs1.getString("sal_cert_enclosed").equals("T")?true:false);
						in[j].setExpenditure(rs1.getDouble("tax_exp_mnth"));
						//						 Code added by Murugesh on 20/12/2005
						in[j].setNetIncome(rs1.getDouble("net_month_income"));
						//
					}
					else if(rs1.getString("emp_type").equals("Business"))		
					{
						in[j].setName(rs1.getString("concern_name"));			
						in[j].setExpenditure(rs1.getDouble("tax_exp_mnth"));	
						in[j].setPhNo(rs1.getInt("ph_no"));
						// Code added by Murugesh on 20/12/2005
						in[j].setNature(rs1.getString("nature_of_emp"));
						in[j].setTurnOver(rs1.getDouble("avg_turnover_mnth"));
						in[j].setSurplus(rs1.getDouble("net_month_income"));
						//
					}
					else if(rs1.getString("emp_type").equals("Pension"))		
					{
						in[j].setName(rs1.getString("employer_name"));			
						in[j].setBankName(rs1.getString("bank_name"));				
						in[j].setAccType(rs1.getString("bank_ac_type"));				
						in[j].setAccNo(String.valueOf(rs1.getInt("bank_ac_no")));
						in[j].setPhNo(rs1.getInt("ph_no"));
						// Code added by Murugesh on 20/12/2005
						in[j].setNetIncome(rs1.getDouble("net_month_income"));
						//
					} 
					else if(rs1.getString("emp_type").equals("Rent"))		
					{
						in[j].setExpenditure(rs1.getDouble("tax_exp_mnth"));	
						// Code added by Murugesh on 20/12/2005
						in[j].setNetIncome(rs1.getDouble("net_month_income"));
						//
					}
					j++;
				}
				
				lnobj.setIncomeDetails(in);
				
				
				System.out.println("After getting property details");
				
				//Select all gold details submited by user
				rs1=stmt.executeQuery("select * from GoldDetMaster where ac_no="+ln_acno+" and ac_type='"+ln_acty+"'");
				GoldObject gobj=null;
				Object dataobj[][]=null;
				j=0;
				if(rs1.next())
				{
					rs1.last();
					gobj=new GoldObject();
					dataobj=new Object[rs1.getRow()][7];
					rs1.beforeFirst();
				}
				else 
					gobj=null;
				
				while(rs1.next())
				{
					
					gobj.setApprisersCode(Integer.parseInt(rs1.getString("appcode")));				
					/*dataobj[j][0]=String.valueOf(j+1);
					 dataobj[j][1]=rs1.getString("descr");
					 dataobj[j][2]=rs1.getString("grwgt");
					 dataobj[j][3]=rs1.getString("netwgt");
					 dataobj[j][4]=rs1.getString("rate");
					 dataobj[j][5]=rs1.getString("netgold");
					 dataobj[j][6]=String.valueOf(Double.parseDouble(dataobj[j][5].toString())*Double.parseDouble(dataobj[j][4].toString()));*/
					
					// Code Changed by Murugesh on 18/11/2005
					dataobj[j][0]=String.valueOf(rs1.getInt("srl_no"));
					dataobj[j][1]=rs1.getString("descr");
					dataobj[j][2]=String.valueOf(rs1.getDouble("grwgt"));
					dataobj[j][3]=String.valueOf(rs1.getDouble("netwgt"));
					dataobj[j][4]=null;
					dataobj[j][5]=String.valueOf(rs1.getDouble("rate"));
					//
					gobj.setApprisalDate(rs1.getString("date"));				
					gobj.setApprisalTime(rs1.getString("time"));				
					
					j++;
				}
				System.out.println("j value"+j);
				System.out.println("before setting");
				if(j>=1)
					gobj.setGoldDet(dataobj);
				
				System.out.println("after setting");
				lnobj.setGoldDet(gobj);
				
				//Vehicle details
				
				rs1=stmt.executeQuery("select * from VehicleMaster where ac_no="+ln_acno+" and ac_type='"+ln_acty+"'");
				VehicleObject vobj=null;
				
				if(rs1.next())
				{
					rs1.last();
					vobj=new VehicleObject();
					rs1.beforeFirst();
					j=0;
				}
				else 
					vobj=null;
				
				while(rs1.next())
				{
					vobj.setVehicleMake(rs1.getString("make"));
					vobj.setVehicleCost(rs1.getDouble("cost"));
					vobj.setAddressDealer(rs1.getString("addrdealer"));
					vobj.setLicenceNo(rs1.getString("licno"));
					vobj.setLicenceValidity(rs1.getString("validity"));
					vobj.setVehicleType(rs1.getString("type"));
					vobj.setPermitNo(rs1.getString("permitno"));	
					vobj.setPermitValidity(rs1.getString("pvalidity"));
					vobj.setVehicleFor(rs1.getString("vehfor"));
					vobj.setArea(rs1.getString("area"));
					vobj.setAddressParking(rs1.getString("addrpark"));
					vobj.setOtherDet(rs1.getString("other"));
					lnobj.setVehicleDet(vobj);
				}
				
				// Signature Details Code Added by Murugesh on 16/12/2005
				SignatureInstructionObject si[]=commonlocal.getSignatureDetails(ln_acno,ln_acty);
				lnobj.setSignatureDet(si);
				//
				
				rs1=stmt.executeQuery("select trn_amt,ve_tml from LoanTransaction where ac_type='"+ln_acty+"' and ac_no="+ln_acno+" and trn_type='D' order by trn_seq desc limit 1");
				if(rs1.next()){
					lnobj.setLastDisbmount(rs1.getDouble("trn_amt"));
					lnobj.setLoanDisbursed(true);
					if(rs1.getString("ve_tml")!=null)
						lnobj.setLoanDisbVerified(true);
				}
				//Vinay
				//ResultSet rs2 = stmt.executeQuery("select * from Acco");
				
				
			}
		}catch(Exception exception){ exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		System.out.println("before returning the loanMaster Object ");
		return lnobj; 
	}
	

	
	//Sanction the loan for given account type,account for this amount and this installment and set priorty, weaker section code
	/*public int sanctionLoan(int actype,int acno,double amt,int install,boolean priority,boolean weak,double rate)
	{
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			return(stmt.executeUpdate("update LoanMaster set sanc_amt="+amt+",sanc_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),disb_left="+amt+",int_rate="+rate+",no_inst="+install+",loan_sanc='Y',wk_sect='"+(weak==true?"Y":"N")+"',psect_cd='"+(priority==true?"Y":"N")+"' where ac_type='"+actype+"' and ac_no="+acno));
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return 0;
	}*/
	
	// Method added by murugesh on 23/12/2005
	public int sanctionLoan(int actype,int acno,double amt,int install,int priority,boolean weak,double rate,int int_type, int int_rate_type, double install_amt,double holiday_months, String trn_date)
	{
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			// return(stmt.executeUpdate("update LoanMaster set inst_amt="+install_amt+",sanc_amt="+amt+",sanc_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),disb_left="+amt+",int_type="+int_type+",int_rate_type="+int_rate_type+",int_rate="+rate+",no_inst="+install+",loan_sanc='Y',wk_sect='"+(weak==true?"Y":"N")+"',psect_cd='"+(priority==true?"Y":"N")+"' where ac_type='"+actype+"' and ac_no="+acno)); // Code changed by Murugesh on 24/02/2006
			return(stmt.executeUpdate("update LoanMaster set holday_mth="+holiday_months+",disb_left="+amt+" ,inst_amt="+install_amt+",sanc_amt="+amt+",sanc_date='"+trn_date+"',int_type="+int_type+",int_rate_type="+int_rate_type+",int_rate="+rate+",no_inst="+install+",loan_sanc='Y',wk_sect='"+(weak==true?"Y":"N")+"',psect_cd='"+priority+"' where ac_type='"+actype+"' and ac_no="+acno));
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return 0;
	}
	//
	
	public int verifyLoanMaster(int actype,int acno,String uid,String tml)
	{
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			return(stmt.executeUpdate("update LoanMaster set ve_tml='"+tml+"',ve_user='"+uid+"',ve_date='"+getSysDateTime()+"' where ac_type='"+actype+"' and ac_no="+acno));
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return 0;
	}
	
	//Verify the loan sanction make sanc_ver as 'Y'
	public int verifySanction(int actype,int acno) 
	{
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			//return(stmt.executeUpdate("update LoanMaster set sanc_ver='Y' where ac_type='"+actype+"' and ac_no="+acno)); // Code changed by Murugesh on 24/02/2006
			return(stmt.executeUpdate("update LoanMaster set sanc_ver='Y',disb_left=sanc_amt where ac_type='"+actype+"' and ac_no="+acno));
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return 0;
	}
	
	/*Disburse the loan for given account type and account number
	 * disbursement will contain the amount disbursed 
	 * object[][] will contain the schedule records
	 * amtsanct will contain the amount sanctioned
	 */
	public int disburseLoan(int actype,int acno,double disbursement,Object data[][],double amtsanct,String uid,String tml,String paymode,String narr,String trn_date,String date_time) 
	{
		System.out.println("amtsanct == "+amtsanct);
		Connection conn=null;
		int last_trn_seq = getLastTransactionSeq(String.valueOf(actype),acno,false); // Code added by Murugesh on 3/1/2006
		int num_disb = getNumPreviousDisb(String.valueOf(actype),acno,1); // Code added by Murugesh on 3/1/2006
		double closing_balance = getCurrentDayClosingBalance(String.valueOf(actype),acno,trn_date); // Code added by Murugesh on 9/2/2006
		closing_balance+= disbursement;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			stmt.executeUpdate("delete from LoanTransaction where ac_type like '"+actype+"' and ac_no="+acno+" and (trn_type like 'D' || trn_type like 'S%' ) and ve_tml  is null ");
			
			ResultSet rs_last_int_uptodate=stmt.executeQuery("select int_upto_date from LoanMaster where ac_type='"+String.valueOf(actype)+"' and ac_no="+acno+"");
			PreparedStatement pstmt=conn.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			pstmt.setInt(1,actype);
			pstmt.setInt(2,acno);
			pstmt.setInt(3,last_trn_seq+1); // Code changed by Murugesh on 3/1/2006
			pstmt.setString(4,trn_date);
			pstmt.setString(5,"D");
			pstmt.setDouble(6,disbursement);
			System.out.println("Narration"+paymode+"  "+narr);
			pstmt.setString(7,paymode);
			pstmt.setString(8,null);
			pstmt.setString(9,null);
			pstmt.setString(10,narr);
			pstmt.setString(11,null);
			pstmt.setString(12,"D");
			if(getNumPreviousDisb(String.valueOf(actype),acno,2)==0) // if the disbursement is the first disbursement then update the int_upto_date value in the LoanMaster with this sysdate-1 or get the last_int_upto date from LoanMaster and update it : by Murugesh 
				pstmt.setString(13,Validations.addDays(trn_date,-1));
			else
				{
					if(rs_last_int_uptodate.next())
						pstmt.setString(13,rs_last_int_uptodate.getString("int_upto_date"));
					else
						pstmt.setString(13,Validations.addDays(trn_date,-1));
				}
			pstmt.setDouble(14,0);
			pstmt.setDouble(15,0);
			pstmt.setDouble(16,0);
			pstmt.setDouble(17,0);
			pstmt.setDouble(18,0);
			pstmt.setDouble(19,closing_balance);
			pstmt.setString(20,tml);
			pstmt.setString(21,uid);
			pstmt.setString(22,date_time);
			//pstmt.setString(22,null);
			pstmt.setString(23,null);
			pstmt.setString(24,null);
			pstmt.setString(25,null);
			pstmt.addBatch();
			
			//Insert all schedule records
			for(int i=0;i<(data.length-1);i++)
			{
				pstmt.setInt(3,0);
				pstmt.setString(4,data[i][1].toString());
				pstmt.setString(5,"S"+(num_disb+1));
				pstmt.setDouble(6,Double.parseDouble(data[i][4].toString()));
				pstmt.setString(12,"C");
				pstmt.setString(13,Validations.addDays(data[i][1].toString(),-1));
				pstmt.setDouble(14,Double.parseDouble(data[i][2].toString()));
				pstmt.setDouble(15,Double.parseDouble(data[i][3].toString()));
				pstmt.setDouble(19,Double.parseDouble(data[i][5].toString()));
				pstmt.setString(7,"");
				pstmt.setString(10,"");
				
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			//return(stmt.executeUpdate("update LoanMaster set disb_left=disb_left-"+disbursement+",int_upto_date='"+Validations.addDays(getSysDate(),-1)+"'  where ac_type='"+actype+"' and ac_no="+acno)); // Code changed by Murugesh on 4/1/2005
			System.out.println("lst_tr_seq---------------------"+(last_trn_seq+1));
			System.out.println("int_upto_date+++++++++++++++++++"+Validations.addDays(trn_date,-1));
			
			if(getNumPreviousDisb(String.valueOf(actype),acno,2)==0) // if the disbursement is the first disbursement then update the int_upto_date value in the LoanMaster with this sysdate-1 or dont change the value : by Murugesh 
				return(stmt.executeUpdate("update LoanMaster set int_upto_date='"+Validations.addDays(trn_date,-1)+"'  where ac_type='"+actype+"' and ac_no="+acno)); // Code changed by Murugesh on 9/2/2006
			else
			 return(1); // Code added by Murugesh on 9/2/2006
			
		}catch(SQLException exception)
		{
			exception.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		catch(DateFormatException exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return 0;
	}
	
	/*Reschedule the loan	for the given account type and account number
	 * Vector will contain new schedule records
	 */
	public int reScheduleLoan(String actype,int acno,int install,double amt,Vector vec,String effdate,String uid, String tml,String date_time) 
	{
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();			
			stmt=conn.createStatement();
			//Delete previous scheduled records after from effective date
			System.out.println("********Reschedule Loan***********");
			System.out.println("Actype"+actype+"Acno"+acno+"Install"+install+"Amt"+amt+"Vector Size"+vec.size()+"Effective Date"+effdate);
			int t=stmt.executeUpdate("delete from LoanTransaction where ac_type="+actype+"  and ac_no="+acno+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))>='"+Validations.convertYMD(effdate)+"' and trn_type='S'");
			//Insert new Schedule records
			System.out.println("Server "+t);
			if(t>0)
			{				
				PreparedStatement pstmt=conn.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				Object data[]=new Object[6];
				for(int j=0;j<vec.size()-1;j++)
				{
					//data=(Object[])vec.elementAt(j);
					
					  Map map1 = new TreeMap();
					  map1 = (Map)vec.elementAt(j);
					 data[0]=map1.get("id");
					 data[1]=map1.get("LoanTrandate");
					 data[2]=map1.get("PrincipalPaid");
					 data[3]=map1.get("IntPaid");
					 data[4]=map1.get("TranAmt");
					 data[5]=map1.get("LoanBal");
					
					
					if(j==0)
					{
						pstmt.setString(1,actype);//Account type
						pstmt.setInt(2,acno);//Account number
						pstmt.setString(5,"S");//Type of record
						pstmt.setString(7,null);//trnasaction mode
						pstmt.setString(8,null);//transaction source
						pstmt.setString(9,null);//Ref no
						pstmt.setString(10,null);//transaction narration
						pstmt.setString(11,null);//recovery date
						pstmt.setString(12,"C");//Cd ind
						pstmt.setDouble(16,0);//penal_amt
						pstmt.setDouble(17,0);//other_amt
						pstmt.setDouble(18,0);//extra_int
						pstmt.setString(20,tml);
						pstmt.setString(21,uid);
						pstmt.setString(22,date_time); // Code Changed by Murugesh on 18/1/2006
						pstmt.setString(23,tml);
						pstmt.setString(24,uid);
						pstmt.setString(25,date_time);
						
						
					}
					pstmt.setInt(3,0);//no transaction seq for "S" records
					pstmt.setString(4,data[1].toString());//	Transaction date
					System.out.println("After getting ");
					pstmt.setDouble(6,Double.parseDouble(data[4].toString()));//Transaction amount
					System.out.println("Data in array"+data[1].toString());
					pstmt.setString(13,Validations.addDays(data[1].toString(),-1));//int_date
					pstmt.setDouble(14,Double.parseDouble(data[2].toString()));//pr_amt
					pstmt.setDouble(15,Double.parseDouble(data[3].toString()));//int_amt
					pstmt.setDouble(19,Double.parseDouble(data[5].toString()));//Pr_bal
					pstmt.addBatch();
				}
				pstmt.executeBatch();
			}
			//Update no of installments
			return(stmt.executeUpdate("update LoanMaster set no_inst="+install+",inst_amt="+amt+"  where ac_type='"+actype+"' and ac_no="+acno));
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return 0;
	}
	
	
	//Automatic loan recovery	
	public int loanRecovery(String uid,String utml,String date,String date_time)
	{	
		System.out.println("******Loan Recovery bean*************");
		Connection conn=null;
		try{
			double prnamt=0,interestamt=0,penalintamt=0,othercharges=0,max_amt_to_collect=0.0,amount_to_collect=0.0,prn_to_be_paid=0.0,min_bal=0.0;
			int sub_category=0;
			ResultSet rs_rec=null;
			Object trn_seq_obj=null;
			LoanTransactionObject loan_trn=null;
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement rec_stmt=conn.createStatement();
			
			stmt=conn.createStatement();
			// ResultSet rs=stmt.executeQuery("select distinct LoanMaster.ac_type,LoanMaster.ac_no,LoanMaster.pay_ac_type,LoanMaster.pay_ac_no from LoanMaster,LoanTransaction where LoanTransaction.ac_type=LoanMaster.ac_type and LoanMaster.ac_no=LoanTransaction.ac_no and pr_bal>0 and pay_mode='T' and pay_ac_no is not null and pay_ac_type is not null"); // Code changed by Murugesh on 15/03/2006
			ResultSet rs=stmt.executeQuery("select distinct LoanMaster.ac_type,LoanMaster.ac_no,LoanMaster.pay_ac_type,LoanMaster.pay_ac_no,CustomerMaster.sub_category from LoanMaster,LoanTransaction,CustomerMaster  where LoanTransaction.ac_type=LoanMaster.ac_type and LoanMaster.ac_no=LoanTransaction.ac_no and LoanMaster.cid=CustomerMaster.cid and pr_bal>0 and pay_mode='T' and pay_ac_no is not null and pay_ac_type is not null and LoanMaster.ac_type like '101%'"); // Code added by Murugesh on 15/03/2006
			while(rs.next())
			{
				String ln_type=rs.getString("LoanMaster.ac_type");
				int no=rs.getInt("LoanMaster.ac_no");
				sub_category = rs.getInt("CustomerMaster.sub_category");
				//Get curent loan status
				//LoanTransactionObject ltrn=getLoanStatus(ln_type,no);				
				String sbtype=rs.getString("pay_ac_type");
				int sbno=rs.getInt("pay_ac_no");
				cremote=home.create();				
				AccountObject acc=cremote.getAccount(null,sbtype,sbno,date);		
				System.out.println("*************Returning from getaccount()***********");
				if(acc!=null)
				{
					rs_rec=rec_stmt.executeQuery("select min_bal from Modules where modulecode='"+sbtype+"'");
					if(rs_rec.next())
						min_bal = rs_rec.getDouble("min_bal");
					
					rs_rec.close();
					double amt_available=(acc.getAmount()-min_bal); // Murugesh: we are doing this in order to maintain a min balance in the account
					
					rs_rec=rec_stmt.executeQuery("select * from LoanRecoveryDetail where concat(right(processing_date,4),'-',mid(processing_date,locate('/',processing_date)+1,(locate('/',processing_date,4)-locate('/',processing_date)-1)),'-',left(processing_date,locate('/',processing_date)-1)) = '"+Validations.convertYMD(date) +"' and ac_type='"+ln_type+"' and ac_no="+no+" ");
					if(!rs_rec.next()){
						postRecoveryDetails(ln_type,no,date,sub_category,1,null,null); // We are calling this function because to update the LoanRecoveryDetail table with current calculated values of interest,penal_interest,other_charges,loan_balance for the current date
					}
					rs_rec.close();
					
					rs_rec = rec_stmt.executeQuery("select int_amt+penal_amt+other_charges as total from LoanRecoveryDetail where ac_type='"+ln_type+"' and ac_no="+no+" ");
					if(rs_rec.next())
					{
						max_amt_to_collect = rs_rec.getDouble("total");
					}
					rs_rec.close();
					
					loan_trn = getPrincipalOutstandings(ln_type,no,date);
					
					if(loan_trn!=null)
						max_amt_to_collect += loan_trn.getPrincipalPayable()+loan_trn.getPrincipalBalance();
					
					if(max_amt_to_collect>amt_available)
						max_amt_to_collect=amt_available;
					if(max_amt_to_collect>0.0)
					{
						loan_trn=null;
						loan_trn=new LoanTransactionObject();
						
						loan_trn.setAccType(ln_type);
						loan_trn.setAccNo(no);
						loan_trn.setTransactionDate(date);
						loan_trn.setTranType("R");
						loan_trn.setTransactionAmount(max_amt_to_collect);
						loan_trn.setTranMode("T");
						loan_trn.setTranSou(utml);
						loan_trn.setReferenceNo(0);
						loan_trn.setTranNarration(sbtype+"  "+sbno);
						loan_trn.setCdind("C");
						loan_trn.uv.setUserTml(utml);
						loan_trn.uv.setUserId(uid);
						loan_trn.uv.setUserDate(date_time);
						loan_trn.uv.setVerDate(date_time);
						loan_trn.uv.setVerId(uid);
						loan_trn.uv.setVerTml(utml);
						
						trn_seq_obj=null;
						try{
							trn_seq_obj = recoverLoanAccount(loan_trn);
						}catch(RecordNotUpdatedException recn){recn.printStackTrace();}
						
						if(trn_seq_obj !=null)
						{
							AccountTransObject actrn=new AccountTransObject();
							actrn.setAccType(sbtype);
							actrn.setAccNo(sbno);
							System.out.println(" @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ trn_date :"+loan_trn.getTransactionDate());
							actrn.setTransDate(date);
							actrn.setTransType("P");
							actrn.setTransAmount(max_amt_to_collect);
							actrn.setTransMode("T");
							actrn.setCdInd("D");
							actrn.setTransNarr(ln_type+"  "+no);
							actrn.uv.setUserTml(uid);
							actrn.uv.setUserId(utml);
							actrn.uv.setVerTml(utml);
							actrn.uv.setVerId(uid);
							
							cremote=home.create();
							int a=cremote.storeAccountTransaction(actrn);
							System.out.println("a == "+a);
						}
					}
				}
			}
			
		}catch(Exception exception){exception.printStackTrace(); sessionContext.setRollbackOnly();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return 0;
	}
	
	/**
	 * @author Murugesh -->
	 * @param LoanTransactionObject 
	 * @throws RemoteException,RecordNotUpdatedException
	 * 
	 */
	public Object recoverLoanAccount(LoanTransactionObject loan_object) throws RecordNotUpdatedException
	{
		Connection conn=null;
		Statement stmt=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		double other_charges=0.0,penal_interest=0.0,interest=0.0,loan_balance=0.0,extra_interest=0.0,amount_available=0.0,principal_payable=0.0;
		String int_upto_date=null,share_type=null;
		String temp_int_upto_date=null;
		int trn_seq=0,category=0,share_no=0;
		LoanTransactionObject ltrn=null;
		Integer trn_seq_object=null;
		try{
			cremote=home.create();
			conn=getConnection();
			stmt=conn.createStatement();
			
			if(loan_object != null)
			{
				//if(loan_object.getTranMode().equalsIgnoreCase("C"))
				{
					if(loan_object.uv.getVerTml()!=null)  // This is checked bcoz the GL enteries should be posted only for verified accounts and for transfer this function is called twice during dataentry and verification and so this checking is essential 
					{
						rs=stmt.executeQuery("select lst_tr_seq+1 as trn_seq,cm.sub_category,lm.int_upto_date,lm.sh_type,lm.sh_no from LoanMaster as lm, CustomerMaster as cm where lm.ac_type='"+loan_object.getAccType()+"' and lm.ac_no="+loan_object.getAccNo()+" and lm.cid=cm.cid");
						if(rs.next())
						{
							if(rs.getString("trn_seq")!=null) // This condition is checked because when it is called for the first time the value will be null and so this condtion check is necessary
								trn_seq = rs.getInt("trn_seq");
							else
								trn_seq=1;
							category = rs.getInt("sub_category");
							temp_int_upto_date=rs.getString("int_upto_date");
							share_type = rs.getString("sh_type");
							share_no = rs.getInt("sh_no");
						}
						rs.close();
						int update=stmt.executeUpdate("update LoanMaster set lst_tr_seq="+trn_seq+" where  ac_type='"+loan_object.getAccType()+"' and ac_no="+loan_object.getAccNo()+"");
						
						if(update<=0)
							throw new RecordNotUpdatedException();
					}
					
					rs=stmt.executeQuery("select cm.sub_category,lm.int_upto_date from LoanMaster as lm, CustomerMaster as cm where lm.ac_type='"+loan_object.getAccType()+"' and lm.ac_no="+loan_object.getAccNo()+" and lm.cid=cm.cid");
					if(rs.next())
						category = rs.getInt("sub_category");
					rs.close();
					
					amount_available = loan_object.getTransactionAmount();
					amount_available=Math.floor(amount_available);
					
					if(amount_available>0)
					{
						other_charges = getOtherChargesForRecovery(loan_object.getAccType(),loan_object.getAccNo(),loan_object.getTransactionDate());
						if(amount_available>=other_charges)
							amount_available=amount_available-other_charges;
						else
						{
							other_charges=amount_available;
							amount_available=0.0;
						}
					}
					
					if(amount_available>0)
					{
						penal_interest= calculatePenalInterestForRecovery(loan_object.getAccType(),loan_object.getAccNo(),loan_object.getTransactionDate(),category);
						if(amount_available >= penal_interest)
							amount_available=amount_available-penal_interest;
						else
						{
							penal_interest=amount_available;
							amount_available=0.0;
						}
					}
					
					if(amount_available>0)
					{
						ltrn=calculateInterestForRecovery(loan_object.getAccType(),loan_object.getAccNo(),loan_object.getTransactionDate(),amount_available,category);
						
						if(ltrn!=null)
						{
							interest = ltrn.getInterestPayable();
							extra_interest = ltrn.getExtraIntAmount();
							int_upto_date = ltrn.getIntUptoDate();

							if(amount_available >= (interest + extra_interest))
								amount_available=amount_available-(interest + extra_interest);
						}
					}
					
					if(int_upto_date==null)  // we are checking this condition because only for interest the above conditon will be checked and int_upto_date will updated otherwise if the amount is partial towards penal interest or other charges the interest conditon will not be checked 
						int_upto_date=temp_int_upto_date; // and in such sitution the int_upto_date declared will be null and null value will be updated int Loan tran table and loan master table so to avoid this we are updating it with the previous int_upto_date
				
					System.out.println("the Other charges values is:"+other_charges);
					System.out.println("the penal_interest values is:"+penal_interest);
					System.out.println("the interest values is:"+interest);
					System.out.println("the amount_available values is:"+amount_available);
					// These conditions are checked because at any point these values should not be negative
					if(other_charges<0)
						other_charges=0.0;
					
					if(penal_interest<0)
						penal_interest=0.0;
					
					if(interest<0)
						interest=0.0;
					
					if(amount_available<0)
						amount_available=0.0;
					
					principal_payable=loan_object.getTransactionAmount()-(other_charges+penal_interest+interest+extra_interest);
					
					if(principal_payable<0)
						principal_payable=0.0;
					
					loan_balance = getCurrentDayClosingBalance(loan_object.getAccType(),loan_object.getAccNo(),loan_object.getTransactionDate());
					loan_balance = loan_balance-principal_payable;
					
					if(loan_balance<0)
						loan_balance=0.0;
					
					pstmt = conn.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

					pstmt.setString(1,loan_object.getAccType());
					pstmt.setInt(2,loan_object.getAccNo());
					pstmt.setInt(3,trn_seq);
					pstmt.setString(4,loan_object.getTransactionDate());
					pstmt.setString(5,loan_object.getTranType());
					pstmt.setDouble(6,loan_object.getTransactionAmount());
					pstmt.setString(7,loan_object.getTranMode());
					pstmt.setString(8,loan_object.getTranSou());
					pstmt.setInt(9,loan_object.getReferenceNo());
					pstmt.setString(10,loan_object.getTranNarration());
					pstmt.setString(11,null);
					pstmt.setString(12,loan_object.getCdind());
					pstmt.setString(13,int_upto_date);
					pstmt.setDouble(14,principal_payable);
					pstmt.setDouble(15,interest);
					pstmt.setDouble(16,penal_interest);
					pstmt.setDouble(17,other_charges);
					pstmt.setDouble(18,extra_interest);
					pstmt.setDouble(19,loan_balance);
					pstmt.setString(20,loan_object.uv.getUserTml());
					pstmt.setString(21,loan_object.uv.getUserId());
					pstmt.setString(22,loan_object.uv.getUserDate());
					pstmt.setString(23,loan_object.uv.getVerTml());
					pstmt.setString(24,loan_object.uv.getVerId());
					pstmt.setString(25,loan_object.uv.getVerDate());
					
					int update_tran=pstmt.executeUpdate();
					if(rs!=null)
						rs.close();

					if(loan_object.uv.getVerTml()!=null)  // This is checked bcoz the GL enteries should be posted only for verified accounts and for transfer this function is called twice during dataentry and verification and so this checking is essential 
					{
						System.out.println(" inside the gl entries---------------- ");
						
						if(update_tran>0)
						{
							int loan_master_update = stmt.executeUpdate("update LoanMaster set int_upto_date='"+int_upto_date+"',lst_trn_date='"+loan_object.getTransactionDate()+"' where ac_type='"+loan_object.getAccType()+"' and ac_no="+loan_object.getAccNo()+"");
							if(loan_master_update<=0)
								throw new RecordNotUpdatedException();
						
							if(loan_balance==0){
								stmt.executeUpdate("update LoanMaster set close_date='"+loan_object.getTransactionDate()+"' where ac_no="+loan_object.getAccNo()+" and ac_type='"+loan_object.getAccType()+"'");
								stmt.executeUpdate("update ShareMaster set ln_availed='F' where ac_type = '"+share_type+"' and ac_no="+share_no+" ");
							}
						}
						else
							throw new RecordNotUpdatedException();
						
						postRecoveryDetails(loan_object.getAccType(),loan_object.getAccNo(),loan_object.getTransactionDate(),category,1,loan_object.uv.getVerId(),loan_object.uv.getVerTml()); // this has been done to update the LoanRecoveryDetail table with new updated values;
						
						//GL Enteries for credit of the above transaction  
						GLTransObject trnobj = new GLTransObject();
						
						trnobj.setCdind(loan_object.getCdind());
						trnobj.setTrnDate(loan_object.getTransactionDate());
						trnobj.setTrnMode(loan_object.getTranMode());
						trnobj.setAccType(loan_object.getAccType());
						trnobj.setAccNo(String.valueOf(loan_object.getAccNo()));
						trnobj.setTrnSeq(trn_seq);
						trnobj.setTrnType(loan_object.getTranType());
						trnobj.setRefNo(loan_object.getReferenceNo());
					    trnobj.setVtml(loan_object.uv.getVerTml());
					    trnobj.setVid(loan_object.uv.getVerId());
					    trnobj.setVDate(loan_object.uv.getVerDate());
					    if(rs!=null)
					    	rs.close();
					    
					    if(other_charges>0)
		                {
		                    rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+loan_object.getAccType()+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='C' and code=4");
		                    if(rs.next()) {
		                    	trnobj.setGLType(rs.getString(1));
		                        trnobj.setGLCode(rs.getString(2));
		                        trnobj.setAmount(other_charges*rs.getInt("mult_by"));
		                    } else {
		                    	trnobj.setGLType(null);
		                        trnobj.setGLCode(null);
		                        trnobj.setAmount(other_charges);
		                    }
		                    cremote.storeGLTransaction(trnobj);
		                }
					    if(rs!=null)
					    	rs.close();

					    if(penal_interest>0)
					    {
					    	rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+loan_object.getAccType()+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='C' and code=3");
		                    if(rs.next()){
		                    	trnobj.setGLType(rs.getString(1));
		                        trnobj.setGLCode(rs.getString(2));
		                        trnobj.setAmount(penal_interest*rs.getInt("mult_by"));
		                    } else {
		                    	trnobj.setGLType(null);
		                        trnobj.setGLCode(null);
		                        trnobj.setAmount(penal_interest);
		                    }
		                    cremote.storeGLTransaction(trnobj);
					    }
					    if(rs!=null)
					    	rs.close();
					    
					    if((interest+extra_interest)>0)
					    {
					    	rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+loan_object.getAccType()+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='C' and code=2");
		                    if(rs.next()) {
		                    	trnobj.setGLType(rs.getString(1));
		                        trnobj.setGLCode(rs.getString(2));
		                        trnobj.setAmount((interest+extra_interest)*rs.getInt("mult_by"));
		                    } else {
		                    	trnobj.setGLType(null);
		                        trnobj.setGLCode(null);
		                        trnobj.setAmount((interest+extra_interest));
		                    }
		                    cremote.storeGLTransaction(trnobj);
					    }
					    if(rs!=null)
					    	rs.close();
					    
					    if(principal_payable>0)
					    {
					    	rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+loan_object.getAccType()+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='C' and code=1");
		                    if(rs.next()) {
		                    	trnobj.setGLType(rs.getString(1));
		                        trnobj.setGLCode(rs.getString(2));
		                        trnobj.setAmount(principal_payable*rs.getInt("mult_by"));
		                    } else {
		                    	trnobj.setGLType(null);
		                        trnobj.setGLCode(null);
		                        trnobj.setAmount(principal_payable);
		                    }
		                    cremote.storeGLTransaction(trnobj);
					    }
					    if(rs!=null)
					    	rs.close();
					}
				    
				   //if(loan_object.getTranMode().equalsIgnoreCase("C"))
				   {
				   		trn_seq_object = new Integer(trn_seq);
				   		return trn_seq_object;
					}
				}
			}
		}
		catch(SQLException exception){
			sessionContext.setRollbackOnly();
			exception.printStackTrace();
		}
		catch(RecordNotUpdatedException rec_exception){
			sessionContext.setRollbackOnly();
			throw rec_exception;
		}
		catch(Exception exe){
			sessionContext.setRollbackOnly();
			exe.printStackTrace();
		}
		finally	{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return null;
	}
	
	public double calculateInterest(String actype,String fdate,String tdate,double amt,int category,int period,double disbamt,int ac_no)
	{
		double interest=0;
		Connection conn=null;
		try{
			conn=getConnection();
			//Statement stmt=conn.createStatement();
			//Get interest rate for the account type
			//Vector will contain the interest slabs defined between the given dates
			Vector vec=getIntRate(actype,fdate,tdate,category,period,disbamt,ac_no);
			String ctrndate=tdate;
			
			System.out.println("-----------------------Interest Calculation--------------");			
			System.out.println("Length"+vec.size());
			
			
			for(Enumeration enume=vec.elements();enume.hasMoreElements();)
			{
				LoanIntRateObject dint[]=(LoanIntRateObject[])enume.nextElement();				
				//if  more that int rate defined between the transaction date
				for(int i=0;i<dint.length;i++)
				{
					
					if(i<(dint.length-1))
						tdate=dint[i].getToDate();
					else
						tdate=ctrndate;
					
					System.out.println("From date"+fdate+"  Tdate"+tdate+" Amount"+amt);
					interest=interest+((Validations.dayCompare(fdate,tdate)*(dint[i].getIntRate()/36500)*(amt)));
					fdate=Validations.addDays(dint[i].getToDate(),1);					  
					System.out.println("----------------------------------------");	
					System.out.println("From date "+fdate);	
					System.out.println("To date "+tdate);
					System.out.println("rate "+dint[i].getIntRate());
					System.out.println("interest value is:"+interest);
					System.out.println("----------------------------------------");	
				}
			}
			
			System.out.println("Intertest"+interest);
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return(interest);
	}	
	
	public double calculatePenalInt(String actype,String fdate,String tdate,double amt,int category)
	{
		double penalint=0.0;
		Connection conn=null;
		try{
			conn=getConnection();
			
			LoanIntRateObject dint[]=getPenalIntRate(actype,fdate,tdate,category);
			String ctrndate=tdate;
			if(dint.length>1)
			{
				//if  more that penal int rate defined between the transaction date
				for(int i=0;i<dint.length;i++)
				{
					if(i<(dint.length-1))
						tdate=dint[i].getToDate();
					else
						tdate=ctrndate;
					//penalint=penalint+(Math.round(Validations.dayCompare(fdate,tdate)*(dint[i].getPenalRate()/36500)*(amt)));
					System.out.println("the number of days between  "+fdate+" to  "+tdate+"  is :"+Validations.dayCompare(fdate,tdate));
					System.out.println("the amount to be calculated is :"+amt);
					penalint=penalint+(Validations.dayCompare(fdate,tdate)*(dint[i].getPenalRate()/36500)*(amt));
					System.out.println("the penal interest is: "+penalint);
					fdate=Validations.addDays(dint[i].getToDate(),1);					  
				}
			}
			else if(dint.length>0){
				 //penalint=(Math.round(Validations.dayCompare(fdate,tdate)*(dint[0].getPenalRate()/36500)*(amt)));
				System.out.println("the number of days between  "+fdate+" to  "+tdate+"  is :"+Validations.dayCompare(fdate,tdate));
				System.out.println("the amount to be calculated is :"+amt);
				penalint=(Validations.dayCompare(fdate,tdate)*(dint[0].getPenalRate()/36500)*(amt));
				System.out.println("the penal interest is: "+penalint);
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}		
		return(penalint);
	}
	
	
	//Get all jewel details between given dates
	public Vector getJewelReport(String from,String to ) 
	{
		//GoldObject gobj[]=null;
		Vector gold=new Vector(0,1);
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select GoldDetMaster.*,sanc_date,name from GoldDetMaster,LoanMaster,ApprisersMaster where ApprisersMaster.code=GoldDetMaster.appcode and  LoanMaster.ac_type=GoldDetMaster.ac_type and LoanMaster.ac_no=GoldDetMaster.ac_no and concat(right(sanc_date,4),'-',mid(sanc_date,locate('/',sanc_date)+1,(locate('/',sanc_date,4)-locate('/',sanc_date)-1)),'-',left(sanc_date,locate('/',sanc_date)-1))>='"+Validations.convertYMD(from)+"' and concat(right(sanc_date,4),'-',mid(sanc_date,locate('/',sanc_date)+1,(locate('/',sanc_date,4)-locate('/',sanc_date)-1)),'-',left(sanc_date,locate('/',sanc_date)-1))<='"+Validations.convertYMD(to)+"' order by concat(right(sanc_date,4),'-',mid(sanc_date,locate('/',sanc_date)+1, (locate('/',sanc_date,4)-locate('/',sanc_date)-1)),'-',left(sanc_date,locate('/',sanc_date)-1)),ac_no,ac_type");		
			int j=0,k=1;
			Vector vec=new Vector(0,1);
			String actype=null;
			int acno=0;
			GoldObject gobj=new GoldObject();
			
			while(rs.next())
			{
				actype=rs.getString("ac_type");
				acno=rs.getInt("ac_no");
				if(rs.getRow()!=1)
				{
					if((!gobj.getAccType().equals(actype)) || gobj.getAccNo()!=acno)
					{
						gobj.setGoldDetVector(vec);
						gold.add(gobj);
						vec=new Vector(0,1);						
						j++;
						gobj=new GoldObject();
						k=1;				
					}					
				}
				
				gobj.setAccType(actype);
				gobj.setAccNo(acno);
				gobj.setApprisersName(rs.getInt("appcode")+"  "+rs.getString("name"));
				gobj.setApprisalDate(rs.getString("sanc_date"));
				
				Object dataobj[]=new Object[6];
				
				dataobj[0]=String.valueOf(k);
				dataobj[1]=rs.getString("descr");
				dataobj[2]=rs.getString("grwgt");
				dataobj[3]=rs.getString("netwgt");
				dataobj[4]=rs.getString("rate");
//				dataobj[5]=rs.getString("netgold");
				
				vec.add(dataobj);
				k++;			
				gobj.setApprisalDate(rs.getString("date"));				
				gobj.setApprisalTime(rs.getString("time"));					
			}
			gobj.setGoldDetVector(vec);
			gold.add(gobj);			
		}catch(Exception exception){
			exception.printStackTrace();
			}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){
				exception.printStackTrace();
				}
		}
		
		return gold;
	}
	
	
	/*public int storeGLTransaction(GLTransObject trnobj) 
	{
		Connection conn=null;
		try{
			conn=getConnection();
			
			PreparedStatement pstmt=conn.prepareStatement("insert into GLTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,date_format(sysdate(),'%d/%m/%Y %r'))");
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
			return(pstmt.executeUpdate());
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return 0;
	}*/
	
	//Athul's
	public int storeLoanAmount(LoanTransactionObject lnobj,int type,String trn_date) throws RecordsNotInsertedException 
	{ 
		int trf_scroll_no=0;		
		int a=0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = null;
			if(type==1){
				System.out.println("11111");
				//trf_scroll_no = commonLocal.getGenParamUpdated("lndep_trn_no");	// Since genParam Table is changed and the trf_scroll number is stored in module table
				trf_scroll_no = getUpdatedTrnScrollNumber();
			}
			else{
				System.out.println("22222");
				st.executeUpdate("delete from LoanTransaction where ref_no="+lnobj.getReferenceNo()+" and ac_type='"+lnobj.getAccType()+"' and ac_no="+lnobj.getAccNo());
				trf_scroll_no=lnobj.getReferenceNo();
			}	
			
			System.out.println("33333");
			LoanTransactionObject loan_trn = new LoanTransactionObject();
			
			loan_trn.setAccType(lnobj.getAccType());
			loan_trn.setAccNo(lnobj.getAccNo());
			loan_trn.setTransactionDate(trn_date);
			loan_trn.setTranType("R");
			loan_trn.setTransactionAmount(lnobj.getTransactionAmount());
			loan_trn.setTranMode("T");
			loan_trn.setTranSou(lnobj.uv.getUserTml());
			loan_trn.setReferenceNo(trf_scroll_no);
			loan_trn.setTranNarration(lnobj.getTranType()+" "+lnobj.getTranSequence());
			loan_trn.setCdind("C");
			loan_trn.uv.setUserTml(lnobj.uv.getUserTml());
			loan_trn.uv.setUserId(lnobj.uv.getUserId());
			loan_trn.uv.setUserDate(lnobj.uv.getUserDate());
			loan_trn.uv.setVerDate(null);
			loan_trn.uv.setVerId(null);
			loan_trn.uv.setVerTml(null);
			
			recoverLoanAccount(loan_trn);
			
			System.out.println("scroll no"+trf_scroll_no);
		}catch(Exception exception){
			exception.printStackTrace();
			sessionContext.setRollbackOnly();
			System.out.println("777777");
			trf_scroll_no = 0;
			throw new RecordsNotInsertedException();
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return trf_scroll_no;	
	}
	
	public LoanMasterObject getQueryOnLoanStatus(String ln_acty,int ln_acno,String trn_date) 
	{
		LoanMasterObject lnobj=null;
		Connection conn=null;
		try{
			System.out.println("-----------In the Bean of getQueryOnLoanStatus-------");
			conn=getConnection();
			ResultSet lnres,lnres_extra;
			Statement lnstat=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			Statement lnstmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			lnres=lnstat.executeQuery("select concat_ws(ifnull(fname,' '),ifnull(mname,' '),ifnull(lname,' ')) as name,lm.*,lp.pps_desc,cm.sub_category from LoanMaster lm,LoanPurposes lp,CustomerMaster cm where lm.ac_type='"+ln_acty+"' and lm.ac_no="+ln_acno+" and lm.pps_code=lp.pps_no and cm.cid=lm.cid");				
			lnres.last();
			if(lnres.getRow()>0)
			{ 	
				System.out.println("-------After executing the Query------");
				lnobj = new LoanMasterObject();
				lnres.first();
				lnobj.setName(lnres.getString("name"));
				lnobj.setMailingAddress(lnres.getInt("lm.addr_type"));
				lnobj.setSanctionedAmount(lnres.getDouble("sanc_amt"));
				lnobj.setSanctionDate(lnres.getString("sanc_date"));
				lnobj.setInstallmentAmt(lnres.getDouble("inst_amt"));
				lnobj.setLastTrnSeq(lnres.getInt("no_inst"));
				lnobj.setLdgPrntDate(lnres.getString("lp.pps_desc"));
				lnobj.setClosedt(lnres.getString("close_date"));
				
				if(lnres.getInt("int_rate_type")==1)  /**floating type calculation*/
					lnobj.setInterestRate(getIntRate(ln_acty,trn_date,lnres.getInt("cm.sub_category"),lnres.getInt("no_inst"),lnres.getDouble("sanc_amt"),ln_acno));
				else
					lnobj.setInterestRate(lnres.getDouble("int_rate"));
				
				lnobj.setCustomerId(lnres.getInt("lm.cid"));
				lnobj.setShareAccNo(lnres.getInt("lm.sh_no"));
				lnobj.setShareAccType(lnres.getString("lm.sh_type"));				
				lnres_extra = lnstmt.executeQuery("select penalrate from PenalIntRate where '"+Validations.convertYMD(trn_date)+"' between date_fr and date_to and ln_type='"+ln_acty+"' and category="+lnres.getInt("cm.sub_category"));
				lnres_extra.last();
				if(lnres_extra.getRow() > 0){ 
					lnres_extra.first();
					lnobj.setPenalRate(lnres_extra.getDouble("penalrate"));
				}
				lnres_extra = lnstmt.executeQuery("select moduleabbr,catname from ShareMaster sm,ShareType st,Modules where sm.ac_type='"+lnobj.getShareAccType()+"' and sm.ac_no="+lnobj.getShareAccNo()+" and sm.ac_type = modulecode and sm.ac_type=st.ac_type and sm.mem_cat=st.mem_cat");
				lnres_extra.next();
				lnobj.setPayMode(lnres_extra.getString(1));
				lnobj.setPaymentAcctype(lnres_extra.getString(2));
			}
		}catch(Exception exception){
			exception.printStackTrace();	
			lnobj = null;
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}	
		//System.out.println("----Result in the Bean----"+ lnobj.getShareAccNo());
		return lnobj;
	}
	/**
	 * 
	 * @param ln_acty
	 * @param ln_acno
	 * @return
	 * getting the loan status
	 */
	
	public LoanTransactionObject getQueryLoanStatus(String ln_acty,int ln_acno,String trn_date,String userid,String terminal,String datetime) 
	{
		LoanTransactionObject lnobj=null;
		/*double prnbal=0,otheramt=0,othercharges=0,extraintamt=0,schamt=0,prnpaid=0,intpaid=0;
		double penalint=0,penalintpaid=0,prevbal=0,intrate=0,disbamt=0,lst_sch_amt=0;
		String intupto="",ptrndt="",ctrndt="",lst_sch_date="";
		int int_rate_type=0,category=0,noinstall=0;*/
		
		Connection conn=null;
		try{
			
			double int_rate=0.0,loan_balance=0.0,other_charges=0.0,penal_interest=0.0,extra_amt=0.0,interest_amt=0.0;
			conn=getConnection();
			Statement lnstmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = lnstmt.executeQuery("select cm.sub_category,lm.sanc_amt,lm.int_rate,lm.int_rate_type,lm.no_inst from LoanMaster lm,CustomerMaster cm where lm.cid=cm.cid and  lm.ac_type='"+ln_acty+"' and lm.ac_no="+ln_acno+"");
				
			int sub_category=0;
			if(rs.next()){
				
				sub_category = rs.getInt("sub_category");
				if(rs.getInt("int_rate_type")==1)
					int_rate=getIntRate(ln_acty,trn_date,rs.getInt("sub_category"),rs.getInt("no_inst"),rs.getDouble("sanc_amt"),ln_acno);
				else
					int_rate=rs.getDouble("int_rate");
			}
			rs.close();
				
			System.out.println("the date is:"+trn_date);
				
			rs = lnstmt.executeQuery("select * from LoanRecoveryDetail where concat(right(processing_date,4),'-',mid(processing_date,locate('/',processing_date)+1,(locate('/',processing_date,4)-locate('/',processing_date)-1)),'-',left(processing_date,locate('/',processing_date)-1)) = '"+Validations.convertYMD(trn_date) +"' and ac_type='"+ln_acty+"' and ac_no="+ln_acno+" ");
				
			if(!rs.next()){
				System.out.println("Doing Posting");
				//postRecoveryDetails(String ac_type,int ac_no,String date,int category,int flag,String user_id,String user_tml) throws RemoteException;
				postRecoveryDetails(ln_acty,ln_acno,trn_date,sub_category,1,userid,terminal); // We are calling this function because to update the LoanRecoveryDetail table with current calculated values of interest,penal_interest,other_charges,loan_balance for the current date
			}
			rs.close();
			rs=lnstmt.executeQuery("select * from LoanRecoveryDetail where ac_type='"+ln_acty+"' and ac_no="+ln_acno+" and concat(right(processing_date,4),'-',mid(processing_date,locate('/',processing_date)+1, (locate('/',processing_date,4)-locate('/',processing_date)-1)),'-',left(processing_date,locate('/',processing_date)-1))='"+Validations.convertYMD(trn_date) +"'");
			
			System.out.println("after LoanRecoveryDetail posting");
			if(rs.next()){
				interest_amt = rs.getDouble("int_amt");
				penal_interest = rs.getDouble("penal_amt");
				other_charges = rs.getDouble("other_charges");
				loan_balance = rs.getDouble("loan_balance");
			}
				
			extra_amt = getLastRecoveryExtraInt(ln_acty,ln_acno,trn_date); // to get the extra amt
				
			interest_amt = interest_amt + extra_amt; // we are deleting it because in the LoanRecoveryDetail table the interest amt is calculated by subtracting it with the extra_int and here we need interest with extra_int we are adding it here again
			    
			lnobj = new LoanTransactionObject();
			lnobj.setAccType(ln_acty);
			lnobj.setAccNo(ln_acno);
			lnobj.setLoanBalance(loan_balance);
			lnobj.setOtherAmount(other_charges);
			lnobj.setPenaltyAmount(penal_interest);
			lnobj.setExtraIntAmount(extra_amt);
			lnobj.setInterestPayable(interest_amt);
			lnobj.setInterestRate(int_rate);
				
			LoanTransactionObject loan_trn = null;
			
			if(!ln_acty.startsWith("1008")) 
			{
				loan_trn=getPrincipalOutstandings(ln_acty,ln_acno,trn_date);
			    if(loan_trn != null){
			    	lnobj.setPrincipalBalance(loan_trn.getPrincipalBalance());//Principle OD
			    	lnobj.setPrincipalPayable(loan_trn.getPrincipalPayable());// Current Install
			    	lnobj.setPrincipalPaid(loan_trn.getPrincipalPaid());// Advance payment
			    	}
			    else{
			    	lnobj.setPrincipalBalance(0);//Principle OD
			    	lnobj.setPrincipalPayable(0);// Current Install
			    	lnobj.setPrincipalPaid(0);;// Advance payment
			    	}
			}
			else
			{
				lnobj.setPrincipalBalance(0.0);
				lnobj.setPrincipalPayable(0.0);// Current Install
				lnobj.setPrincipalPaid(0.0);
			}
				
			System.out.println("finishing query on loan status");
		}catch(Exception exception){
			//file_logger.info(exception.getMessage());
			lnobj = null;
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}		
		return lnobj;
	}
	
	
	public int verifyLoanRecovery(LoanTransactionObject lnobj) throws RecordsNotVerifiedException 
	{	
		Connection conn=null;
		try
		{
			String user_date=null,user_id=null,user_tml=null;
			conn=getConnection();
			CommonLocalHome commonLocalHome = (CommonLocalHome) getInitialContext().lookup("COMMONLOCALWEB");
			CommonLocal commonLocal = commonLocalHome.create();
			ResultSet rs=null,ret=null;
			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			Statement stupdate = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = st.executeQuery("select de_tml,de_user,de_date from LoanTransaction where ref_no="+lnobj.getReferenceNo()+" and ac_type like '101%' and trn_mode='T'");			
			if(rs.next()){
				
				user_date=rs.getString("de_date");
				user_id=rs.getString("de_user");
				user_tml=rs.getString("de_tml");
				
				System.out.println(" testing ************************* 1");
			}
			rs.close();
			
			System.out.println(" testing ************************* 2");
			int delete_check=0;
			delete_check=st.executeUpdate("delete from  LoanTransaction where ref_no="+lnobj.getReferenceNo()+" and ac_type like '101%' and trn_mode='T'");		
			if(delete_check<=0)
			{
				System.out.println(" testing ************************* 3");
				sessionContext.setRollbackOnly();
				throw new RecordsNotVerifiedException();
			}
			/*stupdate.executeUpdate("update LoanTransaction set ve_tml='"+lnobj.uv.getUserTml()+"',ve_user='"+lnobj.uv.getUserId()+"',ve_date='"+getSysDate()+" "+getSysTime()+"' where ref_no="+lnobj.getReferenceNo());
			stupdate.executeUpdate("update LoanMaster set int_upto_date='"+rs.getString("int_date")+"',lst_trn_date='"+getSysDate()+"',lst_tr_seq="+rs.getInt(1)+" where ac_no="+lnobj.getAccNo()+" and ac_type='"+lnobj.getAccType()+"'");	*/
			/*if(rs.getDouble("pr_bal")==0)
				stupdate.executeUpdate("update LoanMaster set close_date='"+getSysDate()+"' where ac_no="+lnobj.getAccNo()+" and ac_type='"+lnobj.getAccType()+"'");*/				
			
			System.out.println(" testing ************************* 4");

			LoanTransactionObject loan_trn = new LoanTransactionObject();
			
			loan_trn.setAccType(lnobj.getAccType());
			loan_trn.setAccNo(lnobj.getAccNo());
			loan_trn.setTransactionDate(lnobj.getTransactionDate());
			loan_trn.setTranType("R");
			loan_trn.setTransactionAmount(lnobj.getTransactionAmount());
			loan_trn.setTranMode("T");
			loan_trn.setTranSou(lnobj.uv.getUserTml());
			loan_trn.setReferenceNo(lnobj.getReferenceNo());
			loan_trn.setTranNarration(lnobj.getTranType()+" "+lnobj.getTranSequence());
			loan_trn.setCdind("C");
			loan_trn.uv.setUserTml(user_tml);
			loan_trn.uv.setUserId(user_id);
			loan_trn.uv.setUserDate(user_date);
			loan_trn.uv.setVerDate(lnobj.uv.getUserDate()); // here userdate is updated to veDate bcoz in the client side the ver_date is updated in the user_date method of the loanTransaction object and same is the case for ver_tml and ver_id 
			loan_trn.uv.setVerId(lnobj.uv.getUserId());
			loan_trn.uv.setVerTml(lnobj.uv.getUserTml());
			
			Object trn_seq_obj=null;
			try{
				trn_seq_obj=recoverLoanAccount(loan_trn);
			}catch(RecordNotUpdatedException rec){
				System.out.println(" testing ************************* 5");
				rec.printStackTrace();
				sessionContext.setRollbackOnly();
			}
			
			System.out.println(" testing ************************* 6");

			if(trn_seq_obj!=null)
			{
				AccountTransObject am = new AccountTransObject();
				am.setAccType(lnobj.getTranType());
				am.setAccNo(lnobj.getTranSequence());
				am.setTransType("P");
				System.out.println(" @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ trn_date :"+lnobj.getTransactionDate());
				am.setTransAmount(lnobj.getTransactionAmount());
				am.setTransDate(lnobj.getTransactionDate());
				am.setTransMode("T");
				am.setTransSource(lnobj.uv.getUserTml());
				am.setCdInd("D");
				am.setChqDDNo(0);
				am.setChqDDDate("");
				am.setTransNarr(lnobj.getAccType()+" "+lnobj.getAccNo());
				am.setRef_No(lnobj.getReferenceNo());
				am.setPayeeName("");
				am.setCloseBal(lnobj.getTransactionAmount());
				am.setLedgerPage(0);
				am.uv.setUserTml(user_tml);
				am.uv.setUserId(user_id);
				am.uv.setUserDate(user_date);
				am.uv.setVerTml(lnobj.uv.getUserTml());
				am.uv.setVerId(lnobj.uv.getUserId());
				am.uv.setVerDate(lnobj.uv.getUserDate());
				System.out.println(" testing ************************* 8");
				commonLocal.storeAccountTransaction(am);
				System.out.println(" testing ************************* 9");
			}
			else
			{
				System.out.println(" testing ************************* 10");
				sessionContext.setRollbackOnly();
			}
			
		}catch(SQLException exception){
			exception.printStackTrace();
			throw new RecordsNotVerifiedException();
		} catch (CreateException e) {
			e.printStackTrace();
			throw new RecordsNotVerifiedException();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new RecordsNotVerifiedException();
		} 
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}		
		return 1;
	}
	
	public LoanTransactionObject getTransferVoucherNo(int trf_scroll_no,String cliendate) 
	{
		LoanTransactionObject loanTransactionObject = null;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery("select * from LoanTransaction where ref_no="+trf_scroll_no+" and ac_type like '101%' and trn_mode='T' and trn_date='"+cliendate+"'and ve_tml is null ;");
			if(rs.next()) {
				loanTransactionObject = new LoanTransactionObject();
				loanTransactionObject.setAccType(rs.getString("ac_type"));
				loanTransactionObject.setAccNo(rs.getInt("ac_no"));
				loanTransactionObject.setTransactionAmount(rs.getDouble("trn_amt"));
				loanTransactionObject.setTransactionDate(rs.getString("trn_date"));
				loanTransactionObject.setOtherAmount(rs.getDouble("other_amt"));
				loanTransactionObject.setPenaltyAmount(rs.getDouble("penal_amt"));
				loanTransactionObject.setInterestPayable(rs.getDouble("int_amt")+rs.getDouble("extra_int"));
				loanTransactionObject.setPrincipalPayable(rs.getDouble("pr_amt"));		
				loanTransactionObject.setTranNarration(rs.getString("trn_narr"));
				loanTransactionObject.uv.setVerTml(rs.getString("ve_tml"));
			}			
		}catch(Exception exception){
			exception.printStackTrace();
			loanTransactionObject = null;
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return loanTransactionObject;		
	}
	
	public int deleteLoanTransactionRecovery(int trf_voucher_no,String actype,int acno) throws RecordsNotDeletedException 
	{
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stupdate = conn.createStatement();
			stupdate.executeUpdate("delete from LoanTransaction where ref_no="+trf_voucher_no+" and ac_type='"+actype+"' and ac_no="+acno);			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new RecordsNotDeletedException();
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}		
		return 1;
	}
	
	public void newstartNPAProcessing(String process_date,String npa_towards,int table,int ac_type,int start_ac_no,int end_ac_no,String uid,String utml ) 
	{	
		if(String.valueOf(ac_type).startsWith("1010") || String.valueOf(ac_type).startsWith("0") )
		{
			Connection conn=null;
			ResultSet rs=null,rs_asset=null, rs_master=null;
			Statement stmt=null,stmt_asset=null;
			PreparedStatement pstmt=null;
			String prn_overdues[]=null,prn_overdue_from=null,int_upto_date=null;
			double prn_overdue_amt=0.0,int_overdue_amt=0.0,prn_prov_amt=0.0,int_prov_amt=0.0,prov_amt=0.0;
			double loan_balance=0.0;
			int prn_overdue_days=0,prn_overdue_period=0,int_overdue_days=0,int_overdue_period=0,prn_asset_code=0,int_asset_code=0,asset_code=0;
			String npa_towards_indicator=null;
			String npa_master_date=null,table_name=null;
			
			try{
				conn=getConnection();
				stmt=conn.createStatement();
				stmt_asset=conn.createStatement();
				pstmt = conn.prepareStatement("insert into NPATable values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,null)");
				
				if( String.valueOf(ac_type).startsWith("0"))
					rs = stmt.executeQuery("select distinct ac_type,ac_no ,sub_category from LoanMaster as lm ,CustomerMaster as cm where concat(right(sanc_date,4),'-',mid(sanc_date,locate('/',sanc_date)+1, (locate('/',sanc_date,4)-locate('/',sanc_date)-1)),'-',left(sanc_date,locate('/',sanc_date)-1))<='"+Validations.convertYMD(process_date)+"' and (close_date is null or  concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))>='"+Validations.convertYMD(process_date)+"') and lm.cid=cm.cid and lm.ve_tml is not null and loan_sanc='Y' and sanc_ver like 'Y' and ac_type like '1010%' order by ac_type,ac_no");
				else
					rs = stmt.executeQuery("select distinct ac_type,ac_no ,sub_category from LoanMaster as lm ,CustomerMaster as cm where concat(right(sanc_date,4),'-',mid(sanc_date,locate('/',sanc_date)+1, (locate('/',sanc_date,4)-locate('/',sanc_date)-1)),'-',left(sanc_date,locate('/',sanc_date)-1))<='"+Validations.convertYMD(process_date)+"' and (close_date is null or  concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))>='"+Validations.convertYMD(process_date)+"') and lm.cid=cm.cid and lm.ve_tml is not null and loan_sanc='Y' and sanc_ver like 'Y' and ac_type like '"+ac_type+"' and ac_no between "+start_ac_no+" and "+end_ac_no+" order by ac_type,ac_no");
				
				while(rs.next()){
					
					// Principal Overdue
					prn_overdues = null;
					prn_overdues = getPrnDueDateAmountForCurrentDate(rs.getString("ac_type"),rs.getInt("ac_no"),process_date);
					if(prn_overdues[0].equalsIgnoreCase("0")){
						prn_overdue_from=process_date;
						prn_overdue_amt=0.0;
						prn_overdue_period=0;
						prn_overdue_days=0;
					}
					else{
						prn_overdue_amt=Double.parseDouble(prn_overdues[0].trim());
						prn_overdue_from=prn_overdues[1];
						prn_overdue_days=Validations.dayCompare(prn_overdue_from,process_date);
						prn_overdue_period=Validations.getDefaultMonths(prn_overdue_from,process_date);
					}
					
					// int Overdue
					int_upto_date = getIntUpToDateOnCurrentDate(rs.getString("ac_type"),rs.getInt("ac_no"),process_date);
					
					if(int_upto_date!=null){
						int_overdue_amt=calculateInterestForNPA(rs.getString("ac_type"),rs.getInt("ac_no"),process_date,int_upto_date,rs.getInt("sub_category"));
						int_overdue_days=Validations.dayCompare(int_upto_date,process_date);
						int_overdue_period=Validations.getDefaultMonths(int_upto_date,process_date);
					}
					
					loan_balance = getCurrentDayClosingBalance(rs.getString("ac_type"),rs.getInt("ac_no"),process_date); // loan balance on the processing date
					
					if(table==90)
						table_name="NPAClassification_90";
					else
						table_name="NPAClassification_180";
					// Principal asset code and prov amt
					
					rs_asset = stmt_asset.executeQuery("select asset_code,prov_perc from "+table_name+" where loan_type='"+rs.getString("ac_type")+"' and "+prn_overdue_days+" between days_limit_fr and days_limit_to");
					if(!rs_asset.next()){
						rs_asset = stmt_asset.executeQuery("select asset_code,prov_perc from "+table_name+" where loan_type='"+rs.getString("ac_type")+"' and "+prn_overdue_period+" between mths_limit_fr and mths_limit_to");
						if(!rs_asset.next()){
							rs_asset = stmt_asset.executeQuery("select asset_code,prov_perc from "+table_name+" where loan_type='"+rs.getString("ac_type")+"' and days_limit_to is null and mths_limit_fr is null");
							if(rs_asset!=null && rs_asset.next()){
								prn_asset_code = rs_asset.getInt("asset_code");
								prn_prov_amt = (loan_balance*rs_asset.getDouble("prov_perc")/100);
							}
						}
						else{
							prn_asset_code = rs_asset.getInt("asset_code");
							prn_prov_amt = (loan_balance*rs_asset.getDouble("prov_perc")/100);
						}
					}
					else {
						prn_asset_code = rs_asset.getInt("asset_code");
						prn_prov_amt = (loan_balance*rs_asset.getDouble("prov_perc")/100);
					}
					
					// Int asset code and prov amt
					rs_asset=null;
					
					rs_asset = stmt_asset.executeQuery("select asset_code,prov_perc from "+table_name+" where loan_type='"+rs.getString("ac_type")+"' and "+int_overdue_days+" between days_limit_fr and days_limit_to");
					if(!rs_asset.next()){
						rs_asset = stmt_asset.executeQuery("select asset_code,prov_perc from "+table_name+" where loan_type='"+rs.getString("ac_type")+"' and "+int_overdue_period+" between mths_limit_fr and mths_limit_to");
						if(!rs_asset.next()){
							rs_asset = stmt_asset.executeQuery("select asset_code,prov_perc from "+table_name+" where loan_type='"+rs.getString("ac_type")+"' and days_limit_to is null and mths_limit_fr is null");
							if(rs_asset!=null && rs_asset.next()){
								int_asset_code = rs_asset.getInt("asset_code");
								int_prov_amt = (loan_balance*rs_asset.getDouble("prov_perc")/100);
							}
						}
						else{
							int_asset_code = rs_asset.getInt("asset_code");
							int_prov_amt = (loan_balance*rs_asset.getDouble("prov_perc")/100);
						}
					}
					else{
						int_asset_code = rs_asset.getInt("asset_code");
						int_prov_amt = (loan_balance*rs_asset.getDouble("prov_perc")/100);
					}
					
					if(npa_towards.equalsIgnoreCase("P")){
						asset_code = prn_asset_code;
						prov_amt = prn_prov_amt;
						npa_towards_indicator ="P";
					}
					else if(npa_towards.equalsIgnoreCase("I")){
						asset_code = int_asset_code;
						prov_amt = int_prov_amt;
						npa_towards_indicator ="I";
					}
					else if(npa_towards.equalsIgnoreCase("PI")){
						if(prn_overdue_days >= int_overdue_days){
							asset_code = prn_asset_code;
							prov_amt = prn_prov_amt;
							npa_towards_indicator ="P";
						}
						else{
							asset_code = int_asset_code;
							prov_amt = int_prov_amt;
							npa_towards_indicator ="I";
						}
					}

					rs_asset = stmt_asset.executeQuery("select asset_code,npa_towards,prov_amt from NPATable where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and days="+table+" and concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1, (locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(process_date)+"' ");
					if(rs_asset.next()){
						if(rs_asset.getInt("asset_code")>asset_code){
							asset_code=rs_asset.getInt("asset_code");
							prov_amt=rs_asset.getDouble("prov_amt");
							npa_towards_indicator=rs_asset.getString("npa_towards");
						}else if(rs_asset.getInt("asset_code")==asset_code && rs_asset.getString("npa_towards")!=null && rs_asset.getString("npa_towards").equalsIgnoreCase("P")){
							asset_code=rs_asset.getInt("asset_code");
							prov_amt=rs_asset.getDouble("prov_amt");
							npa_towards_indicator=rs_asset.getString("npa_towards");
						}
						
						stmt_asset.executeUpdate("delete from NPATable where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and days="+table+" and concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1, (locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(process_date)+"' ");
					}
					
					pstmt.setString(1,process_date);
					pstmt.setInt(2,table);
					pstmt.setString(3,rs.getString("ac_type"));
					pstmt.setInt(4,rs.getInt("ac_no"));
					pstmt.setInt(5,asset_code);
					pstmt.setString(6,npa_towards_indicator);
					pstmt.setString(7,prn_overdue_from);
					pstmt.setDouble(8,prn_overdue_amt);
					pstmt.setInt(9,prn_overdue_period);
					pstmt.setDouble(10,int_overdue_amt);
					pstmt.setString(11,int_upto_date);
					pstmt.setInt(12,int_overdue_period);
					pstmt.setDouble(13,loan_balance);
					pstmt.setDouble(14,prov_amt);
					
					if(pstmt.executeUpdate()>0){
						 rs_master = stmt_asset.executeQuery("select npa_date from LoanMaster where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" ");
						if(rs_master.next()){
							npa_master_date=rs_master.getString("npa_date");
							if(npa_master_date==null || Validations.dayCompare(process_date,npa_master_date)<=0){
								stmt_asset.executeUpdate("update LoanMaster set npa_date='"+process_date+"' ,npa_stg='"+npa_towards_indicator+" "+asset_code+"' where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" ");
							}
						}
					}
				}
			}catch(SQLException exception){
				
				sessionContext.setRollbackOnly();
			} 
			finally{
				try{		    	
					conn.close();
				}catch(Exception exception){exception.printStackTrace();}
			}	
		} 
	}
	
	/**
	 * This Method is used to calculate NPA Amount for OD/CC Accounts.
	 * After Calculating NPA Amounts , it will write record into NPATable. 
	 * @param pro_date - This NPA Processing Date
	 * @param pro_type - Interest wise or Principlewise. 1 is for Principal wise,2 is for Interest wise & 3 is for Both Interest & Principal wise
	 * @param days - This is to NPA Process for How many Days,i.e 90Days or 180 days
	 * @param ln_type - Loan Type 0 if the combo value is 'All', or corresponding modulecode for 'Selected' value combo. 
	 * @param ln_start_no - 0 if it is 'All',Otherwise Account number will be taken from the application.
	 * @param ln_end_no - 0 if it is 'All' Otherwise Account number will be taken from the application.
	 * @param userid - Login User
	 * @param usertml - Login Terminal
	 * @throws RemoteException 
	 * @throws RecordsNotFoundException - If records are not available in master table for the particular condition.
	 */
	public void startODCCNPAProcessing(String pro_date,int pro_type,int days,int ln_type,int ln_start_no,int ln_end_no,String userid, String usertml)throws RemoteException,RecordsNotFoundException
	{
		System.out.println("Satrting NPA Calculation for ODCC..");
		System.out.println("NPA Type:  "+pro_type);
		System.out.println("Loan Type= "+ln_type);
		System.out.println("Loan Start No: "+ln_start_no);
		System.out.println("Loan_end_no: "+ln_end_no);
		String pri_ode_period=null,intcalc_str=null,npa_date=null,npa_category=null,lst_trn_date=null,limit_uptodt,trn_type,int_uptodt = null,lst_int_uptodt,odu_asset_desc = null,npa_stg;
		float lst_cl_bal=0;
		int  npa_start_day=0,no_of_days_nt=0,id=0,no_ofmonth=0,no_of_days_pw=0,no_of_days_iw=0,day=0,odu_asset_code=0,npa_code=0;
		double prov_amt=0,int_calc_amt=0;
		Connection conn=null;
		boolean npa_calculated=false;
		
		try
		{
			CommonLocalHome commonlocalhome=(CommonLocalHome) getInitialContext().lookup("COMMONLOCALWEB");
			CommonLocal commonlocal=commonlocalhome.create();
			
			FrontCounterLocalHome frontcounterlocalhome = (FrontCounterLocalHome) getInitialContext().lookup("FRONTCOUNTERLOCALWEB");
			FrontCounterLocal frontcounterlocal=frontcounterlocalhome.create();
			
			conn=getConnection();
			PreparedStatement pstmt;
			Statement st_mst,st_trn=null,st1_trn=null,st_npa,stmt1=null,stmt2=null,stmt3=null,stmt4=null,stmt5=null;
			ResultSet rs_mst,rs_trn,rs1_trn,rs_npa,rs1,rs2,rs3,rs4,rs5;
			st_mst=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			st_trn=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			st_npa=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			st1_trn=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt4=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt5=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt2=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			pstmt=conn.prepareStatement("insert into NPATable values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,null)");
			
			if(ln_start_no==0)
			{
				rs_mst=st_mst.executeQuery("select ac_type,ac_no,int_rate_type,int_rate,sanc_dt,sanc_amt,int_uptodt,limit_upto,period,last_tr_date from ODCCMaster where ac_type in(1014001,1015001) and ac_closedate is null order by 1,2");
				System.out.println("For all Accounts...");
			}
			else
			{
				System.out.println("Inside selected  Accounts");
				rs_mst=st_mst.executeQuery("select ac_type,ac_no,int_rate_type,int_rate,sanc_dt,sanc_amt,int_uptodt,limit_upto,period,last_tr_date from ODCCMaster where ac_type='"+ln_type+"' and ac_no between "+ln_start_no+" and "+ln_end_no+" and ac_closedate is null order by 1,2");
				System.out.println("1.....");
			}
			
			rs_mst.last();
			int int_no_rec=rs_mst.getRow();
			if(rs_mst.getRow()==0)
				throw new RecordsNotFoundException();
			
			System.out.println("2.....");
			
			rs_mst.beforeFirst();
			
			System.out.println("No of iter: "+int_no_rec);
			System.out.println("3.....");
			
			aa: for(int i=1;i<=int_no_rec;i++)
			{
				System.out.println("i Value: "+i);
				
				rs_mst.absolute(i);
				System.out.println("4......"+rs_mst.getString("ac_no"));
				limit_uptodt=rs_mst.getString("limit_upto");
				
				
				//To find the the NPA Period - if the customer did not do trnsaction for 6months 
				rs_trn=st_trn.executeQuery("select ot.* from ODCCTransaction ot,ODCCMaster om where ot.ac_type='"+rs_mst.getString("ac_type")+"' and ot.ac_no ="+rs_mst.getInt("ac_no")+" and ot.ac_type=om.ac_type and ot.ac_no=om.ac_no and ot.trn_type!='I' and ot.cl_bal<0  and concat(right(ot.trn_date,4),'-',mid(ot.trn_date,locate('/',ot.trn_date)+1, (locate('/',ot.trn_date,4)-locate('/',ot.trn_date)-1)),'-',left(ot.trn_date,locate('/',ot.trn_date)-1))<'"+Validations.convertYMD(pro_date)+"' order by trn_seq desc limit 1");
				if(rs_trn.next()!=false)
				{
					lst_trn_date=rs_trn.getString("trn_date");
					System.out.println("5....... i= "+i);
				}else
					continue aa ;
				
				System.out.println("6.....");
				
				//Closing Balance of Last Transaction..
				rs1_trn=st1_trn.executeQuery("select * from ODCCTransaction where ac_type='"+rs_mst.getString("ac_type")+"' and ac_no="+rs_mst.getInt("ac_no")+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<'"+Validations.convertYMD(pro_date)+"' order by trn_seq desc limit 1");
				if(rs1_trn.next())
				{ 
					System.out.println("7.......");
					
					lst_cl_bal=rs1_trn.getFloat("cl_bal");	
					if(rs1_trn.getString("int_uptodt")==null)
						continue aa;
					else
						int_uptodt=rs1_trn.getString("int_uptodt");	
				}
				
				System.out.println("8...");
				//Check the defined Period
				rs1=stmt1.executeQuery("select if(curdate()>concat(right(om.limit_upto,4),'-',mid(om.limit_upto,locate('/',om.limit_upto)+1, (locate('/',om.limit_upto,4)-locate('/',om.limit_upto)-1)),'-',left(om.limit_upto,locate('/',om.limit_upto)-1)),1,0) as id from ODCCMaster om where om.ac_type='"+rs_mst.getString("ac_type")+"' and om.ac_no="+rs_mst.getInt("ac_no"));
				if(rs1.next());
				id=rs1.getInt("id");
				
				/*
				 * 	````````//Changing Date Format of Lst Trn Dt...  
				 rs2=stmt2.executeQuery("select concat(right('"+lst_trn_date+"',4),'-',mid('"+lst_trn_date+"',locate('/','"+lst_trn_date+"')+1, (locate('/','"+lst_trn_date+"',4)-locate('/','"+lst_trn_date+"')-1)),'-',left('"+lst_trn_date+"',locate('/','"+lst_trn_date+"')-1)) as date"); 
				 if(rs2.next())
				 date=rs2.getString("date");
				 
				 //To Find No of months b/w NPA Date & Last Transaction Dt..
				  rs3=stmt3.executeQuery("select (year(curdate())-year('"+date+"'))*12+(month(curdate())-month('"+date+"'))-if(dayofmonth(curdate())<dayofmonth('"+date+"'),1,0) as months");
				  if(rs3.next())
				  no_ofmonth=rs3.getInt("months");*/
				//date=Validations.convertYMD(lst_trn_date);
				//no_ofmonth=calculateNoOfMonths(lst_trn_date);
				/*no_ofmonth_iw=calculateNoOfMonths(commonlocal.getFutureDayDate(int_uptodt,1));
				 System.out.println("Before 9... NO OF MONTHS = "+no_ofmonth_iw);*/
				
				System.out.println("9.........");
				no_of_days_pw=commonlocal.getDaysFromTwoDate(limit_uptodt,pro_date);
				no_of_days_nt=commonlocal.getDaysFromTwoDate(lst_trn_date,pro_date); 
				
				lst_int_uptodt=commonlocal.getFutureDayDate(int_uptodt,1);
				no_of_days_iw=commonlocal.getDaysFromTwoDate(lst_int_uptodt,pro_date);
				
				/*To find the Standard category Accounts
				 if(no_of_days_pw<0)
				 no_of_days_pw=0;
				 else if(no_of_days_nt<0) //???Check...
				 no_of_days_nt=0;
				 else if(no_of_days_iw<0)
				 no_of_days_iw=0;*/
				
				/*rs4=stmt4.executeQuery("select DATEDIFF(curdate(),'"+date+"')");
				 if(rs4.next())
				 no_of_days_pw=rs4.getInt(1);
				 
				 lst_int_uptodt=Validations.convertYMD(commonlocal.getFutureDayDate(int_uptodt,1));
				 rs5=stmt5.executeQuery("select DATEDIFF(curdate(),'"+lst_int_uptodt+"')");
				 if(rs5.next())
				 no_of_days_iw=rs5.getInt(1);*/
				
				System.out.println("10...");
				//Principal due.. 
				System.out.println("PRO_TYPE = "+pro_type); 
				System.out.println("No Of days_pw: "+no_of_days_pw);
				System.out.println("No of Days_nt: "+no_of_days_nt);
				System.out.println("No of Days_iw: "+no_of_days_iw);
				if(pro_type==1 )
				{
					if(id==1 )
					{
						if(no_of_days_pw>no_of_days_nt)
							day=no_of_days_pw;
						else
							day=no_of_days_nt;
						
						if(day<0)
							day=0;
						
						
						System.out.println("Inside Principal wise Calculation....");	    
						System.out.println("No of days_pw: "+day);
						if(days==90)
							rs_npa=st_npa.executeQuery("select limit_fr,asset_code,asset_desc,prov_perc from NPAClassification_90 where loan_type='"+rs_mst.getString("ac_type")+"' and "+day+" between limit_fr and limit_to");
						else
							rs_npa=st_npa.executeQuery("select limit_fr,asset_code,asset_desc,prov_perc from NPAClassification_180 where loan_type='"+rs_mst.getString("ac_type")+"' and "+day+" between limit_fr and limit_to");
						rs_npa.next();
						odu_asset_code=rs_npa.getInt("asset_code");
						odu_asset_desc=rs_npa.getString("asset_desc");
						npa_start_day=rs_npa.getInt("limit_fr");
						
						System.out.println("asset code: "+odu_asset_code);
						System.out.println("Prov Amount: "+rs_npa.getDouble("prov_perc"));
						
						prov_amt=(lst_cl_bal*rs_npa.getDouble("prov_perc"))/100;
						rs_npa.close(); 
						System.out.println("Prov-Amt:  "+prov_amt);
						
						//NPA DATE 
						if(no_of_days_pw>no_of_days_nt)
							rs2=stmt2.executeQuery("select date_format(date_add('"+Validations.convertYMD(limit_uptodt)+"',interval "+npa_start_day+" day),'%d/%m/%Y') as npa_date");
						else
							rs2=stmt2.executeQuery("select date_format(date_add('"+Validations.convertYMD(lst_trn_date)+"',interval "+npa_start_day+" day),'%d/%m/%Y') as npa_date");	
						
						rs2.next();
						npa_date=rs2.getString(1);
						System.out.println("NPA DATE: "+npa_date);
						
						if(no_of_days_pw>no_of_days_nt)
							npa_category="Principal";
						else
							npa_category="No Transaction";
						
						npa_calculated=true;
					}
				}else if(pro_type==2)
				{
					System.out.println("Inside 'Interest wise' Calculation......");
					System.out.println("No Of Days_IW: "+no_of_days_iw);
					
					if(no_of_days_iw<0)
						no_of_days_iw=0;
					
					if(days==90)
						rs_npa=st_npa.executeQuery("select limit_fr,asset_code,asset_desc,prov_perc from NPAClassification_90 where loan_type='"+rs_mst.getString("ac_type")+"' and "+no_of_days_iw+" between limit_fr and limit_to");
					else
						rs_npa=st_npa.executeQuery("select limit_fr,asset_code,asset_desc,prov_perc from NPAClassification_180 where loan_type='"+rs_mst.getString("ac_type")+"' and "+no_of_days_iw+" between limit_fr and limit_to");
					
					rs_npa.next();
					odu_asset_code=rs_npa.getInt("asset_code");
					odu_asset_desc=rs_npa.getString("asset_desc");
					npa_start_day=rs_npa.getInt("limit_fr");
					
					System.out.println("asset code: "+odu_asset_code);
					System.out.println("Prov Amount: "+rs_npa.getDouble("prov_perc"));
					
					prov_amt=(lst_cl_bal*rs_npa.getDouble("prov_perc"))/100;
					rs_npa.close();
					System.out.println("Interest Prov Amnt: "+prov_amt+"for Acc No: "+rs_mst.getString("ac_no"));
					
					//NPA DATE
					rs2=stmt2.executeQuery("select date_format(date_add('"+Validations.convertYMD(int_uptodt)+"',interval "+npa_start_day+" day),'%d/%m/%Y') as npa_date");	
					rs2.next();
					npa_date=rs2.getString(1);
					System.out.println("NPA DATE: "+npa_date);
					
					npa_category="Interest";
					npa_calculated=true;
					
				}else if(pro_type==3)
				{
					System.out.println("Both Prin & Interest wise.......");
					if(no_of_days_pw>no_of_days_iw && no_of_days_pw>no_of_days_nt)
						day=no_of_days_pw;
					else if(no_of_days_iw>no_of_days_nt)
						day=no_of_days_iw;
					else
						day=no_of_days_nt;
					
					if(day<0)
						day=0;
					
					
					System.out.println("No Of Days:  "+day);
					if(days==90)
						rs_npa=st_npa.executeQuery("select limit_fr,asset_code,asset_desc,prov_perc from NPAClassification_90 where loan_type='"+rs_mst.getString("ac_type")+"' and "+day+" between limit_fr and limit_to");
					else
						rs_npa=st_npa.executeQuery("select limit_fr,asset_code,asset_desc,prov_perc from NPAClassification_180 where loan_type='"+rs_mst.getString("ac_type")+"' and "+day+" between limit_fr and limit_to");
					rs_npa.next();
					odu_asset_code=rs_npa.getInt("asset_code");
					odu_asset_desc=rs_npa.getString("asset_desc");
					npa_start_day=rs_npa.getInt("limit_fr");
					
					prov_amt=(lst_cl_bal*rs_npa.getDouble("prov_perc"))/100;
					rs_npa.close();
					
					//NPA DATE 
					if(no_of_days_pw>no_of_days_nt)
						rs2=stmt2.executeQuery("select date_format(date_add('"+Validations.convertYMD(limit_uptodt)+"',interval "+npa_start_day+" day),'%d/%m/%Y') as npa_date");
					else if(no_of_days_iw>no_of_days_nt)
						rs2=stmt2.executeQuery("select date_format(date_add('"+Validations.convertYMD(int_uptodt)+"',interval "+npa_start_day+" day),'%d/%m/%Y') as npa_date");	
					else
						rs2=stmt2.executeQuery("select date_format(date_add('"+Validations.convertYMD(lst_trn_date)+"',interval "+npa_start_day+" day),'%d/%m/%Y') as npa_date");	
					
					rs2.next();
					npa_date=rs2.getString(1);
					System.out.println("NPA DATE: "+npa_date);
					
					if(no_of_days_pw>no_of_days_iw && no_of_days_pw>no_of_days_nt)
						npa_category="Principal";
					else if(no_of_days_iw>no_of_days_nt)
						npa_category="Interest";
					else
						npa_category="No Transaction";
					npa_calculated=true;
					
				}
				
				//npa Updation
				
				if(npa_calculated==true)
				{
					npa_code=odu_asset_code;
					npa_stg=odu_asset_desc;
					
					System.out.println("Updating Master.......");
					System.out.println("NPA Date: "+pro_date);
					System.out.println("NPA Stag:  "+npa_stg);
					
					pri_ode_period=calculateperiod(limit_uptodt,pro_date);
					System.out.println("Principal Over due Period: "+pri_ode_period);
					
					
					//intcalc_str=frontcounterlocal.ODCCInterestCalc(rs_mst.getString("ac_type"),rs_mst.getInt("ac_no"),0,userid,usertml,1);
					
					System.out.println("Calculated Interest Amount: "+intcalc_str);
					int_calc_amt=Double.parseDouble(intcalc_str.substring(intcalc_str.lastIndexOf(" ")+1,intcalc_str.length()-1));
					
					
					if(st_npa.executeUpdate("update ODCCMaster set npa_dt='"+npa_date+"',npa_stg='"+npa_stg+"',npa_base='"+npa_category+"' where ac_type='"+rs_mst.getString("ac_type")+"' and ac_no="+rs_mst.getInt("ac_no"))==1)
						System.out.println("updated ODCCMaster.....");
					else
						System.out.println("Not Updated ODCCMaster....");
					if(st_npa.executeUpdate("delete from NPATable where ac_type='"+rs_mst.getString("ac_type")+"' and ac_no="+rs_mst.getInt("ac_no")+" and npa_pro_date='"+pro_date+"' and days="+days)==1)
						System.out.println("Deleted the existing rows from NPATable");
					else
						System.out.println("Not deleted from NPATable....");
					System.out.println("11...");
					pstmt.setString(1,pro_date); 
					pstmt.setInt(2, days);
					pstmt.setString(3,rs_mst.getString("ac_type"));
					pstmt.setInt(4,rs_mst.getInt("ac_no"));
					pstmt.setInt(5,npa_code);
					pstmt.setString(6,limit_uptodt);
					pstmt.setDouble(7,lst_cl_bal); // principal Over Due Amount
					pstmt.setString(8,pri_ode_period); //Prin Ov due Period
					pstmt.setDouble(9,int_calc_amt);//Interest Overdue amount
					pstmt.setString(10,int_uptodt);
					pstmt.setString(11,rs_mst.getString("last_tr_date"));
					pstmt.setDouble(12,lst_cl_bal);
					pstmt.setDouble(13,prov_amt);
					pstmt.setString(14,npa_date);
					pstmt.executeUpdate();  	 
					
					System.out.println("Inserted into NPA Table....");
					PreparedStatement pss = conn.prepareStatement("commit");
					pss.executeUpdate();
					
				}
				
			}
			
		}catch(SQLException sql){
			sql.printStackTrace();
			sessionContext.setRollbackOnly();}
		catch(NamingException ne){
			ne.printStackTrace();
			throw new RecordsNotFoundException();}
		catch(CreateException ce){
			ce.printStackTrace();
			throw new RecordsNotFoundException();}
		finally{
			try{
				conn.close();
			}catch(Exception ex){ex.printStackTrace();}
		}
	}
	
	/**
	 * This method is used to calculate Period(in terms of Months & Days) between limit upto Date & NPA Processing Date for OD/CC Accounts
	 * @param first_date  - limit upto date of the OD/CC Acc Loan.i.e Defined Period of the Loan
	 * @param second_date - NPA Processing Date
	 * @return Period in the form of  Months & Days
	 */
	String calculateperiod(String first_date,String second_date)
	{
		System.out.println("Calculating No.Of Months....");
		System.out.println("from Date: "+first_date);
		System.out.println("Second Date: "+second_date);
		
		Connection connection=null;
		
		String period=null;
		int no_ofmonths=0,days=0;
		try{
			Statement stmt2,stmt3;
			ResultSet rs2,rs3;
			connection=getConnection();
			stmt2=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt3=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//Changing Date Format of Lst Trn Dt...  
			rs2=stmt2.executeQuery("select day('"+Validations.convertYMD(second_date)+"')- day('"+Validations.convertYMD(first_date)+"') as days"); 
			if(rs2.next())
				days=rs2.getInt("days");
			if(days<0)
				days=days*-1;
			
			System.out.println("Days: "+days);
			//To Find No of months b/w NPA Date & Last Transaction Dt..
			rs3=stmt3.executeQuery("select (year('"+Validations.convertYMD(second_date)+"')-year('"+Validations.convertYMD(first_date)+"'))*12+(month('"+Validations.convertYMD(second_date)+"')-month('"+Validations.convertYMD(first_date)+"'))-if(dayofmonth('"+Validations.convertYMD(second_date)+"')<dayofmonth('"+Validations.convertYMD(first_date)+"'),1,0) as months");
			if(rs3.next())
				no_ofmonths=rs3.getInt("months");
			System.out.println("No of Months: "+no_ofmonths);
			
			period=no_ofmonths+" "+"Months"+" "+days+" "+"Days";
			System.out.println("Period: "+period);
			System.out.println("Returning from Calculate no of months...");
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				connection.close();
			}catch(SQLException se){se.printStackTrace();}
		}
		return period;
	}
	
	
	
	public String[][] getNPACode() 
	{		
		String code_arr[][]=null;
		Connection conn=null;
		try
		{
			conn=getConnection();			
			ResultSet rs_npa;
			Statement st_npa;
			st_npa = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs_npa = st_npa.executeQuery("select distinct asset_desc,asset_code  from NPAClassification_90 union select distinct asset_desc,asset_code from NPAClassification_180 order by asset_code");
			rs_npa.last();
			code_arr = new String[rs_npa.getRow()][2];
			rs_npa.beforeFirst();
			int i=0;
			while(rs_npa.next()){
				code_arr[i][0] = rs_npa.getString("asset_code"); 
				code_arr[i][1] = rs_npa.getString("asset_desc"); 
				i++;
			}
		}catch(SQLException exception){
			exception.printStackTrace();
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}		
		return code_arr;
	}
	
	public Vector getNPAProcessedDate(int days) 
	{
		Vector pro_date = new Vector(1,1);		
		Connection conn=null;
		try
		{
			System.out.println("In getNPAProcessedDate()");
			conn=getConnection();		
			ResultSet rs_npa=null;
			Statement st_npa;
			st_npa = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			System.out.println("======"+days);
			if(days==180)
				rs_npa = st_npa.executeQuery("select distinct npa_pro_date from NPATable where  days=180");
			else if(days==90)
				rs_npa = st_npa.executeQuery("select distinct npa_pro_date from NPATable where  days=90");
			System.out.println("before while");
			while(rs_npa.next()){
				pro_date.add(rs_npa.getString("npa_pro_date"));
			}
		}catch(SQLException exception){
			exception.printStackTrace();
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return pro_date;
	}
	
	public int deleteNPAProcessedDate(String pro_date,int days) 
	{	
		Connection conn=null;
		try{
			conn=getConnection();
			Statement npa_de = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			npa_de.executeUpdate("delete from NPATable where npa_pro_date='"+pro_date+"' and days="+days);
			return 1;
		}catch(SQLException exception){
			exception.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}		
		return 0;
	}
	
	public LoanReportObject[][] getNPASummary(String pro_date,int days) throws RecordsNotFoundException 
	{
		LoanReportObject[][] lrobj =null;
		Connection conn=null;
		try
		{
			conn=getConnection();			
			int i=0,j=0;			
			ResultSet rs_npa=null,rs_mst=null;
			Statement st_npa=null,st_mst=null;
			st_npa = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			st_mst = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);		
			
			rs_mst = st_mst.executeQuery("select count(ac_no),sum(loan_balance),sum(pri_odue_amt),sum(prov_amt),moduledesc,ac_type from NPATable,Modules where concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1,(locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(pro_date)+"' and modulecode=ac_type and days="+days+" group by ac_type");
			rs_mst.last();			
			if(rs_mst.getRow()==0)
				throw new RecordsNotFoundException(); 
			lrobj = new LoanReportObject[rs_mst.getRow()][];
			rs_mst.beforeFirst();
			i=0;  
			while(rs_mst.next()){
				if(days==180)
					rs_npa = st_npa.executeQuery("select nt.asset_code,npa.asset_desc,count(nt.ac_no),sum(nt.loan_balance),npa.prov_perc,sum(nt.prov_amt) from NPATable nt,NPAClassification_180 npa where nt.ac_type='"+rs_mst.getString("ac_type")+"' and concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1,(locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(pro_date)+"' and nt.ac_type=npa.loan_type and nt.asset_code=npa.asset_code and days="+days+"  group by nt.asset_code order by nt.asset_code");
				else
					rs_npa = st_npa.executeQuery("select nt.asset_code,npa.asset_desc,count(nt.ac_no),sum(nt.loan_balance),npa.prov_perc,sum(nt.prov_amt) from NPATable nt,NPAClassification_90 npa where nt.ac_type='"+rs_mst.getString("ac_type")+"' and concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1,(locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(pro_date)+"' and nt.ac_type=npa.loan_type and nt.asset_code=npa.asset_code and days="+days+" group by nt.asset_code order by nt.asset_code");
				rs_npa.last();
				lrobj[i] = new LoanReportObject[rs_npa.getRow()];
				rs_npa.beforeFirst(); 
				j=0;
				while(rs_npa.next()){
					lrobj[i][j] = new LoanReportObject();
					lrobj[i][j].setName(rs_mst.getString("moduledesc"));
					lrobj[i][j].setAccNo(rs_mst.getInt(1));// total no of acs
					lrobj[i][j].setMaturityAmt(rs_mst.getDouble(2));//total bal o\s
					lrobj[i][j].setPrincipalPaid(rs_mst.getDouble(3));//total amt od
					lrobj[i][j].setDepositAmt(rs_mst.getDouble(4));// total prov amt
					lrobj[i][j].setDepositAccNo(rs_npa.getInt("nt.asset_code"));//asset code
					lrobj[i][j].setAccType(rs_npa.getString("npa.asset_desc"));//asset desc
					lrobj[i][j].setDepositDays(rs_npa.getInt(3));// total acs each asset code
					lrobj[i][j].setLoanBalance(rs_npa.getDouble(4));// total balance each asset code
					lrobj[i][j].setDepositIntRate(rs_npa.getDouble("npa.prov_perc"));//provision each asset code
					lrobj[i][j].setLoanIntRate(rs_npa.getDouble(6));// prov amt each asset code			        
					j++;
				}	 				
				i++;
			}		
		}catch(SQLException exception){
			exception.printStackTrace();		    
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}		
		return lrobj;  
	}
	
	public LoanReportObject[][] getNPAScheduleStatus(String pro_date,int asset_type,int asset_days,int int_npatype,String string_loan_type) throws RecordsNotFoundException 
	{
		LoanReportObject[][] lrobj =null;
		Connection conn=null;
		try
		{
			conn=getConnection();						
			int i=0,j=0;			
			ResultSet rs_npa=null,rs_mst=null;
			Statement st_npa=null,st_mst=null;
			st_npa = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			st_mst = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			/*
			 * Retrieve all 
			 */
			//select concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,lm.sanc_date,lm.sanc_amt,m.moduledesc,lm.ac_type,lm.ac_no,lm.npa_stg from LoanMaster lm,CustomerMaster cm,Modules m,NPATable npa where lm.cid =cm.cid and lm.ac_type=m.modulecode and concat(right(npa.npa_pro_date,4),'-',mid(npa.npa_pro_date,locate('/',npa.npa_pro_date)+1,(locate('/',npa.npa_pro_date,4)-locate('/',npa.npa_pro_date)-1)),'-',left(npa.npa_pro_date,locate('/',npa.npa_pro_date)-1))='"+Validations.convertYMD(pro_date)+"' and npa.ac_type=lm.ac_type and npa.ac_no=lm.ac_no and npa.loan_balance >= 0 group by lm.ac_type 
			if(int_npatype==1)
				if(asset_type==0)
					rs_mst = st_mst.executeQuery("select m.moduledesc,npa.ac_type from Modules m,NPATable npa where npa.ac_type=m.modulecode and concat(right(npa.npa_pro_date,4),'-',mid(npa.npa_pro_date,locate('/',npa.npa_pro_date)+1,(locate('/',npa.npa_pro_date,4)-locate('/',npa.npa_pro_date)-1)),'-',left(npa.npa_pro_date,locate('/',npa.npa_pro_date)-1))='"+Validations.convertYMD(pro_date)+"' and days="+asset_days+" group by npa.ac_type ");
				else
					rs_mst = st_mst.executeQuery("select m.moduledesc,npa.ac_type from Modules m,NPATable npa where npa.ac_type=m.modulecode and concat(right(npa.npa_pro_date,4),'-',mid(npa.npa_pro_date,locate('/',npa.npa_pro_date)+1,(locate('/',npa.npa_pro_date,4)-locate('/',npa.npa_pro_date)-1)),'-',left(npa.npa_pro_date,locate('/',npa.npa_pro_date)-1))='"+Validations.convertYMD(pro_date)+"' and days="+asset_days+" and asset_code="+asset_type+" group by npa.ac_type ");
			else
				if(asset_type==0)
					rs_mst = st_mst.executeQuery("select m.moduledesc,npa.ac_type from Modules m,NPATable npa where npa.ac_type=m.modulecode and concat(right(npa.npa_pro_date,4),'-',mid(npa.npa_pro_date,locate('/',npa.npa_pro_date)+1,(locate('/',npa.npa_pro_date,4)-locate('/',npa.npa_pro_date)-1)),'-',left(npa.npa_pro_date,locate('/',npa.npa_pro_date)-1))='"+Validations.convertYMD(pro_date)+"' and days="+asset_days+" and npa.ac_type='"+string_loan_type+"' group by npa.ac_type ");
				else
					rs_mst = st_mst.executeQuery("select m.moduledesc,npa.ac_type from Modules m,NPATable npa where npa.ac_type=m.modulecode and concat(right(npa.npa_pro_date,4),'-',mid(npa.npa_pro_date,locate('/',npa.npa_pro_date)+1,(locate('/',npa.npa_pro_date,4)-locate('/',npa.npa_pro_date)-1)),'-',left(npa.npa_pro_date,locate('/',npa.npa_pro_date)-1))='"+Validations.convertYMD(pro_date)+"' and days="+asset_days+" and asset_code="+asset_type+" and npa.ac_type='"+string_loan_type+"' group by npa.ac_type ");
			rs_mst.last();			
			if(rs_mst.getRow() == 0)
				throw new RecordsNotFoundException();			
			lrobj = new LoanReportObject[rs_mst.getRow()][];
			rs_mst.beforeFirst();
			i=0;
			while(rs_mst.next())
			{
				if(rs_mst.getString("npa.ac_type").startsWith("1010"))
				{
					if(asset_days==180)
						if(asset_type==0)						
							rs_npa = st_npa.executeQuery("select nt.*,npa.asset_code,npa.asset_desc,npa.prov_perc,concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,lm.sanc_date,lm.sanc_amt from NPATable nt,NPAClassification_180 npa,LoanMaster lm,CustomerMaster cm where nt.ac_type='"+rs_mst.getString("npa.ac_type")+"' and concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1,(locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(pro_date)+"' and nt.asset_code=npa.asset_code and npa.loan_type=nt.ac_type and days="+asset_days+" and nt.ac_type=lm.ac_type and nt.ac_no=lm.ac_no and lm.cid=cm.cid order by ac_no");						
						else
							rs_npa = st_npa.executeQuery("select nt.*,npa.asset_code,npa.asset_desc,npa.prov_perc,concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,lm.sanc_date,lm.sanc_amt from NPATable nt,NPAClassification_180 npa,LoanMaster lm,CustomerMaster cm where nt.ac_type='"+rs_mst.getString("npa.ac_type")+"' and concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1,(locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(pro_date)+"' and nt.asset_code=npa.asset_code and npa.loan_type=nt.ac_type and days="+asset_days+" and nt.ac_type=lm.ac_type and nt.ac_no=lm.ac_no and lm.cid=cm.cid and nt.asset_code="+asset_type+" order by ac_no");
					else
						if(asset_type==0)			                
							rs_npa = st_npa.executeQuery("select nt.*,npa.asset_code,npa.asset_desc,npa.prov_perc,concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,lm.sanc_date,lm.sanc_amt from NPATable nt,NPAClassification_90 npa,LoanMaster lm,CustomerMaster cm where nt.ac_type='"+rs_mst.getString("npa.ac_type")+"' and concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1,(locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(pro_date)+"' and nt.asset_code=npa.asset_code and npa.loan_type=nt.ac_type and days="+asset_days+" and nt.ac_type=lm.ac_type and nt.ac_no=lm.ac_no and lm.cid=cm.cid order by ac_no"); 
						else
							rs_npa = st_npa.executeQuery("select nt.*,npa.asset_code,npa.asset_desc,npa.prov_perc,concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,lm.sanc_date,lm.sanc_amt from NPATable nt,NPAClassification_90 npa,LoanMaster lm,CustomerMaster cm where nt.ac_type='"+rs_mst.getString("npa.ac_type")+"' and concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1,(locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(pro_date)+"' and nt.asset_code=npa.asset_code and npa.loan_type=nt.ac_type and days="+asset_days+" and nt.ac_type=lm.ac_type and nt.ac_no=lm.ac_no and lm.cid=cm.cid and nt.asset_code="+asset_type+" order by ac_no");
				}
				else
				{
					if(asset_days==180)
						if(asset_type==0)						
							rs_npa = st_npa.executeQuery("select nt.*,npa.asset_code,npa.asset_desc,npa.prov_perc,concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,lm.sanc_dt,lm.sanc_amt from NPATable nt,NPAClassification_180 npa,ODCCMaster lm,CustomerMaster cm where nt.ac_type='"+rs_mst.getString("npa.ac_type")+"' and concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1,(locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(pro_date)+"' and nt.asset_code=npa.asset_code and npa.loan_type=nt.ac_type and days="+asset_days+" and nt.ac_type=lm.ac_type and nt.ac_no=lm.ac_no and lm.cid=cm.cid order by ac_no");						
						else
							rs_npa = st_npa.executeQuery("select nt.*,npa.asset_code,npa.asset_desc,npa.prov_perc,concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,lm.sanc_dt,lm.sanc_amt from NPATable nt,NPAClassification_180 npa,ODCCMaster lm,CustomerMaster cm where nt.ac_type='"+rs_mst.getString("npa.ac_type")+"' and concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1,(locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(pro_date)+"' and nt.asset_code=npa.asset_code and npa.loan_type=nt.ac_type and days="+asset_days+" and nt.ac_type=lm.ac_type and nt.ac_no=lm.ac_no and lm.cid=cm.cid and nt.asset_code="+asset_type+" order by ac_no");
					else
						if(asset_type==0)			                
							rs_npa = st_npa.executeQuery("select nt.*,npa.asset_code,npa.asset_desc,npa.prov_perc,concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,lm.sanc_dt,lm.sanc_amt from NPATable nt,NPAClassification_90 npa,ODCCMaster lm,CustomerMaster cm where nt.ac_type='"+rs_mst.getString("npa.ac_type")+"' and concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1,(locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(pro_date)+"' and nt.asset_code=npa.asset_code and npa.loan_type=nt.ac_type and days="+asset_days+" and nt.ac_type=lm.ac_type and nt.ac_no=lm.ac_no and lm.cid=cm.cid order by ac_no"); 
						else
							rs_npa = st_npa.executeQuery("select nt.*,npa.asset_code,npa.asset_desc,npa.prov_perc,concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,lm.sanc_dt,lm.sanc_amt from NPATable nt,NPAClassification_90 npa,ODCCMaster lm,CustomerMaster cm where nt.ac_type='"+rs_mst.getString("npa.ac_type")+"' and concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1,(locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(pro_date)+"' and nt.asset_code=npa.asset_code and npa.loan_type=nt.ac_type and days="+asset_days+" and nt.ac_type=lm.ac_type and nt.ac_no=lm.ac_no and lm.cid=cm.cid and nt.asset_code="+asset_type+" order by ac_no");
				}
				j=0;
				rs_npa.last();					
				lrobj[i] = new LoanReportObject[rs_npa.getRow()];
				rs_npa.beforeFirst();
				while(rs_npa.next()){			        
					lrobj[i][j] = new LoanReportObject();
					lrobj[i][j].setName(rs_npa.getString("name"));
					lrobj[i][j].setCdind(rs_mst.getString("m.moduledesc"));
					lrobj[i][j].setAccType(rs_npa.getString("nt.ac_type"));
					lrobj[i][j].setAccNo(rs_npa.getInt("nt.ac_no"));
					if(rs_npa.getString("nt.ac_type").startsWith("1010"))
						lrobj[i][j].setSanctionDate(rs_npa.getString("lm.sanc_dt"));
					else
						lrobj[i][j].setSanctionDate(rs_npa.getString("lm.sanc_dt"));
					lrobj[i][j].setSanctionedAmount(rs_npa.getDouble("lm.sanc_amt"));
					lrobj[i][j].setTranType(rs_npa.getString("nt.pr_odue_from"));
					lrobj[i][j].setPrincipalPaid(rs_npa.getDouble("nt.pri_odue_amt"));
					lrobj[i][j].setReferenceNo(rs_npa.getInt("nt.pri_odue_prd"));
					lrobj[i][j].setInterestPayable(rs_npa.getDouble("nt.int_odue_amt"));
					lrobj[i][j].setIntUptoDate(rs_npa.getString("nt.int_upto_date")); 
					lrobj[i][j].setTransactiondate(rs_npa.getString("nt.last_trn_date"));
					lrobj[i][j].setLoanBalance(rs_npa.getDouble("nt.loan_balance"));
					lrobj[i][j].setLoanIntRate(rs_npa.getDouble("nt.prov_amt"));
					lrobj[i][j].setTranNarration(rs_npa.getString("npa.asset_desc"));
					lrobj[i][j].setTranMode(String.valueOf(rs_npa.getDouble("npa.prov_perc")));
					lrobj[i][j].setModuleCode(rs_npa.getString("nt.asset_code"));
					j++;
				}	 				
				i++;				
			}			
		}catch(SQLException exception){
			exception.printStackTrace();
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}		
		return lrobj;
	}	
	
	public Connection getConnection() 
	{
		try{			
			return ds.getConnection("root","");
		}catch(Exception e)	{e.printStackTrace();}
		return null;
	}
	
	public String getSysDate() 
	{
		Calendar c=Calendar.getInstance();
		
		try {
			return(Validations.checkDate(c.get(Calendar.DATE)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR)));
		} catch (DateFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getSysTime()
	{
		Calendar c=Calendar.getInstance();
		
		String str=String.valueOf(c.get(Calendar.SECOND));
		if(str.length()==1)
			str="0"+str;
		String str1=String.valueOf(c.get(Calendar.MINUTE));
		if(str1.length()==1)
			str1="0"+str1;
		String str2=String.valueOf(c.get(Calendar.HOUR));
		if(str2.length()==1)
			str2="0"+str2;			
		return(str2+":"+str1+":"+str+"  ");
	}
	public Context getInitialContext()  
	{
		/*java.util.Properties p = new java.util.Properties();
		 p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");		
		 p.put(Context.URL_PKG_PREFIXES, "jboss.naming:org.jnp.interfaces");
		 p.put(Context.PROVIDER_URL, "localhost:1099");
		 return new javax.naming.InitialContext(p);  */
		try {
			return new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null; 
	}
	//Vasu's
	
	public LoanMasterObject getLoanMaster(String actype,int acno, String date) {
		LoanMasterObject lo = new LoanMasterObject();
		Connection conn=null;
		try{
			conn=getConnection();
			//file_logger.info("inside get");
			//file_logger.info("inside actype"+actype);
			//file_logger.info("inside acno"+acno);
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery("select distinct int_type,sanc_date,sanc_amt,no_inst,lst_tr_seq,cid  from LoanMaster,LoanTransaction where LoanMaster.ac_type='"+actype+"' and LoanMaster.ac_no="+acno+" and LoanTransaction.ac_type=LoanMaster.ac_type and LoanTransaction.ac_no=LoanMaster.ac_no");// and trn_seq=lst_tr_seq");//commented as the trn_seq is removed from the database
			if(rs.next())
			{
				lo.setInterestType(rs.getInt("int_type"));
				lo.setSanctionDate(rs.getString("sanc_date"));
				lo.setSanctionedAmount(rs.getDouble("sanc_amt"));
				
				lo.setNoOfInstallments(rs.getInt("no_inst"));
				lo.setCustomerId(rs.getInt("cid"));
				lo.setInstallmentAmt(getCurrentDayClosingBalance(actype,acno,date));//for Principal Balance
				//lo.setRequiredAmount(rs.getDouble("extra_int"));//for extra interest
				//lo.setLastTrnSeq(rs.getInt("lst_tr_seq"));
				lo.setInterestUpto(getIntUpToDateOnCurrentDate(actype,acno,date));
			}else{
				return null;
			}
			//}catch (Exception ex) {lo.setInterestType(-1);file_logger.info(" "+ex);}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return lo;
	}
	
	//method to Store Loan Transaction
	public int storeLoanTransaction(LoanTransactionObject lto){
		Connection conn=null;
		ResultSet rs=null;
		
		int ref_no=0;
		try{
			conn=getConnection();
			
			
			Statement stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select lst_voucher_no from Modules where modulecode like '1010000'");
			if(rs.next()) {
				if(rs.getString("lst_voucher_no") == null) {
					ref_no = 1;
				} else {
					ref_no = rs.getInt("lst_voucher_no");
					ref_no ++;
				}
				
				stmt.executeUpdate("Update  Modules set lst_voucher_no =" + ref_no + " where modulecode like '1010000'");
			}
			
			if(lto.getAccType().startsWith("1010") || lto.getAccType().startsWith("1008")) {
				
				stmt.executeUpdate("delete from LoanRecoveryDetail where ac_type='"+lto.getAccType()+"' and ac_no="+lto.getAccNo()+" ");
				PreparedStatement ps=conn.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1,lto.getAccType());
				ps.setInt(2,lto.getAccNo());
				ps.setInt(3,0);
				ps.setString(4,lto.getTransactionDate());
				ps.setString(5,"O");
				ps.setDouble(6,lto.getTransactionAmount());
				ps.setString(7,"T");
				ps.setString(8,lto.getTranSou());
				ps.setInt(9,ref_no);
				ps.setString(10,lto.getTranNarration());
				ps.setString(11,lto.getRecoveryDate());
				ps.setString(12,"D");
				ps.setString(13,getIntUpToDateOnCurrentDate(lto.getAccType(),lto.getAccNo(),lto.getTransactionDate()));
				ps.setDouble(14,0);
				ps.setDouble(15,0);
				ps.setDouble(16,0);
				ps.setDouble(17,0);
				ps.setDouble(18,getLastRecoveryExtraInt(lto.getAccType(),lto.getAccNo(),lto.getTransactionDate()));
				ps.setDouble(19,getCurrentDayClosingBalance(lto.getAccType(),lto.getAccNo(),lto.getTransactionDate()));
				ps.setString(20,lto.uv.getUserTml());
				ps.setString(21,lto.uv.getUserId());
				ps.setString(22,lto.uv.getUserDate());
				ps.setString(23,null);
				ps.setString(24,null);
				ps.setString(25,null);
				ps.executeUpdate();
				return ref_no;

			} else {
				
				double closing_balance = 0;
				rs = stmt.executeQuery("select cl_bal from ODCCTransaction where ac_type like '" + lto.getAccType() + "' and ac_no = " + lto.getAccNo() + " and ve_tml is not null order by trn_seq desc limit 1");
				if(rs.next()) {
					closing_balance = rs.getDouble("cl_bal");
				}
				
				closing_balance -= lto.getTransactionAmount();
				
				PreparedStatement ps=conn.prepareStatement("insert into ODCCTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1,lto.getAccType());
				ps.setInt(2,lto.getAccNo());
				ps.setString(3,lto.getTransactionDate());
				ps.setString(4,"D");
				ps.setInt(5,0);
				ps.setDouble(6,lto.getTransactionAmount());
				ps.setString(7,"T");
				ps.setString(8,lto.getTranSou());
				ps.setString(9,"D");
				ps.setString(10,null);
				ps.setString(11,null);
				ps.setString(12,lto.getTranNarration());
				ps.setDouble(13,ref_no);
				ps.setString(14,null);
				ps.setDouble(15,closing_balance); // closeing balance
				ps.setDouble(16,0); 
				ps.setString(17,lto.uv.getUserTml());
				ps.setString(18,lto.uv.getUserId());
				ps.setString(19,lto.uv.getUserDate());
				ps.setString(20,null);
				ps.setString(21,null);
				ps.setString(22,null);
				ps.setString(23,null);
				ps.executeUpdate();
				return ref_no;
			}
		}catch(Exception exception){
			exception.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return -1;
	}
	
	
	//Vinay 19/01/2008
	public Hashtable getOtherChargeDetails(int tseq){ 
		Hashtable hash_obj =null;
		LoanMasterObject lmobj=null;
		LoanTransactionObject lnobj=null;
		Connection conn=null;
		ResultSet rs=null,rs1=null;
		int acno=0;
		String actype=null;
		try{
					
			conn=getConnection();
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		//	ResultSet rs=stmt.executeQuery("select ltn.ref_no as ref,lm.ac_type,lm.ac_no,lm.int_type,lm.sanc_date,lm.sanc_amt,lm.no_inst,ltn.trn_date,ltn.trn_amt,ltn.trn_narr from LoanMaster lm,LoanTransaction ltn where lm.ac_type='"+modulecode+"'  and lm.ac_no=ltn.ac_no and ltn.trn_type='O' and ltn.ref_no="+tseq+" and ltn.ve_tml is null");
			rs = stmt.executeQuery("select ltn.ref_no as refno,ltn.ac_type,ltn.ac_no,ltn.trn_date,ltn.trn_amt,ltn.trn_narr from LoanTransaction ltn where ltn.trn_type='O' and ltn.ref_no="+tseq+" ");
			
			lnobj = new LoanTransactionObject();
			hash_obj = new Hashtable();
			if(rs.next()) {
				actype = rs.getString("ltn.ac_type");
				acno = rs.getInt("ltn.ac_no");
				
				lnobj.setReferenceNo(rs.getInt("refno"));
				lnobj.setTransactionDate(rs.getString("ltn.trn_date"));
				lnobj.setTransactionAmount(rs.getDouble("ltn.trn_amt"));
				lnobj.setTranNarration(rs.getString("ltn.trn_narr"));
				
			}
			
			rs1 = stmt.executeQuery("select lm.ac_type,lm.cid,lm.ac_no,lm.int_type,lm.sanc_date,lm.sanc_amt,lm.no_inst from LoanMaster lm where ac_type='"+actype+"' and ac_no="+acno+" ");
			lmobj = new LoanMasterObject();
			
			if(rs1.next()){
				
				lmobj.setAccType(rs1.getString("lm.ac_type"));
				lmobj.setAccNo(rs1.getInt("lm.ac_no"));
				lmobj.setInterestType(rs1.getInt("lm.int_type"));
				lmobj.setSanctionDate(rs1.getString("lm.sanc_date"));
				lmobj.setSanctionedAmount(rs1.getDouble("lm.sanc_amt"));
				lmobj.setNoOfInstallments(rs1.getInt("lm.no_inst"));
				lmobj.setCustomerId(rs1.getInt("lm.cid"));
				
				}
			     hash_obj.put("LoanTransaction",lnobj);
			     hash_obj.put("LoanMaster",lmobj);
			
				//}catch (Exception ex) {lo.setAccNo(-1);file_logger.info(" "+ex);}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return hash_obj;
	}
	public LoanTransactionObject getOtherChargeDetails(String actype,int acno,int tseq){
		LoanTransactionObject lo = new LoanTransactionObject();
		Connection conn=null;
		try{
			conn=getConnection();
			
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery("select int_type,sanc_date,sanc_amt,no_inst,pr_bal,extra_int,trn_amt,trn_date,trn_narr from LoanMaster,LoanTransaction where LoanMaster.ac_type='"+actype+"' and LoanMaster.ac_no="+acno+" and LoanTransaction.ac_type=LoanMaster.ac_type and LoanTransaction.ac_no=LoanMaster.ac_no and ref_no="+tseq+" and trn_type='O' and LoanTransaction.ve_tml is null");
			if(rs.next())
			{
				lo.setAccNo(rs.getInt("int_type"));
				lo.setAccType(rs.getString("sanc_date"));
				lo.setLoanBalance(rs.getDouble("sanc_amt"));
				lo.setReferenceNo(rs.getInt("no_inst"));
				lo.setPrincipalBalance(rs.getDouble("pr_bal"));
				lo.setExtraIntAmount(rs.getDouble("extra_int"));
				lo.setTransactionAmount(rs.getDouble("trn_amt"));
				lo.setTransactionDate(rs.getString("trn_date"));
				lo.setTranNarration(rs.getString("trn_narr"));
			}
			//}catch (Exception ex) {lo.setAccNo(-1);System.out.println(" "+ex);}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return lo;
	}
	
	public int verifyOtherCharges(String actype, int acno, int ref_no, double trn_amt, String date, String vetml, String veuser, String ve_date)  throws GLKeyCodeNotFound
	{ 
		System.out.println("trn sequence == "+ref_no);
		Connection conn=null;
		ResultSet rs;
		try{
			cremote=home.create();
			conn=getConnection();
			Statement stmt = conn.createStatement();
			int trn_seq=0;
			rs=stmt.executeQuery("select lst_tr_seq+1 as trn_seq from LoanMaster where ac_type='"+actype+"' and ac_no="+acno+" ");
			if(rs.next()) {
				if(rs.getString("trn_seq")!=null) // This condition is checked because when it is called for the first time the value will be null and so this condtion check is necessary
					trn_seq = rs.getInt("trn_seq");
				else
					trn_seq=1;
			}
			
			int update=stmt.executeUpdate("update LoanMaster set lst_tr_seq="+trn_seq+" where  ac_type='"+actype+"' and ac_no="+acno+"");
			String int_date = getIntUpToDateOnCurrentDate(actype, acno, date);
			double extra_int = getLastRecoveryExtraInt(actype, acno, date);
			double pr_bal = getCurrentDayClosingBalance(actype, acno, date);
			
			stmt.executeUpdate("Update LoanTransaction set trn_seq = "+trn_seq+",trn_date  = '"+date+"',int_date  = '"+int_date+"', extra_int = "+extra_int+", pr_bal= "+pr_bal+", ve_tml='"+vetml+"',ve_user='"+veuser+"',ve_date='"+ve_date+"' where ac_type='"+actype+"' and ac_no="+acno+" and ref_no="+ref_no+" ");
			
			
			// For Debit Entry in GLTransaction
			GLTransObject trnobj = new GLTransObject();
			
			trnobj.setCdind("D");
			trnobj.setTrnDate(date);
			trnobj.setTrnMode("T");
			trnobj.setAccType(actype);
			trnobj.setAccNo(String.valueOf(acno));
			trnobj.setTrnSeq(trn_seq);
			trnobj.setTrnType("O");
			trnobj.setRefNo(ref_no);
		    trnobj.setVtml(vetml);
		    trnobj.setVid(veuser);
		    trnobj.setVDate(ve_date);
		    
		    rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+actype+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='O' and cr_dr='D' and code=4");
		    if(rs.next()) {
		    	trnobj.setGLType(rs.getString(1));
		    	trnobj.setGLCode(rs.getString(2));
		    	trnobj.setAmount(trn_amt*rs.getInt("mult_by"));
		    } else {
		    	throw new GLKeyCodeNotFound("GL Code has not been set for Other Charges Debit for transaction type 'O'");
		    }
		    cremote.storeGLTransaction(trnobj);
		    
		    // For Credit Entry in GLTransaction
		    trnobj = new GLTransObject();
		    trnobj.setCdind("C");
			trnobj.setTrnDate(date);
			trnobj.setTrnMode("T");
			trnobj.setAccType(actype);
			trnobj.setAccNo(String.valueOf(acno));
			trnobj.setTrnSeq(trn_seq);
			trnobj.setTrnType("R");
			trnobj.setRefNo(ref_no);
		    trnobj.setVtml(vetml);
		    trnobj.setVid(veuser);
		    trnobj.setVDate(ve_date);
		    
		    rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+actype+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='C' and code=5");
		    if(rs.next()) {
		    	trnobj.setGLType(rs.getString(1));
		    	trnobj.setGLCode(rs.getString(2));
		    	trnobj.setAmount(trn_amt*rs.getInt("mult_by"));
		    } else {
		    	throw new GLKeyCodeNotFound("GL Code has not been set for Other Charges Credit for transaction type 'R' ");
		    }
		    cremote.storeGLTransaction(trnobj);
			
			
			return 1;
		} catch (GLKeyCodeNotFound gl) {
			throw gl;
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return -1;
	}
	
	
	//method to get customer Names and cid's from some part of fname
	public String[] getNames(String fname){
		String[] name=null;
		Connection conn=null;
		try{
			conn=getConnection();			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs;
			rs = stmt.executeQuery("select fname,cid from CustomerMaster where fname like '"+fname+"%' order by fname");
			rs.last();
			name = new String[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next()){
				name[i]=rs.getString("fname")+" "+rs.getInt("cid");
				i++;
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{
				if(conn!=null)
					conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return name;
	}
	
	//to get Cid of a given A/C
	public int getCid(String actype,int no) throws CustomerNotFoundException{
		int cid=0;
		Connection conn=null;
		try{
			conn=getConnection();			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=null;
			int ac=Integer.parseInt(actype.substring(0,4));
			switch (ac) {
			case 1001://Share Members
				rs = stmt.executeQuery("select cid from ShareMaster where ac_type='"+actype+"' and ac_no="+no);
				break;
			case 1002:
			case 1007://**Savings Bank
			case 1018://**Savings Bank
				rs = stmt.executeQuery("select cid from AccountMaster where ac_type='"+actype+"' and ac_no="+no);
				break;
			case 1014:
			case 1015://**ODCC Bank
				rs = stmt.executeQuery("select cid from ODCCMaster where ac_type='"+actype+"' and ac_no="+no);
				break;
			case 1003://
			case 1004://** Deposits
			case 1005://
				rs = stmt.executeQuery("select cid from DepositMaster where ac_type='"+actype+"' and ac_no="+no);
				break;
			case 1006:
				rs = stmt.executeQuery("select cid from PygmyMaster where ac_type='"+actype+"' and ac_no="+no);
				break;
			case 1008://
			case 1010://**** Loan Accounts
				rs = stmt.executeQuery("select cid from LoanMaster where ac_type='"+actype+"' and ac_no="+no);
				break;
			case 1009:// Lockers
				break;
			case 1013:
				rs = stmt.executeQuery("select cid from AgentMaster where ac_type='"+actype+"' and ac_no="+no);
				break;
			default :
				break;
			}
			if(rs!=null && rs.next())
				cid=rs.getInt(1);
		}catch(SQLException exception){exception.printStackTrace();
		throw new CustomerNotFoundException();
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return cid;
	}
	
	public Vector queryOnCustomer(int cid){
		Vector details = new Vector(0,1);
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//Statement st = ServerImpl.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs,jhrs;//,bmrs;
			
			try {//retrieve Share Accounts
				rs = stmt.executeQuery("select ac_type,ac_no,share_val,mem_issuedate,mem_cl_date from ShareMaster where cid="+cid+" order by ac_type,ac_no");
				rs.last();
				DetailsObject dt[] = new DetailsObject[rs.getRow()];
				rs.beforeFirst();
				int i=0;
				while(rs.next()){
					dt[i] = new DetailsObject();
					dt[i].setMainModule("1001000");
					dt[i].setAccType(rs.getString("ac_type"));
					dt[i].setAccNo(rs.getInt("ac_no"));
					dt[i].setBalance(rs.getDouble("share_val"));
					dt[i].setOpenDate(rs.getString("mem_issuedate"));
					dt[i].setCloseDate(rs.getString("mem_cl_date"));
					i++;
				}
				if(dt.length > 0)
					details.add(dt);
			}catch (Exception ex) {ex.printStackTrace();}
			
			try {// Savings Accounts
				rs = stmt.executeQuery("select am.ac_type,am.ac_no,at.cl_bal,am.ac_opendate,am.ac_closedate from AccountMaster am,AccountTransaction at where am.ac_type like '1002%' and am.ac_type in(select ac_type from AccountMaster where cid="+cid+") and am.ac_no in(select ac_no from AccountMaster where cid="+cid+" and ac_type like '1002%') and at.ac_type=am.ac_type and at.ac_no=am.ac_no and at.trn_seq in(select max(trn_seq) from AccountTransaction where ac_type=am.ac_type and ac_no=am.ac_no and ve_tml is not null) order by am.ac_type,am.ac_no");
				jhrs = stat.executeQuery("select am.ac_type,am.ac_no,at.cl_bal,am.ac_opendate,am.ac_closedate from AccountMaster am,AccountTransaction at where am.ac_type like '1002%' and am.ac_type in(select ac_type from JointHolders where cid="+cid+") and am.ac_no in(select ac_no from JointHolders where cid="+cid+" and ac_type like '1002%') and at.ac_type=am.ac_type and at.ac_no=am.ac_no and at.trn_seq in(select max(trn_seq) from AccountTransaction where ac_type=am.ac_type and ac_no=am.ac_no and ve_tml is not null) order by am.ac_type,am.ac_no");
				rs.last();
				jhrs.last();
				DetailsObject dt[] = new DetailsObject[rs.getRow()+jhrs.getRow()];
				rs.beforeFirst();
				int i=0;
				try{
					while(rs.next()){
						dt[i] = new DetailsObject();
						dt[i].setMainModule("1002000");
						dt[i].setAccType(rs.getString("ac_type"));
						dt[i].setAccNo(rs.getInt("ac_no"));
						dt[i].setBalance(rs.getDouble("cl_bal"));
						dt[i].setOpenDate(rs.getString("ac_opendate"));
						dt[i].setCloseDate(rs.getString("ac_closedate"));
						i++;
					}
				}catch(Exception e){System.out.println (""+e);}
				
				try{
					jhrs.beforeFirst();
					while(jhrs.next()){
						dt[i] = new DetailsObject();
						dt[i].setMainModule("1002000");
						dt[i].setAccType(jhrs.getString("ac_type"));
						dt[i].setAccNo(jhrs.getInt("ac_no"));
						dt[i].setBalance(jhrs.getDouble("cl_bal"));
						dt[i].setOpenDate(jhrs.getString("ac_opendate"));
						dt[i].setCloseDate(jhrs.getString("ac_closedate"));
						i++;
					}
				}catch(Exception e){System.out.println (""+e);}
				
				if(dt.length > 0)
					details.add(dt);				
			}catch (Exception ex) {ex.printStackTrace();}
			
			try {// Current Accounts
				rs = stmt.executeQuery("select am.ac_type,am.ac_no,at.cl_bal,am.ac_opendate,am.ac_closedate from AccountMaster am,AccountTransaction at where am.ac_type like '1007%' and am.ac_type in(select ac_type from AccountMaster where cid="+cid+") and am.ac_no in(select ac_no from AccountMaster where cid="+cid+" and ac_type like '1007%') and at.ac_type=am.ac_type and at.ac_no=am.ac_no and at.trn_seq in(select max(trn_seq) from AccountTransaction where ac_type=am.ac_type and ac_no=am.ac_no and ve_tml is not null) order by am.ac_type,am.ac_no");
				jhrs = stat.executeQuery("select am.ac_type,am.ac_no,at.cl_bal,am.ac_opendate,am.ac_closedate from AccountMaster am,AccountTransaction at where am.ac_type like '1007%' and am.ac_type in(select ac_type from JointHolders where cid="+cid+") and am.ac_no in(select ac_no from JointHolders where cid="+cid+" and ac_type like '1007%') and at.ac_type=am.ac_type and at.ac_no=am.ac_no and at.trn_seq in(select max(trn_seq) from AccountTransaction where ac_type=am.ac_type and ac_no=am.ac_no and ve_tml is not null) order by am.ac_type,am.ac_no");
				rs.last();
				jhrs.last();
				DetailsObject dt[] = new DetailsObject[rs.getRow()+jhrs.getRow()];
				rs.beforeFirst();
				int i=0;
				try{
					while(rs.next()){
						dt[i] = new DetailsObject();
						dt[i].setMainModule("1007000");
						dt[i].setAccType(rs.getString("ac_type"));
						dt[i].setAccNo(rs.getInt("ac_no"));
						dt[i].setBalance(rs.getDouble("cl_bal"));
						dt[i].setOpenDate(rs.getString("ac_opendate"));
						dt[i].setCloseDate(rs.getString("ac_closedate"));
						i++;
					}
				}catch(Exception e){System.out.println (""+e);}
				
				try{
					jhrs.beforeFirst();
					while(jhrs.next()){
						dt[i] = new DetailsObject();
						dt[i].setMainModule("1007000");
						dt[i].setAccType(jhrs.getString("ac_type"));
						dt[i].setAccNo(jhrs.getInt("ac_no"));
						dt[i].setBalance(jhrs.getDouble("cl_bal"));
						dt[i].setOpenDate(jhrs.getString("ac_opendate"));
						dt[i].setCloseDate(jhrs.getString("ac_closedate"));
						i++;
					}
				}catch(Exception e){System.out.println (""+e);}
				
				if(dt.length > 0)
					details.add(dt);
			}catch (Exception ex) {ex.printStackTrace();}
			
			
			try {// get Fixed Deposit Details
				rs = stmt.executeQuery("select dm.ac_type,dm.ac_no,(dt.dep_amt+dt.int_amt-dt.dep_paid-dt.int_paid),dm.dep_date,dm.close_date from DepositMaster dm,DepositTransaction dt where dm.cid="+cid+" and dm.ac_type like '1003%' and dt.ac_type=dm.ac_type and dt.ac_no=dm.ac_no and dt.trn_seq=dm.lst_trn_seq order by dm.ac_type,dm.ac_no");
				jhrs = stat.executeQuery("select dm.ac_type,dm.ac_no,(dt.dep_amt+dt.int_amt-dt.dep_paid-dt.int_paid),dm.dep_date,dm.close_date from JointHolders jt,DepositMaster dm,DepositTransaction dt where jt.cid="+cid+" and jt.ac_type like '1003%' and dm.ac_type=jt.ac_type and dm.ac_no=jt.ac_no and dt.ac_type=dm.ac_type and dt.ac_no=dm.ac_no and dt.trn_seq=dm.lst_trn_seq order by dm.ac_type,dm.ac_no");
				rs.last();
				jhrs.last();
				DetailsObject dt[] = new DetailsObject[rs.getRow()+jhrs.getRow()];
				rs.beforeFirst();
				int i=0;
				try{
					while(rs.next()){
						dt[i] = new DetailsObject();
						dt[i].setMainModule("1003000");
						dt[i].setAccType(rs.getString("ac_type"));
						dt[i].setAccNo(rs.getInt("ac_no"));
						dt[i].setBalance(rs.getDouble(3));
						dt[i].setOpenDate(rs.getString("dep_date"));
						dt[i].setCloseDate(rs.getString("close_date"));
						i++;
					}
				}catch(Exception e){System.out.println ("loans impl:"+e);}
				
				try{
					jhrs.beforeFirst();
					while(jhrs.next()){
						dt[i] = new DetailsObject();
						dt[i].setMainModule("1003000");
						dt[i].setAccType(jhrs.getString("ac_type"));
						dt[i].setAccNo(jhrs.getInt("ac_no"));
						dt[i].setBalance(jhrs.getDouble(3));
						dt[i].setOpenDate(jhrs.getString("dep_date"));
						dt[i].setCloseDate(jhrs.getString("close_date"));
						i++;
					}
				}catch(Exception e){System.out.println ("loans impl:"+e);}
				
				if(dt.length > 0)
					details.add(dt);
			}catch (Exception ex) {ex.printStackTrace();}
			
			try {// get Recurring Deposit Details
				rs = stmt.executeQuery("select dm.ac_type,dm.ac_no,dt.rd_bal,dm.dep_date,dm.close_date from DepositMaster dm,DepositTransaction dt where dm.cid="+cid+" and dm.ac_type like '1004%' and dt.ac_type=dm.ac_type and dt.ac_no=dm.ac_no and dt.trn_seq=dm.lst_trn_seq order by dm.ac_type,dm.ac_no");
				jhrs = stat.executeQuery("select dm.ac_type,dm.ac_no,dt.rd_bal,dm.dep_date,dm.close_date from JointHolders jt,DepositMaster dm,DepositTransaction dt where jt.cid="+cid+" and jt.ac_type like '1004%' and dm.ac_type=jt.ac_type and dm.ac_no=jt.ac_no and dt.ac_type=dm.ac_type and dt.ac_no=dm.ac_no and dt.trn_seq=dm.lst_trn_seq order by dm.ac_type,dm.ac_no");
				rs.last();
				jhrs.last();
				DetailsObject dt[] = new DetailsObject[rs.getRow()+jhrs.getRow()];
				rs.beforeFirst();
				int i=0;
				try{
					while(rs.next()){
						dt[i] = new DetailsObject();
						dt[i].setMainModule("1004000");
						dt[i].setAccType(rs.getString("ac_type"));
						dt[i].setAccNo(rs.getInt("ac_no"));
						dt[i].setBalance(rs.getDouble("rd_bal"));
						dt[i].setOpenDate(rs.getString("dep_date"));
						dt[i].setCloseDate(rs.getString("close_date"));
						i++;
					}
				}catch(Exception e){System.out.println ("loansimpl:queryoncustomer:"+e);}
				try {
					jhrs.beforeFirst();
					while (jhrs.next()){
						dt[i] = new DetailsObject();
						dt[i].setMainModule("1004000");
						dt[i].setAccType(jhrs.getString("ac_type"));
						dt[i].setAccNo(jhrs.getInt("ac_no"));
						dt[i].setBalance(jhrs.getDouble("rd_bal"));
						dt[i].setOpenDate(jhrs.getString("dep_date"));
						dt[i].setCloseDate(jhrs.getString("close_date"));
						i++;
					}
				}catch (Exception ex) {System.out.println(" "+ex);}
				
				if(dt.length > 0)
					details.add(dt);
			}catch (Exception ex) {ex.printStackTrace();}
			
			try {// get Re-Investment Deposit Details
				rs = stmt.executeQuery("select dm.ac_type,dm.ac_no,(dt.dep_amt+dt.int_amt-dt.dep_paid-dt.int_paid),dm.dep_date,dm.close_date from DepositMaster dm,DepositTransaction dt where dm.cid="+cid+" and dm.ac_type like '1005%' and dt.ac_type=dm.ac_type and dt.ac_no=dm.ac_no and dt.trn_seq=dm.lst_trn_seq order by dm.ac_type,dm.ac_no");
				jhrs = stat.executeQuery("select dm.ac_type,dm.ac_no,(dt.dep_amt+dt.int_amt-dt.dep_paid-dt.int_paid),dm.dep_date,dm.close_date from JointHolders jt,DepositMaster dm,DepositTransaction dt where jt.cid="+cid+" and jt.ac_type like '1005%' and dm.ac_type=jt.ac_type and dm.ac_no=jt.ac_no and dt.ac_type=dm.ac_type and dt.ac_no=dm.ac_no and dt.trn_seq=dm.lst_trn_seq order by dm.ac_type,dm.ac_no");
				rs.last();
				jhrs.last();
				DetailsObject dt[] = new DetailsObject[rs.getRow()+jhrs.getRow()];
				rs.beforeFirst();
				int i=0;
				try{
					while(rs.next()){
						dt[i] = new DetailsObject();
						dt[i].setMainModule("1005000");
						dt[i].setAccType(rs.getString("ac_type"));
						dt[i].setAccNo(rs.getInt("ac_no"));
						dt[i].setBalance(rs.getDouble(3));
						dt[i].setOpenDate(rs.getString("dep_date"));
						dt[i].setCloseDate(rs.getString("close_date"));
						i++;
					}
				}catch(Exception e){System.out.println (""+e);}
				
				try{
					jhrs.beforeFirst();
					while(jhrs.next()){
						dt[i] = new DetailsObject();
						dt[i].setMainModule("1005000");
						dt[i].setAccType(jhrs.getString("ac_type"));
						dt[i].setAccNo(jhrs.getInt("ac_no"));
						dt[i].setBalance(jhrs.getDouble(3));
						dt[i].setOpenDate(jhrs.getString("dep_date"));
						dt[i].setCloseDate(jhrs.getString("close_date"));
						i++;
					}
				}catch(Exception e){System.out.println (""+e);}
				
				if(dt.length > 0)
					details.add(dt);
			}catch (Exception ex) {ex.printStackTrace();}
			
			
			try {// PygmyDeposits
				rs = stmt.executeQuery("select pm.ac_type,pm.ac_no,pt.cl_bal,pm.open_date,pm.close_date from PygmyMaster pm,PygmyTransaction pt where pm.cid="+cid+" and pt.ac_type=pm.ac_type and pt.ac_no=pm.ac_no and pt.trn_seq=pm.lst_trn_seq order by pm.ac_type,pm.ac_no ");
				jhrs=stat.executeQuery("select pm.ac_type,pm.ac_no,pt.cl_bal,pm.open_date,pm.close_date from PygmyMaster pm,PygmyTransaction pt,JointHolders jt where jt.cid=1 and pm.ac_type=jt.ac_type and pm.ac_no=jt.ac_no and pt.ac_type=pm.ac_type and pt.ac_no=pm.ac_no and pt.trn_seq=pm.lst_trn_seq order by pm.ac_type,pm.ac_no");
				rs.last();
				jhrs.last();
				DetailsObject dt[] = new DetailsObject[rs.getRow()+jhrs.getRow()];
				rs.beforeFirst();
				int i=0;
				try{
					while(rs.next()){
						dt[i] = new DetailsObject();
						dt[i].setMainModule("1006000");
						dt[i].setAccType(rs.getString("ac_type"));
						dt[i].setAccNo(rs.getInt("ac_no"));
						dt[i].setBalance(rs.getDouble("cl_bal"));
						dt[i].setOpenDate(rs.getString("open_date"));
						dt[i].setCloseDate(rs.getString("close_date"));
						i++;
					}
				}catch(Exception e){System.out.println (""+e);}
				
				try{
					jhrs.beforeFirst();
					while(jhrs.next()){
						dt[i] = new DetailsObject();
						dt[i].setMainModule("1006000");
						dt[i].setAccType(jhrs.getString("ac_type"));
						dt[i].setAccNo(jhrs.getInt("ac_no"));
						dt[i].setBalance(jhrs.getDouble("cl_bal"));
						dt[i].setOpenDate(jhrs.getString("open_date"));
						dt[i].setCloseDate(jhrs.getString("close_date"));
						i++;
					}
				}catch(Exception e){System.out.println (""+e);}
				
				if(dt.length > 0)
					details.add(dt);
			}catch (Exception ex) {ex.printStackTrace();}
			
			try {//lockers
				rs = stmt.executeQuery("select ac_type,ac_no,allot_dt,close_dt from LockerMaster where cid="+cid);
				rs.last();
				DetailsObject dt[] = new DetailsObject[rs.getRow()];
				rs.beforeFirst();
				int i=0;
				while(rs.next()){
					dt[i] = new DetailsObject();
					dt[i].setMainModule("1009000");
					dt[i].setAccType(rs.getString("ac_type"));
					dt[i].setAccNo(rs.getInt("ac_no"));
					//dt[i].setBalance(rs.getDouble("rent_coll"));
					dt[i].setOpenDate(rs.getString("allot_dt"));
					dt[i].setCloseDate(rs.getString("close_dt"));
					i++;
				}
				if(dt.length > 0)
					details.add(dt);
				
			}catch (Exception ex) {ex.printStackTrace();}
			
			try {//loans
				rs = stmt.executeQuery("select lm.ac_type,lm.ac_no,lt.pr_bal,lm.sanc_date,lm.close_date from LoanMaster lm,LoanTransaction lt where cid="+cid+"  and lt.ac_type=lm.ac_type and lt.ac_no=lm.ac_no and lt.trn_seq=lm.lst_tr_seq order by lm.ac_type,lm.ac_no");
				rs.last();
				DetailsObject dt[] = new DetailsObject[rs.getRow()];
				rs.beforeFirst();
				int i=0;
				while(rs.next()){
					dt[i] = new DetailsObject();
					dt[i].setMainModule("1010000");
					dt[i].setAccType(rs.getString("ac_type"));
					dt[i].setAccNo(rs.getInt("ac_no"));
					dt[i].setBalance(rs.getDouble("pr_bal"));
					dt[i].setOpenDate(rs.getString("sanc_date"));
					dt[i].setCloseDate(rs.getString("close_date"));
					i++;
				}
				if(dt.length > 0)
					details.add(dt);
				
			}catch (Exception ex) {//System.out.println("loans:queryoncustomer:no LoanAccounts for :"+cid);
				ex.printStackTrace();
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return details;
	}
	
	public LedgerObject[] getLedger(int fromac,int toac,int acstatus,String fdate,String tdate){
		LedgerObject[] lo=null;
		Connection conn=null;
		try{
			conn=getConnection();
			
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs,rs2;
			if(acstatus == 0)//Active Accounts
				rs = stmt.executeQuery("select cm.fname,IFNULL(cm.mname,' '),ifnull(cm.lname,' '),cm.sex,cm.scst,LoanPurposes.`pps_desc`,st.catname,lm.* from `CustomerMaster` cm,ShareMaster sm,LoanMaster lm,ShareType st,LoanPurposes where lm.ac_no between "+fromac+" and "+toac+" and sm.ac_type=lm.sh_type and sm.ac_no=lm.sh_no and st.ac_type=sm.ac_type and st.mem_cat=sm.mem_cat and cm.cid=sm.cid and LoanPurposes.pps_no=lm.pps_code and close_date is null order by lm.ac_type,lm.ac_no");
			else if(acstatus == 1)//Closed Accounts
				rs=stmt.executeQuery("select cm.fname,IFNULL(cm.mname,' '),ifnull(cm.lname,' '),cm.sex,cm.scst,LoanPurposes.`pps_desc`,st.catname,lm.* from `CustomerMaster` cm,ShareMaster sm,LoanMaster lm,ShareType st,LoanPurposes where sm.ac_type=lm.sh_type and sm.ac_no=lm.sh_no and st.ac_type=sm.ac_type and st.mem_cat=sm.mem_cat and cm.cid=sm.cid and LoanPurposes.pps_no=lm.pps_code and concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,(locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' order by lm.ac_type,lm.ac_no");
			else
				rs = stmt.executeQuery("select cm.fname,IFNULL(cm.mname,' '),ifnull(cm.lname,' '),cm.sex,cm.scst,LoanPurposes.`pps_desc`,st.catname,lm.* from `CustomerMaster` cm,ShareMaster sm,LoanMaster lm,ShareType st,LoanPurposes where lm.ac_no between "+fromac+" and "+toac+" and sm.ac_type=lm.sh_type and sm.ac_no=lm.sh_no and st.ac_type=sm.ac_type and st.mem_cat=sm.mem_cat and cm.cid=sm.cid and LoanPurposes.pps_no=lm.pps_code order by lm.ac_type,lm.ac_no");
			rs.last();
			lo=new LedgerObject[rs.getRow()];
			rs.beforeFirst();
			int ac=0;
			while(rs.next()){
				lo[ac] = new LedgerObject();
				lo[ac].lm=new LoanMasterObject();
				lo[ac].lm.setAccType(rs.getString("ac_type"));
				lo[ac].lm.setAccNo(rs.getInt("ac_no"));
				lo[ac].lm.setName(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
				lo[ac].lm.setShareAccType(rs.getString("sh_type"));
				lo[ac].lm.setShareAccNo(rs.getInt("sh_no"));
				lo[ac].lm.setMailingAddress(rs.getInt(13));
				lo[ac].lm.setApplicationSrlNo(rs.getInt("appn_srl"));
				lo[ac].lm.setApplicationDate(rs.getString("appn_date"));
				lo[ac].lm.setLastTrndt(rs.getString(6));//for purpose
				lo[ac].lm.setNoOfSurities(rs.getInt("no_surities"));
				lo[ac].lm.setInterestUpto(rs.getString("int_upto_date"));
				lo[ac].lm.setRequiredAmount(rs.getDouble("req_amt"));
				if(rs.getString("cm.sex")!=null && rs.getString("cm.sex").trim().length()>0)
					lo[ac].lm.setSexInd(rs.getString("cm.sex").charAt(0));
				if(rs.getString("cm.scst")!=null)
					lo[ac].lm.setScstInd(rs.getString("cm.scst"));
				else
					lo[ac].lm.setScstInd("N");
				if(rs.getString("wk_sect")!=null)
					lo[ac].lm.setWeakerSection((rs.getString("wk_sect").equalsIgnoreCase("Y"))?true:false);
				else
					lo[ac].lm.setWeakerSection(false);
				lo[ac].lm.setSanctionDate(rs.getString("sanc_date"));
				lo[ac].lm.setSanctionedAmount(rs.getDouble("sanc_amt"));
				if(rs.getString("pay_mode")!=null)
					lo[ac].lm.setPayMode(rs.getString("pay_mode"));
				else
					lo[ac].lm.setPayMode("N");
				lo[ac].lm.setPaymentAcctype(rs.getString("pay_ac_type"));
				lo[ac].lm.setPaymentAccno(rs.getInt("pay_ac_no"));
				lo[ac].lm.setNoOfInstallments(rs.getInt("no_inst"));
				lo[ac].lm.setInstallmentAmt(rs.getDouble("inst_amt"));
				lo[ac].lm.setPurposeCode(rs.getInt("holday_mth"));//for Holiday Period
				
				try {
					if(lo[ac].lm.getPayMode()!=null && lo[ac].lm.getPayMode().equalsIgnoreCase("T")){
						rs2 = stat.executeQuery("select concat(cm.fname,' ',IFNULL(cm.mname,''),' ',IFNULL(cm.lname,'')) from CustomerMaster cm,AccountMaster am where am.ac_type='"+lo[ac].lm.getPaymentAcctype()+"' and am.ac_no="+lo[ac].lm.getPaymentAccno()+" and cm.cid=am.cid");
						if(rs2.next())
							lo[ac].lm.setClosedt(rs2.getString(1));//for A/C Name
					}
				}catch (Exception ex) {System.out.println(" "+ex);}
				
				int j=0;
				try {
					if(acstatus==2)
						rs2=stat.executeQuery("select * from LoanTransaction where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type!='S' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ve_tml is not null order by concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))");
					else
						rs2=stat.executeQuery("select * from LoanTransaction where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type!='S' and ve_tml is not null order by concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))");
					if(rs2.last()){
						lo[ac].lt=new LoanTransactionObject[rs2.getRow()];
						rs2.beforeFirst();
					}
					while(rs2.next()){
						lo[ac].lt[j]=new LoanTransactionObject();
						lo[ac].lt[j].setTransactionDate(rs2.getString("trn_date"));
						lo[ac].lt[j].setTranType(rs2.getString("trn_type"));
						lo[ac].lt[j].setTranNarration(rs2.getString("trn_narr"));
						lo[ac].lt[j].setTranMode(rs2.getString("trn_mode"));
						lo[ac].lt[j].setReferenceNo(rs2.getInt("ref_no"));
						lo[ac].lt[j].setCdind(rs2.getString("cd_ind"));
						lo[ac].lt[j].setTransactionAmount(rs2.getDouble("trn_amt"));
						lo[ac].lt[j].setPrincipalBalance(rs2.getDouble("pr_bal"));
						lo[ac].lt[j].setPrincipalPaid(rs2.getDouble("pr_amt"));
						lo[ac].lt[j].setInterestPaid(rs2.getDouble("int_amt"));
						lo[ac].lt[j].setPenaltyAmount(rs2.getDouble("penal_amt"));
						lo[ac].lt[j].setOtherAmount(rs2.getDouble("other_amt"));
						lo[ac].lt[j].setIntUptoDate(rs2.getString("int_date"));
						j++;
					}
				}catch (Exception ex) {System.out.println(" "+ex);}
				ac++;
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		System.out.println("Ledger Object in bean" + lo.length);
		return lo;
	}
	
	public boolean checkDCBReport(int month,int year){
		Connection conn=null;
		try{
			conn=getConnection();
			
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//to check whether any records exists for the particular month
			ResultSet rs = stmt.executeQuery("select * from DCBReport where month='"+(month+"/"+year)+"'");
			rs.last();
			System.out.println("DCb:rows:::::::"+rs.getRow());
			if(rs.getRow()>0)//if records exists, return true
				return true;
			return false;//if No records, return false
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return false;
	}
	
	public int dcbProcess(int month,int year,int type,String acty,int fromno,int tono,String tml,String user,boolean del){
		Connection conn=null;
		try{
			conn=getConnection();
			
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if(del==true)
				stmt.executeUpdate("delete from DCBReport where month='"+(month+"/"+year)+"'");
			PreparedStatement psend = conn.prepareStatement("insert into DCBReport values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,date_format(sysdate(),'%d/%m/%Y %r'))");
			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs,rs2,rs3;
			GregorianCalendar gcal=new GregorianCalendar();
			int a[]={31,28,31,30,31,30,31,31,30,31,30,31};
			String prevdt="",monthend="";
			
			prevdt = Validations.addDays("01/"+month+"/"+year,-1);
			
			if(gcal.isLeapYear(year) && month==2)
				monthend="29/02"+year;
			else monthend = a[month-1]+"/"+month+"/"+year;
			
			StringTokenizer token=new StringTokenizer(getSysDate(),"/");
			token.nextToken();
			int mm=Integer.parseInt(token.nextToken());
			int yy=Integer.parseInt(token.nextToken());
			if(yy==year && mm==month)
				monthend=getSysDate();
			
			System.out.println ("previous dddddddaaaaate:"+prevdt);	
			System.out.println ("month end :"+monthend);
			
			if(type == 0)
				rs = stmt.executeQuery("select lm.*,cm.sub_category from LoanMaster lm,CustomerMaster cm where cm.cid=lm.cid and lm.ve_tml is not null and (close_date is null or concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,(locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) > '"+Validations.convertYMD(prevdt)+"')");
			else
				rs = stmt.executeQuery("select lm.*,cm.sub_category from LoanMaster lm,CustomerMaster cm where cm.cid=lm.cid and lm.ac_type='"+acty+"' and lm.ac_no between "+fromno+" and "+tono+" and lm.ve_tml is not null and (close_date is null or concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,(locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) > '"+Validations.convertYMD(prevdt)+"')");
			
			while(rs.next()){
				System.out.println ("a/C nOOO:;"+rs.getInt("ac_no"));
				rs2 = st.executeQuery("select lt.pr_bal,lt.int_date,int_rate_type,int_rate from LoanTransaction lt,LoanMaster lm where lt.ac_type='"+rs.getString("ac_type")+"' and lt.ac_no="+rs.getInt("ac_no")+" and lm.ac_type='"+rs.getString("ac_type")+"' and lm.ac_no="+rs.getInt("ac_no")+" and lt.trn_type='R' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(prevdt)+"' order by concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) desc limit 1");
				if(rs2.next())
				{
					int inttype = rs2.getInt("int_rate_type");
					System.out.println ("Int type "+inttype);
					String intupto=rs2.getString("int_date");
					System.out.println ("Interest Upto:"+intupto);
					double intrate=rs2.getDouble("int_rate");
					double prvbal = rs2.getDouble("pr_bal");
					
					psend.setString(1,month+"/"+year);
					psend.setString(2,rs.getString("ac_type"));
					psend.setInt(3,rs.getInt("ac_no"));
					psend.setDouble(4,rs2.getDouble(1));//opening balance
					if(inttype==1)
						intrate=getIntRate(rs.getString("ac_type"),prevdt,rs.getInt("cm.sub_category"),rs.getInt("no_inst"),rs.getDouble("sanc_amt"),rs.getInt("ac_no"));
					System.out.println ("Int Rate "+intrate);
					System.out.println ("no of days..."+Validations.dayCompare(intupto,prevdt));
					System.out.println ("int OD"+Math.round((rs2.getDouble(1)*Validations.dayCompare(intupto,prevdt)*intrate)/(365*100)));
					psend.setDouble(6,Math.round((rs2.getDouble(1)*Validations.dayCompare(intupto,prevdt)*intrate)/(365*100)));
					
					rs2 = st.executeQuery("select sum(pr_amt) from LoanTransaction where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type='S' and ve_tml is not null and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <= '"+Validations.convertYMD(prevdt)+"'");
					rs3 = stat.executeQuery("select sum(pr_amt),sum(other_amt) from LoanTransaction where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type='R' and ve_tml is not null and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <= '"+Validations.convertYMD(prevdt)+"'");
					rs2.next();
					rs3.next();
					double prod=rs2.getDouble(1)-rs3.getDouble(1);
					System.out.println ("principal OD: "+prod);
					
					rs2 = st.executeQuery("select sum(trn_amt) from LoanTransaction where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type='O' and ve_tml is not null and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <= '"+Validations.convertYMD(prevdt)+"'");
					rs2.next();
					System.out.println ("other OD: "+(rs2.getDouble(1)-rs3.getDouble(2)));
					psend.setDouble(8,rs2.getDouble(1)-rs3.getDouble(2));//other OD
					
					System.out.println("fffjfjj");
					
					if(prod>0){//calculate penal Interest
						
						double penalint=0;
						try {
							rs2= st.executeQuery("select pr_bal from LoanTransaction where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type='R' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(intupto)+"' order by concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) desc limit 1");
							rs3=stat.executeQuery("select  * from LoanTransaction  where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+"  and  concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(intupto)+"' and '"+Validations.convertYMD(prevdt)+"' order by concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))");
							double schamt=0;
							String ptrndt=intupto,ctrndt="";
							double prnpaid=0,prnbal=0,prevbal=0;
							if(rs2.next())
								prevbal=rs2.getDouble(1);
							else
								prevbal=0;	
							
							while(rs3.next())
							{
								String trntype=rs3.getString("trn_type");
								System.out.println (10000045);
								
								if(trntype.equals("R") || trntype.equals("D")){
									if(trntype.equals("R")){									
										prnpaid+=rs3.getDouble("pr_amt");
										prnbal=rs3.getDouble("pr_bal");
										System.out.println("principal balance == "+prnbal);
									}								
								}
								else if(trntype.equals("S"))
									schamt+=rs3.getDouble("pr_amt");
								
								System.out.println (21);
								
								if(!trntype.equals("O"))
									ctrndt=rs3.getString("trn_date");
								
								System.out.println (1121);
								//if scheduled amt is greater that principal paid means go for penal interest calculation
								if( prevbal>0 )
									penalint=penalint+calculatePenalInt(rs.getString("ac_type"),ptrndt,ctrndt,prevbal,rs.getInt("cm.sub_category"));
								
								if(schamt>prnpaid && rs3.getRow()>2)
									prevbal=schamt-prnpaid;
								else
									prevbal=0;
								
								if(rs3.getRow()>1 || !trntype.equals("O"))
									ptrndt=ctrndt;
								
							}
							System.out.println("penal interest:"+penalint);						
						}catch (Exception ex) {System.out.println(" "+ex);}
						psend.setDouble(5,prod);//principal OD
						psend.setDouble(7,penalint);//pint OD
						psend.setDouble(9,0);//adv paid
					}//end of if(prod>0)
					else{
						psend.setDouble(5,0);
						psend.setDouble(7,0);
						psend.setDouble(9,-prod);
					}
					
					rs2 = st.executeQuery("select sum(pr_amt) from LoanTransaction where trn_type='S' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(Validations.addDays(prevdt,1))+"' and '"+Validations.convertYMD(monthend)+"'");
					rs2.next();
					System.out.println ("This mnth pr demand :"+rs2.getDouble(1));
					
					psend.setDouble(10,rs2.getDouble(1));//principal demand
					System.out.println ("this mth int dmd:"+Math.round((prvbal*Validations.dayCompare(intupto,monthend)*intrate)/(365*100)));
					
					//interest demand
					psend.setDouble(11,Math.round((prvbal*Validations.dayCompare(intupto,monthend)*intrate)/(365*100)));
					
					rs2 = st.executeQuery("select sum(other_amt) from LoanTransaction where trn_type='O' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(Validations.addDays(prevdt,1))+"' and '"+Validations.convertYMD(monthend)+"'");
					rs2.next();
					System.out.println ("This mnth others demand :"+rs2.getDouble(1));
					
					psend.setDouble(12,rs2.getDouble(1));//others demand
					
					rs2 = st.executeQuery("select sum(pr_amt),sum(int_amt),sum(penal_amt),sum(other_amt) from LoanTransaction where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type='R' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(Validations.addDays(prevdt,1))+"' and '"+Validations.convertYMD(monthend)+"'");
					rs2.next();
					psend.setDouble(13,rs2.getDouble(1));//principal collected
					psend.setDouble(14,rs2.getDouble(2));//interest collected
					psend.setDouble(15,rs2.getDouble(3));//penal interest collected
					psend.setDouble(16,rs2.getDouble(4));//others collected
					
					psend.setDouble(17,0);//advance collected
					psend.setString(18,tml);
					psend.setString(19,user);
					
					System.out.println ("executed:"+psend.executeUpdate());
					
				}
			}
			System.out.println("Inside DCB Process");
			return 1;	        
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return -1;
	}
	
	public DCBObject[] dcbSchedule(int month,int year,int type,String acty,int fromno,int tono){		
		DCBObject dcb[]=null;
		Connection conn=null;
		try{
			conn=getConnection();
			
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs;
			if(type == 0)
				rs = stmt.executeQuery("select * from DCBReport where month='"+(month+"/"+year)+"' order by ac_type,ac_no");
			else
				rs = stmt.executeQuery("select * from DCBReport where month='"+(month+"/"+year)+"' and ac_type='"+acty+"' and ac_no between "+fromno+" and "+tono+" order by ac_no");
			
			rs.last();
			dcb = new DCBObject[rs.getRow()];	
			rs.beforeFirst();	        
			int i=0;
			while(rs.next()){
				dcb[i] = new DCBObject();
				dcb[i].setMonth(rs.getString("month"));
				dcb[i].setAccType(rs.getString("ac_type"));
				dcb[i].setAccNo(rs.getInt("ac_no"));
				dcb[i].setLoanBalance(rs.getDouble("ln_bal"));
				dcb[i].setPrincipalArr(rs.getDouble("pr_arr"));
				dcb[i].setIntrArr(rs.getDouble("int_arr"));
				dcb[i].setPenalIntArr(rs.getDouble("pint_arr"));
				dcb[i].setOtherArr(rs.getDouble("ochg_arr"));
				dcb[i].setAdvPaid(rs.getDouble("adv_paid"));
				dcb[i].setPrincipalDemand(rs.getDouble("pr_dmd"));
				dcb[i].setIntrDemand(rs.getDouble("int_dmd"));
				dcb[i].setOtherDemand(rs.getDouble("ochg_dmd"));
				dcb[i].setPrincipalCollected(rs.getDouble("pr_coll"));
				dcb[i].setIntrCollected(rs.getDouble("int_coll"));
				dcb[i].setPenalCollected(rs.getDouble("pint_coll"));
				dcb[i].setOtherCollected(rs.getDouble("ochg_coll"));
				dcb[i].setAdvCollected(rs.getDouble("adv_coll"));
				
				i++;
			}
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return dcb;
	}
	
	
	public DocumentsObject[] getActionDue(String date, String lnactype, int stagecode)  {
		System.out.println("stagecode == "+stagecode);
		DocumentsObject[] d=null;
		Connection conn=null;
		try{
			conn=getConnection();
			
			String duedate;
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//ResultSet rs = stmt.executeQuery("select * from StageMaster where stage_cd="+(stagecode-1)+" and ac_type='"+lnactype+"'");
			ResultSet rs = stmt.executeQuery("select * from StageMaster where stage_cd="+stagecode+"  and ac_type='"+lnactype+"'");
			ResultSet rs2;
			rs.last();
			if(rs.getRow()==0)
			{
				return d;
			}				
			rs.beforeFirst();
			rs.next();
			duedate=Validations.convertYMD(Validations.addDays(date,-rs.getInt("prd_to")));
			System.out.println("DueDate:"+duedate);
			//rs2 = st.executeQuery("select ac_no,concat(fname,' ',IFNULL(mname,''),' ',IFNULL(lname,'')),sh_type,sh_no,IFNULL(remd_date,'') from LoanMaster,CustomerMaster where CustomerMaster.cid=LoanMaster.cid and close_date is null and remd_no="+(stagecode-1)+" and concat(right(remd_date,4),'-',mid(remd_date,locate('/',remd_date)+1,(locate('/',remd_date,4)-locate('/',remd_date)-1)),'-',left(remd_date,locate('/',remd_date)-1))<'"+duedate+"'");
			rs2 = st.executeQuery("select ac_no,concat(fname,' ',IFNULL(mname,''),' ',IFNULL(lname,'')),sh_type,sh_no,IFNULL(remd_date,'') from LoanMaster,CustomerMaster where CustomerMaster.cid=LoanMaster.cid and close_date is null and remd_no=1 and concat(right(remd_date,4),'-',mid(remd_date,locate('/',remd_date)+1,(locate('/',remd_date,4)-locate('/',remd_date)-1)),'-',left(remd_date,locate('/',remd_date)-1))<'"+duedate+"'");
			rs2.last();
			d= new DocumentsObject[rs2.getRow()];
			rs2.beforeFirst();
			int i=0;
			while(rs2.next()){
				d[i]=new DocumentsObject();
				d[i].setAcno(rs2.getInt("ac_no")); 
				d[i].setLntype(rs2.getString(2));
				d[i].setMemtype(rs2.getString("sh_type"));
				d[i].setMemno(rs2.getInt("sh_no"));
				d[i].setOther_details(rs2.getString(5));//reminder date
				if(i==0)
				{
					d[0].setDoccode(rs.getInt("stage_cd"));
					d[0].setDoc_desc(rs.getString("descptn"));
				}					
				i++;			
			}					
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return d;
	}
	
	public String[] dcbDelete(int type,String m[]){
		String[] data=null;
		Connection conn=null;
		try{
			conn=getConnection();
			
			
			Statement stmt = conn.createStatement();
			if(type==1)//get howmany records are there
			{
				ResultSet rs = stmt.executeQuery("select month,count(*) from DCBReport group by month");
				rs.last();
				data = new String[rs.getRow()];
				rs.beforeFirst();
				int i=0;		
				while(rs.next()){
					data[i]=rs.getString(1)+" "+rs.getInt(2);
					i++;
				}
			}
			if(type==2){
				for(int i=0;i<m.length;i++)
					stmt.addBatch("delete from DCBReport where month='"+m[i]+"'");
				stmt.executeBatch();	
			}			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return data;
	}
	
	public DCBObject[] dcbSummary(String month)
	{
		DCBObject[] dcb=null;
		Connection conn=null;
		try{
			conn=getConnection();
			
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select sum(pr_arr),sum(int_arr),sum(pint_arr),sum(ochg_arr),sum(adv_paid),sum(pr_dmd),sum(int_dmd),sum(ochg_dmd),sum(pr_coll),sum(int_coll),sum(pint_coll),sum(ochg_coll),sum(adv_coll),ac_type from DCBReport where month='"+month+"' group by ac_type order by ac_type");
			rs.last();
			dcb=new DCBObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next()){
				dcb[i]=new DCBObject();
				dcb[i].setPrincipalArr(rs.getDouble(1));
				dcb[i].setIntrArr(rs.getDouble(2));
				dcb[i].setPenalIntArr(rs.getDouble(3));
				dcb[i].setOtherArr(rs.getDouble(4));
				dcb[i].setAdvPaid(rs.getDouble(5));
				dcb[i].setPrincipalDemand(rs.getDouble(6));
				dcb[i].setIntrDemand(rs.getDouble(7));
				dcb[i].setOtherDemand(rs.getDouble(8));
				dcb[i].setPrincipalCollected(rs.getDouble(9));
				dcb[i].setIntrCollected(rs.getDouble(10));
				dcb[i].setPenalCollected(rs.getDouble(11));
				dcb[i].setOtherCollected(rs.getDouble(12));
				dcb[i].setAdvCollected(rs.getDouble(13));
				dcb[i].setAccType(rs.getString(14));
				i++;
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return dcb;
	}
	
	public String[] stageType(int stagecode,String lnactype){
		String s[]=new String[2];
		s[0]=null;
		s[1]=null;
		Connection conn=null;
		try{
			conn=getConnection();
			
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select descptn,stage_ty,prd_from,prd_to from StageMaster where ac_type='"+lnactype+"' and stage_cd="+stagecode);
			if(rs.next())
			{ 
				s[0]=rs.getString("descptn");
				if(rs.getString("stage_ty").equalsIgnoreCase("A"))
					s[1]="Auto  "+rs.getInt("prd_from")+"-"+rs.getInt("prd_to")+" days";
				else if(rs.getString("stage_ty").equalsIgnoreCase("M"))
					s[1]="Manual  "+rs.getInt("prd_from")+"-"+rs.getInt("prd_to")+" days";
				else	
					s[1]="???  "+rs.getInt("prd_from")+"-"+rs.getInt("prd_to")+" days";
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return s;
	}
	
	
	//Kamals
	
	public int storeSpecialInt(String str[]) 
	{
		ResultSet rs=null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			rs=stmt.executeQuery("select lm.ac_no,stm.descptn from LoanMaster lm,StageMaster stm where lm.ac_no="+str[1]+" and lm.ac_type='"+str[0]+"' and stm.ac_type=lm.ac_type and stm.stage_cd=lm.remd_no and stm.splrt='T'");
			if(rs.next())
			{
				PreparedStatement pstmt=conn.prepareStatement("insert into LoanSplInterest values(?,?,?,?,?,?,?,date_format(sysdate(),'%d/%m/%Y %r'))");
				pstmt.setString(1,str[0]);
				pstmt.setInt(2,Integer.parseInt(str[1]));
				pstmt.setString(3,str[2]);
				pstmt.setString(4,"");
				pstmt.setDouble(5,Double.parseDouble(str[4]));
				pstmt.setString(6,str[5]);
				pstmt.setString(7,str[6]);
				if(pstmt.executeUpdate()==1)
					return 1;
			}
			else
				return 2;
			
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return 0;
	}
	
	public LoanReportObject[] getLoanDetails(String ln_type,int acno,String fdate,String tdate,int type,String query) 
	{
		System.out.println("---------------1-------------");
		System.out.println("Acctype"+ln_type+"Accno"+acno+"FromDate"+fdate);
		System.out.println("ToDate"+tdate+"Type"+type+"Query"+query);		LoanReportObject[] lnobj=null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement stat=conn.createStatement();
			ResultSet rs=null;
			if(query==null)
			{    
				
				System.out.println("=====InSide Query====="+query);
				if(type==0)
					rs=stmt.executeQuery("select ac_type,ac_no,pay_ac_type,pay_ac_no,sh_type,sh_no,no_inst,sanc_amt,int_rate,inst_amt,int_upto_date,holday_mth,pps_desc,remd_no,concat(fname,' ',mname,' ',lname) as name from LoanMaster,LoanPurposes,CustomerMaster   where  CustomerMaster.cid=LoanMaster.cid and pps_code=pps_no and ac_type="+ln_type+" and ac_no="+acno);
				else if(type==1)//Loan Meeting Agenda Print
					rs=stmt.executeQuery("select lm.*,concat_ws(' ',fname,mname,lname) as name,cm.scst,ca.address,ca.city,ca.pin,sm.cid,lp.pps_desc from LoanMaster lm,CustomerMaster cm,ShareMaster sm,CustomerAddr ca,LoanPurposes lp where lm.ac_no>="+acno+" and lm.ac_type='"+ln_type+"' and lm.sh_no=sm.ac_no and lm.sh_type=sm.ac_type and cm.cid=sm.cid and ca.cid=cm.cid and ca.addr_type=sm.addr_type and lp.pps_no=lm.pps_code and lm.ve_tml is not null and (lm.sanc_date is null or lm.sanc_ver!='N')");
				else if(type==2)//Loan Spl Int Updn
					rs=stmt.executeQuery("select lm.*,concat_ws(' ',fname,mname,lname) as name,pps_desc,stm.descptn from LoanMaster lm,CustomerMaster cm,ShareMaster sm,CustomerAddr ca,LoanPurposes lp,StageMaster stm where lm.ac_no="+acno+" and lm.ac_type='"+ln_type+"' and lm.sh_no=sm.ac_no and lm.sh_type=sm.ac_type and cm.cid=sm.cid and ca.cid=cm.cid and ca.addr_type=sm.addr_type and lp.pps_no=lm.pps_code and lm.ve_tml is not null and lm.sanc_ver='Y' and lm.default_ind like 'Y%' and stm.ac_type=lm.ac_type and stm.stage_cd=lm.remd_no");
				else if(type==3)//Opened Loans Report
					rs=stmt.executeQuery("select distinct lm.*,concat_ws(' ',fname,mname,lname) as name,cm.scst,ca.address,ca.city,ca.pin,sm.sh_ind,lp.pps_desc from LoanMaster lm,CustomerMaster cm,ShareMaster sm,CustomerAddr ca,LoanPurposes lp where lm.ac_type='"+ln_type+"' and lm.sh_no=sm.ac_no and lm.sh_type=sm.ac_type and cm.cid=sm.cid and ca.cid=cm.cid and ca.addr_type=sm.addr_type and lp.pps_no=lm.pps_code and lm.ve_tml is not null and lm.sanc_ver='Y' and lm.close_date is null and concat(right(sanc_date,4),'-',mid(sanc_date,locate('/',sanc_date)+1,(locate('/',sanc_date,4)-locate('/',sanc_date)-1)),'-',left(sanc_date,locate('/',sanc_date)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' "); // Code added by Murugesh on 23/03/2006
				else if(type==4)//Opened Loans Report
					rs=stmt.executeQuery("select distinct lm.*,concat_ws(' ',fname,mname,lname) as name,cm.scst,ca.address,ca.city,ca.pin,sm.sh_ind,lp.pps_desc from LoanMaster lm,CustomerMaster cm,ShareMaster sm,CustomerAddr ca,LoanPurposes lp where lm.ac_type='"+ln_type+"' and lm.sh_no=sm.ac_no and lm.sh_type=sm.ac_type and cm.cid=sm.cid and ca.cid=cm.cid and ca.addr_type=sm.addr_type and lp.pps_no=lm.pps_code and lm.ve_tml is not null and lm.sanc_ver='Y' and concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,(locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' "); // Code added by Murugesh on 23/03/2006
			}
			else
			{    
				if(type==0)
					rs=stmt.executeQuery("select ac_type,ac_no,pay_ac_type,pay_ac_no,sh_type,sh_no,no_inst,sanc_amt,int_rate,inst_amt,int_upto_date,holday_mth,pps_desc,remd_no,concat_ws(' ',fname,mname,lname) as name from LoanMaster,LoanPurposes,CustomerMaster   where  CustomerMaster.cid=LoanMaster.cid and pps_code=pps_no and ac_type="+ln_type+" and ac_no="+acno+" and ("+query+")");
				else if(type==1)//Loan Meeting Agenda Print
					rs=stmt.executeQuery("select lm.*,concat_ws(' ',fname,mname,lname) as name,cm.scst,ca.address,ca.city,ca.pin,sm.cid,lp.pps_desc from LoanMaster lm,CustomerMaster cm,ShareMaster sm,CustomerAddr ca,LoanPurposes lp where lm.ac_no>="+acno+" and lm.ac_type='"+ln_type+"' and lm.sh_no=sm.ac_no and lm.sh_type=sm.ac_type and cm.cid=sm.cid and ca.cid=cm.cid and ca.addr_type=sm.addr_type and lp.pps_no=lm.pps_code and lm.ve_tml is not null and (lm.sanc_date is null or lm.sanc_ver!='N') and ("+query+")");
				else if(type==2)//Loan Spl Int Updn
					rs=stmt.executeQuery("select lm.*,concat_ws(' ',fname,mname,lname) as name,pps_desc,stm.descptn from LoanMaster lm,CustomerMaster cm,ShareMaster sm,CustomerAddr ca,LoanPurposes lp,StageMaster stm where lm.ac_no="+acno+" and lm.ac_type='"+ln_type+"' and lm.sh_no=sm.ac_no and lm.sh_type=sm.ac_type and cm.cid=sm.cid and ca.cid=cm.cid and ca.addr_type=sm.addr_type and lp.pps_no=lm.pps_code and lm.ve_tml is not null and lm.sanc_ver='Y' and lm.default_ind='T' and stm.ac_type=lm.ac_type and stm.stage_cd=lm.remd_no and ("+query+")");
				else if(type==3){//Opened Loans Report
					//rs=stmt.executeQuery("select lm.*,concat_ws(fname,' ',mname,' ',lname) as name,cm.scst,ca.address,ca.city,ca.pin,sm.sh_ind,lp.pps_desc from LoanMaster lm,CustomerMaster cm,ShareMaster sm,CustomerAddr ca,LoanPurposes lp where lm.ac_type='"+ln_type+"' and lm.sh_no=sm.ac_no and lm.sh_type=sm.ac_type and cm.cid=sm.cid and ca.cid=cm.cid and ca.addr_type=sm.addr_type and lp.pps_no=lm.pps_code and lm.ve_tml is not null and lm.sanc_ver='Y' and concat(right(sanc_date,4),'-',mid(sanc_date,locate('/',sanc_date)+1,(locate('/',sanc_date,4)-locate('/',sanc_date)-1)),'-',left(sanc_date,locate('/',sanc_date)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ("+query+") "); // Code changed by Murugesh on 23/03/2006
					rs=stmt.executeQuery("select distinct lm.*,concat_ws(' ',fname,mname,lname) as name,cm.scst,ca.address,ca.city,ca.pin,sm.sh_ind,lp.pps_desc from LoanMaster lm,CustomerMaster cm,ShareMaster sm,CustomerAddr ca,LoanPurposes lp where lm.ac_type='"+ln_type+"' and lm.sh_no=sm.ac_no and lm.sh_type=sm.ac_type and cm.cid=sm.cid and ca.cid=cm.cid and ca.addr_type=sm.addr_type and lp.pps_no=lm.pps_code and lm.ve_tml is not null and lm.sanc_ver='Y' and lm.close_date is null and concat(right(sanc_date,4),'-',mid(sanc_date,locate('/',sanc_date)+1,(locate('/',sanc_date,4)-locate('/',sanc_date)-1)),'-',left(sanc_date,locate('/',sanc_date)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ("+query+") "); // Code added by Murugesh on 23/03/2006
				}
				else if(type==4){//Opened Loans Report
					//rs=stmt.executeQuery("select lm.*,concat_ws(fname,' ',mname,' ',lname) as name,cm.scst,ca.address,ca.city,ca.pin,sm.sh_ind,lp.pps_desc from LoanMaster lm,CustomerMaster cm,ShareMaster sm,CustomerAddr ca,LoanPurposes lp where lm.ac_type='"+ln_type+"' and lm.sh_no=sm.ac_no and lm.sh_type=sm.ac_type and cm.cid=sm.cid and ca.cid=cm.cid and ca.addr_type=sm.addr_type and lp.pps_no=lm.pps_code and lm.ve_tml is not null and lm.sanc_ver='Y' and concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,(locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ("+query+") "); // Code changed by Murugesh on 23/03/2006
					rs=stmt.executeQuery("select distinct lm.*,concat_ws(' ',fname,mname,lname) as name,cm.scst,ca.address,ca.city,ca.pin,sm.sh_ind,lp.pps_desc from LoanMaster lm,CustomerMaster cm,ShareMaster sm,CustomerAddr ca,LoanPurposes lp where lm.ac_type='"+ln_type+"' and lm.sh_no=sm.ac_no and lm.sh_type=sm.ac_type and cm.cid=sm.cid and ca.cid=cm.cid and ca.addr_type=sm.addr_type and lp.pps_no=lm.pps_code and lm.ve_tml is not null and lm.sanc_ver='Y' and concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,(locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ("+query+") "); // Code added by Murugesh on 23/03/2006
				}
			}
			rs.last(); 
			System.out.println("<=====ResultSet=====>"+rs.getRow());
			rs.beforeFirst();
			if(rs.next())
			{
				rs.last();
				lnobj=new LoanReportObject[rs.getRow()];
				rs.beforeFirst();
			}
			
			int i=0;
			while(rs.next())
			{
				lnobj[i]=new LoanReportObject();
				
				if(type!=0 && type!=2)
				{
					System.out.println("1....");
					
					lnobj[i].addr.setAddress(rs.getString("ca.address"));	
					lnobj[i].addr.setCity(rs.getString("ca.city"));	
					lnobj[i].addr.setPin(rs.getString("ca.pin"));	
					
					lnobj[i].setAddress(rs.getString("ca.address")+"/"+rs.getString("ca.city")+"/"+rs.getString("ca.pin"));
					if(rs.getString("cm.scst")!=null)
						if(rs.getString("cm.scst").equals("Y"))
							lnobj[i].setScstInd("YES");
						else
							lnobj[i].setScstInd("NO");
					
					lnobj[i].setLoanPurpose(rs.getString("lp.pps_desc"));
					lnobj[i].setRequiredAmount(rs.getDouble("req_amt"));
					lnobj[i].setApplicationSrlNo(rs.getInt("appn_srl"));
					lnobj[i].setApplicationDate(rs.getString("appn_date"));
					if(rs.getString("sex_cd")!=null)
						if(rs.getString("sex_cd").equals("M"))
							lnobj[i].setSexInd('M');	
						else
							lnobj[i].setSexInd('F');
					if(rs.getString("wk_sect")!=null)
						if(rs.getString("wk_sect").equals("Y"))
							lnobj[i].setWeakerSection("YES");
						else
							lnobj[i].setWeakerSection("NO");
					
					ResultSet rs1=stat.executeQuery("select * from BorrowerMaster where ac_no="+rs.getInt("ac_no")+" and ac_type='"+ln_type+"' and type='S'");
					
					Vector c=null;
					if(rs1.next())
					{
						rs1.last();
						c=new Vector(rs1.getRow());
						rs1.beforeFirst();
					}
					else
						c=null;
					
					
					while(rs1.next())	
						c.add(rs1.getString("ac_type")+" "+rs1.getInt("ac_no"));			
					lnobj[i].setSurities(c);
					
					
				}
				
				
				lnobj[i].setAccType(rs.getString("ac_type"));
				lnobj[i].setAccNo(rs.getInt("ac_no"));
				if(type==3 ||type==4)
					lnobj[i].setShareDet(rs.getString("sh_type")+"  "+rs.getInt("sh_no")+" "+rs.getString("sm.sh_ind"));
				else
					lnobj[i].setShareDet(rs.getString("sh_type")+"  "+rs.getInt("sh_no"));
				lnobj[i].setSavingDet(rs.getString("pay_ac_type")+"  "+rs.getInt("pay_ac_no"));
				lnobj[i].setNoInstallments(rs.getInt("no_inst"));
				lnobj[i].setSanctionDate(rs.getString("sanc_date"));
				lnobj[i].setSanctionedAmount(rs.getDouble("sanc_amt"));
				lnobj[i].setLoanIntRate(rs.getDouble("int_rate"));
				lnobj[i].setInstallmentAmount(rs.getDouble("inst_amt"));
				lnobj[i].setLoanPurpose(rs.getString("pps_desc"));
				//				lnobj[i].setLoanBalance();
				lnobj[i].setIntUptoDate(rs.getString("int_upto_date"));
				lnobj[i].setHolidayPeriod(rs.getInt("holday_mth"));
				lnobj[i].setName(rs.getString("name"));
				
				lnobj[i].setPrioritySectorCode(rs.getString("psect_cd"));
				//				lnobj[i].setDisbursementDate();
				
				lnobj[i].setRemainderNo(rs.getInt("remd_no"));
				
				lnobj[i].setPayAccountNo(rs.getInt("pay_ac_no"));
				lnobj[i].setPayAccountType(rs.getString("pay_ac_type"));
				if(type==2)
					lnobj[i].setRemainderDesc(rs.getString("stm.descptn"));	
				i++;
				
			}
			
			}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return lnobj;
		
	}
	
	public LoanReportObject[] getLoanRemainderSummary(String ac_type,int stage_no) 
	{
		ResultSet rs=null;
		LoanReportObject ln[]=null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery("select * from LoanHistory where ac_type='"+ac_type+"' and template_no=0 and ref_no=0 and prt_ind='F' and stage_no="+stage_no+"");
			//			concat(right(prt_date,4),'-',mid(prt_date,locate('/',prt_date)+1,(locate('/',prt_date,4)-locate('/',prt_date)-1)),'-',left(prt_date,locate('/',prt_date)-1))=date_format(sysdate(),'%Y-%m-%d') and 
			if(rs.next())
			{
				rs.last();
				ln=new LoanReportObject[rs.getRow()];
				rs.beforeFirst();
			}
			int i=0;
			
			while(rs.next())
			{
				ln[i]=new LoanReportObject();
				
				ln[i].setAccType(rs.getString("ac_type"));
				ln[i].setAccNo(rs.getInt("ac_no"));
				ln[i].setShareNo(rs.getInt("sh_no"));
				ln[i].setShareType(rs.getString("sh_ty"));
				ln[i].setName(rs.getString("name"));
				ln[i].setTemplateNo(rs.getInt("template_no"));
				ln[i].setStageNo(rs.getInt("stage_no"));
				ln[i].setRefNo(rs.getInt("ref_no"));
				ln[i].setPrintedDate(rs.getString("prt_date"));
				ln[i].setSanctionDate(rs.getString("sanc_dt"));
				
				ln[i].setSanctionedAmount(rs.getDouble("sanc_amt"));
				ln[i].setInstallmentAmount(rs.getDouble("instalment_amt"));
				ln[i].setNoOfInstals(rs.getInt("no_of_instalment"));
				ln[i].setCurrentInstallment(rs.getDouble("currentinstalment"));
				ln[i].setLoanIntRate(rs.getDouble("interest"));
				ln[i].setPenalInterest(rs.getDouble("penal_int"));
				ln[i].setOtherCharges(rs.getDouble("oth_charges"));
				ln[i].setLoanBalance(rs.getDouble("loan_bal"));
				ln[i].setAmountOverDue(rs.getDouble("amt_overdue"));
				ln[i].setPrintInd(rs.getString("prt_ind"));
				
				ln[i].setIntUptoDate(rs.getString("int_upto"));
				ln[i].setTransactiondate(rs.getString("lsttrn_date"));
				ln[i].setDefaultedMths(rs.getInt("def_mnths"));
				ln[i].setShareAmt(rs.getDouble("share_amt"));
				ln[i].setBranchCode(rs.getString("branch_code"));
				i++;	
			}
			
			
			
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return ln;
	}
	
	
	public Object[][] getLoanRemainderReport(String lnacty,String ac_no) 
	{
		Object ob[][]=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		int k=0;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery("select LoanMaster.ac_type,LoanMaster.ac_no,appn_srl,appn_date,req_amt,sh_no,sh_type,LoanMaster.pay_ac_type,LoanMaster.pay_ac_no,sanc_amt,sanc_date,inst_amt,fname,mname,lname,address,city,state,pin,country,phno,remd_no from LoanMaster,CustomerMaster,CustomerAddr,ShareMaster where LoanMaster.ac_type='"+lnacty+"' and LoanMaster.ac_no in ("+ac_no+") and ShareMaster.ac_no=LoanMaster.sh_no and ShareMaster.ac_type=LoanMaster.sh_type and CustomerMaster.cid=ShareMaster.cid and CustomerAddr.cid=ShareMaster.cid and CustomerAddr.addr_type=LoanMaster.addr_type and LoanMaster.ve_tml is not null and sanc_ver='Y'");			
			rsmd=rs.getMetaData();
			int a=rsmd.getColumnCount();
			if(rs.next())
			{
				rs.last();
				ob=new Object[rs.getRow()][a];	
				rs.beforeFirst();
			}
			int i=0;
			int j=0;
			System.out.println("1........");
			
			while(rs.next())
			{
				System.out.println("3........");
				for(k=0;k<ob.length;k++)
					ob[i][k]=new Object();
				
				ob[i][j++]=rs.getString("ac_type");
				ob[i][j++]=String.valueOf(rs.getInt("ac_no"));
				
				ob[i][j++]=String.valueOf(rs.getInt("appn_srl"));
				ob[i][j++]=rs.getString("appn_date");
				ob[i][j++]=String.valueOf(rs.getDouble("req_amt"));
				ob[i][j++]=String.valueOf(rs.getInt("sh_no"));
				ob[i][j++]=rs.getString("sh_type");
				ob[i][j++]=rs.getString("pay_ac_type");
				
				ob[i][j++]=String.valueOf(rs.getInt("pay_ac_no"));
				ob[i][j++]=String.valueOf(rs.getDouble("sanc_amt"));
				ob[i][j++]=String.valueOf(rs.getString("sanc_date"));
				ob[i][j++]=String.valueOf(rs.getDouble("inst_amt"));
				ob[i][j++]=rs.getString("fname");
				ob[i][j++]=rs.getString("mname");
				ob[i][j++]=rs.getString("lname");
				ob[i][j++]=rs.getString("address");
				ob[i][j++]=rs.getString("city");
				ob[i][j++]=rs.getString("state");
				ob[i][j++]=String.valueOf(rs.getInt("pin"));
				ob[i][j++]=rs.getString("country");
				ob[i][j++]=String.valueOf(rs.getInt("phno"));
				ob[i][j++]=String.valueOf(rs.getInt("remd_no"));
				j=0;
				i++;	
				
			}
			
			return ob;
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return ob;
	}
	
	public boolean updateLoanRemainderSummary(LoanReportObject ln[]) 
	{
		Connection conn=null;
		try{
			conn=getConnection();
			//Statement stmt=conn.createStatement();
			if(ln!=null)
			{
				PreparedStatement pst=conn.prepareStatement("update LoanMaster set remd_no=?,remd_date=date_format(sysdate(),'%d/%m/%Y') where ac_no=? and ac_type=?");
				PreparedStatement pstmt=conn.prepareStatement("update LoanHistory set template_no=?,ref_no=?,prt_ind='T',action_particulars=? where ac_no=? and ac_type=? and prt_date=date_format(sysdate(),'%d/%m/%Y')");
				PreparedStatement pstmt1=conn.prepareStatement("delete from LoanHistory where ac_no=? and ac_type=? and prt_date=?");
				for(int i=0;i<ln.length;i++)
				{
					if(ln[i].getTemplateNo()!=0 && ln[i].getRefNo()!=0)			
					{
						System.out.println("here.......");
						pstmt.setInt(1,ln[i].getTemplateNo());
						pstmt.setInt(2,ln[i].getRefNo());
						pstmt.setString(3,"Notice sent vide "+ln[i].getRefNo());
						pstmt.setInt(4,ln[i].getAccNo());
						pstmt.setString(5,ln[i].getAccType());
						pstmt.addBatch();
						
						pst.setInt(1,ln[i].getStageNo());
						pst.setInt(2,ln[i].getAccNo());
						pst.setString(3,ln[i].getAccType());
						pst.addBatch();
						
						
					}
					else
					{
						pstmt1.setInt(1,ln[i].getAccNo());
						pstmt1.setString(2,ln[i].getAccType());
						pstmt1.setString(3,ln[i].getPrintedDate());						
						pstmt1.addBatch();
					}
				}
				int a[]=pstmt.executeBatch();
				pst.executeBatch();
				for(int i=0;i<a.length;i++)
				{
					System.out.println("a="+a[i]);
					if(a[i]==0)
						return false;
					
				}
				return true;
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return false;
	}
	
	public File getFile(String modcode,int st) 
	{
		File f=null;
		ResultSet rs=null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery("select * from LoanRemainder where ac_type='"+modcode+"' and stage="+st+"");
			
			String str="";
			if(rs.next())
			{
				f=new File("sample.txt");
				str=rs.getString("letter");
				FileOutputStream fin=new FileOutputStream(f);
				byte b[]=str.getBytes();
				for(int i=0;i<b.length;i++)
				{
					
					fin.write(b[i]);	
					
				}
				fin.close();
				
				
				
				
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return f;
	}
	
	public boolean storeFile(int modcode,int stage,File f) 
	{
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			GregorianCalendar gc=new GregorianCalendar();
			String table="t"+gc.getTimeInMillis();
			
			FileInputStream fis=new FileInputStream(f);
			File file=new File("c:\\"+table);
			FileOutputStream fc=new FileOutputStream(file);
			int ii=0;
			while((ii=fis.read())!=-1)
				fc.write(ii);
			fc.close();
			
			System.out.println("File:"+table);
			
			if(stmt.executeUpdate("insert into LoanRemainder values("+modcode+","+stage+",LOAD_FILE('c:/"+table+"'))")==1)
				return true;
			return false;
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return false;
	}			
	
	public String[] getColumns(String lnacty) 
	{
		String str[]=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			rs=stmt.executeQuery("select distinct lm.ac_type,lm.ac_no,appn_srl,appn_date,req_amt,sm.ac_no,sm.ac_type,lm.pay_ac_type,lm.pay_ac_no,sanc_amt,sanc_date,inst_amt,fname,mname,lname,address,city,state,pin,country,phno from LoanMaster lm,CustomerMaster,CustomerAddr,ShareMaster sm where lm.ac_type='"+lnacty+"' and lm.default_ind=1 and sm.ac_no=lm.sh_no and sm.ac_type=lm.sh_type and CustomerMaster.cid=sm.cid and CustomerAddr.cid=sm.cid and CustomerAddr.addr_type=1 and lm.ve_tml is not null and sanc_ver='Y'");			
			rsmd=rs.getMetaData();
			int a=rsmd.getColumnCount();
			str=new String[a];
			
			for(int i=0;i<a;i++)
				str[i]=rsmd.getColumnName(i+1);
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return str;
	}
	public PSWSObject[] getPSWSDetails(String prdate,int pr_code,int sel,String query) 
	{
		PSWSObject psws[]=null;
		ResultSet rs=null;
		int j=0;
		int pr_cd=0;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			if(sel==3)//for deleting
			{
				int a=stmt.executeUpdate("delete from PSWSDetails where process_dt='"+prdate+"' and pr_code="+pr_code+"");
				System.out.println("a == "+a);
				//if(a!=0);
				psws=new PSWSObject[1];
				return psws;
				
			}
			else if(sel==1)//for Report
			{
				
				System.out.println("1..............");
				if(query==null)
					rs=stmt.executeQuery("select pd.pr_code,pm.pr_desc,count(*),sum(sanc_amt),sum(advanced_amt),sum(bal_outstding),sum(amt_overdue) from PSWSDetails pd,PriorityMaster pm where process_dt='"+prdate+"' and pm.pr_code=pd.pr_code group by pd.pr_code order by pd.pr_code");
				else
					rs=stmt.executeQuery("select pd.pr_code,pm.pr_desc,count(*),sum(sanc_amt),sum(advanced_amt),sum(bal_outstding),sum(amt_overdue) from PSWSDetails pd,PriorityMaster pm where process_dt='"+prdate+"' and pm.pr_code=pd.pr_code and ("+query+") group by pd.pr_code order by pd.pr_code");
				
				if(rs.next())
				{
					rs.last();
					psws=new PSWSObject[rs.getRow()];
					rs.beforeFirst();
				}
				int i=0;
				
				while(rs.next())
				{
					System.out.println("2..............");
					psws[i]=new PSWSObject();
					psws[i].initializeArray(5);
					psws[i].initialize(0);
					psws[i].setPrioritySectorCode(rs.getInt("pd.pr_code"));	
					psws[i].setPrioritySectorDesc(rs.getString("pm.pr_desc"));	
					psws[i].det[0].setNoOfBorrowers(rs.getInt(3));	
					psws[i].det[0].setSanctionedAmt(rs.getDouble(4));	
					psws[i].det[0].setAmtAdvanced(rs.getDouble(5));	
					psws[i].det[0].setLoanBalance(rs.getDouble(6));	
					psws[i].det[0].setAmtOverDue(rs.getDouble(7));	
					System.out.println("4..............");
					i++;
					
				}
				
				if(query==null)
					rs=stmt.executeQuery("select pd.pr_code,pm.pr_desc,count(*),sum(sanc_amt),sum(advanced_amt),sum(bal_outstding),sum(amt_overdue) from PSWSDetails pd,PriorityMaster pm where process_dt='"+prdate+"' and pd.wk_sect='Y' and pm.pr_code=pd.pr_code group by pd.pr_code order by pd.pr_code");
				else
					rs=stmt.executeQuery("select pd.pr_code,pm.pr_desc,count(*),sum(sanc_amt),sum(advanced_amt),sum(bal_outstding),sum(amt_overdue) from PSWSDetails pd,PriorityMaster pm where process_dt='"+prdate+"' and pd.wk_sect='Y' and pm.pr_code=pd.pr_code and ("+query+") group by pd.pr_code order by pd.pr_code");
				
				while(rs.next())
				{
					System.out.println("3..............");
					pr_cd=rs.getInt("pd.pr_code");
					j=0;
					for(j=0;j<psws.length;j++)
					{
						if(pr_cd==psws[j].getPrioritySectorCode())
							break;
					}
					if(j!=psws.length)
					{
						psws[j].initialize(1);
						psws[j].det[1].setNoOfBorrowers(rs.getInt(3));	
						psws[j].det[1].setSanctionedAmt(rs.getDouble(4));	
						psws[j].det[1].setAmtAdvanced(rs.getDouble(5));	
						psws[j].det[1].setLoanBalance(rs.getDouble(6));	
						psws[j].det[1].setAmtOverDue(rs.getDouble(7));	
					}
				}
				
				if(query==null)
					rs=stmt.executeQuery("select pd.pr_code,pm.pr_desc,count(*),sum(sanc_amt),sum(advanced_amt),sum(bal_outstding),sum(amt_overdue) from PSWSDetails pd,PriorityMaster pm where process_dt='"+prdate+"' and pd.wk_sect='Y' and (pd.category='SC' || pd.category='ST') and pm.pr_code=pd.pr_code group by pd.pr_code order by pd.pr_code");
				else
					rs=stmt.executeQuery("select pd.pr_code,pm.pr_desc,count(*),sum(sanc_amt),sum(advanced_amt),sum(bal_outstding),sum(amt_overdue) from PSWSDetails pd,PriorityMaster pm where process_dt='"+prdate+"' and pd.wk_sect='Y' and (pd.category='SC' || pd.category='ST') and pm.pr_code=pd.pr_code and ("+query+") group by pd.pr_code order by pd.pr_code");
				
				while(rs.next())
				{
					System.out.println("3..............");
					pr_cd=rs.getInt("pd.pr_code");
					j=0;
					for(j=0;j<psws.length;j++)
					{
						if(pr_cd==psws[j].getPrioritySectorCode())
							break;
					}
					if(j!=psws.length)
					{
						psws[j].initialize(2);
						psws[j].det[2].setNoOfBorrowers(rs.getInt(3));	
						psws[j].det[2].setSanctionedAmt(rs.getDouble(4));	
						psws[j].det[2].setAmtAdvanced(rs.getDouble(5));	
						psws[j].det[2].setLoanBalance(rs.getDouble(6));	
						psws[j].det[2].setAmtOverDue(rs.getDouble(7));	
					}
				}
				
				if(query==null)
					rs=stmt.executeQuery("select pd.pr_code,pm.pr_desc,count(*),sum(sanc_amt),sum(advanced_amt),sum(bal_outstding),sum(amt_overdue) from PSWSDetails pd,PriorityMaster pm where process_dt='"+prdate+"' and pd.wk_sect='Y' and pd.category!='SC' and pd.category!='ST' and pd.sex_cd='F' and pm.pr_code=pd.pr_code group by pd.pr_code order by pd.pr_code");
				else
					rs=stmt.executeQuery("select pd.pr_code,pm.pr_desc,count(*),sum(sanc_amt),sum(advanced_amt),sum(bal_outstding),sum(amt_overdue) from PSWSDetails pd,PriorityMaster pm where process_dt='"+prdate+"' and pd.wk_sect='Y' and pd.category!='SC' and pd.category!='ST' and pd.sex_cd='F' and pm.pr_code=pd.pr_code and ("+query+") group by pd.pr_code order by pd.pr_code");
				while(rs.next())
				{
					pr_cd=rs.getInt("pd.pr_code");
					j=0;
					for(j=0;j<psws.length;j++)
					{
						if(pr_cd==psws[j].getPrioritySectorCode())
							break;
					}
					if(j!=psws.length)
					{
						System.out.println("3..............");
						psws[j].initialize(3);
						psws[j].det[3].setNoOfBorrowers(rs.getInt(3));	
						psws[j].det[3].setSanctionedAmt(rs.getDouble(4));	
						psws[j].det[3].setAmtAdvanced(rs.getDouble(5));	
						psws[j].det[3].setLoanBalance(rs.getDouble(6));	
						psws[j].det[3].setAmtOverDue(rs.getDouble(7));	
					}
				}
				if(query==null)
					rs=stmt.executeQuery("select pd.pr_code,pm.pr_desc,count(*),sum(sanc_amt),sum(advanced_amt),sum(bal_outstding),sum(amt_overdue) from PSWSDetails pd,PriorityMaster pm where process_dt='"+prdate+"' and pd.wk_sect='Y' and pd.category!='SC' and pd.category!='ST' and pd.sex_cd!='F' and pd.category='Others' and pm.pr_code=pd.pr_code group by pd.pr_code order by pd.pr_code");
				else
					rs=stmt.executeQuery("select pd.pr_code,pm.pr_desc,count(*),sum(sanc_amt),sum(advanced_amt),sum(bal_outstding),sum(amt_overdue) from PSWSDetails pd,PriorityMaster pm where process_dt='"+prdate+"' and pd.wk_sect='Y' and pd.category!='SC' and pd.category!='ST' and pd.sex_cd!='F' and pd.category='Others' and pm.pr_code=pd.pr_code and ("+query+") group by pd.pr_code order by pd.pr_code");
				while(rs.next())
				{
					System.out.println("3..............");
					pr_cd=rs.getInt("pd.pr_code");
					j=0;
					for(j=0;j<psws.length;j++)
					{
						if(pr_cd==psws[j].getPrioritySectorCode())
							break;
					}
					if(j!=psws.length)
					{
						psws[j].initialize(4);
						psws[j].det[4].setNoOfBorrowers(rs.getInt(3));	
						psws[j].det[4].setSanctionedAmt(rs.getDouble(4));	
						psws[j].det[4].setAmtAdvanced(rs.getDouble(5));	
						psws[j].det[4].setLoanBalance(rs.getDouble(6));	
						psws[j].det[4].setAmtOverDue(rs.getDouble(7));	
					}
				}
			}
			else if(sel==2)//for Scheduling
			{
				if(query==null)
					rs=stmt.executeQuery("select pd.*,pm.pr_desc,concat(cm.fname,' ',cm.mname,' ',lname) as name from PSWSDetails pd,PriorityMaster pm,CustomerMaster cm,LoanMaster lm where process_dt='"+prdate+"' and pd.pr_code="+pr_code+" and pm.pr_code=pd.pr_code and lm.ac_type=pd.ac_type and lm.ac_no=pd.ac_no and cm.cid=lm.cid order by pd.pr_code");
				else
					rs=stmt.executeQuery("select pd.*,pm.pr_desc,concat(cm.fname,' ',cm.mname,' ',lname) as name from PSWSDetails pd,PriorityMaster pm,CustomerMaster cm,LoanMaster lm where process_dt='"+prdate+"' and pd.pr_code="+pr_code+" and pm.pr_code=pd.pr_code and lm.ac_type=pd.ac_type and lm.ac_no=pd.ac_no and cm.cid=lm.cid and ("+query+") order by pd.pr_code");
				
				if(rs.next())
				{
					rs.last();
					psws=new PSWSObject[rs.getRow()];
					rs.beforeFirst();
				}
				int i=0;
				while(rs.next())
				{
					System.out.println("2..............");
					psws[i]=new PSWSObject();
					psws[i].initializeArray(1);
					psws[i].initialize(0);
					psws[i].setPrioritySectorCode(rs.getInt("pd.pr_code"));	
					psws[i].setPrioritySectorDesc(rs.getString("pm.pr_desc"));	
					psws[i].setAccType(rs.getString("pd.ac_type"));	
					psws[i].setAccNo(rs.getInt("pd.ac_no"));	
					psws[i].setName(rs.getString("name"));
					psws[i].det[0].setSanctionedAmt(rs.getDouble("pd.sanc_amt"));	
					psws[i].det[0].setAmtAdvanced(rs.getDouble("pd.advanced_amt"));	
					psws[i].det[0].setLoanBalance(rs.getDouble("pd.bal_outstding"));	
					psws[i].det[0].setAmtOverDue(rs.getDouble("pd.amt_overdue"));	
					psws[i].setSexInd(rs.getString("sex_cd"));	
					psws[i].setWKSect(rs.getString("wk_sect"));	
					psws[i].setCategory(rs.getString("category"));	
					
					i++;
					
				}
				
			}			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return psws;
	}
	
	
	public String[] getProcessedDates() 
	{
		String str[]=null;
		ResultSet rs=null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery("select distinct process_dt from PSWSDetails");
			if(rs.next())
			{
				rs.last();
				str=new String[rs.getRow()];
				rs.beforeFirst();
			}
			int i=0;
			while(rs.next())
			{
				str[i]=rs.getString(1);	
				i++;
			}
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return str;
	}
	
	public int processPSWS(String pdate,String utml,String uname,int ty) 	
	{
		ResultSet rs=null;
		ResultSet rs_trn=null;
		double prnbal=0,prevbal=0,prnpaid=0,schamt;
		
		System.out.println("Process Date"+pdate+"Utml"+utml+"Type"+ty+"Uname"+uname);
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement stat=conn.createStatement();
			rs=stmt.executeQuery("select * from PSWSDetails where process_dt='"+pdate+"'");
			if(rs.next() && ty==0)
				return 3;
			
			if(ty==1)
				stmt.executeUpdate("delete from PSWSDetails where process_dt='"+pdate+"'");
			rs.close();
			
			
			PreparedStatement pstmt=conn.prepareStatement("insert into PSWSDetails values(?,?,?,?,?,?,?,?,?,?,?,?,?,'"+getSysDateTime()+"')");
			
			rs=stmt.executeQuery("select ln.ac_type,ln.ac_no,ln.psect_cd,ln.sanc_amt,ln.wk_sect,ln.sex_cd,ln.disb_left,ln.sex_cd,cm.fname,cm.mname,cm.lname,cm.scst from LoanMaster ln,CustomerMaster cm where ln.sanc_ver='Y' and ln.close_date is null and cm.cid=ln.cid and psect_cd is not null");
			while(rs.next())
			{
				
				rs_trn=stat.executeQuery("select trn_type,pr_bal,pr_amt from LoanTransaction  lt where lt.ac_type='"+rs.getString("ln.ac_type")+"' and lt.ac_no="+rs.getString("ln.ac_no")+" and  concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))<='"+Validations.convertYMD(pdate)+"' order by concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1)),trn_seq");
				prnbal=0;prevbal=0;prnpaid=0;schamt=0;
				
					
				while(rs_trn.next())
				{
					String trntype=rs_trn.getString("trn_type");
					int row=rs_trn.getRow();
					if(row==1)
						prnbal=rs_trn.getDouble("pr_bal");
					
					if(trntype!=null)
					{	
					if(trntype.equals("R") || trntype.equals("D"))
					{
						if(trntype.equals("R"))
						{
							prnpaid+=rs_trn.getDouble("pr_amt");
							prnbal=rs_trn.getDouble("pr_bal");
						}
					}
					else if(trntype.equals("S"))
						schamt+=rs_trn.getDouble("pr_amt");			
					
					}
					
					//if scheduled amt is greater that principal paid means go for penal interest calculation
					
					
					if(schamt>prnpaid && row>2)
						prevbal=schamt-prnpaid;
					else
						prevbal=0;
					
				}
				
				
				pstmt.setString(1,rs.getString("ln.ac_type"));
				pstmt.setInt(2,rs.getInt("ln.ac_no"));
				pstmt.setString(3,rs.getString("ln.psect_cd"));
				pstmt.setDouble(4,rs.getDouble("ln.sanc_amt"));
				pstmt.setDouble(5,rs.getDouble("ln.sanc_amt")-rs.getDouble("ln.disb_left"));//advanced amt
				pstmt.setDouble(6,prnbal);//balance
				pstmt.setDouble(7,prevbal);//od amt
				
				if(rs.getString("ln.wk_sect")!=null)
				{
					if(rs.getString("ln.wk_sect").equals("Y"))
						pstmt.setString(8,rs.getString("cm.scst"));
					else
						pstmt.setString(8,null);
				}
				else
					pstmt.setString(8,null);
				
				
				pstmt.setString(9,rs.getString("ln.wk_sect"));
				pstmt.setString(10,rs.getString("ln.sex_cd"));
				pstmt.setString(11,pdate);
				
				pstmt.setString(12,utml);
				pstmt.setString(13,uname);
				pstmt.executeUpdate();
			}
			
			
			
			
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return 1;
	}	
	public PSWSObject getPSDetails() 
	{
		PSWSObject psws=new PSWSObject();
		ResultSet rs=null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			int i=0;
			String desc[]=null;
			int code[]=null;
			System.out.println("1..............");
			rs=stmt.executeQuery("select * from PriorityMaster");
			if(rs.next())
			{
				rs.last();
				i=rs.getRow();
				rs.beforeFirst();
			}
			desc=new String[i];
			code=new int[i];
			i=0;
			while(rs.next())
			{
				code[i]=rs.getInt("pr_code");
				desc[i]=rs.getString("pr_desc");
				i++;
			}
			psws.setPriorityCode(code);
			psws.setPriorityDesc(desc);
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return psws;
	}
	
	public LoanStageObject[] getLoanStageDetails(String acty,int acno,String fdate,String todate,int ty) 
	{
		LoanStageObject[] lnst=null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			
			ResultSet rs=null;
			
			stmt=conn.createStatement();
			if(ty==1)//Query on Loan Stage	
				rs=stmt.executeQuery("select lm.ac_no,lm.ac_type,lm.remd_date,close_date,descptn,moduleabbr,concat(fname,' ',mname,' ',lname) as name,action_particulars from LoanMaster lm left join LoanHistory lh on (lm.ac_type=lh.ac_type and lm.ac_no=lh.ac_no and lh.stage_no=lm.remd_no and lh.prt_date=lm.remd_date) left join StageMaster sm on (sm.ac_type=lh.ac_type and sm.stage_cd=lh.stage_no) ,CustomerMaster cm,Modules where lm.sh_type='"+acty+"' and lm.sh_no="+acno+" and cm.cid=lm.cid and Modules.modulecode=lm.ac_type");
			else if(ty==2)//Loan Stage updated report
				rs=stmt.executeQuery("select stage_cd,descptn,lm.ac_no,remd_date,close_date,lm.ac_type,concat(fname,' ',mname,' ',lname) as name,action_particulars,prt_date from LoanHistory lh,StageMaster sm,CustomerMaster cm,LoanMaster lm where lh.ac_type='"+acty+"' and lh.prt_ind='T' and  sm.loan_ty=lh.ac_type and sm.stage_cd=lh.stage_no and lm.ac_no=lh.ac_no and lm.ac_type=lh.ac_type and cm.cid=lm.cid and concat(right(remd_date,4),'-',mid(remd_date,locate('/',remd_date)+1,(locate('/',remd_date,4)-locate('/',remd_date)-1)),'-',left(remd_date,locate('/',remd_date)-1)) between '"+fdate+"' and '"+todate+"' order by stage_cd");
			
			if(rs.next())
			{
				rs.last();
				lnst=new LoanStageObject[rs.getRow()];
				rs.beforeFirst();
			}
			
			int i=0;
			while(rs.next())
			{
				lnst[i]=new LoanStageObject();
				if(ty==2)
					lnst[i].setStageCode(rs.getInt("stage_cd"));
				if(ty==1)
					lnst[i].setLoanType(rs.getString("moduleabbr"));
				else
					lnst[i].setLoanType(rs.getString("ac_type"));
				lnst[i].setAccNo(rs.getInt("ac_no"));
				lnst[i].setName(rs.getString("name"));
				lnst[i].setDescription(rs.getString("descptn"));
				lnst[i].setActionDate(rs.getString("remd_date"));
				lnst[i].setCloseDate(rs.getString("close_date"));
				lnst[i].setActionParticulars(rs.getString("action_particulars"));
				
				i++;
				
			}
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return lnst;
	}
	
	public int storeLoanRemainderSummary(String pro_date,double famt,double tamt,String loan_type,int loan_start_no,int loan_end_no,int ty,String utml,String uname) 
	{
		boolean int_cal_flag = true;
		int int_rate_type,noinstall,no_of_days=0,i=0,category=0,pri_odue_period;
		double intrate=0,prnbal=0,priamt=0,schamt=0,pri_od_amt=0,int_od_amt=0,disbamt=0;
		String intupto,trntype,ctrndt,pri_odue_date;		
		ResultSet rs=null;
		
		double othercharges=0,otheramt=0;
		double penalint=0;
		String ptrndt="";
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			System.out.println("coming here");
			rs=stmt.executeQuery("select * from LoanHistory where prt_ind='F' and ac_type='"+loan_type+"'");
			if(rs.next() && ty==0)
				return 3;
			
			if(ty==1)
				stmt.executeUpdate("delete from LoanHistory where prt_ind='F' and ac_type='"+loan_type+"'");
			rs.close();
			
			
			Vector schamt_vector = new Vector(0,1);
			Vector date_vector = new Vector(0,1);
			ResultSet rs_mst,rs_trn;
			Statement st_mst,st_trn;
			
			st_mst = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			st_trn = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			PreparedStatement pstmt =conn.prepareStatement("insert into LoanHistory values(?,?,?,?,?,?,?,?,?,?,?,date_format(sysdate(),'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'))");
			
			System.out.println ("start "+loan_start_no);
			System.out.println ("end "+loan_end_no);
			System.out.println ("ln type"+loan_type);
			if(loan_end_no==0)//for all a/cs
				rs_mst= st_mst.executeQuery("select lm.*,sub_category,sm.share_val,cm.fname,cm.mname,cm.lname,bankcode from LoanMaster lm,ShareMaster sm,CustomerMaster cm,Head where lm.ac_type='"+loan_type+"' and sm.ac_no=lm.sh_no and sm.ac_type=lm.sh_type and cm.cid=lm.cid order by ac_type ,ac_no");
			else// selected
				rs_mst= st_mst.executeQuery("select lm.*,sub_category,sm.share_val,cm.fname,cm.mname,cm.lname,bankcode from LoanMaster lm,ShareMaster sm,CustomerMaster cm,Head where lm.ac_type='"+loan_type+"' and lm.ac_no between "+loan_start_no+" and "+loan_end_no+" and sm.ac_no=lm.sh_no and sm.ac_type=lm.sh_type and cm.cid=lm.cid order by ac_no");	
			
			while(rs_mst.next())
			{
				
				othercharges=0;otheramt=0;				
				int_rate_type=0;noinstall=0;no_of_days=0;i=0;category=0;pri_odue_period=0;no_of_days=0;
				intrate=0;prnbal=0;priamt=0;schamt=0;pri_od_amt=0;int_od_amt=0;penalint=0;disbamt=0;
				intupto="";trntype="";ctrndt="";ptrndt="";pri_odue_date="";
				int_cal_flag = true;
				
				System.out.println ("rs_mst.next----------------------------");
				System.out.println ("ln ty"+rs_mst.getString(1));
				System.out.println ("ln no"+rs_mst.getString(2));
				int_rate_type=rs_mst.getInt("int_rate_type");
				category=rs_mst.getInt("sub_category");
				intrate=rs_mst.getDouble("int_rate");
				noinstall=rs_mst.getInt("no_inst");
				//sanc_amt=rs_mst.getDouble("sanc_amt");
				intupto=rs_mst.getString("int_upto_date");				
				//trnseq=rs_mst.getInt("lst_tr_seq");
				//sanc_date = rs_mst.getString("sanc_date");				
				
				rs_trn = st_trn.executeQuery("select * from LoanTransaction lt where ac_type='"+rs_mst.getString("ac_type")+"' and ac_no="+rs_mst.getInt("ac_no")+" and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))<='"+Validations.convertYMD(pro_date)+"' order by concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1)),trn_seq ");
				schamt_vector.clear();
				date_vector.clear();
				System.out.println ("rs_trn.next----------------------------");
				while(rs_trn.next())
				{					
					int row=rs_trn.getRow();
					System.out.println ("inside ln no"+rs_trn.getString(2));
					trntype=rs_trn.getString("trn_type");
					if(trntype.equals("R") || trntype.equals("D")){
						prnbal=rs_trn.getDouble("pr_bal");
						priamt += rs_trn.getDouble("pr_amt");
						otheramt+=rs_trn.getDouble("other_amt");
						if(trntype.equals("D"))
							disbamt=disbamt+rs_trn.getDouble("trn_amt");
					}						
					else if(trntype.equals("S")){
						schamt_vector.add(rs_trn.getString("pr_amt"));
						date_vector.add(rs_trn.getString("trn_date"));
						schamt += rs_trn.getDouble("pr_amt");
					}						
					if(!trntype.equals("O"))
						ctrndt=rs_trn.getString("trn_date");					
					
					if(trntype.equals("O"))
						othercharges=othercharges+rs_trn.getDouble("trn_amt");		
					
					
					if( pri_od_amt>0 )
						penalint=penalint+calculatePenalInt(rs_mst.getString("ac_type"),ptrndt,ctrndt,pri_od_amt,category);			
					
					if(schamt>priamt && row>2)
						pri_od_amt = schamt - priamt;
					else
						pri_od_amt=0;
					
					if(row>1 || !trntype.equals("O"))
						ptrndt=ctrndt;	
					
					
				}
				System.out.println ("after while--------------");
				if(prnbal==0)
					int_cal_flag = false;
				// Principle OD
				if(priamt < schamt){
					pri_od_amt = schamt - priamt;
					double temp_sch_amt=0;
					i=0;
					for(;i<schamt_vector.size();i++){
						temp_sch_amt += Double.parseDouble(schamt_vector.get(i).toString());
						if(temp_sch_amt > priamt)
							break;				
					}
					System.out.println ("after for");
					if(i==schamt_vector.size())
						i--;
					pri_odue_period = noinstall;
					pri_odue_date = date_vector.get(i).toString();	
					if(i>0)	{
						pri_odue_period = schamt_vector.size() - i +1;
						no_of_days = (int) ( ( (pri_od_amt - temp_sch_amt) * (Validations.dayCompare(date_vector.get(i-1).toString(),date_vector.get(i).toString())) ) / (Double.parseDouble(schamt_vector.get(i).toString())) ) ;					
						pri_odue_date = Validations.addDays(date_vector.get(i-1).toString(),no_of_days);
						if(Validations.dayCompare(date_vector.get(i-1).toString(),date_vector.get(i).toString())/2 >= no_of_days)
							pri_odue_period--;
					}
					
					pri_odue_date = Validations.addDays(pri_odue_date,1);
					no_of_days = Validations.dayCompare(pri_odue_date,Validations.addDays(pro_date,-1));
				}
				System.out.println ("after if");
				//Interest ODue
				System.out.println("Interest type"+int_rate_type);
				if(int_cal_flag){
					
					if(int_rate_type==1)
					{	
						int_od_amt=calculateInterest(rs_mst.getString("ac_type"),intupto,Validations.addDays(pro_date,-1),prnbal,category,noinstall,disbamt,rs_mst.getInt("ac_no"));
						intrate=getIntRate(rs_mst.getString("ac_type"),pro_date,category,noinstall,disbamt,rs_mst.getInt("ac_no"));
					}
					else
						int_od_amt=Math.round((prnbal*Validations.dayCompare(intupto,Validations.addDays(pro_date,-1))*intrate)/(365*100));
				}
				
				//ctrndt=getSysDate();
				
				if(schamt>priamt && pri_od_amt>0)
					penalint=penalint+calculatePenalInt(rs_mst.getString("ac_type"),ptrndt,getSysDate(),pri_od_amt,category);			
				
				
				System.out.println ("after int if"+pri_od_amt+" "+no_of_days+" "+pri_odue_date);
				
				
				if(pri_od_amt>=famt && pri_od_amt<=tamt)
				{
					
					
					Statement stat1=conn.createStatement();
					ResultSet rs1=stat1.executeQuery("select stage_cd from StageMaster where ac_type='"+rs_mst.getString("ac_type")+"' and "+no_of_days+" between prd_from and prd_to");
					System.out.println("insert 2........."+rs_mst.getString("ac_type"));
					if(rs1.next())
					{
						System.out.println("insert 2.........");
						
						
						pstmt.setString(1,rs_mst.getString("ac_type"));
						pstmt.setInt(2,rs_mst.getInt("ac_no"));
						pstmt.setString(3,rs_mst.getString("sh_type"));
						pstmt.setInt(4,rs_mst.getInt("sh_no"));
						pstmt.setString(5,"");
						pstmt.setDouble(6,rs_mst.getDouble("sm.share_val"));
						pstmt.setString(7,rs_mst.getString("fname")+" "+rs_mst.getString("mname")+" "+rs_mst.getString("lname"));
						pstmt.setInt(8,0);
						
						//def_mmths,days calc, 
						
						if(rs1.getInt(1)>rs_mst.getInt("remd_no"))
						{
							rs1=stat1.executeQuery("select stage_cd from StageMaster where loan_ty='"+rs_mst.getString("ac_type")+"' and stage_cd > "+rs_mst.getInt("remd_no")+" order by stage_cd limit 1");
							rs1.next();
							pstmt.setInt(9,rs1.getInt(1));
						}
						else if(rs1.getInt(1)<=rs_mst.getInt("remd_no"))
							pstmt.setInt(9,rs_mst.getInt("remd_no"));
						
						
						pstmt.setString(10,String.valueOf(rs_mst.getInt("bankcode")));
						pstmt.setInt(11,0);
						
						
						
						pstmt.setString(12,rs_mst.getString("sanc_date"));
						pstmt.setDouble(13,rs_mst.getDouble("sanc_amt"));
						pstmt.setDouble(14,rs_mst.getDouble("inst_amt"));
						pstmt.setDouble(15,rs_mst.getDouble("no_inst"));
						pstmt.setDouble(16,0);//cur inst
						
						
						pstmt.setDouble(17,int_od_amt);
						pstmt.setString(18,rs_mst.getString("int_upto_date"));
						pstmt.setDouble(19,penalint);//penal int
						pstmt.setDouble(20,(othercharges-otheramt));//other
						pstmt.setDouble(21,prnbal);//loan bal
						pstmt.setDouble(22,pri_od_amt);//amt overdue
						pstmt.setString(23,"F");
						pstmt.setInt(24,pri_odue_period);//def_mnths
						pstmt.setString(25,rs_mst.getString("lst_trn_dt"));
						pstmt.setString(26,uname);
						pstmt.setString(27,utml);
						
						System.out.println("insert 4.........");
						pstmt.executeUpdate();
					}
				}
				
			}//while
			
			System.out.println("int="+int_od_amt+" Penal int="+penalint+" other chrgs="+(othercharges-otheramt)+" "+no_of_days);
			
			return 2;
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}		
		
		return 1;
		
	}
	
	public LoanPurposeObject[] getLoanPurposes() 
	{
		LoanPurposeObject lnpurp[]=null;
		Connection con=null;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from LoanPurposes");
			rs.last();
			lnpurp=new LoanPurposeObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			System.out.println("Loan purposes");
			while(rs.next())
			{
				lnpurp[i]=new LoanPurposeObject();
				lnpurp[i].setPurposeCode(rs.getInt("pps_no"));	
				lnpurp[i].setPurposeDesc(rs.getString("pps_desc"));	
				i++;
				
			}
			System.out.println("Loan purposes ==== 1");
			
		}catch(Exception ex){JOptionPane.showMessageDialog(null,"LoansOnDepositImpl.java(getLoanPurposes) : "+ex);}
		
		finally
		{
			try{
				con.close();
			}catch(Exception ex){ex.printStackTrace();}
		}
		return lnpurp;
	}
	
	
	
	public String getSysDateTime() 
	{
		Calendar c=Calendar.getInstance();
		
		String str=String.valueOf(c.get(Calendar.SECOND));
		if(str.length()==1)
			str="0"+str;
		String str1=String.valueOf(c.get(Calendar.MINUTE));
		if(str1.length()==1)
			str1="0"+str1;
		String str2=String.valueOf(c.get(Calendar.HOUR));
		if(str2.length()==1)
			str2="0"+str2;
		
		try {
			return(Validations.checkDate(c.get(Calendar.DATE)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR))+" "+str2+":"+str1+":"+str+"  ");
		} catch (DateFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList getLastLoanTransactionDate(String ac_type,int ac_no) {
		ArrayList arrayList = new ArrayList(8);
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			rs = stmt.executeQuery("select sanc_date from LoanMaster where ac_type='"+ac_type+"' and ac_no="+ac_no+" ");
			rs.next();
			String sanc_date = rs.getString("sanc_date");
			rs = stmt.executeQuery("select trn_date,other_amt from LoanTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and trn_type='R' and other_amt > 0 order by trn_seq desc ");
			if(rs.next()) {
				
				arrayList.add(0,rs.getString("trn_date"));
				arrayList.add(1,rs.getString("other_amt"));
			}
			else{
				arrayList.add(0,sanc_date);
				arrayList.add(1,"0");
			}
			rs = stmt.executeQuery("select trn_date,penal_amt from LoanTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and trn_type='R' and penal_amt > 0 order by trn_seq desc ");
			if(rs.next()) {
				arrayList.add(2,rs.getString("trn_date"));
				arrayList.add(3,rs.getString("penal_amt"));
			}
			else{
				arrayList.add(2,sanc_date);
				arrayList.add(3,"0");
			}
			rs = stmt.executeQuery("select int_date,int_amt from LoanTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and trn_type='R' and int_amt > 0 order by trn_seq desc ");
			if(rs.next()) {
				arrayList.add(4,rs.getString("int_date"));
				arrayList.add(5,rs.getString("int_amt"));
			}
			else{
				arrayList.add(4,sanc_date);
				arrayList.add(5,"0");
			}
			rs = stmt.executeQuery("select trn_date,pr_amt from LoanTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and trn_type='R' and pr_amt > 0 order by trn_seq desc ");
			if(rs.next()) {
				arrayList.add(6,rs.getString("trn_date"));
				arrayList.add(7,rs.getString("pr_amt"));
			}
			else{
				arrayList.add(6,sanc_date);
				arrayList.add(7,"0");
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return arrayList;
	}
	
	
	public ArrayList getSurityCoBorrowerDetails(String ac_type,int ac_no) {
		ArrayList arrayList = new ArrayList(8);
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			rs = stmt.executeQuery("select bm.ac_type,moduleabbr,bm.ac_no,sm.cid,bm.type from BorrowerMaster bm,Modules m,ShareMaster sm where bm.ac_type=modulecode and sm.ac_type=bm.ac_type and sm.ac_no=bm.ac_no and ln_ac_type='"+ac_type+"' and ln_ac_no="+ac_no+" ");
			while(rs.next()) {
				arrayList.add(rs.getString("bm.ac_type"));
				arrayList.add(rs.getString("bm.type"));
				arrayList.add(rs.getString("moduleabbr"));
				arrayList.add(rs.getString("bm.ac_no"));
				arrayList.add(rs.getString("sm.cid")); 
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return arrayList;
	}
	
	public ArrayList getLoanNPAStatus(String actype,int acno) {
		ArrayList arrayList = new ArrayList(8);
		Connection conn=null;
		try{
			System.out.println("Accno in bean"+acno+"Acctype"+actype);
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			rs = stmt.executeQuery("select distinct nt.*,nc.asset_desc from NPATable nt,NPAClassification nc,LoanMaster lm where nt.ac_type='"+actype+"' and nt.ac_no="+acno+" and nt.npa_pro_date=lm.npa_date and nt.ac_type=nc.loan_type and nt.asset_code=nc.asset_code");
			rs.last();
			System.out.println("GetRow"+rs.getRow());
			rs.beforeFirst();
			if(rs.next()) {
				arrayList.add(rs.getString("nt.npa_pro_date"));
				arrayList.add(rs.getString("nc.asset_desc"));
				arrayList.add(rs.getString("nt.pr_odue_from"));
				arrayList.add(rs.getString("nt.pri_odue_amt"));
				arrayList.add(rs.getString("nt.pri_odue_prd"));
				arrayList.add(rs.getString("nt.int_odue_amt"));			    
			} 
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		System.out.println("Before returning arraylist"+arrayList.size());
		return arrayList;
	}
	
	public ArrayList getLoanHistory(String actype,int acno) {
		ArrayList arrayList = new ArrayList(8);
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			rs = stmt.executeQuery("select * from LoanHistory where ac_type='"+actype+"' and ac_no="+acno+" order by concat(right(lsttrn_date,4),'-',mid(lsttrn_date,locate('/',lsttrn_date)+1, (locate('/',lsttrn_date,4)-locate('/',lsttrn_date)-1)),'-',left(lsttrn_date,locate('/',lsttrn_date)-1))");
			while(rs.next()) {
				arrayList.add(rs.getString("action_particulars"));			    
			}
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return arrayList;
	}
	
	public String getAppraiserName(int code)
	{
		Connection conn=null;
		String name=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			rs = stmt.executeQuery("select name from ApprisersMaster where code="+code+" ");                    
			
			while(rs.next())
				name=rs.getString(1);
			
			System.out.println("***************inside getAppraiserNamwe**************");
			System.out.println("Name :"+name);
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		
		return name;
	}
	
	// Function added by Murugesh on 13/12/2005
	public String[] getReleventDetails(String modulecode)
	{
		String[] releventdetails=null;
		ResultSetMetaData  rs_meta_data;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			rs = stmt.executeQuery("select * from LoanEntryIns where modulecode='"+modulecode+"' ");   
			rs_meta_data = rs.getMetaData();
			
			// releventdetails = new String[rs_meta_data.getColumnCount()-3]; Code changed by Murugesh on 18/1/2006
			releventdetails = new String[11]; //Code added by Murugesh on 18/1/2006
			if(rs.next())
			{
				//for(int i=0;i<rs_meta_data.getColumnCount()-3;i++) Code changed by Murugesh on 18/1/2006
				/*for(int i=0;i<11;i++) //Code added by Murugesh on 18/1/2006
				{
					releventdetails[i]=rs.getString(i+3);
					System.out.println("releventdetails[i]===="+releventdetails[i]);
				}*/
				 releventdetails[0]=rs.getString("Personal");
				 releventdetails[1]=rs.getString("Relative");
				 releventdetails[2]=rs.getString("Employment");
				 releventdetails[3]=rs.getString("Application");
				 releventdetails[4]=rs.getString("LoanShareDet");
				 releventdetails[5]=rs.getString("SignIns");
				 releventdetails[6]=rs.getString("Property");
				 releventdetails[7]=rs.getString("Coborrowers");
				 releventdetails[8]=rs.getString("Surities");
				 releventdetails[9]=rs.getString("Vehicle");
				 releventdetails[10]=rs.getString("Gold");
				 //releventdetails[11]=rs.getString("Od");
			}
			else
				return(null);
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
		return releventdetails;
	}
	
	// Method added by Murugesh on 30/12/2005
	public double getDisbursementLeft(String ac_type,int ac_no)
	{
		double disbursement=-1;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			rs = stmt.executeQuery("select disb_left,sanc_amt from LoanMaster where  ac_type='"+ac_type+"' and ac_no="+ac_no+"");
			if(rs.next())
			{
				if(rs.getDouble("disb_left")!=0.0)
					disbursement = rs.getDouble("disb_left");
				else
					return rs.getDouble("sanc_amt");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return disbursement;
	}
	
	// Method added by Murugesh on 3/1/2006
	public int getPreviousDisbDet(String ac_type,int ac_no,String date)
	{
		int count=0;
		
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null,rs1=null;
			rs = stmt.executeQuery("select count(*) as no_rows from LoanTransaction where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) < '"+Validations.convertYMD(date)+"' and trn_type='S' and ac_type='"+ac_type+"' and ac_no="+ac_no+" order by trn_date");																																																																																																																																																	
			if(rs.next())
			{
				count = rs.getInt("no_rows");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return count;
	}
	
	// Method added by Murugesh on 3/1/2006
	public int getNumPreviousDisb(String ac_type,int ac_no,int flag) 
	{
		int  count=0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			if(flag==1)
			{
				rs = stmt.executeQuery("select count(*) as no_rows from LoanTransaction where  trn_type='D' and  ac_type='"+ac_type+"' and ac_no="+ac_no+"");
			}
			if(flag==2)
			{
				rs = stmt.executeQuery("select count(*) as no_rows from LoanTransaction where  trn_type='D' and  ac_type='"+ac_type+"' and ac_no="+ac_no+" and ve_tml is not null and ve_user is not null and ve_date is not null");
			}
			if(rs.next())
				count = rs.getInt("no_rows");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return count;
	}
	
	public String getLastDisbDate(String ac_type,int ac_no) 
	{
		return "abcd";
	}
	
	// Method added by Murugesh on 3/1/2006 
	/**
	 * @author Murugesh:
	 * This method is used to find the last transaction sequence.
	 * @since The Theme: Its used to get the last transaction sequence from LoanMaster or from the LoanTransaction
	 *	by passing the account type and acc no.If u pass the boolean value as true it will take the last transaction sequence from 
	 *	Loan Master and if u pass false it will take it from the Loan Transaction table
	 *
	 * @param String ac_type
	 * @param int ac_no
	 * @param boolean flag
	 * 
	 * @return int
	 */
	public int getLastTransactionSeq(String ac_type,int ac_no,boolean flag)
	{
		int  trn_seq=0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			if(flag)
				rs = stmt.executeQuery("select lst_tr_seq from LoanMaster where  ac_type='"+ac_type+"' and ac_no="+ac_no+"");
			else
				rs= stmt.executeQuery("select max(trn_seq) as count from LoanTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+"");
			if(rs.next())
			{
				if(flag)
				{
					if(String.valueOf(rs.getInt("lst_tr_seq"))!=null)
						trn_seq = rs.getInt("lst_tr_seq");
					else
						trn_seq = 0;
				}
				else
				{
					if(String.valueOf(rs.getInt("count"))!=null)
						trn_seq = rs.getInt("count");
					else
						trn_seq = 0;
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return trn_seq;
	}
	
	// Method added by Murugesh on 6/1/2006
	/**
	 * @author Murugesh
	 * This method is used to get the previous disburement's schedule records
	 * @since The Theme: Here we have two options. If we want the 'S' records that is the verified
	 * schedule records of the previous disburement we have to pass the boolean flag as false or if 
	 * u want the 'S+' records (+ denotes the number of last disbursement which is not verified) we 
	 * to pass the boolean flag as true, so that it will find what is the number of the previous 
	 * disbursement,append it with 'S+' and it will retrive the last un verified disbursement's 
	 * schedule records.
	 * 
	 * @param String ac_type
	 * @param int ac_no
	 * @param boolean flag
	 * 
	 * @return Object[][]
	 */
	public Object[][] getPreviousDisbursement(String ac_type,int ac_no,boolean flag)
	{
		//int  count=0;
		Connection conn=null;
		int period=0;
		Object data1[][]= null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null,rs1=null;
			if(flag)
			{
				//count = getNumPreviousDisb(ac_type,ac_no,1);
				rs = stmt.executeQuery("select count(*) as rows from LoanTransaction where  trn_type like 'S%' and  ac_type='"+ac_type+"' and ac_no="+ac_no+" and ve_tml is null");
			}
			else
			{
				rs = stmt.executeQuery("select count(*) as rows from LoanTransaction where  trn_type='S' and  ac_type='"+ac_type+"' and ac_no="+ac_no+"");
			}
			if(rs.next())
			{
				period = rs.getInt("rows");
				// Code added by Murugesh on 9/2/2006
				if(period==0)
					return null;
				//
				
				Object data[][]=new Object[period+1][6];
				if(flag)
				{
					rs1 = stmt.executeQuery("select * from LoanTransaction where  trn_type like 'S%' and  ac_type='"+ac_type+"' and ac_no="+ac_no+" and ve_tml is null order by concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))");
				}
				else
				{	
					rs1 = stmt.executeQuery("select * from LoanTransaction where  trn_type='S' and  ac_type='"+ac_type+"' and ac_no="+ac_no+" order by  concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))");
				}
				if(rs1.next())
				{
					for(int i=0;i<period;i++)
					{
						data[i][0]=String.valueOf(i+1);
						data[i][1]= rs1.getString("trn_date");
						data[i][2]=String.valueOf(rs1.getDouble("pr_amt"));
						data[i][3]=String.valueOf(rs1.getDouble("int_amt"));
						data[i][4]=String.valueOf(rs1.getDouble("trn_amt"));
						data[i][5]=String.valueOf(rs1.getDouble("pr_bal"));
						rs1.next();
					}
				}
				return data;	
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return data1;
	}
	
	// Method added by Murugesh on 27/1/2006
	public LoanTransactionObject[][] getOtherChargesDue(String ac_type,int ac_no_from,int ac_no_to,int flag, String date)
	{
		Connection conn=null;
		int count=ac_no_from;
		double sum=0;
		int rows=0;
		int total_count=0;
		LoanTransactionObject[][] loan_trn = null;
		//LoanTransactionObject[][] return_loan_trn=null;
		int[] ac_no=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement stmt1=conn.createStatement();
			ResultSet rs=null,rs1=null,rs2=null;
			
			if(date==null){
				date=getSysDate();
			}
			
			if(flag==2)
			{
				rs1=stmt.executeQuery("select min(ac_no) as min ,max(ac_no) as max from LoanTransaction  where ac_type='"+ac_type+"' and trn_type='O' order by ac_no");
				if(rs1.next())
				{
					if(rs1.getString("min")!=null && rs1.getString("max")!=null)
					{
						ac_no_from=rs1.getInt("min");
						count=ac_no_from;
						ac_no_to=rs1.getInt("max");
						
						System.out.println("the first ac_no"+ac_no_from);
						System.out.println("the last ac_no"+ac_no_to);
						
					}
					else
						return null;
				}
			}
			
			rs=stmt.executeQuery("select  distinct ac_no from LoanTransaction where ac_type='"+ac_type+"' and ac_no between "+ac_no_from+" and  "+ac_no_to+"  and trn_type='O'");
			rs.last();
			if(rs.getRow()==0)
				return null;
			loan_trn = new LoanTransactionObject[rs.getRow()][];
			ac_no = new int[rs.getRow()];
			rs.beforeFirst();
			
			int k=0;
			while(rs.next())
			{
				ac_no[k]=rs.getInt("ac_no");
				k++;
			}
			//System.out.println("the length of the count is &&&&&&&&&&&&& "+rs.getInt("count"));
			
			for(int j=0;j<ac_no.length;j++)
			{
				sum=0;
				rows=0;
				rs=stmt.executeQuery("select distinct sum(other_amt) as sum from LoanTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no[j]+" and trn_type='R' and other_amt>0 and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <'"+Validations.convertYMD(date)+"'");
				System.out.println("the ac_no[j] value is"+ac_no[j]);
				rs.next();
				if(rs.getString("sum")!=null)
				{
					sum=rs.getDouble("sum");
				}
				System.out.println("the sum is"+sum);
				rs=stmt.executeQuery("select * from LoanTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no[j]+" and trn_type='O' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <'"+Validations.convertYMD(date)+"' order by concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))");
				
				rs.beforeFirst();
				System.out.println("11111111111111");
				rs.last();
				rows = rs.getRow();
				System.out.println("row"+rows);
				rs.beforeFirst();
				while(rs.next())
				{
					if( sum<rs.getDouble("trn_amt"))
					{
						System.out.println("22222222222");
						break;
					}
					if(sum>rs.getDouble("trn_amt"));
					{
						sum -= rs.getDouble("trn_amt");
						rows--;
						System.out.println("333333333333333");
					}
				}
				
				if(rows!=0)
				{
					loan_trn[total_count]=new LoanTransactionObject[rows];
					System.out.println("44444444444444444444 ********* rows"+rows);
					
					rs2=stmt1.executeQuery("select fname from CustomerMaster where cid=(select cid from LoanMaster where ac_type='"+ac_type+"' and ac_no="+ac_no[j]+")");
					
					int i=0;
					System.out.println(" the total ac_no[j] is "+total_count);
					loan_trn[total_count][i]=new LoanTransactionObject();
					loan_trn[total_count][i].setAccType(ac_type);
					loan_trn[total_count][i].setAccNo(ac_no[j]);
					if(rs2.next())
						loan_trn[total_count][i].setName(rs2.getString("fname"));
					loan_trn[total_count][i].setTranNarration(rs.getString("trn_narr"));
					loan_trn[total_count][i].setOtherAmount(rs.getDouble("trn_amt")-sum);
					System.out.println("5555555555555555");
					
					while(rs.next())
					{
						i++;
						loan_trn[total_count][i]=new LoanTransactionObject();
						/*loan_trn[total_count][i].setAccType(ac_type);
						loan_trn[total_count][i].setAccNo(ac_no[j]);*/
						loan_trn[total_count][i].setTranNarration(rs.getString("trn_narr"));
						loan_trn[total_count][i].setOtherAmount(rs.getDouble("trn_amt"));
						System.out.println("66666666666");
					}

				}
				total_count++;
				System.out.println("77777777777");
			}
			
			/*{
				return_loan_trn = new LoanTransactionObject[total_count][];
				int row_values=0;
				for(int i=0;i<loan_trn.length;i++)
				{
					if(loan_trn[i]!=null && loan_trn[i][0]!=null)
					{
							return_loan_trn[row_values] = loan_trn[i];
							row_values++;
							System.out.println("the number of values"+row_values);
					}
				}
				loan_trn=null;
			}*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return loan_trn;
	}
	
	// Method added by Murugesh on 30/1/2006
	public LoanTransactionObject[][] getOtherChargesCollected(String ac_type,String from_date,String to_date,int ac_no_from,int ac_no_to,int flag)
	{
		Connection conn=null;
		LoanTransactionObject[][] loan_trn = null;
		int rows=0;
		double other_charges=0;
		double paid=0;
		int total_rows=0;
		try
		{
			conn=getConnection();
			Statement stmt1=conn.createStatement();
			Statement stmt=conn.createStatement();
			ResultSet rs=null,rs1=null,rs2=null,rs3=null;
			if(flag==1)
			{
				rs = stmt1.executeQuery("select distinct(ac_no) , ac_type from LoanTransaction where  concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(from_date)+"' and '"+Validations.convertYMD(to_date)+"' and ac_type like '1010%' and (trn_type='O' or(trn_type='R' and other_amt>0))");
			}
			
			if(flag==2 && ac_type!=null)
			{
				rs = stmt1.executeQuery("select distinct(ac_no) , ac_type from LoanTransaction where  concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(from_date)+"' and '"+Validations.convertYMD(to_date)+"' and ac_type = '"+ac_type+"' and (trn_type='O' or(trn_type='R' and other_amt>0)) and ac_no between "+ac_no_from+" and "+ac_no_to+"");
			}
			
			if(flag==3 && ac_type!=null)
			{
				rs = stmt1.executeQuery(" select distinct(ac_no) , ac_type from LoanTransaction where  concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(from_date)+"' and '"+Validations.convertYMD(to_date)+"' and ac_type = '"+ac_type+"' and (trn_type='O' or(trn_type='R' and other_amt>0))");
			}
			
			if(!rs.next())
			{
				return null;
			}
			rs.beforeFirst();
			System.out.println("11111111111111");
			rs.last();
			rows = rs.getRow();
			System.out.println("row"+rows);
			rs.beforeFirst();
			
			loan_trn = new LoanTransactionObject[rows][];
			
			while(rs.next())
			{
				other_charges=0;
				paid=0;
				
				rs2=stmt.executeQuery("select sum(trn_amt) as other_sum from LoanTransaction where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type='O' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <'"+Validations.convertYMD(from_date)+"'");
				rs2.next();
				if(rs2.getString("other_sum")!=null)
				{
					other_charges=rs2.getDouble("other_sum");
				}
				
				rs3=stmt.executeQuery("select sum(other_amt) as paid from LoanTransaction where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type='R' and other_amt>0 and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <'"+Validations.convertYMD(from_date)+"'");
				rs3.next();
				if(rs3.getString("paid")!=null)
				{
					paid=rs3.getDouble("paid");
				}
				
				rs1=stmt.executeQuery("select * from LoanTransaction where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and (trn_type='O' or(trn_type='R' and other_amt>0)) and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(from_date)+"' and '"+Validations.convertYMD(to_date)+"'");
				
				if(!rs1.next())
					continue;
				rs1.beforeFirst();
				rs1.last();
				rows = rs1.getRow();
				System.out.println("row"+rows);
				rs1.beforeFirst();
				
				loan_trn[total_rows]= new LoanTransactionObject[rows];
				int i=0;
				
				while(rs1.next())
				{
					loan_trn[total_rows][i] = new LoanTransactionObject();
					if(i==0)
					{
						if(other_charges>paid)
						{
							loan_trn[total_rows][0].setCdind("Debit");
							loan_trn[total_rows][0].setLoanBalance(other_charges-paid);
						}
						else
						{
							loan_trn[total_rows][0].setCdind("Credit");
							loan_trn[total_rows][0].setLoanBalance(paid-other_charges);
						}
					}
					
					loan_trn[total_rows][i].setAccType(rs1.getString("ac_type"));
					loan_trn[total_rows][i].setAccNo(rs1.getInt("ac_no"));
					loan_trn[total_rows][i].setTransactionDate(rs1.getString("trn_date"));
					loan_trn[total_rows][i].setTranType(rs1.getString("trn_type"));
					if(rs1.getString("trn_type").equals("O"))
					{
						loan_trn[total_rows][i].setOtherAmount(rs1.getDouble("trn_amt"));
						loan_trn[total_rows][i].setTranNarration(rs1.getString("trn_narr"));
					}
					if(rs1.getString("trn_type").equals("R"))
					{
						loan_trn[total_rows][i].setOtherAmount(rs1.getDouble("other_amt"));
						loan_trn[total_rows][i].setTranNarration(rs1.getString("trn_narr")+"  "+rs1.getString("ref_no"));
					}
					i++;
				}
				total_rows++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		
		return loan_trn;
	}
	/** 
	 * @author Murugesh:
	 * This method is used to find the penal interest for the given account number on any given date.
	 * 
	 * @since The Theme:
	 * Here we are passing the date and ac_no for which the penal interest to be calculated.
	 * 
	 * @param String ac_type
	 * @param int ac_no
	 * @param date for which penal interest to be calculated
	 * @param int sub_category which can be reterived from the CustomerMaster for the ac_no's cid
	 * 
	 * @return double ie,the penal interest 
	 */
	public double calculatePenalInterestForRecovery(String ac_type,int ac_no,String date,int category)
	{
		Connection conn=null;
		Object recovered[][]=null;
		Object scheduled[][]=null;
		double penal_interest=0.0;
		double penal_interest_collected=0.0;
		String last_int_upto_date=null;
		try
		{
			conn=getConnection();
			Statement stmt1=conn.createStatement();
			Statement stmt=conn.createStatement();
			ResultSet rs=null,rs1=null,rs2=null,rs3=null;
			
			rs=stmt.executeQuery("select trn_date,pr_amt from LoanTransaction as lt where ac_type='"+ac_type+"' and ac_no="+ac_no+" and(trn_type='S' ) and   concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))<'"+Validations.convertYMD(date)+"' order by concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1)),trn_seq");
			if(rs.last())
			{
				if(rs.getRow()==0)
					return 0.0;
				
				scheduled= new Object[rs.getRow()][2];
				rs.beforeFirst();
				int j=0;
				while(rs.next())
				{
					scheduled[j][0]=rs.getString("trn_date");
					scheduled[j][1]=rs.getString("pr_amt");
					j++;
				}
			}
			else
				return 0.0;
			
			last_int_upto_date = getIntUpToDateOnCurrentDate(ac_type,ac_no,date);
			rs1=stmt.executeQuery("select trn_date,pr_amt from LoanTransaction as lt where ac_type='"+ac_type+"' and ac_no="+ac_no+" and((trn_type='R' and pr_amt > 0)) and   concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))<'"+Validations.convertYMD(date)+"' and ve_tml is not null and concat(right(lt.int_date,4),'-',mid(lt.int_date,locate('/',lt.int_date)+1, (locate('/',lt.int_date,4)-locate('/',lt.int_date)-1)),'-',left(lt.int_date,locate('/',lt.int_date)-1)) <= '"+Validations.convertYMD(last_int_upto_date)+"'  order by concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1)),trn_seq");
			if(rs1.last())
			{
				if(rs1.getRow()==0)
				{
					recovered=null;
				}
				else
				{
					recovered = new Object[rs1.getRow()][2];
					rs1.beforeFirst();
					int j=0;
					while(rs1.next())
					{
						recovered[j][0]=rs1.getString("trn_date");
						recovered[j][1]=rs1.getString("pr_amt");
						j++;
					}
				}
			}
			else
				recovered=null;
			
			rs1=stmt.executeQuery("select sum(penal_amt) as sum from LoanTransaction as lt where ac_type='"+ac_type+"' and ac_no="+ac_no+" and trn_type='R' and ve_tml is not null and  concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))<='"+Validations.convertYMD(date)+"'");
			if(rs1.next())
			{
				if(rs1.getString("sum")!=null)
				{
					penal_interest_collected = rs1.getDouble("sum");
				}
			}
			int increment_scheduled=0,increment_recovered=0;
			String grace_date_added=null;
			penal_interest=0.0;
			
			if(commonlocal==null)
				commonlocal=commonlocalhome.create();
			
			if(recovered==null)
			{
				for(int i=0;i<scheduled.length;i++)
				{
					//penal_interest+=calculatePenalInt(ac_type,scheduled[i][0].toString(),date,Double.parseDouble(scheduled[i][1].toString()),category);
					grace_date_added=commonlocal.getFutureDayDate(scheduled[i][0].toString(),15);
					if(commonlocal.getDaysFromTwoDate(date,grace_date_added)<0)
					{
						penal_interest+=calculatePenalInt(ac_type,scheduled[i][0].toString(),date,Double.parseDouble(scheduled[i][1].toString()),category);
					}
					System.out.println("the penal interest is:"+penal_interest);
				}
				System.out.println(" %%%%%%%%penal interest"+penal_interest);
				System.out.println(" %%%%%%%% penal_interest collected"+penal_interest_collected);
				
				if(Math.round(penal_interest-penal_interest_collected)>0)   // This condition is required since at any given point of time the interest value should not be negative
					return  Math.round(penal_interest-penal_interest_collected);
				else
					return 0.0;
				//return Math.round(penal_interest-penal_interest_collected);
			}
			
			if(recovered!=null && scheduled!=null)
			{
				while(increment_recovered<recovered.length && increment_scheduled<scheduled.length)
				{
					grace_date_added=commonlocal.getFutureDayDate(scheduled[increment_scheduled][0].toString(),15);
					System.out.println(" %%%%%%%%"+penal_interest);
					if(commonlocal.getDaysFromTwoDate(recovered[increment_recovered][0].toString(),scheduled[increment_scheduled][0].toString())<0)
					{
						if(commonlocal.getDaysFromTwoDate(recovered[increment_recovered][0].toString(),grace_date_added)<0)
						{
							if(Double.parseDouble(recovered[increment_recovered][1].toString())>Double.parseDouble(scheduled[increment_scheduled][1].toString()))
							{
								System.out.println("*** 111111111 *****");
								penal_interest+=calculatePenalInt(ac_type,scheduled[increment_scheduled][0].toString(),recovered[increment_recovered][0].toString(),Double.parseDouble(scheduled[increment_scheduled][1].toString()),category);
								recovered[increment_recovered][1]=String.valueOf(Double.parseDouble(recovered[increment_recovered][1].toString())-Double.parseDouble(scheduled[increment_scheduled][1].toString()));
								increment_scheduled++;
							}
							else
								if(Double.parseDouble(recovered[increment_recovered][1].toString())<Double.parseDouble(scheduled[increment_scheduled][1].toString()))
								{
									System.out.println("*** 22222222222 *******");
									penal_interest+=calculatePenalInt(ac_type,scheduled[increment_scheduled][0].toString(),recovered[increment_recovered][0].toString(),Double.parseDouble(scheduled[increment_scheduled][1].toString()),category);
									scheduled[increment_scheduled][1]=String.valueOf(Double.parseDouble(scheduled[increment_scheduled][1].toString())-Double.parseDouble(recovered[increment_recovered][1].toString()));
									increment_recovered++;
								}
								else
								{
									
									System.out.println("*** 333333333333 ****");
									penal_interest+=calculatePenalInt(ac_type,scheduled[increment_scheduled][0].toString(),recovered[increment_recovered][0].toString(),Double.parseDouble(scheduled[increment_scheduled][1].toString()),category);
									increment_recovered++;
									increment_scheduled++;
								}
						}
						else
						{
							if(Double.parseDouble(recovered[increment_recovered][1].toString())<Double.parseDouble(scheduled[increment_scheduled][1].toString()))
							{
								System.out.println("*** 44444444444 *******");
								scheduled[increment_scheduled][1]=String.valueOf(Double.parseDouble(scheduled[increment_scheduled][1].toString())-Double.parseDouble(recovered[increment_recovered][1].toString()));
								increment_recovered++;
							}
							else if(Double.parseDouble(recovered[increment_recovered][1].toString())>Double.parseDouble(scheduled[increment_scheduled][1].toString()))
							{
								System.out.println("*** 555555555 ******");
								recovered[increment_recovered][1]=String.valueOf(Double.parseDouble(recovered[increment_recovered][1].toString())-Double.parseDouble(scheduled[increment_scheduled][1].toString()));
								increment_scheduled++;
							}
							else
							{
								System.out.println("*** 6666666 **** ");
								increment_recovered++;
								increment_scheduled++;
							}
						}
					}
					else
					{
						if(Double.parseDouble(recovered[increment_recovered][1].toString())<Double.parseDouble(scheduled[increment_scheduled][1].toString()))
						{
							System.out.println("*** 77777777777 *******");
							scheduled[increment_scheduled][1]=String.valueOf(Double.parseDouble(scheduled[increment_scheduled][1].toString())-Double.parseDouble(recovered[increment_recovered][1].toString()));
							increment_recovered++;
							System.out.println(" %%%%%%%%"+penal_interest);
						}
						else if(Double.parseDouble(recovered[increment_recovered][1].toString())>Double.parseDouble(scheduled[increment_scheduled][1].toString()))
						{
							System.out.println("*** 8888888888 ******");
							recovered[increment_recovered][1]=String.valueOf(Double.parseDouble(recovered[increment_recovered][1].toString())-Double.parseDouble(scheduled[increment_scheduled][1].toString()));
							increment_scheduled++;
							System.out.println(" %%%%%%%%"+penal_interest);
						}
						else
						{
							System.out.println("*** 99999999999 ******");
							increment_recovered++;
							increment_scheduled++;
							System.out.println(" %%%%%%%%"+penal_interest);
						}
					}
				} // End of while loop
				
				System.out.println(" the value of recovered length &&&&&&&&&&"+recovered.length);
				System.out.println("the value of increment recovered is "+increment_recovered);
				
				if(increment_scheduled<scheduled.length)
				{
					System.out.println(" RRRRRRRRRRRRRR ");
					while(increment_scheduled<scheduled.length)
					{
						grace_date_added=commonlocal.getFutureDayDate(scheduled[increment_scheduled][0].toString(),15);
						if(commonlocal.getDaysFromTwoDate(date,grace_date_added)<0)
						{
							System.out.println("/n The from date is inside the rec fun:"+scheduled[increment_scheduled][0].toString());
							System.out.println("/nThe to date is inside the rec fun:"+date+"/n");
							penal_interest+=calculatePenalInt(ac_type,scheduled[increment_scheduled][0].toString(),date,Double.parseDouble(scheduled[increment_scheduled][1].toString()),category);
						}
						increment_scheduled++;	
					}
				}
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
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		System.out.println(" %%%%%%%%penal interest"+penal_interest);
		System.out.println(" %%%%%%%% penal_interest collected"+penal_interest_collected);
		
		if(Math.round(penal_interest-penal_interest_collected)>0)   // This condition is required since at any given point of time the interest value should not be negative
			return  Math.round(penal_interest-penal_interest_collected);
		else
			return 0.0;
	}
	
	// Method added by Murugesh on 9/2/2006
	/**
	 * @author Murugesh:
	 * This method is used to find the extra interest on any given date.
	 * @since The Theme: Here we are taking the verified 'R' records less than or equal to the given date 
	 * for that ac_no and we are taking the result sets last record's extra interest which will be the extra 
	 * interest for the given date.
	 * @param String ac_type
	 * @param int ac_no
	 * @param date for which extra interest to be calculated
	 * @return double ie, the extra interest
	 */
	public double getLastRecoveryExtraInt(String ac_type,int ac_no,String date) 
	{
		double extra_int=0.0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			
			rs = stmt.executeQuery(" select extra_int from LoanTransaction as lt where ac_type='"+ac_type+"' and ac_no="+ac_no+" and trn_type='R' and  concat(right(lt.int_date,4),'-',mid(lt.int_date,locate('/',lt.int_date)+1, (locate('/',lt.int_date,4)-locate('/',lt.int_date)-1)),'-',left(lt.int_date,locate('/',lt.int_date)-1)) <='"+Validations.convertYMD(date)+"' and ve_tml is not null order by trn_seq desc limit 1");
			if(rs.next() && rs.getString("extra_int")!=null)
			{
				extra_int = rs.getDouble("extra_int");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return extra_int;
	}
	
	// Method added by Murugesh on 10/2/2009
	/**
	 * @author Murugesh:
	 * This method is used to find the closing principal balance on any given date.
	 * @since The Theme: Here we are taking the verified 'R' and 'D' records less than or equal to the given date 
	 * for that ac_no and we are taking the result sets last record's principal balance which will be the closing 
	 * balance for the given date.
	 * @param String ac_type
	 * @param int ac_no
	 * @param date for which closing balance to be calculated
	 * @return double ie, the closing balance
	 */
	public double getCurrentDayClosingBalance(String ac_type,int ac_no,String date) 
	{
		double closing_balance=0.0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			
			rs = stmt.executeQuery("select pr_bal from LoanTransaction as lt where ac_type='"+ac_type+"' and ac_no="+ac_no+" and (trn_type='D' or trn_type='R') and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))<='"+Validations.convertYMD(date)+"' and ve_tml is not null and ve_user is not null and ve_date is not null order by trn_seq  desc limit 1");
			if(rs.next() && rs.getString("pr_bal")!=null)
			{
				closing_balance = rs.getDouble("pr_bal");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return closing_balance;
	}
	
	// Method added by Murugesh on 10/2/2009
	/**
	 * @author Murugesh
	 * This method is used to find the interest to be paid on any given date for any given 
	 * account number.The last paid int date will be denoted by int_upto_date in the LoanMaster.
	 * We have to calculate interest from that date to the given date.
	 * 
	 * @since: The Theme: First get the principle balance for the int_upto_date, since the last interest paid was on that date 
	 * and so now we have to calculate the interest from that date for that record's principle balance. Then the next thing we have 
	 * to find is that, is there any disbursement happened for that account after that int_upto_date since after any disburement the
	 * principle balance is going to increase.If so any we have to biforcate and have to calculate the interest.Then before starting the 
	 * calculation we have to add the last recovery record extra_interest to the available amount.
	 * 
	 * @param String ac_type 
	 * @param int ac_no
	 * @param String date till which we have to calculate the ineterest
	 * @param int sub_category of the customer which can be get from CustomerMaster 
	 * 
	 * @return double ie, the interest to be paid
	 */
	public double calculateInterestForDailyPosting(String ac_type,int ac_no,String date,int category)
	{
		double pr_bal=0.0;
		String int_upto_date=null;
		int int_rate_type=0,no_inst=0;
		double int_rate=0,disb_amt=0,extra_amt=0,interest=0.0,sanc_amt=0.0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement stmt1=conn.createStatement();
			ResultSet rs=null;
			
			rs=stmt.executeQuery("select int_rate_type,int_rate,int_upto_date,no_inst,sanc_amt from LoanMaster where ac_type='"+ac_type+"' and ac_no="+ac_no+"");
			System.out.println("111111111111111111111");

			if(rs.next() && rs.getString("int_upto_date")!=null)
			{
				int_upto_date=rs.getString("int_upto_date");
				int_rate_type=rs.getInt("int_rate_type");
				int_rate=rs.getDouble("int_rate");
				no_inst=rs.getInt("no_inst");
				sanc_amt=rs.getDouble("sanc_amt");
				pr_bal=getCurrentDayClosingBalance(ac_type,ac_no,int_upto_date);
				System.out.println("222222222222222");
			}
			rs.close();
			
			
			if(int_upto_date!=null)
			{
				System.out.println("33333333333333333333");
				int count_rows=0;
				rs = stmt.executeQuery("select trn_date,trn_type,pr_bal from LoanTransaction as lt where ac_type='"+ac_type+"' and ac_no="+ac_no+" and ((trn_type='R' and pr_amt != 0) or trn_type='D') and ve_tml is not null and ve_user is not null and ve_date is not null and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1)) >'"+Validations.convertYMD(int_upto_date)+"' and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1)) <='"+Validations.convertYMD(date)+"' and concat(right(lt.int_date,4),'-',mid(lt.int_date,locate('/',lt.int_date)+1, (locate('/',lt.int_date,4)-locate('/',lt.int_date)-1)),'-',left(lt.int_date,locate('/',lt.int_date)-1)) <= '"+Validations.convertYMD(int_upto_date)+"' order by concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))");
				if(rs.last())
				{
					count_rows=rs.getRow();
					rs.beforeFirst();
				}
				
				Object data[][]= new Object[count_rows+1][3];
				
				//ResultSet rs_previous_disp = stmt1.executeQuery("select sum(trn_amt) as disb from LoanTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and trn_type='D' and ve_tml is not null and ve_user is not null and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))< "+Validations.convertYMD(int_upto_date)+"");
				
				/*if(rs_previous_disp.next() && rs_previous_disp.getString("disb")!=null)
				{
					disb_amt=rs_previous_disp.getDouble("disb");
				}*/
				
				data[0][0]=int_upto_date;
				data[0][1]="R";
				data[0][2]=String.valueOf(pr_bal);
				System.out.println("444444444444444");
				System.out.println("int upto date:"+data[0][0].toString());
				System.out.println("pr_bal:"+data[0][2].toString());
				
				int i=1;
				while(rs.next())
				{
					data[i][0]=rs.getString("trn_date");
					data[i][1]=rs.getString("trn_type");
					data[i][2]=rs.getString("pr_bal");
					System.out.println("int upto date:"+data[i][0].toString());
					System.out.println("trn_type"+data[i][1].toString());
					System.out.println("pr_bal:"+data[i][2].toString());
					i++;
				}
				
				extra_amt=getLastRecoveryExtraInt(ac_type,ac_no,int_upto_date);
				System.out.println("666666666666666666");
				
				if(int_rate_type==1)  /**floating type calculation*/
				{
					System.out.println("inside floating 33333333333333");
					for(int j=0;j<data.length;j++)
					{
						/*if(data[j][1].equals("D"))
						{
							disb_amt+=Double.parseDouble(data[j][2].toString());
						}*/
						
						if(j==data.length-1)
						{
							interest+=calculateInterest(ac_type,int_upto_date,Validations.addDays(date,-1),Double.parseDouble(data[j][2].toString()),category,no_inst,sanc_amt,ac_no);
							System.out.println(" interest value is:"+interest);
						}
						else
						{
							interest+=calculateInterest(ac_type,int_upto_date,Validations.addDays(data[j+1][0].toString(),-1),Double.parseDouble(data[j][2].toString()),category,no_inst,sanc_amt,ac_no);
							int_upto_date=Validations.addDays(data[j+1][0].toString(),-1);
							System.out.println(" interest value is:"+interest);
						}
					}
				}
				else   /**fixed type direct calculation*/
				{
					System.out.println("inside fixed 33333333333333");
					for(int j=0;j<data.length;j++)
					{
												
						if(j==data.length-1)
						{
							interest+=Math.round((Double.parseDouble(data[j][2].toString())*Validations.dayCompare(int_upto_date,Validations.addDays(date,-1))*int_rate)/(365*100));
							System.out.println(" interest value is:"+interest);
						}
						else
						{
							interest+=Math.round((Double.parseDouble(data[j][2].toString())*Validations.dayCompare(int_upto_date,Validations.addDays(data[j+1][0].toString(),-1))*int_rate)/(365*100));
							int_upto_date=Validations.addDays(data[j+1][0].toString(),-1);
							System.out.println(" interest value is:"+interest);
						}
					}
				} // End of else
				
				System.out.println("7777777777777777");
				interest-=extra_amt;
				System.out.println(" interest value is:"+interest);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		System.out.println("Before returning interest"+interest);
		if(Math.round(interest)>0)   // This condition is required since at any given point of time the interest value should not be negative
			return Math.round(interest);
		else
			return 0.0;
	}
	
	// Method added by Murugesh on 17/2/2006
	/**
	 * @author Murugesh
	 * This method is used to calculate the interest_upto_date,interest_payable for an account on any given
	 * date for a given amount. Since interest is debited after debiting other_charges and penal_interest,
	 * normally not everytime the remaining amount will be available to pay the full interest amount and cover
	 * the whole interest period. so in this case we have to take the remaining amount and if it is more than the
	 * interest amount to be paid then there is no problem we can debit the whole interest amount and the int_upto_date
	 * will be one day lesser than the transacion date. But if the amount available is less than the interest to be paid 
	 * then we have to check until which date it is covering and we have to may the int_upto_date as that date. And in
	 * some case we will have some extra interest which wont cover for a single day and we will put this amount in the 
	 * extra interest field.
	 * 
	 * @since: The Theme: First get the principle balance for the int_upto_date, since the last interest paid was on that date 
	 * and so now we have to calculate the interest from that date for that record's principle balance. Then the next thing we have 
	 * to find is that, is there any disbursement happened for that account after that int_upto_date since after any disburement the
	 * principle balance is going to increase.If so any we have to biforcate and have to calculate the interest.Then before starting the 
	 * calculation we have to add the last recovery record extra_interest to the available amount.
	 * 
	 * @param String ac_type 
	 * @param int ac_no
	 * @param String transaction_date
	 * @param double amount_available
	 * @param int sub_category of the customer which can be get from CustomerMaster 
	 * 
	 * @return LoanTransactionObject
	 */
	
	
	//Added by sunitha on 02/02/2010
	
	public SurityCoBorrowerObject[] getSurityCoBorrowerDetailsTwo(String ac_type,int ac_no) {
		ArrayList arrayList = new ArrayList(8);
		SurityCoBorrowerObject[] security=null;
		Connection conn=null;
		int i=0;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			rs = stmt.executeQuery("select bm.ac_type,moduleabbr,bm.ac_no,sm.cid,bm.type from BorrowerMaster bm,Modules m,ShareMaster sm where bm.ac_type=modulecode and sm.ac_type=bm.ac_type and sm.ac_no=bm.ac_no and ln_ac_type='"+ac_type+"' and ln_ac_no="+ac_no+" ");
		        rs.last();
		        security = new SurityCoBorrowerObject[rs.getRow()];
                rs.beforeFirst();
        	while(rs.next()) {
				
				security[i]=new SurityCoBorrowerObject();
				security[i].setAc_type(rs.getString("bm.ac_type"));
				security[i].setType(rs.getString("bm.type"));
				security[i].setModuleabbr(rs.getString("moduleabbr"));
				security[i].setAc_no(rs.getString("bm.ac_no"));
				security[i].setCid(rs.getString("sm.cid"));
				i++;
			}
			System.out.println("security==========>>>"+security.length);
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return security;
	}
	
	
	public LoanTransactionObject calculateInterestForRecovery(String ac_type,int ac_no,String date,double amount,int category)
	{
		double pr_bal=0.0;
		String int_upto_date=null,temp_int_upto_date=null;
		int int_rate_type=0,no_inst=0;
		double int_rate=0,disb_amt=0,extra_amt=0,interest=0.0,sanc_amt=0.0,temp_interest=0.0;
		Connection conn=null;
		Object data[][]=null;
		LoanTransactionObject loan_trn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement stmt1=conn.createStatement();
			ResultSet rs=null;
			
			rs=stmt.executeQuery("select int_rate_type,int_rate,int_upto_date,no_inst,sanc_amt from LoanMaster where ac_type='"+ac_type+"' and ac_no="+ac_no+"");
			System.out.println("111111111111111111111");

			if(rs.next() && rs.getString("int_upto_date")!=null)
			{
				int_upto_date=rs.getString("int_upto_date");
				temp_int_upto_date=int_upto_date;
				int_rate_type=rs.getInt("int_rate_type");
				int_rate=rs.getDouble("int_rate");
				no_inst=rs.getInt("no_inst");
				sanc_amt=rs.getDouble("sanc_amt");
				pr_bal=getCurrentDayClosingBalance(ac_type,ac_no,int_upto_date); // get the principle balance for the int_upto_date for which the interest have to calculated
				System.out.println("222222222222222");
			}
			rs.close();
			
			if(int_upto_date!=null)
			{
				System.out.println("33333333333333333333");
				int count_rows=0;
				rs = stmt.executeQuery("select trn_date,trn_type,pr_bal from LoanTransaction as lt where ac_type='"+ac_type+"' and ac_no="+ac_no+" and ((trn_type='R' and pr_amt != 0) or trn_type='D') and ve_tml is not null and ve_user is not null and ve_date is not null and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1)) >'"+Validations.convertYMD(int_upto_date)+"' and concat(right(lt.int_date,4),'-',mid(lt.int_date,locate('/',lt.int_date)+1, (locate('/',lt.int_date,4)-locate('/',lt.int_date)-1)),'-',left(lt.int_date,locate('/',lt.int_date)-1)) <= '"+Validations.convertYMD(int_upto_date)+"' order by concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))");
				if(rs.last())
				{
					count_rows=rs.getRow();
					rs.beforeFirst();
				}
				
				 data= new Object[count_rows+1][5];
				
				//ResultSet rs_previous_disp = stmt1.executeQuery("select sum(trn_amt) as disb from LoanTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and trn_type='D' and ve_tml is not null and ve_user is not null and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))< "+Validations.convertYMD(int_upto_date)+"");
				
				/*if(rs_previous_disp.next() && rs_previous_disp.getString("disb")!=null)
				{
					disb_amt=rs_previous_disp.getDouble("disb");
				}*/
				
				data[0][0]=int_upto_date;
				data[0][1]="R";
				data[0][2]=String.valueOf(pr_bal);
				System.out.println("444444444444444");
				System.out.println("int upto date:"+data[0][0].toString());
				System.out.println("pr_bal:"+data[0][2].toString());
				
				int i=1;
				while(rs.next())
				{
					data[i][0]=rs.getString("trn_date");
					data[i][1]=rs.getString("trn_type");
					data[i][2]=rs.getString("pr_bal");
					System.out.println("int upto date:"+data[i][0].toString());
					System.out.println("trn_type"+data[i][1].toString());
					System.out.println("pr_bal:"+data[i][2].toString());
					i++;
				}
				
				extra_amt=getLastRecoveryExtraInt(ac_type,ac_no,int_upto_date); // get the extra_interest from the previous or last recovery record
				System.out.println("666666666666666666");
				
				if(int_rate_type==1)  /**floating type calculation*/
				{
					System.out.println("inside floating 33333333333333");
					for(int j=0;j<data.length;j++)
					{
						/*if(data[j][1].equals("D"))
						{
							disb_amt+=Double.parseDouble(data[j][2].toString());
						}*/
						
						if(j==data.length-1)
						{
							temp_interest=calculateInterest(ac_type,int_upto_date,Validations.addDays(date,-1),Double.parseDouble(data[j][2].toString()),category,no_inst,sanc_amt,ac_no);
							interest+=temp_interest;
							
							data[j][3]=String.valueOf(Validations.dayCompare(int_upto_date,Validations.addDays(date,-1)));
							data[j][4]=String.valueOf(temp_interest);
							//Validations.dayCompare(fdate,tdate)
							
							System.out.println(" interest value is:"+interest);
						}
						else
						{
							temp_interest=calculateInterest(ac_type,int_upto_date,Validations.addDays(data[j+1][0].toString(),-1),Double.parseDouble(data[j][2].toString()),category,no_inst,sanc_amt,ac_no);
							interest+=temp_interest;
							
							data[j][3]=String.valueOf(Validations.dayCompare(int_upto_date,Validations.addDays(data[j+1][0].toString(),-1)));
							data[j][4]=String.valueOf(temp_interest);
							
							int_upto_date=Validations.addDays(data[j+1][0].toString(),-1);
							System.out.println(" interest value is:"+interest);
							//System.out.println(" the int upto date is :"+data[j][3]);
						}
					}
				}
				else   /**fixed type direct calculation*/
				{
					System.out.println("inside fixed 33333333333333");
					for(int j=0;j<data.length;j++)
					{
												
						if(j==data.length-1)
						{
							temp_interest=Math.round((Double.parseDouble(data[j][2].toString())*Validations.dayCompare(int_upto_date,Validations.addDays(date,-1))*int_rate)/(365*100));
							interest+=temp_interest;
							
							data[j][3]=String.valueOf(Validations.dayCompare(int_upto_date,Validations.addDays(date,-1)));
							data[j][4]=String.valueOf(temp_interest);
							
							System.out.println(" interest value is:"+interest);
						}
						else
						{
							temp_interest=Math.round((Double.parseDouble(data[j][2].toString())*Validations.dayCompare(int_upto_date,Validations.addDays(data[j+1][0].toString(),-1))*int_rate)/(365*100));
							interest+=temp_interest;
							
							data[j][3]=String.valueOf(Validations.dayCompare(int_upto_date,Validations.addDays(data[j+1][0].toString(),-1)));
							data[j][4]=String.valueOf(temp_interest);
							
							int_upto_date=Validations.addDays(data[j+1][0].toString(),-1);
							System.out.println(" interest value is:"+interest);
						}
					}
				} // End of else
				
				//amount+=extra_amt; // We are adding the extra amount to the amount before calculating the int_upto_date
				loan_trn = new LoanTransactionObject();
				
				if(int_rate_type==1)  /**floating type calculation*/
					loan_trn.setInterestRate(getIntRate(ac_type,date,category,no_inst,sanc_amt,ac_no));
				else
					loan_trn.setInterestRate(int_rate);
				
				interest = Math.round(interest);
				double per_day_interest=0.0,interest_payable=0;
				double amount_available=Math.round(amount+extra_amt);
				System.out.println("*** out extra_amt available:"+extra_amt);
				System.out.println("*** out amount available:"+amount_available);
				int adjustable_days=0;
				temp_interest=0.0;
				int_upto_date=temp_int_upto_date;
				if(amount_available<interest )
				{
					for(int j=0;j<data.length;j++)
					{
						System.out.println(" j amount available :"+amount_available);
						System.out.println(" j part interest :"+Double.parseDouble(data[j][4].toString()));
						if(amount_available<Double.parseDouble(data[j][4].toString()))
						{
							System.out.println("amount available:"+amount_available);
							per_day_interest=Double.parseDouble(data[j][4].toString())/Integer.parseInt(data[j][3].toString());
							adjustable_days= (int) Math.floor(amount_available/per_day_interest);
							interest_payable = Math.round(adjustable_days*per_day_interest);
							
							//loan_trn.setInterestPayable(interest_payable);  // changed on 14/11/2006 
							loan_trn.setInterestPayable( Math.round(interest_payable+temp_interest) ); // added on 14/11/2006
							//loan_trn.setIntUptoDate(Validations.addDays(int_upto_date,adjustable_days));
							loan_trn.setIntUptoDate(commonlocal.getFutureDayDate(int_upto_date,adjustable_days));
							loan_trn.setExtraIntAmount(Math.round(amount_available-interest_payable));
							
							System.out.println("Per Day Interest:"+per_day_interest);
							System.out.println("Days Adjusted:"+adjustable_days);
							
							System.out.println("Interest Payable:"+(interest_payable+temp_interest));
							System.out.println("previous int UPto Date:"+int_upto_date);
							//System.out.println("Int upto Date"+(Validations.addDays(int_upto_date,adjustable_days)));
							System.out.println("Int upto Date"+(commonlocal.getFutureDayDate(int_upto_date,adjustable_days)));
							System.out.println("Extra Amount:"+(amount_available-interest_payable));
							
							return(loan_trn);
						}
						else if(amount_available>Double.parseDouble(data[j][4].toString()))
						{
							amount_available-=Double.parseDouble(data[j][4].toString());
							if(j==0)
							{
								//amount_available-=Double.parseDouble(data[j][4].toString());
								int_upto_date=data[j][0].toString();
							}
							else
							{
								// int_upto_date=Validations.addDays(data[j][0].toString(),-1); changed on 14/11/2006
								int_upto_date=Validations.addDays(data[j+1][0].toString(),-1); // added on 14/11/2006
							}
							temp_interest+=Double.parseDouble(data[j][4].toString());
						}
						else  if(amount_available==Double.parseDouble(data[j][4].toString()))
						{
							loan_trn.setInterestPayable(temp_interest+Double.parseDouble(data[j][4].toString()));
							loan_trn.setIntUptoDate(Validations.addDays(data[j][0].toString(),-1));
							loan_trn.setExtraIntAmount(0.0);
							
							return(loan_trn);
						}
					}
				}
				else
				{
					loan_trn.setInterestPayable(interest);
					loan_trn.setIntUptoDate(Validations.addDays(date,-1));
					loan_trn.setExtraIntAmount(0);
					
					return(loan_trn);
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return null;
	}

	// Method added by Murugesh on 21/02/2006
	/**
	 * @author Murugesh:
	 * This method is used to calculate the other charges to be paid if any on a given date for
	 * any account number.
	 * @since The Theme: Here how we are calculating the other charges to be paid on any given date is,
	 * first calculate the sum of transaction amount for the verified 'O'records less than or equal to the given date in the LoanTransaction 
	 * which will denote the total other_charges to be collected on that date.Then calculate the sum of other_amt towards
	 * the 'R' record less than or equal to the given date which will denote the total other charges paid till that date. If the total other_charges collected
	 * is greater than the total other charges paid then still some other_charges amount to be paid is pending on that date. so this is the other_charges 
	 * amount to be collected for that account on that date.
	 * 
	 * @param String ac_type
	 * @param int ac_no
	 * @param String date till which we have to find the other_charges to be paid
	 * 
	 * @return double   
	 */
	public double getOtherChargesForRecovery(String ac_type,int ac_no,String date)
	{
		double other_charges=0.0;
		double other_charges_collected=0.0;
		double other_charges_paid=0.0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			
			rs = stmt.executeQuery("select sum(trn_amt) as sum_other_charges from LoanTransaction as lt where ac_type='"+ac_type+"' and ac_no="+ac_no+" and trn_type='O'and ve_tml is not null and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))<='"+Validations.convertYMD(date)+"'");
			if(rs.next() && rs.getString("sum_other_charges")!=null)
			{
				other_charges_collected=rs.getDouble("sum_other_charges");
			}
			rs.close();
			
			rs=stmt.executeQuery("select sum(other_amt) as sum_other_charges_paid from LoanTransaction as lt where ac_type='"+ac_type+"' and ac_no="+ac_no+" and trn_type='R' and  ve_tml is not null and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))<='"+Validations.convertYMD(date)+"'");
			if(rs.next() && rs.getString("sum_other_charges_paid")!=null)
			{
				other_charges_paid=rs.getDouble("sum_other_charges_paid");
			}
			
			other_charges = other_charges_collected-other_charges_paid;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}

		if(other_charges>0) // since at any point of time the other_charges should not be negative
			return other_charges;
		else
			return 0.0;
	}
	
	// Method added by Murugesh on 21/02/2006
	/**
	 * @author Murugesh:
	 * This method is used to update the LoanRecoveryDetail table on a given date with the interest,
	 *  penal interest, other charges that has to be paid on the give date and the principle
	 * balance on the given date. while doing this we have to delete any values from the table
	 * for that ac_type and ac_no for other date,since the table should at any point contain
	 * only one row for that particular ac_type and ac_no.
	 * @param String ac_type
	 * @param int ac_no
	 * @param String date for which the table has to be updated
	 * @param int sub_category which can be reterived from the CustomerMaster for the ac_no's cid
	 * @param int flag which is for future use, so just pass 1
	 * @param String user_id ie, the user id who is calling this function
	 * @param Stirng user_tml ie, the user tml who is calling this function
	 * @return int 
	 */
	public int postRecoveryDetails(String ac_type,int ac_no,String date,int category,int flag,String user_id,String user_tml)
	{
		double other_charges=0.0;
		double penal_interest=0.0;
		double interest=0.0;
		double loan_balance=0.0;
		int ret_value=0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			
			other_charges=getOtherChargesForRecovery(ac_type,ac_no,date);
			penal_interest=calculatePenalInterestForRecovery(ac_type,ac_no,date,category);
			interest=calculateInterestForDailyPosting(ac_type,ac_no,date,category);
			loan_balance=getCurrentDayClosingBalance(ac_type,ac_no,date);
			
			int delete_value=stmt.executeUpdate("delete from LoanRecoveryDetail where ac_type='"+ac_type+"' and ac_no="+ac_no+"");

			ret_value=stmt.executeUpdate("insert into LoanRecoveryDetail (ac_type,ac_no,processing_date,int_amt,penal_amt,other_charges,loan_balance,de_tml,de_user,de_date) values('"+ac_type+"',"+ac_no+",'"+date+"',"+interest+","+penal_interest+","+other_charges+","+loan_balance+",'"+user_tml+"','"+user_id+"','"+getSysDateTime()+"')");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return ret_value;
	}
	
	// Method added by Murugesh on 22/02/2006
	/**
	 * @author Murugesh:
	 * This method is used to post the recovery details ie, the interest,penal interest,
	 * other charges on the given date ,principal balance on the given date for all the active
	 * loan accounts in the loan master.
	 * 
	 * @since Theme: Here first we are taking all the active loan accounts ie, the accounts
	 * which are not closed and we are calling the postRecoveryDetails function for all those
	 * accounts which will post those values into the LoanRecoveryDetail table.
	 * 
	 * @param String date for which the table has to be updated
	 * @param String ac_type
	 * @param int ac_no
	 * 
	 * @return int ie, the number of accounts that has been posted on that day
	 */
	public int doDailyRecoveryPosting(String date,String user_id,String user_tml)
	{
		int count=0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			
			rs = stmt.executeQuery("select  distinct lm.ac_type,lm.ac_no,cm.sub_category from LoanMaster as lm, CustomerMaster as cm where lm.ac_type like '1010%' and lm.sanc_ver='Y' and  lm.sanc_date is not null and lm.close_date is null and cm.cid=lm.cid order by ac_type,ac_no");
			
			int update_value=0;
			while(rs.next())
			{
				update_value=0;
				update_value=postRecoveryDetails(rs.getString("ac_type"),rs.getInt("ac_no"),date,rs.getInt("sub_category"),1,user_id,user_tml);
				if(update_value==1)
					count++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return count;
	}

	// Method added by Murugesh on 07/08/2006
	/**
	 * @author Murugesh
	 * 
	 * This function is used to find the principal over due, principal amount for the
	 * current installation and the advance amount if any paid on the given date which is
	 * passed as the third parameter to the function. 
	 * @param ln_acty
	 * @param ln_acno
	 * @param date
	 * @return LoanTransactionObject
	 */
	
	public LoanTransactionObject getPrincipalOutstandings(String ac_type,int ac_no,String date)
	{	
		int count=0;
		Connection conn=null;
		conn=getConnection();
		ResultSet rs=null;
		double scheduled_amt=0.0,recovered_amt=0.0,last_sch_amt=0.0,current_inst_amt=0.0;
		double prn_overdue=0.0,prn_advance=0.0;
		String last_sch_date=null;
		LoanTransactionObject loan_trn=null;
		try{
			CommonLocalHome commonLocalHome = (CommonLocalHome) getInitialContext().lookup("COMMONLOCALWEB");
			CommonLocal commonLocal = commonLocalHome.create();
			
			Statement stmt=conn.createStatement();
	
			rs = stmt.executeQuery("select trn_type,trn_date,pr_amt from LoanTransaction as lt where ac_type='"+ac_type+"' and ac_no="+ac_no+" and ((trn_type='R' ) or trn_type='S') and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))<='"+Validations.convertYMD(date)+"' and lt.ve_tml is not null order by concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1)),trn_seq");
			
			if(!rs.next())
				return null;
			else
			{
				rs.beforeFirst();
				while(rs.next())
				{
					if(rs.getString("trn_type").equals("S"))
					{
						scheduled_amt += rs.getDouble("pr_amt");
						last_sch_date=rs.getString("trn_date");
						last_sch_amt=rs.getDouble("pr_amt");
					}
					else if(rs.getString("trn_type").equals("R"))
					{
						recovered_amt += rs.getDouble("pr_amt");
						//removed the comment for LD on 05/05/2008
						
						last_sch_date=rs.getString("trn_date");
						last_sch_amt=rs.getDouble("pr_amt");
					}
					System.out.println("...14..a..last_sch_date"+last_sch_date);
				}
				rs.last();
				System.out.println("...14..b..last_sch_date"+last_sch_date);
				if(commonLocal.getDaysFromTwoDate(last_sch_date,date)<=5)
				{
					current_inst_amt=last_sch_amt;
					scheduled_amt -= last_sch_amt;
				}
					
				if(scheduled_amt>recovered_amt)
				{
					prn_overdue=scheduled_amt-recovered_amt;
				}
				if(scheduled_amt<recovered_amt)
				{
					prn_advance=recovered_amt-scheduled_amt;
				}
				
				if(prn_advance>0)
				{
					if(prn_advance>current_inst_amt)
						current_inst_amt=0;
					else
						current_inst_amt = current_inst_amt-prn_advance;
				}
				
				loan_trn = new LoanTransactionObject();
				loan_trn.setPrincipalBalance(prn_overdue); //Principle OD
				loan_trn.setPrincipalPayable(current_inst_amt); // Current Install
				loan_trn.setPrincipalPaid(prn_advance); // Advance payment
				
				//file_logger.info("my The Principle overdue:"+prn_overdue);
				//file_logger.info("my The current installment:"+current_inst_amt);
				//file_logger.info("my The Advance payment:"+prn_advance);
				
				return loan_trn;
			}
		}
		catch(Exception e){
			//file_logger.info(e.getMessage());
		}
		finally{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return null;
	}
	
	// Method added by Murugesh on 18/03/2006
	/**
	 * @author Murugesh:
	 * This method is used to retrive the list of documnents specified in the DocParam table
	 * @param String ac_type which is for future use so pass any string
	 * @param int ac_no which is for future use so pass any int
	 * @return Obeject[][] 
	 */
	 public Object[][] getDocs(String ac_type,int ac_no)
	 {
		Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		Object docs[][]=null;
		int rows=0;
		try{

			conn=getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select doc_no,doc_desc from DocParam");
			if(rs.last())
			{
				rows=rs.getRow();
				docs = new Object[rows][2];
				rows=0;
			}
			rs.beforeFirst();
			
			while(rs.next()){
				
				docs[rows][0]=rs.getString("doc_no");
				docs[rows][1]=rs.getString("doc_desc");
				rows++;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
	 	return docs;
	 }
	 
	 // Method added by Murugesh on 20/03/2006
	 /**
	  * @author Murugesh:
	  * This method is used to get the list of documents collected for a particular 
	  * ac_type and ac_no.
	  * @param String ac_type
	  * @param int ac_no
	  * @return DocumentsObject[]
	  * 
	  */
	 public DocumentsObject[] getLoanDocuments(String ac_type,int ac_no)
	 {
	 	Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		DocumentsObject docs[]=null;
		int rows=0;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			System.out.println("AAAAA");
			
			rs = stmt.executeQuery("select ld.*,dp.doc_desc from LoanDocs as ld , DocParam as dp where ld.ac_type='"+ac_type+"' and ld.ac_no="+ac_no+" and ld.doc_code=dp.doc_no ");
			if(rs.last())
			{
				docs = new DocumentsObject[rs.getRow()];
				System.out.println("the lenght"+rs.getRow());
				System.out.println("the lenght"+docs.length);
			}
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				System.out.println("BBBBB");
				docs[i] = new DocumentsObject();
				docs[i].setLntype(ac_type);
				docs[i].setAcno(ac_no);
				docs[i].setDoccode(rs.getInt("doc_code"));
				docs[i].setOther_details(rs.getString("oth_dtls"));
				docs[i].setPledged_date(rs.getString("pledged_date"));
				docs[i].setReturned_date(rs.getString("return_date"));
				docs[i].setDoc_desc(rs.getString("doc_desc"));
				docs[i].uv.setUserTml(rs.getString("de_tml"));
				docs[i].uv.setUserId(rs.getString("de_user"));
				docs[i].uv.setUserDate(rs.getString("de_date"));
				i++;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
	 	return docs;
	 }
	 
	 // Method added by Murugesh on 20/03/2006
	 /**
	  * @author Murugesh:
	  * This method is used to store the list of documents collected for a particular ac_type
	  * and ac_no. 
	  * @since The Theme: This method may be called to submit the collected documents or to
	  * update the collected documents whose detail are all in the DocumentsObject[] passed.
	  * So if we have any documents available for the ac_type and ac_no in the LoanDocs 
	  * table, first delete all the values and insert the DocumentsObject[] values which will have
	  * the old deleted values and also the newly updated values
	  * 
	  * @param Stirng ac_type
	  * @param int ac_no
	  * @param DocumentsObject[]
	  * 
	  * @return boolean ie, true if data is updated else false
	  */
	 public boolean submitLoanDocuments(String ac_type,int ac_no,DocumentsObject[] loan_docs)
	 {
	 	Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		DocumentsObject docs[]=null;
		int rows=0;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			System.out.println("AAAAA");
			if(loan_docs!=null){
				System.out.println("bbbbb");
				stmt.executeUpdate("delete from LoanDocs where ac_type='"+ac_type+"' and ac_no="+ac_no+"");
				PreparedStatement pstmt=conn.prepareStatement("insert into LoanDocs values(?,?,?,?,?,?,?,?,?,?)");
				for(int i=0;i<loan_docs.length;i++){
					pstmt.setString(1,loan_docs[i].getLntype());
					pstmt.setInt(2,loan_docs[i].getAcno());
					pstmt.setInt(3,loan_docs[i].getDoccode());
					pstmt.setString(4,loan_docs[i].getOther_details());
					pstmt.setString(5,null);
					pstmt.setString(6,loan_docs[i].getPledged_date());
					pstmt.setString(7,loan_docs[i].getReturned_date());
					pstmt.setString(8,loan_docs[i].uv.getUserTml());
					pstmt.setString(9,loan_docs[i].uv.getUserId());
					pstmt.setString(10,loan_docs[i].uv.getUserDate());
					pstmt.addBatch();
				}
			int a[]=pstmt.executeBatch();
			if(a.length>0)
				return true;
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
	 	return false;
	 }
	 
	 // Method added by Murugesh on 18/04/2006
	 public boolean insertTransferVoucherEntry(LoanTransactionObject loan_trn)
	 {
	 	Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		PreparedStatement pstmt=null;
		int trn_seq=0;
		String int_upto_date=null;
		double loan_balance=0.0;
		try{
			if(loan_trn !=null && loan_trn.uv.getVerTml()!= null){
				conn=getConnection();
				stmt=conn.createStatement();
				
				loan_balance = getCurrentDayClosingBalance(loan_trn.getAccType(),loan_trn.getAccNo(),loan_trn.getTransactionDate());
				if(loan_trn.getTransactionAmount() > loan_balance) // at any time trn_amt should be less than the loan_balance on the current day if not the transaction should not occur
					return false;
				
				rs=stmt.executeQuery("select lst_tr_seq+1 as trn_seq,int_upto_date from LoanMaster where ac_type='"+loan_trn.getAccType()+"' and ac_no="+loan_trn.getAccNo()+"");
				if(rs.next())
				{
					if(rs.getString("trn_seq")!=null) // This condition is checked because when it is called for the first time the value will be null and so this condtion check is necessary
						trn_seq = rs.getInt("trn_seq");
					else
						trn_seq=1;
					int_upto_date=rs.getString("int_upto_date");
				}
				rs.close();
				int update=stmt.executeUpdate("update LoanMaster set lst_tr_seq="+trn_seq+",lst_trn_date='"+loan_trn.getTransactionDate()+"' where  ac_type='"+loan_trn.getAccType()+"' and ac_no="+loan_trn.getAccNo()+"");
				pstmt = conn.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

				pstmt.setString(1,loan_trn.getAccType());
				pstmt.setInt(2,loan_trn.getAccNo());
				pstmt.setInt(3,trn_seq);
				pstmt.setString(4,loan_trn.getTransactionDate());
				pstmt.setString(5,loan_trn.getTranType());
				pstmt.setDouble(6,loan_trn.getTransactionAmount());
				pstmt.setString(7,loan_trn.getTranMode());
				pstmt.setString(8,loan_trn.getTranSou());
				pstmt.setInt(9,loan_trn.getReferenceNo());
				pstmt.setString(10,loan_trn.getTranNarration());
				pstmt.setString(11,null);
				pstmt.setString(12,loan_trn.getCdind());
				pstmt.setString(13,int_upto_date);
				pstmt.setDouble(14,loan_trn.getTransactionAmount());
				pstmt.setDouble(15,0);
				pstmt.setDouble(16,0);
				pstmt.setDouble(17,0);
				pstmt.setDouble(18,0);
				pstmt.setDouble(19,(loan_balance-loan_trn.getTransactionAmount()));
				pstmt.setString(20,loan_trn.uv.getUserTml());
				pstmt.setString(21,loan_trn.uv.getUserId());
				pstmt.setString(22,loan_trn.uv.getUserDate());
				pstmt.setString(23,loan_trn.uv.getVerTml());
				pstmt.setString(24,loan_trn.uv.getVerId());
				pstmt.setString(25,loan_trn.uv.getVerDate());
				
				update = pstmt.executeUpdate();
				
				if(update>0)
					return true;
			}
			else
				return false;
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
	 	return false;
	 }
	 
	 // Method added by Murugesh on 25/04/2006
	 public double getModuleTableValue(String data)
	 {
	 	Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		double return_value=0.0;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			if(data!=null){
				if(data.equalsIgnoreCase("ind_max_value"))
					rs = stmt.executeQuery("select  min_bal from Modules where modulecode=1010000"); // Since ind_max_value is stored in min_bal field of LN row in Modules table 
				if(data.equalsIgnoreCase("ins_max_value"))
					rs = stmt.executeQuery("select  max_amt from Modules where modulecode=1010000"); // Since ins_max_value is stored in max_amt field of LN row in Modules table
				
				if(rs.next())
					return_value = rs.getDouble(1);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
	 	return return_value;
	 }
	 
	 // Method added by Murugesh on 26/04/2006
	 public int getUpdatedTrnScrollNumber() 
	 {
		Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		int transfer_scroll_number=0;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			rs = stmt.executeQuery("select  last_trf_scroll_no from Modules where modulecode=1010000"); // Since transfer_scroll_number is stored in last_trf_scroll_no field of LN row in Modules table 
			if(rs.next()){
				if(rs.getString("last_trf_scroll_no")==null)
					transfer_scroll_number=0;
				else
					transfer_scroll_number=rs.getInt("last_trf_scroll_no");
				
				transfer_scroll_number++;
				stmt.executeUpdate("update Modules set last_trf_scroll_no ="+transfer_scroll_number+" where modulecode=1010000");
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
		return transfer_scroll_number;
	 }
	 
	 // Method added by Murugesh on 26/04/2006
	 /**
	  * @author Murugesh 
	  * This method is used for the validation of an ac_no during TransferVoucher
	  * 
	  * @since The Theme: Here the validations for a particular ac_type and ac_no 
	  * viz; weather ac_no Verified, ac_no sanctioned, ac_no sanction verified, 
	  * ac_no closed are cheecked and a string is returned. If the string is true then
	  *	the ac_no is valid and can be used for transaction else the stack wise error
	  * string is returned.
	  * 
	  * @param String ac_type
	  * @param int ac_no
	  * @return String
	  */
	 public String transferVoucherValidation(String ac_type,int ac_no)
	 {
	 	Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		String return_string=null;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			rs = stmt.executeQuery("select loan_sanc,sanc_ver,close_date,ve_tml from LoanMaster where ac_type='"+ac_type+"'and ac_no="+ac_no+" "); 
			if(rs.next()){
				if(rs.getString("ve_tml")==null){
					return_string="Account Number Not Verified";
				}
				else if(rs.getString("loan_sanc")!=null && rs.getString("loan_sanc").trim().equalsIgnoreCase("N")){
					return_string="Account Number Not Sanctioned";
				}
				else if(rs.getString("sanc_ver")!=null && rs.getString("sanc_ver").trim().equalsIgnoreCase("N")){
					return_string="Account Number Not Sanctioned Verified";
				}
				else if(rs.getString("close_date")!=null){
					return_string="Account Number Closed";
				}
				else
					return_string="true";
			}
			else
				return_string="Record Not Found";
				
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
	 	return return_string;
	 }
	 
	 // Method added by Murugesh on 02/05/2006
	 public String getIntUpToDateOnCurrentDate(String ac_type,int ac_no,String date)
	 {
	 	Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		String int_upto_date=null;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select int_date from LoanTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and (trn_type = 'D' or trn_type = 'R') and ve_tml is not null and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(date)+"' order by trn_seq desc limit 1");
			if(rs.next())
				int_upto_date=rs.getString("int_date");
			System.out.println(" ac_type:"+ac_type+" ac_no:"+ac_no+" date:"+int_upto_date);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
				try{                
					conn.close();
				}catch(Exception exception){exception.printStackTrace();}
		}
	 	return int_upto_date;
	 }
	 
	 public String getLastTrnDateOnCurrentDate(String ac_type,int ac_no,String date)
	 {
	 	Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		String last_trn_date=null;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select trn_date from LoanTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and (trn_type = 'D' or trn_type = 'R' or trn_type = 'O') and ve_tml is not null and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(date)+"' order by trn_seq desc limit 1");
			if(rs.next())
				last_trn_date=rs.getString("trn_date");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
				try{                
					conn.close();
				}catch(Exception exception){exception.printStackTrace();}
		}
	 	return last_trn_date;
	 }
	 
	 // Method added by Murugesh on 05/04/2006
	 /**
	  * @author Murugesh
	  * This method is used to calculate the Principal overdue date and principal overdue amount if any for
	  * an account on a given day
	  * 
	  * @param String ac_type
	  * @param int ac_no
	  * @param String date
	  * 
	  * @return String[2], ie [1] --> overdue amount, [2] --> overdue date
	  *  */
	 public String[] getPrnDueDateAmountForCurrentDate(String ac_type,int ac_no,String date)
	 {
	 	Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		String return_values[]= new String[2];
		Vector sch_amt=new Vector();
		Vector sch_date=new Vector();
		double principal_collected=0.0;
		double total_sch_amount=0.0;
		double extra_prn_collected=0.0;
		
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select trn_type,trn_date,pr_amt from LoanTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(date)+"' and (trn_type='S' or trn_type='R') and ve_tml is not null order by concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))");
			while(rs.next()){
				if(rs.getString("trn_type").equalsIgnoreCase("S")){
					sch_amt.add(rs.getString("pr_amt"));
					sch_date.add(rs.getString("trn_date"));
					total_sch_amount += rs.getDouble("pr_amt");
				}
				else
					principal_collected += rs.getDouble("pr_amt");
			}
			rs=stmt.executeQuery("select sanc_amt-(select sum(trn_amt)from LoanTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and trn_type='D' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(date)+"' and ve_tml is not null) as prn_extra from LoanMaster where ac_no="+ac_no+" and ac_type like '"+ac_type+"'");
			if(rs.next())
				extra_prn_collected=rs.getDouble("prn_extra");
			
			principal_collected += extra_prn_collected;
			if(principal_collected>=total_sch_amount){
				return_values[0]="0";
				return_values[1]="0";
			}
			else{
				double temp_sch=0.0;
				for(int i=0;i<sch_amt.size();i++){
					temp_sch=Double.parseDouble(sch_amt.get(i).toString());
					if(temp_sch>principal_collected){
						return_values[0]=String.valueOf(total_sch_amount-principal_collected);
						return_values[1]=sch_date.get(i).toString();
						break;
					}
					else{
						principal_collected -= temp_sch;
						total_sch_amount -= temp_sch;
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
	 	return return_values;
	 }
	 
	 // Method added by Murugesh on 05/04/2006
	 public double calculateInterestForNPA(String ac_type,int ac_no,String date,String int_upto_date, int category)
	 {
	 	double pr_bal=0.0;
	 	int int_rate_type=0,no_inst=0;
	 	double int_rate=0,disb_amt=0,extra_amt=0,interest=0.0,sanc_amt=0.0;
	 	Connection conn=null;
	 	try	{
	 		conn=getConnection();
	 		Statement stmt=conn.createStatement();
	 		Statement stmt1=conn.createStatement();
	 		ResultSet rs=null;
				
	 		rs=stmt.executeQuery("select int_rate_type,int_rate,int_upto_date,no_inst,sanc_amt from LoanMaster where ac_type='"+ac_type+"' and ac_no="+ac_no+"");
	 		System.out.println("111111111111111111111");

	 		if(rs.next() && rs.getString("int_upto_date")!=null){
	 			int_rate_type=rs.getInt("int_rate_type");
	 			int_rate=rs.getDouble("int_rate");
	 			no_inst=rs.getInt("no_inst");
	 			sanc_amt=rs.getDouble("sanc_amt");
	 			pr_bal=getCurrentDayClosingBalance(ac_type,ac_no,int_upto_date);
	 			System.out.println("222222222222222");
	 		}
	 		rs.close();

	 		if(int_upto_date!=null)	{
	 			System.out.println("33333333333333333333");
	 			int count_rows=0;
	 			rs = stmt.executeQuery("select trn_date,trn_type,pr_bal from LoanTransaction as lt where ac_type='"+ac_type+"' and ac_no="+ac_no+" and ((trn_type='R' and pr_amt != 0) or trn_type='D') and ve_tml is not null and ve_user is not null and ve_date is not null and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1)) >'"+Validations.convertYMD(int_upto_date)+"' and concat(right(lt.int_date,4),'-',mid(lt.int_date,locate('/',lt.int_date)+1, (locate('/',lt.int_date,4)-locate('/',lt.int_date)-1)),'-',left(lt.int_date,locate('/',lt.int_date)-1)) <= '"+Validations.convertYMD(int_upto_date)+"' and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1)) <='"+Validations.convertYMD(date)+"' order by concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))");
	 			if(rs.last()){
	 				count_rows=rs.getRow();
	 				rs.beforeFirst();
	 			}
					
	 			Object data[][]= new Object[count_rows+1][3];
					
	 			//ResultSet rs_previous_disp = stmt1.executeQuery("select sum(trn_amt) as disb from LoanTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and trn_type='D' and ve_tml is not null and ve_user is not null and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))< "+Validations.convertYMD(int_upto_date)+"");
					
	 			/*if(rs_previous_disp.next() && rs_previous_disp.getString("disb")!=null)
	 			 {
	 			 	disb_amt=rs_previous_disp.getDouble("disb");
	 			 }*/
					
	 			data[0][0]=int_upto_date;
	 			data[0][1]="R";
	 			data[0][2]=String.valueOf(pr_bal);
	 			System.out.println("444444444444444");
	 			System.out.println("int upto date:"+data[0][0].toString());
	 			System.out.println("pr_bal:"+data[0][2].toString());
					
	 			int i=1;
	 			while(rs.next()){
	 				data[i][0]=rs.getString("trn_date");
	 				data[i][1]=rs.getString("trn_type");
	 				data[i][2]=rs.getString("pr_bal");
	 				System.out.println("int upto date:"+data[i][0].toString());
	 				System.out.println("trn_type"+data[i][1].toString());
	 				System.out.println("pr_bal:"+data[i][2].toString());
	 				i++;
	 			}
					
	 			extra_amt=getLastRecoveryExtraInt(ac_type,ac_no,int_upto_date);
	 			System.out.println("666666666666666666");
					
	 			if(int_rate_type==1){  /**floating type calculation*/
	 				System.out.println("inside floating 33333333333333");
	 				for(int j=0;j<data.length;j++){
	 					/*if(data[j][1].equals("D"))
	 					 {
	 					 disb_amt+=Double.parseDouble(data[j][2].toString());
	 					 }*/
							
	 					if(j==data.length-1){
	 						interest+=calculateInterest(ac_type,int_upto_date,Validations.addDays(date,-1),Double.parseDouble(data[j][2].toString()),category,no_inst,sanc_amt,ac_no);
	 						System.out.println(" interest value is:"+interest);
	 					}
	 					else{
	 						interest+=calculateInterest(ac_type,int_upto_date,Validations.addDays(data[j+1][0].toString(),-1),Double.parseDouble(data[j][2].toString()),category,no_inst,sanc_amt,ac_no);
	 						int_upto_date=Validations.addDays(data[j+1][0].toString(),-1);
	 						System.out.println(" interest value is:"+interest);
	 					}
	 				}
	 			}
	 			else{   /**fixed type direct calculation*/
	 				System.out.println("inside fixed 33333333333333");
	 				for(int j=0;j<data.length;j++){
	 					if(j==data.length-1){
	 						interest+=Math.round((Double.parseDouble(data[j][2].toString())*Validations.dayCompare(int_upto_date,Validations.addDays(date,-1))*int_rate)/(365*100));
	 						System.out.println(" interest value is:"+interest);
	 					}
	 					else{
	 						interest+=Math.round((Double.parseDouble(data[j][2].toString())*Validations.dayCompare(int_upto_date,Validations.addDays(data[j+1][0].toString(),-1))*int_rate)/(365*100));
	 						int_upto_date=Validations.addDays(data[j+1][0].toString(),-1);
	 						System.out.println(" interest value is:"+interest);
	 					}
	 				}
	 			} // End of else
					
	 			System.out.println("7777777777777777");
	 			interest-=extra_amt;
	 			System.out.println(" interest value is:"+interest);
	 		}
	 	}
	 	catch(Exception e){
	 		e.printStackTrace();
	 	}
	 	finally{
	 		try{                
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	if(Math.round(interest)>0)   // This condition is required since at any given point of time the interest value should not be negative
	 		return Math.round(interest);
	 	else
	 		return 0.0;
	 }
	 
	 /**
	  * @author Murugesh:
	  */
	 public LoanReportObject[] getNPAReport(String process_date,String table_type,String asset_code,String ac_type,int from_acno,int to_acno)
	 {
	 	Connection conn=null;
	 	LoanReportObject loan_obj[]=null;
	 	int rows=0;
	 	try	{
	 		conn=getConnection();
	 		Statement stmt=conn.createStatement();
	 		Statement stmt_inst=conn.createStatement();
	 		ResultSet rs=null,rs_inst=null;
	 		if(ac_type!=null && ac_type.equalsIgnoreCase("ALL"))
	 			ac_type="1010%";
	 		if(asset_code!=null && asset_code.equalsIgnoreCase("-1"))
	 			asset_code="%";
	 		if(from_acno==-1 && to_acno==-1)
	 			rs=stmt.executeQuery("select npa.*,concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,(lm.sanc_amt-lm.disb_left) as loan_disb,sanc_amt,no_inst,int_type,lst_trn_date,(((right(npa_pro_date,4)-right(pr_odue_from,4))*12)+(mid(npa_pro_date,4,2)-mid(pr_odue_from,4,2))-if(left(npa_pro_date,2)<left(pr_odue_from,2),1,0)) as period_pri,(((right(npa_pro_date,4)-right(npa.int_upto_date,4))*12)+(mid(npa.npa_pro_date,4,2)-mid(npa.int_upto_date,4,2))-if(left(npa_pro_date,2)<left(npa.int_upto_date,2),1,0)) as period_int from NPATable as npa, CustomerMaster as cm,LoanMaster as lm where concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1, (locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(process_date)+"' and days="+table_type+" and npa.ac_type like '"+ac_type+"' and asset_code like '"+asset_code+"' and npa.ac_type=lm.ac_type and npa.ac_no=lm.ac_no and lm.cid=cm.cid order by asset_code,npa.ac_type,npa.ac_no");
	 		else
	 			rs=stmt.executeQuery("select npa.*,concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,(lm.sanc_amt-lm.disb_left) as loan_disb,sanc_amt,no_inst,int_type,lst_trn_date,(((right(npa_pro_date,4)-right(pr_odue_from,4))*12)+(mid(npa_pro_date,4,2)-mid(pr_odue_from,4,2))-if(left(npa_pro_date,2)<left(pr_odue_from,2),1,0)) as period_pri,(((right(npa_pro_date,4)-right(npa.int_upto_date,4))*12)+(mid(npa.npa_pro_date,4,2)-mid(npa.int_upto_date,4,2))-if(left(npa_pro_date,2)<left(npa.int_upto_date,2),1,0)) as period_int from NPATable as npa, CustomerMaster as cm,LoanMaster as lm where concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1, (locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(process_date)+"' and days="+table_type+" and npa.ac_type like '"+ac_type+"' and asset_code like '"+asset_code+"' and npa.ac_no between "+from_acno+" and "+to_acno+" and npa.ac_type=lm.ac_type and npa.ac_no=lm.ac_no and lm.cid=cm.cid order by asset_code,npa.ac_type,npa.ac_no");
	 		
	 		if(rs.last()){
	 			rows=rs.getRow();
	 			loan_obj = new LoanReportObject[rows];
	 			rs.beforeFirst();
	 			rows=0;
	 		}
	 		
	 		while(rs.next()){
	 			
	 			loan_obj[rows]=new LoanReportObject();
	 			loan_obj[rows].setAccNo(rs.getInt("ac_no"));
	 			loan_obj[rows].setAccType(rs.getString("ac_type"));
	 			loan_obj[rows].setName(rs.getString("name"));
	 			loan_obj[rows].setDisbursementDate(getFirstDisbDate(rs.getString("ac_type"),rs.getInt("ac_no")));
	 			loan_obj[rows].setDisburseAmount(rs.getDouble("loan_disb"));
	 			loan_obj[rows].setPrnOverDueAmt(rs.getDouble("pri_odue_amt"));
	 			loan_obj[rows].setIntUptoDate(rs.getString("int_upto_date"));
	 			loan_obj[rows].setIntOverDueAmt(rs.getDouble("int_odue_amt"));
	 			loan_obj[rows].setSanctionedAmount(rs.getDouble("sanc_amt"));
	 			loan_obj[rows].setNoInstallments(rs.getInt("no_inst"));
	 			loan_obj[rows].setTransactiondate(rs.getString("lst_trn_date"));
	 			if(rs.getString("int_type")!=null && rs.getString("int_type").equalsIgnoreCase("0")){
	 				loan_obj[rows].setInterestType(0);
	 				rs_inst=stmt_inst.executeQuery("select trn_amt from LoanTransaction where ac_type = '"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type='S' limit 1");
	 				if(rs_inst.next())
	 					loan_obj[rows].setInstallmentAmount(rs_inst.getDouble("trn_amt"));
	 			}
	 			else{
	 				loan_obj[rows].setInterestType(1);
	 				rs_inst=stmt_inst.executeQuery("select pr_amt from LoanTransaction where ac_type = '"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type='S' limit 1");
	 				if(rs_inst.next())
	 					loan_obj[rows].setInstallmentAmount(rs_inst.getDouble("pr_amt"));
	 			}
	 			if(rs.getString("npa_towards")!=null && rs.getString("npa_towards").equalsIgnoreCase("P")){
	 				loan_obj[rows].setNpatowards("Principal");
	 				loan_obj[rows].setOverdueperiod(rs.getInt("pri_odue_prd"));
	 				loan_obj[rows].setNpasincedate(rs.getString("pr_odue_from"));
	 			}
	 			else{
	 				loan_obj[rows].setNpatowards("Interest");
	 				loan_obj[rows].setOverdueperiod(rs.getInt("int_odue_prd"));
	 				loan_obj[rows].setNpasincedate(rs.getString("int_upto_date"));
	 			}
	 			loan_obj[rows].setLoanBalance(rs.getDouble("loan_balance"));
	 			loan_obj[rows].setProvisionReq(rs.getDouble("prov_amt"));
	 			
	 			rows++;
	 		}
	 	}
	 	catch(Exception e){
	 		e.printStackTrace();
	 	}
	 	finally{
	 		try{                
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	return loan_obj;
	 }
	 
	 public String getFirstDisbDate(String ac_type,int ac_no)
	 {
	 	Connection conn=null;
	 	String return_value=null;
	 	try	{
	 		conn=getConnection();
	 		Statement stmt=conn.createStatement();
	 		ResultSet rs=null;
	 		
	 		rs=stmt.executeQuery("select trn_date from LoanTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and trn_type='D' and ve_tml is not null order by trn_seq limit 1");
	 		if(rs.next())
	 			return_value = rs.getString("trn_date");
	 	}
	 	catch(Exception e){
	 		e.printStackTrace();
	 	}
	 	finally{
	 		try{                
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	return return_value;
	 }
	 
	 // Method added by Murugesh on 24/04/2006
	 public String[][] getLoanAdminTable(String table_name)
	 {
	 	String table_data[][]=null;
	 	Connection conn=null;
		ResultSet rs=null;
		ResultSetMetaData rs_meta_data=null;
		Statement stmt=null;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			
			System.out.println(" loan admin table "+table_name);
			if(table_name!=null){
				rs = stmt.executeQuery("select * from "+table_name+" ");
				if(rs!=null)
					rs_meta_data = rs.getMetaData();
				if(rs!=null && rs_meta_data!=null && rs.last())
					table_data = new String[rs.getRow()][rs_meta_data.getColumnCount()];
				
				rs.beforeFirst();
				rs.next();
				if(table_data!=null){
					for(int i=0;i<table_data.length;i++){
							for(int j=0;j<table_data[i].length;j++)
								table_data[i][j]=rs.getString(j+1);
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
	 
	 //	 Method added by Murugesh on 16/05/2006 
	 public void setLoanAdminTable(String table_name,String table_data[][],String query)
	 {
	 	Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		PreparedStatement pstmt=null;
		int update[]=null;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			if(table_name!=null && table_name.equalsIgnoreCase("LoanEntryIns"))
				pstmt=conn.prepareStatement("insert into LoanEntryIns values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			if(table_name!=null && table_name.equalsIgnoreCase("LoanIntRate"))
				pstmt=conn.prepareStatement("insert into LoanIntRate values(?,?,?,?,?,?,?,?,?,?)");
			
			if(table_name!=null && table_name.equalsIgnoreCase("LoanPeriodRate"))
				pstmt=conn.prepareStatement("insert into LoanPeriodRate values(?,?,?,?,?,?,?,?,?)");
			
			if(table_name!=null && table_name.equalsIgnoreCase("PenalIntRate"))
				pstmt=conn.prepareStatement("insert into PenalIntRate values(?,?,?,?,?,?,?,?)");
			
			if(table_name!=null && table_name.equalsIgnoreCase("LoanCategoryRate"))
				pstmt=conn.prepareStatement("insert into LoanCategoryRate values(?,?,?,?,?,?,?,?)");
			
			if(table_name!=null && table_name.equalsIgnoreCase("LoanPurposes"))
				pstmt=conn.prepareStatement("insert into LoanPurposes values(?,?,?,?,?)");
			
			
			
			if(table_data!=null){
				for(int i=0;i<table_data.length;i++){
					for(int j=0;j<table_data[i].length;j++){
						pstmt.setString(j+1,table_data[i][j]);
					}
					pstmt.addBatch();
				}
				stmt.executeUpdate("delete from "+query+"");
				update=pstmt.executeBatch();
				if(update==null || update.length==0)
					sessionContext.setRollbackOnly();
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
	 }
	 
	 public String[][] getStageCode() 
	 {		
	 	String code_arr[][]=null;
	 	Connection conn=null;
	 	try{
	 		conn=getConnection();			
	 		ResultSet rs_npa;
	 		Statement st_npa;
	 		st_npa = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	 		rs_npa = st_npa.executeQuery("select distinct stage_cd,descptn from StageMaster");
	 		rs_npa.last();
	 		code_arr = new String[rs_npa.getRow()][2];
	 		rs_npa.beforeFirst();
	 		int i=0;
	 		while(rs_npa.next()){
	 			code_arr[i][0] = rs_npa.getString("stage_cd"); 
	 			code_arr[i][1] = rs_npa.getString("descptn"); 
	 			i++;
	 		}
	 	}catch(Exception exe){
	 		exe.printStackTrace();
	 	}
	 	finally{
	 		try{		    	
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}		
	 	return code_arr;
	 }
	 
	 public Vector getLoanOverdueReport(String ac_type,String stage_type,String report_date,String int_upto_date,int from_ac_no,int to_ac_no,double prn_from_amt,double prn_to_amt,double int_from_amt,double int_to_amt) 
	 {		
	 	Connection conn=null;
	 	ResultSet rs=null,rs_inst=null;
 		Statement stmt=null,stmt_inst=null;
 		Vector return_values= new Vector();
 		LoanReportObject loan_rep=null;
 		double int_overdue,principle_overdue;
 		String loan_type=null,prn_overdues[]=null,int_date=null;
 		int ac_no,sub_category,default_months;
	 	try{
	 		conn=getConnection();
	 		stmt=conn.createStatement();
	 		stmt_inst=conn.createStatement();
	 		System.out.println("inside over due report");
	 		rs=stmt.executeQuery("select concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,sub_category,lm.* from LoanMaster lm,CustomerMaster as cm where ac_type like '"+ac_type+"' and remd_no like '"+stage_type+"' and ac_no between "+from_ac_no+" and "+to_ac_no+" and (close_date is null or concat(right( close_date,4),'-',mid( close_date,locate('/', close_date)+1, (locate('/', close_date,4)-locate('/', close_date)-1)),'-',left( close_date,locate('/', close_date)-1))>='"+Validations.convertYMD(report_date)+"') and ( concat(right( int_upto_date,4),'-',mid( int_upto_date,locate('/', int_upto_date)+1, (locate('/', int_upto_date,4)-locate('/', int_upto_date)-1)),'-',left( int_upto_date,locate('/', int_upto_date)-1))<='"+Validations.convertYMD(int_upto_date)+"')  and lm.cid=cm.cid order by ac_type,ac_no");
	 		while(rs.next()){
	 			System.out.println("inside over due report... 2");
	 			loan_type=rs.getString("ac_type");
	 			ac_no=rs.getInt("ac_no");
	 			sub_category=rs.getInt("sub_category");
	 			
	 			prn_overdues=getPrnDueDateAmountForCurrentDate(loan_type,ac_no,report_date);
	 			if(prn_overdues==null || prn_overdues[0].equalsIgnoreCase("0")){
	 				principle_overdue=0;
	 				default_months=0;
	 			}
	 			else{
	 				principle_overdue=Double.parseDouble(prn_overdues[0].trim());
	 				default_months=Validations.getDefaultMonths(prn_overdues[1],report_date);
	 			}
	 			
	 			if(principle_overdue>=prn_from_amt && principle_overdue<=prn_to_amt){
	 				System.out.println("inside over due report... 3");
	 				int_date = getIntUpToDateOnCurrentDate(loan_type,ac_no,report_date);
	 				if(int_date!=null)
	 					int_overdue=calculateInterestForNPA(loan_type,ac_no,report_date,int_date,sub_category);
					else
						int_overdue=0;
		 			
	 				if(int_overdue>=int_from_amt && int_overdue<=int_to_amt){
		 				
	 					System.out.println("inside over due report... 4");
		 				loan_rep=new LoanReportObject();
		 				
		 				loan_rep.setAccType(loan_type);
		 				loan_rep.setAccNo(ac_no);
		 				loan_rep.setName(rs.getString("name"));
						loan_rep.setShareType(rs.getString("sh_type"));
						loan_rep.setShareNo(rs.getInt("sh_no"));
						loan_rep.setShareAmt(getCurrentDayShareBalance(loan_type,ac_no,report_date));
						loan_rep.setSanctionDate(rs.getString("sanc_date"));
						loan_rep.setSanctionedAmount(rs.getDouble("sanc_amt"));
						if(rs.getString("int_type")!=null && rs.getString("int_type").equalsIgnoreCase("0")){
							loan_rep.setInterestType(0);
							rs_inst=stmt_inst.executeQuery("select trn_amt from LoanTransaction where ac_type = '"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type='S' limit 1");
			 				if(rs_inst.next())
			 					loan_rep.setInstallmentAmount(rs_inst.getDouble("trn_amt"));
			 			}
			 			else{
			 				loan_rep.setInterestType(1);
			 				rs_inst=stmt_inst.executeQuery("select pr_amt from LoanTransaction where ac_type = '"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type='S' limit 1");
			 				if(rs_inst.next())
			 					loan_rep.setInstallmentAmount(rs_inst.getDouble("pr_amt"));
			 			}
						loan_rep.setNoInstallments(rs.getInt("no_inst"));
						loan_rep.setIntUptoDate(int_date);
						loan_rep.setTransactiondate(getLastTrnDateOnCurrentDate(loan_type,ac_no,report_date));
						loan_rep.setReferenceNo(rs.getInt("remd_no"));
						loan_rep.setRemainderDesc(rs.getString("remd_date"));
						loan_rep.setLoanBalance(getCurrentDayClosingBalance(loan_type,ac_no,report_date));
						loan_rep.setPrnOverDueAmt(principle_overdue);
						loan_rep.setIntOverDueAmt(int_overdue);
						loan_rep.setPenalInterest(calculatePenalInterestForRecovery(loan_type,ac_no,report_date,sub_category));
						loan_rep.setOtherCharges(getOtherChargesForRecovery(loan_type,ac_no,report_date));
						loan_rep.setDefaultedMths(default_months);
						
						System.out.println("inside over due report... 6");
						return_values.add(loan_rep);
		 			}
	 			}
	 		}
	 		
	 	}catch(Exception exe){
	 		exe.printStackTrace();
	 	}
	 	finally{
	 		try{		    	
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	System.out.println("++++++++++++++++return_values++++++++++++++++++=" + return_values.size());
	 	return return_values;
	 }
	 public LoanReportObject getLoanOverdueReport1(String ac_type,String stage_type,String report_date,String int_upto_date,int from_ac_no,int to_ac_no,double prn_from_amt,double prn_to_amt,double int_from_amt,double int_to_amt) 
	 {		
	 	Connection conn=null;
	 	ResultSet rs=null,rs_inst=null;
 		Statement stmt=null,stmt_inst=null;
 		Vector return_values= new Vector();
 		LoanReportObject loan_rep=null;
 		double int_overdue,principle_overdue;
 		String loan_type=null,prn_overdues[]=null,int_date=null;
 		int ac_no,sub_category,default_months;
	 	try{
	 		conn=getConnection();
	 		stmt=conn.createStatement();
	 		stmt_inst=conn.createStatement();
	 		System.out.println("inside over due report");
	 		rs=stmt.executeQuery("select concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,sub_category,lm.* from LoanMaster lm,CustomerMaster as cm where ac_type like '"+ac_type+"' and remd_no like '"+stage_type+"' and ac_no between "+from_ac_no+" and "+to_ac_no+" and (close_date is null or concat(right( close_date,4),'-',mid( close_date,locate('/', close_date)+1, (locate('/', close_date,4)-locate('/', close_date)-1)),'-',left( close_date,locate('/', close_date)-1))>='"+Validations.convertYMD(report_date)+"') and ( concat(right( int_upto_date,4),'-',mid( int_upto_date,locate('/', int_upto_date)+1, (locate('/', int_upto_date,4)-locate('/', int_upto_date)-1)),'-',left( int_upto_date,locate('/', int_upto_date)-1))<='"+Validations.convertYMD(int_upto_date)+"')  and lm.cid=cm.cid order by ac_type,ac_no");
	 		while(rs.next()){
	 			System.out.println("inside over due report... 2");
	 			loan_type=rs.getString("ac_type");
	 			ac_no=rs.getInt("ac_no");
	 			sub_category=rs.getInt("sub_category");
	 			
	 			prn_overdues=getPrnDueDateAmountForCurrentDate(loan_type,ac_no,report_date);
	 			if(prn_overdues==null || prn_overdues[0].equalsIgnoreCase("0")){
	 				principle_overdue=0;
	 				default_months=0;
	 			}
	 			else{
	 				principle_overdue=Double.parseDouble(prn_overdues[0].trim());
	 				default_months=Validations.getDefaultMonths(prn_overdues[1],report_date);
	 			}
	 			
	 			if(principle_overdue>=prn_from_amt && principle_overdue<=prn_to_amt){
	 				System.out.println("inside over due report... 3");
	 				int_date = getIntUpToDateOnCurrentDate(loan_type,ac_no,report_date);
	 				if(int_date!=null)
	 					int_overdue=calculateInterestForNPA(loan_type,ac_no,report_date,int_date,sub_category);
					else
						int_overdue=0;
		 			
	 				if(int_overdue>=int_from_amt && int_overdue<=int_to_amt){
		 				
	 					System.out.println("inside over due report... 4");
		 				loan_rep=new LoanReportObject();
		 				
		 				loan_rep.setAccType(loan_type);
		 				loan_rep.setAccNo(ac_no);
		 				loan_rep.setName(rs.getString("name"));
						loan_rep.setShareType(rs.getString("sh_type"));
						loan_rep.setShareNo(rs.getInt("sh_no"));
						loan_rep.setShareAmt(getCurrentDayShareBalance(loan_type,ac_no,report_date));
						loan_rep.setSanctionDate(rs.getString("sanc_date"));
						loan_rep.setSanctionedAmount(rs.getDouble("sanc_amt"));
						if(rs.getString("int_type")!=null && rs.getString("int_type").equalsIgnoreCase("0")){
							loan_rep.setInterestType(0);
							rs_inst=stmt_inst.executeQuery("select trn_amt from LoanTransaction where ac_type = '"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type='S' limit 1");
			 				if(rs_inst.next())
			 					loan_rep.setInstallmentAmount(rs_inst.getDouble("trn_amt"));
			 			}
			 			else{
			 				loan_rep.setInterestType(1);
			 				rs_inst=stmt_inst.executeQuery("select pr_amt from LoanTransaction where ac_type = '"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type='S' limit 1");
			 				if(rs_inst.next())
			 					loan_rep.setInstallmentAmount(rs_inst.getDouble("pr_amt"));
			 			}
						loan_rep.setNoInstallments(rs.getInt("no_inst"));
						loan_rep.setIntUptoDate(int_date);
						loan_rep.setTransactiondate(getLastTrnDateOnCurrentDate(loan_type,ac_no,report_date));
						loan_rep.setReferenceNo(rs.getInt("remd_no"));
						loan_rep.setRemainderDesc(rs.getString("remd_date"));
						loan_rep.setLoanBalance(getCurrentDayClosingBalance(loan_type,ac_no,report_date));
						loan_rep.setPrnOverDueAmt(principle_overdue);
						loan_rep.setIntOverDueAmt(int_overdue);
						loan_rep.setPenalInterest(calculatePenalInterestForRecovery(loan_type,ac_no,report_date,sub_category));
						loan_rep.setOtherCharges(getOtherChargesForRecovery(loan_type,ac_no,report_date));
						loan_rep.setDefaultedMths(default_months);
						
						System.out.println("inside over due report... 6");
						
		 			}
	 			}
	 		}
	 		
	 	}catch(Exception exe){
	 		exe.printStackTrace();
	 	}
	 	finally{
	 		try{		    	
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	System.out.println("++++++++++++++++return_values++++++++++++++++++=" + loan_rep.getAccNo() );
	 	return loan_rep;
	 }
	  
	 public double getCurrentDayShareBalance(String ac_type,int ac_no,String date) 
	 {
	 	double share_balance=0.0;
	 	Connection conn=null;
	 	try{
	 		conn=getConnection();
	 		Statement stmt=conn.createStatement();
	 		ResultSet rs=null;
				
	 		rs = stmt.executeQuery("select share_bal from ShareTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and concat(right( trn_date,4),'-',mid( trn_date,locate('/', trn_date)+1, (locate('/', trn_date,4)-locate('/', trn_date)-1)),'-',left( trn_date,locate('/', trn_date)-1))<='"+Validations.convertYMD(date)+"' and ve_tml is not null order by trn_seq desc limit 1");
	 		if(rs.next() && rs.getString("share_bal")!=null)
	 			share_balance = rs.getDouble("share_bal");
	 	}
	 	catch(Exception e){
	 		e.printStackTrace();
	 	}
	 	finally{
	 		try{                
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	return share_balance;
	 }
	 
	 public Object reverseRecovery(LoanTransactionObject loan_trn,int process, AccountTransObject acc_trn, int gl_code, String gl_type) throws RecordNotUpdatedException
	 {
	 	Connection conn=null;
	 	Statement stmt=null,stmt_master=null;
	 	PreparedStatement pstmt=null;
 		ResultSet rs_master=null,rs=null;
 		double principle_amt=0,interest_amt=0,penal_int=0,other_charges=0,int_rate=0,extra_int=0;
 		double trn_amt=0,loan_balance=0,per_day_interest=0,int_adjusted=0;
 		int adjusted_days=0,trn_seq=0,lapsed_days=0,ref_no=0;
 		String int_upto_date=null,last_intupto_date=null;
 		LoanIntRateObject[] dint=null;
	 	try{
	 		cremote=home.create();
	 		System.out.println("inside revere &&&&&&&&&&&&&&... 1");
	 		conn=getConnection();
	 		stmt=conn.createStatement();
	 		stmt_master=conn.createStatement();
	 		System.out.println("Princiapl PAyable==========>"+loan_trn.getPrincipalPayable());
	 		principle_amt=Math.abs(loan_trn.getPrincipalPayable());
	 		interest_amt=Math.abs(loan_trn.getInterestPayable());
	 		penal_int=Math.abs(loan_trn.getPenaltyAmount());
	 		other_charges=Math.abs(loan_trn.getOtherAmount());
	 		System.out.println("Principle Amt===>"+principle_amt+"Int Amt"+interest_amt+"PenalInt"+penal_int+"OC===>"+other_charges);
	 		
	 		trn_amt=principle_amt+interest_amt+penal_int+other_charges;
	 		System.out.println("Trn Amt====>"+trn_amt);
	 		loan_balance=getCurrentDayClosingBalance(loan_trn.getAccType(),loan_trn.getAccNo(),loan_trn.getTransactionDate());
	 		extra_int=getLastRecoveryExtraInt(loan_trn.getAccType(),loan_trn.getAccNo(),loan_trn.getTransactionDate());
	 		loan_balance+=principle_amt;
			
	 		System.out.println("int outside :"+interest_amt);
	 		System.out.println("extra int outside :"+extra_int);
	 		System.out.println("Acctype=====>"+loan_trn.getAccType()+"Accno=======>"+loan_trn.getAccNo());
	 		rs_master=stmt_master.executeQuery("select cm.sub_category,lm.* from LoanMaster as lm,CustomerMaster as cm where lm.ac_type='"+loan_trn.getAccType()+"' and lm.ac_no="+loan_trn.getAccNo()+" and lm.cid=cm.cid");
	 		if(rs_master.next()){
	 			
	 			System.out.println("inside reverse &&&&&&&&&&&&&&... 2");
	 			if(loan_trn.uv.getVerTml() != null) {
	 				if(rs_master.getString("lst_tr_seq")!=null) { // This condition is checked because when it is called for the first time the value will be null and so this condtion check is necessary
		 				trn_seq = rs_master.getInt("lst_tr_seq")+1; 
	 				} else {
		 				trn_seq=1;
		 			}
					
	 				int update=stmt.executeUpdate("update LoanMaster set lst_tr_seq="+trn_seq+" where  ac_type='"+loan_trn.getAccType()+"' and ac_no="+loan_trn.getAccNo()+"");
					
					if(update<=0) {
						throw new RecordNotUpdatedException();
					}
					
					if(process == 1) {
						stmt.executeUpdate("delete from LoanTransaction where ref_no=" + loan_trn.getReferenceNo() + " and trn_type='R' and cd_ind='D' and ve_tml is null");
					}
					
	 			} else if(loan_trn.getReferenceNo() <= 0){
	 				/*rs = stmt.executeQuery("select lst_voucher_scroll_no from Modules where modulecode like '1010000'");
	 				if(rs.next()) {
	 					if(rs.getString("lst_voucher_scroll_no") == null) {
	 						ref_no = 1;
	 					} else {
	 						ref_no = rs.getInt("lst_voucher_scroll_no");
	 						ref_no ++;
	 					}
	 					
	 					stmt.executeUpdate("Update  Modules set lst_voucher_scroll_no =" + ref_no + " where modulecode like '1010000'");
	 				}*/
	 				ref_no = getUpdatedTrnScrollNumber();
	 			} else {
	 				ref_no = loan_trn.getReferenceNo();
	 				stmt.executeUpdate("delete from LoanTransaction where ref_no=" + ref_no + " and trn_type='R' and cd_ind='D' and ve_tml is null");
	 			}
				
	 			if(rs_master.getInt("int_rate_type")==0){  //**fixed type calculation*//
	 				int_rate=rs_master.getDouble("int_rate");
	 				per_day_interest=(int_rate*loan_balance/36500);
	 				adjusted_days=(int)Math.floor(interest_amt/per_day_interest);
	 				int_adjusted=Math.round(per_day_interest*adjusted_days);
	 				extra_int += (int_adjusted-interest_amt);
	 				int_upto_date=Validations.addDays(rs_master.getString("int_upto_date"),adjusted_days);
	 			}
	 			
	 			if(rs_master.getInt("int_rate_type")==1){ //**floating type calculation*//
	 				Vector vec=getIntRate(loan_trn.getAccType(),"01/01/1980",loan_trn.getTransactionDate(),rs_master.getInt("cm.sub_category"),rs_master.getInt("no_inst"),(rs_master.getDouble("sanc_amt")-rs_master.getDouble("disb_left")),loan_trn.getAccNo());
	 				String ctrndate=null;
	 				String to_date=null,from_date=null;
	 				from_date=loan_trn.getTransactionDate();
	 				int pre_adjusted_days=0;
	 				double int_amt=interest_amt;
	 				
	 				System.out.println("-----------------------Interest Calculation--------------");			
	 				{
	 					 dint=(LoanIntRateObject[])vec.get(0);	
	 					//if  more that int rate defined between the transaction date
	 					for(int i=dint.length-1;i>=0;i--){
	 						if(i==(dint.length-1)) {
	 							to_date=loan_trn.getTransactionDate();
	 						}
	 						from_date=dint[i].getFromDate();
	 						
	 						System.out.println("----------------------------------------");	
	 						System.out.println("From date "+from_date);	
	 						System.out.println("To date "+to_date);
	 						System.out.println("rate "+dint[i].getIntRate());
	 						System.out.println("interest value is:"+from_date);
	 						System.out.println("----------------------------------------");	
	 						
	 						System.out.println(" Loan Balance:"+loan_balance);
	 						per_day_interest=(dint[i].getIntRate()*loan_balance/36500);
	 		 				adjusted_days=(int)Math.ceil(int_amt/per_day_interest);
	 		 				if((Validations.dayCompare(from_date,to_date)+1)<(adjusted_days)){
	 		 					System.out.println(" int amount :"+int_amt);
	 		 					int_amt -= Math.round(per_day_interest*(Validations.dayCompare(from_date,to_date)));
	 		 					int_adjusted += Math.round(per_day_interest*(Validations.dayCompare(from_date,to_date)));
	 		 					to_date=Validations.addDays(from_date,-1);
	 		 				}
	 		 				else{
	 		 					int_adjusted += Math.round(per_day_interest*adjusted_days);
	 		 					extra_int += (int_adjusted-interest_amt);
	 		 					to_date=Validations.addDays(to_date,-1);
	 		 					
	 		 					last_intupto_date = getIntUpToDateOnCurrentDate(loan_trn.getAccType(),loan_trn.getAccNo(),loan_trn.getTransactionDate());
	 		 					int_upto_date=Validations.addDays(last_intupto_date,-adjusted_days);
	 		 					break;
	 		 				}
	 					}
	 				}
	 			}
	 			
	 			
	 			while ( getCurrentDayClosingBalance(loan_trn.getAccType(),loan_trn.getAccNo(),int_upto_date) <= 0) {
	 				int_upto_date = Validations.addDays(int_upto_date,1);
	 			}
	 			
	 			System.out.println("---------------------");
	 			System.out.println(" int rate :"+int_rate);
	 			System.out.println("per day int :"+per_day_interest);
	 			System.out.println("adjusted days :"+adjusted_days);
	 			System.out.println("int inside :"+int_adjusted);
		 		System.out.println("extra int outside :"+extra_int);
		 		
	 			System.out.println("inside reverse &&&&&&&&&&&&&&... 3");
	 			pstmt = conn.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

				pstmt.setString(1,loan_trn.getAccType());
				pstmt.setInt(2,loan_trn.getAccNo());
				pstmt.setInt(3,trn_seq);
				pstmt.setString(4,loan_trn.getTransactionDate());
				pstmt.setString(5,loan_trn.getTranType()); 
				System.out.println("trn_amt in bean====----====>"+trn_amt);
				pstmt.setDouble(6,-(trn_amt));
				pstmt.setString(7,loan_trn.getTranMode());
				pstmt.setString(8,loan_trn.getTranSou());
				if(loan_trn.uv.getVerTml() != null) {
					pstmt.setInt(9,loan_trn.getReferenceNo());
				} else {
					pstmt.setInt(9,ref_no);
				}
				pstmt.setString(10,loan_trn.getTranNarration());
				pstmt.setString(11,null);
				//pstmt.setString(12,loan_trn.getCdind());
				pstmt.setString(12,"D");
				pstmt.setString(13,int_upto_date);
				pstmt.setDouble(14,-principle_amt);
				pstmt.setDouble(15,-int_adjusted);
				pstmt.setDouble(16,-penal_int);
				pstmt.setDouble(17,-other_charges);
				pstmt.setDouble(18,extra_int);
				pstmt.setDouble(19,loan_balance);
				pstmt.setString(20,loan_trn.uv.getUserTml());
				pstmt.setString(21,loan_trn.uv.getUserId());
				pstmt.setString(22,loan_trn.uv.getUserDate());
				pstmt.setString(23,loan_trn.uv.getVerTml());
				pstmt.setString(24,loan_trn.uv.getVerId());
				pstmt.setString(25,loan_trn.uv.getVerDate());
				
				int update_tran=pstmt.executeUpdate();
				if(update_tran>0 && loan_trn.uv.getVerDate() != null) {
					System.out.println("inside reverse &&&&&&&&&&&&&&... 4");
					int loan_master_update = stmt.executeUpdate("update LoanMaster set int_upto_date='"+int_upto_date+"',lst_trn_date='"+loan_trn.getTransactionDate()+"' where ac_type='"+loan_trn.getAccType()+"' and ac_no="+loan_trn.getAccNo()+"");
					if(loan_master_update<=0)
						throw new RecordNotUpdatedException();
					else{
						
						System.out.println("inside reverse &&&&&&&&&&&&&&... 5");
						//postRecoveryDetails(loan_object.getAccType(),loan_trn.getAccNo(),loan_trn.getTransactionDate(),category,1,loan_trn.uv.getVerId(),loan_trn.uv.getVerTml()); // this has been done to update the LoanRecoveryDetail table with new updated values;
						//GL Enteries for credit of the above transaction  
						GLTransObject trnobj = new GLTransObject();
						
						//trnobj.setCdind(loan_trn.getCdind());
						trnobj.setCdind("D");
						trnobj.setTrnDate(loan_trn.getTransactionDate());
						trnobj.setTrnMode(loan_trn.getTranMode());
						trnobj.setAccType(loan_trn.getAccType());
						trnobj.setAccNo(String.valueOf(loan_trn.getAccNo()));
						trnobj.setTrnSeq(trn_seq);
						trnobj.setTrnType(loan_trn.getTranType());
						trnobj.setRefNo(loan_trn.getReferenceNo());
					    trnobj.setVtml(loan_trn.uv.getVerTml());
					    trnobj.setVid(loan_trn.uv.getVerId());
					    trnobj.setVDate(loan_trn.uv.getVerDate());
					    
					    if(other_charges!=0){
		                    rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+loan_trn.getAccType()+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='D' and code=4");
		                    if(rs.next()) {
		                    	trnobj.setGLType(rs.getString(1));
		                        trnobj.setGLCode(rs.getString(2));
		                        trnobj.setAmount(other_charges*rs.getInt("mult_by"));
		                    } else {
		                    	trnobj.setGLType(null);
		                        trnobj.setGLCode(null);
		                        trnobj.setAmount(other_charges);
		                    }
		                    cremote.storeGLTransaction(trnobj);
		                }
					    if(rs!=null)
					    	rs.close();

					    if(penal_int!=0){
					    	rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+loan_trn.getAccType()+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='D' and code=3");
		                    if(rs.next()){
		                    	trnobj.setGLType(rs.getString(1));
		                        trnobj.setGLCode(rs.getString(2));
		                        trnobj.setAmount(penal_int*rs.getInt("mult_by"));
		                    } else {
		                    	trnobj.setGLType(null);
		                        trnobj.setGLCode(null);
		                        trnobj.setAmount(penal_int);
		                    }
		                    cremote.storeGLTransaction(trnobj);
					    }
					    if(rs!=null)
					    	rs.close();
					    
					    if(interest_amt!=0){
					    	rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+loan_trn.getAccType()+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='D' and code=2");
		                    if(rs.next()){
		                    	trnobj.setGLType(rs.getString(1));
		                        trnobj.setGLCode(rs.getString(2));
		                        trnobj.setAmount(interest_amt*rs.getInt("mult_by"));
		                    } else {
		                    	trnobj.setGLType(null);
		                        trnobj.setGLCode(null);
		                        trnobj.setAmount(interest_amt);
		                    }
		                    cremote.storeGLTransaction(trnobj);
					    }
					    if(rs!=null)
					    	rs.close();
					    
					    if(principle_amt!=0){
					    	rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+loan_trn.getAccType()+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='D' and code=1");
		                    if(rs.next()){
		                    	trnobj.setGLType(rs.getString(1));
		                        trnobj.setGLCode(rs.getString(2));
		                        trnobj.setAmount(principle_amt*rs.getInt("mult_by"));
		                    } else {
		                    	trnobj.setGLType(null);
		                        trnobj.setGLCode(null);
		                        trnobj.setAmount(principle_amt);
		                    }
		                    cremote.storeGLTransaction(trnobj);
					    }
					    
					    if(process == 1) {
					    	if(acc_trn != null) {
					    		cremote.storeAccountTransaction(acc_trn);
					    	} else if(gl_code > 0 && gl_type != null) {
					    		
					    		trnobj = new GLTransObject();
					    		trnobj.setGLType(gl_type);
		                        trnobj.setGLCode(String.valueOf(gl_code));
		                        trnobj.setAmount(trn_amt);
					    		trnobj.setCdind("C");
								trnobj.setTrnDate(loan_trn.getTransactionDate());
								trnobj.setTrnMode(loan_trn.getTranMode());
								trnobj.setAccType(loan_trn.getAccType());
								trnobj.setAccNo(String.valueOf(loan_trn.getAccNo()));
								trnobj.setTrnSeq(trn_seq);
								trnobj.setTrnType(loan_trn.getTranType());
								trnobj.setRefNo(loan_trn.getReferenceNo());
							    trnobj.setVtml(loan_trn.uv.getVerTml());
							    trnobj.setVid(loan_trn.uv.getVerId());
							    trnobj.setVDate(loan_trn.uv.getVerDate());
							    cremote.storeGLTransaction(trnobj);
					    	}
					    }
					}
				}if( update_tran <= 0 ) {
					throw new RecordNotUpdatedException();
				}
				
				if(loan_trn.uv.getVerTml() != null) {
					return new Integer(trn_seq);
				} else {
					return new Integer(ref_no);
				}
				
	 		} else {
	 			throw new RecordNotUpdatedException();
	 		}
	 	}
	 	catch(RecordNotUpdatedException rexe){
	 		System.out.println("inside reverse &&&&&&&&&&&&&&... 6");
	 		rexe.printStackTrace();
	 		sessionContext.setRollbackOnly();
	 		throw rexe;}
	 	catch(Exception e){
	 		System.out.println("inside reverse &&&&&&&&&&&&&&... 7");
	 		e.printStackTrace();
	 		sessionContext.setRollbackOnly();
	 	}
	 	finally{
	 		try{                
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	return null;
	 }
	 
	 public LoanTransactionObject getReverseLoanDetails(int voucher_number) 
	 {

		ResultSet rs=null;
		LoanTransactionObject loan_trn = null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery("select * from LoanTransaction where ref_no=" + voucher_number + " and trn_type='R' and cd_ind='D' and ve_tml is null");
			while(rs.next()) {
				loan_trn = new LoanTransactionObject();
				
				loan_trn.setAccType(rs.getString("ac_type"));
				loan_trn.setAccNo(rs.getInt("ac_no"));
				loan_trn.setTranType("R");
				loan_trn.setTranMode("T");
				loan_trn.setCdind("D");
				loan_trn.setReferenceNo(rs.getInt("ref_no"));
				loan_trn.setTranNarration(rs.getString("trn_narr"));
				loan_trn.setTransactionAmount(Math.abs(rs.getDouble("trn_amt")));
				loan_trn.setTranSou(rs.getString("trn_source"));
				loan_trn.setPrincipalPayable(Math.abs(rs.getDouble("pr_amt")));
				loan_trn.setInterestPayable(Math.abs(rs.getDouble("int_amt") + rs.getDouble("extra_int")));
				loan_trn.setPenaltyAmount(Math.abs(rs.getDouble("penal_amt")));
				loan_trn.setOtherAmount(Math.abs(rs.getDouble("other_amt")));
				loan_trn.uv.setUserDate(rs.getString("de_date"));
				loan_trn.uv.setUserId(rs.getString("de_user"));
				loan_trn.uv.setUserTml(rs.getString("de_tml"));
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return loan_trn;
	 }
	 
	 public Vector getIntRateForRevRecovery(String ln_type,String fdate,String tdate,int category,int period,double amt,int ac_no) 
	 {
	 	Vector vec=null;
	 	Connection conn=null;
	 	int purpose_code=0;
	 	try{
	 		conn=getConnection();
	 		Statement stmt=conn.createStatement();
				
	 		ResultSet rs=null,rs_purpose=null;
	 		vec=new Vector(3);
	 		for(int j=0;j<3;j++){
	 			if(j==0)
	 			{	
	 				// Code added by Murugesh on 03/06/2006
	 				rs_purpose=stmt.executeQuery("select pps_code  from LoanMaster where ac_type='"+ln_type+"' and ac_no="+ac_no+" ");
	 				if(rs_purpose.next())
	 					purpose_code=rs_purpose.getInt("pps_code");
	 				rs_purpose=stmt.executeQuery("select  * from LoanIntRate where ln_type='"+ln_type+"' and  "+amt+" between min_bal and max_bal and  pps_code="+purpose_code+" and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
	 				if(!rs_purpose.next())
	 					purpose_code=0;
	 				rs=stmt.executeQuery("select  distinct date_fr,date_to,rate from LoanIntRate where ln_type='"+ln_type+"' and  "+amt+" between min_bal and max_bal and   pps_code="+purpose_code+" and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
	 			}
	 			else if(j==1)
	 				rs=stmt.executeQuery("select distinct date_fr,date_to,rate from LoanPeriodRate where ln_type='"+ln_type+"' and "+period+" between fr_mon and to_mon and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
	 			else if(j==2)
	 				rs=stmt.executeQuery("select distinct date_fr,date_to,rate from LoanCategoryRate where ln_type="+ln_type+" and category="+category+" and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
					
	 			rs.last();
	 			LoanIntRateObject d[]=new LoanIntRateObject[rs.getRow()];
	 			rs.beforeFirst();
	 			int i=0;
	 			while(rs.next())
	 			{
	 				d[i]=new LoanIntRateObject();
	 				d[i].setFromDate(rs.getString("date_fr"));
	 				d[i].setToDate(rs.getString("date_to"));
	 				d[i].setIntRate(rs.getDouble("rate"));
	 				i++;
	 			}
	 			vec.addElement(d);
	 		}
	 	}catch(Exception exception){exception.printStackTrace();}
	 	finally{
	 		try{		    	
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	return vec;
	 }
	 
	 public Vector getIntAccuredReport(String date,String ac_type,int from_ac_no,int to_ac_no,double from_int_amt,double to_int_amt)
	 {	
		 System.out.println("date---"+ date);
		 System.out.println("ac_type---"+ ac_type);
		 System.out.println("from_ac_no---"+ from_ac_no);
		 System.out.println("to_ac_no---"+ to_ac_no);
		 System.out.println("to_ac_no---"+ from_int_amt);
		 System.out.println("to_int_amt---"+ to_int_amt);
	 	Vector vec=new Vector();
	 	Connection conn=null;
	 	ResultSet rs=null,rs_paid=null;
	 	Statement stmt=null,stmt_paid;
	 	String int_date=null;
	 	double int_overdue=0.0;
	 	LoanReportObject loan_rep=null; 
	 	
	 	try{
	 		System.out.println("--------From getIntAccuredReport bean----------");
	 		conn=getConnection();
	 		stmt=conn.createStatement();      
	 		stmt_paid=conn.createStatement();
	 		
	 		rs = stmt.executeQuery("select concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,sub_category,lm.* from LoanMaster lm,CustomerMaster as cm where ac_type like '"+ac_type+"' and ac_no between "+from_ac_no+" and "+to_ac_no+" and (close_date is null or concat(right( close_date,4),'-',mid( close_date,locate('/', close_date)+1, (locate('/', close_date,4)-locate('/', close_date)-1)),'-',left( close_date,locate('/', close_date)-1))>='"+Validations.convertYMD(date)+"') and ( concat(right( sanc_date,4),'-',mid( sanc_date,locate('/', sanc_date)+1, (locate('/', sanc_date,4)-locate('/', sanc_date)-1)),'-',left( sanc_date,locate('/', sanc_date)-1))<='"+Validations.convertYMD(date)+"')  and lm.cid=cm.cid and lm.int_upto_date is not null order by ac_type,ac_no");
	 	while(rs.next()){
	 			System.out.println("-----resultset-----"+ rs.getRow());
	 			int_date = getIntUpToDateOnCurrentDate(rs.getString("ac_type"),rs.getInt("ac_no"),date);
	 			System.out.println("-----int_date----"+int_date);
	 			if(int_date!=null)
	 				{
	 				System.out.println("interest date not null");
	 				int_overdue=calculateInterestForNPA(rs.getString("ac_type"),rs.getInt("ac_no"),date,int_date,rs.getInt("sub_category"));
	 				System.out.println("Interest OverDue"+int_overdue);
	 				}
	 			else
	 				{
	 				System.out.println("interest over  due null and assigned 0");
	 				int_overdue=0;
	 				}
	 			if(int_overdue>=from_int_amt && int_overdue<=to_int_amt){
	 				
	 				loan_rep= new LoanReportObject();
	 				loan_rep.setAccType(rs.getString("ac_type"));
	 				loan_rep.setAccNo(rs.getInt("ac_no"));
	 				loan_rep.setName(rs.getString("name"));
	 				loan_rep.setDisburseAmount(rs.getDouble("sanc_amt")-rs.getDouble("disb_left"));
	 				loan_rep.setIntOverDueAmt(int_overdue);
	 				rs_paid = stmt_paid.executeQuery("select sum(int_amt) as int_paid from LoanTransaction where ac_type ='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and trn_type='R' and ( concat(right( trn_date,4),'-',mid( trn_date,locate('/', trn_date)+1, (locate('/', trn_date,4)-locate('/', trn_date)-1)),'-',left( trn_date,locate('/', trn_date)-1))<='"+Validations.convertYMD(int_date)+"') and ve_tml is not null");
	 				System.out.println("rs_paid"+rs_paid.getFetchSize());
	 				if(rs_paid.next())
	 					loan_rep.setInterestPaid(rs_paid.getDouble("int_paid"));
	 				
	 				vec.add(loan_rep);
	 			}
	 		}
	 	}catch(Exception exception){exception.printStackTrace();}
	 	finally{
	 		try{		    	
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	System.out.println("*************getIntAccuredReport************"+ vec.size() );
	 	return vec;
	 }
	public void loanDefaulterProcessing(String ac_type,int from_ac_no,int to_ac_no,double prn_from_amt,double prn_to_amt,double int_from_amt,double int_to_amt,double bal_from_amt,double bal_to_amt,String process_due_towards,String date)
	{
	 	Connection conn=null;
	 	ResultSet rs=null,rs_stage=null;
	 	Statement stmt=null,stmt_stage=null;
	 	double principle_bal=0.0,principle_overdue=0,interest_overdue=0;
	 	String loan_ac_type=null,default_ind=null,is_reverse_ind=null,current_default=null,current_remd_date=null;
	 	String[] prn_overdues=null;
	 	int loan_ac_no=0,prn_over_due_days=0,int_overdue_days=0,stage_overdue_index=0,current_remd_no=0;
	 	try{
	 		conn=getConnection();
	 		stmt=conn.createStatement();
	 		stmt_stage=conn.createStatement();
	 		System.out.println("===========In loanDefaulterProcessing============= ");
	 		System.out.println("Ac_type"+ac_type+"FromAcno"+from_ac_no+"ToAcno"+to_ac_no+"FromPrincipalAmt"+prn_from_amt+"ToPrnAmt"+prn_to_amt);
	 		System.out.println("IntAmtFrom"+int_from_amt+"IntAmtto"+int_to_amt);
	 		System.out.println("BalFrom"+bal_from_amt+"BaLToAmt"+bal_to_amt+"ProcessTowards"+process_due_towards);
	 		rs=stmt.executeQuery("select cm.sub_category ,lm.* from LoanMaster as lm , CustomerMaster as cm where ac_type like '"+ac_type+"' and ac_no between "+from_ac_no+" and "+to_ac_no+" and close_date is null and lm.cid=cm.cid order by ac_type and ac_no");
	 		while(rs.next()){
	 			loan_ac_type = rs.getString("ac_type");
	 			loan_ac_no = rs.getInt("ac_no");
	 			default_ind = rs.getString("default_ind").trim();
	 			principle_bal = getCurrentDayClosingBalance(loan_ac_type,loan_ac_no,date);
	 			System.out.println("Principal Balance====>"+principle_bal+"Bal From Amt"+bal_from_amt+"Bal To Amt"+bal_to_amt);
	 			if(principle_bal > bal_from_amt && principle_bal < bal_to_amt){
	 				System.out.println(" ac_type:"+loan_ac_type+" ac_no:"+loan_ac_no+ " bal:"+principle_bal+" bal_from_amt:"+bal_from_amt+" bal_to_amt:"+bal_to_amt);
	 				rs_stage = stmt_stage.executeQuery("select prd_to,reversbl from StageMaster where ac_type='"+loan_ac_type+"' and stage_cd=1");
	 				if(rs_stage.next()){
	 					stage_overdue_index = rs_stage.getInt("prd_to");
	 					is_reverse_ind = rs_stage.getString("reversbl");
	 				}else{
	 					stage_overdue_index=0;
	 					is_reverse_ind = "F";
	 				}
	 				System.out.println("Process Due Towards====>"+process_due_towards);
	 				if(process_due_towards.equalsIgnoreCase("P") || process_due_towards.equalsIgnoreCase("B")){
	 					prn_overdues=getPrnDueDateAmountForCurrentDate(loan_ac_type,loan_ac_no,date);
	 					System.out.println("Prn OverDue"+prn_overdues);
	 					if(prn_overdues==null || prn_overdues[0].equalsIgnoreCase("0")){
	 						principle_overdue=0;
	 						prn_over_due_days=0;
	 					}else{
	 						principle_overdue=Double.parseDouble(prn_overdues[0].trim());
	 						prn_over_due_days=Validations.dayCompare(prn_overdues[1],date);
	 					}
		 			}
	 				if(process_due_towards.equalsIgnoreCase("I") || (process_due_towards.equalsIgnoreCase("B") && principle_overdue > prn_from_amt && principle_overdue < prn_to_amt)){
	 					if(rs.getString("int_upto_date")!=null){
	 						interest_overdue=calculateInterestForNPA(loan_ac_type,loan_ac_no,date,rs.getString("int_upto_date"),rs.getInt("sub_category"));
	 						int_overdue_days=Validations.dayCompare(rs.getString("int_upto_date"),date);
	 					}else{
	 						interest_overdue=0;
	 						int_overdue_days=0;
	 					}
	 				}
	 				current_remd_no=rs.getInt("remd_no");
	 				current_remd_date=rs.getString("remd_date");
	 				current_default = default_ind;
	 				System.out.println("Process Due"+process_due_towards+"Index"+stage_overdue_index);
	 				if(process_due_towards.equalsIgnoreCase("P") || (process_due_towards.equalsIgnoreCase("B") && prn_over_due_days >= int_overdue_days)){
	 					if(prn_over_due_days >= stage_overdue_index){
	 						current_default="Y P";
	 					}else{
	 						if(default_ind.equalsIgnoreCase("N") || (default_ind.equalsIgnoreCase("Y P") && is_reverse_ind.equalsIgnoreCase("T"))){
	 							current_default="N";
	 							current_remd_no=0;
		 						current_remd_date=null;
	 						}
	 					}
	 				}
	 				if(process_due_towards.equalsIgnoreCase("I") || (process_due_towards.equalsIgnoreCase("B") && int_overdue_days > prn_over_due_days )){
	 					if(int_overdue_days >= stage_overdue_index){
	 						current_default="Y I";
	 					}else{
	 						if(default_ind.equalsIgnoreCase("N") || (default_ind.equalsIgnoreCase("Y I") && is_reverse_ind.equalsIgnoreCase("T"))){
	 							current_default="N";
	 							current_remd_no=0;
		 						current_remd_date=null;
	 						}
	 					}
	 				}
	 				stmt_stage.executeUpdate("update LoanMaster set default_ind='"+current_default+"',remd_no="+current_remd_no+",remd_date='"+current_remd_date+"' where ac_type ='"+loan_ac_type+"' and ac_no="+loan_ac_no+" ");
	 				System.out.println("Last stmt in Bean");
	 			}
	 		}
	 	}catch(Exception exception){exception.printStackTrace();}
	 	finally{
	 		try{		    	
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 }
	
	public Object remainderTemplates(int process_no,String ac_type,int template_no,int stage_no,String text,String de_user,String de_tml,String de_date) throws RecordNotUpdatedException,RecordsNotDeletedException
	{
		Connection conn=null;
	 	ResultSet rs=null;
	 	Statement stmt=null;
	 	Vector templates=null;
	 	try{
	 		conn=getConnection();
	 		stmt=conn.createStatement();

	 		if(process_no==1){ // deletion
	 			int update = stmt.executeUpdate("delete from Template where ac_type = '"+ac_type+"' and temp_no="+template_no+" and stage_no="+stage_no+" ");
	 			if(update==0)
	 				throw new RecordsNotDeletedException();
	 			else
	 				return Boolean.valueOf(true);
	 		}
	 		
	 		if(process_no==2){ // updation or insertion
	 			if(template_no==0){ // insertion of new template
	 				rs=stmt.executeQuery("select max(temp_no) as max_temp from Template where ac_type='"+ac_type+"' and stage_no="+stage_no+" ");
	 				if(rs.next()){
	 					template_no = (rs.getString("max_temp")==null)?1:(rs.getInt("max_temp")+1);
	 				}
	 				else
	 					template_no=1;
	 			}
	 			
	 			stmt.executeUpdate("delete from Template where ac_type = '"+ac_type+"' and temp_no="+template_no+" and stage_no="+stage_no+" ");
	 			
	 			int insert =  stmt.executeUpdate("insert into Template values('"+ac_type+"',"+template_no+","+stage_no+",'"+text+"','"+de_user+"','"+de_tml+"','"+de_date+"')");
	 			if(insert==0)
	 				throw new RecordNotUpdatedException();
	 			else
	 				return Boolean.valueOf(true);
	 		}
	 		
	 		if(process_no==3){ // getting templates
	 			rs=stmt.executeQuery("select text,temp_no from Template where ac_type like '"+ac_type+"' and stage_no="+stage_no+" order by temp_no");
	 			templates =  new Vector();
	 			while(rs.next()){
	 				templates.add(rs.getString("text")+" "+rs.getString("temp_no"));
	 			}
	 			return templates;
	 		}
	 			
	 	}catch(SQLException exception){exception.printStackTrace();}
	 	finally{
	 		try{		    	
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	return templates;
	}
	
	public Vector getLoanDefaulters(String ac_type,int from_ac_no,int to_ac_no,double prn_from_amt,double prn_to_amt,double int_from_amt,double int_to_amt,double bal_from_amt,double bal_to_amt,String default_towards,int stage_no,String date)
	{
		Connection conn=null;
	 	ResultSet rs=null,rs_stage=null;
	 	Statement stmt=null;
	 	PreparedStatement pstmt=null;
	 	Vector return_values=null;
	 	LoanReportObject loan_rep=null;
	 	LoanTransactionObject loan_trn = null;
	 	String loan_type;
	 	String[] prn_overdues;
	 	int stage_days,ac_no,prn_over_due_days,int_overdue_days,default_months;
	 	double loan_bal,principle_overdue,interest_overdue,current_inst;
	 	try{
	 		conn=getConnection();
	 		stmt=conn.createStatement();
	 		return_values= new Vector();
	 		rs=stmt.executeQuery("select concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,cm.sub_category ,lm.* from LoanMaster as lm , CustomerMaster as cm where ac_type like '"+ac_type+"' and ac_no between "+from_ac_no+" and "+to_ac_no+" and close_date is null and  default_ind like '"+default_towards+"' and remd_no="+stage_no+" and lm.cid=cm.cid order by ac_type and ac_no ");
	 		pstmt = conn.prepareStatement("select prd_from from StageMaster where ac_type like '?' and stage_cd="+stage_no+" ");
	 		while(rs.next()){
	 			loan_type = rs.getString("ac_type");
	 			ac_no=rs.getInt("ac_no");
	 			if(stage_no!=0){
	 				stage_days=0;
	 				pstmt.setString(1,loan_type);
	 				rs_stage = pstmt.executeQuery();
	 				if(rs_stage.next())
	 					stage_days=rs_stage.getInt("prd_from");
	 				if(Validations.dayCompare(rs.getString("remd_date"),date)<stage_days)
	 					continue;
	 			}
	 			loan_bal = getCurrentDayClosingBalance(loan_type,ac_no,date);
	 			if(loan_bal>=bal_from_amt && loan_bal<bal_to_amt){
	 				loan_trn=getPrincipalOutstandings(loan_type,ac_no,date);
	 				prn_overdues=getPrnDueDateAmountForCurrentDate(loan_type,ac_no,date);
	 				if(loan_trn != null){
	 					principle_overdue=Double.parseDouble(prn_overdues[0].trim());
	 					prn_over_due_days=Validations.dayCompare(prn_overdues[1],date);
	 					current_inst=loan_trn.getPrincipalPayable();// Current Install
	 					default_months=Validations.getDefaultMonths(prn_overdues[1],date);
	 				}
	 				else{
	 					principle_overdue=0;
 						prn_over_due_days=0;
 						current_inst=0;
 						default_months=0;
	 				}
 					if(principle_overdue >= prn_from_amt && principle_overdue< prn_to_amt){
 						if(rs.getString("int_upto_date")!=null){
	 						interest_overdue=calculateInterestForNPA(loan_type,ac_no,date,rs.getString("int_upto_date"),rs.getInt("sub_category"));
	 						int_overdue_days=Validations.dayCompare(rs.getString("int_upto_date"),date);
	 					}else{
	 						interest_overdue=0;
	 						int_overdue_days=0;
	 					}
 						if(interest_overdue >= int_from_amt && interest_overdue<int_to_amt){
 							loan_rep = new LoanReportObject();
 							
 							loan_rep.setAccType(loan_type);
 							loan_rep.setAccNo(ac_no);
 							loan_rep.setShareType(rs.getString("sh_type"));
 							loan_rep.setShareNo(rs.getInt("sh_no"));
 							loan_rep.setShareAmt(getCurrentDayShareBalance(loan_type,ac_no,date));
 							loan_rep.setSanctionDate(rs.getString("sanc_date"));
 							loan_rep.setSanctionedAmount(rs.getDouble("sanc_amt"));
 							loan_rep.setName(rs.getString("name"));
 							loan_rep.setInstallmentAmount(rs.getDouble("inst_amt"));
 							loan_rep.setNoOfInstals(rs.getInt("no_inst"));
 							loan_rep.setCurrentInstallment(current_inst);
 							loan_rep.setPenalInterest(calculatePenalInterestForRecovery(loan_type,ac_no,date,rs.getInt("sub_category")));
 							loan_rep.setOtherCharges(getOtherChargesForRecovery(loan_type,ac_no,date));
 							loan_rep.setLoanBalance(loan_bal);
 							loan_rep.setPrnOverDueAmt(principle_overdue);
 							loan_rep.setIntOverDueAmt(interest_overdue);
 							loan_rep.setIntUptoDate(rs.getString("int_upto_date"));
 							loan_rep.setTransactiondate(rs.getString("lst_trn_date"));
 							loan_rep.setReferenceNo(getUpdatedRemainderPrintNumber(conn,loan_type));
 							loan_rep.setDefaultedMths(default_months);
 							loan_rep.setInterestAccrued(rs.getDouble("int_rate"));
 							
 							return_values.add(loan_rep);
 						}
 					}
	 			}
	 		}
	 	}catch(SQLException exception){exception.printStackTrace();}
	 	finally{
	 		try{		    	
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	System.out.println("return_values" + return_values.capacity());
	 	return return_values;
	}
	
	synchronized int getUpdatedRemainderPrintNumber(Connection conn,String ac_type)
	{
		Statement stmt=null;
		ResultSet rs=null;
		int return_value=1;
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate("update Modules set lst_rct_no=lst_rct_no+1 where modulecode like '"+ac_type+"'");
			rs=stmt.executeQuery("select lst_rct_no from Modules where modulecode like '"+ac_type+"'");
			if(rs.next())
				return rs.getInt("lst_rct_no");
		}catch(SQLException exception){exception.printStackTrace();}
		return return_value;
	}
	
	public int setLoanHistory(Vector history_values,String user,String tml,String date_time) throws RecordsNotInsertedException
	{
		Connection conn=null;
	 	ResultSet rs=null,rs_stage=null;
	 	PreparedStatement pstmt=null;
	 	LoanReportObject loan_rep=null;
	 	int return_values=0;
		try{
			conn = getConnection();
			pstmt = conn.prepareStatement("insert into LoanHistory values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			for(int i=0;i<history_values.size();i++){
				loan_rep = (LoanReportObject) history_values.get(i);
				
				pstmt.setString(1,loan_rep.getAccType());
				pstmt.setInt(2,loan_rep.getAccNo());
				pstmt.setString(3,loan_rep.getShareType());
				pstmt.setInt(4,loan_rep.getShareNo());
				pstmt.setString(5,loan_rep.getActionParticulars());
				pstmt.setDouble(6,loan_rep.getProcessingCharges());
				pstmt.setDouble(7,loan_rep.getShareAmt());
				pstmt.setString(8,loan_rep.getName());
				pstmt.setInt(9,loan_rep.getStageNo());
				pstmt.setInt(10,loan_rep.getReferenceNo());
				pstmt.setString(11,loan_rep.getPrintedDate());
				pstmt.setString(12,loan_rep.getSanctionDate());
				pstmt.setDouble(13,loan_rep.getSanctionedAmount());
				pstmt.setDouble(14,loan_rep.getInstallmentAmount());
				pstmt.setInt(15,loan_rep.getNoInstallments());
				pstmt.setDouble(16,loan_rep.getCurrentInstallment());
				pstmt.setDouble(17,loan_rep.getIntOverDueAmt());
				pstmt.setString(18,loan_rep.getIntUptoDate());
				pstmt.setDouble(19,loan_rep.getPenalInterest());
				pstmt.setDouble(20,loan_rep.getOtherCharges());
				pstmt.setDouble(21,loan_rep.getLoanBalance());
				pstmt.setDouble(22,loan_rep.getPrnOverDueAmt());
				pstmt.setString(23,loan_rep.getPrintInd());
				pstmt.setInt(24,loan_rep.getDefaultedMths());
				pstmt.setString(25,loan_rep.getTransactiondate());
				pstmt.setString(26,user);
				pstmt.setString(27,tml);
				pstmt.setString(28,date_time);
				
				pstmt.addBatch();
			}
			int[] ret = pstmt.executeBatch();
			if(ret!=null && ret.length>0)
				return_values = ret.length;
			else
				throw new RecordsNotInsertedException();
		}catch(SQLException exception){
			exception.printStackTrace();
			throw new RecordsNotInsertedException();
		}
	 	finally{
	 		try{		    	
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	return return_values;
	}
	
	 public int getPriority(String tml) 
	 {
	 	int priority=0;
	 	Connection conn=null;
	 	try{
	 		conn=getConnection();
	 		Statement stmt=conn.createStatement();
	 		ResultSet rs=null;
				
	 		rs = stmt.executeQuery("select priority  from Priority where tml='"+tml+"'");
	 		if(rs.next() && rs.getString("priority")!=null)
	 			priority = rs.getInt("priority");
	 	}
	 	catch(Exception e){
	 		e.printStackTrace();
	 	}
	 	finally{
	 		try{                
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	return priority;
	 }
	 
	 public Object[][] getLoanAndShareDetails(String sh_type,int sh_no,String date)
	 {
	 	Object[][] return_values=null;
	 	Connection conn=null;
	 	int count;
	 	Statement stmt;
	 	ResultSet rs;
	 	try{
	 		conn = getConnection();
	 		stmt = conn.createStatement();
	 		rs = stmt.executeQuery("select concat(moduleabbr,'  (',moduledesc,')') as loan_type, lm.ac_type, lm.ac_no, disb_left,sanc_amt,lnk_shares , st.shareval as per_share_value , st.catname as category_name from LoanMaster as lm , Modules as mo ,ShareMaster as sm , ShareType as st where sh_no="+sh_no+" and sh_type='"+sh_type+"' and modulecode=lm.ac_type  and sm.cid=lm.cid and st.mem_cat = sm.mem_cat order by lm.ac_type,lm.ac_no");
	 		
	 		if(rs.next()) {
	 			rs.last();
	 			count = rs.getRow();
	 			return_values = new Object[count+1][6];
	 			double share_val = getCurrentDayShareBalance(sh_type,sh_no,date);
	 			return_values[0][0] = DoubleFormat.toString(rs.getDouble("per_share_value"));
	 			return_values[0][2] = DoubleFormat.toString(share_val / rs.getInt("per_share_value"));
	 			return_values[0][3] = rs.getString("category_name");
	 			rs.beforeFirst();
	 		}
	 		
	 		count = 1;
	 		while(rs.next()) {
	 			return_values[count][0] = rs.getString("loan_type");
	 			return_values[count][1] = Integer.valueOf(rs.getString("ac_no"));
	 			return_values[count][2] = DoubleFormat.toString(rs.getDouble("sanc_amt")); 
	 			return_values[count][3] = DoubleFormat.toString(rs.getDouble("disb_left"));
	 			return_values[count][4] = DoubleFormat.toString(getCurrentDayClosingBalance(rs.getString("ac_type"),rs.getInt("ac_no"),date));
	 			return_values[count][5] = DoubleFormat.toString(rs.getDouble("lnk_shares"));
	 			count++;
	 		}
	 	}
	 	catch(Exception e){
	 		e.printStackTrace();
	 	}
	 	finally{
	 		try{                
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	return return_values;
	 	
	 }
	 
	 public int deleteReverseLoanDetails(int voucher_number){
		 int ret_del=0;
		 Connection conn=null;
		 Statement stmt=null;
		 try{
			 System.out.println("In Bean of deleteReverseLoanDetails");
			 conn = getConnection();
			 stmt = conn.createStatement();
			 ret_del = stmt.executeUpdate("delete from LoanTransaction where ref_no="+voucher_number);
			 
		 }catch(Exception ee){ee.printStackTrace();}
		 
		 return ret_del;
	 }
	 
	 public String[][] getDirectorDetails(String date){
			System.out.println("**********Date for Director details**********" + date);
			Connection conn=null;
			ResultSet rs=null;
			Statement stmt=null;
			String str[][]=null;
			int i=0;
			try{
				conn=getConnection();
				stmt=conn.createStatement();
				date=Validations.convertYMD(date);
				rs = stmt.executeQuery("select director_code,d.cid, concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,''))as name ,from_date,to_date from DirectorMaster d,CustomerMaster cm   where d.cid=cm.cid  and  '"+date+"' between concat(right(from_date,4),'-',mid(from_date,locate('/',from_date)+1, (locate('/',from_date,4)-locate('/',from_date)-1)),'-',left(from_date,locate('/',from_date)-1)) and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1, (locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1))");
				rs.last();
				str=new String[rs.getRow()][5];
				rs.beforeFirst();
				while(rs.next()){
					str[i][0]=rs.getString("director_code");
					str[i][1]=rs.getString("d.cid");
					str[i][2]=rs.getString("name");
					str[i][3]=rs.getString("from_date");
					str[i][4]=rs.getString("to_date");
					i++;
				}
			}
			catch(Exception e){e.printStackTrace();}
			finally
			{
				try{ conn.close(); }
				catch(Exception ex){ex.printStackTrace();}
			}
			return str;
		}
	 public String[] getRelations(){
			Connection conn=null;
			ResultSet rs=null;
			Statement stmt=null;
			String str[]=null;
			int i=0;
			try{
				conn=getConnection();
				stmt=conn.createStatement();
				rs = stmt.executeQuery("select * from Relations");
				rs.last();
				str=new String[rs.getRow()];
				rs.beforeFirst();
				while(rs.next()){
					str[i]=rs.getString("type");
					i++;
				}
			}
			catch(Exception e){e.printStackTrace();}
			finally
			{
				try{ conn.close(); }
				catch(Exception ex){ex.printStackTrace();}
			}
			return str;
		}
	 public String[] getDirectorRelations(){
			Connection conn=null;
			ResultSet rs=null;
			Statement stmt=null;
			String str[]=null;
			int i=0;
			try{
				conn=getConnection();
				stmt=conn.createStatement();
				rs = stmt.executeQuery("select * from DirectorRelation");
				rs.last();
				str=new String[rs.getRow()];
				rs.beforeFirst();
				while(rs.next()){
					str[i]=rs.getString("relation_type");
					i++;
				}
			}
			catch(Exception e){e.printStackTrace();}
			finally
			{
				try{ conn.close(); }
				catch(Exception ex){ex.printStackTrace();}
			}
			return str;
		}
		public int getUpdateOtherCharges(LoanTransactionObject lto,int refno) {
			
			
			Connection conn=null;
			ResultSet rs=null;
			try {
				conn = getConnection();
				Statement stmt = conn.createStatement();
				if(lto.getAccType().startsWith("1010") || lto.getAccType().startsWith("1008")) {
					stmt.executeUpdate("delete from LoanRecoveryDetail where ac_type='"+lto.getAccType()+"' and ac_no="+lto.getAccNo()+" ");
					stmt.executeUpdate("delete from LoanTransaction where ac_type='"+lto.getAccType()+"' and ac_no="+lto.getAccNo()+"  and trn_type='O' and ref_no="+refno+" and ve_tml is null ");
					PreparedStatement ps=conn.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					ps.setString(1,lto.getAccType());
					ps.setInt(2,lto.getAccNo());
					ps.setInt(3,0);
					ps.setString(4,lto.getTransactionDate());
					ps.setString(5,"O");
					ps.setDouble(6,lto.getTransactionAmount());
					ps.setString(7,"T");
					ps.setString(8,lto.getTranSou());
					ps.setInt(9,refno);
					ps.setString(10,lto.getTranNarration());
					ps.setString(11,lto.getRecoveryDate());
					ps.setString(12,"D");
					ps.setString(13,getIntUpToDateOnCurrentDate(lto.getAccType(),lto.getAccNo(),lto.getTransactionDate()));
					ps.setDouble(14,0);
					ps.setDouble(15,0);
					ps.setDouble(16,0);
					ps.setDouble(17,0);
					ps.setDouble(18,getLastRecoveryExtraInt(lto.getAccType(),lto.getAccNo(),lto.getTransactionDate()));
					ps.setDouble(19,getCurrentDayClosingBalance(lto.getAccType(),lto.getAccNo(),lto.getTransactionDate()));
					ps.setString(20,lto.uv.getUserTml());
					ps.setString(21,lto.uv.getUserId());
					ps.setString(22,lto.uv.getUserDate());
					ps.setString(23,null);
					ps.setString(24,null);
					ps.setString(25,null);
					ps.executeUpdate();
					return 1;
				}
				
			}catch(Exception e) {e.printStackTrace();}
	         finally {
	        	 try {
	        	 conn.close();
	        	 }catch(Exception ev) {ev.printStackTrace();}
	         }
			return -1;
			
		}
	 public TreeMap pendingTrayList(String tray_no, String date) 
	 {
	 	Connection conn=null;
	 	Statement stmt=null;
 		ResultSet rs=null;
 		ResultSetMetaData rs_meta;
 		
 		Vector return_values = null;;
 		Vector col_names = null;
 		Vector rows = null;
 		
 		Hashtable hash_query = new Hashtable();
 		TreeMap return_table = new TreeMap();
 		
 		
	 	try{
	 		conn=getConnection();
	 		stmt=conn.createStatement();
	 		
	 		hash_query.put("1", "select cm.fname as name , mo.moduleabbr as ac_type, ac_no, ac_opendate, am.de_tml, am.de_user, am.de_date  from AccountMaster  as am, Modules as mo, CustomerMaster as cm where am.ve_tml is null and ac_opendate = '" + date + "' and am.cid = cm.cid and am.ac_type = mo.modulecode");
	 		hash_query.put("2", "select cm.fname as name , mo.moduleabbr as ac_type, am.ac_no, am.ac_opendate, am.de_tml, am.de_user, am.de_date from AccountTransaction as at, AccountMaster am,  Modules as mo, CustomerMaster as cm  where cl_bal = 0 and at.ac_type = am.ac_type and at.ac_no = am.ac_no  and  at.trn_date = '" + date + "' and am.ac_closedate is null  and am.cid = cm.cid and am.ac_type = mo.modulecode");
	 		//hash_query.put("3", "");
	 		hash_query.put("4", "select  cm.fname as name , mo.moduleabbr as ac_type, am.ac_no , cw.token_no, cw.trn_date, cw.trn_amt, cw.chq_no, cw.chq_dt, cw.book_no, cw.de_tml ,cw.de_user, cw.de_date  from ChequeWithdrawal as cw , AccountMaster am,  Modules as mo, CustomerMaster as cm  where cw.ve_tml is null  and cw.trn_date = '" + date + "' and cw.ac_type =am.ac_type and cw.ac_no = am.ac_no and am.cid = cm.cid and am.ac_type = mo.modulecode");
	 		hash_query.put("5", "select  mo.moduleabbr as ac_type, ac_no , name, trn_date, scroll_no, token_no, csh_amt, tml_no from DayCash as dc , Modules as mo where trn_date = '" + date + "'  and ac_type = modulecode  and token_no > 0 and scroll_no = 0 and tml_no is null");
	 		hash_query.put("6", "select cm.fname as name , mo.moduleabbr as ac_type, am.ac_no,book_no,fst_chq_no, lst_chq_no, no_leaf , cb.de_tml,cb.de_user, cb.de_date from ChequeBook as cb ,  AccountMaster am,  Modules as mo, CustomerMaster as cm  where cb.ve_tml is null and request_dt = '" + date + "' and am.ac_type = cb.ac_type and am.ac_no = cb.ac_no  and am.cid = cm.cid and am.ac_type = mo.modulecode");
	 		hash_query.put("7", "select mo.moduleabbr, po_acno,  po_purchaser_name, m1.moduleabbr, po_glcd , po_glname,po_sno , po_date ,po_amt , comm_amt , pm.de_tml , pm.de_user , pm.de_date from PayOrderMake as pm , Modules as mo ,Modules as m1 where ve_tml is null and po_date = '" + date + "' and pm.po_acty = mo.modulecode and po_gltype =m1.modulecode ");
	 		hash_query.put("8", "select payord_no , payord_dt, payee_nm ,trn_amt ,po_chq_no , valid_upto ,po_csh_dt , de_tml , de_user  de_date  from PayOrder where ve_tml is null and payord_dt = '" + date + "'");
	 		hash_query.put("9", "select cm.fname as name , mo.moduleabbr as ac_type, am.ac_no, ip.trn_date , ip.trn_amt, ip.int_rate,ip.de_tml,ip.de_user ,ip.de_date  from IntPay as ip ,AccountMaster am,  Modules as mo, CustomerMaster as cm  where trn_date = '" + date + "'  and post_ind = 'F' and ip.ac_type = am.ac_type and ip.ac_no = am.ac_no and am.cid = cm.cid and am.ac_type = mo.modulecode");
	 		hash_query.put("10", "select cm.fname as name , mo.moduleabbr as ac_type, ac_no, ac_opendate, am.de_tml, am.de_user, am.de_date  from ODCCMaster  as am, Modules as mo, CustomerMaster as cm where am.ve_tml is null and ac_opendate = '" + date + "' and am.cid = cm.cid and am.ac_type = mo.modulecode");
	 		
	 		hash_query.put("11", "select cm.fname as name , mo.moduleabbr as ac_type, am.ac_no, am.ac_opendate, am.sanc_dt , am.sanc_amt ,am.de_tml, am.de_user, am.de_date from  ODCCMaster am,  Modules as mo, CustomerMaster as cm  where am.ve_tml is not null  and   am.loan_sanc = 'Y' and am.sanc_ver = 'N' and am.cid = cm.cid and am.ac_type = mo.modulecode ");
	 		hash_query.put("12", "select fname,cid,de_user,de_tml,de_date  from CustomerMaster where ve_tml is null and de_date like '" + date + "%'");
	 		hash_query.put("13", "select mo.moduleabbr as ac_type, ac_no, name ,scroll_no ,trn_date , csh_amt , dc.de_user,dc.de_tml,dc.de_date from DayCash as dc , Modules as mo  where trn_type = 'R' and ve_tml is null and trn_date = '" + date + "' and dc.ac_type = mo.modulecode");
	 		hash_query.put("14", "select vch_no, vch_type name ,scroll_no ,trn_date , csh_amt , tml_no, dc.de_user,dc.de_tml,dc.de_date from DayCash as dc   where trn_type = 'R' and ve_tml is null and trn_date = '" + date + "' and vch_type = 'R'  and ac_type is null");
	 		hash_query.put("15", "select vch_no, vch_type name ,scroll_no ,trn_date , csh_amt , tml_no, dc.de_user,dc.de_tml,dc.de_date from DayCash as dc   where ac_type is null and ve_tml is null and attached = 'F'  and trn_date = '" + date + "'");
	 		hash_query.put("16", "select mo.moduleabbr as ac_type, ac_no, name ,scroll_no ,trn_date , csh_amt , dc.de_user,dc.de_tml,dc.de_date from DayCash as dc left join  Modules as mo  on dc.ac_type = mo.modulecode where trn_type = 'A' and oth_tml is not null and ve_tml is null and trn_date = '" + date + "'");
	 		hash_query.put("17", "select mo.moduleabbr as ac_type, ac_no, name ,scroll_no ,trn_date , csh_amt ,vch_no , vch_type, dc.de_user,dc.de_tml,dc.de_date from DayCash as dc left join  Modules as mo  on dc.ac_type = mo.modulecode where trn_type = 'P' and ac_type is not null and ac_no is not null and ve_tml is null and trn_date = '" + date + "'");
	 		hash_query.put("18", "select  name ,scroll_no ,trn_date , csh_amt ,vch_no , vch_type, dc.de_user,dc.de_tml,dc.de_date from DayCash as dc  where trn_type = 'P' and ac_type is  null and ac_no is  null and ve_tml is null and trn_date = '" + date + "'");
	 		//hash_query.put("19", "");
	 		//hash_query.put("20", "");

	 		hash_query.put("21", "select mo.moduleabbr,sm.ac_no,cm.fname as name ,st.trn_date, st.trn_amt, st.share_bal, st.de_user,st.de_tml,st.de_date from ShareTransaction as st , ShareMaster as sm , Modules as mo, CustomerMaster as cm where st.trn_type ='A' and st.trn_seq <> 1 and st.ve_tml is null and trn_date = '" + date + "' and st.ac_type =sm.ac_type and st.ac_no = sm.ac_no and sm.cid = cm.cid and sm.ac_type = mo.modulecode");
	 		hash_query.put("22", "select mo.moduleabbr,sm.ac_no,cm.fname as name ,st.trn_date, st.trn_amt, st.share_bal, st.de_user,st.de_tml,st.de_date from ShareTransaction as st , ShareMaster as sm , Modules as mo, CustomerMaster as cm where st.trn_type ='W'  and st.ve_tml is null and trn_date = '" + date + "' and st.ac_type =sm.ac_type and st.ac_no = sm.ac_no and sm.cid = cm.cid and sm.ac_type = mo.modulecode");
	 		hash_query.put("23", "select mo.moduleabbr,sm.ac_no,cm.fname as name, div_dt,div_amt,sd.pay_mode,cv_ty,cv_dt,cv_no from ShareDividend as sd,  ShareMaster as sm , Modules as mo, CustomerMaster as cm  where sd.ve_tml is null and sd.div_dt = '" + date + "' and sd.de_tml is not null and sd.ve_tml is null and sd.ac_type =sm.ac_type and sd.ac_no = sm.ac_no and sm.cid = cm.cid and sm.ac_type = mo.modulecode order by sd.pay_mode");
	 		hash_query.put("24", "select mo.moduleabbr,lm.ac_no,cm.fname as name , req_amt ,lm.de_user,lm.de_tml,lm.de_date from  LoanMaster as lm , Modules as mo, CustomerMaster as cm  where lm.ve_tml is null and lm.de_date like '" + date + "%' and lm.cid = cm.cid and lm.ac_type = mo.modulecode and lm.ac_type like '1010%'");
	 		hash_query.put("25", "select cm.fname as name , mo.moduleabbr as ac_type, am.ac_no,  am.sanc_date , am.sanc_amt ,am.de_tml, am.de_user, am.de_date from  LoanMaster am,  Modules as mo, CustomerMaster as cm  where am.ve_tml is not null  and   am.loan_sanc = 'Y' and am.sanc_ver = 'N' and am.cid = cm.cid and am.ac_type = mo.modulecode and am.ac_type like '1010%' and sanc_date like '" + date + "'");
	 		hash_query.put("26", "select  cm.fname as name , mo.moduleabbr as ac_type, lm.ac_no,  lm.sanc_date , lm.sanc_amt , lt.trn_amt, lt.trn_date, lt.de_tml, lt.de_user, lt.de_date from  LoanTransaction as lt, LoanMaster lm,  Modules as mo, CustomerMaster as cm    where  lt.ac_type like '1010%' and lt.trn_type like 'D' and lt.ve_tml is null and lt.trn_date like '" + date + "' and lm.cid = cm.cid and lm.ac_type = mo.modulecode  and lm.ac_type = lt.ac_type and lm.ac_no = lt.ac_no");
	 		hash_query.put("27", "select  cm.fname as name , mo.moduleabbr as ac_type, lm.ac_no, lt.trn_amt, lt.trn_date, lt.de_tml, lt.de_user, lt.de_date from  LoanTransaction as lt, LoanMaster lm,  Modules as mo, CustomerMaster as cm    where  lt.ac_type like '1010%' and lt.trn_type like 'O' and lt.ve_tml is null and lt.trn_date like '" + date + "' and lm.cid = cm.cid and lm.ac_type = mo.modulecode  and lm.ac_type = lt.ac_type and lm.ac_no = lt.ac_no");
	 		hash_query.put("28", "select  cm.fname as name , mo.moduleabbr as ac_type, lm.ac_no, lt.ref_no as vch_no, lt.trn_amt, pr_amt , (int_amt+extra_int) as int_amt, penal_amt , other_amt ,  pr_bal , lt.trn_date, lt.de_tml, lt.de_user, lt.de_date from  LoanTransaction as lt, LoanMaster lm,  Modules as mo, CustomerMaster as cm    where  lt.ac_type like '1010%' and trn_type like 'R' and cd_ind = 'C' and lt.ve_tml is null and lt.trn_date like '" + date + "' and lm.cid = cm.cid and lm.ac_type = mo.modulecode  and lm.ac_type = lt.ac_type and lm.ac_no = lt.ac_no");
	 		hash_query.put("29", "select  cm.fname as name , mo.moduleabbr as ac_type, lm.ac_no, ref_no as vch_no, abs(trn_amt) as trn_amt, abs(pr_amt) as pr_amt, abs(int_amt+extra_int) as int_amt, abs(penal_amt) as penal_amt , abs(other_amt) as other_amt,  pr_bal , lt.trn_date, lt.de_tml, lt.de_user, lt.de_date from  LoanTransaction as lt, LoanMaster lm,  Modules as mo, CustomerMaster as cm    where  lt.ac_type like '1010%' and trn_type like 'R' and cd_ind = 'D' and lt.ve_tml is null and lt.trn_date like '" + date + "' and lm.cid = cm.cid and lm.ac_type = mo.modulecode  and lm.ac_type = lt.ac_type and lm.ac_no = lt.ac_no");
	 		hash_query.put("30", "select mo.moduleabbr,lm.ac_no,cm.fname as name , req_amt ,lm.de_user,lm.de_tml,lm.de_date from  LoanMaster as lm , Modules as mo, CustomerMaster as cm  where lm.ve_tml is null and lm.de_date like '" + date + "%' and lm.cid = cm.cid and lm.ac_type = mo.modulecode and lm.ac_type like '1008%'");
	 		
	 		hash_query.put("31", "select  cm.fname as name , mo.moduleabbr as ac_type, lm.ac_no, lt.trn_amt, pr_amt , (int_amt+extra_int) as int_amt, penal_amt , other_amt ,  pr_bal , lt.trn_date, lt.de_tml, lt.de_user, lt.de_date from  LoanTransaction as lt, LoanMaster lm,  Modules as mo, CustomerMaster as cm    where  lt.ac_type like '1008%' and trn_type like 'R' and cd_ind = 'C' and lt.ve_tml is null and lt.trn_date like '" + date + "' and lm.cid = cm.cid and lm.ac_type = mo.modulecode  and lm.ac_type = lt.ac_type and lm.ac_no = lt.ac_no");
	 		hash_query.put("32", "select  cm.fname as name , mo.moduleabbr as ac_type, lm.ac_no,  lm.sanc_date , lm.sanc_amt , lt.trn_amt, lt.trn_date, lt.de_tml, lt.de_user, lt.de_date from  LoanTransaction as lt, LoanMaster lm,  Modules as mo, CustomerMaster as cm    where  lt.ac_type like '1008%' and lt.trn_type like 'D' and lt.ve_tml is null and lt.trn_date like '" + date + "' and lm.cid = cm.cid and lm.ac_type = mo.modulecode  and lm.ac_type = lt.ac_type and lm.ac_no = lt.ac_no");
	 		hash_query.put("33", "select mo.moduleabbr,am.ac_no,cm.fname as name ,appt_date, am.de_user,am.de_tml,am.de_date from AgentMaster as am,  Modules as mo, CustomerMaster as cm where am.ve_tml is null and am.appt_date  = '" + date + "' and am.cid = cm.cid and am.ac_type = mo.modulecode");
	 		hash_query.put("34", "select mo.moduleabbr,am.ac_no,cm.fname as name ,m2.moduleabbr as agt_type ,agt_no,open_date, am.de_user,am.de_tml,am.de_date from PygmyMaster as am,  Modules as mo, Modules as m2,  CustomerMaster as cm where am.ve_tml is null and am.open_date  = '" + date + "' and am.cid = cm.cid and am.ac_type = mo.modulecode and am.agt_type = m2.modulecode");
	 		hash_query.put("35", "select  mo.moduleabbr,pm.ac_no,cm.fname as name , dr.trn_date ,dr.trn_amt  ,dr.amt_paid ,dr.cl_bal ,dr.coll_date ,dr.de_tml  ,dr.de_user  ,dr.de_date from DailyRemittance  as dr, PygmyMaster as pm, Modules as mo, CustomerMaster as cm where dr.ve_tml is null and dr.trn_date = '" + date + "' and dr.ac_type = pm.ac_type and dr.ac_no = pm.ac_no and pm.cid = cm.cid and  pm.ac_type = mo.modulecode group by ref_no ");
	 		hash_query.put("36", "select  mo.moduleabbr,dr.ac_no,cm.fname as name , dr.trn_date ,dr.trn_amt  ,dr.amt_paid ,dr.cl_bal ,dr.de_tml  ,dr.de_user  ,dr.de_date from PygmyTransaction  as dr, PygmyMaster as pm, Modules as mo, CustomerMaster as cm where dr.ve_tml is  null and dr.trn_date = '" + date + "' and trn_type = 'P' and dr.ac_type = pm.ac_type and dr.ac_no = pm.ac_no and pm.cid = cm.cid and  pm.ac_type = mo.modulecode ");
	 		//hash_query.put("37", "");
	 		//hash_query.put("38", "");
	 		hash_query.put("39", "select ctrl_no, doc_sou , doc_dest , doc_bs , qdp_no , qdp_dt , mo.moduleabbr as  cr_ac_type , cr_ac_no , po_comm , dr_ac_no  payee_name , bank_cd , br_name , trn_amt, c.de_tml , c.de_user , c.de_dt_time from Cheque as c  left join  Modules as mo on c.cr_ac_type = mo.modulecode where clg_type = 'O' and doc_bs = 'S' and c.ve_tml is null and c.de_dt_time like '" + date + "%'  ");
	 		hash_query.put("40", "select ctrl_no, doc_sou , doc_dest , doc_bs , qdp_no , qdp_dt , mo.moduleabbr as  cr_ac_type , cr_ac_no , po_comm , dr_ac_no  payee_name , bank_cd , br_name , trn_amt, c.de_tml , c.de_user , c.de_dt_time from Cheque as c  left join  Modules as mo on c.cr_ac_type = mo.modulecode where clg_type = 'O' and doc_bs = 'S' and c.ve_tml is not null  and clg_date is null   ");
	 		
	 		hash_query.put("41", "select ctrl_no, doc_sou , doc_dest , doc_bs , qdp_no , qdp_dt , mo.moduleabbr as  cr_ac_type , cr_ac_no , po_comm , dr_ac_no  payee_name , bank_cd , br_name , trn_amt, c.de_tml , c.de_user , c.de_dt_time from Cheque as c  left join  Modules as mo on c.cr_ac_type = mo.modulecode where clg_type = 'O' and doc_bs = 'S' and c.ve_tml is not null  and clg_date is not null  and desp_ind = 'F'  and  concat(right(exp_clgdt,4),'-',mid(exp_clgdt,locate('/',exp_clgdt)+1,(locate('/',exp_clgdt,4)-locate('/',exp_clgdt)-1)),'-',left(exp_clgdt,locate('/',exp_clgdt)-1)) <= '" + Validations.convertYMD(date) + "'");
	 		hash_query.put("42", "select ctrl_no, doc_sou , doc_dest , doc_bs , qdp_no , qdp_dt , mo.moduleabbr as  cr_ac_type , cr_ac_no , po_comm , dr_ac_no  payee_name , bank_cd , br_name , trn_amt, c.de_tml , c.de_user , c.de_dt_time from Cheque as c  left join  Modules as mo on c.cr_ac_type = mo.modulecode where clg_type = 'O' and doc_bs = 'S' and c.ve_tml is not null  and clg_date is not null  and desp_ind = 'T' and post_ind ='F' and clg_date= '" + date + "' and  prev_ctrl_no = 0  ");
	 		hash_query.put("43", "select ctrl_no, doc_sou , doc_dest , doc_bs , qdp_no , qdp_dt , mo.moduleabbr as  cr_ac_type , cr_ac_no , po_comm , dr_ac_no  payee_name , bank_cd , br_name , trn_amt, c.de_tml , c.de_user , c.de_dt_time from Cheque as c  left join  Modules as mo on c.cr_ac_type = mo.modulecode where clg_type = 'O' and doc_bs = 'S' and c.ve_tml is null  and  mult_cr ='T'");
	 		hash_query.put("44", "select * from Acknowledge where ve_tml is null and reconciled = 'T' and ack_date = '" + date + "'");
	 		hash_query.put("45", "select * from Acknowledge where reconciled  ='F' and ack_date = '" + date + "'");
	 		hash_query.put("46", "select clg_date ,clg_no, ctrl_no, ack_no,  doc_sou , doc_dest , doc_bs , no_docs,doc_tot ,de_tml  ,de_user  ,de_dt_time from Cheque where clg_type = 'I' and ack_no is not null and clg_date = '" + date + "' and ve_tml is null");
	 		hash_query.put("47", "select ctrl_no ,c.ack_no,c.doc_sou , doc_dest , doc_bs , no_docs ,   doc_tot, moduleabbr ,cr_ac_no, bank_cd ,c.de_tml , c.de_user , c.de_dt_time from Cheque  as c , Acknowledge as ak, Modules as mo where cr_ac_type = modulecode and  doc_bs = 'B' and c.ve_tml is null and ak.ack_no = c.ack_no and ak.reconciled = 'T' ");
	 		hash_query.put("48", "select moduleabbr,ac_no,locker_ty,locker_no ,lm.de_tml  ,lm.de_user  ,lm.de_date from LockerMaster as lm, Modules as mo where ve_user is null and ve_tml is null and ve_date is null and ac_type = modulecode and allot_dt='" + date + "'");
	 		hash_query.put("49", "select moduleabbr,ac_no,locker_no ,lt.de_tml  ,lt.de_user  ,lt.de_date from LockerTransaction as lt, Modules as mo   where ve_user is null and ve_tml is null and ve_date is null  and time_in is null and time_out is null and rent_amt>0 and ac_type = modulecode and op_date='" + date + "'");
	 		hash_query.put("50", "select moduleabbr,ac_no,locker_no, lt.de_tml  ,lt.de_user  ,lt.de_date from LockerTransaction as lt, Modules as mo where time_in is not null and time_out is null and ve_tml is null and ve_user is null and ve_date is null and ac_type = modulecode and rent_amt is null  and op_date='" + date + "'");
	 		
	 		hash_query.put("51", "select moduleabbr,ac_no,locker_no , lt.de_tml  ,lt.de_user  ,lt.de_date from LockerTransaction as lt, Modules as mo where ac_type = modulecode and time_in is not null and time_out is not null and ve_tml is null and ve_user is null and ve_date is null  and rent_amt is null and op_date='" + date + "'");
	 		hash_query.put("52", "select moduleabbr,ac_no,locker_no ,lt.de_tml  ,lt.de_user  ,lt.de_date from LockerTransaction as lt, Modules as mo where ac_type = modulecode and  rent_by is null and trf_acty is null and trf_acno is null and rent_amt is null and rent_upto is not null and trn_seq is not null and trn_type is not null and cd_ind is null and time_in is null and time_out is null and ve_tml is null and ve_user is null and ve_date is null and op_date= '" + date + "'");
	 		hash_query.put("53", "select moduleabbr , ac_no , fname as name,  dep_amt, dm.de_tml , dm.de_user , dm.de_date from DepositMaster as dm, CustomerMaster as cm, Modules as mo  where ac_type = modulecode and dm.cid = cm.cid and  dm.ve_tml is  null and dm.dep_date = '" + date + "'");
	 		hash_query.put("54", "select moduleabbr , ac_no , fname as name,  dep_amt, dm.de_tml , dm.de_user , dm.de_date from DepositMaster as dm, CustomerMaster as cm, Modules as mo  where ac_type = modulecode and dm.cid = cm.cid and  rct_no = 0 and rct_prtd ='F' and dm.ac_type not like '1004%' and new_rct='T' and dm.de_date like '" + date + "%'  and dm.ve_tml is not null");
	 		//hash_query.put("55", "select moduleabbr , ac_no , fname as name,  dep_amt, dm.de_tml , dm.de_user , dm.de_date from DepositMaster as dm, CustomerMaster as cm, Modules as mo  where ac_type = modulecode and dm.cid = cm.cid and  dm.ve_tml is not null and dm.mat_date = '" + date + "'");
	 		//changed by geetha on 03/12/2007
	 		//hash_query.put("55" ,"select moduleabbr , ac_no , fname as name, dep_amt, dm.de_tml , dm.close_date,dm.de_user ,dm.ve_tml, dm.de_date from DepositMaster as dm, CustomerMaster as cm, Modules as mo  where ac_type = '1003001' and dm.cid = cm.cid and  dm.ve_tml is not null and close_date is null  and dm.mat_date = '" + date + "'");
	 		hash_query.put("55" ,"select mo.moduleabbr , dm.ac_no , cm.fname as name, dep_amt, dm.de_tml , dm.close_date,dm.de_user ,dm.ve_tml, dm.de_date from DepositMaster as dm, CustomerMaster as cm, Modules as mo  where dm.ac_type ='1003001' and mo.modulecode=dm.ac_type and dm.cid = cm.cid and  dm.ve_tml is not null and close_date is null  and dm.mat_date ='" + date + "'");
	 	


	 		hash_query.put("56", "select si_no, m1.moduleabbr as  fr_ac_ty, fr_ac_no ,m2.moduleabbr as  to_ac_ty, to_ac_no , amount , si.de_tml  ,si.de_user , si.de_dt_time from StdInst as si, Modules as m1, Modules as m2 where si.fr_ac_ty = m1.modulecode and si.to_ac_ty = m2.modulecode and ve_tml is null and si.de_dt_time like '" + date + "%'");
	 		hash_query.put("57", "select si_no, m1.moduleabbr as  fr_ac_ty, fr_ac_no ,m2.moduleabbr as  to_ac_ty, to_ac_no , amount , si.de_tml  ,si.de_user , si.de_dt_time from StdInst as si, Modules as m1, Modules as m2 where si.fr_ac_ty = m1.modulecode and si.to_ac_ty = m2.modulecode and del_ind ='T' and alt_de_tml is not null and alt_ve_tml is null and alt_de_dt_time like '" + date + "%'");
	 		hash_query.put("58", "select distinct vch_no, trn_date, vch_type ,sum(trn_amt) as trn_amt, moduleabbr as gl_sl_type,gl_sl_code , vi.de_tml  ,vi.de_user , vi.de_date from VoucherData as vi left join Modules as mo on  gl_sl_type = modulecode where  vi.de_date like '" + date + "%' and vch_type='T' and ve_user is null  and cd_ind = 'D' group by vch_no");
	 		hash_query.put("59", "select distinct vch_no,trn_date, vch_type ,sum(trn_amt) as trn_amt, moduleabbr as gl_sl_type,gl_sl_code , vi.de_tml  ,vi.de_user , vi.de_date from VoucherData as vi left join Modules as mo on  gl_sl_type = modulecode where  trn_date='" + date + "' and vch_type='P'and ve_user is null and cd_ind = 'D' group by vch_no order  by vch_no");
	 		hash_query.put("60", "select * from DailyStatus  where trn_dt = '" + Validations.convertYMD(date) + "'and cash_close ='T' and post_ind ='F'");
	 		
	 		// added by yashawanth 
	 		hash_query.put("61", "select * from Cheque where clg_type = 'I' and clg_date is null and desp_ind ='F' and post_ind = 'F' ");
	 		hash_query.put("62", "select * from Cheque where clg_type = 'I' and clg_date is null and desp_ind ='F' and post_ind = 'F' and  to_bounce = 'T'");
	 		hash_query.put("63", "select mo.moduleabbr,dm.ac_type,dm.ac_no,concat_ws(' ',fname,mname,lname) as name,ca.address,ca.city,ca.pin,ca.state,ca.country,dm.dep_date,dm.mat_date,dm.dep_amt,dm.mat_amt,dm.nom_no,dm.int_rate,dm.int_mode,dm.new_rct,dm.rct_no,dm.rct_prtd,dm.rct_sign,dm.rcvd_by,dm.dep_days,dm.rcvd_ac_no,dm.de_user,dm.ve_user from Modules mo,CustomerMaster cm,CustomerAddr ca,DepositMaster dm where dm.ac_type=mo.modulecode and dm.cid=cm.cid and cm.cid=ca.cid and   dm.rct_no=0  and dm.add_type = ca.addr_type and dm.ve_tml is not null and dm.dep_date = '" + date + "' order by dm.ac_no desc");

	 		// added by geetha
	 		hash_query.put("64", "select mo.moduleabbr as ac_type,dm.ac_no,cm.fname as name,dm.close_date,dt.de_user, dt.de_date,dt.de_tml from DepositTransaction as dt,DepositMaster as dm , Modules as mo,CustomerMaster as cm  where dm.ac_type=dt.ac_type and dt.ve_tml is null and dt.ve_user is null  and dt.trn_date=dm.close_date  and dm.ac_no=dt.ac_no and dm.cid=cm.cid and dm.ac_type=mo.modulecode and dm.close_date = '" + date + "'  group by dt.ac_type,dt.ac_no order by dm.ac_type, dm.ac_no");
	 		
	 		//added by Vinay
	 		
	 		hash_query.put("65","select distinct(ltrn.ac_no),mo.moduleabbr,cm.fname as name,lm.sanc_amt,lm.disb_left,ltrn.trn_type,ltrn.de_user,ltrn.de_tml,ltrn.de_date from LoanMaster as lm,LoanTransaction as ltrn ,Modules as mo,CustomerMaster as cm where trn_type='D' and ltrn.ve_tml is null and ltrn.trn_date like '" + date + "' and lm.cid=cm.cid and lm.ac_type = mo.modulecode and lm.ac_type=ltrn.ac_type and lm.ac_no=ltrn.ac_no and lm.ac_type like '1010%'");
	 
	 		hash_query.put("66","select ltn.ref_no,lm.ac_type,lm.ac_no,lm.sanc_date,lm.sanc_amt,lm.no_inst,ltn.trn_date,ltn.trn_amt,ltn.trn_narr from LoanMaster lm,LoanTransaction ltn where ltn.trn_date='" + date + "' and trn_type='O' and ltn.ve_tml is null and lm.ac_no=ltn.ac_no and lm.ac_type like '1010%'");
	 		
	 		//added by Geetha on 22/01/2008
	 		
	 		hash_query.put("67","select mo.moduleabbr , dm.ac_no , cm.fname as name, dep_amt, dm.de_tml , dm.close_date,dm.de_user ,dm.ve_tml, dm.de_date from DepositMaster as dm, CustomerMaster as cm, Modules as mo  where dm.ac_type like '1005%' and mo.modulecode=dm.ac_type and dm.cid = cm.cid and  dm.ve_tml is not null and close_date is null  and dm.mat_date = '"+ date +"'  ");
	 		
	 		hash_query.put("68","select ac_no,name ,csh_amt from DayCash where ac_type='1001001' and attached='F' and trn_date='"+ date +"'");
	 		
	 		hash_query.put("69","select lm.ac_type as LoanType,lm.ac_no as LoanNo,lm.td_ac_type as DepType,lm.td_ac_no as DepNo,ltn.trn_date as TranDate,ltn.trn_amt as Amount,ltn.pr_bal as PrincipalBal from LoanMaster lm,LoanTransaction ltn where lm.ac_no=ltn.ac_no and ltn.ve_tml is null and trn_date='"+ date +"' and lm.ac_type like '1008%'");
	 		
	 		if(tray_no != null) {
	 			String query = hash_query.get(tray_no).toString();
	 			hash_query.clear();
	 			hash_query.put(tray_no,query);
	 		}
	 		
	 		Object[] obj = null;
	 		String key = null;
	 		Set keys = hash_query.keySet();
	 		
	 		Iterator iterate = keys.iterator();
	 		
	 		while (iterate.hasNext()) {
	 			
	 			key = iterate.next().toString();
	 			try {
	 				rs = stmt.executeQuery(hash_query.get(key).toString()); 
			 		
			 		if(rs.next()) {

			 			return_values = new Vector();
			 			col_names = new Vector();
			 			rows = new Vector();
			 	 		
			 			rs_meta = rs.getMetaData();
				 		
				 		//file_logger.info(" the column values are " +  rs_meta.getColumnCount());
				 		
				 		for(int i = 0; i < rs_meta.getColumnCount(); i++) {
				 			col_names.add(rs_meta.getColumnName(i+1));
				 		}
				 		
				 		rs.beforeFirst();
				 		while(rs.next()) {
							obj = new Object[rs_meta.getColumnCount()];
							
							for(int i = 0; i < rs_meta.getColumnCount(); i++) {
					 			obj[i] = rs.getObject(i+1);
					 			//file_logger.info(" the " + i + " value is " + rs.getObject(i+1));
					 		}
							
							rows.add(obj);
						}
				 		
				 		return_values.add(col_names);
				 		return_values.add(rows);
				 		
				 		return_table.put(key, return_values);
			 		}
			 		
			 		if(return_table.size() == 0 && tray_no != null) {
			 			//this is put  to display  no records when there is no records to display
			 			return_values = new Vector();
			 			col_names = new Vector();
			 			obj = new Object[1];
			 			rows = new Vector();
			 			
			 			col_names.add("-------------- No Rows Found --------------");
			 			obj[0] = "-------------- No Rows Found --------------";
			 			rows.add(obj);
			 			
			 			return_values.add(col_names);
				 		return_values.add(rows);
				 		
				 		return_table.put(key, return_values);
			 		}
	 			}catch (Exception exe) {
	 				
	 			}
			} 
	 		
	 		
	 	} catch(Exception e){
	 		e.printStackTrace();
	 		sessionContext.setRollbackOnly();
	 		return null;
	 	}
	 	finally{
	 		try{                
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	return return_table;
	 }
	 public NPAObject[] getNPAAdminValues(String ac_type, int table_type) 
	 {
	 	Connection conn=null;
	 	Statement stmt=null;
 		ResultSet rs=null;
 		
 		NPAObject[] return_values = null;
 		String table_name = "NPAClassification";
 		
	 	try{
	 		conn=getConnection();
	 		
	 		stmt=conn.createStatement();
	 		
	 		if(table_type == 1) {
	 			table_name = "NPAClassification_180";
	 		} else if(table_type == 2) {
	 			table_name = "NPAClassification_90";
	 		}
	 		
	 		rs = stmt.executeQuery("select * from " + table_name + " where loan_type = '" + ac_type + "' order by asset_code"); 
	 		
	 		rs.last();
	 		return_values = new NPAObject[rs.getRow()];
	 		rs.beforeFirst();
	 		
	 		int count = 0;
	 		
	 		while(rs.next()) {
	 			
	 			return_values[count] = new NPAObject();
	 			
	 			return_values[count].setAcType(rs.getString("loan_type"));
	 			if(rs.getString("days_limit_fr") != null) {
	 				return_values[count].setDaysFrom(rs.getInt("days_limit_fr"));
	 			}
	 			if(rs.getString("days_limit_to") != null) {
	 				return_values[count].setDaysTo(rs.getInt("days_limit_to"));
	 			}
	 			if(rs.getString("mths_limit_fr") != null) {
	 				return_values[count].setMonthsFrom(rs.getInt("mths_limit_fr"));
	 			}
	 			if(rs.getString("mths_limit_to") != null) {
	 				return_values[count].setMonthsTo(rs.getInt("mths_limit_to"));
	 			}
	 			
	 			return_values[count].setAssetCode(rs.getInt("asset_code"));
	 			return_values[count].setAssetDesc(rs.getString("asset_desc"));
	 			return_values[count].setProvPerc(rs.getDouble("prov_perc"));
	 			return_values[count].setFromDate(rs.getString("from_date"));
	 			return_values[count].setToDate(rs.getString("to_date"));
	 			
	 			count++;
	 		} 
	 		
	 		if (count == 0) {
	 			return null;
	 		}
	 		
	 	} catch(Exception e){
	 		e.printStackTrace();
	 		sessionContext.setRollbackOnly();
	 		return return_values;
	 	}
	 	finally{
	 		try{                
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	return return_values;
	 }
	 
	 public int deleteReverseLoanDetails(int voucher_number,int ind) 
	 {

		ResultSet rs=null;
		LoanTransactionObject loan_trn = null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
		
			if(ind==1)
			stmt.executeUpdate("delete from LoanTransaction where ref_no=" +voucher_number  + " and trn_type='R' and cd_ind='D' and ve_tml is null");
			
			/*while(rs.next()) {
				loan_trn = new LoanTransactionObject();
				
				loan_trn.setAccType(rs.getString("ac_type"));
				loan_trn.setAccNo(rs.getInt("ac_no"));
				loan_trn.setTranType("R");
				loan_trn.setTranMode("T");
				loan_trn.setCdind("D");
				loan_trn.setReferenceNo(rs.getInt("ref_no"));
				loan_trn.setTranNarration(rs.getString("trn_narr"));
				loan_trn.setTransactionAmount(Math.abs(rs.getDouble("trn_amt")));
				loan_trn.setTranSou(rs.getString("trn_source"));
				loan_trn.setPrincipalPayable(Math.abs(rs.getDouble("pr_amt")));
				loan_trn.setInterestPayable(Math.abs(rs.getDouble("int_amt") + rs.getDouble("extra_int")));
				loan_trn.setPenaltyAmount(Math.abs(rs.getDouble("penal_amt")));
				loan_trn.setOtherAmount(Math.abs(rs.getDouble("other_amt")));
				loan_trn.uv.setUserDate(rs.getString("de_date"));
				loan_trn.uv.setUserId(rs.getString("de_user"));
				loan_trn.uv.setUserTml(rs.getString("de_tml"));
			}*/
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();return 0;}
		}
		return 1;
	 }
	 
	 public boolean setNPAAdminValues(NPAObject[] npa_values, int table_type) 
	 {
	 	Connection conn=null;
	 	Statement stmt=null;
 		ResultSet rs=null;
 		PreparedStatement pstmt = null;
 		boolean new_type = false;
 		
 		String table_name = "NPAClassification";
 		
	 	try{
	 		conn=getConnection();
	 		stmt=conn.createStatement();
	 		
	 		if(table_type == 1) {
	 			table_name = "NPAClassification_180";
	 		} else if(table_type == 2) {
	 			table_name = "NPAClassification_90";
	 		}
	 		
	 		pstmt = conn.prepareStatement("insert into "+ table_name + " values (?,?,?,?,?,?,?,?,?,?)");
	 		
	 		int row = stmt.executeUpdate("delete from " + table_name + " where loan_type = '" + npa_values[0].getAcType() + "'");
	 		
	 		if(row == 0) {
	 			new_type = true;
	 			rs = stmt.executeQuery("select * from " + table_name + " where loan_type = '" + npa_values[0].getAcType() + "'");
	 			if(rs.next()) {
	 				new_type = false;
	 			}
	 		}
	 		
	 		if(row > 0 || new_type ) {
	 			
	 			for(int i = 0; i < npa_values.length; i++) {
	 				pstmt.setString(1, npa_values[i].getAcType());
	 				
	 				if( npa_values[i].getDaysFrom() >= 0) {
	 					pstmt.setInt(2, npa_values[i].getDaysFrom());
	 				} else {
	 					pstmt.setString(2, null);
	 				}
	 				
	 				if( npa_values[i].getDaysTo() >= 0){
	 					pstmt.setInt(3, npa_values[i].getDaysTo());
	 				} else {
	 					pstmt.setString(3, null);
	 				}
	 				
	 				if( npa_values[i].getMonthsFrom() >= 0 ) {
	 					pstmt.setInt(4, npa_values[i].getMonthsFrom());
	 				} else {
	 					pstmt.setString(4, null);
	 				}
	 				if( npa_values[i].getMonthsTo() >= 0) {
	 					pstmt.setInt(5, npa_values[i].getMonthsTo());
	 				} else {
	 					pstmt.setString(5, null);
	 				}
	 				
	 				pstmt.setInt(6, npa_values[i].getAssetCode());
	 				pstmt.setString(7, npa_values[i].getAssetDesc());
	 				pstmt.setDouble(8, npa_values[i].getProvPerc());
	 				pstmt.setString(9, npa_values[i].getFromDate());
	 				pstmt.setString(10, npa_values[i].getToDate());
	 				
	 				pstmt.executeUpdate();
	 			}
	 		} else {
	 			sessionContext.setRollbackOnly();
	 			return false;
	 		}
	 		
	 	} catch(Exception e){
	 		e.printStackTrace();
	 		sessionContext.setRollbackOnly();
	 		return false;
	 	}
	 	finally{
	 		try{                
	 			conn.close();
	 		}catch(Exception exception){exception.printStackTrace();}
	 	}
	 	return true;
	 }
	 
	 
	 public LoanTransactionObject deleteOtherCharges(int tseq,String actype,int acno,int ind) 
	 {

		ResultSet rs=null;
		LoanTransactionObject loan_trn = null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			if(ind==1)
			stmt.executeUpdate("delete from LoanTransaction where ref_no=" +tseq + " and ac_type=" +actype+ " and ac_no=" +acno+ " and trn_type='O' and cd_ind='D' and ve_tml is null");
			
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return loan_trn;
	 }
	 
	//Vinay
		public PriorityMasterObject[] getPriorityDesc(){
			masterObject.loans.PriorityMasterObject priorobj[]=null;
			
			Connection con=null;
			try{
				con=getConnection();
				Statement stmt=con.createStatement();
				ResultSet rs = stmt.executeQuery("select * from PriorityMaster");
				rs.last();
				priorobj = new PriorityMasterObject[rs.getRow()];
				rs.beforeFirst();
				int i=0;
				
				while(rs.next())
				{
					priorobj[i]=new PriorityMasterObject();
					priorobj[i].setPrior_code(rs.getInt("pr_code"));	
					priorobj[i].setPrior_desc(rs.getString("pr_desc"));	
					i++;
					
				}
				
			}catch(Exception ex){ex.printStackTrace();}
			
			finally
			{
				try{
					con.close();
				}catch(Exception ex){ex.printStackTrace();}
			}
			return priorobj;
		}
		public SignatureInstructionObject[] getSignatureDetails(int accno,String type) 
		{
			SignatureInstructionObject am[]=null;
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs=null;
			try{
				conn=getConnection();	
				stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
				rs=stmt.executeQuery("select si.*,cm.photo,cm.sign from SignatureInstruction si,CustomerMaster cm where ac_no="+accno+" and ac_type='"+type+"' and cm.cid=si.cid");
				rs.last();
				if(rs.getRow() == 0)
					throw new Exception();
				am=new SignatureInstructionObject[rs.getRow()];
				rs.beforeFirst();
				int i=0;		
				
				while(rs.next())
				{
					am[i]=new SignatureInstructionObject();
					am[i].setSAccType(rs.getString("ac_type"));
					am[i].setSAccNo(rs.getInt("ac_no"));
					am[i].setCid(rs.getInt("cid"));
					am[i].setName(rs.getString("name"));
					am[i].setDesignation(rs.getString("desg"));
					
					
					Blob b=rs.getBlob("cm.photo");
					Blob s=rs.getBlob("cm.sign");
					if(b!=null)
						am[i].setPhoto(b.getBytes(1,(int)b.length()));
					if(s!=null)
						am[i].setSignature(s.getBytes(1,(int)s.length()));
					am[i].setMinLimit(rs.getInt("minlmt"));
					am[i].setMaxLimit(rs.getInt("maxlmt"));
					am[i].setTypeOfOperation(rs.getString("typeofopr"));
					i++;
					
				}
				
				return am;
				
				
			}catch(Exception exception){
				am = null;
			}
			finally{
				try{		    	
					conn.close();
				}catch(Exception exception){exception.printStackTrace();}
			}
			//System.out.println("Signature Inst"+ am.length);
			return am;
			
		}
		
		public SimpleMasterObject getAccDetails(String accno) throws CustomerNotFoundException
		{
			System.out.println("Step 3====== 3");
			Connection conn = null;
			SimpleMasterObject sm = null;
			try{         
				sm =new SimpleMasterObject();
				System.out.println("1...");
				conn = getConnection();
				System.out.println("this is step 4 ============= 4");
				Statement stmt=conn.createStatement();
				System.out.println("this is step 5 ============= 5");
				ResultSet rs=stmt.executeQuery("select * from CustomerMaster where cid="+accno+" ");
				if(rs.next())
				{
					System.out.println("this is step 6 ============= 6");
					sm.setAccType(rs.getString("fname"));
					System.out.println("the acc type is =======>"+rs.getString("fname"));
				}
				else
					throw new CustomerNotFoundException();
				System.out.println("After the result set ========= R S");
			}
			catch(SQLException e)
			{
				System.out.println("The error is ===========");
				e.printStackTrace();
			}
	        finally
	        {
	        	try
	        	{
	        		conn.close();
	        	}
	        	catch(Exception ex)
	        	{
	        		ex.printStackTrace();
	        	}
	        }
		return(sm);
		}

public int deleteLoanSanction(String acctype, int accno){
	 Connection conn=null;
	 Statement stmt = null;
	 int a=0;
		try
		{
			conn=getConnection();
			stmt = conn.createStatement();
			a = stmt.executeUpdate("update LoanMaster set sanc_date=null,sanc_amt=0.00,loan_sanc='N',no_inst=null,inst_amt=null	where ac_type = '"+acctype+"' and ac_no="+accno+" ");			
		}catch (Exception e) {
			e.printStackTrace();
		}
	 return a;
}

public int deleteLoanDisbursement(String acctype,int accno){
	 Connection conn = null;
	 Statement stmt = null;
	 
	 try{
		 conn = getConnection();
		 stmt = conn.createStatement();
		 stmt.executeUpdate("delete from LoanTransaction where ac_type='"+acctype+"' and ac_no="+accno+" ");
		 stmt.executeUpdate("update LoanMaster set int_upto_date=null,disb_left=null where ac_type='"+acctype+"' and ac_no="+accno+" ");
	 }catch (Exception e) {
		 e.printStackTrace(); 
	}
	 return 1;
}

public boolean isNominal(String ac_type, int ac_no) 
{
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;
	
	try{
		conn=getConnection();
		stmt=conn.createStatement();
		
		rs = stmt.executeQuery("select sm.mem_cat from LoanMaster as lm , ShareMaster as sm where lm.ac_type = '" + ac_type + "' and lm.ac_no = " + ac_no + " and lm.sh_type = sm.ac_type and lm.sh_no = sm.ac_no "); 
		
		if(rs.next() && rs.getInt("mem_cat") == 3) {
			return true;
		}
		
	} catch(Exception e){
		e.printStackTrace(); 
		return false;
	}
	finally{
		try{                
			conn.close();
		}catch(Exception exception){exception.printStackTrace();}
	}
	return false;
}
public String[] getQuaterlydates() throws RemoteException{
    String[] dates=null;
	Connection conn=null;
	ResultSet rs=null;
	int i=0,rows=0;
	try
	{
		conn=getConnection();
		Statement stmt=conn.createStatement();
		rs = stmt.executeQuery("select quaterly_date from LoanInterestQuaterlyDates");
		System.out.println("I am in getclosing balanxce");
		rs.last();
		rows=rs.getRow();
		dates=new String[rows];
		System.out.println("The rows are"+rows);
		rs.beforeFirst();
		while(rs.next())
		{
			
			dates[i]=rs.getString("quaterly_date");
			System.out.println("The quateraly date is"+dates[i]);
			i++;
		}
		return dates;
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
		
	}
	finally
	{
		try{                
			conn.close();
		}catch(Exception exception){exception.printStackTrace();}
	}
	return dates;
 
}
public LoanTransactionObject[] calc_int(String today_date)throws RemoteException{
	 System.out.println("The current date is"+today_date);
	 int result=0;
    Connection con=null,conn=null;
    Statement st=null,st6=null,st7=null;
    PreparedStatement pstmt=null;
    ResultSet rs=null, rs1=null,rs5=null,rs3=null,rs4=null,rs6=null,rs7=null;
    String quater_date=null,old_quater_date=null;
    String []trn_date=null;
    String ac_type[]=null;
    int ac_no[]=null;
    double amt=0.0,int_rate=0.0,int_amt=0.0,total_int_amt=0.0,qut_int_amt=0.0,newqut_int_amt=0.0,amt2=0.00, pr_balance=0.0;
    int no_of_days=0,trn_seq=0;
    int diff=0;
    int days=0,h=0,c=0,d=0;
    LoanTransactionObject[] ln_trn=null;
    GLTransObject trnobj=null;
    try{
   	 con=getConnection();
   	 conn=getConnection();
   	 st=con.createStatement();
   	 st6=con.createStatement();
   	 st7=con.createStatement();
   	 rs=st.executeQuery("select * from LoanInterestQuaterlyDates  where flag='0' order by quaterly_date");
   	 while(rs.next()){
   		 total_int_amt=0;
   		 quater_date=rs.getString("quaterly_date");
   		 old_quater_date=rs.getString("quaterly_date");
   		// old_quater_date=Validations.convertYMD(old_quater_date);
   		 System.out.println("The date is"+quater_date);
   		 System.out.println("The old $$$$$ quater date is"+old_quater_date);
   		 //quater_date=Validations.addDays(quater_date,1);
   	 }
   	 String conv_todate=Validations.convertYMD(today_date);
   	 String quater_conv=Validations.convertYMD(quater_date);
   	 //String quater_conv=Validations.addDays(newquater_date,1);
   	 System.out.println("The new qauter date is "+quater_conv);
   	System.out.println("today date"+conv_todate);
   	System.out.println("quater date"+quater_conv);
   	System.out.println("Now where to go???");
   	//rs1=st.executeQuery("select * from LoanMaster where close_date is null and npa_date is null and npa_stg is null and ac_no in(select ac_no from LoanTransaction where "+diff+">365 and trn_type!='R')order by ac_no");
   	//rs1=st.executeQuery("select * from LoanMaster where close_date is null and npa_date is null and npa_stg is null and lst_trn_date  between '"+quater_date+"' and '"+today_date+"' order by ac_no");
   	rs1=st.executeQuery("select lm.*,cm.fname from LoanMaster lm,CustomerMaster cm where (lm.close_date='null'||lm.close_date is null || lm.close_date='' )and lm.cid=cm.cid and  lm.npa_date is null order by lm.ac_no");
   	rs1.last();
   	int rows=rs1.getRow();
   	ln_trn=new LoanTransactionObject[rows];
   	//trnobj=new GLTransObject[rows];
       rs1.beforeFirst();
       ac_no=new int[rows];
       ac_type=new String[rows];
       
       int i=0;
       while(rs1.next()){
       	System.out.println("-------------in rs 1-----------");
       ac_type[i]=rs1.getString("ac_type");
       ac_no[i]=rs1.getInt("ac_no");
       ln_trn[i]=new LoanTransactionObject();
       ln_trn[i].setPrincipalBalance(rs1.getDouble("sanc_amt"));
       ln_trn[i].setName(rs1.getString("fname"));
		ln_trn[i].setAccNo(ac_no[i]);
		ln_trn[i].setAccType(ac_type[i]);
		ln_trn[i].uv.setUserTml(rs1.getString("de_tml"));
		//de_user  de_date               ve_tml  ve_user  ve_date
		ln_trn[i].uv.setUserId(rs1.getString("de_user"));
		ln_trn[i].uv.setUserDate(rs1.getString("de_date"));
		ln_trn[i].uv.setVerDate(rs1.getString("ve_date"));
		ln_trn[i].uv.setVerId(rs1.getString("ve_user"));
		ln_trn[i].uv.setVerTml(rs1.getString("ve_tml"));
		i++;
		
       }
       
       for(int j=0;j<rows;j++){
       	total_int_amt=0.0;
       	newqut_int_amt=0.0;
       	System.out.println("The total int amt initially is"+total_int_amt);
       	System.out.println("The qut_int_amt initially "+qut_int_amt);
       	System.out.println("The new amt initially"+newqut_int_amt);
   	  rs3=st.executeQuery("select * from LoanTransaction lt where lt.ac_no="+ac_no[j]+" and lt.ac_type='"+ac_type[j]+"' and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1,(locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))  between '"+quater_conv+"' and '"+conv_todate+"'  order by concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1,(locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))");
   	   rs3.last();
   	    int no_of_rows1=rs3.getRow();
   	    trn_date=new String[no_of_rows1];
   	
   	    System.out.println("The no of trns "+no_of_rows1);
   	    rs3.beforeFirst();
   	    int k=0;
   	    while(rs3!=null && rs3.next())
			{
				trn_date[k]=rs3.getString("trn_date");
				k++;
			}

   	//rs2=st.executeQuery("select * from LoanTransaction where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))  between '"+Validations.convertYMD(quater_date)+"' and '"+Validations.convertYMD(today_date)+"' and ac_no!=0 and ac_type is not null order by ac_no limit 10");
   /*	rs2=st.executeQuery("select lm.cid,lt.*,cm.fname from LoanTransaction lt,LoanMaster lm,CustomerMaster cm where lt.ac_no="+ac_no+"  and ac_type='"+ac_type+"' and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1,(locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))  between '"+Validations.convertYMD(quater_date)+"' and '"+Validations.convertYMD(today_date)+"' and lt.ac_no!=0 and lt.ac_type is not null and lt.ac_type=lm.ac_type and lt.ac_no=lm.ac_no and lm.cid=cm.cid order by lt.ac_no");
   	rs2.last();
   	int no_of_rows=rs2.getRow();
      	System.out.println("The no of rows are=============>"+no_of_rows);
   	LoanMasterObject[]ln_obj=new LoanMasterObject[no_of_rows];
   	rs2.beforeFirst();
   	int i=0;
   	
   	//ln_trn=new LoanTransactionObject[ac_no.length];
   	while(rs2.next()){
   	     	   
   	   
   	    ln_trn[i]=new LoanTransactionObject();
   		   ln_trn[i].setAccNo(Integer.parseInt(rs2.getString("ac_no")));
   		   ln_trn[i].setAccType(rs2.getString("ac_type"));
   		   ln_trn[i].setPrincipalBalance(rs2.getDouble("pr_bal"));
   		   ln_trn[i].setName(rs2.getString("fname"));
   	    i++;
   		
   	}
*/
   	System.out.println("sss222"+ac_no[j]+" "+ac_type[j]);
   	System.out.println("Quaterly date"+quater_conv);
   	System.out.println("Today date"+conv_todate);

   	rs4=st.executeQuery("select lt.trn_date,lt.pr_bal,lm.int_rate from LoanTransaction lt,LoanMaster lm where lt.ac_no="+ac_no[j]+" and lt.ac_type='"+ac_type[j]+"' and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1,(locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))  between '"+quater_conv+"' and '"+conv_todate+"' and lt.ac_no=lm.ac_no and lt.ac_type=lm.ac_type order by concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1,(locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))limit 1");
   	System.out.println("The result set 4 is"+rs4);
   	while(rs4.next()){
   		qut_int_amt=0.0;
   		String date2=rs4.getString("trn_date");
   		System.out.println("The date 2 is"+date2);
   		String date1=rs4.getString("trn_date");
   		System.out.println("the date 1 is"+date1);
   		System.out.println("ac_type===>"+ac_type[j]+"===>ac_no"+ac_no[j]);
   	    amt=getClosingBalance(ac_type[j],ac_no[j],quater_date);
			int_rate=getIntrest_Rate(ac_type[j], ac_no[j],date1);
			System.out.println("The pr_bal in R4 is--->"+amt);
			System.out.println("date 1="+date1+"===>quater date "+quater_date);
		    no_of_days=commonlocal.getDaysFromTwoDate(quater_date,date1);
		    System.out.println("The no of days is=in quater==>"+no_of_days);
		   qut_int_amt=(amt*int_rate*no_of_days)/36500;
		   
		   System.out.println("The only quater int amt is====>"+qut_int_amt);
   	  
   	}
   	//for(int j=0;j<ac_no.length;j++){
   		//total_int_amt=0;
/*	    	  rs3=st.executeQuery("select lt.trn_date,lt.pr_bal,lm.int_rate from LoanTransaction lt,LoanMaster lm where lt.ac_no="+ac_no+" and lt.ac_type='"+ac_type+"' and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1,(locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))  between '"+quater_conv+"' and '"+conv_todate+"' and lt.ac_no=lm.ac_no and lt.ac_type=lm.ac_type order by concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1,(locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))");
   	   rs3.last();
   	    int no_of_rows1=rs3.getRow();
   	    trn_date=new String[no_of_rows1];
   	
   	    System.out.println("The no of trns "+no_of_rows1);
   	    rs3.beforeFirst();
   	    int k=0;
   	    while(rs3!=null && rs3.next())
			{
				trn_date[k]=rs3.getString("trn_date");
				k++;
			}
*/	    	   for(int m=1;m<trn_date.length;m++){
				//total_int_amt=0.0;
   		    	System.out.println("tra_date k .............suraj"+trn_date[m]);
   		    	System.out.println("tra_date k-1.............suraj"+trn_date[m-1]);
		       System.out.println("***************START*********************");
		       System.out.println("the ac_noooooo is..."+ac_no[j]);
		       System.out.println("the ac_typeeeee is..."+ac_type[j]);
				amt=getClosingBalance(ac_type[j],ac_no[j],trn_date[m-1]);
				int_rate=getIntrest_Rate(ac_type[j], ac_no[j], trn_date[m-1]);
				System.out.println("The pr_bal is"+amt);
   		   no_of_days=commonlocal.getDaysFromTwoDate(trn_date[m-1],trn_date[m]);
   		   System.out.println("the int rate is"+int_rate);
   		   
   		   System.out.println("The no of days is"+no_of_days);
   		   int_amt=(amt*int_rate*no_of_days)/36500;
   		   System.out.println("the interest amt is=====>"+int_amt);
   		   total_int_amt+=int_amt;
   		   
   		   System.out.println("The total interest amount is=======>"+total_int_amt);
   		   System.out.println("********1234566********EN********************");
   		   //ln_trn[m].setInterestPayable(Math.round(total_int_amt));
   		 
        	   }
	rs5=st.executeQuery("select lt.trn_date,lt.pr_bal,lt.trn_seq,lm.int_rate from LoanTransaction lt,LoanMaster lm where lt.ac_no="+ac_no[j]+" and lt.ac_type='"+ac_type[j]+"' and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1,(locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))  between '"+quater_conv+"' and '"+conv_todate+"' and lt.ac_no=lm.ac_no and lt.ac_type=lm.ac_type order by concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1,(locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1)) desc limit 1");
	System.out.println("The result set 5 is"+rs5);
	while(rs5.next()){
		newqut_int_amt=0.0;
		String date1=rs5.getString("trn_date");
       trn_seq=Integer.parseInt(rs5.getString("trn_seq"));
       trn_seq+=1;
		System.out.println("ac_type===>"+ac_type[j]+"===>ac_no"+ac_no[j]);
		amt2=getClosingBalance(ac_type[j],ac_no[j],date1);
		int_rate=getIntrest_Rate(ac_type[j], ac_no[j],date1);
		System.out.println("The pr_bal is"+amt2);
		System.out.println("date 1="+date1+"===>today date "+today_date);
		no_of_days=commonlocal.getDaysFromTwoDate(date1,today_date);
		System.out.println("The no of days is=in quater==>"+no_of_days);
		newqut_int_amt=(amt2*int_rate*no_of_days)/36500;
		System.out.println("The only quater int amt is====>"+newqut_int_amt);

	}  

   	   System.out.println("The total_int_amt is"+total_int_amt);
   	   System.out.println("The qut_int_amt is"+qut_int_amt);
   	   System.out.println("The newqut_int_amt is"+newqut_int_amt);

   	   total_int_amt=total_int_amt+qut_int_amt+newqut_int_amt;
   	   ln_trn[j].setInterestPayable(Math.round(total_int_amt));
   	   System.out.println("*******final*********"+total_int_amt);
   	   System.out.println("The loan transaction is===========================================>"+ln_trn);
       
  		pstmt = conn.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
       System.out.println("Inside the prepared statement");
		pstmt.setString(1,ln_trn[j].getAccType());
		pstmt.setInt(2,ln_trn[j].getAccNo());
		pstmt.setInt(3,trn_seq);
		pstmt.setString(4,today_date);
		pstmt.setString(5,"I");
		pstmt.setDouble(6,ln_trn[j].getInterestPayable());
		pstmt.setString(7,"T");
		pstmt.setString(8,ln_trn[j].uv.getUserTml());
		pstmt.setInt(9,0);
		pstmt.setString(10,"Interest");
		pstmt.setString(11,null);
		pstmt.setString(12,"D");
		pstmt.setString(13,null);
		pstmt.setDouble(14,0.00);
		/* Added by Vinay for Extra Int
		pstmt.setDouble(15,amount_available)*/
		pstmt.setDouble(15,0.00);
		pstmt.setDouble(16,0.00);
		pstmt.setDouble(17,0.00);
		pstmt.setDouble(18,0.00);
		pr_balance=ln_trn[j].getInterestPayable()+amt2;
		pstmt.setDouble(19,pr_balance);
		pstmt.setString(20,ln_trn[j].uv.getUserTml());
		pstmt.setString(21,ln_trn[j].uv.getUserId());
		pstmt.setString(22,ln_trn[j].uv.getUserDate());
		pstmt.setString(23,ln_trn[j].uv.getVerTml());
		pstmt.setString(24,ln_trn[j].uv.getVerId());
		pstmt.setString(25,ln_trn[j].uv.getVerDate());
		 
		int update_tran=pstmt.executeUpdate();
		
			
		
		//**********************GL Entries**********************************//
		/**
		 * GLTRansaction entries.......
		 * debit to account payable gl
		 */
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		rs6=st6.executeQuery("select gk.gl_type,gp.gl_code,gp.mult_by from GLPost gp,GLKeyParam gk where gk.ac_type="+ac_type[j]+" and gp.ac_type="+ac_type[j]+" and gk.gl_code=gp.gl_code and trn_type='D' and cr_dr='D' and code=1");
		System.out.println("&&&&&&&&&&&&&&&&&"+rs6);
		
		if(rs6.next())
		
		{
			
			System.out.println("Cr to Acc payable GL...");
			cremote=home.create();
			//GLTransObject[] trnobj=new GLTransObject[ac_no.length];
			trnobj=new GLTransObject();
			trnobj.setTrnDate(today_date);
			trnobj.setGLType(rs6.getString("gl_type"));
			trnobj.setGLCode(rs6.getString("gl_code"));
			trnobj.setTrnMode("T");
			trnobj.setAmount(ln_trn[j].getInterestPayable());
			trnobj.setCdind("D");
			trnobj.setAccType(ln_trn[j].getAccType());
			trnobj.setAccNo(String.valueOf(ln_trn[j].getAccNo()));
			trnobj.setTrnType("I");
			trnobj.setRefNo(0);
			trnobj.setTrnSeq(trn_seq);
			trnobj.setVtml(ln_trn[j].uv.getVerTml());
			trnobj.setVid(ln_trn[j].uv.getVerId());	
			trnobj.setVDate(ln_trn[j].uv.getVerDate());
			int res6=cremote.storeGLTransaction(trnobj);
			c++;
			System.out.println("The res6 is---------------------> "+res6);
			System.out.println("The GL1 is---------------------> "+c);
			
			//if(commonlocal.storeGLTransaction(trnobj[j])==1)
	
		}	
		//dptype = dptype+2;
		/**
		 * Credit Income GL
		 */				
		/**
		 * GLTRansaction entries.......
		 * credit to interest on loans and advances
		 */
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!11111111!!!!!!!!");
		rs7=st7.executeQuery("select gk.gl_type,gp.gl_code,gp.mult_by from GLPost gp,GLKeyParam gk where gk.ac_type="+ac_type[j]+" and gp.ac_type="+ac_type[j]+" and gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='C' and code=2");
		System.out.println("&&&&&&&&&&&&&&&11111&&&");
		while( rs7.next())
		{System.out.println("inside rs7 while loop");
			
			cremote=home.create();
			//GLTransObject[] trnobj=new GLTransObject[ac_no.length];
			trnobj=new GLTransObject();
			trnobj.setTrnDate(today_date);
			trnobj.setGLType(rs7.getString("gl_type"));
			trnobj.setGLCode(rs7.getString("gl_code"));
			trnobj.setTrnMode("T");
			trnobj.setAmount(ln_trn[j].getInterestPayable());
			trnobj.setCdind("C");
			trnobj.setAccType(ln_trn[j].getAccType());
			trnobj.setAccNo(String.valueOf(ln_trn[j].getAccNo()));
			trnobj.setTrnType("I");
			trnobj.setRefNo(0);
			trnobj.setTrnSeq(trn_seq);
			trnobj.setVtml(ln_trn[j].uv.getVerTml());
			trnobj.setVid(ln_trn[j].uv.getVerId());	
			trnobj.setVDate(ln_trn[j].uv.getVerDate());
			int res7=cremote.storeGLTransaction(trnobj);
			System.out.println("The GL2 is---------------------> "+d);
			
			//if(commonlocal.storeGLTransaction(trnobj[j])==1)
		//	{
				System.out.println("inserted "+res7);	
		//*********************************End GL Transaction*************************************//
			//}
       //int res=st.executeUpdate("update LoanInterestQuaterlyDates set flag=1 where quaterly_date='"+old_quater_date+"' ");
       //int res1=st.executeUpdate("insert into LoanInterestQuaterlyDates values('"+today_date+"',0)");
       
                   
       
   	 // return ln_trn;
    
				}
		
					System.out.println("The int value is"+update_tran);
					h++;
      }
       System.out.println("The h value is------------------------>"+h);
       int res=st.executeUpdate("update LoanInterestQuaterlyDates set flag=1 where quaterly_date='"+old_quater_date+"' ");
       int res1=st.executeUpdate("insert into LoanInterestQuaterlyDates values('"+today_date+"',0)");
       
       return ln_trn;
       }catch(Exception e){
   	 e.printStackTrace();
    }
    finally{
   	 try{
   		 con.close();
   		 conn.close();
   	 }
   	 catch(Exception e){
   		 e.printStackTrace();
   	 }
    }
	 return ln_trn;
}
public double getClosingBalance(String ac_type,int ac_no,String date) 
{
	double cl_bal=0.0;
	Connection conn=null;
	ResultSet rs=null;
	try
	{
		conn=getConnection();
		Statement stmt=conn.createStatement();
		rs = stmt.executeQuery("select pr_bal from LoanTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))='"+Validations.convertYMD(date)+"' order by trn_seq  desc limit 1");
		System.out.println("I am in getclosing balanxce");
		if(rs.next() && rs.getString("pr_bal")!=null)
		{
			cl_bal=rs.getDouble("pr_bal");
			
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
		
	}
	finally
	{
		try{                
			conn.close();
		}catch(Exception exception){exception.printStackTrace();}
	}
	
	return cl_bal;
}
public double getIntrest_Rate(String ac_type,int ac_no,String date) 
{
	double cl_bal=0.0;
	Connection conn=null;
	ResultSet rs=null;
	try
	{
		conn=getConnection();
		Statement stmt=conn.createStatement();
		rs = stmt.executeQuery("select lm.int_rate,lt.ac_no,lt.ac_type from LoanTransaction lt, LoanMaster lm where lt.ac_type='"+ac_type+"' and lt.ac_no="+ac_no+" and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1, (locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))='"+Validations.convertYMD(date)+"' and lt.ac_no=lm.ac_no and lt.ac_type=lm.ac_type order by lt.trn_seq  desc limit 1");
		System.out.println("I am in getclosing balanxce");
		if(rs.next())
		{
			cl_bal=rs.getDouble("int_rate");
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
		
	}
	finally
	{
		try{                
			conn.close();
		}catch(Exception exception){exception.printStackTrace();}
	}
	
	return cl_bal;
}
public LoanTransactionObject[] getQuaterlyIntDetails(String date)throws RemoteException{
	 Connection con=null,conn=null;
    Statement st=null,st6=null,st7=null;
    LoanTransactionObject[] ln_trn=null;
    ResultSet rs=null;
    int rows=0,i=0;
    try{
   	con=getConnection();
   	st=con.createStatement();
   	rs=st.executeQuery("select lt.*,lm.cid,cm.fname from LoanTransaction lt, LoanMaster lm, CustomerMaster cm where lt.trn_date='"+date+"' and lm.ac_no=lt.ac_no and lt.ac_type=lm.ac_type and lm.cid=cm.cid");
   	rs.last();
   	rows=rs.getRow();
   	ln_trn=new LoanTransactionObject[rows];
   	System.out.println("The no of rows in loan trn is"+rows);
   	rs.beforeFirst();
   	
   	while(rs.next()){
   		ln_trn[i]=new LoanTransactionObject();
   		ln_trn[i].setAccNo(rs.getInt("ac_no"));
   		ln_trn[i].setAccType(rs.getString("ac_type"));
   		ln_trn[i].setInterestPayable(rs.getDouble("trn_amt"));
   		ln_trn[i].setPrincipalBalance(rs.getDouble("pr_bal"));
   		ln_trn[i].setName(rs.getString("fname"));
   		i++;
   		System.out.println("The i values is"+i);
   	}
   return ln_trn;
    }catch (Exception e) {
			
		}
   	 finally{
   		 try{
   			 con.close();
   		 }
   		 catch (Exception e1) {
				e1.printStackTrace();
			}
   	 }
    
    return ln_trn;
    
}

public LoanTransactionObject getLoanTran(String acctype,int accno){
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;
	LoanTransactionObject loantrn=null;
	try{
		conn=getConnection();
		stmt=conn.createStatement();
		rs=stmt.executeQuery("select trn_mode,trn_narr,ve_tml,ve_user from LoanTransaction where ac_type='"+acctype+"' and ac_no='"+accno+"' and trn_type='D' ");
		if(rs.next()){
			loantrn=new LoanTransactionObject();
			loantrn.setTranMode(rs.getString("trn_mode"));
			loantrn.setTranNarration(rs.getString("trn_narr"));
			loantrn.uv.setVerTml(rs.getString("ve_tml"));
			loantrn.uv.setVerId(rs.getString("ve_user"));
			System.out.println("*******************Trn Narr"+rs.getString("trn_narr"));
			
		}
		
	}catch(Exception ee){
		ee.printStackTrace();
	}
	finally{
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return loantrn;
}
public int updateLoanMaster(LoanMasterObject lnobj,String actype,int acno,String datetime)
{
	Connection conn=null;
	ResultSet res=null;
    PreparedStatement pstmt=null;
    Statement stmt=null,stmt_ins=null;
    System.out.println("+++++++++++++++In The Bean+++++++++++++");
    System.out.println("++++++++++++++++actype++++++++++++++++++++" + actype);
    System.out.println("++++++++++++++++acno++++++++++++++++++++" + acno);
    try{
   	 conn=getConnection();
   	 stmt_ins=conn.createStatement();
   	 stmt=conn.createStatement();   
   	 System.out.println("---------1---------------");
   	 stmt_ins.executeUpdate("insert into LoanMasterLog select * from LoanMaster where ac_type='"+actype+"' and ac_no="+acno+" ");
   	System.out.println("---------2---------------");
   	 stmt.executeUpdate("delete from LoanMaster where ac_type='"+actype+"' and ac_no="+acno+" ");
   	System.out.println("---------3--------------");
   	 pstmt=conn.prepareStatement("insert into LoanMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
   	 	pstmt.setString(1,String.valueOf(lnobj.getModuleCode()));//lnactype
			pstmt.setInt(2,lnobj.getAccNo());//lnacno
			pstmt.setInt(3,lnobj.getNoOfCoBorrowers());//no jointholders	
			file_logger.info("@@@@@@@@@@@@@@@@@@@@@@="+lnobj.getNoOfCoBorrowers());
			pstmt.setInt(4,lnobj.getNoOfSurities());
			pstmt.setInt(5,lnobj.getCustomerId());
			pstmt.setInt(6,lnobj.getMailingAddress());
			
			pstmt.setInt(7,lnobj.getApplicationSrlNo());//ln appn srl number
			pstmt.setString(8,lnobj.getApplicationDate());//ln appn date
			pstmt.setDouble(9,lnobj.getRequiredAmount());//req amt
			pstmt.setInt(10,lnobj.getShareAccNo());//lnee lf no	
			pstmt.setString(11,lnobj.getShareAccType());//lnee lf ac type
			
			pstmt.setInt(12,lnobj.getPurposeCode());//purpose code
			pstmt.setString(13,null);//nom no
			pstmt.setString(14,lnobj.getDepositAccType());//td actype
			pstmt.setInt(15,lnobj.getDepositAccNo());//td acno		
			pstmt.setInt(16,lnobj.getInterestType());
			pstmt.setInt(17,lnobj.getInterestRateType());
			pstmt.setDouble(18,lnobj.getInterestRate());
			pstmt.setInt(19,lnobj.getPrior());//psect _cd
			pstmt.setString(20,"N");//weeker section
			pstmt.setString(21,String.valueOf(lnobj.getSexInd()));//sex
			pstmt.setString(22,lnobj.getRelative());//relation
			pstmt.setString(23,String.valueOf(lnobj.getDirectorCode()));//dir code
			pstmt.setString(24,null);//conv dt
			file_logger.info("1............");
			pstmt.setInt(25,lnobj.getHolidayPeriod());//holdiday mth		
			pstmt.setString(26,lnobj.getSanctionDate());
			pstmt.setDouble(27,lnobj.getSanctionedAmount());//sanc amt
			pstmt.setString(28,"Y");//loan sanctioned
			
			
			pstmt.setString(29,"Y");// sanc verified		
			pstmt.setInt(30,lnobj.getNoOfInstallments());//no insta
			pstmt.setDouble(31,lnobj.getInstallmentAmt());//insta amt
			
			pstmt.setString(32,lnobj.getInterestUpto());//int uptodt
			pstmt.setString(33,lnobj.getLastTrndt());//lst trn dt
			pstmt.setInt(34,lnobj.getLastTrnSeq());//lst trn seq		
			pstmt.setString(35,"N");//default ind
			pstmt.setString(36,lnobj.getClosedt());//close dt
			file_logger.info("===================================="+lnobj.getPayMode());
			file_logger.info("===================================="+lnobj.getPaymentAccno());
			file_logger.info("===================================="+lnobj.getPaymentAcctype());
			if(lnobj.getPayMode().equalsIgnoreCase("Cash")){
				file_logger.info("in cash"+"C");
				pstmt.setString(37,"C");//paymode
			    pstmt.setString(38,null);//pay actype
			    pstmt.setInt(39,0);//pay acno
			}
			else if(lnobj.getPayMode().equalsIgnoreCase("PayOrder")){
				pstmt.setString(37,"P");//paymode
			    pstmt.setString(38,null);//pay actype
			    pstmt.setInt(39,0);//pay acno
			}
			else if(lnobj.getPayMode().equalsIgnoreCase("Transfer")){
				pstmt.setString(37,"T");//paymode
			    pstmt.setString(38,lnobj.getPaymentAcctype());//pay actype
			    pstmt.setInt(39,lnobj.getPaymentAccno());//pay acno
			}
			
			pstmt.setString(40,null);//remd no
			pstmt.setString(41,null);//remd dt
			pstmt.setDouble(42,(lnobj.getDisbursementLeft()));
			pstmt.setString(43,null);//ldgprtdt
			pstmt.setString(44,null);//npa
			pstmt.setString(45,null);//npastg
			
			pstmt.setString(46,lnobj.uv.getUserTml());//de tml
			pstmt.setString(47,lnobj.uv.getUserId());//de user
			pstmt.setString(48,datetime);//de date time
			pstmt.setString(49,lnobj.uv.getUserTml());//ve tml
			pstmt.setString(50,lnobj.uv.getUserId());//ve user
			pstmt.setString(51,datetime);//ve date time
			
			file_logger.info("+++++++++++++++++++check point for store LoanMaster2++++++++++++++++++");
			pstmt.executeUpdate();
		    if(lnobj.getVehicleDet()!=null){
		     file_logger.info("+++++++++++++++++++check point for store VehicleMaster++++++++++++++++++");
   		 VehicleObject vobj=lnobj.getVehicleDet();
   		 stmt.executeUpdate("insert into VehicleMasterLog select * from VehicleMaster where ac_type='"+actype+"' and ac_no="+acno+" ");
   		 stmt.executeUpdate("delete from VehicleMaster where ac_type='"+actype+"' and ac_no="+acno+" ");
       	 pstmt = conn.prepareStatement("insert into VehicleMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
       	 pstmt.setString(1,String.valueOf(lnobj.getModuleCode()));
       	 pstmt.setInt(2,lnobj.getAccNo());
       	 pstmt.setString(3,vobj.getVehicleMake());
			 pstmt.setDouble(4,vobj.getVehicleCost());
			 pstmt.setString(5,vobj.getAddressDealer());
			 pstmt.setString(6,vobj.getLicenceNo());
			 pstmt.setString(7,vobj.getLicenceValidity());
			 pstmt.setString(8,vobj.getVehicleType());
			 pstmt.setString(9,vobj.getPermitNo());	
			 pstmt.setString(10,vobj.getPermitValidity());
			 pstmt.setString(11,vobj.getVehicleFor());
			 pstmt.setString(12,vobj.getArea());
			 pstmt.setString(13,vobj.getAddressParking());
			 pstmt.setString(14,vobj.getOtherDet());
			 pstmt.executeUpdate();
		 } // Vehicle
   	 
   	 if(lnobj.getGoldDet()!=null){
   		 file_logger.info("+++++++++++++++++++check point for store GoldDetails++++++++++++++++++");
   		 GoldObject gobj=lnobj.getGoldDet();
   		 stmt.executeUpdate("insert into GoldDetMasterLog   select * from GoldDetMaster where ac_type='"+actype+"' and ac_no="+acno+" ");
   		 stmt.executeUpdate("delete from GoldDetMaster where ac_type='"+actype+"' and ac_no="+acno+" ");
       	 pstmt = conn.prepareStatement("insert into GoldDetMaster values(?,?,?,?,?,?,?,?,?,?)");
       	 pstmt.setInt(1,lnobj.getAccNo());
       	 pstmt.setString(2,lnobj.getAccType());
       	 Object data[][]=gobj.getGoldDet();
       	 for(int i=0;i<((data!=null)?data.length:1);i++){
       		    pstmt.setInt(1,lnobj.getAccNo());				
					pstmt.setString(2,String.valueOf(lnobj.getModuleCode()));
					pstmt.setInt(3,gobj.getApprisersCode());
					if(data!=null)
					{
						pstmt.setInt(4,Integer.parseInt(data[i][0].toString()));
						file_logger.info("column 4"+data[i][0].toString());
						pstmt.setString(5,data[i][1].toString());
						file_logger.info("column 5"+data[i][1].toString());
						pstmt.setDouble(6,Double.parseDouble(data[i][2].toString()));
						file_logger.info("column 6"+data[i][2].toString());
						pstmt.setDouble(7,Double.parseDouble(data[i][3].toString()));
						file_logger.info("column 7"+data[i][3].toString());
						pstmt.setDouble(8,Double.parseDouble(data[i][5].toString()));
						file_logger.info("column 8"+data[i][5].toString());
					}
					else
					{
						pstmt.setInt(4,0);
						pstmt.setString(5,null);
						pstmt.setInt(6,0);
						pstmt.setInt(7,0);
						pstmt.setDouble(8,0.0);
					}
					pstmt.setString(9,gobj.getApprisalDate());
					pstmt.setString(10,gobj.getApprisalTime());
					
					pstmt.addBatch();
				}
				file_logger.info("++++++++++++++++check point for GoldMaster 4+++++++++++++++++");
				int array_gold[]=pstmt.executeBatch();
				file_logger.info("length if batch in gold"+array_gold.length);
			}//GoldDetails
   	 
   	 /*if(lnobj.getSignatureDet()!=null){
   		 commonlocal.storeSignatureDetails(lnobj.getSignatureDet(),lnobj.getAccNo());
   	 }*///Signature Details
   	 
   	 if(lnobj.getPropertyDetails()!=null){
   		    file_logger.info("+++++++++++++++++++check point for store PropertyMaster++++++++++++++++++");
				PropertyObject po=lnobj.getPropertyDetails();
				stmt.executeUpdate("insert into PropertyMasterLog  select * from PropertyMaster where ac_type='"+actype+"' and ac_no="+acno+" ");
       		stmt.executeUpdate("delete from PropertyMaster where ac_type='"+actype+"' and ac_no="+acno+" ");
           	pstmt=conn.prepareStatement("insert into PropertyMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");		
				Object data[][]=po.getTenantsDetail();
				for(int i=0;i<((data!=null)?data.length:1);i++)
				{
					pstmt.setInt(1,lnobj.getAccNo());				
					pstmt.setString(2,String.valueOf(lnobj.getModuleCode()));
					pstmt.setString(3,po.getPropertyNo());
					pstmt.setString(4,po.getAddress());
					pstmt.setString(5,po.getMeasurementEW());
					pstmt.setString(6,po.getMeasurementNS());
					pstmt.setString(7,po.getEastBy());
					pstmt.setString(8,po.getWestBy());
					pstmt.setString(9,po.getNorthBy());
					pstmt.setString(10,po.getSouthBy());
					pstmt.setDouble(11,po.getPropertyValue());
					pstmt.setString(12,po.getPropertyAqdBy());
					pstmt.setString(13,po.getPropertyNature());
					
					if(data!=null)
					{
						pstmt.setString(14,data[i][0].toString());
						pstmt.setDouble(15,Double.parseDouble(data[i][1].toString()));
						pstmt.setString(16,data[i][2].toString());
					}
					else
					{
						pstmt.setString(14,null);
						pstmt.setDouble(15,0);
						pstmt.setString(16,null);
					}
					pstmt.addBatch();
				}
				file_logger.info("++++++++++++++++check point for PropertyMaster+++++++++++++++++");
				pstmt.executeBatch();
			}//	Property
			 
   	 if(lnobj.getIncomeDetails()!=null){
   		    file_logger.info("+++++++++++++++++++check point for store Employment Details++++++++++++++++++");
   		    stmt.executeUpdate("insert into EmploymentMasterLog  select * from EmploymentMaster where ac_type='"+actype+"' and ac_no="+acno+" ");
				stmt.executeUpdate("delete from EmploymentMaster where ac_type='"+actype+"' and ac_no="+acno+" ");
           	pstmt=conn.prepareStatement("insert into EmploymentMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");		
				IncomeObject in[]=lnobj.getIncomeDetails();
				for(int i=0;i<lnobj.getIncomeDetails().length;i++)	
				{
					if(in[i]!=null)	
					{
						pstmt.setInt(1,lnobj.getAccNo());				
						pstmt.setString(2,String.valueOf(lnobj.getModuleCode()));
						pstmt.setString(3,in[i].getTypeOfIncome());
						pstmt.setString(4,null);
						pstmt.setString(5,null);
						pstmt.setString(6,null);
						//	pstmt.setString(6,in[i].getAddress());
						pstmt.setString(7,in[i].getAddress());
						//pstmt.setInt(7,in[i].getPhNo());
						file_logger.info("********************* the phone number is "+in[i].getStringPhNo());
						pstmt.setString(8,in[i].getStringPhNo());				
						pstmt.setString(9,null);				
						pstmt.setString(10,null);
						/*pstmt.setInt(11,in[i].getService());
						 pstmt.setString(12,null);			*/
						pstmt.setString(11,null);
						pstmt.setInt(12,in[i].getService());			
						pstmt.setString(13,null);				
						pstmt.setString(14,null);
						//pstmt.setDouble(15,in[i].getIncome());
						pstmt.setDouble(15,0);
						file_logger.info("--------Before 16th--------");
						pstmt.setDouble(16,in[i].getIncome());	
						file_logger.info("--------After 16th--------");
						file_logger.info("++++++++*****"+in[i].getIncome());
						pstmt.setString(17,null);
						file_logger.info("--------After 17th--------");
						pstmt.setString(18,null);				
						pstmt.setInt(19,0);
						pstmt.setString(20,null);
						pstmt.setString(21,null);
						pstmt.setString(22,null);
						pstmt.setString(23,null);
						pstmt.setString(24,null);
						pstmt.setString(25,null);
						if(i==0){	
							//pstmt.setString(4,in[i].getName());
							pstmt.setString(5,in[i].getName());
							pstmt.setDouble(17,in[i].getExpenditure());
							pstmt.setDouble(22,in[i].getNetIncome());
						}
						if(i==1)
						{					   
							//pstmt.setString(4,in[i].getName());
							pstmt.setString(6,in[i].getName());
							/*pstmt.setString(8,in[i].getEmpNo());			
							 pstmt.setString(9,in[i].getDesignation());				
							 pstmt.setString(10,in[i].getDept());*/
							pstmt.setString(9,in[i].getEmpNo());			
							pstmt.setString(10,in[i].getDesignation());				
							pstmt.setString(11,in[i].getDept());
							
   						pstmt.setString(13,(in[i].isConfirmed()?"T":"F"));				
							pstmt.setString(14,(in[i].isTransferable()?"T":"F"));				
							pstmt.setString(15,(in[i].isCertificateEnclosed()?"T":"F"));	
							pstmt.setDouble(22,in[i].getNetIncome());
	
						}	
						if(i==2)
						{
							pstmt.setString(4,in[i].getName());
							pstmt.setString(5,in[i].getNature());
							pstmt.setDouble(17,in[i].getExpenditure());
							pstmt.setDouble(21,in[i].getTurnOver());
							pstmt.setDouble(22,in[i].getNetIncome());
						}
						if(i==3)
						{
							pstmt.setString(6,in[i].getName());			
							pstmt.setString(18,in[i].getBankName());				
							pstmt.setString(19,in[i].getAccType());				
							pstmt.setInt(20,Integer.parseInt(in[i].getAccNo()));
							pstmt.setDouble(22,in[i].getNetIncome());
						}
						if(i==4)
						{
							pstmt.setDouble(17,in[i].getExpenditure());	
							pstmt.setDouble(22,in[i].getNetIncome());
						}
						pstmt.addBatch();	
					}
				}	
				file_logger.info("++++++++++++++++check point for EmployementMaster 4+++++++++++++++++");
				pstmt.executeBatch();
			}//EmploymentMaster
   	 
   	 if(lnobj.getDependents()!=null || lnobj.getInterests()!=null || lnobj.getRelatives()!=null){
   		 file_logger.info("+++++++++++++++++++check point for store Relative Master++++++++++++++++++");
   		 stmt.executeUpdate("insert into RelativeMasterLog  select * from RelativeMaster where ac_type='"+actype+"' and ac_no="+acno+" ");
                stmt.executeUpdate("delete from RelativeMaster where ac_type='"+actype+"' and ac_no="+acno+" ");
   		 	pstmt=conn.prepareStatement("insert into RelativeMaster values(?,?,?,?,?,?,?,?,?)");		
				if(lnobj.getRelatives().length>0)
				{
					//Get relative details and insert
					Object obj[][]=lnobj.getRelatives();
					for(int i=0;i<obj.length;i++)
					{
						pstmt.setString(1,String.valueOf(lnobj.getModuleCode()));
						pstmt.setInt(2,lnobj.getAccNo());
						pstmt.setString(3,"Relatives");
						pstmt.setString(4,obj[i][0].toString());
						pstmt.setString(5,obj[i][1].toString());
						pstmt.setString(6,obj[i][3].toString());
						pstmt.setString(7,obj[i][4].toString());
						pstmt.setString(8,obj[i][5].toString());
						pstmt.setString(9,obj[i][2].toString());
						pstmt.addBatch();				
					}
				}
				if(lnobj.getDependents()!=null){
					//Get dependents details and insert
					
					Object obj[][]=lnobj.getDependents();
					for(int i=0;i<obj.length;i++)
					{
						pstmt.setString(1,String.valueOf(lnobj.getModuleCode()));
						pstmt.setInt(2,lnobj.getAccNo());
						pstmt.setString(3,"Dependents");
						pstmt.setString(4,obj[i][0].toString());
						pstmt.setString(5,obj[i][1].toString());
						pstmt.setString(6,obj[i][2].toString());
						pstmt.setString(7,null);
						pstmt.setString(8,null);
						pstmt.setString(9,obj[i][3].toString());
						pstmt.addBatch();				
					}
				}
				if(lnobj.getInterests()!=null)
				{
					//Get the interests(who and all having interest in loanees property) details
					Object obj[][]=lnobj.getInterests();
					for(int i=0;i<obj.length;i++)
					{
						pstmt.setString(1,String.valueOf(lnobj.getModuleCode()));
						pstmt.setInt(2,lnobj.getAccNo());
						pstmt.setString(3,"Interests");
						pstmt.setString(4,obj[i][0].toString());
						pstmt.setString(5,obj[i][1].toString());
						pstmt.setString(6,obj[i][2].toString());
						pstmt.setString(7,obj[i][3].toString());
						pstmt.setString(8,obj[i][4].toString());
						pstmt.setString(9,null);
						pstmt.addBatch();				
					}
				}
				file_logger.info("++++++++++++++++check point for RelativeMaster 4+++++++++++++++++");
				pstmt.executeBatch();
			}//RelativeMaster
		
   	 if(lnobj.getNoOfCoBorrowers()>0 || lnobj.getNoOfSurities()>0)
			{
				file_logger.info("the number of coborrowers "+lnobj.getNoOfCoBorrowers());
				file_logger.info("the number of surities "+lnobj.getNoOfSurities());
				if(lnobj.getNoOfCoBorrowers()>0)
				{
					stmt.executeUpdate("insert into BorrowerMasterLog  select * from BorrowerMaster where ac_type='"+actype+"' and ac_no="+acno+" and type='C' ");
	                stmt.executeUpdate("delete from BorrowerMaster where ac_type='"+actype+"' and ac_no="+acno+" and type='C' ");
	                file_logger.info("After ");
					pstmt=conn.prepareStatement("insert into BorrowerMaster values(?,?,?,?,?,?)");
					
					Vector  borrowers=null;
					borrowers=lnobj.getCoBorrowers();
					file_logger.info("Borrowers-------"+borrowers);
					
					
					String borrower_detail,sh_type;
					int sh_no;
					file_logger.info("Testing");
					if(borrowers!=null){
					for(int i=0;i<borrowers.size();i++)
					{
						borrower_detail = borrowers.get(i).toString();
						file_logger.info("))))))))))))))1"+borrower_detail);
						sh_type = borrower_detail.substring(0,7);
						file_logger.info("))))))))))))))2"+sh_type);
						
						//file_logger.info("Shre No"+borrower_detail.substring(13,borrower_detail.length()));
						sh_no = Integer.parseInt(borrower_detail.substring(8,borrower_detail.length()));
						file_logger.info("))))))))))))))4"+sh_no);
						pstmt.setString(1,String.valueOf(lnobj.getModuleCode()));
						pstmt.setInt(2,lnobj.getAccNo());
						pstmt.setString(3,"C");
						pstmt.setString(4,sh_type);
						pstmt.setInt(5,sh_no);
						
						ResultSet rs1=stmt.executeQuery("select br_code from ShareMaster where ac_type='"+sh_type+"' and ac_no="+sh_no);
						if(rs1.next())
							pstmt.setString(6,String.valueOf(rs1.getInt(1)));
						else
							pstmt.setString(6,null);
						pstmt.addBatch();	
					}
					}
					else
					{
						file_logger.info("no co brwes");
					}
					file_logger.info("+++++++++++++++++++++check point for Borrwer 3+++++++++++++++++++++");
					pstmt.executeBatch();
				}
				
				if(lnobj.getNoOfSurities()>0)
				{
					stmt.executeUpdate("insert into BorrowerMasterLog select * from BorrowerMaster where ac_type='"+actype+"' and ac_no="+acno+" and type='S' ");
	                 stmt.executeUpdate("delete from BorrowerMaster where ac_type='"+actype+"' and ac_no="+acno+" and type='S' ");
					pstmt=conn.prepareStatement("insert into BorrowerMaster values(?,?,?,?,?,?)");
					//getSurities return vector which will contain coborrower details
					Vector  surities=null;
					surities=lnobj.getSurities();
					String surities_detail,sh_type;
					int sh_no;
					
					if(surities!=null){
					for(int i=0;i<surities.size();i++)
					{
						surities_detail = surities.get(i).toString();
						file_logger.info("HHHHelo surity "+surities_detail);
						sh_type = surities_detail.substring(0,7);
						file_logger.info("HHHHelo surity "+sh_type);
						sh_no = Integer.parseInt(surities_detail.substring(11,surities_detail.length()));
						pstmt.setString(1,String.valueOf(lnobj.getModuleCode()));
						pstmt.setInt(2,lnobj.getAccNo());
						pstmt.setString(3,"S");
						pstmt.setString(4,sh_type);
						pstmt.setInt(5,sh_no);
						
						ResultSet rs1=stmt.executeQuery("select br_code from ShareMaster where ac_type='"+sh_type+"' and ac_no="+sh_no);
						if(rs1.next())
							pstmt.setString(6,String.valueOf(rs1.getInt(1)));
						else
							pstmt.setString(6,null);
						pstmt.addBatch();	
					}
					pstmt.executeBatch();
				}
				file_logger.info("+++++++++++++++++++++check point for Surities 3+++++++++++++++++++++");
			}//Coborrowers
			}
   	}
    catch(Exception exception){
    	/*file_logger.info(exception.getMessage());
    	sessionContext.setRollbackOnly();*/
    	exception.printStackTrace();
    	return 0;
    	}
	finally{
		try{		    	
			conn.close();
		}catch(Exception exception){exception.printStackTrace();}
	}
	file_logger.info("***********updatemaster*********");
	
	return(0);
	 
}





}