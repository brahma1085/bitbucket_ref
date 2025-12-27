
//EJB Class file....

import java.rmi.RemoteException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class CalculatorEJB implements SessionBean
{

  public CalculatorEJB()
  {}

  public double dollarToRs(double dollars)
  {

    return dollars*47.20; // 1 Dollar is 47.20 Rs...
   
  }

  public void ejbCreate() 
  {}

  public void ejbRemove()
  {}

  public void ejbActivate()
  {}

  public void ejbPassivate()
  {}

  public void setSessionContext(SessionContext sc)
  {}

}
