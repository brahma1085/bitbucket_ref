package com.scb.common.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class SignatureInstructionComboAction  extends Action{
	public ActionForward execute(ActionMapping map,ActionForm form,HttpServletRequest req,HttpServletResponse res)throws Exception {
		
		return map.findForward("Done");
	}
		
}
