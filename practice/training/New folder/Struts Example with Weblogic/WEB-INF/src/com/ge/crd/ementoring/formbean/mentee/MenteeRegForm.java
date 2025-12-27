package com.ge.crd.ementoring.formbean.mentee;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;


public class MenteeRegForm extends ActionForm {
	
	ArrayList menteeBand = new ArrayList();
    ArrayList menteeBusiness = new ArrayList();
	String menteeName = null;
    String menteeDesignation = null;
	String menteeBusinessType = null;
	String menteeTitle = null;
	
	/**
	 * @return Returns the menteeBusinessType.
	 */
	public String getMenteeBusinessType() {
		return menteeBusinessType;
	}

	/**
	 * @param menteeBusinessType The menteeBusinessType to set.
	 */
	public void setMenteeBusinessType(String menteeBusinessType) {
		this.menteeBusinessType = menteeBusinessType;
	}
	
	/**
	 * @return Returns the menteeTitle.
	 */
	public String getMenteeTitle() {
		return menteeTitle;
	}

	/**
	 * @param menteeTitle The menteeTitle to set.
	 */
	public void setMenteeTitle(String menteeTitle) {
		this.menteeTitle = menteeTitle;
	}

	/**
	 * @return Returns the menteeBand.
	 */
	public ArrayList getMenteeBand() {
		return menteeBand;
	}

	/**
	 * @param menteeBand The menteeBand to set.
	 */
	public void setMenteeBand(ArrayList menteeBand) {
		this.menteeBand = menteeBand;
	}

	/**
	 * @return Returns the menteeBusiness.
	 */
	public ArrayList getMenteeBusiness() {
		return menteeBusiness;
	}

	/**
	 * @param menteeBusiness The menteeBusiness to set.
	 */
	public void setMenteeBusiness(ArrayList menteeBusiness) {
		this.menteeBusiness = menteeBusiness;
	}

	/**
	 * @return Returns the menteeName.
	 */
	public String getMenteeName() {
		return menteeName;
	}

	/**
	 * @param menteeName The menteeName to set.
	 */
	public void setMenteeName(String menteeName) {
		this.menteeName = menteeName;
	}

	/**
	 * @return Returns the menteeDesignation.
	 */
	public String getMenteeDesignation() {
		return menteeDesignation;
	}

	/**
	 * @param menteeDesignation The menteeDesignation to set.
	 */
	public void setMenteeDesignation(String menteeDesignation) {
		this.menteeDesignation = menteeDesignation;
	}
   
}
