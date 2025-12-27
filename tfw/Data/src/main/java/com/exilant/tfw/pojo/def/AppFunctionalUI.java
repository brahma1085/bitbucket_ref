package com.exilant.tfw.pojo.def;

import java.io.Serializable;
import java.util.List;

import com.exilant.tfw.pojo.AppFeature;


public class AppFunctionalUI implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int appFunctionId;
	private String appFunctionName;
	private String appFunctionDesc;
	private int appFeatureCount;
	
	private List<AppFeature> appFeature;
	
	public int getAppFunctionId() {
		return appFunctionId;
	}
	public void setAppFunctionId(int appFunctionId) {
		this.appFunctionId = appFunctionId;
	}
	public String getAppFunctionName() {
		return appFunctionName;
	}
	public void setAppFunctionName(String appFunctionName) {
		this.appFunctionName = appFunctionName;
	}
	public String getAppFunctionDesc() {
		return appFunctionDesc;
	}
	public void setAppFunctionDesc(String appFunctionDesc) {
		this.appFunctionDesc = appFunctionDesc;
	}
	public int getAppFeatureCount() {
		return appFeatureCount;
	}
	public void setAppFeatureCount(int appFeatureCount) {
		this.appFeatureCount = appFeatureCount;
	}
	public List<AppFeature> getAppFeature() {
		return appFeature;
	}
	public void setAppFeature(List<AppFeature> appFeature) {
		this.appFeature = appFeature;
	}
	@Override
	public String toString() {
		return "AppFunctionalUI [appFunctionId=" + appFunctionId
				+ ", appFunctionName=" + appFunctionName + ", appFunctionDesc="
				+ appFunctionDesc + ", appFeatureCount=" + appFeatureCount
				+ ", appFeature=" + appFeature + "]";
	}
	
	
	

	
	
}
