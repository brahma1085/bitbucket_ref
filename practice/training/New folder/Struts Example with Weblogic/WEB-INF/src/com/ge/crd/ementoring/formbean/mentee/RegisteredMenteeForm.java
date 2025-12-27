package com.ge.crd.ementoring.formbean.mentee;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class RegisteredMenteeForm extends ActionForm{
	
	private String txtRegisteredSlno;
	private String txtMenteeName;
	private ArrayList optStatus;
	private String arrStatusId;
	private String arrStatusName;
	private boolean resultFlag ;
	private ArrayList arrSearchResult;
	
	public ArrayList getArrSearchResult() {
		return arrSearchResult;
	}
	
	public void setArrSearchResult(ArrayList arrSearchResult) {
		this.arrSearchResult = arrSearchResult;
	}
	
	public String getArrStatusId() {
		return arrStatusId;
	}
	
	public void setArrStatusId(String arrStatusId) {
		this.arrStatusId = arrStatusId;
	}
	
	public String getArrStatusName() {
		return arrStatusName;
	}
	
	public void setArrStatusName(String arrStatusName) {
		this.arrStatusName = arrStatusName;
	}
	
	public ArrayList getOptStatus() {
		return optStatus;
	}
	
	public void setOptStatus(ArrayList optStatus) {
		this.optStatus = optStatus;
	}
	
	public boolean isResultFlag() {
		return resultFlag;
	}
	
	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}
	
	public String getTxtMenteeName() {
		return txtMenteeName;
	}
	
	public void setTxtMenteeName(String txtMenteeName) {
		this.txtMenteeName = txtMenteeName;
	}
	
	public String getTxtRegisteredSlno() {
		return txtRegisteredSlno;
	}
	
	public void setTxtRegisteredSlno(String txtRegisteredSlno) {
		this.txtRegisteredSlno = txtRegisteredSlno;
	}
	
}
