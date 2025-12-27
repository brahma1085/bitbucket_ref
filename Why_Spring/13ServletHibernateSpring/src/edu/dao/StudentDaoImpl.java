package edu.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.model.Student001;

public class StudentDaoImpl extends HibernateDaoSupport implements StudentDao {

	public void insertStudent(Student001 student001) {
		getSession().save(student001);
	}

}
