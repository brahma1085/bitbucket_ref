package edo.factory;

import edu.dao.EmployeeDao;
import edu.dao.StudentDao;

public class DaoFactory {
	private static StudentDao studentDao = new StudentDao();
	private static EmployeeDao employeeDao = new EmployeeDao();

	private DaoFactory() {

	}

	public static StudentDao getStudentDao() {
		return studentDao;
	}

	public static EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

}
