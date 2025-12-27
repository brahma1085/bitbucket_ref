package com.sssoft.isatt.data.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;

import com.sssoft.isatt.utils.bean.UtlConf;

/**
 * This class is used to instantiate the data source using the properties file.
 * Pooled connection creation, closing implementation is provided with this
 * class.
 */
public class DBUtils {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(DBUtils.class);

	/** The main props. */
	private static Properties mainProps;
	
	/** The ds obj. */
	private static volatile DataSource dsObj;

	static {
		initialize();
	}

	/**
	 * Method to initialize the properties and the datasource by using the
	 * database properties configured in user defined file.
	 */
	private static void initialize() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("initialize() - start"); //$NON-NLS-1$
		}

		String filePath = UtlConf.getConfigFilePath();
		try {
			InputStream is = new FileInputStream(filePath);
			mainProps = new Properties();
			mainProps.load(is);
			dsObj = BasicDataSourceFactory.createDataSource(mainProps);
		} catch (FileNotFoundException e) {
			LOG.error("initialize()", e); //$NON-NLS-1$
		} catch (IOException e) {
			LOG.error("initialize()", e); //$NON-NLS-1$
		} catch (Exception e) {
			LOG.error("initialize()", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("initialize() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Method to get the pooled connection.
	 * 
	 * @return connection
	 */
	public static Connection getConnection() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConnection() - start"); //$NON-NLS-1$
		}

		Connection connection = null;
		if (null == dsObj) {
			synchronized (DBUtils.class) {
				if (null == dsObj) {
					initialize();
				}
			}
		}
		try {
			connection = dsObj.getConnection();
		} catch (SQLException e) {
			LOG.error("getConnection()", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getConnection() - end"); //$NON-NLS-1$
		}
		return connection;
	}

	/**
	 * Method to get the data-source instance.
	 *
	 * @return DataSource
	 */
	public static DataSource getDataSource() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getDataSource() - start"); //$NON-NLS-1$
		}

		if (null == dsObj) {
			synchronized (DBUtils.class) {
				if (null == dsObj) {
					initialize();
				}
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getDataSource() - end"); //$NON-NLS-1$
		}
		return dsObj;
	}

	/**
	 * Method to close the connection, Statement and Result Set.
	 *
	 * @param conection the conection
	 * @param statement the statement
	 * @param resultset the resultset
	 */
	public static void closeConnection(Connection conection, Statement statement, ResultSet resultset) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("closeConnection(Connection, Statement, ResultSet) - start"); //$NON-NLS-1$
		}

		if (null != resultset) {
			DbUtils.closeQuietly(resultset);
		}
		if (null != statement) {
			DbUtils.closeQuietly(statement);
		}
		if (null != conection) {
			DbUtils.closeQuietly(conection);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("closeConnection(Connection, Statement, ResultSet) - end"); //$NON-NLS-1$
		}
	}
}
