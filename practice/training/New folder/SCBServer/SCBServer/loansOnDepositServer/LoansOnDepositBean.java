package loansOnDepositServer;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.ejb.CreateException;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;

import masterObject.frontCounter.AccountTransObject;
import masterObject.general.Address;
import masterObject.loansOnDeposit.LoanMasterObject;
import masterObject.loansOnDeposit.LoanPurposeObject;
import masterObject.loansOnDeposit.LoanReportObject;
import masterObject.loansOnDeposit.LoanTransactionObject;
import masterObject.termDeposit.DepositMasterObject;
import pygmyServer.PygmyLocalHome;
import pygmyServer.PygmyLocalRemote;
import termDepositServer.TermDepositLocalHome;
import termDepositServer.TermDepositLocalRemote;

import commonServer.CommonLocal;
import commonServer.CommonLocalHome;
import commonServer.GLTransObject;

import exceptions.DateFormatException;
import exceptions.RecordNotInsertedException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;
import frontCounterServer.FrontCounterLocal;
import frontCounterServer.FrontCounterLocalHome;
import general.Validations;

public class LoansOnDepositBean implements javax.ejb.SessionBean
{
    //private String uid;
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CommonLocalHome commonlocalhome;
    CommonLocal commonlocal;
    
    TermDepositLocalHome tdlocalhome;
    TermDepositLocalRemote tdlocal;
    
    FrontCounterLocalHome fclocalhome;
    FrontCounterLocal fclocal;
    PygmyLocalHome pygmyhome;
    PygmyLocalRemote pygmyremote;
    javax.sql.DataSource ds=null;
    
    SessionContext context=null;
    
    public LoansOnDepositBean()	
    {
        
    }
    
    public void ejbCreate()
    {
         
        try{	
            Context ctx=new InitialContext();			
            ds=(javax.sql.DataSource)ctx.lookup("java:MySqlDS");
        }catch(NamingException ex){ex.printStackTrace();}
        
        try 
        {
            Context jndiContext = new InitialContext();//new InitialContext();
            commonlocalhome =(CommonLocalHome)jndiContext.lookup("COMMONLOCALWEB");
            commonlocal = commonlocalhome.create();
            
            tdlocalhome =(TermDepositLocalHome)jndiContext.lookup("TERMDEPOSITLOCALWEB");
            tdlocal = tdlocalhome.create();
            pygmyhome=(PygmyLocalHome)jndiContext.lookup("PYGMYLOCALWEB");
            pygmyremote=pygmyhome.create();
            fclocalhome =(FrontCounterLocalHome)jndiContext.lookup("FRONTCOUNTERLOCALWEB");
            fclocal = fclocalhome.create();
            
        }catch(CreateException re){
            re.printStackTrace();
         }
        catch(NamingException re){
            re.printStackTrace();
         }
    }
    
    public LoanReportObject[] getAccruedInterest(String currentdate,String ac_type) throws  SQLException
    {
        LoanReportObject lnobj[]=null;
        Connection con=null;
        int rs_no=0,res_no=0;
        
        try{
            con=getConnection();
            Statement stmt=con.createStatement();	
            Statement stmt1=con.createStatement();
            ResultSet rs=stmt.executeQuery("select lm.ac_type,lm.ac_no,lm.sanc_date,lm.sanc_amt,concat(IFNULL(fname,''),' ',IFNULL(mname,''),' ',IFNULL(lname,'')) as name,dm.ac_type,dm.ac_no,lm.int_upto_date from DepositMaster dm,LoanMaster lm,CustomerMaster where lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid  and dm.ln_availed='T' and lm.close_date is null  and lm.ac_type='"+ac_type+"' and concat(right(lm.int_upto_date,4),'-',mid(lm.int_upto_date,locate('/',lm.int_upto_date)+1,(locate('/',lm.int_upto_date,4)-locate('/',lm.int_upto_date)-1)),'-',left(lm.int_upto_date,locate('/',lm.int_upto_date)-1))<='"+Validations.convertYMD(currentdate)+"' and lm.ve_tml is not null and lm.ve_user is not null order by lm.ac_type,lm.ac_no" );
            ResultSet res=stmt1.executeQuery("select lm.ac_type,lm.ac_no,lm.sanc_date,lm.sanc_amt,concat(IFNULL(fname,''),' ',IFNULL(mname,''),' ',IFNULL(lname,'')) as name,pm.ac_type,pm.ac_no,lm.int_upto_date from PygmyMaster pm,LoanMaster lm,CustomerMaster where lm.td_ac_type=pm.ac_type and lm.td_ac_no=pm.ac_no and pm.cid=CustomerMaster.cid  and pm.ln_avld='T' and lm.close_date is null  and lm.ac_type='"+ac_type+"' and concat(right(lm.int_upto_date,4),'-',mid(lm.int_upto_date,locate('/',lm.int_upto_date)+1,(locate('/',lm.int_upto_date,4)-locate('/',lm.int_upto_date)-1)),'-',left(lm.int_upto_date,locate('/',lm.int_upto_date)-1))<='"+Validations.convertYMD(currentdate)+"' and lm.ve_tml is not null and lm.ve_user is not null order by lm.ac_type,lm.ac_no");
            if(rs.next())
            {
                rs.last();
                rs_no=rs.getRow();
                rs.beforeFirst();
            }
            if(res.next())
            {
                res.last();
                res_no=res.getRow();
                res.beforeFirst();
            }
            
            lnobj=new LoanReportObject[rs_no+res_no];
            
            int i=0;
            
            while(rs.next())
            {
                lnobj[i]=new LoanReportObject();
                lnobj[i].setAccType(rs.getString("lm.ac_type"));
                lnobj[i].setAccNo(rs.getInt("lm.ac_no"));
                lnobj[i].setSanctionedAmount(rs.getDouble("lm.sanc_amt"));
                lnobj[i].setSanctionDate(rs.getString("lm.sanc_date"));
                lnobj[i].setDepositAccType(rs.getString("dm.ac_type"));
                lnobj[i].setDepositAccNo(rs.getInt("dm.ac_no"));
                lnobj[i].setName(rs.getString("name"));
                lnobj[i].setIntUptoDate(rs.getString("lm.int_upto_date"));
                System.out.println(lnobj[i].getAccType()+"   "+lnobj[i].getAccNo());		
                
                Statement cpstmt=con.createStatement();
                ResultSet rs1=cpstmt.executeQuery("select * from LoanTransaction where ac_type='"+lnobj[i].getAccType()+"' and ac_no="+lnobj[i].getAccNo()+" order by trn_seq");
                double intpaid=0,ln_bal=0,extraint=0;
                
                while(rs1.next())
                {
                    intpaid=intpaid+rs1.getDouble("int_amt")+rs1.getDouble("other_amt");
                    System.out.println("interest paid1"+intpaid);
                    extraint=rs1.getDouble("extra_int");
                    ln_bal=rs1.getDouble("pr_bal");		
                }
                rs1.close();
                
                lnobj[i].setLoanBalance(ln_bal);
                lnobj[i].setInterestPaid(intpaid);
                lnobj[i].setExtraIntPaid(extraint);
                System.out.println("int upto date"+lnobj[i].getIntUptoDate());
                System.out.println("now"+currentdate);
                System.out.println("previous paid"+intpaid);
                System.out.println("interest Due"+getInterestDue(lnobj[i].getIntUptoDate(),currentdate,lnobj[i].getAccType(),lnobj[i].getAccNo(),lnobj[i].getDepositAccType(),lnobj[i].getDepositAccNo()));
                //lnobj.setInterestPayable((Validations.dayCompare(lnobj.getIntUptoDate(),Validations.addDays(getSysDate(),-1))*getCurrentIntRate(lnobj.getDepositAccType(),lnobj.getDepositAccNo())*lnobj.getLoanBalance())/(365*100));
                //lnobj[i].setInterestAccrued(intpaid+getInterestDue(lnobj[i].getIntUptoDate(),currentdate,lnobj[i].getAccType(),lnobj[i].getAccNo(),lnobj[i].getDepositAccType(),lnobj[i].getDepositAccNo()));
                //lnobj[i].setInterestAccrued(intpaid+(Validations.dayCompare(lnobj[i].getIntUptoDate(), Validations.addDays(getSysDate(),-1))*getCurrentIntRate(lnobj[i].getDepositAccType(),lnobj[i].getDepositAccNo())*lnobj[i].getLoanBalance())/(365*100));
                lnobj[i].setInterestAccrued(getCurrentIntPay(lnobj[i].getAccType(),lnobj[i].getAccNo(),currentdate));
                System.out.println("interestac"+lnobj[i].getInterestAccrued());
                i++;
            }
            
            while(res.next())
            {
                lnobj[i]=new LoanReportObject();
                lnobj[i].setAccType(res.getString("lm.ac_type"));
                lnobj[i].setAccNo(res.getInt("lm.ac_no"));
                lnobj[i].setSanctionedAmount(res.getDouble("lm.sanc_amt"));
                lnobj[i].setSanctionDate(res.getString("lm.sanc_date"));
                lnobj[i].setDepositAccType(res.getString("pm.ac_type"));
                lnobj[i].setDepositAccNo(res.getInt("pm.ac_no"));
                lnobj[i].setName(res.getString("name"));
                lnobj[i].setIntUptoDate(res.getString("lm.int_upto_date"));
                System.out.println(lnobj[i].getAccType()+"   "+lnobj[i].getAccNo());		
                
                Statement cpstmt=con.createStatement();
                ResultSet rs1=cpstmt.executeQuery("select * from LoanTransaction where ac_type='"+lnobj[i].getAccType()+"' and ac_no="+lnobj[i].getAccNo()+" order by trn_seq");
                double intpaid=0,ln_bal=0;
                
                while(rs1.next())
                {
                    intpaid=intpaid+rs1.getDouble("int_amt")+rs1.getDouble("other_amt");
                    System.out.println("int paid"+intpaid);
                    ln_bal=rs1.getDouble("pr_bal");		
                    
                }
                rs1.close();
                
                lnobj[i].setLoanBalance(ln_bal);
                lnobj[i].setInterestPaid(intpaid);
                lnobj[i].setInterestAccrued(intpaid+getInterestDue(lnobj[i].getIntUptoDate(),currentdate,lnobj[i].getAccType(),lnobj[i].getAccNo(),lnobj[i].getDepositAccType(),lnobj[i].getDepositAccNo()));
                i++;
            }
            
        }catch(Exception e){e.printStackTrace();}
        finally{
			try
			{
				con.close();	
			}catch(SQLException sql){
				throw new SQLException("Error occured while closing a connction !");
			}
        }
        return lnobj; 
    }
    
    public long getInterestDue(String date,String current,String lntype,int lnno,String deptype,int depno) throws  SQLException
    {
        long int_accrued=0;
        Connection con=null;
        try{
            con=getConnection();	
            System.out.println(lntype+"  "+lnno);	
            
            Statement cpstmt=con.createStatement();
            ResultSet rs1=cpstmt.executeQuery("select * from LoanTransaction lt where lt.ac_type='"+lntype+"' and lt.ac_no="+lnno+" and concat(right(lt.trn_date,4),'-',mid(lt.trn_date,locate('/',lt.trn_date)+1,(locate('/',lt.trn_date,4)-locate('/',lt.trn_date)-1)),'-',left(lt.trn_date,locate('/',lt.trn_date)-1))>'"+Validations.convertYMD(date)+"' order by trn_seq");
            double intpaid=0,ln_bal=0,pr_bal=0;
            
            String prev="";
            
            while(rs1.next())
            {
                intpaid=intpaid+rs1.getDouble("int_amt")+rs1.getDouble("other_amt");
                System.out.println("Interest Accrued int paid"+"  "+intpaid);
                ln_bal=rs1.getDouble("pr_bal");
                System.out.println("Interest Accrued loan balance"+"  "+ln_bal);
                if(rs1.getRow()==1)
                {
                	prev=rs1.getString("trn_date");
                    prev=Validations.addDays(prev,-1);
                    pr_bal=rs1.getDouble("pr_bal");		
                    current=Validations.addDays(getSysDate(),-1);
                }
                if(rs1.getRow()!=1)
                	{	
                    	if(rs1.getString("trn_type").equals("D"))
                    	{
                        current=Validations.addDays(rs1.getString("trn_date"),-1);
                    		//current=rs1.getString("int_date");
                        System.out.println("Interest Accrued"+"  "+int_accrued+prev+"  "+current);
                        int_accrued=int_accrued+Math.round((Validations.dayCompare(prev,current)*pr_bal*getCurrentIntRate(deptype,depno))/(36500));						
                        prev=current;
                        pr_bal=rs1.getDouble("pr_bal");					
                        current=Validations.addDays(getSysDate(),-1);
                        System.out.println("prev date"+prev); 
                    }
                    	//added by puspa
                    	else{
                    		prev=rs1.getString("int_date");
                    	   current=Validations.addDays(getSysDate(),-1);
                    		}
                	}
            }
            System.out.println("prev date"+prev);
            
            System.out.println("totaldays"+Validations.dayCompare(prev,current));
            System.out.println("Interest Accrued balance"+"  "+ln_bal +"CURRENT DATE= "+current);
            int_accrued=int_accrued+Math.round((Validations.dayCompare(prev,current)*pr_bal*getCurrentIntRate(deptype,depno))/(36500));
            
        }catch(Exception e){e.printStackTrace();}
        
        finally
        {
            con.close();
        }
        return int_accrued;
    }
    public int getUpdateData(LoanTransactionObject lnobj,String acc_type,int ac_no,double pramt,double intamt,double otheramt )
    {
    	Connection con=null;
    	
    	
    	try{
    		con=getConnection();
    		Statement stmt=con.createStatement();
    		
    		PreparedStatement pstmt;
    		double trn_amt=0,intpaid=0,ln_bal=0,pr_amt=0,other_amt=0 ,last_prbal=0,current_bal=0;
    		int int_date=0,tran_seq=0;
    		double int_amt=0,extraint=0,extra_amt=0;
    		double intrate=0;
    		String intupto=null;
    		pr_amt=pramt;
    		other_amt=otheramt;
    		int_amt=intamt;
    		
    		ResultSet res=stmt.executeQuery("select * from LoanMaster where ac_type='"+lnobj.getAccType()+"' and ac_no="+lnobj.getAccNo()+"");
    		if(res.next())
    		{
    		    intrate=res.getDouble("int_rate");
    		    intupto=res.getString("int_upto_date");
    		    //intupto=intupto+1;
    		    tran_seq=res.getInt("lst_tr_seq");
    		
    		    last_prbal=getCurrentPrBal(lnobj.getAccType(),lnobj.getAccNo(),lnobj.getTransactionDate());
    		    System.out.println("lastprincipal"+last_prbal);
    		     current_bal=pr_amt+last_prbal;
    		   System.out.println("current bal"+current_bal);
    		   System.out.println("interest Rate"+intrate);
    		   double perday=(current_bal*intrate)/(365*100);
               
               int a=(int)Math.floor(int_amt/perday);
               intpaid=Math.round(perday*a);//current Interest
               extraint=int_amt-intpaid;
               //intupto=Validations.addDays(res.getString("int_upto_date"),-a);
               System.out.println("date is"+intupto);
               System.out.println("value of a is"+a);
               
    		    trn_amt=lnobj.getTransactionAmount();
                pstmt = con.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

 				pstmt.setString(1,lnobj.getAccType());
 				pstmt.setInt(2,lnobj.getAccNo());
 				pstmt.setInt(3,lnobj.getTranSequence()+1);
 				pstmt.setString(4,lnobj.getTransactionDate());
 				pstmt.setString(5,lnobj.getTranType());
 				pstmt.setDouble(6,-trn_amt);
 				pstmt.setString(7,lnobj.getTranMode());
 				pstmt.setString(8,lnobj.getTranSou());
 				pstmt.setInt(9,lnobj.getReferenceNo());
 				pstmt.setString(10,lnobj.getTranNarration());
 				pstmt.setString(11,null);
 				pstmt.setString(12,"D");
 				pstmt.setString(13,Validations.addDays(intupto,-a));
 				pstmt.setDouble(14,-pr_amt);
 				pstmt.setDouble(15,-(int_amt));
 				pstmt.setDouble(16,0);
 				pstmt.setDouble(17,other_amt*(-1));
 				pstmt.setDouble(18,extraint);
 				pstmt.setDouble(19,current_bal);
 				pstmt.setString(20,lnobj.uv.getUserTml());
 				pstmt.setString(21,lnobj.uv.getUserId());
 				pstmt.setString(22,lnobj.uv.getUserDate());
 				pstmt.setString(23,lnobj.uv.getVerTml());
 				pstmt.setString(24,lnobj.uv.getVerId());
 				pstmt.setString(25,lnobj.uv.getVerDate());
 				
 				int update=pstmt.executeUpdate();
 				if(update>0){
                int loan_master= stmt.executeUpdate("update LoanMaster set lst_tr_seq="+(tran_seq+1)+",lst_trn_date='"+getSysDate()+"',int_upto_date='"+Validations.addDays(intupto,-a)+"'  where ac_type='"+lnobj.getAccType()+"' and ac_no="+lnobj.getAccNo()+"");
                if(loan_master>0)
                {
                     GLTransObject trnobj=new GLTransObject();
                     Statement stmt1=con.createStatement();
                     ResultSet rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type="+lnobj.getAccType()+" and gk.gl_code=gp.gl_code and trn_type='D' and cr_dr='C' and code=1");

                     if(rs.next())
                     {
                     	//Statement stmt_lst_trn_seq=conn.createStatement();
                     	//ResultSet rs_lst_trn_seq=stmt_lst_trn_seq.executeQuery("Select lst_tr_seq  from LoanMaster where ac_type='"+ln_object.getAccType()+"' and ac_no="+ln_object.getAccNo()+"  ");
                     	
                     	 trnobj.setTrnDate(getSysDate());
                         trnobj.setGLType(rs.getString("gk.gl_type"));
                         trnobj.setGLCode(rs.getString("gp.gl_code"));				
                        // trnobj.setTrnMode(lnobj.getPayMode());
                          trnobj.setAmount((pr_amt+int_amt)*rs.getInt("mult_by"));
                          trnobj.setCdind("D");
                          trnobj.setTrnType("R");
                          trnobj.setTrnDate(getSysDate());
                          trnobj.setTrnMode(lnobj.getTranMode());
                          if(lnobj.getTranMode().equalsIgnoreCase("P")) {
      	                	trnobj.setTrnMode("T");
                          }
                          trnobj.setAccType(lnobj.getAccType());
                          trnobj.setAccNo(String.valueOf(lnobj.getAccNo()));
                          trnobj.setVtml(lnobj.uv.getVerTml());
                          trnobj.setVid(lnobj.uv.getVerId());
                          trnobj.setVDate(getSysDateTime());
                          commonlocal.storeGLTransaction(trnobj);
                     }
                 if(pr_amt>0)
                 {
                     Statement cpstmt=con.createStatement();
                     ResultSet rs1=cpstmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type="+acc_type+" and gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='D' and code=1");
                     if(rs1.next())
                     {	
                     	 trnobj.setGLType(rs1.getString("gk.gl_type"));
                         trnobj.setGLCode(rs1.getString("gp.gl_code"));				
                         trnobj.setAmount(pr_amt*rs1.getInt("mult_by"));
                         trnobj.setCdind("C");
                         trnobj.setTrnType("R");
                         //storeGLTransaction(trnobj);
                         commonlocal.storeGLTransaction(trnobj);
                     }
                 }
                 
                 if(intpaid>0)
                 {
                     Statement cpstmt=con.createStatement();
                     ResultSet rs1=cpstmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type="+acc_type+" and gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='D' and code=2");
                     if(rs1.next())
                     {	
                     	 trnobj.setGLType(rs1.getString("gk.gl_type"));
                         trnobj.setGLCode(rs1.getString("gp.gl_code"));				
                         trnobj.setAmount((intpaid)*rs1.getInt("mult_by"));
                         trnobj.setCdind("C");
                         trnobj.setTrnType("R");
                         //storeGLTransaction(trnobj);
                         commonlocal.storeGLTransaction(trnobj);
                     }
                 }	
    		
 				} 
    		
    		  }
    		  }
    		  
    		
    	}catch(Exception e){e.printStackTrace();}
    	 finally
	        {try{
	        	
	             con.close();
	        }catch(Exception ex){}
	        }
    return 0;
    }
    public int  getUpdateData(LoanTransactionObject lnobj,String trnmode,String date)
    {
    	Connection con= null;
    	Statement stmt=null;
    	PreparedStatement pstmt=null;
    	ResultSet rs=null;
    	double prn_amt=0,trn_amt=0,int_amt=0,extra_int=0,pr_bal=0;
    	double int_rate=0,current_prbal=0,int_paid=0;
    	String int_upto=null;
    	int trn_seq=0;
    	try{
    		con=getConnection();
    		stmt=con.createStatement();
    		prn_amt=lnobj.getPrincipalPaid();
    		trn_amt=lnobj.getTransactionAmount();
    		int_amt=lnobj.getInterestPaid();
    		extra_int=lnobj.getExtraIntPaid();
    		pr_bal=getCurrentPrBal(lnobj.getAccType(),lnobj.getAccNo(),lnobj.getTransactionDate());
    		rs=stmt.executeQuery("select * from LoanMaster where ac_type="+lnobj.getAccType()+" and ac_no="+lnobj.getAccNo()+"");
    		
    		if(rs.next())
    		{
    			int_rate=rs.getDouble("int_rate");
    			int_upto=rs.getString("int_upto_date");
    			trn_seq=rs.getInt("lst_tr_seq");
    			current_prbal=pr_bal+prn_amt;
    			System.out.println("last pr bal"+pr_bal);
    			System.out.println("current bal"+current_prbal);
    			double perday=(current_prbal*int_rate)/36500;
    			int a=(int)Math.round(int_amt/perday);
    			int_paid=Math.round(perday*a);
    			extra_int=int_amt-int_paid;
    			
    			 pstmt = con.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

  				pstmt.setString(1,lnobj.getAccType());
  				pstmt.setInt(2,lnobj.getAccNo());
  				pstmt.setInt(3,lnobj.getTranSequence()+1);
  				pstmt.setString(4,lnobj.getTransactionDate());
  				pstmt.setString(5,lnobj.getTranType());
  				pstmt.setDouble(6,-trn_amt);
  				pstmt.setString(7,lnobj.getTranMode());
  				pstmt.setString(8,lnobj.getTranSou());
  				pstmt.setInt(9,lnobj.getReferenceNo());
  				pstmt.setString(10,lnobj.getTranNarration());
  				pstmt.setString(11,null);
  				pstmt.setString(12,"D");
  				pstmt.setString(13,Validations.addDays(int_upto,-a));
  				pstmt.setDouble(14,-prn_amt);
  				pstmt.setDouble(15,-(int_amt));
  				pstmt.setDouble(16,0);
  				pstmt.setDouble(17,0);
  				pstmt.setDouble(18,extra_int);
  				pstmt.setDouble(19,current_prbal);
  				pstmt.setString(20,lnobj.uv.getUserTml());
  				pstmt.setString(21,lnobj.uv.getUserId());
  				pstmt.setString(22,lnobj.uv.getUserDate());
  				pstmt.setString(23,lnobj.uv.getVerTml());
  				pstmt.setString(24,lnobj.uv.getVerId());
  				pstmt.setString(25,lnobj.uv.getVerDate());
  				
  				int update=pstmt.executeUpdate();
  				if(update>0){
                 int loan_master= stmt.executeUpdate("update LoanMaster set lst_tr_seq="+(trn_seq+1)+",lst_trn_date='"+getSysDate()+"',int_upto_date='"+Validations.addDays(int_upto,-a)+"'  where ac_type='"+lnobj.getAccType()+"' and ac_no="+lnobj.getAccNo()+"");
                 if(loan_master>0)
                 {
                      GLTransObject trnobj=new GLTransObject();
                      Statement stmt1=con.createStatement();
                      ResultSet res=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type="+lnobj.getAccType()+" and gk.gl_code=gp.gl_code and trn_type='D' and cr_dr='C' and code=1");

                      if(rs.next())
                      {
                      	//Statement stmt_lst_trn_seq=conn.createStatement();
                      	//ResultSet rs_lst_trn_seq=stmt_lst_trn_seq.executeQuery("Select lst_tr_seq  from LoanMaster where ac_type='"+ln_object.getAccType()+"' and ac_no="+ln_object.getAccNo()+"  ");
                      	
                      	trnobj.setTrnDate(getSysDate());
                          trnobj.setGLType(rs.getString("gk.gl_type"));
                          trnobj.setGLCode(rs.getString("gp.gl_code"));				
                         // trnobj.setTrnMode(lnobj.getPayMode());
                          trnobj.setAmount((prn_amt+int_amt)*rs.getInt("mult_by"));
                          trnobj.setCdind("D");
                           trnobj.setTrnType("R");
                           trnobj.setTrnDate(getSysDate());
                           trnobj.setTrnMode(lnobj.getTranMode());
                           if(lnobj.getTranMode().equalsIgnoreCase("P")) {
         	                	trnobj.setTrnMode("T");
                             }
                           trnobj.setAccType(lnobj.getAccType());
                           trnobj.setAccNo(String.valueOf(lnobj.getAccNo()));
                           trnobj.setVtml(lnobj.uv.getVerTml());
                           trnobj.setVid(lnobj.uv.getVerId());
                           trnobj.setVDate(getSysDateTime());
                           commonlocal.storeGLTransaction(trnobj);
                      }
                      if(prn_amt>0)
                      {
                      Statement cpstmt=con.createStatement();
                      ResultSet rs1=cpstmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+lnobj.getAccType()+"' and gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='D' and code=1");
                      if(rs1.next())
                      {	
                      	trnobj.setGLType(rs1.getString("gk.gl_type"));
                          trnobj.setGLCode(rs1.getString("gp.gl_code"));				
                          trnobj.setAmount(prn_amt*rs1.getInt("mult_by"));
                          trnobj.setCdind("C");
                          trnobj.setTrnType("R");
                          //storeGLTransaction(trnobj);
                          commonlocal.storeGLTransaction(trnobj);
                      }
                  }
                  
                  if(int_paid>0)
                  {
                      Statement cpstmt=con.createStatement();
                      ResultSet rs1=cpstmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+lnobj.getAccType()+"' and gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='D' and code=2");
                      if(rs1.next())
                      {	
                      	trnobj.setGLType(rs1.getString("gk.gl_type"));
                          trnobj.setGLCode(rs1.getString("gp.gl_code"));				
                          trnobj.setAmount((int_paid)*rs1.getInt("mult_by"));
                          trnobj.setCdind("C");
                          trnobj.setTrnType("R");
                          //storeGLTransaction(trnobj);
                          commonlocal.storeGLTransaction(trnobj);
                      }
                  }	
     		
  				} 
     		
     		  }
     		  }		
    			
    	    		    
    	    
    		
    	}catch(Exception e){}
    	 finally
	        {try{
	        	
	             con.close();
	        }catch(Exception ex){}
	        }
    	
    	return 0;
    }
    
    
    String getModuleAbbr(String modcode) throws SQLException
    {
        Connection con=null;
        try{
            con=getConnection();
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select moduleabbr from Modules where modulecode="+modcode);
            if(rs.next())
                return(rs.getString(1));
            
            
        }catch(Exception ex){System.out.println("Exception while getting module abbrv"+ex);}
        
        finally
        {
            con.close();
        }
        return "";
    }
    
    
    String getSysDate()
    {
        Calendar c=Calendar.getInstance();
        String s=c.get(Calendar.DATE)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);
        try {
            return(Validations.checkDate(s));
        } catch (DateFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    
    public LoanReportObject[] getLDMaturityList(String date1, String date2,String ac_type,int type) throws SQLException
    {
        LoanReportObject lnobj[]=null;
        Connection con=null;
        ResultSet rs=null;
        try{
            con=getConnection();
            Statement stmt=con.createStatement();
            Statement stmt1=con.createStatement();
            if(type==1)
            	rs=stmt.executeQuery("select lm.ac_type,lm.ac_no,lm.int_upto_date,lt.pr_bal,concat(IFNULL(fname,' '),' ',IFNULL(mname,' '),' ',IFNULL(lname,' ')) as name,dm.ac_type,dm.ac_no,dm.dep_date,dm.mat_date,dm.dep_days,dm.int_rate,dm.dep_amt,dm.mat_amt,dm.int_upto_date,dm.int_mode,dm.trf_ac_type,dm.trf_ac_no,lt.trn_narr  from DepositMaster dm,LoanMaster lm,CustomerMaster,LoanTransaction lt where lm.ac_type=lt.ac_type and lm.ac_no=lt.ac_no and lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid  and dm.ln_availed='T' and lm.close_date is null and concat(right(dm.mat_date,4),'-',mid(dm.mat_date,locate('/',dm.mat_date)+1,(locate('/',dm.mat_date,4)-locate('/',dm.mat_date)-1)),'-',left(dm.mat_date,locate('/',dm.mat_date)-1)) between '" +Validations.convertYMD(date1)+"'  and '" +Validations.convertYMD(date2)+"'  order by lt.trn_seq desc ");
            else
            	rs=stmt.executeQuery("select lm.ac_type,lm.ac_no,lm.int_upto_date,concat(IFNULL(fname,' '),' ',IFNULL(mname,' '),' ',IFNULL(lname,' ')) as name,dm.ac_type,dm.ac_no,dm.dep_date,dm.mat_date,dm.dep_days,dm.int_rate,dm.dep_amt,dm.mat_amt,dm.int_upto_date,dm.int_mode,dm.trf_ac_type,dm.trf_ac_no,dm.close_ind  from DepositMaster dm,LoanMaster lm,CustomerMaster where lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid  and dm.ln_availed='T' and lm.close_date is null and lm.ac_type='"+ac_type+"'and concat(right(dm.mat_date,4),'-',mid(dm.mat_date,locate('/',dm.mat_date)+1,(locate('/',dm.mat_date,4)-locate('/',dm.mat_date)-1)),'-',left(dm.mat_date,locate('/',dm.mat_date)-1)) between '" +Validations.convertYMD(date1)+"'  and '" +Validations.convertYMD(date2)+"' order by lm.ac_type,lm.ac_no");
            
            rs.last();
            System.out.println("Rs===>"+rs);
            System.out.println("Rs===>"+rs.getRow());
            lnobj=new LoanReportObject[rs.getRow()];
            rs.beforeFirst();
            int i=0;
            
            while(rs.next())
            {
                lnobj[i]=new LoanReportObject();
                lnobj[i].setDepositAccType(getModuleAbbr(rs.getString("dm.ac_type")));
                lnobj[i].setDepositAccNo(rs.getInt("dm.ac_no"));
                
                String name=rs.getString("name");
                if(name.length()>0 && name!=null)
                	lnobj[i].setName(name);
                else lnobj[i].setName(null);
                lnobj[i].setDepositAmt(rs.getDouble("dm.dep_amt"));
                lnobj[i].setMaturityAmt(rs.getDouble("dm.mat_amt"));
                lnobj[i].setMaturityDate(rs.getString("dm.mat_date"));
                lnobj[i].setDepDate(rs.getString("dm.dep_date"));
                lnobj[i].setDepositDays(rs.getInt("dm.dep_days"));
                lnobj[i].setTranMode(rs.getString("dm.int_upto_date"));
                String mode=rs.getString("dm.int_mode");
                System.out.println("mode In Bean========>====>"+mode+"No"+rs.getInt("dm.ac_no"));
                
                if(mode.equals("C"))
                    lnobj[i].setTranNarration("Cash");
                else if(mode.equals("T"))
                    lnobj[i].setTranNarration(rs.getString("dm.trf_ac_type")+" "+rs.getInt("dm.trf_ac_no"));
                else
                	lnobj[i].setTranNarration(null);                
               
                
                lnobj[i].setAccType(rs.getString("lm.ac_type"));
                lnobj[i].setAccNo(rs.getInt("lm.ac_no"));
                lnobj[i].setIntUptoDate(rs.getString("lm.int_upto_date"));
                lnobj[i].setLoanIntRate(getCurrentIntRate(rs.getString("dm.ac_type"),rs.getInt("dm.ac_no")));
                ResultSet rs1=stmt1.executeQuery("select * from LoanTransaction where ac_type='"+rs.getString("lm.ac_type")+"' and ac_no="+rs.getInt("lm.ac_no")+" order by trn_seq desc limit 1");
				if(rs1.next()){
					lnobj[i].setLoanBalance(rs1.getDouble("pr_bal"));
				}
               // lnobj[i].setInterestPayable(getInterestDue(lnobj[i].getIntUptoDate(),Validations.addDays(getSysDate(),-1),lnobj[i].getAccType(),lnobj[i].getAccNo(),lnobj[i].getDepositAccType(),lnobj[i].getDepositAccNo()));
				 lnobj[i].setInterestPayable(getCurrentIntPay(rs.getString("lm.ac_type"),rs.getInt("lm.ac_no"),date2));
                i++;
                
            }
        }catch(Exception e){e.printStackTrace();}
        
        finally
        {
            con.close();
        }
        return lnobj;
    }
    
    /*
     * Interestpayable+loanbalance greater than Deposit amount
     */
    public LoanReportObject[] getLDExceedingMaturity(String date1, String date2,String ac_type,int type) throws SQLException
    {
        System.out.println("date1 == "+date1);
        System.out.println("date2 == "+date2);
        LoanReportObject lnobj[]=null;
        Connection con=null;
        ResultSet rs=null;
        try{ 
            con=getConnection();
            Statement stmt=con.createStatement();
            Statement stmt1=con.createStatement();
            /*if(type==1)
             rs=stmt.executeQuery("select lm.ac_no,lm.ac_type,lm.int_upto_date,dm.mat_amt,lt.pr_bal,concat(IFNULL(fname,' '),' ',IFNULL(mname,' '),' ',IFNULL(lname,' ')) as name,dm.ac_type,dm.ac_no,dm.dep_date,dm.mat_date,dm.dep_days,dm.extra_rate_type,dm.int_rate,dm.dep_amt,dm.mat_amt,dm.int_upto_date,dm.ac_type  from DepositMaster dm,LoanMaster lm,CustomerMaster,LoanTransaction lt where lm.ac_type=lt.ac_type and lm.ac_no=lt.ac_no and lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid  and dm.ln_availed='T' and lm.close_date is null and  lt.trn_seq=lm.lst_tr_seq");
            else
             rs=stmt.executeQuery("select lm.ac_type,lm.ac_no,lm.int_upto_date,concat(IFNULL(fname,' '),' ',IFNULL(mname,' '),' ',IFNULL(lname,' ')) as name,dm.ac_type,dm.ac_no,dm.dep_date,dm.mat_date,dm.dep_days,dm.int_rate,dm.dep_amt,dm.mat_amt,dm.int_upto_date,dm.int_mode,dm.trf_ac_type,dm.trf_ac_no,dm.close_ind  from DepositMaster dm,LoanMaster lm,CustomerMaster where lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid  and dm.ln_availed='T' and lm.close_date is null and lm.ac_type='"+ac_type+"'and concat(right(dm.mat_date,4),'-',mid(dm.mat_date,locate('/',dm.mat_date)+1,(locate('/',dm.mat_date,4)-locate('/',dm.mat_date)-1)),'-',left(dm.mat_date,locate('/',dm.mat_date)-1)) between '" +Validations.convertYMD(date1)+"'  and '" +Validations.convertYMD(date2)+"' order by lm.ac_type,lm.ac_no");*/
            if(type==1)
            	rs=stmt.executeQuery("select lm.ac_type,lm.ac_no,lm.int_upto_date,lt.pr_bal,concat(IFNULL(fname,' '),' ',IFNULL(mname,' '),' ',IFNULL(lname,' ')) as name,dm.ac_type,dm.ac_no,dm.dep_date,dm.mat_date,dm.dep_days,dm.int_rate,dm.dep_amt,dm.mat_amt,dm.int_upto_date,dm.int_mode,dm.trf_ac_type,dm.trf_ac_no,lt.trn_narr  from DepositMaster dm,LoanMaster lm,CustomerMaster,LoanTransaction lt where lm.ac_type=lt.ac_type and lm.ac_no=lt.ac_no and lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid  and dm.ln_availed='T' and lm.close_date is null and concat(right(dm.mat_date,4),'-',mid(dm.mat_date,locate('/',dm.mat_date)+1,(locate('/',dm.mat_date,4)-locate('/',dm.mat_date)-1)),'-',left(dm.mat_date,locate('/',dm.mat_date)-1)) between '" +Validations.convertYMD(date1)+"'  and '" +Validations.convertYMD(date2)+"'  order by lt.trn_seq desc ");
            else
            	rs=stmt.executeQuery("select lm.ac_type,lm.ac_no,lm.int_upto_date,concat(IFNULL(fname,' '),' ',IFNULL(mname,' '),' ',IFNULL(lname,' ')) as name,dm.ac_type,dm.ac_no,dm.dep_date,dm.mat_date,dm.dep_days,dm.int_rate,dm.dep_amt,dm.mat_amt,dm.int_upto_date,dm.int_mode,dm.trf_ac_type,dm.trf_ac_no,dm.close_ind  from DepositMaster dm,LoanMaster lm,CustomerMaster where lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid  and dm.ln_availed='T' and lm.close_date is null and lm.ac_type='"+ac_type+"'and concat(right(dm.mat_date,4),'-',mid(dm.mat_date,locate('/',dm.mat_date)+1,(locate('/',dm.mat_date,4)-locate('/',dm.mat_date)-1)),'-',left(dm.mat_date,locate('/',dm.mat_date)-1)) between '" +Validations.convertYMD(date1)+"'  and '" +Validations.convertYMD(date2)+"' order by lm.ac_type,lm.ac_no");
            
            int count = 0;
            while (  rs.next() )  {
            	
            	if((getCurrentPrBal(rs.getString("lm.ac_type"),rs.getInt("lm.ac_no"),date2)+getCurrentIntPay(rs.getString("lm.ac_type"),rs.getInt("lm.ac_no"),date2))>rs.getDouble("dm.mat_amt")){
            		
            		count++;
            	}
            }
            
            
            //rs.last();
            
            //lnobj=new LoanReportObject[rs.getRow()];
            lnobj=new LoanReportObject[count];
            rs.beforeFirst();
            int i=0;
            
            while(rs.next())
            {
                
                System.out.println("Processing"+rs.getInt("lm.ac_no"));
                if((getCurrentPrBal(rs.getString("lm.ac_type"),rs.getInt("lm.ac_no"),date2)+getCurrentIntPay(rs.getString("lm.ac_type"),rs.getInt("lm.ac_no"),date2))>rs.getDouble("dm.mat_amt"))
                {
                lnobj[i]=new LoanReportObject();
              // do{
                lnobj[i].setDepositAccType(rs.getString("dm.ac_type"));
                lnobj[i].setDepositAccNo(rs.getInt("dm.ac_no"));
                lnobj[i].setName(rs.getString("name"));
                lnobj[i].setDepositAmt(rs.getDouble("dm.dep_amt"));
                lnobj[i].setMaturityDate(rs.getString("dm.mat_date"));
                lnobj[i].setDepDate(rs.getString("dm.dep_date"));
                lnobj[i].setDepositDays(rs.getInt("dm.dep_days"));
                lnobj[i].setMaturityAmt(rs.getDouble("dm.mat_amt"));		
                
                lnobj[i].setAccType(rs.getString("lm.ac_type"));
                lnobj[i].setAccNo(rs.getInt("lm.ac_no"));
                lnobj[i].setIntUptoDate(rs.getString("lm.int_upto_date"));
                lnobj[i].setLoanIntRate(lnobj[i].getDepositIntRate()+getCurrentIntRate(lnobj[i].getDepositAccType(),lnobj[i].getDepositAccNo()));
                lnobj[i].setLoanBalance(getCurrentPrBal(rs.getString("lm.ac_type"),rs.getInt("lm.ac_no"),date2));
                
                /*ResultSet rs1=stmt1.executeQuery("select * from LoanTransaction where ac_type='"+rs.getString("lm.ac_type")+"' and ac_no="+rs.getInt("lm.ac_no")+" order by trn_seq desc limit 1");
				if(rs1.next()){
					lnobj[i].setLoanBalance(rs1.getDouble("pr_bal"));
				}*/
               // lnobj[i].setInterestPayable(getInterestDue(lnobj[i].getIntUptoDate(),Validations.addDays(getSysDate(),-1),lnobj[i].getAccType(),lnobj[i].getAccNo(),lnobj[i].getDepositAccType(),lnobj[i].getDepositAccNo()));
				 lnobj[i].setInterestPayable(getCurrentIntPay(rs.getString("lm.ac_type"),rs.getInt("lm.ac_no"),date2));
				 i++;
               
                }
              
            }
            
        }catch(Exception e){e.printStackTrace();}
        
        finally
        {
            con.close();
        }
        return lnobj;
    }
    
    public double getCurrentIntRate(String actype,int acno) throws SQLException
    {
        ResultSet rs=null,rs1=null;
        Connection con=null;
        double int_rate=0,extra_int_rate=0,interest_rate=0;
        int dep_days=0,sub_category=0;
        
        try{
            con=getConnection();
            Statement stnew=con.createStatement();
            System.out.println("Type"+actype);
            System.out.println("No"+acno);
            // Code added by Sanjeet..
            rs=stnew.executeQuery("select ac_no,int_rate,dep_days,sub_category from DepositMaster dm,CustomerMaster cm where dm.ac_type='"+actype+"' and dm.ac_no="+acno+" and dm.cid=cm.cid ");
            if(rs.next()){
            	int_rate=rs.getDouble("int_rate");
            	dep_days=rs.getInt("dep_days");
            	sub_category=rs.getInt("sub_category");
            }
            rs.beforeFirst();
            System.out.println("int_rate==>"+int_rate);
            Statement stmt=con.createStatement();
            rs1=stmt.executeQuery("select extra_lnint_rate from DepositCategoryRate  where ac_type='"+actype+"' and category="+sub_category+" and "+dep_days+" between days_fr and days_to");
            if(rs1.next()){
            	extra_int_rate=rs1.getDouble("extra_lnint_rate");
            }
            rs1.beforeFirst();
            
            interest_rate=extra_int_rate+int_rate;
            System.out.println("Interest Rate==>"+interest_rate);
            return(interest_rate);
            
           // rs=stnew.executeQuery("select (extra_lnint_rate+int_rate) from DepositMaster,DepositCategoryRate,CustomerMaster where  DepositCategoryRate.ac_type='"+actype+"' and category=sub_category and CustomerMaster.cid=DepositMaster.cid and  DepositMaster.ac_type='"+actype+"' and DepositMaster.ac_no="+acno +" and  DepositMaster.dep_days between days_fr and days_to");
            //if(rs.next())
              //  return(rs.getDouble(1));
            
        }catch(Exception e){JOptionPane.showMessageDialog(null,"LoansOnDepositImpl.java(GetCurrentIntRate)"+e);}
        
        finally
        {
            con.close();
        }
        return(0);
    }
    
    public double getCurrentIntRate(String actype,int acno,String date ) throws SQLException
    {
        ResultSet rs=null,rs1=null;
        Connection con=null;
        double int_rate=0,extra_int_rate=0,interest_rate=0;
        int dep_days=0,sub_category=0;
        
        try{
            con=getConnection();
            Statement stnew=con.createStatement();
            System.out.println("Type"+actype);
            System.out.println("No"+acno);
            // Code added by Sanjeet..
            rs=stnew.executeQuery("select ac_no,int_rate,dep_days,sub_category from DepositMaster dm,CustomerMaster cm where dm.ac_type='"+actype+"' and dm.ac_no="+acno+" and dm.cid=cm.cid ");
            if(rs.next()){
                int_rate=rs.getDouble("int_rate");
                dep_days=rs.getInt("dep_days");
                sub_category=rs.getInt("sub_category");
            }
            rs.beforeFirst();
            System.out.println("int_rate==>"+int_rate);
            Statement stmt=con.createStatement();
            rs1=stmt.executeQuery("select extra_lnint_rate from DepositCategoryRate  where ac_type='"+actype+"' and category="+sub_category+" and "+dep_days+" between days_fr and days_to and '"+Validations.convertYMD(date)+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
            if(rs1.next()){
                extra_int_rate=rs1.getDouble("extra_lnint_rate");
            }
            rs1.beforeFirst();
            
            interest_rate=extra_int_rate+int_rate;
            System.out.println("new Interest Rate==>"+interest_rate);
            System.out.println(" the date is " + date);
            return(interest_rate);
            
           // rs=stnew.executeQuery("select (extra_lnint_rate+int_rate) from DepositMaster,DepositCategoryRate,CustomerMaster where  DepositCategoryRate.ac_type='"+actype+"' and category=sub_category and CustomerMaster.cid=DepositMaster.cid and  DepositMaster.ac_type='"+actype+"' and DepositMaster.ac_no="+acno +" and  DepositMaster.dep_days between days_fr and days_to");
            //if(rs.next())
              //  return(rs.getDouble(1));
            
        }catch(Exception e){JOptionPane.showMessageDialog(null,"LoansOnDepositImpl.java(GetCurrentIntRate)"+e);}
        
        finally
        {
            con.close();
        }
        return(0);
    }
    
    public double getPygmyCurrentIntRate(String actype,String date,double amt_req,int ac_no) throws SQLException,RemoteException
    {
        ResultSet rs=null;
        Connection con=null;
        int purpose_code=0;
        System.out.println("Date = "+date);
        System.out.println("Ac Type = "+actype);
        System.out.println("Amt = "+amt_req);
        
        try{
            con=getConnection();
            Statement stmt=con.createStatement();
            rs=stmt.executeQuery("select pps_code  from LoanMaster where ac_type='"+actype+"' and ac_no="+ac_no+" ");
			if(rs.next())
				purpose_code=rs.getInt("pps_code");
			
            rs=stmt.executeQuery("Select rate from LoanIntRate where ln_type='"+actype+"' and '"+Validations.convertYMD(date)+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)) and "+amt_req+" between  min_bal and  max_bal  and pps_code="+purpose_code+" order by concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))  ");
            
            if(!rs.next())
				purpose_code=0;
            
            rs=stmt.executeQuery("Select rate from LoanIntRate where ln_type='"+actype+"' and '"+Validations.convertYMD(date)+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)) and "+amt_req+" between  min_bal and  max_bal  and pps_code="+purpose_code+" order by concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))  ");
			
            if(rs.next()){
                System.out.println("Rate= "+rs.getDouble("rate"));
                return(rs.getDouble("rate"));
            }
        }catch(SQLException e){e.printStackTrace();}
        
        finally
        {
            con.close();
        }
        return(0);
    }
    
    public LoanReportObject getLoanDetails(String actype,int acno) throws SQLException
    {
        System.out.println("----------------------------------- ");
        System.out.println("ac type= "+actype);
        System.out.println("ac no= "+acno);
        
        LoanReportObject lnobj=null;
        Connection con=null;
        ResultSet rs,res=null;
        String ac_type="",ac_name;
        
        double interest=0;
        
        try{
            con=getConnection();
            Statement stmt=con.createStatement();
            
            res=stmt.executeQuery("Select moduleabbr,td_ac_type from LoanMaster,Modules where ac_type='"+actype+"' and ac_no="+acno+" and ac_type=modulecode");
            if(res.next())
                ac_type=res.getString("td_ac_type");
            else
                return lnobj;
            ac_name = res.getString("moduleabbr");
            interest=getCurrentIntPay(actype, acno,getSysDate() );
           
            if(ac_type.substring(1,4).equals("003") || ac_type.substring(1,4).equals("004") || ac_type.substring(1,4).equals("005"))
            {
                rs=stmt.executeQuery("select ln.ac_type,ln.ac_no,concat(IFNULL(fname,' '),' ',IFNULL(mname,' '),' ',IFNULL(lname,' ')) as name,ln.ve_user,sanc_date,sanc_amt,dm.dep_days,td_ac_type,td_ac_no,dep_amt,mat_amt,dep_date,mat_date,pr_bal,int_amt,int_date,ln.close_date,extra_int,lp.pps_desc,dm.int_freq,dm.rct_no,dm.cid,ln.int_rate from LoanMaster ln,DepositMaster dm,CustomerMaster cm,LoanTransaction lt,LoanPurposes lp where cm.cid=dm.cid and lt.ac_type=ln.ac_type and lt.ac_no=ln.ac_no and  ln.td_ac_type=dm.ac_type and ln.ac_type='"+actype+"' and ln.td_ac_no=dm.ac_no and ln.ac_no="+acno+" and ln.pps_code=lp.pps_no and ln.ve_tml is not null order by trn_seq ");
                //double extra=0.0;
                System.out.println("interestamt"+interest);
               
               System.out.println("interest----"+interest);
                while(rs.next())
                {
                	
                    if(rs.getRow()==1)
                    {
                        lnobj=new LoanReportObject();
                        lnobj.setInterestPaid(0.0);  
                        lnobj.setTranMode(ac_name); 
                        System.out.println(lnobj.getDepositAccType()+" "+lnobj.getDepositAccNo());
                        lnobj.setDepositAccType(rs.getString("td_ac_type"));
                        lnobj.setDepositAccNo(rs.getInt("td_ac_no"));
                        lnobj.setSanctionDate(rs.getString("sanc_date"));
                        lnobj.setSanctionedAmount(rs.getDouble("sanc_amt"));
                        lnobj.setName(rs.getString("name"));
                        lnobj.setDepositAmt(rs.getDouble("dep_amt"));
                        lnobj.setMaturityAmt(rs.getDouble("mat_amt"));
                        lnobj.setMaturityDate(rs.getString("mat_date"));
                        lnobj.setDepDate(rs.getString("dep_date"));
                        lnobj.setDepositDays( rs.getInt("dep_days"));
                        lnobj.setCloseDate(rs.getString("ln.close_date" ));
                        System.out.println("First"+lnobj.getDepositAccType()+" "+lnobj.getDepositAccNo());
                    }
                    
                    lnobj.setIntUptoDate(rs.getString("int_date"));
                    lnobj.setLoanBalance(rs.getDouble("pr_bal"));
                    lnobj.setInterestPaid(Math.round(lnobj.getInterestPaid()+rs.getDouble("int_amt")));
                    System.out.println(lnobj.getDepositAccType()+" "+lnobj.getDepositAccNo());
                    
                    System.out.println("Interest rate"+getCurrentIntRate(lnobj.getDepositAccType(),lnobj.getDepositAccNo()));
                    System.out.println("Days === >"+Validations.dayCompare(lnobj.getIntUptoDate(),Validations.addDays(getSysDate(),-1)));
                     //lnobj.setInterestPayable((Validations.dayCompare(lnobj.getIntUptoDate(),Validations.addDays(getSysDate(),-1))*getCurrentIntRate(lnobj.getDepositAccType(),lnobj.getDepositAccNo())*lnobj.getLoanBalance())/(365*100));
                     lnobj.setInterestPayable(interest);
                    System.out.println("interest paid"+lnobj.getInterestPayable());
                    lnobj.setExtraIntPaid(rs.getDouble("extra_int"));
                    lnobj.setLoanPurposeDesc(rs.getString("lp.pps_desc"));
                    lnobj.setIntFreq(rs.getString("dm.int_freq"));
                    lnobj.setRctNo(rs.getInt("dm.rct_no"));
                    lnobj.setCID(rs.getInt("dm.cid"));
                    lnobj.setLoanIntRate(getCurrentIntRate(lnobj.getDepositAccType(),lnobj.getDepositAccNo()));
                    System.out.println("DIRECT ="+getCurrentIntRate(lnobj.getDepositAccType(),lnobj.getDepositAccNo()));
                    System.out.println("INDIRECT ="+lnobj.getLoanIntRate());	
                }
                
                
               
                
            }
            else if(ac_type.substring(1,4).equals("006"))
            {
                System.out.println("1..."); 
                rs=stmt.executeQuery("select distinct ln.ac_type,ln.ac_no,concat(IFNULL(fname,' '),' ',IFNULL(mname,' '),' ',IFNULL(lname,' ')) as name,ln.ve_user,sanc_date,sanc_amt,td_ac_type,td_ac_no,open_date,pr_bal,trn_seq,int_amt,int_date,ln.close_date,extra_int,pm.cid,ln.int_rate,lp.pps_desc from LoanMaster ln,PygmyMaster pm,CustomerMaster cm,LoanTransaction lt,LoanPurposes lp  where cm.cid=pm.cid and lt.ac_type=ln.ac_type and lt.ac_no=ln.ac_no and  ln.td_ac_type=pm.ac_type and ln.ac_type='"+actype+"' and ln.td_ac_no=pm.ac_no and ln.ac_no="+acno+" and ln.pps_code=lp.pps_no order by trn_seq ");
                System.out.println("2...");
                //double extra=0.0;
                
                while(rs.next())
                {
                    if(rs.getRow()==1)
                    {
                        System.out.println("3...");
                        lnobj=new LoanReportObject();
                        lnobj.setInterestPaid(0.0);
                        System.out.println("4...");
                        //System.out.println(lnobj.getDepositAccType()+" "+lnobj.getDepositAccNo());
                        lnobj.setDepositAccType(rs.getString("td_ac_type"));
                        lnobj.setDepositAccNo(rs.getInt("td_ac_no"));
                        System.out.println("5...");
                        lnobj.setSanctionDate(rs.getString("sanc_date"));
                        lnobj.setSanctionedAmount(rs.getDouble("sanc_amt"));
                        lnobj.setName(rs.getString("name"));
                        System.out.println("6...");
                        lnobj.setDepDate(rs.getString("open_date"));
                        lnobj.setCloseDate(rs.getString("ln.close_date" ));
                        System.out.println("First"+lnobj.getDepositAccType()+" "+lnobj.getDepositAccNo());
                    }
                                      
                    
                    lnobj.setIntUptoDate(rs.getString("int_date"));
                    lnobj.setLoanBalance(rs.getDouble("pr_bal"));
                    System.out.println("7...");
                    
                    lnobj.setInterestPaid(Math.round(lnobj.getInterestPaid()+rs.getDouble("int_amt")));
                    //System.out.println(lnobj.getDepositAccType()+" "+lnobj.getDepositAccNo());
                    System.out.println("8...");
                    //System.out.println("Day Compare = "+(Validations.dayCompare(lnobj.getIntUptoDate(),Validations.addDays(getSysDate(),-1))));
                    System.out.println("Day Compare = "+(Validations.dayCompare(lnobj.getIntUptoDate(),getSysDate())));
                    System.out.println("Interest Rate = "+getPygmyCurrentIntRate(actype,lnobj.getDepDate(),rs.getDouble("sanc_amt"),acno));
                    System.out.println("lnobj.getLoanBalance() = "+lnobj.getLoanBalance());
                    System.out.println("lnobj.getIntUptoDate() = "+lnobj.getIntUptoDate());
                    //System.out.println("Validations.addDays(getSysDate(),-1)) = "+Validations.addDays(getSysDate(),-1));
                    
                    lnobj.setInterestPayable(interest);
                    System.out.println("Interest payable"+lnobj.getInterestPayable());
                    lnobj.setExtraIntPaid(rs.getDouble("extra_int"));
                    System.out.println("extra_int"+lnobj.getExtraIntPaid());
                    lnobj.setCID(rs.getInt("pm.cid"));
                    lnobj.setLoanPurposeDesc(rs.getString("lp.pps_desc"));
                    lnobj.setLoanIntRate(getPygmyCurrentIntRate(actype,lnobj.getDepDate(),rs.getDouble("sanc_amt"),acno));
                    System.out.println("9...");
                    
                }
            }
            
        }catch(Exception e){e.printStackTrace();}	
        finally
        {
            con.close();
        }
        return lnobj;	
    }
    
    // overloaded function  getLoanDetails 
    
    public LoanReportObject getLoanDetails(String actype,String date,int acno) throws SQLException
    {
        System.out.println("----------------------------------- ");
        System.out.println("ac type= "+actype);
        System.out.println("ac no= "+acno);
        
        LoanReportObject lnobj=null;
        Connection con=null;
        ResultSet rs,res=null;
        String ac_type=null;
        String ac_name=null;
        double interest=0;
        
        try{
            con=getConnection();
            Statement stmt=con.createStatement();
            
            res=stmt.executeQuery("Select moduleabbr,td_ac_type from LoanMaster,Modules where ac_type='"+actype+"' and ac_no="+acno+" and ac_type=modulecode");
            if(res.next())
                ac_type=res.getString("td_ac_type");
            else
                return lnobj;
            ac_name = res.getString("moduleabbr");
            interest=getCurrentIntPay(actype, acno,getSysDate() );
           
            if(ac_type.substring(1,4).equals("003") || ac_type.substring(1,4).equals("004") || ac_type.substring(1,4).equals("005"))
            {
                rs=stmt.executeQuery("select ln.ac_type,ln.ac_no,concat(IFNULL(fname,' '),' ',IFNULL(mname,' '),' ',IFNULL(lname,' ')) as name,ln.ve_user,sanc_date,sanc_amt,dm.dep_days,td_ac_type,td_ac_no,dep_amt,mat_amt,dep_date,mat_date,pr_bal,int_amt,int_date,ln.close_date,extra_int,lp.pps_desc,dm.int_freq,dm.rct_no,dm.cid,ln.int_rate from LoanMaster ln,DepositMaster dm,CustomerMaster cm,LoanTransaction lt,LoanPurposes lp where cm.cid=dm.cid and lt.ac_type=ln.ac_type and lt.ac_no=ln.ac_no and  ln.td_ac_type=dm.ac_type and ln.ac_type='"+actype+"' and ln.td_ac_no=dm.ac_no and ln.ac_no="+acno+" and ln.pps_code=lp.pps_no and ln.ve_tml is not null order by trn_seq ");
                //double extra=0.0;
                System.out.println("interestamt"+interest);
               
               System.out.println("interest----"+interest);
                while(rs.next())
                {
                	
                    if(rs.getRow()==1)
                    {
                        lnobj=new LoanReportObject();
                        lnobj.setInterestPaid(0.0);  
                        lnobj.setTranMode(ac_name); 
                        System.out.println(lnobj.getDepositAccType()+" "+lnobj.getDepositAccNo());
                        lnobj.setDepositAccType(rs.getString("td_ac_type"));
                        lnobj.setDepositAccNo(rs.getInt("td_ac_no"));
                        lnobj.setSanctionDate(rs.getString("sanc_date"));
                        lnobj.setSanctionedAmount(rs.getDouble("sanc_amt"));
                        lnobj.setName(rs.getString("name"));
                        lnobj.setDepositAmt(rs.getDouble("dep_amt"));
                        lnobj.setMaturityAmt(rs.getDouble("mat_amt"));
                        lnobj.setMaturityDate(rs.getString("mat_date"));
                        lnobj.setDepDate(rs.getString("dep_date"));
                        lnobj.setDepositDays( rs.getInt("dep_days"));
                        lnobj.setCloseDate(rs.getString("ln.close_date" ));
                        System.out.println("First"+lnobj.getDepositAccType()+" "+lnobj.getDepositAccNo());
                    }
                    
                    lnobj.setIntUptoDate(rs.getString("int_date"));
                    lnobj.setLoanBalance(rs.getDouble("pr_bal"));
                    lnobj.setInterestPaid(Math.round(lnobj.getInterestPaid()+rs.getDouble("int_amt")));
                    System.out.println(lnobj.getDepositAccType()+" "+lnobj.getDepositAccNo());
                    
                    System.out.println("Interest rate"+getCurrentIntRate(lnobj.getDepositAccType(),lnobj.getDepositAccNo(),date));
                    System.out.println("Days === >"+Validations.dayCompare(lnobj.getIntUptoDate(),Validations.addDays(getSysDate(),-1)));
                     //lnobj.setInterestPayable((Validations.dayCompare(lnobj.getIntUptoDate(),Validations.addDays(getSysDate(),-1))*getCurrentIntRate(lnobj.getDepositAccType(),lnobj.getDepositAccNo())*lnobj.getLoanBalance())/(365*100));
                     lnobj.setInterestPayable(interest);
                    System.out.println("interest paid"+lnobj.getInterestPayable());
                    lnobj.setExtraIntPaid(rs.getDouble("extra_int"));
                    lnobj.setLoanPurposeDesc(rs.getString("lp.pps_desc"));
                    lnobj.setIntFreq(rs.getString("dm.int_freq"));
                    lnobj.setRctNo(rs.getInt("dm.rct_no"));
                    lnobj.setCID(rs.getInt("dm.cid"));
                    lnobj.setLoanIntRate(getCurrentIntRate(lnobj.getDepositAccType(),lnobj.getDepositAccNo(),date));
                    System.out.println("DIRECT ="+getCurrentIntRate(lnobj.getDepositAccType(),lnobj.getDepositAccNo(),date));
                    System.out.println("INDIRECT ="+lnobj.getLoanIntRate());	
                }
                
                
               
                
            }
            else if(ac_type.substring(1,4).equals("006"))
            {
                System.out.println("1..."); 
                rs=stmt.executeQuery("select distinct ln.ac_type,ln.ac_no,concat(IFNULL(fname,' '),' ',IFNULL(mname,' '),' ',IFNULL(lname,' ')) as name,ln.ve_user,sanc_date,sanc_amt,td_ac_type,td_ac_no,open_date,pr_bal,trn_seq,int_amt,int_date,ln.close_date,extra_int,pm.cid,ln.int_rate,lp.pps_desc from LoanMaster ln,PygmyMaster pm,CustomerMaster cm,LoanTransaction lt,LoanPurposes lp  where cm.cid=pm.cid and lt.ac_type=ln.ac_type and lt.ac_no=ln.ac_no and  ln.td_ac_type=pm.ac_type and ln.ac_type='"+actype+"' and ln.td_ac_no=pm.ac_no and ln.ac_no="+acno+" and ln.pps_code=lp.pps_no order by trn_seq ");
                System.out.println("2...");
                //double extra=0.0;
                
                while(rs.next())
                {
                    if(rs.getRow()==1)
                    {
                        System.out.println("3...");
                        lnobj=new LoanReportObject();
                        lnobj.setInterestPaid(0.0);
                        System.out.println("4...");
                        //System.out.println(lnobj.getDepositAccType()+" "+lnobj.getDepositAccNo());
                        lnobj.setDepositAccType(rs.getString("td_ac_type"));
                        lnobj.setDepositAccNo(rs.getInt("td_ac_no"));
                        System.out.println("5...");
                        lnobj.setSanctionDate(rs.getString("sanc_date"));
                        lnobj.setSanctionedAmount(rs.getDouble("sanc_amt"));
                        lnobj.setName(rs.getString("name"));
                        System.out.println("6...");
                        lnobj.setDepDate(rs.getString("open_date"));
                        lnobj.setCloseDate(rs.getString("ln.close_date" ));
                        System.out.println("First"+lnobj.getDepositAccType()+" "+lnobj.getDepositAccNo());
                    }
                                      
                    
                    lnobj.setIntUptoDate(rs.getString("int_date"));
                    lnobj.setLoanBalance(rs.getDouble("pr_bal"));
                    System.out.println("7...");
                    
                    lnobj.setInterestPaid(Math.round(lnobj.getInterestPaid()+rs.getDouble("int_amt")));
                    //System.out.println(lnobj.getDepositAccType()+" "+lnobj.getDepositAccNo());
                    System.out.println("8...");
                    //System.out.println("Day Compare = "+(Validations.dayCompare(lnobj.getIntUptoDate(),Validations.addDays(getSysDate(),-1))));
                    System.out.println("Day Compare = "+(Validations.dayCompare(lnobj.getIntUptoDate(),getSysDate())));
                    System.out.println("Interest Rate = "+getPygmyCurrentIntRate(actype,lnobj.getDepDate(),rs.getDouble("sanc_amt"),acno));
                    System.out.println("lnobj.getLoanBalance() = "+lnobj.getLoanBalance());
                    System.out.println("lnobj.getIntUptoDate() = "+lnobj.getIntUptoDate());
                    //System.out.println("Validations.addDays(getSysDate(),-1)) = "+Validations.addDays(getSysDate(),-1));
                    
                    lnobj.setInterestPayable(interest);
                    System.out.println("Interest payable"+lnobj.getInterestPayable());
                    lnobj.setExtraIntPaid(rs.getDouble("extra_int"));
                    System.out.println("extra_int"+lnobj.getExtraIntPaid());
                    lnobj.setCID(rs.getInt("pm.cid"));
                    lnobj.setLoanPurposeDesc(rs.getString("lp.pps_desc"));
                    lnobj.setLoanIntRate(getPygmyCurrentIntRate(actype,lnobj.getDepDate(),rs.getDouble("sanc_amt"),acno));
                    System.out.println("9...");
                    
                }
            }
            
        }catch(Exception e){e.printStackTrace();}	
        finally
        {
            con.close();
        }
        return lnobj;	
    }
    
 /*   public LoanReportObject getLoanDetails2(String actype,int acno)throws SQLException
    {
    	LoanReportObject lnobj=null;
    	Connection con=null;
    	Statement stmt=null;
    	ResultSet res=null,rs=null;
    	 String ac_type="",ac_name;
    	try{
    		con=getConnection();
    		stmt=con.createStatement();
    		res=stmt.executeQuery("Select moduleabbr,td_ac_type from LoanMaster,Modules where ac_type='"+actype+"' and ac_no="+acno+" and ac_type=modulecode");
            if(res.next())
                ac_type=res.getString("td_ac_type");
            else
                return lnobj;
            ac_name = res.getString("moduleabbr");
            if(ac_type.substring(1,4).equals("003") || ac_type.substring(1,4).equals("004") || ac_type.substring(1,4).equals("005"))
            {
                rs=stmt.executeQuery("select ln.ac_type,ln.ac_no,concat(IFNULL(fname,' '),' ',IFNULL(mname,' '),' ',IFNULL(lname,' ')) as name,ln.ve_user,sanc_date,sanc_amt,dm.dep_days,td_ac_type,td_ac_no,dep_amt,mat_amt,dep_date,mat_date,pr_bal,int_amt,int_date,ln.close_date,extra_int,lp.pps_desc,dm.int_freq,dm.rct_no,dm.cid,ln.int_rate from LoanMaster ln,DepositMaster dm,CustomerMaster cm,LoanTransaction lt,LoanPurposes lp where cm.cid=dm.cid and lt.ac_type=ln.ac_type and lt.ac_no=ln.ac_no and  ln.td_ac_type=dm.ac_type and ln.ac_type='"+actype+"' and ln.td_ac_no=dm.ac_no and ln.ac_no="+acno+" and ln.pps_code=lp.pps_no and ln.ve_tml is not null order by trn_seq ");
    		
            } 
    	}catch(Exception e){e.printStackTrace();}
    	return lnobj;
    }*/
    
    //Get Transaction details for loan recovery verification
    public LoanTransactionObject getLoanTransaction(int trnno) throws SQLException
    {
    	
        LoanTransactionObject lnobj=null;
        Connection con=null; 
        
        try{
            con=getConnection();	
            Statement stmt=con.createStatement();	
            ResultSet rs=stmt.executeQuery("select * from LoanTransaction where ref_no="+trnno+" and trn_mode='T' and  ve_tml is null and ve_user is null and ac_type like '1008%'");
            System.out.println(getSysDate());
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
                Statement cpstmt=con.createStatement();
                ResultSet rs1=cpstmt.executeQuery("select sum(pr_amt),sum(int_amt) from LoanTransaction where ac_type='"+lnobj.getAccType()+"' and ac_no="+lnobj.getAccNo());
                rs1.next();
                lnobj.setInterestPaid(rs1.getDouble("sum(int_amt)"));
                lnobj.setPrincipalPaid(rs1.getDouble("sum(pr_amt)"));
                
                
                lnobj.setIntUptoDate(rs.getString("int_date"));
                lnobj.setTransactionDate(rs.getString("trn_date"));
                lnobj.setInterestPayable(rs.getDouble("int_amt"));
                lnobj.setExtraIntPaid(rs.getDouble("extra_int"));
                lnobj.setRecoveryDate(rs.getString("rcy_date"));	
                
                System.out.println("lnobj===============>"+lnobj);
                 
            }
            
        }catch(Exception e){JOptionPane.showMessageDialog(null,"LoansOnDepositImpl.java(getLoanTransaction)"+e);}
        
        finally
        {
            con.close();
        }
        return lnobj;
    }
    /*
     *Will return full loan application details during verification from LoanTransaction table
     *its used During Verification of Additional Loans 
     */
    
    public Hashtable getLoanTransaction(String actype,int no) throws SQLException
    {
    	System.out.println("account type===========>"+actype);
    	System.out.println("no==============>"+no); 
    	LoanTransactionObject ltobj=null;
    	LoanMasterObject lnmasterobj;    
        Connection con=null;
        Hashtable trnmast_hashtable=new Hashtable(); 
        try{
            con=getConnection();
            Statement stmt=con.createStatement();	
            ResultSet rs=stmt.executeQuery("select lm.ac_type,lm.ac_no,lm.no_coborrowers,lm.no_surities,lm.cid,lm.addr_type,lm.appn_srl,lm.appn_date,lm.req_amt,lm.sh_no,lm.sh_type, lm.pps_code,lm.nom_reg_no,lm.td_ac_type,lm.td_ac_no,lm.int_type,lm.int_rate_type,lm.int_rate,lm.psect_cd,lm.wk_sect,lm.sex_cd,lm.rel,lm.dir_code,lm.conv_date,  lm.holday_mth , lm.sanc_date,lm.sanc_amt , lm.loan_sanc , lm.sanc_ver , lm.no_inst,  lm.inst_amt,  lm.int_upto_date,  lm.lst_trn_date,lm.lst_tr_seq,  lm.default_ind , lm.close_date,  lm.pay_mode,  lm.pay_ac_type , lm.pay_ac_no,  lm.remd_no , lm.remd_date , lm.disb_left,  lm.ldgprt_date,  lm.npa_date,  lm.npa_stg,lt.trn_amt,lt.pr_bal, lt.de_tml,lt.de_user,lt.de_date,lt.ve_tml,lt.ve_user,lt.ve_date  from LoanMaster lm,LoanTransaction lt where lm.ac_type=lt.ac_type and lm.ac_no=lt.ac_no and lm.ac_type='"+actype+"' and lm.ac_no="+no+" and lm.lst_tr_seq=lt.trn_seq");
            
            if(rs.next())
            {
            	ltobj=new LoanTransactionObject();
            	lnmasterobj=new LoanMasterObject();
            	ltobj.uv.setVerId(rs.getString("lt.ve_user"));
            	ltobj.uv.setUserId(rs.getString("lt.de_user"));
            	ltobj.uv.setVerTml(rs.getString("lt.ve_tml"));
            	ltobj.uv.setUserTml(rs.getString("lt.de_tml"));
            	ltobj.uv.setVerDate(rs.getString("lt.ve_date"));
            	ltobj.uv.setUserDate(rs.getString("lt.de_date"));
                
            	ltobj.setAccType(rs.getString("lm.ac_type"));
            	ltobj.setAccNo(rs.getInt("lm.ac_no"));
            	lnmasterobj.setNoofJtHldrs(rs.getInt("lm.no_coborrowers"));
                //		lnobj.setLoanCategory(rs.getString("loan_ctgry"));
                
            	lnmasterobj.setDepositAccType(rs.getString("lm.td_ac_type"));
            	lnmasterobj.setDepositAccNo(rs.getInt("lm.td_ac_no"));
                
            	lnmasterobj.setApplicationDate(rs.getString("lm.appn_date"));
            	lnmasterobj.setApplicationSrlNo(rs.getInt("lm.appn_srl"));
            	lnmasterobj.setAuto_loan(rs.getString("lm.conv_date"));
                
            	lnmasterobj.setPurposeCode(rs.getInt("lm.pps_code"));
            	lnmasterobj.setNoOfInstallments(rs.getInt("lm.no_inst"));
            	lnmasterobj.setInstallmentAmt(rs.getDouble("lm.inst_amt"));
                
            	lnmasterobj.setPayMode(rs.getString("lm.pay_mode"));
            	lnmasterobj.setPaymentAccno(rs.getInt("lm.pay_ac_no"));
            	lnmasterobj.setPaymentAcctype(rs.getString("lm.pay_ac_type"));
                
            	lnmasterobj.setSanctionDate(rs.getString("lm.sanc_date"));
            	lnmasterobj.setLoanSanctioned(rs.getBoolean("lm.loan_sanc"));
            	lnmasterobj.setSanctionVerified(rs.getBoolean("lm.sanc_ver"));
               // file_logger.info("Verified= "+rs.getBoolean("lm.sanc_ver"));
               // file_logger.info("Verified= "+lnmasterobj.isSanctionVerified());
                lnmasterobj.setSanctionedAmount(rs.getDouble("lm.sanc_amt"));
                lnmasterobj.setRequiredAmount(rs.getDouble("lm.req_amt"));
                lnmasterobj.setInterestRate(rs.getDouble("lm.int_rate"));
               // file_logger.info("lnobj.setInterestRate()="+lnmasterobj.getInterestRate());
               // file_logger.info("From RS= "+rs.getDouble("lm.int_rate"));
                lnmasterobj.setLdgPrntDate(rs.getString("lm.ldgprt_date"));
                lnmasterobj.setLastTrndt(rs.getString("lm.lst_trn_date"));
                lnmasterobj.setLastTrnSeq(rs.getInt("lm.lst_tr_seq"));
                
                lnmasterobj.setClosedt(rs.getString("lm.close_date"));
                lnmasterobj.setInterestUpto(rs.getString("lm.int_upto_date"));
                lnmasterobj.setPrBal(rs.getDouble("lt.pr_bal"));
                ltobj.setTransactionAmount(rs.getDouble("trn_amt"));
                System.out.println("ltobj.getTransactionAmount()====="+ltobj.getTransactionAmount()); 
                
                trnmast_hashtable.put("LoanTransaction",ltobj);
                trnmast_hashtable.put("LoanMasterObject",lnmasterobj);
            }
        }catch(Exception ex){JOptionPane.showMessageDialog(null,"LoansOnDepositImpl.java(getLoanMaster) : "+ex);}
        
        finally
        {
            con.close();
        }
        return trnmast_hashtable;
    }
    //Transaction verification after loan recovery
    public int verifyLoanTransaction(int trnno,String vid,String vtml,String clientdate,String clientdatetime) throws SQLException
    {
        Connection con=null;
        try{
            con=getConnection();
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select lm.td_ac_type,lm.td_ac_no,lt.* from LoanTransaction lt,LoanMaster lm  where  lm.ac_no=lt.ac_no and lt.ac_type=lm.ac_type and ref_no="+trnno+" and trn_mode='T' and trn_date='"+clientdate+"'");
            
            rs.next();
            
            GLTransObject trnobj=new GLTransObject();
            String trnnarr=rs.getString("trn_narr");
            double trnamt=rs.getDouble("trn_amt");
            double pramt=rs.getDouble("pr_amt");
            double intamt=rs.getDouble("int_amt");
            double prnbal=rs.getDouble("pr_bal");
            double extra_int=rs.getDouble("extra_int");
            double oth_amt=rs.getDouble("other_amt");
            String uid=rs.getString("lt.de_user");
            String utml=rs.getString("lt.de_tml");
            //puspa
            String int_upto_date=rs.getString("lt.int_date");
            
            trnobj.setTrnDate(clientdate);
            trnobj.setTrnMode(rs.getString("trn_mode"));
            if(rs.getString("trn_mode").equalsIgnoreCase("P")) {
              	trnobj.setTrnMode("T");
            }
            trnobj.setAccType(rs.getString("ac_type"));
            trnobj.setAccNo(rs.getString("ac_no"));
            trnobj.setVtml(vtml);
            trnobj.setVid(vid);
            trnobj.setVDate(clientdatetime);
            
            
            Statement stmt_trn_seq=con.createStatement();
            ResultSet rs_trn_seq=stmt_trn_seq.executeQuery("Select lst_tr_seq from LoanMaster where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+"  ");
            int trn_seq=0;
            if(rs_trn_seq!=null)
            {
            	if(rs_trn_seq.next())
            		trn_seq=rs_trn_seq.getInt("lst_tr_seq")+1;
            }
            
            trnobj.setTrnSeq(trn_seq);
            
            String ac_type=rs.getString("ac_type");
            
            if(pramt>0)
            {
                Statement cpstmt=con.createStatement();
                ResultSet rs1=cpstmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type="+ac_type+" and gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='C' and code=1");
                if(rs1.next())
                {	
                	trnobj.setGLType(rs1.getString("gk.gl_type"));
                    trnobj.setGLCode(rs1.getString("gp.gl_code"));				
                    trnobj.setAmount(pramt*rs1.getInt("mult_by"));
                    trnobj.setCdind("C");
                    trnobj.setTrnType("R");
                    //storeGLTransaction(trnobj);
                    commonlocal.storeGLTransaction(trnobj);
                }
            }
            
            if(intamt>0)
            {
                Statement cpstmt=con.createStatement();
                ResultSet rs1=cpstmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type="+ac_type+" and gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='C' and code=2");
                if(rs1.next())
                {	
                	trnobj.setGLType(rs1.getString("gk.gl_type"));
                    trnobj.setGLCode(rs1.getString("gp.gl_code"));				
                    trnobj.setAmount((intamt+oth_amt+extra_int)*rs1.getInt("mult_by"));
                    trnobj.setCdind("C");
                    trnobj.setTrnType("R");
                    //storeGLTransaction(trnobj);
                    commonlocal.storeGLTransaction(trnobj);
                }
            }	
            
            if(trnobj.getTrnMode().equals("T"))
            {
                StringTokenizer st=new StringTokenizer(trnnarr);
                
                AccountTransObject accounttransobject=new AccountTransObject();
                accounttransobject.setAccType(st.nextToken());
                accounttransobject.setAccNo(Integer.parseInt(st.nextToken()));
                accounttransobject.setTransDate(clientdate);
                accounttransobject.setTransAmount(trnamt);
                accounttransobject.setTransMode("T");
                accounttransobject.setTransType("P");
                accounttransobject.setCdInd("D");
                accounttransobject.setTransNarr(trnobj.getAccType()+" "+trnobj.getAccNo());
                accounttransobject.uv.setUserTml(utml);
                accounttransobject.uv.setUserId(uid);
                accounttransobject.uv.setVerTml(vtml);
                accounttransobject.uv.setVerId(vid);
                accounttransobject.uv.setVerDate(clientdatetime);
                commonlocal.storeAccountTransaction(accounttransobject);
            }
            
            if(prnbal>0)
                stmt.executeUpdate("update LoanMaster set lst_tr_seq=lst_tr_seq+1,lst_trn_date='"+clientdate+"',int_upto_date='"+int_upto_date+"'  where ac_type='"+trnobj.getAccType()+"' and ac_no="+trnobj.getAccNo());
            else if(prnbal==0)
                stmt.executeUpdate("update LoanMaster set close_date='"+clientdate+"', lst_tr_seq=lst_tr_seq+1,lst_trn_date='"+clientdate+"',int_upto_date='"+int_upto_date+"' where ac_type='"+trnobj.getAccType()+"' and ac_no="+trnobj.getAccNo());
            
            return(stmt.executeUpdate("update LoanTransaction set ve_tml='"+vid+"',ve_user='"+vtml+"',ve_date='"+clientdatetime+"' where ref_no="+trnno+" and trn_mode='T' and trn_date='"+clientdate+"' and ac_type like '1008%'"));
            
        }catch(SQLException e){
        	e.printStackTrace();
        	context.setRollbackOnly();
        }
        finally
        {
            con.close();
        }
        return(0);		
    }
    
    // This has been commented on 7/07/2006... 
    
    //inserts a row in GLTransaction table
   /* public int storeGLTransaction(GLTransObject trnobj) throws SQLException
    {
        Connection con=null;
        try{
            con=getConnection();
            PreparedStatement pstmt=con.prepareStatement("insert into GLTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,date_format(sysdate(),'%d/%m/%Y %r'))");
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
        }catch(SQLException e){
        	e.printStackTrace();
        	context.setRollbackOnly();
        }
        finally
        {
            con.close();
        }
        return 0;
    }*/
    
    /*
     *Loan recovery from savings bank account (Transfer)
     */
    
    public int loanRecovery(String lnactype,int lnacno,String actype,int acno,double amt,String uid,String utml)throws SQLException
    {
        Connection con=null;
        try{
            con=getConnection();
            Statement stmt=con.createStatement();
            int trnno=0;
            double intrate=0;
            //ResultSet rs=stmt.executeQuery("select lndep_trn_no from GenParam");
            // Code added by sanjeet.
            ResultSet rs=stmt.executeQuery("select std_inst from Modules where modulecode=1008000");
            if(rs.next())
                trnno=rs.getInt(1);
            rs.close();
            
            
            //stmt.executeUpdate("update GenParam set lndep_trn_no=lndep_trn_no+1");
            stmt.executeUpdate("update Modules set std_inst=std_inst+1 where modulecode=1008000");
            
            rs=stmt.executeQuery("select trn_seq,sanc_amt,pr_bal,int_date,other_amt,td_ac_type,td_ac_no from LoanTransaction lt,LoanMaster lm where lm.ac_type=lt.ac_type and lm.ac_no=lt.ac_no and  lm.ac_type='"+lnactype+"' and lm.ac_no="+lnacno+" order by trn_seq desc");
            if(rs.next())
            {
                int trnseq=rs.getInt("trn_seq");
                double prnbal=rs.getDouble("pr_bal");
                String intupto=rs.getString("int_date");
                amt=amt+rs.getDouble("other_amt");
                System.out.println("Trn amt==>>"+amt);
                double sancamt=rs.getDouble("sanc_amt");
                if(lnactype.equals("1008006"))
                {try{
                	intrate=getPygmyCurrentIntRate(lnactype,getSysDate(),sancamt,lnacno);
                }catch(Exception e){}
                }
                else
               intrate=getCurrentIntRate(rs.getString("td_ac_type"),rs.getInt("td_ac_no"));
                System.out.println("interest Rate="+intrate);
                //long intamttopay=Math.round((prnbal*Validations.dayCompare(intupto,Validations.addDays(getSysDate(),-1))*intrate)/(365*100));
                //long intamttopay=Math.round(getCurrentIntPay(rs.getString("td_ac_type"),rs.getInt("td_ac_no"),intupto));
                double intamttopay=getCurrentIntPay(lnactype, lnacno,getSysDate());
                System.out.println("int amount "+intamttopay);
                System.out.println("days==>"+Validations.dayCompare(intupto,Validations.addDays(getSysDate(),-1)));
                
                PreparedStatement pstmt1=con.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),?,?,?)");
                pstmt1.setString(1,String.valueOf(lnactype));//lnactype
                pstmt1.setInt(2,lnacno);//lnacno
                pstmt1.setInt(3,trnseq+1);//lst trn seq		
                pstmt1.setString(4,getSysDate());//lst trn dt
                pstmt1.setString(5,"R");
                pstmt1.setDouble(6,amt);
                pstmt1.setString(7,"T");
                pstmt1.setString(8,uid);
                pstmt1.setInt(9,trnno+1);
                pstmt1.setString(10,actype+"  "+acno);
                pstmt1.setString(11,null);
                pstmt1.setString(12,"C");
                double intamt=0.0;
                double pramt=0.0,otheramt=0.0;	
                
                if(amt>=intamttopay)
                {	
                    pstmt1.setString(13,Validations.addDays(getSysDate(),-1));//int uptodate
                    intamt=intamttopay;
                    pramt=amt-intamt;		
                    prnbal=prnbal-pramt;
                }
                else
                {
                    double perday=(prnbal*intrate)/(365*100);
                    
                    int a=(int)Math.floor(amt/perday);
                    intamt=Math.round(perday*a);
                    otheramt=amt-intamt;//here extraamt
                    
                    pstmt1.setString(13,Validations.addDays(intupto,a));		
                }
                pstmt1.setDouble(14,pramt);					
                pstmt1.setDouble(15,intamt);
                pstmt1.setDouble(16,0);
                pstmt1.setDouble(17,0);
                pstmt1.setDouble(18,otheramt);
                pstmt1.setDouble(19,prnbal);	
                pstmt1.setString(20,uid);//de tml
                pstmt1.setString(21,utml);//de user			
                pstmt1.setString(22,null);//ve user			
                pstmt1.setString(23,null);//ve tml
                pstmt1.setString(24,null);//ve date
                pstmt1.executeUpdate();
                return(trnno+1);
            }
        }catch(SQLException e){
        	e.printStackTrace();
        	context.setRollbackOnly();
        }catch(DateFormatException e){
        	e.printStackTrace();
        	context.setRollbackOnly();
        }
        finally
        {
            con.close();
        }
        return(0);
    }
    /*
     *Will return full loan application details during verification
     */
    
    public LoanMasterObject getLoanMaster(String actype,int no) throws SQLException
    {
    	System.out.println("actype==="+actype+"num===>"+no);
        LoanMasterObject lnobj=null; 
        Connection con=null;
        try{
            con=getConnection();
            Statement stmt=con.createStatement();	
            ResultSet rs=stmt.executeQuery("select lm.*,lt.pr_bal from LoanMaster lm,LoanTransaction lt where lm.ac_type=lt.ac_type and lm.ac_no=lt.ac_no and lm.ac_type='"+actype+"' and lm.ac_no="+no +" and lm.lst_tr_seq=lt.trn_seq ");
            
            if(rs.next())
            {
                lnobj=new LoanMasterObject();
                lnobj.uv.setVerId(rs.getString("lm.ve_user"));
                lnobj.uv.setUserId(rs.getString("lm.de_user"));
                lnobj.uv.setVerTml(rs.getString("lm.ve_tml"));
                lnobj.uv.setUserTml(rs.getString("lm.de_tml"));
                lnobj.uv.setVerDate(rs.getString("lm.ve_date"));
                lnobj.uv.setUserDate(rs.getString("lm.de_date"));
                
                lnobj.setAccType(rs.getString("lm.ac_type"));
                lnobj.setAccNo(rs.getInt("lm.ac_no"));
                lnobj.setNoofJtHldrs(rs.getInt("lm.no_coborrowers"));
                //		lnobj.setLoanCategory(rs.getString("loan_ctgry"));
                
                lnobj.setDepositAccType(rs.getString("lm.td_ac_type"));
                lnobj.setDepositAccNo(rs.getInt("lm.td_ac_no"));
                
                lnobj.setApplicationDate(rs.getString("lm.appn_date"));
                lnobj.setApplicationSrlNo(rs.getInt("lm.appn_srl"));
                lnobj.setAuto_loan(rs.getString("lm.conv_date"));
                
                lnobj.setPurposeCode(rs.getInt("lm.pps_code"));
                lnobj.setNoOfInstallments(rs.getInt("lm.no_inst"));
                lnobj.setInstallmentAmt(rs.getDouble("lm.inst_amt"));
                
                lnobj.setPayMode(rs.getString("lm.pay_mode"));
                lnobj.setPaymentAccno(rs.getInt("lm.pay_ac_no"));
                lnobj.setPaymentAcctype(rs.getString("lm.pay_ac_type"));
                
                lnobj.setSanctionDate(rs.getString("lm.sanc_date"));
                lnobj.setLoanSanctioned(rs.getBoolean("lm.loan_sanc"));
                lnobj.setSanctionVerified(rs.getBoolean("lm.sanc_ver"));
                System.out.println("Verified= "+rs.getBoolean("lm.sanc_ver"));
                System.out.println("Verified= "+lnobj.isSanctionVerified());
                lnobj.setSanctionedAmount(rs.getDouble("lm.sanc_amt"));
                lnobj.setRequiredAmount(rs.getDouble("lm.req_amt"));
                lnobj.setInterestRate(rs.getDouble("lm.int_rate"));
                System.out.println("lnobj.setInterestRate()="+lnobj.getInterestRate());
                System.out.println("From RS= "+rs.getDouble("lm.int_rate"));
                lnobj.setLdgPrntDate(rs.getString("lm.ldgprt_date"));
                lnobj.setLastTrndt(rs.getString("lm.lst_trn_date"));
                lnobj.setLastTrnSeq(rs.getInt("lm.lst_tr_seq"));
                
                lnobj.setClosedt(rs.getString("lm.close_date"));
                lnobj.setInterestUpto(rs.getString("lm.int_upto_date"));
                lnobj.setPrBal(rs.getDouble("lt.pr_bal"));
                
            }
        }catch(Exception ex){JOptionPane.showMessageDialog(null,"LoansOnDepositImpl.java(getLoanMaster) : "+ex);}
        
        finally
        {
            con.close();
        }
        return lnobj;
    }
    
    /*
     *Get Deposit master details for give actype and acno
     */
    
    public DepositMasterObject getDepositMaster(String actype,int no) throws SQLException
    {
        DepositMasterObject depositmasterobject=null;	
        Connection con=null;
        ResultSet rs=null;
        String sub_modcode=actype.substring(1,4);
        double total_amt=0;
        System.out.println("sub_mod==>"+sub_modcode);
        
        try
        {  
            con=getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            
            if( sub_modcode.equals("003") || sub_modcode.equals("005")||sub_modcode.equals("004") )
            	rs=stmt.executeQuery("select dm.*,custtype,sub_category from DepositMaster dm,CustomerMaster cm where  dm.ac_type='"+actype+"' and dm.ac_no="+no+" and dm.cid=cm.cid");//added by puspa
               // rs=stmt.executeQuery("select * from DepositMaster where  close_date is null and ac_type='"+actype+"' and ac_no="+no+"  and concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1)) > '"+Validations.convertYMD(getSysDate())+"' ");
            	
               // else if(sub_modcode.equals("004")){   
                //rs=stmt.executeQuery("select  sum(dt.dep_amt) as total,d.*from DepositMaster d ,DepositTransaction dt where d.ac_no=dt.ac_no and  d.ac_type=dt.ac_type and dt.ve_tml is not null and dt.ac_no="+no+" and d.ac_type='"+actype+"' group by ac_no,ac_type");
             
            //ship....19/05/2007
            /*else if(sub_modcode.equals("006"))
                rs=stmt.executeQuery("select pm.*,pt.cl_bal,custtype,sub_category from PygmyMaster pm , PygmyTransaction pt,CustomerMaster cm where  close_date is null and status='O' and pm.ac_type='1006001' and pm.ac_no=2480  and pm.lst_trn_seq=pt.trn_seq  and pm.ac_type=pt.ac_type and pm.ac_no=pt.ac_no and pm.cid=cm.cid");*/
            
            else if(sub_modcode.equals("006"))
                rs=stmt.executeQuery("select pm.*,pt.cl_bal,custtype,sub_category from PygmyMaster pm , PygmyTransaction pt,CustomerMaster cm where  close_date is null and status='O' and pm.ac_type='"+actype+"' and pm.ac_no="+no+" and pm.lst_trn_seq=pt.trn_seq  and pm.ac_type=pt.ac_type and pm.ac_no=pt.ac_no and pm.cid=cm.cid");
            ////////////
            
            if(sub_modcode.equals("003")||sub_modcode.equals("004")  || sub_modcode.equals("005"))
            {
                if(rs.next())
                {
                    System.out.println("1.......");
                    depositmasterobject=new DepositMasterObject();
                    if(rs.getString("ve_user")==null) 
                        depositmasterobject.setVerified("F");
                    else
                        depositmasterobject.setVerified("T");
                    System.out.println("2.......");
                    depositmasterobject.setAccType(actype);
                    depositmasterobject.setAccNo(no);
                    System.out.println("3.......");
                    depositmasterobject.setLoanAccno(rs.getInt("ln_ac_no"));
                    System.out.println("loan ac_no is"+rs.getInt("ln_ac_no"));
                    depositmasterobject.setLoanAcType(rs.getString("ln_ac_type"));
                    depositmasterobject.setCustomerId(rs.getInt("cid"));
                    depositmasterobject.setMaturityAmt(rs.getDouble("mat_amt"));
                    depositmasterobject.setMaturityDate(rs.getString("mat_date"));//added by puspa
                   /* if(sub_modcode.equals("004"))                    
                        depositmasterobject.setDepositAmt(rs.getDouble("total"));
                    else   */                 
                    depositmasterobject.setDepositAmt(rs.getDouble("dep_amt"));
                    
                    System.out.println("4.......");
                    depositmasterobject.setInterestRate(rs.getDouble("int_rate"));
                    depositmasterobject.setDepositDays(rs.getInt("dep_days"));
                    System.out.println("5.......");
                    depositmasterobject.setMaturityDate(rs.getString("mat_date"));
                    depositmasterobject.setDepDate(rs.getString("dep_date"));
                    System.out.println("6.......");
                    depositmasterobject.setVerified(rs.getString("ve_user"));
                    depositmasterobject.setLoanAvailed(rs.getString("ln_availed"));
                    System.out.println("7.......");
                    depositmasterobject.setDPType(rs.getInt("custtype"));
                    depositmasterobject.setCategory(rs.getInt("sub_category"));
                }
                else
                    return null;
            }
           
            else if(sub_modcode.equals("006"))
            {
                if(rs.next())
                {
                    depositmasterobject=new DepositMasterObject();
                    
                    if(rs.getString("pm.ve_user")==null) 
                        depositmasterobject.setVerified("F");
                    else
                        depositmasterobject.setVerified("T");	
                    
                    depositmasterobject.setAccType(actype);
                    depositmasterobject.setAccNo(no);
                    depositmasterobject.setLoanAccno(rs.getInt("pm.ln_ac_no"));
                    System.out.println("loan ac_no is"+rs.getInt("ln_ac_no"));
                    depositmasterobject.setLoanAcType(rs.getString("pm.ln_ac_type"));
                    depositmasterobject.setCustomerId(rs.getInt("pm.cid"));
                    depositmasterobject.setDepositAmt(rs.getDouble("pt.cl_bal"));
                    depositmasterobject.setDepDate(rs.getString("pm.open_date"));
                    depositmasterobject.setLoanAvailed(rs.getString("ln_avld"));
                    depositmasterobject.setDPType(rs.getInt("custtype"));
                    depositmasterobject.setCategory(rs.getInt("sub_category"));
                }
                else return null;
            }
            
        }catch(Exception ex){ex.printStackTrace();}
        
        finally
        {
            con.close();
        }
        
        return depositmasterobject;
    }
    
    /*
     *Returns the loan percentage how much we can take agains the deposit 
     */
    
    public double getLoanPercentage(String actype) throws SQLException
    {
        Connection con=null;
        try{
            con=getConnection();
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select lnk_shares from Modules where modulecode="+actype);
            if(rs.next())
                return(rs.getDouble("lnk_shares"));
            
        }catch(Exception ex){JOptionPane.showMessageDialog(null,"LoansOnDepositImpl.java(getLoanPercentage) : "+ex);}
        
        finally
        {
            con.close();
        }
        return(0);
    }
    
    /*
     *After enter application form store the details in loan master and loan transaction
     *it won't be posted in gl after verification it will posted
     */
    public int storeLoanMaster(LoanMasterObject ln,int type) throws SQLException
    {
        System.out.println("TYPE = "+type);
    	
    	Connection con=null;
    	Statement stmt1=null;
        try{
            con=getConnection();
            stmt1=con.createStatement();
            int no=0;
            
            if(	(! ln.isSanctionVerified()) && ln.getAccNo()!=0 )
            {
                Statement delete=con.createStatement();
                delete.executeUpdate("delete from LoanMaster where ac_no="+ln.getAccNo()+"  and ac_type='"+ln.getAccType()+"' ");
            }
            
            if(ln.getAccNo()==0 && type==0)
            {
            	System.out.println("ln.getAccNo()=====>"+ln.getAccNo()+"Account Type"+ln.getAccType());
                Statement stmt=con.createStatement();
                ResultSet rs=stmt.executeQuery("select lst_acc_no from Modules where modulecode='1008000'");
                if(rs.next())
                {
                    no=rs.getInt(1);
                }				
                ln.setAccNo(no+1);
                rs.close();
                stmt.executeUpdate("update Modules set lst_acc_no=lst_acc_no+1 where modulecode='1008000'");
            }
            if(ln.getDepositAccType().substring(1,4).equals("003") || ln.getDepositAccType().substring(1,4).equals("004") || ln.getDepositAccType().substring(1,4).equals("005"))
                stmt1.executeUpdate("update DepositMaster set ln_availed='T' where ac_type='"+ln.getDepositAccType()+"' and ac_no="+ln.getDepositAccNo());
            else if(ln.getDepositAccType().substring(1,4).equals("006"))
            {
                stmt1.executeUpdate("update PygmyMaster set ln_avld='T' where ac_type='"+ln.getDepositAccType()+"' and ac_no="+ln.getDepositAccNo());
                
            }
            System.out.println("ModuleCode/Acc No: "+ln.getAccNo());
            System.out.println("Acc Ty: "+ln.getAccType());
            int res=0;
            if(type==0)
            {
                System.out.println("4................");
                PreparedStatement pstmt=con.prepareStatement("insert into LoanMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pstmt.setString(1,String.valueOf(ln.getAccType()));//lnactype
                pstmt.setInt(2,ln.getAccNo());//lnacno
                pstmt.setInt(3,ln.getNoofJtHldrs());//no jointholders			
                pstmt.setString(4,null);//no coborrowers
                pstmt.setInt(5,ln.getCustomerId());
                pstmt.setString(6,ln.getMailingAddress());
                pstmt.setInt(7,ln.getApplicationSrlNo());//ln appn srl number
                pstmt.setString(8,ln.getApplicationDate());//ln appn date
                pstmt.setDouble(9,ln.getRequiredAmount());//req amt
                pstmt.setString(10,null);//lnee sh no
                pstmt.setString(11,null);//lnee sh type
                pstmt.setInt(12,ln.getPurposeCode());//purpose code
                pstmt.setString(13,null);//nom reg no
                pstmt.setString(14,ln.getDepositAccType());//td actype
                pstmt.setInt(15,ln.getDepositAccNo());//td acno		
                pstmt.setString(16,null);//int type
                pstmt.setString(17,null);//int calc type
                pstmt.setDouble(18,ln.getInterestRate());//int rate
                
                pstmt.setString(19,null);//psect _cd
                pstmt.setString(20,null);//weeker section
                pstmt.setString(21,String.valueOf(ln.getSexInd()));//sex
                pstmt.setString(22,null);//relation		
                pstmt.setString(23,null);//dir code			
                pstmt.setString(24,ln.getAuto_loan());//conv dt  to store the Auto Loan Recovery I-- for to wards Interest , t - for Towards Total Amount
                pstmt.setString(25,null);//holdiday mth		
                pstmt.setString(26,getSysDate());//sanc dt
                
                pstmt.setDouble(27,ln.getSanctionedAmount());//sanc amt
                pstmt.setString(28,"T");//loan sanctioned				
                pstmt.setString(29,"F");// sanc verified		
                pstmt.setString(30,null);//no insta
                pstmt.setString(31,null);//insta amt
                pstmt.setString(32,Validations.addDays(getSysDate(),-1));//int uptodt
                pstmt.setString(33,getSysDate());//lst trn dt
                pstmt.setInt(34,1);//lst trn seq		
                pstmt.setString(35,null);//default ind
                
                pstmt.setString(36,null);//close dt
                pstmt.setString(37,ln.getPayMode());//paymode
                pstmt.setString(38,ln.getPaymentAcctype());//pay actype
                pstmt.setInt(39,ln.getPaymentAccno());//pay acno		
                pstmt.setString(40,null);//remd no
                pstmt.setString(41,null);//disab left
                pstmt.setString(42,null);//remd dt
                pstmt.setString(43,null);//ldgprtdt
                pstmt.setString(44,null);//remd dt
                pstmt.setString(45,null);//ldgprtdt
                
                pstmt.setString(46,ln.uv.getUserTml());//de tml
                
                pstmt.setString(47,ln.uv.getUserId());//de user			
                pstmt.setString(48,ln.uv.getUserDate());//de date			
                pstmt.setString(49,null);//ve tml
                pstmt.setString(50,null);//ve user
                pstmt.setString(51,null);//ve date time
                
                res=pstmt.executeUpdate();
            }
            
            if((type==0 && res==1) || type==1)
            {
                System.out.println("5................");
                //double pr_bal=ln.getSanctionedAmount();
                int lsttrn=0;
                System.out.println("ln.getSanctionedAmount()= "+ln.getSanctionedAmount());
                //System.out.println("pr bal= "+pr_bal);
                double pr_bal=0;
                if(type==0 && ln.getAccNo()!=0)
                {	
                    System.out.println("1...........");
                    System.out.println("Ln ac no= "+ln.getAccNo());
                    Statement stmt=con.createStatement();
                    stmt.executeUpdate("Delete from LoanTransaction where ac_no="+ln.getAccNo()+" and ac_type='"+ ln.getAccType()+"'");
                }
                
                if(type==0)
                    lsttrn=0;
                else
                {
                    Statement stmt=con.createStatement();
                    ResultSet rs=stmt.executeQuery("select lst_tr_seq,pr_bal from LoanMaster ln,LoanTransaction lt where ln.ac_type=lt.ac_type and ln.ac_no=lt.ac_no and ln.ac_type='"+ln.getAccType()+"' and ln.ac_no="+ln.getAccNo()+"  and lt.trn_seq=ln.lst_tr_seq");
                    
                    if( rs.next() )
                    {
                        lsttrn=rs.getInt(1);
                        pr_bal=pr_bal+rs.getDouble(2);
                        System.out.println("PR_BAL= "+rs.getDouble(2));
                    }
                    
                    System.out.println("PrBal="+pr_bal);
                    rs.close();
                    
                    Statement cpstmt=con.createStatement();
                    cpstmt.executeUpdate("update LoanMaster set lst_tr_seq=lst_tr_seq+1,sanc_amt=sanc_amt+"+ln.getRequiredAmount()+",lst_trn_date='"+getSysDate()+"' where ac_type='"+ln.getAccType()+"' and ac_no="+ln.getAccNo()+"  ");
                }
                
                PreparedStatement pstmt=con.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                
                System.out.println("CALLING __ AMOUNT =  "+ln.getRequiredAmount());
                
                pstmt.setString(1,ln.getAccType());//lnactype
                pstmt.setInt(2,ln.getAccNo());//lnacno
                pstmt.setInt(3,lsttrn+1);//lst trn seq		
                pstmt.setString(4,getSysDate());//lst trn dt
                pstmt.setString(5,"D");
                System.out.println("GET REQ AMT + "+ln.getRequiredAmount());
                pstmt.setDouble(6,ln.getRequiredAmount());
                pstmt.setString(7,ln.getPayMode());
                pstmt.setString(8,null);
                pstmt.setString(9,null);
                if(ln.getPayMode().equals("T"))
                    pstmt.setString(10,ln.getPaymentAcctype()+" "+ln.getPaymentAccno());
                else                	
                    pstmt.setString(10,null);
                
                pstmt.setString(11,null);
                pstmt.setString(12,"D");
                pstmt.setString(13,Validations.addDays(getSysDate(),-1));
                pstmt.setDouble(14,ln.getSanctionedAmount());					
                pstmt.setDouble(15,0);
                pstmt.setDouble(16,0);
                pstmt.setDouble(17,0);
                pstmt.setDouble(18,0);
                pstmt.setDouble(19,ln.getRequiredAmount()+pr_bal);	
                pstmt.setString(20,ln.uv.getUserTml());//de tml
                pstmt.setString(21,ln.uv.getUserId());//de user
                pstmt.setString(22,ln.uv.getUserDate());
                pstmt.setString(23,null);//ve tml
                pstmt.setString(24,null);//ve user
                pstmt.setString(25,null);//ve date time
                int updated=pstmt.executeUpdate();
                System.out.println("UPDATED = "+updated);
           }
            
           return (ln.getAccNo());
            
        }catch(Exception e){
        	e.printStackTrace();
        	context.getRollbackOnly();
        	}
        
        finally
        {
            con.close();
        }
        return(0);
    }
    
    public LoanMasterObject additionalLoanDetails(String ac_type,int ac_no) throws SQLException,RecordsNotFoundException
    {

		LoanMasterObject obj=null;
		Connection con=null;
		
	    try{
	    	con=getConnection();
	    	Statement stmt=con.createStatement();
	    	ResultSet rs=stmt.executeQuery("select trn_amt,trn_mode,trn_narr from LoanMaster lm,LoanTransaction lt where lt.ac_no="+ac_no+" and lt.ac_type='"+ac_type+"' and lm.ac_no=lt.ac_no and lm.ac_type=lt.ac_type and lt.ve_tml is null and lt.ve_user is null and lt.ve_date is null order by trn_seq limit 1");
	    	if(rs!=null){
	    		if(rs.next()){
	    			obj=new LoanMasterObject();
	    			obj.setTrnamt(rs.getDouble("trn_amt"));
	    			obj.setTrnMode(rs.getString("trn_mode"));
	    			if(obj.getTrnMode().equalsIgnoreCase("T")){
	    				String string=rs.getString("trn_narr");
	    				StringTokenizer tokens=new StringTokenizer(string," ");
	    				while(tokens.hasMoreTokens()){
	    					obj.setPaymentAcctype(tokens.nextToken());
	    					obj.setPaymentAccno(Integer.parseInt(tokens.nextToken()));
	    				}
	    			}
	    		}
	    	}else 
	    		throw new RecordsNotFoundException();
	    	
	    }catch(SQLException sql){
	    	sql.printStackTrace();
	    	throw new SQLException();
	    }catch(RecordsNotFoundException rec){
	    	rec.printStackTrace();
	    	throw new RecordsNotFoundException();
	    }finally{
	    	con.close();
	    }
	    	
	    return obj;
	    
    }	
    
    //ver add loan
    
	
    public int verifyAdditionalLoan(LoanMasterObject ln_object,String clientdate) throws RecordNotUpdatedException,SQLException{
    	int result=0;
    	Connection conn=null;
    	int voucher_no=0,ps_no=0;
    	String user_id=null,user_tml=null,user_date=null;
    	
    	try{ 
    		System.out.println("Inside Additional Loan Verification Account Type====>"+ln_object.getAccType());
    		conn=getConnection();
    		Statement statement=conn.createStatement();
    		statement.execute("update LoanTransaction set ve_tml='"+ln_object.uv.getVerTml()+"',ve_user='"+ln_object.uv.getVerId()+"',ve_date='"+ln_object.uv.getVerDate()+"' where ac_no="+ln_object.getAccNo()+" and ac_type='"+ln_object.getAccType()+"' order by trn_seq desc limit 1");
    		if(statement.getUpdateCount()!=0){
    			if(ln_object.getPayMode().equalsIgnoreCase("C"))
                {
                	Statement st2=conn.createStatement();
                    //ResultSet rs2=st2.executeQuery("select lst_vch_no from GenParam");
                	ResultSet rs2=st2.executeQuery("select lst_voucher_no from Modules where modulecode=1008000");
                    rs2.next();
                    voucher_no=rs2.getInt("lst_voucher_no")+1;
                    rs2.close();
                    //st2.executeUpdate( "update GenParam set lst_vch_no =lst_vch_no+1");
                    st2.executeUpdate( "update Modules set lst_voucher_no ="+voucher_no+" where modulecode=1008000");
                }else if(ln_object.getPayMode().equalsIgnoreCase("P"))
                {
                  	Statement stmt_ps_no=conn.createStatement();
             	    ResultSet rs_ps_no=stmt_ps_no.executeQuery("select last_trf_scroll_no from Modules where modulecode='1016001'");
             	    if(rs_ps_no!=null)
             	    {
             	      	if(rs_ps_no.next())
             	       	{
             	       		ps_no=rs_ps_no.getInt("last_trf_scroll_no")+1;
             	       		PreparedStatement ps_update_lst_no=conn.prepareStatement("update Modules set last_trf_scroll_no="+ps_no+" where modulecode='1016001'");
             	       		if(ps_update_lst_no.executeUpdate()==0){
             	       			context.setRollbackOnly();
             	       			throw new SQLException();
             	       		}
             	       	}
             	     }
                 }
                
                GLTransObject trnobj=new GLTransObject();
                Statement stmt=conn.createStatement();
                ResultSet rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type="+ln_object.getAccType()+" and gk.gl_code=gp.gl_code and trn_type='D' and cr_dr='D' and code=1");

                if(rs.next())
                {
                	Statement stmt_lst_trn_seq=conn.createStatement();
                	ResultSet rs_lst_trn_seq=stmt_lst_trn_seq.executeQuery("Select lst_tr_seq  from LoanMaster where ac_type='"+ln_object.getAccType()+"' and ac_no="+ln_object.getAccNo()+"  ");
                	
                	trnobj.setTrnDate(clientdate);
                    trnobj.setGLType(rs.getString("gk.gl_type"));
                    trnobj.setGLCode(rs.getString("gp.gl_code"));				
                    trnobj.setTrnMode(ln_object.getPayMode());
                    if(ln_object.getPayMode().equalsIgnoreCase("P")) {
                      	trnobj.setTrnMode("T");
                    }
                    trnobj.setAmount(ln_object.getSanctionedAmount()*rs.getInt("mult_by"));
                    trnobj.setCdind("D");
                    trnobj.setAccType(ln_object.getAccType());
                    trnobj.setAccNo(String.valueOf(ln_object.getAccNo()));
                    trnobj.setTrnType("D");	
                    if(ln_object.getPayMode().equals("C"))
                        trnobj.setRefNo(voucher_no);
                    else if(ln_object.getPayMode().equals("T"))
                    	trnobj.setRefNo(0);
                    else if(ln_object.getPayMode().equals("P"))
                    	trnobj.setRefNo(ps_no);
                    trnobj.setVtml(ln_object.uv.getVerTml());
                    trnobj.setVid(ln_object.uv.getVerId());
                    trnobj.setVDate(ln_object.uv.getVerDate());
                    
                    if(rs_lst_trn_seq!=null)
                	{
                		if(rs_lst_trn_seq.next())
                			trnobj.setTrnSeq(rs_lst_trn_seq.getInt("lst_tr_seq"));
                	}
                    
                   // storeGLTransaction(trnobj);
                    result=commonlocal.storeGLTransaction(trnobj);
                }

                //If paymode is "C" insert a row in DayCash as voucher

                if(ln_object.getPayMode().equals("C"))
                {
                    PreparedStatement pstmt1=conn.prepareStatement("insert into DayCash (scroll_no,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,attached,vch_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,trn_type,name,trn_seq) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");					
                    
                    
                    pstmt1.setInt(1,0);
                    pstmt1.setString(2,clientdate); 
                    pstmt1.setString(3,ln_object.getAccType());
                    pstmt1.setInt(4,ln_object.getAccNo());
                    pstmt1.setDouble(5,ln_object.getSanctionedAmount());
                    pstmt1.setString(6,"C");
                    pstmt1.setString(7,"W");
                    pstmt1.setString(8,"F");
                    pstmt1.setInt(9,voucher_no);
                    
                    Statement stmt_entry_ids=conn.createStatement();
                    ResultSet rs_entry_ids=stmt_entry_ids.executeQuery("Select de_user,de_tml,de_date from LoanMaster where ac_no="+ln_object.getAccNo()+" and ac_type='"+ln_object.getAccType()+"' ");
                    if(rs_entry_ids!=null)
                    {
                    	if(rs_entry_ids.next())
                    	{
                    		 user_id=rs_entry_ids.getString("de_user");
                             user_tml=rs_entry_ids.getString("de_tml");
                             user_date=rs_entry_ids.getString("de_date");
                    	}
                    }
                    
                    pstmt1.setString(10,user_id);
                    pstmt1.setString(11,user_tml);
                    pstmt1.setString(12,user_date);
                    pstmt1.setString(13,ln_object.uv.getVerId());
                    pstmt1.setString(14,ln_object.uv.getVerTml());
                    //code changed by sanjeet. 
                    //pstmt1.setString(15,commonlocal.getSysDateTime());
                    pstmt1.setString(15,ln_object.uv.getVerDate());
                    pstmt1.setString(16,"P");
                    pstmt1.setString(17,ln_object.getName());
                    pstmt1.setInt(18,1);
                    
                    

                    trnobj=null;
                    trnobj=new GLTransObject();
                    //ResultSet rs_credit=stmt.executeQuery("select gp.gl_code,gk.gl_type,gp.mult_by from GLPost gp,GLKeyParam gk where gp.ac_type='1019001' and gk.ac_type='1019001' and trn_type='P' and cr_dr='C'");
                    ResultSet rs_credit=stmt.executeQuery("select * from GLKeyParam where ac_type='1019001' and code=1");
    				if(rs_credit.next())
    				{
    					trnobj=new GLTransObject();
    					trnobj.setTrnDate(clientdate);
    					//trnobj.setGLType("GL");
    					trnobj.setGLType(rs_credit.getString("gl_type")); 
    					trnobj.setGLCode(rs_credit.getString("gl_code"));
    					trnobj.setTrnMode(ln_object.getPayMode());
    					if(ln_object.getPayMode().equalsIgnoreCase("P")) {
                          	trnobj.setTrnMode("T");
                        }
    					trnobj.setAmount(ln_object.getSanctionedAmount());
    					trnobj.setCdind("C");
    					//trnobj.setAccType("1019001");
    					//trnobj.setAccNo("0");
    					trnobj.setAccType(ln_object.getAccType());
    					trnobj.setAccNo(String.valueOf(ln_object.getAccNo()));
    					trnobj.setTrnSeq(0);
    					trnobj.setTrnType(null);
    					trnobj.setRefNo(voucher_no); 
    					trnobj.setVtml(ln_object.uv.getVerTml());
    					trnobj.setVid(ln_object.uv.getVerId());
    					trnobj.setVDate(ln_object.uv.getVerDate());
    					//storeGLTransaction(trnobj);
    					result=commonlocal.storeGLTransaction(trnobj);
    				}
                    return voucher_no;
                }

                //If paymode is "T" means create accountransobject and call storeaccounttransaction
                if(ln_object.getPayMode().equals("T"))
                {
                    AccountTransObject accounttransobject=new AccountTransObject();
                    accounttransobject.setAccType(ln_object.getPaymentAcctype());
                    accounttransobject.setAccNo(ln_object.getPaymentAccno());
                    accounttransobject.setTransDate(clientdate);
                    accounttransobject.setTransAmount(ln_object.getSanctionedAmount());
                    accounttransobject.setTransMode("T");
                    accounttransobject.setTransType("R");
                    accounttransobject.setCdInd("C");
                    accounttransobject.setTransNarr(ln_object.getAccType()+" "+ln_object.getAccNo());
                    accounttransobject.uv.setUserTml(user_tml);
                    accounttransobject.uv.setUserId(user_id);
                    accounttransobject.uv.setUserDate(user_date);
                    accounttransobject.uv.setVerTml(ln_object.uv.getVerTml());
                    accounttransobject.uv.setVerId(ln_object.uv.getVerId());
                    //code added by sanjeet..
                    accounttransobject.uv.setVerDate(ln_object.uv.getVerDate());
                    result=commonlocal.storeAccountTransaction(accounttransobject);
                }
                
                if(ln_object.getPayMode().equals("P"))
                {
                	int custype=0;
                	
                	Statement stmt_cust_info=conn.createStatement();
                	ResultSet rs_cust_info=stmt_cust_info.executeQuery("Select custtype from CustomerMaster cm,LoanMaster lm where lm.ac_type='"+ln_object.getAccType()+"' and lm.ac_no="+ln_object.getAccNo()+" and lm.cid=cm.cid");
                	if(rs_cust_info!=null)
                		if(rs_cust_info.next())
                			custype= rs_cust_info.getInt("custtype");
                			
                	PreparedStatement ps_payorder=conn.prepareStatement("insert into PayOrderMake values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                	
                	ps_payorder.setString(1,"X");
         	        ps_payorder.setString(2,String.valueOf(custype));
                	ps_payorder.setInt(3,ps_no);
                	//code changed by sanjeet.
                	//ps_payorder.setString(4,commonlocal.getSysDate());
                	ps_payorder.setString(4,clientdate);
                	ps_payorder.setString(5,ln_object.getName());
                	ps_payorder.setString(6,ln_object.getAccType());
                	ps_payorder.setInt(7,ln_object.getAccNo());
                	ps_payorder.setString(8,null);
                	
                	Statement stmt_gl=conn.createStatement();
                	ResultSet rs_gl=stmt_gl.executeQuery("select gk.gl_code,gk.gl_type,gm.gl_name from GLKeyParam gk,GLMaster gm where ac_type='"+ln_object.getAccType()+"' and code=1 and gk.gl_code=gm.gl_code and gk.gl_type=gm.gl_type and ((from_date<='"+Validations.convertYMD(clientdate)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(clientdate)+"' and to_date>='"+Validations.convertYMD(clientdate)+"'))");
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
                		
                	ps_payorder.setDouble(12,ln_object.getSanctionedAmount());
                	ps_payorder.setString(13,"F");
                	
                	/*Statement stmt_po_commission=con.createStatement();
                	ResultSet rs_po_commission=stmt_po_commission.executeQuery("select commission_rate from PayOrderCommission where ac_type='Customer' ");
                	if(rs_po_commission!=null)
                	{
                		if(rs_po_commission.next())
                			ps_payorder.setDouble(14,rs_po_commission.getDouble("commission_rate"));
                	}else*/
                	ps_payorder.setDouble(14,0);
                	
                	ps_payorder.setString(15,ln_object.uv.getUserTml());
                	ps_payorder.setString(16,ln_object.uv.getUserId());
                	ps_payorder.setString(17,ln_object.uv.getVerDate());
                	ps_payorder.setString(18,ln_object.uv.getVerTml());
                	ps_payorder.setString(19,ln_object.uv.getVerId());
                	ps_payorder.setString(20,ln_object.uv.getVerDate());
                	
                	if(ps_payorder.execute())
                	{
                		if(ps_payorder.getUpdateCount()==0)
                			throw new SQLException();
                	}
                	trnobj=null;
                	trnobj=new GLTransObject();
                	
                	Statement stmt_gl_po=conn.createStatement();
                	ResultSet rs_gl_po=stmt_gl_po.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1016001' and code=1");
                	
                	//trnobj.setTrnDate(commonlocal.getSysDate());
                	//code changed by sanjeet.
                	trnobj.setTrnDate(clientdate);
                	
                	if(rs_gl_po!=null)
                	{
                		if(rs_gl_po.next())
                		{
                			trnobj.setGLType(rs_gl_po.getString("gl_type"));
                        	trnobj.setGLCode(rs_gl_po.getString("gl_code"));
                		}
                	}else{
                		trnobj.setGLType(null);
                    	trnobj.setGLCode(null);
                	}
                	
                	trnobj.setTrnMode("T");
                	trnobj.setAmount(ln_object.getSanctionedAmount());
                	trnobj.setCdind("C");
                	trnobj.setAccType(ln_object.getAccType());
                	trnobj.setAccNo(String.valueOf(ln_object.getAccNo()));
                	trnobj.setTrnSeq(0);
                	trnobj.setTrnType(null);
                	trnobj.setRefNo(ps_no);
                	trnobj.setVid(ln_object.uv.getVerId());
                	trnobj.setVtml(ln_object.uv.getVerTml());
                	//code added by puspa
                	trnobj.setVDate(ln_object.uv.getVerDate());
                	//storeGLTransaction(trnobj);
                	result=commonlocal.storeGLTransaction(trnobj);
                }
    		}
    		
    	}catch(SQLException sql){
    		context.setRollbackOnly();
    	}finally{
	    	conn.close();
	    }
    	return result;
    }

    /*
     *This method is used to verify the loans on deposit application
     */
    public int verifyLoansOnDeposit(LoanMasterObject obj,String clientdate) throws SQLException
    {
       System.out.println("obj.getAccType()="+obj.getAccType());
        System.out.println("obj.getDepositAccType()="+obj.getDepositAccType());
        System.out.println("Mode of Payment is ==>>"+obj.getPayMode());
        Connection con=null;
        int voucher_no=0;
        int ps_no=0;
        
        String user_id=null,user_tml=null,user_date=null;
        
        try{
            con=getConnection();
            
            if(obj.getPayMode().equalsIgnoreCase("C"))
            {
            	/*Statement st2=con.createStatement();
               // ResultSet rs2=st2.executeQuery("select lst_vch_no from GenParam");
            	//code changed by sanjeet.. 
            	ResultSet rs2=st2.executeQuery("select lst_voucher_no from Modules where modulecode=1008000");
                rs2.next();
                voucher_no=rs2.getInt("lst_voucher_no")+1;*/
                 voucher_no=commonlocal.getModulesColumn("lst_voucher_scroll_no","1019000");
                //rs2.close();
                //st2.executeUpdate( "update GenParam set lst_vch_no =lst_vch_no+1");
                //st2.executeUpdate( "update Modules set lst_voucher_no =lst_voucher_no+1 where modulecode=1008000");
            }else if(obj.getPayMode().equalsIgnoreCase("P"))
            {
              	Statement stmt_ps_no=con.createStatement();
         	    ResultSet rs_ps_no=stmt_ps_no.executeQuery("select last_trf_scroll_no from Modules where modulecode='1016001'");
         	    if(rs_ps_no!=null)
         	    {
         	      	if(rs_ps_no.next())
         	       	{
         	       		ps_no=rs_ps_no.getInt("last_trf_scroll_no")+1;
         	       		PreparedStatement ps_update_lst_no=con.prepareStatement("update Modules set last_trf_scroll_no="+ps_no+" where modulecode='1016001'");
         	       		if(ps_update_lst_no.executeUpdate()==0)
         	       			throw new SQLException();
         	       	}
         	     }
             }
            
            Statement stmt=con.createStatement();
            //set sanction verified 'T' and verfication details in loans master,transaction
            //update loan avialed 'T' in Depositmaster and set the loan account type, account number in depositmaster
            stmt.executeUpdate("update LoanMaster set sanc_ver='T',ve_tml='"+obj.uv.getVerTml()+"',ve_user='"+obj.uv.getVerId()+"',ve_date='"+obj.uv.getVerDate()+"' where ac_type='"+obj.getAccType()+"' and ac_no="+obj.getAccNo());
            if(obj.getPayMode().equalsIgnoreCase("C"))
            	stmt.executeUpdate("update LoanTransaction set ref_no="+voucher_no+",trn_narr='Csh Vch "+voucher_no+"',ve_tml='"+obj.uv.getVerTml()+"',ve_user='"+obj.uv.getVerId()+"',ve_date='"+obj.uv.getVerDate()+"' where ac_type='"+obj.getAccType()+"' and ac_no="+obj.getAccNo());
            else  
            	stmt.executeUpdate("update LoanTransaction set ve_tml='"+obj.uv.getVerTml()+"',ve_user='"+obj.uv.getVerId()+"',ve_date='"+obj.uv.getVerDate()+"' where ac_type='"+obj.getAccType()+"' and ac_no="+obj.getAccNo());
            	
            
            if(obj.getDepositAccType().substring(1,4).equals("003") || obj.getDepositAccType().substring(1,4).equals("004") || obj.getDepositAccType().substring(1,4).equals("005"))
                stmt.executeUpdate("update DepositMaster set ln_ac_type='"+obj.getAccType()+"',ln_ac_no="+obj.getAccNo()+" where ac_type='"+obj.getDepositAccType()+"' and ac_no="+obj.getDepositAccNo());
            else if(obj.getDepositAccType().substring(1,4).equals("006"))
            {
                stmt.executeUpdate("update PygmyMaster set ln_ac_type='"+obj.getAccType()+"',ln_ac_no="+obj.getAccNo()+" where ac_type='"+obj.getDepositAccType()+"' and ac_no="+obj.getDepositAccNo());
               System.out.println("UPDATED PM");
            }
            
            /*	
             *Select the correspond glcode,multiply by from GLPost,GLKeyParam and write to GLTransaction
            */
            GLTransObject trnobj=new GLTransObject();
            ResultSet rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type="+obj.getAccType()+" and gk.gl_code=gp.gl_code and trn_type='D' and cr_dr='D' and code=1");

            if(rs.next())
            {
            	Statement stmt_lst_trn_seq=con.createStatement();
            	ResultSet rs_lst_trn_seq=stmt_lst_trn_seq.executeQuery("Select lst_tr_seq  from LoanMaster where ac_type='"+obj.getAccType()+"' and ac_no="+obj.getAccNo()+"  ");
            	
            	trnobj.setTrnDate(clientdate);
                trnobj.setGLType(rs.getString("gk.gl_type"));
                trnobj.setGLCode(rs.getString("gp.gl_code"));				
                trnobj.setTrnMode(obj.getPayMode());
                if(obj.getPayMode().equalsIgnoreCase("P")) {
                	trnobj.setTrnMode("T");
                }
                trnobj.setAmount(obj.getSanctionedAmount()*rs.getInt("mult_by"));
                trnobj.setCdind("D");
                trnobj.setAccType(obj.getAccType());
                trnobj.setAccNo(String.valueOf(obj.getAccNo()));
                trnobj.setTrnType("D");	
                if(obj.getPayMode().equals("C"))
                    trnobj.setRefNo(voucher_no);
                else if(obj.getPayMode().equals("T"))
                	trnobj.setRefNo(0);
                else if(obj.getPayMode().equals("P"))
                	trnobj.setRefNo(ps_no);
                trnobj.setVtml(obj.uv.getVerTml());
                trnobj.setVid(obj.uv.getVerId());
                //code added by sanjeet.
                trnobj.setVDate(obj.uv.getVerDate());
                //
                if(rs_lst_trn_seq!=null)
            	{
            		if(rs_lst_trn_seq.next())
            			trnobj.setTrnSeq(rs_lst_trn_seq.getInt("lst_tr_seq"));
            	}
                
                //storeGLTransaction(trnobj);
                commonlocal.storeGLTransaction(trnobj);
            }

            //If paymode is "C" insert a row in DayCash as voucher

            if(obj.getPayMode().equals("C"))
            {
                PreparedStatement pstmt1=con.prepareStatement("insert into DayCash (scroll_no,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,attached,vch_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,trn_type,name,trn_seq) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");					
                System.out.println("Calling DAYCASH !");
                
                pstmt1.setInt(1,0);
                pstmt1.setString(2,clientdate); 
                pstmt1.setString(3,obj.getAccType());
                pstmt1.setInt(4,obj.getAccNo());
                pstmt1.setDouble(5,obj.getSanctionedAmount());
                pstmt1.setString(6,"C");
                pstmt1.setString(7,"W");
                pstmt1.setString(8,"F");
                pstmt1.setInt(9,voucher_no);
                
                Statement stmt_entry_ids=con.createStatement();
                ResultSet rs_entry_ids=stmt_entry_ids.executeQuery("Select de_user,de_tml,de_date from LoanMaster where ac_no="+obj.getAccNo()+" and ac_type='"+obj.getAccType()+"' ");
                if(rs_entry_ids!=null)
                {
                	if(rs_entry_ids.next())
                	{
                		 user_id=rs_entry_ids.getString("de_user");
                         user_tml=rs_entry_ids.getString("de_tml");
                         user_date=rs_entry_ids.getString("de_date");
                	}
                }
                
                pstmt1.setString(10,user_id);
                pstmt1.setString(11,user_tml);
                pstmt1.setString(12,user_date);
                pstmt1.setString(13,obj.uv.getVerId());
                pstmt1.setString(14,obj.uv.getVerTml());
                //code changed by sanjeet.
                //pstmt1.setString(15,commonlocal.getSysDateTime());
                pstmt1.setString(15,obj.uv.getVerDate());
                //
                pstmt1.setString(16,"P");
                pstmt1.setString(17,obj.getName());
                pstmt1.setInt(18,1);
                
                System.out.println("pstmt1 = "+pstmt1.executeUpdate());

                trnobj=null;
                trnobj=new GLTransObject();
                ResultSet rs_credit=stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1019001'");
				if(rs_credit.next())
				{
					trnobj=new GLTransObject();
					trnobj.setTrnDate(clientdate);
					//trnobj.setGLType("GL");
					trnobj.setGLType(rs_credit.getString("gl_type")); 
					trnobj.setGLCode(rs_credit.getString("gl_code"));
					trnobj.setTrnMode(obj.getPayMode());
					if(obj.getPayMode().equalsIgnoreCase("P")) {
	                	trnobj.setTrnMode("T");
	                }
					trnobj.setAmount(obj.getSanctionedAmount());
					trnobj.setCdind("C");
					//trnobj.setAccType("1019001");
					//trnobj.setAccNo("0");
					trnobj.setAccType(obj.getAccType());
					trnobj.setAccNo(String.valueOf(obj.getAccNo()));
					trnobj.setTrnSeq(0);
					trnobj.setTrnType("D");
					trnobj.setRefNo(voucher_no); 
					trnobj.setVtml(obj.uv.getVerTml());
					trnobj.setVid(obj.uv.getVerId());
					//code added by sanjeet.
					trnobj.setVDate(obj.uv.getVerDate());
					//
					//storeGLTransaction(trnobj);
					commonlocal.storeGLTransaction(trnobj);
				}
                return voucher_no;
            }

            //If paymode is "T" means create accountransobject and call storeaccounttransaction
            if(obj.getPayMode().equals("T"))
            {
            	System.out.println("Mode of Payment is transfer");
                AccountTransObject accounttransobject=new AccountTransObject();
                accounttransobject.setAccType(obj.getPaymentAcctype());
                accounttransobject.setAccNo(obj.getPaymentAccno());
                accounttransobject.setTransDate(clientdate);
                accounttransobject.setTransAmount(obj.getSanctionedAmount());
                accounttransobject.setTransMode("T");
                accounttransobject.setTransType("R");
                accounttransobject.setCdInd("C");
                accounttransobject.setTransNarr(obj.getAccType()+" "+obj.getAccNo());
                //added by puspa
                accounttransobject.setTransSource(obj.uv.getUserTml());
                accounttransobject.uv.setUserTml(obj.uv.getUserTml());      		
                accounttransobject.uv.setUserId(obj.uv.getUserId());
                accounttransobject.uv.setUserDate(obj.uv.getUserDate());
                accounttransobject.uv.setVerTml(obj.uv.getVerTml());
                accounttransobject.uv.setVerId(obj.uv.getVerId());
                //code added by sanjeet.
                accounttransobject.uv.setVerDate(obj.uv.getVerDate());
                //
                commonlocal.storeAccountTransaction(accounttransobject);
            }
            
            if(obj.getPayMode().equals("P"))
            {
            	int custype=0;
            	
            	Statement stmt_cust_info=con.createStatement();
            	ResultSet rs_cust_info=stmt_cust_info.executeQuery("Select custtype from CustomerMaster cm,LoanMaster lm where lm.ac_type='"+obj.getAccType()+"' and lm.ac_no="+obj.getAccNo()+" and lm.cid=cm.cid");
            	if(rs_cust_info!=null)
            		if(rs_cust_info.next())
            			custype= rs_cust_info.getInt("custtype");
            			
            	PreparedStatement ps_payorder=con.prepareStatement("insert into PayOrderMake values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            	
            	ps_payorder.setString(1,"X");
     	        ps_payorder.setString(2,String.valueOf(custype));
            	ps_payorder.setInt(3,ps_no);
            	//code changed by sanjeet.
            	//ps_payorder.setString(4,commonlocal.getSysDate());
            	ps_payorder.setString(4,clientdate);
            	ps_payorder.setString(5,obj.getName());
            	ps_payorder.setString(6,obj.getAccType());
            	ps_payorder.setInt(7,obj.getAccNo());
            	ps_payorder.setString(8,null);
            	
            	Statement stmt_gl=con.createStatement();
            	ResultSet rs_gl=stmt_gl.executeQuery("select gk.gl_code,gk.gl_type,gm.gl_name from GLKeyParam gk,GLMaster gm where ac_type='"+obj.getAccType()+"' and code=1 and gk.gl_code=gm.gl_code and gk.gl_type=gm.gl_type and ((from_date<='"+Validations.convertYMD(clientdate)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(clientdate)+"' and to_date>='"+Validations.convertYMD(clientdate)+"'))");
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
            		
            	ps_payorder.setDouble(12,obj.getSanctionedAmount());
            	ps_payorder.setString(13,"F");
            	
            	/*Statement stmt_po_commission=con.createStatement();
            	ResultSet rs_po_commission=stmt_po_commission.executeQuery("select commission_rate from PayOrderCommission where ac_type='Customer' ");
            	if(rs_po_commission!=null)
            	{
            		if(rs_po_commission.next())
            			ps_payorder.setDouble(14,rs_po_commission.getDouble("commission_rate"));
            	}else*/
            	ps_payorder.setDouble(14,0);
            	
            	ps_payorder.setString(15,obj.uv.getUserTml());
            	ps_payorder.setString(16,obj.uv.getUserId());
            	ps_payorder.setString(17,obj.uv.getVerDate());
            	ps_payorder.setString(18,obj.uv.getVerTml());
            	ps_payorder.setString(19,obj.uv.getVerId());
            	ps_payorder.setString(20,obj.uv.getVerDate());
            	
            	if(ps_payorder.execute())
            	{
            		if(ps_payorder.getUpdateCount()==0)
            			throw new SQLException();
            	}
            	trnobj=null;
            	trnobj=new GLTransObject();
            	
            	Statement stmt_gl_po=con.createStatement();
            	ResultSet rs_gl_po=stmt_gl_po.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1016001' and code=1");
            	
            	//trnobj.setTrnDate(commonlocal.getSysDate());
            	//code changed by sanjeet.
            	trnobj.setTrnDate(clientdate);
            	
            	if(rs_gl_po!=null)
            	{
            		if(rs_gl_po.next())
            		{
            			trnobj.setGLType(rs_gl_po.getString("gl_type"));
                    	trnobj.setGLCode(rs_gl_po.getString("gl_code"));
            		}
            	}else{
            		trnobj.setGLType(null);
                	trnobj.setGLCode(null);
            	}
            	
            	trnobj.setTrnMode("T");
            	trnobj.setAmount(obj.getSanctionedAmount());
            	trnobj.setCdind("C");
            	trnobj.setAccType(obj.getAccType());
            	trnobj.setAccNo(String.valueOf(obj.getAccNo()));
            	trnobj.setTrnSeq(0);
            	trnobj.setTrnType(null);
            	trnobj.setRefNo(ps_no);
            	trnobj.setVid(obj.uv.getVerId());
            	trnobj.setVtml(obj.uv.getVerTml());
            	//code added by sanjeet.
            	trnobj.setVDate(obj.uv.getVerDate());
            	//storeGLTransaction(trnobj);
            	commonlocal.storeGLTransaction(trnobj);
            	
            	return(ps_no);
            }

            return (1);
        }catch(SQLException e){
        	e.printStackTrace();
        	context.setRollbackOnly();
        }
        catch(Exception e){
        	e.printStackTrace();
        	context.setRollbackOnly();
        }
        finally
        {
            con.close();
        }
        return(0);
        
    }
    
    
    
    /*
     *This method will return loanpurpose object which will contain purpose code,purpose
     *description
     */
    public LoanPurposeObject[] getLoanPurposes() throws SQLException
    {
        LoanPurposeObject lnpurp[]=null;
        Connection con=null;
        try{
            con=getConnection();
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from LoanPurposes");
            rs.last();
            lnpurp=new LoanPurposeObject[rs.getRow()];
            System.out.println("length"+rs.getRow());
            rs.beforeFirst();
            int i=0;
            while(rs.next())
            {
                lnpurp[i]=new LoanPurposeObject();
                lnpurp[i].setPurposeCode(rs.getInt("pps_no"));	
                lnpurp[i].setPurposeDesc(rs.getString("pps_desc"));	
                lnpurp[i].uv.setUserTml(rs.getString("de_tml"));
                lnpurp[i].uv.setUserId(rs.getString("de_user"));
                lnpurp[i].uv.setUserDate(rs.getString("de_datetime"));
                System.out.println(lnpurp[i].uv.getUserDate());
                i++;
                
            }
        }catch(Exception ex){JOptionPane.showMessageDialog(null,"LoansOnDepositImpl.java(getLoanPurposes) : "+ex);}
        
        finally
        {
            con.close();
        }
        return lnpurp;
    }
    
    /*
     *This method will return the opened account betweent the given date	 
     */
    public LoanReportObject[] getOpenedAccounts(String from,String to,String actype,int type) throws SQLException
    {
        Connection con=null;
        LoanReportObject lnobj[]=null;
        Address addr = null;
        try{
        	
        	
            ResultSet rs1=null,rs2=null;
            con=getConnection();
            Statement stmt=con.createStatement();
            Statement stmt1=con.createStatement();
            String str="";
            if(str.length()>0)
            {
            	if(type==1)
                rs1=stmt.executeQuery("select ca.*,lm.ac_type,lm.ac_no,lm.sanc_date,lm.sanc_amt,concat(IFNULL(fname,''),' ', IFNULL(mname,'') ,' ' ,IFNULL(lname,'')) as name,dm.ac_type,dm.ac_no,dm.dep_date,dm.mat_date,dm.dep_days,dm.int_rate,dm.dep_amt from DepositMaster dm,LoanMaster lm,CustomerMaster,CustomerAddr ca where lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid  and dm.ln_availed='T' and ca.cid=CustomerMaster.cid and ca.addr_type=dm.add_type and concat(right(lm.sanc_date,4),'-',mid(lm.sanc_date,locate('/',lm.sanc_date)+1,(locate('/',lm.sanc_date,4)-locate('/',lm.sanc_date)-1)),'-',left(lm.sanc_date,locate('/',lm.sanc_date)-1)) between '" +Validations.convertYMD(from)+"'  and '" +Validations.convertYMD(to)+"'order by lm.ac_no and lm.ac_type ");
            	else
            		rs1=stmt.executeQuery("select ca.*,lm.ac_type,lm.ac_no,lm.sanc_date,lm.sanc_amt,concat(IFNULL(fname,''),' ', IFNULL(mname,'') ,' ' ,IFNULL(lname,'')) as name,dm.ac_type,dm.ac_no,dm.dep_date,dm.mat_date,dm.dep_days,dm.int_rate,dm.dep_amt from DepositMaster dm,LoanMaster lm,CustomerMaster,CustomerAddr ca where lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid  and dm.ln_availed='T' and ca.cid=CustomerMaster.cid and ca.addr_type=dm.add_type and lm.ac_type='"+actype+"' and concat(right(lm.sanc_date,4),'-',mid(lm.sanc_date,locate('/',lm.sanc_date)+1,(locate('/',lm.sanc_date,4)-locate('/',lm.sanc_date)-1)),'-',left(lm.sanc_date,locate('/',lm.sanc_date)-1)) between '" +Validations.convertYMD(from)+"'  and '" +Validations.convertYMD(to)+"'order by lm.ac_no and lm.ac_type ");
            }
            else
            {
            	if(type==1){
                rs1=stmt.executeQuery("select distinct ca.*,lm.ac_type,lm.ac_no,lm.sanc_date,lm.sanc_amt,concat(IFNULL(fname,''),' ', IFNULL(mname,'') ,' ' ,IFNULL(lname,'')) as name,dm.ac_type,dm.ac_no,dm.dep_date,dm.mat_date,dm.dep_days,dm.int_rate,dm.dep_amt from DepositMaster dm,LoanMaster lm,CustomerMaster,CustomerAddr ca where lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid  and dm.ln_availed='T' and ca.cid=CustomerMaster.cid and ca.addr_type=dm.add_type and concat(right(lm.sanc_date,4),'-',mid(lm.sanc_date,locate('/',lm.sanc_date)+1,(locate('/',lm.sanc_date,4)-locate('/',lm.sanc_date)-1)),'-',left(lm.sanc_date,locate('/',lm.sanc_date)-1)) between '" +Validations.convertYMD(from)+"'  and '" +Validations.convertYMD(to)+"' ");
                rs2=stmt1.executeQuery("select distinct ca.*,lm.ac_type,lm.ac_no,lm.sanc_date,lm.sanc_amt,concat(IFNULL(fname,''),' ', IFNULL(mname,'') ,' ' ,IFNULL(lname,'')) as name,pm.ac_type,pm.ac_no,pm.open_date,lm.appn_date from PygmyMaster pm,LoanMaster lm,CustomerMaster,CustomerAddr ca where lm.td_ac_type=pm.ac_type and lm.td_ac_no=pm.ac_no and pm.cid=CustomerMaster.cid  and pm.ln_avld='T' and ca.cid=CustomerMaster.cid and ca.addr_type=pm.addr_type and concat(right(lm.sanc_date,4),'-',mid(lm.sanc_date,locate('/',lm.sanc_date)+1,(locate('/',lm.sanc_date,4)-locate('/',lm.sanc_date)-1)),'-',left(lm.sanc_date,locate('/',lm.sanc_date)-1)) between '" +Validations.convertYMD(from)+"'  and '" +Validations.convertYMD(to)+"' ");
            	}
            	else{
            		rs1=stmt.executeQuery("select distinct ca.*,lm.ac_type,lm.ac_no,lm.sanc_date,lm.sanc_amt,concat(IFNULL(fname,''),' ', IFNULL(mname,'') ,' ' ,IFNULL(lname,'')) as name,dm.ac_type,dm.ac_no,dm.dep_date,dm.mat_date,dm.dep_days,dm.int_rate,dm.dep_amt from DepositMaster dm,LoanMaster lm,CustomerMaster,CustomerAddr ca where lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid  and dm.ln_availed='T' and ca.cid=CustomerMaster.cid and ca.addr_type=dm.add_type and lm.ac_type='"+actype+"' and concat(right(lm.sanc_date,4),'-',mid(lm.sanc_date,locate('/',lm.sanc_date)+1,(locate('/',lm.sanc_date,4)-locate('/',lm.sanc_date)-1)),'-',left(lm.sanc_date,locate('/',lm.sanc_date)-1)) between '" +Validations.convertYMD(from)+"'  and '" +Validations.convertYMD(to)+"'order by lm.ac_no and lm.ac_type ");
                    rs2=stmt1.executeQuery("select distinct ca.*,lm.ac_type,lm.ac_no,lm.sanc_date,lm.sanc_amt,concat(IFNULL(fname,''),' ', IFNULL(mname,'') ,' ' ,IFNULL(lname,'')) as name,pm.ac_type,pm.ac_no,pm.open_date,lm.appn_date from PygmyMaster pm,LoanMaster lm,CustomerMaster,CustomerAddr ca where lm.td_ac_type=pm.ac_type and lm.td_ac_no=pm.ac_no and pm.cid=CustomerMaster.cid  and pm.ln_avld='T' and ca.cid=CustomerMaster.cid and ca.addr_type=pm.addr_type and lm.ac_type='"+actype+"' and concat(right(lm.sanc_date,4),'-',mid(lm.sanc_date,locate('/',lm.sanc_date)+1,(locate('/',lm.sanc_date,4)-locate('/',lm.sanc_date)-1)),'-',left(lm.sanc_date,locate('/',lm.sanc_date)-1)) between '" +Validations.convertYMD(from)+"'  and '" +Validations.convertYMD(to)+"'order by lm.ac_no and lm.ac_type ");
            	}
            }
            
            int no_rs1=0;
            int no_rs2=0;
            if(rs1.next())
            {	
                rs1.last();
                no_rs1=rs1.getRow();
                rs1.beforeFirst();	
            }
            
            if(rs2.next())
            {	
                rs2.last();
                no_rs2=rs2.getRow();
                rs2.beforeFirst();	
            }
            
            lnobj=new LoanReportObject[no_rs1+no_rs2];
            
            int i=0;
            
            while(rs1.next())
            {
            	
            	addr = new Address();
            	
                lnobj[i]=new LoanReportObject();
                lnobj[i].setAccType(rs1.getString("lm.ac_type"));
                lnobj[i].setAccNo(rs1.getInt("lm.ac_no"));
                lnobj[i].setSanctionedAmount(rs1.getDouble("lm.sanc_amt"));
                lnobj[i].setSanctionDate(rs1.getString("lm.sanc_date"));
                lnobj[i].setDepositAccType(rs1.getString("dm.ac_type"));
                lnobj[i].setDepositAccNo(rs1.getInt("dm.ac_no"));
                lnobj[i].setName(rs1.getString("name"));
                lnobj[i].setDepositAmt(rs1.getDouble("dm.dep_amt"));
                lnobj[i].setMaturityDate(rs1.getString("dm.mat_date"));
                lnobj[i].setDepDate(rs1.getString("dm.dep_date"));
                lnobj[i].setDepositIntRate(rs1.getDouble("dm.int_rate"));
                lnobj[i].setDepositDays(rs1.getInt("dm.dep_days"));
                //lnobj[i].setLoanIntRate(lnobj[i].getDepositIntRate()+getCurrentIntRate(lnobj[i].getDepositAccType(),lnobj[i].getDepositAccNo()));
                lnobj[i].setLoanIntRate(getCurrentIntRate(lnobj[i].getDepositAccType(),lnobj[i].getDepositAccNo()));
                System.out.println("LOAN INT RATE="+lnobj[i].getLoanIntRate());
                
                addr.setAddress(rs1.getString("address"));
                addr.setCity(rs1.getString("city"));
                addr.setState(rs1.getString("state"));
                addr.setPin(rs1.getString("pin"));
                
                lnobj[i].setAddr(addr);
                
                /*lnobj[i].addr.setAddress(rs1.getString("address"));
                lnobj[i].addr.setCity(rs1.getString("city"));
                lnobj[i].addr.setState(rs1.getString("state"));
                lnobj[i].addr.setPin(rs1.getString("pin"));*/
                
                addr = null;
                i++;
            }
            
            while(rs2.next())
            {
            	
            	addr = new Address();
            	
                lnobj[i]=new LoanReportObject();
                lnobj[i].setAccType(rs2.getString("lm.ac_type"));
                lnobj[i].setAccNo(rs2.getInt("lm.ac_no"));
                lnobj[i].setSanctionedAmount(rs2.getDouble("lm.sanc_amt"));
                lnobj[i].setSanctionDate(rs2.getString("lm.sanc_date"));
                lnobj[i].setDepositAccType(rs2.getString("pm.ac_type"));
                lnobj[i].setDepositAccNo(rs2.getInt("pm.ac_no"));
                lnobj[i].setName(rs2.getString("name"));
                lnobj[i].setDepositAmt(0.0);
                lnobj[i].setMaturityDate("");
                lnobj[i].setDepDate(rs2.getString("pm.open_date"));
                lnobj[i].setDepositIntRate(0.0);
                lnobj[i].setDepositDays(0);
                lnobj[i].setLoanIntRate(lnobj[i].getDepositIntRate()+getPygmyCurrentIntRate(lnobj[i].getDepositAccType(),lnobj[i].getSanctionDate(),lnobj[i].getSanctionedAmount(),lnobj[i].getAccNo()));
             
                addr.setAddress(rs2.getString("address"));
                addr.setCity(rs2.getString("city"));
                addr.setState(rs2.getString("state"));
                addr.setPin(rs2.getString("pin"));
                
                lnobj[i].setAddr(addr);
                
                
                /*lnobj[i].addr.setAddress(rs2.getString("address"));
                lnobj[i].addr.setCity(rs2.getString("city"));
                lnobj[i].addr.setState(rs2.getString("state"));
                lnobj[i].addr.setPin(rs2.getString("pin"));*/
                
                addr = null;
                i++;
            }
            
        }catch(Exception e){e.printStackTrace();}
        
        finally
        {
            con.close();
        }
        return lnobj;
    }
    
	public LoanReportObject[] getClosedAccounts(String from,String to,String ac_type,int type) throws SQLException
	{
		Connection con=null;
		LoanReportObject lnobj[]=null;
		Address addr;
		try{
		ResultSet rs1=null,rs2=null;
		con=getConnection();
		Statement stmt=con.createStatement();
		Statement stmt1=con.createStatement();
		String str="";
		if(str.length()>0){
			if(type==1)
				rs1=stmt.executeQuery("select ca.*,lm.ac_type,lm.ac_no,lm.sanc_date,lm.sanc_amt,concat(IFNULL(fname,''),' ', IFNULL(mname,'') ,' ' ,IFNULL(lname,'')) as name,dm.ac_type,dm.ac_no,dm.dep_date,dm.mat_date,dm.dep_days,dm.int_rate,dm.dep_amt from DepositMaster dm,LoanMaster lm,CustomerMaster,CustomerAddr ca where lm.close_date is not null and lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid  and dm.ln_availed='T' and ca.cid=CustomerMaster.cid and ca.addr_type=dm.add_type and concat(right(lm.close_date,4),'-',mid(lm.close_date,locate('/',lm.close_date)+1,(locate('/',lm.close_date,4)-locate('/',lm.close_date)-1)),'-',left(lm.close_date,locate('/',lm.close_date)-1)) between '" +Validations.convertYMD(from)+"'  and '" +Validations.convertYMD(to)+"' ");
			else
				rs1=stmt.executeQuery("select ca.*,lm.ac_type,lm.ac_no,lm.sanc_date,lm.sanc_amt,concat(IFNULL(fname,''),' ', IFNULL(mname,'') ,' ' ,IFNULL(lname,'')) as name,dm.ac_type,dm.ac_no,dm.dep_date,dm.mat_date,dm.dep_days,dm.int_rate,dm.dep_amt from DepositMaster dm,LoanMaster lm,CustomerMaster,CustomerAddr ca where lm.close_date is not null and lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid  and dm.ln_availed='T' and ca.cid=CustomerMaster.cid and ca.addr_type=dm.add_type and lm.ac_type='"+ac_type+"' and concat(right(lm.close_date,4),'-',mid(lm.close_date,locate('/',lm.close_date)+1,(locate('/',lm.close_date,4)-locate('/',lm.close_date)-1)),'-',left(lm.close_date,locate('/',lm.close_date)-1)) between '" +Validations.convertYMD(from)+"'  and '" +Validations.convertYMD(to)+"' ");
		}
			
		else
			{
				if(type==1){
					rs1=stmt.executeQuery("select distinct ca.*,lm.ac_type,lm.ac_no,lm.sanc_date,lm.sanc_amt,concat(IFNULL(fname,''),' ', IFNULL(mname,'') ,' ' ,IFNULL(lname,'')) as name,dm.ac_type,dm.ac_no,dm.dep_date,dm.mat_date,dm.dep_days,dm.int_rate,dm.dep_amt from DepositMaster dm,LoanMaster lm,CustomerMaster,CustomerAddr ca where lm.close_date is not null and lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid  and dm.ln_availed='T' and ca.cid=CustomerMaster.cid and ca.addr_type=dm.add_type and concat(right(lm.close_date,4),'-',mid(lm.close_date,locate('/',lm.close_date)+1,(locate('/',lm.close_date,4)-locate('/',lm.close_date)-1)),'-',left(lm.close_date,locate('/',lm.close_date)-1)) between '" +Validations.convertYMD(from)+"'  and '" +Validations.convertYMD(to)+"' ");
					rs2=stmt1.executeQuery("select distinct ca.*,lm.ac_type,lm.ac_no,lm.sanc_date,lm.sanc_amt,concat(IFNULL(fname,''),' ', IFNULL(mname,'') ,' ' ,IFNULL(lname,'')) as name,pm.ac_type,pm.ac_no,pm.open_date,lm.appn_date from PygmyMaster pm,LoanMaster lm,CustomerMaster,CustomerAddr ca where lm.close_date is not null and lm.td_ac_type=pm.ac_type and lm.td_ac_no=pm.ac_no and pm.cid=CustomerMaster.cid  and pm.ln_avld='T' and ca.cid=CustomerMaster.cid and ca.addr_type=pm.addr_type and concat(right(lm.close_date,4),'-',mid(lm.close_date,locate('/',lm.close_date)+1,(locate('/',lm.close_date,4)-locate('/',lm.close_date)-1)),'-',left(lm.close_date,locate('/',lm.close_date)-1)) between '" +Validations.convertYMD(from)+"'  and '" +Validations.convertYMD(to)+"' ");
					}
				else
				{
					rs1=stmt.executeQuery("select distinct ca.*,lm.ac_type,lm.ac_no,lm.sanc_date,lm.sanc_amt,concat(IFNULL(fname,''),' ', IFNULL(mname,'') ,' ' ,IFNULL(lname,'')) as name,dm.ac_type,dm.ac_no,dm.dep_date,dm.mat_date,dm.dep_days,dm.int_rate,dm.dep_amt from DepositMaster dm,LoanMaster lm,CustomerMaster,CustomerAddr ca where lm.close_date is not null and lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid  and dm.ln_availed='T' and ca.cid=CustomerMaster.cid and ca.addr_type=dm.add_type and lm.ac_type='"+ac_type+"' and concat(right(lm.close_date,4),'-',mid(lm.close_date,locate('/',lm.close_date)+1,(locate('/',lm.close_date,4)-locate('/',lm.close_date)-1)),'-',left(lm.close_date,locate('/',lm.close_date)-1)) between '" +Validations.convertYMD(from)+"'  and '" +Validations.convertYMD(to)+"' ");
					rs2=stmt1.executeQuery("select distinct ca.*,lm.ac_type,lm.ac_no,lm.sanc_date,lm.sanc_amt,concat(IFNULL(fname,''),' ', IFNULL(mname,'') ,' ' ,IFNULL(lname,'')) as name,pm.ac_type,pm.ac_no,pm.open_date,lm.appn_date from PygmyMaster pm,LoanMaster lm,CustomerMaster,CustomerAddr ca where lm.close_date is not null and lm.td_ac_type=pm.ac_type and lm.td_ac_no=pm.ac_no and pm.cid=CustomerMaster.cid  and pm.ln_avld='T' and ca.cid=CustomerMaster.cid and ca.addr_type=pm.addr_type and lm.ac_type='"+ac_type+"' and concat(right(lm.close_date,4),'-',mid(lm.close_date,locate('/',lm.close_date)+1,(locate('/',lm.close_date,4)-locate('/',lm.close_date)-1)),'-',left(lm.close_date,locate('/',lm.close_date)-1)) between '" +Validations.convertYMD(from)+"'  and '" +Validations.convertYMD(to)+"' ");
				}
			}

		int no_rs1=0;
		int no_rs2=0;
		if(rs1.next())
		{	
			rs1.last();
			no_rs1=rs1.getRow();
			rs1.beforeFirst();	
		}
		
		if(rs2.next())
		{	
			rs2.last();
			no_rs2=rs2.getRow();
			rs2.beforeFirst();	
		}
		lnobj=new LoanReportObject[no_rs1+no_rs2];
		
		int i=0;

		while(rs1.next())
		{	
			addr=new Address();
			lnobj[i]=new LoanReportObject();
			lnobj[i].setAccType(rs1.getString("lm.ac_type"));
			lnobj[i].setAccNo(rs1.getInt("lm.ac_no"));
			lnobj[i].setSanctionedAmount(rs1.getDouble("lm.sanc_amt"));
			lnobj[i].setSanctionDate(rs1.getString("lm.sanc_date"));
			lnobj[i].setDepositAccType(rs1.getString("dm.ac_type"));
			lnobj[i].setDepositAccNo(rs1.getInt("dm.ac_no"));
			lnobj[i].setName(rs1.getString("name"));
			lnobj[i].setDepositAmt(rs1.getDouble("dm.dep_amt"));
			lnobj[i].setMaturityDate(rs1.getString("dm.mat_date"));
			lnobj[i].setDepDate(rs1.getString("dm.dep_date"));
			lnobj[i].setDepositIntRate(rs1.getDouble("dm.int_rate"));
			lnobj[i].setDepositDays(rs1.getInt("dm.dep_days"));
			//lnobj[i].setLoanIntRate(lnobj[i].getDepositIntRate()+getCurrentIntRate(lnobj[i].getDepositAccType(),lnobj[i].getDepositAccNo()));
			lnobj[i].setLoanIntRate(getCurrentIntRate(lnobj[i].getDepositAccType(),lnobj[i].getDepositAccNo()));
			System.out.println("LOAN INT RATE="+lnobj[i].getLoanIntRate());
			
			/*lnobj[i].addr.setAddress(rs1.getString("address"));
			lnobj[i].addr.setCity(rs1.getString("city"));
			lnobj[i].addr.setState(rs1.getString("state"));
			lnobj[i].addr.setPin(rs1.getString("pin"));*/
			
			 addr.setAddress(rs1.getString("address"));
             addr.setCity(rs1.getString("city"));
             addr.setState(rs1.getString("state"));
             addr.setPin(rs1.getString("pin"));
             
             lnobj[i].setAddr(addr);
			
			
			i++;
			addr = null;
		}
		
		while(rs2.next())
		{
			addr=new Address();
			lnobj[i]=new LoanReportObject();
			lnobj[i].setAccType(rs2.getString("lm.ac_type"));
			lnobj[i].setAccNo(rs2.getInt("lm.ac_no"));
			lnobj[i].setSanctionedAmount(rs2.getDouble("lm.sanc_amt"));
			lnobj[i].setSanctionDate(rs2.getString("lm.sanc_date"));
			lnobj[i].setDepositAccType(rs2.getString("pm.ac_type"));
			lnobj[i].setDepositAccNo(rs2.getInt("pm.ac_no"));
			lnobj[i].setName(rs2.getString("name"));
			lnobj[i].setDepositAmt(0.0);
			lnobj[i].setMaturityDate("");
			lnobj[i].setDepDate(rs2.getString("pm.open_date"));
			lnobj[i].setDepositIntRate(0.0);
			lnobj[i].setDepositDays(0);
			lnobj[i].setLoanIntRate(lnobj[i].getDepositIntRate()+getPygmyCurrentIntRate(lnobj[i].getDepositAccType(),lnobj[i].getSanctionDate(),lnobj[i].getSanctionedAmount(),lnobj[i].getAccNo()));
			/*lnobj[i].addr.setAddress(rs2.getString("address"));
			lnobj[i].addr.setCity(rs2.getString("city"));
			lnobj[i].addr.setState(rs2.getString("state"));
			lnobj[i].addr.setPin(rs2.getString("pin"));*/
			
			addr.setAddress(rs2.getString("address"));
            addr.setCity(rs2.getString("city"));
            addr.setState(rs2.getString("state"));
            addr.setPin(rs2.getString("pin"));
            
            lnobj[i].setAddr(addr);
			
			
			i++;
			addr = null;
		}
		
		}catch(Exception e){e.printStackTrace();}
		
		finally
		{
			con.close();
		}
		return lnobj;
	}
    
    /*This method is used to retun the passbook of given ld type and for given
     * account number
     */
    
	 public LoanReportObject[] getPassBook(String actype,int acno) throws SQLException
	    {
	    	System.out.println(" actype= "+actype);
	        System.out.println(" acno= "+acno);
	        
	        Connection con=null;
	        ResultSet rs=null,res=null;
	        LoanReportObject lnobj[]=null;
	        String sub_code="";
	        double sanc_amt=0;
	        double intrate=0;
	        int sub_acno=0;
	        int cid=0;
	        try{
	            con=getConnection();
	            System.out.println("2........");
	            Statement stmt=con.createStatement();
	            res=stmt.executeQuery("Select td_ac_type,td_ac_no,sanc_amt,cid from LoanMaster where ac_no="+acno+" and ac_type='"+actype+"'  ");
	            if(res.next()){
	                sub_code=res.getString("td_ac_type");
	                sub_acno=res.getInt("td_ac_no");
	                sanc_amt=res.getDouble("sanc_amt");
	                
	                cid=res.getInt(4);
	               
	            }   
	            else
	                return null;
	            
	            System.out.println("3........");
	            
	            if(sub_code.substring(1,4).equals("003") || sub_code.substring(1,4).equals("004")|| sub_code.substring(1,4).equals("005"))
	            {
            	System.out.println("inside if ");
            	           	
	               // rs=stmt.executeQuery("select lm.ac_type,lm.ac_no,lm.sanc_date,lm.int_upto_date,lm.nom_reg_no,lm.sanc_amt,lm.close_date,concat(IFNULL(fname,''),' ', IFNULL(mname,'') ,' ' ,IFNULL(lname,'')) as name,dm.ac_type,dm.ac_no,lt.trn_type,lt.trn_date,lt.trn_mode,lt.pr_bal,lt.trn_narr,lt.cd_ind,lt.pr_amt,lt.int_amt,lt.other_amt,lt.int_date,lt.ref_no,lt.extra_int,dm.dep_days,dm.dep_date,dm.mat_date,dm.dep_amt,dm.mat_amt,ca.phno,ca.email from DepositMaster dm,LoanMaster lm,LoanTransaction lt,CustomerMaster,CustomerAddr ca where lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid and CustomerMaster.cid =ca.cid and lt.ac_type=lm.ac_type and lt.ac_no=lm.ac_no and dm.ln_availed='T' and lm.ac_type='"+actype+"' and lm.ac_no="+acno+" group by trn_seq ");
            	//vinay 10/07/2007
            	
            	rs=stmt.executeQuery("select lm.ac_type,lm.ac_no,lm.sanc_date,lm.int_upto_date,lm.nom_reg_no,lm.sanc_amt,lm.close_date,concat(IFNULL(fname,''),' ', IFNULL(mname,'') ,' ' ,IFNULL(lname,'')) as name,dm.ac_type,dm.ac_no,lt.trn_type,lt.trn_date,lt.trn_mode,lt.pr_bal,lt.trn_narr,lt.cd_ind,lt.pr_amt,lt.int_amt,lt.other_amt,lt.int_date,lt.ref_no,lt.extra_int,dm.dep_days,dm.dep_date,dm.mat_date,dm.dep_amt,dm.mat_amt,ca.phno,ca.email from DepositMaster dm,LoanMaster lm,LoanTransaction lt,CustomerMaster,CustomerAddr ca where lm.td_ac_type=dm.ac_type and lm.td_ac_no=dm.ac_no and dm.cid=CustomerMaster.cid and CustomerMaster.cid =ca.cid and lt.ac_type=lm.ac_type and lt.ac_no=lm.ac_no and lm.ac_type='"+actype+"' and lm.ac_no="+acno+" group by trn_seq ");
            	
            	intrate=getCurrentIntRate(sub_code,sub_acno);
	            }
	            else if(sub_code.substring(1,4).equals("006")){
	            	System.out.println(" inside ------");
	                rs=stmt.executeQuery("select lm.ac_type,lm.ac_no,lm.sanc_date,lm.int_upto_date,lm.sanc_amt,lm.nom_reg_no,lm.close_date,concat(IFNULL(fname,''),' ', IFNULL(mname,'') ,' ' ,IFNULL(lname,'')) as name,pm.ac_type,pm.ac_no,lt.trn_type,lt.trn_date,lt.trn_mode,lt.pr_bal,lt.trn_narr,lt.cd_ind,lt.pr_amt,lt.int_amt,lt.other_amt,lt.int_date,lt.ref_no,lt.extra_int,ca.email,ca.phno from PygmyMaster pm,LoanMaster lm,LoanTransaction lt,CustomerMaster,CustomerAddr ca where lm.td_ac_type=pm.ac_type and lm.td_ac_no=pm.ac_no and pm.cid=CustomerMaster.cid and CustomerMaster.cid=ca.cid  and lt.ac_type=lm.ac_type and lt.ac_no=lm.ac_no and pm.ln_avld='T' and lm.ac_type='"+actype+"' and lm.ac_no="+acno+" ");
	                System.out.println("4--------");
	               
	                System.out.println(sanc_amt);
	                System.out.println(getSysDate());
	                //System.out.println(getPygmyCurrentIntRate(actype,getSysDate(),sanc_amt,acno));
	                intrate=getPygmyCurrentIntRate(actype,getSysDate(),sanc_amt,acno);
	                System.out.println("intrate"+intrate);
	            }
	            if(! rs.next())
	                return null;
	            
	            rs.last();
	            System.out.println("5........");
	            System.out.println("Length RS= "+rs.getRow());
	            lnobj=new LoanReportObject[rs.getRow()];
	            System.out.println("Length = "+lnobj.length);
	            rs.beforeFirst();
	            int i=0;
	            System.out.println("6........");
	    
	            while(rs.next())
	            {
	                System.out.println("7........");
	                lnobj[i]=new LoanReportObject();
	                System.out.println("8........");
	                if(i==0)
	                {
	                    System.out.println("9........");
	                    lnobj[i].setAccType(rs.getString("lm.ac_type"));
	                    lnobj[i].setAccNo(rs.getInt("lm.ac_no"));
	                    lnobj[i].setSanctionedAmount(rs.getDouble("lm.sanc_amt"));
	                    System.out.println("10........");
	                    lnobj[i].setSanctionDate(rs.getString("lm.sanc_date"));
	                    lnobj[i].setIntUptoDate(rs.getString("lm.int_upto_date"));
	                    //added by puspa
	                    lnobj[i].setPhoneNo(rs.getInt("ca.phno"));
	                //    lnobj[i].setCID(rs.getInt("cid"));//geetha
	                    lnobj[i].setCID(cid);//geetha
	                    lnobj[i].setNomNo(rs.getInt("lm.nom_reg_no"));
	                    lnobj[i].setEmail(rs.getString("ca.email"));
	                    lnobj[i].setLoanIntRate(intrate);
	                    System.out.println("loan interest"+intrate);
	                    System.out.println("email is"+rs.getString("ca.email"));
	                    System.out.println("email is"+lnobj[i].getEmail());
	                    System.out.println("11........");

	                    if(sub_code.substring(1,4).equals("003")|| sub_code.substring(1,4).equals("004") || sub_code.substring(1,4).equals("005"))
	                    {
	                        lnobj[i].setDepositAccType(rs.getString("dm.ac_type"));
	                        System.out.println("Ac type = "+lnobj[0].getDepositAccType());				
	                        lnobj[i].setDepositAccNo(rs.getInt("dm.ac_no"));
	                        //added by puspa
	                        lnobj[i].setDepositDays(rs.getInt("dm.dep_days"));
	                        lnobj[i].setDepDate(rs.getString("dm.dep_date"));
	                        lnobj[i].setMaturityDate(rs.getString("dm.mat_date"));
	                        lnobj[i].setDepositAmt(rs.getDouble("dm.dep_amt"));
	                        lnobj[i].setMaturityAmt(rs.getDouble("dm.mat_amt"));
	                        
	                    }
	                    
	                    else if(sub_code.substring(1,4).equals("006"))
	                    {
	                        lnobj[i].setDepositAccType(rs.getString("pm.ac_type"));
	                        lnobj[i].setDepositAccNo(rs.getInt("pm.ac_no"));
	                       
	                        /*lnobj[i].setDepositDays(0);
	                        lnobj[i].setDepDate(null);
	                        lnobj[i].setMaturityDate(null);*/
	                    }
	                    
	                    lnobj[i].setCloseDate(rs.getString("lm.close_date"));
	                    lnobj[i].setName(rs.getString("name"));
	                }
	                lnobj[i].setInterestPaid(rs.getDouble("lt.int_amt"));
	                lnobj[i].setOtherAmt(rs.getDouble("lt.other_amt"));
	                lnobj[i].setTransactionDate(rs.getString("lt.trn_date"));
	                lnobj[i].setTranType(rs.getString("lt.trn_type"));
	                lnobj[i].setTranMode(rs.getString("lt.trn_mode"));
	                lnobj[i].setTranNarration(rs.getString("lt.trn_narr"));
	                lnobj[i].setCdind(rs.getString("lt.cd_ind"));
	                lnobj[i].setReferenceNo(rs.getInt("lt.ref_no"));
	                lnobj[i].setPrincipalPaid(rs.getDouble("lt.pr_amt"));
	                lnobj[i].setLoanBalance(rs.getDouble("lt.pr_bal"));
	                lnobj[i].setExtraIntPaid(rs.getDouble("lt.extra_int"));
	                System.out.println("Extra Int Paid(rs)"+rs.getDouble("lt.extra_int"));
	                System.out.println("Extra Int Paid(rs)"+rs.getDouble("lt.extra_int"));
	                System.out.println("Extra Int Paid"+lnobj[i].getExtraIntPaid());
	                i++;
	            }
	        }catch(Exception e){e.printStackTrace();}
	        
	        finally
	        {
	            con.close();
	        }
	        return lnobj;
	    }
    
    public void ejbRemove(){}
    public void ejbActivate(){}
    public void ejbPassivate(){}
    public void setSessionContext(javax.ejb.SessionContext cntx){
    	context=cntx;
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    public int deleteAccount(int ac_no,String ac_type,int depacno,String depactype) throws RemoteException,SQLException
    {
        int result=0;
        Connection con=null;
        Statement stmt = null;
      
        try
        {
            con=getConnection();
            stmt = con.createStatement();
            Statement stmtM=con.createStatement();
            Statement stmtT=con.createStatement();
            String qryM=" Delete from LoanMaster where ac_no="+ac_no+" and ac_type='"+ac_type+"' ";
            String qryT=" Delete from LoanTransaction where ac_no="+ac_no+" and ac_type='"+ac_type+"' ";
            stmt.executeUpdate("update DepositMaster set ln_availed='F' where ac_type='"+depactype+"' and ac_no="+depacno+" ");
            if(stmtM.executeUpdate(qryM)>0)
                result=2;
            if(result==2)
            {
                if(stmtT.executeUpdate(qryT)>0)
                    result=1;
                else
                    result=0;
            }
        }catch(Exception exception){exception.printStackTrace();}
        finally
		{
    		try
			{
    			con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
        
        return result;
    }
    
    public LoanReportObject getIntInfo(String ac_type,int ac_no) throws SQLException
    {
        System.out.println("CALLING GETINFO ");
        
        LoanReportObject lnobj=null;
        Connection conn=null;
        
        try
        {
            conn=getConnection();
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("Select lt.trn_amt,lt.int_date,lt.pr_amt,lt.int_amt,lt.extra_int from LoanTransaction lt where lt.ac_no="+ac_no+" and lt.ac_type='"+ac_type+"' order by trn_seq");
            
            if(rs.last())
            {
                lnobj=new LoanReportObject();
                lnobj.setTrnAmt(rs.getDouble("lt.trn_amt"));
                lnobj.setIntUptoDate(rs.getString("lt.int_date"));
                lnobj.setPrnAmt(rs.getDouble("lt.pr_amt"));
                lnobj.setInterestPaid(rs.getDouble("lt.int_amt"));
                lnobj.setExtraIntPaid(rs.getDouble("lt.extra_int"));
                
                System.out.println("TRN AMT = "+lnobj.getTrnAmt());
                System.out.println("INT DATE = "+lnobj.getIntUptoDate());
                System.out.println("PRN AMT = "+lnobj.getPrnAmt());
                System.out.println("INT PAID = "+lnobj.getInterestPaid());
                
            }
            
        }catch(Exception e){e.printStackTrace();}
        finally
        {
            conn.close();
        }
        
        return lnobj;
    }
    
    public LoanReportObject getLastPaymentDetails(String ln_ac_type,int ln_ac_no) throws SQLException
	{
    	LoanReportObject loanreportobject=null;
    	Connection conn=null;
    	
    	try
		{
    		conn=getConnection();
    		Statement stmt_1=conn.createStatement();
    		ResultSet rs_1=stmt_1.executeQuery("Select * from LoanTransaction where ac_no="+ln_ac_no+" and ac_type='"+ln_ac_type+"' and trn_type='R' and trn_mode='T' and cd_ind='C' order by trn_seq desc limit 1");
    		if(rs_1.next())
    		{
    			loanreportobject=new LoanReportObject();

    			loanreportobject.setPrnAmt(rs_1.getDouble("pr_amt"));
    			loanreportobject.setInterestPaid(rs_1.getDouble("int_amt"));
    			loanreportobject.setIntUptoDate(rs_1.getString("trn_date"));
    		}
    		else return loanreportobject;
    		
		}catch(Exception e){e.printStackTrace();}
		finally 
		{
				conn.close();
		}
    	
    	return loanreportobject;
	}
    
    public DepositMasterObject[] getAllowedDepositAccounts(String ac_type) throws RecordsNotFoundException
    {
    	DepositMasterObject[] object=null;
    	Statement stmt=null;
    	Connection conn=null;
    	ResultSet rs=null;
    	try
		{
    		conn=getConnection();
    		stmt=conn.createStatement();
    		if(ac_type.startsWith("1003") || ac_type.startsWith("1004") || ac_type.startsWith("1005"))
    			rs=stmt.executeQuery("select distinct ac_no,dep_amt,concat_ws(' ',fname,mname,lname) as name from DepositMaster dm,CustomerMaster cm where  close_date is null and ac_type='"+ac_type+"'and dm.ve_tml is not null and concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1)) > '"+Validations.convertYMD(getSysDate())+"' and dm.cid=cm.cid and ln_availed='F' order by ac_no desc");

    		else 
    			rs=stmt.executeQuery("select distinct pm.ac_no,pt.cl_bal,concat_ws(' ',fname,mname,lname) as name from PygmyMaster pm , PygmyTransaction pt,CustomerMaster cm where  close_date is null and pm.ve_tml is not null and status='O' and pm.ac_type='"+ac_type+"'  and pm.lst_trn_seq=pt.trn_seq  and pm.ac_type=pt.ac_type and pm.ac_no=pt.ac_no and pm.cid=cm.cid");

    		if(rs.next())
    		{
    			rs.last();
    			object=new DepositMasterObject[rs.getRow()];
    			rs.beforeFirst();
    		}
    		else 
    			throw new RecordsNotFoundException();
    		
    		int i=0;
    		while(rs.next())
    		{
    			object[i]=new DepositMasterObject();
    			object[i].setAccNo(rs.getInt("ac_no"));
    			object[i].setName(rs.getString("name"));
    			
    			if(ac_type.startsWith("1003") || ac_type.startsWith("1004") || ac_type.startsWith("1005"))
    				object[i].setDepositAmt(rs.getDouble("dep_amt"));
    			else 
    				object[i].setDepositAmt(rs.getDouble("cl_bal"));

    			i++;
    		}
    		
		}catch(SQLException sql){sql.printStackTrace();}
		finally
		{
			try
			{
				conn.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
		
		return object;
    }
    public DepositMasterObject[] RdBalancecalc(String ac_type,int acno) throws RecordsNotFoundException
    {
    	System.out.println("In bean==== ac_type="+ac_type+"accc num======>"+acno); 
    	DepositMasterObject[] depositmaster=null;
    	Statement stmt=null;
    	Connection con=null;
    	ResultSet rs=null;
    	try{
    		con=getConnection();
    		stmt=con.createStatement();
    		rs= stmt.executeQuery("select trn_date,dep_amt from DepositTransaction where ac_type='"+ac_type+"' and ac_no ="+acno+" and trn_type = 'D' order by trn_seq");
    		//rs=stmt.executeQuery("select dm.*,dt.trn_date,dt.dep_amt,custtype,sub_category from DepositMaster dm,CustomerMaster cm ,DepositTransaction dt where  close_date is null and ac_type='"+ac_type+"' and ac_no="+acno+" and dm.cid=cm.cid and dm.ac_type=dt.ac_type ");
    		if(rs.next())
    		{
    			rs.last();
    			depositmaster =new DepositMasterObject[rs.getRow()+1];
    			rs.beforeFirst();
    		}
    		else 
    			throw new RecordsNotFoundException();
    		int i=1;
    		while(rs.next())
    		{
    			depositmaster[i]=new DepositMasterObject();
    			depositmaster[i].setDepositAmt(rs.getDouble("dep_amt"));
    			//depositmaster[i].setDepDate(rs.getString("dep_date"));
    			depositmaster[i].setTranDate(rs.getString("trn_date"));
    			/*depositmaster[i].setAccType(ac_type);
    			depositmaster[i].setAccNo(acno);
    			depositmaster[i].setDPType(rs.getInt("custtype"));
    			depositmaster[i].setCategory(rs.getInt("sub_category"));*/
    			i++;
    		}
    		rs.close();
    		
    		
    	}catch(SQLException sql){sql.printStackTrace();}
		finally
		{
			try
			{
				con.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
    	return depositmaster;
    }
    
    public LoanMasterObject[] getLoanVerificationAccounts(String ac_type) throws RemoteException,RecordsNotFoundException
	{
    	LoanMasterObject[] obj=null;
    	Statement stmt=null;
    	Connection conn=null;
    	ResultSet rs=null;
    	
    	try
		{
    		conn=getConnection();
    		String qry="select ac_no,concat_ws(' ',fname,mname,lname) as name,sanc_amt from LoanMaster lm,CustomerMaster cm where sanc_ver='F' and close_date is null and ac_type='"+ac_type+"' and lm.cid=cm.cid order by ac_no desc";
    		stmt=conn.createStatement();
    		rs=stmt.executeQuery(qry);
    		if(rs.next())
    		{
    			rs.last();
        		obj=new LoanMasterObject[rs.getRow()];
        		rs.beforeFirst();
    		}else throw new RecordsNotFoundException();
    		
    		int i=0;
    		while(rs.next())
    		{
    			obj[i]=new LoanMasterObject();
    			
    			obj[i].setAccNo(rs.getInt("ac_no"));
    			obj[i].setSanctionedAmount(rs.getDouble("sanc_amt"));
    			obj[i].setName(rs.getString("name"));
    			
    			i++;
    		}
    	}catch(SQLException sql){sql.printStackTrace();}
    	finally
		{
    		try
			{
    			conn.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
    	
    	return obj;
	}
    
    public LoanMasterObject[] getVerificationScrollNumbers() 
    {
    	LoanMasterObject[] object=null;
    	Statement stmt=null;
    	ResultSet rs=null;
    	
    	Connection conn=null;
    	try
		{
    		conn=getConnection();
    		stmt=conn.createStatement();
    		rs=stmt.executeQuery("select distinct lt.ref_no,m.moduleabbr,lt.ac_no,lt.trn_amt,concat_ws(' ',fname,mname,lname) as name from LoanTransaction lt,LoanMaster lm,CustomerMaster cm,Modules m where lt.trn_mode='T'  and lt.trn_type='R' and lm.ac_type=m.modulecode and cm.cid=lm.cid and lm.ac_no=lt.ac_no and lm.ac_type=lt.ac_type and lt.ve_tml is null and lt.ve_user is null order by lt.ref_no");
    		if(rs!=null)
    		{
    			rs.last();
    			object=new LoanMasterObject[rs.getRow()];
    			rs.beforeFirst();
    			
    			System.out.println("Object.length = "+object.length);
    			
    			int i=0;
    			while(rs.next())
    			{
    				object[i]=new LoanMasterObject();
    				
    				object[i].setVouher_no(rs.getInt("lt.ref_no"));
    				object[i].setAccType(rs.getString("m.moduleabbr"));
    				object[i].setAccNo(rs.getInt("lt.ac_no"));
    				object[i].setTrnamt(rs.getDouble("lt.trn_amt"));
    				object[i].setName(rs.getString("name"));
    				
    				i++;
    			}
    		}
		}catch(SQLException sql){sql.printStackTrace();}
		finally
		{
			try
			{
				conn.close();
			}catch(SQLException sql){sql.printStackTrace();}
		}
    	
    	return object;
    }
    
    public LoanMasterObject[] getRecoveryAccounts(String ac_type) throws SQLException,RecordsNotFoundException{
    	
    	LoanMasterObject[] object=null;
    	Connection con=null;
    	try{
    		con=getConnection();
    		Statement stmt=con.createStatement();
    		ResultSet rs=stmt.executeQuery("select lm.ac_no,concat_WS(' ',fname,mname,lname) as name, lt.pr_bal from LoanMaster lm,LoanTransaction lt,CustomerMaster cm where lm.ac_type='"+ac_type+"' and lm.ac_no=lt.ac_no and lm.ac_type=lt.ac_type and lm.lst_tr_seq=lt.trn_seq and cm.cid=lm.cid and lt.ve_tml is not null and lm.close_date is null order by lm.ac_no");
    		if(rs!=null){
    			rs.last();
    			object=new LoanMasterObject[rs.getRow()];
    			rs.beforeFirst();
    		} else throw new RecordsNotFoundException();
    		
    		int i=0;
    		while(rs.next()){
    		
    			object[i]=new LoanMasterObject();
    			object[i].setAccNo(rs.getInt("lm.ac_no"));
    			object[i].setName(rs.getString("name"));
    			object[i].setPrBal(rs.getDouble("lt.pr_bal"));
    			i++;
    		}
    		
    	}catch(SQLException sql){sql.printStackTrace(); throw new SQLException();}
    	catch(RecordsNotFoundException rec){throw new RecordsNotFoundException();}
    	finally{
    		con.close();
    	}
    	return object;
    }
    
    public Connection getConnection() 
    {
        try{			
            return ds.getConnection("root","");
        }catch(Exception e)	{e.printStackTrace();}
        return null;
    }
    // Code added by Sanjeet..
    public double getCurrentPrBal(String actype,int acno,String date) throws SQLException
    {
        ResultSet rs=null,rs1=null;
        Connection con=null;
        double pr_bal=0;
        
        try{
            con=getConnection();
            Statement stnew=con.createStatement();
            System.out.println("Type"+actype);
            System.out.println("No"+acno);
            System.out.println("Date=>"+date);
           
            rs=stnew.executeQuery("select pr_bal from LoanTransaction where ac_type='"+actype+"' and ac_no="+acno+" and ve_tml is not null and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(date)+"' and (trn_type='R' or trn_type='D') order by trn_seq desc limit 1");
            if(rs.next()){
            	pr_bal=rs.getInt("pr_bal");
            }
            rs.beforeFirst();
            System.out.println("pr_bal==>"+pr_bal);
            return(pr_bal);
            
        }catch(Exception e){e.printStackTrace();}
        finally
        {
            con.close();
        }
        return(0);
    }
    
  
    // written by puspa
    public double getCurrentIntPay(String actype,int acno,String date) throws SQLException
    {
    	System.out.println("inside the loop");
        ResultSet rs=null,rs1=null,rs3=null;
        Connection con=null;
        Statement stmt1,stmt2=null;
        double interest_payable=0,amt=0,sanc_amt=0;
        String int_upto_date=null,dep_ac_type=null,from_date=null,to_date=null;
        int length=0;
        int dep_ac_no=0;
        String[] trn_date=null;
        double[]trn_amt=null;
        int i=0,j=0;
        
        try{
        	System.out.println("++++++++++++++++++ interest calucation started ++++++++++++++++");
            con=getConnection();
            Statement stnew=con.createStatement();
           
            stmt2=con.createStatement();
           	
            System.out.println("Type"+actype);
            System.out.println("No"+acno);
            System.out.println("Date=>"+date);
            ///if R is in last
            
           rs=stnew.executeQuery("select int_date,pr_bal from LoanTransaction where ac_type='"+actype+"' and ac_no="+acno+" and ve_tml is not null and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(date)+"' and trn_type='R' order by trn_seq desc limit 1 ");
          
           
           //if D is After R
           if(rs.next()){
        	   
        	   int_upto_date = rs.getString("int_date");
        	   System.out.println("intuptodate"+int_upto_date);
        	   System.out.println("inside 2nd loop");
        	   stmt1=con.createStatement();
        	   rs1 = stmt1.executeQuery("select int_date,pr_bal from LoanTransaction where ac_type='"+actype+"' and ac_no="+acno+" and ve_tml is not null and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))>='"+Validations.convertYMD(int_upto_date)+"' and (trn_type='R' or trn_type='D') order by trn_seq");
        	   rs.beforeFirst();
           }
           //if All are D type
           else {
        	   rs1=stmt2.executeQuery("select int_date,pr_bal from LoanTransaction where ac_type='"+actype+"' and ac_no="+acno+" and trn_type='D'");
           }
         
           if(rs1.next()) {
        	   rs1.last();
        	   
        	   trn_date=new String[rs1.getRow()];
        	   trn_amt=new double[rs1.getRow()];
        	   int_upto_date = rs1.getString("int_date");
        	   double princl_amnt = rs1.getDouble("pr_bal");
        	   length=rs1.getRow();
        	   rs1.beforeFirst();
        	   System.out.println("the length is...."+length);
        	   System.out.println("the date is...."+int_upto_date);
        	   System.out.println("the Principle amount is...."+princl_amnt);
          
        	   }
          
           System.out.println("the length is"+length);
           System.out.println("int_date==>"+int_upto_date);
           Statement stmt=con.createStatement();
           
           rs3=stmt.executeQuery("select sanc_amt,td_ac_type,td_ac_no from LoanMaster where ac_type='"+actype+"' and ac_no="+acno+"");
           if(rs3.next()) {
        	   sanc_amt=rs3.getDouble("sanc_amt");
        	   dep_ac_type=rs3.getString("td_ac_type");
        	   dep_ac_no=rs3.getInt("td_ac_no");
           }
           rs3.beforeFirst();
           System.out.println("amt==>"+sanc_amt);
           
           if(rs.next()) {
        	   
        	   System.out.println("inside 3rd loop");
        	   if(actype.equals("1008006")) {
        		   interest_payable=(Validations.dayCompare(int_upto_date,Validations.addDays(date,-1))*getPygmyCurrentIntRate(actype,date,sanc_amt,acno)*getCurrentPrBal(actype,acno,date))/(365*100);
        	   } else {
        		   interest_payable=(Validations.dayCompare(int_upto_date,Validations.addDays(date,-1))*getCurrentIntRate(dep_ac_type,dep_ac_no,date)*getCurrentPrBal(actype,acno,date))/(365*100);
        	   }
        	   System.out.println("Interest payable == >"+interest_payable);
           } else {
        	   while(rs1.next()) {
        		   trn_date[i]=rs1.getString("int_date");
        		   trn_amt[i]=rs1.getDouble("pr_bal");
        		   i++;
        	   }
            	for( j=0;j<length-1;j++) {
            		
            		from_date=trn_date[j];
            		to_date=trn_date[j+1];
            		
            		amt=trn_amt[j];
            		
            		System.out.println(" ++++++++++++++++++++++++ ");
            		System.out.println("from date "+from_date);
            		System.out.println("to date "+to_date);
            		System.out.println("Todal days "+Validations.dayCompare(from_date,Validations.addDays(to_date,0)));
            		System.out.println(" the amount is " + amt);
            		System.out.println(" the rate is " + getCurrentIntRate(dep_ac_type,dep_ac_no));
            		
            		// altered by Murugesh on 13/07/2007
            		/*if(actype.equals("1008006")) {
                    	interest_payable+=(Validations.dayCompare(from_date,Validations.addDays(to_date,-1))*getPygmyCurrentIntRate(actype,to_date,sanc_amt,acno)*amt)/(365*100);
            		} else {
            			interest_payable+=(Validations.dayCompare(from_date,Validations.addDays(to_date,-1))*getCurrentIntRate(dep_ac_type,dep_ac_no)*amt)/(365*100);
            		}*/
            		
            		 // added by Murugesh on 13/07/2007
            		if(actype.equals("1008006")) {
                    	interest_payable+=(Validations.dayCompare(from_date,Validations.addDays(to_date,0))*getPygmyCurrentIntRate(actype,to_date,sanc_amt,acno)*amt)/(365*100);
            		} else {
            			interest_payable+=(Validations.dayCompare(from_date,Validations.addDays(to_date,0))*getCurrentIntRate(dep_ac_type,dep_ac_no,date)*amt)/(365*100);
            		}
            		
            		//
            		
            		System.out.println("interest amount is"+interest_payable);
            	}
            	
            	if(j==length-1) {
            		
            		from_date = trn_date[length-1];
            		to_date = date;
            		
            		amt=trn_amt[length-1];
            		
            		System.out.println(" ++++++++++++++++++++++++ ");
            		System.out.println("from date "+from_date);
            		System.out.println("to date "+to_date);
            		System.out.println("Todal days "+Validations.dayCompare(from_date,Validations.addDays(to_date,-1)));
            		System.out.println(" the amount is " + amt);
            		System.out.println(" the rate is " + getCurrentIntRate(dep_ac_type,dep_ac_no));
            		
            		if(actype.equals("1008006")) {
            			interest_payable+=(Validations.dayCompare(from_date,Validations.addDays(to_date,-1))*getPygmyCurrentIntRate(actype,date,sanc_amt,acno)*amt)/(365*100);
            		} else {
            			interest_payable+=(Validations.dayCompare(from_date,Validations.addDays(to_date,-1))*getCurrentIntRate(dep_ac_type,dep_ac_no,date)*amt)/(365*100);
            		}
            		System.out.println("interest amount is----"+interest_payable);
            	}
           }
           System.out.println("++++++++++++++++++ interest calucation finished ++++++++++++++++");
           // return(Math.ceil(interest_payable));
            //code changed by puspa
           System.out.println("interest payable ---------------------**********------"+ Math.round(interest_payable));
           return(Math.round(interest_payable)); 
        } catch(Exception e) {
        	e.printStackTrace();
        }
        finally
        {
            con.close();
        }
        return(0);
    }
    
    public double getOtherAmt(String actype,int acno,String date) throws SQLException
    {
        ResultSet rs=null,rs1=null;
        Connection con=null;
        double other_amount=0,trn_amt=0,otr_amt=0;
        
        
        try{
            con=getConnection();
            Statement stnew=con.createStatement();
            System.out.println("Type"+actype);
            System.out.println("No"+acno);
            System.out.println("Date=>"+date);
           
            rs=stnew.executeQuery("select sum(trn_amt) as trn_amt from LoanTransaction where ac_type='"+actype+"' and ac_no="+acno+" and trn_type='O' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(date)+"'");
            if(rs.next()){
            	trn_amt=rs.getDouble("trn_amt");
            }
            rs.beforeFirst();
            System.out.println("trn_amt==>"+trn_amt);
            
            Statement stmt=con.createStatement();
            rs1=stmt.executeQuery("select sum(other_amt) as other_amt from LoanTransaction where ac_type='"+actype+"' and ac_no="+acno+" and trn_type='R' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(date)+"'");
            if(rs1.next()){
            	otr_amt=rs1.getDouble("other_amt");
            }
            rs1.beforeFirst();
            System.out.println("other_amt==>"+otr_amt);
            
            other_amount=trn_amt-otr_amt;
            
            System.out.println("Interest payable == >"+other_amount);
            return(other_amount);
            
        }catch(Exception e){e.printStackTrace();}
        finally
        {
            con.close();
        }
        return(0);
    }
    
    public double getMaxPayableAmtonCurrentDay(String actype,int acno,String date) throws SQLException
    {
        double max_amount=0,pr_bal=0,interest_payable=0,other_amount=0;
        
        try{
            System.out.println("Type"+actype);
            System.out.println("No"+acno);
            System.out.println("Date=>"+date);
            
            pr_bal=getCurrentPrBal(actype,acno,date);
            interest_payable=getCurrentIntPay(actype,acno,date);
            other_amount=getOtherAmt(actype,acno,date);
            
            max_amount=pr_bal+interest_payable+other_amount;
            System.out.println("Interest payable == >"+max_amount);
            return(max_amount);
            
        }catch(Exception e){e.printStackTrace();}
        return(0);
    }
    
    // code added by sanjeet..
    // loan verification..
    
    public Object recoverLDAccount(LoanTransactionObject loan_object) throws RecordNotUpdatedException
	{
		Connection conn=null;
		Statement stmt=null,cpstmt=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null,rs1=null;
		double other_charges=0.0,interest=0.0,loan_balance=0.0,extra_interest=0.0,amount_available=0.0,principal_payable=0.0,misc_amt=0.0;
		String int_upto_date=null;
		String temp_int_upto_date=null;
		int trn_seq=0;
		LoanTransactionObject ltrn=null;
		GLTransObject trnobj=null;
		Integer trn_seq_object=null;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			trnobj = new GLTransObject();
			if(loan_object != null)
			{
				
					if(loan_object.uv.getVerTml()!=null)  // This is checked bcoz the GL enteries should be posted only for verified accounts and for transfer this function is called twice during dataentry and verification and so this checking is essential 
					{
						rs=stmt.executeQuery("select lst_tr_seq+1 as trn_seq,cm.sub_category,lm.int_upto_date,lm.td_ac_type,lm.td_ac_no from LoanMaster as lm, CustomerMaster as cm where lm.ac_type='"+loan_object.getAccType()+"' and lm.ac_no="+loan_object.getAccNo()+" and lm.cid=cm.cid");
						if(rs.next())
						{
							if(rs.getString("trn_seq")!=null) // This condition is checked because when it is called for the first time the value will be null and so this condtion check is necessary
								trn_seq = rs.getInt("trn_seq");
							else
								trn_seq=1;
							//category = rs.getInt("sub_category");
							temp_int_upto_date=rs.getString("int_upto_date");
						}
						rs.close();
						int update=stmt.executeUpdate("update LoanMaster set lst_tr_seq="+trn_seq+" where  ac_type='"+loan_object.getAccType()+"' and ac_no="+loan_object.getAccNo()+"");
						
						if(update<=0)
							throw new RecordNotUpdatedException();
					}
					
					/*rs=stmt.executeQuery("select cm.sub_category,lm.int_upto_date from LoanMaster as lm, CustomerMaster as cm where lm.ac_type='"+loan_object.getAccType()+"' and lm.ac_no="+loan_object.getAccNo()+" and lm.cid=cm.cid");
					if(rs.next())
						category = rs.getInt("sub_category");
					rs.close();*/
					
					amount_available = loan_object.getTransactionAmount();
					
					
					if(amount_available>0)
					{
						other_charges = getOtherAmt(loan_object.getAccType(),loan_object.getAccNo(),loan_object.getTransactionDate());
						if(amount_available>=other_charges)
							amount_available=amount_available-other_charges;
						else
						{
							other_charges=amount_available;
							amount_available=0.0;
						}
					}
					
					/*if(amount_available>0)
					{
						penal_interest= calculatePenalInterestForRecovery(loan_object.getAccType(),loan_object.getAccNo(),loan_object.getTransactionDate(),category);
						if(amount_available>=penal_interest)
							amount_available=amount_available-penal_interest;
						else
						{
							penal_interest=amount_available;
							amount_available=0.0;
						}
					}*/
					
					if(amount_available>0)
					{
							interest = getCurrentIntPay(loan_object.getAccType(),loan_object.getAccNo(),loan_object.getTransactionDate());
							if(amount_available>interest){
								amount_available=amount_available-interest;
								try{
									int_upto_date=Validations.addDays(loan_object.getTransactionDate(),-1);
								}
								catch(Exception e){e.printStackTrace();}
							}
							else{
								interest=amount_available;
								amount_available=0.0;
								String dep_ac_type=null;
								int dep_ac_no=0;
								rs=stmt.executeQuery("select td_ac_type,td_ac_no from LoanMaster where ac_type='"+loan_object.getAccType()+"' and ac_no="+loan_object.getAccNo()+"");
								if(rs.next())
								{
									dep_ac_type=rs.getString("td_ac_type");
									dep_ac_no=Integer.parseInt(rs.getString("td_ac_no"));
								}
								rs.close();
								double perday=(getCurrentPrBal(loan_object.getAccType(),loan_object.getAccNo(),loan_object.getTransactionDate())*getCurrentIntRate(dep_ac_type,dep_ac_no))/(365*100);
			                    System.out.println("perday"+perday);
			                    int a=(int)Math.round(interest/perday);
			                    interest=Math.round(perday*a);
			                    System.out.println("interest"+interest);
			                    //otheramt=amt-intamt;
			                    try{
			                    	System.out.println("a==>"+a);
			                    	System.out.println("temp_date"+temp_int_upto_date);
			                    int_upto_date=Validations.addDays(temp_int_upto_date,a);
			                    }
			                    catch(Exception e){e.printStackTrace();}
							}
					}
					
					if(int_upto_date==null)  // we are checking this condition because only for interest the above conditon will be checked and int_upto_date will updated otherwise if the amount is partial towards penal interest or other charges the interest conditon will not be checked 
						int_upto_date=temp_int_upto_date; // and in such sitution the int_upto_date declared will be null and null value will be updated int Loan tran table and loan master table so to avoid this we are updating it with the previous int_upto_date
				
					
					System.out.println("the Other charges values is:"+other_charges);
					System.out.println("the interest values is:"+interest);
					System.out.println("the amount_available values is:"+amount_available);
					// These conditions are checked because at any point these values should not be negative
					if(other_charges<0)
						other_charges=0.0;
					
					/*if(penal_interest<0)
						penal_interest=0.0;*/
					
					if(interest<0)
						interest=0.0;
					
					if(amount_available<0)
						amount_available=0.0;
					
					principal_payable=loan_object.getTransactionAmount()-(other_charges+interest);
					
					loan_balance = getCurrentPrBal(loan_object.getAccType(),loan_object.getAccNo(),loan_object.getTransactionDate());
                    
                    System.out.println("principal_payable == "+principal_payable);
                    System.out.println("loan_balance == "+loan_balance);
                    
					//added by puspa				
					if(principal_payable>loan_balance)
					{
                        System.out.println("principal_payable>loan_balance");
                        
						misc_amt=principal_payable-loan_balance;
						principal_payable=loan_balance;
					
						if(misc_amt>0)
						{
						    ResultSet rs_misc=cpstmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1012000' and code=1");
						    
                            if(rs_misc.next())
                            {
                                trnobj.setGLCode(rs_misc.getString("gl_code"));
                                trnobj.setGLType(rs_misc.getString("gl_type"));
                                trnobj.setCdind("C");
                                trnobj.setAmount(misc_amt);
                                commonlocal.storeGLTransaction(trnobj);
                            }
						}
                        
                        loan_balance = 0.0;
					}
                    else
                    {
                        System.out.println("principal_payable < loan_balance");
                        
                        loan_balance = loan_balance-principal_payable;
                    }
                    
                    System.out.println("loan_balance == "+loan_balance);
					
					if(loan_balance<0)
						loan_balance=0.0;
					
					pstmt = conn.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

					pstmt.setString(1,loan_object.getAccType());
					pstmt.setInt(2,loan_object.getAccNo());
					pstmt.setInt(3,trn_seq);
					pstmt.setString(4,loan_object.getTransactionDate());
					pstmt.setString(5,"R");
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
					pstmt.setDouble(16,0);
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
                    
					/*if(rs!=null)
						rs.close();*/

					if(loan_object.uv.getVerTml()!=null)  // This is checked bcoz the GL enteries should be posted only for verified accounts and for transfer this function is called twice during dataentry and verification and so this checking is essential 
					{
						System.out.println(" inside the gl entries---------------- ");
						
						if(update_tran>0)
						{
							int loan_master_update = stmt.executeUpdate("update LoanMaster set int_upto_date='"+int_upto_date+"',lst_trn_date='"+loan_object.getTransactionDate()+"' where ac_type='"+loan_object.getAccType()+"' and ac_no="+loan_object.getAccNo()+"");
							if(loan_master_update<=0)
								throw new RecordNotUpdatedException();
						
							if(loan_balance==0){
								stmt.executeUpdate("update LoanMaster set close_date='"+getSysDate()+"' where ac_no="+loan_object.getAccNo()+" and ac_type='"+loan_object.getAccType()+"'");
								rs1=stmt.executeQuery("select td_ac_type,td_ac_no from LoanMaster  where ac_no="+loan_object.getAccNo()+" and ac_type='"+loan_object.getAccType()+"'");
								if(rs1.next())
								stmt.executeUpdate("update DepositMaster Set ln_availed='F' where ac_type='"+rs1.getString("td_ac_type")+"' and ac_no="+rs1.getInt("td_ac_no")+"");
							}
						}
						else
							throw new RecordNotUpdatedException();
						
						//postRecoveryDetails(loan_object.getAccType(),loan_object.getAccNo(),getSysDate(),category,1,loan_object.uv.getVerId(),loan_object.uv.getVerTml()); // this has been done to update the LoanRecoveryDetail table with new updated values;
						
						//GL Enteries for credit of the above transaction 
						
						trnobj.setCdind(loan_object.getCdind());
						trnobj.setTrnDate(loan_object.getTransactionDate());
						trnobj.setTrnMode(loan_object.getTranMode());
						if(loan_object.getTranMode().equalsIgnoreCase("P")) {
	                      	trnobj.setTrnMode("T");
	                    }
						trnobj.setAccType(loan_object.getAccType());
						trnobj.setAccNo(String.valueOf(loan_object.getAccNo()));
						trnobj.setTrnSeq(trn_seq);
						trnobj.setTrnType(loan_object.getTranType());
						trnobj.setRefNo(loan_object.getReferenceNo());
					    trnobj.setVtml(loan_object.uv.getVerTml());
					    trnobj.setVid(loan_object.uv.getVerId());
					    trnobj.setVDate(loan_object.uv.getVerDate());
                        
					    /*if(rs!=null)
					    	rs.close();*/
					    
					    if(other_charges>0)
		                {
                            System.out.println("inside loan other_charges > 0 .... GL");
                            
		                    rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+loan_object.getAccType()+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='O' and cr_dr='C' and code=4");
		                    if(rs.next())
		                    {
		                    	trnobj.setGLType(rs.getString(1));
		                        trnobj.setGLCode(rs.getString(2));
		                        trnobj.setAmount(other_charges*rs.getInt("mult_by"));
		                        //storeGLTransaction(trnobj);
                                System.out.println("before store GL of loan other_charges > 0");
		                        commonlocal.storeGLTransaction(trnobj);
		                    }
		                }
                        
					    /*if(rs!=null)
					    	rs.close();*/

					    /*if(penal_interest>0)
					    {
					    	rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+loan_object.getAccType()+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='C' and code=3");
		                    if(rs.next())
		                    {
		                    	trnobj.setGLType(rs.getString(1));
		                        trnobj.setGLCode(rs.getString(2));
		                        trnobj.setAmount(penal_interest*rs.getInt("mult_by"));
		                        storeGLTransaction(trnobj);
		                    }
					    }
					    if(rs!=null)
					    	rs.close();*/
					    
					    if(interest>0)
					    {
                            System.out.println("inside loan interest > 0 .... GL");
                            
					    	rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+loan_object.getAccType()+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='C' and code=2");
                            
		                    if(rs.next())
		                    {
		                    	trnobj.setGLType(rs.getString(1));
		                        trnobj.setGLCode(rs.getString(2));
		                        trnobj.setAmount(interest*rs.getInt("mult_by"));
		                        //storeGLTransaction(trnobj);
                                System.out.println("before store GL of loan interest > 0");
		                        commonlocal.storeGLTransaction(trnobj);
		                    }
					    }
                        
					    /*if(rs!=null)
					    	rs.close();*/
					    
					    if(principal_payable>0)
					    {
                            System.out.println("inside loan principal_payable > 0 .... GL");
                            
					    	rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+loan_object.getAccType()+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='C' and code=1");
                            
		                    if(rs.next())
		                    {
		                    	trnobj.setGLType(rs.getString(1));
		                        trnobj.setGLCode(rs.getString(2));
		                        trnobj.setAmount(principal_payable*rs.getInt("mult_by"));
		                        //storeGLTransaction(trnobj);
                                System.out.println("before store GL of loan principal_payable > 0");
		                        commonlocal.storeGLTransaction(trnobj);
		                    }
					    }
                        
					    /*if(rs!=null)
					    	rs.close();*/
				   }
				    
				   if(loan_object.getTranMode().equalsIgnoreCase("C"))
				   {
				   		trn_seq_object = new Integer(trn_seq);
				   		return trn_seq_object;
				   }
                   else if(loan_object.getTranMode().equalsIgnoreCase("G"))
				   {
					   return Integer.valueOf(String.valueOf((misc_amt)));
				   }
				   //ship......18/05/2007
                   else if(loan_object.getTranMode().equalsIgnoreCase("T"))
                   {
                       System.out.println("inside 'T' before return ... loan_balance == "+loan_balance);
                       return (new Double(loan_balance));
                   }
			 }
		}
		catch(SQLException exception){
			//sessionContext.setRollbackOnly();
			exception.printStackTrace();
		}
		catch(RecordNotUpdatedException rec_exception){
			//sessionContext.setRollbackOnly();
			throw rec_exception;
		}
		finally	{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return null;
	}
    
    public double getPrincipalOutstandings(String ac_type,int ac_no,String date)throws SQLException
    {
        return(0);
    }
    public double getPygmyAmt(String ac_type,int ac_no,String curdate )throws SQLException
    {
    	ResultSet rs=null,rs2=null;
    	Statement stmt=null,stmt1=null;
    	Connection con=null;
    	double interest=0,closingbal=0;
    	String open_date;
    	double amt=0;
    	try{
    		con=getConnection();
    		stmt=con.createStatement();
    		stmt1=con.createStatement();
    		interest=pygmyremote.calculateInterest(ac_type,ac_no,getSysDate());
    		closingbal=pygmyremote.getClosingBalance(ac_type,ac_no);
    		 rs = stmt.executeQuery("select open_date from PygmyMaster where ac_type='"+ac_type+"' and ac_no="+ac_no+"");
            rs.next();
            open_date=rs.getString(1);
           
            int months=commonlocal.getNoOfMonths(open_date,getSysDate());
            System.out.println("Months : "+months);
            ResultSet rs1 = stmt1.executeQuery("select int_rate from PygmyRate where '"+Validations.convertYMD(curdate)+"' between  concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1)) and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1)) and "+months+" between prd_fr and prd_to and ac_type='"+ac_type+"' ");
            rs1.next();           			
            System.out.println("Int Rate: "+rs1.getDouble("int_rate"));
            double int_rate=rs1.getDouble("int_rate");
            if(int_rate==0)
            {
            	rs2=stmt.executeQuery("select penalty_rate  from Modules where modulecode='1006001'");
            	rs2.next();
            	double penalty=rs2.getDouble(1);
            	closingbal-=(closingbal*penalty);
            }
            else
             closingbal+=interest;
            
            return closingbal;
    	}catch(Exception e){}
    	
    	finally
        {
            con.close();
        }
        return(0);
    }
    
    public double autoLoanRecovery(String ac_type ,int ac_no , double amount , String gltype,int glcode,String date,String uid,String tml,String sysdatetime ,int mult)throws RecordNotInsertedException{
    	
    	double rem_val = 0.0;
    	double tran_amt = 0.0;
    	Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	boolean auto_rec = false;
    	double interest = 0.0;
    	double max_principal_bal = 0.0;
    	double loan_balance =0.0;
    	try{
    	
    		conn = getConnection();
    		stmt = conn.createStatement();
    		
    		rs = stmt.executeQuery("select ac_type , ac_no, conv_date  from LoanMaster where td_ac_type = '"+ ac_type +"' and td_ac_no = "+ ac_no + " and close_date is null");
    		
    		if(!rs.next())
    			return amount;
    		interest = getCurrentIntPay(rs.getString("ac_type"), rs.getInt("ac_no"),date );
    		
    		max_principal_bal = getMaxPayableAmtonCurrentDay(rs.getString("ac_type"), rs.getInt("ac_no"), date);
    		
    		LoanTransactionObject loan_obj = new LoanTransactionObject();
    		
    		loan_obj.setAccNo(rs.getInt("ac_no"));
    		loan_obj.setAccType(rs.getString("ac_type"));
    		loan_obj.setCdind("C");
    		loan_obj.setTransactionDate(date);
    		loan_obj.setTranMode("T");
    		loan_obj.setTranSou(tml);
    		loan_obj.setReferenceNo(0);
    		loan_obj.setTranNarration("Auto loan Rec" + ac_type+" "+ac_no);
    		loan_obj.uv.setUserDate(sysdatetime);
    		loan_obj.uv.setUserId(uid);
    		loan_obj.uv.setUserTml(tml);
    		loan_obj.uv.setVerDate(sysdatetime);
    		loan_obj.uv.setVerId(uid);
    		loan_obj.uv.setVerTml(tml);
    		
    		
    		if(  rs.getString("conv_date") != null && rs.getString("conv_date").equalsIgnoreCase("I")){
    			
    			if(interest > amount) {
    				loan_obj.setTransactionAmount(amount);
    				tran_amt = amount;
    			} else if(interest < amount) {
    				loan_obj.setTransactionAmount(interest);
    				tran_amt = interest;
    			}
    				auto_rec = true;
    			
    		}else if ( rs.getString("conv_date") != null  && rs.getString("conv_date").equalsIgnoreCase("T")){
    			
    			
    			if( amount > max_principal_bal){
    				
    				loan_obj.setTransactionAmount(max_principal_bal);
    				tran_amt = max_principal_bal;
    				//rem_val = amount - max_principal_bal;
    				
    			} else {
    				
    				loan_obj.setTransactionAmount(amount);
    				//rem_val = 0;
    				tran_amt = amount;
    			}
    			
    			auto_rec = true;
    		}
    		
    		if ( auto_rec){
    		//  calling recovery function 
    		recoverLDAccount(loan_obj);
    		
    		
    		/// GL Transaction for one debit entry 
    		{
    			
    			System.out.println("Dr Loss Acc");
				GLTransObject trnobj=new GLTransObject();
				trnobj.setTrnDate(date);
				trnobj.setGLType(gltype); // gltype,int glcode
				trnobj.setGLCode(Integer.toString(glcode));
				trnobj.setTrnMode("T");
				trnobj.setAmount(tran_amt*mult);
				trnobj.setCdind("D");
				trnobj.setAccType(rs.getString("ac_type"));
				trnobj.setAccNo(rs.getString("ac_no"));
				trnobj.setTrnType("R");
				trnobj.setRefNo(0);
				trnobj.setTrnSeq(0);
				trnobj.setVtml(tml);  //uid,String tml,String sysdatetime
				trnobj.setVid(uid);
				trnobj.setVDate(sysdatetime);
				if(commonlocal.storeGLTransaction(trnobj)==1)
					System.out.println("Written into GLTrn - Dr Loss Acc");
				else
					System.out.println("Unable to insert into GLTrn - - Dr Loss Acc");
    			
    		}
    		
    		System.out.println("interest    calculated -------" + interest);
    		}

    	}catch(SQLException sql ){
    		
    		context.setRollbackOnly();
    		sql.printStackTrace();
    		return amount;
    		
    	}catch (RecordNotUpdatedException rec) {
			// TODO: handle exception
    		
    		rec.printStackTrace();
    		context.setRollbackOnly();
    		return amount;
		}
    	
    	finally	{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
    	
    	return amount - tran_amt;
    	
    	
    }
    
}

