package com.scb.td.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.scb.fc.actions.ResultHelp;
import com.scb.props.MenuNameReader;
import com.scb.td.forms.AllMod;
import com.scb.td.forms.Exampleform;

    
public class ExampleAction extends Action {
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res)throws Exception
	{	
		try{
		System.out.println("hello->");
		Exampleform fm=(Exampleform)form;
		System.out.println("hiiiiiii");
		if(mapping.getPath().equalsIgnoreCase("/TermDeposit/Example1")){
		System.out.println("iN aCTION cLASS"); 
		System.out.println("ma"+mapping.getPath());
		
		System.out.println("Page id===>"+fm.getPageId());
		}
		
		 if(MenuNameReader.containsKeyScreen(fm.getPageId()))
		{
			System.out.println("Inside Link");
			String path=MenuNameReader.getScreenProperties(fm.getPageId());
			req.setAttribute("pageId",path);
			return mapping.findForward(ResultHelp.getSuccess()); 
		}
		
		 
		
		return mapping.findForward(ResultHelp.getError()); 
	
	  
    }
	catch (Exception e) {
		e.printStackTrace();
		return mapping.findForward(ResultHelp.getError());
	}
	
	

 }
	public void add(){
		System.out.println("Hi guys how are you=====>");
	}
}
