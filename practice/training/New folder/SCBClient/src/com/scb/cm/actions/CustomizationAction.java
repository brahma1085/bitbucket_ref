package com.scb.cm.actions;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: shwetha
 * Date: Nov 22, 2007
 * Time: 1:37:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class CustomizationAction extends Action {
    public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {
     String done="Done";
     System.out.println("path"+map.getPath());   
     if(map.getPath().equalsIgnoreCase("/CustomizationMenuLink")){
     DynaActionForm mydynaform=(DynaActionForm)form;
     String pageid=(String)mydynaform.get("pageId");
     System.out.println("pageid==========" +pageid);         
     req.setAttribute("pageId",pageid);
     return map.findForward(done);
     }
     return map.findForward(done);
    }
}
