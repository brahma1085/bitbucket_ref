package com.scb.pd.actions;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import masterObject.customer.CustomerMasterObject;
import masterObject.pygmyDeposit.SimpleMasterObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.common.help.ResultHelp;
import com.scb.common.log4j.LogDetails;

import com.scb.designPatterns.CustomerDelegate;
import com.scb.designPatterns.LoansDelegate;
import com.scb.designPatterns.PygmyDelegate;
import com.scb.pd.forms.SimpleLoanForm;
import com.scb.props.MenuNameReader;

public class SimpleLoanAction extends Action
{
	String path;
	//SampleLogObject log = SampleLogObject.getInstance();
//	Logger logger = log.getLoggerObject("gg");
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
	//	logger.info("this is samplweg loansssssss=================> 1");
		System.out.println("hi this simple loan form");
		SimpleLoanForm simpleLoanForm=(SimpleLoanForm) form;
		if(mapping.getPath().equalsIgnoreCase("/Pygmy/SampleLoanAction"))
		{
			LoansDelegate delegate = new LoansDelegate();
			System.out.println("delegate obj ----- >"+delegate);
			request.setAttribute("ModuleCodeObject",delegate.getMainModules(2,"1010000"));
			LoansDelegate mainModule = (LoansDelegate)request.getAttribute("ModuleCodeObject");
			System.out.println("this is if loop path(/Pygmy/SampleLoanAction)========>"+mapping.getPath());
			System.out.println("page id is(/Pygmy/SampleLoanAction) ===========>"+simpleLoanForm.getPageId());
			path = MenuNameReader.getScreenProperties(simpleLoanForm.getPageId());
			System.out.println("path is(/Pygmy/SampleLoanAction)==============>"+path);
			request.setAttribute("pageId", path);
			mapping.findForward(ResultHelp.getSuccess());
			System.out.println("/Pygmy/SampleLoanAction");
		}
		if(mapping.getPath().equalsIgnoreCase("/simpleForwardAction"))
		{
			//System.out.println("this is in 2nd loop==========>"+mapping.getPath());
			LoansDelegate simpleLoanDelegateObj = new LoansDelegate();
			CustomerDelegate delegate=new CustomerDelegate();
			System.out.println("page id is(/simpleForwardAction) ===========>"+simpleLoanForm.getPageId());
			path = MenuNameReader.getScreenProperties(simpleLoanForm.getPageId());
			System.out.println("path is(/simpleForwardAction)==============>"+path);
			System.out.println("From Action to delegate ========= 1");
			String accno = simpleLoanForm.getSimpLoanName();
		//	logger.info("ac number is ============= "+accno);
			System.out.println("step 1=============>");
			System.out.println("the cid is ==============>"+simpleLoanForm.getCid());
			CustomerMasterObject cust=delegate.getCustomerdetails(simpleLoanForm.getCid());
//			String namIs=cust.getFirstName();
			//System.out.println("the customer name is ========"+cust.getFirstName());
			SimpleMasterObject smObj = simpleLoanDelegateObj.getAccountDetails(accno); 
			request.setAttribute("CUST", cust);
			System.out.println("last step of the flow =============== last");
			request.setAttribute("pageId", path);
			mapping.findForward(ResultHelp.getSuccess());
		}
		return mapping.findForward(ResultHelp.getSuccess());
	}
}
