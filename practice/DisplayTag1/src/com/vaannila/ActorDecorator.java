package com.vaannila;

import org.displaytag.decorator.TableDecorator;

public class ActorDecorator extends TableDecorator {

	public String getUserName() {
		ActorData actorData = (ActorData) getCurrentRowObject();
		return actorData.getUserName();
	}

	public String getEmailId() {
		ActorData actorData = (ActorData) getCurrentRowObject();
		String emailId = "<a href=\"mailto:" + actorData.getEmailId() + "\">"
				+ actorData.getEmailId() + "</a>";
		return emailId;
	}
}