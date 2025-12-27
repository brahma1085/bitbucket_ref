package edu.bean;

import java.io.Serializable;
import java.sql.Date;

public class Contact implements Serializable {

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String designation;
	private String info;
	private String description;
	private Date createdDate;
	private String createdBy;

	public Contact() {
		super();
	}

	public Contact(int id, String name, String info, Date createdDate, String createdBy) {
		super();
		this.id = id;
		this.name = name;
		this.info = info;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
	}

	public Contact(int id, String name, String designation, String info, String description, Date createdDate, String createdBy) {
		super();
		this.id = id;
		this.name = name;
		this.designation = designation;
		this.info = info;
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

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
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
		return "Contact [id=" + id + ", name=" + name + ", designation=" + designation + ", info=" + info + ", description=" + description + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + "]";
	}

}
