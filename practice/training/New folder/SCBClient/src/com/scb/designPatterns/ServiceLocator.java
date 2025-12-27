package com.scb.designPatterns;

import com.scb.designPatterns.exceptions.ServiceLocatorException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.rmi.PortableRemoteObject;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.io.*;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: ${Sreenivas}
 * Date: Nov 16, 2007
 * Time: 2:38:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceLocator 
{
    private InitialContext initialContext;
    private Map cache;
    private static ServiceLocator _instance;

    /*static {
        try {
            _instance = new ServiceLocator();
        } catch (ServiceLocatorException se) {
            System.err.println(se);
            se.printStackTrace(System.err);
        }
    }*/

    private ServiceLocator() throws ServiceLocatorException {
        try {
            initialContext = new InitialContext();
            /*cache = Collections.synchronizedMap(new HashMap());*/
            cache=new HashMap();
        } catch (NamingException ne) {
            throw new ServiceLocatorException("ServiceLocator:NamingException Occured"+ne);
        }
    }

    static public ServiceLocator getInstance() {
        if(_instance==null){
           try{
            _instance=new ServiceLocator();
           }catch(ServiceLocatorException se){
                se.printStackTrace();
           }
            catch(Exception e){
               e.printStackTrace(); 
            }
        }
        return _instance;
    }

    // look up a local home given the JNDI name for the
    // local home
    public EJBLocalHome getLocalHome(String jndiHomeName,Class homeLocalClassName)throws ServiceLocatorException {
        EJBLocalHome localHome = null;
        
        try {
        	System.out.println("inside try....");
            if (cache.containsKey(jndiHomeName)) 
            {
                localHome = (EJBLocalHome)cache.get(jndiHomeName);
                System.out.println("inside if..........");
            } else {
            	Object objref =initialContext.lookup(jndiHomeName);
                Object obj = PortableRemoteObject.narrow(objref, homeLocalClassName);
                localHome  = (EJBLocalHome)obj;
              //  localHome = (EJBLocalHome)initialContext.lookup(jndiHomeName);
                cache.put(jndiHomeName, localHome);
                System.out.println("inside else.......");
            }
        } catch (NamingException nex) {
        	System.out.println("inside catch....");
            throw new ServiceLocatorException("ServiceLocator:NamingException Occured"+nex);
        }
        return localHome;
    }

    // lookup a remote home given the JNDI name for the
    // remote home
    public EJBHome getRemoteHome(String jndiHomeName, Class homeClassName)throws ServiceLocatorException {
        EJBHome remoteHome = null;
    	
        try {
            if (cache.containsKey(jndiHomeName)) {
                System.out.println("****************>"+jndiHomeName);
                remoteHome =(EJBHome) cache.get(jndiHomeName);
                System.out.println("****************1>"+remoteHome);
            }
            else {
                System.out.println("****************>"+jndiHomeName);        
                Object objref =initialContext.lookup(jndiHomeName);
                Object obj = PortableRemoteObject.narrow(objref, homeClassName);
                remoteHome = (EJBHome)obj;
               
                System.out.println("****************>"+remoteHome);
                cache.put(jndiHomeName, remoteHome);
                System.out.println("****************>"+remoteHome);                
            }
        } catch (NamingException nex) {
            throw new ServiceLocatorException("ServiceLocator:NamingException Occured"+nex);
        }
        return remoteHome;
    }



    public static EJBObject getService(String id)throws ServiceLocatorException {
        if (id == null) {
            throw new ServiceLocatorException(
                    "Invalid ID: Cannot create Handle");
        }
        try {
            byte[] bytes = new String(id).getBytes();
            InputStream io = new ByteArrayInputStream(bytes);
            ObjectInputStream os = new ObjectInputStream(io);
            javax.ejb.Handle handle =(javax.ejb.Handle) os.readObject();
            return handle.getEJBObject();
        } catch (IOException IOex) {
            throw new ServiceLocatorException("ServiceLocator:IOException Occured"+IOex);
        } catch (ClassNotFoundException CNFe) {
            throw new ServiceLocatorException("ServiceLocator:ClassNotFoundException Occured"+CNFe);
        }
    }

    // Returns the String that represents the given
    // EJBObject's handle in serialized format.
    public static String getId(EJBObject session)throws ServiceLocatorException {
        String id=null;
        try {
            javax.ejb.Handle handle = session.getHandle();
            ByteArrayOutputStream fo =new ByteArrayOutputStream();
            ObjectOutputStream so =new ObjectOutputStream(fo);
            so.writeObject(handle);
            so.flush();			
            so.close();
            id = new String(fo.toByteArray());
        } catch (RemoteException rex) {
            throw new ServiceLocatorException("ServiceLocator:RemoteException Occured"+rex);
        } catch (IOException ioex) {
            throw new ServiceLocatorException("ServiceLocator:IOException Occured"+ioex);
        }

        return id;
    }
}
