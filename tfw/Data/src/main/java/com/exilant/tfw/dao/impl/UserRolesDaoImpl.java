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

import com.exilant.tfw.dao.UserRolesDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QUserRoles;
import com.exilant.tfw.pojo.UserRoles;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
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
 * This implementation class provides implementation for UserRolesDao interface
 */
public class UserRolesDaoImpl implements UserRolesDao {

	/*
	 * The central class in the Querydsl support is the QueryDslJdbcTemplate.
	 * Just like the NamedParameterJdbcTemplate it wraps a regular JdbcTemplate
	 * that you can get access to by calling the getJdbcOperations method. One
	 * thing to note is that when you use the QueryDslJdbcTemplate, there is no
	 * need to specify the SQL dialect to be used since the template will
	 * auto-detect this when it is created.
	 */
	private QueryDslJdbcTemplate template;
	private QUserRoles qUserRoles = QUserRoles.userRoles;

	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(UserRolesDaoImpl.class);

	/*
	 * You can create a QueryDslJdbcTemplate by passing in a JdbcTemplate or a
	 * DataSource in the constructor.
	 */
	public void setDataSource(DataSource dataSource) {
		this.template = new QueryDslJdbcTemplate(dataSource);
	}

	public class MappingUserRolesProjection extends MappingProjection<UserRoles> {
		/**
		 * Logger for this class
		 */
		private final Logger LOG = Logger.getLogger(MappingUserRolesProjection.class);

		/**
		 * Default serial version id
		 */
		private static final long serialVersionUID = 1L;

		public MappingUserRolesProjection(Expression<?>... args) {
			super(UserRoles.class, args);
		}

		@Override
		protected UserRoles map(Tuple tuple) {
			UserRoles userRoles = new UserRoles();
			userRoles.setUserRolesID(tuple.get(qUserRoles.userRoleId));
			userRoles.setUserID(tuple.get(qUserRoles.userId));
			userRoles.setAuthority(tuple.get(qUserRoles.authority));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning UserRoles object : " + userRoles);
			}
			return userRoles;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 */
	@Override
	public int insertUserRolesGetKey(final UserRoles userRoles) throws DataAccessException {
		int userRolesId = 0;
		try {
			userRolesId = template.insertWithKey(qUserRoles, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					return insert.columns(qUserRoles.userRoleId, qUserRoles.userId, qUserRoles.authority)
							.values(userRoles.getUserRolesID(), userRoles.getUserID(), userRoles.getAuthority()).executeWithKey(qUserRoles.userRoleId);
				}
			});
			LOG.info("Generated userRolesId : " + userRolesId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
		return userRolesId;
	}

	@Override
	public List<String> getUserRoleById(final int userId) throws DataAccessException {
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qUserRoles).where(qUserRoles.userId.eq(userId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, qUserRoles.authority);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public List<String> getUserRolesFilterByUserId(final int userId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUserRolesFilterByUserId(int) - start"); //$NON-NLS-1$
		}
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qUserRoles).where(qUserRoles.userId.eq(userId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, qUserRoles.authority);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}
	
	@Override
	public int getUserIdByRole(String roleName) throws DataAccessException {
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qUserRoles).where(qUserRoles.authority.eq(roleName));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, qUserRoles.userId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

	
	}

}