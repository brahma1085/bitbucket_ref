package com.exilant.tfw.pojo.output;

import java.io.Serializable;
import java.util.List;

/**
 * The Class ApplicationFlow.
 */
public class ApplicationFlow implements Serializable {

	/** Default Serial version Id. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	private String name;

	/** The id. */
	private int id;

	/** The type. */
	private String type;

	/** The flow. */
	private List<TestingFlow> flow;

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
	 * Gets the flow.
	 * 
	 * @return the flow
	 */
	public List<TestingFlow> getFlow() {
		return flow;
	}

	/**
	 * Sets the flow.
	 * 
	 * @param flow
	 *            the new flow
	 */
	public void setFlow(List<TestingFlow> flow) {
		this.flow = flow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ApplicationFlow [name=" + name + ", id=" + id + ", type="
				+ type + ", flow=" + flow + "]";
	}

}
