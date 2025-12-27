 package frontCounterServer;
import exceptions.AccountNotFoundException;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import exceptions.GLCodeNotDefinedException;
import exceptions.NomineeNotCreatedException;
import exceptions.QtrDefinitionNotDefinedException;
import exceptions.RecordsNotFoundException;
import exceptions.SignatureNotInsertedException;
import general.Validations;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.ejb.EJBObject;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.swing.JOptionPane;

import loanServer.LoanLocal;
import loanServer.LoanLocalHome;
import masterObject.customer.CustomerMasterObject;
import masterObject.frontCounter.AccountInfoObject;
import masterObject.frontCounter.AccountMasterObject;
import masterObject.frontCounter.AccountTransObject;
import masterObject.frontCounter.AdminObject;
import masterObject.frontCounter.ChequeObject;
import masterObject.frontCounter.IntPayObject;
import masterObject.frontCounter.ODCCMasterObject;
import masterObject.frontCounter.PayOrderObject;
import masterObject.frontCounter.StockDetailsObject;
import masterObject.general.AccCategoryObject;
import masterObject.general.AccountObject;
import masterObject.general.GLMasterObject;
import masterObject.general.GoldObject;
import masterObject.general.IncomeObject;
import masterObject.general.NomineeObject;
import masterObject.general.PropertyObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.VehicleObject;
import masterObject.loans.LoanIntRateObject;
import masterObject.loans.LoanReportObject;
import masterObject.termDeposit.DepositMasterObject;

import commonServer.CommonLocal;
import commonServer.CommonLocalHome;
import commonServer.GLTransObject;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style
 *  - Code Templates
 */
public class FrontCounterBean implements SessionBean
{
	javax.sql.DataSource ds=null;
	CommonLocalHome commonlocalhome=null;
	CommonLocal commonlocal=null;
	
	LoanLocalHome loanlocalhome;
	LoanLocal loanlocal;
	
	SessionContext ctx=null;
    public void ejbCreate()
    {
		try{
			Context ctx=new InitialContext();			
			ds=(javax.sql.DataSource)ctx.lookup("java:MySqlDS");
			ctx=new InitialContext();
			Object ref=ctx.lookup("COMMONLOCALWEB");
			
	        commonlocalhome = (commonServer.CommonLocalHome)
            PortableRemoteObject.narrow(ref,commonServer.CommonLocalHome.class);
	        
	        loanlocalhome=(loanServer.LoanLocalHome)ctx.lookup("LOANSLOCALWEB");
			loanlocal=loanlocalhome.create();

	}catch(Exception e)	{e.printStackTrace(); }
}    
    
    
    
    public void removeBean(EJBObject obj)
    {
        try
		{
    		obj.remove();
    		ctx.setRollbackOnly();
		}catch(Exception ex){ex.printStackTrace();}
    }
    
     public void ejbActivate()
     {
		try{
			Context ctx=new InitialContext();			
			ds=(javax.sql.DataSource)ctx.lookup("java:MySqlDS");
		
			ctx=new InitialContext();
			Object ref=ctx.lookup("COMMONLOCAL");
			
	        commonlocalhome = (commonServer.CommonLocalHome)
            PortableRemoteObject.narrow(ref,commonServer.CommonLocalHome.class);

        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        System.out.println("Activating");
     }
     public void ejbPassivate()
    {
        ds=null;
        commonlocalhome=null;
    	commonlocal=null;
    	ctx=null;
        
    	System.out.println("Passivating");

    }
    public void ejbRemove(){}
    public void setSessionContext(javax.ejb.SessionContext cnt)
    {
    	this.ctx=cnt;
    }
    
    /** 
     * 
     * @param date
     * @return
     * @throws RemoteException
     */
    
    public GLMasterObject[] getGLMasterDetails(String date) throws RemoteException
	{
		GLMasterObject glm[] =null;
		Connection connection=null;
		Statement statement=null;
		try{
			connection=getConnection();
			statement=connection.createStatement();
			
			//ResultSet rs=statement.executeQuery("select gm.*,m.moduleabbr from GLMaster gm,Modules m where gm.gl_type=m.modulecode");
			ResultSet rs=statement.executeQuery("select gm.*,m.moduleabbr from GLMaster gm,Modules m where gm.gl_type=m.modulecode and ( (from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))"); 	//To add from_date<date & to_date is null 
			rs.last();
			glm=new  GLMasterObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				glm[i]=new GLMasterObject();
				glm[i].setGLType(rs.getString("gl_type"));
				glm[i].setGLCode(rs.getString("gl_code"));
				glm[i].setGLName(rs.getString("gl_name"));
				glm[i].setSCHType(rs.getString("sch_type"));
				glm[i].setGLStatus(rs.getString("gl_status"));
				glm[i].setNormalCd(rs.getString("normal_cd"));
				glm[i].setGLAbbreviation(rs.getString("moduleabbr"));
			
				i++;
			}
		}catch(Exception e)
		{	
			e.printStackTrace();

		
		}
		finally{
			try{
				connection.close();
			}catch(Exception exception){exception.printStackTrace();}
			
		}


		return glm;
	}
    
    /** 
     * To check whether Customer with corresponding cid exists or not
     * @Table used: CustomerMaster
     * @param cid
     * @return 1  ==> If Customer Cid exists; 
     * @return -1 ==> if Customer Cid does not exists.
     * @throws CustomerNotFoundException
     */
	public int confirmCustomerId(String cid) throws CustomerNotFoundException 
	{
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement();

		ResultSet rs=statement.executeQuery("select * from CustomerMaster where cid='"+cid+"'");
		if(rs.next())
			return 1;
		 
			throw new SQLException();
		}catch(SQLException ex){ex.printStackTrace();
		throw new CustomerNotFoundException();}
		catch(Exception ex){
			ex.printStackTrace();
			
			}
		finally{
			try{
				connection.close();
			}catch(Exception exception){exception.printStackTrace();}
			
		}
		return -1;
	}

	/**
	 * This Method is used to get all details of an Accountholder;
	 * Information includes:
	 * 1.Customer Details(Address,pin,mobile...);
	 * 2.Transaction Details(closing balance,trn_date..);
	 * 3.Master Details(ac_status,);
	 * 
	 * Tables used:Modules,GLKeyParam,AccountMaster,AccountTransaction,CustomerAddr
	 * 
	 * @param acctype
	 * @param accno
	 * @return AccountInfoObject by setting all values 
	 * @throws AccountNotFoundException
	 */
	public AccountInfoObject getAccountInfo(String acctype,int accno) throws AccountNotFoundException 
	{
		AccountInfoObject ac=null;
		ResultSet rs=null;
	//	ResultSet rs_bal=null;
		ResultSet rs_lmt=null;
		double shadow_balance=0;
		
		Connection connection=null;
		Statement statement=null,stmt=null;
		
		System.out.println("Inside get Account Information...");
		try
		{
			connection=getConnection();
			statement=connection.createStatement();
			stmt=connection.createStatement();

			System.out.println("AccNo: "+accno);
			System.out.println("AccType:"+acctype);
			if(acctype.startsWith("1002")|| acctype.startsWith("1007")|| acctype.startsWith("1017")|| acctype.startsWith("1018"))
			    /*rs=statement.executeQuery("select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,cl_bal,trn_date,trn_seq,ac_status,ch_bk_issue,AccountMaster.cid,AccountMaster.ve_user,ac_opendate,address,state,pin,country,city,phno,phstd,fax,faxstd,mobile,email,GLKeyParam.gl_type,GLKeyParam.gl_code,gl_name,Modules.*,custtype,sub_category,freeze_ind,int_pbl_date from CustomerMaster,CustomerAddr,AccountMaster ,AccountTransaction,GLKeyParam,GLMaster,Modules  where CustomerAddr.addr_type=AccountMaster.addr_type and CustomerMaster.cid=AccountMaster.cid and CustomerAddr.cid=CustomerMaster.cid and AccountMaster.ac_no=AccountTransaction.ac_no and AccountMaster.ac_type=AccountTransaction.ac_type and AccountMaster.ac_type='"+acctype+"' and AccountMaster.ac_no="+accno+" and GLKeyParam.ac_type='"+acctype+"' and Modules.modulecode='"+acctype+"' and GLKeyParam.code=CustomerMaster.custtype+1 and GLMaster.gl_code=GLKeyParam.gl_code and (AccountTransaction.ve_user is not null || AccountMaster.ve_user is not null ) order by AccountTransaction.trn_seq desc limit 1");*/
				rs=statement.executeQuery("select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,cl_bal,trn_date,trn_seq,ac_status,ch_bk_issue,AccountMaster.cid,AccountMaster.ve_user,ac_opendate,address,state,pin,country,city,phno,phstd,fax,faxstd,mobile,email,GLKeyParam.gl_type,GLKeyParam.gl_code,Modules.*,custtype,sub_category,freeze_ind,int_pbl_date from CustomerMaster,CustomerAddr,AccountMaster ,AccountTransaction,GLKeyParam,Modules where CustomerAddr.addr_type=AccountMaster.addr_type and CustomerMaster.cid=AccountMaster.cid and CustomerAddr.cid=CustomerMaster.cid and AccountMaster.ac_no=AccountTransaction.ac_no and AccountMaster.ac_type=AccountTransaction.ac_type and AccountMaster.ac_type='"+acctype+"' and AccountMaster.ac_no="+accno+" and GLKeyParam.ac_type='"+acctype+"' and Modules.modulecode='"+acctype+"' and GLKeyParam.code=CustomerMaster.custtype+1 and (AccountTransaction.ve_user is not null || AccountMaster.ve_user is not null ) order by AccountTransaction.trn_seq desc limit 1");	//To Remove GLMaster on 07/07/06
			    /*rs=statement.executeQuery("select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,cl_bal,trn_date,ac_status,AccountMaster.cid,AccountMaster.ve_user,ac_opendate,address,state,pin,country,city,phno,phstd,fax,faxstd,mobile,email,gl_type,gl_code,Modules.*,custtype,sub_category,freeze_ind,int_pbl_date from CustomerMaster,CustomerAddr,AccountMaster ,AccountTransaction,GLKeyParam,Modules  where CustomerAddr.addr_type=AccountMaster.addr_type and CustomerMaster.cid=AccountMaster.cid and CustomerAddr.cid=CustomerMaster.cid and AccountMaster.ac_no=AccountTransaction.ac_no and AccountMaster.ac_type=AccountTransaction.ac_type and AccountMaster.ac_type='"+acctype+"' and AccountMaster.ac_no="+accno+" and GLKeyParam.ac_type='"+acctype+"' and Modules.modulecode='"+acctype+"' and GLKeyParam.code=CustomerMaster.custtype+1 and (AccountTransaction.ve_user is not null || AccountMaster.ve_user is null ) order by AccountTransaction.trn_seq desc limit 1");*/
			else if(acctype.startsWith("1015"))
			    /*rs=statement.executeQuery("select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,cl_bal,trn_date,trn_seq,ac_status,ch_bk_issue,ODCCMaster.cid,ODCCMaster.ve_user,sanc_dt,ac_opendate,address,state,pin,country,city,phno,phstd,fax,faxstd,mobile,email,GLKeyParam.gl_type,GLKeyParam.gl_code,gl_name,Modules.*,creditlimit,limit_upto,custtype,sub_category,default_ind,ODCCMaster.int_uptodt from CustomerMaster,CustomerAddr,ODCCMaster,ODCCTransaction,GLKeyParam,GLMaster,Modules  where CustomerAddr.addr_type=ODCCMaster.addr_type and CustomerMaster.cid=ODCCMaster.cid and CustomerAddr.cid=CustomerMaster.cid and ODCCMaster.ac_no=ODCCTransaction.ac_no and ODCCMaster.ac_type=ODCCTransaction.ac_type and ODCCMaster.ac_type='"+acctype+"' and ODCCMaster.ac_no="+accno+" and GLKeyParam.ac_type="+acctype+" and Modules.modulecode="+acctype+" and GLKeyParam.code=CustomerMaster.custtype+1 and GLMaster.gl_code=GLKeyParam.gl_code and (ODCCTransaction.ve_user is not null || ODCCMaster.ve_user is not null ) order by ODCCTransaction.trn_seq desc limit 1");*/
				rs=statement.executeQuery("select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,cl_bal,trn_date,trn_seq,ac_status,ch_bk_issue,ODCCMaster.cid,ODCCMaster.ve_user,sanc_dt,ac_opendate,address,state,pin,country,city,phno,phstd,fax,faxstd,mobile,email,GLKeyParam.gl_type,GLKeyParam.gl_code,Modules.*,creditlimit,limit_upto,custtype,sub_category,default_ind,ODCCMaster.int_uptodt from CustomerMaster,CustomerAddr,ODCCMaster,ODCCTransaction,GLKeyParam,Modules where CustomerAddr.addr_type=ODCCMaster.addr_type and CustomerMaster.cid=ODCCMaster.cid and CustomerAddr.cid=CustomerMaster.cid and ODCCMaster.ac_no=ODCCTransaction.ac_no and ODCCMaster.ac_type=ODCCTransaction.ac_type and ODCCMaster.ac_type='"+acctype+"' and ODCCMaster.ac_no="+accno+" and GLKeyParam.ac_type="+acctype+" and Modules.modulecode="+acctype+" and GLKeyParam.code=CustomerMaster.custtype+1 and (ODCCTransaction.ve_user is not null || ODCCMaster.ve_user is not null ) order by ODCCTransaction.trn_seq desc limit 1");//To Remove GLMaster on 07/07/06
				/*rs=statement.executeQuery("select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,cl_bal,trn_date,ac_status,ODCCMaster.cid,ODCCMaster.ve_user,ac_opendate,address,state,pin,country,city,phno,phstd,fax,faxstd,mobile,email,gl_type,gl_code,Modules.*,creditlimit,limit_upto,custtype,sub_category,default_ind,int_uptodt from CustomerMaster,CustomerAddr,ODCCMaster,ODCCTransaction,GLKeyParam,Modules  where CustomerAddr.addr_type=ODCCMaster.addr_type and CustomerMaster.cid=ODCCMaster.cid and CustomerAddr.cid=CustomerMaster.cid and ODCCMaster.ac_no=ODCCTransaction.ac_no and ODCCMaster.ac_type=ODCCTransaction.ac_type and ODCCMaster.ac_type='"+acctype+"' and ODCCMaster.ac_no="+accno+" and GLKeyParam.ac_type="+acctype+" and Modules.modulecode="+acctype+" and GLKeyParam.code=CustomerMaster.custtype+1 and (ODCCTransaction.ve_user is not null || ODCCMaster.ve_user is null ) order by ODCCTransaction.trn_seq desc limit 1");*/
			else if(acctype.startsWith("1014"))
				/*rs=statement.executeQuery("select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,cl_bal,trn_date,ac_status,ODCCMaster.cid,ODCCMaster.ve_user,ac_opendate,address,state,pin,country,city,phno,phstd,fax,faxstd,mobile,email,gl_type,gl_code,Modules.*,credit_limit,limit_upto,custtype,sub_category,default_ind,ODCCMaster.int_uptodt from CustomerMaster,CustomerAddr,ODCCMaster ,ODCCTransaction,GLKeyParam,Modules,StockInspectionDetails stk  where CustomerAddr.addr_type=ODCCMaster.addr_type and CustomerMaster.cid=ODCCMaster.cid and CustomerAddr.cid=CustomerMaster.cid and ODCCMaster.ac_no=ODCCTransaction.ac_no and ODCCMaster.ac_type=ODCCTransaction.ac_type and ODCCMaster.ac_type='"+acctype+"' and ODCCMaster.ac_no="+accno+" and GLKeyParam.ac_type='"+acctype+"' and Modules.modulecode='"+acctype+"' and GLKeyParam.code=CustomerMaster.custtype+1 and stk.ac_type=ODCCMaster.ac_type and stk.ac_no=ODCCMaster.ac_no and (ODCCTransaction.ve_user is not null || ODCCMaster.ve_user is null ) order by ODCCTransaction.trn_seq desc,concat(right(stk.insp_date,4),'-',mid(stk.insp_date,locate('/',stk.insp_date)+1, (locate('/',stk.insp_date,4)-locate('/',stk.insp_date)-1)),'-',left(stk.insp_date,locate('/',stk.insp_date)-1)) desc limit 1");*/
			    //rs=statement.executeQuery("select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,cl_bal,trn_date,ac_status,ODCCMaster.cid,ODCCMaster.ve_user,ac_opendate,address,state,pin,country,city,phno,phstd,fax,faxstd,mobile,email,GLKeyParam.gl_type,GLKeyParam.gl_code,gl_name,Modules.*,credit_limit,limit_upto,custtype,sub_category,default_ind,ODCCMaster.int_uptodt from CustomerMaster,CustomerAddr,ODCCMaster ,ODCCTransaction,GLKeyParam,GLMaster,Modules,StockInspectionDetails stk  where CustomerAddr.addr_type=ODCCMaster.addr_type and CustomerMaster.cid=ODCCMaster.cid and CustomerAddr.cid=CustomerMaster.cid and ODCCMaster.ac_no=ODCCTransaction.ac_no and ODCCMaster.ac_type=ODCCTransaction.ac_type and ODCCMaster.ac_type='"+acctype+"' and ODCCMaster.ac_no="+accno+" and GLKeyParam.ac_type='"+acctype+"' and Modules.modulecode='"+acctype+"' and GLKeyParam.code=CustomerMaster.custtype+1 and GLMaster.gl_code=GLKeyParam.gl_code and stk.ac_type=ODCCMaster.ac_type and stk.ac_no=ODCCMaster.ac_no and (ODCCTransaction.ve_user is not null || ODCCMaster.ve_user is null ) order by ODCCTransaction.trn_seq desc,concat(right(stk.insp_date,4),'-',mid(stk.insp_date,locate('/',stk.insp_date)+1, (locate('/',stk.insp_date,4)-locate('/',stk.insp_date)-1)),'-',left(stk.insp_date,locate('/',stk.insp_date)-1)) desc limit 1");
			    //rs=statement.executeQuery("select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,cl_bal,trn_date,trn_seq,ac_status,ch_bk_issue,ODCCMaster.cid,ODCCMaster.ve_user,sanc_dt,ac_opendate,address,state,pin,country,city,phno,phstd,fax,faxstd,mobile,email,GLKeyParam.gl_type,GLKeyParam.gl_code,gl_name,Modules.*,limit_upto,custtype,sub_category,default_ind,ODCCMaster.int_uptodt from CustomerMaster,CustomerAddr,ODCCMaster ,ODCCTransaction,GLKeyParam,GLMaster,Modules where CustomerAddr.addr_type=ODCCMaster.addr_type and CustomerMaster.cid=ODCCMaster.cid and CustomerAddr.cid=CustomerMaster.cid and ODCCMaster.ac_no=ODCCTransaction.ac_no and ODCCMaster.ac_type=ODCCTransaction.ac_type and ODCCMaster.ac_type='"+acctype+"' and ODCCMaster.ac_no="+accno+" and GLKeyParam.ac_type='"+acctype+"' and Modules.modulecode='"+acctype+"' and GLKeyParam.code=CustomerMaster.custtype+1 and GLMaster.gl_code=GLKeyParam.gl_code and (ODCCTransaction.ve_user is not null || ODCCMaster.ve_user is not null ) order by ODCCTransaction.trn_seq desc limit 1"); 			// dee on 20/03/2006 to remove StockInspectionDetails
				rs=statement.executeQuery("select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,cl_bal,trn_date,trn_seq,ac_status,ch_bk_issue,ODCCMaster.cid,ODCCMaster.ve_user,sanc_dt,ac_opendate,address,state,pin,country,city,phno,phstd,fax,faxstd,mobile,email,GLKeyParam.gl_type,GLKeyParam.gl_code,Modules.*,limit_upto,custtype,sub_category,default_ind,ODCCMaster.int_uptodt from CustomerMaster,CustomerAddr,ODCCMaster ,ODCCTransaction,GLKeyParam,Modules where CustomerAddr.addr_type=ODCCMaster.addr_type and CustomerMaster.cid=ODCCMaster.cid and CustomerAddr.cid=CustomerMaster.cid and ODCCMaster.ac_no=ODCCTransaction.ac_no and ODCCMaster.ac_type=ODCCTransaction.ac_type and ODCCMaster.ac_type='"+acctype+"' and ODCCMaster.ac_no="+accno+" and GLKeyParam.ac_type='"+acctype+"' and Modules.modulecode='"+acctype+"' and GLKeyParam.code=CustomerMaster.custtype+1 and (ODCCTransaction.ve_user is not null || ODCCMaster.ve_user is not null ) order by ODCCTransaction.trn_seq desc limit 1"); 			// To remove StockInspectionDetails,GLMaster on 07/07/06
			System.out.println("1.......");
			if(rs.next())
			{
				int trn_seq=0;
				System.out.println("2....");
				ac=new AccountInfoObject();
				ac.setAccno(accno);
				ac.setAcctype(acctype);
				ac.setAccname(rs.getString("name"));
				ac.setAmount(rs.getDouble("cl_bal"));
				ac.setAccStatus(rs.getString("ac_status"));
				ac.setCategory(rs.getInt("custtype"));
				ac.setSubCategory(rs.getInt("sub_category"));			
				ac.setChequeIssued(rs.getString("ch_bk_issue"));
				ac.setLastTrnDate(rs.getString("trn_date"));
				ac.setTrnSeq(rs.getInt("trn_seq"));
				ac.setOpenDate(rs.getString("ac_opendate"));
				ac.setGLCode(String.valueOf(rs.getInt("gl_code")));
				ac.setGLType(rs.getString("gl_type"));
				//ac.setGLName(rs.getString("gl_name"));
				ac.addr.setAddress(rs.getString("address"));
				ac.addr.setCity(rs.getString("city"));
				ac.addr.setState(rs.getString("state"));
				ac.addr.setCountry(rs.getString("country"));
				ac.addr.setPin(rs.getString("pin"));
				ac.addr.setPhno(rs.getString("phno"));
				ac.addr.setPhStd(rs.getString("phstd"));
				ac.addr.setEmail(rs.getString("email"));
				ac.addr.setMobile(rs.getString("mobile"));
				ac.addr.setFax(rs.getString("fax"));
				ac.addr.setFaxStd(rs.getString("faxstd"));
				ac.setFreezeInd("F");
				ac.setDefaultInd("F");
				ac.setInterestPayableDate("");
				trn_seq=rs.getInt("trn_seq");
				System.out.println("Trn_seq=="+trn_seq);
				if(acctype.startsWith("1002")|| acctype.startsWith("1007") || acctype.startsWith("1017") || acctype.startsWith("1018"))
				{						
					ac.setCid(rs.getInt("AccountMaster.cid"));
					ac.setVerified(rs.getString("AccountMaster.ve_user"));
					System.out.println("FREZEEEEEE Ind==="+rs.getString("freeze_ind"));
					ac.setFreezeInd(rs.getString("freeze_ind"));
					ac.setInterestPayableDate(rs.getString("int_pbl_date"));

					/*rs_bal = statement.executeQuery("select sum(trn_amt) from AccountTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is null and cd_ind='C'");
					if(rs_bal.next())
						if(rs_bal.getDouble(1)!=0)
						shadow_balance=ac.getAmount()+rs_bal.getDouble(1);
					rs_bal = statement.executeQuery("select sum(trn_amt) from AccountTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is null and cd_ind='D'");
					if(rs_bal.next())
						if(rs_bal.getDouble(1)!=0)
						shadow_balance=shadow_balance-rs_bal.getDouble(1);
					
					/** To Get Prev Balance **//*
					rs_bal = statement.executeQuery("select cl_bal from AccountTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is not null and trn_seq<"+trn_seq+" order by trn_seq desc limit 1");
					if(rs_bal.next())
					{
						ac.setPrevBalance(rs_bal.getDouble("cl_bal"));
						System.out.println("PREVIOUSBALANCE="+ac.getPrevBalance());
					}*/
					
					/** To Get No of Transactions of the Month *//*
					rs=statement.executeQuery("select COUNT(*),AccountTransaction.ac_no from AccountTransaction where AccountTransaction.ac_no="+accno+" and AccountTransaction.ac_type='"+acctype+"' and (AccountTransaction.trn_type='R' || AccountTransaction.trn_type='P') and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(getFirstOfMnth())+"' and '"+Validations.convertYMD(getSysDate())+"'   group by AccountTransaction.ac_no order by AccountTransaction.trn_seq desc limit 1");
					if(rs.next())
					{
						ac.setNoOfTrns(rs.getInt(1));						
					}*/
				
				}
				else if(acctype.startsWith("1014")|| acctype.startsWith("1015"))
				{
					ac.setCid(rs.getInt("ODCCMaster.cid"));
					ac.setVerified(rs.getString("ODCCMaster.ve_user"));
					ac.setDefaultInd(rs.getString("default_ind"));
					ac.setSanctionDate(rs.getString("sanc_dt"));
					if(acctype.startsWith("1015"))
						ac.setCreditLimit(rs.getDouble("creditlimit"));
					else
					{   					    
					    rs_lmt=stmt.executeQuery("select credit_limit from ODCCTransaction,StockInspectionDetails stk,ODCCMaster  where ODCCMaster.ac_type=ODCCTransaction.ac_type and ODCCMaster.ac_no=ODCCTransaction.ac_no and ODCCTransaction.ac_type='"+acctype+"' and ODCCTransaction.ac_no="+accno+" and stk.ac_type=ODCCTransaction.ac_type and stk.ac_no=ODCCTransaction.ac_no and ODCCTransaction.ve_user is not null and sanc_ver='Y' order by ODCCTransaction.trn_seq desc,concat(right(stk.insp_date,4),'-',mid(stk.insp_date,locate('/',stk.insp_date)+1, (locate('/',stk.insp_date,4)-locate('/',stk.insp_date)-1)),'-',left(stk.insp_date,locate('/',stk.insp_date)-1)) desc limit 1");						
					    if(rs_lmt.next())
					        ac.setCreditLimit(rs_lmt.getDouble("credit_limit"));
					    else
					        ac.setCreditLimit(0);
					    
					    //ac.setCreditLimit(rs.getDouble("credit_limit"));//on 21/02/2006					   				        
					}
					ac.setLimitUpto(rs.getString("limit_upto"));
					ac.setInterestPayableDate(rs.getString("int_uptodt"));

					/*rs_bal = statement.executeQuery("select sum(trn_amt) from ODCCTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is null and cd_ind='C'");
					if(rs_bal.next())
						if(rs_bal.getDouble(1)!=0)
						shadow_balance=ac.getAmount()+rs_bal.getDouble(1);
					rs_bal = statement.executeQuery("select sum(trn_amt) from ODCCTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is null and cd_ind='D'");
					if(rs_bal.next())
						if(rs_bal.getDouble(1)!=0)
						shadow_balance=shadow_balance-rs_bal.getDouble(1);
					
					*//** To Get Prev Balance **//*
					rs_bal = statement.executeQuery("select cl_bal from ODCCTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is not null and trn_seq<"+trn_seq+" order by trn_seq desc limit 1");
					if(rs_bal.next())
					{
						ac.setPrevBalance(rs_bal.getDouble("cl_bal"));
					}*/				
				}
				
				ac.setShadowBalance(shadow_balance);
				
		        commonlocal = commonlocalhome.create();

				ac.setSignatureInstruction(commonlocal.getSignatureDetails(accno,acctype));


			}
//			else
	//		throw new AccountNotFoundException();

		}/*catch(AccountNotFoundException sqlexception)
		{
			throw new AccountNotFoundException();
		}*/
		catch(Exception exception){exception.printStackTrace();}
		finally{
			try{
				connection.close();
			}catch(Exception exception){exception.printStackTrace();}
			commonlocal = null;
		}
		return ac;
	}
	
	/*String getFirstOfMnth()
	{
		String mm=null,yy=null;
		try
		{
	
		String dt=getSysDate();
		StringTokenizer st=new StringTokenizer(dt,"/");
		st.nextToken();
		mm=st.nextToken();
		yy=st.nextToken();
	
		}catch(Exception e){}
		return("01/"+((mm.length()==1)?("0"+mm):mm)+"/"+yy);
	}*/

	/**
	 * Verifying ODCCMaster Includes Updating:
	 * 1.DayCash (For AccountOpening new Recipt has to be taken from Cashier i.e,set attached='T')
	 * 2.ODCCMaster
	 * 3.ODCCMasterLog
	 * 4.GLTransaction (inserting)
	 * 
	 * @see COMMON.storeGLTransaction;
	 * @param AccountTransObject
	 * @return result(int) 0 or 1 ==>Stored Correctly
	 * @throws GLCodeNotDefinedException,CreateException
	 */	
	public int verifyODCCMaster(AccountTransObject at) throws GLCodeNotDefinedException,CreateException
	{
		System.out.println("INside bean verifyODCCMaster ====");
		ResultSet rs=null;
		int scode=0;
		int result=0;
		Connection connection=null;
		Statement statement=null;
		
		try
		{
			System.out.println("===========1=========="+at.getAccNo());
			System.out.println("===========2=========="+at.uv.getVerId());
			System.out.println("===========3=========="+at.uv.getVerTml());
			System.out.println("===========4=========="+at.uv.getVerDate());
			System.out.println("===========5=========="+at.getRef_No());
			System.out.println("===========6=========="+at.getTransDate());
			
			connection=getConnection();
			statement=connection.createStatement();			

		    if(statement.executeUpdate("update DayCash set trn_seq=1,ac_no="+at.getAccNo()+",attached='T',ve_user='"+at.uv.getVerId()+"', ve_tml='"+at.uv.getVerTml()+"',ve_date='"+at.uv.getVerDate()+"' where scroll_no='"+at.getRef_No()+"' and trn_date='"+at.getTransDate()+"'")==1)
		    	if(statement.executeUpdate("update ODCCTransaction set ve_tml='"+at.uv.getVerTml()+"',ve_user='"+at.uv.getVerId()+"',ve_date='"+at.uv.getVerDate()+"' where ac_type='"+at.getAccType()+"' and ac_no="+at.getAccNo())==1)
		    		if(statement.executeUpdate("update ODCCMaster set ve_tml='"+at.uv.getVerTml()+"',ve_user='"+at.uv.getVerId()+"',ve_date='"+at.uv.getVerDate()+"' where ac_type='"+at.getAccType()+"' and ac_no="+at.getAccNo())==1)
		    		{
		    			System.out.println("Inside odccmaster updated===");
		    		    if(statement.executeUpdate("update ODCCMasterLog set ve_tml='"+at.uv.getVerTml()+"',ve_user='"+at.uv.getVerId()+"',ve_date='"+at.uv.getVerDate()+"' where ac_no='"+at.getAccNo()+"' and ac_type='"+at.getAccType()+"'")==0)
		    				throw new SQLException("ODCCMasterLog not updated");
						rs=statement.executeQuery("select custtype from ODCCMaster,CustomerMaster where ac_no="+at.getAccNo()+" and ac_type='"+at.getAccType()+"' and  CustomerMaster.cid=ODCCMaster.cid");
			
						if(rs.next())
							scode=rs.getInt(1);
						rs.close();
							
						GLTransObject trn_obj[]=null;
						ResultSet rs_cash=null;
						Statement stmt=connection.createStatement() ;
						int i=0;
						rs_cash = stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1019001' and code=1");
						rs_cash.last();
						System.out.println("rows="+rs_cash.getRow());
						trn_obj=new GLTransObject[rs_cash.getRow()];
						rs_cash.beforeFirst();
						while(rs_cash.next()){
							trn_obj[i]=new GLTransObject();
							trn_obj[i].setGLCode(rs_cash.getString("gl_code"));
				            trn_obj[i].setTrnDate(at.getTransDate());
				            trn_obj[i].setGLType(rs_cash.getString("gl_type"));
				            trn_obj[i].setTrnMode("C");
				            trn_obj[i].setAmount(at.getTransAmount());
				            trn_obj[i].setCdind("D");
				            trn_obj[i].setAccType(at.getAccType());
							trn_obj[i].setAccNo(String.valueOf(at.getAccNo()));
				            trn_obj[i].setTrnSeq(1);//ship......20/12/2005
				            trn_obj[i].setTrnType(at.getTransType());
				            trn_obj[i].setRefNo(at.getRef_No());
							trn_obj[i].setVtml(at.uv.getVerTml());
							trn_obj[i].setVid(at.uv.getVerId());
							trn_obj[i].setVDate(at.uv.getVerDate());
							i++;
							if((commonlocalhome.create()).storeGLTransaction(trn_obj)==1)
				            {		
								System.out.println("===Store gltran has return 1");
								i=0;
								rs=statement.executeQuery("select gk.gl_code,gk.gl_type,gp.mult_by from GLKeyParam gk,GLPost gp where gk.ac_type="+at.getAccType()+" and gk.code="+(scode+1)+" and gp.trn_type='"+at.getTransType()+"' and gp.cr_dr='"+at.getCdInd()+"' and gk.ac_type=gp.ac_type and gp.gl_code=gk.gl_code");
								rs.last();
								trn_obj=new GLTransObject[rs.getRow()];
								rs.beforeFirst();
								//if(rs.next())
								while(rs.next())
								{	
									trn_obj[i]=new GLTransObject();
									trn_obj[i].setTrnDate(at.getTransDate());
									trn_obj[i].setGLType(rs.getString(2));
									trn_obj[i].setGLCode(String.valueOf(rs.getInt(1)));
									trn_obj[i].setTrnMode(at.getTransMode());							
									trn_obj[i].setAmount(at.getTransAmount()*rs.getInt(3));
									trn_obj[i].setCdind(at.getCdInd());
									trn_obj[i].setAccType(at.getAccType());
									trn_obj[i].setAccNo(String.valueOf(at.getAccNo()));
									trn_obj[i].setTrnSeq(at.getTransSeqNo());
									trn_obj[i].setTrnType(at.getTransType());
									trn_obj[i].setRefNo(at.getRef_No());
									trn_obj[i].setVtml(at.uv.getVerTml());
									trn_obj[i].setVid(at.uv.getVerId());
									trn_obj[i].setVDate(at.uv.getVerDate());
									i++;
								}
									commonlocal=commonlocalhome.create();
									result=(commonlocal.storeGLTransaction(trn_obj)==1)?1:0;
									System.out.println("result is========in bean======"+result);
									if(result==0)
										throw new SQLException();
								
						}
						else 
							throw new GLCodeNotDefinedException();
			    	}
		    	}
	
		}catch(SQLException sqlexception)
		{
			ctx.setRollbackOnly();
			sqlexception.printStackTrace();
			try{
				ctx.setRollbackOnly();
		
			}catch(Exception exception){exception.printStackTrace();}
		}
		finally{
			try{
				connection.close();
			}catch(Exception exception){exception.printStackTrace();}
			
		}

		return result;
	}
	
	/**
	 * This Method is used to get Interest rate for any Deposits.
	 * For Interest Calculation for OD.
	 * For OD any Deposit can be given as Surity, in that case to get Eligible amount for Interest Calculation
	 * 
	 * @param actype
	 * @param acno
	 * @return Deposit interest rate(double)
	 * @throws SQLException
	 */
	public double getDepositCurrentIntRate(String actype,int acno) throws SQLException
	{
		ResultSet rs=null;
		Connection con=null;
		
		try{
		con=getConnection();
		Statement stnew=con.createStatement();
		System.out.println("Type"+actype);
		System.out.println("No"+acno);
		rs=stnew.executeQuery("select (extra_lnint_rate+int_rate) from DepositMaster,DepositCategoryRate,CustomerMaster where  DepositCategoryRate.ac_type='"+actype+"' and category=sub_category and CustomerMaster.cid=DepositMaster.cid and  DepositMaster.ac_type='"+actype+"' and DepositMaster.ac_no="+acno +" and  DepositMaster.dep_days between days_fr and days_to");
		if(rs.next())
			return(rs.getDouble(1));

		}catch(Exception e){JOptionPane.showMessageDialog(null,"LoansOnDepositImpl.java(GetCurrentIntRate)"+e);}
		
		finally
		{
			con.close();
		}
		return(0);
	}
	
	/*public double getPygmyCurrentIntRate(String actype,String date,double amt_req) throws SQLException,RemoteException
	{
		ResultSet rs=null;
		Connection con=null;
		
		try{
		con=getConnection();
		Statement stnew=con.createStatement();
		
		rs=stnew.executeQuery("Select rate from LoanIntRate where ln_type='"+actype+"' and '"+Validations.convertYMD(date)+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)) and "+amt_req+" between  min_bal and  max_bal  order by concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))  ");
			if(rs.next())
			{
				System.out.println("Rate= "+rs.getDouble("rate"));
				return(rs.getDouble(1));
			}
		}catch(Exception e){JOptionPane.showMessageDialog(null,"LoansOnDepositImpl.java(GetPygmyCurrentIntRate)"+e);}
		
		finally
		{
			con.close();
		}
		return(0);
	}
*/
	
	public double getPygmyCurrentIntRate(String actype,int acno,String tdate,double amt_req) throws SQLException,RemoteException
	{
		ResultSet rs1=null,rs2=null;
		Connection con=null;
		String open_date="";
		try{
		con=getConnection();
		Statement stnew=con.createStatement();
		Statement stmnt=con.createStatement();
		System.out.println("actype="+actype);
		System.out.println("ac_no="+acno);
		System.out.println("tdate="+tdate);
		
		rs2 = stmnt.executeQuery("select open_date from PygmyMaster where ac_type='"+actype+"' and ac_no="+acno);
        rs2.next();
        open_date=rs2.getString(1);
		System.out.println("open date="+open_date);
		int months=Validations.noOfMonths(open_date,tdate);
		System.out.println("months="+months);
		rs1 = stnew.executeQuery("select int_rate from PygmyRate where '"+Validations.convertYMD(open_date)+"' between  concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1)) and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1)) and "+months+" between prd_fr and prd_to and ac_type='"+actype+"' ");
		
	    
	    double int_rate=0;
		if(rs1.next())
		{
			int_rate=rs1.getDouble("int_rate");
			System.out.println("Int Rate: "+rs1.getDouble("int_rate"));
			return(int_rate);
			
		}
		else{
			System.out.println("int rate not found");
			return 0;
		}
			
		}catch(Exception e){e.printStackTrace();JOptionPane.showMessageDialog(null,"LoansOnDepositImpl.java(GetPygmyCurrentIntRate)"+e);}
		
		finally
		{
			con.close();
		}
		return(0);
	}
	 
	
	/*public double getODCCIntRate(String ln_type,String fdate,int category,int period,double amt) 
	{
		double rate=0;		
		Connection connection=null;
		Statement statement=null;
		ResultSet rs=null;
		
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			fdate=Validations.convertYMD(fdate);
			System.out.println(fdate);
			
			rs=statement.executeQuery("select rate from LoanIntRate where ln_type='"+ln_type+"' and  "+amt+" between min_bal and max_bal and  '"+fdate+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
			if(rs.next())
				rate=rate+rs.getDouble("rate");
			System.out.println(rate);
			
			rs=statement.executeQuery("select rate from LoanPeriodRate where ln_type='"+ln_type+"' and "+period+" between fr_mon and to_mon and '"+fdate+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
			if(rs.next())
				rate=rate+rs.getDouble("rate");

			rs=statement.executeQuery("select rate from LoanCategoryRate where ln_type="+ln_type+" and category="+category+" and '"+fdate+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
			if(rs.next())
				rate=rate+rs.getDouble("rate");			
			
		}catch(Exception ex){ex.printStackTrace();}
		finally{
			try{
				connection.close();
			}catch(Exception exception){exception.printStackTrace();}
			
		}
		return rate;
	}*/
	
	
	public double getODCCIntRate(String ln_type,String fdate,String tdate,int category,int period,double amt,int ac_no) 
	{
		Connection connection=null;
		Statement statement=null;
		ResultSet rs=null,rs_purpose=null;
		int purpose_code=0;
		double rate=0;
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			for(int j=0;j<3;j++)
			{
				/*if(j==0)		
					rs=statement.executeQuery("select date_fr,date_to,rate from LoanIntRate where ln_type='"+ln_type+"' and  "+amt+" between min_bal and max_bal and  concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");*/
				if(j==0)
				{	
					// Code added on 03/06/2006
					rs_purpose=statement.executeQuery("select pps_code  from ODCCMaster where ac_type='"+ln_type+"' and ac_no="+ac_no+" ");
					if(rs_purpose.next())
						purpose_code=rs_purpose.getInt("pps_code");
					rs_purpose=statement.executeQuery("select  * from LoanIntRate where ln_type='"+ln_type+"' and  "+amt+" between min_bal and max_bal and  pps_code="+purpose_code+" and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
					if(!rs_purpose.next())
						purpose_code=0;
					// 
					rs=statement.executeQuery("select rate from LoanIntRate where ln_type='"+ln_type+"' and  "+amt+" between min_bal and max_bal and   pps_code="+purpose_code+" and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
					if(rs.next())
						rate=rate+rs.getDouble("rate");
					System.out.println("LoanIntRate="+rate);
				}
				else if(j==1)
				{
					rs=statement.executeQuery("select rate from LoanPeriodRate where ln_type='"+ln_type+"' and "+period+" between fr_mon and to_mon and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
					if(rs.next())
						rate=rate+rs.getDouble("rate");
					System.out.println("LoanPeriodRate="+rate);
				}
				else if(j==2)
				{
					rs=statement.executeQuery("select date_fr,date_to,rate from LoanCategoryRate where ln_type="+ln_type+" and category="+category+" and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
					if(rs.next())
						rate=rate+rs.getDouble("rate");
					System.out.println("LaonCategoryRate="+rate);
				}
	
			}
		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}
		System.out.println("rate----------------="+rate);
		return rate;
	}
	
	/*public double getODCCPenalRate(String ln_type)//dee  
	{
	    double penal_rate=0;		 
		Connection connection=null;
		Statement statement=null;
		ResultSet rs=null; 
		try
		{
		    connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

		    rs=statement.executeQuery("select penalty_rate from Modules where modulecode='"+ln_type+"' ");
		    if(	rs.next())
		        penal_rate=rs.getDouble("penalty_rate");
		    
		    System.out.println("module code"+ln_type);
		    System.out.println("penal_rate"+penal_rate);
		}catch(Exception ex){ex.printStackTrace();}		
		finally
		{
			try
			{
				connection.close();
			}catch(Exception exception){exception.printStackTrace();}			
		}
		return penal_rate;
	}*/
	
	
	/**
	 * To get All details of a Deposit
	 * @param actype
	 * @param no
	 * @param curdate
	 * @return DepositMasterObject
	 * @throws SQLException
	 */
	public DepositMasterObject getDepositMaster(String actype,int no,String curdate) throws SQLException
	{
		DepositMasterObject depositmasterobject=null;	
		Connection con=null;
		ResultSet rs=null;
		String sub_modcode=actype.substring(1,4);

		try
		{
			con=getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			Statement stmt1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			if( sub_modcode.equals("003") || sub_modcode.equals("004") || sub_modcode.equals("005") )
				rs=stmt.executeQuery("select * from DepositMaster where ac_type='"+actype+"' and ac_no="+no+"");
			else if(sub_modcode.equals("006"))
			    rs=stmt.executeQuery("select pm.*,pt.cl_bal,m.min_period from PygmyMaster pm left join PygmyTransaction pt on (pm.ac_type=pt.ac_type and pm.ac_no=pt.ac_no and pm.lst_trn_seq=pt.trn_seq) left join Modules m on (m.modulecode=pm.ac_type) where pm.ac_type='"+actype+"'  and pm.ac_no="+no+"");
			    
				//rs=stmt.executeQuery("select pm.*,pt.cl_bal,m.min_period from PygmyMaster pm , PygmyTransaction pt,Modules m where pm.ac_type='"+actype+"' and pm.ac_type=m.modulecode and pm.ac_no="+no+"  and pm.lst_trn_seq=pt.trn_seq  and pm.ac_type=pt.ac_type and pm.ac_no=pt.ac_no");
			
			if(sub_modcode.equals("003") || sub_modcode.equals("004") || sub_modcode.equals("005"))
			{
				if(rs.next())
				{

					depositmasterobject=new DepositMasterObject();
					if(rs.getString("ve_user")==null) 
						depositmasterobject.setVerified("F");
					else
						depositmasterobject.setVerified("T");

					depositmasterobject.setClosedt(rs.getString("close_date"));
					depositmasterobject.setMaturityDate(rs.getString("mat_date"));
					
					depositmasterobject.setAccType(actype);
					depositmasterobject.setAccNo(no);
					depositmasterobject.setCustomerId(rs.getInt("cid"));
					depositmasterobject.setMaturityAmt(rs.getDouble("mat_amt"));
					depositmasterobject.setDepositAmt(rs.getDouble("dep_amt"));
					depositmasterobject.setInterestRate(rs.getDouble("int_rate"));
					
					depositmasterobject.setCumInterest(getDepositCurrentIntRate(actype,no));
					
					depositmasterobject.setDepositDays(rs.getInt("dep_days"));
					depositmasterobject.setMaturityDate(rs.getString("mat_date"));
					depositmasterobject.setDepDate(rs.getString("dep_date"));
					depositmasterobject.setLoanAvailed(rs.getString("ln_availed"));

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
					
					depositmasterobject.setClosedt(rs.getString("close_date"));
					depositmasterobject.setCloseStart(rs.getString("status"));
					depositmasterobject.setMaturityDate(Validations.addNoOfMonths(rs.getString("open_date"),12));
					depositmasterobject.setDepositDays(rs.getInt("min_period"));
										
					ResultSet rs_rate=stmt1.executeQuery("select int_rate from PygmyRate where '"+Validations.convertYMD(curdate)+"' between concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1)) and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1)) and "+Validations.noOfMonths(rs.getString("open_date"),curdate)+" between prd_fr and prd_to and ac_type='"+actype+"'");					
					if(rs_rate.next())
						depositmasterobject.setInterestRate(rs_rate.getDouble("int_rate"));
					else
						depositmasterobject.setInterestRate(0);
					
					depositmasterobject.setAccType(actype);
					depositmasterobject.setAccNo(no);
					depositmasterobject.setCustomerId(rs.getInt("pm.cid"));
					depositmasterobject.setDepositAmt(rs.getDouble("pt.cl_bal"));
					depositmasterobject.setDepDate(rs.getString("pm.open_date"));
					depositmasterobject.setLoanAvailed(rs.getString("ln_avld"));
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

	/**
	 * Method is used to Sanction ODCC Loan.
	 * Tables updated is ODCCMaster i.e, sanc_amt,creditlimit,sanc_dt.......
	 * @param lnobj
	 * @param priority
	 * @param weaker
	 * @param sysdate
	 * @param type
	 * @return 0==> if successfully inserted; -1 ==> if not inserted
	 * @throws NumberFormatException
	 * @throws DateFormatException
	 */
	public int sanctionODCC(ODCCMasterObject lnobj,boolean priority,boolean weaker,String sysdate, int type) throws NumberFormatException, DateFormatException
	{
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if(type==0)
			{
			    if(statement.executeUpdate("update ODCCMaster set sanc_amt="+lnobj.getCreditLimit()+",creditlimit="+lnobj.getCreditLimit()+",sanc_dt='"+sysdate+"',int_rate="+lnobj.getInterestRate()+",loan_sanc='Y',wk_sect='"+(weaker==true?1:0)+"',psect_cd='"+(priority==true?1:0)+"',limit_upto='"+Validations.addNoOfMonths(sysdate,Integer.parseInt(lnobj.getLimitUpto()))+"',period="+Integer.parseInt(lnobj.getLimitUpto())+" where ac_type='"+lnobj.getModuleCode()+"' and ac_no="+lnobj.getAccNo())==1)
			        return 1;
			    throw new SQLException();
			}
			if(type==1)
			{
			    if(statement.executeUpdate("update ODCCMaster set sanc_amt=0,creditlimit=0,sanc_dt=NULL,int_rate=0,loan_sanc='N',wk_sect=NULL,psect_cd=NULL,limit_upto=NULL,period=0 where ac_type='"+lnobj.getModuleCode()+"' and ac_no="+lnobj.getAccNo())==1)
			        return 1;
			    throw new SQLException();
			}
			//Added on 14/03/2006 for Checking Cancellation
			if(type==2)
			{
			    if(statement.executeUpdate("update ODCCMaster set sanc_amt=0,creditlimit=0,sanc_dt=NULL,int_rate=0,loan_sanc='Y',wk_sect=NULL,psect_cd=NULL,limit_upto=NULL,period=0,ac_closedate='"+sysdate+"' where ac_type='"+lnobj.getModuleCode()+"' and ac_no="+lnobj.getAccNo())==1)
			        return 1;
			    throw new SQLException();
			}
			
			
		}catch(SQLException ex){
			ex.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally{
			try{
				connection.close();
			}catch(Exception exception){exception.printStackTrace();}
			
		}
			return 0;
	}	
	

	public int verifyODCCSanction(ODCCMasterObject lnobj,int type) 
	{		
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			if(statement.executeUpdate("update ODCCMaster set sanc_ver='Y' where ac_type='"+lnobj.getModuleCode()+"' and ac_no="+lnobj.getAccNo())==1)
			{
				if(lnobj.getDeposits()!=null)
				{	
					Vector vector_deposits=lnobj.getDeposits();
						
					Enumeration en=vector_deposits.elements();
					while(en.hasMoreElements())
					{
						String str=en.nextElement().toString();
						if(str.substring(0,str.indexOf(" ")).startsWith("1006"))
						{
						    if(type==0)
						    {
						        if(statement.executeUpdate("update PygmyMaster set ln_avld='T',ln_ac_type='"+lnobj.getModuleCode()+"',ln_ac_no="+lnobj.getAccNo()+" where ac_type='"+str.substring(0,str.indexOf(" "))+"' and ac_no="+Integer.parseInt(str.substring(str.indexOf(" ")).trim()))!=1)
						            throw new SQLException();
						    }
						    else
						    {
						        if(statement.executeUpdate("update PygmyMaster set ln_avld='F',ln_ac_type=null,ln_ac_no=0 where ac_type='"+str.substring(0,str.indexOf(" "))+"' and ac_no="+Integer.parseInt(str.substring(str.indexOf(" ")).trim()))!=1)
						            throw new SQLException();
						    }
						}
						else
						{
						    if(type==0)
						    {
						        if(statement.executeUpdate("update DepositMaster set ln_availed='T',ln_ac_type='"+lnobj.getModuleCode()+"',ln_ac_no="+lnobj.getAccNo()+" where ac_type='"+str.substring(0,str.indexOf(" "))+"' and ac_no="+Integer.parseInt(str.substring(str.indexOf(" ")).trim()))!=1)
						        	throw new SQLException();
							}
							else
							{
								if(statement.executeUpdate("update DepositMaster set ln_availed='F',ln_ac_type=null,ln_ac_no=0 where ac_type='"+str.substring(0,str.indexOf(" "))+"' and ac_no="+Integer.parseInt(str.substring(str.indexOf(" ")).trim()))!=1)
									throw new SQLException();							        
							}
						}
					}
				}
				
				/** Added on 12/08/06 */
				/*if(type==0)
				{						
					ResultSet rs_code=null;
					PreparedStatement pstmt=connection.prepareStatement("insert into ODInterestDetails values('"+lnobj.getModuleCode()+"',"+lnobj.getAccType()+",?,0,0,0,0,0,0,null,null)");
					
					if(lnobj.getGoldDet()!=null)
					{	
						rs_code=statement.executeQuery("select code from SecurityDetails where modulecode='"+lnobj.getModuleCode()+"' and type_of_security='Gold'");
						pstmt.setInt(1,rs_code.getInt("code"));												
						pstmt.addBatch();						
					}
					if(lnobj.getPropertyDetails()!=null)
					{	
						rs_code=statement.executeQuery("select code from SecurityDetails where modulecode='"+lnobj.getModuleCode()+"' and type_of_security='Property'");
						pstmt.setInt(1,rs_code.getInt("code"));													
						pstmt.addBatch();						
					}
					if(lnobj.getVehicleDet()!=null)
					{	
						rs_code=statement.executeQuery("select code from SecurityDetails where modulecode='"+lnobj.getModuleCode()+"' and type_of_security='Vehicle'");
						pstmt.setInt(1,rs_code.getInt("code"));													
						pstmt.addBatch();						
					}
					if(lnobj.getIncomeDetails()!=null)
					{	
						rs_code=statement.executeQuery("select code from SecurityDetails where modulecode='"+lnobj.getModuleCode()+"' and type_of_security='Vehicle'");
						pstmt.setInt(1,rs_code.getInt("code"));													
						pstmt.addBatch();						
					}					
					
					System.out.println("-----------ODINTEREST DETAILS---------------"+pstmt.getMaxRows());
					
					pstmt.executeBatch();
				}*/			
				
				if(lnobj.getAccType().startsWith("1014") && type==0)
				{				    
					ResultSet rs=statement.executeQuery("select inspection_period from Modules where modulecode='"+lnobj.getAccType()+"'");
					PreparedStatement ps=connection.prepareStatement("insert into StockInspectionDetails values(?,?,?,?,?,?,?,?,?)");

					ps.setString(1,lnobj.getAccType());
					ps.setInt(2,lnobj.getAccNo());
					//ps.setString(3,getSysDate());
					ps.setString(3,lnobj.getTransDate());
					ps.setDouble(4,lnobj.getStockValue());
					ps.setDouble(5,lnobj.getCreditLimit());					
					if(rs.next())
						ps.setString(6,Validations.addNoOfMonths(lnobj.getTransDate(),rs.getInt("inspection_period")));
					
					ps.setString(7,lnobj.uv.getVerId());
					ps.setString(8,lnobj.uv.getVerTml());
					ps.setString(9,lnobj.uv.getVerDate());
					
					if(ps.executeUpdate()!=1)
						throw new SQLException("StockInspectionDetails not inserted");
				}
				if(type==1)
				{
				    if(statement.executeUpdate("update ODCCMaster set ac_status='C' where ac_type='"+lnobj.getModuleCode()+"' and ac_no="+lnobj.getAccNo())!=1)
						throw new SQLException();
				    if(statement.executeUpdate("update NomineeMaster set to_date='"+lnobj.getTransDate()+"' where ac_type='"+lnobj.getModuleCode()+"' and ac_no="+lnobj.getAccNo())!=1)
						throw new SQLException();				    
				    if(statement.executeUpdate("update ODInterestDetails set to_date='"+lnobj.getTransDate()+"' where ac_type='"+lnobj.getModuleCode()+"' and ac_no="+lnobj.getAccNo())!=1)
						throw new SQLException();
				}			
				return 1;
			}		
			throw new SQLException();
		}catch(SQLException ex){
			ex.printStackTrace();
			ctx.setRollbackOnly();
		} catch (DateFormatException e) {
			ctx.setRollbackOnly();
			e.printStackTrace();
		}
		finally{
			try{
				connection.close();
			}catch(Exception exception){exception.printStackTrace();}			
		}
		return 0;
	}
	
	/**
	 * Added by Deepa on 20/04/06
	 * @param odccmasterobject
	 * @param type
	 * type==0 ---> ODCCMaster
	 * type==1 ---> CoBorrower
	 * type==2 ---> Surity
	 * type==3 ---> Signature details
	 * type==4 ---> Income/Employment
	 * type==5 ---> Relative
	 * type==6 ---> Property
	 * type==7 ---> Gold Details
	 * type==8 ---> Deposit
	 * type==9 ---> Vehicle
	 * @return Account Number
	 * @throws CreateException
	 * @throws DateFormatException
	 */
	public int updateODCCMaster(ODCCMasterObject odccmasterobject,int type ) throws  CreateException, DateFormatException
	{		
		System.out.println("INSIDE Bean updateODCCMaster");
		Connection connection=null;
		Statement statement=null;
		//ResultSet rs=null,rs1=null,rs2=null;
		PreparedStatement pstmt_tables=null;
		int no=0;
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);				
			commonlocal=commonlocalhome.create();
		       
		    no=odccmasterobject.getAccNo();		    
		    System.out.println("Ac No="+no+" "+"TYPE: "+type);
		    if(no>0)	
		    {		    	
		    	if(type==0)
		    	{ 		    	
		    		System.out.println("Inserting into ODCCMaster...");		    		
		    		PreparedStatement pstmt=connection.prepareStatement("update ODCCMaster set ch_bk_issue=?,appn_srl=?,appn_date=?,req_amt=?,pps_code=?,ps_bk_seq=?,ac_status=?,default_ind=?,freeze_ind=?,ac_opendate=?,ac_closedate=?,int_rate_type=?,psect_cd=?,wk_sect=?,rel=?,dir_code=?,limit_upto=?,period=? where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+no+" ");
			    		    		
		    		pstmt.setString(1,odccmasterobject.getChqBKIssue());//ch_bk_iss		    		    		
		    		pstmt.setInt(2,odccmasterobject.getApplicationSrlNo());//ln appn srl number
		    		pstmt.setString(3,odccmasterobject.getApplicationDate());//ln appn date
		    		pstmt.setDouble(4,odccmasterobject.getRequiredAmount());//req amt
		    		pstmt.setInt(5,odccmasterobject.getPurposeCode());//purpose code*/ 
		    		pstmt.setInt(6,odccmasterobject.getPassBookSeq());//pass bk seq		    				    		
		    		pstmt.setString(7,odccmasterobject.getAccountStatus());//ac status
		    		pstmt.setString(8,odccmasterobject.getDefaultInd());//def ind
		    		pstmt.setString(9,odccmasterobject.getFreezeInd());//freeze ind		    		
		    		pstmt.setString(10,odccmasterobject.getAccOpenDate());//ac  open date
		    		pstmt.setString(11,odccmasterobject.getClosedt());//ac  close date		    		
		    		pstmt.setInt(12,odccmasterobject.getInterestRateType());//int rate type		    		
		    		pstmt.setBoolean(13,odccmasterobject.isPrioritySector());//psect _cd
		    		pstmt.setBoolean(14,odccmasterobject.isWeakerSection());//weeker section				
		    		pstmt.setString(15,odccmasterobject.getRelative());//relation
		    		pstmt.setInt(16,odccmasterobject.getDirectorCode());//dir code	    		
		    		pstmt.setString(17,null);//limit upto
		    		pstmt.setString(18,odccmasterobject.getLimitUpto());//period				
		    		
		    		if(pstmt.executeUpdate()==1)		    		
		    			statement.executeUpdate("insert into ODCCMasterLog select * from ODCCMaster where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+(no)+"");		    		
		    	}
		    	else
		    	{
		    		statement.executeUpdate("delete from SignatureInstruction where ac_no="+no+" and ac_type='"+odccmasterobject.getModuleCode()+"' ");
		    		boolean id=commonlocal.storeSignatureDetails(odccmasterobject.getSigObj(),(no));
		    		System.out.println("stored signature details.. "+id);
		    		
					if(type==1 || type==3)
					{
						statement.addBatch("delete from BorrowerMaster where ln_ac_no="+no+" and ln_ac_type='"+odccmasterobject.getModuleCode()+"' and type='C'");
						statement.addBatch("update ODCCMaster set no_coborrowers="+odccmasterobject.getNoOfCoBorrowers()+" where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+no+" ");
						statement.executeBatch();
					}
					else if(type==2)
					{
						statement.addBatch("delete from BorrowerMaster where ln_ac_no="+no+" and ln_ac_type='"+odccmasterobject.getModuleCode()+"' and type='S'");
						statement.addBatch("update ODCCMaster set no_surities="+odccmasterobject.getNoOfSurities()+" where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+no+" ");
						statement.executeBatch();
					}					
					else if(type==4)
						statement.executeUpdate("delete from EmploymentMaster where ac_no="+no+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
					
					else if(type==5)					
						statement.executeUpdate("delete from RelativeMaster where ac_no="+no+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
					
					else if(type==6)					
						statement.executeUpdate("delete from PropertyMaster where ac_no="+no+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
					
					else if(type==7)					
						statement.executeUpdate("delete from GoldDetMaster where ac_no="+no+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
					
					else if(type==8)
					{
						statement.addBatch("delete from BorrowerMaster where ln_ac_no="+no+" and ln_ac_type='"+odccmasterobject.getModuleCode()+"' and type='D'");
		    			statement.addBatch("update PygmyMaster set ln_avld='F',ln_ac_type=null,ln_ac_no=0 where ln_ac_type='"+odccmasterobject.getModuleCode()+"' and ln_ac_no="+no);
		    			statement.addBatch("update DepositMaster set ln_availed ='F',ln_ac_type=null,ln_ac_no=0 where ln_ac_type='"+odccmasterobject.getModuleCode()+"' and ln_ac_no="+no);
		    			statement.addBatch("update ODInterestDetails set to_date='"+odccmasterobject.getTransDate()+"'where ac_no="+no+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
		    			statement.executeBatch();
					}
					else if(type==9)
						statement.executeUpdate("delete from VehicleMaster where ac_no="+no+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
					
		    		if(odccmasterobject.getDeposits()!=null && type==8)
		    		{	
		    			System.out.println("DEPOSITS");			    
		    			pstmt_tables=connection.prepareStatement("insert into BorrowerMaster values(?,?,?,?,?,?)");
						
		    			Vector  v=odccmasterobject.getDeposits();
		    			Enumeration en=v.elements();   
		    			while(en.hasMoreElements())
		    			{		    				
		    				pstmt_tables.setString(1,String.valueOf(odccmasterobject.getModuleCode()));
		    				pstmt_tables.setInt(2,no);
		    				pstmt_tables.setString(3,"D");
		    				String str=en.nextElement().toString();
		    				pstmt_tables.setString(4,str.substring(0,str.indexOf(" ")));
		    				pstmt_tables.setInt(5,Integer.parseInt(str.substring(str.indexOf(" ")).trim()));
		    				pstmt_tables.setString(6,null);
		    				pstmt_tables.addBatch();				
		    			}		    				
		    			if(pstmt_tables.executeBatch().length==v.size())	
		    			{
		    				statement.executeUpdate("insert into BorrowerMasterLog select * from BorrowerMaster where ln_ac_type='"+odccmasterobject.getModuleCode()+"' and ln_ac_no="+(no)+" and type='D'");		
		    				
		    				pstmt_tables=connection.prepareStatement("insert into ODInterestDetails values(?,?,?,?,0,0,0,0,0,null,null)");	
				
		    				Vector vector_deposits=odccmasterobject.getDeposits();						
		    				Enumeration enu=vector_deposits.elements();
		    				while(enu.hasMoreElements())
		    				{
		    					pstmt_tables.setString(1,String.valueOf(odccmasterobject.getModuleCode()));
		    					pstmt_tables.setInt(2,no);							
		    					String str=enu.nextElement().toString();
		    					pstmt_tables.setString(3,str.substring(0,str.indexOf(" ")));
		    					pstmt_tables.setInt(4,Integer.parseInt(str.substring(str.indexOf(" ")).trim()));
		    					pstmt_tables.addBatch();
		    					if(str.substring(0,str.indexOf(" ")).startsWith("1006"))
		    						statement.executeUpdate("update PygmyMaster set ln_avld='T',ln_ac_type='"+odccmasterobject.getModuleCode()+"',ln_ac_no="+no+" where ac_type='"+str.substring(0,str.indexOf(" "))+"' and ac_no="+Integer.parseInt(str.substring(str.indexOf(" ")).trim()));
		    					else
		    						statement.executeUpdate("update DepositMaster set ln_availed ='T',ln_ac_type='"+odccmasterobject.getModuleCode()+"',ln_ac_no="+no+" where ac_type='"+str.substring(0,str.indexOf(" "))+"' and ac_no="+Integer.parseInt(str.substring(str.indexOf(" ")).trim()));		    																
		    				}
		    				pstmt_tables.executeBatch();						
		    			}
		    		}
			
		    		/**	CoBorrowers and Surities Details */
		    		if(odccmasterobject.getNoOfCoBorrowers()>0 || odccmasterobject.getNoOfSurities()>0)
		    		{	
		    			if(odccmasterobject.getNoOfCoBorrowers()>0 && (type==1||type==3))
		    			{		    										
		    				pstmt_tables=connection.prepareStatement("insert into BorrowerMaster values(?,?,?,?,?,?)");
					
		    				Vector  v=odccmasterobject.getCoBorrowers();
		    				Enumeration en=v.elements();
		    				while(en.hasMoreElements())
		    				{
		    					System.out.println("At 1238------> "+v.size());
		    					pstmt_tables.setString(1,String.valueOf(odccmasterobject.getModuleCode()));
		    					pstmt_tables.setInt(2,no);
		    					pstmt_tables.setString(3,"C");
		    					String str=en.nextElement().toString();
		    					pstmt_tables.setString(4,str.substring(0,str.indexOf(" ")));
		    					pstmt_tables.setInt(5,Integer.parseInt(str.substring(str.indexOf(" ")).trim()));
		    					pstmt_tables.setString(6,null);
		    					pstmt_tables.addBatch();				
		    				}
		    				if(pstmt_tables.executeBatch().length==v.size())						
		    				{
		    					statement.executeUpdate("insert into BorrowerMasterLog select * from BorrowerMaster where ln_ac_type='"+odccmasterobject.getModuleCode()+"' and ln_ac_no="+(no)+" and type='C'");		    					
		    					statement.executeUpdate("insert into SignatureInstructionLog select * from SignatureInstruction where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+(no)+" and cid!="+odccmasterobject.getCustomerId()+"");
		    				}
		    			}
					
		    			if(odccmasterobject.getNoOfSurities()>0 && type==2)
		    			{		    										
		    				pstmt_tables=connection.prepareStatement("insert into BorrowerMaster values(?,?,?,?,?,?)");
					
		    				Vector  v=odccmasterobject.getSurities();
		    				Enumeration en=v.elements();
		    				while(en.hasMoreElements())
		    				{
		    					pstmt_tables.setString(1,String.valueOf(odccmasterobject.getModuleCode()));
		    					pstmt_tables.setInt(2,no);
		    					pstmt_tables.setString(3,"S");
		    					String str=en.nextElement().toString();
		    					pstmt_tables.setString(4,str.substring(0,str.indexOf("%%%")));
		    					pstmt_tables.setInt(5,Integer.parseInt(str.substring(str.indexOf(" ")).trim()));
		    					pstmt_tables.setString(6,null);
		    					pstmt_tables.addBatch();				
		    				}
		    				if(pstmt_tables.executeBatch().length==v.size())					
		    					statement.executeUpdate("insert into BorrowerMasterLog select * from BorrowerMaster where ln_ac_type='"+odccmasterobject.getModuleCode()+"' and ln_ac_no="+(no)+" and type='S'");						
		    			}													
		    		}
			
		    		/** Dependents Details */
		    		if( (odccmasterobject.getDependents()!=null || odccmasterobject.getInterests()!=null || odccmasterobject.getRelatives()!=null) && type==5)
		    		{
		    			pstmt_tables=connection.prepareStatement("insert into RelativeMaster values(?,?,?,?,?,?,?,?,?)");		
		    			if(odccmasterobject.getDependents()!=null?odccmasterobject.getRelatives().length>0:false)
		    			{					
		    				Object obj[][]=odccmasterobject.getRelatives();
		    				for(int i=0;i<obj.length;i++)
		    				{
		    					pstmt_tables.setString(1,String.valueOf(odccmasterobject.getModuleCode()));
		    					pstmt_tables.setInt(2,no);
		    					pstmt_tables.setString(3,"Relatives");
		    					pstmt_tables.setString(4,obj[i][0].toString());
		    					pstmt_tables.setString(5,obj[i][1].toString());
		    					pstmt_tables.setString(6,obj[i][3].toString());
		    					pstmt_tables.setString(7,obj[i][4].toString());
		    					pstmt_tables.setString(8,obj[i][5].toString());
		    					pstmt_tables.setString(9,obj[i][2].toString());
		    					pstmt_tables.addBatch();				
		    				}
		    				pstmt_tables.executeBatch();					
		    			}
		    			if(odccmasterobject.getInterests()!=null?odccmasterobject.getDependents().length>0:false)
		    			{					
		    				Object obj[][]=odccmasterobject.getDependents();
		    				for(int i=0;i<obj.length;i++)
		    				{
		    					pstmt_tables.setString(1,String.valueOf(odccmasterobject.getModuleCode()));
		    					pstmt_tables.setInt(2,no);
		    					pstmt_tables.setString(3,"Dependents");
		    					pstmt_tables.setString(4,obj[i][0].toString());
		    					pstmt_tables.setString(5,obj[i][1].toString());
		    					pstmt_tables.setString(6,obj[i][2].toString());
		    					pstmt_tables.setString(7,null);
		    					pstmt_tables.setString(8,null);
		    					pstmt_tables.setString(9,obj[i][3].toString());
		    					pstmt_tables.addBatch();				
		    				}
		    				pstmt_tables.executeBatch();					
		    			}
		    			if(odccmasterobject.getRelatives()!=null?odccmasterobject.getInterests().length>0:false)
		    			{					
		    				Object obj[][]=odccmasterobject.getInterests();
		    				for(int i=0;i<obj.length;i++)
		    				{
		    					pstmt_tables.setString(1,String.valueOf(odccmasterobject.getModuleCode()));
		    					pstmt_tables.setInt(2,no);
		    					pstmt_tables.setString(3,"Interests");
		    					pstmt_tables.setString(4,obj[i][0].toString());
		    					pstmt_tables.setString(5,obj[i][1].toString());
		    					pstmt_tables.setString(6,obj[i][2].toString());
		    					pstmt_tables.setString(7,obj[i][3].toString());
		    					pstmt_tables.setString(8,obj[i][4].toString());
		    					pstmt_tables.setString(9,null);
		    					pstmt_tables.addBatch();				
		    				}
		    				pstmt_tables.executeBatch();					
		    			}				
		    			statement.executeUpdate("insert into RelativeMasterLog select * from RelativeMaster where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+(no)+" ");
		    		}			
		    		
		    		/** Income Details */
		    		if(odccmasterobject.getIncomeDetails()!=null && odccmasterobject.getIncomeDetails().length>0 && type==4)
		    		{	
		    			pstmt_tables=connection.prepareStatement("insert into EmploymentMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						
		    			IncomeObject in[]=odccmasterobject.getIncomeDetails();		
		    			for(int i=0;i<odccmasterobject.getIncomeDetails().length;i++)	
		    			{
		    				if(in[i]!=null)	
		    				{
		    					pstmt_tables.setInt(1,no);				
		    					pstmt_tables.setString(2,String.valueOf(odccmasterobject.getModuleCode()));
		    					pstmt_tables.setString(3,in[i].getTypeOfIncome());
		    					pstmt_tables.setString(4,null);
		    					pstmt_tables.setString(5,null);				
		    					pstmt_tables.setString(6,null);				
		    					pstmt_tables.setString(7,in[i].getAddress());
		    					pstmt_tables.setString(8,in[i].getStringPhNo());		    										
		    					pstmt_tables.setString(9,null);				
		    					pstmt_tables.setString(10,null);				
		    					pstmt_tables.setString(11,null);				
		    					pstmt_tables.setInt(12,in[i].getService());
		    					pstmt_tables.setString(13,null);				
		    					pstmt_tables.setString(14,null);				
		    					pstmt_tables.setString(15,null);				
		    					pstmt_tables.setDouble(16,in[i].getIncome());				
		    					pstmt_tables.setDouble(17,0);					
		    					pstmt_tables.setString(18,null);				
		    					pstmt_tables.setString(19,null);				
		    					pstmt_tables.setInt(20,0);				
		    					pstmt_tables.setDouble(21,0);
		    					pstmt_tables.setString(22,null);
		    					pstmt_tables.setString(23,null);
		    					pstmt_tables.setDouble(24,0);
		    					pstmt_tables.setDouble(25,in[i].getNetIncome());
		    					if(i==0)
		    					{
		    						pstmt_tables.setString(5,in[i].getName());	
		    						pstmt_tables.setDouble(17,in[i].getExpenditure());
		    					}
		    					if(i==1)
		    					{					   
		    						pstmt_tables.setString(5,in[i].getName());
		    						pstmt_tables.setString(6,in[i].getName());
		    						pstmt_tables.setString(9,in[i].getEmpNo());			
		    						pstmt_tables.setString(10,in[i].getDesignation());				
		    						pstmt_tables.setString(11,in[i].getDept());			
		    						pstmt_tables.setString(13,(in[i].isConfirmed()?"T":"F"));				
		    						pstmt_tables.setString(14,(in[i].isTransferable()?"T":"F"));				
		    						pstmt_tables.setString(15,(in[i].isCertificateEnclosed()?"T":"F"));
		    						pstmt_tables.setDouble(17,in[i].getExpenditure());
		    					}	
		    					if(i==2)
		    					{						
		    						pstmt_tables.setString(5,in[i].getNature());			
		    						pstmt_tables.setString(4,in[i].getName());
		    						pstmt_tables.setDouble(17,in[i].getExpenditure());				
		    						pstmt_tables.setDouble(21,in[i].getStockValue());
		    						pstmt_tables.setString(22,in[i].getGoodsCondition());
		    						pstmt_tables.setString(23,in[i].getTypeOfGoods());
		    						pstmt_tables.setDouble(24,in[i].getTurnOver());
		    					}
		    					if(i==3)
		    					{
		    						pstmt_tables.setString(6,in[i].getName());			
		    						pstmt_tables.setString(18,in[i].getBankName());				
		    						pstmt_tables.setString(19,in[i].getAccType());				
		    						pstmt_tables.setInt(20,Integer.parseInt(in[i].getAccNo()));						
		    					}
		    					if(i==4)
		    						pstmt_tables.setDouble(17,in[i].getExpenditure());				
						
		    					pstmt_tables.addBatch();		   
		    				}
		    			}	
		    			if(pstmt_tables.executeBatch().length!=0)
		    			{
		    				statement.executeUpdate("insert into EmploymentMasterLog select * from EmploymentMaster where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+(no)+" ");
		    				if(odccmasterobject.isSanctionVerified())
		    					statement.executeUpdate("update StockInspectionDetails set stock_value='"+in[2].getStockValue()+"' where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+(no)+" ");
		    			}
		    		}			

		    		/** Property Details */
		    		if(odccmasterobject.getPropertyDetails()!=null && type==6)
		    		{		    			
		    			PropertyObject po=odccmasterobject.getPropertyDetails();		    			
		    			pstmt_tables=connection.prepareStatement("insert into PropertyMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");		
		    			Object data[][]=po.getTenantsDetail();
		    			for(int i=0;i<((data!=null)?data.length:1);i++)
		    			{
		    				pstmt_tables.setInt(1,no);				
		    				pstmt_tables.setString(2,String.valueOf(odccmasterobject.getModuleCode()));
		    				pstmt_tables.setString(3,po.getPropertyNo());
		    				pstmt_tables.setString(4,po.getAddress());
		    				pstmt_tables.setString(5,po.getMeasurementEW());
		    				pstmt_tables.setString(6,po.getMeasurementNS());
		    				pstmt_tables.setString(7,po.getEastBy());
		    				pstmt_tables.setString(8,po.getWestBy());
		    				pstmt_tables.setString(9,po.getNorthBy());
		    				pstmt_tables.setString(10,po.getSouthBy());
		    				pstmt_tables.setDouble(11,po.getPropertyValue());
		    				pstmt_tables.setString(12,po.getPropertyAqdBy());
		    				pstmt_tables.setString(13,po.getPropertyNature());
			
		    				if(data!=null)
		    				{
		    					pstmt_tables.setString(14,data[i][0].toString());
		    					pstmt_tables.setDouble(15,Double.parseDouble(data[i][1].toString()));
		    					pstmt_tables.setString(16,data[i][2].toString());
		    				}
		    				else
		    				{
		    					pstmt_tables.setString(14,null);
		    					pstmt_tables.setDouble(15,0);
		    					pstmt_tables.setString(16,null);
		    				}
		    				pstmt_tables.addBatch();
		    			}
		    			if(pstmt_tables.executeBatch().length>0)
		    				statement.executeUpdate("insert into PropertyMasterLog select * from PropertyMaster where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+(no)+" ");
		    		}
				
		    		/** Gold Details */
		    		if(odccmasterobject.getGoldDet()!=null && type==7)
		    		{		    			
		    			GoldObject gobj=odccmasterobject.getGoldDet();
		    			pstmt_tables=connection.prepareStatement("insert into GoldDetMaster values(?,?,?,?,?,?,?,?,?,?)");		
		    			Object data[][]=gobj.getGoldDet();
		    			for(int i=0;i<((data!=null)?data.length:1);i++)
		    			{
		    				pstmt_tables.setInt(1,no);				
		    				pstmt_tables.setString(2,String.valueOf(odccmasterobject.getModuleCode()));
		    				pstmt_tables.setInt(3,gobj.getApprisersCode());
		    				if(data!=null)
		    				{
		    					pstmt_tables.setDouble(4,Double.parseDouble(data[i][0].toString()));		    					
		    					pstmt_tables.setString(5,data[i][1].toString());		    					
		    					pstmt_tables.setDouble(6,Double.parseDouble(data[i][2].toString()));		    					
		    					pstmt_tables.setDouble(7,Double.parseDouble(data[i][3].toString()));		    					
		    					pstmt_tables.setDouble(8,Double.parseDouble(data[i][5].toString()));		    											
		    				}
		    				else
		    				{
		    					pstmt_tables.setInt(4,0);
		    					pstmt_tables.setString(5,null);
		    					pstmt_tables.setInt(6,0);
		    					pstmt_tables.setInt(7,0);
		    					pstmt_tables.setDouble(8,0.00);
		    				}
		    				pstmt_tables.setString(9,gobj.getApprisalDate());
		    				pstmt_tables.setString(10,gobj.getApprisalTime());
		    				pstmt_tables.addBatch();
		    			}
		    			if(pstmt_tables.executeBatch().length!=0)
		    				statement.executeUpdate("insert into GoldDetMasterLog select * from GoldDetMaster where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+(no)+" ");
		    		}
				
		    		/** Vehicle Details */
		    		if(odccmasterobject.getVehicleDet()!=null && type==9)
		    		{	
		    			
		    			VehicleObject vobj=odccmasterobject.getVehicleDet();
		    			pstmt_tables=connection.prepareStatement("insert into VehicleMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");		
		    			pstmt_tables.setInt(2,no);				
		    			pstmt_tables.setString(1,String.valueOf(odccmasterobject.getModuleCode()));
		    			pstmt_tables.setString(3,vobj.getVehicleMake());
		    			pstmt_tables.setDouble(4,vobj.getVehicleCost());
		    			pstmt_tables.setString(5,vobj.getAddressDealer());
		    			pstmt_tables.setString(6,vobj.getLicenceNo());
		    			pstmt_tables.setString(7,vobj.getLicenceValidity());
		    			pstmt_tables.setString(8,vobj.getVehicleType());
		    			pstmt_tables.setString(9,vobj.getPermitNo());	
		    			pstmt_tables.setString(10,vobj.getPermitValidity());
		    			pstmt_tables.setString(11,vobj.getVehicleFor());
		    			pstmt_tables.setString(12,vobj.getArea());
		    			pstmt_tables.setString(13,vobj.getAddressParking());
		    			pstmt_tables.setString(14,vobj.getOtherDet());
		    			if(pstmt_tables.executeUpdate()!=0)
		    				statement.executeUpdate("insert into VehicleMasterLog select * from VehicleMaster where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+(no)+" ");						
		    		}
		    	}
		    }
		}catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
			try
			{
				ctx.setRollbackOnly();	
			}catch(Exception exception){exception.printStackTrace();}
		}
		finally
		{
			try
			{
				connection.close();			
			}catch(Exception ex){ex.printStackTrace();}
			commonlocal = null;
		}
		return(no);	    
	}

	
	public int storeODCCMaster(ODCCMasterObject odccmasterobject,int type) throws CreateException, DateFormatException 
	{
		System.out.println("INSIDE Bean storeODCCMaster");
		Connection connection=null;
		Statement statement=null;
		ResultSet rs=null;
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			int no=0;	
			commonlocal=commonlocalhome.create();			
		    System.out.println("TYPE: "+type); 
		    if(odccmasterobject.getAccNo()==0 && type==0)
		    {
		        System.out.println("1..........");
		        rs=statement.executeQuery("select lst_acc_no from Modules where modulecode="+odccmasterobject.getModuleCode());
				if(rs.next())
				    no=rs.getInt(1)+1;
				    //no=rs.getInt(1);				
				/*if(statement.executeUpdate("update Modules set lst_acc_no=lst_acc_no+1 where modulecode="+odccmasterobject.getModuleCode())!=1)*/
				if(statement.executeUpdate("update Modules set lst_acc_no="+no+" where modulecode="+odccmasterobject.getModuleCode())!=1)
				    throw new SQLException("Modules not updated");
				System.out.println("2..........");
		    }
		    else
		        no=odccmasterobject.getAccNo();
		        //no=odccmasterobject.getAccNo()-1;


		int res=0;
		System.out.println("ssssss="+odccmasterobject.getAccNo());
		if(type==0 ||type==1)//1--delete
		{
		    System.out.println("Deleteing already Existing records  for Updation..."+odccmasterobject.getAccNo());
		    System.out.println("3.........");		    
			if(odccmasterobject.getAccNo()>0)
			{
				System.out.println("ac no > 0.");
					            
				/*if(statement.executeUpdate("delete from ODCCTransaction where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'")<=0)
					throw new SQLException("ODCCTransaction not deleted");
				else 
				    System.out.println("ODCCTransaction records not Deleted............");
				
				if(statement.executeUpdate("delete from ODCCMaster where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'")<=0)
					throw new SQLException("ODCCMaster not deleted");
				else 
				    System.out.println("ODCCMaster records Deleted............");
				
				if(statement.executeUpdate("delete from BorrowerMaster where ln_ac_no="+odccmasterobject.getAccNo()+" and ln_ac_type='"+odccmasterobject.getModuleCode()+"'")<=0)
					throw new SQLException("BorrowerMaster not deleted");
				else 
				    System.out.println("BMaster records   are Deleted............");
				
				
				if(statement.executeUpdate("delete from RelativeMaster where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'")<=0)
					throw new SQLException("RelativeMaster not deleted");
				else 
				    System.out.println("RelatiMaster records   are Deleted............");
				
				if(statement.executeUpdate("delete from EmploymentMaster where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'")<=0)
					throw new SQLException("EmploymentMaster not deleted");
				else 
				    System.out.println("EMplMaster records   are Deleted............");
				
				if(statement.executeUpdate("delete from PropertyMaster where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'")<=0)
					throw new SQLException("propertyMaster not deleted");
				else 
				    System.out.println("PropMaster records   are Deleted............");
				
				if(statement.executeUpdate("delete from GoldDetMaster where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'")<=0)
					throw new SQLException("GoldDetMaster not deleted");
				else 
				    System.out.println("GoldMaster records   are Deleted............");
				
				System.out.println("Before Sinature Instruction");
				if(statement.executeUpdate("delete from SignatureInstruction where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'")<=0)
					throw new SQLException("SignatureInstruction not deleted");
				else 
				    System.out.println("SigMaster records   are Deleted............");
				
				System.out.println("NomineeMaster Ac_Ty======="+odccmasterobject.getModuleCode());
				System.out.println("NomineeMaster Ac_No======="+odccmasterobject.getAccNo());
				System.out.println("For Delete NomineeMaster"+statement.executeUpdate("delete from NomineeMaster where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'"));
				if(statement.executeUpdate("delete from NomineeMaster where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'")<=0)
					throw new SQLException("NomineeMaster not deleted");
				else 
				    System.out.println("NomineeMastre records   are Deleted............");
				
				if(statement.executeUpdate("delete from VehicleMaster where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'")<=0)
					throw new SQLException("VehicleMaster not deleted");
				else 
				    System.out.println("EMplMaster records   are Deleted............");
				
				if(statement.executeUpdate("delete from ODInterestDetails where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'")<=0)
					throw new SQLException("ODCCInterestDetails not deleted");
				else 
				    System.out.println("ODCCInterset records   are Deleted............");*/
				
	            //Changed on 1/04/06
				// code Added by Swapna 20/12/2006	
				String trn_date=null;
				int ref_no=0;
				rs=statement.executeQuery("select *  from ODCCTransaction where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
				if(rs.next()){
					trn_date=rs.getString("trn_date");
					ref_no=rs.getInt("ref_no");
				}
				statement.executeUpdate("update DayCash set ac_no=0,attached='F' where scroll_no='"+ref_no+"' and trn_date='"+trn_date+"'  and ac_type='"+odccmasterobject.getModuleCode()+"' ");
				
				statement.addBatch("delete from ODCCMaster where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
	            statement.addBatch("delete from ODCCTransaction where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");	
	            statement.addBatch("delete from ODCCMasterLog where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
	            statement.addBatch("delete from EmploymentMaster where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
	            statement.addBatch("delete from EmploymentMasterLog where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
				statement.addBatch("delete from BorrowerMaster where ln_ac_no="+odccmasterobject.getAccNo()+" and ln_ac_type='"+odccmasterobject.getModuleCode()+"'");				
				statement.addBatch("delete from BorrowerMasterLog where ln_ac_no="+odccmasterobject.getAccNo()+" and ln_ac_type='"+odccmasterobject.getModuleCode()+"'");
				statement.addBatch("delete from NomineeMaster where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
				statement.addBatch("delete from ODInterestDetails where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");				
				statement.addBatch("delete from RelativeMaster where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
				statement.addBatch("delete from RelativeMasterLog where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
				statement.addBatch("delete from PropertyMaster where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
				statement.addBatch("delete from PropertyMasterLog where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
				statement.addBatch("delete from GoldDetMaster where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");				
				statement.addBatch("delete from GoldDetMasterLog where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
				statement.addBatch("delete from VehicleMaster where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
				statement.addBatch("delete from VehicleMasterLog where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
				statement.addBatch("delete from SignatureInstruction where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
				statement.addBatch("delete from SignatureInstructionLog where ac_no="+odccmasterobject.getAccNo()+" and ac_type='"+odccmasterobject.getModuleCode()+"'");
								
				int[] a=statement.executeBatch();
				if(a!=null )
					System.out.println("Batch Length : "+a.length);
	            
				/*if(type==1)
				{
				   if(statement.executeUpdate("update DayCash set ac_no=0,attached='F' where  ac_type='"+odccmasterobject.getModuleCode()+"' and trn_date='"+odccmasterobject.getTransDate()+"' and scroll_no="+odccmasterobject.getRef_No()+" and attached='T' ")<=0)
				       throw new SQLException("DayCash not updated");
				   else 
				       System.out.println("Daycash records   are Deleted............");
				}*/
                System.out.println("Deleted Evrything...");            
			}
			
			if(type==0)
			{    
			    System.out.println("TYPE... : "+type);
			    System.out.println("4.........");
			    System.out.println("Inserting into ODCCMaster...");
			    PreparedStatement pstmt=connection.prepareStatement("insert into ODCCMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			    
			    pstmt.setString(1,String.valueOf(odccmasterobject.getModuleCode()));//actype
			    pstmt.setInt(2,no);//acno
			    pstmt.setInt(3,odccmasterobject.getShareAccNo());//lnee lf no		
			    pstmt.setString(4,odccmasterobject.getShareAccType());//lnee lf ac typev
			    pstmt.setInt(5,odccmasterobject.getCustomerId());
			    pstmt.setString(6,odccmasterobject.getChqBKIssue());//ch_bk_iss
			    pstmt.setInt(7,odccmasterobject.getNoOfCoBorrowers());//no coborrowers			
			    pstmt.setInt(8,odccmasterobject.getNoOfSurities());//surities 
			    pstmt.setInt(9,odccmasterobject.getMailingAddress());
			    pstmt.setInt(10,odccmasterobject.getApplicationSrlNo());//ln appn srl number
			    pstmt.setString(11,odccmasterobject.getApplicationDate());//ln appn date
			    pstmt.setDouble(12,odccmasterobject.getRequiredAmount());//req amt
			    pstmt.setInt(13,odccmasterobject.getPurposeCode());//purpose code

			    NomineeObject nom[]=odccmasterobject.getNomineeDetails();
			    if(nom!=null)
			    {	
			        System.out.println("5........");
			        int id=commonlocal.storeNominee(nom,String.valueOf(odccmasterobject.getModuleCode()),no,odccmasterobject.getTransDate());
			        pstmt.setInt(14,id);
			    }
			    else			
			        pstmt.setInt(14,0);//nom regno			    
			    
				System.out.println("6.........");
				pstmt.setInt(15,1);//lst trn seq
				pstmt.setString(16,odccmasterobject.getTransDate());//lst trn date
				pstmt.setInt(17,0);//pass bk seq
				pstmt.setInt(18,0);//last_line
				pstmt.setInt(19,0);//ledger seq
				pstmt.setString(20,"O");//ac status
				pstmt.setString(21,"F");//def ind
				pstmt.setString(22,"F");//freeze ind
				System.out.println("7.........");
				pstmt.setString(23,odccmasterobject.getTransDate());//ac  open date
				pstmt.setString(24,null);//ac  close date
				pstmt.setInt(25,odccmasterobject.getInterestRateType());//int rate type
				pstmt.setDouble(26,0);//int rate 
				//pstmt.setString(27,"N");//psect _cd
				//pstmt.setString(28,"N");//weeker section
				pstmt.setInt(27,0);//psect _cd
				pstmt.setInt(28,0);
				pstmt.setString(29,String.valueOf(odccmasterobject.getSexInd()));//sex
				pstmt.setString(30,odccmasterobject.getRelative());//relation
				pstmt.setInt(31,odccmasterobject.getDirectorCode());//dir code	
				pstmt.setString(32,null);//conv dt
				pstmt.setString(33,null);//sanc dt
				pstmt.setDouble(34,0);//sanc amt
				pstmt.setString(35,"N");//loan sanctioned
				pstmt.setString(36,"N");// sanc verified
				pstmt.setString(37,Validations.addDays(odccmasterobject.getTransDate(),-1));// int upto dt
				System.out.println("8.........");
				pstmt.setString(38,null);//limit upto
				pstmt.setString(39,null);//period
				pstmt.setDouble(40,0);//credit limit
				pstmt.setString(41,null);//remd no
				pstmt.setString(42,null);//remd dt
				pstmt.setString(43,null);//npa
				pstmt.setString(44,null);//npastgv
				pstmt.setString(45,odccmasterobject.uv.getUserTml());//de tml
				pstmt.setString(46,odccmasterobject.uv.getUserId());//de user
				pstmt.setString(47,odccmasterobject.uv.getUserDate());
				pstmt.setString(48,null);//ve tml
				pstmt.setString(49,null);//ve user
				pstmt.setString(50,null);//ve date time
				System.out.println("9........");
				res=pstmt.executeUpdate();
				System.out.println("10.........");
				
				//CoBorrowers and Surities Details
				if(res==1)
				{
				    System.out.println("After inserting records into Master, storing Sig Details.."); 
				    System.out.println("mmmmmm="+odccmasterobject.getAccNo());
				    
					odccmasterobject.setAccNo(no);	
					
					statement.executeUpdate("insert into ODCCMasterLog select * from ODCCMaster where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+(no)+"");
					
					boolean id=commonlocal.storeSignatureDetails(odccmasterobject.getSigObj(),(no));
					statement.executeUpdate("insert into SignatureInstructionLog select * from SignatureInstruction where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+(no)+" ");
					System.out.println("stored signature details.. "+id);
					
					rs=statement.executeQuery("select de_tml from DayCash where scroll_no='"+odccmasterobject.getRef_No()+"' and trn_date='"+odccmasterobject.getTransDate()+"'");
					System.out.println("11............");
					if(rs.next())
						if(statement.executeUpdate("insert into ODCCTransaction values('"+odccmasterobject.getModuleCode()+"',"+(no)+",'"+odccmasterobject.getTransDate()+"','R',1,'"+odccmasterobject.getTransAmount()+"','C','"+rs.getString(1)+"','C',0,null,null,"+odccmasterobject.getRef_No()+",null,'"+odccmasterobject.getTransAmount()+"',0,'"+odccmasterobject.uv.getUserTml()+"','"+odccmasterobject.uv.getUserId()+"','"+odccmasterobject.uv.getUserDate()+"',null,null,null,'"+Validations.addDays(odccmasterobject.getTransDate(),-1)+"')")!=1)
							throw new SQLException("ODCCTransaction not Inserted");
					
					// day cash updation added by Swapna 20/12/2006
					statement.executeUpdate("update DayCash set trn_seq=1,ac_no='"+odccmasterobject.getAccNo()+"',attached='T' where  trn_date='"+odccmasterobject.getTransDate()+"' and scroll_no="+odccmasterobject.getRef_No()+"  ");
					
					System.out.println("12............");
					if(odccmasterobject.getDeposits()!=null)
					{	
					    System.out.println("13  ...........");
						pstmt=connection.prepareStatement("insert into BorrowerMaster values(?,?,?,?,?,?)");
						System.out.println("14...........");
						Vector  v=odccmasterobject.getDeposits();
						Enumeration en=v.elements();   
						while(en.hasMoreElements())
						{
						    System.out.println("15............");
							pstmt.setString(1,String.valueOf(odccmasterobject.getModuleCode()));
							pstmt.setInt(2,no);
							pstmt.setString(3,"D");
							String str=en.nextElement().toString();
							pstmt.setString(4,str.substring(0,str.indexOf(" ")));
							pstmt.setInt(5,Integer.parseInt(str.substring(str.indexOf(" ")).trim()));
							pstmt.setString(6,null);
							pstmt.addBatch();				
						}
						if(pstmt.executeBatch().length!=v.size())
							throw new SQLException("BorrowerMaster not inserted");						
						else				
							statement.executeUpdate("insert into BorrowerMasterLog select * from BorrowerMaster where ln_ac_type='"+odccmasterobject.getModuleCode()+"' and ln_ac_no="+(no)+" and type='D'");
						
					}
					
					if(odccmasterobject.getNoOfCoBorrowers()>0 || odccmasterobject.getNoOfSurities()>0)
					{	
						pstmt=connection.prepareStatement("insert into BorrowerMaster values(?,?,?,?,?,?)");		
						if(odccmasterobject.getNoOfCoBorrowers()>0)
						{
							Vector  v=odccmasterobject.getCoBorrowers();
							Enumeration en=v.elements();
							while(en.hasMoreElements())
							{
								pstmt.setString(1,String.valueOf(odccmasterobject.getModuleCode()));
								pstmt.setInt(2,no);
								pstmt.setString(3,"C");
								String str=en.nextElement().toString();
								pstmt.setString(4,str.substring(0,str.indexOf(" ")));
								pstmt.setInt(5,Integer.parseInt(str.substring(str.indexOf(" ")).trim()));
								pstmt.setString(6,null);
								pstmt.addBatch();				
							}
							if(pstmt.executeBatch().length!=v.size())
								throw new SQLException("BorrowerMaster not inserted");
							else				
								statement.executeUpdate("insert into BorrowerMasterLog select * from BorrowerMaster where ln_ac_type='"+odccmasterobject.getModuleCode()+"' and ln_ac_no="+(no)+" and type='C'");
						}
						
						if(odccmasterobject.getNoOfSurities()>0)
						{
							Vector  v=odccmasterobject.getSurities();
							Enumeration en=v.elements();
							while(en.hasMoreElements())
							{
								pstmt.setString(1,String.valueOf(odccmasterobject.getModuleCode()));
								pstmt.setInt(2,no);
								pstmt.setString(3,"S");
								String str=en.nextElement().toString();
								pstmt.setString(4,str.substring(0,str.indexOf(" ")));
								pstmt.setInt(5,Integer.parseInt(str.substring(str.indexOf(" ")).trim()));
								pstmt.setString(6,null);
								pstmt.addBatch();				
							}
							if(pstmt.executeBatch().length!=v.size())
								throw new SQLException("BorrowerMaster not inserted");
							else				
								statement.executeUpdate("insert into BorrowerMasterLog select * from BorrowerMaster where ln_ac_type='"+odccmasterobject.getModuleCode()+"' and ln_ac_no="+(no)+" and type='S'");
						}								
					}
			
					/** Dependents Details */
					if(odccmasterobject.getDependents()!=null || odccmasterobject.getInterests()!=null || odccmasterobject.getRelatives()!=null)
					{
						pstmt=connection.prepareStatement("insert into RelativeMaster values(?,?,?,?,?,?,?,?,?)");		
						if(odccmasterobject.getDependents()!=null?odccmasterobject.getRelatives().length>0:false)
						{
							Object obj[][]=odccmasterobject.getRelatives();
							for(int i=0;i<obj.length;i++)
							{
								pstmt.setString(1,String.valueOf(odccmasterobject.getModuleCode()));
								pstmt.setInt(2,no);
								pstmt.setString(3,"Relatives");
								pstmt.setString(4,obj[i][0].toString());
								pstmt.setString(5,obj[i][1].toString());
								pstmt.setString(6,obj[i][3].toString());
								pstmt.setString(7,obj[i][4].toString());
								pstmt.setString(8,obj[i][5].toString());
								pstmt.setString(9,obj[i][2].toString());
								pstmt.addBatch();				
							}
							pstmt.executeBatch();
						}
						if(odccmasterobject.getInterests()!=null?odccmasterobject.getDependents().length>0:false)
						{
							Object obj[][]=odccmasterobject.getDependents();
							for(int i=0;i<obj.length;i++)
							{
								pstmt.setString(1,String.valueOf(odccmasterobject.getModuleCode()));
								pstmt.setInt(2,no);
								pstmt.setString(3,"Dependents");
								pstmt.setString(4,obj[i][0].toString());
								pstmt.setString(5,obj[i][1].toString());
								pstmt.setString(6,obj[i][2].toString());
								pstmt.setString(7,null);
								pstmt.setString(8,null);
								pstmt.setString(9,obj[i][3].toString());
								pstmt.addBatch();				
							}
							pstmt.executeBatch();
						}
						if(odccmasterobject.getRelatives()!=null?odccmasterobject.getInterests().length>0:false)
						{
							Object obj[][]=odccmasterobject.getInterests();
							for(int i=0;i<obj.length;i++)
							{
								pstmt.setString(1,String.valueOf(odccmasterobject.getModuleCode()));
								pstmt.setInt(2,no);
								pstmt.setString(3,"Interests");
								pstmt.setString(4,obj[i][0].toString());
								pstmt.setString(5,obj[i][1].toString());
								pstmt.setString(6,obj[i][2].toString());
								pstmt.setString(7,obj[i][3].toString());
								pstmt.setString(8,obj[i][4].toString());
								pstmt.setString(9,null);
								pstmt.addBatch();				
							}
							pstmt.executeBatch();							
						}
						pstmt.executeBatch();
						statement.executeUpdate("insert into RelativeMasterLog select * from RelativeMaster where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+(no)+" ");
											
					}
			
					System.out.println("15............");
					
					/** Income Details */
					if(odccmasterobject.getIncomeDetails()!=null && odccmasterobject.getIncomeDetails().length>0)
					{
					    System.out.println("16............");
						pstmt=connection.prepareStatement("insert into EmploymentMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						
						IncomeObject in[]=odccmasterobject.getIncomeDetails();		
						for(int i=0;i<odccmasterobject.getIncomeDetails().length;i++)	
						{
							if(in[i]!=null)	
							{
								pstmt.setInt(1,no);				
								pstmt.setString(2,String.valueOf(odccmasterobject.getModuleCode()));
							   	pstmt.setString(3,in[i].getTypeOfIncome());
							   	pstmt.setString(4,null);
							   	pstmt.setString(5,null);				
								pstmt.setString(6,null);				
								pstmt.setString(7,in[i].getAddress());
								pstmt.setString(8,in[i].getStringPhNo());
								System.out.println("The Valueeeeeeeeeeeeeeee"+in[i].getStringPhNo());
								/*pstmt.setInt(8,in[i].getPhNo());*/
								
								pstmt.setString(9,null);				
							   	pstmt.setString(10,null);				
								pstmt.setString(11,null);				
								pstmt.setInt(12,in[i].getService());
								pstmt.setString(13,null);				
								pstmt.setString(14,null);				
							   	pstmt.setString(15,null);				
								pstmt.setDouble(16,in[i].getIncome());				
								pstmt.setDouble(17,0);					
								pstmt.setString(18,null);				
								pstmt.setString(19,null);				
								pstmt.setInt(20,0);				
								pstmt.setDouble(21,0);
								pstmt.setString(22,null);
								pstmt.setString(23,null);
								pstmt.setDouble(24,0);
								pstmt.setDouble(25,in[i].getNetIncome());
								if(i==0)
								{
							  		pstmt.setString(5,in[i].getName());	
							  		pstmt.setDouble(17,in[i].getExpenditure());
								}
							   	if(i==1)
							   	{					   
							   		pstmt.setString(5,in[i].getName());
							   		pstmt.setString(6,in[i].getName());
									pstmt.setString(9,in[i].getEmpNo());			
									pstmt.setString(10,in[i].getDesignation());				
									pstmt.setString(11,in[i].getDept());			
							   		pstmt.setString(13,(in[i].isConfirmed()?"T":"F"));				
									pstmt.setString(14,(in[i].isTransferable()?"T":"F"));				
							        pstmt.setString(15,(in[i].isCertificateEnclosed()?"T":"F"));
							        pstmt.setDouble(17,in[i].getExpenditure());
			
							   	}	
								if(i==2)
								{
							   		pstmt.setString(5,in[i].getNature());			
							   		pstmt.setString(4,in[i].getName());
									pstmt.setDouble(17,in[i].getExpenditure());				
									pstmt.setDouble(21,in[i].getStockValue());
									pstmt.setString(22,in[i].getGoodsCondition());
									pstmt.setString(23,in[i].getTypeOfGoods());
									pstmt.setDouble(24,in[i].getTurnOver());
								}
								if(i==3)
								{
							   		pstmt.setString(6,in[i].getName());			
									pstmt.setString(18,in[i].getBankName());				
									pstmt.setString(19,in[i].getAccType());				
									pstmt.setInt(20,Integer.parseInt(in[i].getAccNo()));						
								}
								if(i==4)
									pstmt.setDouble(17,in[i].getExpenditure());				
								
								   pstmt.addBatch();

//								   statement.executeUpdate("insert into ODInterestDetails values('"+odccmasterobject.getAccType()+"',"+(no+1)+",'"+in[i].getTypeOfIncome()+"',0,"+getO+")");		   
				

							  }
						}	
						if(pstmt.executeBatch().length!=0)
							statement.executeUpdate("insert into EmploymentMasterLog select * from EmploymentMaster where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+(no)+" ");
					}			

					/** Property Details */
					if(odccmasterobject.getPropertyDetails()!=null)
					{
						PropertyObject po=odccmasterobject.getPropertyDetails();
						pstmt=connection.prepareStatement("insert into PropertyMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");		
						Object data[][]=po.getTenantsDetail();
						for(int i=0;i<((data!=null)?data.length:1);i++)
						{
							pstmt.setInt(1,no);				
							pstmt.setString(2,String.valueOf(odccmasterobject.getModuleCode()));
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
						//pstmt.executeBatch();
						if(pstmt.executeBatch().length>0)
							statement.executeUpdate("insert into PropertyMasterLog select * from PropertyMaster where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+(no)+" ");
					}
				
					/** Gold Details */
					if(odccmasterobject.getGoldDet()!=null)
					{
					    GoldObject gobj=odccmasterobject.getGoldDet();
						pstmt=connection.prepareStatement("insert into GoldDetMaster values(?,?,?,?,?,?,?,?,?,?)");		
						Object data[][]=gobj.getGoldDet();
						for(int i=0;i<((data!=null)?data.length:1);i++)
						{
							pstmt.setInt(1,no);				
							pstmt.setString(2,String.valueOf(odccmasterobject.getModuleCode()));
							pstmt.setInt(3,gobj.getApprisersCode());
							if(data!=null)
							{
							    pstmt.setInt(4,Integer.parseInt(data[i][0].toString()));
								pstmt.setString(5,data[i][1].toString());
								pstmt.setString(6,data[i][2].toString());
								pstmt.setString(7,data[i][3].toString());
								pstmt.setDouble(8,Double.parseDouble(data[i][5].toString()));
								/*pstmt.setString(4,data[i][1].toString());
								pstmt.setInt(5,Integer.parseInt(data[i][2].toString()));
								pstmt.setInt(6,Integer.parseInt(data[i][3].toString()));
								pstmt.setDouble(7,Double.parseDouble(data[i][4].toString()));
								pstmt.setInt(8,Integer.parseInt(data[i][5].toString()));*/
							}
							else
							{
								pstmt.setInt(4,0);
								pstmt.setString(5,null);
								pstmt.setInt(6,0);
								pstmt.setInt(7,0);
								pstmt.setDouble(8,0.00);
							}
							pstmt.setString(9,gobj.getApprisalDate());
							pstmt.setString(10,gobj.getApprisalTime());

							pstmt.addBatch();
						}
						//pstmt.executeBatch();
						if(pstmt.executeBatch().length!=0)
							statement.executeUpdate("insert into GoldDetMasterLog select * from GoldDetMaster where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+(no)+" ");
					}
					
					/** Vehicle Details */
					if(odccmasterobject.getVehicleDet()!=null)
					{	
					    System.out.println("before insert into VehicleMaster"); 
						VehicleObject vobj=odccmasterobject.getVehicleDet();
						pstmt=connection.prepareStatement("insert into VehicleMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");		
						pstmt.setInt(2,no);				
						pstmt.setString(1,String.valueOf(odccmasterobject.getModuleCode()));
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
						//pstmt.executeUpdate();
						if(pstmt.executeUpdate()!=0)
							statement.executeUpdate("insert into VehicleMasterLog select * from VehicleMaster where ac_type='"+odccmasterobject.getModuleCode()+"' and ac_no="+(no)+" ");
					}			
			
					/**	Added on 1/03/2006	*/			
					if(odccmasterobject.getDeposits()!=null)
					{
					    pstmt=connection.prepareStatement("insert into ODInterestDetails values(?,?,?,?,0,0,0,0,0,null,null)");		
						
					    Vector vector_deposits=odccmasterobject.getDeposits();						
						Enumeration en=vector_deposits.elements();
						while(en.hasMoreElements())
						{
							pstmt.setString(1,String.valueOf(odccmasterobject.getModuleCode()));
							pstmt.setInt(2,no);							
							String str=en.nextElement().toString();
							pstmt.setString(3,str.substring(0,str.indexOf(" ")));
							pstmt.setInt(4,Integer.parseInt(str.substring(str.indexOf(" ")).trim()));
							System.out.println("-----------ODINTEREST DETAILS---------------"+str.substring(0,str.indexOf(" ")));
							System.out.println("-----------ODINTEREST DETAILS---------------"+Integer.parseInt(str.substring(str.indexOf(" ")).trim()));
							pstmt.addBatch();				
						}
						pstmt.executeBatch();
					} 					
				}			
			}
		} //if result greater than 0
		
		System.out.println("17............");
		}catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
			try{
				ctx.setRollbackOnly();
		
			}catch(Exception exception){exception.printStackTrace();}
		}
		finally
		{
			try
			{
				connection.close();
				
			}catch(Exception ex){ex.printStackTrace();}
			commonlocal = null;
		}
		return(odccmasterobject.getAccNo());
	}
	
	public StockDetailsObject getCashCreditDetails(int acno,String acty) throws AccountNotFoundException 
	{
		StockDetailsObject stockdetailsobject=null;
		//Object data[][]=null;
		Connection connection=null;
		Statement statement=null;
		ResultSet rs=null;
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			

			rs=statement.executeQuery("select odcc.*,stk.*,concat_ws(' ',fname,mname,lname) as name,lp.pps_desc,emp.nature_of_emp,Modules.inspection_period from ODCCMaster odcc,StockInspectionDetails stk,CustomerMaster cm,LoanPurposes lp,EmploymentMaster emp,Modules where odcc.ac_no="+acno+" and odcc.ac_type='"+acty+"' and stk.ac_no="+acno+" and stk.ac_type='"+acty+"' and cm.cid=odcc.cid and lp.pps_no=odcc.pps_code and emp.ac_type=odcc.ac_type and emp.ac_no=odcc.ac_no and emp.emp_type='Business' and Modules.modulecode=odcc.ac_type order by concat(right(stk.insp_date,4),'-',mid(stk.insp_date,locate('/',stk.insp_date)+1, (locate('/',stk.insp_date,4)-locate('/',stk.insp_date)-1)),'-',left(stk.insp_date,locate('/',stk.insp_date)-1)) desc limit 1");


			if(rs.next())
			{
				System.out.println("inside rs");
				stockdetailsobject=new StockDetailsObject();
				stockdetailsobject.setModuleCode(Integer.parseInt(rs.getString("ac_type")));
				stockdetailsobject.setAccType(rs.getString("ac_type"));
				stockdetailsobject.setAccNo(rs.getInt("ac_no"));
				stockdetailsobject.setCustomerId(rs.getInt("cid"));
				
				stockdetailsobject.setCurrentCreditLimit(rs.getDouble("stk.credit_limit"));
				stockdetailsobject.setAccountStatus(rs.getString("ac_status"));
				stockdetailsobject.setMaturityDate(rs.getString("limit_upto"));
				stockdetailsobject.setName(rs.getString("name"));
				stockdetailsobject.setPrevCreditLimit(rs.getDouble("credit_limit"));
				stockdetailsobject.setPrevInspectionDate(rs.getString("insp_date"));
				stockdetailsobject.setPrevStockValue(rs.getDouble("stock_value"));
				stockdetailsobject.setSanctionDate(rs.getString("sanc_dt"));
				stockdetailsobject.setSanctionedLimit(rs.getDouble("odcc.creditlimit"));
				stockdetailsobject.setStockValue(rs.getDouble("stock_value"));
				stockdetailsobject.setNatureOfBusiness(rs.getString("emp.nature_of_emp"));
				stockdetailsobject.setLoanPurpose(rs.getString("lp.pps_desc"));
				stockdetailsobject.uv.setUserId(rs.getString("de_user"));
				stockdetailsobject.uv.setUserDate(rs.getString("de_date"));
				stockdetailsobject.uv.setUserTml(rs.getString("de_tml"));
				stockdetailsobject.uv.setVerId(rs.getString("ve_user"));
				stockdetailsobject.uv.setVerDate(rs.getString("ve_date"));
				stockdetailsobject.uv.setVerTml(rs.getString("ve_tml"));
				stockdetailsobject.setPeriod(rs.getInt("inspection_period"));

			}
			}catch(SQLException sqlexception){sqlexception.printStackTrace();}
			finally
			{
				try
				{
					System.out.println("closing coneection ...........");
					connection.close();
				}catch(SQLException ex){ex.printStackTrace();}
			}

		  return stockdetailsobject; 
		}
				
public boolean storeStockDetails(StockDetailsObject stk)
{
	Connection conn=null;
	try
	{
		conn=getConnection();
		Statement stm=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		//if(stm.executeQuery("select * from StockInspectionDetails where insp_date='"+getSysDate()+"'").next())
		if(stm.executeQuery("select * from StockInspectionDetails where insp_date='"+stk.getInspDate()+"' and ac_type='"+stk.getAccType()+"' and ac_no="+stk.getAccNo()+" ").next()) //ac_type and ac_no added on 20/03/2006
		{
			if(stm.executeUpdate("delete from StockInspectionDetails where insp_date='"+stk.getInspDate()+"' and ac_type='"+stk.getAccType()+"' and ac_no="+stk.getAccNo()+" ")!=1)	//ac_type and ac_no added on 20/03/2006
				throw new SQLException("stock details not deleted");
		}
		PreparedStatement ps=conn.prepareStatement("insert into StockInspectionDetails values(?,?,?,?,?,?,?,?,?)");
		ps.setString(1,stk.getAccType());
		ps.setInt(2,stk.getAccNo());
		ps.setString(3,stk.getInspDate());
		ps.setDouble(4,stk.getStockValue());
		ps.setDouble(5,stk.getInspCreditLimit());
		ps.setString(6,stk.getNextInspDate());
		ps.setString(7,stk.uv.getUserId());
		ps.setString(8,stk.uv.getUserTml());
		ps.setString(9,stk.uv.getUserDate());

		
		if(ps.executeUpdate()!=1)
			throw new SQLException("StockInspectionDetails not inserted");

		return true;
	}catch(SQLException ex)
	{
		ctx.setRollbackOnly();
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
	return false;
}

	public ODCCMasterObject getODCCMaster(int ln_acno,String ln_acty) throws AccountNotFoundException,CreateException 
	{
		System.out.println("Inside getODCCMaster Method....");
		ODCCMasterObject odccmasterobject=null;
		Object data[][]=null;
		Connection connection=null;
		Statement statement=null;
		ResultSet rs=null;
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			 rs=statement.executeQuery("select lm.* from ODCCMaster lm where ac_no="+ln_acno+" and ac_type='"+ln_acty+"'");
			commonlocal=commonlocalhome.create();

			if(rs.next())
			{
				odccmasterobject=new ODCCMasterObject();
				odccmasterobject.setModuleCode(Integer.parseInt(rs.getString("ac_type")));
				odccmasterobject.setAccType(rs.getString("ac_type"));
				odccmasterobject.setAccNo(rs.getInt("ac_no"));
				odccmasterobject.setCustomerId(rs.getInt("cid"));
				odccmasterobject.setMailingAddress(rs.getInt("addr_type"));
				odccmasterobject.setApplicationSrlNo(rs.getInt("appn_srl"));
				odccmasterobject.setApplicationDate(rs.getString("appn_date"));

				odccmasterobject.setInterestRateType(rs.getInt("int_rate_type"));
				//if(odccmasterobject.getInterestRateType()==0)
					odccmasterobject.setInterestRate(rs.getDouble("int_rate"));
//	later			else
	//				lnobj.setLoanIntRate(getIntRate(lnobj.getModuleCode(),getSysDate(),rs.getInt("sub_category"),lnobj.getNoInstallments(),rs.getDouble("sanc_amt")));


				odccmasterobject.setRequiredAmount(rs.getDouble("req_amt"));
				odccmasterobject.setCreditLimit(rs.getDouble("creditlimit"));
				odccmasterobject.setPurposeCode(rs.getInt("pps_code"));
				System.out.println("loan sacntioned="+rs.getString("loan_sanc"));
				odccmasterobject.setLoanSanctioned(rs.getString("loan_sanc").equals("N")?false:true);
				System.out.println("after setting="+odccmasterobject.isLoanSanctioned());
				odccmasterobject.setCreditLimit(rs.getDouble("sanc_amt"));
				odccmasterobject.setWeakerSection(rs.getInt("wk_sect")==0?false:true);
				odccmasterobject.setPrioritySector(rs.getInt("psect_cd")==0?false:true);
				odccmasterobject.setSanctionVerified(rs.getString("sanc_ver").equals("N")?false:true);

				odccmasterobject.setShareAccType(rs.getString("sh_type"));
				odccmasterobject.setShareAccNo(rs.getInt("sh_no"));

				odccmasterobject.setRelative(rs.getString("rel"));
				odccmasterobject.setDirectorCode(rs.getInt("dir_code"));
				odccmasterobject.setNoOfSurities(rs.getInt("no_surities"));
				odccmasterobject.setNoOfCoBorrowers(rs.getInt("no_coborrowers"));
				
				odccmasterobject.setCreditLimit(rs.getDouble("creditlimit"));
				odccmasterobject.setLimitUpto(String.valueOf(rs.getInt("period")));
				
				odccmasterobject.uv.setUserId(rs.getString("de_user"));
				odccmasterobject.uv.setUserDate(rs.getString("de_date"));
				odccmasterobject.uv.setUserTml(rs.getString("de_tml"));
				odccmasterobject.uv.setVerId(rs.getString("ve_user"));
				odccmasterobject.uv.setVerDate(rs.getString("ve_date"));
				odccmasterobject.uv.setVerTml(rs.getString("ve_tml"));
				
				odccmasterobject.setNom_regno(rs.getInt("nom_no"));
				if(rs.getString("sex_cd")!=null && (rs.getString("sex_cd").equals("M")))
					odccmasterobject.setSexInd('M');	
				else
					odccmasterobject.setSexInd('F');
				
				odccmasterobject.setChqBKIssue(rs.getString("ch_bk_issue"));
				odccmasterobject.setFreezeInd(rs.getString("freeze_ind"));
				odccmasterobject.setInterestUpto(rs.getString("int_uptodt"));
				odccmasterobject.setClosedt(rs.getString("ac_closedate"));
				odccmasterobject.setLedgerSeq(rs.getInt("ledger_seq"));
				
				odccmasterobject.setRemainderNo(rs.getInt("remd_no"));
				odccmasterobject.setRemainderDate(rs.getString("ledger_seq"));
				odccmasterobject.setNPADate(rs.getString("npa_dt"));
			    odccmasterobject.setNPAStage(rs.getString("npa_stg"));
			    odccmasterobject.setLastTrndt(rs.getString("last_tr_date"));
			    odccmasterobject.setDefaultInd(rs.getString("default_ind"));
				odccmasterobject.setNomineeDetails(commonlocal.getNominee(odccmasterobject.getNom_regno()));
				odccmasterobject.setAccOpenDate(rs.getString("ac_opendate"));
				odccmasterobject.setRelative(rs.getString("rel"));
				odccmasterobject.setSanctionDate(rs.getString("sanc_dt"));
				odccmasterobject.setSanctionAmount(rs.getDouble("sanc_amt"));
				odccmasterobject.setPrioritySectorCode(rs.getInt("psect_cd"));
				
				odccmasterobject.setPassBookSeq(rs.getInt("ps_bk_seq"));//pass bk seq
				odccmasterobject.setLastLine(rs.getInt("last_line"));//last_line
				odccmasterobject.setAccountStatus(rs.getString("ac_status"));//ac status
				
				ResultSet rs1=statement.executeQuery("select trn_amt,ref_no from ODCCTransaction where ac_type='"+ln_acty+"' and ac_no="+ln_acno+" and trn_date='"+odccmasterobject.getAccOpenDate()+"' ");
				if(rs1.next())
				{
				    System.out.println("Inside Transactionnnnnnnnnnnnnnn="+rs1.getDouble("trn_amt"));
				    System.out.println("Inside Transactionnnnnnnnnnnnnnn="+rs1.getInt("ref_no"));				    
					odccmasterobject.setTransAmount(rs1.getDouble("trn_amt"));
					odccmasterobject.setRef_No(rs1.getInt("ref_no"));
//					lnobj.setName(rs1.getString("name"));
				}

				SignatureInstructionObject si[]=commonlocal.getSignatureDetails(ln_acno,ln_acty);
				odccmasterobject.setSigObj(si);
				rs1=statement.executeQuery("select * from BorrowerMaster where ln_ac_no="+ln_acno+" and ln_ac_type='"+ln_acty+"' and type='S'");

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
				odccmasterobject.setSurities(c);

				rs1=statement.executeQuery("select * from BorrowerMaster where ln_ac_no="+ln_acno+" and ln_ac_type='"+ln_acty+"' and type='C'");
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
				odccmasterobject.setCoBorrowers(c);
				
				
				rs1=statement.executeQuery("select * from BorrowerMaster where ln_ac_no="+ln_acno+" and ln_ac_type='"+ln_acty+"' and type='D'");
				if(rs1.next())
				{
					rs1.last();
					c=new Vector(rs1.getRow());
					rs1.beforeFirst();
				}
				else
					c=null;

				while(rs1.next()){	
					c.add(rs1.getString("ac_type")+" "+rs1.getInt("ac_no"));
					System.out.println("actype="+rs1.getString("ac_type")+" ---------ac_no="+rs1.getString("ac_no"));
				}
				odccmasterobject.setDeposits(c);

				
				System.out.println("After getting surity/borrower details");
		

				int j=0;
				rs1=statement.executeQuery("select * from RelativeMaster where ac_no="+ln_acno+" and ac_type='"+ln_acty+"' and reltype='Relatives'");		
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
				   data[j][2]=rs1.getString("relation")	;
				   data[j][3]=rs1.getString("sex");	
				   data[j][4]=rs1.getString("marital");	
				   data[j][5]=rs1.getString("status");	
				   j++;
				}
				odccmasterobject.setRelatives(data);

				rs1=statement.executeQuery("select * from RelativeMaster where ac_no="+ln_acno+" and ac_type='"+ln_acty+"' and reltype='Interests'");		
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
				   data[j][2]=rs1.getString("sex");	
				   data[j][3]=rs1.getString("marital");	
				   data[j][4]=rs1.getString("status");	
	                j++;
				}
				odccmasterobject.setInterests(data);
				
				rs1=statement.executeQuery("select * from RelativeMaster where ac_no="+ln_acno+" and ac_type='"+ln_acty+"' and reltype='Dependents'");		
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
				   data[j][2]=rs1.getString("sex");	
				   data[j][3]=rs1.getString("relation");	
					j++;
				}
				odccmasterobject.setDependents(data);
				
				System.out.println("After getting relative  details");

				rs1=statement.executeQuery("select * from PropertyMaster where ac_no="+ln_acno+" and ac_type='"+ln_acty+"'");
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
						  System.out.println("tent name="+data[k][0]);
						  data[k][1]=rs1.getString("rent_mnth");	
						  System.out.println("rent per mth="+data[k][1]);
						  data[k][2]=rs1.getString("type");
						  System.out.println("type="+data[k][2]);
						  rs1.next(); 
						}
						prop.setTenantsDetail(data);	
					}
					else
						prop.setTenantsDetail(null);	
					System.out.println("4.....");

				}
				else
					prop=null;

				odccmasterobject.setPropertyDetails(prop);
				
				System.out.println("After getting property details");

				System.out.println("1.....");
				rs1=statement.executeQuery("select * from EmploymentMaster where ac_no="+ln_acno+" and ac_type='"+ln_acty+"'");
				masterObject.general.IncomeObject in[]=null;
				if(rs1.next())
				{
					rs1.last();
					in=new masterObject.general.IncomeObject[rs1.getRow()];
					rs1.beforeFirst();
					j=0;
				}
				else 
					in=null;

				while(rs1.next())
				{
					in[j]=new masterObject.general.IncomeObject();
			   		in[j].setTypeOfIncome(rs1.getString("emp_type"));				
					in[j].setAddress(rs1.getString("addr"));				
					in[j].setPhNo(rs1.getString("ph_no"));					
					in[j].setService(rs1.getInt("serv_length"));				
					in[j].setIncome(rs1.getDouble("amt_mnth"));				
					in[j].setNetIncome(rs1.getDouble("net_month_income"));
					if(rs1.getString("emp_type").equals("Self"))
					{
				   		in[j].setName(rs1.getString("nature_of_emp"));
				   		in[j].setExpenditure(rs1.getDouble("tax_exp_mnth"));
					}
					else if(rs1.getString("emp_type").equals("Service"))		
					{
				   		in[j].setName(rs1.getString("employer_name"));			
						in[j].setEmpNo(rs1.getString("emp_no"));			
						in[j].setDesignation(rs1.getString("designation"));				
						in[j].setDept(rs1.getString("department"));			
					   	in[j].setConfirmed(rs1.getString("emp_confirmed").equals("T")?true:false);				
						in[j].setTransferable(rs1.getString("serv_trans").equals("T")?true:false);				
					        in[j].setCertificateEnclosed(rs1.getString("sal_cert_enclosed").equals("T")?true:false);						
				   	}
					else if(rs1.getString("emp_type").equals("Business"))		
					{
					    System.out.println("------------inside Get ODCCC Businesssssssssssssssssss"+rs1.getString("ph_no"));
						in[j].setNature(rs1.getString("nature_of_emp"));			
				   		in[j].setName(rs1.getString("concern_name"));
						in[j].setExpenditure(rs1.getDouble("tax_exp_mnth"));				
						in[j].setStockValue(rs1.getDouble("stock_val"));
						in[j].setGoodsCondition(rs1.getString("goods_cond"));
						in[j].setTypeOfGoods(rs1.getString("goods_type"));
						in[j].setTurnOver(rs1.getDouble("avg_turnover_mnth"));
				   	}
                    else if(rs1.getString("emp_type").equals("Pension"))	
					{
					   	in[j].setName(rs1.getString("employer_name"));			
						in[j].setBankName(rs1.getString("bank_name"));				
						in[j].setAccType(rs1.getString("bank_ac_type"));				
						in[j].setAccNo(String.valueOf(rs1.getInt("bank_ac_no")));	
				   	} 
                    else if(rs1.getString("emp_type").equals("Rent"))		
						in[j].setExpenditure(rs1.getDouble("tax_exp_mnth"));                 
                    
					j++;
				}
				
				odccmasterobject.setIncomeDetails(in);


				System.out.println("After getting property details");

				rs1=statement.executeQuery("select * from GoldDetMaster where ac_no="+ln_acno+" and ac_type='"+ln_acty+"'");
				
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
			   		dataobj[j][0]=String.valueOf(rs1.getInt("srl_no"));
					dataobj[j][1]=rs1.getString("descr");
					dataobj[j][2]=String.valueOf(rs1.getDouble("grwgt"));
					dataobj[j][3]=String.valueOf(rs1.getDouble("netwgt"));
					dataobj[j][4]=String.valueOf((rs1.getDouble("rate")/rs1.getDouble("netwgt")));
					dataobj[j][5]=String.valueOf(rs1.getDouble("rate"));					
					gobj.setApprisalDate(rs1.getString("date"));				
					gobj.setApprisalTime(rs1.getString("time"));
					
					j++;
				}
				System.out.println("j value"+j);
				System.out.println("before setting");
				if(j>=1)
					gobj.setGoldDet(dataobj);
					
				System.out.println("after setting");
				odccmasterobject.setGoldDet(gobj);
				
				//Vehicle details
				
				rs1=statement.executeQuery("select * from VehicleMaster where ac_no="+ln_acno+" and ac_type='"+ln_acty+"'");
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
					odccmasterobject.setVehicleDet(vobj);
				}
				
//				lnobj.setScstInd(per.cm.getScSt());

				
				//lnobj.uv.setUserId(rs.getString("de_user"));
				//lnobj.uv.setUserTml(rs.getString("de_tml"));
				
			
			}
			else
				 throw new AccountNotFoundException();
		}catch(SQLException sqlexception){
			sqlexception.printStackTrace();
			}
		finally
		{
			try
			{
				System.out.println("closing coneection ...........");
				connection.close();
			}catch(SQLException ex){ex.printStackTrace();}
		}

	  return odccmasterobject; 
	}
	
	/**
	 * Function to verify whether cheque book No already exists
	 * from the table ChequeBook
	 * ChequeBook no is unique with in the AccountType sent
	 */	
	
	
		public String verifyChequeBookNo(String actype,int bookno)
		{
			String str="fail";
			Connection connection=null;
			Statement statement=null;
			ResultSet rs=null;
			try
			{
				connection=getConnection();
				statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rs=statement.executeQuery("select book_no from ChequeBook where ac_type='"+actype+"' and book_no="+bookno+" and ve_user is not null");
				if(rs.next())
					return str="fail";
				
				str="success";
			}catch(Exception sqlexception){sqlexception.printStackTrace();}
			finally
			{
				try
				{
					connection.close();
				}catch(Exception ex){ex.printStackTrace();}
			}

	return str;
		}


/**
 * Function to verify whether cheque leaf No already exists
 * from the table ChequeNo
 * cheque leaf no is unique within the AccountType and Cheque Book No
 */	
	
	public String verifyChequeNo(String actype,int ch_bk_no,int leaf)
	{
		String str="fail";
		Connection connection=null;
		Statement statement=null;
		ResultSet rs=null;
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
//			ResultSet rs=stmt.executeQuery("select chq_no from ChequeNo where ac_type='"+actype+"' and and chq_no="+leaf+"");
			 rs=statement.executeQuery("select book_no from ChequeBook where ac_type='"+actype+"' and book_no="+ch_bk_no+" and "+leaf+" between fst_chq_no and lst_chq_no and ve_user is not null");
			if(rs.next())
			return str="fail";
			
			str="success";
		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}


return str;
	}
	
	// It will take hlf yr def from Qtr def and calculates the next  int
	// upto date from the last interest calculated date
	
	public String getLastIntDate(int code,int type) throws QtrDefinitionNotDefinedException
	{
		String date=null;
		int date1[]=null;
		int a1[]={31,28,31,30,31,30,31,31,30,31,30,31};
		int a2[]={31,29,31,30,31,30,31,31,30,31,30,31};
		Calendar c=Calendar.getInstance();
		int dd1,yy1;
		Connection connection=null;
		Statement statement=null;
		ResultSet rs=null;
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=statement.executeQuery("select month from QtrDefinition where hyr_defn='T'");
			if(rs.next())
			{	
				rs.last();
				date1=new int[rs.getRow()];
				rs.beforeFirst();
			}
			else
				throw new QtrDefinitionNotDefinedException();

			int i=0;
			while(rs.next())
			date1[i++]=rs.getInt("month");
			rs.close();
			i=0;

			

			rs=statement.executeQuery("select lst_intdt from Modules where modulecode="+code+"");
			if(rs.next())
			date=rs.getString(1);
			System.out.println("Print last int date="+date);
			if(type==1)
			{
						
			StringTokenizer st=new StringTokenizer(date,"/");
			int dd=Integer.parseInt(st.nextToken());
			int mm=Integer.parseInt(st.nextToken());
			int yy=Integer.parseInt(st.nextToken());
			yy1=c.get(Calendar.YEAR);
			
			System.out.println("First year="+yy+" Second year="+yy1);
			if(yy<yy1)
			{				
				if(mm<date1[0])
				{
					if(yy%4==0)
			 		dd1=a2[date1[0]-1];
					else
			 		dd1=a1[date1[0]-1];
					date=dd1+"/"+date1[0]+"/"+yy;
				}
				else if(mm>date1[0] && mm<date1[1])
				{
					if(yy%4==0)
			 		dd1=a2[date1[1]-1];
					else
			 		dd1=a1[date1[1]-1];
					date=dd1+"/"+date1[1]+"/"+yy;
				}
				else if(mm>date1[1])
				{
					System.out.println("here");
					if((yy+1)%4==0)
			 		dd1=a2[date1[0]-1];
					else
			 		dd1=a1[date1[0]-1];
					date=dd1+"/"+date1[0]+"/"+(yy+1);
				}
				else if(mm==date1[0])
				{
					if(dd<((yy%4==0)?a2[date1[0]-1]:a1[date1[0]-1]))
					{
						if(yy%4==0)
			 			dd1=a2[date1[0]-1];
						else
			 			dd1=a1[date1[0]-1];
						date=dd1+"/"+date1[0]+"/"+yy;
					}
					else
					{
						if(yy%4==0)
			 			dd1=a2[date1[1]-1];
						else
			 			dd1=a1[date1[1]-1];
						date=dd1+"/"+date1[1]+"/"+yy;
					}
				}
				else if(mm==date1[1])
				{
					System.out.println("month="+mm+"date[1]="+date1[1]);
					if(dd<((yy%4==0)?a2[date1[1]-1]:a1[date1[1]-1]))
					{
						if(yy%4==0)
			 			dd1=a2[date1[1]-1];
						else
			 			dd1=a1[date1[1]-1];
						date=dd1+"/"+date1[1]+"/"+yy;
					}
					else
					{
						if(yy%4==0)
			 			dd1=a2[date1[0]-1];
						else
			 			dd1=a1[date1[0]-1];
						date=dd1+"/"+date1[0]+"/"+(yy+1);
					}
					System.out.println("Date Printtttttttttttttt="+date);
				}				
			}
			else if(yy==yy1)
			{
				if(mm<date1[0])
				{
					if(yy%4==0)
			 		dd1=a2[date1[0]-1];
					else
			 		dd1=a1[date1[0]-1];
					date=dd1+"/"+date1[0]+"/"+yy;
				}
				else if(mm>date1[0] && mm<date1[1])
				{
					if(yy%4==0)
			 		dd1=a2[date1[1]-1];
					else
			 		dd1=a1[date1[1]-1];
					date=dd1+"/"+date1[1]+"/"+yy;
				}
				else if(mm>date1[1])
				{
					if(yy1%4==0)
			 		dd1=a2[date1[0]-1];
					else
			 		dd1=a1[date1[0]-1];
					date=dd1+"/"+date1[0]+"/"+(yy1+1);
				}
				else if(mm==date1[0])
				{
					if(dd<((yy%4==0)?a2[date1[0]-1]:a1[date1[0]-1]))
					{
						if(yy%4==0)
			 			dd1=a2[date1[0]-1];
						else
			 			dd1=a1[date1[0]-1];
						date=dd1+"/"+date1[0]+"/"+yy;
					}
					else
					{
						if(yy%4==0)
			 			dd1=a2[date1[1]-1];
						else
			 			dd1=a1[date1[1]-1];
						date=dd1+"/"+date1[1]+"/"+yy;
					}
				}
				else if(mm==date1[1])
				{
					if(dd<((yy%4==0)?a2[date1[1]-1]:a1[date1[1]-1]))
					{
						if(yy%4==0)
			 			dd1=a2[date1[1]-1];
						else
			 			dd1=a1[date1[1]-1];
						date=dd1+"/"+date1[1]+"/"+yy;
					}
					else
					{
						if(yy%4==0)
			 			dd1=a2[date1[0]-1];
						else
			 			dd1=a1[date1[0]-1];
						date=dd1+"/"+date1[0]+"/"+(yy+1);
					}
				}				
			}			
		}		
			
		//date=Validations.convertDMY(date);
		System.out.println("Print date2222222222=="+date);		
		return date;
		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}
		
		return date;
	}
	
	/*public String getNextIntDate(String last_int_date)
	{
		int valid_date=Validations.validDate(String last_int_date,String sysd);
	}*/
	
	/**
	 * This fn is used to calculate interest for SB/CA Accounts.
	 * @param acno; if acno =0 --> calculates for all the accounts, 
	 * 				if acno!=0 ---> for the specified account
	 * @param calc_ind=2 ---> recalculate,
	 * @param calc_ind=1 --->new calculation
	 *
		1.To Calculate P
			Select 'int_pbl_date' from AccountMaster table for particular ac_type and ac_no.
			Select  'intfrom_day' and 'intto_day' from Modules for particular ac_type.
			To calculate the no_of_months(or no_of_years) Take last_pbl_date and today's date =(Period).
			
			[Interest is calculated between the "last int paid date(int_pbl_date in AccountMaster)"
			(for the Particular account no ) and today's date if it is for only one account
			otherwise next half yr date(@param todate) is taken(calculated only if it is <=today's date)]
				
   			Get min_bal for each month i.e, minimum cl_bal for every month for (Period)=(m1,m2,m3....).
   			So min_bal will be m1+m2+m3+.......=Principle amt(P).
   			
   			[minbal1=min bal betweeen int_from_day and int_to_day of the month is taken
   			 and minbal2=min bal where date <=int_from_day-1 of the month.Then product for
   			 this month will be min(minbal1,minbal2),then this min amount is compared with
	         @param minimumbal then product for this month wiil be min of this two]   			

		2.To Calculate R
			Select 'int_rate' from SavingsIntRate for particular ac_type for that duration of period=(r1).
			And Select 'extra_int_rt' from SavingsCatRate for particular ac_type and category=(r2).
			r1+r2=Interset Rate(R).

		3.To Calculate T
			Interest will be calculated for every Month only, so 1/12=(T) always.

		4.PTR/100    =>(Principle amt * 1/12 * Interest Rate)/100.	
	 
	 * @throws DateFormatException
	 * @throws CreateException
	 */

	public String savingsInterestCalculation(String actype,int acno,int modcode,double minimumbal,String todate,String Uid,String Utml,String Udate,int calc_ind) throws DateFormatException, CreateException
	{
		String todaydt=null;
		double product=0;
		double interest=0;
		int fday=0,tday=0;
		/*int a1[]={31,28,31,30,31,30,31,31,30,31,30,31};
		int a2[]={31,29,31,30,31,30,31,31,30,31,30,31};
		
		int yy,yy1,dd,dd1,mm,mm1;*/
		
		Connection connection=null;
		Statement statement=null;
		Statement stat=null;

		try
		{			 
			System.out.println("Ac Type="+actype);
			System.out.println("Ac No="+acno);
			System.out.println("Mod code="+modcode);
			System.out.println("Min Bal="+ minimumbal);
			System.out.println("To Date="+todate);
			System.out.println("Uid="+Uid);
			System.out.println("Utml="+Utml);
			System.out.println("U date="+Udate);
			System.out.println("calc_ind="+calc_ind);
			
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stat=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs2=null;
			ResultSet rs1=null;
					
			if(calc_ind==1)//calculate 
			{
				if(acno!=0)
				{			
					if(acno!=0 && (rs1=stat.executeQuery("select * from IntPay where post_ind='F' and ac_no="+acno+" and ac_type='"+actype+"' and trn_date='"+todate+"' ")).next())
						return "Already Calculated";
					if(acno!=0 && (rs1=stat.executeQuery("select * from IntPay where post_ind='T' and ac_no="+acno+" and ac_type='"+actype+"'and trn_date='"+todate+"'")).next())
						return "Already Posted";
					else
					{
						System.out.println("Inside delete of INTPAY.........");				
						rs1=statement.executeQuery("select ac_no,int_pbl_date,sub_category from AccountMaster,CustomerMaster where ac_no="+acno+" and ac_type='"+actype+"' and ac_status='O' and freeze_ind='F' and CustomerMaster.cid=AccountMaster.cid and  ac_closedate is null");
					}
				}
				else
				{
					if(acno==0 && (rs1=stat.executeQuery("select * from IntPay where post_ind='F' and ac_type='"+actype+"' and trn_date='"+todate+"' ")).next())		
						return "Already Calculated";
					if(acno==0 && (rs1=stat.executeQuery("select * from IntPay where post_ind='T' and ac_type='"+actype+"' and trn_date='"+todate+"'")).next())
						return "Already Posted";
					else									
						rs1=statement.executeQuery("select ac_no,int_pbl_date,sub_category from AccountMaster,CustomerMaster where ac_type='"+actype+"' and ac_status='O'  and freeze_ind='F'  and CustomerMaster.cid=AccountMaster.cid and  ac_closedate is null");					
				}				
			}
			else if(calc_ind==2)//Recalculate
			{			
				if(acno!=0 )
				{
					if(statement.executeUpdate("delete from IntPay where post_ind='F' and ac_type='"+actype+"' and ac_no="+acno+" and trn_date='"+todate+"' ")<=0)
						throw new SQLException("IntPay not deleted");	
					rs1=statement.executeQuery("select ac_no,int_pbl_date,sub_category from AccountMaster,CustomerMaster where ac_no="+acno+" and ac_type='"+actype+"' and ac_status='O'  and freeze_ind='F' and  CustomerMaster.cid=AccountMaster.cid and  ac_closedate is null");
				}			
				else 
				{
				 if(statement.executeUpdate("delete from IntPay where post_ind='F' and ac_type='"+actype+"' and trn_date='"+todate+"' ")<=0)
				 	throw new SQLException("IntPay not deleted");
				 rs1=statement.executeQuery("select ac_no,int_pbl_date,sub_category from AccountMaster,CustomerMaster where ac_type='"+actype+"' and ac_status='O'  and freeze_ind='F' and CustomerMaster.cid=AccountMaster.cid and  ac_closedate is null");
				}
			}
			
			ResultSet rs3=stat.executeQuery("select intfrom_day,intto_day from Modules where modulecode='"+actype+"'");
			if(rs3.next())
			{
				fday=rs3.getInt(1);
				tday=rs3.getInt(2);
			}		
			rs3.close();
			
			
			todaydt=todate;			
			
			/** todate --> date upto which interest has to be calculated
			 	todaydt--> todays date */
			
			int n1=Validations.validDate(todate,todaydt);
			if(n1==1)
			{	
				if(acno==0)
					return "Interest Not Due";
			}
			else
			{
				if(acno==0)
					todaydt=todate;
			}
				
			n1=0; 
			System.out.println("after no of months");
			
			//todaydt=Validations.addDays(todaydt,-1);
		String enddt=null;
		String lstpddt=null;
		int n=0;
		double minbal[];
		double minbal1=0,minbal2=0;
		double rate=0;
		PreparedStatement ps_intpay,ps_tendays,ps_twentydays;
		ps_intpay=connection.prepareStatement("insert into IntPay values('"+actype+"',?,?,'F',?,?,?,?,?,?,?,?,'F','F','"+Utml+"','"+Uid+"','"+Udate+"')");
		
		ps_tendays=connection.prepareStatement("select cl_bal from AccountTransaction where ac_no=? and ac_type='"+actype+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <= ? order by trn_seq desc");
		ps_twentydays=connection.prepareStatement("select min(cl_bal) from AccountTransaction where ac_no=? and ac_type='"+actype+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) >? and  concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <=? order by cl_bal");
		
		int ac_no=0;
		String tenthdate="";
		while(rs1.next()){
			ac_no=rs1.getInt(1);
			lstpddt=rs1.getString(2);
			product=0;
			if(Validations.validDate(lstpddt,todaydt)==-1){
				n=Validations.noOfMonths(lstpddt,todaydt);					
				//lstpddt=Validations.convertYMD(lstpddt);
				System.out.println("n="+n);
				minbal=new double[7];
				int i=0;
				enddt=getLastDay(lstpddt,tday);
				enddt=Validations.convertYMD(enddt);
				while((Validations.dayCompare(Validations.convertDMY(enddt),todaydt)>1) && (i<6)){
					lstpddt=Validations.addNoOfMonths(lstpddt,1);
					tenthdate=getFromDay(lstpddt,fday);
					enddt=getLastDay(lstpddt,tday);
					enddt=Validations.convertYMD(enddt);
					
					minbal1=0; minbal2=0;
					System.out.println("ACC No="+ac_no +" Tenth Date="+tenthdate+"  end dt="+enddt);
					System.out.println("i="+i);
					
					System.out.println("tenth date="+tenthdate);
					ps_tendays.setInt(1,ac_no);
					ps_tendays.setString(2,tenthdate);
					//System.out.println("ten days ="+ps_tendays);
					rs2=ps_tendays.executeQuery();
					
					
					if(rs2.next()){
						minbal1=rs2.getDouble(1);
						System.out.println("minbal 1="+minbal1);
					}
					rs2.close();
					//between 10 & 31 of the month
					ps_twentydays.setInt(1,ac_no);
					ps_twentydays.setString(2,tenthdate);
					ps_twentydays.setString(3,enddt);
					//System.out.println("20 days="+ps_twentydays);
					rs2=ps_twentydays.executeQuery();
					if(rs2.next() && rs2.getString(1)!=null ){
						minbal2=rs2.getDouble(1);
						System.out.println("minbal 2="+minbal2);
					}
					else{
						minbal2=minbal1;
						System.out.println("no minibal 2");
					}
					if(minbal1<minbal2)//if(minbal1<minbal2)
						minbal[i]=minbal1;
					else
						minbal[i]=minbal2;
		
					if(minbal[i]<minimumbal)	
						minbal[i]=0;
					
					System.out.println("min bal[i]="+minbal[i]);
					product=product+minbal[i];		
					rs2.close();
					i++;
				}


			rate=getIntRate(actype,rs1.getInt("sub_category"),todaydt);
							
			interest=0;
			if(product!=0)
				interest=Math.round(rate/100*1/12*product);
			System.out.println("Interest=========1======"+interest);
			System.out.println("Interest=========2======"+product);
			System.out.println("Interest=========3======"+rate);
			if(minbal[0]==0.0 && minbal[1]== 0.0 && minbal[2]==0.0 && minbal[3]==0.0 && minbal[4]==0.0 && minbal[5]==0.0 )
				
				ps_intpay.setInt(1,ac_no);
				ps_intpay.setString(2,todaydt);
				ps_intpay.setDouble(3,interest);
				ps_intpay.setDouble(4,rate);
				ps_intpay.setDouble(5,minbal[0]);
				ps_intpay.setDouble(6,minbal[1]);
				ps_intpay.setDouble(7,minbal[2]);
				ps_intpay.setDouble(8,minbal[3]);
				ps_intpay.setDouble(9,minbal[4]);
				ps_intpay.setDouble(10,minbal[5]);
				if(ps_intpay.executeUpdate()==0)
					throw new SQLException("IntPay not Inserted");
			}
			if(acno!=0)						
				return "Calculated and Interest Amount is "+interest;
			}
		
		/*while(rs1.next())
		{			
			ac_no=rs1.getInt(1);

			try
			{
				product=0;
							
				tenthdate=rs1.getString(2);
				enddt=null;
				fmonth=null;
				lmonth=null;
				
								
				n=0;
				if(Validations.validDate(lstpddt,todaydt)==-1)
				{	
					n=Validations.noOfMonths(lstpddt,todaydt);					
					lstpddt=Validations.convertYMD(lstpddt);
					System.out.println("n="+n);
	
					
					if(n==0)
					{
						minbal=new double[6];
						for(int i=0;i<6;i++)
							minbal[i]=0;
					}
					else
					{
						if(acno!=0)
							minbal=new double[n];
						else
							minbal=new double[6];
					}
					for(int i=0;i<n;i++)
					{
						
						if(ctx.getRollbackOnly())
							return null;
						st=new StringTokenizer(lstpddt,"-");
						yy=Integer.parseInt(st.nextToken());
						yy1=yy;
					
						mm=Integer.parseInt(st.nextToken());
						mm1=mm;
					
						dd=Integer.parseInt(st.nextToken());
						dd1=dd;	
				
						if(dd<fday)
						{
							dd=fday;
							if(yy%4==0)
								dd1=a2[mm-1];
							else
								dd1=a1[mm-1];
							if(dd1>tday)
								dd1=tday;
						}
						else 
						{	
							dd=fday;					
							if(mm==12)
							{
								mm=1;
								mm1=mm;
								yy=yy+1;
								yy1=yy;				
							}
							else
							{
								mm=mm+1;
								mm1=mm;
								yy1=yy;
							}						
					
							if(yy1%4==0)
								dd1=a2[mm1-1];
							else
								dd1=a1[mm1-1];	
						
							if(dd1>tday)
								dd1=tday;
						}
											
						lstpddt = Validations.convertYMD(Validations.checkDate(dd+"/"+mm+"/"+yy));
						///System.out.println("int pbl date="+lstpddt);
						enddt = Validations.convertYMD(Validations.checkDate(dd1+"/"+mm1+"/"+yy1));
						//System.out.println("end date="+enddt);
						fmonth = Validations.convertYMD(Validations.checkDate("1"+"/"+mm+"/"+yy));
					//	System.out.println("f month="+fmonth);
						lmonth=Validations.convertYMD(Validations.checkDate((fday-1)+"/"+mm+"/"+yy));
						//System.out.println("l month="+lmonth);
						//< 9th  dateof the month
						//rs2=stat.executeQuery("select cl_bal from AccountTransaction where ac_no="+rs1.getInt(1)+" and ac_type='"+actype+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <= '"+lstpddt+"' order by trn_seq desc");
						
						minbal1=0; minbal2=0;
						System.out.println("ACC No="+ac_no +" Lst pbl Date="+lstpddt+"  end dt="+enddt);
						System.out.println("i="+i);
						
						
						ps_tendays.setInt(1,ac_no);
						ps_tendays.setString(2,lstpddt);
						rs2=ps_tendays.executeQuery();
						
						
						if(rs2.next()){
							minbal1=rs2.getDouble(1);
							System.out.println("minbal 1="+minbal1);
						}
						rs2.close();
						//between 10 & 31 of the month
						ps_twentydays.setInt(1,ac_no);
						ps_twentydays.setString(2,lstpddt);
						ps_twentydays.setString(3,enddt);
						rs2=ps_twentydays.executeQuery();
						if(rs2.next() && rs2.getString(1)!=null ){
							minbal2=rs2.getDouble(1);
							System.out.println("minbal 2="+minbal2);
						}
						else{
							minbal2=minbal1;
							System.out.println("no minibal 2");
							
						}
						
						//rs2=stat.executeQuery("select * from AccountTransaction where ac_no="+rs1.getInt(1)+" and ac_type='"+actype+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) >'"+lstpddt+"' and  concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <='"+enddt+"' ");			
						if(rs2.next())// min balance between 10 & 31
						{
							rs2.close();
							rs2=stat.executeQuery("select min(cl_bal) from AccountTransaction where ac_no="+rs1.getInt(1)+" and ac_type='"+actype+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) >'"+lstpddt+"' and  concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <='"+enddt+"' ");			
						}
						else//<= 10 th order by trn_seq desc;
						{
							rs2.close();
							rs2=stat.executeQuery("select cl_bal from AccountTransaction where ac_no="+rs1.getInt(1)+" and ac_type='"+actype+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+lstpddt+"' order by trn_seq desc");			
						}
					
						
						if(rs2.next())
							minbal2=rs2.getDouble(1);
						
						//System.out.println("i="+i);
						
						//System.out.println("min bal1="+minbal1);
						//System.out.println("min bal 2="+minbal2);
						
						if(minbal1<minbal2)//if(minbal1<minbal2)
							minbal[i]=minbal1;
						else
							minbal[i]=minbal2;
			
						if(minbal[i]<minimumbal)	
							minbal[i]=0;
						
						System.out.println("min bal[i]="+minbal[i]);
						product=product+minbal[i];		
						rs2.close();
					}
	
	
				rate=getIntRate(actype,rs1.getInt("sub_category"),todaydt);
								
				interest=0;
				if(product!=0)
					interest=Math.round(rate/100*1/12*product);
				
				if(acno==0)
				{
					if(stmt.executeUpdate("insert into IntPay values('"+actype+"',"+rs1.getInt(1)+",'"+getSysDate()+"','F',"+interest+","+rate+","+minbal[0]+","+minbal[1]+","+minbal[2]+","+minbal[3]+","+minbal[4]+","+minbal[5]+",'F','F','"+Utml+"','"+Uid+"','"+getSysDate()+"')")!=1)
						throw new SQLException("IntPay not Inserted");
				}
				else
				{
					if(!InterestPosting(Utml,Uid,"",modcode,acno,interest))
					   throw new SQLException("Interest not posted");
					return "Calculated and Interest Amount is "+interest;
					
				}
				
				if(minbal[0]==0.0 && minbal[1]== 0.0 && minbal[2]==0.0 && minbal[3]==0.0 && minbal[4]==0.0 && minbal[5]==0.0 ){
					System.out.println("all balances 0 for ..... ac_no"+ac_no);
				}
				else{	
					System.out.println("inserted for="+ac_no);
					ps_intpay.setInt(1,ac_no);
					ps_intpay.setString(2,todaydt);
					ps_intpay.setDouble(3,interest);
					ps_intpay.setDouble(4,rate);
					ps_intpay.setDouble(5,minbal[0]);
					ps_intpay.setDouble(6,minbal[1]);
					ps_intpay.setDouble(7,minbal[2]);
					ps_intpay.setDouble(8,minbal[3]);
					ps_intpay.setDouble(9,minbal[4]);
					ps_intpay.setDouble(10,minbal[5]);
					if(ps_intpay.executeUpdate()==0)
						throw new SQLException("IntPay not Inserted");
					//if(stmt.executeUpdate("insert into IntPay values('"+actype+"',"+rs1.getInt(1)+",'"+todaydt+"','F',"+interest+","+rate+","+minbal[0]+","+minbal[1]+","+minbal[2]+","+minbal[3]+","+minbal[4]+","+minbal[5]+",'F','F','"+Utml+"','"+Uid+"','"+Udate+"')")!=1)
						
				}
												
				if(acno!=0)						
					return "Calculated and Interest Amount is "+interest;
				
				PreparedStatement ps=connection.prepareStatement("Commit");
				ps.execute();
				}
			}catch(Exception sql)
			{
				PreparedStatement ps=connection.prepareStatement("rollback");
				ps.execute();
				ctx.setRollbackOnly();
				sql.printStackTrace();
			}	
		}*/
		
		try
		{
			/*if(acno==0)
				if(statement.executeUpdate("update Modules set lst_intdt='"+todaydt+"' where modulecode="+modcode+"")!=1)
					throw new SQLException("Modules not Updated");*/
				
			PreparedStatement ps=connection.prepareStatement("Commit");
			ps.execute();
		}catch(Exception sql)
		{
			ctx.setRollbackOnly();
			PreparedStatement ps=connection.prepareStatement("rollback");
			ps.execute();
			sql.printStackTrace();
		}

		if(acno==0)
			return "Calculated";
		return "Calculated and Interest Amount is "+interest;
		}catch(SQLException sqlexception){
			ctx.setRollbackOnly();
			sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return "Not Calculated";

	}
	
	/** For SB CA Accounts */
	public double getIntRate(String acty,int cat,String date)
	{
		ResultSet rs_int=null;
		
		double intrate=0;
		Connection connection1=null;
		Statement statement1=null;
		try
		{
			connection1=getConnection();
			statement1=connection1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			
			System.out.println("INSIDE GET INTRATE ---------------"+Validations.convertYMD(date));
			//rs_int=statement1.executeQuery("select int_rate from SavingsIntRate where ac_type='"+acty+"' order by concat(mid(date,locate('/',date,6)+1,4),'-',mid(date,locate('/',date)+1,(locate('/',date,4)-locate('/',date)-1)),'-',left(date,locate('/',date)-1)) desc");
			rs_int=statement1.executeQuery("select concat(mid(date,locate('/',date,6)+1,4),'-',mid(date,locate('/',date)+1,(locate('/',date,4)-locate('/',date)-1)),'-',left(date,locate('/',date)-1)),de_date,int_rate  from SavingsIntRate where ac_type='"+acty+"' and concat(mid(date,locate('/',date,6)+1,4),'-',mid(date,locate('/',date)+1,(locate('/',date,4)-locate('/',date)-1)),'-',left(date,locate('/',date)-1)) >='"+Validations.convertYMD(date)+"' order by  concat(mid(date,locate('/',date,6)+1,4),'-',mid(date,locate('/',date)+1,(locate('/',date,4)-locate('/',date)-1)),'-',left(date,locate('/',date)-1)),substring(date,1,10),substring(date,11,18)");  
			if(rs_int.next())
			{
				rs_int.last();
				intrate+=rs_int.getDouble("int_rate");
				System.out.println("interest rate in getIntRate is================="+intrate);
				
			}
			rs_int.close();

			/*//rs_int=statement1.executeQuery("select int_rate from SavingsIntRate where ac_type='"+acty+"' order by concat(mid(date,locate('/',date,6)+1,4),'-',mid(date,locate('/',date)+1,(locate('/',date,4)-locate('/',date)-1)),'-',left(date,locate('/',date)-1)) desc");
			rs_int=statement1.executeQuery("select concat(mid(date,locate('/',date,6)+1,4),'-',mid(date,locate('/',date)+1,(locate('/',date,4)-locate('/',date)-1)),'-',left(date,locate('/',date)-1)),de_date,int_rate  from SavingsIntRate where ac_type='"+acty+"' and concat(mid(date,locate('/',date,6)+1,4),'-',mid(date,locate('/',date)+1,(locate('/',date,4)-locate('/',date)-1)),'-',left(date,locate('/',date)-1)) <='"+Validations.convertYMD(date)+"' order by  concat(mid(date,locate('/',date,6)+1,4),'-',mid(date,locate('/',date)+1,(locate('/',date,4)-locate('/',date)-1)),'-',left(date,locate('/',date)-1)),substring(de_date,1,10),substring(de_date,11,18)");  
			if(rs_int.next())
			{
				rs_int.last();
				intrate+=rs_int.getDouble("int_rate");
			}
			rs_int.close();
*/
		//	rs_int=statement1.executeQuery("select extra_int_rt  from SavingsCatRate where ac_type='"+acty+"' and category="+cat+" and concat(mid(date,locate('/',date,6)+1,4),'-',mid(date,locate('/',date)+1,(locate('/',date,4)-locate('/',date)-1)),'-',left(date,locate('/',date)-1)) <='"+Validations.convertYMD(date)+"' order by  concat(mid(date,locate('/',date,6)+1,4),'-',mid(date,locate('/',date)+1,(locate('/',date,4)-locate('/',date)-1)),'-',left(date,locate('/',date)-1)),substring(de_date,1,10),substring(de_date,11,18)");
			rs_int=statement1.executeQuery("select extra_int_rt  from SavingsCatRate where ac_type='"+acty+"' and category="+cat+" and concat(mid(date_to,locate('/',date_to,6)+1,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)) <='"+Validations.convertYMD(date)+"' order by  concat(mid(date_to,locate('/',date_to,6)+1,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)),substring(de_date,1,10),substring(de_date,11,18)");
			if(rs_int.next())
			{
				rs_int.last();
				intrate+=rs_int.getDouble("extra_int_rt");
			}

			return intrate;
			
		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection1.close();
			}catch(Exception ex){ex.printStackTrace();}
		}


		return intrate;

	}
	
	
	/**
	 * 
	 *
	 * */
	double calculateODInterestAmount(String actype,int ac_no,String fdate,String tdate,double sanctioned_amt,int category,int period,double balance_amt)
	{
		double interest_amt=0;
		Connection connection=null;
		Statement statement=null;
		Statement stat=null;
		Statement statement_rate=null;
		Statement statement_update=null;
		
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stat=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			statement_rate=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			statement_update=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			ResultSet rs=statement.executeQuery("select od.*,sd.* from ODInterestDetails od left join SecurityDetails sd on od.sec_ac_type=sd.code where ac_type='"+actype+"' and ac_no="+ac_no+"");
			//int i=0;
			String sec_type="(";
			while(rs.next())
			{
				if(rs.getString("sd.modulecode")!=null)
					sec_type+="ln_type= "+rs.getString("sd.modulecode")+" or ";
			}
			System.out.println(sec_type);
			int index=0;
			if(rs.next())
				index=sec_type.lastIndexOf(" or ");
			System.out.println("index="+index);
			
			sec_type=sec_type.substring(0,index);
			sec_type+=")";
		
			String from_date=fdate;
			System.out.println(sec_type);
			System.out.println(fdate+" "+tdate+" "+balance_amt);
			
			//loan int rate
			ResultSet rs2=stat.executeQuery("select distinct date_to from LoanIntRate where '"+sec_type+"' and  "+balance_amt+" between min_bal and max_bal and  concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
			while(rs2.next())
			{
				System.out.println("ac_type...."+actype+" "+ac_no);
				rs=statement.executeQuery("select od.*,sd.* from ODInterestDetails od left join SecurityDetails sd on od.sec_ac_type=sd.code where ac_type='"+actype+"' and ac_no="+ac_no+"");
				while(rs.next())
				{
					System.out.println("inside sec....");
					if(rs.getString("sd.modulecode")!=null)
					{
						System.out.println("Modulecode !=null....");
						ResultSet rs1=statement_rate.executeQuery("select rate from LoanIntRate where ln_type='"+rs.getString("sd.modulecode")+"' and  "+balance_amt+" between min_bal and max_bal and  concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(from_date)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(rs2.getString("date_to"))+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
						if(rs1.next())
						{
							System.out.println("inside update.....");
							statement_update.executeUpdate("update ODInterestDetails set ln_int_rate="+rs1.getString("rate")+" where ac_type='"+actype+"' and ac_no="+ac_no+" and sec_ac_type='"+rs.getString("sec_ac_type")+"'");
						}	
						
					}

				}
				//double int_rate_od=calculateODInterestRate(actype,ac_no,category,period,sanctioned_amt,-1);
				double int_rate_od=calculateODInterestRate(actype,ac_no,fdate,tdate,category,period,sanctioned_amt,-1);
				System.out.println("OD Int rate="+int_rate_od);
				interest_amt+=(Math.round(Validations.dayCompare(from_date,tdate)*(int_rate_od/36500)*(balance_amt)));
				
				from_date=rs2.getString("date_to");
			}
			//period rate
			 from_date=fdate;
			rs2=stat.executeQuery("select distinct date_to from LoanPeriodRate where '"+sec_type+"'  and "+period+" between fr_mon and to_mon and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
			while(rs2.next())
			{
				rs=statement.executeQuery("select od.*,sd.* from ODInterestDetails od left join SecurityDetails sd on od.sec_ac_type=sd.code where ac_type='"+actype+"' and ac_no="+ac_no+"");
				while(rs.next())
				{
					if(rs.getString("sd.modulecode")!=null)
					{
						ResultSet rs1=statement_rate.executeQuery("select rate from LoanPeriodRate where ln_type='"+rs.getString("sd.modulecode")+"'  and "+period+" between fr_mon and to_mon  and  concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(from_date)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(rs2.getString("date_to"))+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
						if(rs1.next())
							statement_update.executeUpdate("update ODInterestDetails set ln_int_rate="+rs1.getString("rate")+" where ac_type='"+actype+"' and ac_no="+ac_no+" and sec_ac_type='"+rs.getString("sec_ac_type")+"'");
						
					}

				}
				
				//interest_amt+=(Math.round(Validations.dayCompare(from_date,tdate)*(calculateODInterestRate(actype,ac_no,category,period,sanctioned_amt,-1)/36500)*(balance_amt)));
				interest_amt+=(Math.round(Validations.dayCompare(from_date,tdate)*(calculateODInterestRate(actype,ac_no,fdate,tdate,category,period,sanctioned_amt,-1)/36500)*(balance_amt)));
				
				from_date=rs2.getString("date_to");
			}

			//Category rate
			 from_date=fdate;
			rs2=stat.executeQuery("select distinct date_to from LoanCategoryRate where '"+sec_type+"'  and category="+category+" and  concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
			while(rs2.next())
			{
				rs=statement.executeQuery("select od.*,sd.* from ODInterestDetails od left join SecurityDetails sd on od.sec_ac_type=sd.code where ac_type='"+actype+"' and ac_no="+ac_no+"");
				while(rs.next())
				{
					if(rs.getString("sd.modulecode")!=null)
					{
						ResultSet rs1=statement_rate.executeQuery("select rate from LoanCategoryRate where ln_type='"+rs.getString("sd.modulecode")+"'  and category="+category+" and  concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(from_date)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(rs2.getString("date_to"))+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
						if(rs1.next())
							statement_update.executeUpdate("update ODInterestDetails set ln_int_rate="+rs1.getString("rate")+" where ac_type='"+actype+"' and ac_no="+ac_no+" and sec_ac_type='"+rs.getString("sec_ac_type")+"'");
						
					}

				}
				
				//interest_amt+=(Math.round(Validations.dayCompare(from_date,tdate)*(calculateODInterestRate(actype,ac_no,category,period,sanctioned_amt,-1)/36500)*(balance_amt)));
				interest_amt+=(Math.round(Validations.dayCompare(from_date,tdate)*(calculateODInterestRate(actype,ac_no,fdate,tdate,category,period,sanctioned_amt,-1)/36500)*(balance_amt)));
				
				from_date=rs2.getString("date_to");
			}
			
			
			

			System.out.println("Intertest"+interest_amt);

		}catch(Exception sqlexception){
			ctx.setRollbackOnly();
			sqlexception.printStackTrace();
			}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

			
		
		return(interest_amt);
		
	}	
	
	
	/**
	 * Changed by Deepa on 11/01/06
	 * @param actype
	 * @param fdate
	 * @param tdate
	 * @param amt
	 * @param category
	 * @param period
	 * @param disbamt
	 * @return
	 */
	double calculateInterest(String actype,String fdate,String tdate,double amt,int category,int period,double disbamt,int ac_no)
	{
		double interest=0;
		try{
			Vector vec=getIntRate(actype,fdate,tdate,category,period,disbamt,ac_no);
			String ctrndate=tdate;

			System.out.println("Length"+vec.size());
			
			for(Enumeration enume=vec.elements();enume.hasMoreElements();)
			{
				System.out.println("First");
			
				LoanIntRateObject dint[]=(LoanIntRateObject[])enume.nextElement();
				System.out.println("Length"+dint.length);
				//if  more that penal int rate defined between the transaction date
				for(int i=0;i<dint.length;i++)
				{
					if(i<(dint.length-1))
						tdate=dint[i].getToDate();
					else
						tdate=ctrndate;
	
					System.out.println("From date"+fdate+"  Tdate"+tdate+" Amount"+amt);
					interest=interest+(Math.round(Validations.dayCompare(fdate,tdate)*(dint[i].getIntRate()/36500)*(amt)));
					fdate=Validations.addDays(dint[i].getToDate(),1);					  
					System.out.println("----------------------------------------");	
					System.out.println("From date "+fdate);	
					System.out.println("To date "+tdate);
					System.out.println("rate "+dint[i].getIntRate());
					System.out.println("----------------------------------------");	
				}
			}

			System.out.println("Intertest"+interest);

		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		
		return(interest);
	}	
	
	/** For CC and OD Accounts on 01/06/06 */
	public Vector getIntRate(String ln_type,String fdate,String tdate,int category,int period,double amt,int ac_no)
	{
		Vector vec=null;
		Connection connection=null;
		Statement statement=null;
		ResultSet rs=null,rs_purpose=null;
		int purpose_code=0;
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			vec=new Vector(3);
			for(int j=0;j<3;j++)
			{
				/*if(j==0)		
					rs=statement.executeQuery("select date_fr,date_to,rate from LoanIntRate where ln_type='"+ln_type+"' and  "+amt+" between min_bal and max_bal and  concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");*/
				if(j==0)
				{	
					// Code added on 03/06/2006
					rs_purpose=statement.executeQuery("select pps_code  from ODCCMaster where ac_type='"+ln_type+"' and ac_no="+ac_no+" ");
					if(rs_purpose.next())
						purpose_code=rs_purpose.getInt("pps_code");
					rs_purpose=statement.executeQuery("select  * from LoanIntRate where ln_type='"+ln_type+"' and  "+amt+" between min_bal and max_bal and  pps_code="+purpose_code+" and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
					if(!rs_purpose.next())
						purpose_code=0;
					// 
					rs=statement.executeQuery("select  distinct date_fr,date_to,rate from LoanIntRate where ln_type='"+ln_type+"' and  "+amt+" between min_bal and max_bal and   pps_code="+purpose_code+" and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
				}
				else if(j==1)
					rs=statement.executeQuery("select date_fr,date_to,rate from LoanPeriodRate where ln_type='"+ln_type+"' and "+period+" between fr_mon and to_mon and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");
				else if(j==2)
					rs=statement.executeQuery("select date_fr,date_to,rate from LoanCategoryRate where ln_type="+ln_type+" and category="+category+" and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))");

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
		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}
		return vec;
	}
	
	/** Calculating PenalIntAmt between the given date for the given account
	 *  iff mat_date crossed grace period */
	double calculatePenalInt(String actype,String fdate,String tdate,double amt,int category)
	{
		double penalamt=0.0;
		Connection conn=null;
		//ResultSet rs_trn=null;
		
		try{
			conn=getConnection();
			//Statement stmt_trn=conn.createStatement();
			LoanIntRateObject dint[]=getPenalIntRate(actype,fdate,tdate,category);
			String ctrndate=tdate;
						
			if(dint.length>1)
			{				
				for(int i=0;i<dint.length;i++)
				{
					if(i<(dint.length-1))
						tdate=dint[i].getToDate();
					else
						tdate=ctrndate;
					
					System.out.println("No of days between "+fdate+" to  "+tdate+"  is :"+Validations.dayCompare(fdate,tdate));
					System.out.println("Amt :"+amt);
					penalamt=penalamt+(Validations.dayCompare(fdate,tdate)*(dint[i].getPenalRate()/36500)*(amt));
					System.out.println("PenalAmt: "+penalamt);
					fdate=Validations.addDays(dint[i].getToDate(),1);					  
				}
			}
			else if(dint.length>0)
			{				
				penalamt=(Validations.dayCompare(fdate,tdate)*(dint[0].getPenalRate()/36500)*(amt));
				System.out.println("PenalAmt: "+penalamt);
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}		
		return(penalamt);
	}
	
	public LoanIntRateObject[] getPenalIntRate(String ln_type,String fdate,String tdate,int category) 
	{
		LoanIntRateObject d[]=null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			ResultSet rs=null;
			System.out.println("Ac type"+ln_type);
			System.out.println("Fdate"+fdate);
			System.out.println("Tdate"+tdate+"  "+category);
			
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select penalrate from PenalIntRate where ln_type='"+ln_type+"' and  category="+category+" and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<='"+Validations.convertYMD(tdate)+"' order by  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1, (locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) ");
			
			rs.last();
			d=new LoanIntRateObject[rs.getRow()];
			rs.beforeFirst();			
			int i=0;
			
			while(rs.next())
			{
				d[i]=new LoanIntRateObject();				
				d[i].setPenalRate(rs.getDouble("penalrate"));
				System.out.println("Prate"+d[i].getPenalRate());
				
				/*d[i].setFromDate(rs.getString("date_fr"));
				d[i].setToDate(rs.getString("date_to"));
				System.out.println("Fdate"+d[i].getFromDate());
				System.out.println("Tdate"+d[i].getToDate());*/				
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
	
	/** For Calculating Interest 
	 * int_rate_type==1 ---> Floating
	 * int_rate_type==0 --->Fixed
	 * 
	 *	A.Interest Calculation for CC:
	 *
				1.	To Calculate P:
				
					1.	For Floating i.e., 'int_rate_type'=1.    
						•Get cl_bal for a transaction.=>(cl_bal).
						•no_of_days=No of days between any two transactions.	
						•Product=(cl_bal)* (no_of_days till the same int_rate remains).
					2.	For Fixed i.e., 'int_rate_type'=0.
						•Get cl_bal for a transaction.=>(cl_bal)
						•no_of_days=No of days between any two transactions.
						•Product=(cl_bal)*(no_of_days till where interest should be calculated).

				2.	To Calculate R

					1.	For Floating i.e., 'int_rate_type'=1.
						•Select 'rate' from LoanIntRate for particular ln_type and between(min and max Balance) for that duration of  period/first breakup=(r1).
						•Select 'rate' from LoanPeriodRate for particular ln_type and between(frm and to Months)  for that duration of period/first breakup=(r2).
						•Select 'rate' from LoanCategoryRate for particular ln_type and category  for that duration of period/first breakup=(r2).
						•(r1)+(r2)+(r3)=Interest Rate(R).
						•Same above process for second break up.	
					2.	For Fixed i.e., 'int_rate_type'=0
						•Select 'int_rate'  from ODCCMaster for particular ac_type and ac_no.=>Interest Rate(R).

				3.	To Calculate T
					•Interest will be calculated for Daily basis only, so 1/365=(T) always.

				4.	PTR/100    
					•(Product* 1* Interest Rate)/(365*100)=P1,->for firsr breakup
					•(Product* 1* Interest Rate)/(365*100)=P2,->for second breakup
					•(Product* 1* Interest Rate)/(365*100)=P3,.................
	
				5.	Total Interest Amount=P1+P2+P3+.............


		B.	Interest Calculation for OD 

				1.	To Calculate P
					•Get cl_bal for a transaction ? balance_amt (P)

				2.	To Calculate  Interest Rate (IR)
				
					1.For Floating i.e., 'int_rate_type'=1.
						(if not there in ODInterestDetails)
						•Go to table SecurityDetails and get all the securities for particular ac_type and ac_no.
						•Go to all Security Tables 
							EmploymentMaster
							BorrowerMaster
							GoldDetMaster
							PropertyMaster
							VehicleMaster
					    •For individual tables get interest rate as follows:
							1.Select 'rate' from LoanIntRate for particular ln_type and between(min and max Balance) for that duration of  period =(r1).
							2.Select 'rate' from LoanPeriodRate for particular ln_type and between(frm and to Months)  for that duration of period =(r2).
							3.Select 'rate' from LoanCategoryRate for particular ln_type and category  for that duration of period =(r2).
							4.(r1)+(r2)+(r3)=Interest Rate(R). 

					(if there in ODInterestDetails)
						select ln_int_rate from ODInterestDetails.
		
					2.For Fixed i.e., 'int_rate_type'=0
						•Select 'int_rate'  from ODCCMaster for particular ac_type and ac_no.=>Interest Rate(R).

					3.Get Eligible amt (E):
						Take lnk_shares from Modules for eligible amt

	      			  Get Sanctioned amt (SA):
	      			  	Take sanc_amt from ODCCMaster.

					4.Arrange interest rate (R) in ascending order and  Eligible amount (E) in descending order.

						(SA) - (E) =value1  ->	 int_amt (i1),
						value1 - next (E) =value2 -> int_amt (i2),
						value2 - next(E) = value3 -> int_amt (i3),
						:
						:
						value(N-1) - next (E) =valueN is 0 int_amt (iN),

						i1+i2+……..+iN = int_amt (I) ;

						Total Interest Rate(IR) = (I)/ (SA);
						
            3.	To Calculate T
				•Interest will be calculated for Daily basis only, so 1/365=(T) always.

			4.	PTR/100
			    •(P* 1* IR)/(365*100). 
	 * 
	 * */
	public String ODCCInterestCalc(String ac_type,int acno,double minimumbal,String Uid,String Utml,String Udate,int calc_ind)
	{
		System.out.println("Inside ODCC Interest Calculation......");
	    System.out.println("minimumbal == "+minimumbal);
		String lstintdt=null,todaydt=null;		
		double prev_clbal=0;
		double intamt=0,penal_intamt=0;
		double intrate=0;
		ResultSet rs_trn=null;
		//ResultSet rs_intpay=null;
		ResultSet rs_mst=null;
		int result=0;
		Connection connection=null;
		Statement statement=null;
		Statement stat=null;
		
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stat=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			if(calc_ind==1 && acno!=0 && statement.executeQuery("select * from IntPay where post_ind='F' and ac_no="+acno+" and ac_type='"+ac_type+"'").next())
			{
				return "Already Calculated";			
			}
			else if(calc_ind==1 && acno!=0)
				rs_mst=statement.executeQuery("select ac_no,ac_type,int_uptodt,custtype,period,int_rate_type,int_rate,sanc_amt from ODCCMaster am,CustomerMaster cm where ac_no="+acno+" and ac_type='"+ac_type+"' and ac_status='O' and am.cid=cm.cid");		
			else if(calc_ind==2 && acno!=0 )
			{
				if(stat.executeUpdate("delete from IntPay where post_ind='F' and ac_type='"+ac_type+"' and ac_no="+acno+"")<=0)
					throw new SQLException("IntPay not deleted");
				rs_mst=statement.executeQuery("select ac_no,ac_type,int_uptodt,custtype,period,int_rate_type,int_rate,sanc_amt from ODCCMaster am,CustomerMaster cm where ac_no="+acno+" and ac_type='"+ac_type+"' and ac_status='O' and am.cid=cm.cid");
			}
			else if(calc_ind==1 && statement.executeQuery("select * from IntPay where post_ind='F' and ac_type='"+ac_type+"'").next())		
			{
				return "Already Calculated";
			}
			else if(calc_ind==1)
			{	
				rs_mst=statement.executeQuery("select ac_no,ac_type,int_uptodt,custtype,period,int_rate_type,int_rate,sanc_amt from ODCCMaster am,CustomerMaster cm where ac_type='"+ac_type+"' and ac_status='O' and am.cid=cm.cid");
			}
			else if(calc_ind==2) 
			{
				if(stat.executeUpdate("delete from IntPay where post_ind='F' and ac_type='"+ac_type+"'")<=0)
					throw new SQLException("IntPay not deleted");
				rs_mst=statement.executeQuery("select ac_no,ac_type,int_uptodt,custtype,period,int_rate_type,int_rate,sanc_amt from ODCCMaster am,CustomerMaster cm where ac_type='"+ac_type+"' and ac_status='O' and am.cid=cm.cid");
			}

		System.out.println("1...");
		System.out.println("actype="+ac_type);
		System.out.println("AcNo: "+acno);
		try
		{
		while(rs_mst.next())
		{
			System.out.println("1............2");
			//todaydt=getSysDate();
			todaydt=Udate.substring(0,10);
			System.out.println("DAte=="+todaydt);
			lstintdt=rs_mst.getString("int_uptodt");
			double sanc_amt=rs_mst.getDouble("sanc_amt");
			intamt=0;

			rs_trn=stat.executeQuery("select * from ODCCTransaction where ac_type='"+rs_mst.getString("ac_type")+"' and ac_no="+rs_mst.getInt("ac_no")+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(lstintdt)+"' and '"+Validations.convertYMD(Validations.addDays(todaydt,-1))+"' order by trn_seq");
			int i=0;
			String lsttrndt="";
			
			while(rs_trn.next())
			{
				System.out.println("1............3");
				if(rs_mst.getInt("int_rate_type")==1)	//For Floating
				{	
				    System.out.println("AAAAAAAAAAAAAAAAAAA");
					if(i==0)
					{	
					    System.out.println("BBBBBBBBBBBBBBBBBB");						
						if(rs_trn.getDouble("cl_bal")<0)
						{
							System.out.println("CCCCCCCCCCCCCCCCCCC");
							if(ac_type.startsWith("1014"))
								intamt+=calculateInterest(rs_mst.getString("ac_type"),lstintdt,Validations.addDays(rs_trn.getString("trn_date"),-1),-(rs_trn.getDouble("cl_bal")),rs_mst.getInt("custtype"),rs_mst.getInt("period"),-(rs_trn.getDouble("cl_bal")),acno);
							else
							{
								System.out.println("bal= -1.......");
								intamt+=calculateODInterestAmount(rs_mst.getString("ac_type"),rs_mst.getInt("ac_no"),lstintdt,Validations.addDays(rs_trn.getString("trn_date"),-1),sanc_amt,rs_mst.getInt("custtype"),rs_mst.getInt("period"),-(rs_trn.getDouble("cl_bal")));								
							}							
						}
					}	
					else
					{	
					    System.out.println("DDDDDDDDDDDDDDDDDD");
						if(prev_clbal<0)
						{
						    System.out.println("EEEEEEEEEEEEEE");
							if(ac_type.startsWith("1014"))
								intamt+=calculateInterest(rs_mst.getString("ac_type"),lsttrndt,Validations.addDays(rs_trn.getString("trn_date"),-1),-prev_clbal,rs_mst.getInt("custtype"),rs_mst.getInt("period"),-prev_clbal,acno);
								//intamt+=calculateInterest(rs_mst.getString("ac_type"),lstintdt,Validations.addDays(rs_trn.getString("trn_date"),-1),-prev_clbal,rs_mst.getInt("custtype"),rs_mst.getInt("period"),-prev_clbal,acno);
							else
								intamt+=calculateODInterestAmount(rs_mst.getString("ac_type"),rs_mst.getInt("ac_no"),lsttrndt,Validations.addDays(rs_trn.getString("trn_date"),-1),sanc_amt,rs_mst.getInt("custtype"),rs_mst.getInt("period"),-prev_clbal);
								//intamt+=calculateODInterestAmount(rs_mst.getString("ac_type"),rs_mst.getInt("ac_no"),lstintdt,Validations.addDays(rs_trn.getString("trn_date"),-1),sanc_amt,rs_mst.getInt("custtype"),rs_mst.getInt("period"),-prev_clbal);							
						}	
					}					
				}
				else			//For Fixed
				{
					intrate=rs_mst.getDouble("int_rate");
					if(i==0)
					{			
						if(rs_trn.getDouble("cl_bal")<0)	
						{
							intamt+=Math.round((-(rs_trn.getDouble("cl_bal"))*Validations.dayCompare(lstintdt,Validations.addDays(rs_trn.getString("trn_date"),-1))*intrate)/(365*100));
						}
						System.out.println("After Calculation-Interest Amount : "+intamt);					
					}	
					else
					{	
					    System.out.println("FFFFFFFFFFFFFFFF");
						if(prev_clbal<0)
							intamt+=Math.round((-prev_clbal*Validations.dayCompare(lsttrndt,Validations.addDays(rs_trn.getString("trn_date"),-1))*intrate)/(365*100));
							//dee intamt+=Math.round((-prev_clbal*Validations.dayCompare(lstintdt,Validations.addDays(rs_trn.getString("trn_date"),-1))*intrate)/(365*100));
					}
				}

				lsttrndt=rs_trn.getString("trn_date");
				prev_clbal=rs_trn.getDouble("cl_bal");
				penal_intamt+=calculatePenalInt(rs_mst.getString("ac_type"),lstintdt,Validations.addDays(rs_trn.getString("trn_date"),-1),-(rs_trn.getDouble("cl_bal")),rs_mst.getInt("custtype"));
				System.out.println("Penal Interest 1.........=="+penal_intamt);
				System.out.println("Prev Cal_Bal  &&&&&&&&&&&&&&& Trn_Date"+prev_clbal+" "+lsttrndt);				
			}//End of rst_trn..While 
			if(rs_trn.previous())
			{	
				System.out.println("Previous rst_trn...");
				if(rs_mst.getInt("int_rate_type")==1)
				{	
					if(prev_clbal<0)
					{
					    System.out.println("GGGGGGGGGGGGGGGGG"+prev_clbal);
						if(ac_type.startsWith("1014"))
							intamt+=calculateInterest(rs_mst.getString("ac_type"),lsttrndt,Validations.addDays(todaydt,-1),-prev_clbal,rs_mst.getInt("custtype"),rs_mst.getInt("period"),-prev_clbal,acno);
						else
							intamt+=calculateODInterestAmount(rs_mst.getString("ac_type"),rs_mst.getInt("ac_no"),lsttrndt,Validations.addDays(todaydt,-1),sanc_amt,rs_mst.getInt("custtype"),rs_mst.getInt("period"),-prev_clbal);						
					}	
				}
				else
				{	
				    System.out.println("HHHHHHHHHHHHHH"+prev_clbal);
					intrate=rs_mst.getDouble("int_rate");
					if(prev_clbal<0)
						intamt+=Math.round((-prev_clbal*Validations.dayCompare(lsttrndt,Validations.addDays(todaydt,-1))*intrate)/(365*100));
					System.out.println("Interest Amount-- 2 : "+intamt);
					
				}
				penal_intamt+=calculatePenalInt(rs_mst.getString("ac_type"),lstintdt,Validations.addDays(rs_trn.getString("trn_date"),-1),-(rs_trn.getDouble("cl_bal")),rs_mst.getInt("custtype"));
				System.out.println("Penal Interest 2........="+penal_intamt);
			}

			System.out.println("intamt="+intamt);
			if(acno==0 || calc_ind==2)
			{	
				PreparedStatement ps1=connection.prepareStatement("insert into IntPay values('"+rs_mst.getString("ac_type")+"',"+rs_mst.getInt("ac_no")+",'"+todaydt+"','F',"+intamt+","+intrate+",0,0,0,0,0,0,'F','F','"+Utml+"','"+Uid+"','"+Udate+"')");
				result=(ps1.executeUpdate()==1)?0:-1;
				if(result==-1)
					throw new SQLException("Modules not Updated");

			}
			if(acno==0)
			{
				result=(statement.executeUpdate("update Modules set lst_intdt='"+todaydt+"' where modulecode="+ac_type+"")==1)?0:-1;
				if(result==-1)
					throw new SQLException("Modules not Updated");
			}			
			else
			{
//				if(!InterestPosting(Utml,Uid,"",Integer.parseInt(ac_type),acno,intamt))
	//			   throw new SQLException("Interest not posted");
				return "Calculated ==> Interest Amt = "+intamt+" & Penal Interest = "+penal_intamt;
			}
			
			PreparedStatement ps=connection.prepareStatement("Commit");
			ps.execute();


		 }//End of While
		}catch(Exception sql)
		{
			ctx.setRollbackOnly();
			PreparedStatement ps=connection.prepareStatement("rollback");
			ps.execute();
			sql.printStackTrace();
		}
		/*if(acno==0)
		{
			result=(statement.executeUpdate("update Modules set lst_intdt='"+todaydt+"' where modulecode="+ac_type+"")==1)?0:-1;
			if(result==-1)
				throw new SQLException("Modules not Updated");
		}

		return (result==-1?"Not Calculated":"Calculated");*/
		}catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
			try
			{
				ctx.setRollbackOnly();
				result=-1;
			}catch(Exception exception){exception.printStackTrace();}
		}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}
		return (result==-1?"Not Calculated":"Calculated ==> Interest Amt = "+intamt+" & Penal Interest = "+penal_intamt);
	}	
	
	public int getStdInstInfo(String acc_ty, int acc_no)
	{		
		Connection conn=null;
		try
		{
			System.out.println("Inside getStdInstInfo");
			System.out.println("AC TYPE="+acc_ty);	
			System.out.println("AC NO="+acc_no);	
			ResultSet rs;
			conn=getConnection();
			Statement stmt =conn.createStatement();
			
			rs=stmt.executeQuery("select * from StdInst where del_ind='F' and (fr_ac_ty='"+acc_ty+"' and fr_ac_no="+acc_no+") ||(to_ac_ty='"+acc_ty+"' and to_ac_no="+acc_no+") and ve_tml is not NULL ");
			
			if(rs.next())
			{
				System.out.println("Insideeeeee STDINST=");
				return -1;
			}
		} catch (SQLException excep)
		{
			excep.printStackTrace();
			try
			{
				ctx.setRollbackOnly();				
			}catch(Exception exception){exception.printStackTrace();}		
		}
		finally
		{
			try
			{
				conn.close();
			}catch(Exception ex){ex.printStackTrace();}
		}
		return 1;
	}
	
	public IntPayObject[] getIntPay(String query)
	{
		IntPayObject ip[]=null;
		ResultSet rs_acmst=null;
		ResultSet rs_odcc=null;
		int no_rows=0;
		Connection connection=null;
		Statement statement=null;
		Statement statement1=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			statement1=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			if(query!=null)
				rs_acmst=statement.executeQuery("select ip.*,am.cid,concat(IFNULL(cm.fname,''),IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name,Modules.moduleabbr from IntPay ip,AccountMaster am,CustomerMaster cm,Modules where ip.ac_no=am.ac_no and ip.ac_type=am.ac_type and am.cid=cm.cid and am.ac_type=Modules.modulecode and ip.post_ind='F' and  ("+query+") order by ip.ac_type,ip.ac_no");
			else
				rs_acmst=statement.executeQuery("select ip.*,am.cid,concat(IFNULL(cm.fname,''),IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name,Modules.moduleabbr from IntPay ip,AccountMaster am,CustomerMaster cm,Modules where ip.ac_no=am.ac_no and ip.ac_type=am.ac_type and am.cid=cm.cid and am.ac_type=Modules.modulecode and ip.post_ind='F' order by ip.ac_type,ip.ac_no");
			
				if(rs_acmst.next())
				{	
					rs_acmst.last();
					no_rows=rs_acmst.getRow();
					ip=new IntPayObject[rs_acmst.getRow()];
					rs_acmst.beforeFirst();
				}
				if(query!=null)
					rs_odcc=statement1.executeQuery("select ip.*,am.cid,concat(IFNULL(cm.fname,''),IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name,Modules.moduleabbr from IntPay ip,ODCCMaster am,CustomerMaster cm,Modules where ip.ac_no=am.ac_no and ip.ac_type=am.ac_type and am.cid=cm.cid and am.ac_type=Modules.modulecode and ip.post_ind='F' and ("+query+") order by ip.ac_type,ip.ac_no");
				else 
					rs_odcc=statement1.executeQuery("select ip.*,am.cid,concat(IFNULL(cm.fname,''),IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name,Modules.moduleabbr from IntPay ip,ODCCMaster am,CustomerMaster cm,Modules where ip.ac_no=am.ac_no and ip.ac_type=am.ac_type and am.cid=cm.cid and am.ac_type=Modules.modulecode and ip.post_ind='F' order by ip.ac_type,ip.ac_no");
				
				if(rs_odcc.next())
				{	
					rs_odcc.last();
					ip=new IntPayObject[no_rows+rs_odcc.getRow()];
					rs_odcc.beforeFirst();
				}
	
				
				int i=0;
				while(rs_acmst.next())
				{
					ip[i]=new IntPayObject();
					ip[i].setAccType(rs_acmst.getString("Modules.moduleabbr"));
					ip[i].setAccNo(rs_acmst.getInt("ac_no"));					
					ip[i].setTrnDate(rs_acmst.getString("ip.trn_date"));
					ip[i].setName(rs_acmst.getString("name"));
				
					ip[i].initarray(6);
					int x=0;
					for(int j=0;j<6;j++)
						ip[i].prod[j]=rs_acmst.getDouble("prod_"+(++x));
						
					ip[i].setIntAmt(rs_acmst.getDouble("trn_amt"));
					ip[i].setCalBy(rs_acmst.getString("de_user"));
					ip[i].setIntRate(rs_acmst.getDouble("int_rate"));

				
					i++;
					
				}
				
				while(rs_odcc.next())
				{
					ip[i]=new IntPayObject();
					ip[i].setAccType(rs_odcc.getString("Modules.moduleabbr"));
					ip[i].setAccNo(rs_odcc.getInt("ac_no"));
					ip[i].setName(rs_odcc.getString("name"));
				
					ip[i].initarray(6);
					int x=0;
					for(int j=0;j<6;j++)
						ip[i].prod[j]=rs_odcc.getDouble("prod_"+(++x));
						
					ip[i].setIntAmt(rs_odcc.getDouble("trn_amt"));
					ip[i].setCalBy(rs_odcc.getString("de_user"));
					ip[i].setIntRate(rs_odcc.getDouble("int_rate"));

				
					i++;
					
				}

				
				
					
			return ip;
		}catch(Exception sqlexception){
			sqlexception.printStackTrace();
			}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}



		return ip;

	}
	
	/** Added on 11/04/06 to get Inoperative Accounts
	 	@param last_voucher_scroll_no from Modules is used to as max_inoperative_period.
	 	max_inoperative_period is used for making accounts Inoperative. for SB/CA/CC/OD only **/
	
	public AccountMasterObject[] getInoperativeRecords(String ac_type,String date) throws RemoteException
	{
		AccountMasterObject accountmasterobj[]=null;
		ResultSet rs_acmst=null,rs=null;
		ResultSet rs_trn=null;
		//int no_rows=0;
		Connection connection=null;
		Statement statement=null;
		Statement statement1=null;
		int months=0;
		try
		{
		    System.out.println("Inside getInoperativeRecords.............");
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			statement1=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println("1..............."+ac_type);
			if(ac_type!=null)
			{	
				rs=statement.executeQuery("select  lst_voucher_scroll_no from Modules where modulecode='"+ac_type+"'");
				if(rs.next() && rs.getString(1)!=null)
					months=rs.getInt(1);

				
				if(ac_type.startsWith("1002")|| ac_type.startsWith("1007"))
				{
					rs_acmst=statement.executeQuery("select ac_type,ac_no,od.cid,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,int_pbl_date,ac_opendate,ac_status,freeze_ind,last_tr_date,last_tr_seq from AccountMaster od ,CustomerMaster cm where  DATE_ADD(concat(right(last_tr_date,4),'-',mid(last_tr_date,locate('/',last_tr_date)+1, (locate('/',last_tr_date,4)-locate('/',last_tr_date)-1)),'-',left(last_tr_date,locate('/',last_tr_date)-1)),interval "+months+" month) <'"+Validations.convertYMD(date)+"' and od.ac_type='"+ac_type+"' and cm.cid=od.cid and ac_status='O'  order by ac_no;");
					//rs_acmst=statement.executeQuery("select distinct AccountMaster.ac_no from CustomerMaster,CustomerAddr,AccountMaster,GLKeyParam,Modules where CustomerAddr.addr_type=AccountMaster.addr_type and CustomerMaster.cid=AccountMaster.cid and CustomerAddr.cid=CustomerMaster.cid and AccountMaster.ac_type='"+ac_type+"' and GLKeyParam.ac_type='"+ac_type+"' and Modules.modulecode='"+ac_type+"' and GLKeyParam.code=CustomerMaster.custtype+1 and AccountMaster.ac_status='O' and AccountMaster.ve_user is not null order by ac_no ");
				}
				else if(ac_type.startsWith("1014")|| ac_type.startsWith("1015"))
				{
					//rs_acmst=statement.executeQuery("select distinct ODCCMaster.ac_no from CustomerMaster,CustomerAddr,ODCCMaster,GLKeyParam,Modules where CustomerAddr.addr_type=ODCCMaster.addr_type and CustomerMaster.cid=ODCCMaster.cid and CustomerAddr.cid=CustomerMaster.cid and ODCCMaster.ac_type='"+ac_type+"' and GLKeyParam.ac_type='"+ac_type+"' and Modules.modulecode='"+ac_type+"' and GLKeyParam.code=CustomerMaster.custtype+1 and ODCCMaster.ac_status='O' and ODCCMaster.ve_user is not null order by ac_no ");
					rs_acmst=statement.executeQuery("select ac_type,ac_no,od.cid,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,int_uptodt,ac_opendate,ac_status,freeze_ind,last_tr_date,last_tr_seq from ODCCMaster od ,CustomerMaster cm where  DATE_ADD(concat(right(last_tr_date,4),'-',mid(last_tr_date,locate('/',last_tr_date)+1, (locate('/',last_tr_date,4)-locate('/',last_tr_date)-1)),'-',left(last_tr_date,locate('/',last_tr_date)-1)),interval "+months+" month) <'"+Validations.convertYMD(date)+"' and od.ac_type='"+ac_type+"' and cm.cid=od.cid and ac_status='O'  order by ac_no;");
				}
			}
			if(rs_acmst.next())
			{	
					System.out.println("Inside deesp");
					rs_acmst.last();
					accountmasterobj=new AccountMasterObject[rs_acmst.getRow()];
					rs_acmst.beforeFirst();
			}
			
			int i=0;
			while(rs_acmst.next())
			{	
				int ac_no=0;
				accountmasterobj[i]=new AccountMasterObject();
				
		        accountmasterobj[i].setAccType(ac_type);
		        accountmasterobj[i].setAccNo(rs_acmst.getInt("ac_no"));
	        	accountmasterobj[i].setCid(rs_acmst.getInt("cid"));
	        	accountmasterobj[i].setAccName(rs_acmst.getString("name"));
			    accountmasterobj[i].setAccOpenDate(rs_acmst.getString("ac_opendate"));
			    accountmasterobj[i].setAccStatus(rs_acmst.getString("ac_status"));			        
			    accountmasterobj[i].setLastTrnDate(rs_acmst.getString("last_tr_date"));			        
			    accountmasterobj[i].setFreezeInd(rs_acmst.getString("freeze_ind"));
			     
	        	
				if(ac_type.startsWith("1002")|| ac_type.startsWith("1007"))
				{
					accountmasterobj[i].setIntrstPayDate(rs_acmst.getString("int_pbl_date"));
					ac_no=rs_acmst.getInt("ac_no");
					rs_trn=statement1.executeQuery("select cl_bal from AccountTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and trn_seq="+rs_acmst.getInt("last_tr_seq")+" ");
				}
				else
				{
					accountmasterobj[i].setIntrstPayDate(rs_acmst.getString("int_uptodt"));
					ac_no=rs_acmst.getInt("ac_no");
					rs_trn=statement1.executeQuery("select cl_bal from ODCCTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and trn_seq="+rs_acmst.getInt("last_tr_seq")+" ");
				}
			    System.out.println("Ac_no===="+ac_no);

			    if(rs_trn.next())
			    {
				    accountmasterobj[i].setCloseBal(rs_trn.getDouble("cl_bal"));
				}
			    i++;
			}
			return accountmasterobj;
		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}
		return accountmasterobj;
	}
	
	/**
	 * Added by deepa on 11/04/06
	 * Added to Store Accounts whict are made Inoperative.Inoperative process on client side calls this process.
	 * @param std_inst in Modules table is used to check min period to make an account Inoperative.
	 * AccountMaster & AccountMasterLog are updated.*/
	
	public int storeInoperativeAccounts(AccountMasterObject[] at) throws RemoteException
	{
		int a=0;
		ResultSet rs=null;
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			for(int i=0;i<at.length;i++)
			{
				System.out.println("Ac No=="+at[i].getAccNo());
				System.out.println("Ac Type=="+at[i].getAccType());
				if(at[i].getAccType().startsWith("1002")||at[i].getAccType().startsWith("1007"))
				{
					System.out.println("SB/CA");
					if((a=statement.executeUpdate("update AccountMaster set ac_status='I' where ac_no='"+at[i].getAccNo()+"' and ac_type='"+at[i].getAccType()+"'"))==0)
						throw new SQLException("AccountMaster not updated");
					
					if((a=statement.executeUpdate("insert into AccountMasterLog select * from AccountMaster where ac_type='"+at[i].getAccType()+"' and ac_no="+at[i].getAccNo()+""))==0)
						throw new SQLException("AccountMasterLog not Inserted");
				}
				else
				{
					System.out.println("ODCC");
					if((a=statement.executeUpdate("update ODCCMaster set ac_status='I' where ac_no='"+at[i].getAccNo()+"' and ac_type='"+at[i].getAccType()+"'"))==0)
						throw new SQLException("ODCCMaster not updated");	
			
					if((a=statement.executeUpdate("insert into ODCCMasterLog select * from ODCCMaster where ac_type='"+at[i].getAccType()+"' and ac_no="+at[i].getAccNo()+""))==0)
						throw new SQLException("ODCCMasterLog not Inserted");
				}
				if(statement.executeUpdate("insert into FreezedAccounts values('"+at[i].getAccType()+"',"+at[i].getAccNo()+",'InOperative','"+Validations.convertYMD(at[i].getIdIssueDate())+"',null,'I')")!=1)
					throw new SQLException("FreezedAccounts not updated");
			}
		}catch(SQLException sqlexception)
		{
			ctx.setRollbackOnly();
			sqlexception.printStackTrace();
			try
			{
				ctx.setRollbackOnly();
				a=0;
			}catch(Exception exception)
			{
				exception.printStackTrace();}
		}
		catch(Exception e){e.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}
		return(a);
	}

	
	public boolean InterestPosting(String UTml,String Uid,String UdateTime,String modname,int modcode,int acno,double amt,Object[][] obj) throws CreateException
	{
		System.out.println("Posting Interest Amount to the Account.....");
		System.out.println("Acc No: "+acno);
		AccountTransObject at=null,at_odcc=null;
		String date=null,udate=null;
		ResultSet rs1 = null,rs2 = null;
		boolean b=false;
		int del=0;
		double amount_ind=0;
		double amount_ins=0;
		ResultSet rs=null;
		boolean post=false;
		/*GLTransObject trn_obj[]=new GLTransObject();*/
		Connection connection=null;
		Statement statement=null;
		Vector vt_odcc=null;
		int count=0,temp_count=0;
		String trn_type=null;
		try
		{	commonlocal=commonlocalhome.create();
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			Statement stat=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			udate=UdateTime.substring(0,10);
			System.out.println("modcode="+modcode+" "+modname);
			GLTransObject trn_obj = null;
			
			if(acno==0)
			{				
				if(String.valueOf(modcode).startsWith("1002") || String.valueOf(modcode).startsWith("1007") || String.valueOf(modcode).startsWith("1017") || String.valueOf(modcode).startsWith("1018"))
					rs=statement.executeQuery("select distinct ip.*,cm.custtype from IntPay ip,AccountMaster am,CustomerMaster cm where post_ind='F' and ip.ac_type='"+modcode+"'  and am.ac_type=ip.ac_type and am.ac_no=ip.ac_no and cm.cid=am.cid and ip.trn_date='"+udate+"' order by ip.ac_type ");
				else if(String.valueOf(modcode).startsWith("1015"))
					rs=statement.executeQuery("select distinct ip.*,cm.custtype  from IntPayOdcc ip,ODCCMaster odccm,CustomerMaster cm where ip.ac_type='"+modcode+"' and ip.trn_date='"+udate+"' and odccm.ac_type=ip.ac_type and odccm.ac_no=ip.ac_no  and cm.cid=odccm.cid  order by ip.ac_type,ip.ac_no");
				else if(String.valueOf(modcode).startsWith("1014"))
					//rs=statement.executeQuery("select ip.*,cm.custtype  from IntPayOdcc ip,ODCCMaster odccm,CustomerMaster cm,StockInspectionDetails stk where  ip.ac_type='"+modcode+"' and odccm.ac_type=ip.ac_type and odccm.ac_no=ip.ac_no and cm.cid=odccm.cid and stk.ac_type=odccm.ac_type and stk.ac_no=odccm.ac_no and stk.insp_date =(select insp_date from StockInspectionDetails stk2 where stk2.ac_type=odccm.ac_type and stk2.ac_no=odccm.ac_no order by concat(right(stk2.insp_date,4),'-',mid(stk2.insp_date,locate('/',stk2.insp_date)+1, (locate('/',stk2.insp_date,4)-locate('/',stk2.insp_date)-1)),'-',left(stk2.insp_date,locate('/',stk2.insp_date)-1)) desc limit 1) order by ip.ac_type");
					rs=statement.executeQuery("select distinct  ip.*,cm.custtype  from IntPayOdcc ip,ODCCMaster odccm,CustomerMaster cm where ip.ac_type='"+modcode+"' and ip.trn_date='"+udate+"' and odccm.ac_type=ip.ac_type and odccm.ac_no=ip.ac_no  and cm.cid=odccm.cid  order by ip.ac_type,ip.ac_no");
				
			}
			else
			{
				if(String.valueOf(modcode).startsWith("1015"))
					rs=statement.executeQuery("select distinct  ip.*,cm.custtype  from IntPayOdcc ip,ODCCMaster odccm,CustomerMaster cm where ip.ac_type='"+modcode+"' and ip.ac_no="+acno+" and ip.trn_date='"+udate+"' and odccm.ac_type=ip.ac_type and odccm.ac_no=ip.ac_no  and cm.cid=odccm.cid  order by ip.ac_type,ip.ac_no");
				else  if(String.valueOf(modcode).startsWith("1014"))
					//rs=statement.executeQuery("select odccm.ac_type,odccm.ac_no,(credit_limit+cl_bal) as bal,cm.cid,cm.custtype  from ODCCMaster odccm,ODCCTransaction odcct,CustomerMaster cm,StockInspectionDetails stk,IntPayOdcc temp  where   temp.ac_type=odccm.ac_type and temp.ac_no=odccm.ac_no and temp.trn_date='"+udate+"' and odccm.ac_type='"+modcode+"' and odccm.ac_no="+acno+" and odcct.ac_type=odccm.ac_type and odcct.ac_no=odccm.ac_no and odccm.cid=cm.cid and trn_seq in(select max(trn_seq) from ODCCTransaction odcctrn where odcctrn.ac_type=odccm.ac_type and odcctrn.ac_no=odccm.ac_no) and stk.insp_date =(select insp_date from StockInspectionDetails stk2 where stk2.ac_type=odccm.ac_type and stk2.ac_no=odccm.ac_no order by concat(right(stk2.insp_date,4),'-',mid(stk2.insp_date,locate('/',stk2.insp_date)+1, (locate('/',stk2.insp_date,4)-locate('/',stk2.insp_date)-1)),'-',left(stk2.insp_date,locate('/',stk2.insp_date)-1)) desc limit 1)");
					rs=statement.executeQuery("select distinct ip.*,cm.custtype  from IntPayOdcc ip,ODCCMaster odccm,CustomerMaster cm where ip.ac_type='"+modcode+"' and ip.ac_no="+acno+" and ip.trn_date='"+udate+"' and odccm.ac_type=ip.ac_type and odccm.ac_no=ip.ac_no  and cm.cid=odccm.cid  order by ip.ac_type,ip.ac_no");
				else
					//ship......24/11/2006
					//rs = statement.executeQuery("select acmst.*,cm.custtype,at.trn_seq from AccountMaster acmst,CustomerMaster cm,AccountTransaction at,IntPay ip where acmst.ac_type='"+modcode+"' and acmst.ac_no="+acno+" and acmst.cid=cm.cid and acmst.ac_type=at.ac_type and acmst.ac_no=at.ac_no and ip.trn_date='"+udate+"' order by at.trn_seq desc limit 1");
					rs=statement.executeQuery("select distinct ip.*,cm.custtype from IntPay ip,AccountMaster am,CustomerMaster cm where post_ind='F' and ip.ac_type='"+modcode+"'  and ip.ac_no="+acno+" and am.ac_type=ip.ac_type and am.ac_no=ip.ac_no and cm.cid=am.cid and ip.trn_date='"+udate+"' order by ip.ac_type ");
				    ///////
			}
			 
			System.out.println("1.......");						
			while(rs.next())
			{
				/*if(String.valueOf(modcode).startsWith("1014") || String.valueOf(modcode).startsWith("1015"))
				{	
					System.out.println("2.....");
					//if(rs.getDouble("bal")-(acno==0?rs.getDouble("trn_amt"):amt)>0) for Testing
					if(rs.getDouble("bal")-(acno==0?rs.getDouble("trn_amt"):amt)>0 ||rs.getDouble("bal")-(acno==0?rs.getDouble("trn_amt"):amt)<0)
					   post=true;
					System.out.println("3.....");
					System.out.println("Post : "+post);
				}
				else*/
					post=true;
				
				try
				{
					if(post)
					{
						if(rs.getString("ac_type").startsWith("1002") || rs.getString("ac_type").startsWith("1007") || rs.getString("ac_type").startsWith("1017") ||rs.getString("ac_type").startsWith("1018"))
						{
							count=1;
						}
						else{
							vt_odcc=ODCCPost(UTml,Uid,UdateTime,modname,modcode,rs.getInt("ip.ac_no"),obj);
							count=vt_odcc.size();
						}
						temp_count=0;
						System.out.println("no of rows to be inserted="+count);
						System.out.println("4....");
						while(temp_count!= count)
						{
							
							at=new AccountTransObject();
							if(rs.getString("ac_type").startsWith("1002") || rs.getString("ac_type").startsWith("1007") || rs.getString("ac_type").startsWith("1017") ||rs.getString("ac_type").startsWith("1018"))
							{
								at.setCdInd("C");
								at.setTransAmount(rs.getDouble("trn_amt"));
								at.setTransType("I");
								trn_type="I";
								//count=1;
							}
							else{
								at.setCdInd("D");
								//vt_odcc=ODCCPost(UTml,Uid,UdateTime,modname,modcode,rs.getInt("ip.ac_no"),obj);
								//count=vt_odcc.size();
							}

							
							at.setTransNarr("By Interest");
							
							if(rs.getString("ac_type").startsWith("1014") || rs.getString("ac_type").startsWith("1015"))
							{
								System.out.println("temp_count=="+temp_count);
								at_odcc=(AccountTransObject) vt_odcc.get(temp_count);
								at.setTransType(at_odcc.getTransType());
								trn_type=at_odcc.getTransType();
								at.setInt_date(at_odcc.getInt_date());
								System.out.println("ODCCPOST Details...................");
								System.out.println("date="+at_odcc.getInt_date());
								System.out.println("int amt="+at_odcc.getInt_amt());
								System.out.println("trns type="+at_odcc.getTransType());
								at.setTransAmount(at_odcc.getInt_amt());
								if(at_odcc.getTransType().equals("E")) {
									at.setTransNarr("By Penal Interest");
							}
							else if(at_odcc.getTransType().equals("I")) {
									at.setTransNarr("By Interest");
							}
							}
							
							temp_count++;
							
							at.setAccType(rs.getString("ac_type"));
							at.setAccNo(rs.getInt("ac_no"));
							System.out.println("****************************");
							System.out.println(">> at.getAccType()=="+at.getAccType());
							System.out.println(">> at.getAccNo()=="+at.getAccNo());
							System.out.println(">> at.getTransType=="+at.getTransType());
							System.out.println("****************************");
							at.setTransMode("T");
							at.setTransSource(UTml);
							at.setTransDate(udate);
						
							//System.out.println("at trn seq = "+rs.getInt("trn_seq"));
							at.setTransSeqNo(333333);//ship.....24/11/2006
							at.setChqDDNo(0);
							at.setChqDDDate("");
							
							
							at.setRef_No(0);
							at.setPayeeName("");
						
							at.setLedgerPage(0);
							at.uv.setUserTml(UTml);
							at.uv.setUserId(Uid);
							at.uv.setUserDate(UdateTime);
							at.uv.setVerTml(UTml);
							at.uv.setVerId(Uid);
							at.uv.setVerDate(UdateTime);
							at.setGLRefCode(modcode);
							System.out.println(" going to stroe acc trna");
							
							if(commonlocal.storeAccountTransaction(at)==1)
							{
                                System.out.println("<<<<<<<<<< i am not here >>>>>>>>>>>>"+rs.getInt("custtype"));
								if(rs.getInt("custtype")==0){
									amount_ind=amount_ind+at.getTransAmount();
									System.out.println("amount_ind=="+amount_ind);
								}	
								else
									amount_ins=amount_ins+at.getTransAmount();
							
								System.out.println("trn amt="+at.getTransAmount());
									if(rs.getString("ac_type").startsWith("1002") || rs.getString("ac_type").startsWith("1007") || rs.getString("ac_type").startsWith("1017") || rs.getString("ac_type").startsWith("1018"))
									{
										stat.executeUpdate("update AccountMaster set int_pbl_date='"+Validations.addDays(udate,-1)+"' where ac_no="+rs.getInt("ac_no")+" and ac_type='"+rs.getString("ac_type")+"'");
										b=(stat.executeUpdate("update IntPay set post_ind='T' where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and post_ind='F'")==1)?true:false;
										if(!b)
											throw new SQLException("post_ind is not updated in IntPay");	

									}
									else 
									{
										if(at.getTransType().equals("I")|| vt_odcc.size()==1)
										{
											if(at.getTransType().equals("I"))
											b=(stat.executeUpdate("update ODCCMaster set int_uptodt='"+Validations.addDays(udate,-1)+"' where ac_no="+rs.getInt("ac_no")+" and ac_type='"+rs.getString("ac_type")+"'")==1)?true:false;
											
											del=stat.executeUpdate("delete from IntPayOdcc  where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getString("ac_no")+" and trn_date='"+udate+"' ");
											if(del==0)
												throw new SQLException("post_ind is not updated in IntPayOdcc");
										}
									}
										
									
									System.out.println("Ac_type="+rs.getString("ac_type"));
									System.out.println("Ac_no="+rs.getInt("ac_no"));
									
									System.out.println("2........"+b);
								}
							else
								System.out.println("store accc tran error");
						//post=true
					
						PreparedStatement ps=connection.prepareStatement("Commit");
						ps.execute();
				
				    //Inserting into GLTransaction
					//SB Account - Credit
					System.out.println("amount_ind="+amount_ind);
					System.out.println("amount_ins="+amount_ins);
					
				    if(amount_ind!=0)//SB Individual
					{
				    	System.out.println("hi ur account is Individual");
				    	if((String.valueOf(modcode).startsWith("1014") || String.valueOf(modcode).startsWith("1015")) &&  (at.getTransType().startsWith("E") || at.getTransType().startsWith("I")))
				    		rs2=stat.executeQuery("select mult_by,gk.gl_code,gk.gl_type from GLPost gp,GLKeyParam gk where gp.ac_type="+modcode+" and gk.ac_type="+modcode+" and gp.gl_code=gk.gl_code and trn_type='P' and cr_dr='D' and code=1");	
				    	else	
				    		rs2=stat.executeQuery("select mult_by,gk.gl_code,gk.gl_type from GLPost gp,GLKeyParam gk where gp.ac_type="+modcode+" and gk.ac_type="+modcode+" and gp.gl_code=gk.gl_code and trn_type='R' and cr_dr='C' and code=1");
					    
					}
					else if(amount_ins!=0)//SB Institute
					{
						System.out.println("hi ur account is Institute");
						if((String.valueOf(modcode).startsWith("1014") || String.valueOf(modcode).startsWith("1015")) &&  (at.getTransType().startsWith("E") || at.getTransType().startsWith("I")))
				    		rs2=stat.executeQuery("select mult_by,gk.gl_code,gk.gl_type from GLPost gp,GLKeyParam gk where gp.ac_type="+modcode+" and gk.ac_type="+modcode+" and gp.gl_code=gk.gl_code and trn_type='P' and cr_dr='D' and code=2");	
				    	else	
				    		rs2=stat.executeQuery("select mult_by,gk.gl_code,gk.gl_type from GLPost gp,GLKeyParam gk where gp.ac_type="+modcode+" and gk.ac_type="+modcode+" and gp.gl_code=gk.gl_code and trn_type='R' and cr_dr='C' and code=2");
					}
				    System.out.println("------------> rs2==="+rs2);
				    if(rs2!=null){
				    	if(rs2.next())
				    	{
				    		trn_obj = new GLTransObject();
						
				    		trn_obj.setTrnDate(at.getTransDate());
				    		System.out.println("date=="+at.getTransDate()); 
				    		trn_obj.setGLType(rs2.getString(3));
				    		System.out.println("rs2.getString(3)=="+rs2.getString(3));
				    		trn_obj.setGLCode(String.valueOf(rs2.getInt(2)));
				    		System.out.println("rs2.getString(2)=="+rs2.getString(2));
				    		trn_obj.setTrnMode("T");
						//trn_obj.setAmount(at.getTransAmount()*rs2.getInt(1));
				    		if(at.getTransAmount()<0)
				    			trn_obj.setAmount(at.getTransAmount()*(-1));
				    		else
				    			trn_obj.setAmount(at.getTransAmount());
				    		System.out.println("at.getTransAmount()=="+at.getTransAmount());
				    		if((String.valueOf(modcode).startsWith("1014") || String.valueOf(modcode).startsWith("1015")) &&  (at.getTransType().startsWith("E") || at.getTransType().startsWith("I")))
				    			trn_obj.setCdind("D");	
				    		else	
				    			trn_obj.setCdind("C");	
				    		trn_obj.setAccType(at.getAccType());
				    		trn_obj.setAccNo(String.valueOf(at.getAccNo()));
				    		trn_obj.setTrnSeq(at.getTransSeqNo());
				    		trn_obj.setTrnType(trn_type);
				    		trn_obj.setRefNo(0);
				    		trn_obj.setVtml(at.uv.getVerTml());
				    		trn_obj.setVid(at.uv.getVerId());
				    		trn_obj.setVDate(at.uv.getVerDate());
						
				    		System.out.println("trn seq = "+at.getTransSeqNo());
				    		System.out.println("credit amount = "+(at.getTransAmount()*rs2.getInt(1)));
				    		System.out.println("credit Entry to GL Tran ");
						
				    		if(!(commonlocal.storeGLTransaction(trn_obj)==1))
				    			throw new SQLException("GLTransaction not stored");
				    	}
					}
				}
			}
		}catch(Exception sql)
		{
			ctx.setRollbackOnly();
			PreparedStatement ps=connection.prepareStatement("rollback");
			ps.execute();
			sql.printStackTrace();
		}
				
				/////////////////////////
		}
			
				
		
	}
	catch(SQLException sqlexception)
	{
		ctx.setRollbackOnly();
		sqlexception.printStackTrace();
		
	}
		finally
		{
			try
			{
				connection.close();
			}
			catch(Exception ex)
			{
			    ex.printStackTrace();
			}
			commonlocal = null;
		}

		return b;
	}


/**
 * This function stores the details in table ChequeWithdrawal 
 */	
	public int storeCashWithdraw(ChequeObject ch,int type) 
	{
		int result=0;
		Connection connection=null;
		Statement statement=null,stmt_po=null;
		
	//	String main_cashier_terminal=null;		
		ResultSet rs=null,rs_po=null;
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_po=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			rs=statement.executeQuery("select * from ChequeWithdrawal where cash_pd='F' and trn_date='"+ch.getTransDate()+"' and tml_no is null and ve_user is null and token_no="+ch.getTokenNo()+"");
			if(rs.next() || type==1)//1--delete
			{
			    System.out.println("Inside ChequeWithdrawal"+ch.getTransMode());
				if(statement.executeUpdate("delete from ChequeWithdrawal where cash_pd='F' and trn_date='"+ch.getTransDate()+"' and tml_no is null and ve_user is null and token_no="+ch.getTokenNo()+"")!=1)
				    throw new SQLException("old Cheque Withdrawal record not deleted");
				/*if(!ch.getTransMode().equals("PO"))
				{
				    System.out.println("Inside transModeeeeeeeeeee");
				    if(ch.getAccType().startsWith("1002") ||ch.getAccType().startsWith("1007") ||ch.getAccType().startsWith("1017") ||ch.getAccType().startsWith("1018"))
				        statement.executeUpdate("delete from AccountTransaction where ac_type='"+ch.getAccType()+"' and ac_no="+ch.getAccNo()+" and ref_no="+ch.getTokenNo()+" and trn_date='"+getSysDate()+"'and ve_user is null order by trn_seq");
				    else if(ch.getAccType().startsWith("1014")||ch.getAccType().startsWith("1015"))
			        	statement.executeUpdate("delete from ODCCTransaction where ac_type='"+ch.getAccType()+"' and ac_no="+ch.getAccNo()+" and ref_no="+ch.getTokenNo()+" and trn_date='"+getSysDate()+"' and ve_user is null order by trn_seq desc limit 1");
				}*/
				result=1;
			}
						
		   
			if(type!=1)
			{    
			    System.out.println("Inside type!=1::::");
				//ResultSet rs_cashier=statement.executeQuery("select rec_type from Currency_Stock where tml_no=(select tml_code from Terminals where tml_type='M' and cur_date='"+getSysDate()+"') ");
			   /* ResultSet rs_cashier=statement.executeQuery("select cash_close from DailyStatus where trn_dt='"+Validations.convertYMD(ch.getTransDate())+"' ");
				if(rs_cashier.next())
	               main_cashier_terminal=rs_cashier.getString(1);
				
				//if(main_cashier_terminal.equalsIgnoreCase("o"))
				if(main_cashier_terminal.equalsIgnoreCase("F"))
				{*/
				    System.out.println("Inside if Casier is Open");
				    PreparedStatement ps=connection.prepareStatement("insert into ChequeWithdrawal values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,null,null,null)");
				    String acctype=ch.getAccType();
				    System.out.println("acctype is==================================="+ch.getTokenNo());
				    
				    ps.setInt(1,ch.getTokenNo());
				    System.out.println("TRN MODE IS  "+ch.getTransMode());
				    if(ch.getTransMode().equals("PO"))
				    {
				        ps.setString(2,"1016001");
				        rs_po=stmt_po.executeQuery("select payord_no from PayOrder where po_chq_no="+ch.getChqNo()+" ");
				        rs_po.next();				        
				        ps.setInt(3,rs_po.getInt("payord_no"));
				    }
				    else
				    {
				        ps.setString(2,ch.getAccType());
				        ps.setInt(3,ch.getAccNo());
				    }
				    System.out.println(" TRN MODE IS "+ch.getTransMode()+"  token no is "+ch.getTokenNo());
				    ps.setString(4,ch.getTransDate());
				    ps.setString(5,ch.getTransType());
				    ps.setDouble(6,ch.getTransAmount());
				    ps.setString(7,ch.getTransMode());
					ps.setString(8,null);
					ps.setInt(9,ch.getBookNo());
					ps.setInt(10,ch.getChqNo());
					ps.setString(11,ch.getChqDate());
					ps.setString(12,ch.getCashPD());
					ps.setString(13,ch.getPayeeName());
					ps.setString(14,ch.uv.getUserTml());
					ps.setString(15,ch.uv.getUserId());
					ps.setString(16,ch.uv.getUserDate());
					if((result=ps.executeUpdate())!=1)
					    throw new SQLException("ChequeWithdrawal record not stored");
									    
				    /*if(!ch.getTransMode().equals("PO"))
				    {
				        int a=0;
					    rs=null;
				        if(acctype.startsWith("1002") ||acctype.startsWith("1007") ||acctype.startsWith("1017") ||acctype.startsWith("1018"))
				            rs=statement.executeQuery("select trn_seq,cl_bal from AccountTransaction where ac_no="+ch.getAccNo()+" and ac_type='"+ch.getAccType()+"' order by trn_seq desc limit 1");
					    else
					        rs=statement.executeQuery("select trn_seq,cl_bal from ODCCTransaction where ac_no="+ch.getAccNo()+" and ac_type='"+ch.getAccType()+"' order by trn_seq desc limit 1");
							
					    int trnseq=0;
					    double cl_bal=0;
					    if(rs.next())
					    {
					        trnseq=rs.getInt(1);
					        cl_bal=rs.getDouble(2);
					    }
					    else
					        throw new SQLException("Account not found");
					
					    if(acctype.startsWith("1002") ||acctype.startsWith("1007") ||acctype.startsWith("1017") ||acctype.startsWith("1018"))
					        ps=connection.prepareStatement("insert into AccountTransaction values(?,?,'"+getSysDate()+"',?,?,?,?,?,?,?,?,?,?,?,?,null,?,?,'"+getSysDate()+" "+getSysTime()+"',null,null,null)");
					    else
					    {
					        ResultSet rs1=null;
					        stmt=null;
					        rs1=stmt.executeQuery("select int_uptodt from ODCCMaster where ac_type='"+ch.getAccType()+"' and ac_no="+ch.getAccNo()+" ");
					        rs1.next();
					        String lst_int_caldt=rs1.getString("int_uptodt");
					        ps=connection.prepareStatement("insert into ODCCTransaction values(?,?,'"+getSysDate()+"',?,?,?,?,?,?,?,?,?,?,?,?,null,?,?,'"+getSysDate()+" "+getSysTime()+"',null,null,null,'"+lst_int_caldt+"')");
					    }

					    ps.setString(1,ch.getAccType());
					    ps.setInt(2,ch.getAccNo());
					    ps.setString(3,ch.getTransType());
					    ps.setInt(4,trnseq+1);
					    ps.setDouble(5,ch.getTransAmount());
					   	ps.setString(6,"C");
					   	String cd_ind="D";					   	
					   	ps.setString(7,null);	//Changed on 03/02/2006 ch.uv.getUserTml()
						ps.setString(8,"D");
						ps.setDouble(9,ch.getChqNo());
						ps.setString(10,ch.getTransDate());
						ps.setString(11,"Token No"+ch.getTokenNo());
						ps.setInt(12,ch.getTokenNo());
						ps.setString(13,ch.getPayeeName());
						ps.setDouble(14,cd_ind.equals("C")?cl_bal+ch.getTransAmount():cl_bal-ch.getTransAmount());
						ps.setString(15,ch.uv.getUserTml());
						ps.setString(16,ch.uv.getUserId());
						
						if((a=ps.executeUpdate())!=1)
							throw new SQLException("ODCC/AccountTransaction not stored");
						
						if(commonlocal==null)
						    commonlocal=commonlocalhome.create();
						if(ch.getAccType().startsWith("1002") )//|| ch.getAccType().startsWith("1014") || ch.getAccType().startsWith("1015"))//ch.getAccType().startsWith("1007") || ch.getAccType().startsWith("1018")|| ch.getAccType().startsWith("1017"))
						{
						    if((a=(commonlocal.storeNo0fAccountTransactions(ch.getAccType(),ch.getAccNo(),0)==1)?1:0)!=1)
						        throw new SQLException("Could not store Penalty for NoofTransaction >Trns_per_mnth");
						    else
						        System.out.println("Successfully Stored into AccountTransaction if NoofTransaction >Trns_per_mnth");
						}					
																	
						if(ch.getAccType().startsWith("1002") || ch.getAccType().startsWith("1007") ||ch.getAccType().startsWith("1018")||ch.getAccType().startsWith("1017"))
						{
							rs=statement.executeQuery("select trn_seq from AccountTransaction where ac_no="+ch.getAccNo()+" and ac_type='"+ch.getAccType()+"' order by trn_seq desc limit 1");
							if(rs.next())
								statement.executeUpdate("update AccountMaster set last_tr_seq="+rs.getString("trn_seq")+",last_tr_date='"+getSysDate()+"' where ac_type='"+ch.getAccType()+"' and ac_no="+ch.getAccNo()+" ");
						}
						else if(ch.getAccType().startsWith("1014") || ch.getAccType().startsWith("1015"))
						{	
							rs=statement.executeQuery("select trn_seq from ODCCTransaction where ac_no="+ch.getAccNo()+" and ac_type='"+ch.getAccType()+"' order by trn_seq desc limit 1");
							if(rs.next())
								statement.executeUpdate("update ODCCMaster set last_tr_seq="+rs.getString("trn_seq")+",last_tr_date='"+getSysDate()+"' where ac_type='"+ch.getAccType()+"' and ac_no="+ch.getAccNo()+" ");
						}
						System.out.println("AccountTransaction in ChequeWithdrawal");
					  }*/
					return 1;
				/*}
				else
				    return 0;*/
			}	
			return(result);

		}catch(Exception sqlexception)
		{	
			ctx.setRollbackOnly();
			sqlexception.printStackTrace();
			try
			{
				ctx.setRollbackOnly();
				result=0;
			}catch(Exception exception){exception.printStackTrace();}
		}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return(result);
	}
/**
 * gets the details of the last withdraw for the token no passed  
 * @param tokenno
 * @param type 0-->Verification && Updation for ChequeWithdrawal
 * @param type 1-->Cashier For Payment 
 * @return
 */
	//ship......20/11/2006
	//public ChequeObject getCashWithdraw(int tokenno,int type)
	public ChequeObject getCashWithdraw(int tokenno,int type,String date)
	{
		ChequeObject ch=null;
		Connection connection=null;
		Statement statement=null;
		ResultSet rs=null;
		//ship....01/02/2006
		int flag = 0;
		//
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if(type==0)		/** Verfication && Updation ChequeWithdrawal */
			    //ship.....20/11/2006
				//rs=statement.executeQuery("select * from ChequeWithdrawal where cash_pd='F' and tml_no is null and ve_user is null and token_no="+tokenno+" "); //and trn_date='"+getSysDate()+"'
			    rs = statement.executeQuery("select * from ChequeWithdrawal where cash_pd='F' and tml_no is null and ve_user is null and token_no="+tokenno+" and trn_date='"+date+"'");
			    //////////  
			else if(type==1)
			{    
			    //ship.......20/11/2006
				rs = statement.executeQuery("select *,am.cid from ChequeWithdrawal cw,AccountMaster am where cash_pd='F' and tml_no is null and cw.ve_user is not null and token_no="+tokenno+" and cw.ac_type=am.ac_type and cw.ac_no=am.ac_no and trn_date='"+date+"'");//and trn_date='"+getSysDate()+"'
				
				if(!rs.next())
				{    
				    rs=statement.executeQuery("select *,om.cid from ChequeWithdrawal cw,ODCCMaster om where cash_pd='F' and tml_no is null and cw.ve_user is not null and token_no="+tokenno+" and cw.ac_type=om.ac_type and cw.ac_no=om.ac_no and trn_date='"+date+"'");// and trn_date='"+getSysDate()+"' 
				    //ship.....01/02/2006
				    if(!rs.next())
				    {
				        rs = statement.executeQuery("select * from ChequeWithdrawal where cash_pd='F' and tml_no is null and ve_user is not null and token_no="+tokenno+" and trn_date='"+date+"'"); //and trn_date='"+getSysDate()+"'
				        flag = 1;
				    }
				    else
				        rs.beforeFirst();
				}
				else
				    rs.beforeFirst();
				////////////
			}

			if(rs.next())
			{
				ch=new ChequeObject();
				ch.setTokenNo(rs.getInt(1));
				
				ch.setAccType(rs.getString(2));
				ch.setAccNo(rs.getInt(3));
				ch.setTransDate(rs.getString(4));
				ch.setTransType(rs.getString(5));
				ch.setTransAmount(rs.getDouble(6));
				ch.setTransMode(rs.getString(7));
				
				ch.setBookNo(rs.getInt(9));
				ch.setChqNo(rs.getInt(10));				
				ch.setChqDate(rs.getString(11));
				ch.setPayeeName(rs.getString(13));
				
				ch.uv.setUserTml(rs.getString(14));
				ch.uv.setUserId(rs.getString(15));
				ch.uv.setUserDate(rs.getString(16));
				ch.uv.setVerTml(rs.getString(17));
				ch.uv.setVerId(rs.getString(18));
				ch.uv.setVerDate(rs.getString(19));
				
				//ship.....01/02/2006
				if(type==1 && flag==0)
				    ch.setCustomerId(rs.getInt("cid"));
				else if(type==1 && flag==1)
					ch.setCustomerId(0);
				//
			}
		}
		catch(Exception sqlexception)
		{
		    sqlexception.printStackTrace();
		}
		finally
		{
			try
			{
				connection.close();
			}
			catch(Exception ex)
			{
			    ex.printStackTrace();
			}
		}

		return ch;
	}
	
	public int verifyChequeWithDrawal(AccountTransObject at)
	{
		int a=0;
		ResultSet rs=null,rs1=null,rs_csh=null,rs_gl=null,rs_po=null,rs_trn=null;
		PreparedStatement ps=null;
		Connection connection=null;
		Statement statement=null,stmt=null,stmt_po=null;
		String lst_int_caldt;		
		
		try
		{
		    System.out.println("Inside Cheque Verification..........");
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_po=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if(commonlocal==null)
				commonlocal = commonlocalhome.create();
			
			String main_cashier_terminal=null;
			//ResultSet rs_cashier=statement.executeQuery("select rec_type from Currency_Stock where tml_no=(select tml_code from Terminals where tml_type='M' and cur_date='"+getSysDate()+"') ");
			ResultSet rs_cashier=statement.executeQuery("select cash_close from DailyStatus where trn_dt='"+Validations.convertYMD(at.getTransDate())+"' ");
			if(rs_cashier.next())
               main_cashier_terminal=rs_cashier.getString(1);
			
			if(main_cashier_terminal.equalsIgnoreCase("F"))
			{			   
			    String ac_ty=at.getAccType();
			    int trn_seq=0;
			    System.out.println("Tranaction Mode==========="+at.getTransMode());
			    if(!at.getTransMode().equals("PO"))
			    {  			       
			        if(ac_ty.startsWith("1002")||ac_ty.startsWith("1007")||ac_ty.startsWith("1017")||ac_ty.startsWith("1018"))
			            rs_trn=statement.executeQuery("select last_tr_seq from AccountMaster where ac_type='"+at.getAccType()+"' and ac_no="+at.getAccNo()+"  ");
			        else	
			            rs_trn=statement.executeQuery("select last_tr_seq from ODCCMaster where ac_type='"+at.getAccType()+"' and ac_no="+at.getAccNo()+" ");
			        rs_trn.next();			        
			        trn_seq=rs_trn.getInt("last_tr_seq")+1;
			    }
			    			    
			    if(!at.getTransMode().equals("PO"))
			    {  	
			    	System.out.println("VE TML="+at.uv.getVerTml());
			    	System.out.println("VE USER="+at.uv.getVerId());
			    	System.out.println("REF NO="+at.getRef_No());
			    	System.out.println("AC TYPE="+at.getAccType());
			    	System.out.println("AC NO="+at.getAccNo());
			        if((a=statement.executeUpdate("update ChequeWithdrawal set ve_tml='"+at.uv.getVerTml()+"',ve_user='"+at.uv.getVerId()+"',ve_date='"+at.uv.getVerDate()+"' where token_no="+at.getRef_No()+" and tml_no is null and ac_type='"+at.getAccType()+"' and ac_no="+at.getAccNo()+" and ve_tml is null"))!=1)
			            throw new SQLException("Cheque Withdraw not verified");	
			        
			        System.out.println("AC TYPE="+at.getAccType());
			    	System.out.println("AC NO="+at.getAccNo());
			    	System.out.println("BOOK NO="+at.getPassBkSeq());
			    	System.out.println("CHQ NO="+at.getChqDDNo());
			        if(at.getTransMode().equals("Q"))
				    {	
			        	if((a=statement.executeUpdate("update ChequeNo set chq_del='T',chq_payee='"+at.getPayeeName()+"',chq_amt='"+at.getTransAmount()+"',chq_iss_dt='"+at.getTransDate()+"' where chq_no="+at.getChqDDNo()+" and book_no="+at.getPassBkSeq()+" and ac_type='"+at.getAccType()+"' and ac_no="+at.getAccNo()+""))!=1)
				            throw new SQLException("ChequeNo not updated");
				    }
			    }
			    
			    else if(at.getTransMode().equals("PO"))	
			    {
			        System.out.println("Token nnnooo:"+at.getRef_No());
			        System.out.println();
			        if((a=statement.executeUpdate("update ChequeWithdrawal set ve_tml='"+at.uv.getVerTml()+"',ve_user='"+at.uv.getVerId()+"',ve_date='"+at.uv.getVerDate()+"' where token_no="+at.getRef_No()+" and tml_no is null and ve_tml is null and trn_date='"+at.getTransDate()+"'"))!=1)
				        throw new SQLException("Cheque Withdraw not verified");	
			        System.out.println("Inside PO");
			        if((a=statement.executeUpdate("update PayOrder set po_csh_ind=1,po_csh_dt='"+at.getTransDate()+"' where po_chq_no="+at.getChqDDNo()+""))!=1)
			            throw new SQLException("PayOrder not updated");
			    }
			     
			    try
			    {
			       System.out.println("Inside insert DayDCashhhhhhhhhhhhh"); 
			       rs_csh= statement.executeQuery("select * from ChequeWithdrawal where cash_pd='F' and trn_date='"+at.getTransDate()+"' and tml_no is null and ve_user is not null and token_no='"+at.getRef_No()+"' ");
			       rs_csh.next();
			       /*PreparedStatement psmt= connection.prepareStatement("insert into DayCash values ('"+getSysDate()+"',?,?,'P',?,'C',?,?,?,?,'F',?,?,?,?,?,?)");*/
			       PreparedStatement psmt= connection.prepareStatement("insert into DayCash (trn_date,ac_type,ac_no,trn_type,csh_amt,cd_ind,name,token_no,trn_seq,chq_no,attached,de_tml,de_date,de_user,ve_user,ve_tml,ve_date) values('"+at.getTransDate()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			       
			       if(at.getTransMode().equals("PO"))
			       {	
			           psmt.setString(1,"1016001");	
			           psmt.setInt(8,0);
			           rs_po=stmt_po.executeQuery("select payord_no from PayOrder where po_chq_no="+at.getChqDDNo()+" ");				      
			           rs_po.next();	           
			           psmt.setInt(2,rs_po.getInt("payord_no"));			           
			       }
			       else
			       {
			           psmt.setString(1,rs_csh.getString("ac_type"));
			           psmt.setInt(2,rs_csh.getInt("ac_no"));
			           psmt.setInt(8,trn_seq);
			       }
			       System.out.println("DayCash :;;"+rs_csh.getInt("token_no"));			       
			       System.out.println("Transeq::::"+trn_seq);
			       
			       psmt.setString(3,"P");			       
			       psmt.setDouble(4,rs_csh.getDouble("trn_amt"));
			       psmt.setString(5,"C");
			       psmt.setString(6,rs_csh.getString("payee_nm"));
			       psmt.setInt(7,rs_csh.getInt("token_no"));			      
			       psmt.setInt(9,rs_csh.getInt("chq_no"));
			       psmt.setString(10,"F");
			       psmt.setString(11,rs_csh.getString("de_tml"));	
			       psmt.setString(12,rs_csh.getString("de_date"));
			       psmt.setString(13,rs_csh.getString("de_user"));
			       psmt.setString(14,rs_csh.getString("ve_user"));
			       psmt.setString(15,rs_csh.getString("ve_tml"));
			       psmt.setString(16,rs_csh.getString("ve_date"));
			       
			       psmt.executeUpdate();
			       System.out.println("Finished insert DayDCashhhhhhhhhhhhh");
			    }
			    catch (SQLException daycashException){daycashException.printStackTrace();}
			    
			    try
			    {
			    	 if(!at.getTransMode().equals("PO"))
				    {
			    	 	/** Changed on 09/06/2006 To Store After Verification only..... plz no more changes again*/
					    at.setChqDDNo(rs_csh.getInt("chq_no"));
					    at.setChqDDDate(rs_csh.getString("chq_dt"));
						at.setTransNarr("TokenNo "+rs_csh.getInt("token_no"));
						at.setTransMode("");
						at.setTransMode("C");
						at.setTransType("P");
						
						if(commonlocal==null)
						    commonlocal=commonlocalhome.create();
						commonlocal.storeAccountTransaction(at);
						System.out.println("AccountTransaction in ChequeWithdrawal");
					  }
			    	
			    	System.out.println("inside GlTransaction");
			    	System.out.println("at Trans Mode="+at.getTransMode());
			    	System.out.println("at cd_ind="+at.getCdInd());
			    	System.out.println("at trn_type="+at.getTransType());
			    	System.out.println("at acctype="+at.getAccType());
			        if(at.getTransMode().equals("PO"))
			            rs_gl=statement.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type='1016001' and code=3");
			        else			        
			            rs_gl=statement.executeQuery("select gk.gl_code,gk.gl_type,gp.mult_by from GLKeyParam gk,GLPost gp where gk.ac_type='"+at.getAccType()+"' and gp.trn_type='"+at.getTransType()+"' and gp.cr_dr='"+at.getCdInd()+"' and gk.ac_type=gp.ac_type and gp.gl_code=gk.gl_code ");
			           
			        GLTransObject trn_obj=null;
					rs_gl.last();
					rs_gl.beforeFirst();
					//int i=0;
			        if(rs_gl.next())
					{	
						if(at.getCdInd().equals("D"))
						{
							//code added by Swapna
							if(at.getTransMode().equals("PO")){
							  trn_obj=new GLTransObject();
							  
							  trn_obj.setGLType(rs_gl.getString(2));	
		                      trn_obj.setGLCode(rs_gl.getString(1));			                  		                  
		                      trn_obj.setCdind("D");
		                      trn_obj.setTrnDate(at.getTransDate());
		                      trn_obj.setTrnMode("C");
		                      trn_obj.setAmount(at.getTransAmount());
		                      trn_obj.setTrnType("P");//doubt
		                      trn_obj.setRefNo(at.getRef_No());
		                      trn_obj.setVtml(at.uv.getVerTml());
		                      trn_obj.setVid(at.uv.getVerId());
		                      trn_obj.setVDate(at.uv.getVerDate());
		                      if(at.getTransMode().equals("PO"))
		                      {
		                          trn_obj.setAccType("1016001");
		                          trn_obj.setAccNo(String.valueOf(rs_po.getInt("payord_no")));
		                          trn_obj.setTrnSeq(0);
		                      }
		                      else
		                      {			                          
		                      	trn_obj.setAccType(at.getAccType());
		                      	trn_obj.setAccNo(String.valueOf(at.getAccNo()));
		                      	trn_obj.setTrnSeq(trn_seq);			                      	
		                      }	
		                      int res_cshgl=(commonlocal.storeGLTransaction(trn_obj)==1)?1:0;
		                      if(res_cshgl==0)
								throw new SQLException();
							}
							
						      System.out.println("....2714....");
			                  rs_csh= statement.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type='1019001' and code=1 ");
			                  if(rs_csh.next())
			                  {
			                	  trn_obj=new GLTransObject();
			                      trn_obj.setGLType(rs_csh.getString(2));	
			                      trn_obj.setGLCode(rs_csh.getString(1));			                  		                  
			                      trn_obj.setCdind("C");
			                      trn_obj.setTrnDate(at.getTransDate());
			                      trn_obj.setTrnMode("C");
			                      trn_obj.setAmount(at.getTransAmount());
			                      trn_obj.setTrnType("P");
			                      trn_obj.setRefNo(at.getRef_No());
			                      trn_obj.setVtml(at.uv.getVerTml());
			                      trn_obj.setVid(at.uv.getVerId());
			                      trn_obj.setVDate(at.uv.getVerDate());
			                      if(at.getTransMode().equals("PO"))
			                      {
			                          trn_obj.setAccType("1016001");
			                          trn_obj.setAccNo(String.valueOf(rs_po.getInt("payord_no")));
			                          trn_obj.setTrnSeq(0);
			                      }
			                      else
			                      {			                          
			                      	trn_obj.setAccType(at.getAccType());
			                      	trn_obj.setAccNo(String.valueOf(at.getAccNo()));
			                      	trn_obj.setTrnSeq(trn_seq);			                      	
			                      }	
			                      int res_cshgl=(commonlocal.storeGLTransaction(trn_obj)==1)?1:0;
			                      if(res_cshgl==0)
									throw new SQLException();			                  
			                      System.out.println("after calling storeGLTransaction 2731    "+res_cshgl);		                  
			                  }
						}
	                 	System.out.println("after calling storeGLTransaction hsgfdhsdggggggggggg   ");
					}
					else 
						throw new GLCodeNotDefinedException();			
				}catch(SQLException gltranException){gltranException.printStackTrace();}   
			    
			    return 1;
			}
			else
			   return 0; 
			
		}
		catch(Exception sqlexception)
		{
			ctx.setRollbackOnly();
			sqlexception.printStackTrace();
			try
			{
				ctx.setRollbackOnly();
				a=-1;
			}catch(Exception exception){exception.printStackTrace();}
		}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}
		return(a);	
	}

	/**
	 * Function to verify the newly opened Account
	 * It verifies AccountMaster,AccountTransaction tables. In DayCash "attached" is set to "T"
	 * GLPosting - posts to SB/CA/OD/CC GL
	 * It returns 0 if not posted properly else 1
	 */
	public int verifySB(AccountTransObject at) throws CreateException
	{
		int a=0;
		ResultSet rs=null;
		int scode=0;
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			if((a=statement.executeUpdate("update AccountMaster set ve_user='"+at.uv.getVerId()+"', ve_tml='"+at.uv.getVerTml()+"',ve_date='"+at.uv.getVerDate()+"',ac_status='O' where ac_no='"+at.getAccNo()+"' and ac_type='"+at.getAccType()+"'"))==0)
				throw new SQLException("AccountMaster not updated");
			if((a=statement.executeUpdate("update DayCash set trn_seq=1,ac_no='"+at.getAccNo()+"',attached='T',ve_user='"+at.uv.getVerId()+"', ve_tml='"+at.uv.getVerTml()+"',ve_date='"+at.uv.getVerDate()+"' where scroll_no='"+at.getRef_No()+"' and trn_date='"+at.getTransDate()+"'"))==0)
				throw new SQLException("DayCash not updated");
			if((a=statement.executeUpdate("update AccountTransaction set ve_tml='"+at.uv.getVerTml()+"',ve_user='"+at.uv.getVerId()+"',ve_date='"+at.uv.getVerDate()+"' where ac_no='"+at.getAccNo()+"' and ac_type='"+at.getAccType()+"'"))==0)
				throw new SQLException("AccountTransaction not updated");
			if((a=statement.executeUpdate("update AccountMasterLog set ve_tml='"+at.uv.getVerTml()+"',ve_user='"+at.uv.getVerId()+"',ve_date='"+at.uv.getVerDate()+"' where ac_no='"+at.getAccNo()+"' and ac_type='"+at.getAccType()+"'"))==0)
				throw new SQLException("AccountMasterLog not updated");
			
			String sub=at.getAccType().substring(1,4);
			if(sub.equals("007")|| sub.equals("002"))
				rs=statement.executeQuery("select custtype from AccountMaster,CustomerMaster where ac_no="+at.getAccNo()+" and ac_type='"+at.getAccType()+"' and CustomerMaster.cid=AccountMaster.cid");
			else
				rs=statement.executeQuery("select custtype from ODCCMaster,CustomerMaster where ac_no="+at.getAccNo()+" and ac_type='"+at.getAccType()+"' and  CustomerMaster.cid=ODCCMaster.cid");

			if(rs.next())
				scode=rs.getInt(1);
			rs.close();
			
			System.out.println("scode="+scode+" Acc typre"+at.getAccType()+" cd"+at.getCdInd()+" gl "+at.getTransType());
			
			GLTransObject trn_obj[]=null;
			ResultSet rs_cash=null;
			Statement stmt=connection.createStatement() ;
			int i=0;

			rs_cash = stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1019001' and code=1");
			rs_cash.last();
			System.out.println("rows="+rs_cash.getRow());
			trn_obj=new GLTransObject[rs_cash.getRow()];
			rs_cash.beforeFirst();
			while(rs_cash.next()){
				trn_obj[i]=new GLTransObject();
				trn_obj[i].setGLCode(rs_cash.getString("gl_code"));
	            trn_obj[i].setTrnDate(at.getTransDate());
	            trn_obj[i].setGLType(rs_cash.getString("gl_type"));
	            trn_obj[i].setTrnMode("C");
	            trn_obj[i].setAmount(at.getTransAmount());
	            trn_obj[i].setCdind("D");
	            trn_obj[i].setAccType(at.getAccType());
				trn_obj[i].setAccNo(String.valueOf(at.getAccNo()));
	            trn_obj[i].setTrnSeq(1);//ship......20/12/2005
	            trn_obj[i].setTrnType("R");
	            trn_obj[i].setRefNo(at.getRef_No());
				trn_obj[i].setVtml(at.uv.getVerTml());
				trn_obj[i].setVid(at.uv.getVerId());
				trn_obj[i].setVDate(at.uv.getVerDate());
				i++;
				if((commonlocalhome.create()).storeGLTransaction(trn_obj)==1)
	            {			  
					i=0;
					rs=statement.executeQuery("select gk.gl_code,gk.gl_type,gp.mult_by from GLKeyParam gk,GLPost gp where gk.ac_type="+at.getAccType()+" and gk.code="+(scode+1)+" and gp.trn_type='"+at.getTransType()+"' and gp.cr_dr='"+at.getCdInd()+"' and gk.ac_type=gp.ac_type and gp.gl_code=gk.gl_code");
					rs.last();
					trn_obj=new GLTransObject[rs.getRow()];
					rs.beforeFirst();
					while(rs.next())
					{	
						trn_obj[i]=new GLTransObject();
						trn_obj[i].setTrnDate(at.getTransDate());
						trn_obj[i].setGLType(rs.getString(2));
						trn_obj[i].setGLCode(String.valueOf(rs.getInt(1)));
						trn_obj[i].setTrnMode(at.getTransSource());
						trn_obj[i].setAmount(at.getTransAmount()*rs.getInt(3));
						trn_obj[i].setCdind(at.getCdInd());
						trn_obj[i].setAccType(at.getAccType());
						trn_obj[i].setAccNo(String.valueOf(at.getAccNo()));
						trn_obj[i].setTrnSeq(at.getTransSeqNo());
						trn_obj[i].setTrnType(at.getTransType());
						trn_obj[i].setRefNo(at.getRef_No());
						trn_obj[i].setVtml(at.uv.getVerTml());
						trn_obj[i].setVid(at.uv.getVerId());
						trn_obj[i].setVDate(at.uv.getVerDate());
						i++;
					}
						a=((commonlocalhome.create()).storeGLTransaction(trn_obj)==1)?1:0;
							if(a==0)
								throw new SQLException("GL not Posted");
					
	            }
				else throw new SQLException("GLcode not defined");
			}
			
			


		}catch(SQLException sqlexception)
		{
			ctx.setRollbackOnly();
			sqlexception.printStackTrace();
			try
			{
				ctx.setRollbackOnly();
				a=0;
			}catch(Exception exception)
			{
				exception.printStackTrace();}
		}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}


		return(a);
	}
	
	/*public String getSysDateTime() 
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
	}*/
	
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

	/*public String getSysTime() 
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
	}*/


/*public int storeGL(AccountMasterObject am,int type) 
{
	int a=0;
	int code=0,scode=0;
	ResultSet rs=null;
	try
	{
		String sub=am.getAccType().substring(1,4);
		if(sub.equals("007")|| sub.equals("002"))
			rs=stmt.executeQuery("select sub_category from AccountMaster,CustomerMaster where ac_no="+am.getAccNo()+" and ac_type='"+am.getAccType()+"' and CustomerMaster.cid=AccountMaster.cid");
		else
			rs=stmt.executeQuery("select sub_category from ODCCMaster,CustomerMaster where ac_no="+am.getAccNo()+" and ac_type='"+am.getAccType()+"' and  CustomerMaster.cid=ODCCMaster.cid");
			rs.next();
			scode=rs.getInt(1);
			rs.close();
			
			rs=stmt.executeQuery("select gl_code from GLKeyParam where ac_type='"+am.getAccType()+"' and code="+scode+"");
			if(rs.next())
				code=rs.getInt(1);
			rs.close();
		
			rs=stmt.executeQuery("select mult_by from GLPost where ac_type='"+am.getAccType()+"' and gl_code="+code+" and trn_type='"+am.getTransType()+"' and cr_dr='"+am.getCdInd()+"'");
			int c=0;
			if(rs.next())
			c=rs.getInt(1);

	
			GLTransObject trn_obj=new GLTransObject();

			trn_obj.setTrnDate(getSysDate());
			trn_obj.setGLType("GL");
			trn_obj.setGLCode(String.valueOf(code));
			trn_obj.setTrnSource(am.getTransSource());
	
		
			
			trn_obj.setAmount(am.getTransAmount()*c);
			trn_obj.setCdind(am.getCdInd());
			trn_obj.setAccType(am.getAccType());
			trn_obj.setAccNo(String.valueOf(am.getAccNo()));
			trn_obj.setTrnSeq(am.getTransSeqNo());
			trn_obj.setTrnType(am.getTransType());
			trn_obj.setRefNo(am.getRef_No());
			trn_obj.setVtml(am.uv.getVerTml());
			trn_obj.setVid(am.uv.getVerId());

			a=(storeGLTransaction(trn_obj)==1)?1:0;
			
			
			if(type==1)
			{
				trntype="P";
				cdind="D";
				rs=stmt.executeQuery("select gl_code from GLKeyParam where ac_type=1999001 and code=1");
				if(rs.next())
				code=rs.getInt(1);
				rs.close();
				
				rs=stmt.executeQuery("select mult_by from GLPost where ac_type=1999001 and gl_code="+code+" and trn_type='"+trntype+"' and cr_dr='"+cdind+"'");
				if(rs.next())
				c=rs.getInt(1);
				
				trn_obj.setGLCode(String.valueOf(code));
				trn_obj.setCdind("D");
				trn_obj.setAmount(am.getTransAmount()*c);
				a=(storeGLTransaction(trn_obj)==1)?1:0;
			}
		return a;
	}catch(SQLException sqlexception){sqlexception.printStackTrace();}
			
	return a;
}*/



	public boolean checkToken(int tokenno,String date,int type) 
	{
		Connection connection=null;
		Statement statement=null;
		ResultSet rs=null;
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if(type==1)
			{	
				rs=statement.executeQuery("select token_no,cash_pd from ChequeWithdrawal where trn_date='"+date+"' and token_no="+tokenno);
				while(rs.next())
					if(rs.getString(2).equals("F"))
						return false;
			}
			else
			{	
				rs=statement.executeQuery("select token_no from TokenNumbers where token_no="+tokenno);
				if(!rs.next())
					return false;
			}
		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return true;
	}
	
	public boolean checkPayOrderChequeNo(int chqno) 
	{
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			ResultSet rs=statement.executeQuery("select po_chq_no from PayOrder where po_chq_no="+chqno);
			if(rs.next())
				return true;
		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return false;
	}


	public AccountObject[] getChequeDet(int chqno) 
	{
		AccountObject acc[]=null;
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

		ResultSet rs=statement.executeQuery("select ChequeBook.ac_type,ChequeBook.ac_no,ChequeBook.book_no,concat(IFNULL(fname,''),' ',IFNULL(mname,''),' ',IFNULL(lname,'')) as name from ChequeBook,AccountMaster,CustomerMaster  where "+chqno+" between fst_chq_no and lst_chq_no and AccountMaster.ac_type=ChequeBook.ac_type and AccountMaster.ac_no=ChequeBook.ac_no and CustomerMaster.cid=AccountMaster.cid and ChequeBook.ve_tml is not null ");
		if(rs.next())
		{	
		  rs.last();	
		  acc=new AccountObject[rs.getRow()];
		  rs.beforeFirst();
		}
		else
		{
			 rs=statement.executeQuery("select ChequeBook.ac_type,ChequeBook.ac_no,ChequeBook.book_no,concat(IFNULL(fname,''),' ',IFNULL(mname,''),' ',IFNULL(lname,'')) as name from ChequeBook,ODCCMaster,CustomerMaster  where "+chqno+" between fst_chq_no and lst_chq_no and CustomerMaster.cid=ODCCMaster.cid and ODCCMaster.ac_type=ChequeBook.ac_type and ODCCMaster.ac_no=ChequeBook.ac_no  and ChequeBook.ve_tml is not null ");
				if(rs.next())
				{	
				  rs.last();	
				  acc=new AccountObject[rs.getRow()];
				  rs.beforeFirst();
				}

		}

		int i=0;
		while(rs.next())
		{
			acc[i]=new AccountObject();
			acc[i].setAcctype(rs.getString(1));
			acc[i].setAccno	(rs.getInt(2));
			acc[i].setAccname(rs.getString(4));
			acc[i].setCid(rs.getInt(3));
			i++;
		}

		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}


		return acc;

	}
	
	/**Created on 08/02/06 for displaying Chequedetails for particular ac_type and ac_no**/
	
	public Vector getChequeNoDet(String acctype,int accno) 
	{
		ChequeObject ch=null;
		Vector vector_ch=null;
		Connection connection=null;
		Statement statement=null;
		ResultSet rs=null;
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

/*			if(acctype.length()>0 && accno>0 && ac_close==null)
				rs=statement.executeQuery("select cn.*,Modules.moduleabbr from ChequeNo cn,Modules where ac_type='"+acctype+"' and ac_no="+accno+" and Modules.modulecode='"+acctype+"' and cn.ve_tml is not null ");*/
			if(acctype.length()>0 && accno>0)
				rs=statement.executeQuery("select cn.*,Modules.moduleabbr from ChequeNo cn,Modules where ac_type='"+acctype+"' and ac_no="+accno+" and Modules.modulecode='"+acctype+"'  and cn.ve_tml is not null  ");

			/*else
			 * 
				rs=statement.executeQuery("select cn.*,Modules.moduleabbr from ChequeNo cn,Modules where chq_no="+chqno+" and Modules.modulecode=cn.ac_type");
*/
			if(rs.last())
			{
				vector_ch=new Vector();
				rs.beforeFirst();
			}
			while(rs.next())
			{
				ch=new ChequeObject();
				ch.setAccType(rs.getString("ac_type"));
				ch.setAccNo(rs.getInt("ac_no"));
				ch.setChqNo(rs.getInt("chq_no"));
				ch.setBookNo(rs.getInt("book_no"));

				ch.setPost_Dated(rs.getString("post_dated"));
				ch.setExpDate(rs.getString("exp_date"));
				ch.setStop_payment(rs.getString("stop_pymnt"));
				ch.setCheque_IssueDate(rs.getString("chq_iss_dt"));
				ch.setCheque_Payee(rs.getString("chq_payee"));
				ch.setCheque_Del(rs.getString("chq_del"));
				ch.setCheque_Cancelled(rs.getString("chq_cancel"));
				ch.setNext_ChequeDate(rs.getString("next_chqdt"));
				ch.setChequeAmount(rs.getDouble("chq_amt"));
				ch.setModuleType(rs.getString("moduleabbr"));
				ch.setVe_tml(rs.getString("ve_tml"));
				ch.setVe_user(rs.getString("ve_user"));
				ch.setVe_date(rs.getString("ve_date"));
				vector_ch.add(ch);

			}

		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return vector_ch;
	}
/**
 * To get the details of the Cheque No from ChequeNo table
 */
	public Vector getChequeNoDet(String acctype,int accno,int chqno) 
	{
		ChequeObject ch=null;
		Vector vector_ch=null;
		Connection connection=null;
		Statement statement=null;
		ResultSet rs=null;
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			if(acctype.length()>0 && accno>0)
			{
			    System.out.println("11 Inside FCgetchqnodet*********");
				rs=statement.executeQuery("select cn.*,Modules.moduleabbr from ChequeNo cn,Modules where chq_no="+chqno+" and ac_type='"+acctype+"' and ac_no="+accno+" and Modules.modulecode='"+acctype+"' ");
			}
			else
			{
			    System.out.println("22 Inside FCgetchqnodet*********");
				rs=statement.executeQuery("select cn.*,Modules.moduleabbr from ChequeNo cn,Modules where chq_no="+chqno+" and Modules.modulecode=cn.ac_type ");
			}

			if(rs.last())
			{
				vector_ch=new Vector();
				rs.beforeFirst();
			}
			while(rs.next())
			{
				ch=new ChequeObject();
				ch.setAccType(rs.getString("ac_type"));
				ch.setAccNo(rs.getInt("ac_no"));
				ch.setChqNo(rs.getInt("chq_no"));
				ch.setBookNo(rs.getInt("book_no"));

				ch.setPost_Dated(rs.getString("post_dated"));
				ch.setExpDate(rs.getString("exp_date"));
				ch.setStop_payment(rs.getString("stop_pymnt"));
				ch.setCheque_IssueDate(rs.getString("chq_iss_dt"));
				ch.setCheque_Payee(rs.getString("chq_payee"));
				ch.setCheque_Del(rs.getString("chq_del"));
				ch.setCheque_Cancelled(rs.getString("chq_cancel"));
				ch.setNext_ChequeDate(rs.getString("next_chqdt"));
				ch.setChequeAmount(rs.getDouble("chq_amt"));
				ch.setModuleType(rs.getString("moduleabbr"));
				vector_ch.add(ch);

			}

		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return vector_ch;
	}
	
	public Vector CheckChqNo(int chq_no,int ac_no,String ac_type,String date) 
	{
		Vector str=null;
		Connection connection=null;
		Statement statement=null;
		ResultSet rs=null;
		System.out.println("Inside  CheckchqNo== ");
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if(chq_no!=0)
			    rs=statement.executeQuery("select book_no,chq_no,de_date from ChequeNo where ac_no='"+ac_no+"' and ac_type='"+ac_type+"' and chq_no='"+chq_no+"' and stop_pymnt='F' and chq_del='F' and chq_cancel='F' and (post_dated='F' or (post_dated='T' and concat(right(exp_date,4),'-',mid(exp_date,locate('/',exp_date)+1, (locate('/',exp_date,4)-locate('/',exp_date)-1)),'-',left(exp_date,locate('/',exp_date)-1))<= '"+Validations.convertYMD(date)+"' )) and ve_tml is not null ");
			else
			   rs=statement.executeQuery("select book_no,fst_chq_no,lst_chq_no,de_date from ChequeBook where ac_no='"+ac_no+"' and ac_type='"+ac_type+"'  and ChequeBook.ve_tml is not null ");

	
			if(rs.last())
			{	
				str=new Vector();
				rs.beforeFirst();
			}

			while(rs.next())
			{
			    if(chq_no!=0)
			        str.add(rs.getInt("book_no")+" "+rs.getInt("chq_no")+" "+rs.getString("de_date").substring(0,10));
			    else
			        str.add(rs.getInt("book_no")+" "+rs.getInt("fst_chq_no")+"-"+rs.getInt("lst_chq_no")+" "+rs.getString("de_date").substring(0,10));			    
			}

		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		System.out.println("str========"+str);
	return str;
	}

/**
 * Function to set stop payment,cheque cancel,post dated instructions in the table ChequeNo
 * Once the details are updated it is copied into ChequeNoLog table 
 */
	public int updateChequeDet(ChequeObject ch) 
	{
		Connection connection=null;
		Statement statement=null;
		int result=0;
		try
		{ if(ch!=null){
			
			System.out.println("ch.getPost_Dated()====="+ch.getPost_Dated());
			System.out.println("ps.setInt(12,ch.getChqNo());====>"+ch.getChqNo());
			System.out.println("ch.getAccNo()=====>"+ch.getAccNo());
			
		}
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			PreparedStatement ps=connection.prepareStatement("update ChequeNo set post_dated=?,exp_date=?,stop_pymnt=?,next_chqdt=?,chq_iss_dt=?,chq_payee=?,chq_amt=?,stop_user=?,chq_cancel=?,alt_de_user=?,alt_de_date=? where chq_no=? and ac_type=? and ac_no=? and book_no=?");

			ps.setString(1,ch.getPost_Dated());
			ps.setString(2,ch.getExpDate());
			ps.setString(3,ch.getStop_payment());
//			ps.setString(4,ch.getNext_ChequeDate());
			ps.setString(4,null);
			ps.setString(5,ch.getCheque_IssueDate());
			ps.setString(6,ch.getCheque_Payee());
			ps.setDouble(7,ch.getChequeAmount());
			ps.setString(8,ch.getStop_User());
			ps.setString(9,ch.getCheque_Cancelled());
			ps.setString(10,ch.getAlt_De_User());
			ps.setString(11,ch.getAlt_De_Date());
			
			ps.setInt(12,ch.getChqNo());
			ps.setString(13,ch.getAccType());
			ps.setInt(14,ch.getAccNo());
			ps.setInt(15,ch.getBookNo());

			if((result=ps.executeUpdate())==1)
			{
				if((result=statement.executeUpdate("insert into ChequeNoLog  select * from ChequeNo where chq_no="+ch.getChqNo()+" and ac_type='"+ch.getAccType()+"' and ac_no="+ch.getAccNo()+" and book_no="+ch.getBookNo()+""))!=1)
					throw new SQLException("ChequeNoLog not inserted");
			}
			else
				throw new SQLException("Cheque No not updated");

			return(result);

		}catch(Exception sqlexception)
		{
			ctx.setRollbackOnly();
			sqlexception.printStackTrace();
			try
			{
				ctx.setRollbackOnly();
				result=0;
			}catch(Exception exception)
			{
				exception.printStackTrace();}
		}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}


	return(result);
	}

	/**
	 * Function to create Temporary PayOrder before it is sent for Consolidation.
	 * It writes to PayOrderMake table and generated serial number is returned.
	 * If the PayOrder Serial no is already existing it deletes that PayOrder
	 * and it is inserted again (updation Purpose) and returns the same payorder serial no.    
	 */
	public int storePayOrder(PayOrderObject po,int type) 
	{
		int pno=0;
		Connection connection=null;
		Statement statement=null;
		AccountTransObject am=new AccountTransObject();
		int a=0;
		String trn_date=null;
		
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			 String acctype=po.getPOAccType();

			if(po.getPOSerialNo()==0)
			{	
				/*ResultSet rs=statement.executeQuery("select lst_paysr_no from GenParam");
				if(rs.next())
					pno=rs.getInt(1)+1;
				else
					throw new SQLException("lst_paysr_no in GenParam not found");
				rs.close();*/
			    ResultSet rs=statement.executeQuery("select last_trf_scroll_no from Modules where modulecode='1016001'");

				if(rs.next())
					pno=rs.getInt(1)+1;
				else
					throw new SQLException("last_trf_scroll_no in Modules not found");
				rs.close();
			}
			else
			{
				pno=po.getPOSerialNo();
				System.out.println("Before Deletion........"+pno);
				System.out.println("Before Deletion........."+po.getPOAccNo());
				System.out.println("Before Deletion........."+po.getPOAccType());
				
				if(statement.executeUpdate("delete from PayOrderMake where po_sno="+po.getPOSerialNo()+" and po_type='"+po.getPOType()+"' and ve_user is null")==0)
					throw new SQLException("PayOrderMake not deleted");
				
				/*if(acctype.startsWith("1002") ||acctype.startsWith("1007") ||acctype.startsWith("1017") ||acctype.startsWith("1018"))
				{				    
				    if(statement.executeUpdate("delete from AccountTransaction where ac_type='"+po.getPOAccType()+"' and ac_no="+po.getPOAccNo()+" and ref_no="+pno+" and trn_date='"+getSysDate()+"'and ve_user is null order by trn_seq")==0)
				        throw new SQLException("AccountTransaction not deleted");
				}
				else if(acctype.startsWith("1014") ||acctype.startsWith("1015"))
				{				    	
				    if(statement.executeUpdate("delete from ODCCTransaction where ac_type='"+po.getPOAccType()+"' and ac_no="+po.getPOAccNo()+" and ref_no="+pno+" and trn_date='"+getSysDate()+"'and ve_user is null order by trn_seq desc limit 1")==0)
				        throw new SQLException("ODCCTransaction not deleted");
				}*/
			}
			
			if(type!=1)//1--delete
			{	
				System.out.println("Inside insertttttttttttttttttttttttttttt PayorderMake--"+pno);
				trn_date=po.getPOMakeDate();
				System.out.println("DATE="+trn_date);
			    PreparedStatement ps=connection.prepareStatement("insert into PayOrderMake values(?,?,?,'"+trn_date+"',?,?,?,?,?,?,?,?,'F',?,?,?,?,null,null,null)");
			    
				ps.setString(1,po.getPOType());
				ps.setInt(2,po.getCustType());
				ps.setInt(3,pno);
				ps.setString(4,po.getPOPayee());
				ps.setString(5,po.getPOAccType());
				ps.setInt(6,po.getPOAccNo());
				ps.setString(7,po.getPOFavour());
				ps.setString(8,po.getPOGlType());
				ps.setInt(9,po.getPOGlCode());
				ps.setString(10,po.getPOGlName());
				ps.setDouble(11,po.getPOAmount());
				ps.setDouble(12,po.getCommissionAmount());
				ps.setString(13,po.uv.getUserTml());
				ps.setString(14,po.uv.getUserId());
				ps.setString(15,po.uv.getUserDate());
				if(ps.executeUpdate()==1)
				{	
					 /*if(po.getPOSerialNo()==0)
					if(statement.executeUpdate("update GenParam set lst_paysr_no=lst_paysr_no+1")!=1)
						throw new SQLException("lst_paysr_no in GenParam not updated");*/
				    //if(po.getPOSerialNo()==0)
						if(statement.executeUpdate("update Modules set last_trf_scroll_no=last_trf_scroll_no+1 where modulecode='1016001'")!=1)
							throw new SQLException("last_trf_scroll_no in Modules not updated");
				}
				
			    
								
			    /*if(!acctype.startsWith("1016001"))
			    {*/
			    /*if(acctype!=null)
			    {
			        ResultSet rs=null;
			        
			        if(acctype.startsWith("1002") ||acctype.startsWith("1007") ||acctype.startsWith("1017") ||acctype.startsWith("1018"))
			            rs=statement.executeQuery("select trn_seq,cl_bal from AccountTransaction where ac_no="+po.getPOAccNo()+" and ac_type='"+po.getPOAccType()+"' order by trn_seq desc limit 1");
				    else
				        rs=statement.executeQuery("select trn_seq,cl_bal from ODCCTransaction where ac_no="+po.getPOAccNo()+" and ac_type='"+po.getPOAccType()+"' order by trn_seq desc limit 1");
						
				        int trnseq=0;
				        double cl_bal=0;
				        if(rs.next())
				        {
				            trnseq=rs.getInt(1);
				            cl_bal=rs.getDouble(2);
				        }
				        else
				            throw new SQLException("Account not found");

						po.setTransSeq(trnseq+1);

						System.out.println("AccType:"+po.getPOAccType());
						System.out.println("AccNo: "+po.getPOAccNo());					
						
						PreparedStatement ps1;
						if(acctype.startsWith("1002") ||acctype.startsWith("1007") ||acctype.startsWith("1017") ||acctype.startsWith("1018"))
							ps1=connection.prepareStatement("insert into AccountTransaction values(?,?,'"+getSysDate()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+getSysDate()+" "+getSysTime()+"',null,null,null)");
						else
						{
							ResultSet rs1=null;							
						    rs1=statement.executeQuery("select int_uptodt from ODCCMaster where ac_type='"+po.getPOAccType()+"' and ac_no="+po.getPOAccNo());
							rs1.next();
								String lst_int_caldt=rs1.getString("int_uptodt");
						    ps1=connection.prepareStatement("insert into ODCCTransaction values(?,?,'"+getSysDate()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+getSysDate()+" "+getSysTime()+"',null,null,null,'"+lst_int_caldt+"')");
						}
						
						System.out.println("##############");
						ps1.setString(1,acctype);
						ps1.setInt(2,po.getPOAccNo());
						
						ps1.setString(3,"P");
						ps1.setInt(4,po.getTransSeq());
						ps1.setDouble(5,(po.getPOAmount()+po.getCommissionAmount()));
						ps1.setString(6,"T");
						ps1.setString(7,po.uv.getUserTml());
						ps1.setString(8,"D");
						ps1.setDouble(9,0);	
						ps1.setString(10,null);
						ps1.setString(11,"PayOrder "+pno);						
						ps1.setInt(12,pno);
						ps1.setString(13,po.getPOPayee());
						ps1.setDouble(14,(cl_bal-(po.getPOAmount()+po.getCommissionAmount())) );
						ps1.setInt(15,0);						
						ps1.setString(16,po.uv.getUserTml());
						ps1.setString(17,po.uv.getUserId());
						System.out.println("55555555555555555"+ps1);
						if(ps1.executeUpdate()!=1)
							throw new SQLException("ODCC/AccountTransaction not stored");  
						
						if(commonlocal.StoreNo0fAccountTransactions(acctype,po.getPOAccNo())!=1)
						if(commonlocal==null)
						    commonlocal=commonlocalhome.create();
						
						if(acctype.startsWith("1002"))// || acctype.startsWith("1007") || acctype.startsWith("1018")|| acctype.startsWith("1017"))
						{
						    if((a=(commonlocal.storeNo0fAccountTransactions(acctype,po.getPOAccNo(),0)==1)?1:0)!=1)
						        throw new SQLException("Could not store Penalty for NoofTransaction >Trns_per_mnth");
						    else
						        System.out.println("Successfully Stored into AccountTransaction if NoofTransaction >Trns_per_mnth");
						}
						if(acctype.startsWith("1002") || acctype.startsWith("1007") ||acctype.startsWith("1018")||acctype.startsWith("1017"))
						{
							rs=statement.executeQuery("select trn_seq from AccountTransaction where ac_no="+po.getPOAccNo()+" and ac_type='"+acctype+"' order by trn_seq desc limit 1");
							if(rs.next())
								statement.executeUpdate("update AccountMaster set last_tr_seq="+rs.getString("trn_seq")+",last_tr_date='"+getSysDate()+"' where ac_type='"+acctype+"' and ac_no="+po.getPOAccNo()+" ");
						}
						else if(acctype.startsWith("1014") || acctype.startsWith("1015"))
						{	
							rs=statement.executeQuery("select trn_seq from ODCCTransaction ac_no="+po.getPOAccNo()+" and ac_type='"+acctype+"' order by trn_seq desc limit 1");
							if(rs.next())
								statement.executeUpdate("update ODCCMaster set last_tr_seq="+rs.getString("trn_seq")+",last_tr_date='"+getSysDate()+"' where ac_type='"+acctype+"' and ac_no="+po.getPOAccNo()+" ");
						}
						
			        }*/
				}		
			}catch(Exception sqlexception)
			{
				ctx.setRollbackOnly();
			    sqlexception.printStackTrace();
			    try
			    {
			        	ctx.setRollbackOnly();
			        	pno=0;
			    }catch(Exception exception){exception.printStackTrace();}
			}
			finally
			{
			    try
			    {
			        connection.close();
			    }catch(Exception ex){ex.printStackTrace();}
			}

		return pno;
	}
	
	 
/**
 * Function to read the details of the Temporary PayOrder(fro given po serial no)
 * from PayOrderMake table
 * sets the po serial no to -1 if payorder serial no not found 
 */
	public PayOrderObject getPayOrder(int srno) 
	{
		PayOrderObject po=new PayOrderObject();
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs=statement.executeQuery("select * from PayOrderMake where po_sno="+srno+" and ve_user is null");
		if(rs.next())
		{
		    po.setPOSerialNo(rs.getInt("po_sno"));
		    po.setPOType(rs.getString("po_type"));
			po.setCustType(rs.getInt("cust_type"));
			po.setPOAccType(rs.getString("po_acty"));
			po.setPOAccNo(rs.getInt("po_acno"));
			po.setPOPayee(rs.getString("po_purchaser_name"));
			po.setPOGlCode(rs.getInt("po_glcd"));			
			po.setPOGlType(rs.getString("po_gltype"));
			po.setPOGlName(rs.getString("po_glname"));			
			po.setPOFavour(rs.getString("po_favour_name"));			
			po.setPOAmount(rs.getDouble("po_amt"));
			System.out.println("getPO Date="+rs.getString("po_date"));
			po.setPOMakeDate(rs.getString("po_date"));
			System.out.println("Afetr Setting date="+po.getPOMakeDate());
			po.setCommissionAmount(rs.getDouble("comm_amt"));
			po.setPOMade(rs.getString("po_made"));			
			po.uv.setUserTml(rs.getString("de_tml"));
			po.uv.setUserId(rs.getString("de_user"));
			po.uv.setVerTml(rs.getString("ve_tml"));
		}
		else
			po.setPOSerialNo(-1);

		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}
		return (po);
	}

	public PayOrderObject getPayOrderInstrn(int chqno) 
	{
		PayOrderObject po=null;
		Connection connection=null;
		Statement statement=null, stmt1=null;
        ResultSet rs1=null;
		try
		{
		    
		    System.out.println("INSIDE bEAN");
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		//ResultSet rs=statement.executeQuery("select * from PayOrder where po_chq_no="+chqno+" and ve_user is not null");
		//ResultSet rs=statement.executeQuery("select po.*, pm.po_acty,pm.po_acno from PayOrder po, PayOrderMake pm where po.payord_no=pm.po_sno and po.po_chq_no="+chqno+" and po.ve_user is not null;");// dee
		     
            /*int pay_ord_no=0;
            int ref_sno=0;
            rs1=stmt1.executeQuery("select pol.* from POLink pol,PayOrder po where pol.payord_no=po.payord_no and  po.po_chq_no="+chqno);
            if(rs1.next())
               pay_ord_no=rs1.getInt("payord_no");  
               ref_sno=rs1.getInt("ref_sno");
            
            ResultSet rs=statement.executeQuery("select po.*, pm.po_acty,pm.po_acno from PayOrder po, PayOrderMake pm where pm.po_sno="+ref_sno+"  and  po.payord_no="+pay_ord_no+" and po.ve_user is not null");*/
            ResultSet rs=statement.executeQuery("select distinct po.*, pm.po_acty,pm.po_acno from PayOrder po, PayOrderMake pm where pm.po_sno in (select pol.ref_sno from POLink pol,PayOrder po where pol.payord_no=po.payord_no and  po.po_chq_no="+chqno+" and pm.po_type=pol.ref_type)  and  po.payord_no in (select pol.payord_no from POLink pol,PayOrder po where pol.payord_no=po.payord_no and  po.po_chq_no="+chqno+" ) and po.ve_user is not null");

		if(rs.next())
		{
		    
		    System.out.println("inside");
			po=new PayOrderObject();
			po.setPOSerialNo(rs.getInt(1));
			po.setPODate(rs.getString(2));
			po.setPOPayee(rs.getString(4));
			po.setPOAmount(rs.getDouble (5));			
			po.setPOCancel(rs.getString(6));
			po.setPOStop(rs.getString(7));
			po.setPOCshIndicator(rs.getInt(9));
			po.setPOChqNo(rs.getInt(10));
			po.setPOValidUpTo(rs.getString(11));
			po.setPOAccType(rs.getString("pm.po_acty"));//dee
			po.setPOAccNo(rs.getInt("pm.po_acno"));//dee
			System.out.println("ac_type"+rs.getString("pm.po_acty"));
			System.out.println("ac_no"+rs.getString("pm.po_acno"));
		}

		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return (po);
	}

/**
 * Verification of PayOrderMake table
 * GL posting --
 * 1.writes debit entry to the GL from which payorder is created, for Accounts(StoreAccountTransaction(CommonImpl.java) fn is called )
 *    or storeGLTransaction is called
 * 2.writes credit entry to PO Suspense GL(for payorder amount) here GLRefCode is 1999003
 *    and one more credit entry to profit GL(for commission amt) here GLRefCode is 1999004
 *   using storePOGL fn 
 * 
 *  
 */	
	
	public int verifyPOEntry(PayOrderObject po) 
	{
		AccountTransObject am=new AccountTransObject();
		GLTransObject trn_obj=new GLTransObject();
		int a=0;
		Connection connection=null;
		Statement statement=null,stmt_gl=null,stmt_trn=null;
		
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_gl=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_trn=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			commonlocal=commonlocalhome.create();
			System.out.println("Condition="+po.getPOType()+" "+po.getPOSerialNo()+" "+po.getPOMakeDate());

			//if((a=statement.executeUpdate("update PayOrderMake set ve_user='"+po.uv.getVerId()+"',ve_tml='"+po.uv.getVerTml()+"',ve_date='"+getSysDate()+" "+getSysTime()+"' where po_sno="+po.getPOSerialNo()+" and po_type='"+po.getPOType()+"'"))!=1)
			if((a=statement.executeUpdate("update PayOrderMake set ve_user='"+po.uv.getVerId()+"',ve_tml='"+po.uv.getVerTml()+"',ve_date='"+po.uv.getVerDate()+"' where po_sno="+po.getPOSerialNo()+" and po_type='"+po.getPOType()+"' and po_date='"+po.getPOMakeDate()+"'"))==0)
				throw new SQLException("PayOrderMake not updated");			
			
			ResultSet rs1=statement.executeQuery("select po_acno,po_acty,po_amt,comm_amt,po_purchaser_name,po_glcd from PayOrderMake where po_sno="+po.getPOSerialNo()+" and po_type='"+po.getPOType()+"' and po_date='"+po.getPOMakeDate()+"'");
			if(rs1.next())
			{
				int ac_no=0,glcd=0;
				String ac_ty="";
				double amt=0;

				ac_no=rs1.getInt("po_acno");
				ac_ty=rs1.getString("po_acty");
				amt=rs1.getDouble("po_amt")+rs1.getDouble("comm_amt");
				glcd=rs1.getInt("po_glcd");
				
				System.out.println("PoAcType====="+ac_ty);				
				System.out.println("Amt="+amt);
				System.out.println("POAmt="+rs1.getDouble ("po_amt"));
				System.out.println("Commamt="+rs1.getDouble ("comm_amt"));

				// if it is from Account
				if(ac_ty!=null)
				{
				    System.out.println("Inside Actrn !=null");
				    /*ResultSet rs4=null;
					if(ac_ty.startsWith("1002") || ac_ty.startsWith("1007") || ac_ty.startsWith("1018"))
					    rs4=stmt_trn.executeQuery("select trn_type,cd_ind,trn_seq from AccountTransaction where ac_no="+ac_no+" and ac_type='"+ac_ty+"' and ref_no="+po.getPOSerialNo()+" ");
					else
					    rs4=stmt_trn.executeQuery("select trn_type,cd_ind,trn_seq from ODCCTransaction where ac_no="+ac_no+" and ac_type='"+ac_ty+"' and ref_no="+po.getPOSerialNo()+" ");
					if(rs4.next())
					{
					    String trn_type=rs4.getString("trn_type");
					    String cd_ind=rs4.getString("cd_ind");
					 	int trn_seq=rs4.getInt("trn_seq");	
					 	System.out.println("After setting values trn_type,cd_ind,trn_seq");
									    
				    if(ac_ty.startsWith("1002")||ac_ty.startsWith("1007")||ac_ty.startsWith("1017")||ac_ty.startsWith("1018"))
				    {
				        statement.executeUpdate("update AccountTransaction set ve_user='"+po.uv.getVerId()+"',ve_tml='"+po.uv.getVerTml()+"',ve_date='"+getSysDate()+" "+getSysTime()+"' where ref_no="+po.getPOSerialNo()+" and ac_type='"+po.getPOAccType()+"' and ac_no="+po.getPOAccNo()+" ");
				        statement.executeUpdate("update AccountMaster set last_tr_seq="+trn_seq+",last_tr_date='"+getSysDate()+" "+getSysTime()+"' where ac_type='"+po.getPOAccType()+"' and ac_no="+po.getPOAccNo()+" ");
				    }
					else
					{
					    statement.executeUpdate("update ODCCTransaction set ve_user='"+po.uv.getVerId()+"',ve_tml='"+po.uv.getVerTml()+"',ve_date='"+getSysDate()+" "+getSysTime()+"' where ref_no="+po.getPOSerialNo()+" and ac_type='"+po.getPOAccType()+"' and ac_no="+po.getPOAccNo()+" ");
					    statement.executeUpdate("update ODCCMaster set last_tr_seq="+trn_seq+",last_tr_date='"+getSysDate()+" "+getSysTime()+"' where ac_type='"+po.getPOAccType()+"' and ac_no="+po.getPOAccNo()+" ");
					}
				    
				    try
				    {   
				        System.out.println("Inside select custyyyyyyyyyyyyyyyyy");
				        int type1=0;
				        ResultSet rs2=null;
				        int scode=0;
						if(ac_ty.startsWith("1002") || ac_ty.startsWith("1007") || ac_ty.startsWith("1018"))
							rs2=statement.executeQuery("select custtype from AccountMaster am,CustomerMaster cm where am.ac_no="+po.getPOAccNo()+" and am.ac_type='"+ac_ty+"' and am.cid=cm.cid");
						else  if(ac_ty.startsWith("1014") |ac_ty.startsWith("1015"))
							rs2=statement.executeQuery("select custtype from ODCCMaster am,CustomerMaster cm where am.ac_no="+po.getPOAccNo()+" and am.ac_type='"+ac_ty+"' and am.cid=cm.cid");
						
						if(rs2.next())
							type1=rs2.getInt(1);
						if(type1==0)
							scode=1;
						else if(type1==1)
							scode=2;
						rs2.close();
						if(trn_type!=null)
					 	{
						    System.out.println("Inside getTranstype"+trn_type);
						    if(trn_type.equals("I"))
						        scode=3;
					 	}					
						
						System.out.println("vale of SSSSSSSSSSSSSSSSSSSSSSScode="+scode);
						System.out.println("Value of tttttttttttttttype="+type1);
						System.out.println("Valuuuuuuuuuuuuuuuu="+po.getTransType());
						System.out.println("CDIndicator="+po.getCDindicator());
						System.out.println("AccountType=="+ac_ty);
												    
						ResultSet rs3=null;						
						rs3=stmt_gl.executeQuery("select gk.gl_code,gk.gl_type,gp.mult_by from GLKeyParam gk,GLPost gp where gk.ac_type='"+ac_ty+"'and gk.code="+scode+" and gp.trn_type='"+trn_type+"' and gp.cr_dr='"+cd_ind+"' and gk.ac_type=gp.ac_type and gp.gl_code=gk.gl_code");
						rs3=stmt_gl.executeQuery("select gk.gl_code,gk.gl_type,gp.mult_by,at.trn_seq,at.cd_ind,at.trn_type  from GLKeyParam gk,GLPost gp,AccountTransaction at where gk.ac_type=at.ac_type and gk.code="+scode+" and gp.trn_type=at.trn_type and gp.cr_dr='C' and gk.ac_type=gp.ac_type and gp.gl_code=gk.gl_code and at.ref_no="+po.getPOSerialNo()+" and at.ac_no="+ac_no+" ");Dee
						rs3=stmt_gl.executeQuery("select gk.gl_code,gk.gl_type,gp.mult_by from GLKeyParam gk,GLPost gp where gk.ac_type='"+ac_ty+"' and gk.code="+scode+" and gp.trn_type='"+trn_type+"' and gp.cr_dr='"+cd_ind+"' and gk.ac_type=gp.ac_type and gp.gl_code=gk.gl_code and at.ref_no="+po.getPOSerialNo()+" and at.ac_no="+ac_no+" ");
						if(rs3.next())
						{   
						    System.out.println("Inside Modified RISHI="+po.getTransType());
							trn_obj.setTrnDate(getSysDate());
							trn_obj.setGLType(rs3.getString(2));
							trn_obj.setTrnDate(getSysDate());
							trn_obj.setGLCode(rs3.getString(1));
							trn_obj.setTrnMode("T");
							trn_obj.setAmount(amt*rs3.getInt(3));
							trn_obj.setCdind(cd_ind);
							trn_obj.setAccType(ac_ty);
							trn_obj.setAccNo(String.valueOf(po.getPOAccNo()));
							trn_obj.setTrnSeq(trn_seq);
							trn_obj.setTrnType(po.getTransType());
							trn_obj.setTrnType(trn_type);
							trn_obj.setRefNo(po.getPOSerialNo());							
							trn_obj.setVtml(po.uv.getVerTml());
							trn_obj.setVid(po.uv.getVerId());
							
							commonlocal.storeGLTransaction(trn_obj);
						}						
				    }catch(SQLException sql){sql.printStackTrace();}
					}
					if(ac_ty.startsWith("1002"))//	 || ac_ty.startsWith("1007") || ac_ty.startsWith("1018")|| ac_ty.startsWith("1017"))
					{
					    if((a=(commonlocal.storeNo0fAccountTransactions(ac_ty,ac_no,1)==1)?1:0)!=1)
					        throw new SQLException("Could not store Penalty for NoofTransaction >Trns_per_mnth");
					    else
					        System.out.println("Successfully Stored into AccountTransaction if NoofTransaction >Trns_per_mnth");
					}*/
				    
				    //Not set chddno,chqdddate,psbkseq,clbal
				    am.setAccType(ac_ty);
					am.setAccNo(po.getPOAccNo());
					am.setTransDate(po.getPOMakeDate());
					am.setTransType("P");	
					am.setTransAmount(amt);
					am.setTransMode("T");
					am.setTransSource(po.uv.getUserTml());
					am.setCdInd("D");					
					am.uv.setUserTml(po.uv.getUserTml());
					am.uv.setUserId(po.uv.getUserId());	
					am.uv.setUserDate(po.uv.getUserDate());
					am.uv.setVerTml(po.uv.getVerTml());
					am.uv.setVerId(po.uv.getVerId());
					am.uv.setVerDate(po.uv.getVerDate());
					am.setTransNarr("1016001 "+po.getPOSerialNo());
					am.setRef_No(po.getPOSerialNo());					
					am.setPayeeName(po.getPOPayee());					
					am.setLedgerPage(0);	
					
				    if(commonlocal==null)
					    commonlocal=commonlocalhome.create();
					commonlocal.storeAccountTransaction(am);
				    
				}
				else
				{
					/*GLTransObject trn_obj=new GLTransObject();*/
					trn_obj.setTrnDate(po.getPOMakeDate());
					trn_obj.setGLType(po.getPOGlType());
					trn_obj.setGLCode(String.valueOf(glcd));
					trn_obj.setTrnMode("T");						
					trn_obj.setAmount(amt);
					trn_obj.setCdind("D");
					System.out.println("Problem of REF ACTy ACNO***********");
					trn_obj.setAccType(po.getPOType());
					trn_obj.setAccNo(String.valueOf(po.getPOSerialNo()));
					trn_obj.setTrnSeq(po.getTransSeq());
					trn_obj.setTrnType(po.getTransType());
					trn_obj.setRefNo(po.getPOChqNo());
					trn_obj.setVtml(po.uv.getVerTml());
					trn_obj.setVid(po.uv.getVerId());
					trn_obj.setVDate(po.uv.getVerDate());
					if((a=(commonlocal.storeGLTransaction(trn_obj)==1)?1:0)!=1)
						throw new SQLException("GLTransaction not inserted");
				}
			}
						
			po.setGLRefCode(1016001);
			po.setGLSubCode(1);
			po.setTransMode("T");
			po.setTransType("");
			po.setCDindicator("C");
			po.setTrnSource("T");
			po.setPOAmount(po.getPOAmount());
			po.setPayOrderNo(po.getPOSerialNo());
			po.setPOChqNo(po.getPOSerialNo());
			
			if((a=storePOGL(po))==1)
			{
				//Posting to Profit
				po.setGLRefCode(1016001);
				po.setGLSubCode(2);
				po.setTransMode("T");
				po.setCDindicator("C");
				po.setTrnSource("T");
				po.setPOAmount(po.getCommissionAmount());
				po.setPayOrderNo(po.getPOSerialNo());
				po.setPOChqNo(po.getPOSerialNo());
				if((a=storePOGL(po))!=1)
					throw new SQLException("GLPost not done");
			}
			else
				throw new SQLException("GLPost not done");
			
		return a;
		}catch(Exception sqlexception)
		{
			sqlexception.printStackTrace();
			try
			{
				ctx.setRollbackOnly();
				a=-1;
			}catch(Exception exception){exception.printStackTrace();}
		}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
			commonlocal = null;
		}

		return(a);
	}
	
	public boolean checkChequeWithdrawal(String actype,int acno,int token_no) 
	{
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			ResultSet rs=statement.executeQuery("select * from ChequeWithdrawal where ac_type='"+actype+"' and ac_no="+acno+" and ve_user is null and token_no!="+token_no+"");
			if(rs.next())
				return true;
			
		}catch(Exception ex){ex.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return false;
	}
	
	public int storePOGL(PayOrderObject po) 
	{
		ResultSet rs=null;
		int a=0;

		Connection connection=null;
		Statement statement=null;
		commonlocal=null;
		try
		{
			commonlocal=commonlocalhome.create();
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=statement.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type='"+po.getGLRefCode()+"' and code="+po.getGLSubCode()+"");
			
			GLTransObject trn_obj[]=null;
			rs.last();
			trn_obj=new GLTransObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			
			while(rs.next())
			{	
				trn_obj[i]=new GLTransObject();
				System.out.println("PayOrder RefNo"+po.getRefSerialNo());
				System.out.println("PayOrder RefNo"+po.getPOChqNo());
				trn_obj[i].setTrnDate(po.getPOMakeDate());
				trn_obj[i].setGLType(rs.getString(2));
				trn_obj[i].setGLCode(String.valueOf(rs.getInt(1)));
				trn_obj[i].setTrnMode(po.getTransMode());				
				trn_obj[i].setAmount((po.getPOAmount()));
				trn_obj[i].setCdind(po.getCDindicator());
				trn_obj[i].setAccType(po.getPOType());
				trn_obj[i].setAccType(String.valueOf(po.getGLRefCode()));
				trn_obj[i].setAccNo(String.valueOf(po.getPayOrderNo()));
				trn_obj[i].setTrnSeq(po.getTransSeq());
				trn_obj[i].setTrnType(po.getTransType());
				trn_obj[i].setRefNo(po.getPOChqNo());
				trn_obj[i].setVtml(po.uv.getVerTml());
				trn_obj[i].setVid(po.uv.getVerId());
				trn_obj[i].setVDate(po.uv.getVerDate());
				i++;
				System.out.println("GOOOOOOOOOOOOOOOOOOOOOOOOOo+"+trn_obj[0].getGLCode());
				
				/*a=(commonlocal.storeGLTransaction(trn_obj)==1)?1:0;
				if(a!=1)
					throw new SQLException("GL not posted");*/
			}
				
				PreparedStatement[] pstmt =new PreparedStatement[trn_obj.length];
				for(int j=0; j<trn_obj.length; j++ )
			    {					
					pstmt[j]=connection.prepareStatement("insert into GLTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					pstmt[j].setString(1,trn_obj[j].getTrnDate());
				    
				    pstmt[j].setString(2,trn_obj[j].getGLType());
				    System.out.println("GLCODE========="+trn_obj[j].getGLCode());
					pstmt[j].setString(3,trn_obj[j].getGLCode());										
					pstmt[j].setString(4,trn_obj[j].getTrnMode());
					pstmt[j].setDouble(5,trn_obj[j].getAmount());
					pstmt[j].setString(6,trn_obj[j].getCdind());
					pstmt[j].setString(7,trn_obj[j].getAccType());
					pstmt[j].setString(8,trn_obj[j].getAccNo());
					pstmt[j].setInt(9,trn_obj[j].getTrnSeq());
					pstmt[j].setString(10,trn_obj[j].getTrnType());
					pstmt[j].setInt(11,trn_obj[j].getRefNo());
					pstmt[j].setString(12,trn_obj[j].getVtml());
					pstmt[j].setString(13,trn_obj[j].getVid());
					pstmt[j].setString(14,trn_obj[j].getVDate());
			    } 			    
			    for(int r=0; r<pstmt.length; r++ )
				{
			    	System.out.println("LENGTH================"+pstmt.length);
			    	if(pstmt[r].executeUpdate() == 0)
			    		throw new SQLException("Common Bean GL posting problem");
			    	else 
			    		a=1;
				}
			 
			
			return a;
			
		}catch(Exception sqlexception){
			sqlexception.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
			commonlocal = null;
		}

		return a;
	}

	/**
	 * To retrieve the different Customer Types created for PayOrder from table
	 * PayOrderCommissionExtraRate table
	 *  i.e the code starts from 301
	 */
	public AccCategoryObject[] getCustomerPayTypes() 
	{

		AccCategoryObject acccategoryobject[]=null; 
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//ship......22/06/2006
			//ResultSet rs=statement.executeQuery("select cust_type,descptn from PayOrderCommissionExtraRate where cust_type>300");
			ResultSet rs=statement.executeQuery("select cust_sub_type,descptn from PayOrderCommissionExtraRate where cust_sub_type>300");
			
			if(rs.next())
			{
				rs.last();
				acccategoryobject=new AccCategoryObject[rs.getRow()];
				rs.beforeFirst();
			}
			int i=0;
			
			while(rs.next())
			{
				acccategoryobject[i]=new AccCategoryObject();
				acccategoryobject[i].setCategoryCode(rs.getInt(1));
				System.out.println("vvvvvvvvvvvvvalue:::"+rs.getInt(1));
				acccategoryobject[i].setCategoryDesc(rs.getString(2));
				System.out.println("vdesccccce:::"+rs.getString(2));
				i++;
			}

		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return acccategoryobject;
	}
	
/**
 * this function returns the commission amount(from PayOrderCommission and
 * PayOrderCommissionExtra table tables) to be applied for PayOrder creation
 * This function is called from PayOrderDataEntry(frontcounter package)
 * The commission amount depends on
 * 1. AccountType -- If the PayOrder is not from the Account then accounttype will be null
 * 2. Customer Type
 * 3. PayOrder Amount
 * First, the max_amt for which the commission is defined is picked from PayOrderCommission table,
 * if the payorder amount is > max_amt then rate for this max_amt is picked.
 * Second, depending on the Customer Type extra rate is picked from PayOrderCommissionExtra table
 * Third, if the payorder amount is > max_amt then max_comm_rate is applied for the
 * (payorder amount - max_amt) and it is added to commission rate
 */
	/*public double getCommission(String actype,int custtype,double amt) 
	{
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			int more=0;
			double max=0,comm=0,extra_comm=0;
			ResultSet rs=null;
			System.out.println("values:"+actype+"--- "+custtype+"----"+amt);
			System.out.println("actype:"+actype);
			if(actype!=null)			    
				rs=statement.executeQuery("select max(max_amt) from PayOrderCommission where ac_type='"+actype+"'");
			    rs=statement.executeQuery("select max(max_amt) from PayOrderCommission where ac_type='Customer'");//For Customer
			else
			{
				System.out.println("actype==nulll;;;;;;;;;;1"+actype);
			    rs=statement.executeQuery("select max(max_amt) from PayOrderCommission where ac_type is null");
				
			}
			if(rs.next())
			max=rs.getDouble(1);
			System.out.println("value of max==="+max);
			rs.close();

			if(max<amt)
				more=1;
			if(more==1)
			{	
				if(actype!=null)	
					rs=statement.executeQuery("select commission_rate from PayOrderCommission where ac_type='Customer' and "+max+" between min_amt and max_amt");
				else
				{
				    System.out.println("actype==nulll;;;;;;;;;2"+actype);
				    rs=statement.executeQuery("select commission_rate from PayOrderCommission where ac_type is null and "+max+" between min_amt and max_amt");
				}
			}
			else
			{
				if(actype!=null)	
					rs=statement.executeQuery("select commission_rate from PayOrderCommission where ac_type='Customer' and "+amt+" between min_amt and max_amt");
				else
				{
				    System.out.println("actype==nulll;;;;;;;;;3"+actype);
				    rs=statement.executeQuery("select commission_rate from PayOrderCommission where ac_type is null and "+amt+" between min_amt and max_amt");
				}
			}

			if(rs.next())
			{	
				comm=rs.getDouble(1);
				rs=statement.executeQuery("select rate from PayOrderCommissionExtraRate where cust_type="+custtype+"");
				
				if(rs.next())
				{
					extra_comm=rs.getDouble(1);
					comm=comm+(extra_comm*comm)/100;
				}
			}

			rs.close();
			if(more==1)
			  {
			    System.out.println("more0002::::::::"+more);
			    double rate_amt=0;
				double rate_for_amt=0;
				rs=statement.executeQuery("select max_comm_rate,comm_rate_for_amt from Modules where modulecode=1016001");
				if(rs.next())
				{	
					rate_amt=rs.getDouble(1);
					rate_for_amt=rs.getDouble(2);
				}
				amt=amt-max;
				comm=comm+(java.lang.Math.round(amt/rate_for_amt)*rate_amt);
				rs.close();
			  }
			System.out.println("comm"+comm);
			return comm;

		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return(0.0);
	}*/
	
	/** Changed on 21/06/2006   **/
	public double getCommission(String actype,int subcategory,double amt,String po_date) 
	{
		Connection connection=null;
		Statement statement=null;
		int cat_code=0;
		double min_amt=0,comm=0,extra_comm=0,rate=0;
		String comm_type=null;
		ResultSet rs=null ,rs_extra=null;
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			System.out.println("values:"+actype+"-----"+subcategory+"------"+amt+"----------"+po_date);
			
			if(actype!=null)
			    rs=statement.executeQuery("select * from PayOrderCommission where po_type='"+actype+"' and cust_type in (select catcode from AccountSubCategory where subcatcode="+subcategory+") and '"+amt+"' between fr_amt and to_amt and '"+Validations.convertYMD(po_date)+"' BETWEEN concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1, (locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1))and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1, (locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1))");
			
			if(rs.next())
			{
				comm_type=rs.getString("comm_type");	
				cat_code=rs.getInt("cust_type");
				min_amt=rs.getDouble("min_comm_amt");
				
				rate=rs.getDouble("comm_rate");			 	
			 	System.out.println("TYPE==="+comm_type);
				System.out.println("Code=="+cat_code);
				System.out.println("MIN_COMM_AMT=="+min_amt);
				System.out.println("Amt="+amt);
				
				if(comm_type.equalsIgnoreCase("S"))			//Slab
				{
					comm=rs.getDouble("comm_rate");
					comm=(comm<min_amt)?min_amt:comm;
				}
				
				else if(comm_type.equalsIgnoreCase("P"))	//Percentage	
				{
					System.out.println("Enter P LOOP");
				 	//comm=java.lang.Math.round((amt/1000)*rate);	
					comm=java.lang.Math.round((Math.ceil(amt/1000))*rate);
				 	comm=(comm<min_amt)?min_amt:comm;
				}
			
				rs_extra=statement.executeQuery("select extra_rate from PayOrderCommissionExtraRate where po_type='"+actype+"' and cust_sub_type="+subcategory+" and '"+amt+"' between fr_amt and to_amt and '"+Validations.convertYMD(po_date)+"' BETWEEN concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1, (locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1))and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1, (locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1))");				
				if(rs_extra.next())
				{
					extra_comm=rs_extra.getDouble(1);
					comm=comm+(extra_comm);
				}
				
				System.out.println("comm"+comm);
				return comm;		
			}
		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return(0.00);
	}

/**
 * Function to data read from POLink and PayOrderMake table
 */
	public PayOrderObject[] getPOMake(int pono) 
	{
		PayOrderObject po[]=null;

		Connection connection=null;
		Statement statement=null,stmt_temp = null;
       System.out.println("the payorder is------------------"+pono);
		try
		{
			connection = getConnection();
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt_temp = connection.createStatement();
			ResultSet rs = null;
            
            //ship.......31/05/2007
			/*if(pono!=0)
			{
//				rs=stmt.executeQuery("select * from PayOrderMake where po_made='F' and po_date='"+getSysDate()+"'");
				rs=statement.executeQuery("select po.po_acty,po.po_acno,po.po_amt,po.po_sno,po.po_type,po.po_gltype,po.po_glcd,po.po_date,po.po_purchaser_name,po.po_favour_name from PayOrderMake po left join POLink pl on (po.po_sno=pl.ref_sno and po.po_type=pl.ref_type and pl.payord_no!="+pono+") where po.po_made='F' and po.ve_user is not null and pl.ref_sno is null");//and po.po_date='"+getSysDate()+"'
			}
			else
				//rs=statement.executeQuery("select po.po_acty,po.po_acno,po.po_amt,po.po_sno,po.po_type,po.po_gltype,po.po_glcd,po.po_date,po.po_purchaser_name,po.po_favour_name from PayOrderMake po left join POLink pl on (po.po_sno=pl.ref_sno and po.po_type=pl.ref_type) where po.po_made='F' and po.ve_user is not null and  pl.ref_sno is null"); //and po.po_date='"+getSysDate()+"' chenged by murugesh on 03/11/2006
				rs=statement.executeQuery("select po.po_acty,po.po_acno,po.po_amt,po.po_sno,po.po_type,po.po_gltype,po.po_glcd,po.po_date,po.po_purchaser_name,po.po_favour_name from PayOrderMake po left join POLink pl on (po.po_sno=pl.ref_sno and po.po_type=pl.ref_type) where po.po_made='F' and po.ve_user is not null ");

			if(rs.next())
			{
				rs.last();
				po=new PayOrderObject[rs.getRow()];
				rs.beforeFirst();
			}

			int i=0;
			while(rs.next())
			{
			    file_logger.info("ACtype             "+rs.getString("po_acty"));
			    file_logger.info("ACNO               "+rs.getInt("po_acno"));
				po[i]=new PayOrderObject();
				po[i].setPOSerialNo(rs.getInt("po_sno"));
				po[i].setPOType(rs.getString("po_type"));
				po[i].setPOGlCode(rs.getInt("po_glcd"));
				po[i].setPOGlType(rs.getString("po_gltype"));
				po[i].setPOMakeDate(rs.getString("po_date"));
				po[i].setPOPayee(rs.getString("po_purchaser_name"));
				po[i].setPOFavour(rs.getString("po_favour_name"));
				po[i].setPOAccType(rs.getString("po_acty"));
				po[i].setPOAccNo(rs.getInt("po_acno"));
				po[i].setPOAmount(rs.getDouble ("po_amt"));
				i++;

			}*/
            
            if(pono>0)
            	//suraj 18/10/2007    
            {
                stmt_temp.addBatch("drop table if exists consolidate");
                stmt_temp.addBatch("create temporary table consolidate as select * from PayOrderMake limit 1");
             stmt_temp.addBatch("delete from consolidate");
                stmt_temp.addBatch("insert into consolidate select * from PayOrderMake po where po.po_made='F' and po.ve_user is not null and po.ve_tml is not null and po.ve_date is not null");
                stmt_temp.addBatch("delete from consolidate where po_sno in(select pom.po_sno from PayOrderMake pom,POLink pl,PayOrder po where pom.po_sno=pl.ref_sno and pom.po_type=pl.ref_type and pom.po_made='F' and pom.ve_user is not null and pom.ve_tml is not null and pom.ve_date is not null and po.payord_no=pl.payord_no and po.ve_user is null and po.ve_tml is null and po.ve_date is null and pl.payord_no!="+pono+")");
                stmt_temp.executeBatch();
                
                rs = statement.executeQuery("select po_acty,po_acno,po_amt,po_sno,po_type,po_gltype,po_glcd,po_date,po_purchaser_name,po_favour_name from consolidate");
                
                if(rs.next())
                {
                    rs.last();
                    po=new PayOrderObject[rs.getRow()];
                    rs.beforeFirst();
                }

                int i=0;
                
                while(rs.next())
                {
                    po[i]=new PayOrderObject();
                    po[i].setPOSerialNo(rs.getInt("po_sno"));
                    po[i].setPOType(rs.getString("po_type"));
                    po[i].setPOGlCode(rs.getInt("po_glcd"));
                    po[i].setPOGlType(rs.getString("po_gltype"));
                    po[i].setPOMakeDate(rs.getString("po_date"));
                    po[i].setPOPayee(rs.getString("po_purchaser_name"));
                    po[i].setPOFavour(rs.getString("po_favour_name"));
                    po[i].setPOAccType(rs.getString("po_acty"));
                    po[i].setPOAccNo(rs.getInt("po_acno"));
                    po[i].setPOAmount(rs.getDouble ("po_amt"));
                    
                    i++;
                }
            }
            
            	
  /*          {
                //rs = statement.executeQuery("select po.po_acty,po.po_acno,po.po_amt,po.po_sno,po.po_type,po.po_gltype,po.po_glcd,po.po_date,po.po_purchaser_name,po.po_favour_name from PayOrderMake po left join POLink pl on (po.po_sno=pl.ref_sno and po.po_type=pl.ref_type and pl.payord_no!="+pono+") where po.po_made='F' and po.ve_user is not null and pl.ref_sno is null");
                
                rs = statement.executeQuery("select distinct po.po_acty,po.po_acno,po.po_amt,po.po_sno,po.po_type,po.po_gltype,po.po_glcd,po.po_date,po.po_purchaser_name,po.po_favour_name from PayOrderMake po left join POLink pl on (po.po_sno=pl.ref_sno and po.po_type=pl.ref_type) where po.po_made='F' and po.ve_user is not null");
                
                if(rs.next())
                {
                    rs.last();
                    po=new PayOrderObject[rs.getRow()];
                    rs.beforeFirst();
                }

                int i=0;
                
                while(rs.next())
                {
                    po[i]=new PayOrderObject();
                    po[i].setPOSerialNo(rs.getInt("po_sno"));
                    po[i].setPOType(rs.getString("po_type"));
                    po[i].setPOGlCode(rs.getInt("po_glcd"));
                    po[i].setPOGlType(rs.getString("po_gltype"));
                    po[i].setPOMakeDate(rs.getString("po_date"));
                    po[i].setPOPayee(rs.getString("po_purchaser_name"));
                    po[i].setPOFavour(rs.getString("po_favour_name"));
                    po[i].setPOAccType(rs.getString("po_acty"));
                    po[i].setPOAccNo(rs.getInt("po_acno"));
                    po[i].setPOAmount(rs.getDouble ("po_amt"));
                    
                    i++;
                }
            }
*/            else
            {
                stmt_temp.addBatch("drop table if exists consolidate");
                stmt_temp.addBatch("create temporary table consolidate as select * from PayOrderMake limit 1");
              stmt_temp.addBatch("delete from consolidate");
                stmt_temp.addBatch("insert into consolidate select * from PayOrderMake po where po.po_made='F' and po.ve_user is not null and po.ve_tml is not null and po.ve_date is not null");
                stmt_temp.addBatch("delete from consolidate where po_sno in(select pom.po_sno from PayOrderMake pom,POLink pl,PayOrder po where pom.po_sno=pl.ref_sno and pom.po_type=pl.ref_type and pom.po_made='F' and pom.ve_user is not null and pom.ve_tml is not null and pom.ve_date is not null and po.payord_no=pl.payord_no and po.ve_user is null and po.ve_tml is null and po.ve_date is null)");
                stmt_temp.executeBatch();
                
                rs = statement.executeQuery("select po_acty,po_acno,po_amt,po_sno,po_type,po_gltype,po_glcd,po_date,po_purchaser_name,po_favour_name from consolidate");
                
                if(rs.next())
                {
                    rs.last();
                    po=new PayOrderObject[rs.getRow()];
                    rs.beforeFirst();
                }

                int i=0;
                
                while(rs.next())
                {
                    po[i]=new PayOrderObject();
                    po[i].setPOSerialNo(rs.getInt("po_sno"));
                    po[i].setPOType(rs.getString("po_type"));
                    po[i].setPOGlCode(rs.getInt("po_glcd"));
                    po[i].setPOGlType(rs.getString("po_gltype"));
                    po[i].setPOMakeDate(rs.getString("po_date"));
                    po[i].setPOPayee(rs.getString("po_purchaser_name"));
                    po[i].setPOFavour(rs.getString("po_favour_name"));
                    po[i].setPOAccType(rs.getString("po_acty"));
                    po[i].setPOAccNo(rs.getInt("po_acno"));
                    po[i].setPOAmount(rs.getDouble ("po_amt"));
                    
                    i++;
                }
            }
            ///////////
		}
        catch(Exception sqlexception)
        {
        	ctx.setRollbackOnly();
            sqlexception.printStackTrace();
        }
		finally
		{
			try
			{
				connection.close();
			}
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
		}

		return po;
	}

/**
 * Function to insert data in POLink and PayOrder table and to generate unique payorder no 
 * Details from PayOrder table for the newly generated PayOrder is copied into PayOrderLog table
 */
	public int storePOLink(PayOrderObject[] po,int updated,int delete) 
	{
		int pono=0;
		double amount=0;
		String valid_date=null,trn_date=null;
		int valid_period=0;
		ResultSet rs=null;
		Connection connection=null;
		Statement statement=null,stmt_trn=null,stmt_po=null,stmt_odcc=null;
		AccountTransObject am=new AccountTransObject();
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_trn=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_po=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_odcc=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
						
			if(updated==0)// not for updation
			{
				rs = statement.executeQuery("select lst_acc_no,chq_validity_period from Modules where modulecode=1016001");
				if(rs.next())
				{	
					pono=rs.getInt(1)+1;
					valid_period=rs.getInt(2);
				}
			}

			if(updated==1||delete==1)// for update for delete
			{
				if(statement.executeUpdate("delete from PayOrder where payord_no="+po[0].getPayOrderNo()+"")!=1)
					throw new SQLException("cannot delete from PayOrder table");
				if(statement.executeUpdate("delete from POLink where payord_no="+po[0].getPayOrderNo()+"")<=0)
					throw new SQLException("cannot delete from POLink table");
				pono=po[0].getPayOrderNo();
			}

			if(updated==1||updated==0)
			{
				PreparedStatement ps=connection.prepareStatement("insert into POLink values(?,?,?,?)");
				for(int i=0;i<po.length;i++)
				{
					trn_date=po[i].uv.getUserDate().substring(0,10);
					ps.setString(1,trn_date);
					ps.setInt(2,pono);
					ps.setInt(3,po[i].getPOSerialNo());
					ps.setString(4,po[i].getPOType());
					amount=amount+po[i].getPOAmount();
	
					ps.addBatch();
				}
				
				int insert[]=ps.executeBatch();
				if(insert.length!=po.length)
					throw new SQLException("POLink not inserted");
				
				trn_date=po[0].uv.getUserDate().substring(0,10);				
				valid_date=Validations.DateAdd(trn_date,valid_period);
				System.out.println(valid_date);
								
				ps=connection.prepareStatement("insert into PayOrder values("+pono+",'"+trn_date+"','C','"+po[0].getPOPayee()+"',"+amount+",'F','F',0,0,'"+po[0].getPOChqNo()+"','"+valid_date+"',null,'"+po[0].uv.getUserTml()+"','"+po[0].uv.getUserId()+"','"+po[0].uv.getUserDate()+"',null,null,null,0)");

				if(ps.executeUpdate()==1)
				{
					if(updated==0)//not updation
						if(statement.executeUpdate("update Modules set lst_acc_no=lst_acc_no+1 where modulecode=1016001")!=1)
							throw new SQLException("Modules table not updated");
				}
				else
					throw new SQLException("PayOrder not inserted");
				
				/*try
				{								
				    System.out.println("ACTTTTTTTTTTTTTTTTTy="+po[0].getPOAccType());
				    String acctype=po[0].getPOAccType();
				    ResultSet rs3=stmt_po.executeQuery("select pm.po_acty,pm.po_acno,po.ref_sno from PayOrderMake pm,POLink po where pm.po_sno=po.ref_sno and po.payord_no="+pono+"");
			        while(rs3.next())
			        {
			            String acty=rs3.getString("pm.po_acty");
			            int acno=rs3.getInt("pm.po_acno");
			            ResultSet rs1=null;
			            int a=0;
			        
			            if(acty.startsWith("1002") ||acty.startsWith("1007") ||acty.startsWith("1017") ||acty.startsWith("1018"))
			                rs1=stmt_trn.executeQuery("select trn_seq,cl_bal from AccountTransaction where ac_no="+acno+" and ac_type='"+acty+"' order by trn_seq desc limit 1");
			            else
			                rs1=stmt_trn.executeQuery("select trn_seq,cl_bal from ODCCTransaction where ac_no="+po[0].getPOAccNo()+" and ac_type='"+po[0].getPOAccType()+"' order by trn_seq desc limit 1");
						
			            int trnseq=0;
				        double cl_bal=0;
				        	if(rs1.next())
				        {
				            trnseq=rs1.getInt(1);
				            cl_bal=rs1.getDouble(2);
				        }
				        else
				            throw new SQLException("Account not found");

						po[0].setTransSeq(trnseq+1);

						System.out.println("AccType:"+acty);
						System.out.println("AccNo: "+acno);					
						
						PreparedStatement ps1=null;
						
						if(acty.startsWith("1002") ||acty.startsWith("1007") ||acty.startsWith("1017") ||acty.startsWith("1018"))
							ps1=connection.prepareStatement("insert into AccountTransaction values(?,?,'"+getSysDate()+"',?,?,?,?,?,?,?,?,?,?,?,?,null,?,?,'"+getSysDate()+" "+getSysTime()+"',null,null,null");
						else
						{
							ResultSet rs2=null;							
						    rs2=stmt_odcc.executeQuery("select int_uptodt from ODCCMaster where ac_type='"+po[0].getPOAccType()+"' and ac_no="+po[0].getPOAccNo());
							rs2.next();
							String lst_int_caldt=rs2.getString("int_uptodt");
						    ps1=connection.prepareStatement("insert into ODCCTransaction values(?,?,'"+getSysDate()+"',?,?,?,?,?,?,?,?,?,?,?,?,null,?,?,'"+getSysDate()+" "+getSysTime()+"',null,null,null,'"+lst_int_caldt+"')");
						}
						
						System.out.println("for Inseeeeeeeeeeeeeeeeeeeeeertingg");
						ps1.setString(1,acty);
						ps1.setInt(2,acno);
						
						ps1.setString(3,"P");
						ps1.setInt(4,po[0].getTransSeq());
						ps1.setDouble(5,po[0].getTransAmount());
						ps1.setString(6,"T");
						ps1.setString(7,po[0].uv.getUserTml());
						ps1.setString(8,po[0].getCDindicator());
						ps1.setDouble(9,0);
						ps1.setString(10,null);
						ps1.setString(11,"PayOrder "+po[0].getPayOrderNo());						
						ps1.setInt(12,po[0].getPayOrderNo());
						ps1.setString(13,po[0].getPOPayee());
						ps1.setDouble(14,cl_bal-po[0].getTransAmount());
						
						ps1.setString(15,po[0].uv.getUserTml());
						ps1.setString(16,po[0].uv.getUserId());
						
						if((a=ps1.executeUpdate())!=1)
							throw new SQLException("ODCC/AccountTransaction not stored");
			        }
				}catch(SQLException sqlex){sqlex.printStackTrace();}*/
									
			}

			return pono;

		}catch(Exception sqlexception)
		{
			ctx.setRollbackOnly();
			sqlexception.printStackTrace();
			try
			{
				ctx.setRollbackOnly();
			}catch(Exception exception){exception.printStackTrace();}
		}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return pono;
	}

	/**
	 * Function to read the details from PayOrder and POLink tables
	 * for updation and verification
	 */
	public PayOrderObject[] getPODetails(int po_no) 
	{
		PayOrderObject po[]=null;
		
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);


			ResultSet rs = statement.executeQuery("select * from PayOrder,POLink where PayOrder.payord_no="+po_no+" and PayOrder.payord_no=POLink.payord_no "); //and PayOrder.payord_dt='"+getSysDate()+"'
			if(rs.next())
			{
				rs.last();
				po=new PayOrderObject[rs.getRow()];
				rs.beforeFirst();
			}
			

			int i=0;
			while(rs.next())
			{
				po[i]=new PayOrderObject();
				if(rs.getString("ve_user")==null) //verified
					po[i].setVerified("F");
				else
					po[i].setVerified("T");

				po[i].setPOChqNo(rs.getInt("po_chq_no"));
				po[i].setPOPayee(rs.getString("payee_nm"));
				po[i].setRefSerialNo(rs.getInt("ref_sno"));
				po[i].setPOType(rs.getString("ref_type"));
				po[i].uv.setUserId(rs.getString("de_user"));
				po[i].setPODate(rs.getString("payord_dt"));
				po[i].setTransAmount(rs.getDouble("trn_amt"));
				i++;

			}

			return po;
		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return po;
	}
	
	/**
	 * Function to verify PayOrder table and to set po_made='T' in PayOrderMake table
	 * Writes Debit entry to POSuspense GL and credir entry to PayOrder GL
	 */
	public int verifyPayOrder(PayOrderObject po) 
	{
		int pono=0;
		int return_value=0;
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			if(statement.executeUpdate("update PayOrder set ve_user='"+po.uv.getVerId()+"',ve_tml='"+po.uv.getVerTml()+"',ve_date='"+po.uv.getVerDate()+"' where payord_no="+po.getPayOrderNo()+"")==1)
			{	
				pono=po.getPayOrderNo();
				return_value=1;
				String ac_type=po.getPOAccType();
				if(statement.executeUpdate("insert into PayOrderLog select * from PayOrder where payord_no="+pono+"")!=1)
					throw new SQLException("PayOrderLog not inserted");
				
				/*if(ac_type.startsWith("1002")||ac_type.startsWith("1007")||ac_type.startsWith("1002")||ac_type.startsWith("1007"))
				    statement.executeUpdate("update AccountTransaction set ve_user='"+po.uv.getVerId()+"',ve_tml='"+po.uv.getVerTml()+"',ve_date='"+getSysDate()+" "+getSysTime()+"' where ref_no="+pono+" ");
				else
				    statement.executeUpdate("update ODCCTransaction set ve_user='"+po.uv.getVerId()+"',ve_tml='"+po.uv.getVerTml()+"',ve_date='"+getSysDate()+" "+getSysTime()+"' where ref_no="+pono+" ");*/
				
				ResultSet rs=statement.executeQuery("select ref_sno,ref_type from POLink where payord_no="+po.getPayOrderNo()+"");
				
				while(rs.next())
				{
					int a=rs.getInt("ref_sno");
					String type=rs.getString("ref_type");
					statement.addBatch("update PayOrderMake set po_made='T' where PayOrderMake.po_sno="+a+" and PayOrderMake.po_type='"+type+"'");

				}
				if(statement.executeBatch().length>0)
				{
					po.setGLRefCode(1016001);
					po.setGLSubCode(1);
					po.setTransMode("T");
					po.setTransType("P");
					po.setTrnSource(po.uv.getUserTml());
					po.setCDindicator("D");
					System.out.println("Problem of Setting Ref ACty n 3333333333Acno");
					po.setPOType("1016001");
					po.setPayOrderNo(po.getPayOrderNo());
					po.setTransSeq(0);					
					
					if((return_value=storePOGL(po))==1)
					{	
						po.setGLRefCode(1016001);
						po.setGLSubCode(3);
						po.setTransMode("T");
						po.setTransType("R");
						po.setTrnSource(po.uv.getUserTml());
						po.setCDindicator("C");
						System.out.println("Problem of Setting Ref ACty n4444444444 Acno");
						po.setPOType("1016001");
						po.setPayOrderNo(po.getPayOrderNo());
						po.setTransSeq(0);
						if((return_value=storePOGL(po))!=1)
							throw new SQLException("GL not posted");
					}	
					else
						throw new SQLException("GL not posted");
				}
				else
					throw new SQLException("PayOrderMake not upadted");
			}
			else
				throw new SQLException("PayOrder not updated");
				

		}catch(Exception sqlexception)
		{
			ctx.setRollbackOnly();
			sqlexception.printStackTrace();
			try
			{
				ctx.setRollbackOnly();
				return_value=0;
			}catch(Exception exception){exception.printStackTrace();}
		}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return return_value;
	}

	public boolean accountClose(String actype,int acno,String date) 
	{
		Connection connection=null;
		Statement statement=null,stmt=null;
		ResultSet rs=null;
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println("BEANNNNNNNNNNNNNNNNNNNNNNN AC_Ty="+actype);
						
			if(actype.length()>0 && acno>0)
				rs=statement.executeQuery("select cn.*,Modules.moduleabbr,cb.book_no from ChequeNo cn,Modules,ChequeBook cb where cb.ac_type='"+actype+"' and cb.ac_no="+acno+" and cb.ac_type=cn.ac_type and cb.ac_no=cn.ac_no and cb.book_no=cn.book_no and cn.chq_del='F' and Modules.modulecode='"+actype+"'");
			
			while(rs.next())
				stmt.executeUpdate("update ChequeNo set chq_cancel='T' where ac_type='"+actype+"' and ac_no="+acno+"");
			
			if(actype.startsWith("1002")||actype.startsWith("1007")||actype.startsWith("1017")||actype.startsWith("1018"))
			{
			    if(statement.executeUpdate("update AccountMaster set ac_status='C',ac_closedate='"+date+"' where ac_type='"+actype+"' and ac_no="+acno)>=1)
			        return true;
			    else
			        return false;
			}
			else
			{
			    if(statement.executeUpdate("update ODCCMaster set ac_status='C',ac_closedate='"+date+"' where ac_type='"+actype+"' and ac_no="+acno)>=1)
			        return true;
			    else
			        return false;
			}
		}catch(Exception sqlexception){
			ctx.setRollbackOnly();
			sqlexception.printStackTrace();
			}
		finally{
			try{
				connection.close();
			}catch(Exception exception){exception.printStackTrace();}
			
		}

		return false;
	}

/**
 * Function to store details in
 *  ChequeBook,ChequeNo tables for ChequeBook Issue
 * For Updation old records are deleted where ve_user is null and
 * new records are inserted again
 */
	public int storeChqDetails(ChequeObject ch,int type)
	{
		int a=0;
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if(type==1 ||type==2) //1---delete 2----update
			{    
				statement.executeUpdate("delete from ChequeBook where ac_type='"+ch.getAccType()+"' and ac_no="+ch.getAccNo()+" and ve_user is null");
				statement.executeUpdate("delete from ChequeNo where ac_type='"+ch.getAccType()+"' and ac_no="+ch.getAccNo()+" and ve_user is null");
				a=1;
			}	
			if(type==2)
			{    
			PreparedStatement ps=connection.prepareStatement("insert into  ChequeBook values(?,?,'"+ch.getTransDate()+"',?,?,?,?,?,0,?,?,?,null,null,null)");
			ps.setString(1,ch.getAccType());
			ps.setInt(2,ch.getAccNo());
			ps.setInt(3,ch.getF_Chq_Prev());
			ps.setInt(4,ch.getBookNo());
			ps.setInt(5,ch.getFirstChequeNo());
			ps.setInt(6,ch.getLastChequeNo());
			ps.setInt(7,ch.getNumLeaf());
			ps.setString(8,ch.uv.getUserTml());
			ps.setString(9,ch.uv.getUserId());
			ps.setString(10,ch.uv.getUserDate());

			a=ps.executeUpdate();

			if(a==1)
			{	
				PreparedStatement ps1=connection.prepareStatement("insert into ChequeNo(ac_type,ac_no,book_no,chq_no,chq_iss_dt,post_dated,stop_pymnt,chq_del,chq_cancel,de_user,de_tml,de_date)values('"+ch.getAccType()+"',"+ch.getAccNo()+","+ch.getBookNo()+",?,null,'F','F','F','F','"+ch.uv.getUserId()+"','"+ch.uv.getUserTml()+"','"+ch.uv.getUserDate()+"')");
				for(int i=ch.getFirstChequeNo();i<=ch.getLastChequeNo();i++)
				{
					ps1.setInt(1,i);
					ps1.addBatch();
				}
				int execute[]=ps1.executeBatch();
				 for(int i=0;i<execute.length;i++)
				 	if((a=execute[i])==0)
				 		throw new SQLException("Details not stored into ChequeNo");
				 
	
			}
			else 
				throw new SQLException("Details not stored into ChequeBook");
			}	

		}catch(Exception sqlexception)
		{	
			ctx.setRollbackOnly();
			sqlexception.printStackTrace();
			try
			{
				ctx.setRollbackOnly();
				a=0;
			}catch(Exception exception){exception.printStackTrace();}
		}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return a;
	}

public ChequeObject[] showChequeNos(String acctype,int accno)
{
	ChequeObject ch[]=null;

	Connection connection=null;
	Statement statement=null;

	try
	{
		connection=getConnection();
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);


	ResultSet rs=statement.executeQuery("select book_no,fst_chq_no,lst_chq_no,no_leaf from ChequeBook where ac_no="+accno+" and ac_type='"+acctype+"' and ve_user is not null order by request_dt");
	rs.last();
	ch=new ChequeObject[rs.getRow()];
	rs.beforeFirst();
	System.out.println(ch.length);
	int i=0;

	while(rs.next())
	{

		double no=rs.getInt(4);
		ch[i]=new ChequeObject();
		ch[i].setBookNo(rs.getInt(1));
		ch[i].setFirstChequeNo(rs.getInt(2));
		ch[i].setLastChequeNo(rs.getInt(3));

		Statement stmt1=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs1=stmt1.executeQuery("select count(chq_del) from ChequeNo where chq_del='T' and chq_no Between "+ch[i].getFirstChequeNo()+" and "+ch[i].getLastChequeNo()+" and ac_no="+accno+" and ac_type='"+acctype+"' and ve_user is not null");

		if(rs1.next())
			ch[i].setSum((int)((rs1.getInt(1)*100 )/no));
		System.out.println("perc="+ch[i].getSum());
		Statement stat=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs2=stat.executeQuery("select chq_no from ChequeNo where chq_del='F' and chq_no Between "+ch[i].getFirstChequeNo()+" and "+ch[i].getLastChequeNo()+" and ac_no="+accno+" and ac_type='"+acctype+"' and ve_user is not null");
		if(rs2.next())
		{
		 rs2.last(); 
		 ch[i].setChqNoCount(rs2.getRow());
		 rs2.beforeFirst();
		}

		int j=0;
		rs2.last();
		System.out.println("here"+rs2.getRow());
		rs2.beforeFirst();
		while(rs2.next())
		{
			ch[i].setChqNo(rs2.getInt(1),j);
			j++;
		}

		System.out.println("here3");
		i++;
	}

	}catch(Exception sqlexception){sqlexception.printStackTrace();}
	finally
	{
		try
		{
			connection.close();
		}catch(Exception ex){ex.printStackTrace();}
	}

	return ch;

}

/**
 * gets the details of latest ChequeBook issued to actype and acno passed
 * @param actype
 * @param acno
 * @return ChequeObject
 */

public ChequeObject getChqDetails(String actype,int acno)
{
	ChequeObject ch=null;
	Connection connection=null;
	Statement statement=null;

	try
	{
		connection=getConnection();
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

		ResultSet rs=statement.executeQuery("select * from ChequeBook where ve_user is null and ac_type='"+actype+"' and ac_no="+acno +" order by concat(mid(de_date,locate('/',de_date,6)+1,4),'-',mid(de_date,locate('/',de_date)+1,(locate('/',de_date,4)-locate('/',de_date)-1)),'-',left(de_date,locate('/',de_date)-1),' ',right(de_date,10)) desc");

		if(rs.next())
		{
			ch=new ChequeObject();
			ch.setAccType(rs.getString(1));
			ch.setAccNo(rs.getInt(2));
			ch.setRequestDate(rs.getString(3));
			ch.setF_Chq_Prev(rs.getInt(4));
			ch.setBookNo(rs.getInt(5));
			ch.setFirstChequeNo(rs.getInt(6));
			ch.setLastChequeNo(rs.getInt(7));
			ch.setNumLeaf(rs.getInt(8));
		}

	}catch(Exception sqlexception){sqlexception.printStackTrace();}
	finally{
		try{
			connection.close();
		}catch(Exception exception){exception.printStackTrace();}
		
	}

	return ch;
}

/**
 * this function  is called when Cheque Book issued to the customers is verified.
 * It updates the ve_user,ve_tml,ve_date in ChequeBook,ChequeNo tables. It also copies the
 * details from ChequeNo table to ChequeNoLog for View Log purpose. 
 * @param acno
 * @param type
 * @param bno   book number
 * @param from  starting leaf no of this book no
 * @param to	last leaf no
 * @param vid   ver id
 * @param tml  ver tml
 * @param vdate ver date
 * @return
 */
	public int verifyCheque(int acno,String type,int bno,int from,int to,String vid,String tml,String vdate)
	{
		int result=0;

		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			if((result=statement.executeUpdate("update ChequeBook set ve_user='"+vid+"', ve_tml='"+tml+"',ve_date='"+vdate+"' where ac_no="+acno+" and ac_type='"+type+"' and book_no="+bno))==1)
			{	
				if((result=statement.executeUpdate("update ChequeNo set ve_user='"+vid+"', ve_tml='"+tml+"',ve_date='"+vdate+"' where ac_no="+acno+" and ac_type='"+type+"' and book_no="+bno+" and chq_no between " +from +" and "+to+""))>0?true:false)
				{	
				 if((result=(statement.executeUpdate("insert into ChequeNoLog  select * from ChequeNo where ac_type='"+type+"' and ac_no="+acno+" and book_no="+bno+"")>0?1:0))==0)
				 	throw new SQLException("ChequeNoLog table not inserted");
				}
				else
					throw new SQLException("ChequeNo table not verified");
			}
			else throw new SQLException("ChequeBook table not verified");
			

		}catch(Exception sqlexception)
		{
			ctx.setRollbackOnly();
			sqlexception.printStackTrace();
			try
			{
				ctx.setRollbackOnly();
				result=0;
			}catch(Exception exception)
			{
				exception.printStackTrace();}
		}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}


		return(result);
	}

/**
 * Function to get the first cheque leaf no of the last Cheque book
 * issued to the Particualr Account Holder from ChequeBook table
 */
	public int getLastChequeNo(int j,String acctype,int accno) 
	{

		int lstno=0;
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			String sub=acctype.substring(1,4);
			ResultSet rs=null;
			rs=statement.executeQuery("select * from ChequeBook where ac_type='"+acctype+"' and ac_no="+accno+" and ve_user is null");
			if(rs.next())
				return -1;
			if(j==1)
				rs=statement.executeQuery("select fst_chq_no  from ChequeBook where ac_no="+accno+" and ac_type='"+acctype+"'  order by concat(mid(ve_date,locate('/',ve_date,6)+1,4),'-',mid(ve_date,locate('/',ve_date)+1,(locate('/',ve_date,4)-locate('/',ve_date)-1)),'-',left(ve_date,locate('/',ve_date)-1),' ',right(ve_date,10)) desc");
			else if(j==2)
			{	
				if(sub.equals("002")|| sub.equals("007"))	
					rs=statement.executeQuery("select fname,fst_chq_no,book_no,lst_chq_no,no_leaf,ChequeBook.ve_user from CustomerMaster,AccountMaster,ChequeBook where CustomerMaster.cid=AccountMaster.cid and AccountMaster.ac_type=ChequeBook.ac_type and AccountMaster.ac_no=ChequeBook.ac_no and ChequeBook.ve_date Is Null And ChequeBook.ac_no="+accno+" and ChequeBook.ac_type='"+acctype+"'");
				else if(sub.equals("014") || sub.equals("015"))
					rs=statement.executeQuery("select fname,fst_chq_no,book_no,lst_chq_no,no_leaf,ChequeBook.ve_user from CustomerMaster,ODCCMaster,ChequeBook where CustomerMaster.cid=ODCCMaster.cid and ODCCMaster.ac_type=ChequeBook.ac_type and ODCCMaster.ac_no=ChequeBook.ac_no and ChequeBook.ve_date Is Null And ChequeBook.ac_no="+accno+" and ChequeBook.ac_type='"+acctype+"'");
			
			}

			if(rs.next())
				lstno=rs.getInt(1);
			//	if(acctype.equals("SB")||acctype.equals("CA"))
					

		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return lstno;

	}
/*	public AccountCategoryObject[] getSBtypes(String mod_name,String ac_ty,String sub_type) 
	{
		int norows=0;
		AccountCategoryObject ac[]=null;
		try
		{
		ResultSet rs;
		if(sub_type==null)
			rs=stmt.executeQuery("select distinct ac_cat_nm from AccountCategory where mod_name='"+mod_name+"' and ac_ty='"+ac_ty+"'");
		else
			rs=stmt.executeQuery("select distinct Subac_cat_nm from AccountCategory where mod_name='"+mod_name+"' and ac_ty='"+ac_ty+"' and ac_cat_nm='"+sub_type+"'");

		while(rs.next())
		norows++;

		ac= new AccountCategoryObject[norows];

		if(sub_type==null)
			rs=stmt.executeQuery("select distinct ac_cat_nm from AccountCategory where mod_name='"+mod_name+"' and ac_ty='"+ac_ty+"'");
		else
			rs=stmt.executeQuery("select distinct Subac_cat_nm from AccountCategory where mod_name='"+mod_name+"' and ac_ty='"+ac_ty+"' and ac_cat_nm='"+sub_type+"'");

		for(int i=0;i<norows;i++)
		{
			if(!rs.next())
				break;
			ac[i]= new AccountCategoryObject();
			if(sub_type==null)
				ac[i].setAccCatname(rs.getString("ac_cat_nm"));
			if(sub_type!=null)
				ac[i].setSubAccname(rs.getString("Subac_cat_nm"));

		}
		return ac;
		}catch(SQLException sqlexception){sqlexception.printStackTrace();}
		return ac;

	}*/
/**
 * For calculating from_interest_date(for setting int_pbl_date in AccountMaster table)
 * for the New Accounts, intfrom_day &  intto_day are taken from Modules
 * (i.e from_day and to_day of the month
 * between which the min_bal of the transactions happened has to be taken)
 * ) 
 * If Account Open day is between intfrom_day and intto_day then int_pbl_date will be
 * 1 st of the next month otherwise it will be 1 st of this AccountOpened month
 * @param Opendt
 * @param code
 * @return
 * @throws RemoteException
 */	
	public String getIntDate(String Opendt,int code) 
	{
		String intdt=null;
		int day1=0;
		int day2=0;
		ResultSet rs=null;
		int a1[]={31,28,31,30,31,30,31,31,30,31,30,31};
		int a2[]={31,29,31,30,31,30,31,31,30,31,30,31};
		int dd,mm,yy;
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=statement.executeQuery("select intfrom_day,intto_day from Modules where modulecode="+code+"");

			if(rs.next())
			{
				day1=rs.getInt("intfrom_day");
				day2=rs.getInt("intto_day");
			}
			rs.close();
			
			StringTokenizer st=new StringTokenizer(Opendt,"/");
			dd=Integer.parseInt(st.nextToken());
			mm=Integer.parseInt(st.nextToken());
			yy=Integer.parseInt(st.nextToken());
			if(dd>=day1 && dd<=day2)
			{

				if(mm==12)
				{
					mm=1;
					yy=yy+1;
				}
				else
				mm=mm+1;
				dd=1;
				
			}
			else
			{
				if(mm==1)
				{
					mm=12;
					yy=yy-1;
				}
				else
				mm=mm-1;
				if(yy%4==0)
					dd=a2[mm-1];
				else
					dd=a1[mm-1];
			}
			intdt=Validations.convertDMY(yy+"-"+mm+"-"+dd);
			
			return intdt;
		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return intdt;
		
	}
	
	/**
	 * Added by Deepa on 20/04/06
	 * this function updates the tables AccountMaster,SignatureInstruction,NomineeMaster
	 * tables
	 * @param am
	 * @return
	 */
	public int updateJointHolder(AccountMasterObject am) 
	{
		int acno=0;

		Connection connection=null;
		Statement statement=null;
        PreparedStatement ps1=null;
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			commonlocal=commonlocalhome.create();
			statement.addBatch("delete from SignatureInstruction where ac_type='"+am.getAccType()+"'and ac_no="+am.getAccNo()+" ");
			statement.addBatch("delete from JointHolders where ac_type='"+am.getAccType()+"'and ac_no="+am.getAccNo()+" and cid!="+am.getCid()+"");
			statement.executeBatch();	
			
			acno=am.getAccNo();
		    if(am.getAccType().startsWith("1006"))
                ps1=connection.prepareStatement("update PygmyMaster set no_jt_hldr="+am.getNoOfJointHolders()+" where ac_type='"+am.getAccType()+"' and ac_no="+am.getAccNo()+" ");
            else
                ps1=connection.prepareStatement("update AccountMaster set no_jt_hldr="+am.getNoOfJointHolders()+" where ac_type='"+am.getAccType()+"' and ac_no="+am.getAccNo()+" ");
			
			if(ps1.executeUpdate()==1)
			{		
                System.out.println("signature details==="+am.getSigObj());
				if(am.getSigObj()!=null)
					commonlocal.storeSignatureDetails(am.getSigObj(),am.getAccNo());
	
				if(am.getJointCid()!=null)
				{
					int addrtype[]=am.getJointAddrType();
					int jcids[]=am.getJointCid();
					for(int i=0;i<jcids.length;i++)
					{	
						if(statement.executeUpdate("insert into JointHolders values('"+am.getAccType()+"','"+am.getAccNo()+"','"+jcids[i]+"','"+addrtype[i]+"')")!=1)
							throw new SQLException("Details not stored in JointHolders");
					}
				}
				if(am.getAccType().startsWith("1006"))
                    statement.addBatch("insert into PygmyMasterLog select * from PygmyMaster where ac_type='"+am.getAccType()+"' and ac_no="+am.getAccNo()+"");
                else
                    statement.addBatch("insert into AccountMasterLog select * from AccountMaster where ac_type='"+am.getAccType()+"' and ac_no="+am.getAccNo()+"");					
				statement.addBatch("insert into SignatureInstructionLog select * from SignatureInstruction where ac_type='"+am.getAccType()+"' and ac_no="+am.getAccNo()+" and cid!="+am.getCid()+" ");					
				statement.addBatch("insert into JointHoldersLog select * from JointHolders where ac_type='"+am.getAccType()+"' and ac_no="+am.getAccNo()+" and cid!="+am.getCid()+" ");
				statement.executeBatch();	
		}
		else
			acno=0;
			
	return acno;
	}catch(NomineeNotCreatedException sqlexception)
	{
		ctx.setRollbackOnly();
		sqlexception.printStackTrace();
		try
		{	
			ctx.setRollbackOnly();
			acno=0;
		}catch(Exception exception){exception.printStackTrace();}		
	}
	catch(SignatureNotInsertedException sqlexception)
	{
		sqlexception.printStackTrace();
		try
		{
			ctx.setRollbackOnly();
			acno=0;
		}catch(Exception exception){exception.printStackTrace();}		
	}
	catch(Exception sqlexception)
	{
		sqlexception.printStackTrace();
		try
		{
			ctx.setRollbackOnly();
			acno=0;
		}catch(Exception exception){exception.printStackTrace();}
	}
	finally
	{
		try
		{
			connection.close();
		}catch(Exception ex){ex.printStackTrace();}
		commonlocal = null;
	}
	return acno;
}

	public int updateAccountMaster(AccountMasterObject am) 
	{
		int sbno=0;

		Connection connection=null;
		Statement statement=null;
		
		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			commonlocal=commonlocalhome.create();
			/*if(statement.executeUpdate("delete from AccountMaster where ac_type='"+am.getAccType()+"'and ac_no='"+am.getAccNo()+"'")!=1)
				throw new SQLException("AccountMaster not deleted");
			if(statement.executeUpdate("delete from SignatureInstruction where ac_type='"+am.getAccType()+"'and ac_no='"+am.getAccNo()+"'")<0)
				throw new SQLException("Signature not deleted");
			if(statement.executeUpdate("delete from JointHolders where ac_type='"+am.getAccType()+"'and ac_no='"+am.getAccNo()+"'")<0)
				throw new SQLException("JointHolders not deleted");
			if(statement.executeUpdate("delete from NomineeMaster where ac_no='"+am.getAccNo()+"' and ac_type='"+am.getAccType()+"'")<0)
				throw new SQLException("Nominee Details Not Deleted");*/

			sbno=am.getAccNo();
		
			/*PreparedStatement ps1=connection.prepareStatement("insert into AccountMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");*/
			PreparedStatement ps1=connection.prepareStatement("update AccountMaster set ch_bk_issue=?,ps_bk_seq=?,int_pbl_date=?,intr_ac_type=?,intr_ac_no=?,ac_status=?,freeze_ind=?,ac_opendate=?,ac_closedate=? where ac_type='"+am.getAccType()+"' and ac_no="+am.getAccNo()+" ");
			
			//ps1.setInt(5,am.getNoOfJointHolders());
			ps1.setString(1,am.getChqBKIssue());
			ps1.setInt(2,am.getPassBkSeq());			
			ps1.setString(3,am.getIntrstPayDate());
			ps1.setString(4,am.getIntrAccType());
			ps1.setInt(5,am.getIntrAccNo());
			ps1.setString(6,am.getAccStatus());	
			ps1.setString(7,am.getFreezeInd());
			ps1.setString(8,am.getAccOpenDate());
			ps1.setString(9,am.getAccCloseDate());
		
			/*NomineeObject nom[]=am.getNomineeDetails();
			if(nom!=null)
			{
				int id=commonlocal.storeNominee(nom,am.getAccType(),am.getAccNo());
				ps1.setInt(14,id);
			}
			else
			    ps1.setInt(14,am.getNom_regno());*/

			/*ps1.setInt(14,am.getNom_regno());//nom regno
*/
			
		if(ps1.executeUpdate()==1)
		{
			if(statement.executeUpdate("insert into AccountMasterLog select * from AccountMaster where ac_type='"+am.getAccType()+"' and ac_no="+am.getAccNo()+"")!=1)
				throw new SQLException("Record not inserted in AccountMasterLog");
			
			/** Added on 26/07/06 
			 * If Freezed then should be inserted into FreezedAccounts table
			 * am.getIdIssueDate()==> to get Trndate from client side i.e,Mainscreen date*/
			if(am.getFreezeInd().equals("T"))
			{				
				if(statement.executeUpdate("insert into FreezedAccounts values('"+am.getAccType()+"','"+am.getAccNo()+"','"+am.getFreezeReason()+"','"+Validations.convertYMD(am.getIdIssueDate())+"',null,'F')")!=1)
					throw new SQLException("Details not stored into FreezedAccounts");
			}
			else
			{
				ResultSet rs=statement.executeQuery("select * from FreezedAccounts where ac_type='"+am.getAccType()+"' and ac_no="+am.getAccNo()+" and to_date is null and frz_inoper_ind='F'");
				if(rs.next())
					if(statement.executeUpdate("update FreezedAccounts set to_date='"+Validations.convertYMD(am.getIdIssueDate())+"' where ac_type='"+am.getAccType()+"' and ac_no="+am.getAccNo()+" and to_date is null and frz_inoper_ind='F'")!=1)
						throw new SQLException("Details not Updated into FreezedAccounts");
			}
			
			/*if(statement.executeUpdate("insert into SignatureInstructionLog select * from SignatureInstruction where ac_type='"+am.getAccType()+"' and ac_no="+am.getAccNo()+"")!=1)
			    throw new SQLException("Record not inserted in SignatureInstructionLog");
			if(statement.executeUpdate("insert into JointHoldersLog select * from JointHolders where ac_type='"+am.getAccType()+"' and ac_no="+am.getAccNo()+"")!=1)
			    throw new SQLException("Record not inserted in JointHoldersLog");*/
					
			/*if(am.getSigObj()!=null)
				commonlocal.storeSignatureDetails(am.getSigObj(),am.getAccNo());
	
			if(am.getJointCid()!=null)
			{
				int addrtype[]=am.getJointAddrType();
				int jcids[]=am.getJointCid();
				for(int i=0;i<jcids.length;i++)
				{	
					if(statement.executeUpdate("insert into JointHolders values('"+am.getAccType()+"','"+am.getAccNo()+"','"+jcids[i]+"','"+addrtype[i]+"')")!=1)
						throw new SQLException("Details not stored in JointHolders");
				}
			}*/
		
		}//ps1 update
		else
			sbno=0;


		//conn.commit();
	return sbno;
	}catch(NomineeNotCreatedException sqlexception)
	{
		ctx.setRollbackOnly();
		sqlexception.printStackTrace();
		try{
	//	conn.rollback();
		ctx.setRollbackOnly();
		sbno=0;
		}catch(Exception exception){exception.printStackTrace();}
		
	}
	catch(SignatureNotInsertedException sqlexception)
	{
		sqlexception.printStackTrace();
		try{
	//	conn.rollback();
		ctx.setRollbackOnly();
		sbno=0;
		}catch(Exception exception){exception.printStackTrace();}
		
	}
	catch(Exception sqlexception)
	{
		sqlexception.printStackTrace();
		try{
			ctx.setRollbackOnly();
		sbno=0;
		}catch(Exception exception){exception.printStackTrace();}
	}
	finally
	{
		try
		{
			connection.close();
		}catch(Exception ex){ex.printStackTrace();}
		commonlocal = null;
	}

return sbno;
}	
/**
 * Function to create New SB/CA Account. Details are stored in AccountMaster,AccountTransaction,
 * JointHolders,SignatureInstruction tables if it is new Account i.e if the account num is zero
 * If the Account No is not zero then old details are deleted and inserted again with
 * the new details for updation purpose.
 * @throws DateFormatException
 */
	public int storeAccountMaster(AccountMasterObject am,int updated) throws CreateException, DateFormatException 
	{
		int sbno=0;
		String trn_date=null;
		Connection connection=null;
		Statement statement=null;
		System.out.println("inside strore Account Tran....................");
		
		System.out.println("inside Bean ........updated value is ............"+updated);
		try
		{
		
			connection=getConnection();
			//connection.setAutoCommit(false);
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			 commonlocal=commonlocalhome.create();
		ResultSet rs=null;
		System.out.println("Before trn date");
		System.out.println("am----------------"+am);
		trn_date=am.uv.getUserDate().substring(0,10);
		System.out.println("trn date is ====="+trn_date);
		if(updated==0)//new Account
		{System.out.println("inside update =====================0");
			rs=statement.executeQuery("select lst_acc_no from Modules where modulecode="+am.getGLRefCode()+"");
			if(rs.next())
				System.out.println("inside rs.next");
				sbno=rs.getInt(1);
			am.setAccNo(sbno+1);
			System.out.println("BEFORE UPDATING DAY CASH");
			if(statement.executeUpdate("update DayCash set ac_no="+am.getAccNo()+" where scroll_no="+am.getRef_No()+" and trn_date='"+trn_date+"'")!=1)
				throw new SQLException("Scroll No not updated");
		}
		if(updated==1 || updated==2)// updatting prev ref_no in day cash
		{
			rs=statement.executeQuery("select ref_no from AccountTransaction where ac_no="+am.getAccNo()+" and ac_type='"+am.getAccountType()+"' ");
			int prev_ref_no=0;
			if(rs.next())
			{
				prev_ref_no=rs.getInt("ref_no");
				System.out.println("prev ref_no="+prev_ref_no);
				if(statement.executeUpdate("update DayCash set trn_seq=0,ac_no=0,attached='F' where  ac_type='"+am.getAccType()+"' and trn_date='"+trn_date+"' and scroll_no="+prev_ref_no+" ")!=1)
					System.out.println("DayCash Not Updated");
			}
		}
		if(updated==1||updated==2)	//	1-->update 	2--->delete
		{
			if(statement.executeUpdate("delete from AccountTransaction where ac_type='"+am.getAccType()+"'and ac_no='"+am.getAccNo()+"'")!=1)
				System.out.println("Account Transaction Not Delete");
				//throw new SQLException("Account Transaction Not Deleted");
			if(statement.executeUpdate("delete from AccountMaster where ac_type='"+am.getAccType()+"'and ac_no='"+am.getAccNo()+"'")!=1)
				System.out.println("Account Not Deleted from AccountMaster");
				//throw new SQLException("Account Not deleted from AccountMaster");
			if(statement.executeUpdate("delete from SignatureInstruction where ac_type='"+am.getAccType()+"'and ac_no='"+am.getAccNo()+"'")<0)
				System.out.println("Signature Instructions  Not Deleted ");
				//throw new SQLException("Signature Details Not Deleted");
			if(statement.executeUpdate("delete from JointHolders where ac_type='"+am.getAccType()+"'and ac_no='"+am.getAccNo()+"'")<0)
				System.out.println("Joint Holders  Not Deleted");
				//throw new SQLException("Joint Holder's Details Not Deleted");
			if(statement.executeUpdate("delete from NomineeMaster where ac_no='"+am.getAccNo()+"' and ac_type='"+am.getAccType()+"'")<0)
				System.out.println("Nominee details  Not Deleted ");
				//throw new SQLException("Nominee Details Not Deleted");
			if(statement.executeUpdate("update DayCash set trn_seq=0,ac_no=0 where ac_no='"+am.getAccNo()+"' and ac_type='"+am.getAccType()+"' and trn_date='"+trn_date+"' and scroll_no="+am.getRef_No()+" ")!=1)
				System.out.println("DayCash Not Updated");
				//throw new SQLException("DayCash not updated");
			
			if(updated==2)
				sbno=am.getAccNo();
			
		
		}

		if(updated==1||updated==0)		//1-->updation   0-->new Account  2--->deletion
		{
			System.out.println("Inside update=0 ===========am.getAccType()=="+am.getAccType());
			PreparedStatement ps1=connection.prepareStatement("insert into AccountMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			ps1.setString(1,am.getAccType());
			ps1.setInt(2,am.getAccNo());
			ps1.setInt(3,am.getCid());
			ps1.setInt(4,am.getMailingAddress());//int
			ps1.setInt(5,am.getNoOfJointHolders());
			ps1.setString(6,am.getChqBKIssue());
			
			ps1.setInt(7,am.getLastTrnSeq());
			
			ps1.setString(8,am.getLastTrnDate());//int
			ps1.setInt(9,am.getPassBkSeq());
			ps1.setInt(10,am.getLedgerSeq());
			ps1.setString(11,Validations.addDays(trn_date,-1));
			ps1.setString(12,am.getIntrAccType());
			ps1.setInt(13,am.getIntrAccNo());
		
			NomineeObject nom[]=am.getNomineeDetails();
			if(nom!=null)
			{	System.out.println("Before Entering Nominee Details");
				int id=commonlocal.storeNominee(nom,am.getAccType(),am.getAccNo(),am.uv.getUserDate());
				ps1.setInt(14,id);

			}
			else	 		
				ps1.setInt(14,0);//nom regno

			ps1.setString(15,am.getAccStatus());
			ps1.setString(16,am.getFreezeInd());
			
			ps1.setString(17,am.getAccOpenDate());
			ps1.setString(18,null);
			ps1.setInt(19,am.getLastLine());

			ps1.setString(20,am.uv.getUserTml());
			ps1.setString(21,am.uv.getUserId());
			ps1.setString(22,am.uv.getUserDate());
			
			ps1.setString(23,null);
			ps1.setString(24,null);
			ps1.setString(25,null);


		if(ps1.executeUpdate()==1)
		{
			if(updated==1)
				statement.executeUpdate("delete from AccountMasterLog where ac_type='"+am.getAccType()+"' and ac_no="+am.getAccNo()+"");

			statement.executeUpdate("insert into AccountMasterLog select * from AccountMaster where ac_type='"+am.getAccType()+"' and ac_no="+am.getAccNo()+"");
			if(am.getSigObj()!=null)
				commonlocal.storeSignatureDetails(am.getSigObj(),am.getAccNo());

			if(updated==0 )//new Account
			{
				System.out.println("UPDATE IS 0==updating Modules and DayCash");
				System.out.println("scroll="+am.getRef_No()+" "+trn_date);
				if(statement.executeUpdate("update Modules set lst_acc_no="+am.getAccNo()+" where modulecode="+am.getGLRefCode()+"")!=1)
					throw new SQLException("Modules not Updated");
				sbno=sbno+1;
				statement.executeUpdate("update DayCash set trn_seq=1,ac_no="+am.getAccNo()+" where scroll_no="+am.getRef_No()+" and trn_date='"+trn_date+"' and ac_type='"+am.getAccType()+"' ");
					//throw new SQLException("Scroll No not updated");
			}
			if(updated==1)//update
			{
				System.out.println("scroll="+am.getRef_No());
				sbno=am.getAccNo();
				if(statement.executeUpdate("update DayCash set trn_seq=1,ac_no="+am.getAccNo()+" where scroll_no="+am.getRef_No()+" and trn_date='"+trn_date+"'")!=1)
						throw new SQLException("Scroll No not updated");
			}
			if(updated==2 )//Delete
			{
				System.out.println("Deleting from Modules scroll="+am.getRef_No()+" "+trn_date);
				int acnum=am.getAccNo()-1;
				if(statement.executeUpdate("update Modules set lst_acc_no="+acnum+" where modulecode="+am.getGLRefCode()+"")!=1)
					throw new SQLException("Modules not Updated");
					//throw new SQLException("Scroll No not updated");
			}
			if(am.getJointCid()!=null)
			{
				int addrtype[]=am.getJointAddrType();
				int jcids[]=am.getJointCid();
				for(int i=0;i<jcids.length;i++)
				{	
					if(statement.executeUpdate("insert into JointHolders values('"+am.getAccType()+"','"+am.getAccNo()+"','"+jcids[i]+"','"+addrtype[i]+"')")!=1)
						throw new SQLException("Joint Holder not inserted");
				}
			}
			
			rs=statement.executeQuery("select de_tml,name from DayCash where scroll_no='"+am.getRef_No()+"' and trn_date='"+trn_date+"'");
			//String date=getSysDateTime();
			if(rs.next())
				if(statement.executeUpdate("insert into AccountTransaction values('"+am.getAccType()+"',"+am.getAccNo()+",'"+trn_date+"','R',1,'"+am.getTransAmount()+"','C','"+rs.getString(1)+"','C',0,null,null,"+am.getRef_No()+",'"+rs.getString(2)+"','"+am.getTransAmount()+"',0,'"+am.uv.getUserTml()+"','"+am.uv.getUserId()+"','"+am.uv.getUserDate()+"',null,null,null)")!=1)
					throw new SQLException("Account Transaction not inserted");
			rs.close();
			
			
		}//ps1 update
		else
			sbno=0;

	}

	return sbno;
	}catch(NomineeNotCreatedException sqlexception)
	{
		ctx.setRollbackOnly();
		sqlexception.printStackTrace();
		try{
		connection.rollback();
		ctx.setRollbackOnly();
		sbno=0;
		}catch(Exception exception){exception.printStackTrace();}
		
	}
	catch(SignatureNotInsertedException sqlexception)
	{
		ctx.setRollbackOnly();
		sqlexception.printStackTrace();
		try{
		//connection.rollback();	
		ctx.setRollbackOnly();
		sbno=0;
		}catch(Exception exception){exception.printStackTrace();}
		
	}
		catch(SQLException sqlexception)
	{
		ctx.setRollbackOnly();	
		sqlexception.printStackTrace();
		try{
		//connection.rollback();	
		ctx.setRollbackOnly();
		sbno=0;
		}catch(Exception exception){exception.printStackTrace();}
	}
	catch(Exception exception)
	{
		ctx.setRollbackOnly();
		exception.printStackTrace();
		try{
			//connection.rollback();
			ctx.setRollbackOnly();
			sbno=0;
		}catch(Exception exception1){exception1.printStackTrace();}
	}

	finally
	{
		try
		{
			connection.close();
		}catch(Exception ex){ex.printStackTrace();}
		commonlocal = null;
	}

return sbno;
}

/**
 * Function to retrieve the details about the SB/CA Account for the  given Account No
 * and AccountType
 */	
public AccountMasterObject getAccountMaster(int accno,String type) throws AccountNotFoundException,CreateException 
{
	AccountMasterObject am=null;

	Connection connection=null;
	Statement statement=null;

	try
	{
		connection=getConnection();
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs=null;
		rs=statement.executeQuery("select * from AccountMaster where ac_no="+accno+" and ac_type='"+type+"'");
		commonlocal=commonlocalhome.create();
		if(rs.next())
		{
			am=new AccountMasterObject();

			if(rs.getString("ve_user")==null) //verified
				am.setVerified("F");
			else
				am.setVerified("T");

			am.setAccNo(rs.getInt("ac_no"));
			am.setCid(rs.getInt("cid"));
			am.setAccType(rs.getString("ac_type"));
			am.setMailingAddress(rs.getInt("addr_type"));
			am.setAccStatus(rs.getString("ac_status"));
			am.setFreezeInd(rs.getString("freeze_ind"));
			am.uv.setUserId(rs.getString("de_user"));
			am.setNoOfJointHolders(rs.getInt("no_jt_hldr"));
			am.setChqBKIssue(rs.getString("ch_bk_issue"));
			am.setTransSource(rs.getString("de_tml"));

			am.setIntrAccNo(rs.getInt("intr_ac_no"));
			am.setIntrAccType(rs.getString("intr_ac_type"));
			am.setNom_regno(rs.getInt("nom_no"));
			
			am.setLastTrnSeq(rs.getInt("last_tr_seq"));
			am.setLastTrnDate(rs.getString("last_tr_date"));//int
			am.setPassBkSeq(rs.getInt("ps_bk_seq"));
			am.setLedgerSeq(rs.getInt("ledger_seq"));
			am.setIntrstPayDate(rs.getString("int_pbl_date"));
			am.setAccStatus(rs.getString("ac_status"));
			am.setAccOpenDate(rs.getString("ac_opendate"));
			am.setAccCloseDate(rs.getString("ac_closedate"));
			am.setLastLine(rs.getInt("last_line"));

			//ResultSet rs3=statement.executeQuery("select * from AccountTransaction where ac_type='"+am.getAccType()+"' and ac_no='"+am.getAccNo()+"' and trn_date='"+getSysDate()+"'");
			ResultSet rs3=statement.executeQuery("select * from AccountTransaction where ac_type='"+am.getAccType()+"' and ac_no='"+am.getAccNo()+"' order by trn_seq desc limit 1");
			if(rs3.next())
			{
				am.setTransAmount(rs3.getDouble ("trn_amt"));
				am.setRef_No(rs3.getInt("ref_no"));
				am.setAccName(rs3.getString("payee_nm"));
			}

			rs3.close();
			rs3 = statement.executeQuery("select cid,addr_type from JointHolders where ac_type='"+am.getAccType()+"' and ac_no='"+am.getAccNo()+"'");
			int j=0;
			int addrtype[]=null;
			int cids[]=null;
			if(rs3.next())
			{
				rs3.last();
				addrtype=new int[rs3.getRow()];
				cids=new int[rs3.getRow()];
				rs3.beforeFirst();

				
			}
		
			while(rs3.next())
			{
				addrtype[j]=rs3.getInt(2);
				cids[j++]=rs3.getInt(1);
			}
			
			am.setJointAddrType(addrtype);
			am.setJointCid(cids);
			 
			NomineeObject[] obj=commonlocal.getNominee(am.getNom_regno());
			if(obj!=null)
			{
			for(int i=0;i<obj.length;i++)
			{
				System.out.println("1....."+obj[i].getCustomerId());
				System.out.println("2......"+obj[i].getNomineeDOB());
				System.out.println("3......."+obj[i].getNomineeAddress());
				System.out.println("4........"+obj[i].getRegNo());
			}
			}
		
			am.setNomineeDetails(commonlocal.getNominee(am.getNom_regno()));
			am.setSigObj(commonlocal.getSignatureDetails(am.getAccNo(),am.getAccType()));
			
			if(am.getFreezeInd().equals("T"))
			{
				ResultSet rs4=statement.executeQuery("select * from FreezedAccounts where ac_type='"+am.getAccType()+"' and ac_no='"+am.getAccNo()+"' and to_date is null and frz_inoper_ind='F'");
				if(rs4.next())
					am.setFreezeReason(rs4.getString("reason"));
			}
		
		}
//		else throw new AccountNotFoundException();

		return am;
	}/*catch(AccountNotFoundException ex)
	{
		throw new AccountNotFoundException();
	}*/
	catch(SQLException sqlexception)
	{
		sqlexception.printStackTrace();
	}
	
	finally
	{
		try
		{
			connection.close();
		}catch(SQLException ex){ex.printStackTrace();}
		commonlocal = null;
	}
	
	return am;
}


	public int[] getCid(int accno,String type) 
	{
		int cid[]=null;
		int norows=0;
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			ResultSet rs=statement.executeQuery("select cid from JointHolders where ac_no='"+accno+"' and ac_type='"+type+"'");
			while(rs.next())
			norows++;

			cid=new int[norows];
			 rs=statement.executeQuery("select cid from JointHolders where ac_no='"+accno+"' and ac_type='"+type+"'");
			for(int i=0;i<norows;i++)
			{
			if(!rs.next())
				break;
			cid[i]=rs.getInt(1);
			}

			return cid;
		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception ex){ex.printStackTrace();}
		}


		return cid;
	}
/**
 * Print -- all the recrods which are not yet printed are printed
 * RePrint -- all the recrods which are to be re printed are printed
 * last line(pass book line)printed is maintained in the last_line field in AccountMaster table
 * ps_bk_seq -- last trn seq for Pass Book
 * last_tr_seq -- last trn seq for ParseSheet

 */

public AccountTransObject[] PrintBook(String acty,int acno,String frdate,String todate,int ps_bk_seq,int a) 
{	    
	int norows=0;
	AccountTransObject am[]=null;
	Connection connection=null;
	Statement statement=null,stat=null;

	try
	{
		connection=getConnection();
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		stat=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs=null;
		if(a==1)//new Book
		{
			if(acty.startsWith("1007") ||acty.startsWith("1017") ||acty.startsWith("1018") || acty.startsWith("1002"))
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.cd_ind,at.cl_bal,at.de_user,at.ve_user,at.trn_narr,at.ref_no,at.trn_seq,at.trn_mode,at.chq_dd_no,am.ps_bk_seq,am.last_line from AccountTransaction at,AccountMaster am where at.ac_type='"+acty+"' and at.ac_no="+acno+" and at.ac_type=am.ac_type and at.ac_no=am.ac_no and at.trn_seq > am.ps_bk_seq");
			else
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.cd_ind,at.cl_bal,at.de_user,at.ve_user,at.trn_narr,at.ref_no,at.trn_seq,at.trn_mode,at.chq_dd_no,am.ps_bk_seq,am.last_line from ODCCTransaction at,ODCCMaster am where at.ac_type='"+acty+"' and at.ac_no="+acno+" and at.ac_type=am.ac_type and at.ac_no=am.ac_no and at.trn_seq > am.ps_bk_seq");
			if(rs.next())
			{
				rs.last();
			 	norows=rs.getRow();
			 	System.out.println("In fcBean norows==="+norows);
			 	am = new AccountTransObject[norows];
			 	rs.beforeFirst();
			}
			
		}
		if(a==2)//Reprint
		{
			/*if(acty.startsWith("1007") ||acty.startsWith("1017") ||acty.startsWith("1018") || acty.startsWith("1002"))
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.cd_ind,at.cl_bal,at.de_user,at.ve_user,at.trn_narr,at.ref_no,at.trn_seq,at.trn_mode,at.chq_dd_no from AccountTransaction at where at.ac_type='"+acty+"' and at.ac_no="+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1))>='"+Validations.convertYMD(frdate)+"'");
			else
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.cd_ind,at.cl_bal,at.de_user,at.ve_user,at.trn_narr,at.ref_no,at.trn_seq,at.trn_mode,at.chq_dd_no from ODCCTransaction at where at.ac_type='"+acty+"' and at.ac_no="+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1))>='"+Validations.convertYMD(frdate)+"'");*/
			
			if(acty.startsWith("1007") ||acty.startsWith("1017") ||acty.startsWith("1018") || acty.startsWith("1002"))
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.cd_ind,at.cl_bal,at.de_user,at.ve_user,at.trn_narr,at.ref_no,at.trn_seq,at.trn_mode,at.chq_dd_no from AccountTransaction at where at.ac_type='"+acty+"' and at.ac_no="+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1)) between '"+Validations.convertYMD(frdate)+"' and '"+Validations.convertYMD(todate)+"' order by at.trn_seq");
			else
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.cd_ind,at.cl_bal,at.de_user,at.ve_user,at.trn_narr,at.ref_no,at.trn_seq,at.trn_mode,at.chq_dd_no from ODCCTransaction at where at.ac_type='"+acty+"' and at.ac_no="+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1))between '"+Validations.convertYMD(frdate)+"' and '"+Validations.convertYMD(todate)+"' order by at.trn_seq");
			
			if(rs.next())
			{
				rs.last();
			 	norows=rs.getRow();
			 	am = new AccountTransObject[norows];
			 	rs.beforeFirst();
			}

		}
		if(norows>0)
		{
			int t=0,k;
			int i=0;
			while(rs.next())
			{
				System.out.println("1...");
				am[i]= new AccountTransObject();
				am[i].setTransDate(rs.getString("at.trn_date"));
				am[i].setTransAmount(rs.getDouble("at.trn_amt"));
				am[i].setCdInd(rs.getString("at.cd_ind"));
				//am[i].setCloseBal(Float.parseFloat(rs.getString(4)));
				am[i].setCloseBal(rs.getDouble("at.cl_bal"));				
				am[i].uv.setUserId(rs.getString("at.de_user"));
				am[i].uv.setVerId(rs.getString("at.ve_user"));
				
				/** Added to get narration */
				if(rs.getString("at.trn_mode").equals("T"))
	 			{
	 				//System.out.println("STRING="+rs.getString("at.trn_narr").toString());
	 				if(rs.getString("at.trn_narr")!=null)// || !rs.getString("at.trn_narr").toString().equals(""))
	 				{
	 					String narr="";
	 					StringTokenizer st=new StringTokenizer(rs.getString("at.trn_narr")," ");
	 					ResultSet rs_mod=stat.executeQuery("select moduleabbr from Modules where modulecode='"+st.nextToken()+"'");	 					
	 					if(rs_mod.next())
	 						narr=narr+rs_mod.getString(1)+" "+st.nextToken();
	 					else
	 						narr=narr+rs.getString("at.trn_narr");
	 					am[i].setTransNarr(narr);
	 				}
	 				else
	 					am[i].setTransNarr(rs.getString("at.trn_narr"));	 					
	 			}
	 			else
	 				am[i].setTransNarr(rs.getString("at.trn_narr"));
				//am[i].setTransNarr(rs.getString(7));
				
				am[i].setRef_No(rs.getInt("at.ref_no"));				
				am[i].setTransSeqNo(rs.getInt("at.trn_seq"));
				am[i].setTransMode(rs.getString("at.trn_mode"));
				am[i].setChqDDNo(rs.getInt("at.chq_dd_no"));
				if(a==1)
				{	 
					t=rs.getInt("am.ps_bk_seq");
					am[i].setPassBkSeq(rs.getInt("am.last_line"));
				}
				i++;
			}
			if(a==1)
			{
				rs.first();
			
				int j = rs.getInt("am.ps_bk_seq")+norows;
				k = (t+norows)%getMaxRows(acty);
				if(acty.startsWith("1007") ||acty.startsWith("1017") || acty.startsWith("1018") ||acty.startsWith("1002"))	
					statement.executeUpdate("update AccountMaster set ps_bk_seq="+j+",last_line="+k+" where ac_type='"+acty+"' and ac_no="+acno+"");
				else
					statement.executeUpdate("update ODCCMaster set ps_bk_seq="+j+",last_line="+k+" where ac_type='"+acty+"' and ac_no="+acno+"");
			}
			if(a==2)
			{

				rs.last();
			
				int j = rs.getInt("at.trn_seq");
				k = (ps_bk_seq+norows)%getMaxRows(acty);
				if(acty.startsWith("1007") ||acty.startsWith("1017") || acty.startsWith("1018") ||acty.startsWith("1002"))
					statement.executeUpdate("update AccountMaster set ps_bk_seq="+j+",last_line="+k+" where ac_type='"+acty+"' and ac_no="+acno+"");
				else
					statement.executeUpdate("update ODCCMaster set ps_bk_seq="+j+",last_line="+k+" where ac_type='"+acty+"' and ac_no="+acno+"");
			}
			

			rs.close();
		}
			
						
	}catch(Exception sqlexception){
		ctx.setRollbackOnly();
		sqlexception.printStackTrace();
		}
	finally
	{
		try
		{
			connection.close();
		}catch(Exception ex){ex.printStackTrace();}
	}

	return am;
}

	public int getMaxRows(String modulecode)
	{
		int m = 0;
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=statement.executeQuery("select pass_bk_lines from Modules where modulecode="+modulecode+"");
			rs.next();
			m = rs.getInt(1);
			rs.close();
			
		}catch(Exception sqlexception){sqlexception.printStackTrace();}
		finally{
			try{
				connection.close();
			}catch(Exception exception){exception.printStackTrace();}
			
		}

		return m;
	}
public PayOrderObject RetrievePrintInfo(String date,int pono,int flag)  
{
		PayOrderObject po = null;
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

	 		ResultSet rs = null;
	 		po = new PayOrderObject();
	 		if(flag==1)
	 			rs = statement.executeQuery("select payord_no,payord_dt,payee_nm,trn_amt from PayOrder where payord_no = "+pono+" and payord_dt = '"+date+"' and po_prt_ind = 0 and po_chq_no!=0");
	 		else if(flag==2)
	 			rs = statement.executeQuery("select payord_no,payord_dt,payee_nm,trn_amt from PayOrder where payord_no = "+pono+" and payord_dt = '"+date+"'  and po_chq_no!=0 ");
	 		if(!rs.next())
	 			po.setPayOrderNo(0);
	 		else
	 		{	
	 			po.setPayOrderNo(rs.getInt(1));
	 			po.setPODate(rs.getString(2));
	 			po.setPOPayee(rs.getString(3));
	 			po.setTransAmount(rs.getDouble (4));	 
	 			
	 		}
	 		if(flag==1)
	 			statement.executeUpdate("update PayOrder set po_prt_ind = 1 where payord_no = "+pono+" and payord_dt = '"+date+"' and po_prt_ind = 0  and po_chq_no!=0" );
		}catch(Exception sqlexception){
			ctx.setRollbackOnly();
			sqlexception.printStackTrace();
			}
		finally{
			try{
				connection.close();
			}catch(Exception exception){
				ctx.setRollbackOnly();
				exception.printStackTrace();
				}
			
		}

	 	return po;   		
   	}	
/*
   	 	For All Case
   	 		Retreving print info if po_prt_ind =0 
   	 		and after printing making po_prt_ind=1;
   	 	
  */

   	public PayOrderObject[] RetrievePrintInfo(int flag)  
   	{

		PayOrderObject[] po = null;
		Connection connection=null;
		Statement statement=null;

		try
		{
			connection=getConnection();
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

	 		ResultSet rs = null;
	 	
	 		rs = statement.executeQuery("select payord_no,payord_dt,payee_nm,trn_amt from PayOrder where po_prt_ind = 0 and po_chq_no!=0");
	 		if(rs.next())
	 		{
	 			rs.last();
	 			po = new PayOrderObject[rs.getRow()];
	 			rs.beforeFirst();
	 		}

	 		int i=0;
	 		
	 		while(rs.next())
	 		{
	 			po[i] = new PayOrderObject();	
	 			po[i].setPayOrderNo(rs.getInt(1));
	 			po[i].setPODate(rs.getString(2));
	 			po[i].setPOPayee(rs.getString(3));
	 			po[i].setTransAmount(rs.getDouble (4));
	 			i++;
	 		}
	 		rs.close();
	 		if(flag==2)
	 			statement.executeUpdate("update PayOrder set po_prt_ind = 1 where po_prt_ind = 0  and po_chq_no!=0");
	 		
		}catch(Exception sqlexception){
			ctx.setRollbackOnly();
			sqlexception.printStackTrace();
			}
		finally{
			try{
				connection.close();
			}catch(Exception exception){exception.printStackTrace();}
			
		}

	 	return po;
	 }
   	
/*
 	For POCash
	Retriving info for particular date If po_csh_ind =1 	
 	For POUnCash
	Retriving info for particular date If po_csh_ind =0 	
 */
   	
public PayOrderObject[] RetrieveCashUncash(String fromdate,String todate,int num,String query)
{   		
	PayOrderObject[] po = null;
	Connection connection=null;
	Statement statement=null,stmt=null;
	
	try
	{
		connection=getConnection();
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
		ResultSet rs = null;
				
		if(query!=null && !query.equals(""))
		{
			System.out.println(query);
		 	if(num == 1)
		 		rs = statement.executeQuery("select payord_no,payord_dt,po_chq_no,payee_nm,trn_amt from PayOrder where concat(right(payord_dt,4),'-',mid(payord_dt,locate('/',payord_dt)+1, (locate('/',payord_dt,4)-locate('/',payord_dt)-1)),'-',left(payord_dt,locate('/',payord_dt)-1)) BETWEEN '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"'  and po_csh_ind = 1 and ve_tml is not null and ("+query+")");	 			
		 	else
		 		//rs = statement.executeQuery("select payord_no,payord_dt,po_chq_no,payee_nm,trn_amt from PayOrder where concat(right(payord_dt,4),'-',mid(payord_dt,locate('/',payord_dt)+1, (locate('/',payord_dt,4)-locate('/',payord_dt)-1)),'-',left(payord_dt,locate('/',payord_dt)-1)) BETWEEN '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"' and po_csh_ind = 0 and ve_tml is not null and ("+query+")");
		 		rs = statement.executeQuery("select payord_no,payord_dt,po_chq_no,payee_nm,trn_amt from PayOrder where concat(right(payord_dt,4),'-',mid(payord_dt,locate('/',payord_dt)+1, (locate('/',payord_dt,4)-locate('/',payord_dt)-1)),'-',left(payord_dt,locate('/',payord_dt)-1)) BETWEEN '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"' and ve_tml is not null and po_cancel='F' and ((concat(right(po_csh_dt,4),'-',mid(po_csh_dt,locate('/',po_csh_dt)+1, (locate('/',po_csh_dt,4)-locate('/',po_csh_dt)-1)),'-',left(po_csh_dt,locate('/',po_csh_dt)-1)) not BETWEEN '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"' and po_csh_dt is not null and po_csh_ind=1) ||(po_csh_ind=0)) and ("+query+")");	/** Changed on 11/05/06 */		
		}
		else
		{	
		 	if(num == 1)
		 		rs = statement.executeQuery("select payord_no,payord_dt,po_chq_no,payee_nm,trn_amt from PayOrder where concat(right(payord_dt,4),'-',mid(payord_dt,locate('/',payord_dt)+1, (locate('/',payord_dt,4)-locate('/',payord_dt)-1)),'-',left(payord_dt,locate('/',payord_dt)-1)) BETWEEN '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"' and po_csh_ind = 1 and ve_tml is not null");	 			
		 	else
		 	{
		 		//rs = statement.executeQuery("select payord_no,payord_dt,po_chq_no,payee_nm,trn_amt from PayOrder where concat(right(payord_dt,4),'-',mid(payord_dt,locate('/',payord_dt)+1, (locate('/',payord_dt,4)-locate('/',payord_dt)-1)),'-',left(payord_dt,locate('/',payord_dt)-1)) BETWEEN '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"' and po_csh_ind = 0 and ve_tml is not null");
		 		/** Changed on 11/05/06 */
		 		rs = statement.executeQuery("select payord_no,payord_dt,po_chq_no,payee_nm,trn_amt from PayOrder where concat(right(payord_dt,4),'-',mid(payord_dt,locate('/',payord_dt)+1, (locate('/',payord_dt,4)-locate('/',payord_dt)-1)),'-',left(payord_dt,locate('/',payord_dt)-1)) BETWEEN '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"' and ve_tml is not null and po_cancel='F' and ((concat(right(po_csh_dt,4),'-',mid(po_csh_dt,locate('/',po_csh_dt)+1, (locate('/',po_csh_dt,4)-locate('/',po_csh_dt)-1)),'-',left(po_csh_dt,locate('/',po_csh_dt)-1)) not BETWEEN '"+Validations.convertYMD(fromdate)+"' and '"+Validations.convertYMD(todate)+"' and po_csh_dt is not null and po_csh_ind=1) ||(po_csh_ind=0))");		
		 	}
		}
	 	
	 	if(rs.next())
	 	{	
	 		rs.last();
	 		po = new PayOrderObject[rs.getRow()];
	 		rs.beforeFirst();
	 	}
	 	int i=0;
	 	while(rs.next())
		{
			po[i] = new PayOrderObject();	
			po[i].setPayOrderNo(rs.getInt(1));
			po[i].setPODate(rs.getString(2));
			po[i].setPOChqNo(rs.getInt(3));
			po[i].setPOPayee(rs.getString(4)!=null?rs.getString(4):"");
			po[i].setTransAmount(rs.getDouble (5));						
			i++;			
		}	 	
	}catch(Exception sqlexception)
	{
		sqlexception.printStackTrace();
		try
		{
			ctx.setRollbackOnly();			
		}catch(Exception exception){exception.printStackTrace();}
	}
	finally
	{
		try
		{			
			connection.close();
		}catch(Exception exception){exception.printStackTrace();}		
	}
	return po;	
}
	 
/**  
	For PO Created 
	Retriveing PODetailes for Particular Date from PayOrderMake Table... 
	For PO Consolidated..
	Retrieving info from POLink and POrder Table for Dates...
*/
public PayOrderObject[] RetrievePOMadeInfo(String date,int no,String query) 
{
	PayOrderObject[] po = null;		
	Connection connection=null;
	Statement statement=null;

	try
	{
		connection=getConnection();
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		System.out.println("Query is"+query);
		System.out.println("date is======="+date+"==no is==="+no);
		if(no==1)
		{
//			rs = statement.executeQuery("select po_type,po_sno,po_purchaser_name,moduleabbr,po_acno,po_glcd,po_amt,comm_amt from PayOrderMake left join Modules on (po_acty=modulecode) where po_date = '"+date+"' and ("+query+")");				
				if(query!=null)
					rs = statement.executeQuery("select distinct po_sno,po_type,po_purchaser_name,po_acno,po_gltype,po_glcd,po_amt,comm_amt,module1.moduleabbr,module2.moduleabbr from PayOrderMake left join Modules module1 on (po_acty=module1.modulecode) left join Modules module2 on (po_gltype=module2.modulecode) where concat(right(po_date,4),'-',mid(po_date,locate('/',po_date)+1, (locate('/',po_date,4)-locate('/',po_date)-1)),'-',left(po_date,locate('/',po_date)-1)) BETWEEN "+date+" and ("+query+")");
				else
					rs = statement.executeQuery("select distinct po_sno,po_type,po_purchaser_name,po_acno,po_gltype,po_glcd,po_amt,comm_amt,module1.moduleabbr,module2.moduleabbr from PayOrderMake left join Modules module1 on (po_acty=module1.modulecode) left join Modules module2 on (po_gltype=module2.moduleabbr) where concat(right(po_date,4),'-',mid(po_date,locate('/',po_date)+1, (locate('/',po_date,4)-locate('/',po_date)-1)),'-',left(po_date,locate('/',po_date)-1)) BETWEEN "+date+"");
					//rs = statement.executeQuery("select po_type,po_sno,po_purchaser_name,po_acno,po_gltype,po_glcd,po_amt,comm_amt,module1.moduleabbr,module2.moduleabbr from PayOrderMake left join Modules module1 on (po_acty=module1.modulecode) left join Modules module2 on (po_gltype=module2.modulecode) where concat(right(po_date,4),'-',mid(po_date,locate('/',po_date)+1, (locate('/',po_date,4)-locate('/',po_date)-1)),'-',left(po_date,locate('/',po_date)-1)) BETWEEN "+date+"");
				
			if(rs.next())
			{	
				rs.last();
				po = new PayOrderObject[rs.getRow()];
				rs.beforeFirst();
			}

		int i=0;	
		while(rs.next())
		{

			po[i] = new PayOrderObject();	
			po[i].setPOType(rs.getString("po_type"));
			
			po[i].setPOAccNo(rs.getInt("po_acno")!=0?rs.getInt("po_acno"):0);
			po[i].setPOSerialNo(rs.getInt("po_sno"));
			po[i].setPOPayee(rs.getString("po_purchaser_name")!=null?rs.getString("po_purchaser_name"):"");
			po[i].setPOAccType(rs.getString("module1.moduleabbr")!=null?rs.getString("module1.moduleabbr"):"");
			po[i].setPOGlType(rs.getString("module2.moduleabbr")!=null?rs.getString("module2.moduleabbr"):"");
			//po[i].setPOGlType(rs.getString("po_gltype"));
			po[i].setPOGlCode(rs.getInt("po_glcd"));
			po[i].setPOAmount(rs.getDouble("po_amt"));	
			po[i].setCommissionAmount(rs.getDouble ("comm_amt"));
			i++;
		}
	 			
		}
		else
		{
			if(query!=null)
				rs = statement.executeQuery("select po.payord_no,po.po_chq_no,po.payee_nm,po.trn_amt,pl.ref_type, pl.ref_sno from PayOrder po,POLink pl where concat(right(pl.payord_dt,4),'-',mid(pl.payord_dt,locate('/',pl.payord_dt)+1, (locate('/',pl.payord_dt,4)-locate('/',pl.payord_dt)-1)),'-',left(pl.payord_dt,locate('/',pl.payord_dt)-1)) BETWEEN "+date+" and po.payord_no = pl.payord_no and ("+query+") order by po.payord_no,pl.ref_sno ");
			else
				rs = statement.executeQuery("select po.payord_no,po.po_chq_no,po.payee_nm,po.trn_amt,pl.ref_type, pl.ref_sno from PayOrder po,POLink pl where concat(right(pl.payord_dt,4),'-',mid(pl.payord_dt,locate('/',pl.payord_dt)+1, (locate('/',pl.payord_dt,4)-locate('/',pl.payord_dt)-1)),'-',left(pl.payord_dt,locate('/',pl.payord_dt)-1)) BETWEEN "+date+" and po.payord_no = pl.payord_no order by po.payord_no,pl.ref_sno");
			if(rs.next())
			{	
				rs.last();
				po = new PayOrderObject[rs.getRow()];
				rs.beforeFirst();
			}
			int i=0;	
			while(rs.next())
			{
				po[i] = new PayOrderObject();	
				po[i].setPayOrderNo(rs.getInt(1));
				po[i].setPOChqNo(rs.getInt(2));
				po[i].setPOPayee(rs.getString(3)!=null?rs.getString(3):"");
				po[i].setTransAmount(rs.getDouble (4));
				po[i].setPOType(rs.getString(5));
				po[i].setPOSerialNo(rs.getInt(6));
				i++;
			}
		}
	 			
	}catch(Exception sqlexception){sqlexception.printStackTrace();}
	finally{
		try{
			connection.close();
		}catch(Exception exception){exception.printStackTrace();}
		
	}
	System.out.println("At the end returning po "+po);
	return po;	
}

/*public PayOrderObject[] RetrievePOMadeInfo(String date,int no,String query) 
{
	PayOrderObject[] po = null;		
	Connection connection=null;
	Statement statement=null;

	try
	{
		connection=getConnection();
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		System.out.println(query);
		if(no==1)
		{
//			rs = statement.executeQuery("select po_type,po_sno,po_purchaser_name,moduleabbr,po_acno,po_glcd,po_amt,comm_amt from PayOrderMake left join Modules on (po_acty=modulecode) where po_date = '"+date+"' and ("+query+")");				
				if(query!=null)
					rs = statement.executeQuery("select distinct po_sno,po_type,po_purchaser_name,po_acno,po_gltype,po_glcd,po_amt,comm_amt,module1.moduleabbr,module2.moduleabbr from PayOrderMake left join Modules module1 on (po_acty=module1.modulecode) left join Modules module2 on (po_gltype=module2.modulecode) where concat(right(po_date,4),'-',mid(po_date,locate('/',po_date)+1, (locate('/',po_date,4)-locate('/',po_date)-1)),'-',left(po_date,locate('/',po_date)-1)) BETWEEN "+date+" and ("+query+")");
				else
					rs = statement.executeQuery("select distinct po_sno,po_type,po_purchaser_name,po_acno,po_gltype,po_glcd,po_amt,comm_amt,module1.moduleabbr,module2.moduleabbr from PayOrderMake left join Modules module1 on (po_acty=module1.modulecode) left join Modules module2 on (po_gltype=module2.moduleabbr) where concat(right(po_date,4),'-',mid(po_date,locate('/',po_date)+1, (locate('/',po_date,4)-locate('/',po_date)-1)),'-',left(po_date,locate('/',po_date)-1)) BETWEEN "+date+"");
					//rs = statement.executeQuery("select po_type,po_sno,po_purchaser_name,po_acno,po_gltype,po_glcd,po_amt,comm_amt,module1.moduleabbr,module2.moduleabbr from PayOrderMake left join Modules module1 on (po_acty=module1.modulecode) left join Modules module2 on (po_gltype=module2.modulecode) where concat(right(po_date,4),'-',mid(po_date,locate('/',po_date)+1, (locate('/',po_date,4)-locate('/',po_date)-1)),'-',left(po_date,locate('/',po_date)-1)) BETWEEN "+date+"");
				
			if(rs.next())
			{	
				rs.last();
				po = new PayOrderObject[rs.getRow()];
				rs.beforeFirst();
			}

		int i=0;	
		while(rs.next())
		{

			po[i] = new PayOrderObject();	
			po[i].setPOType(rs.getString("po_type"));
			
			po[i].setPOAccNo(rs.getInt("po_acno")!=0?rs.getInt("po_acno"):0);
			po[i].setPOSerialNo(rs.getInt("po_sno"));
			po[i].setPOPayee(rs.getString("po_purchaser_name")!=null?rs.getString("po_purchaser_name"):"");
			po[i].setPOAccType(rs.getString("module1.moduleabbr")!=null?rs.getString("module1.moduleabbr"):"");
			po[i].setPOGlType(rs.getString("module2.moduleabbr")!=null?rs.getString("module2.moduleabbr"):"");
			//po[i].setPOGlType(rs.getString("po_gltype"));
			po[i].setPOGlCode(rs.getInt("po_glcd"));
			po[i].setPOAmount(rs.getDouble("po_amt"));	
			po[i].setCommissionAmount(rs.getDouble ("comm_amt"));
			i++;
		}
	 			
		}
		else
		{
			if(query!=null)
				rs = statement.executeQuery("select po.payord_no,po.po_chq_no,po.payee_nm,po.trn_amt,pl.ref_type, pl.ref_sno from PayOrder po,POLink pl where concat(right(pl.payord_dt,4),'-',mid(pl.payord_dt,locate('/',pl.payord_dt)+1, (locate('/',pl.payord_dt,4)-locate('/',pl.payord_dt)-1)),'-',left(pl.payord_dt,locate('/',pl.payord_dt)-1)) BETWEEN "+date+" and po.payord_no = pl.payord_no and ("+query+") order by po.payord_no,pl.ref_sno ");
			else
				rs = statement.executeQuery("select po.payord_no,po.po_chq_no,po.payee_nm,po.trn_amt,pl.ref_type, pl.ref_sno from PayOrder po,POLink pl where concat(right(pl.payord_dt,4),'-',mid(pl.payord_dt,locate('/',pl.payord_dt)+1, (locate('/',pl.payord_dt,4)-locate('/',pl.payord_dt)-1)),'-',left(pl.payord_dt,locate('/',pl.payord_dt)-1)) BETWEEN "+date+" and po.payord_no = pl.payord_no order by po.payord_no,pl.ref_sno");
			if(rs.next())
			{	
				rs.last();
				po = new PayOrderObject[rs.getRow()];
				rs.beforeFirst();
			}
			int i=0;	
			while(rs.next())
			{
				po[i] = new PayOrderObject();	
				po[i].setPayOrderNo(rs.getInt(1));
				po[i].setPOChqNo(rs.getInt(2));
				po[i].setPOPayee(rs.getString(3)!=null?rs.getString(3):"");
				po[i].setTransAmount(rs.getDouble (4));
				po[i].setPOType(rs.getString(5));
				po[i].setPOSerialNo(rs.getInt(6));
				i++;
			}
		}
	 			
	}catch(Exception sqlexception){sqlexception.printStackTrace();}
	finally{
		try{
			connection.close();
		}catch(Exception exception){exception.printStackTrace();}
		
	}

	return po;	
}*/
public AccountTransObject[] retrievePassBook(int type,String actype,int acno,String from,String to) 
{    	
	int norows = 0,lastseqno=0,preseqno=0; 
	double preclosebal=0;
 	AccountTransObject arr1[] = null;
	
	Connection connection=null;
	Statement statement=null;

	try
	{
		connection=getConnection();
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

		Statement stat=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	 	Statement stat1=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
 		ResultSet rs = null,rs1 = null,rs_prev=null;
 		System.out.println("TYPE-="+type);
 		System.out.println("ACTYPE="+actype);
 		System.out.println("ACNO="+acno);
 		System.out.println("DATE="+from);
 		System.out.println("CONVETED DATE="+Validations.convertYMD(from));
 		System.out.println("TO DATE="+to);
 		System.out.println("CONVETED TO DATE="+Validations.convertYMD(to));
 		
 		if(actype.startsWith("1007") ||actype.startsWith("1017") ||actype.startsWith("1018") || actype.startsWith("1002"))
 		{	
	 		if(type==0)
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,IFNULL(at.chq_dd_no,0),at.trn_narr,IFNULL(at.ref_no,0),IFNULL(at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from AccountTransaction at, AccountMaster am where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and at.ac_no = am.ac_no and at.ac_type = am.ac_type and at.trn_seq > am.last_tr_seq ");  
			else if(type==1)
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,IFNULL(at.chq_dd_no,0),at.trn_narr,IFNULL(at.ref_no,0),at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from AccountTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1)) BETWEEN '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' order by trn_seq");
			else if(type==2)
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,IFNULL(at.chq_dd_no,0),at.trn_narr,IFNULL(at.ref_no,0),at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from AccountTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1)) BETWEEN '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' order by trn_seq");
 		}
 		else
 		{	
	 		if(type==0)
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,IFNULL(at.chq_dd_no,0),at.trn_narr,IFNULL(at.ref_no,0),at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from ODCCTransaction at, ODCCMaster am where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and at.ac_no = am.ac_no and at.ac_type = am.ac_type and at.trn_seq > am.last_tr_seq ");  
			else if(type==1)
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,IFNULL(at.chq_dd_no,0),at.trn_narr,IFNULL(at.ref_no,0),at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from ODCCTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1)) BETWEEN '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' order by trn_seq");
			else if(type==2)
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,IFNULL(at.chq_dd_no,0),at.trn_narr,IFNULL(at.ref_no,0),at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from ODCCTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1)) BETWEEN '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' order by trn_seq");
 		}
 			
		if(rs.next())
		{
			rs.last();
			norows=rs.getRow();
			arr1 = new AccountTransObject[rs.getRow()];
			rs.beforeFirst();
			rs.close();
		}
 		
		/**  To get LastTransaction Sequence number...and Previous Closing Balance.*/
 		if(type==0 || type==1)
 		{
	 		if(actype.startsWith("1007") ||actype.startsWith("1017") ||actype.startsWith("1018") || actype.startsWith("1002"))
	 			rs1 = stat.executeQuery(" select at.cl_bal,am.last_tr_seq from AccountTransaction at,AccountMaster am where am.ac_type = '"+actype+"' and am.ac_no = "+acno+" and at.ac_type=am.ac_type and at.ac_no=am.ac_no and (at.trn_seq = am.last_tr_seq or am.last_tr_seq = 1) ");
	 		else
	 			rs1 = stat.executeQuery(" select at.cl_bal,am.last_tr_seq from ODCCTransaction at,ODCCMaster am where am.ac_type = '"+actype+"' and am.ac_no = "+acno+" and at.ac_type=am.ac_type and at.ac_no=am.ac_no and (at.trn_seq = am.last_tr_seq or am.last_tr_seq = 1) ");
	 		
 			if(rs1.next())
 			{
 				lastseqno = rs1.getInt(2);
 				if(lastseqno==0)
 					preclosebal = 0;
 				else
 					preclosebal = rs1.getDouble(1); 	
 							
 				lastseqno = lastseqno+norows;
 			}
  			rs1.close();	  			
  		}
  		
  		/*if(type == 2 || type==1)
  		{
  			if(actype.startsWith("1007") || actype.startsWith("1017") ||actype.startsWith("1018") ||actype.startsWith("1002"))
  				rs=stat.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,at.chq_dd_no,at.trn_narr,at.ref_no,at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from AccountTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1)) BETWEEN '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"'");
  			else
  				rs=stat.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,at.chq_dd_no,at.trn_narr,at.ref_no,at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from ODCCTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1)) BETWEEN '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"'");
  			
 			if(rs.next())
 			{
 				preseqno = rs.getInt(12);
 				rs.close();

 				if(preseqno == 1)
 					preclosebal = 0;
 				else
 				{
 					preseqno = preseqno-1; 	
 					if(actype.startsWith("1007") ||actype.startsWith("1017") ||actype.startsWith("1018") || actype.startsWith("1002"))
 						rs1 = stat.executeQuery("select cl_bal from AccountTransaction where ac_type = '"+actype+"' and ac_no = "+acno+" and trn_seq = "+preseqno+" ");
 					else
 						rs1 = stat.executeQuery("select cl_bal from ODCCTransaction where ac_type = '"+actype+"' and ac_no = "+acno+" and trn_seq = "+preseqno+" ");
 						
 					rs1.next();
 					preclosebal = rs1.getDouble(1);
 					rs1.close();	 					
 				}	 			
	 		}
	 		else 
	 			rs.close();		 			
	 	}*/
 		
 		/** 	 To get Previous Closing Balance..*/
 		if(type == 2 || type==1 || type==0)
  		{
  			if(actype.startsWith("1007") || actype.startsWith("1017") ||actype.startsWith("1018") ||actype.startsWith("1002"))
  				rs_prev=stat.executeQuery("select cl_bal,trn_seq from AccountTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1))< '"+Validations.convertYMD(from)+"' order by trn_seq desc limit 1");
  			else
  				rs_prev=stat.executeQuery("select cl_bal,trn_seq from ODCCTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1))< '"+Validations.convertYMD(from)+"' order by trn_seq desc limit 1");
  			
 			if(rs_prev.next())
 			{
 				preseqno = rs_prev.getInt("trn_seq");	 				
 				if(preseqno == 0)
 					preclosebal = 0;
 				else	 								
 					preclosebal = rs_prev.getDouble("cl_bal");
 				
 				System.out.println("NO ROWSSSSSSSSSSSSSSSS="+norows);
 				if(norows==0)
 				{
 					arr1=new AccountTransObject[rs_prev.getRow()];
 					arr1[0]=new AccountTransObject();
 					arr1[0].setPreCloseBal(preclosebal);
 				}
	 		}
	 		rs_prev.close();		 			
	 	}
	 	/*if(norows>=1)
	 	{
			if(actype.startsWith("1007") ||actype.startsWith("1017") || actype.startsWith("1018") ||actype.startsWith("1002"))
				rs1 = stat.executeQuery("select cm.fname, cm.mname, cm.lname,ca.address,ca.city,ca.state,ca.pin,ca.phno,am.ac_opendate,ca.email,ca.phstd,ca.faxstd,ca.fax,ca.mobile from CustomerAddr ca,CustomerMaster cm , AccountMaster am where ca.addr_type =am.addr_type and am.ac_type = '"+actype+"' and am.ac_no = "+acno+" and am.cid = cm.cid and cm.cid = ca.cid");
			else
				rs1 = stat.executeQuery("select cm.fname, cm.mname, cm.lname,ca.address,ca.city,ca.state,ca.pin,ca.phno,am.ac_opendate,ca.email,ca.phstd,ca.faxstd,ca.fax,ca.mobile from CustomerAddr ca,CustomerMaster cm , ODCCMaster am where ca.addr_type =am.addr_type and am.ac_type = '"+actype+"' and am.ac_no = "+acno+" and am.cid = cm.cid and cm.cid = ca.cid");
	 	}*/
		
		int i=0;
		if(actype.startsWith("1007") || actype.startsWith("1002")|| actype.startsWith("1017") ||actype.startsWith("1018"))
 		{	
	 		if(type==0)
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,IFNULL(at.chq_dd_no,0),at.trn_narr,IFNULL(at.ref_no,0),at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from AccountTransaction at, AccountMaster am where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and at.ac_no = am.ac_no and at.ac_type = am.ac_type and at.trn_seq > am.last_tr_seq ");  
			else if(type==1)
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,IFNULL(at.chq_dd_no,0),at.trn_narr,IFNULL(at.ref_no,0),at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from AccountTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1)) BETWEEN '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' order by trn_seq");
			else if(type==2)
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,IFNULL(at.chq_dd_no,0),at.trn_narr,IFNULL(at.ref_no,0),at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from AccountTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1)) BETWEEN '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' order by trn_seq");
 		}
 		else
 		{	
	 		if(type==0)
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,IFNULL(at.chq_dd_no,0),at.trn_narr,IFNULL(at.ref_no,0),at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from ODCCTransaction at, ODCCMaster am where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and at.ac_no = am.ac_no and at.ac_type = am.ac_type and at.trn_seq > am.last_tr_seq ");  
			else if(type==1)
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,IFNULL(at.chq_dd_no,0),at.trn_narr,IFNULL(at.ref_no,0),at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from ODCCTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1)) BETWEEN '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' order by trn_seq");
			else if(type==2)
				rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,IFNULL(at.chq_dd_no,0),at.trn_narr,IFNULL(at.ref_no,0),at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from ODCCTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1)) BETWEEN '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' order by trn_seq");
 		}			
 		while(rs.next())
 		{	 					
 			arr1[i]=new AccountTransObject(); 		
 			arr1[i].setTransDate(rs.getString(1));
 			arr1[i].setChqDDNo(rs.getInt(5));
 			arr1[i].setTransAmount(rs.getDouble(2));
 			arr1[i].setTransMode(rs.getString(3));
 			arr1[i].setCloseBal(rs.getDouble(9));
 			arr1[i].uv.setUserId(rs.getString(10));
 			arr1[i].uv.setVerId(rs.getString(11));
 			arr1[i].setCdInd(rs.getString(4));

 			if(rs.getString(3).equals("T"))
 			{
 				//System.out.println("STRING="+rs.getString(6).toString());
 				if(rs.getString(6)!=null || !rs.getString(6).toString().equals(""))
 				{
 					String narr="";
 					StringTokenizer st=new StringTokenizer(rs.getString(6)," ");
 					ResultSet rs_mod=stat1.executeQuery("select moduleabbr from Modules where modulecode='"+st.nextToken()+"'");	 					
 					if(rs_mod.next())
 						narr=narr+rs_mod.getString(1)+" "+st.nextToken();
 					else
 						narr=narr+rs.getString(6);
 					arr1[i].setTransNarr(narr);
 				}
 				else
 					arr1[i].setTransNarr(rs.getString(6));	 					
 			}
 			else
 				arr1[i].setTransNarr(rs.getString(6));
 			 			
 			arr1[i].setPayeeName(rs.getString(8));
 			arr1[i].setRef_No(Integer.parseInt(rs.getString(7)));
 			arr1[i].setPreCloseBal(preclosebal);
 			arr1[i].setTransSeqNo(rs.getInt(12));
 			i++;	 			
 		}
 			
 		//  Updating table with new Transaction Sequence...
 		/*if(type==0)
 		{
			if(actype.startsWith("1007") ||actype.startsWith("1017") ||actype.startsWith("1018") || actype.startsWith("1002"))
				statement.executeUpdate(" update AccountMaster set last_tr_seq = "+lastseqno+" where ac_no = "+acno+" and ac_type = '"+actype+"'");
			else
				statement.executeUpdate(" update ODCCMaster set last_tr_seq = "+lastseqno+" where ac_no = "+acno+" and ac_type = '"+actype+"'");
 		}*/
 	
}catch(Exception sqlexception){sqlexception.printStackTrace();}
finally{
	try{
		connection.close();
	}catch(Exception exception){exception.printStackTrace();}
	
}

	return arr1;
	
}
public AccountTransObject[] retrieveParseBook(int type,String actype,int acno,String from,String to)
{
	int norows = 0,lastseqno=0,preseqno=0; 
	double preclosebal=0;
 	AccountTransObject arr1[] = null;
	
	Connection connection=null;
	Statement statement=null;

	try
	{
		connection=getConnection();
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

		Statement stat=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	 	Statement stat1=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
 		ResultSet rs = null,rs1 = null,rs_prev=null;
 		System.out.println("Inside new Parse Sheet ..............");
 		System.out.println("TYPE-="+type);
 		System.out.println("ACTYPE="+actype);
 		System.out.println("ACNO="+acno);
 		System.out.println("DATE="+from);
 		System.out.println("CONVETED DATE="+Validations.convertYMD(from));
 		System.out.println("TO DATE="+to);
 		System.out.println("CONVETED TO DATE="+Validations.convertYMD(to));
 		
 		if(actype.startsWith("1007") ||actype.startsWith("1017") ||actype.startsWith("1018") || actype.startsWith("1002"))
 		{
 			rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,IFNULL(at.chq_dd_no,0),at.trn_narr,IFNULL(at.ref_no,0),at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from AccountTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1)) BETWEEN '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' order by trn_seq");
 			rs_prev=stat.executeQuery("select cl_bal,trn_seq from AccountTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1))< '"+Validations.convertYMD(from)+"' order by trn_seq desc limit 1");
 			
 		}
 		else
 		{
 			rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,IFNULL(at.chq_dd_no,0),at.trn_narr,IFNULL(at.ref_no,0),at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from ODCCTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1)) BETWEEN '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' order by trn_seq");
			rs_prev=stat.executeQuery("select cl_bal,trn_seq from ODCCTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and concat(right(at.trn_date,4),'-',mid(at.trn_date,locate('/',at.trn_date)+1, (locate('/',at.trn_date,4)-locate('/',at.trn_date)-1)),'-',left(at.trn_date,locate('/',at.trn_date)-1))< '"+Validations.convertYMD(from)+"' order by trn_seq desc limit 1");
 		}
 		if(rs_prev.next())
		{
			preseqno = rs_prev.getInt("trn_seq");
			preclosebal=rs_prev.getDouble("cl_bal");
			if(!(rs.next()))
			{			
				arr1 = new AccountTransObject[rs_prev.getRow()];
				arr1[0]=new AccountTransObject();
				arr1[0].setPreCloseBal(preclosebal);
	 			arr1[0].setLast_seq_no(preseqno);
			}
			else
			{
				rs.beforeFirst();
			}
			
		}
		if(rs.next())
		{
			rs.last();
			norows=rs.getRow();
			arr1 = new AccountTransObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
	 		{	 					
	 			arr1[i]=new AccountTransObject(); 		
	 			arr1[i].setTransDate(rs.getString(1));
	 			arr1[i].setChqDDNo(rs.getInt(5));
	 			arr1[i].setTransAmount(rs.getDouble(2));
	 			arr1[i].setTransMode(rs.getString(3));
	 			arr1[i].setCloseBal(rs.getDouble(9));
	 			arr1[i].uv.setUserId(rs.getString(10));
	 			arr1[i].uv.setVerId(rs.getString(11));
	 			arr1[i].setCdInd(rs.getString(4));

	 			if(rs.getString(3).equals("T"))
	 			{
	 				//System.out.println("STRING="+rs.getString(6).toString());
	 				if(rs.getString(6)!=null || !rs.getString(6).toString().equals(""))
	 				{
	 					String narr="";
	 					StringTokenizer st=new StringTokenizer(rs.getString(6)," ");
	 					ResultSet rs_mod=stat1.executeQuery("select moduleabbr from Modules where modulecode='"+st.nextToken()+"'");	 					
	 					if(rs_mod.next())
	 						narr=narr+rs_mod.getString(1)+" "+st.nextToken();
	 					else
	 						narr=narr+rs.getString(6);
	 					arr1[i].setTransNarr(narr);
	 				}
	 				else
	 					arr1[i].setTransNarr(rs.getString(6));	 					
	 			}
	 			else
	 				arr1[i].setTransNarr(rs.getString(6));
	 			 			
	 			arr1[i].setPayeeName(rs.getString(8));
	 			arr1[i].setRef_No(Integer.parseInt(rs.getString(7)));
	 			arr1[i].setPreCloseBal(preclosebal);
	 			arr1[i].setLast_seq_no(preseqno);
	 			arr1[i].setTransSeqNo(rs.getInt(12));
	 			i++;	 			
	 		}
		}
		/*else if(rs_prev.next())
		{
			rs=statement.executeQuery("select at.trn_date,at.trn_amt,at.trn_mode,at.cd_ind,IFNULL(at.chq_dd_no,0),at.trn_narr,IFNULL(at.ref_no,0),at.payee_nm,at.cl_bal,at.de_user,at.ve_user,at.trn_seq from ODCCTransaction at where at.ac_type = '"+actype+"' and at.ac_no = "+acno+" and at.trn_seq="+preseqno+" ");
			int i=0;
			while(rs.next())
		 	{	 					
		 		arr1[i]=new AccountTransObject(); 		
		 		arr1[i].setTransDate(rs.getString(1));
		 		arr1[i].setChqDDNo(rs.getInt(5));
		 		arr1[i].setTransAmount(rs.getDouble(2));
		 		arr1[i].setTransMode(rs.getString(3));
		 		arr1[i].setCloseBal(rs.getDouble(9));
		 		arr1[i].uv.setUserId(rs.getString(10));
		 		arr1[i].uv.setVerId(rs.getString(11));
		 		arr1[i].setCdInd(rs.getString(4));
		 		if(rs.getString(3).equals("T"))
		 		{
		 				//System.out.println("STRING="+rs.getString(6).toString());
		 			if(rs.getString(6)!=null || !rs.getString(6).toString().equals(""))
		 			{
		 				String narr="";
		 				StringTokenizer st=new StringTokenizer(rs.getString(6)," ");
		 				ResultSet rs_mod=stat1.executeQuery("select moduleabbr from Modules where modulecode='"+st.nextToken()+"'");	 					
		 				if(rs_mod.next())
		 					narr=narr+rs_mod.getString(1)+" "+st.nextToken();
		 				else
		 						narr=narr+rs.getString(6);
		 					arr1[i].setTransNarr(narr);
		 				}
		 				else
		 					arr1[i].setTransNarr(rs.getString(6));	 					
		 			}
		 			else
		 				arr1[i].setTransNarr(rs.getString(6));
		 			 			
		 			arr1[i].setPayeeName(rs.getString(8));
		 			arr1[i].setRef_No(Integer.parseInt(rs.getString(7)));
		 			arr1[i].setPreCloseBal(preclosebal);
		 			arr1[i].setLast_seq_no(preseqno);
		 			arr1[i].setTransSeqNo(rs.getInt(12));
		 			i++;	 			
		 		}
			}*/
		
	}catch(Exception sqlexception){sqlexception.printStackTrace();}
	finally{
		try{
			connection.close();
		}catch(Exception exception){exception.printStackTrace();}
		
	}

		return arr1;
		
}

public CustomerMasterObject[] retrieveinfo(String cum_name,String cum_acctype) 
{
	CustomerMasterObject objs[] = null; 
	int norows=0;
	Connection connection=null;
	Statement statement=null;

	try
	{
		connection=getConnection();
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs=statement.executeQuery("select cm.fname,cm.cid, am.ac_no from CustomerMaster cm ,AccountMaster am where am.ac_type = '"+cum_acctype+"' and cm.fname LIKE '"+cum_name+"%' and am.cid = cm.cid"); 

		while(rs.next())
			norows++;

		objs = new CustomerMasterObject[norows];
		rs.close();
		rs=statement.executeQuery("select cm.fname,cm.cid, am.ac_no from CustomerMaster cm ,AccountMaster am where am.ac_type = '"+cum_acctype+"' and cm.fname LIKE '"+cum_name+"%' and am.cid = cm.cid"); 

		for(int i=0;i<norows;i++)
		{
	       	if(!rs.next())
			break; 
		objs[i] = new CustomerMasterObject();
		objs[i].setFirstName(rs.getString(1));
		objs[i].setCustomerID(rs.getInt(3));				
		}
	}catch(Exception sqlexception){sqlexception.printStackTrace();}
	finally{
		try{
			connection.close();
		}catch(Exception exception){exception.printStackTrace();}
		
	}

	 return objs;
}

/**
 * Function to set stop,cancel,revalidate PayOrder Instructions in PayOrder table
 * The updated details are copied into PayOrderLog table
 */
public int setPayOrderInstrn(int type,String no,int chqno,String date,String tml,String uid) 
{
	int result=0;
	Connection connection=null;
	Statement statement=null;
	Statement stmt=null;
	Statement stmnt=null;
	
	PreparedStatement ps=null;

	try
	{
		connection=getConnection();
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		stmnt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		System.out.println("Indicatorrrrrrrrrrrrrrrrrrrrr"+no);
		if(type==1)
			result=statement.executeUpdate("update PayOrder set po_stop='"+no+"',valid_upto='"+date+"' where po_chq_no="+chqno);
		else if(type==2)
		{
			result=statement.executeUpdate("update PayOrder set po_cancel='"+no+"',po_stop='F',valid_upto='"+date+"'  where po_chq_no="+chqno);
		    
		    //Added on 18/02/2006 for Transfer
		   /*try
		   {
		       result=statement.executeUpdate("update PayOrder set po_cancel='"+no+"' and cd_ind='D' where po_chq_no="+chqno);
		       if(result==1)
		       {
		           ResultSet rs_po=stmt.executeQuery("select payord_no,payord_dt,trn_amt from PayOrder where po_chq_no="+chqno+" and ve_user is not null and po_csh_ind=0");
		           if(rs_po.next())
		           {	
		               int po_no=rs_po.getInt("payord_no");
		               String po_dt=rs_po.getString("payord_dt");
		               float po_amt=rs_po.getDouble ("trn_amt");
		               
		               ResultSet rs_lnk=statement.executeQuery("select pm.po_acty,pm.po_acno,pm.po_sno,pm.po_purchaser_name,pm.po_amt from PayOrderMake pm,POLink pl where pm.po_sno=pl.ref_sno and pm.po_type=pl.ref_type and pl.payord_no="+po_no+" and pm.po_date='"+po_dt+"'and pm.ve_user is not null and pl.ref_sno is not null");
		               while(rs_lnk.next())
		               {
		                   String ac_ty=rs_lnk.getString("pm.po_acty");
		                   int ac_no=rs_lnk.getInt("pm.po_acno");
		                   int po_sno=rs_lnk.getInt("pm.po_sno");
		                   String payee_name=rs_lnk.getString("pm.po_purchaser_name");
		                   float po_amt=rs_lnk.getFloat("pm.po_amt");
		                   
		                   if(ac_ty!=null)
		   			       {
		   			        	ResultSet rs1=null;
		   			        
		   			        	if(ac_ty.startsWith("1002") ||ac_ty.startsWith("1007") ||ac_ty.startsWith("1017") ||ac_ty.startsWith("1018"))
		   			        	    rs1=stmnt.executeQuery("select trn_seq,cl_bal from AccountTransaction where ac_no="+ac_no+" and ac_type='"+ac_ty+"' order by trn_seq desc limit 1");
		   			        	else
		   			        	    rs1=stmnt.executeQuery("select trn_seq,cl_bal from ODCCTransaction where ac_no="+ac_no+" and ac_type='"+ac_ty+"' order by trn_seq desc limit 1");
		   						
		   			        	int trnseq=0;
		   			        	double cl_bal=0;
		   			        	if(rs1.next())
		   				    	{
		   			        	    trnseq=rs1.getInt(1)+1;
		   			        	    cl_bal=rs1.getDouble(2);
		   				    	}
		   			        	else
		   			        	    throw new SQLException("Account not found");
		   					
		   					System.out.println("TrnSeq:"+trnseq);
		   					System.out.println("AccType:"+ac_ty);
		   					System.out.println("AccNo: "+ac_no);					

		   					if(ac_ty.startsWith("1002") || ac_ty.startsWith("1007") ||ac_ty.startsWith("1018")||ac_ty.startsWith("1017"))
		                       ps=connection.prepareStatement("insert into AccountTransaction values(?,?,'"+getSysDate()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+getSysDateTime()+"',?,?,'"+getSysDateTime()+"')");
		   					else if(ac_ty.startsWith("1014") || ac_ty.startsWith("1015"))
		   					{
		   					    ResultSet rs2=null;							
							    rs2=statement.executeQuery("select int_uptodt from ODCCMaster where ac_type='"+ac_ty+"' and ac_no="+ac_no);
								rs2.next();
									String lst_int_caldt=rs1.getString("int_uptodt");
		   					    ps=connection.prepareStatement("insert into ODCCTransaction values(?,?,'"+getSysDate()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+getSysDateTime()+"',?,?,'"+getSysDateTime()+"','"+lst_int_caldt+"')"); 
		   					}
		   					
		   					ps.setString(1,ac_ty);
							ps.setInt(2,ac_no);
							
							ps.setString(3,"P");
							ps.setInt(4,trnseq);
							ps.setDouble(5,po_amt);
							ps.setString(6,"T");
							ps.setString(7,tml);
							ps.setString(8,"C");
							ps.setDouble(9,chqno);	
							ps.setString(10,po_dt);
							ps.setString(11,"PayOrder "+po_sno);						
							ps.setInt(12,po_sno);
							ps.setString(13,payee_name);
							ps.setDouble(14,(cl_bal+po_amt));
							ps.setInt(15,0);						
							ps.setString(16,tml);
							ps.setString(17,uid);
							ps.setString(18,tml);
							ps.setString(19,uid);
							System.out.println("55555555555555555"+ps);
							if(ps.executeUpdate()!=1)
								throw new SQLException("ODCC/AccountTransaction not stored"); 
							
		   			       }//ac_ty!=null
		               }//rs_lnk.next()		               
		           }//rs_po.next()
		       }//result!=0		           
		   }catch(Exception e){e.printStackTrace();}*/			
		}
		else if(type==3)
			result=statement.executeUpdate("update PayOrder set po_stop='"+no+"',valid_upto='"+date+"'  where po_chq_no="+chqno);
		
		if(result==1)
		{
			if((result=statement.executeUpdate("insert into PayOrderLog select * from PayOrder where po_chq_no="+chqno+""))!=1)
				throw new SQLException("PayOrderLog not inserted");
		}
		else
			throw new SQLException("PayOrder not updated");

		return(result);
	}catch(Exception sqlexception)
	{
		ctx.setRollbackOnly();
		sqlexception.printStackTrace();
		try
		{
			ctx.setRollbackOnly();
			result=0;
		}catch(Exception exception)
		{
			exception.printStackTrace();}
	}
	finally{
		try{
			connection.close();
		}catch(Exception exception){exception.printStackTrace();}
		
	}


	return(result);
}

/**----------------- Savings Admin--------------------------------*/

public int insertIntoTable(String table_name,int no_of_columns,Vector vec) 
{
	Connection connection=null;
	ResultSet rs=null;
	
	try
	{
		connection=getConnection();
		Statement stmt=connection.createStatement();
		System.out.println("Server Insertintotable "+table_name);
		
		String str="";
		for(int i=0;i<no_of_columns;i++)
			if(i<(no_of_columns-1))
				str+="?,";
			else
				str+="?";
		System.out.println("Server Insertintotable "+str);
		PreparedStatement ps=connection.prepareStatement("insert into "+table_name+" values("+str+")");
		System.out.println("Vector Size "+vec.size());
		for(int i=0;i<vec.size();i++)
		{
		    System.out.println("Vector Size "+vec.size());
		    System.out.println("Vector get i "+vec.get(i));
			
		    Object obj[]=(Object[])vec.get(i);
			System.out.println("Object Length "+obj.length);			
			for(int j=0;j<obj.length;j++)
			{	
			    System.out.println("Vluesss "+(j+1));
				ps.setString(j+1,obj[j].toString());
				System.out.println("Vlooooooo "+obj[j].toString());
				//if(table_name.equalsIgnoreCase("Modules"))
			}
			ps.addBatch();			
		}
		int a[]=ps.executeBatch();
		return (a[0]);		
	}catch(Exception exception){exception.printStackTrace();}
	finally{
		try{
			connection.close();
		}catch(Exception exception){exception.printStackTrace();}		
	}
	return 1;
}

public int deleteFromTable(String table_name,String condition) 
{
	Connection connection=null;
	Statement statement=null;

	try
	{
		connection=getConnection();
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		return( statement.executeUpdate("delete from "+table_name+" where "+condition+""));
				
	}catch(Exception exception){exception.printStackTrace();}
	finally{
		try{
			connection.close();
		}catch(Exception exception){exception.printStackTrace();}		
	}

	return 1;
}

public int updateTable(String table_name,String update,String update_condition,String condition) 
{
	Connection connection=null;
	Statement statement=null;
	ResultSet rs=null;
	System.out.println("Inside Server UpdateTable");
	try
	{
		connection=getConnection();
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		
		System.out.println("Insertion check condition"+update);
	
		rs=statement.executeQuery("select * from "+table_name+" where "+condition+" ");
		if(rs.next())
			return -2;
		else
			return(statement.executeUpdate("update "+table_name+" set "+update+" where "+update_condition+" "));
				
	}catch(Exception exception){
		ctx.setRollbackOnly();
		exception.printStackTrace();
	}
	finally
	{
		try{
			connection.close();
		}catch(Exception exception){exception.printStackTrace();}		
	}
	return 1;
}


/*public Connection getConnection() throws Exception
{
	try{
		return ds.getConnection("root","admin");		

	}catch(Exception e)	{e.printStackTrace();}
	return null;
}*/

private Connection getConnection() throws SQLException{
	Connection con = ds.getConnection("root","");
	if(con == null)
	    throw new SQLException();
	return con;
}




public Object[][] getLoanDetails(String shtype,int shno)
{

	Object obj[][]=null;
	Connection conn=null;
	double share_bal=0;
	int mem_cat=0;
	try{
		conn=getConnection();
		Statement stmt=conn.createStatement();
		Object obj2[][]=null;
		//		ResultSet rs_mst=cat_stmt.executeQuery("select lm.ac_type,lm.ac_no,cm.sub_category,lm.sanc_amt,lm.lst_tr_seq,lm.int_rate,lm.int_rate_type,lm.int_upto_date,lm.no_inst,m.moduledesc,m.moduleabbr from LoanMaster lm,CustomerMaster cm,Modules m where lm.cid="+cid+" and lm.ac_type not like '1008%' and cm.cid=lm.cid and m.modulecode=lm.ac_type");
		//ResultSet rs_mst=stmt.executeQuery("select share_val,mem_cat,lnk_shares,moduleabbr,LoanMaster.ac_no,disb_left,pr_bal,m.moduledesc,m.moduleabbr  from Modules m,ShareMaster,LoanMaster,LoanTransaction where  LoanMaster.sh_no="+shno+" and LoanMaster.sh_type="+shtype+" and LoanMaster.ac_type not like '1008%'  and LoanTransaction.ac_type=LoanMaster.ac_type  and LoanTransaction.ac_no=LoanMaster.ac_no and lst_tr_seq=trn_seq and ln_availed='T' and m.modulecode=LoanMaster.ac_type and ShareMaster.ac_no="+shno+" and ShareMaster.ac_type="+shtype+"  and sanc_ver='Y'");	//Kamal	
		ResultSet rs_mst=stmt.executeQuery("select share_val,mem_cat,m.lnk_shares,LoanMaster.ac_no,sanc_amt,disb_left,pr_bal,m.moduledesc,m.moduleabbr  from Modules m,ShareMaster,LoanMaster,LoanTransaction where  LoanMaster.sh_no="+shno+" and LoanMaster.sh_type="+shtype+" and LoanMaster.ac_type not like '1008%'  and LoanTransaction.ac_type=LoanMaster.ac_type  and LoanTransaction.ac_no=LoanMaster.ac_no and lst_tr_seq=trn_seq and ln_availed='T' and m.modulecode=LoanMaster.ac_type and ShareMaster.ac_no="+shno+" and ShareMaster.ac_type="+shtype+"  and sanc_ver='Y'");	//dee 4 sanc_amt
		if(rs_mst.next())
		{
			rs_mst.last();

			share_bal=rs_mst.getDouble("share_val");
		    mem_cat=rs_mst.getInt("mem_cat");
		    //obj2=new Object[rs_mst.getRow()+1][5];//kamal
		    obj2=new Object[rs_mst.getRow()+1][6];
			rs_mst.beforeFirst();
			
			Statement stmt2=conn.createStatement();
	        ResultSet rs3=stmt2.executeQuery("select shareval from ShareType where mem_cat="+mem_cat+" and ac_type="+shtype);
			if(rs3.next())
			{  
				
			    System.out.println("rs3..........first");	
				obj2[0][4]="";
				obj2[0][0]=String.valueOf(rs3.getDouble("shareval"));
				obj2[0][1]=""+share_bal;
				obj2[0][2]=""+(share_bal/rs3.getDouble("shareval"));
				obj2[0][3]="";
				
				rs3.close();
				stmt2.close();

			}

		}	
		else
		{	
			Statement stmt2=conn.createStatement();
	        //ResultSet rs3=stmt2.executeQuery("select shareval,sh_cap,sus_sh_cap,share_val from ShareType st,ShareMaster sm where st.ac_type='"+shtype+"' and sm.ac_type=st.ac_type and st.mem_cat=sm.mem_cat and sm.ac_no="+shno+"");
			ResultSet rs3=stmt2.executeQuery("select shareval,share_val from ShareType st,ShareMaster sm where st.ac_type='"+shtype+"' and sm.ac_type=st.ac_type and st.mem_cat=sm.mem_cat and sm.ac_no="+shno+"");

	        if(rs3.next())
			{
	        	share_bal=rs3.getDouble("share_val");


				obj2=new Object[1][5];
			    System.out.println("rs3..........second");	
				obj2[0][4]="";
				obj2[0][0]=String.valueOf(rs3.getDouble("shareval"));
				obj2[0][1]=""+share_bal;
				obj2[0][2]=""+(share_bal/rs3.getDouble("shareval"));
				obj2[0][3]="";
				
				rs3.close();
				stmt2.close();

			}

		}
		int i=1;
		while(rs_mst.next())
		{
			obj2[i][5]=rs_mst.getString("lnk_shares");
			obj2[i][0]=rs_mst.getString("moduleabbr")+"("+rs_mst.getString("moduledesc")+")";
			obj2[i][1]=rs_mst.getString("LoanMaster.ac_no");
			obj2[i][2]=rs_mst.getString("sanc_amt");//dee
			obj2[i][3]=rs_mst.getString("disb_left");
			obj2[i][4]=rs_mst.getString("pr_bal");

			i++;			
		}
		
		Object obj1[][]=null;
		//rs_mst=stmt.executeQuery("select odcc.ac_no,odcc.ac_type,share_val,mem_cat,lnk_shares,m.moduledesc,m.moduleabbr,(credit_limit+cl_bal) as clbal  from Modules m,ShareMaster sm,ODCCMaster odcc,ODCCTransaction odcct,StockInspectionDetails stk where odcc.ac_type like '1014%' and odcc.sh_no="+shno+" and odcc.sh_type='"+shtype+"' and odcct.ac_type=odcc.ac_type  and odcct.ac_no=odcc.ac_no and stk.ac_type=odcc.ac_type  and stk.ac_no=odcc.ac_no and m.modulecode=odcc.ac_type and sm.ac_no="+shno+" and sm.ac_type='"+shtype+"' and odcc.sanc_ver='Y' and odcct.trn_seq=(select max(trn_seq) from ODCCTransaction odcct1 where odcct1.ac_type=odcc.ac_type and odcct1.ac_no=odcc.ac_no) and stk.insp_date =(select insp_date from StockInspectionDetails stk2 where stk2.ac_type=odcc.ac_type  and stk2.ac_no=odcc.ac_no order by concat(right(stk2.insp_date,4),'-',mid(stk2.insp_date,locate('/',stk2.insp_date)+1, (locate('/',stk2.insp_date,4)-locate('/',stk2.insp_date)-1)),'-',left(stk2.insp_date,locate('/',stk2.insp_date)-1)) limit 1)");//kamal	
		rs_mst=stmt.executeQuery("select odcc.ac_no,odcc.ac_type,odcc.sanc_amt,share_val,mem_cat,lnk_shares,m.moduledesc,m.moduleabbr,(credit_limit+cl_bal) as clbal  from Modules m,ShareMaster sm,ODCCMaster odcc,ODCCTransaction odcct,StockInspectionDetails stk where odcc.ac_type like '1014%' and odcc.sh_no="+shno+" and odcc.sh_type='"+shtype+"' and odcct.ac_type=odcc.ac_type  and odcct.ac_no=odcc.ac_no and stk.ac_type=odcc.ac_type  and stk.ac_no=odcc.ac_no and m.modulecode=odcc.ac_type and sm.ac_no="+shno+" and sm.ac_type='"+shtype+"' and odcc.sanc_ver='Y' and odcct.trn_seq=(select max(trn_seq) from ODCCTransaction odcct1 where odcct1.ac_type=odcc.ac_type and odcct1.ac_no=odcc.ac_no) and stk.insp_date =(select insp_date from StockInspectionDetails stk2 where stk2.ac_type=odcc.ac_type  and stk2.ac_no=odcc.ac_no order by concat(right(stk2.insp_date,4),'-',mid(stk2.insp_date,locate('/',stk2.insp_date)+1, (locate('/',stk2.insp_date,4)-locate('/',stk2.insp_date)-1)),'-',left(stk2.insp_date,locate('/',stk2.insp_date)-1)) limit 1)");
		if(rs_mst.next())
		{
			rs_mst.last();

			share_bal=rs_mst.getDouble("share_val");
		    mem_cat=rs_mst.getInt("mem_cat");

			//obj1=new Object[rs_mst.getRow()][5];//kamal
		    obj1=new Object[rs_mst.getRow()][6];
			rs_mst.beforeFirst();	
		}	
		i=0;
		while(rs_mst.next())
		{
			obj1[i][5]=rs_mst.getString("lnk_shares");
			obj1[i][0]=rs_mst.getString("moduleabbr")+"("+rs_mst.getString("moduledesc")+")";
			obj1[i][1]=rs_mst.getString("odcc.ac_no");
			obj1[i][2]=rs_mst.getString("odcc.sanc_amt");			
			obj1[i][3]="0.00";
			obj1[i][4]=rs_mst.getString("clbal");

			i++;			
		}
		
		Object obj3[][]=null;
		//rs_mst=stmt.executeQuery("select odcc.ac_no,odcc.ac_type,share_val,mem_cat,lnk_shares,m.moduledesc,m.moduleabbr,(creditlimit+cl_bal) as clbal  from Modules m,ShareMaster sm,ODCCMaster odcc,ODCCTransaction odcct where   odcc.ac_type like '1015%' and odcc.sh_no="+shno+" and odcc.sh_type='"+shtype+"' and odcct.ac_type=odcc.ac_type  and odcct.ac_no=odcc.ac_no and ln_availed='T' and m.modulecode=odcc.ac_type and sm.ac_no="+shno+" and sm.ac_type='"+shtype+"' and odcc.sanc_ver='Y' and odcct.trn_seq=(select max(trn_seq) from ODCCTransaction odcct1 where odcct1.ac_type=odcc.ac_type and odcct1.ac_no=odcc.ac_no)");
		rs_mst=stmt.executeQuery("select odcc.ac_no,odcc.ac_type,odcc.sanc_amt,share_val,mem_cat,lnk_shares,m.moduledesc,m.moduleabbr,(creditlimit+cl_bal) as clbal  from Modules m,ShareMaster sm,ODCCMaster odcc,ODCCTransaction odcct where   odcc.ac_type like '1015%' and odcc.sh_no="+shno+" and odcc.sh_type='"+shtype+"' and odcct.ac_type=odcc.ac_type  and odcct.ac_no=odcc.ac_no and ln_availed='T' and m.modulecode=odcc.ac_type and sm.ac_no="+shno+" and sm.ac_type='"+shtype+"' and odcc.sanc_ver='Y' and odcct.trn_seq=(select max(trn_seq) from ODCCTransaction odcct1 where odcct1.ac_type=odcc.ac_type and odcct1.ac_no=odcc.ac_no)");		
		if(rs_mst.next())
		{
			rs_mst.last();

			share_bal=rs_mst.getDouble("share_val");
		    mem_cat=rs_mst.getInt("mem_cat");

			//obj3=new Object[rs_mst.getRow()][5];
		    obj3=new Object[rs_mst.getRow()][6];
			rs_mst.beforeFirst();
		}	
		i=0;
		while(rs_mst.next())
		{
			obj3[i][5]=rs_mst.getString("lnk_shares");
			obj3[i][0]=rs_mst.getString("moduleabbr")+"("+rs_mst.getString("moduledesc")+")";
			obj3[i][1]=rs_mst.getString("odcc.ac_no");
			obj3[i][2]=rs_mst.getString("odcc.sanc_amt");
			obj3[i][3]="0.00";
			obj3[i][4]=rs_mst.getString("clbal");

			i++;
			
		}

		
		int no_of_rows=0;
		if(obj2!=null)
			no_of_rows=obj2.length;
		
		
		if(obj1!=null)
			no_of_rows+=obj1.length;

		if(obj3!=null)
			no_of_rows+=obj3.length;
		
		//obj=new Object[no_of_rows][5];
		obj=new Object[no_of_rows][6];
		
		i=0;
		if(obj2!=null)
		for(int j=0;j<obj2.length;j++)
			obj[i++]=obj2[j];
		if(obj1!=null)
		for(int j=0;j<obj1.length;j++)
			obj[i++]=obj1[j];
		if(obj3!=null)
		for(int j=0;j<obj3.length;j++)
			obj[i++]=obj3[j];
		
		
	System.out.println("no of shares---------"+obj[0][0]+" "+obj[0][2]);	
		

		
	}catch(Exception exception){exception.printStackTrace();}
	finally{
		try{		    	
			conn.close();
		}catch(Exception exception){exception.printStackTrace();}
	}
	
	return obj;
}

/** Function added by Deepa on 4/01/2006 */
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
				
		releventdetails = new String[12]; 
		if(rs.next())
		{			
			for(int i=0;i<12;i++)
				releventdetails[i]=rs.getString(i+3);			
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

public Object[][] getInterestDetails(String ac_type,int ac_no)
{
    Object obj[][]=null;
	Connection connection=null;
	Statement statement=null;
	Statement statement1=null;
	ResultSet rs1=null;
	try
	{
		connection=getConnection();
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		statement1=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs=statement.executeQuery("select odt.sec_ac_type,odt.sec_ac_no,odt.ln_int_rate,odt.int_type,odt.dep_amt,odt.eligible_amt,odt.int_amt,m.moduleabbr from ODInterestDetails odt left join Modules m on m.modulecode=odt.sec_ac_type where ac_type='"+ac_type+"' and ac_no="+ac_no+" order by odt.ln_int_rate asc,odt.eligible_amt desc");
		
		if(rs.last())
		{
		 obj=new Object[rs.getRow()][];
		    rs.beforeFirst();

		}
		int i=0;
		while(rs.next())
		{
			obj[i]=new Object[7];
			for(int j=0;j<7;j++)
			{	
				if(j==0)
				{	
					if(rs.getString(8)!=null)
						obj[i][j]=rs.getString(8);
					else
					{	
						rs1=statement1.executeQuery("select * from SecurityDetails where code="+rs.getString(1)+"");
						if(rs1.next())
							obj[i][j]=rs1.getString("type_of_security");
					}	
				}
				else
					obj[i][j]=rs.getString(j+1);
			}	
			
			i++;
		}
        
    }catch(Exception ex){ex.printStackTrace();}
    finally
    {
        try {
            connection.close();
        } catch (SQLException e) {
        
            e.printStackTrace();
        }
        
    }
    return obj;
}

/** Added on 16/08/06 */
//public double calculateODInterestRate(String ac_type,int ac_no,String fdate,int category,int period,double amount,int int_type)
public double calculateODInterestRate(String ac_type,int ac_no,String fdate,String tdate,int category,int period,double amount,int int_type)
{
    double int_rate=0;
    
	Connection connection=null;
	Statement statement=null;
	Statement stat=null,stmt_pygmy;
	
	System.out.println("///////////////////Inside calculateODInterestRate/////////////////////");
	System.out.println("AC TYPE="+ac_type);
	System.out.println("AC NO="+ac_no);
	System.out.println(" from date="+fdate);
	System.out.println("To Date="+tdate);
	System.out.println("Category="+category);
	System.out.println("Period="+period);
	System.out.println("Amt="+amount);
	System.out.println("Int TYPE="+int_type);

	try
	{
		connection=getConnection();
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		stat=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		
		if(int_type!=-1)
		{
			ResultSet rs=statement.executeQuery("select * from ODInterestDetails where ac_type='"+ac_type+"' and ac_no="+ac_no+" order by ln_int_rate");

			if(rs.next())
				statement.executeUpdate("delete from ODInterestDetails where ac_type='"+ac_type+"' and ac_no="+ac_no+" order by ln_int_rate");
		
			PreparedStatement ps=connection.prepareStatement("insert into ODInterestDetails values(?,?,?,?,?,?,?,?,?,'"+tdate+"',null)");
	    
			rs=statement.executeQuery("select * from EmploymentMaster where ac_type='"+ac_type+"' and ac_no="+ac_no+"");		    
			while(rs.next())
			{			
				ps.setString(1,rs.getString("ac_type"));
				ps.setString(2,rs.getString("ac_no"));
				

				ResultSet rs1=stat.executeQuery("select sd.*,m.lnk_shares from SecurityDetails sd,Modules m where type_of_security='"+rs.getString("emp_type")+"' and sd.modulecode=m.modulecode and sd.modulecode='"+ac_type+"' ");
				if(rs1.next())
				{    
					System.out.println("Inside EmploymentMaster"+rs1.getString("code"));
					ps.setString(3,rs1.getString("code"));
					ps.setString(4,rs1.getString("code"));
			    
					// double int_rate_sec=getODCCIntRate(rs1.getString("modulecode"),getSysDate(),category,period,amount);
					double int_rate_sec=getODCCIntRate(rs1.getString("modulecode"),fdate,tdate,category,period,amount,ac_no);//String ln_type,String fdate,String tdate,int category,int period,double amt,int ac_no)
					ps.setDouble(5,int_rate_sec);
					ps.setInt(6,int_type);// check int_type
					
					double eligible_amt=0;

					if(rs1.getString("type_of_security").equals("Business"))
						eligible_amt=rs1.getDouble("perc_ln_availed")*rs.getDouble("net_month_income")/100;
					else
						eligible_amt=rs1.getDouble("perc_ln_availed")*rs.getDouble("net_month_income");

					ps.setDouble(8,eligible_amt);

					double int_amt=(eligible_amt*int_rate_sec)/100;
			    		System.out.println("int amount "+int_amt);
			    	ps.setDouble(9,int_amt);
				}
				ps.setDouble(7,rs.getDouble("net_month_income"));
				//ps.addBatch();
				ps.executeUpdate();
			}
		
		rs=statement.executeQuery("select * from GoldDetMaster where ac_type='"+ac_type+"' and ac_no="+ac_no+"");
		if(rs.next())
		{
			
		    ps.setString(1,rs.getString("ac_type"));
		    ps.setString(2,rs.getString("ac_no"));

		    ResultSet rs1=stat.executeQuery("select sd.*,m.lnk_shares from SecurityDetails sd,Modules m where type_of_security='Gold' and sd.modulecode=m.modulecode");
		    if(rs1.next())
		    {	
		    	System.out.println("Inside GoldDetMaster"+rs1.getString("code"));
		    	ps.setString(3,rs1.getString("code"));
		    	ps.setString(4,rs1.getString("code"));
		    				    
		    	double eligible_amt=rs1.getDouble("perc_ln_availed")*(rs.getDouble("rate"))/100;		    	
		    	
		    	double int_rate_sec=getODCCIntRate(rs1.getString("modulecode"),fdate,tdate,category,period,eligible_amt,ac_no);
			    ps.setDouble(5,int_rate_sec);
			    ps.setInt(6,int_type);// check int_type
		    	
			    ps.setDouble(8,eligible_amt);
			    double int_amt=(eligible_amt*int_rate_sec)/100;
			    ps.setDouble(9,int_amt);
		    }
		    ps.setDouble(7,rs.getDouble("rate"));
		    
			//ps.addBatch();
		    ps.executeUpdate();
		}			    
		/*if(rs.next())
		{
		    ps.setString(1,rs.getString("ac_type"));
		    ps.setString(2,rs.getString("ac_no"));

		    ResultSet rs1=stat.executeQuery("select sd.*,m.lnk_shares from SecurityDetails sd,Modules m where type_of_security='Gold' and sd.modulecode=m.modulecode");
		    if(rs1.next())
		    {	
		    	ps.setString(3,rs1.getString("code"));
		    	ps.setString(4,rs1.getString("code"));
			    //double int_rate_sec=getODCCIntRate(rs1.getString("modulecode"),getSysDate(),category,period,amount);
		    	double int_rate_sec=getODCCIntRate(rs1.getString("modulecode"),fdate,tdate,category,period,amount,ac_no);
			    ps.setDouble(5,int_rate_sec);
			    ps.setInt(6,int_type);// check int_type
			    double eligible_amt=rs1.getDouble("lnk_shares")*(rs.getDouble("rate")*rs.getInt("netgold"))/100;
			    ps.setDouble(8,eligible_amt);
			    double int_amt=(eligible_amt*int_rate_sec)/100;
			    ps.setDouble(9,int_amt);
		    }
		    
		    double net_rate=rs.getDouble("rate")*rs.getInt("netgold");
		    while(rs.next())
		    	net_rate+=(rs.getDouble("rate")*rs.getInt("netgold"));
		    
		    ps.setDouble(7,net_rate);
		    
			ps.addBatch();
			    
		}*/    

		rs=statement.executeQuery("select * from PropertyMaster where ac_type='"+ac_type+"' and ac_no="+ac_no+"");			    
		if(rs.next())
		{			
		    ps.setString(1,rs.getString("ac_type"));
		    ps.setString(2,rs.getString("ac_no"));

		    ResultSet rs1=stat.executeQuery("select sd.*,m.lnk_shares from SecurityDetails sd,Modules m where type_of_security='Property' and sd.modulecode=m.modulecode");
		    if(rs1.next())
		    {	
		    	System.out.println("Inside PropertyMaster"+rs1.getString("code"));
		    	ps.setString(3,rs1.getString("code"));
		    	ps.setString(4,rs1.getString("code"));
			    //double int_rate_sec=getODCCIntRate(rs1.getString("modulecode"),getSysDate(),category,period,amount);
		    	
			    ps.setInt(6,int_type);// check int_type
			    ps.setDouble(7,rs.getDouble("market_value"));
			    //double eligible_amt=rs1.getDouble("lnk_shares")*rs.getDouble("market_value")/100;
			    double eligible_amt=rs1.getDouble("perc_ln_availed")*rs.getDouble("market_value")/100;
			    double int_rate_sec=getODCCIntRate(rs1.getString("modulecode"),fdate,tdate,category,period,eligible_amt,ac_no);
			    ps.setDouble(5,int_rate_sec);
			    ps.setDouble(8,eligible_amt);
			    double int_amt=(eligible_amt*int_rate_sec)/100;
			    ps.setDouble(9,int_amt);
		    }    
			//ps.addBatch();
		    ps.executeUpdate();
			    
		}    
			
		rs=statement.executeQuery("select * from VehicleMaster where ac_type='"+ac_type+"' and ac_no="+ac_no+"");
		if(rs.next())
		{
		    ps.setString(1,rs.getString("ac_type"));
		    ps.setString(2,rs.getString("ac_no"));

		    ResultSet rs1=stat.executeQuery("select sd.*,m.lnk_shares from SecurityDetails sd,Modules m where type_of_security='Vehicle' and sd.modulecode=m.modulecode");
		    if(rs1.next())
		    {	
		    	System.out.println("Inside VehicleMaster"+rs1.getString("code"));
		    	ps.setString(3,rs1.getString("code"));
		    	ps.setString(4,rs1.getString("code"));

		    	//double int_rate_sec=getODCCIntRate(rs1.getString("modulecode"),getSysDate(),category,period,amount);
		    	
			    ps.setInt(6,int_type);// check int_type
			    ps.setDouble(7,rs.getDouble("cost"));
			    //double eligible_amt=rs1.getDouble("lnk_shares")*rs.getDouble("cost")/100;
			    double eligible_amt=rs1.getDouble("perc_ln_availed")*rs.getDouble("cost")/100;
			    ps.setDouble(8,eligible_amt);
			    
			    double int_rate_sec=getODCCIntRate(rs1.getString("modulecode"),fdate,tdate,category,period,eligible_amt,ac_no);
			    ps.setDouble(5,int_rate_sec);

			    double int_amt=(eligible_amt*int_rate_sec)/100;
			    ps.setDouble(9,int_amt);
		    }    
			//ps.addBatch();
		    ps.executeUpdate();
		}
		
		rs=statement.executeQuery("select * from BorrowerMaster bm where ln_ac_type='"+ac_type+"' and ln_ac_no="+ac_no+" and type='D'");					    
		while(rs.next())
		{
			System.out.println("Inside BorrowerMaster"+rs.getString("ac_type"));
		    ps.setString(1,rs.getString("ln_ac_type"));
		    ps.setString(2,rs.getString("ln_ac_no"));
	    	ps.setString(3,rs.getString("ac_type"));
	    	ps.setString(4,rs.getString("ac_no"));
	    	double int_rate_sec=0;
	    	if(rs.getString("ac_type").startsWith("1003") ||rs.getString("ac_type").startsWith("1004")||rs.getString("ac_type").startsWith("1005"))
	    	{	
	    		int_rate_sec=getDepositCurrentIntRate(rs.getString("ac_type"),rs.getInt("ac_no"));
			    ps.setDouble(5,int_rate_sec);
			    // later if deposits is made interest calculation type as floating make it 
			    //floating(1) or constant(0)
			    
			    ps.setInt(6,int_type);			// checct interest calculation type.......Need to Change(imp****)
			    
			    ResultSet rs1=stat.executeQuery("select dm.*,m.lnk_shares from DepositMaster dm,Modules m where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and dm.ac_type=m.modulecode");
			    if(rs1.next())
			    {  	
			    	ps.setDouble(7,rs1.getDouble("dep_amt"));
			    	double eligible_amt=rs1.getDouble("lnk_shares")*rs1.getDouble("dep_amt")/100;
				    ps.setDouble(8,eligible_amt);

				    double int_amt=(eligible_amt*int_rate_sec)/100;
					ps.setDouble(9,int_amt);
			    }
	    	}	
	    	else
	    	{	
	    		//int_rate_sec=getPygmyCurrentIntRate(rs.getString("ac_type"),getSysDate(),amount);
	    		int_rate_sec=getPygmyCurrentIntRate(rs.getString("ac_type"),rs.getInt("ac_no"),tdate,amount);
			    ps.setDouble(5,int_rate_sec);
			    // later if deposits is made interest calculation type as floating make it 
			    //floating(1) or constant(0)
			    ps.setInt(6,int_type);// check int_type
			    stmt_pygmy=connection.createStatement();
			    ResultSet rs_pygmy=stmt_pygmy.executeQuery("select pm.*,m.lnk_shares,m.min_period from PygmyMaster pm,Modules m where pm.ac_type='"+rs.getString("ac_type")+"' and pm.ac_no="+rs.getInt("ac_no")+"  and pm.ac_type=m.modulecode ");
			    if(rs_pygmy.next())
			    {
			    	ResultSet rs1=stat.executeQuery("select pt.cl_bal from  PygmyTransaction pt where pt.ac_type='"+rs.getString("ac_type")+"' and pt.ac_no="+rs.getInt("ac_no")+" and pt.trn_seq ="+rs_pygmy.getString("lst_trn_seq")+" ");
			    	if(rs1.next())
			    	{  	
			    		ps.setDouble(7,rs1.getDouble("pt.cl_bal"));
			    		double eligible_amt=rs_pygmy.getDouble("lnk_shares")*rs1.getDouble("pt.cl_bal")/100;
			    		ps.setDouble(8,eligible_amt);
			    		double int_amt=(eligible_amt*int_rate_sec)/100;
			    		ps.setDouble(9,int_amt);
			    	}
			    }
	    	}
			//ps.addBatch();
	    	ps.executeUpdate();
		}
		//ps.executeBatch();
		}
			
		ResultSet rs=statement.executeQuery("select * from ODInterestDetails where ac_type='"+ac_type+"' and ac_no="+ac_no+" order by ln_int_rate asc,eligible_amt desc");
		double left_amount=amount;
		double bef_left_amount=amount;
		double int_amt=0;
		
		while(rs.next())
		{
			int_amt=0;
			bef_left_amount=left_amount;
			left_amount=left_amount-rs.getDouble("eligible_amt");
			if(left_amount>=0)
			{
				int_amt=(rs.getDouble("eligible_amt")*rs.getDouble("ln_int_rate"))/100;	
				
				int_rate+=int_amt;
				
				if(left_amount==0)
					break;
			}
			else
			{	
				int_amt=(bef_left_amount*rs.getDouble("ln_int_rate"))/100;	

				int_rate+=int_amt;

				break;
			}	
		}
		if(int_type!=-1)			
			while(rs.next()) 
			{	
				System.out.println("Before Deleting ODINTERESTDETAILS.........."+rs.getString("sec_ac_type"));
				statement.executeUpdate("delete from ODInterestDetails where sec_ac_type='"+rs.getString("sec_ac_type")+"' and sec_ac_no="+rs.getInt("sec_ac_no")+"");				
			}
		
		System.out.println("int rate="+int_rate);
		int_rate=(int_rate/amount)*100;
		
//		calculateODInterestAmount(ac_type,ac_no,"12/03/2005","05/08/2005",amount,0,2,amount);
        
    }catch(Exception ex){
    	ctx.setRollbackOnly();
    	ex.printStackTrace();
    	}
    finally
    {
        try {
            connection.close();
        } catch (SQLException e) {
        
            e.printStackTrace();
        }
        
    }
    return int_rate;
}

/** For NPA Processing  on 27/07/06 
 * npa_towards==> 1.Principle Wise
 * 				  2.Interest Wise
 * 				  3.Inoperative Account Wise
 * 				  4.Combinations of any above 3, then Max npa_period is taken
 * 
 * */
public void ODCCNPAProcessing(String process_date,String npa_towards,int table,int ac_type,int start_ac_no,int end_ac_no,String uid,String utml )throws RemoteException,RecordsNotFoundException
{
	System.out.println("**************NPA CALCULATION FOR ODCC***************");
	System.out.println("Date="+process_date);
	System.out.println("NPA Type:  "+npa_towards);
	System.out.println("Ac Type= "+ac_type);
	System.out.println("Ac Start No: "+start_ac_no);
	System.out.println("Ac End No: "+end_ac_no);
		
	double closing_balance=0;
	String prn_overdues[]=null,prn_overdue_from=null,int_overdues[]=null,int_upto_date=null,inoperative_trn_date=null;
	String table_name=null,npa_towards_indicator=null,npa_date=null;	
	
	int prn_asset_code=0,int_asset_code=0,inoperative_asset_code=0;
	double prn_prov_amt=0,int_prov_amt=0,inoperative_prov_amt=0; 
	int asset_code=0,npa_days=0,npa_period=0;
	int prn_overdue_period=0,prn_overdue_days=0,int_overdue_days=0,int_overdue_period=0,inoperative_overdue_days=0,inoperative_overdue_period=0;
	double prov_amt=0,prn_overdue_amt=0,int_overdue_amt=0;	
	
	Connection conn=null;
	try
	{	
		conn=getConnection();
		PreparedStatement pstmt;
		Statement stmt_mst=null,stmt_npa=null,stmt_asset=null,stmt_clbal=null;
		ResultSet rs_mst=null,rs_asset=null,rs_clbal=null;
		stmt_mst=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);		
		stmt_npa=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);		
		stmt_asset=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		stmt_clbal=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		
		pstmt=conn.prepareStatement("insert into NPATable values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		if(String.valueOf(ac_type).startsWith("0"))
			//rs_mst = stmt_mst.executeQuery("select distinct ac_type,ac_no,sub_category from ODCCMaster as od ,CustomerMaster as cm where concat(right(sanc_dt,4),'-',mid(sanc_dt,locate('/',sanc_dt)+1, (locate('/',sanc_dt,4)-locate('/',sanc_dt)-1)),'-',left(sanc_dt,locate('/',sanc_dt)-1))<='"+Validations.convertYMD(process_date)+"' and (ac_closedate is null or  concat(right(limit_upto,4),'-',mid(limit_upto,locate('/',limit_upto)+1, (locate('/',limit_upto,4)-locate('/',limit_upto)-1)),'-',left(limit_upto,locate('/',limit_upto)-1))>='"+Validations.convertYMD(process_date)+"') and od.cid=cm.cid and od.ve_tml is not null and loan_sanc='Y' and sanc_ver like 'Y' and ac_type='"+ac_type+"' order by ac_type,ac_no");
			//rs_mst = stmt_mst.executeQuery("select distinct ac_type,ac_no,sub_category from ODCCMaster as od ,CustomerMaster as cm where concat(right(sanc_dt,4),'-',mid(sanc_dt,locate('/',sanc_dt)+1, (locate('/',sanc_dt,4)-locate('/',sanc_dt)-1)),'-',left(sanc_dt,locate('/',sanc_dt)-1))<='"+Validations.convertYMD(process_date)+"' and (ac_closedate is null or  ac_status='O') and od.cid=cm.cid and od.ve_tml is not null and loan_sanc='Y' and sanc_ver like 'Y' and ac_type='"+ac_type+"' order by ac_type,ac_no");
			rs_mst = stmt_mst.executeQuery("select distinct ac_type,ac_no,int_uptodt,period,int_rate_type,int_rate,sanc_amt,custtype,sub_category from ODCCMaster as od ,CustomerMaster as cm where concat(right(sanc_dt,4),'-',mid(sanc_dt,locate('/',sanc_dt)+1, (locate('/',sanc_dt,4)-locate('/',sanc_dt)-1)),'-',left(sanc_dt,locate('/',sanc_dt)-1))<='"+Validations.convertYMD(process_date)+"' and (ac_closedate is null or  ac_status='O') and od.cid=cm.cid and od.ve_tml is not null and loan_sanc='Y' and sanc_ver like 'Y' and ac_type='"+ac_type+"' order by ac_type,ac_no");
		else
			//rs_mst = stmt_mst.executeQuery("select distinct ac_type,ac_no ,sub_category from ODCCMaster as om ,CustomerMaster as cm where concat(right(sanc_dt,4),'-',mid(sanc_dt,locate('/',sanc_dt)+1, (locate('/',sanc_dt,4)-locate('/',sanc_dt)-1)),'-',left(sanc_dt,locate('/',sanc_dt)-1))<='"+Validations.convertYMD(process_date)+"' and (ac_closedate is null or  concat(right(limit_upto,4),'-',mid(limit_upto,locate('/',limit_upto)+1, (locate('/',limit_upto,4)-locate('/',limit_upto)-1)),'-',left(limit_upto,locate('/',limit_upto)-1))>='"+Validations.convertYMD(process_date)+"') and om.cid=cm.cid and om.ve_tml is not null and loan_sanc='Y' and sanc_ver like 'Y' and ac_type like '"+ac_type+"' and ac_no between "+start_ac_no+" and "+end_ac_no+" order by ac_type,ac_no");
			//rs_mst = stmt_mst.executeQuery("select distinct ac_type,ac_no ,sub_category from ODCCMaster as om ,CustomerMaster as cm where concat(right(sanc_dt,4),'-',mid(sanc_dt,locate('/',sanc_dt)+1, (locate('/',sanc_dt,4)-locate('/',sanc_dt)-1)),'-',left(sanc_dt,locate('/',sanc_dt)-1))<='"+Validations.convertYMD(process_date)+"' and (ac_closedate is null or  ac_status='O') and om.cid=cm.cid and om.ve_tml is not null and loan_sanc='Y' and sanc_ver like 'Y' and ac_type like '"+ac_type+"' and ac_no between "+start_ac_no+" and "+end_ac_no+" order by ac_type,ac_no");
			rs_mst = stmt_mst.executeQuery("select distinct ac_type,ac_no ,int_uptodt,period,int_rate_type,int_rate,sanc_amt,custtype,sub_category from ODCCMaster as om ,CustomerMaster as cm where concat(right(sanc_dt,4),'-',mid(sanc_dt,locate('/',sanc_dt)+1, (locate('/',sanc_dt,4)-locate('/',sanc_dt)-1)),'-',left(sanc_dt,locate('/',sanc_dt)-1))<='"+Validations.convertYMD(process_date)+"' and (ac_closedate is null or  ac_status='O') and om.cid=cm.cid and om.ve_tml is not null and loan_sanc='Y' and sanc_ver like 'Y' and ac_type like '"+ac_type+"' and ac_no between "+start_ac_no+" and "+end_ac_no+" order by ac_type,ac_no");
		
		while(rs_mst.next())
		{	
			System.out.println("MASTER ACTYPE="+rs_mst.getString("ac_type")+"\n MASTER AC NO="+rs_mst.getString("ac_no")+" PROCESS DATE="+process_date );
			
			/**  Principal Wise */
			if(npa_towards.equalsIgnoreCase("P") || npa_towards.equalsIgnoreCase("PI") || npa_towards.equalsIgnoreCase("PO") || npa_towards.equalsIgnoreCase("0")||npa_towards.equalsIgnoreCase("PIO"))
			{					
				prn_overdues = null;		
				prn_overdues = getPrincipleOverDue(rs_mst.getString("ac_type"),rs_mst.getInt("ac_no"),process_date);
				System.out.println("PRINCIPLE ==> cl_bal="+prn_overdues[0]+" date="+prn_overdues[1]);
			
				if(prn_overdues!=null && prn_overdues[0].equalsIgnoreCase("0"))
				{
					prn_overdue_from=process_date;
					prn_overdue_amt=0.0;
					prn_overdue_period=0;
					prn_overdue_days=0;
				}
				else
				{
					prn_overdue_amt=Double.parseDouble(prn_overdues[0].trim());
					prn_overdue_from=prn_overdues[1];
					prn_overdue_days= Validations.dayCompare(prn_overdue_from,process_date);
					prn_overdue_period=prn_overdue_from!=null?Validations.getDefaultMonths(prn_overdue_from,process_date):0;
				}
			}
			if(npa_towards.equalsIgnoreCase("I") || npa_towards.equalsIgnoreCase("PI") || npa_towards.equalsIgnoreCase("IO") || npa_towards.equalsIgnoreCase("0")||npa_towards.equalsIgnoreCase("PIO"))
			{
				/** Interest Wise */
				int_upto_date=null;int_overdue_days=0;int_overdue_period=0;int_overdue_amt=0;
				//int_upto_date = getInterestOverDue(rs_mst.getString("ac_type"),rs_mst.getInt("ac_no"),process_date);
				int_overdues=getInterestOverDue(rs_mst.getString("ac_type"),rs_mst.getInt("ac_no"),rs_mst.getString("int_uptodt"),process_date,rs_mst.getDouble("sanc_amt"),rs_mst.getInt("int_rate_type"),rs_mst.getInt("int_rate"),rs_mst.getInt("custtype"),rs_mst.getInt("period"));
				
				if(int_overdues!=null)
				{
				int_upto_date = int_overdues[0];
				int_overdue_amt= Double.parseDouble(int_overdues[1]);
				System.out.println("INTEREST ==>ac _no "+rs_mst.getInt("ac_no")+" int_up_to_date"+int_upto_date+" int_amt="+int_overdue_amt);
				if(int_upto_date!=null)
				{				
					int_overdue_days=Validations.dayCompare(int_upto_date,process_date);
					int_overdue_period=Validations.getDefaultMonths(int_upto_date,process_date);
				}
				}
			}
			if(npa_towards.equalsIgnoreCase("O") || npa_towards.equalsIgnoreCase("PO") || npa_towards.equalsIgnoreCase("IO") || npa_towards.equalsIgnoreCase("PIO")||npa_towards.equalsIgnoreCase("0"))
			{
				/** InOperative Wise */	
				inoperative_trn_date=null;inoperative_overdue_days=0;inoperative_overdue_period=0;
				inoperative_trn_date = getInoperativeOverDue(rs_mst.getString("ac_type"),rs_mst.getInt("ac_no"),process_date);
				System.out.println("INOPERATIVE ==>ac _no "+rs_mst.getInt("ac_no")+" inoperative date"+inoperative_trn_date);
				if(inoperative_trn_date!=null)
				{				
					inoperative_overdue_days=Validations.dayCompare(inoperative_trn_date,process_date);
					inoperative_overdue_period=Validations.getDefaultMonths(inoperative_trn_date,process_date);
				}
			}
			rs_clbal = stmt_clbal.executeQuery("select cl_bal from ODCCTransaction where ac_type='"+rs_mst.getString("ac_type")+"' and ac_no="+rs_mst.getInt("ac_no")+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(process_date)+"' and ve_tml is not null order by trn_seq  desc limit 1");
			if(rs_clbal.next() && rs_clbal.getDouble("cl_bal")!=0)
				if(rs_clbal.getDouble("cl_bal")>0)
					closing_balance = rs_clbal.getDouble("cl_bal");   // Closing balance on the processing date
				else
					closing_balance = -rs_clbal.getDouble("cl_bal");
			
			if(table==90)
				table_name="NPAClassification_90";
			else
				table_name="NPAClassification_180";
						
			if(prn_overdue_period >=int_overdue_period && prn_overdue_period >=inoperative_overdue_period )
			{
				npa_days=prn_overdue_days;
				npa_period=prn_overdue_period;
				npa_towards_indicator ="P";
			}
			else if(int_overdue_period >prn_overdue_period && int_overdue_period >inoperative_overdue_period )
			{
				npa_days=int_overdue_days;
				npa_period=int_overdue_period;
				npa_towards_indicator ="I";
			}
			else if(inoperative_overdue_period >prn_overdue_period && inoperative_overdue_period >int_overdue_period )
			{
				npa_days=inoperative_overdue_days;
				npa_period=inoperative_overdue_period;
				npa_towards_indicator ="O";
			}
			
			System.out.println("#$%ac_no="+rs_mst.getInt("ac_no")+" npa_days:"+npa_days+" npa_mths:"+npa_period);
			
			rs_asset = stmt_asset.executeQuery("select asset_code,prov_perc from "+table_name+" where loan_type='"+rs_mst.getString("ac_type")+"' and "+prn_overdue_days+" between days_limit_fr and days_limit_to");
			if(!rs_asset.next())
			{
				rs_asset = stmt_asset.executeQuery("select asset_code,prov_perc from "+table_name+" where loan_type='"+rs_mst.getString("ac_type")+"' and "+prn_overdue_period+" between mths_limit_fr and mths_limit_to");
				if(!rs_asset.next())
				{
					rs_asset = stmt_asset.executeQuery("select asset_code,prov_perc from "+table_name+" where loan_type='"+rs_mst.getString("ac_type")+"' and days_limit_to is null and mths_limit_fr is null");
					if(rs_asset!=null && rs_asset.next())
					{
						asset_code = rs_asset.getInt("asset_code");
						prov_amt = (closing_balance*rs_asset.getDouble("prov_perc")/100);
					}
				}					
				else
				{
					asset_code = rs_asset.getInt("asset_code");
					prov_amt = (closing_balance*rs_asset.getDouble("prov_perc")/100);
				}
			}
			else 
			{
				asset_code = rs_asset.getInt("asset_code");
				prov_amt = (closing_balance*rs_asset.getDouble("prov_perc")/100);
			}
				
			rs_asset = stmt_asset.executeQuery("select asset_code,npa_towards,prov_amt from NPATable where ac_type='"+rs_mst.getString("ac_type")+"' and ac_no="+rs_mst.getInt("ac_no")+" and days="+table+" and concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1, (locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(process_date)+"' ");
			if(rs_asset.next())
			{
				if(rs_asset.getInt("asset_code")>asset_code)
				{
					asset_code=rs_asset.getInt("asset_code");
					prov_amt=rs_asset.getDouble("prov_amt");
					npa_towards_indicator=rs_asset.getString("npa_towards");
				}
				else if(rs_asset.getInt("asset_code")==asset_code && rs_asset.getString("npa_towards")!=null && rs_asset.getString("npa_towards").equalsIgnoreCase("P"))
				{
					asset_code=rs_asset.getInt("asset_code");
					prov_amt=rs_asset.getDouble("prov_amt");
					npa_towards_indicator=rs_asset.getString("npa_towards");
				}				
				stmt_npa.executeUpdate("delete from NPATable where ac_type='"+rs_mst.getString("ac_type")+"' and ac_no="+rs_mst.getInt("ac_no")+" and days="+table+" and concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1, (locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(process_date)+"' ");
			}
			
			pstmt.setString(1,process_date);
			pstmt.setInt(2,table);
			pstmt.setString(3,rs_mst.getString("ac_type"));
			pstmt.setInt(4,rs_mst.getInt("ac_no"));
			pstmt.setInt(5,asset_code);
			pstmt.setString(6,npa_towards_indicator);
			pstmt.setString(7,prn_overdue_from);
			pstmt.setDouble(8,prn_overdue_amt);
			pstmt.setInt(9,prn_overdue_period);
			pstmt.setDouble(10,int_overdue_amt);
			pstmt.setString(11,int_upto_date);
			pstmt.setInt(12,int_overdue_period);
			pstmt.setDouble(13,closing_balance);
			pstmt.setDouble(14,prov_amt);
			pstmt.setString(15,inoperative_trn_date);
			
			if(pstmt.executeUpdate()>0)
			{
				rs_asset = stmt_asset.executeQuery("select npa_dt from ODCCMaster where ac_type='"+rs_mst.getString("ac_type")+"' and ac_no="+rs_mst.getInt("ac_no")+" ");
				if(rs_asset.next())
				{
					npa_date=rs_asset.getString("npa_dt");
					if(npa_date==null || Validations.dayCompare(process_date,npa_date)<=0)
						stmt_asset.executeUpdate("update ODCCMaster set npa_dt='"+process_date+"' ,npa_stg='"+npa_towards_indicator+" "+asset_code+"' where ac_type='"+rs_mst.getString("ac_type")+"' and ac_no="+rs_mst.getInt("ac_no")+" ");				
				}
			}		
		}
	}catch(SQLException sql){
		ctx.setRollbackOnly();
		sql.printStackTrace();
		}	
	finally{
		try{
			conn.close();
		}catch(Exception ex){ex.printStackTrace();}
	}
}

/** This method is used to calculate the Principal overdue_date and principal overdue_amount if any for
* an account on a given day
* 
* @param String ac_type
* @param int ac_no
* @param String date
* 
* @return String[2], ie [1] --> overdue_amount, [2] --> overdue_date
*  */
public String[] getPrincipleOverDue(String ac_type,int ac_no,String processdate)
{
	System.out.println("Inside getPrincipleOverDue");
	System.out.println("Ac_Type="+ac_type);
	System.out.println("Ac No="+ac_no);
	System.out.println("Processing date="+processdate);
	Connection conn=null;
	ResultSet rs_trn=null,rs_stock=null;
	Statement stmt_trn=null,stmt_stock=null;
	String return_values[]= new String[2];
	String prev_insp_date="";
	double credit_limit=0,clbal_amt=0;
	String prn_overdue_date=null;	
	
	try
	{
		conn=getConnection();		
		stmt_trn=conn.createStatement();
		stmt_stock=conn.createStatement();				
	
		/** To get creditlimit from StockInspectionDetails for CC */
		if(ac_type.startsWith("1014"))
		{	
			rs_stock=stmt_stock.executeQuery("select insp_date,credit_limit from StockInspectionDetails where ac_type='"+ac_type+"' and ac_no="+ac_no+" and concat(right(insp_date,4),'-',mid(insp_date,locate('/',insp_date)+1, (locate('/',insp_date,4)-locate('/',insp_date)-1)),'-',left(insp_date,locate('/',insp_date)-1))<='"+Validations.convertYMD(processdate)+"' order by concat(right(insp_date,4),'-',mid(insp_date,locate('/',insp_date)+1, (locate('/',insp_date,4)-locate('/',insp_date)-1)),'-',left(insp_date,locate('/',insp_date)-1)) desc");
			outside:if(rs_stock!=null && rs_stock.next())
			{		
				//rs_stock.next();
				credit_limit=rs_stock.getDouble("credit_limit");
				rs_stock.last();
				int rows=rs_stock.getRow();
				rs_stock.beforeFirst();
				
				int i=0;
				stock:while(i<=rows)
				{						
					rs_stock.next();
					if(i<rows-1)
					{						
						rs_stock.next();
						prev_insp_date=Validations.convertYMD(rs_stock.getString("insp_date"));
						rs_stock.previous();
					}										
					System.out.println("From date==="+prev_insp_date);
					System.out.println("To Date===="+rs_stock.getString("insp_date"));
					
					rs_trn=null;					
					rs_trn=stmt_trn.executeQuery("select trn_date,cl_bal from ODCCTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+"  and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+prev_insp_date+"'and '"+Validations.convertYMD(rs_stock.getString("insp_date"))+"' and ve_tml is not null order by trn_seq desc,concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) desc");
					rs_trn.last();
					int trn_rows=rs_trn.getRow();
					rs_trn.beforeFirst();
					System.out.println("TRN ROWS+="+trn_rows);
													
					if(trn_rows<=0)
					{
						System.out.println("FOR NULLLLLLLLLLLLLLLLLLLLLLLLLLL");												
						i++;
						continue stock;
					}								
					else
					{										
						int j=0;
						trn:while(j<=trn_rows)
						{	
							if(j<trn_rows)
								rs_trn.next();
							
							System.out.println("Closing bal"+rs_trn.getDouble("cl_bal"));
							System.out.println("Credit="+credit_limit);
							
							if(-(rs_trn.getDouble("cl_bal"))<credit_limit)
							{
								//rs_trn.previous();				
								clbal_amt=(rs_trn.getDouble("cl_bal"));
								prn_overdue_date=rs_trn.getString("trn_date");
								/*clbal_amt=0;
								prn_overdue_date=null;*/
								System.out.println("Closing balance (cl_bal<credit_limit)="+clbal_amt);
								break outside;
							}
							else
							{												
								j++;
								continue trn;
							}							
						}
					}
				}
			}
			else
			{				
				System.out.println("Not StockInspectionDetails.........");
				rs_trn=stmt_trn.executeQuery("select trn_date,cl_bal,creditlimit from ODCCTransaction ot,ODCCMaster om  where om.ac_type='"+ac_type+"' and om.ac_no="+ac_no+" and om.ac_type=ot.ac_type and om.ac_no=ot.ac_no and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(processdate)+"' and ot.ve_tml is not null order by trn_seq desc,concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) desc");
				while(rs_trn.next())
				{					
					if(-(rs_trn.getDouble("cl_bal"))<credit_limit || rs_trn.getDouble("cl_bal")==0)
					{						
						clbal_amt=(rs_trn.getDouble("cl_bal"));
						prn_overdue_date=rs_trn.getString("trn_date");
						
						System.out.println("Closing balance (closing bal<credit)="+clbal_amt);
						break;
					}
					else
					{					
						clbal:while(-(rs_trn.getDouble("cl_bal"))>rs_trn.getDouble("creditlimit"))
						{
							if(-(rs_trn.getDouble("cl_bal"))>rs_trn.getDouble("creditlimit"))
							{							
								System.out.println("Condition closing bal>credit"+(-(rs_trn.getDouble("cl_bal"))));							
								rs_trn.next();
								if(rs_trn==null)
								{
									rs_trn.previous();
									clbal_amt=(rs_trn.getDouble("cl_bal"));
									prn_overdue_date=rs_trn.getString("trn_date");
									break;
								}
								else
									continue clbal;												
							}
						}
					}			
				}
			}
			return_values[0]=String.valueOf(clbal_amt);
			return_values[1]=prn_overdue_date;
			System.out.println("CC CL_BAL="+clbal_amt);
			System.out.println("CC OVERDUE_DT="+prn_overdue_date);
			System.out.println("CC RETURN VALUES="+return_values[0]+" & "+return_values[1]);
		}
		else if(ac_type.startsWith("1015"))
		{
			rs_trn=stmt_trn.executeQuery("select trn_date,cl_bal,creditlimit from ODCCTransaction ot,ODCCMaster om  where om.ac_type='"+ac_type+"' and om.ac_no="+ac_no+" and om.ac_type=ot.ac_type and om.ac_no=ot.ac_no and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(processdate)+"' and ot.ve_tml is not null order by trn_seq desc,concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) desc");				
			while(rs_trn.next())
			{					
				if(-(rs_trn.getDouble("cl_bal"))<credit_limit)
				{						
					clbal_amt=(rs_trn.getDouble("cl_bal"));
					prn_overdue_date=rs_trn.getString("trn_date");
					/*clbal_amt=0;
					prn_overdue_date=null;*/
					System.out.println("Closing balance(closing bal<credit)="+clbal_amt);
					break;
				}
				else
				{					
					clbal:while(-(rs_trn.getDouble("cl_bal"))>rs_trn.getDouble("creditlimit"))
					{
						if(-(rs_trn.getDouble("cl_bal"))>rs_trn.getDouble("creditlimit"))
						{							
							System.out.println("Condition closing bal>credit"+(-(rs_trn.getDouble("cl_bal"))));							
							rs_trn.next();
							if(rs_trn==null)
							{
								rs_trn.previous();
								clbal_amt=(rs_trn.getDouble("cl_bal"));
								prn_overdue_date=rs_trn.getString("trn_date");
								break;
							}
							else
								continue clbal;												
						}
					}
				}			
			}			
			return_values[0]=String.valueOf(clbal_amt);
			return_values[1]=prn_overdue_date;
			System.out.println("OD CL_BAL="+clbal_amt);
			System.out.println("OD OVERDUE_DT="+prn_overdue_date);
			System.out.println("OD RETURN VALUES="+return_values[0]+" & "+return_values[1]);
		}				
	}catch(Exception e){
		e.printStackTrace();
	}
	finally{
		try{                
			conn.close();
		}catch(Exception exception){exception.printStackTrace();}
	}
	
	System.out.println("END RETURN VALUES="+return_values[0]+" & "+return_values[1]);
	return return_values;
}

public String[] getInterestOverDue(String ac_type,int ac_no,String from_date,String to_date,double sanc_amt,int rate_type,int rate,int custtype,int period)
{
	Connection conn=null;
	ResultSet rs_trn=null,rs=null;
	Statement stmt=null;
	String int_upto_date=null;
	String return_values[]= new String[2];
	double int_amt=0;
	double prev_clbal=0;
	try
	{
		conn=getConnection();
		stmt=conn.createStatement();
		//rs=stmt.executeQuery("select int_uptodt from ODCCTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and ve_tml is not null and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(date)+"' order by trn_seq desc limit 1");
		
		rs=stmt.executeQuery("select int_uptodt from ODCCTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(to_date)+"' and ve_tml is not null order by trn_seq desc,concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) desc limit 1");
		if(rs.next())
			int_upto_date=rs.getString("int_uptodt");
		
		int_amt=0;
		rs_trn=stmt.executeQuery("select trn_date,cl_bal from ODCCTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(from_date)+"' and '"+Validations.convertYMD(Validations.addDays(to_date,-1))+"' and ve_tml is not null order by trn_seq asc,concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))");
		int i=0;
		String lsttrndt="";
		
		while(rs_trn.next())
		{
			System.out.println("1............3");
			if(rate_type==1)	//For Floating
			{	
			    System.out.println("For Floating");
				if(i==0)
				{		
					System.out.println("i==0 "+rs_trn.getDouble("cl_bal")+"ac_type="+ac_type+" fromdate="+from_date+" todate="+Validations.addDays(rs_trn.getString("trn_date"),-1)+"custtype="+custtype+"period="+period+"ac_no="+ac_no);
					if(rs_trn.getDouble("cl_bal")<0)
					{	
						if(ac_type.startsWith("1014"))
							int_amt+=calculateInterest(ac_type,from_date,Validations.addDays(rs_trn.getString("trn_date"),-1),-(rs_trn.getDouble("cl_bal")),custtype,period,-(rs_trn.getDouble("cl_bal")),ac_no);
						else												
							int_amt+=calculateODInterestAmount(ac_type,ac_no,from_date,Validations.addDays(rs_trn.getString("trn_date"),-1),sanc_amt,custtype,period,-(rs_trn.getDouble("cl_bal")));							
													
					}
				}	
				else
				{	
					System.out.println("i!=0 prevclbal"+prev_clbal+"ac_type="+ac_type+" fromdate="+from_date+" todate="+Validations.addDays(rs_trn.getString("trn_date"),-1)+"custtype="+custtype+"period="+period+"ac_no="+ac_no);
					if(prev_clbal<0)
					{					   
						if(ac_type.startsWith("1014"))
							int_amt+=calculateInterest(ac_type,lsttrndt,Validations.addDays(rs_trn.getString("trn_date"),-1),-prev_clbal,custtype,period,-prev_clbal,ac_no);							
						else
							int_amt+=calculateODInterestAmount(ac_type,ac_no,lsttrndt,Validations.addDays(rs_trn.getString("trn_date"),-1),sanc_amt,custtype,period,-prev_clbal);														
					}	
				}					
			}
			else			//For Fixed
			{
				System.out.println("For Fixed");				
				if(i==0)
				{		
					System.out.println("i==0 "+rs_trn.getDouble("cl_bal")+"ac_type="+ac_type+" fromdate="+from_date+" todate="+Validations.addDays(rs_trn.getString("trn_date"),-1)+"custtype="+custtype+"period="+period+"ac_no="+ac_no);
					if(rs_trn.getDouble("cl_bal")<0)									   
						int_amt+=Math.round((-(rs_trn.getDouble("cl_bal"))*Validations.dayCompare(from_date,Validations.addDays(rs_trn.getString("trn_date"),-1))*rate)/(365*100));
				}	
				else
				{ 
					System.out.println("i!=0  prevclbal"+prev_clbal+"ac_type="+ac_type+" fromdate="+from_date+" todate="+Validations.addDays(rs_trn.getString("trn_date"),-1)+"custtype="+custtype+"period="+period+"ac_no="+ac_no);
					if(prev_clbal<0)
						int_amt+=Math.round((-prev_clbal*Validations.dayCompare(lsttrndt,Validations.addDays(rs_trn.getString("trn_date"),-1))*rate)/(365*100));						
				}
			}

			lsttrndt=rs_trn.getString("trn_date");
			prev_clbal=rs_trn.getDouble("cl_bal");			
		}
		if(rs_trn.previous())
		{				
			if(rate_type==1)
			{	
				System.out.println("rate type= "+rate_type+"prev clbal"+prev_clbal+"ac_type="+ac_type+" fromdate="+from_date+" todate="+Validations.addDays(rs_trn.getString("trn_date"),-1)+"custtype="+custtype+"period="+period+"ac_no="+ac_no);
				if(prev_clbal<0)
				{				    
					if(ac_type.startsWith("1014"))
						int_amt+=calculateInterest(ac_type,lsttrndt,Validations.addDays(to_date,-1),-prev_clbal,custtype,period,-prev_clbal,ac_no);
					else
						int_amt+=calculateODInterestAmount(ac_type,ac_no,lsttrndt,Validations.addDays(to_date,-1),sanc_amt,custtype,period,-prev_clbal);						
				}	
			}
			else
			{
				if(prev_clbal<0)
					int_amt+=Math.round((-prev_clbal*Validations.dayCompare(lsttrndt,Validations.addDays(to_date,-1))*rate)/(365*100));				
			}			
		}
		return_values[0]= int_upto_date;
		return_values[1] = String.valueOf(int_amt);
		
		System.out.println(" INTEREST ***** ac_type:"+ac_type+" ac_no:"+ac_no+" date:"+int_upto_date+" amt:"+int_amt);
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

public String getInoperativeOverDue(String ac_type,int ac_no,String date)
{
	Connection conn=null;
	ResultSet rs_trn=null,rs_stock=null;
	Statement stmt_trn=null,stmt_stock=null;
	String inoperative_date=null;
	double cl_bal=0,credit=0;
	try
	{
		conn=getConnection();
		stmt_trn=conn.createStatement();
		stmt_stock=conn.createStatement();
		
		rs_trn=stmt_trn.executeQuery("select trn_date,cl_bal,creditlimit from ODCCTransaction ot,ODCCMaster om  where om.ac_type='"+ac_type+"' and om.ac_no="+ac_no+" and om.ac_type=ot.ac_type and om.ac_no=ot.ac_no and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(date)+"' and ot.ve_tml is not null order by trn_seq desc,concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) desc limit 1");
		
		if(rs_trn.next())
		{	
			cl_bal=-rs_trn.getDouble("cl_bal");
			credit=rs_trn.getDouble("creditlimit");
			if(ac_type.startsWith("1014"))
			{
				rs_stock=stmt_stock.executeQuery("select credit_limit from StockInspectionDetails where ac_type='"+ac_type+"' and ac_no="+ac_no+" and concat(right(insp_date,4),'-',mid(insp_date,locate('/',insp_date)+1, (locate('/',insp_date,4)-locate('/',insp_date)-1)),'-',left(insp_date,locate('/',insp_date)-1))<='"+Validations.convertYMD(date)+"' order by concat(right(insp_date,4),'-',mid(insp_date,locate('/',insp_date)+1, (locate('/',insp_date,4)-locate('/',insp_date)-1)),'-',left(insp_date,locate('/',insp_date)-1)) desc limit 1");
				if(rs_stock!=null && rs_stock.next())
					credit=rs_stock.getDouble("credit_limit");								
			}
			//if(cl_bal>credit)
			inoperative_date=rs_trn.getString("trn_date");
		}
		
		System.out.println(" ac_type:"+ac_type+" ac_no:"+ac_no+" date:"+inoperative_date);
	}
	catch(Exception e){
		e.printStackTrace();
	}
	finally{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
	}
	return inoperative_date;
}

/** To Get NPA Report for OD & CC Accounts **/
public LoanReportObject[] getODCCNPAReport(String process_date,String table_type,String asset_code,String ac_type,int from_acno,int to_acno)
{
	System.out.println("*************Inside getODCCNPAReport**************");
	Connection conn=null;
	LoanReportObject loan_obj[]=null;
	int rows=0;
	try	
	{
		conn=getConnection();
		Statement stmt=conn.createStatement();
		Statement stmt_inst=conn.createStatement();
		ResultSet rs=null,rs_inst=null;
			
		if(asset_code!=null && asset_code.equalsIgnoreCase("-1"))
			asset_code="%";
		if(from_acno==-1 && to_acno==-1)
			rs=stmt.executeQuery("select npa.*,concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,sanc_amt,sanc_dt,(((right(npa_pro_date,4)-right(pr_odue_from,4))*12)+(mid(npa_pro_date,4,2)-mid(pr_odue_from,4,2))-if(left(npa_pro_date,2)<left(pr_odue_from,2),1,0)) as period_pri,(((right(npa_pro_date,4)-right(npa.int_upto_date,4))*12)+(mid(npa.npa_pro_date,4,2)-mid(npa.int_upto_date,4,2))-if(left(npa_pro_date,2)<left(npa.int_upto_date,2),1,0)) as period_int from NPATable as npa, CustomerMaster as cm,ODCCMaster as lm where concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1, (locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(process_date)+"' and days="+table_type+" and npa.ac_type like '"+ac_type+"' and asset_code like '"+asset_code+"' and npa.ac_type=lm.ac_type and npa.ac_no=lm.ac_no and lm.cid=cm.cid order by asset_code,npa.ac_type,npa.ac_no");
		else if(ac_type.startsWith("1014")||ac_type.startsWith("1015"))
			rs=stmt.executeQuery("select npa.*,concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,sanc_amt,sanc_dt,(((right(npa_pro_date,4)-right(pr_odue_from,4))*12)+(mid(npa_pro_date,4,2)-mid(pr_odue_from,4,2))-if(left(npa_pro_date,2)<left(pr_odue_from,2),1,0)) as period_pri,(((right(npa_pro_date,4)-right(npa.int_upto_date,4))*12)+(mid(npa.npa_pro_date,4,2)-mid(npa.int_upto_date,4,2))-if(left(npa_pro_date,2)<left(npa.int_upto_date,2),1,0)) as period_int from NPATable as npa, CustomerMaster as cm,ODCCMaster as lm where concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1, (locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(process_date)+"' and days="+table_type+" and npa.ac_type like '"+ac_type+"' and asset_code like '"+asset_code+"' and npa.ac_no between "+from_acno+" and "+to_acno+" and npa.ac_type=lm.ac_type and npa.ac_no=lm.ac_no and lm.cid=cm.cid order by asset_code,npa.ac_type,npa.ac_no");
		else if(ac_type.equalsIgnoreCase("ALL"))
			rs=stmt.executeQuery("select npa.*,concat_ws(IFNULL(cm.fname,' '),IFNULL(cm.mname,' '),IFNULL(cm.lname,' ')) as name,sanc_amt,sanc_dt,(((right(npa_pro_date,4)-right(pr_odue_from,4))*12)+(mid(npa_pro_date,4,2)-mid(pr_odue_from,4,2))-if(left(npa_pro_date,2)<left(pr_odue_from,2),1,0)) as period_pri,(((right(npa_pro_date,4)-right(npa.int_upto_date,4))*12)+(mid(npa.npa_pro_date,4,2)-mid(npa.int_upto_date,4,2))-if(left(npa_pro_date,2)<left(npa.int_upto_date,2),1,0)) as period_int from NPATable as npa, CustomerMaster as cm,ODCCMaster as lm where concat(right(npa_pro_date,4),'-',mid(npa_pro_date,locate('/',npa_pro_date)+1, (locate('/',npa_pro_date,4)-locate('/',npa_pro_date)-1)),'-',left(npa_pro_date,locate('/',npa_pro_date)-1))='"+Validations.convertYMD(process_date)+"' and days="+table_type+" and (npa.ac_type like '1014%' || npa.ac_type like '1015%') and asset_code like '"+asset_code+"' and npa.ac_no between "+from_acno+" and "+to_acno+" and npa.ac_type=lm.ac_type and npa.ac_no=lm.ac_no and lm.cid=cm.cid order by asset_code,npa.ac_type,npa.ac_no");
			
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
			loan_obj[rows].setDisbursementDate(rs.getString("sanc_dt"));			
						
			loan_obj[rows].setIntUptoDate(rs.getString("int_upto_date"));
			loan_obj[rows].setPrnOverDueAmt(rs.getDouble("pri_odue_amt"));
			loan_obj[rows].setIntOverDueAmt(rs.getDouble("int_odue_amt"));
			loan_obj[rows].setSanctionedAmount(rs.getDouble("sanc_amt"));			
						
			if(rs.getString("npa_towards").equalsIgnoreCase("P"))
			{
				loan_obj[rows].setNpatowards("Principal");
				loan_obj[rows].setOverdueperiod(rs.getInt("pri_odue_prd"));
				loan_obj[rows].setNpa_since_date(rs.getString("pr_odue_from"));
			}
			else if(rs.getString("npa_towards").equalsIgnoreCase("I"))
			{
				loan_obj[rows].setNpatowards("Interest");
				loan_obj[rows].setOverdueperiod(rs.getInt("int_odue_prd"));
				loan_obj[rows].setNpa_since_date(rs.getString("int_upto_date"));
			}
			else if(rs.getString("npa_towards").equalsIgnoreCase("O"))
			{
				loan_obj[rows].setNpatowards("InOperative");
				loan_obj[rows].setOverdueperiod(rs.getInt("pri_odue_prd"));
				loan_obj[rows].setNpa_since_date(rs.getString("pr_odue_from"));
			}
			
			/*rs_inst=stmt_inst.executeQuery("select trn_date,trn_amt from ODCCTransaction where ac_type = '"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" order by trn_seq desc limit 1");
			if(rs_inst.next())
				loan_obj[rows].setTransactionDate(rs_inst.getString("trn_date"));*/
			
			loan_obj[rows].setTransactionDate(rs.getString("last_trn_date"));
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

	private String getFromDay(String date,int day){
		String mm=null,yy=null;
		StringTokenizer st=new StringTokenizer(date,"/");
		st.nextToken();
		mm=st.nextToken();
		yy=st.nextToken();
		return yy+"-"+mm+"-"+String.valueOf(day);
	}
	private String getLastDay(String date,int day){
		String mm=null,yy=null;
		StringTokenizer st=new StringTokenizer(date,"/");
		st.nextToken();
		mm=st.nextToken();
		yy=st.nextToken();
		return String.valueOf(day)+"/"+mm+"/"+yy;
	}
	
	public String getLastIntPaidDate(String ac_type,int ac_no){
		Statement stmt1=null;
		ResultSet rs=null;
		Connection con=null;
		String paid_date=null;
		System.out.println("ac_type="+ac_type);
		try{
			con=getConnection();
			stmt1=con.createStatement();
			if(ac_no!=0){
				System.out.println("ac_no="+ac_no);
				if(ac_type.startsWith("1002") || ac_type.startsWith("1007") ||ac_type.startsWith("1018") || ac_type.startsWith("1017"))
				{
					rs=stmt1.executeQuery("select int_pbl_date from AccountMaster where ac_type="+ac_type+"  and ac_no="+ac_no+" ");
				}
				if(ac_type.startsWith("1014")|| ac_type.startsWith("1015"))
				{
					rs=stmt1.executeQuery("select int_uptodt from ODCCMaster where ac_no="+ac_no+" and ac_type='"+ac_type+"' ");
				}
			}
			else{
				System.out.println("all accounts");
				rs=stmt1.executeQuery("select lst_intdt from Modules where modulecode='"+ac_type+"'");
			}
			
			if(rs.next())
				paid_date=rs.getString(1);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		System.out.println("last int Paid date="+paid_date);
		return paid_date;
	}
	public String[] getNextIntDate(String ac_type,String today_date,String br_location){
		Statement stmt1=null,stmt2=null;
		ResultSet rs=null,rs1=null;
		Connection con=null;
		String last_date=null,ret_date[]=new String[2];
		int dd=0,mm=0,yy=0,br_code=0;
		
		boolean flag=true;
		StringTokenizer st=new StringTokenizer(today_date,"/");
			dd=Integer.parseInt(st.nextToken());
			mm=Integer.parseInt(st.nextToken());
			yy=Integer.parseInt(st.nextToken());
			
		try{
			con=getConnection();
			stmt1=con.createStatement();
			stmt2=con.createStatement();
			System.out.println("today date="+today_date);
			rs=stmt1.executeQuery("select * from QtrDefinition where hyr_defn='T' and month="+mm+" ");
			if(rs.next()){
				last_date=Validations.lastDayOfMonth(today_date);
				if(!(last_date.equals(today_date))){// last date not equals to today date check for holiday master for last date
					rs=stmt1.executeQuery("select br_code from BranchMaster where br_name like '"+br_location+"' ");
					//rs.next(); 
					while(rs.next()){
						rs1=stmt2.executeQuery("select * from HolidayMaster where br_code="+rs.getInt("br_code")+" and date='"+last_date+"'");
						if(rs1.next()){
							last_date=Validations.addDays(last_date,-1);
							System.out.println("last date in loop="+last_date);
							if(last_date.equals(today_date)){
								ret_date[0]="1";
								ret_date[1]=last_date;
								break;
							}
							else{
								ret_date[0]="-1";
								ret_date[1]=last_date;
							}
						}
						else{
							ret_date[0]="-1";
							ret_date[1]=last_date;
							break;
						}
					}
				}
				else{// last date equals today date //ok
					ret_date[0]="1";
					ret_date[1]=today_date;
				}
			}
			else{//no matching  month
				ret_date[0]="-1";
				ret_date[1]=today_date;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return ret_date; 
	}
	
	public String[][] getUnverifiedAccounts(String ac_type,String date){
		Statement stmt1=null;
		ResultSet rs=null;
		Connection con=null;
		String str[][]=null;
		int i=0;
		try{
			con=getConnection();
			stmt1=con.createStatement();
			if(ac_type.startsWith("1002") || ac_type.startsWith("1007") || ac_type.startsWith("1018") || ac_type.startsWith("1017") )
				rs=stmt1.executeQuery("select m.moduleabbr as ac_type ,ac_no,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,''))as name  from AccountMaster od,CustomerMaster cm ,Modules m where cm.cid=od.cid and m.modulecode=od.ac_type and ac_type ='"+ac_type+"' and od.ve_tml is null");
			else if(ac_type.startsWith("1014") || ac_type.startsWith("1015"))
				rs=stmt1.executeQuery("select m.moduleabbr as ac_type ,ac_no,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,''))as name  from ODCCMaster od,CustomerMaster cm ,Modules m where cm.cid=od.cid and m.modulecode=od.ac_type and ac_type ='"+ac_type+"' and od.ve_tml is null");
			
				if(rs.next()){
					rs.last();
					str=new String[rs.getRow()][3];
					rs.beforeFirst();
					i=0;
					while(rs.next()){
						str[i][0]=rs.getString("ac_type");
						str[i][1]=rs.getString("ac_no");
						str[i][2]=rs.getString("name");
						i++;
					}
				}
			}catch(Exception e){e.printStackTrace();}
			finally{
				try{                
					con.close();
				}catch(Exception exception){exception.printStackTrace();}
			}
			
			return str;
		}
	public String getInterest(String ac_type,int ac_no,String date){
		Statement stmt1=null;
		ResultSet rs=null;
		Connection con=null;
		String str="Interest Not Calculated";
		try{
			con=getConnection();
			stmt1=con.createStatement();
			if(ac_type.startsWith("1002") || ac_type.startsWith("1007")){
				rs=stmt1.executeQuery("select * from IntPay where ac_type='"+ac_type+"'  and ac_no="+ac_no+" and post_ind='F' and trn_date='"+date+"' order by concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))  desc limit 1");
				if(rs.next()){
					str="Interest Amount is "+rs.getString("trn_amt");
					return str;
				}
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return str;
	}
	public String[][] getLoanNotSancationedAccs(String ac_type,boolean flag)
	{
		Statement stmt1=null;
		ResultSet rs=null;
		Connection con=null;
		String str[][]=null;
		int i=0;
		try{
			con=getConnection();
			stmt1=con.createStatement();
			//flag==false data Entry not done for Sanction
			//flag==true data entry done for sanction
			if(!(flag) && (ac_type.startsWith("1015") || ac_type.startsWith("1014"))){
				rs=stmt1.executeQuery("select m.moduleabbr as ac_type ,ac_no,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,''))as name  from ODCCMaster od,CustomerMaster cm ,Modules m where cm.cid=od.cid and m.modulecode=od.ac_type and ac_type ='"+ac_type+"' and sanc_ver='N'  and ( od.ve_tml is not null   || od.ve_user  is not null) ");
			}
			else if(flag && (ac_type.startsWith("1015") || ac_type.startsWith("1014"))){
				rs=stmt1.executeQuery("select m.moduleabbr as ac_type ,ac_no,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,''))as name  from ODCCMaster od,CustomerMaster cm ,Modules m where cm.cid=od.cid and m.modulecode=od.ac_type and ac_type ='"+ac_type+"' and sanc_ver='N'  and sanc_amt!='' and ( od.ve_tml is not null   || od.ve_user  is not null)");
			}
				
			if(rs.next())
			{
				rs.last();
				str=new String[rs.getRow()][3];
				rs.beforeFirst();
				i=0;
				while(rs.next()){
					str[i][0]=rs.getString("ac_type");
					str[i][1]=rs.getString("ac_no");
					str[i][2]=rs.getString("name");
					i++;
				}
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return str;
	}
	
	public AdminObject[] getData(String tablename)
	{
		Statement stmt1=null;
		ResultSet rs=null;
		Connection con=null;
		Object str[][]=null;
		ResultSetMetaData meta_data=null;
		String[] col_names=null;
		String date=null;
		AdminObject admin_object[]=null;
		try{
			con=getConnection();
			stmt1=con.createStatement();
			if(tablename.equals("SavingsIntRate"))
				rs=stmt1.executeQuery("select 'Y/N',moduleabbr as ac_type ,s.date,s.int_rate,s.de_tml,s.de_user,s.de_date from SavingsIntRate s,Modules m where ac_type=m.modulecode ");
			else if(tablename.equals("SavingsCatRate"))
				//rs=stmt1.executeQuery("select 'Y/N',moduleabbr as ac_type ,s.date,s.extra_int_rt,subcatdesc,s.de_tml,s.de_user,s.de_date from SavingsCatRate s,Modules m,AccountSubCategory where ac_type=m.modulecode and category=subcatcode ");
				rs=stmt1.executeQuery("select 'Y/N',moduleabbr as ac_type ,s.date_fr,s.date_to,s.days_fr,days_to,s.extra_int_rt,subcatdesc,s.de_tml,s.de_user,s.de_date from SavingsCatRate s,Modules m,AccountSubCategory where ac_type=m.modulecode and category=subcatcode;");
			else if(tablename.equals("TokenNumbers"))
				rs=stmt1.executeQuery("select 'Y/N',tn.* from TokenNumbers tn");
			else if(tablename.equals("PayOrderCommission"))
				rs=stmt1.executeQuery("select 'Y/N',m.moduleabbr,c.custtype,p.fr_date,p.to_date,p.fr_amt,p.to_amt,p.comm_rate,p.comm_type,p.min_comm_amt,p.de_tml,p.de_user,p.de_date from PayOrderCommission p,Modules m,CustomerType c  where custcode=cust_type and m.modulecode=p.po_type;");
			else if(tablename.equals("PayOrderCommissionExtraRate"))
				rs=stmt1.executeQuery("select 'Y/N',moduleabbr,a.subcatdesc,p.fr_date,p.to_date,p.fr_amt,p.to_amt,extra_rate,p.de_tml,p.de_user,p.de_date from PayOrderCommissionExtraRate p,AccountSubCategory a,Modules m  where subcatcode=cust_sub_type and m.modulecode=p.po_type");
			else if(tablename.equals("DirectorRelation"))
				rs=stmt1.executeQuery("select 'Y/N',dr.* from "+tablename+" dr ");
			else if(tablename.equals("DirectorMaster"))
				rs=stmt1.executeQuery("select 'Y/N',director_code,d.cid, concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,''))as name ,from_date,to_date from  "+tablename+"  d,CustomerMaster cm   where d.cid=cm.cid");
			else if(tablename.equals("SecurityDetails"))
				rs=stmt1.executeQuery("select 'Y/N',type_of_security,m.moduleabbr as ac_type, perc_ln_availed ,fromdate,todate,s.de_tml, s.de_user,s.de_date from SecurityDetails s,Modules m where s.modulecode=m.modulecode ");
			else if(tablename.equals("Relations"))
				rs=stmt1.executeQuery("select 'Y/N',r.* from "+tablename+" r ");
			
			
			if(rs!=null)
			{
				admin_object=new AdminObject[1];
				admin_object[0]=new AdminObject();
				meta_data=rs.getMetaData();
				col_names=new String[meta_data.getColumnCount()];
			
				for(int i=0;i<meta_data.getColumnCount();i++)
				{
					col_names[i]=meta_data.getColumnName(i+1);
				}
				rs.last();
				str=new Object[rs.getRow()][col_names.length];
				rs.beforeFirst();
				rs.next();
				if(str!=null)
				{
					for(int i=0;i<str.length;i++)
					{
						for(int j=0;j<str[i].length;j++)
						{
							if(j==0)
								str[i][j]=new Boolean(false);
							else 
							{
								str[i][j]=rs.getString(j+1);
							//	if((tablename.equals("TokenNumbers") && (j==2 ||j==3))|| (tablename.equals("SecurityDetails") && (j==4 ||j==5))  || (tablename.equals("SavingsIntRate") && (j==6)) || (tablename.equals("SavingsCatRate") && (j==7)) )
								//Changed by Mohsin 
								if((tablename.equals("TokenNumbers") && (j==2 ||j==3))|| (tablename.equals("SecurityDetails") && (j==4 ||j==5))  || (tablename.equals("SavingsIntRate") && (j==6)))
								{
									if(rs.getString(j+1)!=null && !(rs.getString(j+1).trim().equals("")))
									{
										date=rs.getString(j+1);
										System.out.println("date"+date);
										str[i][j]=Validations.convertDMY(date.substring(0,10))+date.substring(10);
									}
								}
							}
						}
						rs.next();
					}
				}
			
				admin_object[0].setCol_names(col_names);
				admin_object[0].setData(str);
			}
			else
				throw new RecordsNotFoundException();
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
	return admin_object;
	}

	
	public String  CalculateInterestAmountForODCC(String ac_type,int ac_no,double min_amt,String from_date,String to_date,int calc_ind)
	{
		Statement stmt_mst=null,stmt_tran,stmt_tran1=null,stmt_update=null,stat=null;
		ResultSet rs_mst=null,rs_tran1=null,rs1=null;
		Connection con=null;
		double int_rate=0,sanc_amt=0,int_amt=0,penal_amt=0,total_penal=0;
		String int_type=null,open_date=null,sanc_dt=null,name=null;
		int period=0,months=0,acno=0;
		String category=null;
		String prev_bal=null,prev_date=null, next_date=null;
		String prev_seq=null,ret_string=null;
		double prev_penal_int=0,cl_bal=0;
		try
		{
			con=getConnection();
			stmt_mst=con.createStatement();
			stmt_tran=con.createStatement();
			stmt_tran1=con.createStatement();
			stmt_update=con.createStatement();
			stat=con.createStatement();
			
			if(calc_ind==1)//calculate 
			{
				if(ac_no!=0)
				{			
					if(ac_no!=0 && (rs1=stat.executeQuery("select * from IntPayOdcc where ac_no="+ac_no+" and ac_type='"+ac_type+"' and trn_date='"+to_date+"' ")).next())
						return "Already Calculated";
					if(ac_no!=0 && (rs1=stat.executeQuery("select * from ODCCTransaction where  ac_no="+ac_no+" and ac_type='"+ac_type+"' and trn_type='I' and int_uptodt='"+to_date+"'")).next())
						return "Already Posted";
					else
					{
						System.out.println("Inside delete of INTPAY.........");				
						rs_mst=stmt_mst.executeQuery("select ac_no,ac_type,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,''))as name,int_uptodt,sub_category,period,int_rate_type,int_rate,sanc_amt,sanc_dt from ODCCMaster am,CustomerMaster cm where ac_no="+ac_no+" and ac_type='"+ac_type+"' and ac_status='O' and am.cid=cm.cid and sanc_dt is not null");
					}
				}
				else
				{
					if(ac_no==0 && (rs1=stat.executeQuery("select * from IntPayOdcc where  ac_type='"+ac_type+"' and trn_date='"+to_date+"' ")).next())		
						return "Already Calculated";
					if(ac_no==0 && (rs1=stat.executeQuery("select * from ODCCTransaction where  ac_type='"+ac_type+"' and int_uptodt='"+to_date+"' and trn_type='I' ")).next())
						return "Already Posted";
					else									
						rs_mst=stmt_mst.executeQuery("select ac_no,ac_type,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,''))as name,int_uptodt,sub_category,period,int_rate_type,int_rate,sanc_amt,sanc_dt from ODCCMaster am,CustomerMaster cm where ac_type='"+ac_type+"' and ac_status='O' and am.cid=cm.cid and sanc_dt is not null");					
				}				
			}
			else if(calc_ind==2)//Recalculate
			{			
				if(ac_no!=0 )
				{
					stat.executeUpdate("delete from IntPayOdcc where  ac_type='"+ac_type+"' and ac_no="+ac_no+" and trn_date='"+to_date+"' ");
					rs_mst=stmt_mst.executeQuery("select ac_no,ac_type,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,''))as name,int_uptodt,sub_category,period,int_rate_type,int_rate,sanc_amt,sanc_dt from ODCCMaster am,CustomerMaster cm where ac_no="+ac_no+" and ac_type='"+ac_type+"' and ac_status='O' and am.cid=cm.cid and sanc_dt is not null");
				}			
				else 
				{
					stat.executeUpdate("delete from IntPayOdcc where  ac_type='"+ac_type+"' and trn_date='"+to_date+"' ");
					rs_mst=stmt_mst.executeQuery("select ac_no,ac_type,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,''))as name,int_uptodt,sub_category,period,int_rate_type,int_rate,sanc_amt,sanc_dt from ODCCMaster am,CustomerMaster cm where ac_type='"+ac_type+"' and ac_status='O' and am.cid=cm.cid and sanc_dt is not null");
				}
			}
			//stmt_tran.executeUpdate("drop table IF Exists IntPayOdcc");
			//stmt_tran.executeUpdate("create table IntPayOdcc(ac_type varchar(15),ac_no int(15),name varchar(100),prev_bal double(20,2),int_amt double(20,2),penal_int double(20,2),calculated_bal double(20,2) ,posting bool default false,exceeds bool default false,sanc_amt double(20,2),trn_date varchar(15));");
			if(rs_mst!=null)
			{
				stmt_tran.executeUpdate("drop table IF EXISTS  temp_odcc_int");
				stmt_tran.executeUpdate("drop table IF EXISTS  temp_odcc_penal");
				stmt_tran.executeUpdate("create table temp_odcc_int (ac_type int(15),ac_no int(15),from_date varchar(15),to_date varchar(15),trn_seq int(15),bal double(20,2),days int(15),interest double(20,2));");
				stmt_tran.executeUpdate("create table temp_odcc_penal (ac_type int(15),ac_no int(15),from_date varchar(15),to_date varchar(15),trn_seq int(15),bal double(20,2),period int(15),interest double(20,2),prev_penal double(20,2));");
				while(rs_mst.next())
				{
					
					name=rs_mst.getString("name");
					int_type=rs_mst.getString("int_rate_type");
					int_rate=rs_mst.getDouble("int_rate");
					period=rs_mst.getInt("period");
					category=rs_mst.getString("sub_category");
					from_date=rs_mst.getString("int_uptodt");
					sanc_amt=rs_mst.getDouble("sanc_amt");
					acno=rs_mst.getInt("ac_no");
					ac_type=rs_mst.getString("ac_type");
					sanc_dt=rs_mst.getString("sanc_dt");
					// For Calculating Interst
									//	stmt_tran.executeUpdate("insert into temp_odcc_int (ac_type,ac_no,from_date,to_date,trn_seq,bal) select ac_type,ac_no,trn_date,trn_seq,cl_bal from ODCCTransaction where ac_type='"+ac_type+"' and ac_no="+acno+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) >='"+Validations.convertYMD(from_date)+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <='"+Validations.convertYMD(to_date)+"' order by trn_seq"); 
					stmt_tran.executeUpdate("insert into temp_odcc_int (ac_type,ac_no,from_date,trn_seq,bal) select ac_type,ac_no,trn_date,trn_seq,cl_bal from ODCCTransaction where ac_type='"+ac_type+"' and ac_no="+acno+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) >='"+Validations.convertYMD(from_date)+"' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <='"+Validations.convertYMD(to_date)+"' order by trn_seq ");
					RemoveEntries(ac_type,acno,"temp_odcc_int",null,to_date);
					
					rs_tran1=stmt_tran1.executeQuery("select * from temp_odcc_int where  ac_type='"+ac_type+"' and ac_no="+acno+"  order by trn_seq");
					if(rs_tran1.next())
					{
						rs_tran1.beforeFirst();
						while(rs_tran1.next())
						{
							int_amt=0;
							prev_seq=rs_tran1.getString("trn_seq");
							System.out.println("bal----->>"+rs_tran1.getString("bal"));
							if(Double.parseDouble(rs_tran1.getString("bal"))<0)
							{	
								System.out.println("bal-----1>>"+rs_tran1.getString("bal"));
								if(int_type.equals("1")){//Fixed
									int_amt=Math.round(InterestAmount(rs_tran1.getString("from_date"),rs_tran1.getString("to_date"),int_rate,(Double.parseDouble(rs_tran1.getString("bal"))*-1)));
									System.out.println("int_amt --->"+int_amt);
								}	
								else//Float
									{
									 	System.out.println("-------"+ ac_type + " ------"+ rs_tran1.getString("from_date")+"--- "+ rs_tran1.getString("to_date")+ "----" + Double.parseDouble(rs_tran1.getString("bal"))*-1+"---" +category +"---"+rs_tran1.getString("bal")+"---"+ acno+ "");
										int_amt= Math.round(loanlocal.calculateInterest(ac_type,rs_tran1.getString("from_date"),rs_tran1.getString("to_date"),(Double.parseDouble(rs_tran1.getString("bal"))*-1),Integer.parseInt(category),period,(Double.parseDouble(rs_tran1.getString("bal"))*-1),acno));
										System.out.println("int_amt loans--->"+int_amt);
									}
							}
							stmt_update.executeUpdate("update temp_odcc_int set interest='"+int_amt+"' where  ac_no="+acno+" and ac_type='"+ac_type+"' and trn_seq="+prev_seq+" ");
							if(rs_tran1.isLast())
								cl_bal=rs_tran1.getDouble("bal");
						}
						
					}
					//For calculating Penal Interset
					System.out.println("Penal Int Calculation Starts....................");
					total_penal=0;
					penal_amt=0;
					from_date=rs_mst.getString("sanc_dt");
					stmt_tran.executeUpdate("insert into temp_odcc_penal(ac_type,ac_no,from_date,trn_seq,bal) select ac_type,ac_no,trn_date,trn_seq,cl_bal from ODCCTransaction where ac_type='"+ac_type+"' and ac_no="+acno+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <='"+Validations.convertYMD(to_date)+"' order by trn_seq ");
					RemoveEntries(ac_type,acno,"temp_odcc_penal",sanc_dt,to_date);
					
					rs_tran1=stmt_tran1.executeQuery("select * from temp_odcc_penal  where ac_type='"+ac_type+"' and ac_no="+acno+" order by trn_seq");
					if(rs_tran1.next())
					{
						rs_tran1.beforeFirst();
						while(rs_tran1.next())
						{
							prev_date=rs_tran1.getString("from_date");
							if(rs_tran1.getString("to_date")!=null)
								next_date=rs_tran1.getString("to_date");
							else
								next_date=prev_date;
							
							prev_bal=rs_tran1.getString("bal");
							
							if(rs_tran1.getString("period")!=null)
								months=rs_tran1.getInt("period");
							else
								months=0;
							
							prev_seq=rs_tran1.getString("trn_seq");
							
//							System.out.println("Fromdate="+prev_date + "  Next date="+next_date+  "   prev_bal="+prev_bal +"  amount="+int_amt);
							if(Double.parseDouble(prev_bal)<0)
							{
								if(months>period)
								{
									//System.out.println("pver date="+prev_date +"sanc dt="+sanc_dt  +"expiry date="+Validations.addNoOfMonths(sanc_dt,period));
									if(Validations.dayCompare(prev_date,Validations.addNoOfMonths(sanc_dt,period))>=0){
										prev_date=Validations.addNoOfMonths(sanc_dt,period);
										System.out.println("date is less than period="+prev_date);
									}
									penal_amt=Math.round(loanlocal.calculatePenalInt(ac_type,prev_date,next_date,(Double.parseDouble(prev_bal)*-1),Integer.parseInt(category)));
									System.out.println("Second penal amt period="+penal_amt);
								}
								else if(Double.parseDouble(prev_bal)<((sanc_amt*-1)))
								{
									penal_amt=Math.round(loanlocal.calculatePenalInt(ac_type,prev_date,next_date,((Double.parseDouble(prev_bal)*-1)-(sanc_amt)),Integer.parseInt(category)));
									System.out.println("Exceeds Sanc Amonut penal amt="+penal_amt);
								}
								stmt_update.executeUpdate("update temp_odcc_penal set interest='"+penal_amt+"' where  ac_no="+acno+" and ac_type='"+ac_type+"' and trn_seq="+prev_seq+" ");
							}
							if(rs_tran1.isLast())
								cl_bal=rs_tran1.getDouble("bal");
							months=0;
							penal_amt=0;
						}
					}
					
					
					//System.out.println("penal amount before substraction="+total_penal);
					prev_penal_int=0;
					rs_tran1=stmt_tran.executeQuery("select sum(trn_amt) as trn_amt from ODCCTransaction where ac_no="+acno+" and ac_type='"+ac_type+"' and trn_type='E' and  concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <='"+Validations.convertYMD(to_date)+"' ");
					if(rs_tran1.next())
					{
						System.out.println("inside prev penal int");
						if(rs_tran1.getString(1)!=null){
							prev_penal_int= Math.round(rs_tran1.getDouble("trn_amt"));
							stmt_update.executeUpdate("update temp_odcc_penal set prev_penal='"+prev_penal_int+"' where  ac_no="+acno+" and ac_type='"+ac_type+"'  ");
						}
						else
							System.out.println("prev penal is null"+rs_tran1.getString(1));
					}
				
					rs_tran1=stmt_tran.executeQuery("select prev_penal from temp_odcc_penal where ac_no="+acno+"  and ac_type="+ac_type+"  and prev_penal is not null");
					if(rs_tran1.next())
					{
						if(rs_tran1.getString(1)!=null)
							prev_penal_int = Math.round(rs_tran1.getDouble(1));
						else
							prev_penal_int=0;
					}
					else
						prev_penal_int=0;
						
					rs_tran1=stmt_tran.executeQuery("select sum(interest) from temp_odcc_penal where ac_no="+acno+"  and ac_type="+ac_type+" ");
					if(rs_tran1.next())
					{
						if(rs_tran1.getString(1)!=null)
							penal_amt= Math.round(rs_tran1.getDouble(1));
						else
							penal_amt=0;
					}
					else
						penal_amt=0;
					
					total_penal = Math.round(penal_amt-prev_penal_int);
					System.out.println("total penal="+total_penal);
						
					rs_tran1=stmt_tran.executeQuery("select sum(interest) from temp_odcc_int where ac_no="+acno+"  and ac_type="+ac_type+" ");
					if(rs_tran1.next())
					{
						if(rs_tran1.getString(1)!=null)
							int_amt= Math.round(rs_tran1.getDouble(1));
						else
							int_amt=0;
					}
					else
						int_amt=0;
						
					if(int_amt!=0 || total_penal!=0)
					{
						stmt_update.executeUpdate("insert into IntPayOdcc values('"+ac_type+"',"+acno+",'"+name+"',"+cl_bal+","+int_amt*(-1)+","+total_penal*(-1)+","+(cl_bal+int_amt*(-1)+total_penal*(-1))+",false,false,"+sanc_amt+",'"+to_date+"') ");
						ret_string="Interest Calculated";
					}
					else
					{
						ret_string="Penal Interest & Interest Amt is 0.0";
					}
					int_amt=0;
					total_penal=0;
					cl_bal=0;
				}
			}
			else
				ret_string="Interst Not Calulated";
			
		}catch(Exception e){
			ctx.setRollbackOnly();
			e.printStackTrace();
			ret_string="Interst Not Calulated";
			}
		finally{
			try{                
				con.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return ret_string; 
	}



double InterestAmount(String from_date,String to_date,double int_rate,double amount)
{
	int days=Validations.dayCompare(from_date,to_date);
	System.out.println("days="+days);
	double retamt=(amount*int_rate*days)/(365*100);
	return retamt;
}

void RemoveEntries(String ac_type,int acno,String tablename,String start_date,String todate)
{
	ResultSet rs_tran=null,rs_tran1=null;
	Statement stmt_tran=null,stmt_tran1=null,stmt_update=null;
	Connection con=null;
	String prev_date,prev_seq,prev,next,next_seq,temp,next_date;
	int days=0,period=0;
	ArrayList at=new ArrayList();
	try
	{
		con=getConnection();
		stmt_tran=con.createStatement();
		stmt_tran1=con.createStatement();
		stmt_update=con.createStatement();
		
		rs_tran=stmt_tran.executeQuery("select * from "+tablename+" where ac_no="+acno+" and ac_type='"+ac_type+"' ");
		if(rs_tran!=null && rs_tran.next())
		{
			prev=rs_tran.getString("from_date");
			prev_seq=rs_tran.getString("trn_seq");
			while(rs_tran.next())
			{
				next=rs_tran.getString("from_date");
				next_seq=rs_tran.getString("trn_seq");
				if(Validations.dayCompare(prev,next)<=0)
				{
					at.add(prev_seq);
				}
				prev=next;
				prev_seq=next_seq;
			}
			System.out.println("trn_seq list="+at);
			temp=at.toString();
			System.out.println("temp list="+temp);
			temp=temp.replace('[','(');
			System.out.println("temp list="+temp);
			temp=temp.replace(']',')');
			System.out.println("temp list="+temp);
			if(at.size()>0)
				stmt_tran1.executeUpdate("delete from "+tablename+" where trn_seq in "+temp+" and ac_no="+acno+" and ac_type='"+ac_type+"'  ");
			rs_tran1=stmt_tran1.executeQuery("select * from "+tablename+" where ac_no="+acno+" and ac_type='"+ac_type+"' order by trn_seq");
			if(rs_tran1!=null)
			{
				rs_tran1.next();
				prev_date=rs_tran1.getString("from_date");
				prev_seq=rs_tran1.getString("trn_seq");
				
				while(rs_tran1.next())
				{
					next_date=rs_tran1.getString("from_date");
					next_seq=rs_tran1.getString("trn_seq");
					
					if(tablename.equals("temp_odcc_int")){
						days=Validations.dayCompare(prev_date,next_date);
						stmt_update.executeUpdate("update temp_odcc_int set to_date='"+next_date+"',days="+days+" where  ac_no="+acno+" and ac_type='"+ac_type+"' and trn_seq="+prev_seq+" ");
						if(rs_tran1.isLast()){
							days=Validations.dayCompare(next_date,todate);
							stmt_update.executeUpdate("update temp_odcc_int set to_date='"+todate+"',days="+days+" where  ac_no="+acno+" and ac_type='"+ac_type+"' and trn_seq="+next_seq+" ");
						}
					}
					else{
						period=Validations.noOfMonths(start_date,next_date);
						stmt_update.executeUpdate("update temp_odcc_penal set to_date='"+next_date+"',period="+period+" where  ac_no="+acno+" and ac_type='"+ac_type+"' and trn_seq="+prev_seq+" ");
						if(rs_tran1.isLast()){
							period=Validations.noOfMonths(start_date,todate);
							stmt_update.executeUpdate("update temp_odcc_penal set to_date='"+todate+"',period="+period+" where  ac_no="+acno+" and ac_type='"+ac_type+"' and trn_seq="+next_seq+" ");
						}
					}
					prev_date=next_date;
					prev_seq=next_seq;
					period=0;;
					days=0;
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace(); 
		ctx.setRollbackOnly(); 
		}
	finally{
		try{                
			con.close();
		}catch(Exception exception){exception.printStackTrace();}
	}
}
public Object[][] ODCCInterestDetails(String query)
{
	ResultSet rs_tran=null;
	Statement stmt_tran=null;
	Connection con=null;
	Object ret[][]=null;
	int i=0;
	try
	{
		con=getConnection();
		stmt_tran=con.createStatement();
		rs_tran=stmt_tran.executeQuery("select * from IntPayOdcc where trn_date='"+getSysDate()+"' ");
		if(rs_tran!=null && rs_tran.next())
		{
			rs_tran.last();
			ret=new Object[rs_tran.getRow()][10];
			System.out.println("rows="+rs_tran.getRow());
			rs_tran.beforeFirst();
			while(rs_tran.next())
			{
				ret[i][0]=rs_tran.getString(1);
				ret[i][1]=rs_tran.getString(2);
				ret[i][2]=rs_tran.getString(3);
				ret[i][3]=rs_tran.getString(4);
				ret[i][4]=rs_tran.getString(5);
				ret[i][5]=rs_tran.getString(6);
				ret[i][6]=rs_tran.getString(7);
				ret[i][7]=new Boolean(false);
				ret[i][8]=new Boolean(false);
				ret[i][9]=rs_tran.getString(10);
				i++;
			}
			System.out.println("odcc Interest Detatils teble lenth="+ret.length);
		}
	}catch(Exception e){
		e.printStackTrace();
		ctx.setRollbackOnly(); 
		}
	finally{
		try{                
			con.close();
		}catch(Exception exception){exception.printStackTrace();}
	}
	
	return ret;
}
public Vector ODCCPost(String UTml,String Uid,String UdateTime,String modname,int modcode,int acno,Object[][] obj)
{
	ResultSet rs_tran=null,rs;
	Statement stmt_tran=null,stat=null;
	Connection con=null;
	//Object ret[][]=new Object[2][3];
	double trn_amt=0,total_tran_amt=0,final_bal=0;
	//String int_date,to_date=null,prev_date=null;
	int j=0;
	Vector vt=new Vector();
	AccountTransObject at;
	String udate=UdateTime.substring(0,10);
	double prev_penal=0;
	boolean break_flag=false;
	
	try
	{
		con=getConnection();
		stmt_tran=con.createStatement();
		stat=con.createStatement();
		if(obj!=null)
		{
			for(int i=0;i<obj.length;i++)
			{
				if(obj[i][7].equals(new Boolean(true))  && obj[i][0].equals(String.valueOf(modcode)) && obj[i][1].equals(String.valueOf(acno)))
				{
					rs=stat.executeQuery("select odccm.ac_type,odccm.ac_no,cl_bal,odccm.sanc_amt  from ODCCMaster odccm,ODCCTransaction odcct where odccm.ac_type='"+modcode+"' and odccm.ac_no="+acno+" and odcct.ac_type=odccm.ac_type and odcct.ac_no=odccm.ac_no  and trn_seq in(select max(trn_seq) from ODCCTransaction odcctrn where odcctrn.ac_type=odccm.ac_type and odcctrn.ac_no=odccm.ac_no)");
					
					if(rs.next())
					{
						System.out.println("Fetching data from Penal interest table");
						//rs_tran=stmt_tran.executeQuery("select * from temp_odcc_penal where ac_type='"+obj[i][0]+"' and ac_no="+obj[i][1]+" order by trn_seq ");
						rs_tran=stmt_tran.executeQuery("select * from temp_odcc_penal where ac_type='"+modcode+"' and ac_no="+acno+" order by trn_seq ");
						if(rs_tran!=null && rs_tran.next())
						{
							final_bal=(rs.getDouble("cl_bal")-rs_tran.getDouble("prev_penal"));
							total_tran_amt-=rs_tran.getDouble("prev_penal");
							prev_penal=rs_tran.getDouble("prev_penal");
							rs_tran.beforeFirst();
							at=new AccountTransObject();
							
							while(rs_tran.next())
							{
								
								trn_amt=rs_tran.getDouble("interest");
								total_tran_amt+=trn_amt;
								final_bal-=trn_amt;
								System.out.println("For Penal.................Closing bal on ="+rs_tran.getString("to_date") +" "+final_bal +".....interst amount="+trn_amt);
								if(obj[i][8].equals(new Boolean(false)) && (final_bal<0) && Math.abs(final_bal)>rs.getDouble("sanc_amt"))
								{
									final_bal+=trn_amt;
									at.setTransType("E");
									at.setInt_amt(total_tran_amt-trn_amt);
									at.setInt_date(rs_tran.getString("from_date"));
									vt.add(at);
									break_flag=true;;
									break;
								}
							}
							if(!break_flag)
							{
								at.setTransType("E");
								at.setInt_amt(total_tran_amt);
								at.setInt_date(udate);
								vt.add(at);
							}
							j++;
							break_flag=false;
						}
						rs_tran=stmt_tran.executeQuery("select * from temp_odcc_int where ac_type='"+modcode+"' and ac_no="+acno+" order by trn_seq ");
						if(rs_tran!=null && rs_tran.next())
						{
							//prev_date=rs_tran.getString("to_date");
							total_tran_amt=0;
							System.out.println("Fetching data from  interest table");
							rs_tran.beforeFirst();
							at=new AccountTransObject();
							while(rs_tran.next())
							{
								trn_amt=rs_tran.getDouble("interest");
								total_tran_amt+=trn_amt;
								final_bal-=trn_amt;
								System.out.println("For Interest.................Closing bal on ="+rs_tran.getString("to_date") +" "+final_bal +".....interst amount="+trn_amt);
								if(obj[i][8].equals(new Boolean(false)) && (final_bal<0) && Math.abs(final_bal)>rs.getDouble("sanc_amt"))
								{
									final_bal+=trn_amt;
									at.setTransType("I");
									at.setInt_amt(total_tran_amt-trn_amt);
									at.setInt_date(rs_tran.getString("from_date"));
									vt.add(at);
									break_flag=true;
									break;
								}
							}
							if(!break_flag)
							{
								at.setTransType("I");
								at.setInt_amt(total_tran_amt);
								at.setInt_date(udate);
								vt.add(at);
							}
							break_flag=false;
							j++;
						}
					}
				}
			}
		}
		else
			System.out.println("obj is null................");
	}catch(Exception e){e.printStackTrace(); ctx.setRollbackOnly(); }
	finally{
		try{                
			con.close();
		}catch(Exception exception){
			exception.printStackTrace();
			}
	}
	

	return vt;
}
public int insertRecords(AdminObject obj[],String tablename)
{
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	Statement stmt=null;
	Connection con=null;
	String de_date=null;
	int i=0;
	try{
		con=getConnection();
		stmt=con.createStatement();
		if(tablename.equals("SavingsIntRate"))
		{
			while(i!=obj.length)
			{
				/*rs=stmt.executeQuery("select * from "+tablename+" where ac_type="+obj[i].getAc_type()+" and concat(right(date,4),'-',mid(date,locate('/',date)+1, (locate('/',date,4)-locate('/',date)-1)),'-',left(date,locate('/',date)-1))< '"+Validations.convertYMD(obj[i].getDate())+"' ");
				if(rs.next())
					return -2;
				else
				{*/
				pstmt=con.prepareStatement("insert into "+tablename+" values(?,?,?,?,?,?)");
				pstmt.setString(1,obj[i].getAc_type());
				pstmt.setString(2,obj[i].getDate());
				pstmt.setDouble(3,obj[i].getInt_rate());
				pstmt.setString(4,obj[i].getDe_tml());
				pstmt.setString(5,obj[i].getDe_user());
				if(obj[i].getDe_date()!=null && !(obj[i].getDe_date().equals("")))
				{
					de_date=Validations.convertYMD(obj[i].getDe_date().substring(0,10));
					System.out.println("de_date="+(de_date+obj[i].getDe_date().substring(10)));
					pstmt.setString(6,(de_date+obj[i].getDe_date().substring(10)));
				}
				else
					pstmt.setString(6,obj[i].getDe_date());
				
				pstmt.executeUpdate();
				i++;
				//}
			}
		}
		else if(tablename.equals("SavingsCatRate"))
		{
			while(i!=obj.length)
			{
				/*rs=stmt.executeQuery("select * from "+tablename+" where ac_type="+obj[i].getAc_type()+" and concat(right(date,4),'-',mid(date,locate('/',date)+1, (locate('/',date,4)-locate('/',date)-1)),'-',left(date,locate('/',date)-1))< '"+Validations.convertYMD(obj[i].getDate())+"' and category='"+obj[i].getSubcat()+"'  ");
				if(rs.next())
					return -2;
				else
				{*/
				pstmt=con.prepareStatement("insert into "+tablename+" values(?,?,?,?,?,?,?,?,?,?)");
				pstmt.setString(1,obj[i].getAc_type());			
				pstmt.setInt(2,obj[i].getCat());
				pstmt.setString(3,obj[i].getFr_date());
				pstmt.setString(4,obj[i].getTo_date());
				pstmt.setInt(5,obj[i].getDays_fr());
				pstmt.setInt(6,obj[i].getDays_to());
				
				pstmt.setDouble(7,obj[i].getExtra_rate());
				pstmt.setString(8,obj[i].getDe_user());
				pstmt.setString(9,obj[i].getDe_tml());
				/*if(obj[i].getDe_date()!=null && !(obj[i].getDe_date().equals("")))
				{
					de_date=Validations.convertYMD(obj[i].getDe_date().substring(0,10));
					System.out.println("de_date="+(de_date+obj[i].getDe_date().substring(10)));
					pstmt.setString(10,(de_date+obj[i].getDe_date().substring(10)));
				}
				else*/
					pstmt.setString(10,obj[i].getDe_date());
				
				
				pstmt.executeUpdate();
				i++;
				//}
			}
		}
		else if(tablename.equals("TokenNumbers"))
		{
			while(i!=obj.length)
			{
				System.out.println("token num="+obj[i].getTokennum() +"from date="+obj[i].getDate()+ "  To Date="+obj[i].getTo_date());
				rs=stmt.executeQuery("select * from "+tablename+" where token_no="+obj[i].getTokennum()+" and from_date>='"+Validations.convertYMD(obj[i].getDate())+"' ");
				if(rs.next())
					return -2;
				else
				{
					rs=stmt.executeQuery("select * from "+tablename+" where token_no="+obj[i].getTokennum()+" and '"+Validations.convertYMD(obj[i].getDate())+"' between from_date and to_date ");
					if(rs.next())
						stmt.executeUpdate("update "+tablename+" set to_date='"+Validations.convertYMD(Validations.addDays(obj[i].getDate(),-1))+"'  where token_no="+obj[i].getTokennum()+"  and '"+Validations.convertYMD(obj[i].getDate())+"' between from_date and to_date ");
					else
					{
						pstmt=con.prepareStatement("insert into "+tablename+" values(?,?,?,?,?,?)");
						pstmt.setInt(1,obj[i].getTokennum());
						pstmt.setString(2,Validations.convertYMD(obj[i].getDate()));
						pstmt.setString(3,Validations.convertYMD(obj[i].getTo_date()));
						pstmt.setString(4,obj[i].getDe_tml());
						pstmt.setString(5,obj[i].getDe_user());
						pstmt.setString(6,obj[i].getDe_date());
						pstmt.executeUpdate();
						i++;
					}
				}
			}
		}
		else if(tablename.equals("DirectorRelation"))
		{
			int max_code=0;
			while(i!=obj.length)
			{
				max_code=0;
				rs=stmt.executeQuery("select max(rel_code) from "+tablename+" ");
				if(rs.next() && rs.getString(1)!=null)
					max_code=rs.getInt(1);
				
				pstmt=con.prepareStatement("insert into "+tablename+" values(?,?)");
				pstmt.setInt(1,(max_code+1));
				pstmt.setString(2,obj[i].getRelation());
				
				pstmt.executeUpdate();
				i++;
			}
			return (max_code+1);
		}
		//Added by Mohsin
		else if(tablename.equals("Relations"))
		{
			int max_code=0;
			while(i!=obj.length)
			{
				/*max_code=0;
				rs=stmt.executeQuery("select max(rel_code) from "+tablename+" ");
				if(rs.next() && rs.getString(1)!=null)
					max_code=rs.getInt(1);*/
				
				pstmt=con.prepareStatement("insert into "+tablename+" values(?)");
				
				pstmt.setString(1,obj[i].getRelation());
				
				pstmt.executeUpdate();
				i++;
			}
			return (1);
		}
		else if(tablename.equals("DirectorMaster"))
		{
			int max_code=0;
			while(i!=obj.length)
			{
				max_code=0;
				rs=stmt.executeQuery("select max(director_code) from "+tablename+" ");
				if(rs.next() && rs.getString(1)!=null)
					max_code=rs.getInt(1);
				
				pstmt=con.prepareStatement("insert into "+tablename+" values(?,?,?,?,?)");
				pstmt.setInt(1,(max_code+1));
				pstmt.setInt(2,(max_code+1));
				pstmt.setInt(3,obj[i].getCid());
				pstmt.setString(4,obj[i].getDate());
				pstmt.setString(5,obj[i].getTo_date());
				
				pstmt.executeUpdate();
				i++;
			}
			return (max_code+1);
		}
		else if(tablename.equals("SecurityDetails"))
		{
			while(i!=obj.length)
			{
				rs=stmt.executeQuery("select * from "+tablename+" where type_of_security='"+obj[i].getType_of_sec()+"' and modulecode='"+obj[i].getAc_type()+"' and fromdate>='"+Validations.convertYMD(obj[i].getDate())+"' ");
				if(rs.next())
					return -2;
				else
				{
					rs=stmt.executeQuery("select * from "+tablename+" where type_of_security='"+obj[i].getType_of_sec()+"' and modulecode='"+obj[i].getAc_type()+"'  and '"+Validations.convertYMD(obj[i].getDate())+"' between fromdate and todate ");
					if(rs.next())
						stmt.executeUpdate("update "+tablename+" set todate='"+Validations.convertYMD(Validations.addDays(obj[i].getDate(),-1))+"'  where type_of_security='"+obj[i].getType_of_sec()+"' and modulecode='"+obj[i].getAc_type()+"'   and '"+Validations.convertYMD(obj[i].getDate())+"' between fromdate and todate ");
					else
					{
						String code="";
						rs=stmt.executeQuery("select code from "+tablename+" where type_of_security='"+obj[i].getType_of_sec()+"' and modulecode='"+obj[i].getAc_type()+"' ");
						if(rs.next())
						{
							code=rs.getString(1);
						}
						pstmt=con.prepareStatement("insert into "+tablename+" values(?,?,?,?,?,?,?,?,?)");
						pstmt.setString(1,code);
						pstmt.setString(2,obj[i].getType_of_sec());
						pstmt.setString(3,obj[i].getAc_type());
						pstmt.setDouble(4,obj[i].getPercentage());
						pstmt.setString(5,Validations.convertYMD(obj[i].getDate()));
						pstmt.setString(6,Validations.convertYMD(obj[i].getTo_date()));
						pstmt.setString(7,obj[i].getDe_tml());
						pstmt.setString(8,obj[i].getDe_user());
						pstmt.setString(9,obj[i].getDe_date());
						pstmt.executeUpdate();
						i++;
					}
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
		ctx.setRollbackOnly();
		return -1;
		}
	finally{
		try{                
			con.close();
		}catch(Exception exception){exception.printStackTrace();}
	}
	return i;
}
public int updateRecords(AdminObject obj[],String tablename)
{
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	Statement stmt=null;
	Connection con=null;
	int i=0;
	int rows=0;
	try{
		con=getConnection();
		stmt=con.createStatement();
		if(tablename.equals("SavingsIntRate"))
		{
			while(i!=obj.length)
			{
				rows=stmt.executeUpdate("delete from "+tablename+" where date='"+obj[i].getDate()+"'  and ac_type='"+obj[i].getAc_type()+"' and de_date='"+obj[i].getDe_date()+"'");
				i++;
			}
		}
		else if(tablename.equals("SavingsCatRate"))
		{
			while(i!=obj.length)
			{
				rows=stmt.executeUpdate("delete from "+tablename+" where date='"+obj[i].getDate()+"'  and ac_type='"+obj[i].getAc_type()+"' and de_date='"+obj[i].getDe_date()+"' and category '"+obj[i].getSubcat()+"' ");
				i++;
			}
		}
		else if(tablename.equals("TokenNumbers"))
		{
			while(i!=obj.length)
			{
				System.out.println("get Date="+obj[i].getDate());
				rs=stmt.executeQuery("select * from "+tablename+" where token_no="+obj[i].getTokennum()+" and from_date!='"+Validations.convertYMD(obj[i].getDate())+"' and '"+Validations.convertYMD(obj[i].getTo_date())+"'  between from_date and to_date ");
				if(rs.next())
					return -2;
				else
					rows=stmt.executeUpdate("update "+tablename+" set to_date='"+Validations.convertYMD(obj[i].getTo_date())+"' where token_no="+obj[i].getTokennum()+" and  from_date ='"+Validations.convertYMD(obj[i].getDate())+"' ");
				i++;
			}
		}
		else if(tablename.equals("SecurityDetails"))
		{
			while(i!=obj.length)
			{
				System.out.println("get Date="+obj[i].getDate());
				rs=stmt.executeQuery("select * from "+tablename+" where  type_of_security ='"+obj[i].getType_of_sec()+"' and modulecode='"+obj[i].getAc_type()+"'  and fromdate!='"+Validations.convertYMD(obj[i].getDate())+"' and '"+Validations.convertYMD(obj[i].getTo_date())+"'  between fromdate and todate ");
				if(rs.next())
					return -2;
				else
					rows=stmt.executeUpdate("update "+tablename+" set todate='"+Validations.convertYMD(obj[i].getTo_date())+"' where type_of_security ='"+obj[i].getType_of_sec()+"' and modulecode='"+obj[i].getAc_type()+"'  and  fromdate ='"+Validations.convertYMD(obj[i].getDate())+"' ");
				i++;
			}
		}
		else if(tablename.equals("DirectorMaster"))
		{
			while(i!=obj.length)
			{
				rows=stmt.executeUpdate("update "+tablename+" set to_date='"+obj[i].getTo_date()+"' where director_code="+obj[i].getDircode()+" and  from_date ='"+obj[i].getDate()+"' and cid="+obj[i].getCid()+" ");
				i++;
			}
		}
		else
			return -1;
	}catch(Exception e){
		e.printStackTrace();
		ctx.setRollbackOnly();
		return -1;
		}
	finally{
		try{                
			con.close();
		}catch(Exception exception){exception.printStackTrace();}
	}
	return rows;
}
public String checkInspectionDate(String ac_type,int ac_no)
{
//	PreparedStatement pstmt=null;
	ResultSet rs=null;
	Statement stmt=null;
	Connection con=null;
	String date=null;
	
	int i=0;
	try{
		con=getConnection();
		stmt=con.createStatement();
		rs=stmt.executeQuery("select * from StockInspectionDetails where  ac_type='"+ac_type+"' and  ac_no="+ac_no+"  order by concat(right(next_insp_date,4),'-',mid(next_insp_date,locate('/',next_insp_date)+1, (locate('/',next_insp_date,4)-locate('/',next_insp_date)-1)),'-',left(next_insp_date,locate('/',next_insp_date)-1)) desc  limit 1");
		if(rs!=null && rs.next())
		{
			date=rs.getString("next_insp_date");
		}
	}catch(Exception e){
		e.printStackTrace();
		ctx.setRollbackOnly();
		return null;
		}
	finally{
		try{                
			con.close();
		}catch(Exception exception){exception.printStackTrace();}
	}
	return date;
}
public void Check(ODCCMasterObject od)
{
	System.out.println("no="+od.getAccNo());
	System.out.println("name="+od.getName());
	od.setName("shubha");
}
public double getPercentage(String ac_type,String date)
{

	ResultSet rs=null;
	Statement stmt=null;
	Connection con=null;

	
	double per=0;
	try{
		con=getConnection();
		stmt=con.createStatement();
		rs=stmt.executeQuery("select perc_ln_availed from SecurityDetails where code=1 and modulecode='"+ac_type+"' and '"+Validations.convertYMD(date)+"' between fromdate and todate; ");
		if(rs!=null && rs.next())
		{
			per=rs.getDouble(1);
		}
	}catch(Exception e){e.printStackTrace(); ctx.setRollbackOnly();  return 0;}
	finally{
		try{                
			con.close();
		}catch(Exception exception){exception.printStackTrace();}
	}
	return per;
}
public String[] getSecurityCodes()
{

	ResultSet rs=null;
	Statement stmt=null;
	Connection con=null;
	String ret[]=null;
	try{
		con=getConnection();
		stmt=con.createStatement();
		int i=0;
		rs=stmt.executeQuery("select  distinct type_of_security from SecurityDetails ");
		if(rs!=null && rs.next())
		{
			rs.last();
			ret=new String[rs.getRow()];
			rs.beforeFirst();
			while(rs.next())
			{
				ret[i]=rs.getString(1);
				i++;
			}
		}
	}catch(Exception e){e.printStackTrace(); ctx.setRollbackOnly();  return null;}
	finally{
		try{                
			con.close();
		}catch(Exception exception){exception.printStackTrace();}
	}
	return ret;
}
/**
 * po_type - PO
 * acc_cat - 0,1,2.
 * sub_cat - 101,102,201,202,203,301,302,....
 */
public PayOrderObject[] getPOCommissionDetails(String po_type,int acc_cat,int sub_cat)
{
    PayOrderObject[] payorderobject = null;
    
    Connection con = null;
    Statement stmt_POComm = null;
    ResultSet rs_POComm = null;
    
    try
    {
        con = getConnection();
        stmt_POComm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      //  file_logger.info(po_type+"\t"+acc_cat+"\t"+sub_cat);           
        rs_POComm = stmt_POComm.executeQuery("select * from PayOrderCommission where po_type='"+po_type+"' and cust_type="+acc_cat+" and cust_sub_type="+sub_cat+" order by concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1)),fr_amt");
                    
        if(rs_POComm!=null && rs_POComm.last())
        {
            payorderobject = new PayOrderObject[rs_POComm.getRow()];
            
            rs_POComm.beforeFirst();
        }
        
        if(rs_POComm!=null)
        {
            int d = 0;
            
            while(rs_POComm.next())
            {
                payorderobject[d] = new PayOrderObject();
                
                payorderobject[d].setPOFromDate(rs_POComm.getString("fr_date"));
                payorderobject[d].setPOToDate(rs_POComm.getString("to_date"));
                
                payorderobject[d].setPOFromAmt(rs_POComm.getDouble("fr_amt"));
                payorderobject[d].setPOToAmt(rs_POComm.getDouble("to_amt"));
                
                payorderobject[d].setPOCommRate(rs_POComm.getDouble("comm_rate"));
                payorderobject[d].setPOCommType(rs_POComm.getString("comm_type"));
                payorderobject[d].setPOMinCommAmt(rs_POComm.getDouble("min_comm_amt"));                    
                payorderobject[d].setPOExtraCommRate(rs_POComm.getDouble("extra_rate"));
                
                payorderobject[d].uv.setUserDate(rs_POComm.getString("de_date"));
                payorderobject[d].uv.setUserId(rs_POComm.getString("de_user"));
                payorderobject[d].uv.setUserTml(rs_POComm.getString("de_tml"));
                
                d++;
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
            con.close();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }
    
    return payorderobject;
}

/**
 * 
 * @param payorderobject
 * @param type - 1 : insert into PayOrderCommission
 *               2 : update PayOrderCommission
 * @return
 */
public boolean storePOCommissionDetails(PayOrderObject[] payorderobject,int type)
{
	
    Statement stmt_pocomm = null,stmt_poextracomm = null;
    ResultSet rs_pocomm = null,rs_poextracomm = null,rs_check = null;
    
    Statement stmt_same_record = null,stmt_update_record = null,stmt_check = null;
    
    PreparedStatement[] pstmt_pocomm = null;
    PreparedStatement[] pstmt_poextracomm = null;
    
    Connection conn = null;
    
    boolean pocomm = false;
   
    try
    {
        commonlocal = commonlocalhome.create();
        
        conn = getConnection();
        
        stmt_pocomm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        stmt_poextracomm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        
        stmt_same_record = conn.createStatement();
        stmt_update_record = conn.createStatement();
        stmt_check = conn.createStatement();
        
        //To Store the Details
        if(type==1)
        {
        	System.out.println("payorderobject[i].getPOType()===>"+payorderobject[0].getPOType());
        	System.out.println("payorderobject[i].getCustType()===>"+payorderobject[0].getCustType());
            pocomm = true;
            
            pstmt_pocomm = new PreparedStatement[payorderobject.length];
            
            for(int i=0;i<payorderobject.length;i++)
            {    //rs_pocomm = stmt_pocomm.executeQuery("select * from PayOrderCommission where po_type='"+payorderobject[i].getPOType()+"' and cust_type="+payorderobject[i].getCustType()+" and cust_sub_type="+payorderobject[i].getPOSubCat()+" and to_date='31/12/9999' order by fr_amt");             
                rs_pocomm = stmt_pocomm.executeQuery("select * from PayOrderCommission where po_type='"+payorderobject[i].getPOType()+"' and cust_type="+payorderobject[i].getCustType()+" and to_date='31/12/9999' order by fr_amt");
                
                if(rs_pocomm.last())
                {
                     rs_pocomm.beforeFirst();
                }
                else
                {
                    pstmt_pocomm[i]=conn.prepareStatement("insert into PayOrderCommission values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    
                    /*pstmt_pocomm[i].setString(1,payorderobject[i].getPOType());
                    pstmt_pocomm[i].setInt(2,payorderobject[i].getCustType());
                    pstmt_pocomm[i].setInt(3,payorderobject[i].getPOSubCat());
                    
                    pstmt_pocomm[i].setString(4,payorderobject[i].getPOFromDate());                                               
                    pstmt_pocomm[i].setString(5,payorderobject[i].getPOToDate());
                    
                    pstmt_pocomm[i].setDouble(6,payorderobject[i].getPOFromAmt());
                    pstmt_pocomm[i].setDouble(7,payorderobject[i].getPOToAmt());
                    
                    pstmt_pocomm[i].setDouble(8,payorderobject[i].getPOCommRate());
                    pstmt_pocomm[i].setString(9,payorderobject[i].getPOCommType());
                    pstmt_pocomm[i].setDouble(10,payorderobject[i].getPOMinCommAmt());
                    pstmt_pocomm[i].setDouble(11,payorderobject[i].getPOExtraCommRate());
                    
                    pstmt_pocomm[i].setString(12,payorderobject[i].uv.getUserTml());
                    pstmt_pocomm[i].setString(13,payorderobject[i].uv.getUserId());
                    pstmt_pocomm[i].setString(14,payorderobject[i].uv.getUserDate());*/
                    pstmt_pocomm[i].setString(1,payorderobject[i].getPOType());
                    pstmt_pocomm[i].setInt(2,payorderobject[i].getCustType());
                    pstmt_pocomm[i].setInt(3,payorderobject[i].getPOSubCat());
                    
                    pstmt_pocomm[i].setString(4,payorderobject[i].getPOFromDate());                                               
                    pstmt_pocomm[i].setString(5,payorderobject[i].getPOToDate());
                    
                    pstmt_pocomm[i].setDouble(6,payorderobject[i].getPOFromAmt());
                    pstmt_pocomm[i].setDouble(7,payorderobject[i].getPOToAmt());
                    
                    pstmt_pocomm[i].setDouble(8,payorderobject[i].getPOCommRate());
                    pstmt_pocomm[i].setString(9,payorderobject[i].getPOCommType());
                    pstmt_pocomm[i].setDouble(10,payorderobject[i].getPOMinCommAmt());
                   pstmt_pocomm[i].setDouble(11,payorderobject[i].getPOExtraCommRate());
                    
                    pstmt_pocomm[i].setString(12,payorderobject[i].uv.getUserTml());
                    pstmt_pocomm[i].setString(13,payorderobject[i].uv.getUserId());
                    pstmt_pocomm[i].setString(14,payorderobject[i].uv.getUserDate());
                }	
                
                while(rs_pocomm.next())
                {
                    if(rs_pocomm.getString("fr_date").equals(payorderobject[i].getPOFromDate()) && rs_pocomm.getDouble("fr_amt")==payorderobject[i].getPOFromAmt() && rs_pocomm.getDouble("to_amt")==payorderobject[i].getPOToAmt())                             
                    {                        
                    	if(stmt_same_record.executeUpdate("update PayOrderCommission set to_date='"+payorderobject[i].getPOFromDate()+"' where to_date='31/12/9999' and po_type='"+payorderobject[i].getPOType()+"' and cust_type="+payorderobject[i].getCustType()+" and cust_sub_type="+payorderobject[i].getPOSubCat()+" and fr_date='"+payorderobject[i].getPOFromDate()+"' and fr_amt="+payorderobject[i].getPOFromAmt()+" and to_amt="+payorderobject[i].getPOToAmt()+"")==0)
                            throw new SQLException();
                    }
                    else if(rs_pocomm.getString("fr_date").equals(payorderobject[i].getPOFromDate()) && (rs_pocomm.getDouble("fr_amt") < payorderobject[i].getPOToAmt()) && (rs_pocomm.getDouble("to_amt") > payorderobject[i].getPOFromAmt()))
                    {
                        if(stmt_update_record.executeUpdate("update PayOrderCommission set to_date='"+payorderobject[i].getPOFromDate()+"' where to_date='31/12/9999' and po_type='"+payorderobject[i].getPOType()+"' and cust_type="+payorderobject[i].getCustType()+" and cust_sub_type="+payorderobject[i].getPOSubCat()+" and fr_amt="+rs_pocomm.getDouble("fr_amt")+" and to_amt="+rs_pocomm.getDouble("to_amt")+"")==0)
                            throw new SQLException();
                    }                    
                    else if(!rs_pocomm.getString("fr_date").equals(payorderobject[i].getPOFromDate()) && (rs_pocomm.getDouble("fr_amt") < payorderobject[i].getPOToAmt()) && (rs_pocomm.getDouble("to_amt") > payorderobject[i].getPOFromAmt()))
                    {
                         
                    	System.out.println("hello see thbis===> "+payorderobject[i].getPOFromDate());
                    	String string_date_to = commonlocal.getFutureDayDate(payorderobject[i].getPOFromDate(),-1);
                         if(stmt_update_record.executeUpdate("update PayOrderCommission set to_date='"+string_date_to+"' where to_date='31/12/9999' and po_type='"+payorderobject[i].getPOType()+"' and cust_type="+payorderobject[i].getCustType()+" and cust_sub_type="+payorderobject[i].getPOSubCat()+" and fr_amt="+rs_pocomm.getDouble("fr_amt")+" and to_amt="+rs_pocomm.getDouble("to_amt")+"")==0)
                             throw new SQLException();
                    }
                     
                    rs_check = stmt_check.executeQuery("select * from PayOrderCommission where po_type='"+payorderobject[i].getPOType()+"' and cust_type="+payorderobject[i].getCustType()+" and cust_sub_type="+payorderobject[i].getPOSubCat()+" and fr_date='"+payorderobject[i].getPOFromDate()+"' and to_date='"+payorderobject[i].getPOToDate()+"' and fr_amt="+payorderobject[i].getPOFromAmt()+" and to_amt="+payorderobject[i].getPOToAmt()+"");
                    
                    if(!rs_check.next())
                    {
                        pstmt_pocomm[i]=conn.prepareStatement("insert into PayOrderCommission values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        
                        pstmt_pocomm[i].setString(1,payorderobject[i].getPOType());
                        pstmt_pocomm[i].setInt(2,payorderobject[i].getCustType());
                        pstmt_pocomm[i].setInt(3,payorderobject[i].getPOSubCat());
                        
                        pstmt_pocomm[i].setString(4,payorderobject[i].getPOFromDate());                                               
                        pstmt_pocomm[i].setString(5,payorderobject[i].getPOToDate());
                        
                        pstmt_pocomm[i].setDouble(6,payorderobject[i].getPOFromAmt());
                        pstmt_pocomm[i].setDouble(7,payorderobject[i].getPOToAmt());
                        
                        pstmt_pocomm[i].setDouble(8,payorderobject[i].getPOCommRate());
                        pstmt_pocomm[i].setString(9,payorderobject[i].getPOCommType());
                        pstmt_pocomm[i].setDouble(10,payorderobject[i].getPOMinCommAmt());
                        pstmt_pocomm[i].setDouble(11,payorderobject[i].getPOExtraCommRate());
                        
                        pstmt_pocomm[i].setString(12,payorderobject[i].uv.getUserTml());
                        pstmt_pocomm[i].setString(13,payorderobject[i].uv.getUserId());
                        pstmt_pocomm[i].setString(14,payorderobject[i].uv.getUserDate());
                    }
                }
            }
        }
        //To Update the Details
        else if(type==2)
        {
            pstmt_pocomm = new PreparedStatement[payorderobject.length];
            
            for(int i=0;i<payorderobject.length;i++)
            {                  
                rs_pocomm = stmt_pocomm.executeQuery("select * from PayOrderCommission where po_type='"+payorderobject[i].getPOType()+"' and cust_type="+payorderobject[i].getCustType()+" and cust_sub_type="+payorderobject[i].getPOSubCat()+" and fr_date='"+payorderobject[i].getPOFromDate()+"' and fr_amt="+payorderobject[i].getPOFromAmt()+" and to_amt="+payorderobject[i].getPOToAmt()+" and comm_rate="+payorderobject[i].getPOCommRate()+" and comm_type='"+payorderobject[i].getPOCommType()+"' and min_comm_amt="+payorderobject[i].getPOMinCommAmt()+" and extra_rate="+payorderobject[i].getPOExtraCommRate()+"");
                
                if(rs_pocomm.next())
                { 
                    if(!rs_pocomm.getString("to_date").equals(payorderobject[i].getPOToDate()))
                    {
                        pocomm = true;
                        
                        pstmt_pocomm[i] = conn.prepareStatement("update PayOrderCommission set to_date=?,de_user=?,de_tml=?,de_date=? where po_type='"+payorderobject[i].getPOType()+"' and cust_type="+payorderobject[i].getCustType()+" and cust_sub_type="+payorderobject[i].getPOSubCat()+" and fr_date='"+payorderobject[i].getPOFromDate()+"' and fr_amt="+payorderobject[i].getPOFromAmt()+" and to_amt="+payorderobject[i].getPOToAmt()+" and comm_rate="+payorderobject[i].getPOCommRate()+" and comm_type='"+payorderobject[i].getPOCommType()+"' and min_comm_amt="+payorderobject[i].getPOMinCommAmt()+" and extra_rate="+payorderobject[i].getPOExtraCommRate()+"");
                        
                        pstmt_pocomm[i].setString(1,payorderobject[i].getPOToDate());
                        pstmt_pocomm[i].setString(2,payorderobject[i].uv.getUserId());
                        pstmt_pocomm[i].setString(3,payorderobject[i].uv.getUserTml());
                        pstmt_pocomm[i].setString(4,payorderobject[i].uv.getUserDate());
                    }
                }
            }
        }
                    
        for(int r=0;r<pstmt_pocomm.length; r++ )
        {
            if(pocomm && pstmt_pocomm[r].executeUpdate() == 0)
            {
                return false;
            }
        }          
    }
    catch(SQLException exception)
    {
        exception.printStackTrace();
        ctx.setRollbackOnly();
        return false;
    }
    catch(Exception e)
    {
        e.printStackTrace();
        return false;
    }
    finally
    {
        try
        {                
            conn.close();
            commonlocal = null;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
    }
    
    return true;
}
////////////

//To get Deposit Accounts for OD 
public AccountMasterObject[] getODDeposit(int shno){
	ResultSet rs=null;
	Statement stmt=null;
	Connection con=null;
	String ret[]=null;
	AccountMasterObject[] am=null;
	try{
		System.out.println("inside getODDeposit method --> Account No==>>"+shno);
		con=getConnection();
		stmt=con.createStatement();
		//int i=0;
		rs=stmt.executeQuery("select dm.ac_type,dm.ac_no,m.moduleabbr from DepositMaster dm,ShareMaster sm,Modules m where dm.cid=sm.cid and sm.ac_no="+shno+" and dm.ac_type=m.modulecode and dm.close_date is null and dm.ve_tml is not null");
		if(rs!=null && rs.next())
		{
			rs.beforeFirst();
			
			if(rs.next())
			{	
					System.out.println("Inside FrontCounterBean");
					rs.last();
					am=new AccountMasterObject[rs.getRow()];
					rs.beforeFirst();
			}
			
			int i=0;
			while(rs.next())
			{	
				System.out.println("inside while loop");
				am[i]=new AccountMasterObject();
				
		        am[i].setAccType(rs.getString(1)+"--"+rs.getString(3));
		        am[i].setAccNo(rs.getInt("ac_no"));
		        i++;
			}
			
		}
	}catch(Exception e){
		e.printStackTrace();
		ctx.setRollbackOnly();
		return null;
		}
	finally{
		try{                
			con.close();
		}catch(Exception exception){exception.printStackTrace();}
	}
	return am;
}
//Sign Inst

//Added by Mohsin for interestDetails for individual accounts;

}


