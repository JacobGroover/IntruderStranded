package IntruderStrandedTests.controller;

import IntruderStranded.controller.GameplayCommands;
import IntruderStranded.controller.Monster;
import IntruderStranded.controller.Player;
import IntruderStranded.controller.SandPuzzle;
import IntruderStranded.model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

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

    private String callExecuteCommand(String command) throws Exception {
        Class<? extends GameplayCommands> gcClass = commands.getClass();
        Method method = gcClass.getDeclaredMethod("executeCommand", String.class);
        method.setAccessible(true);
        return (String) method.invoke(commands, command);
    }

    private <T> void setGameplayCommandsField(String fieldName, T value) throws Exception {
        Field field = commands.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(commands, value);
    }

    @Test
    void help() throws Exception {
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
            West""", callExecuteCommand("HELP"));

        setGameplayCommandsField("isManagingInventory", true);

        assertEquals("""
            Store
            Use <item>
            Discard <item>
            Close
            Exit""", callExecuteCommand("HELP"));

        setGameplayCommandsField("isManagingInventory", false);
        setGameplayCommandsField("currentPuzzle", new SandPuzzle(1));

        String roomEventHelp = """
            Hint
            Look
            Exit
            Help
            Save Game
            Load Game
            INV
            Flee""";

        assertEquals(roomEventHelp, callExecuteCommand("HELP"));

        setGameplayCommandsField("currentPuzzle", null);
        setGameplayCommandsField("currentMonster", new Monster(1));
        assertEquals(roomEventHelp, callExecuteCommand("HELP"));
    }

    @Test
    void exit() throws Exception {

    }

    @Test
    void getIntroText() {

    }

    @Test
    void teleport() {

    }
}