package com.ge.crd.ementoring.formbean.mentee;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class MenteeFindingMentorForm extends ActionForm {
	
	private String searchBymentorBusiness;
	private String searchBymentorPath;
	private String searchBymentorName;
	private String searchBymentorStrength;
	
	private String hddMentorId;
	
	private ArrayList arrayBusiness = new ArrayList();
	private ArrayList arrayPath  = new ArrayList();
	private ArrayList arrayMentorDetails = new ArrayList();
	
	private ArrayList mentorRecord  = new ArrayList();
	private String mentorID;
	
	private boolean menteeHasMentor;
	
	public String getSearchBymentorBusiness() {
		return searchBymentorBusiness;
	}

	public void setSearchBymentorBusiness(String searchBymentorBusiness) {
		this.searchBymentorBusiness = searchBymentorBusiness;
	}

	public String getSearchBymentorName() {
		return searchBymentorName;
	}

	public void setSearchBymentorName(String searchBymentorName) {
		this.searchBymentorName = searchBymentorName;
	}

	public String getSearchBymentorPath() {
		return searchBymentorPath;
	}

	public void setSearchBymentorPath(String searchBymentorPath) {
		this.searchBymentorPath = searchBymentorPath;
	}

	public String getSearchBymentorStrength() {
		return searchBymentorStrength;
	}

	public void setSearchBymentorStrength(String searchBymentorStrength) {
		this.searchBymentorStrength = searchBymentorStrength;
	}

	public ArrayList getMentorRecord() {
		return mentorRecord;
	}

	public void setMentorRecord(ArrayList mentorRecord) {
		this.mentorRecord = mentorRecord;
	}

	public String getMentorID() {
		return mentorID;
	}

	public void setMentorID(String mentorID) {
		this.mentorID = mentorID;
	}

	public ArrayList getArrayBusiness() {
		return arrayBusiness;
	}

	public void setArrayBusiness(ArrayList arrayBusiness) {
		this.arrayBusiness = arrayBusiness;
	}

	public ArrayList getArrayPath() {
		return arrayPath;
	}

	public void setArrayPath(ArrayList arrayPath) {
		this.arrayPath = arrayPath;
	}

	public ArrayList getArrayMentorDetails() {
		return arrayMentorDetails;
	}

	public void setArrayMentorDetails(ArrayList arrayMentorDetails) {
		this.arrayMentorDetails = arrayMentorDetails;
	}

	public String getHddMentorId() {
		return hddMentorId;
	}

	public void setHddMentorId(String hddMentorId) {
		this.hddMentorId = hddMentorId;
	}

	public boolean isMenteeHasMentor() {
		return menteeHasMentor;
	}

	public void setMenteeHasMentor(boolean menteeHasMentor) {
		this.menteeHasMentor = menteeHasMentor;
	}
	
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		 searchBymentorBusiness="";
		 searchBymentorPath="";
		 searchBymentorName="";
		 searchBymentorStrength="";
		 mentorRecord = null;
	}
	
	   
}
