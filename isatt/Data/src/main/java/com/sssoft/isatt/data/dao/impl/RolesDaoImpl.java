/**
 * 
 */
package com.sssoft.isatt.data.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;
import com.sssoft.isatt.data.dao.RolesDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QRoles;
import com.sssoft.isatt.data.pojo.Roles;

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
 * This implementation class provides implementation for RolesDao interface
 */
public class RolesDaoImpl implements RolesDao {

	/*
	 * The central class in the Querydsl support is the QueryDslJdbcTemplate.
	 * Just like the NamedParameterJdbcTemplate it wraps a regular JdbcTemplate
	 * that you can get access to by calling the getJdbcOperations method. One
	 * thing to note is that when you use the QueryDslJdbcTemplate, there is no
	 * need to specify the SQL dialect to be used since the template will
	 * auto-detect this when it is created.
	 */
	private QueryDslJdbcTemplate template;
	private QRoles qRoles = QRoles.roles;

	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(RolesDaoImpl.class);

	/*
	 * You can create a QueryDslJdbcTemplate by passing in a JdbcTemplate or a
	 * DataSource in the constructor.
	 */
	public void setDataSource(DataSource dataSource) {
		this.template = new QueryDslJdbcTemplate(dataSource);
	}

	public class MappingRolesProjection extends MappingProjection<Roles> {
		/**
		 * Logger for this class
		 */
		private final Logger LOG = Logger.getLogger(MappingRolesProjection.class);

		/**
		 * Default serial version id
		 */
		private static final long serialVersionUID = 1L;

		public MappingRolesProjection(Expression<?>... args) {
			super(Roles.class, args);
		}

		@Override
		protected Roles map(Tuple tuple) {
			Roles roles = new Roles();
			roles.setRoleID(tuple.get(qRoles.roleID));
			roles.setAuthority(tuple.get(qRoles.authority));
			roles.setRolesDescription(tuple.get(qRoles.rolesDescription));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Roles object : " + roles);
			}
			return roles;
		}
	}

	public List<Roles> getRoles() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getApplication() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qRoles);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingRolesProjection(qRoles.roleID,qRoles.authority,qRoles.rolesDescription));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}


	@Override
	public int insertRolesGetKey(final Roles roles) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertRolesGetKey(Roles) - start"); //$NON-NLS-1$
		}
		int roleId = 0;
		try {
			roleId = template.insertWithKey(qRoles,
					new SqlInsertWithKeyCallback<Integer>() {
						@Override
						public Integer doInSqlInsertWithKeyClause(
								SQLInsertClause insert) throws SQLException {
							if (LOG.isDebugEnabled()) {
								LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
							}

							Integer returnInteger = insert
									.columns(qRoles.roleID, qRoles.authority, qRoles.rolesDescription)
									.values(roles.getRoleID(), roles.getAuthority(), roles.getRolesDescription())
									.executeWithKey(qRoles.roleID);
							if (LOG.isDebugEnabled()) {
								LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
							}
							return returnInteger;
						}
					});
			LOG.info("Genrated roleId : " + roleId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScreenGetKey(Screen) - end"); //$NON-NLS-1$
		}
		return roleId;
	}

	@Override
	public Roles getRoleByID(int roleID) throws DataAccessException {
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qRoles).where(qRoles.roleID.eq(roleID));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingRolesProjection(qRoles.roleID,qRoles.authority,qRoles.rolesDescription));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}


	@Override
	public long updateRole(final Roles roles) throws DataAccessException {
		try {
			long returnlong = template.update(qRoles, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qRoles.roleID.eq(roles.getRoleID())).set(qRoles.authority, roles.getAuthority()).set(qRoles.rolesDescription, roles.getRolesDescription()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateRole(Roles) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
	}
}