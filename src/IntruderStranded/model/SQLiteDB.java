package IntruderStranded.model;

/**
 * Class: SQLiteDB
 * @author Jacob Groover
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: October 22, 2024
 * This class - creates the wrapper around SQLite-specific initializations.
 */
public class SQLiteDB extends DB {

	/**
	 * One-Argument Constructor for SQLiteDB class.
	 * Builds the connection String for the database:
	 * Initializes the sJdbc field to "jdbc:sqlite"
	 * Initializes the sDriverName field to "org.sqlite.JDBC" which is the name for the JDBC runtime driver.
	 * 
	 * Registers the driver for the database:
	 * Sets the class object referenced by this class to the sDriverName
	 * Initializes the sDbUrl String to the sJdbc field with the input parameter as the name.
	 * Initializes the dbName field to the input parameter.
	 * Calls the getConnection method from the DriverManager class to load the JDBC driver
	 * from the sDbUrl field.
	 * @param dbName
	 */
	SQLiteDB(String dbName) throws java.sql.SQLException, ClassNotFoundException {
		// TODO - implement SQLiteDB.SQLiteDB
		throw new UnsupportedOperationException();
	}

}