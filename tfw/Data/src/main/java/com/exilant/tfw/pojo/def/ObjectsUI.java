package com.exilant.tfw.pojo.def;

import java.io.Serializable;

public class ObjectsUI implements Serializable {

	private static final long serialVersionUID = 1L;

	private int paramId;
	private String paramName;
	private String paramDesc;
	private String objectName;
	private int sortOrder;

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public int getParamId() {
		return paramId;
	}

	public void setParamId(int paramId) {
		this.paramId = paramId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	@Override
	public String toString() {
		return "ObjectsUI [paramId=" + paramId + ", paramName=" + paramName
				+ ", paramDesc=" + paramDesc + ", objectName=" + objectName
				+ ", sortOrder=" + sortOrder + "]";
	}

	

}
