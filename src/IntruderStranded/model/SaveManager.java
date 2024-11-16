package IntruderStranded.model;

import IntruderStranded.gameExceptions.GameException;

import java.sql.SQLException;

/**
 * Class: SaveManager
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 12, 2024
 *
 * This class gives the Controller package access to the DB.commitTransaction() method,
 * so that it can save the game.
 */
public class SaveManager {
    /**
     * Method: saveGame
     * Saves the game, making permanent any changes which have occurred since the last
     * save.
     * @throws GameException
     */
    public static void saveGame() throws GameException {
        try {
            DBService.getDB().commitTransaction();
        } catch (SQLException exception) {
            throw new GameException(exception.getMessage());
        }
    }

    /**
     * Method: rollbackGame
     * Rolls back any changes which have occurred since the last save.
     * @throws GameException
     */
    public static void rollbackGame() throws GameException {
        try {
            DBService.getDB().rollbackTransaction();
        } catch (SQLException exception) {
            throw new GameException(exception.getMessage());
        }
    }
}
