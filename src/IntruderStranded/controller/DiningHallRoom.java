package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;

import java.util.List;
import java.util.Optional;

/**
 * Class: DiningHallRoom
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 17, 2024
 * This class extends Room and provides custom behaviour for the Dining Hall room.
 */
public class DiningHallRoom extends Room {
    private boolean sneakSuccess;
    private boolean returnRoomEvents;

    /**
     * Creates a new DiningHallRoom from a Room object.
     * @param room The room object.
     */
    protected DiningHallRoom(Room room) {
        super(room);
    }

    /**
     * Method: overrideInput
     * Allows a room to override the handling of user input.
     * @param commands The GameplayCommands instance calling this method.
     * @param input The string entered by the user.
     * @return An empty optional if the room does not want to override input handling, otherwise, an
     * optional containing the string to display.
     */
    @Override
    Optional<String> overrideInput(GameplayCommands commands, String input) throws GameException {
        if (sneakSuccess) {
            return Optional.empty();
        }

        if (super.getRoomEvents().isEmpty()) {
            return super.overrideInput(commands, input);
        }

        if (input.equals("LEFT")) {
            sneakSuccess = true;
            return Optional.of("You managed to sneak around the aliens.\n" + super.displayExits());
        } else if (input.equals("RIGHT")) {
            returnRoomEvents = true;
            return Optional.of("You were caught while trying to sneak around!" + commands.enterRoom());
        }

        throw new GameException("Please enter \"right\" or \"left\"");
    }

    /**
     * Method: canLeave
     * Checks if a player can leave a room.
     * @param player The current player.
     * @return Always returns true for this room.
     */
    @Override
    boolean canLeave(Player player) {
        return true;
    }

    /**
     * Method: getRoomEvents
     * Gets all room events in this room.
     * @return A list of room events.
     */
    @Override
    List<RoomEvent> getRoomEvents() {
        return returnRoomEvents ? super.getRoomEvents() : List.of();
    }

    /**
     * Method: getRoomDescription
     * Gets the description of this room.
     * @return The description of this room.
     */
    @Override
    public String getRoomDescription() {
        if (super.getRoomEvents().isEmpty()) {
            return "The room is now empty of aliens, allowing you to move freely.";
        }

        if (sneakSuccess) {
            return "Entering inside while sneaking around there are multiple aliens around. Some are sitting at tables while others are standing in line to get their meals. You cannot possibly fight against all of them.";
        }

        return super.getRoomDescription();
    }

    /**
     * Method: displayExits
     * Constructs the string for displaying the exits in this room.
     * @return The string to display.
     */
    @Override
    String displayExits() {
        if (sneakSuccess || super.getRoomEvents().isEmpty()) {
            return super.displayExits();
        }

        return "";
    }
}
