package com.bank.adminserver.ejb;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import javax.ejb.CreateException;

/**
 * XDoclet-based session bean.  The class must be declared
 * public according to the EJB specification.
 *
 * To generate the EJB related files to this EJB:
 *		- Add Standard EJB module to XDoclet project properties
 *		- Customize XDoclet configuration for your appserver
 *		- Run XDoclet
 *
 * Below are the xdoclet-related tags needed for this EJB.
 * 
 * @ejb.bean name="Admin"
 *           display-name="Name for Admin"
 *           description="Description for Admin"
 *           jndi-name="ejb/Admin"
 *           type="Stateless"
 *           view-type="both"
 */
public class AdminBean implements SessionBean {

	/** The session context */
	private SessionContext context;

	public AdminBean() {
		// TODO Auto-generated constructor stub
	}

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Set the associated session context. The container calls this method 
	 * after the instance creation.
	 * 
	 * The enterprise bean instance should store the reference to the context 
	 * object in an instance variable.
	 * 
	 * This method is called with no transaction context. 
	 * 
	 * @throws EJBException Thrown if method fails due to system-level error.
	 */
	public void setSessionContext(SessionContext newContext)
		throws EJBException {
		context = newContext;
	}

	/**
	 * An ejbCreate method as required by the EJB specification.
	 * 
	 * The container calls the instance?s <code>ejbCreate</code> method whose
	 * signature matches the signature of the <code>create</code> method invoked
	 * by the client. The input parameters sent from the client are passed to
	 * the <code>ejbCreate</code> method. Each session bean class must have at
	 * least one <code>ejbCreate</code> method. The number and signatures
	 * of a session bean?s <code>create</code> methods are specific to each 
	 * session bean class.
	 * 
	 * @throws CreateException Thrown if method fails due to system-level error.
	 * 
	 * @ejb.create-method
	 * 
	 */
	public void ejbCreate() throws CreateException {
		// TODO Add ejbCreate method implementation
	}

	/**
	 * An example business method
	 *
	 * @ejb.interface-method view-type = "both"
	 * 
	 * @throws EJBException Thrown if method fails due to system-level error.
	 */
	public void replaceWithRealBusinessMethod() throws EJBException {
		// rename and start putting your business logic here
	}

}
