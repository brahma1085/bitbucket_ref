package com.ge.crd.ementoring.formbean.mentor;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class mentorRegistrationFormBean extends ActionForm {
	
	String userAction;
	String mentorName;
	String mentorBusiness;
	String mentorDesignation;
	ArrayList arrBandList = new ArrayList();
	
	String mentoringPathList; 
	ArrayList arrMentoringPathId = new ArrayList();
	ArrayList arrMentoringPathDesc = new ArrayList();
	
	String eductionBackground;
	String positionHeldInOrOutsideGE;
	String strengthsCompetencies;	
	String personalDetails;	
	String areaOfInterest;	
	String noOfmenteesRequired;
	String registredMentorSlno;
	String bandId;
	String bandName;	
	String confirmmessage;	
	ArrayList arrPendingMentoringReqLst= new ArrayList();
	
	ArrayList arrCurrentMenteeList= new ArrayList();
	
	 String menteessoid;
	 String menteeName;	 
	 String business;
	 String designation;
	 //String bandName;	 
	 String elementDesired;
	 String frequenceOfMeeting;
	 //String areaOfInterest;
	 //String personalDetails;	 
	 String txtAreaComments;
	 String mappingId;	 
	 
	 String txtApproveRejectChoice;
	 
	 ArrayList arrPendingSunsetRequest = new ArrayList();
	 
	 String sunsetRelationsipId;
	 
	 ArrayList arrStatusId = new ArrayList();
	 ArrayList arrStatusName = new ArrayList();
	 String txtMenteeName;
	 String optStatus;
	 String resultFlag;
	 String statusDescription;
	
	 ArrayList arrMappingIdList = new ArrayList();	 
	 String initiatedDate;
	 
	 String actionName;
	 String actionDate;
	 
	 ArrayList arrEBList = new ArrayList();
	 ArrayList arrRPList = new ArrayList();
	 
	 String educationRowCount;
	 String roleRowCount;
	 
	 ArrayList arrActionDetails = new ArrayList();	 
	 
	 ArrayList arrRelationShipStatusList = new ArrayList();	 
	 
	public ArrayList getArrRelationShipStatusList() {
		return arrRelationShipStatusList;
	}
	public void setArrRelationShipStatusList(ArrayList arrRelationShipStatusList) {
		this.arrRelationShipStatusList = arrRelationShipStatusList;
	}
	public ArrayList getArrActionDetails() {
		return arrActionDetails;
	}
	public void setArrActionDetails(ArrayList arrActionDetails) {
		this.arrActionDetails = arrActionDetails;
	}
	public String getEducationRowCount() {
		return educationRowCount;
	}
	public void setEducationRowCount(String educationRowCount) {
		this.educationRowCount = educationRowCount;
	}
	public String getRoleRowCount() {
		return roleRowCount;
	}
	public void setRoleRowCount(String roleRowCount) {
		this.roleRowCount = roleRowCount;
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
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getInitiatedDate() {
		return initiatedDate;
	}
	public void setInitiatedDate(String initiatedDate) {
		this.initiatedDate = initiatedDate;
	}
	public ArrayList getArrMappingIdList() {
		return arrMappingIdList;
	}
	public void setArrMappingIdList(ArrayList arrMappingIdList) {
		this.arrMappingIdList = arrMappingIdList;
	}
	public String getResultFlag() {
		return resultFlag;
	}
	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}
	public String getOptStatus() {
		return optStatus;
	}
	public void setOptStatus(String optStatus) {
		this.optStatus = optStatus;
	}
	public String getAreaOfInterest() {
		return areaOfInterest;
	}
	public void setAreaOfInterest(String areaOfInterest) {
		this.areaOfInterest = areaOfInterest;
	}
	public ArrayList getArrBandList() {
		return arrBandList;
	}
	public void setArrBandList(ArrayList arrBandList) {
		this.arrBandList = arrBandList;
	}
	public ArrayList getArrMentoringPathDesc() {
		return arrMentoringPathDesc;
	}
	public void setArrMentoringPathDesc(ArrayList arrMentoringPathDesc) {
		this.arrMentoringPathDesc = arrMentoringPathDesc;
	}
	public ArrayList getArrMentoringPathId() {
		return arrMentoringPathId;
	}
	public void setArrMentoringPathId(ArrayList arrMentoringPathId) {
		this.arrMentoringPathId = arrMentoringPathId;
	}
	public String getEductionBackground() {
		return eductionBackground;
	}
	public void setEductionBackground(String eductionBackground) {
		this.eductionBackground = eductionBackground;
	}
	public String getMentorBusiness() {
		return mentorBusiness;
	}
	public void setMentorBusiness(String mentorBusiness) {
		this.mentorBusiness = mentorBusiness;
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
	public String getRegistredMentorSlno() {
		return registredMentorSlno;
	}
	public void setRegistredMentorSlno(String registredMentorSlno) {
		this.registredMentorSlno = registredMentorSlno;
	}
	public String getBandId() {
		return bandId;
	}
	public void setBandId(String bandId) {
		this.bandId = bandId;
	}
	public String getNoOfmenteesRequired() {
		return noOfmenteesRequired;
	}
	public void setNoOfmenteesRequired(String noOfmenteesRequired) {
		this.noOfmenteesRequired = noOfmenteesRequired;
	}
	public String getBandName() {
		return bandName;
	}
	public void setBandName(String bandName) {
		this.bandName = bandName;
	}
	public String getConfirmmessage() {
		return confirmmessage;
	}
	public void setConfirmmessage(String confirmmessage) {
		this.confirmmessage = confirmmessage;
	}	
	public ArrayList getArrPendingMentoringReqLst() {
		return arrPendingMentoringReqLst;
	}
	public void setArrPendingMentoringReqLst(ArrayList arrPendingMentoringReqLst) {
		this.arrPendingMentoringReqLst = arrPendingMentoringReqLst;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getElementDesired() {
		return elementDesired;
	}
	public void setElementDesired(String elementDesired) {
		this.elementDesired = elementDesired;
	}
	public String getFrequenceOfMeeting() {
		return frequenceOfMeeting;
	}
	public void setFrequenceOfMeeting(String frequenceOfMeeting) {
		this.frequenceOfMeeting = frequenceOfMeeting;
	}
	public String getMenteeName() {
		return menteeName;
	}
	public void setMenteeName(String menteeName) {
		this.menteeName = menteeName;
	}
	public String getTxtAreaComments() {
		return txtAreaComments;
	}
	public void setTxtAreaComments(String txtAreaComments) {
		this.txtAreaComments = txtAreaComments;
	}
	public String getMappingId() {
		return mappingId;
	}
	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}
	public String getTxtApproveRejectChoice() {
		return txtApproveRejectChoice;
	}
	public void setTxtApproveRejectChoice(String txtApproveRejectChoice) {
		this.txtApproveRejectChoice = txtApproveRejectChoice;
	}
	public ArrayList getArrCurrentMenteeList() {
		return arrCurrentMenteeList;
	}
	public void setArrCurrentMenteeList(ArrayList arrCurrentMenteeList) {
		this.arrCurrentMenteeList = arrCurrentMenteeList;
	}
	public ArrayList getArrPendingSunsetRequest() {
		return arrPendingSunsetRequest;
	}
	public void setArrPendingSunsetRequest(ArrayList arrPendingSunsetRequest) {
		this.arrPendingSunsetRequest = arrPendingSunsetRequest;
	}
	public String getSunsetRelationsipId() {
		return sunsetRelationsipId;
	}
	public void setSunsetRelationsipId(String sunsetRelationsipId) {
		this.sunsetRelationsipId = sunsetRelationsipId;
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
	public String getTxtMenteeName() {
		return txtMenteeName;
	}
	public void setTxtMenteeName(String txtMenteeName) {
		this.txtMenteeName = txtMenteeName;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	public String getMenteessoid() {
		return menteessoid;
	}
	public void setMenteessoid(String menteessoid) {
		this.menteessoid = menteessoid;
	}
	public String getUserAction() {
		return userAction;
	}
	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}
	
	

}
