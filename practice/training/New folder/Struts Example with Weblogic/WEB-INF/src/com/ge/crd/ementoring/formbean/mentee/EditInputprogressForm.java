package com.ge.crd.ementoring.formbean.mentee;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class EditInputprogressForm extends ActionForm{
   private ArrayList inputProgressRecord;
   private String progressDate;
   private String progressNotes;
   
   private String menteeName;
   private String mentorName;
     
   private String mappingId;
   private String inputDate;

   private String progressId;
   
    public ArrayList getInputProgressRecord() {
	     return inputProgressRecord;
    }

	public void setInputProgressRecord(ArrayList inputProgressRecord) {
		this.inputProgressRecord = inputProgressRecord;
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
	
	public String getInputDate() {
		return inputDate;
	}
	
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	
	public String getMappingId() {
		return mappingId;
	}
	
	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}
	
	public String getMenteeName() {
		return menteeName;
	}
	
	public void setMenteeName(String menteeName) {
		this.menteeName = menteeName;
	}
	
	public String getMentorName() {
		return mentorName;
	}
	
	public void setMentorName(String mentorName) {
		this.mentorName = mentorName;
	}

	public String getProgressId() {
		return progressId;
	}

	public void setProgressId(String progressId) {
		this.progressId = progressId;
	}
	   
       
}
