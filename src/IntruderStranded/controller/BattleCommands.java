package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;
import IntruderStranded.model.GameDBCreate;

import java.util.ArrayList;
import java.util.List;

/**
 * Class: BattleCommands
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 15, 2024
 *
 * This class is the GameplayCommands subclass for battle commands. Handles all user commands sent
 * from GameController and returns appropriate replies or exceptions.
 * Relevant while the player is in a monster fight.
 */
public class BattleCommands extends GameplayCommands {
    private final GameplayCommands source;
    private final List<Monster> originalMonsters;
    private final List<Monster> monsters;
    private Monster currentMonster;
    private boolean restartPrompted;
    private final int battleStartHealth;
    private static final String ACTION_PROMPT = """
            
            What would you like to do?
            "ATTACK", USE "ITEM", "DEFEND", "FLEE\"""";

    /**
     * Creates a new BattleCommands object.
     * @param source The GameplayCommands object creating the battle, which will
     * be returned to once the battle is over.
     * @param monsters The monsters in the battle.
     */
    public BattleCommands(GameplayCommands source, List<Monster> monsters) {
        super(source.player);
        this.source = source;
        this.monsters = new ArrayList<>(monsters);
        this.originalMonsters = new ArrayList<>(monsters);
        this.currentMonster = monsters.getFirst();
        battleStartHealth = player.getHealth();
    }

    /**
     * Method: executeCommand
     * Executes a command, returning the string to display or throwing an exception in response.
     * @param command The command entered by the user.
     * @return The string to display.
     */
    @Override
    String executeCommand(String command) throws GameException {
        if (isExiting) {
            return exit(command);
        } else if (restartPrompted) {
            restart(command);
            return "";
        } else if (isManagingInventory) {
            return inventory(command);
        } else return switch (command) {
            case "ATTACK" -> attack();
            case "DEFEND" -> defend();
            case "SAVE" -> saveGame();
            case "LOAD" -> loadGame();
            case "ITEM" -> inventory(command);
            case "EXIT" -> exit(command);
            case "HELP" -> help();
            case "FLEE" -> flee();
            default -> throw new GameException("Invalid command");
        };
    }

    /**
     * Method: restart
     * Handles restarting a battle.
     * @param command The command entered by the user.
     */
    private void restart(String command) throws GameException {
        if (command.equals("YES")) {
            changeGameState(new BattleCommands(source, originalMonsters));
        } else if (command.equals("NO")) {
            GameDBCreate gameDBCreate = new GameDBCreate();

            if (gameDBCreate.gameExists(player.getID())) {
                loadGame();
            } else {
                gameDBCreate.newGame(player.getID());
                changeGameState(new GameplayCommands(player));
            }
        } else {
            throw new GameException("Please enter yes or no.");
        }
    }

    /**
     * Method: help
     * Returns the help text for battle commands.
     * @return The help string.
     */
    @Override
    String help() {
        return """
            Battle Commands
            
            Exit - Exit to the main menu
            Help - This command, displays available commands
            Save - Save the game
            Load - Load a save
            Item - To view user's inventory
            Flee - Flee from combat
            Attack - Attack the current monster
            Defend - Reduces damage
            """;
    }

    /**
     * Method: getBattleInfo
     * Gets the current state of the battle.
     * @return The string to display.
     */
    private String getBattleInfo() {
        return "\nYour STATS: " + player.getStatus()
                + "\n" + currentMonster.getName() + " STATS: " + currentMonster.getStatus();
    }

    /**
     * Method: useItem
     * Uses an item during a battle.
     * @param item The item to use.
     * @return The string to display.
     */
    @Override
    protected String useItem(Item item) throws GameException {
        if (item.getConsumableType() == ConsumableType.FREEZING_POTION) {
            player.removeItem(item);
            currentMonster.freeze();
            return "You used the " + item.getItemName();
        }

        return player.useItem(item);
    }

    /**
     * Method: onInventoryClose
     * Gets text to display after the player closes their inventory.
     * @return The string to display.
     */
    @Override
    protected String onInventoryClose() {
        return getBattleInfo() + ACTION_PROMPT;
    }

    /**
     * Method: attack
     * Handles attacking the monster and damage calculations.
     * @return The string to display.
     */
    private String attack() throws GameException {
        final boolean monsterImmune = currentMonster.getName().equalsIgnoreCase("Slime") && (player.getEquippedWeapon() == null || !player.getEquippedWeapon().getItemName().equalsIgnoreCase("Flame Knife"));
        final boolean finalBoss = currentMonster.getName().equalsIgnoreCase("Boss: Supreme Alien Commander");
        String attackText = monsterImmune ? "The monster is immune to your attack!\n" : "You landed a hit!\n";

        if (!monsterImmune) {
            currentMonster.setHealth(Math.max(currentMonster.getHealth() - player.getDamage(), 0));
        }

        if (currentMonster.getHealth() <= 0) {
            monsters.remove(currentMonster);
            currentMonster.delete();

            int scoreGained = player.getHealth() <= battleStartHealth / 2 ? 5 : 10;
            if (finalBoss) {
                scoreGained += 20;
            }
            player.addScore(scoreGained);

            String display = "You charged on " + currentMonster.getName() + "!\n"
                    + attackText + getBattleInfo()
                    + "\n\nYou have defeated " + currentMonster.getName()
                    + "\n(+" + scoreGained + " score) New Score: " + player.getScore() + "\n";

            if (monsters.isEmpty()) {
                String output = source.setRewards(currentMonster.getRewards());
                source.reloadCurrentRoom();
                changeGameState(source);
                if (finalBoss) {
                    display += "\nYou have beaten the final boss and won the game!\nScore: " + player.getScore();
                }
                else if (currentMonster.getRewards().isEmpty()) {
                    display += "\n" + player.getCurrentRoom().display(player);
                } else {
                    display += output;
                }
                return display;
            } else {
                currentMonster = monsters.getFirst();
                display += currentMonster.getName() + " is blocking your path.\n";
                display += getBattleInfo() + ACTION_PROMPT;
            }

            return display;
        }

        if (currentMonster.isFrozen()) {
            currentMonster.tickFreeze();
            return "You charged on " + currentMonster.getName() + "!\n"
                    + attackText + currentMonster.getName() + " is frozen, it couldn't attack you!\n"
                    + getBattleInfo() + ACTION_PROMPT;
        }

        player.setHealth(player.getHealth() - currentMonster.getDamage());

        if (player.getHealth() <= 0) {
            return onPlayerLose();
        }

        return "You charged on " + currentMonster.getName() + "!\n"
                + attackText + currentMonster.getName() + " attacked you!\n"
                + getBattleInfo() + ACTION_PROMPT;
    }

    /**
     * Method: defend
     * Handles defending from the monster and damage calculations.
     * @return The string to display.
     */
    private String defend() {
        player.setHealth(player.getHealth() - currentMonster.getDamage() / 2);

        if (player.getHealth() <= 0) {
            return onPlayerLose();
        }

        return currentMonster.getName() + " attacked you!\n" + getBattleInfo();
    }

    /**
     * Method: onPlayerLose
     * Handles the player losing.
     * @return The string to display.
     */
    private String onPlayerLose() {
        restartPrompted = true;
        return "You have been defeated\nWould you like to restart?";
    }

    /**
     * Method: flee
     * Handles fleeing from this battle into the player's previous room.
     * @return The string to display.
     */
    @Override
    protected String flee() throws GameException {
        player.getCurrentRoom().getRoomEvents().addFirst(currentMonster);
        changeGameState(source);
        return moveTo(player.getPreviousRoom());
    }

    /**
     * Method: getIntroText
     * Gets the text to show when the battle starts.
     * @return The string to display.
     */
    @Override
    protected String getIntroText() {
        return getBattleInfo() + ACTION_PROMPT;
    }
}
