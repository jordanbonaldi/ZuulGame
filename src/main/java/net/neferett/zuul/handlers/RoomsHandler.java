package net.neferett.zuul.handlers;

import lombok.Data;
import lombok.experimental.Delegate;
import net.neferett.zuul.Zuul;
import net.neferett.zuul.gamemiscs.Exit;
import net.neferett.zuul.gamemiscs.Item;
import net.neferett.zuul.gamemiscs.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
public class RoomsHandler {

    /**
     * Delegating rooms to RoomsHandler
     */
    @Delegate
    List<Room> rooms = new ArrayList<>();

    /**
     *
     * Creating a new Room by name and description
     * This function can also take multiple items
     *
     * @param name Name of room
     * @param description Description of room
     * @param maximumExits Maximum exits allowed to this room
     * @param items Optional list of items to add
     */
    public void createRoom(String name, String description, int maximumExits, Item...items) {
        Room room = new Room(name, description, maximumExits);

        Arrays.asList(items).forEach(room::addItem);
        this.rooms.add(room);
    }

    /**
     *
     * @param room
     */
    public void createRoom(Room room) {
        this.rooms.add(room);
    }

    /**
     * Adding an exit by room name
     * @param room Room name
     * @param exits List of exits by var args
     */
    public void addExits(String room, Exit...exits) {
        Room _room = this.getRoom(room);

        /*
         * Filtering for null exits
         */
        Arrays.stream(exits).filter(Objects::nonNull).forEach(_room::addExits);
    }

    /**
     * Getting any room by name
     *
     * @param name Name of room
     * @return Room
     */
    public Room getRoom(String name) {
        return this.rooms.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     * Adding item on a room by room name
     * @param name Name of room
     * @param items List of items to add (infinite) by var args
     */
    public void addItemOnRoom(String name, Item...items) {
        Arrays.asList(items).forEach(e -> this.getRoom(name).addItem(e));
    }

    /**
     * Getting handler instance
     * @return RoomsHandler
     */
    public static RoomsHandler getInstance() {
        return Zuul.getInstance().getRoomsHandler();
    }

}
