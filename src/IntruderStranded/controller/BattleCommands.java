package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;
import IntruderStranded.model.GameDBCreate;

import java.util.ArrayList;
import java.util.List;

public class BattleCommands extends GameplayCommands {
    private final GameplayCommands source;
    private final List<Monster> originalMonsters;
    private final List<Monster> monsters;
    private Monster currentMonster;
    private boolean restartPrompted;
    private int battleStartHealth;
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

    private String getBattleInfo() {
        return "\nYour STATS: " + player.getStatus()
                + "\n" + currentMonster.getName() + " STATS: " + currentMonster.getStatus();
    }

    @Override
    protected String useItem(Item item) throws GameException {
        if (item.getConsumableType() == ConsumableType.FREEZING_POTION) {
            player.removeItem(item);
            currentMonster.freeze();
            return "You used the " + item.getItemName();
        }

        return player.useItem(item);
    }

    @Override
    protected String onInventoryClose() {
        return getBattleInfo() + ACTION_PROMPT;
    }

    private String attack() throws GameException {
        boolean monsterImmune = currentMonster.getName().equals("Slime") && !player.getEquippedWeapon().getItemName().equals("Flame Knife");
        String attackText = monsterImmune ? "The monster is immune to your current weapon!\n" : "You landed a hit!\n";

        if (!monsterImmune) {
            currentMonster.setHealth(Math.max(currentMonster.getHealth() - player.getDamage(), 0));
        }

        if (currentMonster.getHealth() <= 0) {
            monsters.remove(currentMonster);
            currentMonster.delete();

            int scoreGained = player.getHealth() <= battleStartHealth / 2 ? 5 : 10;
            if (currentMonster.getName().equals("Boss: Supreme Alien Commander")) {
                scoreGained += 20;
            }
            player.addScore(scoreGained);

            String display = "You charged on " + currentMonster.getName() + "!\n"
                    + attackText + getBattleInfo()
                    + "\n\nYou have defeated " + currentMonster.getName()
                    + "\n(+" + scoreGained + " score) New Score: " + player.getScore() + "\n\n";

            if (monsters.isEmpty()) {
                String output = source.setRewards(currentMonster.getRewards());
                source.reloadCurrentRoom();
                changeGameState(source);
                display += player.getCurrentRoom().display(player) + (output.isEmpty() ? "" : "\n" + output);
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

    private String defend() {
        player.setHealth(player.getHealth() - currentMonster.getDamage() / 2);

        if (player.getHealth() <= 0) {
            return onPlayerLose();
        }

        return currentMonster.getName() + " attacked you!\n" + getBattleInfo();
    }

    private String onPlayerLose() {
        restartPrompted = true;
        return "You have been defeated\nWould you like to restart?";
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
