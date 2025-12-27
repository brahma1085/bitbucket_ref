package com.scb.common.actions;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.Vector;

import org.apache.struts.action.*;

import administratorServer.AdministratorHome;
import administratorServer.AdministratorRemote;

import com.scb.common.forms.LoginForm;
import com.scb.designPatterns.CommonDelegate;

import commonServer.CommonHome;
import commonServer.CommonRemote;
import exceptions.LoginException;

import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Oct 9, 2007
 * Time: 12:14:49 AM
 * To change this template use File | Settings | File Templates.
 */
 public class LoginAction extends Action  {

 public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)throws Exception{
        /*String name=null,pass=null;*/
	 System.out.println("i am in login  action");
	 	LoginForm logForm=(LoginForm)form;
        boolean flag=false,flag2=false;
        String forward="done";
        String namefailure="not done";
        String passwordfailure="notdone";
        String failure="Invalid";
        CommonHome chome;
		CommonRemote cmnint = null;
		AdministratorHome admin_home = null;
		AdministratorRemote admin_remote = null;
		CommonDelegate cmDelegate=null;
		try 
        {
			cmDelegate=CommonDelegate.getCommonDelegate();
        } 
        catch(RemoteException e2) 
        {
			e2.printStackTrace();
			return map.findForward(failure);
		}
        catch(CreateException e3) 
        {
			e3.printStackTrace();
			return map.findForward(failure);
		}
        try 
        {
			String login=null;
			String ip_address = InetAddress.getLocalHost().getHostAddress();

			Vector user_roles = cmDelegate.checkLogin(logForm.getName().trim().toUpperCase(), logForm.getPass().trim(),cmDelegate.getSysDate(),logForm.getTml().trim().toUpperCase(), ip_address);
			
			
			if(cmDelegate.checkForHoliday(cmDelegate.getSysDate())) 
            {
				login = "H";
			} 
            else 
            {
				login = cmDelegate.checkLogin(logForm.getName().trim().toUpperCase(),logForm.getTml().trim().toUpperCase(),cmDelegate.getSysDate(),cmDelegate.getSysTime());
			}
			
			if(login.equalsIgnoreCase("H")) 
            {
			/*	MainScreen.getInstance(uid.getText().trim().toUpperCase(), tml.getText().trim().toUpperCase(), "H");
				MainScreen.head.addComboTmlList(user_roles);
				
				access_config = AccessConfig.getInstance(uid.getText().trim().toUpperCase(), tml.getText().trim().toUpperCase(), "H");
				access_config.setEnabled(tml.getText().toUpperCase());*/
				req.setAttribute("uid",logForm.getName());
				req.setAttribute("Tml", logForm.getTml());
				return map.findForward(forward);
			} 
            else if(login.equalsIgnoreCase("W"))
            {
			/*	MainScreen.getInstance(uid.getText().trim().toUpperCase(), tml.getText().trim().toUpperCase(), "W");
				MainScreen.head.addComboTmlList(user_roles);
				MainScreen.head.setTerminal( tml.getText().toUpperCase().trim());
				
				access_config = AccessConfig.getInstance(uid.getText().trim().toUpperCase(), tml.getText().trim().toUpperCase(), "W");
				access_config.setEnabled(tml.getText().trim().toUpperCase());*/
            	req.setAttribute("uid",logForm.getName());
				req.setAttribute("Tml", logForm.getTml());
				return map.findForward(forward); 
			} 
            else 
            {
            	return map.findForward(failure);
			}
		} 
        catch(LoginException le) 
        {
        	return map.findForward(failure);
			
		} 
        catch(Exception ex) 
        {
			ex.printStackTrace();
			//System.exit(1);
			return map.findForward(forward);
		}


     //return map.findForward(forward);
 }

}
