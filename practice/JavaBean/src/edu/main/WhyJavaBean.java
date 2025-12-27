package edu.main;

import edu.dao.StudentDao;
import edu.pojo.Stud;

public class WhyJavaBean {
	public static void main(String[] args) {
		Stud st = new Stud();
		st.setStdNo("2");
		st.setStdName("N@IT");
		st.setStdAge("26");
		st.setStdMarks("72.5");
		st.setStdQual("MCA");
		StudentDao stDao = new StudentDao();
		stDao.insertStudent(st);
	}
}
