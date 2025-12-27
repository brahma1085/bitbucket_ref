package administratorServer;

import java.net.ConnectException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import exceptions.*;
import general.Validations;

import masterObject.administrator.AdministratorObject;
import masterObject.administrator.MenuObject;
import masterObject.administrator.TerminalObject;
import masterObject.administrator.TreeObject;
import masterObject.general.BranchObject;
import masterObject.general.ModuleAdminObject;
import masterObject.general.ModuleObject;

public class AdministratorBean implements javax.ejb.SessionBean
{
	DataSource ds=null;
	String uid=null;
	
	SessionContext ctx;
	public AdministratorBean()
	{
		
	}
	
	public void ejbCreate()
	{
		//this.uid=uid;
		
		try{	
			Context ctx=new InitialContext();			
			ds=(javax.sql.DataSource)ctx.lookup("java:MySqlDS");
		
			}catch(Exception ex){ex.printStackTrace();}
		
	}
	
	public void setUserId(String uid)
	{
		this.uid=uid;
	}
	
	public Connection getConnection() 
	 {
	 	try{			
	 		return ds.getConnection("root","");
	 	}catch(Exception e)	{e.printStackTrace();}
	 	return null;
	 }
	public Connection getConn() {
		Connection con=null;
		
		try{
			
			con= ds.getConnection("root","");
		}
		catch(Exception ex){ex.printStackTrace();}
		try{
			if(con!=null)
		        con.close();
		}
		catch(Exception ex1){ex1.printStackTrace();
		}
		
		
		return con;
	}
	public boolean verifyUser(String txt_user_id,String pwd,String txt_terminal_name) throws RemoteException
	{
		Connection conn=null;
		
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String qry1="Select * from Users where uid='"+txt_user_id+"' and pwd=MD5('"+pwd+"')and tml='"+txt_terminal_name+"'" ;
			ResultSet rs1=s1.executeQuery(qry1);
			if(rs1.next())
				return true;
			
			return false;
			
						
		}catch(Exception e1){e1.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return false;
		
		
	}
	public int changePassword(String string_name,String string_old_password,String string_new_password) throws RemoteException
	{
		System.out.println(string_name);
		System.out.println(string_old_password);
		System.out.println(string_new_password);
		
		Connection conn=null;
		int result=0;
		
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement();
			String qry1="Update Users set pwd=MD5('"+string_new_password+"') where uid='"+string_name+"' and pwd=MD5('"+string_old_password+"')  ";
			if(s1.executeUpdate(qry1)!=0)
			{
				result=1;		
			}
			
			
		}catch(Exception e1){e1.printStackTrace();}
		finally
		{
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return result ;
	}
	//code added by amzad
	public Vector usrAccessRights(String string_name) throws RemoteException
	{
		System.out.println(string_name);
		Vector v=new Vector();
		ResultSet res_access=null;
		Connection conn=null;
		int result=0;
		int x;
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement();
			String vsql2="select * from  UserMaster ul, User_Access_Rights ua where ul.user_id=ua.userid and ul.user_id='"+string_name.trim()+"'";
			Statement stm=conn.createStatement();
			res_access=stm.executeQuery(vsql2);
			if(res_access!=null){
				if(res_access.next()){
	  				 System.out.println("res values---"+res_access.getInt("mod1"));
	  				System.out.println("res values---"+res_access.getInt("mod2"));
	  				 
	  				System.out.println("---res_access---");
	  				//for(int z=0;z<13;z++){
	  				v.clear();
	  					x=res_access.getInt("mod1");
	  					v.add(new Integer(x));
	  					
	  					x=res_access.getInt("mod2");
	  					v.add(new Integer(x));
	  					x=res_access.getInt("mod3");
	  					v.add(new Integer(x));
	  					x=res_access.getInt("mod4");
	  					v.add(new Integer(x));
	  					x=res_access.getInt("mod5");
	  					v.add(new Integer(x));
	  					x=res_access.getInt("mod6");
	  					v.add(new Integer(x));
	  					x=res_access.getInt("mod7");
	  					v.add(new Integer(x));
	  					x=res_access.getInt("mod8");
	  					v.add(new Integer(x));
	  					x=res_access.getInt("mod9");
	  					v.add(new Integer(x));
	  					x=res_access.getInt("mod10");
	  					v.add(new Integer(x));
	  					x=res_access.getInt("mod11");
	  					v.add(new Integer(x));
	  					x=res_access.getInt("mod12");
	  					v.add(new Integer(x));
	  					x=res_access.getInt("mod13");
	  					v.add(new Integer(x));
	  					x=res_access.getInt("mod14");
	  					v.add(new Integer(x));
	  					v.add(new Integer(res_access.getInt("access")));
	  					for(int i=0;i<2;i++){
	  				   System.out.println("==========value of vector==="+v.get(i));
	  				}
	  				
	  				
	  				 
	  			 }
			}
			
		}catch(Exception e1){e1.printStackTrace();}
		finally
		{
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return v ;
	}
	//code added by amzad
	//TO CREATE USERS
	//Table Used Users
	public int createUser(AdministratorObject obj_new_user) throws RemoteException
	{
		Connection conn=null;
		int result=0;
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement();
			String qry1="Select * from Users where uid='"+obj_new_user.getUid()+"' ";
			//Checking whether this uid is already there or not
			ResultSet rs1=s1.executeQuery(qry1);
			if(rs1.next())
			{
				result=2;
			}
			
			Statement s2=conn.createStatement();
			//Checking whether this user No is already there or not
			String qry2="Select * from Users where user_no="+obj_new_user.getUserNo()+"  ";
			ResultSet rs2=s2.executeQuery(qry2);
			if(rs2.next())
			{
				result=3;
			}
			
			else
			{
				//if everything is fine,insert in Users Table
				Statement s3=conn.createStatement();
				String qry3="Insert into Users(user_no,user_type,name,sname,uid,pwd,tml) values("+obj_new_user.getUserNo()+",'"+obj_new_user.getUserType()+"','"+obj_new_user.getFullName()+"','"+obj_new_user.getShortName()+"','"+obj_new_user.getUid()+"',MD5('"+obj_new_user.getPassword()+"'),'"+obj_new_user.getTmlNo()+"') ";                            
				if(s3.executeUpdate(qry3)!=0)
				{
					result=1;
				}
			}	
		}catch(Exception e1){e1.printStackTrace();}
		finally
		{
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return result;
	}
	
	//Getting the Terminal Codes from Terminals table
	
	public AdministratorObject[] getTmlCodes() throws RemoteException
	{
		int int_no_records=0;
		int i=0;
		Connection conn=null;
		
		AdministratorObject[] array_obj_tml=null;
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement();
			String qry1="Select distinct tml_code from TerminalDetail";
			ResultSet rs1=s1.executeQuery(qry1);
			while(rs1.next())
			{
				int_no_records++;
			}
			array_obj_tml=new AdministratorObject[int_no_records];
			System.out.println("No-Records= "+int_no_records);
			
			rs1.beforeFirst();
			
			while(rs1.next())
			{
				array_obj_tml[i]=new AdministratorObject();
				array_obj_tml[i].setTmlNo(rs1.getString("tml_code"));
				
				i++;
			}
			
		}catch(Exception e){e.printStackTrace();}
		finally
		{
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		
		return array_obj_tml;
	}
	
	/*method used for getting the details of users corresponding to the terminals 
	 they r assigned */
	
	public AdministratorObject[] getReqUsers(String string_tml_code) throws RemoteException
	{
		AdministratorObject[] array_obj_user=null;
		int int_no_records=0;
		int i=0;
		Connection conn=null;
	
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement();
			//get the user_no from TermianlLog table for corresponding tml_code
			String qry1="Select distinct user_no from TerminalLog where tml_code='"+string_tml_code+"'  ";
			ResultSet rs1=s1.executeQuery(qry1);
			
			while(rs1.next())
			{
				int_no_records++;
			}
			array_obj_user=new AdministratorObject[int_no_records];
			System.out.println("No-Records= "+int_no_records);
		
			rs1.beforeFirst();
			
			while(rs1.next())
			{
				array_obj_user[i]=new AdministratorObject();
				array_obj_user[i].setUserNo(rs1.getInt("user_no"));
			
				i++;
			}
		
			Statement s2=conn.createStatement();
			for(i=0;i<array_obj_user.length;i++)
			{
				//for that corresponding user_no extract the details
				String qry2="Select name,sname,user_type from Users where user_no="+array_obj_user[i].getUserNo()+" ";
				ResultSet rs2=s2.executeQuery(qry2);
				if(rs2.next())
				{
					array_obj_user[i].setFullName(rs2.getString("name"));
					array_obj_user[i].setShortName(rs2.getString("sname"));
					array_obj_user[i].setUserType(rs2.getString("user_type"));
				}
			}
	
		}catch(Exception e1){e1.printStackTrace();}
		finally
		{
				try {
					conn.close();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
		}
		return array_obj_user;
	}
	//getting list of all users
	
	public AdministratorObject[] getAllUsers() throws RemoteException
	{
		AdministratorObject[] array_obj_user=null;
		int int_no_records=0;
		int i=0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			
			Statement s1=conn.createStatement();
			String qry1="Select user_no,tml_code from TerminalLog order by user_no ";
			ResultSet rs1=s1.executeQuery(qry1);
			
			while(rs1.next())
			{
				int_no_records++;
			}
			array_obj_user=new AdministratorObject[int_no_records];
			System.out.println("No-Records= "+int_no_records);
		
			rs1.beforeFirst();
			
			while(rs1.next())
			{
				array_obj_user[i]=new AdministratorObject();
				array_obj_user[i].setUserNo(rs1.getInt("user_no"));
				array_obj_user[i].setTmlNo(rs1.getString("tml_code"));
			
				i++;
			}
		
			Statement s2=conn.createStatement();
			for(i=0;i<array_obj_user.length;i++)
			{
				String qry2="Select name,sname,user_type from Users where user_no="+array_obj_user[i].getUserNo()+" ";
				ResultSet rs2=s2.executeQuery(qry2);
				if(rs2.next())
				{
					array_obj_user[i].setFullName(rs2.getString("name"));
					array_obj_user[i].setShortName(rs2.getString("sname"));
					array_obj_user[i].setUserType(rs2.getString("user_type"));
				}
			}
	
		}catch(Exception e1){e1.printStackTrace();}
		finally
		{
				try {
					conn.close();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
		}
		return array_obj_user;
		
		
	}
	//Get the user_no from Users table
	public AdministratorObject[] getUserDetails() throws RemoteException
	{
		AdministratorObject[] array_obj_user=null;
		int int_no_records=0;
		int i=0;
		Connection conn=null;
		
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement();
			String qry1="Select distinct user_no,name from Users order by user_no ";
			ResultSet rs1=s1.executeQuery(qry1);
			
			while(rs1.next())
			{
				int_no_records++;
			}
			array_obj_user=new AdministratorObject[int_no_records];
			System.out.println("No-Records= "+int_no_records);
		
			rs1.beforeFirst();
			
			while(rs1.next())
			{
				array_obj_user[i]=new AdministratorObject();
				array_obj_user[i].setUserNo(rs1.getInt("user_no"));
				array_obj_user[i].setFullName(rs1.getString("name"));
				
				i++;
			}
		
		}catch(Exception e1){e1.printStackTrace();}
		finally
		{
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return array_obj_user;
	}
	//get the terminal details from terminals table
	public AdministratorObject[] getTmlDetails() throws RemoteException
	{
		AdministratorObject[] array_obj_tml=null;
		int int_no_records=0;
		int i=0;
		Connection conn=null;
		
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement();
			String qry1="Select tml_code,tml_desc from TerminalDetail ";
			ResultSet rs1=s1.executeQuery(qry1);
			
			while(rs1.next())
			{
				int_no_records++;
			}
			
			array_obj_tml=new AdministratorObject[int_no_records];
			System.out.println("No-Records= "+int_no_records);
		
			rs1.beforeFirst();
			
			while(rs1.next())
			{
				array_obj_tml[i]=new AdministratorObject();
				array_obj_tml[i].setTmlNo(rs1.getString("tml_code"));
				array_obj_tml[i].setTmlDesc(rs1.getString("tml_desc"));
			
				i++;
			}
		
		}catch(Exception e1){e1.printStackTrace();}
		finally
		{
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return array_obj_tml;
	}
	//checking whether a user is assigned any terminals or not
	public AdministratorObject[] getUserAccess(int int_user_no) throws RemoteException
	{
		AdministratorObject[] array_obj_tml=null;
		int int_no_records=0;
		int i=0;
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement();
			String qry1="Select distinct tml_code from TerminalLog where user_no="+int_user_no+"  ";
			ResultSet rs1=s1.executeQuery(qry1);
			while(rs1.next())
			{
				int_no_records++;
			}
			
			array_obj_tml=new AdministratorObject[int_no_records];
			System.out.println("No-Records= "+int_no_records);
		
			rs1.beforeFirst();
			
			while(rs1.next())
			{
				array_obj_tml[i]=new AdministratorObject();
				array_obj_tml[i].setTmlNo(rs1.getString("tml_code"));
				
				i++;
			}
		
		
		}catch(Exception e1){e1.printStackTrace();}
		finally
		{
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return array_obj_tml;	
	}
	public AdministratorObject[] getViewUser(String userid,int type,String from,String to) throws SQLException
	{
	    AdministratorObject[] array_obj_user=null;
	    Connection con=null;
	    ResultSet res=null;
	    try
	    {
	        con=getConnection();
	        Statement stmt=con.createStatement();
	        if(type==1)
	        
	        res=stmt.executeQuery("select * from UserMaster where user_id='"+userid+"'and concat(right(left(de_date,10),4),'-',mid(left(de_date,10),locate('/',left(de_date,10))+1, (locate('/',left(de_date,10),4)-locate('/',left(de_date,10))-1)),'-',left(left(de_date,10),locate('/',left(de_date,10))-1)) between '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' order by cid");
	        
	        else
	            res=stmt.executeQuery("select * from UserMaster where concat(right(left(de_date,10),4),'-',mid(left(de_date,10),locate('/',left(de_date,10))+1, (locate('/',left(de_date,10),4)-locate('/',left(de_date,10))-1)),'-',left(left(de_date,10),locate('/',left(de_date,10))-1)) between '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' order by cid");
	        /*if(res.next())
            {
                res.last();
               
                res.beforeFirst();
            }*/
	        res.last();
	        int row=res.getRow();
	        res.beforeFirst();
	        array_obj_user=new AdministratorObject[row];
	       /* if(res.getRow()>0)
	        {
	            res.beforeFirst();*/
	        int i=0;
	        while(res.next())
	        {
	            System.out.println("inside the server");
	            array_obj_user[i]=new AdministratorObject();
	            array_obj_user[i].setUid(res.getString("user_id"));
	            array_obj_user[i].setCust_id(res.getInt("cid"));
	            array_obj_user[i].setShortName(res.getString("short_name"));
	            array_obj_user[i].setPass_expiry_period(res.getInt("pwd_expiry_period"));
	            array_obj_user[i].setPass_expiry_date(res.getString("pwd_expiry_date"));
	            array_obj_user[i].setThump_ipm(res.getString("thump_ipm"));
	            array_obj_user[i].setAcc_operation_fromDate(res.getString("operative_from_date"));
	            array_obj_user[i].setAcc_oper_toDate(res.getString("operative_to_date"));
	            array_obj_user[i].setDisable(res.getBoolean("disable"));
	            array_obj_user[i].setDe_tml(res.getString("de_tml"));
	            array_obj_user[i].setDe_user(res.getString("de_user"));
	            array_obj_user[i].setDe_date(res.getString("de_date"));
	            i++;
                
            }
	        
	     
        }catch(Exception e){e.printStackTrace();}
        
        finally
        {
            con.close();
        }
        return  array_obj_user;
	            
	            
	        }
	/*public AdministratorObject[] getViewUser1(String from,String to) throws SQLException
	{
	    AdministratorObject[] array_obj_user=null;
	    Connection con=null;
	    ResultSet res=null;
	    try
	    {
	        con=getConnection();
	        Statement stmt=con.createStatement();
	       
	        
	        res=stmt.executeQuery("select * from UserMasterLog where concat(right(left(de_date,10),4),'-',mid(left(de_date,10),locate('/',left(de_date,10))+1, (locate('/',left(de_date,10),4)-locate('/',left(de_date,10))-1)),'-',left(left(de_date,10),locate('/',left(de_date,10))-1)) between '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' order by cid");
	        res.last();
	        int row=res.getRow();
	        res.beforeFirst();
	        array_obj_user=new AdministratorObject[row];
	       
	        int i=0;
	        while(res.next())
	        {
	            System.out.println("inside the server1");
	            array_obj_user[i]=new AdministratorObject();
	            array_obj_user[i].setUid(res.getString("user_id"));
	            array_obj_user[i].setCust_id(res.getInt("cid"));
	            array_obj_user[i].setShortName(res.getString("short_name"));
	            array_obj_user[i].setPass_expiry_period(res.getInt("pwd_expiry_period"));
	            array_obj_user[i].setPass_expiry_date(res.getString("pwd_expiry_date"));
	            array_obj_user[i].setThump_ipm(res.getString("thump_ipm"));
	            array_obj_user[i].setAcc_operation_fromDate(res.getString("operative_from_date"));
	            array_obj_user[i].setAcc_oper_toDate(res.getString("operative_to_date"));
	            array_obj_user[i].setDisable(res.getBoolean("disable"));
	            array_obj_user[i].setDe_tml(res.getString("de_tml"));
	            array_obj_user[i].setDe_user(res.getString("de_user"));
	            array_obj_user[i].setDe_date(res.getString("de_date"));
	            i++;
                
            }
	        
	     
        }catch(Exception e){e.printStackTrace();}
        
        finally
        {
            con.close();
        }
	    
	    return  array_obj_user;   
	}*/
	    
	
	//for revoking the terminals assigned
	public int deletePermission(int user_no) throws RemoteException
	{
		int result=0;
		Connection conn=null;
		
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement();
			String qry1="Delete from TerminalLog where user_no="+user_no+"  ";
			if( s1.executeUpdate(qry1)!=0 )
				result=1;
			else 
				result=0;
			
			s1.close();
			
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		finally
		{
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return result;
	}
	//allowing access to terminals
	public int allowAccess(AdministratorObject[] array_obj_permit) throws RemoteException
	{
		int result=0;
		Connection conn=null;
		
		try
		{
			conn=getConnection();
			System.out.println("USer No= "+array_obj_permit[0].getUserNo());
			Statement s1=conn.createStatement();
			String qry1="Delete from TerminalLog where user_no="+array_obj_permit[0].getUserNo()+"  ";
			s1.executeUpdate(qry1);
		
			System.out.println("Array Length= "+array_obj_permit.length);
		
			Statement s2=conn.createStatement();
			for(int i=0;i<array_obj_permit.length;i++)
			{
				
				String qry2="Insert into TerminalLog(user_no,tml_code) values("+array_obj_permit[i].getUserNo()+",'"+array_obj_permit[i].getTmlNo()+"')  ";
				if(s2.executeUpdate(qry2)!=0)
					result=1;		
				else
					break;
			}
			
			s1.close();
			s2.close();
			
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		finally
		{
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return result;
	}
	
	//TerminalDetail
	public int createTerminal(String string_tml_code,String string_tml_name,String string_tml_type) throws RemoteException
	{
		int result=0;
		Connection conn=null;
		
		try
		{
			Statement s1=conn.createStatement();
			String qry1="Select * from TerminalDetail where tml_code='"+string_tml_code+"' and tml_desc='"+string_tml_name+"'   ";
			ResultSet rs1=s1.executeQuery(qry1);
			if(rs1.next())
			{
				result=2;
			}
			
			Statement s2=conn.createStatement();
			String qry2="Select * from TerminalDetail where tml_code='"+string_tml_code+"'  ";
			ResultSet rs2=s2.executeQuery(qry2);
			if(rs2.next())
			{
				result=3;
			}
			
			Statement s3=conn.createStatement();
			String qry3="Select * from TerminalDetail where tml_desc='"+string_tml_name+"'  ";
			ResultSet rs3=s3.executeQuery(qry3);
			if(rs3.next())
			{
				result=4;
			}
			
			Statement s4=conn.createStatement();
			if(result!=2  && result!=3  && result!=4)
			{
				String qry4="Insert into TerminalDetail(tml_code,tml_desc,tml_type) values('"+string_tml_code+"','"+string_tml_name+"','"+string_tml_type+"'  ) ";
				if( s4.executeUpdate(qry4)>0 )
				{
					result=1;
				}
			}
				
			s1.close();
			s2.close();
			s3.close();
			s4.close();
			
		}
		catch(Exception e1)
		{
          	result=0;
          	e1.printStackTrace();
		}
		finally
		{
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return result;
	}
	
	public AdministratorObject[] viewTerminals() throws RemoteException
	{
		AdministratorObject[] array_obj_tml=null;
		int int_no_records=0;
		int i=0;
		Connection conn=null;
		
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement();
			String qry1="Select distinct tml_code,tml_desc,tml_type from TerminalDetail ";
			ResultSet rs1=s1.executeQuery(qry1);
			while(rs1.next())
			{
				int_no_records++;
			}
			array_obj_tml=new AdministratorObject[int_no_records];
			System.out.println("No-Records= "+int_no_records);
		
			rs1.beforeFirst();
			
			while(rs1.next())
			{
				array_obj_tml[i]=new AdministratorObject();
				array_obj_tml[i].setTmlNo(rs1.getString("tml_code"));
				array_obj_tml[i].setTmlDesc(rs1.getString("tml_desc"));
				array_obj_tml[i].setTmlType(rs1.getString("tml_type"));
			
				i++;
			}
			
		}catch(Exception e1){e1.printStackTrace();}
		finally
		{
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return array_obj_tml;
	}
	
	//CREATE TERMINAL TYPE/MENU OBJECT
	public MenuObject[] getMenus() throws RemoteException
	{
		MenuObject[] array_obj_menu=null;
		int i=0;
		Connection conn=null;
		
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement();
			String qry1="Select distinct name,code from Menu order by code ";
			ResultSet rs1=s1.executeQuery(qry1);
			rs1.last();
			
			array_obj_menu=new MenuObject[ rs1.getRow() ];
			System.out.println("No-Records= "+rs1.getRow() );
		
			rs1.beforeFirst();
			
			while(rs1.next())
			{
				array_obj_menu[i]=new MenuObject();
				array_obj_menu[i].setMenuName(rs1.getString("name"));
				array_obj_menu[i].setMenuCode(rs1.getString("code"));
				
				i++;
			}
			
		}catch(Exception e1){e1.printStackTrace();}
		finally
		{
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return array_obj_menu;
	}
			
	
	/** Bean Class for TreeClient
	 * Its function is to extract the information from Database and convert it to
	 * serializable object.The total number of menu names and respective codes are 
	 * retrieved from Database and passed to the client side.
	 * @return
	 * @throws SQLException
	 */	
			public TreeObject[] getTreeDetails() throws SQLException 
			{
			
			System.out.print("...inside...");
			TreeObject TreeM[] =null; // serializable object
			Connection conn=null;
			ResultSet rs=null;
			Statement stmt=null;
			try{
				conn=getConnection();
				stmt=conn.createStatement();
				// Result set will contain the total number of menu names and code
				rs=stmt.executeQuery("Select Name,Num from Menu order by Num"); 
				rs.last();
				TreeM=new  TreeObject[rs.getRow()];
				rs.beforeFirst();
				int i=0;
				while(rs.next())
				{
					TreeM[i]=new TreeObject(); // object array
					
					TreeM[i].setMenuName(rs.getString("Name"));
					TreeM[i].setMenuCode(rs.getString("Num"));
					i++;
				}
			}
			catch(Exception e){	
				e.printStackTrace();		
			}
			finally
			{
					try {
						conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
			}
			return TreeM;
		}
			public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {
				this.ctx = ctx;
			}
	
			public void ejbRemove() throws EJBException, RemoteException {}
	
			public void ejbActivate() throws EJBException, RemoteException {}
	
			public void ejbPassivate() throws EJBException, RemoteException {}
			
			
			// code added by sanjeet..
			
			public int insertAccessValues(Hashtable hash,String tml){
			    Connection conn=null;
			    Statement stmt=null;
			    
				    try{
				        conn=getConnection();
				        stmt=conn.createStatement();
				        Enumeration keys = hash.keys();
				        Object key=null,value=null;
				        stmt.executeUpdate("delete from RoleAccess where role='"+tml+"'");
				        for(int i=0;i<hash.size();i++){
				            key=keys.nextElement();
				            value = hash.get(key);
				            stmt.executeUpdate("insert into RoleAccess values ('"+tml+"','"+key+"',"+value+")");
				        }
				    }
				    catch(Exception e){e.printStackTrace();}
				    finally{
				        try{
				            conn.close();
				        }
				        catch(SQLException sql){sql.printStackTrace();}
				    }
			        return 1;
			}
			
			
			public Hashtable getAccessValues(String tml){
			    Connection conn =null;
			    ResultSet rs=null;
			    Statement stmt=null;
			    Hashtable hash=null;
			    try{
			        conn=getConnection();
			        stmt=conn.createStatement();
			        rs=stmt.executeQuery("select * from RoleAccess where role='"+tml+"'");
			        hash=new Hashtable();
			        while(rs.next())
			            hash.put(rs.getString("screen_id"),rs.getString("access"));
			    }
			    catch(Exception e){e.printStackTrace();}
			    finally{
			        try{
			            conn.close();
			        }
			        catch(SQLException sql){sql.printStackTrace();}
			    }
			    return hash;
			}
			
	public TerminalObject[] getTerminalDetails()
	{
		Connection conn =null;
		ResultSet rs=null;
		Statement stmt=null;
		int count_row=0;
		TerminalObject[] tml_object=null;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select * from TerminalDetail");
			rs.last();
			count_row=rs.getRow();
			tml_object = new TerminalObject[count_row];
			rs.beforeFirst();
			count_row=0;
			while(rs.next()){
				tml_object[count_row]=new TerminalObject();
				tml_object[count_row].setTmlName(rs.getString("tml_code"));
				tml_object[count_row].setTmlDesc(rs.getString("tml_desc"));
				tml_object[count_row].setIPAddress(rs.getString("ip_address"));
				tml_object[count_row].setCashier(rs.getString("tml_type"));
				if(rs.getString("status") != null ) {
					tml_object[count_row].setStatus(rs.getInt("status"));
				}
				if(rs.getString("max_trn_amt")!=null)
					tml_object[count_row].setMaxAmount(rs.getDouble("max_trn_amt"));
				count_row++;
			}
		}
		catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}
			catch(SQLException sql){sql.printStackTrace();}
		}
		return tml_object;
	}
	
	public boolean deleteTerminal(String tml) 
	{
		Connection conn =null;
		ResultSet rs=null;
		Statement stmt=null;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			
			stmt.executeUpdate("delete from TerminalDetail where tml_code = '"+tml+"'");
			stmt.executeUpdate("delete from RoleAccess where role = '"+tml+"'");
			stmt.executeUpdate("delete from TerminalIPAddr where tml_no = '"+tml+"'");
			stmt.executeUpdate("delete from UserTerminals where tml_code = '"+tml+"'");
			
		}
		catch(Exception e){
			e.printStackTrace();
			ctx.setRollbackOnly();
			return false;
		}
		finally{
			try{
				conn.close();
			}
			catch(SQLException sql){sql.printStackTrace();}
		}
		return true;
	}
	
	public void setTerminalDetail(TerminalObject tml_object) throws RecordNotInsertedException
	{
		Connection conn =null;
		ResultSet rs=null;
		Statement stmt=null;
		PreparedStatement pstmt=null;
		int count_row=0;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			pstmt = conn.prepareStatement("insert into TerminalDetail values(?,?,?,?,?,?,?,?,?)");
			stmt.executeUpdate("delete from TerminalDetail where tml_code='"+tml_object.getTmlName()+"'");
			
			pstmt.setString(1,tml_object.getTmlName());
			pstmt.setString(2,tml_object.getTmlDesc());
			pstmt.setDouble(3,tml_object.getMaxAmount());
			pstmt.setString(4,tml_object.getIPAddress());
			pstmt.setInt(5,tml_object.getStatus());
			pstmt.setString(6,tml_object.uv.getUserTml());
			pstmt.setString(7,tml_object.uv.getUserId());
			pstmt.setString(8,tml_object.uv.getUserDate());
			pstmt.setString(9,tml_object.getCashier());
			
			int a = pstmt.executeUpdate();
			if(a<=0)
				throw new RecordNotInsertedException();
		}
		catch(Exception e){e.printStackTrace();throw new RecordNotInsertedException();}
		finally{
			try{
				conn.close();
			}
			catch(SQLException sql){sql.printStackTrace();}
		}
	}
	
	public String[] getUsers()
	{
		Connection conn =null;
		ResultSet rs=null;
		Statement stmt=null;
		String[] user_ids=null;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			rs = stmt.executeQuery("select user_id from UserMaster");
			rs.last();
			user_ids = new String[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next()){
				user_ids[i]=rs.getString("user_id");
				i++;
			}
			
		}
		catch(Exception e){e.printStackTrace();throw new RecordNotInsertedException();}
		finally{
			try{
				conn.close();
			}
			catch(SQLException sql){sql.printStackTrace();}
		}
		return user_ids;
	}
	
	public String[] getUserTerminals(String user_id)
	{
		Connection conn =null;
		ResultSet rs=null;
		Statement stmt=null;
		String[] tml_code=null;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			rs = stmt.executeQuery("select tml_code from UserTerminals where user_id='"+user_id+"'");
			rs.last();
			tml_code = new String[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next()){
				tml_code[i]=rs.getString("tml_code");
				i++;
			}
			
		}
		catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}
			catch(SQLException sql){sql.printStackTrace();}
		}
		return tml_code;
	}
	
	public void setUserTerminals(String user_id,String[] tml_code,String user,String tml,String date) throws RecordsNotInsertedException
	{
		Connection conn =null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		try{
			conn=getConnection();
			conn.createStatement().executeUpdate("delete from UserTerminals where user_id = '" + user_id + "'");
			pstmt = conn.prepareStatement("insert into UserTerminals values('"+user_id+"',?,'"+user+"','"+tml+"','"+date+"','0')");
			for(int i=0,k=0;i<tml_code.length;i++){
				k=0;
				pstmt.setString(1,tml_code[i]);
				k=pstmt.executeUpdate();
				if(k<=0)
					throw new RecordNotInsertedException();
			}
			
		}
		catch(Exception e){e.printStackTrace();throw new RecordNotInsertedException();}
		finally{
			try{
				conn.close();
			}
			catch(SQLException sql){sql.printStackTrace();}
		}
	}
	
	public int creatingUsers(AdministratorObject adminObj,int k)throws RecordNotInsertedException
	{
		System.out.println("Disable is -------->>>>"+adminObj.getDisable());
		int i=0;
		Connection conn =null;
		ResultSet rs=null;
		Statement stmt=null;
		Statement stmt1=null;
		try
		{
			
			conn=getConnection();
			stmt1=conn.createStatement();
			
			if(k==1)
			{
				stmt=conn.createStatement();
				if(adminObj.getDisable())
					i= stmt.executeUpdate("insert into UserMasterLog values('"+adminObj.getUid()+"','"+ adminObj.getCust_id()+"',AES_ENCRYPT('"+adminObj.getPassword()+"',8),'"+ adminObj.getShortName()+"','"+adminObj.getpass_expiry_period()+"','"+adminObj.getPass_expiry_date()+"','"+null+"','"+adminObj.getAcc_operation_fromDate()+"','"+adminObj.getAcc_oper_toDate()+"','"+0+"','"+adminObj.getDe_tml()+"','"+adminObj.getDe_user()+"','"+adminObj.getDe_date()+"')");              
				else
					i= stmt.executeUpdate("insert into UserMasterLog values('"+adminObj.getUid()+"','"+ adminObj.getCust_id()+"',AES_ENCRYPT('"+adminObj.getPassword()+"',8),'"+ adminObj.getShortName()+"','"+adminObj.getpass_expiry_period()+"','"+adminObj.getPass_expiry_date()+"','"+null+"','"+adminObj.getAcc_operation_fromDate()+"','"+adminObj.getAcc_oper_toDate()+"','"+1+"','"+adminObj.getDe_tml()+"','"+adminObj.getDe_user()+"','"+adminObj.getDe_date()+"')");
				
			}
			else
			{
				stmt=conn.createStatement();
				if(adminObj.getDisable())
					i= stmt.executeUpdate("insert into UserMaster values('"+adminObj.getUid()+"','"+ adminObj.getCust_id()+"',AES_ENCRYPT('"+adminObj.getPassword()+"',8),'"+ adminObj.getShortName()+"','"+adminObj.getpass_expiry_period()+"','"+adminObj.getPass_expiry_date()+"','"+null+"','"+adminObj.getAcc_operation_fromDate()+"','"+adminObj.getAcc_oper_toDate()+"','"+0+"','"+adminObj.getDe_tml()+"','"+adminObj.getDe_user()+"','"+adminObj.getDe_date()+"')");              
				else
					i= stmt.executeUpdate("insert into UserMaster values('"+adminObj.getUid()+"','"+ adminObj.getCust_id()+"',AES_ENCRYPT('"+adminObj.getPassword()+"',8),'"+ adminObj.getShortName()+"','"+adminObj.getpass_expiry_period()+"','"+adminObj.getPass_expiry_date()+"','"+null+"','"+adminObj.getAcc_operation_fromDate()+"','"+adminObj.getAcc_oper_toDate()+"','"+1+"','"+adminObj.getDe_tml()+"','"+adminObj.getDe_user()+"','"+adminObj.getDe_date()+"')");
			}
			
		}
		catch(SQLException sql){
			
			throw new RecordNotInsertedException();
		}
		finally{
			try{
				conn.close();
			}
			catch(SQLException sql){sql.printStackTrace();}
		}
		return i;
	}
	
	public AdministratorObject retrieveCustDetail(int cust_id)throws RecordNotInsertedException 
	{
		AdministratorObject adminObj=new AdministratorObject();
		int i=0;		
		Connection conn =null;
		ResultSet rs=null;
		ResultSet rs1=null;
		Statement stmt=null;
		Statement stmt1=null;
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			stmt1=conn.createStatement();
			rs=stmt.executeQuery("select * from UserMaster where user_id like '" + cust_id + "'");
			rs.beforeFirst();
			if(rs.next())
			{
				System.out.println("inside reterive customerdetails");
				adminObj.setUid(rs.getString("user_id"));
				adminObj.setCust_id(rs.getInt("cid"));
		//		adminObj.setPassword(rs.getString("password"));
				adminObj.setShortName(rs.getString("short_name"));
				adminObj.setpass_expiry_period(rs.getInt("pwd_expiry_period"));
				adminObj.setPass_expiry_date(rs.getString("pwd_expiry_date"));
				
				rs1=stmt1.executeQuery("select AES_DECRYPT(password,8) as password from UserMaster where cid='"+cust_id+"'");
				if(rs1.next())
				{
					adminObj.setPassword(rs1.getString("password"));
					
				}
				
				
				
				
				adminObj.setAcc_operation_fromDate(rs.getString("operative_from_date"));
				adminObj.setAcc_oper_toDate(rs.getString("operative_to_date"));
				adminObj.setDe_date(rs.getString("de_date"));
				adminObj.setDe_tml(rs.getString("de_tml"));
				adminObj.setDe_user(rs.getString("de_user"));
				
				if(rs.getInt("disable")==1)
					adminObj.setDisable(true);
				else
					adminObj.setDisable(false);
				return adminObj;
			}
			else
				return null;
		}
		catch(Exception exe)
		{
			exe.printStackTrace();
		}
		finally{
			try{
				conn.close();
			}
			catch(SQLException sql){sql.printStackTrace();}
		}
		return adminObj;
	}
	//CODE ADDED BY AMZAD ON 17.02.2009 STARTS
	public AdministratorObject retrieveCustDetailNew(String cust_id)throws RecordNotInsertedException 
	{
		AdministratorObject adminObj=new AdministratorObject();
		int i=0;		
		Connection conn =null;
		ResultSet rs=null;
		ResultSet rs1=null;
		Statement stmt=null;
		Statement stmt1=null;
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			stmt1=conn.createStatement();
			rs=stmt.executeQuery("select * from UserMaster where user_id like '" + cust_id + "'");
			if(rs.next())
			{
				adminObj.setUid(rs.getString("user_id"));
				adminObj.setCust_id(rs.getInt("cid"));
		//		adminObj.setPassword(rs.getString("password"));
				adminObj.setShortName(rs.getString("short_name"));
				adminObj.setpass_expiry_period(rs.getInt("pwd_expiry_period"));
				adminObj.setPass_expiry_date(rs.getString("pwd_expiry_date"));
				
				rs1=stmt1.executeQuery("select AES_DECRYPT(password,8) as password from UserMaster where cid='"+cust_id+"'");
				if(rs1.next())
				{
					adminObj.setPassword(rs1.getString("password"));
					
				}
				
				
				
				
				adminObj.setAcc_operation_fromDate(rs.getString("operative_from_date"));
				adminObj.setAcc_oper_toDate(rs.getString("operative_to_date"));
				adminObj.setDe_date(rs.getString("de_date"));
				adminObj.setDe_tml(rs.getString("de_tml"));
				adminObj.setDe_user(rs.getString("de_user"));
				
				if(rs.getInt("disable")==1)
					adminObj.setDisable(true);
				else
					adminObj.setDisable(false);
			}
			else
				return null;
		}
		catch(Exception exe)
		{
			exe.printStackTrace();
		}
		finally{
			try{
				conn.close();
			}
			catch(SQLException sql){sql.printStackTrace();}
		}
		return adminObj;
	}
	
	//CODE ADDED BY AMZAD ON 17.02.2009 ENDS
	public int deleteCreateUser(String cid)
	{System.out.println("indide delete code for Create users");
		int i=0;
		Connection conn =null;
		   ResultSet rs=null;
		   Statement stmt=null;
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			 i=stmt.executeUpdate("delete from UserMaster where user_id ='"+cid+"'");
		}
		catch(Exception exec)
		{
			exec.printStackTrace();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
		return i;
	}
	
	public void updateCreate(AdministratorObject adObje,AdministratorObject admObj)throws RecordNotInsertedException
	{
		AdministratorObject admin;
		 int h=adObje.getCust_id();
		//  admin=retrieveCustDetail(h);
		try{
//System.out.println("admin   not null====>>>"+admin.getDisable());
		  creatingUsers(admObj,1);
		 deleteCreateUser(admObj.getUid());
		 creatingUsers(adObje,0);
		}
		catch(RecordNotInsertedException sqle)
		{ 
			 throw sqle;
		}
		catch(Exception ex)
		{ System.out.println("Exception has occured");
			 ex.printStackTrace();
		}
		
	}
	
	public String[] getValidUser(String userid)
	{
		String str[]=null;
		Connection conn =null;
		ResultSet rs=null;
		Statement stmt=null;

		try
		{
			conn = getConnection(); 
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select user_id,AES_DECRYPT(password,8) as password from UserMaster where user_id='"+userid+"'");
			if(rs.next())
			{
				str = new String[2];
				str[0]=rs.getString("user_id");
				str[1]=rs.getString("password");
			}
			else 
			{   		
				return null;
			}
		}
		catch(Exception e)
		{
			
		}
		finally
		{
		    try
			{
		    	conn.close();
			}
		    catch(Exception exe)
			{
		    	exe.printStackTrace();
			}
		}
		return str;
		}
	public int createUserPassword(String string_name,String string_old_password,String string_new_password) throws RemoteException
	{
		System.out.println(string_name);
		System.out.println(string_old_password);
		System.out.println(string_new_password);
		
		Connection conn=null;
		int result=0;
		
		try
		{
			conn=getConnection();
			Statement s1=conn.createStatement();
			String qry1="Update UserMaster set password=AES_ENCRYPT('"+string_new_password+"',8) where user_id='"+string_name+"' and password=AES_ENCRYPT('"+string_old_password+"',8)  ";
			if(s1.executeUpdate(qry1)!=0)
			{
				result=1;		
			}
			
			
		}catch(Exception e1){e1.printStackTrace();}
		finally
		{
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return result ;
	}
	
	
	
	public void insertIntoTerminalIPAddr(AdministratorObject adminObjec,String[] str )throws RecordNotInsertedException
	{
		Connection conn=getConnection();
		PreparedStatement stmt;
		Statement stmt2;
		int i=0;
		int k=0;
		try
		{
			//insertIntoTerminalAll();
			//ArrayList array=getDestinctTerminalAddr();
			stmt2=conn.createStatement();
			//k=stmt2.executeUpdate("delete from TerminalIPAddr where tml_no='"+adminObjec.getTerminal_no()+"'");
			
				for(int j=0;j<str.length;j++)
				{
					stmt=conn.prepareStatement("insert into TerminalIPAddr values(?,?,?,?,?)");
					stmt.setString(1,adminObjec.getTerminal_no());
					stmt.setString(2,str[j]);
					stmt.setString(3,adminObjec.getDe_user());
					stmt.setString(4,adminObjec.getDe_tml());
					stmt.setString(5,adminObjec.getDe_date());
					i=stmt.executeUpdate();
					if(i==0)
					{
						throw new RecordNotInsertedException();
					}
				}
				
				
		}
		catch(Exception exec)
		{
			exec.printStackTrace();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
		}
	}
	
	public ArrayList getDestinctTerminalAddr()
	{
		Connection conn=getConnection();
		Statement stmt;
		PreparedStatement stmt1;
		ResultSet rs;
		ArrayList arr=new ArrayList(); 
		try
		{
			stmt=conn.createStatement();
			
			rs=stmt.executeQuery("select Distinct ip_address from TerminalIPAddr");
			while(rs.next())
			{
				arr.add(rs.getString("ip_address"));
			}
			rs.beforeFirst();
			
		}
		catch(Exception exec)
		{
			exec.printStackTrace();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
		}
		return arr;
	}
	public ArrayList getTerminalIPAddr(String str)
	{
		Connection conn=getConnection();
		Statement stmt;
		ResultSet rs;
		ArrayList arr1=new ArrayList(); 
		int i=0;
		try
		{
			
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select  ip_address from TerminalIPAddr where tml_no='"+str+"'");
			while(rs.next())
			{
				//System.out.println(rs.getString("ip_address"));
				arr1.add(rs.getString("ip_address"));
			}
			
		}
		catch(Exception exec)
		{
			exec.printStackTrace();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
	}
		return arr1;
	}
	
	public int deleteTerminalIpAddr(String str[])throws RecordNotInsertedException
	{
		int i=0;
		Connection conn=getConnection();
		Statement stmt;
		ResultSet rs;
 
		try
		{
			
			stmt=conn.createStatement();
			for(int j=0;j<str.length;j++)
			{
				i=stmt.executeUpdate("delete  from TerminalIPAddr where ip_address='"+str[j] +"'");
				if(i<0)
				{
					throw new RecordNotInsertedException();
				}
			}
			
		}
		catch(Exception exec)
		{
			exec.printStackTrace();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
	}
		return i;
	}
	/*public void insertIntoTerminalAll()
	{
		PreparedStatement stmt1;
		Connection conn;
		Statement smt;
		conn=getConnection();
		ResultSet rs;
		try
		{
				
				smt=conn.createStatement();
				rs=smt.executeQuery("select Distinct ip_address from TerminalIPAddr where tml_no!='ALL'");
				
				int s=0;
				while(rs.next())
				{
					stmt1=conn.prepareStatement("insert into TerminalIPAddr values(?,?,?,?,?)");
					stmt1.setString(1,"ALL");
					stmt1.setString(2,rs.getString("ip_address"));
					stmt1.setString(3,"null");
					stmt1.setString(4,"null");
					stmt1.setString(5,"null");
					s=stmt1.executeUpdate();
				}
		}
		catch(Exception exe)
		{
			exe.printStackTrace();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception rr)
			{
				
			}
		}
		
	}
	*/
	
	public ModuleAdminObject getGLData(String modulecode ) {
		
		Connection conn  = null;
		Statement stmt = null;
		ResultSet rs;
		Vector trn_type=new Vector(),trn_desc=new Vector();
		
		ModuleAdminObject module_obj = new ModuleAdminObject();
		try{
			
			module_obj.setModuleCode(modulecode);
			
			conn=getConnection();
			
			// to fetch the transaction types
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select distinct trn_type,trn_desc from GLTransactionType  where ac_type = '"+modulecode+"'");
			
			if(!rs.next()) {
				rs = stmt.executeQuery("select distinct trn_type,trn_desc from GLTransactionType  where ac_type like '"+modulecode.substring(0, 4)+"%'");
			} else {
				rs.beforeFirst();
			}
			
			while(rs.next()) {
				System.out.println("trn type is getting");
				trn_type.add(rs.getString("trn_type"));
				
				trn_desc.add(rs.getString("trn_desc"));
			}
			module_obj.GLtrntypes.setTrnType(trn_type);
			module_obj.GLtrntypes.setTrnDesc(trn_desc);
			
			// to fetch the GL Key Param Values
			rs.close();
			rs=stmt.executeQuery("select * from GLKeyParam gp where ac_type='"+modulecode+"' order by code");
			
			int key_param_rows=0;
			if(rs.last()) {
				key_param_rows = rs.getRow();
				rs.beforeFirst();
			}
			
			if(key_param_rows>0) {
				module_obj.getGLKeyParaInstance(key_param_rows);
			}
			
			int k=0;
			while(rs.next() && key_param_rows>0) {
				module_obj.GLkeyparam[k]=module_obj.new GLKeyParamObject();
				module_obj.GLkeyparam[k].setAccType(rs.getString("ac_type"));
				
				module_obj.GLkeyparam[k].setKeyDesc(rs.getString("key_desc"));
				module_obj.GLkeyparam[k].setCode(rs.getInt("code"));
				module_obj.GLkeyparam[k].setGLCode(rs.getInt("gl_code"));
				module_obj.GLkeyparam[k].setGLType(rs.getString("gl_type"));
				k++;
			}
			
			//to fetch the GL Post Values
			int post_rows=0;
			rs.close();
			rs=stmt.executeQuery("select * from GLPost where ac_type='"+modulecode+"'");
			
			if(rs.last()) {
				post_rows = rs.getRow();
				rs.beforeFirst();
			}     
			
			/*if(post_rows>0) {
				module_obj.GLpost = module_obj.getGLPostInstance(post_rows);
			}*/
			
			k=0;
			while(rs.next() && post_rows>0) {
				/*System.out.println("At 1677 in AdministratorBean==============><");
				module_obj.GLpost[k]=module_obj.new GLPostObject();
				System.out.println("module_obj.GLpost[k]======>");
				module_obj.GLpost[k].setAccType(rs.getString("ac_type"));
				module_obj.GLpost[k].setGLType(rs.getString("gl_type"));
				module_obj.GLpost[k].setCDInd(rs.getString("cr_dr"));
				module_obj.GLpost[k].setTrnType(rs.getString("trn_type"));
				module_obj.GLpost[k].setGLCode(rs.getInt("gl_code"));    
				module_obj.GLpost[k].setMultiplyBy(rs.getInt("mult_by"));*/
				
				//module_obj.GLPostVector.add(module_obj.GLpost);
				k++;
			}
			
			
			rs.close();
			rs=stmt.executeQuery("select * from Modules where modulecode like '"+modulecode+"' order by modulecode");
			String[] values = null;
			
			
			if(rs.last()){
				
				java.sql.ResultSetMetaData rs_meta = rs.getMetaData();
				
				values = new String[rs_meta.getColumnCount()];
				System.out.println(values.length+"string arr length");
				rs.beforeFirst();
				rs.next();
				
			}
			
			for(int i=2; i<=values.length; i++){
				
				values[i-2] = rs.getString(i);
				System.out.println(rs.getString(i)+"->values");
				
			}
			if(values!= null) {
				module_obj.setModulesValues(values);
			}
			/**
			 *   select * from Modules where modulecode like param
			 *   
			 *   String[] values = new fo
			 *   
			 *   for( int i =0 ; i < 40 i++ {
			 *   
			 *     values[i] = re.getsrtring(i);
			 *    }
			 *    
			 */
			
		}catch(Exception se)
		{
			se.printStackTrace();
		}finally
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
		
		return module_obj;
	}
	
	
	public int GLModuleAdmin(ModuleAdminObject admin){
	
		
		Connection conn  = null;
		PreparedStatement pstmt = null;
		ResultSet rs;
		
		/*try{
			
			conn = getConnection();
			
			//pstmt = conn.prepareStatement("update ")
			
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		*/
		return 1;
	}
	
	public Vector checkLogin(String user_id, String password, String login_date, String tml_id, String ip_address) throws LoginException 
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		Vector return_values;
		try {
 			conn = getConnection();
 			stmt = conn.createStatement();

 			rs = stmt.executeQuery("select user_id,AES_DECRYPT(password,8) as password,pwd_expiry_date,operative_from_date,operative_to_date,disable  from UserMaster where user_id='"+user_id+"'");
 			if(rs.next()) {
 				
 				if(rs.getString("password")== null || !(rs.getString("password").equalsIgnoreCase(password)))
 					throw new PasswordInCorrectException();
 				
 				if(Validations.dayCompare(login_date,rs.getString("pwd_expiry_date")) <= 0)
 					throw new PasswordExpiredException();
 				
 				if((Validations.dayCompare(rs.getString("operative_from_date"),login_date)) <= 0 || (Validations.dayCompare(login_date,rs.getString("operative_to_date")) <= 0))
 					throw new UserInOperativeException();
 				
 				if(rs.getString("disable") == null || rs.getInt("disable")==1 )
 					throw new UserDisabledException();
 				//changed by Mohsin
 				/*if(checkTerminal(tml_id,ip_address)!=1)
 					throw new TerminalNotExistException();*/
 				
 				return_values = getUserRoles(user_id,tml_id);
 					
 			} else {
 				throw new UserNotExistException();
 			}
			
		}catch (SQLException sql) {
			ctx.setRollbackOnly();
			throw new LoginException();
		}catch (LoginException le) {
			ctx.setRollbackOnly();
			throw le;
		}catch (Exception exe) {
			ctx.setRollbackOnly();
			throw new LoginException();
		}finally {
			try {
				conn.close();
			}catch (SQLException sql) {
				ctx.setRollbackOnly();
				throw new LoginException();
			} 
		}
		return return_values;
	}
	
	public int checkTerminal(String tml_id, String ip_address) throws LoginException 
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
 			conn = getConnection();
 			stmt = conn.createStatement();

 			rs = stmt.executeQuery("select tml_code,max_trn_amt,ip_address,status  from TerminalDetail where tml_code='"+tml_id+"'");
 			if(rs.next()) {
 				if(rs.getString("status") == null || (rs.getInt("status")==1 && !(rs.getString("ip_address").equalsIgnoreCase(ip_address)))) {
 					throw new TerminalOccupiedException();
 				}
 				
 				if(rs.getString("ip_address") == null || !(rs.getString("ip_address").equalsIgnoreCase(ip_address))) {
 					rs = stmt.executeQuery("Select * from TerminalIPAddr where tml_no='"+tml_id+"' and ip_address ='"+ip_address+"'");
 					if(!rs.next()) {
 						throw new TerminalIPAddrException();
 					}
 				}
 				
 				if(stmt.executeUpdate("update TerminalDetail set ip_address='"+ip_address+"',status=1 where tml_code='"+tml_id+"'")>0)
 					return 1;
 				
 			} else {
 				throw new TerminalNotExistException();
 			}
			
		}catch (SQLException sql) {
			ctx.setRollbackOnly();
			throw new LoginException();
		}catch (LoginException le) {
			ctx.setRollbackOnly();
			throw le;
		}catch (Exception exe) {
			ctx.setRollbackOnly();
			throw new LoginException();
		} finally {
			try {
				conn.close();
			}catch (SQLException sql) {
				ctx.setRollbackOnly();
				throw new LoginException();
			} 
		}
		return 0;
	}
	
	public Vector getUserRoles(String user_id, String tml_id) throws LoginException
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		Vector return_values;
		
		try {
 			conn = getConnection();
 			stmt = conn.createStatement();
 			
 			if(tml_id != null){
 				rs = stmt.executeQuery("select distinct tml_code from UserTerminals where user_id='"+user_id+"' and tml_code='"+tml_id+"'");
 				if(!rs.next())
 					throw new TerminalAccessNotAllowed();
 			} 

 			return_values = new Vector();
 			rs = stmt.executeQuery("select distinct tml_code from UserTerminals where user_id='"+user_id+"'");
 			while(rs.next()) {
 				return_values.add(rs.getString("tml_code"));
 			}
 			
		}catch (SQLException sql) {
			throw new LoginException();
		}catch (LoginException le) {
			throw le;
		}catch (Exception exe) {
			throw new LoginException();
		} finally {
			try {
				conn.close();
			}catch (SQLException sql) {
				throw new LoginException();
			} 
		}
		return return_values;
	}
	//06-11-2011
	public boolean checkUserLogin(String tml_id,String user_id){
		boolean flag=false;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		int status=0;
		try {
 			conn = getConnection();
 			stmt = conn.createStatement();
 			rs=stmt.executeQuery("select distinct status from UserTerminals where user_id='"+user_id+"' and tml_code='"+tml_id+"'");
 			while(rs.next()){
 				status=rs.getInt("status");
 				if(status==0){
 					stmt.executeUpdate("update UserTerminals set status=1 where user_id='"+user_id+"' and tml_code='"+tml_id+"'");
 				}else{
 					return true;
 				}
 			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			}catch (SQLException sql) {
				sql.printStackTrace();
			} 
		}
		return flag;
	}
	public void doLogout(String tml_id,String user_id)
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
 			conn = getConnection();
 			stmt = conn.createStatement();
 			stmt.executeUpdate("update TerminalDetail set ip_address='"+null+"' ,status=0  where tml_code='"+tml_id+"'");
 			stmt.executeUpdate("update UserTerminals set status=0  where tml_code='"+tml_id+"' and user_id='"+user_id+"'");
		}catch (Exception exe) {
			exe.printStackTrace();
		} finally {
			try {
				conn.close();
			}catch (SQLException sql) {
				sql.printStackTrace();
			} 
		}
	}
	
	
public int UpdateGLModuleAdmin(ModuleAdminObject admin){
	
		
		Connection conn  = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		Statement smt = null;
		Statement smt1 = null;
		Statement smt2 = null;
		ResultSet rs;
		int k=0;
		try{
			
			conn = getConnection();
			smt = conn.createStatement();
			System.out.println("module code "+admin.getModuleCode());
			int j = smt.executeUpdate("delete from GLKeyParam where ac_type = '"+ admin.getModuleCode()+"'");
			pstmt = conn.prepareStatement("insert into GLKeyParam values(?,?,?,?,?,null,null,null) ");
			for(int i=0;  i < admin.GLkeyparam.length; i++) {
				ModuleAdminObject.GLKeyParamObject glkeyparam =  (ModuleAdminObject.GLKeyParamObject)admin.GLkeyparam[i];
				
				//pstmt.executeQuery("delete from GLKeyparam1 where ac_type like '"+ glkeyparam.getAccType()+"'");
				
				
				/*pstmt = conn.prepareStatement("insert into GLKeyparam1 values(?,?,?,?,?,null,null,null) ");*/
				
				pstmt.setString(1,admin.getModuleCode());
				pstmt.setInt(2,glkeyparam.getCode());
				pstmt.setString(3,glkeyparam.getKeyDesc());
				pstmt.setInt(4,glkeyparam.getGLCode());
				pstmt.setString(5,glkeyparam.getGLType());
				
				
				 k = pstmt.executeUpdate();
				 
			}
			smt1 = conn.createStatement();
			int l = smt.executeUpdate("delete from GLPost where ac_type = '"+ admin.getModuleCode()+"'");
			pstmt1 = conn.prepareStatement("insert into GLPost values(?,?,?,?,?,?,null,null,null) ");
			for(int i=0; i < admin.GLPostVector.size(); i++) {
				ModuleAdminObject.GLPostObject glpost =  (ModuleAdminObject.GLPostObject)admin.GLPostVector.get(i);
				
				pstmt1.setString(1,admin.getModuleCode());
				pstmt1.setString(2,glpost.getGLType());
				pstmt1.setInt(3,glpost.getGLCode());
				pstmt1.setString(4,glpost.getTrnType());
				pstmt1.setString(5,glpost.getCDInd());
				pstmt1.setInt(6,glpost.getMultiplyBy());
				
				k = pstmt1.executeUpdate();
				
			}
			
			
			smt2 = conn.createStatement();
			int n = smt2.executeUpdate("delete from Modules where modulecode = '"+ admin.getModuleCode()+"'");
			pstmt2 = conn.prepareStatement("insert into Modules values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
			String [] values = admin.getModulesValues();
			System.out.println("---->1992<------values.length====>"+values.length);
			pstmt2.setString(1,admin.getModuleCode());
			//for (int i = 1; i < values.length+1; i++) {
			for (int i = 1; i < values.length; i++) {
			System.out.println("value of i=====>"+i);
				
				
				pstmt2.setString((i+1),values[i-1]);
				//System.out.println((i+1)+" position ----->"+values[i-1]);
			}
			int ret_val = pstmt2.executeUpdate();
			
			
			
			
		}catch(SQLException sql){
			sql.printStackTrace();
			return -1;
		}finally
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
		
		return k;
	}

	public String getNewModuleCode(String modulecode){
		
		Connection conn = null;
		Statement smt = null;
		ResultSet rs = null ;
		String code = null;
		
		try{
			
			conn = getConnection();
			smt = conn.createStatement();
			rs = smt.executeQuery("select max(modulecode) as modulecode from Modules where modulecode like '"+modulecode.substring(0, 4)+"%'");
			int  new_code = 0;
			if(rs.next()){
				 new_code = rs.getInt("modulecode")+1;
			}
			
			code = String.valueOf(new_code);
			System.out.println(" the value is +++++++++++++" + code);
			
		}catch(SQLException sql){
			sql.printStackTrace();
		}finally{
			
			try{
				
				conn.close();
				
			}catch(SQLException exc){
				
				exc.printStackTrace();
			}
		}
		
		return code;
	}
	//code added  by Amzad on 17.02.2009 starts
	public String deleteHoliday(String day[]) 
	{
		int result=0;
		Connection con=null;
		try
		{
			con=getConnection();
			Statement st=con.createStatement();
	        for(int i=0;i<day.length;i++)		
			    System.out.println("in Bean day values are==="+day[i]);
			Statement stmt=con.createStatement();
			for(int i=0;i<day.length;i++){
				
				result=stmt.executeUpdate("delete from HolidayMaster where date='"+day[i]+"'");
			}
			
			 
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{
			
				con.close();
			}catch(Exception ss){
				
				ss.printStackTrace();
			}
		}
		if(result!=0)
			return "success";
		else
			return "fail";
		
	}
	
	//code added  by Amzad on 17.02.2009 ends
	
	public void insertHoliday(String day[],String date[],String reason[],String br_name[],String tml[],String uid[],String sysdate[]) 
	{
		Connection con=null;
		/*String date1=date;
		String day1=day;
		String reason1=reason;
		String br_name1=br_name;
		String tml1=tml;
		String uid1=uid;
		String sysdate1=sysdate;
		*/
		try
		{
			con=getConnection();
			Statement st=con.createStatement();
			int res1=st.executeUpdate("delete from HolidayMaster");
			for(int i=0;i<day.length;i++)
			 st.executeUpdate("insert into HolidayMaster values('"+date[i]+"','"+br_name[i]+"''"+day[i]+"','"+reason[i]+"','"+tml[i]+"','"+uid[i]+"','"+sysdate[i]+"')");
			//System.out.println("res............"+res);
			//return res;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{
			
				con.close();
			}catch(Exception ss){
				
				ss.printStackTrace();
			}
		}
		//return 0;
	}
	
	
	public AdministratorObject[] showholidays()
	{
		Connection con=null;
		ResultSet rs = null ;
		AdministratorObject arr[]=null;
		try
		{
			con=getConnection();
			Statement st=con.createStatement();
		    rs=st.executeQuery("select distinct * from HolidayMaster");
		    int i=0;
		    rs.last();
		    System.out.println("row number"+rs.getRow());
		    arr =new AdministratorObject[rs.getRow()];
		    rs.beforeFirst();
		    while(rs.next())
		    {
		    	arr[i]=new AdministratorObject();
		    	System.out.println(rs.getString("date"));
		    	arr[i].setdate(rs.getString("date"));
		    	System.out.println(rs.getString("day"));
		    	arr[i].setday(rs.getString("day"));
		    	System.out.println(rs.getString("reason"));
		    	arr[i].setreason(rs.getString("reason"));
		    	System.out.println(rs.getInt("br_code"));
		    	arr[i].setbr_name("Head Office");
		    	System.out.println(rs.getString("de_tml"));
		    	arr[i].setde_tml(rs.getString("de_tml"));
		    	arr[i].setdeuser(rs.getString("de_user"));
		    	System.out.println(rs.getString("de_user"));
		    	arr[i].setdate1(rs.getString("de_dt_time"));
		    	i++;
		    }
		    return arr;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally{
			try{
			con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	//	return arr;
		
	}
	
	public String [] getAccessRight(String user,String tml){
		Connection con=null;
		ResultSet rs = null ;
		String arr[]=null;
		try
		{
			con=getConnection();
			Statement st=con.createStatement();
		    rs=st.executeQuery("select distinct * from User_Access_Rights where userid='"+user+"' and tml='"+tml+"'");
		    int i=0;
		    
		   System.out.println("row number");
		    arr =new String[14];
		  
		    if(rs!=null&&rs.next()){
		    	System.out.println("insode if condition");
		    	for(int j=0;j<arr.length;j++){
		    		int k=j+1;
		    	arr[j]=new String();
		    	arr[j]=""+rs.getInt("mod"+k);
		    	
		    	}
		    	System.out.println("insode if condition  end");
		    	return arr;
		    }
		    else{
		    	return null;
		    }
		    
			
		}
		catch(Exception e)
		{
			System.out.println("null PointerException");
			e.printStackTrace();
			return null;
		}
		finally{
			try{
			con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		
		
	}
		
	}
	
	//to Set Access
	public  String setAccessRight(String user,String tml,String[] arr,int ind){
		Connection con=null,con2=null,con3=null;
		ResultSet rs = null ;
		
		try
		{
			//ind=0 for insert ind=1 for update
		if(ind==0){	
			con=getConnection();
			Statement st=con.createStatement();
		    rs=st.executeQuery("select distinct * from User_Access_Rights where userid='"+user+"' and tml='"+tml+"'");
		    int i=0;
		    
		   System.out.println("row number");
		   
		    
		    if(rs.next())
		    {
		    	return "Access Right's for this user and terminal already Exist";
		    }
		    
		    else{
		    	con2=getConnection();
		    	String queryStr="insert into User_Access_Rights values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		    	PreparedStatement pst=con2.prepareStatement(queryStr);
		    	pst.setString(1,user);
				pst.setInt(2,Integer.parseInt(arr[0].trim()));
				pst.setInt(3,Integer.parseInt(arr[1].trim()));
				pst.setInt(4,Integer.parseInt(arr[2].trim()));
				pst.setInt(5,Integer.parseInt(arr[3].trim()));
				pst.setInt(6,Integer.parseInt(arr[4].trim()));
				pst.setInt(7,Integer.parseInt(arr[5].trim()));
				pst.setInt(8,Integer.parseInt(arr[6].trim()));
				pst.setInt(9,Integer.parseInt(arr[7].trim()));
				pst.setInt(10,Integer.parseInt(arr[8].trim()));
				pst.setInt(11,Integer.parseInt(arr[9].trim()));
				pst.setInt(12,Integer.parseInt(arr[10].trim()));
				pst.setInt(13,Integer.parseInt(arr[11].trim()));
				pst.setInt(14,Integer.parseInt(arr[12].trim()));
				pst.setInt(15,1111);
				pst.setInt(16,Integer.parseInt(arr[13]));
				
				pst.setString(17,tml);
				int re=pst.executeUpdate();
				if(re==1){
					return "Inserted Successfully";
				}
		    }
		    
		}
		else if(ind==1){
			con3=getConnection();
			Statement st=con3.createStatement();
		    st.executeUpdate("delete from User_Access_Rights where userid='"+user+"' and tml='"+tml+"'");
		    
			
			con2=getConnection();
	    	String queryStr="insert into User_Access_Rights values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	    	PreparedStatement pst=con2.prepareStatement(queryStr);
	    	pst.setString(1,user);
			pst.setInt(2,Integer.parseInt(arr[0]));
			pst.setInt(3,Integer.parseInt(arr[1]));
			pst.setInt(4,Integer.parseInt(arr[2]));
			pst.setInt(5,Integer.parseInt(arr[3]));
			pst.setInt(6,Integer.parseInt(arr[4]));
			pst.setInt(7,Integer.parseInt(arr[5]));
			pst.setInt(8,Integer.parseInt(arr[6]));
			pst.setInt(9,Integer.parseInt(arr[7]));
			pst.setInt(10,Integer.parseInt(arr[8]));
			pst.setInt(11,Integer.parseInt(arr[9]));
			pst.setInt(12,Integer.parseInt(arr[10]));
			pst.setInt(13,Integer.parseInt(arr[11]));
			pst.setInt(14,Integer.parseInt(arr[12]));
			pst.setInt(15,1111);
			pst.setInt(16,Integer.parseInt(arr[13]));
			
			pst.setString(17,tml);
			int re=pst.executeUpdate();
			if(re==1){
				return "Updated Successfully";
			}
			
		}
		return null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally{
			try{
			con.close();
			con2.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		
		
	}
		
	}
	
	//Added by Mohsin to get FormwiseAccessRights
	
	public  Object[][] getFormwiseAccessRights(String rolecode,String formcode)
	{
		Connection con=null;
		ResultSet rs = null,rs1=null ;
		Object arr[][]=null;
		try
		{
			con=getConnection();
			Statement st=con.createStatement();
		  //  rs=st.executeQuery("select * from FormsDetail fm,FormwiseAccess fa where fm.userid='"+user+"' and fm.formcode like '"+formcode+"%' fm.formcode=fa.formcode");
			  rs=st.executeQuery("select fm.formname,fa.access from FormsDetail fm,FormwiseAccess fa where fa.role_code='"+rolecode+"' and fm.formcode like '"+formcode+"%' and fm.formcode=fa.formcode order by fm.formcode");
			  ResultSetMetaData rsmd=rs.getMetaData();
				int num=rsmd.getColumnCount();
				
		    int i=0,j=0;
		    rs.last();
		    System.out.println("row number"+rs.getRow());
		    if(rs.getRow()>0){
		    	
		    arr =new Object[rs.getRow()][num];
		    rs.beforeFirst();
		    while(rs.next())
		    {
		    	for(int k=0;k<num;k++){
		    		arr[j][k]=rs.getString(k+1);
		    	}
		    	
		    	j++;
		    }
		    
		    return arr;
		    }
		    else{
		    	
		    	//this code should be placed somewhere else
		    	
		    	 rs1=st.executeQuery("select formname,formcode from FormsDetail where formcode like '"+formcode+"%' order by formcode");
		    	 ResultSetMetaData rsm=rs1.getMetaData();
					int cols=rsmd.getColumnCount();
					
					rs1.last();
					arr =new Object[rs1.getRow()][num];
				    rs1.beforeFirst();
				    while(rs1.next())
				    {
				    	for(int k=0;k<num;k++){
				    		arr[j][k]=rs1.getString(k+1);
				    	}
				    	
				    	j++;
				    }
				    
				    return arr;
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//return null;
		}
		finally{
			try{
			con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return arr;
		
	}
	//Added by Mohsin on 24/11/2009 to get list of Roles
	
	public AdministratorObject[] getRoleDefinition(){
		
		AdministratorObject mod[]=null;
		Connection conn=null;
		Statement stmt=null;
		//System.out.println("TO Populated vales in COMBO Box...");
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			
			ResultSet rs=null;
			
				rs=stmt.executeQuery("select * from RoleDefinition");
			if(rs.next())
			{	
				rs.last();
				mod=new AdministratorObject[rs.getRow()];
				rs.beforeFirst();
			}
			int i=0;
			while(rs.next())
			{
				System.out.println("inside Admin bean--->>>getRoleDefinition");
				mod[i]=new AdministratorObject();
				mod[i].setRolecode(rs.getString("role_code"));
				mod[i].setRoledesc(rs.getString("role_def"));
                
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
	
	//Added by Mohsin for getting User Role Mapping
	
	public AdministratorObject[] getUserRoleAssignment(String user){
		AdministratorObject mod[]=null;
		Connection conn=null;
		Statement stmt=null;
		//System.out.println("TO Populated vales in COMBO Box...");
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			
			ResultSet rs=null;
			
				rs=stmt.executeQuery("select * from UserRoleMapping where userid='"+user+"'");
			if(rs.next())
			{	
				rs.last();
				mod=new AdministratorObject[rs.getRow()];
				rs.beforeFirst();
			}
			int i=0;
			while(rs.next())
			{
				System.out.println("inside Admin bean--->>>getUserRoleAssignment");
				mod[i]=new AdministratorObject();
				mod[i].setRolecode(rs.getString("role_code"));
				
                
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
	
	
//Added by Mohsin for setting User Role Mapping
	
	public String setUserRoleAssignment(String user,AdministratorObject[] admObj){
		AdministratorObject mod[]=null;
		Connection conn=null;
		Statement stmt=null;
		PreparedStatement pstmt=null;
		//System.out.println("TO Populated vales in COMBO Box...");
		try{
			System.out.println("At 2630====>user==>>"+user);
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			
			ResultSet rs=null;
			stmt.executeUpdate("delete from UserRoleMapping where userid='"+user+"'");
			for(int j=0;j<admObj.length;j++)
			{
				pstmt=conn.prepareStatement("insert into UserRoleMapping values(?,?)");
				pstmt.setString(1,user);
				pstmt.setString(2,(String)admObj[j].getRolecode());
				
				int i=pstmt.executeUpdate();
				if(i==0)
				{
					return "Records Not Inserted";
				}
			}
			
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		//System.out.println("LENGTH: "+mod.length);
		return "Roles Assignment successfull";
	}
	
//Added by Mohsin for setting Role Access
	
	public String setFormwiseRoleAccess(AdministratorObject[] admObj,String rolecode,String formcode){
		AdministratorObject mod[]=null;
		Connection conn=null;
		Statement stmt=null;
		PreparedStatement pstmt=null;
		//System.out.println("TO Populated vales in COMBO Box...");
		try{
			System.out.println("At 2670====>setFormwiseRoleAccess==>>");
			if(admObj!=null&&admObj.length>0){
				
			System.out.println("At 2630====>admObj.length==>>"+admObj.length);
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			
			ResultSet rs=null;
			stmt.executeUpdate("delete from FormwiseAccess where role_code='"+rolecode+"' and formcode like '"+formcode+"%'");
			for(int j=0;j<admObj.length;j++)
			{
				pstmt=conn.prepareStatement("insert into FormwiseAccess values(?,?,?,?,?)");
				pstmt.setString(1,admObj[j].getRolecode());
				pstmt.setString(2,admObj[j].getFormcode());
				pstmt.setString(3,admObj[j].getAccess());
				pstmt.setString(4,admObj[j].getDe_user());
				pstmt.setString(5,admObj[j].getDe_date());
				
				int i=pstmt.executeUpdate();
				if(i==0)
				{
					return "Records Not Inserted";
				}
			}
			
		}//end of if	
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		//System.out.println("LENGTH: "+mod.length);
		return "Form Based Role Access successfully Assigned";
	}
	
//Added by Mohsin to get FormwiseAccessRights at the time of Login
	//to be done
	public  Map getUserLoginAccessRights(String user,String formcode)
	{
		AdministratorObject mod[]=null;
		Connection conn=null;
		Statement stmt=null,stmt1=null;
		Vector user_roles=new Vector();
		Map m=new TreeMap();
		
		//System.out.println("TO Populated vales in COMBO Box...");
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			ResultSet rs=null,rs1=null;
			user_roles.clear();
			m.clear();
				rs=stmt.executeQuery("select * from UserRoleMapping where userid='"+user+"'");
			if(rs.next())
			{	
				rs.last();
				mod=new AdministratorObject[rs.getRow()];
				rs.beforeFirst();
			}
			int i=0;
			while(rs.next())
			{
				rs1=stmt1.executeQuery("select * from FormwiseAccess fa,FormsDetail fd where fa.role_code='"+rs.getString("role_code")+"' and fa.formcode like '"+formcode+"%' and fa.formcode=fd.formcode order by fa.formcode");
				while(rs1.next()){
					System.out.println("inside Admin bean--->>>2735--");
					
					
					System.out.println("Creating map in all");
					m.put(rs1.getString("pageId"),rs1.getString("access"));
					
				
				
				}
				System.out.println("inside Admin bean--->>>getUserRoleAssignment"+rs.getString("role_code"));
				
				
                
				i++;
			}
			//user_roles.add(m);
			/*for(int j=0;j<m.size();j++){
				System.out.println("User_roles in bean ----->>"+m.get);
			}*/
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		//System.out.println("LENGTH: "+mod.length);
		return m;
		
	}
	/*public  Vector getUserLoginAccessRights(String user,String formcode)
	{
		AdministratorObject mod[]=null;
		Connection conn=null;
		Statement stmt=null,stmt1=null;
		Vector user_roles=new Vector();
		//System.out.println("TO Populated vales in COMBO Box...");
		try{
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			
			ResultSet rs=null,rs1=null;
			user_roles.clear();
				
			int i=0;
			
				rs=stmt.executeQuery("select * from FormwiseAccess fa,UserRoleMapping ur where fa.role_code=ur.role_code and fa.formcode like '"+formcode+"' and ur.userid='"+user+"'");
				while(rs.next()){
				user_roles.add(rs.getString("access"));
				}
				System.out.println("inside Admin bean--->>>getUserRoleAssignment"+rs.getString("role_code"));
				
				
                
				i++;
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		//System.out.println("LENGTH: "+mod.length);
		return user_roles;
		
	}*/
	
//Added by Mohsin for inserting FormsDetail....
	
	public String insertFormsDetails(AdministratorObject[] admObj,String formcode){
		AdministratorObject mod[]=null;
		Connection conn=null;
		Statement stmt=null;
		PreparedStatement pstmt=null;
		//System.out.println("TO Populated vales in COMBO Box...");
		try{
			System.out.println("At 2807====>user==>>"+formcode);
			conn=getConnection();	
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
			
			ResultSet rs=null;
			stmt.executeUpdate("delete from FormsDetail where formcode like '"+formcode+"%'");
			for(int j=0;j<admObj.length;j++)
			{
				pstmt=conn.prepareStatement("insert into FormsDetail values(?,?,?)");
				pstmt.setString(1,formcode);
				pstmt.setString(2,admObj[j].getFormname().toUpperCase());
				pstmt.setString(3,admObj[j].getPageId());
				int i=pstmt.executeUpdate();
				if(i==0)
				{
					return "Records Not Inserted";
				}
			}
			
			
		}catch(Exception exception){exception.printStackTrace();}
		finally{
			try{		    	
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		//System.out.println("LENGTH: "+mod.length);
		return "Forms Details Inserted Successfully";
	}
	
	public AdministratorObject[] getFormsDetail(String formcode)
    {
		AdministratorObject[] formsdetailsbean=null;
   	 Connection connection = null;
   	 Statement stmt=null;
   	 ResultSet rs=null;
   	 int i=0;
   	 try
        {
   		 connection=getConnection();
   		 stmt=connection.createStatement();
   		 System.out.println("the formcode in bean is---->"+formcode);
   		 rs=stmt.executeQuery("select * from FormsDetail where formcode like '"+formcode+"%'");
   		 
   			 if(rs.next())
   	            {
   	                rs.last();
   	                System.out.println("the number of rows--->"+rs.getRow());
   	                formsdetailsbean=new AdministratorObject[rs.getRow()];
   	                
   	                rs.beforeFirst();
   	            }
   			 while(rs.next())
   			 {
   				 formsdetailsbean[i]=new AdministratorObject();
   				 formsdetailsbean[i].setFormcode(rs.getString("formcode"));
   				 formsdetailsbean[i].setFormname(rs.getString("formname"));
   				 formsdetailsbean[i].setPageId(rs.getString("pageId"));
   				 i++;
   			 }
   		    /*for(int j=0;j<formsdetailsbean.length;j++){
   		    	System.out.println("resut in bean---->"+formsdetailsbean[j].getFormcode());
   		    }*/
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
           	 try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
   	 return formsdetailsbean;
    }
	
	public BranchObject[] getBranchMaster()throws RemoteException
	{
		BranchObject[] branch=null;
		Connection conn=null;
		Statement stm=null;
		ResultSet rs=null;
		try{
			conn=getConnection();
			stm=conn.createStatement();
			rs=stm.executeQuery("select * from BranchMaster");
			if(rs.next())
				rs.last();
				branch=new BranchObject[rs.getRow()];
				rs.beforeFirst();
				int i=0;
			while(rs.next()){
				branch[i]=new BranchObject();
				branch[i].setBranchCode(rs.getInt("br_code"));
				branch[i].setBranchName(rs.getString("br_name"));
				i++;
			}
				
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return branch;
		
	}
	
	
}