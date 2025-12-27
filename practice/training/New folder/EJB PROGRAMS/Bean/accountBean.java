import javax.ejb.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import java.io.*;


public class accountBean implements EntityBean
{
protected EntityContext ctx;

private String accountID;
private String ownerName;
private double balance;

public accountBean()
{
System.out.println("New Bank Account EntityBean java Object created by EJB Container");

}


public void deposit(double amount)
{
System.out.println("deposit amount="+amount);
balance +=amount;
}

public void withdraw(double amt)
{
System.out.println("withdrawal amount="+amt);
balance -=amt;
}

public double getBalance()
{
return balance;
}

public String getOwnerName()
{
return ownerName;
}

public void setOwnerName(String name)
{
ownerName=name;
}

public String getAccountID()
{
return accountID;
}

public void setAccountID(String id)
{
this.accountID=id;
}


public void ejbActivate()
{
System.out.println("ejbActivate called");
}

public void ejbPassivate()
{
System.out.println("ejbpassivate called");
}

public void setEntityContext(EntityContext ctx)
{
System.out.println("EntityContext called");
this.ctx=ctx;
}

public void unsetEntityContext()
{
System.out.println("unsetEntityContext called");
this.ctx=null;
}

public accountPK ejbCreate(String accountID,String ownerName)throws CreateException
{
this.accountID=accountID;
this.ownerName=ownerName;
this.balance=0;
try
{

Class.forName("oracle.jdbc.driver.OracleDriver");
Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:abc","scott","tiger");
PreparedStatement pst=con.prepareStatement("insert into accounts (id,ownerName,balance)"+"values(?,?,?)");
pst.setString(1,accountID);
pst.setString(2,ownerName);
pst.setDouble(3,balance);
pst.executeUpdate();

}
catch(Exception e)
{
System.out.println("ejbCreatemethod:"+e);
}
return new accountPK(accountID);
}

public void ejbPostCreate(String accountID,String ownerName)
{
System.out.println("ejbpostcreatemethod called");
}

public accountPK ejbFindByPrimaryKey(accountPK key)throws FinderException
{
Connection con=null;
PreparedStatement pst=null;
try
{
Class.forName("oracle.jdbc.driver.OracleDriver");
con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:abc","scott","tiger");
pst=con.prepareStatement("select id from accounts where id=?");
pst.setString(1,key.toString());
ResultSet rs=pst.executeQuery();
rs.next();
//return key;
}
catch(Exception e)
{
System.out.println("ejbFindByPrimeryKey()method:"+e);
}
return key;
}

public void ejbLoad()
{
System.out.println("ejbLoad called");

accountPK pk=(accountPK)ctx.getPrimaryKey();
String id=pk.accountID;
try
{
Class.forName("oracle.jdbc.driver.OracleDriver");
Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:abc","scott","tiger");
PreparedStatement pst=con.prepareStatement("select ownerName,balance from accounts where id=?");
pst.setString(1,id);
ResultSet rs=pst.executeQuery();
rs.next();
ownerName=rs.getString("ownerName");
balance=rs.getDouble("balance");
}
catch(Exception e)
{
System.out.println("ejbLoad():"+e);
}
}

public void ejbStore()
{
System.out.println("ejbstore called");
try
{
Class.forName("oracle.jdbc.driver.OracleDriver");
Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:abc","scott","tiger");
PreparedStatement pst=con.prepareStatement("update accounts set ownerName=?,balance=? where id=?");
pst.setString(1,ownerName);
pst.setDouble(2,balance);
pst.setString(3,accountID);
pst.executeUpdate();
}
catch(Exception e)
{
System.out.println("ejbStore:"+e);
}

}

public void ejbRemove()
{
System.out.println("ejbremove called");
accountPK pk=(accountPK)ctx.getPrimaryKey();
String id=pk.accountID;
try
{
Class.forName("oracle.jdbc.driver.OracleDriver");
Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:abc","scott","tiger");
PreparedStatement pst=con.prepareStatement("delete from accounts where id=?");
pst.setString(1,id);
pst.executeUpdate();
}
catch(Exception e)
{
System.out.println("ejbrmove"+e);
}
}
}