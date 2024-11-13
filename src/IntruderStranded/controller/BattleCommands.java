package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;

import java.util.List;

public class BattleCommands extends GameplayCommands {
    private final GameplayCommands source;
    private final Monster currentMonster;
    private final List<Monster> monsters;

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
            INV - Open inventory
            Flee - Flee from the current monster
            """;
    }

    private String getBattleInfo() {
        return "\n";
    }

    private String attack() {
        // TODO - implement GameplayCommands.attack
        throw new UnsupportedOperationException();
    }

    private String defend() {
        // TODO - implement GameplayCommands.defend
        throw new UnsupportedOperationException();
    }

    @Override
    protected String flee() throws GameException {
        player.getCurrentRoom().getRoomEvents().addFirst(currentMonster);
        changeGameState(source);
        return moveTo(player.getPreviousRoom().getID());
    }

    @Override
    protected String getIntroText() {
        return getBattleInfo();
    }
}
