package IntruderStranded.model;

import java.sql.*;

/**
 * Class: DB
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 1, 2024
 * This class handles basic database functionality, including the query and update methods.
 * It also supports committing and rolling back transactions and using prepared statements.
 */
public abstract class DB implements AutoCloseable {
	private final Connection connection;
	private static final int TIMEOUT = 3;

	/**
	 * Constructs a <code>DB</code>.
	 * @param connectionString The JDBC database url to connect to.
	 * @param autoCommit Whether to enable auto-commit mode or not.
	 * @throws SQLException If an error occurs while connecting to the database.
	 */
	public DB(String connectionString, boolean autoCommit) throws SQLException {
		connection = DriverManager.getConnection(connectionString);
		connection.setAutoCommit(autoCommit);
	}

	/**
	 * Method Name: commitTransaction
	 * Makes all changes to the database since the last transaction permanent.
	 * This method should only be called if auto-commit mode is disabled.
	 * @throws SQLException If a database error occurs.
	 */
	public void commitTransaction() throws SQLException {
		connection.commit();
	}

	/**
	 * Method Name: rollbackTransaction
	 * Undoes all changes to the database since the last transaction.
	 * This method should only be called if auto-commit mode is disabled.
	 * @throws SQLException If a database error occurs.
	 */
	public void rollbackTransaction() throws SQLException {
		connection.rollback();
	}

	/**
	 * Method Name: query
	 * Executes the given SQL query and returns the generated <code>ResultSet</code>.
	 * The caller of this method is expected to close the returned <code>ResultSet</code>'s
	 * underlying <code>Statement</code> by calling <code>resultSet.getStatement().close()</code>.
	 * @param sql The SQL string to execute.
	 * @return The <code>ResultSet</code> which contains the data returned by the query.
	 * @throws SQLException If a database error occurs.
	 */
	public ResultSet query(String sql) throws SQLException {
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(TIMEOUT);
		return statement.executeQuery(sql);
	}

	/**
	 * Method Name: queryPrepared
	 * Executes the given SQL query with the given parameters and returns the generated
	 * <code>ResultSet</code>. The caller of this method is expected to close the returned
	 * <code>ResultSet</code>'s underlying <code>Statement</code> by calling
	 * <code>resultSet.getStatement().close()</code>.
	 * @param sql The SQL string to execute.
	 * @param parameters The parameters to use.
	 * @return The <code>ResultSet</code> which contains the data returned by the query.
	 * @throws SQLException If a database error occurs.
	 */
	public ResultSet queryPrepared(String sql, Object... parameters) throws SQLException {
		return createPreparedStatement(sql, parameters).executeQuery();
	}

	/**
	 * Method Name: update
	 * Executes the given SQL statement and returns the number of rows affected.
	 * @param sql The SQL string to execute.
	 * @return The number of rows affected.
	 * @throws SQLException If a database error occurs.
	 */
	public int update(String sql) throws SQLException {
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(TIMEOUT);
		int rowCount = statement.executeUpdate(sql);
		statement.close();
		return rowCount;
	}

	/**
	 * Method Name: updatePrepared
	 * Executes the given SQL statement and returns the number of rows affected.
	 * @param sql The SQL string to execute.
	 * @param parameters The parameters to use.
	 * @return The number of rows affected.
	 * @throws SQLException If a database error occurs.
	 */
	public int updatePrepared(String sql, Object... parameters) throws SQLException {
		PreparedStatement statement = createPreparedStatement(sql, parameters);
		int rowCount = statement.executeUpdate();
		statement.close();
		return rowCount;
	}

	/**
	 * Method Name: close
	 * Closes this <code>DB</code> by closing its underlying connection object. If auto-commit
	 * mode is off, the current transaction is rolled back before the connection to the database
	 * is closed.
	 * @throws SQLException If a database error occurs.
	 */
	@Override
	public void close() throws SQLException {
		if (!connection.getAutoCommit()) {
			connection.rollback();
		}

		connection.close();
	}

	/**
	 * Method Name: isClosed
	 * Checks if this <code>DB</code> has been closed.
	 * @return True if this <code>DB</code>'s underlying connection has been closed, otherwise false.
	 * @throws SQLException If a database error occurs.
	 */
	public boolean isClosed() throws SQLException {
		return connection.isClosed();
	}

	/**
	 * Method Name: createPreparedStatement
	 * Creates a <code>PreparedStatement</code> from an SQL string and a list of parameters.
	 * @param sql The SQL string to use.
	 * @param parameters The parameters to use.
	 * @return The <code>PreparedStatement</code> with the given SQL string and parameters.
	 * @throws SQLException If a database error occurs.
	 */
	private PreparedStatement createPreparedStatement(String sql, Object... parameters) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setQueryTimeout(TIMEOUT);

		for (int i = 0; i < parameters.length; i++) {
			statement.setObject(i + 1, parameters[i]);
		}

		return statement;
	}
}