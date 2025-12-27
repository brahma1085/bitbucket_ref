package com.ge.crd.ementoring.formbean.mentor;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class MentorFormBean extends ActionForm {
	
	String [] optMentorList   = null;	
	String optSerachBy 		  = null;
	String txtSearchValue     = null;	
	ArrayList arrSearchResult = new ArrayList();	
	String [] arrSelectedMentor = null;	
	String resultFlag = null;
	ArrayList arrSearchById= new ArrayList();
	ArrayList arrSearchByName = new ArrayList();
	
	String mentorExistFlag = null;
	
	
	public ArrayList getArrSearchResult() {
		return arrSearchResult;
	}
	public void setArrSearchResult(ArrayList arrSearchResult) {
		this.arrSearchResult = arrSearchResult;
	}
	public String[] getOptMentorList() {
		return optMentorList;
	}
	public void setOptMentorList(String[] optMentorList) {
		this.optMentorList = optMentorList;
	}
	public String getOptSerachBy() {
		return optSerachBy;
	}
	public void setOptSerachBy(String optSerachBy) {
		this.optSerachBy = optSerachBy;
	}
	public String getTxtSearchValue() {
		return txtSearchValue;
	}
	public void setTxtSearchValue(String txtSearchValue) {
		this.txtSearchValue = txtSearchValue;
	}
	public String[] getArrSelectedMentor() {
		return arrSelectedMentor;
	}
	public void setArrSelectedMentor(String[] arrSelectedMentor) {
		this.arrSelectedMentor = arrSelectedMentor;
	}
	public String getResultFlag() {
		return resultFlag;
	}
	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}
	public ArrayList getArrSearchById() {
		return arrSearchById;
	}
	public void setArrSearchById(ArrayList arrSearchById) {
		this.arrSearchById = arrSearchById;
	}
	public ArrayList getArrSearchByName() {
		return arrSearchByName;
	}
	public void setArrSearchByName(ArrayList arrSearchByName) {
		this.arrSearchByName = arrSearchByName;
	}
	
	public String getMentorExistFlag() {
		return mentorExistFlag;
	}
	public void setMentorExistFlag(String mentorExistFlag) {
		this.mentorExistFlag = mentorExistFlag;
	}

		

	
}
