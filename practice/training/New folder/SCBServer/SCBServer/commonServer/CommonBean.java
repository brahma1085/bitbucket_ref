package commonServer;

import java.rmi.RemoteException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import loanServer.LoanLocal;
import loanServer.LoanLocalHome;
import masterObject.administrator.UserActivityMasterObject;
import masterObject.cashier.CashObject;
import masterObject.clearing.ClearingObject;
import masterObject.frontCounter.AccountTransObject;
import masterObject.frontCounter.PayOrderObject;
import masterObject.general.AccCategoryObject;
import masterObject.general.AccSubCategoryObject;
import masterObject.general.AccountObject;
import masterObject.general.Address;
import masterObject.general.AddressTypesObject;
import masterObject.general.BranchObject;
import masterObject.general.ModuleAdminObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.TrfVoucherObject;
import exceptions.AccountClosedException;
import exceptions.AccountNotFoundException;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import exceptions.FreezedAccountException;
import exceptions.InOperativeAccountException;
import exceptions.InsufficientBalanceException;
import exceptions.NomineeNotCreatedException;
import exceptions.RecordsNotFoundException;
import exceptions.ScrollNotFoundException;
import exceptions.SignatureNotInsertedException;
import exceptions.UserNotExistException;
import general.Validations;

public class CommonBean implements SessionBean
{
    static final long serialVersionUID = 1L;
    
	Connection conn1=null;
	SessionContext ctx=null;
	javax.sql.DataSource ds=null;
	
	//code added by amzad
	
	public int getCidofAccountNo(String ac_type,int ac_no){
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		int cid=0;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			
			rs=stmt.executeQuery("select cid from AccountMaster where ac_type='"+ac_type+"' and ac_no="+ac_no+"");
			rs.next();
			
			
			cid=rs.getInt(1);
			
			return cid;
		}catch(SQLException e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(SQLException e){e.printStackTrace();}
		}
		
		return cid;
	}
	
	
	
	
	public String getJointSBAccountName(String agt_acctype,int agt_accno,String pers_acc_type,int pers_acc_no,String jtacctype,int jtaccno,int id)throws SQLException
	{
		
		String name=" ";
		Connection conn=null;
		Statement stmt1=null,stmt2=null,stmt3=null;
		ResultSet rs1=null,rs2=null,rs3=null;
		
		try
		{
			conn=getConnection();
			stmt1=conn.createStatement();
			stmt2=conn.createStatement();
			stmt3=conn.createStatement();
			String jt_ac_type=null;
			int jt_ac_no=0 , no_of_jt_hldr=0, main_cid=0,jt_ac_cid=0;
            
            LoanLocalHome loanlocalhome = null;
            LoanLocal loanlocal = null;
            
            try
            {
                loanlocalhome=(LoanLocalHome) new InitialContext().lookup("LOANSLOCALWEB");
                loanlocal=loanlocalhome.create();
            }
            catch(NamingException ne) 
            {
                ne.printStackTrace();
            }
			
			if(id==1) //For PygmyDeposit , When Focus Lost of Agent Number
			{
				rs1=stmt1.executeQuery("select * from AgentMaster where ac_type='"+agt_acctype+"' and ac_no="+agt_accno+"");
				
				rs1.next();
				jt_ac_type=rs1.getString("jt_ac_type");
				jt_ac_no=rs1.getInt("jt_ac_no");
				main_cid=rs1.getInt("cid");
				System.out.println("Classes not compiling");
				jt_ac_cid=loanlocal.getCid(jt_ac_type,jt_ac_no);
				
				/*rs2=stmt2.executeQuery("select cid from AccountMaster where ac_type='"+jt_ac_type+"' and ac_no="+jt_ac_no+"");
				 rs2.next();
				 jt_ac_cid=rs2.getInt("cid");*/               
				
				rs3=stmt3.executeQuery("select concat_ws(' ',salute,fname,mname,lname) as name from CustomerMaster where cid in("+main_cid+","+jt_ac_cid+")");
				
				while(rs3.next())
				{
					name=name+", "+rs3.getString("name");
					
				}
				System.out.println("NAME...  : "+name);
			}
			else if(id==2) //To get personal acc name & Joint Holders name For a SB Account.. 
			{
				rs1=stmt1.executeQuery("select * from AccountMaster where ac_type='"+jtacctype+"' and ac_no="+jtaccno+"");
				rs1.next();
				no_of_jt_hldr=rs1.getInt("no_jt_hldr");
				main_cid=rs1.getInt("cid");
				
				if(no_of_jt_hldr==0)
					name=getName(jtacctype,jtaccno);	
				else
				{
					name=getName(jtacctype,jtaccno);
					rs2=stmt2.executeQuery("select cid from JointHolders where ac_type='"+ jt_ac_type+"' and ac_no="+jtaccno+" ");
					
					PreparedStatement pstmt=conn.prepareStatement("select concat_ws(' ',salute,fname,mname,lname) as name from CustomerMaster where cid=?");
					while(rs2.next())
					{
						pstmt.setInt(1,rs2.getInt("cid"));
						rs3=pstmt.executeQuery();
						rs3.next();
						name+=", "+rs3.getString("name");
						
					}
					//name=+ rs2.getInt("cid"));
					
				}
				return name.trim();
				//rs1=stmt1.executeQuery("select cid from AccountMaster where ac_type='"+pers_acc_type+"' and ac_no="+pers_acc_no+"");
				
			}
			else if(id==3)//for PygmyDeposit Joint SB Acc Number..
			{
				//main_cid=loanlocal.getCid(pers_acc_type,pers_acc_no);
				rs1=stmt1.executeQuery("select cid,no_jt_hldr from AccountMaster where ac_type='"+jtacctype+"' and ac_no="+jtaccno+"");
				rs1.next();
				main_cid=rs1.getInt("cid");
				rs1.close();
				
				rs2=stmt2.executeQuery("select jh.cid from JointHolders jh,AccountMaster am where am.ac_type='"+jtacctype+"' and am.ac_no="+jtaccno+" and jh.ac_type=am.ac_type and jh.ac_no=am.ac_no");
				//jt_ac_cid=loanlocal.getCid(jtacctype,jtaccno);
				
				while(rs2.next()){
					rs3=stmt3.executeQuery("select concat_ws(' ',salute,fname,mname,lname) as name from CustomerMaster where cid in("+main_cid+","+rs2.getInt(1)+")");
					
					while(rs3.next())
					{
						name+=", "+rs3.getString("name");
					}
					
				}//end of while 1
			}
			
			name.trim();
			System.out.println("name==="+name+"\nname.length=="+name.length()+"\n return name"+name.substring(2,name.length()));
			name=name.substring(2,name.length());
			System.out.println("============"+name.toString().trim());
			return name.toString().trim();
			
		}
		catch(SQLException se)
		{se.printStackTrace();}
		catch(CreateException ce)
		{ce.printStackTrace();}
		catch(CustomerNotFoundException e1)
		{e1.printStackTrace();}
		finally
		{
			try
			{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}
		System.out.println("**********************");
		return null;
	}
	//code added for Login by amzad
	public int storeUserActivity(UserActivityMasterObject user_activ)
	{
		Connection con=null;
		PreparedStatement pstmt=null;
		System.out.println(user_activ.getUser_id());
		System.out.println(user_activ.getTml_no());
		System.out.println(user_activ.getIp_address());
		System.out.println(user_activ.getPage_visit());
		System.out.println(user_activ.getActivity());
		System.out.println(user_activ.getActivity_date());
		
		try
		{
			con=getConnection();
			pstmt=con.prepareStatement("insert into UserActivity values(?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1,user_activ.getUser_id());
			pstmt.setString(2,user_activ.getTml_no());
			pstmt.setString(3,user_activ.getIp_address());
			pstmt.setString(4,user_activ.getPage_visit());
			pstmt.setString(5,user_activ.getActivity());
			pstmt.setString(6,user_activ.getAc_type());
			pstmt.setInt(7,user_activ.getAc_no());
			pstmt.setInt(8,user_activ.getCid());
			pstmt.setString(9,user_activ.getActivity_date());
			pstmt.setString(10,user_activ.getActivity_time());
			if(pstmt.executeUpdate()==1)
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
		catch(Exception e)
		{
			ctx.setRollbackOnly();
			e.printStackTrace();
			return 0;
		}
		finally
		{
			try{ con.close(); }
			catch(Exception ex){ex.printStackTrace();}
		}
	}
	
	/**
	 * This method is used to check valid user.This method is used in 
	 * Login form in client side.
	 * If the user is valid it will return true otherwise return false.
	 */
	
	public String checkLogin(String uid,String tml,String date,String time) 
	{
		Connection conn = null;
		Statement stmt_ds = null,stmt_Tmls = null,stmt_CurrStk = null,stmt_CS = null,stmt_DS = null,stmt_ms = null,stmt_mctml = null;
		ResultSet rs_ds = null,rs_Tmls = null,rs_CurrStk = null,rs_CS = null,rs_DS = null,rs_ms = null,rs_mctml = null;
		
		boolean present = false;
		
		try
		{
			System.out.println("user = "+uid);
			System.out.println("tml = "+tml);
			System.out.println("date = "+date);
			System.out.println("time = "+time);
			
			conn = getConnection();	
			
			stmt_ds = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_Tmls = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_CurrStk = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_CS = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_DS = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_ms = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//ship......25/07/2006
            stmt_mctml = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
            rs_DS = stmt_DS.executeQuery("select cash_close,day_close from DailyStatus where trn_dt='"+Validations.convertYMD(date)+"'");
							
            if(rs_DS.next()) {
            	if(rs_DS.getString("day_close")!= null && rs_DS.getString("day_close").equalsIgnoreCase("T")) {
            		System.out.println("inside date == && day_close = T");
            		return "H";
            	} else {
            		System.out.println("inside date == && day_close != T");
            		present = true;
            	}
            }
            rs_DS.close();
					
            rs_Tmls = stmt_Tmls.executeQuery("select tml_type from TerminalDetail where tml_code='"+tml+"'");
            
            if(rs_Tmls.next()) {
						
            	if(rs_Tmls.getString("tml_type") != null && rs_Tmls.getString("tml_type").equalsIgnoreCase("C")) {//Sub Cashier
							
            		rs_mctml = stmt_mctml.executeQuery("select tml_code from TerminalDetail where tml_type='M'");//To get Main Cashier
                            
            		if(rs_mctml.next()) {
            			rs_CS = stmt_CS.executeQuery("select * from Currency_Stock where tml_no='"+rs_mctml.getString("tml_code")+"' and cur_date='"+date+"'");
            			if(rs_CS.next()) {
            				if(rs_CS.getString("rec_type")!=null && rs_CS.getString("rec_type").equalsIgnoreCase("C")){
            					return "Main Cashier Closed";
            				}
            			} else {
            				return "Main Cashier Not Yet Opened";
            			}
            		} else {
            			return "Main Cashier Terminal Not Found";
            		}
            	}
                        
            	if(rs_Tmls.getString("tml_type") != null && (rs_Tmls.getString("tml_type").equals("M") || rs_Tmls.getString("tml_type").equals("C"))) {
                            
            		rs_CS = stmt_CS.executeQuery("select * from Currency_Stock where tml_no='"+tml+"' and cur_date='"+date+"'");

            		if(!rs_CS.next()){
								
            			rs_CurrStk = stmt_CurrStk.executeQuery("select * from Currency_Stock where tml_no='"+tml+"' order by concat(right(cur_date,4),'-',mid(cur_date,locate('/',cur_date)+1,(locate('/',cur_date,4)-locate('/',cur_date)-1)),'-',left(cur_date,locate('/',cur_date)-1)) desc limit 1");
								
            			if(rs_CurrStk.next()) {

            				if(stmt_ds.executeUpdate("insert into Currency_Stock values('"+tml+"','O','"+date+"',"+rs_CurrStk.getDouble("netamt")+","+rs_CurrStk.getInt("s1000")+","+rs_CurrStk.getInt("s500")+","+rs_CurrStk.getInt("s100")+","+rs_CurrStk.getInt("s50")+","+rs_CurrStk.getInt("s20")+","+rs_CurrStk.getInt("s10")+","+rs_CurrStk.getInt("s5")+","+rs_CurrStk.getInt("s2")+","+rs_CurrStk.getInt("s1")+","+rs_CurrStk.getDouble("scoins")+",'"+tml+"','"+uid+"','"+date+" "+time+"')")==0) {
            					return "Error while inserting into Currency_Stock";
            				}
            			}
            		}
            	}
            } else {
            	return "Terminal does not exist";
            }
					
            if(present==false) {
						
            	if(stmt_ds.executeUpdate("insert into DailyStatus values('"+Validations.convertYMD(date)+"','F','F','F','"+tml+"','"+uid+"',concat(right('"+date+"',4),'-',mid('"+date+"',locate('/','"+date+"')+1,(locate('/','"+date+"',4)-locate('/','"+date+"')-1)),'-',left('"+date+"',locate('/','"+date+"')-1),' ','"+time+"'))")==1) {
							
            		rs_ms = stmt_ms.executeQuery("select * from MonthlyStatus where yr_mth=concat(right('"+date+"',4),mid('"+date+"',locate('/','"+date+"')+1,(locate('/','"+date+"',4)-locate('/','"+date+"')-1)))");
							
            		if(rs_ms.next()) {
							
            			if(stmt_ms.executeUpdate("delete from MonthlyStatus where yr_mth=concat(right('"+date+"',4),mid('"+date+"',locate('/','"+date+"')+1,(locate('/','"+date+"',4)-locate('/','"+date+"')-1)))")==0) {
            				return "Error while deleting data from MonthlyStatus";
            			}
								
            			if(stmt_ms.executeUpdate("insert into MonthlyStatus values(concat(right('"+date+"',4),mid('"+date+"',locate('/','"+date+"')+1,(locate('/','"+date+"',4)-locate('/','"+date+"')-1))),'F','F','"+tml+"','"+uid+"',concat(right('"+date+"',4),'-',mid('"+date+"',locate('/','"+date+"')+1,(locate('/','"+date+"',4)-locate('/','"+date+"')-1)),'-',left('"+date+"',locate('/','"+date+"')-1),' ','"+time+"'))")==1) {
            				return "W";
            			} else {
            				return "Error while inserting into MonthlyStatus";
            			}
            		} else {
            			if(stmt_ms.executeUpdate("insert into MonthlyStatus values(concat(right('"+date+"',4),mid('"+date+"',locate('/','"+date+"')+1,(locate('/','"+date+"',4)-locate('/','"+date+"')-1))),'F','F','"+tml+"','"+uid+"',concat(right('"+date+"',4),'-',mid('"+date+"',locate('/','"+date+"')+1,(locate('/','"+date+"',4)-locate('/','"+date+"')-1)),'-',left('"+date+"',locate('/','"+date+"')-1),' ','"+time+"'))")==1) {
            				return "W";
            			} else {
            				return "Error while inserting into MonthlyStatus";
            			}
            		} 
            	} else {
            		return "Error while inserting into DailyStatus";
            	}
            } else {
            	return "W";
            }
				
		}catch(SQLException sqlex){
			ctx.setRollbackOnly();
			sqlex.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally {
			try	{		    	
				conn.close();
			} catch(Exception exception) {
				exception.printStackTrace();
			}
		}
		return "Error.....";
	}

	
	
	public String checkLogin(String uid,String pwd,String tml,int flag,String date,String time) 
	{
		Connection conn = null;
		Statement stmt_users = null,stmt_ds = null,stmt_Tmls = null,stmt_CurrStk = null,stmt_CS = null,stmt_DS = null,stmt_ms = null,stmt_mctml = null;
		ResultSet rs_users = null,rs_ds = null,rs_Tmls = null,rs_CurrStk = null,rs_CS = null,rs_DS = null,rs_ms = null,rs_mctml = null;
		
		boolean present = false;
		
		try
		{
			System.out.println("user = "+uid);
			System.out.println("tml = "+tml);
			System.out.println("flag = "+flag);
			System.out.println("date = "+date);
			System.out.println("time = "+time);
			
			conn = getConnection();	
			
			stmt_users = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_ds = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_Tmls = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_CurrStk = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_CS = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_DS = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt_ms = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//ship......25/07/2006
            stmt_mctml = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			rs_users = stmt_users.executeQuery("select * from Users where uid='"+uid+"' and pwd=MD5('"+pwd+"') and tml='"+tml+"'");
			
			if(rs_users.next())
			{
				if(flag==0)//0 - holiday Login
				{
					System.out.println("inside holidayday");
					
					return "H";
				}
				else if(flag==1)//1 - Working Day Login
				{
					System.out.println("inside working day");
					
					rs_ds = stmt_ds.executeQuery("select trn_dt from DailyStatus");
					
					while(rs_ds.next())
					{
						if(Validations.dayCompare(Validations.convertDMY(rs_ds.getString("trn_dt")),date)==0)
						{
							System.out.println("inside date == "+date);
							
							rs_DS = stmt_DS.executeQuery("select cash_close,day_close from DailyStatus where trn_dt='"+Validations.convertYMD(date)+"'");
							
							if(rs_DS.next())
							{
								if(rs_DS.getString("day_close").equals("T"))
								{
									System.out.println("inside date == && day_close = T");
									
									return "H";
								}
								else
								{
									System.out.println("inside date == && day_close != T");
									
									present = true;
								}
							}
							
							rs_DS.close();
						}
					}
					
					rs_Tmls = stmt_Tmls.executeQuery("select tml_type from TerminalDetail where tml_code='"+tml+"'");
					
					if(rs_Tmls.next())
					{
                        //ship.......10/03/2007
                        if(rs_Tmls.getString("tml_type").equals("C"))//Sub Cashier
                        {
                            rs_mctml = stmt_mctml.executeQuery("select tml_code from TerminalDetail where tml_type='M'");//To get Main Cashier
                            
                            if(rs_mctml.next())
                            {
                                rs_CS = stmt_CS.executeQuery("select * from Currency_Stock where tml_no='"+rs_mctml.getString("tml_code")+"' and cur_date='"+date+"'");
                                
                                if(rs_CS.next())
                                {
                                    if(rs_CS.getString("rec_type").equals("C"))
                                    {
                                        return "Main Cashier Closed";
                                    }
                                }
                                else
                                {
                                    return "Main Cashier Not Yet Opened";
                                }
                            }
                            else
                                return "Main Cashier Terminal Not Found";
                        }
                        
						if(rs_Tmls.getString("tml_type").equals("M") || rs_Tmls.getString("tml_type").equals("C"))
						{
                            //ship.......10/01/2007
							//rs_CS = stmt_CS.executeQuery("select * from Currency_Stock where tml_no='"+tml+"' and cur_date='"+date+"' and rec_type='O'");
                            
                            rs_CS = stmt_CS.executeQuery("select * from Currency_Stock where tml_no='"+tml+"' and cur_date='"+date+"'");
                            /////////////
							
							if(!rs_CS.next())
							{
								rs_CurrStk = stmt_CurrStk.executeQuery("select * from Currency_Stock where tml_no='"+tml+"' order by concat(right(cur_date,4),'-',mid(cur_date,locate('/',cur_date)+1,(locate('/',cur_date,4)-locate('/',cur_date)-1)),'-',left(cur_date,locate('/',cur_date)-1)) desc limit 1");
								
								if(rs_CurrStk.next())
								{
									if(stmt_ds.executeUpdate("insert into Currency_Stock values('"+tml+"','O','"+date+"',"+rs_CurrStk.getDouble("netamt")+","+rs_CurrStk.getInt("s1000")+","+rs_CurrStk.getInt("s500")+","+rs_CurrStk.getInt("s100")+","+rs_CurrStk.getInt("s50")+","+rs_CurrStk.getInt("s20")+","+rs_CurrStk.getInt("s10")+","+rs_CurrStk.getInt("s5")+","+rs_CurrStk.getInt("s2")+","+rs_CurrStk.getInt("s1")+","+rs_CurrStk.getDouble("scoins")+",'"+tml+"','"+uid+"','"+date+" "+time+"')")==0)
										return "Error while inserting into Currency_Stock";
								}
							}
						}
                        ///////////
					}
					else
						return "Terminal does not exist";
					
					if(present==false)
					{
						if(stmt_ds.executeUpdate("insert into DailyStatus values('"+Validations.convertYMD(date)+"','F','F','F','"+tml+"','"+uid+"',concat(right('"+date+"',4),'-',mid('"+date+"',locate('/','"+date+"')+1,(locate('/','"+date+"',4)-locate('/','"+date+"')-1)),'-',left('"+date+"',locate('/','"+date+"')-1),' ','"+time+"'))")==1)
						{
							rs_ms = stmt_ms.executeQuery("select * from MonthlyStatus where yr_mth=concat(right('"+date+"',4),mid('"+date+"',locate('/','"+date+"')+1,(locate('/','"+date+"',4)-locate('/','"+date+"')-1)))");
							
							if(rs_ms.next())
							{
								if(stmt_ms.executeUpdate("delete from MonthlyStatus where yr_mth=concat(right('"+date+"',4),mid('"+date+"',locate('/','"+date+"')+1,(locate('/','"+date+"',4)-locate('/','"+date+"')-1)))")==0)
									return "Error while deleting data from MonthlyStatus";
								
								if(stmt_ms.executeUpdate("insert into MonthlyStatus values(concat(right('"+date+"',4),mid('"+date+"',locate('/','"+date+"')+1,(locate('/','"+date+"',4)-locate('/','"+date+"')-1))),'F','F','"+tml+"','"+uid+"',concat(right('"+date+"',4),'-',mid('"+date+"',locate('/','"+date+"')+1,(locate('/','"+date+"',4)-locate('/','"+date+"')-1)),'-',left('"+date+"',locate('/','"+date+"')-1),' ','"+time+"'))")==1)
									return "W";
								else
									return "Error while inserting into MonthlyStatus";
							}
							else
							{
								if(stmt_ms.executeUpdate("insert into MonthlyStatus values(concat(right('"+date+"',4),mid('"+date+"',locate('/','"+date+"')+1,(locate('/','"+date+"',4)-locate('/','"+date+"')-1))),'F','F','"+tml+"','"+uid+"',concat(right('"+date+"',4),'-',mid('"+date+"',locate('/','"+date+"')+1,(locate('/','"+date+"',4)-locate('/','"+date+"')-1)),'-',left('"+date+"',locate('/','"+date+"')-1),' ','"+time+"'))")==1)
									return "W";
								else
									return "Error while inserting into MonthlyStatus";
							} 
						}
						else
							return "Error while inserting into DailyStatus";
					}
					else
						return "W";
				}
			}
			else
				return "Invalid User";
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			ctx.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
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
		
		return "Error.....";
	}
	
	//ship.......09/06/2006
	/**
	 * This method is used to check whether it is holiday on that day or not.
	 * This method is used in Login form in client side.
	 * If it is holiday, it will return true, otherwise return false.
	 */
	
	public boolean checkForHoliday(String date) 
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			rs=stmt.executeQuery("select * from HolidayMaster where date = '"+date+"'");
			
			if(rs.next())
				return true;
		}
		catch(SQLException exception)
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
		
		return false;
	}
	////////////////////
	
	/**
	 * @param  //Customer ID and flag
	 * flag=1 - To check the Customer ID is valid or invalid
	 * flag=2 - To check whether the Customer ID is minor or not
	 * 
	 * If the CID is valid it will return true otherwise return false. 
	 */
	public boolean checkCid(int cid,int flag)
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try{
			conn=getConnection();
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if(flag==1)
			{
				rs=stmt.executeQuery("select * from CustomerMaster where cid="+cid);	
				if(rs.next())
					return true;
				return false; 
			}
			else if(flag==2)
			{
				rs=stmt.executeQuery("select * from CustomerMaster where cid="+cid+" and guardianname is not null");
				if(rs.next())
					return true;
				return false;
			}
			
			
		}catch(SQLException se){se.printStackTrace();}
		finally {
			try{
				conn.close();
				stmt.close();
				rs.close();
			}catch(Exception e){e.printStackTrace();}
		}
		return false;
	}
	
	/**
	 * Used to get The Modulecode of the Abbriviation.
	 * 
	 * @return
	 */
	
	public String getModulecode(String abbr)
	{
		String mod_code=null;
		Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		try{
			conn=getConnection();
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			System.out.println("module abbr = "+abbr);
			
			rs=stmt.executeQuery("select * from Modules where moduleabbr='"+abbr+"'");
			
			if(rs.next())
			{
				mod_code=String.valueOf(rs.getInt("modulecode"));
				System.out.println("module code = "+rs.getInt("modulecode"));
			}
			
			System.out.println("Inside Cmmn Bean :"+mod_code);
			
			
			
		}catch(SQLException se){se.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(SQLException e){e.printStackTrace();}
			
		}
		
		return mod_code;
	}
	
	/**
	 * This method checks whether the perticular month is Quarter month or not.
	 * this is helpful when calculating interest.
	 * This is because the interest calculation is done only 
	 * on the basis of Quarter defination.
	 * 
	 * This will return true if the perticular month is quarter otherwise false. 
	 */
	//pygmy interst calculation
	public boolean checkQuarter() 
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			int a1[]={31,28,31,30,31,30,31,31,30,31,30,31};
			int a2[]={31,29,31,30,31,30,31,31,30,31,30,31};
			
			Calendar cal=Calendar.getInstance();
			String dd=String.valueOf(cal.get(Calendar.DATE)).length()==1?("0"+cal.get(Calendar.DATE)):String.valueOf(cal.get(Calendar.DATE));
			String mm=String.valueOf(cal.get(Calendar.MONTH)+1).length()==1?("0"+(cal.get(Calendar.MONTH)+1)):String.valueOf(cal.get(Calendar.MONTH)+1);
			String yy=String.valueOf(cal.get(Calendar.YEAR));
			
			rs=stmt.executeQuery("select * from QtrDefinition where month="+Integer.parseInt(mm)+" and qtr_defn='T'");
			
			if(rs.next())
			{
				if((Integer.parseInt(yy)%4)==0 && a1[Integer.parseInt(mm)]==Integer.parseInt(dd))
					return(true);
				else if(a2[Integer.parseInt(mm)]==Integer.parseInt(dd))
					return(true);
				else
					return(false);
			}
			
			return(false);
			
		}catch(SQLException exception)
		{
			if(true)
				return false;
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return false;
	}
	/**
	 * This method is used to check for perticular Scroll number generated 
	 * by the cashier has been used or not.
	 * 
	 * Here it is returning an object array containing scroll information with
	 * attached indicator and scroll amount. 
	 */
	
	public Object[] checkScrollAttached(int scno,String date,String actype,String acno,int type) throws ScrollNotFoundException
	//public Object[] checkScrollAttached(int scno,String date,String actype,int acno,int type) throws ScrollNotFoundException
	{
		Object[] obj=null;
		ResultSet rs=null;
		Connection conn=null;
		Statement stmt=null;
		try
		{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			
			obj=new Object[2];
			if(type==0)
				rs=stmt.executeQuery("select attached,csh_amt from DayCash where scroll_no="+scno+" and trn_date='"+date+"' and ac_type='"+actype+"' and ac_no='"+acno+"' and ve_tml is null");
			if(type==1)
				rs=stmt.executeQuery("select attached,csh_amt from DayCash where scroll_no="+scno+" and trn_date='"+date+"' and ac_type='"+actype+"' and ac_no='"+acno+"'");
			if(rs.next())
			{	
				obj[0]=rs.getString(1);
				obj[1]=String.valueOf(rs.getDouble(2));
				return obj;
			}
			throw new ScrollNotFoundException();
			
		}catch(SQLException exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return obj;
	}
	
	/**
	 * closing database connection.
	 */
	public void closeConnection()
	{
		try
		{
			conn1.close();
			
		}catch(Exception ex){ex.printStackTrace();}
	}
	public void ejbActivate(){}
	
	public void ejbCreate()
	{     
		try{
			ds=(javax.sql.DataSource)(new InitialContext()).lookup("java:MySqlDS");						
		}catch (NamingException e) {
			e.printStackTrace();
		}
	}
	public void ejbPassivate(){}
	public void ejbRemove(){}
	
	/**
	 * This method Gives the full details of a perticular account like account holder name
	 * his address and etc.
	 * The return type of this method is AccountObject.
	 * 
	 * Here we r checking with account type and account number.
	 * if trn_mode is 'C' then it will give scroll number details.
	 * if trn_mode is 'G' then it will give control number details. 
     * if trn_mode is null then it will give the account details.
	 */
    
    //ship......20/12/2006.......added trn_mode
	public AccountObject getAccount(String trn_mode,String acctype,int accno,String date) 
	{
		AccountObject ac=null;
		ResultSet rs_bal=null,rs_branch=null;
		Connection conn=null;
		Statement stmt=null,stmt_branch=null;
		double shadow_balance=0;
		System.out.println("Inside getAccount() method...");
        System.out.println("Trn Mode...."+trn_mode);
		System.out.println("Acctype...."+acctype);
		System.out.println("Accno..."+accno);
        System.out.println("Trn Date..."+date);
        
		try
		{
			ResultSet rs=null;
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			stmt_branch =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			if((trn_mode!=null) && trn_mode.equals("C"))
                {
				//ship......16/01/2006......commented
				//System.out.println("Inside  CASH  getAccount()..");
				/*rs=stmt.executeQuery("select scroll_no,ac_type,ac_no,name,csh_amt,attached,ve_user from DayCash where trn_date='"+getSysDate()+"' and scroll_no="+accno);		
				 if(rs.next())
				 {
				 //System.out.println(" @@@@@Inside  CASH  getAccount()..");
				  ac=new AccountObject();
				  ac.setAccno(rs.getInt("ac_no"));
				  ac.setAcctype(rs.getString("ac_type"));
				  ac.setAccname(rs.getString("name"));
				  ac.setAmount(rs.getDouble("csh_amt"));
				  if(rs.getString("attached").equals("T"))
				  ac.setAccStatus("C");
				  else
				  ac.setAccStatus("O");
				  if(rs.getString("ve_user")==null)
				  ac.setVerified("");
				  else
				  ac.setVerified(rs.getString("ve_user")); 
				  }*/	
				
				System.out.println("Inside  CASH  getAccount()..");
                
                //ship.......20/12/2006
				rs=stmt.executeQuery("select scroll_no,ac_type,ac_no,name,csh_amt,attached,ve_user,ve_tml,ve_date from DayCash where trn_date='"+date+"' and scroll_no="+accno+" and ac_type='"+acctype+"' and de_tml is not null and de_user is not null and de_date is not null");
                
				if(rs.next())
				{
					System.out.println(" @@@@@Inside  CASH  getAccount()..");
					ac=new AccountObject();
					ac.setAccno(rs.getInt("ac_no"));
					ac.setAcctype(rs.getString("ac_type"));
					ac.setAccname(rs.getString("name"));
					ac.setAmount(rs.getDouble("csh_amt"));
					ac.setScrollno(rs.getInt("scroll_no"));
					if(rs.getString("attached").equals("T"))
						ac.setAccStatus("C");
					else
						ac.setAccStatus("O");
					
					if(rs.getString("ve_user")==null && rs.getString("ve_tml")==null && rs.getString("ve_date")==null)
						ac.setVerified("");
					else if(rs.getString("ve_user")!=null && rs.getString("ve_tml")!=null && rs.getString("ve_date")!=null)
						ac.setVerified(rs.getString("ve_user")); 
                    else
                        ac.setVerified(null);
				}
				System.out.println(" @@@@@outside  CASH  getAccount()..");
				///////////
			}
			else if((trn_mode!=null) && trn_mode.equals("G"))
			{
				rs=stmt.executeQuery("select clg_date,cr_ac_type,cr_ac_no,payee_name,trn_amt,desp_ind,post_ind,ve_user from Cheque where ctrl_no="+accno+" and clg_type='O'");		
				if(rs.next())
				{
					ac=new AccountObject();
					ac.setAccno(rs.getInt("cr_ac_no"));
					ac.setAcctype(rs.getString("cr_ac_type"));
					ac.setAccname(rs.getString("payee_name"));
					ac.setAmount(rs.getDouble("trn_amt"));
					ac.setVerified(rs.getString("ve_user"));				
					ac.setAccStatus(rs.getString("desp_ind"));
					ac.setAccStatus(rs.getString("desp_ind"));
					ac.setPostInd("post_ind");
					ac.setLastTrnDate(rs.getString("clg_date"));
					ac.setQdp_date(rs.getString("clg_date"));
				
				} else {
					rs=stmt.executeQuery("select clg_date,qdp_dt,cr_ac_type,cr_ac_no,payee_name,trn_amt,desp_ind,post_ind,ve_user from Cheque where ctrl_no="+accno+" and clg_type='T'");		
					if(rs.next())
					{
						ac=new AccountObject();
						ac.setAccno(rs.getInt("cr_ac_no"));
						ac.setAcctype(rs.getString("cr_ac_type"));
						ac.setAccname(rs.getString("payee_name"));
						ac.setAmount(rs.getDouble("trn_amt"));
						ac.setVerified(rs.getString("ve_user"));				
						ac.setAccStatus(rs.getString("desp_ind"));
						ac.setAccStatus(rs.getString("desp_ind"));
						ac.setPostInd("post_ind");
						ac.setLastTrnDate(rs.getString("clg_date"));
						ac.setQdp_date(rs.getString("qdp_dt"));
					}
					
				}
				
			}
			else
			{
				rs=null;
				System.out.println("************else**********");
				if(acctype.startsWith("1002")|| acctype.startsWith("1007") || acctype.startsWith("1014") || acctype.startsWith("1015") || acctype.startsWith("1017") || acctype.startsWith("1018") || acctype.startsWith("1006"))
				{
					if(acctype.startsWith("1002")|| acctype.startsWith("1007") ||acctype.startsWith("1017") ||acctype.startsWith("1018"))
					{
						rs=stmt.executeQuery("select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,cl_bal,trn_date,ac_status,ch_bk_issue,freeze_ind,AccountMaster.cid,AccountMaster.ve_user,custtype,sub_category,AccountMaster.no_jt_hldr from CustomerMaster,AccountMaster,AccountTransaction,Modules,GLKeyParam where CustomerMaster.cid=AccountMaster.cid and AccountMaster.ac_no=AccountTransaction.ac_no and AccountMaster.ac_type=AccountTransaction.ac_type and AccountMaster.ac_type='"+acctype+"' and AccountMaster.ac_no="+accno+" and GLKeyParam.ac_type='"+acctype+"' and Modules.modulecode='"+acctype+"' and GLKeyParam.code=CustomerMaster.custtype+1 and (AccountTransaction.ve_tml is not null || AccountMaster.ve_tml is not null)order by AccountTransaction.trn_seq desc limit 1");//ship...03/07/2006.....to Remove GLMaster 
					}
					else if(acctype.startsWith("1015"))
					{
						rs=stmt.executeQuery("select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,cl_bal,trn_date,ac_status,ch_bk_issue,default_ind,freeze_ind,ODCCMaster.cid,ODCCMaster.ve_user,creditlimit,limit_upto,custtype,sub_category from CustomerMaster,ODCCMaster,ODCCTransaction,GLKeyParam,Modules where CustomerMaster.cid=ODCCMaster.cid and ODCCMaster.ac_no=ODCCTransaction.ac_no and ODCCMaster.ac_type=ODCCTransaction.ac_type and ODCCMaster.ac_type='"+acctype+"' and ODCCMaster.ac_no="+accno+" and GLKeyParam.ac_type='"+acctype+"' and Modules.modulecode='"+acctype+"' and GLKeyParam.code=CustomerMaster.custtype+1 and (ODCCTransaction.ve_user is not null || ODCCMaster.ve_user is  not null ) order by ODCCTransaction.trn_seq desc limit 1");//ship...03/07/2006.....to Remove GLMaster 
					}
					else if(acctype.startsWith("1014"))
					{
						rs=stmt.executeQuery("select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,cl_bal,trn_date,ac_status,ch_bk_issue,default_ind,freeze_ind,ODCCMaster.cid,ODCCMaster.ve_user,limit_upto,custtype,sub_category,ODCCMaster.creditlimit from CustomerMaster,ODCCMaster,ODCCTransaction,GLKeyParam,Modules where CustomerMaster.cid=ODCCMaster.cid and ODCCMaster.ac_no=ODCCTransaction.ac_no and ODCCMaster.ac_type=ODCCTransaction.ac_type and ODCCMaster.ac_type='"+acctype+"' and ODCCMaster.ac_no="+accno+" and GLKeyParam.ac_type='"+acctype+"' and Modules.modulecode='"+acctype+"' and GLKeyParam.code=CustomerMaster.custtype+1 and (ODCCTransaction.ve_user is not null || ODCCMaster.ve_user is null ) order by ODCCTransaction.trn_seq desc limit 1");//ship...03/07/2006.....to Remove GLMaster 
					}
					else if(acctype.startsWith("1006"))
						rs=stmt.executeQuery("select concat_ws('',cm.salute,cm.fname,cm.mname,cm.lname) name,cm.custtype,cm.sub_category,pm.ac_type,pm.ac_no,pm.cid,pm.status ac_status,pt.cl_bal,pt.trn_seq from CustomerMaster cm,PygmyMaster pm,PygmyTransaction pt where pm.ac_type='"+acctype+"' and pm.ac_no="+accno+" and pm.close_date is null and pm.ve_tml is not null and pt.ac_no=pm.ac_no and pt.trn_seq=pm.lst_trn_seq and cm.cid=pm.cid  and pt.ve_tml is not null ");
					
					
					System.out.println("2..55522.."); 
					
					if(rs.next())
					{
						System.out.println("3......");
						ac=new AccountObject();
						ac.setAccno(accno);
						System.out.println("---------$$$$$$$$$---"+accno);
						ac.setAcctype(acctype);
						ac.setAccname(rs.getString("name"));						
						ac.setAccStatus(rs.getString("ac_status"));
						ac.setChBkIssue(rs.getString("ch_bk_issue"));
						ac.setAmount(rs.getDouble("cl_bal"));
						ac.setCategory(rs.getInt("custtype"));
						ac.setSubCategory(rs.getInt("sub_category"));
						ac.setFreezeInd("F");
						ac.setDefaultInd("F");						
						if(acctype.startsWith("1006"))
							ac.setCid(rs.getInt("cid"));
						
						if(acctype.startsWith("1002")|| acctype.startsWith("1007")|| acctype.startsWith("1018"))
						{
							if(!acctype.startsWith("1018"))
								ac.setNo_of_jt_hldr(rs.getInt("no_jt_hldr"));
							
							ac.setCid(rs.getInt("AccountMaster.cid"));
							ac.setVerified(rs.getString("AccountMaster.ve_user"));
							ac.setFreezeInd(rs.getString("freeze_ind"));
							
							rs_bal = stmt.executeQuery("select sum(trn_amt) from AccountTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is null and cd_ind='C'");
							if(rs_bal.next())
							{
								System.out.println(rs_bal.getDouble(1));
								if(rs_bal.getDouble(1)!=0)
									shadow_balance=ac.getAmount()+rs_bal.getDouble(1);
							}
							rs_bal = stmt.executeQuery("select sum(trn_amt) from AccountTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is null and cd_ind='D'");
							if(rs_bal.next())
								if(rs_bal.getDouble(1)!=0)
									shadow_balance=shadow_balance-rs_bal.getDouble(1);
						}						
						
						else if(acctype.startsWith("1014")|| acctype.startsWith("1015"))
						{
							System.out.println("4...");
							ac.setCid(rs.getInt("ODCCMaster.cid"));
							ac.setVerified(rs.getString("ODCCMaster.ve_user"));
							ac.setDefaultInd(rs.getString("default_ind"));
							ac.setFreezeInd(rs.getString("freeze_ind"));
							ac.setLimitUpto(rs.getString("limit_upto"));
							System.out.println("PLZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
							if(acctype.startsWith("1015") || acctype.startsWith("1014"))
								ac.setCreditLimit(rs.getDouble("creditlimit"));
							else
							{	
								ResultSet rs_lmt=null;
								rs_lmt=stmt.executeQuery("select credit_limit from ODCCTransaction,StockInspectionDetails stk,ODCCMaster  where ODCCMaster.ac_type=ODCCTransaction.ac_type and ODCCMaster.ac_no=ODCCTransaction.ac_no and ODCCTransaction.ac_type='"+acctype+"' and ODCCTransaction.ac_no="+accno+" and stk.ac_type=ODCCTransaction.ac_type and stk.ac_no=ODCCTransaction.ac_no and ODCCTransaction.ve_user is not null and sanc_ver='Y' order by ODCCTransaction.trn_seq desc,concat(right(stk.insp_date,4),'-',mid(stk.insp_date,locate('/',stk.insp_date)+1, (locate('/',stk.insp_date,4)-locate('/',stk.insp_date)-1)),'-',left(stk.insp_date,locate('/',stk.insp_date)-1)) desc limit 1");						
								if(rs_lmt.next())
									ac.setCreditLimit(rs_lmt.getDouble("credit_limit"));
								else
									ac.setCreditLimit(0);
							}
							rs_bal=null;
							rs_bal = stmt.executeQuery("select sum(trn_amt) from ODCCTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is null and cd_ind='C'");
							if(rs_bal.next())
								if(rs_bal.getDouble(1)!=0)
									shadow_balance=ac.getAmount()+rs_bal.getDouble(1);
							
							rs_bal = stmt.executeQuery("select sum(trn_amt) from ODCCTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and ve_tml is null and cd_ind='D'");
							if(rs_bal.next())
								if(rs_bal.getDouble(1)!=0)
									shadow_balance=shadow_balance-rs_bal.getDouble(1);							
						}						
						ac.setShadowBalance(shadow_balance);
						ac.setSignatureInstruction(getSignatureDetails(accno,acctype));						
					}
				}
				else if(acctype.startsWith("1001"))
				{	
					//rs=stmt.executeQuery("select ac_no,ac_type,ShareMaster.br_code,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,ShareMaster.br_code,share_val,ShareMaster.cid,mem_cl_date,ShareMaster.ve_user from ShareMaster,CustomerMaster  where CustomerMaster.cid=ShareMaster.cid and ac_no="+accno +" and ac_type="+acctype);
					rs=stmt.executeQuery("select ac_no,ac_type,custtype,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,ShareMaster.br_code,share_val,ShareMaster.cid,mem_cl_date,ShareMaster.ve_user,BranchMaster.br_name from ShareMaster,CustomerMaster,BranchMaster where CustomerMaster.cid=ShareMaster.cid and ShareMaster.br_code=BranchMaster.br_code and ac_no="+accno +" and ac_type="+acctype);
					if(rs.next())
					{
						ac=new AccountObject();
						ac.setAccno(rs.getInt("ac_no"));
						ac.setAcctype(rs.getString("ac_type"));
						ac.setAccname(rs.getString("name"));
						ac.setAmount(rs.getDouble("share_val"));						
						ac.setCid(rs.getInt("ShareMaster.cid"));
						ac.setVerified(rs.getString("ShareMaster.ve_user"));
						ac.setScrollno(rs.getInt("br_code"));
						ac.setBranchname(rs.getString("BranchMaster.br_name"));
						if(rs.getString("mem_cl_date")==null)
							ac.setAccStatus("O");
						else
							ac.setAccStatus("C");
						ac.setFreezeInd("F");
						ac.setDefaultInd("F");
						ac.setSignatureInstruction(getSignatureDetails(accno,acctype));
						ac.setCategory(rs.getInt("CustomerMaster.custtype"));
						
						rs_branch = stmt_branch.executeQuery("select * from Head where bankcode="+rs.getInt("br_code")+" ");
						if(!rs_branch.next()) {
							ac.setIsOtherBranch(true);
						}
						
						rs_branch = stmt_branch.executeQuery("select * from DirectorMaster where cid = "+rs.getInt("ShareMaster.cid")+" ");
						if(rs_branch.next()) {
							ac.setIsDirector(true);
						}
					}
				}
				else if(acctype.startsWith("1004"))
				{
					//rs=stmt.executeQuery("select CustomerMaster.custtype,dm.ac_no,dm.ac_type,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,dm.dep_days,dm.dep_mths,dm.dep_yrs,dm.dep_amt,dt.rd_bal,dm.cid,close_ind,dm.ve_user from DepositMaster dm,DepositTransaction dt,CustomerMaster  where CustomerMaster.cid=dm.cid and dm.ac_type=dt.ac_type and dm.ac_no=dt.ac_no and  dm.ac_type='"+acctype+"' and dm.ac_no="+accno +"  order by dt.trn_seq desc limit 1");
					rs=stmt.executeQuery("select CustomerMaster.custtype,dm.*,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,dt.* from DepositMaster dm,DepositTransaction dt,CustomerMaster  where CustomerMaster.cid=dm.cid and dm.ac_type=dt.ac_type and dm.ac_no=dt.ac_no and  dm.ac_type='"+acctype+"' and dm.ac_no="+accno+" order by dt.trn_seq desc limit 1");
					if(rs.next())
					{
						ac=new AccountObject();
						ac.setAccno(rs.getInt("dm.ac_no"));
						ac.setAcctype(rs.getString("dm.ac_type"));
						ac.setAccname(rs.getString("name"));
						ac.setAmount(rs.getDouble("dm.dep_amt"));
						ac.setCid(rs.getInt("dm.cid"));
						ac.setClose_ind(rs.getInt("dm.close_ind"));
						//For cashier  and setcid changed  to setCategory
						ac.setCategory(rs.getInt("CustomerMaster.custtype"));
						
						//ship......22/05/2006
						ac.setDepositdays(rs.getInt("dm.dep_days"));
						ac.setDepositmths(rs.getInt("dm.dep_mths"));
						ac.setDeposityrs(rs.getInt("dm.dep_yrs"));
						/////////////
						
						ac.setShadowBalance(rs.getDouble("dt.rd_bal"));
						ac.setVerified(rs.getString("dm.ve_user"));						
						if(rs.getInt("close_ind")==0)
							ac.setAccStatus("O");
						else
							ac.setAccStatus("C");
						ac.setFreezeInd("F");
						ac.setDefaultInd("F");
						ac.setSignatureInstruction(getSignatureDetails(accno,acctype));
						
						/*//ship.....to get total interest of a RD account.....26/12/2005
						 ResultSet rs_int = null;
						 double int_amt = 0.00;
						 
						 rs_int = stmt.executeQuery("select int_amt from DepositTransaction where ac_type='1004001' and ac_no=5 and trn_type='I'");
						 
						 while(rs_int.next())
						 int_amt = int_amt+rs_int.getDouble("int_amt");
						 
						 ac.setIntAmount(int_amt);
						 //*/
						
						
						ResultSet rs_int = null;
						rs_int = stmt.executeQuery("select sum(int_amt) from DepositTransaction where ac_type='"+acctype+"' and ac_no="+accno +" and trn_type='I'");
						if(rs_int.next()){
							ac.setIntAmount(rs_int.getDouble(1));
						}
						rs_int = stmt.executeQuery("select count(*) from DepositTransaction where ac_type='"+acctype+"' and ac_no="+accno+" and trn_type='D' and ve_tml is not null");
						if(rs_int.next()){
							ac.setInst_no(rs_int.getInt(1));
						}
					}
				}
				else if(acctype.startsWith("1003")||acctype.startsWith("1005"))
				{
					rs=stmt.executeQuery("select dm.ac_no,dm.ac_type,CustomerMaster.custtype,dm.dep_days,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,dm.cid,close_ind,dep_amt,dm.ve_user from DepositMaster dm, CustomerMaster  where CustomerMaster.cid=dm.cid and  dm.ac_type='"+acctype+"' and dm.ac_no="+ accno );
					if(rs.next())
					{
						ac=new AccountObject();
						ac.setAccno(rs.getInt("dm.ac_no"));
						ac.setAcctype(rs.getString("dm.ac_type"));
						ac.setAccname(rs.getString("name"));
						ac.setCid(rs.getInt("dm.cid"));
						ac.setAmount(rs.getDouble("dm.dep_amt"));
						ac.setVerified(rs.getString("dm.ve_user"));
						if(rs.getInt("close_ind")==0)
							ac.setAccStatus("O");
						else
							ac.setAccStatus("C");
						ac.setFreezeInd("F");
						ac.setDefaultInd("F");
						ac.setCategory(rs.getInt("CustomerMaster.custtype"));
						ac.setSignatureInstruction(getSignatureDetails(accno,acctype));
					}
				}     
				else if(acctype.startsWith("1009")) //for lockers 
				{   
					rs=stmt.executeQuery("select l.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,cm.custtype,cm.sub_category from LockerMaster l,CustomerMaster cm,LockerTransaction lt where l.ac_type='"+acctype+"' and l.ac_no="+accno+" and cm.cid=l.cid and lt.ac_no=l.ac_no and lt.ac_type=l.ac_type limit 1");
					
					if(rs.next())
					{
						ac=new AccountObject();
						ac.setAccno(accno);
						ac.setAcctype(acctype);
						ac.setAccname(rs.getString("name"));
						ac.setVerified(rs.getString("ve_user"));
						ac.setCid(rs.getInt("cid"));
						if(rs.getString("close_dt")!=null)
							ac.setAccStatus("C");
						else
							ac.setAccStatus("O");
						
						/*  //ac.setAmount(rs.getDouble("cl_bal"));
						 ac.setCategory(rs.getInt("cm.custtype"));
						 ac.setSubCategory(rs.getInt("cm.sub_category"));
						 ac.setFreezeInd("F");
						 ac.setDefaultInd("F");
						 */
						
					}
				}
				else if(acctype.startsWith("1013") )
				{
					rs=stmt.executeQuery("select ac_type,ac_no,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name,AgentMaster.cid,close_ind,AgentMaster.ve_user  from AgentMaster,CustomerMaster where CustomerMaster.cid=AgentMaster.cid and ac_type='"+acctype+"' and ac_no="+accno);
					if(rs.next())
					{
						ac=new AccountObject();
						ac.setAccno(rs.getInt("ac_no"));
						ac.setAcctype(rs.getString("ac_type"));
						ac.setAccname(rs.getString("name"));
						ac.setCid(rs.getInt("AgentMaster.cid"));	
						if(rs.getInt("close_ind")==0)
							ac.setAccStatus("O");
						else
							ac.setAccStatus("C");													
						ac.setVerified(rs.getString("AgentMaster.ve_user"));
						ac.setFreezeInd("F");
						ac.setDefaultInd("F");
					}
				}
				else if(acctype.startsWith("1008"))
				{
					System.out.println("1@@@@@Inside Loan Account.........");
					//rs=stmt.executeQuery("select ln.ac_type,ln.ac_no,pr_bal,int_date,ln.close_date,ln.cid,ln.ve_user,other_amt,td_ac_type,td_ac_no,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name  from LoanTransaction lt,LoanMaster ln,DepositMaster dm,CustomerMaster cm where ln.ac_type=dm.ln_ac_type and ln.ac_no=dm.ln_ac_no and lt.ac_type=ln.ac_type and lt.ac_no=ln.ac_no and  cm.cid=dm.cid and ln.ac_type='"+acctype+"' and ln.ac_no="+accno+" order by trn_seq desc");
					rs=stmt.executeQuery("select ln.ac_type,ln.ac_no,pr_bal,int_date,ln.close_date,ln.cid,ln.ve_user,other_amt,td_ac_type,td_ac_no,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name  from LoanTransaction lt,LoanMaster ln,DepositMaster dm,CustomerMaster cm where ln.td_ac_type=dm.ac_type and ln.td_ac_no=dm.ac_no and lt.ac_type=ln.ac_type and lt.ac_no=ln.ac_no and  cm.cid=dm.cid and ln.ac_type='"+acctype+"' and ln.ac_no="+accno+" order by trn_seq desc");
					rs.last();
					if(rs.getRow()==0)
						rs=stmt.executeQuery("select ln.ac_type,ln.ac_no,pr_bal,int_date,ln.close_date,ln.cid,ln.ve_user,other_amt,td_ac_type,td_ac_no,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name  from LoanTransaction lt,LoanMaster ln,PygmyMaster dm,CustomerMaster cm where ln.td_ac_type=dm.ac_type and ln.td_ac_no=dm.ac_no and lt.ac_type=ln.ac_type and lt.ac_no=ln.ac_no and  cm.cid=dm.cid and ln.ac_type='"+acctype+"' and ln.ac_no="+accno+" order by trn_seq desc");//Karthi -->22/08/2006
					else
						rs.beforeFirst();
					
					System.out.println("2.........");
					if(rs.next())
					{
						System.out.println("3@@@@.........");
						ac=new AccountObject();
						System.out.println("4.........");
						ac.setAccno(rs.getInt("ln.ac_no"));
						ac.setAcctype(rs.getString("ln.ac_type"));
						ac.setAccname(rs.getString("name"));
						System.out.println("5.........");
       //	        	double d=rs.getDouble("pr_bal");
						ac.setCid(rs.getInt("ln.cid"));	
						if(rs.getString("close_date")==null)
							ac.setAccStatus("O");
						else
							ac.setAccStatus("C");
						
						System.out.println("6.........");
						ac.setVerified(rs.getString("ln.ve_user"));
						ac.setFreezeInd("F");
						ac.setDefaultInd("F");
						System.out.println("7@@@@@.........");
						
					}
				}
				else if(acctype.startsWith("1010"))
				{
					System.out.println("2.........shiva");
					rs=stmt.executeQuery("select ln.ac_type,ln.ac_no,pr_bal,int_date,other_amt,ln.cid,ln.ve_user,ln.close_date,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name  from LoanTransaction lt,LoanMaster ln,ShareMaster sm,CustomerMaster cm where ln.sh_no=sm.ac_no and ln.sh_type=sm.ac_type and lt.ac_type=ln.ac_type and lt.ac_no=ln.ac_no and  cm.cid=sm.cid and ln.ac_type='"+acctype+"' and ln.ac_no="+accno+" order by trn_seq desc");
					
					
					if(rs.next())
					{   
						System.out.println("@@@@@......");
						ac=new AccountObject();	
						ac.setAccno(rs.getInt("ln.ac_no"));
						ac.setAcctype(rs.getString("ln.ac_type"));
						ac.setAccname(rs.getString("name"));
					
						double d=rs.getDouble("pr_bal");
						ac.setAmount(d);
						ac.setCid(rs.getInt("ln.cid"));	
						if(rs.getString("close_date")==null)
							ac.setAccStatus("O");
						else
							ac.setAccStatus("C");													
						ac.setVerified(rs.getString("ln.ve_user"));
						ac.setFreezeInd("F");
						ac.setDefaultInd("F");
						System.out.println("###########.......");
					}
				}
				
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
		return ac;
	}
	
	/**
	 * This method gives the list of scroll numbers for perticular account which are 
	 * verified but not at used.
	 * this will return array of accountObject.
	 * 
	 * In client side this method is using for providing help for the user
	 * by giving full list of scroll numbers available for perticular account. 
	 */
	public AccountObject[] getAccounts(String acctype,int accno,String date)
	{
		AccountObject a[]=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if(accno!=0)			
				rs=stmt.executeQuery("select scroll_no,ac_type,ac_no,name,csh_amt,trn_date from DayCash where  ve_user is not null and trn_date='"+date+"' and attached='F' and ac_type='"+acctype+"' and ac_no="+accno);
			else
				rs=stmt.executeQuery("select scroll_no,ac_type,ac_no,name,csh_amt,trn_date from DayCash where  ve_user is null and trn_date='"+date+"' and attached='F' and ac_type='"+acctype+"' and ac_no="+accno);
			rs.last();	
			a=new AccountObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			
			while(rs.next())
			{
				a[i]=new AccountObject();
				a[i].setCid(rs.getInt(1));
				a[i].setAcctype(rs.getString(2));
				a[i].setAccno(rs.getInt(3));
				a[i].setAccname(rs.getString(4));
				a[i].setAmount(rs.getDouble(5));
				a[i].setAcOpenDate(rs.getString("trn_date"));
				i++;
				
			}
		}catch(Exception exception){
			exception.printStackTrace();
			}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return a;
	}
	
	/**
	 * This method is for getting Account sub categories like staff,senior citizen etc.
	 * This will return all details about a sub category and return type will be 
	 * array of AccSubCategoyObject. 
	 */
	
	public AccSubCategoryObject[] getAccSubCategories(int no) 
	{
		AccSubCategoryObject acc[] =null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			rs=stmt.executeQuery("select * from AccountSubCategory where catcode="+no+""); //where catdesc='"+acty+"'");
			rs.last();
			acc=new  AccSubCategoryObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				acc[i]=new AccSubCategoryObject();
				acc[i].setSubCategoryCode(rs.getInt(1));
				acc[i].setSubCategoryDesc(rs.getString(2));			
				acc[i].setAge(rs.getString("age"));
				i++;
			}
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return acc;
	}
	
	public AccSubCategoryObject[] getAccSubCategories() 
	{
		System.out.println("INSIDE ACCSUBCATEGORIES");
		AccSubCategoryObject acc[] =null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			rs=stmt.executeQuery("select * from AccountSubCategory"); //where catdesc='"+acty+"'");
			rs.last();
			acc=new  AccSubCategoryObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				acc[i]=new AccSubCategoryObject();
				acc[i].setSubCategoryCode(rs.getInt(1));
				acc[i].setSubCategoryDesc(rs.getString(2));			
				acc[i].setAge(rs.getString("age"));
				i++;
			}
			System.out.println("I VALUE===="+i);
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return acc;
	}
	
	public AccCategoryObject[] getAccCategories() 
	{
		System.out.println("INSIDE ACCCATEGORIES");
		AccCategoryObject acc[] =null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			rs=stmt.executeQuery("select * from CustomerType"); 
			rs.last();
			acc=new  AccCategoryObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				acc[i]=new AccCategoryObject();
				acc[i].setCategoryDesc(rs.getString(1));
				acc[i].setCategoryCode(rs.getInt(2));
				i++;
			}
			System.out.println("I VALUE===="+i);
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return acc;
	}
	
	/**
	 * This method gives the address of a perticular customer 
	 * depending on the address type.
	 * 
	 * It returns Address object with all the values of address.
	 *  
	 */
	public Address getAddress(int cid,int addr_type) 
	{
		Address address = null;
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try
		{
			conn = getConnection();
			statement  = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			resultSet = statement.executeQuery("select * from CustomerAddr where cid="+cid+" and addr_type="+addr_type+"");
			
			if(resultSet.next()){ 
				address = new Address();
				address.setAddress(resultSet.getString("address"));
				address.setCity(resultSet.getString("city"));
				address.setCountry(resultSet.getString("country"));
				address.setState(resultSet.getString("state"));
				address.setPin(resultSet.getString("pin"));				
			}
		}catch(Exception exception){
			exception.printStackTrace();
			address = null;
		}	
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return address;
	}
	
	/**
	 * This method is for gettingAddressTypes like Residence,Communication etc
	 * 
	 * It returns array AddressTypesObject.
	 */
	public AddressTypesObject[] getAddressTypes() 
	{
		AddressTypesObject acc[] =null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			rs=stmt.executeQuery("select number,addr_type from AddrTypes");//ship....12/07/2006....replaced addrtype by addr_type
			rs.last();
			acc=new  AddressTypesObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				acc[i]=new AddressTypesObject();
				acc[i].setAddressCode(rs.getInt(1));
				acc[i].setAddressDesc(rs.getString(2));
				i++;
			}
			System.out.println(acc.length);
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return acc;		
	}
	
	/**
	 * This will give the list of branches of the bank
	 * 
	 * if u pass bank code as -1 it will give all the branches except current working branch
	 * if u pass bank code as -2 it will give all the branches including the current working branch
	 * 
	 * if u pass any bank code except the above two numbers it will give u the details of the perticular Bank    
	 */
	public BranchObject[] getBranchDetails(int bcode)
	{
		
		BranchObject[] bm=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			if(bcode==-1)
				rs=stmt.executeQuery("select bm.* from BranchMaster bm,Head where br_code!= bankcode  order by br_code");
			else if(bcode==-2)
				rs=stmt.executeQuery("select bm.* from BranchMaster bm,Head where br_code<1000 order by br_code");
			else if(bcode==-3)
				rs=stmt.executeQuery("select bm.* ,moduleabbr from BranchMaster bm,Head,Modules where br_code<1000 and  br_ac_type = modulecode  order by br_code");
			else 
				rs=stmt.executeQuery("select * from BranchMaster where br_code="+bcode);
			
			rs.last();				
			if(rs.getRow()==0)				
				return bm;
			
			bm=new BranchObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				bm[i]=new BranchObject();
				bm[i].setBranchCode(rs.getInt("br_code"));
				bm[i].setBranchName(rs.getString("br_name"));
				bm[i].setBranchAddress(rs.getString("address"));
				bm[i].setShortName(rs.getString("br_shnm"));
				if(bcode==-3){
					bm[i].setBranchACType(rs.getString("moduleabbr"));
				}
				else{
					System.out.println(rs.getString("br_ac_type"));
					bm[i].setBranchACType(rs.getString("br_ac_type"));
				}
				bm[i].setBranchACNo(rs.getInt("br_ac_no"));
				bm[i].setGlCode(rs.getInt("gl_code"));
				bm[i].setGlType(rs.getString("gl_type"));
				bm[i].setBranchType(rs.getString("br_type"));
				System.out.println (bm[i].getBranchACNo());
				System.out.println (bm[i].getGlCode());
				i++;
			}
			
			
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return bm;
	}	
	
	/**
	 * This will give all the columns of a perticular table.
	 * 
	 * The will return the columns as array of String object. 
	 */
	public String[] getColumns(String tabstr) 
	{
		
		String str[]=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			
			rs=stmt.executeQuery("desc "+tabstr);
			rs.last();
			str=new String[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
				str[i++]=rs.getString(1);
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return str;
	}
	
	/**
	 * This is for getting connection for the database. 
	 */
	private Connection getConnection() throws SQLException
	{
		Connection con = ds.getConnection("root","");
		if(con == null)
			throw new SQLException();
		return con;
	}
	
	/**
	 * Method to get the system date as well as time.
	 * This one we r using in Head panel at client side to display system date and time
	 */	
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
	
	/**
	 * This will give the value of the perticular field of GenParam Table.
	 */
	public Object getGenParam(String column)
	{
		Object obj=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			
			rs=stmt.executeQuery("select "+column+" from GenParam");
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
	
	/**
	 * This gives the current working bank and branch name from the Head table.
	 *  In client side this is appearing in all the screens at the top.
	 * 
	 * It is returning the value as a string object. 
	 */
	public String getHeading() 
	{
		String head=null;
		Connection conn=null;
		Statement stmt=null;
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			
			//Retriev heading from database
			
			ResultSet rs=stmt.executeQuery("select * from Head");
			if(rs.next())
			{
				head=rs.getString(1);
				head=head+"$$$$$$"+rs.getString(2);
				System.out.println("Head Value------------------>"+head);
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return head;
	}
	
	/**
	 * This method is used for getting all the modules and its details.
	 * the value getting from the table is depends on the integer value passed by 
	 * the client while calling the method.
	 * 
	 * if integer is 0 then it gives all the Main modules whose modulecode ends with '000'.
	 * 
	 * if integer is 1 then it gives all the sub module types all module whose modulecode not ends with '000'.
	 * 
	 * if integer is 2 then it gives all the sub modules types which are lies within the modules code sent by the client.
	 * 
	 * if integer is 3 then it gives all the details of the module whose modulecode equals to the code sent by the client.
	 * 
	 * if integer is 4 then it gives all the modules whose modulecode lies within the the modulecode string value sent by the client and modulecode not equal to 1007001 and 1007002.
	 * 
	 * if integer is 5 then it is same as the second option above.   
	 */
	public ModuleObject[] getMainModules(int a,String str) 
	{
		ModuleObject mod[]=null;
		Connection conn=null;
		Statement stmt=null;
		//System.out.println("TO Populated vales in COMBO Box...");
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			
			ResultSet rs=null;
			if(a==0)
				rs=stmt.executeQuery("select * from Modules where modulecode like '%000'");
			else if(a==1)
				rs=stmt.executeQuery("select * from Modules where modulecode not like '%000'");
			else if(a==2)
				rs=stmt.executeQuery("select x.* from Modules x,Modules y where x.modulecode between y.modulecode+1 and y.modulecode+999  and y.modulecode in ("+str+")");
			//rs=stmt.executeQuery("select x.* from Modules x,Modules y where x.modulecode between y.modulecode+1 and y.modulecode+999  and y.modulecode in ("+str+") and y.moduledesc is not null and x.moduledesc is not null");
			else if(a==3)
				rs=stmt.executeQuery("select * from Modules where modulecode='"+str+"'");
			else if(a==4)
				/*rs=stmt.executeQuery("select x.* from Modules x,Modules y where x.modulecode between y.modulecode+1 and y.modulecode+999 and y.modulecode in ("+str+") and x.modulecode not in ('1007001','1007002')");*/
				rs=stmt.executeQuery("select x.* from Modules x,Modules y where x.modulecode between y.modulecode+1 and y.modulecode+999 and y.modulecode in ("+str+")");
			else if(a==5)
				rs=stmt.executeQuery("select x.* from Modules x,Modules y where x.modulecode between y.modulecode+1 and y.modulecode+999 and y.modulecode in ("+str+")");
			if(rs.next())
			{	
				rs.last();
				mod=new ModuleObject[rs.getRow()];
				rs.beforeFirst();
			}
			int i=0;
			while(rs.next())
			{
				mod[i]=new ModuleObject();
				mod[i].setModuleCode(rs.getString("modulecode"));
				mod[i].setModuleDesc(rs.getString("moduledesc"));
				mod[i].setModuleAbbrv(rs.getString("moduleabbr"));
				mod[i].setMinBal(rs.getFloat("min_bal"));
				mod[i].setProperties(rs.getString("other_prop"));
				mod[i].setMaxAmount(rs.getFloat("max_amt"));
				mod[i].setMinPeriod(rs.getInt("min_period"));
				mod[i].setNoOfTrnsPerMonth(rs.getInt("trns_per_mnth"));
				mod[i].setPenaltyRate(rs.getDouble("penalty_rate"));
				mod[i].setMaxRenewalDays(rs.getInt("max_renewal_days"));
				//System.out.println("Max Renewal days="+mod[i].getMaxRenewalDays());
				mod[i].setMaxRenewalCount(rs.getInt("max_renewal_count"));
				mod[i].setTDInterestRate(rs.getDouble("renewal_int_rate"));
				mod[i].setChequeMinAmount(rs.getDouble("min_amt_cheque"));
				mod[i].setChequeMaxAmount(rs.getDouble("max_amt_cheque"));
				mod[i].setSlipMinAmount(rs.getDouble("min_amt_clg"));
				mod[i].setSlipMaxAmount(rs.getDouble("max_amt_clg"));
				mod[i].setLastVoucherScrollno(rs.getInt("lst_voucher_scroll_no"));
				mod[i].setChequeValidityPeriod(rs.getInt("chq_validity_period"));
				mod[i].setStdInst(rs.getInt("std_inst"));
				mod[i].setLastInterestDate(rs.getString("lst_intdt"));
				mod[i].setLinkShares(rs.getFloat("lnk_shares"));
				mod[i].setInspectionPeriod(rs.getInt("inspection_period")); //added by deepa
				mod[i].setLoanModuleCode(String.valueOf(rs.getInt("ln_modulecode")));
                
                //ship....14/02/2007
                mod[i].setLastTRFScrollno(rs.getInt("last_trf_scroll_no"));
                mod[i].setLastInterestDate(rs.getString("lst_intdt"));
                mod[i].setLastVoucherNo(rs.getInt("lst_voucher_no"));
                mod[i].setLastAccNo(rs.getInt("lst_acc_no"));
                mod[i].setIntto_day(rs.getInt("intto_day"));
                ///////////
                
				i++;
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		//System.out.println("LENGTH: "+mod.length);
		return mod;
	}
	
	/**
	 * This will give the perticular nominee details.
	 * 
	 * The return value is array of NomineeObject.
	 */
	public NomineeObject[] getNominee(int reg_no)
	{
		NomineeObject no[]=null;
		Connection conn=null;
		Statement stmt=null;
		
		try
		{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			//ResultSet rs1 = stmt.executeQuery("select * from NomineeMaster where reg_no="+reg_no);//Karthi==>24/03/2006
			ResultSet rs1 = stmt.executeQuery("select * from NomineeMaster where reg_no="+reg_no+" and to_date is null");
			rs1.last();
			if(rs1.getRow() == 0)
				throw new Exception();
			no=new NomineeObject[rs1.getRow()];
			System.out.println("In Comman Bean,no.length==>"+no.length);
			rs1.beforeFirst();
			
			while(rs1.next())
			{
				int k=rs1.getRow()-1;
				
				no[k]=new NomineeObject();
				no[k].setRegNo(rs1.getInt("reg_no"));
				if(rs1.getString("cid")!=null)
					no[k].setCustomerId(rs1.getInt("cid"));
				else
					no[k].setCustomerId(-1);
				
				no[k].setSex(rs1.getInt("sex"));
				no[k].setNomineeName(rs1.getString("name"));
				no[k].setNomineeDOB(rs1.getString("dob"));
				no[k].setNomineeAddress(rs1.getString("address"));
				no[k].setNomineeRelation(rs1.getString("relation"));
				no[k].setPercentage(rs1.getDouble("percentage"));
				no[k].setGuardianName(rs1.getString("guard_name"));
				no[k].setGuardianAddress(rs1.getString("guard_address"));
				no[k].setGuardianType(rs1.getString("guard_type"));
				no[k].setGuardRelation(rs1.getString("guard_rel"));
				no[k].setCourtOrderNo(rs1.getInt("court_order_no"));
				no[k].setCourtOrderDate(rs1.getString("court_order_date"));
				//Added by Karthi==>24/03/2006
				no[k].setAccType(rs1.getString("ac_type"));
				no[k].setAccNo(rs1.getInt("ac_no"));
				no[k].setFromDate(rs1.getString("fr_date"));
				no[k].setToDate(rs1.getString("to_date"));
			}
			
			return no;
		}catch(Exception exception){
			no = null;
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return no;
	}
	
	/**
	 * This method is to get TopMargin,BottomMargin and LinesPerPage for report purpose
	 *  I dont know
	 */
	public int[] getPageDetails() 
	{
		int a[]=new int[3];
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			rs=stmt.executeQuery("select topmargin,bottommargin,linesperpage from GenParam");
			if(rs.next())
			{
				a[0]=rs.getInt(1);
				a[1]=rs.getInt(2);
				a[3]=rs.getInt(3);
			}
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return a;
	}
	
	/**
	 * This method will give the perticular column value of a table.
	 * 
	 * If column value is null then entire table row value.
	 * 
	 * It returns the value as two dimensional array Object with column name and column value.  
	 * 
	 */
	public Object[][] getRows(String str,String column,int no) 
	{
		Object obj[][]=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			
			System.out.println("No : "+no);
			if(column!=null && no!=0)
				rs=stmt.executeQuery("Select * from "+str+" where "+column+"="+no +" order by "+column);
			else if(no==0)
				rs=stmt.executeQuery("Select mem_cat,"+column+" from "+str+" order by mem_cat,"+column);
			else 
				rs=stmt.executeQuery("Select * from "+str);
			
			ResultSetMetaData rsmd=rs.getMetaData();
			int num=rsmd.getColumnCount();
			
			rs.last();
			obj=new Object[rs.getRow()][num];
			rs.beforeFirst();
			
			int i=0;
			while(rs.next())
			{
				for(int j=1;j<=num;j++)
				{
					if(i!=0)
					{
						Object obj1=rs.getObject(j);
						
						if(!String.valueOf(obj[i-1][j-1]).equals(String.valueOf(obj1)))
							obj[i][j-1]=new StringBuffer(String.valueOf(String.valueOf(obj1)));
						else
							obj[i][j-1]=obj1;
					}
					else
						obj[i][j-1]=rs.getString(j);
				}
				i++;			
			}		
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		System.out.println("Length: "+obj.length);
		return obj;
	}
	
	/**
	 * General method for getting the value from perticular table with perticular condition.
	 * 
	 * here table name as well as all conditions are passed when method calling from client side.
	 * 
	 * It returns results as a 2 dimensional Object array. 
	 * 
	 */
	public Object[][] getRows(String str,String column1,String column2,int no,String acc_type,int type) throws RecordsNotFoundException 
	{
		Object obj[][]=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try
		{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			rs=stmt.executeQuery("Select * from "+str+" where "+column1+"="+no+" and "+column2+"='"+acc_type+"'");
			
			ResultSetMetaData rsmd=rs.getMetaData();
			int num=rsmd.getColumnCount();
			
			rs.last();
			if(rs.getRow()==0)
				/* throw new RecordsNotFoundException();*/
				System.out.println("Records Not found");
			obj=new Object[rs.getRow()][num];
			rs.beforeFirst();
			
			int i=0;
			while(rs.next())
			{
				
				for(int j=1;j<=num;j++)
				{
					if(i!=0 && type!=1)
					{
						Object obj1=rs.getObject(j);
						
						if(!String.valueOf(obj[i-1][j-1]).equals(String.valueOf(obj1)))
							obj[i][j-1]=new StringBuffer(String.valueOf(String.valueOf(obj1)));
						else 
							obj[i][j-1]=obj1;			
					}
					else
						obj[i][j-1]=rs.getString(j);
				}
				i++;				
			}
		}catch(SQLException exception){exception.printStackTrace();}		
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return obj;
	}
	
	/**
	 * It gives all the signature details like photo,signature of a perticular account.
	 *  
	 * It returns the result in array of SignatureInstructionObject.
	 */
	public SignatureInstructionObject[] getSigDetails(int accno,String type) 
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
				am[i].setPhoto(b.getBytes(1,(int)b.length()));
				am[i].setSignature(s.getBytes(1,(int)s.length()));
				am[i].setMinLimit(rs.getInt("minlmt"));
				am[i].setMaxLimit(rs.getInt("maxlmt"));
				am[i].setTypeOfOperation(rs.getString("typeofopr"));
				i++;
				
			}
			
			return am;
			
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return am;
		
		
	}
	
	/**
	 * It gives all the signature details like photo,signature of a perticular account.
	 *  
	 * It returns the result in array of SignatureInstructionObject.
	 */
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
			
			am=new SignatureInstructionObject[rs.getRow()];
			
			if(rs.getRow() == 0)
				am=null;
			
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
		
		return am;
		
	}
	
	public SignatureInstructionObject[] getSignatureDetails(int cid) 
	{
		SignatureInstructionObject am[]=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			rs=stmt.executeQuery("select si.*,cm.photo,cm.sign from SignatureInstruction si,CustomerMaster cm where  cm.cid="+cid+" and  si.cid="+cid+"");
			rs.last();
			
			am=new SignatureInstructionObject[rs.getRow()];
			
			if(rs.getRow() == 0)
				am=null;
			
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
		
		return am;
		
	}
	
	
	
	
    //ship.....05/03/2007
	/**
	 * gives the system date.
	 * 
	 *//*
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
	
	*//**
	 * gives the system date and time.
	 */
	public String getSysDateTime() 
	{
		return getDateTime();		
	}
	
	/**
	 * gives the system time.
	 * 
	 *//*
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
	}*/
	///////////////
	
	/**
	 * It checks whether the given date is holiday or not.
	 * 
	 * the return type is boolean true or false.
	 */
	
	public boolean isHoliday(String date)
	{
		boolean check=false;
		Connection connection = null;
		try
		{
			connection = getConnection();
			Statement stmt=connection.createStatement();
			ResultSet rs=stmt.executeQuery("select * from HolidayMaster where date='"+date+"'");
			if(rs.next())
			{
				rs.getString(1);
				check=true;
			}
		}catch(Exception et){check=false;}
		finally{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return check;
	}
	
	/**
	 * This will give the next working day from the given date.
	 * 
	 * Here It checks for all the dates for holiday or not.
	 * 
	 * If the date is found holiday it will go for the next date to check
	 * otherwise it will return the date.
	 * 
	 * The return type is String Object.
	 * @param date
	 * @return
	 */
	public String nextWorkingDay(String date)
	{
		boolean dd=true;
		int num=0;
		try {
			while(dd)
			{
				++num;
				dd=isHoliday(Validations.addDays(date,num));
			}
			
			return Validations.addDays(date,num);
		} catch (DateFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This will give the previous working day of the given date.
	 * 
	 * Here It checks for all the dates for holiday or not.
	 * 
	 * If the date is found holiday it will go for the previous date to check
	 * otherwise it will return the date.
	 * 
	 * The return type is String Object.
	 * @param date
	 * @return
	 */
	public String prevWorkingDay(String date)
	{
		boolean dd=true;
		int num=0;
		try{
			while(dd)
			{
				--num;
				dd=isHoliday(Validations.addDays(date,num));
			}
			return Validations.addDays(date,num);
		}catch(DateFormatException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void setSessionContext(javax.ejb.SessionContext cnt){
		this.ctx=cnt;
	}
	
	/**
	 * General Method to store the each and every account transaction.
	 * the return type is integer positive value. 
	 * @param accounttransobject
	 * @return
	 * @throws SQLException
	 * @throws InsufficientBalanceException
	 * @throws InsufficientBalanceException
	 */	
	public int storeAccountTransaction(AccountTransObject accounttransobject) throws SQLException,InsufficientBalanceException
	{
		System.out.println("Inside StoreAccountTransaction");
		GLTransObject trn_obj=new GLTransObject();
		int scode=0,seq=0;
		PreparedStatement ps=null;
		Connection conn=null;
		String lst_int_caldt=null;
		ResultSet rs=null;
		try
		{
			conn=getConnection();	
			Statement stat=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			if(accounttransobject.getAccType()!=null && accounttransobject.getAccNo()>0)
			{
				System.out.println("Acc_type="+accounttransobject.getAccType()+" & Acc_No="+accounttransobject.getAccNo());			
				
				if(accounttransobject.getAccType().startsWith("1002") || accounttransobject.getAccType().startsWith("1007") ||accounttransobject.getAccType().startsWith("1018")||accounttransobject.getAccType().startsWith("1017"))
				{
					rs=stat.executeQuery("select last_tr_seq from AccountMaster where ac_no="+accounttransobject.getAccNo()+" and ac_type='"+accounttransobject.getAccType()+"'");
					if(rs.next())
						seq=rs.getInt(1)+1;				
					stat.executeUpdate("update AccountMaster set last_tr_seq="+seq+",last_tr_date='"+accounttransobject.getTransDate()+"' where ac_type='"+accounttransobject.getAccType()+"' and ac_no="+accounttransobject.getAccNo()+" ");
				}
				else if(accounttransobject.getAccType().startsWith("1014") || accounttransobject.getAccType().startsWith("1015"))
				{	
					rs=stat.executeQuery("select last_tr_seq from ODCCMaster where ac_no="+accounttransobject.getAccNo()+" and ac_type='"+accounttransobject.getAccType()+"'");
					if(rs.next())
						seq=rs.getInt(1)+1;
					stat.executeUpdate("update ODCCMaster set last_tr_seq="+seq+",last_tr_date='"+accounttransobject.getTransDate()+"' where ac_type='"+accounttransobject.getAccType()+"' and ac_no="+accounttransobject.getAccNo()+" ");
				}			
				
				if(accounttransobject.getAccType().startsWith("1002") || accounttransobject.getAccType().startsWith("1007") ||accounttransobject.getAccType().startsWith("1018")||accounttransobject.getAccType().startsWith("1017"))
					ps=conn.prepareStatement("insert into AccountTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				else if(accounttransobject.getAccType().startsWith("1014") || accounttransobject.getAccType().startsWith("1015"))
					ps=conn.prepareStatement("insert into ODCCTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
				double amt=0;
				ps.setString(1,accounttransobject.getAccType());			
				ps.setInt(2,accounttransobject.getAccNo());
				ps.setString(3,accounttransobject.getTransDate());		
				ps.setString(4,accounttransobject.getTransType());
				
				if(accounttransobject.getAccType().startsWith("1002") || accounttransobject.getAccType().startsWith("1007") || accounttransobject.getAccType().startsWith("1018")||accounttransobject.getAccType().startsWith("1017"))				
					rs=stat.executeQuery("select cl_bal,ac_status,freeze_ind,min_amt_cheque,min_amt_clg,min_bal,ch_bk_issue from AccountTransaction at,AccountMaster am,Modules m where am.ac_no="+accounttransobject.getAccNo()+" and at.ac_no=am.ac_no and am.ac_type='"+accounttransobject.getAccType()+"' and at.ac_type=am.ac_type and m.modulecode='"+accounttransobject.getAccType()+"' order by trn_seq desc limit 1");					
				else if(accounttransobject.getAccType().startsWith("1015"))
					rs=stat.executeQuery("select cl_bal,ac_status,freeze_ind,default_ind,min_amt_cheque,min_amt_clg,min_bal,ch_bk_issue,creditlimit,odccm.int_uptodt from ODCCTransaction odcct,ODCCMaster odccm,Modules m where odccm.ac_no="+accounttransobject.getAccNo()+" and odcct.ac_no=odccm.ac_no and odccm.ac_type='"+accounttransobject.getAccType()+"' and odcct.ac_type=odccm.ac_type and m.modulecode='"+accounttransobject.getAccType()+"' order by trn_seq desc limit 1");
				else if(accounttransobject.getAccType().startsWith("1014"))
					rs=stat.executeQuery("select cl_bal,ac_status,freeze_ind,default_ind,min_amt_cheque,min_amt_clg,min_bal,ch_bk_issue,credit_limit,odccm.int_uptodt from ODCCTransaction odcct,ODCCMaster odccm,Modules m ,StockInspectionDetails stk where odccm.ac_no="+accounttransobject.getAccNo()+" and odcct.ac_no=odccm.ac_no and odccm.ac_type='"+accounttransobject.getAccType()+"' and odcct.ac_type=odccm.ac_type and m.modulecode='"+accounttransobject.getAccType()+"' and stk.ac_type=odccm.ac_type and stk.ac_no=odccm.ac_no order by trn_seq desc,concat(right(stk.insp_date,4),'-',mid(stk.insp_date,locate('/',stk.insp_date)+1, (locate('/',stk.insp_date,4)-locate('/',stk.insp_date)-1)),'-',left(stk.insp_date,locate('/',stk.insp_date)-1)) desc limit 1");
				
				if(rs.next())
				{				
					if(accounttransobject.getAccType().startsWith("1014") || accounttransobject.getAccType().startsWith("1015"))
						lst_int_caldt=rs.getString("odccm.int_uptodt");				
					if(rs.getString("ac_status").equals("C"))
						throw new AccountClosedException();
					if(rs.getString("ac_status").equals("I") && accounttransobject.getCdInd().equals("D"))
						throw new InOperativeAccountException();
					if(rs.getString("freeze_ind").equals("T"))
						throw new FreezedAccountException();
					
					if(accounttransobject.getCdInd().equals("D") && !accounttransobject.getTransType().equals("I"))
					{
						//double min_bal=0;
						double amt_left=0;
						if(accounttransobject.getAccType().startsWith("1015"))
							amt_left=(rs.getDouble("creditlimit")+rs.getDouble("cl_bal"))-accounttransobject.getTransAmount();
						else if(accounttransobject.getAccType().startsWith("1014"))
							amt_left=(rs.getDouble("credit_limit")+rs.getDouble("cl_bal"))-accounttransobject.getTransAmount();
						else
							amt_left=rs.getDouble("cl_bal")-accounttransobject.getTransAmount();
						
						/*if((accounttransobject.getTransMode().equals("T")||accounttransobject.getTransMode().equals("C")) && rs.getString("ch_bk_issue").equals("T"))
						 min_bal=rs.getDouble("min_amt_cheque");
						 else if((accounttransobject.getTransMode().equals("T")||accounttransobject.getTransMode().equals("C") )&& rs.getString("ch_bk_issue").equals("F"))
						 min_bal=rs.getDouble("min_bal");						
						 else if(accounttransobject.getTransMode().equals("G"))
						 min_bal=rs.getDouble("min_amt_clg");
						 
						 System.out.println("minimum balance == "+min_bal);*/
						System.out.println(".......after......"+amt_left);
					}				                
					amt=rs.getDouble("cl_bal");
					rs.close();
				}
				
				System.out.println("trn_seq...."+seq+" amt===>"+amt);
				ps.setInt(5,seq);	
				ps.setDouble(6,accounttransobject.getTransAmount());
				ps.setString(7,accounttransobject.getTransMode());
				ps.setString(8,accounttransobject.getTransSource());
				ps.setString(9,accounttransobject.getCdInd());
				ps.setInt(10,accounttransobject.getChqDDNo());
				ps.setString(11,accounttransobject.getChqDDDate());
				ps.setString(12,accounttransobject.getTransNarr());
				ps.setInt(13,accounttransobject.getRef_No());
				ps.setString(14,accounttransobject.getPayeeName());
				ps.setDouble(15,(accounttransobject.getCdInd().equals("C"))?(amt+accounttransobject.getTransAmount()):(amt-accounttransobject.getTransAmount()));
				ps.setInt(16,accounttransobject.getLedgerPage());
				ps.setString(17,accounttransobject.uv.getUserTml());
				ps.setString(18,accounttransobject.uv.getUserId());
				ps.setString(19,accounttransobject.uv.getUserDate());
				ps.setString(20,accounttransobject.uv.getVerTml());
				ps.setString(21,accounttransobject.uv.getVerId());
				ps.setString(22,accounttransobject.uv.getVerDate());
				if(accounttransobject.getAccType().startsWith("1014") || accounttransobject.getAccType().startsWith("1015"))
					ps.setString(23,lst_int_caldt);
				
				if(ps.executeUpdate()==1)
				{									
					if(accounttransobject.getAccType().startsWith("1002"))// || accounttransobject.getAccType().startsWith("1007") || accounttransobject.getAccType().startsWith("1018")|| accounttransobject.getAccType().startsWith("1017"))
					{
						if(storeNo0fAccountTransactions(accounttransobject.getAccType(),accounttransobject.getAccNo(),2)!=1)
							throw new SQLException("Could not store Penalty for NoofTransaction >Trns_per_mnth");
						else
							System.out.println("Successfully Stored into AccountTransaction if NoofTransaction >Trns_per_mnth");
					}				
					
					/** Posting to GL */
					int type1=0;
					if(accounttransobject.getAccType().startsWith("1002") || accounttransobject.getAccType().startsWith("1007") || accounttransobject.getAccType().startsWith("1018")|| accounttransobject.getAccType().startsWith("1017"))
						rs=stat.executeQuery("select custtype from AccountMaster am,CustomerMaster cm where am.ac_no="+accounttransobject.getAccNo()+" and am.ac_type='"+accounttransobject.getAccType()+"' and am.cid=cm.cid");
					else  if(accounttransobject.getAccType().startsWith("1014") || accounttransobject.getAccType().startsWith("1015"))
						rs=stat.executeQuery("select custtype from ODCCMaster am,CustomerMaster cm where am.ac_no="+accounttransobject.getAccNo()+" and am.ac_type='"+accounttransobject.getAccType()+"' and am.cid=cm.cid");
					
					if(rs.next())
						type1=rs.getInt(1);
					
					if(type1==0)
						scode=1;
					else if(type1==1)
						scode=2;
					
					rs.close();
					
					if(accounttransobject.getTransType().equals("I"))
					{
						//ship.....24/11/2006
						if(type1==0)
							scode=3;
						else if(type1==1)
							scode=4;
						///////////
					}
					else if(accounttransobject.getTransType().equals("E"))
					{
						//ship.....24/11/2006
						if(type1==0)
							scode=5;
						else if(type1==1)
							scode=6;
						///////////
					}
						
					
					//ship......24/11/2006
					//rs=stat.executeQuery("select gk.gl_code,gk.gl_type,gp.mult_by from GLKeyParam gk,GLPost gp where gk.ac_type="+accounttransobject.getAccType()+" and gk.code="+scode+" and gp.trn_type='"+accounttransobject.getTransType()+"' and gp.cr_dr='"+accounttransobject.getCdInd()+"' and gk.ac_type=gp.ac_type and gp.gl_code=gk.gl_code");
					if(accounttransobject.getTransType().equals("I"))
						rs=stat.executeQuery("select gk.gl_code,gk.gl_type,gp.mult_by from GLKeyParam gk,GLPost gp where gk.ac_type="+accounttransobject.getAccType()+" and gk.code="+scode+" and gp.trn_type='"+accounttransobject.getTransType()+"' and gp.cr_dr='D' and gk.ac_type=gp.ac_type and gp.gl_code=gk.gl_code");
					else
						rs=stat.executeQuery("select gk.gl_code,gk.gl_type,gp.mult_by from GLKeyParam gk,GLPost gp where gk.ac_type="+accounttransobject.getAccType()+" and gk.code="+scode+" and gp.trn_type='"+accounttransobject.getTransType()+"' and gp.cr_dr='"+accounttransobject.getCdInd()+"' and gk.ac_type=gp.ac_type and gp.gl_code=gk.gl_code");
					/////////////
					
					if(!accounttransobject.getTransMode().equals("G")) // added by suraj
					{
						if(rs.next())
						{	
							trn_obj.setGLType(rs.getString(2));
							trn_obj.setTrnDate(accounttransobject.getTransDate());
							trn_obj.setGLCode(rs.getString(1));					
							trn_obj.setTrnMode(accounttransobject.getTransMode());
							trn_obj.setAmount(accounttransobject.getTransAmount()*rs.getInt(3));
							
							//ship.....24/11/2006
							if(accounttransobject.getTransType().equals("I"))
								trn_obj.setCdind("D");
							else
								trn_obj.setCdind(accounttransobject.getCdInd());
							///////////////
							
							trn_obj.setAccType(accounttransobject.getAccType());
							trn_obj.setAccNo(String.valueOf(accounttransobject.getAccNo()));
							trn_obj.setTrnSeq(seq);
							trn_obj.setTrnType(accounttransobject.getTransType());
							trn_obj.setRefNo(accounttransobject.getRef_No());
							trn_obj.setVtml(accounttransobject.uv.getVerTml());
							trn_obj.setVid(accounttransobject.uv.getVerId());
							System.out.println("Ver Date="+accounttransobject.uv.getVerDate());
							trn_obj.setVDate(accounttransobject.uv.getVerDate());
							storeGLTransaction(trn_obj);
						}
					}
				}
			}
			else
				System.out.println("Acc Type is NULL / Acc No is 0 ");	
			
		}catch(SQLException exception){
			exception.printStackTrace();
			ctx.setRollbackOnly();
			throw exception;
		}catch (AccountClosedException e) {
			e.printStackTrace();
			ctx.setRollbackOnly();
		}catch (InOperativeAccountException e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}catch (FreezedAccountException e) {
			e.printStackTrace();
			ctx.setRollbackOnly();
		}catch(AccountNotFoundException e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
		
		finally
		{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return 1;
	}   
	
	/*public int storeGLTransaction(GLTransObject trnobj) throws SQLException 
	{
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt;
		ResultSet rs_glmaster = null,rs_glkeyparam = null;
		
		try
		{
			conn=getConnection();
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//ship.....05/01/2006.....to check the presence of glcode in the master
			//rs_glmaster = stmt.executeQuery("select * from GLMaster where gl_code="+trnobj.getGLCode()+"");
			rs_glmaster = stmt.executeQuery("select * from GLMaster where gl_code="+trnobj.getGLCode()+" and ((from_date<='"+Validations.convertYMD(trnobj.getTrnDate())+"' and to_date is null) or (from_date<='"+Validations.convertYMD(trnobj.getTrnDate())+"' and to_date>='"+Validations.convertYMD(trnobj.getTrnDate())+"'))");
			//ship......03/07/2006....to check the period
			
			pstmt=conn.prepareStatement("insert into GLTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1,trnobj.getTrnDate());
			
			if(rs_glmaster.next())
			{
				pstmt.setString(2,trnobj.getGLType());
				pstmt.setInt(3,Integer.parseInt(trnobj.getGLCode()));
			}
			else
			{
				rs_glkeyparam = stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type=1012000 and code=1");
				
				if(rs_glkeyparam.next())
				{
					pstmt.setString(2,rs_glkeyparam.getString("gl_type"));
					pstmt.setInt(3,rs_glkeyparam.getInt("gl_code"));
				}
			}
			
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
			
			if(pstmt.executeUpdate() == 0)
				throw new SQLException("Common Bean GL posting problem");
		}
		catch(SQLException exception){
			exception.printStackTrace();
			ctx.setRollbackOnly();
			throw exception;
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally
		{
			conn.close();
		}
		return 1;	    
	}*/
	
	//added by suraj
	public int storeGLTransaction(GLTransObject trnobj) throws SQLException 
	{
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt;
		ResultSet rs_glmaster = null,rs_glkeyparam = null;
		
		//added by suraj
		try
		{
			conn=getConnection();
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//ship.....05/01/2006.....to check the presence of glcode in the master
			//rs_glmaster = stmt.executeQuery("select * from GLMaster where gl_code="+trnobj.getGLCode()+"");
			System.out.println(trnobj.getTrnDate());
			rs_glmaster = stmt.executeQuery("select * from GLMaster where gl_code="+trnobj.getGLCode()+" and ((from_date<='"+Validations.convertYMD(trnobj.getTrnDate())+"' and to_date is null) or (from_date<='"+Validations.convertYMD(trnobj.getTrnDate())+"' and to_date>='"+Validations.convertYMD(trnobj.getTrnDate())+"'))");
			//ship......03/07/2006....to check the period
			
			pstmt=conn.prepareStatement("insert into GLTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1,trnobj.getTrnDate());
			
			if(rs_glmaster.next())
			{
				pstmt.setString(2,trnobj.getGLType());
				pstmt.setInt(3,Integer.parseInt(trnobj.getGLCode()));
			}
			else
			{
				rs_glkeyparam = stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type=1012000 and code=1");
				
				if(rs_glkeyparam.next())
				{
					pstmt.setString(2,rs_glkeyparam.getString("gl_type"));
					pstmt.setInt(3,rs_glkeyparam.getInt("gl_code"));
				}
			}
			
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
			
			if(pstmt.executeUpdate() == 0){
				System.out.println("i am not here");
				throw new SQLException("Common Bean GL posting problem");
			}
			System.out.println("i am here yes i executed"+trnobj.getTrnDate());
		}
		catch(SQLException exception){
			exception.printStackTrace();
			ctx.setRollbackOnly();
			throw exception;
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally
		{
			conn.close();
		}
		return 1;	    
	}
	
	
	
	/**
	 * General method to store the all kinds of GLTransactions.
	 * the return type is integer positive value.
	 * @param trnobj
	 * @return
	 * @throws SQLException
	 *  
	 */
	//Modified by Riswan 7th Dec
	public int storeGLTransaction(GLTransObject[] trnobj) throws SQLException 
	{
		PreparedStatement[] pstmt =new PreparedStatement[trnobj.length];
		Statement stmt = null;//ship.....07/07/2006
		ResultSet rs_glmaster = null,rs_glkeyparam = null;//ship......07/07/2006
		Connection conn=null;
		
		try
		{
			conn=getConnection();
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println("trnobj.length()======>"+trnobj.length);
			//pstmt=conn.prepareStatement("insert into GLTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,'"+getSysDateTime()+"')");
			for(int i=0; i<trnobj.length; i++ )
			{
				System.out.println("trnobj[i].getGLCode()---->"+trnobj[i].getGLCode());			
			//System.out.println("Validations.convertYMD(trnobj[i].getTrnDate())--"+Validations.convertYMD(trnobj[i].getTrnDate()));
				//ship.....07/07/2006.....to check the presence of gl code
				rs_glmaster = stmt.executeQuery("select * from GLMaster where gl_code="+trnobj[i].getGLCode()+" and ((from_date<='"+Validations.convertYMD(trnobj[i].getTrnDate())+"' and to_date is null) or (from_date<='"+Validations.convertYMD(trnobj[i].getTrnDate())+"' and to_date>='"+Validations.convertYMD(trnobj[i].getTrnDate())+"'))");
				
				pstmt[i]=conn.prepareStatement("insert into GLTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmt[i].setString(1,trnobj[i].getTrnDate());
				
				if(rs_glmaster.next())
				{
					pstmt[i].setString(2,trnobj[i].getGLType());
					pstmt[i].setString(3,trnobj[i].getGLCode());
				}
				else
				{
					rs_glkeyparam = stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type=1012000 and code=1");
					
					if(rs_glkeyparam.next())
					{
						pstmt[i].setString(2,rs_glkeyparam.getString("gl_type"));
						pstmt[i].setInt(3,rs_glkeyparam.getInt("gl_code"));
					}
				}
				
				/*pstmt[i].setString(2,trnobj[i].getGLType());
				 pstmt[i].setString(3,trnobj[i].getGLCode());*/
				
				pstmt[i].setString(4,trnobj[i].getTrnMode());
				pstmt[i].setDouble(5,trnobj[i].getAmount());
				pstmt[i].setString(6,trnobj[i].getCdind());
				pstmt[i].setString(7,trnobj[i].getAccType());
				pstmt[i].setString(8,trnobj[i].getAccNo());
				pstmt[i].setInt(9,trnobj[i].getTrnSeq());
				pstmt[i].setString(10,trnobj[i].getTrnType());
				pstmt[i].setInt(11,trnobj[i].getRefNo());
				pstmt[i].setString(12,trnobj[i].getVtml());
				pstmt[i].setString(13,trnobj[i].getVid());
				pstmt[i].setString(14,trnobj[i].getVDate());
			}
			
			for(int r=0; r<pstmt.length; r++ )
			{
				if(pstmt[r].executeUpdate() == 0)
					throw new SQLException("Common Bean GL posting problem");
			}
		}
		catch(SQLException exception){
			exception.printStackTrace();
			ctx.setRollbackOnly();
			throw exception;
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally
		{
			conn.close();
		}
		
		return 1;	    
	}
	
	/**
	 * It stores the details of the nominee made for perticular account.
	 * Whenever it adds new nominee details into the NomineeMaster it generates
	 * nominee registration number.
	 * 
	 * the returning value is this registration number.
	 * 
	 * Nomiees entries for perticular account may be more than 1 also.
	 * @param nom
	 * @param actype
	 * @param acno
	 * @return
	 * @throws NomineeNotCreatedException
	 */
	public int storeNominee(NomineeObject nom[],String actype,int acno,String date) throws NomineeNotCreatedException 
	{
		int nomid=0;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		System.out.println("entering Store Nominee");
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			if(nom[0].getRegNo()==0)
			{
				//rs=stmt.executeQuery("select lst_nom_no from GenParam");//Karthi==>24/03/2006
				//rs=stmt.executeQuery("select std_inst from Modules where modulecode='1019001'");//std_inst-->nom_no(reg_no)
				rs=stmt.executeQuery("select lst_acc_no from Modules where modulecode='1019001'");
				rs.next();
				nomid=rs.getInt(1)+1;
				rs.close();
				//if(stmt.executeUpdate("update GenParam set lst_nom_no="+nomid)!=1)//Karthi==>24/03/2006
				//if(stmt.executeUpdate("update Modules set std_inst="+nomid+" where modulecode='1019001'")!=1)//std_inst-->nom_no(reg_no)
				if(stmt.executeUpdate("update Modules set lst_acc_no="+nomid+" where modulecode='1019001'")!=1)
					throw new Exception();
				System.out.println("nom id================="+nomid);
				System.out.println("nom[0].getRegNo()==0");
			}
			else
			{
				System.out.println("!!!!nom[0].getRegNo()!=0");
				nomid=nom[0].getRegNo();
				System.out.println("nom id====="+nomid);
				System.out.println("Deleting...........");
				System.out.println("get reg no"+nom[0].getRegNo());
				System.out.println("acctype"+nom[0].getAccType());
				System.out.println("accno"+nom[0].getAccNo());
				stmt.executeUpdate("delete from NomineeMaster where reg_no="+nom[0].getRegNo()+" ");//and ac_type='"+actype+"' and ac_no="+nom[0].getAccNo());
				System.out.println("deleted...........+");
			}	 	
			
			PreparedStatement preparedstatement=conn.prepareStatement("insert into NomineeMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+date+"',null)");
			for(int k=0;k<nom.length;k++)
			{
				System.out.println("insertinginto nominee master...........");
				System.out.println("nm.length"+nom.length);
				preparedstatement.setInt(1,nomid);
				preparedstatement.setInt(2,nom[k].getCustomerId());
				preparedstatement.setString(3,actype);
				preparedstatement.setInt(4,acno);
				preparedstatement.setInt(5,nom[k].getSex());
				preparedstatement.setString(6,nom[k].getNomineeName());
				preparedstatement.setString(7,nom[k].getNomineeDOB());
				preparedstatement.setString(8,nom[k].getNomineeAddress());
				preparedstatement.setString(9,nom[k].getNomineeRelation());
				preparedstatement.setDouble(10,nom[k].getPercentage());
				preparedstatement.setString(11,nom[k].getGuardiantype());
				preparedstatement.setString(12,nom[k].getGuardianName());
				preparedstatement.setString(13,nom[k].getGuardianAddress());
				preparedstatement.setInt(14,nom[k].getGuardSex());
				preparedstatement.setString(15,nom[k].getGuardRelation());
				preparedstatement.setInt(16,nom[k].getCourtOrderNo());
				preparedstatement.setString(17,nom[k].getCourtOrderDate());
				
				if(preparedstatement.executeUpdate()!=1)
					throw new Exception();
				System.out.println("inserted..........+");
			}
			
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		}catch(Exception exception){
			ctx.setRollbackOnly();
			exception.printStackTrace();
			throw new NomineeNotCreatedException();
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return nomid;
	}
	
	/**
	 * This method is for creating a payorder means writing a PO details into
	 * PayOIrderMake table.
	 * 
	 * If it stores properly then it retuns a payorder number which is generated
	 * at the time of writing into table.  
	 * 
	 * @param po
	 * @return
	 * @throws SQLException
	 */
	public int storePayOrder(PayOrderObject po) throws SQLException 
	{
		int pno=0;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try
		{
			System.out.println("inside storePayOrder");
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			
			rs=stmt.executeQuery("select last_trf_scroll_no from Modules where modulecode='1016001'");
			
			if(rs.next())
				pno=rs.getInt(1)+1;
			
			rs.close();
			
			PreparedStatement ps=conn.prepareStatement("insert into PayOrderMake values(?,?,?,'"+po.getPODate()+"',?,?,?,?,?,?,?,?,'F',?,?,?,?,?,?,?)");
			
			ps.setString(1,po.getPOType());
			ps.setInt(2,po.getCustType());
			ps.setInt(3,pno);
			ps.setString(4,po.getPOPayee());
			ps.setString(5,po.getPOAccType());
			ps.setInt(6,po.getPOAccNo());
			ps.setString(7,po.getPOFavour());
			ps.setString(8,po.getPOGlType());
			ps.setInt(9,po.getPOGlCode());
			ps.setString(10,po.getPOGlName());//po_glname, Riz
			ps.setDouble(11,po.getPOAmount());
			ps.setDouble(12,po.getCommissionAmount());
			ps.setString(13,po.uv.getUserTml());
			
			ps.setString(14,po.uv.getUserId());
			ps.setString(15,po.uv.getUserDate());//ship
			ps.setString(16,po.uv.getVerTml());
			ps.setString(17,po.uv.getVerId());
			ps.setString(18,po.uv.getVerDate());//ship
			
			if(ps.executeUpdate()==1)
			{
				if(stmt.executeUpdate("update Modules set last_trf_scroll_no=last_trf_scroll_no+1 where modulecode='1016001'")==0)
					throw new SQLException("My Exception");
			}
			else 
				throw new SQLException();
		}
		catch(SQLException exception){
			exception.printStackTrace();
			ctx.setRollbackOnly();
			throw exception;
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally
		{
			try
			{		    	
				conn.close();
			}
			catch(Exception exception){exception.printStackTrace();}
		}
		
		return pno;
	}
	
	/**
	 * It will store the Signature details for the perticular account into
	 * SignatureInstruction table.
	 * if it is successfull then returns true else false.
	 * @param a
	 * @param acno
	 * @return
	 * @throws SignatureNotInsertedException
	 */
	public boolean storeSignatureDetails(SignatureInstructionObject a[],int acno) throws SignatureNotInsertedException 
	{
		System.out.println("Storing SignatureDetails...");
		Connection conn=null;
		try{
			conn=getConnection();	
			
			PreparedStatement ps=conn.prepareStatement("insert into SignatureInstruction values(?,?,?,?,?,?,?,?)");
			for(int i=0;i<a.length;i++)
			{
				ps.setString(1,a[i].getSAccType());
				ps.setInt(2,acno);
				ps.setInt(3,a[i].getCid());
				ps.setString(4,a[i].getName());
				ps.setString(5,a[i].getDesignation());
				ps.setInt(6,a[i].getMinLimit());
				ps.setInt(7,a[i].getMaxLimit());
				ps.setString(8,a[i].getTypeOfOperation());
				if(ps.executeUpdate()!=1)
				{
					System.out.println("inside not inserted signature");
					throw new SignatureNotInsertedException();
				}
			}
		}catch(SQLException exception){
			exception.printStackTrace();
			ctx.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally{
			try{
				if(conn!=null)
					conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return true;
	}
	
	/**
	 * It stores all kinds of transfer voucher into the InterestTransferVoucher table.
	 * aIt generates one transfer voucher number for each entry and
	 * returns the newly created transfer voucher number.
	 * @param trf
	 * @return
	 * @throws SQLException
	 */
	public int storeTrfVoucher(TrfVoucherObject trf,String date,String datetime) throws SQLException   
	{
		int pno=0;
		Connection conn=null;
		try
		{
			conn=getConnection();			
			pno = getModulesColumn("lst_voucher_no",trf.getAccType());			
			PreparedStatement ps=conn.prepareStatement("insert into InterestTransferVoucher values(?,'"+date+"',?,?,?,?,?,?,?,?,?,?,?,?,'"+datetime+"')");
			//ps.setString(1,trf.getVoucherType());
			ps.setInt(1,pno);
			ps.setDouble(2,trf.getTrfAmount());
			ps.setString(3,trf.getAccType());
			ps.setInt(4,trf.getAccNo());
			ps.setString(5,trf.getTvPrtInd());
			ps.setString(6,trf.getTvPayInd());
			ps.setString(7,trf.getDDPurInd());
			ps.setString(8,trf.getPayAccType());
			ps.setString(9,trf.getTvPayDate());
			ps.setString(10,trf.getPayMode());
			ps.setInt(11,trf.getPayAccNo());
			ps.setString(12,trf.uv.getUserTml());
			ps.setString(13,trf.uv.getUserId());
			ps.executeUpdate(); 
		}catch(SQLException exception){
			ctx.setRollbackOnly();
			exception.printStackTrace();
			throw exception;
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}  
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}		
		return(pno);
	}
	
	/**
	 * This will update the perticular field in the Genparam table with the value
	 * passed by the client.
	 * It returns true if successfull else false.
	 * @param column
	 * @return
	 */
	public boolean UpdateGenParam(String column) 
	{
		Connection conn=null;
		Statement stmt=null;
		
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			if(stmt.executeUpdate("update GenParam set "+column+"="+column+"+1")==1)
				return true;
			
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return false;
	}
	
	/**
	 * This will update the perticular field in the Genparam table with the value
	 * passed by the client.
	 * It returns true if successfull else false.
	 * @param column
	 * @return
	 */
	public boolean UpdateGenParam(String column,int i) 
	{
		Connection conn=null;
		Statement stmt=null;
		
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			if(stmt.executeUpdate("update GenParam set "+column+"="+i)==1)
				return true;
			
		}catch(SQLException exception){
			exception.printStackTrace();
			ctx.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return false;
	}
	
	/**
	 * It gives the name of a perticular account holder.
	 * Returning value is name String of the account holder.
	 * @param ac_type
	 * @param acno
	 * @return
	 * @throws CustomerNotFoundException
	 */
	public String getName(String ac_type,int acno) throws CustomerNotFoundException{
		Connection connection = null;
		try 
        {
			LoanLocalHome loanLocalHome = (LoanLocalHome) new InitialContext().lookup("LOANSLOCALWEB");
			LoanLocal loanLocal = loanLocalHome.create();
			int cid = loanLocal.getCid(ac_type,acno);
			connection = getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select concat(ifnull(fname,''),' ',ifnull(mname,''),' ',ifnull(lname,'')) as name from CustomerMaster where cid="+cid);
			rs.next();
			return rs.getString(1);
		} 
        catch(NamingException ne)
        {
            ne.printStackTrace();
        }
        catch(CreateException e) 
        {
			e.printStackTrace();
		} 
        catch(SQLException e) 
        {
			e.printStackTrace();
		}
        finally
		{  try
		{
			connection.close();
		}catch(SQLException sql){sql.printStackTrace();}
		}
		throw new CustomerNotFoundException();
	}
	
	/**
	 * It gives the list of all the scroll numbers generated from the cashier 
	 * for the perticular account holder.
	 * It returns full details of all the scrolls as CashObject array. 
	 * @param acctype
	 * @param type
	 * @return
	 * @throws RecordsNotFoundException
	 */
	public CashObject[] getScrollNumbers(String acctype,int type,String date) throws RecordsNotFoundException
	{
		Connection conn = null;
		CashObject cashObject[] = null;
		try {
			conn = getConnection();
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = null;
			System.out.println("SYSDATE : "+date);
			System.out.println("ACCTYPE : "+acctype);
			
			if(type==1)
				
				if(type==1)
					resultSet = statement.executeQuery("select * from DayCash where trn_date='"+date+"' and ac_type='"+acctype+"' and attached='F' and ve_user is null and ac_no=0");
			resultSet.last();
			if(resultSet.getRow()==0)
				throw new RecordsNotFoundException();
			cashObject = new CashObject[resultSet.getRow()];
			resultSet.beforeFirst();
			int i=0;
			while(resultSet.next()) {
				cashObject[i] = new CashObject();
				cashObject[i].setScrollno(resultSet.getInt("scroll_no"));
				cashObject[i].setTrndate(resultSet.getString("trn_date"));
				cashObject[i].setAmount(resultSet.getDouble("csh_amt"));
				cashObject[i].setAccname(resultSet.getString("name"));
				i++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e1) {    
				e1.printStackTrace();
			} finally {
			}
		}  
		return cashObject;	    
	}
	
	/**
	 * It gives the list of all the Control numbers generated from the clearing 
	 * in the name of perticular account.
	 * It returns full details of all the Control numbers as ClearingObject array. 
	 * @param acctype
	 * @param type
	 * @return
	 * @throws RecordsNotFoundException
	 */
	public ClearingObject[] getControlNumbers(String acctype,int type) throws RecordsNotFoundException
	{
		Connection conn = null;
		ClearingObject clearingObject[] = null;
		try {
			conn = getConnection();
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = null;
			if(type==1)
				resultSet = statement.executeQuery("select * from Cheque where cr_ac_type='"+acctype+"' and cr_ac_no=0 and post_ind='T' and desp_ind='T' and ve_user is not null"); 
			resultSet.last();
			if(resultSet.getRow()==0)
				throw new RecordsNotFoundException();
			clearingObject = new ClearingObject[resultSet.getRow()];
			resultSet.beforeFirst();
			int i=0;
			while(resultSet.next()) {
				clearingObject[i] = new ClearingObject();
				clearingObject[i].setControlNo(resultSet.getInt("ctrl_no"));
				clearingObject[i].setQdpDate(resultSet.getString("qdp_dt"));
				clearingObject[i].setTranAmt(resultSet.getDouble("trn_amt"));
				clearingObject[i].setPayeeName(resultSet.getString("payee_name"));
				i++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e1) {    
				e1.printStackTrace();
			} finally {
			}
		}  
		return clearingObject;	    
	}
	
	/**
	 * It gives the list of not verified Deposit accounts and its details
	 * for a perticuplar account type. 
	 * It is returning the results in array of AccountObject.
	 * @param acctype
	 * @param type
	 * @return
	 * @throws RecordsNotFoundException
	 */
	public AccountObject[] getNotverifiedNumbers(String acctype,int type) throws RecordsNotFoundException
	{
		Connection conn = null;
		AccountObject accountObject[] = null;
		try {
			conn = getConnection();
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = null;
			if(type==1)
				resultSet = statement.executeQuery("select *,concat_ws(' ',fname,mname,lname) as name from DepositMaster,CustomerMaster where ac_type='"+acctype+"' and DepositMaster.ve_user is null and DepositMaster.cid=CustomerMaster.cid"); 
			resultSet.last();
			if(resultSet.getRow()==0)
				throw new RecordsNotFoundException();
			accountObject = new AccountObject[resultSet.getRow()];    
			resultSet.beforeFirst(); 
			int i=0;
			while(resultSet.next()) {
				accountObject[i] = new AccountObject();
				accountObject[i].setAccname(resultSet.getString("name"));
				accountObject[i].setAccno(resultSet.getInt("ac_no"));
				i++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e1) {    
				e1.printStackTrace();
			} finally {
			}
		}  
		return accountObject;	    
	}
	
	/**
	 * It will give the future date from the current date by adding the number of months.
	 * 
	 * returning the date as String object. 
	 * @param cur_date
	 * @param month
	 * @return
	 */
	public String getFutureMonthDate(String cur_date,int month) 
	{ 
		Connection conn = null;
		Statement stmt = null;
		String string_date=cur_date;
		try
		{
			conn = getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select DATE_FORMAT(DATE_ADD('"+Validations.convertYMD(cur_date)+"', INTERVAL "+month+" MONTH),'%d/%m/%Y')");
			rs.next();
			string_date = rs.getString(1);
		}catch (SQLException exception) {
			exception.printStackTrace();
			string_date = cur_date;
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return string_date;
	}
	
	/**
	 * It will give the future date from the current date by adding the number of days.
	 * 
	 * returning the date as String object. 
	 * @param cur_date
	 * @param //month
	 * @return
	 */
	public String getFutureDayDate(String cur_date,int days) 
	{  
		Connection conn = null;
		Statement stmt = null;
		String string_date=cur_date;
		try
		{
			System.out.println("Current date???"+cur_date+"DAYS??:?"+days);
			conn = getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select DATE_FORMAT(DATE_ADD('"+Validations.convertYMD(cur_date)+"', INTERVAL "+days+" DAY),'%d/%m/%Y')");
			rs.next();
			string_date = rs.getString(1);
		}catch (SQLException exception) {
			exception.printStackTrace();
			string_date = cur_date;
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return string_date;
	}
	
	/**
	 * It gives number of days between the two dates.
	 *
	 * @param first_date
	 * @param second_date
	 * @return
	 */
	public int getDaysFromTwoDate(String first_date,String second_date) 
	{  
		Connection conn = null;
		Statement stmt = null;
		int int_date=0;
		try
		{
			conn = getConnection();
			stmt = conn.createStatement();
			if(second_date!=null  && first_date!=null){
			ResultSet rs = stmt.executeQuery("select DATEDIFF('"+Validations.convertYMD(second_date)+"','"+Validations.convertYMD(first_date)+"')");
			
			rs.next();
			int_date = rs.getInt(1);
			}
		}catch (SQLException exception) {
			exception.printStackTrace();
			
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return int_date;
	}
	
	/**
	 * It gives number of months between the two dates.
	 *
	 * @param //first_date
	 * @param //second_date
	 * @return
	 */
	public int getNoOfMonths(String date_first,String date_second)
	{
		Connection conn = null;
		Statement stmt = null;
		int no_of_months=0;
		try
		{
			conn = getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select (year('"+Validations.convertYMD(date_second)+"')-year('"+Validations.convertYMD(date_first)+"'))*12+(month('"+Validations.convertYMD(date_second)+"')-month('"+Validations.convertYMD(date_first)+"'))-if(dayofmonth('"+Validations.convertYMD(date_second)+"') < dayofmonth('"+Validations.convertYMD(date_first)+"'),1,0) as months");
			rs.next();
			no_of_months = rs.getInt(1);
		}catch (SQLException exception) {
			exception.printStackTrace();			
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return no_of_months;	    
	}
	
	/**
	 * This method will store the letter templates into the database in Template table.
	 * @param text
	 * @param acty
	 * @param stage
	 * @param user
	 * @param tml
	 * @param temp_no
	 * @return
	 */
	public boolean storeTemplate(String text,String acty,int stage,String user,String tml,int temp_no) 
	{
		System.out.println("***inside storeTemplate****");
		
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			if(temp_no==0)
			{
				PreparedStatement pstmt=conn.prepareStatement("insert into Template values(?,?,?,?,?,?,date_format(sysdate(),'%d/%m/%Y %r'))");
				ResultSet rs=stmt.executeQuery("select max(temp_no) from Template where ac_type='"+acty+"'");
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
			
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return false;
	}
	
	/**
	 *This will delete the perticular Template from the table for perticular account type.
	 * @param ac_type
	 * @param int_template_no
	 * @return
	 */
	public int DeleteTemplate(String ac_type,int int_template_no)
	{
		Connection conn=null;
		try
		{         
			conn=getConnection();
			
			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			if(stmt.executeUpdate("delete from Template where temp_no="+int_template_no+" and ac_type='"+ac_type+"' ")==0)
				throw new SQLException();
			
		}catch(SQLException ex){
			ex.printStackTrace();
			ctx.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(SQLException e){e.printStackTrace();}
		}
		return 0;
	}
	
	/**
	 * It will give perticular template for a perticular account type.
	 * 
	 * It returns the result as String array.
	 * @param stage
	 * @param acty
	 * @return
	 * @throws RecordsNotFoundException
	 */
	public String[] getTemplate(int stage,String acty) throws RecordsNotFoundException
	{
		System.out.println("stage == "+stage);
		String str[]=null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			System.out.println("Account Type == "+acty);
			ResultSet rs=stmt.executeQuery("select * from Template where ac_type='"+acty+"'");
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
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return str;
	}
	
	/**
	 * It gives value of a perticular column in Modules 
	 * table for perticular account type(module code).
	 * @param column_name
	 * @param acc_type
	 * @return
	 * @throws SQLException
	 */
	public int getModulesColumn(String column_name,String acc_type) throws SQLException
	{
		Connection connection = null;
		int return_value = 0;
		try {
			connection = getConnection();
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = statement.executeQuery("select modulecode,"+column_name+" from Modules where modulecode='"+acc_type+"'");
			if(resultSet.next()) {
				return_value = resultSet.getInt(2)+1;//shubha....incrementing voucher no before updating
				resultSet.updateInt(2,return_value);
				resultSet.updateRow();	            
			}	 
		}catch (SQLException e) {
			ctx.setRollbackOnly();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally {
			if(connection != null)
				connection.close();            
		}
		return return_value;
	} 
	
	/**
	 * Generalised method to get all the details of perticular table and 
	 * columns with some conditions.
	 * 
	 * Here everything table name,column name and the condition is passed while 
	 * method calling.
	 * 
	 * Returning all the value in double array Object. 
	 * @param tablename
	 * @param colnames
	 * @param condition
	 * @return
	 */
	
	public Object[][] getHelpDetails(String tablename,String colnames[],String condition)
	{
		Object obj[][]=null;
		Connection connection = null;
		ResultSet resultSet = null;
		try {
			String cols="";
			for(int i=0;i<colnames.length;i++)
				if(i<(colnames.length-1)) 
					cols+=colnames[i]+",";
				else
					cols+=colnames[i];
			System.out.println("cols="+cols+" table name="+tablename);
			connection = getConnection();
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			if(condition!=null)
				resultSet = statement.executeQuery("select distinct "+cols+" from "+tablename+" where "+condition+"");
			else
				resultSet = statement.executeQuery("select distinct "+cols+" from "+tablename+"");
			
			if(resultSet.next()) {
				resultSet.last();
				System.out.println("inside res");
				obj=new Object[resultSet.getRow()][];
				resultSet.beforeFirst();
			} 
			int i=0;
			while(resultSet.next())
			{            
				obj[i]=new Object[colnames.length];
				for(int j=0;j<colnames.length;j++)
					obj[i][j]=resultSet.getString(j+1);
				i++;
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			ctx.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally {
			if(connection != null)
				try {
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}            
		}
		return obj;
	}
	
	/**
	 * This will updates the GenParam and gives the updated value.
	 * @param column_name
	 * @return
	 * @throws SQLException
	 */
	public int getGenParamUpdated(String column_name) throws SQLException
	{
		Connection connection = null;
		int return_value = 0;
		try {
			connection = getConnection();
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = statement.executeQuery("select "+column_name+" from GenParam");
			resultSet.next();
			return_value = resultSet.getInt(1);
			statement.executeUpdate("update GenParam set "+column_name+"="+column_name+"+1");
		}catch (SQLException e) {
			ctx.setRollbackOnly();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally {
			if(connection != null)
				connection.close();            
		} 
		return return_value;
	}
	
	//Deepa on 23/01/2006
	public int getNo0fAccountTransactions(String actype,int acno) throws AccountNotFoundException,SQLException
	{
		System.out.println("Inside ++++++++++++++GETNOOFACCOUNTTRANSACTION++++++++++");
		Connection connection = null;
		int return_value = 0;
		int trns_permonth = 0;	    
		try 
		{
			connection = getConnection();
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			Statement stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("select trns_per_mnth from Modules where modulecode='"+actype+"'");
			if(rs.next())
			{
				trns_permonth = rs.getInt("trns_per_mnth");	       
				
				ResultSet rs1 = statement.executeQuery("select trn_date,ref_no,trn_type from AccountTransaction where ac_type='"+actype+"' and ac_no="+acno+" and trn_type!='I' and month(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)))=month(curdate()) and year(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)))=year(curdate())");
				int i=0;
				String cur_date=null;
				String cur_trn_type=null;
				int cur_ref_no=0;
				String next_date=null;
				String next_trn_type=null;
				int next_ref_no=0;
				while(rs1.next())
				{
					cur_date=rs1.getString("trn_date");
					cur_ref_no=rs1.getInt("ref_no");
					cur_trn_type=rs1.getString("trn_type");
					if(rs1.next())
					{
						next_date=rs1.getString("trn_date");
						next_ref_no=rs1.getInt("ref_no");
						next_trn_type=rs1.getString("trn_type");
						if(next_ref_no!=0 && cur_ref_no!=0)
						{
							if( (next_date.equals(cur_date) && next_ref_no==cur_ref_no) && !next_trn_type.equals(cur_trn_type) )
								i++;
							else
							{
								rs1.previous();
								i++;
							}
						}
						else
							i++;
					}
					i++;
				}
				if(i>trns_permonth)             
					return_value = 0;
				else 
					return_value = 1;
			}
			else
				return_value = 0; 	        
		}catch (SQLException e) 
		{ctx.setRollbackOnly();
		throw e;
		}
		finally 
		{
			if(connection != null)
				connection.close();            
		} 
		return return_value;
	}
	
	//Deepa 24/01/2006		
	public int storeNo0fAccountTransactions(String actype,int acno,int type) throws AccountNotFoundException,SQLException
	{
		System.out.println("Inside ++++++++++++++STORE NO OF ACCOUNTTRANSACTION++++++++++");
		Connection connection = null;
		int return_value = 0;
		int trns_permonth = 0;
		int seq=0;
		double comm_rate=0;
		PreparedStatement ps=null;
		try 
		{
			connection = getConnection();
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			Statement stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			Statement stat=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("select at.*,m.trns_per_mnth,m.comm_rate_for_amt from AccountTransaction at,AccountMaster am,Modules m where am.ac_no="+acno+" and at.ac_no=am.ac_no and am.ac_type='"+actype+"' and at.ac_type=am.ac_type and m.modulecode='"+actype+"' order by trn_seq desc limit 1");
			if(rs.next())
			{
				trns_permonth = rs.getInt("m.trns_per_mnth");
				comm_rate = rs.getDouble("m.comm_rate_for_amt");
				ResultSet rs1 = statement.executeQuery("select trn_date,ref_no,trn_type from AccountTransaction where ac_type='"+actype+"' and ac_no="+acno+" and trn_type!='I' and month(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)))=month(curdate()) and year(concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)))=year(curdate())");
				int i=0;
				String cur_date=null;
				String cur_trn_type=null;	        
				int cur_ref_no=0;
				String next_date=null;
				String next_trn_type=null;
				int next_ref_no=0;
				while(rs1.next())
				{
					cur_date=rs1.getString("trn_date");
					cur_ref_no=rs1.getInt("ref_no");
					cur_trn_type=rs1.getString("trn_type");
					if(rs1.next())
					{
						next_date=rs1.getString("trn_date");
						next_ref_no=rs1.getInt("ref_no");
						next_trn_type=rs1.getString("trn_type");
						if(next_ref_no!=0 && cur_ref_no!=0)
						{
							if( (next_date.equals(cur_date) && next_ref_no==cur_ref_no) && !next_trn_type.equals(cur_trn_type) )
								i++;
							else
							{
								rs1.previous();
								i++;
							}
						}
						else
							i++;	            	                
					}
					i++;
				}
				if(i>trns_permonth)
				{
					System.out.println("*******************Inside i>trn_permonth*****************");
					
					if(type==0 || type==2)					//For StoreAccountTransaction
					{
						ResultSet rs_ac=null;
						rs_ac=statement.executeQuery("select last_tr_seq from AccountMaster where ac_no="+acno+" and ac_type='"+actype+"'");
						if(rs_ac.next())
							seq=rs_ac.getInt(1)+1;
						stat.executeUpdate("update AccountMaster set last_tr_seq="+seq+",last_tr_date='"+rs.getString("at.trn_date")+"' where ac_type='"+actype+"' and ac_no="+acno+" ");
						
						ps=connection.prepareStatement("insert into AccountTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						
						ps.setString(1,actype);
						ps.setInt(2,acno);
						ps.setString(3,rs.getString("at.trn_date"));
						ps.setString(4,"P");
						ps.setInt(5,seq);
						ps.setDouble(6,comm_rate);
						ps.setString(7,"T");
						ps.setString(8,rs.getString("at.trn_source"));
						ps.setString(9,"D");
						ps.setInt(10,rs.getInt("at.chq_dd_no"));
						ps.setString(11,rs.getString("at.chq_dd_date"));
						ps.setString(12,"Finefor TrnSeq "+rs.getInt("trn_seq"));
						ps.setInt(13,rs.getInt("at.ref_no"));
						ps.setString(14,null);
						ps.setDouble(15,rs.getDouble("at.cl_bal")-comm_rate);
						ps.setInt(16,0);
						ps.setString(17,rs.getString("at.de_tml"));
						ps.setString(18,rs.getString("at.de_user"));
						ps.setString(19,rs.getString("at.de_date"));
						ps.setString(20,rs.getString("at.ve_tml"));
						ps.setString(21,rs.getString("at.ve_user"));
						ps.setString(22,rs.getString("at.ve_date"));
					}
					else if(type==1 || type==2)			//For StoreGLTransaction
					{
						ResultSet rs2 = statement.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='"+actype+"' and code=5");
						ps=connection.prepareStatement("insert into GLTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						rs2.next();
						ps.setString(1,rs.getString("at.trn_date"));
						ps.setString(2,rs2.getString("gl_type"));
						ps.setInt(3,rs2.getInt("gl_code"));
						ps.setString(4,"");
						ps.setDouble(5,comm_rate);
						ps.setString(6,"C");
						ps.setString(7,actype);
						ps.setInt(8,acno);
						ps.setInt(9,rs.getInt("at.trn_seq"));
						ps.setString(10,"");		            	
						ps.setInt(11,rs.getInt("at.ref_no"));
						ps.setString(12,rs.getString("at.ve_tml"));
						ps.setString(13,rs.getString("at.ve_user"));
						ps.setString(14,rs.getString("at.ve_date"));
					}
					if(ps.executeUpdate()==1)
						return_value = 1;
					else
						return_value = 0;
				}
				else
					return_value = 1; 
			}
			else
				return_value = 0; 	        
		}catch (SQLException e){
			e.printStackTrace();
			ctx.setRollbackOnly();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally 
		{
			if(connection != null)
				connection.close();            
		} 
		return return_value;
	}
	
	
	/// when account no and acc type is passed the corresponding name can be retrieved 
	public String getAccountHolderName(String acc_type,int acc_no) throws SQLException
	{
		Connection conn = null;
		String name=null;
		
		try{
			
			conn = getConnection();
			Statement s22=conn.createStatement();
			Statement s23=conn.createStatement();
			
			if(acc_type.startsWith("1003")||acc_type.startsWith("1004")||acc_type.startsWith("1005"))
				
			{
				System.out.println(" inside deposit master");
				String qry22="select cid from DepositMaster where ac_type='"+acc_type+"' and ac_no="+acc_no+"";
				ResultSet rs22=s22.executeQuery(qry22);
				
				
				if(rs22.next())
				{
					String qry23="select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name from CustomerMaster where cid="+rs22.getInt("cid")+"";
					ResultSet rs23=s23.executeQuery(qry23);
					if(rs23.next())
						name=rs23.getString("name");
					else
						name=rs23.getString("");
					name=rs23.getString("name");	
					
				}
				
			}	
			
			if(acc_type.startsWith("1002")|acc_type.startsWith("1007")||acc_type.startsWith("1018"))
			{
				System.out.println(" inside Account Master");
				String qry22="select cid from AccountMaster where ac_type='"+acc_type+"' and ac_no="+acc_no+"";
				ResultSet rs22=s22.executeQuery(qry22);
				
				if(rs22.next())
				{
					String qry23="select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name from CustomerMaster where cid="+rs22.getInt("cid")+"";
					ResultSet rs23=s23.executeQuery(qry23);
					if(rs23.next())
						name=rs23.getString("name");
					else
						name=rs23.getString("");
					name=rs23.getString("name");
				}
				
			}	
			
			if(acc_type.startsWith("1001"))
			{
				System.out.println(" inside Share Master");
				String qry22="select cid from ShareMaster where ac_type='"+acc_type+"' and ac_no="+acc_no+"";
				ResultSet rs22=s22.executeQuery(qry22);
				
				System.out.println("acc type"+acc_type);
				System.out.println("acc no"+acc_no);
				if(rs22.next())
				{
					String qry23="select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name from CustomerMaster where cid="+rs22.getInt("cid")+"";
					ResultSet rs23=s23.executeQuery(qry23);
					if(rs23.next())
						name=rs23.getString("name");
					else
						name=rs23.getString("");
					name=rs23.getString("name");
					
				}
				System.out.println("name88888"+name);				
			}
			
			if(acc_type.startsWith("1014")||acc_type.startsWith("1015"))
			{
				System.out.println(" inside ODCC Master");
				String qry22="select cid from ODCCMaster where ac_type='"+acc_type+"' and ac_no="+acc_no+"";
				ResultSet rs22=s22.executeQuery(qry22);
				
				if(rs22.next())
				{
					String qry23="select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name from CustomerMaster where cid="+rs22.getInt("cid")+"";
					ResultSet rs23=s23.executeQuery(qry23);
					if(rs23.next())
						name=rs23.getString("name");
					else
						name=rs23.getString("");
					name=rs23.getString("name");
				}
				
			}	
			
			if(acc_type.startsWith("1009"))
			{
				System.out.println(" inside Locker Master");
				String qry22="select cid from LockerMaster where ac_type='"+acc_type+"' and ac_no="+acc_no+"";
				ResultSet rs22=s22.executeQuery(qry22);
				
				if(rs22.next())
				{
					String qry23="select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name from CustomerMaster where cid="+rs22.getInt("cid")+"";
					ResultSet rs23=s23.executeQuery(qry23);
					if(rs23.next())
						name=rs23.getString("name");
					else
						name=rs23.getString("");
					name=rs23.getString("name");
				}
				
			}	
			
			if(acc_type.startsWith("1013"))
			{
				System.out.println(" inside Agent Master");
				String qry22="select cid from AgentMaster where ac_type='"+acc_type+"' and ac_no="+acc_no+"";
				ResultSet rs22=s22.executeQuery(qry22);
				
				if(rs22.next())
				{
					String qry23="select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name from CustomerMaster where cid="+rs22.getInt("cid")+"";
					ResultSet rs23=s23.executeQuery(qry23);
					if(rs23.next())
						name=rs23.getString("name");
					else
						name=rs23.getString("");
					name=rs23.getString("name");
				}
				
			}	
			
			if(acc_type.startsWith("1006"))
			{
				System.out.println(" inside Pygmy Master");
				String qry22="select cid from PygmyMaster where ac_type='"+acc_type+"' and ac_no="+acc_no+"";
				ResultSet rs22=s22.executeQuery(qry22);
				
				if(rs22.next())
				{
					String qry23="select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name from CustomerMaster where cid="+rs22.getInt("cid")+"";
					ResultSet rs23=s23.executeQuery(qry23);
					if(rs23.next())
						name=rs23.getString("name");
					else
						name=rs23.getString("");
					name=rs23.getString("name");
				}
				
			}	         
			
			if(acc_type.startsWith("1008")||acc_type.startsWith("1010"))
			{
				System.out.println(" inside Loan Master");
				String qry22="select cid from LoanMaster where ac_type='"+acc_type+"' and ac_no="+acc_no+"";
				ResultSet rs22=s22.executeQuery(qry22);
				
				if(rs22.next())
				{
					String qry23="select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name from CustomerMaster where cid="+rs22.getInt("cid")+"";
					ResultSet rs23=s23.executeQuery(qry23);
					if(rs23.next())
						name=rs23.getString("name");
					else
						name=rs23.getString("");
					name=rs23.getString("name");
				}
				
			}	
			
			/*if(acc_type.startsWith("1002")||acc_type.startsWith("1007"))
			{
				System.out.println(" inside Company Master");
				String qry22="select cid from CompanyMaster where ac_type='"+acc_type+"' and ac_no="+acc_no+"";
				ResultSet rs22=s22.executeQuery(qry22);
				
				if(rs22.next())
				{
					String qry23="select concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,'')) as name from CustomerMaster where cid="+rs22.getInt("cid")+"";
					ResultSet rs23=s23.executeQuery(qry23);
					if(rs23.next())
						name=rs23.getString("name");
					else
						name=rs23.getString("");
					name=rs23.getString("name");
				}
				
			}*/	
			
		}catch (SQLException e) {
			e.printStackTrace();
			ctx.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
		finally {
			if(conn != null)
				conn.close();            
		}
		return name; 
	}
	
	//ship......06/09/2006
    
    /**
     * type : 0 - Cash Transaction, 1 - Non-Cash Transaction for the same date.
     *        2 - to check the cash_close indicator for previous dates.
     */
	public int checkDailyStatus(String date,int type)
	{
		Connection conn = null;
		Statement stmt_ds = null;
		ResultSet rs_ds = null;
		
		try
		{
			conn = getConnection();	
			
			stmt_ds = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			System.out.println("inside checkDailystatus .... date = "+date);
            
            //ship......14/12/2006
            if(type==0 || type==1)//same date
            {
                rs_ds = stmt_ds.executeQuery("select * from DailyStatus where trn_dt='"+Validations.convertYMD(date)+"'");
                
                if(rs_ds.next())
                {
                    if(rs_ds.getString("day_close").equals("T"))
                        return 3;
                    else
                    {
                        if(type==0)//for Cash Transactions
                        {
                            if(rs_ds.getString("cash_close").equals("T"))
                                return 1;
                        }
                        else if(type==1)//for Non-Cash Transactions
                        {
                            if(rs_ds.getString("post_ind").equals("T"))
                                return 2;
                        }
                    }  
                }
                else
                    return -1;
            }
            else if(type==2)//previous dates
            {
                rs_ds = stmt_ds.executeQuery("select * from DailyStatus where trn_dt<'"+Validations.convertYMD(date)+"'");
                
                if(rs_ds.last())
                {
                    rs_ds.beforeFirst();
                }
                else
                {
                    return -1;
                }
                
                while(rs_ds.next())
                {
                    if(rs_ds.getString("cash_close").equals("F"))
                        return 1;
                }
            }
            ///////////////
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
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
		
		return 0;
	}
	/////////////
	
	//swapna.......06/09/2006
	public boolean updatePostInd(String br_location,String date)
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs_br_name=null;
		int br_code=0;
		
		try
		{
			conn=getConnection(); 
			stmt=conn.createStatement();
			
			rs_br_name=stmt.executeQuery("select br_code from BranchMaster where br_name like '"+br_location+"' ");
			if(rs_br_name.next())
				br_code=rs_br_name.getInt("br_code");
			else
				return false;
			
			stmt.executeUpdate("update DailyStatus set post_ind='F',day_close='F'  where trn_dt='"+Validations.convertYMD(date)+"'");
			stmt.executeUpdate("update DailyConStatus set post_ind='F' where trn_dt='"+Validations.convertYMD(date)+"' and br_code="+br_code+"");
			stmt.executeUpdate("update MonthlyConStatus set post_ind='F' where yr_mth="+convertToYYYYMM(date)+" and br_code="+br_code+"");
			stmt.executeUpdate("update MonthlyStatus set post_ind='F' ,month_close='F' where yr_mth="+convertToYYYYMM(date)+"");
			stmt.executeUpdate("update YearlyStatus set post_ind='F' ,year_close='F' where yr_mth="+convertToYYYYMM(date)+" and br_code="+br_code+"");
		}
		catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
			return false;
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
		
		return true;
	}
	//////////////
	
	//Murugesh......
	public int convertToYYYYMM(String date)
	{
		String month=null,year=null;
		
		if(date!=null)
		{
			StringTokenizer st= new StringTokenizer(date,"/");
			st.nextToken();//day
			month=st.nextToken();
			year=st.nextToken();
			return Integer.parseInt(year+month);
		}
		else
			return 0;
	}
	////////////
	/**
	 * Shubha
	 * gets glkeyparam records for particular account type
	 */
	public ModuleAdminObject getGlkeyParamForAcctype(String ac_type,String date)throws RemoteException
	{
		Connection conn=null;
		ResultSet rs_glparam=null,rs_commrate;
		Statement stmt_glparam=null,stmt_trntype=null,stmt_commrate;
		
		ModuleAdminObject module_values=null;
		ResultSet rs=null,rs_trntype=null;
		Vector trn_type=new Vector(),trn_desc=new Vector();
		
		try
		{
			System.out.println("Inside getGLkerParam*****************************************");
			
			conn=getConnection();
			stmt_glparam=conn.createStatement();
			stmt_commrate=conn.createStatement();
			stmt_trntype=conn.createStatement();
			
			module_values = new ModuleAdminObject();
			
			rs = stmt_glparam.executeQuery("select * from Modules where modulecode='"+ac_type+"'");
			
			if(rs.next())
			{				
				module_values.setModuleCode(rs.getString("modulecode"));
				System.out.println("modulecode==="+module_values.getModuleCode());
				module_values.setModuleAbbr(rs.getString("moduleabbr"));
				module_values.setModuleDesc(rs.getString("moduledesc"));
				
				//Pygmy Deposit or Pygmy Agent
				if(ac_type.startsWith("1006") || ac_type.startsWith("1013"))
				{
					if(ac_type.startsWith("1013"))
					{
						rs_commrate=stmt_commrate.executeQuery("select * from CommissionRate where agt_type="+ac_type+" and date_fr<'"+Validations.convertYMD(date)+"' and date_to>'"+Validations.convertYMD(date)+"' ");
						
						if(rs_commrate.next())
						{
							module_values.pygmy_obj.setCommisionRate(rs_commrate.getDouble("comm_rate"));
							module_values.pygmy_obj.setSecDepositRate(rs_commrate.getDouble("sec_dep_rate"));
						}
					}
					
					module_values.pygmy_obj.setPartWithdraw(rs.getString("other_prop"));
					module_values.pygmy_obj.setPenaltyRate(rs.getInt("std_inst"));		
					module_values.pygmy_obj.setMaxAmount(rs.getDouble("max_amt"));
					module_values.pygmy_obj.setMinAmount(rs.getDouble("min_bal"));
					module_values.pygmy_obj.setMinPeriod(rs.getInt("min_period"));
					module_values.pygmy_obj.setNoJtHolders(rs.getInt("lst_voucher_scroll_no"));
				}
				//ship.......21/11/2006
				//Lockers
				else if(ac_type.startsWith("1009"))
				{
					module_values.locker_obj.setLkAccNo(rs.getInt("lst_acc_no"));
					module_values.locker_obj.setMaxRenewalDays(rs.getInt("max_renewal_count"));
					module_values.locker_obj.setMinPeriod(rs.getInt("min_period"));
					module_values.locker_obj.setDepositMandatory(rs.getString("other_prop"));
					module_values.locker_obj.setMaxPeriod(rs.getInt("max_renewal_days"));
					module_values.locker_obj.setMaxNoOfJtHldrs(rs.getInt("lst_voucher_scroll_no"));
				}
				//////////
				
				
				
				//Common to all Modules
				rs_trntype = stmt_trntype.executeQuery("select * from GLTransactionType where ac_type="+ac_type+"");
				
				while(rs_trntype.next())
				{
					trn_type.add(rs_trntype.getString("trn_type"));
					System.out.println("trn type element===="+trn_type.elementAt(0));
					trn_desc.add(rs_trntype.getString("trn_desc"));
					System.out.println("trn desc element==="+trn_desc.elementAt(0));
				}
				
				module_values.GLtrntypes.setTrnType(trn_type);
				module_values.GLtrntypes.setTrnDesc(trn_desc);
				
				int key_param_rows=0;
				
				rs_glparam=stmt_glparam.executeQuery("select gp.*,mo.moduleabbr from GLKeyParam gp,Modules mo where ac_type='"+ac_type+"' and gp.gl_type=mo.modulecode");
				
				if(rs_glparam.last())
				{
					key_param_rows = rs_glparam.getRow();
					rs_glparam.beforeFirst();
				}
				
				if(key_param_rows>0)
					module_values.GLkeyparam=module_values.getGLKeyParaInstance(key_param_rows);
				
				int k=0;
				
				while(rs_glparam.next() && key_param_rows>0)
				{
					module_values.GLkeyparam[k]=module_values.new GLKeyParamObject();
					module_values.GLkeyparam[k].setAccType(rs_glparam.getString("ac_type"));
					System.out.println("glparam accccccctypeeeeee==="+module_values.GLkeyparam[k].getAccType());
					module_values.GLkeyparam[k].setGLType(rs_glparam.getString("moduleabbr"));
					module_values.GLkeyparam[k].setKeyDesc(rs_glparam.getString("key_desc"));
					module_values.GLkeyparam[k].setCode(rs_glparam.getInt("code"));
					module_values.GLkeyparam[k].setGLCode(rs_glparam.getInt("gl_code"));
					k++;
				}
				
				int post_rows=0;
				
				rs_glparam=stmt_glparam.executeQuery("select * from GLPost where ac_type='"+ac_type+"'");
				
				if(rs_glparam.last())
				{
					post_rows = rs_glparam.getRow();
					rs_glparam.beforeFirst();
				}
				
				if(post_rows>0)
					module_values.GLpost = module_values.getGLPostInstance(post_rows);
				
				k=0;
				
				while(rs_glparam.next() && post_rows>0)
				{
					module_values.GLpost[k]=module_values.new GLPostObject();
					module_values.GLpost[k].setAccType(rs_glparam.getString("ac_type"));
					module_values.GLpost[k].setGLType(rs_glparam.getString("gl_type"));
					System.out.println("glpost gltyppppe=="+module_values.GLpost[k].getGLType());
					module_values.GLpost[k].setCDInd(rs_glparam.getString("cr_dr"));
					module_values.GLpost[k].setTrnType(rs_glparam.getString("trn_type"));
					module_values.GLpost[k].setGLCode(rs_glparam.getInt("gl_code"));
					module_values.GLpost[k].setMultiplyBy(rs_glparam.getInt("mult_by"));
					k++;
				}
			}
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
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
		
		System.out.println("Vluessssssssssssss="+module_values.GLkeyparam.length);
		
		
		return module_values;
	}
	
	public String[] getBankAddress(int br_code)
	{
		Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		String[] arr=null;
		System.out.println("Branch Code: "+br_code);
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select h.bankname,bm.br_name,bm.address from Head h,BranchMaster bm where h.bankcode=bm.br_code and bm.br_code="+br_code);
			rs.last();
			if(rs.getRow()>0)
			{
				rs.beforeFirst();
				rs.next();
				arr=new String[3];
				for(int i=0;i<3;i++){
					arr[i]=new String();
					arr[i]=rs.getString(i+1);
					System.out.println("arr[i]==>"+arr[i]);
				}
			}
			return arr;
		}catch(SQLException se){se.printStackTrace();return null;}
		finally{try{rs.close();conn.close();}catch(SQLException se){se.printStackTrace();}}
		
	}
	
	public int moduleAdmin(ModuleAdminObject mod_admin,String date)
	{
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		PreparedStatement[] pst=null,pstmt1=null;
		Statement stmt=null,stmt_pygrate=null;
		int modulecode=0,count=0;
		
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			
			stmt_pygrate=conn.createStatement();
			
			rs = stmt.executeQuery("select x.* from Modules x,Modules y where x.modulecode between y.modulecode+1 and y.modulecode+999  and y.modulecode in ("+mod_admin.getModuleCode()+") order by modulecode desc limit 1");
			
			if(rs.next())
			{
				modulecode=rs.getInt("modulecode");
			}
			
			//Pygmy Deposit
			if(mod_admin.getModuleCode().startsWith("1006"))
			{
				pstmt=conn.prepareStatement("insert into Modules(modulecode,moduledesc,moduleabbr,min_period,std_inst,other_prop,max_amt,min_bal,lst_voucher_scroll_no) values (?,?,?,?,?,?,?,?,?)");
				
				pstmt.setInt(1,modulecode+1);
				pstmt.setString(2,mod_admin.getModuleDesc());
				pstmt.setString(3,mod_admin.getModuleAbbr());
				pstmt.setInt(4,mod_admin.pygmy_obj.getMinPeriod());
				pstmt.setInt(5,mod_admin.pygmy_obj.getPenaltyRate());
				pstmt.setString(6,mod_admin.pygmy_obj.getPartWithdraw());
				pstmt.setDouble(7,mod_admin.pygmy_obj.getMaxAmount());
				pstmt.setDouble(8,mod_admin.pygmy_obj.getMinAmount());
				pstmt.setInt(9,mod_admin.pygmy_obj.getNoJtHolders());
			}
			//Pygmy Agent
			else if(mod_admin.getModuleCode().startsWith("1013"))
			{
				pstmt=conn.prepareStatement("insert into Modules(modulecode,moduledesc,moduleabbr,min_period,lst_voucher_scroll_no) values (?,?,?,?,?)");
				
				pstmt.setInt(1,modulecode+1);
				pstmt.setString(2,mod_admin.getModuleDesc());
				pstmt.setString(3,mod_admin.getModuleAbbr());
				pstmt.setInt(4,mod_admin.pygmy_obj.getMinPeriod());
				pstmt.setInt(5,mod_admin.pygmy_obj.getNoJtHolders());
			}
			//ship......22/11/2006.....Lockers
			if(mod_admin.getModuleCode().startsWith("1009"))
			{
				pstmt=conn.prepareStatement("insert into Modules(modulecode,moduledesc,moduleabbr,lst_acc_no,max_renewal_count,min_period,other_prop,max_renewal_days,lst_voucher_scroll_no) values (?,?,?,?,?,?,?,?,?)");
				
				pstmt.setInt(1,modulecode+1);
				pstmt.setString(2,mod_admin.getModuleDesc());
				pstmt.setString(3,mod_admin.getModuleAbbr());
				pstmt.setInt(4,mod_admin.locker_obj.getLkAccNo());
				pstmt.setInt(5,mod_admin.locker_obj.getMaxRenewalDays());
				pstmt.setInt(6,mod_admin.locker_obj.getMinPeriod());
				pstmt.setString(7,mod_admin.locker_obj.isDepositMandatory());
				pstmt.setInt(8,mod_admin.locker_obj.getMaxPeriod());
				pstmt.setInt(9,mod_admin.locker_obj.getMaxNoOfJtHldrs());
			}
			//////////
			
			
			//Common to all Modules......insert into GLKeyParam & GLPost
			if(pstmt.executeUpdate()>0)
			{
				if(mod_admin.GLkeyparam!=null && mod_admin.GLkeyparam.length>0)
				{
					pst = new PreparedStatement[mod_admin.GLkeyparam.length];
					
					for(int i=0;i<mod_admin.GLkeyparam.length;i++)
					{
						pst[i]=conn.prepareStatement("insert into GLKeyParam(ac_type,code,key_desc,gl_code,gl_type,de_tml,de_user,de_date) values (?,?,?,?,?,?,?,?)");
						
						pst[i].setInt(1,modulecode+1);
						pst[i].setInt(2,mod_admin.GLkeyparam[i].getCode());
						pst[i].setString(3,mod_admin.GLkeyparam[i].getKeyDesc());
						pst[i].setInt(4,mod_admin.GLkeyparam[i].getGLCode());
						pst[i].setString(5,mod_admin.GLkeyparam[i].getGLType());
						pst[i].setString(6,mod_admin.getUserTml());
						pst[i].setString(7,mod_admin.getUserID());
						pst[i].setString(8,mod_admin.getTranDateTime());
						
						pst[i].executeUpdate();
					}
					
					if(mod_admin.getModuleCode().startsWith("1006"))
					{
						stmt_pygrate.executeUpdate("insert into PygmyRate values( '"+(modulecode+1)+"','"+date+"','99/99/9999',0,999,"+mod_admin.pygmy_obj.getPygmyRate()+",null,null,null)");
					}
					else if(mod_admin.getModuleCode().startsWith("1013"))
					{
						stmt_pygrate.executeUpdate("insert into CommissionRate values( '"+(modulecode+1)+"','"+date+"','99/99/9999',0.00,100000.00,"+mod_admin.pygmy_obj.getCommisionRate()+","+mod_admin.pygmy_obj.getSecDepositRate()+",null,null,null)");
					}
				}
				
				if(mod_admin.GLpost!=null && mod_admin.GLpost.length>0)
				{
					pstmt1 = new PreparedStatement[mod_admin.GLpost.length];
					
					for(int i=0;i<mod_admin.GLpost.length;i++)
					{
						pstmt1[i]=conn.prepareStatement("insert into GLPost(ac_type,gl_type,gl_code,trn_type,cr_dr,mult_by,de_tml,de_user,de_date) values(?,?,?,?,?,?,?,?,?)");
						
						pstmt1[i].setString(1,String.valueOf(modulecode+1));
						pstmt1[i].setString(2,mod_admin.GLpost[i].getGLType());
						pstmt1[i].setInt(3,mod_admin.GLpost[i].getGLCode());
						pstmt1[i].setString(4,mod_admin.GLpost[i].getTrnType());
						pstmt1[i].setString(5,mod_admin.GLpost[i].getCDInd());
						pstmt1[i].setInt(6,mod_admin.GLpost[i].getMultiplyBy());
						pstmt1[i].setString(7,mod_admin.getUserTml());
						pstmt1[i].setString(8,mod_admin.getUserID());
						pstmt1[i].setString(9,mod_admin.getTranDateTime());
						
						pstmt1[i].executeUpdate();
					}
				}
			}
			else 
				count=-1;
		}
		catch(SQLException se){
			se.printStackTrace();
			ctx.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
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
		
		return count;
	}
	
	public int moduleAdminUpdate(ModuleAdminObject mod_admin,String date)
	{
		Connection conn=null;
		PreparedStatement pstmt=null;
		PreparedStatement[] pst=null,pstmt1=null;
		Statement stmt_module=null,stmt_glkeyparam=null,stmt_glpost=null;
		int count=0;
		
		try
		{
			conn=getConnection();
			stmt_module=conn.createStatement();
			stmt_glkeyparam=conn.createStatement();
			stmt_glpost=conn.createStatement();
			
			stmt_module.executeUpdate("delete from Modules where modulecode="+mod_admin.getModuleCode()+" ");
			stmt_glkeyparam.executeUpdate("delete from GLKeyParam where ac_type="+mod_admin.getModuleCode()+" ");
			stmt_glpost.executeUpdate("delete from GLPost where ac_type="+mod_admin.getModuleCode()+" ");
			
			//Pygmy Deposit
			if(mod_admin.getModuleCode().startsWith("1006"))
			{
				pstmt=conn.prepareStatement("insert into Modules(modulecode,moduledesc,moduleabbr,min_period,std_inst,other_prop,max_amt,min_bal,lst_voucher_scroll_no) values (?,?,?,?,?,?,?,?,?)");
				
				pstmt.setString(1,mod_admin.getModuleCode());
				pstmt.setString(2,mod_admin.getModuleDesc());
				pstmt.setString(3,mod_admin.getModuleAbbr());
				pstmt.setInt(4,mod_admin.pygmy_obj.getMinPeriod());
				pstmt.setInt(5,mod_admin.pygmy_obj.getPenaltyRate());
				pstmt.setString(6,mod_admin.pygmy_obj.getPartWithdraw());
				pstmt.setDouble(7,mod_admin.pygmy_obj.getMaxAmount());
				pstmt.setDouble(8,mod_admin.pygmy_obj.getMinAmount());
				pstmt.setInt(9,mod_admin.pygmy_obj.getNoJtHolders());
			}
			//Pygmy Agent
			else if(mod_admin.getModuleCode().startsWith("1013"))
			{
				pstmt=conn.prepareStatement("insert into Modules(modulecode,moduledesc,moduleabbr,min_period,lst_voucher_scroll_no) values (?,?,?,?,?)");
				
				pstmt.setString(1,mod_admin.getModuleCode());
				pstmt.setString(2,mod_admin.getModuleDesc());
				pstmt.setString(3,mod_admin.getModuleAbbr());
				pstmt.setInt(4,mod_admin.pygmy_obj.getMinPeriod());
				pstmt.setInt(5,mod_admin.pygmy_obj.getNoJtHolders());
			}
			//ship......22/11/2006.....Lockers
			if(mod_admin.getModuleCode().startsWith("1009"))
			{
				pstmt=conn.prepareStatement("insert into Modules(modulecode,moduledesc,moduleabbr,lst_acc_no,max_renewal_count,min_period,other_prop,max_renewal_days,lst_voucher_scroll_no) values (?,?,?,?,?,?,?,?,?)");
				
				pstmt.setString(1,mod_admin.getModuleCode());
				pstmt.setString(2,mod_admin.getModuleDesc());
				pstmt.setString(3,mod_admin.getModuleAbbr());
				pstmt.setInt(4,mod_admin.locker_obj.getLkAccNo());
				pstmt.setInt(5,mod_admin.locker_obj.getMaxRenewalDays());
				pstmt.setInt(6,mod_admin.locker_obj.getMinPeriod());
				pstmt.setString(7,mod_admin.locker_obj.isDepositMandatory());
				pstmt.setInt(8,mod_admin.locker_obj.getMaxPeriod());
				pstmt.setInt(9,mod_admin.locker_obj.getMaxNoOfJtHldrs());
			}
			//////////
			
			
			//Common to all Modules........inserting into GLKeyParam & GLPost
			if(pstmt.executeUpdate()>0)
			{
				//ship.....22/11/2006
				if(mod_admin.GLkeyparam!=null && mod_admin.GLkeyparam.length>0)
				{
					pst = new PreparedStatement[mod_admin.GLkeyparam.length];
					
					for(int i=0;i<mod_admin.GLkeyparam.length;i++)
					{
						pst[i]=conn.prepareStatement("insert into GLKeyParam(ac_type,code,key_desc,gl_code,gl_type,de_tml,de_user,de_date) values (?,?,?,?,?,?,?,?)");
						
						pst[i].setString(1,mod_admin.getModuleCode());
						pst[i].setInt(2,mod_admin.GLkeyparam[i].getCode());
						pst[i].setString(3,mod_admin.GLkeyparam[i].getKeyDesc());
						pst[i].setInt(4,mod_admin.GLkeyparam[i].getGLCode());
						pst[i].setString(5,mod_admin.GLkeyparam[i].getGLType());
						pst[i].setString(6,mod_admin.getUserTml());
						pst[i].setString(7,mod_admin.getUserID());
						pst[i].setString(8,mod_admin.getTranDateTime());
						
						pst[i].executeUpdate();
					}
				}
				
				if(mod_admin.GLpost!=null && mod_admin.GLpost.length>0)
				{
					pstmt1 = new PreparedStatement[mod_admin.GLpost.length];
					
					for(int i=0;i<mod_admin.GLpost.length;i++)
					{
						pstmt1[i]=conn.prepareStatement("insert into GLPost(ac_type,gl_type,gl_code,trn_type,cr_dr,mult_by,de_tml,de_user,de_date) values(?,?,?,?,?,?,?,?,?)");
						
						pstmt1[i].setString(1,mod_admin.getModuleCode());
						pstmt1[i].setString(2,mod_admin.GLpost[i].getGLType());
						pstmt1[i].setInt(3,mod_admin.GLpost[i].getGLCode());
						pstmt1[i].setString(4,mod_admin.GLpost[i].getTrnType());
						pstmt1[i].setString(5,mod_admin.GLpost[i].getCDInd());
						pstmt1[i].setInt(6,mod_admin.GLpost[i].getMultiplyBy());
						pstmt1[i].setString(7,mod_admin.getUserTml());
						pstmt1[i].setString(8,mod_admin.getUserID());
						pstmt1[i].setString(9,mod_admin.getTranDateTime());
						
						pstmt1[i].executeUpdate();
					} 
				}
			}
			else 
				count=-1;
		}
		catch(SQLException se){
			se.printStackTrace();
			ctx.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
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
		
		return count;
	}
	
	public String getCustomerName(String custid)
	{
		Connection conn=null;
		String name=null;
		ResultSet rs_name=null;
		Statement stmt_name=null;
		try
		{
			conn=getConnection();
			stmt_name=conn.createStatement();
			rs_name=stmt_name.executeQuery("select * from CustomerMaster where cid="+custid+" and ve_tml is not null");
			if(rs_name.next())
			{
				name=rs_name.getString("fname");
			}
		}
		catch(SQLException se){se.printStackTrace();}
		finally
		{
			try{ conn.close();}
			catch(Exception ex){ex.printStackTrace();}
		}
		return name;
	}
	
	public String getSelfAccountNoForCid(int cid,String ac_type)
	{
		Connection conn=null;
		ResultSet rs_acno=null;
		Statement stmt_acno=null;
		String account_no=null;
		try
		{
			conn=getConnection();
			stmt_acno=conn.createStatement();
			rs_acno=stmt_acno.executeQuery("select * from AccountMaster where cid="+cid+" and ac_type='"+ac_type+"' and no_jt_hldr=0 ");
			if(rs_acno.next())
			{
				account_no=rs_acno.getString("ac_no");
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			try{ conn.close(); }
			catch(Exception ex){ex.printStackTrace();}
		}
		return account_no;   
	}
	
	
	
	public int getPassword(String uid,String tml,String pwd){
		int value=0;
		Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			rs = stmt.executeQuery("select * from Users where uid='"+uid+"' and pwd=MD5('"+pwd+"') and tml like '"+tml+"'");
			if(rs.next()){
				value=1;
			}
		}
		catch(Exception e){e.printStackTrace();}
		finally
		{
			try{ conn.close(); }
			catch(Exception ex){ex.printStackTrace();}
		}
		return value;
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
			rs = stmt.executeQuery("select * from Relations ");
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
	public String[][] getDirectorDetails(String date){
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
	public String getDirectorName(int director_code,String date){
		Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		String str=null;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			date=Validations.convertYMD(date);
			rs = stmt.executeQuery("select director_code,d.cid,concat(IFNULL(fname,''),IFNULL(mname,''),IFNULL(lname,''))as name ,from_date,to_date from DirectorMaster d,CustomerMaster cm   where d.cid=cm.cid  and  director_code="+director_code+" and '"+date+"' between concat(right(from_date,4),'-',mid(from_date,locate('/',from_date)+1, (locate('/',from_date,4)-locate('/',from_date)-1)),'-',left(from_date,locate('/',from_date)-1)) and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1, (locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1)) ");
			if(rs.next()){
				str=rs.getString("name");
			}
			else
				str="Director Code Not Found";
		}
		catch(Exception e){e.printStackTrace();}
		finally
		{
			try{ conn.close(); }
			catch(Exception ex){ex.printStackTrace();}
		}
		return str;
	}
	
	public UserActivityMasterObject[] getUserActivity(String tml_no,String uid,String from_date, String to_date,String ip_address,String queryNum)throws UserNotExistException{
		Connection con=null;
		Statement stmt;
		ResultSet rs;
		UserActivityMasterObject userActive[]=null;
		String query="";
		System.out.println("query=="+queryNum);
		if(queryNum.equals("1000")){
			query="select * from UserActivity where activity_date >='"+from_date+"' and activity_date <='"+to_date+"'";
		}
		else if(queryNum.equals("0100")){
			query="select * from UserActivity where ip_address='"+ip_address+"'";
		}
		else if(queryNum.equals("0010")){
			query="select * from UserActivity where user_id='"+uid+"'";
		}
		else if(queryNum.equals("0001")){
			query="select * from UserActivity where tml_no='"+tml_no+"'";
		}
		else if(queryNum.equals("1100")){
			query="select * from UserActivity where activity_date >='"+from_date+"' and activity_date <='"+to_date+"' and ip_address='"+ip_address+"'";
		}
		else if(queryNum.equals("1010")){
			query="select * from UserActivity where activity_date >='"+from_date+"' and activity_date <='"+to_date+"' and user_id='"+uid+"'";
		}
		else if(queryNum.equals("1001")){
			query="select * from UserActivity where activity_date >='"+from_date+"' and activity_date <='"+to_date+"' and tml_no='"+tml_no+"'";
		}
		else if(queryNum.equals("0110")){
			query="select * from UserActivity where ip_address='"+ip_address+"' and user_id='"+uid+"'";
		}
		else if(queryNum.equals("0101")){
			query="select * from UserActivity where ip_address='"+ip_address+"' and tml_no='"+tml_no+"'";
		}
		else if(queryNum.equals("1011")){
			query="select * from UserActivity where activity_date >='"+from_date+"' and activity_date <='"+to_date+"' and user_id='"+uid+"' and tml_no='"+tml_no+"'";
		}
		else if(queryNum.equals("0011")){
			query="select * from UserActivity where user_id='"+uid+"' and tml_no='"+tml_no+"'";
		}
		else if(queryNum.equals("0111")){
			query="select * from UserActivity where user_id='"+uid+"' and tml_no='"+tml_no+"' and ip_address='"+ip_address+"'";
		}
		else if(queryNum.equals("1110")){
			query="select * from UserActivity where activity_date >='"+from_date+"' and activity_date <='"+to_date+"' and user_id='"+uid+"' and ip_address='"+ip_address+"'";
		}
		else if(queryNum.equals("1111")){
			query="select * from UserActivity where activity_date >='"+from_date+"' and activity_date <='"+to_date+"' and user_id='"+uid+"' and tml_no='"+tml_no+"' and ip_address='"+ ip_address+"'";
		}
		
		try{
				con=getConnection();
				stmt=con.createStatement();
				if(!query.equals("")){
				rs=stmt.executeQuery(query);
				if(rs!=null){
					rs.last();
					int size=rs.getRow();
					rs.beforeFirst();
					int i=0;
					userActive=new UserActivityMasterObject[size];
					while(rs.next()){
						userActive[i]=new UserActivityMasterObject();
						userActive[i].setUser_id(rs.getString(1));
						userActive[i].setTml_no(rs.getString(2));
						userActive[i].setIp_address(rs.getString(3));
						userActive[i].setPage_visit(rs.getString(4));
						userActive[i].setActivity(rs.getString(5));
						userActive[i].setAc_type(rs.getString(6));
						userActive[i].setAc_no(rs.getInt(7));
						userActive[i].setCid(rs.getInt(8));
						userActive[i].setActivity_date(rs.getString(9));
						userActive[i].setActivity_time(rs.getString(10));
						i++;
					}
					
				}
				}
				
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		
			finally
			{
				try{ con.close(); }
				catch(Exception ex){ex.printStackTrace();}
			}
		
		return userActive;
	}	
	
public boolean getVerifiedCustomer(int custid) throws RecordsNotFoundException{
		
		Connection conn=null;
		String name=null;
		ResultSet rs_name=null;
		Statement stmt_name=null;
		boolean verify = false;
		try
		{
			conn=getConnection();
			stmt_name=conn.createStatement();
			System.out.println("so confirm it has been called");
			rs_name=stmt_name.executeQuery("select * from CustomerMaster where cid="+custid+" ");
			if(rs_name.next())
			{
				System.out.println(rs_name.getString("ve_tml")+"............................................");
				if(rs_name.getString("ve_tml")!= null )
					verify = true;
			
				System.out.println("..............."+verify);
				
			}
			else
				throw new RecordsNotFoundException();
		}
		catch(RecordsNotFoundException rec){
			
			throw rec;
		}
		catch(SQLException se){se.printStackTrace();}
		finally
		{
			try{ conn.close();}
			catch(SQLException ex){ex.printStackTrace();}
		}
		
		return verify; 
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
	/*public double getAmountForACOpen(String ac_type)
	{
		Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		String str[]=null;
		int i=0;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			rs = stmt.executeQuery("select * from DirectorRelation");
			
	}*/
}
