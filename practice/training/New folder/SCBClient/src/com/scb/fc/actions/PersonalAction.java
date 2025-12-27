package com.scb.fc.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: ${Sreenivas}
 * Date: Nov 19, 2007
 * Time: 1:26:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class PersonalAction extends Action {
    public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res){
        return map.findForward("Done");
    }
}
