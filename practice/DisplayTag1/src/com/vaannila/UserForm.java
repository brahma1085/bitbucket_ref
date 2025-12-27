package com.vaannila;

import java.util.ArrayList;

public class UserForm extends org.apache.struts.action.ActionForm {

	private static final long serialVersionUID = 1L;
	private ArrayList actorList;

	public UserForm() {
		super();
	}

	public ArrayList getActorList() {
		return actorList;
	}

	public void setActorList(ArrayList actorList) {
		this.actorList = actorList;
	}
}