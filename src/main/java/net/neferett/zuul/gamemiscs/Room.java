package net.neferett.zuul.gamemiscs;

import lombok.Data;
import net.neferett.zuul.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private List<Player> players = new ArrayList<>();

    /**
     * Adding exits and cancel if exits array
     * is bigger than the authorized maximum exit
     * @param exits Array of exits
     */
    public void addExits(Exit...exits) {
        if (exits.length > this.maximumExits || exits.length + this.exits.size() > this.maximumExits) {
            System.out.println("Too many exits for Room " + this.name + ", maximum allowed " + this.maximumExits);
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
        return this.getItem(name, -1) != null;
    }

    /**
     * Get item by name
     * @param name Name of item
     * @return Item
     */
    public Item getItem(String name) {
        return this.getItem(name, -1);
    }

    /**
     * Get item by name and weight, if weight is lower than 0 it's ignored
     * @param name Name of item
     * @param weight Weight of item
     * @return item
     */
    public Item getItem(String name, int weight) {
        return this.items.stream().filter(e -> e.getName().equalsIgnoreCase(name)
                && (weight < 0 || e.getWeight() == weight)).findFirst().orElse(null);
    }

    /**
     * Remove item by name
     * @param name Name of item
     * @return boolean
     */
    public boolean removeItem(String name) {
        Item item = this.getItem(name, -1);

        if (null == item)
            return false;

        this.removeItem(item);

        return false;
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
        System.out.println("You are " + this.name);
        System.out.print("Exits: ");

        this.exits.forEach(e -> System.out.print(e.getName() + "(" + e.getRoom().getName() + ") "));

        System.out.println();
        System.out.print("Items: ");

        this.getItems().forEach(e ->
                System.out.print(e.getName()
                        + '(' + e.getWeight() + ')')
        );

        System.out.println();
    }
}
