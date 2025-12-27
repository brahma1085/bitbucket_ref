import java.io.*;

public class accountPK implements java.io.Serializable
{

public String accountID;

public accountPK(String id)
{
this.accountID=id;
}

public accountPK()
{
}

public String toString()
{
return accountID;
}

public int hashCode()
{
return accountID.hashCode();
}

public boolean equals(Object account)
{
if(!(account instanceof accountPK))
return false;
return ((accountPK)account).accountID.equals(accountID);
}
}

