package net.neferett.zuul.gamemiscs;

import lombok.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.neferett.zuul.interpreter.Output;
import net.neferett.zuul.player.Character;

@Data
public class Room
{
    /**
     * Room name | Field constructor
     */
    private final String name;
    /**
     * Room description | Field constructor
     */
    private final String description;

    /**
     * Room maximum exit | Field constructor
     */
    private final int maximumExits;

    private List<Exit> exits = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private List<Character> characters = new ArrayList<>();

    /**
     * Adding exits and cancel if exits array
     * is bigger than the authorized maximum exit
     * @param exits Array of exits
     */
    public void addExits(Exit...exits) {
        if (exits.length > this.maximumExits || exits.length + this.exits.size() > this.maximumExits) {
            Output.getInstance().print("Too many exits for Room " + this.name + ", maximum allowed " + this.maximumExits);
            return;
        }

        this.exits.addAll(Arrays.asList(exits));
    }

    /**
     * Get Exit object by name
     * @param name Name of exit
     * @return Exit
     */
    public Exit getExit(String name) {
        return this.exits.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     * Add item to room
     * @param item Item to add
     */
    public void addItem(Item item) {
        this.items.add(item);
    }

    /**
     * Add item by name and weight, basically create a new one
     * @param name Name of item to add
     * @param weight Weight of item to add
     */
    public void addItem(String name, int weight) {
        this.items.add(new Item(name, weight));
    }

    /**
     * Contains an item by name
     * @param name Name of item
     * @return boolean
     */
    public boolean containsItem(String name) {
        return this.getItem(name) != null;
    }

    /**
     * Get item by name
     * @param name Name of item
     * @return Item
     */
    public Item getItem(String name) {
        return this.items.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     * Remove item by name
     * @param name Name of item
     * @return boolean
     */
    public boolean removeItem(String name) {
        Item item = this.getItem(name);

        if (null == item)
            return false;

        this.removeItem(item);

        return true;
    }

    /**
     * Remove item by Item Object
     * @param item Item Object
     */
    public void removeItem(Item item) {
        this.items.remove(item);
    }

    /**
     * Print room exits and items informations
     */
    public void printRoomInformation() {
        Output.getInstance().print("You are " + this.name);
        Output.getInstance().printWithoutCarriageReturn("Exits: ");

        this.exits.forEach(e -> Output.getInstance().printWithoutCarriageReturn(e.getName() + "(" + e.getRoom().getName() + ") "));

        Output.getInstance().print("");
        Output.getInstance().printWithoutCarriageReturn("Items: ");

        this.getItems().forEach(e ->
                Output.getInstance().printWithoutCarriageReturn(e.getName()
                        + "(" + e.getWeight() + ") ")
        );

        Output.getInstance().print("");
    }
}
