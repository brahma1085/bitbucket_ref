package com.ge.crd.ementoring.formbean.mentee;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ge.crd.ementoring.util.ValidateService;

public class MenteeRegistrationForm extends ActionForm{

	private String menteeName;
    private String menteeBusiness;
    private String menteeDesignation;
    private String menteeBand;
    private String menteePosition;
    private String menteeElement;
    private String menteeMeetingCount;
    private String menteeInterests;
    private String menteeDetails;
    private String menteeBusinessType = null;
 	private String menteeTitle = null;
     
 	ArrayList arrMenteeRoles = new ArrayList();
    ArrayList arrMenteeBand = new ArrayList();
    String rowCount;
    
    public String getRowCount() {
		return rowCount;
	}

	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	public ArrayList getArrMenteeRoles() {
		return arrMenteeRoles;
	}

	public void setArrMenteeRoles(ArrayList arrMenteeRoles) {
		this.arrMenteeRoles = arrMenteeRoles;
	}

	public ArrayList getArrMenteeBand() {
		return arrMenteeBand;
	}

	public void setArrMenteeBand(ArrayList arrMenteeBand) {
		this.arrMenteeBand = arrMenteeBand;
	}

	public String getMenteeBand() {
		return menteeBand;
	}

	public void setMenteeBand(String menteeBand) {
		this.menteeBand = menteeBand;
	}

	public String getMenteeBusiness() {
		return menteeBusiness;
	}

	public void setMenteeBusiness(String menteeBusiness) {
		this.menteeBusiness = menteeBusiness;
	}

	public String getMenteeBusinessType() {
		return menteeBusinessType;
	}

	public void setMenteeBusinessType(String menteeBusinessType) {
		this.menteeBusinessType = menteeBusinessType;
	}

	public String getMenteeDesignation() {
		return menteeDesignation;
	}

	public void setMenteeDesignation(String menteeDesignation) {
		this.menteeDesignation = menteeDesignation;
	}

	public String getMenteeDetails() {
		return menteeDetails;
	}

	public void setMenteeDetails(String menteeDetails) {
		this.menteeDetails = menteeDetails;
	}

	public String getMenteeElement() {
		return menteeElement;
	}

	public void setMenteeElement(String menteeElement) {
		this.menteeElement = menteeElement;
	}

	public String getMenteeInterests() {
		return menteeInterests;
	}

	public void setMenteeInterests(String menteeInterests) {
		this.menteeInterests = menteeInterests;
	}

	public String getMenteeMeetingCount() {
		return menteeMeetingCount;
	}

	public void setMenteeMeetingCount(String menteeMeetingCount) {
		this.menteeMeetingCount = menteeMeetingCount;
	}

	public String getMenteeName() {
		return menteeName;
	}

	public void setMenteeName(String menteeName) {
		this.menteeName = menteeName;
	}

	public String getMenteePosition() {
		return menteePosition;
	}

	public void setMenteePosition(String menteePosition) {
		this.menteePosition = menteePosition;
	}

	public String getMenteeTitle() {
		return menteeTitle;
	}

	public void setMenteeTitle(String menteeTitle) {
		this.menteeTitle = menteeTitle;
	}
}
