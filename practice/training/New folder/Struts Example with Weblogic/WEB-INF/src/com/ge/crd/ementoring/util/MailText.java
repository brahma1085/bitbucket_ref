package com.ge.crd.ementoring.util;
import com.ge.crd.ementoring.util.Mentor.MailService;

public class MailText {

 private String mailHost = "3.159.17.48";
 private String frmAddress = "ementoring.jfwtc@ge.com";
 private String MentorApprovalSubject = "Request for Mentor Approval";
 
 
 public void sendMailToSelectedMentor(String mentorName,String menteeName,String mentorssoid) {	 
	StringBuffer sBuffer = null;
	try {
		sBuffer = new StringBuffer("");
		sBuffer.append("<font face='GE Inspira' size='3' color='731473'>Dear "+mentorName+"<br><br> " +
				" "+menteeName+" has selected you as a mentor.<br>Follow the below steps to Approve/Reject the mentoring request.<br><br><b>" +
				" 1. Click on the URL <a href='http://genet-ssostage.jfwtc.ge.com/ementoring/' target='_new'>http://genet-ssostage.jfwtc.ge.com/ementorting/</a><br> " +
				" 2. Select Mentor Section on the left navigation bar.<br> " +
				" 3. Select Pending Mentroing Requests.<br>"+
				" 4. Approve/Reject the request.</b><br><br> " +
				" Thank you,<br> eMentoring </font> ");
		
		MailService oMail = new MailService();
        //oMail.doSendMail(mailHost, frmAddress, mentorssoid+"@mail.ad.ge.com", MentorApprovalSubject, sBuffer.toString());
        oMail.doSendMail(mailHost, frmAddress, 501305807+"@mail.ad.ge.com", MentorApprovalSubject, sBuffer.toString());
	} catch(Exception e) {
		System.out.println("Mail function Error : " + e);
	}	
 }	
}
