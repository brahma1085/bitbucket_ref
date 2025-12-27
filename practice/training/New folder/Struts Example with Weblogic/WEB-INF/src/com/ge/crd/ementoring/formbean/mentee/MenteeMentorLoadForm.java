package com.ge.crd.ementoring.formbean.mentee;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class MenteeMentorLoadForm extends ActionForm {
	
	private ArrayList menteeList;
	private ArrayList mentorList;
	private ArrayList relationMenteeMentorList;
	private ArrayList relationMenteeHRList;
	private ArrayList relationMentorHRList;
	
	private boolean menteeStatus;
	private boolean mentorStatus;
	
	private String presentStatus;
	private String personId;
	 
	
	private String mentorId;
	private String menteeId;
	
	
	private String progressDate;
	private String progressNotes;
	private int  userCount;
	
	
	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public ArrayList getMenteeList() {
		return menteeList;
	}
	
	public void setMenteeList(ArrayList menteeList) {
		this.menteeList = menteeList;
	}
	
	public ArrayList getMentorList() {
		return mentorList;
	}
	
	public void setMentorList(ArrayList mentorList) {
		this.mentorList = mentorList;
	}
	
	public ArrayList getRelationMenteeMentorList() {
		return relationMenteeMentorList;
	}
	
	public void setRelationMenteeMentorList(ArrayList relationMenteeMentorList) {
		this.relationMenteeMentorList = relationMenteeMentorList;
	}

	public String getPresentStatus() {
		return presentStatus;
	}

	public void setPresentStatus(String presentStatus) {
		this.presentStatus = presentStatus;
	}

	public String getProgressDate() {
		return progressDate;
	}

	public void setProgressDate(String progressDate) {
		this.progressDate = progressDate;
	}

	public String getProgressNotes() {
		return progressNotes;
	}

	public void setProgressNotes(String progressNotes) {
		this.progressNotes = progressNotes;
	}

	public String getMenteeId() {
		return menteeId;
	}

	public void setMenteeId(String menteeId) {
		this.menteeId = menteeId;
	}

	public String getMentorId() {
		return mentorId;
	}

	public void setMentorId(String mentorId) {
		this.mentorId = mentorId;
	}

	public ArrayList getRelationMenteeHRList() {
		return relationMenteeHRList;
	}

	public void setRelationMenteeHRList(ArrayList relationMenteeHRList) {
		this.relationMenteeHRList = relationMenteeHRList;
	}

	public ArrayList getRelationMentorHRList() {
		return relationMentorHRList;
	}

	public void setRelationMentorHRList(ArrayList relationMentorHRList) {
		this.relationMentorHRList = relationMentorHRList;
	}

	public boolean isMenteeStatus() {
		return menteeStatus;
	}

	public void setMenteeStatus(boolean menteeStatus) {
		this.menteeStatus = menteeStatus;
	}

	public boolean isMentorStatus() {
		return mentorStatus;
	}

	public void setMentorStatus(boolean mentorStatus) {
		this.mentorStatus = mentorStatus;
	}
	
}
