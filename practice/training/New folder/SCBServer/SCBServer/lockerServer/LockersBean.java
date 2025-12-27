
package lockerServer;

import java.awt.Container;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import masterObject.lockers.LockerDetailsObject;
import masterObject.lockers.LockerMasterObject;
import masterObject.lockers.LockerTransObject;

import commonServer.CommonLocal;  
import commonServer.CommonLocalHome;
import commonServer.GLTransObject;

import exceptions.DateFormatException;
import exceptions.NomineeNotCreatedException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;
import exceptions.SignatureNotInsertedException;
import masterObject.general.AccountObject;
import masterObject.general.NomineeObject;
import general.Validations;

public class LockersBean implements javax.ejb.SessionBean
{
    static final long serialVersionUID = 1L;
               
	Container c; 
    CommonLocalHome commonhome_local;
	CommonLocal commonremote_local;
	LockersHome lockershome;
	LockersRemote lockersremote; 
	
	javax.sql.DataSource obj_dataSource=null;
	SessionContext obj_sessionContext=null;
	
	//private String cid;
	
	
	
	public LockersBean() throws RemoteException
	{
        try
        
		{	
			Context ctx=new InitialContext();			
			obj_dataSource=(javax.sql.DataSource)ctx.lookup("java:MySqlDS");
		}
        catch(NamingException namingException)
        {
            namingException.printStackTrace();
        }
	}
	
	public void setSessionContext(SessionContext arg0) throws EJBException,RemoteException 
	{
		obj_sessionContext=arg0;
	}
	
	public void ejbCreate()
	{
		try 
		{
			//(CommonLocalHome)HomeFactory.getFactory().lookUpHome("COMMONLOCAL");
    		commonhome_local =(CommonLocalHome)new InitialContext().lookup("COMMONLOCALWEB");
			commonremote_local = commonhome_local.create();
			
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
	}
	
	public void ejbRemove() throws EJBException, RemoteException {}
	
	public void ejbActivate() throws EJBException, RemoteException 
	{
		Context jndiContext = null;
		
		try 
		{
			jndiContext = new InitialContext();
		} 
		catch(NamingException e) 
		{
			e.printStackTrace();
		}
		
		
		try 
		{
			commonhome_local =(CommonLocalHome)jndiContext.lookup("COMMONLOCAL");
		} 
		catch(NamingException e1) 
		{
			e1.printStackTrace();
		}
		
		try 
		{
			commonremote_local = commonhome_local.create();
		} 
		catch(CreateException e2) 
		{
			e2.printStackTrace();
		}
	}	
	
	public void ejbPassivate() throws EJBException, RemoteException {}

	//Business Methods
	
	
	
	/**
	 * Locker Issue - Insertion, Updation & Verification of Lockers.
	 */
	public int storeLockerMaster(LockerMasterObject lockerMasterObject,int int_verify) throws NomineeNotCreatedException, SignatureNotInsertedException
	{
	    System.out.println("inside store Locker master");
	    
		int lock_no=0;	
		PreparedStatement ps=null,ps1=null;
		ResultSet rs2;
		int acc_no=0;
		int trn_seq = 0;//ship.....19/01/2006
		
		Connection conn=null;
		Statement stmt=null,stmt2=null,stmt3=null;
		
		try
		{
			conn=getConnection();
			
			if(conn==null)
				throw new SQLException();
			
            stmt3=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt2=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);	
					
            if(int_verify==1)
			{          
                //ship........
                System.out.println("inside storeLkMstr....1");
                
                if(lockerMasterObject.getLockerPW().equals(""))
                    ps = conn.prepareStatement("insert into LockerMaster values(?,?,?,?,?,?,null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+lockerMasterObject.uv.getUserDate()+"',null,null,null,'F')");
                else
                    ps = conn.prepareStatement("insert into LockerMaster values(?,?,?,?,?,?,MD5('"+lockerMasterObject.getLockerPW()+"'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+lockerMasterObject.uv.getUserDate()+"',null,null,null,'F')");
                				
                if(lockerMasterObject.getLockerAcNo()==0)
				{ 
                    System.out.println("inside storeLkMstr....2");
                    rs2=stmt2.executeQuery("select lst_acc_no from Modules where modulecode='"+lockerMasterObject.getLockerAcType()+"'");
					
                    while(rs2.next())
						acc_no= rs2.getInt(1);
                    
					rs2.close();
					
					lockerMasterObject.setLockerAcNo(acc_no+1);
					
					if(stmt2.executeUpdate("update Modules set lst_acc_no="+(acc_no+1)+" where modulecode='"+lockerMasterObject.getLockerAcType()+"'")==0)
					    throw new SQLException();
				}
                else if(lockerMasterObject.getLockerAcNo()>0)
				{
                    //ship......06/02/2006
                    ResultSet rs_lm = stmt2.executeQuery("select * from LockerMaster where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"");
                    if(rs_lm.next())
                        if(stmt.executeUpdate("delete from LockerMaster where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"")==0)
                            throw new SQLException();
                        
                    ResultSet rs_lt = stmt2.executeQuery("select * from LockerTransaction where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and trn_seq=1 and op_date='"+lockerMasterObject.getTrnDate()+"' and trn_type='R' and cd_ind='C'");
                    if(rs_lt.next())
                        if(stmt.executeUpdate("delete from LockerTransaction where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and trn_seq=1 and op_date='"+lockerMasterObject.getTrnDate()+"' and trn_type='R' and cd_ind='C'")==0)
                            throw new SQLException();
						
                    ResultSet rs_si = stmt2.executeQuery("select * from SignatureInstruction where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"");
                    if(rs_si.next())
                        if(stmt.executeUpdate("delete from SignatureInstruction where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"")==0)
                            throw new SQLException();
                    //////////
					
					//ship....04/02/2006
					ResultSet rs_jt = stmt2.executeQuery("select * from JointHolders where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"");
					if(rs_jt.next())
					    if(stmt.executeUpdate("delete from JointHolders where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"")==0)
					        throw new SQLException();
					
					ResultSet rs_nm = stmt3.executeQuery("select * from NomineeMaster where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"");
					if(rs_nm.next())
					    if(stmt.executeUpdate("delete from NomineeMaster where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"")==0)
					        throw new SQLException();
					/////////
					    
					//ship.....04/03/2006
					ResultSet rs_od = stmt3.executeQuery("select * from ODInterestDetails where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"");
					if(rs_od.next())
					    if(stmt.executeUpdate("delete from ODInterestDetails where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"")==0)
					        throw new SQLException();
					//////////
				}
				
                System.out.println("inside storeLkMstr....3");
				ps.setString(1,lockerMasterObject.getLockerAcType());
				ps.setInt(2,lockerMasterObject.getLockerAcNo());
				ps.setInt(3,lockerMasterObject.getCid());
				ps.setInt(4,lockerMasterObject.getMailingAddress());
				ps.setString(5,lockerMasterObject.getLockerType());
				ps.setInt(6,lockerMasterObject.getLockerNo());
								
				//ship....06/02/2006
				if(lockerMasterObject.getJointCid()!=null)
				{
				    System.out.println("inside storeLkMstr....4");
					int addrtype[]=lockerMasterObject.getAddr();
					int jcids[]=lockerMasterObject.getJointCid();
					int no_jt_hldr = jcids.length;
					System.out.println("2........"+no_jt_hldr);
					for(int i=0;i<no_jt_hldr;i++)
					{	
						if(stmt.executeUpdate("insert into JointHolders values('"+lockerMasterObject.getLockerAcType()+"','"+lockerMasterObject.getLockerAcNo()+"','"+jcids[i]+"','"+addrtype[i]+"')")==0)
						    throw new SQLException();
					}
					ps.setInt(7,no_jt_hldr);
				}
				else
				    ps.setInt(7,0);
				
				System.out.println("inside storeLkMstr....5");
				
				ps.setString(8,lockerMasterObject.getAllotDate());
				ps.setString(9,lockerMasterObject.getMatDate());
				ps.setInt(10,lockerMasterObject.getReqdMonths());
				ps.setInt(11,lockerMasterObject.getRequiredDays());
				ps.setString(12,lockerMasterObject.getOprInstrn());
				ps.setInt(13,0);
				ps.setString(14,null);
				ps.setString(15,null);
				ps.setString(16,lockerMasterObject.getShareCode());
				ps.setInt(17,lockerMasterObject.getMemberNo());
								
				ps.setInt(18,lockerMasterObject.getNoOfSecurities());
                
				System.out.println("inside storeLkMstr....6");
				
				ps.setString(19,lockerMasterObject.getAutoExtn());
				
				ps.setString(20,lockerMasterObject.getIntrAcTy());
				ps.setInt(21,lockerMasterObject.getIntrAcNo());
				
				System.out.println("inside storeLkMstr....7");
				
				NomineeObject nom[]=lockerMasterObject.getNomineeDetails();
				if(nom!=null)
				{	
					System.out.println("1........");
					int id=commonremote_local.storeNominee(nom,lockerMasterObject.getLockerAcType(),lockerMasterObject.getLockerAcNo(),lockerMasterObject.getAllotDate());
					ps.setInt(22,id);
				}
				else			
					ps.setInt(22,0);//nom regno
				
				ps.setString(23,lockerMasterObject.uv.getUserTml());
				ps.setString(24,lockerMasterObject.uv.getUserId());
                
				//i should check ps add throw SQLException here 			
				int created=ps.executeUpdate();
				System.out.println("created = "+created);
				
				if(created==1)
				{
				    //ship......03/02/2006
	                ps1 = conn.prepareStatement("insert into LockerTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+lockerMasterObject.uv.getUserDate()+"',null,null,null)");
	                
	                ps1.setString(1,lockerMasterObject.getLockerAcType());
	                ps1.setInt(2,lockerMasterObject.getLockerAcNo());
	                ps1.setInt(3,lockerMasterObject.getLockerNo());
	                ps1.setString(4,lockerMasterObject.getRentBy());
	                
	                if(lockerMasterObject.getRentBy().equals("T"))
					{
						System.out.println("********inside if(transfer)******");
						ps1.setString(5,lockerMasterObject.getTransAcType());	
						ps1.setInt(6,lockerMasterObject.getTransAcNo());
					}
					else
					{
						System.out.println("********inside else(scroll)*******");
						ps1.setString(5,lockerMasterObject.getTransAcType());	
						ps1.setInt(6,lockerMasterObject.getTransAcNo());
					}
	                
	                ps1.setDouble(7,lockerMasterObject.getRentColl());
	                ps1.setString(8,lockerMasterObject.getRentUpto());
	                ps1.setInt(9,1);//trn_seq
	                ps1.setString(10,"R");//trn_type
	                ps1.setString(11,"C");//cd_ind
	                ps1.setString(12,null);//oper_by
	                ps1.setString(13,lockerMasterObject.getAllotDate());//op_date
	                ps1.setString(14,null);//time_in
	                ps1.setString(15,null);//time_out
	                
	                ps1.setString(16,lockerMasterObject.uv.getUserTml());
					ps1.setString(17,lockerMasterObject.uv.getUserId());
					
					int create=ps1.executeUpdate();
	                
					if(create==1)
					{
	                ////////
					    //ship....25/02/2006
					    for(int d = 0;d<lockerMasterObject.getNoOfSecurities();d++)
					    {
					        Vector deposit = lockerMasterObject.getDeposits();
					        String ac_type = null;
					        String ac_no = null;
					        
					        System.out.println("getDeposits = "+deposit.get(d));
					        
					        StringTokenizer st = new StringTokenizer(String.valueOf(deposit.get(d))," ");
					        while(st.hasMoreTokens())
					        {
					            ac_type = st.nextToken();
					            ac_no = st.nextToken();
					            System.out.println("ac_type = "+ac_type);
					            System.out.println("ac_no = "+ac_no);
					        }
					        
					        PreparedStatement ps_dep=conn.prepareStatement("insert into ODInterestDetails values('"+lockerMasterObject.getLockerAcType()+"',"+lockerMasterObject.getLockerAcNo()+",'"+ac_type+"',"+ac_no+",null,null,null,null,null,'"+lockerMasterObject.getAllotDate()+"','"+lockerMasterObject.getMatDate()+"')");
					    
					        ps_dep.executeUpdate();
					    }
					    ////////////
					    
					    //ship.....05/03/2007
                        ResultSet rs_daycash = stmt.executeQuery("select * from DayCash where trn_date='"+lockerMasterObject.getAllotDate()+"' and ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and attached='T' and ve_tml is null and ve_user is null and ve_date is null");
                        
                        if(rs_daycash.next())
                        {
                            if(stmt.executeUpdate("update DayCash set ac_no=0,attached='F' where trn_date='"+lockerMasterObject.getAllotDate()+"' and ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and attached='T' and ve_tml is null and ve_user is null and ve_date is null")==0)
                                throw new SQLException();
                        }
                        //////////////
                        
					    if(lockerMasterObject.getRentBy().equals("C"))
						{
							if(stmt.executeUpdate("update DayCash set ac_no="+lockerMasterObject.getLockerAcNo()+",attached='T' where trn_date='"+lockerMasterObject.getAllotDate()+"' and scroll_no="+lockerMasterObject.getTransAcNo()+" and ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no=0 and attached='F' and ve_tml is null and ve_user is null and ve_date is null")==0)
							    throw new SQLException();
						}
						
						if(lockerMasterObject.getSigObj()!=null)
						{
							commonremote_local.storeSignatureDetails(lockerMasterObject.getSigObj(),lockerMasterObject.getLockerAcNo());
						}
					}
				}//end....created==1
				
				System.out.println("1.......="+lockerMasterObject.getNoOfJntHldrs());
				
				return lockerMasterObject.getLockerAcNo();
                ////////////
			}//end.....int_verify==1
            
			else if(int_verify==2)
			{    
			    ////////ship....19/01/2006
			    stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			    
			    ResultSet rs_cash = null,rs_transfer = null,rs_locker = null;
				
				GLTransObject trnobj=new GLTransObject();
				
			    if(stmt.executeUpdate("update LockerMaster set ve_user='"+lockerMasterObject.uv.getVerId()+"',ve_tml='"+lockerMasterObject.uv.getVerTml()+"',ve_date='"+lockerMasterObject.uv.getVerDate()+"' where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and ve_user is null and ve_tml is null and ve_date is null")==0)
                    throw new SQLException();
			    
			    //ship.....31/01/2006
				if(stmt.executeUpdate("update Lockers set locker_st='T' where locker_no="+lockerMasterObject.getLockerNo()+"")==0)
                    throw new SQLException();
				///
				
				//ship.....03/02/2006
				if(stmt.executeUpdate("update LockerTransaction set ve_user='"+lockerMasterObject.uv.getVerId()+"',ve_tml='"+lockerMasterObject.uv.getVerTml()+"',ve_date='"+lockerMasterObject.uv.getVerDate()+"' where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and ve_user is null and ve_tml is null and ve_date is null and op_date='"+lockerMasterObject.getTrnDate()+"'")==0)
                    throw new SQLException();
				///////
				
				//ship....04/03/2006
				if(lockerMasterObject.getDeposits()!=null)
				{
				    for(int d = 0;d<lockerMasterObject.getNoOfSecurities();d++)
				    {
				        Vector deposit = lockerMasterObject.getDeposits();
				        String ac_type = null;
				        String ac_no = null;
				           
				        System.out.println("getDeposits verify= "+deposit.get(d));
				        
				        StringTokenizer st = new StringTokenizer(String.valueOf(deposit.get(d))," ");
				        while(st.hasMoreTokens())
				        {
				            ac_type = st.nextToken();
				            ac_no = st.nextToken();
				            System.out.println("dep_ac_type verify = "+ac_type);
				            System.out.println("dep_ac_no verify = "+ac_no);
				            
				            if(stmt.executeUpdate("update DepositMaster set ln_availed='T',ln_ac_type='"+lockerMasterObject.getLockerAcType()+"',ln_ac_no="+lockerMasterObject.getLockerAcNo()+" where ac_type='"+ac_type+"' and ac_no="+ac_no+" and ln_availed='F'")==0)
			                    throw new SQLException();
				        }
				    }
				}
				/////////
				
				if(lockerMasterObject.getRentBy().equals("C"))
				{
				    System.out.println("inside lk_verify...Cash");
				    
				    if(stmt.executeUpdate("update DayCash set ve_user='"+lockerMasterObject.uv.getVerId()+"',ve_tml='"+lockerMasterObject.uv.getVerTml()+"',ve_date='"+lockerMasterObject.uv.getVerDate()+"' where scroll_no="+lockerMasterObject.getTransAcNo()+" and trn_date='"+lockerMasterObject.getTrnDate()+"' and attached='T' and ve_user is null and ve_tml is null and ve_date is null")==0)
                        throw new SQLException();
				    
				    //inserting into GLTran.....Cash Balance
				    rs_cash = stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1019001' and code=1");
				    
				    if(rs_cash.next())
				    {
				        trnobj.setGLCode(rs_cash.getString("gl_code"));
			            trnobj.setTrnDate(lockerMasterObject.getTrnDate());
			            trnobj.setGLType(rs_cash.getString("gl_type"));
			            trnobj.setTrnMode("C");
			            trnobj.setAmount(lockerMasterObject.getRentColl());
			            trnobj.setCdind("D");
			            trnobj.setAccType(lockerMasterObject.getLockerAcType());
			            trnobj.setAccNo(String.valueOf(lockerMasterObject.getLockerAcNo()));
			            trnobj.setTrnSeq(1);//ship......20/12/2005
			            trnobj.setTrnType("R");
			            trnobj.setRefNo(lockerMasterObject.getTransAcNo());
			            trnobj.setVtml(lockerMasterObject.uv.getVerTml());
			            trnobj.setVid(lockerMasterObject.uv.getVerId());
			            trnobj.setVDate(lockerMasterObject.uv.getVerDate());
			            
			            if(commonremote_local.storeGLTransaction(trnobj)==1)
			            {			                
			                //inserting into GLTran......Locker Account
			                rs_locker = stmt.executeQuery("select gp.gl_code,gk.gl_type,gp.mult_by from GLPost gp,GLKeyParam gk where gp.ac_type='"+lockerMasterObject.getLockerAcType()+"' and gk.ac_type='"+lockerMasterObject.getLockerAcType()+"' and trn_type='R' and cr_dr='C' and gk.code=1");
			                
						    if(rs_locker.next())
						    {
						        trnobj.setGLCode(rs_locker.getString("gp.gl_code"));
					            trnobj.setTrnDate(lockerMasterObject.getTrnDate());
					            trnobj.setGLType(rs_locker.getString("gk.gl_type"));
					            trnobj.setTrnMode("C");
					            trnobj.setAmount(lockerMasterObject.getRentColl()*rs_locker.getInt("gp.mult_by"));
					            trnobj.setCdind("C");
					            trnobj.setAccType(lockerMasterObject.getLockerAcType());
					            trnobj.setAccNo(String.valueOf(lockerMasterObject.getLockerAcNo()));
					            trnobj.setTrnSeq(1);//ship......20/12/2005
					            trnobj.setTrnType("R");
					            trnobj.setRefNo(lockerMasterObject.getTransAcNo());
					            trnobj.setVtml(lockerMasterObject.uv.getVerTml());
					            trnobj.setVid(lockerMasterObject.uv.getVerId());
					            trnobj.setVDate(lockerMasterObject.uv.getVerDate());
					            
					            commonremote_local.storeGLTransaction(trnobj);
						    }//end...rs_locker
			            }
				    }//end...rs_cash
				}
				else if(lockerMasterObject.getRentBy().equals("T"))
				{
				    System.out.println("inside lk_verify...Transfer");
				    
				    ResultSet rs_tran = stmt.executeQuery("select trn_seq,cl_bal from AccountTransaction where ac_type='"+lockerMasterObject.getTransAcType()+"' and ac_no="+lockerMasterObject.getTransAcNo()+" order by trn_seq desc");
				    
	                if(rs_tran.next())
	                {
	                    trn_seq = rs_tran.getInt(1)+1;
	                    
		                if(stmt.executeUpdate("insert into AccountTransaction values('"+lockerMasterObject.getTransAcType()+"',"+lockerMasterObject.getTransAcNo()+",'"+lockerMasterObject.getTrnDate()+"','P',"+trn_seq+","+lockerMasterObject.getRentColl()+",'T','"+lockerMasterObject.uv.getVerTml()+"','D',null,null,'"+lockerMasterObject.getLockerAcType()+" "+lockerMasterObject.getLockerAcNo()+"',0,null,"+(rs_tran.getDouble("cl_bal")-lockerMasterObject.getRentColl())+",0,'"+lockerMasterObject.uv.getUserTml()+"','"+lockerMasterObject.uv.getUserId()+"','"+lockerMasterObject.uv.getUserDate()+"','"+lockerMasterObject.uv.getVerTml()+"','"+lockerMasterObject.uv.getVerId()+"','"+lockerMasterObject.uv.getVerDate()+"')")==0)
		                    throw new SQLException();
		                
		                if(stmt.executeUpdate("update AccountMaster set last_tr_seq="+trn_seq+",last_tr_date='"+lockerMasterObject.getTrnDate()+"' where ac_type='"+lockerMasterObject.getTransAcType()+"' and ac_no="+lockerMasterObject.getTransAcNo()+"")==0)
		                    throw new SQLException();
	                }
	                else
	                    return -1;
				    
				    //inserting into GLTran.....transfer a/c
				    rs_transfer = stmt.executeQuery("select gp.gl_code,gk.gl_type,gp.mult_by from GLPost gp,GLKeyParam gk where gp.ac_type='"+lockerMasterObject.getTransAcType()+"' and gk.ac_type='"+lockerMasterObject.getTransAcType()+"' and trn_type='P' and cr_dr='D' and gk.code=1");
				    
				    if(rs_transfer.next())
				    {
				        System.out.println("inserting into GLTran.......transfer a/c");
				        trnobj.setGLCode(rs_transfer.getString("gp.gl_code"));
			            trnobj.setTrnDate(lockerMasterObject.getTrnDate());
			            trnobj.setGLType(rs_transfer.getString("gk.gl_type"));
			            trnobj.setTrnMode("T");
			            trnobj.setAmount(lockerMasterObject.getRentColl()*rs_transfer.getInt("gp.mult_by"));
			            trnobj.setCdind("D");
			            trnobj.setAccType(lockerMasterObject.getTransAcType());
			            trnobj.setAccNo(String.valueOf(lockerMasterObject.getTransAcNo()));
			            trnobj.setTrnSeq(trn_seq);//ship......20/12/2005
			            trnobj.setTrnType("P");
			            trnobj.setRefNo(0);
			            trnobj.setVtml(lockerMasterObject.uv.getVerTml());
			            trnobj.setVid(lockerMasterObject.uv.getVerId());
			            trnobj.setVDate(lockerMasterObject.uv.getVerDate());
			            
			            if(commonremote_local.storeGLTransaction(trnobj)==1)
			            {
			                //inserting into GLTran......Locker Account
			                rs_locker = stmt.executeQuery("select gp.gl_code,gk.gl_type,gp.mult_by from GLPost gp,GLKeyParam gk where gp.ac_type='"+lockerMasterObject.getLockerAcType()+"' and gk.ac_type='"+lockerMasterObject.getLockerAcType()+"' and gp.trn_type='R' and gp.cr_dr='C' and gk.code=1");
			                
						    if(rs_locker.next())
						    {
						        System.out.println("inserting into GLTran......Locker Account...start");
						        trnobj.setGLCode(rs_locker.getString("gp.gl_code"));
					            trnobj.setTrnDate(lockerMasterObject.getTrnDate());
					            trnobj.setGLType(rs_locker.getString("gk.gl_type"));
					            trnobj.setTrnMode("T");
					            trnobj.setAmount(lockerMasterObject.getRentColl()*rs_locker.getInt("gp.mult_by"));
					            trnobj.setCdind("C");
					            trnobj.setAccType(lockerMasterObject.getLockerAcType());
					            trnobj.setAccNo(String.valueOf(lockerMasterObject.getLockerAcNo()));
					            trnobj.setTrnSeq(1);//ship......20/12/2005
					            trnobj.setTrnType("R");
					            trnobj.setRefNo(0);
					            trnobj.setVtml(lockerMasterObject.uv.getVerTml());
					            trnobj.setVid(lockerMasterObject.uv.getVerId());
					            trnobj.setVDate(lockerMasterObject.uv.getVerDate());
					            
					            commonremote_local.storeGLTransaction(trnobj);
					            System.out.println("inserting into GLTran......Locker Account...end");
						    }//end...rs_locker
						    else
						        return -1;
			            }
			            else
					        return -1;
				    }//end...rs_transfer
				    else
				        return -1;
				}
				/////////
				lock_no = lockerMasterObject.getLockerAcNo();
			}//end....int_verify==2
		}		
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
			obj_sessionContext.setRollbackOnly();			
		} 
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
			    e.printStackTrace();
			}
		}
		
		return lock_no;
	 }
	
     /**
      * Deletes the Locker details only if it is not yet verified.
      * Locker Issue, Locker Extension.
      */	
     public void deleteLockerAccount(LockerMasterObject lockerMasterObject,int type) throws RecordNotUpdatedException 
     {
         System.out.println("ac no"+lockerMasterObject.getLockerAcType());
         System.out.println("ac type"+lockerMasterObject.getLockerAcNo());
         
         Statement stmt=null;
         Connection conn=null;
        
         try
         {
            conn=getConnection();
            
            if(conn==null)
                throw new SQLException();
        
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
         
            if(type==1)//for locker issue.....ship.....03/04/2006
            {
                ResultSet rs_lm = stmt.executeQuery("select * from LockerMaster where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"");
                if(rs_lm.next())
                    if(stmt.executeUpdate("delete from LockerMaster where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"")==0)
                        throw new SQLException();
                    
                ResultSet rs_lt = stmt.executeQuery("select * from LockerTransaction where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and trn_seq=1 and op_date='"+lockerMasterObject.getTrnDate()+"' and trn_type='R' and cd_ind='C' and ve_tml is null and ve_user is null and ve_date is null");
                if(rs_lt.next())
                    if(stmt.executeUpdate("delete from LockerTransaction where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and trn_seq=1 and op_date='"+lockerMasterObject.getTrnDate()+"' and trn_type='R' and cd_ind='C' and ve_tml is null and ve_user is null and ve_date is null")==0)
                        throw new SQLException();
					
                ResultSet rs_si = stmt.executeQuery("select * from SignatureInstruction where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"");
                if(rs_si.next())
                    if(stmt.executeUpdate("delete from SignatureInstruction where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"")==0)
                        throw new SQLException();
                    
				ResultSet rs_jt = stmt.executeQuery("select * from JointHolders where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"");
				if(rs_jt.next())
				    if(stmt.executeUpdate("delete from JointHolders where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"")==0)
				        throw new SQLException();
				
				ResultSet rs_nm = stmt.executeQuery("select * from NomineeMaster where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"");
				if(rs_nm.next())
				    if(stmt.executeUpdate("delete from NomineeMaster where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"")==0)
				        throw new SQLException();
				    
				ResultSet rs_od = stmt.executeQuery("select * from ODInterestDetails where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and from_date='"+lockerMasterObject.getTrnDate()+"'");
				if(rs_od.next())
				    if(stmt.executeUpdate("delete from ODInterestDetails where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and from_date='"+lockerMasterObject.getTrnDate()+"'")==0)
				        throw new SQLException();
                
                if(lockerMasterObject.getRentBy().equals("C"))
				{
					if(stmt.executeUpdate("update DayCash set attached='F' where scroll_no="+lockerMasterObject.getTransAcNo()+" and ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and trn_date='"+lockerMasterObject.getTrnDate()+"'")==0)
					    throw new SQLException();
				}
                
                /*stmt.addBatch("delete from LockerMaster where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo());
                stmt.addBatch("delete from SignatureInstruction where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo());
                stmt.addBatch("delete from JointHolders where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo());
                stmt.addBatch("delete from NomineeMaster where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo());
                                
                if(stmt.executeBatch().length<4)
                    throw new RecordNotUpdatedException();*/
            }
            else if(type==2)//for locker extension.....ship.....03/04/2006
            {
                ResultSet rs_lt = stmt.executeQuery("select * from LockerTransaction where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and trn_seq>1 and op_date='"+lockerMasterObject.getTrnDate()+"' and trn_type='R' and cd_ind='C' and ve_tml is null and ve_user is null and ve_date is null");
                if(rs_lt.next())
                    if(stmt.executeUpdate("delete from LockerTransaction where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and trn_seq>1 and op_date='"+lockerMasterObject.getTrnDate()+"' and trn_type='R' and cd_ind='C' and ve_tml is null and ve_user is null and ve_date is null")==0)
                        throw new SQLException();
                    
                ResultSet rs_od = stmt.executeQuery("select * from ODInterestDetails where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and from_date='"+lockerMasterObject.getTrnDate()+"'");
                if(rs_od.next())
    				if(stmt.executeUpdate("delete from ODInterestDetails where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and from_date='"+lockerMasterObject.getTrnDate()+"'")==0)
    				    throw new SQLException();
                    
                if(lockerMasterObject.getRentBy().equals("C"))
    			{
    				if(stmt.executeUpdate("update DayCash set attached='F' where scroll_no="+lockerMasterObject.getTransAcNo()+" and ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and trn_date='"+lockerMasterObject.getTrnDate()+"'")==0)
    					throw new SQLException();
    			}
            }
         }
         catch(SQLException sqlexception)
         {
             sqlexception.printStackTrace();
             obj_sessionContext.setRollbackOnly();
         }
         finally
         {
            try
            {
                 conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }        
     }
     
     /**
      * Retrieves the details of the given Locker A/c.
      */
     public LockerMasterObject getLockerMaster(String string_locker_type,int int_locker_no,int int_type,String date)
     {       
        System.out.println("**************getLockerMaster Begin*************");
        System.out.println("string_locker_type is "+string_locker_type);
        System.out.println("int_locker_no"+int_locker_no);  
        System.out.println("int_type"+int_type);
            
        Statement stmt=null,stat=null,st_od = null,st_lktrn = null,stmt_lk = null;
        Connection conn=null;
        ResultSet rs = null,rs1 = null,rs_od = null,rs_lktrn = null,rs_fa = null;
        
        //ship......09/03/2006
        String mat_date = null,rent_upto = null;
        //////////
        
        //ship.....06/04/2006
        int req_mths = 0,req_days = 0;
        //////////
        
        LockerMasterObject lockerMasterObject=new LockerMasterObject();
        
        try
        {
            conn=getConnection();
            
            if(conn==null)
                throw new SQLException();
        
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt_lk=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stat=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            st_lktrn=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            st_od=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if(int_type==0 || int_type==3 || int_type==6)//ship....08/03/2006....added int_type==3 for lk_extension
            {
                //ship.....commented.....03/02/2006
                 //rs=stmt.executeQuery("select lk.*,l.key_no,l.descptn,l.locker_st from LockerMaster lk,Lockers l where lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lk.locker_no=l.locker_no");
                if(int_type==0)//lk issue
                         	rs=stmt.executeQuery("select lk.*,l.*,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from LockerMaster lk,LockerTransaction lt,Lockers l,CustomerMaster cm where lk.cid=cm.cid and lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lk.locker_no=l.locker_no and lt.ac_type='"+string_locker_type+"' and lt.ac_no="+int_locker_no+" and lk.locker_no=lt.locker_no and lk.allot_dt=lt.op_date and lk.close_dt is null and lt.time_in is null and lt.time_out is null");
              
                else if(int_type==3)//lk extn
             
                	rs=stmt.executeQuery("select lk.*,l.*,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from LockerMaster lk,LockerTransaction lt,Lockers l,CustomerMaster cm where lk.cid=cm.cid and lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lk.locker_no=l.locker_no and lt.ac_type='"+string_locker_type+"' and lt.ac_no="+int_locker_no+" and lk.locker_no=lt.locker_no and lk.close_dt is null and lt.rent_by is not null and lt.rent_amt is not null and lt.rent_upto is not null and lt.time_in is null and lt.time_out is null order by lt.trn_seq desc limit 1");
                    
                else if(int_type==6)//lk mst updation
                    rs=stmt.executeQuery("select lk.*,l.*,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from LockerMaster lk,LockerTransaction lt,Lockers l,CustomerMaster cm where lk.cid=cm.cid and lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lk.locker_no=l.locker_no and lt.ac_type='"+string_locker_type+"' and lt.ac_no="+int_locker_no+" and lk.locker_no=lt.locker_no and lk.allot_dt=lt.op_date and lk.close_dt is null and lt.time_in is null and lt.time_out is null limit 1");
                /////////
                
                 rs.last();
                 System.out.println("length in server  : "+rs.getRow());
              
                 if(rs.getRow()==0)
                 {
                     lockerMasterObject=null;
                     return lockerMasterObject;
                 }
                 else
                 {
                	
                	
                     rs.beforeFirst();
                     if(rs.next())
                     {
                         System.out.println("inside getLKM...rent_by = "+rs.getString("lt.rent_by"));
                         
                         lockerMasterObject.setTrnDate(rs.getString("lt.op_date"));//ship....12/07/2006
                         lockerMasterObject.setRentBy(rs.getString("lt.rent_by"));
                         
                         rent_upto = rs.getString("lt.rent_upto");
                         
                         //ship........06/04/2006
                         req_mths = rs.getInt("lk.req_mths");
                         req_days = rs.getInt("lk.required_days");
                         /////////
                         
                         lockerMasterObject.setTrnType(rs.getString("lt.trn_type"));
                         
                         if(lockerMasterObject.getRentBy().equals("T"))
                         {
                             System.out.println("inside Transfer...");
                             lockerMasterObject.setTransAcType(rs.getString("lt.trf_acty"));
                             lockerMasterObject.setTransAcNo(rs.getInt("lt.trf_acno"));
                         }
                         else if(lockerMasterObject.getRentBy().equals("C"))
                         {
                             System.out.println("inside Cash.....");
                             
                             rs1=stat.executeQuery("select * from DayCash where ac_type='"+string_locker_type+"' and ac_no="+int_locker_no+" and trn_date='"+date+"' and ve_user is null and ve_tml is null and ve_date is null");
                             rs1.last();
                             System.out.println("no of rows = "+rs1.getRow());
                             rs1.beforeFirst();
                             
                             if(rs1.next())
                             {
                                 System.out.println("scroll_no = "+rs1.getInt("scroll_no"));
                                 lockerMasterObject.setTransAcType(null);
                                 lockerMasterObject.setTransAcNo(rs1.getInt("scroll_no"));
                             }
                             else
                             {
                                 lockerMasterObject.setTransAcType(null);
                                 lockerMasterObject.setTransAcNo(0);
                             }
                         }

                         lockerMasterObject.setRentUpto(rent_upto);
                         
                         //ship......15/03/2007
                         if(int_type==3)//lk_ext
                         {
                             double total_rent = 0.0;
                             
                             rs_lktrn = st_lktrn.executeQuery("select rent_amt from LockerTransaction where ac_type='"+string_locker_type+"' and ac_no="+int_locker_no+" and rent_amt is not null and ve_user is not null and ve_tml is not null and ve_date is not null");
                             
                             while(rs_lktrn.next())
                             {
                                 total_rent = total_rent+rs_lktrn.getDouble("rent_amt");
                             }
                             //NEED TO INCLUDE IMP
                             lockerMasterObject.setTotalRentColl(String.valueOf(total_rent));
                         }
                         
                         lockerMasterObject.setRentColl(rs.getDouble("lt.rent_amt"));
                     }
                     else 
                         lockerMasterObject=null;
                     
                     rs.beforeFirst();
                 }
            }
            //ship.......01/03/2006
            /*else if(int_type==1 || int_type==2)
            {
                if(int_type==1) 
                    rs=stmt.executeQuery("select lk.*,l.key_no,l.descptn,l.locker_st,lt.time_in,lt.time_out,lt.ve_user from LockerMaster lk,LockerTransaction lt,Lockers l where lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lt.ac_type=lk.ac_type and lt.ac_no=lk.ac_no and (lt.time_out is null or lt.ve_user is null)  and lk.locker_no=l.locker_no");
                else if(int_type==2)    
                    rs=stmt.executeQuery("select lk.*,l.key_no,l.descptn,l.locker_st,lt.time_in,lt.time_out,lt.ve_user from LockerMaster lk,LockerTransaction lt,Lockers l where lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lt.ac_type=lk.ac_type and lt.ac_no=lk.ac_no and lt.ve_user is null and lk.locker_no=l.locker_no");
                
                if(rs.next())
                {
                    lockerMasterObject.setTimeIn(rs.getString("time_in"));
                    lockerMasterObject.setTimeOut(rs.getString("time_out"));
                    lockerMasterObject.uv.setVerId(rs.getString("lt.ve_user"));
                    rs.beforeFirst();
                }
                else
                {
                    lockerMasterObject.setTimeIn(null);
                    lockerMasterObject.setTimeOut(null);
                    lockerMasterObject.setVerId("fdhgfh");//why hardcoded here
                    
                    if(!rs.next())
                    {
                        rs=stmt.executeQuery("select lk.*,l.key_no,l.descptn,l.locker_st from LockerMaster lk,Lockers l where lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lk.locker_no=l.locker_no");// and l.locker_st='T'  ");
                    }
                }
            }*/
            
            else if(int_type==1 || int_type==2)//for locker operation 1 - submit, 2 - verify
            {
                //ship.....07/10/2006
                /*if(int_type==1) 
                    rs=stmt.executeQuery("select lk.*,l.*,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from LockerMaster lk,LockerTransaction lt,Lockers l,CustomerMaster cm where lk.cid=cm.cid and lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lt.ac_type=lk.ac_type and lt.ac_no=lk.ac_no and rent_by is null and trf_acty is null and trf_acno is null and rent_amt is null and rent_upto is null and lt.ve_user is null and lt.ve_tml is null and lt.ve_date is null and lk.locker_no=l.locker_no and op_date='"+date+"'");
                else if(int_type==2)    
                    rs=stmt.executeQuery("select lk.*,l.*,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from LockerMaster lk,LockerTransaction lt,Lockers l,CustomerMaster cm where lk.cid=cm.cid and lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lt.ac_type=lk.ac_type and lt.ac_no=lk.ac_no and lt.ve_user is null and lk.locker_no=l.locker_no");*/
                
                if(int_type==1)    
                {	System.out.println("line num 854");
                	rs=stmt.executeQuery("select lk.*,l.*,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from LockerMaster lk,LockerTransaction lt,Lockers l,CustomerMaster cm where lk.cid=cm.cid and lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lt.ac_type=lk.ac_type and lt.ac_no=lk.ac_no and lt.ve_user is null and lk.locker_no=l.locker_no order by lt.trn_seq desc limit 1");
                }
                if(int_type==2) 
                {	System.out.println("line num 858");
                	rs=stmt.executeQuery("select lk.*,l.*,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from LockerMaster lk,LockerTransaction lt,Lockers l,CustomerMaster cm where lk.cid=cm.cid and lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lt.ac_type=lk.ac_type and lt.ac_no=lk.ac_no and rent_by is null and trf_acty is null and trf_acno is null and rent_amt is null and rent_upto is null and lt.ve_user is null and lt.ve_tml is null and lt.ve_date is null and lk.locker_no=l.locker_no and op_date='"+date+"'");
                }
                /////////////
                
                if(rs.next())
                {
                    System.out.println("inside timein ");
                   System.out.println("++=====>"+rs.getString("lt.ve_tml"));
                    //ship........06/04/2006
                    req_mths = rs.getInt("lk.req_mths");
                    req_days = rs.getInt("lk.required_days");
                    /////////
                    
                    lockerMasterObject.setTimeIn(rs.getString("lt.time_in"));
                    lockerMasterObject.setTimeOut(rs.getString("lt.time_out"));
                    
                    lockerMasterObject.uv.setUserId(rs.getString("lt.de_user"));
                    lockerMasterObject.uv.setUserTml(rs.getString("lt.de_tml"));
                    
                    lockerMasterObject.uv.setVerId(rs.getString("lt.ve_user"));
                    lockerMasterObject.uv.setVerTml(rs.getString("lt.ve_tml"));
                    
                    lockerMasterObject.setTrnType(rs.getString("lt.trn_type"));
                    
                    lockerMasterObject.setTrnDate(rs.getString("lt.op_date"));
                    
                    rs.beforeFirst();
                }
                else
                {
                    System.out.println("inside else timein ");
                    
                    lockerMasterObject.setTimeIn(null);
                    lockerMasterObject.setTimeOut(null);
                    
                    lockerMasterObject.uv.setVerId(null);
                    lockerMasterObject.uv.setVerTml(null);
                    
                    //if(!rs.next())
                    
                    rs=stmt.executeQuery("select lk.*,l.key_no,l.descptn,l.locker_st,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from LockerMaster lk,Lockers l,CustomerMaster cm where lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lk.locker_no=l.locker_no");// and l.locker_st='T'  ");
                }
            }
            ////////////
            //ship......31/03/2006.....for Locker surrender
            else if(int_type==4)
            {
                 rs = stmt.executeQuery("select lk.*,l.key_no,l.descptn,l.locker_st,lt.rent_amt,lt.rent_upto,lt.trn_type,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from LockerMaster lk,LockerTransaction lt,Lockers l,CustomerMaster cm where lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lk.locker_no=l.locker_no and lt.ac_type='"+string_locker_type+"' and lt.ac_no="+int_locker_no+" and lk.locker_no=lt.locker_no and lt.time_in is null and lt.time_out is null order by lt.trn_seq desc limit 1");
                 
                 if(rs.last())
                 {
                     double total_rent = 0.0;
                     rent_upto = rs.getString("lt.rent_upto");
                     lockerMasterObject.setRentUpto(rent_upto);
                     
                     //ship........06/04/2006
                     req_mths = rs.getInt("lk.req_mths");
                     req_days = rs.getInt("lk.required_days");
                     /////////
                     
                     lockerMasterObject.setTrnType(rs.getString("lt.trn_type"));
                     
                     rs_lktrn = st_lktrn.executeQuery("select rent_amt from LockerTransaction where ac_type='"+string_locker_type+"' and ac_no="+int_locker_no+" and rent_amt is not null");
                     while(rs_lktrn.next())
                     {
                         total_rent = total_rent+rs_lktrn.getDouble("rent_amt");
                     }
                     System.out.println("total rent = "+total_rent);
                     lockerMasterObject.setRentColl(total_rent);
                 }
                 else 
                     lockerMasterObject=null;
                 
                 rs.beforeFirst();
            }
            
            //ship.....05/04/2006......for locker rent(only thro' transfer)
            else if(int_type==5)
            {
                boolean result = false;
                int lk_mst_req_mths = 0,lk_mst_req_days = 0;
                
                rs=stmt.executeQuery("select lk.*,l.key_no,l.descptn,l.locker_st,lt.trn_seq,lt.rent_by,lt.trf_acty,lt.trf_acno,lt.rent_amt,lt.rent_upto,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from LockerMaster lk,LockerTransaction lt,Lockers l,CustomerMaster cm where lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lk.locker_no=l.locker_no and lt.ac_type='"+string_locker_type+"' and lt.ac_no="+int_locker_no+" and lk.locker_no=lt.locker_no and lt.time_in is null and lt.time_out is null and lt.trn_seq is not null and lt.rent_by='T' and lt.trf_acty is not null and lt.trf_acno is not null order by trn_seq desc limit 1");
                
                if(rs.next())
                {
                    System.out.println("inside int_type==5....");
                    //ship.....06/04/2006
                    lk_mst_req_mths = rs.getInt("lk.req_mths");
                    lk_mst_req_days = rs.getInt("lk.required_days");
                    
                    System.out.println("lk_mst_req_mths = "+lk_mst_req_mths);
                    System.out.println("lk_mst_req_days = "+lk_mst_req_days);
                    ///////////
                    
                    lockerMasterObject.setRentBy("T");
                    
                    rent_upto = rs.getString("lt.rent_upto");
                    
                    lockerMasterObject.setTrnType("R");
                    lockerMasterObject.setTransAcType(rs.getString("lt.trf_acty"));
                    lockerMasterObject.setTransAcNo(rs.getInt("lt.trf_acno"));                    
                    lockerMasterObject.setRentUpto(rent_upto);
                    lockerMasterObject.setRentColl(rs.getDouble("lt.rent_amt"));
                    
                    result = true;
                    
                    rs.beforeFirst();
                }
                else 
                    lockerMasterObject=null;
                
                if(result==true)
                {
                    //ship......06/04/2006
                    ResultSet rs_lkmst_log = stmt_lk.executeQuery("select * from LockerMasterLog where ac_type='"+string_locker_type+"' and ac_no="+int_locker_no+"");
                    
                    if(rs_lkmst_log.last())
                    {
                        System.out.println("inside if rs_lkmst_log.last()");
                        
                        int lk_mst_log_req_mths = rs_lkmst_log.getInt("req_mths");
                        int lk_mst_log_req_days = rs_lkmst_log.getInt("required_days");
                        
                        System.out.println("lk_mst_log_req_mths = "+lk_mst_log_req_mths);
                        System.out.println("lk_mst_log_req_days = "+lk_mst_log_req_days);
                        
                        req_mths = lk_mst_req_mths - lk_mst_log_req_mths;
                        req_days = lk_mst_req_days - lk_mst_log_req_days;
                        
                        System.out.println("req_mths = "+req_mths);
                        System.out.println("req_days = "+req_days);
                    }
                    else
                    {
                        System.out.println("inside else rs_lkmst_log.last()");
                        
                        req_mths = lk_mst_req_mths;
                        req_days = lk_mst_req_days;
                        
                        System.out.println("req_mths = "+req_mths);
                        System.out.println("req_days = "+req_days);
                    }
                }
            }
            /////////////
            //ship.....03/07/2007.....locker passbook
            else if(int_type==7)
            {
                rs = stmt.executeQuery("select lk.*,l.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from LockerMaster lk,LockerTransaction lt,Lockers l,CustomerMaster cm where lk.cid=cm.cid and lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lk.locker_no=l.locker_no and lt.ac_type='"+string_locker_type+"' and lt.ac_no="+int_locker_no+" and lk.locker_no=lt.locker_no");
            }
            /////////
            
            if(rs.next())
            {
                System.out.println("length in server inside rs.next 2 : "+rs.getRow());
                
                mat_date = rs.getString("lk.mat_date");
                
                lockerMasterObject.setLockerAcType(rs.getString("lk.ac_type"));
                lockerMasterObject.setLockerAcNo(rs.getInt("lk.ac_no"));        
                lockerMasterObject.setAccCategory(rs.getInt("lk.ac_type"));
                
                lockerMasterObject.setCid(rs.getInt("lk.cid"));
                lockerMasterObject.setMailingAddress(rs.getInt("lk.addr_type"));
                
                lockerMasterObject.setName(rs.getString("name"));//ship......05/06/2006
                
                lockerMasterObject.setLockerType(rs.getString("lk.locker_ty"));
                lockerMasterObject.setLockerNo(rs.getInt("lk.locker_no"));
                lockerMasterObject.setKeyNo(rs.getInt("l.key_no"));
                System.out.println("rs.getString(lk.locker_pw)......."+rs.getString("lk.locker_pw"));
                lockerMasterObject.setLockerPW(rs.getString("lk.locker_pw"));
                lockerMasterObject.setNoOfJntHldrs(rs.getInt("lk.no_jt_hldr"));
                
                lockerMasterObject.setAllotDate(rs.getString("lk.allot_dt"));
                
                lockerMasterObject.setMatDate(mat_date);
                
                //ship....06/04/2006
                lockerMasterObject.setReqdMonths(req_mths);
                lockerMasterObject.setRequiredDays(req_days);
                ////////////
                lockerMasterObject.setOprInstrn(rs.getString("lk.op_inst"));
                lockerMasterObject.setCloseDate(rs.getString("lk.close_dt"));
                
                System.out.print("ac no in server "+rs.getInt("lk.ac_no"));
                System.out.print("close_dt in server"+rs.getString("lk.close_dt"));
                System.out.println("locker password "+lockerMasterObject.getLockerPW());
                
                lockerMasterObject.setShareCode(rs.getString("lk.sh_type"));
                lockerMasterObject.setMemberNo(rs.getInt("lk.sh_no"));
                                
                lockerMasterObject.setAutoExtn(rs.getString("lk.auto_extn"));
                
                lockerMasterObject.setIntrAcTy(rs.getString("lk.intr_ac_type"));
                lockerMasterObject.setIntrAcNo(rs.getInt("lk.intr_ac_no"));
                
                lockerMasterObject.setNomRegNo(rs.getInt("lk.nom_no"));
                
                lockerMasterObject.uv.setVerId(rs.getString("lk.ve_user"));
                lockerMasterObject.uv.setVerDate(rs.getString("lk.ve_tml"));                
                lockerMasterObject.uv.setVerTml(rs.getString("lk.ve_date"));
                
                lockerMasterObject.uv.setUserId(rs.getString("lk.de_user"));
                lockerMasterObject.uv.setUserTml(rs.getString("lk.de_tml"));                
                lockerMasterObject.uv.setUserDate(rs.getString("lk.de_date"));
                
                int no_securities = rs.getInt("lk.no_securities");
                
                //ship.....26/07/2006
                if(rs.getString("lk.freeze_ind").equals("T"))
                {
                    lockerMasterObject.setFreezeInd("T");
                    
                    rs_fa = stmt.executeQuery("select * from FreezedAccounts where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and to_date is null");
                    
                    if(rs_fa.next())
                        lockerMasterObject.setReason(rs_fa.getString("reason"));
                }
                else if(rs.getString("lk.freeze_ind").equals("F"))
                {
                    lockerMasterObject.setFreezeInd("F");
                    lockerMasterObject.setReason("");
                }
                /////////////
                
                //ship.......22/02/2006
                System.out.println("no_securities = "+no_securities);
                lockerMasterObject.setNoOfSecurities(no_securities);
                //ship......04/03/2006
                if(no_securities > 0)
                {
                    Vector deposits = null;
                    deposits = new Vector(0,1);
                    rs_od = st_od.executeQuery("select * from ODInterestDetails where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and from_date is not null and to_date='"+rent_upto+"'");
                    
                    while(rs_od.next())
                    {
                        deposits.add(rs_od.getString("sec_ac_type")+" "+rs_od.getInt("sec_ac_no"));
                        System.out.println("!!!!!!!!!!!!!----ship----!!!!!!!!!!");
                    }
                    
                    lockerMasterObject.setDeposits(deposits);
                }
                else
                    lockerMasterObject.setDeposits(null);
                /////////
                                
                //ship.....31/03/2006
                /*lockerMasterObject.setClosingDETml(rs.getString("lk.cl_de_tml"));
                lockerMasterObject.setClosingDEUser(rs.getString("lk.cl_de_user"));                
                lockerMasterObject.setClosingDEDtTime(rs.getString("lk.cl_de_date"));
                lockerMasterObject.setClosingVETml(rs.getString("lk.cl_ve_tml"));
                lockerMasterObject.setClosingVEUser(rs.getString("lk.cl_ve_user"));
                lockerMasterObject.setClosingVEDtTime(rs.getString("lk.cl_ve_date"));*/
                
                rs = stmt.executeQuery("select cid,addr_type from JointHolders where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no='"+lockerMasterObject.getLockerAcNo()+"'");
                int j=0;
                int addrtype[]=null;
                int cids[]=null;
                
                if(rs.last())
                {
                    addrtype=new int[rs.getRow()];
                    cids=new int[rs.getRow()];
                    rs.beforeFirst();
                }
                
                while(rs.next())
                {
                    addrtype[j]=rs.getInt(2);
                    cids[j++]=rs.getInt(1);
                }
                
                lockerMasterObject.setAddr(addrtype);
                lockerMasterObject.setJointCid(cids);
                
                lockerMasterObject.setNomineeDetails(commonremote_local.getNominee(lockerMasterObject.getNomRegNo()));
                lockerMasterObject.setSigObj(commonremote_local.getSignatureDetails(lockerMasterObject.getLockerAcNo(),lockerMasterObject.getLockerAcType()));
            }
            else 
                lockerMasterObject=null;  //else lockerMasterObject.setLockerAcNo(-1);            
        }
        catch(SQLException sqlexception)
        {
            sqlexception.printStackTrace();
            obj_sessionContext.setRollbackOnly();
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        System.out.println("**************getLockerMaster End*************");
        
        if(lockerMasterObject==null)
            System.out.print(" in server  :  lockerMasterObject is null");
        else
            System.out.print("in server  :   lockerMasterObject is not null");
                
        return lockerMasterObject;            
    }
     
     //........................new END
/*	public LockerMasterObject getLockerMaster(String string_locker_type,int int_locker_no,int int_type) 
	{
		System.out.println("**************getLockerMaster Begin*************");
		
        System.out.println("string_locker_type is "+string_locker_type);
		System.out.println("int_locker_no"+int_locker_no);	
		System.out.println("int_type"+int_type);
			
		Statement stmt=null,stat=null;
		Connection conn=null;
		ResultSet rs=null;
		ResultSet rs1=null;
		LockerMasterObject lockerMasterObject=new LockerMasterObject();
		
		try
		{
			conn=getConnection();
			
			if(conn==null)
				throw new SQLException();
		
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stat=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			//rs=stmt.executeQuery("select * from LockerMaster where ac_type='"+locktype+"' and ac_no="+lockno+"");
			
            if(int_type==0)
			{
                rs=stmt.executeQuery("select lk.*,l.key_no,l.descptn,l.locker_st from LockerMaster lk,Lockers l where lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lk.locker_no=l.locker_no");
                rs.last();
                System.out.println("length in server at 375 :"+rs.getRow());
                
                if(rs.getRow()==0)
                {
                    lockerMasterObject=null;
                    return lockerMasterObject;
                }
                
                System.out.println(""+rs.getInt("lk.ac_no"));
                System.out.println(""+rs.getString("lk.ac_type"));
                System.out.println(""+rs.getString("lk.locker_ty"));
                System.out.println(""+rs.getInt("lk.locker_no"));
                
                rs.beforeFirst();
            }
			else if(int_type==1 || int_type==2)
			{
				if(int_type==1)	
					rs=stmt.executeQuery("select lk.*,l.key_no,l.descptn,l.locker_st,lt.time_in,lt.time_out,lt.ve_user from LockerMaster lk,LockerTransaction lt,Lockers l where lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lt.ac_type=lk.ac_type and lt.ac_no=lk.ac_no and (lt.time_out is null or lt.ve_user is null)  and lk.locker_no=l.locker_no");
				else if(int_type==2)	
					rs=stmt.executeQuery("select lk.*,l.key_no,l.descptn,l.locker_st,lt.time_in,lt.time_out,lt.ve_user from LockerMaster lk,LockerTransaction lt,Lockers l where lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lt.ac_type=lk.ac_type and lt.ac_no=lk.ac_no and lt.ve_user is null and lk.locker_no=l.locker_no");
			
                if(rs.next())
				{
					lockerMasterObject.setTimeIn(rs.getString("time_in"));
					lockerMasterObject.setTimeOut(rs.getString("time_out"));
					lockerMasterObject.setVerId(rs.getString("lt.ve_user"));
					rs.beforeFirst();
				}
				else
				{
			        System.out.println("....inside else....");
                    lockerMasterObject.setTimeIn(null);
					lockerMasterObject.setTimeOut(null);
					lockerMasterObject.setVerId("fdhgfh");//why hardcoded here
					
					if(!rs.next())
					{
						rs=stmt.executeQuery("select lk.*,l.key_no,l.descptn,l.locker_st from LockerMaster lk,Lockers l where lk.ac_type='"+string_locker_type+"' and lk.ac_no="+int_locker_no+" and lk.locker_no=l.locker_no");// and l.locker_st='T'  ");
					}
				}
			}
			
            if(rs.next())
			{
                System.out.println("length in server  : "+rs.getRow());
                lockerMasterObject=new LockerMasterObject();
                
            	lockerMasterObject.setLockerAcType(rs.getString("lk.ac_type"));
                
	   			lockerMasterObject.setLockerAcNo(rs.getInt("lk.ac_no"));		
				lockerMasterObject.setAccCategory(rs.getInt("lk.ac_type"));
				lockerMasterObject.setCid(rs.getInt("lk.cid"));
				lockerMasterObject.setMailingAddress(rs.getInt("lk.addr_type"));
				lockerMasterObject.setLockerType(rs.getString("lk.locker_ty"));
				lockerMasterObject.setLockerNo(rs.getInt("lk.locker_no"));
				lockerMasterObject.setKeyNo(rs.getInt("l.key_no"));
				lockerMasterObject.setLockerPW(rs.getString("lk.locker_pw"));
				lockerMasterObject.setNoOfJntHldrs(rs.getInt("lk.no_jt_hldr"));
				lockerMasterObject.setAllotDate(rs.getString("lk.allot_dt"));
				lockerMasterObject.setMatDate(rs.getString("lk.mat_date"));
				lockerMasterObject.setReqdMonths(rs.getInt("lk.req_mths"));
				lockerMasterObject.setRequiredDays(rs.getInt("lk.required_days"));
				lockerMasterObject.setOprInstrn(rs.getString("lk.op_inst"));
				lockerMasterObject.setCloseDate(rs.getString("lk.close_dt"));
				lockerMasterObject.setShareCode(rs.getString("lk.sh_type"));
				lockerMasterObject.setMemberNo(rs.getInt("lk.sh_no"));
				lockerMasterObject.setRentBy(rs.getString("lk.rent_by"));
				
				if(lockerMasterObject.getRentBy().equals("T"))
				{
					lockerMasterObject.setTransAcType(rs.getString("lk.trf_acty"));
					lockerMasterObject.setTransAcNo(rs.getInt("lk.trf_acno"));
				}
				else
				{
					rs1=stat.executeQuery("select * from DayCash where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"");
					if(rs1.next())
					{
						lockerMasterObject.setTransAcType(null);
						lockerMasterObject.setTransAcNo(rs1.getInt("scroll_no"));
					}
				}
		        lockerMasterObject.setRentUpto(rs.getString("lk.rent_upto"));
				lockerMasterObject.setRentColl(rs.getDouble("lk.rent_coll"));
				lockerMasterObject.setAutoExtn(rs.getString("lk.auto_extn"));
				lockerMasterObject.setIntrAcTy(rs.getString("lk.intr_ac_type"));
				lockerMasterObject.setIntrAcNo(rs.getInt("lk.intr_ac_no"));
				lockerMasterObject.setNomRegNo(rs.getInt("lk.nom_no"));
				lockerMasterObject.uv.setVerId(rs.getString("lk.ve_user"));
				lockerMasterObject.uv.setVerDate(rs.getString("lk.ve_tml"));
				lockerMasterObject.uv.setVerTml(rs.getString("lk.ve_date"));
				lockerMasterObject.uv.setUserId(rs.getString("lk.de_user"));
				lockerMasterObject.uv.setUserTml(rs.getString("lk.de_tml"));
				lockerMasterObject.uv.setUserDate(rs.getString("lk.de_date"));
				lockerMasterObject.setClosingDETml(rs.getString("lk.cl_de_tml"));
				lockerMasterObject.setClosingDEUser(rs.getString("lk.cl_de_user"));
				lockerMasterObject.setClosingDEDtTime(rs.getString("lk.cl_de_date"));
				lockerMasterObject.setClosingVETml(rs.getString("lk.cl_ve_tml"));
				lockerMasterObject.setClosingVEUser(rs.getString("lk.cl_ve_user"));
				lockerMasterObject.setClosingVEDtTime(rs.getString("lk.cl_ve_date"));
				
				rs = stmt.executeQuery("select cid,addr_type from JointHolders where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no='"+lockerMasterObject.getLockerAcNo()+"'");
				
                int j=0;
				int addrtype[]=null;
				int cids[]=null;
				if(rs.next())
				{
					rs.last();
					addrtype=new int[rs.getRow()];
					cids=new int[rs.getRow()];
					rs.beforeFirst();
				}
				while(rs.next())
				{
					addrtype[j]=rs.getInt(2);
					cids[j++]=rs.getInt(1);
				}
				lockerMasterObject.setAddr(addrtype);
				lockerMasterObject.setJointCid(cids);
				lockerMasterObject.setNomineeDetails(commonremote_local.getNominee(lockerMasterObject.getNomRegNo()));
				lockerMasterObject.setSigObj(commonremote_local.getSignatureDetails(lockerMasterObject.getLockerAcNo(),lockerMasterObject.getLockerAcType()));
			}
			else 
                lockerMasterObject=null;  //else lockerMasterObject.setLockerAcNo(-1);
            
		}catch(SQLException sqlexception){sqlexception.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(SQLException e){e.printStackTrace();}
		}
	//	System.out.println("length is"+lockerMasterObject.length);
		System.out.println("************** End*************");
		
		if(lockerMasterObject==null)
			System.out.print(" in server  :  lockerMasterObject is null");
		else
			System.out.print("in server  :   lockerMasterObject is not null");
		
		
		return lockerMasterObject;
			
	}*/
	
    //ship........06/06/2006
	/*public RentTransObject[] getRentTransaction(int int_account_no,String string_account_type,String string_from_date,String string_to_date,int int_type,String string_query) 
    {
		ResultSet rs=null;
		RentTransObject rto[]=null;
		
		Connection conn=null;
		Statement stat=null;
		
		System.out.println("from  : "+string_from_date);
		System.out.println("to    : "+string_to_date);
		System.out.println("type  : "+int_type);
		System.out.println("query : "+string_query);
		
		try
		{	
			conn=getConnection();
			
			if(conn==null)
				throw new SQLException();
		
			stat=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			if(int_type==1)
				rs=stat.executeQuery("select rt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,lm.locker_no,lm.locker_ty,lt.descrptn,Modules.moduleabbr from RentTran rt,CustomerMaster cm,LockerMaster lm,LockerType lt,Modules where concat(right(rt.trn_date,4),'-',mid(rt.trn_date,locate('/',rt.trn_date)+1,(locate('/',rt.trn_date,4)-locate('/',rt.trn_date)-1)),'-',left(rt.trn_date,locate('/',rt.trn_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lm.ac_no=rt.ac_no and lm.ac_type=rt.ac_type and cm.cid=lm.cid and lt.locker_type=lm.locker_ty and Modules.modulecode=lm.ac_type");
			else if(int_type==3)
				rs=stat.executeQuery("select rt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,lm.locker_no,lm.locker_ty,lt.descrptn,Modules.moduleabbr from RentTran rt,CustomerMaster cm,LockerMaster lm,LockerType lt,Modules where concat(right(rt.trn_date,4),'-',mid(rt.trn_date,locate('/',rt.trn_date)+1,(locate('/',rt.trn_date,4)-locate('/',rt.trn_date)-1)),'-',left(rt.trn_date,locate('/',rt.trn_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lm.ac_no=rt.ac_no and lm.ac_type=rt.ac_type and cm.cid=lm.cid and lt.locker_type=lm.locker_ty and Modules.modulecode=lm.ac_type and ("+string_query+") ");
			else
				rs=stat.executeQuery("select rt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,ca.address,ca.city,ca.state,ca.pin,lm.locker_no,lm.locker_ty,lt.descrptn,Modules.moduleabbr from RentTran rt,CustomerMaster cm,CustomerAddr ca,LockerMaster lm,LockerType lt,Modules where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lm.ac_no="+int_account_no+" and lm.ac_type='"+string_account_type+"' and lm.ac_no=rt.ac_no and lm.ac_type=rt.ac_type and cm.cid=lm.cid and ca.cid=lm.cid and ca.addr_type=lm.addr_type and lt.locker_type=lm.locker_ty and Modules.modulecode=lm.ac_type order by concat(right(rt.trn_date,4),'-',mid(rt.trn_date,locate('/',rt.trn_date)+1,(locate('/',rt.trn_date,4)-locate('/',rt.trn_date)-1)),'-',left(rt.trn_date,locate('/',rt.trn_date)-1))");
			
			if(rs.next())
			{	
				rs.last();
				rto=new RentTransObject[rs.getRow()];
				rs.beforeFirst();
				System.out.println("length"+rto.length);
			}
			int i=0;
			while(rs.next())
			{
				rto[i]=new RentTransObject();
				rto[i].setLockerAcType(rs.getString("Modules.moduleabbr"));
				if(int_type==2)
				{
					String addr="";
					addr+=rs.getString("ca.address")+"\n";
					addr+=rs.getString("ca.city")+"\n";
					addr+=rs.getString("ca.pin")+"\n";
					addr+=rs.getString("ca.state")+"\n";
					rto[i].setAddress(addr);
				}
				rto[i].setName(rs.getString("name"));
				rto[i].setTrnDate(rs.getString("rt.trn_date"));
				rto[i].setLockerType(rs.getString("lt.descrptn"));
				rto[i].setLockerNo(rs.getInt("lm.locker_no"));
				rto[i].setLockerAcNo(rs.getInt("rt.ac_no"));
				rto[i].setTrnSource(rs.getString("rt.trn_source"));
				rto[i].setTrnMode(rs.getString("rt.trn_mode"));
				rto[i].setTrfAcType(rs.getString("trf_ac_ty"));
				rto[i].setTrfAcNo(rs.getInt("trf_ac_no"));
				rto[i].setTrfVoucherNo(rs.getInt("ref_tv_no"));
				rto[i].setRentAmt(rs.getDouble("rt.rent_amt"));
				rto[i].uv.setUserId(rs.getString("de_user"));
				rto[i].uv.setUserTml(rs.getString("de_tml"));
				i++;
			}
			
			return rto;
			
		}catch(SQLException sqlexception){sqlexception.printStackTrace();}	
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		return rto;
	}*/
    ///////////
	
    /**
     * Gives the Locker Rent to be collected from the customer depending on the no of days
     * and locker type.
     */
	public double getRent(String string_locker_type,int int_days,int int_category,String date) 
	{   System.out.println("All rise");
	    System.out.println("lk_bean...inside get renTypee"+string_locker_type+"Daysss"+int_days+"Categoryyy"+int_category+"Dateee"+date);
		ResultSet rs;
		double rate=0;
		
		Connection conn=null;
		Statement stmt=null;
		
		try
		{
			conn=getConnection();
			
			if(conn==null)
				throw new SQLException();
			
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("select locker_rate  from LockerRate where locker_type='"+string_locker_type+"' and '"+Validations.convertYMD(date)+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)) and "+int_days+" between days_fr and days_to");
			if(rs.next())
			{
				rate=rate+rs.getDouble(1);
				System.out.println("lk_bean...locker rate="+rate);
			}
			
			rs=stmt.executeQuery("select extra_locker_rate  from LockerCatRate where locker_type='"+string_locker_type+"' and '"+Validations.convertYMD(date)+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)) and "+int_days+" between days_fr and days_to and category="+int_category+"");	
			if(rs.next())
			{
				rate=rate+rs.getDouble(1);
				System.out.println("lk_bean...Xtra locker rate="+rate);
			}
			
			rs.close();
			//ship....18/01/2006.....commented
			//return rate;
		}
		catch(SQLException sqlexception)
		{
		    sqlexception.printStackTrace();
		    obj_sessionContext.setRollbackOnly();
		}	
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
			    e.printStackTrace();
			}
		}
System.out.println("oh Gosh 'm i returning rent now!!!!!!!");
System.out.println(rate);		
		return rate;
	}
	
	//ship.........01/11/2006
	/*public String getShareHolderName(String string_account_type,int int_share_no) 
    {
		ResultSet rs=null;
		String str=null;
		
		Connection conn=null;
		Statement stmt=null;
		
		try
		{
			conn=getConnection();
		
			if(conn==null)
				throw new SQLException();
		
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("select cm.fname,cm.mname,cm.lname from ShareMaster sh,CustomerMaster cm where sh.cid=cm.cid and ac_no="+int_share_no+" and ac_type='"+string_account_type+"'");
			//I have given pay_ac_no instead of sh_no.
			while(rs.next())
			{
				str=rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3);
				return str;			}
			
		}
		catch(SQLException sqlexception)
		{
		    sqlexception.printStackTrace();
		    obj_sessionContext.setRollbackOnly();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
			    e.printStackTrace();
			}
		}
		
		return str;
	}*/
	
	//ship......01/11/2006
	/*public HashMap getLockerTrans1(String string_account_no,String string_account_type,String string_from_date,String string_to_date,int int_type)
	{
	    System.out.println("acc no == "+string_account_no);
	    System.out.println("acc type == "+string_account_type);
	    System.out.println("from date == "+string_from_date);
	    System.out.println("to date == "+string_to_date);
	    System.out.println("type == "+int_type);
	    
		LockerTransObject lto=null;
		Vector trn=new Vector();
		ResultSet rs1=null;
		HashMap lntrn=new HashMap();
		
		Connection conn=null;
		Statement stmt=null;
		
		try
		{
			conn=getConnection();	
		
			if(conn==null)
				throw new SQLException();
		
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs1=stmt.executeQuery("select lm.ac_no,lm.ac_type,ltr.ac_no,ltr.ac_type,cm.fname,cm.mname,cm.lname,Lockers.key_no,lt.descrptn,Modules.moduleabbr from LockerMaster lm left join LockerTransaction ltr on(lm.ac_type=ltr.ac_type and lm.ac_no=ltr.ac_no and concat(mid(op_date,(locate('/',op_date)+locate('/',op_date)+1),4),   '-',mid(op_date,locate('/',op_date)+1,(locate('/',op_date,4)-   locate('/',op_date)-1)),'-',left(op_date,locate('/',op_date)-1)) between '2004-12-12' and '2005-01-08'),CustomerMaster cm,Lockers,LockerType lt,Modules where  (lm.close_dt is null or concat(right(close_dt,4),'-',mid(close_dt,locate('/',close_dt)+1,(locate('/',close_dt,4)-locate('/',close_dt)-1)),'-',left(close_dt,locate('/',close_dt)-1)) between '2004-12-12' and '2005-01-08') and lm.cid=cm.cid and lm.locker_no=Lockers.locker_no   and lm.locker_ty=Lockers.locker_ty and lt.locker_ty=lm.locker_ty    and Modules.modulecode=lm.ac_type  order by lm.ac_type,lm.ac_no ");						
			int acty=0;
			int acno=0;
			while(rs1.next())
			{
				lto=new LockerTransObject();	
				lto.setLockerAcNo(rs1.getInt("ac_no"));
				
								lto.setLockerNo(rs1.getInt("locker_no"));
				 lto.setTrnSeq(rs1.getInt("trn_seq"));
				 lto.setLockerAcType(rs1.getString("Modules.moduleabbr"));
				 lto.setModuleCode(rs1.getString("ac_type"));
				 lto.setOperatedBy(rs1.getString("oper_by"));
				 lto.setOpDate(rs1.getString("op_date"));
				 lto.setTimeIn(rs1.getString("time_in"));
				 lto.setTimeOut(rs1.getString("time_out"));
				trn.add(lto);
				if(Integer.parseInt(rs1.getString("ac_type"))!=acty && rs1.getInt("ac_no")!=acno)
				{	
					lntrn.put(rs1.getInt("ac_no")+" "+rs1.getString("ac_type"),trn);
					trn=new Vector();
				}
				acty=Integer.parseInt(rs1.getString("ac_type"));
				acno=rs1.getInt("ac_no");
			}
		}
		catch(SQLException sqlexception)
		{
		    sqlexception.printStackTrace();
		    obj_sessionContext.setRollbackOnly();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
			    e.printStackTrace();
			}
		}
		
		return lntrn;
	}*/
	
	//ship.......06/06/2006
	 /*public LockerTransObject[] getLockerTransaction(String string_account_no,String string_account_type,String string_from_date,String string_to_date,int int_type,String string_query) 
	 {
		LockerTransObject lto[]=null;
		ResultSet rs1=null;
		Connection conn=null;
		Statement stmt=null;
		
		System.out.println("*****************inside Trans************************");
		
		System.out.println("ac_no     :"+string_account_no);
		System.out.println("from_date :"+string_from_date);
		System.out.println("to_date   :"+string_to_date);
		System.out.println("string_query     :"+string_query);
		
		System.out.println("*****************inside Trans************************");
		
		try
		{
			conn=getConnection();
			
			if(conn==null)
				throw new SQLException();
		
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			//rs1=stat.executeQuery("select lt.*,Modules.moduleabbr from LockerTransaction lt,Modules where ("+ac_no+") and concat(mid(op_date,(locate('/',op_date)+locate('/',op_date)+1),4),'-',mid(op_date,locate('/',op_date)+1,(locate('/',op_date,4)-locate('/',op_date)-1)),'-',left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and Modules.modulecode=lt.ac_type order by lt.ac_no ");			
			System.out.println("before ac_no"+string_account_no);
			
			if(int_type==1)
			{
				System.out.println("before ac_no in type 1 in if"+string_account_no);
				rs1=stmt.executeQuery("select lt.*,Modules.moduleabbr from LockerTransaction lt,Modules where ("+string_account_no+") and concat(mid(op_date,(locate('/',op_date)+locate('/',op_date)+1),4),'-',mid(op_date,locate('/',op_date)+1,(locate('/',op_date,4)-locate('/',op_date)-1)),'-',left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and Modules.modulecode=lt.ac_type order by lt.ac_no ");
				System.out.println("after ac_no in type 1 in if"+string_account_no);
			}
            else if(int_type==5) // locker_transaction is not null
            {
                System.out.print("inside int_type 5");
                System.out.println("before ac_no in type 1 in if"+string_account_no);
                rs1=stmt.executeQuery("select lt.*,Modules.moduleabbr from LockerTransaction lt,Modules where ("+string_account_no+") and concat(mid(op_date,(locate('/',op_date)+locate('/',op_date)+1),4),'-',mid(op_date,locate('/',op_date)+1,(locate('/',op_date,4)-locate('/',op_date)-1)),'-',left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and Modules.modulecode=lt.ac_type and ("+string_query+") order by lt.ac_no ");
                System.out.println("after ac_no in type 1 in if"+string_account_no);
            }
			else if(int_type==2)//LockerOperation Null
			{
				System.out.println("inside type 2 query is "+string_query);
				//ship......21/04/2006
				//rs1=stmt.executeQuery("select lm.locker_ty,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,lty.descrptn,Modules.moduleabbr from LockerMaster lm,LockerTransaction lt,CustomerMaster cm,LockerType lty,Modules where lm.ac_no=lt.ac_no and lm.cid=cm.cid and (concat(right(lm.close_dt,4),'-',mid(lm.close_dt,locate('/',lm.close_dt)+1,(locate('/',lm.close_dt,4)-locate('/',lm.close_dt)-1)),'-',left(close_dt,locate('/',lm.close_dt)-1))>='"+Validations.convertYMD(string_to_date)+"' or lm.close_dt is null) and concat(mid(op_date,7,4),'-',mid(op_date,locate('/',op_date)+1, (locate('/',op_date,4)-locate('/',op_date)-1)),'-', left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lty.locker_type=lm.locker_ty and Modules.modulecode=lm.ac_type order by lt.ac_type,lt.ac_no");
				//ship.....27/04/2006
				//rs1=stmt.executeQuery("select lm.locker_ty,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,lty.descrptn,Modules.moduleabbr from LockerMaster lm,LockerTransaction lt,CustomerMaster cm,LockerType lty,Modules where lm.ac_no=lt.ac_no and lm.cid=cm.cid and (concat(right(lm.close_dt,4),'-',mid(lm.close_dt,locate('/',lm.close_dt)+1,(locate('/',lm.close_dt,4)-locate('/',lm.close_dt)-1)),'-',left(close_dt,locate('/',lm.close_dt)-1))>='"+Validations.convertYMD(string_to_date)+"' or lm.close_dt is null) and concat(mid(op_date,7,4),'-',mid(op_date,locate('/',op_date)+1, (locate('/',op_date,4)-locate('/',op_date)-1)),'-', left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lty.locker_type=lm.locker_ty and Modules.modulecode=lm.ac_type and lt.rent_by is null order by lt.ac_type,lt.ac_no");
				rs1=stmt.executeQuery("select lm.locker_ty,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,lty.descrptn,Modules.moduleabbr from LockerMaster lm,LockerTransaction lt,CustomerMaster cm,LockerType lty,Modules where lm.ac_no=lt.ac_no and lm.cid=cm.cid and concat(mid(op_date,7,4),'-',mid(op_date,locate('/',op_date)+1, (locate('/',op_date,4)-locate('/',op_date)-1)),'-', left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lty.locker_type=lm.locker_ty and Modules.modulecode=lm.ac_type and lt.rent_by is null order by lt.ac_type,lt.ac_no");
				///////////
			}
			else if(int_type==3)//PassBook
            {
                System.out.println("1.....");
                //rs1=stmt.executeQuery("select lm.locker_ty,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,ca.address,ca.city,ca.pin,ca.state,lty.descrptn,Modules.moduleabbr from LockerMaster lm,LockerTransaction lt,CustomerMaster cm,CustomerAddr ca,LockerType lty,Modules where lm.ac_no="+Integer.parseInt(string_account_no)+" and lm.ac_type='"+string_account_type+"' and lt.ac_no="+Integer.parseInt(string_account_no)+" and lt.ac_type='"+string_account_type+"'and lm.cid=cm.cid and ca.cid=cm.cid and ca.addr_type=lm.addr_type and lm.close_dt is null and concat(mid(op_date,7,4),'-',mid(op_date,locate('/',op_date)+1, (locate('/',op_date,4)-locate('/',op_date)-1)),'-', left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lty.locker_type=lm.locker_ty and Modules.modulecode=lm.ac_type order by concat(mid(op_date,7,4),'-',mid(op_date,locate('/',op_date)+1, (locate('/',op_date,4)-locate('/',op_date)-1)),'-', left(op_date,locate('/',op_date)-1))");
                rs1=stmt.executeQuery("select lm.locker_ty,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,ca.address,ca.city,ca.pin,ca.state,lty.descrptn,Modules.moduleabbr from LockerMaster lm,LockerTransaction lt,CustomerMaster cm,CustomerAddr ca,LockerType lty,Modules where lm.ac_no="+Integer.parseInt(string_account_no)+" and lm.ac_type='"+string_account_type+"' and lt.ac_no="+Integer.parseInt(string_account_no)+" and lt.ac_type='"+string_account_type+"'and lm.cid=cm.cid and ca.cid=cm.cid and ca.addr_type=lm.addr_type and concat(mid(op_date,7,4),'-',mid(op_date,locate('/',op_date)+1, (locate('/',op_date,4)-locate('/',op_date)-1)),'-', left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lty.locker_type=lm.locker_ty and Modules.modulecode=lm.ac_type order by concat(mid(op_date,7,4),'-',mid(op_date,locate('/',op_date)+1, (locate('/',op_date,4)-locate('/',op_date)-1)),'-', left(op_date,locate('/',op_date)-1))");
                
                System.out.println("2.....");
            }
            else if(int_type==4)//LockerOperation Not Null
			{
				System.out.println("inside type 4 query is"+string_query);
				rs1=stmt.executeQuery("select lm.locker_ty,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,lty.descrptn,Modules.moduleabbr from LockerMaster lm,LockerTransaction lt,CustomerMaster cm,LockerType lty,Modules where lm.ac_no=lt.ac_no and lm.cid=cm.cid and (concat(right(lm.close_dt,4),'-',mid(lm.close_dt,locate('/',lm.close_dt)+1,(locate('/',lm.close_dt,4)-locate('/',lm.close_dt)-1)),'-',left(close_dt,locate('/',lm.close_dt)-1))>='"+Validations.convertYMD(string_to_date)+"' or lm.close_dt is null) and concat(mid(op_date,7,4),'-',mid(op_date,locate('/',op_date)+1, (locate('/',op_date,4)-locate('/',op_date)-1)),'-', left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lty.locker_type=lm.locker_ty and Modules.modulecode=lm.ac_type and ("+string_query+") order by lt.ac_type,lt.ac_no");						
			}
            
			if(rs1.next())
			{	
                System.out.println("3.....");
			
    			rs1.last();
    			System.out.println("length in transaction"+rs1.getRow());
    			lto=new LockerTransObject[rs1.getRow()];
    			rs1.beforeFirst();
    			System.out.println("4.....");
    			System.out.println("lengthg="+lto.length);
    		}
			int i=0;
			
			while(rs1.next())
			{
				lto[i]=new LockerTransObject();	
				lto[i].setLockerAcNo(rs1.getInt("ac_no"));
				
				if(int_type==2)
				{
					lto[i].setName(rs1.getString("name"));
					lto[i].setLockerType(rs1.getString("lty.descrptn"));
				}
				if(int_type==4)
				{
					lto[i].setName(rs1.getString("name"));
					lto[i].setLockerType(rs1.getString("lty.descrptn"));
				}
				if(int_type==3)
				{
					String addr="";
					addr+=rs1.getString("ca.address")+"\n";
					addr+=rs1.getString("ca.city")+"\n";
					addr+=rs1.getString("ca.pin")+"\n";
					addr+=rs1.getString("ca.state")+"\n";
					
					lto[i].setAddress(addr);
				}
				
				lto[i].setLockerNo(rs1.getInt("locker_no"));
				lto[i].setTrnSeq(rs1.getInt("trn_seq"));
				lto[i].setLockerAcType(rs1.getString("Modules.moduleabbr"));
				lto[i].setModuleCode(rs1.getString("ac_type"));
				lto[i].setOperatedBy(rs1.getString("oper_by"));
				lto[i].setOpDate(rs1.getString("op_date"));
				
				lto[i].setTimeIn(rs1.getString("time_in"));
				lto[i].setTimeOut(rs1.getString("time_out"));
				
				i++;
			}
		}
		catch(SQLException sqlexception)
		{
		    sqlexception.printStackTrace();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
			    e.printStackTrace();
			}
		}
		
		return lto;
	}*/
	
	/**
	 * Retrieves the details of the given locker a/c from LockerTransaction.
	 */
	public LockerTransObject[] getLockerTransaction(String string_account_no,String string_account_type,String string_from_date,String string_to_date,int int_type,String string_query) 
	{
		LockerTransObject lto[]=null;
		ResultSet rs1=null;
		Connection conn=null;
		Statement stmt=null;
		
		System.out.println("*****************inside Trans************************");
		
		System.out.println("ac_no     :"+string_account_no);
		System.out.println("from_date :"+string_from_date);
		System.out.println("to_date   :"+string_to_date);
		System.out.println("string_query     :"+string_query);
		
		System.out.println("*****************inside Trans************************");
		
		try
		{
			conn=getConnection();
			
			if(conn==null)
				throw new SQLException();
		
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			//rs1=stat.executeQuery("select lt.*,Modules.moduleabbr from LockerTransaction lt,Modules where ("+ac_no+") and concat(mid(op_date,(locate('/',op_date)+locate('/',op_date)+1),4),'-',mid(op_date,locate('/',op_date)+1,(locate('/',op_date,4)-locate('/',op_date)-1)),'-',left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and Modules.modulecode=lt.ac_type order by lt.ac_no ");			
			System.out.println("before ac_no"+string_account_no);
			
			if(int_type==1)
			{
				System.out.println("before ac_no in type 1 in if"+string_account_no);
				rs1=stmt.executeQuery("select lt.*,Modules.moduleabbr from LockerTransaction lt,Modules where ("+string_account_no+") and concat(mid(op_date,(locate('/',op_date)+locate('/',op_date)+1),4),'-',mid(op_date,locate('/',op_date)+1,(locate('/',op_date,4)-locate('/',op_date)-1)),'-',left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and Modules.modulecode=lt.ac_type order by lt.ac_no ");
				System.out.println("after ac_no in type 1 in if"+string_account_no);
			}
           else if(int_type==5) // locker_transaction is not null
           {
               System.out.print("inside int_type 5");
               System.out.println("before ac_no in type 1 in if"+string_account_no);
               rs1=stmt.executeQuery("select lt.*,Modules.moduleabbr from LockerTransaction lt,Modules where ("+string_account_no+") and concat(mid(op_date,(locate('/',op_date)+locate('/',op_date)+1),4),'-',mid(op_date,locate('/',op_date)+1,(locate('/',op_date,4)-locate('/',op_date)-1)),'-',left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and Modules.modulecode=lt.ac_type and ("+string_query+") order by lt.ac_no ");
               System.out.println("after ac_no in type 1 in if"+string_account_no);
           }
			else if(int_type==2)//LockerOperation Null
			{
				System.out.println("inside type 2 query is "+string_query);
				//ship......21/04/2006
				//rs1=stmt.executeQuery("select lm.locker_ty,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,lty.descrptn,Modules.moduleabbr from LockerMaster lm,LockerTransaction lt,CustomerMaster cm,LockerType lty,Modules where lm.ac_no=lt.ac_no and lm.cid=cm.cid and (concat(right(lm.close_dt,4),'-',mid(lm.close_dt,locate('/',lm.close_dt)+1,(locate('/',lm.close_dt,4)-locate('/',lm.close_dt)-1)),'-',left(close_dt,locate('/',lm.close_dt)-1))>='"+Validations.convertYMD(string_to_date)+"' or lm.close_dt is null) and concat(mid(op_date,7,4),'-',mid(op_date,locate('/',op_date)+1, (locate('/',op_date,4)-locate('/',op_date)-1)),'-', left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lty.locker_type=lm.locker_ty and Modules.modulecode=lm.ac_type order by lt.ac_type,lt.ac_no");
				//ship.....27/04/2006
				//rs1=stmt.executeQuery("select lm.locker_ty,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,lty.descrptn,Modules.moduleabbr from LockerMaster lm,LockerTransaction lt,CustomerMaster cm,LockerType lty,Modules where lm.ac_no=lt.ac_no and lm.cid=cm.cid and (concat(right(lm.close_dt,4),'-',mid(lm.close_dt,locate('/',lm.close_dt)+1,(locate('/',lm.close_dt,4)-locate('/',lm.close_dt)-1)),'-',left(close_dt,locate('/',lm.close_dt)-1))>='"+Validations.convertYMD(string_to_date)+"' or lm.close_dt is null) and concat(mid(op_date,7,4),'-',mid(op_date,locate('/',op_date)+1, (locate('/',op_date,4)-locate('/',op_date)-1)),'-', left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lty.locker_type=lm.locker_ty and Modules.modulecode=lm.ac_type and lt.rent_by is null order by lt.ac_type,lt.ac_no");
				rs1=stmt.executeQuery("select lm.locker_ty,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,lty.descrptn,Modules.moduleabbr from LockerMaster lm,LockerTransaction lt,CustomerMaster cm,LockerType lty,Modules where lm.ac_no=lt.ac_no and lm.cid=cm.cid and concat(mid(op_date,7,4),'-',mid(op_date,locate('/',op_date)+1, (locate('/',op_date,4)-locate('/',op_date)-1)),'-', left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lty.locker_type=lm.locker_ty and Modules.modulecode=lm.ac_type and lt.rent_by is null and lt.time_in is not null and lt.time_out is not null and lt.ve_user is not null and lt.ve_tml is not null and lt.ve_date is not null order by lt.ac_type,lt.ac_no");
				///////////
			}
			else if(int_type==3)//PassBook
           {
               System.out.println("1.....");
               //rs1=stmt.executeQuery("select lm.locker_ty,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,ca.address,ca.city,ca.pin,ca.state,lty.descrptn,Modules.moduleabbr from LockerMaster lm,LockerTransaction lt,CustomerMaster cm,CustomerAddr ca,LockerType lty,Modules where lm.ac_no="+Integer.parseInt(string_account_no)+" and lm.ac_type='"+string_account_type+"' and lt.ac_no="+Integer.parseInt(string_account_no)+" and lt.ac_type='"+string_account_type+"'and lm.cid=cm.cid and ca.cid=cm.cid and ca.addr_type=lm.addr_type and lm.close_dt is null and concat(mid(op_date,7,4),'-',mid(op_date,locate('/',op_date)+1, (locate('/',op_date,4)-locate('/',op_date)-1)),'-', left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lty.locker_type=lm.locker_ty and Modules.modulecode=lm.ac_type order by concat(mid(op_date,7,4),'-',mid(op_date,locate('/',op_date)+1, (locate('/',op_date,4)-locate('/',op_date)-1)),'-', left(op_date,locate('/',op_date)-1))");
               rs1=stmt.executeQuery("select lm.locker_ty,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,ca.address,ca.city,ca.pin,ca.state,lty.descrptn,Modules.moduleabbr from LockerMaster lm,LockerTransaction lt,CustomerMaster cm,CustomerAddr ca,LockerType lty,Modules where lm.ac_no="+Integer.parseInt(string_account_no)+" and lm.ac_type='"+string_account_type+"' and lt.ac_no="+Integer.parseInt(string_account_no)+" and lt.ac_type='"+string_account_type+"'and lm.cid=cm.cid and ca.cid=cm.cid and ca.addr_type=lm.addr_type and concat(mid(op_date,7,4),'-',mid(op_date,locate('/',op_date)+1, (locate('/',op_date,4)-locate('/',op_date)-1)),'-', left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lty.locker_type=lm.locker_ty and Modules.modulecode=lm.ac_type order by concat(mid(op_date,7,4),'-',mid(op_date,locate('/',op_date)+1, (locate('/',op_date,4)-locate('/',op_date)-1)),'-', left(op_date,locate('/',op_date)-1))");
               System.out.println("2.....");
           }
           else if(int_type==4)//LockerOperation Not Null
			{
				System.out.println("inside type 4 query is"+string_query);
				rs1=stmt.executeQuery("select lm.locker_ty,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,lty.descrptn,Modules.moduleabbr from LockerMaster lm,LockerTransaction lt,CustomerMaster cm,LockerType lty,Modules where lm.ac_no=lt.ac_no and lm.cid=cm.cid and (concat(right(lm.close_dt,4),'-',mid(lm.close_dt,locate('/',lm.close_dt)+1,(locate('/',lm.close_dt,4)-locate('/',lm.close_dt)-1)),'-',left(close_dt,locate('/',lm.close_dt)-1))>='"+Validations.convertYMD(string_to_date)+"' or lm.close_dt is null) and concat(mid(op_date,7,4),'-',mid(op_date,locate('/',op_date)+1, (locate('/',op_date,4)-locate('/',op_date)-1)),'-', left(op_date,locate('/',op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lty.locker_type=lm.locker_ty and Modules.modulecode=lm.ac_type and ("+string_query+") and lt.ve_user is not null and lt.ve_tml is not null and lt.ve_date is not null order by lt.ac_type,lt.ac_no");						
			}
           
			if(rs1.next())
			{	
               System.out.println("3.....");
               rs1.last();
   				System.out.println("length in transaction"+rs1.getRow());
   				lto=new LockerTransObject[rs1.getRow()];
   				rs1.beforeFirst();
   				System.out.println("4.....");
   				System.out.println("lengthg="+lto.length);
			}
			
			int i=0;
			
			while(rs1.next())
			{
				lto[i]=new LockerTransObject();	
				lto[i].setLockerAcNo(rs1.getInt("ac_no"));
				
				if(int_type==2)
				{
					lto[i].setName(rs1.getString("name"));
					lto[i].setLockerType(rs1.getString("lty.descrptn"));
				}
				if(int_type==4)                  
				{
					lto[i].setName(rs1.getString("name"));
					lto[i].setLockerType(rs1.getString("lty.descrptn"));
				}
				if(int_type==3)
				{
					String addr="";
					addr+=rs1.getString("ca.address")+"\n";
					addr+=rs1.getString("ca.city")+"\n";
					addr+=rs1.getString("ca.pin")+"\n";
					addr+=rs1.getString("ca.state")+"\n";
					
					lto[i].setAddress(addr);
				}
				
				lto[i].setLockerNo(rs1.getInt("locker_no"));
				lto[i].setTrnSeq(rs1.getInt("trn_seq"));
				lto[i].setLockerAcType(rs1.getString("Modules.moduleabbr"));
				lto[i].setModuleCode(rs1.getString("ac_type"));
				lto[i].setOperatedBy(rs1.getString("oper_by"));
				lto[i].setOpDate(rs1.getString("op_date"));
				
				lto[i].setTimeIn(rs1.getString("time_in"));
				lto[i].setTimeOut(rs1.getString("time_out"));
				
				//ship.....11/07/2006
				if(int_type==3)//Passbook
				{
					lto[i].setRentBy(rs1.getString("rent_by"));
					lto[i].setTransAcType(rs1.getString("trf_acty"));
					lto[i].setTransAcNo(rs1.getInt("trf_acno"));
					lto[i].setRentUpto(rs1.getString("rent_upto"));
					lto[i].setRentColl(rs1.getDouble("rent_amt"));
				}
				///////////////
				
				i++;
			}
		}
		catch(SQLException sqlexception)
		{
		    sqlexception.printStackTrace();
		    obj_sessionContext.setRollbackOnly();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
			    e.printStackTrace();
			}
		}
		
		return lto;
	}
	//////////////////////

	/**
	 * Locker Reports.
	 */
     public LockerMasterObject[] getLockerReport(String string_from_date,String string_to_date,int int_type,String string_query) 
     {
            LockerMasterObject lmo[]=null;
            ResultSet rs=null;
            Connection conn=null;
            Statement stmt=null;
            
            System.out.println("from_date = "+string_from_date);
            System.out.println("to_date = "+string_to_date);
            System.out.println("type = "+int_type);
            System.out.println("query = "+string_query);
            
            try
            {
                conn=getConnection();
                
                stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
                if(conn==null)
                    throw new SQLException();
                
                if(int_type==1)//Locker Owner Report
                {
                    int result = 0;
                    stmt.executeUpdate("drop table if exists temp");
                    result = stmt.executeUpdate("create temporary table temp as select ac_type,ac_no,locker_no,rent_by,trf_acty,trf_acno,rent_amt,rent_upto,max(trn_seq) trn_seq,trn_type,cd_ind,oper_by,op_date,time_in,time_out from LockerTransaction where rent_by is not null and ve_user is not null and ve_tml is not null and ve_date is not null group by ac_type,ac_no");
                    System.out.println("result = "+result);
                    
                    if(result > 0)
                    {
                        System.out.println("inside result > 0");
                        rs=stmt.executeQuery("select lm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,t.*,l.*,lty.*,m.* from temp t,LockerMaster lm,Lockers l,LockerType lty,CustomerMaster cm,Modules m where lm.ac_type=t.ac_type and lm.ac_no=t.ac_no and lm.cid=cm.cid and lm.locker_no=l.locker_no and lm.locker_ty=l.locker_ty and l.locker_ty=lty.locker_type and m.modulecode=lm.ac_type and (concat(right(allot_dt,4),'-',mid(allot_dt,locate('/',allot_dt)+1,(locate('/',allot_dt,4)-locate('/',allot_dt)-1)),'-',left(allot_dt,locate('/',allot_dt)-1)) <= '"+Validations.convertYMD(string_to_date)+"') and lm.ve_user is not null and lm.ve_tml is not null and lm.ve_date is not null order by lm.ac_type,lm.ac_no");
                    }
                }
                else if(int_type==7)//Locker Owner Report NotNull
                    rs=stmt.executeQuery("select lm.*,lt.op_date,lt.oper_by,lt.time_in,lt.time_out,lt.ac_type,lt.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,l.key_no,lty.descrptn,m.moduleabbr from LockerMaster lm,LockerTransaction lt,CustomerMaster cm,Lockers l,LockerType lty,Modules m where (concat(right(allot_dt,4),'-',mid(allot_dt,locate('/',allot_dt)+1,(locate('/',allot_dt,4)-locate('/',allot_dt)-1)),'-',left(allot_dt,locate('/',allot_dt)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"') and lm.cid=cm.cid and lm.locker_no=l.locker_no and lm.locker_ty=l.locker_ty and lty.locker_type=lm.locker_ty and m.modulecode=lm.ac_type and lt.ac_no=lm.ac_no and lt.ac_type=lm.ac_type and ("+string_query+") and lt.ve_user is not null and lt.ve_tml is not null and lt.ve_date is not null order by lm.ac_no");
                else if(int_type==2)//Locker Issued
                    rs=stmt.executeQuery("select lm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,l.key_no,lty.descrptn,m.moduleabbr from LockerMaster lm,CustomerMaster cm,Lockers l,LockerType lty,Modules m where lm.cid=cm.cid and lm.locker_no=l.locker_no and lm.locker_ty=l.locker_ty and concat(right(allot_dt,4),'-',mid(allot_dt,locate('/',allot_dt)+1,(locate('/',allot_dt,4)-locate('/',allot_dt)-1)),'-',left(allot_dt,locate('/',allot_dt)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lty.locker_type=lm.locker_ty and m.modulecode=lm.ac_type and lm.ve_user is not null and lm.ve_tml is not null and lm.ve_date is not null order by lm.ac_no");
                else if(int_type==8)//Locker Issued NotNull
                    rs=stmt.executeQuery("select lm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,l.key_no,lty.descrptn,m.moduleabbr from LockerMaster lm,CustomerMaster cm,Lockers l,LockerType lty,Modules m where lm.cid=cm.cid and lm.locker_no=l.locker_no and lm.locker_ty=l.locker_ty and concat(right(allot_dt,4),'-',mid(allot_dt,locate('/',allot_dt)+1,(locate('/',allot_dt,4)-locate('/',allot_dt)-1)),'-',left(allot_dt,locate('/',allot_dt)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lty.locker_type=lm.locker_ty and m.modulecode=lm.ac_type and ("+string_query+") and lm.ve_user is not null and lm.ve_tml is not null and lm.ve_date is not null order by lm.ac_no");
                else if(int_type==3)//Locker Surrended
                    rs=stmt.executeQuery("select lm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,l.key_no,lty.descrptn,m.moduleabbr from LockerMaster lm,CustomerMaster cm,Lockers l,LockerType lty,Modules m where lm.cid=cm.cid and lm.locker_no=l.locker_no and lm.locker_ty=l.locker_ty and concat(right(close_dt,4),'-',mid(close_dt,locate('/',close_dt)+1,(locate('/',close_dt,4)-locate('/',close_dt)-1)),'-',left(close_dt,locate('/',close_dt)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lty.locker_type=lm.locker_ty and m.modulecode=lm.ac_type and lm.ve_user is not null and lm.ve_tml is not null and lm.ve_date is not null order by lm.ac_no");
                else if(int_type==9)//Locker Surrended NotNull
                    rs=stmt.executeQuery("select lm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,l.key_no,lty.descrptn,m.moduleabbr from LockerMaster lm,CustomerMaster cm,Lockers l,LockerType lty,Modules m where lm.cid=cm.cid and lm.locker_no=l.locker_no and lm.locker_ty=l.locker_ty and concat(right(close_dt,4),'-',mid(close_dt,locate('/',close_dt)+1,(locate('/',close_dt,4)-locate('/',close_dt)-1)),'-',left(close_dt,locate('/',close_dt)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lty.locker_type=lm.locker_ty and m.modulecode=lm.ac_type and ("+string_query+") and lm.ve_user is not null and lm.ve_tml is not null and lm.ve_date is not null order by lm.ac_no");
                /*else if(int_type==4)//Locker Not Surrended
                    rs=stmt.executeQuery("select lm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,l.key_no,lty.descrptn,m.moduleabbr from LockerMaster lm,CustomerMaster cm,Lockers l,LockerType lty,Modules m where lm.ve_user is not null and lm.close_dt is not null and lm.cid=cm.cid and lm.locker_no=l.locker_no and lm.locker_ty=l.locker_ty and concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1))<='"+Validations.convertYMD(getSysDate())+"' and lty.locker_type=lm.locker_ty and m.modulecode=lm.ac_type order by lm.ac_no");     
                else if(int_type==10)//Locker Not Surrended Not NUll
                    rs=stmt.executeQuery("select lm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,l.key_no,lty.descrptn,m.moduleabbr from LockerMaster lm,CustomerMaster cm,Lockers l,LockerType lty,Modules m where lm.ve_user is not null and lm.close_dt is not null and lm.cid=cm.cid and lm.locker_no=l.locker_no and lm.locker_ty=l.locker_ty and concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1))<='"+Validations.convertYMD(getSysDate())+"' and lty.locker_type=lm.locker_ty and m.modulecode=lm.ac_type and ("+string_query+") order by lm.ac_no");*/
                /*else if(int_type==5)//Rent Due Report.....ship......22/04/2006
                {
                    int result = 0;
                    stmt.executeUpdate("drop table if exists temp");
                    result = stmt.executeUpdate("create temporary table temp as select ac_type,ac_no,locker_no,rent_by,trf_acty,trf_acno,rent_amt,rent_upto,max(trn_seq) trn_seq,trn_type,cd_ind,oper_by,op_date,time_in,time_out from LockerTransaction where rent_by is not null group by ac_type,ac_no");
                    System.out.println("result = "+result);
                    
                    if(result > 0)
                    {
                        System.out.println("inside result > 0");
                        rs=stmt.executeQuery("select lm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,t.*,l.*,lty.*,m.* from temp t,LockerMaster lm,Lockers l,LockerType lty,CustomerMaster cm,Modules m where lm.ac_type=t.ac_type and lm.ac_no=t.ac_no and lm.cid=cm.cid and lm.locker_no=l.locker_no and lm.locker_ty=l.locker_ty and l.locker_ty=lty.locker_type and m.modulecode=lm.ac_type and lm.ve_tml is not null and lm.ve_user is not null and lm.ve_date is not null and lm.close_dt is null and (concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1)) <= '"+Validations.convertYMD(string_to_date)+"') order by lm.ac_type,lm.ac_no");
                    }
                }
                else if(int_type==11)//Rent Due Report NotNull
                    rs=stmt.executeQuery("select lm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,l.key_no,lty.descrptn,m.moduleabbr from LockerMaster lm,CustomerMaster cm,Lockers l,LockerType lty,Modules m where lm.ve_user is not null and lm.close_dt is null and (concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1))<'"+Validations.convertYMD(string_to_date)+"') and lm.cid=cm.cid and lm.locker_no=l.locker_no and lm.locker_ty=l.locker_ty and concat(right(rent_upto,4),'-',mid(rent_upto,locate('/',rent_upto)+1,(locate('/',rent_upto,4)-locate('/',rent_upto)-1)),'-',left(rent_upto,locate('/',rent_upto)-1))<'"+Validations.convertYMD(getSysDate())+"' and lty.locker_type=lm.locker_ty and m.modulecode=lm.ac_type and ("+string_query+") order by lm.ac_no");*/     
                else if(int_type==6)//Reminders
                //ship.....06/05/2006
                //rs=stmt.executeQuery("select lm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,l.key_no,lty.descrptn,m.moduleabbr from LockerMaster lm,CustomerMaster cm,Lockers l,LockerType lty,Modules m where lm.ve_user is not null and lm.close_dt is null and concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1))<='"+Validations.convertYMD(getSysDate())+"' and lm.cid=cm.cid and lm.locker_no=l.locker_no and lm.locker_ty=l.locker_ty and lty.locker_type=lm.locker_ty and m.modulecode=lm.ac_type order by lm.ac_no");
                {
                    int result = 0;
                    stmt.executeUpdate("drop table if exists temp");
                    result = stmt.executeUpdate("create temporary table temp as select ac_type,ac_no,locker_no,rent_by,trf_acty,trf_acno,rent_amt,rent_upto,max(trn_seq) trn_seq,trn_type,cd_ind,oper_by,op_date,time_in,time_out from LockerTransaction where rent_by is not null and ve_user is not null and ve_tml is not null and ve_date is not null group by ac_type,ac_no");
                    System.out.println("result = "+result);
                    
                    if(result > 0)
                    {
                        System.out.println("inside result > 0");
                        rs=stmt.executeQuery("select lm.*,t.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,l.key_no,lty.descrptn,m.moduleabbr from LockerMaster lm,temp t,CustomerMaster cm,Lockers l,LockerType lty,Modules m where lm.ac_type=t.ac_type and lm.ac_no=t.ac_no and lm.ve_user is not null and lm.close_dt is null and (concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"') and lm.cid=cm.cid and lm.locker_no=l.locker_no and lm.locker_ty=l.locker_ty and lty.locker_type=lm.locker_ty and m.modulecode=lm.ac_type and lm.ve_user is not null and lm.ve_tml is not null and lm.ve_date is not null order by lm.ac_no");
                    }
                } 
                ///////////
                //else if(int_type==12)//Reminders NotNUll
                    //rs=stmt.executeQuery("select lm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,l.key_no,lty.descrptn,m.moduleabbr from LockerMaster lm,CustomerMaster cm,Lockers l,LockerType lty,Modules m where lm.ve_user is not null and lm.close_dt is null and concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1))<='"+Validations.convertYMD(getSysDate())+"' and lm.cid=cm.cid and lm.locker_no=l.locker_no and lm.locker_type=l.locker_ty and lt.locker_type=lm.locker_ty and m.modulecode=lm.ac_type and ("+string_query+") order by lm.ac_no");
                else if(int_type==13)//Rent Collected Report.....ship......25/04/2006
                	rs = stmt.executeQuery("select lm.*,lt.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,lty.descrptn,m.moduleabbr,l.key_no from LockerTransaction lt,CustomerMaster cm,LockerMaster lm,LockerType lty,Modules m,Lockers l where concat(right(lt.op_date,4),'-',mid(lt.op_date,locate('/',lt.op_date)+1,(locate('/',lt.op_date,4)-locate('/',lt.op_date)-1)),'-',left(lt.op_date,locate('/',lt.op_date)-1)) between '"+Validations.convertYMD(string_from_date)+"' and '"+Validations.convertYMD(string_to_date)+"' and lm.ac_no=lt.ac_no and lm.ac_type=lt.ac_type and cm.cid=lm.cid and lty.locker_type=lm.locker_ty and m.modulecode=lm.ac_type and lt.rent_by is not null and lm.locker_no=l.locker_no and lm.locker_ty=l.locker_ty and l.locker_ty=lty.locker_type and lm.ve_user is not null and lm.ve_tml is not null and lm.ve_date is not null and lt.ve_user is not null and lt.ve_tml is not null and lt.ve_date is not null");
                
                if(rs.next())
                {
                    rs.last();
                    lmo=new LockerMasterObject[rs.getRow()];
                    System.out.println("length in server "+rs.getRow());
                }
                
                rs.beforeFirst();
                int i=0;
                
                while(rs.next())
                {
                    lmo[i]=new LockerMasterObject();                
                    lmo[i].setLockerAcNo(rs.getInt("lm.ac_no"));
                    //  lmo[i].setName(rs.getString("cm.fname")+" "+rs.getString("cm.mname")+" "+rs.getString("cm.lname"));
                    lmo[i].setName(rs.getString("name"));
                    lmo[i].setKeyNo(rs.getInt("l.key_no"));
                    lmo[i].setLockerAcType(rs.getString("m.moduleabbr"));
                    lmo[i].setModuleCode(rs.getString("lm.ac_type"));
                    lmo[i].setCid(rs.getInt("cid"));
                    lmo[i].setAddressType(rs.getString("lm.addr_type"));
                    lmo[i].setLockerType(rs.getString("lty.descrptn"));
                    lmo[i].setLockerNo(rs.getInt("lm.locker_no"));
                    lmo[i].setLockerPW(rs.getString("lm.locker_pw"));
                    lmo[i].setAllotDate(rs.getString("lm.allot_dt"));
                    lmo[i].setMatDate(rs.getString("lm.mat_date"));
                    lmo[i].setMemberType(rs.getString("lm.sh_type"));
                    lmo[i].setMemberNo(rs.getInt("lm.sh_no"));
                    lmo[i].setNoOfJntHldrs(rs.getInt("lm.no_jt_hldr"));
                    lmo[i].setReqdMonths(rs.getInt("lm.req_mths"));
                    lmo[i].setRequiredDays(rs.getInt("lm.required_days"));
                    lmo[i].setOprInstrn(rs.getString("lm.op_inst"));
                    //ship....21/03/2006
//                    lmo[i].setRentBy(rs.getString("rent_by"));
//                    lmo[i].setTransAcType(rs.getString("trf_acty"));
//                    lmo[i].setTransAcNo(rs.getInt("trf_acno"));
//                    lmo[i].setRentUpto(rs.getString("rent_upto"));
//                    lmo[i].setRentColl(rs.getDouble("rent_coll"));
                    //////////
                    
                    lmo[i].setAutoExtn(rs.getString("lm.auto_extn"));
                    lmo[i].setIntrAcTy(rs.getString("lm.intr_ac_type"));
                    lmo[i].setIntrAcNo(rs.getInt("lm.intr_ac_no"));
                    //lmo[i].setIntrName(rs.getString("cm.fname")+" "+rs.getString("cm.mname")+" "+rs.getString("cm.lname"));
                    
                    /**
                     * Bug report: 
                     * why i'm setting account holder name to introducer name in the object analyse
                     */
                    
                    lmo[i].setIntrName(rs.getString("name"));
                    lmo[i].setNomRegNo(rs.getInt("lm.nom_no"));
                    lmo[i].setCloseDate(rs.getString("lm.close_dt"));
                    lmo[i].uv.setUserId(rs.getString("lm.de_user"));
                    lmo[i].uv.setUserTml(rs.getString("lm.de_tml"));
                    lmo[i].uv.setVerId(rs.getString("lm.ve_user"));
                    lmo[i].uv.setVerTml(rs.getString("lm.ve_tml"));
                    
                    //ship.......10/04/2006
                    /*lmo[i].setClosingVEUser(rs.getString("cl_ve_user"));
                    lmo[i].setClosingDEUser(rs.getString("cl_de_user"));*/
                    ///////////
                    
                    //ship......21/04/2006.....06/05/2006
                    if(int_type==1 || int_type==5 || int_type==6)
                    {
                        lmo[i].setRentBy(rs.getString("t.rent_by"));
                        lmo[i].setTransAcType(rs.getString("t.trf_acty"));
                        lmo[i].setTransAcNo(rs.getInt("t.trf_acno"));
                        lmo[i].setRentUpto(rs.getString("t.rent_upto"));
                        lmo[i].setRentColl(rs.getDouble("t.rent_amt"));
                    }
                    ////////
                    if(int_type==7)
                    {
                        lmo[i].setOperDate(rs.getString("t.op_date"));
                        lmo[i].setOperBy(rs.getString("t.oper_by"));
                        lmo[i].setTimeIn(rs.getString("t.time_in"));
                        lmo[i].setTimeOut(rs.getString("t.time_out"));
                        
                        System.out.println("..in.."+lmo[i].getTimeIn()+"+db+"+rs.getString("lt.time_in"));
                        System.out.println("..out.."+lmo[i].getTimeOut()+"+db+"+rs.getString("lt.time_out"));
                        
                    }
                    //ship.......25/04/2006
                    if(int_type==13)
                    {
                        lmo[i].setRentBy(rs.getString("lt.rent_by"));
                        lmo[i].setTransAcType(rs.getString("lt.trf_acty"));
                        lmo[i].setTransAcNo(rs.getInt("lt.trf_acno"));
                        lmo[i].setRentUpto(rs.getString("lt.rent_upto"));
                        lmo[i].setRentColl(rs.getDouble("lt.rent_amt"));
                        lmo[i].setOperDate(rs.getString("lt.op_date"));
                    }
                    ////////////
                    i++;
                }
            }
            catch(SQLException sqlexception)
            {
                sqlexception.printStackTrace();
                obj_sessionContext.setRollbackOnly();
                return lmo=null;            
            }
            finally
            {
                try
                {
                    stmt.executeUpdate("drop table if exists temp");
                    conn.close();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
            
            return lmo;
        }
        
      /**
       * Gives the details of the given cid of the customer. 
       */
	  public String[] getCustomerAddress(int int_cid)
	  {
	        String[] array_string=null;
	        
	        ResultSet rs=null;
	        Connection conn=null;
	        Statement stmt=null;
	        
	        try
	        {
	            conn=getConnection();
	        
	            if(conn==null)
	                throw new SQLException();
	    
	            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	            rs=stmt.executeQuery("select address,mobile,email from CustomerAddr where cid="+int_cid+" ");    
	         
	            rs.last();
	            array_string=new String[3];
	            rs.beforeFirst();
	            
	            if(rs.next())
	            {
	                array_string[0]=rs.getString(1);
	                array_string[1]=rs.getString(2);
	                array_string[2]=rs.getString(3);
	                
	            }
	        }
	        catch(SQLException sqlexception)
	        {
	            sqlexception.printStackTrace();
	            obj_sessionContext.setRollbackOnly();
	        }
	        finally
	        {
	            try
	            {
	                conn.close();
	            }
	            catch(SQLException e)
	            {
	                e.printStackTrace();
	            }
	        }
	        
	        return array_string;
	  }
  
	  /**
	   * Deletes the Template of the given template no of the a/c.
	   */
	  public int getDeleteTemplate(String ac_type,int int_template_no)
	  {
	     System.out.println("****inside getDeleteTemlate*****");
	      
	     System.out.println("temp no "+int_template_no);
	     System.out.println("ac_type "+ac_type);
	     
	      Connection conn=null;
	      try
	      {         
	          conn=getConnection();
	
	          Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	          
	          if(stmt.executeUpdate("delete from Template where temp_no="+int_template_no+" and ac_type='"+ac_type+"' ")==0)
	                        throw new SQLException();
	      }
	      catch(SQLException ex)
	      {
	          ex.printStackTrace();
	          obj_sessionContext.setRollbackOnly();
	      }
	      finally
	      {
	          try
	          {
	              conn.close();
	          }
	          catch(SQLException e)
	          {
	              e.printStackTrace();
	          }
	      }
	      
	      return 0;
	  }
  
	  /**
	   * Deletes the templates of the given template nos of the a/c.
	   */
	  public int getDeleteTemplate(String ac_type,int[] int_template_no)
	  {
	     System.out.println("****inside getDeleteTemlate*****");
	      
	     System.out.println("temp no "+int_template_no);
	     System.out.println("ac_type "+ac_type);
	     
	      Connection conn=null;
	      try
	      {         
	          conn=getConnection();
	
	          Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	          
	          for(int i=0;i<int_template_no.length;i++)
	          {
	              if(stmt.executeUpdate("delete from Template where temp_no="+int_template_no[i]+" and ac_type='"+ac_type+"' ")==0)
	                        throw new SQLException();
	          }
	      }
	      catch(SQLException ex)
	      {
	          ex.printStackTrace();
	          obj_sessionContext.setRollbackOnly();
	      }
	      finally
	      {
	          try
	          {
	              conn.close();
	          }
	          catch(SQLException e)
	          {
	              e.printStackTrace();
	          }
	      }      
	      
	      return 0;   
	  }
  
	    /**
	     * Updation & Verification of Locker a/c - LockerExtension, LockerRents.
	     */
		public boolean updateLocker(LockerMasterObject lockerMasterObject,int int_type)
		{
			System.out.println("<------------inside update Locker-------------->"+int_type);
			System.out.println("ac_type-->"+lockerMasterObject.getLockerAcType());
	        System.out.println("ac_no"+lockerMasterObject.getLockerAcNo());
	        
	        PreparedStatement ps=null;
			Connection  conn=null;
			Statement stmt = null,stmt_trnseq = null;
			ResultSet rs_trnseq = null;
			int trn_seq = 0,ac_trn_seq = 0;
			int flag = 0;
							
			try
			{
				conn=getConnection();
				
				stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);//ship...20/02/2006
				stmt_trnseq=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);//ship...20/02/2006
						
				if(conn==null)
					throw new SQLException();
				
				//ship.....20/02/2006
				rs_trnseq = stmt_trnseq.executeQuery("select * from LockerTransaction where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" order by trn_seq desc limit 1");
				if(rs_trnseq.next())
				    trn_seq = rs_trnseq.getInt("trn_seq");
			
				System.out.println("trn_seq = "+trn_seq);
				////////
				
				if(int_type==1)//Locker extension
				{
					/*ps=conn.prepareStatement("update LockerMaster set mat_date=?,req_mths=req_mths+? where ac_no="+lockerMasterObject.getLockerAcNo()+" and ac_type='"+lockerMasterObject.getLockerAcType()+"'");
					ps.setString(1,lockerMasterObject.getMatDate());
					ps.setInt(2,lockerMasterObject.getReqdMonths());*/
					
					//ship.......20/02/2006
					ps = conn.prepareStatement("insert into LockerTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+lockerMasterObject.uv.getUserDate()+"',null,null,null)");
	                
	                ps.setString(1,lockerMasterObject.getLockerAcType());
	                ps.setInt(2,lockerMasterObject.getLockerAcNo());
	                ps.setInt(3,lockerMasterObject.getLockerNo());
	                ps.setString(4,lockerMasterObject.getRentBy());
	                
	                System.out.println("getRentBy = "+lockerMasterObject.getRentBy());
	                if(lockerMasterObject.getRentBy().equals("T"))
					{
						System.out.println("********inside if(transfer)******");
						ps.setString(5,lockerMasterObject.getTransAcType());	
						ps.setInt(6,lockerMasterObject.getTransAcNo());
					}
					else
					{
						System.out.println("********inside else(scroll)*******");
						ps.setString(5,null);	
						ps.setInt(6,lockerMasterObject.getTransAcNo());
					}
	                
	                ps.setDouble(7,lockerMasterObject.getRentColl());
	                ps.setString(8,lockerMasterObject.getRentUpto());
	                ps.setInt(9,trn_seq+1);//trn_seq
	                ps.setString(10,"R");//trn_type
	                ps.setString(11,"C");//cd_ind
	                ps.setString(12,null);//oper_by
	                ps.setString(13,lockerMasterObject.getTrnDate());//op_date
	                ps.setString(14,null);//time_in
	                ps.setString(15,null);//time_out
	                
	                ps.setString(16,lockerMasterObject.uv.getUserTml());
					ps.setString(17,lockerMasterObject.uv.getUserId());
					
					int create = ps.executeUpdate();
	                
					if(create==1)
					{
                        //ship.....06/03/2007
					    /*if(lockerMasterObject.getRentBy().equals("C"))
						{
							if(stmt.executeUpdate("update DayCash set attached='T' where scroll_no="+lockerMasterObject.getTransAcNo()+" and ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and trn_date='"+lockerMasterObject.getTrnDate()+"'")==0)
							    throw new SQLException();
						}*/	

                        ResultSet rs_daycash = stmt.executeQuery("select * from DayCash where trn_date='"+lockerMasterObject.getTrnDate()+"' and ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and attached='T' and ve_tml is null and ve_user is null and ve_date is null");
                        
                        if(rs_daycash.next())
                        {
                            if(stmt.executeUpdate("update DayCash set attached='F' where trn_date='"+lockerMasterObject.getTrnDate()+"' and ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and attached='T' and ve_tml is null and ve_user is null and ve_date is null")==0)
                                throw new SQLException();
                        }
                        
                        if(lockerMasterObject.getRentBy().equals("C"))
                        {
                            if(stmt.executeUpdate("update DayCash set attached='T' where trn_date='"+lockerMasterObject.getTrnDate()+"' and scroll_no="+lockerMasterObject.getTransAcNo()+" and ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and attached='F' and ve_tml is null and ve_user is null and ve_date is null")==0)
                                throw new SQLException();
                        }
                        /////////////////
                        
					    //ship....16/03/2006
					    Statement stmt_dep = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				        ResultSet rs_dep = stmt_dep.executeQuery("select * from ODInterestDetails where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and from_date='"+lockerMasterObject.getTrnDate()+"' and to_date is null");
				        if(rs_dep.next())
				            if(stmt.executeUpdate("delete from ODInterestDetails where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and from_date='"+lockerMasterObject.getTrnDate()+"' and to_date is null")==0)
						        throw new SQLException();
				        
					    for(int d = 0;d<lockerMasterObject.getNoOfSecurities();d++)
					    {
					        Vector deposit = lockerMasterObject.getDeposits();
					        String ac_type = null;
					        String ac_no = null;
					        
					        System.out.println("getDeposits = "+deposit.get(d));
					        
					        StringTokenizer st = new StringTokenizer(String.valueOf(deposit.get(d))," ");
					        while(st.hasMoreTokens())
					        {
					            ac_type = st.nextToken();
					            ac_no = st.nextToken();
					            System.out.println("ac_type = "+ac_type);
					            System.out.println("ac_no = "+ac_no);
					        }
					        
					        PreparedStatement ps_dep=conn.prepareStatement("insert into ODInterestDetails values('"+lockerMasterObject.getLockerAcType()+"',"+lockerMasterObject.getLockerAcNo()+",'"+ac_type+"',"+ac_no+",null,null,null,null,null,'"+lockerMasterObject.getTrnDate()+"','"+lockerMasterObject.getRentUpto()+"')");
				            ps_dep.executeUpdate();		
					    }
					    ////////////
					    flag = 1;
					}
					/////////////
				}
				//ship......31/03/2006
				/*else if(int_type==2)//Locker Surrender
				{
					ps=conn.prepareStatement("update LockerMaster set cl_de_tml=?,cl_de_user=?,cl_de_date=date_format(sysdate(),'%d/%m/%Y %r'),close_dt=date_format(sysdate(),'%d/%m/%Y') where ac_no="+lockerMasterObject.getLockerAcNo()+" and ac_type='"+lockerMasterObject.getLockerAcType()+"'");
					ps.setString(1,lockerMasterObject.getClosingDETml());
					ps.setString(2,lockerMasterObject.getClosingDEUser());
					
					if(ps.executeUpdate()==1)//ship......20/02/2006
					    flag = 1;
				}	
				else if(int_type==3)//verifying Locker Surrender
				{
					ps=conn.prepareStatement("update LockerMaster set close_dt=date_format(sysdate(),'%d/%m/%Y'),cl_ve_tml=?,cl_ve_user=?,cl_ve_date=date_format(sysdate(),'%d/%m/%Y %r') where ac_no="+lockerMasterObject.getLockerAcNo()+" and ac_type='"+lockerMasterObject.getLockerAcType()+"'");
					//			ps=conn.prepareStatement("update LockerMaster,Locker set close_dt='"+getSysDate()+"',cl_ve_tml=?,cl_ve_user=?,cl_ve_date=date_format(sysdate(),'%d/%m/%Y %r'),Locker.locker_st='F' where ac_no="+lockerMasterObject.getLockerAcNo()+" and ac_type='"+lockerMasterObject.getLockerAcType()+"' and Locker.locker_no="+lockerMasterObject.getLockerNo()+" and Locker.locker_ty='"+lockerMasterObject.getLockerType()+"'");
					ps.setString(1,lockerMasterObject.getClosingVETml());
					ps.setString(2,lockerMasterObject.getClosingVEUser());
					
					if(ps.executeUpdate()==1)
					{
					    flag = 1;
					    ps=conn.prepareStatement("update Lockers set locker_st='F' where locker_no="+lockerMasterObject.getLockerNo()+" and locker_ty='"+lockerMasterObject.getLockerType()+"'");
					}
				}*/	
				/////////////
				else if(int_type==4)//Locker Rent Collection
				{
					ps=conn.prepareStatement("update LockerMaster set mat_date=?,req_mths=? where ac_no="+lockerMasterObject.getLockerAcNo()+" and ac_type='"+lockerMasterObject.getLockerAcType()+"'");
					ps.setString(1,lockerMasterObject.getMatDate());
					ps.setInt(2,lockerMasterObject.getReqdMonths());
					
					if(ps.executeUpdate()==1)//ship.....20/02/2006
					    flag = 1;
				}	
				//ship....05/04/2006....added int_type==6....for locker rent
				else if(int_type==5 || int_type==6)//Locker extension Verification
				{
					/*ps=conn.prepareStatement("update LockerMaster set ve_user=?,ve_tml=?,ve_date=date_format(sysdate(),'%d/%m/%Y %r') where ac_no="+lockerMasterObject.getLockerAcNo()+" and ac_type='"+lockerMasterObject.getLockerAcType()+"'");
					ps.setString(1,lockerMasterObject.uv.getVerId());
					ps.setString(2,lockerMasterObject.uv.getVerTml());
	                
	                System.out.println("-------------inside type 5-----------");*/
				    
				    //ship....05/04/2006
				    if(int_type==6)
				    {   System.out.println(lockerMasterObject.getNoOfSecurities()+"><><"+lockerMasterObject.getReqdMonths()+"??"+lockerMasterObject.getRequiredDays()+"inside bean of type 66666"+lockerMasterObject.getLockerAcType()+"<->"+lockerMasterObject.getRentBy()+"<->"+lockerMasterObject.getLockerNo()+"<->"+lockerMasterObject.getLockerAcNo()+"<->"+lockerMasterObject.getTransAcType()+"<->"+lockerMasterObject.getTransAcNo()+"<->"+lockerMasterObject.getRentColl()+"<->"+lockerMasterObject.getRentUpto()+"<->"+trn_seq+"<->"+lockerMasterObject.getTrnDate());
				        ps = conn.prepareStatement("insert into LockerTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+lockerMasterObject.uv.getUserDate()+"',?,?,'"+lockerMasterObject.uv.getVerDate()+"')");
		                
		                ps.setString(1,lockerMasterObject.getLockerAcType());
		                ps.setInt(2,lockerMasterObject.getLockerAcNo());
		                ps.setInt(3,lockerMasterObject.getLockerNo());
		                ps.setString(4,lockerMasterObject.getRentBy());
		                ps.setString(5,lockerMasterObject.getTransAcType());	
						ps.setInt(6,lockerMasterObject.getTransAcNo());	                
		                ps.setDouble(7,lockerMasterObject.getRentColl());
		                ps.setString(8,lockerMasterObject.getRentUpto());
		                ps.setInt(9,trn_seq+1);//trn_seq
		                ps.setString(10,"R");//trn_type
		                ps.setString(11,"C");//cd_ind
		                ps.setString(12,null);//oper_by
		                ps.setString(13,lockerMasterObject.getTrnDate());//op_date
		                ps.setString(14,null);//time_in
		                ps.setString(15,null);//time_out
		                
		                ps.setString(16,lockerMasterObject.uv.getUserTml());
						ps.setString(17,lockerMasterObject.uv.getUserId());
						
						ps.setString(18,lockerMasterObject.uv.getVerTml());
						ps.setString(19,lockerMasterObject.uv.getVerId());
						
						int create = ps.executeUpdate();
						
						
						
						if(create==1)
						{
						    for(int d = 0;d<lockerMasterObject.getNoOfSecurities();d++)
						    {
						        Vector deposit = lockerMasterObject.getDeposits();
						        String ac_type = null;
						        String ac_no = null;
						        
						        System.out.println("locker.getdeposits----"+lockerMasterObject.getDeposits());
						        System.out.println("vectore deposit---"+deposit.size());
						        Enumeration enumeration=deposit.elements();
						        //StringTokenizer st = new StringTokenizer(String.valueOf(deposit.get(d))," ");
						         	
						        while(enumeration.hasMoreElements())
						        {
						        	StringTokenizer st=new StringTokenizer(enumeration.nextElement().toString()," ");
			                           
						            ac_type = st.nextToken();
						            ac_no = st.nextToken();
						            System.out.println("ac_type = "+ac_type);
						            System.out.println("ac_no = "+ac_no);
						        }
						        
						        PreparedStatement ps_dep=conn.prepareStatement("insert into ODInterestDetails values('"+lockerMasterObject.getLockerAcType()+"',"+lockerMasterObject.getLockerAcNo()+",'"+ac_type+"',"+ac_no+",null,null,null,null,null,'"+lockerMasterObject.getTrnDate()+"','"+lockerMasterObject.getRentUpto()+"')");
					            ps_dep.executeUpdate();		
						    }
						}
				    }
				    
				    //ship.......20/02/2006
				    stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				    
				    ResultSet rs_cash = null,rs_transfer = null,rs_locker = null,rs = null,rs_lk_trn = null;
					
					GLTransObject trnobj=new GLTransObject();
					
					rs = stmt.executeQuery("select * from LockerMaster where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"");
					
					if(rs.next())
					{
					    //ship......16/03/2006
					    String last_mat_date = rs.getString("mat_date");
					    ///////////
					    
					    
					    System.out.println("monthds==>"+rs.getInt("req_mths"));
					    System.out.println("2----->"+rs.getInt("required_days"));
					    System.out.println("5---->");
					   
					    int req_days=rs.getInt("required_days");
				        int days=lockerMasterObject.getRequiredDays();
				        int req_months=rs.getInt("req_mths");
				        int months=lockerMasterObject.getReqdMonths();
				         int tot_req_days=(req_days+days);
					    int tot_req_months=(req_months+months);
					   
					    		
					   

					    System.out.println("Om namah shiva-->"+lockerMasterObject.getMatDate());
            			System.out.println("Om namah shiva-->"+lockerMasterObject.getReqdMonths());
            			System.out.println("Om namah shiva-->"+lockerMasterObject.getRequiredDays());
            			System.out.println("Om namah shiva-->"+lockerMasterObject.getTrnDate());
            			System.out.println("Om namah shiva-->"+lockerMasterObject.getNoOfSecurities());
            			System.out.println("Om namah shiva-->"+lockerMasterObject.getLockerAcNo());
            			System.out.println("Om namah shiva-->"+lockerMasterObject.uv.getVerDate());
            			System.out.println("Om namah shiva-->"+lockerMasterObject.getTrnDate());
            			System.out.println("Om namah shiva-->"+lockerMasterObject.getLockerAcType());
            			
					    
					    
					    if(stmt.executeUpdate("insert into LockerMasterLog select * from LockerMaster where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"")!=1)
							throw new SQLException("Record not inserted in LockerMasterLog");
						
					    if(int_type==5)//for locker Extn...verify
					    {
					        rs_lk_trn = stmt.executeQuery("select de_user,de_tml,de_date from LockerTransaction where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and op_date='"+lockerMasterObject.getTrnDate()+"' and rent_by is not null and ve_tml is null and ve_user is null and ve_date is null");
					      //  rs_lk_trn.next();
					        
					        if(rs_lk_trn.next())
					        {
					        	System.out.println("3--->"+rs_lk_trn.getString("de_user"));
					        	System.out.println("4--->"+rs_lk_trn.getString("de_date"));
							   
					            if(stmt.executeUpdate("update LockerMaster set mat_date='"+lockerMasterObject.getMatDate()+"',req_mths="+tot_req_months+",required_days="+tot_req_days+",lst_tr_seq="+trn_seq+",lst_trndt='"+lockerMasterObject.getTrnDate()+"',no_securities="+lockerMasterObject.getNoOfSecurities()+",de_user='"+rs_lk_trn.getString("de_user")+"',de_tml='"+rs_lk_trn.getString("de_tml")+"',de_date='"+rs_lk_trn.getString("de_date")+"',ve_user='"+lockerMasterObject.uv.getVerId()+"',ve_tml='"+lockerMasterObject.uv.getVerTml()+"',ve_date='"+lockerMasterObject.uv.getVerDate()+"' where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and ve_user is not null and ve_tml is not null and ve_date is not null")==0)
				                    throw new SQLException();
					            
					            if(stmt.executeUpdate("update LockerTransaction set ve_user='"+lockerMasterObject.uv.getVerId()+"',ve_tml='"+lockerMasterObject.uv.getVerTml()+"',ve_date='"+lockerMasterObject.uv.getVerDate()+"' where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and ve_user is null and ve_tml is null and ve_date is null and op_date='"+lockerMasterObject.getTrnDate()+"' and time_in is null and time_out is null")==0)
						            throw new SQLException();
					        }
					    }
					    else if(int_type==6)//locker rent....ship.....03/05/2006
					    {
					        if(stmt.executeUpdate("update LockerMaster set mat_date='"+lockerMasterObject.getMatDate()+"',req_mths="+rs.getInt("req_mths")+"+"+lockerMasterObject.getReqdMonths()+",required_days="+rs.getInt("required_days")+"+"+lockerMasterObject.getRequiredDays()+",lst_tr_seq="+trn_seq+",lst_trndt='"+lockerMasterObject.getTrnDate()+"',no_securities="+lockerMasterObject.getNoOfSecurities()+",de_user='"+lockerMasterObject.uv.getUserId()+"',de_tml='"+lockerMasterObject.uv.getUserTml()+"',de_date='"+lockerMasterObject.uv.getUserDate()+"',ve_user='"+lockerMasterObject.uv.getVerId()+"',ve_tml='"+lockerMasterObject.uv.getVerTml()+"',ve_date='"+lockerMasterObject.uv.getVerDate()+"' where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and ve_user is not null and ve_tml is not null and ve_date is not null")==0)
			                    throw new SQLException();
					    }
						
						//ship.....16/03/2006
						int isThere = 0;
						
						Statement st_dep = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				        ResultSet res_dep = st_dep.executeQuery("select * from ODInterestDetails where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and from_date is not null and to_date is not null");
				        
				        while(res_dep.next())
				        {
				            System.out.println("res_dep.next().....start ");
				            isThere = 0;
				            String dep_ac_type = res_dep.getString("sec_ac_type");
				            int dep_ac_no = res_dep.getInt("sec_ac_no");
				            
				            System.out.println("dep_ac_type = "+dep_ac_type);
				            System.out.println("dep_ac_no = "+dep_ac_no);
				            System.out.println("last_mat_date = "+last_mat_date);
				        
				            if(res_dep.getString("to_date").equals(last_mat_date))
				            {
				                System.out.println("inside equals(last_mat_date)");
				                /*for(int d = 0;d<lockerMasterObject.getNoOfSecurities();d++)
							    {
							        Vector deposit = lockerMasterObject.getDeposits();
							        String ac_type = null;
							        String ac_no = null;
							        
							        System.out.println("getDeposits = "+deposit.get(d));
							        
							        StringTokenizer st = new StringTokenizer(String.valueOf(deposit.get(d))," ");
							        while(st.hasMoreTokens())
							        {
							            ac_type = st.nextToken();
							            ac_no = st.nextToken();
							            System.out.println("ac_type = "+ac_type);
							            System.out.println("ac_no = "+ac_no);
							        }
							        
							        if(dep_ac_type.equals(ac_type) && dep_ac_no==Integer.parseInt(ac_no))
							        {
							            System.out.println("inside dep_ac_type==ac_type");
							            isThere = 0;
							            break;
							        }
							    }*/
				                isThere = 0;
				            }
				            else if(res_dep.getString("from_date").equals(lockerMasterObject.getTrnDate()))
				            {
				                System.out.println("inside equals(getSysDate())");
				                isThere = 2;
				            }
				            
				            System.out.println("isThere = "+isThere);
				            
				            if(isThere==0)
				            {
				                if(stmt.executeUpdate("update DepositMaster set ln_availed='F',ln_ac_type=null,ln_ac_no=null where ac_type='"+dep_ac_type+"' and ac_no="+dep_ac_no+" and ln_availed='T' and ln_ac_type='"+lockerMasterObject.getLockerAcType()+"' and ln_ac_no="+lockerMasterObject.getLockerAcNo()+"")==0)
				                    throw new SQLException();
				                
				                try
				                {
				                    if(stmt.executeUpdate("update ODInterestDetails set to_date='"+Validations.addDays(lockerMasterObject.getTrnDate(),-1)+"' where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and sec_ac_type='"+dep_ac_type+"' and sec_ac_no="+dep_ac_no+" and from_date is not null and to_date='"+last_mat_date+"'")==0)
					                    throw new SQLException();
				                }
				                catch(DateFormatException dfe)
				                {
				                    dfe.printStackTrace();
				                }
				            }
				            /*else if(isThere==1)
				            {
				                try
				                {
				                    if(stmt.executeUpdate("update ODInterestDetails set to_date='"+Validations.addDays(getSysDate(),-1)+"' where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and sec_ac_type='"+dep_ac_type+"' and sec_ac_no="+dep_ac_no+" and from_date is not null and to_date='"+last_mat_date+"'")==0)
					                    throw new SQLException();
				                }
				                catch(DateFormatException dfe)
				                {
				                    dfe.printStackTrace();
				                }
				            }*/
				            else if(isThere==2)
				            {
				                if(stmt.executeUpdate("update DepositMaster set ln_availed='T',ln_ac_type='"+lockerMasterObject.getLockerAcType()+"',ln_ac_no="+lockerMasterObject.getLockerAcNo()+" where ac_type='"+dep_ac_type+"' and ac_no="+dep_ac_no+" and ln_availed='F'")==0)
				                    throw new SQLException();
				            }
				            
				            System.out.println("res_dep.next().....end ");
				        }
				        /////////////
						
						System.out.println("Rent By = "+lockerMasterObject.getRentBy());
						
						if(lockerMasterObject.getRentBy().equals("C"))
						{
						    System.out.println("inside lk_verify...Cash");
						    
						    if(stmt.executeUpdate("update DayCash set trn_seq="+trn_seq+",ve_user='"+lockerMasterObject.uv.getVerId()+"',ve_tml='"+lockerMasterObject.uv.getVerTml()+"',ve_date='"+lockerMasterObject.uv.getVerDate()+"' where scroll_no="+lockerMasterObject.getTransAcNo()+" and trn_date='"+lockerMasterObject.getTrnDate()+"' and attached='T' and ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and ve_user is null and ve_tml is null and ve_date is null")==0)
		                        throw new SQLException();
						    
						    //inserting into GLTran.....Cash Balance
						    rs_cash = stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1019001' and code=1");
						    
						    if(rs_cash.next())
						    {
						        trnobj.setGLCode(rs_cash.getString("gl_code"));
					            trnobj.setTrnDate(lockerMasterObject.getTrnDate());
					            trnobj.setGLType(rs_cash.getString("gl_type"));
					            trnobj.setTrnMode("C");
					            trnobj.setAmount(lockerMasterObject.getRentColl());
					            trnobj.setCdind("D");
					            trnobj.setAccType("1019001");
					            trnobj.setAccNo(null);
					            trnobj.setTrnSeq(0);//ship......20/12/2005
					            trnobj.setTrnType(null);
					            trnobj.setRefNo(lockerMasterObject.getTransAcNo());
					            trnobj.setVtml(lockerMasterObject.uv.getVerTml());
					            trnobj.setVid(lockerMasterObject.uv.getVerId());
					            trnobj.setVDate(lockerMasterObject.uv.getVerDate());
					            
					            if(commonremote_local.storeGLTransaction(trnobj)==1)
					            {			                
					                //inserting into GLTran......Locker Account
					                rs_locker = stmt.executeQuery("select gp.gl_code,gk.gl_type,gp.mult_by from GLPost gp,GLKeyParam gk where gp.ac_type='"+lockerMasterObject.getLockerAcType()+"' and gk.ac_type='"+lockerMasterObject.getLockerAcType()+"' and trn_type='R' and cr_dr='C' and gk.code=1");
					                
								    if(rs_locker.next())
								    {
								        trnobj.setGLCode(rs_locker.getString("gp.gl_code"));
							            trnobj.setTrnDate(lockerMasterObject.getTrnDate());
							            trnobj.setGLType(rs_locker.getString("gk.gl_type"));
							            trnobj.setTrnMode("C");
							            trnobj.setAmount(lockerMasterObject.getRentColl()*rs_locker.getInt("gp.mult_by"));
							            trnobj.setCdind("C");
							            trnobj.setAccType(lockerMasterObject.getLockerAcType());
							            trnobj.setAccNo(String.valueOf(lockerMasterObject.getLockerAcNo()));
							            trnobj.setTrnSeq(trn_seq);//ship......20/12/2005
							            trnobj.setTrnType("R");
							            trnobj.setRefNo(lockerMasterObject.getTransAcNo());
							            trnobj.setVtml(lockerMasterObject.uv.getVerTml());
							            trnobj.setVid(lockerMasterObject.uv.getVerId());
							            trnobj.setVDate(lockerMasterObject.uv.getVerDate());
							            
							            commonremote_local.storeGLTransaction(trnobj);
								    }//end...rs_locker
					            }
						    }//end...rs_cash
						    flag = 1;
						}
						else if(lockerMasterObject.getRentBy().equals("T"))
						{
						    System.out.println("inside lk_verify...Transfer");
						    
						    ResultSet rs_tran = stmt.executeQuery("select trn_seq,cl_bal from AccountTransaction where ac_type='"+lockerMasterObject.getTransAcType()+"' and ac_no="+lockerMasterObject.getTransAcNo()+" order by trn_seq desc");
						    
			                if(rs_tran.next())
			                {
			                    System.out.println("inside rs_tran"+lockerMasterObject.getTransAcType()+"<-->"+lockerMasterObject.getTransAcNo()+"<-->"+lockerMasterObject.getTrnDate()+"<-->"+ac_trn_seq+"<-->"+lockerMasterObject.getRentColl());
			                    
			                    ac_trn_seq = rs_tran.getInt(1)+1;
			                    
				                if(stmt.executeUpdate("insert into AccountTransaction values('"+lockerMasterObject.getTransAcType()+"',"+lockerMasterObject.getTransAcNo()+",'"+lockerMasterObject.getTrnDate()+"','P',"+ac_trn_seq+","+lockerMasterObject.getRentColl()+",'T','"+lockerMasterObject.uv.getVerTml()+"','D',null,null,'"+lockerMasterObject.getLockerAcType()+" "+lockerMasterObject.getLockerAcNo()+"',0,null,"+(rs_tran.getDouble("cl_bal")-lockerMasterObject.getRentColl())+",0,'"+lockerMasterObject.uv.getUserTml()+"','"+lockerMasterObject.uv.getUserId()+"','"+lockerMasterObject.uv.getUserDate()+"','"+lockerMasterObject.uv.getVerTml()+"','"+lockerMasterObject.uv.getVerId()+"','"+lockerMasterObject.uv.getVerDate()+"')")==0)
				                    throw new SQLException();
				                
				                if(stmt.executeUpdate("update AccountMaster set last_tr_seq="+ac_trn_seq+",last_tr_date='"+lockerMasterObject.getTrnDate()+"' where ac_type='"+lockerMasterObject.getTransAcType()+"' and ac_no="+lockerMasterObject.getTransAcNo()+"")==0)
				                    throw new SQLException();
			                }
						    
						    //inserting into GLTran.....transfer a/c
						    rs_transfer = stmt.executeQuery("select gp.gl_code,gk.gl_type,gp.mult_by from GLPost gp,GLKeyParam gk where gp.ac_type='"+lockerMasterObject.getTransAcType()+"' and gk.ac_type='"+lockerMasterObject.getTransAcType()+"' and trn_type='P' and cr_dr='D' and gk.code=1");
						    
						    if(rs_transfer.next())
						    {
						        System.out.println("inside rs_transfer");
						        
						        trnobj.setGLCode(rs_transfer.getString("gp.gl_code"));
					            trnobj.setTrnDate(lockerMasterObject.getTrnDate());
					            trnobj.setGLType(rs_transfer.getString("gk.gl_type"));
					            trnobj.setTrnMode("T");
					            trnobj.setAmount(lockerMasterObject.getRentColl()*rs_transfer.getInt("gp.mult_by"));
					            trnobj.setCdind("D");
					            trnobj.setAccType(lockerMasterObject.getTransAcType());
					            trnobj.setAccNo(String.valueOf(lockerMasterObject.getTransAcNo()));
					            trnobj.setTrnSeq(ac_trn_seq);//ship......20/12/2005
					            trnobj.setTrnType("P");
					            trnobj.setRefNo(0);
					            trnobj.setVtml(lockerMasterObject.uv.getVerTml());
					            trnobj.setVid(lockerMasterObject.uv.getVerId());
					            trnobj.setVDate(lockerMasterObject.uv.getVerDate());
					            
					            if(commonremote_local.storeGLTransaction(trnobj)==1)
					            {
					                //inserting into GLTran......Locker Account
					                rs_locker = stmt.executeQuery("select gp.gl_code,gk.gl_type,gp.mult_by from GLPost gp,GLKeyParam gk where gp.ac_type='"+lockerMasterObject.getLockerAcType()+"' and gk.ac_type='"+lockerMasterObject.getLockerAcType()+"' and trn_type='R' and cr_dr='C' and gk.code=1");
					                
								    if(rs_locker.next())
								    {
								        System.out.println("inside rs_locker");
								        
								        trnobj.setGLCode(rs_locker.getString("gp.gl_code"));
							            trnobj.setTrnDate(lockerMasterObject.getTrnDate());
							            trnobj.setGLType(rs_locker.getString("gk.gl_type"));
							            trnobj.setTrnMode("T");
							            trnobj.setAmount(lockerMasterObject.getRentColl()*rs_locker.getInt("gp.mult_by"));
							            trnobj.setCdind("C");
							            trnobj.setAccType(lockerMasterObject.getLockerAcType());
							            trnobj.setAccNo(String.valueOf(lockerMasterObject.getLockerAcNo()));
							            trnobj.setTrnSeq(trn_seq);//ship......20/12/2005
							            trnobj.setTrnType("R");
							            trnobj.setRefNo(0);
							            trnobj.setVtml(lockerMasterObject.uv.getVerTml());
							            trnobj.setVid(lockerMasterObject.uv.getVerId());
							            trnobj.setVDate(lockerMasterObject.uv.getVerDate());
							            
							            commonremote_local.storeGLTransaction(trnobj);
								    }
								    //end...rs_locker
					            }
						    }//end...rs_transfer
						    flag = 1;
						}
					}
				}
				
				/*if(ps.executeUpdate()==0)
					throw new SQLException();
				
				if(ps.executeUpdate()==1)
				{
					if(int_type==5)
					{
	                    System.out.println("-------------inside rent check-----------");
	                    
	                    if(rentCollectByTransfer(lockerMasterObject,3)==0)
							return true;
						
						return false;
					}				
					return true;
				}			
				return false;*/			
			}
			catch(SQLException sqlexception)
			{
			    sqlexception.printStackTrace();
			    obj_sessionContext.setRollbackOnly();
			}
			finally
			{
				try
				{
					conn.close();
				}
				catch(SQLException e)
				{
				    e.printStackTrace();
				}
			}
			
			if(flag==1)
			    return true;
			
			return false;		
		}
	
	//ship......04/07/2006
	/*String getSysDate()
	{
		Calendar c=Calendar.getInstance();
		String sysdate=null;
        try {
            sysdate = (Validations.checkDate(c.get(Calendar.DATE)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR)));
        } catch (DateFormatException e) {
            e.printStackTrace();
        }
		return sysdate;
	}
	
	//ship.....23/03/2006
	public String getSysTime() 
	{
	    //ship.....06/05/2006
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
	    
	    return(new Date().getHours()+":"+new Date().getMinutes()+":"+new Date().getSeconds()+"  ");
	}
	///////////
*/   
    // For LockerType Customization
    /**
     * Gives the details of the given Locker Type.
     */
    public LockerDetailsObject getLockerDetails(String string_locker_type)
    {
        System.out.println("**********inside getLockerDetails**********"+string_locker_type);
        System.out.println("hi ^^^S");
        
        /**
         * 
         * Locker type field in Locker Type should be a primary key. Otherwise it will give problem
         * while Customization.
         */
        Connection  conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        LockerDetailsObject loc=null;
        
        try
        {
            conn=getConnection();
            
            if(conn==null)
                throw new SQLException();
        
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs=stmt.executeQuery("select descrptn,length,height,depth from LockerType where locker_type='"+string_locker_type+"' ");
            
            if(rs.next())
            {
                loc=new LockerDetailsObject();
                loc.setDescription(rs.getString("descrptn"));
                loc.setLockerLength(rs.getString("length"));
                loc.setLockerHeight(rs.getString("height"));
                loc.setLockerDepth(rs.getString("depth"));
            }
        }
        catch(SQLException sqlexception)
        {
            sqlexception.printStackTrace();
            obj_sessionContext.setRollbackOnly();
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        return loc;
    }
    
    /**
     * LockerRateAdmin - Gives the Locker Rate details for different Locker Types.
     */
    public LockerDetailsObject[] getLockerRateParameter()
    {
        System.out.println("...inside getLockerRateParemeter...");
        LockerDetailsObject[] array_LockerDetailsObject=null;
        Connection  conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        
        try
        {
            conn=getConnection();
            
            if(conn==null)
                throw new SQLException();
        
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs=stmt.executeQuery("select * from LockerRate order by locker_type desc,date_to");   
            
            if(rs.last())
            {
                array_LockerDetailsObject=new LockerDetailsObject[rs.getRow()];
                System.out.println("........length.........."+rs.getRow());
                rs.beforeFirst();
            }
            
            int i=0;
            
            while(rs.next())
            {
                array_LockerDetailsObject[i]=new LockerDetailsObject();
                
                array_LockerDetailsObject[i].setLockerType(rs.getString("locker_type"));
                array_LockerDetailsObject[i].setDateFrom(rs.getString("date_fr"));
                array_LockerDetailsObject[i].setDateTo(rs.getString("date_to"));
                array_LockerDetailsObject[i].setDaysFrom(rs.getInt("days_fr"));
                array_LockerDetailsObject[i].setDaysTo(rs.getInt("days_to"));
                array_LockerDetailsObject[i].setLockerRate(rs.getDouble("locker_rate"));
                array_LockerDetailsObject[i].setSecurityDeposit(rs.getDouble("security_deposit"));
                array_LockerDetailsObject[i].uv.setUserId(rs.getString("de_user"));
                array_LockerDetailsObject[i].uv.setUserTml(rs.getString("de_tml"));
                array_LockerDetailsObject[i].uv.setUserDate(rs.getString("de_date"));
                i++;
            }
        }
        catch(SQLException sqlexception)
        {
            sqlexception.printStackTrace();
            obj_sessionContext.setRollbackOnly();
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
     
        return array_LockerDetailsObject;
    }
    
    //ship......01/11/2006
    /**
     * 
     * @param array_lockerDetailsObject having all locker records to be deleted
     * @ if return 1 means record deleted successfully
     * @ if return 2 means record Not deleted
     * @ if return 3 means this locker type used by some locker account. So it shouldn't delete the record.
     */
    /*public int deleteLockerRateParameter(LockerDetailsObject[] array_lockerDetailsObject) 
    {
        Connection  conn=null;
        Statement stmt=null,stmt1=null;
        ResultSet rs=null;
        System.out.println(".........inside deleteLockerRateParameter........"+array_lockerDetailsObject.length);
        
        try
        {
            conn=getConnection();
            
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if(conn==null)
                throw new SQLException();
        
            for(int i=0;i<array_lockerDetailsObject.length;i++)
            {
                System.out.println("..........inside for loop..........");
                ps=conn.prepareStatement("delete from LockerRate where locker_type='"+array_lockerDetailsObject[i].getLockerType()+"' and date_fr='"+array_lockerDetailsObject[i].getDateFrom()+"' and date_to='"+array_lockerDetailsObject[i].getDateTo()+"' and days_fr='"+array_lockerDetailsObject[i].getDaysFrom()+"' and date_to='"+array_lockerDetailsObject[i].getDateTo()+"' and locker_rate='"+array_lockerDetailsObject[i].getLockerRate()+"' and security_deposit='"+array_lockerDetailsObject[i].getSecurityDeposit()+"' ");
                ps.addBatch();
                
            }
            if(ps.executeBatch().length==0)
            {
                if(stmt.executeUpdate("update LockerRate set date_to='99/99/9999' where date_to<'99/99/9999' and locker_type='"+array_lockerDetailsObject[0].getLockerType()+"' order by date_to desc limit 1")==0)
                    throw new SQLException();
                    
                return false;
            }
            
            if(array_lockerDetailsObject!=null)
            {
                System.out.println(""+array_lockerDetailsObject[0].getLockerType()+" "+array_lockerDetailsObject[0].getDateFrom());
                
                System.out.println("days diff"+commonremote_local.getDaysFromTwoDate(array_lockerDetailsObject[0].getDateFrom(),array_lockerDetailsObject[0].getTrnDate()));
                
                *//** 
                 * @param array_lockerDetailsObject[0].getLockerType()) giving the locker type
                 *//*
                if(checkLockerType(array_lockerDetailsObject[0].getLockerType()))
                    return 3;

                System.out.println("days diff"+commonremote_local.getDaysFromTwoDate(array_lockerDetailsObject[0].getDateFrom(),array_lockerDetailsObject[0].getTrnDate()));
                if(commonremote_local.getDaysFromTwoDate(array_lockerDetailsObject[0].getDateFrom(),array_lockerDetailsObject[0].getTrnDate())>0)
                    return 4;
                
                if(stmt.executeUpdate("delete from LockerRate where locker_type='"+array_lockerDetailsObject[0].getLockerType()+"' and date_fr='"+array_lockerDetailsObject[0].getDateFrom()+"' ")==0)
                    throw new SQLException();
                
                rs=stmt1.executeQuery("select * from LockerRate where date_to<'99/99/9999' and locker_type='"+array_lockerDetailsObject[0].getLockerType()+"' order by date_to desc limit 1");
                if(rs.next())
                    if(stmt.executeUpdate("update LockerRate set date_to='99/99/9999' where date_to<'99/99/9999' and locker_type='"+array_lockerDetailsObject[0].getLockerType()+"' ")==0)
                        throw new SQLException();
            }
        }
        catch(SQLException sqlexception)
        {
            sqlexception.printStackTrace();
            obj_sessionContext.setRollbackOnly();
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        return 1;
    }*/
    
    /**
     * LockerRateAdmin - Insertion & Updation of Locker Rate Parameters.
     */
    
   /*
    * lockers details  new method added by balu
    * 
    */
    
    
    public LockerDetailsObject[] getLockerDetails()
    {
        System.out.println("**********inside getLockerDetails**********");
        System.out.println("hi ^^^S");
        
        /**
         * 
         * Locker type field in Locker Type should be a primary key. Otherwise it will give problem
         * while Customization.
         */
        Connection  conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        LockerDetailsObject[] loc=null;
        
        try
        {
            conn=getConnection();
            
            if(conn==null)
                throw new SQLException();
        
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs=stmt.executeQuery("select locker_type,descrptn,length,height,depth from LockerType");
            if(rs.last())
            {
            	loc=new LockerDetailsObject[rs.getRow()];
                System.out.println("........length.........."+rs.getRow());
                rs.beforeFirst();
            }
            int k=0;
            while(rs.next())
            {
                loc[k]=new LockerDetailsObject();
                loc[k].setDescription(rs.getString("descrptn"));
                loc[k].setLockerLength(rs.getString("length"));
                loc[k].setLockerHeight(rs.getString("height"));
                loc[k].setLockerDepth(rs.getString("depth"));
                loc[k].setLockerType(rs.getString("locker_type"));
                k++;
            }
        }
        catch(SQLException sqlexception)
        {
            sqlexception.printStackTrace();
            obj_sessionContext.setRollbackOnly();
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        return loc;
    }
    
    
    
    
    
    
    
    public boolean addLockerRateParameter(LockerDetailsObject[] array_lockerDetailsObject,int type) throws RecordNotUpdatedException 
    {
        System.out.println("....inside addLockerRateParameter....length = "+array_lockerDetailsObject.length);
        
        PreparedStatement pstat=null;
        Connection conn=null;
        Statement stmt1,stmt_same_record,stmt_update_record,stmt_ckeck;
        ResultSet rs1=null,rs_check = null;
        int length = 0;
        
        try
        {
            conn=getConnection();
            
            if(conn==null)
                throw new SQLException();
                        
            stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt_same_record=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt_update_record=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt_ckeck=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                                    
		     if(array_lockerDetailsObject!=null)
		     {
		         if(type==0)//insert into Locker Rate
		         {
		             for(int i=0;i<array_lockerDetailsObject.length;i++)
			         {
		                 System.out.println("for i = "+i);
			             System.out.println("date from = "+array_lockerDetailsObject[i].getDateFrom());
			             System.out.println("locker type = "+array_lockerDetailsObject[i].getLockerType());
			             System.out.println("days from = "+array_lockerDetailsObject[i].getDaysFrom());
			             System.out.println("days to = "+array_lockerDetailsObject[i].getDaysTo());
				        
			             //rs1=stmt1.executeQuery("select * from LockerRate where date_to='31/12/9999' and locker_type='"+array_lockerDetailsObject[i].getLockerType()+"' and days_fr<"+array_lockerDetailsObject[i].getDaysTo()+" and days_to>"+array_lockerDetailsObject[i].getDaysFrom()+"");
			             //rs1=stmt1.executeQuery("select * from LockerRate where (date_to='31/12/9999' or (date_fr='"+array_lockerDetailsObject[i].getTrnDate()+"' and date_to='"+array_lockerDetailsObject[i].getTrnDate()+"')) and locker_type='"+array_lockerDetailsObject[i].getLockerType()+"' and days_fr<"+array_lockerDetailsObject[i].getDaysTo()+" and days_to>"+array_lockerDetailsObject[i].getDaysFrom()+"");
                         rs1=stmt1.executeQuery("select * from LockerRate where date_to='31/12/9999' and locker_type='"+array_lockerDetailsObject[i].getLockerType()+"'");
			             
			             //ship.....11/09/2006
			             if(rs1.last())
                         {
			                 rs1.beforeFirst();
                         }
			             else
			             {
			                 pstat=conn.prepareStatement("insert into LockerRate(locker_type,date_fr,date_to,days_fr,days_to,locker_rate,de_user,de_tml,de_date,security_deposit) values(?,?,?,?,?,?,?,?,?,?)");
						        
				             System.out.println(".........before executing.......length"+array_lockerDetailsObject.length);
				             System.out.println("before inserting lk type "+array_lockerDetailsObject[i].getLockerType());
				             
				             pstat.setString(1,array_lockerDetailsObject[i].getLockerType());
				             pstat.setString(2,array_lockerDetailsObject[i].getDateFrom());
				             pstat.setString(3,array_lockerDetailsObject[i].getDateTo());
				             pstat.setInt(4,array_lockerDetailsObject[i].getDaysFrom());
				             pstat.setInt(5,array_lockerDetailsObject[i].getDaysTo());
				             pstat.setDouble(6,array_lockerDetailsObject[i].getLockerRate());
				             pstat.setString(7,array_lockerDetailsObject[i].uv.getUserId());
				             pstat.setString(8,array_lockerDetailsObject[i].uv.getUserTml());
				             pstat.setString(9,array_lockerDetailsObject[i].uv.getUserDate());
				             pstat.setDouble(10,array_lockerDetailsObject[i].getSecurityDeposit());
				                
				             if(pstat.executeUpdate()>0)
				                 length++;
				             else
				                 throw new SQLException();
			             }
			             /////////
			            
			             while(rs1.next())
			             {
			                 System.out.println("date from = "+rs1.getString("date_fr"));
			                 System.out.println("days from = "+rs1.getInt("days_fr"));
			                 System.out.println("days to = "+rs1.getInt("days_to"));
			                 
			                 System.out.println("trn date = "+array_lockerDetailsObject[i].getTrnDate());
			                 
                             //ship......12/05/2007
			                 if(rs1.getString("date_fr").equals(array_lockerDetailsObject[i].getTrnDate()) && rs1.getInt("days_fr")==array_lockerDetailsObject[i].getDaysFrom() && rs1.getInt("days_to")==array_lockerDetailsObject[i].getDaysTo())                             
			                 {
			                     System.out.println("date from .. days == .. == i = "+i);
			                     
			                     /*if(stmt_same_record.executeUpdate("delete from LockerRate where date_to='31/12/9999' and locker_type='"+array_lockerDetailsObject[i].getLockerType()+"' and date_fr='"+array_lockerDetailsObject[i].getDateFrom()+"' and days_fr="+array_lockerDetailsObject[i].getDaysFrom()+" and days_to="+array_lockerDetailsObject[i].getDaysTo()+"")==0)
			                         throw new SQLException();*/
                                 
                                 if(stmt_same_record.executeUpdate("update LockerRate set date_to='"+array_lockerDetailsObject[i].getTrnDate()+"' where date_to='31/12/9999' and locker_type='"+array_lockerDetailsObject[i].getLockerType()+"' and date_fr='"+array_lockerDetailsObject[i].getDateFrom()+"' and days_fr="+array_lockerDetailsObject[i].getDaysFrom()+" and days_to="+array_lockerDetailsObject[i].getDaysTo()+"")==0)
                                     throw new SQLException();
			                 }
                             //ship.......15/05/2007
                             else if(rs1.getString("date_fr").equals(array_lockerDetailsObject[i].getTrnDate()) && (rs1.getInt("days_fr") < array_lockerDetailsObject[i].getDaysTo()) && (rs1.getInt("days_to") > array_lockerDetailsObject[i].getDaysFrom()))
                             {
                                 System.out.println("date from .. days != .. == i = "+i);
                                 
                                 if(stmt_update_record.executeUpdate("update LockerRate set date_to='"+array_lockerDetailsObject[i].getTrnDate()+"' where locker_type='"+rs1.getString("locker_type")+"' and date_to='31/12/9999' and days_fr="+rs1.getInt("days_fr")+" and days_to="+rs1.getInt("days_to")+"")==0)
                                     throw new SQLException();
                             }
                             //////////
			                 else if(!rs1.getString("date_fr").equals(array_lockerDetailsObject[i].getTrnDate()) && (rs1.getInt("days_fr") < array_lockerDetailsObject[i].getDaysTo()) && (rs1.getInt("days_to") > array_lockerDetailsObject[i].getDaysFrom()))
			                 {
			                     System.out.println("date from != i = "+i);
			                     
			                     String string_date_to=commonremote_local.getFutureDayDate(array_lockerDetailsObject[i].getDateFrom(),-1);
			                     
			                     if(stmt_update_record.executeUpdate("update LockerRate set date_to='"+string_date_to+"' where locker_type='"+rs1.getString("locker_type")+"' and date_to='31/12/9999' and days_fr="+rs1.getInt("days_fr")+" and days_to="+rs1.getInt("days_to")+"")==0)
			                         throw new SQLException();
			                 }
			                 
                             rs_check = stmt_ckeck.executeQuery("select * from LockerRate where locker_type='"+array_lockerDetailsObject[i].getLockerType()+"' and date_fr='"+array_lockerDetailsObject[i].getDateFrom()+"' and date_to='"+array_lockerDetailsObject[i].getDateTo()+"' and days_fr="+array_lockerDetailsObject[i].getDaysFrom()+" and days_to="+array_lockerDetailsObject[i].getDaysTo()+"");
                             
                             if(!rs_check.next())
                             {
                                 pstat=conn.prepareStatement("insert into LockerRate(locker_type,date_fr,date_to,days_fr,days_to,locker_rate,de_user,de_tml,de_date,security_deposit) values(?,?,?,?,?,?,?,?,?,?)");
                                
                                 System.out.println(".........before executing.......length"+array_lockerDetailsObject.length);
                                 System.out.println("before inserting lk type "+array_lockerDetailsObject[i].getLockerType());
                                 
                                 pstat.setString(1,array_lockerDetailsObject[i].getLockerType());
                                 pstat.setString(2,array_lockerDetailsObject[i].getDateFrom());
                                 pstat.setString(3,array_lockerDetailsObject[i].getDateTo());
                                 pstat.setInt(4,array_lockerDetailsObject[i].getDaysFrom());
                                 pstat.setInt(5,array_lockerDetailsObject[i].getDaysTo());
                                 pstat.setDouble(6,array_lockerDetailsObject[i].getLockerRate());
                                 pstat.setString(7,array_lockerDetailsObject[i].uv.getUserId());
                                 pstat.setString(8,array_lockerDetailsObject[i].uv.getUserTml());
                                 pstat.setString(9,array_lockerDetailsObject[i].uv.getUserDate());
                                 pstat.setDouble(10,array_lockerDetailsObject[i].getSecurityDeposit());
                                    
                                 if(pstat.executeUpdate()>0)
                                     length++;
                                 else
                                     throw new SQLException();
                             }
			             }    
				    
			             //ship......11/09/2006
			             /*pstat=conn.prepareStatement("insert into LockerRate(locker_type,date_fr,date_to,days_fr,days_to,locker_rate,de_user,de_tml,de_date,security_deposit) values(?,?,?,?,?,?,?,?,?,?)");
				        
			             System.out.println(".........before executing.......length"+array_lockerDetailsObject.length);
			             System.out.println("before inserting lk type "+array_lockerDetailsObject[i].getLockerType());
			             
			             String locker_type=array_lockerDetailsObject[i].getLockerType();
			            
			             pstat.setString(1,locker_type.trim());
			             System.out.println("lk in string "+locker_type);
			             pstat.setString(2,array_lockerDetailsObject[i].getDateFrom());
			             pstat.setString(3,array_lockerDetailsObject[i].getDateTo());
			             pstat.setInt(4,array_lockerDetailsObject[i].getDaysFrom());
			             pstat.setInt(5,array_lockerDetailsObject[i].getDaysTo());
			             pstat.setDouble(6,array_lockerDetailsObject[i].getLockerRate());
			             pstat.setString(7,array_lockerDetailsObject[i].uv.getUserId());
			             pstat.setString(8,array_lockerDetailsObject[i].uv.getUserTml());
			             pstat.setString(9,array_lockerDetailsObject[i].uv.getUserDate());
			             pstat.setDouble(10,array_lockerDetailsObject[i].getSecurityDeposit());
			                
			             pstat.addBatch();*/
			         }		        
			        
			         //int[] length = pstat.executeBatch();
			        
			         System.out.println("insert == length is "+length);
			         System.out.println("insert == arr length "+array_lockerDetailsObject.length);
		         }                 
		         else if(type==1)//update Locker Rate
		         {
		             for(int i=0;i<array_lockerDetailsObject.length;i++)
			         {
		                 //ship.....09/09/2006		                 
		                 rs1 = stmt1.executeQuery("select * from LockerRate where locker_type='"+array_lockerDetailsObject[i].getLockerType()+"' and date_fr='"+array_lockerDetailsObject[i].getDateFrom()+"' and days_fr="+array_lockerDetailsObject[i].getDaysFrom()+" and days_to="+array_lockerDetailsObject[i].getDaysTo()+" and locker_rate="+array_lockerDetailsObject[i].getLockerRate()+" and security_deposit="+array_lockerDetailsObject[i].getSecurityDeposit()+"");
		                 
		                 if(rs1.next())
		                 {
		                     System.out.println("inside update Locker Rate....rs1");
		                     
		                     if(!rs1.getString("date_to").equals(array_lockerDetailsObject[i].getDateTo()))
		                     {
		                         System.out.println("inside date to != ");
		                         
		                         pstat = conn.prepareStatement("update LockerRate set date_to=?,de_user=?,de_tml=?,de_date=? where locker_type='"+array_lockerDetailsObject[i].getLockerType()+"' and date_fr='"+array_lockerDetailsObject[i].getDateFrom()+"' and days_fr="+array_lockerDetailsObject[i].getDaysFrom()+" and days_to="+array_lockerDetailsObject[i].getDaysTo()+" and locker_rate="+array_lockerDetailsObject[i].getLockerRate()+" and security_deposit="+array_lockerDetailsObject[i].getSecurityDeposit()+"");
		 					    		 			             
		 			             pstat.setString(1,array_lockerDetailsObject[i].getDateTo());
		 			             pstat.setString(2,array_lockerDetailsObject[i].uv.getUserId());
		 			             pstat.setString(3,array_lockerDetailsObject[i].uv.getUserTml());
		 			             pstat.setString(4,array_lockerDetailsObject[i].uv.getUserDate());
		 			                
		 			             if(pstat.executeUpdate()==1)
		 			                 length++;
		 			             else
		 			                 throw new SQLException();
		                     }
		                 }
		                 //////////
			         }
				        
				     System.out.println("length is "+length);
				     System.out.println("arr length "+array_lockerDetailsObject.length);
		         }
		     }
        }
        catch(SQLException sqlexception)
        {
            sqlexception.printStackTrace();
            obj_sessionContext.setRollbackOnly();           
        }        
        finally
        {
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        return true; // if true means locker type in added in the locker type table
    }
    
    /**
     * Insertion of Locker Type Parameters.
     */
    public boolean addLockerTypeParameter(LockerDetailsObject lockerDetailsObject) 
    {
        PreparedStatement ps=null;
        Connection conn=null;
        
        try
        {
            conn=getConnection();
            if(conn==null)
                throw new SQLException();
            
            
            ps=conn.prepareStatement("insert into LockerType(locker_type,descrptn,height,length,depth,de_user,de_tml,de_date) values(?,?,?,?,?,?,?,?)");
            System.out.println("'m i here inside add locker ln 3236");
            System.out.println(lockerDetailsObject.getLockerHeight());
            System.out.println("^^^");
            
            ps.setString(1,lockerDetailsObject.getLockerType());
            ps.setString(2,lockerDetailsObject.getDescription());            
            ps.setString(3,lockerDetailsObject.getLockerHeight());
            ps.setString(4,lockerDetailsObject.getLockerLength());
            ps.setString(5,lockerDetailsObject.getLockerDepth());
            ps.setString(6,lockerDetailsObject.uv.getUserId());
            ps.setString(7,lockerDetailsObject.uv.getUserTml());
            ps.setString(8,lockerDetailsObject.uv.getUserDate());
            
            if(ps.executeUpdate()==0)
                return false;
        }
        catch(SQLException sqlexception)
        {
            sqlexception.printStackTrace();
            obj_sessionContext.setRollbackOnly();           
        } 
        finally
        {
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        return true; // if true means locker type in added in the locker type table
    }
    
    /**
     * Deletion of Locker Type Parameters.
     */
    public int deleteLockerTypeParameter(String string_locker_type)
    {
        Connection conn=null;
        Statement stmt=null;
        
        ResultSet rs = null;
        
        try
        {         
            conn=getConnection();
            
            if(conn==null)
                throw new SQLException();
     
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs = stmt.executeQuery("select * from LockerMaster where locker_ty='"+string_locker_type+"'");
            
            if(rs.next())
            {
                return 2;
            }
            
            rs.close();
            
            rs = stmt.executeQuery("select * from LockerMasterLog where locker_ty='"+string_locker_type+"'");
            
            if(rs.next())
            {
                return 2;
            }
            
            rs.close();
            
            if(stmt.executeUpdate("delete from LockerType where locker_type='"+string_locker_type+"'")==0)
                throw new SQLException();
        }
        catch(SQLException sqlexception)
        {
            sqlexception.printStackTrace();
            obj_sessionContext.setRollbackOnly();
            return 3;
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
       
        return 1;// if true means locker type is deleted.
    }
    
    //ship....01/11/2006
    /*public boolean checkLockerType(String string_locker_type) 
    {
        Connection  conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        
        try
        {
            conn=getConnection();
            
            if(conn==null)
                throw new SQLException();
        
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs=stmt.executeQuery("select locker_ty from LockerMaster where locker_ty='"+string_locker_type+"' limit 1");
            
            if(rs.next())
                return true;
            
                
        }
        catch(SQLException sqlexception)
        {
            sqlexception.printStackTrace();
            obj_sessionContext.setRollbackOnly();
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
       return false;// if false means particular locker type never used.
    }
    //end 
*/    
    
    /**
     * Gives the details of all the Locker Types.
     */
	public LockerDetailsObject[] getLockerTypes() 
	{
		Connection  conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		LockerDetailsObject loc[]=null;
		
		try
		{
			conn=getConnection();
			
			if(conn==null)
				throw new SQLException();
		
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("select lt.*,lr.locker_rate,lr.security_deposit from LockerType lt left join LockerRate lr on lt.locker_type=lr.locker_type order by locker_type,concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
			
			if(rs.next())
			{
				rs.last();
				loc=new LockerDetailsObject[rs.getRow()];
			}
			rs.beforeFirst();
			int i=0;
			
			System.out.println("length"+loc.length);
			
			while(rs.next())
			{
				loc[i]=new LockerDetailsObject();
				
				loc[i].setLockerType(rs.getString("locker_type"));
				loc[i].setLockerLength(rs.getString("length"));
				loc[i].setLockerHeight(rs.getString("height"));
				loc[i].setLockerDepth(rs.getString("depth"));
				loc[i].setDescription(rs.getString("descrptn"));
				loc[i].setLockerRate(rs.getDouble("locker_rate"));
				loc[i].setSecurityDeposit(rs.getDouble("security_deposit"));
				loc[i].uv.setUserId(rs.getString("de_tml"));
				loc[i].uv.setUserTml(rs.getString("de_user"));
                
				i++;
			}
		}
		catch(SQLException sqlexception)
		{
		    sqlexception.printStackTrace();
		    obj_sessionContext.setRollbackOnly();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
			    e.printStackTrace();
			}
		}
		
		return loc;
	}
	
	/**
	 * Gives the details of all the Locker Types.
	 */
	public String[] getLockersTypes() throws RemoteException
    {
        System.out.println("...........inside getLockersTypes().............");
        String[] array_locker_types=null;
        
        Connection  conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        
        try
        {
            conn=getConnection();
            
            if(conn==null)
                throw new SQLException();
     
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs=stmt.executeQuery("select distinct locker_type from LockerType order by locker_type desc");//Locker Nos
            rs.last();
            if(rs.getRow()!=0)
                array_locker_types=new String[rs.getRow()];
            
            System.out.println("length = "+rs.getRow());
            
            rs.beforeFirst();
            int count=0;
            while(rs.next())
            {
                array_locker_types[count]=rs.getString("locker_type");
                System.out.println(" lk type "+array_locker_types[count]);
                count++;
            }
       
        }
        catch(SQLException sqlexception)
        {
            sqlexception.printStackTrace();
            obj_sessionContext.setRollbackOnly();
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        return array_locker_types;
    }
	
	/**
	 * Cash - General Receipt - to get Locker Description of all the Lockers.
	 */
	//ship.....29/12/2005
	public String[] getLockersDesc() throws RemoteException
    {
		
		        System.out.println("...........inside getLockersDesc().............");
		        String[] array_locker_types=null;
		        
		        Connection  conn=null;
		        Statement stmt=null;
		        ResultSet rs=null;
		        
		        try
		        {
		            conn=getConnection();
		            
		            if(conn==null)
		                throw new SQLException();
		     
		            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		            rs=stmt.executeQuery("select distinct descrptn from LockerType order by locker_type desc");//Locker Desc
		            rs.last();
		            if(rs.getRow()!=0)
		                array_locker_types=new String[rs.getRow()];
		            
		            System.out.println("length"+rs.getRow());
		            
		            rs.beforeFirst();
		            int count=0;
		            while(rs.next())
		            {
		                array_locker_types[count]=rs.getString("descrptn");
		                System.out.println(" lk desc "+array_locker_types[count]);
		                count++;
		            }
		        }
		        catch(SQLException sqlexception)
		        {
		            sqlexception.printStackTrace();
		            obj_sessionContext.setRollbackOnly();
		        }
		        finally
		        {
		            try
		            {
		                conn.close();
		            }
		            catch(SQLException e)
		            {
		                e.printStackTrace();
		            }
		        }
		        
		        return array_locker_types;
    }
	//
	
	/**
	 * Gives the cabin structure for the given locker type. 
	 */
	public LockerDetailsObject[] getCabinStructure(String string_locker_type) 
	{
		Connection  conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		LockerDetailsObject loc[]=null;
		String cabno="";
		
		try
		{
			conn=getConnection();
			
			if(conn==null)
				throw new SQLException();
			
			System.out.println("locker type = "+string_locker_type);
			
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			rs=stmt.executeQuery("select distinct cab_no from Lockers where locker_ty='"+string_locker_type+"' order by cab_no");//Locker Nos
			
			int g = 0;
			
			if(rs.last())
			{
			    g=rs.getRow();
				rs.beforeFirst(); 
			}
			
			while(rs.next())
			{
			    System.out.println("inside while....");
			    
				if(g==rs.getRow())	
					cabno=cabno+String.valueOf(rs.getInt(1));
				else
					cabno=cabno+String.valueOf(rs.getInt(1))+",";
			}
			
			System.out.println("cabno = "+cabno);
			
			rs.close();
			//rs=stmt.executeQuery("select cs.*,cm.master_key from CabStructure cs,CabMaster cm where cs.cab_no in("+cabno+") and cm.cab_no=cs.cab_no order by cs.cab_no,cs.row_no");//Locker Nos
			
            if(!cabno.equals(""))
            {
                rs=stmt.executeQuery("select cs.*,cm.master_key from CabStructure cs,CabMaster cm where cs.cab_no in ("+cabno+") and cm.cab_no=cs.cab_no order by cs.cab_no,cs.row_no");//Locker Nos
			
            /**
             * Here instead of cs.cab_no in ("+cabno+") cs.cab_no=cab_no needed check once in where condition
             */
            
                if(rs.last())
                {
                    loc=new LockerDetailsObject[rs.getRow()];
                    rs.beforeFirst();  
                }
			
                int i=0;
                
                //System.out.println("length = "+loc.length);
                
                while(rs.next())
    			{
    				loc[i]=new LockerDetailsObject();
    				loc[i].setCabNo(rs.getInt("cab_no"));
    				loc[i].setMasterKey(rs.getString("cm.master_key"));
    				loc[i].setRowNo(rs.getInt("row_no"));
    				loc[i].setCols(rs.getInt("no_of_cols"));
    				i++;
    			}
             }
		}
		catch(SQLException sqlexception)
		{
		    sqlexception.printStackTrace();
		    obj_sessionContext.setRollbackOnly();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
			    e.printStackTrace();
			}
		}
		
		return loc;
	}
	
	/**
	 * Inserts the details of the Lockers into CabMaster, CabStructure, Lockers.
	 */
	public int storeLockers(LockerDetailsObject lockedetailsobject_cabstructure[],LockerDetailsObject lockerDetailsObject_lockers[]) 
	{
		Connection  conn=null;
		Statement stmt=null;
		PreparedStatement pstmt=null;
		int cabno=0;
		ResultSet rs=null;
		int lockerno=0;
		int a[][]=null;
		
		try
		{
			conn=getConnection();
			
			if(conn==null)
				throw new SQLException();
		
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
			rs=stmt.executeQuery("select max(cab_no) from CabMaster");	
            
			if(rs.next())
				cabno=rs.getInt(1)+1;
            
			if(lockedetailsobject_cabstructure!=null && lockerDetailsObject_lockers!=null)
			{
				System.out.println("1........");
				System.out.println("loc1="+lockedetailsobject_cabstructure.length);
				System.out.println("loc2="+lockerDetailsObject_lockers.length);
                
                System.out.println("before if........");
                System.out.println(lockedetailsobject_cabstructure[0]);
				
                if(lockedetailsobject_cabstructure[0]!=null)
				{
					System.out.println("cab no"+cabno);
                    
                    System.out.println("before insert");
                    
                    if(stmt.executeUpdate("insert into CabMaster values("+cabno+",'"+lockedetailsobject_cabstructure[0].getDescription()+"','"+lockedetailsobject_cabstructure[0].getMasterKey()+"',"+lockerDetailsObject_lockers.length+",'"+lockedetailsobject_cabstructure[0].uv.getUserId()+"','"+lockedetailsobject_cabstructure[0].uv.getUserTml()+"','"+lockedetailsobject_cabstructure[0].uv.getUserDate()+"')")==1)
					{
						System.out.println("2........");
						
						pstmt=conn.prepareStatement("insert into CabStructure values(?,?,?)");
						
						System.out.println("2........"+lockedetailsobject_cabstructure.length);
						
                        for(int i=0;i<lockedetailsobject_cabstructure.length;i++)	
						{
							if(lockedetailsobject_cabstructure[i]!=null)
							{
								System.out.println("inside i........");				
								pstmt.setInt(1,cabno);
								pstmt.setInt(2,lockedetailsobject_cabstructure[i].getRowNo());
								pstmt.setInt(3,lockedetailsobject_cabstructure[i].getCols());
								
								pstmt.addBatch();
								
							}
							else
							{
								if(stmt.executeUpdate("delete from CabMaster where cab_no="+cabno+"")==0)
									throw new SQLException();
								if(stmt.executeUpdate("delete from CabStructure where cab_no="+cabno+"")==0)
									throw new SQLException();
								
								return -1;
							}
						}
                    //    System.out.println("executeBatch----"+pstmt.executeBatch().length);
						if(pstmt.executeBatch().length==lockedetailsobject_cabstructure.length)
						{
							System.out.println("inside locker");
							rs=stmt.executeQuery("select lst_acc_no from Modules where modulecode='1009000'");
                            
							if(rs.next())
								lockerno=rs.getInt(1);
							
							a=new int[lockedetailsobject_cabstructure.length][];
							//int ind[]=new int[loc1.length];
							for(int i=0;i<lockedetailsobject_cabstructure.length;i++)
								a[i]=new int[lockedetailsobject_cabstructure[i].getCols()];
							
							/*				for(int i=0;i<loc1.length;i++)
							 {
							 for(int j=0;j<loc1.length;j++)
							 {
							 if(loc1[j].getCols()>i)
							 a[j][i]=++lockerno;
							 
							 }
							 
							 }*/
							
							int large_col_no=lockedetailsobject_cabstructure[0].getCols();
							//	for(int i=0;i<loc1.length;i++)
							//	{	
							for(int j=1;j<lockedetailsobject_cabstructure.length;j++)
							{
								if(lockedetailsobject_cabstructure[j].getCols()>=large_col_no)
									large_col_no=lockedetailsobject_cabstructure[j].getCols();
							}
							
							//	}
							System.out.println("col="+large_col_no);
							
							for(int i=0;i<large_col_no;i++)
							{
								for(int j=0;j<lockedetailsobject_cabstructure.length;j++)
								{
									if(i<a[j].length)
										a[j][i]=++lockerno;
									
								}
							}	
							System.out.println("3........"+lockerDetailsObject_lockers.length);
							
							pstmt=conn.prepareStatement("insert into Lockers values(?,?,?,?,?,?,'F','F',?,?,?,?)");
							
							int rowno=0;
							int colno=0;
							
							for(int i=0;i<lockerDetailsObject_lockers.length;i++)	
							{
								if(lockerDetailsObject_lockers[i]!=null)	
								{									
									pstmt.setInt(1,cabno);
									System.out.println("3..1.....");
									pstmt.setInt(2,lockerDetailsObject_lockers[i].getRowNum());
									pstmt.setInt(3,lockerDetailsObject_lockers[i].getColNo());
									//pstmt.setInt(4,++lockerno);
									System.out.println("colno is================>"+colno);
									System.out.println("lockedetailsobject_cabstructure[rowno].getCols()"+lockedetailsobject_cabstructure[rowno].getCols());
									if((colno)!=lockedetailsobject_cabstructure[rowno].getCols())
									{
										System.out.println("bala row no--"+rowno+"col no--"+colno+"---"+a[rowno][colno]); 
										pstmt.setInt(4,a[rowno][colno]);
										colno++;
									}
									else if((colno)==lockedetailsobject_cabstructure[rowno].getCols())
									{
										rowno++;
										colno=0;
										System.out.println(" "+rowno+" "+colno+" "+a[rowno][colno]); 
										pstmt.setInt(4,a[rowno][colno]);
										colno++;
									}
									
									if(lockerDetailsObject_lockers[i].getLockerType().equals("None"))
									{
										System.out.println("inside loc2,None....");
										String type=null;
										rs=stmt.executeQuery("select max(locker_ty) from LockerType");
										if(rs.next())
											type=rs.getString(1);
										char c;
										for(c='A';c<='Z';c++)
										{
											if(type.equals(String.valueOf(c)))
											{
												c++;
												System.out.println("c="+c);
												type=String.valueOf(c);
												System.out.println("c="+c);
												break;
												
											}
										}
											
                                        if(stmt.executeUpdate("insert into LockerType values('"+type+"','"+lockerDetailsObject_lockers[i].getDescription()+"',"+lockerDetailsObject_lockers[i].getLockerHeight()+","+lockerDetailsObject_lockers[i].getLockerLength()+","+lockerDetailsObject_lockers[i].getLockerDepth()+",'"+lockerDetailsObject_lockers[i].uv.getUserId()+"','"+lockerDetailsObject_lockers[i].uv.getUserTml()+"','"+lockerDetailsObject_lockers[i].uv.getUserDate()+"')")==0)
											throw new SQLException();
										
										pstmt.setString(5,type);
									}
									else
										pstmt.setString(5,lockerDetailsObject_lockers[i].getLockerType());
									
									pstmt.setInt(6,lockerDetailsObject_lockers[i].getKeyNo());
									pstmt.setString(7,lockerDetailsObject_lockers[i].getDescription());
									pstmt.setString(8,lockerDetailsObject_lockers[i].uv.getUserId());
									pstmt.setString(9,lockerDetailsObject_lockers[i].uv.getUserTml());
									pstmt.setString(10,lockerDetailsObject_lockers[i].uv.getUserDate());
									
									pstmt.addBatch();
							    }
								else
								{
									if(stmt.executeUpdate("delete from CabMaster where cab_no="+cabno+"")==0)
										throw new SQLException();
									if(stmt.executeUpdate("delete from CabStructure where cab_no="+cabno+"")==0)
										throw new SQLException();
									if(stmt.executeUpdate("delete from Lockers where cab_no="+cabno+"")==0)
										throw new SQLException();
									
									System.out.println("inside 1-1");
                                    System.out.print("......Control is here.....");
									return -1;
								}
							}
    							/*System.out.println("..1.."+lockerDetailsObject_lockers.length);
                                System.out.println("..2.."+pstmt.executeBatch().length);
                                */
    							if((pstmt.executeBatch()).length==lockerDetailsObject_lockers.length)
    							{
    								System.out.println("...........before update...........");
                                    
                                    if(stmt.executeUpdate("update Modules set lst_acc_no="+lockerno+" where modulecode='1009000'")==0)
                                        throw new SQLException();
                                    
    								return cabno;
    							}
    							if(stmt.executeUpdate("delete from CabMaster where cab_no="+cabno+"")==0)
    							    throw new SQLException();
    							if(stmt.executeUpdate("delete from CabStructure where cab_no="+cabno+"")==0)
    							    throw new SQLException();
    							if(stmt.executeUpdate("delete from Lockers where cab_no="+cabno+"")==0)
    							    throw new SQLException();
    							System.out.println("inside 1-1");
    							return -1;
    							
    						}
    						if(stmt.executeUpdate("delete from CabMaster where cab_no="+cabno+"")==0)
    						    throw new SQLException();
    						if(stmt.executeUpdate("delete from CabStructure where cab_no="+cabno+"")==0)
    						    throw new SQLException();
    						System.out.println("inside 2-1");
    						return -1;    						
    					}
    				}
    				else
    					return -1;
    			}
		    }
			catch(SQLException sqlexception)
		    {
    			sqlexception.printStackTrace();
    			obj_sessionContext.setRollbackOnly();			
    		}
    		finally
    		{
    		    try
    		    {
    		         conn.close();
    		    }
    		    catch(SQLException e)
    		    {
    		        e.printStackTrace();
    		    }
    		}
    		
    		return -1;
	}
	
	/**
	 * Gives the details of the given locker type from Lockers table.
	 */
	public LockerDetailsObject[] getLockers(String string_locker_type) 
	{
		ResultSet rs=null;
		LockerDetailsObject loc[]=null;
		
		Connection  conn=null;
		Statement stmt=null;
		
		try
		{
			conn=getConnection();
		
			if(conn==null)
				throw new SQLException();
		
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		
			rs=stmt.executeQuery("select * from Lockers where locker_ty='"+string_locker_type+"' order by cab_no,row_no,col_no");
			rs.last();
			loc=new LockerDetailsObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			
			while(rs.next())
			{
				loc[i]=new LockerDetailsObject();
				loc[i].setLockerNo(rs.getInt("locker_no"));
				loc[i].setKeyNo(rs.getInt("key_no"));
				loc[i].setLockerType(rs.getString("locker_ty"));
				loc[i].setLockerStatus(rs.getString("locker_st"));
				loc[i].setDescription(rs.getString("descptn"));
				loc[i].setRowNo(rs.getInt("row_no"));
				loc[i].setColNo(rs.getInt("col_no"));
				loc[i].setCabNo(rs.getInt("cab_no"));
				i++;
			}
		}
		catch(SQLException sqlexception)
		{
		    sqlexception.printStackTrace();
		    obj_sessionContext.setRollbackOnly();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
			    e.printStackTrace();
			}
		}
		
		return loc;
	}
	
	/**
	 * Gives the details of the lockers present in the given cabinet no.
	 */
	public LockerDetailsObject[] getLockers(int int_cab_no) 
	{
		Connection  conn=null;
        
		Statement stmt=null,stat=null,stamt=null;
		
		ResultSet rs = null,rs1 = null,rs2 = null;
		
		LockerDetailsObject loc[]=null;
		
		try
		{
			conn=getConnection();
			
			if(conn==null)
				throw new SQLException();
		
			stat=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stamt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
            //ship......26/04/2007
			/*rs=stmt.executeQuery("select * from Lockers lk left join LockerMaster lm on lk.locker_no=lm.locker_no where cab_no="+int_cab_no+"");//Locker Nos
			rs.last();
			loc=new LockerDetailsObject[rs.getRow()];
			rs.beforeFirst();
			
			int i=0;
			System.out.println("length"+loc.length);
		
			while(rs.next())
			{
				loc[i]=new LockerDetailsObject();
				loc[i].setLockerNo(rs.getInt("locker_no"));
				loc[i].setLockerAcNo(rs.getInt("ac_no"));
				
				rs1=stat.executeQuery("select * from Modules where modulecode="+rs.getString("ac_type")+"");				
				if(rs1.next())
					loc[i].setLockerAcType(rs1.getString("moduleabbr"));
				loc[i].setKeyNo(rs.getInt("key_no"));
				loc[i].setRowNum(rs.getInt("row_no"));
				loc[i].setColNo(rs.getInt("col_no"));
				loc[i].setLockerType(rs.getString("locker_ty"));
				loc[i].setLockerStatus(rs.getString("locker_st"));
				loc[i].setDescription(rs.getString("descptn"));
				loc[i].setSiezeInd(rs.getString("sieze_ind"));
				loc[i].uv.setUserId(rs.getString("de_tml"));
				loc[i].uv.setUserTml(rs.getString("de_user"));
				i++;
			}*/
            
            rs = stmt.executeQuery("select * from Lockers where cab_no="+int_cab_no+" order by row_no ");//Locker Nos
            
            if(rs.last())
            {
                loc=new LockerDetailsObject[rs.getRow()];
                rs.beforeFirst();
            }
            
            int i=0;
            
            System.out.println("length == "+loc.length);
        
            while(rs.next())
            {
                loc[i]=new LockerDetailsObject();
                
                loc[i].setLockerNo(rs.getInt("locker_no"));
                loc[i].setKeyNo(rs.getInt("key_no"));
                loc[i].setRowNum(rs.getInt("row_no"));
                loc[i].setColNo(rs.getInt("col_no"));
                loc[i].setLockerType(rs.getString("locker_ty"));
                loc[i].setLockerStatus(rs.getString("locker_st"));
                loc[i].setDescription(rs.getString("descptn"));
                loc[i].setSiezeInd(rs.getString("sieze_ind"));
                
                loc[i].uv.setUserId(rs.getString("de_tml"));
                loc[i].uv.setUserTml(rs.getString("de_user"));
                
                if(rs.getString("locker_st").equals("T"))
                {
                    rs2 = stamt.executeQuery("select ac_type,ac_no from LockerMaster where locker_ty='"+rs.getString("locker_ty")+"' and locker_no='"+rs.getString("locker_no")+"' and close_dt is null");
                    
                    if(rs2.next())
                    {
                        loc[i].setLockerAcNo(rs2.getInt("ac_no"));
                        
                        rs1 = stat.executeQuery("select * from Modules where modulecode='"+rs2.getString("ac_type")+"'");
                        
                        if(rs1.next())
                        {
                            loc[i].setLockerAcType(rs1.getString("moduleabbr"));
                        }
                    }
                    else
                    {
                        loc[i].setLockerAcNo(0);
                        
                        loc[i].setLockerAcType("");
                    }
                }
                else
                {
                    loc[i].setLockerAcNo(0);
                    
                    loc[i].setLockerAcType("");
                } 
                
                i++;
            }
		}
		catch(SQLException sqlexception)
		{
		    sqlexception.printStackTrace();
		    obj_sessionContext.setRollbackOnly();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
			    e.printStackTrace();
			}
		}
		
		return loc;
	}
	
	//ship......01/11/2006
	/*public LockerMasterObject[] getRentDueAccountHolders(int int_type,String date)
    {
        LockerMasterObject[] array_LockerMasterObject=null;
        Connection  conn=null;
        Statement stmt=null;
        ResultSet rs=null;
    
        try
        {
            conn=getConnection();
            
            if(conn==null)
                throw new SQLException();
    
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if(int_type==2)
            {
                
                System.out.println(".............2..........");
                *//**
                 * I have removed rent_by!='C' in where condition.04/08/2005 
                 *//*
                rs=stmt.executeQuery("select lk.*,sub_category from LockerMaster lk,CustomerMaster cm where lk.ve_user is not null and lk.close_dt is null and concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1))<'"+Validations.convertYMD(date)+"' and concat(right(rent_upto,4),'-',mid(rent_upto,locate('/',rent_upto)+1,(locate('/',rent_upto,4)-locate('/',rent_upto)-1)),'-',left(rent_upto,locate('/',rent_upto)-1))<'"+Validations.convertYMD(date)+"' and cm.cid=lk.cid");
                rs.last();
                
                if(rs.getRow()==0)
                    return array_LockerMasterObject;
                array_LockerMasterObject=new LockerMasterObject[rs.getRow()];
                    
                System.out.println(".............3.........."+rs.getRow());
                
                if(rs.last())
                    rs.beforeFirst();
                else
                    return array_LockerMasterObject;
            }
            
            System.out.println("..........inside while......");
            
            if(int_type==2)         
            {
                
                for(int i=0;i<array_LockerMasterObject.length;i++)
                {
                    rs.next();
                    
                    array_LockerMasterObject[i]=new LockerMasterObject();
                    array_LockerMasterObject[i].setLockerAcNo(rs.getInt("lk.ac_no"));
                    array_LockerMasterObject[i].setLockerAcType(rs.getString("lk.ac_type"));
                    array_LockerMasterObject[i].setLockerNo(rs.getInt("lk.locker_no"));
                    array_LockerMasterObject[i].setLockerType(rs.getString("lk.locker_ty"));
                    array_LockerMasterObject[i].setRentUpto(rs.getString("lk.rent_upto"));
                    array_LockerMasterObject[i].setRentBy(rs.getString("lk.rent_by"));
                    array_LockerMasterObject[i].setAccSubCategory(rs.getInt("sub_category"));
                    array_LockerMasterObject[i].setTransAcNo(rs.getInt("lk.trf_acno"));
                    array_LockerMasterObject[i].setTransAcType(rs.getString("lk.trf_acty"));
                    array_LockerMasterObject[i].uv.setUserId(rs.getString("lk.de_user"));
                    array_LockerMasterObject[i].uv.setUserTml(rs.getString("lk.de_tml"));
                    
                    System.out.println(i+" "+rs.getInt("lk.ac_no"));
                }
            }
        }
        catch(SQLException sqlexception)
        {
            sqlexception.printStackTrace();
            obj_sessionContext.setRollbackOnly();
        }      
        finally
        {
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        return array_LockerMasterObject;
    }*/
	
	//ship........04/07/2006
     /*public int getPayLockerRent(LockerMasterObject lockerMasterObject,int int_type) throws InOperativeAccountException
     {
        Connection  conn=null;
        Statement stat=null;
        
        RentTransObject rto=null;
        ResultSet rs1=null;
        String dt=null;
        double cl_bal=0;
        double min_bal=0;
        double rent=0;
        double totrent=0;
        int days=0;
        
        System.out.println("..........inside getPayLockerRent......acno"+lockerMasterObject.getLockerAcNo());
        
        System.out.println("..rent by..."+lockerMasterObject.getRentBy());
        if(lockerMasterObject.getRentBy().equals("C"))
        {
            return 6;
        }
        try
        {
            conn=getConnection();
            
            if(conn==null)
                throw new SQLException();
    
            stat=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                        
             if(int_type==2)            
             {
                    dt=String.valueOf(Validations.noOfMonths(lockerMasterObject.getRentUpto(),lockerMasterObject.getTrnDate()));
                    System.out.println("diff="+dt); 
                    days=Validations.dayCompare(lockerMasterObject.getRentUpto(),lockerMasterObject.getTrnDate());
                    rs1=stat.executeQuery("select locker_rate from LockerRate where locker_type='"+lockerMasterObject.getLockerType()+"' and '"+Validations.convertYMD(lockerMasterObject.getTrnDate())+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)) and "+days+" between days_fr and days_to");
                    
                 if(rs1.next())
                        rent=rs1.getDouble(1);
                    
                 rs1.close();
                    rs1=stat.executeQuery("select extra_locker_rate from LockerCatRate where locker_type='"+lockerMasterObject.getLockerAcType()+"' and '"+Validations.convertYMD(lockerMasterObject.getTrnDate())+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)) and category='"+lockerMasterObject.getAccSubCategory()+"' and "+days+" between days_fr and days_to");
                    
                 if(rs1.next())
                        rent=rent-rs1.getDouble(1);
                    rs1.close();
                    
                }
                
                rs1=stat.executeQuery("select cl_bal,min_bal from AccountTransaction,Modules where ac_no="+lockerMasterObject.getTransAcNo()+" and ac_type='"+lockerMasterObject.getTransAcType()+"' and Modules.modulecode='"+lockerMasterObject.getLockerAcType()+"' order by trn_seq desc limit 1");
                if(rs1.next())
                {
                    cl_bal=rs1.getDouble(1);
                    min_bal=rs1.getDouble(2);
                    //  modulecode=rs1.getInt(3);
                }
             System.out.println("....................trf_acno............"+lockerMasterObject.getTransAcNo());
             System.out.println("...................trf_actype............"+lockerMasterObject.getTransAcType());
             System.out.println("...................module_code............"+lockerMasterObject.getLockerAcType());
             System.out.println("....................cl_bal............"+cl_bal);
             System.out.println("...................min_bal............"+min_bal);
             System.out.println("...................rent............"+rent);
             
             cl_bal=cl_bal-rent;
            
             System.out.println(".......cl_bal after rent..........."+cl_bal);
                
                if(cl_bal>=min_bal && rent!=0)
                {
                    System.out.println("3.......");
                    totrent=totrent+rent;
                    AccountTransObject am=new AccountTransObject();
                
              //      am.setGLRefCode(Integer.parseInt(rs.getString("trf_acty")));
                    am.setAccType(lockerMasterObject.getTransAcType());
                    am.setAccNo(lockerMasterObject.getTransAcNo()); 
                    am.setTransAmount(rent);
                    am.setTransMode("T");
                    am.setTransType("P");
                    am.setTransSource(lockerMasterObject.uv.getUserTml());
                    am.setCdInd("D");
                    am.setChqDDNo(0);
                    am.setChqDDDate(null);
                    am.setTransNarr(lockerMasterObject.getLockerAcType()+" "+lockerMasterObject.getLockerAcNo());
                    am.setRef_No(lockerMasterObject.getLockerAcNo());//doubt
                    am.setPayeeName(null);
                    am.setCloseBal(rent);
                    am.setLedgerPage(0);
                    am.uv.setUserId(lockerMasterObject.uv.getUserId());
                    am.uv.setUserTml(lockerMasterObject.uv.getUserTml());
                    am.uv.setVerTml(lockerMasterObject.uv.getUserTml());
                    am.uv.setVerId(lockerMasterObject.uv.getUserId());
                    
                        
                    
                    if(commonremote_local.storeAccountTransaction(am)==1)
                    {
                    if(stat.executeUpdate("update LockerMaster set rent_coll=rent_coll+"+rent+",rent_upto=date_format(sysdate(),'%d/%m/%Y') where ac_no="+lockerMasterObject.getLockerAcNo()+" and ac_type="+lockerMasterObject.getLockerAcType()+" ")==1)
                    {
                        rto=new RentTransObject();
                        rto.setLockerAcType(lockerMasterObject.getLockerAcType());
                        rto.setLockerAcNo(lockerMasterObject.getLockerAcNo());
                        rto.setTrnSource(lockerMasterObject.uv.getUserTml());
                        rto.setTrnMode("T");
                        rto.setTrfAcNo(lockerMasterObject.getTransAcNo());
                        rto.setTrfAcType(lockerMasterObject.getTransAcType());
                        rto.setTrfVoucherNo(0);
                        rto.setRentAmt(rent);
                        rto.uv.setUserId(lockerMasterObject.uv.getUserId());
                        rto.uv.setUserTml(lockerMasterObject.uv.getUserTml());
                        storeRentTran(rto);
                    }
                 }
                    
                }
                else if(int_type==2 && cl_bal<min_bal)
                {
                    return 7;
                }
                else if(int_type==1||int_type==3)
                {
                    if(cl_bal<=min_bal)  
                        return 2;
                    else if(rent==0)
                        return 5;
                }
                
            
            if(totrent>0)
            {
                int code=0;
                rs1=stat.executeQuery("select gl_code from GLKeyParam where ac_type=1999005 and code=1");
                if(rs1.next())
                    code=rs1.getInt(1);
                rs1.close();
                
                GLTransObject trn_obj=new GLTransObject();
                trn_obj.setTrnDate(lockerMasterObject.getTrnDate());
                trn_obj.setGLType("GL");
                trn_obj.setGLCode(String.valueOf(code));
                trn_obj.setTrnMode("T");
                trn_obj.setAmount(totrent);
                trn_obj.setCdind("C");
                if(int_type==1||int_type==3)
                {
                    trn_obj.setAccType(lockerMasterObject.getLockerAcType());
                    trn_obj.setAccNo(String.valueOf(lockerMasterObject.getLockerAcNo()));
                }
                else
                {
                    trn_obj.setAccType("");
                    trn_obj.setAccNo("");
                }
                trn_obj.setTrnSeq(0);
                trn_obj.setTrnType("");
                trn_obj.setRefNo(0);
                trn_obj.setVtml(lockerMasterObject.uv.getUserTml());
                trn_obj.setVid(lockerMasterObject.uv.getUserId());
                
                *//**
                 * if block should throw SQLException() if 0 else it will return 3
                 *
                if(commonremote_local.storeGLTransaction(trnobj)==0)
                {
                    throw new SQLException();
                    
                }
                else 
                return 3;
            **//*
                return(commonremote_local.storeGLTransaction(trn_obj)==1?0:3);
            }
        
        }catch(SQLException sqlexception){sqlexception.printStackTrace();}
        
        finally{
            try{
                conn.close();
        
            }catch(SQLException e){e.printStackTrace();}
        }
        
            
         return 1;  
     }*/
       
    //ship.........04/07/2006
    /*public int rentCollectByTransfer(LockerMasterObject lockerMasterObject,int int_type) 
	{
		System.out.println("---------------------inside rentCollectByTransfer--------------------");
        
        Connection  conn=null;
		Statement stmt=null,stat=null;
		
		RentTransObject rto=null;
		ResultSet rs=null;
		ResultSet rs1=null;
		String dt=null;
		double cl_bal=0;
		double min_bal=0;
		double rent=0;
		double totrent=0;
		int days=0;
		
		try
		{
			conn=getConnection();
			
			if(conn==null)
				throw new SQLException();
		
			stat=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
            if(int_type==1||int_type==3)
			{
				dt=String.valueOf(Validations.noOfMonths(lockerMasterObject.getRentUpto(),lockerMasterObject.getTrnDate()));
			
                
                if(int_type==1)
					days=Validations.dayCompare(lockerMasterObject.getRentUpto(),lockerMasterObject.getTrnDate());
				else
					days=Validations.dayCompare(lockerMasterObject.getRentUpto(),lockerMasterObject.getMatDate());
				
                
                System.out.println("........days.............."+days);
				System.out.println(".......locker type........"+lockerMasterObject.getLockerType());
				
                rs1=stat.executeQuery("select locker_rate from LockerRate where locker_type='"+lockerMasterObject.getLockerType()+"' and '"+Validations.convertYMD(lockerMasterObject.getTrnDate())+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)) and "+days+" between days_fr and days_to");
				if(rs1.next())
					rent=rs1.getDouble(1);
			//	rs1.close();
				rs=stmt.executeQuery("select lk.*,sub_category from LockerMaster lk,CustomerMaster cm where ac_no="+lockerMasterObject.getLockerAcNo()+" and ac_type='"+lockerMasterObject.getLockerAcType()+"' and cm.cid=lk.cid");
				if(rs.next())
				    rs1=stat.executeQuery("select extra_locker_rate from LockerCatRate where locker_type='"+lockerMasterObject.getLockerType()+"' and '"+Validations.convertYMD(lockerMasterObject.getTrnDate())+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)) and category="+rs.getInt("sub_category")+"  and "+days+" between days_fr and days_to");
				if(rs1.next())
					rent=rent-rs1.getDouble(1);
				rs1.close();
				
				rs.beforeFirst();
				System.out.println("1.......rent="+rent);
			}
			if(int_type==2)
			{
				
                System.out.println(".............2..........");
                rs=stmt.executeQuery("select lk.*,sub_category from LockerMaster lk,CustomerMaster cm where lk.ve_user is not null and lk.close_dt is null and lk.rent_by!='C' and concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1))>='"+Validations.convertYMD(lockerMasterObject.getTrnDate())+"' and concat(right(rent_upto,4),'-',mid(rent_upto,locate('/',rent_upto)+1,(locate('/',rent_upto,4)-locate('/',rent_upto)-1)),'-',left(rent_upto,locate('/',rent_upto)-1))<'"+Validations.convertYMD(lockerMasterObject.getTrnDate())+"' and cm.cid=lk.cid");
                rs.last();
                System.out.println(".............3.........."+rs.getRow());
                
				if(rs.last())
					rs.beforeFirst();
				else
					return 1;
			}
			while(rs.next())
			{
				System.out.println("..........inside while......");
			
                if(int_type==2)			
				{
					dt=String.valueOf(Validations.noOfMonths(rs.getString("rent_upto"),lockerMasterObject.getTrnDate()));
					System.out.println("diff="+dt); 
					days=Validations.dayCompare(rs.getString("rent_upto"),lockerMasterObject.getTrnDate());
					rs1=stat.executeQuery("select locker_rate from LockerRate where locker_type='"+rs.getString("locker_ty")+"' and '"+Validations.convertYMD(lockerMasterObject.getTrnDate())+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)) and "+days+" between days_fr and days_to");
					
                    if(rs1.next())
						rent=rs1.getDouble(1);
					
                    rs1.close();
					rs1=stat.executeQuery("select extra_locker_rate from LockerCatRate where locker_type='"+rs.getString("locker_ty")+"' and '"+Validations.convertYMD(lockerMasterObject.getTrnDate())+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)) and category="+rs.getInt("sub_category")+" and "+days+" between days_fr and days_to");
					
                    if(rs1.next())
						rent=rent-rs1.getDouble(1);
					rs1.close();
					
				}
				
				rs1=stat.executeQuery("select cl_bal,min_bal from AccountTransaction,Modules where ac_no="+rs.getInt("trf_acno")+" and ac_type='"+rs.getString("trf_acty")+"' and Modules.modulecode='"+rs.getString("ac_type")+"' order by trn_seq desc limit 1");
				if(rs1.next())
				{
					cl_bal=rs1.getDouble(1);
					min_bal=rs1.getDouble(2);
					//	modulecode=rs1.getInt(3);
				}
				System.out.println("....................trf_acno............"+rs.getInt("trf_acno"));
                System.out.println("...................trf_actype............"+rs.getString("ac_type"));
                System.out.println("...................module_code............"+rs.getString("ac_type"));
                System.out.println("....................cl_bal............"+cl_bal);
                System.out.println("...................min_bal............"+min_bal);
                
                cl_bal=cl_bal-rent;
				System.out.println("3......."+cl_bal);
				if(cl_bal>=min_bal && rent!=0)
				{
					System.out.println("3.......");
					totrent=totrent+rent;
					AccountTransObject am=new AccountTransObject();
					am.setGLRefCode(Integer.parseInt(rs.getString("trf_acty")));
					am.setAccType(rs.getString("trf_acty"));
					am.setAccNo(rs.getInt("trf_acno"));	
					am.setTransAmount(rent);
					am.setTransMode("T");
					am.setTransType("P");
					am.setTransSource(lockerMasterObject.uv.getUserTml());
					am.setCdInd("D");
					am.setChqDDNo(0);
					am.setChqDDDate(null);
					am.setTransNarr(rs.getString("ac_type")+" "+rs.getInt("ac_no"));
					am.setRef_No(rs.getInt("ac_no"));//doubt
					am.setPayeeName(null);
					am.setCloseBal(rent);
					am.setLedgerPage(0);
					am.uv.setUserId(lockerMasterObject.uv.getUserId());
					am.uv.setUserTml(lockerMasterObject.uv.getUserTml());
					am.uv.setVerTml(lockerMasterObject.uv.getUserTml());
					am.uv.setVerId(lockerMasterObject.uv.getUserId());
					
					if(commonremote_local.storeAccountTransaction(am)==1)
                    {
                    	if(stat.executeUpdate("update LockerMaster set rent_coll=rent_coll+"+rent+",rent_upto=date_format(sysdate(),'%d/%m/%Y') where ac_no="+rs.getInt("ac_no")+" and ac_type='"+rs.getString("ac_type")+"'")==1)
                    	{
                    		rto=new RentTransObject();
                    		rto.setLockerAcType(rs.getString("ac_type"));
                    		rto.setLockerAcNo(rs.getInt("ac_no"));
                    		rto.setTrnSource(lockerMasterObject.uv.getUserTml());
                    		rto.setTrnMode("T");
                    		rto.setTrfAcNo(rs.getInt("trf_acno"));
                    		rto.setTrfAcType(rs.getString("trf_acty"));
                    		rto.setTrfVoucherNo(0);
                    		rto.setRentAmt(rent);
                    		rto.uv.setUserId(lockerMasterObject.uv.getUserId());
                    		rto.uv.setUserTml(lockerMasterObject.uv.getUserTml());
                    		storeRentTran(rto);
                    	}
                    }
					
				}
				else if(int_type==1||int_type==3)
				{
					if(cl_bal<=min_bal)  
						return 2;
					else if(rent==0)
						return 5;
				}
				
			}	
			if(totrent>0)
			{
				int code=0;
				rs1=stat.executeQuery("select gl_code from GLKeyParam where ac_type=1999005 and code=1");
				if(rs1.next())
					code=rs1.getInt(1);
				rs1.close();
				
				GLTransObject trn_obj=new GLTransObject();
				trn_obj.setTrnDate(lockerMasterObject.getTrnDate());
				trn_obj.setGLType("GL");
				trn_obj.setGLCode(String.valueOf(code));
				trn_obj.setTrnMode("T");
				trn_obj.setAmount(totrent);
				trn_obj.setCdind("C");
				if(int_type==1||int_type==3)
				{
					trn_obj.setAccType(lockerMasterObject.getLockerAcType());
					trn_obj.setAccNo(String.valueOf(lockerMasterObject.getLockerAcNo()));
				}
				else
				{
					trn_obj.setAccType("");
					trn_obj.setAccNo("");
				}
				trn_obj.setTrnSeq(0);
				trn_obj.setTrnType("");
				trn_obj.setRefNo(0);
				trn_obj.setVtml(lockerMasterObject.uv.getUserTml());
				trn_obj.setVid(lockerMasterObject.uv.getUserId());
				
				*//**
				 * if block should throw SQLException() if 0 else it will return 3
				 *
				if(commonremote_local.storeGLTransaction(trnobj)==0)
				{
					throw new SQLException();
					
				}
				else 
				return 3;
			**//*
				return(commonremote_local.storeGLTransaction(trn_obj)==1?0:3);
			}
			
			
		}catch(SQLException sqlexception){sqlexception.printStackTrace();}		
		finally{
			try{
				conn.close();
		
			}catch(SQLException e){e.printStackTrace();}
		}
		
        System.out.println("---------------------inside rentCollectByTransfer--------------------");
        
        return 4;
	}*/
	
    //ship........04/07/2006
	/*public int storeRentTran(RentTransObject rto) 
	{
		Connection  conn=null;
		PreparedStatement pstmt=null;
		try
		{
			conn=getConnection();
			
			if(conn==null)
				throw new SQLException();
		
			pstmt=conn.prepareStatement("insert into RentTran values(?,?,date_format(sysdate(),'%d/%m/%Y'),?,?,?,?,?,?,?,?,date_format(sysdate(),'%d/%m/%Y %r'))");
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
			
			if(pstmt.executeUpdate()==0)
				throw new SQLException();
				
			return(pstmt.executeUpdate());
			
		}
		catch(SQLException sqlexception){sqlexception.printStackTrace();}		
		finally{
			try{
				conn.close();
			}catch(SQLException e){e.printStackTrace();}
		}
		return 0;
	}*/
	
	/**
	 * Insertion of the details of the given locker a/c into LockerTransaction.
	 */
	public boolean storeLockerTransaction(LockerTransObject lockerTransObject,int int_type) 
	{
		Connection  conn = null;
		Statement stmt = null,st_lt = null,st_od = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null,rs_lt = null,rs_od = null;
		
		try
		{
			conn = getConnection();
		
			if(conn==null)
				throw new SQLException();
		
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			st_lt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			st_od = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);//ship.....31/03/2006
			System.out.println("0...");
		
			//ship.....21/03/2006
            /*if(int_type==1)
			{
				System.out.println("10...");
				if(lockerTransObject.getTimeOut().equals("0"))
				{
					System.out.println("1...");
					System.out.println(" "+getSysDate());
					
					pstmt=conn.prepareStatement("insert into LockerTransaction values(?,?,?,null,null,null,null,null,?,null,null,?,'"+getSysDate()+"',?,null,?,?,'"+getSysDate()+"',null,null,null)");	
					pstmt.setString(1,lockerTransObject.getLockerAcType());
					pstmt.setInt(2,lockerTransObject.getLockerAcNo());
					pstmt.setInt(3,lockerTransObject.getLockerNo());
					System.out.println("3...");
					rs=stmt.executeQuery("select trn_seq from LockerTransaction where ac_type='"+lockerTransObject.getLockerAcType()+"' and ac_no="+lockerTransObject.getLockerAcNo()+" order by trn_seq desc");			
					if(rs.next())
						pstmt.setInt(4,rs.getInt(1)+1);
					else
						pstmt.setInt(4,1);
					pstmt.setString(5,lockerTransObject.getOperatedBy());
					pstmt.setString(6,lockerTransObject.getTimeIn());
					
					System.out.println("2...");
					pstmt.setString(7,lockerTransObject.uv.getUserTml());
					pstmt.setString(8,lockerTransObject.uv.getUserId());
					
					if(pstmt.executeUpdate()==0)
						throw new SQLException();
					return true;
				}
				System.out.println("12...");
				if(stmt.executeUpdate("update LockerTransaction set time_out='"+lockerTransObject.getTimeOut()+"' where ac_type='"+lockerTransObject.getLockerAcType()+"' and ac_no="+lockerTransObject.getLockerAcNo()+" and time_out is null")==1)
				    return true;
				return false;	
			}
            System.out.println("11...");
            //	if(stmt.executeUpdate("update LockerTransaction set ve_user='"+lockerTransObject.uv.getVerId()+"',ve_tml='"+lockerTransObject.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %r') where ac_type='"+lockerTransObject.getLockerAcType()+"' andlockerTransObject_no="+lockerTransObject.getLockerAcNo()+" and ve_user is null")==1)
            
            System.out.println("acc type"+lockerTransObject.getLockerAcType());
            System.out.println("ac_no"+lockerTransObject.getLockerAcNo());
            System.out.println("line no1633");
            
            if(stmt.executeUpdate("update LockerTransaction set ve_user='"+lockerTransObject.uv.getVerId()+"',ve_tml='"+lockerTransObject.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %r') where ac_type='"+lockerTransObject.getLockerAcType()+"' and ac_no="+lockerTransObject.getLockerAcNo()+" and ve_user is null")==1)
                return true;
            return false;*/
			
			if(int_type==1)//LK operation... data entry
			{
			    System.out.println("inside lk_op data entry");
			    rs_lt = st_lt.executeQuery("select lk.*,l.key_no,l.descptn,l.locker_st,lt.time_in,lt.time_out,lt.de_user,lt.de_tml,lt.ve_user,lt.ve_tml from LockerMaster lk,LockerTransaction lt,Lockers l where lk.ac_type='"+lockerTransObject.getLockerAcType()+"' and lk.ac_no="+lockerTransObject.getLockerAcNo()+" and lt.ac_type=lk.ac_type and lt.ac_no=lk.ac_no and rent_by is null and trf_acty is null and trf_acno is null and rent_amt is null and rent_upto is null and lt.ve_user is null and lt.ve_tml is null and lt.ve_date is null and lk.locker_no=l.locker_no and op_date='"+lockerTransObject.getTrnDate()+"'");
			    
			    if(!rs_lt.next())
			    {
			        System.out.println("inside !rs_lt.next()");
			        pstmt=conn.prepareStatement("insert into LockerTransaction values(?,?,?,null,null,null,null,null,?,null,null,?,'"+lockerTransObject.getTrnDate()+"',?,?,?,?,'"+lockerTransObject.uv.getUserDate()+"',null,null,null)");	
					pstmt.setString(1,lockerTransObject.getLockerAcType());
					pstmt.setInt(2,lockerTransObject.getLockerAcNo());
					pstmt.setInt(3,lockerTransObject.getLockerNo());
					System.out.println("3...");
					rs=stmt.executeQuery("select trn_seq from LockerTransaction where ac_type='"+lockerTransObject.getLockerAcType()+"' and ac_no="+lockerTransObject.getLockerAcNo()+" order by trn_seq desc");			
					if(rs.next())
						pstmt.setInt(4,rs.getInt(1)+1);
					else
						pstmt.setInt(4,1);
					pstmt.setString(5,lockerTransObject.getOperatedBy());
					pstmt.setString(6,lockerTransObject.getTimeIn());
					
					pstmt.setString(7,lockerTransObject.getTimeOut());
					
					System.out.println("2...");
					pstmt.setString(8,lockerTransObject.uv.getUserTml());
					pstmt.setString(9,lockerTransObject.uv.getUserId());
					
					
					if(pstmt.executeUpdate() > 0)
					    return true;
					else
					    throw new SQLException();
			    }
			    else
			    {
			        System.out.println("inside rs_lt.next()");
			        if(stmt.executeUpdate("update LockerTransaction set time_out='"+lockerTransObject.getTimeOut()+"' where ac_type='"+lockerTransObject.getLockerAcType()+"' and ac_no="+lockerTransObject.getLockerAcNo()+" and time_in is not null and time_out is null and ve_tml is null and ve_user is null and ve_date is null and op_date='"+lockerTransObject.getTrnDate()+"'")==1)
					    return true;
			        else
					    throw new SQLException();
			    }
			}
			//ship......29/03/2006
			else if(int_type==2)//LK operation... replacement
			{
			    System.out.println("inside int_type==3");
		        pstmt=conn.prepareStatement("insert into LockerTransaction values(?,?,?,null,null,null,null,null,?,null,null,?,'"+lockerTransObject.getTrnDate()+"',?,?,?,?,'"+lockerTransObject.uv.getUserDate()+"',null,null,null)");	
				pstmt.setString(1,lockerTransObject.getLockerAcType());
				pstmt.setInt(2,lockerTransObject.getLockerAcNo());
				pstmt.setInt(3,lockerTransObject.getLockerNo());
				System.out.println("3...");
				rs=stmt.executeQuery("select trn_seq from LockerTransaction where ac_type='"+lockerTransObject.getLockerAcType()+"' and ac_no="+lockerTransObject.getLockerAcNo()+" order by trn_seq desc");			
				if(rs.next())
					pstmt.setInt(4,rs.getInt(1)+1);
				else
					pstmt.setInt(4,1);
				pstmt.setString(5,lockerTransObject.getOperatedBy());
				pstmt.setString(6,lockerTransObject.getTimeIn());
				pstmt.setString(7,lockerTransObject.getTimeOut());
				
				System.out.println("2...");
				pstmt.setString(8,lockerTransObject.uv.getUserTml());
				pstmt.setString(9,lockerTransObject.uv.getUserId());
				
				if(pstmt.executeUpdate() > 0)
				    return true;
				else
				    throw new SQLException();
			}
			else if(int_type==3)//LK operation....verification
			{
			    if(stmt.executeUpdate("update LockerTransaction set ve_user='"+lockerTransObject.uv.getVerId()+"',ve_tml='"+lockerTransObject.uv.getVerTml()+"',ve_date='"+lockerTransObject.uv.getVerDate()+"' where ac_type='"+lockerTransObject.getLockerAcType()+"' and ac_no="+lockerTransObject.getLockerAcNo()+" and time_in is not null and time_out is not null and ve_tml is null and ve_user is null and ve_date is null and op_date='"+lockerTransObject.getTrnDate()+"'")==1)
				    return true;
		        else
				    throw new SQLException();
			}			
			//ship.......31/03/2006
			else if(int_type==4)//LK Surrender... data entry
			{
			    System.out.println("inside int_type==4");
		        pstmt=conn.prepareStatement("insert into LockerTransaction values(?,?,?,null,null,null,null,?,?,'C',null,?,'"+lockerTransObject.getTrnDate()+"',null,null,?,?,'"+lockerTransObject.uv.getUserDate()+"',null,null,null)");	
				pstmt.setString(1,lockerTransObject.getLockerAcType());
				pstmt.setInt(2,lockerTransObject.getLockerAcNo());
				pstmt.setInt(3,lockerTransObject.getLockerNo());
				System.out.println("4...");
				
				pstmt.setString(4,lockerTransObject.getRentUpto());
				rs=stmt.executeQuery("select trn_seq from LockerTransaction where ac_type='"+lockerTransObject.getLockerAcType()+"' and ac_no="+lockerTransObject.getLockerAcNo()+" order by trn_seq desc");			
				if(rs.next())
					pstmt.setInt(5,rs.getInt(1)+1);
				else
					pstmt.setInt(5,1);
				pstmt.setString(6,lockerTransObject.getOperatedBy());
				
				System.out.println("2...");
				pstmt.setString(7,lockerTransObject.uv.getUserTml());
				pstmt.setString(8,lockerTransObject.uv.getUserId());
				
				if(pstmt.executeUpdate() > 0)
				    return true;
				else
				    throw new SQLException();
			}
			else if(int_type==5)//LK Surrender.....Verification
			{
			    System.out.println("lk surrender verify....ve_tml = "+lockerTransObject.uv.getVerTml());
			    System.out.println("lk surrender verify....vid = "+lockerTransObject.uv.getVerId());
			    System.out.println("lk surrender verify....ve_date = "+lockerTransObject.uv.getVerDate());
			    System.out.println("lk surrender Trndate = "+lockerTransObject.getTrnDate());
				   
			    rs = stmt.executeQuery("select * from LockerMaster where ac_type='"+lockerTransObject.getLockerAcType()+"' and ac_no="+lockerTransObject.getLockerAcNo()+" and ve_tml is not null");
			    
			    if(rs.next())
			    {
			        if(stmt.executeUpdate("update LockerTransaction set ve_user='"+lockerTransObject.uv.getVerId()+"',ve_tml='"+lockerTransObject.uv.getVerTml()+"',ve_date='"+lockerTransObject.uv.getVerDate()+"' where ac_type='"+lockerTransObject.getLockerAcType()+"' and ac_no="+lockerTransObject.getLockerAcNo()+" and rent_by is null and trf_acty is null and trf_acno is null and rent_amt is null and rent_upto is not null and trn_seq is not null and trn_type is not null and cd_ind is null and time_in is null and time_out is null and ve_tml is null and ve_user is null and ve_date is null and op_date='"+lockerTransObject.getTrnDate()+"'")==0)
			            throw new SQLException();
			        
			        if(stmt.executeUpdate("update LockerMaster set close_dt='"+lockerTransObject.getTrnDate()+"' where ac_type='"+lockerTransObject.getLockerAcType()+"' and ac_no="+lockerTransObject.getLockerAcNo()+" and ve_user is not null and ve_tml is not null and ve_date is not null")==0)
			            throw new SQLException();
			        
			        if(stmt.executeUpdate("update Lockers set locker_st='F' where locker_no="+lockerTransObject.getLockerNo()+" and locker_ty='"+lockerTransObject.getLockerType()+"'")==0)
			            throw new SQLException();
			        
			        rs_od = st_od.executeQuery("select * from ODInterestDetails where ac_type='"+lockerTransObject.getLockerAcType()+"' and ac_no="+lockerTransObject.getLockerAcNo()+" and to_date='"+lockerTransObject.getRentUpto()+"'");
	                
	                while(rs_od.next())
	                {
	                    if(stmt.executeUpdate("update DepositMaster set ln_availed='F',ln_ac_type=null,ln_ac_no=null where ac_type='"+rs_od.getString("sec_ac_type")+"' and ac_no="+rs_od.getInt("sec_ac_no")+" and ln_availed='T' and ln_ac_type='"+lockerTransObject.getLockerAcType()+"' and ln_ac_no="+lockerTransObject.getLockerAcNo()+"")==0)
		                    throw new SQLException();
	                }
	                
	                return true;
			    }
			}
			////////////////
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
			obj_sessionContext.setRollbackOnly();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
			    e.printStackTrace();
			}
		}
		
		return false;
	}
	
	/**
	 * Gives the details of Security Deposit for the given locker a/c. 
	 */
	public double getSecurityDeposit(String string_account_type,int int_account_no)
	{
		Connection  conn=null;
		Statement stmt=null,stmt1=null;
		ResultSet rs=null,rs1=null;
		double double_security_deposit=0;
		
		System.out.println("**********inside bean getSecuriytDeposit**************");
	
		System.out.println("type"+string_account_type);
		System.out.println("no"+int_account_no);
		
		try
		{
			conn=getConnection();
		
			if(conn==null)
				throw new SQLException();
		
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			rs=stmt.executeQuery("Select locker_ty from LockerMaster where ac_type='"+string_account_type+"' and ac_no="+int_account_no+" ");
			
			if(rs.next())
				rs1=stmt1.executeQuery("select security_deposit from LockerRate where locker_type='"+rs.getString(1)+"' ");
			else
				throw new SQLException();
			if(rs1.next())
					double_security_deposit=rs1.getDouble(1);
			else
				throw new SQLException();
			
					
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
			obj_sessionContext.setRollbackOnly();
		}		
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
			    e.printStackTrace();
			}
		}
		
		System.out.println("*****before return in getSecurityDeposit*********");
		System.out.println("deposit ="+double_security_deposit);
				
		return double_security_deposit;
	}
	
	/**
	 * Gives the details of Locker Rate for the given Locker a/c.
	 */
	public double getLockerRate(String string_account_type,int int_account_no) 
	{
		Connection  conn=null;
		Statement stmt=null,stmt1=null;
		ResultSet rs=null,rs1=null;
		double double_locker_rate=0;
		
		System.out.println("**********inside bean getLcokerRate**************");
	
		System.out.println("type"+string_account_type);
		System.out.println("no"+int_account_no);
	
		try
		{
			conn=getConnection();
		
			if(conn==null)
				throw new SQLException();
		
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			rs=stmt.executeQuery("Select locker_ty from LockerMaster where ac_type='"+string_account_type+"' and ac_no="+int_account_no+" ");
			
			stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			if(rs.next())
				rs1=stmt1.executeQuery("select locker_rate from LockerRate where locker_type='"+rs.getString(1)+"' ");
			else
				throw new SQLException();
		
			if(rs1.next())
				double_locker_rate=rs1.getDouble(1);
			else
				throw new SQLException();
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
			obj_sessionContext.setRollbackOnly();
		}		
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
			    e.printStackTrace();
			}
		}
	
		System.out.println("*****before return in getLockerRate*********");
		System.out.println("rate ="+double_locker_rate);
	
		return double_locker_rate;
	}

	
//Get Distinct cab numbers for Locker table
	
	public int[] getDistinctCabs(String lkTypes){
		Connection  conn=null;
		Statement stmt=null,stmt1=null;
		ResultSet rs=null,rs1=null;
		int[] cabNumbers=null;
		try
		{
			conn=getConnection();
		
			if(conn==null)
				throw new SQLException();
		
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			
			rs=stmt.executeQuery("select DISTINCT (cab_no) as num from Lockers where locker_ty='"+lkTypes+"' ");
			
			rs.last();
			int size=rs.getRow();
			cabNumbers = new int[size];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				cabNumbers[i]=rs.getInt("num");
				i++;
			}
						}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
			obj_sessionContext.setRollbackOnly();
		}		
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
			    e.printStackTrace();
			}
		
	}
		return cabNumbers;
		}
	
	
	public LockerDetailsObject[] getLockerCabStructure(int cabNum){
		Connection  conn=null;
		Statement stmt=null,stmt1=null;
		ResultSet rs=null,rs1=null;
		LockerDetailsObject[] locDetailsObjects=null;
		
		try
		{
			conn=getConnection();
					if(conn==null)
				throw new SQLException();
		
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			rs=stmt.executeQuery("select * from CabStructure where cab_no="+cabNum+" order by row_no;");
			
			
			 if ( rs.last()){
				 
				 
				 System.out.println( rs.getRow() + " rows lemhth " );
				 locDetailsObjects = new LockerDetailsObject[ rs.getRow()];	
				 
			 } else {
				 
				 return null;
			 }
			
			 rs.beforeFirst();
			 
			 int i = 0;
			 while ( rs.next() ){
				 
				 locDetailsObjects[i] = new LockerDetailsObject();
				 
				 locDetailsObjects[i].setCabNo(rs.getInt("cab_no"));
				 locDetailsObjects[i].setRowNum(rs.getInt("row_no"));
				 locDetailsObjects[i].setCols(rs.getInt("no_of_cols"));
				 i++;
				 System.out.println( rs.getInt("cab_no")+ "     " + rs.getInt("row_no")  + "     " + rs.getInt("no_of_cols") ); 
				 
			 }  
			
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
			obj_sessionContext.setRollbackOnly();
		}		
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
			    e.printStackTrace();
			}
		
	}
		return locDetailsObjects;
		}
	/**
	 * Gives the cabin no of the locker for the given locker a/c.
	 */
	public int getCabinNo(String string_account_type,int int_account_no)
	{
		Connection  conn=null;
		Statement stmt=null,stmt1=null;
		ResultSet rs=null,rs1=null;
		int int_cabin_no=0;
		
		System.out.println("**********inside bean getLcokerRate**************");
	
		System.out.println("type"+string_account_type);
		System.out.println("no"+int_account_no);
	
		try
		{
			conn=getConnection();
		
			if(conn==null)
				throw new SQLException();
		
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			rs=stmt.executeQuery("Select locker_ty,locker_no from LockerMaster where ac_type='"+string_account_type+"' and ac_no="+int_account_no+" ");
			
			stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			if(rs.next())
				rs1=stmt1.executeQuery("select cab_no from Lockers where locker_ty='"+rs.getString(1)+"' and locker_no="+rs.getInt(2)+" ");
			else
				throw new SQLException();
		
			if(rs1.next())
				int_cabin_no=rs1.getInt(1);
			else
				throw new SQLException();
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
			obj_sessionContext.setRollbackOnly();
		}		
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
			    e.printStackTrace();
			}
		}
	
		System.out.println("*****before return in getCabinNo*********");
		System.out.println("rate ="+int_cabin_no);
	
		return int_cabin_no;
	}
	
	/*	public int storeLockerGL(LockerMasterObject lockerMasterObject,int scode) throws RemoteException
	 {
	 ResultSet rs1=null;
	 int code=0;
	 try
	 {
	 
	 
	 }catch(SQLException sqlexception){sqlexception.printStackTrace();}		
	 }*/
    
    /**
     * Retrieves all the templates for the given locker a/c type.
     */
    //For RemainderReport
    public String[] getTemplate(String acty)
    {
        String str[]=null;
        Connection conn=null;
        
        try
        {
            conn=getConnection();
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("select text,temp_no from Template where ac_type='"+acty+"'");
            
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
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            obj_sessionContext.setRollbackOnly();
        }
        finally
        {
            try
            {                
                conn.close();
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
        }
        
        return str;
    }
    
    /**
     * Insertion of templates for the given locker a/c type.
     */
    public boolean storeTemplate(String text,String acty,int stage,String user,String tml,int temp_no,String date_time) 
    {
        System.out.println("***inside storeTemplate****");
        
        Connection conn=null;
        
        try
        {
            conn=getConnection();
            Statement stmt=conn.createStatement();
            if(temp_no==0)
            {
                PreparedStatement pstmt=conn.prepareStatement("insert into Template values(?,?,?,?,?,?,?)");
                ResultSet rs=stmt.executeQuery("select max(temp_no) from Template where ac_type='"+acty+"'");
                if(rs.next())
                {
                    pstmt.setString(1,acty);
                    pstmt.setInt(2,rs.getInt(1)+1);
                    pstmt.setInt(3,stage);
                    pstmt.setString(4,text);
                    pstmt.setString(5,user);
                    pstmt.setString(6,tml);
                    pstmt.setString(7,date_time);
                    
                    if(pstmt.executeUpdate()==1)
                        return true;
                    
                    return false;
                }
            }
            else if(stmt.executeUpdate("update Template set text='"+text+"' where ac_type='"+acty+"' and temp_no="+temp_no+"")==1)
                return true;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            obj_sessionContext.setRollbackOnly();
        }
        finally
        {
            try
            {                
                conn.close();
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
        }
        
        return false;
    }
    
    /**
     * Reminders Report - Insertion & Updation of LockerRemainder
     */
    //ship......01/11/2006....added date
    public int getRemainderNotification(String ac_type,int ac_no,String email_flag,String sms_flag,String date)
    {
        Connection  conn=null;
        Statement stmt=null;
        PreparedStatement pstmt=null;
        
        try 
        {
            conn=getConnection();
            if(conn==null)
                throw new SQLException();
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("Select * from LockerRemainder where ac_type='"+ac_type+"' and ac_no="+ac_no+" ");
            if(rs.next()) 
            { 
                if(stmt.executeUpdate("update LockerRemainder set email_flag='"+email_flag+"' and sms_flag='"+sms_flag+"' where ac_type='"+ac_type+" and ac_no='"+ac_no+"'")==0)
                    throw new SQLException();
            }
            else 
            {
                pstmt=conn.prepareStatement("insert into LockerRemainder values(?,?,'"+date+"',?,?");
                pstmt.setString(1,ac_type);
                pstmt.setInt(2,ac_no);
                pstmt.setString(3,email_flag);
                pstmt.setString(4,sms_flag);
               
                if(pstmt.executeUpdate()==0)
                    throw new SQLException();
                return 1;
            }
            
            return(pstmt.executeUpdate());
        }
        catch(SQLException sqlexception) 
        {
             sqlexception.printStackTrace();
             obj_sessionContext.setRollbackOnly();
             return 0;
        } 
        finally 
        {
            try 
            {
                conn.close();
            }
            catch(SQLException e) 
            {
                e.printStackTrace();
            }
        }
   }
    
    public Connection getConnection() 
	{
		try
		{			
			return obj_dataSource.getConnection("root","");
		}
		catch(SQLException e)	
		{
		    e.printStackTrace();
		}
		
		return null;
	}
  
    public String[] getOperatedBy() 
    {
        return null; 
    }
    
    //Example user Transaction
    public int[] getAccountNumbers()
    {
        System.out.println("---------------inside getAccountNumbers start-----------------");
        
        Connection  conn=null;
        Statement stmt=null;
        int[] ac_nos=null;
        
        try
        {
            conn=getConnection();
            
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("Select ac_no from VouMas where credit>500");
            
            if(rs.next())
            {
                rs.last();
                System.out.print("length "+rs.getRow());
                ac_nos=new int[rs.getRow()];
            }
            
            int i=0;
            rs.beforeFirst();
              
            while(rs.next())
            {
                ac_nos[i]=rs.getInt("ac_no");
                System.out.println("ac no in getAccountNumbers"+rs.getInt("ac_no"));
                i++;
            }
        }
        catch(SQLException sqlexception)
        {
             sqlexception.printStackTrace();
             obj_sessionContext.setRollbackOnly();
        }       
        finally
        {
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        System.out.println("---------------inside getAccountNumbers end-----------------");
        
        return ac_nos;
    }
    
    public double[] getCreditAmount() throws RemoteException
    {
        System.out.println("---------------inside getCreditAmount start-----------------");
        
        Connection  conn=null;
        Statement stmt=null;
        double[] credit_amount=null;
        
        try
        {
            conn=getConnection();
            
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("Select credit from VouMas where credit>500");
            
            if(rs.next())
            {
                rs.last();
                System.out.print("length "+rs.getRow());
                credit_amount=new double[rs.getRow()];
            }
            int i=0;
            rs.beforeFirst();
              
            while(rs.next())
            {
                credit_amount[i]=rs.getDouble("credit");
                System.out.println("credit amount in getCreditamount"+rs.getDouble("credit"));
                i++;
            }
        }
        catch(SQLException sqlexception)
        {
             sqlexception.printStackTrace();
             obj_sessionContext.setRollbackOnly();
        }       
        finally
        {
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        System.out.println("---------------inside getCreditamount end-----------------");
        
        return credit_amount;
    }
    
    public void getDeleteRecord(int ac_no)
    {
        System.out.println("---------------inside getDeleteRecord start-----------------");
        
        Connection  conn=null;
        Statement stmt=null;
                
        try
        {
            conn=getConnection();
            stmt=conn.createStatement();
            
            if(stmt.executeUpdate("delete from VouMas where ac_no="+ac_no+" and credit>500 ")==0)
                System.out.println("No matching Record for"+ac_no);
        
            ResultSet rs=stmt.executeQuery("Select ac_no from VouMas where credit>500");
            if(rs.next())
            {
                rs.last();
                System.out.println("length after deleting"+rs.getRow());
            }
        }
        catch(SQLException sqlexception)
        {
             sqlexception.printStackTrace();
             obj_sessionContext.setRollbackOnly();
        }       
        finally
        {
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("---------------inside getDeleteRecord end-----------------");
    }
    
    public int getUpdateRecord(int ac_no,double credit_amount)
    {
        System.out.println("---------------inside getUpdate start-----------------");
        
        Connection  conn=null;
        Statement stmt=null;
                
        try
        {
            conn=getConnection();
            
            stmt=conn.createStatement();
            System.out.println("ac no is "+ac_no);
               
            if(stmt.executeUpdate("update VouTran set balance=balance-"+credit_amount+" where ac_no="+ac_no+"  and balance>"+credit_amount+"")==0)
            {
                System.out.println("***********1 if***************");
                return 0;
            }
            System.out.println("***********1 else***************");
            
            if(stmt.executeUpdate("update VouTran set credit_flag='F' where ac_no="+ac_no+"")==0)
                return 0;        
        }
        catch(SQLException sqlexception)
        {
             sqlexception.printStackTrace();
             obj_sessionContext.setRollbackOnly();
             return 0;
        }       
        finally
        {
            try
            {
                conn.close();
            }catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        System.out.println("---------------inside getUpdate end-----------------");
           
        return 1;
    }
	
    //ship......04/07/2006
   /* //ship.....19/01/2006
    public String getSysDateTime() 
    {
        Calendar c = Calendar.getInstance();
        
        Statement stmt_time = null;
        Connection conn = null;
        ResultSet rs_time = null;
        
        //ship.....06/05/2006
        String str=String.valueOf(c.get(Calendar.SECOND));
        if(str.length()==1)
            str="0"+str;
        
        String str1=String.valueOf(c.get(Calendar.MINUTE));
        if(str1.length()==1)
            str1="0"+str1;
        
        String str2=String.valueOf(c.get(Calendar.HOUR));
        if(str2.length()==1)
            str2="0"+str2;
        
        try 
        {
            return(Validations.checkDate(c.get(Calendar.DATE)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR))+" "+new Date().getHours()+":"+new Date().getMinutes()+":"+new Date().getSeconds()+"");
		}
        catch(DateFormatException e) 
        {
			e.printStackTrace();
		}
		return null;
    }*/
    
    /**
     * Checks whether the locker password entered is valid or not.
     */
    //ship.....20/03/2006.....to check locker password
    public boolean checkPwd(String lk_actype,int lk_acno,String lk_pwd) 
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			rs=stmt.executeQuery("select * from LockerMaster where ac_type='"+lk_actype+"' and ac_no="+lk_acno+" and locker_pw=MD5('"+lk_pwd+"')");
			if(rs.next())
				return true;
			
			return false;
		}
		catch(SQLException exception)
		{
		    exception.printStackTrace();
		    obj_sessionContext.setRollbackOnly();
		}
		finally
		{
			try
			{		    	
				conn.close();
			}
			catch(Exception exception)
			{
			    exception.printStackTrace();
			}
		}
		
		return false;
	}
    ////////////
    
    /**
     * Deletes the entries in the Locker Transaction for Locker operation & Locker Surrender.
     */
    //ship......24/03/2006
    public boolean deleteLockerTransaction(LockerTransObject lockerTransObject,int type) throws RecordNotUpdatedException 
    {
        System.out.println("inside  deleteLockerTransaction");
        System.out.println("lk ac no = "+lockerTransObject.getLockerAcType());
        System.out.println("lk ac type = "+lockerTransObject.getLockerAcNo());
        System.out.println("lk time in = "+lockerTransObject.getTimeIn());
        System.out.println("lk time out = "+lockerTransObject.getTimeOut());
        
        Statement stmt=null;
        Connection conn=null;
       
        try
        {
           conn=getConnection();
           
           if(conn==null)
               throw new SQLException();
       
           stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           
           if(type==1)//to delete locker operation entries.....31/03/2006
           {
        	   
        	   System.out.println("----Tran type------"+lockerTransObject.getModuleCode());
        	   System.out.println("-----Lock num-----"+lockerTransObject.getLockerAcNo());
        	   System.out.println("-----tim out-----"+lockerTransObject.getTimeOut());
        	   System.out.println("-----time in-----"+lockerTransObject.getTimeIn());
        	   System.out.println("-----op date -----"+lockerTransObject.getTrnDate());
        	   
               if(stmt.executeUpdate("delete from LockerTransaction where ac_type='1009001' and ac_no="+lockerTransObject.getLockerAcNo()+" and rent_by is null and trf_acty is null and trf_acno is null and rent_amt is null and rent_upto is null and time_in='"+lockerTransObject.getTimeIn()+"' and time_out='"+lockerTransObject.getTimeOut()+"' and ve_user is null and ve_tml is null and ve_date is null and op_date='"+lockerTransObject.getTrnDate()+"'")==0)
                   throw new SQLException();
               else
                   return true;
           }
           else if(type==2)//to delete locker surrender entries.....31/03/2006
           {
               if(stmt.executeUpdate("delete from LockerTransaction where ac_type='"+lockerTransObject.getLockerAcType()+"' and ac_no="+lockerTransObject.getLockerAcNo()+" and rent_by is null and trf_acty is null and trf_acno is null and rent_amt is null and rent_upto is not null and trn_type is not null and cd_ind is null and time_in is null and time_out is null and ve_user is null and ve_tml is null and ve_date is null and op_date='"+lockerTransObject.getTrnDate()+"'")==0)
                   throw new SQLException();
               else
                   return true;
           }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj_sessionContext.setRollbackOnly();
        }
        finally
		{
			try
			{		    	
				conn.close();
			}
			catch(Exception exception)
			{
			    exception.printStackTrace();
			}
		}
        
        return false;
    }
    ////////
    
    /**
     * Gives the details of locker a/cs which are opted foe auto extension through transfer.
     */
    //ship......04/04/2006
    public LockerMasterObject[] getAutoExtnLockers(String date) throws RecordsNotFoundException
    {
        Connection conn=null;
        
        LockerMasterObject array_lkmasterobject[] = null;
        
        try
        {
            ResultSet  rs=null;
            conn=getConnection();
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
          //  rs = stmt.executeQuery("select * from LockerMaster where concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1)) <= '"+Validations.convertYMD(date)+"' and auto_extn='T'");
           
          //  Change By Balakrishna On 30/10/2009 for Core Bank 
          // Gives Details only those rent by Transfer  
            
            rs = stmt.executeQuery("select * from LockerMaster lm,LockerTransaction lt where concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1)) <='"+Validations.convertYMD(date)+"' and lm.ac_no=lt.ac_no and lt.rent_by='T' and auto_extn='T' and close_dt is null  and lt.trn_seq=lm.lst_tr_seq");
            
        	rs.last();
            
        	if(rs.getRow()==0)
        	{
        		array_lkmasterobject=null;
        	}   
        	else
        	{
        	    array_lkmasterobject = new LockerMasterObject[rs.getRow()];
        		rs.beforeFirst();
        		int i = 0;
                
        		while(rs.next())
        		{
        			System.out.println("igoin Locker no--"+rs.getInt("ac_no"));
        			array_lkmasterobject[i] = new LockerMasterObject();  
        			
        			array_lkmasterobject[i].setLockerAcType(rs.getString("ac_type"));
        			array_lkmasterobject[i].setLockerAcNo(rs.getInt("ac_no"));
        			array_lkmasterobject[i].setMatDate(rs.getString("mat_date"));
            
        		//	System.out.println("*************inside getAutoExtnLockers**************");
        		
        			i++;
        		}
        	}
        }
        catch(Exception exception)
		{
		    exception.printStackTrace();
		    obj_sessionContext.setRollbackOnly();
		}
        finally
		{
			try
			{		    	
				conn.close();
			}
			catch(Exception exception)
			{
			    exception.printStackTrace();
			}
		}
        
        return array_lkmasterobject;
    }
    
    /**
     * Locker Rent Due Report.
     */
    //ship.......28/04/2006
    public LockerMasterObject[] getRentDueReport(String string_to_date,int int_type,String string_query)
    {
        LockerMasterObject lmo[] = null;
        ResultSet rs = null,rs1 = null,rs3 = null,rs_modules = null;
        Connection conn = null;
        Statement stmt = null,stmt1 = null,stmt3 = null,stmt_modules = null;
        
        String lk_ac_type = null,name = null,lk_type = null,mat_dt = null,memb_type = null;
        int lk_ac_no = 0,lk_no = 0,memb_no = 0;
        
        int i = 0,no_rows = 0;
        
        String mat_date = null;
        
        System.out.println("inside getRentDueReport");
        System.out.println("to_date = "+string_to_date);
        System.out.println("type = "+int_type);
        System.out.println("query = "+string_query);
        
        try
        {
            conn = getConnection();
            
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            stmt3 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt_modules = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
            if(conn==null)
                throw new SQLException();
            
            if(int_type==1 || int_type==3)//Rent Due Report, Not Surrendered Report
            {
                System.out.println("inside int_type == 1");
                rs = stmt.executeQuery("select lm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,lty.descrptn,m.moduleabbr from LockerMaster lm,CustomerMaster cm,Lockers l,LockerType lty,Modules m where lm.cid=cm.cid and lm.locker_no=l.locker_no and lm.locker_ty=l.locker_ty and l.locker_ty=lty.locker_type and m.modulecode=lm.ac_type and lm.close_dt is null and lm.ve_tml is not null and lm.ve_user is not null and lm.ve_date is not null");
                
                if(rs.next())
                {
                    rs.last();
                    no_rows = rs.getRow();
                    lmo=new LockerMasterObject[no_rows];
                    System.out.println("length in getRentDueReport = "+no_rows);
                    
                    rs.beforeFirst();
                }
                
                while(rs.next())
                {
                    System.out.println("inside while rs.next()");
                    lk_ac_type = rs.getString("lm.ac_type");
                    lk_ac_no = rs.getInt("lm.ac_no");
                    name = rs.getString("name");
                    lk_type = rs.getString("lty.descrptn");
                    lk_no = rs.getInt("lm.locker_no");
                    mat_dt = rs.getString("lm.mat_date");
                    memb_type = rs.getString("lm.sh_type");
                    memb_no = rs.getInt("lm.sh_no");
                    
                    /*rs1 = stmt1.executeQuery("select * from LockerMasterLog where ac_type='"+lk_ac_type+"' and ac_no="+lk_ac_no+" and (concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1)) < '"+Validations.convertYMD(string_to_date)+"')");
                    
                    if(rs1.next())
                        flag = true;
                    else
                        flag = true;
                    
                    System.out.println("flag == "+flag);
                    
                    if(flag==true)
                    {
                        System.out.println("inside flag == true");
                        rs2 = stmt2.executeQuery("select * from LockerMaster where ac_type='"+lk_ac_type+"' and ac_no="+lk_ac_no+" and (concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1)) < '"+Validations.convertYMD(string_to_date)+"')");
                        
                        if(rs2.next())
                        {
                            System.out.println("inside rs2.next()");
                            rs3 = stmt3.executeQuery("select * from LockerTransaction where ac_type='"+lk_ac_type+"' and ac_no="+lk_ac_no+" and (concat(right(rent_upto,4),'-',mid(rent_upto,locate('/',rent_upto)+1,(locate('/',rent_upto,4)-locate('/',rent_upto)-1)),'-',left(rent_upto,locate('/',rent_upto)-1)) < '"+Validations.convertYMD(string_to_date)+"')");
                            
                            if(rs3.last())
                            {
                                System.out.println("inside rs3.next()");
                                lmo[i]=new LockerMasterObject();
                                
                                lmo[i].setLockerAcType(lk_ac_type);
                                lmo[i].setLockerAcNo(lk_ac_no);
                                lmo[i].setName(name);
                                lmo[i].setLockerType(lk_type);
                                lmo[i].setLockerNo(lk_no);
                                lmo[i].setMemberType(memb_type);
                                lmo[i].setMemberNo(memb_no);
                                lmo[i].setRentBy(rs3.getString("rent_by"));
                                lmo[i].setRentUpto(rs3.getString("rent_upto"));
                                lmo[i].setRentColl(rs3.getDouble("rent_amt"));
                                
                                System.out.println("i = "+i);
                                
                                i++;
                            }
                        }
                    }*/
                    
                    rs1 = stmt1.executeQuery("select * from LockerMaster where ac_type='"+lk_ac_type+"' and ac_no="+lk_ac_no+" and (concat(mid(ve_date,locate('/',ve_date)+4,(locate(' ',ve_date)-locate('/',ve_date,4)-1)),'-',mid(ve_date,locate('/',ve_date)+1,(locate('/',ve_date,4)-locate('/',ve_date)-1)),'-',left(ve_date,locate('/',ve_date)-1)) <= '"+Validations.convertYMD(string_to_date)+"') union select * from LockerMasterLog where ac_type='"+lk_ac_type+"' and ac_no="+lk_ac_no+" and (concat(mid(ve_date,locate('/',ve_date)+4,(locate(' ',ve_date)-locate('/',ve_date,4)-1)),'-',mid(ve_date,locate('/',ve_date)+1,(locate('/',ve_date,4)-locate('/',ve_date)-1)),'-',left(ve_date,locate('/',ve_date)-1)) <= '"+Validations.convertYMD(string_to_date)+"') order by concat(mid(ve_date,locate('/',ve_date)+4,(locate(' ',ve_date)-locate('/',ve_date,4)-1)),'-',mid(ve_date,locate('/',ve_date)+1,(locate('/',ve_date,4)-locate('/',ve_date)-1)),'-',left(ve_date,locate('/',ve_date)-1),' ',right(ve_date,8)) desc limit 1");
                                        
                    if(rs1.next())
                    {
                        mat_date = rs1.getString("mat_date");
                        
                        rs_modules = stmt_modules.executeQuery("select max_renewal_count from Modules where modulecode='"+lk_ac_type+"'");
                        
                        if(rs_modules.next())
                        {
                            int grace_period = rs_modules.getInt("max_renewal_count");
                            
                            String date = Validations.addDays(mat_date,grace_period);
                            
                            System.out.println("mat_date = "+mat_date);
                            System.out.println("grace_period = "+grace_period);
                            System.out.println("date = "+date);
                            System.out.println("Entered Date = "+string_to_date);
                            System.out.println("lk_ac_no = "+lk_ac_no);
                            
                            if(((Validations.checkDateValid(date,string_to_date)==1) && (Validations.validDate(string_to_date,mat_date)==1) && int_type==1) || ((Validations.validDate(string_to_date,date)==1) && int_type==3))
                            {
                                System.out.println("inside checkDateValid.next()");
                                
                                rs3 = stmt3.executeQuery("select * from LockerTransaction where ac_type='"+lk_ac_type+"' and ac_no="+lk_ac_no+" and (concat(right(rent_upto,4),'-',mid(rent_upto,locate('/',rent_upto)+1,(locate('/',rent_upto,4)-locate('/',rent_upto)-1)),'-',left(rent_upto,locate('/',rent_upto)-1)) = concat(right('"+mat_date+"',4),'-',mid('"+mat_date+"',locate('/','"+mat_date+"')+1,(locate('/','"+mat_date+"',4)-locate('/','"+mat_date+"')-1)),'-',left('"+mat_date+"',locate('/','"+mat_date+"')-1)))");
                                
                               
                                
                                if(rs3.last())
                                {
                                    System.out.println("inside rs3.next()");
                                    lmo[i]=new LockerMasterObject();
                                    
                                    lmo[i].setLockerAcType(lk_ac_type);
                                    lmo[i].setLockerAcNo(lk_ac_no);
                                    lmo[i].setName(name);
                                    lmo[i].setLockerType(lk_type);
                                    lmo[i].setLockerNo(lk_no);
                                    lmo[i].setMatDate(mat_dt);
                                    lmo[i].setMemberType(memb_type);
                                    lmo[i].setMemberNo(memb_no);
                                    lmo[i].setRentBy(rs3.getString("rent_by"));
                                    lmo[i].setRentUpto(rs3.getString("rent_upto"));
                                    lmo[i].setRentColl(rs3.getDouble("rent_amt"));
                                    
                                    System.out.println("i = "+i);
                                    
                                    i++;
                                }
                            }
                        }
                    }
                }
                
                System.out.println("value of no_rows = "+no_rows);
                System.out.println("value of i = "+i);
                
                for(int j=i;j<no_rows;j++)
                {
                    lmo[j] = new LockerMasterObject();
                    lmo[j].setLockerAcType(null);
                }
            }
        }
        catch(Exception exception)
		{
		    exception.printStackTrace();
		    obj_sessionContext.setRollbackOnly();
		}
        finally
		{
			try
			{		    	
				conn.close();
			}
			catch(Exception exception)
			{
			    exception.printStackTrace();
			}
		}
        
        return lmo;
    }
    ////////////
    
    /**
     * LockerMasterUpdation - Updation of LockerMaster.
     */
    //ship......25/07/2006
    public boolean updateLockerMaster(LockerMasterObject lockerMasterObject)
	{
        System.out.println("------------ Inside update Locker Master --------------");
		System.out.println("ac_type = "+lockerMasterObject.getLockerAcType());
        System.out.println("ac_no = "+lockerMasterObject.getLockerAcNo());
        
		Connection  conn=null;
		Statement stmt_lm = null,stmt_fa = null;
		ResultSet rs_lm = null,rs_fa = null;
						
		try
		{
			conn=getConnection();
			
			stmt_lm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt_fa = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			if(conn==null)
				throw new SQLException();
			
			rs_lm = stmt_lm.executeQuery("select * from LockerMaster where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"");
			
			if(rs_lm.next())
			{
			    String last_mat_date = rs_lm.getString("mat_date");
			    
			    if(stmt_lm.executeUpdate("insert into LockerMasterLog select * from LockerMaster where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"")!=1)
					throw new SQLException("Record not inserted in LockerMasterLog");
			    
			    if(stmt_lm.executeUpdate("update LockerMaster set cid="+lockerMasterObject.getCid()+",addr_type="+lockerMasterObject.getMailingAddress()+",locker_no="+lockerMasterObject.getLockerNo()+",no_jt_hldr="+lockerMasterObject.getNoOfJntHldrs()+",op_inst='"+lockerMasterObject.getOprInstrn()+"',sh_type='"+lockerMasterObject.getShareCode()+"',sh_no="+lockerMasterObject.getMemberNo()+",no_securities="+lockerMasterObject.getNoOfSecurities()+",auto_extn='"+lockerMasterObject.getAutoExtn()+"',intr_ac_type='"+lockerMasterObject.getIntrAcTy()+"',intr_ac_no="+lockerMasterObject.getIntrAcNo()+",de_user='"+lockerMasterObject.uv.getUserId()+"',de_tml='"+lockerMasterObject.uv.getUserTml()+"',de_date='"+lockerMasterObject.uv.getUserDate()+"',ve_user='"+lockerMasterObject.uv.getVerId()+"',ve_tml='"+lockerMasterObject.uv.getVerTml()+"',ve_date='"+lockerMasterObject.uv.getVerDate()+"',freeze_ind='"+lockerMasterObject.getFreezeInd()+"' where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and ve_user is not null and ve_tml is not null and ve_date is not null")==0)
                    throw new SQLException();
			    
			    //to check the status of "Freeze"
			    if(lockerMasterObject.getFreezeInd().equals("T"))
			    {
			        rs_fa = stmt_fa.executeQuery("select * from FreezedAccounts where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and to_date is null");
			        
			        if(!rs_fa.next())
			            if(stmt_fa.executeUpdate("insert into FreezedAccounts values('"+lockerMasterObject.getLockerAcType()+"',"+lockerMasterObject.getLockerAcNo()+",'"+lockerMasterObject.getReason()+"','"+Validations.convertYMD(lockerMasterObject.getTrnDate())+"',null,'F')")==0)
				            throw new SQLException();
			    }
			    else if(lockerMasterObject.getFreezeInd().equals("F"))
			    {
			        rs_fa = stmt_fa.executeQuery("select * from FreezedAccounts where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and to_date is null");
			        
			        if(rs_fa.next())
			        {
			            if(rs_fa.getString("from_date").equals(Validations.convertYMD(lockerMasterObject.getTrnDate())))
			            {
			                if(stmt_fa.executeUpdate("update FreezedAccounts set to_date='"+Validations.convertYMD(Validations.addDays(lockerMasterObject.getTrnDate(),0))+"' where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and to_date is null")==0)
				                throw new SQLException();
			            }
			            else
			            {
			                if(stmt_fa.executeUpdate("update FreezedAccounts set to_date='"+Validations.convertYMD(Validations.addDays(lockerMasterObject.getTrnDate(),-1))+"' where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and to_date is null")==0)
				                throw new SQLException();
			            }
			        } 
			    }
			    //end....Freeze
			    
			    //To check the Security Account - Deposit Account
			    Statement stmt = null,st_dep = null;
			    ResultSet res_dep = null;
			    
			    stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				st_dep = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				
				String dep_ac_type = null;
				int dep_ac_no = 0;
				
			    if(lockerMasterObject.getNoOfSecurities()>0)
			    {
			        for(int d = 0;d<lockerMasterObject.getNoOfSecurities();d++)
				    {
				        Vector deposit = lockerMasterObject.getDeposits();
				        String ac_type = null;
				        String ac_no = null;
				        
				        System.out.println("getDeposits = "+deposit.get(d));
				        
				        StringTokenizer st = new StringTokenizer(String.valueOf(deposit.get(d))," ");
				        
				        while(st.hasMoreTokens())
				        {
				            ac_type = st.nextToken();
				            ac_no = st.nextToken();
				            System.out.println("ac_type = "+ac_type);
				            System.out.println("ac_no = "+ac_no);
				        }
				        
				        res_dep = st_dep.executeQuery("select * from ODInterestDetails where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and sec_ac_type='"+ac_type+"' and sec_ac_no="+ac_no+" and from_date is not null and to_date='"+last_mat_date+"'");
				        
				        if(!res_dep.next())
				        {
				            PreparedStatement ps_dep=conn.prepareStatement("insert into ODInterestDetails values('"+lockerMasterObject.getLockerAcType()+"',"+lockerMasterObject.getLockerAcNo()+",'"+ac_type+"',"+ac_no+",null,null,null,null,null,'"+lockerMasterObject.getTrnDate()+"','"+lockerMasterObject.getRentUpto()+"')");
				            ps_dep.executeUpdate();
				        }
				    }
			        
			        int isThere = 0;
			        
			        String ac_type = null;
			        String ac_no = null;
			        String from_date = null;
					
			        res_dep = st_dep.executeQuery("select * from ODInterestDetails where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and from_date is not null and to_date='"+last_mat_date+"'");
    
			        while(res_dep.next())
			        {
			            isThere = 0;
			            
			            dep_ac_type = res_dep.getString("sec_ac_type");
			            dep_ac_no = res_dep.getInt("sec_ac_no");
			            from_date = res_dep.getString("from_date");
			            
			            for(int d = 0;d<lockerMasterObject.getNoOfSecurities();d++)
					    {
					        Vector deposit = lockerMasterObject.getDeposits();
					        
					        StringTokenizer st = new StringTokenizer(String.valueOf(deposit.get(d))," ");
					        
					        while(st.hasMoreTokens())
					        {
					            ac_type = st.nextToken();
					            ac_no = st.nextToken();
					        }
					        
					        if(dep_ac_type.equals(ac_type) && dep_ac_no==Integer.parseInt(ac_no))
					        {
					            isThere = 1;
					            break;
					        }
					    }
			            
			            System.out.println("dep_ac_type = "+dep_ac_type);
			            System.out.println("dep_ac_no = "+dep_ac_no);
			            System.out.println("last_mat_date = "+last_mat_date);
			            System.out.println("isThere = "+isThere);
        
			            if(isThere==0)
			            {
			                if(stmt.executeUpdate("update DepositMaster set ln_availed='F',ln_ac_type=null,ln_ac_no=null where ac_type='"+dep_ac_type+"' and ac_no="+dep_ac_no+" and ln_availed='T' and ln_ac_type='"+lockerMasterObject.getLockerAcType()+"' and ln_ac_no="+lockerMasterObject.getLockerAcNo()+"")==0)
		                        throw new SQLException();
			                
			                try
			                {
			                    if(from_date.equals(lockerMasterObject.getTrnDate()))
			                    {
			                        if(stmt.executeUpdate("update ODInterestDetails set to_date='"+Validations.addDays(lockerMasterObject.getTrnDate(),0)+"' where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and sec_ac_type='"+dep_ac_type+"' and sec_ac_no="+dep_ac_no+" and from_date is not null and to_date='"+last_mat_date+"'")==0)
			                            throw new SQLException();
			                    }
			                    else
			                    {
			                        if(stmt.executeUpdate("update ODInterestDetails set to_date='"+Validations.addDays(lockerMasterObject.getTrnDate(),-1)+"' where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and sec_ac_type='"+dep_ac_type+"' and sec_ac_no="+dep_ac_no+" and from_date is not null and to_date='"+last_mat_date+"'")==0)
			                            throw new SQLException();
			                    }
			                }
			                catch(DateFormatException dfe)
			                {
			                    dfe.printStackTrace();
			                }
			            }
			            else if(isThere==1)
			            {
			                ResultSet rs_dep = stmt.executeQuery("select * from DepositMaster where ac_type='"+dep_ac_type+"' and ac_no="+dep_ac_no+" and ln_availed='T' and ln_ac_type='"+lockerMasterObject.getLockerAcType()+"' and ln_ac_no="+lockerMasterObject.getLockerAcNo()+"");
			                
			                if(!rs_dep.next())
			                    if(stmt.executeUpdate("update DepositMaster set ln_availed='T',ln_ac_type='"+lockerMasterObject.getLockerAcType()+"',ln_ac_no="+lockerMasterObject.getLockerAcNo()+" where ac_type='"+dep_ac_type+"' and ac_no="+dep_ac_no+" and ln_availed='F'")==0)
			                        throw new SQLException();
			            }
        
			            System.out.println("res_dep.next().....end ");
			        }
			    }
			    else
			    {
			        res_dep = st_dep.executeQuery("select * from ODInterestDetails where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and from_date is not null and to_date='"+last_mat_date+"'");
			        
			        while(res_dep.next())
			        {
			            System.out.println("res_dep.next().....start ");
			            dep_ac_type = res_dep.getString("sec_ac_type");
			            dep_ac_no = res_dep.getInt("sec_ac_no");
        
			            System.out.println("no dep_ac_type = "+dep_ac_type);
			            System.out.println("no dep_ac_no = "+dep_ac_no);
			            System.out.println("no last_mat_date = "+last_mat_date);
			            
			            if(stmt.executeUpdate("update DepositMaster set ln_availed='F',ln_ac_type=null,ln_ac_no=null where ac_type='"+dep_ac_type+"' and ac_no="+dep_ac_no+" and ln_availed='T' and ln_ac_type='"+lockerMasterObject.getLockerAcType()+"' and ln_ac_no="+lockerMasterObject.getLockerAcNo()+"")==0)
		                    throw new SQLException();
        
		                try
		                {
		                    if(res_dep.getString("from_date").equals(lockerMasterObject.getTrnDate()))
		                    {
		                        if(stmt.executeUpdate("update ODInterestDetails set to_date='"+Validations.addDays(lockerMasterObject.getTrnDate(),0)+"' where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and sec_ac_type='"+dep_ac_type+"' and sec_ac_no="+dep_ac_no+" and from_date is not null and to_date='"+last_mat_date+"'")==0)
		                            throw new SQLException();
		                    }
		                    else
		                    {
		                        if(stmt.executeUpdate("update ODInterestDetails set to_date='"+Validations.addDays(lockerMasterObject.getTrnDate(),-1)+"' where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+" and sec_ac_type='"+dep_ac_type+"' and sec_ac_no="+dep_ac_no+" and from_date is not null and to_date='"+last_mat_date+"'")==0)
		                            throw new SQLException();
		                    }
		                }
		                catch(DateFormatException dfe)
		                {
		                    dfe.printStackTrace();
		                }
			        }
			    }
		        //end.......Deposit
		        
		        //To check JointHolders
		        if(lockerMasterObject.getJointCid()!=null)
				{
		            ResultSet rs_jt = stmt.executeQuery("select * from JointHolders where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"");
		            
					if(rs_jt.next())
					    if(stmt.executeUpdate("delete from JointHolders where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"")==0)
					        throw new SQLException();
					    
					int addrtype[]=lockerMasterObject.getAddr();
					int jcids[]=lockerMasterObject.getJointCid();
					int no_jt_hldr = jcids.length;
					
					System.out.println("2........"+no_jt_hldr);
					
					for(int i=0;i<no_jt_hldr;i++)
						if(stmt.executeUpdate("insert into JointHolders values('"+lockerMasterObject.getLockerAcType()+"','"+lockerMasterObject.getLockerAcNo()+"','"+jcids[i]+"','"+addrtype[i]+"')")==0)
						    throw new SQLException();
				}
		        else
		        {
		            ResultSet rs_jt = stmt.executeQuery("select * from JointHolders where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"");
		            
					if(rs_jt.next())
					    if(stmt.executeUpdate("delete from JointHolders where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"")==0)
					        throw new SQLException();
		        }
		        //end.....JointHolders
		
		        //To check Signature Instruction
		        if(lockerMasterObject.getSigObj()!=null)
				{
		            ResultSet rs_si = stmt.executeQuery("select * from SignatureInstruction where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"");
		            
                    if(rs_si.next())
                        if(stmt.executeUpdate("delete from SignatureInstruction where ac_type='"+lockerMasterObject.getLockerAcType()+"' and ac_no="+lockerMasterObject.getLockerAcNo()+"")==0)
                            throw new SQLException();
                        
					if(commonremote_local.storeSignatureDetails(lockerMasterObject.getSigObj(),lockerMasterObject.getLockerAcNo())==true)
					    return true;
				}
		        /////////
			}
		}
		catch(Exception ex)
		{
		    ex.printStackTrace();
		    obj_sessionContext.setRollbackOnly();
		}
		finally
		{
			try
			{		    	
				conn.close();
			}
			catch(Exception exception)
			{
			    exception.printStackTrace();
			}
		}
		
		return false;
	}
    ////////////
    
    /**
     * Checks whether the notice has been sent for the given date or not.
     */
    //ship.....11/08/2006
    public boolean checkReminderNotice(String ac_type,int ac_no,String date)
    {
        System.out.println("------------ Inside check Reminder Notice --------------");
		System.out.println("ac_type = "+ac_type);
        System.out.println("ac_no = "+ac_no);
        System.out.println("date = "+date);
        
		Connection  conn = null;
		Statement stmt_rn = null;
		ResultSet rs_rn = null;
		
        try
        {
            conn = getConnection();
			
			stmt_rn = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			if(conn==null)
				throw new SQLException();
			
			rs_rn = stmt_rn.executeQuery("select * from ReminderNotices where ac_type='"+ac_type+"' and ac_no="+ac_no+" and date='"+Validations.convertYMD(date)+"'");
			
			if(rs_rn.next())
			    return true;
        }
        catch(Exception exception)
		{
		    exception.printStackTrace();
		    obj_sessionContext.setRollbackOnly();
		}
        finally
		{
			try
			{		    	
				conn.close();
			}
			catch(Exception exception)
			{
			    exception.printStackTrace();
			}
		}
        
        return false;
    }
    
    /**
     * Insertion into ReminderNotice.
     */
    public int storeReminderNotice(String ac_type,int ac_no,String date,String notice_type)
    {
        System.out.println("------------ Inside store Reminder Notice --------------");
		System.out.println("ac_type = "+ac_type);
        System.out.println("ac_no = "+ac_no);
        System.out.println("date = "+date);
        System.out.println("notice type = "+notice_type);
        
		Connection  conn = null;
		Statement stmt_rn = null,stmt_module = null;
		ResultSet rs_module = null;
		int notice_no = 0;
		
        try
        {
            conn = getConnection();
			
			stmt_rn = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt_module = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			if(conn==null)
				throw new SQLException();
			
			rs_module = stmt_module.executeQuery("select lst_acc_no from Modules where modulecode='1009000'");
			
			if(rs_module.next())
			{
			    notice_no = rs_module.getInt("lst_acc_no")+1;
			    
			    if(stmt_rn.executeUpdate("insert into ReminderNotices values("+notice_no+",'"+Validations.convertYMD(date)+"','"+notice_type+"','"+ac_type+"',"+ac_no+")")==0)
			        throw new SQLException();
			    
			    if(stmt_module.executeUpdate("update Modules set lst_acc_no="+notice_no+" where modulecode='1009000'")==0)
			        throw new SQLException();
			}
        }
        catch(Exception exception)
		{
		    exception.printStackTrace();
		    obj_sessionContext.setRollbackOnly();
		}
        finally
		{
			try
			{		    	
				conn.close();
			}
			catch(Exception exception)
			{
			    exception.printStackTrace();
			}
		}
        
        return notice_no;
    }
    //////////////
    
    //ship.....14/02/2007
    public AccountObject getShareAccount(int cid)
    {
        AccountObject a = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try
        {
            conn = getConnection();   
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            a = new AccountObject();
            rs = stmt.executeQuery("select * from ShareMaster where cid="+cid+" and mem_cl_date is null");
            
            
            if(rs.next())
            {
                
                
                a.setCid(rs.getInt("cid"));
                a.setAcctype(rs.getString("ac_type"));
                a.setAccno(rs.getInt("ac_no"));
            }
            else
            {
            	a=null;
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            try
            {                
                conn.close();
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
        }
        
        return a;
    }
    ////////
    
    //ship......07/03/2007
    public LockerDetailsObject getLockerDetails(String locker_type,int locker_no) 
    {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        LockerDetailsObject loc = null;
        
        try
        {
            conn=getConnection();
            
            if(conn==null)
                throw new SQLException();
        
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           
            rs=stmt.executeQuery("select l.cab_no,l.key_no,l.locker_st,cm.master_key,lt.descrptn,lt.height,lt.length,lt.depth from Lockers l,CabMaster cm,LockerType lt where l.locker_ty='"+locker_type+"' and l.locker_no="+locker_no+" and l.cab_no=(select cab_no from Lockers where locker_ty='"+locker_type+"' and locker_no="+locker_no+") and l.cab_no=cm.cab_no and lt.locker_type='"+locker_type+"'");
                        
            if(rs.next())
            {
                loc = new LockerDetailsObject();
                
                loc.setCabNo(rs.getInt("l.cab_no"));
                loc.setKeyNo(rs.getInt("l.key_no"));
                loc.setLockerStatus(rs.getString("l.locker_st"));
                loc.setMasterKey(rs.getString("cm.master_key"));
                loc.setDescription(rs.getString("lt.descrptn"));
                loc.setLockerHeight(rs.getString("lt.height"));
                loc.setLockerLength(rs.getString("lt.length"));
                loc.setLockerDepth(rs.getString("lt.depth"));
            }
        }
        catch(SQLException sqlexception)
        {
            sqlexception.printStackTrace();
            obj_sessionContext.setRollbackOnly();
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        return loc;
    }
    //////////////
}