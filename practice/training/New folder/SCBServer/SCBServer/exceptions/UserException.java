/*
 * Created on Apr 11, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package exceptions;

import java.rmi.RemoteException;
import javax.swing.JOptionPane;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserException extends Exception{ 

	public UserException(RemoteException remote){ 
		String str = remote.detail.toString();   
		if(str.lastIndexOf(":") > 0)
			JOptionPane.showMessageDialog(null,str.substring(str.lastIndexOf(":")+1));
		else
			JOptionPane.showMessageDialog(null,remote.detail.toString());	
	} 
	
}
