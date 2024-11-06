package IntruderStrandedTests.model;

import IntruderStranded.model.SQLiteDB;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DBNonAutoCommitTest extends DBAutoCommitTest {
    @BeforeAll
    static void setUp() throws SQLException, IOException {
        Files.deleteIfExists(Path.of("test.db"));
        db = new SQLiteDB("test.db", false);
        db.update("CREATE TABLE test_table (test_int INT PRIMARY KEY, test_string VARCHAR(255), test_bool INT)");
    }

    @Test
    @Order(1)
    void rollbackTransaction() throws SQLException {
        db.rollbackTransaction();

        ResultSet resultSet = db.query("SELECT name FROM sqlite_master WHERE type='table' AND name='test_table'");
        assertFalse(resultSet.next());
        resultSet.getStatement().close();

        db.update("CREATE TABLE test_table (test_int INT PRIMARY KEY, test_string VARCHAR(255), test_bool INT)");
    }

    @Test
    @Order(2)
    void commitTransaction() throws SQLException {
        update();
        updatePrepared();
        db.commitTransaction();
        db.rollbackTransaction();
        query();
        db.update("DELETE FROM test_table");
    }

    @Test
    void close() throws SQLException {
        db.close();
        db = new SQLiteDB("test.db", false);
    }
}
