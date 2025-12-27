import javax.ejb.*;
import java.rmi.*;

public abstract class cmppBean implements EntityBean
{

protected EntityContext ctx;

public abstract String getOwnername();
public abstract void setOwnername(String ownername);
public abstract String getAccountid();
public abstract void setAccountid(String accountid);
public abstract double getBalance();
public abstract void setBalance(double balance);

public void deposit(double amount)
{
setBalance(getBalance() + amount);

}

public void withdraw(double amount)
{
setBalance(getBalance() - amount);
}

public void setEntityContext(EntityContext ctx)
{
this.ctx=ctx;
}

public void unsetEntityContext()
{
this.ctx=null;
}


public void ejbActivate()
{
}

public void ejbPassivate()
{
}

public void ejbLoad()
{
}

public void ejbStore()
{
}

public void ejbRemove()
{
}

public String ejbCreate(String accountid,String ownername,double balance)throws CreateException
{

setAccountid(accountid);
setOwnername(ownername);
setBalance(balance);
return accountid;
}

public void ejbPostCreate(String accountid,String ownername,double balance)
{
}


}
