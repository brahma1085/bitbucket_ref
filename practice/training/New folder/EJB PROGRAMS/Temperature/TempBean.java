import javax.ejb.*;
import java.rmi.*;

public class TempBean implements SessionBean
{
 public double FahrenheitToCelcius(double F)
 {
  double c;
  c=((F-32)*5)/9;
  return c;
 }
 
 public void ejbCreate() {}
 public void ejbActivate() {}
 public void ejbPassivate() {}
 public void ejbRemove() {}
 public void setSessionContext(SessionContext sc) {}
 
}