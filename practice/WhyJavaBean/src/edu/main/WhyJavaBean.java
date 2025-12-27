package edu.main;

import edu.dao.StudentDao;

public class WhyJavaBean {
	public static void main(String[] args) {
		String stno = "1";
		String stname = "N@IT";
		String stage = "25";
		String stmarks = "67.5";
		String stqual = "distinction";
		StudentDao stDao = new StudentDao();
		stDao.createStud();
		stDao.insertStud(stno, stname, stage, stmarks, stqual);
	}
}
