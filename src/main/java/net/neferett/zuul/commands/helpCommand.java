package net.neferett.zuul.commands;

import net.neferett.zuul.handlers.Command;
import net.neferett.zuul.handlers.CommandsHandler;
import net.neferett.zuul.handlers.ExtendableCommand;
import net.neferett.zuul.player.Player;

@Command(name = "help", argsLength = 0, desc = "Print all commands", help = "help")
public class helpCommand extends ExtendableCommand {

    @Override
    public boolean onCommand(Player player, String... args) {

        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Here's all commands");

        // Looping over all commands and printing name and description
        CommandsHandler.getInstance().getManager().forEach(e ->
            System.out.println(e.getCommand().name() + " - " + e.getCommand().desc())
        );

        return true;
    }

}
