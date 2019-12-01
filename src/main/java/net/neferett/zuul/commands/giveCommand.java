package net.neferett.zuul.commands;

import net.neferett.zuul.gamemiscs.Item;
import net.neferett.zuul.handlers.Command;
import net.neferett.zuul.handlers.ExtendableCommand;
import net.neferett.zuul.handlers.CharacterHandler;
import net.neferett.zuul.interpreter.Output;
import net.neferett.zuul.player.Character;
import net.neferett.zuul.player.Player;

@Command(name = "give", argsLength = 3, desc = "Give an item to a player", help = "give <player> <item_name> <item_weight>", gui = false)
public class giveCommand extends ExtendableCommand {

    @Override
    public boolean onCommand(Character player, String... args) {
        // Args can't overbound commandsHandler handle it
        Player playerToGive = CharacterHandler.getInstance().getPlayerByName(args[0]);
        Item item = new Item(args[1], Integer.parseInt(args[2]));

        Output.getInstance().print("Giving to " + playerToGive.getName() + " : " + item.getName() + "(" + item.getWeight() + ")");

        // Giving item and printing inventory
        playerToGive.add(item);
        playerToGive.printInventory();

        return true;
    }

}
