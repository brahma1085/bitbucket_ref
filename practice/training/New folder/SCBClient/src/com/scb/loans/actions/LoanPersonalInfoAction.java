package com.scb.loans.actions;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import masterObject.frontCounter.AccountMasterObject;
import masterObject.general.AccountObject;
import masterObject.general.IncomeObject;
import masterObject.general.ModuleObject;
import masterObject.general.PropertyObject;
import masterObject.general.VehicleObject;
import masterObject.loans.LoanMasterObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.common.help.CommonPanelHeading;
import com.scb.designPatterns.LoansDelegate;
import com.scb.designPatterns.PygmyDelegate;
import com.scb.fc.actions.ResultHelp;
import com.scb.loans.forms.LoanPersonalInfoForm;
import com.scb.loans.forms.PropertyForm;
import com.scb.props.MenuNameReader;

public class LoanPersonalInfoAction extends Action
{
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws RemoteException
	{
		System.out.println("this is action");
		LoanPersonalInfoForm lform = (LoanPersonalInfoForm) form;
		ModuleObject mod[] = null;
		PygmyDelegate pgDelegate;
		String path=null;
		LoansDelegate delegate;
		int int_type=0, int_rate_type=0;
		int i12=0;
		AccountObject ac= new AccountObject();
		double int_rate=0.0;
		double insta=0;
		try
		{
		if(mapping.getPath().equalsIgnoreCase("/Loans/LoanPersonalInfo"))
		{
			delegate = new LoansDelegate();
			pgDelegate =  PygmyDelegate.getPygmyDelegateInstance() ;
			System.out.println("Inside if block ======= 1");
			path=MenuNameReader.getScreenProperties(lform.getPageidentity().getPageId());
			//path = MenuNameReader.getScreenProperties(lform.getPageidentity().getPageId());
			System.out.println("the path is ======"+path);
			if(path!=null)
			{
				System.out.println("the path isd ======"+path);
				String str[] = delegate.getDetails();
				for(int i=0;i<str.length;i++)
				{
					System.out.println("The Details is ======="+str[i]);
				}
				mod=delegate.getLoanmodulecode(2);
				for(int i1=0;i1<mod.length;i1++)
				{
					System.out.println("The Modsule code is ======="+mod[i1]);
				}
				req.setAttribute("Details",delegate.getDetails());
				req.setAttribute("LoanModuleCode",delegate.getLoanmodulecode(2));
				req.setAttribute("AgentAcType", pgDelegate.getComboElements(3));
				req.setAttribute("LoanPurpose",delegate.getLoanDesc()); 
				req.setAttribute("PriorityDesc",delegate.getPriorityDesc());
				req.setAttribute("AccountTypeCode",delegate.getAccountType(2));
				req.setAttribute("pageId",path);
				return mapping.findForward(ResultHelp.getSuccess());
			}
		}
		if(mapping.getPath().equalsIgnoreCase("/Loans/LoanPersonalInfoTab"))
			{
				LoanPersonalInfoForm lform1 = (LoanPersonalInfoForm)form;
				LoanMasterObject lnobj=new LoanMasterObject();
				delegate = new LoansDelegate();
				pgDelegate =  PygmyDelegate.getPygmyDelegateInstance() ;
				System.out.println("inside if block ==== 1");
				req.setAttribute("Details",delegate.getDetails());
				req.setAttribute("LoanModuleCode",delegate.getLoanmodulecode(2));
				req.setAttribute("AgentAcType", pgDelegate.getComboElements(3));
				req.setAttribute("LoanPurpose",delegate.getLoanDesc()); 
				req.setAttribute("PriorityDesc",delegate.getPriorityDesc());
				req.setAttribute("AccountTypeCode",delegate.getAccountType(2));
				try
				{
					System.out.println("inside try ==== 1");
					System.out.println("path is ======= "+path);
					
					if(lform1.getAccno()!=0)
					{
						int accNo = lform1.getAccno();
						String accType = lform1.getAcctype();
						System.out.println("the Loan Acc number is ======"+ accNo);
						System.out.println("the Loan Acc type is ======"+ accType);
						System.out.println("i12 value is ===="+i12);
					try{
							i12 = delegate.cheakLoanNum(accNo);
							if(i12==2)
							{
								System.out.println("Loan id is not found");
								lform1.setValidateFlag("Loan Id is Not Found");
							}
							else 
							{
								lform1.setValidateFlag("Loan Id is Found");
							}
						}
						catch (Exception e) 
						{
							// TODO: handle exception
							e.printStackTrace();
						}
						lnobj=delegate.getLoanMaster(accNo,accType);
						if(lnobj!=null)
						{
							int_type =lnobj.getInterestType();
							int_rate_type =lnobj.getInterestRateType();
							System.out.println("int type is ===="+int_type);
							System.out.println("int rate type is ===="+int_rate_type);
						System.out.println("");
						System.out.println("the detail page is ======"+lform1.getDetails());
						//PropertyObject PropObj = lnobj.getPropertyDetails();
						//PropertyForm prop = new PropertyForm();
						//prop.setPropertyno(PropObj.getPropertyNo());
						//System.out.println("property number is ===="+PropObj.getPropertyNo());
						//req.setAttribute("",);
						System.out.println("From Loan Master Object Class");
						//obj.getNorthBy()
						req.setAttribute("APPDATA",lnobj);
						setAgentDetails(lform1.getDetails(),lform1.getAcctype(),lform1.getAccno(),delegate,req,lnobj,lform);
						lform1.setShno(String.valueOf(lnobj.getShareAccNo()));
						}
						System.out.println("Outof if loop");
					}
					if(lform1.getPeriod()!=0 && lform.getHoliday()!=0 && lform.getAmount()!=0)
					{
						System.out.println("the Amount is =="+lform.getAmount());
						System.out.println("the Period is =="+lform1.getPeriod());
						System.out.println("the Holiday Period is ==" +lform.getHoliday());
						double prd=0;
						
						int_rate=delegate.getIntRate(lform.getAcctype(),delegate.getSysDate(),0,lform.getPeriod(),lform.getAmount(),lform.getAccno());
						System.out.println("int rate is ===="+int_rate);
						lform.setIntrate(int_rate);
						
						double penal_int=delegate.getPenalIntRate(lform.getAcctype(),delegate.getSysDate(),3);
						System.out.println("Penal int rate====>"+penal_int); 
					//	lform.setPenalrate(penal_int);
						prd = (lform.getPeriod()-lform.getHoliday());
						double rate=int_rate/1200;
						double pow=1;
							for(int j=0;j<prd;j++)
								pow=pow*(1+rate);
							 insta=Math.round(0.01*Math.round(100*(lform.getAmount()*pow*rate)/(pow-1)));
							 System.out.println("Instalments===>"+insta); 
							 lform.setInstallment(insta);
							 System.out.println("Paymode Acc Type ====>"+lnobj.getPaymentAcctype());
							 System.out.println("Paymode Acc Type ====>"+lnobj.getPaymentAccno());
							 System.out.println("Pay type is ====>"+lform.getPayAccountType()+"Pay Number is==="+lform.getPayAccNo());
							// ac = delegate.getAccount(null,lform.getPayAccountType(),lform.getPayAccNo());
							// System.out.println("Account name is ======"+ac.getAccname());
							 
						}	
						if(lform.getPayAccNo()!=0)
						{
							ac = delegate.getAccount(null,lform.getPayAccountType(),lform.getPayAccNo());
							System.out.println("Account name is ======"+ac.getAccname());
							lform.setPayAccName(ac.getAccname());
						}
						System.out.println("button value is ======="+lform.getButtonvalue());
						if(lform.getButtonvalue()!=null&&lform.getButtonvalue().equalsIgnoreCase("Submit"))
						{
							//int result=delegate.sanctionLoan(Integer.valueOf(lform.getAcctype()),lform.getAccno(),lform.getAmount(),lform.getPeriod(),false,false,int_rate,int_type,int_rate_type, insta,lform.getHoliday(),delegate.getSysDate());
							int result=delegate.sanctionLoan(Integer.parseInt(lform.getAcctype()),lform.getAccno(),lform.getAmount(),lform.getPeriod(),Integer.parseInt(lform.getPriority()),false,int_rate,int_type,int_rate_type, insta,lform.getHoliday(),delegate.getSysDate());
						}
					if(MenuNameReader.containsKeyScreen(lform1.getPageidentity().getPageId()))
					{ 
					     
						System.out.println("------------Testing ----------");
					     path = MenuNameReader.getScreenProperties(lform1.getPageidentity().getPageId());
					     System.out.println("path is ===========" +path);
						 req.setAttribute("pageId",path);						 
					}
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				mapping.findForward(ResultHelp.getSuccess());
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return mapping.findForward(ResultHelp.getError());
	}
	public void setAgentDetails(String details,String accType,int accNo,LoansDelegate delgate,HttpServletRequest req,LoanMasterObject lnobj,LoanPersonalInfoForm lform) throws Exception	{
		System.out.println("inside method ======= 1");
		System.out.println("the details name is ======="+details);
		if(details.equalsIgnoreCase("Personal"))
		{
			AccountMasterObject amObj;
			
			int custId = lnobj.getCustomerId();
			System.out.println("Customer id is =========="+custId);
			
			req.setAttribute("personalInfo", delgate.getCustomer(custId));
			System.out.println("this is inside ==== Personal");
			String perPath=MenuNameReader.getScreenProperties("0001");
			req.setAttribute("panelName",CommonPanelHeading.getPersonal());
			System.out.println("path=="+perPath);
			req.setAttribute("flag",perPath);
			
		}
		if(lform.getDetails().equalsIgnoreCase("Relative"))
		{
			System.out.println("this is inside ==== Relative");
			String perPath=MenuNameReader.getScreenProperties("0001");
			System.out.println("path=="+perPath);
			req.setAttribute("flag",perPath);
		}
		if(lform.getDetails().equalsIgnoreCase("Employment"))
		{
			System.out.println("this is inside ==== Employment");
			String perPath=MenuNameReader.getScreenProperties("6003");
			String pageActions[]={"/Loans/LoanPersonalInfoTab?tabPaneHeading=SelfEmployed&pageId="+lform.getPageidentity().getPageId(),"/Loans/LoanPersonalInfoTab?tabPaneHeading=Service&pageId="+lform.getPageidentity().getPageId(),"/Loans/LoanPersonalInfoTab?tabPaneHeading=Business&pageId="+lform.getPageidentity().getPageId(),"/Loans/LoanPersonalInfoTab?tabPaneHeading=Pension&pageId="+lform.getPageidentity().getPageId(),"/Loans/LoanPersonalInfoTab?tabPaneHeading=Rent&pageId="+lform.getPageidentity().getPageId()};
			String tabHeading[]={"SelfEmployed","Service","Business","Pension","Rent"};
			String pagePath[]={MenuNameReader.getScreenProperties("5005").trim(),MenuNameReader.getScreenProperties("5006").trim(),MenuNameReader.getScreenProperties("5007").trim(),MenuNameReader.getScreenProperties("5008").trim(),MenuNameReader.getScreenProperties("5009").trim()};
			req.setAttribute("TabHeading",tabHeading);
			req.setAttribute("PageActions", pageActions);
			req.setAttribute("PagePath", pagePath);
			req.setAttribute("TabNum","0");
			if(lform.getTabPaneHeading()!=null)
		    {	 
		    	if(lform.getTabPaneHeading().equalsIgnoreCase("Service"))
		    	{
		    		req.setAttribute("TabNum","1");
		    	}
		    	else if(lform.getTabPaneHeading().equalsIgnoreCase("Business"))
		    	{
		    		req.setAttribute("TabNum","2");
		    	}
		    	else if(lform.getTabPaneHeading().equalsIgnoreCase("Pension"))
		    	{
		    		req.setAttribute("TabNum","3");
		    	}
		    	else if(lform.getTabPaneHeading().equalsIgnoreCase("Rent"))
		    	{
		    		 req.setAttribute("TabNum","4");
		    	}
		     }
			IncomeObject[] in = lnobj.getIncomeDetails();
			for(int i=0;i<in.length;i++)
			{
				
				if(in[i].getTypeOfIncome().equals("Self"))
				{
					System.out.println("this is self employment");
					in[i].getAddress();
					in[i].getName();
					in[i].getPhNo();
					in[i].getService();
					in[i].getIncome();
					in[i].getExpenditure();
					in[i].getNetIncome();
					System.out.println(" ADDR =="+in[i].getAddress()+
									   "Nature of Emp ==="+in[i].getName()+
									   "Phone Number ===="+in[i].getPhNo()+
									   "Service -----"+in[i].getService()+
									   "Income ===="+in[i].getIncome()+
									   "Expense ==="+in[i].getExpenditure()+
									   "Net Income ====="+in[i].getNetIncome());
					req.setAttribute("SELOBJ",in);
				}
				if(in[i].getTypeOfIncome().equals("Service"))
				{
					in[i].getName();
					req.setAttribute("SEROBJ",in);
				}
			}
			
		//	in[0].getTypeOfIncome();
			System.out.println("path=="+perPath);
			req.setAttribute("flag",perPath);
		}
		if(lform.getDetails().trim().equalsIgnoreCase("Application"))
		{
			System.out.println("this is inside ==== Application");
			if(lnobj!=null)
			{
			//	lform.setAppln_no(lnobj.getApplicationSrlNo());
			//	lform.setAppndate(Integer.valueOf(lnobj.getApplicationDate()));
			//	lform.setReqamount(lnobj.getRequiredAmount());
				System.out.println("app amount ========"+lnobj.getRequiredAmount());
				System.out.println("this is inside ==== Appliaction");
			}
			String perPath=MenuNameReader.getScreenProperties("5002");
			req.setAttribute("panelName",CommonPanelHeading.getApplication());
			System.out.println("path=="+perPath);
			req.setAttribute("flag",perPath);
		}
		if(lform.getDetails().trim().equalsIgnoreCase("Loan and Share Details"))
		{
			System.out.println("this is inside ==== Loans and Share Details ");
			String perPath=MenuNameReader.getScreenProperties("0001");
			System.out.println("path=="+perPath);
			req.setAttribute("flag",perPath);
		}
		if(lform.getDetails().trim().equalsIgnoreCase("Signature Ins"))
		{
			System.out.println("this is inside ==== Signature Ins ");
			String perPath=MenuNameReader.getScreenProperties("0004");
			System.out.println("path=="+perPath);
			req.setAttribute("flag",perPath);
		}
		if(lform.getDetails().trim().equalsIgnoreCase("Properpty"))
		{
			System.out.println("inside propery");
			PropertyObject proObj=lnobj.getPropertyDetails();
			System.out.println("Object is ======= "+proObj);
			req.setAttribute("PropertyObj",proObj);
			System.out.println("the property number is ======"+proObj.getPropertyNo());
			String perPath=MenuNameReader.getScreenProperties("5004");
			req.setAttribute("panelName",CommonPanelHeading.getProperty());
			System.out.println("path=="+perPath);
			req.setAttribute("flag",perPath);
		}
		if(lform.getDetails().trim().equalsIgnoreCase("CoBorrowers"))
		{
			System.out.println("this is inside ==== CoBorrowers");
			String perPath=MenuNameReader.getScreenProperties("5032");
			System.out.println("path=="+perPath);
			req.setAttribute("flag",perPath);
		}
		if(lform.getDetails().trim().equalsIgnoreCase("Securities"))
		{
			System.out.println("this is inside ==== Securities");
			String perPath=MenuNameReader.getScreenProperties("5022");
			System.out.println("path=="+perPath);
			req.setAttribute("flag",perPath);
		}
		if(lform.getDetails().trim().equalsIgnoreCase("Vehicle"))
		{
			System.out.println("this is inside ==== Vehicle");
			VehicleObject vehicleObj = lnobj.getVehicleDet();
			System.out.println("Vehicle Obj is ====="+vehicleObj);
			req.setAttribute("VECHOBJ",vehicleObj);
			String perPath=MenuNameReader.getScreenProperties("5003");
			req.setAttribute("panelName",CommonPanelHeading.getVechicle());
			System.out.println("path=="+perPath);
			req.setAttribute("flag",perPath);
		}
		if(lform.getDetails().trim().equalsIgnoreCase("Gold"))
		{
			System.out.println("this is inside ==== Gold");
			String perPath=MenuNameReader.getScreenProperties("0001");
			System.out.println("path=="+perPath);
			req.setAttribute("flag",perPath);
		}
	}
}
