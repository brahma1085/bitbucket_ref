package com.sssoft.isatt.data.pojo.output;

import java.io.Serializable;

/**
 * The Class TestingFlow.
 */
public class TestingFlow implements Serializable {

	/** Default Serial Version Id. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	private String name;

	/** The type. */
	private String type;

	/** The id. */
	private int id;

	/** The sibling id. */
	private int siblingId;

	/** The parent id. */
	private int parentId;

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the sibling id.
	 * 
	 * @return the sibling id
	 */
	public int getSiblingId() {
		return siblingId;
	}

	/**
	 * Sets the sibling id.
	 * 
	 * @param siblingId
	 *            the new sibling id
	 */
	public void setSiblingId(int siblingId) {
		this.siblingId = siblingId;
	}

	/**
	 * Gets the parent id.
	 * 
	 * @return the parent id
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * Sets the parent id.
	 * 
	 * @param parentId
	 *            the new parent id
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestingFlow [name=" + name + ", type=" + type + ", id=" + id
				+ ", siblingId=" + siblingId + ", parentId=" + parentId + "]";
	}

}
