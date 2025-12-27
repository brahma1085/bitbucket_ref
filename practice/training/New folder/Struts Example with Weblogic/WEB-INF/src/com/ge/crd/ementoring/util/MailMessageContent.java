package com.ge.crd.ementoring.util;
import com.ge.crd.ementoring.util.Mentor.MailService;
public class MailMessageContent {

     private String mailHost = "3.159.17.48";
	 private String frmAddress = "ementoring.jfwtc@ge.com";
	 private String MentorApprovalSubject = "Request for Mentor Approval";
	 
	 
	 public void sendMailToSelectedMentor(String mentorName,String menteeName,String mentorssoid) {	 
		StringBuffer sBuffer = null;
		try {
			sBuffer = new StringBuffer("");
			sBuffer.append("<font face='GE Inspira' size='3' color='731473'>Hi "+mentorName+"<br><br>It gives us great pleasure to inform you that" +
					" "+menteeName+" has requested you as a Mentor.<br><br>You are required to either accept or decline this request for Mentorship.<br>In both cases we recommend that you personally meet or call the individual to inform him/her of your final decision regarding entering into the proposed Mentoring partnership.<br><br>Please follow the below steps to approve or decline this request - <br><br><b>" +
					" 1. Click on the link <a href='http://genet-ssostage.jfwtc.ge.com/ementoring/' target='_new'>http://genet-ssostage.jfwtc.ge.com/ementorting/</a> and login<br> " +
					" 2. Click on the link Mentor Section.<br> " +
					" 3. Click on the link Pending Request.<br>"+
					" 4. Click on Approve/Decline button in &#34;Pending Mentoring Request&#34; to view Mentee's profile.</b><br><br>For any support, do get in touch with your HRM or Business Mentoring Contact.<br><br>Thanks for your continued support & engagement in the JFWTC Power Mentoring Program. " +
					" Best Regards,<br> The Power Mentoring Team </font> ");
			
			MailService oMail = new MailService();
	        //oMail.doSendMail(mailHost, frmAddress, mentorssoid+"@mail.ad.ge.com", MentorApprovalSubject, sBuffer.toString());
	        oMail.doSendMail(mailHost, frmAddress, 501305807+"@mail.ad.ge.com", MentorApprovalSubject, sBuffer.toString());
		} catch(Exception e) {
			System.out.println("Mail function Error : " + e);
		}	
	 }	
	}

