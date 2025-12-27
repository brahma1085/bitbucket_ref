package com.ge.crd.ementoring.formbean.mentee;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class MentorProfileForm extends ActionForm{
	
	private String menteeName;
	private String menteeBusiness;
	private String menteeDesignation;
	private String mappingId;
	
	private String mentorName;
	private String mentorStatus;
	
	private ArrayList arrMentorList;
	private ArrayList arrstatusDescriptionList;
	
	public String getMenteeBusiness() {
		return menteeBusiness;
	}
	
	public void setMenteeBusiness(String menteeBusiness) {
		this.menteeBusiness = menteeBusiness;
	}
	
	public String getMenteeDesignation() {
		return menteeDesignation;
	}
	
	public void setMenteeDesignation(String menteeDesignation) {
		this.menteeDesignation = menteeDesignation;
	}
	
	public String getMenteeName() {
		return menteeName;
	}
	
	public void setMenteeName(String menteeName) {
		this.menteeName = menteeName;
	}

	public ArrayList getArrMentorList() {
		return arrMentorList;
	}

	public void setArrMentorList(ArrayList arrMentorList) {
		this.arrMentorList = arrMentorList;
	}

	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}

	public String getMentorName() {
		return mentorName;
	}

	public void setMentorName(String mentorName) {
		this.mentorName = mentorName;
	}

	public String getMentorStatus() {
		return mentorStatus;
	}

	public void setMentorStatus(String mentorStatus) {
		this.mentorStatus = mentorStatus;
	}

	public ArrayList getArrstatusDescriptionList() {
		return arrstatusDescriptionList;
	}

	public void setArrstatusDescriptionList(ArrayList arrstatusDescriptionList) {
		this.arrstatusDescriptionList = arrstatusDescriptionList;
	}
	 
	 
}
