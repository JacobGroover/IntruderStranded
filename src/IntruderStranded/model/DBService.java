package IntruderStranded.model;

import java.sql.SQLException;

/**
 * Class: DBService
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 2, 2024
 * This class is a singleton which provides global access to a database object.
 * This class cannot be instantiated or extended.
 */
public final class DBService {
    private static DB db;

    private DBService() {}

    /**
     * Method: start
     * Starts the service with the given <code>DB</code>.
     * @param db The <code>DB</code> to use.
     */
    public static void start(DB db) throws SQLException {
        if (DBService.db != null && !DBService.db.isClosed()) {
            throw new IllegalStateException("DBService has already been started.");
        }

        if (db == null) {
            throw new IllegalArgumentException("Argument db cannot be null.");
        }

        DBService.db = db;
    }

    /**
     * Method: getDB
     * Gets the current <code>DB</code>.
     * @return The current <code>DB</code>.
     */
    static DB getDB() {
        return db;
    }
}
