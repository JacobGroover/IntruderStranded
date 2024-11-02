package IntruderStranded.model;

import java.sql.SQLException;

/**
 * Class: SQLiteDB
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 1, 2024
 * This class creates the wrapper around SQLite-specific initialization.
 */
public class SQLiteDB extends DB {
	/**
	 * Constructs an <code>SQLiteDB</code>.
	 * @param dbFilePath The path to the SQLite database file.
	 * @param autoCommit Whether to enable auto-commit mode or not.
	 * @throws SQLException If an error occurs while connecting to the database.
	 */
	public SQLiteDB(String dbFilePath, boolean autoCommit) throws SQLException {
		super("jdbc:sqlite:" + dbFilePath, autoCommit);
	}
}