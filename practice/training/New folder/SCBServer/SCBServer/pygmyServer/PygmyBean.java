package pygmyServer;
 
import java.rmi.RemoteException;
import java.security.SignatureException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



import masterObject.pygmyDeposit.AgentMasterObject;
import masterObject.pygmyDeposit.PygmyMasterObject;
import masterObject.pygmyDeposit.PygmyRateObject;
import masterObject.pygmyDeposit.PygmyTransactionObject;
import masterObject.pygmyDeposit.QuarterInterestObject;
import client.HomeFactory;

import commonServer.CommonHome;
import commonServer.CommonLocal;
import commonServer.CommonLocalHome;
import commonServer.CommonRemote;
import commonServer.GLTransObject;

import exceptions.AccountNotFoundException;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import exceptions.JointAccountNotFound;
import exceptions.NomineeNotCreatedException;
import exceptions.NotValidJointHolder;
import exceptions.RecordsNotFoundException;
import exceptions.ScrollNotFoundException;
import masterObject.frontCounter.AccountTransObject;
import masterObject.frontCounter.PayOrderObject;
import masterObject.general.AccountObject;
import masterObject.general.ModuleAdminObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import general.Validations;
import masterObject.generalLedger.GLObject;

      
public class PygmyBean implements SessionBean
{
    private CommonLocal common;
    private CommonLocalHome commonLocalHome;
    transient CommonHome commonhome;
    private CommonRemote commonremote;
    private DataSource ds = null;
    private SessionContext sessionContext;
    private String g_pgacctype,g_user,g_tml;
    private int g_pgaccno;
    
    /**
     * H.R.B 
     * 
     * @return 1 if successfully inserted.
     *         0 if unable to insert.
     * 	      -1 if main Cashier has Closed.
     */
    public int insertintoPygmyTran(String ac_ty,int ac_no,String trn_date,double trn_amt,String trn_type,String cd_ind,String de_tml,String de_user,String de_date,String ve_user,String ve_tml,String ve_date)
	{
    	
    	System.out.println("Inside Pygmy Transactionnnnnnnnnnnnnn"+ac_ty);
    	Connection conn=null;
    	PreparedStatement pst=null;
    	Statement st=null,st1=null,st2=null;
    	ResultSet rs=null,rs1=null;
    	String v_main_cashier = null;
    	try{
    		conn=getConnection();
    		st=conn.createStatement();
    		st1=conn.createStatement();
    		st2=conn.createStatement();
    		rs1=st2.executeQuery("select rec_type from Currency_Stock where tml_no=(select tml_code from Terminals where tml_type='M')");
            if(rs1.next())
                v_main_cashier=rs1.getString(1);
                
            if(v_main_cashier.equalsIgnoreCase("o"))
            {
    		rs=st.executeQuery("select trn_seq,cl_bal from PygmyTransaction where ac_type='"+ac_ty+"' and ac_no="+ac_no+" and ref_ind=3 and ve_tml is not null order by trn_seq desc limit 1");
    		rs.next();
    		
    		int trn_seq=rs.getInt(1);
    		double cl_bal=rs.getDouble(2);
    		
    		pst=conn.prepareStatement("insert into PygmyTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    		
    		pst.setString(1,ac_ty);
    		pst.setInt(2,ac_no);
    		pst.setInt(3,++trn_seq);
    		pst.setString(4,trn_date);
    		pst.setString(5,"T");
    		pst.setDouble(6,trn_amt);
    		pst.setDouble(7,trn_amt);//amt_paid
    		pst.setString(8,trn_type);
    		pst.setString(9,de_tml);//trn_source
    		pst.setString(10,cd_ind);
    		if(cd_ind.equalsIgnoreCase("C"))
    		pst.setDouble(11,(cl_bal+trn_amt));
    		else if(cd_ind.equalsIgnoreCase("D"))
    		pst.setDouble(11,(cl_bal-trn_amt));	
    		pst.setInt(12,0);
    		pst.setString(13,null);
    		pst.setString(14,null);
    		pst.setString(15,null);
    		pst.setDouble(16,0.00);
    		pst.setDouble(17,0.00);
    		
    		pst.setString(18,de_tml);
    		pst.setString(19,de_user);
    		pst.setString(20,de_date);
    		pst.setString(21,ve_tml);
    		pst.setString(22,ve_user);
    		pst.setString(23,ve_date);
    		pst.setInt(24,3);
    		
    		if(pst.executeUpdate()!=0)
    		if(st1.executeUpdate("update PygmyMaster set lst_trn_seq="+trn_seq+" where ac_type='"+ac_ty+"' and ac_no="+ac_no+" and close_date is null and ve_tml is not null")!=0)	
    			return 1;
    		else
    			return 0;
            }
            return -1; // if main cashier is closed
    	}catch(SQLException se){
    		sessionContext.setRollbackOnly();
    		se.printStackTrace();}
    	finally{
    		try{
    			conn.close();
    		}catch(SQLException se){se.printStackTrace();}
    	}
    	return 0;
	}
   
    
    public int updateAgentMaster(AgentMasterObject amo){
    	Connection conn=null;
    	PreparedStatement pst=null;
    	Statement st=null;
    	    
    	int r=0;
    	try{
    	      conn=getConnection();
    	      st=conn.createStatement();
    	      System.out.println("INSIDE AGENT MASTER UPDATION CLOSE IND=="+amo.getCloseIndicator());
              System.out.println("test equals===="+amo.getCloseIndicator().startsWith("c"));
    	      /*if(st.executeUpdate("insert into AgentMasterLog select * from AgentMaster where ac_type='"+amo.getAgentType()+"' and ac_no="+amo.getAgentNo()+"")!=0)
    	      	r=1; //inserted record
    	      else
    	      	r=-1; //query failed
    	      *///commented by sunitha on 03/11/2009
    	      
    	    	  pst=conn.prepareStatement("update AgentMaster set cid=?,appt_date=?,p_ac_type=?,p_ac_no=?,jt_ac_type=?,jt_ac_no=?,close_date=?,close_ind=?,intr_ac_type=?,intr_ac_no=? where ac_type='"+amo.getAgentType()+"' and ac_no="+amo.getAgentNo()+"");
        	      
        	         pst.setInt(1,amo.getCid());
        	      	pst.setString(2,amo.getAppointDate());
        	      	pst.setString(3,amo.getAccType());
        	      	pst.setInt(4,amo.getAccNo());
        	      	pst.setString(5,amo.getJointAccType());
        	      	pst.setInt(6,amo.getJointAccNo());
        	      	//pst.setInt(7,amo.getSecurityCID());
        	      	pst.setString(7,amo.getCloseDate());
                    if(amo.getCloseIndicator().startsWith("c")||amo.getCloseIndicator().startsWith("C"))
                        pst.setInt(8,1);
                    else if(amo.getCloseIndicator().startsWith("o")||amo.getCloseIndicator().startsWith("O"))
                        pst.setInt(8,0);   
                    pst.setString(9,amo.getIntrAccType());
                    pst.setInt(10,amo.getIntrAccNo());
    	      	if(pst.executeUpdate()!=0)
    	      		r=2; //Both Insertion & Updation Successfully done
    	      	else
    	      		r=-2; //AgentMasterLog Entry happend, But Updation in AgentMaster not Happend!
    	      
    	      
    	      if(r!=0)
    	        return r;
    	      else
    	      	return r;
    	      
     	}catch(SQLException se){
     		sessionContext.setRollbackOnly();
     		se.printStackTrace();}
    	finally{
    		try{
    			conn.close();
    		}catch(SQLException se){se.printStackTrace();}
    	}
    	return r; //Error!
    	
    }
    
    
    /**
     *  H.R.B
     *  
     *  used to get the PAG - Joint Account Holder's CIDs and Addres Types of Joint Account Holder
     * @return AgnetMasterObject 
      */
    
    public AgentMasterObject getPAGJointAccountCids(String jt_acc_ty,int jt_acc_no){
    	Connection conn=null;
    	Statement stmt=null;
    	ResultSet rs=null;
        AgentMasterObject agtobj=null; 
    	int[] cid,addr_type;
        try{
    	      conn=getConnection();
    	      stmt=conn.createStatement();
    	      System.out.println("Inside getPAGjtAcc ....");
    	      System.out.println("jt Ac Type: "+jt_acc_ty);
    	      System.out.println("jt Acc No: "+jt_acc_no);
    	      rs=stmt.executeQuery("select cid,addr_type from JointHolders where ac_type='"+jt_acc_ty+"' and ac_no="+jt_acc_no+"");
    		  
    	      rs.next();
    	      
    	      System.out.println("@@ CID:"+rs.getInt(1));
    	          	      
    	      rs.last();
    	      
    	     
    	      cid=new int[rs.getRow()];
    	      addr_type=new int[rs.getRow()];
    	     
    	      rs.beforeFirst();
    	      int r=0;
    	      while(rs.next()){
    	      cid[r]=rs.getInt("cid");
    	      addr_type[r]=rs.getInt("addr_type");
    	      
    	      r++;
    	      }
    	      agtobj=new AgentMasterObject();
    	      agtobj.setJt_cids(cid);
    	      agtobj.setJt_addr_types(addr_type);
    	      System.out.println("m in bean====agtobj1111======"+agtobj);
    	      return agtobj;
    	      
    	}catch(SQLException se){se.printStackTrace();}
    	
    	finally{
    		try{
    			conn.close();
    		}catch(SQLException e){e.printStackTrace();}
    	}
    	System.out.println("m in bean====agtobj======"+agtobj);
    	return agtobj;
    }
    
    
    /**
     * H.R.B
     * 
     * used to get UnVerified Pygmy Account Nos.
     * @return
     * @throws RecordsNotFoundException
     */
    public PygmyMasterObject[] getUnVerifiedPDNo(){
    	PygmyMasterObject[] pdObj=null;
    	Connection connection=null;
    	Statement st=null;
    	ResultSet rs=null;
    	try{
    		connection=getConnection();
    		st=connection.createStatement();
    		rs=st.executeQuery("select pm.ac_no,concat_ws('',fname,mname,lname) as name from PygmyMaster pm,CustomerMaster cm where cm.cid=pm.cid and pm.ve_tml is null");
            rs.next();
            rs.last();
            pdObj=new PygmyMasterObject[rs.getRow()];
            
            rs.beforeFirst();
            int r=0;
            while(rs.next())
            {
               pdObj[r]=new PygmyMasterObject();
               
               pdObj[r].setAccNo(rs.getInt("ac_no"));	
               pdObj[r].setName(rs.getString("name"));
               r++;
            }
    		
    		return pdObj;
    	}catch(SQLException se){se.printStackTrace();}
    	
        finally{
        	try{
        		connection.close();
        	}catch(SQLException e){e.printStackTrace();}
        }
        return pdObj;
    }
    
    public PygmyMasterObject[] getUnVerifiedPDClosureAcc(String curdate){
    	PygmyMasterObject[] pdObj=null;
    	Connection connection=null;
    	Statement st=null;
    	ResultSet rs=null;
    	try{
    		connection=getConnection();
    		st=connection.createStatement();
    		rs=st.executeQuery("select distinct pt.ac_no,concat_ws('',fname,mname,lname) as name from PygmyTransaction pt,CustomerMaster cm,PygmyMaster pm where pm.cid=cm.cid and pm.ac_no=pt.ac_no and pt.ve_tml is null and pt.trn_date='"+curdate+"'");
            if(rs.next())
           {
            rs.last();
            pdObj=new PygmyMasterObject[rs.getRow()];
            
            rs.beforeFirst();
            int r=0;
            while(rs.next())
            {
               pdObj[r]=new PygmyMasterObject();
               
               pdObj[r].setAccNo(rs.getInt("ac_no"));	
               pdObj[r].setName(rs.getString("name"));
               r++;
            }
           }	
    		return pdObj;
    	}catch(SQLException se){se.printStackTrace();}
    	
        finally{
        	try{
        		connection.close();
        	}catch(SQLException e){e.printStackTrace();}
        }
        System.out.println("unver acc length==="+pdObj.length);
        return pdObj;
        
    }
    
    
    
    
    /**
     * H.R.B
     * 
     * used to get Unverified Agent's Acc No & Their Names
     * @return Array of AgentMasterObject.  Unverified Agent Details.
     * @throws RemoteException
     */
    public AgentMasterObject[] getUnVerifiedAgtNo()throws RecordsNotFoundException
    {
    	AgentMasterObject[] agtobj=null;
    	Connection conn=null;
    	ResultSet rs=null;
    	Statement stmt=null;
    	int count=0;
    	try{
    		conn=getConnection();
    		stmt=conn.createStatement();
    		
    		rs=stmt.executeQuery("select am.ac_no,concat_ws('',cm.fname,cm.mname,cm.lname) as name from AgentMaster am,CustomerMaster cm where cm.cid=am.cid and am.ve_tml is null");
    		rs.next();
    		rs.last();
    		count=rs.getRow();
    		if(count==0)
    			throw new RecordsNotFoundException();
    		agtobj=new AgentMasterObject[count];
    		
    		rs.beforeFirst();
    		int r=0;
    		while(rs.next())
    		{
    		 agtobj[r]=new AgentMasterObject();
    		 agtobj[r].setAgentNo(rs.getInt(1));
    		 agtobj[r].setName(rs.getString(2));
    		 r++;
    		}
    		return agtobj;
    	}catch(SQLException se){se.printStackTrace();}
    	 catch(RecordsNotFoundException e){System.out.println("Unverified Agt Nos Not Available");}
      
    	 finally{
            try{
                conn.close();
            }catch(Exception e){e.printStackTrace();}
        }
    	 
    	 return agtobj;
    }
    
    
    //Ris
    public PygmyRateObject[] getPygmyRate()
    {
        PygmyRateObject[] po=null;
        Connection conn = null;
        Statement stmt=null;
        
        try {
            conn=getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //ResultSet rs = stmt.executeQuery("select pr.*,moduleabbr from PygmyRate pr,Modules where modulecode=pr.pg_type");
            ResultSet rs=stmt.executeQuery("select pr.*,moduleabbr from PygmyRate pr,Modules where modulecode=pr.ac_type order by to_date asc");
            
            rs.last();
            po = new PygmyRateObject[rs.getRow()];
            rs.beforeFirst();
            int i=0;
            while(rs.next()){
                po[i]=new PygmyRateObject();
                po[i].setACType(rs.getString("moduleabbr"));
                po[i].setFromDate(rs.getString("fr_date"));
                po[i].setToDate(rs.getString("to_date"));
                po[i].setPeriodFrom(rs.getInt("prd_fr"));
                po[i].setPeriodTo(rs.getInt("prd_to"));
                po[i].setIntRate(rs.getDouble("int_rate"));
                po[i].setDeUser(rs.getString("de_user"));
                po[i].setDeTml(rs.getString("de_tml"));
                po[i].setDeDate(rs.getString("de_date"));
                i++;
            }
        } catch (SQLException e){e.printStackTrace();}
        finally{
            try{
                conn.close();
            }catch(Exception e){e.printStackTrace();}
        }
        return po;
    }
    
    public int modifyPygmyRate(String actype,int prdfrm,int prdto,double intrate,int option) throws SQLException
    {
        Connection conn=null;
        Statement stmt=null;
      
        try{
            conn=getConnection();
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(option==1)
                stmt.executeQuery("insert into PygmyRate values('1006001',frdt,todt,prdfr,prdto,intrate,detml,deuser,de_date,moduleabbr)");
            /* else if(option==2)
             stmt.executeQuery("delete from PygmyRate where ");*/	
            
            
        }catch(Exception se){se.printStackTrace();}
        finally{
            try{
                conn.close();
            }catch(Exception e){e.printStackTrace();}
        }
        return 1;
    }
    
    
    //Ris
    public int createPygmy(ModuleObject mo,String detml,String deuser,int type)
    {
        Connection conn=null;
        String modulecode=null;
        CommonHome commonhome;
        CommonRemote commonremote;
        try {
            conn=getConnection();
            System.out.println("moduleabbr update=="+mo.getModuleAbbrv());
            System.out.println("Penalty rate==="+mo.getPenaltyRate());
          
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            if(type==-1){
                rs=stmt.executeQuery("select max(modulecode)+1 from Modules where modulecode like '1006%'");
                rs.next();
                //return stmt.executeUpdate("insert into Modules(modulecode,moduledesc,moduleabbr,lst_acc_no,min_bal,min_period,other_prop,max_amt,de_tml,de_user,de_dt_time) values("+rs.getInt(1)+",'"+mo.getModuleDesc()+"','"+mo.getModuleAbbrv()+"',0,"+mo.getMinBal()+","+mo.getMinPeriod()+",'"+mo.getProperties()+"',"+mo.getMaxAmount()+",'"+detml+"','"+deuser+"',date_format(sysdate(),'%d/%m/%Y %r'))");
                return stmt.executeUpdate("insert into Modules(modulecode,moduledesc,moduleabbr,lst_acc_no,max_renewal_count,min_bal,min_period,other_prop,lnk_shares,max_amt,intfrom_day,intto_day,trns_per_mnth,lst_intdt,int_rate,std_inst,de_tml,de_user,de_dt_time,max_renewal_days,renewal_int_rate,penalty_rate,pass_bk_lines,comm_rate_for_amt,max_comm_rate,lst_voucher_scroll_no,chq_validity_period,min_amt_cheque,min_amt_clg,max_amt_cheque,max_amt_clg,top_margin,lines_per_page,bottom_margin,last_trf_scroll_no,lst_rct_no,lst_voucher_no,inspection_period,ln_modulecode) values("+rs.getInt(1)+",'"+mo.getModuleDesc()+"','"+mo.getModuleAbbrv()+"',0,null,"+mo.getMinBal()+","+mo.getMinPeriod()+",'"+mo.getProperties()+"',null,"+mo.getMaxAmount()+",null,null,0,null,null,"+mo.getPenaltyRate()+",'"+detml+"','"+deuser+"',date_format(sysdate(),'%d/%m/%Y %r'),null,null,"+mo.getPenaltyRate()+",null,null,null,null,null,null,null,null,null,null,null,null,0,null,0,null,1008006)");
            }
            else if(type==-2)
            {//to delete pygmytype
                rs = stmt.executeQuery("select count(ac_type) from PygmyMaster where ac_type='"+mo.getModuleCode()+"' and status='O'");
                rs.next();
                if(rs.getInt(1)>0)
                    return rs.getInt(1);
                else{	
                    System.out.println("i am in delete else"); 
                    stmt.executeUpdate("delete from Modules where modulecode ='"+mo.getModuleCode()+"'");
                    //stmt.executeUpdate("delete from AgentMaster where agt_type='"+mo.getModuleCode()+"'");
                    return 0;
                }
            }
            else {//for updating Agent details..
                try{
                    commonhome=(CommonHome)HomeFactory.getFactory().lookUpHome("COMMONWEB");
            		commonremote=commonhome.create();
            		modulecode=commonremote.getModulecode(mo.getModuleAbbrv());
                   stmt.executeUpdate("Update Modules set moduleabbr='"+mo.getModuleAbbrv()+"',moduledesc='"+mo.getModuleDesc()+"',min_bal="+mo.getMinBal()+",min_period="+mo.getMinPeriod()+",other_prop='"+mo.getProperties()+"',max_amt="+mo.getMaxAmount()+",penalty_rate="+mo.getPenaltyRate()+" where modulecode='"+modulecode+"'");
                   return 1;
                }catch(Exception ex){ex.printStackTrace();}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try{
                conn.close();
            }catch(Exception e){e.printStackTrace();}
        }
        
        return -1;
    }
    
    
    //ris
    public int createAgent(String agttype,String agtdesc,String min_period,String detml,String deuser,int opt)
    {
        System.out.println("Inside Create Agent....");
        System.out.println("Agent Type: "+agttype);
        System.out.println("Agent Desc: "+agtdesc);
        System.out.println("De Tml: "+detml);
        System.out.println("De user: "+deuser);
        System.out.println("Option: "+opt);
        int minimum_period=0;
        
        Connection conn=null;
     
        try
        {
            conn=getConnection();
            Statement stmt=conn.createStatement();
            ResultSet rs;
            minimum_period=Integer.parseInt(min_period);
            if(opt==-1){//for adding new agent
                rs=stmt.executeQuery("select max(modulecode)+1 from Modules where modulecode like '1013%'");
                rs.next();
                return stmt.executeUpdate("insert into Modules(modulecode,moduledesc,moduleabbr,lst_acc_no,max_renewal_count,min_bal,min_period,other_prop,lnk_shares,max_amt,intfrom_day,intto_day,trns_per_mnth,lst_intdt,int_rate,std_inst,de_tml,de_user,de_dt_time,max_renewal_days,renewal_int_rate,penalty_rate,pass_bk_lines,comm_rate_for_amt,max_comm_rate,lst_voucher_scroll_no,chq_validity_period,min_amt_cheque,min_amt_clg,max_amt_cheque,max_amt_clg,top_margin,lines_per_page,bottom_margin,last_trf_scroll_no,lst_rct_no,lst_voucher_no,inspection_period,ln_modulecode) values("+rs.getInt(1)+",'"+agtdesc+"','"+agttype+"',0,null,null,"+minimum_period+",null,null,null,null,null,null,null,null,null,'"+detml+"','"+deuser+"',date_format(sysdate(),'%d/%m/%Y %r'),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null)");
            }
            else if(opt==-2) {//to delete agent
                rs = stmt.executeQuery("select count(ac_type) from AgentMaster where ac_type='"+agttype+"' and close_ind=0");
                rs.next();
                if(rs.getInt(1)>0)
                    return rs.getInt(1);
                else{	
                    stmt.executeUpdate("delete from Modules where modulecode ='"+agttype+"'");
                    stmt.executeUpdate("delete from AgentMaster where ac_type='"+agttype+"'");
                    return 0;
                }
            }
            else {//for updating Agent details..
                
                stmt.executeUpdate("Update Modules set modulecode='"+agttype+"',moduledesc='"+agtdesc+"',min_period="+minimum_period+" where modulecode='"+agttype+"'");
                return 1;
            }
        }catch(SQLException sql){sql.printStackTrace();}
        finally{
            try{
                conn.close();
            }catch(Exception e){e.printStackTrace();}
        }
        return -1;
    }
    
    
    public int agentUpdate(String agttype,int agentno,String curdate,int type)
    {		
        Statement stmt;
        Connection connection = null;
        try
        {
            common = commonLocalHome.create();
            connection = getConnection();
            if(type==0)
            {
                stmt=connection.createStatement();
                stmt.executeUpdate("Update AgentMaster set close_ind=19 where ac_type='"+agttype+"' and ac_no="+agentno);
            }
            else if(type==1)
            {
                stmt=connection.createStatement();
                stmt.executeUpdate("Update AgentMaster set close_ind=1,close_date='"+curdate+"' where ac_type='"+agttype+"' and ac_no="+agentno+" and close_ind=19 ");				
            }
            return 1;
        }catch(SQLException e){
            e.printStackTrace();
            sessionContext.setRollbackOnly();
        } catch (CreateException e) {
            e.printStackTrace();
        }
        finally{
            try {
                common = null;
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return 0;
    }
    
    /**
     * Called from Stateless Session Bean..
     * @return
     */
    
    public double calculateInterest(String pyg_acc_type,int pyg_acc_no,String curdate)
    {
        System.out.println("Interest Calculation...");
        double intamount=0;
        int days[]={31,28,31,30,31,30,31,31,30,31,30,31};
        Connection connection = null;
        ResultSet rs3=null;
        ResultSet rs2=null,rs4=null;
        try
        {
            common = commonLocalHome.create();
            connection = getConnection();
            Statement stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement stat = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement stmnt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement smnt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement stmt4=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            String open_date="";
            String date2="";
            //String close_date=common.getSysDate();
            String close_date=curdate;
            System.out.println("newly modified====bean");
            System.out.println("close date====curdate===="+curdate);
            System.out.println("Acc Type "+pyg_acc_type);
            System.out.println("Acc No "+pyg_acc_no);
            
             rs2 = stmnt.executeQuery("select open_date from PygmyMaster where ac_type='"+pyg_acc_type+"' and ac_no="+pyg_acc_no+"");
            rs2.next();
            open_date=rs2.getString(1);
           // int months=Validations.noOfMonths(open_date,close_date);
            int months=common.getNoOfMonths(open_date,close_date);
            System.out.println("Months : "+months);
            
            StringTokenizer st=new StringTokenizer(open_date,"/");
            byte d=Byte.parseByte(st.nextToken());
            byte m=Byte.parseByte(st.nextToken());
            short y=Short.parseShort(st.nextToken());
            
            date2=Validations.checkDate(days[m-1]+"/"+m+"/"+y);
            System.out.println("initial date2==="+date2);
            double minbal[]=new double[months+1];

                     
            int i=0;
            double sum=0;
            //int noofdays=Validations.dayCompare(open_date,date2);
            int noofdays=common.getDaysFromTwoDate(open_date,date2);
            System.out.println("No of days : "+noofdays);
            ResultSet rs1 = stat.executeQuery("select int_rate from PygmyRate where '"+Validations.convertYMD(curdate)+"' between  concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1)) and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1)) and "+months+" between prd_fr and prd_to and ac_type='"+pyg_acc_type+"' ");
            rs1.next();
           			
            System.out.println("Int Rate: "+rs1.getDouble("int_rate"));
            double int_rate=rs1.getDouble("int_rate");
            
            System.out.println("DayCompare: "+common.getDaysFromTwoDate(date2,close_date));
            while(common.getDaysFromTwoDate(date2,close_date)>=0)
            {
                try
                {   System.out.println("came inside..."); 
                    System.out.println("Open Date => "+open_date);
                    System.out.println("Date2     => "+date2);
                    System.out.println("diff of two dates==="+common.getDaysFromTwoDate(date2,close_date));
                   // rs3 = smnt.executeQuery("select min(cl_bal) from PygmyTransaction where ac_type='"+pyg_acc_type+"' and ac_no="+pyg_acc_no+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(open_date)+"' and '"+Validations.convertYMD(date2)+"' group by concat(mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)))");
                  //rs3 = smnt.executeQuery("select min(cl_bal) from PygmyTransaction where ac_type='"+pyg_acc_type+"' and ac_no="+pyg_acc_no+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(open_date)+"' and '"+Validations.convertYMD(date2)+"' group by  ac_no");
                    rs3=smnt.executeQuery("select cl_bal from PygmyTransaction where ac_type='"+pyg_acc_type+"' and ac_no="+pyg_acc_no+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(open_date)+"' and '"+Validations.convertYMD(date2)+"'  order by trn_seq desc limit 1");
                    if(rs3.next())
                    {
                        System.out.println("min Bal =>  "+rs3.getDouble(1));
                        minbal[i]=rs3.getDouble(1);
                        System.out.println("Min Bal:  "+minbal[0]);
                    }
                    else
                    {
                        rs3=stmt.executeQuery("select cl_bal from PygmyTransaction where ac_type='"+pyg_acc_type+"' and ac_no="+pyg_acc_no+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(open_date)+"' order by trn_seq desc limit 1");
                       if(rs3.next())
                       {
                        minbal[i]=rs3.getDouble(1);
                        System.out.println("Min Bal=>:  "+minbal[0]);
                       }
                    }
                }
                catch(Exception b)
                {
                    b.printStackTrace();                     
                  
                }
                
                open_date=Validations.addDays(date2,1);
                m++;
                if(m==13)
                {
                    m=(byte)(m%12);
                    y++;
                }
                date2=Validations.checkDate(days[m-1]+"/"+m+"/"+y);
                /*if(i==0 && d>1)
                {   
                    System.out.println("IntAmount: "+intamount);
                    System.out.println("MinBal: "+minbal[0]);
                    System.out.println("NoOf Days: "+noofdays);
                    System.out.println("Int Rate: "+int_rate);
                    intamount=intamount+minbal[0];
                    intamount=(intamount*rs1.getDouble(1)*noofdays)/36500;
                    System.out.println("int amount after calc==="+intamount);
                    sum=sum+minbal[i];
                }*/
            
               
                    sum=sum+minbal[i];
                    System.out.println("summmmmmmmmm"+sum);
               
                System.out.println("Iteration "+i);
                i++;
                
            }
            if(common.getDaysFromTwoDate(date2,close_date)<0)
            {
                rs4=stmt4.executeQuery("select cl_bal from PygmyTransaction where ac_type='"+pyg_acc_type+"' and ac_no="+pyg_acc_no+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(close_date)+"' order by trn_seq desc limit 1");
                if(rs4.next())
                {
                    System.out.println("last closing balance==="+rs4.getDouble(1));
                    sum=sum+rs4.getDouble(1);          
                }
            }
           
            System.out.println("SUM => "+sum);
           
            intamount=Math.round(intamount+((sum*rs1.getDouble(1))/1200));
            System.out.println("Int Amount => "+intamount);
            System.out.println("end of interest Calculation..");
            return intamount;
        }catch(Exception e){e.printStackTrace();}
        finally{
            common = null;
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        System.out.println("Int Amount: "+intamount);
        System.out.println("end of interest Calculation..");
        return intamount;
        
    }
    
    
    
    /**
     *   NOTE => called from stateful Session Bean
     * @return
     */
    /*public double calculateInterest()
    {
        System.out.println("Interest Calculation...");
        double intamount=0;
        int days[]={31,28,31,30,31,30,31,31,30,31,30,31};
        Connection connection = null;
        try
        {
            common = commonLocalHome.create();
            connection = getConnection();
            Statement stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement stat = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement stmnt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement smnt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            String open_date="";
            String date2="";
            String close_date=common.getSysDate();
            
            System.out.println("Acc Type "+g_pgacctype);
            System.out.println("Acc No "+g_pgaccno);
            
            ResultSet rs2 = stmnt.executeQuery("select open_date from PygmyMaster where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);
            rs2.next();
            open_date=rs2.getString(1);
            int months=Validations.noOfMonths(open_date,close_date);
            System.out.println("Months : "+months);
            
            StringTokenizer st=new StringTokenizer(open_date,"/");
            byte d=Byte.parseByte(st.nextToken());
            byte m=Byte.parseByte(st.nextToken());
            short y=Short.parseShort(st.nextToken());
            
            date2=Validations.checkDate(days[m-1]+"/"+m+"/"+y);
            double minbal[]=new double[months+1];

                     
            int i=0;
            double sum=0;
            //int noofdays=Validations.dayCompare(open_date,date2);
            int noofdays=common.getDaysFromTwoDate(open_date,date2);
            
            System.out.println("No of days: "+noofdays);
            ResultSet rs1 = stat.executeQuery("select int_rate from PygmyRate where '"+Validations.convertYMD(common.getSysDate())+"' between  concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1)) and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1)) and "+months+" between prd_fr and prd_to and ac_type='"+g_pgacctype+"' ");
            rs1.next();
            ResultSet rs3;			
            System.out.println("Int Rate: "+rs1.getDouble("int_rate"));
            double int_rate=rs1.getDouble("int_rate");
            
            System.out.println("DayCompare: "+common.getDaysFromTwoDate(date2,close_date));
            while(common.getDaysFromTwoDate(date2,close_date)>0)
            {
                try
                {
                    rs3 = smnt.executeQuery("select min(cl_bal) from PygmyTransaction where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(open_date)+"' and '"+Validations.convertYMD(date2)+"' group by concat(mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)))");
                    rs3.next();
                    minbal[i]=rs3.getDouble(1);
                    System.out.println("Min Bal:  "+minbal[0]);
                }
                catch(Exception b){
                    try
                    {
                        rs3=stmt.executeQuery("select cl_bal from PygmyTransaction where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(open_date)+"' order by trn_seq desc limit 1");
                        rs3.next();
                        minbal[i]=rs3.getDouble(1);
                        System.out.println("Min Bal=>:  "+minbal[0]);
                    }catch(SQLException sq){minbal[i]=0;}
                }
                
                open_date=Validations.addDays(date2,1);
                m++;
                if(m==13)
                {
                    m=(byte)(m%12);
                    y++;
                }
                date2=Validations.checkDate(days[m-1]+"/"+m+"/"+y);
                
                if(i==0 && d>1)
                {   
                    System.out.println("IntAmount: "+intamount);
                    System.out.println("MinBal: "+minbal[0]);
                    System.out.println("NoOf Days: "+noofdays);
                    System.out.println("Int Rate: "+int_rate);
                    intamount=intamount+minbal[0];
                    intamount=(intamount*rs1.getDouble(1)*noofdays)/36500;
                }
                else 
                    sum=sum+minbal[i];				
                System.out.println("Iteration "+i);
                i++;
                
            }
            intamount=Math.round(intamount+((sum*rs1.getDouble(1))/1200));
            return intamount;
        }catch(Exception e){e.printStackTrace();}
        finally{
            common = null;
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        System.out.println("Int Amount: "+intamount);
        System.out.println("end of interest Calculation..");
        return intamount;
        
    }
*/    
    /**
     * used to get The Matured Period(No of Months) of the Pygmy Account. 
     * @param 
     * @return
     */
    
    public int getMaturedPeriodPD()
    {
    	Connection conn=null;
    	Statement stmt=null;
    	ResultSet rs=null;
    	int period=0;
    	try{
    		conn=getConnection();
    		stmt=conn.createStatement();
    		rs=stmt.executeQuery("select prd_to from PygmyRate order by prd_to asc limit 1");
    		rs.next();
    		period=rs.getInt(1);
    		
    		return period;
    	}catch(SQLException e){e.printStackTrace();}
        finally{
        	try{
        		conn.close();
        	}catch(SQLException e){e.printStackTrace();}
        }
       return period;
    }
    
    
    
    /**
     * This method is used to calculate the penalty amount for the non interest paid account holder.
     * Process: It will get the 'penalty interest rate' from the modules table. 
     * Then it will calculate the Penalty amount. 
     * Formula: (withdrawal amount* penalty interest)/100;
     * @param withAmt - with drawal amount which has enterd by the user.
     * @return  penalty amount , which should be subtracted from the withdrawal amount.
     */
    public double getPenaltyAmount(double withAmt)
    {
    	System.out.println("Calculating Penalty amount for non interest paid Pygmy Account...");
    	System.out.println("withdrawal amount passed===="+withAmt);
        double penalty=0;
    	Connection conn=null;
    	ResultSet rs=null;
    	Statement stmt=null;
    	float penaltyRate=0;
    	try{
    		conn=getConnection();
    		stmt=conn.createStatement();
    		rs=stmt.executeQuery("select * from Modules where modulecode="+g_pgacctype+"");
    		if(rs.next())
    			penaltyRate=rs.getFloat("penalty_rate");
    		 		
    		penalty=(withAmt*penaltyRate)/100;
    		System.out.println("penalty calculated===="+penalty);
    		
    		
    	}catch(SQLException se){se.printStackTrace();}
    	finally{
    		try{
    			conn.close();
    		}catch(SQLException e1){e1.printStackTrace();}
    	}
     return Math.round(penalty);    
    }
    
    /**
     * This method is used to get the Interest details of Pygmy Account Holder.
     * Process:  Pygmy Account Type(modulecode - 1006001) and Account Number 
     * will be taken when focus lost of the 'Pygmy Account No' text field component.
     * Then it will calculate the no of months between Pygmy Account open date and
     * current date.Depends on the no of months it will take the Interest details from
     * the 'PygmyRate' table.
     * @return PygmyRateObject , which contains Pygmy Account's Interest Details..
     */
    public PygmyRateObject getInterestDetails(int no_of_months,String curdate)
    {
        System.out.println("Get Interest Rate..");
        
        Connection connection = null;
      
        PygmyRateObject pr=null;
        try
        {
            common = commonLocalHome.create();
            connection = getConnection();
            Statement stat = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement stmnt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
           
            pr=new PygmyRateObject();
            
            String open_date="";
            //String date2="";
            //String close_date=common.getSysDate();
            String close_date=curdate;
            ResultSet rs2 = stmnt.executeQuery("select open_date from PygmyMaster where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);
            rs2.next();
            open_date=rs2.getString(1);
            int months=common.getNoOfMonths(open_date,close_date);
            System.out.println("Open Date: "+open_date);
            System.out.println("Closing Date : "+close_date);
            System.out.println("Months : "+months);
            System.out.println("no of months==="+no_of_months);
                      
            if(no_of_months==0){
            ResultSet rs1 = stat.executeQuery("select * from PygmyRate where '"+Validations.convertYMD(curdate)+"' between  concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1)) and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1)) and "+months+" between prd_fr and prd_to and ac_type='"+g_pgacctype+"' ");
            if(rs1.next())
             {
            	System.out.println("Int Rate: "+rs1.getDouble("int_rate"));
            	pr.setIntRate(rs1.getDouble("int_rate"));
            	pr.setPeriodTo(rs1.getInt("prd_to"));
             }
            }
            else 
            {
            	//ResultSet rs1 = stat.executeQuery("select * from PygmyRate where ac_type='"+g_pgacctype+"' limit 1");
            	ResultSet rs1 = stat.executeQuery("select * from PygmyRate where ac_type='"+g_pgacctype+"' and "+no_of_months+" between prd_fr and prd_to");
            	
                if(rs1.next())
               {
                System.out.println("Int Prd_to: "+rs1.getDouble("prd_to"));
                pr.setIntRate(rs1.getDouble("int_rate"));
                pr.setPeriodTo(rs1.getInt("prd_to"));
               }
            }
            System.out.println("Returning from GetInterestDet..");
            return pr;
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
        catch(Exception se){se.printStackTrace();}
        finally{
            common = null;
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return pr;
    }
    
    
    public HashMap getScrollDetails(String ac_type, int agentno,String cur_date,int value) throws RecordsNotFoundException
    {
        Connection con=null;
        //Object[][] scroll=null;
        HashMap hashMap = new HashMap();
        ResultSet rs=null;
        try{
            con=getConnection();
            Statement stmt=con.createStatement();
            //ResultSet rs=stmt.executeQuery("select scroll_no,csh_amt from DayCash where ac_type='"+ac_type+"' and ac_no="+agentno+" and attached='F'");
            if(value==0)
            	rs=stmt.executeQuery("select c.scroll_no,am.ac_type,am.ac_no, concat_ws('',cm.fname,cm.mname,cm.lname) name,c.csh_amt from DayCash c,AgentMaster am,CustomerMaster cm where c.ac_type='"+ac_type+"' and c.ac_no="+agentno+" and c.attached='F'  and c.trn_date='"+cur_date+"' and am.ac_type=c.ac_type and am.ac_no=c.ac_no and cm.cid=am.cid and c.ve_tml is null");
            else if(value==1)
            	rs=stmt.executeQuery("select distinct dc.scroll_no,am.ac_type,am.ac_no, concat_ws('',cm.fname,cm.mname,cm.lname) name,dc.csh_amt from DayCash dc,AgentMaster am,CustomerMaster cm where dc.ac_type='"+ac_type+"' and dc.ac_no="+agentno+" and dc.attached='T'  and dc.trn_date='"+cur_date+"' and am.ac_type=dc.ac_type and am.ac_no=dc.ac_no and cm.cid=am.cid and dc.ve_tml is null");
            rs.last();
            if(rs.getRow()==0)
            	return null;
            rs.beforeFirst();
            while(rs.next())
            {
                hashMap.put(new Integer(rs.getInt("scroll_no")),new String(rs.getString("ac_type"))+"/"+new Integer(rs.getInt("ac_no"))+"/"+new String(rs.getString("name"))+"/"+new Double(rs.getDouble("csh_amt")));
                 //hashMap.put(new Integer(rs.getInt("scroll_no")),new Double(rs.getDouble("csh_amt")));
            
            }	     
        }catch(Exception e){
            throw new RecordsNotFoundException();
        }
        finally{
            try {
                common=null;
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return hashMap;
    }
    
    /**
     * This method is used to retrieve the details abt the Joint Account Holder when 
     * opening new Pygmy Agent Account.
     * @param string_accounttype -modulecode of the SB Acc : 1002001
     * @param personal_accno - Self SB Account Number.
     * @param //int_accountno - Joint SB Account Number. 
     * @param agentCid - Customer ID of the Self SB Account No.
     * @return  Name of the Joint Account Holder.
     * @throws JointAccountNotFound
     * @throws NotValidJointHolder
     */
    public String checkJointAccount(String string_accounttype,int personal_accno,int jointaccountno,int agentCid) throws JointAccountNotFound,NotValidJointHolder
    {		
        int nohldrs=0,mainCid=-1;
        String name="";
        boolean found=false;
        Connection connection = null;
        
        System.out.println("Accounttype"+string_accounttype+"Personal AccNo"+personal_accno+"account"+jointaccountno+"agentcid"+agentCid);
        try
        {
            common=commonLocalHome.create();
            connection = getConnection();
            Statement stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs,rs1;
            rs=stmt.executeQuery("select no_jt_hldr,cid from AccountMaster where ac_type='"+string_accounttype+"' and ac_no="+personal_accno);
            try
            {
                rs.next();
                nohldrs=rs.getInt(1);    //No of Joint Holder of the SB Joint Acc Holder
                mainCid=rs.getInt(2);    // CID of the Self SB Acc holder - 'main Cid'
                System.out.println("-----1---------nohldrs"+nohldrs);
                System.out.println("-------2----------maincid"+mainCid);
                
                rs.close();
            }catch(SQLException e){throw new JointAccountNotFound();}
            
            if(nohldrs>0)
            {
                System.out.println("---3---no of hldrs");
                if(mainCid!=agentCid)
                {
                    System.out.println("-----4------agentCid");
                    rs=stmt.executeQuery("select cid from JointHolders where ac_type='"+string_accounttype+"' and ac_no="+jointaccountno+" and cid="+agentCid);
                    try
                    {
                        rs.next();
                        rs.getInt(1);
                        rs.close();
                        found=true;
                    }catch(SQLException ae){throw new NotValidJointHolder();}
                    
                    if(found)
                    {
                        System.out.println("----5--------found");
                        rs=stmt.executeQuery("select concat_ws(' ',fname,mname,lname)as name from CustomerMaster where cid="+mainCid);
                        rs.next();
                        name=rs.getString("name");
                        System.out.println("--------66name"+name);
                        return name;
                    }
                }
                else if(mainCid==agentCid)
                {
                    rs=stmt.executeQuery("select cid from JointHolders where ac_type='"+string_accounttype+"' and ac_no="+jointaccountno);
                    try
                    {
                        rs.next();
                        mainCid=rs.getInt(1);  // CID of the Joint SB Acc Holder
                        rs.close();
                        found=true;
                    }catch(SQLException ae){throw new NotValidJointHolder();}
                    
                    System.out.println("Main CID - (Joint Acc Holder's): "+mainCid);
                    System.out.println("Agent CID: "+agentCid);
                    
                    if(found)
                    {
                        rs1=stmt.executeQuery("select concat_ws(' ',fname,mname,lname)as name from CustomerMaster where cid in("+mainCid+","+agentCid+")");
                        while(rs1.next())
                        {
                            System.out.println("*********name>>>>>>>>>"+rs1.getString("name"));
                            name=name+rs1.getString(1);
                        }
                        System.out.println("JointHolder Name:"+name);
                        return name;
                        
                    }
                }
            }
            else throw new JointAccountNotFound();
        }catch(SQLException exc){exc.printStackTrace();}
        catch(CreateException ce){ce.printStackTrace();}  		
        finally{
            try {
                common=null;
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return name;
    }
    /**
     * This is used to close the Pygmy Account.
     * @param interest - If it is matured account , interest is given by the bank based on the duration of the account opened & current date.
     * @param fine - If it is Prematured account means fine will be charged for closing account.
     * @param loan_balance
     * @param pay_mode - 'C' or 'P' or 'T'  i.e Cash or Pay Order or Transfer to any other account
     * @param pay_ac_type - modulecode of the Pay Account.If it is 'T' paymode 
     * @param pay_ac_no - Account No of Pay Account,If it is 'T' Pay mode.
     * @return '1' if it is successfully happened,Otherwise '-1'.
     */
    public int closure(double interest,double fine,double loan_balance,double loan_interest,String pay_mode,String pay_ac_type,int pay_ac_no,String curdate,String curdatetime,int flag,String g_acctype,int g_acno,String de_tml,String de_user)
    {
        Connection connection = null;
        ResultSet rs_verify=null;
        String v_main_cashier=null;
        Statement stmt_verify=null;
        double pygmy_balance=0.0;
        double loan_sum=0.0;
        int trn_seq=0;
        g_pgaccno=g_acno;
        g_pgacctype=g_acctype;
        try {
        	connection = getConnection();
            stmt_verify=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs_verify=stmt_verify.executeQuery("select cash_close from DailyStatus where trn_dt='"+Validations.convertYMD(curdate)+"'");
            if(rs_verify.next())
                v_main_cashier=rs_verify.getString(1);
                           
                common = commonLocalHome.create();
               
                Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                             
                System.out.println("Inside Closing operation..");
                if(flag==2)
                {
                	//stmt.executeUpdate("delete from PygmyTransaction where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and  trn_type='E' and ve_tml is null");
                	System.out.println("inside delete");
                	stmt.executeUpdate("delete from PygmyTransaction where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and  ve_tml is null");
                
                }
                            
                PreparedStatement pstmt = connection.prepareStatement("insert into PygmyTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NULL,NULL,NULL,?)");
                ResultSet rs = stmt.executeQuery("select trn_seq,cl_bal from PygmyTransaction where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" order by trn_seq desc limit 1");
                if(rs.next())
               {     
                 trn_seq=rs.getInt(1);
                 pygmy_balance = rs.getDouble(2);
               }
                loan_sum=loan_balance+loan_interest;
                System.out.println("interest amount===="+interest);
                if(interest>0){
                	System.out.println("Writing interest record...");
                    pstmt.setString(1,g_pgacctype);
                    pstmt.setInt(2,g_pgaccno);
                    pstmt.setInt(3,++trn_seq);
                    //pstmt.setString(4,common.getSysDate());
                    pstmt.setString(4,curdate);
                    pstmt.setString(5,"E");
                    System.out.println("Got Interest-->"+interest);
                    pstmt.setDouble(6,interest);
                    pstmt.setDouble(7,interest);
                    pstmt.setString(8,"T");//trn_mode
                    pstmt.setString(9,g_tml); //trn_source
                    pstmt.setString(10,"C"); //cd_ind
                    pygmy_balance+=interest;
                    pstmt.setDouble(11,pygmy_balance);
                    pstmt.setInt(12,0);  //ref_no
                    pstmt.setString(13,"Intr");
                    pstmt.setString(14,null);
                    pstmt.setString(15,null);
                    pstmt.setDouble(16,0);
                    pstmt.setDouble(17,interest);
                    pstmt.setString(18,de_tml);
                    pstmt.setString(19,de_user);
                    pstmt.setString(20,curdatetime);
                    pstmt.setInt(21,1); //Data Entry time : 1
                    System.out.println("retunr="+pstmt.executeUpdate());
                }
                if(fine>0){
                	System.out.println("Writing  fine record...");
                    pstmt.setString(1,g_pgacctype);
                    pstmt.setInt(2,g_pgaccno);
                    pstmt.setInt(3,++trn_seq);
                    //pstmt.setString(4,common.getSysDate());
                    pstmt.setString(4,curdate);
                    pstmt.setString(5,"E");
                    System.out.println("Towards Fine-->"+fine);
                    pstmt.setDouble(6,fine);
                    pstmt.setDouble(7,fine);
                    pstmt.setString(8,"T");//trn_mode
                    pstmt.setString(9,g_tml);
                    pstmt.setString(10,"D");
                    pygmy_balance-=fine;
                    pstmt.setDouble(11,pygmy_balance);
                    pstmt.setInt(12,0);
                    pstmt.setString(13,"Penalty");
                    pstmt.setString(14,null);
                    pstmt.setString(15,null);
                    pstmt.setDouble(16,0);
                    pstmt.setDouble(17,0);
                    pstmt.setString(18,de_tml);
                    pstmt.setString(19,de_user);
                    pstmt.setString(20,curdatetime);
                    pstmt.setInt(21,1); //=> 1 for Data Entry 
                    pstmt.executeUpdate();
                }
                
                if(loan_sum>0 && pygmy_balance>0){
                	System.out.println("TOwards Loan..");
                    pstmt.setString(1,g_pgacctype);
                    pstmt.setInt(2,g_pgaccno);
                    pstmt.setInt(3,++trn_seq);
                    pstmt.setString(4,curdate);
                    pstmt.setString(5,"E");
                    double loan_paid=0.0;
                    /*if(loan_sum>pygmy_balance){
                        loan_paid=loan_balance-pygmy_balance;
                        pygmy_balance=0;
                    }*/
                      if(pygmy_balance>loan_sum)
                      {
                        loan_paid=loan_sum;
                        pygmy_balance-=loan_paid;
                      }
                    System.out.println("Towards Loan---->"+loan_paid);
                    
                    pstmt.setDouble(6,loan_paid);
                    pstmt.setDouble(7,loan_paid);
                    pstmt.setString(8,"T");//trn_mode
                    pstmt.setString(9,g_tml);
                    pstmt.setString(10,"D");
                    //pygmy_balance-=fine;
                    pstmt.setDouble(11,pygmy_balance);
                    pstmt.setInt(12,0);
                    pstmt.setString(13,"Loan");
                    pstmt.setString(14,null);
                    pstmt.setString(15,null);
                    pstmt.setDouble(16,loan_paid);
                    pstmt.setDouble(17,0);
                    pstmt.setString(18,de_tml);
                    pstmt.setString(19,de_user);
                    pstmt.setString(20,curdatetime);
                    pstmt.setInt(21,1); //=> 1 for Data Entry 
                    pstmt.executeUpdate();
                }
                if(pygmy_balance>0){   // => Balance Amount 'Dr' Transaction...
                    pstmt.setString(1,g_pgacctype);
                    pstmt.setInt(2,g_pgaccno);
                    pstmt.setInt(3,++trn_seq);
                    pstmt.setString(4,curdate);
                    pstmt.setString(5,"E");
                    System.out.println("AtLast remaining-->"+pygmy_balance);
                    pstmt.setDouble(6,pygmy_balance);
                    pstmt.setDouble(7,pygmy_balance);
                    System.out.println("Pay MODEEEEEEEEEEEEEEEEEEEEEEEEEE="+pay_mode);
                    if(!pay_mode.equalsIgnoreCase("P"))
                      pstmt.setString(8,pay_mode);//trn_mode
                    else
                      pstmt.setString(8,"T");  
                    pstmt.setString(9,g_tml);
                    pstmt.setString(10,"D");
                    pstmt.setDouble(11,0);
                    pstmt.setInt(12,0);
                    if(pay_mode.equalsIgnoreCase("T"))
                        pstmt.setString(13,pay_ac_type+"-"+pay_ac_no);
                    else if(pay_mode.equalsIgnoreCase("C"))
                    	pstmt.setString(13,"Closure-Cash");
                    else if(pay_mode.equalsIgnoreCase("P"))
                    	pstmt.setString(13,"Closure-PayOrder");
                    
                    pstmt.setString(14,null);
                    pstmt.setString(15,null);
                    pstmt.setDouble(16,pygmy_balance);
                    pstmt.setDouble(17,0);
                    pstmt.setString(18,de_tml);
                    pstmt.setString(19,de_user);
                    pstmt.setString(20,curdatetime);
                    pstmt.setInt(21,1); //=> 1 for Data Entry 
                    pstmt.executeUpdate();
                }
                //stmt.executeUpdate("update PygmyMaster set status='V',lst_trn_seq="+trn_seq+" where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);
                stmt.executeUpdate("update PygmyMaster set status='V' where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);
                
                return 1;
                                
          } catch (SQLException e) {
            e.printStackTrace();
            sessionContext.setRollbackOnly();
        } catch (CreateException e) {
            e.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally{
            common = null;
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return -1;
    }
    
    
    /**
     *  used to update the ref_ind field in 'PygmyTransaction' for PARTIALWITHDRAWAL.
     *  
     */
    
    public int getPartWithdUpdateRefInd(String ac_type,int ac_no)
    {
    	Connection conn=null;
    	PreparedStatement pstmt=null;
    	try{
    		System.out.println("Inside updation- PartialWithdrawal..");
    		System.out.println("AcTy =>"+ac_type);
    		System.out.println("Ac_no => "+ac_no);
    		conn=getConnection();
    		pstmt=conn.prepareStatement("update PygmyTransaction set ref_ind=2 where ac_type='"+ac_type+"' and ac_no="+ac_no+" and ve_tml is null and ref_ind in (1,2)");
    		if(pstmt.executeUpdate()!=0)
    		{	System.out.println("Updated Succ..");
    			return 1;
    		}
    	
    		
    	}catch(SQLException se){se.printStackTrace();}
    	    	 
        finally{
        	try{
        		conn.close();
        	}catch(SQLException e){e.printStackTrace();}
        }
        return 0;
    }
     
      
      public void ejbActivate() throws EJBException{
          try
          {
              commonLocalHome = (commonServer.CommonLocalHome) new InitialContext().lookup("COMMONLOCALWEB");
              System.out.println("Pygmy Bean Activating");
          }
          catch(NamingException ne)
          {
              ne.printStackTrace();
          }
    }
    
    public void ejbCreate(){
        try
        {
            commonLocalHome = (CommonLocalHome) new InitialContext().lookup("COMMONLOCALWEB");
            ds = (DataSource) new InitialContext().lookup("java:MySqlDS");
            
            /*commonhome=(CommonHome)HomeFactory.getFactory().lookUpHome("COMMONWEB");
            commonremote=commonhome.create();*/
        }
        catch(NamingException ex)
        {
            ex.printStackTrace();
        }
    }
    
    /*public void ejbCreate(String actype,int acno,String veuser,String vetml) throws AccountNotFoundException{
        g_pgacctype = actype; //PD or PD2
        g_pgaccno = acno; //PygmyAccNo(third Party)
        g_user = veuser; 
        g_tml = vetml;
        commonLocalHome = (CommonLocalHome) sessionContext.lookup("COMMONLOCAL");
        ds = (DataSource) sessionContext.lookup("java:MySqlDS");
        Connection conn = null;
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from PygmyMaster where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);
            rs.next();
            if(rs.getInt(1)!=1)
            	throw new AccountNotFoundException();
               // throw new pygmyDeposit.AccountNotFoundException();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AccountNotFoundException();
            //throw new pygmyDeposit.AccountNotFoundException();
        }finally{
            if(conn != null)
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
        }
    }*/
    
    public void checkAccountValidation(String actype,int acno,String veuser,String vetml) throws AccountNotFoundException{
        g_pgacctype = actype; //PD or PD2
        g_pgaccno = acno; //PygmyAccNo(third Party)
        g_user = veuser; 
        g_tml = vetml;
        
        try
        {
            commonLocalHome = (CommonLocalHome) new InitialContext().lookup("COMMONLOCALWEB");
            ds = (DataSource) new InitialContext().lookup("java:MySqlDS");
        }
        catch(NamingException ne)
        {
            ne.printStackTrace();
        }
        
        
        Connection conn = null;
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from PygmyMaster where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);
            rs.next();
            if(rs.getInt(1)!=1)
            	throw new AccountNotFoundException();
               // throw new pygmyDeposit.AccountNotFoundException();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AccountNotFoundException();
            //throw new pygmyDeposit.AccountNotFoundException();
        }finally{
            if(conn != null)
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
        }
    }
    
    
    public void ejbPassivate() throws EJBException {
        System.out.println("Pygmy Bean passivating");
        common = null;
        commonLocalHome = null;
    }	
    
    public void ejbRemove() throws EJBException{
    }
    
    /**
     * To get All Agent Numbers 
     * @param agttype - modulecode
     * @return Array of Object 'agentmasterobject'- all Agent Numbers has set into this object
     */
    public AgentMasterObject[] getAgentNos(String agttype,int close_open_ind)
    {
        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        AgentMasterObject[] agentmasterobject=null;
        try
        {
            conn=getConnection();
            System.out.println("modulecode===="+agttype);
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //rs=stmt.executeQuery("select distinct ac_no from AgentMaster where ac_type="+agttype+"  order by ac_no ");
            if(close_open_ind==0)
                rs=stmt.executeQuery("select distinct ac_no,agm.cid as cid,cm.fname as name,close_ind from AgentMaster agm,CustomerMaster cm where cm.cid=agm.cid and ac_type="+agttype+" and agm.ve_tml is not null and close_ind='O' order by ac_no");
            else if(close_open_ind==1)
                rs=stmt.executeQuery("select distinct ac_no,agm.cid as cid,cm.fname as name,close_ind from AgentMaster agm,CustomerMaster cm where cm.cid=agm.cid and ac_type="+agttype+" order by ac_no");
            if(rs.next())
            {
                rs.last();
                agentmasterobject=new AgentMasterObject[rs.getRow()];
                rs.beforeFirst();
            }
            
            int i=0;
            while(rs.next())
            {
                agentmasterobject[i]=new AgentMasterObject(); 
                agentmasterobject[i].setAgentNumber(rs.getInt("ac_no"));
                agentmasterobject[i].setName(rs.getString("name"));
                agentmasterobject[i].setCid(rs.getInt("cid"));
                agentmasterobject[i].setCloseInd(rs.getInt("close_ind"));
                i++;
            }
        }catch(Exception e){e.printStackTrace();}
        finally{
            try{
                conn.close();
                stmt.close();
                rs.close();
            }catch(SQLException se){se.printStackTrace();}
        }
       
        return agentmasterobject;
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
    
    public AgentMasterObject[] getAgentCloseToVerify(String agttype)
    {
        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        AgentMasterObject[] agentmasterobject=null;
        try
        {
            conn=getConnection();
            System.out.println("modulecode===="+agttype);
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //rs=stmt.executeQuery("select distinct ac_no from AgentMaster where ac_type="+agttype+"  order by ac_no ");
            rs=stmt.executeQuery("select distinct ac_no,cm.fname as name,agm.cid as cid,agm.close_ind from AgentMaster agm,CustomerMaster cm where cm.cid=agm.cid and ac_type="+agttype+" and agm.ve_tml is not null and close_ind=19 order by ac_no");
            if(rs.next())
            {
                rs.last();
                agentmasterobject=new AgentMasterObject[rs.getRow()];
                rs.beforeFirst();
            }
            
            int i=0;
            while(rs.next())
            {
                agentmasterobject[i]=new AgentMasterObject(); 
                agentmasterobject[i].setAgentNumber(rs.getInt("ac_no"));
                agentmasterobject[i].setName(rs.getString("name"));
                agentmasterobject[i].setCid(rs.getInt("cid"));
                agentmasterobject[i].setCloseInd(rs.getInt("close_ind"));
                i++;
            }
        }catch(Exception e){e.printStackTrace();}
        finally{
            try{
                conn.close();
                stmt.close();
                rs.close();
            }catch(SQLException se){se.printStackTrace();}
        }
       
        return agentmasterobject;
    }
    /**
     * This will give the Agent Information.
     * @param agttype - modulecode
     * @param agtno
     * @return 'agentmasterobject' which contains Agent Details.
     */
    public AgentMasterObject getAgentDetails(String agttype,int  agtno,String date)
    {
        Statement stmt;
        ResultSet rs;
        AgentMasterObject agentmasterobject=new AgentMasterObject();
        AccountObject accountobject_personal=null;
        AccountObject accountobject_joint=null;
        System.out.println("Inside getAgentDetails....");
        System.out.println("agenttype"+agttype);
        System.out.println("agentno"+agtno);
        Connection connection = null;
        int jt_ac_no=0,personal_accno=0,joint_accno=0;
       
        String jt_ac_ty=null,personal_acctype=null,joint_acctype=null;
        try
        {
        	 /*CommonLocalHome commonLocalHome;
        	 
            commonLocalHome =(CommonLocalHome)getInitialContext().lookup("COMMONLOCALWEB"); 
    		CommonLocal commonlocal=commonLocalHome.create();
    		*/
        	
        	Context ctx=getInitialContext();
        	Object obj=ctx.lookup("COMMONWEB");
        	commonServer.CommonHome home=(commonServer.CommonHome)obj;
        	CommonRemote commonremote=home.create();
            connection = getConnection();
           //log4j
           /* appender=new FileAppender(layout,"PygmyBean.html",false);
			logger.addAppender(appender);
			*/
            stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=stmt.executeQuery("select mo.moduleabbr,agm.*,cm.fname,concat_ws(' ',cm.fname,cm.mname,cm.lname) as agent_name,mo2.moduleabbr,mo3.moduleabbr,mo4.moduleabbr from Modules mo,Modules mo2,Modules mo3,Modules mo4,AgentMaster agm,CustomerMaster cm where ac_type='"+agttype+"' and ac_no="+agtno+" and mo.modulecode=ac_type and mo2.modulecode=p_ac_type and mo3.modulecode=jt_ac_type and cm.cid=agm.cid and (intr_ac_type is null || (intr_ac_type is not null and mo4.modulecode=intr_ac_type)) limit 1");
           
            if(rs.next())
        {
                personal_acctype=rs.getString("p_ac_type");
                joint_acctype=rs.getString("jt_ac_type");
                personal_accno=rs.getInt("p_ac_no");
                joint_accno=rs.getInt("jt_ac_no");
                accountobject_personal=commonremote.getAccount(null,personal_acctype,personal_accno,date);
                System.out.println("accountobject_personal"+ accountobject_personal);
                accountobject_joint=commonremote.getAccount(null,joint_acctype,joint_accno,date);
                System.out.println("accountobject_joint"+ accountobject_joint);
                if((accountobject_personal!=null && accountobject_joint!=null && !accountobject_personal.getAccStatus().equalsIgnoreCase("c"))&&((accountobject_personal!=null && accountobject_joint!=null && accountobject_personal.getAccStatus().equalsIgnoreCase("o")&& accountobject_joint.getAccStatus().equalsIgnoreCase("o"))||(accountobject_personal!=null && accountobject_joint!=null && Integer.parseInt(accountobject_personal.getAccStatus())==0 && Integer.parseInt(accountobject_joint.getAccStatus())==0)))
              {
                agentmasterobject.setAgentType(rs.getString("mo.moduleabbr"));
                agentmasterobject.setAgentNo(rs.getInt("ac_no"));
                agentmasterobject.setCid(rs.getInt("cid"));
                System.out.println("agent name======"+rs.getString("agent_name"));
                agentmasterobject.setName(rs.getString("agent_name"));
                agentmasterobject.setFname(rs.getString("fname"));
                agentmasterobject.setJointHldrs(rs.getInt("no_jt_hldr"));
                agentmasterobject.setAppointDate(rs.getString("appt_date"));
                agentmasterobject.setAccType(rs.getString("mo2.moduleabbr"));
                agentmasterobject.setAccNo(rs.getInt("p_ac_no"));
                agentmasterobject.setPersonalAccType(rs.getString("p_ac_type"));
                jt_ac_ty=rs.getString("jt_ac_type");
                agentmasterobject.setJointAccType(rs.getString("mo3.moduleabbr"));
                jt_ac_no=rs.getInt("jt_ac_no");
                agentmasterobject.setJointAccNo(jt_ac_no);
                agentmasterobject.setJtAccType(rs.getString("jt_ac_type"));
               // agentmasterobject.setSecurityCID(rs.getInt("sec_cid"));
                /*if(String.valueOf(rs.getInt("sec_cid"))!=null)
                    agentmasterobject.setSecurityCID(rs.getInt("sec_cid"));
                else
                    agentmasterobject.setSecurityCID(0);*/
                agentmasterobject.setIntrAccType(rs.getString("intr_ac_Type"));
                agentmasterobject.setIntrAccNo((rs.getInt("intr_ac_no")));
                agentmasterobject.setCloseDate(rs.getString("close_date"));
                agentmasterobject.setCloseInd(rs.getInt("close_ind"));
                System.out.println("close ind in agent det=="+agentmasterobject.getCloseInd());
               // agentmasterobject.setSecurityName(rs.getString("security_name"));
                agentmasterobject.setVeTml(rs.getString("ve_tml"));
                agentmasterobject.setRef_Ind(rs.getInt("ref_ind"));
				              
                
                /*if(agentmasterobject.getJointAccNo()!=0)
                {
                    
                    String pname = common.getName(rs.getString("p_ac_type"),agentmasterobject.getAccNo());
                    rs=stmt.executeQuery("select cid from JointHolders where ac_type='"+rs.getString("jt_ac_type")+"' and ac_no="+agentmasterobject.getJointAccNo());
                    if(rs.next())
                    {
                        joint_cid=rs.getInt(1);
                    }
                    System.out.println("Joint CID: "+joint_cid);
                    rs=stmt.executeQuery("select concat_ws(' ',fname,mname,lname)as jname from CustomerMaster where cid="+joint_cid);
                    if(rs.next())
                    {
                        jname = rs.getString(1);
                    }
                    agentmasterobject.setJointName(pname+"&"+jname);
                	System.out.println("pers acc ty="+rs.getString("p_ac_type"));
                	System.out.println("pers ac no="+rs.getString("p_ac_no"));
                	System.out.println("join ac no="+rs.getString("jt_ac_type"));
                	System.out.println("join ac no="+rs.getString("jt_ac_no"));
                	System.out.println(" common remote"+commonremote);
                    logger.info("pers acc ty="+rs.getString("p_ac_type"));
                	logger.info("pers ac no="+rs.getString("p_ac_no"));
                	logger.info("join ac no="+rs.getString("jt_ac_type"));
                	logger.info("join ac no="+rs.getString("jt_ac_no"));
                	logger.info(" common remote"+commonremote);
                	jname=commonremote.getJointSBAccountName(null,0,rs.getString("p_ac_type"),rs.getInt("p_ac_no"),rs.getString("jt_ac_type"),rs.getInt("jt_ac_no"),3);
              
                    agentmasterobject.setJointName(jname);
                }			
              */  System.out.println("JT Ac Ty: "+agentmasterobject.getJointAccType());
                System.out.println("JT Ac No: "+agentmasterobject.getJointAccNo());
                
                if(jt_ac_ty!=null && jt_ac_no>0)
                { 		
                
                	System.out.println("JT Ac Ty @@: "+jt_ac_ty);
                    System.out.println("JT Ac No @@: "+jt_ac_no);
                    
                	//if(agentmasterobject.getJointAccType().startsWith("1002"))
                rs.close();
                rs = stmt.executeQuery("select cid from AccountMaster where ac_type='"+jt_ac_ty+"' and ac_no="+jt_ac_no+"");
                int cid;
                /*int addrtype[]=null;
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
                
                agentmasterobject.setJointAddrType(addrtype);*/
                rs.next();
                cid=rs.getInt(1);
                System.out.println("JT CID: "+cid);
                agentmasterobject.setJointCid(cid);
                System.out.println("JT CID: "+agentmasterobject.getJointCid());
                } // end of finding jt CID..
                
                System.out.println(" common remote:"+commonremote);
                System.out.println(" get sign:"+commonremote.getSignatureDetails(agtno,agttype));
                agentmasterobject.setSigObj(commonremote.getSignatureDetails(agtno,agttype));
                
                //System.out.println("PAG SigObj Length : "+agentmasterobject.getSigObj().length);
                //System.out.println("JT Ac Ty: "+agentmasterobject.getJointAccType());
                //System.out.println("JT Ac No: "+agentmasterobject.getJointAccNo());
             
                rs.close();
                
                rs=stmt.executeQuery("select * from DayCash where ac_type='"+agttype+"'  and ac_no="+agtno+" and attached='F' and trn_date='"+date+"'");
                
                if(rs.next())
                {
                    System.out.println("scroll no = "+rs.getInt("scroll_no"));
                    System.out.println("scroll amt = "+rs.getDouble("csh_amt"));
                    
                	agentmasterobject.setScroll_no(rs.getInt("scroll_no"));
                	agentmasterobject.setScroll_amoount(rs.getDouble("csh_amt"));
                }
            }
             else 
                 agentmasterobject.setAgentNo(-2);
                    
          }       
            else
            {
                System.out.println("Setting -1 to Agent No..");
               agentmasterobject.setAgentNo(-1);
               rs.close();
            }
                
        }catch(Exception e){e.printStackTrace();}
        finally{
            try {
                //common = null;
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        System.out.println("agt mas obj="+agentmasterobject);
        System.out.println("agt mas scrlno=="+agentmasterobject.getScroll_no());
        System.out.println("closeind=="+agentmasterobject.getCloseInd());
        System.out.println("agentmasterobject===>"+ agentmasterobject.getJointAccNo());
        System.out.println("agentmasterobject===>"+ agentmasterobject.getAccNo());
        return agentmasterobject;
    }
    
    
    
    public int getRefInd(String agt_type,int agt_no,String coll_dt,int scrl_no)
    {
     Connection conn=null;
     Statement st=null;
     ResultSet rs=null;
     try{
     	conn=getConnection();
     	st=conn.createStatement();
     	System.out.println("Ac_ty: "+agt_type+ " Ac No : "+agt_no);
     	rs=st.executeQuery("select ref_ind from DailyRemittance where ac_type='"+agt_type+"' and ac_no="+agt_no+" and coll_date='"+coll_dt+"' and ref_no="+scrl_no+" order by ac_no limit 1");
        if(rs.next())
    	return  rs.getInt(1);
        
     } catch(SQLException se){se.printStackTrace();}
     finally{
     	try{
     	rs.close();
     	rs=null;
     	conn.close();
     	}catch(SQLException e){e.printStackTrace();}
     }
     return 0;
    	
    }
    
    
    
    /**
     * ths is used to get the Remittance Details of the Particular Agent.
     * @param agenttype
     * @param agentno
     * @param collection_date
     * @return - Pygmy Transaction Object 'pt' with data.
     */
    public PygmyTransactionObject[] getAgentRemittance(String agenttype,int agentno,String collection_date,boolean flag)
    {
        PygmyTransactionObject[] pt=null;
        Connection connection = null;
        ResultSet rs=null,rs1=null,rs2=null,rs3=null;
        System.out.println("----------PTRAN----------"+agenttype);
       	System.out.println("----------PTRAN----------"+agentno);
       	System.out.println("----------PTRAN----------"+collection_date);
       	System.out.println("----------PTRAN----------"+flag);
        try
        {
            common = commonLocalHome.create();
            connection = getConnection();
            Statement stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement stmt1=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            Statement stmt2=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            Statement stmt3=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            int scrlno = 0;
            String trndate = null;
            double scrlamt=0;
            
            System.out.println("Inside getRemittance:....");
            System.out.println("AGent Type:"+agenttype);
            System.out.println("Agent No:  "+agentno);
            System.out.println("Date : "+collection_date);
           
            
            /*if(flag)
            rs=stmt.executeQuery("select m.moduleabbr,pt.ac_no,cm.fname,pt.trn_date,pt.trn_amt,pt.coll_date,pt.ref_no,pt.ve_tml,pt.ref_ind from Modules m,PygmyTransaction pt,PygmyMaster pm,AgentMaster am,CustomerMaster cm where am.ac_type='"+agenttype+"' and am.ac_no="+agentno+" and pm.agt_type=am.ac_type and pm.agt_no=am.ac_no and pt.ac_type=pm.ac_type and pt.ac_no=pm.ac_no and pm.cid=cm.cid and m.modulecode=pt.ac_type and pt.trn_seq=pm.lst_trn_seq and pt.coll_date='"+collection_date+"'and pt.ve_tml is null");
            else if(!flag)*/
            rs3=stmt3.executeQuery("select m.moduleabbr,dr.ac_no,cm.fname,dr.trn_date,dr.trn_amt,dr.coll_date,dr.ref_no,dr.ve_tml,dr.ref_ind from Modules m,PygmyMaster pm,AgentMaster am,CustomerMaster cm,DailyRemittance dr where am.ac_type='"+agenttype+"' and am.ac_no="+agentno+" and pm.agt_type=am.ac_type and pm.agt_no=am.ac_no and dr.ac_type=pm.ac_type and dr.ac_no=pm.ac_no and pm.cid=cm.cid and m.modulecode=dr.ac_type  and dr.coll_date='"+collection_date+"'and dr.ve_tml is null order by dr.ac_no limit 1");
            System.out.println("PLZZZZZZZZZZZZZ"+rs3);
            while(rs3.next())
           {
            	System.out.println("PLZZZZZZZZZZZZZ");
            rs=stmt.executeQuery("select m.moduleabbr,dr.ac_no,cm.fname,dr.trn_date,dr.trn_amt,dr.coll_date,dr.ref_no,dr.ve_tml,dr.ref_ind from Modules m,PygmyMaster pm,AgentMaster am,CustomerMaster cm,DailyRemittance dr where am.ac_type='"+agenttype+"' and am.ac_no="+agentno+" and pm.agt_type=am.ac_type and pm.agt_no=am.ac_no and dr.ac_type=pm.ac_type and dr.ac_no=pm.ac_no and pm.cid=cm.cid and m.modulecode=dr.ac_type  and dr.coll_date='"+collection_date+"' and ref_no="+rs3.getInt("ref_no")+" and dr.ve_tml is null order by dr.ac_no");
            System.out.println("PLZZZZZZZZZZZZZ");
            rs.next();
            rs.last();
            int count=0;
            count=rs.getRow();
            /*if(count==0)
            {
            	pt=new PygmyTransactionObject[]
            }
            else*/ if(count!=0){
                scrlno=rs.getInt(7);
                trndate=rs.getString(4);
                System.out.println("Scrl No: "+scrlno);
                System.out.println("Tran Date: "+trndate);
                Object[] obj=common.checkScrollAttached(scrlno,trndate,agenttype,String.valueOf(agentno),1);//type 1 to get scroll Amount
                scrlamt=Double.parseDouble(obj[1].toString());
            }
            pt=new PygmyTransactionObject[rs.getRow()];
            rs.beforeFirst();
            int i=0;
            
            System.out.println("SCROLL NO: "+scrlamt);
            if(scrlamt!=-1)
                while(rs.next())
                {
                    pt[i]=new PygmyTransactionObject();
                    pt[i].setAccType(rs.getString("moduleabbr"));
                    pt[i].setAccNo(rs.getInt("ac_no"));
                    pt[i].setName(rs.getString("fname"));
                    pt[i].setTranDate(trndate);
                    pt[i].setTranAmt(rs.getDouble("trn_amt"));
                    pt[i].setCollectionDate(rs.getString("coll_date"));
                    pt[i].setRefNo(scrlno);
                    pt[i].setPrnPaid(scrlamt);//for scroll amount
                    pt[i].setTranSource(rs.getString("ve_tml"));
                    pt[i].setRef_ind(rs.getInt("ref_ind"));
                    
                    System.out.println("i value: "+i);
                    i++;
                    
                }
            
            
            //rs1=stmt1.executeQuery("select distinct pt.ac_type, pt.ac_no from PygmyTransaction pt, PygmyMaster pm where pm.agt_type='"+agenttype+"' and pm.agt_no="+agentno+" and pt.coll_date='"+collection_date+"' and pt.ac_no=pm.ac_no and pt.ve_tml is null");
            rs1=stmt1.executeQuery("select distinct dr.ac_type, dr.ac_no from DailyRemittance dr, PygmyMaster pm where pm.agt_type='"+agenttype+"' and pm.agt_no="+agentno+" and dr.coll_date='"+collection_date+"' and dr.ac_no=pm.ac_no and ref_no="+rs3.getInt("ref_no")+" and dr.ve_tml is null order by ac_no");
            
            int j=0;
            while(rs1.next())
            {
                //rs2=stmt2.executeQuery("select if(pt.cd_ind='C', cl_bal-trn_amt, cl_bal+trn_amt)  as cl_bal from PygmyTransaction pt where ac_type='"+rs1.getString("ac_type")+"' and ac_no="+rs1.getInt("ac_no")+" and coll_date='"+collection_date+"' and ve_tml is  null order by trn_seq desc limit 1");
            	rs2=stmt2.executeQuery("select cl_bal from PygmyTransaction pt where ac_type='"+rs1.getString("ac_type")+"' and ac_no="+rs1.getInt("ac_no")+" and ve_tml is  not null order by trn_seq desc limit 1");	
            	if(rs2.next())
                    pt[j].setPrevBalance(rs2.getDouble("cl_bal"));

                System.out.println("j Value: "+j);
                j++;
            }
          }  
         
           
        }catch(SQLException sql){sql.printStackTrace();} catch (CreateException e) {
            e.printStackTrace();
        } catch (ScrollNotFoundException e) {
            e.printStackTrace();
        }
        finally{
            try {
                common = null;
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("pygmy tran daily remit ln=="+pt.length);
        return pt;
        
    }
    
    public PygmyTransactionObject[] getAgentRemittanceForUpdate(String agenttype,int agentno,int scroll_no,String collection_date,String remit_date)
    {
    	PygmyTransactionObject[] ptran=null;
    	Connection conn=null;
    	ResultSet rs=null,rs1=null,rs2=null;
    	Statement stmt=null,stmt1=null,stmt2=null;
    	int i=0;
    	try
    	{
    		conn=getConnection();
    		stmt=conn.createStatement();
    		stmt1=conn.createStatement();
    		stmt2=conn.createStatement();
    		
    		rs=stmt.executeQuery("select m.moduleabbr,dr.ac_type, dr.ac_no,cm.fname,dr.trn_date,dr.trn_amt,dr.coll_date,dr.ref_no,dr.ve_tml,dr.ref_ind from Modules m,PygmyMaster pm,AgentMaster am,CustomerMaster cm,DailyRemittance dr where am.ac_type='"+agenttype+"' and am.ac_no="+agentno+" and pm.agt_type=am.ac_type and pm.agt_no=am.ac_no and dr.ac_type=pm.ac_type and dr.ac_no=pm.ac_no and pm.cid=cm.cid and m.modulecode=dr.ac_type  and dr.coll_date='"+collection_date+"' and dr.trn_date='"+remit_date+"' and dr.ref_no="+scroll_no+" and dr.ve_tml is null order by dr.ac_no");
    		if(rs.next())
    		{
    			rs.last();
    			ptran=new PygmyTransactionObject[rs.getRow()];
    			rs.beforeFirst();
    		}
    		//rs1=stmt1.executeQuery("select distinct dr.ac_type, dr.ac_no from DailyRemittance dr, PygmyMaster pm where pm.agt_type='"+agenttype+"' and pm.agt_no="+agentno+" and dr.coll_date='"+collection_date+"' and dr.trn_date='"+remit_date+"' and  dr.ac_no=pm.ac_no and dr.ref_no="+scroll_no+" and dr.ve_tml is null order by ac_no");
    		while(rs.next())
    		{
    			ptran[i]=new PygmyTransactionObject();
    			rs1=stmt1.executeQuery("select cl_bal from PygmyTransaction pt where ac_type='"+rs.getString("ac_type")+"' and ac_no="+rs.getInt("ac_no")+" and ve_tml is  not null order by trn_seq desc limit 1");
    			if(rs1.next())
    				ptran[i].setPrevBalance(rs1.getDouble("cl_bal"));
    			else
    				ptran[i].setPrevBalance(0.0);
    			ptran[i].setAccType(rs.getString("moduleabbr"));
    			ptran[i].setAccNo(rs.getInt("ac_no"));
    			ptran[i].setName(rs.getString("fname"));
    			ptran[i].setTranAmt(rs.getDouble("trn_amt"));
    			i++;
    		}
    	}
    	catch(Exception ex){
    		ex.printStackTrace();
    	}
    	finally{
    		try{
    			conn.close();
    		}
    		catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    	return ptran;
    }
    
    
    public int getMaxPeriodDRemittance(int ac_type)
    {
    	Connection conn=null;
    	Statement st=null;
    	ResultSet rs=null;
    	int max_period=0;
    	try{
    		conn=getConnection();
    		st=conn.createStatement();
    		rs=st.executeQuery("select min_period from Modules where modulecode="+ac_type+"");
    		rs.next();
    		max_period=rs.getInt(1);
    		return max_period;
    		
    	}catch(SQLException se){se.printStackTrace();}
    	finally{
    		try{
    			conn.close();
    		}catch(SQLException e){e.printStackTrace();}
    	}
    	return max_period;
    }
       
        
    
    /**
     * ths is used to get the Information about the Particular Agent.
     * @param agenttype
     * @param agentno
     * @return -PygmyTransaction Object 'pt' with data about Pygmy Agent.
     */
    public PygmyTransactionObject[] getAgentInformation(String agenttype,int agentno)throws RecordsNotFoundException
    {
        PygmyTransactionObject[] pt=null;
        Connection connection = null;
        //int bit=flag;
        try
        {
            common = commonLocalHome.create();
            connection = getConnection();
            Statement stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement stmt1=connection.createStatement();
            ResultSet rs1=null;
            ResultSet rs=null;
            int row=0;
         
            double scrlamt=0;
            int i=0;   
            System.out.println("Inside getRemittance:....");
            System.out.println("AGent Type:"+agenttype);
            System.out.println("Agent No:  "+agentno);
            
            //if(bit==0)
            System.out.println("1...");
            //ResultSet rs=stmt.executeQuery("select m.moduleabbr,pt.ac_no,cm.fname,pt.trn_date,pt.trn_amt,pt.coll_date,pt.ref_no,pt.ve_tml,pt.cl_bal from Modules m,PygmyTransaction pt,PygmyMaster pm,AgentMaster am,CustomerMaster cm where am.ac_type='"+agenttype+"' and am.ac_no="+agentno+" and pm.agt_type=am.ac_type and pm.agt_no=am.ac_no and pt.ac_type=pm.ac_type and pt.ac_no=pm.ac_no and pm.cid=cm.cid and m.modulecode=pt.ac_type and pt.trn_seq=pm.lst_trn_seq");
            //ResultSet rs=stmt.executeQuery("select m.moduleabbr,pm.ac_no,cm.fname,pt.trn_date,pt.trn_amt,pt.coll_date,pt.ref_no,ifnull(pt.ve_tml,'') as ve_tml,ifnull(pt.cl_bal,0.00) as cl_bal from Modules m,PygmyMaster pm,AgentMaster am,CustomerMaster cm left join PygmyTransaction pt on  (pm.lst_trn_seq= pt.trn_seq and  pm.ac_type=pt.ac_type and pm.ac_no=pt.ac_no) where am.ac_type='"+agenttype+"' and am.ac_no="+agentno+" and pm.agt_type=am.ac_type and pm.agt_no=am.ac_no and pm.cid=cm.cid and m.modulecode=pm.ac_type and pm.ve_tml is not null and pm.close_date is null and pm.status='o'");
            //ResultSet rs=stmt.executeQuery("select m.moduleabbr,pm.ac_no,cm.fname,pt.trn_date,pt.trn_amt,pt.coll_date,pt.ref_no,ifnull(pt.ve_tml,'') as ve_tml,ifnull(pt.cl_bal,0.00) as cl_bal from Modules m,PygmyMaster pm,AgentMaster am,CustomerMaster cm,PygmyTransaction pt  where am.ac_type='"+agenttype+"' and am.ac_no="+agentno+" and pm.lst_trn_seq= pt.trn_seq and  pm.ac_type=pt.ac_type and pm.ac_no=pt.ac_no and pm.agt_type=am.ac_type and pm.agt_no=am.ac_no and pm.cid=cm.cid and m.modulecode=pm.ac_type and pm.ve_tml is not null and pm.close_date is null and pm.status='o'");
            
            rs=stmt.executeQuery("select m.moduleabbr,pm.ac_type,pm.ac_no,pm.lst_trn_seq,cm.fname from Modules m,PygmyMaster pm,AgentMaster am,CustomerMaster cm  where am.ac_type='"+agenttype+"' and am.ac_no="+agentno+" and pm.agt_type=am.ac_type and pm.agt_no=am.ac_no and pm.cid=cm.cid and m.modulecode=pm.ac_type and pm.ve_tml is not null and pm.close_date is null and pm.status='O'");
            if(rs.last())
          {    
            
             System.out.println("2....");
             row=rs.getRow();
             rs.beforeFirst();
          }  
             System.out.println("num of rows="+row);
             pt=new PygmyTransactionObject[row];
             if(scrlamt!=-1)
                 while(rs.next())
                 {
                     rs1=stmt1.executeQuery("select trn_date,trn_amt,coll_date,ref_no,ifnull(ve_tml,'') as ve_tml,ifnull(cl_bal,0.00) as cl_bal  from PygmyTransaction where trn_seq="+rs.getInt("pm.lst_trn_seq")+" and ac_type='"+rs.getString("pm.ac_type")+"' and ac_no="+rs.getString("pm.ac_no")+"");
                     if(rs1.next())
                     {     
                     System.out.println("4...");
                     pt[i]=new PygmyTransactionObject();
                     pt[i].setAccType(rs.getString("moduleabbr"));
                     pt[i].setAccNo(rs.getInt("ac_no"));
                     pt[i].setName(rs.getString("fname"));
                     System.out.println("m in server\\\\\\\\\\\\\\--------->>>>>>"+rs.getString("fname"));
                     pt[i].setTranDate(rs1.getString("trn_date"));
                     //pt[i].setRefNo(scrlno);
                     //pt[i].setPrnPaid(scrlamt);//for scroll amount
                     pt[i].setTranAmt(rs1.getDouble("trn_amt"));
                     pt[i].setCollectionDate(rs1.getString("coll_date"));
                     pt[i].setTranSource(rs1.getString("ve_tml"));
                     pt[i].setPrevBalance(rs1.getDouble("cl_bal"));
                     i++;                    
                    }
                     else
                     {
                         System.out.println("5...");
                         pt[i]=new PygmyTransactionObject();
                         pt[i].setAccType(rs.getString("moduleabbr"));
                         pt[i].setAccNo(rs.getInt("ac_no"));
                         pt[i].setName(rs.getString("fname"));
                         pt[i].setTranDate(" ");
                         //pt[i].setRefNo(scrlno);
                         //pt[i].setPrnPaid(scrlamt);//for scroll amount
                         pt[i].setTranAmt(0.0);
                         pt[i].setCollectionDate(" ");
                         pt[i].setTranSource(" ");
                         pt[i].setPrevBalance(0.0);
                         i++;                          
                         
                     }
                 }
                  
            
            /*if(row==0)
                throw new RecordsNotFoundException();*/
                   
                                      
            System.out.println("6.....");
            System.out.println("pt length1="+pt.length);
            return pt;
        }catch(SQLException sql){sql.printStackTrace();} catch (CreateException e) {
            e.printStackTrace();
        } /*catch (ScrollNotFoundException e) {
        e.printStackTrace();
        }*/
        //catch(RecordsNotFoundException ex){ex.printStackTrace();}
        finally{
            try {
                common = null;
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("pt length2="+pt.length);
        return pt;
    }
    
    
    
    public AgentMasterObject[] getAgentReport()
    {
        Statement stmt;
        ResultSet rs;
        AgentMasterObject am[]=null;
        Connection connection = null;
        try
        {
            connection = getConnection();
            stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs=stmt.executeQuery("select agm.*,cm.fname,cm.mname,cm.lname from AgentMaster agm,CustomerMaster cm  where cm.cid=agm.cid");
            
            rs.last();
            am=new AgentMasterObject[rs.getRow()];
            rs.beforeFirst();
            int i=0;
            while(rs.next())
            {
                am[i]=new AgentMasterObject();
                am[i].setAgentType(rs.getString(1));
                am[i].setAgentNo(rs.getInt(2));
                am[i].setName(rs.getString(19)+" "+rs.getString(20)+" "+rs.getString(21));
                am[i].setJointHldrs(rs.getInt(4));
                am[i].setAppointDate(rs.getString(5));
                am[i].setAccType(rs.getString(6));
                am[i].setAccNo(rs.getInt(7));
                am[i].setJointAccType(rs.getString(8));
                am[i].setJointAccNo(rs.getInt(9));
               // am[i].setSecurityCID(rs.getInt(10));
                am[i].setCloseDate(rs.getString(11));
                am[i].setCloseInd(rs.getInt(12));
                //				am[i].setSecurityName(rs.getString(22)+" "+rs.getString(23)+" "+rs.getString(24));
                
                String name = common.getName(am[i].getJointAccType(),am[i].getJointAccNo());
                am[i].setJointName(name);
                i++;
                
            }
            
        }catch(Exception e){e.printStackTrace();}
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        
        return am;
    }
    
    /**
     * This method is used to create a new Agent Number.And To update the Agent creation details before Verification.
     * It refers the Modules Table to get the Last Generated Agent Number.
     * @param agentmasterobject
     * @param int_verify - '1' is to create or update the Agent Number. '2' is to Verify.
     * @return 'Agent Number'
     *
     * ###### TO REFER..
     *  if(int_verify == 1) // For Data Entry & Updation Case..
     *  {
     *    
     *    if(cs.getAccNo()==0)
     *     {
     *       generate new Agent Account Number by refering the last account number field of 
     *       agent from 'Modules' table. 
     *     }
     *     else  //Ths z for Updation case..
     *     {
     *       delete the existing record from 'Agent Master' & 'SignatureInstruction' tables
     *     }  
     *     
     *     insert data into 'AgentMaster' Table & 'SignatureInstruction' Table
     *     NOTE:
     *     To store SignatureInstruction details call,
     *    'common.storeSignatureDetails(agentmasterobject.getSigObj(),agtno);'
     *     
     *     if(cs.getAccNo()==0) //DATA ENTRY CASE
     *     {
     *       Update the lst acc no field in Modules Table & return the New Agent Acc No.
     *     }
     *     else if(cs.getAccNo()>0) //UPDATE CASE
     *     {
     *       return the Agent No.
     *     }
     *   } //end of if(int_verify==1)
     * 
     *   if(int_verify==2) //VERIFICATION CASE
     *   {
     *    update verification fields & "close Indicator" as 'o' for the newly generated 
     *    agent acc no.
     *    return 1;
     *    }
     *        
     * 
     * ##### TABLE ENTRIES - TO REFER....
     * 
     * (a) When Action Performed of Submit Button, i.e Data Entry Time 
     *     'close_ind'=9 //for programmer purpose   
     *      'sec_cid'=>introducer's cid
     * 
     */
    public int getAgentUpdate(AgentMasterObject agentmasterobject,int int_verify,String curdatetime)
    {
        int agtno=0;
        System.out.println("inside getAgentUpdate , Creating a new Agent Acc no.."+int_verify);
        Statement stmt;
        PreparedStatement ps;
        ResultSet rs;
        Connection connection = null ;
        
        try
        {
            common = commonLocalHome.create();
            connection = getConnection();
            stmt=connection.createStatement();
            System.out.println("Before iF");
            if(int_verify==1)//to insert or update details
            {
                System.out.println("After iF");
                ps=connection.prepareStatement("insert into AgentMaster values(?,?,?,?,?,?,?,?,?,?,?,NULL,?,?,?,'"+curdatetime+"',NULL,NULL,NULL,?)");
                
                if(agentmasterobject.getAgentNo()==0)
                {
                    rs=stmt.executeQuery("select lst_acc_no from Modules where modulecode="+agentmasterobject.getAgentType());
                    rs.next();
                    agtno=rs.getInt(1);
                    agtno++;
                }
                else if(agentmasterobject.getAgentNo()>0)
                {
                    stmt.executeUpdate("delete from AgentMaster where ac_type='"+agentmasterobject.getAgentType()+"' and ac_no="+agentmasterobject.getAgentNo());
                    stmt.executeUpdate("delete from SignatureInstruction where  ac_type='"+agentmasterobject.getAgentType()+"' and ac_no="+agentmasterobject.getAgentNo());
                    agtno=agentmasterobject.getAgentNo();
                }
                
                System.out.println("Agent CID: "+agentmasterobject.getCid());
                ps.setString(1,String.valueOf(agentmasterobject.getAgentType())); //modulecode
                ps.setInt(2,agtno);//Newly Generated Agent No..
                ps.setInt(3,agentmasterobject.getCid()); // Personal Acc nO's CID . i.e Agent's CID
                ps.setInt(4,0);  // No of Jt Hldrs : always '0' value..
                ps.setString(5,agentmasterobject.getAppointDate()); // Current Date
                ps.setString(6,agentmasterobject.getAccType()); //Personal Acc Type
                ps.setInt(7,agentmasterobject.getAccNo());  // Personal Acc No
                ps.setString(8,agentmasterobject.getJointAccType()); //Jt Acc Ty
                ps.setInt(9,agentmasterobject.getJointAccNo()); //Jt Acc No
                
                //ship......12/07/2007
                //ps.setInt(10,agentmasterobject.getSecurityCID()); //Introducer CID
                ps.setString(10,agentmasterobject.getIntrAccType()); //Introducer Acc Ty
                ps.setInt(11,agentmasterobject.getIntrAccNo()); //Introducer Acc No
                ////////////
                
                ps.setInt(12,9); // Temporary Purpose, It will get updated when Verification as 'o', i.e Open/Active Account
                ps.setString(13,agentmasterobject.getDeTml());  // de_tml
                ps.setString(14,agentmasterobject.getDeUser()); //de_user
                //if(agentmasterobject.getAgentNo()==0)
                ps.setInt(15,1); //Ref_indicator => Data Entry =1.
                /*else
                ps.setInt(14,2); //Ref_indicator => Updation Case=2;
*/                
                ps.executeUpdate();
                System.out.println("Before ADFAFASDFASFADFAF");
                if(agentmasterobject.getSigObj()!=null){
                    System.out.println("bfore signa ... ");
                    common.storeSignatureDetails(agentmasterobject.getSigObj(),agtno);
                    System.out.println("after ..  signa ... ");
                }
                /*if(agentmasterobject.getJointCid()!=null)
                 {
                 int addrtype[]=agentmasterobject.getJointAddrType();
                 int jcids[]=agentmasterobject.getJointCid();
                 for(int i=0;i<jcids.length;i++)
                 {	
                 stmt.executeUpdate("insert into JointHolders values('"+agentmasterobject.getAgentType()+"','"+agtno+"','"+jcids[i]+"','"+addrtype[i]+"')");
                 }
                 }*/						
                
                if(agentmasterobject.getAgentNo()==0)
                {
                	
                    stmt.executeUpdate("update Modules  set lst_acc_no=lst_acc_no+1 where modulecode="+agentmasterobject.getAgentType());				
                    return agtno;
                }
                else if(agentmasterobject.getAgentNo()>0){				
                    return agentmasterobject.getAgentNo();
                }
            }
            else if(int_verify==2)//to verify data
            {
            	System.out.println(curdatetime+"============>>>>>>>>>"+agentmasterobject.getVeTml()+"-----"+agentmasterobject.getVeUser()+"---------"+agentmasterobject.getAgentNo());
                int updated = stmt.executeUpdate("Update AgentMaster set ref_ind=3,close_ind=0,ve_tml='"+agentmasterobject.getVeTml()+"',ve_user='"+agentmasterobject.getVeUser()+"',ve_date='"+curdatetime+"' where ac_type='"+agentmasterobject.getAgentType()+"' and ac_no="+agentmasterobject.getAgentNo()+" and ref_ind=1");
                System.out.println("(((((((((((----------------)))))))))))"+updated);
                return updated;		
            }
            
        }catch(SQLException e){
            e.printStackTrace();
            sessionContext.setRollbackOnly();
        } catch (CreateException e) {
            e.printStackTrace();
        }
        finally{
            try {
                common = null;
                connection.close();				
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (EJBException e) {
                e.printStackTrace();
            }
        }
        return agtno;
        
    }

    
    /**
     * used to Update ref_ind field in AgentMaster Table.
     * 
     * @param //agttype
     * @param //agentno
     * @return
     */
    public int getPAGRefIndUpdate(String ac_type,int ac_no,int id)
    {
    	Connection conn=null;
    	PreparedStatement pstmt=null;
    	try{
    		System.out.println("Inside updation of Ref_Ind..");
    		System.out.println("AcTy: "+ac_type);
    		System.out.println("AcNo: "+ac_no);
            System.out.println("id: "+id);
    		conn=getConnection();
    		pstmt=conn.prepareStatement("update AgentMaster set ref_ind=? where ac_type=? and ac_no=? and ref_ind=? and ve_tml is null");
            
    		switch(id){
    		case 1:
    		case 2:
    		{
    			System.out.println("#####");
    			pstmt.setInt(1,2);//ref_ind=2 for Updation case
    			pstmt.setString(2,ac_type);
    			pstmt.setInt(3,ac_no);
    			pstmt.setInt(4,id);
    			if(pstmt.executeUpdate()==1)
    			{System.out.println("Ref_ind Updated");
    			return 1;
    			}
    		}
    		case 3:
    		{
    			
    		}
    		default:
    			return 0;    			
    		}
    	  	
    	}catch(SQLException se){se.printStackTrace();}
    	
    	finally{
    		try{
    			conn.close();
    		}catch(SQLException e){e.printStackTrace();}
    	}
      return 0;	
    }
    
    
    public PygmyMasterObject[] getAgtClose(String agttype,int agentno)
    {
        Statement stmt;
        ResultSet rs;
        PygmyMasterObject[] pm=null;
        Connection connection = null;
        try
        {
            connection = getConnection();
            stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs=stmt.executeQuery("select pm.ac_type,pm.ac_no,pm.agt_no from PygmyMaster pm,AgentMaster am where pm.agt_type='"+agttype+"' and pm.agt_no="+agentno+" and am.close_ind=0 and pm.agt_no=am.ac_no");
            System.out.println("inside get agent close");        
            rs.last();
            pm=new PygmyMasterObject[rs.getRow()];
            rs.beforeFirst();
            int i=0;
            while(rs.next())
            {
                pm[i]=new PygmyMasterObject();
                pm[i].setAccType(rs.getString("pm.ac_type"));
                pm[i].setAccNo(rs.getInt("pm.ac_no"));
                i++;
            }
            
            rs.close();
        }catch (Exception e){e.printStackTrace();}
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        System.out.println("agent close lngth=="+pm.length);
        return pm;
    }
    
    public PygmyMasterObject[] getAccountsUnderAgent(String agttype,int agentno)
    {
        Statement stmt;
        ResultSet rs;
        PygmyMasterObject[] pm=null;
        Connection connection = null;
        try
        {
            connection = getConnection();
            System.out.println("agent type=="+agttype);
            System.out.println("agent no=="+agentno);
            stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            //rs=stmt.executeQuery("select moduleabbr,pm.ac_type,pm.ac_no,pm.cid,pm.addr_type,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name from PygmyMaster pm,Modules,AgentMaster am,CustomerMaster cm where modulecode=pm.ac_type and pm.agt_type='"+agttype+"' and pm.agt_no="+agentno+" and pm.agt_no=am.ac_no and cm.cid=pm.cid and am.close_ind=0");
            rs=stmt.executeQuery("select moduleabbr,pm.ac_type,pm.ac_no,pm.cid,pm.addr_type,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name from PygmyMaster pm,Modules,AgentMaster am,CustomerMaster cm where modulecode=pm.ac_type and pm.agt_type='"+agttype+"' and pm.agt_no="+agentno+" and pm.agt_no=am.ac_no and cm.cid=pm.cid and pm.ac_no not in (select agtch.ac_no from AgentChange agtch where agtch.ref_ind=1) and am.close_ind=0");
            if(rs.next())
          {
            rs.last();
            pm=new PygmyMasterObject[rs.getRow()];
            System.out.println("pm length inside=="+pm.length);
            rs.beforeFirst();
            int i=0;
            while(rs.next())
            {
                //System.out.println("inside while lop=="+i);
                pm[i]=new PygmyMasterObject();
                pm[i].setAccType(rs.getString("moduleabbr"));
                pm[i].setModulecode(rs.getString("pm.ac_type"));
                pm[i].setAccNo(rs.getInt("pm.ac_no"));
                pm[i].setCid(rs.getInt("pm.cid"));
                pm[i].setName(rs.getString("name"));
                pm[i].setAddrType(rs.getInt("pm.addr_type"));				
                i++;
            }			
            rs.close();
          }
        }catch (Exception e){e.printStackTrace();}
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }	
        
        return pm;		
    }
    
    
    public PygmyTransactionObject[] getAgtWiseRemittanceReport(int agentno,String agent_type,String date)
    {
        Statement stmt=null;
        ResultSet rs=null;
        PygmyTransactionObject[] pt=null;
        Connection connection = null;
        try
        {
            connection = getConnection();
            stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //rs=stmt.executeQuery("select mo.moduleabbr,pt.ac_no,pt.trn_amt from Modules mo,PygmyMaster pm,PygmyTransaction pt where mo.modulecode=pt.ac_type and trn_date='"+date+"' and pm.agt_no="+agentno+" and pm.agt_type='"+agent_type+"' and pm.ac_type=pt.ac_type and pm.ac_no=pt.ac_no");
            System.out.println("before rs");
            rs=stmt.executeQuery("select distinct mo.moduleabbr,pt.ac_no,pt.trn_amt,pt.coll_date,concat_ws(' ',cm.salute,cm.fname,cm.mname,cm.lname) as pdname from Modules mo,PygmyMaster pm,PygmyTransaction pt,CustomerMaster cm where pm.agt_no="+agentno+" and pm.agt_type='1013001' and pt.trn_date='"+date+"' and pt.trn_type='R' and cm.cid=pm.cid and mo.modulecode=pt.ac_type and pm.ac_type=pt.ac_type and pm.ac_no=pt.ac_no");
            System.out.println("after rs");
            rs.last();
            pt = new PygmyTransactionObject[rs.getRow()];
            rs.beforeFirst();
            int i=0;
            while(rs.next()){			
                pt[i] = new PygmyTransactionObject();
                pt[i].setAccType(rs.getString("moduleabbr"));
                pt[i].setAccNo(rs.getInt("ac_no"));
                pt[i].setTranAmt(rs.getDouble("trn_amt")); //Remitted Anmount
                pt[i].setName(rs.getString("pdname"));
                pt[i].setCollectionDate(rs.getString("coll_date"));
                i++;
            }
            rs.close();
        }catch (Exception e){e.printStackTrace();}
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return pt;
    }
    /**
     * This will get the closing balance of the pygmy account.
     * @return - Balance amount
     */
    public double getClosingBalance(String ac_type,int ac_no)
    {
        Connection connection = null;
        try {
            connection = getConnection();
            g_pgacctype=ac_type;
            g_pgaccno=ac_no;
            
            Statement stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            
            System.out.println("getting CLosing Balance for Pygmy Account..");
            //rs= stmt.executeQuery("select cl_bal from PygmyTransaction where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and trn_type!='E' order by trn_seq desc limit 1");
            rs=stmt.executeQuery("select cl_bal from PygmyTransaction where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and trn_type not in('E','W')  order by trn_seq desc limit 1");
            
            rs.next();
            if(rs.getRow()>0){
                return rs.getDouble(1);
            }			
            
            System.out.println("Returning from 'getClosingBalance()'...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return 0;	
    }
    
    /**
     * 
     * @param ac_type
     * @param ac_no
     * @param id => 0 to Check is there any Records Available in PygmyTransaction table.
     *              1 for to Check is there any unverified records available in DailyRemittance Table.
     * @return
     */
    
    public boolean checkPygTran(String ac_type,int ac_no,int id)
    {
    	Connection connection = null;
    	ResultSet rs=null;
    	boolean flag=false;
        try {
            connection = getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(id==0)
            {
            	rs=stmt.executeQuery("select * from PygmyTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" ");
            	 rs.next();
                 rs.last();
                 if(rs.getRow()>0) 
                 flag=true;  //=> coz, some transaction records i.e Bal Amt shld be available to Withdraw money.
                 else
                 flag=false;
                
            
            }
            else if(id==1)
            {
            	rs=stmt.executeQuery("select * from DailyRemittance where ac_type='"+ac_type+"' and ac_no="+ac_no+" and ve_tml is null and trn_seq is null ");	
            rs.next();
            rs.last();
            if(rs.getRow()>0)
            flag=false;
            else
            flag=true;
            
            /*rs=null;
            rs=stmt.executeQuery("select * from PygmyTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and ve_tml is null");
            rs.next();
            rs.last();
            if(rs.getRow()>0)
            flag=false;
            else
            flag=true;*/
            }
            return flag;
            
            
            
        }catch(SQLException se){se.printStackTrace();}
        finally{
        	try{
        		connection.close();
        	}catch(SQLException s){s.printStackTrace();}
        }
        return false;
        
    }
    
    
    /**
     * ths is used to get the Pygmy Withdrawal / Closure details of the particular Pygmy Account
     * @return  array of object 'pt'- getting data from the server,setting into the object.
     */
    public PygmyTransactionObject[] getClosureDetails(String ac_type,int ac_no)
    {
        g_pgacctype=ac_type;
        g_pgaccno=ac_no;
        System.out.println("Inside getClsureDetails..");
        PygmyTransactionObject pt[]=null;
        Connection connection = null;
        try {
            connection = getConnection();
            commonLocalHome = (CommonLocalHome) new InitialContext().lookup("COMMONLOCALWEB");
            CommonLocal g_commonRemote = commonLocalHome.create();
            //CommonHome commonHome = (CommonHome) HomeFactory.getFactory().lookUpHome("COMMONWEB");
    		//CommonRemote g_commonRemote = commonHome.create();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //ResultSet rs = stmt.executeQuery("select * from PygmyTransaction where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and trn_type='W' order by trn_seq");
            //Reason: It has to select Interest or Penalty information also if it is available in Transaction table.
            ResultSet rs=stmt.executeQuery("select * from PygmyTransaction where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and (trn_type='W' or trn_type='E' or trn_type='S') and ve_tml is null order by trn_seq");
            
            rs.last();
            pt = new PygmyTransactionObject[rs.getRow()];
            System.out.println("pt array : "+pt.length);
            rs.beforeFirst();
            int i=0;
            while(rs.next()){
                pt[i]=new PygmyTransactionObject();
                pt[i].setTranAmt(rs.getDouble("trn_amt"));
                pt[i].setTranDate(rs.getString("trn_date"));
                if(rs.getString("trn_mode").equalsIgnoreCase("T") && (!rs.getString("trn_narr").equalsIgnoreCase("Penalty") && !rs.getString("trn_narr").equalsIgnoreCase("Intr") && !rs.getString("trn_narr").equalsIgnoreCase("Closure-PayOrder") && !rs.getString("trn_narr").equalsIgnoreCase("Pay Order")))
                {
                	StringTokenizer st=new StringTokenizer(rs.getString("trn_narr"),"-");
                	
                	String payac_type=st.nextToken();
                	String payAc_type=st.nextToken();
                  	int payac_no=Integer.parseInt(payAc_type);
                  	System.out.println("------->>>>>>>-----"+payac_no);
                	String payname=g_commonRemote.getName(payac_type,payac_no);
                	pt[i].setName(payname);
                	
                }
                pt[i].setTranNarration(rs.getString("trn_narr"));
                pt[i].setTranMode(rs.getString("trn_mode"));
                pt[i].setCloseBal(rs.getDouble("cl_bal"));
                pt[i].setTranType(rs.getString("trn_type"));
                pt[i].setVe_tml(rs.getString("ve_tml"));
                pt[i].setRef_ind(rs.getInt("ref_ind"));
                
                System.out.println("Verifi -Tml: "+rs.getString("ve_tml"));
                i++;
            }
            System.out.println("Before Closing of getClosuredetails..");
            
        } catch (SQLException e) {e.printStackTrace();}
        catch(NamingException ne){
        	ne.printStackTrace();
        }
        catch(CreateException ec){
        	ec.printStackTrace();
        }
        catch(CustomerNotFoundException ce){
        	ce.printStackTrace();
        }
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return pt;
        
        
    }
    
    private Connection getConnection()throws SQLException{
        return ds.getConnection("root","");
    }
    
    public PygmyMasterObject[] getOpenClosedReport(String from_date,String to_date,int type)
    {
        Statement stmt,stmt1;
        ResultSet rs = null;
        ResultSet rs1=null;
        
        PygmyMasterObject pm[]=null;
        Connection connection = null;
        try{
        	System.out.println("Inside getOpenClosedReport()....");
            System.out.println("From Date: "+from_date);
            System.out.println("To Date: "+to_date);
            System.out.println("frm dt:  "+Validations.convertYMD(from_date));
            System.out.println("to dt: "+Validations.convertYMD(to_date));
        	connection = getConnection();
            stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt1=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if(type==0)//to retrieve Active Accounts
            {
                //rs=stmt.executeQuery("select mo.moduleabbr,mo2.moduleabbr,pgm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,concat_ws(' ',cm2.fname,cm2.mname,cm2.lname)as agtname from AgentMaster am,PygmyMaster pgm,CustomerMaster cm,CustomerMaster cm2,Modules mo,Modules mo2 where pgm.close_date is null and cm.cid=pgm.cid and am.ac_type=pgm.agt_type and am.ac_no=pgm.agt_no and cm2.cid=am.cid and mo.modulecode=pgm.ac_type and mo2.modulecode=am.ac_type and concat(right(open_date,4),'-',mid(open_date,locate('/',open_date)+1,(locate('/',open_date,4)-locate('/',open_date)-1)),'-',left(open_date,locate('/',open_date)-1)) between '"+from_date+"' and '"+to_date+"' and pgm.ve_tml is not null order by pgm.ac_type,pgm.ac_no");
                rs=stmt.executeQuery("select distinct mo.moduleabbr,mo2.moduleabbr,pgm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,cad.address,cad.city,cad.state,cad.pin,concat_ws(' ',cm2.fname,cm2.mname,cm2.lname)as agtname from AgentMaster am,PygmyMaster pgm,CustomerMaster cm,CustomerMaster cm2,Modules mo,Modules mo2,CustomerAddr cad where pgm.close_date is null and cm.cid=pgm.cid and am.ac_type=pgm.agt_type and am.ac_no=pgm.agt_no and cm2.cid=am.cid and cad.cid=pgm.cid and cad.addr_type=pgm.addr_type and mo.modulecode=pgm.ac_type and mo2.modulecode=am.ac_type and concat(right(open_date,4),'-',mid(open_date,locate('/',open_date)+1,(locate('/',open_date,4)-locate('/',open_date)-1)),'-',left(open_date,locate('/',open_date)-1)) between '"+Validations.convertYMD(from_date)+"' and '"+Validations.convertYMD(to_date)+"' and pgm.ve_tml is not null order by pgm.ac_type,pgm.ac_no");
            	
            	System.out.println("1@....");
            }
            else if(type==1)//to retrieve closed Accounts
            {   //rs=stmt.executeQuery("select mo.moduleabbr,mo2.moduleabbr,pgm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,concat_ws(' ',cm2.fname,cm2.mname,cm2.lname)as agtname from PygmyMaster pgm,AgentMaster pag,CustomerMaster cm,CustomerMaster cm2,Modules mo,Modules mo2 where cm.cid=pgm.cid and pag.ac_type=pgm.agt_type and pag.ac_no=pgm.agt_no and cm2.cid=pag.cid and mo.modulecode=pgm.ac_type and mo2.modulecode=pag.ac_type and pgm.status='C' and concat(right(pgm.close_date,4),'-',mid(pgm.close_date,locate('/',pgm.close_date)+1,(locate('/',pgm.close_date,4)-locate('/',pgm.close_date)-1)),'-',left(pgm.close_date,locate('/',pgm.close_date)-1)) between '"+from_date+"' and '"+to_date+"' and pgm.close_date is not null and pgm.ve_tml is not null order by pgm.ac_type,pgm.ac_no");
                  rs=stmt.executeQuery("select mo.moduleabbr,mo2.moduleabbr,pgm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,concat_ws(' ',cm2.fname,cm2.mname,cm2.lname)as agtname,cad.address,cad.city,cad.state,cad.pin from PygmyMaster pgm,AgentMaster pag,CustomerMaster cm,CustomerMaster cm2,Modules mo,Modules mo2,CustomerAddr cad where cm.cid=pgm.cid and pag.ac_type=pgm.agt_type and pag.ac_no=pgm.agt_no and cm2.cid=pag.cid and cad.cid=pgm.cid and cad.addr_type=pgm.addr_type and mo.modulecode=pgm.ac_type and mo2.modulecode=pag.ac_type and pgm.status='C' and concat(right(pgm.close_date,4),'-',mid(pgm.close_date,locate('/',pgm.close_date)+1,(locate('/',pgm.close_date,4)-locate('/',pgm.close_date)-1)),'-',left(pgm.close_date,locate('/',pgm.close_date)-1)) between '"+Validations.convertYMD(from_date)+"' and '"+Validations.convertYMD(to_date)+"' and pgm.close_date is not null and pgm.ve_tml is not null order by pgm.ac_type,pgm.ac_no");
            }
            else if(type==2)// to retrieve All Accounts
                 //rs=stmt.executeQuery("select mo.moduleabbr,mo2.moduleabbr,pgm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,concat_ws(' ',cm2.fname,cm2.mname,cm2.lname)as agtname from PygmyMaster pgm,AgentMaster pag,CustomerMaster cm,CustomerMaster cm2,Modules mo,Modules mo2 where cm.cid=pgm.cid and pag.ac_type=pgm.agt_type and pag.ac_no=pgm.agt_no and cm2.cid=pag.cid and mo.modulecode=pgm.ac_type and mo2.modulecode=pag.ac_type and concat(right(open_date,4),'-',mid(open_date,locate('/',open_date)+1,(locate('/',open_date,4)-locate('/',open_date)-1)),'-',left(open_date,locate('/',open_date)-1)) between '"+from_date+"' and '"+to_date+"' and pgm.ve_tml is not null order by pgm.ac_type,pgm.ac_no");
            	//rs=stmt.executeQuery("select distinct mo.moduleabbr,mo2.moduleabbr,pgm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,concat_ws(' ',cm2.fname,cm2.mname,cm2.lname)as agtname,cad.address,cad.city,cad.state,cad.pin from PygmyMaster pgm,AgentMaster pag,CustomerMaster cm,CustomerMaster cm2,Modules mo,Modules mo2,CustomerAddr cad where cm.cid=pgm.cid and cad.cid=pgm.cid and cad.addr_type=pgm.addr_type and pag.ac_type=pgm.agt_type and pag.ac_no=pgm.agt_no and cm2.cid=pag.cid and mo.modulecode=pgm.ac_type and mo2.modulecode=pag.ac_type and concat(right(open_date,4),'-',mid(open_date,locate('/',open_date)+1,(locate('/',open_date,4)-locate('/',open_date)-1)),'-',left(open_date,locate('/',open_date)-1)) between '"+from_date+"' and '"+to_date+"' and pgm.ve_tml is not null order by pgm.ac_type,pgm.ac_no");
            	
            rs=stmt.executeQuery("select distinct mo.moduleabbr,mo2.moduleabbr,pgm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,cad.address,cad.city,cad.state,cad.pin,concat_ws(' ',cm2.fname,cm2.mname,cm2.lname)as agtname from AgentMaster am,PygmyMaster pgm,CustomerMaster cm,CustomerMaster cm2,Modules mo,Modules mo2,CustomerAddr cad where pgm.close_date is null and cm.cid=pgm.cid and am.ac_type=pgm.agt_type and am.ac_no=pgm.agt_no and cm2.cid=am.cid and cad.cid=pgm.cid and cad.addr_type=pgm.addr_type and mo.modulecode=pgm.ac_type and mo2.modulecode=am.ac_type and concat(right(open_date,4),'-',mid(open_date,locate('/',open_date)+1,(locate('/',open_date,4)-locate('/',open_date)-1)),'-',left(open_date,locate('/',open_date)-1)) between '"+Validations.convertYMD(from_date)+"' and '"+Validations.convertYMD(to_date)+"' and pgm.ve_tml is not null order by pgm.ac_type,pgm.ac_no");
            rs1=stmt1.executeQuery("select mo.moduleabbr,mo2.moduleabbr,pgm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,concat_ws(' ',cm2.fname,cm2.mname,cm2.lname)as agtname,cad.address,cad.city,cad.state,cad.pin from PygmyMaster pgm,AgentMaster pag,CustomerMaster cm,CustomerMaster cm2,Modules mo,Modules mo2,CustomerAddr cad where cm.cid=pgm.cid and pag.ac_type=pgm.agt_type and pag.ac_no=pgm.agt_no and cm2.cid=pag.cid and cad.cid=pgm.cid and cad.addr_type=pgm.addr_type and mo.modulecode=pgm.ac_type and mo2.modulecode=pag.ac_type and pgm.status='C' and concat(right(pgm.close_date,4),'-',mid(pgm.close_date,locate('/',pgm.close_date)+1,(locate('/',pgm.close_date,4)-locate('/',pgm.close_date)-1)),'-',left(pgm.close_date,locate('/',pgm.close_date)-1)) between '"+Validations.convertYMD(from_date)+"' and '"+Validations.convertYMD(to_date)+"' and pgm.close_date is not null and pgm.ve_tml is not null order by pgm.ac_type,pgm.ac_no");
            rs1.last();
                  
            
            rs.last();
            
            if(type==2)
            pm=new PygmyMasterObject[rs.getRow()+rs1.getRow()];
            else
            pm=new PygmyMasterObject[rs.getRow()];	
            
            rs.beforeFirst();
            int i=0;
            while(rs.next())
            {
                System.out.println("i val="+i);
                pm[i]=new PygmyMasterObject();
                pm[i].setAccType(rs.getString("mo.moduleabbr"));
                pm[i].setAccNo(rs.getInt("ac_no"));
                pm[i].setName(rs.getString("pygname"));
                pm[i].setAgentName(rs.getString("agtname"));
                pm[i].setAgentType(rs.getString("mo2.moduleabbr"));
                pm[i].setAgentNo(rs.getString("agt_no"));
                pm[i].setAccOpenDate(rs.getString("pgm.open_date"));
                pm[i].setAccCloseDate(rs.getString("pgm.close_date"));
                pm[i].setAddress(rs.getString("address"));
                pm[i].setCity(rs.getString("city"));
                pm[i].setState(rs.getString("state"));
                pm[i].setState(rs.getString("pin"));
                
                i++;
            }
            
            //rs.close();
                     
            if(type==2)
            {	
            rs1.beforeFirst();           
            while(rs1.next())
            {
            	pm[i]=new PygmyMasterObject();
                pm[i].setAccType(rs1.getString("mo.moduleabbr"));
                pm[i].setAccNo(rs1.getInt("ac_no"));
                pm[i].setName(rs1.getString("pygname"));
                pm[i].setAgentName(rs1.getString("agtname"));
                pm[i].setAgentType(rs1.getString("mo2.moduleabbr"));
                pm[i].setAgentNo(rs1.getString("agt_no"));
                pm[i].setAccOpenDate(rs1.getString("pgm.open_date"));
                pm[i].setAccCloseDate(rs1.getString("pgm.close_date"));
                pm[i].setAddress(rs1.getString("address"));
                pm[i].setCity(rs1.getString("city"));
                pm[i].setState(rs1.getString("state"));
                pm[i].setState(rs1.getString("pin"));
                
              i++;	
            }
            }
            System.out.println("Returning from getOpenClosedReport()...");
        }catch(SQLException e){e.printStackTrace();}
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        System.out.println("acc lngth="+pm.length);
        return pm;
    }
    
    
    public PygmyMasterObject getPygmyDetails() throws AccountNotFoundException
    {
        System.out.println("Inside getPygmyDetails");
        System.out.println("g_pgacctype: "+g_pgacctype);
        System.out.println("g_pgaccno: "+g_pgaccno);
        return getPygmyDetails(g_pgacctype,g_pgaccno); //It calls another function.Here 'g_pgacctype' & 'g_pgaccno' are Global variables which are taken when Beans instance created. 
    }
    
    /**
     * 
     * @param pgtype - modulecode of Pygmy Account Type '1006001'
     * @param pgno - Pygmy Account Number which is entered in the application.
     * @return - PygmyMaster Object 'pm'.If no records found for the given Pygmy Account Number, in 'pm' object Account Number will be set as '-1'.If record found Pygmy Account Number will be set into the object.
     * @throws //pygmyDeposit.AccountNotFoundException
     */
    public PygmyMasterObject getPygmyDetails(String pgtype,int pgno) throws AccountNotFoundException
    {
        Statement stmt;
        ResultSet rs=null;
        PygmyMasterObject pm=new PygmyMasterObject();
        Connection connection=null;
             
        
        try
        {   
            System.out.println("Inside getPygmyDetails1....");
            System.out.println("pgType: "+pgtype);
            System.out.println("pgno:  "+pgno);
            
            common = commonLocalHome.create();
            System.out.println("Here......");
            connection = getConnection();
            stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
                     
            //rs=stmt.executeQuery("select mo.moduleabbr,mo2.moduleabbr,pgm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)as agtname,concat_ws(' ',cm2.fname,cm2.mname,cm2.lname)as name from Modules mo,Modules mo2,PygmyMaster pgm,CustomerMaster cm,CustomerMaster cm2 where cm2.cid=pgm.cid and pgm.ac_type='"+pgtype+"' and pgm.ac_no="+pgno+" and mo.modulecode=pgm.ac_type and cm.cid=pgm.cid and mo2.modulecode=agt_type");
            rs=stmt.executeQuery("select mo.moduleabbr,mo2.moduleabbr,pgm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name from Modules mo,Modules mo2,PygmyMaster pgm,CustomerMaster cm where pgm.ac_type='"+pgtype+"' and pgm.ac_no="+pgno+" and  cm.cid=pgm.cid and mo.modulecode=pgm.ac_type and mo2.modulecode=pgm.agt_type");
            //rs=stmt.executeQuery("select mo.moduleabbr,mo2.moduleabbr,pgm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,pt.cl_bal from Modules mo,Modules mo2,PygmyMaster pgm,CustomerMaster cm,PygmyTransaction pt where pgm.ac_type='"+pgtype+"' and pgm.ac_no="+pgno+" and  mo.modulecode=pgm.ac_type and cm.cid=pgm.cid and mo2.modulecode=pgm.agt_type and pgm.lst_trn_seq=pt.trn_seq and pgm.ac_type=pt.ac_type and pgm.ac_no=pt.ac_no");
            System.out.println("B1.......");
            
            if(rs.next())
            {				
                System.out.println("Inside...");
                //rs.beforeFirst();
               
                System.out.println("AccNo: "+rs.getInt("ac_no"));
                
                pm.setAccType(rs.getString("mo.moduleabbr"));
                pm.setAccNo(rs.getInt("ac_no"));
                pm.setCid(rs.getInt("cid"));
                pm.setAddrType(rs.getInt("addr_type"));
                
                pm.setName(rs.getString("name"));
                
                
                //pm.setAgentName(rs.getString("agtname"));
                pm.setJointHolders(rs.getInt("no_jt_hldr"));
                pm.setAgentType(rs.getString("mo2.moduleabbr"));
                pm.setAgentNo(rs.getString("agt_no"));
                pm.setNomineeNo(rs.getInt("nom_no"));
                pm.setAccStatus(rs.getString("status"));
                pm.setLastTrnSeq(rs.getInt("lst_trn_seq"));
                pm.setLastIntrDate(rs.getString("lst_int_dt"));
                pm.setLastWDDate(rs.getString("lst_wdl_dt"));
                pm.setLastWDNo(rs.getInt("lst_wdl_no"));
                pm.setLnAvailed(rs.getString("ln_avld"));
                pm.setLnAccType(rs.getString("ln_ac_type"));
                pm.setLnAccNo(rs.getInt("ln_ac_no"));
                pm.setWDAmt(rs.getDouble("wdl_amt"));
                pm.setPayMode(rs.getString("pay_mode"));
                pm.setPayAccType(rs.getString("pay_ac_type"));
                pm.setPayAccNo(rs.getInt("pay_ac_no"));
                pm.setAccOpenDate(rs.getString("open_date"));
                pm.setAccCloseDate(rs.getString("close_date"));
                pm.setLdgrPrtDate(rs.getString("ldgrprtdt"));
                pm.setRef_ind(rs.getInt("ref_ind"));
                
                /**
                 * Closing Balance from PygmyTransaction table.
                 */
                System.out.println("1....");
                
                rs=null;
                ResultSet rs1=null;
                rs1=stmt.executeQuery("select pt.cl_bal from PygmyMaster pgm,PygmyTransaction pt,Modules mo,CustomerMaster cm where pgm.ac_type='"+pgtype+"' and pgm.ac_no="+pgno+" and pgm.lst_trn_seq=pt.trn_seq and pgm.ac_type=pt.ac_type and pgm.ac_no=pt.ac_no and cm.cid=pgm.cid and mo.modulecode=pgm.ac_type");
                if(rs1.next())
                {
                    pm.setPrevBalance(rs1.getDouble("cl_bal"));	
                }
                
                System.out.println("1..1.....");
                
                rs1=null;
                rs1=stmt.executeQuery("select concat_ws(' ',cm.fname,cm.mname,cm.lname)as agtname from CustomerMaster cm,PygmyMaster pm,AgentMaster am where pm.ac_type='"+pgtype+"' and pm.ac_no='"+pgno+"' and pm.agt_no=am.ac_no and pm.agt_type=am.ac_type and am.cid=cm.cid");	
                if(rs1.next())
                {
                    pm.setAgentName(rs1.getString("agtname"));			  
                }
                
                if(pm.getPayAccType()!=null){
                rs1=null;
                rs1=stmt.executeQuery("select concat_ws(' ',cm.salute,cm.fname,cm.mname,cm.lname)as payaccname from CustomerMaster cm,PygmyMaster pm,AgentMaster agm,AccountMaster am  where pm.ac_type='"+pgtype+"' and pm.ac_no="+pgno+" and agm.ac_no=pm.agt_no and agm.ac_type=pm.agt_type and am.ac_type=pm.pay_ac_type and am.ac_no=pm.pay_ac_no and cm.cid=am.cid");
                if(rs1.next()){
                	pm.setPay_acc_name(rs1.getString("payaccname"));
                }
                }
                
                System.out.println("2.....");
                rs1=null;
                rs1 = stmt.executeQuery("select cid,addr_type from JointHolders where ac_type='"+pgtype+"' and ac_no='"+pgno+"'");
                int j=0;
                int addrtype[]=null;
                int cids[]=null;
                if(rs1.next())
                {
                    rs1.last();
                    addrtype=new int[rs1.getRow()];
                    cids=new int[rs1.getRow()];
                    rs1.beforeFirst();				
                }
                
                System.out.println("3.....");
                while(rs1.next())
                {
                    addrtype[j]=rs1.getInt(2);
                    cids[j++]=rs1.getInt(1);
                }
                
                pm.setJointAddrType(addrtype);
                pm.setJointCid(cids);
                System.out.println("nom no in pyg det******************=="+pm.getNomineeNo());
                pm.setNomineeDetails(common.getNominee(pm.getNomineeNo()));
                System.out.println("get nom details inside bean*************************=="+pm.getNomineeDetails());
                pm.setSigObj(common.getSignatureDetails(pgno,pgtype));
                
                
                System.out.println("4..");
                System.out.println(" Returning from getPygmyDetails1....");
                rs1.close();
                
                return pm;
            }else{ // end of master_data_count >
            pm.setAccNo(-1);
            //rs.close();
            }
            System.out.println("pm acc no="+pm.getAccNo());
            return pm;			
        }catch(SQLException e){e.printStackTrace();} catch (CreateException e) {
            e.printStackTrace();
        }
        finally{
            try {
                common = null;
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }		
        throw new AccountNotFoundException();
        //throw new pygmyDeposit.AccountNotFoundException();
    }
    
    public PygmyMasterObject[] getPygmyLedgerReport(int type,String from_date,String to_date,int start_accno,int end_accno)
    {
        Statement stmt;
        ResultSet rs;
        PygmyMasterObject pm[]=null;
        Connection connection = null;
        try {
            connection = getConnection();
            stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            System.out.println("Typesss       "+type);
            
            if(type==0) //ACTIVE ACCOUNTS
            {
                System.out.println("Typesss 2      "+type);
                System.out.println("frm dt: "+from_date);
                System.out.println("to dt: "+to_date);
                System.out.println("frm dt:  "+Validations.convertYMD(from_date));
                System.out.println("to dt: "+Validations.convertYMD(to_date));
                //rs=stmt.executeQuery("select PygmyMaster.*,name,relation,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,concat_ws(cm1.fname,cm1.mname,cm1.lname)as agentname from PygmyMaster,NomineeMaster,CustomerMaster cm,CustomerMaster cm1,AgentMaster am where am.cid=cm1.cid and PygmyMaster.cid=cm.cid and NomineeMaster.reg_no=PygmyMaster.nom_no and PygmyMaster.ac_no between "+start_accno+" and "+end_accno+" and PygmyMaster.agt_no=am.ac_no and PygmyMaster.agt_type=am.ac_type and concat(right(PygmyMaster.open_date,4),'-',mid(PygmyMaster.open_date,locate('/',PygmyMaster.open_date)+1,(locate('/',PygmyMaster.open_date,4)-locate('/',PygmyMaster.open_date)-1)),'-',left(PygmyMaster.open_date,locate('/',PygmyMaster.open_date)-1)) between '"+from_date+"' and '"+to_date+"' and PygmyMaster.close_date is null ");
                //rs=stmt.executeQuery("select pm.*,name,relation,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,concat_ws(' ',cm1.fname,cm1.mname,cm1.lname)as agentname from PygmyMaster pm,NomineeMaster nm,CustomerMaster cm,CustomerMaster cm1,AgentMaster am where  pm.ac_no between "+start_accno+" and "+end_accno+" and am.ac_no=pm.agt_no and am.ac_type=pm.agt_type and cm1.cid=am.cid and cm.cid=pm.cid and nm.reg_no=pm.nom_no   and concat(right(pm.open_date,4),'-',mid(pm.open_date,locate('/',pm.open_date)+1,(locate('/',pm.open_date,4)-locate('/',pm.open_date)-1)),'-',left(pm.open_date,locate('/',pm.open_date)-1)) between '"+from_date+"' and '"+to_date+"' and pm.close_date is null");
                rs=stmt.executeQuery("select pm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,concat_ws(' ',cm1.fname,cm1.mname,cm1.lname)as agentname from PygmyMaster pm,CustomerMaster cm,CustomerMaster cm1,AgentMaster am where  pm.ac_no between "+start_accno+" and "+end_accno+" and am.ac_no=pm.agt_no and am.ac_type=pm.agt_type and cm1.cid=am.cid and cm.cid=pm.cid and concat(right(pm.open_date,4),'-',mid(pm.open_date,locate('/',pm.open_date)+1,(locate('/',pm.open_date,4)-locate('/',pm.open_date)-1)),'-',left(pm.open_date,locate('/',pm.open_date)-1)) between '"+Validations.convertYMD(from_date)+"' and '"+Validations.convertYMD(to_date)+"' and pm.close_date is null order by pm.ac_no limit 1");
                
                rs.last();
                System.out.println("rs.getRow()== "+rs.getRow());
                pm=new PygmyMasterObject[rs.getRow()];
                rs.beforeFirst();
                int i=0;
                while(rs.next())
                {
                    pm[i]=new PygmyMasterObject();
                    pm[i].setAccNo(rs.getInt("ac_no"));
                    pm[i].setName(rs.getString("pygname"));
                    pm[i].setAgentNo(rs.getString("agt_no"));
                    pm[i].setAgentName(rs.getString("agentname"));
                    pm[i].setAccOpenDate(rs.getString("open_date"));
                    pm[i].setLnAvailed(rs.getString("ln_avld"));
                    pm[i].setLastIntrDate(rs.getString("lst_int_dt"));
                    pm[i].setPayMode(rs.getString("pay_mode"));
                    pm[i].setNomineeNo(rs.getInt("nom_no"));
                    pm[i].setPayAccType(rs.getString("pay_ac_type"));
                    pm[i].setPayAccNo(rs.getInt("pay_ac_no"));
                    i++;
                }
                
                rs.close();
                
                for(int r=0; r<pm.length; r++)
                {
                	if(pm[r].getNomineeNo()!=0)
                	{
                		rs=stmt.executeQuery("select name,relation from NomineeMaster where reg_no="+pm[r].getNomineeNo()+"");
                	    rs.next();
                	    pm[r].setNom_details(rs.getString(1)+" & "+rs.getString(2));
                	    System.out.println("Nom Acc No : "+pm[r].getAccNo());  
                	    System.out.println("Nom Nm : "+pm[r].getNom_details());
 
                	    
                	}else
                		pm[r].setNom_details("");
                }
                
            }
            
            else if(type==1) //CLOSED ACCOUNTS
            {
                rs=stmt.executeQuery("select PygmyMaster.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,concat_ws(' ',cm1.fname,cm1.mname,cm1.lname)as agentname from PygmyMaster,CustomerMaster cm,CustomerMaster cm1,AgentMaster am where am.cid=cm1.cid and PygmyMaster.cid=cm.cid and PygmyMaster.ac_no between "+start_accno+" and "+end_accno+" and PygmyMaster.agt_no=am.ac_no and PygmyMaster.agt_type=am.ac_type and concat(right(PygmyMaster.close_date,4),'-',mid(PygmyMaster.close_date,locate('/',PygmyMaster.close_date)+1,(locate('/',PygmyMaster.close_date,4)-locate('/',PygmyMaster.close_date)-1)),'-',left(PygmyMaster.close_date,locate('/',PygmyMaster.close_date)-1)) between '"+from_date+"' and '"+to_date+"' and PygmyMaster.close_date is not null");
                
                rs.last();
                pm=new PygmyMasterObject[rs.getRow()];
                rs.beforeFirst();
                int i=0;
                while(rs.next())
                {
                    pm[i]=new PygmyMasterObject();
                    pm[i].setAccNo(rs.getInt("ac_no"));
                    pm[i].setAgentName(rs.getString("agentname"));
                    pm[i].setAccOpenDate(rs.getString("open_date"));
                    pm[i].setLnAvailed(rs.getString("ln_avld"));
                    pm[i].setLastIntrDate(rs.getString("lst_int_dt"));
                    pm[i].setPayMode(rs.getString("pay_mode"));
                    pm[i].setNomineeNo(rs.getInt("nom_no"));
                    pm[i].setPayAccType(rs.getString("pay_ac_type"));
                    pm[i].setPayAccNo(rs.getInt("pay_ac_no"));
                    i++;
                }
                
                rs.close();
                
                for(int r=0; r<pm.length; r++)
                {
                	if(pm[r].getNomineeNo()!=0)
                	{
                		rs=stmt.executeQuery("select name,relation from NomineeMaster where reg_no="+pm[r].getNomineeNo()+"");
                	    rs.next();
                	    
                	    pm[r].setNom_details(rs.getString(1)+" & "+rs.getString(2));
                	    System.out.println("Nom Acc No : "+pm[r].getAccNo());  
                	    System.out.println("Nom Nm : "+pm[r].getNom_details());
 
                	    
                	}else
                		pm[r].setNom_details("");
                }
            }
            
            else if(type==2) //ALL ACCOUNTS
            {
                rs=stmt.executeQuery("select pg.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,concat_ws(' ',cm1.fname,cm1.mname,cm1.lname)as agentname from PygmyMaster pg,CustomerMaster cm,CustomerMaster cm1,AgentMaster am where pg.cid=cm.cid and am.cid=cm1.cid and pg.ac_no between "+start_accno+" and "+end_accno+" and pg.agt_no=am.ac_no and pg.agt_type=am.ac_type and ((concat(right(pg.open_date,4),'-',mid(pg.open_date,locate('/',pg.open_date)+1,(locate('/',pg.open_date,4)-locate('/',pg.open_date)-1)),'-',left(pg.open_date,locate('/',open_date)-1)) between '"+from_date+"' and '"+to_date+"') or (concat(right(pg.close_date,4),'-',mid(pg.close_date,locate('/',pg.close_date)+1,(locate('/',pg.close_date,4)-locate('/',pg.close_date)-1)),'-',left(pg.close_date,locate('/',pg.close_date)-1)) between '"+from_date+"' and '"+to_date+"'))");
                rs.last();
                pm=new PygmyMasterObject[rs.getRow()];
                rs.beforeFirst();
                int i=0;
                while(rs.next())
                {
                    pm[i]=new PygmyMasterObject();
                    pm[i].setAccNo(rs.getInt("ac_no"));
                    pm[i].setAgentName(rs.getString("agentname"));
                    pm[i].setAgentNo(rs.getString("agt_no"));
                    pm[i].setAccOpenDate(rs.getString("pg.open_date"));
                    pm[i].setLnAvailed(rs.getString("ln_avld"));
                    pm[i].setLastIntrDate(rs.getString("lst_int_dt"));
                    pm[i].setPayMode(rs.getString("pay_mode"));
                    pm[i].setNomineeNo(rs.getInt("nom_no"));
                    pm[i].setPayAccType(rs.getString("pay_ac_type"));
                    pm[i].setPayAccNo(rs.getInt("pay_ac_no"));
                    i++;
                }
                
                rs.close();
                
                for(int r=0; r<pm.length; r++)
                {
                	if(pm[r].getNomineeNo()!=0)
                	{
                		rs=stmt.executeQuery("select name,relation from NomineeMaster where reg_no="+pm[r].getNomineeNo()+"");
                	    rs.next();
                	    pm[r].setNom_details(rs.getString(1)+" & "+rs.getString(2));
                	    System.out.println("Nom Acc No : "+pm[r].getAccNo());  
                	    System.out.println("Nom Nm : "+pm[r].getNom_details());
 
                	    
                	}else
                		pm[r].setNom_details("");
                }
            }					
        }catch(SQLException e){e.printStackTrace();}
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        
        return pm;
    }
    
    public PygmyTransactionObject[] getPygmyLedgerTransaction(int type,String from_date,String to_date,int start_accno,int end_accno)
    {
        Statement stmt;
        ResultSet rs;
        PygmyTransactionObject[] pt=null;
        boolean next=false;
        Connection connection = null;
        try {
            connection = getConnection();
            
            System.out.println("type: "+type);
            System.out.println("frm dt: "+from_date);
            System.out.println("to dt: "+to_date);
            System.out.println("start accno: "+start_accno);
            System.out.println("End accno: "+end_accno);
            if(type==0)
            {
                stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                //rs=stmt.executeQuery("select pgm.*,pg.open_date from PygmyTransaction pgm,PygmyMaster pg where pgm.ac_no between "+start_accno+" and "+end_accno+" and concat(right(pgm.trn_date,4),'-',mid(pgm.trn_date,locate('/',pgm.trn_date)+1,(locate('/',pgm.trn_date,4)-locate('/',pgm.trn_date)-1)),'-',left(pgm.trn_date,locate('/',pgm.trn_date)-1)) between '"+from_date+"' and '"+to_date+"' and pg.open_date is not null and pgm.ac_no=pg.ac_no and pgm.ac_type=pg.ac_type");
               rs=stmt.executeQuery("select pgm.*,pg.open_date from PygmyTransaction pgm,PygmyMaster pg where pgm.ac_no between "+start_accno+" and "+end_accno+" and concat(right(pgm.trn_date,4),'-',mid(pgm.trn_date,locate('/',pgm.trn_date)+1,(locate('/',pgm.trn_date,4)-locate('/',pgm.trn_date)-1)),'-',left(pgm.trn_date,locate('/',pgm.trn_date)-1)) between '"+Validations.convertYMD(from_date)+"' and '"+Validations.convertYMD(to_date)+"' and pg.open_date is not null and pgm.ac_no=pg.ac_no and pgm.ac_type=pg.ac_type order by ac_no,trn_seq");
                try
                {
                    if(rs.next())
                    {
                        rs.getString(1);
                        next=true;
                    }
                }catch(Exception e){next=false;}
                
                if(next)
                {
                    rs.last();
                    pt=new PygmyTransactionObject[rs.getRow()];
                    rs.beforeFirst();
                    
                    int i=0;
                    while(rs.next())
                    {
                        pt[i]=new PygmyTransactionObject();
                        pt[i].setAccNo(rs.getInt("ac_no"));
                        pt[i].setTranDate(rs.getString("trn_date"));
                        pt[i].setTranAmt(rs.getDouble("trn_amt"));
                        pt[i].setTranNarration(rs.getString("trn_narr"));
                        pt[i].setCDInd(rs.getString("cd_ind"));
                        if(pt[i].getCDInd().equalsIgnoreCase("D")){
                        	pt[i].setDebAmt(rs.getDouble("trn_amt"));
                        }else if(pt[i].getCDInd().equalsIgnoreCase("C")){
                        	pt[i].setCrAmt(rs.getDouble("trn_amt"));
                        }
                        pt[i].setCloseBal(rs.getDouble("cl_bal"));
                        i++;
                    }
                    
                    rs.close();
                }
                else
                {
                    pt=new PygmyTransactionObject[1];
                    pt[0]=new PygmyTransactionObject();
                    pt[0].setAccNo(-1);
                    rs.close();
                }
            }
            if(type==1)
            {
                
                stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                //rs=stmt.executeQuery("select pgm.*,pg.close_date from PygmyTransaction pgm,PygmyMaster pg where pgm.ac_no between "+start_accno+" and "+end_accno+" and concat(right(pgm.trn_date,4),'-',mid(pgm.trn_date,locate('/',pgm.trn_date)+1,(locate('/',pgm.trn_date,4)-locate('/',pgm.trn_date)-1)),'-',left(pgm.trn_date,locate('/',pgm.trn_date)-1)) between '"+from_date+"' and '"+to_date+"' and pg.close_date is not null and pgm.ac_no=pg.ac_no and pgm.ac_type=pg.ac_type");
                rs=stmt.executeQuery("select pgm.*,pg.close_date from PygmyTransaction pgm,PygmyMaster pg where pgm.ac_no between "+start_accno+" and "+end_accno+" and concat(right(pgm.trn_date,4),'-',mid(pgm.trn_date,locate('/',pgm.trn_date)+1,(locate('/',pgm.trn_date,4)-locate('/',pgm.trn_date)-1)),'-',left(pgm.trn_date,locate('/',pgm.trn_date)-1)) between '"+from_date+"' and '"+to_date+"' and pg.close_date is not null and pgm.ac_no=pg.ac_no and pgm.ac_type=pg.ac_type order by ac_no,trn_seq");
                try
                {
                    if(rs.next())
                    {
                        rs.getString(1);
                        next=true;
                    }
                }catch(Exception e){next=false;}
                
                if(next)
                {
                    
                    rs.last();
                    pt=new PygmyTransactionObject[rs.getRow()];
                    rs.beforeFirst();
                    
                    int i=0;
                    while(rs.next())
                    {
                        pt[i]=new PygmyTransactionObject();
                        pt[i].setAccNo(rs.getInt("ac_no"));
                        pt[i].setTranDate(rs.getString("trn_date"));
                        pt[i].setTranAmt(rs.getDouble("trn_amt"));
                        pt[i].setTranNarration(rs.getString("trn_narr"));
                        pt[i].setCDInd(rs.getString("cd_ind"));
                        if(pt[i].getCDInd().equalsIgnoreCase("D")){
                        	pt[i].setDebAmt(rs.getDouble("trn_amt"));
                        }else if(pt[i].getCDInd().equalsIgnoreCase("C")){
                        	pt[i].setCrAmt(rs.getDouble("trn_amt"));
                        }
                        pt[i].setCloseBal(rs.getDouble("cl_bal"));
                        i++;
                    }
                    
                    rs.close();
                }
                else
                {
                    pt=new PygmyTransactionObject[1];
                    pt[0]=new PygmyTransactionObject();
                    pt[0].setAccNo(-1);
                    rs.close();
                }
                
                
                
            }
            
            if(type==2)
            {
                stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                rs=stmt.executeQuery("select * from PygmyTransaction where ac_no between "+start_accno+" and "+end_accno+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+from_date+"' and '"+to_date+"' order by ac_no,trn_seq");
                try
                {
                    if(rs.next())
                    {
                        rs.getString(1);
                        next=true;
                    }
                }catch(Exception e){next=false;}
                
                if(next)
                {
                    
                    rs.last();
                    pt=new PygmyTransactionObject[rs.getRow()];
                    rs.beforeFirst();
                    
                    int i=0;
                    while(rs.next())
                    {
                        pt[i]=new PygmyTransactionObject();
                        pt[i].setAccNo(rs.getInt("ac_no"));
                        pt[i].setTranDate(rs.getString("trn_date"));
                        pt[i].setTranAmt(rs.getDouble("trn_amt"));
                        pt[i].setTranNarration(rs.getString("trn_narr"));
                        pt[i].setCDInd(rs.getString("cd_ind"));
                        if(pt[i].getCDInd().equalsIgnoreCase("D")){
                        	pt[i].setDebAmt(rs.getDouble("trn_amt"));
                        }else if(pt[i].getCDInd().equalsIgnoreCase("C")){
                        	pt[i].setCrAmt(rs.getDouble("trn_amt"));
                        }
                        pt[i].setCloseBal(rs.getDouble("cl_bal"));
                        
                        i++;
                    }
                    
                    rs.close();
                }
                else
                {
                    pt=new PygmyTransactionObject[1];
                    pt[0]=new PygmyTransactionObject();
                    pt[0].setAccNo(-1);
                    rs.close();
                }
                
            }			
        }catch(Exception e){e.printStackTrace();}
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        System.out.println("pt length++++++="+pt.length);
        return pt;
    }
    
    
    /**
     * Ths is used to fetch data of a particular Pygmy Account Number for the given duration.
     * @param fdate - From Date
     * @param tdate - To Date
     * @return Array of object 'pt' with data.
     */
    public PygmyTransactionObject[] getPygmyTransaction(String accType,int acno,String fdate,String tdate)
    {
        Statement stmt,stmt_bf;
        ResultSet rs=null,rs_bf=null;
        PygmyTransactionObject[] pt=null;
        Connection connection = null;
        double bforward=0.00;
        try
        {
            connection = getConnection();
            System.out.println("account type==="+accType);
            System.out.println("account number==="+acno);
            System.out.println("from date=="+fdate);
            System.out.println("to date==="+tdate);
            stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt_bf=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            
            rs=stmt.executeQuery("select * from PygmyTransaction where ac_type='"+accType+"' and ac_no="+acno+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(fdate)+"' and '"+Validations.convertYMD(tdate)+"' and ve_tml is not null order by trn_seq");
            if(rs.next())
            {
            	rs.last();
            	pt=new PygmyTransactionObject[rs.getRow()];
            	rs.beforeFirst();
            }
            rs_bf=stmt_bf.executeQuery("select cl_bal from PygmyTransaction where ac_type='"+accType+"' and ac_no="+acno+" and  concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<'"+Validations.convertYMD(fdate)+"' order by trn_seq desc limit 1");
            if(rs_bf.next())
            	bforward=rs_bf.getDouble("cl_bal");
            int i=0;
            while(rs.next())
            {
                pt[i]=new PygmyTransactionObject();
                pt[i].setAccType(rs.getString("ac_type"));
                pt[i].setAccNo(rs.getInt("ac_no"));
                pt[i].setTranSeq(rs.getInt("trn_seq"));
                pt[i].setTranDate(rs.getString("trn_date"));
                pt[i].setTranType(rs.getString("trn_type"));
                pt[i].setTranAmt(rs.getDouble("trn_amt"));
                pt[i].setAmtPaid(rs.getDouble("amt_paid"));
                pt[i].setTranMode(rs.getString("trn_mode"));
                pt[i].setTranSource(rs.getString("trn_source"));
               
                pt[i].setCDInd(rs.getString("cd_ind"));
                if(pt[i].getCDInd().equalsIgnoreCase("D")){
                	pt[i].setDebAmt(rs.getDouble("trn_amt"));
                }else if(pt[i].getCDInd().equalsIgnoreCase("C")){
                	pt[i].setCrAmt(rs.getDouble("trn_amt"));
                }
                pt[i].setCloseBal(rs.getDouble("cl_bal"));
                pt[i].setRefNo(rs.getInt("ref_no"));
                pt[i].setTranNarration(rs.getString("trn_narr"));
                pt[i].setCollectionDate(rs.getString("coll_date"));
                pt[i].setInterestFrom(rs.getString("int_from"));
                pt[i].setPrnPaid(rs.getDouble("prn_paid"));
                pt[i].setInterestPaid(rs.getDouble("int_paid"));
                pt[i].setBforward(bforward);
                i++;
            }
            
            rs.close();
        }catch(Exception e){e.printStackTrace();}
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        
        return pt;
    }
    
    public PygmyMasterObject[] getUnverifiedPigmyDetails()
    {
        Statement stmt;
        ResultSet rs;
        PygmyMasterObject[] pm=null;
        Connection connection = null;
        try
        {
            connection = getConnection();
            stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            
            rs=stmt.executeQuery("select pgm.*,cm.fname,cm.mname,cm.lname from PygmyMaster pgm,CustomerMaster cm where status='V' and pgm.cid=cm.cid");
            
            int i=0;
            rs.last();
            pm=new PygmyMasterObject[rs.getRow()];
            rs.beforeFirst();
            
            while(rs.next())
            {
                pm[i]=new PygmyMasterObject();
                
                pm[i].setAccType(rs.getString("ac_type"));
                
                pm[i].setAccNo(rs.getInt("ac_no"));
                
                pm[i].setCid(rs.getInt("cid"));
                
                pm[i].setName(rs.getString("cm.fname")+" "+rs.getString("cm.mname")+" "+rs.getString("cm.lname"));
                
                pm[i].setAccStatus(rs.getString("status"));
                i++;
            }
        }catch(Exception e){e.printStackTrace();}
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }		
        return pm;
    }
    /**
     * To store the PygmyWithdrwal information into the database And To update the Transaction Details.
     * It will insert the new record in PygmyTransaction Table of trnsaction type 'W' and updates the trnsaction number
     * in PygmyMaster table for that Particular PygmyAccount Number.
     * In case of updation it will delete the previous transaction details which is not yet verified
     * and it will store the new record in child table & updates the parent tale.
     * @param amount - withdrwal amount which is entered by the user
     * @param pay_mode - 'C' for Cash or 'P' for PayOrder or 'T' for Transfer to any other Account 
     * @param pay_ac_type - To which Account type money has to be transfered SB/SBA/CA. 
     * @param pay_ac_no - To which Account Number money has to be transfered.
     * @return '1' if Transaction has successfully done,Otherwise returns '-1'
     * Fields entry in Detail table(PygmyTransaction): Trnsaction Type is 'W' for Withdrwal,Transaction Source is 'C',cd_ind is 'D' i.e Debit Entry Bcoz Bank is going to pay withdrwal amount to the PygmyAccount Holder,Transaction Narration is 'T' if it is Transfer Pay mode.
     */
    public int partialWithdraw(double amount,double penalty,String pay_mode,String pay_ac_type,int pay_ac_no,String curdate,String curdatetime)
    {
        System.out.println("Inside Partial Withdrawal...");
        System.out.println("current date===="+curdate);
        Connection connection = null;
        ResultSet rs=null,rs1=null;
        int trn_seq=0;
        String v_id=null, v_main_cashier=null;
        double pygmy_balance=0;
        try {
            common = commonLocalHome.create();
            connection = getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement stmt1=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs1=stmt1.executeQuery("select cash_close from DailyStatus where trn_dt='"+Validations.convertYMD(curdate)+"'");
            if(rs1.next())
                v_main_cashier=rs1.getString(1);
                
            if(v_main_cashier.equalsIgnoreCase("f"))
            {	
                rs=stmt.executeQuery("select ve_tml from PygmyTransaction where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and trn_type in('W','E') order by trn_seq desc limit 1");
                
                rs.last();
                int row=rs.getRow();
                rs.beforeFirst();
                if(row>0)
                {
                    if(rs.next())
                    	v_id=rs.getString("ve_tml");
                    if(v_id==null)
                    {
                        System.out.println("1....");
                        stmt.executeUpdate("delete from PygmyTransaction where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and trn_type in('S','W','E')");
                        //stmt.addBatch("update PygmyMaster set lst_trn_seq=lst_trn_seq-1 where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno );
                        //stmt.executeBatch();
                    }
                }
                rs = stmt.executeQuery("select trn_seq,cl_bal,ve_tml from PygmyTransaction where ac_type = '"+g_pgacctype+"' and ac_no="+g_pgaccno+" order by trn_seq desc limit 1 ");
                rs.next();
                trn_seq=rs.getInt("trn_seq")+1;
                pygmy_balance=rs.getDouble("cl_bal");
                /*else if(with_amt>0)
                 {
                 stmt.addBatch("delete from PygmyTransaction where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" order by trn_seq desc limit 1");
                 stmt.addBatch("update PygmyMaster set lst_trn_seq=lsat_trn_seq-1 where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno );
                 stmt.executeBatch();
                 }*/
                
            	if(penalty>0){
                System.out.println("Writing  penalty record...");
            	
            	PreparedStatement pstmt_penalty=connection.prepareStatement("insert into PygmyTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NULL,NULL,NULL,?)");
            	
            	pstmt_penalty.setString(1,g_pgacctype);
                pstmt_penalty.setInt(2,g_pgaccno);
                pstmt_penalty.setInt(3,trn_seq);
                //pstmt_penalty.setInt(3,null);
                //pstmt_penalty.setString(3,common.getSysDate());
                pstmt_penalty.setString(4,curdate);
                pstmt_penalty.setString(5,"S"); //For Penalty, It will be change as 'P' when Verification
                System.out.println("PreMatured Withdrawal : Towards Peanlty-->"+penalty);
                pstmt_penalty.setDouble(6,penalty);//trn_amt
                pstmt_penalty.setDouble(7,penalty);//amt_paid
                pstmt_penalty.setString(8,"T");//trn_mode
                pstmt_penalty.setString(9,g_tml); //trn_source
                pstmt_penalty.setString(10,"D");//cd_ind
                pygmy_balance-=penalty; //cl_bal
                pstmt_penalty.setDouble(11,pygmy_balance);
                pstmt_penalty.setInt(12,0); //ref_no
                pstmt_penalty.setString(13,"Penalty"); //trn_narr
                pstmt_penalty.setString(14,null);
                pstmt_penalty.setString(15,null);
                pstmt_penalty.setDouble(16,penalty);//prn_paid
                pstmt_penalty.setDouble(17,0.00);//int_paid
                pstmt_penalty.setString(18,g_tml);
                pstmt_penalty.setString(19,g_user);
                pstmt_penalty.setString(20,curdatetime);
                pstmt_penalty.setInt(21,1); //ref_ind=1 =>Data Entry Mode
                pstmt_penalty.executeUpdate();
            	}          
                
                System.out.println("Writing withdrawal amount into PygmyTransaction table.."); 
                PreparedStatement pstmt = connection.prepareStatement("insert into PygmyTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NULL,NULL,NULL,?)");
                pstmt.setString(1,g_pgacctype);
                pstmt.setInt(2,g_pgaccno);
                //pstmt.setInt(3,null); //trn_seq
                //pstmt.setString(3,common.getSysDate());
                if(penalty>0)
                	pstmt.setInt(3,++trn_seq);
                else
                	pstmt.setInt(3,trn_seq);
                pstmt.setString(4,curdate);
                pstmt.setString(5,"W");
                pstmt.setDouble(6,amount);//trn_amt
                pstmt.setDouble(7,amount);//amt_paid
                if(!pay_mode.equalsIgnoreCase("P"))
                	pstmt.setString(8,pay_mode);//trn_mode
                else
                	pstmt.setString(8,"T");
                pstmt.setString(9,g_tml);//trn_source
                pstmt.setString(10,"D");//cd_ind
                pstmt.setDouble(11,pygmy_balance-amount);//cl_bal
                pstmt.setInt(12,0);//ref_no
                if(pay_mode.equalsIgnoreCase("T"))
                    pstmt.setString(13,pay_ac_type+"-"+pay_ac_no); ///trn_narr
                else if(pay_mode.equalsIgnoreCase("P"))
                	pstmt.setString(13,"Pay Order"); //trn_narr
                else 
                	pstmt.setString(13,"Withdrawal Cash");//trn_narr
                pstmt.setString(14,null);
                pstmt.setString(15,null);
                pstmt.setDouble(16,amount);//prn_paid
                pstmt.setDouble(17,0);//int_paid
                pstmt.setString(18,g_tml);
                pstmt.setString(19,g_user);
                pstmt.setString(20,curdatetime);
                pstmt.setInt(21,1); //ref_ind=1
                
                pstmt.executeUpdate();
                //stmt.executeUpdate("update PygmyMaster set lst_trn_seq="+trn_seq+" where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);
                System.out.println("Returning from PartialWithdrawal...@@@@@@@@@");
                return 1;
            } 
                return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            sessionContext.setRollbackOnly();
        } catch (CreateException e) {
            e.printStackTrace();
        }
        finally{
            common = null;
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return -1;
    }
      
    
    public int pigmyCalendarQuerterInterestCalc(String uid,String tml,String curdate,String curdatetime,String br_location)
    {
        Statement stmt,stat,stmnt,smnt,stmt_temp;
        PreparedStatement pstmt1;
        ResultSet rs1,rs2,rs3;	
        int ret=0;
        int days[]={31,28,31,30,31,30,31,31,30,31,30,31};
        Connection connection = null;
        try
        {
            common = commonLocalHome.create();
            connection = getConnection();
            stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stat=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmnt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            smnt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt_temp=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           // pstmt1=connection.prepareStatement("delete  from PygmyQtrInterest where int_date=?");
            
            String string_opendate="";
            String date2="";
            String[] date3=getYearEnd(curdate,br_location);
            System.out.println("date3=================>>"+date3);
            System.out.println("date3[0]====="+date3[0]);
            System.out.println("date[1]====="+date3[1]);
            System.out.println("curdate===="+curdate);
            System.out.println("br_location===="+br_location);
            System.out.println("date compare--->"+date3[1].equals(curdate));
            if(Integer.parseInt(date3[0])==-1 && date3[1].equals(curdate))
            {
            	System.out.println("m inside -2====");
            	ret=-2;
            	
            	System.out.println("inside if======"+ret);
            }
            else
           {  
            System.out.println("YR E adfa ad (Year End)"+date3);
            rs2=stmnt.executeQuery("select ac_no,open_date,ac_type from PygmyMaster where status='O' and close_date is null");
           
            while(rs2.next())
            {
                int pgno=rs2.getInt(1);
                string_opendate=rs2.getString(2);
                String pgtype=rs2.getString(3);
                stmt_temp.executeUpdate("drop table if exists pygmy_acnos");
                stmt_temp.executeUpdate("create temporary table pygmy_acnos select * from PygmyTransaction where ac_no="+pgno+" and ac_type='"+pgtype+"'");
                //int months=Validations.noOfMonths(string_opendate,date3);
                int months=common.getNoOfMonths(string_opendate,date3[1]);
                System.out.println("months===========>>>"+months);
               // System.out.println("no of months bet opendate and curdate==="+months);
                StringTokenizer st=new StringTokenizer(string_opendate,"/");
                byte d=Byte.parseByte(st.nextToken());
                byte m=Byte.parseByte(st.nextToken());
                short y=Short.parseShort(st.nextToken());
                
                date2=Validations.checkDate(days[m-1]+"/"+m+"/"+y);
                
                double minbal[]=new double[months+1];
                System.out.println("min bal length==="+minbal.length);
                int i=0;
                double sum=0;
                double intamount=0;
                
                int noofdays=common.getDaysFromTwoDate(string_opendate,date2);
                System.out.println("noofdays====bean===>>>>"+noofdays);
                //rs1=stat.executeQuery("select min(int_rate) as int_rate from PygmyRate where prd_fr!=0");
                rs1=stat.executeQuery("select int_rate from PygmyRate where '"+Validations.convertYMD(curdate)+"' between  concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1)) and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1)) and "+months+" between prd_fr and prd_to and ac_type='"+pgtype+"'");
                rs1.next();
                if(date3[0].equals("1") && date3[1].equals(curdate))
               {
                while(Validations.dayCompare(date2,date3[1])>0)
               // while(date2<=equals(date3[1]))
                {
                    try
                    {
                    
                        //rs3=smnt.executeQuery("select min(cl_bal) from PygmyTransaction where ac_no="+rs2.getInt(1)+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_opendate)+"' and '"+Validations.convertYMD(date2)+"' group by concat(mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)))");
                    	rs3=smnt.executeQuery("select min(cl_bal) from pygmy_acnos where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(string_opendate)+"' and '"+Validations.convertYMD(date2)+"'");
                        if(rs3.next())
                        {
                        	if(rs3.getString(1)!=null)
                        		minbal[i]=rs3.getDouble(1);
                        	else if(rs3.getString(1)==null && i==0) 
                        		minbal[i]=0.00;
                        	else
                        		minbal[i]=minbal[i-1];
                        }
                    	
                       System.out.println("getting Minimum CLBal for the Period from "+string_opendate+" to "+string_opendate);
                      System.out.println("MinBalance: "+minbal[i]+   "acccc no==="+pgno);
                        
                    }
                    catch(Exception b){
                        b.printStackTrace();
                    }
                    
                    string_opendate=common.getFutureDayDate(date2,1); //Assume starting date
                   
                    m++;
                    
                    if(m==13)
                    {
                        m=(byte)(m%12);
                        y++;
                    }
                    
                    date2=Validations.checkDate(days[m-1]+"/"+m+"/"+y);
                    
                    System.out.println("After, Month: "+m);
                   System.out.println("After, Year:  "+y);
                  System.out.println("After, date2: "+date2);
                  System.out.println("i========>>>"+i);
                  System.out.println("d========>>>"+d);
                  
                  System.out.println("rs1.getDouble(1)====>>>"+rs1.getDouble(1));
                    if(i==0 && d>1)
                    {
                        intamount=intamount+minbal[0];
                        intamount=(intamount*rs1.getDouble(1)*noofdays)/36500;
                        
                    }
                    else
                    { 
                    	
                    	sum=sum+minbal[i];
                    	System.out.println("4.....SUM: "+sum);
                    }
                  
                    System.out.println("Date1: "+string_opendate+"   "+"Date2: "+date2+"  "+"MinBalance : "+minbal[i]+"i value: "+i);
                    i++;
                }
                intamount=Math.round(intamount+((sum*rs1.getDouble(1))/1200));
                System.out.println("After Calculating: "+intamount); 
                
                if(intamount>0)
                {	
                	System.out.println("DATE3 : "+date3);
                	   pstmt1=connection.prepareStatement("delete  from PygmyQtrInterest where ac_no=? and concat(right(int_date,4),'-',mid(int_date,locate('/',int_date)+1,(locate('/',int_date,4)-locate('/',int_date)-1)),'-',left(int_date,locate('/',int_date)-1))=?");
                	
                	System.out.println("ac no = "+pgno);
                	
                	pstmt1.setInt(1,pgno);
                	pstmt1.setString(2,Validations.convertYMD(date3[1]));
                	
                	int rows=0;
                	rows=pstmt1.executeUpdate();
                	System.out.println("No of rows Deleted: "+rows);
                	/*if(rows==0)
                	    System.out.println("Existing Record not deleted...");
                	else
                		System.out.println("Existing records  deleted...");*/
                    stmt.executeUpdate("insert into PygmyQtrInterest values('"+pgtype+"',"+pgno+",'"+date3[1]+"',"+intamount+",'"+tml+"','"+uid+"','"+curdatetime+"')");			
                }
              }
                else
               {
                	ret=-1;
                	
               }
               
            }
         }
        }catch(SQLException e){e.printStackTrace();
        sessionContext.setRollbackOnly();
        } catch (CreateException e) {
            e.printStackTrace();
        } catch (DateFormatException e) {
            e.printStackTrace();
        }
        finally{
            try {
                common = null;
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        
        System.out.println("ret value======"+ret);
        return ret;
    }
    
    /*public PygmyTransactionObject[] printAcknowledgementSlips(String agenttype,int agentno,String frm_date,String to_date)
    {
    	Statement stmt,stmt1;
    	ResultSet rs,rs1=null;
    	PygmyTransactionObject[] pt=null;
    	int norows=0;
    	Connection connection = null;
    	try
		{
    		connection = getConnection();
    		stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
    		stmt1=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
    		//String str =  "concat(right(pt.trn_date,4),'-',mid(pt.trn_date,locate('/',pt.trn_date)+1,(locate('/',pt.trn_date,4)-locate('/',pt.trn_date)-1)),'-',left(pt.trn_date,locate('/',pt.trn_date)-1))";
    		
    		//rs=stmt.executeQuery("select pt.coll_date,pt.trn_date,concat_ws(' ',cm1.fname,cm1.mname,cm1.lname)  agentname,pm.ac_no,concat_ws(' ',cm2.fname,cm2.mname,cm2.lname) recievedfrom,pt.cl_bal,pt.trn_amt from AgentMaster am,PygmyMaster pm,PygmyTransaction pt,CustomerMaster cm1,CustomerMaster cm2 where am.ac_type='"+agenttype+"' and am.ac_no="+agentno+" and pm.ac_no=pt.ac_no and  am.cid=cm1.cid  and pm.cid=cm2.cid and pt.trn_date = '"+frm_date+"' and pt.trn_type='R' and pt.ve_tml is not null");
    		//rs=stmt.executeQuery("select pt.coll_date,pt.trn_date,concat_ws(' ',cm1.fname,cm1.mname,cm1.lname)  agentname,pm.ac_no,concat_ws(' ',cm2.fname,cm2.mname,cm2.lname) recievedfrom,pt.cl_bal,pt.trn_amt from AgentMaster am,PygmyMaster pm,PygmyTransaction pt,CustomerMaster cm1,CustomerMaster cm2 where am.ac_type='1013001' and am.ac_no=10000068 and pt.trn_type='R' and pm.ac_no=pt.ac_no and pm.lst_trn_seq=pt.trn_seq and  am.cid=cm1.cid  and pm.cid=cm2.cid and concat(right(pt.trn_date,4),'-',mid(pt.trn_date,locate('/',pt.trn_date)+1,(locate('/',pt.trn_date,4)-locate('/',pt.trn_date)-1)),'-',left(pt.trn_date,locate('/',pt.trn_date)-1)) between  '"+frm_date+"' and '"+to_date+"' order by 4");
    		
    		System.out.println("frm Dt: "+frm_date);
    		System.out.println("Ag Ty: "+agenttype);
    		System.out.println("Ag No: "+agentno);
    		rs=stmt.executeQuery("select pt.coll_date,pt.trn_date,pt.trn_seq,pt.cl_bal,pt.trn_amt,concat_ws(' ',cm1.fname,cm1.mname,cm1.lname)  agentname,pm.ac_no,concat_ws(' ',cm2.fname,cm2.mname,cm2.lname) recievedfrom from AgentMaster am,PygmyMaster pm,PygmyTransaction pt,CustomerMaster cm1,CustomerMaster cm2 where am.ac_type='"+agenttype+"' and am.ac_no="+agentno+" and pm.ac_no=pt.ac_no and  am.cid=cm1.cid  and pm.cid=cm2.cid and pt.trn_date = '"+Validations.convertDMY(frm_date)+"' and pt.trn_type='R' and pt.ve_tml is not null order by pm.ac_no,pt.trn_seq");
    		System.out.println("after resultset");
    		if(rs.next())
    		{
    		rs.last();
    		norows=rs.getRow();
    		rs.beforeFirst();
    		
    		int rows=1;
    		
    		rs.next();
    		int pygaccno=rs.getInt("ac_no");
    		rs.beforeFirst();
    		while(rs.next())
    		{	                    
    			if(pygaccno!=rs.getInt("ac_no"))
    			{
    				rows++; 
    				System.out.println("ACCNO: "+pygaccno);
    				pygaccno=rs.getInt("ac_no");
    				System.out.println("AcNo: "+rs.getInt("ac_no")+"rows :"+rows);
    				
    			}	
    		}
    		
    		System.out.println("No of ROws: "+rows);
    		pt = new PygmyTransactionObject[rows];
    		rs.beforeFirst();
    		
    		int var1=0,var2=0,i=0;
    		
    		while(rs.next())
    		{
    			System.out.println("1... ac_no: "+rs.getInt("ac_no"));
    			var1=rs.getInt("ac_no");
    			//System.out.println("Compare: accno: "+var1+" rs.getInt("+rs.getInt("ac_no")+")");
    			
    			if(rs.isLast())
    			{
    				System.out.println("2....");
    				pt[i]=new PygmyTransactionObject();
    				pt[i].setAccNo(var1);
    				//System.out.println("TrnSeq: "+rs.getInt("trn_seq"));
    			}
    			
    			if(rs.next())
    			{
    			
    				System.out.println("3...");
    				var2=rs.getInt("ac_no"); //current 
    				if(var1!=var2)
    				{          		
    					System.out.println("4....");
    					//System.out.println("Trn Seq: "+rs.getInt("trn_seq")+"ac_no: "+var1);
    					pt[i] = new PygmyTransactionObject();
    					pt[i].setAccNo(var1);
    					System.out.println("ACC NO@@ : "+pt[i].getAccNo());                                              
    					i++;
    				}
    				rs.previous();	
    			}
    			
    		}
    			for(i=0;i<pt.length;i++)
    				System.out.println("Ac No " +i+" = "+pt[i].getAccNo());
    			
    			System.out.println("pt length:  "+pt.length); 
    			for(int j=0; j<pt.length; j++)
    			{
    				
    				System.out.println("@@  j Value :"+j+": "+pt[j].getAccNo());
    				rs1=stmt1.executeQuery("select distinct pt.coll_date,pt.trn_date,pt.trn_seq,pt.cl_bal,pt.trn_amt,concat_ws(' ',cm.fname,cm.mname,cm.lname)  as recievedfrom,concat_ws(' ',cm1.fname,cm1.mname,cm1.lname)  as agentname from PygmyTransaction pt,AgentMaster am,PygmyMaster pm,CustomerMaster cm,CustomerMaster cm1 where pm.ac_no="+pt[j].getAccNo()+" and am.ac_no=pm.agt_no and cm1.cid=am.cid and cm.cid=pm.cid and pt.ac_no=pm.ac_no and pt.trn_date='"+Validations.convertDMY(frm_date)+"' and pt.trn_type='R' and pt.ve_tml is not null order by  trn_seq asc"); 	
    				if(rs1.next())
    				{
    				    
    				//pt[j] = new PygmyTransactionObject();
    				      pt[j].setCollectionDate(rs1.getString("pt.coll_date"));
    				      pt[j].setTranDate(rs1.getString("pt.trn_date"));
    				      pt[j].setTranSource(rs1.getString("agentname"));//name
    				      pt[j].setAccNo(pt[j].getAccNo());
    				
    				      pt[j].setCDInd(rs1.getString("recievedfrom")); //Recieved from..
    				      System.out.println("rs_closebal==="+rs1.getDouble("cl_bal"));
    				      System.out.println("rs_tran amount==="+rs1.getDouble("trn_amt"));
    				
    				      pt[j].setCloseBal(rs1.getDouble("cl_bal")-rs1.getDouble("trn_amt"));
    				//pt[j].setTranAmt(rs1.getDouble("pt.trn_amt"));
    				}
    				
    				
    			}//end of for..
    			
    			rs1.close();
    			double tot_remit=0;
    			for(int r=0; r<pt.length; r++)
    			{
    				rs1=stmt1.executeQuery("select pt.trn_amt,pt.coll_date to_colldt,pt.trn_date to_remdt from PygmyTransaction pt,AgentMaster am,PygmyMaster pm,CustomerMaster cm,CustomerMaster cm1 where pm.ac_no="+pt[r].getAccNo()+" and am.ac_no=pm.agt_no and cm1.cid=am.cid and cm.cid=pm.cid and pt.ac_no=pm.ac_no and concat(right(pt.trn_date,4),'-',mid(pt.trn_date,locate('/',pt.trn_date)+1,(locate('/',pt.trn_date,4)-locate('/',pt.trn_date)-1)),'-',left(pt.trn_date,locate('/',pt.trn_date)-1)) between  '"+frm_date+"' and '"+to_date+"' and pt.trn_type='R' and pt.ve_tml is not null order by  trn_seq desc");
    				if(rs1.next())
    				{
    				  rs1.beforeFirst();  
    				 while(rs1.next())
    				{
    				tot_remit=+rs1.getDouble(1);
    				System.out.println("total remit acno==="+pt[r].getAccNo());
    				System.out.println("total remit amount=="+tot_remit);
    				}
    				pt[r].setTotal_remittance(tot_remit);
    				System.out.println("TOTAL REMITTANCE====="+pt[r].getTotal_remittance());
    				
    				rs1.beforeFirst();
    				rs1.next();
    				pt[r].setTo_colldt(rs1.getString("to_colldt"));
    				pt[r].setTo_trndt(rs1.getString("to_remdt"));
    				
    				}
    			}
    			
    			 
    		    rs.close();
    		}
    		       
        }catch (Exception e){e.printStackTrace();}
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }                  
        //System.out.println("ack slip ln=="+pt.length);
        return pt;
    }*/
    /**
     * newly modified acknowledgement printing
     * shubha
     */
    public PygmyTransactionObject[] printAcknowledgementSlips(String agenttype,int agentno,String frm_date,String to_date)
    {
        Statement stmt=null,stmt_update=null,stmt_final=null;
        ResultSet rs_final=null;
        PygmyTransactionObject[] ptran=null;
        int num_rows=0,i=0;
        Connection connection = null;
        try
        {
            System.out.println("print from date==="+frm_date);
            System.out.println("print to date==="+to_date);
            connection = getConnection();
            stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt_update=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt_final=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            {
            	System.out.println("m inside stmt_final.......... ");
                 stmt.addBatch("drop table if exists acknowledgement");
                 stmt.addBatch("drop table if exists accountnum");
                 stmt.addBatch("drop table if exists closebal");
                 stmt.addBatch("drop table if exists total_remit");
                 stmt.executeBatch();
                 System.out.println("after executeBatch()");
                 stmt_update.executeUpdate("create temporary table acknowledgement (pigmy_accno int,collecton varchar(20),remiton varchar(15),to_collecton varchar(20),to_remiton varchar(15),agentname varchar(30),recvdfrom varchar(30),prevbal double default 0.00,remitamt double default 0.00,totalbal double default 0.00)");
                 stmt_update.executeUpdate("create temporary table accountnum select distinct pm.ac_no from PygmyMaster pm,AgentMaster am,PygmyTransaction pt,CustomerMaster cm1,CustomerMaster cm2 where am.ac_type='"+agenttype+"' and am.ac_no="+agentno+" and pm.ac_no=pt.ac_no and  am.cid=cm1.cid  and pm.cid=cm2.cid and concat(right(pt.trn_date,4),'-',mid(pt.trn_date,locate('/',pt.trn_date)+1,(locate('/',pt.trn_date,4)-locate('/',pt.trn_date)-1)),'-',left(pt.trn_date,locate('/',pt.trn_date)-1)) = '"+frm_date+"' and pt.trn_type='R' and pt.ve_tml is not null order by pm.ac_no,pt.trn_seq");
                 stmt_update.executeUpdate("insert into acknowledgement(pigmy_accno) (select * from accountnum)");
                 stmt_update.executeUpdate("create temporary table closebal select pt.coll_date,pt.trn_date,max(pt.trn_seq),pt.cl_bal,pt.trn_amt,(pt.cl_bal-pt.trn_amt) as prevbal,pm.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname)  as recievedfrom,concat_ws(' ',cm1.fname,cm1.mname,cm1.lname)  as agentname from PygmyTransaction pt,AgentMaster am,PygmyMaster pm,CustomerMaster cm,CustomerMaster cm1,accountnum where pm.ac_no=accountnum.ac_no and am.ac_no=pm.agt_no and cm1.cid=am.cid and cm.cid=pm.cid and pt.ac_no=pm.ac_no and concat(right(pt.trn_date,4),'-',mid(pt.trn_date,locate('/',pt.trn_date)+1,(locate('/',pt.trn_date,4)-locate('/',pt.trn_date)-1)),'-',left(pt.trn_date,locate('/',pt.trn_date)-1))='"+frm_date+"' and pt.trn_type='R' and pt.ve_tml is not null group by pm.ac_no order by pm.ac_no,trn_seq asc");
                 stmt_update.executeUpdate("create temporary table total_remit select pm.ac_no,sum(pt.trn_amt) as totalremit,max(pt.coll_date) to_colldt,max(concat(right(pt.trn_date,4),'-',mid(pt.trn_date,locate('/',pt.trn_date)+1,(locate('/',pt.trn_date,4)-locate('/',pt.trn_date)-1)),'-',left(pt.trn_date,locate('/',pt.trn_date)-1))) to_remdt from PygmyTransaction pt,AgentMaster am,PygmyMaster pm,CustomerMaster cm,CustomerMaster cm1,accountnum where pm.ac_no=accountnum.ac_no and am.ac_no=pm.agt_no and cm1.cid=am.cid and cm.cid=pm.cid and pt.ac_no=pm.ac_no and concat(right(pt.trn_date,4),'-',mid(pt.trn_date,locate('/',pt.trn_date)+1,(locate('/',pt.trn_date,4)-locate('/',pt.trn_date)-1)),'-',left(pt.trn_date,locate('/',pt.trn_date)-1)) between  '"+frm_date+"' and '"+to_date+"' and pt.trn_type='R' and pt.ve_tml is not null group by pm.ac_no order by pm.ac_no,trn_seq desc");
                 stmt_update.executeUpdate("update acknowledgement ack set collecton=(select coll_date from closebal where ack.pigmy_accno=closebal.ac_no)");
                 stmt_update.executeUpdate("update acknowledgement ack set remiton=(select trn_date from closebal where ack.pigmy_accno=closebal.ac_no)");
                 stmt_update.executeUpdate("update acknowledgement ack set agentname=(select agentname from closebal where ack.pigmy_accno=closebal.ac_no)");
                 stmt_update.executeUpdate("update acknowledgement ack set recvdfrom=(select recievedfrom from closebal where ack.pigmy_accno=closebal.ac_no)");
                 stmt_update.executeUpdate("update acknowledgement ack set prevbal=(select prevbal from closebal where ack.pigmy_accno=closebal.ac_no)");
                 stmt_update.executeUpdate("update acknowledgement ack set to_collecton=(select to_colldt from total_remit where ack.pigmy_accno=total_remit.ac_no)");
                 stmt_update.executeUpdate("update acknowledgement ack set to_remiton=(select to_remdt from total_remit where ack.pigmy_accno=total_remit.ac_no)");
                 stmt_update.executeUpdate("update acknowledgement ack set remitamt=(select totalremit from total_remit where ack.pigmy_accno=total_remit.ac_no)");
                 stmt_update.executeUpdate("update acknowledgement set totalbal=(prevbal+remitamt)");
                 System.out.println("m after stmt_update ");
                rs_final=stmt_final.executeQuery("select * from acknowledgement limit 10");
                if(rs_final.next())
                {
                	System.out.println("m inside ifssssssss");
                    rs_final.last();
                    num_rows=rs_final.getRow();
                    ptran=new PygmyTransactionObject[num_rows];
                    rs_final.beforeFirst();
                }
                while(rs_final.next())
                {
                    ptran[i]=new PygmyTransactionObject();
                    ptran[i].setAccNo(rs_final.getInt("pigmy_accno"));
                    ptran[i].setCollectionDate(rs_final.getString("collecton"));
                    ptran[i].setTranDate(rs_final.getString("remiton"));
                    ptran[i].setTo_colldt(rs_final.getString("to_collecton"));
                    ptran[i].setTo_trndt(rs_final.getString("to_remiton"));
                    ptran[i].setName(rs_final.getString("agentname"));
                    ptran[i].setRecvFrom(rs_final.getString("recvdfrom"));
                    ptran[i].setPrevBalance(rs_final.getDouble("prevbal"));
                    ptran[i].setTotal_remittance(rs_final.getDouble("remitamt"));
                    ptran[i].setCloseBal(rs_final.getDouble("totalbal"));
                    i++;
                }
               
            }
            
                       
       }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally{
            try{
                connection.close();
            }
            catch(SQLException se){
                se.printStackTrace();
            }
        }
        //System.out.println("acknowledge length=="+ptran.length);
        return ptran;
    }
    /**
     * Ths is used to delete the Transaction of Pygmy Withdrawal. 
     * @param actype
     * @param accno
     * @param id => 0 is for Withgdrawal 1 is for Closure
     * @return '1' if it is successfully deleted.Otherwise '-1'.
     * @throws SQLException
     * 
     */
    public int pygmyWithdrwalDeletion(String actype,int accno,int id) throws SQLException
    {
        
        Connection conn=null;
        System.out.println("Acc_type: "+actype);
        System.out.println("Acc NO: "+accno);
        conn=getConnection();
        Statement stmt=conn.createStatement();
        Statement stmt1=conn.createStatement();
        try{
            
            
            if(id==1)
            {   System.out.println("Inside PygmyWithDrwal Deletion...");
            stmt.addBatch("delete from PygmyTransaction where ac_type='"+actype+"' and ac_no="+accno+" and ve_tml is null and trn_type in('S','W')");
            //stmt.addBatch("update PygmyMaster set lst_trn_seq=lst_trn_seq-1 where ac_type='"+actype+"' and ac_no="+accno);
            
            if(stmt.executeBatch().length >0)
                return 2;
            }
            else if(id==0)
            {
                System.out.println("Inside PygmyClosure(Account Closure) Deletion...");
                stmt1.executeUpdate("delete from PygmyTransaction where ac_type='"+actype+"' and ac_no="+accno+" and trn_type='E' and ve_tml is null order by trn_seq desc");
                /* int[] arr=stmt1.executeBatch();   
                 System.out.println("Array Length: "+arr.length);
                 if(arr.length>1)
                 System.out.println("@@@@");*/
                stmt1.executeUpdate("update PygmyMaster set status='O' where ac_type='"+actype+"' and ac_no="+accno+"");    
                return 1;
            }
        }
        
        catch(SQLException se){se.printStackTrace();
        sessionContext.setRollbackOnly();}
        finally{
            try{
                conn.close();
                
            }
            catch(SQLException se){se.printStackTrace();}
        }
        System.out.println("3....");
        return -1;
    }
    
    /**
     * TO delete the Existing PygmyAccount.
     * @param actype - modulecode.
     * @param acno - Pygmy Account Number.
     * @return - deleted PygmyAccount Number.
     */
    public int pygmyAccountDeletion(String actype,int acno)
    {
        System.out.println("Inside Delete Action");
    System.out.println("AcType: "+actype);
    System.out.println("AccNo: "+acno);
    //int pigmyno=0;
    Connection connection =null;
    try
    {
        connection = getConnection();
        Statement stmt = connection.createStatement();
        
        stmt.addBatch("delete from JointHolders where ac_type='"+actype+"' and ac_no="+acno);
        stmt.addBatch("delete from NomineeMaster where ac_type='"+actype+"' and ac_no="+acno);
        stmt.addBatch("delete from SignatureInstruction where ac_type='"+actype+"' and ac_no="+acno);
        stmt.addBatch("delete from PygmyMaster where ac_type='"+actype+"' and ac_no="+acno);
        //stmt.addBatch("update Modules set lst_acc_no=lst_acc_no-1 where modulecode='"+actype+"'");
        stmt.executeBatch();
        
    }catch(SQLException exception){
        exception.printStackTrace();
        sessionContext.setRollbackOnly();
    }
    finally{try {
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
    return acno;
    }
    
    /**
     * To Open a new PygmyAccount & update the Transaction before Verification.
     * Get the data from 'pygmymasterobject' and insert into Master table(PygmyMaster).
     * get the SignatureInstruction, JointHolders & Nominee Details & insert records into the corresponding tables.
     * Refer the last PygmyAccount Number 'lst_acc_no' from Modules table to generate new PygmyAccount Number.
     * In case of Updation,
     * updation is possible before Verification.
     * If the Pygmy Account Number is greater than 0, delete the old records of PygmyMaster,Signature,JointHolder& Nominee details.And insert the new records(updated information). 
     * @param pygmymasterobject - Data eneterd in the application to open the Account.
     * @param detml - Terminal Name from the MainScreen.
     * @param deuser - Terminal User Name
     * @param int_verify - '0' is for Account Opening & Updation. '1' is for Verification.
     * @return the new PygmyAccount Number.
     * @throws NomineeNotCreatedException 
     * @throws SignatureException 
     */
    public int pygmyAccountOpen(PygmyMasterObject pygmymasterobject,String detml,String deuser,String curdatetime,int int_verify) throws SignatureException
    {
        int pigmyno=0;
        Statement stmt;
        PreparedStatement ps;
        ResultSet rs;
        Connection connection = null;
        try
        {
            System.out.println("Pygmy Account Opening....");
            common = commonLocalHome.create();
            connection = getConnection();
            if(connection != null )
                stmt=connection.createStatement();
            else throw new SQLException();
            
            if(int_verify==0)
            {
                System.out.println("1....");
                ps=connection.prepareStatement("insert into PygmyMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NULL,NULL,NULL,?)");
                if(pygmymasterobject.getAccNo()==0) //Submit Case
                {
                	System.out.println("11.............................submit");
                    rs=stmt.executeQuery("select lst_acc_no from Modules where modulecode="+pygmymasterobject.getAccType());
                    rs.next();
                    pigmyno=rs.getInt(1);
                    pigmyno++;
                    ps.setInt(2,pigmyno);
                    ps.setInt(27,2); //ref_ind=2 when Updation case..
                }
                else
                { //Updation Case
                    System.out.println("2.....");
                    pigmyno=pygmymasterobject.getAccNo();
                    //rs=stmt.executeQuery("select nom_reg_no from PygmyMaster where ac_type='"+am.getAccType()+"' and ac_no="+am.getAccNo());
                    //rs.next();
                    
                    stmt.addBatch("delete from SignatureInstruction where ac_type='"+pygmymasterobject.getAccType()+"'  and ac_no="+pygmymasterobject.getAccNo());
                    stmt.addBatch("delete from JointHolders where ac_type='"+pygmymasterobject.getAccType()+"'  and ac_no="+pygmymasterobject.getAccNo());
                    stmt.addBatch("delete from NomineeMaster where ac_no="+pygmymasterobject.getAccNo()+" and ac_type='"+pygmymasterobject.getAccType()+"'");
                    stmt.addBatch("delete from PygmyMaster where ac_type='"+pygmymasterobject.getAccType()+"' and ac_no="+pygmymasterobject.getAccNo());
                    
                    int a[]=stmt.executeBatch();
                    System.out.println("Updation- ExecuteBatch Length  : "+a.length);
                    ps.setInt(2,pygmymasterobject.getAccNo());
                    System.out.println("3....");
                }
                ps.setString(1,pygmymasterobject.getAccType());
                ps.setInt(3,pygmymasterobject.getCid());			
                
                ps.setInt(4,pygmymasterobject.getAddrType());
                ps.setInt(5,pygmymasterobject.getJointHolders());
                ps.setString(6,pygmymasterobject.getAgentType());
                ps.setString(7,pygmymasterobject.getAgentNo());
                
                System.out.println("4...");
                if(pygmymasterobject.getNomineeDetails()!=null && pygmymasterobject.getNomineeDetails().length>0)
               {    
                System.out.println("nominee details lngth===="+pygmymasterobject.getNomineeDetails().length);
                NomineeObject nom[]=pygmymasterobject.getNomineeDetails(); //refer abt 'pygmymasterobject.setNomineeDetails()' when Action Performed of 'Submit' Button.
                //System.out.println("Nominee Obj Length: "+nom.length);
                System.out.println("nom getreg no=+=+=+="+nom[0].getRegNo());  
                if(nom!=null && nom.length>0)
                {	System.out.println("Before Calling storeNominee...@@@@");
                int id=common.storeNominee(nom,pygmymasterobject.getAccType(),pigmyno,getSysDate());
                System.out.println("return val frm store nom====="+id);
                System.out.println("NomineeDetails stored...@@@@");
                if(id==0)
                    throw new SignatureException();
                ps.setInt(8,id);
                }
                else			
                    ps.setInt(8,0);//nominee no
               }
              else
                    ps.setInt(8,0);
                ps.setString(9,pygmymasterobject.getAccStatus());
                ps.setInt(10,0);//lst_trn_seq
                ps.setString(11,"");//lst_int_date
                ps.setString(12,null);
                ps.setInt(13,0);
                ps.setString(14,"F");
                ps.setString(15,null);
                ps.setInt(16,0);
                ps.setDouble(17,0);
                ps.setString(18,pygmymasterobject.getPayMode());
                ps.setString(19,pygmymasterobject.getPayAccType());
                ps.setInt(20,pygmymasterobject.getPayAccNo());
                //ps.setString(21,common.getSysDate());  //Acc Opening date
                ps.setString(21,pygmymasterobject.getAccOpenDate());
                ps.setString(22,null);
                ps.setString(23,null);//ledger print date
                ps.setString(24,detml);
                ps.setString(25,deuser);
                ps.setString(26,curdatetime);
                ps.setInt(27,1); // ref_ind=1 when DE
                
                if(ps.executeUpdate()==1){
                	System.out.println("Inserted into 'PygmyMaster' Table");
                    if(pygmymasterobject.getSigObj()!=null){
                        System.out.println("before calling signatuerere");
                        common.storeSignatureDetails(pygmymasterobject.getSigObj(),pigmyno);
                        System.out.println("after calling signatuerere");
                    }
                    if(pygmymasterobject.getJointCid()!=null)
                    {
                        int addrtype[]=pygmymasterobject.getJointAddrType();
                        int jcids[]=pygmymasterobject.getJointCid();
                        for(int i=0;i<jcids.length;i++)
                        {	
                            stmt.executeUpdate("insert into JointHolders values('"+pygmymasterobject.getAccType()+"','"+pigmyno+"','"+jcids[i]+"','"+addrtype[i]+"')");
                        }
                    }						
                }
                else
                    throw new SQLException("Failed to write into PygmyMaster");	
                
                if(pygmymasterobject.getAccNo()==0){
                    stmt.executeUpdate("Update Modules set lst_acc_no=1+lst_acc_no where modulecode="+pygmymasterobject.getAccType());
                }
                else 
                    pigmyno=pygmymasterobject.getAccNo();
                System.out.println("pigmyno==++++++++++++++++++++++++"+pigmyno);
                
                }
            if(int_verify==1)
            {//Verification Case.  ref_ind=3 when Verification Case 
            	System.out.println(curdatetime+"============>>>>>>>>>"+detml+"-----"+deuser+"---------"+pygmymasterobject.getAccStatus()+"---------"+pygmymasterobject.getAccNo()+"----------"+pygmymasterobject.getAccType());
               pigmyno=stmt.executeUpdate("update PygmyMaster set ve_tml='"+detml+"',ve_user='"+deuser+"',ve_date='"+curdatetime+"',status='"+pygmymasterobject.getAccStatus()+"',ref_ind=3 where ac_type='"+pygmymasterobject.getAccType()+"' and ac_no="+pygmymasterobject.getAccNo());
            }
                System.out.println("pygmy number====000000000000000000000000000111111"+pigmyno); 
        }catch(SQLException n){n.printStackTrace();
        sessionContext.setRollbackOnly();
        } /*catch (SignatureException se) {
            se.printStackTrace();
            throw se;
            //throw e;
        }*/ catch (CreateException e) {
            e.printStackTrace();
            throw new NomineeNotCreatedException();
        }
        finally{
            try {
                common = null;
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("pygmy number====000000000000000000000000000"+pigmyno);
        return pigmyno;
    }
    
    /**
     * H.R.B
     * 
     * used to Update the 'ref_ind' field in PygmyMaster Table.
     * @param //fdate
     * @param //tdate
     * @param //query
     * @return
     */
    public  int getUpdatePDRefInd(String ac_ty,int ac_no,int id){
    	Connection conn=null;
    	PreparedStatement pst=null;
    	
    	try{
    		conn=getConnection();
    		pst=conn.prepareStatement("update PygmyMaster set ref_ind=? where ac_type=? and ac_no=? and ref_ind=? and ve_tml is null;");
    		pst.setInt(1,2); //ref_ind=2 "Updation Mode".
    		pst.setString(2,ac_ty);
    		pst.setInt(3,ac_no);
    		pst.setInt(4,id);
    		
    		pst.executeUpdate();
    		
    		return 1;
    	}catch(SQLException se){se.printStackTrace();}
    	finally{
    		try{
    			conn.close();
    		}catch(SQLException e){e.printStackTrace();}
    	}
    	return 0;
    }
    
    
    
    public QuarterInterestObject[] retrieveInterestRegister(String fdate,String tdate,String query)
    {
        int norows=0;
        QuarterInterestObject[] pi = null;
        Connection connection = null;
        fdate=Validations.convertYMD(fdate);
        tdate=Validations.convertYMD(tdate);
        System.out.println("frm dt:  "+fdate);
        System.out.println("to dt: "+tdate);
        try
        {
            connection = getConnection();
            ResultSet rs = null;
            Statement stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if(query!=null)
                rs = stmt.executeQuery("select mo.moduleabbr,pi.* from Modules mo,PygmyQtrInterest pi where mo.modulecode=pi.ac_type and ("+query+")");
            else{
                String str =  "concat(right(int_date,4),'-',mid(int_date,locate('/',int_date)+1,(locate('/',int_date,4)-locate('/',int_date)-1)),'-',left(int_date,locate('/',int_date)-1))";
                rs=stmt.executeQuery("select mo.moduleabbr,pi.* from PygmyQtrInterest pi,Modules mo where mo.modulecode=pi.ac_type and "+str+" between '"+fdate+"' and '"+tdate+"'");
            }
            
            if(rs.next())
            {
                rs.last();
                norows=rs.getRow();
                pi=new QuarterInterestObject[norows];
                rs.beforeFirst();
            }
            
            for(int i=0;i<norows;i++)
            {
                if(!rs.next())
                    break;
                pi[i] = new QuarterInterestObject();
                
                
                pi[i].setPygmyType(rs.getString("mo.moduleabbr"));
                pi[i].setPygmyNo(rs.getInt("ac_no"));
                pi[i].setIntDate(rs.getString("int_date"));
                pi[i].setIntAmount(rs.getDouble("int_amt"));
                pi[i].setTml(rs.getString("de_tml"));
                pi[i].setUid(rs.getString("de_user"));
                pi[i].setTime(rs.getString("de_date"));
            }
            
            rs.close();
        } catch (Exception e){e.printStackTrace();}
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        
        return pi;
    }    
    
    public PygmyTransactionObject[] retrieveMonthlyRemit(String agent_type,int agent_no,String fromdate,String todate)
    {
        Statement stmt=null;
        int row=0;
        ResultSet rs=null;
        PygmyTransactionObject[] pt=null;
        Connection connection = null;
        System.out.println("Inside RetrieveMonthlyRemittance..");
        System.out.println("Agent Type: "+agent_type);
        System.out.println("Agent No: "+agent_no);
        System.out.println("Frm Dt: "+fromdate);
        System.out.println("TO Dt: "+todate);
        
        try
        {
            connection = getConnection();
            stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            if(agent_no==0)
                rs=stmt.executeQuery("select mo.moduleabbr,pt.ac_no,pt.trn_date,pt.coll_date,pt.trn_type,pt.trn_amt,pt.cl_bal,pt.trn_narr,concat(IFNULL(fname,''),' ',IFNULL(mname,''),' ',IFNULL(lname,''))as name,pm.agt_no from Modules mo,PygmyTransaction pt,CustomerMaster cm,PygmyMaster pm where pt.ac_type=pm.ac_type and pm.ac_no=pt.ac_no and pm.status='O' and pt.trn_type='R' and pm.cid=cm.cid and mo.modulecode=pt.ac_type and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"' and '"+todate+"' and pt.ve_user is not null order by pm.agt_no,pt.ac_no,pt.trn_seq");
            else
                rs=stmt.executeQuery("select mo.moduleabbr,pt.ac_no,pt.trn_date,pt.coll_date,pt.trn_type,pt.trn_amt,pt.cl_bal,pt.trn_narr,concat_ws(' ',fname,mname,lname)as name,pm.agt_no from PygmyTransaction pt,Modules mo,CustomerMaster cm,PygmyMaster pm where pt.ac_type=pm.ac_type and pm.ac_no=pt.ac_no and pm.status='O' and pt.trn_type='R' and pm.cid=cm.cid and pm.agt_type='"+agent_type+"' and pm.agt_no="+agent_no+" and mo.modulecode=pt.ac_type and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+fromdate+"' and '"+todate+"' and pt.ve_user is not null order by pt.ac_no,pt.trn_seq");
            if(!rs.next())
                System.out.println("rs null");
            int i=0;
            rs.beforeFirst();
            if(rs.next())
            {
            rs.last();
            row=rs.getRow();
            System.out.println("num of rows="+row);
            pt=new PygmyTransactionObject[rs.getRow()];
            rs.beforeFirst();
            while(rs.next())
            {
                pt[i]=new PygmyTransactionObject();
                pt[i].setAccType(rs.getString("mo.moduleabbr"));
                pt[i].setAccNo(rs.getInt("pt.ac_no"));
                pt[i].setTranDate(rs.getString("pt.trn_date"));
                pt[i].setTranSource(rs.getString("name"));
                pt[i].setTranType(rs.getString("pt.trn_type"));
                pt[i].setCollectionDate(rs.getString("pt.coll_date"));
                pt[i].setTranAmt(rs.getDouble("pt.trn_amt"));
                pt[i].setCloseBal(rs.getDouble("pt.cl_bal"));
                pt[i].setTranNarration(rs.getString("pt.trn_narr"));
                pt[i].setTranSeq(rs.getInt("pm.agt_no"));
                i++;
            }
            }
        }catch(SQLException e){e.printStackTrace();}
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
//        System.out.println("mnthly remittance lnght="+pt.length);
        return pt;
    }
    
    public PygmyTransactionObject retrievePygmyTransaction(String pgtype,int int_pygmyno)
    {
        Statement stmt;
        ResultSet rs;
        PygmyTransactionObject pt1=null;
        Connection connection = null;
        try{
            connection = getConnection();
            stmt=connection.createStatement();
            rs=stmt.executeQuery("select pt.trn_seq,pt.cl_bal,pt.trn_amt,pm.open_date,pt.trn_date,m.min_bal,pt.int_paid,pt.trn_type from PygmyTransaction pt, PygmyMaster pm,Modules m where pt.ac_type='"+pgtype+"' and pt.ac_no="+int_pygmyno+" and pt.trn_type='P' and pm.ac_type=pt.ac_type and pm.ac_no=pt.ac_no and m.modulecode=pt.ac_type and pt.ve_tml is null order by pt.trn_seq desc limit 1 ");
            while(rs.next()){
                pt1= new PygmyTransactionObject();
                pt1.setTranSeq(rs.getInt(1));
                pt1.setCloseBal(Math.round(rs.getDouble(2)));
                pt1.setTranAmt(Math.round(rs.getDouble(3)));
                pt1.setCDInd(rs.getString(4));
                pt1.setTranDate(rs.getString(5));
                
                double maxbal = pt1.getCloseBal();
                if(maxbal >=0)
                    pt1.setPrnPaid(Math.round(maxbal));
                else
                    pt1.setPrnPaid(0.0);
                
                pt1.setAmtPaid(Math.round(rs.getDouble(2)));
                pt1.setInterestPaid(rs.getInt(7));
                pt1.setTranType(rs.getString(8));
            }
        }catch(Exception e){e.printStackTrace();}
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        
        return pt1;
        
    }
    
    public QuarterInterestObject[] retrieveRegister(String from_date,String to_date,int type)
    {
        int int_norows=0;
        QuarterInterestObject[] pi = null;
        Connection connection = null;
        
        System.out.println("from date in bean is "+from_date);
        System.out.println("to date in bean is "+to_date);
        try
        {
            connection = getConnection();
            ResultSet rs = null;
            Statement stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            System.out.println("THE TYPE IN BEAN IS"+type);
            if(type == 0) // opened accounts
                //rs=stmt.executeQuery("select mo.moduleabbr,pm.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname),pm.open_date,pm.close_date,if((pm.ln_avld='T'),concat((select moduleabbr from Modules where modulecode=pm.ln_ac_type),' ',pm.ln_ac_no),'')as loan from Modules mo,PygmyMaster pm,CustomerMaster cm where concat(right(pm.open_date,4),'-',mid(pm.open_date,locate('/',pm.open_date)+1,(locate('/',pm.open_date,4)-locate('/',pm.open_date)-1)),'-',left(pm.open_date,locate('/',pm.open_date)-1)) between '"+from_date+"' and '"+to_date+"' and mo.modulecode=pm.ac_type and pm.cid=cm.cid");
                rs=stmt.executeQuery("select mo.moduleabbr,pm.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname),pm.open_date,pm.close_date,if((pm.ln_avld='T'),concat((select moduleabbr from Modules where modulecode=pm.ln_ac_type),' ',pm.ln_ac_no),'')as loan,nm.name,nm.relation,pm.lst_int_dt  from Modules mo,PygmyMaster pm,CustomerMaster cm,NomineeMaster nm where concat(right(pm.open_date,4),'-',mid(pm.open_date,locate('/',pm.open_date)+1,(locate('/',pm.open_date,4)-locate('/',pm.open_date)-1)),'-',left(pm.open_date,locate('/',pm.open_date)-1)) between '"+from_date+"' and '"+to_date+"' and mo.modulecode=pm.ac_type and pm.cid=cm.cid and nm.ac_type=pm.ac_type and nm.ac_no=pm.ac_no and nm.reg_no=pm.nom_no and pm.ve_tml is not null  order by pm.ac_no");
            else if(type == 1) // closed accounts
                //rs=stmt.executeQuery("select mo.moduleabbr,pm.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname),pm.open_date,pm.close_date,if((pm.ln_avld='T'),concat((select moduleabbr from Modules where modulecode=pm.ln_ac_type),' ',pm.ln_ac_no),'')as loan from Modules mo,PygmyMaster pm,CustomerMaster cm where concat(right(pm.close_date,4),'-',mid(pm.close_date,locate('/',pm.close_date)+1,(locate('/',pm.close_date,4)-locate('/',pm.close_date)-1)),'-',left(pm.close_date,locate('/',pm.close_date)-1)) between '"+from_date+"' and '"+to_date+"' and mo.modulecode=pm.ac_type and pm.cid=cm.cid");
                rs=stmt.executeQuery("select mo.moduleabbr,pm.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname),pm.open_date,pm.close_date,if((pm.ln_avld='T'),concat((select moduleabbr from Modules where modulecode=pm.ln_ac_type),' ',pm.ln_ac_no),'')as loan,nm.name,nm.relation,pm.lst_int_dt from Modules mo,PygmyMaster pm,CustomerMaster cm,NomineeMaster nm where concat(right(pm.close_date,4),'-',mid(pm.close_date,locate('/',pm.close_date)+1,(locate('/',pm.close_date,4)-locate('/',pm.close_date)-1)),'-',left(pm.close_date,locate('/',pm.close_date)-1)) between '"+from_date+"' and '"+to_date+"' and mo.modulecode=pm.ac_type and pm.cid=cm.cid and nm.ac_type=pm.ac_type and nm.ac_no=pm.ac_no and nm.reg_no=pm.nom_no and pm.ve_tml is not null order by pm.ac_no");  
            	
           if(rs.next())
           {
        	   System.out.println("sang is here");
            rs.last();
            System.out.println("num rows==="+rs.getRow());
            int_norows=rs.getRow();
            pi=new QuarterInterestObject[int_norows];
            System.out.println("----&&&&&&&&&&&&&&&&&&&--"+pi);
            rs.beforeFirst();
           }
            int i=0;
            	
            while(rs.next()){
            	System.out.println("sang in while loop");
            	pi[i] = new QuarterInterestObject();			
                pi[i].setPygmyType(rs.getString("moduleabbr"));
                pi[i].setPygmyNo(rs.getInt("pm.ac_no"));
                pi[i].setIntDate(rs.getString("pm.open_date"));
                //				pi[i].setIntAmount(rs.getDouble("pt.cl_bal"));
                pi[i].setTml(rs.getString(3));				
                pi[i].setNomName(rs.getString("name"));
                pi[i].setNomRelation(rs.getString("relation"));
                pi[i].setIntPdUpto(rs.getString("lst_int_dt"));
                String date=rs.getString("pm.close_date");
                if(date==null)
                    pi[i].setUid(rs.getString("loan"));
                else
                    pi[i].setUid("Closed On :"+date);
                i++;
            }
           
            //rs.close();
           
            if(type==0)
            {
            	System.out.println("the length is"+pi.length);
            	for(int r=0;r<pi.length;r++)
             {
            	//rs=stmt.executeQuery("select pt.cl_bal from PygmyMaster pm, PygmyTransaction pt where pm.ac_no="+pi[r].getPygmyNo()+" and pt.ac_no=pm.ac_no and concat(right(pm.open_date,4),'-',mid(pm.open_date,locate('/',pm.open_date)+1,(locate('/',pm.open_date,4)-locate('/',pm.open_date)-1)),'-',left(pm.open_date,locate('/',pm.open_date)-1)) between '"+from_date+"' and '"+to_date+"'and pm.ve_tml is not null and pt.ve_tml is not null order by trn_seq desc");
            		rs=stmt.executeQuery("select pt.cl_bal from  PygmyTransaction pt where pt.ac_no="+pi[r].getPygmyNo()+" order by trn_seq desc limit 1");
                if(rs.next())
                    pi[r].setClBal(rs.getDouble(1));
                else
                    pi[r].setClBal(0.00);
             }
            }else if(type==1)
            {
            	if(pi!=null)
            	{
            	//System.out.println("Inside @@...");
            	for(int r=0; r<pi.length; r++)
            	{
            		//System.out.println("ACC No : "+pi[r].getPygmyNo());
            		rs=stmt.executeQuery("select pt.cl_bal from PygmyMaster pm, PygmyTransaction pt where pm.ac_no="+pi[r].getPygmyNo()+" and concat(right(pm.close_date,4),'-',mid(pm.close_date,locate('/',pm.close_date)+1,(locate('/',pm.close_date,4)-locate('/',pm.close_date)-1)),'-',left(pm.close_date,locate('/',pm.close_date)-1)) between '"+from_date+"' and '"+to_date+"' and pt.ac_no=pm.ac_no and pm.ve_tml is not null and pt.ve_tml is not null order by trn_seq desc");
            		
            		if(rs.next())
            		    pi[r].setClBal(rs.getDouble(1));
                    else
                        pi[r].setClBal(0.00);
            	}
            }
            }
            
            
            
        } catch (Exception e){e.printStackTrace();}
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        
        return pi;
    }
    
    public int setAgtToCustomers(int[] array_int_accnos,String string_from_agent,int int_from_agent,String string_to_agent,int int_to_agent)
    {
        Statement stmt;
        int flag=0,flag1=0;
        Connection connection = null;
        try
        {
            connection = getConnection();
            stmt=connection.createStatement();
            int size=array_int_accnos.length;
            
            for(int i=0;i<size;i++)
            {
                flag1=stmt.executeUpdate("Update PygmyMaster set agt_type='"+string_to_agent+"',agt_no="+int_to_agent+" where agt_type='"+string_from_agent+"' and agt_no="+int_from_agent+" and ac_no="+array_int_accnos[i]+" ");
                if(flag1 > 0)
                    flag=1;
            }
            
        }catch(SQLException e){
            e.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally{
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return flag;
    }
    
    public void setSessionContext(SessionContext ctx) throws EJBException {
        this.sessionContext  = ctx;
    }
    
    
    public int deleteRemittanceTransaction(Object[][] obj,int scroll_no,String coll_dt,String remit_dt)
    {
        Connection conn=null;
        Statement stmt;
        try{
            
            conn=getConnection();
            stmt=conn.createStatement();
            
            //for(int i=0; i<obj.length; i++)
            //{
                //stmt.executeUpdate("delete from PygmyTransaction where trn_date='"+remit_dt+"' and coll_date='"+coll_dt+"' and ref_no="+scroll_no+" and ve_tml is null and ac_no in(select ac_no from PygmyMaster where agt_no='"+agt_no+"') ");
                /*stmt.addBatch("delete from PygmyTransaction where trn_date='"+remit_dt+"' and coll_date='"+coll_dt+"' and ref_no="+scroll_no+" and ve_tml is null and ac_type='"+obj[i][0].toString()+"' and ac_no="+Integer.parseInt(obj[i][1].toString())+"");  
                stmt.addBatch("update PygmyMaster set lst_trn_seq=lst_trn_seq-1 where  ac_type='"+obj[i][0].toString()+"' and ac_no="+Integer.parseInt(obj[i][1].toString())+"");*/
            stmt.executeUpdate("delete from DailyRemittance where ref_no="+scroll_no+" and trn_date='"+remit_dt+"'"); 
            	
            //}
            //stmt.executeBatch();
            stmt.executeUpdate("update DayCash set attached='F' where trn_date='"+remit_dt+"' and scroll_no="+scroll_no);  
            return 1;
            
        }catch(SQLException se)
        {
            se.printStackTrace();
            sessionContext.setRollbackOnly();
        }finally{
            try{
                if(conn!=null)
                    conn.close();
                
            }catch(Exception e){e.printStackTrace();}
        }
        return -1;
    }
    
    /** 
     * To deposit amount into PygmyAccount Holders.And To Update the Remittance Details before Verification.
     * It will get The Pygmy Account Numbers for the Particular Agent and it will insert the records(Remittance detail) into the PygmyTransaction Table with Transaction Type as 'R' i.e for Remittance.
     * After inserting records(Remittance details) into the detial table it will update the 'DayCash' Table i.e it will set 'T' for attached field for the correspoding Scroll Number & Date.    
     * In Updation Case,
     * When displaying information of the Remittance details in Application for Updation the variable 'oldscrl' will take the value of 'scrlno'.If the user modify the scroll Number in Application while updation records(Remittance details) will be deleted from PygmyTransaction Table for the 'oldscrl' Value.And it will update the 'DayCash' table as Attached 'F'(i.e False) for the 'oldscrl' value.After that it will insert the new records(i.e updated Remittance detials entered in the Application) into the Detail table.After that it will update the Daycash table as attched 'T' for the 'scrlno'.
     *  
     * @param agttype - modulecode of the Agent Type. '1013001' for Agent
     * @param agtno -Agent Number Selected from the Application.
     * @param obj - Remittance data which has entered in the application table.
     * @param colldt - Collection  Date
     * @param scrlno - Scroll Number generated in Cash Counter for the Particular Agent.
     * @param oldscrl - It will be useful in case of updation case.
     * @param detml - Terminal Name
     * @param deuser - Terminal User Name
     * @return '1' if the transaction has successfully done,Otherwise '-1'.
     */
    public int storeDailyRemittance(String agttype,int agtno,Object[][] obj,String colldt,int scrlno,int oldscrl,String detml,String deuser,String curdate,String curdatetime,byte indicator)
    {
    	System.out.println("Inside StoreDailyRemittance.....");
        System.out.println("Old Scroll no: "+oldscrl);
        System.out.println("ScrlNo2: "+scrlno);
        System.out.println("Obj[0][0] value==="+obj[0][0]);
        ResultSet rs,rs1,rs_all=null;
        int trnseq;
        double clbal;
        Statement stmt,stmt1,stmt2;
        
        Connection connection = null;
        Statement stmt_insert=null,stmt_all=null;
        try
        {
            common = commonLocalHome.create();
            connection = getConnection();
            stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt1=connection.createStatement();
            stmt_insert=connection.createStatement();
            stmt2=connection.createStatement();
            stmt_all=connection.createStatement();
           /* //fetching all pygmyaccount nos from the database for the particular Agent
            //rs=stmt.executeQuery("select ac_type,ac_no from PygmyMaster where agt_type='"+agttype+"' and agt_no="+agtno);
            //file_logger.info("Ac_type: "+rs.getInt(1));
            while(rs .next())
            { //deleting record frm the table for the old scrollNo when updating.
                //stmt.executeUpdate("delete from PygmyTransaction where ac_type='"+rs.getString(1)+"' and ac_no="+rs.getInt(2)+" and coll_date='"+colldt+"' and ref_no="+oldscrl+" and ve_tml is null");
                
            	
            	stmt.executeUpdate("delete from DailyRemittance where ac_type='"+rs.getString(1)+"' and ac_no="+rs.getInt(2)+"  and coll_date='"+colldt+"' and ref_no="+oldscrl+" and ve_tml is null");
               
            	
            }*/
           
            rs_all=stmt_all.executeQuery("select * from DailyRemittance");
            if(rs_all.next())
            {
                rs_all.last();
                if(rs_all.getRow()>0 && rs_all.getString("ve_date")!=null)
                    stmt.executeUpdate("delete from DailyRemittance where ve_date is not null;");
                   
            }
            rs=stmt.executeQuery("select * from DailyRemittance where ac_type="+obj[0][0]+" and ref_no="+scrlno+" and coll_date='"+colldt+"' and trn_date='"+curdate+"' and ve_date is null");
            if(rs.next())
            {
                rs.last();
                System.out.println("rs number of rows=="+rs.getRow());
                stmt.executeUpdate("delete from DailyRemittance where ac_type="+obj[0][0]+" and ref_no="+scrlno+" and coll_date='"+colldt+"' and trn_date='"+curdate+"' and ve_date is null");
            }  
            /**
             * Should Refer Intermediate Table 'DailyRemittance' instead of 'PygmyTransaction' Table.
             * Should delete all records of Remittance Details.
             */
            
            rs=null;
            
            if(oldscrl!=scrlno){ 
                stmt.executeUpdate("update DayCash set attached='F' where trn_date='"+curdate+"' and scroll_no="+oldscrl);
                stmt2.execute("delete from DailyRemittance where ac_type="+obj[0][0]+" and ref_no="+oldscrl+" and coll_date='"+colldt+"' and trn_date='"+curdate+"' and ve_date is null");
            }
            
            /* getting de_tml for the scroll Number of the particular Agent.. Transaction Source=>de_tml of DayCash for the particular scrollno. */
            rs1=stmt1.executeQuery("select de_tml from DayCash where scroll_no="+scrlno+"");
            if(rs1.next()){
                String tml=rs1.getString("de_tml");
                for(int i=0;i<obj.length;i++)
                {
                    if((obj[i][1]!= null && (obj[i][1].toString()).length()>0) && (obj[i][2]!=null) && (obj[i][2].toString()).length()>0 && (obj[i][3]!=null)&&(obj[i][3].toString()).length()>0 && (obj[i][4]!=null)&&(obj[i][4].toString().length()>0))
                    {
                        /* fetching the pygmyAccount information from PygmyTransaction table(Database) for the last transation sequence of the particular date. */
                    
                        /**
                         *  get Closing Balance from PygmyTransaction
                         */
                        //rs=stmt.executeQuery("select trn_seq,cl_bal from PygmyTransaction where ac_type='"+obj[i][0].toString()+"' and ac_no="+(Integer.parseInt(obj[i][1].toString()))+" order by trn_seq desc limit 1");
                        rs=stmt.executeQuery("select trn_seq,cl_bal from PygmyTransaction where ac_type='"+obj[i][0].toString()+"' and ac_no="+(Integer.parseInt(obj[i][1].toString()))+" order by trn_seq desc limit 1");
                        clbal=0;
                        if(rs.next()){
                            try
                            {
                            	System.out.println("clBal: "+rs.getDouble(2));
                                trnseq=rs.getInt(1);
                                clbal=rs.getDouble(2);
                            }catch(SQLException sql){clbal=0;trnseq=0;}
                            trnseq++;
                            clbal+=Double.parseDouble(obj[i][4].toString());
                        }
                        else
                        {
                           clbal=Double.parseDouble(obj[i][4].toString());  
                        }
                        
                        /* inserting information of the pygmyaccounts into the Transaction table by reading the whtever enterd in the remittance form. */
                        //stmt.executeUpdate("insert into PygmyTransaction values('"+obj[i][0].toString()+"',"+(Integer.parseInt(obj[i][1].toString()))+","+trnseq+",'"+common.getSysDate()+"','R',"+Double.parseDouble(obj[i][4].toString())+",0.00,'C','"+rs1.getString("de_tml")+"','C',"+clbal+","+scrlno+",'Remittance,ScrollNo "+scrlno+"','"+colldt+"',null,0.00,0.00,'"+detml+"','"+deuser+"','"+common.getDateTime()+"',null,null,null,1)");
                        //pstmt=connection.prepareStatement("insert into PygmyTransaction values('"+obj[i][0].toString()+"',"+(Integer.parseInt(obj[i][1].toString()))+","+trnseq+",'"+common.getSysDate()+"','R',"+Double.parseDouble(obj[i][4].toString())+",0.00,'C','"+rs1.getString("de_tml")+"','C',"+clbal+","+scrlno+",'Remittance,ScrollNo "+scrlno+"','"+colldt+"',null,0.00,0.00,'"+detml+"','"+deuser+"','"+common.getDateTime()+"',null,null,null,?)");
                        //pstmt=connection.prepareStatement("insert into DailyRemittance values('"+obj[i][0].toString()+"',"+(Integer.parseInt(obj[i][1].toString()))+",null,'"+common.getSysDate()+"','R',"+Double.parseDouble(obj[i][4].toString())+",0.00,'C','"+rs1.getString("de_tml")+"','C',"+clbal+","+scrlno+",'Remittance,ScrollNo "+scrlno+"','"+colldt+"',null,0.00,0.00,'"+detml+"','"+deuser+"','"+common.getDateTime()+"',null,null,null,?)");
                        
                        /*if(indicator==0)
                         pstmt.setInt(1,1); //ref_ind=1 When Data Entry..
                        else if(indicator==1)
                        pstmt.setInt(1,2);*/
                        //pstmt.setInt(1,1);
                        //pstmt.addBatch();
                        stmt_insert.addBatch("insert into DailyRemittance values('"+obj[i][0].toString()+"',"+(Integer.parseInt(obj[i][1].toString()))+",null,'"+curdate+"','R',"+Double.parseDouble(obj[i][4].toString())+",0.00,'C','"+tml+"','C',"+clbal+","+scrlno+",'Remittance,ScrollNo "+scrlno+"','"+colldt+"',null,0.00,0.00,'"+detml+"','"+deuser+"','"+curdatetime+"',null,null,null,1)");
                        //stmt.executeUpdate("update PygmyMaster set lst_trn_seq="+trnseq+" where ac_type='"+obj[i][0].toString()+"' and ac_no="+(Integer.parseInt(obj[i][1].toString())));
                        //file_logger.info("obj value:"+obj[i][0].toString());
                    }
                    System.out.println("i Value: "+i);
                }
                stmt_insert.executeBatch();
            }
            if(obj.length==0) //After Deleteing All the existing unverified remittance records from trn, releasing the Scroll NO.
            stmt.executeUpdate("update DayCash set attached='F' where trn_date='"+curdate+"' and scroll_no="+scrlno);   
            else    
            stmt.executeUpdate("update DayCash set attached='T' where trn_date='"+curdate+"' and scroll_no="+scrlno);
            
            return 1;
        
        }
            catch(SQLException se){
            se.printStackTrace();
            sessionContext.setRollbackOnly();
        } catch (CreateException e) {
            e.printStackTrace();
        }
        finally{
            try {
                common = null;
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
    
    /**
     *   H.R.B
     * 
     * Used to update the 'ref_ind' field as 2 (Updation mode) in 'PygmyTransaction'
     * table for the UnVerified Remittance Details.
     * @return
     */
    public int getUpdateRemittanceRefInd(String agt_ty,int agt_no,String coll_dt){
    	Connection conn=null;
    	PreparedStatement pstmt=null;
    	Statement stmt=null;
    	ResultSet rs=null;
    	try{
    		System.out.println("Inside Updation of Remittance ref_id");
    		System.out.println(agt_ty);
    		System.out.println(agt_no);
    		System.out.println(coll_dt);
    		
    		conn=getConnection();
    		stmt=conn.createStatement();
    		
    		rs=stmt.executeQuery("select dr.* from PygmyMaster pm,AgentMaster am,DailyRemittance dr where  am.ac_type='"+agt_ty+"' and am.ac_no="+agt_no+" and pm.agt_no=am.ac_no and pm.agt_type=am.ac_type and dr.ac_no=pm.ac_no and dr.coll_date='"+coll_dt+"' and dr.ve_tml is null");
    		rs.next();
    		rs.last();
    		System.out.println("Length : "+rs.getRow());
    		
    		rs.beforeFirst();
    		int i=0;
    		while(rs.next())
    		{
    		 pstmt=conn.prepareStatement("update DailyRemittance set ref_ind=? where ac_type=? and ac_no=? and coll_date=? ");
    		 
    		 pstmt.setInt(1,2); //ref_ind=2 =>Updation Mode.
    		 pstmt.setString(2,rs.getString("ac_type"));
    		 pstmt.setInt(3,rs.getInt("ac_no"));
    		 pstmt.setString(4,rs.getString("coll_date"));
    		 
    		 
    		 if(pstmt.executeUpdate()!=0)
    		  {
    		 	System.out.println("Updated 'ref_ind' field of DailyRemittance..");
    		    i++;
    		  }
    		}
    		if(i!=0)
    		return 1;
    		
    	}catch(SQLException se){se.printStackTrace();}
    	finally{
    		try{
    			conn.close();
    		}catch(SQLException e){e.printStackTrace();}
    	}
    	return 0;
    }
 
    
    /**
     * To verify the Pygmy Withdrawal/Closure Transaction.
     * At the end of verification GL Posting have written for the correponding Transaction.
     * @return '1' if it is successfully Verified.Otherwise '-1'.
     */
    public int verifyClosure(double loan_bal,double loan_interest,String curdate,String curdatetime,String g_acctype,int g_acno,String g_tml,String g_user)
    {
    	Connection connection = null;
    	int lst_vch_no=0,pay_order_no=0;
    	int trn_seq=0;
    	String sysdate="",v_main_cashier=null;
    	ResultSet rs1=null,rs_cn=null,rs_name=null,rs_loan=null;
    	PayOrderObject payOrderObject=null; 
        g_pgaccno=g_acno;
        g_pgacctype=g_acctype;
    	
    	double interest_amount=0.0; // Code added on 26/05/2006
    	try {
    		
    		common = commonLocalHome.create();
    		
    		sysdate=common.getDateTime();
    		connection = getConnection();
    		Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
    		Statement stat = connection.createStatement();
    		Statement stmt1=connection.createStatement();
    		Statement stmt_cn=connection.createStatement();
    		Statement stmt_name=connection.createStatement();
    		Statement stmt_loan=connection.createStatement();
    		rs1=stmt1.executeQuery("select cash_close from DailyStatus where trn_dt='"+Validations.convertYMD(curdate)+"'");
    		if(rs1.next())
    			v_main_cashier=rs1.getString(1);
    		
    			//ResultSet rs = stmt.executeQuery("select * from PygmyTransaction where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and (trn_type='E' or trn_type='W') order by trn_seq ");
    			ResultSet rs = stmt.executeQuery("select * from PygmyTransaction where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and (trn_type='E' or trn_type='W' or trn_type='S') order by trn_seq");
    			System.out.println("Inside VerifyClosure....");
    			int r=0;
    			ResultSet rs_trn_seq=stmt1.executeQuery("select trn_seq from PygmyTransaction where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and ve_tml is not null and ref_ind=3 order by trn_seq desc limit 1");
				
    			if(rs_trn_seq.next())
    			    trn_seq=rs_trn_seq.getInt(1);
    			
				rs_trn_seq=null;
				System.out.println("trn_seq => "+trn_seq);
    			
				while(rs.next())
    			{
    				
    				System.out.println("Loop Count : "+r);
    				System.out.println("Trn Mode => "+rs.getString("trn_mode"));
    				System.out.println("Trn Narr => "+rs.getString("trn_narr"));
    				System.out.println("Trn Type => "+rs.getString("trn_type"));
    				System.out.println("Cd Ind => "+rs.getString("cd_ind"));
    				
    				PygmyTransactionObject pt = new PygmyTransactionObject();
    				Statement st = connection.createStatement();
    				ResultSet rs_gl;
    				    				
    				
    				if(rs.getString("cd_ind").equalsIgnoreCase("C")) //if transaction is Credit to GL.
    				{   
    					System.out.println("cd_ind: C");
    					stat.executeUpdate("update PygmyTransaction set trn_type='I',trn_seq="+(++trn_seq)+",ve_tml='"+g_tml+"',ve_user='"+g_user+"',ve_date='"+curdatetime+"',ref_ind=3 where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and trn_type='"+rs.getString("trn_type")+"' and cd_ind='C'");
    					
    					stat.executeUpdate("update PygmyMaster set lst_trn_seq="+trn_seq+" where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);
    					
    					pt.setTranType("I");
    				}
    				else{//if Transaction is Debit to Pygmy Account...
    					System.out.println("1 cd_ind= D .......");
    					System.out.println("ACCCC TYYYPE==="+g_pgacctype);
    					System.out.println("ACCCC NUUM==="+g_pgaccno);
    					System.out.println("TRRRN TYYYPE==="+rs.getString("trn_type"));
    					//System.out.println("Trnsaction Seq : "+rs.getInt("trn_seq"));
    					stat.executeUpdate("update PygmyTransaction set trn_type='P',trn_seq="+(++trn_seq)+",ve_tml='"+g_tml+"',ve_user='"+g_user+"',ve_date='"+curdatetime+"',ref_ind=3 where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and trn_type='"+rs.getString("trn_type")+"'");
    					/*if(stat.executeUpdate("update PygmyTransaction set trn_type='P',trn_seq="+(++trn_seq)+",ve_tml='"+g_tml+"',ve_user='"+g_user+"',ve_date='"+curdatetime+"',ref_ind=3 where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and trn_type='"+rs.getString("trn_type")+"'")!=0)
    						System.out.println("Updated Pyg Trn");
    					else*/
    						System.out.println("Unable to Update Pyg Trn");
    					
    					System.out.println("After"+trn_seq);
    					if(stat.executeUpdate("update PygmyMaster set lst_trn_seq="+trn_seq+" where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno)!=0)
    						System.out.println("Updated Pyg Mast");
    					else
    						System.out.println("Unable to Update Pyg Mast");
    					
    					System.out.println("loop count "+r);
    					System.out.println("After@@"+trn_seq);
    					
    					pt.setTranType("P");
    					
    					System.out.println("2.......");
    				}  // end of Updating verification fields of 'PygmyTransaction' Table...
    				
    				/*System.out.println("@@@ trn_seq => "+trn_seq);
    				rs_trn_seq=stmt1.executeQuery("select ve_tml from PygmyTransaction where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and trn_seq="+trn_seq+"");
    				rs_trn_seq.next();
    				if(rs_trn_seq.getString(1)!=null)
    				{//=> Checking Pyg trn Before going to GL */    				
    				
    				if(rs.getString("trn_narr").equalsIgnoreCase("Intr"))
    				{				
    					//stat.execute("update PygmyMaster set lst_int_dt='"+sysdate+"' where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);

                        //Debit Interest GL	
    					common = commonLocalHome.create();
    					System.out.println("trn_narr is 'Intr' , Debit Interest GL.. ");
    					rs_gl=st.executeQuery("select * from GLKeyParam where ac_type='"+g_pgacctype+"' and code=3");
    					rs_gl.next();
    					GLTransObject gl=new GLTransObject();
    					gl.setTrnDate(sysdate);
    					gl.setGLType(rs_gl.getString("gl_type"));
    					gl.setGLCode(rs_gl.getString("gl_code"));
    					gl.setTrnMode(rs.getString("trn_mode"));
    					gl.setAmount(rs.getDouble("trn_amt"));
    					interest_amount=rs.getDouble("trn_amt"); // Code added on 26/05/2006
    					gl.setCdind("D");
    					gl.setAccType(g_pgacctype);
    					gl.setAccNo(String.valueOf(g_pgaccno));
    					gl.setTrnSeq(rs.getInt("trn_seq"));
    					//glpost.setTrnType(rs.getString("trn_type"));
    					gl.setTrnType("I");
    					gl.setRefNo(0);
    					gl.setVtml(g_tml);
    					gl.setVid(g_user);	
    					gl.setVDate(curdatetime);
    					System.out.println(common);
    					common.storeGLTransaction(gl);
    					
    					stat.execute("update PygmyMaster set lst_int_dt='"+curdate+"' where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);
    				    
    				
    				
    				}
    				if(rs.getString("trn_narr").equalsIgnoreCase("Penalty")){
    					//credit profit GL
    					System.out.println("Trn_type narr is 'Penalty',  Credit Entry to GL..");
    					
    				}
    				if(rs.getString("trn_narr").equalsIgnoreCase("Loan")){
    					System.out.println("Trnsaction narr is 'Loan'.,");
    					
    					rs_loan=stmt_loan.executeQuery("select * from GLKeyParam where ac_type='1008006' and code=1");
    					//rs_loan=stmt_loan.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where gk.ac_type='1008006' and gk.code=1 and gp.ac_type=gk.ac_type and gp.trn_type='R' and gp.cr_dr='C'")
    					if(rs_loan.next())
    				  {   
    					GLTransObject gl_lnbal=new GLTransObject();
    					gl_lnbal.setTrnDate(curdate);
    					gl_lnbal.setGLType(rs_loan.getString("gl_type"));
    					gl_lnbal.setGLCode(rs_loan.getString("gl_code"));
    					gl_lnbal.setTrnMode(rs.getString("trn_mode"));
    					gl_lnbal.setAmount(loan_bal);
    					gl_lnbal.setCdind("C");
    					gl_lnbal.setAccType(rs.getString("ac_type"));
    					gl_lnbal.setAccNo(rs.getString("ac_no"));
    					gl_lnbal.setTrnSeq(rs.getInt("trn_seq"));
    					gl_lnbal.setTrnType("R");
    					gl_lnbal.setRefNo(0);
    					gl_lnbal.setVtml(g_tml);
    					gl_lnbal.setVid(g_user);
    					gl_lnbal.setVDate(curdatetime);
    					System.out.println("GL LOAN BALANCE11111==="+gl_lnbal.getAccNo());
    					System.out.println("GL LOAN BALANCE222==="+gl_lnbal.getVDate());
    					common = commonLocalHome.create();
    					common.storeGLTransaction(gl_lnbal);
    				  }
    					rs_loan=null;
    					
    					rs_loan=stmt_loan.executeQuery("select * from GLKeyParam where ac_type='1008006' and code=2");
    					if(rs_loan.next())
    				  {
    					GLTransObject gl_lnint=new GLTransObject();
    					gl_lnint.setTrnDate(curdate);
    					gl_lnint.setGLType(rs_loan.getString("gl_type"));
    					gl_lnint.setGLCode(rs_loan.getString("gl_code"));
    					gl_lnint.setTrnMode(rs.getString("trn_mode"));
    					gl_lnint.setAmount(loan_interest);
    					gl_lnint.setCdind("C");
    					gl_lnint.setAccType(rs.getString("ac_type"));
    					gl_lnint.setAccNo(rs.getString("ac_no"));
    					gl_lnint.setTrnSeq(rs.getInt("trn_seq"));
    					gl_lnint.setTrnType("R");
    					gl_lnint.setRefNo(0);
    					gl_lnint.setVtml(g_tml);
    					gl_lnint.setVid(g_user);
    					gl_lnint.setVDate(curdatetime);
    					common.storeGLTransaction(gl_lnint);
    				  }
    				}
    				if(rs.getString("trn_type").equalsIgnoreCase("W") || rs.getDouble("cl_bal")==0 )
    				{
    					System.out.println("If trn_type is W, Credit Cash /PayOrder/SB GL...");
    					//credit cash/po/SB GL
    					String modulecode="1019000"; // Cash GL from Modules Table
    					
    					System.out.println("Transaction Narr => "+rs.getString("trn_narr"));
    					
    					if(rs.getString("trn_mode").equalsIgnoreCase("C") && rs.getString("trn_narr").equalsIgnoreCase("Withdrawal Cash") || rs.getString("trn_narr").equalsIgnoreCase("Closure-Cash")) 
    					{   
    						//TODO write cash GL
    						System.out.println("write into DayCash... Cr Entry into Day Cash@@@");	
    						//rs_gl = st.executeQuery("select lst_voucher_scroll_no from Modules where modulecode='"+g_pgacctype+"'");
    						//st.executeUpdate("update Modules set lst_voucher_scroll_no=lst_voucher_scroll_no+1 where modulecode='"+modulecode+"'");
    						//rs_gl = st.executeQuery("select lst_voucher_scroll_no from Modules where modulecode='"+modulecode+"'");
    						//rs_gl.next();
    						//lst_vch_no=rs_gl.getInt(1); //getting the Voucher Number..
    						common = commonLocalHome.create();
    						lst_vch_no=common.getModulesColumn("lst_voucher_scroll_no",modulecode); //getting the Voucher Number..
    						System.out.println("Voucher No: "+lst_vch_no);
    						rs_cn=stmt_cn.executeQuery("select concat_ws(' ',cm.salute,cm.fname,cm.mname,cm.lname) as name from CustomerMaster cm,PygmyMaster pm where pm.ac_type='"+rs.getString("ac_type") +"' and ac_no="+rs.getString("ac_no")+" and cm.cid=pm.cid");
    						rs_cn.next();  //getting name of the account Holder..
    				  						
    						PreparedStatement pstmt = connection.prepareStatement("insert into DayCash(trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_no,vch_type,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,trn_type,name,attached) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");						
    						System.out.println("1........");
    						pstmt.setString(1,curdate);
    						pstmt.setString(2,g_pgacctype);
    						pstmt.setInt(3,g_pgaccno);
    						System.out.println("Transaction Amount: "+rs.getDouble("trn_amt"));
    						pstmt.setDouble(4,rs.getDouble("trn_amt"));
    						pstmt.setString(5,"C");  //cd_ind
    						pstmt.setInt(6,lst_vch_no);
    						//pstmt.setString(7,"P");
    						pstmt.setString(7,"W");
    						pstmt.setString(8,rs.getString("de_user"));//de_user
    						pstmt.setString(9,rs.getString("de_tml"));//de_tml
    						pstmt.setString(10,rs.getString("de_date"));//de_dt
    						pstmt.setString(11,g_user);
    						pstmt.setString(12,g_tml);
    						pstmt.setString(13,curdatetime);
    						pstmt.setString(14,"P");
    						pstmt.setString(15,rs_cn.getString("name"));
    						pstmt.setString(16,"F");
    						pstmt.executeUpdate();
    						
    						st.executeUpdate("update PygmyTransaction set ref_no="+lst_vch_no+",trn_narr='Withdrwal Cash"+" "+"Voucher No: "+lst_vch_no+"' where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and trn_seq="+rs.getInt("trn_seq"));
    						
    						//updating last withdrawal number in PygmyMaster..
    						stat.executeUpdate("update PygmyMaster set lst_wdl_no=lst_wdl_no+1,lst_wdl_dt='"+curdate+"' where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);  
    						//stat.execute("update PygmyMaster set lst_wdl_no=lst_wdl_no+1,lst_wdl_dt='"+sysdate+"' where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);
    						System.out.println("After Updation of PygmyMaster..");
    						
    						
    						/*rs1=stmt1.executeQuery("select * from GLKeyParam where ac_type=1019001");
    						 rs1.next();*/
    						
    						
    						System.out.println("2........");
    						
    					}
    					else if(rs.getString("trn_mode").equalsIgnoreCase("T") ) 
    					{
    						//Inserting into 'PayOrderMake' table@@ & Cr Entry to GLTransaction
    					    if(rs.getString("trn_narr").equalsIgnoreCase("Closure-PayOrder")|| rs.getString("trn_narr").equalsIgnoreCase("Pay Order"))
    					  {
    					    System.out.println("Inserting into 'PayOrderMake' table@@ & Cr Entry to GLTransaction");	
    						
    						System.out.println("3.....");
    						rs_name=stmt_name.executeQuery("select * from PygmyMaster where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" ");
    						rs_name.next();
    						int pygmy_cid=rs_name.getInt("cid");
    						rs_name.close();
    						
    						rs_name=stmt_name.executeQuery("select concat_ws('',salute,fname,mname,lname) as name,sub_category from CustomerMaster where cid="+pygmy_cid+"");                            
    						System.out.println("If Trnasaction type is 'Pay Order'...");
    						rs_name.next();
    						
    						payOrderObject = new PayOrderObject();
    						
    						payOrderObject.setPOPayee(rs_name.getString(1)); //name
    						payOrderObject.setPOFavour(rs_name.getString(1));
    						payOrderObject.setPOCustType(rs_name.getInt("sub_category"));
    						rs_name.close();
    						//rs_name=stmt_name.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where gk.ac_type="+pygmytransactionobject.getAccType()+" and gk.code=1 and gp.ac_type=gk.ac_type and gp.trn_type='"+pygmytransactionobject.getTranType()+"' and gp.cr_dr='"+pygmytransactionobject.getCDInd()+"'");
    						rs_name=stmt_name.executeQuery("select * from GLKeyParam where ac_type='1006001' and code=1");
    						rs_name.next();
    						
    						payOrderObject.setPOGlCode(rs_name.getInt("gl_code"));
    						payOrderObject.setPOGlType(rs_name.getString("gl_type"));
    						payOrderObject.setPOGlName(rs_name.getString("key_desc"));
    						rs_name.close();
    						
    						
    						payOrderObject.setPOType("X");
    						payOrderObject.setPODate(curdate);
    						payOrderObject.setPOAccType(g_pgacctype);
    						payOrderObject.setPOAccNo(g_pgaccno);
    						
    						payOrderObject.setPOAmount(rs.getDouble("trn_amt")+rs.getDouble("int_paid"));
    						payOrderObject.setCommissionAmount(0.00);
    						payOrderObject.setPOMade("F");
    						payOrderObject.uv.setUserTml(g_tml);
    						payOrderObject.uv.setUserId(g_user);
    						payOrderObject.uv.setVerTml(g_tml);
    						payOrderObject.uv.setVerId(g_user);
    						payOrderObject.uv.setVerDate(curdatetime);
    						System.out.println("pay="+payOrderObject.getPOAccNo());
    						common = commonLocalHome.create();
    						pay_order_no=common.storePayOrder(payOrderObject);//insert into PayOrder Table
    						
    						System.out.println("Pygmy AccNo: "+g_pgaccno);
    						System.out.println("Trn Seq: "+rs.getInt("trn_seq"));
    						st.executeUpdate("update PygmyTransaction set ref_no="+pay_order_no+", trn_narr='Pay Order No:  "+pay_order_no+"' where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and trn_seq="+rs.getInt("trn_seq"));
    						
    						//rs_gl=st.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where gk.ac_type=1016001  and gk.code=1 and gp.ac_type=gk.ac_type and gp.trn_type='P' and gp.cr_dr='C'");
    						
    						rs_gl=st.executeQuery("select * from GLKeyParam where ac_type='1016001' and code=1");
    						
    						rs_gl.next();
    						GLTransObject glpost=new GLTransObject();
    						glpost.setTrnDate(sysdate);
    						glpost.setGLType(rs_gl.getString("gl_type"));
    						glpost.setGLCode(rs_gl.getString("gl_code"));
    						glpost.setTrnMode(rs.getString("trn_mode")); //changed...
    						//glpost.setAmount(rs.getDouble("trn_amt")*rs_gl.getInt("mult_by"));
    						glpost.setAmount(rs.getDouble("trn_amt")+rs.getDouble("int_paid"));
    						glpost.setCdind("C");
    						glpost.setAccType(rs_gl.getString("ac_type"));
    						glpost.setAccNo(String.valueOf(pay_order_no));
    						glpost.setTrnSeq(0);
    						glpost.setTrnType("P");
    						glpost.setRefNo(pay_order_no);
    						glpost.setVtml(g_tml);
    						glpost.setVid(g_user);
    						glpost.setVDate(curdatetime);
    						common.storeGLTransaction(glpost);	//Insert into GLTransaction	
    						
    						stat.executeUpdate("update PygmyMaster set lst_wdl_no=lst_wdl_no+1,lst_wdl_dt='"+curdate+"' where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);  
    						//stat.execute("update PygmyMaster set lst_wdl_no=lst_wdl_no+1,lst_wdl_dt='"+sysdate+"' where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);
    						System.out.println("After Updation of PygmyMaster..");
    					    }
    					    else
        					{  
        						//Cr Entry to Pay Acc No.
        						
        						System.out.println("Trnsaction Type is 'T'..");
        						AccountTransObject am = new AccountTransObject();
        						
        						
        						System.out.println("Transaction narration : "+rs.getString("trn_narr"));
        						
        						
        						StringTokenizer stoken= new StringTokenizer(rs.getString("trn_narr"),"-");
        						String sb_ac_type=stoken.nextToken();
        						int sb_ac_no=Integer.parseInt(stoken.nextToken());
        						//String sb_ac_no=stoken.nextToken();
        						System.out.println("sb acc type==="+sb_ac_type);
        						System.out.println("sb ac no==="+sb_ac_no);
        						am.setAccType(sb_ac_type);
        						am.setAccNo(sb_ac_no);
        						am.setTransDate(rs.getString("trn_date"));
        						am.setTransType("R");
        						am.setTransAmount(rs.getDouble("trn_amt"));
        						am.setTransMode("T");
        						am.setTransSource(g_tml);
        						am.setCdInd("C");
        						
        						System.out.println("AccType : "+g_pgacctype);
        						common = commonLocalHome.create();
        						//String pgtype=common.getMainModules(3,g_pgacctype)[0].getModuleAbbrv();
        						String pgtype=common.getMainModules(3,g_pgacctype)[0].getModuleCode();
        						am.setTransNarr(pgtype+" "+g_pgaccno);
        						String sbtype = common.getMainModules(3,sb_ac_type)[0].getModuleAbbrv();
        						stat.execute("update PygmyTransaction set trn_narr='Trf "+sbtype+" "+sb_ac_no+"' where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno+" and trn_seq="+rs.getInt("trn_seq"));
        						am.setRef_No(0);
        						am.uv.setUserTml(g_tml);
        						am.uv.setUserId(g_user);
        						am.uv.setUserDate(curdate);
        						am.uv.setVerTml(g_tml);
        						am.uv.setVerId(g_user);
        						am.uv.setVerDate(curdatetime);
        						int updated = common.storeAccountTransaction(am);
        						if(updated != 1)
        							throw new SQLException("Exception while writing to Trf A/C");
        					
        						stat.executeUpdate("update PygmyMaster set lst_wdl_no=lst_wdl_no+1,lst_wdl_dt='"+curdate+"' where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);  
        						//stat.execute("update PygmyMaster set lst_wdl_no=lst_wdl_no+1,lst_wdl_dt='"+sysdate+"' where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);
        						System.out.println("After Updation of PygmyMaster..");
        					
        					
        					}    
    						
    					}
    					
    					System.out.println("If cl_bal=0");
    					
    					if(rs.getDouble("cl_bal")==0)
    						stat.execute("update PygmyMaster set status='C',close_date='"+curdate+"' where ac_type='"+g_pgacctype+"' and ac_no="+g_pgaccno);
    					System.out.println("If cl_bal=0");
    				} // => End of 'trn_type'=W or 'cl_bal'=0.
    				
    				
    				
    				//Continuation of setting remaining values in the pygmyTransaction Object
    					//GL Entries for CASH with respective 'cd_ind'=> from PygmyTransaction
    					//Dr Entries 
    					
    				if(rs.getString("trn_mode").equalsIgnoreCase("C") || rs.getString("trn_mode").equalsIgnoreCase("P") || rs.getString("trn_mode").equalsIgnoreCase("T") )
    				{
    					//GL Entries for CASH with respective 'cd_ind'=> from PygmyTransaction
    					//Dr Entries 
    					
    					System.out.println("Setting remains into the PygmyTransaction Obj..");
    					System.out.println("GL Entries for CASH with respective 'cd_ind'=> from PygmyTransaction");;
    					
    					pt.setTranDate(sysdate);
    					pt.setTranSource(rs.getString("trn_source")); //User
    					pt.setTranAmt(rs.getDouble("trn_amt"));
    					pt.setCDInd(rs.getString("cd_ind"));  //before verification 'D'
    					System.out.println("AccType:  "+rs.getString("ac_type"));
    					pt.setAccType(rs.getString("ac_type"));
    					pt.setAccNo(rs.getInt("ac_no"));
    					pt.setTranSeq(rs.getInt("trn_seq"));
    					//pt[0].setRefNo(0);
    					if(pay_order_no!=0)
    					    pt.setRefNo(pay_order_no);
    					else
    					   pt.setRefNo(lst_vch_no);
    					//pt[0].setTranAmt(lst_vch_no);
    					pt.setTranMode(rs.getString("trn_mode"));
    					
    					System.out.println("Before Writing into CASHGL...");
    					writePygmyGL(pt,g_tml,g_user,curdatetime,"CASH");   // => PD Acc No & code=1
    					
    					
    					if(rs.getString("trn_mode").equalsIgnoreCase("C")||rs.getString("trn_mode").equalsIgnoreCase("T"))
    					{
    						//Cr Entry to CASH GL  for trn_amount
    						
    						ResultSet rs_bal=null;
    						Statement stmt_bal=connection.createStatement();
    						System.out.println("Tran Narr => "+rs.getString("trn_narr"));
    						System.out.println("trn mode==>"+rs.getString("trn_mode"));
    						if(rs.getString("trn_narr").equalsIgnoreCase("Withdrawal Cash") || rs.getString("trn_narr").equalsIgnoreCase("Closure-Cash") )
    						  rs_bal=stmt_bal.executeQuery("select * from GLKeyParam where ac_type='1019001' and  code=1");
    						else if(rs.getString("trn_narr").equalsIgnoreCase("Penalty") && rs.getString("trn_mode").equalsIgnoreCase("T"))
    					      rs_bal=stmt_bal.executeQuery("select * from GLKeyParam where ac_type='"+rs.getString("ac_type")+"' and  code=4");	
    					    
    						
    						if(rs_bal!=null)
    						{
    						 if(rs_bal.next())
    						{
    						System.out.println("inside rsbal next"); 
    						System.out.println("rs_bal glcode==="+rs_bal.getString("gl_code"));
    						GLTransObject gl=new GLTransObject();
    						gl.setTrnDate(curdate);
    						gl.setGLType(rs_bal.getString("gl_type"));
    						gl.setGLCode(rs_bal.getString("gl_code"));
    						gl.setTrnMode(rs.getString("trn_mode")); //changed..
    						//System.out.println("TranAmnt :"+pygmytransactionobject[i].getTranAmt());
    						//gl.setAmount(rs.getDouble("trn_amt")*rs_bal.getInt("mult_by"));
    						gl.setAmount(rs.getDouble("trn_amt"));
    						gl.setCdind("C");
    						gl.setAccType(rs.getString("ac_type"));//shubha...on 27/06/06
    						gl.setAccNo(rs.getString("ac_no"));
    						//System.out.println(pygmytransactionobject[i].getAccNo()+"   tran seq  GL............"+pygmytransactionobject[i].getTranSeq());
    						gl.setTrnSeq(rs.getInt("trn_seq"));
    						gl.setTrnType("P");//ref_trn_type
    						gl.setRefNo(lst_vch_no);
    						gl.setVtml(g_tml);
    						gl.setVid(g_user);
    						gl.setVDate(curdatetime);
    						
    						common = commonLocalHome.create();
    						System.out.println("GL: "+common);
    						common.storeGLTransaction(gl);
    					}	
    				}	 
    			  }
    			}    				
    			// }//=> end of Checking ve_tml is null or  not in Pyg Tran..
    				r++;
    			} //end of while rs(next) statement
    			//Update Last WithDrawal Date and No
    			System.out.println("Before Updation");
    			//System.out.println("Date: "+common.getSysDate());
    			System.out.println("g_AccType: "+g_pgacctype);
    			System.out.println("g_accNo: "+g_pgaccno);
    			System.out.println("last voucher number==="+lst_vch_no);
    			
    			if(lst_vch_no!=0)
    				return lst_vch_no;
    			else if(pay_order_no!=0)
    			{
    			    lst_vch_no=pay_order_no;
    			    return lst_vch_no;
    			}
    			else
    			    return -2;
    			
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    		sessionContext.setRollbackOnly();
    	} catch (CreateException e) {
    		e.printStackTrace();
    	}
    	finally{
    		common = null;
    		try {
    			connection.close();
    		} catch (SQLException e1) {
    			e1.printStackTrace();
    		}
    	}
    	return -1;
    }
    /**
     * GL Posting for Pygmy Withdrwal/Closing - Cash Pyament Mode
     * Get the GLCode,GLType,Multiply factor from GLKeyparam Table for the following condition.
     * Call 'storeGLTransaction' method in Common Bean to strore the transaction details in GLTransaction table.
     * @param pygmytransactionobject - Data to write into GLTransaction Table.
     * @param vetml - Verification Terminal
     * @param veuser - Verification User
     * @return -'true' if transaction successfully happened.Otherwise 'false'
     */
    public boolean writePygmyGL(PygmyTransactionObject pygmytransactionobject,String vetml,String veuser,String curdatetime,String GLType){
        Connection connection = null;
        try
        {
            System.out.println("Inside PygmyGL Writing...");
            common = commonLocalHome.create();
            connection = getConnection();
            
            Statement stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement stmt1=connection.createStatement();
            ResultSet rs1=null;
            int i;
            
                ResultSet rs=null;
                //if(pygmytransactionobject[i].getTranType().equalsIgnoreCase("R") || pygmytransactionobject[i].getTranType().equalsIgnoreCase("P"))
                //rs=stmt.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where gk.ac_type="+pygmytransactionobject[i].getAccType()+" and gk.code=1 and gp.ac_type=gk.ac_type and gp.trn_type='"+pygmytransactionobject[i].getTranType()+"' and gp.cr_dr='"+pygmytransactionobject[i].getCDInd()+"'");
                
                System.out.println("AccType :"+pygmytransactionobject.getAccType());
                System.out.println("TrnType: "+pygmytransactionobject.getTranType());
                System.out.println("Cr_Dr: "+pygmytransactionobject.getCDInd());
                //rs=stmt.executeQuery("select gk.gl_code,gk.gl_type,gp.mult_by from GLKeyParam gk,GLPost gp where gk.ac_type='"+pygmytransactionobject[i].getAccType()+"' and gk.code=1 and gp.ac_type=gk.ac_type and gp.gl_code=gk.gl_code and gp.trn_type='"+pygmytransactionobject[i].getTranType()+"' and gp.cr_dr='"+pygmytransactionobject[i].getCDInd()+"'");
                 
                if(GLType.startsWith("CASH"))
                rs=stmt.executeQuery("select * from GLKeyParam where ac_type='"+pygmytransactionobject.getAccType()+"' and code=1");
                else if(GLType.startsWith("PD"))
                rs=stmt.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where gk.ac_type="+pygmytransactionobject.getAccType()+" and gk.code=1 and gp.ac_type=gk.ac_type and gp.trn_type='"+pygmytransactionobject.getTranType()+"' and gp.cr_dr='"+pygmytransactionobject.getCDInd()+"'");	
                	
                //else if(pygmytransactionobject[i].getTranType().equalsIgnoreCase("I"))
                //	rs=stmt.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where gk.ac_type="+pygmytransactionobject[i].getAccType()+" and gk.code=3 and gp.ac_type=gk.ac_type and gp.trn_type='"+pygmytransactionobject[i].getTranType()+"' and gp.cr_dr='"+pygmytransactionobject[i].getCDInd()+"'");
                System.out.println("1.......");
                rs.next();
                               
                GLTransObject glpost=new GLTransObject();
               
                //glpost=new GLTransObject();
                glpost.setTrnDate(pygmytransactionobject.getTranDate());
                glpost.setGLType(rs.getString("gl_type"));
                glpost.setGLCode(rs.getString("gl_code"));
                glpost.setTrnMode(pygmytransactionobject.getTranMode()); //changed..
                System.out.println("TranAmnt :"+pygmytransactionobject.getTranAmt());
                
                //glpost.setAmount(pygmytransactionobject[i].getTranAmt()*rs.getInt("mult_by"));
                
                glpost.setAmount(pygmytransactionobject.getTranAmt());
                
                glpost.setCdind(pygmytransactionobject.getCDInd());
                glpost.setAccType(pygmytransactionobject.getAccType());
                glpost.setAccNo(String.valueOf(pygmytransactionobject.getAccNo()));
                System.out.println(pygmytransactionobject.getAccNo()+"   tran seq  GL............"+pygmytransactionobject.getTranSeq());
                glpost.setTrnSeq(pygmytransactionobject.getTranSeq());
                glpost.setTrnType(pygmytransactionobject.getTranType());
                glpost.setRefNo(pygmytransactionobject.getRefNo());
                glpost.setVtml(vetml);
                glpost.setVid(veuser);
                glpost.setVDate(curdatetime);            
                common.storeGLTransaction(glpost);
                
                System.out.println("2returning true.........");
            
            return true;
        }catch(SQLException sql){
            sql.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        catch (CreateException e) {
            e.printStackTrace();
        }
        finally{
            common = null;
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public boolean writePygmyTranGL(PygmyTransactionObject pygmytransactionobject[],String vetml,String veuser,String GLType,String curdatetime){
        Connection connection = null;
        try
        {
            System.out.println("Inside PygmyTranGL Writing..");
            common = commonLocalHome.create();
            connection = getConnection();
            
            Statement stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement stmt1=connection.createStatement();
            ResultSet rs1=null;
            int i=0;
            
                ResultSet rs=null;
                //if(pygmytransactionobject[i].getTranType().equalsIgnoreCase("R") || pygmytransactionobject[i].getTranType().equalsIgnoreCase("P"))
                //rs=stmt.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where gk.ac_type="+pygmytransactionobject[i].getAccType()+" and gk.code=1 and gp.ac_type=gk.ac_type and gp.trn_type='"+pygmytransactionobject[i].getTranType()+"' and gp.cr_dr='"+pygmytransactionobject[i].getCDInd()+"'");
                 
               /*  System.out.println("AccType :"+pygmytransactionobject[i].getAccType());
                 System.out.println("TrnType: "+pygmytransactionobject[i].getTranType());
                 System.out.println("Cr_Dr: "+pygmytransactionobject[i].getCDInd());
               */  
                //rs=stmt.executeQuery("select gk.gl_code,gk.gl_type,gp.mult_by from GLKeyParam gk,GLPost gp where gk.ac_type='"+pygmytransactionobject[i].getAccType()+"' and gk.code=1 and gp.ac_type=gk.ac_type and gp.gl_code=gk.gl_code and gp.trn_type='"+pygmytransactionobject[i].getTranType()+"' and gp.cr_dr='"+pygmytransactionobject[i].getCDInd()+"'");
                int j=0;               
                GLTransObject glpost[]=null;
                glpost=new GLTransObject[pygmytransactionobject.length];
             for( i=0,j=0;i<pygmytransactionobject.length && j<glpost.length;i++,j++) 
             {                         
                    if(GLType.startsWith("CASH"))
                         rs=stmt.executeQuery("select * from GLKeyParam where ac_type='"+pygmytransactionobject[i].getAccType()+"' and code=1");
                     else if(GLType.startsWith("PD"))
                         rs=stmt.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where gk.ac_type="+pygmytransactionobject[i].getAccType()+" and gk.code=1 and gp.ac_type=gk.ac_type and gp.trn_type='"+pygmytransactionobject[i].getTranType()+"' and gp.cr_dr='"+pygmytransactionobject[i].getCDInd()+"'");	
                	
                //else if(pygmytransactionobject[i][i].getTranType().equalsIgnoreCase("I"))
                //	rs=stmt.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where gk.ac_type="+pygmytransactionobject[i][i].getAccType()+" and gk.code=3 and gp.ac_type=gk.ac_type and gp.trn_type='"+pygmytransactionobject[i].getTranType()+"' and gp.cr_dr='"+pygmytransactionobject[i].getCDInd()+"'");
                System.out.println("1.......");
                rs.next();                
                    
                 glpost[j]=new GLTransObject();   
                glpost[j].setTrnDate(pygmytransactionobject[i].getTranDate());
                glpost[j].setGLType(rs.getString("gl_type"));
                glpost[j].setGLCode(rs.getString("gl_code"));
                glpost[j].setTrnMode(pygmytransactionobject[i].getTranMode()); //changed..
                System.out.println("TranAmnt :"+pygmytransactionobject[i].getTranAmt());
                
                //glpost.setAmount(pygmytransactionobject[i].getTranAmt()*rs.getInt("mult_by"));
                
                glpost[j].setAmount(pygmytransactionobject[i].getTranAmt());
                
                glpost[j].setCdind(pygmytransactionobject[i].getCDInd());
                glpost[j].setAccType(pygmytransactionobject[i].getAccType());
                glpost[j].setAccNo(String.valueOf(pygmytransactionobject[i].getAccNo()));
                System.out.println(pygmytransactionobject[i].getAccNo()+"   tran seq  GL............"+pygmytransactionobject[i].getTranSeq());
                glpost[j].setTrnSeq(pygmytransactionobject[i].getTranSeq());
                glpost[j].setTrnType(pygmytransactionobject[i].getTranType());
                glpost[j].setRefNo(pygmytransactionobject[i].getRefNo());
                glpost[j].setVtml(vetml);
                glpost[j].setVid(veuser);
                glpost[j].setVDate(curdatetime);
               // common.storeGLTransaction(glpost[j]);
            }
               
             if(common.storeGLTransaction(glpost)==1)
             {      
                System.out.println("2returning true.........");
            
             return true;
             }
             else
                 return false;
        }catch(SQLException sql){
            sql.printStackTrace();
            /*sessionContext.setRollbackOnly();*/
        }
        catch (CreateException e) {
            e.printStackTrace();
        }
        finally{
            common = null;
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    /**
     * To Verify the Remittance Details.
     *  Depends upon the Collection date & scroll Number Data will be dispalyed in Apllication.This operation has handeled in Client Side itself. 
     * @param agenttype
     * @param agentno
     * @param collection_date 
     * @param scrollno
     * @param vetml
     * @param veuser
     * @return '1' if Verification & GLPosting has successfully happened,Otherwise '-1'.
     * @throws DateFormatException
     * NOTE: 'D' entry to DayCash table.Bcoz while Depositing (Remitting) money 'C' entry has put in PygmyTransaction Table.
     */
    public int verifyRemittance(String agenttype,int agentno,String collection_date,int scrollno,String vetml,String veuser,String curdate,String curdatetime) throws DateFormatException
    {
        int i=0,r1=0,b=0;
        Connection connection = null;
        ResultSet rs1=null,rs2=null,rs3=null,rs_dailyremit=null;
        String v_main_cashier=null;
        int row=0;
        try
        {    
            
            CommonLocalHome commonLocalHome = (CommonLocalHome) new InitialContext().lookup("COMMONLOCALWEB");
            common = commonLocalHome.create();
            connection = getConnection();
            
            Statement stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement st=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement st1=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement st2=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement stmt_del=connection.createStatement();
            //rs1=stmt.executeQuery("select rec_type from Currency_Stock where tml_no=(select tml_code from Terminals where tml_type='M' and cur_date='"+getSysDate()+"')");
            rs1=stmt.executeQuery("select cash_close from DailyStatus where trn_dt='"+Validations.convertYMD(curdate)+"'");
            if(rs1.next())
                v_main_cashier=rs1.getString(1);
                System.out.println("main cashier==+++=="+v_main_cashier); 
                
            rs1=null;
            if(v_main_cashier.equalsIgnoreCase("f"))
            {
			//r1=stmt.executeUpdate("update DailyRemittance dr,PygmyMaster pm set dr.ve_tml='"+vetml+"',dr.ve_user='"+veuser+"',dr.ve_date='"+common.getDateTime()+"' where dr.coll_date='"+collection_date+"' and pm.agt_type='"+agenttype+"' and pm.agt_no="+agentno+" and dr.ac_type=pm.ac_type and dr.ac_no=pm.ac_no");
            //r1=stmt.executeUpdate("update DailyRemittance dr,PygmyMaster pm set dr.ve_tml='"+vetml+"',dr.ve_user='"+veuser+"',dr.ve_date='"+common.getDateTime()+"' where dr.coll_date='"+collection_date+"' and pm.agt_type='"+agenttype+"' and pm.agt_no="+agentno+" and dr.ref_no="+scrollno+" and dr.agt_type=pm.agt_type and dr.agt_no=pm.agt_no and dr.ac_type=pm.ac_type and dr.ac_no=pm.ac_no ");
            	r1=stmt.executeUpdate("update DailyRemittance dr,PygmyMaster pm set dr.ve_tml='"+vetml+"',dr.ve_user='"+veuser+"',dr.ve_date='"+curdatetime+"' where dr.coll_date='"+collection_date+"' and pm.agt_type='"+agenttype+"' and pm.agt_no="+agentno+" and dr.ref_no="+scrollno+" and  dr.ac_type=pm.ac_type and dr.ac_no=pm.ac_no");
                System.out.println("r1 resultset====="+r1);	
                if(r1==0)
                    throw new SQLException();

            //PreparedStatement ps=connection.prepareStatement("insert into PygmyTransaction select dr.* from PygmyMaster pm,AgentMaster am,DailyRemittance dr where  am.ac_type='"+agenttype+"'  and am.ac_no="+agentno+" and pm.agt_no=am.ac_no and pm.agt_type=am.ac_type and dr.ac_no=pm.ac_no and dr.coll_date='"+collection_date+"' and dr.ref_ind=1");// and dr.ve_tml is null");
            //System.out.println("Updated Value = "+ps.executeUpdate());
            
                PreparedStatement pstmt=connection.prepareStatement("insert into PygmyTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"); 
           
                rs1=st.executeQuery("select dr.* from DailyRemittance dr,PygmyMaster pm where coll_date='"+collection_date+"' and pm.agt_type='"+agenttype+"' and pm.agt_no="+agentno+" and dr.ac_type=pm.ac_type and dr.ac_no=pm.ac_no and ref_no="+scrollno+"" );
                rs1.last();
                int ref_no=rs1.getInt("ref_no");
                System.out.println("Ref_no==="+ref_no);
                rs1.beforeFirst();
                while(rs1.next())
                {
                    System.out.println("inside while++++++");
                //rs2=st1.executeQuery("select trn_seq,cl_bal from PygmyTransaction where ac_type='"+rs1.getString("ac_type")+"' and ac_no="+rs1.getInt("ac_no")+" order by trn_seq desc limit 1");
                    rs2=st1.executeQuery("select lst_trn_seq from PygmyMaster where ac_type='"+rs1.getString("ac_type")+"' and ac_no="+rs1.getInt("ac_no")+" and status='o' and ve_tml is not null");
                
                    if(rs2.next())
                    {
                        System.out.println("inside rs2 +++++++++"+rs1.getInt("ac_no"));                    
                        pstmt.setString(1,rs1.getString("ac_type"));
                        pstmt.setInt(2,rs1.getInt("ac_no"));
                    pstmt.setInt(3,((rs2.getInt("lst_trn_seq"))+1));
                    	pstmt.setString(4,rs1.getString("trn_date"));
                    pstmt.setString(5,rs1.getString("trn_type"));
                    pstmt.setDouble(6,rs1.getDouble("trn_amt"));
                    pstmt.setDouble(7,rs1.getDouble("amt_paid"));
                    pstmt.setString(8,rs1.getString("trn_mode"));
                    pstmt.setString(9,rs1.getString("trn_source"));
                    pstmt.setString(10,rs1.getString("cd_ind"));
                    pstmt.setDouble(11,rs1.getDouble("cl_bal"));
                    pstmt.setInt(12,rs1.getInt("ref_no"));
                    pstmt.setString(13,rs1.getString("trn_narr"));
                    pstmt.setString(14,rs1.getString("coll_date"));
                    pstmt.setString(15,rs1.getString("int_from"));
                    pstmt.setDouble(16,rs1.getDouble("prn_paid"));
                    pstmt.setDouble(17,rs1.getDouble("int_paid")); 		
                    pstmt.setString(18,rs1.getString("de_tml"));
                    pstmt.setString(19,rs1.getString("de_user"));
                    pstmt.setString(20,rs1.getString("de_date"));
                    pstmt.setString(21,null);
                    pstmt.setString(22,null);
                    pstmt.setString(23,null);
                    	pstmt.setInt(24,rs1.getInt("ref_ind"));
                    
                    	if(pstmt.executeUpdate()==1)
                    	{
                    	    System.out.println("after inserting into pygmy tran.....");
                        
                    	    if(stmt.executeUpdate("update PygmyMaster set lst_trn_seq="+((rs2.getInt("lst_trn_seq"))+1)+" where ac_type='"+rs1.getString("ac_type")+"' and ac_no="+rs1.getInt("ac_no"))==0)
                    	        throw new SQLException();
                    	}
                    	else
                    	    throw new SQLException();                    
                    }               
                }
                System.out.println("after pygmymaster updation");
                rs3=st2.executeQuery("select * from PygmyTransaction where trn_date='"+curdate+"' and ref_no="+ref_no+" ");
                while(rs3.next())
                {
                    System.out.println("inside rs3 while======");
                    i=stmt.executeUpdate("update PygmyTransaction pt,PygmyMaster pm set pt.ve_tml='"+vetml+"',pt.ve_user='"+veuser+"',pt.ve_date='"+curdate+"',pt.ref_ind=3 where pt.coll_date='"+collection_date+"' and pm.agt_type='"+agenttype+"' and pm.agt_no="+agentno+" and pt.ac_type=pm.ac_type and pt.ac_no=pm.ac_no");
                    System.out.println("i="+i);
                    if(i==0)
                        throw new SQLException();
            
            //ResultSet ca=stmt.executeQuery("select csh_amt from DayCash where trn_date='"+common.getSysDate()+"' and scroll_no="+scrollno);
                    ResultSet ca=stmt.executeQuery("select csh_amt from DayCash where  scroll_no="+scrollno+" and trn_date='"+curdate+"'"); //CHanged by Ris
            
                    ca.next();
            
                    ResultSet rs_bal=null;
                    Statement stmt_bal=connection.createStatement();
                    rs_bal=stmt_bal.executeQuery("select * from GLKeyParam where ac_type=1019001");
                    rs_bal.next();
            
            //Dr Entry to CASH BAL
            GLTransObject gl=new GLTransObject();
            //gl.setTrnDate(common.getSysDate());
            gl.setTrnDate(curdate);
            gl.setGLType(rs_bal.getString("gl_type"));
            gl.setGLCode(String.valueOf(rs_bal.getInt("gl_code")));
			gl.setTrnMode("C"); //changed.
			gl.setAmount(ca.getDouble(1));
			gl.setCdind("D");
			gl.setAccType(agenttype);
			gl.setAccNo(String.valueOf(agentno));
			gl.setTrnSeq(0);
			gl.setTrnType("R");
			gl.setRefNo(scrollno);
			gl.setVtml(vetml);
			gl.setVid(veuser);
			gl.setVDate(curdatetime);
			
			common.storeGLTransaction(gl);
            
            /*CashObject co=new CashObject();
            co.setGLRefCode("1019001");
            co.setTrndate(common.getSysDate());
            co.setAmount(ca.getDouble(1));
            
            co.setCdind("D");  //Debit Entry into DayCash Table.
            co.setAcctype(agenttype);
            co.setAccno(String.valueOf(agentno));
            co.setTrnseq(0);
            co.setTrntype("R"); //Reciept to Cashier
            co.setScrollno(scrollno);
            co.uv.setVerTml(vetml);
            co.uv.setVerId(veuser);
            
            
            CashLocalHome cashLocalHome = (CashLocalHome)sessionContext.lookup("CASHLOCAL");
            CashLocal cashLocal = cashLocalHome.create(veuser);
            cashLocal.verifyDayCash(co);
            
			//Cr entries to Pygmy Accounts GL 
            ResultSet rset=st.executeQuery("select pt.trn_mode,pt.ac_type,pt.ac_no,pt.trn_date,pt.trn_seq,pt.trn_amt,pt.trn_source from PygmyMaster pm,PygmyTransaction pt where pm.agt_type='"+agenttype+"' and pm.agt_no="+agentno+" and pt.ac_no=pm.ac_no and pt.coll_date='"+collection_date+"' and pt.ref_no="+scrollno);
            rset.last();
            System.out.println(" the row length is ////////// ************ +++++"+rset.getRow());
            PygyTransactionObject pt=new PygmyTransactionObject();
            rset.beforeFirst();
            int r=0;
            while(rset.next())
            {
                //pt=new PygmyTransactionObject();
                pt.setTranDate(rset.getString("pt.trn_date"));
                pt.setTranSource(rset.getString("pt.trn_source"));
                pt.setTranAmt(rset.getDouble("pt.trn_amt"));
                pt.setCDInd("C");
                pt.setAccType(rset.getString("pt.ac_type"));
                pt.setAccNo(rset.getInt("pt.ac_no"));
                pt.setTranSeq(rset.getInt("pt.trn_seq"));
                pt.setTranType("R");
                pt.setRefNo(scrollno);
                pt.setTranMode(rset.getString("pt.trn_mode"));
                r++;
              
                writePygmyGL(pt,vetml,veuser,"PD");
            }*/
            
            
             	ResultSet rset=st.executeQuery("select pt.trn_mode,pt.ac_type,pt.ac_no,pt.trn_date,pt.trn_seq,pt.trn_amt,pt.trn_source from PygmyMaster pm,PygmyTransaction pt where pm.agt_type='"+agenttype+"' and pm.agt_no="+agentno+" and pt.ac_no=pm.ac_no and pt.coll_date='"+collection_date+"' and pt.ref_no="+scrollno);
			    
             	if(rset.last())
             	{   
             	    System.out.println(" the row length is ////////// ************ +++++"+rset.getRow());	          
             	    row=rset.getRow();
             	}
             	int r=0;
             	PygmyTransactionObject[] pt=null;
	          
             	if(row==0)
	              throw new SQLException();
	          else
	          {
	              rset.beforeFirst();
	              
	              pt=new PygmyTransactionObject[row];
	              
		            while(rset.next())
		            {
		                System.out.println("inside rset while+++++++");
		                pt[r]=new PygmyTransactionObject();
		                pt[r].setTranDate(rset.getString("pt.trn_date"));
		                pt[r].setTranSource(rset.getString("pt.trn_source"));
		                pt[r].setTranAmt(rset.getDouble("pt.trn_amt"));
		                pt[r].setCDInd("C");
		                pt[r].setAccType(rset.getString("pt.ac_type"));
		                pt[r].setAccNo(rset.getInt("pt.ac_no"));
		                pt[r].setTranSeq(rset.getInt("pt.trn_seq"));
		                pt[r].setTranType("R");
		                pt[r].setRefNo(scrollno);
		                pt[r].setTranMode(rset.getString("pt.trn_mode"));
		                r++;
		            }
		            
		            writePygmyTranGL(pt,vetml,veuser,"PD",curdatetime);
	          	}
	          
            
            //find a total Remittance amount(scroll amt), Agent's personal SB Account & Joint SB Acc No
             	ResultSet rs=stmt.executeQuery("select sum(trn_amt),am.p_ac_type,am.p_ac_no,am.jt_ac_type,am.jt_ac_no from AgentMaster am,PygmyTransaction pt,PygmyMaster pm where ref_no="+scrollno+" and coll_date='"+collection_date+"' and pm.agt_type='"+agenttype+"' and pm.agt_no="+agentno+" and am.ac_type='"+agenttype+"' and am.ac_no="+agentno+" and pt.ac_type=pm.ac_type and pt.ac_no=pm.ac_no group by ref_no");
            rs.next();
            double amt=rs.getDouble(1);
            String pactype=rs.getString(2);
            int pacno=rs.getInt(3);
            String jtactype=rs.getString(4);
            int jtacno=rs.getInt(5);
            common = commonLocalHome.create();
            rs=stmt.executeQuery("select comm_rate,sec_dep_rate from CommissionRate where agt_type='"+agenttype+"' and '"+Validations.convertYMD(curdate)+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)) and "+amt+" between min_amt and max_amt");
            
            
            rs.next();
            double commrate=rs.getDouble(1);
            double secrate=rs.getDouble(2);
            //double commission=Math.round((amt*commrate)/100);
            //double secdep=Math.round((commission*secrate)/100);
            double commission=(amt*commrate)/100;
            double secdep=(commission*secrate)/100;
            double securitydep=Math.round(commission-secdep);
            System.out.println("Commission:"+commission);
            System.out.println("Security:"+secdep);
            if(commission!=0){				
                AccountTransObject am=new AccountTransObject();
                System.out.println("pyg actype====="+pactype);
                am.setAccType(pactype);
                am.setAccNo(pacno);
                am.setTransDate(curdate);
                am.setTransType("R");
                if(jtacno!=0)
                    am.setTransAmount(Math.round(commission-secdep));
                else
                    am.setTransAmount(Math.round(commission));
                am.setTransMode("T");
                am.setTransSource(vetml);
                am.setCdInd("C");
                am.setTransNarr(agenttype+" "+agentno);
                am.setRef_No(scrollno);
                am.uv.setUserTml(vetml);
                am.uv.setUserId(veuser);
                am.uv.setUserDate(curdatetime);
                am.uv.setVerTml(vetml);
                am.uv.setVerId(veuser);
                am.uv.setVerDate(curdatetime);
                common.storeAccountTransaction(am);
                if(jtacno!=0&&secdep!=0)
                {
                    System.out.println("join acc type+++++="+jtactype);
                    am.setAccType(jtactype);
                    am.setAccNo(jtacno);
                    am.setTransType("R");
                    am.setTransAmount(commission-securitydep);
                    am.setTransMode("T");
                    am.setTransSource(vetml);
                    am.setCdInd("C");
                    am.setTransNarr(agenttype+" "+agentno);
                    am.setRef_No(scrollno);
                    am.uv.setUserTml(vetml);
                    am.uv.setUserId(veuser);
                    am.uv.setVerTml(vetml);
                    am.uv.setVerId(veuser);
                    am.uv.setVerDate(curdatetime);
                    am.setGLRefCode(1002001);
                    common.storeAccountTransaction(am);						
                }
            	}
            
            //Loss GL for Commission Paid to Agent
            //ResultSet rsgl=stmt.executeQuery("select gk.gl_type,gk.gl_code,mult_by from GLKeyParam gk,GLPost gp where gk.ac_type="+agenttype+" and gk.code=1 and gp.ac_type=gk.ac_type and gp.trn_type='P' and gp.cr_dr='D'");
            ResultSet rsgl=stmt.executeQuery("select * from GLKeyParam where ac_type="+agenttype+" and code=1");
            rsgl.next();
            GLTransObject glpost=new GLTransObject();
            glpost.setTrnDate(curdate);
            //glpost.setGLType("GL");
            glpost.setGLType(rsgl.getString("gl_type"));
            glpost.setGLCode(rsgl.getString("gl_code"));
            glpost.setTrnMode("T"); //changed..
           //glpost.setAmount(commission*rsgl.getInt("mult_by"));
            glpost.setAmount(commission);
            glpost.setCdind("D");
            glpost.setAccType(agenttype);
            glpost.setAccNo(String.valueOf(agentno));
            glpost.setTrnSeq(0);
            glpost.setTrnType("P");
            glpost.setRefNo(scrollno);
            glpost.setVtml(vetml);
            glpost.setVid(veuser);
            glpost.setVDate(curdatetime);
            common.storeGLTransaction(glpost);
            
            rs_dailyremit=stmt_del.executeQuery("select * from DailyRemittance where ve_date is not null");
            if(rs_dailyremit.next())
                //stmt_del.executeUpdate("delete from DailyRemittance where ve_date is not null");
            //stmt.executeUpdate("delete from DailyRemittance where coll_date='"+collection_date+"' and ref_no="+scrollno+"");
            stmt.executeUpdate("update DayCash set ve_user='"+veuser+"', ve_date='"+curdatetime+"', ve_tml='"+vetml+"' where scroll_no="+scrollno+" and ac_no="+agentno+" and trn_date='"+curdate+"'");     
            	return 1;           
                }  
            } //=> end of checking Main Cashier opened or Not..    
        }catch(SQLException sql){sessionContext.setRollbackOnly(); sql.printStackTrace();
            return -1;
        } catch (CreateException e) {sessionContext.setRollbackOnly();e.printStackTrace();
        }catch (Exception exep){sessionContext.setRollbackOnly(); exep.printStackTrace();}
        finally{try {
            common = null;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }}
        return -1;
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
    
    private String[] getYearEnd(String today_date,String br_location) throws SQLException, CreateException, DateFormatException
    {
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
			rs=stmt1.executeQuery("select * from QtrDefinition where qtr_defn='T' and month="+mm+" ");
			if(rs.next())
			{
				last_date=Validations.lastDayOfMonth(today_date);
				if(!(last_date.equals(today_date))){// last date not equals to today date check for holiday master for last date
					rs=stmt1.executeQuery("select br_code from BranchMaster where br_name like '"+br_location+"' ");
					rs.next(); 
					while(flag){
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
    /**
     * This will delete the existing Agent.
     * @param agenttype - modulecode of Agent
     * @param agentno
     * @return '1' if it is successfully deleted the Agent Otherwise '-1'.
     */
    public int deleteAgent(String agenttype,int agentno) {
        Connection conn = null;
        //int result=0;
        System.out.println("Deleting Agent..");
        try {
            conn = getConnection();
            
            Statement stmt = conn.createStatement();
            stmt.addBatch("delete from SignatureInstruction where ac_type='"+agenttype+"' and ac_no="+agentno);
            stmt.addBatch("delete from AgentMaster where ac_type='"+agenttype+"' and ac_no="+agentno);
            //stmt.addBatch("update Modules set lst_acc_no=lst_acc_no-1 where modulecode='"+agenttype+"'");
            int[] a=stmt.executeBatch();
            System.out.println("Batch Length : "+a.length);
            if(a.length==2)
                return 1;
            
        } catch (SQLException e) {
            e.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally {
            if(conn != null)
                try {
                    conn.close();
                } catch (SQLException e1) {}
                //   
        }
        return -1;
    }
       
        /**author shubha
     * this method selects all records from commission rate table
     * @param //todaydate
     * @return array of records
     * @throws RemoteException
     */
    public PygmyRateObject[] commissionRatesChange()
    {
       Connection con=null;
       ResultSet rs=null;
       Statement stmt=null;
       PygmyRateObject[] commission_obj=null;
       int i=0,row=0;
       String agenttype,fromdate,todate;
       double commissionrate=0.0;
       try
       {
           con=getConnection();
           stmt=con.createStatement();
           rs=stmt.executeQuery("select agt_type,date_fr,date_to,min_amt,max_amt,comm_rate,sec_dep_rate,de_user,de_tml,de_date from CommissionRate where agt_type like'1013%' order by date_to asc");
           if(rs.next())
           {
               rs.last();
               row=rs.getRow();
               rs.beforeFirst();
               commission_obj=new PygmyRateObject[row];
               while(rs.next())
               {
                   commission_obj[i]=new PygmyRateObject();
                   agenttype=rs.getString("agt_type");
                   fromdate=rs.getString("date_fr");
                   todate=rs.getString("date_to");
                   commissionrate=rs.getDouble("comm_rate");
                   commission_obj[i].setAgentType(agenttype);
                   System.out.println("agent"+i+"="+commission_obj[i].getAgentType());
                   commission_obj[i].setFromDate(fromdate);
                   commission_obj[i].setToDate(todate);
                   commission_obj[i].setMinAmt(rs.getDouble("min_amt"));
                   commission_obj[i].setMaxAmt(rs.getDouble("max_amt"));
                   commission_obj[i].setCommissionRate(commissionrate);
                   commission_obj[i].setSecurityDepRate(rs.getDouble("sec_dep_rate"));
                   commission_obj[i].setDeUser(rs.getString("de_user"));
                   commission_obj[i].setDeTml(rs.getString("de_tml"));
                   commission_obj[i].setDeDate(rs.getString("de_date"));
                   i++;
              }
        
           }
       }//end of try
       catch(SQLException sql){sql.printStackTrace();} 
		
	finally{
		try{
			con.close();
		}catch(SQLException sql){sql.printStackTrace();}
	}
	System.out.println("comm obj lngth="+commission_obj.length);
	return  commission_obj;
    }
    /**author shubha
     *@param comm_object
     * @param tml
     * @param uid
     * @return returns 0 if entered date is less than previous dates,1 if entered date is between two dates and date_to is !=futuredate
     * returns 2 if insertion is not possible,on insertion returns 3 
     */
    public int insertCommissionRt(PygmyRateObject comm_object,String tml,String uid,String curdate,String curdatetime)
    {
       	    ResultSet rs=null,rs1=null,rs2=null,rs3=null;
    	    Statement stmt=null,stmt1=null,stmt2=null,stmt_up=null,stmt3=null;
    	    Connection con=null;
    	    String fromdate_1=null;
    	    String futuredate="31/12/9999";
    	    String currentdate;
    	    int ret=0,insert_value=0;
    	    System.out.println("The agent btype in bean is============>"+comm_object.getAgentType());
    	    System.out.println("The from date is in bean"+comm_object.getFromDate());
    	    System.out.println("The curdate is"+comm_object.getToDate());
    	    try{
    	    con=getConnection();
    	    stmt=con.createStatement();
    	    stmt1=con.createStatement();
    	    stmt2=con.createStatement();
    	    stmt_up=con.createStatement();
    	    stmt3=con.createStatement();
    	    common = commonLocalHome.create();
    	    currentdate=common.getDateTime();
    	    /*StringTokenizer st=new StringTokenizer(currentdate," ");
    	    String date=st.nextToken();
    	    String time=st.nextToken();
    	   */
    	    rs=stmt.executeQuery("select * from CommissionRate where agt_type="+comm_object.getAgentType()+" and  concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))>='"+comm_object.getFromDate()+"' and '"+comm_object.getFromDate()+"'<'"+curdate+"'");
    	    if(rs.next()){
    	        System.out.println(" i am in first");
    	    	ret=0;
    	    	return ret;
    	    }
    	   
    	   else 
    	   { 
    		 rs1=stmt1.executeQuery("select * from CommissionRate where agt_type="+comm_object.getAgentType()+" and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<'"+Validations.convertYMD(comm_object.getFromDate())+"' and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(comm_object.getFromDate())+"' and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))!='"+Validations.convertYMD(futuredate)+"'");
	         rs3=stmt3.executeQuery("select * from CommissionRate where agt_type="+comm_object.getAgentType()+" and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))<'"+Validations.convertYMD(comm_object.getToDate())+"' and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))>='"+Validations.convertYMD(comm_object.getToDate())+"' and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))!='"+Validations.convertYMD(futuredate)+"'");
    	         
	         	if(rs1.next()||rs3.next())
    	        {
    	            System.out.println(" i am in second11");
    		    	ret=1;
    		    	return ret;
    		    	    		    	    		    	    		
    	        }
    	       else
    	       {
    	    	   System.out.println("m in 2nd else");
    	            fromdate_1=Validations.addDays(comm_object.getFromDate(),-1);
    		        ret=stmt_up.executeUpdate("update CommissionRate set date_to='"+fromdate_1+"',de_user='"+uid+"', de_tml='"+tml+"' , de_date='"+curdatetime+"' where agt_type="+comm_object.getAgentType()+" and date_fr<'"+comm_object.getFromDate()+"' and date_to>='"+comm_object.getFromDate()+"' and date_to='"+futuredate+"'");
    		        System.out.println("update return="+ret);
    		        insert_value=insertCommissionRow(comm_object,tml,uid);
    		        if(insert_value==1)
    		           ret=2;
    		        else
    		            ret=3;
    		        System.out.println("i am in third!!!!");
    		        return ret;
    	        }
    	   }
    	    
    	    
    	     } catch(Exception e){e.printStackTrace();} 
    		finally{
    			try{
    				con.close();
    			}catch(SQLException sql){sql.printStackTrace();}
    		}
    		System.out.println("return value="+ret);
    	    return ret;
    	}
    /**author shubha
     * insert a valid record into commissionrate 
     * @param comm_object
     * @param tml
     * @param uid
     * @return returns 1 if cannot insert
     */
    public int insertCommissionRow(PygmyRateObject comm_object,String tml,String uid)
    {
    	System.out.println("M in insertCommissionRow======");
        String db_todate,fromdate,db_fromdate,db_agttype;
        double db_commrate=0.0,db_secdeprate=0.0;
        Statement stmt=null,stmt1=null;
        PreparedStatement ps;
        ResultSet rs=null;
        int ret=0;
     
        Connection connection = null ;
        try{
            common = commonLocalHome.create();
            connection = getConnection();
            stmt=connection.createStatement();
            stmt1=connection.createStatement();
            rs=stmt1.executeQuery("select * from CommissionRate where agt_type='"+comm_object.getAgentType()+"' and concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1))='"+comm_object.getFromDate()+"'");
            if(rs.next())
                ret=1;
           else
           {
            ps=connection.prepareStatement("insert into CommissionRate values(?,?,?,?,?,?,?,?,?,'"+common.getDateTime()+"')");
            
            ps.setString(1,String.valueOf(comm_object.getAgentType())); //modulecode
            ps.setString(2,String.valueOf(comm_object.getFromDate()));//from date..
            ps.setString(3,String.valueOf(comm_object.getToDate())); // to date
            ps.setDouble(4,comm_object.getMinAmt());  // minimum amt
            ps.setDouble(5,comm_object.getMaxAmt()); // maximum amt
            ps.setDouble(6,comm_object.getCommissionRate()); //commission rate
            ps.setDouble(7,comm_object.getSecurityDepRate());  // security deposit rate
            ps.setString(8,tml);     //terminal
            ps.setString(9,uid);     //de uid
            
            ps.executeUpdate();
            ret=0;
           }
        }
        catch(SQLException e){
            e.printStackTrace();
            sessionContext.setRollbackOnly();
        } catch (CreateException e) {
            e.printStackTrace();
        }
     
        finally{
            try {
                common = null;
                connection.close();				
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (EJBException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
    
   /**
    * author shubha
    * this method checks whether there are any transactions for the passed date
    * @param currentdate
    * @return returns 1 if there are transactions else returns 0
    */
    public int PygmyTransactedAccOnDt(String currentdate)
    {
        Connection con=null;
        ResultSet rs=null;
        Statement stmt=null;
        int ret=0;
        try{
            con=getConnection();
            stmt=con.createStatement();
            rs=stmt.executeQuery("select * from PygmyTransaction where concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))='"+Validations.convertYMD(currentdate)+"'");
            if(rs.next())
                ret=1;
            else
                ret=0;
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                common = null;
                con.close();				
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (EJBException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
    /**
     * author shubha
     * @param comm_object--values that has to be updated to commissionrate tbl
     * @param value=1 then its for updating,else deletion
     * @return 1-->sussessful updation,2-->successful deletion
     */
    public int CommissionRtUpdate(PygmyRateObject comm_object,String curdate,int value)
    {
        Statement stmt_update=null,stmt=null;
        PreparedStatement ps;
        ResultSet rs=null;
        int ret=0;
     
        Connection connection = null ;
        try{
            common = commonLocalHome.create();
            connection = getConnection();
            stmt_update=connection.createStatement();
            stmt=connection.createStatement();
            System.out.println("agent type in bean==="+comm_object.getAgentType());
            System.out.println("commission rt update from date==="+comm_object.getFromDate());
            System.out.println("commission rt update de date==="+comm_object.getDeDate());
            if(value==1)
            {    
             //stmt_update.executeUpdate("delete from CommissionRate where agt_type='"+comm_object.getAgentType()+"' and date_fr='"+comm_object.getFromDate()+"' and  de_date='"+comm_object.getDeDate()+"'");
            	/*rs=stmt.executeQuery("select * from CommissionRate where agt_type="+comm_object.getAgentType()+" and '"+Validations.convertYMD(comm_object.getToDate())+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
            	if(rs.next())
            	{
                  ret=-1; 		
            	}
            	else
           	{*/
            	stmt_update.executeUpdate("delete from CommissionRate where agt_type='"+comm_object.getAgentType()+"' and date_fr='"+comm_object.getFromDate()+"'");
            ps=connection.prepareStatement("insert into CommissionRate values(?,?,?,?,?,?,?,?,?,'"+curdate+"')");
            ps.setString(1,String.valueOf(comm_object.getAgentType())); //modulecode
            ps.setString(2,String.valueOf(comm_object.getFromDate()));//from date..
            ps.setString(3,String.valueOf(comm_object.getToDate())); // to date
            ps.setDouble(4,comm_object.getMinAmt());  // minimum amt
            ps.setDouble(5,comm_object.getMaxAmt()); // maximum amt
            ps.setDouble(6,comm_object.getCommissionRate()); //commission rate
            ps.setDouble(7,comm_object.getSecurityDepRate());  // security deposit rate
            ps.setString(8,comm_object.getDeTml());     //terminal
            ps.setString(9,comm_object.getDeUser());     //de uid
            
            ps.executeUpdate();
            ret=1;
          // }
          }
            else{
            	//changed by Mohsin on 18.11.2009
              //  stmt_update.executeUpdate("delete from CommissionRate where agt_type='"+comm_object.getAgentType()+"' and date_fr='"+comm_object.getFromDate()+"' and  de_date='"+comm_object.getDeDate()+"'");
            	  stmt_update.executeUpdate("delete from CommissionRate where agt_type='"+comm_object.getAgentType()+"' and date_fr='"+comm_object.getFromDate()+"' and  date_to='"+comm_object.getToDate()+"'");
                ret=2;
               }
        }   
        catch(SQLException e){
            e.printStackTrace();
            sessionContext.setRollbackOnly();
        } catch (CreateException e) {
            e.printStackTrace();
        }
     
        finally{
            try {
                common = null;
                connection.close();				
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (EJBException e) {
                e.printStackTrace();
            }
        }
           return ret;
       }
 /**
  * shubha
  * pygmymaster updation
  * @param amo
  * @return
  */   
     public int updatePygmyMaster(PygmyMasterObject amo){
    	Connection conn=null;
    	PreparedStatement pst=null;
    	Statement st=null,st1=null,st2=null;
    	ResultSet rs=null,rs1=null;
    	String v_main_cashier = null;
    	int r=0;
    	try{
    	      conn=getConnection();
    	      st=conn.createStatement();
              if(st.executeUpdate("insert into PygmyMasterLog select * from PygmyMaster where ac_type='"+amo.getAccType()+"' and ac_no="+amo.getAccNo()+"")!=0)
                r=1; //inserted record
              else
                r=-1; //query failed
              
    	      if(r==1){
    	      
                  pst=conn.prepareStatement("update PygmyMaster set cid=?,addr_type=?,agt_type=?,agt_no=?,open_date=?,lst_int_dt=?,ln_avld=?,ln_ac_type=?,ln_ac_no=?,pay_mode=?,pay_ac_type=?,pay_ac_no=?,close_date=?,status=? where ac_type='"+amo.getAccType()+"' and ac_no="+amo.getAccNo()+"");
    	      	pst.setInt(1,amo.getCid());
    	      	pst.setInt(2,amo.getAddrType());
    	      	pst.setString(3,amo.getAgentType());
    	      	pst.setInt(4,Integer.parseInt(amo.getAgentNo()));
    	      	pst.setString(5,amo.getAccOpenDate());
    	      	pst.setString(6,amo.getLastIntrDate());
    	      	pst.setString(7,amo.getLnAvailed());
    	      	pst.setString(8,amo.getLnAccType());
    	      	pst.setInt(9,amo.getLnAccNo());
    	      	pst.setString(10,amo.getPayMode());
    	      	pst.setString(11,amo.getPayAccType());
    	      	pst.setInt(12,amo.getPayAccNo());
    	      	pst.setString(13,amo.getAccCloseDate());
                pst.setString(14,amo.getAccStatus());
    	      
    	      	if(pst.executeUpdate()!=0)
    	      		r=2; //Both Insertion & Updation Successfully done
    	      	else
    	      		r=-2; //AgentMasterLog Entry happend, But Updation in AgentMaster not Happend!
    	      
    	      } 
    	     /* if(r!=0)
    	        return r;
    	      else
    	      	return r;*/
    	      
     	}catch(SQLException se){
     		sessionContext.setRollbackOnly();
     		se.printStackTrace();}
    	finally{
    		try{
    			conn.close();
    		}catch(SQLException se){se.printStackTrace();}
    	}
    	return r; //Error!
    	
    }
     /**
      * shubha
      * insertion and updation of new pygmy rates in pygmyadmin
      * @param po
      * @param tml
      * @param user
      * @param datetime
      * @param update_delete_indicator
      * @return
      */
     public int insert_updatePygmyRate(PygmyRateObject po[],String tml,String user,String datetime,int update_delete_indicator)
     {
         Connection conn=null;
         ResultSet rs1=null,rs2=null,rs3=null,rs4=null,rs5=null;
         Statement stmt_select=null,stmt_update=null,stmt_delete=null,stmt_previous,stmt=null;
         PreparedStatement[] pstmt =new PreparedStatement[po.length];
         String futuredate="31/12/9999",date=null,time=null,previousdate=null;
         //StringTokenizer st=null;
         int ret=0,j=0,k=0;
         String[] date_time=null;
         try
         {
            conn=getConnection();
            stmt_select=conn.createStatement();
            stmt_update=conn.createStatement();
            stmt_delete=conn.createStatement();
            stmt_previous=conn.createStatement();
            stmt=conn.createStatement();
            /*st=new StringTokenizer(datetime," ");
            date=st.nextToken();
            time=st.nextToken();*/
            System.out.println("update del indicator==="+update_delete_indicator);
            System.out.println("po obj lngth==="+po.length);
            System.out.println("PO ACC TYPE=="+po[0].getACType());
            System.out.println("FROM DATE=="+po[0].getFromDate());
            
            if(update_delete_indicator==0)
            {
        	  rs1=stmt_select.executeQuery("select * from PygmyRate where ac_type="+po[0].getACType()+" and concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1))<'"+Validations.convertYMD(po[0].getFromDate())+"' and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1))>='"+Validations.convertYMD(po[0].getFromDate())+"' and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1))!='"+Validations.convertYMD(futuredate)+"'");
              rs5=stmt.executeQuery("select * from PygmyRate where ac_type="+po[0].getACType()+" and concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1))='"+Validations.convertYMD(po[0].getFromDate())+"' and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1))='"+Validations.convertYMD(po[0].getToDate())+"'");
              if(rs1.next())
              {
                  ret=1;
                  return ret;   
              }
              else if(rs5.next())
              {
           		 ret=5;
           		return ret;
              }
              else
              {
               for(int i=0;i<po.length;i++)
               {  
                       pstmt[i]=conn.prepareStatement("insert into PygmyRate values(?,?,?,?,?,?,?,?,?)");
                                            
                       pstmt[i].setString(1,po[i].getACType());
                       pstmt[i].setString(2,po[i].getFromDate());
                       pstmt[i].setString(3,po[i].getToDate());
                       pstmt[i].setInt(4,po[i].getPeriodFrom());
                       pstmt[i].setInt(5,po[i].getPeriodTo());
                       pstmt[i].setDouble(6,po[i].getIntRate());
                       pstmt[i].setString(7,tml);
                       pstmt[i].setString(8,user);
                       pstmt[i].setString(9,datetime);
                      if(pstmt[i].executeUpdate()==0)
                       throw new SQLException("PygmyRate insertion problem");
                     else 
                       ret=2;
               
            }
               for(j=0;j<po.length;j++)
             {
                   
               rs2=stmt_select.executeQuery("select * from PygmyRate where ac_type="+po[j].getACType()+" and concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1))<'"+Validations.convertYMD(po[j].getFromDate())+"' and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1))='"+Validations.convertYMD(futuredate)+"'");
               rs4=stmt_previous.executeQuery("select * from PygmyRate where ac_type="+po[j].getACType()+" and concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1))<'"+Validations.convertYMD(po[j].getFromDate())+"' and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1))<'"+Validations.convertYMD(po[j].getFromDate())+"'");      
               if(rs2.next()||rs4.next())
               {                   
                  previousdate=Validations.addDays(po[j].getFromDate(),-1);
                  stmt_update.executeUpdate("update PygmyRate set to_date='"+previousdate+"' where concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1))<'"+Validations.convertYMD(po[j].getFromDate())+"' and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1))='"+Validations.convertYMD(futuredate)+"' or concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1))<'"+Validations.convertYMD(po[j].getFromDate())+"'");
               }
             }
            }
           }
            else if(update_delete_indicator==1)
            {
                System.out.println("update acctype==="+po[j].getACType());
                System.out.println("update from date=="+po[j].getFromDate());
                
                
                rs3=stmt_select.executeQuery("select * from PygmyRate where ac_type="+po[j].getACType()+" and concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1))='"+Validations.convertYMD(po[j].getFromDate())+"'");
                if(rs3.next())
               {
                    stmt_delete.executeUpdate("delete from PygmyRate where ac_type="+po[j].getACType()+" and concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1))='"+Validations.convertYMD(po[j].getFromDate())+"'");
                 for(j=0;j<po.length;j++)
                {
                   System.out.println("po length inside for=="+j);
                                               
                       pstmt[j]=conn.prepareStatement("insert into PygmyRate values(?,?,?,?,?,?,?,?,?)");
                       pstmt[j].setString(1,po[j].getACType());
                       pstmt[j].setString(2,po[j].getFromDate());
                       pstmt[j].setString(3,po[j].getToDate());
                       pstmt[j].setInt(4,po[j].getPeriodFrom());
                       pstmt[j].setInt(5,po[j].getPeriodTo());
                       pstmt[j].setDouble(6,po[j].getIntRate());
                       pstmt[j].setString(7,tml);
                       pstmt[j].setString(8,user);
                       pstmt[j].setString(9,datetime);
                      if(pstmt[j].executeUpdate()==0)
                       throw new SQLException("PygmyRate insertion problem");
                     else 
                       ret=3;
                }                       
               }
                else 
                    ret=-3;
              }
            
            else if(update_delete_indicator==2)
            {
                System.out.println("inside deletion");
                for(k=0;k<po.length;k++)
                    if(stmt_select.executeUpdate("delete from PygmyRate where ac_type="+po[k].getACType()+" and concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1))='"+Validations.convertYMD(po[k].getFromDate())+"' and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1))='"+Validations.convertYMD(po[k].getToDate())+"' and prd_fr="+po[k].getPeriodFrom()+" and prd_to="+po[k].getPeriodTo()+"")>0)
                        ret=4;
            }
         }
         catch(SQLException se)
         {
             se.printStackTrace();
             sessionContext.setRollbackOnly();
         }
         catch(Exception e){e.printStackTrace();}
         finally
         {
             try{
                 conn.close();
             }catch(Exception e){e.printStackTrace();}
         }
       return ret;   
     }
     /**
      * shubha
      * copying glparamkey values of previous from actype to newly introduced actype 
      * @param from_ac_type
      * @param to_ac_type
      * @param tml
      * @param user
      * @param date_time
      * @return
      */
     public GLObject[] copyGLdata(String from_ac_type,String to_ac_type,String tml,String user,String date_time)
     {
         Connection conn=null;
         int numrows=0,i=0,ret=0;
         GLObject[] gl_object=null;
         try
         {
             conn=getConnection();
             ResultSet rs_glkeyparam=null;
             ResultSet rs_select=null;
             Statement stmt_glkeyparam=null;
             Statement stmt_glinsert=null;
             stmt_glkeyparam=conn.createStatement();
             stmt_glinsert=conn.createStatement();
          
             rs_glkeyparam=stmt_glkeyparam.executeQuery("select * from GLKeyParam where ac_type="+from_ac_type+"");
            
             if(rs_glkeyparam.next())
             {
                 rs_glkeyparam.last();
                 numrows=rs_glkeyparam.getRow();
                 rs_glkeyparam.beforeFirst();
             }
               while(rs_glkeyparam.next())
               {
                 if(stmt_glinsert.executeUpdate("insert into GLKeyParam(ac_type,code,key_desc,gl_code,gl_type,de_tml,de_user,de_date) values ("+to_ac_type+","+rs_glkeyparam.getInt("code")+",'"+rs_glkeyparam.getString("key_desc")+"',"+rs_glkeyparam.getInt("gl_code")+",'"+rs_glkeyparam.getString("gl_type")+"','"+tml+"','"+user+"','"+date_time+"')")>0)
                 {
                     System.out.println("insert and select/////////////");
                     rs_select=stmt_glinsert.executeQuery("select gp.*,moduleabbr from GLKeyParam gp,Modules mo where gp.ac_type="+to_ac_type+" and gp.gl_type=mo.modulecode");
                 }
                 else
                     throw new SQLException("Problem in copying GLdata");
               }
               if(rs_select.next())
               {
                   rs_select.last();
                   gl_object=new GLObject[rs_select.getRow()];
                   rs_select.beforeFirst();
                   while(rs_select.next())
                   {
                       gl_object[i]=new GLObject();
                       gl_object[i].setAcType(String.valueOf(rs_select.getInt("ac_type")));
                       gl_object[i].setCode(rs_select.getInt("code"));
                       gl_object[i].setKeyDesc(rs_select.getString("key_desc"));
                       gl_object[i].setGlCode(rs_select.getString("gl_code"));
                       gl_object[i].setGLTypeAbbr(rs_select.getString("moduleabbr"));
                       i++;
                   }
               }
               
             }
         catch(SQLException se){
             sessionContext.setRollbackOnly();
             se.printStackTrace();}
         catch(Exception ex){ex.printStackTrace();}
         finally
         {
             try{
                 conn.close();
             }
             catch(Exception ex){ex.printStackTrace();}
         }
         System.out.println("gl_obj lngth==="+gl_object.length);
         return gl_object;
     }
     
     /**
      * shubha
      * copying Glpost values of previous from_actype to newly introduced to_actype
      * @param from_actype
      * @param to_actype
      * @param tml
      * @param user
      * @param datetime
      * @return
      */
     public GLObject[] copyGLPost(String from_actype,String to_actype,String tml,String user,String datetime)
     {
         Connection conn=null;
         ResultSet rs_glpost=null;
         ResultSet rs_select=null;
         Statement stmt_glpost=null;
         Statement stmt_insert=null;
         Statement stmt_select=null;
         GLObject[] gl_object=null;
         int numrows=0,i=0;
         try
         {
             conn=getConnection();
             stmt_glpost=conn.createStatement();
             stmt_insert=conn.createStatement();
             stmt_select=conn.createStatement();
             rs_glpost=stmt_glpost.executeQuery("select * from GLPost where ac_type="+from_actype+"");
             if(rs_glpost.next())
             {
                 rs_glpost.last();
                 numrows=rs_glpost.getRow();
                 rs_glpost.beforeFirst();
             }
            while(rs_glpost.next())
            {
                 stmt_insert.executeUpdate("insert into GLPost(ac_type,gl_code,trn_type,cr_dr,mult_by,de_tml,de_user,de_date) values ("+to_actype+","+rs_glpost.getInt("gl_code")+",'"+rs_glpost.getString("trn_type")+"','"+rs_glpost.getString("cr_dr")+"',"+rs_glpost.getInt("mult_by")+",'"+tml+"','"+user+"','"+datetime+"')");
            }
            rs_select=stmt_select.executeQuery("select * from GLPost where ac_type="+to_actype+"");
            if(rs_select.next())
            {
                rs_select.last();
                gl_object=new GLObject[rs_select.getRow()];
                rs_select.beforeFirst();
            }
            while(rs_select.next())
            {
                gl_object[i]=new GLObject();
                gl_object[i].setAcType(rs_select.getString("ac_type"));
                gl_object[i].setGlCode(rs_select.getString("gl_code"));
                gl_object[i].setTrnType(rs_select.getString("trn_type"));
                gl_object[i].setCrDr(rs_select.getString("cr_dr"));
                gl_object[i].setMultBy(rs_select.getInt("mult_by"));
                i++;
            }
         }
         catch(SQLException se){se.printStackTrace();}
         catch(Exception ex){ex.printStackTrace();}
         finally
         {
          try{
              conn.close();
             }
          catch(Exception ex){ex.printStackTrace();}
         }
         System.out.println("gl object length==="+gl_object.length);
         return gl_object;
     }
     
     public int checkCid(String cid)
     {
        Connection conn=null;
        ResultSet rs=null,rs_cid=null;
        Statement stmt=null,stmt_cid=null;
        String ac_no=null;
        int ret=0;
        try
        {
            conn=getConnection();
            stmt=conn.createStatement();
            stmt_cid=conn.createStatement();
            System.out.println("cid value==="+cid);
            rs_cid=stmt_cid.executeQuery("select * from CustomerMaster where cid='"+cid+"'");
            if(rs_cid.next())
            {
              ret=1;
            }
             else
             {
                 System.out.println("-2 ret value");
                 ret=-1;
             }
         }                   
        
        catch(SQLException se){se.printStackTrace();}
        catch(Exception ex){ex.printStackTrace();}
        finally
        {
            try{
                conn.close();
            }catch(Exception ex){ex.printStackTrace();}
        }
        return ret;
     }
 
     public GLObject[] getGLKeyParam(ModuleObject[] mo,int value)
 	{
 	    Connection conn=null;
 	    ResultSet rs=null,rs1=null;
 	    GLObject[] gl_obj=null;
 	    int i=0,j=0,numrows=0;
 	    Statement stmt=null,stmt1=null;
 	    try
 	   {
 	        conn=getConnection();
 	        stmt=conn.createStatement();
 	        stmt1=conn.createStatement();
 	        if(value==1)
 	    {
 	        rs1=stmt1.executeQuery("select * from GLKeyParam where ac_type='1013001'");
 	        if(rs1.next())
 	       {
 	          rs1.last();  
 	          numrows=rs1.getRow();
 	       }
 	       gl_obj=new GLObject[mo.length*numrows];
 	        for(j=0;j<mo.length;j++)
 	      {
 	        rs=stmt.executeQuery("select gp.*,mo.moduleabbr from GLKeyParam gp,Modules mo where gp.ac_type='"+mo[j].getModuleCode()+"' and mo.modulecode=gp.ac_type");
 	        if(rs.next())
 	       {
 	           rs.beforeFirst();  	       
 	        while(rs.next())
 	        {
 	            gl_obj[j]=new GLObject();
 	            gl_obj[j].setAcType(rs.getString("moduleabbr"));
 	            gl_obj[j].setCode(rs.getInt("code"));
 	            gl_obj[j].setKeyDesc(rs.getString("key_desc"));
 	            gl_obj[j].setGlCode(rs.getString("gl_code"));
 	            gl_obj[j].setGlType(rs.getString("gl_type"));
 	         }
 	        }
 	      }
 	    }
 	        else if(value==2)
 	        {
 	            rs1=stmt1.executeQuery("select * from GLKeyParam where ac_type='1006001'");
 	            if(rs1.next())
 	           {
 	            rs1.last();
 	            numrows=rs1.getRow();
 	           }
 	           gl_obj=new GLObject[mo.length*numrows];
 	           int k=0;
 	           System.out.println("mo length==="+mo.length);
 	           System.out.println("gl object length=="+gl_obj.length);
 	           for(j=0;j<mo.length;j++)
 	           {
 	               rs=stmt.executeQuery("select gp.*,mo.moduleabbr from GLKeyParam gp,Modules mo where gp.ac_type="+mo[j].getModuleCode()+" and mo.modulecode=gp.ac_type");
 	               if(rs.next())
 	               {
 	                  rs.beforeFirst();
 	                  while(rs.next())
 	                  {
 	                      System.out.println("kkkk value==="+k);
 	                     gl_obj[k]=new GLObject();
 	     	            gl_obj[k].setAcType(rs.getString("moduleabbr"));
 	     	            gl_obj[k].setCode(rs.getInt("code"));
 	     	            gl_obj[k].setKeyDesc(rs.getString("key_desc"));
 	     	            gl_obj[k].setGlCode(rs.getString("gl_code"));
 	     	            gl_obj[k].setGlType(rs.getString("gl_type"));
 	     	            k++;
 	     	           
 	                  }
 	               }
 	           }
 	               
 	        }
 	        
 	   }        
 	    catch(SQLException se){se.printStackTrace();}
 	    catch(Exception ex){ex.printStackTrace();}
 	    finally
 	    {
 	        try{
 	            conn.close();
 	        }catch(Exception ex){ex.printStackTrace();}
 	    }
 	    return gl_obj;
 	}
     
     public GLObject[] getGLPost(ModuleObject[] mo)
     {
        Connection conn=null;
        ResultSet rs=null,rs1=null;
        Statement stmt=null,stmt1=null;
        GLObject[] gl_obj=null;
        int i=0,j=0,numrows=0;
        try{
            conn=getConnection();
            stmt=conn.createStatement();
            stmt1=conn.createStatement();
            
            System.out.println("mo length==="+mo.length);
           
            rs1=stmt1.executeQuery("select * from GLPost where ac_type='1006001'");
            if(rs1.next())
           {
               rs1.last(); 
               numrows=rs1.getRow();
           }
            gl_obj=new GLObject[mo.length*numrows];
            for(i=0;i<mo.length;i++)
           {
                System.out.println("mo lenth=="+i);
                System.out.println("mo[1]=="+mo[i].getModuleCode());
               
            rs=stmt.executeQuery("select gp.*,mo.moduleabbr from GLPost gp,Modules mo where gp.ac_type='"+mo[i].getModuleCode()+"' and mo.modulecode=gp.ac_type");
            System.out.println("after rs");
            if(rs.next())
            {
                rs.beforeFirst();
                while(rs.next())
                {
                    System.out.println("inside while j val==="+j);
                   gl_obj[j]=new GLObject();
                   gl_obj[j].setAcType(rs.getString("moduleabbr"));
                   gl_obj[j].setGlCode(rs.getString("gl_code"));
                   gl_obj[j].setTrnType(rs.getString("trn_type"));
                   gl_obj[j].setCrDr(rs.getString("cr_dr"));
                   gl_obj[j].setMultBy(rs.getInt("mult_by"));
                   j++;
                }
            }
        }
     }
        catch(SQLException se){se.printStackTrace();}
        catch(Exception ex){ex.printStackTrace();}
        finally{
            try{
                conn.close();
            }catch(Exception ex){ex.printStackTrace();}
        }
        return gl_obj;
     }
     
     public int checkAgentUsage(String ac_type)
     {
         Connection conn=null;
         ResultSet rs=null;
         Statement stmt=null;
         int ret=0;
         try
         {
             conn=getConnection();
             stmt=conn.createStatement();
             rs=stmt.executeQuery("select * from AgentMaster where ac_type="+ac_type+"");
             if(rs.next())
                 ret=1;
             else
                 ret=0;
         }
         catch(SQLException se){se.printStackTrace();}
         catch(Exception ex){ex.printStackTrace();}
         finally{
             try{
                 conn.close();
             }catch(Exception ex){ex.printStackTrace();}
         }
         return ret;
     }
     
     public GLObject[] getGltranType(ModuleObject[] mo)
     {
       Connection conn=null;
       GLObject[] gl_object=null;
       ResultSet rs=null;
       Statement stmt=null;
       int j=0;
       System.out.println("mo length==="+mo.length);
       try
      {
           conn=getConnection();
          stmt=conn.createStatement();
          for(int i=0;i<mo.length;i++)
        {
          	System.out.println("modulecode==="+mo[i].getModuleCode());
          	System.out.println("i valuee===="+i);
            rs=stmt.executeQuery("select * from GLTransactionType where ac_type="+mo[i].getModuleCode()+"");
            if(rs.next() && rs!=null)
          {
              rs.last();
              gl_object=new GLObject[rs.getRow()];
              System.out.println("numrows==="+rs.getRow());
              rs.beforeFirst();
          }
          while(rs.next())
          {
          	  System.out.println("value of j==inside while=="+j);
              gl_object[j]=new GLObject();
              gl_object[j].setAcType(rs.getString("ac_type"));
              gl_object[j].setGlType(rs.getString("gl_type"));
              gl_object[j].setGlCode(rs.getString("gl_code"));
              gl_object[j].setTrnType(rs.getString("trn_type"));
              gl_object[j].setTrnDesc(rs.getString("trn_desc"));
              j++;
          }
                  
       }
     }
       catch(SQLException se){se.printStackTrace();}
       catch(Exception ex){ex.printStackTrace();}
       finally
       {
           try{
               conn.close();
           }catch(Exception ex){ex.printStackTrace();}
       }
       //System.out.println("gl_obj lngth in gltrntyp=="+gl_object.length);
       return gl_object;
     }
    
     public ModuleAdminObject getCommissionRate(String agent_type,String date)
     {
     	Connection con=null;
     	ResultSet rs_commrate=null;
     	Statement stmt_commrate=null;
     	ModuleAdminObject admin_comm=null;
     	int i=0;
     	try
		{
     		con=getConnection();
     		stmt_commrate=con.createStatement();
     		rs_commrate=stmt_commrate.executeQuery("select * from CommissionRate where agt_type="+agent_type+" and date_fr<'"+Validations.convertYMD(date)+"' and date_to>'"+Validations.convertYMD(date)+"'");
     		if(rs_commrate.next())
     		{
     			rs_commrate.last();
     			rs_commrate.beforeFirst();
     		}
     		while(rs_commrate.next())
     		{
     			admin_comm=new ModuleAdminObject();
     			admin_comm.pygmy_obj.setAgentType(rs_commrate.getString("agt_type"));
     			admin_comm.pygmy_obj.setMaxAmount(rs_commrate.getDouble("max_amt"));
     			admin_comm.pygmy_obj.setMinAmount(rs_commrate.getDouble("min_amt"));
     			admin_comm.pygmy_obj.setCommisionRate(rs_commrate.getDouble("comm_rate"));
     			admin_comm.pygmy_obj.setSecDepositRate(rs_commrate.getDouble("sec_dep_rate"));
     			//System.out.println("security dep rate==="+admin_comm.getSecurityDepRate());
     			i++;
     		}
		}
     	catch(SQLException se){se.printStackTrace();}
     	catch(Exception ex){ex.printStackTrace();}
     	finally
		{
     		try
			{con.close();}
     		catch(SQLException se){se.printStackTrace();}
		}
     	return admin_comm;
     }
     
     public int checkJointAccno(int cid,String ac_type,String acno)
     {
         Connection conn=null;
         ResultSet rs_acno=null;
         Statement stmt_acno=null;
         int ret=0;
         try
         {
             conn=getConnection();
             stmt_acno=conn.createStatement();
             rs_acno=stmt_acno.executeQuery("select * from AccountMaster where cid="+cid+" and ac_type='"+ac_type+"' and ac_no='"+acno+"' ");
             if(rs_acno.next())
                 ret=1;
             else
                 ret=-1;
         }
         catch(Exception ex){ex.printStackTrace();}
         finally
         {
             try{ conn.close(); }
             catch(Exception ex){ex.printStackTrace();}
         }
         return ret;
     }
     
     public int storeAgentChange(PygmyMasterObject[] pyg_obj,String frm_agtty,int frm_agtno,String to_agtty,int to_agtno,String de_tml,String de_user,String date,int indicator)throws SQLException
     {
         Connection con=null;
         PreparedStatement[] pstmt=new PreparedStatement[pyg_obj.length];
         Statement stmt=null,stmt_delete=null;
         String ac_type=null;
         int result=0; 

         try
         {
            
             con=getConnection();
             stmt=con.createStatement();
             stmt_delete=con.createStatement();
                         
             System.out.println("master obj length=="+pyg_obj.length);
             stmt_delete.executeUpdate("delete from AgentChange where ref_ind=3 and ve_tml is not null");
             for(int i=0;i<pyg_obj.length;i++)
             {
                 //ac_type=commonRemote.getModulecode(pyg_obj[i].getAccType());
                // System.out.println("accccccctype testing=="+pyg_obj[i].getAccType());
                 
                 if(indicator==0)
                     stmt_delete.executeUpdate("delete from AgentChange where ac_type='"+pyg_obj[i].getAccType()+"' and ac_no="+pyg_obj[i].getAccNo()+"");
                 else if(indicator==1)
                     stmt_delete.executeUpdate("delete from AgentChange where frm_agt_type='"+frm_agtty+"' and frm_agt_no="+frm_agtno+" and to_agt_type='"+to_agtty+"' and to_agt_no="+to_agtno+"");


                 pstmt[i]=con.prepareStatement("insert into AgentChange values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
             
                 pstmt[i].setString(1,frm_agtty);
                 pstmt[i].setInt(2,frm_agtno);
                 pstmt[i].setString(3,to_agtty);
                 pstmt[i].setInt(4,to_agtno);
                 pstmt[i].setString(5,pyg_obj[i].getAccType());
                 System.out.println("pyg_obj[i].getAccType()------->>>>"+pyg_obj[i].getAccType());
                 pstmt[i].setString(6,null);
                 pstmt[i].setInt(7,pyg_obj[i].getAccNo());
                 System.out.println("pyg_obj[i].getAccNo()==========>"+pyg_obj[i].getAccNo());
                 pstmt[i].setString(8,de_tml);
                 pstmt[i].setString(9,de_user);
                 pstmt[i].setString(10,date);
                 pstmt[i].setString(11,null);
                 pstmt[i].setString(12,null);
                 pstmt[i].setString(13,null);
                 pstmt[i].setInt(14,1);


             }
             for(int i=0;i<pstmt.length;i++)
             {
                 if(pstmt[i].executeUpdate()==0)
                 {
                     result=0;
                     throw new SQLException("Agent Change insertion problem");
                 }
                 else
                 {
                     stmt.executeUpdate("update AgentChange agtch set pd_modulecode=(select modulecode from Modules mo where agtch.ac_type=mo.moduleabbr)");
                     


                     result=1;
                 }
             }
         }
         catch(SQLException se){
             se.printStackTrace();
             sessionContext.setRollbackOnly();
             throw se;
             
         }
         catch(Exception ex){
             ex.printStackTrace();
         }
         finally{
             try{
                 con.close();
             }
             catch(SQLException e){
                 e.printStackTrace();
             }
         }
         
         return result;
     }
     
     public PygmyMasterObject[] getUnverifiedAgtChange(String frm_agttype,int frm_agtno,String to_agttype,int to_agtno)
     {
    	 System.out.println("M inside getUnverifiedAgtChange method");
        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        PygmyMasterObject[] pyg_obj=null;
        int i=0;
        try
        {
            conn=getConnection();
            stmt=conn.createStatement();
            rs=stmt.executeQuery("select agtch.ac_type,agtch.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,agtch.ref_ind from AgentChange agtch,PygmyMaster pm,CustomerMaster cm where agtch.frm_agt_type='"+frm_agttype+"' and agtch.frm_agt_no="+frm_agtno+" and agtch.to_agt_type='"+to_agttype+"' and agtch.to_agt_no="+to_agtno+" and agtch.ac_no=pm.ac_no and cm.cid=pm.cid and agtch.ref_ind in(1,2)");
            if(rs.next())
            {
                rs.last();
                pyg_obj=new PygmyMasterObject[rs.getRow()];
                rs.beforeFirst();
            }
            while(rs.next())
            {
                pyg_obj[i]=new PygmyMasterObject();
                pyg_obj[i].setAccType(rs.getString("ac_type"));
                pyg_obj[i].setAccNo(rs.getInt("ac_no"));
                pyg_obj[i].setName(rs.getString("name"));
                pyg_obj[i].setRef_ind(rs.getInt("ref_ind"));
                i++;
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            try{
                conn.close();
            }
            catch(SQLException es){
                es.printStackTrace();
            }
        }
        return pyg_obj;
     }
     
     public int VerifyAgentChange(PygmyMasterObject[] pyg_obj,String frm_agtty,int frm_agtno,String to_agtty,int to_agtno,String ve_tml,String ve_user,String date)
     {
         Connection conn=null;
         Statement stmt_verify=null,stmt_master=null,stmt_log=null;
         ResultSet rs_update=null;
         int result=0;
         System.out.println("VerifyAgentChange=============>>>>>");
         try
         {
             conn=getConnection();
             stmt_verify=conn.createStatement();
             stmt_master=conn.createStatement();
             stmt_log=conn.createStatement();
             if(stmt_verify.executeUpdate("update AgentChange set ve_tml='"+ve_tml+"',ve_user='"+ve_user+"',ve_date='"+date+"',ref_ind=3 where frm_agt_type='"+frm_agtty+"' and frm_agt_no="+frm_agtno+" and to_agt_type='"+to_agtty+"' and to_agt_no="+to_agtno+" ")==0)
             {
                 result=0;
                 throw new SQLException();
             }
             else
             {
                 rs_update=stmt_master.executeQuery("select * from AgentChange where frm_agt_type='"+frm_agtty+"' and frm_agt_no="+frm_agtno+" and to_agt_type='"+to_agtty+"' and to_agt_no="+to_agtno+" and ref_ind=3");
                 while(rs_update.next())
                 {
                     stmt_log.executeUpdate("insert into PygmyMasterLog select * from PygmyMaster where agt_type='"+frm_agtty+"' and agt_no="+frm_agtno+" and ac_type='"+rs_update.getString("pd_modulecode")+"' and ac_no="+rs_update.getInt("ac_no")+"");
                     stmt_master.executeUpdate("update PygmyMaster set agt_type='"+to_agtty+"',agt_no="+to_agtno+" where agt_type='"+frm_agtty+"' and agt_no="+frm_agtno+" and ac_type='"+rs_update.getString("pd_modulecode")+"' and ac_no="+rs_update.getInt("ac_no")+"");
                     result=1;
                 }
                 
             }
             
         }
         catch(SQLException se){
             se.printStackTrace();
             sessionContext.setRollbackOnly();
         }
         catch(Exception ex){
             ex.printStackTrace();
         }
         finally{
             try{
                 conn.close();
             }
             catch(SQLException es){
                 es.printStackTrace();
             }
         }
         return result;
     }
     public void UpdateRefIndAgtChange(String frm_agtty,int frm_agtno,String to_agtty,int to_agtno)
     {
         Connection conn=null;
         Statement stmt_update=null;
         try
         {
             conn=getConnection();
             stmt_update=conn.createStatement();
             stmt_update.executeUpdate("update AgentChange set ref_ind=2 where frm_agt_type='"+frm_agtty+"' and frm_agt_no="+frm_agtno+" and to_agt_type='"+to_agtty+"' and to_agt_no="+to_agtno+" and ref_ind=1 and ve_tml is null");
         }
         catch(SQLException se){
             se.printStackTrace();
         }
         catch(Exception ex){
             ex.printStackTrace();
         }
         finally{
             try{
                 conn.close();
             }
             catch(SQLException es){
                 es.printStackTrace();
             }
         }
     }
     
     public AgentMasterObject getAgtDetailsForMasterUpdation(String agttype,int agtno,String date)
     {
         Statement stmt;
         ResultSet rs;
         AgentMasterObject agentmasterobject=new AgentMasterObject();
         AccountObject accountobject_personal=null;
         AccountObject accountobject_joint=null;
         System.out.println("Inside getAgentDetails....");
         System.out.println("agenttype"+agttype);
         System.out.println("agentno"+agtno);
         Connection connection = null;
         int jt_ac_no=0,personal_accno=0,joint_accno=0;
        
         String jt_ac_ty=null,personal_acctype=null,joint_acctype=null;
         try
         {
        	 Context ctx=getInitialContext();
          	Object obj=ctx.lookup("COMMONWEB");
          	commonServer.CommonHome home=(commonServer.CommonHome)obj;
          	CommonRemote commonremote=home.create();
            
             connection = getConnection();
             stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             rs=stmt.executeQuery("select mo.moduleabbr,agm.*,cm.fname,concat_ws(' ',cm.fname,cm.mname,cm.lname) as agent_name,mo2.moduleabbr,mo3.moduleabbr from Modules mo,Modules mo2,Modules mo3,AgentMaster agm,CustomerMaster cm where ac_type='"+agttype+"' and ac_no="+agtno+" and mo.modulecode=ac_type and mo2.modulecode=p_ac_type and mo3.modulecode=jt_ac_type and cm.cid=agm.cid");
            
             if(rs.next())
             {
                 personal_acctype=rs.getString("p_ac_type");
                 joint_acctype=rs.getString("jt_ac_type");
                 personal_accno=rs.getInt("p_ac_no");
        
                 joint_accno=rs.getInt("jt_ac_no");
                 accountobject_personal=commonremote.getAccount(null,personal_acctype,personal_accno,date);
                 accountobject_joint=commonremote.getAccount(null,joint_acctype,joint_accno,date);
               
                 agentmasterobject.setAgentType(rs.getString("mo.moduleabbr"));
                 agentmasterobject.setAgentNo(rs.getInt("ac_no"));
                 agentmasterobject.setCid(rs.getInt("cid"));
                 System.out.println("agent name======"+rs.getString("agent_name"));
                 agentmasterobject.setName(rs.getString("agent_name"));
                 agentmasterobject.setFname(rs.getString("fname"));
                 agentmasterobject.setJointHldrs(rs.getInt("no_jt_hldr"));
                 agentmasterobject.setAppointDate(rs.getString("appt_date"));
                 agentmasterobject.setAccType(rs.getString("mo2.moduleabbr"));
                 agentmasterobject.setAccNo(rs.getInt("p_ac_no"));
                 jt_ac_ty=rs.getString("jt_ac_type");
                 agentmasterobject.setJointAccType(rs.getString("mo3.moduleabbr"));
                 jt_ac_no=rs.getInt("jt_ac_no");
                 agentmasterobject.setJointAccNo(jt_ac_no);
                 /*if(String.valueOf(rs.getInt("sec_cid"))!=null)
                     agentmasterobject.setSecurityCID(rs.getInt("sec_cid"));
                 else
                     agentmasterobject.setSecurityCID(0);*/
                 agentmasterobject.setCloseDate(rs.getString("close_date"));
                 agentmasterobject.setCloseInd(rs.getInt("close_ind"));
                 System.out.println("close ind in agent det=="+agentmasterobject.getCloseInd());
                // agentmasterobject.setSecurityName(rs.getString("security_name"));
                 agentmasterobject.setVeTml(rs.getString("ve_tml"));
                 agentmasterobject.setRef_Ind(rs.getInt("ref_ind"));
                 System.out.println("JT Ac Ty: "+agentmasterobject.getJointAccType());
                 System.out.println("JT Ac No: "+agentmasterobject.getJointAccNo());
                 
                 if(jt_ac_ty!=null && jt_ac_no>0)
                 {      
                 
                    System.out.println("JT Ac Ty @@: "+jt_ac_ty);
                    System.out.println("JT Ac No @@: "+jt_ac_no);
                     
                 rs.close();
                 rs = stmt.executeQuery("select cid from AccountMaster where ac_type='"+jt_ac_ty+"' and ac_no="+jt_ac_no+"");
                 int cid;
                 if(rs.next());
                     cid=rs.getInt(1);
                 System.out.println("JT CID: "+cid);
                 agentmasterobject.setJointCid(cid);
                 System.out.println("JT CID: "+agentmasterobject.getJointCid());
                 } // end of finding jt CID..
                 
                 System.out.println(" common remote:"+commonremote);
                 System.out.println(" get sign:"+commonremote.getSignatureDetails(agtno,agttype));
                 agentmasterobject.setSigObj(commonremote.getSignatureDetails(agtno,agttype));
                               
                 rs.close();
                 
                 rs=stmt.executeQuery("select * from DayCash where ac_type='"+agttype+"'  and ac_no="+agtno+" and attached='F' and trn_date='"+date+"'");
                 
                 if(rs.next())
                 {
                     System.out.println("scroll no = "+rs.getInt("scroll_no"));
                     System.out.println("scroll amt = "+rs.getDouble("csh_amt"));
                     
                    agentmasterobject.setScroll_no(rs.getInt("scroll_no"));
                    agentmasterobject.setScroll_amoount(rs.getDouble("csh_amt"));
                 }
             
           }       
             else
             {
                 System.out.println("Setting -1 to Agent No..");
                agentmasterobject.setAgentNo(-1);
                rs.close();
             }
                 
         }catch(Exception e){e.printStackTrace();}
         finally{
             try {
                 //common = null;
                 connection.close();
             } catch (SQLException e1) {
                 e1.printStackTrace();
             }
         }
         System.out.println("agt mas obj="+agentmasterobject);
         System.out.println("agt mas scrlno=="+agentmasterobject.getScroll_no());
         System.out.println("closeind=="+agentmasterobject.getCloseInd());
         return agentmasterobject;
     }
    
     public String getAgentName(String actype,int acno)throws RemoteException{
    	 AgentMasterObject agentmasterobject=new AgentMasterObject();
    	 Connection connection = null;
    	 Statement stmt=null;
    	 ResultSet rs=null;
    	 String name="";
        
        
       try
         {
         	Context ctx=getInitialContext();
         	Object obj=ctx.lookup("COMMONWEB");
         	commonServer.CommonHome home=(commonServer.CommonHome)obj;
         	CommonRemote commonremote=home.create();
             connection = getConnection();
             
             System.out.println("-----actype===="+actype);
             System.out.println("-----acno===="+acno);
             
             stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             rs=stmt.executeQuery("select am.ac_type,am.ac_no,cm.cid,concat_ws(' ',cm.fname,cm.mname,cm.lname) as agent_name from AgentMaster am,CustomerMaster cm where ac_type='"+actype+"' and ac_no="+acno+" and cm.cid=am.cid" );
             if(rs.next()){
            	 agentmasterobject.setAgentType(rs.getString("ac_type"));
                 agentmasterobject.setAgentNo(rs.getInt("ac_no"));
                 agentmasterobject.setCid(rs.getInt("cid"));
                 System.out.println("agent name======"+rs.getString("agent_name"));
                 agentmasterobject.setName(rs.getString("agent_name"));
                 name=rs.getString("agent_name");
             }
         }catch(Exception e){e.printStackTrace();}
         finally{
             try {
                 //common = null;
                 connection.close();
             } catch (SQLException e1) {
                 e1.printStackTrace();
             }
         }   
    	 return name;
     }
     
     public PygmyMasterObject[] getPygmyLedgerReportNew(String from_date,String to_date,int start_accno)
     {
     	System.out.println("m in getPygmyLedgerReportNew===");
     	 Statement stmt;
          ResultSet rs;
          PygmyMasterObject pm[]=null;
          Connection connection = null;
          try {
              connection = getConnection();
              stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
              
              
                 // System.out.println("Typesss 2      "+type);
              System.out.println("Account no"+start_accno);
                  System.out.println("frm dt: "+from_date);
                  System.out.println("to dt: "+to_date);
                  System.out.println("frm dt:  "+Validations.convertYMD(from_date));
                  System.out.println("to dt: "+Validations.convertYMD(to_date));
                  //rs=stmt.executeQuery("select PygmyMaster.*,name,relation,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,concat_ws(cm1.fname,cm1.mname,cm1.lname)as agentname from PygmyMaster,NomineeMaster,CustomerMaster cm,CustomerMaster cm1,AgentMaster am where am.cid=cm1.cid and PygmyMaster.cid=cm.cid and NomineeMaster.reg_no=PygmyMaster.nom_no and PygmyMaster.ac_no between "+start_accno+" and "+end_accno+" and PygmyMaster.agt_no=am.ac_no and PygmyMaster.agt_type=am.ac_type and concat(right(PygmyMaster.open_date,4),'-',mid(PygmyMaster.open_date,locate('/',PygmyMaster.open_date)+1,(locate('/',PygmyMaster.open_date,4)-locate('/',PygmyMaster.open_date)-1)),'-',left(PygmyMaster.open_date,locate('/',PygmyMaster.open_date)-1)) between '"+from_date+"' and '"+to_date+"' and PygmyMaster.close_date is null ");
                  //rs=stmt.executeQuery("select pm.*,name,relation,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,concat_ws(' ',cm1.fname,cm1.mname,cm1.lname)as agentname from PygmyMaster pm,NomineeMaster nm,CustomerMaster cm,CustomerMaster cm1,AgentMaster am where  pm.ac_no between "+start_accno+" and "+end_accno+" and am.ac_no=pm.agt_no and am.ac_type=pm.agt_type and cm1.cid=am.cid and cm.cid=pm.cid and nm.reg_no=pm.nom_no   and concat(right(pm.open_date,4),'-',mid(pm.open_date,locate('/',pm.open_date)+1,(locate('/',pm.open_date,4)-locate('/',pm.open_date)-1)),'-',left(pm.open_date,locate('/',pm.open_date)-1)) between '"+from_date+"' and '"+to_date+"' and pm.close_date is null");
                //  rs=stmt.executeQuery("select pg.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,concat_ws(' ',cm1.fname,cm1.mname,cm1.lname)as agentname from PygmyMaster pg,CustomerMaster cm,CustomerMaster cm1,AgentMaster am where pg.cid=cm.cid and am.cid=cm1.cid and pg.ac_no="+start_accno+" and pg.agt_no=am.ac_no and pg.agt_type=am.ac_type and ((concat(right(pg.open_date,4),'-',mid(pg.open_date,locate('/',pg.open_date)+1,(locate('/',pg.open_date,4)-locate('/',pg.open_date)-1)),'-',left(pg.open_date,locate('/',open_date)-1)) between '"+from_date+"' and '"+to_date+"') or (concat(right(pg.close_date,4),'-',mid(pg.close_date,locate('/',pg.close_date)+1,(locate('/',pg.close_date,4)-locate('/',pg.close_date)-1)),'-',left(pg.close_date,locate('/',pg.close_date)-1)) between '"+from_date+"' and '"+to_date+"'))");
                  rs=stmt.executeQuery("select pg.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)as pygname,concat_ws(' ',cm1.fname,cm1.mname,cm1.lname)as agentname from PygmyMaster pg,CustomerMaster cm,CustomerMaster cm1,AgentMaster am where pg.cid=cm.cid and am.cid=cm1.cid and pg.ac_no="+start_accno+" and pg.agt_no=am.ac_no and pg.agt_type=am.ac_type");
                  
                  rs.last();
                  System.out.println("rs.getRow()== "+rs.getRow());
                  pm=new PygmyMasterObject[rs.getRow()];
                  rs.beforeFirst();
                  int i=0;
                  while(rs.next())
                  {
                      pm[i]=new PygmyMasterObject();
                      pm[i].setAccNo(rs.getInt("ac_no"));
                      pm[i].setName(rs.getString("pygname"));
                      pm[i].setAgentNo(rs.getString("agt_no"));
                      pm[i].setAgentName(rs.getString("agentname"));
                      pm[i].setAccOpenDate(rs.getString("open_date"));
                      pm[i].setLnAvailed(rs.getString("ln_avld"));
                      pm[i].setLastIntrDate(rs.getString("lst_int_dt"));
                      pm[i].setPayMode(rs.getString("pay_mode"));
                      pm[i].setNomineeNo(rs.getInt("nom_no"));
                      pm[i].setPayAccType(rs.getString("pay_ac_type"));
                      pm[i].setPayAccNo(rs.getInt("pay_ac_no"));
                      i++;
                  }
                  
                  rs.close();
                  
                  for(int r=0; r<pm.length; r++)
                  {
                  	if(pm[r].getNomineeNo()!=0)
                  	{
                  		rs=stmt.executeQuery("select name,relation from NomineeMaster where reg_no="+pm[r].getNomineeNo()+"");
                  	    rs.next();
                  	    pm[r].setNom_details(rs.getString(1)+" & "+rs.getString(2));
                  	    System.out.println("Nom Acc No : "+pm[r].getAccNo());  
                  	    System.out.println("Nom Nm : "+pm[r].getNom_details());
   
                  	    
                  	}else
                  		pm[r].setNom_details("");
                  }
                  
              
              					
          }catch(SQLException e){e.printStackTrace();}
          finally{
              try {
                  connection.close();
              } catch (SQLException e1) {
                  e1.printStackTrace();
              }
          }
          System.out.println("pm====m in bean==="+pm.length);
          return pm;
              
     }
     
     //added by sunitha on 25/11/2009
     public PygmyTransactionObject[] getPygmyLedgerTransactionNew(String from_date,String to_date,int start_accno)
     {
     	System.out.println("m in getPygmyLedgerTransactionNew===");
     	 Statement stmt;
          ResultSet rs;
          PygmyTransactionObject[] pt=null;
          boolean next=false;
          Connection connection = null;
          try {
              connection = getConnection();
              System.out.println("frm dt: "+from_date);
              System.out.println("to dt: "+to_date);
              System.out.println("start accno: "+start_accno);
              
                  stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                  rs=stmt.executeQuery("select * from PygmyTransaction where ac_no="+start_accno+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(from_date)+"' and '"+Validations.convertYMD(to_date)+"' order by ac_no,trn_seq");
                  try
                  {
                      if(rs.next())
                      {
                          rs.getString(1);
                          next=true;
                      }
                  }catch(Exception e){next=false;}
                  
                  if(next)
                  {
                      
                      rs.last();
                      pt=new PygmyTransactionObject[rs.getRow()];
                      rs.beforeFirst();
                      
                      int i=0;
                      while(rs.next())
                      {
                          pt[i]=new PygmyTransactionObject();
                          pt[i].setAccNo(rs.getInt("ac_no"));
                          pt[i].setTranDate(rs.getString("trn_date"));
                          pt[i].setTranAmt(rs.getDouble("trn_amt"));
                          pt[i].setTranNarration(rs.getString("trn_narr"));
                          pt[i].setCDInd(rs.getString("cd_ind"));
                          if(pt[i].getCDInd().equalsIgnoreCase("D")){
                          	pt[i].setDebAmt(rs.getDouble("trn_amt"));
                          }else if(pt[i].getCDInd().equalsIgnoreCase("C")){
                          	pt[i].setCrAmt(rs.getDouble("trn_amt"));
                          }
                          pt[i].setCloseBal(rs.getDouble("cl_bal"));
                          
                          i++;
                      }
                      
                      rs.close();
                  }
                  else
                  {
                      pt=new PygmyTransactionObject[1];
                      pt[0]=new PygmyTransactionObject();
                      pt[0].setAccNo(-1);
                      rs.close();
                  }
                  
              			
          }catch(Exception e){e.printStackTrace();}
          finally{
              try {
                  connection.close();
              } catch (SQLException e1) {
                  e1.printStackTrace();
              }
          }
          System.out.println("pt length++++bean++="+pt.length);
          return pt;
              
     }
   }