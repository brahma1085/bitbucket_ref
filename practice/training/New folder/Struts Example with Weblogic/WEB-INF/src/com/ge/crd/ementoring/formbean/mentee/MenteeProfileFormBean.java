package com.ge.crd.ementoring.formbean.mentee;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class MenteeProfileFormBean extends ActionForm{
	
	private String menteeName;
	private String business;
	private String designation;
	private String positionHeldInOrOutsideGE;
	private String menteeDesiredFrequencyofMeetings;
	private String elementDesired;
	private String frequenceOfMeeting;
	private String areaOfInterest;
	private String personalDetails;
	private String initiatedDate;
	private String approveOrRejectionDate;
	private String statusDescription;
	private ArrayList statusDescriptionList;
	private ArrayList businessList;
	private ArrayList locationList;
	private ArrayList locationBusinessMappingList;
	private String menteeStatus;
	
	private String userAdminFlag;
	private String businessType;
	private String locationType;
	
	
	private String menteeRegSerialNo;
	private ArrayList arrRPList;
	private ArrayList arrMenteeList;
	
	
	
	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public ArrayList getLocationList() {
		return locationList;
	}

	public void setLocationList(ArrayList locationList) {
		this.locationList = locationList;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getUserAdminFlag() {
		return userAdminFlag;
	}

	public void setUserAdminFlag(String userAdminFlag) {
		this.userAdminFlag = userAdminFlag;
	}

	
	public ArrayList getLocationBusinessMappingList() {
		return locationBusinessMappingList;
	}

	public void setLocationBusinessMappingList(ArrayList locationBusinessMappingList) {
		this.locationBusinessMappingList = locationBusinessMappingList;
	}

	public ArrayList getBusinessList() {
		return businessList;
	}

	public void setBusinessList(ArrayList businessList) {
		this.businessList = businessList;
	}

	public ArrayList getStatusDescriptionList() {
		return statusDescriptionList;
	}

	public void setStatusDescriptionList(ArrayList statusDescriptionList) {
		this.statusDescriptionList = statusDescriptionList;
	}

	public ArrayList getArrMenteeList() {
		return arrMenteeList;
	}

	public void setArrMenteeList(ArrayList arrMenteeList) {
		this.arrMenteeList = arrMenteeList;
	}

	public String getMenteeRegSerialNo() {
		return menteeRegSerialNo;
	}

	public void setMenteeRegSerialNo(String menteeRegSerialNo) {
		this.menteeRegSerialNo = menteeRegSerialNo;
	}

	public String getApproveOrRejectionDate() {
		return approveOrRejectionDate;
	}

	public void setApproveOrRejectionDate(String approveOrRejectionDate) {
		this.approveOrRejectionDate = approveOrRejectionDate;
	}

	public String getAreaOfInterest() {
		return areaOfInterest;
	}

	public void setAreaOfInterest(String areaOfInterest) {
		this.areaOfInterest = areaOfInterest;
	}

	public ArrayList getArrRPList() {
		return arrRPList;
	}

	public void setArrRPList(ArrayList arrRPList) {
		this.arrRPList = arrRPList;
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

	public String getInitiatedDate() {
		return initiatedDate;
	}

	public void setInitiatedDate(String initiatedDate) {
		this.initiatedDate = initiatedDate;
	}

	public String getMenteeDesiredFrequencyofMeetings() {
		return menteeDesiredFrequencyofMeetings;
	}

	public void setMenteeDesiredFrequencyofMeetings(
			String menteeDesiredFrequencyofMeetings) {
		this.menteeDesiredFrequencyofMeetings = menteeDesiredFrequencyofMeetings;
	}

	public String getMenteeName() {
		return menteeName;
	}

	public void setMenteeName(String menteeName) {
		this.menteeName = menteeName;
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

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getMenteeStatus() {
		return menteeStatus;
	}

	public void setMenteeStatus(String menteeStatus) {
		this.menteeStatus = menteeStatus;
	}
	
}
