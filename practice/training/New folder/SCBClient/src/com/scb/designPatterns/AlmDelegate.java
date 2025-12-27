package com.scb.designPatterns;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import alm.ALMHome;
import alm.ALMRemote;

import com.scb.designPatterns.exceptions.ApplicationException;
import com.scb.designPatterns.exceptions.ServiceLocatorException;
import com.scb.designPatterns.exceptions.SystemException;

import commonServer.CommonHome;
import commonServer.CommonRemote;
import customerServer.CustomerHome;
import customerServer.CustomerRemote;

public class AlmDelegate {
	private ServiceLocator serviceLoactor;
	private ALMHome almHome;
	private ALMRemote almRemote;
	private CommonRemote commonRemote;
    private CommonHome commHome;
    
    public AlmDelegate() throws ApplicationException,SystemException{
    	
    	
    	almHome =(ALMHome)ServiceLocator.getInstance().getRemoteHome("ALMWEB",ALMHome.class);
    		System.out.println("almHome=="+almHome);
        
        try {
            if(almHome !=null){  
            	almRemote = almHome.create();
                System.out.println("almRemote=="+almRemote);
            }
            else
                System.out.println("Ya its null here");
        } catch (RemoteException e) {
            throw new SystemException("SystemException:NamingException Occured"+e);
        } catch (CreateException ex) {
            throw new ApplicationException("EJBCreateException:NamingException Occured"+ex);
        }
    }
    
   
    
    
  
	
    }
 
