package com.eris;

import java.sql.*;
import java.io.*;

public class AddJobBean implements Serializable {
	private static final long serialVersionUID = 1L;
	int jid, cid;
	String od, ed, edu, exp, eif, s, cat, nov, a, jdesc;

	public void setJobid(int id) {
		jid = id;
	}

	public int getJobid() {
		return jid;
	}

	public void setClientid(int id) {
		cid = id;
	}

	public int getClientid() {
		return cid;
	}

	public void setOpendate(String s) {
		od = s;
	}

	public String getOpendate() {
		return od;
	}

	public void setEnddate(String s) {
		ed = s;
	}

	public String getEnddate() {
		return ed;
	}

	public void setEdu(String s) {
		edu = s;
	}

	public String getEdu() {
		return edu;
	}

	public void setExp(String s) {
		exp = s;
	}

	public String getExp() {
		return exp;
	}

	public void setNov(String s) {
		nov = s;
	}

	public String getNov() {
		return nov;
	}

	public void setEif(String s) {
		eif = s;
	}

	public String getEif() {
		return eif;
	}

	public void setStatus(String s) {
		this.s = s;
	}

	public String getStatus() {
		return s;
	}

	public void setAssigned(String s) {
		a = s;
	}

	public String getAssigned() {
		return a;
	}

	public void setJobcat(String s) {
		cat = s;
	}

	public String getJobcat() {
		return cat;
	}

	public void setJobdesc(String s) {
		jdesc = s;
	}

	public String getJobdesc() {
		return jdesc;
	}

	DBConnection db = new DBConnection();

	public void updateJob() throws Exception {

		Connection con1 = db.getConnection();
		Statement st1 = con1.createStatement();
		st1.execute("update joborder set JOBID=" + jid + ",CLIENTID=" + cid
				+ ",OPENDATE='" + od + "',ENDDATE='" + ed + "',EDUCATION='"
				+ edu + "',EXPERIENCS='" + exp + "',EXPERIENCEINFIELD='" + eif
				+ "',STATUS='" + s + "',JOBCATEGORY='" + cat
				+ "',NUMBEROFVACANCIES='" + nov + "',ASSIGNED='" + a
				+ "',JOBDESCRIPTION='" + jdesc + "'");
	}

	public void addJob() throws Exception {
		System.out.println("in addjobbean:addjob");
		Connection con2 = db.getConnection();
		Statement st2 = con2.createStatement();
		st2.execute("insert into joborder values(" + jid + "," + cid + ",'"
				+ od + "','" + ed + "','" + edu + "','" + exp + "','" + eif
				+ "','" + s + "','" + cat + "','" + nov + "','" + a + "','"
				+ jdesc + "')");
		System.out.println("in addjobservlet:after insert");
	}

}