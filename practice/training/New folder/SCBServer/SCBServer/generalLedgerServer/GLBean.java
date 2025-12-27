package generalLedgerServer;

import exceptions.DateFormatException;
import exceptions.RecordNotInsertedException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotDeletedException;
import exceptions.RecordsNotFoundException;
import masterObject.general.DoubleFormat;
import general.Validations;
import masterObject.generalLedger.DayBookObject;
import masterObject.generalLedger.DlyConsObject;
import masterObject.generalLedger.Form1Object;
import masterObject.generalLedger.GLObject;
import masterObject.generalLedger.GLReportObject;
import masterObject.generalLedger.MonthlyConsObject;
import masterObject.generalLedger.TransferScroll;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.swing.JOptionPane;

/**
 * @author Riswan Babu.H
 *
 */

public class GLBean implements javax.ejb.SessionBean
{
	SessionContext sessioncontext=null;
	DataSource ds=null;
	Connection connection=null;
	
	public GLBean(){
	}  
	
//	Beans life cycle management methods follow.
	public void ejbCreate(){
		
		try{
			ds=(DataSource)new InitialContext().lookup("java:MySqlDS");
			
		}catch(NamingException ex){ex.printStackTrace();}
		
	}
	public void setSessionContext(SessionContext context){
		sessioncontext=context;
	}
	public void ejbActivate(){}
	public void ejbPassivate(){}
	public void ejbRemove(){}
	
//	Bean's business methods follow.
	
	/**
	 * To Covert Date Format from String to int
	 */
	public int convertDateYYMMDD(String date)
	{
	    String yy,mm,dd,date_int;
           
	    //System.out.println("Date for STok : "+date);
	    StringTokenizer st=new StringTokenizer(date,"/");
	    
	    dd=st.nextToken();
	    mm=st.nextToken();
	    yy=st.nextToken();
	    
	    date_int=yy+mm+dd;
	    
	    return Integer.parseInt(date_int);
	}
    public Object[][] getDatesForAMonth(String date){
    	Connection conn=null;
    	Statement st1=null;
    	PreparedStatement pst1=null;
    	ResultSet rs=null;
    	Object[] dates=null;
    	Object[][] dates_day=null;
        String first_dt=null;
        int no_of_days=0;
    	try{
    		conn=getConnection();
    		st1=conn.createStatement();
    		pst1=conn.prepareStatement("select date_format(?,'%d-%b-%Y') date, left(dayname(?),3) name");
    		
    		rs=st1.executeQuery("select '"+date+"' first_dt,(date_sub(date_add(date_sub('"+date+"',interval dayofmonth('"+date+"')-1 day),interval 1 month),interval 1 day)) last_dt,dayofmonth(date_sub(date_add(date_sub('"+date+"',interval dayofmonth('"+date+"')-1 day),interval 1 month),interval 1 day)) days");
    		rs.next();
    		first_dt=rs.getString("first_dt");
    		no_of_days=rs.getInt("days");
    		    		   		
    	    dates=new Object[no_of_days];
    		
    		//System.out.println(first_dt.substring(0,8)+0+1);
    	    int z=1;
    		for(int r=0; r<dates.length; r++){
    			if(z<10)
    			dates[r]=first_dt.substring(0,8)+0+z;
    			else
    			dates[r]=first_dt.substring(0,8)+z;
    			
    			z++;
    		}
    		
    		rs=null;
    		dates_day=new Object[no_of_days][2];
    		for(int r=0; r<dates.length; r++){
    		pst1.setString(1,dates[r].toString());
    		pst1.setString(2,dates[r].toString());
    		rs=pst1.executeQuery();
    		rs.next();
    		dates_day[r][0]=rs.getString("date");
    		dates_day[r][1]=rs.getString("name");
    		}
    		
    		return dates_day;
    	}catch(SQLException se){se.printStackTrace();}
    	finally{
    		try{
    			conn.close();
    		}catch(SQLException e){e.printStackTrace();}
    	}
    	return dates_day;
    }
    
    
	
	
	public Object[][] getGLHeads(String cur_date){
		Connection conn=null;
		Statement st1=null;
		Object[][] heads=null;
		ResultSet rs=null;
		try{
			conn=getConnection();
			st1=conn.createStatement();
			
			rs=st1.executeQuery("select x.code,x.head from GLHead x,GLHead y where x.code between y.code+1 and y.code+999 and y.code in(select code from GLHead where code like '%000') and curdate() between x.frm_dt and x.to_dt ");
			rs.last();
			
			heads=new Object[rs.getRow()][2];
			rs.beforeFirst();
			rs.next();
			for(int r=0; r<heads.length; r++)
			{
				heads[r][0]=new Integer(rs.getInt("code"));
				heads[r][1]=rs.getString("head");
				rs.next();
			}
						
			return heads;
		}catch(SQLException se){se.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(SQLException se){se.printStackTrace();}
		}
		return heads;
	}
	/**
	 * swapna
	 * @return
	 */
	public String dailyPosting(String date,String de_user,String de_tml,String datetime,String br_location){
		Connection conn=null;
		Statement stmt=null,stmt1=null;
		PreparedStatement pstmt=null;
		ResultSet rs_gl=null,rs=null,rs_br_name=null;
		int br_code=0;
		System.out.println("*********Daily POsting details in bean********");
		System.out.println("date"+date);
		System.out.println("de_user"+de_user+"de tml"+de_tml);
		System.out.println("datetime"+datetime+"location"+br_location);
		System.out.println("*********Daily POsting details in bean********");
		try{ 
		    System.out.println("Inside DailyPosting..");
			conn=getConnection();
			stmt=conn.createStatement();
			stmt1=conn.createStatement();
			//stmt2=conn.createStatement();
			rs_br_name=stmt.executeQuery("select br_code from BranchMaster where br_name like '"+br_location+"' ");
			rs_br_name.next();
			br_code=rs_br_name.getInt("br_code");
			rs=stmt.executeQuery("select * from GLTransaction where trn_date = '"+date+"'");
			if(rs.next()){
				rs.beforeFirst();
				pstmt=conn.prepareStatement("insert into GLTranOld values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				while(rs.next()){
					pstmt.setString(1,rs.getString(1));
					pstmt.setString(2,rs.getString(2));
					pstmt.setString(3,rs.getString(3));
					pstmt.setString(4,rs.getString(4));
					pstmt.setString(5,rs.getString(5));
					pstmt.setString(6,rs.getString(6));
					pstmt.setString(7,rs.getString(7));
					pstmt.setString(8,rs.getString(8));
					pstmt.setString(9,rs.getString(9));
					pstmt.setString(10,rs.getString(10));
					pstmt.setString(11,rs.getString(11));
					pstmt.setString(12,rs.getString(12));
					pstmt.setString(13,rs.getString(13));
					pstmt.setString(14,rs.getString(14));
					pstmt.executeUpdate();
				}
			}
			stmt.executeUpdate("delete from GLTransaction where trn_date='"+date+"' ");
			stmt.executeUpdate("delete from DailySummary where trn_dt='"+Validations.convertYMD(date)+"'");
			stmt.executeUpdate("drop table if exists gltran");
			stmt.executeUpdate("create table gltran select *  from GLTranOld where trn_date='"+date+"';");
			stmt.executeUpdate("update DailyStatus set post_ind='T' where trn_dt='"+Validations.convertYMD(date)+"'");
			stmt.executeUpdate("update DailyConStatus set post_ind='F' where trn_dt='"+Validations.convertYMD(date)+"' and br_code="+br_code+" ");
			stmt.executeUpdate("update MonthlyStatus set post_ind='F' ,month_close='F' where yr_mth="+convertToYYYYMM(date)+"  ");
			stmt.executeUpdate("update MonthlyConStatus set post_ind='F' where yr_mth="+convertToYYYYMM(date)+" and br_code="+br_code+" ");
			stmt.executeUpdate("update YearlyStatus set post_ind='F' ,year_close='F' where yr_mth="+convertToYYYYMM(date)+"  and br_code="+br_code+" ");
			rs=stmt.executeQuery("select distinct gl_type,gl_code  from gltran order by gl_type,gl_code");
			pstmt=conn.prepareStatement("insert into DailySummary values(?,?,?,?,?,?,?,?,?,?,?,?)");
			date=Validations.convertYMD(date);
			if(rs.next()){
				rs.beforeFirst();
				while(rs.next()){
					rs_gl=stmt1.executeQuery("select gl_type,gl_code,trn_date,(select sum(trn_amt) from gltran where gl_code="+rs.getInt("gl_code")+" and gl_type='"+rs.getString("gl_type")+"' and trn_mode='C' and cd_ind='D' group by gl_code,trn_mode,cd_ind) as cash_dr,(select sum(trn_amt) from gltran where gl_code="+rs.getInt("gl_code")+" and gl_type='"+rs.getString("gl_type")+"' and trn_mode='G' and cd_ind='D' group by gl_code,trn_mode,cd_ind) as cg_dr ,(select sum(trn_amt) from gltran where gl_code="+rs.getInt("gl_code")+" and gl_type='"+rs.getString("gl_type")+"' and trn_mode='T' and cd_ind='D' group by gl_code,trn_mode,cd_ind) as trf_dr,(select sum(trn_amt) from gltran where gl_code="+rs.getInt("gl_code")+" and gl_type='"+rs.getString("gl_type")+"' and trn_mode='C' and cd_ind='C' group by gl_code,trn_mode,cd_ind) as cash_cr,(select sum(trn_amt) from gltran where gl_code="+rs.getInt("gl_code")+" and gl_type='"+rs.getString("gl_type")+"' and trn_mode='G' and cd_ind='C' group by gl_code,trn_mode,cd_ind) as cg_cr,(select sum(trn_amt) from gltran where gl_code="+rs.getInt("gl_code")+" and gl_type='"+rs.getString("gl_type")+"' and trn_mode='T' and cd_ind='C' group by gl_code,trn_mode,cd_ind) as trf_cr from gltran gl where gl.gl_code="+rs.getInt("gl_code")+" and gl.gl_type='"+rs.getString("gl_type")+"' group by gl_code order by gl_code,gl_type;");
					if(rs_gl.next()){
						pstmt.setString(1,date);
						pstmt.setString(2,rs_gl.getString("gl_type"));
						pstmt.setString(3,rs_gl.getString("gl_code"));
						if(rs_gl.getString("cash_dr")!=null)
							pstmt.setDouble(4,rs_gl.getDouble("cash_dr"));
						else
							pstmt.setDouble(4,0.00);
								
						if(rs_gl.getString("cg_dr")!=null)
							pstmt.setDouble(5,rs_gl.getDouble("cg_dr"));
						else
							pstmt.setDouble(5,0.00);
							
						if(rs_gl.getString("trf_dr")!=null)
							pstmt.setDouble(6,rs_gl.getDouble("trf_dr"));
						else
							pstmt.setDouble(6,0.00);
							
						if(rs_gl.getString("cash_cr")!=null)
							pstmt.setDouble(7,rs_gl.getDouble("cash_cr"));
						else
							pstmt.setDouble(7,0.00);
							
						if(rs_gl.getString("cg_cr")!=null)
							pstmt.setDouble(8,rs_gl.getDouble("cg_cr"));
						else
							pstmt.setDouble(8,0.00);

						if(rs_gl.getString("trf_cr")!=null)
							pstmt.setDouble(9,rs_gl.getDouble("trf_cr"));
						else
							pstmt.setDouble(9,0.00);
							
						pstmt.setString(10,de_tml);
						pstmt.setString(11,de_user);
						pstmt.setString(12,datetime);
						pstmt.executeUpdate();
						System.out.println("gl_code="+rs_gl.getString("gl_code"));
					}
				}
			}
		}catch(SQLException se){
		    sessioncontext.setRollbackOnly();    
		    System.out.println("RollBacked...");
		    se.printStackTrace();
		    return "0";}
		finally{
		    try{
		    	stmt.executeUpdate("drop table if exists gltran");
		    	conn.close();
				
		    }catch(SQLException se){se.printStackTrace();}
		}
	return "1";	
	}
	/**
	 * swapna
	 * @return
	 */
	public int doDayClose(String date,String user,String tml)
	{
	    Connection conn=null;
	    PreparedStatement pstmt=null;
	    //boolean do_allow=false;
	    try{
	        conn=getConnection();
	        pstmt=conn.prepareStatement("update DailyStatus set day_close='T' where trn_dt='"+Validations.convertYMD(date)+"' ");
	        if(pstmt.executeUpdate()!=0)
	        {
	            System.out.println(" updated DayClose!");
	            return -1;
	        }
	        else
	           System.out.println("unable to update DayClose!"); 
	    }catch(SQLException se){
	    	sessioncontext.setRollbackOnly();
	    	se.printStackTrace();
	    	}
	    finally{
	        try{
	            conn.close();
	        }catch(SQLException se){se.printStackTrace();}
	    }
	    return 0;
	}
	/**
	 * swapna
	 * @param date
	 * @param lastdate
	 * @return PF	= Posting not done for previous date
	 * @return CF	= Cashier not closed
	 * @return PT	=Posting done do you to overwrite
	 * @return CT	=cash closed posting not done
	 * @return DT	= Day Closed    
	 * @return 0	=no entries in DailyStatus
	 */
	public String[][] checkDailyStatus(String date,String lastdate){
		Connection conn=null;
	    Statement stmt=null;
	    ResultSet rs=null;
	    String ret[][]=new String[1][2];
	    System.out.println("from date in dlystatus="+date);
	    System.out.println("to date in dlystatus="+lastdate);
	    try{
	    	conn=getConnection();
	    	System.out.println("Connection : "+conn);
	    	stmt=conn.createStatement();
	    	/*int month=0;
			int year=0;
			int date1=0;*/
			/*StringTokenizer st=new StringTokenizer(date,"/");
			date1=Integer.parseInt(st.nextToken());
			month=Integer.parseInt(st.nextToken());
			year=Integer.parseInt(st.nextToken());*/
			rs=stmt.executeQuery("select * from DailyStatus where trn_dt<='"+Validations.convertYMD(date)+"' ");
	    	if(rs.next())
	    		System.out.println("greater than minimum date in Dialy Status ");
	    	else{
	    		rs=stmt.executeQuery("select * from DailyStatus order by trn_dt limit 1");
	    		if(rs.next()){// date less than minimum date;
	    			ret[0][0]="MIN";
	    			ret[0][1]=rs.getString("trn_dt");
	    			return ret;
	    		}
	    		else{
	    			ret[0][0]="0";
	    			ret[0][1]="0";
	    			return ret;
	    		}
	    	}
	    	rs=stmt.executeQuery("select * from DailyStatus where trn_dt<'"+Validations.convertYMD(date)+"' and (post_ind='F' or cash_close='F') order by trn_dt  limit 1");
	    	if(rs.next()){//posting not done for previous days
	    		ret[0][0]="PF";
	    		ret[0][1]=rs.getString("trn_dt");
	    		return ret;
	    	}
	    	rs=stmt.executeQuery("select * from DailyStatus where trn_dt between '"+Validations.convertYMD(date)+"' and '"+Validations.convertYMD(lastdate)+"' and cash_close='F' order by trn_dt limit 1 ");
	    	if(rs.next()){//cash not closed
	    		ret[0][0]="CF";
	    		ret[0][1]=rs.getString("trn_dt");
	    		return ret;
    		}
	    	rs=stmt.executeQuery("select * from DailyStatus where trn_dt between '"+Validations.convertYMD(date)+"' and '"+Validations.convertYMD(lastdate)+"' and cash_close='T' and post_ind='F' order by trn_dt ");
	    	if(rs.next()){//cash close you can do posting
	    		ret[0][0]="CT";
	    		ret[0][1]=rs.getString("trn_dt");
	    		return ret;
    		}
	    	rs=stmt.executeQuery("select * from DailyStatus where trn_dt between '"+Validations.convertYMD(date)+"' and '"+Validations.convertYMD(lastdate)+"' and day_close='T' order by trn_dt  limit 1 ");
    		if(rs.next()){//day closed for paricular day you cannot do posting
    			ret[0][0]="DT";
	    		ret[0][1]=rs.getString("trn_dt");
	    		return ret;
    		}
	    	rs=stmt.executeQuery("select * from DailyStatus where trn_dt between '"+Validations.convertYMD(date)+"' and '"+Validations.convertYMD(lastdate)+"' and post_ind='T' order by trn_dt limit 1 ");
	    	if(rs.next()){//posting done reposting
	    		ret[0][0]="PT";
	    		ret[0][1]=rs.getString("trn_dt");
	    		return ret;
    		}
	    	rs=stmt.executeQuery("select * from DailyStatus order by trn_dt limit 1 ");
	    	if(rs.next()){//No Entries in Daily Status  
	    		ret[0][0]="0";// returning minimum date from DailyStaus;
	    		ret[0][1]=rs.getString("trn_dt");
	    		return ret;
    		}
	    } catch(Exception se){se.printStackTrace();}
	    finally{
	        try{
	            conn.close();
	        }catch(SQLException se){se.printStackTrace();}
	    }
	    ret[0][0]="0";// you dont have entries in dailystatusl;
	    ret[0][1]="0";
	    return ret;
   }
	public String[][] checkDailyStatusForMonth(String date,String lastdate){
		Connection conn=null;
	    Statement stmt=null,stmt1=null;
	    ResultSet rs=null,rs1=null;
	    //int rows=0;
	    String ret[][]=new String[1][2];
	    System.out.println("from date in dlystatus="+date);
	    System.out.println("to date in dlystatus="+lastdate);
	    try{
	    	conn=getConnection();
	    	System.out.println("Connection : "+conn);
	    	stmt=conn.createStatement();
	    	stmt1=conn.createStatement();
	    	int month=0;
			int year=0;
			int date1=0;
			StringTokenizer st=new StringTokenizer(date,"/");
			date1=Integer.parseInt(st.nextToken());
			month=Integer.parseInt(st.nextToken());
			year=Integer.parseInt(st.nextToken());
			rs=stmt.executeQuery("select year(trn_dt),trn_dt from DailyStatus where year(trn_dt)<="+year+"  order by  trn_dt desc limit 1");
	    	if(rs.next()){
	    		if(year==rs.getInt(1)){
	    			rs1=stmt1.executeQuery("select trn_dt from DailyStatus where month(trn_dt)<="+month+"  and year(trn_dt)="+year+"  order by  trn_dt limit 1");
	    			if(rs1.next())
	    	    		System.out.println("greater than minimum month in Dialy Status ");
	    			else{
	    	    		ret[0][0]="MIN";
	    	    		ret[0][1]=rs.getString("trn_dt");
	    	    		return ret;
	    			}
	    		}
	    		else
	    			System.out.println("greater than minimum date in Dialy Status ");
	    	}
	    	else{
	    		rs=stmt.executeQuery("select * from DailyStatus order by trn_dt limit 1");
	    		if(rs.next()){// date less than minimum date;
	    			ret[0][0]="MIN";
	    			ret[0][1]=rs.getString("trn_dt");
	    			return ret;
	    		}
	    		else{
	    			ret[0][0]="0";// no entries;
	    			ret[0][1]="0";
	    			return ret;
	    		}
	    	}
	    	rs=stmt.executeQuery("select * from DailyStatus where trn_dt<'"+Validations.convertYMD(date)+"' and (post_ind='F' or cash_close='F') order by trn_dt  limit 1");
	    	if(rs.next()){//posting not done for previous days
	    		ret[0][0]="PF";
	    		ret[0][1]=rs.getString("trn_dt");
	    		return ret;
	    	}
	    	rs=stmt.executeQuery("select * from DailyStatus where trn_dt between '"+Validations.convertYMD(date)+"' and '"+Validations.convertYMD(lastdate)+"' and cash_close='F' order by trn_dt limit 1 ");
	    	if(rs.next()){//cash not closed
	    		ret[0][0]="CF";
	    		ret[0][1]=rs.getString("trn_dt");
	    		return ret;
    		}
	    	rs=stmt.executeQuery("select * from DailyStatus where trn_dt between '"+Validations.convertYMD(date)+"' and '"+Validations.convertYMD(lastdate)+"' and cash_close='T' and post_ind='F' order by trn_dt ");
	    	if(rs.next()){//cash close you can do posting
	    		ret[0][0]="CT";
	    		ret[0][1]=rs.getString("trn_dt");
	    		return ret;
    		}
	    	rs=stmt.executeQuery("select * from DailyStatus where trn_dt between '"+Validations.convertYMD(date)+"' and '"+Validations.convertYMD(lastdate)+"' and day_close='T' order by trn_dt  limit 1 ");
    		if(rs.next()){//day closed for paricular day you cannot do posting
    			ret[0][0]="DT";
	    		ret[0][1]=rs.getString("trn_dt");
	    		return ret;
    		}
	    	rs=stmt.executeQuery("select * from DailyStatus where trn_dt between '"+Validations.convertYMD(date)+"' and '"+Validations.convertYMD(lastdate)+"' and post_ind='T' order by trn_dt limit 1 ");
	    	if(rs.next()){//posting done reposting
	    		ret[0][0]="PT";
	    		ret[0][1]=rs.getString("trn_dt");
	    		return ret;
    		}
	    	rs=stmt.executeQuery("select * from DailyStatus order by trn_dt limit 1 ");
	    	if(rs.next()){//No Entries in Daily Status  
	    		ret[0][0]="0";// returning minimum date from DailyStaus;
	    		ret[0][1]=rs.getString("trn_dt");
	    		return ret;
    		}
	    } catch(Exception se){se.printStackTrace();}
	    finally{
	        try{
	            conn.close();
	        }catch(SQLException se){se.printStackTrace();}
	    }
	    ret[0][0]="0";// you dont have entries in dailystatusl;
	    ret[0][1]="0";
	    return ret;
   }
/**
 * swapna
 * @param frm_mth=fromdate
 * @param to_mth= todate
 * @return PF	= posting not done for provious date 
 * @return PT  or 0= Posting done do you to overwrite?
 * @return MT 	= Month Closed
 * @return OK	= allow for Posting
 * @return OP	= openig balance not updated
 * @return NO	= Connot do posting diff is more
 * @return 1	= no entries in MonthlyStatus allow for posting  
 */
	public String[][] checkMonthlyStatus(String frm_mth,String to_mth){
		Connection conn=null;
	    Statement stmt=null;
	    ResultSet rs=null;
	    //int rows=0;
	    String ret[][]=new String[1][2];
	    String str_year=null,str_mth=null,str_yrmth=null;
	    int int_year=0,int_mth=0,int_yrmth=0,frm_month=0;
	    try{
	    	conn=getConnection();
	    	System.out.println("Connection : "+conn);
	    	stmt=conn.createStatement();
	    	rs=stmt.executeQuery("select * from MonthlySummary where yr_mth<"+convertToYYYYMM(frm_mth)+" and record_type='O' order by yr_mth limit 1");
	    	if(rs.next()){
	    		System.out.println(" Opening balance found");
	    	}
	    	else{//Opening Balance Not updated
	    		ret[0][0]="OP";
	    		ret[0][1]="OP";
	    		return ret;
	    	}
	    	rs=stmt.executeQuery("select * from MonthlyStatus where yr_mth<"+convertToYYYYMM(frm_mth)+" and post_ind='F' order by yr_mth limit 1");
	    	if(rs.next()){//posting not done for previous days
	    		ret[0][0]="PF";
	    		ret[0][1]=rs.getString("yr_mth");
	    		return ret;
	    	}
	    	rs=stmt.executeQuery("select * from MonthlyStatus where yr_mth between "+convertToYYYYMM(frm_mth)+" and "+convertToYYYYMM(frm_mth)+" and month_close='T' order by yr_mth  limit 1 ");
    		if(rs.next()){//Month closed
    			ret[0][0]="MT";
	    		ret[0][1]=rs.getString("yr_mth");
	    		return ret;
    		}
    		rs=stmt.executeQuery("select * from MonthlyStatus where yr_mth between "+convertToYYYYMM(frm_mth)+" and "+convertToYYYYMM(frm_mth)+" and post_ind='T' order by yr_mth  limit 1 ");
    		if(rs.next()){//Posting Done
    			ret[0][0]="PT";
	    		ret[0][1]=rs.getString("yr_mth");
	    		return ret;
    		}
    		rs=stmt.executeQuery("select * from MonthlyStatus where yr_mth between "+convertToYYYYMM(frm_mth)+" and "+convertToYYYYMM(frm_mth)+" and post_ind='F' ");
    		if(rs.next()){
    			ret[0][0]="OK";
    			ret[0][1]="OK";
    			return ret;
    		}
    		rs=stmt.executeQuery("select * from MonthlyStatus where post_ind='T' order by yr_mth desc limit 1");
    		if(rs.next()){
    			frm_month=convertToYYYYMM(frm_mth);
         		str_year=rs.getString("yr_mth").substring(0,4);
         		str_mth=rs.getString("yr_mth").substring(4,6);
         		System.out.println("month con status="+rs.getInt("yr_mth"));
         		if(rs.getInt("yr_mth")>=frm_month){//over write
         			ret[0][0]="0";
         			ret[0][1]=rs.getString("yr_mth");
         			return ret;
         		}
         		if(rs.getInt("yr_mth")<frm_month){
         			if(str_mth.equals("12")){
         				int_year=Integer.parseInt(str_year);
         				int_year++;
         				int_mth=1;
         				str_yrmth=String.valueOf(int_year)+String.valueOf(int_mth);
         			}
         			else{
         				int_yrmth=rs.getInt("yr_mth");
         				int_yrmth++;
         				str_yrmth=String.valueOf(int_yrmth);
         			}
         			if(Integer.parseInt(str_yrmth)==frm_month){//this is next month you can continue
         				ret[0][0]="OK";
         				ret[0][1]="OK";
         				return ret;
         			}
         			else{// diff is more you cannot continue;
         				ret[0][0]="NO";
         				ret[0][1]=rs.getString("yr_mth");
         				return ret;
         			}
         		}
         	}
         	else{
         		ret[0][0]="1";// no entries in MonthlyStatus 
         		ret[0][1]="1";
         		return ret;
         	}

    		
	    } catch(Exception se){se.printStackTrace();}
	    finally{
	        try{
	            conn.close();
	        }catch(SQLException se){se.printStackTrace();}
	    }
	    ret[0][0]="0";//allow for posting
	    ret[0][1]="0";
	    return ret;
   }
	public String[][] checkForMonthClose(String yr_mth){
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		String ret[][]=new String[1][2];
		//String str_year=null,str_mth=null;
		//String prev_year=null,prev_mth=null;
		//int mth=0;
		try{
		   	conn=getConnection();
		   	stmt=conn.createStatement();
		   	//stmt1=conn.createStatement();
		   	rs=stmt.executeQuery(" select * from MonthlyStatus where month_close='F' and yr_mth<"+yr_mth+" ");
		   	if(rs.next()){
		   		ret[0][0]="-1";// previous months not closed
				ret[0][1]=rs.getString("yr_mth");//no
				return ret;
		   	}
		   	rs=stmt.executeQuery(" select * from MonthlyStatus where post_ind='T' and yr_mth="+yr_mth+" ");
		   	if(rs.next()){
		   		ret[0][0]="1";// ok
				ret[0][1]="1";
				return ret;
		   	}
		   	else{
		   		ret[0][0]="0";// no
				ret[0][1]="0";
				return ret;
		   	}
		}catch(Exception se){se.printStackTrace();}
	    finally{
	        try{
	            conn.close();
	        }catch(SQLException se){se.printStackTrace();}
	    }
   	return ret;
   	}
	public int checkYearPosting(String from_dt,String to_dt,String br_location ){
		 Connection conn=null;
	     Statement stmt=null;
	     ResultSet rs_br_name=null,rs=null;
	     //int frm_mth=convertToYYYYMM(from_dt);
	     int br_code=0,ret=-10;
	     try{
	          conn=getConnection();
	          stmt=conn.createStatement();
	          rs_br_name=stmt.executeQuery("select br_code from BranchMaster where br_name like '"+br_location+"' ");
	          rs_br_name.next();
			  br_code=rs_br_name.getInt("br_code");
			  rs=stmt.executeQuery("select * from YearlyStatus where yr_mth between "+convertToYYYYMM(from_dt)+" and "+convertToYYYYMM(to_dt)+" and br_code="+br_code+"  and  year_close='T' ");
	          if(rs.next())
	          	return 2;
	          rs=stmt.executeQuery("select * from YearlyStatus where yr_mth between "+convertToYYYYMM(from_dt)+" and "+convertToYYYYMM(to_dt)+" and br_code="+br_code+"  and  post_ind='T' ");
	          if(rs.next())
	          	return 1;
	          rs=stmt.executeQuery("select * from YearlyStatus where yr_mth between "+convertToYYYYMM(from_dt)+" and "+convertToYYYYMM(to_dt)+" and br_code="+br_code+"  and  post_ind='F' ");
	          if(rs.next())
	          	return 0;
	     }catch(Exception se){se.printStackTrace();}
		    finally{
		        try{
		            conn.close();
		        }catch(SQLException se){se.printStackTrace();}
		    }
		    
	        return ret;
	}

	
	public String[][] checkYearStatus(String yr_mth,String br_location){
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null,rs1=null,rs_br_name=null;
		String ret[][]=new String[1][2];
		String str_year=null,str_mth=null;
		//String prev_year=null,prev_mth=null;
		int br_code=0,int_count=0;;
		String str_frm_year=null,str_frm_mth=null,str_frm_date=null,str_date=null;
		try{
		   	conn=getConnection();
		   	stmt=conn.createStatement();
		   //	stmt1=conn.createStatement();
		   	System.out.println(" i  am in year status ");
		   	System.out.println("year mth="+yr_mth);
		   	rs_br_name=stmt.executeQuery("select br_code from BranchMaster where br_name like '"+br_location+"' ");
			rs_br_name.next();
			br_code=rs_br_name.getInt("br_code");
			rs=stmt.executeQuery("select * from MonthlyStatus where yr_mth<="+yr_mth+" ");
			if(rs.next())
				System.out.println("have entries in MonthlyStatus ");
			else{
				ret[0][0]="CN";// no entries in Monthly Status
				ret[0][0]="CN";
				return ret;
			}
			
		    rs=stmt.executeQuery("select * from MonthlyStatus where yr_mth<="+yr_mth+" and month_close='F' order by yr_mth limit 1 ");
		   	if(rs.next()){
		   		ret[0][0]="-2";// monthl not closed for yr_mth
		   		ret[0][1]=rs.getString("yr_mth");
		   		return ret;
		   	}
		   	rs=stmt.executeQuery("select * from MonthlyStatus where yr_mth<="+yr_mth+" and  month_close='T' order by yr_mth desc limit 1");
		   	if(rs.next()){
		   		if(yr_mth.equals(rs.getString("yr_mth"))){// Ok from monthly status
		   			ret[0][0]="OK";
		   			ret[0][1]=rs.getString("yr_mth");
		   			System.out.println(" monhtly Ok....");
		   		}
		   		else{
		   			ret[0][0]="NO";//monthly status not updated
		   			ret[0][1]=rs.getString("yr_mth");
		   			return ret;
		   		}
		   	}
		   	else{// no entires in monthly closed that are  closed 
		   		ret[0][0]="-1";
	   			ret[0][1]="-1";
	   			return ret;
		   	}
		   	rs1=stmt.executeQuery("select * from YearlyStatus where br_code="+br_code+" ");
		   	if(rs1.next()){
		   		rs=stmt.executeQuery("select * from  YearlyStatus where yr_mth<"+yr_mth+" and br_code="+br_code+" and post_ind='F' order by yr_mth limit 1");
		   		if(rs.next()){
		   			ret[0][0]="PF";
		   			ret[0][1]=rs.getString("yr_mth");
		   			return ret;
		   		}
		   		rs=stmt.executeQuery("select * from  YearlyStatus where yr_mth<"+yr_mth+" and br_code="+br_code+" and year_close='F' order by yr_mth limit 1");
		   		if(rs.next()){
		   			ret[0][0]="YN";
		   			ret[0][1]=rs.getString("yr_mth");
		   			return ret;
		   		}
		   		rs=stmt.executeQuery("select * from YearlyStatus where yr_mth="+yr_mth+" and year_close='T' and br_code="+br_code+" ");
		   		if(rs.next()){
		   			ret[0][0]="YT";
		   			ret[0][1]="YT";
		   			return ret;
		   		}
		   		rs=stmt.executeQuery("select * from YearlyStatus where yr_mth="+yr_mth+" and post_ind='T' and br_code="+br_code+" ");
		   		if(rs.next()){
		   			ret[0][0]="PT";
		   			ret[0][1]="PT";
		   			return ret;
		   		}   
		   		rs=stmt.executeQuery("select * from  YearlyStatus where yr_mth<"+yr_mth+" and br_code="+br_code+" and year_close='T' order by yr_mth desc limit 1");
		   		if(rs.next()){
		   			str_year=rs.getString("yr_mth").substring(0,4);
	         		str_mth=rs.getString("yr_mth").substring(4,6);
	         		str_date="01"+"/"+str_mth+"/"+str_year;
	         		str_frm_year=yr_mth.substring(0,4);
	         		str_frm_mth=yr_mth.substring(4,6);
	         		str_frm_date="01"+"/"+str_frm_mth+"/"+str_frm_year;
	         		int_count=0;
	         		while(int_count<=12){
	         			if(str_date.equals(str_frm_date))
	         				break;
	         			str_date=Validations.addNoOfMonths(str_date,1);
	         			++int_count;
	         		}
	         		if(!(str_date.equals(str_frm_date))){
	         			ret[0][0]="YN1";
			   			ret[0][1]=str_date;
			   			return ret;
	         		}
	         		else{
	         			ret[0][0]="OK";
			   			ret[0][1]=str_year+str_mth;
			   			return ret;
	         		}
		   	   }
		   	 }
		   	else{
		   		System.out.println(" no entries in Yearly Status");
		   		rs=stmt.executeQuery("select * from MonthlyStatus where yr_mth<"+yr_mth+" order by yr_mth");
		   		rs.next();
	  			str_year=rs.getString("yr_mth").substring(0,4);
         		str_mth=rs.getString("yr_mth").substring(4,6);
         		str_date="01"+"/"+str_mth+"/"+str_year;
         		str_frm_year=yr_mth.substring(0,4);
         		str_frm_mth=yr_mth.substring(4,6);
         		str_frm_date="01"+"/"+str_frm_mth+"/"+str_frm_year;
         		int_count=0;
         		while(int_count<=12){
         			if(str_date.equals(str_frm_date))
         				break;
         			str_date=Validations.addNoOfMonths(str_date,1);
         			++int_count;
         		}
         		if(!(str_date.equals(str_frm_date))){
         			ret[0][0]="MN1";
		   			ret[0][1]=str_date;
		   			return ret;
         		}
         		else{
         			ret[0][0]="OK";
		   			ret[0][1]=str_year+str_mth;
		   			return ret;
         		}
		   }
		}catch(Exception se){se.printStackTrace();}
	    finally{
	        try{
	            conn.close();
	        }catch(SQLException se){se.printStackTrace();}
	    }
   	return ret;
   	}
	
	public String[][] checkYearStatusBranch(String yr_mth,String br_location){
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null,rs1=null,rs_br_name=null;
		String ret[][]=new String[1][2];
		String str_year=null,str_mth=null;
//		String prev_year=null,prev_mth=null;
		int br_code=0,int_count=0;;
		String str_frm_year=null,str_frm_mth=null,str_frm_date=null,str_date=null;
		try{
		   	conn=getConnection();
		   	stmt=conn.createStatement();
		   	System.out.println(" i  am in year status ");
		   	System.out.println("year mth="+yr_mth);
		   	rs_br_name=stmt.executeQuery("select br_code from BranchMaster where br_name like '"+br_location+"' ");
			rs_br_name.next();
			br_code=rs_br_name.getInt("br_code");
			rs=stmt.executeQuery("select * from MonthlyConStatus where yr_mth<="+yr_mth+" and br_code="+br_code+" ");
			if(rs.next())
				System.out.println("have entries in MonthlyStatus ");
			else{
				ret[0][0]="CN";// no entries in Monthly Status
				ret[0][0]="CN";
				return ret;
			}
		    rs=stmt.executeQuery("select * from MonthlyConStatus where yr_mth<="+yr_mth+" and br_code="+br_code+" and post_ind='F' order by yr_mth limit 1 ");
		   	if(rs.next()){
		   		ret[0][0]="-2";// month not closed for yr_mth
		   		ret[0][1]=rs.getString("yr_mth");
		   		return ret;
		   	}
		   	rs=stmt.executeQuery("select * from MonthlyConStatus where yr_mth<="+yr_mth+" and br_code="+br_code+"  and post_ind='T' order by yr_mth desc limit 1");
		   	if(rs.next()){
		   		if(yr_mth.equals(rs.getString("yr_mth"))){// Ok from monthly status
		   			ret[0][0]="OK";
		   			ret[0][1]="OK";
		   			System.out.println(" monhtly Ok....");
		   		}
		   		else{
		   			ret[0][0]="NO";//monthly status not updated
		   			ret[0][1]=rs.getString("yr_mth");
		   			return ret;
		   		}
		   	}
		   	else{// no entires in monthly closed that are  closed 
		   		ret[0][0]="-1";
	   			ret[0][1]="-1";
	   			return ret;
		   	}
		   	rs1=stmt.executeQuery("select * from YearlyStatus where br_code="+br_code+" ");
		   	if(rs1.next()){
		   		rs=stmt.executeQuery("select * from  YearlyStatus where yr_mth<"+yr_mth+" and br_code="+br_code+" and post_ind='F' order by yr_mth limit 1");
		   		if(rs.next()){
		   			ret[0][0]="PF";
		   			ret[0][1]=rs.getString("yr_mth");
		   			return ret;
		   		}
		   		rs=stmt.executeQuery("select * from  YearlyStatus where yr_mth<"+yr_mth+" and br_code="+br_code+" and year_close='F' order by yr_mth limit 1");
		   		if(rs.next()){
		   			ret[0][0]="YN";
		   			ret[0][1]=rs.getString("yr_mth");
		   			return ret;
		   		}
		   		rs=stmt.executeQuery("select * from YearlyStatus where yr_mth="+yr_mth+" and year_close='T' and br_code="+br_code+" ");
		   		if(rs.next()){
		   			ret[0][0]="YT";
		   			ret[0][1]="YT";
		   			return ret;
		   		}
		   		rs=stmt.executeQuery("select * from YearlyStatus where yr_mth="+yr_mth+" and post_ind='T' and br_code="+br_code+" ");
		   		if(rs.next()){
		   			ret[0][0]="PT";
		   			ret[0][1]="PT";
		   			return ret;
		   		}   
		   		rs=stmt.executeQuery("select * from  YearlyStatus where yr_mth<"+yr_mth+" and br_code="+br_code+" and year_close='T' order by yr_mth desc limit 1");
		   		if(rs.next()){
		   			str_year=rs.getString("yr_mth").substring(0,4);
	         		str_mth=rs.getString("yr_mth").substring(4,6);
	         		str_date="01"+"/"+str_mth+"/"+str_year;
	         		str_frm_year=yr_mth.substring(0,4);
	         		str_frm_mth=yr_mth.substring(4,6);
	         		str_frm_date="01"+"/"+str_frm_mth+"/"+str_frm_year;
	         		int_count=0;
	         		while(int_count<=12){
	         			if(str_date.equals(str_frm_date))
	         				break;
	         			str_date=Validations.addNoOfMonths(str_date,1);
	         			++int_count;
	         		}
	         		if(!(str_date.equals(str_frm_date))){
	         			ret[0][0]="YN1";
			   			ret[0][1]=str_date;
			   			return ret;
	         		}
	         		else{
	         			ret[0][0]="OK";
			   			ret[0][1]=str_year+str_mth;
			   			return ret;
	         		}
		   	   }
		   	 }
		   	else{
		   		System.out.println(" no entries in Yearly Status");
		   		rs=stmt.executeQuery("select * from MonthlyConStatus where yr_mth<"+yr_mth+" and br_code="+br_code+" order by yr_mth");
		   		rs.next();
	  			str_year=rs.getString("yr_mth").substring(0,4);
         		str_mth=rs.getString("yr_mth").substring(4,6);
         		str_date="01"+"/"+str_mth+"/"+str_year;
         		str_frm_year=yr_mth.substring(0,4);
         		str_frm_mth=yr_mth.substring(4,6);
         		str_frm_date="01"+"/"+str_frm_mth+"/"+str_frm_year;
         		int_count=0;
         		while(int_count<=12){
         			if(str_date.equals(str_frm_date))
         				break;
         			str_date=Validations.addNoOfMonths(str_date,1);
         			++int_count;
         		}
         		if(!(str_date.equals(str_frm_date))){
         			ret[0][0]="MN1";
		   			ret[0][1]=str_date;
		   			return ret;
         		}
         		else{
         			ret[0][0]="OK";
		   			ret[0][1]=str_year+str_mth;
		   			return ret;
         		}
		   }
		}catch(Exception se){se.printStackTrace();}
	    finally{
	        try{
	            conn.close();
	        }catch(SQLException se){se.printStackTrace();}
	    }
   	return ret;
   	}
	/**
	 * Swapna
	 * @return
	 */
	public int checkDlyStatusForReport(String fromdate,String todate){
		   Connection conn=null;
		   Statement stmt=null;
		   ResultSet rs=null;
		   try{
		   	conn=getConnection();
		   	stmt=conn.createStatement();
		   	rs=stmt.executeQuery("select * from DailyStatus where trn_dt between '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"'  and post_ind='F' ");
		   	if(rs.next())
		   		return 1;
		   	else{
		   		rs=stmt.executeQuery("select * from DailyStatus where trn_dt between '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"'  and post_ind='T' ");
		   		if(rs.next())
		   			return 0;
		   		else
		   			return 1;
		   	}
		  }catch(Exception se){se.printStackTrace();}
		    finally{
		        try{
		            conn.close();
		        }catch(SQLException se){se.printStackTrace();}
		    }
		  return 1;
	}
	/**
	 * swapna
	 * @return
	 */
	public int checkMthStatusForReport(String fromdate,String todate){
		   Connection conn=null;
		   Statement stmt=null;
		   ResultSet rs=null;
		   try{
		   	conn=getConnection();
		   	stmt=conn.createStatement();
		   	rs=stmt.executeQuery("select * from MonthlyStatus where yr_mth between '"+convertToYYYYMM(fromdate)+"' and '"+convertToYYYYMM(todate)+"'  and post_ind='F' ");
		   	if(rs.next())
		   		return 1;
		   	else{
		   		rs=stmt.executeQuery("select * from MonthlyStatus where yr_mth between '"+convertToYYYYMM(fromdate)+"' and '"+convertToYYYYMM(todate)+"'  and post_ind='T' ");
		   		if(rs.next())
		   			return 0;
		   		else
		   			return 1;
		   	  }
		    } catch(Exception se){se.printStackTrace();}
		    finally{
		        try{
		            conn.close();
		        }catch(SQLException se){se.printStackTrace();}
		    }
		  return 1;
	}
	/**
	 * swapna
	 * @return
	 */
	public int checkDlyConStatusForReport(String fromdate,String todate,String br_code){
		   Connection conn=null;
		   Statement stmt=null,stmt1=null;
		   ResultSet rs=null,rs1=null;
		   try{
		   	conn=getConnection();
		   	stmt=conn.createStatement();
		   	stmt1=conn.createStatement();
		   	rs1=stmt1.executeQuery("select * from DailyConStatus where trn_dt between '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"'  and post_ind='F'  and  br_code="+br_code+" ");
		   	if(rs1.next())
		   		return 0;
		   	else{
		   		rs=stmt.executeQuery("select * from DailyConStatus where trn_dt between '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"'  and post_ind='T'  and  br_code="+br_code+" ");
		   		if(rs.next())
		   			return 0;
		   		else
		   			return 1;
		   	   }
		    }catch(Exception se){se.printStackTrace();}
		    finally{
		        try{
		            conn.close();
		        }catch(SQLException se){se.printStackTrace();}
		    }
		  return 1;
	}
	/**
	 * swapna
	 * @return
	 */
	public int checkMthConStatusForReport(String fromdate,String todate,String br_code){
		   Connection conn=null;
		   Statement stmt=null,stmt1=null;
		   ResultSet rs=null,rs1=null;
		   try{
		   	conn=getConnection();
		   	stmt=conn.createStatement();
		   	stmt1=conn.createStatement();
		   	rs1=stmt1.executeQuery("select * from MonthlyConStatus where yr_mth between '"+convertToYYYYMM(fromdate)+"' and '"+convertToYYYYMM(todate)+"'  and post_ind='F'  and br_code="+br_code+" ");
		   	if(rs1.next())
		   		return 1;
	        else{
	        	rs=stmt.executeQuery("select * from MonthlyConStatus where yr_mth between '"+convertToYYYYMM(fromdate)+"' and '"+convertToYYYYMM(todate)+"'  and post_ind='T'  and br_code="+br_code+" ");
	        	if(rs.next())
	        		return 0;
	        	else
	        		return 1;
	           }
		    }catch(Exception se){se.printStackTrace();}
		    finally{
		        try{
		            conn.close();
		        }catch(SQLException se){se.printStackTrace();}
		    }
		  return 1;
	}
	/**
	 * swapna
	 * @return
	 */
	public int closeMonth(int year,int month){
	    Connection conn=null;
	    PreparedStatement pstmt=null;
	    String mth=null;
	    try{
	        if(String.valueOf(month).length()==1)
	            mth="0"+month;
	        else
	        	mth=String.valueOf(month);
	        conn=getConnection();
	        pstmt=conn.prepareStatement("update MonthlyStatus set month_close='T' where yr_mth="+Integer.parseInt(String.valueOf(year)+mth)+"");
	        if(pstmt.executeUpdate()!=0)
	            return 1;
	        else
	            return 0;
	    }
	    catch(SQLException se){
	    	sessioncontext.setRollbackOnly();
	    	se.printStackTrace();
	    	}
	    finally{
	        try{
	            conn.close();
	        }catch(SQLException se){se.printStackTrace();}
	    }
	    return 0;
	}
	public String[][] getCreateFileData(){
		Connection conn=null;
	    Statement stmt=null;
	    ResultSet rs=null;
	    String gl_object[][]=null;
	    try{
	        conn=getConnection();
	        stmt=conn.createStatement(); 
	        rs=stmt.executeQuery("select 1 as br_code,'01/09/1996' as trn_dt,gl_type,gl_code,replace(gl_name,',',' ') as gl_name,0.00 as cash_dr,0.00 as cg_dr,0.00 as trf_dr,0.00 as cash_cr,0.00 as cg_cr,0.00 as trf_cr from GLMaster order by gl_type,gl_code;");
	        if(rs.next()){
	        	rs.last();
	        	gl_object=new String[rs.getRow()][11];
	        	rs.beforeFirst();
	        	rs.next();
	        	if(gl_object!=null){
					for(int i=0;i<gl_object.length;i++){
							for(int j=0;j<gl_object[i].length;j++){
								if(rs.getString(j+1)==null)
									gl_object[i][j]="null";
								else
									gl_object[i][j]=rs.getString(j+1);
							}
						rs.next();
					}
				}
	        }
	    }catch(SQLException se){
	    	sessioncontext.setRollbackOnly();
	    	se.printStackTrace();
	    	}
		    finally{
		        try{
		            conn.close();
		        }catch(SQLException se){se.printStackTrace();}
		    }
    return gl_object;
	}
	/**
	 * Swapna
	 * @return
	 */
	public int doMonthlyPosting(int year,int month,String br_location,String tml,String user,String datetime,String rec_type)
	{
	    Connection conn=null;
	    Statement stmt_amt=null,stmt=null;
	    ResultSet rs_amt=null,rs_br_name=null;
	    PreparedStatement pstmt_in=null,pstmt_up=null;
	    int br_code=0;
	    String mth=null;
        if(String.valueOf(month).length()==1)
            mth="0"+month;
        else 
        	mth=String.valueOf(month);
        
	    String yr_mth=year+mth;
	    System.out.println("year mth in Monthly Posting="+yr_mth);
	    try{
	        conn=getConnection();
	        stmt_amt=conn.createStatement();
	        stmt=conn.createStatement();
	        stmt.executeUpdate("delete from MonthlySummary where yr_mth="+yr_mth+" ");
	        stmt.executeUpdate("delete from MonthlyStatus where yr_mth="+yr_mth+" " );
	        rs_br_name=stmt.executeQuery("select br_code from BranchMaster where br_name like '"+br_location+"' ");
			rs_br_name.next();
			br_code=rs_br_name.getInt("br_code");
	        pstmt_in=conn.prepareStatement("insert into MonthlySummary values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	        if(!(rec_type.equalsIgnoreCase("o"))){
	        	pstmt_up=conn.prepareStatement("insert into MonthlyStatus values(?,?,?,?,?,?)");
	        	pstmt_up.setInt(1,Integer.parseInt(String.valueOf(year)+mth));  
		           pstmt_up.setString(2,"T");
		           pstmt_up.setString(3,"F");
		           pstmt_up.setString(4,tml);
		           pstmt_up.setString(5,user);
		           pstmt_up.setString(6,datetime);
		           
		           if(pstmt_up.executeUpdate()!=0)
		            System.out.println("Updated MonthlyStatus");
		           else
		               System.out.println(" Not UPdated MS..");
	        }

	        rs_amt=stmt_amt.executeQuery("select ds.gl_type,ds.gl_code,sum(ds.cash_dr) csh_dr,sum(ds.cg_dr) cg_dr, sum(ds.trf_dr) trf_dr,sum(ds.cash_cr) csh_cr,sum(ds.cg_cr) cg_cr, sum(ds.trf_cr) trf_cr from DailySummary ds,DailyStatus dst where year(dst.trn_dt)="+year+" and month(dst.trn_dt)="+month+" and dst.post_ind='T' and ds.trn_dt=dst.trn_dt group by gl_type,gl_code order by 1,2"); 
	        while(rs_amt.next())
		    {
	          pstmt_in.setInt(1,Integer.parseInt(String.valueOf(year)+mth));
	          pstmt_in.setString(2,"T");
              pstmt_in.setString(3,rs_amt.getString("gl_type"));
              pstmt_in.setInt(4,rs_amt.getInt("gl_code"));
              pstmt_in.setDouble(5,rs_amt.getDouble("csh_dr"));
              pstmt_in.setDouble(6,rs_amt.getDouble("cg_dr"));
              pstmt_in.setDouble(7,rs_amt.getDouble("trf_dr"));
	          pstmt_in.setDouble(8,rs_amt.getDouble("csh_cr"));
	          pstmt_in.setDouble(9,rs_amt.getDouble("cg_cr"));
	          pstmt_in.setDouble(10,rs_amt.getDouble("trf_cr"));
	          pstmt_in.setString(11,tml);
	          pstmt_in.setString(12,user);
	          pstmt_in.setString(13,datetime);
		      pstmt_in.setString(14,tml);
	          pstmt_in.setString(15,user);
	          pstmt_in.setString(16,datetime);
	          pstmt_in.executeUpdate();
            }
	        stmt.executeUpdate("update MonthlyConStatus set post_ind='F' where yr_mth="+yr_mth+"  and br_code="+br_code+" ");
	        stmt.executeUpdate("update YearlyStatus set post_ind='F',year_close='F'  where yr_mth="+yr_mth+"  and br_code="+br_code+" ");
	        return 1;
	            
	    }
	    catch(SQLException se){
	    	sessioncontext.setRollbackOnly();
	    	se.printStackTrace();
	    	}
	    finally{
	        try{
	            conn.close();
	        }catch(SQLException se){se.printStackTrace();}
	    }
	    return 0;
	}
	/**
	 * Swapna 
	 * @param fromdate
	 * @param todate
	 * @param br_location
	 * @return 3	= posting not done for  privous date
	 * @return 1	=you have entries but post_ind false
	 * @return 0	=You have Entries do you want to overwrite
	 * @return 2	=difference is more you cannot do posting
	 * @return -1	=no entries in  DailyConStatus fo paricular branch 
	 * 
	 */
	public String[][] checkDlyConStatusForMonth(String fromdate,String todate,String br_location){
		 Connection conn=null;
	     Statement stmt=null,stmt1=null;
	     //PreparedStatement pstmt=null,pstmt_del=null;
	     ResultSet rs_br_name=null,rs=null,rs1=null;
	     String ret[][]=new String[1][3];
	     int br_code=0;
	     try{
	          conn=getConnection();
	          stmt=conn.createStatement();
	          stmt1=conn.createStatement();
	          rs_br_name=stmt.executeQuery("select br_code from BranchMaster where br_name like '"+br_location+"' ");
			  rs_br_name.next();
			  br_code=rs_br_name.getInt("br_code");
			  System.out.println("br_code="+br_code);
			  int month=0;
			  int year=0;
			  int date=0;
			  StringTokenizer st=new StringTokenizer(fromdate,"/");
			  date=Integer.parseInt(st.nextToken());
			  month=Integer.parseInt(st.nextToken());
			  year=Integer.parseInt(st.nextToken());
			  System.out.println("year="+year);
			  System.out.println("month="+month);
			  rs=stmt.executeQuery("select year(trn_dt),trn_dt from DailyConStatus where year(trn_dt)<="+year+"  and br_code="+br_code+" order by  trn_dt  limit 1");
		      if(rs.next()){
		    	if(year==rs.getInt(1)){
		    		rs1=stmt1.executeQuery("select trn_dt from DailyConStatus where month(trn_dt)<="+month+"  and year(trn_dt)="+year+"  and br_code="+br_code+" order by  trn_dt  limit 1");
		    		if(rs1.next())
		    	   		System.out.println("greater than minimum month in Dialy Con Status ");
		    		else{
		    	   		ret[0][0]="NO";
		    	   		ret[0][1]=rs.getString("trn_dt");
		    	   		return ret;
		    		}
		    	}
		    	else
		    		System.out.println("greater than minimum date in Dialy Con Status ");
		    }
		    else{
		    	rs=stmt.executeQuery("select * from DailyConStatus where br_code="+br_code+" order by trn_dt limit 1");
		    	if(rs.next()){// date less than minimum date;
		    		ret[0][0]="NO";
		    		ret[0][1]=rs.getString("trn_dt");
		    		return ret;
		    	}
		    	else{
		    		ret[0][0]="-1";// no entries;
		    		ret[0][1]="-1";
		    		return ret;
		   		}
		   	}
			  rs=stmt.executeQuery("select * from DailyConStatus where  trn_dt<'"+Validations.convertYMD(fromdate)+"' and  br_code="+br_code+" and post_ind='F' order by trn_dt  limit 1");
			  if(rs.next()){
				ret[0][0]="3";// posting not done for previous date
      			ret[0][1]=Validations.convertDMY(rs.getString("trn_dt"));
      			return ret;
			  }
			  rs=stmt.executeQuery(" select * from DailyConStatus where  br_code="+br_code+" and trn_dt between '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"'");
			  if(rs.next()){
			  	rs1=stmt.executeQuery(" select * from DailyConStatus where  br_code="+br_code+" and trn_dt between '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"' and post_ind='F' ");
			  	if(rs1.next()){
			  		ret[0][0]="1";// entries there but post_ind false .. continue
          			ret[0][1]=Validations.convertDMY(rs1.getString("trn_dt"));
          			return ret;
			  	}
			  	rs1=stmt.executeQuery(" select count(*),trn_dt from DailyConStatus where  br_code="+br_code+" and trn_dt between '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"' and post_ind='T' group by br_code order by trn_dt ");
			  	if(rs1.next()){
			  		ret[0][0]="0";//  posting done do you want to overwite
          			ret[0][1]=Validations.convertDMY(rs1.getString("trn_dt"));
          			ret[0][2]=rs1.getString(1);
          			return ret;
			  	}
			  }
			  rs=stmt.executeQuery("select * from DailyConStatus where  br_code="+br_code+" and post_ind='T' order by trn_dt desc limit 1");
	          	if(rs.next()){
	          		System.out.println("date in DlyConStatus="+rs.getString("trn_dt"));
	          		if(Validations.dayCompare(Validations.convertDMY(rs.getString("trn_dt")),fromdate)<=0){//over write
	          			rs1=stmt1.executeQuery("select * from DailyConStaus where  br_code="+br_code+" order by trn_dt asc limit 1");
	          			if(rs1.next()){
	          				ret[0][0]="OP";
	          				ret[0][1]=Validations.convertDMY(rs.getString("trn_dt"));
	          				return ret;
	          			}
	          		}
	          		else if(Validations.dayCompare(Validations.convertDMY(rs.getString("trn_dt")),fromdate)==1){//allow
	          			ret[0][0]="1";
	          			ret[0][1]=Validations.convertDMY(rs.getString("trn_dt"));
	          			return ret;
	          		}
	          		else if(Validations.dayCompare(Validations.convertDMY(rs.getString("trn_dt")),fromdate)>1){//diff is more than one day you cannot
	          			ret[0][0]="2";
	          			ret[0][1]=Validations.convertDMY(rs.getString("trn_dt"));
	          			return ret;
	          		}
	          	}
	          	else {
          			ret[0][0]="-1";// not entries
          			ret[0][1]="-1";
          			return ret;
          		}
	        }catch(SQLException se){se.printStackTrace();}
		    finally{
		        try{
		            conn.close();
		        }catch(SQLException se){se.printStackTrace();}
		    }
		    ret[0][0]="-2";
		    ret[0][1]="-2";
		    return ret;
	}
	public String[][] checkDlyConStatus(String fromdate,String todate,String br_location){
		 Connection conn=null;
	     Statement stmt=null,stmt1=null;
	     //PreparedStatement pstmt=null,pstmt_del=null;
	     ResultSet rs_br_name=null,rs=null,rs1=null;
	     String ret[][]=new String[1][3];
	     int br_code=0;
	     try{
	          conn=getConnection();
	          stmt=conn.createStatement();
	          stmt1=conn.createStatement();
	          rs_br_name=stmt.executeQuery("select br_code from BranchMaster where br_name like '"+br_location+"' ");
			  rs_br_name.next();
			  br_code=rs_br_name.getInt("br_code");
			  System.out.println("br_code="+br_code);
			  int month=0;
			  int year=0;
			  int date=0;
			  StringTokenizer st=new StringTokenizer(fromdate,"/");
			  date=Integer.parseInt(st.nextToken());
			  month=Integer.parseInt(st.nextToken());
			  year=Integer.parseInt(st.nextToken());
			  System.out.println("year="+year);
			  System.out.println("month="+month);
			  rs=stmt.executeQuery("select * from DailyConStatus where trn_dt<='"+Validations.convertYMD(fromdate)+"' and br_code="+br_code+"  ");
		    	if(rs.next())
		    		System.out.println("greater than minimum date in Dialy Con Status ");
		    	else{
		    		rs=stmt.executeQuery("select * from DailyConStatus where br_code="+br_code+" order by trn_dt limit 1");
		    		if(rs.next()){// date less than minimum date;
		    			ret[0][0]="NO";
		    			ret[0][1]=rs.getString("trn_dt");
		    			return ret;
		    		}
		    		else{
		    			ret[0][0]="-1";
		    			ret[0][1]="0";
		    			return ret;
		    		}
		    	}
			  rs=stmt.executeQuery("select * from DailyConStatus where  trn_dt<'"+Validations.convertYMD(fromdate)+"' and  br_code="+br_code+" and post_ind='F' order by trn_dt  limit 1");
			  if(rs.next()){
				ret[0][0]="3";// posting not done for previous date
     			ret[0][1]=Validations.convertDMY(rs.getString("trn_dt"));
     			return ret;
			  }
			  rs=stmt.executeQuery(" select * from DailyConStatus where  br_code="+br_code+" and trn_dt between '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"'");
			  if(rs.next()){
			  	rs1=stmt.executeQuery(" select * from DailyConStatus where  br_code="+br_code+" and trn_dt between '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"' and post_ind='F' ");
			  	if(rs1.next()){
			  		ret[0][0]="1";// entries there but post_ind false .. continue
         			ret[0][1]=Validations.convertDMY(rs1.getString("trn_dt"));
         			return ret;
			  	}
			  	rs1=stmt.executeQuery(" select count(*),trn_dt from DailyConStatus where  br_code="+br_code+" and trn_dt between '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"' and post_ind='T' group by br_code order by trn_dt ");
			  	if(rs1.next()){
			  		ret[0][0]="0";//  posting done do you want to overwite
         			ret[0][1]=Validations.convertDMY(rs1.getString("trn_dt"));
         			return ret;
			  	}
			  }
			  rs=stmt.executeQuery("select * from DailyConStatus where  br_code="+br_code+" and post_ind='T' order by trn_dt desc limit 1");
	          	if(rs.next()){
	          		System.out.println("date in DlyConStatus="+rs.getString("trn_dt"));
	          		if(Validations.dayCompare(Validations.convertDMY(rs.getString("trn_dt")),fromdate)<=0){//over write
	          			rs1=stmt1.executeQuery("select * from DailyConStaus where  br_code="+br_code+" order by trn_dt asc limit 1");
	          			if(rs1.next()){
	          				ret[0][0]="OP";
	          				ret[0][1]=Validations.convertDMY(rs.getString("trn_dt"));
	          				return ret;
	          			}
	          		}
	          		else if(Validations.dayCompare(Validations.convertDMY(rs.getString("trn_dt")),fromdate)==1){//allow
	          			ret[0][0]="1";
	          			ret[0][1]=Validations.convertDMY(rs.getString("trn_dt"));
	          			return ret;
	          		}
	          		else if(Validations.dayCompare(Validations.convertDMY(rs.getString("trn_dt")),fromdate)>1){//diff is more than one day you cannot
	          			ret[0][0]="2";
	          			ret[0][1]=Validations.convertDMY(rs.getString("trn_dt"));
	          			return ret;
	          		}
	          	}
	          	else {
         			ret[0][0]="-1";// not entries
         			ret[0][1]="-1";
         			return ret;
         		}
	        }catch(SQLException se){se.printStackTrace();}
		    finally{
		        try{
		            conn.close();
		        }catch(SQLException se){se.printStackTrace();}
		    }
		    ret[0][0]="-2";
		    ret[0][1]="-2";
		    return ret;
	}
	/**
	 * Swapna
	 * @param from_mth	=fromdate
	 * @param to_mth	= todate
	 * @return PF	= posting not done for provious date 
	 * @return PT   = Posting done do you to overwrite?
	 * @return OK	= allow for Posting
	 * @return NO	= Diff is more cannot
	 * @return CN	= Continue
	 * @return 1	= no entries in MonthlyStatus allow for posting  
	 */
	public String[][] checkMthConStatus(String from_mth,String to_mth,String br_location){
		 Connection conn=null;
	     Statement stmt=null;
	     ResultSet rs_br_name=null,rs=null,rs1=null;
	     String ret[][]=new String[1][2];
	     int frm_mth=convertToYYYYMM(from_mth),int_yrmth=0;
	     String str_mth=null,str_year=null,str_yrmth;
	     int int_year=0,int_mth=0,br_code=0;
	     try{
	          conn=getConnection();
	          stmt=conn.createStatement();
	          rs_br_name=stmt.executeQuery("select br_code from BranchMaster where br_name like '"+br_location+"' ");
			  rs_br_name.next();
			  br_code=rs_br_name.getInt("br_code");
			  rs=stmt.executeQuery("select * from MonthlyConsolidation where yr_mth<"+frm_mth+" and br_code="+br_code+" and record_type='O' order by yr_mth  limit 1");
			  if(rs.next()){
			  	System.out.println("opeining balance found in Month conolodation");
			  }
			  else{
				ret[0][0]="OP";
     			ret[0][1]="OP";
     			return ret;
			  }
			  rs=stmt.executeQuery("select * from MonthlyConStatus where br_code="+br_code+" ");
			  if(rs.next()){
			  	rs=stmt.executeQuery("select * from MonthlyConStatus where br_code="+br_code+" and yr_mth<='"+frm_mth+"' order by yr_mth limit 1");
			  	if(rs.next())
			  		System.out.println("greater than start month");
			  	else{
			  		ret[0][0]="-1";// date should be  greater than miminum date
          			ret[0][1]=" ";
          			return ret;
			  	}
			  }
			  else{
			  	ret[0][0]="1";// No Entries
      			ret[0][1]="1";
      			return ret;
			  }
			  rs=stmt.executeQuery("select * from MonthlyConStatus where yr_mth<"+frm_mth+" and br_code="+br_code+" and post_ind='F' order by yr_mth  limit 1");
			  if(rs.next()){
				ret[0][0]="PF";
     			ret[0][1]=rs.getString("yr_mth");
     			return ret;
			  }
			  rs1=stmt.executeQuery("select * from MonthlyConStatus where yr_mth between "+convertToYYYYMM(from_mth)+" and "+convertToYYYYMM(to_mth)+" and br_code="+br_code+" ");
			  if(rs1.next()){
			  	rs=stmt.executeQuery("select * from MonthlyConStatus where yr_mth between "+convertToYYYYMM(from_mth)+" and "+convertToYYYYMM(to_mth)+" and br_code="+br_code+" and post_ind='T' ");
			  	if(rs.next()){
			  		ret[0][0]="PT";// posting has done do you to overwrite
			  		ret[0][1]=rs.getString("yr_mth");
			  		return ret;
			  	}
			  	rs=stmt.executeQuery("select * from MonthlyConStatus where yr_mth between "+convertToYYYYMM(from_mth)+" and "+convertToYYYYMM(to_mth)+" and br_code="+br_code+" and post_ind='F' ");
			  	if(rs.next()){
			  		ret[0][0]="CN";// continue
			  		ret[0][1]="CN";
			  		return ret;
			  	}
			  }
	          rs=stmt.executeQuery("select * from MonthlyConStatus where  br_code="+br_code+" and post_ind='T' order by yr_mth desc limit 1");
	         	if(rs.next()){
	         		str_year=rs.getString("yr_mth").substring(0,4);
	         		str_mth=rs.getString("yr_mth").substring(4,6);
	         		System.out.println("month con status="+rs.getInt("yr_mth"));
	         		if(rs.getInt("yr_mth")>=frm_mth){//over write
	         			ret[0][0]="0";
	         			ret[0][1]=rs.getString("yr_mth");
	         			return ret;
	         		}
	         		if(rs.getInt("yr_mth")<frm_mth){
	         			if(str_mth.equals("12")){
	         				int_year=Integer.parseInt(str_year);
	         				int_year++;
	         				int_mth=1;
	         				str_yrmth=String.valueOf(int_year)+String.valueOf(int_mth);
	         			}
	         			else{
	         				int_yrmth=rs.getInt("yr_mth");
	         				int_yrmth++;
	         				str_yrmth=String.valueOf(int_yrmth);
	         			}
	         			if(Integer.parseInt(str_yrmth)==frm_mth){//this is next month you can continue
	         				ret[0][0]="OK";
	         				ret[0][1]="OK";
	         				return ret;
	         			}
	         			else{// diff is more you cannot continue;
	         				ret[0][0]="NO";
	         				ret[0][1]=rs.getString("yr_mth");
	         				return ret;
	         			}
	         		}
	         	}
	         	else{
	         		ret[0][0]="1";// no entries in MonthlyConStatus for paricular br_code
	         		ret[0][1]="1";
	         		return ret;
	         	}
	     }catch(SQLException se){se.printStackTrace();}
		    finally{
		        try{
		            conn.close();
		        }catch(SQLException se){se.printStackTrace();}
		    }
		    ret[0][0]="-2";
		    ret[0][1]="-2";
		    return ret;
	}
public int checkForHoliday(String date,String todate,String br_location,String user,String tml,String datetime){
		Connection conn=null;
	     Statement stmt=null;//,stmt_name=null;
	     PreparedStatement pstmt=null;//,pstmt_del=null;
	     ResultSet rs_data=null,rs_br_name=null;
	     int br_code=-1,ret=-1;;
	     String tempdate=null;;
	     
	     try{
	     	  int days=Validations.dayCompare(Validations.addDays(date,1),todate);
	     	  tempdate=Validations.addDays(date,1);
	     	  //tempdate=date;
	          conn=getConnection();
	          stmt=conn.createStatement();
	          rs_br_name=stmt.executeQuery("select br_code from BranchMaster where br_name like '"+br_location+"' ");
			  rs_br_name.next();
			  br_code=rs_br_name.getInt(1);
			  System.out.println("date="+date);
			  System.out.println("todate="+todate);
			  rs_data=stmt.executeQuery("select * from HolidayMaster where concat(right(date,4),'-',mid(date,locate('/',date)+1,(locate('/',date,4)-locate('/',date)-1)),'-',left(date,locate('/',date)-1)) between '"+Validations.convertYMD(date)+"' and '"+Validations.convertYMD(todate)+"'  and br_code="+br_code+" ");
			  if(rs_data.next()){
			  	rs_data.last();
			  	System.out.println("days="+days);
			  	System.out.println("diff="+rs_data.getRow());
			  	if(rs_data.getRow()==days){
			  	  rs_data.beforeFirst();
			  	  while(Validations.dayCompare(tempdate,todate)>0){
			  	  	pstmt=conn.prepareStatement("insert into DailyConStatus values(?,?,?,?,?,?)");
			  	  	pstmt.setString(1,Validations.convertYMD(tempdate));
			  	  	pstmt.setString(2,"T");
			  	  	pstmt.setInt(3,br_code);
			  	  	pstmt.setString(4,tml);
			  	  	pstmt.setString(5,user);
			  	  	pstmt.setString(6,datetime);
			  	  	pstmt.executeUpdate();
			  	  	System.out.println("tempdate="+tempdate);
			  	  	tempdate=Validations.addDays(tempdate,1);
			  	  	ret=1;
			  	  }
			  }
			  else
			  	ret=-1;
			}
		  else
		  		ret=-1;
	     }catch(Exception se){
	    	 sessioncontext.setRollbackOnly();
	    	 se.printStackTrace();
	    	 }
		    finally{
		        try{
		            conn.close();
		        }catch(SQLException se){se.printStackTrace();}
		    }
	    return ret;
	}
	/**
	 * Swapna
	 * @return
	 */
	 public int DailyConPosting(String frm_dt,String to_dt,String branch,String user,String tml,String datetime)
	 {
	     Connection conn=null;
	     Statement stmt=null;//,stmt_name=null;
	     PreparedStatement pstmt=null;//,pstmt_del=null;
	     ResultSet rs_data=null,rs_br_name=null;
	     String  temp_frm_dt=frm_dt;
	     int br_code=-1;
	     try{
	          conn=getConnection();
	          stmt=conn.createStatement();
	          rs_br_name=stmt.executeQuery("select br_code from BranchMaster where br_name like '"+branch+"' ");
			  rs_br_name.next();
			  br_code=rs_br_name.getInt(1);
	          stmt.executeUpdate("delete from DailyConsolidation where trn_dt between '"+Validations.convertYMD(frm_dt)+"'  and '"+Validations.convertYMD(to_dt)+"' and br_code="+br_code+" ");
	          stmt.executeUpdate("delete from DailyConStatus where trn_dt between '"+Validations.convertYMD(frm_dt)+"'  and '"+Validations.convertYMD(to_dt)+"' and br_code="+br_code+" ");
	          pstmt=conn.prepareStatement("insert into DailyConStatus values(?,?,?,?,?,?)");
	          pstmt.setString(2,"T");
	          pstmt.setInt(3,br_code);
              pstmt.setString(4,tml);
              pstmt.setString(5,user);
              pstmt.setString(6,datetime);
	          while(Validations.dayCompare(temp_frm_dt,to_dt)>=0){
	          	pstmt.setString(1,Validations.convertYMD(temp_frm_dt));
	          	temp_frm_dt=Validations.addDays(temp_frm_dt,1);
	          	pstmt.executeUpdate() ;
	          }
	          stmt.executeUpdate("update MonthlyConStatus set post_ind='F' where yr_mth between "+convertToYYYYMM(frm_dt)+" and  "+convertToYYYYMM(to_dt)+" and br_code="+br_code+" ");
	          stmt.executeUpdate("update YearlyStatus set post_ind='F',year_close='F' where yr_mth between "+convertToYYYYMM(frm_dt)+" and  "+convertToYYYYMM(to_dt)+" and br_code="+br_code+" ");
	          rs_data=stmt.executeQuery("select * from DailySummary where trn_dt between '"+Validations.convertYMD(frm_dt)+"'  and '"+Validations.convertYMD(to_dt)+"' order by gl_type,gl_code,trn_dt;");
	          if(rs_data.next()){
	          	rs_data.beforeFirst();	
	          pstmt=conn.prepareStatement("insert into DailyConsolidation values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	          while(rs_data.next())
	          {
	                 pstmt.setInt(1,br_code);
	                 pstmt.setDate(2,rs_data.getDate("trn_dt"));
	                 pstmt.setString(3,rs_data.getString("gl_type"));
	                 pstmt.setInt(4,rs_data.getInt("gl_code"));
	                 pstmt.setDouble(5,rs_data.getDouble("cash_dr"));
	                 pstmt.setDouble(6,rs_data.getDouble("cg_dr"));
	                 pstmt.setDouble(7,rs_data.getDouble("trf_dr"));
	                 pstmt.setDouble(8,rs_data.getDouble("cash_cr"));
	                 pstmt.setDouble(9,rs_data.getDouble("cg_cr"));
	                 pstmt.setDouble(10,rs_data.getDouble("trf_cr"));
	                 pstmt.setString(11,tml);
	                 pstmt.setString(12,user);
	                 pstmt.setString(13,datetime);
	                 pstmt.setString(14,tml);
	                 pstmt.setString(15,user); 
	                 pstmt.setString(16,datetime);
	                 System.out.println("Gl_type: "+rs_data.getString("gl_type")+ "GlCode : "+rs_data.getInt("gl_code"));
	                 if(pstmt.executeUpdate()!=0)
	                     System.out.println("Updated DailyCons");
	                 else
	                     System.out.println("Not Updated");
	             }
	          }
	          else 
	          		return 1;
	     }catch(Exception se){
	    	 sessioncontext.setRollbackOnly();
	    	 se.printStackTrace();
	    	 return -1;
	    	 }
		    finally{
		        try{
		            conn.close();
		        }catch(SQLException se){se.printStackTrace();}
		    }
		    return 1;
	 }
	 public  DlyConsObject[] getPostedData(String frm_dt,String to_dt,String br_location){
	     
	     Connection conn=null;
	     Statement st=null;
	     ResultSet rs=null;
	     String br_code=null;
	     DlyConsObject[] obj=null;
	     try{
	         conn=getConnection();
	         st=conn.createStatement();
	         br_location=br_location+"%";
	         System.out.println("br_location="+br_location);
	         rs=st.executeQuery("select br_code from BranchMaster where br_name like'"+br_location+"'");
	         rs.next();
	         br_code=rs.getString("br_code");
	         rs=st.executeQuery("select * from DailyConsolidation where trn_dt between '"+Validations.convertYMD(frm_dt)+"' and '"+Validations.convertYMD(to_dt)+"' and br_code='"+br_code+"' ");
	         rs.last();
	         
	         obj=new DlyConsObject[rs.getRow()];
	         
	         rs.beforeFirst();
	         
	         int r=0;
	         while(rs.next())
	         {
	          obj[r]=new DlyConsObject();
	          obj[r].setBr_code(rs.getInt("br_code"));
	          obj[r].setTrn_dt(rs.getString("trn_dt"));
	          obj[r].setGl_type(rs.getString("gl_type"));
	          obj[r].setGl_code(rs.getInt("gl_code"));
	          obj[r].setCsh_dr(rs.getDouble("cash_dr"));
	          obj[r].setCg_dr(rs.getDouble("cg_dr"));
	          obj[r].setTrf_dr(rs.getDouble("trf_dr"));
	          obj[r].setCsh_cr(rs.getDouble("cash_cr"));
	          obj[r].setCg_cr(rs.getDouble("cg_cr"));
	          obj[r].setTrf_cr(rs.getDouble("trf_cr"));
	          obj[r].setDe_tml(rs.getString("de_tml"));
	          obj[r].setDe_user(rs.getString("de_user"));
	          obj[r].setDe_dt_tm(rs.getString("de_date_time"));
	          obj[r].setVe_tml(rs.getString("ve_tml"));
	          obj[r].setVe_user(rs.getString("ve_user"));
	          obj[r].setVe_dt_tm(rs.getString("ve_date_time"));
	          
	          r++;   
	         }
	         
	         return obj;
	     }catch(SQLException se){se.printStackTrace();}
		    finally{
		        try{
		            conn.close();
		        }catch(SQLException se){se.printStackTrace();}
		    }
		    return obj;
	 }
	 /**
	 * Swapna
	 * @return
	 */
    public int doMonthlyConsolidation(String from_yrmth,String to_yrmth,String br_location,String uname,String utml,String datetime,String rec_type)
    {
         Connection conn=null;
	     Statement stmt=null,stmt_name=null;
	     PreparedStatement pstmt=null;//,pstmt_del=null;
	     ResultSet rs_data=null,rs_br_name=null;
	     int br_code=0;
	     String temp_yrmth=null;
	     try{
	          conn=getConnection();
	          stmt=conn.createStatement();
	          stmt_name=conn.createStatement();
	          rs_br_name=stmt_name.executeQuery("select br_code from BranchMaster where br_name='"+br_location+"'");
	          rs_br_name.next();
	          br_code=rs_br_name.getInt("br_code");
	          stmt.executeUpdate("delete from MonthlyConsolidation where yr_mth between "+convertToYYYYMM(from_yrmth)+" and "+convertToYYYYMM(to_yrmth)+" and br_code="+br_code+" ");
              rs_data=stmt.executeQuery("select  * from MonthlySummary where yr_mth between "+convertToYYYYMM(from_yrmth)+" and "+convertToYYYYMM(to_yrmth)+" order by gl_type,gl_code ");             
	             while(rs_data.next())
	             {
	             	pstmt=conn.prepareStatement("insert into MonthlyConsolidation values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	                 pstmt.setInt(2,rs_data.getInt("yr_mth"));
	                 pstmt.setString(3,rs_data.getString("record_type"));
	                 pstmt.setString(4,rs_data.getString("gl_type"));
	                 pstmt.setInt(5,rs_data.getInt("gl_code"));
	                 pstmt.setDouble(6,rs_data.getDouble("cash_dr"));
	                 pstmt.setDouble(7,rs_data.getDouble("cg_dr"));
	                 pstmt.setDouble(8,rs_data.getDouble("trf_dr"));
	                 pstmt.setDouble(9,rs_data.getDouble("cash_cr"));
	                 pstmt.setDouble(10,rs_data.getDouble("cg_cr"));
	                 pstmt.setDouble(11,rs_data.getDouble("trf_cr"));
	                 pstmt.setInt(1,rs_br_name.getInt("br_code")); 
	                 pstmt.setString(13,uname);
	                 pstmt.setString(12,utml);
	                 pstmt.setString(14,datetime);
	                 pstmt.setString(16,uname);
	                 pstmt.setString(15,utml);
	                 pstmt.setString(17,datetime); 
	 	            
	 	            
	                 System.out.println("Gl_type: "+rs_data.getString("gl_type")+ "GlCode : "+rs_data.getInt("gl_code"));
	                 pstmt.executeUpdate();
	             }
	             stmt.executeUpdate("delete from MonthlyConStatus where yr_mth between "+convertToYYYYMM(from_yrmth)+" and "+convertToYYYYMM(to_yrmth)+" and br_code="+br_code+" ");
	             temp_yrmth=from_yrmth;
	             System.out.println("temp yr_mth="+temp_yrmth);
	             System.out.println("temp yr_mth="+to_yrmth);
	             if(!(rec_type.equalsIgnoreCase("o"))){
	             	while(Validations.dayCompare(temp_yrmth,to_yrmth)>=0){
	             		pstmt=conn.prepareStatement("insert into MonthlyConStatus values(?,?,?,?,?,?)");
	             		pstmt.setInt(1,convertToYYYYMM(temp_yrmth));
	             		pstmt.setString(2,"T");
	             		pstmt.setInt(3,br_code); 
	             		pstmt.setString(4,utml);
	             		pstmt.setString(5,uname);
	             		pstmt.setString(6,datetime);
	             		temp_yrmth=Validations.addNoOfMonths(temp_yrmth,1);
	             		pstmt.executeUpdate();
	             }
	          }
	             
	             
	             return 1;
	     }catch(Exception se){
	    	 sessioncontext.setRollbackOnly();
	    	 se.printStackTrace();
	    	 }
		    finally{
		        try{
		            conn.close();
		        }catch(SQLException se){se.printStackTrace();}
		    }
		    return 0;   
        
    }
    public int doMonthlyConsolidationForBranch(GLObject globject[],String from_yrmth,String to_yrmth,String br_location,String uname,String utml,String datetime,String rec_type)
    {
         Connection conn=null;
	     Statement stmt=null,stmt_name=null;
	     PreparedStatement pstmt1=null;;
	     ResultSet rs_br_name=null;
	     int br_code=0,ret=0;
	     String temp_yrmth=null;
	     PreparedStatement[] pstmt =new PreparedStatement[globject.length];
	     System.out.println(" i am in monhtly consolidaiton");
	     
	     try
		 {
	          conn=getConnection();
	          stmt=conn.createStatement();
	          stmt_name=conn.createStatement();
	          rs_br_name=stmt_name.executeQuery("select br_code from BranchMaster where br_name='"+br_location+"'");
	          rs_br_name.next();
	          br_code=rs_br_name.getInt("br_code");
	          stmt.executeUpdate("delete from MonthlyConStatus where yr_mth between "+convertToYYYYMM(from_yrmth)+" and "+convertToYYYYMM(to_yrmth)+" and br_code="+br_code+" ");
	          temp_yrmth=from_yrmth;
	          System.out.println("temp yr_mth="+temp_yrmth);
	          System.out.println("temp yr_mth="+to_yrmth);
	          
	          if(!(rec_type.equalsIgnoreCase("o")))
	          {
	        	while(Validations.dayCompare(temp_yrmth,to_yrmth)>=0)
	        	{
	        		pstmt1=conn.prepareStatement("insert into MonthlyConStatus values(?,?,?,?,?,?)");
             		pstmt1.setInt(1,convertToYYYYMM(temp_yrmth));
             		pstmt1.setString(2,"T");
             		pstmt1.setInt(3,br_code); 
             		pstmt1.setString(4,utml);
             		pstmt1.setString(5,uname);
             		pstmt1.setString(6,datetime);
             		temp_yrmth=Validations.addNoOfMonths(temp_yrmth,1);
             		pstmt1.executeUpdate();
	        	}
	        }
	        stmt.executeUpdate("update YearlyStatus set post_ind='F',year_close='F' where yr_mth between  "+convertToYYYYMM(from_yrmth)+" and "+convertToYYYYMM(to_yrmth)+" and br_code="+br_code+" ");  
	          
	        stmt_name.executeUpdate("delete from MonthlyConsolidation where yr_mth between "+convertToYYYYMM(from_yrmth)+" and "+convertToYYYYMM(to_yrmth)+" and br_code="+br_code+" ");
	        for(int i=0;i<globject.length;i++)
		    {
		            pstmt[i]=conn.prepareStatement("insert into MonthlyConsolidation values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		            pstmt[i].setInt(1,globject[i].getBr_code());
		            pstmt[i].setString(2,globject[i].getFrom_dt());
		            pstmt[i].setString(3,globject[i].getRecordType());
		            pstmt[i].setString(4,globject[i].getGlType());
		            pstmt[i].setString(5,globject[i].getGlCode());
		            pstmt[i].setDouble(6,globject[i].getCash_dr());
		            pstmt[i].setDouble(7,globject[i].getCg_dr());
		            pstmt[i].setDouble(8,globject[i].getTrf_dr());
		            pstmt[i].setDouble(9,globject[i].getCash_cr());
		            pstmt[i].setDouble(10,globject[i].getCg_cr());
		            pstmt[i].setDouble(11,globject[i].getTrf_cr());
		            pstmt[i].setString(12,globject[i].getDeTml());
		            pstmt[i].setString(13,globject[i].getDeUser());
		            pstmt[i].setString(14,globject[i].getDeDate());
		            pstmt[i].setString(15,globject[i].getVeTml());
		            pstmt[i].setString(16,globject[i].getVeUser());
		            pstmt[i].setString(17,globject[i].getVeDate());
		            
		            if(pstmt[i].executeUpdate()==0)
		            {
		            	System.out.println("ret = 0");
		                ret=0;
		                throw new SQLException("Montly Consolidation insertion problem");
		            }
		            else
		            {
		                ret=2;
		                System.out.println("monthly consolidaiton updated");
		            }
		        }
	        
		        /*for(int j=0;j<pstmt.length;j++)
		        {
		            if(pstmt[j].executeUpdate()==0)
		            {
		            	System.out.println("ret = 0");
		                ret=0;
		                throw new SQLException("Montly Consolidation insertion problem");
		            }
		            else
		            {
		                ret=1;
		                System.out.println("monthly consolidaiton updated");
		            }
		        }*/
		        
		        System.out.println("ret = "+ret);
		        
		        if(ret==1)
		        	return ret;
		  }
	     catch(Exception se)
		 {
	    	 sessioncontext.setRollbackOnly();
	     	se.printStackTrace();
	     }
	       finally
		   {
		        try
				{
		            conn.close();
		        }
		        catch(SQLException se)
				{
		        	se.printStackTrace();
		        }
		    }
	       
	       return ret;
    }
    public int doDailyConsolidationForBranch(GLObject globject[],String frm_dt,String to_dt,String br_location,String uname,String utml,String datetime)
    {
         Connection conn=null;
	     Statement stmt=null,stmt_name=null;
	     PreparedStatement pstmt1=null;
	     ResultSet rs_br_name=null;
	     int br_code=0,ret=0;
	     String temp_frm_dt=frm_dt;
	     PreparedStatement[] pstmt =new PreparedStatement[globject.length];
	     try{
	          conn=getConnection();
	          stmt=conn.createStatement();
	          stmt_name=conn.createStatement();
	          rs_br_name=stmt_name.executeQuery("select br_code from BranchMaster where br_name='"+br_location+"'");
	          rs_br_name.next();
	          br_code=rs_br_name.getInt("br_code");
	          stmt.executeUpdate("delete from DailyConsolidation where trn_dt between '"+Validations.convertYMD(frm_dt)+"'  and '"+Validations.convertYMD(to_dt)+"' and br_code="+br_code+" ");
	          for(int i=0;i<globject.length;i++)
		      {
	          	
		            pstmt[i]=conn.prepareStatement("insert into DailyConsolidation values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		            
		            pstmt[i].setInt(1,globject[i].getBr_code());
		            pstmt[i].setString(2,globject[i].getFrom_dt());
		            pstmt[i].setString(3,globject[i].getGlType());
		            pstmt[i].setString(4,globject[i].getGlCode());
		            pstmt[i].setDouble(5,globject[i].getCash_dr());
		            pstmt[i].setDouble(6,globject[i].getCg_dr());
		            pstmt[i].setDouble(7,globject[i].getTrf_dr());
		            pstmt[i].setDouble(8,globject[i].getCash_cr());
		            pstmt[i].setDouble(9,globject[i].getCg_cr());
		            pstmt[i].setDouble(10,globject[i].getTrf_cr());
		            pstmt[i].setString(11,globject[i].getDeTml());
		            pstmt[i].setString(12,globject[i].getDeUser());
		            pstmt[i].setString(13,globject[i].getDeDate());
		            pstmt[i].setString(14,globject[i].getVeTml());
		            pstmt[i].setString(15,globject[i].getVeUser());
		            pstmt[i].setString(16,globject[i].getVeDate());
		        }
		        for(int j=0;j<pstmt.length;j++){
		            if(pstmt[j].executeUpdate()==0){
		                ret=0;
		                throw new SQLException("Daily Consolidation insertion problem");
		            }
		            else
		                ret=2;
		        }
		        stmt.executeUpdate("delete from DailyConStatus where trn_dt between '"+Validations.convertYMD(frm_dt)+"'  and '"+Validations.convertYMD(to_dt)+"' and br_code="+br_code+" ");
		        pstmt1=conn.prepareStatement("insert into DailyConStatus values(?,?,?,?,?,?)");
		        pstmt1.setString(2,"T");
		        pstmt1.setInt(3,br_code);
	            pstmt1.setString(4,utml);
	            pstmt1.setString(5,uname);
	            pstmt1.setString(6,datetime);
		        while(Validations.dayCompare(temp_frm_dt,to_dt)>=0){
		          	pstmt1.setString(1,Validations.convertYMD(temp_frm_dt));
		          	temp_frm_dt=Validations.addDays(temp_frm_dt,1);
		          	pstmt1.executeUpdate() ;
		          }
		        stmt.executeUpdate("update YearlyStatus set post_ind='F',year_close='F' where yr_mth between  "+convertToYYYYMM(frm_dt)+" and "+convertToYYYYMM(to_dt)+" and br_code="+br_code+" ");
		        stmt.executeUpdate("update MonthlyConStatus set post_ind='F' where yr_mth between "+convertToYYYYMM(frm_dt)+" and  "+convertToYYYYMM(to_dt)+" and br_code="+br_code+" ");
	     }catch(Exception se){ se.printStackTrace();}
	       finally{
		        try{
		            conn.close();
		        }catch(SQLException se){
		        	sessioncontext.setRollbackOnly();
		        	se.printStackTrace();
		        	}
		    }
	       return ret;
    }
	
    public MonthlyConsObject[] getMonthlyConsData(String frm_yrmth,String to_yrmth,String br_location)
    {
       Connection conn=null;
       ResultSet rs=null;
       Statement stmt=null;
       MonthlyConsObject[] mcons=null;
       String br_code=null;
       try{
           conn=getConnection();
           stmt=conn.createStatement();
           br_location=br_location+"%";
	       System.out.println("br_location="+br_location);
	       rs=stmt.executeQuery("select br_code from BranchMaster where br_name like'"+br_location+"'");
	       rs.next();
	       br_code=rs.getString("br_code");
	         
           rs=stmt.executeQuery("select * from MonthlyConsolidation where yr_mth between "+frm_yrmth+" and "+to_yrmth+" and br_code="+br_code+" ");
           
           rs.last();
           mcons=new MonthlyConsObject[rs.getRow()];
           
           rs.beforeFirst();
           int r=0;
           while(rs.next())
           {
               mcons[r]=new MonthlyConsObject();
               mcons[r].setBr_code(rs.getInt("br_code"));
               mcons[r].setYr_mth(rs.getInt("yr_mth"));
               mcons[r].setRecord_type(rs.getString("record_type"));
               mcons[r].setGl_type(rs.getString("gl_type"));
               mcons[r].setGl_code(rs.getInt("gl_code"));
               mcons[r].setCsh_dr(rs.getDouble("cash_dr"));
               mcons[r].setCg_dr(rs.getDouble("cg_dr"));
               mcons[r].setTrf_dr(rs.getDouble("trf_dr"));
               mcons[r].setCsh_cr(rs.getDouble("cash_cr"));
               mcons[r].setCg_cr(rs.getDouble("cg_cr"));
               mcons[r].setTrf_cr(rs.getDouble("trf_cr"));
               mcons[r].setDe_tml(rs.getString("de_tml"));
               mcons[r].setDe_user(rs.getString("de_user"));
               mcons[r].setDe_dt_tm(rs.getString("de_date_time"));
               mcons[r].setVe_tml(rs.getString("ve_tml"));
               mcons[r].setVe_user(rs.getString("ve_user"));
               mcons[r].setVe_dt_tm(rs.getString("ve_date_time"));
               
               r++;
           }
           
           return mcons;
       }catch(SQLException se){se.printStackTrace();}
       finally{
	        try{
	            conn.close();
	        }catch(SQLException se){se.printStackTrace();}
	    }
	    return mcons;
    }
    /**
	 * Swapna
	 * @return
	 */
   public String getYearMonth()
    {
    Connection conn=null;
    ResultSet rs=null;
    Statement stmt=null;
    try{
    	conn=getConnection();
    	stmt=conn.createStatement();
    	System.out.println("i am in qtr defn");
    	rs=stmt.executeQuery("select month from QtrDefinition where yr_defn='T'");
    	if(rs.next()){
    		return rs.getString("month");
    	}
    	else
    		return "0";
      }catch(Exception e){e.printStackTrace();}
      finally{
        try{
            conn.close();
        }catch(SQLException se){se.printStackTrace();}
    }
      return "0";
  }
   /**
	 * Swapna
	 * @return
	 */
   public int getLastYearMonth(){
    Connection conn=null;
    ResultSet rs=null;
    Statement stmt=null;
    try{
    	conn=getConnection();
    	stmt=conn.createStatement();
    	rs=stmt.executeQuery("select yr_mth from YearlyStatus where post_ind='T' order by yr_mth desc limit 1");
    	if(rs.next()){
    		return rs.getInt("yr_mth");
    	}
    	else
    		return 0;
      }catch(Exception e){e.printStackTrace();}
      finally{
        try{
            conn.close();
        }catch(SQLException se){se.printStackTrace();}
    }
      return 0;
   	}
   
   public double getPLProfit(String to_yrmth,String br_location ,boolean branch){
   	Connection conn=null;
    ResultSet rs=null,rs1=null;
    Statement stmt=null,stmt1=null;
    //PreparedStatement pstmt=null,pstmt_in=null;
    double sum1=0.0;//credit=0.0,debit=0.0;
    int br_code=0,fr_yrmth=0;
    try{
    	conn=getConnection();
    	stmt=conn.createStatement();
    	stmt1=conn.createStatement();
    	
    	rs=stmt.executeQuery("select * from BranchMaster where br_name like'"+br_location+"' ");
    	rs.next();
    	br_code=rs.getInt("br_code");
    	rs1=stmt1.executeQuery("select * from YearlyStatus where br_code="+br_code+" and yr_mth<"+to_yrmth+" and post_ind='T' order by yr_mth desc ");
		if(rs1.next()){
			if(!(branch)){
				fr_yrmth=rs1.getInt("yr_mth");
				rs=stmt.executeQuery("select (sum(trf_cr+cash_cr+cg_cr)-sum(trf_dr+cash_dr+cg_dr))as sum1 from MonthlySummary where  yr_mth>"+fr_yrmth+"  and yr_mth<="+to_yrmth+" and record_type!='X' and (gl_code like '3%' or gl_code like'4%') ;");
			}
			else{
				rs=stmt.executeQuery("select (sum(trf_cr+cash_cr+cg_cr)-sum(trf_dr+cash_dr+cg_dr))as sum1 from MonthlyConsolidation where  yr_mth>"+fr_yrmth+" and yr_mth<="+to_yrmth+" and br_code="+br_code+" and  record_type!='X' and  (gl_code like '3%' or gl_code like'4%') ; ");			}
		}
    	else{
    		if(!(branch))
    			rs=stmt.executeQuery("select (sum(trf_cr+cash_cr+cg_cr)-sum(trf_dr+cash_dr+cg_dr))as sum1 from MonthlySummary where  yr_mth<="+to_yrmth+" and (gl_code like '3%' or gl_code like'4%') and record_type!='X' ;");
    		else
    			rs=stmt.executeQuery("select (sum(trf_cr+cash_cr+cg_cr)-sum(trf_dr+cash_dr+cg_dr))as sum1 from MonthlyConsolidation where  yr_mth<="+to_yrmth+" and (gl_code like '3%' or gl_code like'4%') and record_type!='X' and br_code="+br_code+" ");
    	}
    	
    	if(rs.next()){
    		sum1=rs.getDouble("sum1");	
    	}
    		
    }catch(Exception e){e.printStackTrace();}
	finally{
		try{
			conn.close();
		}catch(SQLException se){se.printStackTrace();}
	}
		return sum1;
   }
   /**
	 * Swapna B Shetty
	 * @return
	 */
   public int PLPosting(String from_yrmth,String to_yrmth,String br_location,String tml,String user,String datetime,boolean branch){
   	Connection conn=null;
    ResultSet rs=null,rs1=null;
    Statement stmt=null,stmt1=null;
    PreparedStatement pstmt=null,pstmt_in=null;
    double credit=0.0,debit=0.0,sum=0.0;
    int br_code=0;
    String gl_type=null,gl_code=null;
    try{
    	conn=getConnection();
    	stmt=conn.createStatement();
    	stmt1=conn.createStatement();
    	rs=stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1012000' and code=2 ");
    	if(rs.next()){
    		gl_type=rs.getString(1);
    		gl_code=rs.getString(2);
    	}
    	else
    		return -10;
    	
    	rs=stmt.executeQuery("select br_code from BranchMaster where br_name like'"+br_location+"'");
	    rs.next();
	    br_code=rs.getInt("br_code");
	    if(branch){// Non Computersied Branch
	    	stmt.executeUpdate("delete from MonthlyConsolidation where yr_mth="+to_yrmth+" and record_type='X' and br_code="+br_code+"  ");
	    	//stmt.executeUpdate("update MonthlyConStatus set post_ind='F' where yr_mth="+to_yrmth+" and br_code="+br_code+" ");
	    	rs1=stmt1.executeQuery("select * from YearlyStatus where br_code="+br_code+" and yr_mth<"+to_yrmth+" and post_ind='T' order by yr_mth desc");
			if(rs1.next())
				rs=stmt.executeQuery("select  gl_type,gl_code,(sum(trf_cr+cash_cr+cg_cr)-sum(trf_dr+cash_dr+cg_dr))as sum1 from MonthlyConsolidation where yr_mth>"+rs1.getString("yr_mth")+" and yr_mth<="+to_yrmth+" and br_code="+br_code+" and (gl_code like '3%' or gl_code like'4%') group by gl_type,gl_code");
	    	else
    			rs=stmt.executeQuery("select  gl_type,gl_code,(sum(trf_cr+cash_cr+cg_cr)-sum(trf_dr+cash_dr+cg_dr))as sum1 from MonthlyConsolidation where yr_mth<="+to_yrmth+" and (gl_code like '3%' or gl_code like'4%')  and br_code="+br_code+" group by gl_type,gl_code");
	    }
	    else{// Head Office
	    	stmt.executeUpdate("delete from MonthlySummary where yr_mth="+to_yrmth+" and record_type='X' ");
	    	stmt.executeUpdate("update MonthlyConStatus set post_ind='F' where yr_mth="+to_yrmth+" and br_code="+br_code+" ");
	    	rs1=stmt1.executeQuery("select * from YearlyStatus where br_code="+br_code+" and yr_mth<"+to_yrmth+" and post_ind='T' order by yr_mth desc");
			if(rs1.next())
				rs=stmt.executeQuery("select gl_type,gl_code,(sum(trf_cr+cash_cr+cg_cr)-sum(trf_dr+cash_dr+cg_dr))as sum1 from MonthlySummary where yr_mth>"+rs1.getString("yr_mth")+" and yr_mth<="+to_yrmth+" and (gl_code like '3%' or gl_code like'4%') group by gl_type,gl_code ");
			else
    			rs=stmt.executeQuery("select  gl_type,gl_code,(sum(trf_cr+cash_cr+cg_cr)-sum(trf_dr+cash_dr+cg_dr))as sum1 from MonthlySummary where yr_mth<="+to_yrmth+" and (gl_code like '3%' or gl_code like'4%')  group by gl_type,gl_code ");
	    }
    	if(!branch){//Head Office
    		System.out.println("Head office");
    		if(rs.next()){
    		pstmt=conn.prepareStatement("insert into MonthlySummary values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    		rs.beforeFirst();
    		while(rs.next())
		    {
    			if(rs.getDouble("sum1")<0.0){
    				pstmt.setDouble(7,0.00);
    				pstmt.setDouble(10,Math.abs(rs.getDouble("sum1")));//credit
    				debit+=rs.getDouble("sum1");
    			}
    			else{
    				pstmt.setDouble(7,rs.getDouble("sum1"));//debit side
    				pstmt.setDouble(10,0.00);
    				credit+=rs.getDouble("sum1");
    			}
    			pstmt.setString(1,to_yrmth);
    			pstmt.setString(2,"X");
    			pstmt.setString(3,rs.getString("gl_type"));
    			pstmt.setInt(4,rs.getInt("gl_code"));
    			pstmt.setDouble(5,0.00);
    			pstmt.setDouble(6,0.00);
    			pstmt.setDouble(8,0.00);
    			pstmt.setDouble(9,0.00);
    			pstmt.setString(11,tml);
    			pstmt.setString(12,user);
    			pstmt.setString(13,datetime);
    			pstmt.setString(14,tml);
    			pstmt.setString(15,user);
    			pstmt.setString(16,datetime);
    			
    			pstmt.executeUpdate();
		    	}
    		sum=credit+debit;
    		pstmt.setString(3,gl_type);
    		pstmt.setString(4,gl_code);
			if(sum<0.0){
				pstmt.setDouble(7,sum);
				pstmt.setDouble(10,0.0);
			}
			else{
				pstmt.setDouble(10,sum);
				pstmt.setDouble(7,0.0);
			}
			pstmt.executeUpdate();
		}
    	else
    		return -1;
    	}
    	else if(branch){//Non Computerised Branch
    		if(rs.next()){
        		pstmt=conn.prepareStatement("insert into MonthlyConsolidation values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        		rs.beforeFirst();
        		while(rs.next())
    		    {
        			if(rs.getDouble("sum1")<0.0){
        				pstmt.setDouble(8,0.00);
        				pstmt.setDouble(11,rs.getDouble("sum1"));//credit
        				debit+=rs.getDouble("sum1");
        			}
        			else{
        				pstmt.setDouble(8,rs.getDouble("sum1"));//debit side
        				pstmt.setDouble(11,0.00);
        				credit+=rs.getDouble("sum1");
        			}
        			pstmt.setInt(1,br_code);
        			pstmt.setString(2,to_yrmth);
        			pstmt.setString(3,"X");
        			pstmt.setString(4,rs.getString("gl_type"));
        			pstmt.setInt(5,rs.getInt("gl_code"));
        			pstmt.setDouble(6,0.00);
        			pstmt.setDouble(7,0.00);
        			pstmt.setDouble(9,0.00);
        			pstmt.setDouble(10,0.00);
        			pstmt.setString(12,tml);
        			pstmt.setString(13,user);
        			pstmt.setString(14,datetime);
        			pstmt.setString(15,tml);
        			pstmt.setString(16,user);
        			pstmt.setString(17,datetime);
        			
        			pstmt.executeUpdate();
    		    	}
        		sum=credit-debit;
        		pstmt.setString(4,gl_type);
        		pstmt.setString(5,gl_code);
    			if(sum<0.0){
    				pstmt.setDouble(8,sum);
    				pstmt.setDouble(11,0.0);
    			}
    			else{
    				pstmt.setDouble(11,sum);
    				pstmt.setDouble(8,0.0);
    			}
    			pstmt.executeUpdate();
    		}
        	else
        		return -1;
    		
    	}
    	stmt.executeUpdate("delete from YearlyStatus where yr_mth="+to_yrmth+" and br_code="+br_code+" ");
    	pstmt_in=conn.prepareStatement("insert into YearlyStatus values(?,?,?,?,?,?,?)");
		pstmt_in.setString(1,to_yrmth);
		pstmt_in.setInt(2,br_code);
		pstmt_in.setString(3,"T");
		pstmt_in.setString(4,"F");
		pstmt_in.setString(5,tml);
		pstmt_in.setString(6,user);
		pstmt_in.setString(7,datetime);
		pstmt_in.executeUpdate();
		
   	}catch(Exception e){
   		sessioncontext.setRollbackOnly();
   		e.printStackTrace();
   		}
    	finally{
    		try{
    			conn.close();
    		}catch(SQLException se){se.printStackTrace();}
    }
    	return 1;
  }
   public int closeYear(String yr_mth,String br_location){
	Connection con=null;
	Statement stmt=null;
	ResultSet rs=null;
	int br_code=0;
	try{
		con=getConnection();
		stmt=con.createStatement();
		rs=stmt.executeQuery("select * from BranchMaster where br_name='"+br_location+"' ");
		if(rs.next())
			br_code=rs.getInt("br_code");
		else
			return -1;
		
		stmt.executeUpdate("update YearlyStatus set year_close='T' where yr_mth="+yr_mth+" and br_code="+br_code+"  and post_ind='T' ");
	}catch(Exception e){
		sessioncontext.setRollbackOnly();
		e.printStackTrace();
		}
	finally{
		try{
			con.close();
		}catch(SQLException se){se.printStackTrace();}
}
	return 1;
   }
	public GLObject getTableInformation(String table_name) throws SQLException {
		
		GLObject gl_table=null;
		String[] col_names=null;
		ResultSetMetaData meta_data=null;
		Connection con=null;
		System.out.println(" i am in  get table information");
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("Select distinct * from "+table_name+" ");
			if(rs!=null){
				meta_data=rs.getMetaData();
				col_names=new String[meta_data.getColumnCount()];
				
				for(int i=0;i<meta_data.getColumnCount();i++){
					col_names[i]=meta_data.getColumnName(i+1);
				}
				gl_table=new GLObject();
				gl_table.setTableColumnNames(col_names);
			}
		}catch(SQLException sql){sql.printStackTrace();throw new SQLException();}
		finally{
			con.close();
		}
		return gl_table;
	}
	
	public GLObject[] getTable(String table_name,String date,boolean selected) throws RecordsNotFoundException,SQLException 
	{
		GLObject[] gl_object=null;
		Connection con=null;
		ResultSetMetaData meta_data=null;
		ResultSet rs=null;
		String[] col_names=null;
		System.out.println(" i am in get table");
		System.out.println("table="+table_name);
		System.out.println("date1="+date);
		try{
			if(date!=null)
				date=Validations.convertYMD(date);
			
			con=getConnection();
			Statement stmt=con.createStatement();
			
			if(table_name.equals("GLMaster")&& date==null){
				System.out.println("date2="+date);
				rs=stmt.executeQuery("select  m.moduleabbr,gl.gl_code,gl_name,sch_type,gl_status,normal_cd,from_date,to_date,gl.de_tml,gl.de_user,gl.de_date from "+table_name+" gl,Modules m where m.modulecode=gl.gl_type order by 1,2 ");
			}
			else if(table_name.equals("GLMaster") &&  !(date.equals(null))){
				System.out.println("date3="+date);
				rs=stmt.executeQuery("select  m.moduleabbr,gl.gl_code,gl_name,sch_type,gl_status,normal_cd,from_date,to_date,gl.de_tml,gl.de_user,gl.de_date from "+table_name+" gl,Modules m where m.modulecode=gl.gl_type and '"+date+"' between gl.from_date and gl.to_date order by 1,2 ");
			}
			else if(table_name.equals("GLPost"))
				rs=stmt.executeQuery("select  m1.moduleabbr as ac_type,m.moduleabbr as gl_type ,p.gl_code,gl.gl_name,p.trn_type,p.cr_dr,p.mult_by,p.de_tml,p.de_user,p.de_date from Modules m1,Modules m,GLPost p left join GLMaster gl on gl.gl_type=p.gl_type and p.gl_code=gl.gl_code where  m.modulecode=p.gl_type  and m1.modulecode=p.ac_type and '"+date+"' between gl.from_date and gl.to_date ");
			else if(table_name.equals("GLTransactionType"))
				rs=stmt.executeQuery("select  m1.moduleabbr as ac_type,m.moduleabbr as gl_type ,p.gl_code,gl.gl_name,p.trn_type,p.trn_desc,p.de_tml,p.de_user,p.de_date from Modules m1,Modules m,GLTransactionType p left join GLMaster gl on gl.gl_type=p.gl_type and p.gl_code=gl.gl_code where  m.modulecode=p.gl_type  and m1.modulecode=p.ac_type and '"+date+"' between gl.from_date and gl.to_date  ");
			else if(table_name.equals("GLKeyParam") && selected)
				rs=stmt.executeQuery("select  m1.moduleabbr ,p.ac_type,code,key_desc,m.moduleabbr as gl_type,p.gl_code,gl.gl_name,p.de_tml,p.de_user,p.de_date from Modules m1,Modules m,GLKeyParam p left join GLMaster gl on gl.gl_type=p.gl_type and p.gl_code=gl.gl_code where  m.modulecode=p.gl_type  and m1.modulecode=p.ac_type and '"+date+"' between gl.from_date and gl.to_date and p.ac_type like '1012%' order by p.ac_type,p.code");
			else if(table_name.equals("GLKeyParam") && !(selected))	
			    rs=stmt.executeQuery("select  m1.moduleabbr ,p.ac_type,code,key_desc,m.moduleabbr as gl_type,p.gl_code,gl.gl_name,p.de_tml,p.de_user,p.de_date from Modules m1,Modules m,GLKeyParam p left join GLMaster gl on gl.gl_type=p.gl_type and p.gl_code=gl.gl_code where  m.modulecode=p.gl_type  and m1.modulecode=p.ac_type and '"+date+"' between gl.from_date and gl.to_date ");
			else if(table_name.equals("Modules") && selected)
				rs=stmt.executeQuery("Select distinct * from "+table_name+" where modulecode like'1012%' order by 1");
			else           
				rs=stmt.executeQuery("Select distinct * from "+table_name+" order by 1");
			
			if(rs!=null){
				meta_data=rs.getMetaData();
				col_names=new String[meta_data.getColumnCount()];
				
				for(int i=0;i<meta_data.getColumnCount();i++){
					col_names[i]=meta_data.getColumnName(i+1);
				}
				rs.last();
				gl_object=new GLObject[rs.getRow()];
				rs.beforeFirst();
				int i=0;
				if(table_name.equalsIgnoreCase("GLPost")){
					
					while(rs.next()){
						gl_object[i]=new GLObject();
						gl_object[i].setAcType(rs.getString("ac_type"));
						gl_object[i].setGlType(rs.getString("gl_type"));
						gl_object[i].setGlCode(rs.getString("gl_code"));
						gl_object[i].setGlName(rs.getString("gl_name"));
						gl_object[i].setTrnType(rs.getString("trn_type"));
						gl_object[i].setCrDr(rs.getString("cr_dr"));
						gl_object[i].setMultBy(rs.getInt("mult_by"));
						gl_object[i].setDeTml(rs.getString("de_tml"));
						gl_object[i].setDeUser(rs.getString("de_user"));
						gl_object[i].setDeDate(rs.getString("de_date"));
						i++;
					}
					if(gl_object[0]!=null)
						gl_object[0].setTableColumnNames(col_names);
				}
				else if(table_name.equalsIgnoreCase("GLMaster")){
					while(rs.next()){
						gl_object[i]=new GLObject();
						gl_object[i].setGlType(rs.getString("moduleabbr"));
						gl_object[i].setGlCode(rs.getString("gl_code"));
						gl_object[i].setGlName(rs.getString("gl_name"));
						gl_object[i].setShType(rs.getString("sch_type"));
						gl_object[i].setGlStatus(rs.getString("gl_status"));
						gl_object[i].setNormalCD(rs.getString("normal_cd"));
						if(rs.getString("from_date")!=null){
							gl_object[i].setFrom_dt(Validations.convertDMY(rs.getString("from_date")));
						}
						else{
							gl_object[i].setFrom_dt(rs.getString("from_date"));
						}
						
						if(rs.getString("to_date")!=null)
							gl_object[i].setTo_dt(Validations.convertDMY(rs.getString("to_date")));
						else
							gl_object[i].setTo_dt(rs.getString("to_date"));
						
						gl_object[i].setDeTml(rs.getString("de_tml"));
						gl_object[i].setDeUser(rs.getString("de_user"));
						gl_object[i].setDeDate(rs.getString("de_date"));
						
						i++;
					}
					if(gl_object[0]!=null)
						gl_object[0].setTableColumnNames(col_names);
				}
				else if(table_name.equalsIgnoreCase("GLKeyParam")){
					while(rs.next()){
						gl_object[i]=new GLObject();
						gl_object[i].setModAbbr(rs.getString("moduleabbr"));
						gl_object[i].setAcType(rs.getString("ac_type"));
						gl_object[i].setCode(rs.getInt("code"));
						gl_object[i].setKeyDesc(rs.getString("key_desc"));
						gl_object[i].setGlType(rs.getString("gl_type"));
						gl_object[i].setGlCode(rs.getString("gl_code"));
						gl_object[i].setGlName(rs.getString("gl_name"));
						gl_object[i].setDeTml(rs.getString("de_tml"));
						gl_object[i].setDeUser(rs.getString("de_user"));
						gl_object[i].setDeDate(rs.getString("de_date"));
						i++;
					}
					if(gl_object[0]!=null)
						gl_object[0].setTableColumnNames(col_names);
					
				}else if(table_name.equalsIgnoreCase("GLTransactiontype")){
					while(rs.next()){
						gl_object[i]=new GLObject();
						gl_object[i].setAcType(rs.getString("ac_type"));
						gl_object[i].setGlType(rs.getString("gl_type"));
						gl_object[i].setGlCode(rs.getString("gl_code"));
						gl_object[i].setGlName(rs.getString("gl_name"));
						gl_object[i].setTrnType(rs.getString("trn_type"));
						gl_object[i].setTrnDesc(rs.getString("trn_desc"));
						gl_object[i].setDeTml(rs.getString("de_tml"));
						gl_object[i].setDeUser(rs.getString("de_user"));
						gl_object[i].setDeDate(rs.getString("de_date"));
						
						i++;
					}
					if(gl_object.length!=0)
						gl_object[0].setTableColumnNames(col_names);
				}
				else if(table_name.equalsIgnoreCase("GLMasterPeriod")){
				   while(rs.next()){
					gl_object[i]=new GLObject();
						
					gl_object[i].setGlType(rs.getString("gl_type"));
					gl_object[i].setGlCode(rs.getString("gl_code"));
					gl_object[i].setGlName(rs.getString("gl_name"));
					gl_object[i].setFrom_dt(rs.getString("from_date"));
					gl_object[i].setTo_dt(rs.getString("to_date"));
					gl_object[i].setDeTml(rs.getString("de_tml"));
					gl_object[i].setDeUser(rs.getString("de_user"));
					gl_object[i].setDeDate(rs.getString("de_date"));
					
					i++;
				}
				if(gl_object.length!=0)
					gl_object[0].setTableColumnNames(col_names);
				}
				else if(table_name.equalsIgnoreCase("DailyStatus")){
					while(rs.next()){
						gl_object[i]=new GLObject();
						gl_object[i].setDate(Validations.convertDMY(rs.getString("trn_dt")));
						gl_object[i].setCashClose(rs.getString("cash_close"));
						gl_object[i].setPostInd(rs.getString("post_ind"));
						gl_object[i].setDayClose(rs.getString("day_close"));
						gl_object[i].setDeTml(rs.getString("de_tml"));
						gl_object[i].setDeUser(rs.getString("de_user"));
						gl_object[i].setDeDate(rs.getString("de_date_time"));
						i++;
					}
					if(gl_object[0]!=null)
						gl_object[0].setTableColumnNames(col_names);
				}
				else if(table_name.equalsIgnoreCase("MonthlyStatus")){
					while(rs.next()){
						gl_object[i]=new GLObject();
						gl_object[i].setDate(rs.getString("yr_mth"));
						gl_object[i].setPostInd(rs.getString("post_ind"));
						gl_object[i].setMonthClose(rs.getString("month_close"));
						gl_object[i].setDeTml(rs.getString("de_tml"));
						gl_object[i].setDeUser(rs.getString("de_user"));
						gl_object[i].setDeDate(rs.getString("de_date_time"));
						i++;
					}
					if(gl_object[0]!=null)
						gl_object[0].setTableColumnNames(col_names);
				}
				else if(table_name.equalsIgnoreCase("DailyConStatus")){
					while(rs.next()){
						gl_object[i]=new GLObject();
						gl_object[i].setDate(Validations.convertDMY(rs.getString("trn_dt")));
						gl_object[i].setPostInd(rs.getString("post_ind"));
						gl_object[i].setBr_code(rs.getInt("br_code"));
						gl_object[i].setDeTml(rs.getString("de_tml"));
						gl_object[i].setDeUser(rs.getString("de_user"));
						gl_object[i].setDeDate(rs.getString("de_date_time"));
						i++;
					}
					if(gl_object[0]!=null)
						gl_object[0].setTableColumnNames(col_names);
				}
				else if(table_name.equalsIgnoreCase("MonthlyConStatus")){
					while(rs.next()){
						gl_object[i]=new GLObject();
						gl_object[i].setDate(rs.getString("yr_mth"));
						gl_object[i].setPostInd(rs.getString("post_ind"));
						gl_object[i].setBr_code(rs.getInt("br_code"));
						gl_object[i].setDeTml(rs.getString("de_tml"));
						gl_object[i].setDeUser(rs.getString("de_user"));
						gl_object[i].setDeDate(rs.getString("de_date_time"));
						i++;
					}
					if(gl_object[0]!=null)
						gl_object[0].setTableColumnNames(col_names);
				}
				else if(table_name.equalsIgnoreCase("YearlyStatus")){
					while(rs.next()){
						gl_object[i]=new GLObject();
						gl_object[i].setDate(rs.getString("yr_mth"));
						gl_object[i].setBr_code(rs.getInt("br_code"));
						gl_object[i].setPostInd(rs.getString("post_ind"));
						gl_object[i].setYearClose(rs.getString("year_close"));
						gl_object[i].setDeTml(rs.getString("de_tml"));
						gl_object[i].setDeUser(rs.getString("de_user"));
						gl_object[i].setDeDate(rs.getString("de_date_time"));
						i++;
					}
					if(gl_object[0]!=null)
						gl_object[0].setTableColumnNames(col_names);
				}
				else if(table_name.equalsIgnoreCase("Modules")){
				    while(rs.next()){
				        gl_object[i]=new GLObject();
						gl_object[i].setModCode(rs.getString(1));
						gl_object[i].setModDesc(rs.getString(2));
						gl_object[i].setModAbbr(rs.getString(3));
						gl_object[i].setLstAccNO(rs.getString(4));
						gl_object[i].setMaxRen(rs.getString(5));
						gl_object[i].setMinBal(rs.getString(6));
						gl_object[i].setMinPeriod(rs.getString(7));
						gl_object[i].setOtherProp(rs.getString(8));
						gl_object[i].setLnkShares(rs.getString(9));
						gl_object[i].setMaxAmt(rs.getString(10));
						gl_object[i].setFromDay(rs.getString(11));
						gl_object[i].setToDay(rs.getString(12));
						gl_object[i].setTransPerMth(rs.getString(13));
						gl_object[i].setLstDate(rs.getString(14));
						gl_object[i].setIntRate(rs.getString(15));
						gl_object[i].setStdInst(rs.getString(16));
						gl_object[i].setDeTml(rs.getString("de_tml"));
						gl_object[i].setDeUser(rs.getString("de_user"));
						gl_object[i].setDeDate(rs.getString("de_dt_time"));
						gl_object[i].setMaxRenDays(rs.getString(20));
						gl_object[i].setRenIntRate(rs.getString(21));
						gl_object[i].setPenRate(rs.getString(22));
						gl_object[i].setPassBkLines(rs.getString(23));
						gl_object[i].setCommRate(rs.getString(24));
						gl_object[i].setMaxCommRate(rs.getString(25));
						gl_object[i].setVchScrlNo(rs.getString(26));
						gl_object[i].setChqValidPrd(rs.getString(27));
						gl_object[i].setMinAmtChq(rs.getString(28));
						gl_object[i].setMinAmtClg(rs.getString(29));
						gl_object[i].setMaxAmtChq(rs.getString(30));
						gl_object[i].setMaxAmtClg(rs.getString(31));
						gl_object[i].setTopMargin(rs.getString(32));
						gl_object[i].setLinesPerPage(rs.getString(33));
						gl_object[i].setBottomMargin(rs.getString(34));
						gl_object[i].setLstTrfScrlNo(rs.getString(35));
						gl_object[i].setLstRectNo(rs.getString(36));
						gl_object[i].setLstVchNo(rs.getString(37));
						gl_object[i].setInsPrd(rs.getString(38));
						gl_object[i].setLnCode(rs.getString(39));
						i++;
					}
					if(gl_object[0]!=null)
						gl_object[0].setTableColumnNames(col_names);
				}
			 else throw new RecordsNotFoundException();
			}
		}catch(SQLException sql){
			sql.printStackTrace();
			throw new SQLException();
		}
		finally{
			con.close();
		}
		
		return gl_object;
	}
	// Code Added by sanjeet.. for GLAdmin on 21/04/2006
	
	public int updateTable(String table_name,GLObject[] gl_object) throws RecordNotUpdatedException,SQLException
	{
		
		int result=0;
		Connection con=null;
		PreparedStatement ps_update=null;
		ResultSet rs=null,rs_br_name=null;
		Statement stmt1=null,stmt=null;
		
		try{
			con=getConnection();
			stmt1=con.createStatement();
			stmt=con.createStatement();
			
				if(table_name.equalsIgnoreCase("GLMaster")){
				System.out.println("GL Length = "+gl_object.length);
				for(int i=0;i<gl_object.length;i++){
					rs=stmt1.executeQuery("select * from "+table_name+" where gl_type='"+gl_object[i].getGlType()+"' and  gl_code="+gl_object[i].getGlCode()+" and from_date!='"+Validations.convertYMD(gl_object[i].getFrom_dt())+"'  and '"+Validations.convertYMD(gl_object[i].getTo_dt())+"' between from_date and to_date");
					if(rs.next())
						return -1;
					
					rs=stmt1.executeQuery("select * from GLKeyParam where gl_type='"+gl_object[i].getGlType()+"' and  gl_code="+gl_object[i].getGlCode()+" ");
					if(rs.next())
						return -2;
					
					rs=stmt1.executeQuery("select * from GLPost where gl_type='"+gl_object[i].getGlType()+"' and  gl_code="+gl_object[i].getGlCode()+" ");
					if(rs.next())
						return -2;
					
					rs=stmt1.executeQuery("select * from GLTransactionType where gl_type='"+gl_object[i].getGlType()+"' and  gl_code="+gl_object[i].getGlCode()+" ");
					if(rs.next())
						return -2;
					
					ps_update=con.prepareStatement("update "+table_name+" set gl_name=?,sch_type=?,gl_status=?,normal_cd=?,to_date=?,de_tml=?,de_user=?,de_date=? where gl_type=? and gl_code=?  and from_date=? ");
					
					ps_update.setString(1,gl_object[i].getGlName());
					ps_update.setString(2,gl_object[i].getShType());
					ps_update.setString(3,gl_object[i].getGlStatus());
					ps_update.setString(4,gl_object[i].getNormalCD());
					ps_update.setString(5,Validations.convertYMD(gl_object[i].getTo_dt()));
					ps_update.setString(6,gl_object[i].getDeTml());
					ps_update.setString(7,gl_object[i].getDeUser());
					ps_update.setString(8,gl_object[i].getDeDate());
					ps_update.setString(9,gl_object[i].getGlType());
					ps_update.setInt(10,Integer.parseInt(gl_object[i].getGlCode()));
					ps_update.setString(11,Validations.convertYMD(gl_object[i].getFrom_dt()));
					
					if(ps_update.executeUpdate()==0)
						throw new RecordNotUpdatedException();
					else 
						result =1;
				}
			}
			else if(table_name.equalsIgnoreCase("GLKeyParam")){
				
				for(int i=0;i<gl_object.length;i++){
					
					ps_update=con.prepareStatement("update GLKeyParam set key_desc=?,gl_code=?,gl_type=?,de_tml=?,de_user=?,de_date=? where ac_type=? and code=?");
					
					ps_update.setString(1,gl_object[i].getKeyDesc());
					ps_update.setString(2,gl_object[i].getGlCode());
					ps_update.setString(3,gl_object[i].getGlType());
					ps_update.setString(4,gl_object[i].getDeTml());
					ps_update.setString(5,gl_object[i].getDeUser());
					ps_update.setString(6,gl_object[i].getDeDate());
					ps_update.setString(7,gl_object[i].getAcType());
					ps_update.setInt(8,gl_object[i].getCode());
			
					if(ps_update.executeUpdate()==0)
						throw new RecordNotUpdatedException();
					else
					    result =1;
				}
			}
			else if(table_name.equalsIgnoreCase("Modules")){
				
				for(int i=0;i<gl_object.length;i++){

					ps_update=con.prepareStatement("update Modules set moduledesc=?,moduleabbr=?,de_tml=?,de_user=?,de_dt_time=? where modulecode=? ");
					
					ps_update.setString(1,gl_object[i].getModDesc());
					ps_update.setString(2,gl_object[i].getModAbbr());
					ps_update.setString(3,gl_object[i].getDeTml());
					ps_update.setString(4,gl_object[i].getDeUser());
					ps_update.setString(5,gl_object[i].getDeDate());
					ps_update.setString(6,gl_object[i].getModCode());
					
					if(ps_update.executeUpdate()==0)
						throw new RecordNotUpdatedException();
					else result =1;
				}
			}
			else if(table_name.equalsIgnoreCase("DailyStatus") ){
			    int br_code=0;
				for(int i=0;i<gl_object.length;i++){
					System.out.println("At 2743 in bean");
					  System.out.println("1 cash"+gl_object[i].getCashClose());
					  System.out.println("1 cash"+gl_object[i].getPostInd());
					  System.out.println("1 cash"+gl_object[i].getDayClose());
				    rs_br_name=stmt.executeQuery("select br_code from BranchMaster where br_name like '"+gl_object[i].getBr_name().trim()+"' ");
					rs_br_name.next();
					br_code=rs_br_name.getInt("br_code");
				        ps_update=con.prepareStatement("update DailyStatus set cash_close=?,post_ind=?,day_close=?,de_tml=?,de_user=?,de_date_time=? where trn_dt=?");
				        ps_update.setString(1,gl_object[i].getCashClose().trim());
				        ps_update.setString(2,gl_object[i].getPostInd().trim());
				        ps_update.setString(3,gl_object[i].getDayClose().trim());
				       
				        if(gl_object[i].getCashClose().trim().equalsIgnoreCase("F")){
				            ps_update.setString(1,"F");
				            ps_update.setString(2,"F");
				            ps_update.setString(3,"F");
				        }
				        else if(gl_object[i].getPostInd().trim().equalsIgnoreCase("F")){
				            ps_update.setString(1,gl_object[i].getCashClose().trim());
				            ps_update.setString(2,"F");
				            ps_update.setString(3,"F");
				        }
				        else if(gl_object[i].getDayClose().trim().equalsIgnoreCase("F")){
				            ps_update.setString(1,gl_object[i].getCashClose().trim());
				            ps_update.setString(2,"F");
				            ps_update.setString(3,"F");
				        }
				        
				        ps_update.setString(4,gl_object[i].getDeTml());
				        ps_update.setString(5,gl_object[i].getDeUser());
				        ps_update.setString(6,gl_object[i].getDeDate());
				        ps_update.setString(7,gl_object[i].getDate());
				    
					
				        if(ps_update.executeUpdate()==0)
				            throw new RecordNotUpdatedException();
				        else 
				            result =1;
				        
					if(gl_object[i].getPostInd().equalsIgnoreCase("F") || gl_object[i].getCashClose().equalsIgnoreCase("F") || gl_object[i].getDayClose().equalsIgnoreCase("F")){
				            ps_update=con.prepareStatement("update DailyConStatus set post_ind=?,de_tml=?,de_user=?,de_date_time=?  where trn_dt=? and br_code=?");
			                ps_update.setString(1,"F");
				            ps_update.setString(2,gl_object[i].getDeTml());
				            ps_update.setString(3,gl_object[i].getDeUser());
				            ps_update.setString(4,gl_object[i].getDeDate());
				            ps_update.setString(5,gl_object[i].getDate());
				            ps_update.setInt(6,br_code);
				            ps_update.executeUpdate();

				            ps_update=con.prepareStatement("update MonthlyConStatus set post_ind=?,de_tml=?,de_user=?,de_date_time=?  where yr_mth=? and br_code=?");
			                ps_update.setString(1,"F");
						    ps_update.setString(2,gl_object[i].getDeTml());
						    ps_update.setString(3,gl_object[i].getDeUser());
						    ps_update.setString(4,gl_object[i].getDeDate());
						    ps_update.setInt(5,convertToYYYYMM(Validations.convertDMY(gl_object[i].getDate())));
						    ps_update.setInt(6,br_code);
						    ps_update.executeUpdate();

						    ps_update=con.prepareStatement("update MonthlyStatus set post_ind=?,month_close=?,de_tml=?,de_user=?,de_date_time=?  where yr_mth=?  ");
						    ps_update.setString(1,"F");
						    ps_update.setString(2,"F");
						    ps_update.setString(3,gl_object[i].getDeTml());
						    ps_update.setString(4,gl_object[i].getDeUser());
						    ps_update.setString(5,gl_object[i].getDeDate());
						    ps_update.setInt(6,convertToYYYYMM(Validations.convertDMY(gl_object[i].getDate())));
						    ps_update.executeUpdate();

						    ps_update=con.prepareStatement("update YearlyStatus set post_ind=?,year_close=?,de_tml=?,de_user=?,de_date_time=?  where yr_mth=? and br_code=?  ");
						    ps_update.setString(1,"F");
						    ps_update.setString(2,"F");
						    ps_update.setString(3,gl_object[i].getDeTml());
						    ps_update.setString(4,gl_object[i].getDeUser());
						    ps_update.setString(5,gl_object[i].getDeDate());
						    ps_update.setInt(6,convertToYYYYMM(Validations.convertDMY(gl_object[i].getDate())));
						    ps_update.setInt(7,br_code);
						    ps_update.executeUpdate();
					}
				}
			}
			else if(table_name.equalsIgnoreCase("MonthlyStatus") ){
			    int br_code=0;
				for(int i=0;i<gl_object.length;i++){
				    rs_br_name=stmt.executeQuery("select br_code from BranchMaster where br_name like '"+gl_object[i].getBr_name()+"' ");
					rs_br_name.next();
					br_code=rs_br_name.getInt("br_code");
					ps_update=con.prepareStatement("update MonthlyStatus set post_ind=?,month_close=?,de_tml=?,de_user=?,de_date_time=?  where yr_mth=?  ");
				    ps_update.setString(1,gl_object[i].getPostInd());
				    ps_update.setString(2,gl_object[i].getMonthClose());
					if(gl_object[i].getMonthClose().equalsIgnoreCase("F")){
						ps_update.setString(1,"F");
					    ps_update.setString(2,"F");
					}
					ps_update.setString(3,gl_object[i].getDeTml());
					ps_update.setString(4,gl_object[i].getDeUser());
					ps_update.setString(5,gl_object[i].getDeDate());
					ps_update.setString(6,gl_object[i].getDate());
					ps_update.executeUpdate();
					
					if(gl_object[i].getPostInd().equalsIgnoreCase("F")){
						ps_update=con.prepareStatement("update DailyConStatus set post_ind=?,de_tml=?,de_user=?,de_date_time=?  where year(trn_dt)=? and month(trn_dt)=? and br_code=?");
						ps_update.setString(1,"F");
						ps_update.setString(2,gl_object[i].getDeTml());
						ps_update.setString(3,gl_object[i].getDeUser());
						ps_update.setString(4,gl_object[i].getDeDate());
						String year=gl_object[i].getDate().substring(0,4);
						String mth=gl_object[i].getDate().substring(4,6);
						System.out.println("year="+year);
						System.out.println("mth="+mth);
						ps_update.setString(5,year);
						ps_update.setString(6,mth);
						ps_update.setInt(7,br_code);
						ps_update.executeUpdate();
					}
					if(gl_object[i].getPostInd().equalsIgnoreCase("F")){
						ps_update=con.prepareStatement("update MonthlyConStatus set post_ind=?,de_tml=?,de_user=?,de_date_time=?  where yr_mth=? and br_code=?");
						ps_update.setString(1,"F");
						ps_update.setString(2,gl_object[i].getDeTml());
						ps_update.setString(3,gl_object[i].getDeUser());
						ps_update.setString(4,gl_object[i].getDeDate());
						ps_update.setString(5,gl_object[i].getDate());
						ps_update.setInt(6,br_code);
						ps_update.executeUpdate();
					}
					
					if(gl_object[i].getPostInd().equalsIgnoreCase("F") || gl_object[i].getMonthClose().equalsIgnoreCase("F")){
					    ps_update=con.prepareStatement("update YearlyStatus set post_ind=?,year_close=?,de_tml=?,de_user=?,de_date_time=?  where yr_mth=? and br_code=?  ");
					    ps_update.setString(1,"F");
					    ps_update.setString(2,"F");
					    ps_update.setString(3,gl_object[i].getDeTml());
					    ps_update.setString(4,gl_object[i].getDeUser());
					    ps_update.setString(5,gl_object[i].getDeDate());
					    ps_update.setString(6,gl_object[i].getDate());
					    ps_update.setInt(7,br_code);
					    ps_update.executeUpdate();
					}
				}
				result=1;
				   return result;
			}
			else if(table_name.equalsIgnoreCase("DailyConStatus") ){
			    int br_code=0;
				for(int i=0;i<gl_object.length;i++){
				    rs_br_name=stmt.executeQuery("select br_code from BranchMaster where br_name like '"+gl_object[i].getBr_name()+"' ");
					rs_br_name.next();
					br_code=rs_br_name.getInt("br_code");
				        				        
					if(gl_object[i].getPostInd().equalsIgnoreCase("F")){
				            ps_update=con.prepareStatement("update DailyConStatus set post_ind=?,de_tml=?,de_user=?,de_date_time=?  where trn_dt=? and br_code=?");
			                ps_update.setString(1,"F");
				            ps_update.setString(2,gl_object[i].getDeTml());
				            ps_update.setString(3,gl_object[i].getDeUser());
				            ps_update.setString(4,gl_object[i].getDeDate());
				            ps_update.setString(5,gl_object[i].getDate());
				            ps_update.setInt(6,br_code);
				            ps_update.executeUpdate();

				            ps_update=con.prepareStatement("update MonthlyConStatus set post_ind=?,de_tml=?,de_user=?,de_date_time=?  where yr_mth=? and br_code=?");
			                ps_update.setString(1,"F");
						    ps_update.setString(2,gl_object[i].getDeTml());
						    ps_update.setString(3,gl_object[i].getDeUser());
						    ps_update.setString(4,gl_object[i].getDeDate());
						    ps_update.setInt(5,convertToYYYYMM(Validations.convertDMY(gl_object[i].getDate())));
						    ps_update.setInt(6,br_code);
						    ps_update.executeUpdate();

						    ps_update=con.prepareStatement("update YearlyStatus set post_ind=?,year_close=?,de_tml=?,de_user=?,de_date_time=?  where yr_mth=? and br_code=?  ");
						    ps_update.setString(1,"F");
						    ps_update.setString(2,"F");
						    ps_update.setString(3,gl_object[i].getDeTml());
						    ps_update.setString(4,gl_object[i].getDeUser());
						    ps_update.setString(5,gl_object[i].getDeDate());
						    ps_update.setInt(6,convertToYYYYMM(Validations.convertDMY(gl_object[i].getDate())));
						    ps_update.setInt(7,br_code);
						    ps_update.executeUpdate();
					}
				}
				result=1;
					return result;
			}
			else if(table_name.equalsIgnoreCase("MonthlyConStatus") ){
			    int br_code=0;
				for(int i=0;i<gl_object.length;i++){
				    rs_br_name=stmt.executeQuery("select br_code from BranchMaster where br_name like '"+gl_object[i].getBr_name()+"' ");
					rs_br_name.next();
					br_code=rs_br_name.getInt("br_code");
				        				        
					if(gl_object[i].getPostInd().equalsIgnoreCase("F")){
				            ps_update=con.prepareStatement("update MonthlyConStatus set post_ind=?,de_tml=?,de_user=?,de_date_time=?  where yr_mth=? and br_code=?");
			                ps_update.setString(1,"F");
						    ps_update.setString(2,gl_object[i].getDeTml());
						    ps_update.setString(3,gl_object[i].getDeUser());
						    ps_update.setString(4,gl_object[i].getDeDate());
						    ps_update.setInt(5,convertToYYYYMM(Validations.convertDMY(gl_object[i].getDate())));
						    ps_update.setInt(6,br_code);
						    ps_update.executeUpdate();

						    ps_update=con.prepareStatement("update YearlyStatus set post_ind=?,year_close=?,de_tml=?,de_user=?,de_date_time=?  where yr_mth=? and br_code=?  ");
						    ps_update.setString(1,"F");
						    ps_update.setString(2,"F");
						    ps_update.setString(3,gl_object[i].getDeTml());
						    ps_update.setString(4,gl_object[i].getDeUser());
						    ps_update.setString(5,gl_object[i].getDeDate());
						    ps_update.setInt(6,convertToYYYYMM(Validations.convertDMY(gl_object[i].getDate())));
						    ps_update.setInt(7,br_code);
						    ps_update.executeUpdate();
					}
				}
				result=1;
			    	return result;
			}
			else if(table_name.equalsIgnoreCase("YearlyStatus") ){
			    int br_code=0;
				for(int i=0;i<gl_object.length;i++){
				    rs_br_name=stmt.executeQuery("select br_code from BranchMaster where br_name like '"+gl_object[i].getBr_name()+"' ");
					rs_br_name.next();
					br_code=rs_br_name.getInt("br_code");

				   ps_update=con.prepareStatement("update YearlyStatus set post_ind=?,year_close=?,de_tml=?,de_user=?,de_date_time=?  where yr_mth=? and br_code=?  ");
				   ps_update.setString(1,gl_object[i].getPostInd());
				   ps_update.setString(2,gl_object[i].getYearClose());
				   if(gl_object[i].getPostInd().equalsIgnoreCase("F")){         
						    ps_update.setString(1,"F");
						    ps_update.setString(2,"F");
				   }
				   ps_update.setString(3,gl_object[i].getDeTml());
				   ps_update.setString(4,gl_object[i].getDeUser());
				   ps_update.setString(5,gl_object[i].getDeDate());
				   ps_update.setString(6,gl_object[i].getDate());
				   ps_update.setInt(7,br_code);
				   ps_update.executeUpdate();
				}
				result=1;
   			    return result;
			}
		}catch(SQLException sql){
			sessioncontext.setRollbackOnly();
			sql.printStackTrace(); 
			throw new SQLException();
		}catch(RecordNotUpdatedException rec){
			sessioncontext.setRollbackOnly();
			result=-1;
			rec.printStackTrace(); 
			throw new RecordNotUpdatedException();
		}
		finally{
			con.close();
		}
		
		return result;
	}
	
     //	 Code Added by sanjeet.. for GLAdmin on 21/04/2006
	
	public int insertRecords(String table_name,GLObject[] gl_object) throws SQLException
	{
		
		int result=0;
		Connection con=null;
		PreparedStatement ps_insert=null;
		Statement stmt=null,stmt1=null,stmt_up=null;
		ResultSet rs=null,rs1=null;
		String futuredate="9999-12-31",fromdate_1=null;
		int ret=0;
		
		
		try{
			con=getConnection();
			stmt=con.createStatement();
			stmt1=con.createStatement();
			stmt_up=con.createStatement();
			if(table_name.equalsIgnoreCase("GLMaster")){
				int i=0;
				rs=stmt.executeQuery("select * from "+table_name+" where gl_code="+gl_object[i].getGlCode()+" and gl_type='"+gl_object[i].getGlType()+"' and from_date>='"+Validations.convertYMD(gl_object[i].getFrom_dt())+"' ");
				if(rs.next())
			    	return 0;
			    else
			    {
		    		rs1=stmt1.executeQuery("select * from "+table_name+" where gl_code="+gl_object[i].getGlCode()+" and gl_type='"+gl_object[i].getGlType()+"' and from_date<'"+Validations.convertYMD(gl_object[i].getFrom_dt())+"' and to_date>='"+Validations.convertYMD(gl_object[i].getFrom_dt())+"' and to_date!='"+futuredate+"' ");
			        if(rs1.next()){
			            System.out.println(" i am in second");
				    	ret=1;
				    	return ret;
			        }
			        else{
			            fromdate_1=Validations.addDays(gl_object[i].getFrom_dt(),-1);
		            	ret=stmt_up.executeUpdate("update "+table_name+" set to_date='"+Validations.convertYMD(fromdate_1)+"',de_user='"+gl_object[i].getDeUser()+"', de_tml='"+gl_object[i].getDeTml()+"' , de_date='"+gl_object[i].getDeDate() +"' where gl_code="+gl_object[i].getGlCode()+"  and gl_type='"+gl_object[i].getGlType()+"' and from_date<'"+Validations.convertYMD(gl_object[i].getFrom_dt())+"' and to_date>='"+gl_object[i].getFrom_dt()+"' and to_date='"+futuredate+"' ");
				    
				        for(i=0;i<gl_object.length;i++){
				        	ps_insert=con.prepareStatement("insert into "+table_name+"  values(?,?,?,?,?,?,?,?,?,?,?)");
				        	ps_insert.setString(1,gl_object[i].getGlType());
				        	ps_insert.setInt(2,Integer.parseInt(gl_object[i].getGlCode()));
				        	ps_insert.setString(3,gl_object[i].getGlName());
				        	ps_insert.setString(4,gl_object[i].getShType());
				        	ps_insert.setString(5,gl_object[i].getGlStatus());
				        	ps_insert.setString(6,gl_object[i].getNormalCD());
				        	ps_insert.setString(7,Validations.convertYMD(gl_object[i].getFrom_dt()));
				        	ps_insert.setString(8,Validations.convertYMD(gl_object[i].getTo_dt()));
				        	ps_insert.setString(9,gl_object[i].getDeTml());
				        	ps_insert.setString(10,gl_object[i].getDeUser());
				        	ps_insert.setString(11,gl_object[i].getDeDate());
				        	if(ps_insert.executeUpdate()==0)
				        		throw new RecordNotInsertedException();
				        	else result =1;
				        }
				        ret=2;
				        System.out.println("i am in third");
				        return ret;
			        }
			    }
			}
			else if(table_name.equalsIgnoreCase("GLKeyParam")){
				
				for(int i=0;i<gl_object.length;i++){
					ps_insert=con.prepareStatement("insert into GLKeyParam values(?,?,?,?,?,?,?,?)");
					ps_insert.setString(1,gl_object[i].getAcType());
					ps_insert.setInt(2,gl_object[i].getCode());
					ps_insert.setString(3,gl_object[i].getKeyDesc());
					ps_insert.setString(4,gl_object[i].getGlType());
					ps_insert.setString(5,gl_object[i].getGlCode());
					ps_insert.setString(6,gl_object[i].getDeTml());
					ps_insert.setString(7,gl_object[i].getDeUser());
					ps_insert.setString(8,gl_object[i].getDeDate());
					
					if(ps_insert.executeUpdate()==0)
						throw new RecordNotInsertedException();
					else result =1;
				}
			}
			else if(table_name.equalsIgnoreCase("Modules")){
				
				for(int i=0;i<gl_object.length;i++){
					ps_insert=con.prepareStatement("insert into Modules (modulecode,moduledesc,moduleabbr,de_tml,de_user,de_dt_time) values(?,?,?,?,?,?)");
					ps_insert.setString(1,gl_object[i].getModCode());
					ps_insert.setString(2,gl_object[i].getModDesc());
					ps_insert.setString(3,gl_object[i].getModAbbr());
					ps_insert.setString(4,gl_object[i].getDeTml());
					ps_insert.setString(5,gl_object[i].getDeUser());
					ps_insert.setString(6,gl_object[i].getDeDate());
					
					if(ps_insert.executeUpdate()==0)
						throw new RecordNotInsertedException();
					else result =1;
				}
			}
		
		}catch(DateFormatException de){
			de.printStackTrace();
			sessioncontext.setRollbackOnly();
			result=-1;
		}
		catch(SQLException sql){
			sql.printStackTrace();
			sessioncontext.setRollbackOnly();
			throw new SQLException();
		}catch(RecordNotInsertedException rec){
			rec.printStackTrace();
			sessioncontext.setRollbackOnly();
			throw new RecordNotInsertedException();
		}
		finally{
			con.close();
		}
		
		return result;
	}
	
		//	 Code Added by sanjeet.. for GLAdmin on 21/04/2006
	
	public int deleteRecords(String table_name,GLObject gl_object[]) throws RecordsNotDeletedException,SQLException
	{
		
		int result=0;
		Connection con=null;
		PreparedStatement ps_delete=null;
		
		try{
			con=getConnection();
			
			if(table_name.equalsIgnoreCase("GLPost")){
				for(int i=0;i<gl_object.length;i++){
					ps_delete=con.prepareStatement("Delete from GLPost where ac_type='"+gl_object[i].getAcType()+"' and gl_code="+gl_object[i].getGlCode()+" ");
					if(ps_delete.executeUpdate()==0)
						throw new RecordsNotDeletedException();
					else result=1;
				}
			}
			else if(table_name.equalsIgnoreCase("GLMaster")){
				for(int i=0;i<gl_object.length;i++){
					ps_delete=con.prepareStatement("Delete from GLMaster where gl_type='"+gl_object[i].getGlType()+"' and gl_code="+gl_object[i].getGlCode()+" ");
					if(ps_delete.executeUpdate()==0)
						throw new RecordsNotDeletedException();
					else result=1;
				}
			}
			else if(table_name.equalsIgnoreCase("GLKeyParam")){
				for(int i=0;i<gl_object.length;i++){
					ps_delete=con.prepareStatement("Delete from GLKeyParam where ac_type='"+gl_object[i].getAcType()+"' and code="+gl_object[i].getCode()+" ");
					if(ps_delete.executeUpdate()==0)
						throw new RecordsNotDeletedException();
					else result=1;
				}
			}
			else if(table_name.equalsIgnoreCase("GLTransactiontype")){
				for(int i=0;i<gl_object.length;i++){
					ps_delete=con.prepareStatement("Delete from GLTransactiontype where ac_type='"+gl_object[i].getAcType()+"' and gl_code="+gl_object[i].getGlCode()+" ");
					if(ps_delete.executeUpdate()==0)
						throw new RecordsNotDeletedException();
					else result=1;
				}
			}
			else if(table_name.equalsIgnoreCase("GLMasterPeriod")){
				for(int i=0;i<gl_object.length;i++){
					ps_delete=con.prepareStatement("Delete from GLMasterPeriod where gl_type='"+gl_object[i].getGlType()+"' and gl_code="+gl_object[i].getGlCode()+" ");
					if(ps_delete.executeUpdate()==0)
						throw new RecordsNotDeletedException();
					else result=1;
				}
			}
		}catch(SQLException sql){
			sessioncontext.setRollbackOnly();
			sql.printStackTrace();
			throw new SQLException();
		}
		catch(RecordsNotDeletedException sql){
			sessioncontext.setRollbackOnly();
			sql.printStackTrace();
			throw new RecordsNotDeletedException();
		}
		finally{
			con.close();
		}
		return result;
	}
	
	public Connection getConnection()
	{
		try{
			return ds.getConnection("root","");
		}catch(SQLException sql){sql.printStackTrace();}
		
		return null;
	}
	
	// Code Added By Sanjeet....
	

	
	public GLObject[] getDetails() throws RecordsNotFoundException 
	{
		
		GLObject[] gl_object=null;
		Connection con=null;
		int i=0;
		System.out.println("length"+gl_object);
		
		
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select distinct m.moduleabbr,gl_code from GLMaster gl,Modules m where gl.gl_type=m.modulecode ");
			
				rs.last();
				if(rs.getRow()==0)
				    throw new RecordsNotFoundException();
				gl_object=new GLObject[rs.getRow()];
				rs.beforeFirst();
					while(rs.next()){
						gl_object[i]=new GLObject();
						
						gl_object[i].setAcType(rs.getString("m.moduleabbr"));
						gl_object[i].setCode(rs.getInt("gl_code"));
						
						i++;
					}
					
			System.out.println("********");
			
		}catch(SQLException sql){
			sql.printStackTrace();
			//throw new SQLException();
		}
		finally{
			try{
			con.close();
			}catch(SQLException sql){
				sql.printStackTrace();
				//throw new SQLException();
			}
		}
		System.out.println("length"+gl_object);
		return gl_object;
	}
	public GLObject[] getVouchingSlip(String slip_date,String gl_type,int gl_no1,int gl_no2,int type) throws RecordsNotFoundException 
	{
		GLObject[] gl_object=null;
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		int i=0;
		try{
			con=getConnection();
			stmt=con.createStatement();
			System.out.println("Slip date="+slip_date);
			rs=stmt.executeQuery("select distinct ds.cash_dr,ds.cg_dr,ds.trf_dr,ds.cash_cr,ds.cg_cr,ds.trf_cr,m.moduleabbr,ds.gl_code,h.bankname,bm.br_name,gt.trn_mode,ds.trn_dt from DailySummary ds,GLMaster gl,Modules m,Head h,BranchMaster bm,GLTranOld gt where bm.br_code%1111!=0 and  gl.gl_type=m.modulecode and ds.gl_type='"+gl_type+"' and ds.gl_code between "+gl_no1+" and "+gl_no2+" and  ds.trn_dt='"+slip_date+"' and gt.trn_date='"+Validations.convertDMY(slip_date)+"' and ds.gl_code=gl.gl_code and bm.br_code=1 ");
			rs.last();
			if(rs.getRow()==0)
				throw new RecordsNotFoundException();
			gl_object=new GLObject[rs.getRow()];
			rs.beforeFirst();
					while(rs.next()){
						gl_object[i]=new GLObject();
						gl_object[i].setAcType(rs.getString("m.moduleabbr"));
						gl_object[i].setCode(rs.getInt("ds.gl_code"));
						gl_object[i].setBank_name(rs.getString("h.bankname"));
						gl_object[i].setAddr(rs.getString("bm.br_name"));
						gl_object[i].setTrn_date(rs.getString("ds.trn_dt"));
						gl_object[i].setTrnType(rs.getString("gt.trn_mode"));
						
						if(type==1){
							gl_object[i].setCash_cr(rs.getDouble("ds.cash_cr"));
							gl_object[i].setCg_cr(rs.getDouble("ds.cg_cr"));
							gl_object[i].setTrf_cr(rs.getDouble("ds.trf_cr"));
						}
						else{
							gl_object[i].setCash_dr(rs.getDouble("ds.cash_dr"));
							gl_object[i].setCg_dr(rs.getDouble("ds.cg_dr"));
							gl_object[i].setTrf_dr(rs.getDouble("ds.trf_dr"));
						}
						i++;
					}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		finally{
			try{
			con.close();
			}catch(SQLException sql){
				sql.printStackTrace();}
		}
		
		return gl_object;
	}
	
	
	 // Method added by sanjeet...
	 
	 public GLObject[] getReceiptAndPayment(int from_date,int to_date,String qry) throws RecordsNotFoundException 
	{
	 System.out.println("Inside the GLBean of method getRP");
	 System.out.println("fromdate in the GLBean is =="+from_date);
	 System.out.println("todate in the GLBean is =="+to_date);
	 System.out.println("qry in the GLBean is =="+qry);
	 GLObject[] gl_object=null;
	 Connection con=null;
	 ResultSet rs=null;
		
		int i=0;
		
		
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			if(qry.equals("")){
			   rs=stmt.executeQuery("select gl.gl_name,m.moduleabbr,ml.gl_code,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum from MonthlySummary as ml,GLMaster as gl,Modules as m where  yr_mth  between "+from_date+" and "+to_date+" and ml.gl_type=gl.gl_type and ml.gl_code=gl.gl_code and gl.gl_type=m.modulecode group by gl_code  ");
			   System.out.println("when the query is null in Glbean");
			}
			else
				rs=stmt.executeQuery("select gl.gl_name,m.moduleabbr,ml.gl_code,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum from MonthlySummary as ml,GLMaster as gl,Modules as m where  yr_mth  between "+from_date+" and "+to_date+" and ml.gl_type=gl.gl_type and ml.gl_code=gl.gl_code and gl.gl_type=m.modulecode  and ("+qry+") group by gl_code  ");
			
			System.out.println("****1111*****");
				rs.last();
				if(rs.getRow()==0)
					throw new RecordsNotFoundException();
				
				gl_object=new GLObject[rs.getRow()];
				rs.beforeFirst();
				
				System.out.println("****1221*****");		
					while(rs.next()){
						gl_object[i]=new GLObject();
						
						gl_object[i].setAcType(rs.getString("m.moduleabbr"));
						gl_object[i].setCode(rs.getInt("ml.gl_code"));
						gl_object[i].setGlName(rs.getString("gl.gl_name"));
						gl_object[i].setCredit_sum(rs.getDouble("credit_sum"));
						gl_object[i].setDebit_sum(rs.getDouble("debit_sum"));
						
												
						i++;
					}
					
					System.out.println("****1331*****");
			System.out.println("successfully executed the method rp in the bean");
		}catch(SQLException sql){
			sql.printStackTrace();
			
		}
		finally{
			try{
			con.close();
			}catch(SQLException se){se.printStackTrace();}
		}
		
		return gl_object;
	}		

	 /// Code Added By Sanjeet ... on 10/04/2006.
	 
	 public String[][] getBranchDetails()
	 {
		Connection con=null;
		String br_data[][]=null;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select br_code,br_name,address,br_shnm from BranchMaster where br_code%1111 !=0 ");
			if(rs.last()){
				br_data = new String[rs.getRow()][4];
			}
			rs.beforeFirst();
			int count=0;
			if(br_data!=null){
				while(rs.next()){
					br_data[count][0]=rs.getString("br_code");
					br_data[count][1]=rs.getString("br_name");
					br_data[count][2]=rs.getString("address");
					br_data[count][3]=rs.getString("br_shnm");
					count++;
				}
			}
				
		}catch(SQLException sql){sql.printStackTrace();}
		finally{
			try{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
	 	return br_data;
	 }
	 
	 public String[][] getNonComputersiedBranchs()
	 {
		Connection con=null;
		String br_data[][]=null;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select br_code,br_name,address,br_shnm from BranchMaster where  computerised='F'");
			if(rs.last()){
				br_data = new String[rs.getRow()][4];
			}
			rs.beforeFirst();
			int count=0;
			if(br_data!=null){
				while(rs.next()){
					br_data[count][0]=rs.getString("br_code");
					br_data[count][1]=rs.getString("br_name");
					br_data[count][2]=rs.getString("address");
					br_data[count][3]=rs.getString("br_shnm");
					count++;
				}
			}
				
		}catch(SQLException sql){sql.printStackTrace();}
		finally{
			try{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
	 	return br_data;
	 }
	
	 public String[][] getBranchCode(String br_location)
	 {
		Connection con=null;
		String br_data[][] =null;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select br_code,br_name,address,br_shnm,br_type from BranchMaster where br_name='"+br_location+"' ");
			if(rs.last()){
				br_data = new String[rs.getRow()][5];
			}
			rs.beforeFirst();
			int count=0;
			if(br_data!=null){
				while(rs.next()){
					br_data[count][0]=rs.getString("br_code");
					br_data[count][1]=rs.getString("br_name");
					br_data[count][2]=rs.getString("address");
					br_data[count][3]=rs.getString("br_shnm");
					br_data[count][4]=rs.getString("br_type");
					count++;
				}
			}
				
		}catch(SQLException sql){sql.printStackTrace();}
		finally{
			try{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
	 	return br_data;
	 }
	 public String[][] getBranchName(int br_code)
	 {
		Connection con=null;
		String br_data[][] =null;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select br_code,br_name,address,br_shnm,br_type from BranchMaster where br_code='"+br_code+"' ");
			if(rs.last()){
				br_data = new String[rs.getRow()][5];
			}
			rs.beforeFirst();
			int count=0;
			if(br_data!=null){
				while(rs.next()){
					br_data[count][0]=rs.getString("br_code");
					br_data[count][1]=rs.getString("br_name");
					br_data[count][2]=rs.getString("address");
					br_data[count][3]=rs.getString("br_shnm");
					br_data[count][4]=rs.getString("br_type");
					count++;
				}
			}
				
		}catch(SQLException sql){sql.printStackTrace();}
		finally{
			try{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
	 	return br_data;
	 }

	 
	 
//	 Method added by sanjeet... on 11/04/2006.
	 
	 public GLObject[] getConsolidatedReceiptAndPayment(int from_date,int to_date,String query_str) throws RecordsNotFoundException 
	{
	 
	 GLObject[] gl_object=null;
		Connection con=null;
		ResultSet rs=null;
		int i=0;
		
		
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			if(query_str.equals(""))
				rs=stmt.executeQuery("select gl.gl_name,m.moduleabbr,mc.gl_code,bm.br_code,bm.br_name,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum from MonthlyConsolidation as mc,GLMaster as gl,Modules as m,BranchMaster as bm where bm.br_code%1111!=0 and  mc.yr_mth  between "+from_date+" and "+to_date+" and mc.gl_type=gl.gl_type and mc.gl_code=gl.gl_code and gl.gl_type=m.modulecode and mc.br_code=bm.br_code and (mc.cash_dr + mc.cg_dr + mc.trf_dr >0) and (mc.cash_cr + mc.cg_cr + mc.trf_cr >0) group by gl_code,br_code order by gl_code  ");
			else
				rs=stmt.executeQuery("select gl.gl_name,m.moduleabbr,mc.gl_code,bm.br_code,bm.br_name,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum from MonthlyConsolidation as mc,GLMaster as gl,Modules as m,BranchMaster as bm where bm.br_code%1111!=0 and mc.yr_mth  between "+from_date+" and "+to_date+" and mc.gl_type=gl.gl_type and mc.gl_code=gl.gl_code and gl.gl_type=m.modulecode and mc.br_code=bm.br_code and (mc.cash_dr + mc.cg_dr + mc.trf_dr >0) and (mc.cash_cr + mc.cg_cr + mc.trf_cr >0) and ("+query_str+") group by gl_code,br_code order by gl_code  ");
			System.out.println("****1111*****");
				rs.last();
				if(rs.getRow()==0)
					throw new RecordsNotFoundException();
				
				gl_object=new GLObject[rs.getRow()];
				rs.beforeFirst();
				
				System.out.println("****1221*****");		
					while(rs.next()){
						gl_object[i]=new GLObject();
						
						gl_object[i].setAcType(rs.getString("m.moduleabbr"));
						gl_object[i].setCode(rs.getInt("mc.gl_code"));
						gl_object[i].setGlName(rs.getString("gl.gl_name"));
						gl_object[i].setBr_code(rs.getInt("bm.br_code"));
						gl_object[i].setBr_name(rs.getString("bm.br_name"));
						gl_object[i].setCredit_sum(rs.getDouble("credit_sum"));
						gl_object[i].setDebit_sum(rs.getDouble("debit_sum"));
						
												
						i++;
					}
					
					System.out.println("****1331*****");
			
		}catch(SQLException sql){
			sql.printStackTrace();
			
		}
		finally{
			try{
			con.close();
			}catch(SQLException se){se.printStackTrace();}
		}
		
		return gl_object;
	}		
	 
	 
	
	// Method added by Murugesh on 04/04/2006
	/**
	 * @author Murugesh
	 * This function is used to get the active gl_code,gl_type,gl_name on any given date.
	 * 
	 * @param String date
	 * @return String[][]
	 */
	 public String[][] getGLDetails(String date)
	 {
		Connection con=null;
		String gl_data[][]=null;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select gm.gl_type,gm.gl_code,gm.gl_name from  GLMaster gm where  ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))   order by gl_type,gl_code");
			if(rs.last()){
				gl_data = new String[rs.getRow()][3];
			}
			rs.beforeFirst();
			int count=0;
			if(gl_data!=null){
				while(rs.next()){
					gl_data[count][0]=rs.getString("gl_type");
					gl_data[count][1]=rs.getString("gl_code");
					gl_data[count][2]=rs.getString("gl_name");
					count++;
				}
			}
				
		}catch(SQLException sql){sql.printStackTrace();}
		finally{
			try{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
	 	return gl_data;
	 }
	 
	 // Method added by Murugesh on 04/04/2006
	 public double OpeningBalance(String gl_type,int gl_code,String date) 
	 {
	 	Connection con=null;
	 	double debit=0.0,credit=0.0;
	 	double opening_balance=0.0;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			String year=null;
			String month=null;
			String day=null;
		//	String previous_month_date=Validations.previousMonthFirstDay(date);
			int year_month=convertToYYYYMM(date);
			
			ResultSet rs=stmt.executeQuery("select sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum from MonthlySummary where gl_type='"+gl_type+"' and gl_code="+gl_code+" and yr_mth<"+year_month+" ");
			if(rs.next()){
				debit=rs.getDouble("debit_sum");
				credit=rs.getDouble("credit_sum");
			}
			
			rs=stmt.executeQuery("select max(yr_mth) as last_year_month from MonthlySummary where gl_type='"+gl_type+"' and gl_code="+gl_code+" and yr_mth<"+year_month+" ");
			
			String last_year_month="199001"; // This has been set to this month year by default because if there is no value in the MonthlySummary then it will be null
			if(rs.next()){
				if(rs.getString("last_year_month")!=null)
					last_year_month=rs.getString("last_year_month");
			}
			
			System.out.println("the last_year_month in "+last_year_month);
			if(last_year_month!=null){
				day="01";
				month=last_year_month.substring(4,6);
				year=last_year_month.substring(0,4);
				String last_month=day+"/"+month+"/"+year;
				
				System.out.println(" the last month is "+last_month);
				String next_month_first_day=Validations.nextMonthFirstDay(last_month);
				String previous_day = Validations.addDays(date,-1);
				
				rs=stmt.executeQuery("select sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum from DailySummary where gl_type='"+gl_type+"' and gl_code="+gl_code+" and trn_dt between '"+Validations.convertYMD(next_month_first_day)+"' and '"+Validations.convertYMD(previous_day)+"'");
				
				if(rs.next()){
					debit += rs.getDouble("debit_sum");
					credit += rs.getDouble("credit_sum");
				}
			}
			
			opening_balance = credit-debit;
			
		}catch(SQLException sql){sql.printStackTrace();} 
		catch (DateFormatException e) {e.printStackTrace();
		}
		finally{
			try{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
		
		return opening_balance;
	 }
	 
	 // Method added by Murugesh on 04/04/2006
	 public double ClosingBalance(String gl_type,int gl_code,String date) 
	 {
	 	Connection con=null;
	 	double debit=0.0,credit=0.0;
	 	double closing_balance=0.0;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			String year=null;
			String month=null;
			String day=null;
			System.out.println("close");
			//String previous_month_date=Validations.previousMonthFirstDay(date);
			int year_month=convertToYYYYMM(date);
			
			ResultSet rs=stmt.executeQuery("select sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum from MonthlySummary where gl_type='"+gl_type+"' and gl_code="+gl_code+" and yr_mth<"+year_month+" ");
			if(rs.next()){
				debit=rs.getDouble("debit_sum");
				credit=rs.getDouble("credit_sum");
			}
			
			rs=stmt.executeQuery("select max(yr_mth) as last_year_month from MonthlySummary where gl_type='"+gl_type+"' and gl_code="+gl_code+" and yr_mth<"+year_month+" ");
			
			String last_year_month="199001"; // This has been set to this month year by default because if there is no value in the MonthlySummary then it will be null
			if(rs.next()){
				if(rs.getString("last_year_month")!=null)
					last_year_month=rs.getString("last_year_month");
			}
			
			if(last_year_month!=null){
				day="01";
				month=last_year_month.substring(4,6);
				year=last_year_month.substring(0,4);
				String last_month=day+"/"+month+"/"+year;
				
				String next_month_first_day=Validations.nextMonthFirstDay(last_month);

				rs=stmt.executeQuery("select sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum from DailySummary where gl_type='"+gl_type+"' and gl_code="+gl_code+" and trn_dt between '"+Validations.convertYMD(next_month_first_day)+"' and '"+Validations.convertYMD(date)+"'");
				
				if(rs.next()){
					debit += rs.getDouble("debit_sum");
					credit += rs.getDouble("credit_sum");
				}
			}
			
			closing_balance = credit-debit;
			System.out.println("close bal in close="+closing_balance);
			
		}catch(SQLException sql){sql.printStackTrace();} 
		finally{
			try{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
		
		return closing_balance;
	 }
	 
	 // Method added by Murugesh 
	 public int convertToYYYYMM(String date)
	 {
	 	String day=null,month=null,year=null;
	 	if(date!=null){
			StringTokenizer st= new StringTokenizer(date,"/");
			day=st.nextToken();
			month=st.nextToken();
			year=st.nextToken();
			return Integer.parseInt(year+month);
		}
	 	else
	 		return 0;
	 }
	 
	 public DayBookObject[] getDetails(String date,String string_qry)
		{
			
			//DayBookObject daybook = null;
			DayBookObject daybook_object[]=null;
			
			//double cr_total=0.0,dr_total=0.0;
			//double net=0.0,bal=0.0,total_credit=0.0,dly_diff=0.0,dly_credit=0.0,dly_debit=0.0,dly_cashcr=0.0,dly_clearcr=0.0,dly_trfcr=0.0,dly_cashdr=0.0,dly_cleardr=0.0,dly_trfdr=0.0;
			//float mth_diff=0.0f,mth_credit=0.0f,mth_debit=0.0f,mth_cashcr=0.0f,mth_clearcr=0.0f,mth_trfcr=0.0f,mth_cashdr=0.0f,mth_cleardr=0.0f,mth_trfdr=0.0f;
			//String mth_date;
			//int temp_yr,temp_mth;
			//int flag_net=0,flag_close=0;
			//String temp_gltype,str_yrmth,substr_mth,substr_yr;
			//int cash_gl;
		    //int i=0,temp_gl,temp_glcode;
			Connection con=null;
			//int count=0;
			String cash_gltype;//gl_type,gl_name,
			
			int cash_glcode;
	     String cash_gltypecode[]=null;	
						
		   //int year,month,day,temp_yrmth;
		   String year_mth;
		   double cash_openbal=0.0,cash_closebal=0.0;
		   
	         
		    
		   //System.out.println("Date for STok : "+date);
		    /*StringTokenizer st=new StringTokenizer(date,"/");
		    
		    day=Integer.parseInt(st.nextToken());
		    month=Integer.parseInt(st.nextToken());
		    year=Integer.parseInt(st.nextToken());*/
		    
		    year_mth=date.substring(6,10)+date.substring(3,5);
		    System.out.println("year="+date.substring(6,10));
		    System.out.println("month="+date.substring(3,5));
		    System.out.println("year_mth"+year_mth);
			try
			{
			con=getConnection();
			cash_gltypecode=new String[2];	
		   cash_gltypecode= getCashGLTypeCode();
						
			  cash_gltype= cash_gltypecode[0];
			  cash_glcode=Integer.parseInt(cash_gltypecode[1]);
			  
			  System.out.println("cash gltype="+cash_gltype);
			  System.out.println("cash glcode="+cash_glcode);
			  cash_closebal=ClosingBalance(cash_gltype,cash_glcode,date);
			  if(cash_closebal<0.0)
			  {
			  	cash_closebal=cash_closebal*(-1);
			  	System.out.println("cash close bal="+cash_closebal);
			  }
			  cash_openbal=OpeningBalance(cash_gltype,cash_glcode,date);
			  if(cash_openbal<0.0) 
			  {
					cash_openbal=cash_openbal*(-1);
					System.out.println("cas bal="+cash_openbal);
			  }
			  //daybook = new DayBookObject();
			  			  
			  DayBookObject return_daybook_object[]= getDayBookDetails(date,string_qry);
			  if(return_daybook_object!=null){
			  		daybook_object=new DayBookObject[return_daybook_object.length+1];
			  		daybook_object[0]=new DayBookObject();
			  		daybook_object[0].setcash_openbal(cash_openbal);
			  	    daybook_object[0].setcash_glcode(cash_glcode);
				    daybook_object[0].setcash_closebal(cash_closebal);
				
			  		for(int j=1;j<daybook_object.length;j++)
			  			daybook_object[j]=return_daybook_object[j-1];
			  }
			  
			System.out.println("daybookobject="+daybook_object[0].getcash_openbal());
						
 }//end of try
						
			catch(Exception e){e.printStackTrace();}
			finally{
				try{
					con.close();
				}catch(Exception e){e.printStackTrace();}
			}		
			System.out.println("the first length is:"+daybook_object.length);
			return daybook_object;
		
		}
	 // Method added by Murugesh on 06/04/2006
	 public String[][] getGLDetailsForTwoDates(String date1,String date2)
	 {
	 	Connection con=null;
		String gl_data[][]=null;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			//ResultSet rs=stmt.executeQuery("select gm.gl_type,gm.gl_code,gm.gl_name,gm.normal_cd,moduleabbr from GLMaster gm,Modules mo where mo.modulecode=gm.gl_type and ((from_date<='"+Validations.convertYMD(date1)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date1)+"' and to_date>='"+Validations.convertYMD(date1)+"') or (from_date>='"+Validations.convertYMD(date1)+"' and to_date<='"+Validations.convertYMD(date2)+"') or (from_date<='"+Validations.convertYMD(date2)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date2)+"' and to_date>='"+Validations.convertYMD(date2)+"'))  order by gl_type,gl_code");
			ResultSet rs=stmt.executeQuery("select m.moduleabbr,gl.* from GLMaster gl,Modules m where m.modulecode=gl.gl_type and from_date<='"+Validations.convertYMD(date2)+"' and ((to_date is null or to_date='') or to_date>='"+Validations.convertYMD(date1)+"')order by gl_type,gl_code;");
			if(rs.last()){
				gl_data = new String[rs.getRow()][5];
			}
			rs.beforeFirst();
			int count=0;
			if(gl_data!=null){
				while(rs.next()){
					gl_data[count][0]=rs.getString("gl_type");
					gl_data[count][1]=rs.getString("gl_code");
					gl_data[count][2]=rs.getString("gl_name");
					gl_data[count][3]=rs.getString("normal_cd");
					gl_data[count][4]=rs.getString("moduleabbr");
					count++;
				}
			}
				
		}catch(SQLException sql){sql.printStackTrace();}
		finally{
			try{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
	 	return gl_data;
	 }
	 
	 
	 // Method added by Murugesh on 07/04/2006
	 public DayBookObject getDailySummary(String gl_type,int gl_code,String date)
	 {
	 	Connection con=null;
	 	DayBookObject daybook=null;
	 	double credit_total=0.0,debit_total=0.0;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			System.out.println("daily =");
			ResultSet rs = stmt.executeQuery("select * from DailySummary where gl_type='"+gl_type+"' and gl_code="+gl_code+" and trn_dt='"+Validations.convertYMD(date)+"'");
			if(rs.next()){
				daybook = new DayBookObject();
				
				daybook.setGLType(rs.getString("gl_type"));
				daybook.setgl_code(rs.getInt("gl_code"));
				System.out.println("daily gl_code="+daybook.getgl_code());
				daybook.setTransactionDate(convertDateToString(rs.getString("trn_dt")));
				
				daybook.setcr_cash(rs.getDouble("cash_cr"));
				daybook.setcr_clear(rs.getDouble("cg_cr"));
				daybook.setcr_trans(rs.getDouble("trf_cr"));
				
				credit_total=rs.getDouble("cash_cr")+rs.getDouble("cg_cr")+rs.getDouble("trf_cr");
				daybook.setcr_total(credit_total);
				
				daybook.setdr_cash(rs.getDouble("cash_dr"));
				daybook.setdr_clear(rs.getDouble("cg_dr"));
				daybook.setdr_trans(rs.getDouble("trf_dr"));
				
				debit_total=rs.getDouble("cash_dr")+rs.getDouble("cg_dr")+rs.getDouble("trf_dr");
				daybook.setdr_total(debit_total);
				
				daybook.set_net(credit_total-debit_total);
			}
			
		}catch(SQLException sql){sql.printStackTrace();}
		finally{
			try{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
		return daybook;
	 }
	 
	 // Method added by Murugesh on 10/04/2006
	 public DayBookObject getDailySummaryForBranch(String gl_type,int gl_code,int branch_code,String date)
	 {
	 	Connection con=null;
	 	DayBookObject daybook=null;
	 	double credit_total=0.0,debit_total=0.0;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			System.out.println("daily =");
			ResultSet rs = stmt.executeQuery("select * from DailyConsolidation where gl_type='"+gl_type+"' and gl_code="+gl_code+" and trn_dt='"+Validations.convertYMD(date)+"' and  br_code="+branch_code+" ");
			if(rs.next()){
				daybook = new DayBookObject();
				
				daybook.setGLType(rs.getString("gl_type"));
				daybook.setgl_code(rs.getInt("gl_code"));
				System.out.println("daily gl_code="+daybook.getgl_code());
				daybook.setTransactionDate(convertDateToString(rs.getString("trn_dt")));
				
				daybook.setcr_cash(rs.getDouble("cash_cr"));
				daybook.setcr_clear(rs.getDouble("cg_cr"));
				daybook.setcr_trans(rs.getDouble("trf_cr"));
				
				credit_total=rs.getDouble("cash_cr")+rs.getDouble("cg_cr")+rs.getDouble("trf_cr");
				daybook.setcr_total(credit_total);
				
				daybook.setdr_cash(rs.getDouble("cash_dr"));
				daybook.setdr_clear(rs.getDouble("cg_dr"));
				daybook.setdr_trans(rs.getDouble("trf_dr"));
				
				debit_total=rs.getDouble("cash_dr")+rs.getDouble("cg_dr")+rs.getDouble("trf_dr");
				daybook.setdr_total(debit_total);
				
				daybook.set_net(credit_total-debit_total);
			}
			
		}catch(SQLException sql){sql.printStackTrace();}
		finally{
			try{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
		return daybook;
	 }
	 
	 // Method added by Murugesh on 07/04/2006
	 public String convertDateToString(String date)
	 {
	 	String date_string=null;
	 	if(date!=null){
	 		StringTokenizer st = new StringTokenizer(date,"-");
	 		String year = st.nextToken();
	 		String month = st.nextToken();
	 		String day = st.nextToken();
	 		
	 		date_string=day+"/"+month+"/"+year;
	 	}
	 	return date_string;
	 }
	 
	 // Method added by Murugesh on 07/04/2006
	 public String[][] getGLDetailsForMonth(int year_month)
	 {
		String gl_data[][]=null;
		try{
			
			String day="01";
			String month = String.valueOf(year_month).substring(4,6);
			String year =  String.valueOf(year_month).substring(0,4);
			
			String month_first_day = day+"/"+month+"/"+year;
			String month_last_day = Validations.lastDayOfMonth(month_first_day);
			
			gl_data = getGLDetailsForTwoDates(month_first_day,month_last_day);
			
		}catch(Exception sql){sql.printStackTrace();}
		
	 	return gl_data;
	 }
	 
	 // Method added by Murugesh on 07/04/2006
	 public String[][] getGLDetailsFromMonthToMonth(int from_year_month,int to_year_month)
	 {
	 	String gl_data[][]=null;
		try{
			
			String day="01";
			String month = String.valueOf(from_year_month).substring(4,6);
			String year =  String.valueOf(from_year_month).substring(0,4);
			String from_month_first_day = day+"/"+month+"/"+year;
			
			day="01";
			month = String.valueOf(to_year_month).substring(4,6);
			year =  String.valueOf(to_year_month).substring(0,4);
			String to_month_first_day = day+"/"+month+"/"+year;
			
			String to_month_last_day = Validations.lastDayOfMonth(to_month_first_day);
			
			gl_data = getGLDetailsForTwoDates(from_month_first_day,to_month_last_day);
			
		}catch(Exception sql){sql.printStackTrace();}
		
	 	return gl_data;
	 }
	 

	 // Method added by Murugesh on 08/04/2006
	public double ConsolidatedOpeningBalance(String gl_type,int gl_code,String date) 
	{
		Connection con=null;
		double debit=0.0,credit=0.0;
		double opening_balance=0.0;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			String year=null;
			String month=null;
			String day=null;
			//String previous_month_date=Validations.previousMonthFirstDay(date);
			int year_month=convertToYYYYMM(date);
			
			ResultSet rs=stmt.executeQuery("select sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum from MonthlyConsolidation where gl_type='"+gl_type+"' and gl_code="+gl_code+" and yr_mth<"+year_month+" ");
			if(rs.next()){
				debit=rs.getDouble("debit_sum");
				credit=rs.getDouble("credit_sum");
			}
			
			rs=stmt.executeQuery("select max(yr_mth) as last_year_month from MonthlyConsolidation where gl_type='"+gl_type+"' and gl_code="+gl_code+" and yr_mth<"+year_month+" ");
				
			String last_year_month=null;
			if(rs.next()){
				last_year_month=rs.getString("last_year_month");
				System.out.println("the last_year_month in "+last_year_month);
			}
			System.out.println("the last_year_month in "+last_year_month);
			if(last_year_month!=null){
				day="01";
				month=last_year_month.substring(4,6);
				year=last_year_month.substring(0,4);
				String last_month=day+"/"+month+"/"+year;
				
				System.out.println(" the last month is "+last_month);
				String next_month_first_day=Validations.nextMonthFirstDay(last_month);
				String previous_day = Validations.addDays(date,-1);
				
				rs=stmt.executeQuery("select sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum from DailyConsolidation where gl_type='"+gl_type+"' and gl_code="+gl_code+" and trn_dt between '"+Validations.convertYMD(next_month_first_day)+"' and '"+Validations.convertYMD(previous_day)+"'");
					
				if(rs.next()){
					debit += rs.getDouble("debit_sum");
					credit += rs.getDouble("credit_sum");
				}
			}
				
			opening_balance = credit-debit;
				
		}catch(SQLException sql){sql.printStackTrace();} 
		catch (DateFormatException e) {e.printStackTrace();
		}
		finally{
			try{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
			
		return opening_balance;
	}
		 
	// Method added by Murugesh on 08/04/2006
	public double ConsolidatedClosingBalance(String gl_type,int gl_code,String date) 
	{
		Connection con=null;
		double debit=0.0,credit=0.0;
		double closing_balance=0.0;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			String year=null;
			String month=null;
			String day=null;
			
			//String previous_month_date=Validations.previousMonthFirstDay(date);
			int year_month=convertToYYYYMM(date);
			
			ResultSet rs=stmt.executeQuery("select sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum from MonthlyConsolidation where gl_type='"+gl_type+"' and gl_code="+gl_code+" and yr_mth<"+year_month+" ");
			if(rs.next()){
				debit=rs.getDouble("debit_sum");
				credit=rs.getDouble("credit_sum");
			}
				
			rs=stmt.executeQuery("select max(yr_mth) as last_year_month from MonthlyConsolidation where gl_type='"+gl_type+"' and gl_code="+gl_code+" and yr_mth<"+year_month+" ");
			
			String last_year_month=null;
			if(rs.next()){
				last_year_month=rs.getString("last_year_month");
			}
				
			if(last_year_month!=null){
				day="01";
				month=last_year_month.substring(4,6);
				year=last_year_month.substring(0,4);
				String last_month=day+"/"+month+"/"+year;
					
				String next_month_first_day=Validations.nextMonthFirstDay(last_month);

				rs=stmt.executeQuery("select sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum from DailyConsolidation where gl_type='"+gl_type+"' and gl_code="+gl_code+" and trn_dt between '"+Validations.convertYMD(next_month_first_day)+"' and '"+Validations.convertYMD(date)+"'");
					
				if(rs.next()){
					debit += rs.getDouble("debit_sum");
					credit += rs.getDouble("credit_sum");
				}
			}
				
			closing_balance = credit-debit;
				
		}catch(SQLException sql){sql.printStackTrace();} 
		finally{
			try{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
			
		return closing_balance;
	}
		 
	// Method added by Murugesh on 10/04/2006
		 
	public double OpeningBalanceForBranch(String gl_type,int gl_code,int branch_code,String date) 
	{
		Connection con=null;
		double debit=0.0,credit=0.0;
		double opening_balance=0.0;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			String year=null;
			String month=null;
			String day=null;
			//String previous_month_date=Validations.previousMonthFirstDay(date);
			int year_month=convertToYYYYMM(date);
				
			ResultSet rs=stmt.executeQuery("select sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum from MonthlyConsolidation where gl_type='"+gl_type+"' and gl_code="+gl_code+" and yr_mth<"+year_month+" and  br_code="+branch_code+" ");
			if(rs.next()){
				debit=rs.getDouble("debit_sum");
				credit=rs.getDouble("credit_sum");
			}
				
			rs=stmt.executeQuery("select max(yr_mth) as last_year_month from MonthlyConsolidation where gl_type='"+gl_type+"' and gl_code="+gl_code+" and yr_mth<"+year_month+" and  br_code="+branch_code+" ");
				
			String last_year_month="199001"; // This has been set to this month year by default because if there is no value in the MonthlyConsolidation then it will be null
			if(rs.next()){
				if(rs.getString("last_year_month")!=null)
					last_year_month=rs.getString("last_year_month");
			}
			
			System.out.println("the last_year_month in "+last_year_month);
			if(last_year_month!=null){
				day="01";
				month=last_year_month.substring(4,6);
				year=last_year_month.substring(0,4);
				String last_month=day+"/"+month+"/"+year;
					
				System.out.println(" the last month is "+last_month);
				String next_month_first_day=Validations.nextMonthFirstDay(last_month);
				String previous_day = Validations.addDays(date,-1);
					
				rs=stmt.executeQuery("select sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum from DailyConsolidation where gl_type='"+gl_type+"' and gl_code="+gl_code+" and trn_dt between '"+Validations.convertYMD(next_month_first_day)+"' and '"+Validations.convertYMD(previous_day)+"' and  br_code="+branch_code+" ");
					
				if(rs.next()){
					debit += rs.getDouble("debit_sum");
					credit += rs.getDouble("credit_sum");
				}
			}
				
			opening_balance = credit-debit;
				
		}catch(SQLException sql){sql.printStackTrace();} 
		catch (DateFormatException e) {e.printStackTrace();
		}
		finally{
			try{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
			
		return opening_balance;
	}
		 
	// Method added by Murugesh on 08/04/2006
	public double ClosingBalanceForBranch(String gl_type,int gl_code,int branch_code,String date) 
	{
		Connection con=null;
		double debit=0.0,credit=0.0;
		double closing_balance=0.0;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			String year=null;
			String month=null;
			String day=null;
				
			//String previous_month_date=Validations.previousMonthFirstDay(date);
			int year_month=convertToYYYYMM(date);
				
			ResultSet rs=stmt.executeQuery("select sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum from MonthlyConsolidation where gl_type='"+gl_type+"' and gl_code="+gl_code+" and yr_mth<"+year_month+" and  br_code="+branch_code+" ");
			if(rs.next()){
				debit=rs.getDouble("debit_sum");
				credit=rs.getDouble("credit_sum");
			}
				
			rs=stmt.executeQuery("select max(yr_mth) as last_year_month from MonthlyConsolidation where gl_type='"+gl_type+"' and gl_code="+gl_code+" and yr_mth<"+year_month+" and  br_code="+branch_code+" ");
				
			String last_year_month="199001"; // This has been set to this month year by default because if there is no value in the MonthlyConsolidation then it will be null
			if(rs.next()){
				if(rs.getString("last_year_month")!=null)
					last_year_month=rs.getString("last_year_month");
			}
				
			if(last_year_month!=null){
				day="01";
				month=last_year_month.substring(4,6);
				year=last_year_month.substring(0,4);
				String last_month=day+"/"+month+"/"+year;
					
				String next_month_first_day=Validations.nextMonthFirstDay(last_month);

				rs=stmt.executeQuery("select sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum from DailyConsolidation where gl_type='"+gl_type+"' and gl_code="+gl_code+" and trn_dt between '"+Validations.convertYMD(next_month_first_day)+"' and '"+Validations.convertYMD(date)+"' and  br_code="+branch_code+" ");
				
				if(rs.next()){
					debit += rs.getDouble("debit_sum");
					credit += rs.getDouble("credit_sum");
				}
			}
				
			closing_balance = credit-debit;
				
		}catch(SQLException sql){sql.printStackTrace();} 
		finally{
			try{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
			
		return closing_balance;
	}
		 
	public String[][] getGLDetailsForTwoDatesForGLType(String date1,String date2,String gltype)
	 {
	 	Connection con=null;
		String gl_data[][]=null;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			//ResultSet rs=stmt.executeQuery("select gm.gl_type,gm.gl_code,gm.gl_name from  GLMaster gm where gl_type='"+gltype+"' and  ((from_date<='"+Validations.convertYMD(date1)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date1)+"' and to_date>='"+Validations.convertYMD(date1)+"') or (from_date>='"+Validations.convertYMD(date1)+"' and to_date<='"+Validations.convertYMD(date2)+"') or (from_date<='"+Validations.convertYMD(date2)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date2)+"' and to_date>='"+Validations.convertYMD(date2)+"'))  order by gl_type,gl_code");
			ResultSet rs=stmt.executeQuery("select * from GLMaster where from_date<='"+Validations.convertYMD(date2)+"' and ((to_date is null or to_date='') or to_date>='"+Validations.convertYMD(date1)+"' order by gl_type,gl_code);");
			if(rs.last()){
				gl_data = new String[rs.getRow()][3];
				System.out.println("gldata="+gl_data.length);
			}
			rs.beforeFirst();
			int count=0;
			if(gl_data!=null){
				while(rs.next()){
					gl_data[count][0]=rs.getString("gl_type");
					gl_data[count][1]=rs.getString("gl_code");
					gl_data[count][2]=rs.getString("gl_name");
					count++;
				}
			}
				
		}catch(SQLException sql){sql.printStackTrace();}
		finally{
			try{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
	 	return gl_data;
	 }
	 
	
	 /*public MonthObject[] mthConsolidationBranch(int mth,int year,int from_glcode,String from_gltype,int to_glcode,String to_gltype)
	 {
	 	Statement stmt=null,stmt1=null;
	 	ResultSet rs=null,rs1=null;
	 	Connection con=null;
	 	String codetype;
	 	int glcode=0;
	 	String year_mth ;
	 	int rows;
	 	MonthObject mthConsObject[]=null;
	 	int i=0;
	 	String date1="";
	 	
	 	try
		{
	 		con=getConnection();
	 		stmt=con.createStatement();
	 		
	 		//rs=stmt.executeQuery("select bm.br_shnm, m.moduleabbr,dc.gl_type,dc.gl_code,gm.gl_name, trn_dt,((cash_cr+cg_cr+trf_cr)-(cash_dr+cg_dr+trf_dr))  as net_trans from DailyConsolidation dc,GLMasterPeriod gm, bm,Modules m where gm.gl_code=dc.gl_code and bm.br_code=dc.br_code and dc.gl_type=m.modulecode and YEAR(trn_dt)= "+year+" and MONTH(trn_dt)="+mth+" group by dc.gl_code,trn_dt,bm.br_code  having gl_code BETWEEN "+from_glcode+" and "+to_glcode+" and  dc.gl_type BETWEEN '"+from_gltype+"' and '"+to_gltype+"'");
	 		rs=stmt.executeQuery("select bm.br_shnm, m.moduleabbr,dc.gl_type,dc.gl_code,gm.gl_name, trn_dt,((cash_cr+cg_cr+trf_cr)-(cash_dr+cg_dr+trf_dr))  as net_trans from DailyConsolidation dc,GLMaster gm,BranchMaster bm,Modules m where gm.gl_code=dc.gl_code and bm.br_code=dc.br_code and dc.gl_type=m.modulecode and YEAR(trn_dt)= "+year+" and MONTH(trn_dt)="+mth+" group by dc.gl_code,trn_dt,bm.br_code  having gl_code BETWEEN "+from_glcode+" and "+to_glcode+" and  dc.gl_type BETWEEN '"+from_gltype+"' and '"+to_gltype+"'");
	 		
			rs.last();
	 		rows=rs.getRow();
	 		rs.beforeFirst();
	 		//mthConsObject[][]=new mthConsObject[rows][7];
	 		mthConsObject =new MonthObject[rows];
		 	System.out.println("rows=" +rows);
		 	while(rs.next())
		 	{
			mthConsObject[i]=new MonthObject();
			
			if(i==0){
			    glcode=rs.getInt("dc.gl_code");
			    
			    date1=convertDateToString(String.valueOf(rs.getDate(6)));
			    mthConsObject[i].setOpeningBal(ConsolidatedOpeningBalance(rs.getString("dc.gl_type"),rs.getInt("dc.gl_code"),date1));
			    System.out.println(mthConsObject[i].getOpeningBal());
			    mthConsObject[i].setClosingBal(ConsolidatedClosingBalance(rs.getString("dc.gl_type"),rs.getInt("dc.gl_code"),date1));
			}
			if(glcode!=rs.getInt("dc.gl_code") || date1!=convertDateToString(String.valueOf(rs.getDate(6))))
			{
			glcode=rs.getInt("dc.gl_code");    
			date1=convertDateToString(String.valueOf(rs.getDate(6)));
			mthConsObject[i].setClosingBal(ConsolidatedClosingBalance(rs.getString("dc.gl_type"),rs.getInt("dc.gl_code"),convertDateToString(String.valueOf(rs.getDate(6)))));
			mthConsObject[i].setOpeningBal(ConsolidatedOpeningBalance(rs.getString("dc.gl_type"),glcode,convertDateToString(String.valueOf(rs.getDate(6)))));
			System.out.println("gl code="+glcode);
		    System.out.println("open bal="+mthConsObject[i].getOpeningBal());
			System.out.println(mthConsObject[i].getOpeningBal());
			}
			mthConsObject[i].setbr_code(rs.getString("bm.br_shnm"));
	 		mthConsObject[i].setgl_code(rs.getInt("dc.gl_code"));
	 		mthConsObject[i].setgl_type(rs.getString("dc.gl_type"));
	 		mthConsObject[i].setmoduleabbr(rs.getString("m.moduleabbr"));
	 		mthConsObject[i].setgl_name(rs.getString("gm.gl_name"));
	 		mthConsObject[i].setdate(convertDateToString(String.valueOf(rs.getDate(6))));
	 		mthConsObject[i].setnet_trans(rs.getDouble("net_trans"));
	 		i++;
			}
		}
	 	catch(Exception e){e.printStackTrace();}
		finally{
			try{
				con.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		System.out.println(" mth length="+mthConsObject.length);
	 	return mthConsObject;
}
	 
	 public MonthObject[] mthConsolidation(int mth,int year,int from_glcode,String from_gltype,int to_glcode,String to_gltype)
	 {
	 	Statement stmt=null,stmt1=null;
	 	ResultSet rs=null,rs1=null;
	 	Connection con=null;
	 	String codetype,gltype=" ";
	 	int glcode=0;
	 	String year_mth ;
	 	int rows;
	 	MonthObject mthConsObject[]=null;
	 	int i=0;
	 	String date1="";
	 	
	 	try
		{
	 		con=getConnection();
	 		stmt=con.createStatement();
	 		rs=stmt.executeQuery("select  m.moduleabbr,dc.gl_type,dc.gl_code,gm.gl_name, trn_dt,sum(((cash_cr+cg_cr+trf_cr)-(cash_dr+cg_dr+trf_dr)))  as net_trans from DailyConsolidation dc,GLMaster gm,Modules m where gm.gl_code=dc.gl_code and dc.gl_type=m.modulecode and YEAR(trn_dt)= "+year+" and MONTH(trn_dt)="+mth+" group by dc.gl_code,trn_dt  having gl_code BETWEEN "+from_glcode+" and "+to_glcode+" and  dc.gl_type BETWEEN '"+from_gltype+"' and '"+to_gltype+"'");
			rs.last();
	 		rows=rs.getRow();
	 		rs.beforeFirst();
	 		mthConsObject =new MonthObject[rows];
		 	System.out.println("rows=" +rows);
		 	while(rs.next())
		 	{
			mthConsObject[i]=new MonthObject();
			if(i==0)
			{
				glcode=rs.getInt("dc.gl_code");
				gltype=rs.getString("dc.gl_type");
				date1=convertDateToString(String.valueOf(rs.getDate(5)));
				mthConsObject[i].setOpeningBal(ConsolidatedOpeningBalance(rs.getString("dc.gl_type"),rs.getInt("dc.gl_code"),date1));
			}
			if(!(glcode==rs.getInt("dc.gl_code")&& gltype.equalsIgnoreCase(rs.getString("dc.gl_type"))))    
			{
			    date1=convertDateToString(String.valueOf(rs.getDate(5)));
			    mthConsObject[i].setOpeningBal(ConsolidatedOpeningBalance(rs.getString("dc.gl_type"),rs.getInt("dc.gl_code"),date1));
			    System.out.println("gl code="+rs.getInt("dc.gl_code"));
			    System.out.println("open bal="+mthConsObject[i].getOpeningBal());
			
			    System.out.println(mthConsObject[i].getOpeningBal());
			    mthConsObject[i].setClosingBal(ConsolidatedClosingBalance(rs.getString("dc.gl_type"),rs.getInt("dc.gl_code"),date1));
			}
			
	 		mthConsObject[i].setgl_code(rs.getInt("dc.gl_code"));
	 		mthConsObject[i].setgl_type(rs.getString("dc.gl_type"));
	 		mthConsObject[i].setmoduleabbr(rs.getString("m.moduleabbr"));
	 		mthConsObject[i].setgl_name(rs.getString("gm.gl_name"));
	 		mthConsObject[i].setdate(convertDateToString(String.valueOf(rs.getDate(5))));
	 		mthConsObject[i].setnet_trans(rs.getDouble("net_trans"));
	 		glcode=rs.getInt("dc.gl_code");
			gltype=rs.getString("dc.gl_type");
	 		i++;
			}
		}
	 	catch(Exception e){e.printStackTrace();}
		finally{
			try{
				con.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		System.out.println(" mth length="+mthConsObject.length);
	 	return mthConsObject;
}
*/	 // Method added by Murugesh on 07/04/2006
	 public String[] getCashGLTypeCode()
	 {
		Connection con=null;
		String cash_data[]=new String[2];
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1019001'");
			if(rs.last()){
				cash_data[0]=rs.getString("gl_type");
				cash_data[1]=rs.getString("gl_code");
			}
			
				
		}catch(SQLException sql){sql.printStackTrace();}
		finally{
			try{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
	 	return cash_data;
	 }
	 
	 // Method added by Murugesh on 12/04/2006
	 /*public String[][] getExpensesDetails(String date1,String date2)
	 {
	 	String gldata[][]=null;
	 	String expenses_gldata[][]=null;
	 	String expenses_report_data[][]=null;
	 	int expenses_report_data_length=0;
	 	
	 	try{
	 		
	 		gldata = getGLDetailsForTwoDates(date1,date2);
	 		if(gldata!=null){
	 			
	 			expenses_gldata = getParticularGLTypeCodes(gldata,"4"); // getting only Expenses gl_type,codes from the entire data
	 			
	 			// retreving expenses data
	 			if(expenses_gldata!=null){
	 				expenses_report_data = new String[expenses_gldata.length][5];
	 				expenses_report_data_length= expenses_gldata.length;
	 				
	 				for(int i=0;i<expenses_gldata.length;i++){
	 					if(expenses_gldata[i][1]!=null && expenses_gldata[i][1].toString().trim().substring(3,6).equalsIgnoreCase("000")){ // This condition is checked whether it is sub heading or not, for sub heading no need to calculate the closing balance
	 						expenses_report_data[i][0]="  ";
	 						expenses_report_data[i][4]="  ";
	 					}
	 					else{
	 						expenses_report_data[i][0]=DoubleFormat.doubleToString(ClosingBalance(expenses_gldata[i][0],Integer.parseInt(expenses_gldata[i][1]),date1),2); // expenses Closing balance as on first date
	 						expenses_report_data[i][4]=DoubleFormat.doubleToString(ClosingBalance(expenses_gldata[i][0],Integer.parseInt(expenses_gldata[i][1]),date2),2); // expenses Closing balance as on first date
	 					}
	 					expenses_report_data[i][1]=expenses_gldata[i][0]; // gl_type
	 					expenses_report_data[i][2]=expenses_gldata[i][1]; // gl_code
	 					expenses_report_data[i][3]=expenses_gldata[i][2]; // gl_name
	 					
	 				}
	 			}
	 		}
	 	}catch(Exception e){e.printStackTrace();}
	 	return expenses_report_data;
	 }
	 
	 // Method added by Murugesh on 17/04/2006
	 public String[][] getIncomeDetails(String date1,String date2)
	 {
	 	String gldata[][]=null;
	 	String income_gldata[][]=null;
	 	String income_report_data[][]=null;
	 	int income_report_data_length=0;
	 	
	 	try{
	 		
	 		gldata = getGLDetailsForTwoDates(date1,date2);
	 		if(gldata!=null){
	 			income_gldata = getParticularGLTypeCodes(gldata,"3"); // getting only income gl_type,codes from the entire data
	 			// retreving income data
	 			if(income_gldata!=null){
	 				income_report_data = new String[income_gldata.length][5];
	 				income_report_data_length = income_gldata.length;
	 				
	 				for(int i=0;i<income_gldata.length;i++){
	 					if(income_gldata[i][1]!=null && income_gldata[i][1].toString().trim().substring(3,6).equalsIgnoreCase("000")){ // This condition is checked whether it is sub heading or not, for sub heading no need to calculate the closing balance
	 						income_report_data[i][0]="    ";
	 						income_report_data[i][4]="    ";
	 					}
	 					else{
	 						income_report_data[i][0]=DoubleFormat.doubleToString(ClosingBalance(income_gldata[i][0],Integer.parseInt(income_gldata[i][1]),date1),2); // Income Closing balance as on first date
		 					income_report_data[i][4]=DoubleFormat.doubleToString(ClosingBalance(income_gldata[i][0],Integer.parseInt(income_gldata[i][1]),date2),2); // Income Closing balance as on first date
	 					}
	 					
	 					income_report_data[i][1]=income_gldata[i][0]; // gl_type
	 					income_report_data[i][2]=income_gldata[i][1]; // gl_code
	 					income_report_data[i][3]=income_gldata[i][2]; // gl_name
	 					
	 				}
	 			}	
	 		}
	 		
	 	}catch(Exception e){e.printStackTrace();}
	 return income_report_data;
	 }
*/
	 // Method added by Murugesh on 12/04/2006
	 public String[][] getParticularGLTypeCodes(String gldata[][],String starts_with)
	 {
	 	String particular_data[][]=null;
	 	try{
	 		if(gldata!=null){
	 			int count=0;
	 			for(int i=0;i<gldata.length;i++){
	 				if(gldata[i][1]!=null && gldata[i][1].startsWith(starts_with))
	 					count++;
	 			}
	 			
	 			particular_data = new String[count][4];
	 			count=0;
	 			
	 			for(int i=0;i<gldata.length;i++){
	 				if(gldata[i][1]!=null && gldata[i][1].startsWith(starts_with)){
	 					particular_data[count]=gldata[i];
	 					count++;
	 				}
	 			}
	 		}
	 	}catch(Exception e){e.printStackTrace();}
	 	return particular_data;
	 }
	 
	 public DayBookObject[] ConsolDayBookOpeningClosingBal(String date)
	 { 
	     DayBookObject consoldaybook_object[]=null; 
	     //DayBookObject grandtotal[]=null;
	     Connection con=null;
	     String cash_gltype,br_shnm;
	     int cash_glcode,br_code;
	     double cashopen_balforbr=0.0,cashclose_balforbr=0.0,consolopen_bal=0.0,consolclose_bal=0.0;
	    	    
	    System.out.println("before try");	        
	    try
		{
	    	con=getConnection();
	    	String branchdet[][]=null;
	    	String cash_gltypecode[]=null;
	    	branchdet=getBranchDetails();
	    	
	    	cash_gltypecode=getCashGLTypeCode();
	    	cash_gltype=cash_gltypecode[0];
	    	cash_glcode=Integer.parseInt(cash_gltypecode[1]);
	    	System.out.println("branch det length="+branchdet.length);
	    	consoldaybook_object=new DayBookObject[branchdet.length];
	    	//grandtotal=getBranchConsolidatedDayBook( date,query_string,2);
	    	int i;
	    	for( i=0;i<branchdet.length;i++)
	    	{
	    				
	    		consoldaybook_object[i]=new DayBookObject();
	    		br_code=Integer.parseInt(branchdet[i][0]);
	    		System.out.println("branch code="+br_code);
	    	    
	    		br_shnm=branchdet[i][3];
	    		System.out.println("branch name="+br_shnm);
	    
	    		cashopen_balforbr=OpeningBalanceForBranch(cash_gltype,cash_glcode,br_code,date);
	    		cashclose_balforbr=ClosingBalanceForBranch(cash_gltype,cash_glcode,br_code,date);
	    		consolopen_bal=ConsolidatedOpeningBalance(cash_gltype,cash_glcode,date);
	    		consolclose_bal=ConsolidatedClosingBalance(cash_gltype,cash_glcode,date);
	    		consoldaybook_object[i].setbr_code(br_code);
	    		consoldaybook_object[i].setbr_shnm(br_shnm);
	    		consoldaybook_object[i].setcashopen_bal(cashopen_balforbr);
	    		consoldaybook_object[i].setcashclose_bal(cashclose_balforbr);
	    		consoldaybook_object[0].setconsolopen_bal(consolopen_bal);
	    		consoldaybook_object[0].setconsolclose_bal(consolclose_bal);
	    		consoldaybook_object[0].setcash_glcode(cash_glcode);    	        
	     	}//end of for
	    	   				
	   	}//end of try
	    	
	    catch(Exception e){e.printStackTrace();}
		finally{
			try{
				con.close();
			}catch(Exception e){e.printStackTrace();}
		}
		
		return consoldaybook_object;
	 }//method added by shubha on 18/04/06
	


		// Method added by Murugesh on 27/04/2006
	 public DayBookObject[] getDayBookDetails(String date,String string_qry)
	 {
	 	System.out.println("query string="+string_qry);
	 	Connection con=null;
	 	DayBookObject day_book_object[]=null;
	 	try{
	 		con=getConnection();
	 		Statement stmt=con.createStatement();
	 		Statement stmt_select=con.createStatement();
	 		Statement stmt_update=con.createStatement();
	 		ResultSet rs,rs1 =null;
	 		
	 		// The following set of queries has to be executed simultaneously to get the result
	 		{
	 			stmt.addBatch("drop table if exists gl_temp_day_book ");
	 			stmt.addBatch("drop table if exists month_data_day_book");
	 			stmt.addBatch("drop table if exists daily_data_day_book");
	 			stmt.addBatch("drop table if exists day_month_day_book");
	 			stmt.addBatch("drop table if exists daily_day_book");
	 			stmt.executeBatch();
	 			
	 			stmt_update.executeUpdate("create temporary table gl_temp_day_book select gm.gl_type,gm.gl_code,gm.gl_name from  GLMaster gm where  ((from_date<='"+Validations.convertYMD(date)+"'and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))  order by gl_type,gl_code;");

	 			stmt_update.executeUpdate("create temporary table month_data_day_book (gl_type varchar(20),gl_code int(11),gl_name varchar(50),debit_sum double(30,5) not null default 0 ,credit_sum double(30,5) not null default 0.0 ,next_month date not null)");
	 			rs1 =stmt_select.executeQuery("select gt.*,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum,max(concat(left((period_add(yr_mth,1)),4),'-',right((period_add(yr_mth,1)),2),'-','01')) from gl_temp_day_book as gt left join MonthlySummary as ms on  ms.yr_mth<"+convertToYYYYMM(date)+" and ms.gl_type=gt.gl_type and ms.gl_code=gt.gl_code group by gl_type,gl_code");
		 		if(rs1!=null)
		 		{
		 			rs1.last();
		 			rs1.beforeFirst();
		 			
		 			while(rs1.next()){
		 				//System.out.println("cash_dr----->"+rs1.getString("cash_dr"));
		 				//System.out.println("cg_dr------->"+rs1.getString("cg_dr"));
		 				//System.out.println("trf_dr------>"+rs1.getString("trf_dr"));
		 				System.out.println("Debit Sum-1111-->"+rs1.getString("debit_sum"));
		 				//System.out.println("cash_cr----->"+rs1.getString("cash_cr"));
		 				//System.out.println("cg_cr------->"+rs1.getString("cg_cr"));
		 				//System.out.println("trf_cr------>"+rs1.getString("trf_cr"));
		 				System.out.println("Credit Sum-1111-->"+rs1.getString("credit_sum"));
		 			}	
		 		}
	 			stmt_update.executeUpdate("insert into month_data_day_book select gt.*,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum,max(concat(left((period_add(yr_mth,1)),4),'-',right((period_add(yr_mth,1)),2),'-','01')) from gl_temp_day_book as gt left join MonthlySummary as ms on  ms.yr_mth<"+convertToYYYYMM(date)+" and ms.gl_type=gt.gl_type and ms.gl_code=gt.gl_code group by gl_type,gl_code");
	 			stmt_update.executeUpdate("create temporary table daily_data_day_book select md.gl_type,md.gl_code,md.gl_name,sum(ds.cash_cr)+sum(ds.cg_cr)+sum(ds.trf_cr)-(sum(ds.cash_dr)+sum(ds.cg_dr)+sum(ds.trf_dr))as day_balance from  month_data_day_book as md,DailySummary as ds where   md.gl_type =ds.gl_type and md.gl_code = ds.gl_code and (trn_dt >=md.next_month and trn_dt<='"+Validations.convertYMD(date)+"') group by md.gl_type,md.gl_code");
	 			stmt_update.executeUpdate("create temporary table day_month_day_book (gl_type varchar(20),gl_code int(11),gl_name varchar(50),month_balance double(20,16)  not null Default 0.0,day_balance double(20,16)  not null Default 0.0)");
	 			stmt_update.executeUpdate("insert into day_month_day_book select md.gl_type,md.gl_code,md.gl_name, credit_sum-debit_sum,day_balance from month_data_day_book md left join  daily_data_day_book as dd on md.gl_type=dd.gl_type and md.gl_code=dd.gl_code");
	 			stmt_update.executeUpdate("create temporary table daily_day_book select * from DailySummary where  trn_dt='"+Validations.convertYMD(date)+"'");
	 		   if(string_qry.equals(" "))
	 		   {
	 			rs =stmt_select.executeQuery("select moduleabbr,dm.gl_code,dm.gl_type,dm.gl_name,ds.cash_dr,ds.cg_dr,ds.trf_dr,(ds.cash_dr+ds.cg_dr+ds.trf_dr)as debit_sum,ds.cash_cr,ds.cg_cr,ds.trf_cr,(ds.cash_cr+ds.cg_cr+ds.trf_cr) as credit_sum,((ds.cash_cr+ds.cg_cr+ds.trf_cr)-(ds.cash_dr+ds.cg_dr+ds.trf_dr)) as net_tran,month_balance+day_balance as balance from day_month_day_book as dm left join daily_day_book as ds  on dm.gl_type=ds.gl_type and dm.gl_code=ds.gl_code,Modules as mo where mo.modulecode=dm.gl_type order by dm.gl_type,dm.gl_code");
	 		   }
	 		   else
	 		   	rs =stmt_select.executeQuery("select moduleabbr,dm.gl_code,dm.gl_type,dm.gl_name,ds.cash_dr,ds.cg_dr,ds.trf_dr,(ds.cash_dr+ds.cg_dr+ds.trf_dr)as debit_sum,ds.cash_cr,ds.cg_cr,ds.trf_cr,(ds.cash_cr+ds.cg_cr+ds.trf_cr) as credit_sum,((ds.cash_cr+ds.cg_cr+ds.trf_cr)-(ds.cash_dr+ds.cg_dr+ds.trf_dr)) as net_tran,month_balance+day_balance as balance from day_month_day_book as dm left join daily_day_book as ds  on dm.gl_type=ds.gl_type and dm.gl_code=ds.gl_code,Modules as mo where mo.modulecode=dm.gl_type and "+string_qry+" order by dm.gl_type,dm.gl_code");
	 			stmt.executeBatch();
	 		}
	 		
	 		if(rs!=null){
	 			
	 			rs.last();
	 			day_book_object = new DayBookObject[rs.getRow()];
	 			rs.beforeFirst();
	 			int row=0;
	 			while(rs.next()){
	 				
	 				day_book_object[row] = new DayBookObject();
	 				
	 				day_book_object[row].setgl_abbr(rs.getString("moduleabbr"));
	 				day_book_object[row].setGLType(rs.getString("gl_type"));
	 				day_book_object[row].setgl_code(rs.getInt("gl_code"));
	 				day_book_object[row].setgl_name(rs.getString("gl_name"));
	 				day_book_object[row].setdr_cash(rs.getString("cash_dr")==null? 0 : rs.getDouble("cash_dr"));
	 				day_book_object[row].setdr_clear(rs.getString("cg_dr")==null? 0 : rs.getDouble("cg_dr"));
	 				day_book_object[row].setdr_trans(rs.getString("trf_dr")==null? 0 : rs.getDouble("trf_dr"));
	 				day_book_object[row].setdr_total(rs.getString("debit_sum")==null? 0 : rs.getDouble("debit_sum"));
	 				
	 				day_book_object[row].setcr_cash(rs.getString("cash_cr")==null? 0 : rs.getDouble("cash_cr"));
	 				day_book_object[row].setcr_clear(rs.getString("cg_cr")==null? 0 : rs.getDouble("cg_cr"));
	 				day_book_object[row].setcr_trans(rs.getString("trf_cr")==null? 0 : rs.getDouble("trf_cr"));
	 				day_book_object[row].setcr_total(rs.getString("credit_sum")==null? 0 : rs.getDouble("credit_sum"));
	 				
	 				day_book_object[row].set_net(rs.getString("net_tran")==null? 0 : rs.getDouble("net_tran"));
	 				day_book_object[row].setclose_bal(rs.getString("balance")==null? 0 : rs.getDouble("balance"));
	 				
	 				row++;
	 			}
	 		}
	 	}catch(SQLException sql){
	 		sessioncontext.setRollbackOnly();
	 		sql.printStackTrace();
	 		} 
	 	finally{
	 		try{
	 			con.close();
	 		}catch(SQLException sql){sql.printStackTrace();}
	 	}
	 	return day_book_object;
	 }



	//Method added by Murugesh on 27/04/2006
	/**
	 * @author Murugesh
	 * This function is used to retreive the branch consolidation or consolidated daybook
	 * @since The Theme: If u send 1 as the third parameter then function will return branch 
	 * consolidation and if u pass 2 as third parameter then it will return consolidated daybook
	 * 
	 * @param String date
	 * @param String query_string
	 * @param int value
	 * 
	 * @return DayBookObject[]
	 */
public DayBookObject[] getBranchConsolidatedDayBook(String date,String query_string,int value)
{
	Connection con=null;
	DayBookObject day_book_object[]=null;
	Statement stmt=null,stmt_select=null,stmt_update=null;
	try{
		con=getConnection();
		stmt=con.createStatement();
		stmt_select=con.createStatement();
		stmt_update=con.createStatement();
		ResultSet rs =null;
		
		// The following set of queries has to be executed simultaneously to get the result
		{
			
			stmt.addBatch("drop table if exists gl_temp_day_book ");
			stmt.addBatch("drop table if exists month_data_day_book ");
			stmt.addBatch("drop table if exists month_data_cons;");
			stmt.addBatch("drop table if exists daily_data_cons;");
			stmt.addBatch("drop table if exists day_month_cons;");
			stmt.addBatch("drop table if exists daily_cons;");
			stmt.addBatch("drop table if exists cons_day_book;");
			
			stmt.executeBatch();
			
			stmt_update.addBatch("create temporary table gl_temp_day_book select gm.gl_type,gm.gl_code,gm.gl_name,br_code from  GLMaster gm, BranchMaster as bm where  bm.br_code%1111!=0 and  ((from_date<='"+Validations.convertYMD(date)+"'and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))  order by gl_type,gl_code;");
			stmt_update.addBatch("delete from gl_temp_day_book where gl_code like '___000' and br_code<>1");
			stmt_update.addBatch("create temporary table  month_data_day_book select gl_type,gl_code,br_code,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum,max(concat(left((period_add(yr_mth,1)),4),'-',right((period_add(yr_mth,1)),2),'-','01')) as next_month from MonthlyConsolidation  where yr_mth<"+convertToYYYYMM(date)+"  group by gl_type,gl_code,br_code");
			stmt_update.addBatch("create temporary table month_data_cons (gl_type varchar(20),gl_code int(11),gl_name varchar(20),br_code varchar(20),debit_sum double(19,2) not null default 0.0 ,credit_sum double(19,2) not null default 0.0 ,next_month  date not null )");
			stmt_update.addBatch("insert into month_data_cons select gt.*,debit_sum,credit_sum,next_month from gl_temp_day_book as gt left join month_data_day_book as md on gt.gl_type=md.gl_type and gt.gl_code=md.gl_code and gt.br_code=md.br_code order by gt.gl_type,gt.gl_code,gt.br_code");
			stmt_update.addBatch("create temporary table daily_data_cons select md.gl_type,md.gl_code,md.gl_name,md.br_code,(sum(ds.cash_cr)+sum(ds.cg_cr)+sum(ds.trf_cr))-(sum(ds.cash_dr)+sum(ds.cg_dr)+sum(ds.trf_dr))as day_balance from  month_data_cons as md ,DailyConsolidation as ds where   (md.br_code=ds.br_code and md.gl_type =ds.gl_type and md.gl_code = ds.gl_code ) and (trn_dt between md.next_month and '"+Validations.convertYMD(date)+"') group by md.gl_type,md.gl_code,md.br_code");
			stmt_update.addBatch("create temporary table day_month_cons (gl_type varchar(20),gl_code int(11),gl_name varchar(50),br_code varchar(20),month_balance double(19,2)  not null Default 0.0,day_balance double(19,2)  not null Default 0.0)");
			stmt_update.addBatch("insert into day_month_cons select md.gl_type,md.gl_code,md.gl_name,md.br_code, credit_sum-debit_sum,day_balance from month_data_cons md left join  daily_data_cons as dd on md.gl_type=dd.gl_type and md.gl_code=dd.gl_code and md.br_code=dd.br_code");
			stmt_update.addBatch("create temporary table daily_cons select * from DailyConsolidation where  trn_dt='"+Validations.convertYMD(date)+"'");
			if(value == 2)
				stmt_update.addBatch("create temporary table cons_day_book select moduleabbr,dm.gl_type,dm.gl_code,dm.gl_name,dm.br_code,ds.cash_dr,ds.cg_dr,ds.trf_dr,(ds.cash_dr+ds.cg_dr+ds.trf_dr)as debit_sum,ds.cash_cr,ds.cg_cr,ds.trf_cr,(ds.cash_cr+ds.cg_cr+ds.trf_cr) as credit_sum,((ds.cash_cr+ds.cg_cr+ds.trf_cr)-(ds.cash_dr+ds.cg_dr+ds.trf_dr)) as net_tran,month_balance+day_balance as balance from day_month_cons as dm left join daily_cons as ds  on dm.gl_type=ds.gl_type and dm.gl_code=ds.gl_code and dm.br_code=ds.br_code,Modules as mo where mo.modulecode=dm.gl_type order by dm.gl_type,dm.gl_code,dm.br_code");
			
			stmt_update.executeBatch();
			if(value == 1 && query_string.equals(" "))
				rs =stmt_select.executeQuery("select moduleabbr,dm.gl_type,dm.gl_code,dm.gl_name,dm.br_code,br.br_shnm,ds.cash_dr,ds.cg_dr,ds.trf_dr,(ds.cash_dr+ds.cg_dr+ds.trf_dr)as debit_sum,ds.cash_cr,ds.cg_cr,ds.trf_cr,(ds.cash_cr+ds.cg_cr+ds.trf_cr) as credit_sum,((ds.cash_cr+ds.cg_cr+ds.trf_cr)-(ds.cash_dr+ds.cg_dr+ds.trf_dr)) as net_tran,month_balance+day_balance as balance from day_month_cons as dm left join daily_cons as ds  on dm.gl_type=ds.gl_type and dm.gl_code=ds.gl_code and dm.br_code=ds.br_code,Modules as mo,BranchMaster as br where  br.br_code%1111!=0 and  mo.modulecode=dm.gl_type and dm.br_code=br.br_code  and (ds.cash_dr is not null and ds.cg_dr is not null and  ds.trf_dr is not null and ds.cash_cr is not null and ds.cg_cr is not null and ds.trf_cr is not null)  order by dm.gl_type,dm.gl_code,dm.br_code;");
			if(value == 1 && !query_string.equals(" "))
				rs =stmt_select.executeQuery("select moduleabbr,dm.gl_type,dm.gl_code,dm.gl_name,dm.br_code,br.br_shnm,ds.cash_dr,ds.cg_dr,ds.trf_dr,(ds.cash_dr+ds.cg_dr+ds.trf_dr)as debit_sum,ds.cash_cr,ds.cg_cr,ds.trf_cr,(ds.cash_cr+ds.cg_cr+ds.trf_cr) as credit_sum,((ds.cash_cr+ds.cg_cr+ds.trf_cr)-(ds.cash_dr+ds.cg_dr+ds.trf_dr)) as net_tran,month_balance+day_balance as balance from day_month_cons as dm left join daily_cons as ds  on dm.gl_type=ds.gl_type and dm.gl_code=ds.gl_code and dm.br_code=ds.br_code,Modules as mo,BranchMaster as br where br.br_code%1111!=0 and  mo.modulecode=dm.gl_type and dm.br_code=br.br_code and (ds.cash_dr is not null and ds.cg_dr is not null and  ds.trf_dr is not null and ds.cash_cr is not null and ds.cg_cr is not null and ds.trf_cr is not null) and "+query_string+" order by dm.gl_type,dm.gl_code,dm.br_code");
			 if (value==2 && query_string.equals(" "))
				rs =stmt_select.executeQuery("select moduleabbr,gl_type,gl_code,gl_name,sum(cash_dr) as cash_dr,sum(cg_dr) as cg_dr,sum(trf_dr) as trf_dr,sum(debit_sum) as debit_sum,sum(cash_cr) cash_cr,sum(cg_cr) as cg_cr,sum(trf_cr) as trf_cr,sum(credit_sum) as credit_sum,sum(net_tran) as net_tran,sum(balance) as balance from cons_day_book group  by gl_type,gl_code");
			if(value==2 && !query_string.equals(" "))
				rs =stmt_select.executeQuery("select moduleabbr,gl_type,gl_code,gl_name,sum(cash_dr) as cash_dr,sum(cg_dr) as cg_dr,sum(trf_dr) as trf_dr,sum(debit_sum) as debit_sum,sum(cash_cr) cash_cr,sum(cg_cr) as cg_cr,sum(trf_cr) as trf_cr,sum(credit_sum) as credit_sum,sum(net_tran) as net_tran,sum(balance) as balance from cons_day_book where "+query_string+" group  by gl_type,gl_code");
			if(value == 3 && query_string.equals(" "))
				rs =stmt_select.executeQuery("select moduleabbr,dm.gl_type,dm.gl_code,dm.gl_name,dm.br_code,br.br_shnm,ds.cash_dr,ds.cg_dr,ds.trf_dr,(ds.cash_dr+ds.cg_dr+ds.trf_dr)as debit_sum,ds.cash_cr,ds.cg_cr,ds.trf_cr,(ds.cash_cr+ds.cg_cr+ds.trf_cr) as credit_sum,((ds.cash_cr+ds.cg_cr+ds.trf_cr)-(ds.cash_dr+ds.cg_dr+ds.trf_dr)) as net_tran,month_balance+day_balance as balance from day_month_cons as dm left join daily_cons as ds  on dm.gl_type=ds.gl_type and dm.gl_code=ds.gl_code and dm.br_code=ds.br_code,Modules as mo,BranchMaster as br where br.br_code%1111!=0 and  mo.modulecode=dm.gl_type and dm.br_code=br.br_code  order by dm.gl_type,dm.gl_code,dm.br_code;");
			if(value == 3 && !query_string.equals(" "))
				rs =stmt_select.executeQuery("select moduleabbr,dm.gl_type,dm.gl_code,dm.gl_name,dm.br_code,br.br_shnm,ds.cash_dr,ds.cg_dr,ds.trf_dr,(ds.cash_dr+ds.cg_dr+ds.trf_dr)as debit_sum,ds.cash_cr,ds.cg_cr,ds.trf_cr,(ds.cash_cr+ds.cg_cr+ds.trf_cr) as credit_sum,((ds.cash_cr+ds.cg_cr+ds.trf_cr)-(ds.cash_dr+ds.cg_dr+ds.trf_dr)) as net_tran,month_balance+day_balance as balance from day_month_cons as dm left join daily_cons as ds  on dm.gl_type=ds.gl_type and dm.gl_code=ds.gl_code and dm.br_code=ds.br_code,Modules as mo,BranchMaster as br where  br.br_code%1111!=0 and  mo.modulecode=dm.gl_type and dm.br_code=br.br_code and "+query_string+" order by dm.gl_type,dm.gl_code,dm.br_code");
			
			
			stmt.executeBatch();
		}
		
		if(rs!=null){
			
			rs.last();
			day_book_object = new DayBookObject[rs.getRow()];
			rs.beforeFirst();
			int row=0;
			while(rs.next()){
				
				day_book_object[row] = new DayBookObject();
				
				day_book_object[row].setgl_abbr(rs.getString("moduleabbr"));
				day_book_object[row].setGLType(rs.getString("gl_type"));
				day_book_object[row].setgl_code(rs.getInt("gl_code"));
				day_book_object[row].setgl_name(rs.getString("gl_name"));
				
				if(value==1 || value==3){
					day_book_object[row].setbr_code(rs.getString("br_code")==null? 0 : rs.getInt("br_code"));
					day_book_object[row].setbr_shnm(rs.getString("br_shnm"));
				}
				
				day_book_object[row].setdr_cash(rs.getString("cash_dr")==null? 0 : rs.getDouble("cash_dr"));
				day_book_object[row].setdr_clear(rs.getString("cg_dr")==null? 0 : rs.getDouble("cg_dr"));
				day_book_object[row].setdr_trans(rs.getString("trf_dr")==null? 0 : rs.getDouble("trf_dr"));
				day_book_object[row].setdr_total(rs.getString("debit_sum")==null? 0 : rs.getDouble("debit_sum"));
				
				day_book_object[row].setcr_cash(rs.getString("cash_cr")==null? 0 : rs.getDouble("cash_cr"));
				day_book_object[row].setcr_clear(rs.getString("cg_cr")==null? 0 : rs.getDouble("cg_cr"));
				day_book_object[row].setcr_trans(rs.getString("trf_cr")==null? 0 : rs.getDouble("trf_cr"));
				day_book_object[row].setcr_total(rs.getString("credit_sum")==null? 0 : rs.getDouble("credit_sum"));
				
				day_book_object[row].set_net(rs.getString("net_tran")==null? 0 : rs.getDouble("net_tran"));
				day_book_object[row].setclose_bal(rs.getString("balance")==null? 0 : rs.getDouble("balance"));
				
				row++;
			}
		}
	}catch(SQLException sql){
		sessioncontext.setRollbackOnly();
		sql.printStackTrace();
		} 
	finally{
		try{
			stmt.executeBatch();
			con.close();
		}catch(SQLException sql){sql.printStackTrace();}
	}
	return day_book_object;
}

	// Method added by Murugesh on 29/04/2006
	/*public GLReportObject[] getBalance(String date,int gl_values)
	{
		Connection con=null;
		GLReportObject gl_object[]=null;
		Statement stmt=null,stmt_select=null,stmt_update=null;
		try{
			con=getConnection();
			stmt=con.createStatement();
			stmt_select=con.createStatement();
			stmt_update=con.createStatement();
			ResultSet rs =null;
			
			// The following set of queries has to be executed simultaneously to get the result
			{
				stmt.addBatch("drop table if exists gl_temp ");
				stmt.addBatch("drop table if exists month_data");
				stmt.addBatch("drop table if exists daily_data");
				stmt.addBatch("drop table if exists day_month");
				stmt.executeBatch();
				
				if(gl_values==0)
					stmt_update.addBatch("create temporary table gl_temp select gm.gl_type,gm.gl_code,gm.gl_name,gm.normal_cd from  GLMaster gm where  ((from_date<='"+Validations.convertYMD(date)+"'and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))  order by gl_type,gl_code");
				else
					stmt_update.addBatch("create temporary table gl_temp select gm.gl_type,gm.gl_code,gm.gl_name,gm.normal_cd from  GLMaster gm where  ((from_date<='"+Validations.convertYMD(date)+"'and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"')) and  gm.gl_code like '"+gl_values+"_____' order by gl_type,gl_code");
					
				stmt_update.addBatch("create temporary table month_data (gl_type varchar(20),gl_code int(11),gl_name varchar(50),normal_cd varchar(5),credit_sum double(19,2) not null default 0.0 ,debit_sum double(19,2) not null default 0.0 ,next_month date not null)");
				stmt_update.addBatch("insert into month_data select gt.*,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,max(concat(left((period_add(yr_mth,1)),4),'-',right((period_add(yr_mth,1)),2),'-','01')) from gl_temp as gt left join MonthlySummary as ms on  ms.yr_mth<"+convertToYYYYMM(date)+" and ms.gl_type=gt.gl_type and ms.gl_code=gt.gl_code group by gl_type,gl_code");
				stmt_update.addBatch("create temporary table daily_data select md.gl_type,md.gl_code,md.gl_name,md.normal_cd,(sum(ds.cash_cr)+sum(ds.cg_cr)+sum(ds.trf_cr))-(sum(ds.cash_dr)+sum(ds.cg_dr)+sum(ds.trf_dr))as day_balance from  month_data as md,DailySummary as ds where   md.gl_type =ds.gl_type and md.gl_code = ds.gl_code and (trn_dt >=md.next_month and trn_dt<='"+Validations.convertYMD(date)+"') group by md.gl_type,md.gl_code");
				stmt_update.addBatch("create temporary table day_month (gl_type varchar(20),gl_code int(11),gl_name varchar(50),normal_cd varchar(5),month_balance double(19,2)  not null Default 0.0,day_balance double(19,2)  not null Default 0.0)");
				stmt_update.addBatch("insert into day_month select md.gl_type,md.gl_code,md.gl_name,md.normal_cd, credit_sum-debit_sum,day_balance from month_data md left join  daily_data as dd on md.gl_type=dd.gl_type and md.gl_code=dd.gl_code");
				stmt_update.executeBatch();
				
				rs =stmt_select.executeQuery("select moduleabbr,dm.gl_code,dm.gl_type,dm.gl_name,dm.normal_cd,month_balance+day_balance as balance  from day_month as dm, Modules as mo where dm.gl_type=mo.modulecode order by gl_type,gl_code");
				stmt.executeBatch();
			}
			
			if(rs!=null){
				
				rs.last();
				gl_object = new GLReportObject[rs.getRow()];
				rs.beforeFirst();
				int row=0;
				while(rs.next()){
					
					gl_object[row] = new GLReportObject();
					
					gl_object[row].setGLAbbr(rs.getString("moduleabbr"));
					gl_object[row].setGLType(rs.getString("gl_type"));
					gl_object[row].setGLCode(rs.getInt("gl_code"));
					gl_object[row].setGLName(rs.getString("gl_name"));
					gl_object[row].setBalance(rs.getString("balance")==null ? 0.0:rs.getDouble("balance"));
					gl_object[row].setNormalCD(rs.getString("normal_cd")==null ? " ":rs.getString("normal_cd"));
					row++;
				}
			}
		}catch(SQLException sql){sql.printStackTrace();} 
		finally{
			try{
				stmt.executeBatch();
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
		return gl_object;
	}*/
	 
	// Method added by Murugesh on 29/04/2006
	public GLReportObject[] getBalanceTwoDates(String date1,String date2,int gl_values,String string_qry)
	{
		Connection con=null;
		GLReportObject gl_object[]=null;
		Statement stmt=null,stmt_select=null,stmt_update=null;
		try{
			con=getConnection();
			stmt=con.createStatement();
			stmt_select=con.createStatement();
			stmt_update=con.createStatement();
			ResultSet rs =null;
			String gl_type=null;
			int gl_code=0;
			// The following set of queries has to be executed simultaneously to get the result
			
				
				stmt.addBatch("drop table if exists gl_temp ;");
				
				stmt.addBatch("create table gl_temp (gl_type varchar(20), gl_code int(10),gl_name varchar(100),normal_cd varchar(5),first_bal double(30,2),second_bal double(30,2))");
				stmt.executeBatch();
				
				if(gl_values==0)
					stmt.execute("insert into gl_temp(gl_type,gl_code,gl_name,normal_cd) (select gl_type,gl_code,gl_name,normal_cd from GLMaster where from_date<='"+Validations.convertYMD(date2)+"' and (to_date is null or to_date='' or to_date>='"+Validations.convertYMD(date1)+"'))");
				else
					stmt.execute("insert into gl_temp(gl_type,gl_code,gl_name,normal_cd) (select gl_type,gl_code,gl_name,normal_cd from GLMaster where  gl_code like '"+gl_values+"_____' and from_date<='"+Validations.convertYMD(date2)+"' and (to_date is null or to_date='' or to_date>='"+Validations.convertYMD(date1)+"'))");
				
				
				/*stmt.execute("insert into gl_temp(gl_type,gl_code,gl_name,normal_cd) (select gl_type,gl_code,gl_name,normal_cd from GLMaster where from_date<='"+Validations.convertYMD(date2)+"' and (to_date is null or to_date='' or to_date>='"+Validations.convertYMD(date1)+"'))");
				rs=stmt.executeQuery("select * from gl_temp ");
				while(rs.next())
				{
					gl_type=rs.getString("gl_type");
					gl_code=rs.getInt("gl_code");
					stmt_update.execute("update gl_temp set first_bal="+ClosingBalance(gl_type,gl_code,date1)+" ,second_bal="+ClosingBalance(gl_type, gl_code, date2)+" where gl_type='"+gl_type+"' and gl_code="+gl_code+"  ");
				}
				if(string_qry.equals(" "))
					   rs =stmt_select.executeQuery("select distinct moduleabbr,df.gl_code,df.gl_type,df.gl_name,first_bal,second_bal,df.normal_cd  from gl_temp as df, Modules as mo where df.gl_type=mo.modulecode   order by df.gl_type,df.gl_code;");
					else{
						System.out.println("query="+string_qry);
						rs =stmt_select.executeQuery("select distinct moduleabbr,df.gl_code,df.gl_type,df.gl_name,first_bal,second_bal,df.normal_cd  from gl_temp as df, Modules as mo where df.gl_type=mo.modulecode and "+string_qry+" order by df.gl_type,df.gl_code");
				}
				//stmt.execute("update gl_temp set first_bal="+ClosingBalance(, gl_code, date))
*/				
				
				stmt.addBatch("drop table if exists gl_temp ;");
				stmt.addBatch("drop table if exists month_data_first_date;");
				stmt.addBatch("drop table if exists daily_data_first_date;");
				stmt.addBatch("drop table if exists day_month_first_date;");
				stmt.addBatch("drop table if exists month_data_second_date;");
				stmt.addBatch("drop table if exists daily_data_second_date;");
				stmt.addBatch("drop table if exists day_month_second_date;");
				stmt.executeBatch();
				
				if(gl_values==0)
					stmt_update.addBatch("create temporary table gl_temp select gm.gl_type,gm.gl_code,gm.gl_name,gm.normal_cd from  GLMaster gm where ((from_date<='"+Validations.convertYMD(date1)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date1)+"' and to_date>='"+Validations.convertYMD(date1)+"') or (from_date>='"+Validations.convertYMD(date1)+"' and to_date<='"+Validations.convertYMD(date2)+"') or (from_date<='"+Validations.convertYMD(date2)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date2)+"' and to_date>='"+Validations.convertYMD(date2)+"')) order by gl_type,gl_code");
				else
					stmt_update.addBatch("create temporary table gl_temp select gm.gl_type,gm.gl_code,gm.gl_name,gm.normal_cd from  GLMaster gm where ((from_date<='"+Validations.convertYMD(date1)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date1)+"' and to_date>='"+Validations.convertYMD(date1)+"') or (from_date>='"+Validations.convertYMD(date1)+"' and to_date<='"+Validations.convertYMD(date2)+"') or (from_date<='"+Validations.convertYMD(date2)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date2)+"' and to_date>='"+Validations.convertYMD(date2)+"'))  and  gm.gl_code like '"+gl_values+"_____'order by gl_type,gl_code");
				
				stmt_update.addBatch("create temporary table month_data_first_date (gl_type varchar(20),gl_code int(11),gl_name varchar(50),normal_cd varchar(5),debit_sum double(19,2) not null default 0.0 ,credit_sum double(19,2) not null default 0.0 ,next_month  date not null)");
				stmt_update.addBatch("create temporary table month_data_second_date (gl_type varchar(20),gl_code int(11),gl_name varchar(50),normal_cd varchar(5),debit_sum double(19,2) not null default 0.0 ,credit_sum double(19,2) not null default 0.0 ,next_month  date not null)");
				stmt_update.addBatch("insert into month_data_first_date select gt.*,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum,max(concat(left((period_add(yr_mth,1)),4),'-',right((period_add(yr_mth,1)),2),'-','01')) from gl_temp as gt left join MonthlySummary as ms on  ms.yr_mth<"+convertToYYYYMM(date1)+" and ms.gl_type=gt.gl_type and ms.gl_code=gt.gl_code group by gl_type,gl_code");
				stmt_update.addBatch("insert into month_data_second_date select gt.*,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum,max(concat(left((period_add(yr_mth,1)),4),'-',right((period_add(yr_mth,1)),2),'-','01')) from gl_temp as gt left join MonthlySummary as ms on  ms.yr_mth<"+convertToYYYYMM(date2)+" and ms.gl_type=gt.gl_type and ms.gl_code=gt.gl_code group by gl_type,gl_code");
				stmt_update.addBatch("create temporary table daily_data_first_date select md.gl_type,md.gl_code,md.gl_name,md.normal_cd ,sum(ds.cash_cr)+sum(ds.cg_cr)+sum(ds.trf_cr)-(sum(ds.cash_dr)+sum(ds.cg_dr)+sum(ds.trf_dr))as day_balance from  month_data_first_date as md,DailySummary as ds where   md.gl_type =ds.gl_type and md.gl_code = ds.gl_code and (trn_dt >=md.next_month and trn_dt<='"+Validations.convertYMD(date1)+"') group by md.gl_type,md.gl_code");
				stmt_update.addBatch("create temporary table daily_data_second_date select md.gl_type,md.gl_code,md.gl_name,md.normal_cd,sum(ds.cash_cr)+sum(ds.cg_cr)+sum(ds.trf_cr)-(sum(ds.cash_dr)+sum(ds.cg_dr)+sum(ds.trf_dr))as day_balance from  month_data_second_date as md,DailySummary as ds where   md.gl_type =ds.gl_type and md.gl_code = ds.gl_code and (trn_dt >=md.next_month and trn_dt<='"+Validations.convertYMD(date2)+"') group by md.gl_type,md.gl_code");
				stmt_update.addBatch("create temporary table day_month_first_date (gl_type varchar(20),gl_code int(11),gl_name varchar(50),normal_cd varchar(5),month_balance double(19,2)  not null Default 0.0,day_balance double(19,2)  not null Default 0.0)");
				stmt_update.addBatch("create temporary table day_month_second_date (gl_type varchar(20),gl_code int(11),gl_name varchar(50),normal_cd varchar(5),month_balance double(19,2)  not null Default 0.0,day_balance double(19,2)  not null Default 0.0)");
				stmt_update.addBatch("insert into day_month_first_date select md.gl_type,md.gl_code,md.gl_name,md.normal_cd, credit_sum-debit_sum,day_balance from month_data_first_date md left join  daily_data_first_date as dd on md.gl_type=dd.gl_type and md.gl_code=dd.gl_code");
				stmt_update.addBatch("insert into day_month_second_date select md.gl_type,md.gl_code,md.gl_name, md.normal_cd,credit_sum-debit_sum,day_balance from month_data_second_date md left join  daily_data_second_date as dd on md.gl_type=dd.gl_type and md.gl_code=dd.gl_code");
				stmt_update.executeBatch();
				if(string_qry.equals(" "))
				   rs =stmt_select.executeQuery("select distinct moduleabbr,df.gl_code,df.gl_type,df.gl_name,round(df.month_balance+df.day_balance,2) as balance_first,round(ds.month_balance+ds.day_balance,2)  as balance_second ,df.normal_cd  from day_month_first_date as df, Modules as mo,day_month_second_date as ds where df.gl_type=mo.modulecode and df.gl_type=ds.gl_type and df.gl_code=ds.gl_code  order by df.gl_type,df.gl_code");
				else{
					System.out.println("query="+string_qry);
					rs =stmt_select.executeQuery("select moduleabbr,df.gl_code,df.gl_type,df.gl_name,round(df.month_balance+df.day_balance,2) as balance_first,round(ds.month_balance+ds.day_balance,2)  as balance_second,df.normal_cd from day_month_first_date as df, Modules as mo,day_month_second_date as ds where df.gl_type=mo.modulecode and df.gl_type=ds.gl_type and df.gl_code=ds.gl_code and "+string_qry+" order by df.gl_type,df.gl_code");
				}
				stmt.executeBatch();
			//}
			
			if(rs!=null){
				
				rs.last();
				gl_object = new GLReportObject[rs.getRow()];
				rs.beforeFirst();
				int row=0;
				while(rs.next()){
					
					gl_object[row] = new GLReportObject();
					
					gl_object[row].setGLAbbr(rs.getString("moduleabbr"));
					gl_object[row].setGLType(rs.getString("gl_type"));
					gl_object[row].setGLCode(rs.getInt("gl_code"));
					gl_object[row].setGLName(rs.getString("gl_name"));
					gl_object[row].setFirstDateBalance(rs.getDouble("balance_first"));
					gl_object[row].setSecondDateBalance(rs.getDouble("balance_second"));
					gl_object[row].setNormalCD(rs.getString("normal_cd")==null?" ":rs.getString("normal_cd"));
					System.out.println("gl_code="+rs.getInt("gl_code"));
					System.out.println("first bal="+rs.getDouble("balance_first"));
					System.out.println("second bal="+rs.getDouble("balance_second"));
					row++;
					
					
				}
			}
		}catch(Exception sql){
			sessioncontext.setRollbackOnly();
			sql.printStackTrace();
			} 
		finally{
			try{
				stmt.executeBatch();
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
		return gl_object;
	}
	
//	 Method added by Murugesh on 29/04/2006
	public GLReportObject[] getBalanceForBranch(String date,int gl_values)
	{
		Connection con=null;
		GLReportObject gl_object[]=null;
		Statement stmt=null,stmt_select=null,stmt_update=null;
		try{
			con=getConnection();
			stmt=con.createStatement();
			stmt_select=con.createStatement();
			stmt_update=con.createStatement();
			ResultSet rs =null;
			
			// The following set of queries has to be executed simultaneously to get the result
			{
				stmt.addBatch("drop table if exists gl_temp");
				stmt.addBatch("drop table if exists month_data");
				stmt.addBatch("drop table if exists month_data_cons");
				stmt.addBatch("drop table if exists daily_data_cons");
				stmt.addBatch("drop table if exists day_month_cons");
				stmt.addBatch("drop table if exists daily_cons");
				
				stmt.executeBatch();
				
				if(gl_values==0)
					stmt_update.addBatch("create temporary table gl_temp select gm.gl_type,gm.gl_code,gm.gl_name,br_code,gm.normal_cd from  GLMaster gm, BranchMaster as bm  bm.br_code%1111!=0 and  where  ((from_date<='"+Validations.convertYMD(date)+"'and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"')) order by gl_type,gl_code,br_code;");
				else
					stmt_update.addBatch("create temporary table gl_temp select gm.gl_type,gm.gl_code,gm.gl_name,br_code,gm.normal_cd from  GLMaster gm, BranchMaster as bm where  bm.br_code%1111!=0 and  ((from_date<='"+Validations.convertYMD(date)+"'and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"')) and gm.gl_code like '"+gl_values+"_____'order by gl_type,gl_code,br_code;");
				
				stmt_update.addBatch("delete from gl_temp where gl_code like '___000' and br_code<>1");
				stmt_update.addBatch("create temporary table  month_data select gl_type,gl_code,br_code,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum,max(concat(left((period_add(yr_mth,1)),4),'-',right((period_add(yr_mth,1)),2),'-','01')) as next_month from MonthlyConsolidation  where yr_mth<"+convertToYYYYMM(date)+" group by gl_type,gl_code,br_code");
				stmt_update.addBatch("create temporary table month_data_cons (gl_type varchar(20),gl_code int(11),gl_name varchar(20),br_code varchar(20),normal_cd varchar(5),debit_sum double(19,2) not null default 0.0 ,credit_sum double(19,2) not null default 0.0 ,next_month  date not null)");
				stmt_update.addBatch("insert into month_data_cons select gt.*,debit_sum,credit_sum,next_month from gl_temp as gt left join month_data as md on gt.gl_type=md.gl_type and gt.gl_code=md.gl_code and gt.br_code=md.br_code order by gt.gl_type,gt.gl_code,gt.br_code");
				stmt_update.addBatch("create temporary table daily_data_cons select md.gl_type,md.gl_code,md.gl_name,md.br_code,md.normal_cd,(sum(ds.cash_cr)+sum(ds.cg_cr)+sum(ds.trf_cr))-(sum(ds.cash_dr)+sum(ds.cg_dr)+sum(ds.trf_dr))as day_balance from  month_data_cons as md ,DailyConsolidation as ds where (md.br_code=ds.br_code and md.gl_type =ds.gl_type and md.gl_code = ds.gl_code ) and (trn_dt between md.next_month and '"+Validations.convertYMD(date)+"') group by md.gl_type,md.gl_code,md.br_code");
				stmt_update.addBatch("create temporary table day_month_cons (gl_type varchar(20),gl_code int(11),gl_name varchar(50),br_code varchar(20),normal_cd varchar(5),month_balance double(19,2)  not null Default 0.0,day_balance double(19,2)  not null Default 0.0)");
				stmt_update.addBatch("insert into day_month_cons select md.gl_type,md.gl_code,md.gl_name,md.br_code,md.normal_cd,credit_sum-debit_sum,day_balance from month_data_cons md left join  daily_data_cons as dd on md.gl_type=dd.gl_type and md.gl_code=dd.gl_code and md.br_code=dd.br_code");
				
				stmt_update.executeBatch();
				
				rs =stmt_select.executeQuery("select moduleabbr,dm.gl_code,dm.gl_type,dm.gl_name,dm.br_code,bm.br_shnm,dm.normal_cd,month_balance+day_balance as balance   from day_month_cons as dm, Modules as mo,BranchMaster as bm where  bm.br_code%1111!=0 and  dm.gl_type=mo.modulecode and dm.br_code=bm.br_code order by gl_type,gl_code,br_code");
				stmt.executeBatch();
			}
			
			if(rs!=null){
				
				rs.last();
				gl_object = new GLReportObject[rs.getRow()];
				rs.beforeFirst();
				int row=0;
				while(rs.next()){
					
					gl_object[row] = new GLReportObject();
					
					gl_object[row].setGLAbbr(rs.getString("moduleabbr"));
					gl_object[row].setGLType(rs.getString("gl_type"));
					gl_object[row].setGLCode(rs.getInt("gl_code"));
					gl_object[row].setGLName(rs.getString("gl_name"));
					gl_object[row].setBranchCode(rs.getString("br_code"));
					gl_object[row].setBranchName(rs.getString("br_shnm"));
					gl_object[row].setBalance(rs.getString("balance")==null ? 0.0:rs.getDouble("balance"));
					gl_object[row].setNormalCD(rs.getString("normal_cd")==null?" ":rs.getString("normal_cd"));
					row++;
				}
			}
		}catch(SQLException sql){
			sessioncontext.setRollbackOnly();
			sql.printStackTrace();
			} 
		finally{
			try{
				stmt.executeBatch();
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
		return gl_object;
	}
	
//	 Method added by Murugesh on 29/04/2006
	public GLReportObject[] getBalanceBranchTwoDates(String date1,String date2,int gl_values,int flag_branch,String str)
	{
		Connection con=null;
		GLReportObject gl_object[]=null;
		Statement stmt=null,stmt_select=null,stmt_update=null;
		try{
			con=getConnection();
			stmt=con.createStatement();
			stmt_select=con.createStatement();
			stmt_update=con.createStatement();
			ResultSet rs =null;
			
			// The following set of queries has to be executed simultaneously to get the result
			{
				stmt.addBatch("drop table if exists gl_temp");
				stmt.addBatch("drop table if exists month_data_first_date ;");
				stmt.addBatch("drop table if exists month_data_cons_first_date;");
				stmt.addBatch("drop table if exists daily_data_cons_first_date;");
				stmt.addBatch("drop table if exists day_month_cons_first_date;");
				stmt.addBatch("drop table if exists daily_cons_first_date;");
				stmt.addBatch("drop table if exists month_data_second_date ;");
				stmt.addBatch("	drop table if exists month_data_cons_second_date;");
				stmt.addBatch("drop table if exists daily_data_cons_second_date;");
				stmt.addBatch("drop table if exists day_month_cons_second_date;");
				stmt.addBatch("drop table if exists daily_cons_second_date;");
				stmt.addBatch("drop table if exists result_branch");
				stmt.addBatch("drop table if exists result");
				
				stmt.executeBatch();
				
				if(gl_values==0)
					stmt_update.addBatch("create temporary table gl_temp select gm.gl_type,gm.gl_code,gm.gl_name,br_code,gm.normal_cd from  GLMaster gm, BranchMaster as bm  where  bm.br_code%1111!=0 and  ((from_date<='"+Validations.convertYMD(date1)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date1)+"' and to_date>='"+Validations.convertYMD(date1)+"') or (from_date>='"+Validations.convertYMD(date1)+"' and to_date<='"+Validations.convertYMD(date2)+"') or (from_date<='"+Validations.convertYMD(date2)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date2)+"' and to_date>='"+Validations.convertYMD(date2)+"')) order by gl_type,gl_code,br_code;");
				else
					stmt_update.addBatch("create temporary table gl_temp select gm.gl_type,gm.gl_code,gm.gl_name,br_code,gm.normal_cd from  GLMaster gm, BranchMaster as bm where   bm.br_code%1111!=0 and  ((from_date<='"+Validations.convertYMD(date1)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date1)+"' and to_date>='"+Validations.convertYMD(date1)+"') or (from_date>='"+Validations.convertYMD(date1)+"' and to_date<='"+Validations.convertYMD(date2)+"') or (from_date<='"+Validations.convertYMD(date2)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date2)+"' and to_date>='"+Validations.convertYMD(date2)+"'))  and  gm.gl_code like '"+gl_values+"_____'order by gl_type,gl_code,br_code;");
				
				stmt_update.addBatch("delete from gl_temp where gl_code like '___000' and br_code<>1");
				stmt_update.addBatch("create temporary table  month_data_first_date select gl_type,gl_code,br_code,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum,max(concat(left((period_add(yr_mth,1)),4),'-',right((period_add(yr_mth,1)),2),'-','01')) as next_month from MonthlyConsolidation  where yr_mth<"+convertToYYYYMM(date1)+"  group by gl_type,gl_code,br_code");
				stmt_update.addBatch("create temporary table  month_data_second_date select gl_type,gl_code,br_code,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum,max(concat(left((period_add(yr_mth,1)),4),'-',right((period_add(yr_mth,1)),2),'-','01')) as next_month from MonthlyConsolidation  where yr_mth<"+convertToYYYYMM(date2)+"  group by gl_type,gl_code,br_code");
				stmt_update.addBatch("create temporary table month_data_cons_first_date (gl_type varchar(20),gl_code int(11),gl_name varchar(20),br_code varchar(20),normal_cd varchar(5),debit_sum double(19,2) not null default 0.0 ,credit_sum double(19,2) not null default 0.0 ,next_month  date not null )");
				stmt_update.addBatch("create temporary table month_data_cons_second_date (gl_type varchar(20),gl_code int(11),gl_name varchar(20),br_code varchar(20),normal_cd varchar(5),debit_sum double(19,2) not null default 0.0 ,credit_sum double(19,2) not null default 0.0 ,next_month  date not null )");
				stmt_update.addBatch("insert into month_data_cons_first_date select gt.*,debit_sum,credit_sum,next_month from gl_temp as gt left join month_data_first_date as md on gt.gl_type=md.gl_type and gt.gl_code=md.gl_code and gt.br_code=md.br_code order by gt.gl_type,gt.gl_code,gt.br_code ");
				stmt_update.addBatch("insert into month_data_cons_second_date select gt.*,debit_sum,credit_sum,next_month from gl_temp as gt left join month_data_second_date as md on gt.gl_type=md.gl_type and gt.gl_code=md.gl_code and gt.br_code=md.br_code order by gt.gl_type,gt.gl_code,gt.br_code ");
				stmt_update.addBatch("create temporary table daily_data_cons_first_date select md.gl_type,md.gl_code,md.gl_name,md.br_code,md.normal_cd,(sum(ds.cash_cr)+sum(ds.cg_cr)+sum(ds.trf_cr))-(sum(ds.cash_dr)+sum(ds.cg_dr)+sum(ds.trf_dr))as day_balance from  month_data_cons_first_date as md ,DailyConsolidation as ds where   (md.br_code=ds.br_code and md.gl_type =ds.gl_type and md.gl_code = ds.gl_code ) and (trn_dt between md.next_month and '"+Validations.convertYMD(date1)+"') group by md.gl_type,md.gl_code,md.br_code");
				stmt_update.addBatch("create temporary table daily_data_cons_second_date select md.gl_type,md.gl_code,md.gl_name,md.br_code,md.normal_cd,(sum(ds.cash_cr)+sum(ds.cg_cr)+sum(ds.trf_cr))-(sum(ds.cash_dr)+sum(ds.cg_dr)+sum(ds.trf_dr))as day_balance from  month_data_cons_second_date as md ,DailyConsolidation as ds where   (md.br_code=ds.br_code and md.gl_type =ds.gl_type and md.gl_code = ds.gl_code ) and (trn_dt between md.next_month and '"+Validations.convertYMD(date2)+"') group by md.gl_type,md.gl_code,md.br_code;");
				stmt_update.addBatch("create temporary table day_month_cons_first_date (gl_type varchar(20),gl_code int(11),gl_name varchar(50),br_code varchar(20),normal_cd varchar(5),month_balance double(19,2)  not null Default 0.0,day_balance double(19,2)  not null Default 0.0)");
				stmt_update.addBatch("create temporary table day_month_cons_second_date (gl_type varchar(20),gl_code int(11),gl_name varchar(50),br_code varchar(20),normal_cd varchar(5),month_balance double(19,2)  not null Default 0.0,day_balance double(19,2)  not null Default 0.0)");
				stmt_update.addBatch("insert into day_month_cons_first_date select md.gl_type,md.gl_code,md.gl_name,md.br_code,md.normal_cd,credit_sum-debit_sum,day_balance from month_data_cons_first_date md left join  daily_data_cons_first_date as dd on md.gl_type=dd.gl_type and md.gl_code=dd.gl_code and md.br_code=dd.br_code");
				stmt_update.addBatch("insert into day_month_cons_second_date select md.gl_type,md.gl_code,md.gl_name,md.br_code,md.normal_cd,credit_sum-debit_sum,day_balance from month_data_cons_second_date md left join  daily_data_cons_second_date as dd on md.gl_type=dd.gl_type and md.gl_code=dd.gl_code and md.br_code=dd.br_code");
				stmt_update.addBatch("create temporary table result_branch select moduleabbr,df.gl_code,df.gl_type,df.gl_name,df.br_code,br_shnm,df.normal_cd,df.month_balance+df.day_balance as balance_first,ds.month_balance+ds.day_balance as balance_second   from day_month_cons_first_date as df, Modules as mo,BranchMaster as bm,day_month_cons_second_date as ds where  bm.br_code%1111!=0 and  df.gl_type=mo.modulecode and df.br_code=bm.br_code and df.gl_type = ds.gl_type and df.gl_code=ds.gl_code and df.br_code = ds.br_code order by gl_type,gl_code,br_code");
				stmt_update.addBatch("create temporary table result select moduleabbr,df.gl_code,df.gl_type,df.gl_name,df.normal_cd,sum(df.month_balance+df.day_balance) as balance_first,sum(ds.month_balance+ds.day_balance) as balance_second   from day_month_cons_first_date as df, Modules as mo,BranchMaster as bm,day_month_cons_second_date  as ds where  bm.br_code%1111!=0 and  df.gl_type=mo.modulecode and df.br_code=bm.br_code and df.gl_type = ds.gl_type and df.gl_code=ds.gl_code and df.br_code = ds.br_code group by df.gl_type,df.gl_code");
				stmt_update.executeBatch();
				if(str.equals(" "))
				{
				if(flag_branch==1)
				rs =stmt_select.executeQuery("select * from result_branch");
				else
				rs=stmt_select.executeQuery("select * from result") ;
				}
				else
				{
				if(flag_branch==1)
			    rs =stmt_select.executeQuery("select * from result_branch where ("+str+")");
				else
				rs=stmt_select.executeQuery("select * from result where ("+str+") ") ;
				}
				stmt.executeBatch();
			}
			
			if(rs!=null){
				
				rs.last();
				gl_object = new GLReportObject[rs.getRow()];
				rs.beforeFirst();
				int row=0;
				while(rs.next()){
					
					gl_object[row] = new GLReportObject();
					System.out.println("gl_code="+rs.getInt("gl_code"));
					gl_object[row].setGLAbbr(rs.getString("moduleabbr"));
					gl_object[row].setGLType(rs.getString("gl_type"));
					gl_object[row].setGLCode(rs.getInt("gl_code"));
					gl_object[row].setGLName(rs.getString("gl_name"));
					gl_object[row].setFirstDateBalance(rs.getString("balance_first")==null ? 0.0:rs.getDouble("balance_first"));
					gl_object[row].setSecondDateBalance(rs.getString("balance_second")==null ? 0.0:rs.getDouble("balance_second"));
					gl_object[row].setNormalCD(rs.getString("normal_cd")==null?" ":rs.getString("normal_cd"));
					if(flag_branch==1){
					gl_object[row].setBranchCode(rs.getString("br_code"));
					gl_object[row].setBranchName(rs.getString("br_shnm"));
					}

					row++;
				}
			}
		}catch(SQLException sql){
			sessioncontext.setRollbackOnly();
			sql.printStackTrace();
			} 
		finally{
			try{
				stmt.executeBatch();
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
		return gl_object;
	}
	
	public GLReportObject[] getMonthlyConsolidationDetailsForMonth(String mth,String year,int from_glcode,String from_gltype,int to_glcode,String to_gltype,int flag_branch,String str)
	{
		Connection con=null;
		GLReportObject gl_object[]=null;
		Statement stmt=null,stmt_select=null,stmt_update=null;
		String date1=null,date2=null,temp_date1=null,temp_date2=null;
		temp_date1="01"+"/"+mth+"/"+year;
		temp_date2=Validations.lastDayOfMonth(temp_date1);
		date1=Validations.convertYMD(temp_date1);
		date2=Validations.convertYMD(temp_date2);
		
		System.out.println(" date 1 "+date1);
		System.out.println(" date 2 "+date2);
		
		String yearmth1=year+""+mth;
		int  yearmth=Integer.parseInt(yearmth1);
		System.out.println("yearmth="+yearmth);
	
		try{
			con=getConnection();
			stmt=con.createStatement();
			stmt_select=con.createStatement();
			stmt_update=con.createStatement();
			ResultSet rs =null;
			
			// The following set of queries has to be executed simultaneously to get the result
			{
				stmt.addBatch("drop table if exists daily ;");
				stmt.addBatch("drop table if exists mth;");
				stmt.addBatch("drop table if exists monthly;");
				stmt.addBatch("drop table if exists month_data_cons;");
				stmt.addBatch("drop table if exists day_data_cons;");
				stmt.addBatch("drop table if exists day_month;");
				stmt.addBatch("drop table if exists daily_trans;");
				stmt.addBatch("drop table if exists daily_day_month;");
				stmt.addBatch("drop table if exists result;");
				
				stmt.executeBatch();
				stmt_update.addBatch("create temporary table daily (trn_dt date,moduleabbr varchar(10) ,gl_type varchar(20),gl_code int(15),gl_name varchar(50),normal_cd varchar(5),balance double(20,2) not null)");
				stmt_update.addBatch("create temporary table mth (gl_type varchar(20),gl_code int(11))");
				stmt_update.addBatch("insert into daily select dc.trn_dt,m.moduleabbr,dc.gl_type,dc.gl_code,gm.gl_name,gm.normal_cd,(sum(cash_cr)+sum(cg_cr)+sum(trf_cr))-(sum(cash_dr)+sum(cg_dr)+sum(trf_dr)) as balance from DailyConsolidation dc ,Modules m,GLMaster gm  where trn_dt between '"+date1+"' and '"+date2+"' and  gm.gl_code=dc.gl_code and dc.gl_type=gm.gl_type and m.modulecode=dc.gl_type and dc.gl_code between "+from_glcode+" and "+to_glcode+" and dc.gl_type between '"+from_gltype+"' and '"+to_gltype+"' group by gl_type,gl_code,trn_dt order by gl_type,gl_code,trn_dt");
				stmt_update.addBatch("insert into mth select distinct dc.gl_type,dc.gl_code from MonthlyConsolidation  ms,daily  dc where dc.gl_code=ms.gl_code and dc.gl_type=ms.gl_type");
				stmt_update.addBatch("create temporary table monthly (gl_type varchar(20),gl_code int(11),balance double(19,2) not null default 0.0 ,next_month  date not null)");
				stmt_update.addBatch("insert into monthly select dc.gl_type,dc.gl_code,(sum(ms.cash_cr)+sum(ms.cg_cr)+sum(ms.trf_cr))-(sum(ms.cash_dr)+sum(ms.cg_dr)+sum(ms.trf_dr)) as balance,max(concat(left((period_add(ms.yr_mth,1)),4),'-',right((period_add(ms.yr_mth,1)),2),'-','01'))from mth dc left join MonthlyConsolidation  ms on  dc.gl_type=ms.gl_type and dc.gl_code=ms.gl_code and ms.yr_mth<"+yearmth+" group by dc.gl_type,dc.gl_code order by dc.gl_type,dc.gl_code");
				stmt_update.addBatch("create temporary table month_data_cons select do.gl_type,do.gl_code,do.trn_dt,do.normal_cd,mo.balance,mo.next_month from daily as do left join monthly as mo on mo.gl_type=do.gl_type and mo.gl_code=do.gl_code");
				stmt_update.addBatch("create temporary table day_data_cons select md.gl_type,md.gl_code,md.trn_dt,md.normal_cd,(sum(ds.cash_cr)+sum(ds.cg_cr)+sum(ds.trf_cr))-(sum(ds.cash_dr)+sum(ds.cg_dr)+sum(ds.trf_dr))as day_balance from  month_data_cons as md left join DailyConsolidation as ds on   (md.gl_type =ds.gl_type and md.gl_code = ds.gl_code ) and (ds.trn_dt between md.next_month and md.trn_dt) group by ds.gl_type,ds.gl_code,md.trn_dt");
				stmt_update.addBatch("create temporary table day_month as select mo.gl_type,mo.gl_code,mo.trn_dt,mo.normal_cd,day_balance,mo.balance+dd.day_balance as close_bal from month_data_cons as mo,day_data_cons as dd where mo.gl_type=dd.gl_type and mo.gl_code=dd.gl_code and mo.trn_dt=dd.trn_dt");
				stmt_update.addBatch("create temporary table daily_trans select dc.br_code,bm.br_shnm,dc.trn_dt,dc.gl_code,dc.gl_type,(cash_cr+cg_cr+trf_cr)-(cash_dr+cg_dr+trf_dr) as net_trans from DailyConsolidation dc,BranchMaster bm where  bm.br_code%1111!=0 and  trn_dt between '"+date1+"' and '"+date2+"' and dc.gl_code between "+from_glcode+" and "+to_glcode+" and dc.gl_type between '"+from_gltype+"' and '"+to_gltype+"' and  bm.br_code=dc.br_code order by gl_type,gl_code,trn_dt,br_code");
				stmt_update.addBatch("create temporary table daily_day_month select da.*,day_balance,close_bal,dm.close_bal-da.balance as open_bal from daily da,day_month dm where  dm.gl_code=da.gl_code and dm.gl_type=da.gl_type and dm.trn_dt=da.trn_dt");
				stmt_update.addBatch("create temporary table result select dd.moduleabbr,dd.gl_code,dd.gl_type,dd.gl_name,dt.br_code,dd.normal_cd,dt.br_shnm,dt.trn_dt,dt.net_trans,dd.close_bal,dd.open_bal from daily_trans dt,daily_day_month dd where dd.gl_code=dt.gl_code and dd.gl_type=dt.gl_type and dd.trn_dt=dt.trn_dt");
				stmt_update.executeBatch();
				if(str.equals(" "))
				{
				if(flag_branch==1)
				rs =stmt_select.executeQuery("select * from result");
				else
				rs=stmt_select.executeQuery("select * from daily_day_month");
				}
				else
				{
				if(flag_branch==1)
				rs =stmt_select.executeQuery("select * from result where ("+str+") ");
				else
				rs=stmt_select.executeQuery("select * from daily_day_month where ("+str+") ");
				}
				stmt.executeBatch();
			}
			
			if(rs!=null){
				
				rs.last();
				gl_object = new GLReportObject[rs.getRow()];
				rs.beforeFirst();
				int row=0;
				while(rs.next()){
					
					gl_object[row] = new GLReportObject();
					
					gl_object[row].setGLAbbr(rs.getString("moduleabbr"));
					gl_object[row].setGLType(rs.getString("gl_type"));
					gl_object[row].setGLCode(rs.getInt("gl_code"));
					gl_object[row].setGLName(rs.getString("gl_name"));
					gl_object[row].setDate(convertDateToString(String.valueOf(rs.getDate("trn_dt"))));
					gl_object[row].setClosingBalance(rs.getDouble("close_bal"));
					gl_object[row].setOpeningBalance(rs.getDouble("open_bal"));
					gl_object[row].setNormalCD(rs.getString("normal_cd")==null?" ":rs.getString("normal_cd"));
					if(flag_branch==1)
					{
					gl_object[row].setBalance(rs.getDouble("net_trans"));
					gl_object[row].setBranchCode(String.valueOf(rs.getInt("br_code")));
					gl_object[row].setBranchName(rs.getString("br_shnm"));
					}
					else
					{
					gl_object[row].setBalance(rs.getDouble("balance"));
					}

					row++;
				}
			}
		}catch(SQLException sql){
			sessioncontext.setRollbackOnly();
			sql.printStackTrace();
			} 
		finally{
			try{
				stmt.executeBatch();
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
		System.out.println("lenght="+gl_object.length );
		return gl_object;
	}
	
	

	public GLReportObject[] getMonthlyDetailsForMonth(String mth,String year,int from_glcode,String from_gltype,int to_glcode,String to_gltype,String str)
	{
		Connection con=null;
		GLReportObject gl_object[]=null;
		Statement stmt=null,stmt_select=null,stmt_update=null;
		String date1=null,date2=null,temp_date1=null,temp_date2=null;
		temp_date1="01"+"/"+mth+"/"+year;
		temp_date2=Validations.lastDayOfMonth(temp_date1);
		date1=Validations.convertYMD(temp_date1);
		date2=Validations.convertYMD(temp_date2);
		System.out.println(" date 1 "+date1);
		System.out.println(" date 2 "+date2);
		
		String yearmth1=year+""+mth;
		int  yearmth=Integer.parseInt(yearmth1);
		System.out.println("yearmth="+yearmth);
	
		try{
			con=getConnection();
			stmt=con.createStatement();
			stmt_select=con.createStatement();
			stmt_update=con.createStatement();
			ResultSet rs =null;
			
			// The following set of queries has to be executed simultaneously to get the result
			{
				stmt.addBatch("drop table if exists daily ;");
				stmt.addBatch("drop table if exists mth;");
				stmt.addBatch("drop table if exists monthly;");
				stmt.addBatch("drop table if exists month_data_cons;");
				stmt.addBatch("drop table if exists day_data_cons;");
				stmt.addBatch("drop table if exists day_month;");
				stmt.addBatch("drop table if exists result;");
				stmt.addBatch("drop table if exists mth");
				
				stmt.executeBatch();
				stmt_update.addBatch("create temporary table daily (trn_dt date,moduleabbr varchar(10) ,gl_type varchar(20),gl_code int(15),gl_name varchar(50),normal_cd varchar(5),balance double(20,2) not null)");
				stmt_update.addBatch("create temporary table mth (gl_type varchar(20),gl_code int(11))");
				stmt_update.addBatch("insert into daily select dc.trn_dt,m.moduleabbr,dc.gl_type,dc.gl_code,gm.gl_name,gm.normal_cd,(sum(cash_cr)+sum(cg_cr)+sum(trf_cr))-(sum(cash_dr)+sum(cg_dr)+sum(trf_dr)) as balance from DailySummary dc ,Modules m,GLMaster gm  where trn_dt between '"+date1+"' and '"+date2+"' and dc.gl_code between "+from_glcode+" and "+to_glcode+" and dc.gl_type between '"+from_gltype+"' and '"+to_gltype+"' and  gm.gl_code=dc.gl_code and dc.gl_type=gm.gl_type and m.modulecode=dc.gl_type  group by gl_type,gl_code,trn_dt order by gl_type,gl_code,trn_dt");
				stmt_update.addBatch("insert into mth select distinct dc.gl_type,dc.gl_code from MonthlySummary  ms,daily  dc where dc.gl_code=ms.gl_code and dc.gl_type=ms.gl_type");
				stmt_update.addBatch("create temporary table monthly (gl_type varchar(20),gl_code int(11),balance double(19,2) not null default 0.0 ,next_month  date not null)");
				stmt_update.addBatch("insert into monthly select dc.gl_type,dc.gl_code,(sum(ms.cash_cr)+sum(ms.cg_cr)+sum(ms.trf_cr))-(sum(ms.cash_dr)+sum(ms.cg_dr)+sum(ms.trf_dr)) as balance,max(concat(left((period_add(ms.yr_mth,1)),4),'-',right((period_add(ms.yr_mth,1)),2),'-','01'))from mth dc left join MonthlyConsolidation  ms on  ms.yr_mth<"+yearmth+"  and dc.gl_type=ms.gl_type and dc.gl_code=ms.gl_code  group by dc.gl_type,dc.gl_code order by dc.gl_type,dc.gl_code");
				stmt_update.addBatch("create temporary table month_data_cons select do.gl_type,do.gl_code,do.trn_dt,mo.balance,do.normal_cd,mo.next_month from daily as do left join monthly as mo on mo.gl_type=do.gl_type and mo.gl_code=do.gl_code");
				stmt_update.addBatch("create temporary table day_data_cons select md.gl_type,md.gl_code,md.trn_dt,(sum(ds.cash_cr)+sum(ds.cg_cr)+sum(ds.trf_cr))-(sum(ds.cash_dr)+sum(ds.cg_dr)+sum(ds.trf_dr))as day_balance,md.normal_cd  from  month_data_cons as md , DailySummary as ds where   (md.gl_type =ds.gl_type and md.gl_code = ds.gl_code ) and (ds.trn_dt between md.next_month and md.trn_dt) group by ds.gl_type,ds.gl_code,md.trn_dt");
				stmt_update.addBatch("create temporary table day_month as select mo.gl_type,mo.gl_code,mo.trn_dt,day_balance,mo.balance+dd.day_balance as close_bal from month_data_cons as mo,day_data_cons as dd where mo.gl_type=dd.gl_type and mo.gl_code=dd.gl_code and mo.trn_dt=dd.trn_dt");
				stmt_update.addBatch("create temporary table result select da.*,dm.day_balance,dm.close_bal,dm.close_bal-da.balance as open_bal from day_month dm,daily da where da.gl_code=dm.gl_code and da.gl_type=dm.gl_type and da.trn_dt=dm.trn_dt order by da.gl_type,da.gl_code,da.trn_dt");
				stmt_update.executeBatch();
				if(str.equals(" "))
				rs =stmt_select.executeQuery("select * from result ");
				else
				{
				System.out.println("str="+str);    
				rs =stmt_select.executeQuery("select * from result where ("+str+")");
				}
				stmt.executeBatch();
			}
			
			if(rs!=null){
				
				rs.last();
				gl_object = new GLReportObject[rs.getRow()];
				rs.beforeFirst();
				int row=0;
				while(rs.next()){
					
					gl_object[row] = new GLReportObject();
					
					gl_object[row].setGLAbbr(rs.getString("moduleabbr"));
					gl_object[row].setGLType(rs.getString("gl_type"));
					gl_object[row].setGLCode(rs.getInt("gl_code"));
					gl_object[row].setGLName(rs.getString("gl_name"));
					gl_object[row].setDate(convertDateToString(String.valueOf(rs.getDate("trn_dt"))));
					gl_object[row].setClosingBalance(rs.getDouble("close_bal"));
					gl_object[row].setOpeningBalance(rs.getDouble("open_bal"));
					gl_object[row].setBalance(rs.getDouble("balance"));
					gl_object[row].setNormalCD(rs.getString("normal_cd")==null?" ":rs.getString("normal_cd"));

					row++;
				}
			}
		}catch(SQLException sql){
			sessioncontext.setRollbackOnly();
			sql.printStackTrace();
			} 
		finally{
			try{
				stmt.executeBatch();
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
		System.out.println("lenght="+gl_object.length );
		return gl_object;
	}
	
	public TransferScroll[] transferScrollPrint(String date1,String date2,String ac_type,String str)
	{
		Connection con=null;
		TransferScroll trfscroll_object[]=null;
		Statement stmt=null,stmt_select=null,stmt_update=null;
		/*if(ac_type.equals("All_Types"))
		    ac_type="1010%";*/
		
		try{
			con=getConnection();
			stmt=con.createStatement();
			stmt_select=con.createStatement();
			stmt_update=con.createStatement();
			ResultSet rs =null;
			
			// The following set of queries has to be executed simultaneously to get the result
			{
				stmt.addBatch("drop table if exists loantran;");
				stmt.addBatch("drop table if exists module_code;");
				stmt.addBatch("drop table if exists cid_det;");
				stmt.addBatch("drop table if exists loancid_det;");
				stmt.addBatch("drop table if exists result;");
				stmt.addBatch("drop table if exists details;");
				stmt.addBatch("drop table if exists trf_scroll;");
				stmt.executeBatch();
				
				stmt_update.addBatch("create temporary table module_code select modulecode,moduleabbr from Modules where moduleabbr in('SB','CA','OD','CC');");
				if(ac_type.equals("All_Types"))//ac_type=1008% and ac_type=1010%
				stmt_update.addBatch("create temporary table loantran select lt.ref_no,left(lt.trn_narr,locate(' ',lt.trn_narr)) as ac_abbr,substring(lt.trn_narr,locate(' ',lt.trn_narr)+1,(length(lt.trn_narr)-locate(' ',lt.trn_narr)))as frmacc_no,lt.trn_type,lt.trn_mode,lt.ac_type,lt.ac_no,lt.trn_amt,lt.trn_date,lt.de_tml,lt.de_user,lt.ve_tml,lt.ve_user from LoanTransaction lt  where  lt.trn_type='R'and lt.trn_mode='T' and lt.trn_narr not like '(NULL)' and  concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1,(locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1)) between  '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"'");
				else//ac_type =1008 or ac_type=1010
				stmt_update.addBatch("create temporary table loantran select lt.ref_no,left(lt.trn_narr,locate(' ',lt.trn_narr)) as ac_abbr,substring(lt.trn_narr,locate(' ',lt.trn_narr)+1,(length(lt.trn_narr)-locate(' ',lt.trn_narr)))as frmacc_no,lt.trn_type,lt.trn_mode,lt.ac_type,lt.ac_no,lt.trn_amt,lt.trn_date,lt.de_tml,lt.de_user,lt.ve_tml,lt.ve_user from LoanTransaction lt  where lt.ac_type like'"+ac_type+"'and lt.trn_type='R'and lt.trn_mode='T' and lt.trn_narr not like '(NULL)' and  concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1,(locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1)) between  '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"'");
				
				stmt_update.addBatch("create  temporary table cid_det select lt.ref_no,am.cid as frmcid,mc.moduleabbr as frmac_abbr,am.ac_type as frmacc_type,am.ac_no as frmacc_no,concat(IFNULL(cm.fname,''),IFNULL(cm.mname,''),IFNULL(cm.lname,''))as frmname ,lm.cid,m.moduleabbr,lm.ac_type,lm.ac_no from LoanMaster lm,loantran lt,AccountMaster am,module_code mc,Modules m ,CustomerMaster cm where am.ac_type=mc.modulecode and mc.moduleabbr=lt.ac_abbr  and lt.frmacc_no=am.ac_no and lt.ac_no=lm.ac_no and lt.ac_type=lm.ac_type and m.modulecode=lt.ac_type and cm.cid=am.cid;");
				stmt_update.addBatch("insert into cid_det select lt.ref_no,am.cid as frmcid,mc.moduleabbr as frmac_abbr,am.ac_type as frmacc_type,am.ac_no as frmacc_no,concat(IFNULL(cm.fname,''),IFNULL(cm.mname,''),IFNULL(cm.lname,''))as frmname,lm.cid,m.moduleabbr,lm.ac_type,lm.ac_no from CustomerMaster cm,LoanMaster lm,loantran lt,ODCCMaster am,module_code mc,Modules m where am.ac_type=mc.modulecode and mc.moduleabbr=lt.ac_abbr  and lt.frmacc_no=am.ac_no and lt.ac_no=lm.ac_no and lt.ac_type=lm.ac_type and m.modulecode=lt.ac_type and cm.cid=am.cid;");
				stmt_update.addBatch("insert into cid_det select lt.ref_no,am.cid as frmcid,mc.moduleabbr as frmac_abbr,am.ac_type as frmacc_type,am.ac_no as frmacc_no,concat(IFNULL(cm.fname,''),IFNULL(cm.mname,''),IFNULL(cm.lname,''))as frmname,lm.cid,m.moduleabbr,lm.ac_type,lm.ac_no from LoanMaster lm,loantran lt,AccountMaster am,module_code mc,Modules m,CustomerMaster cm where lt.frmacc_no in(201,202,203) and lt.ac_abbr='CA' and am.ac_type like '1018%' and mc.moduleabbr=lt.ac_abbr  and lt.frmacc_no=am.ac_no and lt.ac_no=lm.ac_no and lt.ac_type=lm.ac_type and m.modulecode=lt.ac_type and cm.cid=am.cid;");
				stmt_update.addBatch("create temporary table loancid_det select cd.cid,concat(IFNULL(cm.fname,''),IFNULL(cm.mname,''),IFNULL(cm.lname,''))as name from cid_det cd left join CustomerMaster cm on cd.cid=cm.cid");
				stmt_update.addBatch("create temporary table details select distinct cd.*,ld.name from cid_det cd left join  loancid_det ld on  ld.cid=cd.cid");
				stmt_update.addBatch("create temporary table trf_scroll select concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1,(locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))as trn_date  ,lt.ref_no,lt.ac_abbr,de.frmacc_no,de.frmname,de.moduleabbr,de.ac_type,de.ac_no,de.name,trn_amt,de_tml,de_user,ve_tml,ve_user from loantran lt left join details de on de.ref_no=lt.ref_no order by concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1,(locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1)),lt.ref_no;");
				stmt_update.executeBatch();
				if(str.equals(" "))
					rs =stmt_select.executeQuery("select * from trf_scroll ");
				else
				{
					System.out.println("str="+str);    
					rs =stmt_select.executeQuery("select * from trf_sroll where ("+str+")");
				}
					stmt.executeBatch();
			}
			if(rs!=null)
			{
				rs.last();
				trfscroll_object = new TransferScroll[rs.getRow()];
				rs.beforeFirst();
				int row=0;
				while(rs.next()){
					trfscroll_object[row] = new TransferScroll();
					
					trfscroll_object[row].setRefNo(rs.getInt("ref_no"));
					trfscroll_object[row].setFromModuleabbr(rs.getString("ac_abbr"));
					trfscroll_object[row].setFromAccNo(rs.getInt("frmacc_no"));
					trfscroll_object[row].setFromName(rs.getString("frmname"));
					trfscroll_object[row].setToModuleabbr(rs.getString("moduleabbr"));
					trfscroll_object[row].setToAccType(rs.getString("ac_type"));
					trfscroll_object[row].setToName(rs.getString("name"));
					trfscroll_object[row].setToAccNo(rs.getInt("ac_no"));
					trfscroll_object[row].setTranAmt(rs.getDouble("trn_amt"));
					trfscroll_object[row].setTranDate(Validations.convertDMY(rs.getString("trn_date")));
					trfscroll_object[row].setDe_tml(rs.getString("de_tml"));
					trfscroll_object[row].setDe_user(rs.getString("de_user"));
					trfscroll_object[row].setVe_tml(rs.getString("ve_tml"));
					trfscroll_object[row].setVe_user(rs.getString("ve_user"));
					row++;
					}
				}
			}catch(Exception e){
				sessioncontext.setRollbackOnly();
				e.printStackTrace();
			}
			finally{
				try{                
					con.close();
				}catch(Exception exception){exception.printStackTrace();}
			}
			return trfscroll_object;
}
	
	public String[][] getForm1Details(int num)
	{
	    Statement stmt=null;
	    Connection con=null;
	    ResultSet rs=null;
	    int row=0;
	    String form1_object[][]=null;
	    
	    try{
	        con=getConnection();     
	   	    stmt=con.createStatement();
	   	    if(num<=1)
	   	    	rs=stmt.executeQuery("select code,name,(concat(right((left (fromdate,10)),2),'/',substring((left (fromdate,10)),6,2),'/',substring((left (fromdate,10)),1,4))) as  fromdate,( concat(right((left (todate,10)),2),'/',substring((left (todate,10)),6,2),'/',substring((left (todate,10)),1,4))) as  todate,de_user,de_tml,( concat(right((left (de_dt_time,10)),2),'/',substring((left (de_dt_time,10)),6,2),'/',substring((left (de_dt_time,10)),1,4),' ',right(de_dt_time,8))) as de_dt_time  from RBIForm1Head  order by code,fromdate,todate");
	   	    else
	   	    	rs=stmt.executeQuery("select code,name,(concat(right((left (fromdate,10)),2),'/',substring((left (fromdate,10)),6,2),'/',substring((left (fromdate,10)),1,4))) as  fromdate,( concat(right((left (todate,10)),2),'/',substring((left (todate,10)),6,2),'/',substring((left (todate,10)),1,4))) as  todate,de_user,de_tml,( concat(right((left (de_dt_time,10)),2),'/',substring((left (de_dt_time,10)),6,2),'/',substring((left (de_dt_time,10)),1,4),' ',right(de_dt_time,8))) as de_dt_time  from RBIForm9Master  order by code,fromdate,todate");
	        if(rs.next())
	        {
	            rs.last();
				form1_object = new String[rs.getRow()][7];
				rs.beforeFirst();
				System.out.println("row="+form1_object.length );
				while(rs.next())
				{
				    //form1_object[row] = new Form1Object();
				    
				    
				    if(rs.getString("code")!=null)
				    form1_object[row][0]=rs.getString("code");
				    else
				    form1_object[row][0]=" ";    
				    
				    if(!(rs.getString("name").equals(null)))
				    form1_object[row][1]=rs.getString("name");
				    else
				    form1_object[row][1]=" ";
				    
				    if((rs.getString("fromdate")!=null))
				    form1_object[row][2]=rs.getString("fromdate");
				    else
				    form1_object[row][2]=" ";
				    
				    if((rs.getString("todate")!= null))
					form1_object[row][3]=rs.getString("todate");
					else
					form1_object[row][3]=" ";
				    
				    if(rs.getString("de_user")!=null)
				    form1_object[row][4]=rs.getString("de_user");
					else
					form1_object[row][4]=" ";
				    
				    if((rs.getString("de_tml")!=null))
					form1_object[row][5]=rs.getString("de_tml");
					else
					form1_object[row][5]=" ";
					    
				    
				    
				    if((rs.getString("de_dt_time")!=null))
					form1_object[row][6]=rs.getString("de_dt_time");
					else
					form1_object[row][6]=" ";
				    
				    row++;
				}
	        }
	    }catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}   
	return form1_object;    
	}
	public String[][] getForm1LinkDetails(String date,int num)
	{
	    Statement stmt=null;
	    Connection con=null;
	    ResultSet rs=null;
	    int row=0;
	    String form1_object[][]=null;
	    
	    try{
	        con=getConnection();     
	   	    stmt=con.createStatement();
	   	    if(num<=1)
	   	    	rs=stmt.executeQuery("select rb.code,rb1.name,rb.trn_src,rb.gl_type,m.moduleabbr as moduleabbr ,rb.gl_code,gp.gl_name,(concat(right((left (rb.fromdate,10)),2),'/',substring((left (rb.fromdate,10)),6,2),'/',substring((left (rb.fromdate,10)),1,4))) as  fromdate,( concat(right((left (rb.todate,10)),2),'/',substring((left (rb.todate,10)),6,2),'/',substring((left (rb.todate,10)),1,4))) as  todate,percentage,cd_ind,mul_by,sequence,rb.de_user,rb.de_tml,( concat(right((left (rb.de_dt_time,10)),2),'/',substring((left (rb.de_dt_time,10)),6,2),'/',substring((left (rb.de_dt_time,10)),1,4),' ',right(rb.de_dt_time,8))) as de_dt_time  from RBIForm1Link rb,RBIForm1Head rb1,Modules m,GLMaster gp where m.modulecode=rb.gl_type and rb.code=rb1.code and gp.gl_code=rb.gl_code and gp.gl_type=rb.gl_type and  '"+Validations.convertYMD(date)+"' between rb.fromdate and rb.todate union select distinct rb.code,rb1.name,rb.trn_src,rb.gl_type,m.moduleabbr=' ',rb.gl_code,rbh.name as gl_name ,(concat(right((left (rb.fromdate,10)),2),'/',substring((left (rb.fromdate,10)),6,2),'/',substring((left (rb.fromdate,10)),1,4))) as  fromdate,( concat(right((left (rb.todate,10)),2),'/',substring((left (rb.todate,10)),6,2),'/',substring((left (rb.todate,10)),1,4))) as  todate,percentage,cd_ind,mul_by,sequence,rb.de_user,rb.de_tml,( concat(right((left (rb.de_dt_time,10)),2),'/',substring((left (rb.de_dt_time,10)),6,2),'/',substring((left (rb.de_dt_time,10)),1,4),' ',right(rb.de_dt_time,8))) as de_dt_time  from Modules m ,RBIForm1Link rb ,RBIForm1Head rb1,RBIForm1Head rbh  where rb.code=rb1.code and rb.gl_code=rbh.code  and  rb.trn_src not like 'GL%' and  '"+Validations.convertYMD(date)+"' between rb.fromdate and rb.todate order by code,gl_type,gl_code,fromdate,todate ;");
	   	    else
	   	    	rs=stmt.executeQuery("select rb.code,rb1.name,rb.trn_src,rb.gl_type,m.moduleabbr as moduleabbr ,rb.gl_code,gp.gl_name,(concat(right((left (rb.fromdate,10)),2),'/',substring((left (rb.fromdate,10)),6,2),'/',substring((left (rb.fromdate,10)),1,4))) as  fromdate,( concat(right((left (rb.todate,10)),2),'/',substring((left (rb.todate,10)),6,2),'/',substring((left (rb.todate,10)),1,4))) as  todate,percentage,cd_ind,mul_by,sequence,rb.de_user,rb.de_tml,( concat(right((left (rb.de_dt_time,10)),2),'/',substring((left (rb.de_dt_time,10)),6,2),'/',substring((left (rb.de_dt_time,10)),1,4),' ',right(rb.de_dt_time,8))) as de_dt_time  from RBIForm9Link rb,RBIForm9Master rb1,Modules m,GLMaster gp where rb.trn_src like'GL%' and m.modulecode=rb.gl_type and rb.code=rb1.code and gp.gl_code=rb.gl_code and gp.gl_type=rb.gl_type and  '"+Validations.convertYMD(date)+"' between rb.fromdate and rb.todate union select distinct rb.code,rb1.name,rb.trn_src,rb.gl_type,' ',rb.gl_code,rbh.name as gl_name ,(concat(right((left (rb.fromdate,10)),2),'/',substring((left (rb.fromdate,10)),6,2),'/',substring((left (rb.fromdate,10)),1,4))) as  fromdate,( concat(right((left (rb.todate,10)),2),'/',substring((left (rb.todate,10)),6,2),'/',substring((left (rb.todate,10)),1,4))) as  todate,percentage,cd_ind,mul_by,sequence,rb.de_user,rb.de_tml,( concat(right((left (rb.de_dt_time,10)),2),'/',substring((left (rb.de_dt_time,10)),6,2),'/',substring((left (rb.de_dt_time,10)),1,4),' ',right(rb.de_dt_time,8))) as de_dt_time  from RBIForm9Link rb ,RBIForm9Master rb1,RBIForm9Master rbh  where rb.code=rb1.code and rb.gl_code=rbh.code  and  rb.trn_src not like 'GL%' and  '"+Validations.convertYMD(date)+"' between rb.fromdate and rb.todate order by code,gl_type,gl_code,fromdate,todate ;");
	   	    
	        if(rs.next())
	        {
	            rs.last();
				form1_object = new String[rs.getRow()][15];
				rs.beforeFirst();
				System.out.println("row="+form1_object.length );
				while(rs.next())
				{
				    form1_object[row][0]=rs.getString("code");
				    
				    if((rs.getString("name")!=null))
					form1_object[row][1]=String.valueOf(rs.getString("name"));
					else
					form1_object[row][1]=" ";
						
				    if((rs.getString("trn_src")!=null))
				    form1_object[row][2]=String.valueOf(rs.getString("trn_src"));
					else
					form1_object[row][2]=" ";
						
				    
				    if((rs.getString("moduleabbr")!=null))
					form1_object[row][3]=rs.getString("moduleabbr");
					else
					form1_object[row][3]=" ";
					    
				    if(rs.getString("gl_code")!=null)
					form1_object[row][4]=rs.getString("gl_code");
					else
					form1_object[row][4]=" ";
					    
				    if((rs.getString("gl_name")!=null))
				    form1_object[row][5]=rs.getString("gl_name");
				    else
				    form1_object[row][5]=" ";
				    
				    if((rs.getString("fromdate")!=null))
				    form1_object[row][6]=rs.getString("fromdate");
				    else
				    form1_object[row][6]=" ";
				    
				    if((rs.getString("todate")!= null))
					form1_object[row][7]=rs.getString("todate");
					else
					form1_object[row][7]=" ";
				    
				    if((String.valueOf(rs.getDouble("percentage"))!=null))
			        form1_object[row][8]=rs.getString("percentage");
					else
					form1_object[row][8]=" ";
					    
				        
				    if((rs.getString("cd_ind")!=null))
					form1_object[row][9]=String.valueOf(rs.getString("cd_ind"));
					else
					form1_object[row][9]=" ";

				    if((rs.getString("mul_by")!=null))
					form1_object[row][10]=String.valueOf(rs.getInt("mul_by"));
					else
					form1_object[row][10]=" ";

				    if((rs.getString("sequence")!=null))
					form1_object[row][11]=String.valueOf(rs.getInt("sequence"));
					else
					form1_object[row][11]=" ";
						
					    
				    if(rs.getString("de_user")!=null)
				    form1_object[row][12]=rs.getString("de_user");
					else
					form1_object[row][12]=" ";
				    
				    if((rs.getString("de_tml")!=null))
					form1_object[row][13]=rs.getString("de_tml");
					else
					form1_object[row][13]=" ";
				    
				    if((rs.getString("de_dt_time")!=null))
					form1_object[row][14]=String.valueOf(rs.getString("de_dt_time"));
					else
					form1_object[row][14]=" ";
				   
				    row++;
				}
	        }
	    }catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
	return form1_object;    
	}

	public int form1InsertRow(Form1Object form1_object,int num)
	{
	    ResultSet rs=null,rs1=null;//,rs2=null;
	    Statement stmt=null,stmt1=null,stmt_up=null;
	    PreparedStatement pstmt=null;
	    Connection con=null;
	    String fromdate_1=null;
	    String futuredate="9999-12-31";
	    int ret=10;
	    try{
	    con=getConnection();
	    stmt=con.createStatement();
	    stmt1=con.createStatement();
	    stmt_up=con.createStatement();
	    if(num<=1)
	    	rs=stmt.executeQuery("select * from RBIForm1Head where code="+form1_object.getCode()+" and  fromdate>='"+form1_object.getFromDate()+"'" );
	    else
	    	rs=stmt.executeQuery("select * from RBIForm9Master where code="+form1_object.getCode()+" and  fromdate>='"+form1_object.getFromDate()+"'" );
	    
	    if(rs.next()){
	        System.out.println(" i am in first");
	    	ret=0;
	    	return ret;
	    }
	    else
	    {
	    	if(num<=1)
	    		rs1=stmt1.executeQuery("select * from RBIForm1Head where code="+form1_object.getCode()+" and fromdate<'"+form1_object.getFromDate()+"' and todate>='"+form1_object.getFromDate()+"' and todate!='"+futuredate+"' ");
	    	else
	    		rs1=stmt1.executeQuery("select * from RBIForm9Master where code="+form1_object.getCode()+" and fromdate<'"+form1_object.getFromDate()+"' and todate>='"+form1_object.getFromDate()+"' and todate!='"+futuredate+"' ");
	    	
	        if(rs1.next()){
	            System.out.println(" i am in second");
		    	ret=1;
		    	return ret;
	        }
	        else{
	            System.out.println("fromdate="+form1_object.getFromDate());
	            fromdate_1=Validations.addDays(Validations.convertDMY(form1_object.getFromDate()),-1);
	            if(num<=1)
	            	ret=stmt_up.executeUpdate("update RBIForm1Head set todate='"+Validations.convertYMD(fromdate_1)+"',de_user='"+form1_object.getDeUser()+"', de_tml='"+form1_object.getDeTml()+"' , de_dt_time='"+form1_object.getDeDate() +"' where code="+form1_object.getCode()+" and fromdate<'"+form1_object.getFromDate()+"' and todate>='"+form1_object.getFromDate()+"' and todate='"+futuredate+"'");
	            else
	            	ret=stmt_up.executeUpdate("update RBIForm9Master set todate='"+Validations.convertYMD(fromdate_1)+"',de_user='"+form1_object.getDeUser()+"', de_tml='"+form1_object.getDeTml()+"' , de_dt_time='"+form1_object.getDeDate() +"' where code="+form1_object.getCode()+" and fromdate<'"+form1_object.getFromDate()+"' and todate>='"+form1_object.getFromDate()+"' and todate='"+futuredate+"'");
		        
	            System.out.println("update return="+ret);
		    
	            if(num<=1)
	            	pstmt=con.prepareStatement("insert into RBIForm1Head values(?,?,?,?,null,?,?,?)");
	            else
	            	pstmt=con.prepareStatement("insert into RBIForm9Master values(?,?,?,?,null,?,?,?)");
			    
	            pstmt.setInt(1,form1_object.getCode());
			    pstmt.setString(2,form1_object.getName());
			    pstmt.setString(3,form1_object.getFromDate());
			    pstmt.setString(4,form1_object.getToDate());
			    pstmt.setString(5,form1_object.getDeTml());
			    pstmt.setString(6,form1_object.getDeUser());
			    pstmt.setString(7,form1_object.getDeDate());
			    ret =pstmt.executeUpdate();
			    System.out.println("insert return="+ret);
			
		        //form1InsertRow(form1_object);
		        ret=2;
		        System.out.println("i am in third");
		        return ret;
	        }
	    }
	     }catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
	    return ret;
	}
	
	public int form1LinkInsertRow(Form1Object form1_object,int num)
	{
	    
	    
	    ResultSet rs=null,rs1=null,rs2=null;
	    Statement stmt=null,stmt1=null,stmt2=null,stmt_up=null;//,stmt3=null;
	    PreparedStatement pstmt=null;
	    Connection con=null;
	    String fromdate_1=null;
	    String futuredate="9999-12-31";
	    int ret=10;
	    double percent=0.0;
	    
	    try{
	    con=getConnection();
	    stmt=con.createStatement();
	    stmt1=con.createStatement();
	    stmt2=con.createStatement();
	    stmt_up=con.createStatement();
	    
	    if(!(form1_object.getTrnSrc().equalsIgnoreCase("GL"))){
	    	if(num<=1)	
	    		rs=stmt.executeQuery("select * from RBIForm1Link where code="+form1_object.getGLCode()+"  and gl_code="+form1_object.getCode()+" and ((fromdate<='"+form1_object.getFromDate()+"' and todate is null) or (fromdate<='"+form1_object.getFromDate()+"' and todate>='"+form1_object.getFromDate()+"') or (fromdate>='"+form1_object.getFromDate()+"'  and todate<='"+form1_object.getToDate()+"') or (fromdate<='"+form1_object.getToDate()+"' and todate is null) or (fromdate<='"+form1_object.getToDate()+"' and todate>='"+form1_object.getToDate()+"'))" );
	    	else	
	    		rs=stmt.executeQuery("select * from RBIForm9Link where code="+form1_object.getGLCode()+"  and gl_code="+form1_object.getCode()+" and ((fromdate<='"+form1_object.getFromDate()+"' and todate is null) or (fromdate<='"+form1_object.getFromDate()+"' and todate>='"+form1_object.getFromDate()+"') or (fromdate>='"+form1_object.getFromDate()+"'  and todate<='"+form1_object.getToDate()+"') or (fromdate<='"+form1_object.getToDate()+"' and todate is null) or (fromdate<='"+form1_object.getToDate()+"' and todate>='"+form1_object.getToDate()+"'))" );
	    	
	    	if(rs.next()){// causes circular refernece
	    		ret=4;
	    		return ret;
	    	}
	    }
	    
	    
	    if(num<=1)
	    	rs=stmt.executeQuery("select * from RBIForm1Link where code="+form1_object.getCode()+" and gl_type='"+form1_object.getGLType()+"' and gl_code="+form1_object.getGLCode()+" and  fromdate>='"+form1_object.getFromDate()+"'" );
	    else
	    	rs=stmt.executeQuery("select * from RBIForm9Link where code="+form1_object.getCode()+" and gl_type='"+form1_object.getGLType()+"' and gl_code="+form1_object.getGLCode()+" and  fromdate>='"+form1_object.getFromDate()+"'" );
	    if(rs.next()){
	        System.out.println(" i am in first");
	    	ret=0;
	    	return ret;
	    }
	    else
	    {
	        if(num<=1)
	        	rs1=stmt1.executeQuery("select * from RBIForm1Link where code="+form1_object.getCode()+" and code="+form1_object.getCode()+" and gl_type='"+form1_object.getGLType()+"' and gl_code="+form1_object.getGLCode()+" and fromdate<'"+form1_object.getFromDate()+"' and todate>='"+form1_object.getFromDate()+"' and todate!='"+futuredate+"' ");
	        else
	        	rs1=stmt.executeQuery("select * from RBIForm9Link where code="+form1_object.getCode()+" and gl_type='"+form1_object.getGLType()+"' and gl_code="+form1_object.getGLCode()+" and  fromdate>='"+form1_object.getFromDate()+"'" );
	        
	        if(rs1.next()){
	            System.out.println(" i am in second");
		    	ret=1;
		    	return ret;
	        }
	        else{
	        	System.out.println(" fromdate="+form1_object.getFromDate());
	            fromdate_1=Validations.addDays(Validations.convertDMY(form1_object.getFromDate()),-1);
	            if(form1_object.getTrnSrc().equalsIgnoreCase("GL")){
	            	if(num<=1)
	            		rs2=stmt2.executeQuery("select gl_type,gl_code,sum(percentage)as percentage from RBIForm1Link where gl_code="+form1_object.getGLCode()+" and gl_type='"+form1_object.getGLType()+"' and fromdate<='"+form1_object.getToDate()+"' and todate>='"+form1_object.getFromDate()+"' group by gl_type,gl_code ");
	            	else
	            		rs2=stmt2.executeQuery("select gl_type,gl_code,sum(percentage)as percentage from RBIForm9Link where gl_code="+form1_object.getGLCode()+" and gl_type='"+form1_object.getGLType()+"' and fromdate<='"+form1_object.getToDate()+"' and todate>='"+form1_object.getFromDate()+"' and sequence=0 group by gl_type,gl_code ");
	            	
	            	if(rs2.next()){
	            		percent=0;
	            		percent=rs2.getDouble("percentage");
	            		System.out.println(" percent="+percent);
	            		percent+=form1_object.getPercent();
	            		System.out.println(" percent1 ="+percent);
	            		if(percent>100.00){
	            			ret=3;
	            			return ret;
	            		}
	            	}
	            }
	            if(num<=1)
	            	ret=stmt_up.executeUpdate("update RBIForm1Link  set todate='"+Validations.convertYMD(fromdate_1)+"',de_user='"+form1_object.getDeUser()+"', de_tml='"+form1_object.getDeTml()+"' , de_dt_time='"+form1_object.getDeDate() +"' where code="+form1_object.getCode()+" and code="+form1_object.getCode()+" and gl_type='"+form1_object.getGLType()+"' and gl_code="+form1_object.getGLCode()+" and '"+form1_object.getFromDate()+"' between fromdate and  todate  and todate='"+futuredate+"'");
	            else
	            	ret=stmt_up.executeUpdate("update RBIForm9Link  set todate='"+Validations.convertYMD(fromdate_1)+"',de_user='"+form1_object.getDeUser()+"', de_tml='"+form1_object.getDeTml()+"' , de_dt_time='"+form1_object.getDeDate() +"' where code="+form1_object.getCode()+" and code="+form1_object.getCode()+" and gl_type='"+form1_object.getGLType()+"' and gl_code="+form1_object.getGLCode()+" and '"+form1_object.getFromDate()+"' between fromdate and  todate  and todate='"+futuredate+"'");
		        System.out.println("update return="+ret);

		        if(num<=1)
		        	pstmt=con.prepareStatement("insert into RBIForm1Link values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
		        else
		        	pstmt=con.prepareStatement("insert into RBIForm9Link values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
	            
		        pstmt.setInt(1,form1_object.getCode());
	            pstmt.setString(2,form1_object.getTrnSrc());
	            pstmt.setString(3,form1_object.getGLType());
	            pstmt.setInt(4,form1_object.getGLCode());
	            pstmt.setString(5,form1_object.getFromDate());
	            pstmt.setString(6,form1_object.getToDate());
	            pstmt.setDouble(7,form1_object.getPercent());
	            pstmt.setString(8,form1_object.getCdInd());
	            pstmt.setInt(9,form1_object.getMulBy());
	            pstmt.setInt(10,form1_object.getSequence());
	            pstmt.setString(11,form1_object.getDeTml());
	            pstmt.setString(12,form1_object.getDeUser());
	            pstmt.setString(13,form1_object.getDeDate());
	            ret =pstmt.executeUpdate();
	            System.out.println("execute update="+ret);
	            ret=2;
	            return ret;
	        }
	    }
	    }catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return ret;
	}
	public int form1DeleteRow(Form1Object form1_object,int num)
	{
	//    ResultSet rs=null;
	    Statement stmt=null,stmt1=null;
	    Connection con=null;
	    int ret=0;
	    try{
	    con=getConnection();
	    stmt=con.createStatement();
	    stmt1=con.createStatement();
	    if(num<=1){
	    	ret=stmt.executeUpdate("delete from RBIForm1Head  where code="+form1_object.getCode()+" and fromdate='"+form1_object.getFromDate()+"' and  todate='"+form1_object.getToDate()+"' ");
	    	ret=stmt1.executeUpdate("delete from RBIForm1Link  where code="+form1_object.getCode()+" and fromdate>='"+form1_object.getFromDate()+"' and  todate<='"+form1_object.getToDate()+"' ");
	    }
	    else{
	    	ret=stmt.executeUpdate("delete from RBIForm9Master  where code="+form1_object.getCode()+" and fromdate='"+form1_object.getFromDate()+"' and  todate='"+form1_object.getToDate()+"' ");
	    	ret=stmt1.executeUpdate("delete from RBIForm9Link  where code="+form1_object.getCode()+" and fromdate>='"+form1_object.getFromDate()+"' and  todate<='"+form1_object.getToDate()+"' ");
	    }
	    System.out.println("Form1Link delete="+ret);
	     } catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){
				sessioncontext.setRollbackOnly();
				exception.printStackTrace();
				}
		}
	    return ret;
	}
	public int form1LinkDeleteRow(Form1Object form1_object,int num)
	{
	   // ResultSet rs=null;
	    Statement stmt=null;
	    Connection con=null;
	    int ret=0;
	    try{
	    con=getConnection();
	    stmt=con.createStatement();
	    if(num<=1)
	    	ret=stmt.executeUpdate("delete from RBIForm1Link  where code="+form1_object.getCode()+" and gl_type='"+form1_object.getGLType()+"' and gl_code="+form1_object.getGLCode()+" and fromdate='"+form1_object.getFromDate()+"' and  todate='"+form1_object.getToDate()+"' ");
	    else
	    	ret=stmt.executeUpdate("delete from RBIForm9Link  where code="+form1_object.getCode()+" and gl_type='"+form1_object.getGLType()+"' and gl_code="+form1_object.getGLCode()+" and fromdate='"+form1_object.getFromDate()+"' and  todate='"+form1_object.getToDate()+"' ");
	    
	    System.out.println("ret="+ret);
	     }catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}

	    return ret;
	}

	public int form1UpdateRow(Form1Object form1_object,int num)
	{
	    ResultSet rs=null;
	    Statement stmt=null,stmt1=null;
	    Connection con=null;
	    int ret=10;
	    try{
	    con=getConnection();
	    stmt=con.createStatement();
	    stmt1=con.createStatement() ;
	    if(num<=1)
	    	rs=stmt1.executeQuery("select * from RBIForm1Head where code="+form1_object.getCode()+" and fromdate!='"+form1_object.getFromDate()+"'  and '"+form1_object.getToDate()+"' between fromdate and todate");
	    else
	    	rs=stmt1.executeQuery("select * from RBIForm9Master where code="+form1_object.getCode()+" and fromdate!='"+form1_object.getFromDate()+"'  and '"+form1_object.getToDate()+"' between fromdate and todate");
	    
	    if(rs.next()){
	        return ret;
	    }
	    if(num<=1)
	    	ret=stmt.executeUpdate("update RBIForm1Head set name='"+form1_object.getName()+"' , todate='"+form1_object.getToDate()+"' ,de_user='"+form1_object.getDeUser()+"' , de_tml='"+form1_object.getDeTml()+"', de_dt_time='"+form1_object.getDeDate()+"' where code="+form1_object.getCode()+" and fromdate='"+form1_object.getFromDate()+"'  "); 
	    else
	    	ret=stmt.executeUpdate("update RBIForm9Master set name='"+form1_object.getName()+"' , todate='"+form1_object.getToDate()+"' ,de_user='"+form1_object.getDeUser()+"' , de_tml='"+form1_object.getDeTml()+"', de_dt_time='"+form1_object.getDeDate()+"' where code="+form1_object.getCode()+" and fromdate='"+form1_object.getFromDate()+"'  ");
	    
	    System.out.println(" return updated="+ret);
	    }catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return ret;
	}
	
	public int form1LinkUpdateRow(Form1Object form1_object,int num)
	{
	    ResultSet rs=null;
	    Statement stmt=null,stmt1=null;
	    Connection con=null;
	    int ret=10;
	    try{
	    con=getConnection();
	    stmt=con.createStatement();
	    stmt1=con.createStatement();	    
	    if(form1_object.getTrnSrc().equalsIgnoreCase("Self")){
	    	if(num<=1)
	    		rs=stmt1.executeQuery("select * from RBIForm1Link where code="+form1_object.getCode()+" and  gl_code="+form1_object.getGLCode()+"  and fromdate!='"+form1_object.getFromDate()+"'  and '"+form1_object.getToDate()+"' between fromdate and todate");
	    	else
	    		rs=stmt1.executeQuery("select * from RBIForm9Link where code="+form1_object.getCode()+" and  gl_code="+form1_object.getGLCode()+"  and fromdate!='"+form1_object.getFromDate()+"'  and '"+form1_object.getToDate()+"' between fromdate and todate");
	    	if(rs.next())
		        return ret;
		    else{
		    	if(num<=1)
		    		ret=stmt.executeUpdate("update RBIForm1Link set percentage="+form1_object.getPercent()+" ,trn_src='"+form1_object.getTrnSrc()+"',cd_ind='"+form1_object.getCdInd()+"', todate='"+form1_object.getToDate()+"' ,mul_by="+form1_object.getMulBy()+" ,sequence="+form1_object.getSequence()+" ,de_user='"+form1_object.getDeUser()+"' , de_tml='"+form1_object.getDeTml()+"', de_dt_time='"+form1_object.getDeDate()+"' where code="+form1_object.getCode()+" and fromdate='"+form1_object.getFromDate()+"' and gl_code="+form1_object.getGLCode()+" ");
		    	else
		    		ret=stmt.executeUpdate("update RBIForm9Link set percentage="+form1_object.getPercent()+" ,trn_src='"+form1_object.getTrnSrc()+"',cd_ind='"+form1_object.getCdInd()+"', todate='"+form1_object.getToDate()+"' ,mul_by="+form1_object.getMulBy()+" ,sequence="+form1_object.getSequence()+" ,de_user='"+form1_object.getDeUser()+"' , de_tml='"+form1_object.getDeTml()+"', de_dt_time='"+form1_object.getDeDate()+"' where code="+form1_object.getCode()+" and fromdate='"+form1_object.getFromDate()+"' and gl_code="+form1_object.getGLCode()+" "); 
		    
		    		System.out.println(" return updated link="+ret);
		    }
	    }
		else{
			if(num<=1)
			rs=stmt1.executeQuery("select * from RBIForm1Link where code="+form1_object.getCode()+" and  gl_code="+form1_object.getGLCode()+" and gl_type='"+form1_object.getGLType()+"' and fromdate!='"+form1_object.getFromDate()+"'  and '"+form1_object.getToDate()+"' between fromdate and todate");
			else
			rs=stmt1.executeQuery("select * from RBIForm9Link where code="+form1_object.getCode()+" and  gl_code="+form1_object.getGLCode()+" and gl_type='"+form1_object.getGLType()+"' and fromdate!='"+form1_object.getFromDate()+"'  and '"+form1_object.getToDate()+"' between fromdate and todate");
			
			if(rs.next())
		        return ret;
			else{		    
				if(num<=1)
					ret=stmt.executeUpdate("update RBIForm1Link set percentage="+form1_object.getPercent()+" ,trn_src='"+form1_object.getTrnSrc()+"',cd_ind='"+form1_object.getCdInd()+"', todate='"+form1_object.getToDate()+"' ,mul_by="+form1_object.getMulBy()+" ,sequence="+form1_object.getSequence()+",de_user='"+form1_object.getDeUser()+"' , de_tml='"+form1_object.getDeTml()+"', de_dt_time='"+form1_object.getDeDate()+"' where code="+form1_object.getCode()+" and fromdate='"+form1_object.getFromDate()+"' and gl_code="+form1_object.getGLCode()+" and gl_type='"+form1_object.getGLType()+"'");
				else
					ret=stmt.executeUpdate("update RBIForm9Link set percentage="+form1_object.getPercent()+" ,trn_src='"+form1_object.getTrnSrc()+"',cd_ind='"+form1_object.getCdInd()+"', todate='"+form1_object.getToDate()+"' ,mul_by="+form1_object.getMulBy()+" ,sequence="+form1_object.getSequence()+",de_user='"+form1_object.getDeUser()+"' , de_tml='"+form1_object.getDeTml()+"', de_dt_time='"+form1_object.getDeDate()+"' where code="+form1_object.getCode()+" and fromdate='"+form1_object.getFromDate()+"' and gl_code="+form1_object.getGLCode()+" and gl_type='"+form1_object.getGLType()+"'");
		    
				System.out.println(" return updated link="+ret);
			}
		}
	    
	    }catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return ret;
	}
	 public String[][] getCodeDetails(String date,int num)
	 {
		Connection con=null;
		String codename[][]=null;
		ResultSet rs=null;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			if(num<=1)
				rs=stmt.executeQuery("select code,name from RBIForm1Head where '"+Validations.convertYMD(date)+"' between fromdate and todate  order by code");
			else
				rs=stmt.executeQuery("select code,name from RBIForm9Master where '"+Validations.convertYMD(date)+"' between fromdate and todate  order by code");
			if(rs.next()){
				rs.last();
				codename = new String[rs.getRow()][2];
				rs.beforeFirst();
				int count=0;
				if(codename!=null){
					while(rs.next()){
						codename[count][0]=rs.getString("code");
						//System.out.println("code="+codename[count][0]);
					    if(rs.getString("name")!=null)    
						codename[count][1]=rs.getString("name");
					    else
					    codename[count][1]=" ";
						
					    count++;
					}
				}

			}
				
		}catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		//System.out.println("lenght="+codename.length );
	 	return codename;
	 }
	 public String[][] getSelfDetails(String date,int num)
	 {
		Connection con=null;
		String codename[][]=null;
		ResultSet rs=null;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			if(num<=1)
				rs=stmt.executeQuery("select rb.code,rb1.name from RBIForm1Link rb,RBIForm1Head rb1 where '"+Validations.convertYMD(date)+"' between rb.fromdate and rb.todate and rb.code=rb1.code  order by code");
			else
				rs=stmt.executeQuery("select rb.code,rb1.name from RBIForm9Link rb,RBIForm9Master rb1 where '"+Validations.convertYMD(date)+"' between rb.fromdate and rb.todate and rb.code=rb1.code  order by code");
			
			if(rs.next()){
				rs.last();
				codename = new String[rs.getRow()][2];
				rs.beforeFirst();
				int count=0;
				if(codename!=null){
					while(rs.next()){
						codename[count][0]=rs.getString("code");
						//System.out.println("code="+codename[count][0]);
					    if(rs.getString("name")!=null)    
						codename[count][1]=rs.getString("name");
					    else
					    codename[count][1]=" ";
						
					    count++;
					}
				}
			}
		}catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		//System.out.println("lenght="+codename.length );
	 	return codename;
	 }
	
	 public String[][] getGLDetails(String gltype,String date)
	 {
		Connection con=null;
		String gl_data[][]=null;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select gm.gl_type,gm.gl_code,gm.gl_name,gm.normal_cd from  GLMaster gm where  ((from_date<='"+date+"' and to_date is null) or (from_date<='"+date+"' and to_date>='"+date+"')) and gm.gl_type='"+gltype+"' order by gm.gl_type,gm.gl_code;");
			if(rs.last()){
				gl_data = new String[rs.getRow()][4];
				System.out.println("length="+gl_data.length);
			}
			rs.beforeFirst();
			int count=0;
			if(gl_data!=null){
				while(rs.next()){
					gl_data[count][0]=rs.getString("gl_type");
					gl_data[count][1]=rs.getString("gl_code");
					//System.out.println(" gl_code="+rs.getString("gl_code"));
					gl_data[count][2]=rs.getString("gl_name");
					gl_data[count][3]=rs.getString("normal_cd");
					count++;
				}
			}
				
		}catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
	 	return gl_data;
	 }
	
	 	public int insertMarkingDateNDTL(Form1Object form1_object) throws RemoteException
		{
			Connection con=null;
			Statement stmt=null,stmt_select=null,stmt_update=null,stmt1=null;//,stmt2=null,stmt3=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null,rs1=null;//,rs2=null,rs3=null;
			String  date=null;;
			int yearmth=0,days=0,ret=10;
			try{
					con=getConnection();
					stmt=con.createStatement();
					stmt_select=con.createStatement();
					stmt_update=con.createStatement();
					stmt1=con.createStatement();
					//stmt2=con.createStatement();
					//stmt3=con.createStatement();
					System.out.println("insert marking date NDTL"); 
					rs=stmt.executeQuery("select * from PrecedingForNight order by marking_dt desc limit 1 ;");
					if(rs.next()){
						date=Validations.convertDMY(rs.getString("marking_dt"));
						days=Validations.dayCompare(date,Validations.convertDMY(form1_object.getFromDate()));
						if(days>=rs.getInt("recurring_days")){
							date=Validations.addDays(date,rs.getInt("recurring_days"));
						}
						else{
							days=Validations.dayCompare(date,Validations.convertDMY(form1_object.getToDate()));
							if(days>=rs.getInt("recurring_days")){
								date=Validations.addDays(date,rs.getInt("recurring_days"));
							}
						}
						if(days>=rs.getInt("recurring_days")){
							stmt1.executeUpdate("delete from RBIForm1Transaction where trn_date>='"+date+"'");
							while(Validations.dayCompare(date,Validations.convertDMY(form1_object.getToDate()))>=0){
								ret=10;
								yearmth=convertToYYYYMM(date);	
								System.out.println("marking date not updated");
								System.out.println("date="+date);
								System.out.println("todate="+form1_object.getToDate());
							
								stmt.addBatch("drop table if exists gl_temp");
								stmt.addBatch("drop table if exists month_data");
								stmt.addBatch("drop table if exists daily_data");
								stmt.addBatch("drop table if exists day_month");
								stmt.addBatch("drop table if exists RBITran1;");
								stmt.addBatch("drop table if exists RBItemp2");
								stmt.addBatch("drop table if exists RBItemp1");
								stmt.executeBatch();
								
								stmt_update.addBatch("create temporary table gl_temp select code,gl_type,gl_code,cd_ind from RBIForm1Link where trn_src like 'GL'  and '"+Validations.convertYMD(date)+"'  between fromdate and  todate");;
								stmt_update.addBatch("create temporary table month_data (code int(10),gl_type varchar(20),gl_code int(11),normal_cd varchar(5),debit_sum double(19,2) not null default 0.0 ,credit_sum double(19,2) not null default 0.0 ,next_month date not null)");
								stmt_update.addBatch("create table daily_data (code int(10),gl_type varchar(20),gl_code int(10),normal_cd varchar(3),day_balance double(20,2) not null default 0.0)");
								stmt_update.addBatch("insert into month_data select gt.*,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum,max(concat(left((period_add(yr_mth,1)),4),'-',right((period_add(yr_mth,1)),2),'-','01')) from gl_temp as gt left join MonthlySummary as ms on  ms.yr_mth<"+yearmth+" and ms.gl_type=gt.gl_type and ms.gl_code=gt.gl_code  group by code,gl_type,gl_code");;
								stmt_update.addBatch("insert into daily_data select md.code,md.gl_type,md.gl_code,md.normal_cd,(sum(ds.cash_cr)+sum(ds.cg_cr)+sum(ds.trf_cr))-(sum(ds.cash_dr)+sum(ds.cg_dr)+sum(ds.trf_dr))as day_balance from  month_data as md left join DailySummary as ds on   md.gl_type =ds.gl_type and md.gl_code = ds.gl_code and (trn_dt >=md.next_month and trn_dt<='"+Validations.convertYMD(date)+"') group by code,md.gl_type,md.gl_code");
								stmt_update.addBatch("create temporary table day_month (code int(10),gl_type varchar(20),gl_code int(11),normal_cd varchar(5),balance double(19,2)  not null Default 0.0)");
								stmt_update.addBatch("insert into day_month select md.code,md.gl_type,md.gl_code,md.normal_cd, (credit_sum-debit_sum)+day_balance as balance  from month_data md left join  daily_data as dd on md.gl_type=dd.gl_type and md.gl_code=dd.gl_code and md.code=dd.code");
								stmt_update.addBatch("create table RBITran1 select rb.code,rb.trn_src,rb.gl_type,rb.gl_code,if(normal_cd='D',(balance*(-1)*(mul_by)*(percentage/100)),(balance*(mul_by)*(percentage/100))) as balance,rb.sequence from RBIForm1Link rb left join day_month dm on rb.gl_code=dm.gl_code and rb.gl_type=dm.gl_type and rb.code=dm.code where rb.trn_src='GL';");
								stmt_update.executeBatch();
								while(ret!=0){
									stmt1.executeUpdate("drop  table if exists RBItemp2");
									stmt1.executeUpdate("drop table if exists RBItemp1");
									stmt1.executeUpdate("create table RBItemp1 select rb.code as code,rb.trn_src,sum(balance*mul_by*(percentage/100)) as balance ,count(distinct rbt.code)as rbtcount,(select count(gl_code) from RBIForm1Link where '"+Validations.convertYMD(date)+"'  between fromdate and  todate and rb.code=code group by code)as rbcount from   RBITran1 rbt,RBIForm1Link rb where '"+Validations.convertYMD(date)+"'  between fromdate and  todate and rb.gl_code=rbt.code  and rb.code not in (select code from RBITran1 )group by rb.code,rb.sequence;");
									stmt1.executeUpdate("create table RBItemp2 select t.code,t.trn_src,(select sum(balance) from RBItemp1 where code=t.code and balance>=0 group by code) as balance ,sum(t.rbtcount) as rbtcount,t.rbcount from RBItemp1 t  group by t.code ;");
									ret=stmt1.executeUpdate("delete from RBItemp2  where rbtcount!=rbcount;");
									ret=stmt1.executeUpdate("insert into RBITran1 (select code,trn_src,0,0,balance,0 from RBItemp2);");
								}
								ret = 10;
								rs1 =stmt_select.executeQuery("select code,balance from RBITran1 where trn_src like 'NDTL' ;");
								if(rs1.next()){
									System.out.println("code="+rs1.getInt("code"));
									System.out.println("closing bal="+rs1.getDouble("balance"));
									System.out.println("date="+date);
				
								pstmt=con.prepareStatement("insert into PrecedingForNight values(?,?,?,?,?,?,?)");
								pstmt.setString(1,rs.getString("day_of_week"));
								pstmt.setString(2,Validations.convertYMD(date));
								pstmt.setInt(3,rs.getInt("recurring_days"));
								pstmt.setDouble(4,rs1.getDouble("balance"));
								pstmt.setString(5,form1_object.getDeTml());
								pstmt.setString(6,form1_object.getDeUser());
								pstmt.setString(7,form1_object.getDeDate());
								pstmt.executeUpdate();
								//stmt.executeBatch();
								
								}
								date=Validations.addDays(date,rs.getInt("recurring_days"));
							}
						}
					}
					else{
						return 0;
					}
						
				}catch(Exception e){
					sessioncontext.setRollbackOnly();
					e.printStackTrace();
				}
				finally{
					try{                
						stmt.executeBatch();
						con.close();
						
					}catch(Exception exception){exception.printStackTrace();}
				}
				
				return ret;
			}
		
		

			
		public int Form1Posting(Form1Object form1_object) 
		{
			Connection con=null;
			Statement stmt=null,stmt_update=null,stmt1=null,stmt2=null,stmt3=null;
			//PreparedStatement pstmt=null;
			ResultSet rs2=null,rs3=null;
			String  temp_fromdate=form1_object.getFromDate();//,date=null;;
			int yearmth=0,ret=10;
			try{
			insertMarkingDateNDTL(form1_object);
			con=getConnection();
			stmt=con.createStatement();
			//stmt_select=con.createStatement();
			stmt_update=con.createStatement();
			stmt1=con.createStatement();
			stmt2=con.createStatement();
			stmt3=con.createStatement();
			stmt_update.executeUpdate("delete from RBIForm1Transaction where trn_date>='"+temp_fromdate+"'");
			while(Validations.dayCompare(Validations.convertDMY(temp_fromdate),Validations.convertDMY(form1_object.getToDate()))>=0){
				System.out.println("temp_fromdate="+temp_fromdate);
				System.out.println("todate="+form1_object.getToDate());
					yearmth=convertToYYYYMM(Validations.convertDMY(temp_fromdate));
					System.out.println("yearmth="+yearmth);
					ret=10;
							
					stmt.addBatch("drop table if exists gl_temp");
					stmt.addBatch("drop table if exists RBITran1;");
					stmt.addBatch("drop table if exists RBItemp2");
					stmt.addBatch("drop table if exists RBItemp1");
					stmt.executeBatch();
					
					stmt_update.addBatch("create table gl_temp(code int(10),gl_type varchar(20),gl_code int(11),normal_cd varchar(5),trn_dt date,mth_sum double(19,2) not null default 0.0 ,day_sum double(19,2) not null default 0.0 ,next_month date not null,balance double(19,2) not null default 0.0); ");;
					stmt_update.addBatch("insert into gl_temp(code,gl_type,gl_code,normal_cd,trn_dt) (select code,gl_type,gl_code,cd_ind,'"+temp_fromdate+"' from RBIForm1Link where trn_src like 'GL'  and '"+temp_fromdate+"'  between fromdate and  todate);");
					stmt_update.addBatch("update gl_temp gt set mth_sum=(select (sum(cash_cr)+sum(cg_cr)+sum(trf_cr))-(sum(cash_dr)+sum(cg_dr)+sum(trf_dr)) from MonthlySummary ms where  ms.yr_mth<"+yearmth+" and ms.gl_type=gt.gl_type and ms.gl_code=gt.gl_code  group by code,gl_type,gl_code)");
					stmt_update.addBatch("update gl_temp gt set next_month=(select max(concat(left((period_add(yr_mth,1)),4),'-',right((period_add(yr_mth,1)),2),'-','01')) from MonthlySummary ms where  ms.yr_mth<"+yearmth+"  and ms.gl_type=gt.gl_type and ms.gl_code=gt.gl_code  group by code,gl_type,gl_code)");
					stmt_update.addBatch("update gl_temp gt set day_sum=(select(sum(ds.cash_cr)+sum(ds.cg_cr)+sum(ds.trf_cr))-(sum(ds.cash_dr)+sum(ds.cg_dr)+sum(ds.trf_dr)) from DailySummary ds where (trn_dt >=gt.next_month and trn_dt<='"+temp_fromdate+"')  and ds.gl_type=gt.gl_type and ds.gl_code=gt.gl_code  group by code,gl_type,gl_code)");
					stmt_update.addBatch("update gl_temp set balance=day_sum+mth_sum;");
					stmt_update.addBatch("create table RBITran1 select rb.code,rb.trn_src,rb.gl_type,rb.gl_code,if(normal_cd='D',(balance*(-1)*(mul_by)*(percentage/100)),(balance*(mul_by)*(percentage/100))) as balance,rb.sequence from RBIForm1Link rb left join gl_temp gm on rb.gl_code=gm.gl_code and rb.gl_type=gm.gl_type and gm.code=rb.code where rb.trn_src='GL';");
					stmt_update.executeBatch();
					
					while(ret!=0){
						stmt1.executeUpdate("drop  table if exists RBItemp2");
						stmt1.executeUpdate("drop table if exists RBItemp1");
						stmt1.executeUpdate("create table RBItemp1 select rb.code as code,rb.trn_src,sum(balance*mul_by*(percentage/100)) as balance ,count(distinct rbt.code)as rbtcount,(select count(gl_code) from RBIForm1Link where '"+temp_fromdate+"'  between fromdate and  todate and rb.code=code group by code)as rbcount from   RBITran1 rbt,RBIForm1Link rb where rb.gl_code=rbt.code and '"+temp_fromdate+"'  between rb.fromdate and  rb.todate and rb.code not in (select code from RBITran1 )group by rb.code,rb.sequence;");
						stmt1.executeUpdate("create table RBItemp2 select t.code,t.trn_src,(select sum(balance) from RBItemp1 where code=t.code and balance>=0 group by code) as balance ,sum(t.rbtcount) as rbtcount,t.rbcount from RBItemp1 t  group by t.code ;");
						rs2=stmt2.executeQuery("select * from RBItemp2 where trn_src like 'NDTL'");
						if(rs2.next()){
							rs3=stmt3.executeQuery("select * from PrecedingForNight where marking_dt<'"+temp_fromdate+"' order by marking_dt desc limit 2");
							if(rs3.next()){
								rs3.last();
								int row=rs3.getRow();
								System.out.println("NDTL="+rs3.getDouble("NDTLamount"));
								if(row<2)
									return -1;
								else
									stmt1.executeUpdate(" update RBItemp2 set balance="+rs3.getDouble("NDTLamount")+" where trn_src='NDTL'");
							}
						}
						ret=stmt1.executeUpdate("delete from RBItemp2  where rbtcount!=rbcount;");
						ret=stmt1.executeUpdate("insert into RBITran1 (select code,trn_src,0,0,balance,0 from RBItemp2);");
					}
					ret=stmt1.executeUpdate("insert into RBIForm1Transaction select code,trn_src,gl_type,gl_code,'"+temp_fromdate+"',balance,sequence,'"+form1_object.getDeTml()+"','"+form1_object.getDeUser()+"','"+form1_object.getDeDate()+"' from RBITran1");
					temp_fromdate=Validations.addDays(Validations.convertDMY(temp_fromdate),1);
					System.out.println("temp_date"+temp_fromdate);
					temp_fromdate=Validations.convertYMD(temp_fromdate);
				}
			}
			catch(Exception e){
				sessioncontext.setRollbackOnly();
				e.printStackTrace();
			}
			finally{
				try{                
					con.close();
				}catch(Exception exception){exception.printStackTrace();}
			}
		ret=1;	
		return ret;
		}
		public String[] checkRBITransaction(Form1Object form1_object)
		{
			ResultSet rs=null;
		    Statement stmt=null;
		    Connection con=null;
		    int days=0;
		    String days_array[]= new String[3];
		    try{
		    	con=getConnection();
		    	stmt=con.createStatement();
		    	rs=stmt.executeQuery("select * from  RBIForm1Transaction where trn_date<='"+form1_object.getFromDate()+"' order by trn_date");
		    	if(rs.next()){
		    		rs=stmt.executeQuery("select distinct trn_date from RBIForm1Transaction order by trn_date desc limit 1;");
		    		if(rs.next()){
		    			days=Validations.dayCompare(Validations.convertDMY(rs.getString("trn_date")),Validations.convertDMY(form1_object.getFromDate()));
		    			days_array[0]="1";
		    			if(days==1){
		    				days_array[1]="1";
		    				days_array[2]="1";
		    				return days_array;
		    			}
		    			else{
		    				days_array[1]=Validations.convertDMY(rs.getString("trn_date"));
		    				days_array[2]="1";
		    				return days_array;
		    			}
		    		}
		    		else{
		    			rs=stmt.executeQuery("select marking_dt,recurring_days from PrecedingForNight order by marking_dt  limit 2");
		    			if(rs.next()){
		    				rs.last();
		    				days_array[0]="2";
		    				if(rs.getRow()<2){
		    					days_array[1]="2";
		    					days_array[2]="2";
		    					return days_array;
		    				}
		    				else{
		    					days_array[1]=Validations.convertDMY(rs.getString("marking_dt"));
		    					days_array[2]=rs.getString("recurring_days");
		    					return days_array;
		    				}
		    			}
		    			else{
		    				days_array[0]="0";days_array[1]="0";
		    				days_array[2]="0";
		    				return days_array;
		    			}
		    		}
		    	}
		    	else{
		    		rs=stmt.executeQuery("select marking_dt,recurring_days from PrecedingForNight order by marking_dt  limit 2");
	    			if(rs.next()){
	    				rs.last();
	    				days_array[0]="2";
	    				if(rs.getRow()<2){
	    					days_array[1]="2";
	    					days_array[2]="2";
	    					return days_array;
	    				}
	    				else{
	    					days_array[1]=Validations.convertDMY(rs.getString("marking_dt"));
	    					days_array[2]=rs.getString("recurring_days");
	    					return days_array;
	    				}
	    			}
	    			else{
	    				days_array[0]="0";days_array[1]="0";
	    				days_array[2]="0";
	    				return days_array;
	    			}
	    		}
		    }catch(Exception e){e.printStackTrace();}
		    finally{
				try{                
					con.close();
				}catch(Exception exception){exception.printStackTrace();}
			}
		    
		    return days_array;
		}
		public int MarkingDateInsertRow(Form1Object form1_object)
		{
		    ResultSet rs=null;
		    Statement stmt=null,stmt1=null;
		    PreparedStatement pstmt=null;
		    Connection con=null;
		    int ret=10,diff_days=0;
		    
		    try{
		    con=getConnection();
		    stmt=con.createStatement();
		    stmt1=con.createStatement();
		    stmt.executeUpdate("delete from PrecedingForNight where marking_dt>='"+form1_object.getDate()+"'" );
		    stmt.executeUpdate("delete from RBIForm1Transaction where trn_date>='"+form1_object.getDate()+"'" );
		    rs=stmt.executeQuery("select * from PrecedingForNight order by marking_dt desc limit 1");
		    if(rs.next()){
		    	
		    		System.out.println("dater="+rs.getString("marking_dt"));
		    		System.out.println("first "+Validations.convertDMY(rs.getString("marking_dt")));
		    		System.out.println("second "+Validations.convertDMY(form1_object.getDate()));
		    		diff_days=Validations.dayCompare(Validations.convertDMY(rs.getString("marking_dt")),Validations.convertDMY(form1_object.getDate()));
		    		if(diff_days== rs.getInt("recurring_days"))
		    			System.out.println(" days mathc");
		    		else{
		    			stmt1.executeUpdate("update PrecedingForNight set recurring_days="+diff_days+" where marking_dt='"+rs.getString("marking_dt")+"'");
		    			System.out.println("diffeenrece="+diff_days);
		    		}
		    }
		    
		    pstmt=con.prepareStatement("insert into PrecedingForNight values(?,?,?,?,?,?,?)");
		    pstmt.setString(1,form1_object.getDayOfWeek());
		    pstmt.setString(2,form1_object.getDate());
		    pstmt.setInt(3,form1_object.getRecurringDays());
		    pstmt.setDouble(4,form1_object.getNDTLAmount());
		    pstmt.setString(5,form1_object.getDeTml());
		    pstmt.setString(6,form1_object.getDeUser());
		    pstmt.setString(7,form1_object.getDeDate());
		    ret =pstmt.executeUpdate();
		    }catch(Exception e){
				sessioncontext.setRollbackOnly();
				e.printStackTrace();
			}
			finally{
				try{                
					con.close();
				}catch(Exception exception){exception.printStackTrace();}
			}
		    return ret;
		}
		  
		    
	public int MarkingDateDeleteRow(Form1Object form1_object){
	    Statement stmt=null;
	    Connection con=null;
	    ResultSet rs=null,rs1=null;
	    int ret=10;
	    try{
	    con=getConnection();
	    stmt=con.createStatement();
	    ret=stmt.executeUpdate("delete from RBIForm1Transaction  where trn_date>='"+form1_object.getDate()+"'");
	    ret=stmt.executeUpdate("delete from PrecedingForNight where marking_dt>='"+form1_object.getDate()+"'");
	    rs=stmt.executeQuery("select marking_dt from PrecedingForNight where marking_dt<='"+form1_object.getDate()+"' order by marking_dt desc limit 1");
	    if(rs.next()){
	    	rs1=stmt.executeQuery("select recurring_days from PrecedingForNight where marking_dt<'"+rs.getString("marking_dt")+"' order by marking_dt desc limit 1 ");
	    	if(rs1.next())
	    	stmt.executeUpdate("update PrecedingForNight set recurring_days="+rs1.getInt("recurring_days")+" where marking_dt='"+rs.getString("marking_dt")+"'");
	    }
	    return ret;
	    }
	    catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
	    return ret;
	}
	public int MarkingDateUpdateRow(Form1Object form1_object){
		Statement stmt=null;
	    Connection con=null;
	    int ret=10;
	    try{
	    con=getConnection();
	    stmt=con.createStatement();
		 ret=stmt.executeUpdate("update PrecedingForNight set NDTLamount="+form1_object.getNDTLAmount()+",de_user='"+form1_object.getDeUser()+"' , de_tml='"+form1_object.getDeTml()+"', de_dt_time='"+form1_object.getDeDate()+"' where  marking_dt='"+form1_object.getDate()+"' ");
		 System.out.println("ret="+ret);
	    }catch(Exception e){
			e.printStackTrace();
			sessioncontext.setRollbackOnly();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		 return ret;
	}
	public String[][] getMarkingDateDetails(){
	   
	 	String table_data[][]=null;
	 	Connection con=null;
		ResultSet rs=null;
		ResultSetMetaData rs_meta_data=null;
		Statement stmt=null;
		try{
			con=getConnection();
			stmt=con.createStatement();
			rs = stmt.executeQuery("select day_of_week,(concat(right((left (marking_dt,10)),2),'/',substring((left (marking_dt,10)),6,2),'/',substring((left (marking_dt,10)),1,4))) as  marking_dt,recurring_days,NDTLAmount,de_tml,de_user,( concat(right((left (de_dt_time,10)),2),'/',substring((left (de_dt_time,10)),6,2),'/',substring((left (de_dt_time,10)),1,4),' ',right(de_dt_time,8))) as de_dt_time  from PrecedingForNight order by marking_dt");;
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
		catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
	 	return table_data;
	 }
	public String[][] getCRSLRReport(String year,String mth,int code) 
	{
		String firstdate="01"+"/"+mth+"/"+year;
		String lastdate= Validations.lastDayOfMonth(firstdate);
	 	String table_data[][]=null;
	 	Connection con=null;
		ResultSet rs=null;
		ResultSetMetaData rs_meta_data=null;
		Statement stmt=null;
		try{
			con=getConnection();
			stmt=con.createStatement();
			stmt.executeUpdate("drop table if exists RBITran");
			stmt.executeUpdate("drop table if exists result_set");
			stmt.executeUpdate("create table RBITran (gl_type varchar(20) not null default 0,code int(10)not null default 0,name varchar(100) not null default '',trn_date date not null,closing_bal double(20,2) not null default  0.0,dt int(3) not null default  0);");
			if(code==0){
				stmt.executeUpdate("insert into  RBITran select  0,glt.code,glt.name,rbt.trn_date,round(sum(rbt.closing_bal)/1000)  as closing_bal ,right(trn_date,2) from  RBIForm1Head glt left join RBIForm1Transaction rbt on  glt.code = rbt.code and rbt.trn_date between '"+Validations.convertYMD(firstdate)+"' and '"+Validations.convertYMD(lastdate)+"'  and glt.fromdate<='"+Validations.convertYMD(lastdate)+"' and glt.todate>='"+Validations.convertYMD(firstdate)+"' group by glt.code,rbt.trn_date;");
				stmt.executeUpdate("create temporary table result_set select distinct t.code,t.name,(select closing_bal from RBITran where dt=01 and code=t.code) as col1,(select closing_bal from RBITran where dt=02 and code=t.code) as col2 ,(select closing_bal from RBITran where dt=03 and code=t.code) as col3,(select closing_bal from RBITran where dt=04 and code=t.code) as col4,(select closing_bal from RBITran where dt=05 and code=t.code) as col5,(select closing_bal from RBITran where dt=06 and code=t.code) as col6,(select closing_bal from RBITran where dt=07 and code=t.code) as col7,(select closing_bal from RBITran where dt=08 and code=t.code) as coln8,(select closing_bal from RBITran where dt=09 and code=t.code) as col9,(select closing_bal from RBITran where dt=10 and code=t.code) as col10,(select  closing_bal from RBITran where dt=11 and code=t.code) as col11,(select closing_bal from RBITran where dt=12 and code=t.code) as col12,(select closing_bal from RBITran where dt=13 and code=t.code) as col13,(select closing_bal from RBITran where dt=14 and code=t.code) as col14,(select closing_bal from RBITran where dt=15 and code=t.code) as col15,(select closing_bal from RBITran where dt=16 and code=t.code) as col16,(select closing_bal from RBITran where dt=17 and code=t.code) as col17,(select closing_bal from RBITran where dt=18 and code=t.code) as col18,(select closing_bal from RBITran where dt=19 and code=t.code) as col19,(select closing_bal from RBITran where dt=20 and code=t.code) as col20,(select closing_bal from RBITran where dt=21 and code=t.code) as col21,(select closing_bal from RBITran where dt=22 and code=t.code) as col22,(select closing_bal from RBITran where dt=23 and code=t.code) as col23,(select closing_bal from RBITran where dt=24 and code=t.code) as col24,(select closing_bal from RBITran where dt=25 and code=t.code) as col25 ,(select closing_bal from RBITran where dt=26 and code=t.code) as col26,(select closing_bal from RBITran where dt=27 and code=t.code) as col27,(select closing_bal from RBITran where dt=28 and code=t.code) as col28,(select closing_bal from RBITran where dt=29 and code=t.code) as col29,(select closing_bal from RBITran where dt=30 and code=t.code) as col30,(select closing_bal from RBITran where dt=31 and code=t.code) as col31 from RBITran t");
			}
			else{
				stmt.executeUpdate("insert into RBITran select distinct 0,rbt.code,rbh.name,rbt.trn_date,round(sum(closing_bal)/1000) ,right(trn_date,2) from RBIForm1Link rb,RBIForm1Head rbh,RBIForm1Transaction rbt where  rbt.trn_date between '"+Validations.convertYMD(firstdate)+"' and  '"+Validations.convertYMD(lastdate)+"' and rbh.fromdate<='"+Validations.convertYMD(lastdate)+"' and rbh.todate>='"+Validations.convertYMD(firstdate)+"' and rb.trn_src not like'GL' and rb.code="+code+" and rb.gl_code=rbt.code and rbt.code=rbh.code group by rbt.code,rbt.trn_date union select distinct m.moduleabbr,rbt.gl_code,gl.gl_name,rbt.trn_date,closing_bal,right(trn_date,2) from GLMaster gl,RBIForm1Link rb,RBIForm1Transaction rbt,Modules m where rbt.gl_type=m.modulecode and rbt.code=rb.code and rb.code="+code+"  and rbt.gl_code=gl.gl_code and rbt.trn_date between  '"+Validations.convertYMD(firstdate)+"' and  '"+Validations.convertYMD(lastdate)+"' and ((from_date<='"+Validations.convertYMD(lastdate)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(lastdate)+"' and to_date>='"+Validations.convertYMD(firstdate)+"')) and rbt.trn_src like'GL'");
				rs=stmt.executeQuery("select trn_src from RBIForm1Link where code="+code+" and fromdate<='"+Validations.convertYMD(lastdate)+"' and todate>='"+Validations.convertYMD(firstdate)+"'"); 
				if(rs.next()){
					if(rs.getString("trn_src").trim().equals("GL")){
						stmt.executeUpdate("insert into RBITran select 0,rbt.code,'Total',rbt.trn_date,round(sum(closing_bal)/1000) ,right(trn_date,2) from RBIForm1Link rb,RBIForm1Head rbh,RBIForm1Transaction rbt where rb.code="+code+" and rbt.code=rb.code and rbt.trn_date between  '"+Validations.convertYMD(firstdate)+"' and  '"+Validations.convertYMD(lastdate)+"' and rbh.fromdate<= '"+Validations.convertYMD(lastdate)+"' and rbh.todate>= '"+Validations.convertYMD(firstdate)+"' and  rbh.code=rbt.code group by rb.code,rbt.trn_date");
					}
					else{
					stmt.executeUpdate("insert into RBITran select 0,rbt.code,'Total',rbt.trn_date,round(closing_bal/1000) ,right(trn_date,2) from RBIForm1Link rb,RBIForm1Head rbh,RBIForm1Transaction rbt where rb.code="+code+" and rbt.code=rb.code and rbt.trn_date between  '"+Validations.convertYMD(firstdate)+"' and  '"+Validations.convertYMD(lastdate)+"' and rbh.fromdate<= '"+Validations.convertYMD(lastdate)+"' and rbh.todate>= '"+Validations.convertYMD(firstdate)+"' and  rbh.code=rbt.code");
					}
				}
				//stmt.executeUpdate("insert into RBITran select 0,rbt.code,'Total',rbt.trn_date,closing_bal ,right(trn_date,2) from RBIForm1Link rb,RBIForm1Head rbh,RBIForm1Transaction rbt where rb.code="+code+" and rbt.code=rb.code and rbt.trn_date between  '"+Validations.convertYMD(firstdate)+"' and  '"+Validations.convertYMD(lastdate)+"' and rbh.fromdate<= '"+Validations.convertYMD(lastdate)+"' and rbh.todate>= '"+Validations.convertYMD(firstdate)+"' and  rbh.code=rbt.code");
				stmt.executeUpdate("create temporary table result_set select distinct t.gl_type,t.code,t.name,(select closing_bal from RBITran where dt=01 and code=t.code limit 1) as col1,(select closing_bal from RBITran where dt=02 and code=t.code limit 1) as col2 ,(select closing_bal from RBITran where dt=03 and code=t.code limit 1) as col3,(select closing_bal from RBITran where dt=04 and code=t.code limit 1) as col4,(select closing_bal from RBITran where dt=05 and code=t.code limit 1) as col5,(select closing_bal from RBITran where dt=06 and code=t.code limit 1) as col6,(select closing_bal from RBITran where dt=07 and code=t.code limit 1) as col7,(select closing_bal from RBITran where dt=08 and code=t.code limit 1) as coln8,(select closing_bal from RBITran where dt=09 and code=t.code limit 1) as col9,(select closing_bal from RBITran where dt=10 and code=t.code limit 1) as col10,(select  closing_bal from RBITran where dt=11 and code=t.code limit 1) as col11,(select closing_bal from RBITran where dt=12 and code=t.code limit 1) as col12,(select closing_bal from RBITran where dt=13 and code=t.code limit 1) as col13,(select closing_bal from RBITran where dt=14 and code=t.code limit 1) as col14,(select closing_bal from RBITran where dt=15 and code=t.code limit 1) as col15,(select closing_bal from RBITran where dt=16 and code=t.code limit 1) as col16,(select closing_bal from RBITran where dt=17 and code=t.code limit 1) as col17,(select closing_bal from RBITran where dt=18 and code=t.code limit 1) as col18,(select closing_bal from RBITran where dt=19 and code=t.code limit 1) as col19,(select closing_bal from RBITran where dt=20 and code=t.code limit 1) as col20,(select closing_bal from RBITran where dt=21 and code=t.code limit 1) as col21,(select closing_bal from RBITran where dt=22 and code=t.code limit 1) as col22,(select closing_bal from RBITran where dt=23 and code=t.code limit 1) as col23,(select closing_bal from RBITran where dt=24 and code=t.code limit 1) as col24,(select closing_bal from RBITran where dt=25 and code=t.code limit 1) as col25 ,(select closing_bal from RBITran where dt=26 and code=t.code limit 1) as col26,(select closing_bal from RBITran where dt=27 and code=t.code limit 1) as col27,(select closing_bal from RBITran where dt=28 and code=t.code limit 1) as col28,(select closing_bal from RBITran where dt=29 and code=t.code limit 1) as col29,(select closing_bal from RBITran where dt=30 and code=t.code limit 1) as col30,(select closing_bal from RBITran where dt=31 and code=t.code limit 1 ) as col31 from RBITran t");
			}
			rs=stmt.executeQuery("select * from result_set");
			if(rs!=null)
				rs_meta_data = rs.getMetaData();
				if(rs!=null && rs_meta_data!=null && rs.last())
					table_data = new String[rs.getRow()][rs_meta_data.getColumnCount()];
				System.out.println("columns="+rs_meta_data.getColumnCount());
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
		catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
			System.out.println("error");
		}
		finally{
			try{                
				stmt.executeUpdate("drop table if exists RBITran");
				stmt.executeUpdate("drop table if exists result_set");
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
	 	return table_data;
		}
	
	public String[][] getSubSchedules(String year,String mth,int num)
	 {
		String firstdate="01"+"/"+mth+"/"+year;
		String lastdate= Validations.lastDayOfMonth(firstdate);
		Connection con=null;
		String codename[][]=null;
		ResultSet rs=null;
		try{
			con=getConnection();
			Statement stmt=con.createStatement();
			if(num<1)
				rs=stmt.executeQuery("select distinct rb.code,rbh.name from RBIForm1Link rb,RBIForm1Head rbh  where  rb.fromdate<='"+Validations.convertYMD(lastdate)+"' and rb.todate>='"+Validations.convertYMD(firstdate)+"' and rbh.fromdate<='"+Validations.convertYMD(lastdate)+"' and rbh.todate>='"+Validations.convertYMD(firstdate)+"' and rb.code=rbh.code group by rb.code having count(rb.code)>1");
			else
				rs=stmt.executeQuery("select distinct rb.code,rbh.name from RBIForm9Link rb,RBIForm9Master rbh  where  rb.fromdate<='"+Validations.convertYMD(lastdate)+"' and rb.todate>='"+Validations.convertYMD(firstdate)+"' and rbh.fromdate<='"+Validations.convertYMD(lastdate)+"' and rbh.todate>='"+Validations.convertYMD(firstdate)+"' and rb.code=rbh.code group by rb.code having count(rb.code)>1");	
			
			if(rs.next()){
				rs.last();
				codename = new String[rs.getRow()][2];
				System.out.println("codelength="+codename.length);
				rs.beforeFirst();
				int count=0;
				if(codename!=null){
					while(rs.next()){
						codename[count][0]=rs.getString("code");
						System.out.println("code="+codename[count][0]);
					    if(rs.getString("name")!=null)    
						codename[count][1]=rs.getString("name");
					    else
					    codename[count][1]=" ";
						
					    count++;
					}
				}

			}
				
		}catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		//System.out.println("lenght="+codename.length );
	 	return codename;
	 }

	public String[][] getForm1LinkBCDetails(String date)
	{
	String link[][]=null;
	Statement stmt=null;
	Connection con=null;
	ResultSet rs=null;
	String trn_src;
	
	
	try{
		con=getConnection();
		stmt=con.createStatement();
		rs=stmt.executeQuery("select distinct rbh.code,rbh.name,rbh.form1link,rb.trn_src from RBIForm1Head rbh,RBIForm1Link rb where rb.trn_src like'Self' and '"+Validations.convertYMD(date)+"' between rbh.fromdate and rbh.todate and form1link is not null union select distinct rbh.code,rbh.name,rbh.form1link,rb.trn_src from RBIForm1Head rbh,RBIForm1Link rb where '"+Validations.convertYMD(date)+"' between rb.fromdate and rb.todate and rb.code=rbh.code and rb.trn_src like 'NDTL' order by code");
		if(rs.next()){
			trn_src=rs.getString("trn_src");
			if(trn_src.equals("NDTL"))
				System.out.println("got NDTL");
			else{
				link = new String[1][3];
				link[0][0]="0";link[0][1]="0";link[0][2]="0";
				return link;
			}
			rs.last();
			link = new String[rs.getRow()][3];
			System.out.println("codelength="+link.length);
			rs.beforeFirst();
			int count=0;
			if(link!=null){
				while(rs.next()){
					link[count][0]=rs.getString("code");
					link[count][1]=rs.getString("name");
					if(rs.getString("form1link")!=null)
						link[count][2]=rs.getString("form1link");
					else
						link[count][2]="0";
					
				    count++;
				}
			}
		}
	}catch(Exception e){
		sessioncontext.setRollbackOnly();
		e.printStackTrace();
	}
	finally{
		try{                
			con.close();
		}catch(Exception exception){exception.printStackTrace();}
	}
	
	return link;
	}
	public int updateFormLinkdetails(String link[][],int num)
	{
		Statement stmt=null;
		Connection con=null;
		//ResultSet rs=null;
		//String trn_src;
		int ret=0,i=0;
		
		try{
			con=getConnection();
			stmt=con.createStatement();
			System.out.println("link len="+link.length);
			if(num==0){
				for(i=1;i<link.length;i++)
					ret=stmt.executeUpdate("update RBIForm1Head set form1link="+Integer.parseInt(link[i][1])+" where code="+Integer.parseInt(link[i][0])+" and '"+Validations.convertYMD(link[i][2])+"' between fromdate and todate");
			}
			else{
				for(i=0;i<link.length;i++)
					ret=stmt.executeUpdate("update RBIForm9Master set form9link=1 where code="+Integer.parseInt(link[i][0])+" and '"+Validations.convertYMD(link[i][1])+"' between fromdate and todate");
			}
		}catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
	return ret;	
	}
	public String [] getMarkingFridays(String  year,String mth){
		String form[]=null;
		Statement stmt=null;
		Connection con=null;
		ResultSet rs=null;
		try{
			con=getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery("select marking_dt from PrecedingForNight where year(marking_dt)="+year+" and month(marking_dt)="+Integer.parseInt(mth)+"  order by marking_dt" );
			if(rs.next()){
				rs.last();
				form=new String[rs.getRow()];
				rs.beforeFirst();
				int count=0;
				while(rs.next()){
					form[count]=Validations.convertDMY(rs.getString(1));
					count++;
				}
			}
		}catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
	return form;	
	}
	public String[][] getForm1AReport(String year,String mth) 
	{
		String form1a[][]=null,markingdate[]=new String[3];
		
		Statement stmt=null;
		Connection con=null;
		ResultSet rs=null,rs2=null;
		String firstdate=01+"/"+mth+"/"+year;
		String lastdate=Validations.lastDayOfMonth(firstdate);
		firstdate=Validations.convertYMD(firstdate);
		lastdate=Validations.convertYMD(lastdate);
		int row=0,count=0;
		System.out.println("first date="+firstdate);
		System.out.println("last date="+lastdate);
		
		try{
			con=getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery("select marking_dt from PrecedingForNight where marking_dt between '"+firstdate+"' and '"+lastdate+"'");
			if(rs.next())
			{
				rs.last();
				row=rs.getRow();
				rs.beforeFirst();
				while(rs.next()){
					markingdate[count]=rs.getString("marking_dt");
					count++;
				}
				if(row==1){
					for(int i=count;i<markingdate.length;i++)
						markingdate[count]="0";
				}
				if(row==2){
					for(int i=count;i<markingdate.length;i++)
						markingdate[count]="0";
				}
			stmt.executeUpdate("drop table if exists RBItemp");	
			stmt.executeUpdate("create temporary table RBItemp select code,name from RBIForm1Head where code like'%00' and todate>='"+firstdate+"' and fromdate<='"+lastdate+"' ");
			rs2=stmt.executeQuery("select distinct rbh.code,rbh.name,(select sum(closing_bal) from RBIForm1Transaction where code=rbt.code and trn_date='"+markingdate[0]+"')as col1,(select sum(closing_bal) from RBIForm1Transaction where code=rbt.code and trn_date='"+markingdate[1]+"')as col2,(select sum(closing_bal) from RBIForm1Transaction where code=rbt.code and trn_date='"+markingdate[2]+"')as col3 from  RBItemp rbh left join RBIForm1Transaction rbt on rbt.code=rbh.code and rbh.code in(select code from RBIForm1Head where code like'%00') and rbt.trn_date between '"+firstdate+"' and '"+lastdate+"'   group by rbt.code,rbh.name order by code");
			stmt.executeUpdate("drop table if exists RBItemp");
			if(rs2.next()){
				rs2.last();
				row=rs2.getRow();
				form1a=new String[row][5];
				rs2.beforeFirst();
				rs2.next();
				if(form1a!=null){
					for(int i=0;i<form1a.length;i++){
							for(int j=0;j<form1a[i].length;j++)
								form1a[i][j]=rs2.getString(j+1);
						rs2.next();
					}
				}
			}
		}	
		}catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return form1a;
	}
	public String[][]  getForm1BCReport(String year,String mth,int num)
	{
		System.out.println("I entered into the GLBean Method on 3rd Feb 09");
		Statement stmt=null;
		Connection con=null;
		ResultSet rs=null;
		String firstdate=01+"/"+mth+"/"+year;
		String lastdate=Validations.lastDayOfMonth(firstdate);
		firstdate=Validations.convertYMD(firstdate);
		lastdate=Validations.convertYMD(lastdate);
		int row=0,count=0;
		String form1b[][]=null;
		
		try{
			con=getConnection();
			stmt=con.createStatement();
			if(num==1)
				rs=stmt.executeQuery("select trn_date,(select closing_bal/100000  from RBIForm1Transaction where trn_date =rbt1.trn_date and trn_src like'NDTL' group by code,trn_date limit 1 ) as col1 ,(select sum(closing_bal)/100000 from RBIForm1Transaction rbt, RBIForm1Head rbh where form1link=1 and rbh.code=rbt.code and trn_date =rbt.trn_date  and fromdate<='"+lastdate+"' and todate>='"+firstdate+"' group by rbt.code,trn_date limit 1 )as col2,(select sum(closing_bal)/100000 from RBIForm1Transaction rbt, RBIForm1Head rbh where form1link=2 and rbh.code=rbt.code and trn_date =rbt.trn_date and fromdate<='"+lastdate+"' and todate>='"+firstdate+"' group by rbt.code,trn_date limit 1)as col3 from RBIForm1Transaction rbt1 where rbt1.trn_date between '"+firstdate+"' and '"+lastdate+"' group by trn_date");
			else
				rs=stmt.executeQuery("select trn_date,(select closing_bal/100000  from RBIForm1Transaction where trn_date =rbt1.trn_date and trn_src like'NDTL' group by code,trn_date limit 1 ) as col1 ,(select sum(closing_bal)/100000 from RBIForm1Transaction rbt, RBIForm1Head rbh where form1link=3 and rbh.code=rbt.code and trn_date =rbt.trn_date  and fromdate<='"+lastdate+"' and todate>='"+firstdate+"' group by rbt.code,trn_date limit 1 )as col2,(select sum(closing_bal)/100000 from RBIForm1Transaction rbt, RBIForm1Head rbh where form1link=4 and rbh.code=rbt.code and trn_date =rbt.trn_date and fromdate<='"+lastdate+"' and todate>='"+firstdate+"' group by rbt.code,trn_date limit 1)as col3 from RBIForm1Transaction rbt1 where rbt1.trn_date between '"+firstdate+"' and '"+lastdate+"' group by trn_date");
			
			if(rs.next()){
				rs.last();
				row=rs.getRow();
				form1b=new String[row+1][7];
				rs.beforeFirst();
				while(rs.next()){
					count++;
					form1b[count][0]=String.valueOf(count);
					form1b[count][1]=Validations.convertDMY(rs.getString("trn_date"));
					if(rs.getString(2)!=null)
						form1b[count][2]=DoubleFormat.doubleToString(Double.parseDouble(rs.getString(2)),2);
					else
						form1b[count][2]=rs.getString(2);
					
					//form1b[count][2]=String.ValueOf(DoubleFormat.doublePrecision(Double.parseDouble(rs.getString(3)),2));
					System.out.println("val="+rs.getDouble(2));
					System.out.println(" round val="+DoubleFormat.doubleToString(rs.getDouble(2),2));
					form1b[count][3]=DoubleFormat.doubleToString(rs.getDouble(3),2);
					form1b[count][4]=DoubleFormat.doubleToString(rs.getDouble(4),2);
					//form1b[count][3]=String.ValueOf(DoubleFormat.doublePrecision(Double.parseDouble(rs.getString(4)),2));;
					if(Double.parseDouble(form1b[count][3])>=Double.parseDouble(form1b[count][4])){
						form1b[count][5]=String.valueOf(Double.parseDouble(form1b[count][3])-Double.parseDouble(form1b[count][4]));
						form1b[count][6]="0.00";
					}
					else{
						form1b[count][5]="0.00";
						form1b[count][6]=String.valueOf(Double.parseDouble(form1b[count][4])-Double.parseDouble(form1b[count][3]));
					}
				}
				System.out.println("Out of whille -----------------");
			}
		}
		catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return form1b;
	}
	public String[][] checkPostings(String date,String todate){
		Connection con=null;
		Statement stmt=null,stmt1=null;
		ResultSet rs=null,rs1=null;
		int year_mth=convertToYYYYMM(date);
		String ret[][]=new String[1][3];
		int br_code=0,daily_count=0,mth_count=0,rows=0;
		try{
			con=getConnection();
			stmt=con.createStatement();
			stmt1=con.createStatement();
			rs=stmt.executeQuery("select * from BranchMaster where (br_code%1111)!=0 ");
			if(rs.next()){
				while(rs.next()){
					br_code=rs.getInt("br_code");
					System.out.println("br_code="+br_code);
					rs1=stmt1.executeQuery("select * from DailyConStatus where trn_dt<='"+Validations.convertYMD(date)+"' and br_code="+br_code+" order by trn_dt " );
					if(rs1.next()){
						System.out.println("greater than minimum date");
					}
					else{
						ret[0][0]="MIN";
						return ret;
					}
					rs1=stmt1.executeQuery("select * from MonthlyConStatus where yr_mth<"+year_mth+"  and br_code="+br_code+" and post_ind='F' ");
					if(rs1.next()){
						ret[0][0]="PFM";
						ret[0][1]=rs.getString("br_name");
						ret[0][2]=rs1.getString("yr_mth");
						return ret;
					}
					rs1=stmt1.executeQuery("select * from DailyConStatus where trn_dt<='"+Validations.convertYMD(date)+"' and br_code="+br_code+" and post_ind='F' ");
					if(rs1.next()){
						ret[0][0]="PFD";
						ret[0][1]=rs.getString("br_name");
						ret[0][2]=rs1.getString("trn_dt");
						return ret;
					}
					rs1=stmt1.executeQuery("select * from DailyConStatus where trn_dt between '"+Validations.convertYMD(date)+"' and '"+Validations.convertYMD(todate)+"' and br_code="+br_code+" and post_ind='F' ");
					if(rs1.next()){
						ret[0][0]="PFD";
						ret[0][1]=rs.getString("br_name");
						ret[0][2]=rs1.getString("trn_dt");
						return ret;
					}
					rs1=stmt1.executeQuery("select * from MonthlyConStatus where yr_mth between "+year_mth+"  and "+convertToYYYYMM(todate)+" and br_code="+br_code+" and post_ind='F' ");
					if(rs1.next()){
						ret[0][0]="PFM";
						ret[0][1]=rs.getString("br_name");
						ret[0][2]=rs1.getString("yr_mth");
						return ret;
					}
				}
			rows=rs.getRow();
			System.out.println("rows="+rows);
			rs.beforeFirst();
			while(rs.next()){
				br_code=rs.getInt("br_code");
				rs1=stmt1.executeQuery("select * from DailyConStatus where trn_dt between '"+Validations.convertYMD(date)+"' and '"+Validations.convertYMD(todate)+"' and br_code="+br_code+" and post_ind='T' ");
				if(rs1.next()){
					daily_count++;
					System.out.println("daily="+daily_count);
				}
				rs1=stmt1.executeQuery("select * from MonthlyConStatus where yr_mth betwen "+convertToYYYYMM(date)+" and "+convertToYYYYMM(todate)+" and br_code="+br_code+" and post_ind='T' ");
				if(rs1.next()){
					mth_count++;
					System.out.println("month="+mth_count);
				}
			}
			if(rows==daily_count && rows==mth_count){
				ret[0][0]="OK";
				ret[0][1]="OK";
				return ret;
			}
			else{
				ret[0][0]="NO";
				ret[0][1]="NO";
				return ret;
			}
		}
		else{
			ret[0][0]="NOB";
			ret[0][1]="NOB";
			return ret;
		}
		}
		catch(Exception e){e.printStackTrace();}
			finally{
				try{                
					con.close();
				}catch(Exception exception){exception.printStackTrace();}
			}
		return ret;
	}
	public int  checkForm9Transaction(String lastfriday) 
	{
		Connection con=null;
		Statement stmt=null;
		//PreparedStatement pstmt=null;
		ResultSet rs=null;//,rs1=null,rs_select=null,rs3=null;
		int ret=0;
		try{
			con=getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery("select distinct trn_date from RBIForm9Transaction where trn_date='"+Validations.convertYMD(lastfriday)+"' order by trn_date desc limit 1 ");
			if(rs.next()){
				ret=1;
				return ret;
			}
		}catch(Exception e){e.printStackTrace();}
			finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
	return ret;
	}
	
	public boolean Form9Posting(String lastfriday,String de_user,String de_tml,String datetime,String amount[][]) 
	{
		Connection con=null;
		Statement stmt=null,stmt_update=null,stmt1=null;//,stmt2=null,stmt3=null;
		//PreparedStatement pstmt=null;
		ResultSet rs=null;//,rs1=null,rs_select=null;//,rs2=null,rs3=null;
		String  temp_fromdate=null;//,date=null;;
		int yearmth=convertToYYYYMM(lastfriday),ret=1;
		temp_fromdate=Validations.convertYMD(lastfriday);
		
		System.out.println("temp_fromdate = "+temp_fromdate);
		try{
		con=getConnection();
		stmt=con.createStatement();
		//stmt_select=con.createStatement();
		stmt_update=con.createStatement();
		stmt1=con.createStatement();
		//stmt2=con.createStatement();
		//stmt3=con.createStatement();
		stmt_update.executeUpdate("delete from RBIForm9Transaction where trn_date='"+temp_fromdate+"'");

		stmt.addBatch("drop table if exists gl_temp");
		stmt.addBatch("drop table if exists month_data");
		stmt.addBatch("drop table if exists daily_data");
		stmt.addBatch("drop table if exists day_month");
		stmt.addBatch("drop table if exists RBITran1");
		stmt.addBatch("drop table if exists RBItemp2");
		stmt.addBatch("drop table if exists RBItemp1");
		stmt.executeBatch();
				
		stmt_update.addBatch("create temporary table gl_temp select code,gl_type,gl_code,cd_ind from RBIForm9Link where trn_src like 'GL'  and '"+temp_fromdate+"'  between fromdate and  todate");
		stmt_update.addBatch("create temporary table month_data (code int(10),gl_type varchar(20),gl_code int(11),normal_cd varchar(5),debit_sum double(19,2) not null default 0.0 ,credit_sum double(19,2) not null default 0.0 ,next_month date not null)");
		stmt_update.addBatch("create table daily_data (code int(10),gl_type varchar(20),gl_code int(10),normal_cd varchar(3),day_balance double(20,2) not null default 0.0)");

		stmt_update.addBatch("insert into month_data select gt.*,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum,max(concat(left((period_add(yr_mth,1)),4),'-',right((period_add(yr_mth,1)),2),'-','01')) from gl_temp as gt left join MonthlySummary as ms on  ms.yr_mth<"+yearmth+" and ms.gl_type=gt.gl_type and ms.gl_code=gt.gl_code  group by code,gl_type,gl_code");
		stmt_update.addBatch("insert into daily_data select md.code,md.gl_type,md.gl_code,md.normal_cd,(sum(ds.cash_cr)+sum(ds.cg_cr)+sum(ds.trf_cr))-(sum(ds.cash_dr)+sum(ds.cg_dr)+sum(ds.trf_dr))as day_balance from  month_data as md left join DailySummary as ds on   md.gl_type =ds.gl_type and md.gl_code = ds.gl_code and (trn_dt >=md.next_month and trn_dt<='"+temp_fromdate+"') group by md.code,md.gl_type,md.gl_code");
		stmt_update.addBatch("create temporary table day_month (code int(10),gl_type varchar(20),gl_code int(11),normal_cd varchar(5),balance double(19,2)  not null Default 0.0)");
		stmt_update.addBatch("insert into day_month select md.code,md.gl_type,md.gl_code,md.normal_cd, (credit_sum-debit_sum)+day_balance as balance  from month_data md left join  daily_data as dd on md.gl_type=dd.gl_type and md.gl_code=dd.gl_code and md.code=dd.code");
				
		stmt_update.addBatch("create table RBITran1 select rb.code,rb.trn_src,rb.gl_type,rb.gl_code,if(normal_cd='D',(balance*(-1)*(mul_by)*(percentage/100)),(balance*(mul_by)*(percentage/100))) as balance,rb.sequence from RBIForm9Link rb left join day_month dm on rb.gl_code=dm.gl_code and rb.gl_type=dm.gl_type and rb.code=dm.code  where rb.trn_src='GL'");
		stmt_update.executeBatch();
		System.out.println("before while....");
		rs=stmt.executeQuery("select code,sum(balance) as balance from RBITran1 where sequence=1 group by code");
		if(rs.next()){
			rs.beforeFirst();
			while(rs.next()){
				int code=rs.getInt(1);
				double bal=rs.getDouble(2);
				if(bal<0.0){
					stmt1.executeUpdate("update RBITran1 set balance=0.0 where code="+code+"  and sequence =1 ");
					System.out.println("code  1="+code);
					System.out.println("bal 1 ="+bal);
				}
				
				System.out.println("code="+code);
				System.out.println("bal="+bal);
			}
		}
		while(ret!=0)
		{
			System.out.println("before ret = "+ret);
			
			stmt1.executeUpdate("drop  table if exists RBItemp2");
			stmt1.executeUpdate("drop table if exists RBItemp1");
			stmt1.executeUpdate("create table RBItemp1 select rb.code as code,rb.trn_src,sum(balance*mul_by*(percentage/100)) as balance ,count(distinct rbt.code)as rbtcount,(select count(gl_code) from RBIForm9Link where '"+temp_fromdate+"'  between fromdate and  todate  and rb.code=code group by code)as rbcount from   RBITran1 rbt,RBIForm9Link rb where '"+temp_fromdate+"'  between fromdate and  todate and rb.gl_code=rbt.code  and rb.code not in (select code from RBITran1 )group by rb.code,rb.sequence");
			stmt1.executeUpdate("create table RBItemp2 select t.code,t.trn_src,(select sum(balance) from RBItemp1 where code=t.code  group by code) as balance ,sum(t.rbtcount) as rbtcount,t.rbcount from RBItemp1 t  group by t.code");
			ret=stmt1.executeUpdate("delete from RBItemp2  where rbtcount!=rbcount");
			ret=stmt1.executeUpdate("insert into RBITran1 (select code,trn_src,0,0,balance,0 from RBItemp2)");
			
			System.out.println("after ret = "+ret);
		}
		System.out.println("after while....");
		ret=stmt1.executeUpdate("insert into RBIForm9Transaction select code,trn_src,gl_type,gl_code,'"+temp_fromdate+"',balance,sequence,'"+de_tml+"','"+de_user+"','"+datetime+"' from RBITran1");
		for(int i=0;i<amount.length;i++){
			ret=stmt1.executeUpdate("insert into RBIForm9Transaction values('"+amount[i][0]+"','','','','"+temp_fromdate+"',"+amount[i][1]+",0,'"+de_tml+"','"+de_user+"','"+datetime+"')");
		}
		 
		if(ret>0)
		{
			System.out.println("RBIForm9Transaction");
			return true;
		}
		}
		catch(Exception e){
			sessioncontext.setRollbackOnly();
			e.printStackTrace();
			return false;
			}
		finally{
			try{                
				stmt.executeBatch() ;
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return true;
	}
	public String[][] getForm9Report(String year,String mth,int code,String qry)
	{
		String table_data[][]=null;
	 	Connection con=null;
		ResultSet rs=null,rs1=null;
		ResultSetMetaData rs_meta_data=null;
		Statement stmt=null;
		String firstdate="01"+"/"+mth+"/"+year;
		String lastdate= Validations.lastDayOfMonth(firstdate);
	 	
		try{
			con=getConnection();
			stmt=con.createStatement();
			if(code==0){
				System.out.println("Changed on 2nd Feb 09=================");
				if(qry==null)
					rs = stmt.executeQuery("select  glt.code,glt.name,round(sum(rbt.closing_bal)/100000,2)  as closing_bal  from  RBIForm9Master glt left join RBIForm9Transaction rbt on  glt.code = rbt.code and year(rbt.trn_date)='"+year+"' and month(rbt.trn_date)='"+mth+"'  and  rbt.trn_date between glt.fromdate and glt.todate  group by glt.code,rbt.trn_date order by code");
				else
					rs = stmt.executeQuery("select  glt.code,glt.name,round(sum(rbt.closing_bal)/100000,2)  as closing_bal  from  RBIForm9Master glt left join RBIForm9Transaction rbt on  glt.code = rbt.code and year(rbt.trn_date)="+year+" and month(rbt.trn_date)="+mth+"  and  rbt.trn_date between glt.fromdate and glt.todate  and "+qry+" group by glt.code,rbt.trn_date order by code;");
			}
		    else if(code!=0){
		    	stmt.executeUpdate("drop table if exists RBITran");
				stmt.executeUpdate("create table RBITran (gl_type varchar(20) not null default 0,code int(10)not null default 0,name varchar(100) not null default '',closing_bal double(20,2) not null default 0.0);");
				stmt.executeUpdate("insert into RBITran select distinct 0,rbt.code,rbh.name,round(sum(closing_bal)/1000)  from RBIForm9Link rb,RBIForm9Master rbh,RBIForm9Transaction rbt where  rbt.trn_date between '"+Validations.convertYMD(firstdate)+"' and  '"+Validations.convertYMD(lastdate)+"' and rbh.fromdate<='"+Validations.convertYMD(lastdate)+"' and rbh.todate>='"+Validations.convertYMD(firstdate)+"' and rb.trn_src not like'GL' and rb.code="+code+" and rb.gl_code=rbt.code and rbt.code=rbh.code group by rbt.code,rbt.trn_date union select distinct m.moduleabbr,rbt.gl_code,gl.gl_name,round(closing_bal/1000,2) from GLMaster gl,RBIForm9Link rb,RBIForm9Transaction rbt,Modules m where rbt.gl_type=m.modulecode and rbt.code=rb.code and rb.code="+code+"  and rbt.gl_code=gl.gl_code and rbt.trn_date between  '"+Validations.convertYMD(firstdate)+"' and  '"+Validations.convertYMD(lastdate)+"' and ((from_date<='"+Validations.convertYMD(lastdate)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(lastdate)+"' and to_date>='"+Validations.convertYMD(firstdate)+"')) and rbt.trn_src like'GL'");
				rs1=stmt.executeQuery("select trn_src from RBIForm9Link where code="+code+" and fromdate<='"+Validations.convertYMD(lastdate)+"' and todate>='"+Validations.convertYMD(firstdate)+"'"); 
				if(rs1.next()){
					if(rs1.getString("trn_src").trim().equals("GL")){
						stmt.executeUpdate("insert into RBITran select 0,rbt.code,'Total',round(sum(closing_bal)/1000)  from RBIForm9Link rb,RBIForm9Master rbh,RBIForm9Transaction rbt where rb.code="+code+" and rbt.code=rb.code and rbt.trn_date between  '"+Validations.convertYMD(firstdate)+"' and  '"+Validations.convertYMD(lastdate)+"' and rbh.fromdate<='"+Validations.convertYMD(lastdate)+"' and rbh.todate>='"+Validations.convertYMD(firstdate)+"'  and  rbh.code=rbt.code group by rb.code,rbt.trn_date");
					}
					else{
						stmt.executeUpdate("insert into RBITran select distinct 0,rbt.code,'Total',round(closing_bal/1000) from RBIForm9Link rb,RBIForm9Master rbh,RBIForm9Transaction rbt where rb.code="+code+" and rbt.code=rb.code and rbt.trn_date between  '"+Validations.convertYMD(firstdate)+"' and  '"+Validations.convertYMD(lastdate)+"' and rbh.fromdate<= '"+Validations.convertYMD(lastdate)+"' and rbh.todate>= '"+Validations.convertYMD(firstdate)+"' and  rbh.code=rbt.code");
					}
					if(qry==null)
						rs=stmt.executeQuery("select * from RBITran ");
					else
						rs=stmt.executeQuery("select * from RBITran where "+qry+" ");
				}
		    }
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
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
	 	return table_data;
		
	}
	public GLReportObject[] getGLSchduleDetailsForTwoDates(String date1,String date2,String from_gltype,int from_glcode,String to_gltype,int to_glcode)
	{
		Statement stmt=null,stmt_update=null,stmt_rs=null;
		Connection con=null;
		ResultSet rs=null;//,rs1=null,rs2=null;
		int yearmth1,yearmth2;
		GLReportObject gl_object[]=null;
		try{
			yearmth1=convertToYYYYMM(date1);
			yearmth2=convertToYYYYMM(date2);
			date1=Validations.convertYMD(date1);
			date2=Validations.convertYMD(date2);
			
			con=getConnection();
			stmt=con.createStatement();
			stmt_update=con.createStatement();
			stmt_rs=con.createStatement() ;
			System.out.println("from glno="+from_glcode);
			System.out.println("to glno="+to_glcode);
			System.out.println("from gltype="+from_gltype);
			System.out.println("to gltype="+to_gltype);
			System.out.println("from date="+date1);
			System.out.println("date2="+date2);
			
			stmt.addBatch("drop table if exists details;"); 
			stmt.addBatch("drop table if exists gl_det;");
			stmt.addBatch("drop table if exists gl_temp ;");
			stmt.addBatch("drop table if exists month_data_first_date;");
			stmt.addBatch("drop table if exists daily_data_first_date;");
			stmt.addBatch("drop table if exists day_month_first_date;");
			stmt.addBatch("drop table if exists month_data_second_date;");
			stmt.addBatch("drop table if exists daily_data_second_date;");
			stmt.addBatch("drop table if exists day_month_second_date;");
			stmt.executeBatch();
			
			stmt_update.addBatch("create temporary table details (trn_date varchar(20),gl_type varchar(10),gl_abbr varchar(5),gl_code int (10),gl_name varchar(50),ref_ac_type varchar(12),ac_abbr varchar(10),ref_ac_no int(12),name varchar(30),trn_mode varchar(3),trn_amt double(15,2),cd_ind varchar(2),ref_tr_seq int(10),ref_tr_type varchar(2),trn_desc varchar(15),open_bal double(30,2),close_bal double(30,2),normal_cd varchar(3));");
			stmt_update.addBatch("insert into details(trn_date,gl_type,gl_code,ref_ac_type,ref_ac_no,trn_mode,trn_amt,cd_ind,ref_tr_seq,ref_tr_type)select trn_date,gl_type,gl_code,ref_ac_type,ref_ac_no,trn_mode,trn_amt,cd_ind,ref_tr_seq,ref_tr_type from GLTranOld where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+date1+"' and '"+date2+"' and  gl_code between "+from_glcode+" and "+to_glcode+" and gl_type between '"+from_gltype+"' and '"+to_gltype+"' order by gl_type,gl_code ;");
			stmt_update.addBatch("update details d set name=(select concat(IFNULL(cm.fname,''),IFNULL(cm.mname,''),IFNULL(cm.lname,'')) from CustomerMaster cm,AccountMaster am where cm.cid=am.cid and am.ac_type=d.ref_ac_type and am.ac_no=d.ref_ac_no) where ref_ac_type like'1002%' or ref_ac_type like'1017%' or ref_ac_type like'1018%';");
			stmt_update.addBatch("update details d set name=(select concat(IFNULL(cm.fname,''),IFNULL(cm.mname,''),IFNULL(cm.lname,'')) from CustomerMaster cm,ODCCMaster am where cm.cid=am.cid and am.ac_type=d.ref_ac_type and am.ac_no=d.ref_ac_no) where ref_ac_type like'1015%' or ref_ac_type like'1014%'; "); 
			stmt_update.addBatch("update details d set name=(select concat(IFNULL(cm.fname,''),IFNULL(cm.mname,''),IFNULL(cm.lname,'')) from CustomerMaster cm,LoanMaster am where cm.cid=am.cid and am.ac_type=d.ref_ac_type and am.ac_no=d.ref_ac_no) where ref_ac_type like'1008%' or ref_ac_type like'1010%'");
			stmt_update.addBatch("update details d set name=(select concat(IFNULL(cm.fname,''),IFNULL(cm.mname,''),IFNULL(cm.lname,'')) from CustomerMaster cm,LockerMaster am where cm.cid=am.cid and am.ac_type=d.ref_ac_type and am.ac_no=d.ref_ac_no) where ref_ac_type like'1009%';  ");
			stmt_update.addBatch("update details d set name=(select concat(IFNULL(cm.fname,''),IFNULL(cm.mname,''),IFNULL(cm.lname,'')) from CustomerMaster cm,DepositMaster am where cm.cid=am.cid and am.ac_type=d.ref_ac_type and am.ac_no=d.ref_ac_no) where ref_ac_type like'1003%' or ref_ac_type like'1004%' or ref_ac_type like'1005%';  ;");
			stmt_update.addBatch("update details d set name=(select concat(IFNULL(cm.fname,''),IFNULL(cm.mname,''),IFNULL(cm.lname,'')) from CustomerMaster cm,PygmyMaster am where cm.cid=am.cid and am.ac_type=d.ref_ac_type and am.ac_no=d.ref_ac_no) where ref_ac_type like'1006%';");
			stmt_update.addBatch("update details d set name=(select concat(IFNULL(cm.fname,''),IFNULL(cm.mname,''),IFNULL(cm.lname,'')) from CustomerMaster cm,AgentMaster am where cm.cid=am.cid and am.ac_type=d.ref_ac_type and am.ac_no=d.ref_ac_no) where ref_ac_type like'1013%';;");
			stmt_update.addBatch("update details d set name=(select concat(IFNULL(cm.fname,''),IFNULL(cm.mname,''),IFNULL(cm.lname,'')) from CustomerMaster cm,ShareMaster am where cm.cid=am.cid and am.ac_type=d.ref_ac_type and am.ac_no=d.ref_ac_no) where ref_ac_type like'1001%';");
			stmt_update.addBatch("update details m set name=(select distinct narration from VoucherData where vch_no=ref_ac_no and m.trn_date=trn_date and m.ref_tr_type=vch_type) where ref_tr_type in('R','P','T') and ref_ac_type=1019001;");
			stmt_update.addBatch("update details set ac_abbr=(select moduleabbr from Modules where modulecode=ref_ac_type);");
			stmt_update.addBatch("update details d set trn_desc=(select trn_desc from GLTransactionType where ac_type=ref_ac_type and gl_code=d.gl_code and trn_type=ref_tr_type);");
			
			stmt_update.addBatch("create temporary table gl_temp select distinct gm.gl_type,gm.gl_code,gm.gl_name,gm.normal_cd from  GLMaster gm,details de where  gm.gl_code=de.gl_code  and gm.gl_type=de.gl_type order by gm.gl_type,gm.gl_code;");
			stmt_update.addBatch("create temporary table month_data_first_date (gl_type varchar(20),gl_code int(11),gl_name varchar(50),normal_cd varchar(5),debit_sum double(19,2) not null default 0.0 ,credit_sum double(19,2) not null default 0.0 ,next_month  date not null);");
			stmt_update.addBatch("create temporary table month_data_second_date (gl_type varchar(20),gl_code int(11),gl_name varchar(50),normal_cd varchar(5),debit_sum double(19,2) not null default 0.0 ,credit_sum double(19,2) not null default 0.0 ,next_month  date not null);");
			stmt_update.addBatch("insert into month_data_first_date select gt.*,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum,max(concat(left((period_add(yr_mth,1)),4),'-',right((period_add(yr_mth,1)),2),'-','01')) from gl_temp as gt left join MonthlySummary as ms on  ms.yr_mth<"+yearmth1+" and ms.gl_type=gt.gl_type and ms.gl_code=gt.gl_code group by gl_type,gl_code;");
			stmt_update.addBatch("insert into month_data_second_date select gt.*,sum(cash_dr)+sum(cg_dr)+sum(trf_dr) as debit_sum,sum(cash_cr)+sum(cg_cr)+sum(trf_cr) as credit_sum,max(concat(left((period_add(yr_mth,1)),4),'-',right((period_add(yr_mth,1)),2),'-','01')) from gl_temp as gt left join MonthlySummary as ms on  ms.yr_mth<"+yearmth2+" and ms.gl_type=gt.gl_type and ms.gl_code=gt.gl_code group by gl_type,gl_code;");
			stmt_update.addBatch("create temporary table daily_data_first_date select md.gl_type,md.gl_code,md.gl_name,md.normal_cd ,sum(ds.cash_cr)+sum(ds.cg_cr)+sum(ds.trf_cr)-(sum(ds.cash_dr)+sum(ds.cg_dr)+sum(ds.trf_dr))as day_balance from  month_data_first_date as md,DailySummary as ds where   md.gl_type =ds.gl_type and md.gl_code = ds.gl_code and (trn_dt >=md.next_month and trn_dt<='"+date1+"') group by md.gl_type,md.gl_code;");
			stmt_update.addBatch("create temporary table daily_data_second_date select md.gl_type,md.gl_code,md.gl_name,md.normal_cd,sum(ds.cash_cr)+sum(ds.cg_cr)+sum(ds.trf_cr)-(sum(ds.cash_dr)+sum(ds.cg_dr)+sum(ds.trf_dr))as day_balance from  month_data_second_date as md,DailySummary as ds where   md.gl_type =ds.gl_type and md.gl_code = ds.gl_code and (trn_dt >=md.next_month and trn_dt<='"+date2+"') group by md.gl_type,md.gl_code;");
			stmt_update.addBatch("create temporary table day_month_first_date (gl_type varchar(20),gl_code int(11),gl_name varchar(50),normal_cd varchar(5),month_balance double(19,2)  not null Default 0.0,day_balance double(19,2)  not null Default 0.0);");
			stmt_update.addBatch("create temporary table day_month_second_date (gl_type varchar(20),gl_code int(11),gl_name varchar(50),normal_cd varchar(5),month_balance double(19,2)  not null Default 0.0,day_balance double(19,2)  not null Default 0.0);");
			stmt_update.addBatch("insert into day_month_first_date select md.gl_type,md.gl_code,md.gl_name,md.normal_cd, credit_sum-debit_sum,day_balance from month_data_first_date md left join  daily_data_first_date as dd on md.gl_type=dd.gl_type and md.gl_code=dd.gl_code;");
			stmt_update.addBatch("insert into day_month_second_date select md.gl_type,md.gl_code,md.gl_name, md.normal_cd,credit_sum-debit_sum,day_balance from month_data_second_date md left join  daily_data_second_date as dd on md.gl_type=dd.gl_type and md.gl_code=dd.gl_code;");
			stmt_update.addBatch("create temporary table gl_det select distinct moduleabbr,df.gl_code,df.gl_type,df.gl_name,df.month_balance+df.day_balance as balance_first,ds.month_balance+ds.day_balance  as balance_second ,df.normal_cd  from day_month_first_date as df, Modules as mo,day_month_second_date as ds where df.gl_type=mo.modulecode and df.gl_type=ds.gl_type and df.gl_code=ds.gl_code  order by df.gl_type,df.gl_code;");
			stmt_update.addBatch("update details de set gl_abbr=(select moduleabbr from gl_det where de.gl_type=gl_type and gl_code=de.gl_code);");
			stmt_update.addBatch("update details de set gl_name=(select gl_name from gl_det where de.gl_type=gl_type and gl_code=de.gl_code);");
			stmt_update.addBatch("update details de set open_bal=(select balance_first from gl_det where de.gl_type=gl_type and gl_code=de.gl_code);");
			stmt_update.addBatch("update details de set close_bal=(select balance_second from gl_det where de.gl_type=gl_type and gl_code=de.gl_code);");
			stmt_update.addBatch("update details de set normal_cd=(select normal_cd from gl_det where de.gl_type=gl_type and gl_code=de.gl_code);");
			stmt_update.executeBatch();
			
			rs=stmt_rs.executeQuery("select * from details order by gl_type,gl_code,ref_ac_type,concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)),ref_ac_no;");
			if(rs!=null){
				rs.last();
				gl_object = new GLReportObject[rs.getRow()];
				System.out.println("gl_object="+gl_object.length);
				rs.beforeFirst();
				int row=0;
				while(rs.next()){
					
					gl_object[row] = new GLReportObject();
					
					gl_object[row].setDate(rs.getString("trn_date"));
					gl_object[row].setGLAbbr(rs.getString("gl_abbr"));
					gl_object[row].setGLType(rs.getString("gl_type"));
					gl_object[row].setGLCode(rs.getInt("gl_code"));
					gl_object[row].setGLName(rs.getString("gl_name"));
					gl_object[row].setAcType(rs.getString("ref_ac_type")==null?" ":rs.getString("ref_ac_type"));
					gl_object[row].setAcNo(rs.getString("ref_ac_no")==null?" ":rs.getString("ref_ac_no"));
					gl_object[row].setAcAbbr(rs.getString("ac_abbr")==null?" ":rs.getString("ac_abbr"));
					gl_object[row].setName(rs.getString("name")==null?" ":rs.getString("name"));
					gl_object[row].setTrnMode(rs.getString("trn_mode")==null?" ":rs.getString("trn_mode"));
					gl_object[row].setCdInd(rs.getString("cd_ind"));
					gl_object[row].setTrnAmt(rs.getDouble("trn_amt"));
					gl_object[row].setTrnDesc(rs.getString("trn_desc")==null?" ":rs.getString("trn_desc"));
					gl_object[row].setTrnSeq(rs.getString("ref_tr_seq")==null?" ":rs.getString("ref_tr_seq"));
					gl_object[row].setClosingBalance(rs.getDouble("close_bal"));
					gl_object[row].setOpeningBalance(rs.getDouble("open_bal"));
					gl_object[row].setNormalCD(rs.getString("normal_cd")==null?" ":rs.getString("normal_cd"));

					row++;
				}
			}
		}catch(SQLException sql){sql.printStackTrace();} 
		finally{
			try{
				stmt.executeBatch();
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
		return gl_object;
	}
	public String getStartingDate()
	{
	    Connection conn=null;
	    Statement stmt=null;
	    ResultSet rs=null;
	    //int year;
	    //int month;
	    String result=null;
	    try
	    {
	        conn=getConnection();
	        stmt=conn.createStatement();
	        rs=stmt.executeQuery("select trn_dt from DailyStatus order by trn_dt limit 1");
	        if(rs.next())
	        	result=rs.getString("trn_dt");
	        else
	        	result="0";
		}catch(SQLException sql){sql.printStackTrace();} 
		finally{
			try{
				stmt.executeBatch();
				conn.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
		return result;
	}
	/**
	 * shubha
	 * @return
	 */
	
	public String startingDate()
	{
	    Connection conn=null;
	    Statement stmt=null;
	    ResultSet rs=null;
	    int year;
	    int month;
	    String result=null;
	    try
	    {
	        conn=getConnection();
	        stmt=conn.createStatement();
	        rs=stmt.executeQuery("select year(trn_dt),month(trn_dt) from DailyStatus order by trn_dt limit 1");
	        if(rs.next())
	        {
	            year=rs.getInt(1);
	            month=rs.getInt(2);
	            if(month==1)
	            {
	                year--;
	                month=12;
	            }
	            else
	                month--;
	            
	            if(String.valueOf(month).length()>1)
	               result=String.valueOf(year)+""+String.valueOf(month);
	            else
	                result=String.valueOf(year)+"0"+String.valueOf(month);
	        }
	        else
	            result="0";
	    }
	    catch(SQLException sql){
			sql.printStackTrace();
			//throw new SQLException();
		}
	    finally{
			try{
			conn.close();
			}catch(SQLException sql){
				sql.printStackTrace();
				//throw new SQLException();
			}
		}
	return result;	
	}
	public String startingDateForBranch(int br_code)
	{
	    Connection conn=null;
	    Statement stmt=null;
	    ResultSet rs=null;
	    int year;
	    int month;
	    String result=null;
	    try
	    {
	        conn=getConnection();
	        stmt=conn.createStatement();
	        rs=stmt.executeQuery("select year(trn_dt),month(trn_dt) from DailyConStatus where br_code="+br_code+"  order by trn_dt limit 1");
	        if(rs.next())
	        {
	            year=rs.getInt(1);
	            month=rs.getInt(2);
	            if(month==1)
	            {
	                year--;
	                month=12;
	            }
	            else
	                month--;
	            
	            if(String.valueOf(month).length()>1)
	               result=String.valueOf(year)+""+String.valueOf(month);
	            else
	                result=String.valueOf(year)+"0"+String.valueOf(month);
	        }
	        else
	            result="0";
	    }
	    catch(SQLException sql){
			sql.printStackTrace();
		}
	    finally{
			try{
			conn.close();
			}catch(SQLException sql){
				sql.printStackTrace();
			}
		}
	return result;	
	}
	/**
	 * shubha
	 * @param year_mnth
	 * @param globject
	 * @param tml
	 * @param user
	 * @param date_time
	 * @return
	 */
	public int storeMonthlySummary(String year_mnth,GLObject globject[],String tml,String user,String date_time)
	{
	    Connection conn=null;
	    Statement stmt_datetime=null,stmt=null;
	    ResultSet rs_datetime=null;
	    int i=0,j=0,ret=0;
	    PreparedStatement[] pstmt =new PreparedStatement[globject.length];
	    try{
	        conn=getConnection();
	        stmt_datetime=conn.createStatement();
	        stmt=conn.createStatement();
	        System.out.println("date and time1111===="+date_time);
	        stmt.executeUpdate("delete from MonthlySummary where yr_mth="+year_mnth+" and record_type='o' ");
	        rs_datetime=stmt_datetime.executeQuery("select concat(mid('"+date_time+"',locate('/','"+date_time+"')+4,(locate(' ','"+date_time+"')-locate('/','"+date_time+"',4)-1)),'-',mid('"+date_time+"',locate('/','"+date_time+"')+1,(locate('/','"+date_time+"',4)-locate('/','"+date_time+"')-1)),'-',left('"+date_time+"',locate('/','"+date_time+"')-1),' ',right('"+date_time+"',locate(' ','"+date_time+"')-3)) as datetime");
	        rs_datetime.next();
	        for(i=0;i<globject.length;i++)
	        {
	            pstmt[i]=conn.prepareStatement("insert into MonthlySummary values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	            pstmt[i].setString(1,year_mnth);
	            pstmt[i].setString(2,"O");
	            pstmt[i].setString(3,globject[i].getGlType());
	            pstmt[i].setString(4,globject[i].getGlCode());
	            pstmt[i].setDouble(5,globject[i].getCash_dr());
	            pstmt[i].setDouble(6,globject[i].getCg_dr());
	            pstmt[i].setDouble(7,globject[i].getTrf_dr());
	            pstmt[i].setDouble(8,globject[i].getCash_cr());
	            pstmt[i].setDouble(9,globject[i].getCg_cr());
	            pstmt[i].setDouble(10,globject[i].getTrf_cr());
	            pstmt[i].setString(11,tml);
	            pstmt[i].setString(12,user);
	            pstmt[i].setString(13,rs_datetime.getString(1));
	            pstmt[i].setString(14,null);
	            pstmt[i].setString(15,null);
	            pstmt[i].setString(16,null);
	            
	        }
	        for(j=0;j<pstmt.length;j++)
	        {
	            if(pstmt[j].executeUpdate()==0)
	            {
	                ret=0;
	                throw new SQLException("Montly Summary insertion problem");
	            }
	            else
	                ret=1;
	        }
	    }
	    catch(SQLException exception)
		{
			exception.printStackTrace();
			sessioncontext.setRollbackOnly();
		}
	    catch(Exception e){e.printStackTrace();}
	    finally{
			try{
			conn.close();
			}catch(SQLException sql){
				sql.printStackTrace();
				//throw new SQLException();
			}
		}
	    return ret;
	}
	/**
	 * shubha
	 * @return
	 */
	public GLObject[] getMonthlySummary()
	{
	    Connection conn=null;
	    ResultSet rs_mnthsum=null,rs=null;
	    Statement stmt_mnthsum=null,stmt=null;
	    GLObject gl_object[]=null;
	    int num_rows=0,i=0;
	    String year=null,mth=null,date=null;
	    try{
	        conn=getConnection();
	        stmt_mnthsum=conn.createStatement();
	        stmt=conn.createStatement();
	        rs=stmt.executeQuery("select distinct yr_mth from MonthlySummary where record_type='o' ");
	        if(rs.next()){
	        	year=rs.getString("yr_mth").trim().substring(0,4);
	        	mth=rs.getString("yr_mth").trim().substring(4,6);
	        	date="01"+"/"+mth+"/"+year;
	        	date=Validations.lastDayOfMonth(date);
	        	date=Validations.convertYMD(date);
	        }
	        rs_mnthsum=stmt_mnthsum.executeQuery("select moduleabbr,gm.gl_name,ms.*from Modules,MonthlySummary ms left join GLMaster gm on gm.gl_code=ms.gl_code where record_type='O' and ms.gl_type=modulecode and '"+date+"' between gm.from_date and gm.to_date" );
	        if(rs_mnthsum.next())
	        {
	           if(rs_mnthsum.getString("ve_tml")!=null && rs_mnthsum.getString("ve_user")!=null)
	           {
	               rs_mnthsum.last();
	               gl_object=new GLObject[rs_mnthsum.getRow()];
	               rs_mnthsum.beforeFirst();
	               while(rs_mnthsum.next())
	               {
	                   gl_object[i]=new GLObject();
	                   gl_object[i].setGLTypeAbbr(rs_mnthsum.getString("moduleabbr"));
	                   gl_object[i].setGlType(rs_mnthsum.getString("gl_type"));
	                   gl_object[i].setGlCode(rs_mnthsum.getString("gl_code"));
	                   gl_object[i].setGlName(rs_mnthsum.getString("gl_name"));
	                   gl_object[i].setTrn_date(rs_mnthsum.getString("yr_mth"));
	                   gl_object[i].setCash_dr(rs_mnthsum.getDouble("cash_dr"));
		               gl_object[i].setCg_dr(rs_mnthsum.getDouble("cg_dr"));
		               gl_object[i].setTrf_dr(rs_mnthsum.getDouble("trf_dr"));
		               gl_object[i].setCash_cr(rs_mnthsum.getDouble("cash_cr"));
		               gl_object[i].setCg_cr(rs_mnthsum.getDouble("cg_cr"));
		               gl_object[i].setTrf_cr(rs_mnthsum.getDouble("trf_cr"));
		               gl_object[i].setDeTml(rs_mnthsum.getString("de_tml"));
		               gl_object[i].setDeUser(rs_mnthsum.getString("de_user"));
		               gl_object[i].setDeDate(rs_mnthsum.getString("de_date_time"));
		               i++;
	               }
	              
	           }
	           else if(rs_mnthsum.getString("ve_tml")==null && rs_mnthsum.getString("ve_user")==null)
	           {
	              rs_mnthsum.last();
	              num_rows=rs_mnthsum.getRow();
	              rs_mnthsum.beforeFirst();
	              gl_object=new GLObject[num_rows];
	              while(rs_mnthsum.next())
	              {
	                  gl_object[i]=new GLObject();
	                  gl_object[i].setGLTypeAbbr(rs_mnthsum.getString("moduleabbr"));
	                  gl_object[i].setGlType(rs_mnthsum.getString("gl_type"));
	                  gl_object[i].setGlCode(rs_mnthsum.getString("gl_code"));
	                  gl_object[i].setGlName(rs_mnthsum.getString("gl_name"));
	                  gl_object[i].setTrn_date(rs_mnthsum.getString("yr_mth"));
	                  gl_object[i].setCash_dr(rs_mnthsum.getDouble("cash_dr"));
	                  gl_object[i].setCg_dr(rs_mnthsum.getDouble("cg_dr"));
	                  gl_object[i].setTrf_dr(rs_mnthsum.getDouble("trf_dr"));
	                  gl_object[i].setCash_cr(rs_mnthsum.getDouble("cash_cr"));
	                  gl_object[i].setCg_cr(rs_mnthsum.getDouble("cg_cr"));
	                  gl_object[i].setTrf_cr(rs_mnthsum.getDouble("trf_cr"));
	                  gl_object[i].setDeTml(rs_mnthsum.getString("ve_tml"));
		              gl_object[i].setDeUser(rs_mnthsum.getString("ve_user"));
		              gl_object[i].setDeDate(rs_mnthsum.getString("ve_date_time"));
		               
	                  i++;
	              }	            	              
	           }              
	           
	        }
	        else
	        {
	            gl_object=new GLObject[1];
	            gl_object[0]=new GLObject();
	            gl_object[0].setGlCode("1");
	        }
	      }
	    catch(Exception e){e.printStackTrace();}
	    finally{
			try{
			conn.close();
			}catch(SQLException sql){
				sql.printStackTrace();
				//throw new SQLException();
			}
		}
	    return gl_object;
	}
	
	public int verifyMonthlySummary(String yearmonth,String ve_tml,String ve_user,String ve_date_time)
	{
	 Connection conn=null;
	 Statement stmt=null;
	 Statement stmt_datetime=null;
	 ResultSet rs_datetime=null;
	 String date_time=null;
	 int result=0,ret=0;
	 try
	 {
	    conn=getConnection(); 
	    stmt=conn.createStatement();
	    stmt_datetime=conn.createStatement();
	    rs_datetime=stmt_datetime.executeQuery("select concat(mid('"+ve_date_time+"',locate('/','"+ve_date_time+"')+4,(locate(' ','"+ve_date_time+"')-locate('/','"+ve_date_time+"',4)-1)),'-',mid('"+ve_date_time+"',locate('/','"+ve_date_time+"')+1,(locate('/','"+ve_date_time+"',4)-locate('/','"+ve_date_time+"')-1)),'-',left('"+ve_date_time+"',locate('/','"+ve_date_time+"')-1),' ',right('"+ve_date_time+"',locate(' ','"+ve_date_time+"')-3)) as datetime");
        rs_datetime.next();
        date_time=rs_datetime.getString("datetime");
	    result=stmt.executeUpdate("update MonthlySummary set ve_tml='"+ve_tml+"',ve_user='"+ve_user+"',ve_date_time='"+date_time+"' where yr_mth="+yearmonth+" and record_type='O' and ve_user is null");
	    if(result==0)
	    {
	        ret=0;
	        throw new SQLException();
	    }
	    else
	        ret=1;
	  
	 }
	 catch(SQLException se)
	 {
	     se.printStackTrace();
	     sessioncontext.setRollbackOnly();
	  }
	 catch(Exception e){e.printStackTrace();}
	 finally
	 {
	     try{
	         conn.close();
	     }catch(SQLException se){
	         se.printStackTrace();
	     }
	 }
	 return ret;
	}
	public int  getModuleCode(String table_name){
	     Connection conn=null;
		 Statement stmt=null;
		 ResultSet rs=null;
		 int modcode=0;
		 try
		 {
		    conn=getConnection(); 
		    stmt=conn.createStatement();
		    rs=stmt.executeQuery("select * from "+table_name+" where modulecode like '1012%' and modulecode not like'%000' order by  modulecode desc limit 1");
		    if(rs.next()){
		        modcode=rs.getInt("modulecode");
		        modcode++;
		        return modcode;
		    }
		 }catch(Exception e){e.printStackTrace();}
		    finally{
				try{
				conn.close();
				}catch(SQLException sql){
					sql.printStackTrace();
				}
			}
		    return modcode;
		}
	public String[] getKeyParam(String table_name){
	     Connection conn=null;
		 Statement stmt=null;
		 ResultSet rs=null;
		 int code=0;
		 String ret[]=new String[3];
		 try
		 {
		    conn=getConnection(); 
		    stmt=conn.createStatement();
		    rs=stmt.executeQuery("select m.moduleabbr,gl.* from GLKeyParam gl,Modules m where ac_type like '1012%' and m.modulecode=gl.ac_type order by code desc limit 1;");
		    if(rs.next()){
		        code=rs.getInt("code");
		        code++;
		        ret[0]=rs.getString(1);
		        ret[1]=rs.getString("ac_type");
		        ret[2]=String.valueOf(code);
		        return ret;
		    }
		    else{
		        rs=stmt.executeQuery("select moduleabbr,modulecode from Modules where modulecode like '1012%' and modulecode like'____000' ");
		        ret[0]=rs.getString(1);
		        ret[1]=rs.getString(2);
		        ret[2]="1";
		        return ret;
		    }
	    }catch(Exception e){e.printStackTrace();}
	    finally{
			try{
			conn.close();
			}catch(SQLException sql){
				sql.printStackTrace();
			}
		}
	    return ret;
	}
	public String[][] getForm9InputCodes(String date){
		 Connection conn=null;
		 Statement stmt=null;
		 ResultSet rs=null;
		 int i=0;
		 String ret[][]=null;
		 try
		 {
		    conn=getConnection(); 
		    stmt=conn.createStatement();
		    rs=stmt.executeQuery("select code,name from RBIForm9Master where code not in(Select distinct code from RBIForm9Link where '"+Validations.convertYMD(date)+"' between fromdate and todate) and '"+Validations.convertYMD(date)+"' between fromdate and todate and form9link is null");
		    if(rs!=null){
		    	rs.last();
		    	ret=new String[rs.getRow()][2];
		    	rs.beforeFirst();
		    	while(rs.next()){
		    		ret[i][0]=rs.getString(1);
		    		ret[i][1]=rs.getString(2);
		    		i++;
		    	}
		    }
		    }catch(Exception e){e.printStackTrace();}
		    finally{
				try{
				conn.close();
				}catch(SQLException sql){
					sql.printStackTrace();
				}
			}
		    return ret;
	}
	public String[][] getForm9LinkCodes(String date){
		 Connection conn=null;
		 Statement stmt=null;
		 ResultSet rs=null;
		 int i=0;
		 String ret[][]=null;
		 try
		 {
		    conn=getConnection(); 
		    stmt=conn.createStatement();
		    rs=stmt.executeQuery("select code,name from RBIForm9Master where form9link is not null and '"+Validations.convertYMD(date)+"'  between fromdate and todate");
		    if(rs!=null){
		    	rs.last();
		    	ret=new String[rs.getRow()][2];
		    	rs.beforeFirst();
		    	while(rs.next()){
		    		ret[i][0]=rs.getString(1);
		    		ret[i][1]=rs.getString(2);
		    		i++;
		    	}
		    }
		    }catch(Exception e){e.printStackTrace();}
		    finally{
				try{
				conn.close();
				}catch(SQLException sql){
					sql.printStackTrace();
				}
			}
		    return ret;
	}
}
