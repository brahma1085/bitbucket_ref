package com.sssoft.isatt.data.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;
import com.sssoft.isatt.data.dao.AgentDetailsDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QAgentdetails;
import com.sssoft.isatt.data.pojo.AgentDetails;

/**
 * The Class AgentDetailsDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for AgentDetailsDao
 * interface
 */
public class AgentDetailsDaoImpl implements AgentDetailsDao {

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

	/** The q agent details. */
	private QAgentdetails qAgentDetails = QAgentdetails.agentdetails;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AgentDetailsDaoImpl.class);

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
	 * The Class MappingAgentDetailsProjection.
	 */
	public class MappingAgentDetailsProjection extends MappingProjection<AgentDetails> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping agent details projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingAgentDetailsProjection(Expression<?>... args) {
			super(AgentDetails.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected AgentDetails map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			AgentDetails agentDetails = new AgentDetails();
			agentDetails.setAgentID(tuple.get(qAgentDetails.agentID));
			agentDetails.setAgentName(tuple.get(qAgentDetails.agentName));
			agentDetails.setIP(tuple.get(qAgentDetails.ip));
			agentDetails.setPort(tuple.get(qAgentDetails.port));
			agentDetails.setMachineDetails(tuple.get(qAgentDetails.machineDetails));
			agentDetails.setProtocol(tuple.get(qAgentDetails.protocol));
			agentDetails.setStatus(Boolean.parseBoolean(tuple.get(qAgentDetails.status)));
			agentDetails.setCreatedBy(tuple.get(qAgentDetails.createdBy));
			agentDetails.setCreatedDateTime(tuple.get(qAgentDetails.createdDateTime));
			agentDetails.setUpdatedBy(tuple.get(qAgentDetails.updatedBy));
			agentDetails.setUpdatedDateTime(tuple.get(qAgentDetails.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Actions object : " + agentDetails);
			}
			return agentDetails;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param agentDetails
	 *            the agent details
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertAgentDetails(final AgentDetails agentDetails) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAgentDetails(AgentDetails) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in AgentDetails: " + agentDetails);
		try {
			result = template.insert(qAgentDetails, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qAgentDetails.agentID, qAgentDetails.agentName, qAgentDetails.ip, qAgentDetails.port, qAgentDetails.machineDetails, qAgentDetails.protocol, qAgentDetails.status, qAgentDetails.createdBy, qAgentDetails.createdDateTime, qAgentDetails.updatedBy, qAgentDetails.updatedDateTime).values(agentDetails.getAgentID(), agentDetails.getAgentName(), agentDetails.getIP(), agentDetails.getPort(), agentDetails.getMachineDetails(), agentDetails.getProtocol(), agentDetails.isStatus(), agentDetails.getCreatedBy(), agentDetails.getCreatedDateTime(), agentDetails.getUpdatedBy(), agentDetails.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in AgentDetails");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAgentDetails(AgentDetails) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param agentDetails
	 *            the agent details
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateAgentDetails(final AgentDetails agentDetails) throws DataAccessException {
		LOG.info("Started updating data in AgentDetails: " + agentDetails);
		try {
			long returnlong = template.update(qAgentDetails, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qAgentDetails.agentID.eq(agentDetails.getAgentID())).set(qAgentDetails.ip, agentDetails.getIP()).set(qAgentDetails.port, agentDetails.getPort()).set(qAgentDetails.machineDetails, agentDetails.getMachineDetails()).set(qAgentDetails.protocol, agentDetails.getProtocol()).set(qAgentDetails.updatedBy, agentDetails.getUpdatedBy()).set(qAgentDetails.status, String.valueOf(agentDetails.isStatus())).set(qAgentDetails.updatedDateTime, agentDetails.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateAgentDetails(AgentDetails) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.AgentDetailsDao#getAgentDetails()
	 */
	@Override
	public List<AgentDetails> getAgentDetails() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAgentDetails() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qAgentDetails);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingAgentDetailsProjection(qAgentDetails.agentID, qAgentDetails.agentName, qAgentDetails.ip,
					qAgentDetails.port, qAgentDetails.machineDetails, qAgentDetails.protocol, qAgentDetails.status, qAgentDetails.createdBy,
					qAgentDetails.createdDateTime, qAgentDetails.updatedBy, qAgentDetails.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.AgentDetailsDao#getAgentDetailsById(int)
	 */
	@Override
	public AgentDetails getAgentDetailsById(int agentId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAgentDetailsById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qAgentDetails).where(qAgentDetails.agentID.eq(agentId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingAgentDetailsProjection(qAgentDetails.agentID, qAgentDetails.agentName, qAgentDetails.ip,
					qAgentDetails.port, qAgentDetails.machineDetails, qAgentDetails.protocol, qAgentDetails.status, qAgentDetails.createdBy,
					qAgentDetails.createdDateTime, qAgentDetails.updatedBy, qAgentDetails.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.AgentDetailsDao#insertAgentDetailsGetKey(com.exilant
	 * .tfw.pojo.AgentDetails)
	 */
	@Override
	public int insertAgentDetailsGetKey(final AgentDetails agentDetails) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAgentDetailsGetKey(AgentDetails) - start"); //$NON-NLS-1$
		}

		int agentDetailsId = 0;
		try {
			agentDetailsId = template.insertWithKey(qAgentDetails, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qAgentDetails.agentID, qAgentDetails.agentName, qAgentDetails.ip, qAgentDetails.port, qAgentDetails.machineDetails, qAgentDetails.protocol, qAgentDetails.status, qAgentDetails.createdBy, qAgentDetails.createdDateTime, qAgentDetails.updatedBy, qAgentDetails.updatedDateTime).values(agentDetails.getAgentID(), agentDetails.getAgentName(), agentDetails.getIP(), agentDetails.getPort(), agentDetails.getMachineDetails(), agentDetails.getProtocol(), agentDetails.isStatus(), agentDetails.getCreatedBy(), agentDetails.getCreatedDateTime(), agentDetails.getUpdatedBy(), agentDetails.getUpdatedDateTime()).executeWithKey(qAgentDetails.agentID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated agentDetailsId : " + agentDetailsId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAgentDetailsGetKey(AgentDetails) - end"); //$NON-NLS-1$
		}
		return agentDetailsId;
	}

}
