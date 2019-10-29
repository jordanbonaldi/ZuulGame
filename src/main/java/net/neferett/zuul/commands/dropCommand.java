package net.neferett.zuul.commands;

import net.neferett.zuul.gamemiscs.Item;
import net.neferett.zuul.gamemiscs.Room;
import net.neferett.zuul.handlers.Command;
import net.neferett.zuul.handlers.ExtendableCommand;
import net.neferett.zuul.player.Player;

@Command(name = "drop", argsLength = 1, desc = "Drop an item from player's inventory", help = "drop <item>")
public class dropCommand extends ExtendableCommand {

    @Override
    public boolean onCommand(Player player, String... args) {
        // args[0] can't overbound it's handler in command handler
        Item item = player.getItem(args[0]);
        Room room = player.getCurrentRoom();

        // Checking if item is on player's inventory
        if (item == null) {
            System.out.println(args[0] + "is not in your inventory");

            return false;
        }

        // Remove item and print
        player.remove(item);
        player.printInventory();

        // Add item and print
        room.addItem(item);
        room.printRoomInformation();

        return true;
    }

}
