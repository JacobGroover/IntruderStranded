package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;

import java.util.List;

public class BattleCommands extends GameplayCommands {
    private final GameplayCommands source;
    private final List<Monster> monsters;
    private Monster currentMonster;
    private static final String ACTION_PROMPT = """
            
            What would you like to do?
            "ATTACK", "USE ITEM", "DEFEND", "FLEE\"""";

    /**
     * Creates a new BattleCommands object.
     * @param source The GameplayCommands object creating the battle, which will
     * be returned to once the battle is over.
     * @param monsters The monsters in the battle.
     */
    public BattleCommands(GameplayCommands source, List<Monster> monsters) {
        super(source.player);
        this.source = source;
        this.monsters = monsters;
        this.currentMonster = monsters.getFirst();
    }

    @Override
    String executeCommand(String command) throws GameException {
        return switch (command) {
            case "ATTACK" -> attack();
            case "DEFEND" -> defend();
            case "SAVE" -> saveGame();
            case "LOAD" -> loadGame();
            case "INV" -> inventory(command);
            case "EXIT" -> exit(command);
            case "HELP" -> help();
            case "FLEE" -> flee();
            default -> throw new GameException("Invalid command");
        };
    }

    @Override
    String help() {
        return """
            Battle Commands
            
            Exit - Exit to the main menu
            Help - This command, displays available commands
            Save - Save the game
            Load - Load a save
            INV - To view user's inventory
            Flee - Flee from combat
            Attack - Attack the current monster
            Defend - Reduces damage
            """;
    }

    private String getBattleInfo() {
        return "\nYour STATS: " + player.getStatus()
                + "\n" + currentMonster.getName() + " STATS: " + currentMonster.getStatus();
    }

    @Override
    protected String useItem(Item item) throws GameException {
        if (item.getConsumableType() == ConsumableType.FREEZING_POTION) {
            currentMonster.freeze();
            return "You used the " + item.getItemName();
        }

        return player.useItem(item);
    }

    private String attack() throws GameException {
        currentMonster.setHealth(currentMonster.getHealth() - player.getDamage());

        if (currentMonster.getHealth() <= 0) {
            monsters.remove(currentMonster);
            currentMonster.delete();

            String display = "You charged on " + currentMonster.getName() + "!\n"
                    + "You landed a hit!\n" + getBattleInfo()
                    + "\n\nYou have defeated " + currentMonster.getName();

            if (monsters.isEmpty()) {
                source.setRewards(currentMonster.getRewards());
                changeGameState(source);
                return display;
            } else {
                currentMonster = monsters.getFirst();
                display += "\n\n" + currentMonster.getName() + " is blocking your path.";
                display += getBattleInfo() + ACTION_PROMPT;
            }

            return display;
        }

        if (currentMonster.isFrozen()) {
            currentMonster.tickFreeze();
            return "You charged on " + currentMonster.getName() + "!\n"
                    + currentMonster.getName() + " is frozen, it couldn't attack you!"
                    + getBattleInfo() + ACTION_PROMPT;
        }

        player.setHealth(player.getHealth() - currentMonster.getDamage());

        if (player.getHealth() <= 0) {
            return onPlayerLose();
        }

        return "You charged on " + currentMonster.getName() + "!\n"
                + "You landed a hit!\n" + currentMonster.getName() + "attacked you!"
                + getBattleInfo() + ACTION_PROMPT;
    }

    private String defend() {
        player.setHealth(player.getHealth() - currentMonster.getDamage() / 2);

        if (player.getHealth() <= 0) {
            return onPlayerLose();
        }

        return currentMonster.getName() + "attacked you!" + getBattleInfo();
    }

    private String onPlayerLose() {
        return "You lost!";
    }

    @Override
    protected String flee() throws GameException {
        player.getCurrentRoom().getRoomEvents().addFirst(currentMonster);
        changeGameState(source);
        return moveTo(player.getPreviousRoom().getID());
    }

    @Override
    protected String getIntroText() {
        return getBattleInfo() + ACTION_PROMPT;
    }
}
