package com.ge.crd.ementoring.util.Mentor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.sql.DataSource;

public class MailText {

	private String mailHost;// = "3.159.17.48";
	private String frmAddress;// = "ementoring.jfwtc@ge.com";
	private String nominatedMentorSubject;// = "Congratulations on being a Power Mentor!";
	private String approvalSubject;// ="Pleased to be your Mentor"; 
	private String declineSubject;// = "Mentoring Request Declined";
	private String sunsetRSubject;// = "Sunset - Power Mentoring Relationship";
	private String menteeMailSubject;// = "Mentoring Request";
	private String mentorMailSubject;// = "Mentoring Request by ";
	private String applicationURL;// = "http://genet-ssostage.jfwtc.ge.com/ementoring/";
	 	
  public MailText() {	
	  ResourceBundle rb = ResourceBundle.getBundle("Mail");
	  mailHost   		= rb.getString("mailHost");
	  frmAddress 		= rb.getString("frmAddress");
	  nominatedMentorSubject = rb.getString("nominatedMentorSubject");
	  approvalSubject   = rb.getString("approvalSubject");
	  declineSubject    = rb.getString("declineSubject");
	  sunsetRSubject    = rb.getString("sunsetRSubject");
	  menteeMailSubject = rb.getString("menteeMailSubject");
	  mentorMailSubject = rb.getString("mentorMailSubject");
	  applicationURL    = rb.getString("applicationURL");	  
  }
  
 public void sendMailToNominatedMentor(String mentorName,String mentorssoid,String hrssoid) {
 // Purpose of mail : Notifies the Mentor, that the request is submitted - 
 //	notifes the employee that he has been nominated as a mentor.
 // From Email - HR who nominate the mentor(s).
 // To Email   - Nominated mentor.
	 
	StringBuffer sBuffer = null;
	try {
		sBuffer = new StringBuffer("");
		sBuffer.append("<font face='GE Inspira' size='3' color='731473'>Hi "+mentorName+",<br><br> " +
				" Congratulations! You have been selected as one of the power mentors at JFWTC.<br> " +
				" Welcome to the The JFWTC Power Mentoring Program! .<br><br> " +
				" To facilitate a Mentee in initiating a mentoring relationship it is essential that Mentor Profiles be easily accessible to him/her.<br> " +
				" In this regard you are requested to register & create your profile in the e- mentoring system. <br><br>" +				
				" Please follow the below steps to fill in your details as a process of <b>'Mentor Registration'</b>: <br> " +
				" 1. Click on the link <a href='"+applicationURL+"' target='_new'> " + applicationURL + " </a> and login.<br>" +
				" 2. Click on link <b>'Mentor Section'</b>.<br>" +
				" 3. Click on link <b>'Nomination Request'</b>.<br>" +
				" 4. Register your details as a Mentor.<br>" +
				" <br><br>" +				
				" Thanks for joining the initiative and we look forward to your leadership in making the Power Mentoring Program a success . <br><br>" +
				" Thanks & Regards,<br>" +
				" The Power Mentoring Team</font>");			
	    MailService oMail = new MailService();
	    
	    oMail.doSendMail(mailHost, hrssoid+"@mail.ad.ge.com", mentorssoid+"@mail.ad.ge.com", nominatedMentorSubject, sBuffer.toString());
	    
	} catch(Exception e) {
		System.out.println("Error Occured while Sending nominated mentor(s) Mail. : " + e);
	}	
}	
 

 public void sendApprovalMailToMentee(String menteessoid,String menteename,String mentorssoid,String mentorName,String mentorComments) {
 // Purpose of mail --Intimates mentee for action taken by Mentor 
 // From Email - Mentor
 // To Email - 	 Mentee
		StringBuffer sBuffer = null;
		try {
			sBuffer = new StringBuffer("");
			sBuffer.append("<font face='GE Inspira' size='3' color='731473'>Dear "+menteename+",<br><br> " +
					" I would like to thank you for the opportunity to be your Mentor - A Friend,Coach & Confidant.<br><br> " +
					" Let us set up a meeting where you and I can discuss our expectations and the way forward for this partnership.<br> " +
					" I would like to assure you that all our discussions will be confidential. <br><br> ");
					if(mentorComments.length() > 0 ) {
					  sBuffer.append(mentorComments + "<br><br>");
					}		
					sBuffer.append(" All the best! <br><br>" +					
					" Thanks & Regards,<br>"+mentorName+"</font> ");		
		    MailService oMail = new MailService();	    
		    oMail.doSendMail(mailHost, mentorssoid+"@mail.ad.ge.com", menteessoid+"@mail.ad.ge.com", approvalSubject, sBuffer.toString());	 
		} catch(Exception e) {
			System.out.println("Error Occured while Sending Approval Mentee Request Mail. : " + e);
		}	
	}

 public void sendDeclinedMailToMentee(String menteessoid,String menteename,String mentorssoid,String mentorName,String mentorComments) {
	 // Purpose of mail --Intimates mentee for action taken by Mentor 
	 // From Email - Mentor
	 // To Email - 	Mentee
			StringBuffer sBuffer = null;
			try {
				sBuffer = new StringBuffer("");
				sBuffer.append("<font face='GE Inspira' size='3' color='731473'>Dear "+menteename+",<br><br> " +
						" Thank you for considering me as a potential mentor.<br><br> " + mentorComments + " <br><br> " +						
						" In case you have any queries, please do get in touch with me or your businesses Power Mentoring contact.<br><br> " +											
						" Thanks & Regards,<br>"+mentorName+"</font> ");		
			    MailService oMail = new MailService();	    
			   oMail.doSendMail(mailHost, mentorssoid+"@mail.ad.ge.com", menteessoid+"@mail.ad.ge.com", declineSubject, sBuffer.toString());	 
			} catch(Exception e) {
				System.out.println("Error Occured while Sending Decline Mentee Request Mail. : " + e);
			}	
 }

 
 public void sendSunsetRelationshipMailToMentor(String menteessoid,String menteename,String mentorssoid,String mentorName,String menteeComments) {	 
 // Purpose of mail - Intimates user that sunset relationship request has been submitted 
 // From Email - Mentee
 // To Email   - Mentor 	
		StringBuffer sBuffer = null;
			try {
				sBuffer = new StringBuffer("");
				sBuffer.append("<font face='GE Inspira' size='3' color='731473'>Dear "+mentorName+",<br><br> " +
						" It was a great experience being a part of this mentoring partnership. <br><br> " +						
						" As discussed we have mutually agreed to formally close this partnership.<br><br> " +	menteeComments + "<br><br>" +						
						" Please follow the below steps to sunset the relationship - <br>" +
						" 1. Click on the link <a href='"+applicationURL+"' target='_new'> " + applicationURL + "</a>  and login " +
						" 2. Click on link Mentor Section. <br>" +
						" 3. Click on link Pending Request. <br>" +
						" 4. Click on Approve/Decline button in <b>'Pending Sunset Relationship Request'</b> section. <br><br>" +						
						" Thanks & Regards,<br>" +
						""+mentorName+"</font>");
				
			    MailService oMail = new MailService();    
			    oMail.doSendMail(mailHost, menteessoid+"@mail.ad.ge.com", mentorssoid+"@mail.ad.ge.com", sunsetRSubject, sBuffer.toString());	 
			} catch(Exception e) {
				System.out.println("Error Occured while Sending Sunset relationship Mail. : " + e);
			}	
 }
 
 
 public void sendMailToMentee(String menteessoid,String menteename) {
	 // Purpose of mail --Intimates user that request has been submitted.  
	 // From Email - System
	 // To Email - 	 Mentee
			StringBuffer sBuffer = null;
			try {
				sBuffer = new StringBuffer("");
				sBuffer.append("<font face='GE Inspira' size='3' color='731473'>Dear "+menteename+",<br><br> " +
						" Your request has been submitted to the potential Mentor you have selected.<br><br> " +
						" Your selected Mentor will be accessing your request and will get back to you either personally or via the e- mentoring system shortly.<br> " +
						" We request you to get in touch with your selected mentor if you do not get an intimation from your choosen mentor within a weeks time.<br><br> " +
						" Best of Luck! <br><br>" +					
						" Thanks & Regards,<br>The Power Mentoring Team</font> ");		
			    MailService oMail = new MailService();	    
			    oMail.doSendMail(mailHost, frmAddress, menteessoid+"@mail.ad.ge.com", menteeMailSubject, sBuffer.toString());	 
			} catch(Exception e) {
				System.out.println("Error Occured while Sending Approval Mentee Request Mail. : " + e);
			}	
}
 
 
 public void sendMailToMentor(String mentorssoid,String mentorname,String menteename) {
	 // Purpose of mail --Intimates mentor that request has been submitted by mentee.  
	 // From Email - System
	 // To Email - 	 Mentee
			StringBuffer sBuffer = null;
			try {
				sBuffer = new StringBuffer("");
				sBuffer.append("<font face='GE Inspira' size='3' color='731473'>Hi "+mentorname+",<br><br> " +
						" It gives us great pleasure to inform you that "+menteename+" has requested you as a Mentor.<br><br> " +
						" You are required to either accept or decline this request for Mentorship." +
						" In both cases we recommend that you personally meet or call the individual to inform him/her of your final decision regarding entering into the proposed Mentoring partnership.<br><br> " +
						" Please follow the below steps to approve or decline this request -<br> " +
						" 1. Click on the link <a href='"+applicationURL+"' target='_new'> " + applicationURL + "</a> and login.<br> " +
						" 2. Click on the link Mentor Section.<br>" +					
						" 3. Click on the link Pending Request.<br>" +
						" 4. Click on Approve/Decline button in <b>'Pending Mentoring Request'</b> section to view Mentee's profile.<br><br> " +
						" For any support, do get in touch with your HRM or Business Mentoring Contact.<br><br>" +
						" Thanks for your continued support & engagement in the JFWTC Power Mentoring Program." +
						" <br><br>Thanks & Regards,<br>The Power Mentoring Team</font> ");		
			    MailService oMail = new MailService();			    
			    oMail.doSendMail(mailHost, frmAddress, mentorssoid+"@mail.ad.ge.com", mentorMailSubject + menteename, sBuffer.toString());	 
			} catch(Exception e) {
				System.out.println("Error Occured while Sending Approval Mentee Request Mail. : " + e);
			}	
		}
 
 
  public String getMentor(String selectedMentor,DataSource ds) throws Exception {		
		Connection con 		= null;
		ResultSet rs 		= null;
		PreparedStatement ps= null;
		String name = null;
		try {
			con = ds.getConnection();						
			String sql = "select GEHRFULLNAME from MENTOR_SPODA_EMPLOYEE where ssoid = ?";
			ps = con.prepareStatement(sql);			
			ps.setString(1,selectedMentor);
			rs = ps.executeQuery();
			rs.next();
			name = rs.getString(1);			
		} catch(Exception e) {			
			throw e;
		} finally {
			 try {
				  if(rs!=null) {
					  rs.close();
					  rs = null;
				  }
				  if(ps!=null) {
					  ps.close();
					  ps = null;
				  }			  
				  if(con!=null) {
					  con.close();
					  con = null;
				  }
			 } catch(SQLException sqlEx) {
				 throw sqlEx;
			 }		
		}
		return name;  
	 }
}
