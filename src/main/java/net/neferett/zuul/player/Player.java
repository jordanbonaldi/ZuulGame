package net.neferett.zuul.player;

import lombok.Data;
import lombok.experimental.Delegate;
import net.neferett.zuul.gamemiscs.Item;
import net.neferett.zuul.gamemiscs.Room;

import java.util.ArrayList;

@Data
public class Player {

    private final String name;

    private Room currentRoom;

    public Player(String name, Room currentRoom) {
        this.name = name;
        this.currentRoom = currentRoom;
    }

    /**
     * Initialise a player only by its name
     * @param name Player Name
     */
    public Player(String name) {
        this(name, null);
    }

    /**
     * Delegating player's inventory of player class
     */
    @Delegate
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * Sum of all the weights inside item list
     * @return int
     */
    public int getTotalWeight() {
        return items.stream().map(Item::getWeight).reduce(0, Integer::sum);
    }

    /**
     * Prints player inventory
     */
    public void printInventory() {
        System.out.println(this.name + "'s inventory: ");
        this.items.forEach(e -> System.out.println(e.getName() + "(" + e.getWeight() + ")"));
    }

    /**
     * Get item on player's inventory by name
     *
     * @param name item name by string
     * @return Item
     */
    public Item getItem(String name) {
        return this.items.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
