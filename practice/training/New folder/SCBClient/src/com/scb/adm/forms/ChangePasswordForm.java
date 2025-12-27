package com.scb.adm.forms;
//Create by Mohsin on 22DEC2008
import org.apache.struts.action.ActionForm;

public class ChangePasswordForm extends ActionForm {
	
	String pageId;	
String usn,oldpsw,newpsw,confpsw;
public String getPageId() {
	return pageId;
}
public void setPageId(String pageId) {
	this.pageId = pageId;
}
public String getUsn() {
	return usn;
}
public void setUsn(String usn) {
	this.usn = usn;
}
public String getOldpsw() {
	return oldpsw;
}
public void setOldpsw(String oldpsw) {
	this.oldpsw = oldpsw;
}
public String getNewpsw() {
	return newpsw;
}
public void setNewpsw(String newpsw) {
	this.newpsw = newpsw;
}
public String getConfpsw() {
	return confpsw;
}
public void setConfpsw(String confpsw) {
	this.confpsw = confpsw;
}


}
