
//Remote Interface...

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface CalculatorRemote extends EJBObject
{

  public double dollarToRs(double dollars) throws RemoteException;

}
