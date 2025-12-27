/*
 * Created on Apr 11, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package exceptions;

import javax.naming.NamingException;
import javax.swing.JOptionPane;



/**
 * @author user 
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NamingClientException {

	/*public String toString(){ 
		return "Server Not Connected";  
	}*/   
	
	/**
	 * 
	 */
	public NamingClientException(NamingException e) {
		JOptionPane.showMessageDialog(null,"JNDI Problem ? No Path Found "+e.getExplanation());   
	}	
	
}
