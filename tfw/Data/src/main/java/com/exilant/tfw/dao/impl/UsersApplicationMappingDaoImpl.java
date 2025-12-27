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

import com.exilant.tfw.dao.UsersApplicationMappingDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QUsersapplicationmapping;
import com.exilant.tfw.pojo.UsersApplicationMapping;
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
 * This implementation class provides implementation for
 * UsersApplicationMappingDao interface
 */
public class UsersApplicationMappingDaoImpl implements UsersApplicationMappingDao {

	/*
	 * The central class in the Querydsl support is the QueryDslJdbcTemplate.
	 * Just like the NamedParameterJdbcTemplate it wraps a regular JdbcTemplate
	 * that you can get access to by calling the getJdbcOperations method. One
	 * thing to note is that when you use the QueryDslJdbcTemplate, there is no
	 * need to specify the SQL dialect to be used since the template will
	 * auto-detect this when it is created.
	 */
	private QueryDslJdbcTemplate template;
	private QUsersapplicationmapping qUsersApplicationMapping = QUsersapplicationmapping.usersapplicationmapping;

	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(UsersApplicationMappingDaoImpl.class);

	/*
	 * You can create a QueryDslJdbcTemplate by passing in a JdbcTemplate or a
	 * DataSource in the constructor.
	 */
	public void setDataSource(DataSource dataSource) {
		this.template = new QueryDslJdbcTemplate(dataSource);
	}

	public class MappingUsersApplicationMappingProjection extends MappingProjection<UsersApplicationMapping> {
		/**
		 * Logger for this class
		 */
		private final Logger LOG = Logger.getLogger(MappingUsersApplicationMappingProjection.class);

		/**
		 * Default serial version id
		 */
		private static final long serialVersionUID = 1L;

		public MappingUsersApplicationMappingProjection(Expression<?>... args) {
			super(UsersApplicationMapping.class, args);
		}

		@Override
		protected UsersApplicationMapping map(Tuple tuple) {
			UsersApplicationMapping usersApplicationMapping = new UsersApplicationMapping();
			usersApplicationMapping.setUsersApplicationMappingID(tuple.get(qUsersApplicationMapping.usersApplicationMappingID));
			usersApplicationMapping.setUserID(tuple.get(qUsersApplicationMapping.userId));
			usersApplicationMapping.setAppID(tuple.get(qUsersApplicationMapping.appID));
			usersApplicationMapping.setAuthority(tuple.get(qUsersApplicationMapping.authority));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning UsersApplicationMapping object : " + usersApplicationMapping);
			}
			return usersApplicationMapping;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 */
	@Override
	public int insertUsersApplicationMappingGetKey(final UsersApplicationMapping usersApplicationMapping) throws DataAccessException {
		int usersApplicationMappingId = 0;
		try {
			usersApplicationMappingId = template.insertWithKey(qUsersApplicationMapping, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					return insert
							.columns(qUsersApplicationMapping.usersApplicationMappingID, qUsersApplicationMapping.userId, qUsersApplicationMapping.appID,
									qUsersApplicationMapping.authority)
							.values(usersApplicationMapping.getUsersApplicationMappingID(), usersApplicationMapping.getUserID(),
									usersApplicationMapping.getAppID(), usersApplicationMapping.getAuthority())
							.executeWithKey(qUsersApplicationMapping.usersApplicationMappingID);
				}
			});
			LOG.info("Generated usersApplicationMappingId : " + usersApplicationMappingId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
		return usersApplicationMappingId;

	}

	@Override
	public List<UsersApplicationMapping> getApplicationByUserId(final int userId) throws DataAccessException {
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qUsersApplicationMapping).where(qUsersApplicationMapping.userId.eq(userId));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingUsersApplicationMappingProjection(qUsersApplicationMapping.usersApplicationMappingID,
					qUsersApplicationMapping.userId, qUsersApplicationMapping.appID, qUsersApplicationMapping.authority));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

	}

	@Override
	public List<Integer> getApplicationsByRoleAndUserID(final int userID, final String authority)
			throws DataAccessException {
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qUsersApplicationMapping).where(qUsersApplicationMapping.userId.eq(userID).and(qUsersApplicationMapping.authority.eq(authority)));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, qUsersApplicationMapping.appID);
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
		
	}

}