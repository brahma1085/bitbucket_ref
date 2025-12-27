package com.sssoft.isatt.data.pojo.def;

import java.io.Serializable;
import java.util.List;

import com.sssoft.isatt.data.pojo.Screen;

public class AppFeatureUI implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	private int appFeatureId;
	private String appFeatureName;
	private String appFeatureDesc;
	private int appscreensCount;
	private List<Screen> screen; 
	
	public List<Screen> getScreen() {
		return screen;
	}
	public void setScreen(List<Screen> screen) {
		this.screen = screen;
	}
	
	public int getAppFeatureId() {
		return appFeatureId;
	}
	public void setAppFeatureId(int appFeatureId) {
		this.appFeatureId = appFeatureId;
	}
	public String getAppFeatureName() {
		return appFeatureName;
	}
	public void setAppFeatureName(String appFeatureName) {
		this.appFeatureName = appFeatureName;
	}
	public String getAppFeatureDesc() {
		return appFeatureDesc;
	}
	public void setAppFeatureDesc(String appFeatureDesc) {
		this.appFeatureDesc = appFeatureDesc;
	}
	public int getAppscreensCount() {
		return appscreensCount;
	}
	public void setAppscreensCount(int appscreensCount) {
		this.appscreensCount = appscreensCount;
	}
	
	
	@Override
	public String toString() {
		return "AppFeatureUI [appFeatureId=" + appFeatureId + ", appFeatureName=" + appFeatureName + ", appFeatureDesc=" + appFeatureDesc
				+ ", appscreensCount=" + appscreensCount + "]";
	}

}
