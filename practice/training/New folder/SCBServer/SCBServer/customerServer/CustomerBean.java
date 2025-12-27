package customerServer;
import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.Calendar;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import client.HomeFactory;

import masterObject.customer.CustomerMasterObject;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import masterObject.general.Address;
import general.Validations;
import generalLedgerServer.GLLocal;
import generalLedgerServer.GLLocalHome;
import generalLedgerServer.GLRemote;

public class CustomerBean implements SessionBean {

	javax.sql.DataSource ds=null;
	SessionContext ctx=null;
 

	public CustomerBean()   
	{
		try{
		Context ctx=new InitialContext();
		ds=(javax.sql.DataSource)ctx.lookup("java:MySqlDS");
		}catch(Exception ex){ex.printStackTrace();}

	}

	public void setSessionContext(SessionContext arg0) throws EJBException
	{
		ctx=arg0;
	}
	public void ejbCreate(){
        try{
		Context ctx=new InitialContext();
		ds=(javax.sql.DataSource)ctx.lookup("java:MySqlDS");
		}catch(Exception ex){ex.printStackTrace();}
    }
	public void ejbRemove() throws EJBException{}
	public void ejbActivate() throws EJBException{}
	public void ejbPassivate() throws EJBException
	{

	}

	public int confirmCustomerId(String cid)
	{
		Connection c=null;
		try{
			c=getConnection();
			Statement stmt=c.createStatement();

		ResultSet rs=stmt.executeQuery("select * from CustomerMaster where cid='"+cid+"'");
		if(rs.next())
			return 1;
        return -1;
		}catch(Exception ex){ex.printStackTrace();}
		finally{
			try{
			c.close();
			}catch(Exception ex){ex.printStackTrace();}
		}

		return -1;
	}

	public String[] getIndivisual_Institute(String str1)
	{
		Connection conn=null;
		String[] str=null;
		ResultSet rs=null;
		Statement stmt=null;
		int i=0;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			if(str1.equalsIgnoreCase("CustomerType"))
			{
				rs=stmt.executeQuery("select * from CustomerType where custcode in(0,1)");
				if(rs.next())
				{
					rs.last();
					str=new String[rs.getRow()];
					rs.beforeFirst();
				}
				while(rs.next())
				{
					str[i]=rs.getString("custype");
					i++;
				}
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception x)
			{
				x.printStackTrace();
			}
		}
		return str;
	}

	public String[] getCustomerParams(String str1)
	{

		String str[]=null;
		ResultSet rs=null,rs_code=null;
		Connection c=null;
		String[] return_values = null;
		try{
			c=getConnection();
			Statement stmt=c.createStatement();
			Statement stmt_code=c.createStatement();
			StringTokenizer st=new StringTokenizer(str1,"$$$");

			String s=st.nextToken();
			System.out.println("st====="+s);
			int flag=0;

			if(str1.equals("Country")|| str1.equals("AddrTypes"))
				flag=1;

			if(st.hasMoreElements())
			{
			    String s1=st.nextToken();
			    System.out.println("s1 ======"+s1);

/*//				rs=stmt.executeQuery("select state from State,Country where State.code=Country.code and country='"+st.nextToken()+"'");
				rs=stmt.executeQuery("select state from State,Country where State.code=Country.code and country='"+s1+"'");*/

			    if(s.equalsIgnoreCase("AccountSubCategory"))
			    {
			    	rs_code=stmt_code.executeQuery("select * from CustomerType where custype='"+s1+"'");
			    	if(rs_code.next())
			    		rs=stmt.executeQuery("select subcatdesc from AccountSubCategory where catcode="+rs_code.getInt("custcode")+" ");
			    }
			    else
			    	rs=stmt.executeQuery("select state from State,Country where State.code=Country.code and country='"+s1+"'");

			}

			else
				rs=stmt.executeQuery("select * from " +str1);

			rs.last();
			str=new String[rs.getRow()];
			rs.beforeFirst();

			int i=0;
			while(rs.next())
			{
				if(str1.equals("CustomerParameters"))
					str[i++]=rs.getString(1)+"$$$"+rs.getString(2);
				else if(flag==1)
					str[i++]=rs.getString(1)+"$$$"+rs.getString(2);
				else
					str[i++]=rs.getString(1);
			}

			int count = 0;

			for(int k = 0; k< str.length; k++) {
				if(str[k] != null && str[k].trim().length() > 0) {
					count++;
				}
			}

			return_values = new String[count];

			count = 0;
			for(int k = 0; k< str.length; k++) {
				if(str[k] != null && str[k].trim().length() > 0) {
					return_values[count++] = str[k];
				}
			}

		}catch(Exception ex){ex.printStackTrace();}
		finally{
			try{
			c.close();
			}catch(Exception ex){ex.printStackTrace();}
		}
		return return_values;
	}

	public CustomerMasterObject getCustomer(int cid) throws CustomerNotFoundException
	{
		
		CustomerMasterObject cm=null;
		Connection conn=null;
		try{         
			System.out.println("1...------>"+cid);
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select * from CustomerMaster where cid="+cid+" ");
			if(rs.next())
			{				cm=new CustomerMasterObject();
				cm.setCustomerID(rs.getInt("cid"));
				cm.setCategory(rs.getInt("custtype"));
				cm.setIntroducerId(rs.getString("introid"));
				cm.setFirstName(rs.getString("fname"));
				System.out.println("First Name"+rs.getString("fname"));
				System.out.println("Middle Name"+rs.getString("mname"));
				System.out.println("Last Name"+rs.getString("lname"));
				cm.setMiddleName((rs.getString("mname")!=null?rs.getString("mname"):" "));
				cm.setLastName((rs.getString("lname")!=null?rs.getString("lname"):" "));
				cm.setSalute(rs.getString("salute"));
				cm.setDOB(rs.getString("dob"));
				cm.setSex(rs.getString("sex"));
				cm.setMaritalStatus(rs.getString("marital"));
				cm.setPhoto(rs.getString("photo"));
				cm.setSubCategory(rs.getInt("sub_category"));
				cm.setFatherName(rs.getString("fathername"));
				cm.setMotherName(rs.getString("mothername"));
				cm.setSpouseName(rs.getString("spousename"));

				Blob b=rs.getBlob("photo");
				Blob s=rs.getBlob("sign");
				if(b!=null)
					cm.setBinaryImage( b.getBytes(1,(int)b.length()));
				else
					cm.setBinaryImage(null);
				if(s!=null)
					cm.setBinarySignImage( s.getBytes(1,(int)s.length()));
				else
					cm.setBinarySignImage(null);


				cm.setNationality(rs.getString("nationality"));
				cm.setReligion(rs.getString("religion"));
				cm.setCaste(rs.getString("caste"));
				cm.setAddressProof(rs.getString("addrproof"));
				cm.setNameProof(rs.getString("nameproof"));
				cm.setSubOccupation(rs.getString("suboccupation"));
				cm.setPanno(rs.getString("panno"));
				cm.setOccupation(rs.getString("occupation"));
				cm.setScSt(rs.getString("scst"));
				cm.setPassPortNumber(rs.getString("passportno"));
				cm.setPPIssueDate(rs.getString("dateofissue"));
				cm.setPPExpiryDate(rs.getString("dateofexpiry"));

				cm.setGuardType(rs.getString("guardiantype"));
				cm.setGuardName(rs.getString("guardianname"));
				cm.setGuardSalute(rs.getString("guardiansalute"));
				cm.setGuardSex(rs.getString("guardiansex"));
				cm.setGuardRelationship(rs.getString("guardianrelation"));
				cm.setGuardAddress(rs.getString("guardianaddress"));
				cm.setCourtDate(rs.getString("courtdate"));

				cm.uv.setUserId(rs.getString("de_user"));
				cm.uv.setUserTml(rs.getString("de_tml"));
				cm.uv.setUserDate(rs.getString("de_date"));
				cm.uv.setVerId(rs.getString("ve_user"));
				cm.uv.setVerTml(rs.getString("ve_tml"));
				cm.uv.setVerDate(rs.getString("ve_date"));

				java.util.HashMap address = getAddress(cid);
				  cm.setAddress(address);
			 }
			else
				return null;

	      }catch(SQLException e){e.printStackTrace();}
	        finally{
	        	try{
	        	conn.close();
	        	}catch(Exception ex){ex.printStackTrace();}
	        }
		return(cm);
	}

	public String getCustomerAddress(int cid)throws RemoteException,CustomerNotFoundException,SQLException
	{
		String addr=null;
		
		Connection conn=null;
		conn = getConnection();
		
		Statement stmt=conn.createStatement();

		
		
		try{
			
			ResultSet rs1=stmt.executeQuery("select  address from CustomerAddr where cid="+cid+" ");
			
		
			while(rs1.next())
			{
				
				addr=rs1.getString(1);
				
				
				
			}
		
			
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return addr;
	
		
		
	}
	
	 public String getBOD(int cid) throws RemoteException,CustomerNotFoundException,SQLException 
	  {
			String bod=null;
			
			Connection conn=null;
			conn = getConnection();
			System.out.println("Connection is Openend !!!!!!!!!");
			Statement stmt=conn.createStatement();

			System.out.println("Statement Executed !!!!!!!!!!!!!!!!!");
			
			
			try{
				
				ResultSet rs1=stmt.executeQuery("select  dob from CustomerMaster where cid="+cid+" ");
				
			
				while(rs1.next())
				{
					
					bod=rs1.getString(1);
					
					System.out.println("The b'date here is -----"+bod);
					
				}
			
				
			}catch(Exception exception)
			{
				exception.printStackTrace();
			}
			finally
			{
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			
			return bod;
	 }
	 
	 public int[] getCustomerAddrType(int cid) throws RemoteException,SQLException{
		 int[] addr_type=new int[0];
		 Connection conn=null;
		 conn = getConnection();
		 Statement stmt=conn.createStatement();
		 try{
				ResultSet rs1=stmt.executeQuery("select distinct addr_type from CustomerAddr where cid="+cid+" ");
				if(rs1.last()){
					addr_type=new int[rs1.getRow()];
					rs1.beforeFirst();
				}
				int i=0;
				rs1.beforeFirst();
				while(rs1.next())
				{
					addr_type[i]=rs1.getInt("addr_type");
					i++;
				}
				rs1.beforeFirst();
				if(!rs1.next()){
					addr_type=new int[1];
					addr_type[0]=0;
					return addr_type;
				}
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		 return addr_type;
	 }
	 
	public String getCustomerType(int cid) throws RemoteException,SQLException,CustomerNotFoundException
	{
     String addr_type=null;
     String addrType=null;
		Connection conn=null;
		conn = getConnection();
		Statement stmt=conn.createStatement();
		try{
			ResultSet rs1=stmt.executeQuery("select addr_type  from CustomerAddr where cid="+cid+" ");
			while(rs1.next())
			{
				addr_type=rs1.getString(1);
			}
			ResultSet rs2=stmt.executeQuery("select addr_type from AddrTypes where number="+addr_type+"");
		while(rs2.next())
		{
			addrType=rs2.getString(1);
		
		}
			
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return addrType;
	
		
		
	}
	
	
	
    /**
     * @param cid
     * @return
     * @throws CustomerNotFoundException
     */
    public java.util.HashMap getAddress(int cid) throws CustomerNotFoundException
    {
        Connection conn = null;
        java.util.HashMap address=new java.util.HashMap();
        Address[] addr = null;

        try
        {
            conn = getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs1=stmt.executeQuery("select addr_type,address,city,state,country,pin,phno,phstd,fax,faxstd,mobile,email from CustomerAddr where cid='"+cid+"' ");

            if(rs1.last())
            {
                addr = new Address[rs1.getRow()];
                rs1.beforeFirst();
            }

            int i=0;
            rs1.beforeFirst();
            while(rs1.next())
            {
            	System.out.println("i am inside bean");
            	addr[i] = new Address();

                addr[i].setFlag(rs1.getInt("addr_type"));
                addr[i].setAddress(rs1.getString("address"));
                addr[i].setCity(rs1.getString("city"));
                addr[i].setState(rs1.getString("state"));
                addr[i].setCountry(rs1.getString("country"));
                addr[i].setPin(rs1.getString("pin"));
                addr[i].setPhno(rs1.getString("phno"));
                addr[i].setPhStd(rs1.getString("phstd"));
                addr[i].setFax(rs1.getString("fax"));
                addr[i].setFaxStd(rs1.getString("faxstd"));
                addr[i].setMobile(rs1.getString("mobile"));
                addr[i].setEmail(rs1.getString("email"));

            	address.put(new Integer(addr[i].getFlag()),addr[i]);

            	i++;
            }

            return address;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(conn!=null)

            try
            {
                conn.close();
            }
            catch (SQLException e1)
            {
                e1.printStackTrace();
            }
        }

        throw new CustomerNotFoundException();
    }

	public String[] getCustomerIds(String cum_name)
	{
		String str[]=null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			//ResultSet rs=stmt.executeQuery("select concat(ifnull(fname,''),' ',ifnull(mname,''),' ',ifnull(lname,'')),cid from CustomerMaster  where concat(ifnull(fname,''),' ',ifnull(mname,''),' ',ifnull(lname,'')) LIKE '"+cum_name+"%' ");
			ResultSet rs=stmt.executeQuery("select concat(ifnull(fname,''),' ',ifnull(mname,''),' ',ifnull(lname,'')),cid from CustomerMaster  where concat(ifnull(fname,''),' ',ifnull(mname,''),' ',ifnull(lname,'')) LIKE '"+cum_name+"%' order by fname,mname,lname");
			rs.last();
			str=new String[rs.getRow()];
			rs.beforeFirst();

			int i=0;
			while(rs.next())
				str[i++]=rs.getString(1)+"$$"+rs.getString(2);
		}catch(Exception ex){ex.printStackTrace();}
		 finally{
		 	try{
		 	conn.close();
		 	}catch(Exception ex){ex.printStackTrace();}
		 }
		return str;
	}


	public int storeCustomerParams(String str,String arr)
	{
		int a=0;
		Connection conn=null;
		ResultSet rs_code=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement stmt_code=conn.createStatement();
			Statement stmt_custcode=conn.createStatement();
			System.out.println("table name===="+str);
			//System.out.println("insert into "+str+" values("+arr+")");
			if(str.equalsIgnoreCase("CustomerType"))
			{
				rs_code=stmt_code.executeQuery("select max(custcode) as custcode from CustomerType");
				if(rs_code.next())
					a=stmt.executeUpdate("insert into "+str+" values("+arr+","+(rs_code.getInt("custcode")+1)+")");
			}
			else if(str.equalsIgnoreCase("AddrTypes"))
			{
				rs_code=stmt_code.executeQuery("select max(number) as number from AddrTypes");
				if(rs_code.next())
					a=stmt.executeUpdate("insert into "+str+" values("+(rs_code.getInt("number")+1)+","+arr+")");
			}
			else if(str.equalsIgnoreCase("Country"))
			{
				rs_code=stmt_code.executeQuery("select max(code) as code from Country");
				if(rs_code.next())
					a=stmt.executeUpdate("insert into "+str+" values("+arr+","+(rs_code.getInt("code")+1)+")");
			}
			else if(str.equalsIgnoreCase("AccountSubCategory"))
			{
			   	System.out.println("print array====="+arr);
			   	StringTokenizer st=new StringTokenizer(arr,",");
			   	String cust_type=st.nextToken();
			   	String cat_desc=st.nextToken();
			   	int age=Integer.parseInt(st.nextToken());
			   	ResultSet rs_custcode=stmt_custcode.executeQuery("select custcode from CustomerType where custype='"+cust_type+"'");
			   	rs_custcode.next();
			   	rs_code=stmt_code.executeQuery("select max(subcatcode) as subcatcode from AccountSubCategory ac,CustomerType ct where ct.custcode=ac.catcode and ac.catcode="+rs_custcode.getInt("custcode")+" ");
			   	if(rs_code.next())
			   		a=stmt.executeUpdate("insert into "+str+" values ("+(rs_code.getInt("subcatcode")+1)+",'"+cat_desc+"',"+rs_custcode.getInt("custcode")+","+age+")");

			}
			else
				a=stmt.executeUpdate("insert into "+str+" values("+arr+")");

			//PreparedStatement ps=conn.prepareStatement("insert into "+str+" values("+")");
			//ps.setString(1,arr);
			//a=ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}

		}
		return a;
	}
 
	
	
	public String getCustomerName(String cid)
	{
		String str=null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();


			System.out.println("Within get customer name");
			ResultSet rs=stmt.executeQuery("select concat(IFNULL(fname,' '),' ',IFNULL(mname,' '),' ',IFNULL(lname,' ')) from CustomerMaster  where  cid='"+cid+"' and ve_user is not null");
			while(rs.next())
				str=rs.getString(1);

		}catch(Exception e){e.printStackTrace();}
		finally
		{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}

		}

		return str;
	}

	public boolean checkAvailability(String table,String col,String value)
	{
	    System.out.println("table == "+table);
	    System.out.println(" col== "+col);
	    System.out.println("value == "+value);

		Connection conn=null;
		ResultSet rs=null,rs_number=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement stmt_number=conn.createStatement();
			//ResultSet rs=stmt.executeQuery("select "+col+" from "+table+" where "+col+"='"+value+"'");
			if((table.equalsIgnoreCase("State")) || (table.equalsIgnoreCase("Country")) || (table.equalsIgnoreCase("AddrTypes")))
			{
				if(table.equalsIgnoreCase("AddrTypes"))
				{
					rs_number=stmt_number.executeQuery("select number from AddrTypes where addr_type='"+value+"'");
					if(rs_number.next())
						rs=stmt.executeQuery("select "+col+" from CustomerAddr where "+col+"='"+rs_number.getInt("number")+"'");
				}
				else
					rs=stmt.executeQuery("select "+col+" from CustomerAddr where " +col+"='"+value+"'");
			}
			else if(table.equalsIgnoreCase("CustomerType"))
			{
				rs_number=stmt_number.executeQuery("select custcode from CustomerType where custype='"+value+"'");
				if(rs_number.next())
					rs=stmt.executeQuery("select "+col+" from CustomerMaster where " +col+"='"+rs_number.getInt("custcode")+"'");
			}
			else if(table.equalsIgnoreCase("AccountSubCategory"))
			{
				rs_number=stmt_number.executeQuery("select subcatcode from AccountSubCategory where subcatdesc='"+value+"'");
				if(rs_number.next())
					rs=stmt.executeQuery("select "+col+" from CustomerMaster where "+col+"="+rs_number.getInt("subcatcode")+" ");
			}
			else
				rs=stmt.executeQuery("select "+col+" from CustomerMaster where " +col+"='"+value+"'");

			if(rs.next())
				return true;
          return false;
		}catch(Exception e){e.printStackTrace();}
		finally
		{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}

		}

		return false;
	}

	public int removeData(String table,String col,String value)
	{
		ResultSet rs=null;

		System.out.println("in removedata");
		System.out.println("table "+table);
		System.out.println("col "+col);
		System.out.println("value "+value);
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			if(table.equalsIgnoreCase("AddrTypes"))
			{
				rs=stmt.executeQuery("select number from AddrTypes where addr_type='"+value+"'");
				if(rs.next())
					return(stmt.executeUpdate("delete from "+table+" where " +col+"="+rs.getInt("number")+""));
			}
			else if(table.equalsIgnoreCase("AccountSubCategory"))
			{
				return(stmt.executeUpdate("delete from  "+table+" where subcatdesc='"+value+"'"));
			}
			else
               	return(stmt.executeUpdate("delete from "+table+" where " +col+"='"+value+"'"));
		}catch(Exception e){e.printStackTrace();}
		finally
		{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}

		}

		return (0);
	}


	public int storeCustomer(CustomerMasterObject cm)
	{
		Connection conn=null; 
		String[][] br_code=null;
		try{
			conn=getConnection();
			GLLocalHome gllocalhome;
			//generalLedgerServer.GLHome home=(generalLedgerServer.GLHome)HomeFactory.getFactory().lookUpHome("GLWEB");
			Context jndiContext = new InitialContext();
			gllocalhome=(GLLocalHome)jndiContext.lookup("GLLOCALWEB");
			GLLocal gllocal=gllocalhome.create();
			
			Statement stmt=conn.createStatement();

			String vid=null,vtml=null,vdate=null; 

			PreparedStatement pstmt=null;
			br_code=gllocal.getBranchCode(cm.getBranchCode());
			
			if(cm.getCustomerID()==0)
			{
				ResultSet rs=stmt.executeQuery("select max_renewal_count from Modules where modulecode like '1019000'");
				if(rs.next()) {
				   cm.setCustomerID(rs.getInt(1)+1);
				}

				rs.close();
				stmt.executeUpdate("update Modules set max_renewal_count=max_renewal_count+1 where modulecode like '1019000'");
			}
			else
			{
				if(cm.getOperation()!=1 && cm.getOperation()!=3)
				{
					ResultSet rs=stmt.executeQuery("select ve_user,ve_tml,ve_date from CustomerMaster where cid='"+cm.getCustomerID()+"'");
					if(rs.next())
					{
					vid=rs.getString(1);
					vtml=rs.getString(2);
					vdate=rs.getString(3);
					}
					stmt.executeUpdate("delete from CustomerMaster where cid='"+cm.getCustomerID()+"'");
					//stmt.executeUpdate("delete from CustomerMasterLog where cid='"+cm.getCustomerID()+"'");
				}
			}

			//1 means creating new customer in which we shouldn't insert into viewlog
			//2 means only ammendments in customer master
			//3 means only ammendments in customer address
			//4 means ammendments in customer master and address

			if(cm.getOperation()==1 ||cm.getOperation()==2 ||cm.getOperation()==4)
			{

				System.out.println("The Religion Value BEfore Inserting=====> "+cm.getReligion());
				
				pstmt=conn.prepareStatement("insert into CustomerMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmt.setString(1,br_code[0][0]);//Branch code
				pstmt.setInt(2,cm.getCustomerID());
				pstmt.setInt(3,cm.getCategory()); 
				System.out.println("inside customer Bean category====="+cm.getCategory());
				pstmt.setString(4,cm.getIntroducerId());
				pstmt.setString(5,cm.getFirstName().trim().toUpperCase());
				pstmt.setString(6,cm.getMiddleName().trim().toUpperCase());
				System.out.println("middle name in customer bean========="+cm.getMiddleName());
				System.out.println("mother name in customer bean========="+cm.getMotherName());
				if(cm.getLastName()!=null) 
				pstmt.setString(7,cm.getLastName().trim().toUpperCase());
				else
					pstmt.setString(7,null);
				pstmt.setString(8,cm.getSalute());
				if(cm.getMotherName()!=null)
							
				pstmt.setString(9,cm.getMotherName().trim().toUpperCase());
				
				else
					pstmt.setString(9,null);

				if(cm.getFatherName()!=null)
					pstmt.setString(10,cm.getFatherName().trim().toUpperCase());
					else
						pstmt.setString(10,null);

				if(cm.getSpouseName()!=null)
					pstmt.setString(11,cm.getSpouseName().trim().toUpperCase());
					else
						pstmt.setString(11,null);

				if(cm.getDOB()!=null)
					pstmt.setString(12,cm.getDOB().trim().toUpperCase());
				else
					pstmt.setString(12,null);

				if(cm.getSex()!=null)
					pstmt.setString(13,cm.getSex().trim().toUpperCase());
					else
						pstmt.setString(13,null);

				if(cm.getMaritalStatus()!=null)
					pstmt.setString(14,cm.getMaritalStatus().trim());
					else
						pstmt.setString(14,null);



				//pstmt.setString(10,cm.getFatherName().trim().toUpperCase());

				//pstmt.setString(11,cm.getSpouseName().trim().toUpperCase());
				//pstmt.setString(12,cm.getDOB());
				//pstmt.setString(13,cm.getSex());
				//pstmt.setString(14,cm.getMaritalStatus());
				pstmt.setInt(15,cm.getSubCategory());

				if(cm.getBinaryImage()!=null)
				{
					byte image[]=cm.getBinaryImage();
					ByteArrayInputStream bin=new ByteArrayInputStream(image);
					pstmt.setBinaryStream(16,bin,image.length);
				}
				else
					pstmt.setBinaryStream(16,null,0);

				if(cm.getBinarySignImage()!=null)
				{
					byte image[]=cm.getBinarySignImage();
					ByteArrayInputStream bin=new ByteArrayInputStream(image);
					pstmt.setBinaryStream(17,bin,image.length);
				}
				else
					pstmt.setBinaryStream(17,null,0);

				if(cm.getNameProof()!=null)
					pstmt.setString(18,cm.getNameProof().trim());
					else
						pstmt.setString(18,null);

				if(cm.getAddressProof()!=null)
					pstmt.setString(19,cm.getAddressProof().trim());
					else
						pstmt.setString(19,null);
				if(cm.getSubOccupation()!=null)
					pstmt.setString(22,cm.getSubOccupation().trim());
					else
						pstmt.setString(22,null);


				if(cm.getScSt()!=null)
					pstmt.setString(23,cm.getScSt().trim().toUpperCase());
					else
						pstmt.setString(23,null);


				//pstmt.setString(18,cm.getNameProof());
				//pstmt.setString(19,cm.getAddressProof());
				pstmt.setString(20,cm.getPanno());
				pstmt.setString(21,cm.getOccupation());
				//pstmt.setString(22,cm.getSubOccupation());
				//pstmt.setString(23,cm.getScSt());
				if(cm.getCaste()!=null)
					cm.setCaste(cm.getCaste().toUpperCase());

				pstmt.setString(24,cm.getCaste());
				if(cm.getNationality()!=null)
					cm.setNationality(cm.getNationality().toUpperCase());
				if(cm.getReligion()!=null)
					cm.setReligion(cm.getReligion().toUpperCase());
				pstmt.setString(25,cm.getNationality());
				pstmt.setString(26,cm.getReligion());
				pstmt.setString(27,cm.getPassPortNumber());
				pstmt.setString(28,cm.getPPIssueDate());
				pstmt.setString(29,cm.getPPExpiryDate());

				pstmt.setString(30,cm.getGuardType());
				if(cm.getGuardName()!=null)
				{
					cm.setGuardName(cm.getGuardName().trim().toUpperCase());
					cm.setGuardRelationship(cm.getGuardRelationship().trim().toUpperCase());
					cm.setGuardAddress(cm.getGuardAddress().trim().toUpperCase());
				}

				pstmt.setString(31,cm.getGuardName());
				pstmt.setString(32,cm.getGuardSalute());

				pstmt.setString(33,cm.getGuardSex());
				pstmt.setString(34,cm.getGuardRelationship());
				pstmt.setString(35,cm.getGuardAddress());

				pstmt.setString(36,cm.getCourtDate());
				pstmt.setString(37,cm.uv.getUserId().trim().toUpperCase());
				pstmt.setString(38,cm.uv.getUserTml().trim().toUpperCase());
				pstmt.setString(39,getSysDate()+" "+getSysTime());
				if(vid!=null)
				{
					vid=vid.trim().toUpperCase();
					vtml=vtml.trim().toUpperCase();
				}
				pstmt.setString(40,vid);
				pstmt.setString(41,vtml);
				pstmt.setString(42,vdate);
				pstmt.executeUpdate();

				if(cm.getOperation()!=1)
					stmt.executeUpdate("insert into CustomerMasterLog   select * from CustomerMaster where cid="+cm.getCustomerID());
			}


			if(cm.getOperation()==3 || cm.getOperation()==4 || cm.getOperation()==1)
			{ 
				System.out.println("***************inside customer bean in side operation=1*************");
				Iterator iterator=cm.getAddress().values().iterator(); 
				System.out.println("address value in customer bean==================" +cm.getAddress());
				while(iterator.hasNext())
				{
					Address addr=(Address)iterator.next();
					System.out.println("Addrss bit"+addr.isChanged());
					if(addr.isChanged() )
					{
						System.out.println("inside addr if changed"); 
							stmt.executeUpdate("delete from CustomerAddr where cid='"+cm.getCustomerID()+"' and addr_type='"+addr.getFlag()+"'");

							PreparedStatement ps=conn.prepareStatement("insert into CustomerAddr values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

							/*ps.setInt(1,cm.getCustomerID());
							ps.setInt(2,addr.getFlag());
							ps.setString(3,getSysDate());
							ps.setString(4,addr.getAddress().trim());
							ps.setString(5,addr.getCity().trim());
							ps.setString(6,addr.getState().trim());
							ps.setString(7,addr.getPin());
							ps.setString(8,addr.getCountry().trim());
							ps.setString(9,addr.getPhno());
							ps.setString(10,addr.getPhStd());
							ps.setString(11,addr.getFax());
							ps.setString(12,addr.getFaxStd());
							System.out.println("mobile no is "+addr.getMobile());
							ps.setString(13,addr.getMobile());
							ps.setString(14,addr.getEmail());
							ps.setString(15,cm.uv.getUserId().trim().toUpperCase());
							ps.setString(16,cm.uv.getUserTml().trim().toUpperCase());
							ps.setString(17,getSysDate()+" "+getSysTime());
							ps.executeUpdate();*/
							
							ps.setInt(1,cm.getCustomerID());
							ps.setInt(2,addr.getFlag());
							ps.setString(3,getSysDate());
							ps.setString(4,addr.getAddress().trim());
							ps.setString(5,addr.getCity().trim());
							ps.setString(6,addr.getState().trim());
							if(addr.getPin()==null||addr.getPin().trim().length()==0)
							ps.setInt(7,0);
							else
								ps.setInt(7,Integer.parseInt(addr.getPin().trim()));
							ps.setString(8,addr.getCountry().trim());
							
							if(addr.getPhno()==null||addr.getPhno().trim().length()==0)
							ps.setInt(9,0);
							else
								ps.setInt(9,Integer.parseInt(addr.getPhno().trim()));
							
							if(addr.getPhStd()==null||addr.getPhStd()!=null&&addr.getPhStd().trim().length()==0)
							ps.setInt(10,0);
							else
								ps.setInt(10,Integer.parseInt(addr.getPhStd().trim()));
							
							if(addr.getFax()==null||addr.getFax().trim().length()==0)
							ps.setInt(11,0);
							else
								ps.setInt(11,Integer.parseInt(addr.getFax().trim()));
							
							if(addr.getFaxStd()==null||addr.getFaxStd().trim().length()==0)
							ps.setInt(12,0);
							else
								ps.setInt(12,Integer.parseInt(addr.getFaxStd().trim()));
							
							//System.out.println("mobile no is "+addr.getMobile());
							if(addr.getMobile()==null&&addr.getMobile().trim().length()==0)
							ps.setString(13,"0");
							else
								ps.setString(13,addr.getMobile().trim());
							
							
							ps.setString(14,addr.getEmail());
							ps.setString(15,cm.uv.getUserId().trim().toUpperCase());
							ps.setString(16,cm.uv.getUserTml().trim().toUpperCase());
							ps.setString(17,getSysDate()+" "+getSysTime());							
							ps.executeUpdate();
				

							if(cm.getOperation()!=1)
								stmt.executeUpdate("insert into CustomerAddrLog select * from CustomerAddr where cid='"+cm.getCustomerID() +"' and addr_type="+addr.getFlag());
					}
				}//while iterator
		}//if operation
		return (cm.getCustomerID());
		}catch(Exception e){
			System.out.println("Before setting context rollback exception");
			e.printStackTrace();
			try{
				ctx.setRollbackOnly();
				throw new EJBException(e);
			}catch(Exception ex){ex.printStackTrace();}
		}
		finally
		{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}
		return (0);
	}

	public int verifyCustomer(int cid,String uid,String tml)
	{
		int a=0;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			a=stmt.executeUpdate("update CustomerMaster set ve_user='"+uid+"',ve_tml='"+tml+"',ve_date='"+getSysDate()+" "+getSysTime()+"' where cid="+cid);
			//stmt.executeUpdate("delete from CustomerMasterLog where cid='"+cid+"'");
			stmt.executeUpdate("insert into CustomerMasterLog   select * from CustomerMaster where cid="+cid);
			stmt.executeUpdate("delete from CustomerAddrLog where cid='"+cid+"'");
			stmt.executeUpdate("insert into CustomerAddrLog select * from CustomerAddr where cid="+cid);
			}catch(Exception e){
				e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e1) {	e1.printStackTrace();}
		}
		return a;
	}
	
//added by swetha
	
	public CustomerMasterObject customer_verify(int cid)
	{
		String str=null;
		Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		CustomerMasterObject custmastobj=null;
		try{
			conn=getConnection();
			 stmt=conn.createStatement();
			rs=stmt.executeQuery("select ve_user, ve_tml from CustomerMaster where cid="+cid+"");
			
			while(rs.next()){
				custmastobj=new CustomerMasterObject();
				custmastobj.uv.setVerId(rs.getString("ve_user"));
				custmastobj.uv.setVerTml(rs.getString("ve_tml"));
				 
			}
			 
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e1) {	e1.printStackTrace();}
		}
		
		return custmastobj;
	}
	

	public int deleteCustomer(int cid)
	{
		int a=0;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();


			a=stmt.executeUpdate("delete from CustomerMaster where cid='"+cid+"'");
			if(a==1)
				stmt.executeUpdate("delete from CustomerAddr where cid='"+cid+"'");

		}catch(Exception e){e.printStackTrace();}
		finally
		{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}

		}
		return a;
	}
	public String[] getStateNames(int index)
	{
	    Connection conn=null;
	    ResultSet rs_state=null;
	    Statement stmt_state=null;
	    String[] states=null;
        int i=0,code=0;

	    try
	    {
	        conn=getConnection();
	        stmt_state=conn.createStatement();
	        code=index+1;
	        rs_state=stmt_state.executeQuery("select * from State,Country where State.code="+code+" and  State.code=Country.code");

	        if(rs_state.next())
	        {
	            rs_state.last();
	            states=new String[rs_state.getRow()];
	            rs_state.beforeFirst();
	        }
	        while(rs_state.next())
	        {
	            states[i]=new String();
	            states[i]=rs_state.getString("state");
	            i++;

	        }
	    }
	    catch(Exception ex){ex.printStackTrace();}
	    finally{
	        try
	        {
	            conn.close();
	        }
	        catch(Exception ex){ex.printStackTrace();}
	    }
	   // System.out.println("states length==="+states.length);

	    return states;
	}

	private Connection getConnection() throws SQLException
	{
		Connection con = ds.getConnection("root","");
		if(con == null)
		    throw new SQLException();
		return con;
	}

	public String getSysDate()
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

}
