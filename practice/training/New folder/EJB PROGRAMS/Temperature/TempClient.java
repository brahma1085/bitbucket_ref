import javax.ejb.*;
import javax.naming.*;
import java.io.*;
import java.util.*;
import javax.rmi.*;

public class TempClient
{
 public static void main(String[] arg) 
 {
  try
  {
Properties p=new Properties();
	p.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
	p.put(Context.PROVIDER_URL,"t3://localhost:7001");
        Context ic=new InitialContext(p); //Getting a JNDI naming context..

	Object s = ic.lookup("degree");
	TemperatureHome home = (TemperatureHome) PortableRemoteObject.narrow(s,TemperatureHome.class);
	Temperature obj = home.create();
	
    try
     {
        double F=0;
        while(F!='q')
         {
          BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
          
          System.out.print("\n\n Enter the temperature in Fahrenheit(Type 'q' to quit): ");
 
         F=Double.parseDouble(br.readLine());
         
         double Cel=obj.FahrenheitToCelcius(F);
 
         System.out.println("The temperature  is Celcius: " + String.valueOf(Cel));

        }
     }catch(Exception e) {System.out.println("Thank you!");}

      obj.remove();
  }catch(Exception ex)
      {
        System.err.println("Caught an unexpected exception!");
        ex.printStackTrace();
      }



    }
}