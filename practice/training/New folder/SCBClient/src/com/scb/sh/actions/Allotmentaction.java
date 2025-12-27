package com.scb.sh.actions;

import general.Validations;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import masterObject.customer.CustomerMasterObject;
import masterObject.general.AccountObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.share.DividendObject;
import masterObject.share.DividendRateObject;
import masterObject.share.ShareCategoryObject;
import masterObject.share.ShareMasterObject;
import masterObject.share.ShareParamObject;
import masterObject.share.ShareReportObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.common.forms.SignatureInstructionForm;
import com.scb.common.help.CommonPanelHeading;
import com.scb.common.help.ResultHelp;
import com.scb.common.log4j.LogDetails;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.PygmyDelegate;
import com.scb.designPatterns.ShareDelegate;
import com.scb.props.MenuNameReader;
import com.scb.sh.forms.AddAllotmentForm;
import com.scb.sh.forms.AllotmentForm;
import com.scb.sh.forms.CertificateForm;
import com.scb.sh.forms.ChangeofModulesForm;
import com.scb.sh.forms.DividendCalc;
import com.scb.sh.forms.DividendDataForm;
import com.scb.sh.forms.DividendPaymentForm;
import com.scb.sh.forms.DividendRegistryForm;
import com.scb.sh.forms.DividendReportForm;
import com.scb.sh.forms.MasterUpdationForm;
import com.scb.sh.forms.NomineeForm;
import com.scb.sh.forms.OpenReportForm;
import com.scb.sh.forms.PassBookForm;
import com.scb.sh.forms.RegularizationForm;
import com.scb.sh.forms.ShareAdminForm;
import com.scb.sh.forms.ShareDivrateForm;
import com.scb.sh.forms.ShareLedgerform;
import com.scb.sh.forms.SignForm;
import com.scb.sh.forms.TempPermForm;
import com.scb.sh.forms.UnclaimedDivForm;
import com.scb.sh.forms.UnclaimedNoticeForm;
import com.scb.sh.forms.ViewlogForm;
import com.scb.sh.forms.WithdrawalForm;


public class Allotmentaction extends Action{
  private String path;
  private ShareDelegate shDelegate;
  private ShareMasterObject shobj;
  private ShareParamObject[] shparam;
  private ModuleObject[] mod_obj;
  private HttpSession session,sessions; 
  private ShareMasterObject[] shobjarr;
  String shuser,shtml;
  AdministratorDelegate admDelegate;
  Map user_role;
  final Logger logger=LogDetails.getInstance().getLoggerObject("ShareDelegate");
  String name=null;
  public ActionForward execute(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res)throws Exception{
    	        sessions=req.getSession();
    		    shuser=(String)sessions.getAttribute("UserName");
    	    	shtml=(String)sessions.getAttribute("UserTml") ;
    	    	try{
    	    		synchronized(req) {
    					if(shuser!=null){
    						admDelegate=new AdministratorDelegate();
    						user_role=admDelegate.getUserLoginAccessRights(shuser,"SH");
    						if(user_role!=null){
    							req.setAttribute("user_role",user_role);
    							if(req.getParameter("pageId")!=null){
    								System.out.println("req.getParameter--in FC ACtion===>"+req.getParameter("pageId"));
    								req.setAttribute("accesspageId",req.getParameter("pageId").trim());
    							}
    						}else{
    							path=MenuNameReader.getScreenProperties("0000");
    							setErrorPageElements("Sorry, You do not have access to this page",req,path);
    							return map.findForward(ResultHelp.getError());
    						}
    					}
    	    			}
    	    		}catch(Exception ex){
    	    			ex.printStackTrace();
    	    		}
    	    		System.out.println("The main path is"+map.getPath());
    	    		if(map.getPath().trim().equalsIgnoreCase("/Share/AllotmentMenu")){
    	    			try{
    	    				
    	    				AllotmentForm sh_allotform=(AllotmentForm)form;
    	    				logger.info("This is from Shares"+map.getPath());
    	    				req.setAttribute("pagenum", sh_allotform.getPageId());
    	    				req.setAttribute("TabNum", "0");
    	    				req.setAttribute("flag", MenuNameReader.getScreenProperties("3003"));
    	    				getTabbedpaneAllotment(req,sh_allotform);//comment on 2.12.2008
    	    				req.setAttribute("Bal",0.0);
    	    				if(MenuNameReader.containsKeyScreen(sh_allotform.getPageId())){
    	    					path=MenuNameReader.getScreenProperties(sh_allotform.getPageId());
    	    					shDelegate=new ShareDelegate();
    	    					setShOpeningInitParams(req, path, shDelegate);
    	    					req.setAttribute("pageId",path);
    	    					sh_allotform.setAcnum("0");
    	    					sh_allotform.setCid("0");
    	    					return map.findForward(ResultHelp.getSuccess());
    	    				}else{
    	    					path=MenuNameReader.getScreenProperties("0000");
    	    					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
    	    					return map.findForward(ResultHelp.getError());
    	    				}
    	    			}catch(Exception e){
    	    				path=MenuNameReader.getScreenProperties("0000");
    	    				setErrorPageElements(""+e, req, path);
    	    				return map.findForward(ResultHelp.getError());
    	    			}
    	    		}else if (map.getPath().trim().equalsIgnoreCase("/Share/Allotment")){
						AllotmentForm sh_allotform=(AllotmentForm)form;
						String path=null;
						try{
							
							req.setAttribute("pagenum", sh_allotform.getPageId());
							if(MenuNameReader.containsKeyScreen(sh_allotform.getPageId())){
								req.setAttribute("Bal",0.0);
								path=MenuNameReader.getScreenProperties(sh_allotform.getPageId());
								shDelegate=new ShareDelegate();
								req.setAttribute("pageId",path);
								setShOpeningInitParams(req, path, shDelegate);
								System.out.println("the delegate is  as follws----------> "+shDelegate);
								if(sh_allotform.getAcnum()!=null && sh_allotform.getAcnum().trim().length()>0 && sh_allotform.getAcnum().trim().length()<11 && Integer.parseInt(sh_allotform.getAcnum())!=0){
									int maxnum=shDelegate.checkShareNumber(Integer.parseInt(sh_allotform.getAcnum()),0);
									System.out.println("The max number is ------"+maxnum);
									if(Integer.parseInt(sh_allotform.getAcnum())>maxnum){
										req.setAttribute("msg","Account not found");	
									}else{
										req.setAttribute("msg","");
										ShareMasterObject shobj=null;
										shobj=shDelegate.getShareMaster(sh_allotform.getActype(), Integer.parseInt(sh_allotform.getAcnum()));
										System.out.println("Share Master Object==>"+shobj);
										if(shobj!=null){
											if(shobj.uv.getVerTml()!=null && !shobj.uv.getVerTml().equals("null")){
												req.setAttribute("msg","Account Already Verified");
											}else{
												req.setAttribute("AcTypes", shDelegate.getAcTypes());
												req.setAttribute("ShareDetails", shobj);
												req.setAttribute("PaymentDetails", shDelegate.getPaymentDetails(shobj.getNumberofShares(), shobj.getShareVal(), shobj.getAmount()));
												req.setAttribute("Disable2",false);
												if(sh_allotform.getSr_no()!=null&&sh_allotform.getSr_no().trim().length()>0){
													req.setAttribute("cashamt",shDelegate.paycash(sh_allotform.getPay_type(),sh_allotform.getActype(),shobj.getScrollno()));
												}
												req.setAttribute("Disable", true);
												req.setAttribute("disablefield",false);
												req.setAttribute("vis", "visible");
												if(shobj.getRecievedMode()!=null){
													if(shobj.getRecievedMode().equals("C")){
														AccountObject acobj=null;
														try{
															acobj=shDelegate.paycash(shobj.getRecievedMode(),shobj.getShareAccType(),shobj.getRecievedAccno());
															req.setAttribute("cashamt",acobj);
														}catch(Exception e){
															e.printStackTrace();
														}
														sh_allotform.setPay_ac_no("0");
														sh_allotform.setSr_no(String.valueOf(shobj.getScrollno()));
														sh_allotform.setPay_type("C");
														sh_allotform.setVerifyCh("C");
														req.setAttribute("CashChoice", "CashChoice");
														req.setAttribute("dontshowtrf", "Trf");
													}else if(shobj.getRecievedMode().equals("T")){
														sh_allotform.setVerifyCh("T");
													}
												}
												if(shobj!=null){
													NomineeObject[] nom1=PygmyDelegate.getPygmyDelegateInstance().getNominee(shobj.getNomineeno());
													if(nom1!=null && nom1[0]!=null){
														if(nom1[0].getCustomerId()!=0){
															req.setAttribute("vis", "visible");
															sh_allotform.setNomCid(String.valueOf(nom1[0].getCustomerId()));
														}else{
															req.setAttribute("vis", "visible");
															sh_allotform.setNomCid("0");
														}
														sh_allotform.setNomName(nom1[0].getNomineeName());
														sh_allotform.setDob(nom1[0].getNomineeDOB());
														sh_allotform.setGender(String.valueOf(nom1[0].getSex()));
														sh_allotform.setAddress(nom1[0].getNomineeAddress());
														sh_allotform.setRel_ship(nom1[0].getNomineeRelation());
														sh_allotform.setPercentage(String.valueOf(nom1[0].getPercentage()));
													}
												}
												String perpath=MenuNameReader.getScreenProperties("0001");
												req.setAttribute("panelName", "Personal Details");
												req.setAttribute("personalInfo", shDelegate.getCustomer(shobj.getCustomerId()));
												req.setAttribute("flag",perpath);
											}
										}else{
											req.setAttribute("msg","Records Not Found");
										}
									}
								}else if(sh_allotform.getAcnum().trim().length()>=11){
									req.setAttribute("msg", "Please enter Proper Account Number");
									sh_allotform.setAcnum("0");
								}
								String cid=sh_allotform.getCid();
								System.out.println("The customer id is"+cid);
								if(sh_allotform.getCid()!=null && sh_allotform.getCid().trim().length()>0 && sh_allotform.getCid().trim().length()<11 && Integer.parseInt(sh_allotform.getCid())!=0){
									int maxcid=shDelegate.checkShareNumber(Integer.parseInt(sh_allotform.getCid()),1);
									int sh_cid=shDelegate.check_for_share_holder(Integer.parseInt(sh_allotform.getCid()));
									if(Integer.parseInt(sh_allotform.getCid())> maxcid){
										req.setAttribute("msg","Customer ID Not Found");	
										sh_allotform.setCid("0");
									}else if(sh_cid==1){
									 		req.setAttribute("msg","The Customer is already a Share Holder!!");
									 		req.setAttribute("focusto", "cid");
									}else{
										String perpath=MenuNameReader.getScreenProperties("0001");
				    					req.setAttribute("flag",perpath);
									}
								}else if(sh_allotform.getCid().trim().length()>=11){
									req.setAttribute("msg","Please Enter Proper Customer ID");	
									sh_allotform.setCid("0");
								}
								if(sh_allotform.getNum_shares()!=null && sh_allotform.getNum_shares().trim().length()>0 && sh_allotform.getNum_shares().trim().length()<10){
									ShareCategoryObject[] shCatObj=null;
									try{
										shCatObj=shDelegate.getShareCategory(Integer.parseInt(sh_allotform.getSh_cat()),sh_allotform.getActype());
									}catch(Exception e){
										e.printStackTrace();
									}
									req.setAttribute("Total", shDelegate.getSharecalc(Double.parseDouble(sh_allotform.getNum_shares())));
									req.setAttribute("Sh_cal_amt", shDelegate.calculateShareAmt(shparam, Double.parseDouble(sh_allotform.getNum_shares()), 100.00, Integer.parseInt(sh_allotform.getSh_cat()),0));
								    if(Integer.parseInt(sh_allotform.getSh_cat())==1 || Integer.parseInt(sh_allotform.getSh_cat())==2 || Integer.parseInt(sh_allotform.getSh_cat())==4){
								    	if(Double.parseDouble(sh_allotform.getNum_shares())< shCatObj[0].getMinShare()){
								            req.setAttribute("msg"," You have to buy aleast "+shCatObj[0].getMinShare()+ " shares for share category "+shCatObj[0].getCatName()+"");
								            sh_allotform.setNum_shares("0");
								    	}else if(Double.parseDouble(sh_allotform.getNum_shares())>=shCatObj[0].getMinShare()){
								    		req.setAttribute("msg","");
								    		req.setAttribute("Total", shDelegate.getSharecalc(Double.parseDouble(sh_allotform.getNum_shares())));
											req.setAttribute("Sh_cal_amt", shDelegate.calculateShareAmt(shparam, Double.parseDouble(sh_allotform.getNum_shares()), 100.00, Integer.parseInt(sh_allotform.getSh_cat()),0));
								    	}
								    }
								}
								
								if(sh_allotform.getIntro_ac_num()!=null && sh_allotform.getIntro_ac_num().trim().length()>0 && sh_allotform.getIntro_ac_num().trim().length()<11 && Integer.parseInt(sh_allotform.getIntro_ac_num())!=0){
									AccountObject acc_obj= shDelegate.getIntrodet(sh_allotform.getIntro_type(), Integer.parseInt(sh_allotform.getIntro_ac_num()));
									if(acc_obj==null){
										req.setAttribute("msg","Introducer Not Found");
									}else{
										String intro_code=sh_allotform.getIntro_type();
										String intr_no=sh_allotform.getIntro_ac_num();
										String perpath=MenuNameReader.getScreenProperties("0001");
										AccountObject acntObject=shDelegate.getIntrodet(intro_code,Integer.parseInt(intr_no));
										if(acntObject!=null){
											req.setAttribute("panelName", "Introducer Details");
											req.setAttribute("personalInfo",shDelegate.getCustomer(acntObject.getCid()));
										}else{
											req.setAttribute("flag",perpath);
											req.setAttribute("panelName", CommonPanelHeading.getIntroucer());
										}
									}
								}else if(sh_allotform.getIntro_ac_num().trim().length()>=11){
									req.setAttribute("msg", "Please Enter Proper Introducer ID");
									sh_allotform.setIntro_ac_num("0");
								}
								
								if(sh_allotform.getDiv_paymode().trim().equalsIgnoreCase("C")){
									req.setAttribute("Butt_disable",true);
									req.setAttribute("hideTransfer", "yes");
									req.setAttribute("disablefield",true);
								}
								
								if(sh_allotform.getPay_type().trim().equalsIgnoreCase("C")){
									if(sh_allotform.getSr_no()!=null&&sh_allotform.getSr_no().trim().length()>0&&Integer.parseInt(sh_allotform.getSr_no())!=0){
										String scroll_nos[][]=null;
										AccountObject ac_obj=shDelegate.paycash(sh_allotform.getPay_type(),sh_allotform.getActype(),Integer.parseInt(sh_allotform.getSr_no()));
										AccountObject accobj[]=shDelegate.getAccounts(sh_allotform.getActype().toString());
										if(accobj!=null){
											scroll_nos=new String[accobj.length][3];
											for(int i=0;i<accobj.length;i++){
												scroll_nos[i][0]=String.valueOf(accobj[i].getCid());
												scroll_nos[i][1]=String.valueOf(accobj[i].getAccno());
												scroll_nos[i][2]=String.valueOf(accobj[i].getAmount());
											}
										}
										if(ac_obj==null){
											req.setAttribute("msg","Scroll number not found");
										}else if(ac_obj.getVerified().trim().length()>0){
											req.setAttribute("msg","Scroll number Already Verified");	
										}else{
											req.setAttribute("displaysh","displaysh");
											ac_obj=shDelegate.paycash(sh_allotform.getPay_type(),sh_allotform.getActype(),Integer.parseInt(sh_allotform.getSr_no()));
											double num_of_shares_c=Double.parseDouble((sh_allotform.getNum_shares()));
											double sh_amt_c=100.00;
											sh_allotform.setSr_no(String.valueOf(ac_obj.getScrollno()));
											sh_allotform.setName(ac_obj.getAccname());
											sh_allotform.setAmt(String.valueOf(ac_obj.getAmount()));
											req.setAttribute("PaymentDetails", shDelegate.getPaymentDetails(num_of_shares_c, sh_amt_c, ac_obj.getAmount()));
											req.setAttribute("cashamt",ac_obj );
											req.setAttribute("UnverifiedScrNos",scroll_nos);
										}
									}
									req.setAttribute("CashChoice", "CashChoice");
								}
								System.out.println("Amzad ______________________Paymode type________"+sh_allotform.getPay_ac_no());
							    if(sh_allotform.getPay_ac_no()!=null && sh_allotform.getPay_ac_no().trim().length()>0 && sh_allotform.getPay_ac_no().trim().length()<11 && sh_allotform.getPay_type().equalsIgnoreCase("T")){
				                    	AccountObject acc_obj=shDelegate.getIntrodet(sh_allotform.getPay_ac_type(),Integer.parseInt(sh_allotform.getPay_ac_no()));
				                    	if(acc_obj==null){
				                    		req.setAttribute("msg","Account not found");
				                    		sh_allotform.setPay_ac_no("0");
										}else if(acc_obj.getAccStatus().equalsIgnoreCase("C")){
				                    		req.setAttribute("msg","Account is Closed");
				                    		sh_allotform.setPay_ac_no("0");
				                    	}else{
				                    		double sh_amt=shDelegate.calculateShareAmt(shparam, Double.parseDouble(sh_allotform.getNum_shares()), 100.00, Integer.parseInt(sh_allotform.getSh_cat()),0);
				                    		double min_bal=shDelegate.getBalance(sh_allotform.getPay_type(),sh_allotform.getPay_ac_type(), Integer.parseInt(sh_allotform.getPay_ac_no()),sh_amt);
											if(min_bal<sh_amt){
												req.setAttribute("msg","No Minimum balance in the account after the transfer");
											}else{
												double num_of_shares=Double.parseDouble(sh_allotform.getNum_shares().trim());
												sh_amt=100.00;
												double amt_recvd=Double.parseDouble(sh_allotform.getAmount());
												req.setAttribute("PaymentDetails", shDelegate.getPaymentDetails(num_of_shares, sh_amt, amt_recvd));
												req.setAttribute("Bal", shDelegate.getBalance(sh_allotform.getPay_type(),sh_allotform.getPay_ac_type(), Integer.parseInt(sh_allotform.getPay_ac_no()),sh_amt));
												req.setAttribute("focusto", "pay_ac_type");
												req.setAttribute("hideTransfer", "no");
											}
				                    	}
				                  	}else if(sh_allotform.getPay_ac_no().trim().length()>=11){
				                  		req.setAttribute("msg", "Please Enter Proper Payment Account Number");
				                  		sh_allotform.setPay_ac_no("0");
				                  	}				
							       	if(sh_allotform.getNomineeChoice()!=null){
							       		if(sh_allotform.getNomineeChoice().equalsIgnoreCase("yes")){
							       			req.setAttribute("addnomineechoice", "yes");
							       		}else{
							       			req.setAttribute("addnomineechoice", "no");
							       		}
							       	}
							       	if(sh_allotform.getNum_shares()!=null && sh_allotform.getAmount()!=null){
							       		if(sh_allotform.getNum_shares().trim().length()!=0 && sh_allotform.getAmount().trim().length()!=0){
							       			double sh_amt=shDelegate.calculateShareAmt(shparam, Double.parseDouble(sh_allotform.getNum_shares()), 100.00, Integer.parseInt(sh_allotform.getSh_cat()),0);
							       			req.setAttribute("PaymentDetails", shDelegate.getPaymentDetails(Double.parseDouble(sh_allotform.getNum_shares().trim()), sh_amt, Double.parseDouble(sh_allotform.getAmount())));
							       		}
							       	}
							       	if(sh_allotform.getFlag()!=null){
							       		if(sh_allotform.getFlag().equalsIgnoreCase("nomineeHasAccount")){
							       			if(sh_allotform.getHas_ac()!=null && sh_allotform.getHas_ac().equalsIgnoreCase("hasAccount"))
							       				sh_allotform.setHas_ac("hasAccount");
							       			if(sh_allotform.getNomineeChoice()!=null && sh_allotform.getNomineeChoice().equalsIgnoreCase("yes")){
							       				req.setAttribute("addnomineechoice", "yes");
							       			}
							       			req.setAttribute("vis", "visible");  
							       		}
							       		if(sh_allotform.getFlag().equalsIgnoreCase("nomineeCid")){
							       			CustomerMasterObject cust_obj=shDelegate.getCustomer(Integer.parseInt(sh_allotform.getNomCid()));
											req.setAttribute("custdetails",cust_obj); 
											req.setAttribute("vis", "visible");
							       		}
							       	}
							       	if(sh_allotform.getAcnum()!=null && sh_allotform.getCid()!=null){
							       		if(sh_allotform.getCombo_details().trim().equalsIgnoreCase("Personal Details") && sh_allotform.getCid().trim().length()<11){
					    					String perpath=MenuNameReader.getScreenProperties("0001");
					    				    getTabbedpaneAllotment(req,sh_allotform);
					    				    req.setAttribute("panelName", "Personal Details");
					    				    CustomerMasterObject cmObj=shDelegate.getCustomer(Integer.parseInt(sh_allotform.getCid()));
					    				    if(cmObj!=null){
					    				    	req.setAttribute("personalInfo", cmObj);
					    				    }else if(req.getAttribute("msg")==null && cmObj==null){
					    				    	req.setAttribute("msg", "Customer Id is Not Found");
					    				    	req.setAttribute("personalInfo", null);
					    				    }else{
					    				    	req.setAttribute("personalInfo", null);
					    				    }
					    					req.setAttribute("TabNum", "0");
					    					req.setAttribute("flag",perpath);
					    					req.setAttribute("focusto", "div_paymode");
					    					System.out.println("Personal Details are displayed in action class");
					    			    }else if(sh_allotform.getCombo_details().trim().equalsIgnoreCase("Intorducer Details") && sh_allotform.getIntro_ac_num()!=null && sh_allotform.getIntro_ac_num().trim().length()<11){
					                    	String perPath=MenuNameReader.getScreenProperties("0001");
					                    	AccountObject acntObject=shDelegate.getIntrodet(sh_allotform.getIntro_type(), Integer.parseInt(sh_allotform.getIntro_ac_num()));
					                    	req.setAttribute("Introducer Type",acntObject);
					                    	req.setAttribute("TabNum","1");
					                    	if(acntObject!=null){
					                    		req.setAttribute("panelName", "Introducer Details");
					                    		CustomerMasterObject cmObj=shDelegate.getCustomer(acntObject.getCid());
					                    		if(cmObj!=null){
													req.setAttribute("personalInfo",cmObj);
												}else if(req.getAttribute("msg")==null && cmObj==null){
													req.setAttribute("msg", "Customer Id not found");
													req.setAttribute("personalInfo", null);
												}else{
													req.setAttribute("personalInfo", null);
												}
					                    	}else{		
					                    		req.setAttribute("panelName", CommonPanelHeading.getIntroucer());
					                    		req.setAttribute("personalInfo",new CustomerMasterObject());
					                    	}
					                    	req.setAttribute("flag",perPath);
					                    	req.setAttribute("panelName", CommonPanelHeading.getIntroucer());
					                    	req.setAttribute("focusto", "div_paymode");
					                    }else if(sh_allotform.getCombo_details().trim().equalsIgnoreCase("Signature Details")){
					                    	String perPath=MenuNameReader.getScreenProperties("4041");
					                    	SignatureInstructionObject[] acntObject=null;
					                    	if(Integer.parseInt(sh_allotform.getIntro_ac_num())!=0 && Integer.parseInt(sh_allotform.getIntro_ac_num())>0 && sh_allotform.getIntro_type()!=null){
					                    	 acntObject=shDelegate.getSignature(Integer.parseInt(sh_allotform.getIntro_ac_num()),sh_allotform.getIntro_type());
					                    	System.out.println("acntObject.getCid()=="+acntObject);
					                    	req.setAttribute("Introducer Type",acntObject);
					                    	req.setAttribute("TabNum","2");
					                    	if(acntObject!=null){
					                    		req.setAttribute("panelName", CommonPanelHeading.getSignature());
					                    		CustomerMasterObject cmObj=shDelegate.getCustomer(Integer.parseInt(sh_allotform.getCid()));
					                    		if(cmObj!=null){
													req.setAttribute("personalInfo",cmObj);
												}else if(req.getAttribute("msg")==null && cmObj==null){
													req.setAttribute("msg", "Customer Id not found");
													req.setAttribute("personalInfo", null);
												}else{
													req.setAttribute("personalInfo", null);
												}
					                    		req.setAttribute("sigObject",acntObject);
					                    	}
					                    	}else{
					                    		req.setAttribute("msg", "Please Enter the Introducer Number");
					                    	}
					                    	req.setAttribute("flag",perPath);
					                    	req.setAttribute("panelName", CommonPanelHeading.getSignature());
					                    	req.setAttribute("focusto", "div_paymode");
					                    }
								     }else{
								    	 return map.findForward(ResultHelp.getSuccess());
								     }
								}else{
									path=MenuNameReader.getScreenProperties("0000");
									setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
									return map.findForward(ResultHelp.getError());
								}
								System.out.println("I am forwardddddddddddddddddddddd"+sh_allotform.getForward());
								if(sh_allotform.getFlag()!=null){
									if(sh_allotform.getFlag().equalsIgnoreCase("from_div_acno")){
										AccountObject act=null;
										if(sh_allotform.getDiv_ac_type()!=null && sh_allotform.getDiv_ac_num()!=null && sh_allotform.getDiv_ac_num().trim().length()>0 && sh_allotform.getDiv_ac_num().trim().length()<11){
										act=shDelegate.getIntrodet(sh_allotform.getDiv_ac_type(), Integer.parseInt(sh_allotform.getDiv_ac_num()));
											if(act==null){
												req.setAttribute("msg","Account Number not Found");
												sh_allotform.setDiv_ac_num("0");
											}
										}
									}
									if(sh_allotform.getFlag().equalsIgnoreCase("submit")){
										System.out.println("*******************Gettin the info of shares***************");
										NomineeObject[] nom=null;
										if(sh_allotform.getNomineeChoice()!=null){
											nom=new NomineeObject[1];
											nom[0]=new NomineeObject();
											nom[0].setRegNo(0);
											nom[0].setAccType(sh_allotform.getActype());
											if(sh_allotform.getNomCid().length()>0){
												nom[0].setCustomerId(Integer.parseInt(sh_allotform.getNomCid()));
											}else{
												nom[0].setCustomerId(0);
											}
											nom[0].setNomineeName(sh_allotform.getNomName());
											nom[0].setNomineeDOB(sh_allotform.getDob());
											nom[0].setNomineeAddress(sh_allotform.getAddress());
											nom[0].setNomineeRelation(sh_allotform.getRel_ship());
											nom[0].setPercentage(Double.parseDouble(sh_allotform.getPercentage()));
											nom[0].setFromDate(sh_allotform.getAppdate());
										}
										ShareMasterObject sh=new ShareMasterObject();
										sh.setNomineeDetails(nom);
										sh.setShareAccType(sh_allotform.getActype());
										sh.setCustomerId(Integer.parseInt(sh_allotform.getCid()));
										sh.setIssueDate(sh_allotform.getAppdate());
										if(sh_allotform.getSh_type().equalsIgnoreCase("Direct")){
											sh.setShareStatus("P");
										}else{
											sh.setShareStatus("T");
										}
										sh.setShareType(Integer.parseInt(sh_allotform.getSh_cat()));
										sh.setBranchCode(Integer.parseInt(sh_allotform.getBranch()));
										sh.setNumberofShares(Double.parseDouble(sh_allotform.getNum_shares()));
										sh.setShareVal(Double.parseDouble(sh_allotform.getSh_amt()));
										sh.setMemberCategory(Integer.parseInt(sh_allotform.getSh_cat()));
										sh.setIntroducerAcctype(sh_allotform.getIntro_type());
										sh.setIntroducerAccno(Integer.parseInt(sh_allotform.getIntro_ac_num()));
										if(sh_allotform.getDiv_paymode().equalsIgnoreCase("C")){
											sh.setPayMode(sh_allotform.getDiv_paymode());
											sh.setPaymentAcctype("");
											sh.setPaymentAccno(0);
										}else{
											sh.setPayMode(sh_allotform.getDiv_paymode());
											sh.setPaymentAcctype(sh_allotform.getDiv_ac_type());
											sh.setPaymentAccno(Integer.parseInt(sh_allotform.getDiv_ac_num()));
										}
										if(sh_allotform.getPay_type().equalsIgnoreCase("C")&&sh_allotform.getSr_no()!=null){
											sh.setRecievedMode(sh_allotform.getPay_type());
											sh.setRecievedAcctype(sh_allotform.getActype());
											sh.setRecievedAccno(Integer.parseInt(sh_allotform.getSr_no()));
										}else{
											sh.setRecievedMode(sh_allotform.getPay_type());
											sh.setRecievedAcctype(sh_allotform.getPay_ac_type());
											sh.setRecievedAccno(Integer.parseInt(sh_allotform.getPay_ac_no()));
										}
										sh.setAmount(Double.parseDouble(sh_allotform.getAmount()));
										SignatureInstructionObject[] sig_obj=new SignatureInstructionObject[7];
										sig_obj[0]=new SignatureInstructionObject();
										sig_obj[0].setSAccType(sh_allotform.getActype());
										sig_obj[1]=new SignatureInstructionObject();
										sig_obj[1].setCid(Integer.parseInt(sh_allotform.getCid()));
										sig_obj[2]=new SignatureInstructionObject();
										sig_obj[2].setName(sh_allotform.getSname());
										sig_obj[3]=new SignatureInstructionObject();
										sig_obj[3].setDesignation("");
										sig_obj[4]=new SignatureInstructionObject();
										sig_obj[4].setMinLimit(0);
										sig_obj[5]=new SignatureInstructionObject();
										sig_obj[5].setMaxLimit(100);
										sig_obj[6]=new SignatureInstructionObject();
										sig_obj[6].setTypeOfOperation(sh_allotform.getSign());
										sh.setSignatureDetails(sig_obj);
										// default values goes here
										sh.setNomineeno(1260);
										sh.setMailingAddress(1);
										sh.setNumberCert(1111);
										sh.setLoanAvailed("F");
										sh.setDivUptoDate(sh_allotform.getAppdate());
										sh.setCloseDate(null);
										sh.setTransferDate(sh_allotform.getAppdate());
										sh.setLdgPrntDate(null);
										sh.setRelationtoDirector(null);
										sh.setRelationDesc(null);
										sh.setTrnSeq(0);
										sh.uv.setUserTml(shtml);
										sh.uv.setUserId(shuser);
										sh.uv.setUserDate(sh_allotform.getAppdate());
										sh.uv.setVerDate(sh_allotform.getAppdate());
										sh.uv.setVerTml(shtml);
										sh.uv.setVerId(shuser);
										System.out.println("*******************Gettin out of info of shares***************");
										int number=0;
										if(req.getAttribute("msg1")==null)
											number=shDelegate.storeShare(sh);
										System.out.println("The share acount number is"+number);
										if(number!=0){
											req.setAttribute("msg1","Share Number generated is   "+String.valueOf(number));
											req.setAttribute("pagenum",4041);
											ModuleObject[] mod_obj=shDelegate.getAllAc_types();
											req.setAttribute("AllAc_types", mod_obj);
											
											req.setAttribute("shnum",number);
											
										}else{
											req.setAttribute("msg", "Share Account is not generated properly");
										}
									}
								}
								if(sh_allotform.getForward().equalsIgnoreCase("Update")){
									System.out.println("*******************Gettin the Updated info of shares***************");
									ShareMasterObject sh=new ShareMasterObject();
									sh.setShareAccType(sh_allotform.getActype());
									sh.setCustomerId(Integer.parseInt(sh_allotform.getCid()));
									sh.setIssueDate(sh_allotform.getAppdate());
									sh.setShareStatus(sh_allotform.getSh_type());
									sh.setShareType(Integer.parseInt(sh_allotform.getSh_cat()));
									sh.setBranchCode(Integer.parseInt(sh_allotform.getBranch()));
									sh.setNumberofShares(Double.parseDouble(sh_allotform.getNum_shares()));
									sh.setShareVal(Double.parseDouble(sh_allotform.getSh_amt()));
									sh.setMemberCategory(Integer.parseInt(sh_allotform.getSh_cat()));
									sh.setIntroducerAcctype(sh_allotform.getIntro_type());
									sh.setIntroducerAccno(Integer.parseInt(sh_allotform.getIntro_ac_num()));
									if(sh_allotform.getDiv_paymode().equalsIgnoreCase("C")){
										sh.setPayMode(sh_allotform.getDiv_paymode());
										sh.setPaymentAcctype("");
										sh.setPaymentAccno(0);
									}else{
										sh.setPayMode(sh_allotform.getDiv_paymode());
										sh.setPaymentAcctype(sh_allotform.getDiv_ac_type());
										sh.setPaymentAccno(Integer.parseInt(sh_allotform.getDiv_ac_num()));
									}
									if(sh_allotform.getPay_type().equalsIgnoreCase("C")){
										sh.setRecievedMode(sh_allotform.getPay_type());
										sh.setRecievedAcctype("");
										sh.setRecievedAccno(0);
									}else{
										sh.setRecievedMode(sh_allotform.getPay_type());
										sh.setRecievedAcctype(sh_allotform.getPay_ac_type());
										sh.setRecievedAccno(Integer.parseInt(sh_allotform.getPay_ac_no()));
									}
									sh.setAmount(Double.parseDouble(sh_allotform.getAmount()));
									int number=shDelegate.storeShare(sh);
									System.out.println("The share acount number is updated successfully");
									if(number!=0){
									   req.setAttribute("msg","Share Number is updated successfully");
									}else{
										req.setAttribute("msg", "Share Number is not updated successfully");
									}
								}
								if(sh_allotform.getForward().equalsIgnoreCase("delete")){
									System.out.println("Inside deletion of Share Account");
									int result=shDelegate.delete_share(Integer.parseInt(sh_allotform.getAcnum()),sh_allotform.getActype());
									if(result>0){
										req.setAttribute("msg","Share Number Deleted Successfully");
									}else{
										req.setAttribute("msg","Share Number is not deleted");
									}
								}
								if(sh_allotform.getForward().equalsIgnoreCase("Verify")){
									System.out.println("=====GOING TO VERIFY=====");
									ShareMasterObject sh=new ShareMasterObject();
									shobj=shDelegate.getShareMaster(sh_allotform.getActype(), Integer.parseInt(sh_allotform.getAcnum()));
									if(shobj.getVe_user()!=null && shobj.getVe_user().trim().length()!=0)
									{
										req.setAttribute("msg","Share Number Already  Verified");
									}else{																		
									ShareParamObject shparam[]=null;
									sh.setShareAccType(sh_allotform.getActype());
									sh.setShareNumber(Integer.parseInt(sh_allotform.getAcnum()));
									sh.setCustomerId(Integer.parseInt(sh_allotform.getCid()));
									sh.setIssueDate(sh_allotform.getAppdate());
									sh.setNumberofShares(Double.parseDouble(sh_allotform.getNum_shares()));
									sh.setShareVal(Double.parseDouble(sh_allotform.getSh_val()));
									sh.setMemberCategory(Integer.parseInt(sh_allotform.getSh_cat()));
									if(sh_allotform.getSh_type().equalsIgnoreCase("Direct")){
										sh.setShareStatus("P");
									}else{
										sh.setShareStatus("T");
									}
									sh_allotform.setSign("single");
									sh_allotform.setCombo_details("Personal Details");
									sh.setShareType(Integer.parseInt(sh_allotform.getSh_cat()));
									sh.setBranchCode(Integer.parseInt(sh_allotform.getBranch()));
									sh.setNumberofShares(Double.parseDouble(sh_allotform.getNum_shares()));
									sh.setIntroducerAcctype(sh_allotform.getIntro_type());
									sh.setIntroducerAccno(Integer.parseInt(sh_allotform.getIntro_ac_num()));
									if(sh_allotform.getDiv_paymode().equalsIgnoreCase("C")){
										sh.setPayMode(sh_allotform.getDiv_paymode());
										sh.setPaymentAcctype("");
										sh.setPaymentAccno(0);
									}else{
										sh.setPayMode(sh_allotform.getDiv_paymode());
										sh.setPaymentAcctype(sh_allotform.getDiv_ac_type());
										sh.setPaymentAccno(Integer.parseInt(sh_allotform.getDiv_ac_num()));
									}
									if(sh_allotform.getPay_type().equalsIgnoreCase("C")){
										sh.setRecievedMode("C");
										sh.setRecievedAcctype("");
										sh.setRecievedAccno(shobj.getRecievedAccno());
									}else{
										sh.setRecievedMode("T");
										sh.setRecievedAcctype(sh_allotform.getPay_ac_type());
										sh.setRecievedAccno(Integer.parseInt(sh_allotform.getPay_ac_no()));
									}
									// default values goes here
									sh.setNomineeno(1260);
									sh.setNumberCert(1111);
									sh.setLoanAvailed("F");
									sh.setDivUptoDate(sh_allotform.getAppdate());
									sh.setCloseDate(null);
									sh.setTransferDate(sh_allotform.getAppdate());
									sh.setLdgPrntDate(null);
									sh.setRelationtoDirector(null);
									sh.setRelationDesc(null);
									sh.setTrnSeq(0);
									sh.setAmount(Double.parseDouble(sh_allotform.getAmount()));
									sh.setMiscAmount(Double.parseDouble(sh_allotform.getMisc()));
									sh.uv.setUserTml(shtml);
									sh.uv.setUserId(shuser);
									sh.uv.setUserDate(sh_allotform.getAppdate());
									sh.uv.setVerDate(sh_allotform.getAppdate());
									sh.uv.setVerId(shuser);
									sh.uv.setVerTml(shtml);
									shparam=shDelegate.getshparam(Integer.parseInt(sh_allotform.getSh_cat()),sh_allotform.getActype());
									String gl0="";
									String gl1="";
									String gl2="";
									System.out.println("THE LENGTH OF SHARE PARAM IS"+shparam.length);
									for(int i=0;i<shparam.length;i++){
										gl0=shparam[0].getPrm_gl_key();
										gl1=shparam[1].getPrm_gl_key();
										gl2=shparam[2].getPrm_gl_key();
									}
									shparam[0]=new ShareParamObject();
									shparam[0].setPrm_amt(Double.parseDouble(sh_allotform.getSh_fee()));
									shparam[0].setPaid(true);
									shparam[0].setPrm_gl_key(gl0);
									shparam[1]=new ShareParamObject();
									shparam[1].setPrm_amt(Double.parseDouble(sh_allotform.getAd_fee()));
									shparam[1].setPaid(true);
									shparam[1].setPrm_gl_key(gl1);
									shparam[2]=new ShareParamObject();
									shparam[2].setPrm_amt(Double.parseDouble(sh_allotform.getSh_sale()));
									shparam[2].setPaid(true);
									shparam[2].setPrm_gl_key(gl2);
									int ver_result=shDelegate.verifyshare(sh, shparam);
									if(ver_result>0){
										req.setAttribute("msg","Share Number1 Verified Successfully");
									}else{
										req.setAttribute("msg","Error while Verifying ");
									}
								}
								}
							}catch (Exception e){
								path=MenuNameReader.getScreenProperties("0000");
								e.printStackTrace();
								setErrorPageElements(""+e, req, path);
								return map.findForward(ResultHelp.getError());
							}
    	    		}else if(map.getPath().trim().equalsIgnoreCase("/Share/Signature")){
						SignatureInstructionForm siForm=(SignatureInstructionForm)form;
				    	AllotmentForm sh_allotform=siForm.getShareForm();
				    	String path=null;
				    	int signFlag=1;
				    	System.out.println("I am in /share/signature");
				    	try{
				    		req.setAttribute("pageNum",sh_allotform.getPageId().trim());
				    		if(MenuNameReader.containsKeyScreen(sh_allotform.getPageId())){
				    			path=MenuNameReader.getScreenProperties(sh_allotform.getPageId());
				                String perPath=MenuNameReader.getScreenProperties("0004");
				                req.setAttribute("flag",perPath);
				                SignatureInstructionObject[] signObject=shDelegate.getSignature(Integer.parseInt(sh_allotform.getAcnum()),sh_allotform.getActype());
				                req.setAttribute("SignInstActnPath","/Share/Signature");
				                req.setAttribute("panelName","Signature Instruction");
				                req.setAttribute("ShareForm", sh_allotform);
				                if(signObject!=null){
				                	req.setAttribute("signInfo",signObject);
				                	if(signFlag==1){
				                		if(siForm!=null){
				                			req.setAttribute("signInfo1",signObject[Integer.parseInt(siForm.getSignCombo().trim())]);
				                			req.setAttribute("Signature",MenuNameReader.getScreenProperties("0005"));
				                			req.setAttribute("SIFormPath","/Share/SignCombo");
				            			}	
				            		}
				            	}else{
				            		req.setAttribute("Signature",MenuNameReader.getScreenProperties("0005"));
				    				req.setAttribute("SIFormPath","/Share/SignCombo");
				            	}
				           		setShOpeningInitParams(req, path, shDelegate);
				                return map.findForward(ResultHelp.getSuccess());
				    		}
				    	}catch(Exception e){
				    		e.printStackTrace();
				    	}
    	    		}else if(map.getPath().trim().equalsIgnoreCase("/Share/ShareAllotmentVerificationMenu")){
    	    			try{
    	    				AllotmentForm sh_allotform=(AllotmentForm)form;
    	    				logger.info("This is from Shares"+map.getPath());
    	    				req.setAttribute("pagenum", sh_allotform.getPageId());
    	    				req.setAttribute("TabNum", "0");
    	    				req.setAttribute("flag", MenuNameReader.getScreenProperties("3003"));
    	    				getTabbedpaneAllotment(req,sh_allotform);
    	    				if(MenuNameReader.containsKeyScreen(sh_allotform.getPageId())){
    	    					path=MenuNameReader.getScreenProperties(sh_allotform.getPageId());
    	    					shDelegate=new ShareDelegate();
    	    					setShOpeningInitParams(req, path, shDelegate);
    	    					req.setAttribute("pageId",path);
    	    					return map.findForward(ResultHelp.getSuccess());
    	    				}else{
    	    					path=MenuNameReader.getScreenProperties("0000");
    	    					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
    	    					return map.findForward(ResultHelp.getError());
    	    				}
    	    			}catch(Exception e){
    	    				path=MenuNameReader.getScreenProperties("0000");
    	    				setErrorPageElements(""+e, req, path);
    	    				return map.findForward(ResultHelp.getError());
    	    			}
    	    		}else if(map.getPath().trim().equalsIgnoreCase("/Share/ShareAllotmentVerification")){
    	    			try{
    	    				AllotmentForm sh_allotform=(AllotmentForm)form;
    	    				logger.info("This is from Shares"+map.getPath());
    	    				req.setAttribute("pagenum", sh_allotform.getPageId());
    	    				req.setAttribute("TabNum", "0");
    	    				req.setAttribute("flag", MenuNameReader.getScreenProperties("3003"));
    	    				getTabbedpaneAllotment(req,sh_allotform);
    	    				req.setAttribute("focusto", "actype");
    	    				if(MenuNameReader.containsKeyScreen(sh_allotform.getPageId())){
    	    					path=MenuNameReader.getScreenProperties(sh_allotform.getPageId());
    	    					shDelegate=new ShareDelegate();
    	    					setShOpeningInitParams(req, path, shDelegate);
    	    					req.setAttribute("pageId",path);
    	    					return map.findForward(ResultHelp.getSuccess());
    	    				}else{
    	    					path=MenuNameReader.getScreenProperties("0000");
    	    					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
    	    					return map.findForward(ResultHelp.getError());
    	    				}
    	    			}catch(Exception e){
    	    				path=MenuNameReader.getScreenProperties("0000");
    	    				setErrorPageElements(""+e, req, path);
    	    				return map.findForward(ResultHelp.getError());
    	    			}
    	    		}else if(map.getPath().trim().equalsIgnoreCase("/Share/AdditionalAllotmentMenu")){
    	    			try{
    	    				AddAllotmentForm add_form=(AddAllotmentForm)form;
    	    				req.setAttribute("pagenum", add_form.getPageId() );
    	    				req.setAttribute("TabNum", "0");
    	    				req.setAttribute("flag", MenuNameReader.getScreenProperties("3003"));
    	    				req.setAttribute("Bal",0.0);
    	    				getTabbedpane1(req, add_form);
    	    				if(MenuNameReader.containsKeyScreen(add_form.getPageId())){
    	    					path=MenuNameReader.getScreenProperties(add_form.getPageId());
    	    					shDelegate=new ShareDelegate();
    	    					setShOpeningInitParams(req, path, shDelegate);
    	    					req.setAttribute("pageId", path);
    	    					return map.findForward(ResultHelp.getSuccess());
    	    				}else{
    	    					path=MenuNameReader.getScreenProperties("0000");
    	    					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
    	    					return map.findForward(ResultHelp.getError());
    	    				}
    	    			}catch(Exception e){
    	    				path=MenuNameReader.getScreenProperties("0000");
    	    				setErrorPageElements(""+e,req,path);
    	    				return map.findForward(ResultHelp.getError());
    	    			}
    	    		}else if(map.getPath().trim().equalsIgnoreCase("/Share/AdditionalAllotment")){
    	    			AddAllotmentForm add_form=(AddAllotmentForm)form;
			String path=null;
			String no_of_shares=null;
			
		try
		{
			req.setAttribute("Bal",0.0);
		    
			if(MenuNameReader.containsKeyScreen(add_form.getPageId()))
			{
				path=MenuNameReader.getScreenProperties(add_form.getPageId());
				shDelegate=new ShareDelegate();
				req.setAttribute("pageId",path);
				setShOpeningInitParams(req, path, shDelegate);
				System.out.println("the delegate is "+shDelegate);
			}
		/*}catch (Exception e) {
			path=MenuNameReader.getScreenProperties("0000");
			setErrorPageElements(""+e,req,path);
			return map.findForward(ResultHelp.getError());
		}*/
		//checking the conditions for additional share allotment
		
		if(Integer.parseInt(add_form.getTransaction_num())>0)
		{
			System.out.println("The tran no is"+add_form.getTransaction_num());
			ShareMasterObject shobj=null;
			try{
			shobj=shDelegate.getShare(Integer.parseInt(add_form.getTransaction_num()));
			}catch(Exception e){
				e.printStackTrace();
			}
			if(shobj!=null){
			add_form.setSh_type(shobj.getShareStatus());
			if(shobj.getRecievedMode().equalsIgnoreCase("C")){
				add_form.setPay_mode("C");
			}else if(shobj.getRecievedMode().equalsIgnoreCase("T")){
				add_form.setPay_mode("T");
				req.setAttribute("Bal", shDelegate.getBalance(shobj.getRecievedMode(),shobj.getRecievedAcctype(), shobj.getRecievedAccno(),0.0));
			}
			if(shobj.getRecievedMode().equalsIgnoreCase("C")){
			 req.setAttribute("cashamt", shDelegate.paycash(shobj.getRecievedMode().toString().trim(), shobj.getShareAccType(), shobj.getRecievedAccno()));
			}
			StringTokenizer st=null;
			ShareMasterObject shObj1=shDelegate.getShareTransaction(Integer.parseInt(add_form.getTransaction_num()));
			int no_sh=shObj1.getNumberCert();
			double sh_addallot_val=Math.floor(Double.parseDouble(shobj.getTrnamt())/100.00);
			double shares=no_sh*100.00;
			shobj.setLnk_shares(String.valueOf(no_sh));
			shobj.setTotalAmount(shares);
			//add_form.setPay_mode(shobj.getTrnMode());
			
			req.setAttribute("ShareDetails_trn",shobj );
			req.setAttribute("Disable2", false);
			if(shobj!=null){
				String perpath=MenuNameReader.getScreenProperties("0001");
				System.out.println("The path going to customer is"+perpath);
			   	req.setAttribute("panelName", "Personal Details");
				req.setAttribute("personalInfo", shDelegate.getCustomer(shobj.getCustomerId()));
				System.out.println("Suraj is setting the tab number");
				req.setAttribute("flag",perpath);
			//req.setAttribute("Total", shDelegate.getSharecalc(no_sh));	
			req.setAttribute("PaymentDetails", shDelegate.get_addPaymentDetails(no_sh, 100.00,Double.parseDouble(shobj.getTrnamt())));
			req.setAttribute("vis", "visible");
			}
		//}
			/*req.setAttribute("pagenum", add_form.getPageId());
			req.setAttribute("panelName", "Personal Details");
			req.setAttribute("TabNum", "0");
			req.setAttribute("flag", MenuNameReader.getScreenProperties("3003"));
			getTabbedpane1(req, add_form);*/
			}
			else{
				req.setAttribute("msg","Transaction Number Already Verified");
			}
		}
		if(add_form.getAc_no().length()>0)
		{
			int maxnum=shDelegate.checkShareNumber(Integer.parseInt(add_form.getAc_no()),0);
			String[] s=new String[2];
			s=shDelegate.checkShareTransaction(add_form.getAc_type(),Integer.parseInt(add_form.getAc_no()));
			System.out.println("I am in the checking transaction ");
			//commented by amzad
			/*if(s!=null){
			String shr_ind=s[1];
			System.out.println("The shr_ind is==============>"+shr_ind);
			System.out.println("The s[0] is----->"+s[0]);*/
				
			if(Integer.parseInt(add_form.getAc_no())>maxnum)
			{
				req.setAttribute("msg","Account not found");	
			}
			
			/*else if(s[0].equalsIgnoreCase("False")) 
			{commented by Amzad on 01/07/2009
				req.setAttribute("msg","Previous transaction for this account is not yet verified.");
			}
			else*/
			else{   
				ShareMasterObject shobj=shDelegate.getShareMaster(add_form.getAc_type(), Integer.parseInt(add_form.getAc_no()));
				if(shobj!=null){
				if(shobj.uv.getVerTml()==null)
				{
					req.setAttribute("msg","Previous Transaction is not yet verified");
				}
				else if(shobj.getCloseDate()!=null){
					req.setAttribute("msg","Account is Closed,Cannot allot any shares");
				}
				else
				{
					req.setAttribute("msg","");

					req.setAttribute("ShareDetails",shobj );
					req.setAttribute("panelName", "Personal Details");
					req.setAttribute("personalInfo",shDelegate.getCustomer(shobj.getCustomerId()));
					req.setAttribute("focusto","no_of_sh");
				}
			}else{
				req.setAttribute("msg","Records Not Found");
			}
			}
		}
		  if(add_form.getNo_of_sh().length()>0)
		{
			add_form.setSh_cat("1");
			no_of_shares=add_form.getNo_of_sh();
			System.out.println("The number of sharez "+add_form.getNo_of_sh());
			double no_sh=Double.parseDouble(add_form.getNo_of_sh());
			req.setAttribute("Total", shDelegate.getSharecalc(no_sh));
			req.setAttribute("Sh_cal_amt", shDelegate.calculateShareAmt(shparam, Double.parseDouble(add_form.getNo_of_sh()), 100.00, Integer.parseInt(add_form.getSh_cat()),1));
						
		}
		
		if(add_form.getPay_mode().equalsIgnoreCase("C"))
		{
		  if(Integer.parseInt(add_form.getSr_no())!=0)
		  {
			  AccountObject ac_obj=null;
			  System.out.println("*****************************going in the cash");
			  System.out.println("The scrool no is"+add_form.getSr_no());
			  ac_obj=shDelegate.paycash(add_form.getPay_mode(),add_form.getAc_type(),Integer.parseInt(add_form.getSr_no()));
			  if(ac_obj==null)
			  {
			   req.setAttribute("msg","Scroll number not found");
			  }
			  else
			  {
				  req.setAttribute("msg","");
				  add_form.setSr_no(String.valueOf(ac_obj.getScrollno()));
				  System.out.println("The amt from cash is"+ac_obj.getAmount());
				  System.out.println("The name is "+ac_obj.getAccname());
				  req.setAttribute("cashamt",ac_obj );
				  req.setAttribute("vis", "visible");
				  System.out.println("I am in CASH");
				  System.out.println("The amt is"+add_form.getAmt());
				  req.setAttribute("PaymentDetails", shDelegate.get_addPaymentDetails(Double.parseDouble(add_form.getNo_of_sh()), 100.00,ac_obj.getAmount()));
			  }
		}
		  
	}
		
		//if(Double.parseDouble(add_form.getAmt())!=0.00)
		/*if(add_form.getAmt().length()>0 && add_form.getNo_of_sh().length()>0)
		{
			double num_of_shares=Double.parseDouble(add_form.getNo_of_sh().trim());
			System.out.println("----------------------------------------->"+no_of_shares);
			double sh_amt=100.00;
			double amt_recvd=Double.parseDouble(add_form.getAmt());
			System.out.println("The amt recieved is"+amt_recvd);
			req.setAttribute("PaymentDetails", shDelegate.get_addPaymentDetails(num_of_shares, sh_amt, amt_recvd));
		}*/
				
		if(add_form.getPay_mode().equalsIgnoreCase("T"))
		{
			System.out.println("The BLANK METHOD");
		}
		
	   if(add_form.getPay_ac_no().length()>0 && add_form.getPay_ac_no()!=null && add_form.getNo_of_sh().length()>0 && add_form.getNo_of_sh()!=null)
	   {
		   System.out.println("The  pay ac_no is "+add_form.getPay_ac_type());
		   String ac_type=add_form.getPay_ac_type();
		   
		   int ac_no=Integer.parseInt(add_form.getPay_ac_no());
		   System.out.println("The  pay ac_no is "+add_form.getPay_ac_no());
		   AccountObject acc_obj=shDelegate.getIntrodet(add_form.getPay_ac_type(), Integer.parseInt(add_form.getPay_ac_no()));
		   if(acc_obj==null)
		   {
			   req.setAttribute("msg","Account not found");
		   }
		   else if(acc_obj.getAccStatus().equalsIgnoreCase("C"))
		   {
			   req.setAttribute("msg","Account is closed");
		   }
		   else
		   {
			   double sh_amt=Double.parseDouble(add_form.getAmount());
			   System.out.println("The payment details are----------------->");
			   System.out.println(add_form.getPay_mode());
			   System.out.println(ac_type);
			   System.out.println(ac_no); 
			   System.out.println(sh_amt);
			   System.out.println("Amt Recieved"+add_form.getAmtrcvd());
			   System.out.println("The amount is"+add_form.getAmount());
			   System.out.println("The amt is "+add_form.getAmt());
			   req.setAttribute("Bal", shDelegate.getBalance(add_form.getPay_mode(),ac_type, ac_no,sh_amt));
			   req.setAttribute("PaymentDetails", shDelegate.get_addPaymentDetails(Double.parseDouble(add_form.getNo_of_sh()), 100.00,Double.parseDouble(add_form.getAmount())));
			   
			   
			   
			   req.setAttribute("vis", "visible");
		   }
	   }
	  	   
           if(add_form.getCombo_details().trim().equalsIgnoreCase("Personal Details") && add_form.getAc_no()!=null && Integer.parseInt(add_form.getTransaction_num())==0)
           {
        	   ShareMasterObject shobj1=shDelegate.getShareMaster(add_form.getAc_type(), Integer.parseInt(add_form.getAc_no()));	
           	   System.out.println("Suraj is checking here");
           	
				String perpath=MenuNameReader.getScreenProperties("0001");
				System.out.println("The path going to customer is"+perpath);
				getTabbedpane1(req,add_form);
			    req.setAttribute("panelName", "Personal Details");
				req.setAttribute("personalInfo", shDelegate.getCustomer(shobj1.getCustomerId()));
				req.setAttribute("TabNum", "0");
				System.out.println("Suraj is setting the tab number");
				req.setAttribute("flag",perpath);
				System.out.println("Suraj is checking the path again");
				//req.setAttribute("focusto", "div_paymode");
		    }
           else if(add_form.getCombo_details().trim().equalsIgnoreCase("Payment Details")&&add_form.getPay_ac_no()!=null){
           	String perPath=MenuNameReader.getScreenProperties("0001");
           	AccountObject acntObject=shDelegate.getIntrodet(add_form.getPay_ac_type(), Integer.parseInt(add_form.getPay_ac_no()));
           	System.out.println("acntObject.getCid()=="+acntObject);
           	req.setAttribute("Introducer Type",acntObject);
           	req.setAttribute("TabNum","1");
           	if(acntObject!=null){
           		System.out.println("acntObject.getCid()=="+acntObject.getCid());
           		req.setAttribute("panelName", "Introducer Details");
           		CustomerMasterObject cmObj=shDelegate.getCustomer(acntObject.getCid());
           		if(cmObj!=null){
						req.setAttribute("personalInfo",cmObj);
					}else if(req.getAttribute("msg")==null && cmObj==null){
						req.setAttribute("msg", "Customer Id not found");
						req.setAttribute("personalInfo", null);
					}else{
						req.setAttribute("personalInfo", null);
					}
           	}else{		
           		req.setAttribute("panelName", CommonPanelHeading.getIntroucer());
           		req.setAttribute("personalInfo",new CustomerMasterObject());
           	}
           	req.setAttribute("flag",perPath);
           	req.setAttribute("panelName", CommonPanelHeading.getIntroucer());
           	req.setAttribute("focusto", "div_paymode");
           }
          /* else if(add_form.getCombo_details().trim().equalsIgnoreCase("Payment Details")){
        	   System.out.println("Suraj is checking in payment details here");
              	
				String perpath=MenuNameReader.getScreenProperties("0001");
				System.out.println("The path going to customer is"+perpath);
				getTabbedpane1(req,add_form);
			    req.setAttribute("panelName", "Payment Details");
			    double num_of_shares=Double.parseDouble(add_form.getNo_of_sh().trim());
				double sh_amt=100.00;
				double amt_recvd=Double.parseDouble(add_form.getAmount());
 			    req.setAttribute("details", shDelegate.get_addPaymentDetails(num_of_shares, sh_amt, amt_recvd));
				req.setAttribute("TabNum", "1");
				System.out.println("Suraj is setting the tab number");
				req.setAttribute("flag",perpath);
				System.out.println("Suraj is checking the path again");
           }*/
           
	   
	   /*if(add_form.getAmount().trim().length()!=0 )
	   {
		      
		   double num_of_shares=Double.parseDouble(add_form.getNo_of_sh().trim());
		   double sh_amt=100.00;
		   double amt_recvd=Double.parseDouble(add_form.getAmount());

		   req.setAttribute("PaymentDetails", shDelegate.get_addPaymentDetails(num_of_shares, sh_amt, amt_recvd));
	   }*/
	   	System.out.println("I am forwardddddddddddddddddddddd"+add_form.getForward());
	   	if(add_form.getForward().equalsIgnoreCase("Submit"))
	   	{

	   		ShareMasterObject sh=new ShareMasterObject();
	   		System.out.println("*****************ADD SHARE DETAILS*************************");
	   		sh.setShareAccType(add_form.getAc_type());
	   		System.out.println("Ac Type"+add_form.getAc_type());
	   		sh.setShareNumber(Integer.parseInt(add_form.getAc_no()));
	   		System.out.println("Ac No"+add_form.getAc_no());
	   		sh.setNumberofShares(Double.parseDouble(add_form.getNo_of_sh()));
	   		System.out.println("No of Shares"+Double.parseDouble(add_form.getNo_of_sh()));
	   		sh.setShareStatus(add_form.getSh_type());
	   		System.out.println("Share Type"+add_form.getSh_type());
	   		sh.setMemberCategory(Integer.parseInt(add_form.getSh_cat()));
	   		System.out.println("Category"+Integer.parseInt(add_form.getSh_cat()));
	   		sh.setIssueDate(ShareDelegate.getSysDate());
	   		sh.setAmount(Double.parseDouble(add_form.getAmtrcvd()));
           // sh.setLnk_shares(add_form.getNo_of_sh());
	   		sh.setRecievedMode(add_form.getPay_mode());
	   		
	   		//added on 23/02/2009
	   		
	   		if(add_form.getPay_mode().equalsIgnoreCase("C")){
	   		  /*sh.setCashAccountName(add_form.getName());
	   		  System.out.println("Name"+add_form.getName());
	   		  */
	   		  sh.setRecievedAccno(Integer.parseInt(add_form.getSr_no()));
	   		  System.out.println("Scroll number"+add_form.getSr_no());
	   		  /*sh.setCashAmount(Double.parseDouble(add_form.getAmt()));
	   		  System.out.println(add_form.getAmt());*/
	   		  sh.setAmount_paid(Double.parseDouble(add_form.getSh_amt()));
	   		  System.out.println("The amount from cash is"+add_form.getSh_amt());
	   			
	   		}
	   		else{
	   		sh.setRecievedAcctype(add_form.getPay_ac_type());
	   		System.out.println("The recievd ac_type is"+add_form.getPay_ac_type());
	   		sh.setRecievedAccno(Integer.parseInt(add_form.getPay_ac_no()));
	   		System.out.println("The ac no is "+Integer.parseInt(add_form.getPay_ac_no()));
            //sh.setTotalAmount(Double.parseDouble(add_form.getSh_amt()));
	   		sh.setAmount_paid(Double.parseDouble(add_form.getAmount()));
	   		}
	   		//sh.setTrnamt(add_form.getSh_amt());
	   		sh.uv.setUserTml(shtml);
	   		sh.uv.setUserId(shuser);
	   		sh.uv.setUserDate(ShareDelegate.getSysDate());
	   		sh.uv.setVerDate(ShareDelegate.getSysDate());
	   		sh.uv.setVerTml(shuser);
	   		sh.uv.setVerId(shtml);
	   		sh.setShareVal(100.00);

	   		System.out.println("**************************************************************");

	   		//req.setAttribute("Storeaddshare", shDelegate.store_addshare(shobj));
	   		int trn_no=shDelegate.store_addshare(sh);
	   		System.out.println("The transaction number is==="+trn_no);
	   		if(trn_no!=0)
	   		{
			    req.setAttribute("msg","Transaction Number is"+String.valueOf(trn_no));
		    }
		   else
		   {
			   req.setAttribute("msg","");
		   }
		   
	   }
	   	
	   	
	   if(add_form.getForward().equalsIgnoreCase("Verify")){
		   System.out.println("**************"+add_form.getForward());
		  
		   shobj=shDelegate.getShareMaster(add_form.getAc_type(), Integer.parseInt(add_form.getAc_no()));
		  
			System.out.println("GOING TO VERIFY");
			ShareMasterObject sh=new ShareMasterObject();
			System.out.println("1");
		
			ShareParamObject shparam[]=null;
			System.out.println("11");
			sh.setShareAccType(add_form.getAc_type());
			System.out.println("111");
			sh.setShareNumber(Integer.parseInt(add_form.getAc_no()));
			System.out.println("4");
			sh.setNumberofShares(Double.parseDouble(add_form.getNo_of_sh()));
			System.out.println("5");
			System.out.println("THE MEM CATEGORY IS"+add_form.getSh_cat());
			sh.setMemberCategory(Integer.parseInt(add_form.getSh_cat()));
			System.out.println("The SHARE STATUS IS "+add_form.getSh_type());
		
			sh.setShareVal(100.00);
			sh.setShareStatus(add_form.getSh_type());
			System.out.println("The share status is ----------->"+add_form.getSh_type());
			/*if(add_form.getSh_type().equalsIgnoreCase("Direct"))
			{
				sh.setShareStatus("P");
			}
			else
			{
				sh.setShareStatus("T");
			}
			
*/			
			sh.setShareType(Integer.parseInt(add_form.getSh_cat()));
			sh.setBranchCode(Integer.parseInt(add_form.getBranch_code()));
			sh.setNumberofShares(Double.parseDouble(add_form.getNo_of_sh()));
			
			System.out.println("66");
			
			
			System.out.println("THE RECIEVED TYPE IS "+add_form.getPay_mode());
			if(add_form.getPay_mode().equalsIgnoreCase("C"))
			{
				sh.setRecievedMode("");
				sh.setRecievedAcctype("");
				sh.setRecievedAccno(shobj.getRecievedAccno());
			}
			else
			{
				sh.setRecievedMode(add_form.getPay_mode());
				System.out.println("In T Values"+add_form.getPay_mode());
			    sh.setRecievedAcctype(add_form.getPay_ac_type());
			    System.out.println("In T Values"+add_form.getPay_ac_type());
			    sh.setRecievedAccno(Integer.parseInt(add_form.getPay_ac_no()));
			    System.out.println("In T Values"+Integer.parseInt(add_form.getPay_ac_no()));
			}
			
			
			// default values goes here
			
			
			sh.setAmount(Double.parseDouble(add_form.getAmount()));
			System.out.println("Amount is"+add_form.getAmount());
			
			sh.setMiscAmount(Double.parseDouble(add_form.getMisc()));
			sh.uv.setUserTml(shtml);
			sh.uv.setUserId(shuser);
			sh.uv.setUserDate(add_form.getDate());
			sh.uv.setVerDate(add_form.getDate());
			sh.uv.setVerId(shuser);
			sh.uv.setVerTml(shtml);

			shparam=shDelegate.getshparam(Integer.parseInt(add_form.getSh_cat()),add_form.getAc_type());
			String gl0="";
			String gl1="";
			String gl2="";
			System.out.println("THE LENGTH OF SHARE PARAM IS"+shparam.length);
			for(int i=0;i<shparam.length;i++)
			{
				System.out.println(shparam[i].getPrm_gl_key());
				System.out.println(shparam[i].getPrm_desc());
				gl0=shparam[0].getPrm_gl_key();
				gl1=shparam[1].getPrm_gl_key();
				gl2=shparam[2].getPrm_gl_key();
			}
			
			shparam[0]=new ShareParamObject();
			shparam[0].setPrm_amt(Double.parseDouble(add_form.getSh_fee()));
			shparam[0].setPaid(true);
			shparam[0].setPrm_gl_key(gl0);
			shparam[1]=new ShareParamObject();
			shparam[1].setPrm_amt(Double.parseDouble(add_form.getAddshfee()));
			shparam[1].setPaid(true);
			shparam[1].setPrm_gl_key(gl1);
			shparam[2]=new ShareParamObject();
			System.out.println("The prm gl type 2 is "+shparam[2].getPrm_gl_key());
			int ver_result=shDelegate.verifyAddshare(sh, shparam,Integer.parseInt(add_form.getTransaction_num()));
			if(ver_result>0)
			{
				req.setAttribute("msg","Share Number Verified Successfully");
			}
	   //now added
			else
			{
				req.setAttribute("msg","Error while Verifying ");
			}
		}
	   

	}catch (Exception e) 
		{
			path=MenuNameReader.getScreenProperties("0000");
			setErrorPageElements(""+e,req,path);
			e.printStackTrace();
			return map.findForward(ResultHelp.getError());
		}
	   
	}
		
		// for Share Dividend
		
	   else if(map.getPath().trim().equalsIgnoreCase("/Share/DividendCalMenu"))
	   {
		   try{
			   DividendCalc divcal=(DividendCalc)form;
			   req.setAttribute("pagenum", divcal.getPageId());
			   
			   if(MenuNameReader.containsKeyScreen(divcal.getPageId()))
			   {
				   path=MenuNameReader.getScreenProperties(divcal.getPageId());
				   shDelegate=new ShareDelegate();
				   setShOpeningInitParams(req, path, shDelegate);
				   req.setAttribute("pageId", path);
				   req.setAttribute("focusto","from_dt");
				   
				   return map.findForward(ResultHelp.getSuccess());
			   }
			   else
			   {
				   path=MenuNameReader.getScreenProperties("0000");
				   setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
				   return map.findForward(ResultHelp.getError());
			   }
			   
		   }
		   catch(Exception e)
		   {
			   path=MenuNameReader.getScreenProperties("0000");
			   setErrorPageElements(""+e,req,path);
			   return map.findForward(ResultHelp.getError());
		   }
		   
	   }
		
	   else if(map.getPath().trim().equalsIgnoreCase("/Share/DividendCal"))
	   {
		   DividendCalc divcal=(DividendCalc)form;
		   String path;
		   try{
				req.setAttribute("pagenum", divcal.getPageId());
				if(MenuNameReader.containsKeyScreen(divcal.getPageId()))
				{

					path=MenuNameReader.getScreenProperties(divcal.getPageId());
					shDelegate=new ShareDelegate();
					req.setAttribute("pageId",path);
					setShOpeningInitParams(req, path, shDelegate);
					System.out.println("the delegate is in Dividend calculation "+shDelegate);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		//checking conditions for dividend calculation	
			
			
			if(divcal.getForward().equalsIgnoreCase("Set Rate"))
			{
				System.out.println("$$$$$$$$$$"+divcal.getForward());
				System.out.println("The from date is  "+divcal.getFrom_dt());
				System.out.println("The to date is "+divcal.getTo_dt());
				System.out.println("The set rate is "+divcal.getSetrate());
				System.out.println("The df amt is "+divcal.getDrfamt());
				DividendRateObject[] divrateobj=new DividendRateObject[1];
				divrateobj[0]=new DividendRateObject();
				divrateobj[0].setFromDate(divcal.getFrom_dt());
				divrateobj[0].setToDate(divcal.getTo_dt());
				divrateobj[0].setRate(Double.parseDouble(divcal.getSetrate()));
				divrateobj[0].setAmount(Double.parseDouble(divcal.getDrfamt()));
				int result=shDelegate.setDivrate(divrateobj,2,shuser,shtml);
				if(result==1)
				{
					req.setAttribute("Divrate", result);
					req.setAttribute("msg","Dividend Rate is Set");
				}
			}
			
	           if(divcal.getForward().equalsIgnoreCase("Calculate"))
	           {
	        	   DividendRateObject[] divrateobj=new DividendRateObject[1];
	        	   divrateobj[0]=new DividendRateObject();
	        	   divrateobj[0].setFromDate(divcal.getFrom_dt());
	        	   divrateobj[0].setToDate(divcal.getTo_dt());
	        	   divrateobj[0].setRate(Double.parseDouble(divcal.getSetrate()));
	        	   divrateobj[0].setAmount(Double.parseDouble(divcal.getDrfamt()));
	        	   divrateobj[0].setCalDone("F");
	        	   System.out.println("Going to calculate");
	        	   int result=shDelegate.setDivrate(divrateobj,1,shuser,shtml);
	        	   req.setAttribute("msg","Dividend calculated successfully...!");
        		}
			 
			 if(divcal.getForward().equalsIgnoreCase("Re-Calculate"))
			 {
				 DividendRateObject[] divrateobj=new DividendRateObject[1];
				 divrateobj[0]=new DividendRateObject();
				 divrateobj[0].setFromDate(divcal.getFrom_dt());
				 divrateobj[0].setToDate(divcal.getTo_dt());
				 divrateobj[0].setRate(Double.parseDouble(divcal.getSetrate()));
				 divrateobj[0].setAmount(Double.parseDouble(divcal.getDrfamt()));
				 divrateobj[0].setCalDone("F");
				 System.out.println("Going to calculate");
				 int result=shDelegate.setDivrate(divrateobj,3,shuser,shtml);
				 req.setAttribute("msg","Re-calculation successfully...!");
			 }
			
			 if(divcal.getForward().equalsIgnoreCase("Clear"))
			 {
				 divcal.setFrom_dt("");
				 divcal.setTo_dt("");
				 divcal.setSetrate("");
				 divcal.setDrfamt("");
			 }
			 
			 if(divcal.getFrom_dt().length()>0 && divcal.getTo_dt().length()>0)
			 {
				 DividendRateObject[] divobj;
				 System.out.println("Going to fetch the div rate");
				 divobj=shDelegate.getDivRate(divcal.getFrom_dt(),divcal.getTo_dt());
				 for(int i=0;i<divobj.length;i++)
				 {
					 System.out.println("The div rate in action class "+divobj[i].getRate());
					 System.out.println("The amount in action class "+divobj[i].getAmount());
				 }
				 System.out.println("Came after  fetching the div rate");
				 
				 req.setAttribute("divrate", divobj);
			 }
			
			
			
	   }
	//for Share Pass Book
		
	   else if(map.getPath().trim().equalsIgnoreCase("/Share/PassBookMenu"))
	   {
		   try{
			   PassBookForm pass=(PassBookForm)form;
			   req.setAttribute("pagenum", pass.getPageId());

			   //suraj added on 23/2/08
			   req.setAttribute("TabNum", "0");
			   req.setAttribute("flag", MenuNameReader.getScreenProperties("3003"));
			   getTabbedpane(req,pass);
			   req.setAttribute("focusto", "code");
			   
			   if(MenuNameReader.containsKeyScreen(pass.getPageId()))
			   {
				   path=MenuNameReader.getScreenProperties(pass.getPageId());
				   shDelegate=new ShareDelegate();
				   
				   setShOpeningInitParams(req, path, shDelegate);
				   req.setAttribute("pageId", path);
				   req.setAttribute("focusto", "code");
				   return map.findForward(ResultHelp.getSuccess());
			   }
			   else
			   {
				   path=MenuNameReader.getScreenProperties("0000");
				   setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
				   return map.findForward(ResultHelp.getError());
			   }
		   }
		   catch(Exception e)
		   {
			   e.printStackTrace();
			   path=MenuNameReader.getScreenProperties("0000");
			   setErrorPageElements(""+e,req,path);
			   return map.findForward(ResultHelp.getError());
		   }
	   }
		
	   else if(map.getPath().trim().equalsIgnoreCase("/Share/PassBook"))
	   {
		   
		   PassBookForm pass=(PassBookForm)form;
		   try{
			   ShareReportObject shrep[]=null;
			   req.setAttribute("msg","");
			   req.setAttribute("TabNum", "0");
			   req.setAttribute("flag", MenuNameReader.getScreenProperties("3003"));
			   getTabbedpane(req,pass);
			   req.setAttribute("focusto", "code");


			   if(MenuNameReader.containsKeyScreen(pass.getPageId()))
			   {
				   path=MenuNameReader.getScreenProperties(pass.getPageId());
				   shDelegate=new ShareDelegate();
				   req.setAttribute("pagenum", pass.getPageId());
				   req.setAttribute("pageId",path);
				   setShOpeningInitParams(req, path, shDelegate);
				   req.setAttribute("focusto", "code");
				   System.out.println("the delegate is "+shDelegate);

				   if(pass.getShnum().length()>0)
				   {
					   System.out.println("The acno is "+pass.getShnum());
					   
					   System.out.println("Before going to share pass baook bean method");
					   shrep= shDelegate.getPassbook(3, null, null, Integer.parseInt(pass.getShnum()), "1001001");
					   
					   if(shrep==null){
						   req.setAttribute("msg","No details exists for this account number");
						   req.setAttribute("PassbookDetails", "PassbookDetails");
					   }
					   else{
						  
					   req.setAttribute("passBook",shrep);
					   req.setAttribute("panelName", "Personal Details");
					   req.setAttribute("personalInfo", shDelegate.getCustomer(shrep[0].getCustomerId()));
					   				   
					   /*for(int i=0;i<shrep.length;i++)
					   {
						   System.out.println("*************PASS BOOK IN ACTION CLASS***");
						   System.out.println(shrep[i].getAccStatus());
						   System.out.println(shrep[i].getBranchName());
						   System.out.println(shrep[i].getShareStatus());
						   System.out.println("*************PASS BOOK IN ACTION CLASS***");
			          }*/
					   }
			          //req.setAttribute("PassBook",shrep); 		          
			           setShOpeningInitParams(req, path, shDelegate);
			           req.setAttribute("TabNum", "0");
				       req.setAttribute("flag", MenuNameReader.getScreenProperties("3003"));
					   getTabbedpane(req,pass);
					   req.setAttribute("pageId", path);
					   req.setAttribute("Download", "Download");   
				   }
				   System.out.println("Inside Pass value of Flag is-----"+pass.getFlag());
				   if(pass.getFlag()!=null && pass.getFlag().length()!=0 && pass.getFlag().equalsIgnoreCase("Download") && !(pass.getFlag().equalsIgnoreCase(""))){
					   System.out.println("Inside download no.of records ------"+shrep.length);
					   res.setContentType("application/.csv");
           	           res.setHeader("Content-disposition", "attachment;filename=output.csv");
           	           PrintWriter out=res.getWriter();
           	           out.print("\n");
        	           out.print("\n");
        	           out.print("\n");
        	           out.print(",");
           	           out.print("Share Passbook Details");
           	           out.print("\n");
           	           out.print(",");
           	           out.print("Share Cat:  ");out.print(shrep[0].getShareStatus());
           	           out.print(",");out.print(",");
           	           out.print("Share No:  ");out.print(pass.getShnum());
           	           out.print("\n");
           	           out.print("Trn Date"); out.print(",");
               		   out.print("Debit Amt"); out.print(",");
               		   out.print("Credit Amt"); out.print(",");
               		   out.print("Sh Bal"); out.print(",");
               		   out.print("No.Of Shares"); out.print(",");
               		   out.print("\n");
               		   for(int d=0;d<shrep.length;d++){
               			   out.print(shrep[d].getTransDate()); out.print(",");
                		   out.print(shrep[d].getDebitAmount()); out.print(",");
                		   out.print(shrep[d].getCreditAmount()); out.print(",");
                		   out.print(shrep[d].getshare_bal()); out.print(",");
                		   out.print(shrep[d].getNumberofShares()); out.print(",");
                		   out.print("\n");  
               		   }
               		pass.setFlag("");
               		return null;
                              		
				   }
				   //setShOpeningInitParams(req, path, shDelegate);
				   //req.setAttribute("pageId", path);
				   System.out.println("The path at this point is "+path);
				   System.out.println("yes its returning...........................");
				   return map.findForward(ResultHelp.getSuccess());
		       }
		   }
		   catch (Exception e)
		   { 
			   e.printStackTrace();
			   path=MenuNameReader.getScreenProperties("0000");
			   setErrorPageElements(""+e,req,path);
			   return map.findForward(ResultHelp.getError());
		   }
		  	   
  }
		
		
	//for Share Withdrawal
	   else if(map.getPath().trim().equalsIgnoreCase("/Share/WithdrawalMenu"))
	   {
		   try{
			   WithdrawalForm with_form=(WithdrawalForm)form;
			   req.setAttribute("pagenum", with_form.getPageId());

			   if(MenuNameReader.containsKeyScreen(with_form.getPageId()))
			   {
				   path=MenuNameReader.getScreenProperties(with_form.getPageId());
				   shDelegate=new ShareDelegate();
				   setShOpeningInitParams(req, path, shDelegate);
				   req.setAttribute("pageId",path);
				   req.setAttribute("focusto", "sh_no");
				   req.setAttribute("Bal",0.0);
				   req.setAttribute("Amt",0.0);
				   return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		
	   }
		
	   else if(map.getPath().trim().equalsIgnoreCase("/Share/Withdrawal"))
	   {
		   System.out.println("Amzad____________Amzad________");
		   try{
				WithdrawalForm with_form=(WithdrawalForm)form;
				req.setAttribute("pagenum", with_form.getPageId());
							
				if(MenuNameReader.containsKeyScreen(with_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(with_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					req.setAttribute("Bal",0.0);
					req.setAttribute("Amt",0.0);
					
					if(with_form.getSh_no()!=null && with_form.getSh_no().trim().length()>0 && with_form.getSh_no().trim().length()<11 && Integer.parseInt(with_form.getSh_no().trim())!=0)
					{
						System.out.println("THE ACCOUNT NUMBER IS "+with_form.getSh_no());
						System.out.println("Going to fetch share details");
						try{					
							ShareMasterObject shobj=new ShareMasterObject();
							int maxnum=shDelegate.checkShareNumber(Integer.parseInt(with_form.getSh_no().trim()),0);
							if(Integer.parseInt(with_form.getSh_no().trim())==0)
							{
								req.setAttribute("msg","Enter the Share Number");
							}
							else if(Integer.parseInt(with_form.getSh_no().trim())>maxnum)
							{
								req.setAttribute("msg","Account Not Found");
							}
							else if(with_form.getSh_no().trim().length()>0)
							{
								ShareReportObject[] sh_rep=null;
								sh_rep =shDelegate.getPassbook(3,null,null,Integer.parseInt(with_form.getSh_no().trim()),with_form.getAc_type());
								if(sh_rep==null){
									req.setAttribute("WithdrawalShare", "WithdrawalShare");
								}
								if(sh_rep!=null){
								System.out.println("The status is---------------------------- "+sh_rep[0].getAccStatus());
								if(sh_rep[0].getAccStatus().equalsIgnoreCase("CLOSED"))
								{
									req.setAttribute("msg","The Account is closed");
								}
								else if(sh_rep[0].getAccStatus().equalsIgnoreCase("OPENED"))
								{
									shobj=shDelegate.getShareMaster(with_form.getAc_type(), Integer.parseInt(with_form.getSh_no().trim()));
									System.out.println("Getting the info 000000000000000");
									req.setAttribute("ShareDetails", shobj);
									// req.setAttribute("personalInfo",shDelegate.getCustomer(shobj.getCustomerId()));
									req.setAttribute("min_withdraw", shDelegate.getWithdrawamount(with_form.getAc_type(), Integer.parseInt(with_form.getSh_no().trim()), Integer.parseInt(with_form.getSh_cat())));
								}
								else
								{
									req.setAttribute("msg","");
								}
							}
							}
							else
							{
								System.out.println("Getting the info..................");
								req.setAttribute("msg","");
								shobj=shDelegate.getShareMaster(with_form.getAc_type(), Integer.parseInt(with_form.getSh_no().trim()));
								System.out.println("Getting the info 000000000000000");
								req.setAttribute("ShareDetails", shobj);
								// req.setAttribute("personalInfo",shDelegate.getCustomer(shobj.getCustomerId()));
								req.setAttribute("min_withdraw", shDelegate.getWithdrawamount(with_form.getAc_type(), Integer.parseInt(with_form.getSh_no().trim()), Integer.parseInt(with_form.getSh_cat())));
							}
						}catch(Exception e)
						{
							path=MenuNameReader.getScreenProperties("0000");
							e.printStackTrace();
							setErrorPageElements("No details exists for this account number",req,path);
							return map.findForward(ResultHelp.getError());
						}
						
					}else if(with_form.getSh_no().trim().length()>=11){
						req.setAttribute("msg", "Please Enter Proper Share Account Number");
					}
					if(with_form.getDetails().equalsIgnoreCase("PersonalDetails"))
					{
						req.setAttribute("TabNum", "0");
					    getWithdrawTabs(req, with_form);
						shobj=shDelegate.getShareMaster(with_form.getAc_type(), Integer.parseInt(with_form.getSh_no().trim()));
						req.setAttribute("panelName", "Personal Details");
					    req.setAttribute("personalInfo",shDelegate.getCustomer(shobj.getCustomerId()));
					    req.setAttribute("flag", MenuNameReader.getScreenProperties("3003"));
					}
					
					else if(with_form.getDetails().equalsIgnoreCase("LoanDetails"))
					{
						req.setAttribute("LoanDetails",shDelegate.getLoanDetails(Integer.parseInt(with_form.getSh_no()),with_form.getAc_type()));
					}
				
				/* Checking here for the type of Share withdrawal*/	
					if(with_form.getWith_type().equalsIgnoreCase("C"))
					{
						System.out.println("The select withdrawal type is"+with_form.getWith_type());
						req.setAttribute("totshares", with_form.getNo_of_sh_al());
						req.setAttribute("Amount", shDelegate.closure_amt(Double.parseDouble(with_form.getNo_of_sh_al()), Double.parseDouble(with_form.getSh_val())));
	            		//req.setAttribute("checkloans", shDelegate.checkloans(with_form.getAc_type(), Integer.parseInt(with_form.getSh_no())));
						String check=shDelegate.checkloans(with_form.getAc_type(), Integer.parseInt(with_form.getSh_no().trim()));
						if(check.equalsIgnoreCase("T"))
						{
							req.setAttribute("msg","Cannot close the account,as you have to pay the loan amount!! ");
						}
						else
						{
							req.setAttribute("msg","");
						}
					}
					
					else if(with_form.getWith_type().equalsIgnoreCase("W") && !with_form.getNo_of_sh().equalsIgnoreCase("0"))
					{
						req.setAttribute("partshares", with_form.getNo_of_sh());
						req.setAttribute("Amountcl", shDelegate.closure_amt(Double.parseDouble(with_form.getNo_of_sh()), Double.parseDouble(with_form.getSh_val())));
					}
					
					
					System.out.println("with_form.getPaymode()----->1956---->"+with_form.getPaymode());
			/* Checking here for the type of paymode*/		
					
					if(with_form.getPaymode().equalsIgnoreCase("T") && with_form.getPay_ac_no()!=null && with_form.getPay_ac_no().trim().length()>0 && with_form.getPay_ac_no().trim().length()<11)
					{
						double sh_amt=shDelegate.calculateShareAmt(shparam, Double.parseDouble(with_form.getNo_of_sh()), 100.00, Integer.parseInt(with_form.getSh_cat()),0);
                		double min_bal=shDelegate.getBalance(with_form.getPaymode(),with_form.getPay_ac_type(), Integer.parseInt(with_form.getPay_ac_no()),sh_amt);
						if(min_bal<sh_amt){
							req.setAttribute("msg","No Minimum balance in the account after the transfer");
						}
						req.setAttribute("Bal", shDelegate.getBalance(with_form.getPaymode(), with_form.getPay_ac_type(), Integer.parseInt(with_form.getPay_ac_no()), Double.parseDouble(with_form.getTot_amt())));
						req.setAttribute("Amt", with_form.getTot_amt());
					}else if(with_form.getPay_ac_no().trim().length()>=11){
						req.setAttribute("msg", "Please Enter Proper Payment Account Number");
					}
					
					if(with_form.getPaymode().equalsIgnoreCase("C"))
					{
						 req.setAttribute("Butt_disable", true);
						 req.setAttribute("disablefield",false);
						 req.setAttribute("CashWithdrawal","CashWithdrawal");
						 req.setAttribute("Amt", with_form.getTot_amt());
					}
					
		    /* Retrive the data from the transaction number  */
					
					if(with_form.getTrn_no()!=null && with_form.getTrn_no().trim().length()>0 && with_form.getTrn_no().trim().length()<11 && Integer.parseInt(with_form.getTrn_no())!=0 )
					{
						System.out.println("Going to get details using transcation number");
						System.out.println("The tran number is>>>>>>>>>>>>>>>>> "+with_form.getTrn_no());
						ShareMasterObject shobj=null;
						shobj=new ShareMasterObject();
						shobj=shDelegate.getShareTransaction(Integer.parseInt(with_form.getTrn_no()));
					    req.setAttribute("ShareDetails", shobj);
						System.out.println("The share master object in after transaction number is"+shobj);
						
						System.out.println("The status is --------------->"+shobj.getShareStatus());
						//System.out.println("The VETML is <<<<<<<<<<<<<<<<"+shobj.getVe_tml());
						if(shobj==null)
						{ 
							req.setAttribute("msg","Transaction number does not exit");
						}
						else if(shobj.uv.getVerTml()!=null)
						{
							req.setAttribute("msg","Transaction number is already verified");
						}
						else
						{
							req.setAttribute("msg","");

							System.out.println("The tran no after fetching is--------->"+shobj.getTranNumber());
							with_form.setSh_type(shobj.getShareStatus());
							with_form.setPaymode(shobj.getRecievedMode());
							if(shobj.getRecievedMode().equalsIgnoreCase("C"))
								req.setAttribute("CashWithdrawal","CashWithdrawal");
							else
								req.setAttribute("Bal", shDelegate.getBalance(shobj.getRecievedMode(), shobj.getRecievedAcctype(), shobj.getRecievedAccno(), Double.parseDouble(shobj.getTrnamt())));
							with_form.setDetails("PersonalDetails");
							
							req.setAttribute("with_det",shobj);
							with_form.setAmount(String.valueOf(shobj.getAmount()));
							System.out.println("out of tran no");
						}
						
					}else if(with_form.getTrn_no().trim().length()>=11){
						req.setAttribute("msg", "Please Enter Proper Transaction Number");
					}
					
					
					if(with_form.getForward().equalsIgnoreCase("Submit"))
					{
						System.out.println("SUBMITTING))))))))))))))))))))))))))))))))");
						ShareMasterObject shob=new ShareMasterObject();
						shob.setShareNumber(Integer.parseInt(with_form.getSh_no().trim()));
						shob.setShareAccType(with_form.getAc_type());
						System.out.println("The mem category is "+with_form.getSh_cat());
						shob.setMemberCategory(Integer.parseInt(with_form.getSh_cat()));
						System.out.println("SHARE STATUS IS"+with_form.getSh_type());
						if(with_form.getSh_type().equalsIgnoreCase("Direct"))
						{
							shob.setShareStatus("P");
						}
						shob.setNumberofShares(Double.parseDouble(with_form.getNo_of_sh()));
						shob.setShareBalance(Double.parseDouble(with_form.getTot_amt()));
						shob.setAmount(Double.parseDouble(with_form.getTot_amt()));

						if(with_form.getPaymode().equalsIgnoreCase("T"))
						{
							shob.setRecievedMode(with_form.getPaymode());
							shob.setRecievedAcctype(with_form.getPay_ac_type());
							shob.setRecievedAccno(Integer.parseInt(with_form.getPay_ac_no()));
							shob.setAmount(Double.parseDouble(with_form.getAmount()));
						}else if(with_form.getPaymode().equalsIgnoreCase("C")){
							shob.setRecievedMode("C");
							shob.setRecievedAcctype(with_form.getPay_ac_type());
							shob.setRecievedAccno(0);
							shob.setAmount(Double.parseDouble(with_form.getAmount()));
						}
						shob.setTransferDate(ShareDelegate.getSysDate());
						shob.setCustomerId(4929);
						shob.uv.setUserId(shuser);
						shob.uv.setUserDate(ShareDelegate.getSysDate());
						shob.uv.setUserTml(shtml);
						
						int trn_no=shDelegate.with_storeShare(shob);
						System.out.println("The transaction number"+trn_no);
						if(trn_no!=0)
						{
							req.setAttribute("msg","The Transaction number is "+String.valueOf(trn_no));
						}
						else
						{
							req.setAttribute("msg","");
						}
						
					}
					
					if(with_form.getForward().equalsIgnoreCase("Verify"))
					{
						System.out.println("IN VERIFICATION");
						ShareMasterObject sh=new ShareMasterObject();
						System.out.println(with_form.getTrn_no());
						sh.setTranNumber(Integer.parseInt(with_form.getTrn_no()));
						System.out.println(with_form.getAc_type());
						sh.setShareAccType(with_form.getAc_type());
						System.out.println(Integer.parseInt(with_form.getSh_no().trim()));
						sh.setShareNumber(Integer.parseInt(with_form.getSh_no().trim()));
						System.out.println(with_form.getSh_type());
						sh.setShareStatus(with_form.getSh_type());
						sh.setBranchCode(Integer.parseInt(with_form.getBr_code()));
						sh.setMemberCategory(Integer.parseInt(with_form.getSh_cat()));
						//sh.setWithdrawalType(Integer.parseInt(with_form.getWith_type()));
						sh.setNumberofShares(Double.parseDouble(with_form.getNo_of_sh()));
						sh.setShareBalance(Double.parseDouble(with_form.getTot_amt()));
						sh.setRecievedMode(with_form.getPaymode());
						
						if(with_form.getPaymode().equalsIgnoreCase("T")){
						   sh.setRecievedAccno(Integer.parseInt(with_form.getPay_ac_no()));
						   sh.setRecievedAcctype(with_form.getPay_ac_type());
						}
						else{
							sh.setRecievedAccno(0);
							sh.setRecievedAcctype(null);
						}
						sh.setAmount(Double.parseDouble(with_form.getAmount()));
						sh.setShareVal(1.0);
						
						sh.setTransferDate(ShareDelegate.getSysDate());
						sh.uv.setUserId(shuser);
						sh.uv.setUserDate(ShareDelegate.getSysDate());
						sh.uv.setUserTml(shtml);
						
						
						sh.uv.setVerId(shuser);
						sh.uv.setVerDate(ShareDelegate.getSysDate());
						sh.uv.setVerTml(shtml);
						
						
						int verify=shDelegate.verify_withdraw(sh, Integer.parseInt(with_form.getTrn_no()));
						if(verify>0)
						{
							req.setAttribute("msg","Successfully Verified");
						}
						else
						{
							req.setAttribute("msg","Error while verification !!!");
						}
						
					}
					req.setAttribute("Paymode", shDelegate.getpaymode());
					return map.findForward(ResultHelp.getSuccess());
				}
				
	              
				
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			    	
				
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
	   }
		//end of Share Withdrawal
		
		//for reports
		else if(map.getPath().trim().equalsIgnoreCase("/Share/ShareOpenReportMenu"))
		{
			System.out.println("In reports");
			try{
				OpenReportForm open_form=(OpenReportForm)form;
				req.setAttribute("pagenum", open_form.getPageId());

				if(MenuNameReader.containsKeyScreen(open_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(open_form.getPageId());
					shDelegate=new ShareDelegate();
					open_form.setFrm_dt(shDelegate.getSysDate());
					open_form.setTo_dt(shDelegate.getSysDate());
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
			
		}
		
		

		else if(map.getPath().trim().equalsIgnoreCase("/Share/ShareOpenReport"))
		{
			OpenReportForm open_form=(OpenReportForm)form;
			String path;
			try{
				req.setAttribute("pagenum", open_form.getPageId());
				if(MenuNameReader.containsKeyScreen(open_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(open_form.getPageId());
					shDelegate=new ShareDelegate();
					req.setAttribute("pageId",path);
					setShOpeningInitParams(req, path, shDelegate);
					System.out.println("the delegate is "+shDelegate);

				}
				
				if(open_form.getForward().equalsIgnoreCase("View"))
				{
					System.out.println(open_form.getFrm_dt());
					System.out.println(open_form.getTo_dt());
					if(open_form.getFrm_dt()!=null && open_form.getTo_dt()!=null)
					{
						req.setAttribute("Report", shDelegate.shareopenclosereport(open_form.getFrm_dt(), open_form.getTo_dt(),1));
					}
				}
				
				if(open_form.getForward().equalsIgnoreCase("Save")){
					
					/*JFileChooser jfile=new JFileChooser();
				-
					ShareReportObject[] shobj=shDelegate.shareopenclosereport(open_form.getFrm_dt(), open_form.getTo_dt(),1);
					req.setAttribute("Report",shobj );
					FileWriter fw=new FileWriter("c:/surajb/emp1.xls",true);
					fw.write("Shno");
					fw.write("\t");
				    fw.write("ShareStatus");
				    fw.write("\t");
				    fw.write("CID");
				    fw.write("\n");
				    
				    
				    for(int i=0;i<shobj.length;i++){
				    	fw.write(String.valueOf(shobj[i].getShareNumber()));
				    	fw.write("\t");
				    	fw.write(shobj[i].getShareStatus());
				    	fw.write("\t");
				    	fw.write(String.valueOf(shobj[i].getCustomerId()));
				    	fw.write("\n");
				    }
				    fw.close();
*/               try{
	              ShareReportObject shOpenCloseReport[]=shDelegate.shareopenclosereport(open_form.getFrm_dt(), open_form.getTo_dt(),1);
	              res.setContentType("appliaction/.csv");
	              res.setHeader("Content-disposition", "attachment;filename=shareopenreport.csv");
	              PrintWriter out=res.getWriter();
	              out.print("\n");
	              out.print("\n");
	              out.print(",");out.print(",");out.print(",");
	              out.print("Shares opened between  "+open_form.getFrm_dt()+"  "+open_form.getTo_dt());
	              out.print("\n");
	              out.print("Sh No");out.print(",");
	              out.print("Sh Status");out.print(",");
	              out.print("Customer Id");out.print(",");
	              out.print("Name");out.print(",");
	              out.print("No of Shares");out.print(",");
	              out.print("Branch Code");out.print(",");
	              out.print("Issue Date");out.print(",");
	              out.print("Sh Value");out.print(",");
	              out.print("\n");
	              for(int r=0;r<shOpenCloseReport.length;r++){
	            	  out.print(shOpenCloseReport[r].getShareNumber());out.print(",");
		              out.print(shOpenCloseReport[r].getShareStatus());out.print(",");
		              out.print(shOpenCloseReport[r].getCustomerId());out.print(",");
		              out.print(shOpenCloseReport[r].getName());out.print(",");
		              out.print(shOpenCloseReport[r].getNumberofShares());out.print(",");
		              out.print(shOpenCloseReport[r].getBranchCode());out.print(",");
		              out.print(shOpenCloseReport[r].getIssueDate());out.print(",");
		              out.print(shOpenCloseReport[r].getShareVal());out.print(",");  
		              out.println();
	              }
                 }catch(Exception e){
                	 e.printStackTrace();
                 }
                 return null;
				}
				
			}catch (Exception e)
			{
		           e.printStackTrace();		
			}
			
			
		}
		

		
		else if(map.getPath().trim().equalsIgnoreCase("/Share/ShareCloseReportMenu"))
		{
			System.out.println("In reports");
			try{
				OpenReportForm open_form=(OpenReportForm)form;
				System.out.println("The page iid is "+open_form.getPageId());
				req.setAttribute("pagenum", open_form.getPageId());
				
				if(MenuNameReader.containsKeyScreen(open_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(open_form.getPageId());
					shDelegate=new ShareDelegate();
					open_form.setFrm_dt(shDelegate.getSysDate());
					open_form.setTo_dt(shDelegate.getSysDate());
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
			
		}
		
		

		else if(map.getPath().trim().equalsIgnoreCase("/Share/ShareCloseReport"))
		{
			OpenReportForm open_form=(OpenReportForm)form;
			String path;
			try{
				req.setAttribute("pagenum", open_form.getPageId());
				if(MenuNameReader.containsKeyScreen(open_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(open_form.getPageId());
					shDelegate=new ShareDelegate();
					req.setAttribute("pageId",path);
					setShOpeningInitParams(req, path, shDelegate);
					System.out.println("the delegate is "+shDelegate);

				}
				
				if(open_form.getForward().equalsIgnoreCase("View"))
				{
					System.out.println(open_form.getFrm_dt());
					System.out.println(open_form.getTo_dt());
					if(open_form.getFrm_dt()!=null && open_form.getTo_dt()!=null)
					{
						req.setAttribute("Report", shDelegate.shareopenclosereport(open_form.getFrm_dt(), open_form.getTo_dt(),2));
					}
				}
				if(open_form.getForward().equalsIgnoreCase("download"))
				{
					try{
			              ShareReportObject shOpenCloseReport[]=shDelegate.shareopenclosereport(open_form.getFrm_dt(), open_form.getTo_dt(),2);
			              res.setContentType("appliaction/.csv");
			              res.setHeader("Content-disposition", "attachment;filename=shareclosereport.csv");
			              PrintWriter out=res.getWriter();
			              out.print("\n");
			              out.print("\n");
			              out.print(",");out.print(",");out.print(",");
			              out.print("Shares closed between  "+open_form.getFrm_dt()+"  "+open_form.getTo_dt());
			              out.print("\n");
			              out.print("Sh No");out.print(",");
			              out.print("Sh Status");out.print(",");
			              out.print("Customer Id");out.print(",");
			              out.print("Name");out.print(",");
			              out.print("No of Shares");out.print(",");
			              out.print("Branch Code");out.print(",");
			              out.print("Issue Date");out.print(",");
			              out.print("Sh Value");out.print(",");
			              out.print("\n");
			              for(int r=0;r<shOpenCloseReport.length;r++){
			            	  out.print(shOpenCloseReport[r].getShareNumber());out.print(",");
				              out.print(shOpenCloseReport[r].getShareStatus());out.print(",");
				              out.print(shOpenCloseReport[r].getCustomerId());out.print(",");
				              out.print(shOpenCloseReport[r].getName());out.print(",");
				              out.print(shOpenCloseReport[r].getNumberofShares());out.print(",");
				              out.print(shOpenCloseReport[r].getBranchCode());out.print(",");
				              out.print(shOpenCloseReport[r].getIssueDate());out.print(",");
				              out.print(shOpenCloseReport[r].getShareVal());out.print(",");
				              out.println();
			              }
		                 }catch(Exception e){
		                	 e.printStackTrace();
		                 }
		                 return null;
				}
			}catch (Exception e)
			{
		           e.printStackTrace();		
			}
			
			
		}

	//Temp Share Report
		
		else if(map.getPath().trim().equalsIgnoreCase("/Share/TempShareMenu"))
		{
			System.out.println("In reports");
			try{
				OpenReportForm open_form=(OpenReportForm)form;
				System.out.println("The page iid is "+open_form.getPageId());
				req.setAttribute("pagenum", open_form.getPageId());
				
				if(MenuNameReader.containsKeyScreen(open_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(open_form.getPageId());
					shDelegate=new ShareDelegate();
					open_form.setFrm_dt(shDelegate.getSysDate());
					open_form.setTo_dt(shDelegate.getSysDate());
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
			
		}
		
		

		else if(map.getPath().trim().equalsIgnoreCase("/Share/TempShare"))
		{
			OpenReportForm open_form=(OpenReportForm)form;
			String path;
			try{
				req.setAttribute("pagenum", open_form.getPageId());
				if(MenuNameReader.containsKeyScreen(open_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(open_form.getPageId());
					shDelegate=new ShareDelegate();

					req.setAttribute("pageId",path);
					setShOpeningInitParams(req, path, shDelegate);
					System.out.println("the delegate is "+shDelegate);

				}
				
				if(open_form.getForward().equalsIgnoreCase("View"))
				{
					System.out.println(open_form.getFrm_dt());
					System.out.println(open_form.getTo_dt());
					if(open_form.getFrm_dt()!=null && open_form.getTo_dt()!=null)
					{
						req.setAttribute("Report", shDelegate.shareopenclosereport(open_form.getFrm_dt(), open_form.getTo_dt(),3));
					}
				}
				if(open_form.getForward().equalsIgnoreCase("download"))
				{
					try{
			              ShareReportObject shOpenCloseReport[]=shDelegate.shareopenclosereport(open_form.getFrm_dt(), open_form.getTo_dt(),3);
			              res.setContentType("appliaction/.csv");
			              res.setHeader("Content-disposition", "attachment;filename=temporarysharesreport.csv");
			              PrintWriter out=res.getWriter();
			              out.print("\n");
			              out.print("\n");
			              out.print(",");out.print(",");out.print(",");
			              out.print("Temporary Shares between "+open_form.getFrm_dt()+"  "+open_form.getTo_dt());
			              out.print("\n");
			              out.print("Sh No");out.print(",");
			              out.print("Sh Status");out.print(",");
			              out.print("Customer Id");out.print(",");
			              out.print("Name");out.print(",");
			              out.print("No of Shares");out.print(",");
			              out.print("Branch Code");out.print(",");
			              out.print("Issue Date");out.print(",");
			              out.print("Sh Value");out.print(",");
			              out.print("\n");
			              for(int r=0;r<shOpenCloseReport.length;r++){
			            	  out.print(shOpenCloseReport[r].getShareNumber());out.print(",");
				              out.print(shOpenCloseReport[r].getShareStatus());out.print(",");
				              out.print(shOpenCloseReport[r].getCustomerId());out.print(",");
				              out.print(shOpenCloseReport[r].getName());out.print(",");
				              out.print(shOpenCloseReport[r].getNumberofShares());out.print(",");
				              out.print(shOpenCloseReport[r].getBranchCode());out.print(",");
				              out.print(shOpenCloseReport[r].getIssueDate());out.print(",");
				              out.print(shOpenCloseReport[r].getShareVal());out.print(",");
				              out.println();
			              }
		                 }catch(Exception e){
		                	 e.printStackTrace();
		                 }
		                 return null;
				}
			}catch (Exception e)
			{
		           e.printStackTrace();		
			}
			
			
		}

	//Permenant Shares
		
		else if(map.getPath().trim().equalsIgnoreCase("/Share/PermShareMenu"))
		{
			System.out.println("In reports");
			try{
				OpenReportForm open_form=(OpenReportForm)form;
				System.out.println("The page iid is "+open_form.getPageId());
				req.setAttribute("pagenum", open_form.getPageId());

				if(MenuNameReader.containsKeyScreen(open_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(open_form.getPageId());
					shDelegate=new ShareDelegate();
					open_form.setFrm_dt(ShareDelegate.getSysDate());
					open_form.setTo_dt(ShareDelegate.getSysDate());
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
			
		}
		
		

		else if(map.getPath().trim().equalsIgnoreCase("/Share/PermShare"))
		{
			OpenReportForm open_form=(OpenReportForm)form;
			String path;
			try{
				req.setAttribute("pagenum", open_form.getPageId());
				if(MenuNameReader.containsKeyScreen(open_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(open_form.getPageId());
					shDelegate=new ShareDelegate();
					req.setAttribute("pageId",path);
					setShOpeningInitParams(req, path, shDelegate);
					System.out.println("the delegate is "+shDelegate);
						
				}
				
				if(open_form.getForward().equalsIgnoreCase("View"))
				{
					System.out.println(open_form.getFrm_dt());
					System.out.println(open_form.getTo_dt());
					if(open_form.getFrm_dt()!=null && open_form.getTo_dt()!=null)
					{
						req.setAttribute("Report", shDelegate.shareopenclosereport(open_form.getFrm_dt(), open_form.getTo_dt(),4));
					}
				}
				if(open_form.getForward().equalsIgnoreCase("download"))
				{
					try{
			              ShareReportObject shOpenCloseReport[]=shDelegate.shareopenclosereport(open_form.getFrm_dt(), open_form.getTo_dt(),4);
			              res.setContentType("appliaction/.csv");
			              res.setHeader("Content-disposition", "attachment;filename=permanentsharesreport.csv");
			              PrintWriter out=res.getWriter();
			              out.print("\n");
			              out.print("\n");
			              out.print(",");out.print(",");out.print(",");
			              out.print("Permanent Shares between "+open_form.getFrm_dt()+"  "+open_form.getTo_dt());
			              out.print("\n");
			              out.print("Sh No");out.print(",");
			              out.print("Sh Status");out.print(",");
			              out.print("Customer Id");out.print(",");
			              out.print("Name");out.print(",");
			              out.print("No of Shares");out.print(",");
			              out.print("Branch Code");out.print(",");
			              out.print("Issue Date");out.print(",");
			              out.print("Sh Value");out.print(",");
			              out.print("\n");
			              for(int r=0;r<shOpenCloseReport.length;r++){
			            	  out.print(shOpenCloseReport[r].getShareNumber());out.print(",");
				              out.print(shOpenCloseReport[r].getShareStatus());out.print(",");
				              out.print(shOpenCloseReport[r].getCustomerId());out.print(",");
				              out.print(shOpenCloseReport[r].getName());out.print(",");
				              out.print(shOpenCloseReport[r].getNumberofShares());out.print(",");
				              out.print(shOpenCloseReport[r].getBranchCode());out.print(",");
				              out.print(shOpenCloseReport[r].getIssueDate());out.print(",");
				              out.print(shOpenCloseReport[r].getShareVal());out.print(",");
				              out.println();
			              }
		                 }catch(Exception e){
		                	 e.printStackTrace();
		                 }
		                 return null;
				}
			}catch (Exception e) 
			{
		           e.printStackTrace();		
			}
			
			
		}

							/* For voter list*/	
		else if(map.getPath().equalsIgnoreCase("/Share/Votermenu"))
		{
			System.out.println("In reports");
			try{
				OpenReportForm open_form=(OpenReportForm)form;
				System.out.println("The page iid is "+open_form.getPageId());
				req.setAttribute("pagenum", open_form.getPageId());

				if(MenuNameReader.containsKeyScreen(open_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(open_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}

		
		else if(map.getPath().equalsIgnoreCase("/Share/Voter"))
		{
			System.out.println("In action class again-----------------");
			OpenReportForm open_form=(OpenReportForm)form;
			String path;
			try{
				req.setAttribute("pagenum", open_form.getPageId());
				if(MenuNameReader.containsKeyScreen(open_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(open_form.getPageId());
					shDelegate=new ShareDelegate();
					req.setAttribute("pageId",path);
					setShOpeningInitParams(req, path, shDelegate);
					System.out.println("the delegate is "+shDelegate);
						
				}
				
				if(open_form.getForward().equalsIgnoreCase("View"))
				{
					req.setAttribute("Report", shDelegate.voterlist());
					session=req.getSession(true);
					session.setAttribute("Report", shDelegate.voterlist());
				}
				if(open_form.getForward().equalsIgnoreCase("download"))
				{
					try{
						  String address,newaddress;
			              ShareReportObject shOpenCloseReport[]=shDelegate.voterlist();
			              res.setContentType("appliaction/.csv");
			              res.setHeader("Content-disposition", "attachment;filename=sharevoterlist.csv");
			              PrintWriter out=res.getWriter();
			              out.print("\n");
			              out.print("\n");
			              out.print(",");out.print(",");out.print(",");
			              out.print("Shares Voter List  ");
			              out.print("\n");
			              out.print("Sh No");out.print(",");
			              out.print("Name");out.print(",");
			              out.print("Address");out.print(",");
			              out.print("City");out.print(",");
			              out.print("Pin");out.print(",");
			              out.print("Share Status");out.print(",");
			              out.print("\n");
			              for(int r=0;r<shOpenCloseReport.length;r++){
			            	  address=shOpenCloseReport[r].getAddress();
			            	  newaddress=address.replace(",", " ");
			            	  out.print(shOpenCloseReport[r].getShareNumber());out.print(",");
				              out.print(shOpenCloseReport[r].getName());out.print(",");
				              out.print(newaddress);out.print(",");
				              out.print(shOpenCloseReport[r].getCity());out.print(",");
				              out.print(shOpenCloseReport[r].getPin());out.print(",");
				              out.print(shOpenCloseReport[r].getShareStatus());out.print(",");
				              out.println();
			              }
		                 }catch(Exception e){
		                	 e.printStackTrace();
		                 }
		                 return null;
				}

			}catch (Exception e) {
		           e.printStackTrace();		
			
			}
		}
	/* for Share Registry */
		
		else if(map.getPath().equalsIgnoreCase("/Share/ShareRegistryMenu"))
		{
			System.out.println("In reports");
			try{
				OpenReportForm open_form=(OpenReportForm)form;
				System.out.println("The page iid is "+open_form.getPageId());
				req.setAttribute("pagenum", open_form.getPageId());
				
				if(MenuNameReader.containsKeyScreen(open_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(open_form.getPageId());
					shDelegate=new ShareDelegate();
					open_form.setFrm_dt(shDelegate.getSysDate());
					open_form.setTo_dt(shDelegate.getSysDate());
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}

		
		else if(map.getPath().equalsIgnoreCase("/Share/ShareRegistry"))
		{
			OpenReportForm open_form=(OpenReportForm)form;
			String path;
			try{
				req.setAttribute("pagenum", open_form.getPageId());
				if(MenuNameReader.containsKeyScreen(open_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(open_form.getPageId());
					shDelegate=new ShareDelegate();
					req.setAttribute("pageId",path);
					setShOpeningInitParams(req, path, shDelegate);
					System.out.println("the delegate is "+shDelegate);

				}
				
				if(open_form.getForward().equalsIgnoreCase("View"))
				{
					System.out.println("m in View.....");
					req.setAttribute("Report", shDelegate.shareopenclosereport(open_form.getFrm_dt(), open_form.getTo_dt(), 5));
				}
				
				if(open_form.getForward().equalsIgnoreCase("save"))
				{
					System.out.println("m in save--------------");
					 ShareReportObject[] shareReportObject=null;
					 System.out.println("Inside save ");
					 //TO save to an excel Sheet
					 res.setContentType("application/.csv");
           	         res.setHeader("Content-disposition", "attachment;filename=output.csv");
           	        
           	        java.io.PrintWriter out = res.getWriter();
           	        out.print("\n");
           	        out.print("\n");
           	        out.print("\n");
           	        out.print(",");out.print(",");out.print(",");
           	        out.print("Share Registry Details:");
           	        out.print("\n");
           	        out.print("\n");
              		   /*HSSFCell cell = row.createCell((short)0);
              		   cell.setCellValue(1);*/
           	        out.print("name"); out.print(",");
            		out.print("intrAccno"); out.print(",");
            		out.print("nomineeRegNo"); out.print(",");
            		out.print("numberofShares"); out.print(",");
            		out.print("shareVal"); out.print(",");
            		out.print("divUptoDate"); out.print(",");
            		out.print("loanAvailed"); out.print("\n");
            		shareReportObject=shDelegate.shareopenclosereport(open_form.getFrm_dt(), open_form.getTo_dt(), 5);
            		System.out.println("shareReportObject=======>>>"+shareReportObject.length );
            		 for(int k=0;k<shareReportObject.length;k++){
              			  
                			out.print(shareReportObject[k].getName());
                			out.print(",");
                			out.print(shareReportObject[k].getIntrAccno());
                			out.print(",");
                			out.print(shareReportObject[k].getNomineeRegNo());
                			out.print(",");
                			out.print(shareReportObject[k].getNumberofShares());
                			out.print(",");
                			out.print(shareReportObject[k].getShareVal());
                			out.print(",");
                			out.print(shareReportObject[k].getDivUptoDate());
                			out.print(",");
                			out.print(shareReportObject[k].getLoanAvailed());
                			out.print(",");
                			out.print("\n");
            		 }
            		 req.setAttribute("msg","Saved to excel file in C:");
         		     return null;
            		 }
            		

			}catch (Exception e) 
			{
		           e.printStackTrace();		
			
			}
		}
		
		/* for Allotment and Withdrawal*/
		
		else if(map.getPath().equalsIgnoreCase("/Share/ShareAllotwithMenu"))
		{
			System.out.println("In reports");
			
			try{
				OpenReportForm open_form=(OpenReportForm)form;
				System.out.println("The page iid is "+open_form.getPageId());
				req.setAttribute("pagenum", open_form.getPageId());

				if(MenuNameReader.containsKeyScreen(open_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(open_form.getPageId());
					shDelegate=new ShareDelegate();
					open_form.setFrm_dt(shDelegate.getSysDate());
					open_form.setTo_dt(shDelegate.getSysDate());
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}	
		}
		
		else if(map.getPath().equalsIgnoreCase("/Share/ShareAllotwith"))
		{
			OpenReportForm open_form=(OpenReportForm)form;
			String path;
			try{
				req.setAttribute("pagenum", open_form.getPageId());
				if(MenuNameReader.containsKeyScreen(open_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(open_form.getPageId());
					shDelegate=new ShareDelegate();
					req.setAttribute("pageId",path);
					setShOpeningInitParams(req, path, shDelegate);
					System.out.println("the delegate is "+shDelegate);

				}
				
				if(open_form.getForward().equalsIgnoreCase("View"))
				{
					System.out.println("m in View,,,,,");
					req.setAttribute("Report", shDelegate.shareopenclosereport(open_form.getFrm_dt(), open_form.getTo_dt(),6));
				}
				if(open_form.getForward().equalsIgnoreCase("save"))
				{
					 System.out.println("Inside save ");
            		 //TO save to an excel Sheet
            		 res.setContentType("application/.csv");
            	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
            	        
            	        java.io.PrintWriter out = res.getWriter();
            	        out.print("\n");
            	        out.print("\n");
            	        out.print("\n");
            	        out.print(",");out.print(",");out.print(",");
            	        out.print("Share Allotment/withdrawal Details: ");
            	        out.print("\n");
            	        out.print("\n");
               		   /*HSSFCell cell = row.createCell((short)0);
               		   cell.setCellValue(1);*/
            	        out.print("shareNumber"); out.print(",");
                   		out.print("name"); out.print(",");
                   		out.print("transDate"); out.print(",");
                   		out.print("transType"); out.print(",");
                   		out.print("numberofShares"); out.print(",");
                   		out.print("shareVal"); out.print("\n");
                   		ShareReportObject[] sharereportobj=shDelegate.shareopenclosereport(open_form.getFrm_dt(), open_form.getTo_dt(),6);
                   	 for(int k=0;k<sharereportobj.length;k++){
              			  
                			out.print(sharereportobj[k].getShareNumber());
                			out.print(",");
                			out.print(sharereportobj[k].getName());
                			out.print(",");
                			out.print(sharereportobj[k].getTransDate());
                			out.print(",");
                			out.print(sharereportobj[k].getTransType());
                			out.print(",");
                			out.print(sharereportobj[k].getNumberofShares());
                			out.print(",");
                			out.print(sharereportobj[k].getShareVal());
                			out.print(",");
                			out.print("\n");
                			
                   	 }
                   	req.setAttribute("msg","Saved to excel file in C:");
        		    return null;
                   		
				}

			}catch (Exception e) {
		           e.printStackTrace();		
			
			}
		}

		/*  Branch wise Summary*/
		
		else if(map.getPath().equalsIgnoreCase("/Share/ShareRegistrySumMenu"))
		{
			System.out.println("In reports");
			try{
				OpenReportForm open_form=(OpenReportForm)form;
				System.out.println("The page iid is "+open_form.getPageId());
				req.setAttribute("pagenum", open_form.getPageId());
				
				if(MenuNameReader.containsKeyScreen(open_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(open_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}	
		}
		
		else if(map.getPath().equalsIgnoreCase("/Share/ShareRegistrySum"))
		{
			OpenReportForm open_form=(OpenReportForm)form;
			String path;
			try{
				req.setAttribute("pagenum", open_form.getPageId());
				if(MenuNameReader.containsKeyScreen(open_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(open_form.getPageId());
					shDelegate=new ShareDelegate();
					req.setAttribute("pageId",path);
					setShOpeningInitParams(req, path, shDelegate);
					System.out.println("the delegate is "+shDelegate);

				}
				
				if(open_form.getForward().equalsIgnoreCase("View"))
				{
					req.setAttribute("Report", shDelegate.shareopenclosereport(open_form.getFrm_dt(), open_form.getTo_dt(),7));
				}
				if(open_form.getForward().equalsIgnoreCase("save"))
				{
					ShareReportObject[] sharereportobj=null;
					System.out.println("Inside save ");
           		 //TO save to an excel Sheet
           		 res.setContentType("application/.csv");
           	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
           	        
           	        java.io.PrintWriter out = res.getWriter();
           	        out.print("\n");
           	        out.print("\n");
           	        out.print("\n");
           	        out.print(",");out.print(",");out.print(",");
           	        out.print("BranchWiseRegistry Details: ");
           	        out.print("\n");
           	        out.print("\n");
              		   /*HSSFCell cell = row.createCell((short)0);
              		   cell.setCellValue(1);*/
           	        out.print("branchCode"); out.print(",");
            		out.print("branchName"); out.print(",");
            		out.print("numberofShares"); out.print(",");
            		out.print("shareVal"); out.print("\n");
            		sharereportobj=shDelegate.shareopenclosereport(open_form.getFrm_dt(), open_form.getTo_dt(),7);
            		for(int k=0;k<sharereportobj.length;k++){
             			  
               			out.print(sharereportobj[k].getBranchCode());
               			out.print(",");
               			out.print(sharereportobj[k].getBranchName());
               			out.print(",");
               			out.print(sharereportobj[k].getNumberofShares());
               			out.print(",");
               			out.print(sharereportobj[k].getShareVal());
               			out.print(",");
               			out.print("\n");   
               			}
            		req.setAttribute("msg","Saved to excel file in C:");
        		    return null;
           	        
				}

			}catch (Exception e) {
		           e.printStackTrace();		
			
			}
		}
		
		
		/********************************* For Share Ledger *************************/
		
		else if(map.getPath().equalsIgnoreCase("/Share/ShareLedgerMenu"))
		{
			try{
				ShareLedgerform sh_ledger=(ShareLedgerform)form;
				System.out.println("The page iid is "+sh_ledger.getPageId());
				req.setAttribute("pagenum", sh_ledger.getPageId());
				
				if(MenuNameReader.containsKeyScreen(sh_ledger.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(sh_ledger.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					req.setAttribute("focusto","acctype");
					return map.findForward(ResultHelp.getSuccess());
					
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}	
		}
		
		else if(map.getPath().equalsIgnoreCase("/Share/ShareLedger"))
		{
			ShareLedgerform sh_ledger=(ShareLedgerform)form;
			String path;
			try{
				req.setAttribute("pagenum", sh_ledger.getPageId());
				if(MenuNameReader.containsKeyScreen(sh_ledger.getPageId()))
				{

					path=MenuNameReader.getScreenProperties(sh_ledger.getPageId());
					shDelegate=new ShareDelegate();
					req.setAttribute("pageId",path);
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("focusto","acctype");
					System.out.println("the delegate is "+shDelegate);
					if(sh_ledger.getAcno().length()!=0)
					{
						int maxnum=shDelegate.checkShareNumber(Integer.parseInt(sh_ledger.getAcno()),0);
						System.out.println("The max number is ------"+maxnum);
						if(Integer.parseInt(sh_ledger.getAcno())>maxnum)
						{
							System.out.println("Check gor null");
							req.setAttribute("msg", "Account Not Found");	
						}
						else
						{
							ShareReportObject[] sh_rep=null;
							try{
								
							sh_rep=shDelegate.getShareLedger(4, null, null, Integer.parseInt(sh_ledger.getAcno()),sh_ledger.getAcctype());
							}catch(Exception e){
								e.printStackTrace();
							}
							if(sh_rep!=null){
							req.setAttribute("shareledger",sh_rep);
							}else{
								req.setAttribute("msg", "No Records Found to Display");	
							}
						}
					}
					
					if(sh_ledger.getForward().equalsIgnoreCase("save"))
					{
						ShareReportObject[] sh_rep=null;
						 System.out.println("Inside save ");
                		 //TO save to an excel Sheet
                		 res.setContentType("application/.csv");
                	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
                	        
                	        java.io.PrintWriter out = res.getWriter();
                	        out.print("\n");
                	        out.print("\n");
                	        out.print("\n");
                	        out.print(",");out.print(",");out.print(",");
                	        out.print("Share Ledger Details: ");
                	        out.print("\n");
                	        out.print("\n");
                   		   /*HSSFCell cell = row.createCell((short)0);
                   		   cell.setCellValue(1);*/
                	       
                   		out.print("transDate"); out.print(",");
                   		out.print("name"); out.print(",");
                   		out.print("debitAmount"); out.print(",");
                   		out.print("creditAmount"); out.print(",");
                   		out.print("share_bal"); out.print("\n");
                   		try{
							
							sh_rep=shDelegate.getShareLedger(4, null, null, Integer.parseInt(sh_ledger.getAcno()),sh_ledger.getAcctype());
							}catch(Exception e){
								e.printStackTrace();
							}
							for(int k=0;k<sh_rep.length;k++){
                     			  
                       			out.print(sh_rep[k].getTransDate());
                       			out.print(",");
                       			out.print(sh_rep[k].getName());
                       			out.print(",");
                       			out.print(sh_rep[k].getDebitAmount());
                       			out.print(",");
                       			out.print(sh_rep[k].getCreditAmount());
                       			out.print(",");
                       			out.print(sh_rep[k].getshare_bal());
                       			out.print(",");
                       			out.print("\n");
                       			
							}
							 req.setAttribute("msg","Saved to excel file in C:");
                 		    return null;
                 		 }
					}
				}
			catch (Exception e) 
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
			
		}
		
	       /* For Dividend Data Entry */
		
		else if(map.getPath().equalsIgnoreCase("/Share/DividendEntryMenu"))
		{
			try{
			    DividendDataForm div_data=(DividendDataForm)form;
				System.out.println("The page iid is "+div_data.getPageId());
				req.setAttribute("pagenum", div_data.getPageId());
				
				if(MenuNameReader.containsKeyScreen(div_data.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(div_data.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					req.setAttribute("focusto","acctype");
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		
		else if(map.getPath().equalsIgnoreCase("/Share/DividendEntry"))
		{
			DividendDataForm div_data=(DividendDataForm)form;
			String path;
			try{
				req.setAttribute("pagenum", div_data.getPageId());
				if(MenuNameReader.containsKeyScreen(div_data.getPageId()))
				{

					path=MenuNameReader.getScreenProperties(div_data.getPageId());
					shDelegate=new ShareDelegate();
					req.setAttribute("pageId",path);
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("focusto","acctype");
					System.out.println("the delegate is "+shDelegate);

					/*Conditions starts here*/
					
					if(Integer.parseInt(div_data.getAcno())!=0)
					{
						int maxnum=shDelegate.checkShareNumber(Integer.parseInt(div_data.getAcno()),0);
						if(Integer.parseInt(div_data.getAcno())>maxnum)
						{
							System.out.println("Check gor null");
							req.setAttribute("msg","Account not found");
						}
						else
						{
							req.setAttribute("msg","");
							req.setAttribute("name",shDelegate.getCustDetails(Integer.parseInt(div_data.getAcno())));
						}
					}
					
					if(div_data.getPay_mode().trim().equalsIgnoreCase("Cash"))
					{
						System.out.println("CHECKING HERE"+div_data.getPay_mode());
						req.setAttribute("Disable",true);
					}
					
					if(div_data.getPay_ac_no().length()!=0)
					{
						AccountObject accobj=shDelegate.getIntrodet(div_data.getPay_ac_type(), Integer.parseInt(div_data.getPay_ac_no()));
						if(accobj==null)
						{
							req.setAttribute("msg","Account not found");
						}
						else if(accobj.getAccStatus().equalsIgnoreCase("C"))
						{
							req.setAttribute("msg","Account is closed");
						}
						else
						{
							req.setAttribute("msg","");
							req.setAttribute("acname", shDelegate.getAcname(Integer.parseInt(div_data.getPay_ac_no()), div_data.getPay_ac_type()));
						}
						
					}
					
					if(div_data.getForward().equalsIgnoreCase("SUBMIT"))
					{
						System.out.println("SUBMITTING THE DIVIDEND DETAILS");
						String ac_type=div_data.getAcctype();
						System.out.println(div_data.getAcctype());
						String shno=div_data.getAcno();
						System.out.println(div_data.getAcno());
						String div_date=div_data.getDiv_dt();
						System.out.println(div_data.getDiv_dt());
						String div_amt=div_data.getDiv_amt();
						System.out.println(div_data.getDiv_amt());
						String paymode=div_data.getPay_mode();
						System.out.println(div_data.getPay_mode());
						String pay_ac_type=div_data.getPay_ac_type();
						System.out.println(div_data.getPay_ac_type());
						String pay_ac_no=div_data.getPay_ac_no();
						System.out.println(div_data.getPay_ac_no());
						System.out.println("END HERE");
						
						int result=shDelegate.storeDividendData(ac_type,Integer.parseInt(shno),Float.parseFloat(div_amt),div_date,paymode,Integer.parseInt(pay_ac_no),pay_ac_type,shuser,shtml);
						if(result==1)
						{
							System.out.println("DATA STORED SUCCESSFULLY");
							req.setAttribute("msg","Dividend Data Stored Successfully");
						}
						else
						{
							System.out.println("ERROR IN STORING");
							req.setAttribute("msg","Error in Storing");
						}
					}
					
					
				}
				
			}
			catch (Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		
	                   /* For Convert temp to Perm Shares*/	
		
		else if(map.getPath().equalsIgnoreCase("/Share/ConvertTempMenu"))
		{
			System.out.println("GOING TO CONVERT");
			
			try{
			    TempPermForm temp_form=(TempPermForm)form;
				System.out.println("The page iid is "+temp_form.getPageId());
				req.setAttribute("pagenum", temp_form.getPageId());
				System.out.println("CHECK AGAIN");
				
				if(MenuNameReader.containsKeyScreen(temp_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(temp_form.getPageId());
					shDelegate=new ShareDelegate();
					temp_form.setFrm_date(shDelegate.getSysDate());
					temp_form.setTo_date(shDelegate.getSysDate());
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					
					System.out.println("SURAJ IS CHECKINHG HERE");
					req.setAttribute("focusto","date");
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		
		else if(map.getPath().equalsIgnoreCase("/Share/ConvertTemp"))
		{
			try{
				req.setAttribute("disabled",null);
			    TempPermForm temp_form=(TempPermForm)form;
				System.out.println("The page iidd is "+temp_form.getPageId());
				req.setAttribute("pagenum", temp_form.getPageId());
				
				if(MenuNameReader.containsKeyScreen(temp_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(temp_form.getPageId());
					shDelegate=new ShareDelegate();
					System.out.println("Check 1");
					setShOpeningInitParams(req, path, shDelegate);
					//session.getAttribute("tempperm");
					System.out.println("Check 2");
					
					//List convert_list=(ArrayList)session.getAttribute("tempperm");
					
					System.out.println("Check 3");
					
					String method=req.getParameter("method");
					System.out.println("The method is "+method);
					
					req.setAttribute("pageId",path);
					req.setAttribute("focusto","date");
					
					//String method=req.getParameter("method"); 
					/*if(temp_form.getChk1()!=null)
					{
						System.out.println("The value of getchk1 is"+temp_form.getChk1());
						System.out.println("CHECKING HERE FOR VISIBLITY");
					
						req.setAttribute("vis", "visible");
						
					}
					if(temp_form.getChk1()==null)
					{
						System.out.println("CHECKING HERE FOR VISIBLITY");
						req.setAttribute("vis", "hidden");
					}
					
*/					
					if(method!=null && method.equalsIgnoreCase("Reset"))
					{  //needs to be changed
						System.out.println("DISPLAYING");
						System.out.println("suraj is entering"+temp_form.getFrm_date());
						System.out.println("999999"+temp_form.getTo_date());
					    System.out.println("THE CHECK BOX VALUE IS"+temp_form.getChk());	
						if(temp_form.getFrm_date().length()>0 && temp_form.getTo_date().length()>0)
						{
							//req.setAttribute("tempperm",shDelegate.converttemp_perm_date(temp_form.getFrm_date().trim(),temp_form.getTo_date().trim()));
							System.out.println("suraj is here");

							List con_list=setConvertToPerm(shDelegate,req,temp_form.getFrm_date(),temp_form.getTo_date(),1);
							if(con_list.size()==0){
								req.setAttribute("msg","Records not found");
							}
							else{
								setConvertToPerm(shDelegate,req,temp_form.getFrm_date(),temp_form.getTo_date(),1);
							}
							
						}
						
						
/*						if(temp_form.getFrm_date().length()>0 || temp_form.getTo_date().length()>0)
						{
							System.out.println("Working fine");
							// req.setAttribute("tempperm", shDelegate.converttemp_perm());
							 setConvertToPerm(shDelegate,req);
							 
						}
*/						
						//if(temp_form.getForward().equalsIgnoreCase("SUBMIT"))
						/*if(method.equalsIgnoreCase("Save"))
						{
							System.out.println("The method in save is"+method);
							System.out.println("The id in save is"+req.getParameter("id"));

						}*/

						/*if(temp_form.getSh_num().length()>0){
							System.out.println("The acccount number is "+temp_form.getSh_num());
						}*/
						req.setAttribute("disabled", "enableme");	
						
					}
					else if(method.equalsIgnoreCase("Save")){
						String ids_value[]=null;
					/*	if(req.getParameter("id")!=null){
							System.out.println("The parameter id "+req.getParameter("id"));
						}
					*/
						if(req.getParameterValues("id")!=null){
							System.out.println("After checking parameter values for not null---------------");
							ids_value=req.getParameterValues("id");
							System.out.println("The length is ====>"+ids_value.length);
							for(int i=0;i<ids_value.length;i++){
							System.out.println("The parameter id "+ids_value[i]);
							}
						System.out.println("---1----");
						List convert=(ArrayList)session.getAttribute("tempperm");
						System.out.println("---2----");
						//String id=req.getParameter("id");
						String[] ids=new String[convert.size()];//store list size
						System.out.println("----3----");
						System.out.println("The ids value is======= "+ids.length);
						
					  	/*for(int j=0;j<ids.length;j++){
					  		System.out.println("----4----");
					  		
					  		ids[j]=req.getParameter("id");
					  		
					  	}*/
					  	
					  	System.out.println("----7----");
					  	System.out.println("I am in Save");
						//System.out.println("The value in convert is"+convert);
						if(convert!=null){
							Map map_data=null;
							System.out.println("The size of the list is------------>>"+convert.size());
							
							for(int i=0;i<convert.size();i++){
								System.out.println("Entering for loop");
								map_data=(TreeMap)convert.get(i);
								String useid=map_data.get("id").toString(); 
								System.out.println("the got id is"+map_data.get("id"));
								
								
								
								System.out.println("The id value is"+useid);
								//for(int k=0;k<ids.length;k++){
									for(int k=0;k<ids_value.length;k++){
									System.out.println("The value of ids in for loop is ------->"+ids_value[k]);
								if(ids_value[k].equals(useid))
								{
									System.out.println("printing now");
									System.out.print(map_data.get("ShareNo")+" "+map_data.get("Name")+" "+map_data.get("cid")+" "+map_data.get("BranchCode")+" "+map_data.get("ShCat")+" "+map_data.get("IssueDate")+" "+map_data.get("NumofShare")+" "+map_data.get("ShareValue"));
									
									shobjarr=new ShareMasterObject[1];
									shobjarr[0]=new ShareMasterObject();
									System.out.println("Setting share master values");
									shobjarr[0].setTempShareNumber(Integer.parseInt(map_data.get("ShareNo").toString()));
									shobjarr[0].setShareAccType("1001001");
									shobjarr[0].setMemberCategory(Integer.parseInt(map_data.get("ShCat").toString()));
									shobjarr[0].setShareStatus("T");
									shobjarr[0].setRecievedMode("T");
									shobjarr[0].setNumberofShares(Double.parseDouble(map_data.get("NumofShare").toString()));
									shobjarr[0].setAmount(Double.parseDouble(map_data.get("ShareValue").toString()));
									shobjarr[0].uv.setVerId(shtml);
									shobjarr[0].uv.setVerTml(shtml);
									System.out.println("Out of values");
									shDelegate.confirmPermShare(shobjarr,temp_form.getDate(),1);
									
								}
								}
							}

						}
						}
					}
						setConvertToPerm(shDelegate,req,temp_form.getFrm_date(),temp_form.getTo_date(),1);
					    req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		
		/* for convertion of temp to permenant aditional shares */
		else if(map.getPath().equalsIgnoreCase("/Share/ConvertTempAddMenu"))
		{
			System.out.println("GOING TO CONVERT");
			
			try{
			    TempPermForm temp_form=(TempPermForm)form;
				System.out.println("The page iid is "+temp_form.getPageId());
				req.setAttribute("pagenum", temp_form.getPageId());
				System.out.println("CHECK AGAIN");
				
				if(MenuNameReader.containsKeyScreen(temp_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(temp_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					System.out.println("SURAJ IS CHECKINHG HERE");
					req.setAttribute("focusto","date");
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		
		else if(map.getPath().equalsIgnoreCase("/Share/ConvertTempAdd"))
		{
			try{
			    TempPermForm temp_form=(TempPermForm)form;
				System.out.println("The page iidd is "+temp_form.getPageId());
				req.setAttribute("pagenum", temp_form.getPageId());
				
				if(MenuNameReader.containsKeyScreen(temp_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(temp_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					req.setAttribute("focusto","date");
					String method=req.getParameter("method"); 
					if(temp_form.getChk1()!=null)
					{
						System.out.println("CHECKING HERE FOR VISIBLITY");
						req.setAttribute("vis", "visible");
					}
					if(temp_form.getChk1()==null)
					{
						System.out.println("CHECKING HERE FOR VISIBLITY");
						req.setAttribute("vis", "hidden");
					}
					
					
					if(method!=null && method.equalsIgnoreCase("reset"))
					{  //needs to be changed
						System.out.println("DISPLAYING");
						System.out.println("suraj is entering"+temp_form.getFrm_date());
						System.out.println("temp to date==>"+temp_form.getTo_date());
					    System.out.println("THE CHECK BOX VALUE IS"+temp_form.getChk());	
						if(temp_form.getFrm_date().length()>0 && temp_form.getTo_date().length()>0)
						{
							//req.setAttribute("tempperm",shDelegate.converttemp_perm_date(temp_form.getFrm_date().trim(),temp_form.getTo_date().trim()));
							System.out.println("suraj is here");
							List con_list=setConvertToPerm(shDelegate,req,temp_form.getFrm_date(), temp_form.getTo_date(), 2);
							if(con_list.size()==0){
								req.setAttribute("msg","Records Not found");
							}
							else{
								setConvertToPerm(shDelegate,req,temp_form.getFrm_date(), temp_form.getTo_date(), 2);
							}
						}
						
						
						/*if(temp_form.getFrm_date().length()>0 || temp_form.getTo_date().length()>0)
						{
							 //req.setAttribute("tempperm", shDelegate.converttemp_perm());
							 System.out.println("THe ac no"+temp_form.getSh_num());
							 setConvertToPerm(shDelegate,req,null,null, 2);
							 
						}*/
												
					}
					
					else if(method.equalsIgnoreCase("Save")){
						List convert=(ArrayList)session.getAttribute("tempperm");
						String id_value[]=req.getParameterValues("id");
						
						System.out.println("I am in Save");
					    
						System.out.println("The value in convert is"+convert);
						if(convert!=null){
							Map map_data=null;
							System.out.println("The size of the list is------------>>"+convert.size());
							if(id_value!=null){
							for(int i=0;i<convert.size();i++){
								System.out.println("Entering for loop");
								map_data=(TreeMap)convert.get(i);
								String useid=map_data.get("id").toString(); 
								System.out.println("the got id is"+map_data.get("id"));
								for(int k=0;k<id_value.length;k++){
								if(id_value[k].equals(useid))
								{ 
									System.out.println("printing now");
									System.out.print(map_data.get("ShareNo")+" "+map_data.get("Name")+" "+map_data.get("cid")+" "+map_data.get("BranchCode")+" "+map_data.get("ShCat")+" "+map_data.get("IssueDate")+" "+map_data.get("NumofShare")+" "+map_data.get("ShareValue"));
									
									shobjarr=new ShareMasterObject[1];
									shobjarr[0]=new ShareMasterObject();
									System.out.println("Setting share master values");
									shobjarr[0].setTempShareNumber(Integer.parseInt(map_data.get("ShareNo").toString()));
									shobjarr[0].setShareAccType("1001001");
									shobjarr[0].setShareNumber(Integer.parseInt(map_data.get("Ac_no").toString()));
									shobjarr[0].setMemberCategory(Integer.parseInt(map_data.get("ShCat").toString()));
									shobjarr[0].setShareStatus("T");
									shobjarr[0].setNumberofShares(Double.parseDouble(map_data.get("NumofShare").toString()));
									shobjarr[0].setAmount(Double.parseDouble(map_data.get("ShareValue").toString()));
									shobjarr[0].uv.setVerId(shtml);
									shobjarr[0].uv.setVerTml(shtml);
									System.out.println("Out of values");
									shDelegate.confirmPermShare(shobjarr,temp_form.getDate(),2);
									req.setAttribute("msg","Selected Shares are confirmed");
								}
								}
							}
							}
						}
						setConvertToPerm(shDelegate,req,temp_form.getFrm_date(),temp_form.getTo_date(),2);
						req.setAttribute("pageId",path);
						
					}
					
					
						return map.findForward(ResultHelp.getSuccess());
					}
					else
					{
						path=MenuNameReader.getScreenProperties("0000");
						setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
						return map.findForward(ResultHelp.getError());
					}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		
						/* Share Dispatch */
		
		else if(map.getPath().equalsIgnoreCase("/Share/ShareDispatchMenu"))
		{
			System.out.println("GOING TO CONVERT");
			
			try{
			    TempPermForm temp_form=(TempPermForm)form;
				System.out.println("The page iid is "+temp_form.getPageId());
				req.setAttribute("pagenum", temp_form.getPageId());
				System.out.println("CHECK AGAIN");
				
				if(MenuNameReader.containsKeyScreen(temp_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(temp_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					System.out.println("SURAJ IS CHECKINHG HERE");
					req.setAttribute("focusto","date");
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		
		else if(map.getPath().equalsIgnoreCase("/Share/ShareDispatch"))
		{
			try{
			    TempPermForm temp_form=(TempPermForm)form;
				System.out.println("The page iidd is "+temp_form.getPageId());
				req.setAttribute("pagenum", temp_form.getPageId());
				
				if(MenuNameReader.containsKeyScreen(temp_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(temp_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					req.setAttribute("focusto","date");
					String method=req.getParameter("method");
					System.out.println("The method is"+method);
					if(temp_form.getChk1()!=null)
					{
						System.out.println("CHECKING HERE FOR VISIBLITY");
						req.setAttribute("vis", "visible");
					}
					if(temp_form.getChk1()==null)
					{
						System.out.println("CHECKING HERE FOR VISIBLITY");
						req.setAttribute("vis", "hidden");
					}
					
					
					if(method!=null && method.equalsIgnoreCase("reset"))
					{  //needs to be changed
							
						if(temp_form.getFrm_date().length()>0 && temp_form.getTo_date().length()>0)
						{
							setConvertToPerm(shDelegate,req,temp_form.getFrm_date(),temp_form.getTo_date(),3);
						}
						}
					else if(method.equalsIgnoreCase("save")){
						session=req.getSession(true);
						List convert=(ArrayList)session.getAttribute("tempperm");
						String id=req.getParameter("id");
						if(convert!=null){
							Map map_data=null;
						if(id!=null){
							for(int i=0;i<convert.size();i++){
								System.out.println("Entering for loop");
								map_data=(TreeMap)convert.get(i);
								String useid=map_data.get("id").toString(); 
								System.out.println("the got id is"+map_data.get("id"));
								if(id.equals(useid))
								{
									System.out.println("printing now");
									System.out.print("The ac no form Share Dispatch -------------->"+map_data.get("ShareNo").toString());
									int ac_no=Integer.parseInt(map_data.get("ShareNo").toString());
									shDelegate.shareDispatch(ac_no);
								}
							}
						}
						}
						System.out.println("What to do now????");
						//setConvertToPerm(shDelegate,req,temp_form.getFrm_date(),temp_form.getTo_date(),3);
						try{
					    req.setAttribute("pageId",path);
						}catch(Exception e){
							e.printStackTrace();
						}
						return map.findForward(ResultHelp.getSuccess());
					}
						return map.findForward(ResultHelp.getSuccess());
					}
					else
					{
						path=MenuNameReader.getScreenProperties("0000");
						setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
						return map.findForward(ResultHelp.getError());
					}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
								/* Share Regularization*/
		else if(map.getPath().equalsIgnoreCase("/Share/RegularizationMenu"))
		{
			System.out.println("GOING TO CONVERT");
			
			try{
			    RegularizationForm reg_form=(RegularizationForm)form;
				System.out.println("The page iid is "+reg_form.getPageId());
				req.setAttribute("pagenum", reg_form.getPageId());
				System.out.println("CHECK AGAIN");
				
				if(MenuNameReader.containsKeyScreen(reg_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(reg_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					System.out.println("SURAJ IS CHECKINHG HERE");
					//req.setAttribute("focusto","date");
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		else if(map.getPath().equalsIgnoreCase("/Share/Regularization"))
		{
			try{
			    RegularizationForm reg_form=(RegularizationForm)form;
				req.setAttribute("pagenum", reg_form.getPageId());
				if(MenuNameReader.containsKeyScreen(reg_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(reg_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					if(reg_form.getForward().equalsIgnoreCase("DISPLAY"))
					{
						req.setAttribute("regularization", shDelegate.getTempNos());
					}
					if(reg_form.getForward().equalsIgnoreCase("SUBMIT") && reg_form.getTemp_num()!=null && reg_form.getPerm_num()!=null && reg_form.getPerm_num().trim().length()!=0 && reg_form.getTemp_num().trim().length()!=0)
					{
						
						ShareMasterObject shm[]=new ShareMasterObject[1];
						//shm=shDelegate.converttemp_perm();
						shm[0]=new ShareMasterObject();
						shm[0].setTempShareNumber(Integer.parseInt(reg_form.getTemp_num()));
						shm[0].setShareNumber(Integer.parseInt(reg_form.getPerm_num()));
						shDelegate.shareregularization(shm);
					}
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		
		else if(map.getPath().equalsIgnoreCase("/Share/UnClaimedDividendMenu"))
		{
			try{
			    UnclaimedDivForm un_claim=(UnclaimedDivForm)form;
				req.setAttribute("pagenum", un_claim.getPageId());
				
				if(MenuNameReader.containsKeyScreen(un_claim.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(un_claim.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					un_claim.setDate(shDelegate.getSysDate());
					System.out.println("SURAJ IS CHECKINHG HERE");
					//req.setAttribute("focusto","date");
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}

		else if(map.getPath().equalsIgnoreCase("/Share/UnClaimedDividend"))
		{
			try{
			    UnclaimedDivForm un_claim=(UnclaimedDivForm)form;
				System.out.println("The page iid is "+un_claim.getPageId());
				req.setAttribute("pagenum", un_claim.getPageId());
				System.out.println("CHECK AGAIN");
				
				if(MenuNameReader.containsKeyScreen(un_claim.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(un_claim.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					System.out.println("SURAJ IS CHECKINHG HERE");
					req.setAttribute("focusto","actype");
					
					if(un_claim.getForward().equalsIgnoreCase("View"))
					{
						DividendObject[] div_obj=null;
						try{
						div_obj=shDelegate.unclaimeddiv_list(Integer.parseInt(un_claim.getBrcode()),Validations.convertYMD(un_claim.getDate().trim()) ,Integer.parseInt(un_claim.getAc_start()),Integer.parseInt(un_claim.getAc_end()));
						}catch(Exception e){
							e.printStackTrace();
						}
						if(div_obj==null || div_obj.equals("null"))
						{
							req.setAttribute("msg","No records found");
						}
						else
						{
							un_claim.setValidations("");
							req.setAttribute("unclaimeddiv",div_obj);
						}
					}
					
					if(un_claim.getForward().equalsIgnoreCase("save"))
					{
                		 //TO save to an excel Sheet
                		 res.setContentType("application/.csv");
                	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
                	        
                	        java.io.PrintWriter out = res.getWriter();
                	        out.print("\n");
                	        out.print("\n");
                	        out.print("\n");
                	        out.print(",");out.print(",");out.print(",");
                	        out.print("List of Unclaimed Dividend Details: ");
                	        out.print("\n");
                	        out.print("\n");
                   		   /*HSSFCell cell = row.createCell((short)0);
                   		   cell.setCellValue(1);*/
                	       
                   		out.print("divAccNo"); out.print(",");
                   		out.print("name"); out.print(",");
                   		out.print("divDate"); out.print(",");
                   		out.print("divAmount"); out.print("\n");
                   		DividendObject[] div_obj=null;
						try{
						div_obj=shDelegate.unclaimeddiv_list(Integer.parseInt(un_claim.getBrcode()),Validations.convertYMD(un_claim.getDate().trim()) ,Integer.parseInt(un_claim.getAc_start()),Integer.parseInt(un_claim.getAc_end()));
						System.out.println("The object is "+div_obj);
						}catch(Exception e){
							e.printStackTrace();
						}
                   		for(int k=0;k<div_obj.length;k++){
                 			  
                   			out.print(div_obj[k].getDivAccNo());
                   			out.print(",");
                   			out.print(div_obj[k].getName());
                   			out.print(",");
                   			out.print(div_obj[k].getDivDate());
                   			out.print(",");
                   			out.print(div_obj[k].getDivAmount());
                   			out.print(",");
                   			out.print("\n");  
                   			
                   		}
                   		req.setAttribute("msg","Saved to excel file in C:");
            		    return null;
					}
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		
		else if(map.getPath().equalsIgnoreCase("/Share/DividendRegistryMenu"))
		{
			try{
			    DividendRegistryForm divreg_form=(DividendRegistryForm)form;
				req.setAttribute("pagenum", divreg_form.getPageId());
				if(MenuNameReader.containsKeyScreen(divreg_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(divreg_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					divreg_form.setDate(shDelegate.getSysDate());
					req.setAttribute("focusto","date");
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
			
		}
		
		else if(map.getPath().equalsIgnoreCase("/Share/DividendRegistry"))
		{
			try{
			    DividendRegistryForm divreg_form=(DividendRegistryForm)form;
				req.setAttribute("pagenum", divreg_form.getPageId());
				if(MenuNameReader.containsKeyScreen(divreg_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(divreg_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					req.setAttribute("focusto","date");
					divreg_form.setDate(divreg_form.getDate());
					
					if((divreg_form.getComb_branch().equalsIgnoreCase("Branch Wise")))
					{
						req.setAttribute("Disable3", true);
						req.setAttribute("Disable2", false);
										
					}
					else if(divreg_form.getIndividual()!=null){
						
						if(divreg_form.getIndividual().equalsIgnoreCase("Individual"))
					{
						req.setAttribute("Disable2", false);
						req.setAttribute("Disable3", false);
					}
					}
				   
					if(divreg_form.getForward().equalsIgnoreCase("View"))
					{
						if(divreg_form.getComb_branch().equalsIgnoreCase("Branch Wise") && divreg_form.getIndividual().equalsIgnoreCase("Individual"))
						{
							req.setAttribute("divregistery",shDelegate.dividendregistry(divreg_form.getDate(), Integer.parseInt(divreg_form.getBrcode()), 0, "") );
						}
						
						if(divreg_form.getComb_branch().equalsIgnoreCase("Branch Wise") && divreg_form.getIndividual().equalsIgnoreCase("All"))
						{
							req.setAttribute("divregistery",shDelegate.dividendregistry(divreg_form.getDate(), 0, 1, "") );
						}
						
						if(divreg_form.getComb_branch().equalsIgnoreCase("Combined"))
						{
							req.setAttribute("divregistery",shDelegate.dividendregistry(divreg_form.getDate(), 0, 2, "") );
						}
					}
					
					if(divreg_form.getForward().equalsIgnoreCase("save"))
					{
						ShareMasterObject[] sharemasterobj=null;
						System.out.println("Inside save ");
               		 //TO save to an excel Sheet
               		 res.setContentType("application/.csv");
               	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
               	        java.io.PrintWriter out = res.getWriter();
               	        out.print("\n");
               	        out.print("\n");
               	        out.print("\n");
               	        out.print(",");out.print(",");out.print(",");
               	        out.print("Divident Registry Details: ");
               	        out.print("\n");
               	        out.print("\n");
                  		   /*HSSFCell cell = row.createCell((short)0);
                  		   cell.setCellValue(1);*/
               	        out.print("shareNumber"); out.print(",");
                		out.print("name"); out.print(",");
                		out.print("issueDate"); out.print(",");
                		out.print("numberofShares"); out.print(",");
                		out.print("shareBalance"); out.print(",");
                		out.print("payMode"); out.print(",");
                		out.print("paymentAccno"); out.print(",");
                		out.print("branchName"); out.print(",");
                		out.print("divAmount"); out.print(",");
                		out.print("drfAmount"); out.print("\n");
               	     if(divreg_form.getComb_branch().equalsIgnoreCase("Branch Wise") && divreg_form.getIndividual().equalsIgnoreCase("Individual"))
						{
               	    	sharemasterobj=shDelegate.dividendregistry(divreg_form.getDate(), Integer.parseInt(divreg_form.getBrcode()), 0, "");
						}
						
						if(divreg_form.getComb_branch().equalsIgnoreCase("Branch Wise") && divreg_form.getIndividual().equalsIgnoreCase("All"))
						{
							sharemasterobj=shDelegate.dividendregistry(divreg_form.getDate(), 0, 1, "");
						}
						
						if(divreg_form.getComb_branch().equalsIgnoreCase("Combined"))
						{
							sharemasterobj=shDelegate.dividendregistry(divreg_form.getDate(), 0, 2, "");
						}
						 for(int k=0;k<sharemasterobj.length;k++){
                  			  
                    			out.print(sharemasterobj[k].getShareNumber());
                    			out.print(",");
                    			out.print(sharemasterobj[k].getName());
                    			out.print(",");
                    			out.print(sharemasterobj[k].getIssueDate());
                    			out.print(",");
                    			out.print(sharemasterobj[k].getNumberofShares());
                    			out.print(",");
                    			out.print(sharemasterobj[k].getShareBalance());
                    			out.print(",");
                    			out.print(sharemasterobj[k].getPayMode());
                    			out.print(",");
                    			out.print(sharemasterobj[k].getPaymentAccno());
                    			out.print(",");
                    			out.print(sharemasterobj[k].getBranchName());
                    			out.print(",");
                    			out.print(sharemasterobj[k].getDivAmount());
                    			out.print(",");
                    			out.print(sharemasterobj[k].getDrfAmount());
                    			out.print(",");
                    			out.print("\n");
               	        
					}
						req.setAttribute("msg","Saved to excel file in C:");
             		    return null;
					}
					
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				e.printStackTrace();
				return map.findForward(ResultHelp.getError());
			}
			
		}
		
								/* Share Master Updation  */
		
		else if(map.getPath().equalsIgnoreCase("/Share/MasterUpdationMenu"))
		{
			try{
			    MasterUpdationForm master_form=(MasterUpdationForm)form;
				req.setAttribute("pagenum", master_form.getPageId());
				req.setAttribute("focusto", "acno");
				if(MenuNameReader.containsKeyScreen(master_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(master_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
			
		}
		
		else if(map.getPath().equalsIgnoreCase("/Share/MasterUpdation"))
		{
			try{
			    MasterUpdationForm master_form=(MasterUpdationForm)form;
				req.setAttribute("pagenum", master_form.getPageId());
				if(MenuNameReader.containsKeyScreen(master_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(master_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					if(master_form.getAcno()!=null && master_form.getAcno().trim().length()>0 && master_form.getAcno().trim().length()<11 && Integer.parseInt(master_form.getAcno())!=0)
					{
						int maxnum=shDelegate.checkShareNumber(Integer.parseInt(master_form.getAcno()),0);
						if(Integer.parseInt(master_form.getAcno())>maxnum)
						{
							req.setAttribute("msg","Account not found");
						}
						
						else if(Integer.parseInt(master_form.getAcno())!=0)
						{
							
 							AccountObject accobj=shDelegate.getIntrodet(master_form.getActype(),Integer.parseInt(master_form.getAcno()));
 							if(accobj==null)
 							{
 								req.setAttribute("msg","Account is Closed. You cannot Update");
 							}
						
						else
						{
							req.setAttribute("ShareDetails", shDelegate.getShareMaster(master_form.getActype(), Integer.parseInt(master_form.getAcno())));	
						}
					}
				}else if(master_form.getAcno().trim().length()>=11){
					req.setAttribute("msg", "Please Enter Proper Share Account Number");
				}
					
					if(master_form.getForward().equalsIgnoreCase("UPDATE"))
					{
						 req.setAttribute("disablefield",false);
					}
					
					if(master_form.getForward().equalsIgnoreCase("SUBMIT")&&master_form.getAcno()!=null&&master_form.getBrcode()!=null)
					{
						ShareMasterObject shobj=new ShareMasterObject();
						shobj.setShnum(Integer.parseInt(master_form.getAcno()));
						shobj.setShareNumber(Integer.parseInt(master_form.getAcno()));
						shobj.setShareAccType(master_form.getActype());
						shobj.setShareStatus(master_form.getSharestate());
						shobj.setBranchCode(Integer.parseInt(master_form.getBrcode()));
						shobj.setMailingAddress(Integer.parseInt(master_form.getAddtype()));
						shobj.setMemberCategory(Integer.parseInt(master_form.getMemcat()));
						shobj.setIssueDate(master_form.getIssuedate());
						shobj.setIntroducerAcctype(master_form.getIntrtype());
						shobj.setIntroducerAccno(Integer.parseInt(master_form.getIntracno()));
						shobj.setDivUptoDate(master_form.getDiv_date());
						shobj.setCloseDate(master_form.getClosedate());
						if(master_form.getPaymode().equalsIgnoreCase("Transfer"))
						{
							shobj.setPayMode("T");
						}
						else
						{
							shobj.setPayMode("C");
						}
						shobj.setPaymentAcctype(master_form.getPaytype());
						shobj.setPaymentAccno(Integer.parseInt(master_form.getPayacno()));
						shDelegate.masterupdation(shobj, master_form.getActype().trim(), Integer.parseInt(master_form.getAcno().trim()));
					}
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		
		
		/******************************** Share Master Log ****************************************/
		else if(map.getPath().equalsIgnoreCase("/Share/ViewLogMenu"))
		{
			try{
			    ViewlogForm view_form=(ViewlogForm)form;
				req.setAttribute("pagenum", view_form.getPageId());
				if(MenuNameReader.containsKeyScreen(view_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(view_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		
		else if(map.getPath().equalsIgnoreCase("/Share/ViewLog"))
		{
			try{
			    ViewlogForm view_form=(ViewlogForm)form;
				req.setAttribute("pagenum", view_form.getPageId());
				if(MenuNameReader.containsKeyScreen(view_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(view_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					if(Integer.parseInt(view_form.getShnum())!=0)
					{
						int maxnum=shDelegate.checkShareNumber(Integer.parseInt(view_form.getShnum()),0);
						if(Integer.parseInt(view_form.getShnum())>maxnum)
						{
							req.setAttribute("msg","Account not found");	
						}
						else
						{
						    req.setAttribute("msg","");
						    ShareMasterObject[] shobj=shDelegate.sharemasterlog(view_form.getActype(), Integer.parseInt(view_form.getShnum()));
						    for(int i=0;i<shobj.length;i++)
						    {
						    	req.setAttribute("color", "blue");
						    }
						    req.setAttribute("Viewlog1",shobj);
						}
					}
					
					if(view_form.getForward().equalsIgnoreCase("save"))
					{
                		 //TO save to an excel Sheet
                		 res.setContentType("application/.csv");
                	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
                	        
                	        java.io.PrintWriter out = res.getWriter();
                	        out.print("\n");
                	        out.print("\n");
                	        out.print("\n");
                	        out.print(",");out.print(",");out.print(",");
                	        out.print("Share MasterLog Details: ");
                	        out.print("\n");
                	        out.print("\n");
                   		   /*HSSFCell cell = row.createCell((short)0);
                   		   cell.setCellValue(1);*/
                	       
                   		out.print("shareNumber"); out.print(",");
                   		out.print("mailingAddress"); out.print(",");
                   		out.print("divUptoDate"); out.print(",");
                   		out.print("issueDate"); out.print(",");
                   		out.print("closeDate"); out.print(",");
                   		out.print("loanAvailed"); out.print(",");
                   		out.print("payMode"); out.print(",");
                   		out.print("paymentAcctype"); out.print(",");
                   		out.print("paymentAccno"); out.print("\n");
                   		
                   		ShareMasterObject[] shobj=shDelegate.sharemasterlog(view_form.getActype(), Integer.parseInt(view_form.getShnum()));
                   		
                   	 for(int k=0;k<shobj.length;k++){
              			  
                			out.print(shobj[k].getShareNumber());
                			out.print(",");
                			out.print(shobj[k].getMailingAddress());
                			out.print(",");
                			out.print(shobj[k].getDivUptoDate());
                			out.print(",");
                			out.print(shobj[k].getIssueDate());
                			out.print(",");
                			out.print(shobj[k].getCloseDate());
                			out.print(",");
                			out.print(shobj[k].getLoanAvailed());
                			out.print(",");
                			out.print(shobj[k].getPayMode());
                			out.print(",");
                			out.print(shobj[k].getPaymentAcctype());
                			out.print(",");
                			out.print(shobj[k].getPaymentAccno());
                			out.print(",");
                			out.print("\n");
                		}
                   	req.setAttribute("msg","Saved to excel file in C:");
        		    return null;
					}
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		/****************************End of View Log***************************/
		/****************************Start of Dividend Report***************************/
		else if(map.getPath().equalsIgnoreCase("/Share/DividendReportMenu"))
		{
			try{
			    DividendReportForm div_rep=(DividendReportForm)form;
				req.setAttribute("pagenum", div_rep.getPageId());
				if(MenuNameReader.containsKeyScreen(div_rep.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(div_rep.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		
		else if(map.getPath().equalsIgnoreCase("/Share/DividendReport"))
		{
			try{
			    DividendReportForm div_rep=(DividendReportForm)form;
				req.setAttribute("pagenum", div_rep.getPageId());
				if(MenuNameReader.containsKeyScreen(div_rep.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(div_rep.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					if(div_rep.getForward().equalsIgnoreCase("VIEW"))
					{
						if(div_rep.getPay_type().equalsIgnoreCase("T"))
						{
							req.setAttribute("div_report",shDelegate.retrivepayment(div_rep.getFrm_dt(), div_rep.getTo_dt(), Integer.parseInt(div_rep.getAcno()), div_rep.getActype(), 0));
						}
						if(div_rep.getPay_type().equalsIgnoreCase("C"))
						{
							shDelegate.retrivepayment(div_rep.getFrm_dt(), div_rep.getTo_dt(), Integer.parseInt(div_rep.getAcno()), div_rep.getActype(), 2);
						}
					}
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
	/******************************** End of Dividend Report *********************/	
		
	/******************************** Start of Share Certificate *********************/	
		else if(map.getPath().equalsIgnoreCase("/Share/CertificateMenu"))
		{
			try{
			    CertificateForm certi_form=(CertificateForm)form;
				req.setAttribute("pagenum", certi_form.getPageId());
				req.setAttribute("focusto", "ac_no");
				if(MenuNameReader.containsKeyScreen(certi_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(certi_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		
		else if(map.getPath().equalsIgnoreCase("/Share/Certificate"))
		{
			try{
			    CertificateForm certi_form=(CertificateForm)form;
				req.setAttribute("pagenum", certi_form.getPageId());
				if(MenuNameReader.containsKeyScreen(certi_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(certi_form.getPageId());
					shDelegate=new ShareDelegate();
					ShareReportObject[] sh_rep=null;
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					if(certi_form.getAc_no().length()!=0)
					{
						int maxnum=shDelegate.checkShareNumber(Integer.parseInt(certi_form.getAc_no()),0);
						if(Integer.parseInt(certi_form.getAc_no())>maxnum)
						{
							req.setAttribute("msg","Account not found");	
						}
						else
						{
							req.setAttribute("msg","");
						    sh_rep=shDelegate.certificate(Integer.parseInt(certi_form.getAc_no()), Integer.parseInt(certi_form.getAc_no()), certi_form.getActype(), 1);
						    if(sh_rep==null)
						    {
						    	req.setAttribute("msg","No Records found");
						    }
						    else
						    {
						    	req.setAttribute("msg","");
						    	req.setAttribute("certificate",sh_rep );
						    }
						}
					}
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		/******************************** End of Share Certificate *********************/
		/******************************** Start of Dividend Notice *********************/
		else if(map.getPath().equalsIgnoreCase("/Share/UnClaimedDividendNoticeMenu"))
		{
			try{
			    UnclaimedNoticeForm div_notice=(UnclaimedNoticeForm)form;
				req.setAttribute("pagenum", div_notice.getPageId());
				if(MenuNameReader.containsKeyScreen(div_notice.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(div_notice.getPageId());
					shDelegate=new ShareDelegate();
					//setDivNoticeParams(req, path, shDelegate);
					 req.setAttribute("pageId",path);
					 req.setAttribute("date", ShareDelegate.getSysDate());
					 req.setAttribute("AcType",shDelegate.getmodcode());
					 req.setAttribute("Template", shDelegate.getTemplates());
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		
		
		else if(map.getPath().equalsIgnoreCase("/Share/UnClaimedDividendNotice"))
		{
			try{
			    UnclaimedNoticeForm div_notice=(UnclaimedNoticeForm)form;
				req.setAttribute("pagenum", div_notice.getPageId());
				if(MenuNameReader.containsKeyScreen(div_notice.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(div_notice.getPageId());
					shDelegate=new ShareDelegate();
					session=req.getSession(true);
					setDivNoticeParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					if(req.getParameter("method")!=null){
					if(req.getParameter("method").equalsIgnoreCase("View"))
					{
						DividendObject[] div_obj=shDelegate.dividend_notice(div_notice.getActype(), div_notice.getNotice_dt());
						List notice_list=new ArrayList();
						for(int i=0;i<div_obj.length;i++){
							Map m=new TreeMap();
							m.put("id", (i+1));
							m.put("Sh_No",div_obj[i].getShnum());
							m.put("Cid", div_obj[i].getCustomerID());
							m.put("Name", div_obj[i].getName());
							m.put("Div_Amt", div_obj[i].getDivAmount());
							m.put("Div_Date", div_obj[i].getDivDate());
							notice_list.add(m);
						}
						session.setAttribute("divnotice",notice_list);
						req.setAttribute("divnotice",notice_list);				
						if(div_obj==null || div_obj.equals("null"))
						{
							req.setAttribute("msg","No Records Found");
						}
						else
						{
							 req.setAttribute("msg","");
							 req.setAttribute("divnotice",notice_list);	
						}
					}
					
					else if(req.getParameter("method").equalsIgnoreCase("Update")){
						req.getSession(true);
						boolean result=shDelegate.updateTemplate(div_notice.getActype(),Integer.parseInt(div_notice.getTemplate()),div_notice.getContent(),shuser,shtml);
					}
					else if(req.getParameter("method").equalsIgnoreCase("Delete")){
						System.out.println("I am in delete------------------------------");
					}
				}
					
					
					if(div_notice.getTemplate()!=null){
						List notice_list=(ArrayList)session.getAttribute("divnotice");
						session.setAttribute("divnotice",notice_list);
						req.setAttribute("divnotice",notice_list);
						String templates=shDelegate.getTemplatesDetails(Integer.parseInt(div_notice.getTemplate()));
						req.setAttribute("data",templates);
					}
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		/******************************** End of Dividend Notice *********************/
		/******************************** Start of Share Admin *********************/
		else if(map.getPath().equalsIgnoreCase("/Share/ShareAdminMenu"))
		{
			try{
			    ShareAdminForm sh_admin=(ShareAdminForm)form;
				req.setAttribute("pagenum", sh_admin.getPageId());
				if(MenuNameReader.containsKeyScreen(sh_admin.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(sh_admin.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					setShareCatList(shDelegate,req);
					setShareParamList(shDelegate,req);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		
		else if(map.getPath().equalsIgnoreCase("/Share/ShareAdmin"))
		{
			try{
			    ShareAdminForm sh_admin=(ShareAdminForm)form;
				req.setAttribute("pagenum", sh_admin.getPageId());
				req.setAttribute("id", req.getParameter("id"));
				req.setAttribute("method", req.getParameter("method"));
				if(MenuNameReader.containsKeyScreen(sh_admin.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(sh_admin.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					List cat_list=(ArrayList)session.getAttribute("AdminList");
					List param_list=(ArrayList)session.getAttribute("param_list");
					String use_id,param_id;
					String method=req.getParameter("method");
					use_id=req.getParameter("id1");
					param_id=req.getParameter("id");
					if(method!=null){
						if(method.equalsIgnoreCase("Save")){
							int r=0;
							Vector vec=new Vector(0,1);
							Object obj[]=new Object[7];
							obj[0]="1001001";
							obj[1]=req.getParameter("Member_cat").toString().trim();
							obj[2]=req.getParameter("Cat_Name").toString().trim();
							obj[3]=req.getParameter("Min_Share").toString().trim();
							obj[4]=req.getParameter("Max_Share").toString().trim();
							obj[5]=req.getParameter("Share_Val").toString().trim();
							obj[6]=req.getParameter("Vote_Power").toString().trim();
							vec.add(obj);
							if(vec.size()>0)
								r=shDelegate.store_admin(vec);
							if(r==1)
								req.setAttribute("msg", "Updated successfully..!");
							else
								req.setAttribute("msg", "Unabel to Update..!");
						}
						else if(method.equalsIgnoreCase("Add")){
							Map m=new TreeMap();
							Map map_ac=(TreeMap)cat_list.get(0);
							String id=""+(cat_list.size()+1);
							m.put("id",id);
							m.put("Sh_type",map_ac.get("Sh_type"));
							m.put("Mem_cat","");
							m.put("Cat_Name","");
							m.put("Min_Share","");
							m.put("Max_Share","");
							m.put("Share_Val","");
							m.put("Vote_Power","");
							cat_list.add(m);
						}
						else if(method.equalsIgnoreCase("Delete")){
							for(int i=0;i<cat_list.size();i++){
								Map m=(TreeMap)cat_list.get(i);
								String id=m.get("id").toString();
								if(use_id.equals(id)){
									cat_list.remove(m); 
									break;
								}
							}
						}
						else if(method.equalsIgnoreCase("Reset")){
							cat_list=setShareCatList(shDelegate, req);
						}
						else if(method.equalsIgnoreCase("ResetP")){
							param_list=setShareParamList(shDelegate, req);
						}
						
						else if(method.equalsIgnoreCase("AddP")){
							Map m=new TreeMap();
							Map map_ac=(TreeMap)cat_list.get(0);
							
							String id=""+(param_list.size()+1);
							 m.put("id",id);
							 m.put("Sh_type",""); 
							 m.put("Mem_cat","");
							 m.put("prm_code","");
							 m.put("prm_desc","");
							 m.put("prm_amt","");
							 m.put("prm_freq","");
							 m.put("prm_type","");
							 m.put("prm_gl_code","");
							 m.put("prm_gl_type","");
							param_list.add(m);
						}
						
						if(method.equalsIgnoreCase("SaveP")){
							int result=0;
							Object obj[][]=new Object[1][9];
							obj[0][0]=req.getParameter("Sh_type").toString().trim();
							obj[0][1]=req.getParameter("Mem_cat").toString().trim();
							obj[0][2]=req.getParameter("prm_code").toString().trim();
							obj[0][3]=req.getParameter("prm_desc").toString().trim();
							obj[0][4]=req.getParameter("prm_amt").toString().trim();
							obj[0][5]=req.getParameter("prm_freq").toString().trim();
							obj[0][6]=req.getParameter("prm_type").toString().trim();
							obj[0][7]=req.getParameter("prm_gl_code").toString().trim();
							obj[0][8]=req.getParameter("prm_gl_type").toString().trim();
							try{
								result=shDelegate.storeShParam(obj);
							}catch(Exception e){
								e.printStackTrace();
							}
							if(result==1)
								req.setAttribute("msg", "Updated Successfully");
							else
								req.setAttribute("msg", "Unable to Update");
							for(int i=0;i<param_list.size();i++){
								Map m=(TreeMap)param_list.get(i);
								String id=m.get("id").toString();
								if(param_id!=null){
									if(param_id.equals(id)){
										m.put("id",req.getParameter("id"));
										m.put("Sh_type",req.getParameter("Sh_type"));
										m.put("Mem_cat",req.getParameter("Mem_cat"));
										m.put("prm_code",req.getParameter("prm_code"));
										m.put("prm_desc",req.getParameter("prm_desc"));
										m.put("prm_amt",req.getParameter("prm_amt"));
										m.put("prm_desc",req.getParameter("prm_desc"));
										m.put("prm_freq",req.getParameter("prm_freq"));
										m.put("prm_type",req.getParameter("prm_type"));
										m.put("prm_gl_code",req.getParameter("prm_gl_code"));
										m.put("prm_gl_type",req.getParameter("prm_gl_type"));
										param_list.set(i,m);
										break;
									}
								}
							}
						}
						
					}
					session.setAttribute("AdminList",cat_list);
				    
					req.setAttribute("AdminList",cat_list);
					
					session.setAttribute("param_list", param_list);
					req.setAttribute("param_list", param_list);
					req.setAttribute("pageId",path);
					if(sh_admin.getForward().equalsIgnoreCase("submit")){
						Object[] obj=new Object[7];
						Vector vec=new Vector();
						if(cat_list!=null){
							Map map_data=null;
							for(int i=0;i<cat_list.size();i++){
								map_data=(TreeMap)cat_list.get(i);
								obj=new Object[7];
								obj[0]=map_data.get("Sh_type");
								obj[1]=map_data.get("Mem_cat");
								obj[2]=map_data.get("Cat_Name");
								obj[3]=map_data.get("Min_Share");
								obj[4]=map_data.get("Max_Share");
								obj[5]=map_data.get("Share_Val");
								obj[6]=map_data.get("Vote_Power");
								vec.add(obj);
							}
							shDelegate.store_admin(vec);
						}
					}
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		/******************************** End of Share Admin *********************/
		/******************************** Start of Share Div Rate ****************/
		else if(map.getPath().equalsIgnoreCase("/Share/ShareDivRateMenu"))
		{
			try{
			    ShareDivrateForm share_div=(ShareDivrateForm)form;
				req.setAttribute("pagenum", share_div.getPageId());
				if(MenuNameReader.containsKeyScreen(share_div.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(share_div.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					setShareDivList(shDelegate, req);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		
		else if(map.getPath().equalsIgnoreCase("/Share/ShareDivRate"))
		{
			try{
			    ShareDivrateForm share_div=(ShareDivrateForm)form;
				req.setAttribute("pagenum", share_div.getPageId());
				if(MenuNameReader.containsKeyScreen(share_div.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(share_div.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					if(share_div.getSelect().equalsIgnoreCase("ShareDivRate"))
					{
						setShareDivList(shDelegate, req);
						req.setAttribute("vis_div", "visible");
						req.setAttribute("vis_dirmaster", "hidden");
						req.setAttribute("vis_dir_rel", "hidden");
						if(req.getParameter("method")!=null){
							req.setAttribute("id", req.getParameter("id"));
							req.setAttribute("method", req.getParameter("method"));
							List cat_list=(ArrayList)session.getAttribute("DivList");
							String use_id;
							String method=req.getParameter("method");
							use_id=req.getParameter("id");
							if(session.getAttribute("DivList")==null)
								cat_list=setShareDivList(shDelegate, req);
							if(method!=null){
								if(method.equalsIgnoreCase("Save")){
									for(int i=0;i<cat_list.size();i++){
										Map m=(TreeMap)cat_list.get(i);
										String id=m.get("id").toString();
										if(use_id!=null){
											if(use_id.equals(id)){
												m.put("id",req.getParameter("id"));
												m.put("frm_dt",m.get("frm_dt"));
												m.put("to_dt",req.getParameter("to_dt"));
												m.put("div_rate",req.getParameter("div_rate"));
												m.put("drf",req.getParameter("drf"));
												m.put("cal_done",req.getParameter("cal_done"));
												m.put("cal_optd",req.getParameter("cal_optd"));
												cat_list.set(i,m);
												break;
											}
										}
									}
								}
								else if(method.equalsIgnoreCase("Add")){
									Map m=new TreeMap();
									String id=""+(cat_list.size()+1);
									m.put("id",id);
									m.put("frm_dt","");
									m.put("to_dt","");
									m.put("div_rate","");
									m.put("drf","");
									m.put("cal_done","");
									m.put("cal_optd","");
									cat_list.add(m);
								}
								else if(method.equalsIgnoreCase("Delete")){
									for(int i=0;i<cat_list.size();i++){
										Map m=(TreeMap)cat_list.get(i);
										String id=m.get("id").toString();
										if(use_id.equals(id)){
											cat_list.remove(m); 
											break;
										}
									}
								}
								else if(method.equalsIgnoreCase("Reset")){
									cat_list=setShareDivList(shDelegate, req);
								}
								
								else if(method.equalsIgnoreCase("submit")){
									if(cat_list!=null){
										Map map_data;
										DividendRateObject[] dro=null;
										dro=new DividendRateObject[cat_list.size()];
										for(int i=0;i<cat_list.size();i++){
											map_data=(TreeMap)cat_list.get(i);
											dro[i]=new DividendRateObject();
											dro[i].setFromDate(map_data.get("frm_dt").toString());
											dro[i].setToDate(map_data.get("to_dt").toString());
											dro[i].setRate(Double.parseDouble(map_data.get("div_rate").toString()));
											dro[i].setAmount(Double.parseDouble(map_data.get("drf").toString()));
											dro[i].setCalDone(map_data.get("cal_done").toString());
											dro[i].setCalopt(map_data.get("cal_optd").toString());
										}
										shDelegate.storeDivRate(dro,0,shuser,shtml);
									}
								}
							}
							req.setAttribute("pageId", path);
						}
					}
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		/******************************** End of Share Div Rate ****************/
		/******************************** Start of Change of Modules ****************/
		else if(map.getPath().equalsIgnoreCase("/Share/ChangeofModulesMenu"))
		{
			try{
			    ChangeofModulesForm ch_mod=(ChangeofModulesForm)form;
				req.setAttribute("pagenum", ch_mod.getPageId());
				if(MenuNameReader.containsKeyScreen(ch_mod.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(ch_mod.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					setModulesParams(shDelegate, req);//for loading the table
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		

		else if(map.getPath().equalsIgnoreCase("/Share/ChangeofModules"))
		{
			try{
			    ChangeofModulesForm ch_mod=(ChangeofModulesForm)form;
				req.setAttribute("pagenum", ch_mod.getPageId());
				List mod_list=(ArrayList)session.getAttribute("chmod");
				if(MenuNameReader.containsKeyScreen(ch_mod.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(ch_mod.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					setModulesParams(shDelegate, req);//for loading the table
					String method=req.getParameter("method");
					String id=req.getParameter("id");
					if(ch_mod.getActype().equalsIgnoreCase("1001001"))
					{
						req.setAttribute("vis", "visible");
					}
					if(method.equalsIgnoreCase("Add"))
					{
						Map module_map=new TreeMap();
						int size=(mod_list.size()+1);
						module_map.put("id",size);
						module_map.put("mod_code","1001001");
					 	module_map.put("mod_desc","Share");
					 	module_map.put("mod_abbr","SH");
					    module_map.put("Lst_ac_no","");
					 	module_map.put("max_R_Count","");
					 	module_map.put("std_inst","");
					 	module_map.put("max_R_days","");
					 	module_map.put("RCTNo","");
					 	module_map.put("Penalty_Rate","");
					 	module_map.put("lst_vou","");
					 	module_map.put("lst_TRF_scroll_no","");
					 	module_map.put("Inspection_Period","");
					 	module_map.put("loan_mod","");
					    mod_list.add(module_map);
					}
					if(method.equalsIgnoreCase("save"))
					{
						mod_obj=new ModuleObject[1];
						mod_obj[0]=new ModuleObject();
						//for(int i=0;i<mod_obj.length;i++){
							mod_obj[0].setModuleCode(req.getParameter("Mod_Code"));
							mod_obj[0].setModuleDesc(req.getParameter("Mod_des"));
							mod_obj[0].setModuleAbbrv(req.getParameter("Mod_abbr"));
							mod_obj[0].setLastAccNo(Integer.parseInt(req.getParameter("LstAccno").toString()));
							//mod_obj[i].setMaxRenewalCount(Integer.parseInt(req.getParameter("LstAccno").toString()));
							mod_obj[0].setStdInst(Integer.parseInt(req.getParameter("std_inst").toString()));
							//mod_obj[i].setLastVoucherScrollno(Integer.parseInt(req.getParameter("").toString()));
							//mod_obj[i].setTopmargin(Integer.parseInt(table.getValueAt(i,31).toString().trim()));
							mod_obj[0].setLastTRFScrollno(Integer.parseInt(req.getParameter("lstTrfScrNo").toString()));
							mod_obj[0].setRCTNo(Integer.parseInt(req.getParameter("renewalInt").toString()));
							mod_obj[0].setLastVoucherNo(Integer.parseInt(req.getParameter("lstVoucher").toString()));
							mod_obj[0].setInspectionPeriod(Integer.parseInt(req.getParameter("insPeriod").toString()));
							mod_obj[0].setLoanModuleCode(req.getParameter("Loan").toString());
					//	}
						shDelegate.insertdeletemodule(mod_obj);
					}
					if(ch_mod.getForward().equalsIgnoreCase("Delete"))
					{
						System.out.println("The button is"+ch_mod.getForward());
					}
					if(ch_mod.getForward().equalsIgnoreCase("Clear"))
					{
						req.setAttribute("vis", "visible");
					}
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		/******************************** End of Change of Modules ****************/
		/******************************** Start of Dividend Payment ****************/
		else if(map.getPath().equalsIgnoreCase("/Share/DividendPaymentMenu"))
		{
			try{
			    DividendPaymentForm div_pay_form=(DividendPaymentForm)form;
				req.setAttribute("pagenum", div_pay_form.getPageId());
				if(MenuNameReader.containsKeyScreen(div_pay_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(div_pay_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					div_pay_form.setVoucher_no("");
					div_pay_form.setFlag("");
					div_pay_form.setAc_no("");
					req.setAttribute("pageId",path);
					div_pay_form.setFrm_dt(shDelegate.getSysDate());
					div_pay_form.setTo_dt(shDelegate.getSysDate());
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		else if(map.getPath().equalsIgnoreCase("/Share/DividendPayment"))
		{
			try{
			    DividendPaymentForm div_pay_form=(DividendPaymentForm)form;
				req.setAttribute("pagenum", div_pay_form.getPageId());
				if(MenuNameReader.containsKeyScreen(div_pay_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(div_pay_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					if(div_pay_form.getVoucher_no().length()>0 && !(div_pay_form.getVoucher_no().equals(" ")) && !(div_pay_form.getFlag().equalsIgnoreCase("Verify"))){
						try{
							DividendObject[] div_obj_verify=null;
							div_obj_verify=shDelegate.getDividend(Integer.parseInt(div_pay_form.getVoucher_no()), div_pay_form.getActype());
							session=req.getSession(true);
							session.setAttribute("verifyDivObj",div_obj_verify);
							int k=0,id=0;
							if(div_obj_verify!=null && div_obj_verify.length>0){
								for(int i=0;i<div_obj_verify.length;i++){
									k++;
								}
								List div_list=new ArrayList();
								k=0;
								for(int i=0;i<div_obj_verify.length;i++){
									Map div_map=new TreeMap();
									div_map.put("id",""+(id+1));
									div_map.put("Sh_no", div_obj_verify[k].getSHNumber());
									div_map.put("Shtype", div_obj_verify[k].getSHType());
									div_map.put("paymode", div_obj_verify[k].getPayMode());
									div_map.put("divdate", div_obj_verify[k].getDivDate());
									div_map.put("divamt", div_obj_verify[k].getDivAmount());
									div_map.put("drfamt", div_obj_verify[k].getDrfAmount());
									div_map.put("cvnum", div_obj_verify[k].getCvNumber());
									div_map.put("voucno", div_obj_verify[k].getVoucherNo());
									div_list.add(div_map);
									k++;
								}
								req.setAttribute("div_payment",div_list);
								div_pay_form.setForward("Trans");
								if(div_obj_verify.length>1)
								{
								div_pay_form.setFrm_dt(div_obj_verify[0].getDivDate());
								div_pay_form.setTo_dt(div_obj_verify[div_obj_verify.length-1].getDivDate());
								}else{
									div_pay_form.setFrm_dt(div_obj_verify[0].getDivDate());
									div_pay_form.setTo_dt(div_obj_verify[0].getDivDate());
								}
								if(div_obj_verify[0].getPayMode().equalsIgnoreCase("T")){
								if(div_obj_verify[0].getDivAccType().equalsIgnoreCase("1002001"))
								    div_pay_form.setCashAcType("SB");
								else if(div_obj_verify[0].getDivAccType().equalsIgnoreCase("1007001"))
								    div_pay_form.setCashAcType("CA");
								else if(div_obj_verify[0].getDivAccType().equalsIgnoreCase("1014001"))
								    div_pay_form.setCashAcType("CC");
								else if(div_obj_verify[0].getDivAccType().equalsIgnoreCase("1015001"))
								    div_pay_form.setCashAcType("OD");
								}
								if(div_obj_verify[0].getPayMode().equalsIgnoreCase("T"))
									div_pay_form.setCashPayMode("Transfer");
								else if(div_obj_verify[0].getPayMode().equalsIgnoreCase("C"))
									div_pay_form.setCashPayMode("Pay By Cash");
								else
									div_pay_form.setCashPayMode("PayOrder");
								div_pay_form.setAc_no(String.valueOf(div_obj_verify[0].getSHNumber()));
								div_pay_form.setCashAcno(String.valueOf(div_obj_verify[0].getDivAccNo()));
								req.setAttribute("DividendVerify", "DividendVerify");
								}else{
									req.setAttribute("msg", "Voucher Number Already Verified");
								}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					if(div_pay_form.getFlag().equalsIgnoreCase("Verify")){
						DividendObject update_div_share[]=null;
						DividendObject verify_div_share[]=null;
						session=req.getSession(true);
						verify_div_share=(DividendObject[])session.getAttribute("verifyDivObj");
						update_div_share=new DividendObject[verify_div_share.length];
						int vouch_no=0;
			        	String acct_type=null;
			        	vouch_no=Integer.parseInt(div_pay_form.getVoucher_no().trim());
			        	if(div_pay_form.getCashPayMode().toString().equals("Pay By Cash"))
						{
							int k=0;
							for(int i=0;i<verify_div_share.length;i++)
							{
									update_div_share[k]=new DividendObject();
									update_div_share[k].setSHType(verify_div_share[i].getSHType().toString().trim());
									update_div_share[k].setSHNumber(verify_div_share[i].getSHNumber());
									update_div_share[k].setPayMode(verify_div_share[i].getPayMode().toString().trim());
									update_div_share[k].setDivDate(verify_div_share[i].getDivDate().toString().trim());
									update_div_share[k].setDivAmount(verify_div_share[i].getDivAmount());
									update_div_share[k].setDrfAmount(Float.parseFloat(String.valueOf(verify_div_share[i].getDrfAmount())));
									update_div_share[k].setPaymentType("C");
									k++;
							}
						}
						else if(div_pay_form.getCashPayMode().toString().equals("Transfer"))
						{         
							int k=0;
							for(int i=0;i<verify_div_share.length;i++)
							{
								update_div_share[k]=new DividendObject();
								update_div_share[k].setSHType(verify_div_share[i].getSHType().toString().trim());
								update_div_share[k].setSHNumber(verify_div_share[i].getSHNumber());
								update_div_share[k].setPayMode(verify_div_share[i].getPayMode().toString().trim());
								update_div_share[k].setDivDate(verify_div_share[i].getDivDate().toString().trim());
								update_div_share[k].setDivAmount(verify_div_share[i].getDivAmount());
								update_div_share[k].setDrfAmount(Float.parseFloat(String.valueOf(verify_div_share[i].getDrfAmount())));
									update_div_share[k].setDivAccType(div_pay_form.getCashAcType().toString().trim());
									update_div_share[k].setDivAccNo(Integer.parseInt(div_pay_form.getCashAcno().trim()));
									update_div_share[k].setPaymentType("T");
									DividendObject[] getdeDetails=null;
									k++;
							}
						}
						else if(div_pay_form.getCashPayMode().toString().equals("PayOrder"))
						{
							int k=0;
							for(int i=0;i<verify_div_share.length;i++)
							{
								update_div_share[k]=new DividendObject();
								update_div_share[k].setSHType(verify_div_share[i].getSHType().toString().trim());
								update_div_share[k].setSHNumber(verify_div_share[i].getSHNumber());
								update_div_share[k].setPayMode(verify_div_share[i].getPayMode().toString().trim());
								update_div_share[k].setDivDate(verify_div_share[i].getDivDate().toString().trim());
								update_div_share[k].setDivAmount(verify_div_share[i].getDivAmount());
								update_div_share[k].setDrfAmount(Float.parseFloat(String.valueOf(verify_div_share[i].getDrfAmount())));
									update_div_share[k].setPaymentType("PO");
									k++;
							}
						}	
						try
						{
							int result=shDelegate.verifyDividend(update_div_share,vouch_no,shuser,shtml,shDelegate.getSysDateTime(),shDelegate.getSysDate());
							if(result==1)
							{
								req.setAttribute("msg","VoucherNumber "+vouch_no+" is Verified Successfully");
							}
							else
							{
								req.setAttribute("msg","Unable to Verify");
							}
						}catch(Exception ex){
							ex.printStackTrace();
							}
					}
					
					if(req.getParameter("method")!=null){
					if(req.getParameter("method").equalsIgnoreCase("PayByCash"))
					{
						req.setAttribute("CashPanel", "ShowPanel");
					}
					}
					if(div_pay_form.getFlag().equalsIgnoreCase("Transfer"))
					{
						/*try{
						shDelegate.main_transfer(div_pay_form.getActype(), shtml, shuser, ShareDelegate.getSysDate(), ShareDelegate.getSysDate());
						}catch(Exception e){
							e.printStackTrace();
						}*/
					}
					if(div_pay_form.getFlag().equalsIgnoreCase("accountNumber"))
					{
						if(div_pay_form.getCashAcno().equalsIgnoreCase("")||div_pay_form.getCashAcno().equalsIgnoreCase("0"))
							req.setAttribute("msg", "Enter Valid Account Number");
						else if(div_pay_form.getCashAcno().trim().length()>0 )
						{
						    try
						    {
						        AccountObject accountobject=shDelegate.getIntrodet(div_pay_form.getCashAcType(),Integer.parseInt(div_pay_form.getCashAcno()));
								if(accountobject==null)
								{
									req.setAttribute("msg","Given account number not found");
									div_pay_form.setCashAcno("");
								}
								else if(accountobject.getAccStatus().equalsIgnoreCase("C")){
									req.setAttribute("msg","Given account number is closed");
								}
								else
								{
									req.setAttribute("DividendCustomerName",accountobject.getAccname());
								}
						    }catch(RemoteException exception){exception.printStackTrace();}
						}
					}
					String method=req.getParameter("method");
					if((div_pay_form.getFlag().equalsIgnoreCase("View") || div_pay_form.getFlag().equalsIgnoreCase("accountNumber")) && div_pay_form.getVoucher_no().length()==0)
					{
						DividendObject[] div_obj=null;
						List div_list=new ArrayList();
						try{
						div_obj=shDelegate.retrivedivpayment(Validations.convertYMD(div_pay_form.getFrm_dt()), Validations.convertYMD(div_pay_form.getTo_dt()), Integer.parseInt(div_pay_form.getAcnum()), div_pay_form.getActype());
						}catch(Exception e){
							e.printStackTrace();
						}
						int id=0;
						if(div_obj!=null){
						for(int i=0;i<div_obj.length;i++){
							Map div_map=new TreeMap();
							div_map.put("id",""+(id+1));
							div_map.put("Sh_no", div_obj[i].getShnum());
							div_map.put("Shtype", div_obj[i].getSHType());
							div_map.put("paymode", div_obj[i].getPayMode());
							div_map.put("divdate", div_obj[i].getDivDate());
							div_map.put("divamt", div_obj[i].getDivAmount());
							div_map.put("drfamt", div_obj[i].getDrfAmount());
							div_map.put("cvnum", div_obj[i].getCvNumber());
							div_map.put("voucno", div_obj[i].getVoucherNo());
							div_list.add(div_map);
						}
						session=req.getSession(true);
						session.setAttribute("div_payment",div_list);
						req.setAttribute("div_payment",div_list);
						req.setAttribute("DividendPay","DividendPay");
						}else{
							req.setAttribute("msg", "Records Not Found");
						}
					}
					if(method!=null){
					if(method.equalsIgnoreCase("Pay")){
						DividendObject[] div_obj=null;
						String[] ar=req.getParameterValues("id");
						for(int i=0;i<ar.length;i++)
							System.out.println("Vlues of id in dividend payment are------>"+ar[i]);
						div_obj=new DividendObject[ar.length];
						session=req.getSession(true);
						List lst=(ArrayList)session.getAttribute("div_payment");
						TreeMap tm=null;
						int k=0;
						if(div_pay_form.getCashPayMode().equalsIgnoreCase("Pay By Cash")){
						for(int l=0;l<ar.length;l++){
							tm=(TreeMap)lst.get(l);
							    if(ar[l].equals(tm.get("id").toString())){
								div_obj[k]=new DividendObject();
								div_obj[k].setSHType(tm.get("Shtype").toString().trim());
								div_obj[k].setSHNumber(Integer.parseInt(tm.get("Sh_no").toString().trim()));
								div_obj[k].setDivDate(tm.get("divdate").toString().trim());
								div_obj[k].setDivAmount(Double.parseDouble(tm.get("divamt").toString().trim()));
								div_obj[k].setDrfAmount(Float.parseFloat(tm.get("drfamt").toString().trim()));
								k++;
								if(Double.parseDouble(tm.get("divamt").toString().trim())<(Float.parseFloat(tm.get("drfamt").toString().trim()))) {
						         req.setAttribute("msg","Dividend Amount is less than DRF amount");
							    }
						}
						}
						int t;	
						
						try {
							t=shDelegate.cash(div_obj,shtml,shuser,shDelegate.getSysDateTime(),shDelegate.getSysDate());
							if(t==0)
							{
								req.setAttribute("msg","No Voucher Number Generated");
							}
							else
							{
								req.setAttribute("msg","Voucher No generated: "+t);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						} 
						}else if(div_pay_form.getCashPayMode().equalsIgnoreCase("Transfer")){
							for(int l=0;l<ar.length;l++){
								tm=(TreeMap)lst.get(l);
								    if(ar[l].equals(tm.get("id").toString())){
									div_obj[k]=new DividendObject();
									div_obj[k].setSHType(tm.get("Shtype").toString().trim());
									div_obj[k].setSHNumber(Integer.parseInt(tm.get("Sh_no").toString().trim()));
									div_obj[k].setDivDate(tm.get("divdate").toString().trim());
									div_obj[k].setDivAmount(Double.parseDouble(tm.get("divamt").toString().trim()));
									div_obj[k].setDrfAmount(Float.parseFloat(tm.get("drfamt").toString().trim()));
									div_obj[k].setDivAccType(div_pay_form.getCashAcType());
									div_obj[k].setDivAccNo(Integer.parseInt(div_pay_form.getCashAcno().trim()));
									div_obj[k].setPayMode(div_pay_form.getCashPayMode().toString());
									k++;
							}
							}
							try {
								int t;
								t=shDelegate.transfer(div_obj,shtml,shuser,shDelegate.getSysDateTime(),0,shDelegate.getSysDate());
								if(t>0)
								{
									req.setAttribute("msg","Dividend Transfered to A/C "+div_pay_form.getCashAcno()+"  VoucherNumber generated"+t);
								}
								else if(t==0)
								{
									req.setAttribute("msg","No Unclaimed Dividend for this A/C");
								}
							} catch (Exception e1) {
								e1.printStackTrace();
							} 
						}else if(div_pay_form.getCashPayMode().equalsIgnoreCase("PayOrder")){
							for(int l=0;l<ar.length;l++){
								tm=(TreeMap)lst.get(l);
								    if(ar[l].equals(tm.get("id").toString())){
									div_obj[k]=new DividendObject();
									div_obj[k].setSHType(tm.get("Shtype").toString().trim());
									div_obj[k].setSHNumber(Integer.parseInt(tm.get("Sh_no").toString().trim()));
									div_obj[k].setDivDate(tm.get("divdate").toString().trim());
									div_obj[k].setDivAmount(Double.parseDouble(tm.get("divamt").toString().trim()));
									div_obj[k].setDrfAmount(Float.parseFloat(tm.get("drfamt").toString().trim()));
									k++;
							}
							}
							int t;	
							try {
								t=shDelegate.payOrder(div_obj,shtml,shuser,shDelegate.getSysDateTime(),0,shDelegate.getSysDate());
								if(t==0)
								{
									req.setAttribute("msg","No Voucher Number Generated");
								}
								else
								{
									req.setAttribute("msg","Voucher No generated: "+t);
								}
							} catch (Exception e1) {
								e1.printStackTrace();
							} 
						}
					}
				}
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		/******************************** End of Dividend Payment ****************/
		/********************************* Nominee ******************************/
		else if(map.getPath().equalsIgnoreCase("/Share/NomineeMenu"))
		{	
			try{
			    NomineeForm nom_form=(NomineeForm)form;
				req.setAttribute("pagenum", nom_form.getPageId());
				if(MenuNameReader.containsKeyScreen(nom_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(nom_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		else if(map.getPath().equalsIgnoreCase("/Share/Nominee"))
		{
			try{
			    NomineeForm nom_form=(NomineeForm)form;
				req.setAttribute("pagenum", nom_form.getPageId());
				if(MenuNameReader.containsKeyScreen(nom_form.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(nom_form.getPageId());
					shDelegate=new ShareDelegate();
					setShOpeningInitParams(req, path, shDelegate);
					req.setAttribute("pageId",path);
					req.setAttribute("msg","");
					if(nom_form.getHas_ac()!=null){
						req.setAttribute("vis", "visible");
						if(nom_form.getCid().length()>0){
							CustomerMasterObject cust_obj=shDelegate.getCustomer(Integer.parseInt(nom_form.getCid()));
							req.setAttribute("custdetails",cust_obj);
						}
					}
					 if(nom_form.getForward().equalsIgnoreCase("submit")){
                        req.setAttribute("ShareAcNo", nom_form.getAcno().toString());
						NomineeObject[] nom=new NomineeObject[1];
						nom[0]=new NomineeObject();
						nom[0].setRegNo(0);
						nom[0].setAccNo(Integer.parseInt(nom_form.getAcno()));
						nom[0].setAccType(nom_form.getActype());
						if(nom_form.getCid().length()>0){
						nom[0].setCustomerId(Integer.parseInt(nom_form.getCid()));
						}
						else{
							nom[0].setCustomerId(0);
						}
						nom[0].setNomineeName(nom_form.getName());
						nom[0].setNomineeDOB(nom_form.getDob());
						nom[0].setNomineeAddress(nom_form.getAddress());
						nom[0].setNomineeRelation(nom_form.getRel_ship());
						nom[0].setPercentage(Double.parseDouble(nom_form.getPercentage()));
						nom[0].setFromDate(nom_form.getIssuedate());
						shDelegate.Nominee(nom);
						int percent=shDelegate.getnomineePercentage(Integer.parseInt(nom_form.getAcno()),nom_form.getActype());
						if(percent<100){
						    req.setAttribute("msg","The percentage is less than 100. Add another nominee");
						}
						else if(percent==100){
							req.setAttribute("msg","Nominee added successfully");
							req.setAttribute("pagenum",4001);
							   if(MenuNameReader.containsKeyScreen(String.valueOf(4001)))
								{
									path=MenuNameReader.getScreenProperties(String.valueOf(4001));
									shDelegate=new ShareDelegate();
									req.setAttribute("pageId",path);
									setShOpeningInitParams(req, path, shDelegate);
								}
						}
					}
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}
			catch(Exception e)
			{
				path=MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		return map.findForward(ResultHelp.getSuccess());
		}
  
  private void setErrorPageElements(String exception,HttpServletRequest req,String path)
  {
	  req.setAttribute("exception",exception);
	  req.setAttribute("pageId",path);
  }

  private void getTabbedpane(HttpServletRequest req,PassBookForm pass)
  {
	  String pageActions[]={"/Share/PassBook?tabPaneHeading=Personal&pageId="+pass.getPageId()};
	  String tabheading[]={"Personal"};
	  String pagepath[]={MenuNameReader.getScreenProperties("0001").trim()};
	  req.setAttribute("TabHeading", tabheading);
	  req.setAttribute("PageActions", pageActions);
	  req.setAttribute("PagePath", pagepath);
	  req.setAttribute("pagenum", "3003");
  }

 private void getTabbedpaneAllotment(HttpServletRequest req, AllotmentForm allot)
 {
	 String pageActions[]={"/Share/Allotment?tabPaneHeading=Personal&pageId="+allot.getPageId(),"/Share/Allotment?tabPaneHeading=Introducer Type&pageid="+allot.getPageId()};
	 String tabheading[]={"Personal","Introducer Type"};
	 String pagepath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0001").trim()};
	 req.setAttribute("TabHeading", tabheading);
	 req.setAttribute("PageActions", pageActions);
	 req.setAttribute("PagePath", pagepath);
	 req.setAttribute("pagenum", "3003");
 }
	 
 private void getTabbedpane1(HttpServletRequest req,AddAllotmentForm add_form)
 {
	 String pageActions[]={"/Share/AdditionalAllotment?tabPaneHeading=Personal&pageId="+add_form.getPageId()};
	 String tabheading[]={"Personal"};
	 String pagepath[]={MenuNameReader.getScreenProperties("0001").trim()};
	 req.setAttribute("TabHeading", tabheading);
	 req.setAttribute("PageActions", pageActions);
	 req.setAttribute("PagePath", pagepath);
	 req.setAttribute("pagenum", "3003");
 }
	 
 private void getWithdrawTabs(HttpServletRequest req,WithdrawalForm with_form)
 {
	 String pageActions[]={"/Share/Withdrawal?tabPaneHeading=Personal&pageId="+with_form.getPageId()};
	 String tabheading[]={"Personal"};
	 String pagepath[]={MenuNameReader.getScreenProperties("0001").trim()};
	 req.setAttribute("TabHeading", tabheading);
	 req.setAttribute("PageActions", pageActions);
	 req.setAttribute("PagePath", pagepath);
	 req.setAttribute("pagenum", "3003");
 }
 
 private void setDivNoticeParams(HttpServletRequest req,String path,ShareDelegate shDelegate)throws Exception
 {
	 req.setAttribute("pageId",path);
	 req.setAttribute("date", ShareDelegate.getSysDate());
	 req.setAttribute("AcType",shDelegate.getmodcode());
	 req.setAttribute("Template", shDelegate.getTemplates());
 }
	 
 private void setShOpeningInitParams(HttpServletRequest req,String path,ShareDelegate shDelegate)throws Exception
 {
	 try{
		 req.setAttribute("pageId",path);
		 req.setAttribute("date", ShareDelegate.getSysDate());
		 req.setAttribute("AcType",shDelegate.getmodcode());
		 req.setAttribute("ShareType",shDelegate.getShareType());
		 req.setAttribute("ShareCat", shDelegate.getShareCategory()); //commented by amzad on 08/11/2009
		 req.setAttribute("Branch", shDelegate.getBranchcode());
		 req.setAttribute("Branch1", shDelegate.getBranchcode1());
		 req.setAttribute("Divpaymode", shDelegate.getDivpaymode());
		 req.setAttribute("AcTypes", shDelegate.getAcTypes());
		 req.setAttribute("Paymode", shDelegate.getpaymode());
		 req.setAttribute("Details", shDelegate.getdetails());
		 req.setAttribute("branch", admDelegate.getBranchMaster());
		 //req.setAttribute("AllAc_types", shDelegate.getAllAc_types());
		 req.setAttribute("WithdrawalTypes", shDelegate.WithdrawalTypes());
		 req.setAttribute("WithDetails", shDelegate.getwithdetails());
		 req.setAttribute("Disable",false);
		 req.setAttribute("Butt_disable", false);
		 // req.setAttribute("Disable1",false);
		 req.setAttribute("Disable2", true);
		 req.setAttribute("Disable3", true);
		 req.setAttribute("vis", "hidden");
		 req.setAttribute("vistable", "hidden");
		 req.setAttribute("div_reg",shDelegate.getDivregtypes());
		 req.setAttribute("div_types", shDelegate.getDivregoptions());
		 req.setAttribute("disablefield",true);
		 req.setAttribute("color", "yellow");
		 req.setAttribute("ShareCategory", shDelegate.storeSharetypes());
		 req.setAttribute("ShareParam", shDelegate.storeShareParams());
		 //req.setAttribute("Template", shDelegate.getTemplates());
		 req.setAttribute("AllAc_types",shDelegate.getAllAc_types());
		 req.setAttribute("Tables", shDelegate.getTables());
		 //req.setAttribute("vis_div", "hidden");
		 req.setAttribute("vis_dirmaster", "hidden");
		 req.setAttribute("vis_dir_rel", "hidden");
		 //req.setAttribute("Sharedivrates", shDelegate.getDivrates());
	 }catch(Exception e){
		 throw e;
	 }
 }
 
 private List setShareCatList(ShareDelegate shDelegate,HttpServletRequest req)throws Exception
 {
	 ShareCategoryObject shcat[]=shDelegate.storeSharetypes();//get the array
	 List cat_list=new ArrayList();//create a list
	 for(int i=0;i<shcat.length;i++){
		 Map map=new TreeMap();// create a map
		 map.put("id",""+(i+1));
		 map.put("Sh_type",shcat[i].getShareType()); 
		 map.put("Mem_cat",shcat[i].getShCat());
		 map.put("Cat_Name",shcat[i].getCatName());
		 map.put("Min_Share",shcat[i].getMinShare());
		 map.put("Max_Share",shcat[i].getMaxShare());
		 map.put("Share_Val",shcat[i].getShareVal());
		 map.put("Vote_Power",shcat[i].getVote());
		 cat_list.add(map);// add map to the list
	 }
	 session=req.getSession(true);
	 session.setAttribute("AdminList", cat_list);//to keep the last data
	 req.setAttribute("AdminList", cat_list);//send the data to the client
	 return cat_list;
 }

 private List setShareParamList(ShareDelegate shDelegate,HttpServletRequest req)throws Exception
 {
	 ShareParamObject sh_param[]=shDelegate.storeShareParams();//get the array
	 List param_list=new ArrayList();//create a list
	 for(int i=0;i<sh_param.length;i++){
		 Map map=new TreeMap();// create a map
		 map.put("id",""+(i+1));
		 map.put("Sh_type",sh_param[i].getShareType()); 
		 map.put("Mem_cat",sh_param[i].getSh_cat());
		 map.put("prm_code",sh_param[i].getPrm_code());
		 map.put("prm_desc",sh_param[i].getPrm_desc());
		 map.put("prm_amt",sh_param[i].getPrm_amt());
		 map.put("prm_freq",sh_param[i].getPrm_freq());
		 map.put("prm_type",sh_param[i].getPrm_ty());
		 map.put("prm_gl_code", sh_param[i].getPrm_gl_key());
		 map.put("prm_gl_type", sh_param[i].getPrm_gl_type());
		 param_list.add(map);// add map to the list
	 }
	 session=req.getSession(true);
	 session.setAttribute("param_list", param_list);//to keep the last data
	 req.setAttribute("param_list", param_list);//send the data to the client
	 return param_list;
 }
 
 private List setShareDivList(ShareDelegate shDelegate,HttpServletRequest req)throws Exception
 {
	 DividendRateObject[] div_rate= shDelegate.getDivrates();
	 List cat_list=new ArrayList();
	 for(int i=0;i<div_rate.length;i++){
		 Map map=new TreeMap();
		 map.put("id",""+(i+1));
		 map.put("frm_dt",div_rate[i].getFromDate());
		 map.put("to_dt",div_rate[i].getToDate());
		 map.put("div_rate",div_rate[i].getRate());
		 map.put("drf",div_rate[i].getAmount());
		 map.put("cal_done",div_rate[i].getCalDone());
		 map.put("cal_optd",div_rate[i].getCalopt());
		 cat_list.add(map);
	 }
	 session=req.getSession(true);
	 session.setAttribute("DivList", cat_list);
	 req.setAttribute("DivList",cat_list );
	 return cat_list;
 }
 
 private List setConvertToPerm(ShareDelegate shdelegate,HttpServletRequest req,String frm,String to,int type )throws RemoteException{
   ShareMasterObject[] shobj=shdelegate.converttemp_perm_date(frm,to,type);
   List convert_list=new ArrayList();
   for(int i=0;i<shobj.length;i++){
	   Map convert_map=new TreeMap();
	   convert_map.put("id",(i+1));
	   convert_map.put("cid", shobj[i].getCustomerId());
	   convert_map.put("ShareNo", shobj[i].getTempShareNumber());
	   convert_map.put("Ac_no", shobj[i].getShareNumber());
	   convert_map.put("Name", shobj[i].getName());
	   convert_map.put("NumofShare", shobj[i].getNumberofShares());
	   convert_map.put("ShareValue", shobj[i].getShareVal()*shobj[i].getNumberofShares());
	   convert_map.put("BranchCode", shobj[i].getBranchCode());
	   convert_map.put("ShCat", shobj[i].getMemberCategory());
	   convert_map.put("IssueDate", shobj[i].getIssueDate());
	   convert_list.add(convert_map);
   }
   session=req.getSession(true);
   session.setAttribute("tempperm",convert_list);
   req.setAttribute("tempperm", convert_list);
   req.setAttribute("idvalue", shobj);
   req.setAttribute("visrow", "hidden");
   return convert_list;
 }
 
 private List setModulesParams(ShareDelegate shdelegate,HttpServletRequest req)throws RemoteException{
	    ModuleObject mod_obj=shdelegate.change_of_module("1001001");
	 	List module_list=new ArrayList();
	 	Map module_map=new TreeMap();
	 	module_map.put("id",1);
	 	module_map.put("mod_code",mod_obj.getModuleCode());
	 	module_map.put("mod_desc",mod_obj.getModuleDesc());
	 	module_map.put("mod_abbr",mod_obj.getModuleAbbrv());
	 	module_map.put("Lst_ac_no",mod_obj.getLastAccNo());
	 	module_map.put("max_R_Count",mod_obj.getMaxRenewalCount());
	 	module_map.put("std_inst",mod_obj.getStdInst());
	 	module_map.put("max_R_days",mod_obj.getMaxRenewalDays());
	 	module_map.put("RCTNo",mod_obj.getRCTNo());
	 	module_map.put("Penalty_Rate",mod_obj.getPenaltyRate());
	 	module_map.put("lst_vou",mod_obj.getLastVoucherNo());
	 	module_map.put("lst_TRF_scroll_no",mod_obj.getLastTRFScrollno());
	 	module_map.put("Inspection_Period",mod_obj.getInspectionPeriod());
	 	module_map.put("loan_mod",mod_obj.getLoanModuleCode());
	 	module_list.add(module_map);
	 	session=req.getSession(true);
	    session.setAttribute("chmod",module_list);
	    req.setAttribute("chmod",module_list);
	 	return module_list;
 }
}