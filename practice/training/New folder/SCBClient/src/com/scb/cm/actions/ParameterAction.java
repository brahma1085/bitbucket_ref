package com.scb.cm.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Nov 6, 2007
 * Time: 5:42:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class ParameterAction extends Action {
     public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)throws Exception{
         String done="Done";
         System.out.println("in action form swetha");
         ParameterForm myform=(ParameterForm)form;
         String addr=myform.getAddresstype();
          System.out.println("in action form swetha"+addr);          
          myform.setTxt("swetha");
          req.setAttribute("swe1",myform);
        /* if(myform.getAddresstype().equalsIgnoreCase("Communication")){
        myform.setComm("tumkur");
         req.setAttribute("swe",myform);    
         }*/
       return map.findForward(done);
     }
}
