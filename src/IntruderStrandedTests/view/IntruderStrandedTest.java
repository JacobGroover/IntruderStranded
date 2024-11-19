package IntruderStrandedTests.view;

import IntruderStranded.model.*;
import IntruderStranded.view.IntruderStranded;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntruderStrandedTest {

    private static DB db;
    private static IntruderStranded is;

    @BeforeAll
    public static void setup() throws Exception {
        Files.deleteIfExists(Path.of("test.db"));
        db = new SQLiteDB("test.db", false);
        DBService.start(db);
        GameDBCreate gameDBCreate = new GameDBCreate();
        gameDBCreate.buildTables();
        is = new IntruderStranded();
    }

    @AfterAll
    static void tearDown() throws Exception {
        db.close();
        Files.deleteIfExists(Path.of("test.db"));
    }

    @Test
    @Order(1)
    void displayFirstIntroText() {
        assertEquals("""
                Intruder Stranded
                
                Please enter the command "Login" or "Create Account"
                Forgot Password "Forgot Password"
                Forgot Username "Retrieve Username"
                If you need help. Please enter "HELP" to find more commands.
                """, is.gc.displayIntroText());
    }

    @Test
    @Order(2)
    void loginTest1() {
        assertAll("login 1.0",
                () -> assertEquals("\nUsername: \\b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \\b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.", is.gc.executeCommand("P1")),
                () -> assertEquals("\nUsername: \\b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \\b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.", is.gc.executeCommand("PASSWORD12345")),
                () -> assertEquals("\nUsername: \\b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \\b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.\nIf you have forgotten your user account please enter \"Retrieve Username\" to retrieve\n" +
                        "username, or \"Forgot Password\" to reset password.\n", is.gc.executeCommand("PASSWORD1")));
    }

    @Order(3)
    @Test
    void createAccountTest1() {
        assertAll("create account 1",
                () -> assertEquals("\nUsername: \\b", is.gc.executeCommand("CREATE ACCOUNT")),
                () -> assertEquals("Username must be between 4 and 10 characters long.\n\nUsername: \\b", is.gc.executeCommand("123")),
                () -> assertEquals("Username must be between 4 and 10 characters long.\n\nUsername: \\b", is.gc.executeCommand("ELEVEN LONG")),
                () -> assertEquals("Password: \\b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Password must be between 8 and 12 characters long.\n\nPassword: \\b", is.gc.executeCommand("SEVEN L")),
                () -> assertEquals("Password must be between 8 and 12 characters long.\n\nPassword: \\b", is.gc.executeCommand("THIRTEEN LONG")),
                () -> assertEquals("Email: \\b", is.gc.executeCommand("1PASSWORD")),
                () -> assertEquals("Email must be 20 characters or less and contain a '.' and a '@'\n\nEmail: \\b", is.gc.executeCommand("EMAIL")),
                () -> assertEquals("Email must be 20 characters or less and contain a '.' and a '@'\n\nEmail: \\b", is.gc.executeCommand("EMAIL@")),
                () -> assertEquals("Email must be 20 characters or less and contain a '.' and a '@'\n\nEmail: \\b", is.gc.executeCommand("EMAIL.")),
                () -> assertEquals("Email must be 20 characters or less and contain a '.' and a '@'\n\nEmail: \\b", is.gc.executeCommand("EMAIL IS LONGER THAN 20")),
                () -> assertEquals("Email must be 20 characters or less and contain a '.' and a '@'\n\nEmail: \\b", is.gc.executeCommand("EMAIL LONGER THAN 20.")),
                () -> assertEquals("Email must be 20 characters or less and contain a '.' and a '@'\n\nEmail: \\b", is.gc.executeCommand("EMAIL LONGER THAN 20@")),
                () -> assertEquals("Email must be 20 characters or less and contain a '.' and a '@'\n\nEmail: \\b", is.gc.executeCommand("EMAIL LONGER THAN 20.@")),
                () -> assertEquals("Successfully created account. Please login to continue.\n", is.gc.executeCommand("VALID@EMAIL.COM")));

        assertAll("create account 2",
                () -> assertEquals("\nUsername: \\b", is.gc.executeCommand("CREATE ACCOUNT")),
                () -> assertEquals("Password: \\b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Email: \\b", is.gc.executeCommand("1PASSWORD")),
                () -> assertEquals("Account already exists, please try logging in\n", is.gc.executeCommand("VALID@EMAIL.COM")));

        // Create a 2nd account with the same email as first account to test error handling
        assertAll("create account 3",
                () -> assertEquals("\nUsername: \\b", is.gc.executeCommand("CREATE ACCOUNT")),
                () -> assertEquals("Password: \\b", is.gc.executeCommand("PLAYER2")),
                () -> assertEquals("Email: \\b", is.gc.executeCommand("PASSWORD2")),
                () -> assertEquals("Account already exists, please try logging in\n", is.gc.executeCommand("VALID@EMAIL.COM")));

        // Create a 2nd account with the same username as first account to test error handling
        assertAll("create account 4",
                () -> assertEquals("\nUsername: \\b", is.gc.executeCommand("CREATE ACCOUNT")),
                () -> assertEquals("Password: \\b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Email: \\b", is.gc.executeCommand("PASSWORD2")),
                () -> assertEquals("Account already exists, please try logging in\n", is.gc.executeCommand("VALID2@EMAIL.COM")));

        // Create a 2nd account with same username and email as first account, different password, to test error handling
        assertAll("create account 5",
                () -> assertEquals("\nUsername: \\b", is.gc.executeCommand("CREATE ACCOUNT")),
                () -> assertEquals("Password: \\b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Email: \\b", is.gc.executeCommand("PASSWORD2")),
                () -> assertEquals("Account already exists, please try logging in\n", is.gc.executeCommand("VALID@EMAIL.COM")));

        // Create a valid 2nd account with the same password as first account to test error handling. This one should succeed in creating a second account
        assertAll("create account 6",
                () -> assertEquals("\nUsername: \\b", is.gc.executeCommand("CREATE ACCOUNT")),
                () -> assertEquals("Password: \\b", is.gc.executeCommand("PLAYER2")),
                () -> assertEquals("Email: \\b", is.gc.executeCommand("PASSWORD1")),
                () -> assertEquals("Successfully created account. Please login to continue.\n", is.gc.executeCommand("VALID2@EMAIL.COM")));
    }

    @Test
    @Order(4)
    void loginTest2() {
        assertAll("login 2.0",
                () -> assertEquals("\nUsername: \\b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \\b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.", is.gc.executeCommand("PASSWORD1")),
                () -> assertEquals("\nUsername: \\b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \\b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.", is.gc.executeCommand("PASSWORD1")),
                () -> assertEquals("\nUsername: \\b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \\b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.\nIf you have forgotten your user account please enter \"Retrieve Username\" to retrieve\n" +
                        "username, or \"Forgot Password\" to reset password.\n", is.gc.executeCommand("PASSWORD1")));
    }

    @Test
    @Order(5)
    void forgotPasswordTest1() throws Exception {
        assertAll("forgot password 1",
                () -> assertEquals("\nPlease Enter Username: \\b", is.gc.executeCommand("FORGOT PASSWORD")),
                () -> assertEquals("Username does not exist.", is.gc.executeCommand("A PERSON")));

        assertAll("forgot password 2",
                () -> assertEquals("\nPlease Enter Username: \\b", is.gc.executeCommand("FORGOT PASSWORD")),
                () -> assertEquals("Username Found.\nPlease enter new password: \\b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Password must be between 8 and 12 characters long.\n\nPassword: \\b", is.gc.executeCommand("P1")),
                () -> assertEquals("Password must be between 8 and 12 characters long.\n\nPassword: \\b", is.gc.executeCommand("PASSWORD12345")),
                () -> assertEquals("Successfully Reset Password\n", is.gc.executeCommand("PASSWORD1")));
    }

    @Test
    @Order(6)
    void loginTest3() {
        // Ensure old password no longer works after being changed
        assertAll("login 3.0",
                () -> assertEquals("\nUsername: \\b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \\b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.", is.gc.executeCommand("1PASSWORD")),
                () -> assertEquals("\nUsername: \\b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \\b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.", is.gc.executeCommand("1PASSWORD")),
                () -> assertEquals("\nUsername: \\b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \\b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.\nIf you have forgotten your user account please enter \"Retrieve Username\" to retrieve\n" +
                        "username, or \"Forgot Password\" to reset password.\n", is.gc.executeCommand("1PASSWORD")));

        // Successfully login and move to main menu
        assertAll("login 3.1",
                () -> assertEquals("\nUsername: \\b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \\b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Successful\n\nIntruder Stranded\n\n" +
                        "Please select an option \"New\" Game or \"Load\" Game\n" +
                        "If you need help. Please enter \"HELP\" to find more commands.\n" +
                        "Please enter \"exit\" to end the game.\n", is.gc.executeCommand("PASSWORD1")));
    }

}