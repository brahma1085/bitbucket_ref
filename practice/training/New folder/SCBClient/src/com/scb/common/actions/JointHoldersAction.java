package com.scb.common.actions;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;

import com.scb.common.forms.JointHoldersActionForm;
import com.scb.common.help.ResultHelp;
import com.scb.props.MenuNameReader;
/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Dec 7, 2007
 * Time: 11:42:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class JointHoldersAction extends Action{
    public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res){
    	
    	
    	if(map.getPath().trim().equalsIgnoreCase("/Common/JointHolder"))
    	{
    		JointHoldersActionForm jointhold=(JointHoldersActionForm)form;
    		if (MenuNameReader.containsKeyScreen(jointhold.getPageId())) 
    		{
				String path = MenuNameReader.getScreenProperties(jointhold.getPageId());
				if(jointhold.getCid()==null)
				{
					jointhold.setCid("hiiiiii");
				}
				req.setAttribute("pageId", path);
    		}
    	}
    	
        return map.findForward(ResultHelp.getSuccess());
    }
}
