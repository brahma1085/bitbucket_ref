package com.scb.pd.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scb.pd.forms.PersonalForm;

/**
 * Created by IntelliJ IDEA.
 * User: Suraj
 * Date: Nov 19, 2007
 * Time: 12:11:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class PersonalAction extends Action {
    public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res){
        String custid=null;
        String category=null;
        String subcat=null;
        String mailid=null;
        String scst=null;
        String address=null;
        String dob=null;
        String sex=null;
        String age=null;
        String occupation=null;
         String fwd="done";

        if(form!=null){
            PersonalForm perform=(PersonalForm)form;
            custid=perform.getCustid();
            dob=perform.getDob();



       // req.setAttribute("sang",perform);

        }

        return map.findForward(fwd);
    }
}
