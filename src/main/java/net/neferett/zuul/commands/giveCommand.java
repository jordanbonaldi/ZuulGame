package net.neferett.zuul.commands;

import net.neferett.zuul.gamemiscs.Item;
import net.neferett.zuul.handlers.Command;
import net.neferett.zuul.handlers.ExtendableCommand;
import net.neferett.zuul.handlers.PlayersHandler;
import net.neferett.zuul.player.Player;

@Command(name = "give", argsLength = 3, desc = "Give an item to a player", help = "give <player> <item_name> <item_weight>")
public class giveCommand extends ExtendableCommand {

    @Override
    public boolean onCommand(Player player, String... args) {
        // Args can't overbound commandsHandler handle it
        Player playerToGive = PlayersHandler.getInstance().getPlayerByName(args[0]);
        Item item = new Item(args[1], Integer.parseInt(args[2]));

        System.out.println("Giving to " + playerToGive.getName() + " : " + item.getName() + "(" + item.getWeight() + ")");

        // Giving item and printing inventory
        playerToGive.add(item);
        playerToGive.printInventory();

        return true;
    }

}
