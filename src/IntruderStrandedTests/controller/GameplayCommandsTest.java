package IntruderStrandedTests.controller;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;
import IntruderStranded.model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameplayCommandsTest {
    private static GameplayCommands commands;
    private static DB db;
    private static Player player;

    @BeforeAll
    static void setUp() throws Exception {
        Files.deleteIfExists(Path.of("test.db"));
        db = new SQLiteDB("test.db", false);
        DBService.start(db);
        GameDBCreate gameDBCreate = new GameDBCreate();
        gameDBCreate.buildTables();
        PlayerDB playerDB = new PlayerDB();
        playerDB.addPlayer("Test User", "", "");
    }

    @BeforeEach
    void beforeEach() throws Exception {
        player = Player.getById(1);
        commands = new GameplayCommands(player);
    }

    @AfterAll
    static void tearDown() throws Exception {
        db.close();
        Files.deleteIfExists(Path.of("test.db"));
    }

    private <T> T callGameplayCommandsMethod(String methodName, Object... parameters) throws Throwable {
        Class<? extends GameplayCommands> gcClass = commands.getClass();
        Method method = gcClass.getDeclaredMethod(methodName, Arrays.stream(parameters).map(Object::getClass).toArray(Class[]::new));
        method.setAccessible(true);

        try {
            return (T) method.invoke(commands, parameters);
        } catch (InvocationTargetException exception) {
            throw exception.getCause();
        }
    }

    private <T> void setGameplayCommandsField(String fieldName, T value) throws Exception {
        Field field = commands.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(commands, value);
    }

    private String callExecuteCommand(String command) throws Throwable {
        return callGameplayCommandsMethod("executeCommand", command.toUpperCase());
    }

    private String callGetCommandArgument(String command) throws Throwable {
        return callGameplayCommandsMethod("getCommandArgument", command.toUpperCase());
    }

    @Test
    void help() throws Throwable {
        assertEquals("""
            Gameplay Commands
            
            Hint - Get a hint about the current monster/puzzle
            Look - Print the room description again
            Exit - Exit to the main menu
            Help - This command, displays available commands
            Save Game - Save the game
            Load Game - Load a save
            INV - Open inventory
            North - Move north
            South - Move south
            East - Move east
            West - Move west
            TEL - Teleport
            """, callExecuteCommand("help"));

        player.setCurrentRoom(Room.getById(2, player.getID()));

        assertEquals("""
            Gameplay Commands
            
            Hint - Get a hint about the current monster/puzzle
            Look - Print the room description again
            Exit - Exit to the main menu
            Help - This command, displays available commands
            Save Game - Save the game
            Load Game - Load a save
            INV - Open inventory
            North - Move north
            South - Move south
            East - Move east
            West - Move west
            """, callExecuteCommand("help"));

        setGameplayCommandsField("isManagingInventory", true);

        assertEquals("""
            Inventory Commands
            
            Store <item> - Pick up an item from the room
            Use <item> - Use an item in your inventory
            Discard <item> - Discard an item in your inventory
            Close - Close the inventory menu
            Exit - Exit to the main menu
            Help - This command, displays available commands
            """, callExecuteCommand("help"));

        setGameplayCommandsField("isManagingInventory", false);
        setGameplayCommandsField("currentPuzzle", new SandPuzzle(1, 1, 1));

        String roomEventHelp = """
            Gameplay Commands
            
            Hint - Get a hint about the current monster/puzzle
            Look - Print the room description again
            Exit - Exit to the main menu
            Help - This command, displays available commands
            Save Game - Save the game
            Load Game - Load a save
            INV - Open inventory
            Flee - Flee from the current monster/puzzle
            """;

        assertEquals(roomEventHelp, callExecuteCommand("help"));

        setGameplayCommandsField("currentPuzzle", null);
        setGameplayCommandsField("currentMonster", new Monster(1, 1, 1));
        assertEquals(roomEventHelp, callExecuteCommand("help"));
    }

    @Test
    void exit() throws Throwable {
        assertEquals("Do you want to save your game?", callExecuteCommand("exit"));
        assertThrows(GameException.class, () -> callExecuteCommand(""));

        assertEquals("Do you want to save your game?", callExecuteCommand("exit"));
        assertThrows(GameException.class, () -> callExecuteCommand("aaaaaaaa"));

        for (String command : List.of("yes", "y", "no", "n")) {
            assertEquals("Do you want to save your game?", callExecuteCommand("exit"));
            assertEquals("", callExecuteCommand(command));
            beforeEach();
        }
    }

    @Test
    void getCommandArgument() throws Throwable {
        assertEquals("ITEM", callGetCommandArgument("use item"));
        assertEquals("ITEM 2", callGetCommandArgument("use item 2"));
        assertEquals(" THIS", callGetCommandArgument("use  this"));
        assertEquals(" THIS  ", callGetCommandArgument("use  this  "));
        assertEquals("ITEM TEST 2 ARG", callGetCommandArgument("use Item test 2 Arg"));

        assertThrows(GameException.class, () -> callGetCommandArgument("use"));
        assertThrows(GameException.class, () -> callGetCommandArgument("use "));
        assertThrows(GameException.class, () -> callGetCommandArgument("use  "));

        assertThrows(IllegalArgumentException.class, () -> callGetCommandArgument(""));
        assertThrows(IllegalArgumentException.class, () -> callGetCommandArgument(" "));
    }

    @Test
    void teleport() {

    }
}