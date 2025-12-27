package edu.generator;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class StudentNoGenerator implements IdentifierGenerator {

	public Serializable generate(SessionImplementor arg0, Object arg1)
			throws HibernateException {
		String studentNo = "HYD000";
		Statement statement = null;
		ResultSet resultSet = null;
		String studentNoSeqQuery = "SELECT TO_CHAR(STUDENTNO_SEQ.NEXTVAL, 'FM000') FROM DUAL";
		try {
			statement = arg0.getBatcher().prepareSelectStatement(
					studentNoSeqQuery);
			resultSet = statement.executeQuery(studentNoSeqQuery);
			if (resultSet != null && resultSet.next()) {
				studentNo = studentNo + "" + resultSet.getString(1);
			}
		} catch (SQLException e) {
			System.err.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		}
		return studentNo;
	}
}
