 import javax.ejb.*;
import javax.rmi.*;
import java.rmi.*;
import java.util.*;
import javax.naming.*;


public class accountClient
{

public static void main(String ad[])
{
Properties p=new Properties();

try
{
p.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
p.put(Context.PROVIDER_URL,"t3://localhost:7001");
Context ctx=new InitialContext(p);
Object obj=ctx.lookup("accountJNDII");
accountHome home=(accountHome)PortableRemoteObject.narrow(obj,accountHome.class);
//System.out.println(home.getTotalaccount());
account rem=home.create("aa134","jaya");
rem.deposit(5000);
System.out.println(rem.getBalance());
//System.out.println(home.getTotalaccount());
rem.withdraw(600);
System.out.println(rem.getBalance());
//System.out.println(home.getTotalaccount());

}
catch(Exception e)
{
System.out.println("error from client: "+e);
}




}
}
