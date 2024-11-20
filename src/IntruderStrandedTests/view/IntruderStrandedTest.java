package IntruderStrandedTests.view;

import IntruderStranded.controller.Player;
import IntruderStranded.gameExceptions.GameException;
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
    private static Player player;

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
                () -> assertEquals("\nUsername: \b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.", is.gc.executeCommand("P1")),
                () -> assertEquals("\nUsername: \b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.", is.gc.executeCommand("PASSWORD12345")),
                () -> assertEquals("\nUsername: \b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.\nIf you have forgotten your user account please enter \"Retrieve Username\" to retrieve\n" +
                        "username, or \"Forgot Password\" to reset password.\n", is.gc.executeCommand("PASSWORD1")));
    }

    @Order(3)
    @Test
    void createAccountTest1() {
        assertAll("create account 1",
                () -> assertEquals("\nUsername: \b", is.gc.executeCommand("CREATE ACCOUNT")),
                () -> assertEquals("Username must be between 4 and 10 characters long.\n\nUsername: \b", is.gc.executeCommand("123")),
                () -> assertEquals("Username must be between 4 and 10 characters long.\n\nUsername: \b", is.gc.executeCommand("ELEVEN LONG")),
                () -> assertEquals("Password: \b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Password must be between 8 and 12 characters long.\n\nPassword: \b", is.gc.executeCommand("SEVEN L")),
                () -> assertEquals("Password must be between 8 and 12 characters long.\n\nPassword: \b", is.gc.executeCommand("THIRTEEN LONG")),
                () -> assertEquals("Email: \b", is.gc.executeCommand("1PASSWORD")),
                () -> assertEquals("Email must be 20 characters or less and contain a '.' and a '@'\n\nEmail: \b", is.gc.executeCommand("EMAIL")),
                () -> assertEquals("Email must be 20 characters or less and contain a '.' and a '@'\n\nEmail: \b", is.gc.executeCommand("EMAIL@")),
                () -> assertEquals("Email must be 20 characters or less and contain a '.' and a '@'\n\nEmail: \b", is.gc.executeCommand("EMAIL.")),
                () -> assertEquals("Email must be 20 characters or less and contain a '.' and a '@'\n\nEmail: \b", is.gc.executeCommand("EMAIL IS LONGER THAN 20")),
                () -> assertEquals("Email must be 20 characters or less and contain a '.' and a '@'\n\nEmail: \b", is.gc.executeCommand("EMAIL LONGER THAN 20.")),
                () -> assertEquals("Email must be 20 characters or less and contain a '.' and a '@'\n\nEmail: \b", is.gc.executeCommand("EMAIL LONGER THAN 20@")),
                () -> assertEquals("Email must be 20 characters or less and contain a '.' and a '@'\n\nEmail: \b", is.gc.executeCommand("EMAIL LONGER THAN 20.@")),
                () -> assertEquals("Successfully created account. Please login to continue.\n", is.gc.executeCommand("VALID@EMAIL.COM")));

        assertAll("create account 2",
                () -> assertEquals("\nUsername: \b", is.gc.executeCommand("CREATE ACCOUNT")),
                () -> assertEquals("Password: \b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Email: \b", is.gc.executeCommand("1PASSWORD")),
                () -> assertEquals("Account already exists, please try logging in\n", is.gc.executeCommand("VALID@EMAIL.COM")));

        // Create a 2nd account with the same email as first account to test error handling
        assertAll("create account 3",
                () -> assertEquals("\nUsername: \b", is.gc.executeCommand("CREATE ACCOUNT")),
                () -> assertEquals("Password: \b", is.gc.executeCommand("PLAYER2")),
                () -> assertEquals("Email: \b", is.gc.executeCommand("PASSWORD2")),
                () -> assertEquals("Account already exists, please try logging in\n", is.gc.executeCommand("VALID@EMAIL.COM")));

        // Create a 2nd account with the same username as first account to test error handling
        assertAll("create account 4",
                () -> assertEquals("\nUsername: \b", is.gc.executeCommand("CREATE ACCOUNT")),
                () -> assertEquals("Password: \b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Email: \b", is.gc.executeCommand("PASSWORD2")),
                () -> assertEquals("Account already exists, please try logging in\n", is.gc.executeCommand("VALID2@EMAIL.COM")));

        // Create a 2nd account with same username and email as first account, different password, to test error handling
        assertAll("create account 5",
                () -> assertEquals("\nUsername: \b", is.gc.executeCommand("CREATE ACCOUNT")),
                () -> assertEquals("Password: \b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Email: \b", is.gc.executeCommand("PASSWORD2")),
                () -> assertEquals("Account already exists, please try logging in\n", is.gc.executeCommand("VALID@EMAIL.COM")));

        // Create a valid 2nd account with the same password as first account to test error handling. This one should succeed in creating a second account
        assertAll("create account 6",
                () -> assertEquals("\nUsername: \b", is.gc.executeCommand("CREATE ACCOUNT")),
                () -> assertEquals("Password: \b", is.gc.executeCommand("PLAYER2")),
                () -> assertEquals("Email: \b", is.gc.executeCommand("PASSWORD1")),
                () -> assertEquals("Successfully created account. Please login to continue.\n", is.gc.executeCommand("VALID2@EMAIL.COM")));
    }

    @Test
    @Order(4)
    void loginTest2() {
        assertAll("login 2.0",
                () -> assertEquals("\nUsername: \b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.", is.gc.executeCommand("PASSWORD1")),
                () -> assertEquals("\nUsername: \b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.", is.gc.executeCommand("PASSWORD1")),
                () -> assertEquals("\nUsername: \b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.\nIf you have forgotten your user account please enter \"Retrieve Username\" to retrieve\n" +
                        "username, or \"Forgot Password\" to reset password.\n", is.gc.executeCommand("PASSWORD1")));
    }

    @Test
    @Order(5)
    void forgotPasswordTest1() {
        assertAll("forgot password 1",
                () -> assertEquals("\nPlease Enter Username: \b", is.gc.executeCommand("FORGOT PASSWORD")),
                () -> assertEquals("Username does not exist.", is.gc.executeCommand("A PERSON")));

        assertAll("forgot password 2",
                () -> assertEquals("\nPlease Enter Username: \b", is.gc.executeCommand("FORGOT PASSWORD")),
                () -> assertEquals("Username Found.\nPlease enter new password: \b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Password must be between 8 and 12 characters long.\n\nPassword: \b", is.gc.executeCommand("P1")),
                () -> assertEquals("Password must be between 8 and 12 characters long.\n\nPassword: \b", is.gc.executeCommand("PASSWORD12345")),
                () -> assertEquals("Successfully Reset Password\n", is.gc.executeCommand("PASSWORD1")));
    }

    @Test
    @Order(6)
    void helpTest1() throws Exception {
        assertEquals("""
				Account Management Commands
				
				Login - Enter your username and password
				Create Account - Sign up with username, password, and email
				Forgot Password - Reset user's password
				Retrieve Username - Get user's username with email
				Exit - Exits the application
				Help - This command, displays available commands
				""", is.gc.executeCommand("HELP"));
    }

    @Test
    @Order(7)
    void exitTest1() throws Exception {
        assertEquals("\nExiting Game", is.gc.executeCommand("EXIT"));
    }

    @Test
    @Order(8)
    void loginTest3() {
        // Ensure old password no longer works after being changed
        assertAll("login 3.0",
                () -> assertEquals("\nUsername: \b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.", is.gc.executeCommand("1PASSWORD")),
                () -> assertEquals("\nUsername: \b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.", is.gc.executeCommand("1PASSWORD")),
                () -> assertEquals("\nUsername: \b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Failed. Please Try Again.\nIf you have forgotten your user account please enter \"Retrieve Username\" to retrieve\n" +
                        "username, or \"Forgot Password\" to reset password.\n", is.gc.executeCommand("1PASSWORD")));

        // Successfully login and move to main menu
        assertAll("login 3.1",
                () -> assertEquals("\nUsername: \b", is.gc.executeCommand("LOGIN")),
                () -> assertEquals("Password: \b", is.gc.executeCommand("PLAYER1")),
                () -> assertEquals("Login Successful\n\nIntruder Stranded\n\n" +
                        "Please select an option \"New\" Game or \"Load\" Game\n" +
                        "If you need help. Please enter \"HELP\" to find more commands.\n" +
                        "Please enter \"exit\" to end the game.\n", is.gc.executeCommand("PASSWORD1")));
    }

    @Test
    @Order(9)
    void helpTest2() throws Exception {
        player = Player.getById(1);
        assertEquals("""
				Main Menu Commands
				
				New - Start a new game
				Load - Load a saved game
				Exit - Exits the application
				Help - This command, displays available commands
				""", is.gc.executeCommand("HELP"));
    }

    @Test
    @Order(10)
    void exitTest2() throws Exception {
        assertEquals("\nExiting Game", is.gc.executeCommand("EXIT"));
    }

    @Test
    @Order(11)
    void loadTest1() throws Exception {
        assertEquals("No save has been made.", is.gc.executeCommand("LOAD"));
    }

    @Test
    @Order(12)
    void newGameTest1() throws Exception {
        assertEquals("""
            
            Welcome to Intruder Stranded
            Enter "North", "South", "East", or "West" to move
            Enter "Look" to look at the room
            Enter "Help" for more commands
            
            Cell (Not Visited)
            Current Level: Level -2
            
            An empty cell with somewhat rusty bars, most of them are empty except a few of them filled
            with dangerous monsters""", is.gc.executeCommand("NEW"));
    }

    @Test
    @Order(13)
    void moveTest1() {
        GameException exception = assertThrows(GameException.class, () -> is.gc.executeCommand("WEST"));
        assertEquals("Can't leave room yet", exception.getMessage());
        exception = assertThrows(GameException.class, () -> is.gc.executeCommand("EAST"));
        assertEquals("Can't leave room yet", exception.getMessage());
        exception = assertThrows(GameException.class, () -> is.gc.executeCommand("SOUTH"));
        assertEquals("Can't leave room yet", exception.getMessage());
        exception = assertThrows(GameException.class, () -> is.gc.executeCommand("NORTH"));
        assertEquals("Can't leave room yet", exception.getMessage());
    }

    @Test
    @Order(14)
    void teleportTest1() {
        GameException exception = assertThrows(GameException.class, () -> is.gc.executeCommand("TEL"));
        assertEquals("Invalid command", exception.getMessage());
    }

    @Test
    @Order(15)
    void helpTest3() throws Exception {
        assertEquals("""
            Gameplay Commands
            
            Hint - Display the hint for the current room
            Look - Display the current room
            Exit - Exit to the main menu
            Help - This command, displays available commands
            Save - Creates a save file or overwrites a previous save
            Load - Loads previous saved game
            INV - Open inventory
            North - Move north
            South - Move south
            East - Move east
            West - Move west
            TEL - Access teleportation
            """, is.gc.executeCommand("HELP"));
    }

    @Test
    @Order(16)
    void hintTest1() throws Exception {
        assertEquals("Enter \"look\" to look around", is.gc.executeCommand("HINT"));
    }

    @Test
    @Order(17)
    void exitTest3() throws Exception {
        assertEquals("Do you want to save your game? (yes/no)", is.gc.executeCommand("EXIT"));
        GameException exception = assertThrows(GameException.class, () -> is.gc.executeCommand("maybe"));
        assertEquals("Invalid command", exception.getMessage());
    }

}