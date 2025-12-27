package com.ge.crd.ementoring.formbean.mentor;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class NominateMentorsFormBean extends ActionForm{
	
	String txtMentorName;
	String optStatus;
	String mentorBusiness;	
	ArrayList arrStatusId = new ArrayList();
	ArrayList arrStatusName = new ArrayList();
	
	ArrayList arrNominatedMentorsLst = new ArrayList();
	ArrayList arrMenteeList       = new ArrayList();
	
	ArrayList arrSearchResult = new ArrayList();
	
	String mentorName;
	//String mentorBusiness;
	String mentorDesignation;
	String mentoringPathList; 
	String eductionBackground;
	String positionHeldInOrOutsideGE;
	String strengthsCompetencies;	
	String personalDetails;	
	String areaOfInterest;
	String noOfMenteeRequired;
	String txtRegisteredSlno;
	
	String resultFlag;
	
	String mentorStatus;

	ArrayList arrRLList = new ArrayList();
	
	String nominationDate;
	String registrationDate;
	
	ArrayList arrEBList = new ArrayList();
    ArrayList arrRPList = new ArrayList();
    
    String optLocationLst;
    String optBusinessList;
	ArrayList arrLocationId = new ArrayList();
    ArrayList arrLocationName = new ArrayList();    
    ArrayList arrBusinessName = new ArrayList();
    

	public ArrayList getArrBusinessName() {
		return arrBusinessName;
	}

	public void setArrBusinessName(ArrayList arrBusinessName) {
		this.arrBusinessName = arrBusinessName;
	}

	public ArrayList getArrLocationId() {
		return arrLocationId;
	}

	public void setArrLocationId(ArrayList arrLocationId) {
		this.arrLocationId = arrLocationId;
	}

	public ArrayList getArrLocationName() {
		return arrLocationName;
	}

	public void setArrLocationName(ArrayList arrLocationName) {
		this.arrLocationName = arrLocationName;
	}

	public String getOptBusinessList() {
		return optBusinessList;
	}

	public void setOptBusinessList(String optBusinessList) {
		this.optBusinessList = optBusinessList;
	}

	public String getOptLocationLst() {
		return optLocationLst;
	}

	public void setOptLocationLst(String optLocationLst) {
		this.optLocationLst = optLocationLst;
	}

	public ArrayList getArrEBList() {
		return arrEBList;
	}

	public void setArrEBList(ArrayList arrEBList) {
		this.arrEBList = arrEBList;
	}

	public ArrayList getArrRPList() {
		return arrRPList;
	}

	public void setArrRPList(ArrayList arrRPList) {
		this.arrRPList = arrRPList;
	}

	public String getNominationDate() {
		return nominationDate;
	}

	public void setNominationDate(String nominationDate) {
		this.nominationDate = nominationDate;
	}

	public ArrayList getArrRLList() {
		return arrRLList;
	}

	public void setArrRLList(ArrayList arrRLList) {
		this.arrRLList = arrRLList;
	}

	public String getMentorStatus() {
		return mentorStatus;
	}

	public void setMentorStatus(String mentorStatus) {
		this.mentorStatus = mentorStatus;
	}

	public String getResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}

	public String getTxtRegisteredSlno() {
		return txtRegisteredSlno;
	}

	public void setTxtRegisteredSlno(String txtRegisteredSlno) {
		this.txtRegisteredSlno = txtRegisteredSlno;
	}

	public ArrayList getArrNominatedMentorsLst() {
		return arrNominatedMentorsLst;
	}

	public void setArrNominatedMentorsLst(ArrayList arrNominatedMentorsLst) {
		this.arrNominatedMentorsLst = arrNominatedMentorsLst;
	}

	public String getMentorBusiness() {
		return mentorBusiness;
	}

	public void setMentorBusiness(String mentorBusiness) {
		this.mentorBusiness = mentorBusiness;
	}

	public String getOptStatus() {
		return optStatus;
	}

	public void setOptStatus(String optStatus) {
		this.optStatus = optStatus;
	}

	public String getTxtMentorName() {
		return txtMentorName;
	}

	public void setTxtMentorName(String txtMentorName) {
		this.txtMentorName = txtMentorName;
	}

	public ArrayList getArrStatusId() {
		return arrStatusId;
	}

	public void setArrStatusId(ArrayList arrStatusId) {
		this.arrStatusId = arrStatusId;
	}

	public ArrayList getArrStatusName() {
		return arrStatusName;
	}

	public void setArrStatusName(ArrayList arrStatusName) {
		this.arrStatusName = arrStatusName;
	}

	public ArrayList getArrSearchResult() {
		return arrSearchResult;
	}

	public void setArrSearchResult(ArrayList arrSearchResult) {
		this.arrSearchResult = arrSearchResult;
	}

	public String getAreaOfInterest() {
		return areaOfInterest;
	}

	public void setAreaOfInterest(String areaOfInterest) {
		this.areaOfInterest = areaOfInterest;
	}

	public String getEductionBackground() {
		return eductionBackground;
	}

	public void setEductionBackground(String eductionBackground) {
		this.eductionBackground = eductionBackground;
	}

	public String getMentorDesignation() {
		return mentorDesignation;
	}

	public void setMentorDesignation(String mentorDesignation) {
		this.mentorDesignation = mentorDesignation;
	}

	public String getMentoringPathList() {
		return mentoringPathList;
	}

	public void setMentoringPathList(String mentoringPathList) {
		this.mentoringPathList = mentoringPathList;
	}

	public String getMentorName() {
		return mentorName;
	}

	public void setMentorName(String mentorName) {
		this.mentorName = mentorName;
	}

	public String getPersonalDetails() {
		return personalDetails;
	}

	public void setPersonalDetails(String personalDetails) {
		this.personalDetails = personalDetails;
	}

	public String getPositionHeldInOrOutsideGE() {
		return positionHeldInOrOutsideGE;
	}

	public void setPositionHeldInOrOutsideGE(String positionHeldInOrOutsideGE) {
		this.positionHeldInOrOutsideGE = positionHeldInOrOutsideGE;
	}

	public String getStrengthsCompetencies() {
		return strengthsCompetencies;
	}

	public void setStrengthsCompetencies(String strengthsCompetencies) {
		this.strengthsCompetencies = strengthsCompetencies;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getNoOfMenteeRequired() {
		return noOfMenteeRequired;
	}

	public void setNoOfMenteeRequired(String noOfMenteeRequired) {
		this.noOfMenteeRequired = noOfMenteeRequired;
	}

	public ArrayList getArrMenteeList() {
		return arrMenteeList;
	}

	public void setArrMenteeList(ArrayList arrMenteeList) {
		this.arrMenteeList = arrMenteeList;
	}	
	
    
}
