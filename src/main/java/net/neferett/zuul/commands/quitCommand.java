package net.neferett.zuul.commands;

import net.neferett.zuul.Zuul;
import net.neferett.zuul.handlers.Command;
import net.neferett.zuul.handlers.ExtendableCommand;
import net.neferett.zuul.interpreter.Output;
import net.neferett.zuul.player.Character;
import net.neferett.zuul.player.Player;

@Command(name = "quit", argsLength = 0, desc = "Quit the game", help = "quit", gui = false)
public class quitCommand extends ExtendableCommand {

    @Override
    public boolean onCommand(Character player, String... args) {
        Output.getInstance().print("Stopping the game..");
        // Stopping thread loop
        Zuul.getInstance().getInterpreter().setActivated(false);
        return true;
    }

}
