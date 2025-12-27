/*
 * Created on Apr 11, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package exceptions;

import javax.ejb.CreateException;
import javax.swing.JOptionPane;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreateClientException {

	/*public String toString(){
		return "Creation Exception";  
	}*/
	
	/**
	 * 
	 */
	public CreateClientException(CreateException e) {
		JOptionPane.showMessageDialog(null,"Server Problem "+e.getMessage()); 			
	}
}
