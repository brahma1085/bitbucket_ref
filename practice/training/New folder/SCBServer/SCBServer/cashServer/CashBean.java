
package cashServer;

/**
 * Created on Mar 12, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import loanServer.LoanLocal;
import loanServer.LoanLocalHome;
import masterObject.loans.LoanTransactionObject;
import masterObject.cashier.CashObject;
import masterObject.cashier.CurrencyStockObject;
import masterObject.cashier.TerminalObject;
import masterObject.cashier.VoucherDataObject;

import commonServer.CommonLocal;
import commonServer.CommonLocalHome;
import commonServer.GLTransObject;

import exceptions.DateFormatException;
import exceptions.InsufficientAmountException;
import exceptions.InsufficientBalanceException;
import exceptions.RecordNotInsertedException;
import exceptions.RecordsNotFoundException;
import exceptions.ScrollNotFoundException;
import exceptions.TerminalNotFoundException;
import masterObject.frontCounter.PayOrderObject;
import masterObject.general.GLMasterObject;
import frontCounterServer.FrontCounterLocal;
import frontCounterServer.FrontCounterLocalHome;
import general.Validations;

//ship......23/05/2006
import generalLedgerServer.GLHome;
import generalLedgerServer.GLRemote;
///////////

//ship.....19/06/2006
import loansOnDepositServer.LoansOnDepositLocal;
import loansOnDepositServer.LoansOnDepositLocalHome;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CashBean implements SessionBean
{
    static final long serialVersionUID = 1L;//ship.....03/01/2007
        
    SessionContext sessionContext = null;
    javax.sql.DataSource ds=null;    
    Context jndiContext ;
    int row;
    transient CommonLocalHome common_local_home;
    transient CommonLocal common_local;
    
    transient LoanLocalHome loan_local_home;
    transient LoanLocal loan_local;
    
    transient FrontCounterLocalHome fc_local_home;
    transient FrontCounterLocal fc_local;
    
    //ship.....23/05/2006
    transient GLHome gl_home;
    transient GLRemote gl_remote;
    ////////
    
    //ship.......19/06/2006
    transient LoansOnDepositLocalHome ld_local_home;
    transient LoansOnDepositLocal ld_local;
    //////////
    
    public void ejbCreate() 
    {
        try
        {
            jndiContext =new InitialContext();
            
            common_local_home=(CommonLocalHome)jndiContext.lookup("COMMONLOCALWEB");
            common_local = common_local_home.create();
            
            loan_local_home=(LoanLocalHome)jndiContext.lookup("LOANSLOCALWEB");
            loan_local =  loan_local_home.create();
            
            fc_local_home=(FrontCounterLocalHome)jndiContext.lookup("FRONTCOUNTERLOCALWEB");
            fc_local = fc_local_home.create();
            
            //ship......23/05/2006
            gl_home = (GLHome)jndiContext.lookup("GLWEB");
            gl_remote = gl_home.create();
            //////////
            
            //ship......19/06/2006
            ld_local_home = (LoansOnDepositLocalHome)jndiContext.lookup("LOANSONDEPOSITLOCALWEB");
            ld_local = ld_local_home.create();
            /////////
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        try
        {            
            ds=(DataSource)jndiContext.lookup("java:MySqlDS");            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void setSessionContext(SessionContext cnt) throws EJBException
    {
        this.sessionContext=cnt;
    }
    
    public void ejbRemove() throws EJBException{}
    
    public void ejbActivate() throws EJBException
    {
        try 
        {
            jndiContext = new InitialContext();
            ds=(DataSource)jndiContext.lookup("java:MySqlDS");
            
            common_local_home=(CommonLocalHome)jndiContext.lookup("COMMONLOCALWEB");
            common_local = common_local_home.create();
            
            loan_local_home=(LoanLocalHome)jndiContext.lookup("LOANSLOCALWEB");
            loan_local = loan_local_home.create();
            
            fc_local_home=(FrontCounterLocalHome)jndiContext.lookup("FRONTCOUNTERLOCALWEB");
            fc_local = fc_local_home.create();
            
            //ship......24/05/2006
            gl_home = (GLHome)jndiContext.lookup("GLWEB");
            gl_remote = gl_home.create();
            //////////
            
            //ship......19/06/2006
            ld_local_home = (LoansOnDepositLocalHome)jndiContext.lookup("LOANSONDEPOSITLOCALWEB");
            ld_local = ld_local_home.create();
            /////////
        } 
        catch(NamingException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        System.out.println("Activating");
    }
    
    public void ejbPassivate() throws EJBException
    {
        //ship......16/06/2006
        /*ds=null;
        jndiContext=null;
        common_local_home=null;
        common_local=null;
        
        loan_local_home=null;
        loan_local=null;
        
        fc_local_home=null;
        fc_local=null;
        
        gl_home = null;
        gl_remote = null;*/
        
        System.out.println("Passivating");
    }
    
    public Connection getConnection() 
    {
        try
        {			
            return ds.getConnection("root","");
        }
        catch(Exception e)	
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
    //Business methods
    /**
     * Deletes the Scroll no entry from DayCash only if it is not yet verified for the given date.
     * Also updates the Currency_Stock.
     */
    public boolean deleteData(int scroll,int type,String date,String time) throws SQLException,ScrollNotFoundException   
    {   
        PreparedStatement ps=null;
        ResultSet res;
        Connection conn=null;
        
        System.out.println("scroll no to be deleted is "+scroll);
        
        try
        {	        
            conn=getConnection();
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ps=conn.prepareStatement("select * from DayCash where scroll_no=? and ve_tml is null and trn_date='"+date+"'");
            ps.setInt(1,scroll);
            res=ps.executeQuery();
            
            if(res.last())
            {
                if(res.getRow()==0)
                    throw new ScrollNotFoundException();
            
                res.beforeFirst();
            }
            
            if(type==1)
            {
                if(res.next())
                {
                    if(stmt.executeUpdate("update Currency_Stock set s1000=s1000-"+((res.getInt("r1000")-res.getInt("p1000")))+",s500=s500-"+((res.getInt("r500")-res.getInt("p500")))+",s100=s100-"+((res.getInt("r100")-res.getInt("p100")))+",s50=s50-"+((res.getInt("r50")-res.getInt("p50")))+",s20=s20-"+((res.getInt("r20")-res.getInt("p20")))+",s10=s10-"+((res.getInt("r10")-res.getInt("p10")))+",s5=s5-"+((res.getInt("r5")-res.getInt("p5")))+",s2=s2-"+((res.getInt("r2")-res.getInt("p2")))+",s1=s1-"+((res.getInt("r1")-res.getInt("p1")))+",scoins=scoins-"+((res.getFloat("rcoins")-res.getFloat("pcoins")))+",netamt=netamt-"+(res.getFloat("csh_amt") )+",de_user='"+res.getString("de_user")+"',de_tml='"+res.getString("de_tml")+"',de_date='"+date+"'"+" "+"'"+time+"' where tml_no='"+res.getString("de_tml")+"' and cur_date='"+date+"' and rec_type='O'")==0)
                        throw new SQLException();
                    
                    if(stmt.executeUpdate("delete from DayCash where scroll_no="+scroll+" and ve_tml is null and trn_date='"+date+"'")==0)
                        throw new SQLException();
                    
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if(type==2)//intercounter transfer
            {
                if(res.next())
                {
                	System.out.println("oth_tml=="+res.getString("oth_tml"));
                	System.out.println("de_tml=="+res.getString("de_tml"));
                    
                    if(stmt.executeUpdate("update Currency_Stock set s1000=s1000+"+((res.getInt("p1000")-res.getInt("r1000")))+",s500=s500+"+((res.getInt("p500")-res.getInt("r500")))+",s100=s100+"+((res.getInt("p100")-res.getInt("r100")))+",s50=s50+"+((res.getInt("p50")-res.getInt("r50")))+",s20=s20+"+((res.getInt("p20")-res.getInt("r20")))+",s10=s10+"+((res.getInt("p10")-res.getInt("r10")))+",s5=s5+"+((res.getInt("p5")-res.getInt("r5")))+",s2=s2+"+((res.getInt("p2")-res.getInt("r2")))+",s1=s1+"+((res.getInt("p1")-res.getInt("r1")))+",scoins=scoins+"+((res.getFloat("pcoins")-res.getFloat("rcoins")))+",netamt=netamt+"+(res.getFloat("csh_amt") )+" where tml_no='"+res.getString("de_tml")+"' and cur_date='"+date+"' and rec_type='O'")==0)
                        throw new SQLException();
                    
                    if(stmt.executeUpdate("delete from DayCash where scroll_no="+scroll+" and ve_tml is null and trn_date='"+date+"'")==0)
                        throw new SQLException();
                    
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if(type==3)//re-opening the tml
            {
                if(res.next())
                {
                	System.out.println("oth_tml=="+res.getString("oth_tml"));
                	System.out.println("de_tml=="+res.getString("de_tml"));
                    
                    if(stmt.executeUpdate("update Currency_Stock set rec_type='O',s1000=s1000+"+((res.getInt("p1000")-res.getInt("r1000")))+",s500=s500+"+((res.getInt("p500")-res.getInt("r500")))+",s100=s100+"+((res.getInt("p100")-res.getInt("r100")))+",s50=s50+"+((res.getInt("p50")-res.getInt("r50")))+",s20=s20+"+((res.getInt("p20")-res.getInt("r20")))+",s10=s10+"+((res.getInt("p10")-res.getInt("r10")))+",s5=s5+"+((res.getInt("p5")-res.getInt("r5")))+",s2=s2+"+((res.getInt("p2")-res.getInt("r2")))+",s1=s1+"+((res.getInt("p1")-res.getInt("r1")))+",scoins=scoins+"+((res.getFloat("pcoins")-res.getFloat("rcoins")))+",netamt=netamt+"+(res.getFloat("csh_amt") )+" where tml_no='"+res.getString("de_tml")+"' and cur_date='"+date+"' and rec_type='P'")==0)
                        throw new SQLException();
                    
                    if(stmt.executeUpdate("delete from DayCash where scroll_no="+scroll+" and ve_tml is null and trn_date='"+date+"'")==0)
                        throw new SQLException();
                    
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally
        {
            ps.close();
            conn.close();
        }
        
        return false;
    }
    /**
     * Voucher, bea/Voucher Payment - Updates the DayCash with the Scroll No generated.
     * Also updates the corresponding transaction tables, VoucherData, Currency_Stock, Modules and GLTransaction.
     * 
     * Accounts - Share, Loans, Loans On Deposits, Pygmy, Deposits
     */
    public int storeVoucher(CashObject co) throws SQLException
    {   
        Connection conn=null;
        ResultSet rs_trn_narr = null,rs_gltran = null;
        int scno = 0,s = 0,scno1 = 0;
        String narr = null;//ship.....01/02/2006
        double run_bal = 0.0;
        System.out.println("Are you In Bean !!!!!!!!!!!!!!!!!");
        try
		{
        	
        	String cd_ind = co.getCdind();
            
            conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            ResultSet rs2=stmt.executeQuery("select lst_acc_no from Modules where modulecode='1019000'");//ship......28/03/2006
            
            if(rs2.next())
            {
            	scno1=rs2.getInt(1);
             
            }
            scno=scno1+1;
            System.out.println("Scroll No in Bean -------------"+scno);
            //ship......14/06/2006
            /*ResultSet rs=stmt.executeQuery("select run_bal from DayCash where tml_no='"+co.uv.getUserTml()+"' and run_bal is not null");
            
            if(rs.next())
            {
                rs.last();
                file_logger.info("run bal.....= "+rs.getDouble(1));
                //ship.....commented...08/11/2005
                if(co.getTrntype().equals("R"))
                    run_bal=run_bal+rs.getDouble(1);
                if(trn_type.equals("P"))
                    run_bal=rs.getDouble(1)-run_bal;
                file_logger.info("run bal.....after sub = "+run_bal);
            }*/		
            
            ResultSet rs=stmt.executeQuery("select netamt from Currency_Stock where tml_no='"+co.uv.getUserTml()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'");
            
            if(rs.next())
                run_bal = rs.getDouble("netamt");
            else 
                throw new TerminalNotFoundException(); 
            System.out.println("$$$$$$$$$$$$$$$$$"+co.getAmount());  
            System.out.println("################"+co.getCdind()); 
            if(cd_ind.equals("C"))
            {
                if(run_bal >= co.getAmount())
                    run_bal = run_bal-co.getAmount();
                	
                else
                    throw new InsufficientBalanceException();
            }
            ////////////////
            
            PreparedStatement ps=null;		
            System.out.println(co.uv.getUserTml()+"\t "+co.getVchno()+"\t"+co.getVchtype()+"\t "+co.getAccno()+"\t"+co.getAmount());
            ps=conn.prepareStatement("update DayCash set scroll_no=?,run_bal=?,attached='T',r1000=?,p1000=?,r500=?,p500=?,r100=?,p100=?,r50=?,p50=?,r20=?,p20=?,r10=?,p10=?,r5=?,p5=?,r2=?,p2=?,r1=?,p1=?,rcoins=?,pcoins=?,tml_no='"+co.uv.getUserTml()+"',attached='T' where vch_no="+co.getVchno()+" and vch_type='"+co.getVchtype()+"' and trn_date='"+co.getTrndate()+"' and ((ac_type is null and ac_no is null) or (ac_type='"+co.getAcctype()+"' and ac_no="+co.getAccno()+")) and csh_amt="+co.getAmount()+" and scroll_no=0 and attached='F'");		
            ps.setInt(1,scno);
            ps.setDouble(2,run_bal);
            
            ps.setInt(3,co.getR1000());
            ps.setInt(4,co.getP1000());
            ps.setInt(5,co.getR500());
            ps.setInt(6,co.getP500());
            ps.setInt(7,co.getR100());
            ps.setInt(8,co.getP100());
            ps.setInt(9,co.getR50());
            ps.setInt(10,co.getP50());
            ps.setInt(11,co.getR20());
            ps.setInt(12,co.getP20());
            ps.setInt(13,co.getR10());
            ps.setInt(14,co.getP10());
            ps.setInt(15,co.getR5());
            ps.setInt(16,co.getP5());
            ps.setInt(17,co.getR2());
            ps.setInt(18,co.getP2());
            ps.setInt(19,co.getR1());
            ps.setInt(20,co.getP1());
            ps.setDouble(21,co.getRcoins());
            ps.setDouble(22,co.getPcoins());
            ps.executeUpdate();
            
            //s = ps.executeUpdate();
            
            //update Modules set lst_acc_no=lst_acc_no+1 where modulecode='1019000'")
            if(stmt.executeUpdate("update Modules set lst_acc_no="+scno+" where modulecode='1019000'")==0)
                throw new SQLException();
            
            if(stmt.executeUpdate("update Currency_Stock set s1000=s1000-"+(co.getP1000()-co.getR1000())+",s500=s500-"+(co.getP500()-co.getR500())+",s100=s100-"+(co.getP100()-co.getR100())+",s50=s50-"+(co.getP50()-co.getR50())+",s20=s20-"+(co.getP20()-co.getR20())+",s10=s10-"+(co.getP10()-co.getR10())+",s5=s5-"+(co.getP5()-co.getR5())+",s2=s2-"+(co.getP2()-co.getR2())+",s1=s1-"+(co.getP1()-co.getR1())+",scoins=scoins-"+(co.getPcoins()-co.getRcoins())+",netamt=netamt-"+co.getAmount()+",de_user='"+co.uv.getUserId()+"',de_tml='"+co.uv.getUserTml()+"',de_date='"+co.getTrndate()+"'"+" "+"'"+co.getTrntime()+"' where tml_no='"+co.uv.getUserTml()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'")==0)
                throw new SQLException();
            
            
            
            narr = " CshScrNo "+scno; 
            
                System.out.println("narr in bean"+narr);
            if(co.getAcctype().startsWith("1001"))
            {
                
                
                rs_trn_narr = stmt.executeQuery("select trn_narr from ShareTransaction where ac_no="+co.getAccno()+" and ac_type='"+co.getAcctype()+"' and trn_no="+co.getVchno()+" and trn_date='"+co.getTrndate()+"'");
                
                if(rs_trn_narr.next())
                {
                    if(stmt.executeUpdate("update ShareTransaction set trn_source='"+co.uv.getUserTml()+"',trn_narr='"+rs_trn_narr.getString(1)+"'"+"'"+narr+"',ref_no="+scno+" where ac_no="+co.getAccno()+" and ac_type='"+co.getAcctype()+"' and trn_no="+co.getVchno()+" and trn_date='"+co.getTrndate()+"'")==0)
                        throw new SQLException();
                }
            }
            else if(co.getAcctype().startsWith("1008") || co.getAcctype().startsWith("1010"))
            {
               
                
                rs_trn_narr = stmt.executeQuery("select trn_narr from LoanTransaction where ac_no="+co.getAccno()+" and ac_type='"+co.getAcctype()+"' and ref_no="+co.getVchno()+" and trn_date='"+co.getTrndate()+"'");
                
                if(rs_trn_narr.next())
                {
                    if(stmt.executeUpdate("update LoanTransaction set trn_source='"+co.uv.getUserTml()+"',trn_narr='"+rs_trn_narr.getString(1)+"'"+"'"+narr+"',ref_no="+scno+" where ac_no="+co.getAccno()+" and ac_type='"+co.getAcctype()+"' and ref_no="+co.getVchno()+" and trn_date='"+co.getTrndate()+"'")==0)
                        throw new SQLException();
                }
            }
            //ship.....08/11/2005
            else if(co.getAcctype().startsWith("1006"))
            {
                
                
                rs_trn_narr = stmt.executeQuery("select trn_narr from PygmyTransaction where ac_no="+co.getAccno()+" and ac_type='"+co.getAcctype()+"' and trn_date='"+co.getTrndate()+"' and ref_no="+co.getVchno()+"");
                
                if(rs_trn_narr.next())
                {
                    if(stmt.executeUpdate("update PygmyTransaction set trn_source='"+co.uv.getUserTml()+"',trn_narr='"+rs_trn_narr.getString(1)+"'"+"'"+narr+"',ref_no="+scno+" where ac_no="+co.getAccno()+" and ac_type='"+co.getAcctype()+"' and trn_date='"+co.getTrndate()+"' and ref_no="+co.getVchno()+"")==0)
                        throw new SQLException();
                }
            }
            //
            //ship.....10/11/2005
            else if(co.getAcctype().startsWith("1003") || co.getAcctype().startsWith("1004") || co.getAcctype().startsWith("1005"))
            {
                
                
                //ship......09/02/2007
                /*rs_trn_narr = stmt.executeQuery("select trn_narr from DepositTransaction where ac_no="+co.getAccno()+" and ac_type="+co.getAcctype()+" and (trn_date='"+co.getTrndate()+"' or paid_date='"+co.getTrndate()+"') and ref_no="+co.getVchno()+"");
                
                if(rs_trn_narr.next())
                {
                    if(stmt.executeUpdate("update DepositTransaction set trn_source='"+co.uv.getUserTml()+"',trn_narr='"+rs_trn_narr.getString(1)+"'"+"  "+"'"+narr+"',ref_no="+scno+" where ac_no="+co.getAccno()+" and ac_type="+co.getAcctype()+" and (trn_date='"+co.getTrndate()+"' or paid_date='"+co.getTrndate()+"') and ref_no="+co.getVchno()+"")==0)
                        throw new SQLException();
                }*/
                
                //ship......21/02/2007
                /*if(co.getVchtype().equals("I"))//Interest
                {
                    rs_interest = stmt_interest.executeQuery("select vch_no,vch_date from InterestTransferVoucher where ac_type='"+co.getAcctype()+"' and ac_no="+co.getAccno()+" and vch_pay_ind='T' and vch_pay_date='"+co.getTrndate()+"' and pay_mode='C' and pay_ac_no="+co.getVchno()+"");
                    
                    while(rs_interest.next())
                    {
                        rs_trn_narr = stmt.executeQuery("select trn_narr from DepositTransaction where ac_no="+co.getAccno()+" and ac_type='"+co.getAcctype()+"' and (trn_date='"+rs_interest.getString("vch_date")+"' or paid_date='"+rs_interest.getString("vch_date")+"') and ref_no="+co.getVchno()+" and trn_mode='C'");
                        
                        if(rs_trn_narr.next())
                        {
                            if(stmt.executeUpdate("update DepositTransaction set trn_source='"+co.uv.getUserTml()+"',trn_narr='"+rs_trn_narr.getString(1)+"'"+"'"+narr+"',ref_no="+scno+" where ac_no="+co.getAccno()+" and ac_type='"+co.getAcctype()+"' and (trn_date='"+rs_interest.getString("vch_date")+"' or paid_date='"+rs_interest.getString("vch_date")+"') and ref_no="+co.getVchno()+" and trn_mode='C'")==0)
                                throw new SQLException();
                        }
                    }
                }
                else   //Closure
                {
                    rs_trn_narr = stmt.executeQuery("select trn_narr from DepositTransaction where ac_no="+co.getAccno()+" and ac_type="+co.getAcctype()+" and (trn_date='"+co.getTrndate()+"' or paid_date='"+co.getTrndate()+"') and ref_no="+co.getVchno()+"");
                    
                    if(rs_trn_narr.next())
                    {
                        if(stmt.executeUpdate("update DepositTransaction set trn_source='"+co.uv.getUserTml()+"',trn_narr='"+rs_trn_narr.getString(1)+"'"+"'"+narr+"',ref_no="+scno+" where ac_no="+co.getAccno()+" and ac_type="+co.getAcctype()+" and (trn_date='"+co.getTrndate()+"' or paid_date='"+co.getTrndate()+"') and ref_no="+co.getVchno()+"")==0)
                            throw new SQLException();
                    }
                }*/
                
                rs_trn_narr = stmt.executeQuery("select trn_narr from DepositTransaction where ac_no="+co.getAccno()+" and ac_type='"+co.getAcctype()+"' and (trn_date='"+co.getTrndate()+"' or paid_date='"+co.getTrndate()+"') and ref_no="+co.getVchno()+"");
                
                if(rs_trn_narr.next())
                {
                    if(stmt.executeUpdate("update DepositTransaction set trn_source='"+co.uv.getUserTml()+"',trn_narr='"+rs_trn_narr.getString(1)+"'"+"'"+narr+"',ref_no="+scno+" where ac_no="+co.getAccno()+" and ac_type='"+co.getAcctype()+"' and (trn_date='"+co.getTrndate()+"' or paid_date='"+co.getTrndate()+"') and ref_no="+co.getVchno()+"")==0)
                        throw new SQLException();
                }
                //////////
            }
            //
            //ship.......20/06/2006
            else if(co.getAcctype().startsWith("1012"))
            {
                
                
                if(stmt.executeUpdate("update VoucherData set cash_pdrd='T' where vch_type='"+co.getVchtype()+"' and vch_no="+co.getVchno()+" and trn_date='"+co.getTrndate()+"'")==0)
                    throw new SQLException();
            }
            /////////////
            
            //ship......28/06/2006......updating GLTran
            if(co.getAcctype().startsWith("1012"))
            {
                rs_gltran = stmt.executeQuery("select * from GLTransaction where trn_date='"+co.getTrndate()+"' and ref_ac_type='1019001' and ref_ac_no="+co.getVchno()+" and trn_mode='C' and ref_tr_type='"+co.getVchtype()+"'");
                
                while(rs_gltran.next())
                {
                    
                    
                    if(stmt.executeUpdate("update GLTransaction set ref_no="+scno+" where ref_ac_type='1019001' and trn_date='"+co.getTrndate()+"' and ref_ac_no="+co.getVchno()+" and trn_mode='C' and ref_tr_type='"+co.getVchtype()+"' and gl_type='"+rs_gltran.getString("gl_type")+"' and gl_code="+rs_gltran.getInt("gl_code")+"")==0)
                        throw new SQLException();
                }
            }
            else
            {
                //ship.....17/05/2006
                rs_gltran = stmt.executeQuery("select * from GLTransaction where trn_date='"+co.getTrndate()+"' and ref_ac_no="+co.getAccno()+" and ref_ac_type='"+co.getAcctype()+"' and ref_no="+co.getVchno()+" and trn_mode='C' and cd_ind='D'");
                
                while(rs_gltran.next())
                {
                    
                    
                    if(stmt.executeUpdate("update GLTransaction set ref_no="+scno+" where ref_ac_no="+co.getAccno()+" and ref_ac_type='"+co.getAcctype()+"' and trn_date='"+co.getTrndate()+"' and ref_no="+co.getVchno()+" and trn_mode='C' and cd_ind='D' and gl_type='"+rs_gltran.getString("gl_type")+"' and gl_code="+rs_gltran.getInt("gl_code")+"")==0)
                        throw new SQLException();
                    
                    /*if(rs_gltran.getString("ref_ac_type").equals("1019001"))
                    {
                        file_logger.info("inside ref ac type == 1019001");
                        if(stmt.executeUpdate("update GLTransaction set ref_no="+scno+" where ref_ac_type='1019001' and trn_date='"+co.getTrndate()+"' and ref_no="+co.getVchno()+" and trn_mode='C' and cd_ind='D' and gl_type='"+rs_gltran.getString("gl_type")+"' and gl_code="+rs_gltran.getInt("gl_code")+"")==0)
                            throw new SQLException();
                    }
                    else
                    {
                        file_logger.info("inside ref ac type != 1019001");
                        if(stmt.executeUpdate("update GLTransaction set ref_no="+scno+" where ref_ac_no="+co.getAccno()+" and ref_ac_type="+co.getAcctype()+" and trn_date='"+co.getTrndate()+"' and ref_no="+co.getVchno()+" and trn_mode='C' and cd_ind='D' and gl_type='"+rs_gltran.getString("gl_type")+"' and gl_code="+rs_gltran.getInt("gl_code")+"")==0)
                            throw new SQLException();
                    }*/
                }
                
                
                
                rs_gltran = stmt.executeQuery("select * from GLTransaction where trn_date='"+co.getTrndate()+"' and ref_ac_no="+co.getAccno()+" and ref_ac_type='"+co.getAcctype()+"' and ref_no="+co.getVchno()+" and trn_mode='C' and cd_ind='C'");
                
                if(rs_gltran.next())
                {
                   
                    
                    if(stmt.executeUpdate("update GLTransaction set ref_no="+scno+" where ref_ac_no="+co.getAccno()+" and ref_ac_type='"+co.getAcctype()+"' and trn_date='"+co.getTrndate()+"' and ref_no="+co.getVchno()+" and trn_mode='C' and cd_ind='C' and gl_type='"+rs_gltran.getString("gl_type")+"' and gl_code="+rs_gltran.getInt("gl_code")+"")==0)
                        throw new SQLException();
                }
                
                /*if(stmt.executeUpdate("update GLTransaction set ref_no="+scno+" where ref_ac_type='1019001' and trn_date='"+co.getTrndate()+"' and ref_no="+co.getVchno()+" and trn_mode='C' and cd_ind='C'")==0)
                    throw new SQLException();*/
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            sessionContext.setRollbackOnly();	
        }
        catch(Exception ex1)
        {
            ex1.printStackTrace();
            sessionContext.setRollbackOnly();	
        }
        finally
        {
            conn.close();
        }
        System.out.println("SSSSSSSSSSS"+s);
        //if(s>0)
            return scno;
        
        //else
        //	return 0;
    }
    /**
     * General Payment - Updates the DayCash with the Scroll No generated.
     * Also updates the corresponding transaction tables, ChequeWithdrawal, Currency_Stock, Modules, PayOrder and GLTransaction.
     * 
     * Accounts - Savings, Current A/c, Cash Credit, Over Draft, Current A/c Branch, Current A/c Bank, Pay Order 
     */
    //ship.....09/11/2005.....to store the details of paid token no.
    public int storeToken(CashObject co) throws SQLException
    {
        Connection conn=null;
        int scno = 0,s = 0;
        String narr = null;
        double run_bal = 0.0;
        
        try
		{
        	System.out.println("inside try...store vch...co.vchtype...== "+co.getVchtype());
        	String cd_ind = co.getCdind(); 
        	System.out.println("User terminal Code==="+co.uv.getUserTml());
        	System.out.println("User terminal Code==="+co.getTrndate());
        	conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        	
        	ResultSet rs=stmt.executeQuery("select netamt from Currency_Stock where tml_no='"+co.uv.getUserTml()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'");
        	rs.beforeFirst();
            if(rs.next())
                run_bal = rs.getDouble("netamt");
            else 
                throw new TerminalNotFoundException(); 
                        
            if(cd_ind.equals("C"))
            {
                if(run_bal >= co.getAmount())
                    run_bal = run_bal-co.getAmount();
                else
                    throw new InsufficientBalanceException();
            }
            
            ResultSet rs2=stmt.executeQuery("select lst_acc_no from Modules where modulecode='1019000'");//ship....28/03/2006
            
            if(rs2.next())
                scno=rs2.getInt(1)+1;
            
            //ship......14/06/2006
            /*ResultSet rs=stmt.executeQuery("select run_bal from DayCash where tml_no='"+co.uv.getUserTml()+"' and run_bal is not null");
            
            if(rs.next())
            {
                rs.last();
                file_logger.info("run bal.....= "+rs.getDouble(1));
                
                if(trn_type.equals("P"))
                    run_bal=rs.getDouble(1)-run_bal;
                file_logger.info("run bal.....after sub = "+run_bal);
            }*/	
                
           System.out.println("run bal.....after sub = "+run_bal);
            //////////////
            
            PreparedStatement ps = null;
            
            ps = conn.prepareStatement("update DayCash set scroll_no=?,run_bal=?,r1000=?,p1000=?,r500=?,p500=?,r100=?,p100=?,r50=?,p50=?,r20=?,p20=?,r10=?,p10=?,r5=?,p5=?,r2=?,p2=?,r1=?,p1=?,rcoins=?,pcoins=?,tml_no=?,attached=? where ac_type=? and ac_no=? and token_no=? and trn_date='"+co.getTrndate()+"' and chq_no=? and csh_amt="+co.getAmount()+" and scroll_no=0 and attached='F'");
            System.out.println("!!!!!!!!!! vch no="+scno);
            ps.setInt(1,scno);
            ps.setDouble(2,run_bal);
            
            ps.setInt(3,co.getR1000());
            ps.setInt(4,co.getP1000());
            ps.setInt(5,co.getR500());
            ps.setInt(6,co.getP500());
            ps.setInt(7,co.getR100());
            ps.setInt(8,co.getP100());
            ps.setInt(9,co.getR50());
            ps.setInt(10,co.getP50());
            ps.setInt(11,co.getR20());
            ps.setInt(12,co.getP20());
            ps.setInt(13,co.getR10());
            ps.setInt(14,co.getP10());
            ps.setInt(15,co.getR5());
            ps.setInt(16,co.getP5());
            ps.setInt(17,co.getR2());
            ps.setInt(18,co.getP2());
            ps.setInt(19,co.getR1());
            ps.setInt(20,co.getP1());
            ps.setDouble(21,co.getRcoins());
            ps.setDouble(22,co.getPcoins());
            
            ps.setString(23,co.getTerminalNo());
            ps.setString(24,"T");
            
            ps.setString(25,co.getAcctype());
            ps.setString(26,co.getAccno());
            ps.setInt(27,co.getTokenno());
            ps.setInt(28,co.getChqno());
            
            s = ps.executeUpdate();
            
            if(stmt.executeUpdate("update Modules set lst_acc_no=lst_acc_no+1 where modulecode='1019000'")==0)//ship.....28/03/2006
                throw new SQLException();
                
            if(stmt.executeUpdate("update Currency_Stock set s1000=s1000-"+(co.getP1000()-co.getR1000())+",s500=s500-"+(co.getP500()-co.getR500())+",s100=s100-"+(co.getP100()-co.getR100())+",s50=s50-"+(co.getP50()-co.getR50())+",s20=s20-"+(co.getP20()-co.getR20())+",s10=s10-"+(co.getP10()-co.getR10())+",s5=s5-"+(co.getP5()-co.getR5())+",s2=s2-"+(co.getP2()-co.getR2())+",s1=s1-"+(co.getP1()-co.getR1())+",scoins=scoins-"+(co.getPcoins()-co.getRcoins())+",netamt=netamt-"+co.getAmount()+",de_user='"+co.uv.getUserId()+"',de_tml='"+co.uv.getUserTml()+"',de_date='"+co.getTrndate()+"'"+" "+"'"+co.getTrntime()+"' where tml_no='"+co.uv.getUserTml()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'")==0)
                throw new SQLException();
                       
            if(stmt.executeUpdate("update ChequeWithdrawal set cash_pd='T',tml_no='"+co.uv.getUserTml()+"' where cash_pd='F' and trn_date='"+co.getTrndate()+"' and ac_type="+co.getAcctype()+" and ac_no="+co.getAccno()+" and tml_no is null and token_no="+co.getTokenno()+"")==0)
                throw new SQLException();
            
            System.out.println("Updation of token no.."+s);
            System.out.println(scno);    
            
            narr = "Csh Scr No "+scno+""; 
            
            System.out.println("Narration = "+narr);
            
            if(co.getAcctype().startsWith("1002") || co.getAcctype().startsWith("1007") || co.getAcctype().startsWith("1017") || co.getAcctype().startsWith("1018"))
            {
                ResultSet rs_trn_narr = stmt.executeQuery("select trn_narr from AccountTransaction  where trn_date='"+co.getTrndate()+"' and chq_dd_no="+co.getChqno()+" and ac_type="+co.getAcctype()+" and ac_no="+co.getAccno()+" and ref_no="+co.getTokenno()+"");
                
                if(rs_trn_narr.next())
                {
                    System.out.println("inside a/c tran ....narration = "+rs_trn_narr.getString(1));
                    
                    if(stmt.executeUpdate("update AccountTransaction set trn_source='"+co.uv.getUserTml()+"',trn_narr='"+rs_trn_narr.getString(1)+"'"+"  "+"'"+narr+"',ref_no="+scno+" where trn_date='"+co.getTrndate()+"' and chq_dd_no="+co.getChqno()+" and ac_type="+co.getAcctype()+" and ac_no="+co.getAccno()+" and ref_no="+co.getTokenno()+"")==0)
                        throw new SQLException();
                }
            }
            else if(co.getAcctype().startsWith("1014") || co.getAcctype().startsWith("1015"))
            {
                ResultSet rs_trn_narr = stmt.executeQuery("select trn_narr from ODCCTransaction  where trn_date='"+co.getTrndate()+"' and chq_dd_no="+co.getChqno()+" and ac_type="+co.getAcctype()+" and ac_no="+co.getAccno()+" and ref_no="+co.getTokenno()+"");
                if(rs_trn_narr.next())
                {
                    if(stmt.executeUpdate("update ODCCTransaction set trn_source='"+co.uv.getUserTml()+"',trn_narr='"+rs_trn_narr.getString(1)+"'"+"  "+"'"+narr+"',ref_no="+scno+" where trn_date='"+co.getTrndate()+"' and chq_dd_no="+co.getChqno()+" and ac_type="+co.getAcctype()+" and ac_no="+co.getAccno()+" and ref_no="+co.getTokenno()+"")==0)
                        throw new SQLException();
                }
            }
            //ship......01/02/2006
            else if(co.getAcctype().startsWith("1016"))
            {
                if(stmt.executeUpdate("update PayOrder set po_encsh_ref_no="+scno+" where payord_no="+co.getAccno()+" and po_csh_ind=1 and po_chq_no="+co.getChqno()+" and po_csh_dt='"+co.getTrndate()+"'")==0)
                    throw new SQLException();
            }
            //ship......03/12/2005
            if(stmt.executeUpdate("update GLTransaction set ref_no="+scno+" where ref_ac_no="+co.getAccno()+" and ref_ac_type='"+co.getAcctype()+"' and ref_no="+co.getTokenno()+" and trn_date='"+co.getTrndate()+"' and trn_mode='C' and cd_ind='D'")==0)
                throw new SQLException();
            // 
            
            //ship......12/01/2006
            if(stmt.executeUpdate("update GLTransaction set ref_no="+scno+" where ref_ac_no="+co.getAccno()+" and ref_ac_type='"+co.getAcctype()+"' and trn_date='"+co.getTrndate()+"' and ref_no="+co.getTokenno()+" and trn_mode='C' and cd_ind='C'")==0)
                throw new SQLException();
            //
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            sessionContext.setRollbackOnly();	
        }
        catch(Exception ex1)
        {
            ex1.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally
        {
            conn.close();
        }
        
        if(s>0)
            return scno;
        else
        	return 0;
    }
    //
    
    //ship.....13/06/2006
    /*public int storeDayCash(CashObject co) throws SQLException,InsufficientAmountException,TerminalNotFoundException, InsufficientBalanceException
    {
        System.out.println("IN Store DayCash");
        Connection conn=null;
        PreparedStatement ps=null;
        int up=0,s=0;
        int scno=0,vchno=0;//ship
        
        try
		{
            conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //int scno=0,vchno=0;
            
            System.out.println("vch_no in storeDayCash from object"+co.getVchno());
            
            *//**
             *  vchno variable is used for getting the voucherno from cashobject ie, for 
             * already existing records. 
             *//*
            vchno=co.getVchno();//added for MiscellanousReceipts
            
            System.out.println("scroll_no in storeDayCash from object "+co.getScrollno());
            System.out.println("vch_no in storeDayCash from variable"+vchno);
            
            scno=co.getScrollno();
            vchno=co.getVchno();
            int copy_scno=scno;
            //int copy_vchno=vchno;
            double run_bal = co.getAmount();
            int flag=0;//ship
            
            if(scno==0)
            {
            	flag=1;//ship
                System.out.println("...1");
                ResultSet rs2=null;
                
                if(co.getVchno()==-1)
                {
                    System.out.println("*********inside if(co.getVchno()*******");
                    
                    //rs2=stmt.executeQuery("select scroll_no,vch_no from GenParam");
                    rs2=stmt.executeQuery("select lst_acc_no,lst_rct_no from Modules where modulecode='1019000'");//ship.....28/03/2006
                    rs2.next();
                    
                    System.out.println("scroll no before update"+rs2.getInt(1));
                    System.out.println("vch no before update"+rs2.getInt(2));
                    
                    scno=rs2.getInt(1)+1;			
                    vchno=rs2.getInt(2)+1;
                }
                else
                {
                    //rs2=stmt.executeQuery("select scroll_no from GenParam");
                    rs2=stmt.executeQuery("select lst_acc_no from Modules where modulecode='1019000'");//ship...28/03/2006
                    rs2.next();
                    scno=rs2.getInt(1)+1;				
                    System.out.println("Scroll no from Modules = "+scno);
                }
                System.out.println("...2  othertmel"+co.getOthtml());
                
                if(co.getOthtml()!=null && co.getTrntype().equals("A")) //for intercounter transfer
                {
                    System.out.println("other terminal &&&& =="+co.getOthtml());
                    
                    ResultSet rs3=stmt.executeQuery("select * from Currency_Stock where tml_no='"+co.getOthtml()+"' and cur_date='"+getClientDate()+"' and rec_type='O'");
                    
                    if(rs3.next())
                    {
                        //ship.......12/06/2006
                        if(rs3.getString("rec_type").equals("C")) //if the terminal is closed, open it.
                        {
                            System.out.println("Terminal closed.. will open the terminal");
                            
                            if(stmt.executeUpdate("update Currency_Stock set s1000=s1000+"+(co.getP1000()-co.getR1000())+",s500=s500+"+(co.getP500()-co.getR500())+",s100=s100+"+(co.getP100()-co.getR100())+",s50=s50+"+(co.getP50()-co.getR50())+",s20=s20+"+(co.getP20()-co.getR20())+",s10=s10+"+(co.getP10()-co.getR10())+",s5=s5+"+(co.getP5()-co.getR5())+",s2=s2+"+(co.getP2()-co.getR2())+",s1=s1+"+(co.getP1()-co.getR1())+",scoins=scoins+"+(co.getPcoins()-co.getRcoins())+",netamt=netamt+"+co.getAmount()+",rec_type='O' where tml_no='"+co.getOthtml()+"' and cur_date='"+getClientDate()+"'")==0) // add the money to the transfered terminal
                                throw new SQLException();
                        }
                        else //else the terminal is already opened
                        {
                            System.out.println("Terminal is  open "+co.getTerminalNo());
                            ResultSet rs4=stmt.executeQuery("select * from Currency_Stock where tml_no='"+co.getTerminalNo()+"'");
                            rs4.next();
                        	
                        	System.out.println("Terminal is  open "+co.getOthtml());
                        	
                            ResultSet rs4=stmt.executeQuery("select * from Currency_Stock where tml_no='"+co.getTerminalNo()+"' and cur_date='"+getClientDate()+"' and rec_type='O'");
                            
                            if(rs4.next())
                            {
                                if((rs4.getInt("s1000")>=((co.getR1000()-co.getP1000())!=0?co.getR1000()-co.getP1000():rs4.getInt("s1000"))) &&
                                        (rs4.getInt("s500")>=((co.getR500()-co.getP500())!=0?co.getR500()-co.getP500(): rs4.getInt("s500"))) &&
                                        (rs4.getInt("s100")>=((co.getR100()-co.getP100())!=0?co.getR100()-co.getP100():rs4.getInt("s100"))) &&
                                        (rs4.getInt("s50")>=((co.getR50()-co.getP50())!=0?co.getR50()-co.getP50():rs4.getInt("s50"))) && 
                                        (rs4.getInt("s20")>=((co.getR20()-co.getP20())!=0?co.getR20()-co.getP20():rs4.getInt("s20"))) &&
                                        (rs4.getInt("s10")>=((co.getR10()-co.getP10())!=0?co.getR10()-co.getP10():rs4.getInt("s10"))) &&
                                        (rs4.getInt("s5")>=((co.getR5()-co.getP5())!=0?co.getR5()-co.getP5():rs4.getInt("s5"))) &&
                                        (rs4.getInt("s2")>=((co.getR2()-co.getP2())!=0?co.getR2()-co.getP2():rs4.getInt("s2"))) &&
                                        (rs4.getInt("s1")>=((co.getR1()-co.getP1())!=0?co.getR1()-co.getP1():rs4.getInt("s1"))) &&
                                        (rs4.getFloat("scoins")>=((co.getRcoins()-co.getPcoins())!=0?co.getRcoins()-co.getPcoins():rs4.getFloat("scoins"))))
                                       {
                                           System.out.println("INside the IF condition" );
                                           //ship.....commented to update only the current terminal....04/10/2005
                                           if((stmt.executeUpdate("update Currency_Stock set cur_date='"+getClientDate()+"',s1000=s1000+"+(co.getR1000()-co.getP1000())+",s500=s500+"+(co.getR500()-co.getP500())+",s100=s100+"+(co.getR100()-co.getP100())+",s50=s50+"+(co.getR50()-co.getP50())+",s20=s20+"+(co.getR20()-co.getP20())+",s10=s10+"+(co.getR10()-co.getP10())+",s5=s5+"+(co.getR5()-co.getP5())+",s2=s2+"+(co.getR2()-co.getP2())+",s1=s1+"+(co.getR1()-co.getP1())+",scoins=scoins+"+(co.getRcoins()-co.getPcoins())+",netamt=netamt+"+co.getAmount()+" where tml_no='"+co.getOthtml()+"' "))==0) // add the money to the transfered terminal
                                               throw new SQLException();
                                        
                                           if((stmt.executeUpdate("update Currency_Stock set s1000=s1000-"+(co.getP1000()-co.getR1000())+",s500=s500-"+(co.getP500()-co.getR500())+",s100=s100-"+(co.getP100()-co.getR100())+",s50=s50-"+(co.getP50()-co.getR50())+",s20=s20-"+(co.getP20()-co.getR20())+",s10=s10-"+(co.getP10()-co.getR10())+",s5=s5-"+(co.getP5()-co.getR5())+",s2=s2-"+(co.getP2()-co.getR2())+",s1=s1-"+(co.getP1()-co.getR1())+",scoins=scoins-"+(co.getPcoins()-co.getRcoins())+",netamt=netamt-"+co.getAmount()+" where tml_no='"+co.getTerminalNo()+"' and cur_date='"+getClientDate()+"'"))==0)// subtract money from the terminal from where money is transferred
                                               throw new SQLException();
                                       }
                                       else 
                                       {
                                       	System.out.println("INside the else condition" );
                                       	System.out.println("co.isInsufficientFlag() == "+co.isInsufficientFlag());
                                       	
                                           if(co.isInsufficientFlag())
                                           {	
                                           	//ship.....commented to update only the current terminal....04/10/2005
                                               if((stmt.executeUpdate("update Currency_Stock set cur_date='"+getClientDate()+"',s1000=s1000+"+(co.getR1000()-co.getP1000())+",s500=s500+"+(co.getR500()-co.getP500())+",s100=s100+"+(co.getR100()-co.getP100())+",s50=s50+"+(co.getR50()-co.getP50())+",s20=s20+"+(co.getR20()-co.getP20())+",s10=s10+"+(co.getR10()-co.getP10())+",s5=s5+"+(co.getR5()-co.getP5())+",s2=s2+"+(co.getR2()-co.getP2())+",s1=s1+"+(co.getR1()-co.getP1())+",scoins=scoins+"+(co.getRcoins()-co.getPcoins())+",netamt=netamt+"+co.getAmount()+" where tml_no='"+co.getOthtml()+"' "))==0) // add the money to the transfered terminal
                                                   throw new SQLException();
                                              
                                               if((stmt.executeUpdate("update Currency_Stock set s1000=s1000-"+(co.getP1000()-co.getR1000())+",s500=s500-"+(co.getP500()-co.getR500())+",s100=s100-"+(co.getP100()-co.getR100())+",s50=s50-"+(co.getP50()-co.getR50())+",s20=s20-"+(co.getP20()-co.getR20())+",s10=s10-"+(co.getP10()-co.getR10())+",s5=s5-"+(co.getP5()-co.getR5())+",s2=s2-"+(co.getP2()-co.getR2())+",s1=s1-"+(co.getP1()-co.getR1())+",scoins=scoins-"+(co.getPcoins()-co.getRcoins())+",netamt=netamt-"+co.getAmount()+" where tml_no='"+co.getTerminalNo()+"' and cur_date='"+getClientDate()+"' and rec_type='O'"))==0)// subtract money from the terminal from where money is transferred
                                                   throw new SQLException();
                                           }
                                           else
                                               throw new InsufficientAmountException();
                                           System.out.println("Before diplaying no currency denominations" );
                                       }
                                }
                        //}
                    }
                    else
                        throw new TerminalNotFoundException();
                }
                else if(!(co.getOthtml()!=null)) //for other transfers
                {
                	System.out.println("for other transfers");
                	System.out.println("trn_type"+co.getTrntype());
                	System.out.println("co.isInsufficientFlag() == "+co.isInsufficientFlag());
                	if(co.getTrntype().equals("R"))
                	{
                		System.out.println("Before Settin in CurrencyStock for terminal "+co.uv.getUserTml());
                		
                		if(stmt.executeUpdate("update Currency_Stock set s1000=s1000+"+(co.getR1000()-co.getP1000())+",s500=s500+"+(co.getR500()-co.getP500())+",s100=s100+"+(co.getR100()-co.getP100())+",s50=s50+"+(co.getR50()-co.getP50())+",s20=s20+"+(co.getR20()-co.getP20())+",s10=s10+"+(co.getR10()-co.getP10())+",s5=s5+"+(co.getR5()-co.getP5())+",s2=s2+"+(co.getR2()-co.getP2())+",s1=s1+"+(co.getR1()-co.getP1())+",scoins=scoins+"+(co.getRcoins()-co.getPcoins())+",netamt=netamt+"+co.getAmount()+",de_user='"+co.uv.getUserId()+"',de_tml='"+co.uv.getUserTml()+"',de_date='"+getClientDate()+"' where tml_no='"+co.uv.getUserTml()+"' and cur_date='"+getClientDate()+"' and rec_type='O'")==0)
                			throw new SQLException();
                	}
                	else if(co.getTrntype().equals("P"))
                	{
                		if(stmt.executeUpdate("update Currency_Stock set s1000=s1000-"+(co.getR1000()-co.getP1000())+",s500=s500-"+(co.getR500()-co.getP500())+",s100=s100-"+(co.getR100()-co.getP100())+",s50=s50-"+(co.getR50()-co.getP50())+",s20=s20-"+(co.getR20()-co.getP20())+",s10=s10-"+(co.getR10()-co.getP10())+",s5=s5-"+(co.getR5()-co.getP5())+",s2=s2-"+(co.getR2()-co.getP2())+",s1=s1-"+(co.getR1()-co.getP1())+",scoins=scoins-"+(co.getRcoins()-co.getPcoins())+",netamt=netamt-"+co.getAmount()+",de_user='"+co.uv.getUserId()+"',de_tml='"+co.uv.getUserTml()+"',de_date='"+getClientDate()+"' where tml_no='"+co.uv.getUserTml()+"' and cur_date='"+getClientDate()+"' and rec_type='O'")==0)
                			throw new SQLException();
                	}
                }
            }
            else //for already existing scroll nos.
            {
                System.out.println("*************438****************");
                
                //ship.....altered on 05/10/2005
                if(co.getOthtml()!=null) //for intercounter transfer
                {
                ResultSet rs=stmt.executeQuery("select * from DayCash where scroll_no="+scno+" and ve_tml is null and trn_date='"+getClientDate()+"'");
                
                if(rs.next())
                {
                	System.out.println("scroll no inside if= "+scno);
                	PreparedStatement ps1=null;		
                    ps1=conn.prepareStatement("update DayCash set ac_type="+co.getAcctype()+",ac_no="+co.getAccno()+",name='"+co.getAccname()+"',csh_amt=?,trn_type=?,run_bal=?,r1000=?,p1000=?,r500=?,p500=?,r100=?,p100=?,r50=?,p50=?,r20=?,p20=?,r10=?,p10=?,r5=?,p5=?,r2=?,p2=?,r1=?,p1=?,rcoins=?,pcoins=?,de_user=?,de_tml=? where scroll_no="+scno+" and trn_date='"+getClientDate()+"'" );		
                    ps1.setDouble(1,co.getAmount());
                    ps1.setString(2,"A");
                    ps1.setDouble(3,run_bal);
                    
                    ps1.setInt(4,co.getR1000());
                    ps1.setInt(5,co.getP1000());
                    ps1.setInt(6,co.getR500());
                    ps1.setInt(7,co.getP500());
                    ps1.setInt(8,co.getR100());
                    ps1.setInt(9,co.getP100());
                    ps1.setInt(10,co.getR50());
                    ps1.setInt(11,co.getP50());
                    ps1.setInt(12,co.getR20());
                    ps1.setInt(13,co.getP20());
                    ps1.setInt(14,co.getR10());
                    ps1.setInt(15,co.getP10());
                    ps1.setInt(16,co.getR5());
                    ps1.setInt(17,co.getP5());
                    ps1.setInt(18,co.getR2());
                    ps1.setInt(19,co.getP2());
                    ps1.setInt(20,co.getR1());
                    ps1.setInt(21,co.getP1());
                    ps1.setDouble(22,co.getRcoins());
                    ps1.setDouble(23,co.getPcoins());
                    ps1.setString(24,co.uv.getUserId());
                    ps1.setString(25,co.uv.getUserTml());
                    
                    System.out.println("Updation"+ps1.executeUpdate());
                    System.out.println(scno);
                    System.out.println(scno);
                    
                    if(stmt.executeUpdate("update Currency_Stock set s1000=s1000-"+((co.getP1000()-co.getR1000())-(rs.getInt("p1000")-rs.getInt("r1000")))+",s500=s500-"+((co.getP500()-co.getR500())-(rs.getInt("p500")-rs.getInt("r500")))+",s100=s100-"+((co.getP100()-co.getR100())-(rs.getInt("p100")-rs.getInt("r100")))+",s50=s50-"+((co.getP50()-co.getR50())-(rs.getInt("p50")-rs.getInt("r50")))+",s20=s20-"+((co.getP20()-co.getR20())-(rs.getInt("p20")-rs.getInt("r20")))+",s10=s10-"+((co.getP10()-co.getR10())-(rs.getInt("p10")-rs.getInt("r10")))+",s5=s5-"+((co.getP5()-co.getR5())-(rs.getInt("p5")-rs.getInt("r5")))+",s2=s2-"+((co.getP2()-co.getR2())-(rs.getInt("p2")-rs.getInt("r2")))+",s1=s1-"+((co.getP1()-co.getR1())-(rs.getInt("p1")-rs.getInt("r1")))+",scoins=scoins-"+((co.getPcoins()-co.getRcoins())-(rs.getInt("pcoins")-rs.getInt("rcoins")))+",netamt=netamt-"+(co.getAmount()-rs.getFloat("csh_amt"))+",de_user='"+co.uv.getUserId()+"',de_tml='"+co.uv.getUserTml()+"',de_date='"+getClientDate()+"' where tml_no='"+co.getTerminalNo()+"' and cur_date='"+getClientDate()+"' and rec_type='O'")==0)
                        throw new SQLException();
                }
                }
                else//for other transfers
                {
                    ResultSet rs=stmt.executeQuery("select * from DayCash where scroll_no="+scno+" and ve_tml is null and trn_date='"+getClientDate()+"'");
                    
                    if(rs.next())
                    {
                    	System.out.println("scroll no inside if= "+scno);
                    	PreparedStatement ps1=null;		
                        ps1=conn.prepareStatement("update DayCash set ac_type="+co.getAcctype()+",ac_no="+co.getAccno()+",name='"+co.getAccname()+"',csh_amt=?,trn_type=?,run_bal=?,r1000=?,p1000=?,r500=?,p500=?,r100=?,p100=?,r50=?,p50=?,r20=?,p20=?,r10=?,p10=?,r5=?,p5=?,r2=?,p2=?,r1=?,p1=?,rcoins=?,pcoins=?,de_user=?,de_tml=? where scroll_no="+scno+" and trn_date='"+getClientDate()+"'" );		
                        ps1.setDouble(1,co.getAmount());
                        ps1.setString(2,"R");
                        ps1.setDouble(3,run_bal);
                        
                        ps1.setInt(4,co.getR1000());
                        ps1.setInt(5,co.getP1000());
                        ps1.setInt(6,co.getR500());
                        ps1.setInt(7,co.getP500());
                        ps1.setInt(8,co.getR100());
                        ps1.setInt(9,co.getP100());
                        ps1.setInt(10,co.getR50());
                        ps1.setInt(11,co.getP50());
                        ps1.setInt(12,co.getR20());
                        ps1.setInt(13,co.getP20());
                        ps1.setInt(14,co.getR10());
                        ps1.setInt(15,co.getP10());
                        ps1.setInt(16,co.getR5());
                        ps1.setInt(17,co.getP5());
                        ps1.setInt(18,co.getR2());
                        ps1.setInt(19,co.getP2());
                        ps1.setInt(20,co.getR1());
                        ps1.setInt(21,co.getP1());
                        ps1.setDouble(22,co.getRcoins());
                        ps1.setDouble(23,co.getPcoins());
                        ps1.setString(24,co.uv.getUserId());
                        ps1.setString(25,co.uv.getUserTml());
                        
                        System.out.println("Updation"+ps1.executeUpdate());
                        System.out.println(scno);
                        System.out.println(scno);
                        
                        if(stmt.executeUpdate("update Currency_Stock set s1000=s1000+"+((co.getR1000()-co.getP1000())-(rs.getInt("r1000")-rs.getInt("p1000")))+",s500=s500+"+((co.getR500()-co.getP500())-(rs.getInt("r500")-rs.getInt("p500")))+",s100=s100+"+((co.getR100()-co.getP100())-(rs.getInt("r100")-rs.getInt("p100")))+",s50=s50+"+((co.getR50()-co.getP50())-(rs.getInt("r50")-rs.getInt("p50")))+",s20=s20+"+((co.getR20()-co.getP20())-(rs.getInt("r20")-rs.getInt("p20")))+",s10=s10+"+((co.getR10()-co.getP10())-(rs.getInt("r10")-rs.getInt("p10")))+",s5=s5+"+((co.getR5()-co.getP5())-(rs.getInt("r5")-rs.getInt("p5")))+",s2=s2+"+((co.getR2()-co.getP2())-(rs.getInt("r2")-rs.getInt("p2")))+",s1=s1+"+((co.getR1()-co.getP1())-(rs.getInt("r1")-rs.getInt("p1")))+",scoins=scoins+"+((co.getRcoins()-co.getPcoins())-(rs.getInt("rcoins")-rs.getInt("pcoins")))+",netamt=netamt+"+(co.getAmount()-rs.getFloat("csh_amt"))+",de_user='"+co.uv.getUserId()+"',de_tml='"+co.uv.getUserTml()+"',de_date='"+getClientDate()+"' where tml_no='"+co.getTerminalNo()+"' and cur_date='"+getClientDate()+"' and rec_type='O'")==0)
                            throw new SQLException();
                    }
                }
                //
            }
            
            System.out.println("trn_type "+co.getTrntype());
            
            if(co.getTrntype().equals("A"))
            {
                System.out.println("trntype= A");
                ResultSet rs5=stmt.executeQuery("select run_bal from DayCash where de_tml ='"+co.getUserTml()+"' and trn_date='"+getClientDate()+"' order by concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)),scroll_no");
                
                if(rs5.next())
                {
                    rs5.last();
                    if(rs5.getFloat("run_bal")>=run_bal)
                        run_bal=rs5.getFloat("run_bal")-run_bal;
                    else
                        throw new InsufficientBalanceException();
                }
                else 
                    throw new TerminalNotFoundException();
            }
            //ship.....commented on 07/10/2005
            else if(co.getTrntype().equals("B"))
            {
                System.out.println("trntype= B");
                ResultSet rs4=stmt.executeQuery("select run_bal from DayCash where de_tml ='"+co.getTerminalNo()+"' order by concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)),scroll_no");
                
                if(rs4.next())
                {
                    rs4.last();
                    run_bal=run_bal+rs4.getDouble(1);	
                }
            }
            
            else if(co.getTrntype().equals("R") || co.getTrntype().equals("P"))
            {
                System.out.println("trntype= R or P");
                System.out.println("usertmel == "+co.uv.getUserTml());
                
                //ship.....added scroll_no!=0 in the where clause on 14/10/2005                
                ResultSet rs6=stmt.executeQuery("select run_bal from DayCash where de_tml='"+co.uv.getUserTml()+"' and scroll_no!=0 order by concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)),scroll_no");
                
                if(rs6.next())
                {
                    rs6.last();
                    if(co.getTrntype().equals("R"))
                    {
                        System.out.println("prev runball is "+rs6.getDouble(1));
                        System.out.println("before adding the amount runball is "+run_bal);
                        //  if(co.getTrntype().equals("R"))
                        run_bal=run_bal+rs6.getDouble(1);
                        System.out.println("after adding the amount runball is "+run_bal);
                    }
                    else if(co.getTrntype().equals("P"))
                    {
                    	System.out.println("!!!!!!trn P");
                        if(rs6.getDouble(1)>=run_bal)
                        {
                            run_bal=rs6.getDouble(1)-run_bal;
                            System.out.println("run_bal==="+run_bal);
                        }
                        else
                            throw new InsufficientBalanceException();
                    }
                }
                //                else
                //                { 
                //                    ResultSet rs7=stmt.executeQuery("select run_bal from DayCash where de_tml='"+co.uv.getUserTml()+"' order by concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)),scroll_no");
                //                    if(rs7.next())
                //                    {
                //                        rs7.last();
                //                        if(rs7.getFloat(1)>=run_bal)
                //                        	run_bal=rs7.getFloat(1)-run_bal;
                //                        else
                //                        	throw new InsufficientAmountException();
                //                    }
                //                }
            }
            
            System.out.println("Scroollll no "+scno);
                       
            	if(co.getTrntype().equals("R"))
            	{
            		//ship.....added this to update the scroll no.
                    if(flag==1)
                    	ps=conn.prepareStatement("insert into DayCash(scroll_no,trn_date,ac_type,ac_no,trn_type,oth_tml,csh_amt,comm_amt,locker_type,name,run_bal,cd_ind,trn_seq,attached,de_user,de_tml,de_date,r1000,r500,r100,r50,r20,r10,r5,r2,r1,rcoins,p1000,p500,p100,p50,p20,p10,p5,p2,p1,pcoins,tml_no,vch_type,vch_no,share_category,cust_ac_type,cust_ac_no,cust_code,po_favour_name) values("+scno+",'"+getClientDate()+"',?,?,?,?,?,?,?,?,?,?,?,'F',?,?,'"+getSysDateTime()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    else
                    	ps=conn.prepareStatement("update DayCash set ac_type=?,ac_no=?,trn_type=?,oth_tml=?,csh_amt=?,comm_amt=?,locker_type=?,name=?,run_bal=?,cd_ind=?,trn_seq=?,de_user=?,de_tml=?,de_date='"+getSysDateTime()+"',r1000=?,r500=?,r100=?,r50=?,r20=?,r10=?,r5=?,r2=?,r1=?,rcoins=?,p1000=?,p500=?,p100=?,p50=?,p20=?,p10=?,p5=?,p2=?,p1=?,pcoins=?,tml_no=?,vch_type=?,vch_no=?,share_category=?,cust_ac_type=?,cust_ac_no=?,cust_code=?,po_favour_name=? where scroll_no="+scno+" and trn_date='"+getClientDate()+"'");
                    //
            	}
            	else if(co.getTrntype().equals("P") && (co.getVchno()<=0 ))
            	{
            		System.out.println("only for SB... Payments");
            		ps=conn.prepareStatement("insert into DayCash(scroll_no,trn_date,ac_type,ac_no,trn_type,oth_tml,csh_amt,comm_amt,locker_type,name,run_bal,cd_ind,trn_seq,attached,de_user,de_tml,de_date,r1000,r500,r100,r50,r20,r10,r5,r2,r1,rcoins,p1000,p500,p100,p50,p20,p10,p5,p2,p1,pcoins,tml_no,vch_type,token_no,share_category,cust_ac_type,cust_ac_no,cust_code,po_favour_name) values("+scno+",'"+getClientDate()+"',?,?,?,?,?,?,?,?,?,?,?,'F',?,?,'"+getSysDateTime()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            	}
            	else if(co.getTrntype().equals("P") && co.getVchtype().equals("P") && (co.getVchno()>0 ))
            	{
            		System.out.println("only for Vouchers... Payments");
            		ps=conn.prepareStatement("insert into DayCash(scroll_no,trn_date,ac_type,ac_no,trn_type,oth_tml,csh_amt,comm_amt,locker_type,name,run_bal,cd_ind,trn_seq,attached,de_user,de_tml,de_date,r1000,r500,r100,r50,r20,r10,r5,r2,r1,rcoins,p1000,p500,p100,p50,p20,p10,p5,p2,p1,pcoins,tml_no,vch_type,vch_no,share_category,cust_ac_type,cust_ac_no,cust_code,po_favour_name) values("+scno+",'"+getClientDate()+"',?,?,?,?,?,?,?,?,?,?,?,'T',?,?,'"+getSysDateTime()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            	}
            	//ship......commented on 07/10/2005
            	else if(co.getTrntype().equals("B"))//accepting money from other terminal
            		ps=conn.prepareStatement("insert into DayCash(scroll_no,trn_date,ac_type,ac_no,trn_type,oth_tml,csh_amt,comm_amt,locker_type,name,run_bal,cd_ind,trn_seq,attached,de_user,de_tml,de_date,r1000,r500,r100,r50,r20,r10,r5,r2,r1,rcoins,p1000,p500,p100,p50,p20,p10,p5,p2,p1,pcoins,tml_no,vch_type,vch_no) values("+scno+",'"+getClientDate()+"',?,?,?,?,?,?,?,?,?,?,?,'F',?,?,'"+getSysDateTime()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            	else if(co.getTrntype().equals("A"))//transfering money to other terminal
            	{
            			//ps=conn.prepareStatement("insert into DayCash(scroll_no,trn_date,ac_type,ac_no,trn_type,oth_tml,csh_amt,comm_amt,locker_type,name,run_bal,cd_ind,trn_seq,attached,de_user,de_tml,de_date,r1000,r500,r100,r50,r20,r10,r5,r2,r1,rcoins,p1000,p500,p100,p50,p20,p10,p5,p2,p1,pcoins,tml_no,vch_type,vch_no) values("+scno+",'"+getClientDate()+"',?,?,?,?,?,?,?,?,?,?,?,'F',?,?,'"+getSysDateTime()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            			//ship.....added this to update the scroll no....05/10/2005
                        if(flag==1)
                        	ps=conn.prepareStatement("insert into DayCash(scroll_no,trn_date,ac_type,ac_no,trn_type,oth_tml,csh_amt,comm_amt,locker_type,name,run_bal,cd_ind,trn_seq,attached,de_user,de_tml,de_date,r1000,r500,r100,r50,r20,r10,r5,r2,r1,rcoins,p1000,p500,p100,p50,p20,p10,p5,p2,p1,pcoins,tml_no,vch_type,vch_no,share_category,cust_ac_type,cust_ac_no,cust_code,po_favour_name) values("+scno+",'"+getClientDate()+"',?,?,?,?,?,?,?,?,?,?,?,'F',?,?,'"+getSysDateTime()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        else
                        	ps=conn.prepareStatement("update DayCash set ac_type=?,ac_no=?,trn_type=?,oth_tml=?,csh_amt=?,comm_amt=?,locker_type=?,name=?,run_bal=?,cd_ind=?,trn_seq=?,attached='F',de_user=?,de_tml=?,de_date='"+getSysDateTime()+"',r1000=?,r500=?,r100=?,r50=?,r20=?,r10=?,r5=?,r2=?,r1=?,rcoins=?,p1000=?,p500=?,p100=?,p50=?,p20=?,p10=?,p5=?,p2=?,p1=?,pcoins=?,tml_no=?,vch_type=?,vch_no=?,share_category=?,cust_ac_type=?,cust_ac_no=?,cust_code=?,po_favour_name=? where scroll_no="+scno+" and trn_date='"+getClientDate()+"'");
                        //
            	}
            	
            	co.setScrollno(scno);
            	ps.setString(1,co.getAcctype());
            	ps.setString(2,co.getAccno());
            	ps.setString(3,co.getTrntype());
            	ps.setString(4,co.getOthtml());
            	ps.setDouble(5,co.getAmount());
            	ps.setDouble(6,co.getCommamt());
            	ps.setString(7,co.getLockertype());
            	ps.setString(8,co.getAccname());
            	ps.setDouble(9,run_bal);
            	ps.setString(10,co.getCdind());
            
            Statement stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //ship....26/10/2005......added ac_type & order by trn_seq
            ResultSet rs1=stmt1.executeQuery("select trn_seq from DayCash where ac_type='"+co.getAcctype()+"' and ac_no='"+co.getAccno()+"' order by trn_seq desc");
            //ship
            if(rs1.next())
            {
            	co.setTrnseq(rs1.getInt(1));
            	System.out.println("trn....seq ="+rs1.getInt(1));
            }
            
            //ship.......16/01/2006
            if(flag==1)
                ps.setInt(11,0); 
            else
                ps.setInt(11,co.getTrnseq());
            //
            ps.setString(12,co.uv.getUserId());
            ps.setString(13,co.uv.getUserTml());
            
            ps.setInt(14,co.getR1000());
            ps.setInt(15,co.getR500());
            ps.setInt(16,co.getR100());
            ps.setInt(17,co.getR50());
            ps.setInt(18,co.getR20());
            ps.setInt(19,co.getR10());
            ps.setInt(20,co.getR5());
            ps.setInt(21,co.getR2());
            ps.setInt(22,co.getR1());
            ps.setDouble(23,co.getRcoins());
            
            ps.setInt(24,co.getP1000());
            ps.setInt(25,co.getP500());
            ps.setInt(26,co.getP100());
            ps.setInt(27,co.getP50());
            ps.setInt(28,co.getP20());
            ps.setInt(29,co.getP10());
            ps.setInt(30,co.getP5());
            ps.setInt(31,co.getP2());
            ps.setInt(32,co.getP1());
            ps.setDouble(33,co.getPcoins());
            
            if(co.getTrntype().equals("P"))
                ps.setInt(34,co.getP1());
            
            ps.setString(34,co.getTerminalNo());
            ps.setString(35,co.getVchtype());
            ps.setInt(36,vchno);
            ps.setInt(37,co.getSharecat());
            ps.setString(38,co.getCustAcctype());
            ps.setString(39,co.getCustAccno());
            ps.setString(40,co.getCustCode());
            ps.setString(41,co.getPOName());
            
            s=ps.executeUpdate();
            
            System.out.println("vch_no"+co.getVchno());
            System.out.println("vch_type"+co.getVchtype());
            System.out.println("trn_type"+co.getTrntype());
            System.out.println("token no"+co.getTokenno());
           
            if(co.getTrntype().equals("P") && co.getVchno()>0 && co.getVchtype().equals("I"))
            {
            	System.out.println("only for FD");
            	if(stmt.executeUpdate("update DayCash set scroll_no="+scno+",run_bal="+run_bal+" where trn_date='"+getClientDate()+"' and trn_type='P' and cd_ind='C' and vch_type='I' and vch_no="+co.getVchno()+" ")==0)
            		throw new SQLException();
            }
            if(co.getTrntype().equals("P") && co.getVchno()>0 && co.getVchtype().equals("C"))
            {
            	System.out.println("only for SH");
            	if(stmt.executeUpdate("update DayCash set scroll_no="+scno+",run_bal="+run_bal+" where trn_date='"+getClientDate()+"' and trn_type='P' and cd_ind='C' and vch_type='C' and vch_no="+co.getVchno()+" ")==0)
            		throw new SQLException();
            }
            if(co.getScrollno()!=copy_scno)
            {
                //while(true)
                //{ 
                if(stmt.executeUpdate("update Modules set lst_acc_no=lst_acc_no+1 where modulecode='1019000'")==0)
                    //System.out.println("1");
                    throw new SQLException();
                //}
                
                //	else
                //System.out.println("&&&&&&&&&&&& inside cash bean line 556 &&&&&&&&&&&&&&&&&");
                
            }
            if(co.getVchno()==-1)
            { 
                if(stmt.executeUpdate("update Modules set lst_rct_no=lst_rct_no+1 where modulecode='1019000'")==0)
                    throw new SQLException();
            }
            if((co.getTrntype().equals("P") && co.getTokenno()!=0))
            {
            	System.out.println("\n");
            	System.out.println("exclusively for general paymen");
                System.out.println("usertml=="+co.uv.getUserTml());
                System.out.println("scno=="+scno);
                System.out.println("Tokenno=="+co.getTokenno());
                
                ResultSet rs3=stmt.executeQuery("select chq_no from ChequeWithdrawal  where cash_pd='F' and trn_date='"+getClientDate()+"' and tml_no is null and token_no="+co.getTokenno());
                int chequeno=0;
                if(rs3.next())
                    chequeno=rs3.getInt(1);
                System.out.println("chequeno=="+chequeno);
                
                if(stmt.executeUpdate("update ChequeWithdrawal set cash_pd='T',tml_no='"+co.uv.getUserTml()+"'  where cash_pd='F' and trn_date='"+getClientDate()+"' and tml_no is null and token_no="+co.getTokenno()+"")==0)
                    throw new SQLException();
                System.out.println("after cheque withdrawal....");
                if(stmt.executeUpdate("update AccountTransaction set trn_source='"+co.uv.getUserTml()+"',ref_no="+scno+" where trn_date='"+getClientDate()+"' and chq_dd_no="+chequeno+" and trn_source is null and trn_narr like '%"+String.valueOf(co.getTokenno())+"'")==0)
                    throw new SQLException();
                System.out.println("after account transaction....");
                
                //ship......25/10/2005
                ResultSet rs4=stmt.executeQuery("select trn_seq from AccountTransaction  where trn_date='"+getClientDate()+"' and chq_dd_no="+chequeno+" and ac_type="+co.getAcctype()+" and ac_no="+co.getAccno()+"");
                rs4.next();
                if(stmt.executeUpdate("update AccountMaster set last_tr_seq="+rs4.getInt(1)+",last_tr_date='"+getClientDate()+"' where ac_type="+co.getAcctype()+" and ac_no="+co.getAccno()+"")==0)
                    throw new SQLException();
                if(stmt.executeUpdate("update DayCash set trn_seq="+rs4.getInt(1)+" where ac_type="+co.getAcctype()+" and ac_no="+co.getAccno()+" and scroll_no="+scno+"")==0)
                    throw new SQLException();
                //
                System.out.println("after account master.....");
                ResultSet rs=null;
                GLTransObject trnobj=new GLTransObject();
                
                // credit to Cash GL
                rs = stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1019001' and code=1");
                rs.next();
                trnobj.setGLCode(rs.getString("gl_code"));
                trnobj.setCdind("C");
                trnobj.setTrnDate(co.getTrndate());
                trnobj.setGLType(rs.getString("gl_type"));
                trnobj.setTrnMode("C");
                trnobj.setAmount(co.getAmount());
                trnobj.setCdind(co.getCdind());
                trnobj.setAccType("1019001");
                trnobj.setAccNo(null);
                trnobj.setTrnSeq(co.getTrnseq());
                trnobj.setTrnType(co.getTrntype());
                trnobj.setRefNo(co.getScrollno());
                trnobj.setVtml(co.uv.getVerTml());
                trnobj.setVid(co.uv.getVerId());
                
                if(common_local.storeGLTransaction(trnobj)==1)
                {	    			
                    //debit to PayOrder GL if it is PO
                    if(co.getVchtype().equals("PO"))
                    {	
                        rs=stmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type='1999002'");
                        rs.next();
                        trnobj.setGLCode(rs.getString(1));
                        trnobj.setCdind("D");
                        trnobj.setTrnDate(co.getTrndate());
                        trnobj.setGLType(rs.getString(2));
                        trnobj.setTrnMode("C");
                        trnobj.setAmount(co.getAmount());
                        trnobj.setCdind("D");
                        trnobj.setAccType("1999002");
                        trnobj.setAccNo(null);
                        trnobj.setTrnSeq(co.getTrnseq());
                        trnobj.setTrnType(co.getTrntype());
                        trnobj.setRefNo(co.getScrollno());
                        trnobj.setVtml(co.uv.getVerTml());
                        trnobj.setVid(co.uv.getVerId());
                        //int a=common_local.storeGLTransaction(trnobj);
                        common_local.storeGLTransaction(trnobj);
                        System.out.println("inside ac payorder");
                    }	
                    
                    // posting debit to SBGL if it is not PayOrder
                    if(!co.getVchtype().equals("PO"))
                    {
                        rs=stmt.executeQuery("select (custtype+1) from CustomerMaster cm,AccountMaster am where am.ac_type='"+co.getAcctype()+"' and am.ac_no="+co.getAccno()+" and cm.cid=am.cid");
                        rs.next();
                        int custtype=rs.getInt(1);
                        
                        rs=stmt.executeQuery("select gp.gl_code,gl_type from GLPost gp,GLKeyParam gk where gk.ac_type='"+co.getAcctype()+"' and gp.ac_type='"+co.getAcctype()+"' and gk.code="+custtype+" and trn_type='"+co.getTrntype()+"' and cr_dr='D'");
                        if(rs.next())
                        {
                            System.out.println("inside ac tran");
                            trnobj.setGLCode(rs.getString(1));
                            trnobj.setGLType(rs.getString(2));
                            trnobj.setAccType(co.getAcctype());
                            trnobj.setAccNo(co.getAccno());
                            trnobj.setCdind("D");
                            //int a=common_local.storeGLTransaction(trnobj);
                            common_local.storeGLTransaction(trnobj);
                        }
                    }
                }
            }
            ResultSet rs7=stmt.executeQuery("select lst_rct_no from Modules where modulecode='1019000'");
            if(rs7.next())
                System.out.println("vch no in 646"+rs7.getInt(1));
            
            ResultSet rs8=stmt.executeQuery("select vch_no from DayCash where scroll_no="+co.getScrollno()+" ");
            if(rs8.next())
                System.out.println("vch no in day cash for scroll no "+co.getScrollno()+" is "+rs8.getInt(1));
            System.out.println("vch no in object"+co.getVchno());
            //ship
            if (s == 1)
            {
                up=1;
            }
            //
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        catch(TerminalNotFoundException e)
        {
            sessionContext.setRollbackOnly();
            throw e;
        }
        catch(InsufficientAmountException e)
        {
            sessionContext.setRollbackOnly();
            throw e;
        }
        finally
        {
            conn.close();
        }
        if (up == 1)
            return scno;
        else        
            return 0;
    }*/
    
    /**
     * Insertion and Updation of Scroll No generated into DayCash and 
     * as well as into corresponding tables depending on the A/c types.
     */
    public int storeDayCash(CashObject co) throws SQLException,InsufficientAmountException,TerminalNotFoundException, InsufficientBalanceException
    {   
        Connection conn=null;
        PreparedStatement ps=null;
        int up=0,s=0;
        int scno=0,vchno=0;//ship
        
        Statement stmt_CS = null,stmt = null;
        ResultSet rs_CS = null;
        
        double run_bal = 0.0;
        
        try
		{
            conn = getConnection();
            
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt_CS = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           
            System.out.println("vch_no in storeDayCash from object"+co.getVchno());
            System.out.println("scroll_no in storeDayCash from object "+co.getScrollno());
            System.out.println("vch_no in storeDayCash from variable"+vchno);
            
            
            scno=co.getScrollno();
            System.out.println("---------------Scroll No"+scno);
            vchno=co.getVchno();//added for MiscellanousReceipts
            int copy_scno=scno;
            
            rs_CS = stmt_CS.executeQuery("select netamt from Currency_Stock where tml_no='"+co.getTerminalNo()+"' and rec_type='O' and cur_date='"+co.getTrndate()+"'");
            
            if(rs_CS.next())
                run_bal = rs_CS.getDouble("netamt");
            else 
                throw new TerminalNotFoundException();
            
            System.out.println("net amt before updation = "+run_bal+" in Terminal = "+co.getTerminalNo());
            
            int flag=0;//ship
            
            if(scno==0)
            {
            	flag=1;//ship
            	 System.out.println("...1");
                ResultSet rs2=null;
                
                if(co.getVchno()==-1)
                {
                	 System.out.println("*********inside if(co.getVchno()*******");
                                        
                    rs2=stmt.executeQuery("select lst_acc_no,lst_rct_no from Modules where modulecode='1019000'");//ship.....28/03/2006
                    
                    if(rs2.next())
                    {
                    	 System.out.println("scroll no before update"+rs2.getInt(1));
                        System.out.println("vch no before update"+rs2.getInt(2));
                    
                        scno=rs2.getInt(1)+1;			
                        vchno=rs2.getInt(2)+1;
                    }
                }
                else
                {
                    rs2=stmt.executeQuery("select lst_acc_no from Modules where modulecode='1019000'");//ship...28/03/2006
                    
                    if(rs2.next())
                        scno=rs2.getInt(1)+1;
                    
                    System.out.println("Scroll no from Modules = "+scno);
                }
                
                System.out.println("...2  othertmel"+co.getOthtml());
                
                if(co.getOthtml()!=null && co.getTrntype().equals("A")) //for intercounter transfer
                {
                	 System.out.println("other terminal &&&& =="+co.getOthtml());
                    
                    ResultSet rs3=stmt.executeQuery("select * from Currency_Stock where tml_no='"+co.getOthtml()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'");
                    
                    if(rs3.next())
                    {
                    	 System.out.println("Terminal is  open "+co.getOthtml());
                    	
                    	ResultSet rs4=stmt.executeQuery("select * from Currency_Stock where tml_no='"+co.getTerminalNo()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'");
                        
                    	if(rs4.next())
                    	{
                            if((rs4.getInt("s1000")>=((co.getR1000()-co.getP1000())!=0?co.getR1000()-co.getP1000():rs4.getInt("s1000"))) &&
                                    (rs4.getInt("s500")>=((co.getR500()-co.getP500())!=0?co.getR500()-co.getP500(): rs4.getInt("s500"))) &&
                                    (rs4.getInt("s100")>=((co.getR100()-co.getP100())!=0?co.getR100()-co.getP100():rs4.getInt("s100"))) &&
                                    (rs4.getInt("s50")>=((co.getR50()-co.getP50())!=0?co.getR50()-co.getP50():rs4.getInt("s50"))) && 
                                    (rs4.getInt("s20")>=((co.getR20()-co.getP20())!=0?co.getR20()-co.getP20():rs4.getInt("s20"))) &&
                                    (rs4.getInt("s10")>=((co.getR10()-co.getP10())!=0?co.getR10()-co.getP10():rs4.getInt("s10"))) &&
                                    (rs4.getInt("s5")>=((co.getR5()-co.getP5())!=0?co.getR5()-co.getP5():rs4.getInt("s5"))) &&
                                    (rs4.getInt("s2")>=((co.getR2()-co.getP2())!=0?co.getR2()-co.getP2():rs4.getInt("s2"))) &&
                                    (rs4.getInt("s1")>=((co.getR1()-co.getP1())!=0?co.getR1()-co.getP1():rs4.getInt("s1"))) &&
                                    (rs4.getFloat("scoins")>=((co.getRcoins()-co.getPcoins())!=0?co.getRcoins()-co.getPcoins():rs4.getFloat("scoins"))))
                             {
                            	 System.out.println("INside the IF condition" );
                                    
                                  if((stmt.executeUpdate("update Currency_Stock set s1000=s1000-"+(co.getP1000()-co.getR1000())+",s500=s500-"+(co.getP500()-co.getR500())+",s100=s100-"+(co.getP100()-co.getR100())+",s50=s50-"+(co.getP50()-co.getR50())+",s20=s20-"+(co.getP20()-co.getR20())+",s10=s10-"+(co.getP10()-co.getR10())+",s5=s5-"+(co.getP5()-co.getR5())+",s2=s2-"+(co.getP2()-co.getR2())+",s1=s1-"+(co.getP1()-co.getR1())+",scoins=scoins-"+(co.getPcoins()-co.getRcoins())+",netamt=netamt-"+co.getAmount()+" where tml_no='"+co.getTerminalNo()+"' and cur_date='"+co.getTrndate()+"'"))==0)// subtract money from the terminal from where money is transferred
                                        throw new SQLException();
                              }
                              else 
                              {
                            	  System.out.println("INside the else condition" );
                                   System.out.println("co.isInsufficientFlag() == "+co.isInsufficientFlag());
                                      	
                                   if(co.isInsufficientFlag())
                                   {
                                        if((stmt.executeUpdate("update Currency_Stock set s1000=s1000-"+(co.getP1000()-co.getR1000())+",s500=s500-"+(co.getP500()-co.getR500())+",s100=s100-"+(co.getP100()-co.getR100())+",s50=s50-"+(co.getP50()-co.getR50())+",s20=s20-"+(co.getP20()-co.getR20())+",s10=s10-"+(co.getP10()-co.getR10())+",s5=s5-"+(co.getP5()-co.getR5())+",s2=s2-"+(co.getP2()-co.getR2())+",s1=s1-"+(co.getP1()-co.getR1())+",scoins=scoins-"+(co.getPcoins()-co.getRcoins())+",netamt=netamt-"+co.getAmount()+" where tml_no='"+co.getTerminalNo()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'"))==0)// subtract money from the terminal from where money is transferred
                                             throw new SQLException();
                                   }
                                   else
                                        throw new InsufficientAmountException();
                                       
                                   System.out.println("Before diplaying no currency denominations" );
                               }
                         }
                    }
                    else
                        throw new TerminalNotFoundException();
                }
                else if(!(co.getOthtml()!=null)) //for other transfers
                {
                	 System.out.println("for other transfers");
                	 System.out.println("trn_type "+co.getTrntype());
                	 System.out.println("cd ind "+co.getCdind());
                	 System.out.println("co.isInsufficientFlag() == "+co.isInsufficientFlag());
                	
                    //ship......02/02/2007
                    if(co.getCdind().equals("D"))
                    {
                        if(stmt.executeUpdate("update Currency_Stock set s1000=s1000+"+(co.getR1000()-co.getP1000())+",s500=s500+"+(co.getR500()-co.getP500())+",s100=s100+"+(co.getR100()-co.getP100())+",s50=s50+"+(co.getR50()-co.getP50())+",s20=s20+"+(co.getR20()-co.getP20())+",s10=s10+"+(co.getR10()-co.getP10())+",s5=s5+"+(co.getR5()-co.getP5())+",s2=s2+"+(co.getR2()-co.getP2())+",s1=s1+"+(co.getR1()-co.getP1())+",scoins=scoins+"+(co.getRcoins()-co.getPcoins())+",netamt=netamt+"+co.getAmount()+",de_user='"+co.uv.getUserId()+"',de_tml='"+co.uv.getUserTml()+"',de_date='"+co.getTrndate()+"'"+" "+"'"+co.getTrntime()+"' where tml_no='"+co.getTerminalNo()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'")==0)
                            throw new SQLException();
                    }
                    else if(co.getCdind().equals("C"))
                    {
                        if(stmt.executeUpdate("update Currency_Stock set s1000=s1000-"+(co.getR1000()-co.getP1000())+",s500=s500-"+(co.getR500()-co.getP500())+",s100=s100-"+(co.getR100()-co.getP100())+",s50=s50-"+(co.getR50()-co.getP50())+",s20=s20-"+(co.getR20()-co.getP20())+",s10=s10-"+(co.getR10()-co.getP10())+",s5=s5-"+(co.getR5()-co.getP5())+",s2=s2-"+(co.getR2()-co.getP2())+",s1=s1-"+(co.getR1()-co.getP1())+",scoins=scoins-"+(co.getRcoins()-co.getPcoins())+",netamt=netamt-"+co.getAmount()+",de_user='"+co.uv.getUserId()+"',de_tml='"+co.uv.getUserTml()+"',de_date='"+co.getTrndate()+"'"+" "+"'"+co.getTrntime()+"' where tml_no='"+co.getTerminalNo()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'")==0)
                            throw new SQLException();
                    }
                    //////////////
                    
                	/*if(co.getCdind().equals("D"))
                	{
                		file_logger.info("Before Settin in CurrencyStock for terminal "+co.uv.getUserTml());
                		
                		if(stmt.executeUpdate("update Currency_Stock set s1000=s1000+"+(co.getR1000()-co.getP1000())+",s500=s500+"+(co.getR500()-co.getP500())+",s100=s100+"+(co.getR100()-co.getP100())+",s50=s50+"+(co.getR50()-co.getP50())+",s20=s20+"+(co.getR20()-co.getP20())+",s10=s10+"+(co.getR10()-co.getP10())+",s5=s5+"+(co.getR5()-co.getP5())+",s2=s2+"+(co.getR2()-co.getP2())+",s1=s1+"+(co.getR1()-co.getP1())+",scoins=scoins+"+(co.getRcoins()-co.getPcoins())+",netamt=netamt+"+co.getAmount()+",de_user='"+co.uv.getUserId()+"',de_tml='"+co.uv.getUserTml()+"',de_date='"+co.getTrndate()+"'"+" "+"'"+co.getTrntime()+"' where tml_no='"+co.uv.getUserTml()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'")==0)
                			throw new SQLException();
                	}
                	else if(co.getCdind().equals("C"))
                	{
                		if(stmt.executeUpdate("update Currency_Stock set s1000=s1000-"+(co.getR1000()-co.getP1000())+",s500=s500-"+(co.getR500()-co.getP500())+",s100=s100-"+(co.getR100()-co.getP100())+",s50=s50-"+(co.getR50()-co.getP50())+",s20=s20-"+(co.getR20()-co.getP20())+",s10=s10-"+(co.getR10()-co.getP10())+",s5=s5-"+(co.getR5()-co.getP5())+",s2=s2-"+(co.getR2()-co.getP2())+",s1=s1-"+(co.getR1()-co.getP1())+",scoins=scoins-"+(co.getRcoins()-co.getPcoins())+",netamt=netamt-"+co.getAmount()+",de_user='"+co.uv.getUserId()+"',de_tml='"+co.uv.getUserTml()+"',de_date='"+co.getTrndate()+"'"+" "+"'"+co.getTrntime()+"' where tml_no='"+co.uv.getUserTml()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'")==0)
                			throw new SQLException();
                	}*/
                }
                
                System.out.println("trn_type "+co.getTrntype());
                
                /*if(co.getTrntype().equals("A"))
                {
                    file_logger.info("trntype = A");
                    
                    if(run_bal >= co.getAmount())
                        run_bal = run_bal-co.getAmount();
                    else
                        throw new InsufficientBalanceException();
                }*/
                if(co.getCdind().equals("D"))
                {
                	 System.out.println("trntype = R");
                    
                    run_bal = run_bal+co.getAmount();
                }
                else if(co.getCdind().equals("C"))
                {
                	 System.out.println("trntype = P");
                	
                    if(run_bal >= co.getAmount())
                        run_bal = run_bal-co.getAmount();
                    else
                        throw new InsufficientBalanceException();
                }
            }
            else //for already existing scroll nos.
            {
            	 System.out.println("*************438****************");
                
                //ship.....altered on 05/10/2005
                if(co.getOthtml()!=null) //for intercounter transfer
                {
                    ResultSet rs = stmt.executeQuery("select * from DayCash where scroll_no="+scno+" and ve_tml is null and trn_date='"+co.getTrndate()+"'");
                
                    /*if(rs.next())
                    {
                        file_logger.info("scroll no inside if= "+scno);
                    	PreparedStatement ps1=null;		
                        ps1=conn.prepareStatement("update DayCash set ac_type="+co.getAcctype()+",ac_no="+co.getAccno()+",name='"+co.getAccname()+"',csh_amt=?,trn_type=?,run_bal=?,r1000=?,p1000=?,r500=?,p500=?,r100=?,p100=?,r50=?,p50=?,r20=?,p20=?,r10=?,p10=?,r5=?,p5=?,r2=?,p2=?,r1=?,p1=?,rcoins=?,pcoins=?,de_user=?,de_tml=? where scroll_no="+scno+" and trn_date='"+getClientDate()+"'" );		
                        ps1.setDouble(1,co.getAmount());
                        ps1.setString(2,"A");
                        ps1.setDouble(3,run_bal);
                        
                        ps1.setInt(4,co.getR1000());
                        ps1.setInt(5,co.getP1000());
                        ps1.setInt(6,co.getR500());
                        ps1.setInt(7,co.getP500());
                        ps1.setInt(8,co.getR100());
                        ps1.setInt(9,co.getP100());
                        ps1.setInt(10,co.getR50());
                        ps1.setInt(11,co.getP50());
                        ps1.setInt(12,co.getR20());
                        ps1.setInt(13,co.getP20());
                        ps1.setInt(14,co.getR10());
                        ps1.setInt(15,co.getP10());
                        ps1.setInt(16,co.getR5());
                        ps1.setInt(17,co.getP5());
                        ps1.setInt(18,co.getR2());
                        ps1.setInt(19,co.getP2());
                        ps1.setInt(20,co.getR1());
                        ps1.setInt(21,co.getP1());
                        ps1.setDouble(22,co.getRcoins());
                        ps1.setDouble(23,co.getPcoins());
                        ps1.setString(24,co.uv.getUserId());
                        ps1.setString(25,co.uv.getUserTml());
                        
                        file_logger.info("Updation"+ps1.executeUpdate());
                        file_logger.info(scno);
                        file_logger.info(scno);*/
                    
                    
                        
                    if(rs.next())
                    {
                        if(stmt.executeUpdate("update Currency_Stock set s1000=s1000-"+((co.getP1000()-co.getR1000())-(rs.getInt("p1000")-rs.getInt("r1000")))+",s500=s500-"+((co.getP500()-co.getR500())-(rs.getInt("p500")-rs.getInt("r500")))+",s100=s100-"+((co.getP100()-co.getR100())-(rs.getInt("p100")-rs.getInt("r100")))+",s50=s50-"+((co.getP50()-co.getR50())-(rs.getInt("p50")-rs.getInt("r50")))+",s20=s20-"+((co.getP20()-co.getR20())-(rs.getInt("p20")-rs.getInt("r20")))+",s10=s10-"+((co.getP10()-co.getR10())-(rs.getInt("p10")-rs.getInt("r10")))+",s5=s5-"+((co.getP5()-co.getR5())-(rs.getInt("p5")-rs.getInt("r5")))+",s2=s2-"+((co.getP2()-co.getR2())-(rs.getInt("p2")-rs.getInt("r2")))+",s1=s1-"+((co.getP1()-co.getR1())-(rs.getInt("p1")-rs.getInt("r1")))+",scoins=scoins-"+((co.getPcoins()-co.getRcoins())-(rs.getInt("pcoins")-rs.getInt("rcoins")))+",netamt=netamt-"+(co.getAmount()-rs.getFloat("csh_amt"))+",de_user='"+co.uv.getUserId()+"',de_tml='"+co.uv.getUserTml()+"',de_date='"+co.getTrndate()+"'"+" "+"'"+co.getTrntime()+"' where tml_no='"+co.getTerminalNo()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'")==0)
                            throw new SQLException();
                        
                        if(run_bal >= (co.getAmount()-rs.getFloat("csh_amt")))
                            run_bal = run_bal-(co.getAmount()-rs.getFloat("csh_amt"));
                        else
                            throw new InsufficientBalanceException();
                    }
                    //}
                }
                else//for other transfers
                {
                    ResultSet rs=stmt.executeQuery("select * from DayCash where scroll_no="+scno+" and ve_tml is null and trn_date='"+co.getTrndate()+"'");
                    
                    /*if(rs.next())
                    {
                    	file_logger.info("scroll no inside if= "+scno);
                    	PreparedStatement ps1=null;		
                        ps1=conn.prepareStatement("update DayCash set ac_type="+co.getAcctype()+",ac_no="+co.getAccno()+",name='"+co.getAccname()+"',csh_amt=?,trn_type=?,run_bal=?,r1000=?,p1000=?,r500=?,p500=?,r100=?,p100=?,r50=?,p50=?,r20=?,p20=?,r10=?,p10=?,r5=?,p5=?,r2=?,p2=?,r1=?,p1=?,rcoins=?,pcoins=?,de_user=?,de_tml=? where scroll_no="+scno+" and trn_date='"+getClientDate()+"'" );		
                        ps1.setDouble(1,co.getAmount());
                        ps1.setString(2,"R");
                        ps1.setDouble(3,run_bal);
                        
                        ps1.setInt(4,co.getR1000());
                        ps1.setInt(5,co.getP1000());
                        ps1.setInt(6,co.getR500());
                        ps1.setInt(7,co.getP500());
                        ps1.setInt(8,co.getR100());
                        ps1.setInt(9,co.getP100());
                        ps1.setInt(10,co.getR50());
                        ps1.setInt(11,co.getP50());
                        ps1.setInt(12,co.getR20());
                        ps1.setInt(13,co.getP20());
                        ps1.setInt(14,co.getR10());
                        ps1.setInt(15,co.getP10());
                        ps1.setInt(16,co.getR5());
                        ps1.setInt(17,co.getP5());
                        ps1.setInt(18,co.getR2());
                        ps1.setInt(19,co.getP2());
                        ps1.setInt(20,co.getR1());
                        ps1.setInt(21,co.getP1());
                        ps1.setDouble(22,co.getRcoins());
                        ps1.setDouble(23,co.getPcoins());
                        ps1.setString(24,co.uv.getUserId());
                        ps1.setString(25,co.uv.getUserTml());
                        
                        file_logger.info("Updation"+ps1.executeUpdate());
                        file_logger.info(scno);
                        file_logger.info(scno);*/
                        
                    if(rs.next())
                    {
                        if(stmt.executeUpdate("update Currency_Stock set s1000=s1000+"+((co.getR1000()-co.getP1000())-(rs.getInt("r1000")-rs.getInt("p1000")))+",s500=s500+"+((co.getR500()-co.getP500())-(rs.getInt("r500")-rs.getInt("p500")))+",s100=s100+"+((co.getR100()-co.getP100())-(rs.getInt("r100")-rs.getInt("p100")))+",s50=s50+"+((co.getR50()-co.getP50())-(rs.getInt("r50")-rs.getInt("p50")))+",s20=s20+"+((co.getR20()-co.getP20())-(rs.getInt("r20")-rs.getInt("p20")))+",s10=s10+"+((co.getR10()-co.getP10())-(rs.getInt("r10")-rs.getInt("p10")))+",s5=s5+"+((co.getR5()-co.getP5())-(rs.getInt("r5")-rs.getInt("p5")))+",s2=s2+"+((co.getR2()-co.getP2())-(rs.getInt("r2")-rs.getInt("p2")))+",s1=s1+"+((co.getR1()-co.getP1())-(rs.getInt("r1")-rs.getInt("p1")))+",scoins=scoins+"+((co.getRcoins()-co.getPcoins())-(rs.getInt("rcoins")-rs.getInt("pcoins")))+",netamt=netamt+"+(co.getAmount()-rs.getFloat("csh_amt"))+",de_user='"+co.uv.getUserId()+"',de_tml='"+co.uv.getUserTml()+"',de_date='"+co.getTrndate()+"'"+" "+"'"+co.getTrntime()+"' where tml_no='"+co.getTerminalNo()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'")==0)
                            throw new SQLException();
                        
                        if(rs.getString("cd_ind").equals("D"))
                        {
                        	 System.out.println("trntype = R");
                            
                            run_bal = run_bal+(co.getAmount()-rs.getFloat("csh_amt"));
                        }
                        else if(rs.getString("cd_ind").equals("C"))
                        {
                        	 System.out.println("trntype = P");
                        	
                            if(run_bal >= (co.getAmount()-rs.getFloat("csh_amt")))
                                run_bal = run_bal-(co.getAmount()-rs.getFloat("csh_amt"));
                            else
                                throw new InsufficientBalanceException();
                        }
                    }
                }	
            }
            
            System.out.println("Scroollll no "+scno+"TrnDate"+co.getTrndate());
           
                       
            if(co.getCdind().equals("D"))
        	{
        		//ship.....added this to update the scroll no.
            	System.out.println("Flag====>"+flag);
                if(flag==1)
                {
                	
                	ps=conn.prepareStatement("insert into DayCash(scroll_no,trn_date,ac_type,ac_no,trn_type,oth_tml,csh_amt,comm_amt,locker_type,name,run_bal,cd_ind,trn_seq,attached,de_user,de_tml,de_date,r1000,r500,r100,r50,r20,r10,r5,r2,r1,rcoins,p1000,p500,p100,p50,p20,p10,p5,p2,p1,pcoins,tml_no,vch_type,vch_no,share_category,cust_ac_type,cust_ac_no,cust_code,po_favour_name) values("+scno+",'"+co.getTrndate()+"',?,?,?,?,?,?,?,?,?,?,?,'F',?,?,'"+co.uv.getUserDate()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                }             
                else
                	ps=conn.prepareStatement("update DayCash set ac_type=?,ac_no=?,trn_type=?,oth_tml=?,csh_amt=?,comm_amt=?,locker_type=?,name=?,run_bal=?,cd_ind=?,trn_seq=?,de_user=?,de_tml=?,de_date='"+co.getTrndate()+"'"+" "+"'"+co.getTrntime()+"',r1000=?,r500=?,r100=?,r50=?,r20=?,r10=?,r5=?,r2=?,r1=?,rcoins=?,p1000=?,p500=?,p100=?,p50=?,p20=?,p10=?,p5=?,p2=?,p1=?,pcoins=?,tml_no=?,vch_type=?,vch_no=?,share_category=?,cust_ac_type=?,cust_ac_no=?,cust_code=?,po_favour_name=? where scroll_no="+scno+" and trn_date='"+co.getTrndate()+"'");
                //
        	}
        	else if(co.getCdind().equals("C") && (co.getVchno()<=0) && co.getOthtml()==null)
        	{
        		 System.out.println("only for SB... Payments");
        		ps=conn.prepareStatement("insert into DayCash(scroll_no,trn_date,ac_type,ac_no,trn_type,oth_tml,csh_amt,comm_amt,locker_type,name,run_bal,cd_ind,trn_seq,attached,de_user,de_tml,de_date,r1000,r500,r100,r50,r20,r10,r5,r2,r1,rcoins,p1000,p500,p100,p50,p20,p10,p5,p2,p1,pcoins,tml_no,vch_type,token_no,share_category,cust_ac_type,cust_ac_no,cust_code,po_favour_name) values("+scno+",'"+co.getTrndate()+"',?,?,?,?,?,?,?,?,?,?,?,'F',?,?,'"+co.uv.getUserDate()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        	}
        	else if(co.getCdind().equals("C") && (co.getVchtype()!=null && co.getVchtype().equals("P")) && (co.getVchno()>0))
        	{
        		 System.out.println("only for Vouchers... Payments");
        		ps=conn.prepareStatement("insert into DayCash(scroll_no,trn_date,ac_type,ac_no,trn_type,oth_tml,csh_amt,comm_amt,locker_type,name,run_bal,cd_ind,trn_seq,attached,de_user,de_tml,de_date,r1000,r500,r100,r50,r20,r10,r5,r2,r1,rcoins,p1000,p500,p100,p50,p20,p10,p5,p2,p1,pcoins,tml_no,vch_type,vch_no,share_category,cust_ac_type,cust_ac_no,cust_code,po_favour_name) values("+scno+",'"+co.getTrndate()+"',?,?,?,?,?,?,?,?,?,?,?,'T',?,?,'"+co.uv.getUserDate()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        	}
        	else if(co.getTrntype().equals("A") && co.getOthtml()!=null)//transfering money to other terminal
        	{
        		//ship.....added this to update the scroll no....05/10/2005
                if(flag==1)
                    ps=conn.prepareStatement("insert into DayCash(scroll_no,trn_date,ac_type,ac_no,trn_type,oth_tml,csh_amt,comm_amt,locker_type,name,run_bal,cd_ind,trn_seq,attached,de_user,de_tml,de_date,r1000,r500,r100,r50,r20,r10,r5,r2,r1,rcoins,p1000,p500,p100,p50,p20,p10,p5,p2,p1,pcoins,tml_no,vch_type,vch_no,share_category,cust_ac_type,cust_ac_no,cust_code,po_favour_name) values("+scno+",'"+co.getTrndate()+"',?,?,?,?,?,?,?,?,?,?,?,'F',?,?,'"+co.uv.getUserDate()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                else
                    ps=conn.prepareStatement("update DayCash set ac_type=?,ac_no=?,trn_type=?,oth_tml=?,csh_amt=?,comm_amt=?,locker_type=?,name=?,run_bal=?,cd_ind=?,trn_seq=?,attached='F',de_user=?,de_tml=?,de_date='"+co.getTrndate()+"'"+" "+"'"+co.getTrntime()+"',r1000=?,r500=?,r100=?,r50=?,r20=?,r10=?,r5=?,r2=?,r1=?,rcoins=?,p1000=?,p500=?,p100=?,p50=?,p20=?,p10=?,p5=?,p2=?,p1=?,pcoins=?,tml_no=?,vch_type=?,vch_no=?,share_category=?,cust_ac_type=?,cust_ac_no=?,cust_code=?,po_favour_name=? where scroll_no="+scno+" and trn_date='"+co.getTrndate()+"'");
                //
        	}
        	
        	co.setScrollno(scno);
        	
        	ps.setString(1,co.getAcctype());
        	ps.setString(2,co.getAccno());
        	ps.setString(3,co.getTrntype());
        	ps.setString(4,co.getOthtml());
        	ps.setDouble(5,co.getAmount());
        	ps.setDouble(6,co.getCommamt());
        	ps.setString(7,co.getLockertype());
        	ps.setString(8,co.getAccname());
        	ps.setDouble(9,run_bal);
        	ps.setString(10,co.getCdind());
            
            Statement stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //ship....26/10/2005......added ac_type & order by trn_seq
            ResultSet rs1=stmt1.executeQuery("select trn_seq from DayCash where ac_type='"+co.getAcctype()+"' and ac_no='"+co.getAccno()+"' order by trn_seq desc");
            //ship
            if(rs1.next())
            {
            	co.setTrnseq(rs1.getInt(1));
            	//file_logger.info("trn....seq ="+rs1.getInt(1));
            }
            
            //ship.......16/01/2006
            /*if(flag==1)
                ps.setInt(11,0); 
            else
                ps.setInt(11,co.getTrnseq());*/
            
            ps.setInt(11,0);//trn_seq
            //
            ps.setString(12,co.uv.getUserId());
            ps.setString(13,co.uv.getUserTml());
            
            ps.setInt(14,co.getR1000());
            ps.setInt(15,co.getR500());
            ps.setInt(16,co.getR100());
            ps.setInt(17,co.getR50());
            ps.setInt(18,co.getR20());
            ps.setInt(19,co.getR10());
            ps.setInt(20,co.getR5());
            ps.setInt(21,co.getR2());
            ps.setInt(22,co.getR1());
            ps.setDouble(23,co.getRcoins());
            
            ps.setInt(24,co.getP1000());
            ps.setInt(25,co.getP500());
            ps.setInt(26,co.getP100());
            ps.setInt(27,co.getP50());
            ps.setInt(28,co.getP20());
            ps.setInt(29,co.getP10());
            ps.setInt(30,co.getP5());
            ps.setInt(31,co.getP2());
            ps.setInt(32,co.getP1());
            ps.setDouble(33,co.getPcoins());
            
            if(co.getCdind().equals("C"))
                ps.setInt(34,co.getP1());
            
            ps.setString(34,co.getTerminalNo());
            ps.setString(35,co.getVchtype());
            ps.setInt(36,vchno);
            ps.setInt(37,co.getSharecat());
            ps.setString(38,co.getCustAcctype());
            ps.setString(39,co.getCustAccno());
            ps.setString(40,co.getCustCode());
            ps.setString(41,co.getPOName());
            
            s=ps.executeUpdate();
            System.out.println("s======>"+s);
            System.out.println("vch_no"+co.getVchno());
            System.out.println("vch_type"+co.getVchtype());
            System.out.println("trn_type"+co.getTrntype());
            System.out.println("token no"+co.getTokenno());
           
            if(co.getCdind().equals("C") && co.getVchno()>0 && co.getVchtype().equals("I"))
            {
            	 System.out.println("only for FD");
            	if(stmt.executeUpdate("update DayCash set scroll_no="+scno+",run_bal="+run_bal+" where trn_date='"+co.getTrndate()+"' and cd_ind='C' and vch_type='I' and vch_no="+co.getVchno()+" ")==0)
            		throw new SQLException();
            }
            if(co.getCdind().equals("C") && co.getVchno()>0 && co.getVchtype().equals("C"))
            {
            	 System.out.println("only for SH");
            	if(stmt.executeUpdate("update DayCash set scroll_no="+scno+",run_bal="+run_bal+" where trn_date='"+co.getTrndate()+"' and cd_ind='C' and vch_type='C' and vch_no="+co.getVchno()+" ")==0)
            		throw new SQLException();
            }
            if(co.getScrollno()!=copy_scno)
            {
                if(stmt.executeUpdate("update Modules set lst_acc_no=lst_acc_no+1 where modulecode='1019000'")==0)
                    //file_logger.info("1");
                    throw new SQLException();
            }
            if(co.getVchno()==-1)
            { 
                if(stmt.executeUpdate("update Modules set lst_rct_no=lst_rct_no+1 where modulecode='1019000'")==0)
                    throw new SQLException();
            }
            if((co.getCdind().equals("C") && co.getTokenno()!=0))
            {
            	System.out.println("\n");
            	System.out.println("exclusively for general paymen");
            	System.out.println("usertml=="+co.uv.getUserTml());
                System.out.println("scno=="+scno);
                System.out.println("Tokenno=="+co.getTokenno());
                
                ResultSet rs3=stmt.executeQuery("select chq_no from ChequeWithdrawal  where cash_pd='F' and trn_date='"+co.getTrndate()+"' and tml_no is null and token_no="+co.getTokenno());
                int chequeno=0;
                if(rs3.next())
                    chequeno=rs3.getInt(1);
                System.out.println("chequeno=="+chequeno);
                
                if(stmt.executeUpdate("update ChequeWithdrawal set cash_pd='T',tml_no='"+co.uv.getUserTml()+"'  where cash_pd='F' and trn_date='"+co.getTrndate()+"' and tml_no is null and token_no="+co.getTokenno()+"")==0)
                    throw new SQLException();
                System.out.println("after cheque withdrawal....");
                if(stmt.executeUpdate("update AccountTransaction set trn_source='"+co.uv.getUserTml()+"',ref_no="+scno+" where trn_date='"+co.getTrndate()+"' and chq_dd_no="+chequeno+" and trn_source is null and trn_narr like '%"+String.valueOf(co.getTokenno())+"'")==0)
                    throw new SQLException();
                System.out.println("after account transaction....");
                
                //ship......25/10/2005
                ResultSet rs4=stmt.executeQuery("select trn_seq from AccountTransaction  where trn_date='"+co.getTrndate()+"' and chq_dd_no="+chequeno+" and ac_type="+co.getAcctype()+" and ac_no="+co.getAccno()+"");
                rs4.next();
                if(stmt.executeUpdate("update AccountMaster set last_tr_seq="+rs4.getInt(1)+",last_tr_date='"+co.getTrndate()+"' where ac_type="+co.getAcctype()+" and ac_no="+co.getAccno()+"")==0)
                    throw new SQLException();
                if(stmt.executeUpdate("update DayCash set trn_seq="+rs4.getInt(1)+" where ac_type="+co.getAcctype()+" and ac_no="+co.getAccno()+" and scroll_no="+scno+" and trn_date='"+co.getTrndate()+"'")==0)
                    throw new SQLException();
                //
                System.out.println("after account master.....");
                ResultSet rs=null;
                GLTransObject trnobj=new GLTransObject();
                
                // credit to Cash GL
                rs = stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1019001' and code=1");
                
                if(rs.next())
                {
                    trnobj.setGLCode(rs.getString("gl_code"));
                    trnobj.setCdind("C");
                    trnobj.setTrnDate(co.getTrndate());
                    trnobj.setGLType(rs.getString("gl_type"));
                    trnobj.setTrnMode("C");
                    trnobj.setAmount(co.getAmount());
                    trnobj.setCdind(co.getCdind());
                    trnobj.setAccType("1019001");
                    trnobj.setAccNo(null);
                    trnobj.setTrnSeq(co.getTrnseq());
                    trnobj.setTrnType(co.getTrntype());
                    trnobj.setRefNo(co.getScrollno());
                    trnobj.setVtml(co.uv.getVerTml());
                    trnobj.setVid(co.uv.getVerId());
                    trnobj.setVDate(co.uv.getVerDate());
                    
                    if(common_local.storeGLTransaction(trnobj)==1)
                    {	    			
                        //debit to PayOrder GL if it is PO
                        if(co.getVchtype().equals("PO"))
                        {	
                            rs=stmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type='1999002'");
                            rs.next();
                            trnobj.setGLCode(rs.getString(1));
                            trnobj.setCdind("D");
                            trnobj.setTrnDate(co.getTrndate());
                            trnobj.setGLType(rs.getString(2));
                            trnobj.setTrnMode("C");
                            trnobj.setAmount(co.getAmount());
                            trnobj.setCdind("D");
                            trnobj.setAccType("1999002");
                            trnobj.setAccNo(null);
                            trnobj.setTrnSeq(co.getTrnseq());
                            trnobj.setTrnType(co.getTrntype());
                            trnobj.setRefNo(co.getScrollno());
                            trnobj.setVtml(co.uv.getVerTml());
                            trnobj.setVid(co.uv.getVerId());
                            trnobj.setVDate(co.uv.getVerDate());
                           
                            common_local.storeGLTransaction(trnobj);
                            System.out.println("inside ac payorder");
                        }	
                        
                        // posting debit to SBGL if it is not PayOrder
                        if(!co.getVchtype().equals("PO"))
                        {
                            rs=stmt.executeQuery("select (custtype+1) from CustomerMaster cm,AccountMaster am where am.ac_type='"+co.getAcctype()+"' and am.ac_no="+co.getAccno()+" and cm.cid=am.cid");
                            rs.next();
                            int custtype=rs.getInt(1);
                            
                            rs=stmt.executeQuery("select gp.gl_code,gl_type from GLPost gp,GLKeyParam gk where gk.ac_type='"+co.getAcctype()+"' and gp.ac_type='"+co.getAcctype()+"' and gk.code="+custtype+" and trn_type='"+co.getTrntype()+"' and cr_dr='D'");
                            if(rs.next())
                            {
                            	 System.out.println("inside ac tran");
                                trnobj.setGLCode(rs.getString(1));
                                trnobj.setGLType(rs.getString(2));
                                trnobj.setAccType(co.getAcctype());
                                trnobj.setAccNo(co.getAccno());
                                trnobj.setCdind("D");
                                
                                common_local.storeGLTransaction(trnobj);
                            }
                        }
                    }
                }
            }
            
            ResultSet rs7=stmt.executeQuery("select lst_rct_no from Modules where modulecode='1019000'");
            
            if(rs7.next())
            {
            	 System.out.println("vch no in 646"+rs7.getInt(1));
            }
            
            ResultSet rs8=stmt.executeQuery("select vch_no from DayCash where scroll_no="+co.getScrollno()+" and trn_date='"+co.getTrndate()+"'");
            
            if(rs8.next())
            	 System.out.println("vch no in day cash for scroll no "+co.getScrollno()+" is "+rs8.getInt(1));
            
            System.out.println("vch no in object"+co.getVchno());
       
            if (s == 1)
                up=1;
            
            System.out.println("net amt after updation = "+run_bal+" in Terminal = "+co.getTerminalNo()+"First Occurence"+up);
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        catch(TerminalNotFoundException e)
        {
        	 System.out.println("inside catch TerminalNotFoundException");
            sessionContext.setRollbackOnly();
            //throw e;//ship......08/12/2006
            return -1;
        }
        catch(InsufficientAmountException e)
        {
            sessionContext.setRollbackOnly();
            //throw e;
            return -2;
        }
        finally
        {
            conn.close();
        }
        
       System.out.println("store day cash before return ... up = "+up+"hghhgfgf"+scno);
     
        if (up == 1)
           return scno;
        
        else        
            return 0;
    }
    //////////////////
    
   /* public int storeGLTransaction(GLTransObject trnobj) throws SQLException
    {
        Connection conn=null;
        PreparedStatement pstmt=null;
        try{
            conn= getConnection();
            pstmt=conn.prepareStatement("insert into GLTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,'"+getClientDate()+"')");
            pstmt.setString(1,trnobj.getTrnDate());
            pstmt.setString(2,trnobj.getGLType());
            pstmt.setString(3,trnobj.getGLCode());
            pstmt.setString(4,trnobj.getTrnSource());
            pstmt.setDouble(5,trnobj.getAmount());
            pstmt.setString(6,trnobj.getCdind());
            pstmt.setString(7,trnobj.getAccType());
            pstmt.setString(8,trnobj.getAccNo());
            pstmt.setInt(9,trnobj.getTrnSeq());
            pstmt.setString(10,trnobj.getTrnType());
            pstmt.setInt(11,trnobj.getRefNo());
            pstmt.setString(12,trnobj.getVtml());
            pstmt.setString(13,trnobj.getVid());
            int s=pstmt.executeUpdate();
            if(s==1)
                return s;
            throw new RecordNotInsertedException();
        }catch(SQLException e)
        {
            e.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally
        {
            conn.close();
        }
        return 0;
    }*/
    
    /**
     * Updates the Currency_Stock of the given Cash Terminal 
     * for the given date only if the tml is open.
     */
    public void updateCurrency(CashObject co) throws SQLException
    {
        Connection conn=null;
        try
		{
            conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            System.out.println("hi i am in update Currency"+co.getR50()+"pvalue"+co.getP100());
            if(stmt.executeUpdate("update Currency_Stock set s1000=s1000+"+(co.getR1000()-co.getP1000())+",s500=s500+"+(co.getR500()-co.getP500())+",s100=s100+"+(co.getR100()-co.getP100())+",s50=s50+"+(co.getR50()-co.getP50())+",s20=s20+"+(co.getR20()-co.getP20())+",s10=s10+"+(co.getR10()-co.getP10())+",s5=s5+"+(co.getR5()-co.getP5())+",s2=s2+"+(co.getR2()-co.getP2())+",s1=s1+"+(co.getR1()-co.getP1())+",scoins=scoins+"+(co.getRcoins()-co.getPcoins())+",netamt=netamt+"+co.getAmount()+",de_user='"+co.uv.getUserId()+"',de_tml='"+co.uv.getUserTml()+"',de_date='"+co.uv.getUserDate()+"' where tml_no='"+co.uv.getUserTml()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'")==0)
            	throw new SQLException();    
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally
        {
            conn.close();
        }
    }
    
    /**
     * Miscellaneous Receipt - Inserts into VoucherData. 
     */
    public void storeVoucherData(VoucherDataObject vdo) throws SQLException,RecordNotInsertedException
    {
        PreparedStatement ps = null;
        Connection conn=null;
        try
        {
            conn=getConnection();
            ps = conn.prepareStatement("insert into VoucherData(vch_type,vch_no,trn_date,gl_sl_type,gl_sl_code,chq_no,chq_dt,trn_amt,cd_ind,cash_pdrd,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,narration) values(?,?,'"+vdo.getTransactionDate()+"',?,?,?,?,?,?,'F',?,?,'"+vdo.obj_userverifier.getUserDate()+"',?,?,?,?)");
            
            ps.setString(1, vdo.getVoucherType());
            ps.setInt(2, vdo.getVoucherNo());
            ps.setString(3, vdo.getGlType());
            ps.setInt(4, vdo.getGlCode());
            
            ps.setInt(5, vdo.getChequeNo());
            ps.setString(6, vdo.getChequeDate());
            
            ps.setDouble(7, vdo.getTransactionAmount());
            ps.setString(8, vdo.getCdIndicator());
            
            ps.setString(9, vdo.obj_userverifier.getUserId());
            ps.setString(10, vdo.obj_userverifier.getUserTml());
            ps.setString(11, vdo.obj_userverifier.getVerId());
            ps.setString(12, vdo.obj_userverifier.getVerTml());
            ps.setString(13, vdo.getVerifiedUserDateTime());
            //ship.....01/12/2005
            ps.setString(14, vdo.getNarration());
            
            if(ps.executeUpdate()==-1)
                throw new RecordNotInsertedException();
            
        } catch (SQLException exs)
        {
            exs.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally
        {
            conn.close();
        }
    }
    /**
     * Retrieves the row from DayCash for the given Scroll No for the given date.
     */
    public CashObject getData(int sc_no,int type,String date) throws SQLException,ScrollNotFoundException
    {   
        Connection conn=null;
        CashObject cashobject=null;
        int row = 0;
        
        try
		{
            ResultSet  rs=null;
            conn=getConnection();
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            //ship......17/06/2006
            /*if(type==0)
                rs=stmt.executeQuery("select * from DayCash where scroll_no="+sc_no+" and trn_date='"+getClientDate()+"' and ve_tml is null and ve_user is null and ve_date is null");*/
           
            if(type==0)
            {
                rs = stmt.executeQuery("select * from DayCash where scroll_no="+sc_no+" and trn_date='"+date+"' and de_tml is not null and de_user is not null and de_date is not null");
            }
            //ship.......27/06/2007
            else if(type==1)
            {
                rs = stmt.executeQuery("select * from DayCash where token_no="+sc_no+" and trn_date='"+date+"' and scroll_no=0 and vch_no is null and vch_type is null and de_tml is not null and de_user is not null and de_date is not null and ve_tml is not null and ve_user is not null and ve_date is not null");
            }
            ///////
            ///////////
            
            if(rs.last())
                row = rs.getRow();
            
            if(row==0)
            {
                //throw new ScrollNotFoundException();
                return cashobject;
            }
            else
            {
                rs.beforeFirst();
                
                if(rs.next())
                {
                    
                    cashobject =new CashObject();
                    
                    cashobject.setScrollno(rs.getInt("scroll_no"));
                    cashobject.setAcctype(rs.getString("ac_type"));
                    cashobject.setAccno(rs.getString("ac_no"));
                    cashobject.setTrndate(rs.getString("trn_date"));
                    cashobject.setTrntype(rs.getString("trn_type"));
                    cashobject.setOthtml(rs.getString("oth_tml"));
                    cashobject.setAmount(rs.getFloat("csh_amt"));
                    cashobject.setRunbal(rs.getDouble("run_bal"));//ship.....added on 19/10/2005
                    
                                       
                    cashobject.setCommamt(rs.getFloat("comm_amt"));
                    cashobject.setCdind(rs.getString("cd_ind"));
                    cashobject.setAccname(rs.getString("name"));
                    cashobject.setTokenno(rs.getInt("token_no"));
                    cashobject.setVchtype(rs.getString("vch_type"));
                    cashobject.setVchno(rs.getInt("vch_no"));
                    cashobject.setTrnseq(rs.getInt("trn_seq"));
                    cashobject.setLockertype(rs.getString("locker_type"));
                    cashobject.setSharecat(rs.getInt("share_category"));
                    //ship.....24/11/2005
                    cashobject.setCustAcctype(rs.getString("cust_ac_type"));
                    cashobject.setCustAccno(rs.getString("cust_ac_no"));
                    cashobject.setCustCode(rs.getString("cust_code"));
                    cashobject.setPOName(rs.getString("po_favour_name"));
                    //
                    
                    //ship.......24/12/2006
                    cashobject.setAttached(rs.getString("attached"));
                    //////////
                    
                    cashobject.uv.setUserId(rs.getString("de_user"));
                    cashobject.uv.setUserTml(rs.getString("de_tml"));
                    cashobject.uv.setUserDate(rs.getString("de_date"));
                    
                    cashobject.uv.setVerId(rs.getString("ve_user"));
                    cashobject.uv.setVerTml(rs.getString("ve_tml"));
                    cashobject.uv.setVerDate(rs.getString("ve_date"));                
                    
                    //ship......14/09/2006
                    if((rs.getInt(25)==0) && (rs.getInt(26)==0) && (rs.getInt(27)==0) && (rs.getInt(28)==0) && (rs.getInt(29)==0) && (rs.getInt(30)==0) && (rs.getInt(31)==0) && (rs.getInt(32)==0) && (rs.getInt(33)==0) && (rs.getInt(34)==0) && (rs.getInt(35)==0) && (rs.getInt(36)==0) && (rs.getInt(37)==0) && (rs.getInt(38)==0) && (rs.getInt(39)==0) && (rs.getInt(40)==0) && (rs.getInt(41)==0) && (rs.getInt(42)==0) && (rs.getFloat(43)==0.0) && (rs.getFloat(44)==0.0))
                    {
                        cashobject.setR1000(0);
                        cashobject.setP1000(0);
                        
                        cashobject.setR500(0);
                        cashobject.setP500(0);
                        
                        cashobject.setR100(0);
                        cashobject.setP100(0);
                        
                        cashobject.setR50(0);
                        cashobject.setP50(0);
                        
                        cashobject.setR20(0);
                        cashobject.setP20(0);
                        
                        cashobject.setR10(0);
                        cashobject.setP10(0);
                        
                        cashobject.setR5(0);
                        cashobject.setP5(0);
                        
                        cashobject.setR2(0);
                        cashobject.setP2(0);
                        
                        cashobject.setR1(0);
                        cashobject.setP1(0);
                        
                        cashobject.setRcoins(0.0);
                        cashobject.setPcoins(0.0);
                        
                        cashobject.setCurrDenom(false);
                    }
                    else
                    {
                        cashobject.setR1000(rs.getInt(25));
                        cashobject.setP1000(rs.getInt(26));
                        
                        cashobject.setR500(rs.getInt(27));
                        cashobject.setP500(rs.getInt(28));
                        
                        cashobject.setR100(rs.getInt(29));
                        cashobject.setP100(rs.getInt(30));
                        
                        cashobject.setR50(rs.getInt(31));
                        cashobject.setP50(rs.getInt(32));
                        
                        cashobject.setR20(rs.getInt(33));
                        cashobject.setP20(rs.getInt(34));
                        
                        cashobject.setR10(rs.getInt(35));
                        cashobject.setP10(rs.getInt(36));
                        
                        cashobject.setR5(rs.getInt(37));
                        cashobject.setP5(rs.getInt(38));
                        
                        cashobject.setR2(rs.getInt(39));
                        cashobject.setP2(rs.getInt(40));
                        
                        cashobject.setR1(rs.getInt(41));
                        cashobject.setP1(rs.getInt(42));
                        
                        cashobject.setRcoins(rs.getFloat(43));
                        cashobject.setPcoins(rs.getFloat(44));
                        
                        cashobject.setCurrDenom(true);
                    }
                    ///////////
                    
                    cashobject.setTerminalNo(rs.getString(45));
                }
            }
        }
        catch(SQLException ex)
		{
        	ex.printStackTrace();
        }
        finally
		{
            conn.close();
        }
        
              
        return cashobject;
    }
    
    /**
     * Lists the UnVerified Scroll Nos for the given date.
     * General & Miscellaneous Receipts - General, Voucher & Cash/Voucher Payments
     * Lockers
     */
    //ship....to get unverified scroll nos
    public CashObject[] forVerify(int type,String date) throws SQLException,ScrollNotFoundException
    {   
        Connection conn=null;
        
        
        CashObject cashobject[]=null;
        int row = 0;
        
        try
		{
            ResultSet  rs=null;
            conn=getConnection();
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            //for General Receipt .... Verification
            if(type==0)
            {
                //ship...21/12/2005.....added 1016001...
            	rs=stmt.executeQuery("select scroll_no,ac_type,ac_no,csh_amt,tml_no from DayCash where ((scroll_no!=0 and trn_date='"+date+"' and ac_type is not null and ac_no>0 and ac_type not like '1001%' and ac_type not like '1009%' and ac_type not like '1013%') or (scroll_no!=0 and trn_date='"+date+"' and ac_type like '1016%' and ac_no=0)) and attached='F' and de_user is not null and de_tml is not null and de_date is not null and ve_user is null and ve_tml is null and ve_date is null order by scroll_no");
            	
            	if(rs.last())
            	    row = rs.getRow();
            	
            	/*if(row==0)
            		throw new ScrollNotFoundException();*/
            	if(row>0)
            	{
            		cashobject = new CashObject[rs.getRow()];
            		rs.beforeFirst();
            		int i = 0;
            		
            		while(rs.next())
            		{
            			
            			cashobject[i] = new CashObject();               
            			cashobject[i].setScrollno(rs.getInt("scroll_no"));
            			cashobject[i].setAcctype(rs.getString("ac_type"));
            			cashobject[i].setAccno(rs.getString("ac_no"));
            			cashobject[i].setAmount(rs.getFloat("csh_amt"));
            			cashobject[i].setTerminalNo(rs.getString("tml_no"));
                
            			
            		
            			i++;
            		}
            	}
            }
            //for Miscellaneous Receipts
            else if(type==1)
            {
            	rs=stmt.executeQuery("select scroll_no,csh_amt,tml_no from DayCash where scroll_no!=0 and trn_date='"+date+"' and ac_type is null and ac_no is null and ve_user is null order by scroll_no");
            	
            	if(rs.last())
            	    row = rs.getRow();
            	
            	/*if(row==0)
            		throw new ScrollNotFoundException();*/
            	if(row>0)
            	{
            		cashobject = new CashObject[rs.getRow()];
            		rs.beforeFirst();
            		int i = 0;
            		while(rs.next())
            		{
            			
            			cashobject[i] = new CashObject();               
            			cashobject[i].setScrollno(rs.getInt("scroll_no"));
            			cashobject[i].setAmount(rs.getFloat("csh_amt"));
            			cashobject[i].setTerminalNo(rs.getString("tml_no"));
                            		
            			i++;
            		}
            	}
            }
            //for General Payments....08/11/2005
            else if(type==2)
            {
            	rs=stmt.executeQuery("select token_no,ac_type,ac_no,csh_amt from DayCash where scroll_no=0 and trn_date='"+date+"' and ac_type is not null and ac_no is not null and cd_ind='C' and token_no is not null and tml_no is null and de_user is not null and de_tml is not null and de_date is not null and ve_user is not null and ve_tml is not null and ve_date is not null order by token_no");
            	
            	if(rs.last())
            	    row = rs.getRow();
            	
            	/*if(row==0)
            		throw new ScrollNotFoundException();  */ 
            	if(row>0)
            	{
            		cashobject = new CashObject[rs.getRow()];
            		rs.beforeFirst();
            		int i = 0;
            		while(rs.next())
            		{
            			
            			cashobject[i] = new CashObject();               
            			cashobject[i].setTokenno(rs.getInt("token_no"));
            			cashobject[i].setAcctype(rs.getString("ac_type"));
            			cashobject[i].setAccno(rs.getString("ac_no"));
            			cashobject[i].setAmount(rs.getFloat("csh_amt"));
                
            			
            		
            			i++;
            		}
            	}
            }
            //for Voucher Payments....08/11/2005
            else if(type==3)
            {
            	rs=stmt.executeQuery("select vch_no,csh_amt from DayCash where scroll_no=0 and trn_date='"+date+"' and ac_type is null and ac_no is null and cd_ind='C' and vch_no is not null and tml_no is null and de_user is not null and de_tml is not null and de_date is not null and ve_user is not null and ve_tml is not null and ve_date is not null order by vch_no");
            	
            	if(rs.last())
            	    row = rs.getRow();
            	
            	/*if(row==0)
            		throw new ScrollNotFoundException();*/   
            	if(row>0)
            	{
            		cashobject = new CashObject[rs.getRow()];
            		rs.beforeFirst();
            		int i = 0;
            		while(rs.next())
            		{
            			cashobject[i] = new CashObject();               
            			cashobject[i].setVchno(rs.getInt("vch_no"));
            			cashobject[i].setAmount(rs.getFloat("csh_amt"));
                
            			
            		
            			i++;
            		}
            	}
            }
            //for Cash Payments....08/11/2005
            else if(type==4)
            {
            	rs=stmt.executeQuery("select vch_no,ac_type,ac_no,name,csh_amt from DayCash where scroll_no=0 and trn_date='"+date+"' and ac_type is not null and ac_no is not null and cd_ind='C' and vch_no is not null and tml_no is null and de_user is not null and de_tml is not null and de_date is not null and ve_user is not null and ve_tml is not null and ve_date is not null order by vch_no");
            	
            	if(rs.last())
            	    row = rs.getRow();
            	
            	/*if(row==0)
            		throw new ScrollNotFoundException();*/   
            	if(row>0)
            	{
            		cashobject = new CashObject[rs.getRow()];
            		rs.beforeFirst();
            		int i = 0;
            		while(rs.next())
            		{
            			
            			cashobject[i] = new CashObject();               
            			cashobject[i].setVchno(rs.getInt("vch_no"));
            			cashobject[i].setAcctype(rs.getString("ac_type"));
            			cashobject[i].setAccno(rs.getString("ac_no"));
            			cashobject[i].setAmount(rs.getFloat("csh_amt"));
                        cashobject[i].setAccname(rs.getString("name"));
                
            			
            		
            			i++;
            		}
            	}
            }
            //for Lockers
            else if(type==5)
            {
                //ship...21/12/2005.....added 1016001...
            	rs=stmt.executeQuery("select scroll_no,ac_type,ac_no,name,csh_amt from DayCash where scroll_no!=0 and trn_date='"+date+"' and ac_type is not null and ac_no is not null and ve_tml is null and ve_user is null and ve_date is null and attached='F' and ac_type like '1009%' order by scroll_no");
            	
            	if(rs.last())
            	    row = rs.getRow();
            	
            	/*if(row==0)
            		throw new ScrollNotFoundException();*/   
            	if(row>0)
            	{
            		cashobject = new CashObject[rs.getRow()];
            		rs.beforeFirst();
            		int i = 0;
            		while(rs.next())
            		{
            			
            			cashobject[i] = new CashObject();               
            			cashobject[i].setScrollno(rs.getInt("scroll_no"));
            			cashobject[i].setAcctype(rs.getString("ac_type"));
            			cashobject[i].setAccno(rs.getString("ac_no"));
            			cashobject[i].setAccname(rs.getString("name"));
            			cashobject[i].setAmount(rs.getFloat("csh_amt"));
                
            			
            		
            			i++;
            		}
            	}
            }
            //for General Receipts ..... Updation ..... 24/12/2006
            else if(type==6)
            {
                rs=stmt.executeQuery("select scroll_no,ac_type,ac_no,csh_amt,tml_no from DayCash where scroll_no!=0 and trn_date='"+date+"' and ac_type is not null and ac_no is not null and attached='F' and de_user is not null and de_tml is not null and de_date is not null and ve_user is null and ve_tml is null and ve_date is null order by scroll_no");
                
                if(rs.last())
                    row = rs.getRow();
                
                /*if(row==0)
                    throw new ScrollNotFoundException();*/
                if(row>0)
                {
                    cashobject = new CashObject[rs.getRow()];
                    rs.beforeFirst();
                    int i = 0;
                    
                    while(rs.next())
                    {
                        
                        cashobject[i] = new CashObject();               
                        cashobject[i].setScrollno(rs.getInt("scroll_no"));
                        cashobject[i].setAcctype(rs.getString("ac_type"));
                        cashobject[i].setAccno(rs.getString("ac_no"));
                        cashobject[i].setAmount(rs.getFloat("csh_amt"));
                        cashobject[i].setTerminalNo(rs.getString("tml_no"));
                
                       
                    
                        i++;
                    }
                }
            }
		}
        catch(SQLException ex)
		{
           	ex.printStackTrace();
		}
        finally
		{
           	conn.close();
		}
        
        return cashobject;
    }
    //
    
    /**
     * Payments - Retrieves the details of the given token/voucher no to be payed.
     */
    public CashObject getPaymentDetails(int vch_no,String date) throws SQLException,RecordsNotFoundException
    {
        Connection conn=null;
             CashObject cashobject=null;
        
        try
		{
            ResultSet  rs=null,rs1=null;
            conn=getConnection();
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //ship......08/11/2005
            //rs1=stmt1.executeQuery("select ac_type from DayCash where trn_date='"+getClientDate()+"' and vch_no="+vch_no+" and scroll_no=0 ");
            rs1=stmt1.executeQuery("select ac_type from DayCash where trn_date='"+date+"' and vch_no="+vch_no+" and scroll_no=0 and ac_type is not null and ac_no is not null and cd_ind='C' and vch_no is not null and vch_type is not null and tml_no is null and de_user is not null and de_tml is not null and de_date is not null and ve_user is not null and ve_tml is not null and ve_date is not null");
            //
            if(rs1.next())
            {
                if(rs1.getRow()==0)
                    throw new RecordsNotFoundException();
                
                if(rs1.getString(1).startsWith("1001") )//shares
                {
                     rs=stmt.executeQuery("select *,cid from DayCash d,ShareMaster sm where vch_no="+vch_no+" and d.ac_type=sm.ac_type and d.ac_no=sm.ac_no and trn_date='"+date+"' and cd_ind='C'");
                }
                else if(rs1.getString(1).startsWith("1003") || rs1.getString(1).startsWith("1004") || rs1.getString(1).startsWith("1005"))//deposits
                {
                    rs=stmt.executeQuery("select *,cid from DayCash d,DepositMaster dm where vch_no="+vch_no+" and d.ac_type=dm.ac_type and d.ac_no=dm.ac_no and trn_date='"+date+"' and cd_ind='C'");
                }
                else if(rs1.getString(1).startsWith("1006"))//pygmy
                {
                    rs=stmt.executeQuery("select *,cid from DayCash d,PygmyMaster pm where vch_no="+vch_no+" and d.ac_type=pm.ac_type and d.ac_no=pm.ac_no and trn_date='"+date+"' and cd_ind='C'");
                }
                else if(rs1.getString(1).startsWith("1008") || rs1.getString(1).startsWith("1010"))//loans
                {
                    rs=stmt.executeQuery("select *,cid from DayCash d,LoanMaster lm where vch_no="+vch_no+" and d.ac_type=lm.ac_type and d.ac_no=lm.ac_no and trn_date='"+date+"' and cd_ind='C'");
                }
                
                rs.last();
                
                if(rs.getRow()==0)
                    throw new RecordsNotFoundException();
                else
                {
                    rs.beforeFirst();
                    
                    if(rs.next())
                    {
                        
                        cashobject =new CashObject();
                        
                        cashobject.setScrollno(rs.getInt("scroll_no"));
                        cashobject.setAcctype(rs.getString("ac_type"));
                        cashobject.setAccno(rs.getString("ac_no"));
                        cashobject.setTrndate(rs.getString("trn_date"));
                        cashobject.setTrntype(rs.getString("trn_type"));
                        cashobject.setOthtml(rs.getString("oth_tml"));
                        cashobject.setAmount(rs.getFloat("csh_amt"));
                        
                        cashobject.setCommamt(rs.getFloat("comm_amt"));
                        cashobject.setCdind(rs.getString("cd_ind"));
                        cashobject.setAccname(rs.getString("name"));
                        cashobject.setTokenno(rs.getInt("token_no"));
                        cashobject.setVchtype(rs.getString("vch_type"));
                        cashobject.setVchno(rs.getInt("vch_no"));
                        cashobject.setTrnseq(rs.getInt("trn_seq"));
                        cashobject.setLockertype(rs.getString("locker_type"));
                        cashobject.setCustomerId(rs.getInt("cid"));
                        
                        cashobject.uv.setUserId(rs.getString("de_user"));
                        cashobject.uv.setUserTml(rs.getString("de_tml"));
                        cashobject.uv.setUserDate(rs.getString("de_date"));
                        
                        cashobject.uv.setVerId(rs.getString("ve_user"));
                        cashobject.uv.setVerTml(rs.getString("ve_tml"));
                        cashobject.uv.setVerDate(rs.getString("ve_date"));                
                        
                        cashobject.setR1000(rs.getInt(25));
                        cashobject.setP1000(rs.getInt(26));
                        
                        cashobject.setR500(rs.getInt(27));
                        cashobject.setP500(rs.getInt(28));
                        
                        cashobject.setR100(rs.getInt(29));
                        cashobject.setP100(rs.getInt(30));
                        
                        cashobject.setR50(rs.getInt(31));
                        cashobject.setP50(rs.getInt(32));
                        
                        cashobject.setR20(rs.getInt(33));
                        cashobject.setP20(rs.getInt(34));
                        
                        cashobject.setR10(rs.getInt(35));
                        cashobject.setP10(rs.getInt(36));
                        
                        cashobject.setR5(rs.getInt(37));
                        cashobject.setP5(rs.getInt(38));
                        
                        cashobject.setR2(rs.getInt(39));
                        cashobject.setP2(rs.getInt(40));
                        
                        cashobject.setR1(rs.getInt(41));
                        cashobject.setP1(rs.getInt(42));
                        
                        cashobject.setRcoins(rs.getFloat(43));
                        cashobject.setPcoins(rs.getFloat(44));
                        cashobject.setTerminalNo(rs.getString(45));
                    }
                }
            }
        }
        catch(SQLException ex)
		{
        	ex.printStackTrace();
        }
        finally
		{
            conn.close();
        }
        
        return cashobject;
    }
    
    /**
     * Terminal Closing - used to close the sub Cash Terminals.
     */
    public int closeTerminal(String subtml,String maintml,CashObject co) throws SQLException
    {
        ResultSet rs = null;	
        Connection conn = null;
        PreparedStatement ps = null;
        int scno = 0;
        
        try
        {		
            conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            //ship......10/01/2007
            //rs=stmt.executeQuery("select * from Currency_Stock where tml_no='"+subtml+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'");
            rs=stmt.executeQuery("select * from Currency_Stock where tml_no='"+subtml+"' and cur_date='"+co.getTrndate()+"'");
            
            if(rs.next())
            {
                if(!(rs.getDouble("netamt")==0.00) && rs.getString("rec_type").equals("O"))
                {
                    rs=stmt.executeQuery("select lst_acc_no,lst_rct_no from Modules where modulecode='1019000'");
                    
                    if(rs.next())
                    {
                        System.out.println("scroll no before update"+rs.getInt(1));
                        System.out.println("vch no before update"+rs.getInt(2));
                        
                        scno=rs.getInt(1)+1;	
                        
                        ps=conn.prepareStatement("insert into DayCash(scroll_no,trn_date,trn_type,vch_no,csh_amt,cd_ind,trn_seq,attached,de_user,de_tml,de_date,r1000,r500,r100,r50,r20,r10,r5,r2,r1,rcoins,p1000,p500,p100,p50,p20,p10,p5,p2,p1,pcoins,tml_no,oth_tml,run_bal) values("+scno+",'"+co.getTrndate()+"','A',?,?,'C',0,'F','"+co.uv.getUserId()+"','"+subtml+"','"+co.uv.getUserDate()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+subtml+"','"+maintml+"',0.00)");
        				
                    	ps.setString(1,"0");
                    	ps.setDouble(2,co.getAmount());
                    	
                    	ps.setInt(3,co.getR1000());
                        ps.setInt(4,co.getR500());
                        ps.setInt(5,co.getR100());
                        ps.setInt(6,co.getR50());
                        ps.setInt(7,co.getR20());
                        ps.setInt(8,co.getR10());
                        ps.setInt(9,co.getR5());
                        ps.setInt(10,co.getR2());
                        ps.setInt(11,co.getR1());
                        ps.setDouble(12,co.getRcoins());
                        
                        ps.setInt(13,co.getP1000());
                        ps.setInt(14,co.getP500());
                        ps.setInt(15,co.getP100());
                        ps.setInt(16,co.getP50());
                        ps.setInt(17,co.getP20());
                        ps.setInt(18,co.getP10());
                        ps.setInt(19,co.getP5());
                        ps.setInt(20,co.getP2());
                        ps.setInt(21,co.getP1());
                        ps.setDouble(22,co.getPcoins());
                        
                        ps.executeUpdate();
                        
                        if((stmt.executeUpdate("update Modules set lst_acc_no="+scno+" where modulecode='1019000'"))==0)
                            throw new SQLException();
                                                
                        if(stmt.executeUpdate("update Currency_Stock set s1000="+0+",s500="+0+",s100="+0+",s50="+0+",s20="+0+",s10="+0+",s5="+0+",s2="+0+",s1="+0+",scoins="+0.00+",netamt="+0.00+",rec_type='P' where tml_no='"+subtml+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'")==0)
                            throw new SQLException();
                    }
                }
                else if(rs.getDouble("netamt")==0.00 && rs.getString("rec_type").equals("P"))
                	scno = -1;
                else if(rs.getDouble("netamt")==0.00 && rs.getString("rec_type").equals("C"))
                    scno = -2;
            }
            else
                scno = -3;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally
        {
            conn.close();
        }
       System.out.println("scr--------------------"+scno);
        return scno;
    }
    
    /**
     * Cash Close - used to close the Main Cash Terminal.
     */
    //ship......added on 20/10/2005....to close the cash
    public int closeCash(String maintml,String date) throws SQLException
    {
        ResultSet rs = null,rs_dc = null;	
        Connection conn = null;
        int closed = 0;
        
        try
        {		
            conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement stmt_dc = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs=stmt.executeQuery("select * from Currency_Stock where tml_no='"+maintml+"' and cur_date='"+date+"'");
            
            if(rs.next())
            {
                if(rs.getString("rec_type").equalsIgnoreCase("O"))
                {
                	System.out.println("main tml opened");
                    
                    //ship.......20/06/2007
                    rs_dc = stmt_dc.executeQuery("select * from DayCash where trn_date='"+date+"'");
                    
                    if(rs_dc.next())
                    {
                        if(stmt_dc.executeUpdate("insert into DayCashOld select * from DayCash where trn_date='"+date+"'")==0)
                            throw new SQLException("Record not inserted in DayCashOld");
                        
                        if(stmt_dc.executeUpdate("delete from DayCash where trn_date='"+date+"'")==0)
                            throw new SQLException();
                    }
                    ////////////
                    
                    if(stmt.executeUpdate("update Currency_Stock set rec_type='C' where tml_no='"+maintml+"' and cur_date='"+date+"' and rec_type='O'")==0)
                        throw new SQLException();
                    
                    //ship......09/06/2006
                    if(stmt.executeUpdate("update DailyStatus set cash_close='T' where trn_dt='"+Validations.convertYMD(date)+"'")==0)
                        throw new SQLException();
                    /////////
                    
                    //ship......12/06/2006
                    if(stmt.executeUpdate("update Modules set lst_acc_no=0,lst_rct_no=0,lst_voucher_no=0 where modulecode='1019000'")==0)
                        throw new SQLException();
                    /////////////
                    
                    closed = 0;
                }
                else
                {
                	System.out.println("main tml already closed");
                    closed = 1;
                }
            }
            else
            {
               System.out.println("no entry of main tml");
                closed = -1;
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally
        {
            conn.close();
        }
        
        return closed;
    }
    //
    
    /**
     * Updates the Currency_Stock of the given Sub Cash Terminal only if it is open.
     */
    //ship
    public int updateTerminal(CashObject co) throws SQLException
    {
        ResultSet rs=null;	
        Connection conn=null;
        int flag = 0;
        
        try
        {		
            conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs=stmt.executeQuery("select * from Currency_Stock where tml_no='"+co.getTerminalNo()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'");
            
            if(rs.next())
            {
                /*if(stmt.executeUpdate("update Currency_Stock set cur_date='"+getClientDate()+"',s1000=s1000+"+rs.getString("s1000")+",s500=s500+"+rs.getString("s500")+",s100=s100+"+rs.getString("s100")+",s50=s50+"+rs.getString("s50")+",s20=s20+"+rs.getString("s20")+",s10=s10+"+rs.getString("s10")+",s5=s5+"+rs.getString("s5")+",s2=s2+"+rs.getString("s2")+",s1=s1+"+rs.getString("s1")+",scoins=scoins+"+rs.getString("scoins")+",netamt=netamt+"+rs.getString("netamt")+",de_user='"+userid+"',de_tml='"+tmlno+"',de_date='"+getClientDate()+"' where tml_no='"+tmlno+"'")==0)
                    throw new SQLException();*/
                
            	if(stmt.executeUpdate("update Currency_Stock set s1000="+co.getP1000()+",s500="+co.getP500()+",s100="+co.getP100()+",s50="+co.getP50()+",s20="+co.getP20()+",s10="+co.getP10()+",s5="+co.getP5()+",s2="+co.getP2()+",s1="+co.getP1()+",scoins="+co.getPcoins()+",netamt="+co.getAmount()+",de_tml='"+co.uv.getUserTml()+"',de_user='"+co.uv.getUserId()+"',de_date='"+co.getTrndate()+"'"+" "+"'"+co.getTrntime()+"' where tml_no='"+co.getTerminalNo()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'")==0)
                    throw new SQLException();
            	
                flag = 1;
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally
        {
            conn.close();
        }
        return flag;
    }
    //
    
    /**
     * Updates the Currency_Stock of the Main Cash Terminal for the given date 
     * irrespective of open/close.
     */
    //ship......20/10/2005
    public int updateCash(CashObject co) throws SQLException
    {
        ResultSet rs=null;	
        Connection conn=null;
        int flag = 0;
        
        try
        {		
            conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            //ship......01/11/2006
            //rs=stmt.executeQuery("select * from Currency_Stock where tml_no='"+co.getTerminalNo()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'");
            rs=stmt.executeQuery("select * from Currency_Stock where tml_no='"+co.getTerminalNo()+"' and cur_date='"+co.getTrndate()+"'");
            
            if(rs.next())
            {
                //ship.....01/11/2006
            	/*if(stmt.executeUpdate("update Currency_Stock set s1000="+co.getR1000()+",s500="+co.getR500()+",s100="+co.getR100()+",s50="+co.getR50()+",s20="+co.getR20()+",s10="+co.getR10()+",s5="+co.getR5()+",s2="+co.getR2()+",s1="+co.getR1()+",scoins="+co.getRcoins()+",netamt="+rs.getDouble("netamt")+",de_tml='"+co.uv.getUserTml()+"',de_user='"+co.uv.getUserId()+"',de_date='"+co.uv.getUserDate()+"' where tml_no='"+co.getTerminalNo()+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'")==0)
                    throw new SQLException();*/
                if(stmt.executeUpdate("update Currency_Stock set s1000="+co.getR1000()+",s500="+co.getR500()+",s100="+co.getR100()+",s50="+co.getR50()+",s20="+co.getR20()+",s10="+co.getR10()+",s5="+co.getR5()+",s2="+co.getR2()+",s1="+co.getR1()+",scoins="+co.getRcoins()+",netamt="+rs.getDouble("netamt")+",de_tml='"+co.uv.getUserTml()+"',de_user='"+co.uv.getUserId()+"',de_date='"+co.uv.getUserDate()+"' where tml_no='"+co.getTerminalNo()+"' and cur_date='"+co.getTrndate()+"'")==0)
                    throw new SQLException();
            	
            	flag = 1;
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally
        {
            conn.close();
        }
        
        return flag;
    }
    //
    
    /**
     * Checks whether the given tml is Sub Cash Terminal.
     * If so allows to close the terminal.
     */
    //ship......added on 17/10/2005....only sub cashiers r allowed to close the terminal
    public int checkTerminal(String tmlno) throws SQLException
    {
        ResultSet rs=null;	
        Connection conn=null;
        int flag = 0;
        
        try
        {		
            conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs=stmt.executeQuery("select tml_type from TerminalDetail where tml_code='"+tmlno+"'");
            
            if(rs.next())
            {
                if(rs.getString(1).equals("C"))
                    flag = 0;
                else
                	flag = 1;
            }
            else
                flag = -1;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally
        {
            conn.close();
        }
        return flag;
    }
    //
    
    /**
     * Checks whether the given tml is Main Cash Terminal.
     * If so allows to close the Cash. 
     */
    //ship......added on 20/10/2005......only main cashiers r allowed to close the cash
    public int checkCash(String tmlno) throws SQLException
    {
        ResultSet rs=null;	
        Connection conn=null;
        int flag = 0;
        
        try
        {		
            conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs=stmt.executeQuery("select tml_type from TerminalDetail where tml_code='"+tmlno+"'");
            rs.next();
            
            if(rs.getString(1).equals("M"))
                flag = 0;
            else
            	flag = 1;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally
        {
            conn.close();
        }
        
        return flag;
    }
    //
    
    /**
     * Checks whether the given Terminal is open/close
     */
    //ship......23/06/2006....to check the tmls whether open/close.
    public int checkTmlOpenClose(String tmlno,String date) throws SQLException
    {
        Connection conn = null;
        Statement stmt_tmls = null,stmt_CurrStk = null;
        ResultSet rs_tmls = null,rs_CurrStk = null;	
        int flag = 0;
        
        try
        {		
            conn = getConnection();
            stmt_tmls = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt_CurrStk = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs_tmls = stmt_tmls.executeQuery("select tml_type from TerminalDetail where tml_code='"+tmlno+"'");
            
            if(rs_tmls.next())
            {
                if(rs_tmls.getString(1).equals("C"))
                {
                    rs_CurrStk = stmt_CurrStk.executeQuery("select * from Currency_Stock where cur_date='"+date+"' and tml_no='"+tmlno+"'");
                    
                    if(rs_CurrStk.next())
                    {
                        if(rs_CurrStk.getString("rec_type").equals("O"))
                            flag = 1;
                        else if(rs_CurrStk.getString("rec_type").equals("C") && rs_CurrStk.getDouble("netamt")==0)
                            flag = 2;
                        else if(rs_CurrStk.getString("rec_type").equals("P") && rs_CurrStk.getDouble("netamt")==0)
                            flag = 3;
                    }
                }
                else if(rs_tmls.getString(1).equals("M"))
                    flag = 1;
            }
            else
                flag = -1;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally
        {
            conn.close();
        }
        return flag;
    }
    //////////////
    
    //ship......commented.....16/01/2006
    /*public int setCurrencyStockObject(CurrencyStockObject cso)throws SQLException
    {
        ResultSet rs = null;
        Connection conn=null;
        try
        {
            conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery("Select rec_type from Currency_Stock where tml_no='"+ cso.getTmlNo() + "' ");
            rs.next();
            if (rs.getString("rec_type").equals("O"))
            {
                if(stmt.executeUpdate("update Currency_Stock set cur_date='"+getClientDate()+"',s1000="+ (cso.gets1000()) + ",s500=" + (cso.gets500())+ ",s100=" + (cso.gets100()) + ",s50=" + (cso.gets50())+ ",s20=" + (cso.gets20()) + ",s10=" + (cso.gets10())+ ",s5=" + (cso.gets5()) + ",s2=" + (cso.gets2())+ ",s1=" + (cso.gets1()) + ",scoins="+ (cso.getscoins()) + ",netamt=" + cso.getNetamt()+ ",de_user='"+cso.getUserId()+"',de_tml='"+ cso.getTmlNo() +"',de_date='"+getClientDate()+"' where tml_no='" + cso.getTmlNo() + "'")==0)
                    throw new SQLException();
                return 1;
            }
            return 0;
            
        } catch (SQLException ex)
        {
            ex.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally
        {
            conn.close();
        }
        return 0;
    }*/
    
    //ship......01/11/2006   
    /*public int updateVoucherData(int int_scroll_no,int vchno,String[] gltype,int[] glcode,double[] amount,String date) throws SQLException
    {
        Connection conn=null;
        double total_amt = 0.0;//ship.....18/09/2006
        try
        {
            ResultSet res=null;
            conn=getConnection();
            Statement stat=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            System.out.println("gltype length = "+gltype.length);
            
            //ship......18/09/2006
            res = stat.executeQuery("select * from DayCash where scroll_no="+int_scroll_no+" and vch_no="+vchno+" and vch_type='R' and ve_user is null and ve_tml is null and ve_date is null and trn_date='"+date+"'");
            
            if(res.next())
            {
                for(int i=0;i<gltype.length;i++)
                {
                    if(stat.executeUpdate("update VoucherData set trn_amt=trn_amt-"+amount[i]+",gl_sl_code="+glcode[i]+",gl_sl_code="+glcode[i]+" where vch_no="+vchno+" and vch_type='R' and cd_ind='C' and trn_date='"+date+"'")==0)
                        throw new SQLException();
                }
            }
            
            for(int i=0;i<gltype.length;i++)
            {
            	//ship
            	res=stat.executeQuery("select * from DayCash where scroll_no="+int_scroll_no+" and vch_no="+vchno+" and ve_tml is null and trn_date='"+date+"'");
            	if(res.next())
            	{
            		if(stat.executeUpdate("update DayCash set csh_amt=csh_amt-"+amount[i]+" where vch_type='R' and vch_no="+vchno+" and scroll_no="+int_scroll_no+" and cd_ind='D' and trn_date='"+date+"'")==0)
                        throw new SQLException();
            		if(stat.executeUpdate("update VoucherData set trn_amt=trn_amt-"+amount[i]+",gl_sl_code="+glcode[i]+",gl_sl_code="+glcode[i]+" where vch_no="+vchno+" and vch_type='R' and cd_ind='C' and trn_date='"+date+"'")==0)
                        throw new SQLException();
            	}
            	//
                if(stat.executeUpdate("delete from VoucherData where vch_no="+vchno+" and gl_sl_type='"+gltype[i]+"' and gl_sl_code="+glcode[i]+" and trn_amt="+amount[i]+" and trn_date='"+getClientDate()+"'  ")==0)
                    throw new SQLException();
                if(stat.executeUpdate("update VoucherData set trn_amt=trn_amt-"+amount[i]+" where vch_no="+vchno+" and vch_type='R' and cd_ind='D' and trn_date='"+getClientDate()+"'")==0)
                    throw new SQLException();
                if(stat.executeUpdate("update DayCash set csh_amt=csh_amt-"+amount[i]+" where vch_type='R' and vch_no="+vchno+" and scroll_no="+int_scroll_no+" and trn_date='"+getClientDate()+"' ")==0)
                    throw new SQLException();
                res=stat.executeQuery("select * from DayCash where scroll_no="+int_scroll_no+" and ve_tml is null and trn_date='"+getClientDate()+"' ");
                if(res.next())
                {	            	
                    if(res.getDouble("csh_amt")==0.00)
                    {	            	
                        if(stat.executeUpdate("update Currency_Stock set cur_date='"+getClientDate()+"',s1000=s1000-"+((res.getInt("r1000")-res.getInt("p1000")))+",s500=s500-"+((res.getInt("r500")-res.getInt("p500")))+",s100=s100-"+((res.getInt("r100")-res.getInt("p100")))+",s50=s50-"+((res.getInt("r50")-res.getInt("p50")))+",s20=s20-"+((res.getInt("r20")-res.getInt("p20")))+",s10=s10-"+((res.getInt("r10")-res.getInt("p10")))+",s5=s5-"+((res.getInt("r5")-res.getInt("p5")))+",s2=s2-"+((res.getInt("r2")-res.getInt("p2")))+",s1=s1-"+((res.getInt("r1")-res.getInt("p1")))+",scoins=scoins-"+((res.getFloat("rcoins")-res.getFloat("pcoins")))+",netamt=netamt-"+(res.getFloat("csh_amt") )+",de_user='"+userid+"',de_tml='"+res.getString("de_tml")+"',de_date='"+getClientDate()+"' where tml_no='"+res.getString("de_tml")+"'")==0)
                            throw new SQLException();
                        
                        ResultSet rs=stat.executeQuery("Select vch_no,vch_type from DayCash where scroll_no="+int_scroll_no+" and trn_date='"+getSysDate()+" ");
                        
                        if(stat.executeUpdate("delete from DayCash where scroll_no="+int_scroll_no+" and ve_tml is null and trn_date='"+getClientDate()+"'")==0)
                            throw new SQLException();
                        
                        if(rs.next())
                        {
                            if(stat.executeUpdate("delete from VoucherData where vch_type='"+rs.getString(2)+"' and vch_no="+rs.getInt(1)+" and ve_tml is null and trn_date='"+getClientDate()+"'")==0)
                                throw new SQLException();
                        }
                    }
                    else
                    {
                        if(stat.executeUpdate("update Currency_Stock set cur_date='"+getClientDate()+"',s1000=s1000-"+((res.getInt("r1000")-res.getInt("p1000")))+",s500=s500-"+((res.getInt("r500")-res.getInt("p500")))+",s100=s100-"+((res.getInt("r100")-res.getInt("p100")))+",s50=s50-"+((res.getInt("r50")-res.getInt("p50")))+",s20=s20-"+((res.getInt("r20")-res.getInt("p20")))+",s10=s10-"+((res.getInt("r10")-res.getInt("p10")))+",s5=s5-"+((res.getInt("r5")-res.getInt("p5")))+",s2=s2-"+((res.getInt("r2")-res.getInt("p2")))+",s1=s1-"+((res.getInt("r1")-res.getInt("p1")))+",scoins=scoins-"+((res.getFloat("rcoins")-res.getFloat("pcoins")))+",netamt=netamt-"+(res.getFloat("csh_amt") )+",de_user='"+userid+"',de_tml='"+res.getString("de_tml")+"',de_date='"+getClientDate()+"' where tml_no='"+res.getString("de_tml")+"'")==0)
                            throw new SQLException();              
                    }
                }
            }
        }
        catch(SQLException Exception)
        {
            Exception.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally
        {
            conn.close();
        }
        return 1;
    }*/
    
    /**
     * Deletes the details of Voucher(Misc) Receipts from DayCash, VoucherData.
     */
    public void deleteVoucherData(int int_vchno,String string_vch_type,String string_tml,String date,String time)throws SQLException
    {
        System.out.println("************inside erData**************");
      
        Connection conn=null;
        try
        {
            conn=getConnection();
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=stmt.executeQuery("Select * from DayCash where vch_no="+int_vchno+" and vch_type='"+string_vch_type+"' and trn_date='"+date+"'");
          
            if(rs.next())
            {
                System.out.println("Inside if ******updating currency*****");
                
                if(stmt.executeUpdate("update Currency_Stock set s1000=s1000-"+(rs.getInt("r1000")-rs.getInt("p1000"))+",s500=s500-"+(rs.getInt("r500")-rs.getInt("p500"))+",s100=s100-"+(rs.getInt("r100")-rs.getInt("p100"))+",s50=s50-"+(rs.getInt("r50")-rs.getInt("p50"))+",s20=s20-"+(rs.getInt("r20")-rs.getInt("p20"))+",s10=s10-"+(rs.getInt("r10")-rs.getInt("p10"))+",s5=s5-"+(rs.getInt("r5")-rs.getInt("p5"))+",s2=s2-"+(rs.getInt("r2")-rs.getInt("p2"))+",s1=s1-"+(rs.getInt("r1")-rs.getInt("p1"))+",scoins=scoins-"+(rs.getFloat("rcoins")-rs.getFloat("pcoins"))+",netamt=netamt-"+rs.getFloat("csh_amt")+",de_user='"+rs.getString("de_user")+"',de_tml='"+string_tml+"',de_date='"+date+"'"+" "+"'"+time+"' where tml_no='"+string_tml+"' and cur_date='"+date+"' and rec_type='O'")==0)
                    throw new SQLException();
            }
            
            if(stmt.executeUpdate("delete from VoucherData where vch_no="+int_vchno+" and vch_type='"+string_vch_type+"' and trn_date='"+date+"'")==0)
                throw new SQLException();
            
            if(stmt.executeUpdate("delete from DayCash where vch_no="+int_vchno+" and vch_type='"+string_vch_type+"' and trn_date='"+date+"'")==0)
                throw new SQLException();
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally
        {
            conn.close();
        }
    }
    
    /**
     * Deletes the rows of a given voucher no from VoucherData for the given date.
     */
    //ship.....added to delete the rows in the gl table when submit button is pressed..03/10/2005
    public void deleteVoucherDataTable(int int_vchno,String string_vch_type,String string_tml,String date) throws SQLException
    {
        System.out.println("************inside deleteVoucherDataTable**************");
      
        Connection conn=null;
        try
        {
            conn=getConnection();
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                      
            stmt.executeUpdate("delete from VoucherData where vch_no="+int_vchno+" and vch_type='"+string_vch_type+"' and trn_date='"+date+"' and de_tml='"+string_tml+"'");   
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
            sessionContext.setRollbackOnly();
        }
        finally
        {
            conn.close();
        }
    }
    //
    
    /**
     * Miscellaneous Receipt - Verification
     */
    public boolean verifyVoucherReceipts(CashObject co) throws SQLException
    {
        Connection conn = null;
        ResultSet rs = null,rs1 = null;
        GLTransObject trnobj = new GLTransObject();
        try
        {
            conn=getConnection();
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if(stmt.executeUpdate("update DayCash set ve_user='"+co.uv.getVerId()+"',ve_tml='"+co.uv.getVerTml()+"',ve_date='"+co.uv.getVerDate()+"',attached='T' where vch_no="+co.getVchno()+" and trn_date='"+co.getTrndate()+"' and vch_type='"+co.getVchtype()+"'")==0)
                throw new SQLException();
            //else
               if(stmt.executeUpdate("update VoucherData set cash_pdrd='T',ve_user='"+co.uv.getVerId()+"',ve_tml='"+co.uv.getVerTml()+"',ve_date='"+co.uv.getVerDate()+"' where vch_no="+co.getVchno()+" and trn_date='"+co.getTrndate()+"' and vch_type='"+co.getVchtype()+"'")==0)
                    throw new SQLException();
              /* else
                   return true;*/
               
            rs = stmt.executeQuery("select * from VoucherData where vch_no="+co.getVchno()+" and trn_date='"+co.getTrndate()+"' and vch_type='"+co.getVchtype()+"' and cd_ind='D'");
            
            if(rs.next())
            {
                trnobj.setGLCode(rs.getString("gl_sl_code"));
                trnobj.setTrnDate(rs.getString("trn_date"));
                trnobj.setGLType(rs.getString("gl_sl_type"));
                trnobj.setAmount(rs.getDouble("trn_amt"));
                trnobj.setCdind(rs.getString("cd_ind"));
                trnobj.setAccType("1019001");
                trnobj.setAccNo(String.valueOf(rs.getInt("vch_no")));
                trnobj.setTrnType("R");
                trnobj.setTrnMode("C");
                trnobj.setRefNo(co.getScrollno());
                trnobj.setVtml(co.uv.getVerTml());
                trnobj.setVid(co.uv.getVerId());
                trnobj.setVDate(co.uv.getVerDate());
                
                if(common_local.storeGLTransaction(trnobj)==1)
                {
                    System.out.println("after first storeGLTransaction......");
                    rs1 = stmt.executeQuery("select * from VoucherData where vch_no="+co.getVchno()+" and trn_date='"+co.getTrndate()+"' and vch_type='"+co.getVchtype()+"' and cd_ind='C'");
                    
                    while(rs1.next())
                    {
                        System.out.println("inside second storeGLTransaction....");
                        trnobj.setGLCode(rs1.getString("gl_sl_code"));
                        trnobj.setTrnDate(rs1.getString("trn_date"));
                        trnobj.setGLType(rs1.getString("gl_sl_type"));
                        trnobj.setAmount(rs1.getDouble("trn_amt"));
                        trnobj.setCdind(rs1.getString("cd_ind"));
                        trnobj.setAccType("1019001");
                        trnobj.setAccNo(String.valueOf(rs1.getInt("vch_no")));
                        trnobj.setTrnType("R");
                        trnobj.setTrnMode("C");
                        trnobj.setRefNo(co.getScrollno());
                        trnobj.setVtml(co.uv.getVerTml());
                        trnobj.setVid(co.uv.getVerId());
                        trnobj.setVDate(co.uv.getVerDate());
                        
                        common_local.storeGLTransaction(trnobj);
                    }
                }
            }
            
            return true;
        }
        catch(SQLException sqlexception)
        {
            sqlexception.printStackTrace();
            return false;
        }
        finally
        {
            conn.close();
        }
   }
    
    /**
     * General Receipt - Pay Order - Verification
     */
    //ship....PO.....verify
    public int verifyDayCashPO(CashObject co) throws SQLException, DateFormatException
    {
    	   int po_sr_no = 0;
           Connection conn=null;
           ResultSet rs_po = null;
           System.out.println("inside PO...scroll no = "+co.getScrollno());
           
           try
   		{
               conn=getConnection();
               Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
               
               rs_po = stmt.executeQuery("select * from DayCash where scroll_no="+co.getScrollno()+" and trn_date='"+co.getTrndate()+"'");
               
               if(rs_po.next())
               {
                   //ship....01/02/2007
                   /*if(stmt.executeUpdate("update DayCash  set attached='T',ve_user='"+co.uv.getVerId()+"',ve_tml='"+co.uv.getVerTml()+"',ve_date='"+co.uv.getVerDate()+"' where scroll_no="+co.getScrollno()+" and trn_date='"+co.getTrndate()+"'")==0)
                   	throw new SQLException();*/
                   
                   PayOrderObject po=new PayOrderObject();
                   po.setPOType("C");
                   po.setCustType(Integer.parseInt(co.getCustCode()));
                   po.setPOPayee(co.getAccname());
                   po.setPOAccType(co.getCustAcctype());
                   po.setPOAccNo(Integer.parseInt(co.getCustAccno()));
                   po.setPOFavour(co.getPOName());
                   po.setPODate(co.getTrndate());
                   po.setPOAmount(co.getAmount()-co.getCommamt());
                   po.setCommissionAmount(co.getCommamt());
                   po.uv.setUserTml(rs_po.getString("de_tml"));
                   po.uv.setUserId(rs_po.getString("de_user"));
                   po.uv.setUserDate(rs_po.getString("de_date"));
                   po.uv.setVerTml(co.uv.getVerTml());
                   po.uv.setVerId(co.uv.getVerId());
                   po.uv.setVerDate(co.uv.getVerDate());
                   
                   ResultSet rs_PO = null;
                   rs_PO=stmt.executeQuery("select * from GLKeyParam where ac_type='1019001' and code=1");
                   
                   if(rs_PO.next())
                   {
                       po.setPOGlName(rs_PO.getString("key_desc"));
                       po.setPOGlType(rs_PO.getString("gl_type"));
                       po.setPOGlCode(rs_PO.getInt("gl_code"));
                       
                      
                       po_sr_no = common_local.storePayOrder(po);
                       
                       //ship.....01/02/2007
                       if(stmt.executeUpdate("update DayCash set ac_no="+po_sr_no+",attached='T',ve_user='"+co.uv.getVerId()+"',ve_tml='"+co.uv.getVerTml()+"',ve_date='"+co.uv.getVerDate()+"' where scroll_no="+co.getScrollno()+" and trn_date='"+co.getTrndate()+"' and ac_type='"+co.getAcctype()+"' and ac_no=0")==0)
                           throw new SQLException();
                       ////////////
                   }
                   
                   ResultSet rs = null,rs1 = null,rs2 = null;
                   GLTransObject trnobj = new GLTransObject();
                   
                   rs = stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1019001' and code=1");
                   
                   if(rs.next())
                   {
                       trnobj.setGLCode(rs.getString("gl_code"));
                       trnobj.setGLType(rs.getString("gl_type"));
                   }
                   
                   trnobj.setCdind("D");
                   trnobj.setTrnDate(co.getTrndate());
                   trnobj.setTrnMode("C");
                   trnobj.setAmount(co.getAmount());
                   trnobj.setCdind(co.getCdind());
                   //ship.....28/06/2006
                   trnobj.setAccType(co.getAcctype());
                   trnobj.setAccNo(String.valueOf(po_sr_no));
                   trnobj.setTrnSeq(0);
                   trnobj.setTrnType("R");
                   ///////////
                   /*trnobj.setAccType("1019001");
                   trnobj.setAccNo(null);
                   trnobj.setTrnSeq(0);
                   trnobj.setTrnType("R");*/
                   trnobj.setRefNo(co.getScrollno());
                   trnobj.setVtml(co.uv.getVerTml());
                   trnobj.setVid(co.uv.getVerId());
                   trnobj.setVDate(co.uv.getVerDate());
                   
                   common_local.storeGLTransaction(trnobj);
                   
                   System.out.println("2....PO........");
                   System.out.println("cash bean verify PO acc_type = "+co.getAcctype());
                   po.setGLRefCode(Integer.parseInt(co.getAcctype()));
                   po.setGLSubCode(1);
                   po.setPOAmount(po.getPOAmount());
                   
                   rs1=stmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type="+po.getGLRefCode()+" and code="+po.getGLSubCode()+"");
                   
                   if(rs1.next())
                   {
                       trnobj.setGLCode(rs1.getString(1));
                       trnobj.setGLType(rs1.getString(2));
                   }
                   
                   trnobj.setCdind("C");
                   trnobj.setTrnDate(co.getTrndate());
                   trnobj.setTrnMode("C");
                   trnobj.setAmount(po.getPOAmount());
                   trnobj.setAccType(co.getAcctype());
                   trnobj.setAccNo(String.valueOf(po_sr_no));
                   trnobj.setTrnSeq(0);
                   trnobj.setTrnType("R");
                   trnobj.setRefNo(co.getScrollno());
                   trnobj.setVtml(co.uv.getVerTml());
                   trnobj.setVid(co.uv.getVerId());
                   trnobj.setVDate(co.uv.getVerDate());
                   
                   common_local.storeGLTransaction(trnobj);
                   
                  System.out.println("3....PO........");
                   
                   //Posting to Profit
                   po.setGLRefCode(Integer.parseInt(co.getAcctype()));
                   po.setGLSubCode(2);
                   po.setPOAmount(po.getCommissionAmount());
                   
                   rs2=stmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type="+po.getGLRefCode()+" and code="+po.getGLSubCode()+"");
                   
                   if(rs2.next())
                   {
                       trnobj.setGLCode(rs2.getString(1));
                       trnobj.setGLType(rs2.getString(2));
                   }
                   
                   trnobj.setCdind("C");
                   trnobj.setTrnDate(co.getTrndate());
                   trnobj.setTrnMode("C");
                   trnobj.setAmount(po.getPOAmount());
                   trnobj.setAccType(co.getAcctype());
                   trnobj.setAccNo(String.valueOf(po_sr_no));
                   trnobj.setTrnSeq(0);
                   trnobj.setTrnType("R");
                   trnobj.setRefNo(co.getScrollno());
                   trnobj.setVtml(co.uv.getVerTml());
                   trnobj.setVid(co.uv.getVerId());
                   trnobj.setVDate(co.uv.getVerDate());
                   
                   common_local.storeGLTransaction(trnobj);
                   
                   
               }
   		}
           catch(SQLException e)
           {
               e.printStackTrace();
               throw new RecordNotInsertedException();
           }
           finally
           {
               conn.close();
           }
           return po_sr_no;
    }
    //
    
    /**
     * Checks the Max amount to be paid for Loan Recovery.
     */
    //ship.......13/06/2006
    public double checkLNAmount(String ln_ac_type,int ln_ac_no,String date,String de_user,String de_tml) throws SQLException
    {
        Connection conn = null;
        Statement stmt_ln = null,stmt_lm = null;
        ResultSet rs_ln = null,rs_lm = null;
        
        double max_amt = 0.0;
        int flag = 0;
        
        try
        {
            conn = getConnection();
            stmt_ln = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt_lm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            rs_ln = stmt_ln.executeQuery("select * from LoanRecoveryDetail where ac_type='"+ln_ac_type+"' and ac_no="+ln_ac_no+" and processing_date='"+date+"'");
            
            if(rs_ln.next())
                max_amt = rs_ln.getDouble("loan_balance")+rs_ln.getDouble("int_amt")+rs_ln.getDouble("penal_amt")+rs_ln.getDouble("other_charges");
            else
            {
                rs_lm = stmt_lm.executeQuery("select sub_category from CustomerMaster where cid=(select cid from LoanMaster where ac_type='"+ln_ac_type+"' and ac_no="+ln_ac_no+")");
                
                if(rs_lm.next())
                {
                    flag = loan_local.postRecoveryDetails(ln_ac_type,ln_ac_no,date,rs_lm.getInt("sub_category"),0,de_user,de_tml);
                    
                    if(flag>0)
                    {
                        rs_ln = stmt_ln.executeQuery("select * from LoanRecoveryDetail where ac_type='"+ln_ac_type+"' and ac_no="+ln_ac_no+" and processing_date='"+date+"'");
                        
                        if(rs_ln.next())
                            max_amt = rs_ln.getDouble("loan_balance")+rs_ln.getDouble("int_amt")+rs_ln.getDouble("penal_amt")+rs_ln.getDouble("other_charges");
                    }
                }
            }
        }
        catch(SQLException se)
        {
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
        
        return max_amt;
    }
    
    /**
     * General Receipt - Verification
     */
    public boolean verifyDayCash(CashObject co) throws SQLException,DateFormatException
    {
        Connection conn=null;
        int trn_seq = 0;
        
        try
		{
            System.out.println("inside Verify Day Cash");;
            
            String code=co.getGLRefCode();
            conn=getConnection();
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                       
            // 4 existing SB/CA accounts.....ship added CA......16/12/2005
            System.out.println("I am Here --->"+co.getAccno());
            System.out.println("AC_Typeeee----> "+code);
            if((code.startsWith("1002") || code.startsWith("1007") || code.startsWith("1017") || code.startsWith("1018")) && !co.getAccno().equals("0"))
            {
                Statement stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet rs=stmt1.executeQuery("select trn_seq,cl_bal from AccountTransaction where ac_type='"+co.getAcctype()+"' and ac_no="+co.getAccno()+" order by trn_seq desc");
                
                if(rs.next())
                    trn_seq = rs.getInt(1)+1;
                if(stmt.executeUpdate("insert into AccountTransaction values('"+co.getAcctype()+"',"+co.getAccno()+",'"+co.getTrndate()+"','"+co.getTrntype()+"',"+trn_seq+","+co.getAmount()+",'C','"+co.uv.getUserTml()+"','C',null,null,null,"+co.getScrollno()+",null,"+(rs.getDouble(2)+co.getAmount())+",null,'"+co.uv.getUserTml()+"','"+co.uv.getUserId()+"','"+co.uv.getUserDate()+"','"+co.uv.getVerTml()+"','"+co.uv.getVerId()+"','"+co.uv.getVerDate()+"')")==0)
                    throw new SQLException();
                if(stmt.executeUpdate("update DayCash  set attached='T',trn_seq="+trn_seq+",ve_user='"+co.uv.getVerId()+"',ve_tml='"+co.uv.getVerTml()+"',ve_date='"+co.uv.getVerDate()+"' where scroll_no="+co.getScrollno()+" and trn_date='"+co.getTrndate()+"'")==0)
                    throw new SQLException();
                //ship
                if(stmt.executeUpdate("update AccountMaster set last_tr_seq="+trn_seq+",last_tr_date='"+co.getTrndate()+"' where ac_type="+co.getAcctype()+" and ac_no="+co.getAccno()+"")==0)
                    throw new SQLException();
                //
            }
            // 4 existing RD accounts
            else if(code.startsWith("1004") && !co.getAccno().equals("0"))
            {
            	
                Statement stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                //ship
                //ResultSet rs=stmt1.executeQuery("select trn_seq,rd_bal,cum_int from DepositTransaction where dp_ac_ty='"+co.getAcctype()+"' and dp_ac_no="+co.getAccno()+" order by trn_seq desc");
                ResultSet rs=stmt1.executeQuery("select trn_seq,rd_bal,cum_int from DepositTransaction where ac_type='"+co.getAcctype()+"' and ac_no="+co.getAccno()+" order by trn_seq desc");
                //
                rs.next();
                trn_seq = rs.getInt(1)+1;
                if(stmt.executeUpdate("insert into DepositTransaction values('"+co.getAcctype()+"',"+co.getAccno()+","+trn_seq+",'"+co.getTrndate()+"','D',"+co.getAmount()+",0.00,0.00,0.00,"+(rs.getDouble("rd_bal")+co.getAmount())+",null,null,"+co.getScrollno()+",null,'C','"+co.uv.getUserTml()+"','C',"+rs.getDouble("cum_int")+",'"+co.uv.getUserTml()+"','"+co.uv.getUserId()+"','"+co.uv.getUserDate()+"','"+co.uv.getVerTml()+"','"+co.uv.getVerId()+"','"+co.uv.getVerDate()+"')")==0)
                    throw new SQLException();
                
                if(stmt.executeUpdate("update DepositMaster set lst_trn_seq="+trn_seq+" where ac_type='"+co.getAcctype() +"' and ac_no="+co.getAccno())==0)
                    throw new SQLException();
                //
                if(stmt.executeUpdate("update DayCash set attached='T',trn_seq="+trn_seq+",ve_user='"+co.uv.getVerId()+"',ve_tml='"+co.uv.getVerTml()+"',ve_date='"+co.uv.getVerDate()+"' where scroll_no="+co.getScrollno()+" and trn_date='"+co.getTrndate()+"'")==0)
                	throw new SQLException();
                System.out.println("2...........");
            }
            // 4 existing LOAN accounts
            else if(code.startsWith("1008") && !co.getAccno().equals("0"))
            {
                //ship.....19/06/2006
              masterObject.loansOnDeposit.LoanTransactionObject lob = null;
                Object seq = null;
                
                try
                {
                    lob = new masterObject.loansOnDeposit.LoanTransactionObject();	
                    
				    lob.setAccType(co.getAcctype());
				    lob.setAccNo(Integer.parseInt(co.getAccno()));
				    lob.setTransactionDate(co.getTrndate());
				    lob.setTranType("R");
				    lob.setTransactionAmount(co.getAmount());
				    lob.setTranMode("C");
				    lob.setTranSou(co.uv.getUserTml());
				    lob.setReferenceNo(co.getScrollno());
				    lob.setTranNarration("Csh Scr No "+co.getScrollno());
				    lob.setCdind("C");
				    lob.uv.setUserTml(co.uv.getUserTml());
				    lob.uv.setUserId(co.uv.getUserId());
				    lob.uv.setUserDate(co.uv.getUserDate());
				    lob.uv.setVerTml(co.uv.getVerTml());
				    lob.uv.setVerId(co.uv.getVerId()) ;
				    lob.uv.setVerDate(co.uv.getVerDate());
				    
				    System.out.println("before recover ld account");
				    
				    seq = ld_local.recoverLDAccount(lob);
				    
				    if(seq==null)
				        return (false);
				    else
				    {
				    	  System.out.println("seq != null");
				        trn_seq = Integer.parseInt(seq.toString());
				        System.out.println("trn seq = "+trn_seq);
				    }
                }
                catch(ClassCastException cce)
                {
                    cce.printStackTrace();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                
                if(trn_seq > 0)
                    if(stmt.executeUpdate("update DayCash  set attached='T',trn_seq="+trn_seq+",ve_user='"+co.uv.getVerId()+"',ve_tml='"+co.uv.getVerTml()+"',ve_date='"+co.uv.getVerDate()+"' where scroll_no="+co.getScrollno()+" and trn_date='"+co.getTrndate()+"'")==0)
                        throw new SQLException();
            }
            // 4 existing LOANS
            else if(code.startsWith("1010") && !co.getAccno().equals("LN"))
            {
                //ship.....13/06/2006
                LoanTransactionObject lob = null;
                Object seq = null;
                
                try
                {
                    lob = new LoanTransactionObject();	
                    
				    lob.setAccType(co.getAcctype());
				    lob.setAccNo(Integer.parseInt(co.getAccno()));
				    lob.setTransactionDate(co.getTrndate());
				    lob.setTranType("R");
				    lob.setTransactionAmount(co.getAmount());
				    lob.setTranMode("C");
				    lob.setTranSou(co.uv.getUserTml());
				    lob.setReferenceNo(co.getScrollno());
				    lob.setTranNarration("Csh Scr No "+co.getScrollno());
				    lob.setCdind("C");
				    lob.uv.setUserTml(co.uv.getUserTml());
				    lob.uv.setUserId(co.uv.getUserId());
				    lob.uv.setUserDate(co.uv.getUserDate());
				    lob.uv.setVerTml(co.uv.getVerTml());
				    lob.uv.setVerId(co.uv.getVerId()) ;
				    lob.uv.setVerDate(co.uv.getVerDate());
				    
				    System.out.println("before recover loan account");
				    
				    seq = loan_local.recoverLoanAccount(lob);
				    
				    if(seq==null)
				        return (false);
				    else
				    {
				    	  System.out.println("seq != null");
				        trn_seq = Integer.parseInt(seq.toString());
				        System.out.println("trn seq = "+trn_seq);
				    }
                }
                catch(ClassCastException cce)
                {
                    cce.printStackTrace();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                
                if(trn_seq > 0)
                    if(stmt.executeUpdate("update DayCash  set attached='T',trn_seq="+trn_seq+",ve_user='"+co.uv.getVerId()+"',ve_tml='"+co.uv.getVerTml()+"',ve_date='"+co.uv.getVerDate()+"' where scroll_no="+co.getScrollno()+" and trn_date='"+co.getTrndate()+"'")==0)
                        throw new SQLException();
            }
            //ship.....4 existing OD/CC accounts
            else if((code.startsWith("1014") || code.startsWith("1015")) && !co.getAccno().equals("0"))
            {
                Statement stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet rs=stmt1.executeQuery("select trn_seq,cl_bal from ODCCTransaction where ac_type='"+co.getAcctype()+"' and ac_no="+co.getAccno()+" order by trn_seq desc");
                rs.next();
                trn_seq = rs.getInt(1)+1;
                //Getting last interest calculation date frm Master table to insert record into ODCCTransaction table
                Statement stmt2=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet rs1=stmt2.executeQuery("select int_uptodt from ODCCMaster where ac_type='"+co.getAcctype()+"' and ac_no="+co.getAccno());
                rs1.next();
                
                if(stmt.executeUpdate("insert into ODCCTransaction values('"+co.getAcctype()+"',"+co.getAccno()+",'"+co.getTrndate()+"','"+co.getTrntype()+"',"+trn_seq+","+co.getAmount()+",'C','"+co.uv.getUserTml()+"','C',null,null,null,"+co.getScrollno()+",null,"+(rs.getDouble(2)+co.getAmount())+",null,'"+co.uv.getUserTml()+"','"+co.uv.getUserId()+"','"+co.uv.getUserDate()+"','"+co.uv.getVerTml()+"','"+co.uv.getVerId()+"','"+co.uv.getVerDate()+"','"+rs1.getString("int_uptodt")+"')")==0)
                    throw new SQLException();
                
                if(stmt.executeUpdate("update DayCash  set attached='T',trn_seq="+trn_seq+",ve_user='"+co.uv.getVerId()+"',ve_tml='"+co.uv.getVerTml()+"',ve_date='"+co.uv.getVerDate()+"' where scroll_no="+co.getScrollno()+" and trn_date='"+co.getTrndate()+"'")==0)
                    throw new SQLException();
                //ship
                if(stmt.executeUpdate("update ODCCMaster set last_tr_seq="+trn_seq+",last_tr_date='"+co.getTrndate()+"' where ac_type='"+co.getAcctype()+"' and ac_no="+co.getAccno()+"")==0)
                    throw new SQLException();
                //
            }
            
            ResultSet rs=null;
            GLTransObject trnobj=new GLTransObject();
            rs = stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1019001' and code=1");
            
            if(rs.next())
            {
                trnobj.setGLCode(rs.getString("gl_code"));
                trnobj.setCdind("C");
                trnobj.setTrnDate(co.getTrndate());
                trnobj.setGLType(rs.getString("gl_type"));
                trnobj.setTrnMode("C");
                trnobj.setAmount(co.getAmount());
                trnobj.setCdind(co.getCdind());
                //ship......28/06/2006
                trnobj.setAccType(co.getAcctype());
                trnobj.setAccNo(co.getAccno());
                trnobj.setTrnSeq(trn_seq);//ship......20/12/2005
                trnobj.setTrnType(co.getTrntype());
                /////////
                /*trnobj.setAccType("1019001");
                trnobj.setAccNo(null);
                trnobj.setTrnSeq(0);//ship......20/12/2005
                trnobj.setTrnType(co.getTrntype());*/
                trnobj.setRefNo(co.getScrollno());
                trnobj.setVtml(co.uv.getVerTml());
                trnobj.setVid(co.uv.getVerId());
                trnobj.setVDate(co.uv.getVerDate());
                
                common_local.storeGLTransaction(trnobj);
            }
                        
            if((code.startsWith("1002") || code.startsWith("1007") || code.startsWith("1017") || code.startsWith("1018")) && !co.getAccno().equals("0"))
            {
                //ship....27/12/2005
                int subcode = 0;
                Statement stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet rs_acmst = stmt1.executeQuery("select cid from AccountMaster where ac_type='"+co.getAcctype()+"' and ac_no="+co.getAccno()+"");
                
                if(rs_acmst.next())
                {
                    ResultSet rs_custmst = stmt1.executeQuery("select custtype from CustomerMaster where cid="+rs_acmst.getInt("cid")+"");
                    
                    if(rs_custmst.next())
                    {
                        int cust_type = rs_custmst.getInt("custtype");
                        if(cust_type==0)
                            subcode = 1;
                        else if(cust_type==1)
                            subcode = 2;
                        else
                            subcode = 1;
                        
                        System.out.println("inside SB/CA GLtran...subcode = "+subcode);
                        //rs=stmt.executeQuery("select gl_code from GLPost where ac_type='"+co.getGLRefCode()+"' and trn_type='"+co.getTrntype()+"' and cr_dr='"+co.getCdind()+"'");
                        rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+co.getGLRefCode()+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='C' and code="+subcode+"");
                        
                        if(rs.next())
                        {
                            //ship......20/12/2005
                            trnobj.setGLType(rs.getString(1));
                            trnobj.setGLCode(rs.getString(2));
                            trnobj.setCdind("C");
                            trnobj.setTrnDate(co.getTrndate());
                            trnobj.setTrnMode("C");
                            trnobj.setAmount(co.getAmount());
                            trnobj.setAccType(co.getAcctype());
                            trnobj.setAccNo(co.getAccno());
                            trnobj.setTrnSeq(trn_seq);
                            trnobj.setTrnType(co.getTrntype());
                            trnobj.setRefNo(co.getScrollno());
                            trnobj.setVtml(co.uv.getVerTml());
                            trnobj.setVid(co.uv.getVerId());
                            trnobj.setVDate(co.uv.getVerDate());
                            
                            common_local.storeGLTransaction(trnobj);
                            //
                        }
                    }
                }
                //
            }
            else if((code.startsWith("1014") || code.startsWith("1015")) && !co.getAccno().equals("0"))
            {
//              ship....27/12/2005
                int subcode = 0;
                Statement stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet rs_acmst = stmt1.executeQuery("select cid from ODCCMaster where ac_type='"+co.getAcctype()+"' and ac_no="+co.getAccno()+"");
                if(rs_acmst.next())
                {
                    ResultSet rs_custmst = stmt1.executeQuery("select custtype from CustomerMaster where cid="+rs_acmst.getInt("cid")+"");
                    if(rs_custmst.next())
                    {
                        int cust_type = rs_custmst.getInt("custtype");
                        if(cust_type==0)
                            subcode = 1;
                        else if(cust_type==1)
                            subcode = 2;
                        else
                            subcode = 1;
                        
                        System.out.println("inside OD/CC GLtran...subcode = "+subcode);
                        
                        rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+co.getGLRefCode()+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='C' and code="+subcode+"");
                        
                        if(rs.next())
                        {
                            trnobj.setGLType(rs.getString(1));
                            trnobj.setGLCode(rs.getString(2));
                            trnobj.setCdind("C");
                            trnobj.setTrnDate(co.getTrndate());
                            trnobj.setTrnMode("C");
                            trnobj.setAmount(co.getAmount());
                            trnobj.setAccType(co.getAcctype());
                            trnobj.setAccNo(co.getAccno());
                            trnobj.setTrnSeq(trn_seq);
                            trnobj.setTrnType(co.getTrntype());
                            trnobj.setRefNo(co.getScrollno());
                            trnobj.setVtml(co.uv.getVerTml());
                            trnobj.setVid(co.uv.getVerId());
                            trnobj.setVDate(co.uv.getVerDate());
                            
                            common_local.storeGLTransaction(trnobj);
                        }
                    }
                }
            }
            //ship.....21/12/2005
            if(code.startsWith("1004") && !co.getAccno().equals("0"))
            {
                //ship....27/12/2005
                int subcode = 0;
                Statement stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet rs_acmst = stmt1.executeQuery("select cid from DepositMaster where ac_type='"+co.getAcctype()+"' and ac_no="+co.getAccno()+"");
                if(rs_acmst.next())
                {
                    ResultSet rs_custmst = stmt1.executeQuery("select custtype from CustomerMaster where cid="+rs_acmst.getInt("cid")+"");
                    if(rs_custmst.next())
                    {
                        int cust_type = rs_custmst.getInt("custtype");
                        if(cust_type==0)
                            subcode = 1;
                        else if(cust_type==1)
                            subcode = 2;
                        else
                            subcode = 1;
                        
                        System.out.println("inside RD GLtran...subcode = "+subcode);
                        rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+co.getGLRefCode()+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='D' and cr_dr='C' and code="+subcode+"");
                        if(rs.next())
                        {
                            //ship......20/12/2005
                            trnobj.setGLType(rs.getString(1));
                            trnobj.setGLCode(rs.getString(2));
                            trnobj.setCdind("C");
                            trnobj.setTrnDate(co.getTrndate());
                            trnobj.setTrnMode("C");
                            trnobj.setAmount(co.getAmount());
                            trnobj.setAccType(co.getAcctype());
                            trnobj.setAccNo(co.getAccno());
                            trnobj.setTrnSeq(trn_seq);
                            trnobj.setTrnType(co.getTrntype());
                            trnobj.setRefNo(co.getScrollno());
                            trnobj.setVtml(co.uv.getVerTml());
                            trnobj.setVid(co.uv.getVerId());
                            trnobj.setVDate(co.uv.getVerDate());
                            
                            common_local.storeGLTransaction(trnobj);
                        }
                    }
                }
            }
            //
            
            /**
             * Added for locker on 27/07/2005-ravis
             */
            if(code.startsWith("1009"))
            {
            	  System.out.println("..............before calling rent by cash....");
                
                //cashobject=cash_remote.getData(co.getScrollno(),0);
                System.out.println("..ac type" +co.getAcctype());
                System.out.println("..ac no" +co.getAccno());
                System.out.println("..amt"+co.getAmount());
                
                
                System.out.println("..............after calling rent by cash....");
            
                if(stmt.executeUpdate("update DayCash  set ve_user='"+co.uv.getVerId()+"',ve_tml='"+co.uv.getVerTml()+"',ve_date='"+co.uv.getVerDate()+"',attached='T' where scroll_no="+co.getScrollno()+" and trn_date='"+co.getTrndate()+"'")==0)
                    throw new SQLException();
                
                boolean flag=rentCollectByCash(co.getAcctype(),Integer.parseInt(co.getAccno()),co.getAmount(),co.getTrndate());
                System.out.println("...flag..."+flag);
                
                rs=stmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type='"+co.getAcctype()+"'");
                
                if(rs.next())
                {
                    trnobj.setGLCode(rs.getString("gl_code"));
                    trnobj.setGLType(rs.getString("gl_type"));
                    trnobj.setCdind("D");
                    trnobj.setTrnDate(co.getTrndate());
                    trnobj.setTrnMode("C");
                    trnobj.setAmount(co.getAmount());
                    trnobj.setAccType(co.getAcctype());
                    trnobj.setAccNo(co.getAccno());
                    trnobj.setTrnSeq(0);
                    trnobj.setTrnType(co.getTrntype());
                    trnobj.setRefNo(co.getScrollno());
                    trnobj.setVtml(co.uv.getVerTml());
                    trnobj.setVid(co.uv.getVerId());
                    trnobj.setVDate(co.uv.getVerDate());
                 
                    if(common_local.storeGLTransaction(trnobj)==1)
                    {                 
                        rs=stmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type='1019001'");
                        rs.next();
                        
                        trnobj.setGLCode(rs.getString("gl_code"));
                        trnobj.setGLType(rs.getString("gl_type"));
                        trnobj.setCdind("C");
                        trnobj.setTrnDate(co.getTrndate());
                        trnobj.setTrnMode("C");
                        trnobj.setAmount(co.getAmount());
                        trnobj.setAccType(co.getAcctype());
                        trnobj.setAccNo(co.getAccno());
                        trnobj.setTrnSeq(0);
                        trnobj.setTrnType(co.getTrntype());
                        trnobj.setRefNo(co.getScrollno());
                        trnobj.setVtml(co.uv.getVerTml());
                        trnobj.setVid(co.uv.getVerId());
                        trnobj.setVDate(co.uv.getVerDate());
                        
                        common_local.storeGLTransaction(trnobj);
                    }
                }
            }
            //ship.......19/06/2006
            /*if(code.startsWith("1008") && !co.getAccno().equals("0"))
            {
                //ship.....27/12/2005
                //rs=stmt.executeQuery("select gl_code from GLPost where ac_type='"+co.getGLRefCode()+"' and trn_type='R' and cr_dr='C'");
                file_logger.info("inside RD GLtran...subcode = ");
                rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type='"+co.getGLRefCode()+"' and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='D' and cr_dr='C'");
                if(rs.next())
                {
                    trnobj.setGLCode(rs.getString(1));
                    trnobj.setAccType(co.getAcctype());
                    trnobj.setAccNo(co.getAccno());                    
                    trnobj.setCdind("C");
                    common_local.storeGLTransaction(trnobj);
                }
            }*/
            //ship.......13/06/2006
            /*if(code.startsWith("1010") && !co.getAccno().equals("0"))
            {
               if(othercharges>0)
                {
                    rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type="+co.getGLRefCode()+" and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='O' and cr_dr='C' and code=4");
                    if(rs.next())
                    {
                        trnobj.setGLType(rs.getString(1));
                        trnobj.setGLCode(rs.getString(2));
                        trnobj.setCdind("C");
                        trnobj.setTrnDate(co.getTrndate());
                        trnobj.setTrnMode("C");
                        trnobj.setAmount(othercharges*rs.getInt("mult_by"));
                        trnobj.setAccType(co.getAcctype());
                        trnobj.setAccNo(co.getAccno());
                        trnobj.setTrnSeq(trn_seq);
                        trnobj.setTrnType(co.getTrntype());
                        trnobj.setRefNo(co.getScrollno());
                        trnobj.setVtml(co.uv.getVerTml());
                        trnobj.setVid(co.uv.getVerId());
                        common_local.storeGLTransaction(trnobj);
                    }
                }
                
                if(penalintamt>0)
                {
                    rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type="+co.getGLRefCode()+" and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='C' and code=3");
                    if(rs.next())
                    {
                        trnobj.setGLType(rs.getString(1));
                        trnobj.setGLCode(rs.getString(2));
                        trnobj.setCdind("C");
                        trnobj.setTrnDate(co.getTrndate());
                        trnobj.setTrnMode("C");
                        trnobj.setAmount(penalintamt*rs.getInt("mult_by"));
                        trnobj.setAccType(co.getAcctype());
                        trnobj.setAccNo(co.getAccno());
                        trnobj.setTrnSeq(trn_seq);
                        trnobj.setTrnType(co.getTrntype());
                        trnobj.setRefNo(co.getScrollno());
                        trnobj.setVtml(co.uv.getVerTml());
                        trnobj.setVid(co.uv.getVerId());
                        common_local.storeGLTransaction(trnobj);
                    }
                }
                if(interestamt>0)
                {
                    rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type="+co.getGLRefCode()+" and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='C' and code=2");
                    if(rs.next())
                    {
                        trnobj.setGLType(rs.getString(1));
                        trnobj.setGLCode(rs.getString(2));
                        trnobj.setCdind("C");
                        trnobj.setTrnDate(co.getTrndate());
                        trnobj.setTrnMode("C");
                        trnobj.setAmount(interestamt*rs.getInt("mult_by"));
                        trnobj.setAccType(co.getAcctype());
                        trnobj.setAccNo(co.getAccno());
                        trnobj.setTrnSeq(trn_seq);
                        trnobj.setTrnType(co.getTrntype());
                        trnobj.setRefNo(co.getScrollno());
                        trnobj.setVtml(co.uv.getVerTml());
                        trnobj.setVid(co.uv.getVerId());
                        common_local.storeGLTransaction(trnobj);
                    }
                }
                file_logger.info("Principal amount"+prnamt);
                if(prnamt>0)
                {
                    file_logger.info("Goint inside");
                    rs=stmt.executeQuery("select gk.gl_type,gp.gl_code,mult_by from GLPost gp,GLKeyParam gk where gk.ac_type="+co.getGLRefCode()+" and gk.ac_type=gp.ac_type  and  gk.gl_code=gp.gl_code and trn_type='R' and cr_dr='C' and code=1");
                    if(rs.next())
                    {
                        trnobj.setGLType(rs.getString(1));
                        trnobj.setGLCode(rs.getString(2));
                        trnobj.setCdind("C");
                        trnobj.setTrnDate(co.getTrndate());
                        trnobj.setTrnMode("C");
                        trnobj.setAmount(prnamt*rs.getInt("mult_by"));
                        trnobj.setAccType(co.getAcctype());
                        trnobj.setAccNo(co.getAccno());
                        trnobj.setTrnSeq(trn_seq);
                        trnobj.setTrnType(co.getTrntype());
                        trnobj.setRefNo(co.getScrollno());
                        trnobj.setVtml(co.uv.getVerTml());
                        trnobj.setVid(co.uv.getVerId());
                        common_local.storeGLTransaction(trnobj);
                    }
                }
            }*/
            ///////////////
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new RecordNotInsertedException();
        }
        finally
        {
            conn.close();
          //  common_local=null;
            /*fc_local=null;
            loan_local=null;*/
        }
        
        return(true);
    }//end of VerifyDayCash......
    
    /**
     * Lists all the Cash Terminals details.
     */
    public TerminalObject[] getTerminalObject() throws SQLException
    {   
        TerminalObject array_terminalobject[]=null;
        Connection conn=null;
        
        try
        {
            ResultSet rs=null;
            conn=getConnection();
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //ship......commented to display only Sub-cashier terminals
            rs=stmt.executeQuery("select * from TerminalDetail where tml_type='M' or tml_type='C'");
            //rs=stmt.executeQuery("select * from TerminalDetail where tml_type='C'");
            //
            if(rs.last())
            {
                array_terminalobject=new TerminalObject[rs.getRow()];            
                System.out.println("i am in terminalobject......."+rs.getRow());
                rs.beforeFirst();
            }
            
            int j=0;
            
            while(rs.next())//for(int j=0;j<to.length();j++)
            {
                array_terminalobject[j]=new TerminalObject();
                array_terminalobject[j].setTerminals(rs.getString(1));
                array_terminalobject[j].setTerminalDesc(rs.getString(2));
                array_terminalobject[j].setTerminalType(rs.getString(3));
                array_terminalobject[j].setDataEntryUser(rs.getString(4));
                array_terminalobject[j].setDataDate(rs.getString(5));
                j++;
            }
        }
        catch(SQLException ex)
		{
        	ex.printStackTrace();
        }
        finally
        {
            conn.close();
        }
        
        return array_terminalobject;
    }
    
    
    /*public String getRunningBalance(String utml) throws SQLException
    {
        String s = "";
        Connection conn=null;
        try
        {
            conn=getConnection();
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=stmt.executeQuery("select run_bal from DayCash where de_tml='"+utml+"' and run_bal is not null and trn_date='"+getClientDate()+"' order by scroll_no");
            System.out.println(" in Runing Bal  "+getSysDate());
            //ResultSet rs = stmt.executeQuery("select netamt from Currency_Stock where tml_no='"+ utml + "' and netamt is not null");
            if(rs.last())
            {
                System.out.println("Runing Bal is "+rs.getString(1));
                s = rs.getString(1);            	
                return s;
            }
            
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            conn.close();
        }
        return "0.00";
    }*/
    
    /**
     * Gives the Current Running Balance of the given Cash Terminal.
     */
    //ship.......09/06/2006
    public double getCashTmlRunningBalance(String utml,String date) throws SQLException
    {
        double run_bal = 0.0;
        Connection conn=null;
        
        try
        {
            conn=getConnection();
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            ResultSet rs=stmt.executeQuery("select netamt from Currency_Stock where tml_no='"+utml+"' and rec_type='O' and cur_date='"+date+"'");
            
            if(rs.next())
            {
            	run_bal = rs.getDouble("netamt");
            }
            else
            {
                run_bal = -1;
            }
        } 
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch (SQLException se)
            {
                se.printStackTrace();
            }
        }
        
        return run_bal;
    }
    
    /**
     * Gives the Current Running Balance of all cash Terminals.
     */
    //ship.......06/11/2006
    public double getAllCashTmlRunningBalance(String date) throws SQLException
    {
        double total_run_bal = 0.0;
        Connection conn = null;
        ResultSet rs = null,rs_tml = null;
        
        try
        {
            conn=getConnection();
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            //ship.......22/12/2006
            rs_tml = stmt.executeQuery("select tml_code from TerminalDetail where tml_type='M'");
            
            if(rs_tml.last())
            {
                rs_tml.beforeFirst();
            }
            else
            {
                return total_run_bal;
            }
            
            while(rs_tml.next())
            {
                rs = stmt1.executeQuery("select netamt from Currency_Stock where cur_date='"+date+"' and tml_no='"+rs_tml.getString("tml_code")+"'");
                
                if(rs.next())
                {
                    total_run_bal = total_run_bal+rs.getDouble("netamt");
                }
            }
                
           // rs_tml = stmt.executeQuery("select tml_code from TerminalDetail where tml_type='C'");
            rs_tml = stmt.executeQuery("select tml_code from TerminalDetail where tml_type='C'");
            
            if(rs_tml.last())
            {
                rs_tml.beforeFirst();
            }
            else
            {
                return total_run_bal;
            }
            
            while(rs_tml.next())
            {
                rs=stmt1.executeQuery("select netamt from Currency_Stock where cur_date='"+date+"' and tml_no='"+rs_tml.getString("tml_code")+"' and rec_type='O'");
                
                if(rs.next())
                {
                    total_run_bal = total_run_bal+rs.getDouble("netamt");
                }
            }
            ///////////////
        } 
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch (SQLException se)
            {
                se.printStackTrace();
            }
        }
        
        return total_run_bal;
    }
    /////////
    
    //ship.......19/06/2006
    /*public void setRunningBalance(String rb,String utml) throws SQLException
	{
    	Connection conn=null;
    	
        try
        {
            conn=getConnection();
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if(stmt.executeUpdate("update Currency_Stock set netamt="+rb+" where tml_no='"+utml+"' and rec_type='O' and cur_date='"+getClientDate()+"'")==0)  
                throw new SQLException();
        } 
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            conn.close();
        }
	}*/
    ////////////////
    
    /**
     * Consolidated Scroll Report
     */
    public CashObject[] getDayCashData(String curdate,String tmlno,int flag,String query) throws SQLException,RecordsNotFoundException //used to access the data for the reports by giving the date and terminal
    {
        Statement stmt_vch = null;
        PreparedStatement ps = null;
        ResultSet rs = null,rs_vch = null;	
        Connection conn = null;
        CashObject array_cashobject[]=null;
        
        System.out.println("curdate "+curdate);
        System.out.println("tmlno "+tmlno);
        System.out.println("flag "+flag);
        System.out.println("query "+query);
        
        try
        {
            conn = getConnection();
            stmt_vch = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if(tmlno!=null)
            {
                System.out.println("hellooooooooooooo  1");
                
                if(flag==0)
                    //ship.......11/05/2006
                    //ps= conn.prepareStatement("select * from DayCash where trn_date=? and de_tml=? order by scroll_no");
                    ps= conn.prepareStatement("select * from DayCash where trn_date=? and tml_no=? order by scroll_no");
                else
                    //ship.......11/05/2006
                    //ps= conn.prepareStatement("select * from DayCash where trn_date=? and de_tml=? and ("+query+")");
                    ps= conn.prepareStatement("select * from DayCash where trn_date=? and tml_no=? and ("+query+")");
                
                ps.setString(1,curdate);
                ps.setString(2,tmlno);
                rs=ps.executeQuery();
            }
            else
            {
                System.out.println("hellooooooooooooo  2");
                if(flag==0)
                    ps = conn.prepareStatement("select * from DayCash where trn_date=?");
                else
                    ps = conn.prepareStatement("select * from DayCash where trn_date=? and ("+query+")");
                ps.setString(1,curdate);			
                rs=ps.executeQuery();
            }
            
            rs.last();
            System.out.println("hellooooooooooooo  3------>"+rs.getRow());
            
            if(rs.getRow()==0)
                throw new RecordsNotFoundException();
            else
            {
                array_cashobject=new CashObject[rs.getRow()];
                rs.beforeFirst();
                int i=0;
                //ship.....12/05/2006
                String ac_type = null,ac_no = null,name = null,vch_type = null;
                int vch_no = 0;
                //////////
                
                while(rs.next())
                {
                    System.out.println("i = "+i);
                    
                    ac_type = rs.getString("ac_type");
                    ac_no = rs.getString("ac_no");
                    name = rs.getString("name");
                    vch_type = rs.getString("vch_type");
                    vch_no = rs.getInt("vch_no");
                    
                    System.out.println("ac_type = "+ac_type);
                    System.out.println("ac_no = "+ac_no);
                    
                    array_cashobject[i]=new CashObject();
                    array_cashobject[i].setScrollno(rs.getInt("scroll_no"));			
                    array_cashobject[i].setAcctype(ac_type);		
                    array_cashobject[i].setAccno(ac_no);
                    array_cashobject[i].setTrntype(rs.getString("trn_type"));
                    array_cashobject[i].setOthtml(rs.getString("oth_tml"));				
                    array_cashobject[i].setAmount(rs.getFloat("csh_amt"));
                    array_cashobject[i].setCdind(rs.getString("cd_ind"));
                    
                    if(ac_type!=null && ac_no!=null)
                    {
                        array_cashobject[i].setAccname(name);
                        System.out.println("inside ac_type!=null && ac_no!=null");
                    }
                    else
                    {
                        System.out.println("inside ac_type==null && ac_no==null");
                        
                        rs_vch = stmt_vch.executeQuery("select narration from VoucherData where vch_no="+vch_no+" and vch_type='"+vch_type+"' and trn_date='"+curdate+"'");
                        
                        if(rs_vch.next())
                            array_cashobject[i].setAccname(rs_vch.getString("narration"));
                    }
                    
                    array_cashobject[i].setTokenno(rs.getInt("token_no"));
                    array_cashobject[i].setVchtype(vch_type);
                    array_cashobject[i].setVchno(vch_no);
                    array_cashobject[i].setTrnseq(rs.getInt("trn_seq"));
                    array_cashobject[i].setTrndate(rs.getString("trn_date"));
                    array_cashobject[i].setRunbal(rs.getFloat("run_bal"));
                    array_cashobject[i].setLockertype(rs.getString("locker_type"));
                    
                    array_cashobject[i].uv.setUserId(rs.getString("de_user"));
                    array_cashobject[i].uv.setUserTml(rs.getString("de_tml"));
                    array_cashobject[i].uv.setUserDate(rs.getString("de_date"));
                    
                    array_cashobject[i].uv.setVerId(rs.getString("ve_user"));
                    array_cashobject[i].uv.setVerDate(rs.getString("ve_date"));
                    array_cashobject[i].uv.setVerTml(rs.getString("ve_tml"));
                    
                    array_cashobject[i].setR1000(rs.getInt(25));
                    array_cashobject[i].setP1000(rs.getInt(26));
                    
                    array_cashobject[i].setR500(rs.getInt(27));
                    array_cashobject[i].setP500(rs.getInt(28));
                    
                    array_cashobject[i].setR100(rs.getInt(29));
                    array_cashobject[i].setP100(rs.getInt(30));
                    
                    array_cashobject[i].setR50(rs.getInt(31));
                    array_cashobject[i].setP50(rs.getInt(32));
                    
                    array_cashobject[i].setR20(rs.getInt(33));
                    array_cashobject[i].setP20(rs.getInt(34));
                    
                    array_cashobject[i].setR10(rs.getInt(35));
                    array_cashobject[i].setP10(rs.getInt(36));
                    
                    array_cashobject[i].setR5(rs.getInt(37));
                    array_cashobject[i].setP5(rs.getInt(38));
                    
                    array_cashobject[i].setR2(rs.getInt(39));
                    array_cashobject[i].setP2(rs.getInt(40));
                    
                    array_cashobject[i].setR1(rs.getInt(41));
                    array_cashobject[i].setP1(rs.getInt(42));
                    
                    array_cashobject[i].setRcoins(rs.getFloat(43));
                    array_cashobject[i].setPcoins(rs.getFloat(44));
                    
                    array_cashobject[i].setTerminalNo(rs.getString(45));
                    
                    i++;
                }
            }
        }
        catch(RecordsNotFoundException e)
        {
        	return null;
          //  throw new RecordsNotFoundException();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            conn.close();
        }
        
        return array_cashobject;
    }
    
    /**
     * Gives Day Opening & Closing Balance, Total Amount of Receipts & Payments
     * for the given date.
     */
    //ship.....22/05/2006......to get Day Opening Balance,Closing Balance,Receipts and Payments
    public CashObject getDayCashSummary(String curdate,String tmlno) throws SQLException
    { 
        Statement stmt_tml = null,stmt_openbal = null,stmt_rec = null,stmt_pay = null;
        ResultSet rs_tml = null,rs_daycash_openbal = null,rs_receipt = null,rs_payment = null;	
        Connection conn = null;
        CashObject cashobject = null;
        
        double open_amt = 0.00,rec_amt = 0.00,pay_amt = 0.00,close_amt = 0.00;
        
        System.out.println("curdate "+curdate);
        System.out.println("tmlno "+tmlno);
        
        try
        {
            conn = getConnection();
            cashobject = new CashObject();
            stmt_tml = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt_openbal = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt_rec = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt_pay = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if(tmlno!=null)
            {
                rs_tml = stmt_tml.executeQuery("select tml_type from TerminalDetail where tml_code='"+tmlno+"'");
                                
                if(rs_tml.next())
                {
                    System.out.println("inside Terminals");
                    
                    //ship.....01/11/2006
                    if(rs_tml.getString("tml_type").equals("M"))
                    {
                        rs_daycash_openbal = stmt_openbal.executeQuery("select netamt from Currency_Stock where concat(right(cur_date,4),'-',mid(cur_date,locate('/',cur_date)+1,(locate('/',cur_date,4)-locate('/',cur_date)-1)),'-',left(cur_date,locate('/',cur_date)-1)) < '"+Validations.convertYMD(curdate)+"' and rec_type='C' and tml_no='"+tmlno+"' order by concat(right(cur_date,4),'-',mid(cur_date,locate('/',cur_date)+1,(locate('/',cur_date,4)-locate('/',cur_date)-1)),'-',left(cur_date,locate('/',cur_date)-1)) desc");
                        
                           if(rs_daycash_openbal.next())
                           {
                               System.out.println("inside run bal");
                               open_amt = rs_daycash_openbal.getDouble("netamt");
                               cashobject.setDayOpeningBalance(open_amt);
                           }
                           else
                               cashobject.setDayOpeningBalance(open_amt);
                           
                           rs_receipt = stmt_rec.executeQuery("select csh_amt from DayCash where trn_date='"+curdate+"' and cd_ind='D'");
                           
                           while(rs_receipt.next())
                           {
                               System.out.println("inside Receipts");
                               rec_amt = rec_amt+rs_receipt.getDouble("csh_amt");
                           }
                           
                           cashobject.setDayReceipts(rec_amt);
                           
                           rs_payment = stmt_pay.executeQuery("select csh_amt from DayCash where trn_date='"+curdate+"' and cd_ind='C'");
                           
                           while(rs_payment.next())
                           {
                               System.out.println("inside Payments");
                               pay_amt = pay_amt+rs_payment.getDouble("csh_amt");
                           }
                           
                           cashobject.setDayPayments(pay_amt);
                           
                           close_amt = open_amt+rec_amt-pay_amt;
                           cashobject.setDayClosingBalance(close_amt);
                    }
                }
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            conn.close();
        }
        
        return cashobject;
    }
    /////////////
    
    /**
     * Gives the Net amount & Currency denominations for the given Terminal.
     */
    public CurrencyStockObject getCurrencyStockObject(String tml,String date,int flag) throws SQLException
    {
        CurrencyStockObject cso=new CurrencyStockObject();
        Connection conn=null;
        ResultSet rs=null;
        try
        {
            conn=getConnection();
            Statement stmt= conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if(flag==0)//Closing
                rs=stmt.executeQuery("select * from Currency_Stock where tml_no='"+tml+"' and cur_date='"+date+"' and rec_type='O'");
            else if(flag==1)//Currency Stock Updation
                rs=stmt.executeQuery("select * from Currency_Stock where tml_no='"+tml+"' and cur_date='"+date+"'");
            
            if(rs.next())
            {
                cso.setTmlNo(rs.getString("tml_no"));
                cso.setRecType(rs.getString("rec_type"));
                cso.setNetamt(rs.getString("netamt"));
                cso.sets1000(rs.getInt("s1000"));
                cso.sets500(rs.getInt("s500"));
                cso.sets100(rs.getInt("s100"));
                cso.sets50(rs.getInt("s50"));
                cso.sets20(rs.getInt("s20"));
                cso.sets10(rs.getInt("s10"));
                cso.sets5(rs.getInt("s5"));
                cso.sets2(rs.getInt("s2"));
                cso.sets1(rs.getInt("s1"));
                cso.setscoins(rs.getDouble("scoins"));
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            conn.close();
        }
        
        return cso;
    }
    
    //ship......01/11/2006
    /*public String[] getGLTypes() throws SQLException
    {
        String array_string_gltypes[] = null;
        Connection conn=null;
        try
        {
            conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select distinct gl_type from GLMaster order by gl_type");
            rs.last();
            array_string_gltypes = new String[rs.getRow()];
            rs.beforeFirst();
            int int_index = 0;
            while (rs.next())
            {
                array_string_gltypes[int_index] = rs.getString(1);
                int_index++;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            conn.close();
        }
        return array_string_gltypes;
    }*/
    
    /**
     * Miscellaneous Receipts - to get all the active GL Codes.
     * type - 0 : all glcodes
     *        1 : glcodes without cash gl
     */
    public int[] getGLCodes(String date,int type) throws SQLException
    {
        int glm[] = null;
        Connection conn = null;
        ResultSet rs = null;
        
        try
        {
            conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            //ship......01/11/2006
            //ResultSet rs=stmt.executeQuery("select gl_code from GLMaster order by gl_code");
            
            //ship....05/02/2007
            if(type==0)
                rs=stmt.executeQuery("select gl_code from GLMaster where ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"')) order by gl_code");
            else if(type==1)
                rs=stmt.executeQuery("select gl_code from GLMaster where ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"')) and gl_code not like '%000' and gl_code not like '201%' order by gl_code");
            //////////
            
            if(rs.last())
            {
                glm=new int[rs.getRow()];
                rs.beforeFirst();
            }
            
            int i=0;
            while(rs.next())
            {
                glm[i]=rs.getInt(1);
                i++;
            }
        }
        catch(SQLException e)
        {	
            e.printStackTrace();	
        }
        finally
        {
            conn.close();
        }
        return glm;
    }
    
    /**
     * Gives the GL Name for the given GL Code.
     */
    public String getGLName(String s1) throws SQLException
    {
        String Gl_name="";
        Connection conn=null;
        try
        {
            conn=getConnection();
            Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            ResultSet rs=st.executeQuery("select gl_name from GLMaster where gl_code='"+s1+"'");
            //rs.first();
            System.out.println("GL Code"+s1);
            while(rs.next())
            {
                Gl_name=rs.getString(1);
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            conn.close();
        }
        
        return Gl_name;
    }
    
    /**
     * Retrieves the details of the given voucher no from VoucherData. 
     */
    public VoucherDataObject getVoucherData(int vch_no,String vch_type,String date) throws SQLException,RecordsNotFoundException
    {
        VoucherDataObject voucherdataobject = null;
        Connection conn=null;
        
        try
        {
            conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           
            ResultSet rs = stmt.executeQuery("select * from VoucherData where vch_no="+vch_no+" and vch_type='"+vch_type+"' and cd_ind='D' and trn_date='"+date+"' limit 10");
            //ResultSet rs = stmt.executeQuery("select * from VoucherData where vch_no="+ vch_no + " and vch_type='"+vch_type+"' and cd_ind='C' and trn_date='"+getClientDate()+"' ");
           
            //   concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1))<='"+Validations.convertYMD(getSysDate())+"'
            rs.last();
            
            System.out.println("length in server" + rs.getRow());
            
            if(rs.getRow()==0)
                throw new RecordsNotFoundException();
                rs.beforeFirst();
                //int i = 0;
                while (rs.next())
                {
                    voucherdataobject = new VoucherDataObject();
                    voucherdataobject.setVoucherType(rs.getString("vch_type"));
                    voucherdataobject.setVoucherNo(rs.getInt("vch_no"));
                    voucherdataobject.setGlType(rs.getString("gl_sl_type"));
                    voucherdataobject.setGlCode(rs.getInt("gl_sl_code"));
                    
                    voucherdataobject.setChequeNo(rs.getInt("chq_no"));
                    voucherdataobject.setChequeDate(rs.getString("chq_dt"));
                    
                    voucherdataobject.setTransactionAmount(rs.getDouble("trn_amt"));
                    
                    System.out.println("trn_amt==="+rs.getDouble("trn_amt"));
                    
                    voucherdataobject.setDataEntryUser(rs.getString("de_user"));
                    
                    voucherdataobject.obj_userverifier.setUserId(rs.getString("de_user"));
                    voucherdataobject.obj_userverifier.setUserTml(rs.getString("de_tml"));
                    voucherdataobject.obj_userverifier.setUserDate(rs.getString("de_date"));
                    
                    voucherdataobject.obj_userverifier.setVerId(rs.getString("ve_user"));
                    voucherdataobject.obj_userverifier.setVerTml(rs.getString("ve_tml"));
                    voucherdataobject.obj_userverifier.setVerDate(rs.getString("ve_date"));
                    
                    voucherdataobject.setDataEntryUserDateTime(rs.getString("de_date"));
                    voucherdataobject.setVerifiedUserDateTime(rs.getString("ve_date"));
                    
                    // i++;
                }
            
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            conn.close();
        }
        return voucherdataobject;
    }
    
    /**
     * Retrieves the rows corresponding to the given voucher no from VoucherData.
     */
    public VoucherDataObject[] getArrayVoucherData(int int_vch_no,String vch_type,String date)throws SQLException,RecordsNotFoundException
    {   
        VoucherDataObject voucherdataobject[] = null;
        Connection conn=null;
        System.out.println("vch_no of array  in server" +int_vch_no);
        System.out.println("vch_type of array in server" + vch_type);
        
        try
		{
            ResultSet rs = null;
            conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //ship.....
            //vch_type == R
            if(vch_type.equals("R"))
            {
            	System.out.println("Error barta ide!!!!!!!!");
            	rs = stmt.executeQuery("select * from VoucherData where vch_no="+int_vch_no+ " and vch_type='"+vch_type+"' and cd_ind='C' and trn_date='"+date+"' and trn_amt>0 limit 10 ");
            	rs.next();
            	
            	if(rs.getRow()==0)
            		throw new RecordsNotFoundException();
            	else
            	{
            		voucherdataobject = new VoucherDataObject[rs.getRow()];
            		rs.beforeFirst();
            		int i = 0;
                
            		while(rs.next())
            		{
            			voucherdataobject[i] = new VoucherDataObject();
                    
            			voucherdataobject[i].setVoucherType(rs.getString("vch_type"));
            			System.out.println("VoucherType---------->"+ rs.getString("vch_type"));
            			voucherdataobject[i].setVoucherNo(rs.getInt("vch_no"));
            			System.out.println("VoucherNO---------->"+ rs.getString("vch_no"));
            			voucherdataobject[i].setGlType(rs.getString("gl_sl_type"));
            			System.out.println("GL Type---------->"+ rs.getString("gl_sl_type"));
            			voucherdataobject[i].setGlCode(rs.getInt("gl_sl_code"));
            			System.out.println("VoucherType---------->"+ rs.getString("gl_sl_code"));
            			voucherdataobject[i].setTransactionAmount(rs.getDouble("trn_amt"));
            			System.out.println("VoucherType---------->"+ rs.getString("trn_amt"));
            			
            			voucherdataobject[i].obj_userverifier.setUserId(rs.getString("de_user"));
            			voucherdataobject[i].obj_userverifier.setUserTml(rs.getString("de_tml"));
            			voucherdataobject[i].obj_userverifier.setVerId(rs.getString("ve_user"));
            			voucherdataobject[i].obj_userverifier.setVerTml(rs.getString("ve_tml"));
            			voucherdataobject[i].obj_userverifier.setUserDate(rs.getString("de_date"));
            			voucherdataobject[i].obj_userverifier.setVerDate(rs.getString("ve_date"));
            			String glName=getGLName(String.valueOf(voucherdataobject[i].getGlCode()));
            			System.out.println("Not getinggggg whether error is b4...........");
            			System.out.println("*************"+glName);
            			    System.out.println("Not getinggggg where is error...........");     			
            			i++;
            		}
            	}
            }
            else if(vch_type.equals("P"))
            {
            	rs = stmt.executeQuery("select * from VoucherData where vch_no="+int_vch_no+ " and vch_type='"+vch_type+"' and cd_ind='D' and trn_date='"+date+"'");
                rs.last();
                
                if(rs.getRow()==0)
                    throw new RecordsNotFoundException();
                else
                {
                    voucherdataobject = new VoucherDataObject[rs.getRow()];
                    rs.beforeFirst();
                    int i = 0;
                    
                    while(rs.next())
                    {
                        voucherdataobject[i] = new VoucherDataObject();
                        
                        voucherdataobject[i].setVoucherType(rs.getString("vch_type"));
                        voucherdataobject[i].setVoucherNo(rs.getInt("vch_no"));
                        voucherdataobject[i].setGlType(rs.getString("gl_sl_type"));
                        voucherdataobject[i].setGlCode(rs.getInt("gl_sl_code"));
                        voucherdataobject[i].setTransactionAmount(rs.getDouble("trn_amt"));
                        
                        voucherdataobject[i].obj_userverifier.setUserId(rs.getString("de_user"));
                        voucherdataobject[i].obj_userverifier.setUserTml(rs.getString("de_tml"));
                        voucherdataobject[i].obj_userverifier.setVerId(rs.getString("ve_user"));
                        voucherdataobject[i].obj_userverifier.setVerTml(rs.getString("ve_tml"));
                        voucherdataobject[i].obj_userverifier.setUserDate(rs.getString("de_date"));
                        voucherdataobject[i].obj_userverifier.setVerDate(rs.getString("ve_date"));
                        
                        i++;
                    }
                }
            }
            //
        } 
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            conn.close();
        }
        
        
        return voucherdataobject;
    }
 
    
    /**
     * Voucher Payment - Retrieves the details of the given voucher no from VoucherData. 
     */
    public VoucherDataObject getPaymentData(int int_vch_no,String vch_type,String date)throws SQLException,RecordsNotFoundException
    {
        VoucherDataObject voucherdataobject = null;
        Connection conn=null;
        try
        {
            ResultSet rs = null,rs_day=null;
            conn=getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            Statement stmt_day = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs_day=stmt_day.executeQuery("select scroll_no from DayCash where vch_no="+int_vch_no+" and vch_type='"+vch_type+"' and trn_date='"+date+"'");
            
            if(rs_day.next())
            {
                rs = stmt.executeQuery("select * from VoucherData where vch_no="+int_vch_no+" and vch_type='"+vch_type+"' and cd_ind='C' and trn_date='"+date+"' and ve_user is not null");
                
                voucherdataobject = new VoucherDataObject();
                
                while (rs.next())
                {                    
                    voucherdataobject.setVoucherType(rs.getString("vch_type"));
                    voucherdataobject.setVoucherNo(rs.getInt("vch_no"));
                    System.out.println("in getPaymentData.... vch_no frmo db   "+rs.getInt("vch_no"));
                    voucherdataobject.setGlType(rs.getString("gl_sl_type"));
                    voucherdataobject.setGlCode(rs.getInt("gl_sl_code"));
                    voucherdataobject.setTransactionAmount(rs.getDouble("trn_amt"));
                    voucherdataobject.setNarration(rs.getString("narration"));
                    voucherdataobject.setTransactionDate(rs.getString("trn_date"));
                    
                    voucherdataobject.obj_userverifier.setUserId(rs.getString("de_user"));
                    voucherdataobject.obj_userverifier.setUserTml(rs.getString("de_tml"));
                    voucherdataobject.obj_userverifier.setVerId(rs.getString("ve_user"));
                    voucherdataobject.obj_userverifier.setVerTml(rs.getString("ve_tml"));
                    voucherdataobject.obj_userverifier.setUserDate(rs.getString("de_date"));
                    voucherdataobject.obj_userverifier.setVerDate(rs.getString("ve_date"));
                  
                   String glName=getGLName(String.valueOf(voucherdataobject.getGlCode()));
        			System.out.println("*************"+glName);
        			
                }
            }
        } 
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            conn.close();
        }
        
        return voucherdataobject;
    }
    
    /**
     * Gives the GL Name for the corresponding GL Code.
     */
    public String getGlName(int gl_code) throws SQLException
    {
        String glname=null;
        Connection conn=null;
        
        try
        {
            conn=getConnection();
            Statement stmt= conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=stmt.executeQuery("select gl_name from GLMaster where gl_code="+gl_code+" ");
            while(rs.next())
            {
                glname=rs.getString(1);
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
        
        return glname;
    }
    
    //ship......16/06/2006
    /*String getSysDate() 
    {
        Calendar c=Calendar.getInstance();
        try {
			return(Validations.checkDate(c.get(Calendar.DATE)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR)));
		} catch (DateFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    public String getSysDateTime() 
    {
        Calendar c=Calendar.getInstance();
        
        //ship......06/05/2006
        String str=String.valueOf(c.get(Calendar.SECOND));
        if(str.length()==1)
            str="0"+str;
        
        String str1=String.valueOf(c.get(Calendar.MINUTE));
        if(str1.length()==1)
            str1="0"+str1;
        
        String str2=String.valueOf(c.get(Calendar.HOUR));
        if(str2.length()==1)
            str2="0"+str2;
        ////////////
                
        try 
        {
            return(Validations.checkDate(c.get(Calendar.DATE)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR))+" "+new Date().getHours()+":"+new Date().getMinutes()+":"+new Date().getSeconds()+"");
		} 
        catch (DateFormatException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return null;
    }*/
    
    
    /**
     * Gives the Locker Rent to be collected for the given Locker Type.
     */
    //for collecting Locker Rent
    public double getLockerRent(String string_locker_ac_type,int int_locker_ac_no,String date) throws SQLException
    {   
       
        double double_locker_rent=0.0;
        Connection conn=null;
        ResultSet rs=null,rs1=null;
        try
        {
            conn=getConnection();
            Statement stmt= conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            //ship.....23/02/2007
            //rs=stmt.executeQuery("select locker_ty,locker_no,allot_dt,rent_upto from LockerMaster where ac_no="+int_locker_ac_no+" and ac_type='"+string_locker_ac_type+"'");
            rs = stmt.executeQuery("select lm.locker_ty,lm.locker_no,lm.allot_dt,lt.trn_seq,lt.rent_by,lt.trf_acty,lt.trf_acno,lt.rent_amt,lt.rent_upto from LockerMaster lm,LockerTransaction lt where lm.ac_no="+int_locker_ac_no+" and lm.ac_type='"+string_locker_ac_type+"' and lm.ac_no=lt.ac_no and lm.ac_type=lt.ac_type and lt.rent_by is not null and rent_amt is not null and rent_upto is not null order by lt.trn_seq desc");
            ////////

            if(rs.next())
            {
                
                String string_rent_upto=Validations.convertYMD(rs.getString("rent_upto"));
                String string_rent_upto_dt=rs.getString("rent_upto");
                
                int days = Validations.dayCompare(string_rent_upto_dt,date);
               
                
                if(days>=0)//ship.....02/01/2006
                {
                    rs1=stmt.executeQuery("select locker_rate from LockerRate where locker_type='"+rs.getString("locker_ty")+"' and '"+string_rent_upto+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)) and "+days+" between days_fr and days_to");
                    //double double_rent_year=0.0;
                    if(rs1.next())
                    {
                                              double_locker_rent=rs1.getDouble("locker_rate");
                    }
                    else
                        double_locker_rent = 0.0;
                }
                else
                {
                    rs1=stmt.executeQuery("select locker_rate from LockerRate where locker_type='"+rs.getString("locker_ty")+"' and '"+Validations.convertYMD(date)+"' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
                    if(rs1.next())
                    {
                       
                        double_locker_rent=rs1.getDouble("locker_rate");
                    }
                    else
                        double_locker_rent = 0.0;
                }
                
                
             //   int int_months=common_local.getNoOfMonths(rs.getString("allot_dt"),Validations.convertYMD(getSysDate()));
                
                    /*rs2=stmt.executeQuery("select (year('"+Validations.convertYMD(getSysDate())+"')-year('"+string_rent_upto+"'))");
                
                    if(rs2.next())
                    {
                        int int_no_years=rs2.getInt(1);
                    
                        file_logger.info("....no of years..."+int_no_years);
                        
                        if(int_no_years==0)
                            double_locker_rent=rs.getDouble(1);
                        else if(int_no_years>0)
                        {
                                String date1=common_local.getFutureMonthDate(string_allot_date_month,12*int_no_years);
                                
                                file_logger.info(".....date1....."+date1);
                                
                                if(date1.equals(Validations.convertYMD(getSysDate())))
                                {
                                    file_logger.info("...inside if....");
                                    double_locker_rent=double_rent_year*int_no_years;
                                }
                                else
                                {
                                    file_logger.info("...inside else....");
                                    double_locker_rent=double_rent_year*(int_no_years+1);
                                }
                        }
                    }*/
                
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
        return double_locker_rent;
    }
    
    /**
     * Gives the Locker Type of the given Locker A/c No.
     */
    public String getLockerType(String string_locker_ac_type,int int_locker_ac_no) throws SQLException
    {
        System.out.println("string_locker_ac_type == "+string_locker_ac_type);
        
        Connection conn=null;
        ResultSet rs=null;
        String string_locker_type=null;
        try
        {
            conn=getConnection();
            Statement stmt= conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs=stmt.executeQuery("select locker_ty from LockerMaster where ac_no="+int_locker_ac_no+" and ac_type='"+string_locker_ac_type+"'");
  
            if(rs.next())
            {
                string_locker_type=rs.getString("locker_ty");
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

        System.out.println(".......before return..."+string_locker_type);
        return string_locker_type;
    }
    
    /**
     * Updates the Rent Collected, Rent Upto in LockerMaster while Verifying the scroll no.
     */
    public boolean rentCollectByCash(String string_ac_type,int int_ac_no,double double_rent,String date) 
    {   
        
        Connection  conn=null;
        Statement stmt=null;
     
        try
        {
            conn=getConnection();
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            if(conn==null)
                throw new SQLException();
        
            /**
             * 
             * Here i'm not updating de_user,de_date,de_tml u clarify with sir.
             */
            if(stmt.executeUpdate("update LockerMaster set rent_coll=rent_coll+"+double_rent+",rent_upto='"+date+"' where ac_no="+int_ac_no+" and ac_type='"+string_ac_type+"'")==0)
                throw new SQLException();
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
        return true;
    }
    
    /**
     * Accepts the money sent by other cash terminal.
     */
    //ship......4 intercounter transfer....06/10/2005
    public int AcceptMoney(CashObject co,String utml,String scroll)
    {
    	System.out.println("inside AcceptMoney......");
    	Connection conn = null;
        Statement stmt = null;
        PreparedStatement ps = null;
        ResultSet res = null,res_cs = null;
        int scno = 0;
        
    	if(co.getTrntype().equals("A"))
    	{
    		try
			{
    			System.out.println("inside try....");
    			double runbal = 0.0;
    			conn = getConnection();
    			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                
    			res = stmt.executeQuery("select * from DayCash where scroll_no="+scroll+" and oth_tml='"+utml+"' and trn_date='"+co.getTrndate()+"'");
    			
    			if(res.next())
    			{
    				String tfrngtml = res.getString("de_tml");//transfering tml
    				System.out.println("running...balance.....= "+res.getDouble("run_bal"));
    				System.out.println("cashobj...runbal...= "+co.getRunbal());
    				    				
    				if((stmt.executeUpdate("update Currency_Stock set s1000=s1000+"+(res.getInt("p1000")-res.getInt("r1000"))+",s500=s500+"+(res.getInt("p500")-res.getInt("r500"))+",s100=s100+"+(res.getInt("p100")-res.getInt("r100"))+",s50=s50+"+(res.getInt("p50")-res.getInt("r50"))+",s20=s20+"+(res.getInt("p20")-res.getInt("r20"))+",s10=s10+"+(res.getInt("p10")-res.getInt("r10"))+",s5=s5+"+(res.getInt("p5")-res.getInt("r5"))+",s2=s2+"+(res.getInt("p2")-res.getInt("r2"))+",s1=s1+"+(res.getInt("p1")-res.getInt("r1"))+",scoins=scoins+"+(res.getInt("pcoins")-res.getInt("rcoins"))+",netamt=netamt+"+res.getDouble("csh_amt")+" where tml_no='"+utml+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'"))==0) // add the money to the transfered terminal
                        throw new SQLException();
    				
    				if((stmt.executeUpdate("update DayCash set trn_type='A',ve_user='"+co.uv.getUserId()+"',ve_tml='"+utml+"',ve_date='"+co.uv.getUserDate()+"' where scroll_no="+scroll+" and oth_tml='"+utml+"' and trn_date='"+co.getTrndate()+"'"))==0)
    					throw new SQLException();
    				
    				if(res.getDouble("run_bal")==0.00)//for terminal closing....18/10/2005
    				{
    					if(stmt.executeUpdate("update Currency_Stock set s1000="+0+",s500="+0+",s100="+0+",s50="+0+",s20="+0+",s10="+0+",s5="+0+",s2="+0+",s1="+0+",scoins="+0.00+",netamt="+0.00+",rec_type='C' where tml_no='"+tfrngtml+"' and cur_date='"+co.getTrndate()+"' and rec_type='P'")==0)
    	                    throw new SQLException();
    				}
                    
                    res_cs = stmt.executeQuery("select netamt from Currency_Stock where tml_no='"+utml+"' and cur_date='"+co.getTrndate()+"' and rec_type='O'");
                    
                    if(res_cs.next())
                        runbal = res_cs.getDouble(1);
                     
                    System.out.println("...runbal...= "+runbal);
                    co.setRunbal(runbal);
    				
    				res = stmt.executeQuery("select lst_acc_no,lst_rct_no from Modules where modulecode='1019000'");
                    
                    //ship.......05/01/2007
                    if(res.next())
                    {
                        System.out.println("scroll no before update"+res.getInt(1));
                        System.out.println("vch no before update"+res.getInt(2));
                        
                        scno=res.getInt(1)+1;   
                        
                        ps=conn.prepareStatement("insert into DayCash(scroll_no,trn_date,trn_type,vch_no,csh_amt,cd_ind,trn_seq,attached,de_user,de_tml,de_date,r1000,r500,r100,r50,r20,r10,r5,r2,r1,rcoins,p1000,p500,p100,p50,p20,p10,p5,p2,p1,pcoins,tml_no,oth_tml,run_bal) values("+scno+",'"+co.getTrndate()+"','B',?,?,'D',0,'F','"+co.uv.getUserId()+"','"+utml+"','"+co.uv.getUserDate()+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+utml+"','"+tfrngtml+"',?)");
                        
                        ps.setString(1,"0");
                        ps.setDouble(2,co.getAmount());
                        
                        //ship.....to store paid currencies to received currencies
                        ps.setInt(3,co.getP1000());
                        ps.setInt(4,co.getP500());
                        ps.setInt(5,co.getP100());
                        ps.setInt(6,co.getP50());
                        ps.setInt(7,co.getP20());
                        ps.setInt(8,co.getP10());
                        ps.setInt(9,co.getP5());
                        ps.setInt(10,co.getP2());
                        ps.setInt(11,co.getP1());
                        ps.setDouble(12,co.getPcoins());
                        
                        ps.setInt(13,co.getR1000());
                        ps.setInt(14,co.getR500());
                        ps.setInt(15,co.getR100());
                        ps.setInt(16,co.getR50());
                        ps.setInt(17,co.getR20());
                        ps.setInt(18,co.getR10());
                        ps.setInt(19,co.getR5());
                        ps.setInt(20,co.getR2());
                        ps.setInt(21,co.getR1());
                        ps.setDouble(22,co.getRcoins());
                        
                        ps.setDouble(23,co.getRunbal());
                        
                        ps.executeUpdate();
                        
                        if((stmt.executeUpdate("update Modules set lst_acc_no="+scno+" where modulecode='1019000'"))==0)
                            throw new SQLException();
                    }
                    /////////
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
    	}
    	
    	return scno;
    }
    
    /**
     * Consolidated Scroll Report - to get the short names of the A/c type.
     */
    //ship......added on 24/10/2005...to get the short names of the acc_type
    public String getAcctypename(String acctypecode) throws SQLException
	{
    	System.out.println("inside getAcctypename......");
    	Connection conn = null;
        Statement stmt = null;
        ResultSet res = null;
        String accname = "";
        String acctype = "";
        acctype = acctypecode;
        
        try
		{
        	conn=getConnection();
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			res = stmt.executeQuery("select moduleabbr from Modules where modulecode='"+acctype+"'");
			
        	if(res.next())
        	    accname = res.getString(1);
        	
        	System.out.println("accname = "+accname);
		}
        catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
		}       
        finally
		{
            conn.close();
        }
        
        return accname;
	}
    
    /**
     * Gives the Customer Subcategory.
     */
    //ship......added on 21/11/2005...to get the Customer Sub Category
    public String getCustSubCat(String cust_acctype,String cust_accno) throws SQLException
	{   
    	
    	Connection conn = null;
        Statement stmt = null;
        ResultSet res = null;
        String cust_subcat = "";
        System.out.println("inside getsubcategory==>"+cust_acctype+"  & customer no==>"+cust_accno);
        
        try
		{
        	conn=getConnection();
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			if(cust_acctype.startsWith("1002") || cust_acctype.startsWith("1007") || cust_acctype.startsWith("1014") || cust_acctype.startsWith("1015") || cust_acctype.startsWith("1017") || cust_acctype.startsWith("1018"))
			    res = stmt.executeQuery("select subcatdesc from AccountSubCategory where subcatcode=(select sub_category from CustomerMaster where cid=(select cid from AccountMaster where ac_type="+cust_acctype+" and ac_no="+cust_accno+"))");
			else if(cust_acctype.startsWith("1003") || cust_acctype.startsWith("1004") || cust_acctype.startsWith("1005"))
			    res = stmt.executeQuery("select subcatdesc from AccountSubCategory where subcatcode=(select sub_category from CustomerMaster where cid=(select cid from DepositMaster where ac_type="+cust_acctype+" and ac_no="+cust_accno+"))");
			else if(cust_acctype.startsWith("1008") || cust_acctype.startsWith("1010"))
			    res = stmt.executeQuery("select subcatdesc from AccountSubCategory where subcatcode=(select sub_category from CustomerMaster where cid=(select cid from LoanMaster where ac_type="+cust_acctype+" and ac_no="+cust_accno+"))");
			else if(cust_acctype.startsWith("1009"))
			    res = stmt.executeQuery("select subcatdesc from AccountSubCategory where subcatcode=(select sub_category from CustomerMaster where cid=(select cid from LockerMaster where ac_type="+cust_acctype+" and ac_no="+cust_accno+"))");
			else if(cust_acctype.startsWith("1006"))
			    res = stmt.executeQuery("select subcatdesc from AccountSubCategory where subcatcode=(select sub_category from CustomerMaster where cid=(select cid from PygmyMaster where ac_type="+cust_acctype+" and ac_no="+cust_accno+"))");
			else if(cust_acctype.startsWith("1001"))
			    res = stmt.executeQuery("select subcatdesc from AccountSubCategory where subcatcode=(select sub_category from CustomerMaster where cid=(select cid from ShareMaster where ac_type="+cust_acctype+" and ac_no="+cust_accno+"))");
			else if(cust_acctype.startsWith("1013"))
			    res = stmt.executeQuery("select subcatdesc from AccountSubCategory where subcatcode=(select sub_category from CustomerMaster where cid=(select cid from AgentMaster where ac_type="+cust_acctype+" and ac_no="+cust_accno+"))");
			
        	if(res.next());
        		cust_subcat = res.getString("subcatdesc");
        	
		}
        catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
		}       
        finally
		{
            conn.close();
        }
        
        return cust_subcat;
	}
    
    /**
     * Miscellaneous Receipts - to get GL Code, GL Type.
     */
    //ship......10/01/2006.....to get gl_code and gl_type for Misc. Rec
    public int[] getGLParam() throws SQLException
    {
        int GLParam[] = new int[2];
        System.out.println("inside getGLParam......");
    	Connection conn = null;
        Statement stmt = null;
        ResultSet res = null;
        
        try
        {
            conn=getConnection();
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			res = stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1019001' and code=1");
			if(res.next())
			{
			    System.out.println("inside getGLParam...gl_type = "+res.getInt("gl_type"));
			    System.out.println("inside getGLParam...gl_code = "+res.getInt("gl_code"));
			    GLParam[0] = res.getInt("gl_type");
				GLParam[1] = res.getInt("gl_code");
			}
        }
        catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
		}       
        finally
		{
            conn.close();
        }
        return GLParam;
    }
    
    /**
     * Cash Closing - to check whether the net amount in Currency_Stock is matching 
     * with the Closing balance of GLTransaction. 
     */
    //ship......23/05/2006
    public boolean checkClosingBalance(String main_tml_no,String date) throws SQLException
    {
    	System.out.println("inside check closing balance");
        
        boolean close = false;
        int GLParam[] = new int[2];
        double yester = 0,rec = 0,pay = 0,today = 0;
        
        Connection conn = null;
        Statement stmt_daycash = null,stmt_glrec = null,stmt_glpay = null;
        ResultSet res_daycash = null,res_glrec = null,res_glpay = null;
        
        try
        {
            conn = getConnection();
            
			stmt_daycash = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt_glrec = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt_glpay = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
            GLParam = getGLParam();
            
            System.out.println("gl_type = "+GLParam[0]);
            System.out.println("gl_code = "+GLParam[1]);
		    System.out.println("date = "+date);
            System.out.println("Validations.addDays(date,-1) == "+Validations.addDays(date,-1));
		    
            yester = gl_remote.ClosingBalance(String.valueOf(GLParam[0]),GLParam[1],Validations.addDays(date,-1));
            
            System.out.println("yesterday's closing balance = "+yester);
            
            res_daycash = stmt_daycash.executeQuery("select netamt from Currency_Stock where tml_no='"+main_tml_no+"' and cur_date='"+date+"' and rec_type='O'");
            
            if(res_daycash.next())
            {
                today = res_daycash.getDouble("netamt");
                
                System.out.println("today's closing balance = "+today);
                
                res_glrec = stmt_glrec.executeQuery("select trn_amt from GLTransaction where trn_date='"+date+"' and gl_type='"+String.valueOf(GLParam[0])+"' and gl_code="+GLParam[1]+" and cd_ind='D'");
                
                while(res_glrec.next())
                {
                	System.out.println("inside Receipts");
                    rec = rec+res_glrec.getDouble("trn_amt");
                }
                
                System.out.println("Receipts = "+rec);
                
                res_glpay = stmt_glpay.executeQuery("select trn_amt from GLTransaction where trn_date='"+date+"' and gl_type='"+String.valueOf(GLParam[0])+"' and gl_code="+GLParam[1]+" and cd_ind='C'");
                
                while(res_glpay.next())
                {
                	System.out.println("inside Payments");
                    pay = pay+res_glpay.getDouble("trn_amt");
                }
                
                System.out.println("Payments = "+pay);
                
                System.out.println("yester+rec-pay = "+((yester*(-1))+rec-pay));
                
                if(today==((yester*(-1))+rec-pay))
                    close = true;
            }
        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }
        catch(RemoteException re)
        {
            re.printStackTrace();
        }
        catch(DateFormatException de)
        {
            de.printStackTrace();
        }
        finally
		{
            conn.close();
        }
        
        return close;
    }
    
    /**
     * General Receipt - to check whether the PAG scroll no is already used or not
     */
    //ship.....25/01/2006
    public boolean checkPAG(int sc_no,String date) throws SQLException
    {
        boolean attach = false;
        Connection conn = null;
        Statement stmt = null;
        ResultSet res = null;
        try
        {
            conn=getConnection();
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
            res = stmt.executeQuery("select * from DayCash where scroll_no="+sc_no+" and trn_date='"+date+"' and ve_tml is null and ve_user is null and ve_date is null");
            if(res.next())
            {
                System.out.println("res.getString(attached) = "+res.getString("attached"));
                if(String.valueOf(res.getString("attached")).equals("T"))
                    attach = true;
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
            catch(SQLException sqlexception)
    		{
    			sqlexception.printStackTrace();
    		} 
        }
        System.out.println("attach = "+attach);
        return attach;
    }
    
    /**
     * Checks if any Receipts are Pending before closing the sub cash terminals.
     */
    //ship......25/05/2006
    public int checkReceiptPending(String sub_tml_no,String date) throws SQLException
    {
        int rec_pending = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try
        {
            conn=getConnection();
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
            rs=stmt.executeQuery("select * from DayCash where scroll_no>0 and run_bal>0 and ac_type>0 and ac_no>0 and tml_no='"+sub_tml_no+"' and trn_date='"+date+"' and ve_user is null and ve_tml is null and ve_date is null");
            
            if(rs.next())
            {
                System.out.println("inside existing a/c");
                rec_pending = 1;//Existing A/c
            }
            else
            {
            	rs=stmt.executeQuery("select * from DayCash where scroll_no>0 and run_bal>0 and ac_type>0 and ((ac_no=0 and ve_user is null and ve_tml is null and ve_date is null) or ac_no=0) and tml_no='"+sub_tml_no+"' and trn_date='"+date+"'");
            	
            	if(rs.next())
            	{
            		System.out.println("inside new a/c");
            	    rec_pending = 2;//New A/c
            	}
            	else
            	{
            	    rs=stmt.executeQuery("select * from DayCash where scroll_no>0 and run_bal>0 and ac_type is null and ac_no is null and trn_type!='B' and tml_no='"+sub_tml_no+"' and trn_date='"+date+"' and ve_user is null and ve_tml is null and ve_date is null");
                	
                	if(rs.next())
                	{
                		System.out.println("inside misc rec a/c");
                	    rec_pending = 3;//Misc Rec
                	}
                	else
                	    rec_pending = 0;//No Pending
            	}
            }
        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }
        finally
		{
            try
            {
                conn.close();
            }
            catch(SQLException sqlexception)
    		{
    			sqlexception.printStackTrace();
    		} 
        }
        
        return rec_pending;
    }
    
    /**
     * Checks if any Payments are pending before closing the Main Cash Terminal.
     */
    //ship.......01/06/2006
    public int checkPaymentPending(String sub_tml_no,String date) throws SQLException
    {
    	 int pay_pending = 0;
         Connection conn = null;
         Statement stmt = null;
         ResultSet rs = null,rs1 = null;
         
         try
         {
             conn=getConnection();
 			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
 			
 			System.out.println("no payment pending");
             
             rs1 = stmt.executeQuery("select cs.rec_type from Currency_Stock cs,TerminalDetail t where cs.tml_no=t.tml_code and t.tml_type='C' and cs.cur_date='"+date+"'");
             
             while(rs1.next())//to check whether all sub cash terminals r closed
             {
             	if(rs1.getString(1).equals("O"))
             	{
             		System.out.println("sub cashier tml to be closed");
             	    pay_pending = 1;//sub cashier tml to be closed
             	    break;
             	}
             }
             
             if(pay_pending!=1)
             {
                 rs = stmt.executeQuery("select * from DayCash where scroll_no=0 and trn_date='"+date+"'");
                 
                 if(rs.next())
                 {
                	 System.out.println("payment pending");
                     pay_pending = 2;//payment pending
                 }
                 else
                 {
                	 System.out.println("no payment pending");
                     pay_pending = 0;//all the sub cashier tmls and no payment pending
                 }
             }
         }
         catch(SQLException se)
         {
             se.printStackTrace();
         }
         finally
 		{
             try
             {
                 conn.close();
             }
             catch(SQLException sqlexception)
     		{
     			sqlexception.printStackTrace();
     		} 
         }
         
         return pay_pending;
    }
    //////////
    
    /**
     * Rebalances the running balance of the given terminal w.r.t scroll no in DayCash.
     */
    //ship......05/07/2006
    public int rebalancingScroll(String date,String tml) throws SQLException
    {
        Connection conn = null;
        Statement stmt_cs = null,stmt_dc = null;
        ResultSet rs_cs = null,rs_dc = null;
        
        double net_amount = 0.0;
        
        try
        {
            conn = getConnection();
            stmt_cs = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt_dc = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			rs_cs = stmt_cs.executeQuery("select * from Currency_Stock where tml_no='"+tml+"' and rec_type='C' and concat(right(cur_date,4),'-',mid(cur_date,locate('/',cur_date)+1,(locate('/',cur_date,4)-locate('/',cur_date)-1)),'-',left(cur_date,locate('/',cur_date)-1)) < '"+Validations.convertYMD(date)+"' order by concat(right(cur_date,4),'-',mid(cur_date,locate('/',cur_date)+1,(locate('/',cur_date,4)-locate('/',cur_date)-1)),'-',left(cur_date,locate('/',cur_date)-1)) desc limit 1");
			
			if(rs_cs.next())
			{
			    net_amount = rs_cs.getDouble("netamt");
			    
			    System.out.println("date = "+date);
				System.out.println("tml = "+tml);
				System.out.println("Net Amount of Prevoius date = "+net_amount);
				
				rs_dc = stmt_dc.executeQuery("select * from DayCash where scroll_no>0 and trn_date='"+date+"' and tml_no='"+tml+"' order by scroll_no");
				
				if(rs_dc.last())
				    rs_dc.beforeFirst();
				else
				    return 2;
				
				while(rs_dc.next())
				{
				    System.out.println("Scroll No = "+rs_dc.getInt("scroll_no"));
				    
				    if(rs_dc.getString("cd_ind").equals("D"))//Receipts
				    {
				        System.out.println("Rec = "+rs_dc.getDouble("csh_amt"));
				        net_amount = net_amount + rs_dc.getDouble("csh_amt");
				    }
				    else if(rs_dc.getString("cd_ind").equals("C"))//Payments
				    {
				        System.out.println("Pay = "+rs_dc.getDouble("csh_amt"));
				        net_amount = net_amount - rs_dc.getDouble("csh_amt");
				    }
				    
				    System.out.println("Net Amount = "+net_amount);
				    
				    if(stmt_dc.executeUpdate("update DayCash set run_bal="+net_amount+" where scroll_no="+rs_dc.getInt("scroll_no")+" and trn_date='"+date+"' and tml_no='"+tml+"' and cd_ind='"+rs_dc.getString("cd_ind")+"'")==0)
				        throw new SQLException();
				}
			}
			else
			    return 0;
			
			System.out.println("Net Amount of today's date = "+net_amount);
			
			return 1;
        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }
        finally
		{
            try
            {
                conn.close();
            }
            catch(SQLException sqlexception)
    		{
    			sqlexception.printStackTrace();
    		} 
        }
        
        return 0;
    }
    
    /**
     * Checks whether the Main Cash Terminal is closed for the given date.
     */
    //ship......06/07/2006
    public String checkDailyStatus(String date,int type) throws SQLException
    {
        Connection conn = null;
        Statement stmt_ds = null;
        ResultSet rs_ds = null;
        
        try
        {
            conn = getConnection();
            stmt_ds = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            //ship......21/08/2006
            if(type==0)//for Previous Dates
            {
                rs_ds = stmt_ds.executeQuery("select * from DailyStatus where cash_close='F' and trn_dt!='"+Validations.convertYMD(date)+"'");
                
                if(rs_ds.next())
                    return rs_ds.getString("trn_dt");
            }
            else if(type==1)//for the same date
            {
                rs_ds = stmt_ds.executeQuery("select * from DailyStatus where trn_dt='"+Validations.convertYMD(date)+"'");
                
                if(rs_ds.next())
                {
                    if(rs_ds.getString("cash_close").equals("T"))
                        return "F";
                }
            }
            ////////////
        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }
        finally
		{
            try
            {
                conn.close();
            }
            catch(SQLException sqlexception)
    		{
    			sqlexception.printStackTrace();
    		} 
        }
        
        return "T";
    }
    ////////////
    
    //ship......03/01/2007
    
    /**
     * Checks whether the Scroll No is Verified or Not
     */
    public int isScrollVerified(int scroll_no,String trndate) throws SQLException
    {
    	   Connection conn = null;
           Statement stmt_dc = null;
           ResultSet rs_dc = null;
           
           try
           {
               conn = getConnection();
               stmt_dc = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
               
               rs_dc = stmt_dc.executeQuery("select * from DayCash where scroll_no="+scroll_no+" and trn_date='"+trndate+"'");
               
               if(rs_dc.next())
               {
                   System.out.println("Verified status---> "+rs_dc.getString("ve_tml"));
            	   
            	   if(rs_dc.getString("ve_tml")!=null && rs_dc.getString("ve_user")!=null && rs_dc.getString("ve_date")!=null)
                   {
                       return 1;//Verified
                   }
                   else if(rs_dc.getString("ve_tml")==null && rs_dc.getString("ve_user")==null && rs_dc.getString("ve_date")==null)
                   {
                       return 2;//Not Verified
                   }
                   else
                   {
                       return 3;//Error in DB Entries
                   }
               }
               else
               {
                   return -1;
               }
           }
           catch(SQLException se)
           {
               se.printStackTrace();
           }
           finally
           {
               try
               {
                   conn.close();
               }
               catch(SQLException sqlexception)
               {
                   sqlexception.printStackTrace();
               } 
           }
           
           return 0;
    }
    //////////
    /*//Added By Shreya
    public String[] getGlNameCode() throws SQLException
    {
        String[] glname=null;
        Connection conn=null;
        
        try
        {
            conn=getConnection();
            Statement stmt= conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=stmt.executeQuery("select gl_name,gl_code from GLMaster ");
            glname= new String[rs.getRow()];
            
            while(rs.next())
             
            {
            	row=rs.getRow();
            	System.out.println("Print row value"+row);
            	
            	//glname[row]=new String();
          
            
                glname[row]=rs.getString(1)+"-"+rs.getString(2);
                row = rs.getRow();
            
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
    //Ended Here
*/
    

//Added by Amzad
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

public VoucherDataObject[] voucherDetails(int vch_no,String vch_type,String date)throws SQLException ,RecordsNotFoundException,RemoteException
{
VoucherDataObject voucherdetails[] = null;
Connection conn=null;
System.out.println("vch_no in server in voucher details" + vch_no);
System.out.println("vch_type in server in voucher details" + vch_type);

try
{
    conn=getConnection();
    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
    
   ResultSet rs = stmt.executeQuery("select gl_sl_type,gl_sl_code,trn_amt from VoucherData where trn_date='"+date+"' and vch_type='"+vch_type+"'and vch_no="+vch_no+" and cd_ind='C'");
    //ResultSet rs = stmt.executeQuery("select * from VoucherData where vch_no="+vch_no+" and vch_type='"+vch_type+"' and cd_ind='D' and trn_date='"+date+"' limit 10");
    //ResultSet rs = stmt.executeQuery("select * from VoucherData where vch_no="+ vch_no + " and vch_type='"+vch_type+"' and cd_ind='C' and trn_date='"+getClientDate()+"' ");
   
    //   concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1))<='"+Validations.convertYMD(getSysDate())+"'
    rs.last();
    
    System.out.println("length in server" + rs.getRow());
    
    if(rs.getRow()==0)
    {
        throw new RecordsNotFoundException();
    }    
   else
   {
	   voucherdetails = new VoucherDataObject[rs.getRow()];
        rs.beforeFirst();
        int i = 0;
        while (rs.next())
        {
        	System.out.println("Error in Voucher details!!!!!!!");
        	 voucherdetails[i] = new VoucherDataObject();
        	 System.out.println("Are you telling about this error");
        	 
        	
        	 voucherdetails[i].setGlType(rs.getString("gl_sl_type"));
        	 voucherdetails[i].setGlCode(rs.getInt("gl_sl_code"));
        	
            
        	 voucherdetails[i].setTransactionAmount(rs.getDouble("trn_amt"));
            
            System.out.println("trn_amt==="+rs.getDouble("trn_amt"));
            
         
            i++;
        }
   }
} catch (SQLException e)
{
    e.printStackTrace();
}
finally
{
    conn.close();
}
return  voucherdetails;


}
    }



