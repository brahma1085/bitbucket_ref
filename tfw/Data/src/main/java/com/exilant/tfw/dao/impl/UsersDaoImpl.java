/**
 * 
 */
package com.exilant.tfw.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.exilant.tfw.dao.UsersDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QUsers;
import com.exilant.tfw.pojo.Users;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;

/**
 * @author mohammedfirdos
 *
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for UsersDao interface
 */
public class UsersDaoImpl implements UsersDao {

	/*
	 * The central class in the Querydsl support is the QueryDslJdbcTemplate.
	 * Just like the NamedParameterJdbcTemplate it wraps a regular JdbcTemplate
	 * that you can get access to by calling the getJdbcOperations method. One
	 * thing to note is that when you use the QueryDslJdbcTemplate, there is no
	 * need to specify the SQL dialect to be used since the template will
	 * auto-detect this when it is created.
	 */
	private QueryDslJdbcTemplate template;
	private QUsers qUsers = QUsers.users;

	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(UsersDaoImpl.class);

	/*
	 * You can create a QueryDslJdbcTemplate by passing in a JdbcTemplate or a
	 * DataSource in the constructor.
	 */
	public void setDataSource(DataSource dataSource) {
		this.template = new QueryDslJdbcTemplate(dataSource);
	}

	public class MappingUsersProjection extends MappingProjection<Users> {
		/**
		 * Logger for this class
		 */
		private final Logger LOG = Logger.getLogger(MappingUsersProjection.class);

		/**
		 * Default serial version id
		 */
		private static final long serialVersionUID = 1L;

		public MappingUsersProjection(Expression<?>... args) {
			super(Users.class, args);
		}

		@Override
		protected Users map(Tuple tuple) {
			Users users = new Users();
			users.setUserID(tuple.get(qUsers.userId));
			users.setUsername(tuple.get(qUsers.username));
			users.setPassword(tuple.get(qUsers.password));
			users.setEnabled(tuple.get(qUsers.enabled));
			users.setEmailID(tuple.get(qUsers.emailId));
			users.setPasswordCount(tuple.get(qUsers.passwordCount));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Users object : " + users);
			}
			return users;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 */
	@Override
	public int insertUsersGetKey(final Users users) throws DataAccessException {
		int usersId = 0;
		try {
			usersId = template.insertWithKey(qUsers, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					return insert.columns(qUsers.userId, qUsers.username, qUsers.password, qUsers.enabled, qUsers.emailId,qUsers.passwordCount)
							.values(users.getUserID(), users.getUsername(), users.getPassword(), users.isEnabled(), users.getEmailID(), users.getPasswordCount()).executeWithKey(qUsers.userId);
				}
			});
			LOG.info("Generated usersId : " + usersId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
		return usersId;
	}

	@Override
	public boolean checkAvailability(final String username) throws DataAccessException {
		SQLQuery sqlQuery = template.newSqlQuery().from(qUsers).where(qUsers.username.eq(username));
		LOG.info("generated query : " + sqlQuery);
		return template.notExists(sqlQuery);
	}

	@Override
	public int getUserIdByUserName(String userName) throws DataAccessException {
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qUsers).where(qUsers.username.eq(userName));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, qUsers.userId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

	}

	@Override
	public Users getUsersByName(final String username) throws DataAccessException {
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qUsers).where(qUsers.username.eq(username));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingUsersProjection(qUsers.userId, qUsers.username, qUsers.password, qUsers.enabled, qUsers.emailId, qUsers.passwordCount));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public String getPasswordByUsernameEmailID(String username, String emailID) throws DataAccessException {
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qUsers).where(qUsers.username.eq(username).and(qUsers.emailId.eq(emailID)));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, qUsers.password);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public int getIncorrectPasswordCount(String username) throws DataAccessException {
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qUsers).where(qUsers.username.eq(username));
			LOG.info("generated query : " + sqlQuery);
			System.out.println("DataBase Retrieved Password Count..."+qUsers.passwordCount);
			return template.queryForObject(sqlQuery, qUsers.passwordCount);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}
	
	@Override
	public long updateIncorrectPasswordCount(final String username, final int incorrectPasswordCount) throws DataAccessException {
		try {
			long passwordCount = template.update(qUsers, new SqlUpdateCallback() {
				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					long passwordCount = sqlUpdateClause.where(qUsers.username.eq(username)).set(qUsers.passwordCount, incorrectPasswordCount).execute();
					System.out.println("DataBase Updated.."+passwordCount);
					return passwordCount;
				}
			});
			System.out.println("DataBase Updated..."+passwordCount);
			return passwordCount;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public String getEmailByUsername(String username) throws DataAccessException {
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qUsers).where(qUsers.username.eq(username));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, qUsers.emailId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	
	}

	@Override
	public long updatePasswordByUsername(final String username, final String newPassword) throws DataAccessException {
		try {
			long passwordCount = template.update(qUsers, new SqlUpdateCallback() {
				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					long passwordCount = sqlUpdateClause.where(qUsers.username.eq(username)).set(qUsers.password, newPassword).execute();
					System.out.println("DataBase Updated.."+passwordCount);
					return passwordCount;
				}
			});
			System.out.println("DataBase Updated..."+passwordCount);
			return passwordCount;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
	
	}

	@Override
	public List<Users> getAllUsers() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getApplication() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qUsers);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingUsersProjection(qUsers.userId, qUsers.username, qUsers.password, qUsers.enabled, qUsers.emailId, qUsers.passwordCount));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public Users getUsersByUserID(int userID) throws DataAccessException {
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qUsers).where(qUsers.userId.eq(userID));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingUsersProjection(qUsers.userId,qUsers.username,qUsers.password,qUsers.enabled,qUsers.emailId,qUsers.passwordCount));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public long updateUser(final Users users) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateUser(Users) - Start"); //$NON-NLS-1$
		}
		try {
			long returnlong = template.update(qUsers, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qUsers.userId.eq(users.getUserID())).set(qUsers.username, users.getUsername()).set(qUsers.password, users.getPassword()).set(qUsers.emailId, users.getEmailID()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateUser(Users) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
	}
}