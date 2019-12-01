package net.neferett.zuul.player;

import lombok.Data;
import lombok.experimental.Delegate;
import net.neferett.zuul.gamemiscs.Item;
import net.neferett.zuul.gamemiscs.Room;
import net.neferett.zuul.interpreter.Output;

import java.util.ArrayList;

@Data
public abstract class Character {

    protected final String name;

    protected Room currentRoom;

    public Character(String name, Room currentRoom) {
        this.name = name;
        this.currentRoom = currentRoom;
        currentRoom.getCharacters().add(this);
    }

    /**
     * Delegating player's inventory of player class
     */
    @Delegate
    protected ArrayList<Item> items = new ArrayList<>();

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
        Output.getInstance().print(this.name + "'s inventory: ");
        this.items.forEach(e -> Output.getInstance().print(e.getName() + "(" + e.getWeight() + ") "));
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

    public abstract void nextRound();
}
