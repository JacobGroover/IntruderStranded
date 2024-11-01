package IntruderStranded.model;

import java.sql.Connection;

/**
 * Class: DB
 * @author Jacob Groover
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: October 22, 2024
 * This class - Handles basic database functionality, including query and update methods.
 */
public abstract class DB {

	protected String dbName = "src/IntruderStranded/IntruderStranded.db";
	protected String sJdbc;
	protected String sDriverName;
	protected Connection conn;
	protected String sDbUrl;
	protected int timeout = 5;

	/**
	 * Method: queryDB
	 * Reads from the database, returning a ResultSet
	 * @param sql
	 */
	protected java.sql.ResultSet queryDB(String sql) throws java.sql.SQLException {
		// TODO - implement DB.queryDB
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: updateDB
	 * Updates the database, returning true if the first result is a ResultSet object; false if it is an
	 * update count or there are no results.
	 * @param SQL
	 */
	protected boolean updateDB(String SQL) throws java.sql.SQLException {
		// TODO - implement DB.updateDB
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: count
	 * Gets the count of records in the specified table from the database
	 * @param table
	 */
	protected int count(String table) throws java.sql.SQLException {
		// TODO - implement DB.count
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: getMaxValue
	 * Gets the max value for a specific field in a specific table from the database.
	 * @param columnName
	 * @param table
	 */
	protected int getMaxValue(String columnName, String table) throws java.sql.SQLException {
		// TODO - implement DB.getMaxValue
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: close
	 * Closes the database connection.
	 */
	protected void close() throws java.sql.SQLException {
		// TODO - implement DB.close
		throw new UnsupportedOperationException();
	}

}