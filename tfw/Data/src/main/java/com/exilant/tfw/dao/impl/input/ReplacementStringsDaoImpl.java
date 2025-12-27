package com.exilant.tfw.dao.impl.input;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.exilant.tfw.dao.input.ReplacementStringsDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QApplication;
import com.exilant.tfw.generated.pojo.QReplacementStrings;
import com.exilant.tfw.pojo.input.ReplacementStrings;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;

/**
 * The Class ReplacementStringsDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for ReplacementStringsDao
 * interface
 */
public class ReplacementStringsDaoImpl implements ReplacementStringsDao {

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

	/** The q replacement strings. */
	private QReplacementStrings qReplacementStrings = QReplacementStrings.replacementStrings;

	/** The q application. */
	private QApplication qApplication = QApplication.application;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(ReplacementStringsDaoImpl.class);

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
	 * The Class MappingReplacement_StringsProjection.
	 */
	public class MappingReplacement_StringsProjection extends MappingProjection<ReplacementStrings> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping replacement_ strings projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingReplacement_StringsProjection(Expression<?>... args) {
			super(ReplacementStrings.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected ReplacementStrings map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			ReplacementStrings replacement_Strings = new ReplacementStrings();
			replacement_Strings.setID(tuple.get(qReplacementStrings.id));
			replacement_Strings.setAppID(tuple.get(qReplacementStrings.appID));
			replacement_Strings.setLevel(tuple.get(qReplacementStrings.level));
			replacement_Strings.setForeignID(tuple.get(qReplacementStrings.foreignID));
			replacement_Strings.setName(tuple.get(qReplacementStrings.name));
			replacement_Strings.setValue(tuple.get(qReplacementStrings.value));
			replacement_Strings.setEncrypted(tuple.get(qReplacementStrings.encrypted));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Replacement_Strings object : " + replacement_Strings);
			}
			return replacement_Strings;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param replacementStrings
	 *            the replacement strings
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertReplacementStrings(final ReplacementStrings replacementStrings) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertReplacementStrings(ReplacementStrings) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in replacement_Strings: " + replacementStrings);
		try {
			result = template.insert(qReplacementStrings, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause
							.columns(qReplacementStrings.id, qReplacementStrings.appID, qReplacementStrings.level, qReplacementStrings.foreignID,
									qReplacementStrings.name, qReplacementStrings.value, qReplacementStrings.encrypted)
							.values(replacementStrings.getID(), replacementStrings.getAppID(), replacementStrings.getLevel(),
									replacementStrings.getForeignID(), replacementStrings.getName(), replacementStrings.getValue(),
									replacementStrings.getEncrypted()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in ConditionGroup");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertReplacementStrings(ReplacementStrings) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param replacementStrings
	 *            the replacement strings
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateReplacementStrings(final ReplacementStrings replacementStrings) throws DataAccessException {
		LOG.info("Started updating data in Replacement_Strings: " + replacementStrings);
		try {
			long returnlong = template.update(qReplacementStrings, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qReplacementStrings.id.eq(replacementStrings.getID()))
							.set(qReplacementStrings.value, replacementStrings.getValue()).set(qReplacementStrings.name, replacementStrings.getName())
							.set(qReplacementStrings.level, replacementStrings.getLevel())
							.set(qReplacementStrings.foreignID, replacementStrings.getForeignID())
							.set(qReplacementStrings.encrypted, replacementStrings.getEncrypted()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateReplacementStrings(ReplacementStrings) - end"); //$NON-NLS-1$
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
	 * @see
	 * com.exilant.tfw.dao.input.ReplacementStringsDao#getReplacementStrings()
	 */
	@Override
	public List<ReplacementStrings> getReplacementStrings() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getReplacementStrings() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qReplacementStrings).where(qReplacementStrings.appID.eq(qApplication.appID));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingReplacement_StringsProjection(qReplacementStrings.id, qReplacementStrings.appID,
					qReplacementStrings.level, qReplacementStrings.foreignID, qReplacementStrings.name, qReplacementStrings.value,
					qReplacementStrings.encrypted));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.ReplacementStringsDao#getReplacementStringsById
	 * (int)
	 */
	@Override
	public ReplacementStrings getReplacementStringsById(int replacementId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getReplacementStringsById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qReplacementStrings).where(qReplacementStrings.id.eq(replacementId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingReplacement_StringsProjection(qReplacementStrings.id, qReplacementStrings.appID,
					qReplacementStrings.level, qReplacementStrings.foreignID, qReplacementStrings.name, qReplacementStrings.value,
					qReplacementStrings.encrypted));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.ReplacementStringsDao#
	 * insertReplacementStringsGetKey
	 * (com.exilant.tfw.pojo.input.ReplacementStrings)
	 */
	@Override
	public int insertReplacementStringsGetKey(final ReplacementStrings replacementStrings) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertReplacementStringsGetKey(ReplacementStrings) - start"); //$NON-NLS-1$
		}

		int replacementStringsId = 0;
		try {
			replacementStringsId = template.insertWithKey(qReplacementStrings, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert
							.columns(qReplacementStrings.id, qReplacementStrings.appID, qReplacementStrings.level, qReplacementStrings.foreignID,
									qReplacementStrings.name, qReplacementStrings.value, qReplacementStrings.encrypted)
							.values(replacementStrings.getID(), replacementStrings.getAppID(), replacementStrings.getLevel(),
									replacementStrings.getForeignID(), replacementStrings.getValue(), replacementStrings.getEncrypted())
							.executeWithKey(qReplacementStrings.id);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated replacementStringsId : " + replacementStringsId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertReplacementStringsGetKey(ReplacementStrings) - end"); //$NON-NLS-1$
		}
		return replacementStringsId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.ReplacementStringsDao#getReplacementStringsByAppID
	 * (int)
	 */
	@Override
	public List<ReplacementStrings> getReplacementStringsByAppID(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getReplacementStringsByAppID(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qReplacementStrings).where(qReplacementStrings.appID.eq(appId));
			LOG.info("ReplacementStrings query : " + sqlQuery);
			return template.query(sqlQuery, new MappingReplacement_StringsProjection(qReplacementStrings.id, qReplacementStrings.appID,
					qReplacementStrings.level, qReplacementStrings.foreignID, qReplacementStrings.name, qReplacementStrings.value,
					qReplacementStrings.encrypted));

		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

}