package edu.bean;

import java.io.Serializable;
import java.sql.Date;

public class Users implements Serializable {

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String password;
	private String description;
	private Date createdDate;
	private String createdBy;

	public Users() {
		super();
	}

	public Users(int id, String name, String password, String description, Date createdDate, String createdBy) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.description = description;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", name=" + name + ", password=" + password + ", description=" + description + ", createdDate=" + createdDate + ", createdBy=" + createdBy + "]";
	}

}
