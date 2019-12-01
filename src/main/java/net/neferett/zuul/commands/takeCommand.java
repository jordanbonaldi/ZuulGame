package net.neferett.zuul.commands;

import net.neferett.zuul.Zuul;
import net.neferett.zuul.gamemiscs.Item;
import net.neferett.zuul.gamemiscs.Room;
import net.neferett.zuul.handlers.Command;
import net.neferett.zuul.handlers.ExtendableCommand;
import net.neferett.zuul.interpreter.Output;
import net.neferett.zuul.player.Character;
import net.neferett.zuul.player.Player;

@Command(name = "take", argsLength = 1, desc = "Take an item", help = "take <item>")
public class takeCommand extends ExtendableCommand {

    @Override
    public boolean onCommand(Character player, String... args) {
        Room current = player.getCurrentRoom();

        // Check if room contains items
        if (current.getItems().size() == 0) {
            Output.getInstance().print("No items on this room");

            return false;
        }

        // Args can't overbound commandsHandler handle it
        Item item = current.getItem(args[0]);

        // If item is found in player's inventory
        if (item == null) {
            Output.getInstance().print("Item doesn't exist");

            return false;
        } else if (player.getTotalWeight() > Zuul.getInstance().getMaxWeight()) { // If this item will not be too heavy
            Output.getInstance().print("Item is too heavy to carry");

            return false;
        }

        // Removing item
        boolean removed = current.removeItem(args[0]);

        if (!removed)
            return false;

        // Adding item and printing information
        player.add(item);
        player.printInventory();

        return true;
    }

}
