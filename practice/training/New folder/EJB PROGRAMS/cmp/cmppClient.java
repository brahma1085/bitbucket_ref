import javax.ejb.*;
import javax.rmi.*;
import java.rmi.*;
import java.util.*;
import javax.naming.*;


public class cmppClient
{

public static void main(String ad[])
{
Properties p=new Properties();

try
{
p.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
p.put(Context.PROVIDER_URL,"t3://localhost:7001");
Context ctx=new InitialContext(p);
Object obj=ctx.lookup("cmppJNDI");
cmppHome home=(cmppHome)PortableRemoteObject.narrow(obj,cmppHome.class);
//System.out.println(home.getTotalcmpp());
cmpp rem=home.create("sreenivas","1911",0);
rem.deposit(15001);
System.out.println(rem.getBalance());
//System.out.println(home.getTotalcmpp());
rem.withdraw(1001);
System.out.println(rem.getBalance());
//System.out.println(home.getTotalcmpp());

}
catch(Exception e)
{
System.out.println("error from client: "+e);
}




}
}
