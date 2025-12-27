package client;

import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.swing.JOptionPane;

/*
import clearingServer.ClearingHome;

import loanServer.LoanHome;
import loansOnDepositServer.LoansOnDepositHome;
import lockerServer.LockersHome;
*/

import administratorServer.AdministratorHome;
/*
import backOfficeServer.BackOfficeHome;

import cashServer.CashHome;

import pygmyServer.PygmyHome;
import shareServer.ShareHome;
import termDepositServer.TermDepositHome;
*/

import commonServer.CommonHome;
import customerServer.CustomerHome;
/*
import frontCounterServer.FrontCounterHome;

import parametersServer.parametersHome;
*/
import generalLedgerServer.GLHome;

/**@author root
 *This Class HomeFactory is for creating and pooling EJBHome Objects.
 *Whenever a client lookups for a home, it checks for the Home Object in HashMap.
 *If it found an object, it returns it. Otherwise it looksup the server for that Home Object
 *and stores that object in HashMap.
 */
public final class HomeFactory 
{
	private HashMap ejbHomes;
	private static HomeFactory homeFactory;
	private static Context ctx = null;
	
	private HomeFactory()
	{
		ejbHomes = new HashMap(13);
		System.out.println("new Home Factory Created");
		try 
		{
			ctx = new InitialContext();
		}
		catch(NamingException e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Unable to get the Server Context");
			System.exit(1);
		}
	}
	
	public Object lookUpHome(String name) throws NamingException
	{
		if(ejbHomes.containsKey(name))
			return ejbHomes.get(name);	
		
		Class castClass = null;
		String jndiname = null;
		
		if(name.equals("COMMON"))
		{
			jndiname = "COMMON";
			castClass = CommonHome.class;
		}
		/*else if(name.equals("CLEARING"))
		{
			jndiname = "CLEARING";
			castClass = ClearingHome.class;
		}*/
		else if(name.equals("CUSTOMER"))
		{
			jndiname = "CUSTOMER";
			castClass = CustomerHome.class;
		}
		/*else if(name.equals("CASH"))
		{
			jndiname = "CASH";
			castClass = CashHome.class;
		}
		else if(name.equals("FRONTCOUNTER"))
		{
			jndiname = "FRONTCOUNTER";
			castClass = FrontCounterHome.class;
		}
		else if(name.equals("BACKOFFICE"))
		{
			jndiname = "BACKOFFICE";
			castClass = BackOfficeHome.class;
		}
		else if(name.equals("LOCKERS"))
		{
			jndiname = "LOCKERS";
			castClass = LockersHome.class;
		}
		else if(name.equals("LOANSONDEPOSIT"))
		{
			jndiname = "LOANSONDEPOSIT";
			castClass = LoansOnDepositHome.class;
		}		
		else if(name.equals("LOANS"))
		{
			jndiname = "LOANS";
			castClass = LoanHome.class;
		}
		else if(name.equals("TERMDEPOSIT"))
		{
			jndiname = "TERMDEPOSIT";
			castClass = TermDepositHome.class;
		}
		else if(name.equals("PYGMY"))
		{
			jndiname = "PYGMY";
			castClass = PygmyHome.class;
		}
		else if(name.equals("SHARE"))
		{
			jndiname = "SHARE";
			castClass = ShareHome.class;
		}	*/	
		else if(name.equals("ADMINISTRATOR"))
		{
			jndiname = "ADMINISTRATOR";
			castClass = AdministratorHome.class;
		}		
		/*else if(name.equals("PARAMETERS"))
		{
			jndiname = "PARAMETERS";
			castClass = parametersHome.class;
		}*/		
		else if(name.equals("GL"))
		{
			jndiname = "GL";
			castClass = GLHome.class;
		}		
		if(jndiname!=null && castClass!=null)
		{
			ejbHomes.put(name,PortableRemoteObject.narrow(ctx.lookup(jndiname),castClass));
			System.out.println("Added "+name+" Home to Factory");
			return ejbHomes.get(name);
		}
		//ship....added else...17/01/2006
		else
		    throw new NamingException("("+name+") Name Not found..");
	}
	
	public static HomeFactory getFactory()
	{
		if(homeFactory != null)
			return homeFactory;
		
		homeFactory = new HomeFactory();
		
		return homeFactory;
	}
}