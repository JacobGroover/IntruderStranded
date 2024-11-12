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
        commands = new GameplayCommands(Player.getById(1));
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

    @Test
    void help() throws Throwable {
        assertEquals("""
            Hint
            Look
            Exit
            Help
            Save Game
            Load Game
            INV
            TEL
            North
            South
            East
            West""", callExecuteCommand("help"));

        setGameplayCommandsField("isManagingInventory", true);

        assertEquals("""
            Store
            Use <item>
            Discard <item>
            Close
            Exit""", callExecuteCommand("help"));

        setGameplayCommandsField("isManagingInventory", false);
        setGameplayCommandsField("currentPuzzle", new SandPuzzle(1, 1, 1));

        String roomEventHelp = """
            Hint
            Look
            Exit
            Help
            Save Game
            Load Game
            INV
            Flee""";

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
    void getIntroText() {

    }

    @Test
    void teleport() {

    }
}