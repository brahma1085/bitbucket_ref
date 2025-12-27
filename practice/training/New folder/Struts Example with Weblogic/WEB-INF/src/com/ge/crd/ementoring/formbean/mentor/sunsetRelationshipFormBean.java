package com.ge.crd.ementoring.formbean.mentor;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class sunsetRelationshipFormBean extends ActionForm {
	
 ArrayList arrMentorDetails	= new ArrayList();	
 ArrayList arrMentorssoid 	= new ArrayList();
 ArrayList arrMentorssoName = new ArrayList();
 String reason; 
 String mentorList;
 

 ArrayList arrRatingMentor 	= new ArrayList(); 
 

public ArrayList getArrMentorDetails() {
	return arrMentorDetails;
}

public void setArrMentorDetails(ArrayList arrMentorDetails) {
	this.arrMentorDetails = arrMentorDetails;
}

public ArrayList getArrMentorssoid() {
	return arrMentorssoid;
}

public void setArrMentorssoid(ArrayList arrMentorssoid) {
	this.arrMentorssoid = arrMentorssoid;
}

public ArrayList getArrMentorssoName() {
	return arrMentorssoName;
}

public void setArrMentorssoName(ArrayList arrMentorssoName) {
	this.arrMentorssoName = arrMentorssoName;
}

public String getReason() {
	return reason;
}

public void setReason(String reason) {
	this.reason = reason;
}

public String getMentorList() {
	return mentorList;
}

public void setMentorList(String mentorList) {
	this.mentorList = mentorList;
}

public ArrayList getArrRatingMentor() {
	return arrRatingMentor;
}

public void setArrRatingMentor(ArrayList arrRatingMentor) {
	this.arrRatingMentor = arrRatingMentor;
}
 
 
}
