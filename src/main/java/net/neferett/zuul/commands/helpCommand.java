package net.neferett.zuul.commands;

import net.neferett.zuul.handlers.Command;
import net.neferett.zuul.handlers.CommandsHandler;
import net.neferett.zuul.handlers.ExtendableCommand;
import net.neferett.zuul.interpreter.Output;
import net.neferett.zuul.player.Character;

@Command(name = "help", argsLength = 0, desc = "Print all commands", help = "help", gui = false)
public class helpCommand extends ExtendableCommand {

    @Override
    public boolean onCommand(Character player, String... args) {

        Output.getInstance().print("You are lost. You are alone. You wander");
        Output.getInstance().print("around at the university.");
        Output.getInstance().print("");
        Output.getInstance().print("Here's all commands");

        // Looping over all commands and printing name and description
        CommandsHandler.getInstance().getManager().forEach(e ->
            Output.getInstance().print(e.getCommand().name() + " - " + e.getCommand().desc())
        );

        return true;
    }

}
