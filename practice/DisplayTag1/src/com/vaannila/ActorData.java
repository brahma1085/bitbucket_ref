package com.vaannila;

import java.util.ArrayList;

public class ActorData {

	private String tvShow;
	private String userName;
	private String emailId;

	public ActorData() {

	}

	public ActorData(String tvShow, String userName, String emailId) {
		this.tvShow = tvShow;
		this.userName = userName;
		this.emailId = emailId;
	}

	public ArrayList loadData() {
		ArrayList actorList = new ArrayList();
		actorList.add(new ActorData("The Office", "Michael Scott",
				"michael.scott@dundermifflin.com"));
		actorList.add(new ActorData("The Office", "Dwight Schrute",
				"dwight.schrute@dundermifflin.com"));
		actorList.add(new ActorData("The Office", "Jim Halpert",
				"jim.halpert@dundermifflin.com"));
		actorList.add(new ActorData("The Office", "Pam Beesly",
				"pam.beesly@dundermifflin.com"));
		actorList.add(new ActorData("The Office", "Andy Bernad",
				"andy.bernad@dundermifflin.com"));
		actorList.add(new ActorData("The Office", "Angela Martin",
				"angela.martin@dundermifflin.com"));
		actorList.add(new ActorData("Friends", "Rachel Green",
				"rachel.green@friends.com"));
		actorList.add(new ActorData("Friends", "Monica Geller",
				"monica.geller@friends.com"));
		actorList.add(new ActorData("Friends", "Phoebe Buffay",
				"phoebe.buffay@friends.com"));
		actorList.add(new ActorData("Friends", "Joey Tribbiani",
				"joey.tribbiani@friends.com"));
		actorList.add(new ActorData("Friends", "Chandler Bing",
				"chandler.bing@friends.com"));
		actorList.add(new ActorData("Friends", "Ross Geller",
				"ross.geller@friends.com"));
		return actorList;
	}

	public String getTvShow() {
		return tvShow;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmailId() {
		return emailId;
	}

}