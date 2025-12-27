package com.exilant.tfw.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.exilant.tfw.dao.ActionsDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QActions;
import com.exilant.tfw.pojo.Actions;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;

/**
 * The Class ActionsDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for ActionDao interface
 */
public class ActionsDaoImpl implements ActionsDao {

	/*
	 * The central class in the Querydsl support is the QueryDslJdbcTemplate.
	 * Just like the NamedParameterJdbcTemplate it wraps a regular JdbcTemplate
	 * that you can get access to by calling the getJdbcOperations method. One
	 * thing to note is that when you use the QueryDslJdbcTemplate, there is no
	 * need to specify the SQL dialect to be used since the template will
	 * auto-detect this when it is created.
	 */
	/** The template. */
	private QueryDslJdbcTemplate template;

	/** The q action. */
	private QActions qAction = QActions.actions;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(ActionsDaoImpl.class);

	/*
	 * You can create a QueryDslJdbcTemplate by passing in a JdbcTemplate or a
	 * DataSource in the constructor.
	 */
	/**
	 * Sets the data source.
	 * 
	 * @param dataSource
	 *            the new data source
	 */
	public void setDataSource(DataSource dataSource) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("setDataSource(DataSource) - start"); //$NON-NLS-1$
		}

		this.template = new QueryDslJdbcTemplate(dataSource);

		if (LOG.isDebugEnabled()) {
			LOG.debug("setDataSource(DataSource) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * The Class MappingActionsProjection.
	 */
	public class MappingActionsProjection extends MappingProjection<Actions> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping actions projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingActionsProjection(Expression<?>... args) {
			super(Actions.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected Actions map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			Actions actions = new Actions();
			actions.setActionID(tuple.get(qAction.actionID));
			actions.setActionName(tuple.get(qAction.actionName));
			actions.setDescription(tuple.get(qAction.description));
			actions.setCreatedBy(tuple.get(qAction.createdBy));
			actions.setCreatedDateTime(tuple.get(qAction.createdDateTime));
			actions.setUpdatedBy(tuple.get(qAction.updatedBy));
			actions.setUpdatedDateTime(tuple.get(qAction.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Actions object : " + actions);
			}
			return actions;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param actions
	 *            the actions
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertActions(final Actions actions) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertActions(Actions) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in Actions: " + actions);
		try {
			result = template.insert(qAction, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qAction.actionID, qAction.actionName, qAction.description, qAction.createdBy, qAction.createdDateTime, qAction.updatedBy, qAction.updatedDateTime).values(actions.getActionID(), actions.getActionName(), actions.getDescription(), actions.getCreatedBy(), actions.getCreatedDateTime(), actions.getUpdatedBy(), actions.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in Actions");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertActions(Actions) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param actions
	 *            the actions
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateActions(final Actions actions) throws DataAccessException {
		LOG.info("Started updating data in Actions: " + actions);
		try {
			long returnlong = template.update(qAction, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qAction.actionID.eq(actions.getActionID())).set(qAction.description, actions.getDescription()).set(qAction.updatedBy, actions.getUpdatedBy()).set(qAction.updatedDateTime, actions.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateActions(Actions) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.ActionsDao#getActions()
	 */
	@Override
	public List<Actions> getActions() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getActions() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qAction);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingActionsProjection(qAction.actionID, qAction.actionName, qAction.description, qAction.createdBy,
					qAction.createdDateTime, qAction.updatedBy, qAction.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.ActionsDao#getActionsById(int)
	 */
	@Override
	public Actions getActionsById(int actionsId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getActionsById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qAction).where(qAction.actionID.eq(actionsId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingActionsProjection(qAction.actionID, qAction.actionName, qAction.description,
					qAction.createdBy, qAction.createdDateTime, qAction.updatedBy, qAction.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.ActionsDao#getActionsIdByName(java.lang.String)
	 */
	@Override
	public int getActionsIdByName(String actionName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getActionsIdByName(String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qAction).where(qAction.actionName.eq(actionName));
			Integer actionId = template.queryForObject(sqlQuery, qAction.actionID);
			if (actionId != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getActionsIdByName(String) - end"); //$NON-NLS-1$
				}
				return actionId;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getActionsIdByName(String) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.ActionsDao#insertActionsGetKey(com.exilant.tfw.pojo
	 * .Actions)
	 */
	@Override
	public int insertActionsGetKey(final Actions actions) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertActionsGetKey(Actions) - start"); //$NON-NLS-1$
		}

		int actionsId = 0;
		try {
			actionsId = template.insertWithKey(qAction, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qAction.actionID, qAction.actionName, qAction.description, qAction.createdBy, qAction.createdDateTime, qAction.updatedBy, qAction.updatedDateTime).values(actions.getActionID(), actions.getActionName(), actions.getDescription(), actions.getCreatedBy(), actions.getCreatedDateTime(), actions.getUpdatedBy(), actions.getUpdatedDateTime()).executeWithKey(qAction.actionID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated actions Id : " + actionsId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertActionsGetKey(Actions) - end"); //$NON-NLS-1$
		}
		return actionsId;
	}

}
