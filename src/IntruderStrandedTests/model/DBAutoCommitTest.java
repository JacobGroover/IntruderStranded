package IntruderStrandedTests.model;

import IntruderStranded.model.DB;
import IntruderStranded.model.SQLiteDB;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

class DBAutoCommitTest {
    protected static DB db;

    @BeforeAll
    static void setUp() throws IOException, SQLException {
        Files.deleteIfExists(Path.of("test.db"));
        db = new SQLiteDB("test.db", true);
        db.update("CREATE TABLE test_table (test_int INT PRIMARY KEY, test_string VARCHAR(255), test_bool INT)");
    }

    @AfterAll
    static void tearDown() throws IOException, SQLException {
        db.close();
        Files.deleteIfExists(Path.of("test.db"));
    }

    @Test
    void update() throws SQLException {
        int rowCount = db.update("INSERT INTO test_table (test_int, test_string, test_bool) VALUES (3, 'Test String', 1)");
        assertEquals(rowCount, 1);
        rowCount = db.update("INSERT INTO test_table (test_int, test_string, test_bool) VALUES (44, 'Test Data', 0), (11, 'Test 2', 1)");
        assertEquals(rowCount, 2);
        rowCount = db.update("UPDATE test_table SET test_bool = 1 WHERE test_bool = 1");
        assertEquals(rowCount, 2);
    }

    @Test
    void updatePrepared() throws SQLException {
        int rowCount = db.updatePrepared("INSERT INTO test_table (test_int, test_string, test_bool) VALUES (?, ?, ?)", 4, "Test String", true);
        assertEquals(rowCount, 1);
        rowCount = db.updatePrepared("INSERT INTO test_table (test_int, test_string, test_bool) VALUES (?, ?, ?), (?, ?, ?)", 45, "Test Data", false, 12, "Test 2", true);
        assertEquals(rowCount, 2);
        rowCount = db.updatePrepared("UPDATE test_table SET test_bool = ? WHERE test_bool = ?", true, true);
        assertEquals(rowCount, 4);
    }

    @Test
    void commitTransaction() throws SQLException {
        assertThrows(SQLException.class, db::commitTransaction);
    }

    @Test
    void rollbackTransaction() throws SQLException {
        assertThrows(SQLException.class, db::rollbackTransaction);
    }

    @Test
    void query() throws SQLException {
        ResultSet resultSet = db.query("SELECT * FROM test_table");
        testResultSet(resultSet);
    }

    @Test
    void queryPrepared() throws SQLException {
        ResultSet resultSet = db.queryPrepared("SELECT * FROM test_table WHERE 'test' = ? AND 3 = ? AND true = ?", "test", 3, true);
        testResultSet(resultSet);
    }

    private void testResultSet(ResultSet resultSet) throws SQLException {
        int[] expectedInts = {3, 44, 11, 4, 45, 12};
        String[] expectedStrings = {"Test String", "Test Data", "Test 2", "Test String", "Test Data", "Test 2"};
        boolean[] expectedBools = {true, false, true, true, false, true};

        int i = 0;
        while (resultSet.next()) {
            assertEquals(expectedInts[i], resultSet.getInt("test_int"));
            assertEquals(expectedStrings[i], resultSet.getString("test_string"));
            assertEquals(expectedBools[i], resultSet.getBoolean("test_bool"));
            i++;
        }

        resultSet.getStatement().close();
    }

    @Test
    void close() throws SQLException {
        db.close();
        db = new SQLiteDB("test.db", true);
    }
}